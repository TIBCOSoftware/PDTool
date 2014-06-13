/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
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
