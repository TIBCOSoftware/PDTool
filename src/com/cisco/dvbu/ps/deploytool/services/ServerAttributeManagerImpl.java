/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.services;

/**
 * Initial Version:	Mike Tinius :: 6/8/2011		
 * Modifications:	initials :: Date
 * Check attribute update rule : Mkazia   :: 8/3/2011
 * Extract variables from value objects : mtinius :: 7/3/2012
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
				AttributeList attributeList = new AttributeList();
				
				// Get all server attribute def
				AttributeDefList attributeDefList = getServerAttributeDAO().getServerAttributeDefsFromPath(serverId, "/", pathToServersXML);
				List<AttributeDef> attributeDefs = null;
				
				// Continue if there is a list
				if(attributeDefList != null && attributeDefList.getAttributeDef() != null && !attributeDefList.getAttributeDef().isEmpty()){
					// Assign the list of Server Attribute Definitions to a local AttributeDef type variable
					attributeDefs = attributeDefList.getAttributeDef();
				}

				
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
						Attribute attribute = new Attribute();

						AttributeDef attributeDef = getAttributeDef(attributeDefs, serverAttribute.getName());
						if ((attributeDef != null
								&& (attributeDef.getUpdateRule() == AttributeUpdateRule.READ_WRITE))
								&&	!PropertyManager.getInstance().containsPropertyValue(propertyFile, "ServerAttributeModule_NonUpdateableAttributes", serverAttribute.getName())) {
							
							if(logger.isInfoEnabled()){
								logger.info("processing action "+actionName+" on server attribute "+serverAttributePath);
							}

							attribute.setName(serverAttribute.getName());
							if (serverAttribute.getType() != null) {
								attribute.setType(AttributeType.valueOf(serverAttribute.getType().toString()));
							}
							
							// Use this flag to add the attribute structure to the request only if there is a value
							Boolean valueFound = false;
							
							// Set the Value object if it exsts
							if (serverAttribute.getValue() != null) {
								// mtinius: 2012-07-03 - Parse the value for variables and resolve
								String value = CommonUtils.extractVariable(prefix, serverAttribute.getValue(), propertyFile, false);
								attribute.setValue(value);
								valueFound = true;
							}
							
							// Set the Value Array if it exists
							if (serverAttribute.getValueArray() != null) {
								// Instantiate a new CIS WS Schema Element (AttributeSimpleValueList)
								AttributeSimpleValueList attributeSimpleValueList = new AttributeSimpleValueList();
								ServerAttributeValueArray serverAttrValueArray = serverAttribute.getValueArray();
								for (String item : serverAttrValueArray.getItem()) {
									// mtinius: 2012-07-03 - Parse the item value for variables and resolve
									String value = CommonUtils.extractVariable(prefix, item, propertyFile, false);
									attributeSimpleValueList.getItem().add(value);
								}
								attribute.setValueArray(attributeSimpleValueList);
								valueFound = true;
						}

							// Set the Value List if it exists
							if (serverAttribute.getValueList() != null) {
								// Instantiate a new CIS WS Schema Element (AttributeTypeValueList)
								AttributeTypeValueList valueList = new AttributeTypeValueList();
								ServerAttributeValueList serverAttrValueList = serverAttribute.getValueList();
								for (ServerAttributeValueListItemType item : serverAttrValueList.getItem()) {
									AttributeTypeValue attributeValueType = new AttributeTypeValue();
									
									attributeValueType.setType(AttributeType.valueOf(item.getType().toString()));

									// mtinius: 2012-07-03 - Parse the value for variables and resolve
									String value = CommonUtils.extractVariable(prefix, item.getValue(), propertyFile, false);
									attributeValueType.setValue(value);
									valueList.getItem().add(attributeValueType);
								}
								attribute.setValueList(valueList);
								valueFound = true;
							}

							// Set the Value Map if it exists
							if (serverAttribute.getValueMap() != null) {
								// Instantiate a new CIS WS Schema Element (AttributeTypeValueMap)
								AttributeTypeValueMap valueMap = new AttributeTypeValueMap();

								ServerAttributeValueMap serverAttrValueMap = serverAttribute.getValueMap();		
								for (ServerAttributeValueMapEntryType item : serverAttrValueMap.getEntry()) {
									Entry entry = new Entry();

									// Set the value map entry key node
									AttributeTypeValue key = new AttributeTypeValue();
									key.setType(AttributeType.valueOf(item.getKey().getType().toString()));
									key.setValue(item.getKey().getValue());
									entry.setKey(key);

									// Set the value map entry value node
									AttributeTypeValue value = new AttributeTypeValue();
									value.setType(AttributeType.valueOf(item.getValue().getType().toString()));
									// mtinius: 2012-07-03 - Parse the value for variables and resolve
									String value2 = CommonUtils.extractVariable(prefix, item.getValue().getValue(), propertyFile, false);
									value.setValue(value2);
									entry.setValue(value);

									valueMap.getEntry().add(entry);
								}
								attribute.setValueMap(valueMap);
								valueFound = true;
							}	
							
							if (valueFound){
								attributeList.getAttribute().add(attribute);								
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
				getServerAttributeDAO().takeServerAttributeAction(actionName, attributeList, serverId, pathToServersXML);
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

		// Retrieve the list of Server Attributes by invoking the CIS Web Service API
		AttributeList attributeList = getServerAttributeDAO().getServerAttributesFromPath(serverId, startPath, pathToServersXML);

		// Continue if there is a list
		if(attributeList != null && attributeList.getAttribute() != null && !attributeList.getAttribute().isEmpty()){
			// Assign the list of Server Attributes to a local Attribute type variable
			List<Attribute> attributes = attributeList.getAttribute();
			
			// Prepare a local ServerAttributeModule XML variable for creating a list of "ServerAttribute" nodes
			// This XML variable will be written out to the specified file. 
			ServerAttributeModule serverAttrModule = new ObjectFactory().createServerAttributeModule();

			// For efficiency, read the entire Server Attribute Definition List once (web service API call to CIS) which will be used to 
			// determine whether the attribute updateRule is READ_WRITE or READ_ONLY.
			// The selection of server attributes to write out to the XML will depend on the passed in update rule compared to the definition
			// If the passed in updateRule is null, then only READ_WRITE attributes are written to the ServerAttributeModule XML file.
			AttributeDefList attributeDefList = getServerAttributeDAO().getServerAttributeDefsFromPath(serverId, startPath, pathToServersXML);
			
			// Continue if there is a list
			if(attributeDefList != null && attributeDefList.getAttributeDef() != null && !attributeDefList.getAttributeDef().isEmpty()){
				// Assign the list of Server Attribute Definitions to a local AttributeDef type variable
				List<AttributeDef> attributeDefs = attributeDefList.getAttributeDef();			

				// Iterate over the retrieved Server Attribute List
				for (Attribute attribute : attributes) {

					// Compare the passed in update rule with the Server Attribute Definition
					String updateRuleFromServerList = null;
					for (AttributeDef attributeDef : attributeDefs) {
						if (attribute.getName().equalsIgnoreCase(attributeDef.getName())) {
							
							String updRule = "";
							// DEBUG
							if (attributeDef.getUpdateRule() == null) {
								logger.debug("Update Rule is null for attributeDef.getUpdateRule()");
								updRule = "UNKNOWN";
							} else {
								updRule = attributeDef.getUpdateRule().toString();
								updateRuleFromServerList = attributeDef.getUpdateRule().toString();
							}
							logger.debug(CommonUtils.rpad(updRule, 20, " ") + attributeDef.getName() );
						}
					}
					boolean getAttribute = false;
					// If the passed in updateRule is null, empty, blank or contains an asterisk then assume then get all attribute in the Server Attribute List for writing to the XML file
					if (updateRule == null) {
						getAttribute = true;
					} else {
						updateRule = updateRule.trim();
						if (updateRule.trim().contains("*") || updateRule.length() == 0) {
							getAttribute = true;
						}
						// If a Server Attribute Definition updateRule was found and it matches what was passed in then get this attributeDef for writing to the XML file
						if (updateRuleFromServerList != null && updateRule.contains(updateRuleFromServerList)) {
							getAttribute = true;
						}
					}

					// Get the Server Attribute and compose a ServerAttribute type node to write to the XML file.
					if (getAttribute) {
						// Instantiate a new CisDeployToolModules Schema Element (ServerAttributeType) and populate it
						ServerAttributeType serverAttrType = new ServerAttributeType();
						// Generate and ID using the first token in the server attribute definition path name
						String id = getToken(1, attribute.getName());
						if (id == null) {
							// Just use the original name for the id
							serverAttrType.setId(attribute.getName());
						} else {
							// Increment a number and concatenate to the token
							serverAttrType.setId(getTokenId(id));
						}
						serverAttrType.setName(attribute.getName());
						// Set the Attribute Type by converting from the attribute type being returned
						serverAttrType.setType(AttributeTypeSimpleType.valueOf(attribute.getType().toString()));
						if (attribute.getValue() != null) {
							serverAttrType.setValue(attribute.getValue());
						}
						
						/* Process Value Array
							<server:attributes>
								<common:attribute xmlns:common="http://www.compositesw.com/services/system/util/common">
									<common:name>/server/api/protocol/supportedProtocolVersions</common:name>
									<common:type>STRING_ARRAY</common:type>
									<common:valueArray>
										<common:item>5.0</common:item>
										<common:item>4.0</common:item>
										<common:item>3.1</common:item>
										<common:item>3.0</common:item>
										<common:item>2.0</common:item>
										<common:item>1.2</common:item>
										<common:item>1.1</common:item>
										<common:item>1.0</common:item>
									</common:valueArray>
								</common:attribute>
							</server:attributes>
						 */
						if (attribute.getValueArray() != null) {
							// Instantiate a new CisDeployToolModules Schema Element (ServerAttributeValueArray)
							ServerAttributeValueArray serverAttrValueArray = new ServerAttributeValueArray();
						
							// Add another item to the ValueArray node
							AttributeSimpleValueList valueArray = attribute.getValueArray();
							for (String item : valueArray.getItem()) {
								serverAttrValueArray.getItem().add(item);
							}
							// Set the ValueArray XML Node
							serverAttrType.setValueArray(serverAttrValueArray);
						}
		
						/* Process Value List
						  	<server:attributes>
								<common:attribute xmlns:common="http://www.compositesw.com/services/system/util/common">
									<common:name>/studio/data/examplelist</common:name>
									<common:type>LIST</common:type>
									<common:valueList>
										<common:item>
											<common:type>STRING</common:type>
											<common:value>a</common:value>
										</common:item>
										<common:item>
											<common:type>STRING</common:type>
											<common:value>b</common:value>
										</common:item>
									</common:valueList>
								</common:attribute>
							</server:attributes>					
						 */
						if (attribute.getValueList() != null) {
							// Instantiate a new CisDeployToolModules Schema Element (ServerAttributeValueList)
							ServerAttributeValueList serverAttrValueList = new ServerAttributeValueList();
							
							// Add another item to the ValueList
							AttributeTypeValueList valueList = attribute.getValueList();
							for (AttributeTypeValue item : valueList.getItem()) {
								ServerAttributeValueListItemType serverAttrValueListItem = new ServerAttributeValueListItemType();
								serverAttrValueListItem.setType(AttributeTypeSimpleType.valueOf(item.getType().toString()));
								serverAttrValueListItem.setValue(item.getValue());
								serverAttrValueList.getItem().add(serverAttrValueListItem);
							}
							// Set the ValueList XML Node
							serverAttrType.setValueList(serverAttrValueList);
						}
		
						/* Process Value Map
							<server:attributes>
								<common:attribute xmlns:common="http://www.compositesw.com/services/system/util/common">
									<common:name>/studio/data/examplemap</common:name>
									<common:type>MAP</common:type>
									<common:valueMap>
										<common:entry>
											<common:key>
												<common:type>STRING</common:type>
												<common:value>k1</common:value>
											</common:key>
											<common:value>
												<common:type>STRING</common:type>
												<common:value>v1</common:value>
											</common:value>
										</common:entry>
										<common:entry>
											<common:key>
												<common:type>STRING</common:type>
												<common:value>k2</common:value>
											</common:key>
											<common:value>
												<common:type>STRING</common:type>
												<common:value>v2</common:value>
											</common:value>
										</common:entry>
									</common:valueMap>
								</common:attribute>
							</server:attributes>
						*/
						if (attribute.getValueMap() != null) {
							// Instantiate a new CisDeployToolModules Schema Element (ServerAttributeValueMap)
							ServerAttributeValueMap serverAttrValueMap = new ServerAttributeValueMap();
							
							// Add another item to the ValueMap
							AttributeTypeValueMap valueMap = attribute.getValueMap();
							for (Entry item : valueMap.getEntry()) {
								ServerAttributeValueMapEntryType serverAttrValueMapEntry = new ServerAttributeValueMapEntryType();
								
								// Set the value map entry key node
								ServerAttributeValueMapEntryKeyType serverAttrValueMapEntryKey = new ServerAttributeValueMapEntryKeyType();
								serverAttrValueMapEntryKey.setType(AttributeTypeSimpleType.valueOf(item.getKey().getType().toString()));
								serverAttrValueMapEntryKey.setValue(item.getKey().getValue());				
								serverAttrValueMapEntry.setKey(serverAttrValueMapEntryKey);
								
								// Set the value map entry value node
								ServerAttributeValueMapEntryValueType serverAttrValueMapEntryValue = new ServerAttributeValueMapEntryValueType();
								serverAttrValueMapEntryValue.setType(AttributeTypeSimpleType.valueOf(item.getValue().getType().toString()));
								serverAttrValueMapEntryValue.setValue(item.getValue().getValue());				
								serverAttrValueMapEntry.setValue(serverAttrValueMapEntryValue);
								
								// Add the value map entry (key and value) to the XML
								serverAttrValueMap.getEntry().add(serverAttrValueMapEntry);
							}
							// Set the ValueMap XML Node
							serverAttrType.setValueMap(serverAttrValueMap);
						}
		
						// Add the ServerAttributeibuteChoiceType node to the XML
						serverAttrModule.getServerAttribute().add(serverAttrType);
					}
				}
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
		
		// Retrieve the list of Server Attributes by invoking the CIS Web Service API
		AttributeDefList attributeDefList = getServerAttributeDAO().getServerAttributeDefsFromPath(serverId, startPath, pathToServersXML);
		
		// Continue if there is a list
		if(attributeDefList != null && attributeDefList.getAttributeDef() != null && !attributeDefList.getAttributeDef().isEmpty()){

			// Assign the list of Server Attribute Definitions to a local AttributeDef type variable
			List<AttributeDef> attributeDefs = attributeDefList.getAttributeDef();			

			// Prepare a local ServerAttributeModule XML variable for creating a list of "ServerAttributeDef" nodes
			// This XML variable will be written out to the specified file. 
			ServerAttributeModule serverAttrModule = new ObjectFactory().createServerAttributeModule();

			// Iterate over the retrieved Server Attribute Definitions List
			for (AttributeDef attributeDef : attributeDefs) {
				
				// Compare the passed in update rule with the Server Attribute Definition
				String updateRuleFromServerList = null;
				if (attributeDef.getUpdateRule() != null) {
					updateRuleFromServerList = attributeDef.getUpdateRule().toString();
				} else {
					logger.debug("Update Rule is null for "+attributeDef.getName());
				}
				boolean getAttribute = false;
				// If the passed in updateRule is null, empty, blank or contains an asterisk then assume then get all attribute definitions in the Server Attribute Def List for writing to the XML file
				if (updateRule == null) {
					getAttribute = true;
				} else {
					updateRule = updateRule.trim();
					if (updateRule.trim().contains("*") || updateRule.length() == 0) {
						getAttribute = true;
					}
					// If a Server Attribute Definition updateRule was found and it matches what was passed in then get this attributeDef for writing to the XML file
					if (updateRuleFromServerList != null && updateRule.contains(updateRuleFromServerList)) {
						getAttribute = true;
					}
				}
				
				// Get the Server Attribute Definition and compose a ServerAttributeDef type node to write to the XML file.
				if (getAttribute) {
					// Instantiate and populate a new ServerAttributeDef type variable
					ServerAttributeDefType serverAttrDefType = new ServerAttributeDefType();
					// Generate and ID using the first token in the server attribute definition path name
					String id = getToken(1, attributeDef.getName());
					if (id == null) {
						// Just use the original name for the id
						serverAttrDefType.setId(attributeDef.getName());
					} else {
						// Increment a number and concatenate to the token
						serverAttrDefType.setId(getTokenId(id));
					}
					serverAttrDefType.setName(attributeDef.getName());
					// Set the Attribute Type by converting from the attribute type being returned
					serverAttrDefType.setType(AttributeTypeSimpleType.valueOf(attributeDef.getType().toString()));

					if (attributeDef.getAllowedValues() != null) {
						String list = "";
						AttributeSimpleValueList allowedValues = attributeDef.getAllowedValues();
						for (String item : allowedValues.getItem()) {
							list = list+item+",";
						}
						if (list.length() > 0) {
							list = list.substring(0, list.length()-1);
						}
						serverAttrDefType.setAllowedValues(list);
					}
					if (attributeDef.getAnnotation() != null) {
						serverAttrDefType.setAnnotation(attributeDef.getAnnotation());
					}
					if (attributeDef.getDefaultValue() != null) {
						serverAttrDefType.setDefaultValue(attributeDef.getDefaultValue());
					}
					if (attributeDef.getDisplayName() != null) {
						serverAttrDefType.setDisplayName(attributeDef.getDisplayName());
					}	
					if (attributeDef.getMaxValue() != null) {
						serverAttrDefType.setMaxValue(attributeDef.getMaxValue());
					}	
					if (attributeDef.getMinValue() != null) {
						serverAttrDefType.setMinValue(attributeDef.getMinValue());
					}	
					if (attributeDef.getPattern() != null) {
						serverAttrDefType.setPattern(attributeDef.getPattern());
					}
					
					if (attributeDef.getSuggestedValues() != null) {
						String list = "";
						AttributeSimpleValueList suggestedValues = attributeDef.getSuggestedValues();
						for (String item : suggestedValues.getItem()) {
							list = list+item+",";
						}
						if (list.length() > 0) {
							list = list.substring(0, list.length()-1);
						}
						serverAttrDefType.setSuggestedValues(list);
					}
					if (attributeDef.getUnitName() != null) {
						serverAttrDefType.setUnitName(attributeDef.getUnitName());
					}
					if (attributeDef.getUpdateRule() != null) {
						serverAttrDefType.setUpdateRule(attributeDef.getUpdateRule().toString());
					}
					
					// Add the node to the XML
					serverAttrModule.getServerAttributeDef().add(serverAttrDefType);
					}
			}
			// Generate the XML file
			XMLUtils.createXMLFromModuleType(serverAttrModule, pathToServerAttributeXML);
		}	
	}

	/**
	 * @return the nth token for a string value
	 * tokenNum=1
	 * name=/server/webservices/baseURI
	 * tokenized string= server webservices baseURI
	 * return the value "server"
	 */
	private String getToken(int tokenNum, String name) {
		// Tokenize a path based on "/" separator
	    StringTokenizer st = new StringTokenizer(name, "/");
	    int i=0;
	    while (st.hasMoreTokens()) {
	    	i++;
	    	if (i == tokenNum) {
	    		return st.nextToken();
	    	}	
	    	st.nextToken();
	    }
	    return null;
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
	
	/**
	 * Find the attributeDef from the list of attributeDefs that matches the given attribute name
	 * @param attributeDefList Attribute Def List
	 * @param attributeName Attribute Name
	 * @return AttributeDef that matches the attribute Name, null if not found
	 */
	private AttributeDef getAttributeDef(List<AttributeDef> attributeDefList, String attributeName) {

		if (attributeDefList == null || attributeName == null) {
			return null;
		}
		for (AttributeDef attributeDef : attributeDefList) {
			
			if (attributeDef.getName().equalsIgnoreCase(attributeName)) {
				return attributeDef;
			}
		}
		return null;
	}
	
}