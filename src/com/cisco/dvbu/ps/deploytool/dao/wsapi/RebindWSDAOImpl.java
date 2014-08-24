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
import com.compositesw.services.system.admin.resource.Column;
import com.compositesw.services.system.admin.resource.ColumnList;
import com.compositesw.services.system.admin.resource.Model;
import com.compositesw.services.system.admin.resource.MultiPathTypeRequest.Entries;
import com.compositesw.services.system.admin.resource.Parameter;
import com.compositesw.services.system.admin.resource.ParameterList;
import com.compositesw.services.system.admin.resource.PathTypePair;
import com.compositesw.services.system.admin.resource.RebindRule;
import com.compositesw.services.system.admin.resource.RebindRuleList;
import com.compositesw.services.system.admin.resource.ResourceType;
import com.compositesw.services.system.util.common.Attribute;
import com.compositesw.services.system.util.common.DetailLevel;
import com.compositesw.services.system.util.common.AttributeList;

public class RebindWSDAOImpl implements RebindDAO {

	private static Log logger = LogFactory.getLog(RebindWSDAOImpl.class);


//	@Override
	public void rebindResource(String serverId, PathTypePair source, RebindRuleList rebinds, String pathToServersXML) throws CompositeException {

		// For debugging
		if(logger.isDebugEnabled()) {
			logger.debug("RebindWSDAOImpl.rebindResource(serverId, source[path,type], rebinds, pathToServersXML).  serverId=\"" + serverId+"\"  pathToServersXML=\""+pathToServersXML+"\"");
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

			if(logger.isDebugEnabled()) 
			{
				String sourceText = "\n            Entries:[";
				String entryText = "";
				if (entries != null && entries.getEntry() != null) {
					for (PathTypePair pathType : entries.getEntry()) {
						if (entryText.length() != 0)
							entryText = entryText + ",\n                     ";
						entryText = entryText + "SRC:\"" + pathType.getPath() + "\",  \"" + pathType.getType() + "\"";
					}
				}
				sourceText = sourceText + entryText + "]";
				
				String rebindText = "\n            Rebinds:[";
				String rebindRuleText = "";
				if (rebinds != null && rebinds.getRebindRule() != null) {
					for (RebindRule rebindRule : rebinds.getRebindRule()) {
						if (rebindRuleText.length() != 0)
							rebindRuleText = rebindRuleText + ",\n                     ";
						rebindRuleText = rebindRuleText +   "OLD:\"" + rebindRule.getOldPath() + "\",  \"" + rebindRule.getOldType() + "\",";
						rebindRuleText = rebindRuleText + "\n                     NEW:\"" + rebindRule.getNewPath() + "\",  \"" + rebindRule.getNewType() + "\"";
					}
				}
				rebindText = rebindText + rebindRuleText + "]";
				logger.debug("RebindWSDAOImpl.rebindResource().  Invoking port.rebindResources("+sourceText+", "+rebindText+").");
			}

			port.rebindResources(entries, rebinds);
			
			if(logger.isDebugEnabled()) {
				logger.debug("RebindWSDAOImpl.rebindResource().  Success: port.rebindResources().");
			}
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
			logger.debug("RebindWSDAOImpl.takeRebindFolderAction(serverId, pathToServersXML, actionName, resourcePath, detailLevel, procedureText, usedResourcePath, usedResourceType, isExplicitDesign, model, columns, parameters, annotation, attributes)."+
					"  serverId=\"" + serverId + "\"  pathToServersXML=\"" + pathToServersXML + "\"");
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
			String annotationStr = (annotation == null) ? null : "\""+annotation+"\"";
			String modelType = null;
			if (model != null && model.getType() != null)
				modelType = model.getType().toString();
			int attrSize = 0;
			String attrList = "";
			int colSize = 0;
			String colList = "";
			int paramSize = 0;
			String paramList = "";
			if(logger.isDebugEnabled()) {
				if (attributes != null && attributes.getAttribute() != null) {
					attrSize = attributes.getAttribute().size();
					for (Attribute attr:attributes.getAttribute()) {
						if (attrList.length() != 0)
							attrList = attrList + ", ";
						if (attr.getType().toString().equals("PASSWORD_STRING"))
							attrList = attrList + attr.getName() + "=********";
						else
							attrList = attrList + attr.getName() + "=" + attr.getValue();
					}
				}
				if (columns != null && columns.getColumn() != null) {
					colSize = columns.getColumn().size();
					for (Column col:columns.getColumn()) {
						if (colList.length() != 0)
							colList = colList + ", ";
						String colType = "UNKNOWN";
						if (col.getDataType() != null && col.getDataType().getSqlType() != null && col.getDataType().getSqlType().getDefinition() != null)
							colType = col.getDataType().getSqlType().getDefinition();
						colList = colList + col.getName() + "  " + colType;
					}
				}
				if (parameters != null && parameters.getParameter() != null) {
					paramSize = parameters.getParameter().size();
					for (Parameter param:parameters.getParameter()) {
						if (paramList.length() != 0)
							paramList = paramList + ", ";
						String paramType = "UNKNOWN";
						if (param.getDataType() != null && param.getDataType().getSqlType() != null && param.getDataType().getSqlType().getDefinition() != null)
							paramType = param.getDataType().getSqlType().getDefinition();
						paramList = paramList + param.getName() + "  " + paramType;
					}
				}
			}
			if(actionName.equalsIgnoreCase(RebindDAO.action.SQL_TABLE.name()))
			{
				logger.info("updateSqlTable("+resourcePath+")");
				if(logger.isDebugEnabled()) {
					logger.debug("RebindWSDAOImpl.takeRebindFolderAction("+actionName+").  Invoking port.updateSqlTable("+
					"\n            resourcePath=\""+resourcePath+"\","+
					"\n             detailLevel=\""+detailLevel+"\", "+
					"\n                SQL_TEXT: \""+procedureText+"\","+
					"\n               modelType="+modelType+","+
					"\n        isExplicitDesign="+isExplicitDesign+","+
					"\n              COLUMNS:["+colList+"],"+
					"\n              annotation="+ annotationStr+","+
					"\n              ATTRIBUTES:["+attrList+"])."+
					"\n                #columns="+colSize+
					"\n             #attributes="+attrSize);
				}
				
				port.updateSqlTable(resourcePath, detailLevel, procedureText, model, isExplicitDesign, columns, annotation, attributes);
				
				if(logger.isDebugEnabled()) {
					logger.debug("RebindWSDAOImpl.takeRebindFolderAction("+actionName+").  Success: port.updateSqlTable().");
				}
			
			}else if(actionName.equalsIgnoreCase(RebindDAO.action.SQL_SCRIPT_PROCEDURE.name()))
			{
				logger.info("updateSqlScriptProcedure("+resourcePath+")");
				if(logger.isDebugEnabled()) {
					logger.debug("RebindWSDAOImpl.takeRebindFolderAction("+actionName+").  Invoking port.updateSqlScriptProcedure("+
							"\n            resourcePath=\""+resourcePath+"\","+
							"\n             detailLevel=\""+detailLevel+"\", "+
							"\n          PROCEDURE_TEXT: \""+procedureText+"\","+
							"\n               modelType="+modelType+","+
							"\n        isExplicitDesign="+isExplicitDesign+","+
							"\n              PARAMETERS:["+paramList+"],"+
							"\n              annotation="+ annotationStr+","+
							"\n              ATTRIBUTES:["+attrList+"])."+
							"\n             #parameters="+paramSize+
							"\n             #attributes="+attrSize);
				}
				
				port.updateSqlScriptProcedure(resourcePath, detailLevel, procedureText, model, isExplicitDesign, parameters, annotation, attributes);
				
				if(logger.isDebugEnabled()) {
					logger.debug("RebindWSDAOImpl.takeRebindFolderAction("+actionName+").  Success: port.updateSqlScriptProcedure().");
				}
				
			}else if(actionName.equalsIgnoreCase(RebindDAO.action.EXTERNAL_SQL_PROCEDURE.name()))
			{	
				logger.info("updateExternalSqlProcedure("+resourcePath+")");
				if(logger.isDebugEnabled()) {
					logger.debug("RebindWSDAOImpl.takeRebindFolderAction("+actionName+").  Invoking ort.updateExternalSqlProcedure("+
							"\n            resourcePath=\""+resourcePath+"\","+
							"\n             detailLevel=\""+detailLevel+"\", "+
							"\n          PROCEDURE_TEXT: \""+procedureText+"\","+
							"\n        usedResourcePath=\""+usedResourcePath+"\","+
							"\n              PARAMETERS:["+paramList+"],"+
							"\n              annotation="+ annotationStr+","+
							"\n              ATTRIBUTES:["+attrList+"])."+
							"\n             #parameters="+paramSize+
							"\n             #attributes="+attrSize);
				}
				
				port.updateExternalSqlProcedure(resourcePath, detailLevel, procedureText, usedResourcePath, parameters, annotation, attributes);
				
				if(logger.isDebugEnabled()) {
					logger.debug("RebindWSDAOImpl.takeRebindFolderAction("+actionName+").  Success: port.updateExternalSqlProcedure().");
				}

			}else if(actionName.equalsIgnoreCase(RebindDAO.action.BASIC_TRANSFORM_PROCEDURE.name()))
			{	
				logger.info("updateBasicTransformProcedure("+resourcePath+")");
				if(logger.isDebugEnabled()) {
					logger.debug("RebindWSDAOImpl.takeRebindFolderAction("+actionName+").  Invoking port.updateBasicTransformProcedure("+
							"\n            resourcePath=\""+resourcePath+"\","+
							"\n             detailLevel=\""+detailLevel+"\", "+
							"\n          PROCEDURE_TEXT: \""+procedureText+"\","+
							"\n        usedResourcePath=\""+usedResourcePath+"\","+
							"\n        usedResourceType=\""+resourceType+"\","+
							"\n              annotation="+ annotationStr+","+
							"\n              ATTRIBUTES:["+attrList+"])."+
							"\n             #attributes="+attrSize);
				}
				
				port.updateBasicTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, annotation, attributes);
				
				if(logger.isDebugEnabled()) {
					logger.debug("RebindWSDAOImpl.takeRebindFolderAction("+actionName+").  Success: port.updateBasicTransformProcedure().");
				}
				
			}else if(actionName.equalsIgnoreCase(RebindDAO.action.XSLT_TRANSFORM_PROCEDURE.name()))
			{	
				logger.info("updateXsltTransformProcedure("+resourcePath+")");
				if(logger.isDebugEnabled()) {
					logger.debug("RebindWSDAOImpl.takeRebindFolderAction("+actionName+").  Invoking port.updateXsltTransformProcedure("+
							"\n            resourcePath=\""+resourcePath+"\","+
							"\n             detailLevel=\""+detailLevel+"\", "+
							"\n        usedResourcePath=\""+usedResourcePath+"\","+
							"\n        usedResourceType=\""+resourceType+"\","+
							"\n          PROCEDURE_TEXT: \""+procedureText+"\","+
							"\n               modelType="+modelType+","+
							"\n              annotation="+ annotationStr+","+
							"\n        isExplicitDesign="+isExplicitDesign+","+
							"\n              PARAMETERS:["+paramList+"],"+
							"\n              ATTRIBUTES:["+attrList+"])."+
							"\n             #parameters="+paramSize+
							"\n             #attributes="+attrSize);
				}
				
				port.updateXsltTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, procedureText, model, annotation, isExplicitDesign, parameters, attributes);
				
				if(logger.isDebugEnabled()) {
					logger.debug("RebindWSDAOImpl.takeRebindFolderAction("+actionName+").  Success: port.updateXsltTransformProcedure().");
				}
			
			}else if(actionName.equalsIgnoreCase(RebindDAO.action.STREAM_TRANSFORM_PROCEDURE.name()))
			{	
				logger.info("updateStreamTransformProcedure("+resourcePath+")");
				if(logger.isDebugEnabled()) {
					logger.debug("RebindWSDAOImpl.takeRebindFolderAction("+actionName+").  Invoking port.updateStreamTransformProcedure("+
							"\n            resourcePath=\""+resourcePath+"\","+
							"\n             detailLevel=\""+detailLevel+"\", "+
							"\n        usedResourcePath=\""+usedResourcePath+"\","+
							"\n        usedResourceType=\""+resourceType+"\","+
							"\n               modelType="+modelType+","+
							"\n        isExplicitDesign="+isExplicitDesign+","+
							"\n              PARAMETERS:["+paramList+"],"+
							"\n              annotation="+ annotationStr+","+
							"\n              ATTRIBUTES:["+attrList+"])."+
							"\n             #parameters="+paramSize+
							"\n             #attributes="+attrSize);
				}
				
				port.updateStreamTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, model, isExplicitDesign, parameters, annotation, attributes);
				
				if(logger.isDebugEnabled()) {
					logger.debug("RebindWSDAOImpl.takeRebindFolderAction("+actionName+").  Success: port.updateStreamTransformProcedure().");
				}
			
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
