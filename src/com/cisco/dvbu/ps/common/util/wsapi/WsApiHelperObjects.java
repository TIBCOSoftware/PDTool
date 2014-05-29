/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.common.util.wsapi;

import java.util.List;

import org.apache.commons.logging.Log;
import org.jdom.Element;

import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.PropertyManager;
import com.cisco.dvbu.ps.common.util.XMLUtils;
import com.cisco.dvbu.ps.deploytool.dao.ServerDAO;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.ServerWSDAOImpl;

public class WsApiHelperObjects {

	private static ServerDAO serverDAO = null;
    private static String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");

	// -- New method with logging capabilities
	public static CompositeServer getServerLogger(String serverId, String pathToServersXML, String prefix, Log logger) throws CompositeException {
		
		// Get the server information
		CompositeServer serverInfo = getServerImpl(serverId, pathToServersXML);

		// Validate the configuration property file exists
		if (!PropertyManager.getInstance().doesPropertyFileExist(propertyFile)) {
			throw new ApplicationException("The property file does not exist for CONFIG_PROPERTY_FILE="+propertyFile);
		}
		
		String validOptions = "true,false";
	    // Get the property from the property file
	    boolean debug1 = false;
	    // Get debug2 (level two) from the property file to determine if server info should be printed out or not.
	    String debug = CommonUtils.getFileOrSystemPropertyValue(propertyFile, "DEBUG2");
	    boolean debug2 = false;
	    if (debug != null && validOptions.contains(debug)) {
	    	debug2 = Boolean.valueOf(debug);
	    }
	    boolean debug3 = false;
	    /* Example servers.xml
	        <id>localhost9400</id>
		    <hostname>localhost</hostname>
		    <port>9400</port>
		    <usage>DEV6.2</usage>
		    <user>admin</user>
		    <encryptedpassword>Encrypted:7F6324FFD300BE8F</encryptedpassword>
		    <domain>composite</domain>
		    <cishome>C:\CompositeSoftware\CIS6.2.0</cishome>
		    <clustername>cluster2</clustername>
		    <site>US East</site>
		    <useHttps>false</useHttps>
			<allowVariables>false</allowVariables>
	     */
        CommonUtils.writeOutput("Server Info:",														prefix,"-debug2",logger,debug1,debug2,debug3);
        CommonUtils.writeOutput("  Server-id=               "+serverInfo.getId(),					prefix,"-debug2",logger,debug1,debug2,debug3);
        CommonUtils.writeOutput("  Server-hostname=         "+serverInfo.getHostname(),				prefix,"-debug2",logger,debug1,debug2,debug3);
        CommonUtils.writeOutput("  Server-port=             "+String.valueOf(serverInfo.getPort()),	prefix,"-debug2",logger,debug1,debug2,debug3);
        CommonUtils.writeOutput("  Server-usage=            "+serverInfo.getUsage(),				prefix,"-debug2",logger,debug1,debug2,debug3);
        CommonUtils.writeOutput("  Server-user=             "+serverInfo.getUser(),					prefix,"-debug2",logger,debug1,debug2,debug3);
        CommonUtils.writeOutput("  Server-encryptedpassword=********",								prefix,"-debug2",logger,debug1,debug2,debug3);
        CommonUtils.writeOutput("  Server-domain=           "+serverInfo.getDomain(),				prefix,"-debug2",logger,debug1,debug2,debug3);
        CommonUtils.writeOutput("  Server-cishome=          "+serverInfo.getCishome(),				prefix,"-debug2",logger,debug1,debug2,debug3);
        CommonUtils.writeOutput("  Server-clustername=      "+serverInfo.getClustername(),			prefix,"-debug2",logger,debug1,debug2,debug3);
        CommonUtils.writeOutput("  Server-site=             "+serverInfo.getSite(),					prefix,"-debug2",logger,debug1,debug2,debug3);
        CommonUtils.writeOutput("  Server-useHttps=         "+serverInfo.isUseHttps(),				prefix,"-debug2",logger,debug1,debug2,debug3);
        CommonUtils.writeOutput("  Server-allowVariables=   "+serverInfo.isAllowVariables(),		prefix,"-debug2",logger,debug1,debug2,debug3);
        CommonUtils.writeOutput("",																	prefix,"-debug2",logger,debug1,debug2,debug3);

		return serverInfo;
	}
	
	// -- could also put in CommonUtils
	public static CompositeServer getServer(String serverId, String pathToServersXML) throws CompositeException {
		return getServerImpl(serverId, pathToServersXML);
	}

	private static CompositeServer getServerImpl(String serverId, String pathToServersXML) throws CompositeException {
		CompositeServer retval = null;
		
		String prefix = "WsApiHelperObjects.getServerImpl";
		String serversXML = CommonUtils.getFileAsString(pathToServersXML);
		Element el = XMLUtils.getDocumentFromString(serversXML);
		@SuppressWarnings("unchecked")
		List<Element> servers = el.getChildren();
		
		// -- too lazy to write XPath expression to get server directly
		for (Element server : servers) {				
			
			String id = server.getChildText("id");
			
			if (id.equals(serverId)) {
				
				retval = new CompositeServer();
				
				// Determine whether to extract variables or not
                String allowVariables = CommonUtils.extractVariable(prefix, server.getChildText("allowVariables"), propertyFile, true);
            	retval.setAllowVariables(false);
                if(allowVariables != null && allowVariables.trim().length() > 0) {
                	retval.setAllowVariables(Boolean.parseBoolean(allowVariables));
                } 
                
                // Construct server info while extracting variables
                if (retval.isAllowVariables())
                {
					retval.setId(CommonUtils.extractVariable(prefix, server.getChildText("id"), propertyFile, true));
					retval.setDomain(CommonUtils.extractVariable(prefix, server.getChildText("domain"), propertyFile, true));
					retval.setHostname(CommonUtils.extractVariable(prefix, server.getChildText("hostname"), propertyFile, true));
					
					String encryptedpassword = CommonUtils.extractVariable(prefix, server.getChildText("encryptedpassword"), propertyFile, true);
					String decryptedpassword = null;
					if(encryptedpassword != null){
						decryptedpassword = CommonUtils.decrypt(encryptedpassword);
					}
					retval.setPassword(decryptedpassword);
					
					retval.setPort(CommonUtils.extractVariable(prefix, server.getChildText("port"), propertyFile, true));
					retval.setUsage(CommonUtils.extractVariable(prefix, server.getChildText("usage"), propertyFile, true));
					retval.setUser(CommonUtils.extractVariable(prefix, server.getChildText("user"), propertyFile, true));
	
					retval.setCishome(CommonUtils.extractVariable(prefix, server.getChildText("cishome"), propertyFile, true));
					retval.setClustername(CommonUtils.extractVariable(prefix, server.getChildText("clustername"), propertyFile, true));
					retval.setSite(CommonUtils.extractVariable(prefix, server.getChildText("site"), propertyFile, true));
	
	                String useHttps = CommonUtils.extractVariable(prefix, server.getChildText("useHttps"), propertyFile, true);
                	retval.setUseHttps(false);
	                if(useHttps != null && useHttps.trim().length() > 0) {
	                	retval.setUseHttps(Boolean.parseBoolean(useHttps));
	                }
	                
	             // Construct server info while not extracting variables
                } else {
					retval.setId(server.getChildText("id"));
					retval.setDomain(server.getChildText("domain"));
					retval.setHostname(server.getChildText("hostname"));
					
					String encryptedpassword = server.getChildText("encryptedpassword");
					String decryptedpassword = null;
					if(encryptedpassword != null){
						decryptedpassword = CommonUtils.decrypt(encryptedpassword);
					}
					retval.setPassword(decryptedpassword);
					
					retval.setPort(server.getChildText("port"));
					retval.setUsage(server.getChildText("usage"));
					retval.setUser(server.getChildText("user"));
	
					retval.setCishome(server.getChildText("cishome"));
					retval.setClustername(server.getChildText("clustername"));
					retval.setSite(server.getChildText("site"));
	
	                String useHttps = server.getChildText("useHttps");
                	retval.setUseHttps(false);
	                if(useHttps != null && useHttps.trim().length() > 0) {
	                	retval.setUseHttps(Boolean.parseBoolean(useHttps));
	                }
                }
				break;
			}
		}
		
		if (retval == null) {
			throw new ApplicationException("No server information was found for serverId="+serverId);
		}

		return retval;		
	}
	
	// Ping the Composite Server based on the CompositeServer server info
	public static void pingServer(CompositeServer serverInfo, Boolean pingServer) throws CompositeException {
				
		// Get the configuration property file set in the environment with a default of deploy.properties
		String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");
		// Get the property from the property file
		String propertValue = CommonUtils.extractVariable("pingServer", CommonUtils.getFileOrSystemPropertyValue(propertyFile,"CIS_PING_SERVER"), propertyFile, true);
		// Only override the ping server variable if it is found and contains a valid value of [true|false], otherwise keep the original default
		if (propertValue != null && propertValue.length() > 0) {
			if (propertValue.equalsIgnoreCase("true"))
				pingServer = true;
			if (propertValue.equalsIgnoreCase("false"))
				pingServer = false;
		}
			
		// Ping the Server to insure that it can be reached
		if (pingServer) {
			getServerDAO().pingServer(serverInfo);
		}
	}

	
	/**
	 * @return serverDAO
	 */
	public static ServerDAO getServerDAO() {
		if(serverDAO == null){
			serverDAO = new ServerWSDAOImpl();
		}
		return serverDAO;
	}
}
