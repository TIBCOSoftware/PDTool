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
