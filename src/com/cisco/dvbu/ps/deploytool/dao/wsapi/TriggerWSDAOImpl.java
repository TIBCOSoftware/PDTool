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
	@Override
	public ResourceList takeTriggerAction(String actionName, String triggerPath, String triggerId, String isEnabled, BigInteger maxEventsQueued, String annotation, String conditionType, AttributeList conditionAttributes, Schedule conditionSchedule, String actionType, AttributeList actionAttributes,
			String serverId, String pathToServersXML) throws CompositeException {
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
						returnResList = port.updateTrigger(triggerPath, DetailLevel.FULL, null, conditionType, conditionSchedule, conditionAttributes, 
								actionType, actionAttributes, maxEventsQueued, annotation, attributes);
					} else {
						returnResList = port.updateTrigger(triggerPath, DetailLevel.FULL, Boolean.valueOf(isEnabled), conditionType, conditionSchedule, conditionAttributes, 
								actionType, actionAttributes, maxEventsQueued, annotation, attributes);				
					}
					triggerProcessed = true;
				}
				
				if(actionName.equalsIgnoreCase(TriggerDAO.action.ENABLE.name())) {
					logger.debug("Invoking trigger enable for trigger id: " + triggerId + " with enabled flag: " + isEnabled);
					if (isEnabled == null) {
						returnResList = port.updateTrigger(triggerPath, DetailLevel.FULL, null, null, null, null, null, null, null, null, null);
					} else {
						returnResList = port.updateTrigger(triggerPath, DetailLevel.FULL, Boolean.valueOf(isEnabled), null, null, null, null, null, null, null, null);			
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
			resourceReturnList = port.getResource(triggerPath, ResourceType.TRIGGER, DetailLevel.FULL);

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
