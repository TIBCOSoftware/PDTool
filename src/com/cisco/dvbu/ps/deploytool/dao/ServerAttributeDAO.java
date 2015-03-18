/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 * 
 * This software is released under the Eclipse Public License. The details can be found in the file LICENSE. 
 * Any dependent libraries supplied by third parties are provided under their own open source licenses as 
 * described in their own LICENSE files, generally named .LICENSE.txt. The libraries supplied by Cisco as 
 * part of the Composite Information Server/Cisco Data Virtualization Server, particularly csadmin-XXXX.jar, 
 * csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar, csext-XXXX.jar, csjdbc-XXXX.jar, 
 * csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar, and customproc-XXXX.jar (where -XXXX is an 
 * optional version number) are provided as a convenience, but are covered under the licensing for the 
 * Composite Information Server/Cisco Data Virtualization Server. They cannot be used in any way except 
 * through a valid license for that product.
 * 
 * This software is released AS-IS!. Support for this software is not covered by standard maintenance agreements with Cisco. 
 * Any support for this software by Cisco would be covered by paid consulting agreements, and would be billable work.
 * 
 */
package com.cisco.dvbu.ps.deploytool.dao;

import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeModule;

public interface ServerAttributeDAO {

	public static enum action {UPDATE};

	/**
	 * Update Server Attributes method updates the server attribute configurations for the passed in 
	 * server attribute Ids list found in the the passed in ServerAttributeModule.xml file for the 
	 * target server Id 
	 * @param actionName action to perform (update)
	 * @param serverAttributeModule server attribute module xml
	 * @param serverId target server id from servers config xml
	 * @param pathToServersXML path to the server values xml
	 */
	public void takeServerAttributeAction(String actionName, ServerAttributeModule serverAttributeModule, String serverId, String pathToServersXML);

	/**
	 * Retrieves a server attribute associated with passed in server attribute path
	 * Get server attributes for the given paths.
	 * @param serverId target server config name
	 * @param serverAttrPath server attribute path
	 * @param pathToServersXML path to the server values xml
	 * @return Attribute if the attribute exists else null
	 */
	public ServerAttributeModule getServerAttribute(String serverId, String serverAttrPath, String pathToServersXML);

	/**
	 * Get all server attributes of the given server attribute path.  
	 * This method traverses the resource tree from the starting server attribute path and builds a attribute list. 
	 * This will be an empty list for attribute definitions that are not of the "FOLDER" type.
	 * @param serverId target server config name
	 * @param startPath starting path of the server attribute folder e.g /server
	 * @param pathToServersXML path to the server values xml
	 * @param updateRule acts as a filter to only get the attribute paths defined by the update rule.
	 * @return ServerAttributeModule list with all attributes from the starting server attribute path
	 */	
	public ServerAttributeModule getServerAttributesFromPath(String serverId, String startPath, String pathToServersXML, String updateRule);

	/**
	 * Retrieves a server attribute definition associated with passed in server attribute path
	 * Get server attribute definitions for the given paths.
	 * @param serverId target server config name
	 * @param serverAttrPath server attribute path
	 * @param pathToServersXML path to the server values xml
	 * @return ServerAttributeModule if the attribute exists else null
	 */
	public ServerAttributeModule getServerAttributeDefinition(String serverId, String serverAttrPath, String pathToServersXML);

	/**
	 * Get all server attribute definitions of the given server attribute path.  
	 * This method traverses the resource tree from the starting server attribute path and builds a attribute definition list. 
	 * This will be an empty list for attribute definitions that are not of the "FOLDER" type.
	 * @param serverId target server config name
	 * @param startPath starting path of the server attribute folder e.g /server
	 * @param pathToServersXML path to the server values xml
	 * @param updateRule acts as a filter to only get the attribute paths defined by the update rule.
	 * @return ServerAttributeModule list with all attributes from the starting server attribute path
	 */	
	public ServerAttributeModule getServerAttributeDefsFromPath(String serverId, String startPath, String pathToServersXML, String updateRule);

	/**
	 * Get the server version
	 * @param serverId target server config name
	 * @param pathToServersXML path to the server values xml
	 * @return String The server version
	 */	
	public String getServerVersion(String serverId, String pathToServersXML) throws CompositeException;

}
