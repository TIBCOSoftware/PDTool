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

import com.cisco.dvbu.ps.common.exception.CompositeException;

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
