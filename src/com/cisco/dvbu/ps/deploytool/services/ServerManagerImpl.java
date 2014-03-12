/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;

import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.deploytool.dao.ServerDAO;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.ServerWSDAOImpl;

public class ServerManagerImpl implements ServerManager {

	private ServerDAO serverDAO = null;
	
	// Get the configuration property file set in the environment with a default of deploy.properties
	String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");

	private static Log logger = LogFactory.getLog(ServerManagerImpl.class);
	
	@Override
	public void startServer(String serverId, String pathToServersXML) throws CompositeException {
		String prefix = "startServer";
		// Extract variables for the serverId
		serverId = CommonUtils.extractVariable(prefix, serverId, propertyFile, true);

		if(logger.isDebugEnabled()){
			logger.debug(" Entering ServerManagerImpl.start() with following params - serverId: " + serverId + ", pathToServersXML: " + pathToServersXML);
		}
		try {
			serverManagerAction(ServerDAO.action.START.name(), serverId, pathToServersXML);
		} catch (CompositeException e) {
			logger.error("Error while starting server: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
	}	
	
	@Override
	public void stopServer(String serverId, String pathToServersXML)  throws CompositeException {

		String prefix = "stopServer";
		// Extract variables for the serverId
		serverId = CommonUtils.extractVariable(prefix, serverId, propertyFile, true);

		if(logger.isDebugEnabled()){
			logger.debug(" Entering ServerManagerImpl.start() with following params - serverId: " + serverId + ", pathToServersXML: " + pathToServersXML);
		}
		try {
			serverManagerAction(ServerDAO.action.STOP.name(), serverId, pathToServersXML);
		} catch (CompositeException e) {
			logger.error("Error while stopping server: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
	}

	@Override
	public void restartServer(String serverId, String pathToServersXML) throws CompositeException {

		String prefix = "restartServer";
		// Extract variables for the serverId
		serverId = CommonUtils.extractVariable(prefix, serverId, propertyFile, true);

		if(logger.isDebugEnabled()){
			logger.debug(" Entering ServerManagerImpl.start() with following params - serverId: " + serverId + ", pathToServersXML: " + pathToServersXML);
		}
		try {
			serverManagerAction(ServerDAO.action.RESTART.name(), serverId, pathToServersXML);
		} catch (CompositeException e) {
			logger.error("Error while restarting server: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
	}

	private void serverManagerAction(String actionName, String serverId, String pathToServersXML) throws CompositeException {

		String prefix = "serverManagerAction";
		// Extract variables for the serverId
		serverId = CommonUtils.extractVariable(prefix, serverId, propertyFile, true);

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		try {
			if(logger.isInfoEnabled()){
				logger.info("processing action "+ actionName + " on server "+ serverId);
			}
			getServerDAO().takeServerManagerAction(actionName, serverId, pathToServersXML);
		} catch (CompositeException e) {
			logger.error("Error on server action (" + actionName + "): ", e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
	}
	
	/**
	 * @return serverDAO
	 */
	public ServerDAO getServerDAO() {
		if(this.serverDAO == null){
			this.serverDAO = new ServerWSDAOImpl();
		}
		return serverDAO;
	}

	/**
	 * @param set serverDAO
	 */
	public void setServerDAO(ServerDAO serverDAO) {
		this.serverDAO = serverDAO;
	}	

}
