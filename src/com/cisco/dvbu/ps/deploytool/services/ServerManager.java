/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.services;

import com.cisco.dvbu.ps.common.exception.CompositeException;

public interface ServerManager {

	//---------------------------------------------------------------------------------------------------------
	/**
	 * Start method starts specified server  
	 * @param serverId target server id from servers config xml
	 * @param pathToServersXML path to the server values xml
	 */
	public void startServer(String serverId, String pathToServersXML) throws CompositeException;
	
	//---------------------------------------------------------------------------------------------------------
	/**
	 * Stop method stops specified server  
	 * @param serverId target server id from servers config xml
	 * @param pathToServersXML path to the server values xml
	 */
	public void stopServer(String serverId, String pathToServersXML) throws CompositeException;
	
	//---------------------------------------------------------------------------------------------------------
	/**
	 * Restart method restarts specified server  
	 * @param serverId target server id from servers config xml
	 * @param pathToServersXML path to the server values xml
	 */	
	public void restartServer(String serverId, String pathToServersXML) throws CompositeException;
	
}
