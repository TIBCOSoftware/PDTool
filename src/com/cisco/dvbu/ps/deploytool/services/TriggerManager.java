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
