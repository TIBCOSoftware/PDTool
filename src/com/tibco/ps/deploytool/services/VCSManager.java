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
	 * Initialize base folder checkin from the local workspace to the VCS repository.  This provides an alternative way to establish the Composite repository base folders
	 * into the VCS repository without checking in the entire Composite repository.  This can be useful for multi-tenant environments where only certain folders will be
	 * held under version control.  The issue is that all the base-level folders must first be checked in into prior to any user-level folders being checked in.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param customPathList - a comma separated list of paths that are added to the base paths of /shared or /services/databases or /services/webservices 
	 *                         these paths and their corresponding .cmf file will be created during initialization of the workspace and vcs repository. 
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsInitializeBaseFolderCheckin(String customPathList, String vcsUser, String vcsPassword) throws CompositeException;


	/**
	 * Initialize base folder checkin from the local workspace to the VCS repository.  This provides an alternative way to establish the Composite repository base folders
	 * into the VCS repository without checking in the entire Composite repository.  This can be useful for multi-tenant environments where only certain folders will be
	 * held under version control.  The issue is that all the base-level folders must first be checked in into prior to any user-level folders being checked in.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param vcsConnectionId - VCS Connection property information
	 * @param customPathList - a comma separated list of paths that are added to the base paths of /shared or /services/databases or /services/webservices 
	 *                         these paths and their corresponding .cmf file will be created during initialization of the workspace and vcs repository. 
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsInitializeBaseFolderCheckin2(String vcsConnectionId, String customPathList, String pathToVcsXML, String vcsUser, String vcsPassword) throws CompositeException;


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
	 * Initialize base folder checkin from the local workspace to the VCS repository.  This provides an alternative way to establish the Composite repository base folders
	 * into the VCS repository without checking in the entire Composite repository.  This can be useful for multi-tenant environments where only certain folders will be
	 * held under version control.  The issue is that all the base-level folders must first be checked in into prior to any user-level folders being checked in.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param customPathList - a comma separated list of paths that are added to the base paths of /shared or /services/databases or /services/webservices 
	 *                         these paths and their corresponding .cmf file will be created during initialization of the workspace and vcs repository.
	 * @param vcsCheckinOptions - a space separated list of check-in options that are put on the VCS command line at the time of execution.  
	 * 							  However, if a value exists in the studio.properties file, these values are not used. 
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */	
	public void vcsStudioInitializeBaseFolderCheckin(String customPathList, String vcsCheckinOptions, String vcsUser, String vcsPassword) throws CompositeException;

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
	
	/**
	 *  This method handles scanning the Composite path and searching for encoded paths
	 *  that equal or exceed the windows 259 character limit.  If found this routine reports those paths.
	 *  The 259 character limit is only a limitation for windows-based implementations of VCS
	 *  like TFS.  Subversion does not have this issue.
	 *  
	 * @param serverId - target server name
	 * @param vcsMaxPathLength - a positive integer length from which to compare path lengths found in vcsResourcePathList.  When 0, use the default CommonConstants.maxWindowsPathLen=259.
	 * @param vcsResourcePathList -  a comma separated list of CIS resource paths to scan
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * 			[Optional parameter when values are set in studio.properties, deploy.properties or VCSModule.xml.  pass in null.]
	 * @param vcsPassword - the VCS user passed in from the command line
	 * 			[Optional parameter when values are set in studio.properties, deploy.properties or VCSModule.xml.  pass in null.]
	 * @throws CompositeException
	 */
	public void vcsScanPathLength(String serverId, int vcsMaxPathLength, String vcsResourcePathList, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 *  This method handles scanning the Composite path and searching for encoded paths
	 *  that equal or exceed the windows 259 character limit.  If found this routine reports those paths.
	 *  The 259 character limit is only a limitation for windows-based implementations of VCS
	 *  like TFS.  Subversion does not have this issue.
	 *  
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information 
	 * @param vcsMaxPathLength - a positive integer length from which to compare path lengths found in vcsResourcePathList.  When 0, use the default CommonConstants.maxWindowsPathLen=259.
	 * @param vcsResourcePathList -  a comma separated list of CIS resource paths to scan
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against. 
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * 			[Optional parameter when values are set in studio.properties, deploy.properties or VCSModule.xml.  pass in null.]
	 * @param vcsPassword - the VCS user passed in from the command line
	 * 			[Optional parameter when values are set in studio.properties, deploy.properties or VCSModule.xml.  pass in null.]
	 * @throws CompositeException
	 */
	public void vcsScanPathLength2(String serverId, String vcsConnectionId, int vcsMaxPathLength, String vcsResourcePathList, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

}
