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
package com.tibco.ps.common.util;

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
