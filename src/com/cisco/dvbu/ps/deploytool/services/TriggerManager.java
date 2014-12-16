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

public interface TriggerManager {
	
	/**
	 * Update CIS triggers. The triggers must already exist for them to be updated.
	 * @param serverId target server id from servers config xml
	 * @param triggerIds comma separated list of trigger Ids(comma separated trigger Ids configured in TriggerModule.xml)
	 * @param pathToTriggersXML path to the groups xml
	 * @param pathToServersXML path to the server values xml
	 * @return void
	 */
	public void updateTriggers(String serverId, String triggerIds, String pathToTriggersXML, String pathToServersXML) throws CompositeException;

	/**
	 * Add passed in users to passed in groups associated with group ids to target server associated with passed in server Id
	 * @param serverId target server id from servers config xml
	 * @param triggerIds comma separated list of trigger Ids(comma separated trigger Ids configured in TriggerModule.xml)
	 * @param userNames comma separated user names
	 * @param pathToTriggersXML path to the groups xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void enableTriggers(String serverId, String triggerIds, String pathToTriggersXML, String pathToServersXML) throws CompositeException;
	
	/**
	 * Create existing CIS triggers to an XML file from the server associated with passed in target server id
	 * @param serverId target server id from servers config xml
	 * @param startPath the location in the namespace from which to traverse looking for trigger resources
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void generateTriggersXML(String serverId, String startPath, String pathToTriggersXML, String pathToServersXML) throws CompositeException;

}
