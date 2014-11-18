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
package com.cisco.dvbu.ps.deploytool.wsapi;

import java.util.HashMap;

import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CommonUtils;

public class CompositeWsApiCall {

	// -- some common stuff that will be in every task
	//    ** The same list should appear in CompositeAntTask **	
	private String pathToServersXML;
	private String endExecutionOnTaskFailure;
//	private Project antProject;
	private String targetServerName;
	private String sourceServerName;
	private String logFileFolder;
	private String logFileName;
	
	private HashMap<String, String> args;
	
	public boolean validateArgs(HashMap<String, String> args) {
		
		// -- TODO: All tasks must have certain args valued
		return true;
	}
	
	public String getPathToServersXML() {
		return pathToServersXML;
	}
	public void setPathToServersXML(String pathToServersXML) {
		this.pathToServersXML = pathToServersXML;
	}
	
	public void setEndExecutionOnTaskFailure(
			String endExecutionOnTaskFailure) throws CompositeException {
		
		if (! CommonUtils.stringIsValidBoolean(endExecutionOnTaskFailure)) {
			throw new CompositeException(
					"Illegal value for EndExecutionOnTaskFailure property.");
		}

		this.endExecutionOnTaskFailure = endExecutionOnTaskFailure;
	}

	public String getEndExecutionOnTaskFailure() {
		return endExecutionOnTaskFailure;
	}
	public String getTargetServerName() {
		return targetServerName;
	}
	public void setTargetServerName(String targetServerName) {
		this.targetServerName = targetServerName;
	}
	public String getSourceServerName() {
		return sourceServerName;
	}
	public void setSourceServerName(String sourceServerName) {
		this.sourceServerName = sourceServerName;
	}
	public String getLogFileFolder() {
		return logFileFolder;
	}
	public void setLogFileFolder(String logFileFolder) {
		this.logFileFolder = logFileFolder;
	}
	public String getLogFileName() {
		return logFileName;
	}
	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}
	public void setArgs(HashMap<String, String> args) {
		this.args = args;
	}
	public HashMap<String, String> getArgs() {
		return args;
	}


}
