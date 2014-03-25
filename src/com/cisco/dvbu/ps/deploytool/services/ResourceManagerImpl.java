/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.services;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;

import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.exception.ValidationException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.XMLUtils;
import com.cisco.dvbu.ps.deploytool.dao.ResourceDAO;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.ResourceWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.cisco.dvbu.ps.deploytool.modules.ResourceModule;
import com.cisco.dvbu.ps.deploytool.modules.ResourceType;
import com.compositesw.services.system.admin.resource.Resource;
import com.compositesw.services.system.admin.resource.ResourceList;

public class ResourceManagerImpl implements ResourceManager{

	private ResourceDAO resourceDAO = null;
	// Get the configuration property file set in the environment with a default of deploy.properties
	String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");
	
	private static Log logger = LogFactory.getLog(ResourceManagerImpl.class);

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#executeConfiguredProcedure(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void executeConfiguredProcedures(String serverId,String procedureIds, String pathToResourceXML,String pathToServersXML) throws CompositeException {

		String prefix = "executeConfiguredProcedures";
		
		// Extract variables for the procedureIds
		procedureIds = CommonUtils.extractVariable(prefix, procedureIds, propertyFile, true);

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToResourceXML)) {
			throw new CompositeException("File ["+pathToResourceXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		List<ResourceType> resourceList = getResources(serverId, procedureIds, pathToResourceXML, pathToServersXML);

		if (resourceList != null && resourceList.size() > 0) {
			
			for (ResourceType resource : resourceList) 
			{
				// Get the identifier and convert any $VARIABLES
				String resourceId = CommonUtils.extractVariable(prefix, resource.getId(), propertyFile, true);
				
				/**
				 * Possible values for archives 
				 * 1. csv string like import1,import2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -import1,import3 (we ignore passed in resources and process rest of the in the input xml
				 */
				 if(DeployUtil.canProcessResource(procedureIds, resourceId)){
					 String arguments ="";
					 if(resource.getArgument() != null){
						 
						 List<String> argumentList = resource.getArgument();
						 int i = 0;
						 for (String argument : argumentList) {
							 
							 if(argument != null){
								 if(i > 0){
									 arguments +=",";
								 }
								 arguments += "'"+argument.trim()+"'";
							 }
							 i++;
						 }

					 }
					if(logger.isInfoEnabled()){
						 logger.info("Executing Procedure "+resource.getResourcePath()+" with arguments "+arguments);
					 }
					 executeProcedure(serverId, resource.getResourcePath(), resource.getDataServiceName(), pathToServersXML, arguments); 
				 }
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#executeProcedure(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void executeProcedure(String serverId, String procedureName, String dataServiceName, String pathToServersXML, String arguments) throws CompositeException {

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		if(logger.isInfoEnabled()){
			 logger.info("Executing Procedure "+procedureName+" with arguments "+arguments);
		 }

		getResourceDAO().executeProcedure(serverId, procedureName, dataServiceName, pathToServersXML, arguments);
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#deleteResource(java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void deleteResource(String serverId, String resourcePath, String pathToServersXML) throws CompositeException {

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		try {
			String resourceType = getResourceType(serverId, resourcePath, pathToServersXML);

			if(resourceType !=null){
				 if(logger.isInfoEnabled()){
					logger.info("Deleting resource "+resourcePath+" on server "+serverId);
				 }
	
				getResourceDAO().deleteResource(serverId, resourcePath, resourceType, pathToServersXML);
			}
		} catch (CompositeException e) {
			if (e.getMessage().contains("does not exist")) {
				if(logger.isInfoEnabled()){
					logger.info("Ignoring Fault Message...Resource does not exist: "+resourcePath+" on server "+serverId);
				}
			} else {
				throw new CompositeException(e);
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#deleteResources(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void deleteResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException {

		String prefix = "deleteResources";
		
		// Extract variables for the resourceIds
		resourceIds = CommonUtils.extractVariable(prefix, resourceIds, propertyFile, true);

		List<ResourceType> resourceList = getResources(serverId, resourceIds, pathToResourceXML, pathToServersXML);
		if (resourceList != null && resourceList.size() > 0) {
			
			for (ResourceType resource : resourceList) {

				// Get the identifier and convert any $VARIABLES
				String resourceId = CommonUtils.extractVariable(prefix, resource.getId(), propertyFile, true);
				
				/**
				 * Possible values for archives 
				 * 1. csv string like import1,import2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -import1,import3 (we ignore passed in resources and process rest of the in the input xml
				 */
				 if(DeployUtil.canProcessResource(resourceIds,resourceId)){
					 
					 deleteResource(serverId, resource.getResourcePath(), pathToServersXML);
				 }
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#renameResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void renameResource(String serverId, String resourcePath, String pathToServersXML, String newName) throws CompositeException {

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		Resource resource = getResourceDAO().getResource(serverId, resourcePath, pathToServersXML);
		
		if(resource !=null){
			 if(logger.isInfoEnabled()){
				logger.info("Renaming resource "+resourcePath+" to new name "+newName+" on server "+serverId);
			 }

			getResourceDAO().renameResource(serverId, resourcePath, resource.getType().name(), newName, pathToServersXML);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#renameResources(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void renameResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException {

		String prefix = "renameResources";
		
		// Extract variables for the resourceIds
		resourceIds = CommonUtils.extractVariable(prefix, resourceIds, propertyFile, true);

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToResourceXML)) {
			throw new CompositeException("File ["+pathToResourceXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		List<ResourceType> resourceList = getResources(serverId, resourceIds, pathToResourceXML, pathToServersXML);
		if (resourceList != null && resourceList.size() > 0) {
			
			for (ResourceType resource : resourceList) {

				// Get the identifier and convert any $VARIABLES
				String resourceId = CommonUtils.extractVariable(prefix, resource.getId(), propertyFile, true);
				
				/**
				 * Possible values for archives 
				 * 1. csv string like import1,import2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -import1,import3 (we ignore passed in resources and process rest of the in the input xml
				 */
				 if(DeployUtil.canProcessResource(resourceIds,resourceId)){
					 renameResource(serverId, resource.getResourcePath(), pathToServersXML, resource.getNewName());
				 }
			}
		}
	}


	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#copyResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void copyResource(String serverId, String resourcePath, String pathToServersXML, String targetContainerPath, String newName, String copyMode) throws CompositeException {

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		Resource resource = getResourceDAO().getResource(serverId, resourcePath, pathToServersXML);
		
		if(resource !=null){
			 if(logger.isInfoEnabled()){
				logger.info("Copy resource "+resourcePath+" to new path "+targetContainerPath+"/"+newName+" on server "+serverId);
			 }

			 if (copyMode == null) {
				 copyMode = "FAIL_IF_EXISTS";
			 }
			getResourceDAO().copyResource(serverId, resourcePath, resource.getType().name(), targetContainerPath, newName, copyMode, pathToServersXML);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#copyResources(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void copyResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException {

		String prefix = "copyResources";
		
		// Extract variables for the resourceIds
		resourceIds = CommonUtils.extractVariable(prefix, resourceIds, propertyFile, true);

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToResourceXML)) {
			throw new CompositeException("File ["+pathToResourceXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		List<ResourceType> resourceList = getResources(serverId, resourceIds, pathToResourceXML, pathToServersXML);
		if (resourceList != null && resourceList.size() > 0) {
			
			for (ResourceType resource : resourceList) {

				// Get the identifier and convert any $VARIABLES
				String resourceId = CommonUtils.extractVariable(prefix, resource.getId(), propertyFile, true);
				
				/**
				 * Possible values for archives 
				 * 1. csv string like import1,import2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -import1,import3 (we ignore passed in resources and process rest of the in the input xml
				 */
				 if(DeployUtil.canProcessResource(resourceIds,resourceId)){
					 copyResource(serverId, resource.getResourcePath(), pathToServersXML, resource.getTargetContainerPath(), resource.getNewName(), resource.getCopyMode());
				 }
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#moveResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void moveResource(String serverId, String resourcePath, String pathToServersXML, String targetContainerPath, String newName) throws CompositeException {

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		Resource resource = getResourceDAO().getResource(serverId, resourcePath, pathToServersXML);
		
		if(resource !=null){
			 if(logger.isInfoEnabled()){
				logger.info("Move resource "+resourcePath+" to new path "+targetContainerPath+"/"+newName+" on server "+serverId);
			 }

			getResourceDAO().moveResource(serverId, resourcePath, resource.getType().name(), targetContainerPath, newName, pathToServersXML);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#moveResources(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void moveResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException {

		String prefix = "moveResources";
		
		// Extract variables for the resourceIds
		resourceIds = CommonUtils.extractVariable(prefix, resourceIds, propertyFile, true);

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToResourceXML)) {
			throw new CompositeException("File ["+pathToResourceXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		List<ResourceType> resourceList = getResources(serverId, resourceIds, pathToResourceXML, pathToServersXML);
		if (resourceList != null && resourceList.size() > 0) {
			
			for (ResourceType resource : resourceList) {

				// Get the identifier and convert any $VARIABLES
				String resourceId = CommonUtils.extractVariable(prefix, resource.getId(), propertyFile, true);
				
				/**
				 * Possible values for archives 
				 * 1. csv string like import1,import2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -import1,import3 (we ignore passed in resources and process rest of the in the input xml
				 */
				 if(DeployUtil.canProcessResource(resourceIds,resourceId)){
					 moveResource(serverId, resource.getResourcePath(), pathToServersXML, resource.getTargetContainerPath(), resource.getNewName());
				 }
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#doResourceExist(java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public boolean doResourceExist(String serverId, String resourcePath, String pathToServersXML) {

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		Resource resource = getResourceDAO().getResource(serverId, resourcePath, pathToServersXML);
		
		boolean exists = false;
		if(resource !=null){
			exists = true;
		}
		if(logger.isInfoEnabled()){
			logger.info("Resource exists? ["+exists+"] "+resourcePath+" on server "+serverId);
		}
		return exists;
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#resourceExists(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public boolean resourceExists(String serverId, String resourcePath, String resourceType, String pathToServersXML) {
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		boolean exists = getResourceDAO().resourceExists(serverId, resourcePath, resourceType, pathToServersXML);

		if(logger.isInfoEnabled()){
			logger.info("Resource exists? ["+exists+"] "+resourcePath+" on server "+serverId);
		}
		return exists;
	}
	
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#doResourcesExist(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void doResourcesExist(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException {

		String prefix = "doResourcesExist";
		
		// Extract variables for the resourceIds
		resourceIds = CommonUtils.extractVariable(prefix, resourceIds, propertyFile, true);

		List<ResourceType> resourceList = getResources(serverId, resourceIds, pathToResourceXML, pathToServersXML);
		if (resourceList != null && resourceList.size() > 0) {
			
			for (ResourceType resource : resourceList) {

				// Get the identifier and convert any $VARIABLES
				String resourceId = CommonUtils.extractVariable(prefix, resource.getId(), propertyFile, true);
				
				/**
				 * Possible values for archives 
				 * 1. csv string like import1,import2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -import1,import3 (we ignore passed in resources and process rest of the in the input xml
				 */
				 if(DeployUtil.canProcessResource(resourceIds,resourceId)){
					 
					 if(!doResourceExist(serverId, resource.getResourcePath(), pathToServersXML)){
						 throw new CompositeException("Resource Id "+resourceId +" does not exist on server "+serverId);
					 }
				 }
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#lockResource(java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void lockResource(String serverId, String resourcePath, String pathToServersXML) throws CompositeException {

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		Resource resource = getResourceDAO().getResource(serverId, resourcePath, pathToServersXML);
		
		if(resource !=null){
			if(logger.isInfoEnabled()){
				logger.info("Locking resource "+resourcePath+" on server "+serverId);
			}
			getResourceDAO().lockResource(serverId, resourcePath, resource.getType().name(), pathToServersXML);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#lockResources(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void lockResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException {

		String prefix = "lockResources";
		
		// Extract variables for the resourceIds
		resourceIds = CommonUtils.extractVariable(prefix, resourceIds, propertyFile, true);

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToResourceXML)) {
			throw new CompositeException("File ["+pathToResourceXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		List<ResourceType> resourceList = getResources(serverId, resourceIds, pathToResourceXML, pathToServersXML);
		if (resourceList != null && resourceList.size() > 0) {
			
			for (ResourceType resource : resourceList) {

				// Get the identifier and convert any $VARIABLES
				String resourceId = CommonUtils.extractVariable(prefix, resource.getId(), propertyFile, true);
				
				/**
				 * Possible values for archives 
				 * 1. csv string like import1,import2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -import1,import3 (we ignore passed in resources and process rest of the in the input xml
				 */
				 if(DeployUtil.canProcessResource(resourceIds,resourceId)){
					 lockResource(serverId, resource.getResourcePath(), pathToServersXML);
				 }
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#unlockResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void unlockResource(String serverId, String resourcePath, String pathToServersXML, String comment) throws CompositeException {

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		Resource resource = getResourceDAO().getResource(serverId, resourcePath, pathToServersXML);
		
		if(resource !=null){
			if(logger.isInfoEnabled()){
				logger.info("Unlocking resource "+resourcePath+" on server "+serverId);
			}
			getResourceDAO().unlockResource(serverId, resourcePath, resource.getType().name(), pathToServersXML, comment);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#unlockResources(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void unlockResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException {

		String prefix = "unlockResources";
		
		// Extract variables for the resourceIds
		resourceIds = CommonUtils.extractVariable(prefix, resourceIds, propertyFile, true);

		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		List<ResourceType> resourceList = getResources(serverId, resourceIds, pathToResourceXML, pathToServersXML);
		if (resourceList != null && resourceList.size() > 0) {
			
			for (ResourceType resource : resourceList) {

				// Get the identifier and convert any $VARIABLES
				String resourceId = CommonUtils.extractVariable(prefix, resource.getId(), propertyFile, true);
				
				/**
				 * Possible values for archives 
				 * 1. csv string like import1,import2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -import1,import3 (we ignore passed in resources and process rest of the in the input xml
				 */
				 if(DeployUtil.canProcessResource(resourceIds,resourceId)){
					 
					 String comment = null;
					 if (resource.getComment() != null) {
						 comment = resource.getComment();
					 }
					 unlockResource(serverId, resource.getResourcePath(), pathToServersXML, comment);
				 }
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#getResourceType(java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public String getResourceType(String serverId, String resourcePath, String pathToServersXML) {

		try {
			Resource resource = getResourceDAO().getResource(serverId, resourcePath, pathToServersXML);
	
			// Return the resource type
			return resource.getType().name();
			
		} catch (CompositeException e) {
			throw new CompositeException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#getResourcesFromPath(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public ResourceList getResourcesFromPath(String serverId, String resourcePath, String resourceType, String resourceTypeFilter, String detailLevel,String pathToServersXML) {
		return getResourceDAO().getResourcesFromPath(serverId, resourcePath, resourceType, resourceTypeFilter, detailLevel, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#getImmediateResourcesFromPath(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public ResourceList getImmediateResourcesFromPath(String serverId, String resourcePath, String resourceType, String detailLevel,String pathToServersXML) {
		return getResourceDAO().getImmediateResourcesFromPath(serverId, resourcePath, resourceType, detailLevel, pathToServersXML);
	}

	
	private List<ResourceType> getResources(String serverId, String resources, String pathToResourceXML, String pathToServersXML) {
		// validate incoming arguments
		if(serverId == null || serverId.trim().length() ==0 || resources == null || resources.trim().length() ==0 || pathToServersXML == null || pathToServersXML.trim().length() ==0 || pathToResourceXML == null || pathToResourceXML.trim().length() ==0){
			throw new ValidationException("Invalid Arguments");
		}

		try {
			//using jaxb convert xml to corresponding java objects
			ResourceModule resourceModuleType = (ResourceModule)XMLUtils.getModuleTypeFromXML(pathToResourceXML);
			if(resourceModuleType != null && resourceModuleType.getResource() != null && !resourceModuleType.getResource().isEmpty()){
				return resourceModuleType.getResource();
			}
		} catch (CompositeException e) {
			logger.error("Error while parsing resource xml" , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#createFolder(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void createFolder(String serverId, String resourcePath, String pathToServersXML, String recursive) throws CompositeException {
		getResourceDAO().createFolder(serverId, resourcePath, pathToServersXML, recursive);
		if(logger.isInfoEnabled()){
			logger.info("Resource Folder Created "+resourcePath+" on server "+serverId);
		}

	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.ResourceManager#createFolders(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void createFolders(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException {

		String prefix = "createFolders";
		
		// Extract variables for the resourceIds
		resourceIds = CommonUtils.extractVariable(prefix, resourceIds, propertyFile, true);

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		List<ResourceType> resourceList = getResources(serverId, resourceIds, pathToResourceXML, pathToServersXML);
		if (resourceList != null && resourceList.size() > 0) {
			
			for (ResourceType resource : resourceList) {
				String recursive = "true";
				if (resource.getRecursive() != null)
					recursive = resource.getRecursive();

				// Get the identifier and convert any $VARIABLES
				String resourceId = CommonUtils.extractVariable(prefix, resource.getId(), propertyFile, true);
				
				/**
				 * Possible values for archives 
				 * 1. csv string like import1,import2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -import1,import3 (we ignore passed in resources and process rest of the in the input xml
				 */
				if(DeployUtil.canProcessResource(resourceIds,resourceId)){
					createFolder(serverId, resource.getResourcePath(), pathToServersXML, recursive);
				}
			}
		}
		
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