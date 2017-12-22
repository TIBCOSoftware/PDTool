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

public interface PrivilegeManager {

	/**
	 * Update Resource Privileges method updates privileges on resources for the passed in 
	 * privilege Ids list found in the the passed in Privilege.xml file for the target server Id 
	 * @param serverId target server id from servers config xml
	 * @param privilegeIds list of resource privilege Ids(comma separated data privilege Ids)
	 * @param pathToPrivilegeXML path to the privilege xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void updatePrivileges(String serverId, String privilegeIds, String pathToPrivilegeXML, String pathToServersXML) throws CompositeException;

	/**
	 * @param serverId target server id from servers config xml
	 * @param startPath starting path of the resource e.g /shared
	 * @param pathToPrivilegeXML path including name to the privilege xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * 
	 * @param filter - a filter to return all or restrict the types of resources that are returned.
	 *        The filter list is a space or comma separated list which may include one or more of the following
	 *        [ALL CONTAINER DATA_SOURCE DEFINITION_SET LINK PROCEDURE TABLE TREE TRIGGER COLUMN]
	 *        If the list contains ALL anywhere in the list then ALL resource types are returned and the rest of the list is ignored.
	 *        
	 * @param options specify behavior.
	 *        Note: admin privileges are never generated and never updated 
	 *        Options contain a space or comma separated list of one or more options to generate privileges for 
	 *           [USER GROUP SYSTEM NONSYSTEM PARENT CHILD]
	 *        
	 *   nameType
	 * 		  USER  - generate nameType=USER privileges for a given resource
	 *        GROUP - generate nameType=GROUP privileges for a given resource (Default behavior if nothing is specified)
	 *        Note: Set both USER,GROUP to generate both
	 *        
	 *   System vs. NonSystem nameTypes
	 *        SYSTEM -    generate SYSTEM nameTypes
	 *                    group=all
	 *                    users=anonymous, monitor
	 *                    
	 *        NONSYSTEM - generate NONSYSTEM nameTypes (all users and groups that are not SYSTEM) (Default behavior if nothing is specified)
	 *        Note: Set both SYSTEM,NONSYSTEM to generate both groups
	 *        
	 *   Path hierarchy
	 *        PARENT    - generate only the parent (starting path) according to the filter (Default behavior if nothing is specified)
	 *        CHILD     - generate privileges for all children of the starting path according to the filter
	 *        Note: Set both PARENT,CHILD if you want to generate the Parent along with its children
	 *        
	 * @param domainList a space or comma separate list of domains for which to generate privileges for (Default=composite)
	 * 
	 * @throws CompositeException
	 */
	public void generatePrivilegesXML(String serverId, String startPath, String pathToPrivilegeXML, String pathToServersXML, String filter, String options, String domainList) throws CompositeException;

}
