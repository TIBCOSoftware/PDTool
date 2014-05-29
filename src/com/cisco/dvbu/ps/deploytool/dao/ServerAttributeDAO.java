/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao;

import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.compositesw.services.system.util.common.Attribute;
import com.compositesw.services.system.util.common.AttributeList;
import com.compositesw.services.system.util.common.AttributeDef;
import com.compositesw.services.system.util.common.AttributeDefList;

public interface ServerAttributeDAO {

	public static enum action {UPDATE};

	/**
	 * Update Server Attributes method updates the server attribute configurations for the passed in 
	 * server attribute Ids list found in the the passed in ServerAttributeModule.xml file for the 
	 * target server Id 
	 * @param actionName action to perform (update)
	 * @param attributeList server attribute list
	 * @param serverId target server id from servers config xml
	 * @param pathToServersXML path to the server values xml
	 */
	public void takeServerAttributeAction(String actionName, AttributeList attributeList, String serverId, String pathToServersXML);

	/**
	 * Retrieves a server attribute associated with passed in server attribute path
	 * Get server attributes for the given paths.
	 * @param serverId target server config name
	 * @param serverAttrPath server attribute path
	 * @param pathToServersXML path to the server values xml
	 * @return Attribute if the attribute exists else null
	 */
	public Attribute getServerAttribute(String serverId, String serverAttrPath, String pathToServersXML);

	/**
	 * Get all server attributes of the given server attribute path.  
	 * This method traverses the resource tree from the starting server attribute path and builds a attribute list. 
	 * This will be an empty list for attribute definitions that are not of the "FOLDER" type.
	 * @param serverId target server config name
	 * @param startPath starting path of the server attribute folder e.g /server
	 * @param pathToServersXML path to the server values xml
	 * @return Attribute list with all attributes from the starting server attribute path
	 */	
	public AttributeList getServerAttributesFromPath(String serverId, String startPath, String pathToServersXML);

	/**
	 * Retrieves a server attribute definition associated with passed in server attribute path
	 * Get server attribute definitions for the given paths.
	 * @param serverId target server config name
	 * @param serverAttrPath server attribute path
	 * @param pathToServersXML path to the server values xml
	 * @return AttributeDef if the attribute exists else null
	 */
	public AttributeDef getServerAttributeDefinition(String serverId, String serverAttrPath, String pathToServersXML);

	/**
	 * Get all server attribute definitions of the given server attribute path.  
	 * This method traverses the resource tree from the starting server attribute path and builds a attribute definition list. 
	 * This will be an empty list for attribute definitions that are not of the "FOLDER" type.
	 * @param serverId target server config name
	 * @param startPath starting path of the server attribute folder e.g /server
	 * @param pathToServersXML path to the server values xml
	 * @return AttributeDef list with all attributes from the starting server attribute path
	 */	
	public AttributeDefList getServerAttributeDefsFromPath(String serverId, String startPath, String pathToServersXML);

	/**
	 * Get the server version
	 * @param serverId target server config name
	 * @param pathToServersXML path to the server values xml
	 * @return String The server version
	 */	
	public String getServerVersion(String serverId, String pathToServersXML) throws CompositeException;

}
