/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.common.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XMLXPath {

	public static String xpath(String xpathInput, String tempFile) {
		//throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
	
		String resultValue = null;
	    try {

			//loading the XML document from a file
			DocumentBuilderFactory builderfactory = DocumentBuilderFactory.newInstance();
			builderfactory.setNamespaceAware(true);
			DocumentBuilder builder = builderfactory.newDocumentBuilder();
				
	    	File file = new File(tempFile);
	    	Document doc = (Document) builder.parse(file);

		    XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    xpath.setNamespaceContext(new BindingModelNamespaceContext());
		    XPathExpression expr = xpath.compile(xpathInput);

		    Object result = expr.evaluate(doc, XPathConstants.NODESET);
		    NodeList nodes = (NodeList) result;
		    for (int i = 0; i < nodes.getLength(); i++) {
		    	resultValue = nodes.item(i).getNodeValue();
//		        System.out.println(resultValue); 
		    }

		    return resultValue;
	    } catch (Exception e) {
	    	System.out.println(e.toString());
	    }

	    return resultValue;
	  }
}
