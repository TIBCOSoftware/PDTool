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

package com.cisco.dvbu.ps.common.adapters.util;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import com.cisco.dvbu.ps.common.util.XMLUtils;

/**
* @author vmadired, March 2015
*/

public class XslTransformUtility {

	private static Log logger = LogFactory.getLog(XslTransformUtility.class);

	/**
	 * Execute the transformation specified on the incoming XML file source specified by the XSL style sheet file source.
	 * @param XMLFileSource file path to XML document
	 * @param XSLFileSource file path to XSL style sheet
	 */
	public static void executeXslTransformUtility(String XMLFileSource, String XSLFileSource) {

		if (logger.isInfoEnabled()) {
			logger.info("ScriptUtil.XslTransformUtility:: XMLFileSource="+XMLFileSource);
			logger.info("ScriptUtil.XslTransformUtility:: XSLFileSource="+XSLFileSource);
			//System.out.println("ScriptUtil.XslTransformUtility:: XMLFileSource="+XMLFileSource);
			//System.out.println("ScriptUtil.XslTransformUtility:: XSLFileSource="+XSLFileSource);
		}

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc;
		try {
			// Get the XML Document
			FileInputStream in = new FileInputStream(XMLFileSource);
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(in);
			
			// Get the XSL stylesheet
			StreamSource ss = new StreamSource(new File(XSLFileSource));
			
			// Transform the document
		    Document newdoc = XmlUtils.xslTransform(doc, ss);
		    
		    String output = XmlUtils.nodeToString(newdoc);
		    
			if (logger.isInfoEnabled()) {
				logger.info("ScriptUtil.XslTransformUtility:: output:\n"+XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(output)));
				//System.out.println("ScriptUtil.XslTransformUtility:: output:\n"+output);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			//System.err.println("" + e.getMessage());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    if (args.length != 2) {
	        System.err.println("usage: java com.cisco.dvbu.ps.common.adapters.util.XslTransformUtility source stylesheet");
	        System.exit(1);
        }
	    executeXslTransformUtility(args[0], args[1]);
	}
	
}
