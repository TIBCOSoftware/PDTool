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

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBElement;
//import javax.xml.bind.annotation.XmlEnumValue;







import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;

import com.tibco.ps.common.CommonConstants;
import com.tibco.ps.common.exception.CompositeException;
import com.tibco.ps.common.exception.ValidationException;
import com.tibco.ps.common.util.CommonUtils;
import com.tibco.ps.common.util.XMLUtils;
import com.tibco.ps.deploytool.DeployManagerUtil;
import com.tibco.ps.deploytool.dao.TriggerDAO;
import com.tibco.ps.deploytool.dao.wsapi.TriggerWSDAOImpl;
import com.tibco.ps.deploytool.util.DeployUtil;
import com.tibco.ps.deploytool.modules.ObjectFactory;
import com.tibco.ps.deploytool.modules.TriggerActionChoiceType;
import com.tibco.ps.deploytool.modules.TriggerActionExecuteProcedureType;
import com.tibco.ps.deploytool.modules.TriggerActionGatherStatisticsType;
import com.tibco.ps.deploytool.modules.TriggerActionReintrospectDatasourceType;
import com.tibco.ps.deploytool.modules.TriggerActionSendEmailType;
import com.tibco.ps.deploytool.modules.TriggerActionTypeValidationList;
import com.tibco.ps.deploytool.modules.TriggerConditionChoiceType;
import com.tibco.ps.deploytool.modules.TriggerConditionJmsEventType;
import com.tibco.ps.deploytool.modules.TriggerConditionSystemEventType;
import com.tibco.ps.deploytool.modules.TriggerConditionSystemEventValidationList;
import com.tibco.ps.deploytool.modules.TriggerConditionTimerEventType;
import com.tibco.ps.deploytool.modules.TriggerConditionTypeValidationList;
import com.tibco.ps.deploytool.modules.TriggerConditionUserDefinedEventType;
import com.tibco.ps.deploytool.modules.TriggerListType;
import com.tibco.ps.deploytool.modules.TriggerModeValidationList;
import com.tibco.ps.deploytool.modules.TriggerModule;
import com.tibco.ps.deploytool.modules.TriggerPeriodValidationList;
import com.tibco.ps.deploytool.modules.TriggerScheduleListType;
import com.tibco.ps.deploytool.modules.TriggerScheduleType;
import com.tibco.ps.deploytool.modules.TriggerType;
import com.compositesw.services.system.admin.resource.CalendarPeriod;
import com.compositesw.services.system.admin.resource.Resource;
import com.compositesw.services.system.admin.resource.ResourceList;
import com.compositesw.services.system.admin.resource.ResourceType;
import com.compositesw.services.system.admin.resource.Schedule;
import com.compositesw.services.system.admin.resource.ScheduleMode;
import com.compositesw.services.system.admin.resource.TriggerResource;
import com.compositesw.services.system.util.common.Attribute;
import com.compositesw.services.system.util.common.AttributeList;
import com.compositesw.services.system.util.common.AttributeType;
import com.compositesw.services.system.util.common.DetailLevel;

/**
 * @author kobrien
 *
 */
public class TriggerManagerImpl implements TriggerManager {

	private static Log logger = LogFactory.getLog(TriggerManagerImpl.class);

    private TriggerDAO triggerDAO = null;
    private int scheduleCount = 0;

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.TriggerManager#updateTriggers(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void updateTriggers(String serverId, String triggerIds, String pathToTriggersXML, String pathToServersXML) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering TriggerManagerImpl.updateTriggers() with following params "+" serverId: "+serverId+", triggerIds: "+triggerIds+", pathToTriggersXML: "+pathToTriggersXML+", pathToServersXML: "+pathToServersXML);
		}

		doTriggerAction(TriggerDAO.action.UPDATE.name(), serverId, triggerIds, null, pathToTriggersXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.TriggerManager#enableTriggers(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void enableTriggers(String serverId, String triggerIds,
			String pathToTriggersXML, String pathToServersXML)
			throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering TriggerManagerImpl.enableTriggers() with following params "+" serverId: "+serverId+", triggerIds: "+triggerIds+", pathToTriggersXML: "+pathToTriggersXML+", pathToServersXML: "+pathToServersXML);
		}
		
		doTriggerAction(TriggerDAO.action.ENABLE.name(), serverId, triggerIds, null, pathToTriggersXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.TriggerManager#generateTriggersXML(java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateTriggersXML(String serverId, String startPath, String pathToTriggersXML, String pathToServersXML) throws CompositeException {

		// Set the command and action name
		String command = "generateTriggersXML";
		String actionName = "CREATE_XML";

		if(logger.isDebugEnabled()){
			logger.debug("Entering TriggerManagerImpl.generateTriggersXML() with following params " + " serverId: " + serverId + ", pathToTriggersXML: " + pathToTriggersXML + ", pathToServersXML: " + pathToServersXML);
		}

		// Set the Module Action Objective
		String s1 = (startPath == null) ? "no_startPath" : "Path="+startPath;
		System.setProperty("MODULE_ACTION_OBJECTIVE", "GENERATE : "+s1);

        // Validate whether the files exist or not
        if (!CommonUtils.fileExists(pathToServersXML)) {
              throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
        }
		
		ResourceList resourceList = DeployManagerUtil.getDeployManager().getResourcesFromPath(serverId, startPath, ResourceType.CONTAINER.name(), ResourceType.TRIGGER.name(), DetailLevel.FULL.name(), pathToServersXML);

		if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty()){

			List<Resource> resources = resourceList.getResource();			
			TriggerModule triggerModule = new ObjectFactory().createTriggerModule();
			TriggerListType triggerList = new ObjectFactory().createTriggerListType();
			TriggerScheduleListType triggerScheduleList = new ObjectFactory().createTriggerScheduleListType();
			
			triggerModule.setTriggerList(triggerList);
			triggerModule.setScheduleList(triggerScheduleList);
			
			long loopCount = 0;
			for (Resource resource : resources) {
				loopCount++;
				if(logger.isDebugEnabled()){
					logger.debug("Processing trigger resource # " + loopCount + "Resource is type: " + resource.getClass().getName());
				}

				TriggerResource triggerResource = (TriggerResource)resource;
				TriggerType triggerType = new TriggerType();
				triggerModule.getTriggerList().getTrigger().add(triggerType);
				populateTriggerAttributes(triggerResource, triggerType, loopCount, triggerModule.getScheduleList());
			}

			// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
			if (CommonUtils.isExecOperation()) 
			{					
				XMLUtils.createXMLFromModuleType(triggerModule, pathToTriggersXML);
			} else {
				logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
			}
		}
	}

	// Populate all Trigger Attributes for generateTriggersXML()
	private void populateTriggerAttributes(TriggerResource triggerResource, TriggerType triggerType, long loopCount, TriggerScheduleListType scheduleList) {

		triggerType.setId("TR-" + loopCount);
		triggerType.setResourcePath(triggerResource.getPath());
		triggerType.setIsEnabled(triggerResource.isEnabled());
		triggerType.setAnnotation(triggerResource.getAnnotation());
		if(logger.isDebugEnabled()){
			logger.debug("Processing trigger: " + triggerResource.getPath());
			logger.debug("Rest field is of type: " + triggerResource.getRest().getClass().getName());
			logger.debug("Looping through list:");
		}
		
		long objLoopCount = 0;
		String conditionType = null;
		String actionType = null;
		AttributeList conditionAttributes = null;
		Schedule conditionSchedule = null;
		AttributeList actionAttributes = null;
		BigInteger maxEventsQueued = null;
		
		for (JAXBElement<?> triggerObj : triggerResource.getRest()) {
			objLoopCount++;
			
			if(logger.isDebugEnabled()){
				logger.debug("Rest field[" + objLoopCount + "] is of type: " + triggerObj.getClass().getName());
				logger.debug("JAXB Name: " + triggerObj.getName());
				logger.debug("JAXB Declared Type: " + triggerObj.getDeclaredType());
				
			}
			if (triggerObj.getName().getLocalPart().toString().equalsIgnoreCase("conditionType")) {
				logger.debug("Processing conditionType");
				conditionType = (String)triggerObj.getValue();
			}
			if (triggerObj.getName().getLocalPart().toString().equalsIgnoreCase("conditionAttributes")) {
				logger.debug("Processing condition attributes");
				conditionAttributes = (AttributeList)triggerObj.getValue();
			}
			if (triggerObj.getName().getLocalPart().toString().equalsIgnoreCase("conditionSchedule")) {
				logger.debug("Processing condition schedule");
				conditionSchedule = (Schedule)triggerObj.getValue();
			}
			if (triggerObj.getName().getLocalPart().toString().equalsIgnoreCase("actionType")) {
				logger.debug("Processing actionType");
				actionType = (String)triggerObj.getValue();
			}
			if (triggerObj.getName().getLocalPart().toString().equalsIgnoreCase("actionAttributes")) {
				logger.debug("Processing action attributes");
				actionAttributes = (AttributeList)triggerObj.getValue();
			}
			if (triggerObj.getName().getLocalPart().toString().equalsIgnoreCase("maxEventsQueued")) {
				logger.debug("Processing max events queued");
				maxEventsQueued = (BigInteger)triggerObj.getValue();
			}
		}
		
		triggerType.setMaxEventsQueued(maxEventsQueued);
		if(logger.isDebugEnabled()){
			logger.debug("Condition type: " + conditionType);
			logger.debug("Action type: " + actionType);
		}
		
		triggerType.setCondition(populateTriggerCondition(conditionType, conditionAttributes, conditionSchedule, "TR-" + loopCount, scheduleList));
		triggerType.setAction(populateTriggerAction(actionType, actionAttributes, "TR-" + loopCount));
		
		if(logger.isDebugEnabled()) {
			if(scheduleList.getSchedule().isEmpty()) {
				logger.debug("Schedule List is empty");
			} else {
				logger.debug("Schedule List size: " + scheduleList.getSchedule().size());
			}
		}
	}
	
	// Populate all Trigger Conditions for generateTriggersXML()
	private TriggerConditionChoiceType populateTriggerCondition(String conditionType, AttributeList conditionAttributes, Schedule conditionSchedule, String triggerId, TriggerScheduleListType scheduleList) {

		TriggerConditionChoiceType tcct = new TriggerConditionChoiceType();
		TriggerScheduleType tst = null;
		
		if(conditionType.equals(TriggerConditionTypeValidationList.USER_DEFINED.name())) {
			TriggerConditionUserDefinedEventType triggerCondition = new TriggerConditionUserDefinedEventType();

			if(logger.isDebugEnabled()){
				logger.debug("Processing User Defined Event Condition");
			}

			for (Attribute attr : conditionAttributes.getAttribute()) {
				if(attr.getName().equals("NAME")) {
					triggerCondition.setEventName(attr.getValue());
				}
			}
			tcct.setUserDefinedEvent(triggerCondition);
		}

		if(conditionType.equals(TriggerConditionTypeValidationList.JMS.name())) {
			TriggerConditionJmsEventType triggerCondition = new TriggerConditionJmsEventType();

			if(logger.isDebugEnabled()){
				logger.debug("Processing JMS Condition");
			}

			for (Attribute attr : conditionAttributes.getAttribute()) {
				if(attr.getName().equals("JMS_DESTINATION")) {
					triggerCondition.setDestination(attr.getValue());
				}
				if(attr.getName().equals("JMS_SELECTOR")) {
					triggerCondition.setSelector(attr.getValue());
				}
				if(attr.getName().equals("JMS_CONNECTOR")) {
					triggerCondition.setConnector(attr.getValue());
				}
			}
			tcct.setJmsEvent(triggerCondition);
		}

		if(conditionType.equals(TriggerConditionTypeValidationList.SYSTEM_EVENT.name())) {
			TriggerConditionSystemEventType triggerCondition = new TriggerConditionSystemEventType();

			if(logger.isDebugEnabled()){
				logger.debug("Processing System Event Condition");
			}

			for (Attribute attr : conditionAttributes.getAttribute()) {
				if(attr.getName().equals("EVENT_NAME")) {
					String value = attr.getValue();
					triggerCondition.setEventName(TriggerConditionSystemEventValidationList.fromValue(value));
				}
			}
			tcct.setSystemEvent(triggerCondition);
		}

		if(conditionType.equals(TriggerConditionTypeValidationList.TIMER.name())) {
			TriggerConditionTimerEventType triggerCondition = new TriggerConditionTimerEventType();
			scheduleCount++;

			if(logger.isDebugEnabled()){
				logger.debug("Processing Timer Event Condition");
			}

			triggerCondition.setScheduleId(triggerId + "-SCH-" + scheduleCount);
			tcct.setTimerEvent(triggerCondition);

			tst = new TriggerScheduleType();
			logger.debug("Step 1: set scheduleId");
			tst.setScheduleId(triggerId + "-SCH-" + scheduleCount);

			logger.debug("Step 2: set mode");
			if (conditionSchedule.getMode().name().equalsIgnoreCase("NONE")) {
				tst.setMode(TriggerModeValidationList.NONE);
				// Set the startTime
				tst.setStartTime(conditionSchedule.getStartTime());
			} else {
				tst.setMode(TriggerModeValidationList.PERIODIC);

				logger.debug("Step 3: set condition schedule startTime, period and count");
			
				// Set the startTime
				tst.setStartTime(conditionSchedule.getStartTime());
				
				// Set the period and count
				if(conditionSchedule.getPeriod() == null) {
					logger.debug("Step 4: set period and count for INTERVAL (Period is null)");
					if (conditionSchedule.getInterval() != null) {
						tst.setPeriod(TriggerPeriodValidationList.fromValue("MINUTE"));
						tst.setCount(BigInteger.valueOf(conditionSchedule.getInterval()));		
					}
				} else {
					logger.debug("Step 5: set period and count for CALENDAR (Period is NOT NULL)");
					tst.setPeriod(TriggerPeriodValidationList.fromValue(conditionSchedule.getPeriod().name()));
					tst.setCount(BigInteger.valueOf(conditionSchedule.getCount()));
				}
			}

			logger.debug("Step 6: set fromTimeInADay");
			if(conditionSchedule.getFromTimeInADay() == null) {
				logger.debug("FromTimeInADay is null");
				tst.setFromTimeInADay(BigInteger.valueOf(-1));
			} else {
				logger.debug("FromTimeInADay is NOT null");
				tst.setFromTimeInADay(BigInteger.valueOf(conditionSchedule.getFromTimeInADay()));
			}

			logger.debug("Step 7: set endTimeInADay");
			if(conditionSchedule.getEndTimeInADay() == null) {
				logger.debug("EndTimeInADay is null");
				tst.setEndTimeInADay(BigInteger.valueOf(-1));
			} else {
				logger.debug("EndTimeInADay is NOT null");
				tst.setEndTimeInADay(BigInteger.valueOf(conditionSchedule.getEndTimeInADay()));
			}

			logger.debug("Step 8: set recurringDay");
			if(conditionSchedule.getRecurringDay() == null) {
				logger.debug("RecurringDay is null");
				tst.setRecurringDay(BigInteger.valueOf(-1));
			} else {
				logger.debug("RecurringDay is NOT null");
				tst.setRecurringDay(BigInteger.valueOf(conditionSchedule.getRecurringDay()));
			}

			logger.debug("Step 9: set isCluster");
			if(conditionSchedule.isIsCluster() == null) {
				logger.debug("IsCluster is null");
				tst.setIsCluster(Boolean.TRUE);
			} else {
				logger.debug("IsCluster is NOT null");
				tst.setIsCluster(conditionSchedule.isIsCluster());
			}

			logger.debug("Step 10: add schedule to list");
			scheduleList.getSchedule().add(tst);
		}

		return tcct;
	}
	
	// Populate all Trigger actions for generateTriggersXML()
	private TriggerActionChoiceType populateTriggerAction(String actionType, AttributeList actionAttributes, String triggerId) {
		TriggerActionChoiceType tact = new TriggerActionChoiceType();
		
		if(actionType.equals(TriggerActionTypeValidationList.EMAIL.name())) {
			TriggerActionSendEmailType triggerAction = new TriggerActionSendEmailType();

			if(logger.isDebugEnabled()){
				logger.debug("Processing Email Action");
			}

			for (Attribute attr : actionAttributes.getAttribute()) {
				if(attr.getName().equals("SUBJECT")) {
					triggerAction.setEmailSubject(attr.getValue());
				}

				if(attr.getName().equals("SUMMARY")) {
					triggerAction.setIncludeSummary(Boolean.valueOf(attr.getValue()));
				}

				if(attr.getName().equals("BCC")) {
					triggerAction.setEmailBCC(attr.getValue());
				}

				if(attr.getName().equals("SKIP_IF_NO_RESULTS")) {
					triggerAction.setSkipIfNoResults(Boolean.valueOf(attr.getValue()));
				}

				if(attr.getName().equals("CONTENT")) {
					triggerAction.setEmailBody(attr.getValue());
				}

				if(attr.getName().equals("CC")) {
					triggerAction.setEmailCC(attr.getValue());
				}

				if(attr.getName().equals("PARAMETERS")) {
					triggerAction.setParameterValues(attr.getValue());						
				}

				if(attr.getName().equals("TO")) {
					triggerAction.setEmailTo(attr.getValue());
				}

				if(attr.getName().equals("PATH")) {
					triggerAction.setResourcePath(attr.getValue());
				}

				if(attr.getName().equals("REPLYTO")) {
					triggerAction.setEmailReplyTo(attr.getValue());
				}
			}
			tact.setSendEmail(triggerAction);
		}
		
		if(actionType.equals(TriggerActionTypeValidationList.PROCEDURE.name())) {
			TriggerActionExecuteProcedureType triggerAction = new TriggerActionExecuteProcedureType();

			if(logger.isDebugEnabled()){
				logger.debug("Processing Procedure Action");
			}

			for (Attribute attr : actionAttributes.getAttribute()) {
				if(attr.getName().equals("PARAMETERS")) {
					triggerAction.setParameterValues(attr.getValue());						
				}
				
				if(attr.getName().equals("PATH")) {
					triggerAction.setResourcePath(attr.getValue());
				}
			}
			tact.setExecuteProcedure(triggerAction);
		}
		
		if(actionType.equals(TriggerActionTypeValidationList.REINTROSPECT.name())) {
			TriggerActionReintrospectDatasourceType triggerAction = new TriggerActionReintrospectDatasourceType();

			if(logger.isDebugEnabled()){
				logger.debug("Processing Reintrospect Action");
			}

			for (Attribute attr : actionAttributes.getAttribute()) {
				if(attr.getName().equals("SUBJECT")) {
					triggerAction.setEmailSubject(attr.getValue());
				}

				if(attr.getName().equals("BCC")) {
					triggerAction.setEmailBCC(attr.getValue());
				}

				if(attr.getName().equals("SKIP_IF_NO_RESULTS")) {
					triggerAction.setSkipIfNoResults(Boolean.valueOf(attr.getValue()));
				}

				if(attr.getName().equals("CONTENT")) {
					triggerAction.setEmailBody(attr.getValue());
				}

				if(attr.getName().equals("CC")) {
					triggerAction.setEmailCC(attr.getValue());
				}

				if(attr.getName().equals("TO")) {
					triggerAction.setEmailTo(attr.getValue());
				}

				if(attr.getName().equals("PATH")) {
					triggerAction.setResourcePath(attr.getValue());
				}

				if(attr.getName().equals("REPLYTO")) {
					triggerAction.setEmailReplyTo(attr.getValue());
				}
				
				if(attr.getName().equals("NO_COMMIT")) {
					triggerAction.setNoCommit(Boolean.valueOf(attr.getValue()));
				}
			}
			tact.setReintrospectDatasource(triggerAction);
		}
		
		if(actionType.equals(TriggerActionTypeValidationList.STATISTICS.name())) {
			TriggerActionGatherStatisticsType triggerAction = new TriggerActionGatherStatisticsType();

			if(logger.isDebugEnabled()){
				logger.debug("Processing Statistics Action");
			}

			for (Attribute attr : actionAttributes.getAttribute()) {
				if(attr.getName().equals("PATH")) {
					triggerAction.setResourcePath(attr.getValue());
				}
			}
			tact.setGatherStatistics(triggerAction);
		}

		return tact;
	}
	
	// Perform a trigger actions such as update or enable
	private void doTriggerAction(String actionName, String serverId, String triggerIds, String userName, String pathToTriggersXML, String pathToServersXML) throws CompositeException {

		// Initialize variables
		String processedIds = null;
		ResourceList returnResList = null;
		AttributeList conditionAttributes = null;
		Schedule conditionSchedule = null;
		String conditionType = null;
		AttributeList actionAttributes = null;
		String actionType = null;

        // Validate whether the files exist or not
        if (!CommonUtils.fileExists(pathToTriggersXML)) {
              throw new CompositeException("File ["+pathToTriggersXML+"] does not exist.");
        }
        if (!CommonUtils.fileExists(pathToServersXML)) {
              throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
        }

		
		// Retrieve the TriggerModule XML
		TriggerModule triggers = getTriggerModuleXML(serverId, triggerIds, pathToTriggersXML, pathToServersXML);
		TriggerListType triggerListType = triggers.getTriggerList();
		TriggerScheduleListType  triggerScheduleListType = triggers.getScheduleList();
		
		List<TriggerType> triggerList = getTriggers(triggerListType);

		if (triggerList != null && triggerList.size() > 0) {
			returnResList = new ResourceList();
		}

		// Get the configuration property file set in the environment with a default of deploy.properties
		String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");

		String prefix = "doTriggerAction";
		// Extract variables for the triggerIds
		triggerIds = CommonUtils.extractVariable(prefix, triggerIds, propertyFile, true);

		// Set the Module Action Objective
		String s1 = (triggerIds == null) ? "no_triggerIds" : "Ids="+triggerIds;
		System.setProperty("MODULE_ACTION_OBJECTIVE", actionName+" : "+s1);

		int loopCount = 0;
		for (TriggerType trigger : triggerList) {
			loopCount++;

			// Get the identifier and convert any $VARIABLES
			String identifier = CommonUtils.extractVariable(prefix, trigger.getId(), propertyFile, true);
			
			/**
			 * Possible values for archives 
			 * 1. csv string like import1,import2 (we process only resource names which are passed in)
			 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
			 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
			 * 	  like -import1,import3 (we ignore passed in resources and process rest of the in the input xml
			 */
			if(DeployUtil.canProcessResource(triggerIds, identifier))
			{
				// Add to the list of processed ids
				if (processedIds == null)
					processedIds = "";
				else
					processedIds = processedIds + ",";
				processedIds = processedIds + identifier;

				if(logger.isInfoEnabled()){
					logger.info("processing action " + actionName + " on trigger " + identifier);
				}

				if(logger.isDebugEnabled()) {
					logger.debug("Processing Trigger # " + loopCount);
					logger.debug("Trigger Id: " + identifier);
					logger.debug("Resource Path: " + trigger.getResourcePath());
					logger.debug("Enabled: " + trigger.isIsEnabled());
					logger.debug("Max Events Queued: " + trigger.getMaxEventsQueued());
					logger.debug("Annotation: " + trigger.getAnnotation());
					logger.debug("Condition: " + trigger.getCondition().toString());
					logger.debug("Action: " + trigger.getAction().toString());
				}

				// Set the Module Action Objective
				s1 = identifier+"=" + ((trigger.getResourcePath() == null) ? "no_triggerPath" : trigger.getResourcePath());
				System.setProperty("MODULE_ACTION_OBJECTIVE", actionName+" : "+s1);

				/*
				 * Process Trigger Baseline
				*/
				String isEnabled = null;
				if (trigger.isIsEnabled() != null) {
					isEnabled = trigger.isIsEnabled().toString();
				}
				BigInteger maxEventsQueued = trigger.getMaxEventsQueued();
				String annotation = trigger.getAnnotation();
				
				/*
				 * Process Trigger Condition
				*/
				TriggerConditionChoiceType tcct = trigger.getCondition();
				if(tcct.getJmsEvent() != null)
				{
					if(logger.isDebugEnabled()) {
						logger.debug("Trigger Condition: JMS Event");
						logger.debug("JMS Connector: " + tcct.getJmsEvent().getConnector());
						logger.debug("JMS Destination: " + tcct.getJmsEvent().getDestination());
						logger.debug("JMS Selector: " + tcct.getJmsEvent().getSelector());
					}
					
					conditionType = TriggerConditionTypeValidationList.JMS.name();
					conditionAttributes = getJmsConditionAttributes(tcct.getJmsEvent(), identifier);
				}

				if(tcct.getSystemEvent() != null)
				{
					if(logger.isDebugEnabled()) {
						logger.debug("Trigger Condition: System Event");
						logger.debug("System Event Name: " + tcct.getSystemEvent().getEventName().value());
					}

					conditionType = TriggerConditionTypeValidationList.SYSTEM_EVENT.name();
					conditionAttributes = getSystemConditionAttributes(tcct.getSystemEvent(), identifier);
				}

				if(tcct.getTimerEvent() != null)
				{
					if(logger.isDebugEnabled()) {
						logger.debug("Trigger Condition: Timer Event");
						logger.debug("Timer Schedule Id: " + tcct.getTimerEvent().getScheduleId());
					}

					conditionType = TriggerConditionTypeValidationList.TIMER.name();
					conditionSchedule = getTimerConditionSchedule(tcct.getTimerEvent().getScheduleId(), triggerScheduleListType);
				}

				if(tcct.getUserDefinedEvent() != null)
				{
					if(logger.isDebugEnabled()) {
						logger.debug("Trigger Condition: User Defined Event");
						logger.debug("User Defined Event Name: " + tcct.getUserDefinedEvent().getEventName());
					}

					conditionType = TriggerConditionTypeValidationList.USER_DEFINED.name();
					conditionAttributes = getUserDefinedConditionAttributes(tcct.getUserDefinedEvent(), identifier);
				}

				if(logger.isDebugEnabled()){
					logger.debug("processing trigger action on trigger " + identifier);
				}

				/*
				 * Process Trigger Action
				*/
				TriggerActionChoiceType tact = trigger.getAction();
				if(tact.getExecuteProcedure() != null) {
					if(logger.isDebugEnabled()) {
						logger.debug("Trigger Action: ExecuteProcedure");
					}

					actionType = TriggerActionTypeValidationList.PROCEDURE.name();
					actionAttributes = getProcedureActionAttributes(tact.getExecuteProcedure(), identifier);
				}
				
				if(tact.getGatherStatistics() != null) {
					if(logger.isDebugEnabled()) {
						logger.debug("Trigger Action: GatherStatistics");
					}

					actionType = TriggerActionTypeValidationList.STATISTICS.name();
					actionAttributes = getStatisticsActionAttributes(tact.getGatherStatistics(), identifier);
				}
				
				if(tact.getReintrospectDatasource() != null) {
					if(logger.isDebugEnabled()) {
						logger.debug("Trigger Action: ReintrospectDatasource");
					}

					actionType = TriggerActionTypeValidationList.REINTROSPECT.name();
					actionAttributes = getReintrospectActionAttributes(tact.getReintrospectDatasource(), identifier);
				}
				
				if(tact.getSendEmail() != null) {
					if(logger.isDebugEnabled()) {
						logger.debug("Trigger Action: SendEmail");
					}

					actionType = TriggerActionTypeValidationList.EMAIL.name();
					actionAttributes = getEmailActionAttributes(tact.getSendEmail(), identifier);
				}

				if(conditionType == null) {
					logger.error("Unable to process trigger condition for trigger id: " + identifier);
					throw new ValidationException("Unable to process trigger condition for trigger id: " + identifier);
				}

				if(actionType == null) {
					logger.error("Unable to process trigger action for trigger id: " + identifier);
					throw new ValidationException("Unable to process trigger action for trigger id: " + identifier);
				}
				
				/*
				 * Invoke the Trigger Action
				*/
				ResourceList resourceList = getTriggerDAO().takeTriggerAction(actionName, trigger.getResourcePath(), identifier, isEnabled, maxEventsQueued, annotation, conditionType, conditionAttributes, conditionSchedule, actionType, actionAttributes, serverId, pathToServersXML);

				if(resourceList != null){
					returnResList.getResource().addAll(resourceList.getResource());
				}

				if(logger.isInfoEnabled()){
					logger.info("Successfully updated trigger: " + identifier + " " + trigger.getResourcePath());
				}

			} else {
				if(logger.isDebugEnabled()){
					String msg = "Warning: Skipping action " + actionName + " on trigger " + identifier;
					logger.info(msg);
					System.setProperty("MODULE_ACTION_MESSAGE", msg);
				}
			}
		}
		// Determine if any resourceIds were not processed and report on this
		if (processedIds != null) {
			if(logger.isInfoEnabled()){
				logger.info("Trigger entries processed="+processedIds);
			}
		} else {
			if(logger.isInfoEnabled()){
				String msg = "Warning: No trigger entries were processed for the input list.  triggerIds="+triggerIds;
				logger.info(msg);
				System.setProperty("MODULE_ACTION_MESSAGE", msg);
			}		
		}
	}

	// Get the list of trigger list from the TriggerModule.xml property file
	private TriggerModule getTriggerModuleXML(String serverId, String triggerIds, String pathToTriggersXML, String pathToServersXML) {
		// validate incoming arguments
		if(serverId == null || serverId.trim().length() ==0 || triggerIds == null || triggerIds.trim().length() ==0 || pathToServersXML == null || pathToServersXML.trim().length() ==0 || pathToTriggersXML == null || pathToTriggersXML.trim().length() ==0){
			throw new ValidationException("Invalid Arguments");
		}
		try {
    		//using jaxb convert xml to corresponding java objects
			TriggerModule triggerModuleType = (TriggerModule)XMLUtils.getModuleTypeFromXML(pathToTriggersXML);
			if(triggerModuleType != null && triggerModuleType.getTriggerList() != null){
				return triggerModuleType;
			}
		} catch (CompositeException e) {
			logger.error("Error while parsing Trigger Module XML" , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
		return null;
	}
	
	// Get the list of triggers from the TriggerModule.xml
	private List<TriggerType> getTriggers(TriggerListType triggerListType) {
		try {
			if(triggerListType != null && triggerListType.getTrigger() != null && !triggerListType.getTrigger().isEmpty()){
				return triggerListType.getTrigger();
			}
		} catch (CompositeException e) {
			logger.error("Error while parsing Trigger Module XML" , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
		return null;
	
	}

	// Extract and validate the JMS Condition attributes from the TriggerModule.xml property file
	private AttributeList getJmsConditionAttributes(TriggerConditionJmsEventType jmsEvent, String triggerId) throws CompositeException {

		if(jmsEvent.getConnector() == null)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  JMS event condition property 'Connector' is null");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  JMS event condition property 'Connector' is null");
		}
		
		if(jmsEvent.getConnector().length() == 0)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  JMS event condition property 'Connector' is empty");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  JMS event condition property 'Connector' is empty");
		}

		if(jmsEvent.getDestination() == null)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  JMS event condition property 'Destination' is null");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  JMS event condition property 'Destination' is null");
		}
		
		if(jmsEvent.getDestination().length() == 0)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  JMS event condition property 'Destination' is empty");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  JMS event condition property 'Destination' is empty");
		}
		
		AttributeList conditionAttributes = new AttributeList();
		
		Attribute attrJmsConnector = new Attribute();
		attrJmsConnector.setName("JMS_CONNECTOR");
		attrJmsConnector.setType(AttributeType.STRING);
		attrJmsConnector.setValue(jmsEvent.getConnector());
		conditionAttributes.getAttribute().add(attrJmsConnector);
		
		Attribute attrJmsDestination = new Attribute();
		attrJmsDestination.setName("JMS_DESTINATION");
		attrJmsDestination.setType(AttributeType.STRING);
		attrJmsDestination.setValue(jmsEvent.getDestination());
		conditionAttributes.getAttribute().add(attrJmsDestination);

		if(jmsEvent.getSelector() != null  && jmsEvent.getSelector().length() > 0) {
			Attribute attrJmsSelector = new Attribute();
			attrJmsSelector.setName("JMS_SELECTOR");
			attrJmsSelector.setType(AttributeType.STRING);
			attrJmsSelector.setValue(jmsEvent.getSelector());
			conditionAttributes.getAttribute().add(attrJmsSelector);
		}

		return conditionAttributes;
	}

	// Extract and validate the system condition from the TriggerModule.xml property file
	private AttributeList getSystemConditionAttributes(TriggerConditionSystemEventType systemEvent, String triggerId) throws CompositeException {

		if(systemEvent.getEventName() == null)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  System event condition property 'System Event Name' is null");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  System event condition property 'System Event Name' is null");
		}
		
		if(systemEvent.getEventName().name().length() == 0)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  System event condition property 'System Event Name' is empty");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  System event condition property 'System Event Name' is empty");
		}

		// Validate the Trigger Condition System Event against the XML Schema validation restriction enum list (use .values() instead of .name())
		// JAXB Generated code
		//    @XmlEnumValue("CacheRefreshFailure")			validSystemEventName.value()=CacheRefreshFailure
	    //	  CACHE_REFRESH_FAILURE("CacheRefreshFailure")  validSystemEventName.name()=CACHE_REFRESH_FAILURE
		//
		boolean isValid = false;
		for (TriggerConditionSystemEventValidationList validSystemEventName : TriggerConditionSystemEventValidationList.values())
		{	
			if(validSystemEventName.value().equals(systemEvent.getEventName().value())){
				if(logger.isDebugEnabled()){
					logger.debug("System Event Name '" + systemEvent.getEventName().value() + "' matches Type from enum '" + validSystemEventName.value() + "'");
				}
				isValid = true;
			}
		}
		if(isValid == false) {
			logger.error("Error processing trigger id: " + triggerId + ".  System event condition property 'System Event Name' value '" + systemEvent.getEventName().value() + "' is invalid");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  System event condition property 'System Event Name' value '" + systemEvent.getEventName().value() + "' is invalid");
		}
		
		AttributeList conditionAttributes = new AttributeList();
		
		Attribute attrEventName = new Attribute();
		attrEventName.setName("EVENT_NAME");
		attrEventName.setType(AttributeType.STRING);
		attrEventName.setValue(systemEvent.getEventName().value());
		conditionAttributes.getAttribute().add(attrEventName);

		return conditionAttributes;
	}

	// Extract and validate the user defined condition attributes from the TriggerModule.xml property file
	private AttributeList getUserDefinedConditionAttributes(TriggerConditionUserDefinedEventType userDefinedEvent, String triggerId) throws CompositeException {

		if(userDefinedEvent.getEventName() == null)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  User defined event condition property 'User Defined Event Name' is null");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  User defined event condition property 'User Defined Event Name' is null");
		}
		
		if(userDefinedEvent.getEventName().length() == 0)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  User defined event condition property 'User Defined Event Name' is empty");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  User defined event condition property 'User Defined Event Name' is empty");
		}
		AttributeList conditionAttributes = new AttributeList();
		
		Attribute attrEventName = new Attribute();
		attrEventName.setName("NAME");
		attrEventName.setType(AttributeType.STRING);
		attrEventName.setValue(userDefinedEvent.getEventName());
		conditionAttributes.getAttribute().add(attrEventName);

		return conditionAttributes;
	}
	
	// Get the timer condition schedule from the TriggerModule.xml property file
	private Schedule getTimerConditionSchedule(String scheduleId, TriggerScheduleListType triggerScheduleListType) {

		TriggerScheduleType triggerSchedule = null;
		Schedule triggerConditionSchedule = null;
		
		triggerSchedule = getTriggerSchedule(scheduleId, triggerScheduleListType);
/*
 * Having identified the matching Schedule, now we need to validate and build the schedule condition		
 */
		if(logger.isDebugEnabled()){
			logger.debug("Schedule id: " + triggerSchedule.getScheduleId());
			logger.debug("Schedule Mode: " + triggerSchedule.getMode().name());  // should be CALENDAR or NONE
			logger.debug("Schedule End Time In A Day: " + triggerSchedule.getEndTimeInADay()); // represents minutes.  Should be < 1440 >= 0.  Is -1 if no restriction.  Should be > FromTimeInADay.  Can End exist without From?  UI restricts to selections in 30 min values.
			logger.debug("Schedule From Time In A Day: " + triggerSchedule.getFromTimeInADay()); // represents minutes.  Should be < 1440 >= 0.  Is -1 if no restriction.  Should be < EndTimeInADay.  Can From exist without End?  UI restricts to selections in 30 min values.
			logger.debug("Schedule Period: " + triggerSchedule.getPeriod());  // represents a value list [MINUTE,HOUR,DAY,WEEK,MONTH,YEAR]
			logger.debug("Schedule Count: " + triggerSchedule.getCount());  // represents value of period. 
			logger.debug("Schedule Recurring Day: " + triggerSchedule.getRecurringDay());  // -1 if no recurrence restriction.  Bit pattern 1=SUN,2=MON,4=TUE,8=WED,16=THU,32=FRI,64=SAT.  Max 127.
			logger.debug("Schedule Start Time: " + triggerSchedule.getStartTime());
			logger.debug("Schedule isCluster: " + triggerSchedule.isIsCluster());
		}

		validateTriggerSchedule(triggerSchedule);
		
		// Set up the attribute list that specify the desired config values for
		// this trigger timer condition
		triggerConditionSchedule = new Schedule();

		//populate attributes for trigger condition schedule
		if (triggerSchedule.getStartTime() != null) {
			triggerConditionSchedule.setStartTime(triggerSchedule.getStartTime());
		}
		
		// mode=INTERVAL
		if(triggerSchedule.getMode().name().equals(TriggerModeValidationList.PERIODIC.name())) {
			// When PERIODIC and period is MINUTE then set mode to INTERVAL and use interval element
			if (triggerSchedule.getPeriod().name().equalsIgnoreCase("MINUTE")) {
				triggerConditionSchedule.setMode(ScheduleMode.INTERVAL);
				triggerConditionSchedule.setInterval(triggerSchedule.getCount().intValue());
			} else {
			// When PERIODIC and period is HOUR,DAY,WEEK,MONTH,YEAR then set mode to CALENDAR and use period and count elements
				triggerConditionSchedule.setMode(ScheduleMode.CALENDAR);
				triggerConditionSchedule.setPeriod(CalendarPeriod.valueOf(triggerSchedule.getPeriod().name()));
				triggerConditionSchedule.setCount(triggerSchedule.getCount().intValue());
			}
			
			// Populate fromtTimeInADay
			if (triggerSchedule.getFromTimeInADay() != null) {
				triggerConditionSchedule.setFromTimeInADay(triggerSchedule.getFromTimeInADay().longValue());
			}
			// Populate endTimeInADay
			if (triggerSchedule.getEndTimeInADay() != null) {
				triggerConditionSchedule.setEndTimeInADay(triggerSchedule.getEndTimeInADay().longValue());
			}
			// Populate recurringDay
			if (triggerSchedule.getRecurringDay() != null) {
				triggerConditionSchedule.setRecurringDay(triggerSchedule.getRecurringDay().intValue());
			}

		} else { // mode=NONE
			triggerConditionSchedule.setMode(ScheduleMode.NONE);
			triggerConditionSchedule.setFromTimeInADay((long) -1);
			triggerConditionSchedule.setEndTimeInADay((long) -1);
			triggerConditionSchedule.setRecurringDay(-1);		
		}
		if (triggerSchedule.isIsCluster() != null) {
			triggerConditionSchedule.setIsCluster(triggerSchedule.isIsCluster());
		}
		return triggerConditionSchedule;
	}

	// Get the list of trigger schedules from the TriggerModule.xml property file
	private List<TriggerScheduleType> getTriggerSchedules(TriggerScheduleListType triggerScheduleListType) {
		
		try {
			if(triggerScheduleListType != null && triggerScheduleListType.getSchedule() != null && !triggerScheduleListType.getSchedule().isEmpty()){
				return triggerScheduleListType.getSchedule();
			}
		} catch (CompositeException e) {
			logger.error("Error while parsing Trigger Module XML" , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
		return null;
	
	}

	// Retrieve a matching trigger schedule from the trigger schedule (TriggerModule.xml) list as compared to the CIS schedule list 
	private TriggerScheduleType getTriggerSchedule(String scheduleId, TriggerScheduleListType triggerScheduleListType) {

		List<TriggerScheduleType> scheduleList = null;
		TriggerScheduleType matchingSchedule = null;

		if(logger.isDebugEnabled()){
			logger.debug("Schedule Id listed in Trigger: " + scheduleId);
		}
		
		int loopCount = 0;
		scheduleList = getTriggerSchedules(triggerScheduleListType);
		for (TriggerScheduleType triggerSchedule : scheduleList) {
			loopCount++;
			if(triggerSchedule.getScheduleId().equals(scheduleId)) {
				matchingSchedule = triggerSchedule;
				if(logger.isDebugEnabled()){
					logger.debug("Found matching schedule in schedule list");
				}
			}
		}
		
		if(matchingSchedule == null) {
			logger.error("Error processing trigger.  Referenced ScheduleId " + scheduleId + " is missing from TriggerModule.xml");
			throw new ValidationException("Error processing trigger.  Referenced ScheduleId " + scheduleId + " is missing from TriggerModule.xml");
		}

		return matchingSchedule;
	}

	// Validate the trigger schedule found in the TriggerModule.xml property file
	private void validateTriggerSchedule(TriggerScheduleType triggerSchedule) throws CompositeException {

		boolean isValid = false;
		
		//***************************************************************
		// Validate schedule mode
		//***************************************************************
		if(logger.isDebugEnabled()){
			logger.debug("Validating schedule mode ...");
		}
		if(triggerSchedule.getMode() == null) {
			logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' is null");
			throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' is null");
		}
		
		if(triggerSchedule.getMode().name().length() == 0) {
			logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' is empty");
			throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' is empty");
		}

		// Validate mode against the XML Schema enum validation list
		isValid = false;
		for (TriggerModeValidationList validScheduleMode : TriggerModeValidationList.values()) {
			if(validScheduleMode.name().equals(triggerSchedule.getMode().name())){
				if(logger.isDebugEnabled()){
					logger.debug("Schedule Mode '" + triggerSchedule.getMode().name() + "' matches Type from enum '" + validScheduleMode.name() + "'");
				}
				isValid = true;
			}
		}
		if(isValid == false) {
			logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' value '" + triggerSchedule.getMode().name() + "' is invalid");
			throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' value '" + triggerSchedule.getMode().name() + "' is invalid");
		}
		

		//***************************************************************
		// Validate schedule mode=PERIODIC [CIS: INTERVAL or CALENDAR].
		//***************************************************************
		isValid = false;
		// Check mode against the XML Schema enum validation list
		if(triggerSchedule.getMode().name().equals(TriggerModeValidationList.PERIODIC.name())) {
			if(logger.isDebugEnabled()){
				logger.debug("Validating schedule mode=PERIODIC ...");
			}

			//***************************************************************
			// Validate period element
			//***************************************************************
			if(triggerSchedule.getCount() == null) {
				logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has value 'PERIODIC' but schedule condition property 'count' is missing");
				throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has value 'PERIODIC' but schedule condition property 'count' is missing");
			}

			if(triggerSchedule.getCount().compareTo(BigInteger.valueOf(0)) < 0) {
				logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has value 'PERIODIC' but schedule condition property 'count' is less than zero: " + triggerSchedule.getCount());
				throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has value 'PERIODIC' but schedule condition property 'count' is less than zero: " + triggerSchedule.getCount());
			}

			if(triggerSchedule.getCount().compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
				logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Count' has value " + triggerSchedule.getCount() + " which exceeds maximum allowed value of " + Integer.MAX_VALUE);
				throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Count' has value " + triggerSchedule.getCount() + " which exceeds maximum allowed value of " + Integer.MAX_VALUE);
			}

			isValid = false;
			if(triggerSchedule.getCount() != null && triggerSchedule.getCount().compareTo(BigInteger.valueOf(0)) >= 0) {
				isValid = true;
			}
			if(isValid == false) {
				logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has value 'PERIODIC' but schedule condition property 'interval' has an invalid value: " + triggerSchedule.getCount());
				throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has value 'PERIODIC' but schedule condition property 'interval' has an invalid value: " + triggerSchedule.getCount());
			}
				
			//***************************************************************
			// Validate period element
			//***************************************************************
			if(triggerSchedule.getPeriod() == null) {
				logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has value 'PERIODIC' but schedule condition property 'period' is missing");
				throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has value 'PERIODIC' but schedule condition property 'period' is missing");
			}

			// Validate the Period value against the XML Schema Trigger Period Validation Restriction enum list
			isValid = false;
			for (TriggerPeriodValidationList validSchedulePeriod : TriggerPeriodValidationList.values())
			{
				if(validSchedulePeriod.name().equals(triggerSchedule.getPeriod().name())){
					if(logger.isDebugEnabled()){
						logger.debug("Schedule Period '" + triggerSchedule.getPeriod() + "' matches Type from enum '" + validSchedulePeriod.name() + "'");
					}
					isValid = true;
				}
			}
			if(isValid == false) {
				logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Period' has an invalid value: " + triggerSchedule.getPeriod());
				throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Period' has an invalid value: " + triggerSchedule.getPeriod());
			}


			//***************************************************************
			// Validate schedule recurring day
			//***************************************************************
			if(logger.isDebugEnabled()){
				logger.debug("Validating recurring day ...");
			}
			isValid = false;
			// Recurring Day must be between 1 and 127
			if((triggerSchedule.getRecurringDay().compareTo(BigInteger.valueOf(1)) < 0 || triggerSchedule.getRecurringDay().compareTo(BigInteger.valueOf(127)) > 0)) {
				if(!(triggerSchedule.getRecurringDay().compareTo(BigInteger.valueOf(-1)) == 0)) {

					logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Recurring Day' value '" + triggerSchedule.getRecurringDay() + "' is invalid.  Valid values are between 1 and 127.");
					throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Recurring Day' value '" + triggerSchedule.getRecurringDay() + "' is invalid.  Valid values are between 1 and 127.");
				}

			}

			//***************************************************************
			// Validate schedule from time in a day and end time in a day
			//***************************************************************
			if(logger.isDebugEnabled()){
				logger.debug("Validating from time in a day and end time in a day ...");
			}
			isValid = false;
			if(triggerSchedule.getFromTimeInADay() == null) {
				logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'FromTimeInADay' is null");
				throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'FromTimeInADay' is null");
			}

			if(triggerSchedule.getFromTimeInADay().compareTo(BigInteger.valueOf(0)) < 0 || triggerSchedule.getFromTimeInADay().compareTo(BigInteger.valueOf(1440)) > 0) {
				if(!(triggerSchedule.getFromTimeInADay().compareTo(BigInteger.valueOf(-1)) == 0)) {
					logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'FromTimeInADay' must have a value between 0 and 1440.  Current value is: " + triggerSchedule.getFromTimeInADay());
					throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'FromTimeInADay' must have a value between 0 and 1440.  Current value is: " + triggerSchedule.getFromTimeInADay());
				}
			}

			if(triggerSchedule.getEndTimeInADay() == null) {
				logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'EndTimeInADay' is null");
				throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'EndTimeInADay' is null");
			}

			if(triggerSchedule.getEndTimeInADay().compareTo(BigInteger.valueOf(0)) < 0 || triggerSchedule.getEndTimeInADay().compareTo(BigInteger.valueOf(1440)) > 0) {
				if(!(triggerSchedule.getEndTimeInADay().compareTo(BigInteger.valueOf(-1)) == 0)) {
					logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'EndTimeInADay' must have a value between 0 and 1440.  Current value is: " + triggerSchedule.getEndTimeInADay());
					throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'EndTimeInADay' must have a value between 0 and 1440.  Current value is: " + triggerSchedule.getEndTimeInADay());
				}
			}
		}

		//***************************************************************
		// Validate schedule mode=NONE.
		//***************************************************************
		isValid = false;
		// Check mode against the XML Schema enum validation list
		if(triggerSchedule.getMode().name().equals(TriggerModeValidationList.NONE.name())) {
			if(logger.isDebugEnabled()){
				logger.debug("Validating schedule mode=NONE ...");
			}
/* MTINIUS: After performing various updateTriggers() from Studio, I have determined that when mode=NONE, the presence or absences of values does not matter.
 * 

			//***************************************************************
			// Elements fromTimeInADay, endTimeInADay and recurringDay must all be -1.
			//***************************************************************
			if(triggerSchedule.getFromTimeInADay() == null) {
				logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has a value of 'NONE'.  Schedule condition property 'FromTimeInADay' must have value -1.  Current value is null");
				throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has a value of 'NONE'.  Schedule condition property 'FromTimeInADay' must have value -1.  Current value is null");
			}

			if(!(triggerSchedule.getFromTimeInADay().compareTo(BigInteger.valueOf(-1)) == 0)) {
				logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has a value of 'NONE'.  Schedule condition property 'FromTimeInADay' must have value -1.  Current value is: " + triggerSchedule.getFromTimeInADay());
				throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has a value of 'NONE'.  Schedule condition property 'FromTimeInADay' must have value -1.  Current value is: " + triggerSchedule.getFromTimeInADay());
			}

			if(triggerSchedule.getEndTimeInADay() == null) {
				logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has a value of 'NONE'.  Schedule condition property 'EndTimeInADay' must have value -1.  Current value is null");
				throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has a value of 'NONE'.  Schedule condition property 'EndTimeInADay' must have value -1.  Current value is null");
			}

			if(!(triggerSchedule.getEndTimeInADay().compareTo(BigInteger.valueOf(-1)) == 0)) {
				logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has a value of 'NONE'.  Schedule condition property 'EndTimeInADay' must have value -1.  Current value is: " + triggerSchedule.getEndTimeInADay());
				throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has a value of 'NONE'.  Schedule condition property 'EndTimeInADay' must have value -1.  Current value is: " + triggerSchedule.getEndTimeInADay());
			}

			if(triggerSchedule.getRecurringDay() == null) {
				logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has a value of 'NONE'.  Schedule condition property 'RecurringDay' must have value -1.  Current value is null");
				throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has a value of 'NONE'.  Schedule condition property 'RecurringDay' must have value -1.  Current value is null");
			}

			if(!(triggerSchedule.getRecurringDay().compareTo(BigInteger.valueOf(-1)) == 0)) {
				logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has a value of 'NONE'.  Schedule condition property 'RecurringDay' must have value -1.  Current value is: " + triggerSchedule.getRecurringDay());
				throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has a value of 'NONE'.  Schedule condition property 'RecurringDay' must have value -1.  Current value is: " + triggerSchedule.getRecurringDay());
			}
			
			//***************************************************************
			// Must also have a valid startTime.
			//***************************************************************
			if(triggerSchedule.getStartTime() == null) {
				logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has a value of 'NONE'.  Schedule condition property 'StartTime' must have a valid value.  Current value is null");
				throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'Mode' has a value of 'NONE'.  Schedule condition property 'StartTime' must have a valid value.  Current value is null");
			}
 */			
		}
				
		//***************************************************************
		// Validate schedule isCluster flag
		//***************************************************************
		if(logger.isDebugEnabled()){
			logger.debug("Validating schedule isCluster ...");
		}
		isValid = false;
		if(triggerSchedule.isIsCluster() == null) {
			logger.error("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'isCluster' must be true or false.  Current value is null");
			throw new ValidationException("Error processing trigger schedule id: " + triggerSchedule.getScheduleId() + ". Schedule condition property 'isCluster' must be true or false.  Current value is null");
		}

		if(logger.isDebugEnabled()){
			logger.debug("Schedule validation completed");
		}
	}

	private AttributeList getStatisticsActionAttributes(TriggerActionGatherStatisticsType triggerAction, String triggerId) {

		AttributeList actionAttributes = null;
		
		if(triggerAction.getResourcePath() == null) {
			logger.error("Error processing trigger id: " + triggerId + ". Must specify datasource path for Gather Statistics action.  Current value is null");
			throw new ValidationException("Error processing trigger id: " + triggerId + ". Must specify datasource path for Gather Statistics action.  Current value is null");
		}
		
		if(triggerAction.getResourcePath().length() == 0) {
			logger.error("Error processing trigger id: " + triggerId + ". Must specify datasource path for Gather Statistics action.  Current value is empty");
			throw new ValidationException("Error processing trigger id: " + triggerId + ". Must specify datasource path for Gather Statistics action.  Current value is empty");
		}
				
		actionAttributes = new AttributeList();
		
		Attribute attrDsPath = new Attribute();
		attrDsPath.setName("PATH");
		attrDsPath.setType(AttributeType.STRING);
		attrDsPath.setValue(triggerAction.getResourcePath());
		actionAttributes.getAttribute().add(attrDsPath);

		return actionAttributes;
	}

	private AttributeList getProcedureActionAttributes(TriggerActionExecuteProcedureType triggerAction, String triggerId) {

		if(triggerAction.getResourcePath() == null)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  Execute Procedure action property 'Procedure Path' is null");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  Execute Procedure action property 'Procedure Path' is null");
		}
		
		if(triggerAction.getResourcePath().length() == 0)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  Execute Procedure action property 'Procedure Path' is empty");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  Execute Procedure action property 'Procedure Path' is empty");
		}

		AttributeList actionAttributes = null;
		
		actionAttributes = new AttributeList();

		//Mandatory
		Attribute attrDsPath = new Attribute();
		attrDsPath.setName("PATH");
		attrDsPath.setType(AttributeType.STRING);
		attrDsPath.setValue(triggerAction.getResourcePath());
		actionAttributes.getAttribute().add(attrDsPath);

		Attribute attrParameters = new Attribute();
		attrParameters.setName("PARAMETERS");
		attrParameters.setType(AttributeType.STRING);
		if(triggerAction.getParameterValues() != null && triggerAction.getParameterValues().length() > 0) {
			attrParameters.setValue(triggerAction.getParameterValues());
		} else {
			attrParameters.setValue("");
		}
		actionAttributes.getAttribute().add(attrParameters);
		
		return actionAttributes;
	}

	private AttributeList getEmailActionAttributes(TriggerActionSendEmailType triggerAction, String triggerId) {

		if(triggerAction.getResourcePath() == null)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  Send Email action property 'Resource Path' is null");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  Send Email action property 'Resource Path' is null");
		}
		
		if(triggerAction.getResourcePath().length() == 0)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  Send Email action property 'Resource Path' is empty");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  Send Email action property 'Resource Path' is empty");
		}

		if(triggerAction.getEmailTo() == null)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  Send Email action property 'To' is null");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  Send Email action property 'To' is null");
		}
		
		if(triggerAction.getEmailTo().length() == 0)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  Send Email action property 'To' is empty");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  Send Email action property 'To' is empty");
		}

		AttributeList actionAttributes = null;
		
		actionAttributes = new AttributeList();
		
		//Mandatory
		Attribute attrDsPath = new Attribute();
		attrDsPath.setName("PATH");
		attrDsPath.setType(AttributeType.STRING);
		attrDsPath.setValue(triggerAction.getResourcePath());
		actionAttributes.getAttribute().add(attrDsPath);

		//Mandatory
		Attribute attrTo = new Attribute();
		attrTo.setName("TO");
		attrTo.setType(AttributeType.STRING);
		attrTo.setValue(triggerAction.getEmailTo());
		actionAttributes.getAttribute().add(attrTo);
		
		Attribute attrCc = new Attribute();
		attrCc.setName("CC");
		if(triggerAction.getEmailCC() != null && triggerAction.getEmailCC().length() > 0) {
			attrCc.setType(AttributeType.STRING);
			attrCc.setValue(triggerAction.getEmailCC());
		} else {
			attrCc.setType(AttributeType.NULL);
		}
		actionAttributes.getAttribute().add(attrCc);
		
		Attribute attrBcc = new Attribute();
		attrBcc.setName("BCC");
		if(triggerAction.getEmailBCC() != null && triggerAction.getEmailBCC().length() > 0) {
			attrBcc.setType(AttributeType.STRING);
			attrBcc.setValue(triggerAction.getEmailBCC());
		} else {
			attrBcc.setType(AttributeType.NULL);
		}
		actionAttributes.getAttribute().add(attrBcc);
		
		Attribute attrReplyTo = new Attribute();
		attrReplyTo.setName("REPLYTO");
		if(triggerAction.getEmailReplyTo() != null && triggerAction.getEmailReplyTo().length() > 0) {
			attrReplyTo.setType(AttributeType.STRING);
			attrReplyTo.setValue(triggerAction.getEmailReplyTo());
		} else {
			attrReplyTo.setType(AttributeType.NULL);
		}
		actionAttributes.getAttribute().add(attrReplyTo);
		
		Attribute attrSubject = new Attribute();
		attrSubject.setName("SUBJECT");
		if(triggerAction.getEmailSubject() != null && triggerAction.getEmailSubject().length() > 0) {
			attrSubject.setType(AttributeType.STRING);
			attrSubject.setValue(triggerAction.getEmailSubject());
		} else {
			attrSubject.setType(AttributeType.NULL);
		}
		actionAttributes.getAttribute().add(attrSubject);
		
		Attribute attrContent = new Attribute();
		attrContent.setName("CONTENT");
		if(triggerAction.getEmailBody() != null && triggerAction.getEmailBody().length() > 0) {
			attrContent.setType(AttributeType.STRING);
			attrContent.setValue(triggerAction.getEmailBody());
		} else {
			attrContent.setType(AttributeType.NULL);
		}
		actionAttributes.getAttribute().add(attrContent);
		
		//Mandatory
		Attribute attrSummary = new Attribute();
		attrSummary.setName("SUMMARY");
		attrSummary.setType(AttributeType.BOOLEAN);
		if(triggerAction.isIncludeSummary()) {
			attrSummary.setValue("true");
		} else {
			attrSummary.setValue("false");
		}
		actionAttributes.getAttribute().add(attrSummary);
		
		//Mandatory
		Attribute attrNoResults = new Attribute();
		attrNoResults.setName("SKIP_IF_NO_RESULTS");
		attrNoResults.setType(AttributeType.BOOLEAN);
		if(triggerAction.isSkipIfNoResults()) {
			attrNoResults.setValue("true");
		} else {
			attrNoResults.setValue("false");
		}
		actionAttributes.getAttribute().add(attrNoResults);

		Attribute attrParameters = new Attribute();
		attrParameters.setName("PARAMETERS");
		if(triggerAction.getParameterValues() != null && triggerAction.getParameterValues().length() > 0) {
			attrParameters.setType(AttributeType.STRING);
			attrParameters.setValue(triggerAction.getParameterValues());
		} else {
			attrParameters.setType(AttributeType.NULL);
		}
		actionAttributes.getAttribute().add(attrParameters);

		//Mandatory
		Attribute attrType = new Attribute();
		attrType.setName("TYPE");
		attrType.setType(AttributeType.STRING);
		attrType.setValue("PROCEDURE");
		actionAttributes.getAttribute().add(attrType);

		return actionAttributes;
	}

	private AttributeList getReintrospectActionAttributes(TriggerActionReintrospectDatasourceType triggerAction, String triggerId) {

		if(triggerAction.getResourcePath() == null)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  Reintrospect Datasource action property 'Data Source Path' is null");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  Reinstrospect Datasource action property 'Data Source Path' is null");
		}
		
		if(triggerAction.getResourcePath().length() == 0)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  Reintrospect Datasource action property 'Data Source Path' is empty");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  Reintrospect Datasource action property 'Data Source Path' is empty");
		}

		if(triggerAction.getEmailTo() == null)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  Reintrospect Datasource action property 'To' is null");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  Reintrospect Datasource action property 'To' is null");
		}
		
		if(triggerAction.getEmailTo().length() == 0)
		{
			logger.error("Error processing trigger id: " + triggerId + ".  Reintrospect Datasource action property 'To' is empty");
			throw new ValidationException("Error processing trigger id: " + triggerId + ".  Reintrospect Datasource action property 'To' is empty");
		}

		AttributeList actionAttributes = null;
		
		actionAttributes = new AttributeList();
		
		//Mandatory
		Attribute attrDsPath = new Attribute();
		attrDsPath.setName("PATH");
		attrDsPath.setType(AttributeType.STRING);
		attrDsPath.setValue(triggerAction.getResourcePath());
		actionAttributes.getAttribute().add(attrDsPath);

		//Mandatory
		Attribute attrTo = new Attribute();
		attrTo.setName("TO");
		attrTo.setType(AttributeType.STRING);
		attrTo.setValue(triggerAction.getEmailTo());
		actionAttributes.getAttribute().add(attrTo);
		
		Attribute attrCc = new Attribute();
		attrCc.setName("CC");
		if(triggerAction.getEmailCC() != null && triggerAction.getEmailCC().length() > 0) {
			attrCc.setType(AttributeType.STRING);
			attrCc.setValue(triggerAction.getEmailCC());
		} else {
			attrCc.setType(AttributeType.NULL);
		}
		actionAttributes.getAttribute().add(attrCc);
		
		Attribute attrBcc = new Attribute();
		attrBcc.setName("BCC");
		if(triggerAction.getEmailBCC() != null && triggerAction.getEmailBCC().length() > 0) {
			attrBcc.setType(AttributeType.STRING);
			attrBcc.setValue(triggerAction.getEmailBCC());
		} else {
			attrBcc.setType(AttributeType.NULL);
		}
		actionAttributes.getAttribute().add(attrBcc);
		
		Attribute attrReplyTo = new Attribute();
		attrReplyTo.setName("REPLYTO");
		if(triggerAction.getEmailReplyTo() != null && triggerAction.getEmailReplyTo().length() > 0) {
			attrReplyTo.setType(AttributeType.STRING);
			attrReplyTo.setValue(triggerAction.getEmailReplyTo());
		} else {
			attrReplyTo.setType(AttributeType.NULL);
		}
		actionAttributes.getAttribute().add(attrReplyTo);
		
		Attribute attrSubject = new Attribute();
		attrSubject.setName("SUBJECT");
		if(triggerAction.getEmailSubject() != null && triggerAction.getEmailSubject().length() > 0) {
			attrSubject.setType(AttributeType.STRING);
			attrSubject.setValue(triggerAction.getEmailSubject());
		} else {
			attrSubject.setType(AttributeType.NULL);
		}
		actionAttributes.getAttribute().add(attrSubject);
		
		Attribute attrContent = new Attribute();
		attrContent.setName("CONTENT");
		if(triggerAction.getEmailBody() != null && triggerAction.getEmailBody().length() > 0) {
			attrContent.setType(AttributeType.STRING);
			attrContent.setValue(triggerAction.getEmailBody());
		} else {
			attrContent.setType(AttributeType.NULL);
		}
		actionAttributes.getAttribute().add(attrContent);
		
		//Mandatory
		Attribute attrNoCommit = new Attribute();
		attrNoCommit.setName("NO_COMMIT");
		attrNoCommit.setType(AttributeType.BOOLEAN);
		if(triggerAction.isNoCommit()) {
			attrNoCommit.setValue("true");
		} else {
			attrNoCommit.setValue("false");
		}
		actionAttributes.getAttribute().add(attrNoCommit);
		
		//Mandatory
		Attribute attrNoResults = new Attribute();
		attrNoResults.setName("SKIP_IF_NO_RESULTS");
		attrNoResults.setType(AttributeType.BOOLEAN);
		if(triggerAction.isSkipIfNoResults()) {
			attrNoResults.setValue("true");
		} else {
			attrNoResults.setValue("false");
		}
		actionAttributes.getAttribute().add(attrNoResults);
		
		return actionAttributes;
	}

	
	public TriggerDAO getTriggerDAO() {
		if(triggerDAO == null){
			triggerDAO = new TriggerWSDAOImpl();
		}
		return triggerDAO;
	}

	public void setTriggerDAO(TriggerDAO triggerDAO) {
		this.triggerDAO = triggerDAO;
	}

}
