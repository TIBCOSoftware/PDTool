/**
 * (c) 2015 Cisco and/or its affiliates. All rights reserved.
 * 
 * This software is released under the Eclipse Public License. The details can be found in the file LICENSE. 
 * Any dependent libraries supplied by third parties are provided under their own open source licenses as 
 * described in their own LICENSE files, generally named .LICENSE.txt. The libraries supplied by Cisco as 
 * part of the Composite Information Server/Cisco Data Virtualization Server, particularly csadmin-XXXX.jar, 
 * csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar, csext-XXXX.jar, csjdbc-XXXX.jar, 
 * csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar, and customproc-XXXX.jar (where -XXXX is an 
 * optional version number) are provided as a convenience, but are covered under the licensing for the 
 * Composite Information Server/Cisco Data Virtualization Server. They cannot be used in any way except 
 * through a valid license for that product.
 * 
 * This software is released AS-IS!. Support for this software is not covered by standard maintenance agreements with Cisco. 
 * Any support for this software by Cisco would be covered by paid consulting agreements, and would be billable work.
 * 
 */

package com.cisco.dvbu.ps.common.adapters.config;

import java.util.HashMap;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cisco.dvbu.ps.common.adapters.common.AdapterConstants;
import com.cisco.dvbu.ps.common.adapters.common.AdapterException;

/**
 * @author vmadired, March 2015
 */

public class AdapterConfig {
	private static Log log	 									= LogFactory.getLog(AdapterConfig.class);
	private HashMap<String,ConnectorConfig> connConfigs			= new HashMap<String,ConnectorConfig>();
	private Properties adapterProps;
	private String name;
	private String desc;
	private String cisVersion;
	private int retryAttempts;
	private int maxClients;
	private int minClients;
	// Will be used to create the 1st connector for connection pool.
	// The assumption is we will have only one type of connector per adapter
	private String connectorType;  
	private Document cfgDoc;

	public AdapterConfig(Properties props, Document cfgDoc) throws AdapterException {
		log.debug("Entered AdapterConfig Constructor");
		if (cfgDoc==null) {
			throw new AdapterException(301, "Configuration file DOM is null", null);
		}
		this.cfgDoc = cfgDoc;
		connectorType = AdapterConstants.CONNECTOR_TYPE_UNKNOWN;
		adapterProps = props;
		parse();
		log.debug("Exiting AdapterConfig Constructor");
	}

	private void parse() throws AdapterException {
		log.debug("Entered parse");
		cfgDoc.getDocumentElement().normalize();
		parseAdapterCommon(cfgDoc);
		parseConnectors(cfgDoc);
		validate();
		log.debug("Exiting parse");
	}
	
	private String getTagValue(String sTag, Element eElement) {
//		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
//				.getChildNodes();
		String value = null;
		NodeList nlList = eElement.getElementsByTagName(sTag);
		if ( nlList != null && nlList.item(0) != null) {
			value = ((Node) nlList.item(0).getChildNodes().item(0)).getNodeValue();
		}
		return value;
	}
	
	private HashMap<String,String> getElementsByTagName(String sTag, Element eElement) {
		String value = null;
		HashMap<String,String> map = new HashMap<String,String>();
		NodeList nlList = eElement.getElementsByTagName(sTag);
		if ( nlList != null && nlList.item(0) != null) {
			NodeList nodes = nlList.item(0).getChildNodes();
			for (int i=0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				map.put(node.getNodeName(), node.getTextContent());
			}
		}
		return map;
	}
	
	private void parseAdapterCommon(Document doc) throws AdapterException {
		log.debug("Root element :" + doc.getDocumentElement().getNodeName());
		XPath xpath = XPathFactory.newInstance().newXPath();
		try {
			Attr a = (Attr) xpath.evaluate(AdapterConstants.XPATH_CONFIG_VERSION, doc, XPathConstants.NODE);
			log.debug(a.getName() + ": " + a.getValue());
			Element e = (Element) xpath.evaluate(AdapterConstants.XPATH_NAME, doc, XPathConstants.NODE);
			name = e.getTextContent();
			log.debug(e.getNodeName() + ": " + e.getTextContent());
			e = (Element) xpath.evaluate(AdapterConstants.XPATH_DESC, doc, XPathConstants.NODE);
			desc = e.getTextContent();
			log.debug(e.getNodeName() + ": " + e.getTextContent());
			e = (Element) xpath.evaluate(AdapterConstants.XPATH_CIS_VERSION, doc, XPathConstants.NODE);
			cisVersion = e.getTextContent();
			log.debug(e.getNodeName() + ": " + e.getTextContent());
			e = (Element) xpath.evaluate(AdapterConstants.XPATH_CONN_RETRYATTMPTS, doc, XPathConstants.NODE);
			retryAttempts = (e!= null && e.getTextContent()!=null) ? Integer.parseInt(e.getTextContent()) : -1;
			log.debug("retryAttempts: " + retryAttempts);
			e = (Element) xpath.evaluate(AdapterConstants.XPATH_CONN_MAXCLIENTS, doc, XPathConstants.NODE);
			maxClients = (e!= null && e.getTextContent()!=null) ? Integer.parseInt(e.getTextContent()) : -1;
			log.debug("maxclients: " + maxClients);
			e = (Element) xpath.evaluate(AdapterConstants.XPATH_CONN_MINCLIENTS, doc, XPathConstants.NODE);
			minClients = (e!= null && e.getTextContent()!=null) ? Integer.parseInt(e.getTextContent()) : -1;
			log.debug("minclients: " + minClients);
		} catch (Exception e) {
			log.error("Configuration File Error! One or more mandatory configuration options are missing");
			throw new AdapterException(302,
					"Configuration File Error! One or more mandatory configuration options are missing.", e);
		}		
	}
	
	private void parseSoapHttpConnector(String name, Document doc) throws AdapterException {
		try {
			SoapHttpConfig sc = new SoapHttpConfig(name, doc, this);
			connConfigs.put(name, sc);
		} catch (NullPointerException e) {
			log.error("Configuration File Error! One or more mandatory configuration options are missing");
			throw new AdapterException(306,
					"Configuration File Error! One or more mandatory configuration options are missing.", e);
		}
	}
	
	private void parseConnectors(Document doc) throws AdapterException {
		XPath xpath = XPathFactory.newInstance().newXPath();
		try {
			parseSoapHttpConnector("SOAPHTTP", doc);
		} catch (AdapterException ae) {
			throw ae;
		} catch (Exception e) {
			log.error("Configuration File Error! One or more mandatory configuration options are missing");
			throw new AdapterException(308,
					"Configuration File Error! One or more mandatory configuration options are missing.", e);
		}		
	}
	
	public void validate() throws AdapterException {
	}
	
	public ConnectorConfig getConnectorConfig(String name) {
		return connConfigs.get(name.toUpperCase());
	}
	
	public HashMap<String,ConnectorConfig> getConnectorConfigs() {
		return connConfigs;
	}
	
	public String getProperty(String name) {
		return (adapterProps != null) ? adapterProps.getProperty(name.toUpperCase()) : null;
	}
	
	public String getConnectorType() {
		return connectorType;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public String getCisVersion() {
		return cisVersion;
	}
	
	public int getRetryAttempts() {
		return retryAttempts;
	}

	public int getMaxClients() {
		return maxClients;
	}

	public int getMinClients() {
		return minClients;
	}

	public static void main( String[] args ) throws Exception {
		org.apache.log4j.BasicConfigurator.configure();
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse("D:\\src\\composite\\wsadapter\\cis_adapter_config.xml");
		AdapterConfig ac = new AdapterConfig(null, doc);
		ConnectorConfig cfg = ac.getConnectorConfig("soaphttp");
		ac.validate();
		if (cfg instanceof SoapHttpConfig)
			System.out.println("Configuration type of CIS: SOAPHTTP");
		else
			System.out.println("Unknown config type");
	}
	
}
