/*******************************************************************************
* Copyright (c) 2014 Cisco Systems
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* PDTool project commiters - initial release
*******************************************************************************/
/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.services;

import com.cisco.dvbu.ps.common.exception.CompositeException;

public interface ServerAttributeManager {
	
	/**
	 * Generate Server Attribute XML
	 * @param serverId target server id from servers config xml
	 * @param serverAttributeIds list of server attributes comma separated
	 * @param pathToServerAttributeXML path including name to the server attributes xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void updateServerAttributes(String serverId, String serverAttributeIds, String pathToServerAttributeXML, String pathToServersXML) throws CompositeException;
	

	/**
	 * Generate Server Attribute XML
	 * @param serverId target server id from servers config xml
	 * @param startPath starting path of the server attribute folder e.g /server
	 * @param pathToServerAttributeXML path including name to the server attributes xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @param updateRule the type of rule described by the attribute definition (READ_ONLY, READ_WRITE, NULL to return all rules)
	 * @throws CompositeException
	 */
	public void generateServerAttributesXML(String serverId, String startPath, String pathToServerAttributeXML, String pathToServersXML, String updateRule) throws CompositeException;

	/**
	 * Generate Server Attribute Definitions XML
	 * @param serverId target server id from servers config xml
	 * @param startPath starting path of the server attribute folder e.g /server
	 * @param pathToServerAttributeXML path including name to the server attributes xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @param updateRule the type of rule described by the attribute definition (READ_ONLY, READ_WRITE, NULL to return all rules)
	 * @throws CompositeException
	 */
	public void generateServerAttributeDefinitionsXML(String serverId, String startPath, String pathToServerAttributeXML, String pathToServersXML, String updateRule) throws CompositeException;

}
