/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
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
package com.cisco.dvbu.ps.deploytool.services;

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

