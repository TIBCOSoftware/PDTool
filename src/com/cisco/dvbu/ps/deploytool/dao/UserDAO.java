/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao;

import java.util.ArrayList;

import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.compositesw.services.system.admin.user.DomainMemberReferenceList;
import com.compositesw.services.system.admin.user.UserList;

public interface UserDAO {

	public static enum action {CREATEORUPDATE,CREATE,UPDATE,DELETE};

	/**
	 * This method takes the passed in action and executes it on the user 
	 * @param actionName - the action to execute [CREATE,UPDATE,DELETE]
	 * @param userName - the name of the user to act upon
	 * @param oldPassword - the old password if changing the password
	 * @param password - the new or current password if setting the password
	 * @param domainName - CIS user domain
	 * @param groupNames - A list of groups which contain group name and domain
	 * @param explicitRights - a comma separate list of user rights
	 * @param annotation - a descriptive annotation set for the user
	 * @param serverId target server id from servers config XML
	 * @param pathToServersXML path to the server values XML
	 * @throws CompositeException
	 */
	public void takeUserAction(String actionName, String userName, String oldPassword, String password, String domainName, DomainMemberReferenceList groupNames, String explicitRights, String annotation, String serverName, String pathToServersXML) throws CompositeException;

	/**
	 * This method gets all the users for a given domain and server instance
	 * @param validUsers - A valid array of users to get from CIS if they exist
	 * @param domainName - CIS user domain
	 * @param serverId target server id from servers config XML
	 * @param pathToServersXML - path to the server values XML
	 * @throws CompositeException
	 */
	UserList getAllUsers(ArrayList<String> validUsers, String domainName, String serverId, String pathToServersXML) throws CompositeException;

	/**
	 * This method gets a specific user for a given domain and server instance
	 * @param userName - CIS username
	 * @param domainName - CIS user domain
	 * @param serverId target server id from servers config XML
	 * @param pathToServersXML - path to the server values XML
	 * @throws CompositeException
	 */
	UserList getUsers(String userName, String domainName, String serverId, String pathToServersXML) throws CompositeException;

}
