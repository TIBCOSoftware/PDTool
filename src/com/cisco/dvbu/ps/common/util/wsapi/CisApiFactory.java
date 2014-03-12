/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.common.util.wsapi;

import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import com.cisco.dvbu.ps.common.BasicAuthenticator;
import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.CompositeLogger;
import com.cisco.dvbu.ps.common.util.Sleep;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
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
			
				ResourcePortType port = res.getResourcePort(); // Get the server port
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
				ExecutePortType port = exec.getExecutePort(); // Get the server port
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
				UserPortType port = user.getUserPort(); // Get the server port
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
				ServerPortType port = srvr.getServerPort(); // Get the server port
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
