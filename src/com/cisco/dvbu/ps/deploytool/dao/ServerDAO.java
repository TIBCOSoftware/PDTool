/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao;

import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;

public interface ServerDAO {
	
	public static enum action {START, STOP, RESTART};

	/**
	 * Starts/stops/restarts a Composite server via the monitor process 
	 * @param serverId target server id from servers config xml
	 * @param pathToServersXML path to the server values xml
	 */
	public void takeServerManagerAction(String actionName, String serverId, String pathToServersXML)
		throws CompositeException;

	/**
	 * Ping a Composite server 
	 * @param targetServer - contains all necessary CIS target server info to connect to a CIS instance
	 */
	public void pingServer(CompositeServer targetServer) throws CompositeException;

}
