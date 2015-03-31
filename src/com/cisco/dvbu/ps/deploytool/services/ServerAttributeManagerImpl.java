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

/**
 * Initial Version:	Mike Tinius :: 6/8/2011		
 * Modifications:	initials :: Date
 * Check attribute update rule : Mkazia   :: 8/3/2011
 * Extract variables from value objects : mtinius :: 7/3/2012
 * Changed strategy to use pure soap call instead of CisAdminApi.port JAXB call : mtinius :: 3/13/15
 */

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;

import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.exception.ValidationException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.PropertyManager;
import com.cisco.dvbu.ps.common.util.XMLUtils;
import com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.ServerAttributeWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.cisco.dvbu.ps.deploytool.modules.AttributeTypeSimpleType;
import com.cisco.dvbu.ps.deploytool.modules.ObjectFactory;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeDefType;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeModule;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeType;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeValueArray;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeValueList;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeValueListItemType;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeValueMap;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeValueMapEntryKeyType;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeValueMapEntryType;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeValueMapEntryValueType;
import com.compositesw.services.system.util.common.Attribute;
import com.compositesw.services.system.util.common.AttributeList;
import com.compositesw.services.system.util.common.AttributeSimpleValueList;
import com.compositesw.services.system.util.common.AttributeType;
import com.compositesw.services.system.util.common.AttributeTypeValue;
import com.compositesw.services.system.util.common.AttributeTypeValueList;
import com.compositesw.services.system.util.common.AttributeTypeValueMap;
import com.compositesw.services.system.util.common.AttributeDef;
import com.compositesw.services.system.util.common.AttributeDefList;
import com.compositesw.services.system.util.common.AttributeUpdateRule;
import com.compositesw.services.system.util.common.AttributeTypeValueMap.Entry;

public class ServerAttributeManagerImpl implements ServerAttributeManager{

	private ServerAttributeDAO serverAttributeDAO = null;
	private ArrayList<String> tokenType = new ArrayList<String>();
	private ArrayList<Integer> tokenNum = new ArrayList<Integer>();
	
	private static Log logger = LogFactory.getLog(ServerAttributeManagerImpl.class);

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ServerAttributeManager#updateServerAttribute(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void updateServerAttributes(String serverId, String serverAttributeIds, String pathToServerAttributeXML, String pathToServersXML) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug(" Entering ServerAttributeManagerImpl.updateServerAttributes() with following params "+" serverId: "+serverId+", serverAttributeIds: "+serverAttributeIds+", pathToServerAttributeXML: "+pathToServerAttributeXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
			serverAttributeAction(ServerAttributeDAO.action.UPDATE.name(), serverId, serverAttributeIds, pathToServerAttributeXML, pathToServersXML);
		} catch (CompositeException e) {
			logger.error("Error while updating server attribute: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
	}

	private void serverAttributeAction(String actionName, String serverId, String serverAttributeIds, String pathToServerAttributeXML, String pathToServersXML) throws CompositeException {

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServerAttributeXML)) {
			throw new CompositeException("File ["+pathToServerAttributeXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// Get the configuration property file set in the environment with a default of deploy.properties
		String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");

		String prefix = "ServerAttributeManagerImpl.serverAttributeAction";
		
		// Extract variables for the serverAttributeIds
		serverAttributeIds = CommonUtils.extractVariable(prefix, serverAttributeIds, propertyFile, true);

		try {
			List<ServerAttributeType> serverAttributeList = getServerAttributes(serverId, serverAttributeIds, pathToServerAttributeXML, pathToServersXML);
			if (serverAttributeList != null && serverAttributeList.size() > 0) {
	
				// Loop over the list of server attributes and apply their attributes to
				// the target CIS instance.
				String serverAttributePath = null;
				ServerAttributeModule updateServerAttributeObj = new ServerAttributeModule();
							
				// Get all server attribute def
				/***************************************
				 * 
				 * ServerAttributeWSDAOImpl Invocation
				 * 
				 ***************************************/

				for (ServerAttributeType serverAttribute : serverAttributeList) {
	
					// Get the identifier and convert any $VARIABLES
					String identifier = CommonUtils.extractVariable(prefix, serverAttribute.getId(), propertyFile, true);
					
					serverAttributePath = serverAttribute.getName();
					/**
					 * Possible values for server attributes 
					 * 1. csv string like sa1,sa2 (we process only resource names which are passed in)
					 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
					 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
					 * 	  like -sa1,sa2 (we ignore passed in resources and process rest of the in the input xml
					 */
					if(DeployUtil.canProcessResource(serverAttributeIds, identifier)) {

						ServerAttributeType updAttribute = new ServerAttributeType();
						
						/***************************************
						 * 
						 * ServerAttributeWSDAOImpl Invocation
						 * 
						 ***************************************/
						// Get the server attribute definition for the current server attribute name being processed
						String name = serverAttribute.getName();
					
						ServerAttributeModule attributeDefModule = getServerAttributeDAO().getServerAttributeDefinition(serverId, name, pathToServersXML);
						ServerAttributeDefType attributeDef = attributeDefModule.getServerAttributeDef().get(0);
						
						// Validate that the server attribute updateRule allows for READ_WRITE
						if ((attributeDef != null
								&& (attributeDef.getUpdateRule().toString().equalsIgnoreCase("READ_WRITE")))
								&&	!PropertyManager.getInstance().containsPropertyValue(propertyFile, "ServerAttributeModule_NonUpdateableAttributes", serverAttribute.getName())) {

							if(logger.isInfoEnabled()){
								logger.info("processing action "+actionName+" on server attribute "+serverAttributePath);
							}

							// Get the id
							updAttribute.setId(serverAttribute.getId());

							// Get the name
							updAttribute.setName(serverAttribute.getName());
							
							// Get the type
							if (serverAttribute.getType() != null) {
								updAttribute.setType(AttributeTypeSimpleType.valueOf(serverAttribute.getType().toString()));
							}
							
							// Use this flag to add the attribute structure to the request only if there is a value
							Boolean valueFound = false;
							
							// Set the Value object if it exsts
							if (serverAttribute.getValue() != null) {
								// mtinius: 2012-07-03 - Parse the value for variables and resolve
								String value = CommonUtils.extractVariable(prefix, serverAttribute.getValue(), propertyFile, false);
								updAttribute.setValue(value);
								valueFound = true;
							}
							
							// Set the Value Array if it exists
							if (serverAttribute.getValueArray() != null) {
								// Instantiate a new CIS WS Schema Element (AttributeSimpleValueList)
								ServerAttributeValueArray updArray = new ServerAttributeValueArray();
								ServerAttributeValueArray serverAttrValueArray = serverAttribute.getValueArray();
								for (String item : serverAttrValueArray.getItem()) {
									// mtinius: 2012-07-03 - Parse the item value for variables and resolve
									String value = CommonUtils.extractVariable(prefix, item, propertyFile, false);
									updArray.getItem().add(value);
								}
								updAttribute.setValueArray(updArray);
								valueFound = true;
							}

							// Set the Value List if it exists
							if (serverAttribute.getValueList() != null) {
								// Instantiate a new CIS WS Schema Element (AttributeTypeValueList)
								ServerAttributeValueList updValueList = new ServerAttributeValueList();
								ServerAttributeValueList serverAttrValueList = serverAttribute.getValueList();
								for (ServerAttributeValueListItemType item : serverAttrValueList.getItem()) {
									ServerAttributeValueListItemType updAttributeValueType = new ServerAttributeValueListItemType();
									
									updAttributeValueType.setType(AttributeTypeSimpleType.valueOf(item.getType().toString()));

									// mtinius: 2012-07-03 - Parse the value for variables and resolve
									String value = CommonUtils.extractVariable(prefix, item.getValue(), propertyFile, false);
									updAttributeValueType.setValue(value);
									updValueList.getItem().add(updAttributeValueType);
								}
								updAttribute.setValueList(updValueList);
								valueFound = true;
							}

							// Set the Value Map if it exists
							if (serverAttribute.getValueMap() != null) {
								// Instantiate a new CIS WS Schema Element (AttributeTypeValueMap)
								ServerAttributeValueMap updValueMap = new ServerAttributeValueMap();

								ServerAttributeValueMap serverAttrValueMap = serverAttribute.getValueMap();		
								for (ServerAttributeValueMapEntryType item : serverAttrValueMap.getEntry()) {
									ServerAttributeValueMapEntryType updEntry = new ServerAttributeValueMapEntryType();

									// Set the value map entry key node
									ServerAttributeValueMapEntryKeyType updKey = new ServerAttributeValueMapEntryKeyType();
									updKey.setType(AttributeTypeSimpleType.valueOf(item.getKey().getType().toString()));
									updKey.setValue(item.getKey().getValue());
									updEntry.setKey(updKey);

									// Set the value map entry value node
									ServerAttributeValueMapEntryValueType updValue = new ServerAttributeValueMapEntryValueType();
									updValue.setType(AttributeTypeSimpleType.valueOf(item.getValue().getType().toString()));
									// mtinius: 2012-07-03 - Parse the value for variables and resolve
									String value2 = CommonUtils.extractVariable(prefix, item.getValue().getValue(), propertyFile, false);
									updValue.setValue(value2);
									updEntry.setValue(updValue);

									updValueMap.getEntry().add(updEntry);
								}
								updAttribute.setValueMap(updValueMap);
								valueFound = true;
							}	
							
							if (valueFound){
								updateServerAttributeObj.getServerAttribute().add(updAttribute);								
							} else {
								if(logger.isInfoEnabled()){
									logger.info("skipping action "+actionName+" on server attribute "+serverAttributePath + " Reason: value not found.");
								}
							}
							
						} else {
							if(logger.isInfoEnabled()){
								logger.info("skipping action "+actionName+" on server attribute "+serverAttributePath + " Reason: attribute is READ_ONLY or was found in Server Attribute Non-Updateable list.");
							}
						}
					}
				}
				/***************************************
				 * 
				 * ServerAttributeWSDAOImpl Invocation
				 * 
				 ***************************************/
				getServerAttributeDAO().takeServerAttributeAction(actionName, updateServerAttributeObj, serverId, pathToServersXML);
			}
		} catch (CompositeException e) {
			logger.error("Error on server attribute action ("+actionName+"): " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
	}

	private List<ServerAttributeType> getServerAttributes(String serverId, String serverAttributeIds, String pathToServerAttributeXML, String pathToServersXML)  {
		// validate incoming arguments
		if(serverId == null || serverId.trim().length() ==0 || serverAttributeIds == null || serverAttributeIds.trim().length() ==0 || pathToServersXML == null || pathToServersXML.trim().length() ==0 || pathToServerAttributeXML == null || pathToServerAttributeXML.trim().length() ==0){
			throw new ValidationException("Invalid Arguments");
		}

		try {
			//using jaxb convert xml to corresponding java objects
			ServerAttributeModule serverAttributeModuleType = (ServerAttributeModule)XMLUtils.getModuleTypeFromXML(pathToServerAttributeXML);
			if(serverAttributeModuleType != null && serverAttributeModuleType.getServerAttribute() != null && !serverAttributeModuleType.getServerAttribute().isEmpty()){
				return serverAttributeModuleType.getServerAttribute();
			}
		} catch (CompositeException e) {
			logger.error("Error while parsing server attribute xml" , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ServerAttributeManager#generateServerAttributesXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateServerAttributesXML(String serverId, String startPath, String pathToServerAttributeXML, String pathToServersXML, String pUpdateRule) throws CompositeException {
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}
		
		String updateRule = null;
		// Default to READ_WRITE if the passed Update rule is empty
		if (pUpdateRule == null || pUpdateRule.trim().isEmpty() || pUpdateRule.trim().equalsIgnoreCase("")) {
			updateRule = AttributeUpdateRule.READ_WRITE.value();
		} 
		else {
			updateRule = pUpdateRule;
		}
		// Trim the update rule
		if (updateRule != null)
			updateRule = updateRule.trim();

		// Make sure the startPath has a valid format
		if (startPath == null || startPath.trim().length() == 0)
			throw new CompositeException("The parameter \"startPath\" may not be null or blank.");
		startPath = startPath.trim();
		if (!startPath.equals("/")) {
			int lastCharPos = startPath.lastIndexOf("/");
			int lastPos = startPath.length()-1;
			if (lastCharPos == lastPos)
				startPath = startPath.substring(0, startPath.length() - 1);
			if (!startPath.substring(0, 1).equalsIgnoreCase("/"))
				startPath = "/" + startPath;
		}
		
		/***************************************
		 * 
		 * ServerAttributeWSDAOImpl Invocation
		 * 
		 ***************************************/
		ServerAttributeModule serverAttrModule = getServerAttributeDAO().getServerAttributesFromPath(serverId, startPath, pathToServersXML, updateRule);

		if (serverAttrModule.getServerAttribute() != null) {
			List<ServerAttributeType> serverAttributeList = serverAttrModule.getServerAttribute();
			for (int i=0; i < serverAttributeList.size(); i++) 
			{
				// Generate and ID using the first token in the server attribute definition path name
				String id = CommonUtils.getToken(1, serverAttributeList.get(i).getName());
				// Increment a number and concatenate to the token
				serverAttributeList.get(i).setId(getTokenId(id));
			}
			
			// Generate the XML file
			XMLUtils.createXMLFromModuleType(serverAttrModule, pathToServerAttributeXML);			
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ServerAttributeManager#generateServerAttributeDefinitionsXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateServerAttributeDefinitionsXML(String serverId, String startPath, String pathToServerAttributeXML, String pathToServersXML, String pUpdateRule) throws CompositeException {
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}
		
		String updateRule = null;
		// Default to READ_WRITE if the passed Update rule is empty
		if (pUpdateRule == null || pUpdateRule.isEmpty() || pUpdateRule.equalsIgnoreCase("")) {
			updateRule = AttributeUpdateRule.READ_WRITE.value();
		} 
		else {
			updateRule = pUpdateRule;
		}
		// Trim the update rule
		if (updateRule != null)
			updateRule = updateRule.trim();

		// Make sure the startPath has a valid format
		if (startPath == null || startPath.trim().length() == 0)
			throw new CompositeException("The parameter \"startPath\" may not be null or blank.");
		startPath = startPath.trim();
		if (!startPath.equals("/")) {
			int lastCharPos = startPath.lastIndexOf("/");
			int lastPos = startPath.length()-1;
			if (lastCharPos == lastPos)
				startPath = startPath.substring(0, startPath.length() - 1);
			if (!startPath.substring(0, 1).equalsIgnoreCase("/"))
				startPath = "/" + startPath;
		}

		// Retrieve the list of Server Attributes by invoking the CIS Web Service API
		/***************************************
		 * 
		 * ServerAttributeWSDAOImpl Invocation
		 * 
		 ***************************************/
		ServerAttributeModule serverAttrModule = getServerAttributeDAO().getServerAttributeDefsFromPath(serverId, startPath, pathToServersXML, updateRule);

		if (serverAttrModule.getServerAttributeDef() != null) 
		{
			List<ServerAttributeDefType> serverAttributeDefList = serverAttrModule.getServerAttributeDef();
			for (int i=0; i < serverAttributeDefList.size(); i++) 
			{
				// Generate and ID using the first token in the server attribute definition path name
				String id = CommonUtils.getToken(1, serverAttributeDefList.get(i).getName());
				// Increment a number and concatenate to the token
				serverAttributeDefList.get(i).setId(getTokenId(id));
			}
			// Generate the XML file
			XMLUtils.createXMLFromModuleType(serverAttrModule, pathToServerAttributeXML);			
		}
	}

	public String getServerVersion(String serverId, String pathToServersXML)
			throws CompositeException {
		return getServerAttributeDAO().getServerVersion(serverId, pathToServersXML);
	}

	/**
	 * @return the token and numerical Id 
	 * Using an ArrayList, track the various tokens that are passed in and increment a number.
	 * Return the token and the number concatenated together
	 * cms1
	 * discovery1
	 * monitor1
	 * server1
	 * source1
	 * studio1
	 */
	private String getTokenId(String name) {
		for (int i=0; i < tokenType.size(); i++) {
			if (tokenType.get(i).equalsIgnoreCase(name)) {
				int n = tokenNum.get(i).intValue();
				tokenNum.set(i, Integer.valueOf(++n));
				return name+n;
			}
		}
		tokenType.add(name);
		tokenNum.add(Integer.valueOf(1));
	    return name+1;
	}

	/**
	 * @return the serverAttributeDAO
	 */
	public ServerAttributeDAO getServerAttributeDAO() {
		if(this.serverAttributeDAO == null){
			this.serverAttributeDAO = new ServerAttributeWSDAOImpl();
		}
		return serverAttributeDAO;
	}

	/**
	 * @param serverAttributeDAO the serverAttributeDAO to set
	 */
	public void setServerAttributeDAO(ServerAttributeDAO serverAttributeDAO) {
		this.serverAttributeDAO = serverAttributeDAO;
	}
}