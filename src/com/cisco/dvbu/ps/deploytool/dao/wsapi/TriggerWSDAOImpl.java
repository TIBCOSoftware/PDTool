/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao.wsapi;

import java.math.BigInteger;

//import javax.jws.WebParam;





import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.exception.ValidationException;
import com.cisco.dvbu.ps.common.util.CompositeLogger;
import com.cisco.dvbu.ps.common.util.wsapi.CisApiFactory;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.DeployManagerUtil;
import com.cisco.dvbu.ps.deploytool.dao.TriggerDAO;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.compositesw.services.system.admin.GetResourceSoapFault;
import com.compositesw.services.system.admin.ResourcePortType;
import com.compositesw.services.system.admin.UpdateTriggerSoapFault;
import com.compositesw.services.system.admin.resource.ImpactLevel;
import com.compositesw.services.system.admin.resource.Resource;
import com.compositesw.services.system.admin.resource.ResourceList;
import com.compositesw.services.system.admin.resource.ResourceType;
import com.compositesw.services.system.admin.resource.Schedule;
import com.compositesw.services.system.util.common.Attribute;
import com.compositesw.services.system.util.common.AttributeList;
import com.compositesw.services.system.util.common.DetailLevel;

/**
 * @author kobrien
 *
 */
public class TriggerWSDAOImpl implements TriggerDAO {

	private static Log logger = LogFactory.getLog(TriggerWSDAOImpl.class);


	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.TriggerDAO#takeTriggerAction(java.lang.String, java.lang.String, com.compositesw.services.system.util.common.AttributeList, java.lang.String, java.lang.String)
	 */
//	@Override
	public ResourceList takeTriggerAction(String actionName, String triggerPath, String triggerId, String isEnabled, BigInteger maxEventsQueued, String annotation, 
			String conditionType, AttributeList conditionAttributes, Schedule conditionSchedule, String actionType, AttributeList actionAttributes,
			String serverId, String pathToServersXML) throws CompositeException {
		
		// For debugging
		String annotationStr = (annotation == null) ? null : "\""+annotation+"\"";
		String scheduleText = "";
		String conditionAttrText = "";
		String actionAttrText = "";
		if(logger.isDebugEnabled()) {
			int conditionAttrSize = 0;
			int scheduleCount = 0;
			int actionAttrSize = 0;
			String scheduleInterval = null;
			String scheduleRecurringDay = null;
			String scheduleEndTimeInADay = null;
			String scheduleFromTimeInADay = null;
			String scheduleMode = null;
			String schedulePeriod = null;
			String scheduleStartTime = null;

			// Check the refresh entry
			if (conditionSchedule != null) {
				if (conditionSchedule.getCount() != null)
					scheduleCount = conditionSchedule.getCount();
				if (conditionSchedule.getInterval() != null)
					scheduleInterval = conditionSchedule.getInterval().toString();
				if (conditionSchedule.getRecurringDay() != null)
					scheduleRecurringDay = conditionSchedule.getRecurringDay().toString();
				if (conditionSchedule.getEndTimeInADay() != null)
					scheduleEndTimeInADay = conditionSchedule.getEndTimeInADay().toString();
				if (conditionSchedule.getFromTimeInADay() != null)
					scheduleFromTimeInADay = conditionSchedule.getFromTimeInADay().toString();
				if (conditionSchedule.getMode() != null)
					scheduleMode = conditionSchedule.getMode().toString();
				if (conditionSchedule.getPeriod() != null)
					schedulePeriod = conditionSchedule.getPeriod().toString();
				if (conditionSchedule.getStartTime() != null)
					scheduleStartTime = conditionSchedule.getStartTime().toString();
				scheduleText = scheduleText + "\n                    conditionSchedule:";
				scheduleText = scheduleText + "\n                                       Count=" + scheduleCount;
				scheduleText = scheduleText + "\n                                    Interval=" + scheduleInterval;
				scheduleText = scheduleText + "\n                              RecurringDaynt=" + scheduleRecurringDay;
				scheduleText = scheduleText + "\n                               EndTimeInADay=" + scheduleEndTimeInADay;
				scheduleText = scheduleText + "\n                              FromTimeInADay=" + scheduleFromTimeInADay;
				scheduleText = scheduleText + "\n                                        Mode=" + scheduleMode;
				scheduleText = scheduleText + "\n                                      Period=" + schedulePeriod;
				scheduleText = scheduleText + "\n                                   StartTime=" + scheduleStartTime+"\n";
			}
			if (conditionAttributes != null && conditionAttributes.getAttribute() != null) {
				conditionAttrSize = conditionAttributes.getAttribute().size();
			for (Attribute attr:conditionAttributes.getAttribute()) {
					if (conditionAttrText.length() != 0)
						conditionAttrText = conditionAttrText + ", ";
					if (attr.getType().toString().equals("PASSWORD_STRING"))
						conditionAttrText = conditionAttrText + attr.getName() + "=********";
					else
						conditionAttrText = conditionAttrText + attr.getName() + "=" + attr.getValue();
				}
			}
			if (actionAttributes != null && actionAttributes.getAttribute() != null) {
				actionAttrSize = actionAttributes.getAttribute().size();
				for (Attribute attr:actionAttributes.getAttribute()) {
					if (actionAttrText.length() != 0)
						actionAttrText = actionAttrText + ", ";
					if (attr.getType().toString().equals("PASSWORD_STRING"))
						actionAttrText = actionAttrText + attr.getName() + "=********";
					else
						actionAttrText = actionAttrText + attr.getName() + "=" + attr.getValue();
				}
			}
			
			logger.debug("TriggerWSDAOImpl.takeTriggerAction(actionName, triggerPath, triggerId, isEnabled, maxEventsQueued, annotation, conditionType, conditionAttributes, conditionSchedule, actionType, actionAttributes, serverId, pathToServersXML).  " +
					"actionName="+actionName+"  triggerPath="+triggerPath+"  triggerId="+triggerId+"  isEnabled="+isEnabled+"  maxEventsQueued="+maxEventsQueued+"  annotation="+annotationStr+
					"  conditionType="+conditionType+"  #conditionAttributes="+conditionAttrSize+", conditionSchedule, #actionAttributes="+actionAttrSize+
					"  serverId="+serverId+"  pathToServersXML="+pathToServersXML);
		}

		if(logger.isDebugEnabled()){
			logger.debug("Entering TriggerWSDAOImpl.takeTriggerAction() with following params "+  "actionName: " + actionName + "conditionType: " + conditionType + " conditionAttributes: " + conditionAttributes + " serverId: "+serverId + ", pathToServersXML: " + pathToServersXML);
		}
		
		ResourceList returnResList = null;
		ResourceList resourceReturnList = null;
		
		AttributeList attributes = null;

		// read target server properties from xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "TriggerWSDAOImpl.takeTriggerAction("+actionName+")", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			// Make sure the resource exists before executing any actions
			if (DeployManagerUtil.getDeployManager().resourceExists(serverId, triggerPath, ResourceType.TRIGGER.name(), pathToServersXML)) {
				boolean triggerProcessed = false;
				
				if(actionName.equalsIgnoreCase(TriggerDAO.action.UPDATE.name())){
					if (isEnabled == null) {
						if(logger.isDebugEnabled()) {
							logger.debug("TriggerWSDAOImpl.takeTriggerAction(\""+actionName+"\").  Invoking port.updateTrigger(\""+triggerPath+"\", \"FULL\", null, \""+conditionType+"\", "+scheduleText+", conditionAttributes=["+conditionAttrText+"], \""+actionType+"\", actionAttributes=["+actionAttrText+"], "+maxEventsQueued+", "+annotationStr+", attributes).");
						}

						returnResList = port.updateTrigger(triggerPath, DetailLevel.FULL, null, conditionType, conditionSchedule, conditionAttributes, 
								actionType, actionAttributes, maxEventsQueued, annotation, attributes);

						if(logger.isDebugEnabled()) {
							logger.debug("TriggerWSDAOImpl.takeTriggerAction(\""+actionName+"\").  Success: port.updateTrigger().");
						}
					} else {
						if(logger.isDebugEnabled()) {
							logger.debug("TriggerWSDAOImpl.takeTriggerAction(\""+actionName+"\").  Invoking port.updateTrigger(\""+triggerPath+"\", \"FULL\", "+isEnabled+", \""+conditionType+"\", "+scheduleText+", conditionAttributes=["+conditionAttrText+"], \""+actionType+"\", actionAttributes=["+actionAttrText+"], "+maxEventsQueued+", "+annotationStr+", attributes).");
						}
						
						returnResList = port.updateTrigger(triggerPath, DetailLevel.FULL, Boolean.valueOf(isEnabled), conditionType, conditionSchedule, conditionAttributes, 
								actionType, actionAttributes, maxEventsQueued, annotation, attributes);				
	
						if(logger.isDebugEnabled()) {
							logger.debug("TriggerWSDAOImpl.takeTriggerAction(\""+actionName+"\").  Success: port.updateTrigger().");
						}
					}
					triggerProcessed = true;
				}
				
				if(actionName.equalsIgnoreCase(TriggerDAO.action.ENABLE.name())) {
					logger.debug("Invoking trigger enable for trigger id: " + triggerId + " with enabled flag: " + isEnabled);
					if (isEnabled == null) {
						if(logger.isDebugEnabled()) {
							logger.debug("TriggerWSDAOImpl.takeTriggerAction(\""+actionName+"\").  Invoking  port.updateTrigger(\""+triggerPath+"\", \"FULL\", null, null, null, null, null, null, null, null, null).");
						}
						
						returnResList = port.updateTrigger(triggerPath, DetailLevel.FULL, null, null, null, null, null, null, null, null, null);

						if(logger.isDebugEnabled()) {
							logger.debug("TriggerWSDAOImpl.takeTriggerAction(\""+actionName+"\").  Success: port.updateTrigger().");
						}
					} else {
						if(logger.isDebugEnabled()) {
							logger.debug("TriggerWSDAOImpl.takeTriggerAction(\""+actionName+"\").  Invoking  port.updateTrigger(\""+triggerPath+"\", \"FULL\", "+isEnabled+", null, null, null, null, null, null, null, null).");
						}
						
						returnResList = port.updateTrigger(triggerPath, DetailLevel.FULL, Boolean.valueOf(isEnabled), null, null, null, null, null, null, null, null);			

						if(logger.isDebugEnabled()) {
							logger.debug("TriggerWSDAOImpl.takeTriggerAction(\""+actionName+"\").  Success: port.updateTrigger().");
						}
					}
					triggerProcessed = true;
				}
				
				if(!triggerProcessed) {
					logger.error("Unsupport action type: " + actionName + " for trigger processing");
					throw new ApplicationException("Unsupport action type: " + actionName + " for trigger processing");
				}

			} else {
				throw new ApplicationException("The resource "+triggerPath+" does not exist.");
			}			
		} catch (UpdateTriggerSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), actionName, "Trigger", triggerId, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}

		// Some trigger updates may work successfully, but the impact api renders the trigger in error state.  For example, defining a send email action
		// but not having the email host configured in server properties.  We want to catch these types of errors and report them as failures.  This requires
		// invoking the getResource api after successfully updating the trigger.
		
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("TriggerWSDAOImpl.takeTriggerAction(\""+actionName+"\").  Invoking  port.getResource(\""+triggerPath+"\", \"TRIGGER\", \"FULL\")");
			}

			resourceReturnList = port.getResource(triggerPath, ResourceType.TRIGGER, DetailLevel.FULL);

			if(logger.isDebugEnabled()) {
				logger.debug("TriggerWSDAOImpl.takeTriggerAction(\""+actionName+"\").  Success: port.getResource().");
			}

			for (Resource respResource : resourceReturnList.getResource()) {
				ImpactLevel impactLevel = respResource.getImpactLevel();
				if(impactLevel.compareTo(ImpactLevel.NONE) != 0) {
					String errorMessage = null;
					if(respResource.getImpactMessage() == null || respResource.getImpactMessage().length() == 0) {
						errorMessage = "None";
					} else {
						errorMessage = respResource.getImpactMessage();
					}
					logger.error("Error detected (impact) after updating trigger id: " + triggerId + " Impact level: " + impactLevel.toString() + " Error Message: " + errorMessage);
					throw new ValidationException("Error detected (impact) after updating trigger id: " + triggerId + " Impact level: " + impactLevel.toString() + " Error Message: " + errorMessage);
				}
			}
			
		} catch (GetResourceSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), actionName, "Trigger", triggerId, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}

		return returnResList;
	}

}
