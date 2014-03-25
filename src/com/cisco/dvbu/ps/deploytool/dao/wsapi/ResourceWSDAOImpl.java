/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao.wsapi;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CompositeLogger;
import com.cisco.dvbu.ps.common.util.wsapi.CisApiFactory;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.DeployManagerUtil;
import com.cisco.dvbu.ps.deploytool.dao.ResourceDAO;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.compositesw.services.system.admin.CopyResourceSoapFault;
import com.compositesw.services.system.admin.CreateResourceSoapFault;
import com.compositesw.services.system.admin.DestroyResourceSoapFault;
import com.compositesw.services.system.admin.ExecutePortType;
import com.compositesw.services.system.admin.ExecuteSqlSoapFault;
import com.compositesw.services.system.admin.GetAllResourcesByPathSoapFault;
import com.compositesw.services.system.admin.GetChildResourcesSoapFault;
import com.compositesw.services.system.admin.GetUsedResourcesSoapFault;
import com.compositesw.services.system.admin.LockResourceSoapFault;
import com.compositesw.services.system.admin.MoveResourceSoapFault;
import com.compositesw.services.system.admin.RenameResourceSoapFault;
import com.compositesw.services.system.admin.ResourceExistsSoapFault;
import com.compositesw.services.system.admin.ResourcePortType;
import com.compositesw.services.system.admin.UnlockResourceSoapFault;
import com.compositesw.services.system.admin.resource.CopyMode;
import com.compositesw.services.system.admin.resource.Resource;
import com.compositesw.services.system.admin.resource.ResourceList;
import com.compositesw.services.system.admin.resource.ResourceSubType;
import com.compositesw.services.system.admin.resource.ResourceType;
import com.compositesw.services.system.util.common.DetailLevel;

public class ResourceWSDAOImpl implements ResourceDAO {

	private static Log logger = LogFactory.getLog(ResourceWSDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#executeProcedure(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void executeProcedure(String serverId, String procedureName,String dataServiceName, String pathToServersXML, String arguments) throws CompositeException {

		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.executeProcedure", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the execute port based on target server name
		ExecutePortType port = CisApiFactory.getExecutePort(targetServer);		
		
		
		String procedureScript = "";

		if(arguments == null || arguments.trim().length() == 0 || arguments.trim().equals("\"\"")){
			procedureScript =  "select * from "+procedureName+"()" ;
		}else{
			procedureScript =  "select * from "+procedureName+"("+arguments+")";
		}
		
		try {
			if(logger.isInfoEnabled()){
				logger.info("Calling executeSql with sql "+procedureScript);
			}
			port.executeSql(procedureScript, true, false, 0, 1, false, null, null, dataServiceName, null, null, null, null, null, null, null);
		
		} catch (ExecuteSqlSoapFault e) {
			/*		
			if (e.getFaultInfo().getErrorEntry() != null) {
				for (MessageEntry me : e.getFaultInfo().getErrorEntry()) {
					logger.error("ExecuteSqlSoapFault ------------------- Composite Server SOAP Fault MessageEntry Begin -------------------");
					logger.error("ExecuteSqlSoapFault MessageEntry.name ["+me.getName()+"] code=["+me.getCode()+"]");
					logger.error("ExecuteSqlSoapFault MessageEntry.message:"+me.getMessage());
					logger.error("ExecuteSqlSoapFault MessageEntry.detail:"+me.getDetail());
					logger.error("ExecuteSqlSoapFault ------------------- Composite Server SOAP Fault MessageEntry End -------------------");
				}				
			}
*/
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "executeProcedure", "Resource", procedureName, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#deleteResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void deleteResource(String serverId, String resourcePath, String resourceType, String pathToServersXML) throws CompositeException {

		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.deleteResource", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			// Make sure the resource exists before executing any actions
			if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, resourceType, pathToServersXML)) {
				port.destroyResource(resourcePath, ResourceType.valueOf(resourceType), true);
			} else {
				throw new ApplicationException("The resource "+resourcePath+" does not exist.");
			}				
		} catch (DestroyResourceSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "deleteResource", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#renameResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void renameResource(String serverId, String resourcePath, String resourceType, String newName, String pathToServersXML) throws CompositeException {

		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.renameResource", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			// Make sure the resource exists before executing any actions
			if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, resourceType, pathToServersXML)) {
				port.renameResource(resourcePath, ResourceType.valueOf(resourceType), newName);
			} else {
				throw new ApplicationException("The resource "+resourcePath+" does not exist.");
			}				
		} catch (RenameResourceSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "renameResource", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#copyResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void copyResource(String serverId, String resourcePath, String resourceType, String targetContainerPath, String newName, String copyMode, String pathToServersXML) throws CompositeException {

		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.copyResource", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			// Make sure the resource exists before executing any actions
			if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, resourceType, pathToServersXML)) {
				port.copyResource(resourcePath, ResourceType.valueOf(resourceType), targetContainerPath, newName, CopyMode.valueOf(copyMode));
			} else {
				throw new ApplicationException("The resource "+resourcePath+" does not exist.");
			}				
		} catch (CopyResourceSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "copyResource", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#moveResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void moveResource(String serverId, String resourcePath, String resourceType, String targetContainerPath, String newName, String pathToServersXML) throws CompositeException {

		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.moveResource", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			// Make sure the resource exists before executing any actions
			if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, resourceType, pathToServersXML)) {
				port.moveResource(resourcePath, ResourceType.valueOf(resourceType), targetContainerPath, newName, true);
			} else {
				throw new ApplicationException("The resource "+resourcePath+" does not exist.");
			}				
		} catch (MoveResourceSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "moveResource", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#unlockResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void lockResource(String serverId, String resourcePath, String resourceType, String pathToServersXML) throws CompositeException {

		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.lockResource", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			// Make sure the resource exists before executing any actions
			if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, resourceType, pathToServersXML)) {
				port.lockResource(resourcePath, ResourceType.valueOf(resourceType), DetailLevel.FULL);
			} else {
				throw new ApplicationException("The resource "+resourcePath+" does not exist.");
			}				
		} catch (LockResourceSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "lockResource", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#unlockResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void unlockResource(String serverId, String resourcePath, String resourceType, String pathToServersXML, String comment) throws CompositeException {

		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.unlockResource", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			// Make sure the resource exists before executing any actions
			if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, resourceType, pathToServersXML)) {
				port.unlockResource(resourcePath, ResourceType.valueOf(resourceType), DetailLevel.FULL, comment);
			} else {
				throw new ApplicationException("The resource "+resourcePath+" does not exist.");
			}		
		} catch (UnlockResourceSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "unlockResource", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#getResource(java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public Resource getResource(String serverId, String resourcePath, String pathToServersXML) throws CompositeException {

		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
				ResourceList resourceList = port.getAllResourcesByPath(resourcePath, DetailLevel.FULL);
				if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty()){
					
					List<Resource> resources = resourceList.getResource();
					
					for (Resource resource : resources) {
						if(resource.getPath().equalsIgnoreCase(resourcePath)){
							return resource;
						}
					}
				}

		} catch (GetAllResourcesByPathSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getResource", "Resource", resourcePath, targetServer) +
							 "\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#getResourceCompositeServer(CompositeServer, java.lang.String)
	 */
//	@Override
	public Resource getResourceCompositeServer(CompositeServer targetServer, String resourcePath) throws CompositeException {

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
				ResourceList resourceList = port.getAllResourcesByPath(resourcePath, DetailLevel.FULL);
				if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty()){
					
					List<Resource> resources = resourceList.getResource();
					
					for (Resource resource : resources) {
						if(resource.getPath().equalsIgnoreCase(resourcePath)){
							return resource;
						}
					}
				}

		} catch (GetAllResourcesByPathSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getResource", "Resource", resourcePath, targetServer) +
							 "\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#resourceExists(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public boolean resourceExists(String serverId, String resourcePath, String resourceType, String pathToServersXML) {

		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		boolean exists = false;
		try {
			
			exists = port.resourceExists(resourcePath, ResourceType.valueOf(resourceType), null);
			
		} catch (ResourceExistsSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "resourceExists", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
		return exists;
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#getResourcesFromPath(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public ResourceList getResourcesFromPath(String serverId, String resourcePath, String resourceType, String resourceTypeFilter, String detailLevel, String pathToServersXML) throws CompositeException{

		String validateResourceType = resourceType;
		if (resourceType == null) {
			validateResourceType = ResourceType.CONTAINER.name();
		}
		// Make sure the resource exists before executing any actions
		if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, validateResourceType, pathToServersXML)) {
			return getAllResourcesFromPath(serverId, resourcePath, resourceType, resourceTypeFilter, detailLevel, pathToServersXML, true);
		} else {
			throw new ApplicationException("The resource "+resourcePath+" does not exist.");
		}	
	}
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#getImmediateResourcesFromPath(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public ResourceList getImmediateResourcesFromPath(String serverId,String resourcePath, String resourceType, String detailLevel,String pathToServersXML) throws CompositeException {

		String validateResourceType = resourceType;
		if (resourceType == null) {
			validateResourceType = ResourceType.CONTAINER.name();
		}
		// Make sure the resource exists before executing any actions
		if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, validateResourceType, pathToServersXML)) {
			return getAllResourcesFromPath(serverId, resourcePath, resourceType, null, detailLevel, pathToServersXML, false);
		} else {
			throw new ApplicationException("The resource "+resourcePath+" does not exist.");
		}	
	}
	
	private ResourceList getAllResourcesFromPath(String serverId, String resourcePath, String resourceType, String resourceTypeFilter, String detailLevel, String pathToServersXML, boolean recurse) {

		ResourceList returnResourceList = new ResourceList();

		// Add the resource to the list if no resourceTypeFilter specified or if the resource matches the passed in resourceTypeFilter
		Resource startingResource = getResource(serverId, resourcePath, pathToServersXML);
		if(resourceTypeFilter == null || (startingResource.getType() != null && resourceTypeFilter.contains(startingResource.getType().name()))){	
			returnResourceList.getResource().add(startingResource);
		}
		
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.getAllResourcesFromPath", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);
		
		addResourcesFromPath(port, returnResourceList, resourcePath, resourceType, resourceTypeFilter, detailLevel, targetServer, recurse);
		
		return returnResourceList;
	}

	private void addResourcesFromPath(ResourcePortType port, ResourceList resourceList, String resourcePath, String resourceType, String resourceTypeFilter, String detailLevel,CompositeServer targetServer, boolean recurse){
		try {
			if (resourceType == null) {
				resourceType = ResourceType.CONTAINER.name();
			}
			ResourceList childResourceList = port.getChildResources(resourcePath, ResourceType.fromValue(resourceType), DetailLevel.fromValue(detailLevel));
			if(childResourceList!= null && childResourceList.getResource() != null && !childResourceList.getResource().isEmpty()){

				List<Resource> resources = childResourceList.getResource();

				for (Resource resource : resources) {

					// Add the resource to the list if no resourceTypeFilter specified or if the resource matches the passed in resourceTypeFilter
					if(resourceTypeFilter == null || (resource.getType() != null && resourceTypeFilter.contains(resource.getType().name()))){	
						resourceList.getResource().add(resource);
					}

					// Recurse the tree structure if recurse is true and the resource sub-type is a FOLDER_CONTAINER or a data source with a suby-type of RELATIONAL_DATA_SOURCE
					//   Data Sources contain folders and tables
					//   A relational data source contains one or more folders called a schema_container which may contain TABLES
					if(recurse && resource.getSubtype() != null) {
						if ( resource.getSubtype().equals(ResourceSubType.FOLDER_CONTAINER) || resource.getSubtype().equals(ResourceSubType.RELATIONAL_DATA_SOURCE) || resource.getSubtype().equals(ResourceSubType.SCHEMA_CONTAINER) ) 
						{
							addResourcesFromPath(port, resourceList,resource.getPath(), resource.getType().name(), resourceTypeFilter, detailLevel, targetServer, recurse);
						}
					} 
				}
			}
		} catch (GetChildResourcesSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "addResourcesFromPath", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(e.getMessage(), e);
		} catch (Exception e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "addResourcesFromPath", "Resource", resourcePath, targetServer));
			throw new ApplicationException(e.getMessage(), e);
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#getUsedResources(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public ResourceList getUsedResources(String serverId, String resourcePath, String resourceType, String detailLevel, String pathToServersXML) throws CompositeException {

		if(logger.isDebugEnabled()){
			logger.debug("Entering ResourceWSDAOImpl.getUsedResources() with following params: " +
					"\n            serverId: " + serverId + 
					"\n                path: " + resourcePath +
					"\n                type: " + resourceType +
					"\n              detail: " + detailLevel + 
					"\n    pathToServersXML: " + pathToServersXML +
					"\n");
		}

		ResourceList returnResourceList = new ResourceList();

		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);
		
		try 
		{
			returnResourceList = port.getUsedResources(resourcePath, ResourceType.valueOf(resourceType), DetailLevel.valueOf(detailLevel));
		} 
		catch (GetUsedResourcesSoapFault e) 
		{
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getUsedResources", "Resource", resourcePath, targetServer) +
			 	"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
		return returnResourceList;
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#createFolder(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void createFolder(String serverId, String resourcePath, String pathToServersXML, String recursive) throws CompositeException {
//		 * @param recursive false=only create the folder specified, true=create all folders recursively

		// Init variables
		int lastIndex = 0;
		String path = null;
		String name = null;

		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.createFolder", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			// Make sure the resource does not exist before executing the create resource
			if (!DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, "CONTAINER", pathToServersXML)) {
				
				if (recursive.equalsIgnoreCase("true") || recursive.equalsIgnoreCase("1")) {
					String[] pathparts = resourcePath.split("/");
					resourcePath = "";
					boolean checkedPathExists = false;
					
					// Loop through the parts
			        for (int i=0; i<pathparts.length; i++)
			        {
			        	if (pathparts[i].trim().length() > 0) {
							resourcePath = resourcePath + "/" + pathparts[i];
							if (!resourcePath.equalsIgnoreCase("/") && !resourcePath.equalsIgnoreCase("/shared")) {
								if (checkedPathExists || !DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, "CONTAINER", pathToServersXML)) {
									// Extract the path and the name from the full resource path
									lastIndex = resourcePath.lastIndexOf("/");
									path = resourcePath.substring(0, lastIndex);
									name = resourcePath.substring(lastIndex+1);
									port.createResource(path, name, DetailLevel.valueOf("FULL"), ResourceType.valueOf("CONTAINER"), ResourceSubType.valueOf("FOLDER_CONTAINER"), null, null, null, false);
									
									// Initialize checked path exists the first time through here so that it does not check the path the next time through 
									checkedPathExists = true;
								}
							}
			        	}
			        }

				} else {
					if (!resourcePath.equalsIgnoreCase("/") && !resourcePath.equalsIgnoreCase("/shared")) {
						if (!DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, "CONTAINER", pathToServersXML)) {
							// Extract the path and the name from the full resource path
							lastIndex = resourcePath.lastIndexOf("/");
							path = resourcePath.substring(0, lastIndex);
							name = resourcePath.substring(lastIndex+1);
							port.createResource(path, name, DetailLevel.valueOf("FULL"), ResourceType.valueOf("CONTAINER"), ResourceSubType.valueOf("FOLDER_CONTAINER"), null, null, null, false);
						}
					}
				}
			}				
		} catch (CreateResourceSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "createFolder::createResource", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}	
	}
	
}
