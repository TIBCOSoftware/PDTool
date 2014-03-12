/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.common.util.jdbcapi;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;

public class JdbcConnector
{
    private static Log logger = LogFactory.getLog(JdbcConnector.class);

	/**
	 * Creates a java.sql.Connection to a CIS server based on server.xml configuration
	 * 
	 * @param serverId				  serverId in servers.xml
	 * @param pathToServersXML		  path to servers.xml
	 * @param pubDs					  published datasource to connect to. 
	 * 
	 * @return  java.sql.Connection   established JDBC connection
	 * @throws CompositeException
	 */
	public Connection connectToCis(CompositeServer cisServerConfig, String pubDs) throws CompositeException
	{
		String url = null;
        Connection conn = null;
		
		// Validate input:
        try    							{ 	validateCisConnectionParams(cisServerConfig); }
        catch (CompositeException e)	{ 	throw e;	}
        
// Get config values:        
        
        String host = cisServerConfig.getHostname();
        String jdbcPort = new Integer((cisServerConfig.getPort() + 1)).toString();  // 
        String cisDomain = cisServerConfig.getDomain();
        String userName = cisServerConfig.getUser();
        String userPwd = CommonUtils.decrypt(cisServerConfig.getPassword());

		long startTime = System.currentTimeMillis();
		if (logger.isDebugEnabled()) {
			logger.debug("Started establishing JDBC connection to CIS server " + host + ":" + jdbcPort);
		}
        
        try
        {
            Class.forName("cs.jdbc.driver.CompositeDriver");

            url = "jdbc:compositesw:dbapi@" + host + ":" + jdbcPort + "?domain=" + cisDomain + "&dataSource=" + pubDs; // e.g. "system";     

            conn = DriverManager.getConnection(url, userName, userPwd);
        }
        catch (ClassNotFoundException e)
        {
        	logger.error("Problem loading jdbc driver:\n",e);
         	throw new CompositeException("Problem loading jdbc driver:\n" + e.getMessage());
        }
        catch (Exception e)
        {
        	logger.error("Exception caught: \n",e);
         	throw new CompositeException("Exception caught:\n" + e.getMessage());
        }
        
		long endTime = System.currentTimeMillis();
		DecimalFormat threeDForm = new DecimalFormat("#.###");
		double elapsedTime = Double.valueOf(threeDForm.format((0.001 * (endTime - startTime))));
		if (logger.isDebugEnabled()) {
			logger.debug("CIS connection was established in " + elapsedTime + " seconds for database "+pubDs);
		}

        return conn;
	}
	
	/**
	 * Closes established JDBC connection
	 * 
	 * @param conn
	 * @throws CompositeException
	 */
	public void closeJdbcConnection(Connection conn) throws CompositeException
	{
		if (conn == null)
		{
//			throw new CompositeException("Unable to close existing connection - it is already null.");
			if (logger.isDebugEnabled()) {
				logger.debug("Unable to close existing connection - it is already null.");
			}
			return;
		}
		try
		{
			conn.close();
		}
		catch (SQLException e)
		{
        	logger.error("Unable to close JDBC connection to CIS:\n",e);
			throw new CompositeException("Unable to close JDBC connection to CIS:\n" + e.getMessage());
		}
	}
	
	/**
	 * Validates that CIS connection parameters are not empty
	 * 
	 * @param targetServer
	 * @param dsName
	 * @return	true if parameters are valid and false otherwise
	 */
	private void validateCisConnectionParams(CompositeServer targetServer) 
												throws CompositeException
	{
		if(targetServer == null)
		{
        	logger.error("Unable to proceed: targetServer is null, check configuration.");
			throw new CompositeException("Unable to proceed: targetServer is null, check configuration.");
		}
		String host = targetServer.getHostname();
		if (host == null || host.isEmpty())
		{
        	logger.error("Unable to proceed: Host name/ip is not defined for generateInputFile(). Check servers.xml");
        	throw new CompositeException("Unable to proceed: Host name/ip is not defined for generateInputFile(). Check servers.xml");
		}
		String port = new Integer(targetServer.getPort()).toString();
		if (port == null || port.length() == 0)
        {
        	logger.error("Unable to proceed: Port number is not defined for generateInputFile(). Check servers.xml");
        	throw new CompositeException("Unable to proceed: Port number is not defined for generateInputFile(). Check servers.xml");
        }
		String domain = targetServer.getDomain();
		if (domain == null || domain.length() == 0)
        {
        	logger.error("Unable to proceed: Domain is not defined for generateInputFile(). Check servers.xml");
        	throw new CompositeException("Unable to proceed: Domain is not defined for generateInputFile(). Check servers.xml");
        }	
		String userName = targetServer.getUser();
		if (userName == null || userName.length() == 0)
        {
        	logger.error("Unable to proceed: userName is not defined for generateInputFile(). Check servers.xml");
        	throw new CompositeException("Unable to proceed: userName is not defined for generateInputFile(). Check servers.xml");
        }
        String userPwd = targetServer.getPassword();
        if (userPwd == null || userPwd.length() == 0)
        {
        	logger.error("Unable to proceed: userPwd is not defined for generateInputFile(). Check servers.xml");
        	throw new CompositeException("Unable to proceed: userPwd is not defined for generateInputFile(). Check servers.xml");
        }
	}

} // end class
