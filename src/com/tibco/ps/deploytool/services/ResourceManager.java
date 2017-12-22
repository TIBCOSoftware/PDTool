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
package com.tibco.ps.deploytool.services;

import com.tibco.ps.common.exception.CompositeException;
import com.compositesw.services.system.admin.resource.ResourceList;

public interface ResourceManager {
	
	/**
	 * Execute a published procedure associated with passed in procedure id in resource xml along with passed in arguments and
	 * arguments should be passed in the format 'arg1','arg2','arg3'....
	 * @param serverId target server config name
	 * @param procedureIds procedure ids (comma separated values) in the resourceXML
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if the execution of the procedure fails
	 */
	public void executeConfiguredProcedures(String serverId, String procedureIds, String pathToResourceXML, String pathToServersXML) throws CompositeException;
	
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
	public void executeProcedure(String serverId,String procedureName, String dataServiceName, String pathToServersXML, String arguments) throws CompositeException;

	/**
	 * Execute a published procedure that outputs the return variable values to the log.
	 * The executed procdure is associated with passed in name along with passed in arguments and
	 * arguments should be passed in the format 'arg1','arg2','arg3'....
	 * @param serverId target server config name
	 * @param procedureName published procedure name
	 * @param dataServiceName data service name (equivalent to schema name)
	 * @param pathToServersXML path to the server values xml
	 * @param arguments string with arguments in the format 'arg1','arg2','arg3'....
	 * @param outputReturnVariables true=(default) output the values of the return variables from the procedure call, false=do not output the return variable values.
	 * @throws CompositeException if the execution of the procedure fails
	 */
	public void executeProcedure(String serverId,String procedureName, String dataServiceName, String pathToServersXML, String arguments, String outputReturnVariables) throws CompositeException;

	/**
	 * Delete a resource associated with passed in resource path 
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if deletion of the resource fails
	 */
	public void deleteResource(String serverId, String resourcePath, String pathToServersXML) throws CompositeException;

	/**
	 * Delete a resource associated with passed in resource path 
	 * @param serverId target server config name
	 * @param resourceIds comma separated list of resource ids from the resource xml
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if resource deletion fails
	 */
	public void deleteResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * Rename resources associated with passed in resource id from resource xml 
	 * @param serverId target server id from servers config xml
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @param newName the new name of the resource (this is not a path)
	 * @throws CompositeException if resource rename fails
	 */
	void renameResource(String serverId, String resourcePath, String pathToServersXML, String newName) throws CompositeException;

	/**
	 * Rename resources associated with passed in resource id from resource xml 
	 * @param serverId target server id from servers config xml
	 * @param resourceIds comma separated list of resource ids from the resource xml
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if resource rename fails
	 */
	public void renameResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * Copy a resource associated with passed in resource path, target container path and new name
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @param targetContainerPath the target CIS folder path to copy the resource to
	 * @param newName the new name of the resource being copied
	 * @param copyMode the mode by which a copy is to be executed
	 * @throws CompositeException if resource copy fails
	 */
	void copyResource(String serverId, String resourcePath, String pathToServersXML, String targetContainerPath, String newName, String copyMode) throws CompositeException;

	/**
	 * Copy a resource associated with the passed in resource ids and path
	 * @param serverId target server config name
	 * @param resourceIds comma separated list of resource ids from the resource xml
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if resource copy fails
	*/
	void copyResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * Move a resource associated with passed in resource path, target container path and new name 
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @param targetContainerPath the target CIS folder path to copy the resource to
	 * @param newName the new name of the resource being copied
	 * @throws CompositeException if resource move fails
	 */
	void moveResource(String serverId, String resourcePath, String pathToServersXML, String targetContainerPath, String newName) throws CompositeException;

	/**
	 * Move a resource associated with the passed in resource ids and path
	 * @param serverId target server config name
	 * @param resourceIds comma separated list of resource ids from the resource xml
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if resource move fails
	*/
	void moveResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * Lock a resource associated with the passed in resource path
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if resource lock fails
	 */
	void lockResource(String serverId, String resourcePath, String pathToServersXML) throws CompositeException;

	/**
	 * Lock a resource associated with the passed in resource ids and path
	 * @param serverId target server config name
	 * @param resourceIds comma separated list of resource ids from the resource xml
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if resource lock fails
	 */
	void lockResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * Unlock a resource associated with the passed in resource path
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @param comment description/comment for the unlock action
	 * @throws CompositeException if resource unlock fails
	 */
	void unlockResource(String serverId, String resourcePath, String pathToServersXML, String comment) throws CompositeException;

	/**
	 * Unlock a resource associated with the passed in resource ids and path
	 * @param serverId target server config name
	 * @param resourceIds comma separated list of resource ids from the resource xml
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if resource unlock fails
	 */
	void unlockResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * Checks for existence of a resource associated with passed in resource id
	 * @param serverId target server config name
	 * @param resourceId resource id from the resource xml
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if resource cannot be found
	 */
	public void doResourcesExist(String serverId, String resourceId,String pathToResourceXML,String pathToServersXML) throws CompositeException;

	/**
	 * Checks for existence of a resource associated with passed in resource path
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @return true if the resource exists else false
	 */
	public boolean doResourceExist(String serverId, String resourcePath,String pathToServersXML);

	/**
	 * Checks for existence of a resource associated with passed in resource path
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param resourceType resource type here is the list of valid resource types 
	 * @param pathToServersXML path to the server values xml
	 * @return true if the resource exists else false
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
	 * Get the resource type for a resource path
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @return true if the resource exists else false
	 */
	public String getResourceType(String serverId, String resourcePath,	String pathToServersXML) throws CompositeException;

	/**
	 * Create all folders in the path associated with passed in resource path.
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @param recursive false=only create the folder specified, true=create all folders recursively
	 * @throws CompositeException if resource create folder fails (if intermediate folders do not exist and recursive=0 an exception is thrown)
	 */
	void createFolder(String serverId, String resourcePath, String pathToServersXML, String recursive) throws CompositeException;

	/**
	 * Create all folders in the path associated with passed in resource path and ignore any errors.
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @param recursive false=only create the folder specified, true=create all folders recursively
	 * @param ignoreErrors true=ignore any errors that are thrown. false/null=Default. Do not ignore errors.
	 * @throws CompositeException if resource create folder fails (if intermediate folders do not exist and recursive=0 an exception is thrown)
	 */
	void createFolder(String serverId, String resourcePath, String pathToServersXML, String recursive, String ignoreErrors) throws CompositeException;

	/**
	 * Create all folders in the path associated with the passed in resource ids.
	 * @param serverId target server config name
	 * @param resourceIds comma separated list of resource ids from the resource xml
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if resource move fails
	*/
	void createFolders(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException;

}
