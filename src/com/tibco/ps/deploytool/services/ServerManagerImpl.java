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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;

import com.tibco.ps.common.CommonConstants;
import com.tibco.ps.common.exception.CompositeException;
import com.tibco.ps.common.util.CommonUtils;
import com.tibco.ps.deploytool.dao.ServerDAO;
import com.tibco.ps.deploytool.dao.wsapi.ServerWSDAOImpl;

public class ServerManagerImpl implements ServerManager {

	private ServerDAO serverDAO = null;
	
	// Get the configuration property file set in the environment with a default of deploy.properties
	String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");

	private static Log logger = LogFactory.getLog(ServerManagerImpl.class);
	
//	@Override
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
	
//	@Override
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

//	@Override
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
		String processedIds = null;

		// Extract variables for the serverId
		serverId = CommonUtils.extractVariable(prefix, serverId, propertyFile, true);

		// Set the Module Action Objective
		String s1 = (serverId == null) ? "no_serverId" : "Ids="+serverId;
		System.setProperty("MODULE_ACTION_OBJECTIVE", actionName+" : "+s1);

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
