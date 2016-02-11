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

package com.cisco.dvbu.ps.common.adapters.connect;

import java.net.URL;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import com.cisco.dvbu.ps.common.adapters.common.AdapterConstants;
import com.cisco.dvbu.ps.common.adapters.common.AdapterException;
import com.cisco.dvbu.ps.common.adapters.common.SoapHttpConnectorCallback;
import com.cisco.dvbu.ps.common.adapters.config.ConnectorConfig;
import com.cisco.dvbu.ps.common.adapters.config.SoapHttpConfig;
import com.cisco.dvbu.ps.common.adapters.util.XmlUtils;
import com.cisco.dvbu.ps.common.util.XMLUtils;

/**
* @author vmadired, March 2015
*/

public class SoapHttpConnector implements Connector {
	private static Log log	 									= LogFactory.getLog(SoapHttpConnector.class);

	private SoapHttpConfig shConfig = null;
	
	public SoapHttpConnector(ConnectorConfig shConfig) {
		this.shConfig = (SoapHttpConfig)shConfig;
		
	}
	public SoapHttpConnector(SoapHttpConnector conn) {
		this.shConfig = conn.shConfig;
	}
	
	private Document send(SoapHttpConnectorCallback cb, String requestXml) throws AdapterException {
		log.debug("Entered send: " + cb.getName());
		
		// set up the factories and create a SOAP factory
		SOAPConnection soapConnection;
		Document doc = null;
		URL endpoint = null;
		try {
			soapConnection = SOAPConnectionFactory.newInstance().createConnection();
			MessageFactory messageFactory = MessageFactory.newInstance();
			
			// Create a message from the message factory.
			SOAPMessage soapMessage = messageFactory.createMessage();
			
			// Set the SOAP Action here
			MimeHeaders headers = soapMessage.getMimeHeaders(); 
			headers.addHeader("SOAPAction", cb.getAction()); 
			
			// set credentials
//			String authorization = new sun.misc.BASE64Encoder().encode((shConfig.getUser() + "@" + shConfig.getDomain() + ":" + shConfig.getPassword()).getBytes());
			String authorization = new String(org.apache.commons.codec.binary.Base64.encodeBase64((shConfig.getUser() + "@" + shConfig.getDomain() + ":" + shConfig.getPassword()).getBytes()));
            headers.addHeader("Authorization", "Basic " + authorization);
            log.debug("Authentication: " + authorization);
            
			
			// create a SOAP part have populate the envelope
			SOAPPart soapPart = soapMessage.getSOAPPart();
			SOAPEnvelope envelope = soapPart.getEnvelope();
			envelope.setEncodingStyle( SOAPConstants.URI_NS_SOAP_ENCODING );
			
		    SOAPHeader head = envelope.getHeader();
		    if (head == null)
                head = envelope.addHeader();
		    
			// create a SOAP body
		    SOAPBody body = envelope.getBody();
		    
		    log.debug("Request XSL Style Sheet:\n"+XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(cb.getRequestBodyXsl())));

		    // convert request string to document and then transform
			body.addDocument(XmlUtils.xslTransform(XmlUtils.stringToDocument(requestXml), cb.getRequestBodyXsl()));
	
		    // build the request structure
		    soapMessage.saveChanges();
		    log.debug("Soap request successfully built: ");
		    log.debug("  Body:\n"+XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(XmlUtils.nodeToString(body))));
		    
		    if (shConfig.useProxy()) {
				System.setProperty("http.proxySet", "true");
				System.setProperty("http.proxyHost", shConfig.getProxyHost());
				System.setProperty("http.proxyPort", ""+shConfig.getProxyPort());
				if (shConfig.useProxyCredentials()) {
					System.setProperty("http.proxyUser", shConfig.getProxyUser());
					System.setProperty("http.proxyPassword", shConfig.getProxyPassword());
				}
		    }
		    
		    endpoint = new URL(shConfig.getEndpoint(cb.getEndpoint()));
		    
		    // now make that call over the SOAP connection
		    SOAPMessage reply = null;
		    AdapterException ae = null;
		    
		    for (int i=1; (i <= shConfig.getRetryAttempts() && reply==null); i++) {
			    log.debug("Attempt " + i + ": sending request to endpoint: " + endpoint);
			    try {
			    	reply = soapConnection.call( soapMessage, endpoint );
			    	log.debug("Attempt " + i + ": received response: " + reply);
				} catch (Exception e) {
					ae = new AdapterException(502, String.format(AdapterConstants.ADAPTER_EM_CONNECTION, endpoint), e);
					Thread.sleep(100);
				}
		    }
	
		    // close down the connection
		    soapConnection.close();
		    
		    if (reply==null)
		    	throw ae;
		    
		    SOAPFault fault = reply.getSOAPBody().getFault();
		    if (fault == null) {
		    	doc = reply.getSOAPBody().extractContentAsDocument();
		    } else {
		    	// Extracts the entire Soap Fault message coming back from CIS
		    	String faultString = XmlUtils.nodeToString(fault);
		    	throw new AdapterException(503, faultString, null);
		    }
		} catch (AdapterException e) {
			throw e;
		} catch (Exception e) {
			throw new AdapterException(504, String.format(AdapterConstants.ADAPTER_EM_CONNECTION, shConfig.getEndpoint(cb.getEndpoint())), e);
		} finally {
			if (shConfig.useProxy()) {
				System.setProperty("http.proxySet", "false");
			}
		}
		log.debug("Exiting send: " + cb.getName());

	    return doc;
	}

	// mtinius - remove override
	//@Override
	public void init() {
	}

	// mtinius - remove override
	//@Override
	public void cleanup() {
	}

	// mtinius - remove override
	//@Override
	public String execute(String cbName, String requestXml) throws AdapterException {
		log.debug("Entered execute: " + cbName);
		SoapHttpConnectorCallback cb = shConfig.getCallback(cbName);

		// execute the external service operation
		Document doc = send(cb, requestXml);
		String output = null;

		try {
		    log.debug("Response XSL Style Sheet:\n"+XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(cb.getResponseBodyXsl())));
			// transform the response body to string
		  	output = XmlUtils.nodeToString(XmlUtils.xslTransform(doc, cb.getResponseBodyXsl()));
		} catch (Exception e) {
			throw new AdapterException(505, "Transformation Error! Failed to transform response.", e);
		}

	  	log.debug("Exiting execute: " + cbName + " response = " + output);
		return output;
	}

	@Override
	public Object clone(){
		return new SoapHttpConnector(this);
	}
}
