/*******************************************************************************
* Copyright (c) 2014 Cisco Systems
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* PDTool project commiters - initial release
*******************************************************************************/
/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao;

import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.compositesw.services.system.admin.resource.Resource;
import com.compositesw.services.system.admin.resource.ResourceList;

public interface ResourceDAO {
	
	/**
	 * Execute a published procedure associated with passed in name along with passed in arguments and
	 * arguments should be passed in the format 'arg1','arg2','arg3'....
	 * @param serverId target server config name
	 * @param procedureName published procedure name
	 * @param dataServiceName data service name (equivalent to schema name)
	 * @param pathToServersXML path to the server values xml
	 * @param arguments string with arguments in the format 'arg1','arg2','arg3'....
	 * @throws CompositeException if the execution of the procedure fails
	 */
	public void executeProcedure(String serverId,String procedureName, String dataServiceName,String pathToServersXML, String arguments) throws CompositeException;

	
	/**
	 * Delete a resource associated with passed in resource path 
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param resourceType ResourceType
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if deletion of the resource fails
	 */
	public void deleteResource(String serverId,String resourcePath, String resourceType, String pathToServersXML) throws CompositeException;

	/**
	 * Rename a resource
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param resourceType ResourceType
	 * @param newName the new name of the resource (not a path)
	 * @param pathToServersXML path to the server values xml
	 * @param comment description for unlock
	 * @throws CompositeException if deletion of the resource fails
	 */	
	public void renameResource(String serverId, String resourcePath, String resourceType, String newName, String pathToServersXML) throws CompositeException;

	/**
	 * Copy a resource associated with passed in resource path, target container path and new name
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param resourceType ResourceType
	 * @param targetContainerPath the target CIS folder path to copy the resource to
	 * @param newName the new name of the resource being copied
	 * @param copyMode the mode by which a copy is to be executed
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if deletion of the resource fails
	 */
	public void copyResource(String serverId, String resourcePath, String resourceType, String targetContainerPath, String newName, String copyMode, String pathToServersXML) throws CompositeException;

	/**
	 * Move a resource associated with passed in resource path, target container path and new name 
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param resourceType ResourceType
	 * @param targetContainerPath the target CIS folder path to copy the resource to
	 * @param newName the new name of the resource being copied
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if deletion of the resource fails
	 */
	public void moveResource(String serverId, String resourcePath, String resourceType, String targetContainerPath, String newName, String pathToServersXML) throws CompositeException;

	/**
	 * Lock a resource associated with the passed in resource path
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param resourceType ResourceType
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if resource deletion fails
	 */
	public void lockResource(String serverId, String resourcePath, String resourceType, String pathToServersXML) throws CompositeException;

	/**
	 * Unlock a resource
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param resourceType ResourceType
	 * @param pathToServersXML path to the server values xml
	 * @param comment description for unlock
	 * @throws CompositeException if deletion of the resource fails
	 */	
	public void unlockResource(String serverId, String resourcePath, String resourceType, String pathToServersXML, String comment) throws CompositeException;

	/**
	 * Retrieves a resource associated with passed in resource path
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @return Resource if the resource exists else null
	 */
	public Resource getResource(String serverId,String resourcePath,String pathToServersXML);

	/**
	 * Checks for the existence of a resource
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param resourceType ResourceType
	 * @param pathToServersXML path to the server values xml
	 * @return boolean true/false based on existence
	 */
	public boolean resourceExists(String serverId, String resourcePath, String resourceType, String pathToServersXML);

	/**
	 * Get all resources of passed in resource type from passed in resource path, this method traverses the resource tree from the 
	 * starting resource path and builds a resource list of passed in resource type
	 * @param serverId target server config name
	 * @param resourcePath starting resource path if no resource path is passed /shared is defaulted to resource path
	 * @param resourceType resource type here is the list of valid resource types 
	 * CONTAINER,DATA_SOURCE,DEFINITION_SET,LINK,PROCEDURE,TABLE,TREE,TRIGGER
	 * if resourceType is not passed then all resources are returned
	 * @param detail Level resource detail Level here is the list of valid detail levels 
	 * FULL, SMIPLE, NONE. if detail level is not passed NONE is defaulted to detail level
	 * @param pathToServersXML path to the server values xml
	 * @return resource list with all resources of passed in resource type in the resource tree from the starting resource path
	 */	
	public ResourceList getResourcesFromPath(String serverId,String resourcePath, String resourceType, String resourceTypeFilter, String detailLevel,String pathToServersXML);

	/**
	 * Get all resources of passed in resource type from passed in resource path, this method builds resource list
	 * starting resource path and builds a resource list of passed in resource type
	 * @param serverId target server config name
	 * @param resourcePath starting resource path if no resource path is passed /shared is defaulted to resource path
	 * @param resourceType resource type here is the list of valid resource types 
	 * CONTAINER,DATA_SOURCE,DEFINITION_SET,LINK,PROCEDURE,TABLE,TREE,TRIGGER
	 * if resourceType is not passed then all resources are returned
	 * @param detail Level resource detail Level here is the list of valid detail levels 
	 * FULL, SMIPLE, NONE. if detail level is not passed NONE is defaulted to detail level
	 * @param pathToServersXML path to the server values xml
	 * @return resource list with immediate resources of passed in resource type in the resource tree from the starting resource path
	 */	
	public ResourceList getImmediateResourcesFromPath(String serverId,String resourcePath, String resourceType, String detailLevel,String pathToServersXML);


	/**
	 * Get the resources used by a given resourcePath
	 * 
	 * @param serverId target server config name
	 * @param resourcePath starting resource path if no resource path is passed /shared is defaulted to resource path
	 * @param resourceType resource type here is the list of valid resource types 
	 * 		CONTAINER,DATA_SOURCE,DEFINITION_SET,LINK,PROCEDURE,TABLE,TREE,TRIGGER
	 * 		if resourceType is not passed then all resources are returned
	 * @param detail Level resource detail Level here is the list of valid detail levels 
	 * 		FULL, SMIPLE, NONE. if detail level is not passed NONE is defaulted to detail level
	 * @param pathToServersXML path to the server values xml
	 * @return resource list with resources used by the incoming resource path
	 */	
	ResourceList getUsedResources(String serverId, String resourcePath,	String resourceType, String detailLevel, String pathToServersXML) throws CompositeException;

	/**
	 * Create all folders in the path associated with passed in resource path.
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @param recursive false=only create the folder specified, true=create all folders recursively
	 * @throws CompositeException if resource create folder fails (if intermediate folders do not exist and recursive=0 an exception is thrown)
	 */
	public void createFolder(String serverId, String resourcePath, String pathToServersXML, String recursive) throws CompositeException;


	/**
	 * Retrieves a resource associated with passed in resource path
	 * @param targetServer target server configuration
	 * @param resourcePath resource path
	 * @return Resource if the resource exists else null
	 */
	public Resource getResourceCompositeServer(CompositeServer targetServer, String resourcePath) throws CompositeException;

}
