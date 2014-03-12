/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao.wsapi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.exception.ValidationException;
import com.cisco.dvbu.ps.common.util.CompositeLogger;
import com.cisco.dvbu.ps.common.util.wsapi.CisApiFactory;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.dao.ServerDAO;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.compositesw.services.system.admin.ServerPortType;
import com.compositesw.services.system.admin.server.LicenseList;
import com.cs.admin.monitor.MonitorConnectionFactory;

public class ServerWSDAOImpl implements ServerDAO {

	private static Log logger = LogFactory.getLog(ServerWSDAOImpl.class);
	
	private static String STATUS_OPERATIONAL = "operational";
	private static String STATUS_STOPPED = "stopped";	
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO#takeServerAttributeAction(com.cisco.dvbu.ps.common.util.wsapi.CompositeServer)
	 */
	@Override
	public void pingServer(CompositeServer targetServer) throws CompositeException {	
		// Use the simple getLicesense invocation that takes no parameters.  This either works or it does not.
		// This method is used to test whether the "targetServer" information is correct or not.
		// Any exception with this method call indicates the "targetServer" information is incorrect.
		try {
			ServerPortType port = CisApiFactory.getServerPort(targetServer);
			LicenseList licenses = port.getLicenses();
		} catch(Exception e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "pingServer::The Server identified by id=["+targetServer.getId()+"] is unavailable.", "ServerManager", "serverId", targetServer));
			throw new ValidationException(e.getMessage(), e);
		}
	}
	
	@Override
	public void takeServerManagerAction(String actionName, String serverId, String pathToServersXML) throws CompositeException {	
		
		// -- read target server properties from xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ServerWSDAOImpl.takeServerManagerAction("+actionName+")", logger);
		
		String hostname = targetServer.getHostname();
		int port = targetServer.getPort() + 6;
		String username = targetServer.getUser();
		String password = targetServer.getPassword();
		String domain = targetServer.getDomain();
		
		String userAtDomain = username + "@" + domain;
		
		// -- from the api
		int timeout = 300000;
		
		try {
			
			// -- common logic regardless of action
			String[] servers = MonitorConnectionFactory.createConnection(hostname, port, 
					userAtDomain, password, timeout).getServerList();
			
			if (servers.length > 1)
				throw new CompositeException("Server Module does not support multiple instances of Composite per monitor process");
			
			String server = servers[0];
			
			// -- You can get back multiple status values from a single server
			//    You want to use MonitorConnectionFactory because the return class
			//    has only package visibility
			String[] statuses = MonitorConnectionFactory.createConnection(hostname, port, 
					userAtDomain, password, timeout)
				.getServerStatus(new String[] {server});
			
			// -- I've not seen a situation where multiple status messages from the
			//    same server were different
			String status = statuses[statuses.length - 1];
			
			/*******************************
			 *  START the Server
			 *******************************/
			if(actionName.equalsIgnoreCase(ServerDAO.action.START.name())) {
				
				if (STATUS_OPERATIONAL.equals(status)) {
					logger.info("Server " + server + " is operational - there is no need to start it.");
					return;
				}
				
				logger.info("Status of server " + server + " is: " + status + ". Starting server.");
				
				// -- this is a blocking call
				MonitorConnectionFactory.createConnection(hostname, port, 
						userAtDomain, password, timeout)
					.startServer(server);
				
				statuses = MonitorConnectionFactory.createConnection(hostname, port, 
						userAtDomain, password, timeout)
					.getServerStatus(new String[] {server});
				
				status = statuses[statuses.length - 1];	
				
				logger.info("Status of server " + server + " is now: " + status + ".");
				
				if (!STATUS_OPERATIONAL.equals(status))
					throw new CompositeException("Server " + server + " was not started successfully. Status is: " + status + ".");
				
				return;

				/*******************************
				 *  STOP the Server
				 *******************************/
			} else if(actionName.equalsIgnoreCase(ServerDAO.action.STOP.name())) {
				
				pingServer(targetServer);

				if (STATUS_STOPPED.equals(status)) {
					logger.info("Server " + server + " is stopped - there is no need to stop it.");
					return;
				}
				
				logger.info("Status of server " + server + " is: " + status + ". Stopping server.");
				
				MonitorConnectionFactory.createConnection(hostname, port, 
						userAtDomain, password, timeout)
					.stopServer(server);
				
				statuses = MonitorConnectionFactory.createConnection(hostname, port, 
						userAtDomain, password, timeout)
					.getServerStatus(new String[] {server});
				
				status = statuses[statuses.length - 1];	
				
				logger.info("Status of server " + server + " is now: " + status + ".");
				
				if (!STATUS_STOPPED.equals(status))
					throw new CompositeException("Server " + server + " was not stopped successfully.");
				
				return;

				/*******************************
				 *  RESTART the Server
				 *******************************/
			} else if(actionName.equalsIgnoreCase(ServerDAO.action.RESTART.name())) {
				
				pingServer(targetServer);

				// -- v2 - just go ahead and start it
				if (STATUS_STOPPED.equals(status)) {
					logger.info("Server " + server + " is stopped - it can not be restarted.");
					return;
				}
				
				logger.info("Status of server " + server + " is: " + status + ". Restarting server.");
				
				MonitorConnectionFactory.createConnection(hostname, port, 
						userAtDomain, password, timeout)
					.restartServer(server);
				
				statuses = MonitorConnectionFactory.createConnection(hostname, port, 
						userAtDomain, password, timeout)
					.getServerStatus(new String[] {server});
				
				status = statuses[statuses.length - 1];	
				
				logger.info("Status of server " + server + " is now: " + status + ".");
				
				if (!STATUS_OPERATIONAL.equals(status))
					throw new CompositeException("Server " + server + " was not restarted successfully. Status is: " + status + ".");
				
				return;

			}
			
			if(logger.isDebugEnabled())	{
				logger.debug("ServerWSDAOImpl.takeServerAttributeAction::actionName=" + actionName + " was successful.");
			}

		} catch (Exception e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), actionName, "Server", "serverId", targetServer));
			throw new ApplicationException(e.getMessage(), e);
		}
			
	}

}
