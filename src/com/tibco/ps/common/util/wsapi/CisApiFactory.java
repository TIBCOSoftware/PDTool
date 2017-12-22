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
package com.tibco.ps.common.util.wsapi;

import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tibco.ps.common.BasicAuthenticator;
import com.tibco.ps.common.CommonConstants;
import com.tibco.ps.common.exception.CompositeException;
import com.tibco.ps.common.util.CommonUtils;
import com.tibco.ps.common.util.CompositeLogger;
import com.tibco.ps.common.util.Sleep;
import com.tibco.ps.deploytool.services.GroupManagerImpl;
import com.tibco.ps.deploytool.util.DeployUtil;
import com.compositesw.client.CaSession;
import com.compositesw.client.port.CaExecutePort;
import com.compositesw.client.port.CaResourcePort;
import com.compositesw.client.port.CaServerPort;
import com.compositesw.client.port.CaUserPort;
import com.compositesw.services.system.admin.Execute;
import com.compositesw.services.system.admin.ExecutePortType;
import com.compositesw.services.system.admin.Resource;
import com.compositesw.services.system.admin.ResourcePortType;
import com.compositesw.services.system.admin.Server;
import com.compositesw.services.system.admin.ServerPortType;
import com.compositesw.services.system.admin.User;
import com.compositesw.services.system.admin.UserPortType;
//import com.compositesw.services.system.util.;

public class CisApiFactory {

	private static Log logger = LogFactory.getLog(CisApiFactory.class);

	public static final String nsResourceUrl = "http://www.compositesw.com/services/system/admin";
	public static final String nsResourceUrl623 = "http://www.623.compositesw.com/services/system/admin";
	public static final String nsResourceName = "resource";

	public static final String nsExecuteUrl = "http://www.compositesw.com/services/system/admin";
	public static final String nsExecuteName = "execute";	

	public static final String nsUserUrl = "http://www.compositesw.com/services/system/admin";
	public static final String nsUserName = "user";		

	public static final String nsServerUrl = "http://www.compositesw.com/services/system/admin";
	public static final String nsServerName = "server";		

//	public static final String nsSessionUrl = "http://www.compositesw.com/services/system/util";
//	public static final String nsSessionName = "session";		

	public static int numRetries = getRetryConnections();
	public static int sleepRetry = getRetrySleep();
	public static boolean sleepDebug = true;

        static {
	    //for localhost testing only
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	    new javax.net.ssl.HostnameVerifier(){
 
	        public boolean verify(String hostname,
	                javax.net.ssl.SSLSession sslSession) {
	            return true;
	        }
	    });
	}
	
	/**
	 * Returns the ResourcePort of AdminAPI
	 * @param server Composite Server
	 * @return ResourcePort of AdminAPI
	 */
	public static ResourcePortType getResourcePort(CompositeServer server) {

		Authenticator.setDefault(new BasicAuthenticator(server));
		
		URL url = null;
                String protocol = "http";
                int wsPort = server.getPort();
                if (server.isUseHttps()) {
                  protocol = "https";
                  wsPort +=2;
                }
		try {
			url = new URL(protocol + "://" + server.getHostname() + ":" + wsPort + "/services/system/admin?wsdl");
			if(logger.isDebugEnabled()){
				logger.debug("Entering CisApiFactory.getResourcePort() with following params "+" url: "+url);
			}
		} catch (MalformedURLException e) {
			String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Creating Resource Port", "Admin API", "ExecutePort" , server);
			CompositeLogger.logException(e, errorMessage);
			throw new CompositeException(errorMessage,e);
		}
		
		int retry = 0;
		while (retry < numRetries) {
			retry++;
			try {
				Resource res = new Resource(url, new QName(nsResourceUrl, nsResourceName)); // Get the connection to the server.		
				if(logger.isDebugEnabled()){
					if (res != null)
					logger.debug("Entering CisApiFactory.getResourcePort(). Resource acquired "+" Resource: "+res.toString());
				}
				ResourcePortType port = res.getResourcePort(); // Get the server port
				if(logger.isDebugEnabled()){
					if (port != null)
					logger.debug("Entering CisApiFactory.getResourcePort(). Port acquired "+" Port: "+port.toString());
				}
				return port; // Return the port connection to the server.
			}
			catch (Exception e) {
				String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Getting Server Port, [CONNECT ATTEMPT="+retry+"]", "Admin API", "ServerPort" , server);
				if (retry == numRetries) {
					throw new CompositeException(errorMessage,e);
				} else {
					// Log the error and sleep before retrying
					CompositeLogger.logException(e, errorMessage);
					Sleep.sleep(sleepDebug, sleepRetry);
				}
			}
		} 
		throw new CompositeException("Maximum connection attempts reached without connecting to the Composite Information Server.");

	}

	
	/**
	 * Returns the ResourcePort of AdminAPI
	 * @param server Composite Server
	 * @return ResourcePort of AdminAPI
	 */
	/* 
	 * This was a test to see if there could be a second point of execution.  It did not work.
	 *  
	public static com.compositesw._623.services.system.admin.ResourcePortType getResourcePort623(CompositeServer server) {

		Authenticator.setDefault(new BasicAuthenticator(server));
		
		URL url = null;
                String protocol = "http";
                int wsPort = server.getPort();
                if (server.isUseHttps()) {
                  protocol = "https";
                  wsPort +=2;
                }
		try {
			url = new URL(protocol + "://" + server.getHostname() + ":" + wsPort + "/services/system/admin?wsdl");
		} catch (MalformedURLException e) {
			String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Creating Resource Port", "Admin API", "ExecutePort" , server);
			CompositeLogger.logException(e, errorMessage);
			throw new CompositeException(errorMessage,e);
		}
		
		int retry = 0;
		while (retry < numRetries) {
			retry++;
			try {
				com.compositesw.services.system.admin.Resource res = new com.compositesw.services.system.admin.Resource(url, new QName(nsResourceUrl, nsResourceName)); // Get the connection to the server.
			
				com.compositesw._623.services.system.admin.ResourcePortType port = (com.compositesw._623.services.system.admin.ResourcePortType) res.getResourcePort(); // Get the server port
				return port; // Return the port connection to the server.
			}
			catch (Exception e) {
				String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Getting Server Port, [CONNECT ATTEMPT="+retry+"]", "Admin API", "ServerPort" , server);
				if (retry == numRetries) {
					throw new CompositeException(errorMessage,e);
				} else {
					// Log the error and sleep before retrying
					CompositeLogger.logException(e, errorMessage);
					Sleep.sleep(sleepDebug, sleepRetry);
				}
			}
		} 
		throw new CompositeException("Maximum connection attempts reached without connecting to the Composite Information Server.");

	}	
	*/
	
	/**
	 * Returns the ResourcePort of Web API
	 * @param server Composite Server
	 * @return ResourcePort of Web API
	 */
	public static CaResourcePort getCaResourcePort(CompositeServer server){
		try {
			int retry = 0;
			while (retry < numRetries) {
				retry++;
				try {
					CaSession caSession = new CaSession(server.getId(),server.getCishome(),server.getPort(),server.getUser(),server.getDomain(),server.getPassword(),3600,false,false);
					if(logger.isDebugEnabled()){
						if (caSession != null)
						logger.debug("Entering CisApiFactory.getCaResourcePort(). Session acquired "+" Session: "+caSession.toString());
					}
					return caSession.getResourcePort();
				}
				catch (Exception e) {
					String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Getting Server Port, [CONNECT ATTEMPT="+retry+"]", "Admin API", "ServerPort" , server);
					if (retry == numRetries) {
						throw new CompositeException(errorMessage,e);
					} else {
						// Log the error and sleep before retrying
						CompositeLogger.logException(e, errorMessage);
						Sleep.sleep(sleepDebug, sleepRetry);
					}
				}
			} 
			throw new CompositeException("Maximum connection attempts reached without connecting to the Composite Information Server.");
			
		} catch (Exception e) {
			String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Creating CaResource Port", "Web API", "CaResourcePort" , server);
			CompositeLogger.logException(e, errorMessage);
			throw new CompositeException(errorMessage,e);
		}
	}

	/**
	 * Returns the UserPort of Web API
	 * @param server Composite Server
	 * @return UserPort of Web API
	 */
	public static CaUserPort getCaUserPort(CompositeServer server){
		try {
			int retry = 0;
			while (retry < numRetries) {
				retry++;
				try {
					CaSession caSession = new CaSession(server.getId(),server.getCishome(),server.getPort(),server.getUser(),server.getDomain(),server.getPassword(),3600,false,false);
					if(logger.isDebugEnabled()){
						if (caSession != null)
						logger.debug("Entering CisApiFactory.getCaUserPort(). Session acquired "+" Session: "+caSession.toString());
					}
					return caSession.getUserPort();
				}
				catch (Exception e) {
					String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Getting Server Port, [CONNECT ATTEMPT="+retry+"]", "Admin API", "ServerPort" , server);
					if (retry == numRetries) {
						throw new CompositeException(errorMessage,e);
					} else {
						// Log the error and sleep before retrying
						CompositeLogger.logException(e, errorMessage);
						Sleep.sleep(sleepDebug, sleepRetry);
					}
				}
			} 
			throw new CompositeException("Maximum connection attempts reached without connecting to the Composite Information Server.");					
					
		} catch (Exception e) {
			String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Creating CaUser Port", "Web API", "CaUserPort" , server);
			CompositeLogger.logException(e, errorMessage);
			throw new CompositeException(errorMessage,e);
		}
	}
	
	/**
	 * Returns the ServerPort of Web API
	 * @param server Composite Server
	 * @return ServerPort of Web API
	 */
	public static CaServerPort getCaServerPort(CompositeServer server){
		try {
			int retry = 0;
			while (retry < numRetries) {
				retry++;
				try {
					CaSession caSession = new CaSession(server.getId(),server.getCishome(),server.getPort(),server.getUser(),server.getDomain(),server.getPassword(),3600,false,false);
					if(logger.isDebugEnabled()){
						if (caSession != null)
						logger.debug("Entering CisApiFactory.getCaServerPort(). Session acquired "+" Session: "+caSession.toString());
					}
					return caSession.getServerPort();
				}
				catch (Exception e) {
					String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Getting Server Port, [CONNECT ATTEMPT="+retry+"]", "Admin API", "ServerPort" , server);
					if (retry == numRetries) {
						throw new CompositeException(errorMessage,e);
					} else {
						// Log the error and sleep before retrying
						CompositeLogger.logException(e, errorMessage);
						Sleep.sleep(sleepDebug, sleepRetry);
					}
				}
			} 
			throw new CompositeException("Maximum connection attempts reached without connecting to the Composite Information Server.");					
					
		} catch (Exception e) {
			String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Creating CaServer Port", "Web API", "CaServerPort" , server);
			CompositeLogger.logException(e, errorMessage);
			throw new CompositeException(errorMessage,e);
		}
		
	}
	
	/**
	 * Returns the ExecutePort of Web API
	 * @param server Composite Server
	 * @return ExecutePort of Web API
	 */
	public static CaExecutePort getCaExecutePort(CompositeServer server){
		try {
			int retry = 0;
			while (retry < numRetries) {
				retry++;
				try {
					CaSession caSession = new CaSession(server.getId(),server.getCishome(),server.getPort(),server.getUser(),server.getDomain(),server.getPassword(),3600,false,false);
					if(logger.isDebugEnabled()){
						if (caSession != null)
						logger.debug("Entering CisApiFactory.getCaExecutePort(). Session acquired "+" Session: "+caSession.toString());
					}
					return caSession.getExecutePort();
				}
				catch (Exception e) {
					String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Getting Server Port, [CONNECT ATTEMPT="+retry+"]", "Admin API", "ServerPort" , server);
					if (retry == numRetries) {
						throw new CompositeException(errorMessage,e);
					} else {
						// Log the error and sleep before retrying
						CompositeLogger.logException(e, errorMessage);
						Sleep.sleep(sleepDebug, sleepRetry);
					}
				}
			} 
			throw new CompositeException("Maximum connection attempts reached without connecting to the Composite Information Server.");					
					
		} catch (Exception e) {
			String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Creating CaExecute Port", "Web API", "CaExecutePort" , server);
			CompositeLogger.logException(e, errorMessage);
			throw new CompositeException(errorMessage,e);
		}
	}
	
	/**
	 * Returns the ExecutePort of AdminAPI
	 * @param server Composite Server
	 * @return ExecutePort of AdminAPI
	 */
	public static ExecutePortType getExecutePort(CompositeServer server) {

		Authenticator.setDefault(new BasicAuthenticator(server));
		
		URL url = null;
                String protocol = "http";
                int wsPort = server.getPort();
                if (server.isUseHttps()) {
                  protocol = "https";
                  wsPort +=2;
                }
		try {
			url = new URL(protocol + "://" + server.getHostname() + ":" + wsPort + "/services/system/admin?wsdl");
			if(logger.isDebugEnabled()){
				logger.debug("Entering CisApiFactory.getExecutePort() with following params "+" url: "+url);
			}
		} catch (MalformedURLException e) {
			String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Creating Execute Port", "Admin API", "ExecutePort" , server);
			CompositeLogger.logException(e, errorMessage);
			throw new CompositeException(errorMessage,e);
		}
		
		int retry = 0;
		while (retry < numRetries) {
			retry++;
			try {
				Execute exec = new Execute(url, new QName(nsExecuteUrl, nsExecuteName)); // Get the connection to the server.
				if(logger.isDebugEnabled()){
					if (exec != null)
					logger.debug("Entering CisApiFactory.getExecutePort(). Execute acquired "+" Execute: "+exec.toString());
				}
				ExecutePortType port = exec.getExecutePort(); // Get the server port
				if(logger.isDebugEnabled()){
					if (port != null)
					logger.debug("Entering CisApiFactory.getExecutePort(). Port acquired "+" Port: "+port.toString());
				}
				return port; // Return the port connection to the server.
			}
			catch (Exception e) {
				String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Getting Server Port, [CONNECT ATTEMPT="+retry+"]", "Admin API", "ServerPort" , server);
				if (retry == numRetries) {
					throw new CompositeException(errorMessage,e);
				} else {
					// Log the error and sleep before retrying
					CompositeLogger.logException(e, errorMessage);
					Sleep.sleep(sleepDebug, sleepRetry);
				}
			}
		} 
		throw new CompositeException("Maximum connection attempts reached without connecting to the Composite Information Server.");
	}
	
	/**
	 * Returns the UserPort of AdminAPI
	 * @param server Composite Server
	 * @return UserPort of AdminAPI
	 */
	public static UserPortType getUserPort(CompositeServer server) {

		Authenticator.setDefault(new BasicAuthenticator(server));
		
		URL url = null;
                String protocol = "http";
                int wsPort = server.getPort();
                if (server.isUseHttps()) {
                  protocol = "https";
                  wsPort +=2;
                }
		try {
			url = new URL(protocol + "://" + server.getHostname() + ":" + wsPort + "/services/system/admin?wsdl");
			if(logger.isDebugEnabled()){
				logger.debug("Entering CisApiFactory.getUserPort() with following params "+" url: "+url);
			}
		} catch (MalformedURLException e) {
			String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Creating User Port", "Admin API", "UserPort" , server);
			CompositeLogger.logException(e, errorMessage);
			throw new CompositeException(errorMessage,e);
		}
		
		int retry = 0;
		while (retry < numRetries) {
			retry++;
			try {
				User user = new User(url, new QName(nsUserUrl, nsUserName)); // Get the connection to the server.
				if(logger.isDebugEnabled()){
					if (user != null)
					logger.debug("Entering CisApiFactory.getUserPort(). User acquired "+" User: "+user.toString());
				}
				UserPortType port = user.getUserPort(); // Get the server port
				if(logger.isDebugEnabled()){
					if (port != null)
					logger.debug("Entering CisApiFactory.getUserPort(). Port acquired "+" Port: "+port.toString());
				}
				return port; // Return the port connection to the server.
			}
			catch (Exception e) {
				String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Getting Server Port, [CONNECT ATTEMPT="+retry+"]", "Admin API", "ServerPort" , server);
				if (retry == numRetries) {
					throw new CompositeException(errorMessage,e);
				} else {
					// Log the error and sleep before retrying
					CompositeLogger.logException(e, errorMessage);
					Sleep.sleep(sleepDebug, sleepRetry);
				}
			}
		} 
		throw new CompositeException("Maximum connection attempts reached without connecting to the Composite Information Server.");
	}	

	/**
	 * Returns the ServerPort of AdminAPI
	 * @param server Composite Server
	 * @return ServerPort of AdminAPI
	 */
	public static ServerPortType getServerPort(CompositeServer server) {

		Authenticator.setDefault(new BasicAuthenticator(server));
		
		URL url = null;
                String protocol = "http";
                int wsPort = server.getPort();
                if (server.isUseHttps()) {
                  protocol = "https";
                  wsPort +=2;
                }
		try {
			url = new URL(protocol + "://" + server.getHostname() + ":" + wsPort + "/services/system/admin?wsdl");
			if(logger.isDebugEnabled()){
				logger.debug("Entering CisApiFactory.getServerPort() with following params "+" url: "+url);
			}
		} catch (MalformedURLException e) {
			String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Creating Server Port", "Admin API", "ServerPort" , server);
			CompositeLogger.logException(e, errorMessage);
			throw new CompositeException(errorMessage,e);
		}
		
		int retry = 0;
		while (retry < numRetries) {
			retry++;
			try {
				Server srvr = new Server(url, new QName(nsServerUrl, nsServerName)); // Get the connection to the server.
				if(logger.isDebugEnabled()){
					if (srvr != null)
					logger.debug("Entering CisApiFactory.getServerPort(). Server acquired "+" Server: "+srvr.toString());
				}
				ServerPortType port = srvr.getServerPort(); // Get the server port
				if(logger.isDebugEnabled()){
					if (port != null)
					logger.debug("Entering CisApiFactory.getServerPort(). Port acquired "+" Port: "+port.toString());
				}
				return port; // Return the port connection to the server.
			}
			catch (Exception e) {
				String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Getting Server Port, [CONNECT ATTEMPT="+retry+"]", "Admin API", "ServerPort" , server);
				if (retry == numRetries) {
					throw new CompositeException(errorMessage,e);
				} else {
					// Log the error and sleep before retrying
					CompositeLogger.logException(e, errorMessage);
					Sleep.sleep(sleepDebug, sleepRetry);
				}
			}
		} 
		throw new CompositeException("Maximum connection attempts reached without connecting to the  to Composite Information Server.");		
	}
	
	
	/* *  POTENTIAL FUTURE CAPABILITY
	 * 
	 * Returns the SessionPort of UtilAPI
	 * @param server Composite Server
	 * @return SessionPort of UtilAPI
	 */
	/*
	public static SessionPortType getSessionPort(CompositeServer server) {

		Authenticator.setDefault(new BasicAuthenticator(server));
		
		URL url = null;
		try {
			url = new URL("http://" + server.getHostname() + ":" + server.getPort() + "/services/system/util?wsdl");
		} catch (MalformedURLException e) {
			String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Creating Session Port", "Util API", "SessionPort" , server);
			CompositeLogger.logException(e, errorMessage);
			throw new CompositeException(errorMessage,e);
		}
		
		int retry = 0;
		while (retry < numRetries) {
			retry++;
			try {
				Resource res = new Resource(url, new QName(nsSessionUrl, nsSessionName)); // Get the connection to the server.
			
				SessionPortType port = res.getResourcePort(); // Get the server port
				return port; // Return the port connection to the server.
			}
			catch (Exception e) {
				String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Getting Session Port, [CONNECT ATTEMPT="+retry+"]", "Util API", "SessionPort" , server);
				if (retry == numRetries) {
					throw new CompositeException(errorMessage,e);
				} else {
					// Log the error and sleep before retrying
					CompositeLogger.logException(e, errorMessage);
					Sleep.sleep(sleepDebug, sleepRetry);
				}
			}
		} 
		throw new CompositeException("Maximum connection attempts reached without connecting to the Composite Information Server.");

	}
*/
	/* *  POTENTIAL FUTURE CAPABILITY
	 * 
	 * Returns the SessionPort of Web API
	 * @param server Composite Server
	 * @return SessionPort of Web API
	 */
	/*
	 * 
	public static CaSessionPort getCaSessionPort(CompositeServer server){
		try {
			int retry = 0;
			while (retry < numRetries) {
				retry++;
				try {
					CaSession caSession = new CaSession(server.getId(),server.getCishome(),server.getPort(),server.getUser(),server.getDomain(),server.getPassword(),3600,false,false);
					return caSession.getResourcePort();
				}
				catch (Exception e) {
					String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Getting Session Port, [CONNECT ATTEMPT="+retry+"]", "Util API", "SessionPort" , server);
					if (retry == numRetries) {
						throw new CompositeException(errorMessage,e);
					} else {
						// Log the error and sleep before retrying
						CompositeLogger.logException(e, errorMessage);
						Sleep.sleep(sleepDebug, sleepRetry);
					}
				}
			} 
			throw new CompositeException("Maximum connection attempts reached without connecting to the Composite Information Server.");
			
		} catch (Exception e) {
			String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Creating CaResource Port", "Web API", "CaSessionPort" , server);
			CompositeLogger.logException(e, errorMessage);
			throw new CompositeException(errorMessage,e);
		}
	}
	*/
	
	
	// Get the number of retry attempts from the deployment property file
	private static int getRetryConnections() {
		int numRetries = 1;
		
		// Get the configuration property file set in the environment with a default of deploy.properties
		String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");
		// Get the property from the property file
		String propertValue = CommonUtils.extractVariable("retryConnections", CommonUtils.getFileOrSystemPropertyValue(propertyFile,"CIS_CONNECT_RETRY"), propertyFile, true);
		// Get the number of retries
		if (propertValue != null && propertValue.length() > 0) {
			try {
				numRetries = Integer.valueOf(propertValue);
				if (numRetries <= 1)
					numRetries = 1;
			} catch (Exception e) {
				numRetries = 1;
			}
		}
		return numRetries;
	}
	
	// Get the number of milliseconds to sleep during connection retry
	private static int getRetrySleep() {
		int sleepRetry = 0;
		
		// Get the configuration property file set in the environment with a default of deploy.properties
		String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");
		// Get the property from the property file
		String propertValue = CommonUtils.extractVariable("getSleepRetry", CommonUtils.getFileOrSystemPropertyValue(propertyFile,"CIS_CONNECT_RETRY_SLEEP_MILLIS"), propertyFile, true);
		// Get the number of retries
		if (propertValue != null && propertValue.length() > 0) {
			try {
				sleepRetry = Integer.valueOf(propertValue);
				if (sleepRetry < 0)
					sleepRetry = 0;
			} catch (Exception e) {
				sleepRetry = 0;
			}
		}
		return sleepRetry;
	}
}
