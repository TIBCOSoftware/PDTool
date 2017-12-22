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
