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
package com.tibco.ps.deploytool.services;

import com.tibco.ps.common.exception.CompositeException;
import com.compositesw.services.system.util.common.Attribute;

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

	/**
	 * Get the server version
	 * @param serverId target server config name
	 * @param pathToServersXML path to the server values xml
	 * @return String The server version
	 */	
	public String getServerVersion(String serverId, String pathToServersXML) throws CompositeException;

}
