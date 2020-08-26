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
package com.tibco.ps.deploytool.dao.wsapi;

import java.math.BigInteger;
import java.util.List;

import javax.xml.ws.Holder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tibco.ps.common.CommonConstants;
import com.tibco.ps.common.exception.ApplicationException;
import com.tibco.ps.common.exception.CompositeException;
import com.tibco.ps.common.util.CommonUtils;
import com.tibco.ps.common.util.CompositeLogger;
import com.tibco.ps.common.util.wsapi.CisApiFactory;
import com.tibco.ps.common.util.wsapi.CompositeServer;
import com.tibco.ps.common.util.wsapi.WsApiHelperObjects;
import com.tibco.ps.deploytool.DeployManagerUtil;
import com.tibco.ps.deploytool.dao.DataSourceDAO;
import com.tibco.ps.deploytool.dao.ResourceDAO;
import com.tibco.ps.deploytool.util.DeployUtil;
import com.compositesw.services.system.admin.GetChildResourcesSoapFault;
import com.compositesw.services.system.admin.GetDataSourceAttributeDefsSoapFault;
import com.compositesw.services.system.admin.GetDataSourceTypesSoapFault;
import com.compositesw.services.system.admin.IntrospectResourcesResultSoapFault;
import com.compositesw.services.system.admin.IntrospectResourcesTaskSoapFault;
import com.compositesw.services.system.admin.ReintrospectDataSourceSoapFault;
import com.compositesw.services.system.admin.ResourcePortType;
import com.compositesw.services.system.admin.UpdateDataSourceSoapFault;
import com.compositesw.services.system.admin.UpdateResourceEnabledSoapFault;
import com.compositesw.services.system.admin.resource.DataSourceTypeInfo;
import com.compositesw.services.system.admin.resource.GetDataSourceTypesResponse.DataSourceTypes;
import com.compositesw.services.system.admin.resource.IntrospectionChangeEntry;
import com.compositesw.services.system.admin.resource.IntrospectionPlan;
import com.compositesw.services.system.admin.resource.IntrospectionPlanEntry;
import com.compositesw.services.system.admin.resource.IntrospectionStatus;
import com.compositesw.services.system.admin.resource.Resource;
import com.compositesw.services.system.admin.resource.ResourceList;
import com.compositesw.services.system.admin.resource.ResourceType;
import com.compositesw.services.system.util.common.Attribute;
import com.compositesw.services.system.util.common.AttributeDef;
import com.compositesw.services.system.util.common.AttributeDefList;
import com.compositesw.services.system.util.common.AttributeList;
import com.compositesw.services.system.util.common.DetailLevel;
import com.compositesw.services.system.util.common.MessageEntry;
import com.compositesw.services.system.util.common.Page;

/*
 * port.introspectResourcesResult(taskId, block, page, DetailLevel.FULL, totalResults, completed, status);
 * 
Gets the results from the introspectResourcesTask operation.  The number of
results returned is limited by page size and total
number of known fields.

Results are in the form of introspection change entries, which contain the
path, type, and subtype of the resource that was introspected, the introspection
action that occurred, and a message, if available, regarding introspection of that resource.

Subsequent calls to this operation incrementally return the full set of results.

If the block element is set and TRUE, then this operation will block until the task is complete.
Otherwise, this operation will not block.

The page size controls the maximum number of change entries that will be returned from this call.

The page start determines which result will be returned first.  This can be used to jump ahead in the
result list.  This jump is relative to the current position.  If a non-zero page start is provided
with every call to this operation, then this will have the effect of skipping page-size number of results
every call.

Specifying the page start sets the position where you would like to start receiving results.
Subsequent calls to this operation will start from that point.  If an insufficient number
of results have been found during a call to this operation, then this operation will either
timeout or return with completed equal TRUE.  In either case, an empty list of results would be returned.

This operation returns a taskId that can be used to get results using the
getIntrospectableResourceFieldResult operation or canceled using the cancelServerTask
operation.

This operation returns the total number of results in the totalResults element.  If it is
not know what the total number of results is, then this element will be unset.

This operation returns a completed element that indicates whether or not processing has completed
and these are the last results the caller will get.  If TRUE, then a subsequent call to
getIntrospectableResourceFieldsResult will generate a NotFound fault.

This operation returns the introspection status in the status element.  If the detail element is NONE,
then minimal information is returned to indicate the running state of the task. If SIMPLE, then overall
status, and counts are returned. If FULL, then the list of introspection change entries is returned.  The
status startTime element will only be set after introspection has started. The status endTime element will
only be set when introspected has completed.  The list of introspection change entries will only contain
entries for newly added messages or resource identifiers since the last call to the introspectResourcesResult
operation.

Request Elements:
    taskId: The server task ID associated with the original call.
    block: Whether or not to block until processing is complete.  Defaults to false if unset.
    page:
        size: The number of resource identifiers to return in the result.
        start: The page number to start retrieving data from.  Defaults to 0.
    detailLevel: Detail level. May be NONE, SIMPLE, or FULL.

Response Elements:
    taskId: The server task ID associated with the original call.
    totalResults: If known, the total result set size.  Otherwise this element will not exist.
    completed: True if processing is completed and the result set has been exhausted.
    status: The introspection status report.

Faults:
    DataSourceError: If a data source connection cannot be established or if a data
       source request returns an error.
    IllegalArgument: If the taskId, page, or detailLevel is malformed.
    IllegalState: If the data source is disabled.
    NotFound: If the taskId does not exist or has completed.
    Security: If the user does not have the ACCESS_TOOLS right.
    Security: If a different CIS session was used to create the server task.
*/

public class DataSourceWSDAOImpl implements DataSourceDAO {

	private ResourceDAO resourceDAO = null;

	private static Log logger = LogFactory.getLog(DataSourceWSDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.dao.DataSourceDAO#takeDataSourceAction(java.lang.String,java.lang.String, com.compositesw.services.system.util.common.AttributeList, java.lang.String, java.lang.String)
	 */
	public ResourceList takeDataSourceAction(String actionName, String dataSourcePath, IntrospectionPlan plan, boolean runInBackgroundTransaction, String reportDetail, AttributeList dataSourceAttributes, String serverId, String pathToServersXML) throws CompositeException {
		
		// For debugging
		if(logger.isDebugEnabled()) {
			int planSize = 0;
			if (plan != null && plan.getEntries() != null && plan.getEntries().getEntry() != null)
				planSize = plan.getEntries().getEntry().size();
			int attrSize = 0;
			if (dataSourceAttributes != null && dataSourceAttributes.getAttribute() != null)
				attrSize = dataSourceAttributes.getAttribute().size();
			logger.debug("DataSourceWSDAOImpl.takeDataSourceAction(actionName , dataSourcePath, plan, runInBackgroundTransaction, reportDetail, dataSourceAttributes, serverId, pathToServersXML).  actionName="+actionName+"  dataSourcePath="+dataSourcePath+"  #plans="+planSize+"  runInBackgroundTransaction="+runInBackgroundTransaction+"  reportDetail="+reportDetail+"  #dataSourceAttributes="+attrSize+"  serverId="+serverId+"  pathToServersXML="+pathToServersXML);
		}

		// Declare variables
		ResourceList returnResList = null;
		String command = null;
		
		// read target server properties from xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "DataSourceWSAOImpl.takeDataSourceAction("+actionName+")", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);
		
		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			// Make sure the resource exists before executing any actions
			if (DeployManagerUtil.getDeployManager().resourceExists(serverId, dataSourcePath, ResourceType.DATA_SOURCE.name(), pathToServersXML)) {
				/*********************************
				 *  UPDATE datasource
				 *********************************/
				if(actionName.equalsIgnoreCase(DataSourceDAO.action.UPDATE.name()))
				{
					command = "updateDataSource";
					
					if(logger.isDebugEnabled()) {
						int dsAttrSize = 0;
						String dsAttrString = "";
						if (dataSourceAttributes != null && dataSourceAttributes.getAttribute() != null) {
							for (Attribute attr:dataSourceAttributes.getAttribute()) {
								if (dsAttrString.length() != 0)
									dsAttrString = dsAttrString + ", ";
								if (attr.getType().toString().equals("PASSWORD_STRING"))
									dsAttrString = dsAttrString + attr.getName() + "=********";
								else
									dsAttrString = dsAttrString + attr.getName() + "=" + attr.getValue();
							}
							dsAttrSize = dataSourceAttributes.getAttribute().size();
						}
						logger.debug("DataSourceWSDAOImpl.takeDataSourceAction(\""+actionName+"\").  Invoking port.updateDataSource(\""+dataSourcePath+"\", \"FULL\", null, DS_ATTRIBUTES:[\""+dsAttrString+"]\").  #dataSourceAttributes="+dsAttrSize);
					}
					
					// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
					if (CommonUtils.isExecOperation()) {					
						returnResList = port.updateDataSource(dataSourcePath, DetailLevel.FULL, null, dataSourceAttributes);
						
						if(logger.isDebugEnabled()) {
							logger.debug("DataSourceWSDAOImpl.takeDataSourceAction(\""+actionName+"\").  Success: port.updateDataSource().");
						}
					} else {
						logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
					}
				}
				/*********************************
				 *  ENABLE datasource
				 *********************************/
				else if(actionName.equalsIgnoreCase(DataSourceDAO.action.ENABLE.name()))
				{
					command = "updateResourceEnabled";
					
					if(logger.isDebugEnabled()) {
						logger.debug("DataSourceWSDAOImpl.takeDataSourceAction(\""+actionName+"\").  Invoking port.updateResourceEnabled(\""+dataSourcePath+"\", \"DATA_SOURCE\", \"FULL\", true).");
					}
					
					// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
					if (CommonUtils.isExecOperation()) {					
						returnResList = port.updateResourceEnabled(dataSourcePath, ResourceType.DATA_SOURCE, DetailLevel.FULL, true);			
						
						if(logger.isDebugEnabled()) {
							logger.debug("DataSourceWSDAOImpl.takeDataSourceAction(\""+actionName+"\").  Success: port.updateResourceEnabled().");
						}
					} else {
						logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
					}
				}
				/*********************************
				 *  REINTROSPECT datasource
				 *********************************/
				else if(actionName.equalsIgnoreCase(DataSourceDAO.action.REINTROSPECT.name()))
				{
					command = "reintrospectDataSource";
					
					if(logger.isDebugEnabled()) {
						logger.debug("DataSourceWSDAOImpl.takeDataSourceAction(\""+actionName+"\").  Invoking port.reintrospectDataSource(\""+dataSourcePath+"\", true, null, null, null, null).");
					}

					// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
					if (CommonUtils.isExecOperation()) {					
						// Errors were being thrown when attributes were present for dataSourceAttributes.  Setting to null.
						//port.reintrospectDataSource(dataSourcePath, true, dataSourceAttributes, null, null, null);	
						port.reintrospectDataSource(dataSourcePath, true, null, null, null, null);			

						if(logger.isDebugEnabled()) {
							logger.debug("DataSourceWSDAOImpl.takeDataSourceAction(\""+actionName+"\").  Success: port.reintrospectDataSource().");
						}
					} else {
						logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
					}
				}
				/*********************************
				 *  INTROSPECT datasource
				 *********************************/
				else if(actionName.equalsIgnoreCase(DataSourceDAO.action.INTROSPECT.name())){
					command = "introspectResourcesTask";
					
					Holder<String> taskId = new Holder<String>();
					Holder<BigInteger> totalResults = new Holder<BigInteger>();
					Holder<Boolean> completed = new Holder<Boolean>();
					

					if(logger.isDebugEnabled()) {
						int dsAttrSize = 0;
						String dsAttrString = "";
						if (dataSourceAttributes != null && dataSourceAttributes.getAttribute() != null) {
							for (Attribute attr:dataSourceAttributes.getAttribute()) {
								if (dsAttrString.length() != 0)
									dsAttrString = dsAttrString + ", ";
								if (attr.getType().toString().equals("PASSWORD_STRING"))
									dsAttrString = dsAttrString + attr.getName() + "=********";
								else
									dsAttrString = dsAttrString + attr.getName() + "=" + attr.getValue();
							}
							dsAttrSize = dataSourceAttributes.getAttribute().size();
						}
						String planString = "";
						if (plan != null && plan.getEntries() != null && plan.getEntries().getEntry() != null) {
							for (IntrospectionPlanEntry entry:plan.getEntries().getEntry()) {
								if (planString.length() != 0)
									planString = "], " + planString;
								planString = planString + "PLAN:[";
								planString = planString + "  action=" + entry.getAction();
								planString = planString + "  path=" + entry.getResourceId().getPath();
								planString = planString + "  type=" + entry.getResourceId().getType();
								String attrList = "";
								if (entry.getAttributes() != null && entry.getAttributes().getAttribute() != null) {
									for (Attribute attr:entry.getAttributes().getAttribute()) {
										if (attrList.length() != 0)
											attrList = attrList + ", ";
										if (attr.getType().toString().equalsIgnoreCase("PASSWORD_STRING"))
											attrList = attrList + attr.getName() + "=********";
										else
											attrList = attrList + attr.getName() + "=" + attr.getValue();	
									}		
								}
								planString = planString + "  " + attrList + "]";
							}
						}
						logger.debug("DataSourceWSDAOImpl.takeDataSourceAction(\""+actionName+"\").  Invoking port.introspectResourcesTask(\""+dataSourcePath+"\", \""+planString+"\", \""+runInBackgroundTransaction+"\", DS_ATTRIBUTES:[\""+dsAttrString+"]\", taskId, totalResults, completed).   #dataSourceAttributes="+dsAttrSize);
					}

					// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
					if (CommonUtils.isExecOperation()) {					
						// Invoke the method to introspect and add, update or remove data source resources
						port.introspectResourcesTask(dataSourcePath, plan, runInBackgroundTransaction, dataSourceAttributes, taskId, totalResults, completed);

						if(logger.isDebugEnabled()) {
							logger.debug("DataSourceWSDAOImpl.takeDataSourceAction(\""+actionName+"\").  Success: port.introspectResourcesTask().");
						}

						Boolean block = true; // Make this a blocking call
						Page page = new Page();
				
						Holder<IntrospectionStatus> status = new Holder<IntrospectionStatus>();
						
						if(logger.isDebugEnabled()) {
							String simpleStatus = "";
							if (status != null && status.value != null && status.value.getStatus() != null)
								simpleStatus = status.value.getStatus().value();										
							logger.debug("DataSourceWSDAOImpl.takeDataSourceAction(\""+actionName+"\").  Invoking port.introspectResourcesResult(\""+taskId.value.toString()+"\", \""+block.toString()+"\", page, \"FULL\", \""+totalResults.value.toString()+"\", \""+completed.value.toString()+"\", \""+simpleStatus+"\").");
						}

						// Since a blocking call is used, a single call to get results is all that is needed
						port.introspectResourcesResult(taskId, block, page, DetailLevel.FULL, totalResults, completed, status);

						if(logger.isDebugEnabled()) {
							logger.debug("DataSourceWSDAOImpl.takeDataSourceAction(\""+actionName+"\").  Success: port.introspectResourcesResult().");
						}
	
						boolean errorDetected = false;
						String errorEntryPaths = "";
						
						// Check the status and print out the report
						if (status != null && status.value != null && status.value.getStatus() != null) {
							String simpleStatus = status.value.getStatus().value();										
							// Print out a status report
							logger.info("Introspection Report ("+reportDetail+"):");
							logger.info("          Status="+simpleStatus);
							logger.info("      Start Time="+status.value.getStartTime().toString());
							logger.info("        End Time="+status.value.getEndTime().toString());
							logger.info("           Added="+status.value.getAddedCount());
							logger.info("         Removed="+status.value.getRemovedCount());
							logger.info("         Updated="+status.value.getUpdatedCount());
							logger.info("         Skipped="+status.value.getSkippedCount());
							logger.info("       Completed="+status.value.getTotalCompletedCount());
							logger.info("         Warning="+status.value.getWarningCount());
							logger.info("          Errors="+status.value.getErrorCount());	
							logger.info("");
							if (status.value.getReport() != null) {	
								List<IntrospectionChangeEntry> reportEntries = status.value.getReport().getEntry();
								// Iterate over the report entries
								for (IntrospectionChangeEntry reportEntry : reportEntries) {
									// Print out the Resource and Status on 2 separate lines with a blank line separator following
									if (reportDetail.equals("SIMPLE") || reportDetail.equals("FULL")) {
										logger.info("   RESOURCE:  Path="+reportEntry.getPath()+"   Type="+reportEntry.getType().value()+"   Subtype="+reportEntry.getSubtype().value());
										logger.info("     STATUS:  Status="+reportEntry.getStatus().value()+"   Action="+reportEntry.getAction().value()+"   Duration="+reportEntry.getDurationMs());
									}
									// Print out the Resource and Status as a single line with no blank lines following
									if (reportDetail.equals("SIMPLE_COMPRESSED")) {
										logger.info("   RESOURCE:  Path="+reportEntry.getPath()+"   Type="+reportEntry.getType().value()+"   Subtype="+reportEntry.getSubtype().value()+
													"   [STATUS]:  Status="+reportEntry.getStatus().value()+"   Action="+reportEntry.getAction().value()+"   Duration="+reportEntry.getDurationMs());
									}
									boolean entryErrorDetected = false;
									if (reportEntry.getStatus().value().equalsIgnoreCase("ERROR")) {
										errorDetected = true;
										entryErrorDetected = true;
										if (errorEntryPaths.length() > 0) 
											errorEntryPaths = errorEntryPaths + ", ";
										errorEntryPaths = errorEntryPaths + reportEntry.getPath();
									}
									if (entryErrorDetected || reportDetail.equals("FULL")) {
										if (reportEntry.getMessages() != null) {
											List<MessageEntry> messages = reportEntry.getMessages().getEntry();
											for (MessageEntry message : messages) {
												String severity = "";
												String code = "";
												String name = "";
												String msg = "";
												
												if (message.getSeverity() != null && message.getSeverity().value().length() > 0)
													severity = "  Severity="+message.getSeverity().value();
												if (message.getCode() != null && message.getCode().length() > 0)
													code = "   Code="+message.getCode();
												if (message.getName() != null && message.getName().length() > 0)
													name = "   Name="+message.getName();
												if (message.getMessage() != null && message.getMessage().length() > 0)
													msg = "   Message="+message.getMessage();
												logger.info("   MESSAGES:"+severity+code+name+msg);
												if (message.getDetail() != null)
													logger.info("   MESSAGES:  Detail="+message.getDetail());		
											}
										}	
									}
									if (reportDetail.equals("SIMPLE") || reportDetail.equals("FULL")) {
										logger.info("");
									}
								}
							}
							if (errorDetected) {
								throw new ApplicationException("Resource action="+DataSourceDAO.action.INTROSPECT.name()+" was not successful.  Review the introspection report in the log for more details.  Introspection Entry Paths with errors="+errorEntryPaths);
							}
						} else {
							// Since the status was null, then assume the server is 6.1 which contains a bug where the status has the wrong namespace.
							// Based on the input of ADD_OR_UPDATE, ADD_OR_UPDATE_RECURSIVELY, or REMOVE, query the resources to determine if the operation was successful.
	
							// Print out a status report
							logger.info("Introspection Report ("+reportDetail+"):");
							logger.info("      Start Time="+status.value.getStartTime().toString());
							logger.info("        End Time="+status.value.getEndTime().toString());
							logger.info("           Added="+status.value.getAddedCount());
							logger.info("         Removed="+status.value.getRemovedCount());
							logger.info("         Updated="+status.value.getUpdatedCount());
							logger.info("         Skipped="+status.value.getSkippedCount());
							logger.info("       Completed="+status.value.getTotalCompletedCount());
							logger.info("         Warning="+status.value.getWarningCount());
							logger.info("          Errors="+status.value.getErrorCount());	
							logger.info("");
	
							List<IntrospectionPlanEntry> planEntries = plan.getEntries().getEntry();
							for (IntrospectionPlanEntry planEntry : planEntries) {
								String planAction = planEntry.getAction().value().toString();
								String resourcePath = dataSourcePath;
								if (planEntry.getResourceId().getPath()!= null && planEntry.getResourceId().getPath().length() > 0)
									resourcePath = resourcePath+"/"+planEntry.getResourceId().getPath();
								String resourceType = null;
								if (planEntry.getResourceId().getType() != null)
									resourceType = planEntry.getResourceId().getType().toString();
								String subtype = null;
								if (planEntry.getResourceId().getSubtype() != null)
									subtype = planEntry.getResourceId().getSubtype().toString();
								String planStatus = "";
								
								// Print out the Resource and Status on 2 separate lines with a blank line separator following (this is the first line.)
								if (reportDetail.equals("SIMPLE") || reportDetail.equals("FULL")) {
									logger.info("   RESOURCE:  Path="+resourcePath+"   Type="+resourceType+"   Subtype="+subtype);
								}
								
								//Determine if this plan entry exists
								if (planAction.equalsIgnoreCase("ADD_OR_UPDATE")) {
									boolean exists = getResourceDAO().resourceExists(serverId, resourcePath, resourceType, pathToServersXML);
									if (!exists) {
										throw new ApplicationException("Resource action="+DataSourceDAO.action.INTROSPECT.name()+" was not successful.  The resource ["+resourcePath+"] does not exist for the requested plan entry action ["+planEntry.getAction().value().toString()+"].");									
									}
									planStatus = "SUCCESS";
								}
								//Just get the list of resources for the log. [there is no way to tell whether this was successful or not.]
								else if (planAction.equalsIgnoreCase("ADD_OR_UPDATE_RECURSIVELY")) {
									ResourceList resourceList = getResourceDAO().getResourcesFromPath(serverId, resourcePath, resourceType, null, "SIMPLE", pathToServersXML);
									if (resourceList != null && resourceList.getResource().size() > 0) {
										for (Resource resource : resourceList.getResource()) {
											if (resource != null && reportDetail.equals("FULL")) {
												logger.info("      CHILD RESOURCE:  Path="+resource.getPath()+"   Type="+resource.getType()+"   Subtype="+resource.getSubtype());
											}
										}
									}
									planStatus = "SUCCESS";
								}
								//Determine if this plan has been removed
								else if (planAction.equalsIgnoreCase("REMOVE")) {
									boolean exists = getResourceDAO().resourceExists(serverId, resourcePath, resourceType, pathToServersXML);
									if (exists) {
										throw new ApplicationException("Resource action="+DataSourceDAO.action.INTROSPECT.name()+" was not successful.  The resource ["+resourcePath+"] was not removed for the requested plan entry action ["+planEntry.getAction().value().toString()+"].");									
									}								
									planStatus = "SUCCESS";
								}
								else {
									throw new ApplicationException("Resource action="+DataSourceDAO.action.INTROSPECT.name()+" was not successful.  The status field is null and the plan entry action ["+planEntry.getAction().value().toString()+"] is unknown.");
								}
								
								// Print out the Resource and Status on 2 separate lines with a blank line separator following (this is the second line).
								if (reportDetail.equals("SIMPLE") || reportDetail.equals("FULL")) {
									logger.info("     STATUS:  Status="+planStatus+"   Action="+planAction);
								}
								// Print out the Resource and Status as a single line with no blank lines following
								if (reportDetail.equals("SIMPLE_COMPRESSED")) {
									logger.info("   RESOURCE:  Path="+resourcePath+"   Type="+resourceType+"   Subtype="+subtype+"   [STATUS]:  Status="+planStatus+"   Action="+planAction);
								}
								// Print out a blank line when report detail is SIMPLE or FULL
								if (reportDetail.equals("SIMPLE") || reportDetail.equals("FULL")) {
									logger.info("");
								}
							}
						}	
					} else {
						logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
					}
				}
				if(logger.isDebugEnabled() && returnResList != null)
				{
					logger.debug("DataSourceWSDAOImpl.takeDataSourceAction::returnResList.getResource().size()="+returnResList.getResource().size());
				}
			} else {
				throw new ApplicationException("The resource "+dataSourcePath+" does not exist.");
			}

		} catch (UpdateDataSourceSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), DataSourceDAO.action.UPDATE.name(), "DataSource", dataSourcePath, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);

		} catch (UpdateResourceEnabledSoapFault e){
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), DataSourceDAO.action.ENABLE.name(), "DataSource", dataSourcePath, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		
		} catch (ReintrospectDataSourceSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), DataSourceDAO.action.REINTROSPECT.name(), "DataSource", dataSourcePath, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		} catch (IntrospectResourcesTaskSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), DataSourceDAO.action.INTROSPECT.name(), "DataSource", pathToServersXML, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		} catch (IntrospectResourcesResultSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), DataSourceDAO.action.INTROSPECT.name(), "DataSource", pathToServersXML, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		} catch (Exception e) {
			throw new ApplicationException(e.getMessage(), e);

		}
		return returnResList;
	}
	
	public ResourceList getDataSourceChildResourcesFromPath(String serverId, String resourcePath, String parentResourceType, String resourceTypeFilter, boolean includeContainers, String detailLevel, String pathToServersXML) throws CompositeException {

		if(logger.isDebugEnabled()) {
			logger.debug("DataSourceWSDAOImpl.getDataSourceChildResourcesFromPath(serverId, resourcePath, parentResourceType, resourceTypeFilter, includeContainers, detailLevel, pathToServersXML).  serverId="+serverId+"  resourcePath="+resourcePath+"  parentResourceType="+parentResourceType+"  resourceTypeFilter="+resourceTypeFilter+"  includeContainers="+includeContainers+"  detailLevel="+detailLevel+"  pathToServersXML="+pathToServersXML);
		}
		// Make sure the resource exists before executing any actions
		if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, parentResourceType, pathToServersXML)) {
			return getAllDataSourceChildResourcesFromPath(serverId, resourcePath, parentResourceType, resourceTypeFilter, includeContainers, detailLevel, pathToServersXML, true);
		} else {
			throw new ApplicationException("The resource "+resourcePath+" does not exist.");
		}
	}

	private void getDataSourceChildResourcesFromPath(ResourcePortType port,ResourceList resourceList, String resourcePath, String parentResourceType, String resourceTypeFilter, boolean includeContainers, String detailLevel,CompositeServer targetServer, boolean recurse){
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("DataSourceWSDAOImpl.getDataSourceChildResourcesFromPath().  Invoking port.getChildResources(\""+resourcePath+"\", \""+parentResourceType+"\", \""+detailLevel+"\").");
			}
			ResourceList childResourceList = port.getChildResources(resourcePath, ResourceType.valueOf(parentResourceType), DetailLevel.fromValue(detailLevel));

			if(logger.isDebugEnabled()) {
				logger.debug("DataSourceWSDAOImpl.getDataSourceChildResourcesFromPath().  Success: port.getChildResources().");
			}

			if(childResourceList!= null && childResourceList.getResource() != null && !childResourceList.getResource().isEmpty()){

				List<Resource> resources = childResourceList.getResource();

				for (Resource resource : resources) {

					// [mtinius: 8/19/2013 - this logic will handle any data source with different containers.  
					//		Databases have CATALOG_CONTAINER and SCHEMA_CONTAINER.
					//		Web services have SERVICE_CONTAINER, PORT_CONTAINER and OPERATIONS_CONTAINER
					//		JMS data source has a CONNECTOR_CONTAINER
//					if( (resource.getSubtype().equals(ResourceSubType.SCHEMA_CONTAINER) || resource.getSubtype().equals(ResourceSubType.CATALOG_CONTAINER)) && recurse){
					if(resource.getType().equals(ResourceType.CONTAINER) && recurse){
						
						// Only output the container information when includeContainers is set to true
						if (includeContainers) {
							resourceList.getResource().add(resource);
						}

						// Get the children of the container
						getDataSourceChildResourcesFromPath(port, resourceList,resource.getPath(), ResourceType.CONTAINER.name(), resourceTypeFilter, includeContainers, detailLevel, targetServer, recurse);

					}else if(resourceTypeFilter == null || (resource.getType() != null && resourceTypeFilter.contains(resource.getType().name()))){

						resourceList.getResource().add(resource);
					}
				}
			}
		} catch (GetChildResourcesSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getDataSourceChildResourcesFromPath", "DataSource", resourcePath, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}		
	}

	private ResourceList getAllDataSourceChildResourcesFromPath(String serverId, String resourcePath, String parentResourceType, String resourceTypeFilter, boolean includeContainers, String detailLevel, String pathToServersXML, boolean recurse) {

		ResourceList returnResourceList = new ResourceList();
		
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		getDataSourceChildResourcesFromPath(port, returnResourceList, resourcePath, parentResourceType, resourceTypeFilter, includeContainers, detailLevel, targetServer, recurse);
		
		return returnResourceList;
	}
	
	public List<AttributeDef> getDataSourceAttributeDefs(String serverId, String pathToServersXML, String dataSourceType) throws CompositeException {
		
		if (serverId == null || pathToServersXML == null || dataSourceType == null) {
			return null;
		}
		// read target server properties from server xml and build server object based on server name 
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);
		
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("DataSourceWSDAOImpl.getDataSourceAttributeDefs().  Invoking port.getDataSourceAttributeDefs(\""+dataSourceType+"\").");
			}
			AttributeDefList attributeDefsList = port.getDataSourceAttributeDefs(dataSourceType);
	
			if(logger.isDebugEnabled()) {
				logger.debug("DataSourceWSDAOImpl.getDataSourceAttributeDefs().  Success: port.getDataSourceAttributeDefs().");
			}

			if(attributeDefsList != null ) {
				return attributeDefsList.getAttributeDef();
			}
		} catch (GetDataSourceAttributeDefsSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getDataSourceAttributeDefs", "DataSource", pathToServersXML, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}
		
		return null;
	}
	
	public List<DataSourceTypeInfo> getDataSourceTypes(String serverId, String pathToServersXML) throws CompositeException {
		
		if (serverId == null || pathToServersXML == null) {
			return null;
		}
		// read target server properties from server xml and build server object based on server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "DataSourceWSAOImpl.getDataSourceAttributeDefs", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);
		
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("DataSourceWSDAOImpl.getDataSourceTypes().  Invoking port.getDataSourceTypes(\"FULL\").");
			}
			DataSourceTypes dsTypes = port.getDataSourceTypes(DetailLevel.FULL);
		
			if(logger.isDebugEnabled()) {
				logger.debug("DataSourceWSDAOImpl.getDataSourceTypes().  Success: port.getDataSourceTypes().");
			}

			if(dsTypes != null ) {
				return dsTypes.getDataSourceType();
			}
		} catch (GetDataSourceTypesSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getDataSourceTypes", "DataSource", pathToServersXML, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * @return the resourceDAO
	 */
	public ResourceDAO getResourceDAO() {
		if(this.resourceDAO == null){
			this.resourceDAO = new ResourceWSDAOImpl();
		}
		return resourceDAO;
	}
}