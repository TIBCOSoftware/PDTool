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
