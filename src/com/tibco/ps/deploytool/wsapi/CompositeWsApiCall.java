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
package com.tibco.ps.deploytool.wsapi;

import java.util.HashMap;

import com.tibco.ps.common.exception.CompositeException;
import com.tibco.ps.common.util.CommonUtils;

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
