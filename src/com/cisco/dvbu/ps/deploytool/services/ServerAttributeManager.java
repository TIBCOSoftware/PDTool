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
