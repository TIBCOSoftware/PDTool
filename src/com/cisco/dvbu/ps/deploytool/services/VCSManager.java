/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.services;

import com.cisco.dvbu.ps.common.exception.CompositeException;

/* Debug within Eclipse:
 * 
vcsInitWorkspace		vcsuser vcspassword
vcsCheckout    			localhost9410 "testNN" 		resources/modules/LabVCSModule.xml resources/modules/servers.xml
vcsCheckin    			localhost9410 "testNN" 		resources/modules/LabVCSModule.xml resources/modules/servers.xml
vcsForcedCheckin 		localhost9410 "testNN" 		resources/modules/LabVCSModule.xml resources/modules/servers.xml
vcsPrepareCheckin		localhost9410 "testNN" 		resources/modules/LabVCSModule.xml resources/modules/servers.xml
 */

public interface VCSManager {
	
	public static enum action {VCSINIT,CHECKIN,CHECKOUT,FORCED_CHECKIN,PREPARE_CHECKIN};

	/*******************************************************
	 * 
	 * PDTOOL VCS INTEGRATION METHODS
	 *
	 *******************************************************/

	/**
	 * Initialize the VCS local workspace on the deployment server by linking a local folder with a VCS repository project
	 * and checking out all the resources from the VCS repository into the local workspace folder.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsInitWorkspace(String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Initialize the VCS local workspace on the deployment server by linking a local folder with a VCS repository project
	 * and checking out all the resources from the VCS repository into the local workspace folder.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param vcsConnectionId - VCS Connection property information
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	public void vcsInitWorkspace2(String vcsConnectionId, String pathToVcsXML, String vcsUser, String vcsPassword) throws CompositeException;


	/**
	 * Checkout changes from the repository and import the differences into the CIS server.
	 * If folders are present in CIS that are not present in the repository, those folders will be deleted in CIS.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsResourcePath - the actual CIS resource path (not encoded)
	 * @param vcsResourceType - the resource type
	 * @param vcsRevision - the revision from the VCS (HEAD or an integer value representing the revision number.)
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsCheckout(String serverId, String vcsResourcePath, String vcsResourceType, String vcsRevision, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * This method contains an additional parameter "vcsLabel", therefore resourcePath and resourceType are left null.
	 * 
	 * Checkout changes from the repository and import the differences into the CIS server.
	 * If folders are present in CIS that are not present in the repository, those folders will be deleted in CIS.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsResourcePath - the actual CIS resource path (not encoded)
	 * @param vcsResourceType - the resource type
	 * @param vcsLabel - the VCS label that associates an entire release of CIS resources together.  If not null, then vcsResourcePath and vcsResourceType must be null otherwise vcsResourcePath takes precedence.
	 * @param vcsRevision - the revision from the VCS (HEAD or an integer value representing the revision number.)
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsCheckout(String serverId, String vcsResourcePath, String vcsResourceType,  String vcsLabel, String vcsRevision, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;


	/**
	 * Checkout changes from the repository and import the differences into the CIS server.
	 * If folders are present in CIS that are not present in the repository, those folders will be deleted in CIS.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsResourcePath - the actual CIS resource path (not encoded)
	 * @param vcsResourceType - the resource type
	 * @param vcsRevision - the revision from the VCS (HEAD or an integer value representing the revision number.)
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	public void vcsCheckout2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String vcsRevision, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * This method contains an additional parameter "vcsLabel", therefore resourcePath and resourceType are left null.
	 * 
	 * Checkout changes from the repository and import the differences into the CIS server.
	 * If folders are present in CIS that are not present in the repository, those folders will be deleted in CIS.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsResourcePath - the actual CIS resource path (not encoded)
	 * @param vcsResourceType - the resource type
	 * @param vcsLabel - the VCS label that associates an entire release of CIS resources together.  If not null, then vcsResourcePath and vcsResourceType must be null otherwise vcsResourcePath takes precedence.
	 * @param vcsRevision - the revision from the VCS (HEAD or an integer value representing the revision number.)
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	public void vcsCheckout2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String vcsLabel, String vcsRevision, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * References multiple vcsIds from the VCSModule.xml to checkout changes from the repository and import the differences into the CIS server.
	 * If folders are present in CIS that are not present in the repository, those folders will be deleted in CIS.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsIds - comma separated list of VCS identifiers.
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsCheckouts(String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;
	
	/**
	 * References multiple vcsIds from the VCSModule.xml to checkout changes from the repository and import the differences into the CIS server.
	 * If folders are present in CIS that are not present in the repository, those folders will be deleted in CIS.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsIds - comma separated list of VCS identifiers.
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	public void vcsCheckouts2(String serverId, String vcsConnectionId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Checkin the changes from the local workspace to the VCS repository.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsResourcePath - the actual CIS resource path (not encoded)
	 * @param vcsResourceType - the resource type
	 * @param vcsMessage - the message that describes the checkin
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsCheckin(String serverId, String vcsResourcePath, String vcsResourceType, String vcsMessage, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Checkin the changes from the local workspace to the VCS repository.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsResourcePath - the actual CIS resource path (not encoded)
	 * @param vcsResourceType - the resource type
	 * @param vcsMessage - the message that describes the checkin
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	public void vcsCheckin2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String vcsMessage, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * References multiple vcsIds in the VCSModule.xml to checkin the changes from the local workspace to the VCS repository.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsIds - comma separated list of VCS identifiers.
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsCheckins(String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * References multiple vcsIds in the VCSModule.xml to checkin the changes from the local workspace to the VCS repository.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsIds - comma separated list of VCS identifiers.
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	public void vcsCheckins2(String serverId, String vcsConnectionId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Forced checkin based on what is in the local workspace for CIS vs. the repository.  
	 * Overwrite differences in the VCS repository favor of what is in the CIS server. 
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsResourcePath - the actual CIS resource path (not encoded)
	 * @param vcsResourceType - the resource type
	 * @param vcsMessage - the message that describes the checkin
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsForcedCheckin(String serverId, String vcsResourcePath, String vcsResourceType, String vcsMessage, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Forced checkin based on what is in the local workspace for CIS vs. the repository.  
	 * Overwrite differences in the VCS repository favor of what is in the CIS server. 
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsResourcePath - the actual CIS resource path (not encoded)
	 * @param vcsResourceType - the resource type
	 * @param vcsMessage - the message that describes the checkin
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	public void vcsForcedCheckin2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String vcsMessage, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * References multiple vcsIds in the VCSModule.xml to force checkin based on what is in the local workspace for CIS vs. the repository.  
	 * Overwrite differences in the VCS repository favor of what is in the CIS server. 
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsIds - comma separated list of VCS identifiers.
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsForcedCheckins(String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * References multiple vcsIds in the VCSModule.xml to force checkin based on what is in the local workspace for CIS vs. the repository.  
	 * Overwrite differences in the VCS repository favor of what is in the CIS server. 
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsIds - comma separated list of VCS identifiers.
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	public void vcsForcedCheckins2(String serverId, String vcsConnectionId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Prepare checkin by updating the local workspace copy and comparing with VCS but don't checkin.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsResourcePath - comma separated list of VCS identifiers.
	 * @param vcsResourceType - the resource type
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsPrepareCheckin(String serverId, String vcsResourcePath, String vcsResourceType, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Prepare checkin by updating the local workspace copy and comparing with VCS but don't checkin.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsResourcePath - comma separated list of VCS identifiers.
	 * @param vcsResourceType - the resource type
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	public void vcsPrepareCheckin2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * References multiple vcsIds in the VCSModule.xml to prepare checkin by updating the local workspace copy and comparing with VCS but don't checkin.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsIds - comma separated list of VCS identifiers.
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsPrepareCheckins(String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * References multiple vcsIds in the VCSModule.xml to prepare checkin by updating the local workspace copy and comparing with VCS but don't checkin.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsIds - comma separated list of VCS identifiers.
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	public void vcsPrepareCheckins2(String serverId, String vcsConnectionId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;	

	/**
	 * Generate a VCS Module XML
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId target server id from servers config xml
	 * @param startPath starting path of the resource e.g /shared
	 * @param pathToDataSourceXML path including name to the VCS source xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void generateVCSXML(String serverId, String startPath, String pathToVCSXML, String pathToServersXML) throws CompositeException;

	/**
	 * Generate a VCS Module XML
	 *
	 * @param serverId target server id from servers config xml
	 * @param vcsConnectionId - VCS Connection property information
	 * @param startPath starting path of the resource e.g /shared
	 * @param pathToDataSourceXML path including name to the VCS source xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void generateVCSXML2(String serverId, String vcsConnectionId, String startPath, String pathToVcsXML, String pathToServersXML) throws CompositeException;


	/*******************************************************
	 * 
	 * PDTOOL STUDIO VCS INTEGRATION METHODS
	 *
	 *******************************************************/
	
	/**
	 * Initialize the VCS local workspace on the deployment server by linking a local folder with a VCS repository project
	 * and checking out all the resources from the VCS repository into the local workspace folder.
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */	
	public void vcsStudioInitWorkspace(String vcsUser, String vcsPassword) throws CompositeException;
	
	/**
	 * Composite Studio integrates with vcsStudioCheckout to checkout the changes from the repository and import the differences into the CIS server.
	 * If folders are present in CIS that are not present in the repository, those folders will be deleted in CIS.
	 * 
	 * @param resourcePath - CIS resource path 				(e.g. /shared/MyFolder/My__View), using file system (encoded) names
	 * @param resourceType - CIS resource type 				(e.g. FOLDER, table, procedure etc.)
	 * @param revision - checkout revision					(e.g. HEAD or 3177)
	 * @param vcsWorkspace - path to the workspace folder 	(e.g. C:\Temp\workspaces\workspace_CIS)
	 * @param vcsWorkspaceTemp - path to the workspace temp (e.g. C:\Temp\workspaces\temp_CIS)
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsStudioCheckout(String resourcePath, String resourceType, String revision, String vcsWorkspace, String vcsWorkspaceTemp) throws CompositeException;
	
	/**
	 * Composite Studio integrates with vcsStudioCheckin to checkin the changes from the local Studio workspace to the VCS repository.
	 * 
	 * @param resourcePath - CIS resource path 				(e.g. /shared/MyFolder/My__View), using file system (encoded) names
	 * @param resourceType - CIS resource type 				(e.g. FOLDER, table, procedure etc.)
	 * @param message - checkin message						(e.g. Adding My View)
	 * @param vcsWorkspace - path to the workspace folder 	(e.g. C:\Temp\workspaces\workspace_CIS)
	 * @param vcsWorkspaceTemp - path to the workspace temp (e.g. C:\Temp\workspaces\temp_CIS)
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsStudioCheckin(String resourcePath, String resourceType, String message, String vcsWorkspace, String vcsWorkspaceTemp) throws CompositeException;

	/**
	 * Composite Studio integrates with vcsStudioForcedCheckin to force a checkin of the changes from the local Studio workspace to the VCS repository.
	 * Force checkin based on what is in the local workspace for CIS vs. the repository.  
	 * Overwrite differences in the VCS repository favor of what is in the CIS server. 
	 * 
	 * @param resourcePath - CIS resource path 				(e.g. /shared/MyFolder/My__View), using file system (encoded) names
	 * @param resourceType - CIS resource type 				(e.g. FOLDER, table, procedure etc.)
	 * @param message - checkin message						(e.g. Adding My View)
	 * @param vcsWorkspace - path to the workspace folder 	(e.g. C:\Temp\workspaces\workspace_CIS)
	 * @param vcsWorkspaceTemp - path to the workspace temp (e.g. C:\Temp\workspaces\temp_CIS)
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsStudioForcedCheckin(String resourcePath, String resourceType, String message, String vcsWorkspace, String vcsWorkspaceTemp) throws CompositeException;
	
}
