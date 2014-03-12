/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
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
