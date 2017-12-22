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
