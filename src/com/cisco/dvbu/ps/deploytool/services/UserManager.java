/*******************************************************************************
* Copyright (c) 2014 Cisco Systems
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* PDTool project commiters - initial release
*******************************************************************************/
/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.services;

import com.cisco.dvbu.ps.common.exception.CompositeException;

/* Debug within Eclipse:
 * 
generateUsersXML 	localhost 	composite  	resources/modules/getUserModule.xml resources/modules/servers.xml

createOrUpdateUsers localhost  user1  		resources/modules/UserModule.xml resources/modules/servers.xml

deleteUsers 		localhost  user1  		resources/modules/UserModule.xml resources/modules/servers.xml
 */

public interface UserManager {
	
	/**
	 * Create CIS users. If they already exist, update them instead. 
	 * @param serverId - target server name
	 * @param userIds - comma separated list of user names to create or update.
	 * @param pathToUsersXML - path including name to the users XML containing a list of UserIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @return void
	 * @throws CompositeException
	 */
	public void createOrUpdateUsers(String serverId, String userIds, String pathToUsersXML, String pathToServersXML) throws CompositeException;

	/**
	 * Delete CIS users from a specified domain.
	 * @param serverId - target server name
	 * @param userIds - comma separated list of user names to be deleted.
	 * @param pathToUsersXML - path including name to the users XML containing a list of UserIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @return void
	 * @throws CompositeException
	 */
	public void deleteUsers(String serverId, String userIds, String pathToUsersXML, String pathToServersXML) throws CompositeException;
	
	/**
	 * Export existing CIS users to a XML file based on the list of passed in user ids and server id
	 * @param serverId - target server name
	 * @param doaminName - domain name from which to get a list of users.  If null get all users from all domains.
	 * @param pathToUsersXML - path including name to the users XML which will get created
	 * @param pathToServersXML - path to the server values XML
	 * @return void
	 * @throws CompositeException
	 */
	public void generateUsersXML(String serverId, String doaminName, String pathToUsersXML, String pathToServersXML) throws CompositeException;

}
