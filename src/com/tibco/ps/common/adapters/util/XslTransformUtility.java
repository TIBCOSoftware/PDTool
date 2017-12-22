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

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import com.tibco.ps.common.util.XMLUtils;

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
	        System.err.println("usage: java com.tibco.ps.common.adapters.util.XslTransformUtility source stylesheet");
	        System.exit(1);
        }
	    executeXslTransformUtility(args[0], args[1]);
	}
	
}
