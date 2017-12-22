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
package com.tibco.ps.deploytool.ant;

import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.Task;

import com.tibco.ps.common.exception.ApplicationException;
import com.tibco.ps.common.exception.CompositeException;
import com.tibco.ps.common.util.CommonUtils;
import com.tibco.ps.common.util.PropertyManager;
import com.tibco.ps.deploytool.DeployManager;
import com.tibco.ps.deploytool.DeployManagerUtil;

public class CompositeAntTask extends Task {

	private static Log logger = LogFactory.getLog(CompositeAntTask.class);

	private String endExecutionOnTaskFailure;
	
	private String action;

	private String arguments;
	
	private String propertyFile = null;
		
	public CompositeAntTask() {
	}

	public void setEndExecutionOnTaskFailure(String endExecutionOnTaskFailure)throws CompositeException {

		if (!CommonUtils.stringIsValidBoolean(endExecutionOnTaskFailure)) {
			throw new CompositeException("Illegal value for EndExecutionOnTaskFailure property.");
		}
		this.endExecutionOnTaskFailure = endExecutionOnTaskFailure;
	}

	public String getEndExecutionOnTaskFailure() {
		return endExecutionOnTaskFailure;
	}


	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	
	public void execute() {
		
		try {
			logger.info("--------------------------------------------------------");
			logger.info("-------------------- ANT DEPLOYMENT --------------------");
			logger.info("--------------------------------------------------------");       
			/*  Determine the property file name for this environment
			 *    1. Start with default file "deploy.properties"
			 *    2. Get Java Environment variables
			 *    3. Get OS System Environment variables
			 */
	        propertyFile = CommonUtils.getFileOrSystemPropertyValue(null, "CONFIG_PROPERTY_FILE");
			logger.info("");
			logger.info("----------------------------------------------");
			logger.info("CONFIG_PROPERTY_FILE="+propertyFile);
			logger.info("----------------------------------------------");

			if (!PropertyManager.getInstance().doesPropertyFileExist(propertyFile)) {
				throw new ApplicationException("The property file does not exist for CONFIG_PROPERTY_FILE="+propertyFile);
			}
			
			String executeAction = getAction();
			if(logger.isInfoEnabled()){
				logger.info("Calling Action "+ executeAction);
		    }	

		    String[] args = getArgumentsArray();
			// Number of method arguments
			int numInputArgs = args.length - 1;

			// Determine which parameter is the password parameter when invoking methods.  
			// The list and parameter number are contained in DeployManager.methodList.
			int maskParamNum = CommonUtils.getMaskParamNum(args[0], numInputArgs, DeployManager.methodList);

			for (int i = 0; i < args.length; i++) {
				//Skip arg[0] - method name
				if (i > 0) {
					String arg = "";
					if (args[i] != null) {
						arg = args[i].trim();
					}
					if(logger.isDebugEnabled()){
						// This is a special case.  If a method in the DeployManager.methodList is being invoked then it has the potential of 
						//  containing a password.  If it does that password is blanked out on display with "********".
						if (maskParamNum != i) {
							logger.debug("arg["+i+"]="+ arg);
						} else {
							if (arg.length() == 0) {
								logger.debug("arg["+i+"]="+ arg);
							} else {
								logger.debug("arg["+i+"]=********");						
							}					
						}
					}
				}
			}
				
			DeployManagerUtil.main(getArgumentsArray());
		}catch (CompositeException e) {
			if (CommonUtils.isTrue(this.getEndExecutionOnTaskFailure())) {
				logger.error("Abort "+getAction()+" processing due to",e);				
				throw new ApplicationException(e);
			} else {
				logger.error("Error occured while executing "+ getAction(),e);				
			}
		}
		
	}


	/**
	 * @return the arguments
	 */
	public String getArguments() {
		return arguments;
	}

	/**
	 * @param arguments the arguments to set
	 */
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	private String[] getArgumentsArray(){
		if(arguments != null && arguments.trim().length() > 0){
			StringTokenizer st = new StringTokenizer(arguments,getDelimiterString());

			String[] args = new String[st.countTokens()+1];
			args[0] = getAction();
			int i = 1;
			
			while(st.hasMoreTokens()){
				args[i] = st.nextToken().trim();
				i++;
			}
			return args;
		}
		return null;
	}
	
	private String getDelimiterString(){
		String delim = null;
		delim = PropertyManager.getInstance().getProperty(propertyFile,"delimiterString");
		if(delim == null){
			delim = "^";
		}
		return delim;
	}
		
}
