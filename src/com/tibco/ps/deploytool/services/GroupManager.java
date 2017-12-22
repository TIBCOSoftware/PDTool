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

public interface GroupManager {
	/**
	 * Create CIS groups. If they already exist, update them instead.
	 * @param serverId target server id from servers config xml
	 * @param groupIds comma separated list of group Ids(comma separated group Ids configured in GroupXML)
	 * @param pathToGroupsXML path to the groups xml
	 * @param pathToServersXML path to the server values xml
	 * @return void
	 */
	public void createOrUpdateGroups(String serverId, String groupIds, String pathToGroupsXML, String pathToServersXML) throws CompositeException;

	/**
	 * We need a deleteGroups action because group objects are not considered resources in CIS, so the deletResources
	 * action will not work for groups.
	 *
	 * @param serverId target server id from servers config xml
	 * @param groupIds comma separated list of group Ids(comma separated group Ids configured in GroupXML)
	 * @param pathToGroupsXML path to the groups xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void deleteGroups(String serverId, String groupIds, String pathToGroupsXML, String pathToServersXML) throws CompositeException;

	/**
	 * Add passed in users to passed in groups associated with group ids to target server associated with passed in server Id
	 * @param serverId target server id from servers config xml
	 * @param groupIds comma separated list of group Ids(comma separated group Ids configured in GroupXML)
	 * @param userNames comma separated user names
	 * @param pathToGroupsXML path to the groups xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void addUsersToGroups(String serverId, String groupIds, String userNames,String pathToGroupsXML, String pathToServersXML) throws CompositeException;

	/**
	 * Delete passed in users from passed in groups associated with group ids to target server associated with passed in server Id
	 * @param serverId target server id from servers config xml
	 * @param groupIds comma separated list of group Ids(comma separated group Ids configured in GroupXML)
	 * @param userNames comma separated user names like username1,username2
	 * @param pathToGroupsXML path to the groups xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void deleteUsersFromGroups(String serverId, String groupIds, String userNames,String pathToGroupsXML, String pathToServersXML) throws CompositeException;
	
	/**
	 * Create existing CIS groups  to an XML file from the server associated with passed in target server id
	 * @param serverId target server id from servers config xml
	 * @param domain domain name
	 * if domain is not passed then all groups are included
	 * @param pathToGroupsXML path including name to the groups xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void generateGroupsXML(String serverId,String domainName,String pathToGroupsXML, String pathToServersXML) throws CompositeException;

}
