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
package com.tibco.ps.deploytool.util;

import java.util.StringTokenizer;

import com.tibco.ps.common.util.CommonUtils;
import com.tibco.ps.common.util.PropertyUtil;
import com.tibco.ps.common.util.wsapi.CompositeServer;

/**
 * Static Class with bunch of utility methods needed for Modules/Components
 *
 */
public class DeployUtil {
	
	public static enum MessageType {ERROR,INFO,DEBUG};

	/**
	 * Utility Method to determine if we need to process a resource based on passed in resourceNameList and a
	 * resource Name. 
	 *  			 
	 * Possible values for resourceNameList 
	 * 1. " * " or whatever is configured to indicate all resources (we process all resources in this case)
	 *    Must have double quotes and a space surrounding the *.
	 * 2. csv string with '-' or whatever is configured to indicate exclude resources as prefix 
	 * 	  like -resourceName1,resourceName2 (we ignore passed in resources and process rest of the in the input xml
	 * 3. csv string like resourceName1,resourceName2 (we process only resource names which are passed in)
	 * 4. wild card - prefix/postfix any label with a "*" or whatever is configured to indicate all resources.
	 *    *label   - process all labels that end with "label"
	 *    label*   - process all labels that start with "label"
	 *    *label*  - process all labels that contain the string "label"
	 *    -*label  - process all labels that DO NOT end with "label"
	 *    -label*  - process all labels that DO NOT start with "label"
	 *    -*label* - process all labels that DO NOT contain the string "label"
	 * @param resourceNameList comma separated values for resource names
	 * @param resourceName resource name
	 * @return true if resource could be processed else false
	 */
	public static boolean canProcessResource(String resourceNameList,String resourceName) {
			
		// Trim spaces from either side of the list
		resourceNameList = resourceNameList.trim();
		
		// Rule 1: Contains only the "all resources indicator" wildcard character " * "
		if (resourceNameList.equals(PropertyUtil.getAllResourcesIndicatorString())) {
			return true;
		}
		
		// Rule 2: Starts with the "exclusion" character "-" and is not in the passed in exclusion list
		if (resourceNameList.startsWith(PropertyUtil.getExcludeResourcesIndicatorString()) && !doesResourceIdExistInPassedInResourceList(resourceNameList.substring(1), resourceName)) {
			return true;
		}
		
		// Rule 3: Does not start with an "exclusion" character "-" and is in the passed in list
		if (!resourceNameList.startsWith(PropertyUtil.getExcludeResourcesIndicatorString()) && doesResourceIdExistInPassedInResourceList(resourceNameList, resourceName)) {
			return true;
		}			

		return false;
	}
	
	public static boolean doesResourceIdExistInPassedInResourceList(String resourceNameList,String resourceName){
		if(resourceNameList.contains(",")){
			StringTokenizer st = new StringTokenizer(resourceNameList,",");
			while(st.hasMoreTokens()){
				String token = st.nextToken().trim();
				if (wildCardTest(token, resourceName)) {
					return true;
				}
			}
		}else{
			//return resourceNameList.equalsIgnoreCase(resourceName);
			return wildCardTest(resourceNameList, resourceName);
		}
		
		return false;
	}
	
	private static boolean wildCardTest(String token, String resourceName ) {
		if (token.startsWith(PropertyUtil.getAllResourcesIndicatorString()) || token.endsWith(PropertyUtil.getAllResourcesIndicatorString())) {
			boolean wildCardBegin = false;
			boolean wildCardEnd = false;
			
			if (token.startsWith(PropertyUtil.getAllResourcesIndicatorString())) {
				wildCardBegin = true;
				token = token.substring(1); // remove the wildcard at the end
			}
			if (token.endsWith(PropertyUtil.getAllResourcesIndicatorString())) {
				wildCardEnd = true;
				token = token.substring(0,token.length()-1); // remove the wildcard at the beginning
			}
			// Since the wild card is at the beginning, match on any text at the end of the string 
			if (wildCardBegin && !wildCardEnd) {
				if (resourceName.endsWith(token)) {
					return true;
				}
			// Since the wild card is at the end, match on any text at the start of the string 
			} else if (!wildCardBegin && wildCardEnd) {
				if (resourceName.startsWith(token)) {
					return true;
				}	
			// Since the wild card is at the beginning and end, match on any text contained in the string 
			} else { // wildcard in beginning and end
				if (resourceName.contains(token)) {
					return true;
				}						
			}
		} else {				
			if(token.equalsIgnoreCase(resourceName)){
				return true;
			}
		}
		return false;
	}
	
	
	public static String constructMessage(String messageType, String action,String resourceType, String resourcePath, CompositeServer server){
		StringBuffer errorMessage = new StringBuffer();
		
		if(messageType.equals(MessageType.ERROR.name())){
			errorMessage.append("Error occured while invoking action ");
			
		}else if(messageType.equals(MessageType.INFO.name())){
			errorMessage.append("Invoking action ");

		}else if(messageType.equals(MessageType.DEBUG.name())){
			errorMessage.append("Invoking action ");
		}

		if(action != null && action.trim().length() > 0){

			errorMessage.append("\""+action+"\"");
		}
		
		if(resourceType != null && resourceType.trim().length() > 0){

			errorMessage.append(" on ");
			errorMessage.append(resourceType);
		}		

		if(resourcePath != null && resourcePath.trim().length() > 0){
	
			errorMessage.append(" with path ");
			errorMessage.append("\""+resourcePath+"\"");
		}		

		if(server != null && server.getHostname() != null && server.getHostname().trim().length() > 0){
			
			errorMessage.append(" on ");
			errorMessage.append("server ");
			errorMessage.append(server.getHostname());
			errorMessage.append(" on ");
			errorMessage.append("port ");
			errorMessage.append(server.getPort());
			
		}

		if(server != null && server.getClustername() != null && server.getClustername().trim().length() > 0){
			
			errorMessage.append(" in ");
			errorMessage.append("cluster ");
			errorMessage.append(server.getClustername());
		}

		if(server != null && server.getSite() != null && server.getSite().trim().length() > 0){

			errorMessage.append(" at ");
			errorMessage.append("site ");
			errorMessage.append(server.getSite());
		}

		return errorMessage.toString();
	}

}
