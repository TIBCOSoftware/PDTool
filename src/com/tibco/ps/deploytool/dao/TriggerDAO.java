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

import java.math.BigInteger;

import com.tibco.ps.common.exception.CompositeException;
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
