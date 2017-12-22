/**
 * (c) 2017 TIBCO Software Inc. All rights reserved.
 * 
 * Except as specified below, this software is licensed pursuant to the Eclipse Public License v. 1.0.
 * The details can be found in the file LICENSE.
 * 
 * The following proprietary files are included as a convenience, and may not be used except pursuant
 * to valid license to Composite Information Server or TIBCO(R) Data Virtualization Server:
 * csadmin-XXXX.jar, csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar,
 * csext-XXXX.jar, csjdbc-XXXX.jar, csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar,
 * and customproc-XXXX.jar (where -XXXX is an optional version number).  Any included third party files
 * are licensed under the terms contained in their own accompanying LICENSE files, generally named .LICENSE.txt.
 * 
 * This software is licensed AS-IS. Support for this software is not covered by standard maintenance agreements with TIBCO.
 * If you would like to obtain assistance with this software, such assistance may be obtained through a separate paid consulting
 * agreement with TIBCO.
 * 
 */

package com.tibco.ps.common.adapters.config;

import java.util.HashMap;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.ps.common.adapters.common.AdapterConstants;
import com.tibco.ps.common.adapters.common.AdapterException;
import com.tibco.ps.common.adapters.common.SoapHttpConnectorCallback;
import com.tibco.ps.common.adapters.config.ConnectorConfig;
import com.tibco.ps.common.adapters.protocol.AdapterProxy;

/**
 * @author vmadired, March 2015
 */

public class SoapHttpConfig implements ConnectorConfig {
	private static Log log	 										= LogFactory.getLog(SoapHttpConfig.class);
	private HashMap<String,SoapHttpConnectorCallback> connCallbacks = new HashMap<String,SoapHttpConnectorCallback>();
	private HashMap<String,String> connEndpoints					= new HashMap<String,String>();
	private AdapterConfig config;
	private String name;
	private String endpoint;
	private String nsUrl;
	private String nsPrefix;
	private int retryAttempts;
	private int maxClients;
	private int minClients;
	private boolean validated;
	private AdapterProxy proxyConfig;
	
	SoapHttpConfig(String name, Document doc, AdapterConfig config) throws AdapterException {
		this.name = name;
		this.config = config;
		validated = false;
		parse(doc);
		proxyConfig = new AdapterProxy(config);
	}

	// mtinius - remove override
	//@Override
	public String getName() {
		return name;
	}

	// mtinius - remove override
	//@Override
	public String getType() {
		return AdapterConstants.CONNECTOR_TYPE_SOAPHTTP;
	}
	
	public String getEndpoint(String ep) {
		String http = config.getProperty(AdapterConstants.ADAPTER_USE_HTTPS);
		int port = Integer.parseInt (config.getProperty(AdapterConstants.ADAPTER_PORT));
		
		http = ((http != null) && (http.equalsIgnoreCase("true"))) ? "https" : "http";
		if (http.equals("https")) port += 2;
		return http + "://" + config.getProperty(AdapterConstants.ADAPTER_HOST) + ":" + port + connEndpoints.get(ep);
	}
	
	public String getNsUrl() {
		return nsUrl;
	}

	public String getNsPrefix() {
		return nsPrefix;
	}

	public int getRetryAttempts() {
		return config.getRetryAttempts();
	}

	public int getMaxClients() {
		return config.getMaxClients();
	}

	public int getMinClients() {
		return config.getMinClients();
	}

	private void parseCallback(Element eElement) throws AdapterException {
		XPath xpath = XPathFactory.newInstance().newXPath();
		try {
			SoapHttpConnectorCallback cb = new SoapHttpConnectorCallback();
			cb.setOperation(eElement.getAttribute(AdapterConstants.CONNECTOR_SE_NAME));
//			log.debug("Operation: " + cb.getOperation());
			cb.setEndpoint(eElement.getAttribute(AdapterConstants.CONNECTOR_SH_ENDPOINT));
//			log.debug("Endpoint: " + cb.getEndpoint());
			cb.setName(cb.getEndpoint() + AdapterConstants.CONNECTOR_EP_SEPARATOR + cb.getOperation());
			Element e = (Element) xpath.evaluate(AdapterConstants.XPATH_CONN_CB_SOAPACTION, eElement, XPathConstants.NODE);
			cb.setAction(e.getTextContent());
//			log.debug(e.getNodeName() + ": " + e.getTextContent());
			e = (Element) xpath.evaluate(AdapterConstants.XPATH_CONN_CB_RQ_BODY, eElement, XPathConstants.NODE);
			cb.setRequestBodyXsl(e.getTextContent().trim());
//			log.debug(e.getNodeName() + ": " + e.getTextContent());
			e = (Element) xpath.evaluate(AdapterConstants.XPATH_CONN_CB_RS_BODY, eElement, XPathConstants.NODE);
			cb.setResponseBodyXsl(e.getTextContent().trim());
//			log.debug(e.getNodeName() + ": " + e.getTextContent());
			connCallbacks.put(cb.getName(), cb);
		} catch (Exception e) {
			log.error("Configuration File Error! One or more mandatory configuration options are missing");
			throw new AdapterException(401,
					"Configuration File Error! One or more mandatory configuration options are missing.", e);
		}
	}
	
	private void parseCallbacks(Element parent) throws AdapterException {
		XPath xpath = XPathFactory.newInstance().newXPath();
		try {
			NodeList nlList = parent.getElementsByTagName(AdapterConstants.CONNECTOR_CALLBACK);
			if ( nlList != null && nlList.item(0) != null) {
				for (int i=0; i < nlList.getLength(); i++) {
					parseCallback((Element)nlList.item(i));
				}
			}
		} catch (NullPointerException e) {
			throw new AdapterException(402,
					"Configuration File Error! One or more mandatory configuration options are missing.", e);
		}		
	}
	
	private String getTagValue(String sTag, Element eElement) {
		String value = null;
		NodeList nlList = eElement.getElementsByTagName(sTag);
		if ( nlList != null && nlList.item(0) != null) {
			value = ((Node) nlList.item(0).getChildNodes().item(0)).getNodeValue();
		}
		return value;
	}
	
	private void parse(Document doc) throws AdapterException {
		XPath xpath = XPathFactory.newInstance().newXPath();
		try {
			Element e = (Element) xpath.evaluate(AdapterConstants.XPATH_CONN_ENDPOINTS, doc, XPathConstants.NODE);
			if (e != null) {
				NodeList nlList = e.getElementsByTagName(AdapterConstants.CONNECTOR_SH_ENDPOINT);
				if ( nlList != null && nlList.item(0) != null) {
					for (int i=0; i < nlList.getLength(); i++) {
						Element elem = (Element)nlList.item(i);
						connEndpoints.put(elem.getAttribute(AdapterConstants.CONNECTOR_SE_NAME), elem.getTextContent());
						log.debug(elem.getAttribute(AdapterConstants.CONNECTOR_SE_NAME) + ": " + elem.getTextContent());
					}
				}
			}
		} catch (Exception e) {
			log.error("Configuration File Error! One or more mandatory configuration options are missing");
			throw new AdapterException(403,
					"Configuration File Error! One or more mandatory configuration options are missing.", e);
		}		
		try {
			parseCallbacks((Element) xpath.evaluate(AdapterConstants.XPATH_CALLBACKS, doc, XPathConstants.NODE));
		} catch (AdapterException ae) {
			throw ae;
		} catch (Exception e) {
			log.error("Configuration File Error! One or more mandatory configuration options are missing");
			throw new AdapterException(404,
					"Configuration File Error! One or more mandatory configuration options are missing.", e);
		}
	}
	
	public String getProperty(String name) {
		return config.getProperty(name.toUpperCase());
	}
	
	public String getProperty(String name, String defaultValue) {
		String ret = getProperty(name);
		return (ret!=null) ? ret : defaultValue;
	}
	
	public SoapHttpConnectorCallback getCallback(String callbackName) {
		return connCallbacks.get(callbackName);
	}
	
	public String getUser() {
		return getProperty(AdapterConstants.ADAPTER_USER);
	}
	
	public String getDomain() {
		return (getProperty(AdapterConstants.ADAPTER_DOMAIN) == null) ? "composite" : getProperty(AdapterConstants.ADAPTER_DOMAIN);
	}
	
	public String getPassword() {
		return getProperty(AdapterConstants.ADAPTER_PSWD);
	}

	public boolean useProxy() {
		return proxyConfig.useProxy();
	}
	
	public boolean useProxyCredentials() {
		return proxyConfig.useCredentials();
	}
	
	public String getProxyHost() {
		return proxyConfig.getHost();
	}
	
	public int getProxyPort() {
		return proxyConfig.getPort();
	}
	
	public String getProxyUser() {
		return proxyConfig.getUser();
	}
	
	public String getProxyPassword() {
		return proxyConfig.getPassword();
	}

	public void validate() {
		if (!validated) {
			validated = true;
		}
	}
}
