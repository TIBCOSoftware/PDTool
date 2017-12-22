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
package com.tibco.ps.deploytool.dao;

import com.tibco.ps.common.exception.CompositeException;
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
