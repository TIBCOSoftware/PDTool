/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
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
			buf.append("input = " + input + "\n");
			return buf.toString();
		}		
}

