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
package com.cisco.dvbu.ps.deploytool.dao;

import java.math.BigInteger;

import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.compositesw.services.system.admin.resource.ResourceList;
import com.compositesw.services.system.admin.resource.Schedule;
import com.compositesw.services.system.util.common.AttributeList;

public interface TriggerDAO {

	public static enum action {UPDATE,ENABLE};

	/**
	 * Take Trigger Action method takes passed action on the trigger with the passed in trigger 
	 * attributes for the passed in target server name 
	 * @param actionName name of action to perform on trigger
	 * @param triggerPath trigger path
	 * @param triggerId the trigger id from TriggerModule.xml
	 * @param isEnabled flag to indicate if trigger should be enabled
	 * @param maxEventsQueued number of events to queue up
	 * @param conditionType one of SYSTEM_EVENT, USER_DEFINED, JMS or TIMER
	 * @param conditionAttributes attributes concerning condition configuration
	 * @param conditionSchedule timer information if trigger is invoked by timer
	 * @param actionType one of EMAIL, REINTROSPECT, PROCEDURE or STATISTICS
	 * @param actionAttributes attributes concerning the action configuration
	 * @param serverId target server config name
	 * @param pathToServersXML path to the server values xml
	 */
	public ResourceList takeTriggerAction(String actionName, String triggerPath, String triggerId, String isEnabled, BigInteger maxEventsQueued, String annotation, String conditionType, AttributeList conditionAttributes, Schedule conditionSchedule, String actionType, AttributeList actionAttributes, String serverId, String pathToServersXML) throws CompositeException;


}
