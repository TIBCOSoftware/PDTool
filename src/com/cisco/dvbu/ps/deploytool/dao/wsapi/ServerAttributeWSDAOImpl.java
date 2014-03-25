/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao.wsapi;

/**
 * Initial Version:	Mike Tinius :: 6/8/2011		
 * Modifications:	initials :: Date
 * 
 */

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CompositeLogger;
import com.cisco.dvbu.ps.common.util.wsapi.CisApiFactory;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.compositesw.services.system.admin.GetServerAttributeDefChildrenSoapFault;
import com.compositesw.services.system.admin.GetServerAttributeDefsSoapFault;
import com.compositesw.services.system.admin.GetServerAttributesSoapFault;
import com.compositesw.services.system.admin.ServerPortType;
import com.compositesw.services.system.admin.UpdateServerAttributesSoapFault;
import com.compositesw.services.system.admin.server.PathList;
import com.compositesw.services.system.util.common.Attribute;
import com.compositesw.services.system.util.common.AttributeDef;
import com.compositesw.services.system.util.common.AttributeDefList;
import com.compositesw.services.system.util.common.AttributeList;
import com.compositesw.services.system.util.common.AttributeType;

public class ServerAttributeWSDAOImpl implements ServerAttributeDAO {

	private static Log logger = LogFactory.getLog(ServerAttributeWSDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO#takeServerAttributeAction(java.lang.String, com.compositesw.services.system.util.common.AttributeList, java.lang.String, java.lang.String)
	 */
//	@Override
	public void takeServerAttributeAction(String actionName, AttributeList attributeList, String serverId, String pathToServersXML) throws CompositeException {
		
		// read target server properties from xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ServerAttributeWSDAOImpl.takeServerAttributeAction("+actionName+")", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the server port based on target server name
		ServerPortType port = CisApiFactory.getServerPort(targetServer);

		try {
			if(actionName.equalsIgnoreCase(ServerAttributeDAO.action.UPDATE.name())){
				port.updateServerAttributes(attributeList);
			}
			if(logger.isDebugEnabled())	{
				logger.debug("ServerAttributeWSDAOImpl.takeServerAttributeAction::actionName="+actionName+" was successful.");
			}

		} catch (UpdateServerAttributesSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), ServerAttributeDAO.action.UPDATE.name(), "ServerAttribute", "attributeList", targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO#getServerAttribute(java.lang.String, java.lang.String, java.lang.String)
	 * Get server attributes for the given paths.
	 */
//	@Override
	public Attribute getServerAttribute(String serverId, String serverAttrPath, String pathToServersXML)  throws CompositeException {

		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ServerAttributeWSDAOImpl.getServerAttribute", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the server port based on target server name
		ServerPortType port = CisApiFactory.getServerPort(targetServer);

		try {
			PathList paths = new PathList();
			paths.getPath().add(serverAttrPath);
			
			AttributeList attributeList = port.getServerAttributes(paths);
			if(attributeList != null && attributeList.getAttribute() != null && !attributeList.getAttribute().isEmpty()){
				
				List<Attribute> attributes = attributeList.getAttribute();
				
				for (Attribute attribute : attributes) {
					if(attribute.getName().equalsIgnoreCase(serverAttrPath)){
						return attribute;
					}
				}
				
			}
			
		} catch (GetServerAttributesSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getServerAttribute", "ServerAttribute", serverAttrPath, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO#getServerVersion(java.lang.String, java.lang.String, java.lang.String)
	 * Get server attributes for the given paths.
	 */
//	@Override
	public String getServerVersion(String serverId, String pathToServersXML)  throws CompositeException {

		// Get the CIS Version
		String serverAttrPath = "/server/config/info/versionFull";
		
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ServerAttributeWSDAOImpl.getServerAttribute", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the server port based on target server name
		ServerPortType port = CisApiFactory.getServerPort(targetServer);

		try {
			PathList paths = new PathList();
			paths.getPath().add(serverAttrPath);
			
			AttributeList attributeList = port.getServerAttributes(paths);
			if(attributeList != null && attributeList.getAttribute() != null && !attributeList.getAttribute().isEmpty()){
				
				List<Attribute> attributes = attributeList.getAttribute();
				
				for (Attribute attribute : attributes) {
					if(attribute.getName().equalsIgnoreCase(serverAttrPath)){
						return attribute.getValue();
					}
				}
				
			}
			
		} catch (GetServerAttributesSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getServerAttribute", "ServerAttribute", serverAttrPath, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO#getServerAttributesFromPath(java.lang.String, java.lang.String, java.lang.String)
	 * Get the child server attributes of the given path.  This will be an empty list for attribute definitions that are not of the "FOLDER" type.
	 */
//	@Override
	public AttributeList getServerAttributesFromPath(String serverId, String startpath, String pathToServersXML) {

		AttributeList returnAttributeList = new AttributeList();
		
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ServerAttributeWSDAOImpl.getServerAttributesFromPath", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the server port based on target server name
		ServerPortType port = CisApiFactory.getServerPort(targetServer);
		
		addServerAttributesFromPath(port, returnAttributeList, startpath, targetServer);
		
		return returnAttributeList;
	}
	
	private void addServerAttributesFromPath(ServerPortType port, AttributeList attributeList, String startpath, CompositeServer targetServer){
		try {
			AttributeDefList attributeDefList = port.getServerAttributeDefChildren(startpath);
			if(attributeDefList!= null && attributeDefList.getAttributeDef() != null && !attributeDefList.getAttributeDef().isEmpty()){

				List<AttributeDef> attributeDefs = attributeDefList.getAttributeDef();

				for (AttributeDef attributeDef : attributeDefs) {

					String currentPath = attributeDef.getName().toString();
					if(attributeDef.getType().equals(AttributeType.FOLDER)){

						addServerAttributesFromPath(port, attributeList, currentPath, targetServer);

					} else {
						try {
							PathList paths = new PathList();
							paths.getPath().add(currentPath);
							
							AttributeList attrList = port.getServerAttributes(paths);
							if(attrList != null && attrList.getAttribute() != null && !attrList.getAttribute().isEmpty()){
								
								List<Attribute> attributes = attrList.getAttribute();
								
								for (Attribute attribute : attributes) {
									if(attribute.getName().equalsIgnoreCase(currentPath)){
										attributeList.getAttribute().add(attribute);
									}
								}
							}
						} catch (GetServerAttributesSoapFault e) {
							CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "addServerAttributesFromPath", "ServerAttribute", currentPath, targetServer),e.getFaultInfo());
							throw new ApplicationException(e.getMessage(), e);
						}						
					}
				}
			}
		} catch (GetServerAttributeDefChildrenSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "addServerAttributesFromPath", "ServerAttribute", startpath, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}		
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO#getServerAttributeDefinition(java.lang.String, java.lang.String, java.lang.String)
	 * Get server attribute definitions for the given paths.
	 */
//	@Override
	public AttributeDef getServerAttributeDefinition(String serverId, String serverAttrPath, String pathToServersXML) {

		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ServerAttributeWSDAOImpl.getServerAttributeDefinition", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the server port based on target server name
		ServerPortType port = CisApiFactory.getServerPort(targetServer);

		try {
			PathList paths = new PathList();
			paths.getPath().add(serverAttrPath);
			
			AttributeDefList attributeDefList = port.getServerAttributeDefs(paths);
			if(attributeDefList != null && attributeDefList.getAttributeDef() != null && !attributeDefList.getAttributeDef().isEmpty()){
				
				List<AttributeDef> attributeDefs = attributeDefList.getAttributeDef();
				
				for (AttributeDef attributeDef : attributeDefs) {
					if(attributeDef.getName().equalsIgnoreCase(serverAttrPath)){
						return attributeDef;
					}
				}
				
			}
			
		} catch (GetServerAttributeDefsSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getServerAttributeDefinition", "ServerAttribute", serverAttrPath, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO#getServerAttributeDefsFromPath(java.lang.String, java.lang.String, java.lang.String)
	 * Get the child server attribute definitions of the given path.  This will be an empty list for attribute definitions that are not of the "FOLDER" type.
	 */
//	@Override
	public AttributeDefList getServerAttributeDefsFromPath(String serverId, String startpath, String pathToServersXML) {

		AttributeDefList returnAttributeDefList = new AttributeDefList();
		
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the server port based on target server name
		ServerPortType port = CisApiFactory.getServerPort(targetServer);
		
		addServerAttributeDefsFromPath(port, returnAttributeDefList, startpath, targetServer);
		
		return returnAttributeDefList;
	}
	
	private void addServerAttributeDefsFromPath(ServerPortType port, AttributeDefList attributeList, String startpath, CompositeServer targetServer){
		try {
			AttributeDefList attributeDefList = port.getServerAttributeDefChildren(startpath);
			if(attributeDefList!= null && attributeDefList.getAttributeDef() != null && !attributeDefList.getAttributeDef().isEmpty()){

				List<AttributeDef> attributeDefs = attributeDefList.getAttributeDef();

				for (AttributeDef attributeDef : attributeDefs) {

					if(attributeDef.getType().equals(AttributeType.FOLDER)){

						addServerAttributeDefsFromPath(port, attributeList, attributeDef.getName(), targetServer);

					} else {
						attributeList.getAttributeDef().add(attributeDef);
						
						// DEBUG
						if (attributeDef.getUpdateRule() == null) {
//							logger.debug("Update Rule is null: "+attributeDef.getName());
						} 
					}
				}
			}
		} catch (GetServerAttributeDefChildrenSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "addServerAttributeDefsFromPath", "ServerAttribute", startpath, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}		
	}
}
