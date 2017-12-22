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

package com.tibco.ps.common.adapters.util;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xalan.processor.TransformerFactoryImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
* @author vmadired, March 2015
*/

public class XmlUtils {
	private static Log log = LogFactory.getLog(XmlUtils.class);
			
	public static String nodeToString(Node node) throws Exception {
		StringWriter sw = new StringWriter();
		try {
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(node), new StreamResult(sw));
		} catch (TransformerException te) {
			log.error("Xml to String Transformation error!" + te.getMessage());
			throw te;
		}
		return sw.toString();
	}
	
	public static Document stringToDocument(String xmlString) throws Exception {
		// Insure that there are no carriage return and line feeds in the string.
		xmlString = xmlString.replaceAll("[\n\r]", "");
		
		// Convert from string to Document
		Document doc = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(new ByteArrayInputStream(xmlString.getBytes()));
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			doc = null;
			log.error("String to Xml Transformation error!" + e.getMessage());
			throw e;
		}
		return doc;
	}

	public static String getXpathValue(String xpathStr, Document doc) throws Exception {
		String value = null;
		try {
			XPath xpath = XPathFactory.newInstance().newXPath();
			Element e = (Element) xpath.evaluate(xpathStr, doc, XPathConstants.NODE);
			value = e.getTextContent();
		} catch (Exception e) {
			throw e;
		}
		return value;
	}

	public static Document xslTransform(Node xmlData, String xsl) throws Exception {
		if (xsl.trim().length() == 0) {
			//System.out.println("No XSL provided.");
			return (Document) xmlData;
		} else {
			return xslTransform(xmlData, new StreamSource(new StringReader(xsl)));
		}
	}
	

	public static Document xslTransform(Node xmlData, StreamSource xslSource) throws Exception {
	    TransformerFactory tFactory = new TransformerFactoryImpl();
	    Transformer transformer;
	    DOMResult domRes;
	    Document doc = null;
		try {
			transformer = tFactory.newTransformer(xslSource);
		    domRes = new DOMResult();

		    // perform transformation
		  	transformer.transform(new DOMSource(xmlData), domRes);
		  	doc = (Document)domRes.getNode();
//		  	log.debug(XmlUtils.nodeToString(doc));
		} catch (Exception e) {
			log.error("Xslt Transformation error! " + e.getMessage());
			throw e;
		}
		return doc;
	}
	
	// mtinius: This is in a prototyping phase
	public static String getNodeIterationValue(String tagName, String doc) throws Exception {
		
		String value = null;
		
		try {
			// Convert string to a DOM object
			Document docXML = stringToDocument(doc);
			NodeList node1 = docXML.getElementsByTagName(tagName);
			if (node1 != null && node1.item(0) != null) {
				for (int i=0; i < node1.getLength(); i++) {
					NodeList node2 = node1.item(i).getChildNodes();
					if (node2 != null && node2.item(0) != null) {
						for (int j=0; j < node2.getLength(); j++) {
							Node n = node2.item(j);
							System.out.println("name="+n.getNodeName()+"  value="+n.getTextContent());
						}
					}
				}
			}

		}
		catch (Exception e) {
			throw e;
		}
		return value;
	}

	public static String getTagValue(String sTag, Element eElement) {
//		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
//				.getChildNodes();
		String value = null;
		NodeList nlList = eElement.getElementsByTagName(sTag);
		if ( nlList != null && nlList.item(0) != null) {
			value = ((Node) nlList.item(0).getChildNodes().item(0)).getNodeValue();
		}
		return value;
	}
	
	public static HashMap<String,String> getElementsByTagNameHashMap(String sTag, Element eElement) {
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

	public static String[] getElementsByTagNameArray(String sTag, Element eElement) {
		String[] array = new String[0];
		NodeList nlList = eElement.getElementsByTagName(sTag);
		if ( nlList != null && nlList.item(0) != null) {
			NodeList nodes = nlList.item(0).getChildNodes();
			array = new String[nodes.getLength()];
			for (int i=0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				array[i] = node.getTextContent();
			}
		}
		return array;
	}

	/* Given an XML string, extract the child nodes of the sTag that is passed in.
	 * 
	 * For example:
	 * sTag =  common:attribute
	 * doc =
		 	<server:attributes>
				<common:attribute>
					<common:name>/monitor/server/connection/domain</common:name>
					<common:type>STRING</common:type>
					<common:value>composite</common:value>
				</common:attribute>
			</server:attributes>
	 * @return String
	 * 			<common:attribute>
					<common:name>/monitor/server/connection/domain</common:name>
					<common:type>STRING</common:type>
					<common:value>composite</common:value>
				</common:attribute>
	 */
	public static String getChildNodeStr(String sTag, String doc) {
		String childNodeStr = null;
		String beginTag = "<"+sTag+">";
		String endTag = "</"+sTag+">";
		//String nullTag = "<"+sTag+"/>";
		if (doc.contains(beginTag) && doc.contains(endTag)) {
			int beg = doc.indexOf(beginTag) + beginTag.length();
			int end = doc.indexOf(endTag);
			childNodeStr = doc.substring(beg, end);
		}
		return childNodeStr;
	}
}
