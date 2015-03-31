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
package com.cisco.dvbu.ps.deploytool.dao.wsapi;

import java.math.BigInteger;
import java.util.List;

import javax.xml.ws.Holder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CompositeLogger;
import com.cisco.dvbu.ps.common.util.wsapi.CisApiFactory;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.DeployManagerUtil;
import com.cisco.dvbu.ps.deploytool.dao.ResourceDAO;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.compositesw.services.system.admin.CopyResourceSoapFault;
import com.compositesw.services.system.admin.CreateResourceSoapFault;
import com.compositesw.services.system.admin.DestroyResourceSoapFault;
import com.compositesw.services.system.admin.ExecutePortType;
import com.compositesw.services.system.admin.ExecuteSqlSoapFault;
import com.compositesw.services.system.admin.GetAllResourcesByPathSoapFault;
import com.compositesw.services.system.admin.GetChildResourcesSoapFault;
import com.compositesw.services.system.admin.GetUsedResourcesSoapFault;
import com.compositesw.services.system.admin.LockResourceSoapFault;
import com.compositesw.services.system.admin.MoveResourceSoapFault;
import com.compositesw.services.system.admin.RenameResourceSoapFault;
import com.compositesw.services.system.admin.ResourceExistsSoapFault;
import com.compositesw.services.system.admin.ResourcePortType;
import com.compositesw.services.system.admin.UnlockResourceSoapFault;
import com.compositesw.services.system.admin.execute.RequestStatus;
import com.compositesw.services.system.admin.execute.TabularValue;
import com.compositesw.services.system.admin.execute.Value;
import com.compositesw.services.system.admin.execute.ValueList;
import com.compositesw.services.system.admin.resource.Column;
import com.compositesw.services.system.admin.resource.ColumnList;
import com.compositesw.services.system.admin.resource.CopyMode;
import com.compositesw.services.system.admin.resource.Resource;
import com.compositesw.services.system.admin.resource.ResourceList;
import com.compositesw.services.system.admin.resource.ResourceSubType;
import com.compositesw.services.system.admin.resource.ResourceType;
import com.compositesw.services.system.util.common.DetailLevel;
import com.compositesw.services.system.util.common.MessageEntry;

import cs.jdbc.driver.protocol.Logger;

public class ResourceWSDAOImpl implements ResourceDAO {

	private static Log logger = LogFactory.getLog(ResourceWSDAOImpl.class);

	/* (non-Javadoc)
	 * This method only supports all scalar values or one cursor output. 
	 * This method does not support procedure calls with no output.
	 * This method does not support procedure calls with mixed scalar and cursor output.
	 * 
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#executeProcedure(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, Boolean)
	 */
//	@Override
	@SuppressWarnings("unused")
	public void executeProcedure(String serverId, String procedureName, String dataServiceName, String pathToServersXML, String arguments, Boolean outputReturnVariables) throws CompositeException {

		if(logger.isDebugEnabled()) {
			logger.debug("ResourceWSDAOImpl.executeProcedure(serverId, procedureName, dataServiceName, pathToServersXML, arguments).  serverId="+serverId+"  procedureName="+procedureName+"  dataServiceName="+dataServiceName+"  pathToServersXML="+pathToServersXML+"  arguments="+arguments);
		}
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.executeProcedure", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the execute port based on target server name
		ExecutePortType port = CisApiFactory.getExecutePort(targetServer);		
		
		
		String procedureScript = "";

		if(arguments == null || arguments.trim().length() == 0 || arguments.trim().equals("\"\"")){
			procedureScript =  "select * from "+procedureName+"()" ;
		}else{
			procedureScript =  "select * from "+procedureName+"("+arguments+")";
		}
		
		try {
			if(logger.isInfoEnabled()){
				logger.info("ResourceWSDAOImpl.executeProcedure(). Calling executeSql with sql "+procedureScript);
			}
			if(logger.isDebugEnabled()) {
				logger.debug("ResourceWSDAOImpl.executeProcedure(). Invoking port.executeSql(\""+procedureScript+"\", true, false, 0, 1, false, null, null, \""+dataServiceName+"\", null, null, null, null, null, null, null).");
			}
			/*
			 * void com.compositesw.services.system.admin.ExecutePortType.executeSql(
			 * 		@WebParam(name="sqlText", targetNamespace="http://www.compositesw.com/services/system/admin/execute") String sqlText, 
			 * 		@WebParam(name="isBlocking", targetNamespace="http://www.compositesw.com/services/system/admin/execute") Boolean isBlocking, 
			 * 		@WebParam(name="includeMetadata", targetNamespace="http://www.compositesw.com/services/system/admin/execute") Boolean includeMetadata, 
			 * 		@WebParam(name="skipRows", targetNamespace="http://www.compositesw.com/services/system/admin/execute") Integer skipRows, 
			 * 		@WebParam(name="maxRows", targetNamespace="http://www.compositesw.com/services/system/admin/execute") Integer maxRows, 
			 * 		@WebParam(name="consumeRemainingRows", targetNamespace="http://www.compositesw.com/services/system/admin/execute") Boolean consumeRemainingRows, 
			 * 		@WebParam(name="users", targetNamespace="http://www.compositesw.com/services/system/admin/execute") DomainMemberReferenceList users, 
			 * 		@WebParam(name="groups", targetNamespace="http://www.compositesw.com/services/system/admin/execute") DomainMemberReferenceList groups, 
			 * 		@WebParam(name="dataServiceName", targetNamespace="http://www.compositesw.com/services/system/admin/execute") String dataServiceName, 
			 * 		@WebParam(name="completed", targetNamespace="http://www.compositesw.com/services/system/admin/execute", mode=Mode.OUT) Holder<Boolean> completed, 
			 * 		@WebParam(name="requestStatus", targetNamespace="http://www.compositesw.com/services/system/admin/execute", mode=Mode.OUT) Holder<RequestStatus> requestStatus, 
			 * 		@WebParam(name="metadata", targetNamespace="http://www.compositesw.com/services/system/admin/execute", mode=Mode.OUT) Holder<ColumnList> metadata, 
			 * 		@WebParam(name="rowsAffected", targetNamespace="http://www.compositesw.com/services/system/admin/execute", mode=Mode.OUT) Holder<Integer> rowsAffected, 
			 * 		@WebParam(name="result", targetNamespace="http://www.compositesw.com/services/system/admin/execute", mode=Mode.OUT) Holder<TabularValue> result, 
			 * 		@WebParam(name="resultId", targetNamespace="http://www.compositesw.com/services/system/admin/execute", mode=Mode.OUT) Holder<String> resultId, 
			 * 		@WebParam(name="requestId", targetNamespace="http://www.compositesw.com/services/system/admin/execute", mode=Mode.OUT) Holder<Long> requestId) 
			 * 	throws ExecuteSqlSoapFault

					@WebMethod(action="executeSql")
					@RequestWrapper(localName="executeSql", targetNamespace="http://www.compositesw.com/services/system/admin/execute", className="com.compositesw.services.system.admin.execute.ExecuteSqlRequest")
					@ResponseWrapper(localName="executeSqlResponse", targetNamespace="http://www.compositesw.com/services/system/admin/execute", className="com.compositesw.services.system.admin.execute.ExecuteSqlResponse")
			*/
			Holder<Boolean> completed = new Holder<Boolean>();
			Holder<RequestStatus> requestStatus = new Holder<RequestStatus>();
			Holder<ColumnList> metadata = new Holder<ColumnList>();
			Holder<Integer> rowsAffected = new Holder<Integer>();
			Holder<TabularValue> result = new Holder<TabularValue>();
			Holder<String> resultId = new Holder<String>();
			Holder<Long> requestId = new Holder<Long>();
			 

			port.executeSql(procedureScript, true, true, 0, null, true, null, null, dataServiceName, completed, requestStatus, metadata, rowsAffected, result, resultId, requestId);


			if (!outputReturnVariables) {
				logger.info("ResourceWSDAOImpl.executeProcedure(). Successfully executed: port.executeSql().");
			
			} else {
				String resultStatistics = "ResourceWSDAOImpl.executeProcedure(). Successfully executed: port.executeSql(). ";

				if (completed != null && logger.isDebugEnabled())
					resultStatistics = resultStatistics + "completed="+completed.value.toString()+",   ";
				if (requestStatus != null)
					resultStatistics = resultStatistics + "requestStatus="+requestStatus.value.toString()+",   ";
				if (rowsAffected != null)
					resultStatistics = resultStatistics + "rowsAffected="+rowsAffected.value.toString()+",   ";
				if (resultId != null && logger.isDebugEnabled())
					resultStatistics = resultStatistics + "resultId="+resultId.value.toString()+",   ";
				if (requestId != null && logger.isDebugEnabled())
					resultStatistics = resultStatistics + "requestId="+requestId.value.toString()+",   ";
				resultStatistics = resultStatistics.trim();
				resultStatistics = resultStatistics.substring(0, resultStatistics.length()-1);
				logger.info(resultStatistics);
				
				resultStatistics = "Procedure output for variables: [";
				// Extract metadata
				String[] columnList = new String[0];
				if (metadata != null) {
					List<Column> columns = metadata.value.getColumn();
					int size = columns.size();
					columnList = new String[size];
					int i = 0;
					for (Column col : columns) {
						columnList[i] = col.getName();
						if (i > 0)
							resultStatistics = resultStatistics + ", ";
						resultStatistics = resultStatistics + col.getName();
						i = i + 1;
					}
				}
				resultStatistics = resultStatistics + "]";
				logger.info(resultStatistics);
		
				// Extract values
				String[] values = new String[0];
				if (result != null) {
					TabularValue res = result.value;
					if (res != null) {
						List<ValueList> rows = res.getRows().getRow();
						
						if (rows != null) {
							// Determine the size of the value array
							int size = 0;
							for (ValueList row : rows) {
								for (Value val : row.getValue()) {
									size++;
								}
							}
							// Declare the size
							values = new String[size];					
							int i = 0;
							for (ValueList row : rows) {
								for (Value val : row.getValue()) {
									if (val != null)
										values[i++] = val.getValue();
									else
										values[i++] = null;
								}
							}
						}
					}
				}
				// Process the columns and values
				if (columnList != null) {
					int valuelen = 0;
					String columname = null;
					for (int i=0; i < columnList.length; i++) {
						String val = null;
						columname = columnList[i];
						if (i < values.length) {
							val = values[i];
							valuelen = i + 1;
						}
						
						logger.info(columname+'='+val);
					}
					if (valuelen < values.length) {
						for (int i=valuelen; i < values.length; i++) {
							String val = values[i];
							logger.info(columname+'='+val);
						}
					}
				}
			}

		} catch (ExecuteSqlSoapFault e) {
			/*	
			 * 		List<ResourceType> resourceList = getResources(serverId, procedureIds, pathToResourceXML, pathToServersXML);

		if (resourceList != null && resourceList.size() > 0) {
			
			for (ResourceType resource : resourceList) 

	
			if (e.getFaultInfo().getErrorEntry() != null) {
				for (MessageEntry me : e.getFaultInfo().getErrorEntry()) {
					logger.error("ExecuteSqlSoapFault ------------------- Composite Server SOAP Fault MessageEntry Begin -------------------");
					logger.error("ExecuteSqlSoapFault MessageEntry.name ["+me.getName()+"] code=["+me.getCode()+"]");
					logger.error("ExecuteSqlSoapFault MessageEntry.message:"+me.getMessage());
					logger.error("ExecuteSqlSoapFault MessageEntry.detail:"+me.getDetail());
					logger.error("ExecuteSqlSoapFault ------------------- Composite Server SOAP Fault MessageEntry End -------------------");
				}				
			}
*/
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "executeProcedure", "Resource", procedureName, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#deleteResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void deleteResource(String serverId, String resourcePath, String resourceType, String pathToServersXML) throws CompositeException {

		if(logger.isDebugEnabled()) {
			logger.debug("ResourceWSDAOImpl.deleteResource(serverId, resourcePath, resourceType, pathToServersXML, arguments).  serverId="+serverId+"  resourcePath="+resourcePath+"  resourceType="+resourceType+"  pathToServersXML="+pathToServersXML);
		}
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.deleteResource", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			// Make sure the resource exists before executing any actions
//			if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, resourceType, pathToServersXML)) {
			if (resourceExistsFromPort(port, resourcePath, resourceType)) {
				if(logger.isDebugEnabled()) {
					logger.debug("ResourceWSDAOImpl.deleteResource().  Invoking port.destroyResource(\""+resourcePath+"\", \""+resourceType+"\", true).");
				}
				
				port.destroyResource(resourcePath, ResourceType.valueOf(resourceType), true);

				if(logger.isDebugEnabled()) {
					logger.debug("ResourceWSDAOImpl.deleteResource().  Success: port.destroyResource().");
				}
			} else {
				throw new ApplicationException("The resource "+resourcePath+" does not exist.");
			}				
		} catch (DestroyResourceSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "deleteResource", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#renameResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void renameResource(String serverId, String resourcePath, String resourceType, String newName, String pathToServersXML) throws CompositeException {

		if(logger.isDebugEnabled()) {
			logger.debug("ResourceWSDAOImpl.renameResource(serverId, resourcePath, resourceType, newName, pathToServersXML, arguments).  serverId="+serverId+"  resourcePath="+resourcePath+"  resourceType="+resourceType+"  newName="+newName+"  pathToServersXML="+pathToServersXML);
		}
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.renameResource", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			// Make sure the resource exists before executing any actions
//			if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, resourceType, pathToServersXML)) {
			if (resourceExistsFromPort(port, resourcePath, resourceType)) {
				if(logger.isDebugEnabled()) {
					logger.debug("ResourceWSDAOImpl.renameResource().  Invoking port.renameResource(\""+resourcePath+"\", \""+resourceType+"\", \""+newName+"\").");
				}

				port.renameResource(resourcePath, ResourceType.valueOf(resourceType), newName);

				if(logger.isDebugEnabled()) {
					logger.debug("ResourceWSDAOImpl.renameResource().  Success: port.renameResource().");
				}
			} else {
				throw new ApplicationException("The resource "+resourcePath+" does not exist.");
			}				
		} catch (RenameResourceSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "renameResource", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#copyResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void copyResource(String serverId, String resourcePath, String resourceType, String targetContainerPath, String newName, String copyMode, String pathToServersXML) throws CompositeException {

		if(logger.isDebugEnabled()) {
			logger.debug("ResourceWSDAOImpl.copyResource(serverId, resourcePath, resourceType, targetContainerPath, newName, copyMode, pathToServersXML, arguments).  serverId="+serverId+"  resourcePath="+resourcePath+"  resourceType="+resourceType+"  targetContainerPath="+targetContainerPath+"  newName="+newName+"  copyMode="+copyMode+"  pathToServersXML="+pathToServersXML);
		}
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.copyResource", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			// Make sure the resource exists before executing any actions
//			if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, resourceType, pathToServersXML)) {
			if (resourceExistsFromPort(port, resourcePath, resourceType)) {
				if(logger.isDebugEnabled()) {
					logger.debug("ResourceWSDAOImpl.copyResource().  Invoking port.copyResource(\""+resourcePath+"\", \""+resourceType+"\", \""+targetContainerPath+"\", \""+newName+"\", \""+copyMode+"\").");
				}
				
				port.copyResource(resourcePath, ResourceType.valueOf(resourceType), targetContainerPath, newName, CopyMode.valueOf(copyMode));
				
				if(logger.isDebugEnabled()) {
					logger.debug("ResourceWSDAOImpl.copyResource().  Success: port.copyResource().");
				}
			} else {
				throw new ApplicationException("The resource "+resourcePath+" does not exist.");
			}				
		} catch (CopyResourceSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "copyResource", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#moveResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void moveResource(String serverId, String resourcePath, String resourceType, String targetContainerPath, String newName, String pathToServersXML) throws CompositeException {

		if(logger.isDebugEnabled()) {
			logger.debug("ResourceWSDAOImpl.moveResource(serverId, resourcePath, resourceType, targetContainerPath, newName, pathToServersXML, arguments).  serverId="+serverId+"  resourcePath="+resourcePath+"  resourceType="+resourceType+"  targetContainerPath="+targetContainerPath+"  newName="+newName+"  pathToServersXML="+pathToServersXML);
		}
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.moveResource", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			// Make sure the resource exists before executing any actions
//			if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, resourceType, pathToServersXML)) {
			if (resourceExistsFromPort(port, resourcePath, resourceType)) {
				if(logger.isDebugEnabled()) {
					logger.debug("ResourceWSDAOImpl.moveResource().  Invoking port.moveResource(\""+resourcePath+"\", \""+resourceType+"\", \""+targetContainerPath+"\", \""+newName+"\", true).");
				}
				
				port.moveResource(resourcePath, ResourceType.valueOf(resourceType), targetContainerPath, newName, true);
				
				if(logger.isDebugEnabled()) {
					logger.debug("ResourceWSDAOImpl.moveResource().  Success: port.moveResource().");
				}
			} else {
				throw new ApplicationException("The resource "+resourcePath+" does not exist.");
			}				
		} catch (MoveResourceSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "moveResource", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#unlockResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void lockResource(String serverId, String resourcePath, String resourceType, String pathToServersXML) throws CompositeException {

		if(logger.isDebugEnabled()) {
			logger.debug("ResourceWSDAOImpl.lockResource(serverId, resourcePath, resourceType, pathToServersXML, arguments).  serverId="+serverId+"  resourcePath="+resourcePath+"  resourceType="+resourceType+"  pathToServersXML="+pathToServersXML);
		}
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.lockResource", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			// Make sure the resource exists before executing any actions
//			if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, resourceType, pathToServersXML)) {
			if (resourceExistsFromPort(port, resourcePath, resourceType)) {
				if(logger.isDebugEnabled()) {
					logger.debug("ResourceWSDAOImpl.lockResource().  Invoking port.lockResource(\""+resourcePath+"\", \""+resourceType+"\", \"FULL\").");
				}
				
				port.lockResource(resourcePath, ResourceType.valueOf(resourceType), DetailLevel.FULL);
				
				if(logger.isDebugEnabled()) {
					logger.debug("ResourceWSDAOImpl.lockResource().  Success: port.lockResource().");
				}
			} else {
				throw new ApplicationException("The resource "+resourcePath+" does not exist.");
			}				
		} catch (LockResourceSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "lockResource", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#unlockResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void unlockResource(String serverId, String resourcePath, String resourceType, String pathToServersXML, String comment) throws CompositeException {

		if(logger.isDebugEnabled()) {
			logger.debug("ResourceWSDAOImpl.unlockResource(serverId, resourcePath, resourceType, pathToServersXML, arguments).  serverId="+serverId+"  resourcePath="+resourcePath+"  resourceType="+resourceType+"  pathToServersXML="+pathToServersXML+"  comment="+comment);
		}
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.unlockResource", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			// Make sure the resource exists before executing any actions
//			if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, resourceType, pathToServersXML)) {
			if (resourceExistsFromPort(port, resourcePath, resourceType)) {
				if(logger.isDebugEnabled()) {
					logger.debug("ResourceWSDAOImpl.unlockResource().  Invoking port.unlockResource(\""+resourcePath+"\", \""+resourceType+"\", \"FULL\", \""+comment+"\").");
				}
				
				port.unlockResource(resourcePath, ResourceType.valueOf(resourceType), DetailLevel.FULL, comment);
				
				if(logger.isDebugEnabled()) {
					logger.debug("ResourceWSDAOImpl.unlockResource().  Success: port.unlockResource().");
				}
			} else {
				throw new ApplicationException("The resource "+resourcePath+" does not exist.");
			}		
		} catch (UnlockResourceSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "unlockResource", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#getResource(java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public Resource getResource(String serverId, String resourcePath, String pathToServersXML) throws CompositeException {

		if(logger.isDebugEnabled()) {
			logger.debug("ResourceWSDAOImpl.getResource(serverId, resourcePath, pathToServersXML).  serverId="+serverId+"  resourcePath="+resourcePath+"  pathToServersXML="+pathToServersXML);
		}
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			if(logger.isDebugEnabled()) {
				logger.debug("ResourceWSDAOImpl.getResource().  Invoking port.getAllResourcesByPath(\""+resourcePath+"\", \"FULL\").");
			}
			
			ResourceList resourceList = port.getAllResourcesByPath(resourcePath, DetailLevel.FULL);
			
			if(logger.isDebugEnabled()) {
				logger.debug("ResourceWSDAOImpl.getResource().  Success: port.getAllResourcesByPath().");
			}

			if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty()){
				
				List<Resource> resources = resourceList.getResource();
				
				for (Resource resource : resources) {
					if(resource.getPath().equalsIgnoreCase(resourcePath)){
						return resource;
					}
				}
			}

		} catch (GetAllResourcesByPathSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getResource", "Resource", resourcePath, targetServer) +
							 "\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#getResourceCompositeServer(CompositeServer, java.lang.String)
	 */
//	@Override
	public Resource getResourceCompositeServer(CompositeServer targetServer, String resourcePath) throws CompositeException {

		if(logger.isDebugEnabled()) {
			logger.debug("ResourceWSDAOImpl.getResourceCompositeServer(targetServer, resourcePath).  serverId="+targetServer.getId()+"  hostname="+targetServer.getHostname()+"  resourcePath="+resourcePath);
		}
		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			if(logger.isDebugEnabled()) {
				logger.debug("ResourceWSDAOImpl.getResourceCompositeServer().  Invoking port.getAllResourcesByPath(\""+resourcePath+"\", \"FULL\").");
			}
			
			ResourceList resourceList = port.getAllResourcesByPath(resourcePath, DetailLevel.FULL);
			
			if(logger.isDebugEnabled()) {
				logger.debug("ResourceWSDAOImpl.getResourceCompositeServer().  Success: port.getAllResourcesByPath().");
			}

			if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty()){
				
				List<Resource> resources = resourceList.getResource();
				
				for (Resource resource : resources) {
					if(resource.getPath().equalsIgnoreCase(resourcePath)){
						return resource;
					}
				}
			}

		} catch (GetAllResourcesByPathSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getResource", "Resource", resourcePath, targetServer) +
							 "\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see boolean#resourceExistsFromPort(ResourcePortType, java.lang.String, java.lang.String)
	 * 
	 * If resourceType is passed in then check to see if a resource exists with that type otherwise only use the resourcePath to determine existence.
	 * Utilize the port with an existing connection to determine the resource existence.  This is more efficient than creating a new connection.
	 * 
	 * @return Return true/false if the resource exists.
	 */
//	@Override
	public boolean resourceExistsFromPort(ResourcePortType port, String resourcePath, String resourceType) {
		if(logger.isDebugEnabled()) {
			logger.debug("ResourceWSDAOImpl.resourceExistsFromPort(port, resourcePath, resourceType).  resourcePath="+resourcePath+"  resourceType="+resourceType);
		}

		Resource resource = getResourceFromPort(port, resourcePath, resourceType);
		if (resource != null) {
			if(logger.isInfoEnabled()){
				logger.info("Resource exists? [true] "+resourcePath+"  type="+resourceType);
			}
			return true;
		} else {
			if(logger.isInfoEnabled()){
				logger.info("Resource exists? [false] "+resourcePath+"  type="+resourceType);
			}
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#resourceExists(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public boolean resourceExists(String serverId, String resourcePath, String resourceType, String pathToServersXML) {

		if(logger.isDebugEnabled()) {
			logger.debug("ResourceWSDAOImpl.resourceExists(serverId, resourcePath, resourceType, pathToServersXML).  serverId="+serverId+"  resourcePath="+resourcePath+"  resourceType="+resourceType+"  pathToServersXML="+pathToServersXML);
		}
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		boolean exists = false;
		try {			
			if(logger.isDebugEnabled()) {
				logger.debug("ResourceWSDAOImpl.resourceExists().  Invoking port.resourceExists(\""+resourcePath+"\", \""+resourceType+"\", null).");
			}
			
			exists = port.resourceExists(resourcePath, ResourceType.valueOf(resourceType), null);
			
			if(logger.isDebugEnabled()) {
				logger.debug("ResourceWSDAOImpl.resourceExists().  Success: port.resourceExists().");
			}
		
		} catch (ResourceExistsSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "resourceExists", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
		return exists;
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#getResourcesFromPath(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public ResourceList getResourcesFromPath(String serverId, String resourcePath, String resourceType, String resourceTypeFilter, String detailLevel, String pathToServersXML) throws CompositeException{

		if(logger.isDebugEnabled()) {
			logger.debug("ResourceWSDAOImpl.getResourcesFromPath(serverId, resourcePath, resourceType, resourceTypeFilter, detailLevel, pathToServersXML).  serverId="+serverId+"  resourcePath="+resourcePath+"  resourceType="+resourceType+"  resourceTypeFilter="+resourceTypeFilter+"  detailLevel="+detailLevel+"  pathToServersXML="+pathToServersXML);
		}
		String validateResourceType = resourceType;
		if (resourceType == null) {
			validateResourceType = ResourceType.CONTAINER.name();
		}
		// Make sure the resource exists before executing any actions
		if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, validateResourceType, pathToServersXML)) {
			return getAllResourcesFromPath(serverId, resourcePath, resourceType, resourceTypeFilter, detailLevel, pathToServersXML, true);
		} else {
			throw new ApplicationException("The resource "+resourcePath+" does not exist.");
		}	
	}
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#getImmediateResourcesFromPath(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public ResourceList getImmediateResourcesFromPath(String serverId, String resourcePath, String resourceType, String detailLevel, String pathToServersXML) throws CompositeException {

		if(logger.isDebugEnabled()) {
			logger.debug("ResourceWSDAOImpl.getImmediateResourcesFromPath(serverId, resourcePath, resourceType, detailLevel, pathToServersXML).  serverId="+serverId+"  resourcePath="+resourcePath+"  resourceType="+resourceType+"  detailLevel="+detailLevel+"  pathToServersXML="+pathToServersXML);
		}
		String validateResourceType = resourceType;
		if (resourceType == null) {
			validateResourceType = ResourceType.CONTAINER.name();
		}
		// Make sure the resource exists before executing any actions
		if (DeployManagerUtil.getDeployManager().resourceExists(serverId, resourcePath, validateResourceType, pathToServersXML)) {
			return getAllResourcesFromPath(serverId, resourcePath, resourceType, null, detailLevel, pathToServersXML, false);
		} else {
			throw new ApplicationException("The resource "+resourcePath+" does not exist.");
		}	
	}
	
	private ResourceList getAllResourcesFromPath(String serverId, String resourcePath, String resourceType, String resourceTypeFilter, String detailLevel, String pathToServersXML, boolean recurse) {

		if(logger.isDebugEnabled()) {
			logger.debug("private ResourceWSDAOImpl.getAllResourcesFromPath(serverId, resourcePath, resourceType, resourceTypeFilter, detailLevel, pathToServersXML, recurse).  serverId="+serverId+"  resourcePath="+resourcePath+"  resourceType="+resourceType+"  resourceTypeFilter="+resourceTypeFilter+"  detailLevel="+detailLevel+"  pathToServersXML="+pathToServersXML+"  recurse="+recurse);
		}
		ResourceList returnResourceList = new ResourceList();

		// Add the resource to the list if no resourceTypeFilter specified or if the resource matches the passed in resourceTypeFilter
		Resource startingResource = getResource(serverId, resourcePath, pathToServersXML);
		if(resourceTypeFilter == null || (startingResource.getType() != null && resourceTypeFilter.contains(startingResource.getType().name()))){	
			returnResourceList.getResource().add(startingResource);
		}
		
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.getAllResourcesFromPath", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);
		
		addResourcesFromPath(port, returnResourceList, resourcePath, resourceType, resourceTypeFilter, detailLevel, targetServer, recurse);
		
		return returnResourceList;
	}

	private void addResourcesFromPath(ResourcePortType port, ResourceList resourceList, String resourcePath, String resourceType, String resourceTypeFilter, String detailLevel,CompositeServer targetServer, boolean recurse){
		try {
			if (resourceType == null) {
				resourceType = ResourceType.CONTAINER.name();
			}
			if(logger.isDebugEnabled()) {
				logger.debug("ResourceWSDAOImpl.addResourcesFromPath().  Invoking port.getChildResources(\""+resourcePath+"\", \""+resourceType+"\", \""+detailLevel+"\").");
			}

			ResourceList childResourceList = port.getChildResources(resourcePath, ResourceType.fromValue(resourceType), DetailLevel.fromValue(detailLevel));

			if(logger.isDebugEnabled()) {
				logger.debug("ResourceWSDAOImpl.addResourcesFromPath().  Success: port.getChildResources().");
			}

			if(childResourceList!= null && childResourceList.getResource() != null && !childResourceList.getResource().isEmpty()){

				List<Resource> resources = childResourceList.getResource();

				for (Resource resource : resources) {

					// Add the resource to the list if no resourceTypeFilter specified or if the resource matches the passed in resourceTypeFilter
					if(resourceTypeFilter == null || (resource.getType() != null && resourceTypeFilter.contains(resource.getType().name()))){	
						resourceList.getResource().add(resource);
					}

					// Recurse the tree structure if recurse is true and the resource sub-type is a FOLDER_CONTAINER or a data source with a suby-type of RELATIONAL_DATA_SOURCE
					//   Data Sources contain folders and tables
					//   A relational data source contains one or more folders called a schema_container which may contain TABLES
					if(recurse && resource.getSubtype() != null) {
						if ( resource.getSubtype().equals(ResourceSubType.FOLDER_CONTAINER) || resource.getSubtype().equals(ResourceSubType.RELATIONAL_DATA_SOURCE) || resource.getSubtype().equals(ResourceSubType.SCHEMA_CONTAINER) ) 
						{
							addResourcesFromPath(port, resourceList,resource.getPath(), resource.getType().name(), resourceTypeFilter, detailLevel, targetServer, recurse);
						}
					} 
				}
			}
		} catch (GetChildResourcesSoapFault e) {
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "addResourcesFromPath", "Resource", resourcePath, targetServer) +
				"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(e.getMessage(), e);
		} catch (Exception e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "addResourcesFromPath", "Resource", resourcePath, targetServer));
			throw new ApplicationException(e.getMessage(), e);
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#getUsedResources(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public ResourceList getUsedResources(String serverId, String resourcePath, String resourceType, String detailLevel, String pathToServersXML) throws CompositeException {

		if(logger.isDebugEnabled()) {
			logger.debug("ResourceWSDAOImpl.getUsedResources(serverId, resourcePath, resourceType, detailLevel, pathToServersXML).  serverId="+serverId+"  resourcePath="+resourcePath+"  resourceType="+resourceType+"  detailLevel="+detailLevel+"  pathToServersXML="+pathToServersXML);
		}
		ResourceList returnResourceList = new ResourceList();

		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);
		
		try 
		{
			if(logger.isDebugEnabled()) {
				logger.debug("ResourceWSDAOImpl.getUsedResources().  Invoking port.getUsedResources(\""+resourcePath+"\", \""+resourceType+"\", \""+detailLevel+"\").");
			}
			
			returnResourceList = port.getUsedResources(resourcePath, ResourceType.valueOf(resourceType), DetailLevel.valueOf(detailLevel));

			if(logger.isDebugEnabled()) {
				logger.debug("ResourceWSDAOImpl.getUsedResources().  Success: port.getUsedResources().");
			}
		} 
		catch (GetUsedResourcesSoapFault e) 
		{
			String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getUsedResources", "Resource", resourcePath, targetServer) +
			 	"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
			CompositeLogger.logException(e, message);
			throw new ApplicationException(message, e);
		}
		return returnResourceList;
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#createFolder(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void createFolder(String serverId, String resourcePath, String pathToServersXML, String recursive, String ignoreErrors) throws CompositeException {
//		 * @param recursive false=only create the folder specified, true=create all folders recursively

		if(logger.isDebugEnabled()) {
			logger.debug("ResourceWSDAOImpl.createFolder(serverId, resourcePath, pathToServersXML, recursive, ignoreErrors).  serverId="+serverId+"  resourcePath="+resourcePath+"  pathToServersXML="+pathToServersXML+"  recursive="+recursive+"  ignoreErrors="+ignoreErrors);
		}
		// Init variables
		Resource resource = null;
		int lastIndex = 0;
		String parentPath = null;
		String name = null;
		boolean ignoreErrorsBool = false;
		if (ignoreErrors.equalsIgnoreCase("true"))
			ignoreErrorsBool = true;
		
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceWSDAOImpl.createFolder", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			// Make sure the resource does not exist before executing the create resource
			if (!resourcePath.equalsIgnoreCase("/") && 
					!resourcePath.toLowerCase().equalsIgnoreCase("/shared") && 
					!resourcePath.toLowerCase().equalsIgnoreCase("/services") && 
					!resourcePath.toLowerCase().equalsIgnoreCase("/services/databases") && 
					!resourcePath.toLowerCase().equalsIgnoreCase("/services/webservices") &&
					!resourcePath.toLowerCase().equalsIgnoreCase("/users") &&
					!resourcePath.toLowerCase().equalsIgnoreCase("/users/composite") &&
					!resourcePath.toLowerCase().equalsIgnoreCase("/users/composite/admin") &&
					!resourcePath.toLowerCase().startsWith("/lib") &&
					!resourcePath.toLowerCase().startsWith("/policy") &&
					!resourcePath.toLowerCase().startsWith("/security")
				) 
			{
				// Get the resource object if it exists otherwise resource is null
				resource = getResourceFromPort(port, resourcePath, null);
				// If resource does not exist then continue
				if (resource == null) {
					if(logger.isInfoEnabled()){
						logger.info("Resource exists? [false] "+resourcePath+" on server "+serverId);
					}

					if (recursive.equalsIgnoreCase("true") || recursive.equalsIgnoreCase("1")) {
						String[] pathparts = resourcePath.split("/");
						resourcePath = "";
						
						// Loop through the parts
				        for (int i=0; i<pathparts.length; i++)
				        {
				        	if (pathparts[i].trim().length() > 0) {
								resourcePath = resourcePath + "/" + pathparts[i];
								
								if (!resourcePath.equalsIgnoreCase("/") && 
										!resourcePath.toLowerCase().equalsIgnoreCase("/shared") && 
										!resourcePath.toLowerCase().equalsIgnoreCase("/services") && 
										!resourcePath.toLowerCase().equalsIgnoreCase("/services/databases") && 
										!resourcePath.toLowerCase().equalsIgnoreCase("/services/webservices") &&
										!resourcePath.toLowerCase().equalsIgnoreCase("/users") &&
										!resourcePath.toLowerCase().equalsIgnoreCase("/users/composite") &&
										!resourcePath.toLowerCase().equalsIgnoreCase("/users/composite/admin")
									) 
								{
									// Get the resource object if it exists otherwise resource is null
									resource = getResourceFromPort(port, resourcePath, null);

									// If resource does not exist then continue
									if (resource == null ) {
										if(logger.isInfoEnabled()){
											logger.info("Resource exists? [false] "+resourcePath+" on server "+serverId);
										}
	
										// Extract the path and the name from the full resource path
										lastIndex = resourcePath.lastIndexOf("/");
										parentPath = resourcePath.substring(0, lastIndex);
										name = resourcePath.substring(lastIndex+1);
										if(logger.isDebugEnabled()) {
											logger.debug("ResourceWSDAOImpl.createFolder().  Invoking port.createResource(\""+parentPath+"\", \""+name+"\", \"FULL\", \"CONTAINER\", \"FOLDER_CONTAINER\", null, null, null, false).");
										}
										
										// Get the resource object if it exists otherwise resource is null
										resource = getResourceFromPort(port, parentPath, null);
										
										// If parent resource exists then continue
										if (resource != null) 
										{
											String type = resource.getType().toString();
											String subtype = resource.getSubtype().toString();
											if (type.equalsIgnoreCase("CONTAINER") && (subtype.equalsIgnoreCase("FOLDER") || subtype.equalsIgnoreCase("FOLDER_CONTAINER")) ) {
												// Create the resource path
												port.createResource(parentPath, name, DetailLevel.valueOf("FULL"), ResourceType.valueOf("CONTAINER"), ResourceSubType.valueOf("FOLDER_CONTAINER"), null, null, null, false);
												
												if(logger.isInfoEnabled()){
													logger.info("Resource Folder Created "+resourcePath+" on server "+serverId);
												}
											} else {
												throw new CompositeException("Cannot create a folder when the parent is not a folder for parentPath="+parentPath+" type="+type+" subtype="+subtype);
											}
										} else {
											throw new CompositeException("Cannot create a folder when the parent folder does not exist for resourcePath="+parentPath);
										}
									} else {
										if(logger.isInfoEnabled()){
											logger.info("Resource exists? [true] "+resourcePath+" on server "+serverId);
										}										
									}
								}
				        	}
				        }
	
					} else {
						// Extract the path and the name from the full resource path
						lastIndex = resourcePath.lastIndexOf("/");
						parentPath = resourcePath.substring(0, lastIndex);
						name = resourcePath.substring(lastIndex+1);
						if(logger.isDebugEnabled()) {
							logger.debug("ResourceWSDAOImpl.createFolder().  Invoking port.createResource(\""+parentPath+"\", \""+name+"\", \"FULL\", \"CONTAINER\", \"FOLDER_CONTAINER\", null, null, null, false).");
						}
						
						// Get the resource object if it exists otherwise resource is null
						resource = getResourceFromPort(port, parentPath, null);
						
						// If parent resource exists then continue
						if (resource != null) 
						{
							String type = resource.getType().toString();
							String subtype = resource.getSubtype().toString();
							if (type.equalsIgnoreCase("CONTAINER") && (subtype.equalsIgnoreCase("FOLDER") || subtype.equalsIgnoreCase("FOLDER_CONTAINER")) ) {
								// Create the resource path
								port.createResource(parentPath, name, DetailLevel.valueOf("FULL"), ResourceType.valueOf("CONTAINER"), ResourceSubType.valueOf("FOLDER_CONTAINER"), null, null, null, false);
								
								if(logger.isInfoEnabled()){
									logger.info("Resource Folder Created "+resourcePath+" on server "+serverId);
								}
							} else {
								throw new CompositeException("Cannot create a folder when the parent is not a folder for parentPath="+parentPath+" type="+type+" subtype="+subtype);
							}
						} else {
							throw new CompositeException("Cannot create a folder when the parent folder does not exist for resourcePath="+parentPath);
						}
					}
				} else {
					if(logger.isInfoEnabled()){
						logger.info("Resource exists? [true] "+resourcePath+" on server "+serverId);
					}
				}
			} else {
				if(logger.isInfoEnabled()){
					logger.info("Cannot create a system folder for resourcePath="+resourcePath+" on server "+serverId);
				}
			}
		} catch (CreateResourceSoapFault e) {
			if (ignoreErrorsBool) {
				String message = DeployUtil.constructMessage(DeployUtil.MessageType.INFO.name(), "WARNING IGNORE ERRORS: createFolder::createResource", "Resource", resourcePath, targetServer) +
						"\nWarningMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
					CompositeLogger.logInfoMessage(message);
			} else {
				String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "createFolder::createResource", "Resource", resourcePath, targetServer) +
						"\nErrorMessage: "+ CompositeLogger.getFaultMessage(e, e.getFaultInfo());
					CompositeLogger.logException(e, message);
					throw new ApplicationException(message, e);
			}
		} catch (CompositeException e) {
			if (ignoreErrorsBool) {
				String message = DeployUtil.constructMessage(DeployUtil.MessageType.INFO.name(), "WARNING IGNORE ERRORS: createFolder::createResource", "Resource", resourcePath, targetServer) +
						"\nWarningMessage: "+ e.getMessage();
					CompositeLogger.logInfoMessage(message);
			} else {
				String message = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "createFolder::createResource", "Resource", resourcePath, targetServer) +
						"\nErrorMessage: "+ e.getMessage();
					CompositeLogger.logException(e, message);
					throw new ApplicationException(message, e);
			}
		}	
	}
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceDAO#getResourceFromPort(ResourcePortType, java.lang.String, java.lang.String)
	 */
//	@Override
	// Return the Resource object if the resource exists otherwise return null even if there is an exception.
	//  If resourceType is passed in then check to see if a resource exists with that type otherwise only use the resourcePath to determine existence.
	private Resource getResourceFromPort(ResourcePortType port, String resourcePath, String resourceType) {

		Resource resource = null;
		try {		
			ResourceList resourceList = port.getAllResourcesByPath(resourcePath, DetailLevel.FULL);
			
			if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty()){
				
				List<Resource> resources = resourceList.getResource();

				boolean continueLoop = true;
				for (int i=0; i < resources.size() && continueLoop; i++) {
					Resource res = resources.get(i);
					if (resourceType != null && resourceType.trim().length() > 0) {
						if (res.getPath().equalsIgnoreCase(resourcePath) && res.getType().toString().equalsIgnoreCase(resourceType)) {
							resource = res;
							continueLoop = false;
						}
					} else {
						if (res.getPath().equalsIgnoreCase(resourcePath)) {
							resource = res;
							continueLoop = false;
						}						
					}
				}
			}
		} catch (GetAllResourcesByPathSoapFault e) {
			String message = null;
			String name = null;
			// Determine if this is a real error or the acceptable resource does not exist error.
			boolean isResExistsError = false;
			if (e.getFaultInfo() != null) { 
				List<MessageEntry> messageEntries = e.getFaultInfo().getErrorEntry();
				for (MessageEntry messageEntry : messageEntries) {
					// e.g. NotFound
					name = messageEntry.getName();
					// e.g. The resource "/shared/test00/DataSourcesCopy/ds_orders/customers2" does not exist.
					message = messageEntry.getMessage();
					if  (message != null && message.toLowerCase().contains("does not exist") ||
						(name != null && name.toLowerCase().contains("notfound"))
						) {
						isResExistsError = true;
					}
				}
			}
			// If this is a resource does not exist error then simply return null otherwise throw an exception
			if (isResExistsError)
				return null;
			else
				throw new ApplicationException(e);
			
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		return resource;
	}
	
}
