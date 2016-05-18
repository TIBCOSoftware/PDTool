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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;

import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.exception.ValidationException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.PropertyManager;
import com.cisco.dvbu.ps.common.util.XMLUtils;
import com.cisco.dvbu.ps.deploytool.DeployManagerUtil;
import com.cisco.dvbu.ps.deploytool.dao.RebindDAO;
import com.cisco.dvbu.ps.deploytool.dao.ResourceDAO;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.RebindWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.ResourceWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.cisco.dvbu.ps.deploytool.modules.ObjectFactory;
import com.cisco.dvbu.ps.deploytool.modules.RebindFolderType;
import com.cisco.dvbu.ps.deploytool.modules.RebindModule;
import com.cisco.dvbu.ps.deploytool.modules.RebindResourceType;
import com.cisco.dvbu.ps.deploytool.modules.RebindRuleType;
import com.cisco.dvbu.ps.deploytool.modules.RebindType;
import com.cisco.dvbu.ps.deploytool.modules.ResourceTypeSimpleType;
import com.compositesw.services.system.admin.resource.ColumnList;
import com.compositesw.services.system.admin.resource.Model;
import com.compositesw.services.system.admin.resource.ParameterList;
import com.compositesw.services.system.admin.resource.PathTypePair;
import com.compositesw.services.system.admin.resource.ProcedureResource;
import com.compositesw.services.system.admin.resource.RebindRule;
import com.compositesw.services.system.admin.resource.RebindRuleList;
import com.compositesw.services.system.admin.resource.Resource;
import com.compositesw.services.system.admin.resource.ResourceList;
import com.compositesw.services.system.admin.resource.ResourceSubType;
import com.compositesw.services.system.admin.resource.ResourceType;
import com.compositesw.services.system.admin.resource.TableResource;
import com.compositesw.services.system.util.common.AttributeList;
import com.compositesw.services.system.util.common.DetailLevel;

public class RebindManagerImpl implements RebindManager {

	private static Log logger = LogFactory.getLog(RebindManagerImpl.class);
	
	private static ObjectFactory objectFactory = new ObjectFactory();
	
	private RebindDAO rebindDAO = null;
	private ResourceDAO resourceDAO = null;
	
	// Path Type Class
	private static class PathType {
		private String path;
		private String type;

		//constructor
	    private PathType() {
	    	path = null;
	    	type = null;
	    }
	    // Set/Get systemPath
	    private void setPath(String s) {
	    	this.path = s;
	    }
	    private String getPath() {
	    	return this.path;
	    }
	    // Set/Get systemPath
	    private void setType(String s) {
	    	this.type = s;
	    }
	    private String getType() {
	    	return this.type;
	    }

	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.RebindManager#generateRebindXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateRebindXML(String serverId, String startPath, String pathToRebindXML, String pathToServersXML) throws CompositeException {

		// Set the command and action name
		String command = "generateRebindXML";
		String actionName = "CREATE_XML";

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// Set the Module Action Objective
		String s1 = (startPath == null) ? "no_startPath" : "Path="+startPath;
		System.setProperty("MODULE_ACTION_OBJECTIVE", "GENERATE : "+s1);

		// Set the list of rebindable resource types for the filter list
		String rebindableResourceTypeFilter = "TABLE,PROCEDURE,LINK,DEFINITION_SET,DATA_SOURCE,TRIGGER";
		
		// Resource SubTypes that are not considered rebindable
		String defaultPropertyFile = CommonConstants.propertyFile;
		/*  Determine the property file name for this environment
		 *    1. Start with default file CommonConstants.propertyFile
		 *    2. Get Java Environment variables
		 *    3. Get OS System Environment variables
		 */
        String propertyFile = CommonUtils.getFileOrSystemPropertyValue(defaultPropertyFile, "CONFIG_PROPERTY_FILE");
        // Set a default value if not found
        if (propertyFile == null || propertyFile.trim().length() == 0) {
        	propertyFile = defaultPropertyFile;
        }
		String nonRebindableResourceSubTypeList = PropertyManager.getInstance().getProperty(propertyFile,"nonRebindableResourceSubTypeList");
		if (nonRebindableResourceSubTypeList == null || nonRebindableResourceSubTypeList.length() == 0) {
			nonRebindableResourceSubTypeList = "DATABASE_TABLE";
		} 
		
		// Get a list of all resources from the startPath
		ResourceList resourceList = DeployManagerUtil.getDeployManager().getResourcesFromPath(serverId, startPath, ResourceType.CONTAINER.name(), rebindableResourceTypeFilter, DetailLevel.FULL.name(), pathToServersXML);
		if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty()){

			// 
			List<Resource> resources = resourceList.getResource();			
			RebindModule rebindModule = objectFactory.createRebindModule();
			
			//
			// TODO - Check type of child resource before calling getUsedResources
			//        If there are no used resources, there is no reason to generate bind
			//        Change name of RebindDAO method to getRebindableResources
			List<RebindType> rebinds = rebindModule.getRebind();
			int i = 0;
			for (Resource resource : resources) {
	
				// If the resource sub type is not in the "non-rebind list" then go ahead get rebindable resources
				if ( !nonRebindableResourceSubTypeList.equalsIgnoreCase(resource.getSubtype().name()) ) {
					
					List<Resource> rebindableResources = getRebindableResources(serverId, resource.getPath(), resource.getType().name(), "SIMPLE", pathToServersXML);
					if (rebindableResources != null && rebindableResources.size() > 0)
					{

						RebindResourceType rebindResource = objectFactory.createRebindResourceType();
						rebindResource.setId("rebindResource-" + ++i);
						rebindResource.setResourcePath(resource.getPath());
						rebindResource.setResourceType(ResourceTypeSimpleType.fromValue(resource.getType().name()));
						List<RebindRuleType> rebindRules = rebindResource.getRebindRules();
						
						
						for (Resource usedResource : rebindableResources)
						{
							RebindRuleType rebindRule = objectFactory.createRebindRuleType();
							rebindRule.setOldPath(usedResource.getPath());
							rebindRule.setOldType(ResourceTypeSimpleType.fromValue(usedResource.getType().name()));
							rebindRule.setNewPath(usedResource.getPath() + "_CHANGEME");
							rebindRule.setNewType(ResourceTypeSimpleType.fromValue(usedResource.getType().name()));

							rebindRules.add(rebindRule);
						}
						
						RebindType rebind = objectFactory.createRebindType();
						rebind.setRebindResource(rebindResource);
						rebinds.add(rebind);
					}							
				}
			}

			// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
			if (CommonUtils.isExecOperation()) 
			{					
				XMLUtils.createXMLFromModuleType(rebindModule, pathToRebindXML);
			} else {
				logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
			}
		}
	}

	private List<Resource> getRebindableResources(String serverId, String path, String type, String detail, String pathToServersXML) throws CompositeException {
		
		if(logger.isDebugEnabled()){
			logger.debug("Entering RebindManagerImpl.getRebindableResources() with following params: " +
					"\n                path: " + path +
					"\n                type: " + type +
					"\n              detail: " + detail + 
					"\n            serverId: " + serverId + 
					"\n    pathToServersXML: " + pathToServersXML +
					"\n");
		}
		List<Resource> rebindableResources = null;

		// Make sure the resource exists before executing any actions
		if (DeployManagerUtil.getDeployManager().resourceExists(serverId, path, type, pathToServersXML)) {
			ResourceType sourceType = ResourceType.fromValue(type);
			rebindableResources = new ArrayList<Resource>();

			switch (sourceType)
			{
				case TABLE:
				case PROCEDURE:
				case LINK:
				case DEFINITION_SET:
				case DATA_SOURCE:
				case TRIGGER:
					// Obtain the list of used resources from the admin API
					 ResourceList resourceList = getResourceDAO().getUsedResources(serverId, path, type, detail, pathToServersXML);
					 
					if (resourceList != null && resourceList.getResource() != null && resourceList.getResource().size() > 0)
					{
						rebindableResources.addAll(resourceList.getResource());
					}
					break;
					
				default:
					// Empty list for non-rebindable resources
			}
		} else {
			throw new ApplicationException("The resource "+path+" does not exist.");
		}			// Build a list of rebindable items

		return rebindableResources;
	}	

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.RebindManager#rebindResources(java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void rebindResources(String serverId, String rebindIds, String pathToRebindXml, String pathToServersXML) throws CompositeException {

		String prefix = "rebindResources";
		String processedIds = null;
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToRebindXml)) {
			throw new CompositeException("File ["+pathToRebindXml+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// Get the configuration property file set in the environment with a default of deploy.properties
		String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");

		// Extract variables for the rebindIds
		rebindIds = CommonUtils.extractVariable(prefix, rebindIds, propertyFile, true);

		// Set the Module Action Objective
		String s1 = (rebindIds == null) ? "no_rebindIds" : "Ids="+rebindIds;
		System.setProperty("MODULE_ACTION_OBJECTIVE", "REBIND : "+s1);

		List<RebindType> rebindList = getRebinds(serverId, rebindIds, pathToRebindXml, pathToServersXML);
		if (rebindList != null && rebindList.size() > 0) {

			// Loop over the list of rebinds looking for rebind Resources
			for (RebindType rebindType : rebindList) {

				if(rebindType.getRebindResource() != null){
					RebindResourceType rebindResource = rebindType.getRebindResource();
	
					// Get the identifier and convert any $VARIABLES
					String identifier = CommonUtils.extractVariable(prefix, rebindResource.getId(), propertyFile, true);
					
					/**
					 * Possible values for datasources 
					 * 1. csv string like datasource1,datasource2 (we process only resource names which are passed in)
					 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
					 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
					 * 	  like -datasource1,datasource2 (we ignore passed in resources and process rest of the in the input xml
					 */
					if(DeployUtil.canProcessResource(rebindIds, identifier))
					{
						// Add to the list of processed ids
						if (processedIds == null)
							processedIds = "";
						else
							processedIds = processedIds + ",";
						processedIds = processedIds + identifier;

						// Get the path and type of the single resource to rebind
						String resourcePath = CommonUtils.extractVariable(prefix, rebindResource.getResourcePath(), propertyFile, true);
						String resourceType = CommonUtils.extractVariable(prefix, rebindResource.getResourceType().value(), propertyFile, true);
						if(logger.isInfoEnabled()){
							logger.info("processing rebindResource on " + resourcePath);
						}

						// Setup the rebind rules and invoke the admin API
						RebindRuleList rebindRuleList = new RebindRuleList();
						List<RebindRule> rebindRules = rebindRuleList.getRebindRule();

						
						List<RebindRuleType> rules = rebindResource.getRebindRules();
						if (rules != null && !rules.isEmpty())
						{
							// Create the DAO RebindRule from the XML values
							for (RebindRuleType rule : rules)
							{
								RebindRule newRule = new RebindRule();
								newRule.setNewPath(CommonUtils.extractVariable(prefix, rule.getNewPath(), propertyFile, true));
								newRule.setNewType(ResourceType.fromValue(CommonUtils.extractVariable(prefix, rule.getNewType().value(), propertyFile, true)));
								newRule.setOldPath(CommonUtils.extractVariable(prefix, rule.getOldPath(), propertyFile, true));
								newRule.setOldType(ResourceType.fromValue(CommonUtils.extractVariable(prefix, rule.getOldType().value(), propertyFile, true)));

								// Add the new rule to the list of rules for the rebind
								rebindRules.add(newRule);
							}
							
						}
						
						// Invoke the method to validate the rebind paths exist
						RebindRuleList rebindRuleListValidated = checkPathExists(serverId, pathToServersXML, rebindRuleList);

						// If all of the old and new rebinable paths exist in CIS then use rebindResource() method to rebind.
						if (rebindRuleListValidated != null && rebindRuleListValidated.getRebindRule().size() > 0) 
						{
							
							// Set the Module Action Objective
							s1 = identifier+"=" + ((resourcePath == null) ? "no_resourcePath" : resourcePath);
							System.setProperty("MODULE_ACTION_OBJECTIVE", "REBIND_RESOURCE : "+s1);

							// Setup the list of path/type pairs to hold a single resource
							PathTypePair singlePathTypePair = new PathTypePair();
							singlePathTypePair.setPath(resourcePath);
							singlePathTypePair.setType(ResourceType.valueOf(resourceType));
							
							/*
							 * Once the rebind rule list has been prepared by evaluating all of the used paths, rebindResource is invoked to
							 * rebind all of the matching paths from the old path to the new path.  Be aware, that rebindResources
							 * requires the old resource and the new resource to be present for this method to work.
							 */
							rebindDAO.rebindResource(serverId, singlePathTypePair, rebindRuleListValidated, pathToServersXML);
						} 
						else 
						{
							/*
							 * Use the method to replace the path text within the table or procedure script
							 *   This only gets used when the "from" path does not exist in the target CIS server.
							 *   This can happen when a car file is imported from one environment like DEV where the 
							 *   physical layer resources point to one data source and the target CIS server like TEST
							 *   contains a different data source name.   In that use case, it is not permitted to use
							 *   the regular rebind as it will throw an error, therefore, the only recourse is to rebind
							 *   the actual script or procedure text and use the individual methods for each sub-type
							 *   to perform the update.
							 *   
							 * Textual Path Replacement Rules:
							 * ------------------------------
							 *  Caveat:
							 *      For textual path replacement, when the "fromFolder" does not exist and the SQL_TABLE (Views) and 
							 *      SQL_SCRIPT_PROCEDURE (Procedures) have models, the model is lost.  The reason is there is no way
							 *      to programatically create a model in the API.   For parameterized queries this is unfortunate as
							 *      there is no way to rebuild the model once removed.  For Views, the model can be regenerated in
							 *      most cases.
							 * 
							 *	resourceType = 'TABLE'
							 *		subtype = 'SQL_TABLE' -- Get Regular View
							 *			procedureTextCurr = tableResource.getSqlText();
							 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
							 *			procedureText = procedureTextCurr.replaceAll(fromFolder, toFolder);
							 *			port.updateSqlTable(resourcePath, detailLevel, procedureText, model, isExplicitDesign, columns, annotation, attributes);
							 *
							 *	resourceType = 'PROCEDURE'
							 *		subtype = 'SQL_SCRIPT_PROCEDURE' -- Update Regular Procedure
							 *			procedureTextCurr = procedureResource.getScriptText();
							 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
							 *			procedureText = procedureTextCurr.replaceAll(fromFolder, toFolder);
							 *			port.updateSqlScriptProcedure(resourcePath, detailLevel, procedureText, model, isExplicitDesign, parameters, annotation, attributes);
							 *
							 *		subtype = 'EXTERNAL_SQL_PROCEDURE' -- Update Packaged Query Procedure
							 *			usedResourcePathCurr = procedureResource.getExternalDataSourcePath();
							 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
							 *			usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
							 *			port.updateExternalSqlProcedure(resourcePath, detailLevel, procedureText, usedResourcePath, parameters, annotation, attributes);
							 *
							 *		subtype = 'BASIC_TRANSFORM_PROCEDURE' -- Update XSLT Basic Transformation definition
							 *			usedResourcePathCurr = procedureResource.getTransformSourcePath();
							 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
							 *			usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
							 *			port.updateBasicTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, annotation, attributes);
							 *
							 *		subtype = 'XSLT_TRANSFORM_PROCEDURE' -- Update XSLT Transformation text
							 *			usedResourcePathCurr = procedureResource.getTransformSourcePath();
							 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
							 *			usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
							 *			port.updateXsltTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, procedureText, model, annotation, isExplicitDesign, parameters, attributes);
							 *
							 *		subtype = 'STREAM_TRANSFORM_PROCEDURE' -- Update XSLT Stream Transformation text
							 *			usedResourcePathCurr = procedureResource.getTransformSourcePath();
							 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
							 *			usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
							 *			port.updateStreamTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, model, isExplicitDesign, parameters, annotation, attributes);
							 */
							Resource resource = getResourceDAO().getResource(serverId, resourcePath, pathToServersXML);
							
							// Set the Module Action Objective
							s1 = identifier+"=" + ((resource == null) ? "no_resource" : resource);
							System.setProperty("MODULE_ACTION_OBJECTIVE", "REBIND_FOLDER : "+s1);

							for (RebindRuleType rule : rules)
							{
								String fromFolder = CommonUtils.extractVariable(prefix, rule.getOldPath(), propertyFile, true);
								String toFolder = CommonUtils.extractVariable(prefix, rule.getNewPath(), propertyFile, true);
								// Invoke the replace path text for this rule
								replacePathText(serverId, pathToServersXML, resource, fromFolder, toFolder);
							}
						}
					}
				}
			}
			// Determine if any resourceIds were not processed and report on this
			if (processedIds != null) {
				if(logger.isInfoEnabled()){
					logger.info("Rebind entries processed="+processedIds);
				}
			} else {
				if(logger.isInfoEnabled()){
					String msg = "Warning: No rebind entries were processed for the input list.  rebindIds="+rebindIds;
					logger.info(msg);
					System.setProperty("MODULE_ACTION_MESSAGE", msg);
				}		
			}
		} else {
			if(logger.isInfoEnabled()){
				String msg = "Warning: No rebind entries found for Rebind Module XML at path="+pathToRebindXml;
				logger.info(msg);
				System.setProperty("MODULE_ACTION_MESSAGE", msg);
			}							
		}
	}

	/**
	 * Get rebind XML from the RebindModule.xml file.
	 * 
	 * @param serverId
	 * @param rebindIds
	 * @param pathToRebindsXml
	 * @param pathToServersXML
	 * @return
	 */
	private List<RebindType> getRebinds(String serverId, String rebindIds,	String pathToRebindsXml, String pathToServersXML) {
		// validate incoming arguments
		if(serverId == null || serverId.trim().length() ==0 || rebindIds == null || rebindIds.trim().length() ==0 || pathToServersXML == null || pathToServersXML.trim().length() ==0 || pathToRebindsXml == null || pathToRebindsXml.trim().length() ==0){
			throw new ValidationException("Invalid Arguments");
		}

		try {
			//using jaxb convert xml to corresponding java objects
			RebindModule rebindModule = (RebindModule)XMLUtils.getModuleTypeFromXML(pathToRebindsXml);
			if(rebindModule != null && rebindModule.getRebind() != null && !rebindModule.getRebind().isEmpty()){
				return rebindModule.getRebind();
			}
		} catch (CompositeException e) {
			logger.error("Error while parsing rebind xml" , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.RebindManager#rebindFolder(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void rebindFolder(String serverId, String rebindIds,	String pathToRebindXml, String pathToServersXML) throws CompositeException {
	
		String prefix = "rebindFolder";
		
		List<RebindType> rebindList = getRebinds(serverId, rebindIds, pathToRebindXml, pathToServersXML);
		if (rebindList != null && rebindList.size() > 0) {
			
			// Set the list of rebindable resource types for the filter list
			String editableResourceTypeFilter = "TABLE,PROCEDURE";

			// Get the configuration property file set in the environment with a default of deploy.properties
			String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");

			// Extract variables for the rebindIds
			rebindIds = CommonUtils.extractVariable(prefix, rebindIds, propertyFile, true);

			// Loop over the list of rebinds looking for rebind Folders
			for (RebindType rebindType : rebindList) {

				if(rebindType.getRebindFolder() != null){
					RebindFolderType rebindFolder = rebindType.getRebindFolder();

					// Get the identifier and convert any $VARIABLES
					String identifier = CommonUtils.extractVariable(prefix, rebindFolder.getId(), propertyFile, true);

					/**
					 * Possible values for datasources 
					 * 1. csv string like datasource1,datasource2 (we process only resource names which are passed in)
					 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
					 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
					 * 	  like -datasource1,datasource2 (we ignore passed in resources and process rest of the in the input xml
					 */
					if(DeployUtil.canProcessResource(rebindIds, identifier)){
						String startPath = CommonUtils.extractVariable(prefix, rebindFolder.getStartingFolderPath(), propertyFile, true);
						String fromFolder = CommonUtils.extractVariable(prefix, rebindFolder.getRebindFromFolder(), propertyFile, true);
						String toFolder = CommonUtils.extractVariable(prefix, rebindFolder.getRebindToFolder(), propertyFile, true);

						if(logger.isDebugEnabled()){
							logger.debug("Entering RebindManagerImpl.rebindFolder() with following params: " +
									"\n             serverId: " + serverId + 
									"\n            rebindIds: " + rebindIds + 
									"\n      pathToRebindXml: " + pathToRebindXml +
									"\n     pathToServersXML: " + pathToServersXML +
									"\n            startPath: " + startPath  + 
									"\n           fromFolder: " + fromFolder + 
									"\n             toFolder: " + toFolder +
									"\n");
								}

						// Get a list of all resources from the startPath
						ResourceList resourceList = DeployManagerUtil.getDeployManager().getResourcesFromPath(serverId, startPath, ResourceType.CONTAINER.name(), editableResourceTypeFilter, DetailLevel.FULL.name(), pathToServersXML);
						if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty())
						{	
							List<Resource> resources = resourceList.getResource();
							for( Resource resource: resources )	
							{	
								String resourcePath = resource.getPath();
								String resourceType = resource.getType().name();
								
								/* 
								 * Get the list of rebinable resources (used resources) to determine if both the from resource and to resource exist.
								 * If all resources exist then use rebindResource otherwise continue and use the "Replace Path Text" capability within this method.
								 */
								RebindRuleList rebindRuleList = getAllExistingRebindableResources(serverId, resourcePath, resourceType, fromFolder, toFolder, pathToServersXML);

								// If all of the old and new rebinable paths exist in CIS then use rebindResource() method to rebind.
								if (rebindRuleList != null && rebindRuleList.getRebindRule().size() > 0) 
								{
									// Setup the list of path/type pairs to hold a single resource
									PathTypePair singlePathTypePair = new PathTypePair();
									singlePathTypePair.setPath(resourcePath);
									singlePathTypePair.setType(ResourceType.valueOf(resourceType));
									
									/*
									 * Once the rebind rule list has been prepared by evaluating all of the used paths, rebindResource is invoked to
									 * rebind all of the matching paths from the old path to the new path.  Be aware, that rebindResources
									 * requires the old resource and the new resource to be present for this method to work.
									 */
									rebindDAO.rebindResource(serverId, singlePathTypePair, rebindRuleList, pathToServersXML);
								} 
								else 
								{
									/*
									 * Use the method to replace the path text within the table or procedure script
									 *   This only gets used when the "from" path does not exist in the target CIS server.
									 *   This can happen when a car file is imported from one environment like DEV where the 
									 *   physical layer resources point to one data source and the target CIS server like TEST
									 *   contains a different data source name.   In that use case, it is not permitted to use
									 *   the regular rebind as it will throw an error, therefore, the only recourse is to rebind
									 *   the actual script or procedure text and use the individual methods for each sub-type
									 *   to perform the update.
									 *   
									 * Textual Path Replacement Rules:
									 * ------------------------------
									 *  Caveat:
									 *      For textual path replacement, when the "fromFolder" does not exist and the SQL_TABLE (Views) and 
									 *      SQL_SCRIPT_PROCEDURE (Procedures) have models, the model is lost.  The reason is there is no way
									 *      to programatically create a model in the API.   For parameterized queries this is unfortunate as
									 *      there is no way to rebuild the model once removed.  For Views, the model can be regenerated in
									 *      most cases.
									 * 
									 *	resourceType = 'TABLE'
									 *		subtype = 'SQL_TABLE' -- Get Regular View
									 *			procedureTextCurr = tableResource.getSqlText();
									 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
									 *			procedureText = procedureTextCurr.replaceAll(fromFolder, toFolder);
									 *			port.updateSqlTable(resourcePath, detailLevel, procedureText, model, isExplicitDesign, columns, annotation, attributes);
									 *
									 *	resourceType = 'PROCEDURE'
									 *		subtype = 'SQL_SCRIPT_PROCEDURE' -- Update Regular Procedure
									 *			procedureTextCurr = procedureResource.getScriptText();
									 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
									 *			procedureText = procedureTextCurr.replaceAll(fromFolder, toFolder);
									 *			port.updateSqlScriptProcedure(resourcePath, detailLevel, procedureText, model, isExplicitDesign, parameters, annotation, attributes);
									 *
									 *		subtype = 'EXTERNAL_SQL_PROCEDURE' -- Update Packaged Query Procedure
									 *			usedResourcePathCurr = procedureResource.getExternalDataSourcePath();
									 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
									 *			usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
									 *			port.updateExternalSqlProcedure(resourcePath, detailLevel, procedureText, usedResourcePath, parameters, annotation, attributes);
									 *
									 *		subtype = 'BASIC_TRANSFORM_PROCEDURE' -- Update XSLT Basic Transformation definition
									 *			usedResourcePathCurr = procedureResource.getTransformSourcePath();
									 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
									 *			usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
									 *			port.updateBasicTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, annotation, attributes);
									 *
									 *		subtype = 'XSLT_TRANSFORM_PROCEDURE' -- Update XSLT Transformation text
									 *			usedResourcePathCurr = procedureResource.getTransformSourcePath();
									 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
									 *			usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
									 *			port.updateXsltTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, procedureText, model, annotation, isExplicitDesign, parameters, attributes);
									 *
									 *		subtype = 'STREAM_TRANSFORM_PROCEDURE' -- Update XSLT Stream Transformation text
									 *			usedResourcePathCurr = procedureResource.getTransformSourcePath();
									 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
									 *			usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
									 *			port.updateStreamTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, model, isExplicitDesign, parameters, annotation, attributes);
									 */
									replacePathText(serverId, pathToServersXML, resource, fromFolder, toFolder);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * If all resources exist, return the list of rebinbable resources in the format of RebindRuleList.  If any resources in the list 
	 * do not exist, then return an null list which indicates that the use of rebindResource() method will not work since it requires
	 * the presence of both the old path and new path for it to work.
	 * 
	 * @param serverId
	 * @param resourcePath
	 * @param resourceType
	 * @param fromFolder
	 * @param toFolder
	 * @param pathToServersXML
	 * @return
	 */
	private RebindRuleList getAllExistingRebindableResources(String serverId, String resourcePath, String resourceType, String fromFolder, String toFolder, String pathToServersXML) throws CompositeException
	{
		
		// Get the list of rebinable resources for a given resource path and type
		RebindRuleList rebindRuleList = new RebindRuleList();
		List<RebindRule> rebindRules = rebindRuleList.getRebindRule();

		List<Resource> rebindableResources = getRebindableResources(serverId, resourcePath, resourceType, "SIMPLE", pathToServersXML);
		if (rebindableResources != null && rebindableResources.size() > 0)
		{
			for (Resource usedResource : rebindableResources)
			{
				// Setup the old and new paths
				String oldPath = usedResource.getPath();
				ResourceType oldType = usedResource.getType();
				// Replace any portion of the old path with the new path text.
				String newPath = oldPath.replaceAll(fromFolder, toFolder);
				ResourceType newType = oldType;

				if(logger.isDebugEnabled()){
					logger.debug("RebindManagerImpl.replacePath() rebind rules: " +
							"\n        resource path: " + resourcePath  + 
							"\n        resource type: " + resourceType + 
							"\n            from path: " + fromFolder +
							"\n              to path: " + toFolder +
							"\n         rebind rules: " +
							"\n              oldPath: " + oldPath  + 
							"\n              oldType: " + oldType.name() + 
							"\n              newPath: " + newPath + 
							"\n              newType: " + newType.name() +
							"\n");
				}
			
				if (!oldPath.equals(newPath)) {
					RebindRule newRule = new RebindRule();
					newRule.setNewPath(newPath);
					newRule.setNewType(newType);
					newRule.setOldPath(oldPath);
					newRule.setOldType(oldType);

					// Add the new rule to the list of rules for the rebind
					rebindRules.add(newRule);
				}
			}
		}
		return checkPathExists(serverId, pathToServersXML, rebindRuleList);
	}

	/**
	 * This procedure is used by both rebindFolder and rebindResources to check the existence of the rebind rules old and new paths.
	 * The rules are as follows:
	 *   When the rebind rule list has 1 or more rules then it will use the "rebindResource" API because both the "from" and the "to" paths exist.
	 *   When no paths match the "from" criteria or the "from" path does not exist then return null which will invoke the "Replace Text Path" methods.
	 *   When the "to" path does not exist, the method will throw an exception since it is not permitted to rebind to a non-existent target folder
	 *   
	 * @param serverId
	 * @param pathToServersXML
	 * @param rebindRuleList
	 * @return
	 */
	private RebindRuleList checkPathExists(String serverId, String pathToServersXML, RebindRuleList rebindRuleList) {
		
		// Initialize to true
		Boolean allRebindableResorucesExist = true;
		
		// Get the list of rebinable resources for a given resource path and type
		//RebindRuleList rebindRuleList = new RebindRuleList();
		List<RebindRule> rebindRules = rebindRuleList.getRebindRule();

		/* 
		 * Loop through the rebinbable list and create a new list of non-duplicate paths.  
		 * It is more efficient to check resource existence once per unique path.
		 */
		ArrayList<PathType> oldPathList = new ArrayList<PathType>();
		ArrayList<PathType> newPathList = new ArrayList<PathType>();
		for (RebindRule rebindRule : rebindRules) 
		{ 
			PathType pathType = new PathType();
			pathType.setPath(rebindRule.getOldPath());
			pathType.setType(rebindRule.getOldType().name());
			if (!oldPathList.contains(pathType)) {
				oldPathList.add(pathType);
			}
			
			pathType = new PathType();
			pathType.setPath(rebindRule.getNewPath());
			pathType.setType(rebindRule.getNewType().name());
			if (!newPathList.contains(pathType)) {
				newPathList.add(pathType);
			}
		}
		
		/* 
		 * Check for existence of the unique set of old paths
		 * Once it is determined that the path does not exist, there is no need to check any more paths.
		 * This is the decision point on whether to invoke the "rebindResource" API that requires both from and to paths to exist,
		 * or use the "Text Path Replacement" methodology where the specific CIS resource is called to update the resource based
		 * on its sub-type.
		 */
		for (int i=0; i < oldPathList.size() && allRebindableResorucesExist; i++) {
			PathType pathType = oldPathList.get(i);
			if (!getResourceDAO().resourceExists(serverId, pathType.getPath(), pathType.getType(), pathToServersXML))
				allRebindableResorucesExist = false;
		}
		/* 
		 * Check for existence of the unique set of "to" paths
		 * Throw an exception if the "to" paths do not exist.
		 * You can't execute a rebind if the target "to" path does not exist.
		 */
		for (int i=0; i < newPathList.size(); i++) {
			PathType pathType = newPathList.get(i);
			if (!getResourceDAO().resourceExists(serverId, pathType.getPath(), pathType.getType(), pathToServersXML))
				throw new ApplicationException("The rebind target path ["+pathType.getPath()+"] with type ["+pathType.getType()+"] does not exist.");
		}
		
		// If all rebindable "from" paths and "to" paths exist then return the RebindRuleList otherwise return null;
		if (allRebindableResorucesExist && rebindRuleList != null && rebindRuleList.getRebindRule().size() > 0) 
		{
			// When the list rebind rule list has 1 or more rules then it will use the "rebindResource" API because both the "from" and the "to" paths exist.
			return rebindRuleList; //
		} 
		else 
		{
			// When no paths match the "from" criteria or the "from" path does not exist then return null which will invoke the "Replace Text Path" methods.
			return null;
		}
	}
	
	/**
	 * This method is used to replace the path text within the table or procedure script.  
	 * 
	 *  Caveat:
	 *      For SQL_TABLE (Views) and SQL_SCRIPT_PROCEDURE (Procedures) that have models, the model is lost.
	 *      For Views, it can be regenerated but unfortunately for procedures it cannot.
	 *      
	 * The following rules are in place:
	 *      
	 * 	case SQL_TABLE:
	 *			procedureTextCurr = tableResource.getSqlText();
	 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
	 *			procedureText = procedureTextCurr.replaceAll(fromFolder, toFolder);
	 *	case SQL_SCRIPT_PROCEDURE:
	 *			procedureTextCurr = procedureResource.getScriptText();
	 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
	 *			procedureText = procedureTextCurr.replaceAll(fromFolder, toFolder);
	 *	case EXTERNAL_SQL_PROCEDURE:
	 *			usedResourcePathCurr = procedureResource.getExternalDataSourcePath();
	 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
	 *			usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
	 *	case BASIC_TRANSFORM_PROCEDURE:
	 *			usedResourcePathCurr = procedureResource.getTransformSourcePath();
	 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
	 *			usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
	 *	case XSLT_TRANSFORM_PROCEDURE:
	 *			usedResourcePathCurr = procedureResource.getTransformSourcePath();
	 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
	 *			usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
	 *	case STREAM_TRANSFORM_PROCEDURE:
	 *			usedResourcePathCurr = procedureResource.getTransformSourcePath();
	 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
	 *			usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
	 *
	 * @param serverId
	 * @param pathToServersXML
	 * @param resource
	 * @param fromFolder
	 * @param toFolder
	 */
	private void replacePathText(String serverId, String pathToServersXML, Resource resource, String fromFolder, String toFolder) {
		/*
		 * 	The following resource types and sub-types are supported:
		 *	resourceType = 'TABLE'
		 *		subtype = 'SQL_TABLE' -- Get Regular View
		 *
		 *	resourceType = 'PROCEDURE'
		 *		subtype = 'SQL_SCRIPT_PROCEDURE' -- Get Regular Procedure
		 *		subtype = 'EXTERNAL_SQL_PROCEDURE' -- Get Packaged Query Procedure
		 *		subtype = 'BASIC_TRANSFORM_PROCEDURE' -- Get XSLT Basic Transformation definition
		 *		subtype = 'XSLT_TRANSFORM_PROCEDURE' -- Get XSLT Transformation text
		 *		subtype = 'STREAM_TRANSFORM_PROCEDURE' -- Get XSLT Stream Transformation text
		 *
		 *	void takeRebindFolderAction(String serverId, String pathToServersXML, String actionName, String resourcePath, DetailLevel detailLevel,
		 *		String procedureText, String usedResourcePath, String usedResourceType, Boolean isExplicitDesign, Model model,
		 *		ColumnList columns, ParameterList parameters, String annotations, AttributeList attributes) throws CompositeException;
		 * @param serverId
		 * @param pathToServersXML
		 * @param actionName
		 * @param resourcePath
		 * @param detailLevel
		 * @param procedureText
		 * @param usedResourcePath
		 * @param usedResourceType
		 * @param isExplicitDesign
		 * @param model
		 * @param columns
		 * @param parameters
		 * @param annotations
		 * @param attributes
		 */
		String resourcePath = resource.getPath();
		ProcedureResource procedureResource = null;
		TableResource tableResource = null;

		DetailLevel detailLevel = DetailLevel.FULL;
		String procedureText = null;
		String procedureTextCurr = null;
		String usedResourcePath = null;
		String usedResourcePathCurr = null;
		String usedResourceType = null;
		Boolean isExplicitDesign = false;
		Model model = null;
		ColumnList columns = null;
		ParameterList parameters = null;
		String annotation = null;
		if (resource.getAnnotation() != null) {
			annotation = resource.getAnnotation();
		}
		AttributeList attributes = null;
		if (resource.getAttributes() != null) {
			attributes = resource.getAttributes();
		}

		ResourceSubType subTypeAction = resource.getSubtype();
		switch (subTypeAction) {
			case SQL_TABLE:
				tableResource = (TableResource) resource;
				if (tableResource.getSqlText() != null) {
					procedureTextCurr = tableResource.getSqlText();
					// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
					procedureText = procedureTextCurr.replaceAll(fromFolder, toFolder);
				}
				if (tableResource.isExplicitlyDesigned()) {
					isExplicitDesign = true;
				}
				if (tableResource.getSqlModel() != null) {
					model = tableResource.getSqlModel();
				}
				if (tableResource.getColumns() != null) {
					columns = tableResource.getColumns();
				}
				 /* 2013-02-21: mtinius
				  *  
				  * Caveat:
				  *      For SQL_TABLE (Views) and SQL_SCRIPT_PROCEDURE (Procedures) that have models, the model is lost.
				  *      For Views, it can be regenerated but unfortunately for procedures it cannot.
				  *      Unfortunately, many attempts were made to modify the SQL text and keep the model, but the
				  *      view would show that the resource rebind list was in fact rebound from old path to new path
				  *      however, the model and the script text still showed the old path.  Tried many combinations of
				  *      variables with values and with null passed into rebindDAO.takeRebindFolderAction.
				  *      This was the only recourse left given the amount of time to spend on this.
				*/
				if (model != null) {
					model = null;
				}
				// Invoke the action to update the resource, if there is procedure text to update and there is a change in the text
				if (procedureText != null && !procedureText.equals(procedureTextCurr)) {
					rebindDAO.takeRebindFolderAction(serverId, pathToServersXML, subTypeAction.name(), resourcePath, detailLevel, procedureText, usedResourcePath, usedResourceType, isExplicitDesign, model, columns, parameters, annotation, attributes);
				}
				break;
			case SQL_SCRIPT_PROCEDURE:
				procedureResource = (ProcedureResource) resource;
				if (procedureResource.getScriptText() != null) {
					procedureTextCurr = procedureResource.getScriptText();
					// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
					procedureText = procedureTextCurr.replaceAll(fromFolder, toFolder);
				}
				if (procedureResource.isExplicitlyDesigned()) {
					isExplicitDesign = true;
				}
				if (procedureResource.getScriptModel() != null) {
					model = procedureResource.getScriptModel();
				}
				if (procedureResource.getParameters() != null) {
					parameters = procedureResource.getParameters();
				}
				 /* 2013-02-21: mtinius
				  *  
				  * Caveat:
				  *      For SQL_TABLE (Views) and SQL_SCRIPT_PROCEDURE (Procedures) that have models, the model is lost.
				  *      For Views, it can be regenerated but unfortunately for procedures it cannot.
				  *      Unfortunately, many attempts were made to modify the SQL text and keep the model, but the
				  *      view would show that the resource rebind list was in fact rebound from old path to new path
				  *      however, the model and the script text still showed the old path.  Tried many combinations of
				  *      variables with values and with null passed into rebindDAO.takeRebindFolderAction.
				  *      This was the only recourse left given the amount of time to spend on this.
				*/
				if (model != null) {
					model = null;
				}
				// Invoke the action to update the resource, if there is procedure text to update and there is a change in the text
				if (procedureText != null && !procedureText.equals(procedureTextCurr)) {
					rebindDAO.takeRebindFolderAction(serverId, pathToServersXML, subTypeAction.name(), resourcePath, detailLevel, procedureText, usedResourcePath, usedResourceType, isExplicitDesign, model, columns, parameters, annotation, attributes);
				}
				break;
			case EXTERNAL_SQL_PROCEDURE:
				procedureResource = (ProcedureResource) resource;
				if (procedureResource.getExternalSqlText() != null) {
					procedureText = procedureResource.getExternalSqlText();
				}
				if (procedureResource.isExplicitlyDesigned()) {
					isExplicitDesign = true;
				}
				if (procedureResource.getParameters() != null) {
					//parameters = procedureResource.getParameters();
				}
				if (procedureResource.getExternalDataSourcePath() != null) {
					usedResourcePathCurr = procedureResource.getExternalDataSourcePath();
					// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
					usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
				}
				// Invoke the action to update the resource, if there is used resource path text to update and there is a change in the used resource path
				if (usedResourcePath != null && !usedResourcePath.equals(usedResourcePathCurr)) {
					rebindDAO.takeRebindFolderAction(serverId, pathToServersXML, subTypeAction.name(), resourcePath, detailLevel, procedureText, usedResourcePath, usedResourceType, isExplicitDesign, model, columns, parameters, annotation, attributes);
				}
				break;
			case BASIC_TRANSFORM_PROCEDURE:
				procedureResource = (ProcedureResource) resource;
				if (procedureResource.getTransformSourcePath() != null) {
					usedResourcePathCurr = procedureResource.getTransformSourcePath();
					// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
					usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
				}
				if (procedureResource.getTransformSourceType() != null) {
					usedResourceType = procedureResource.getTransformSourceType();
				}
				// Invoke the action to update the resource, if there is used resource path text to update and there is a change in the used resource path
				if (usedResourcePath != null && !usedResourcePath.equals(usedResourcePathCurr)) {
					rebindDAO.takeRebindFolderAction(serverId, pathToServersXML, subTypeAction.name(), resourcePath, detailLevel, procedureText, usedResourcePath, usedResourceType, isExplicitDesign, model, columns, parameters, annotation, attributes);
				}
				break;
			case XSLT_TRANSFORM_PROCEDURE:
				procedureResource = (ProcedureResource) resource;
				if (procedureResource.getXsltText() != null) {
					procedureText = procedureResource.getXsltText();
				}
				if (procedureResource.isExplicitlyDesigned()) {
					isExplicitDesign = true;
				}
				if (procedureResource.getXsltModel() != null) {
					model = procedureResource.getXsltModel();
				}
				if (procedureResource.getParameters() != null) {
					parameters = procedureResource.getParameters();
				}
				if (procedureResource.getTransformSourcePath() != null) {
					usedResourcePathCurr = procedureResource.getTransformSourcePath();
					// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
					usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
				}
				if (procedureResource.getTransformSourceType() != null) {
					usedResourceType = procedureResource.getTransformSourceType();
				}
				/* 2013-02-21: mtinius
				 * According to the rules in the "updateXsltTransformProcedure" the following must be set
				 * thus the reason for setting procedureText and parameters to null if the model exists.
				 * 
			     *	xsltModel (optional): A model of the XSLT transformation.  If this element is provided, 
			     *		neither the "xsltText" or "parameters" elements should be provided.
			     *	isExplicitDesign (optional): "true" if the parameters were provided by the resource
			     *	  designer. "false" if they were derived from the resource.
			     *	parameters (optional): The parameter definitions of the procedure.  This element is
			     *	  ignored (even if provided) unless "isExplicitDesign" is both present and "true".
				 */
				if (model != null) {
					procedureText = null;
					parameters = null;
				}
				// Invoke the action to update the resource, if there is used resource path text to update and there is a change in the used resource path
				if (usedResourcePath != null && !usedResourcePath.equals(usedResourcePathCurr)) {
					rebindDAO.takeRebindFolderAction(serverId, pathToServersXML, subTypeAction.name(), resourcePath, detailLevel, procedureText, usedResourcePath, usedResourceType, isExplicitDesign, model, columns, parameters, annotation, attributes);
				}
				break;
			case STREAM_TRANSFORM_PROCEDURE:
				procedureResource = (ProcedureResource) resource;
				if (procedureResource.getStreamText() != null) {
					procedureText = procedureResource.getStreamText();
				}
				if (procedureResource.isExplicitlyDesigned()) {
					isExplicitDesign = true;
				}
				if (procedureResource.getStreamModel() != null) {
					model = procedureResource.getStreamModel();
				}
				if (procedureResource.getParameters() != null) {
					parameters = procedureResource.getParameters();
				}
				if (procedureResource.getTransformSourcePath() != null) {
					usedResourcePathCurr = procedureResource.getTransformSourcePath();
					// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
					usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
				}
				if (procedureResource.getTransformSourceType() != null) {
					usedResourceType = procedureResource.getTransformSourceType();
				}
				if (model != null) {
					procedureText = null;
					parameters = null;
				}
				// Invoke the action to update the resource, if there is used resource path text to update and there is a change in the used resource path
				if (usedResourcePath != null && !usedResourcePath.equals(usedResourcePathCurr)) {
					rebindDAO.takeRebindFolderAction(serverId, pathToServersXML, subTypeAction.name(), resourcePath, detailLevel, procedureText, usedResourcePath, usedResourceType, isExplicitDesign, model, columns, parameters, annotation, attributes);
				}
				break;
			default:
				// SubType is not permitted	
				logger.info("RebindManagerImpl.replacePathText():  Rebind of subType (" + subTypeAction.name() + ") is not permitted for resource: " + resourcePath);
		}
	}
	
	
	/**
	 * @return the rebindWSDAO
	 */
	public RebindDAO getRebindDAO() {
		if(rebindDAO == null){
			rebindDAO = new RebindWSDAOImpl();
		}
		return rebindDAO;
	}

	/**
	 * @param set rebindDAO
	 */
	public void setRebindDAO(RebindDAO rebindDAO) {
		this.rebindDAO = rebindDAO;
	}
	
	/**
	 * @return the resourceDAO
	 */
	public ResourceDAO getResourceDAO() {
		if(this.resourceDAO == null){
			this.resourceDAO = new ResourceWSDAOImpl();
		}
		return resourceDAO;
	}

	/**
	 * @param resourceDAO the resourceDAO to set
	 */
	public void setResourceDAO(ResourceDAO resourceDAO) {
		this.resourceDAO = resourceDAO;
	}
	

}
