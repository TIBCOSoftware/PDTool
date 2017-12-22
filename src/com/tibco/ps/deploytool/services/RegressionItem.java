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
package com.tibco.ps.deploytool.services;

import java.util.Arrays;

/**
 *  An 'Item' from the pubtest input file
 * 
 * @author sst
 *
 */
public class RegressionItem
{
		public int type;
		public int lineNum;
		public String input;
		public String outputFilename;
		public String path;
		public String action;
		public boolean encrypt;
	    public String contentType;
		public String database;
		public String[] outTypes = new String[0];
		public String resourcePath;
		public String resourceType;

		public String toString()
		{
			StringBuffer buf = new StringBuffer();
			buf.append("Line number = " + lineNum + "\n");
			buf.append("database = " + database + "\n");
			buf.append("path = " + path + "\n");
			buf.append("action = " + action + "\n");
			buf.append("encrypt = " + encrypt + "\n");
	        buf.append("contentType = "+contentType+"\n");
			buf.append("outTypes = " + Arrays.asList(outTypes) + "\n");
	        buf.append("resourcePath = "+resourcePath+"\n");
	        buf.append("resourceType = "+resourceType+"\n");
	        buf.append("outputFilename = "+outputFilename+"\n");
			buf.append("input = " + input + "\n");
			return buf.toString();
		}		
}

