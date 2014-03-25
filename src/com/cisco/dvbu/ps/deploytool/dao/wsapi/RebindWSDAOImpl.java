/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao.wsapi;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CompositeLogger;
import com.cisco.dvbu.ps.common.util.wsapi.CisApiFactory;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.dao.RebindDAO;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.compositesw.services.system.admin.RebindResourcesSoapFault;
import com.compositesw.services.system.admin.ResourcePortType;
import com.compositesw.services.system.admin.UpdateBasicTransformProcedureSoapFault;
import com.compositesw.services.system.admin.UpdateExternalSqlProcedureSoapFault;
import com.compositesw.services.system.admin.UpdateSqlScriptProcedureSoapFault;
import com.compositesw.services.system.admin.UpdateSqlTableSoapFault;
import com.compositesw.services.system.admin.UpdateStreamTransformProcedureSoapFault;
import com.compositesw.services.system.admin.UpdateXsltTransformProcedureSoapFault;
import com.compositesw.services.system.admin.resource.ColumnList;
import com.compositesw.services.system.admin.resource.Model;
import com.compositesw.services.system.admin.resource.MultiPathTypeRequest.Entries;
import com.compositesw.services.system.admin.resource.ParameterList;
import com.compositesw.services.system.admin.resource.PathTypePair;
import com.compositesw.services.system.admin.resource.RebindRule;
import com.compositesw.services.system.admin.resource.RebindRuleList;
import com.compositesw.services.system.admin.resource.ResourceType;
import com.compositesw.services.system.util.common.DetailLevel;
import com.compositesw.services.system.util.common.AttributeList;

public class RebindWSDAOImpl implements RebindDAO {

	private static Log logger = LogFactory.getLog(RebindWSDAOImpl.class);


//	@Override
	public void rebindResource(String serverId, PathTypePair source, RebindRuleList rebinds, String pathToServersXML) throws CompositeException {

		if(logger.isDebugEnabled()){
			String rebindText = "";
			if (rebinds != null) {
				List<RebindRule> rebindList = rebinds.getRebindRule();
				for (RebindRule rebindRule : rebindList) {
					rebindText = rebindText + rebindRule.getOldPath();
					rebindText = rebindText + "\n                       " + rebindRule.getOldType();
					rebindText = rebindText + "\n                       " + rebindRule.getNewPath();
					rebindText = rebindText + "\n                       " + rebindRule.getNewType();
				}
			}
			logger.debug("Entering RebindWSDAOImpl.rebindResource() with following params: " +
					"\n               source: " + source.getPath() + 
					"\n                 type: " + source.getType().name() + 
					"\n              rebinds: " + rebindText + 
					"\n             serverId: " + serverId + 
					"\n     pathToServersXML: " + pathToServersXML + 
					"\n");
		}
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "RebindWSDAOImpl.rebindResource", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {

			// Setup the list of path/type pairs to hold the single source
			Entries entries = new Entries();
			entries.getEntry().add(source);
			
			// Invoke the rebindResources admin API
			logger.info("rebindResources("+source.getPath()+")");
			port.rebindResources(entries, rebinds);
			
		} catch (RebindResourcesSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "rebindResource", "Rebind", source.getType().name(), targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		} 
	}	

	/**
	 * 	The following resource types and sub-types are supported:
	 *	resourceType = 'TABLE'
	 *		subtype = 'SQL_TABLE' -- Get Regular View
	 *			port.updateSqlTable(resourcePath, detailLevel, procedureText, model, isExplicitDesign, columns, annotation, attributes);
	 *
	 *	resourceType = 'PROCEDURE'
	 *		subtype = 'SQL_SCRIPT_PROCEDURE' -- Update Regular Procedure
	 *			port.updateSqlScriptProcedure(resourcePath, detailLevel, procedureText, model, isExplicitDesign, parameters, annotation, attributes);
	 *		subtype = 'EXTERNAL_SQL_PROCEDURE' -- Update Packaged Query Procedure
	 *			port.updateExternalSqlProcedure(resourcePath, detailLevel, procedureText, usedResourcePath, parameters, annotation, attributes);
	 *		subtype = 'BASIC_TRANSFORM_PROCEDURE' -- Update XSLT Basic Transformation definition
	 *			port.updateBasicTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, annotation, attributes);
	 *		subtype = 'XSLT_TRANSFORM_PROCEDURE' -- Update XSLT Transformation text
	 *			port.updateXsltTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, procedureText, model, annotation, isExplicitDesign, parameters, attributes);
	 *		subtype = 'STREAM_TRANSFORM_PROCEDURE' -- Update XSLT Stream Transformation text
	 *			port.updateStreamTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, model, isExplicitDesign, parameters, annotation, attributes);
	 */
//	@Override
	public void takeRebindFolderAction(String serverId, String pathToServersXML, String actionName, String resourcePath, DetailLevel detailLevel, String procedureText, String usedResourcePath, String usedResourceType, 
			Boolean isExplicitDesign, Model model, ColumnList columns, ParameterList parameters, String annotation, AttributeList attributes) throws CompositeException {

		if(logger.isDebugEnabled()){
			logger.debug("Entering RebindWSDAOImpl.takeRebindFolderAction() with following params: " +
					"\n           actionName: " + actionName + 
					"\n         resourcePath: " + resourcePath + 
					"\n             serverId: " + serverId + 
					"\n     pathToServersXML: " + pathToServersXML + 
					"\n");
		}
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "RebindWSDAOImpl.takeRebindFolderAction("+actionName+")", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);
		ResourceType resourceType = null;
		if (usedResourceType != null) {
			resourceType = ResourceType.valueOf(usedResourceType);
		}
		
		// Invoke the specific API for the action
		try {

			if(actionName.equalsIgnoreCase(RebindDAO.action.SQL_TABLE.name())){

				logger.info("updateSqlTable("+resourcePath+")");
				port.updateSqlTable(resourcePath, detailLevel, procedureText, model, isExplicitDesign, columns, annotation, attributes);
			
			}else if(actionName.equalsIgnoreCase(RebindDAO.action.SQL_SCRIPT_PROCEDURE.name())){
		
				logger.info("updateSqlScriptProcedure("+resourcePath+")");
				port.updateSqlScriptProcedure(resourcePath, detailLevel, procedureText, model, isExplicitDesign, parameters, annotation, attributes);
				
			}else if(actionName.equalsIgnoreCase(RebindDAO.action.EXTERNAL_SQL_PROCEDURE.name())){
				
				logger.info("updateExternalSqlProcedure("+resourcePath+")");
				port.updateExternalSqlProcedure(resourcePath, detailLevel, procedureText, usedResourcePath, parameters, annotation, attributes);

			}else if(actionName.equalsIgnoreCase(RebindDAO.action.BASIC_TRANSFORM_PROCEDURE.name())){
				
				logger.info("updateBasicTransformProcedure("+resourcePath+")");
				port.updateBasicTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, annotation, attributes);
				
			}else if(actionName.equalsIgnoreCase(RebindDAO.action.XSLT_TRANSFORM_PROCEDURE.name())){
				
				logger.info("updateXsltTransformProcedure("+resourcePath+")");
				port.updateXsltTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, procedureText, model, annotation, isExplicitDesign, parameters, attributes);
			
			}else if(actionName.equalsIgnoreCase(RebindDAO.action.STREAM_TRANSFORM_PROCEDURE.name())){
				
				logger.info("updateStreamTransformProcedure("+resourcePath+")");
				port.updateStreamTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, model, isExplicitDesign, parameters, annotation, attributes);
			
			} else {
				throw new ApplicationException("The action "+actionName+" does not exist.");
			}
			
		} catch (UpdateSqlTableSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "takeRebindFolderAction", "Rebind", actionName, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		} catch (UpdateSqlScriptProcedureSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "takeRebindFolderAction", "Rebind", actionName, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		} catch (UpdateExternalSqlProcedureSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "takeRebindFolderAction", "Rebind", actionName, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		} catch (UpdateBasicTransformProcedureSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "takeRebindFolderAction", "Rebind", actionName, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		} catch (UpdateXsltTransformProcedureSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "takeRebindFolderAction", "Rebind", actionName, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		} catch (UpdateStreamTransformProcedureSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "takeRebindFolderAction", "Rebind", actionName, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		} 
	}
	
}
