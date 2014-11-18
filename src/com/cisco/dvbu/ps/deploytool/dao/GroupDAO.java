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
package com.cisco.dvbu.ps.deploytool.dao;

import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.compositesw.services.system.admin.user.Group;
import com.compositesw.services.system.admin.user.GroupList;

public interface GroupDAO {

	public static enum action {CREATEORUPDATE,CREATE, UPDATE, DELETE, ADDUSER, REMOVEUSER};

	/**
	 * This method takes passed action on the group with the passed in group attributes for the passed in
	 * target server Id
	 * @param actionName action name
	 * @param groupName group name
	 * @param groupDomain Group Domain
	 * @param userName userName
	 * @param privileges privileges
	 * @param serverId target server id from servers config xml
	 * @param pathToServersXML path to the server values xml
	 */
	public void takeGroupAction(String actionName, String groupName, String groupDomain, String userName, String privileges, String serverId, String pathToServersXML) throws CompositeException;

	/**
	 * Retrieve group associated with passed in group name in passed in domain
	 * @param groupName Group Name
	 * @param groupDomain Domain Name
	 * @param serverId target server id from servers config xml
	 * @param pathToServersXML path to the server values xml
	 * @return group associated with passed in group name in passed in domain
	 */
	public Group getGroup (String groupName, String groupDomain, String serverId, String pathToServersXML);

	/**
	 * Retrieve group associated with passed in group domain
	 * if groupDomain is null then all groups will be returned
	 * @param groupDomain Domain Name
	 * @param serverId target server id from servers config xml
	 * @param pathToServersXML path to the server values xml
	 * @return group associated with passed in domain, if groupDomain is null then all groups will be returned
	 */
	public GroupList getAllGroups (String groupDomain, String serverId, String pathToServersXML);

	/**
	 * This method gets a list of groups for a given user, domain and server instance
	 * @param userName - CIS username
	 * @param domainName - CIS user domain
	 * @param serverId - target server configuration name
	 * @param pathToServersXML - path to the server values XML
	 * @throws CompositeException
	 */
	GroupList getGroupsByUser(String userName, String domainName, String serverId, String pathToServersXML) throws CompositeException;;

}
