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
import com.cisco.dvbu.ps.deploytool.modules.ArchiveType;

public interface ArchiveDAO {

	public static enum action {IMPORT,EXPORT,BACKUP,RESTORE};

	/**
	 * Take Archive Action method passes in the action and archive operation attributes along with information to identify the target server
	 * @param actionName - the action to perform
	 * @param archiveAttributeList - an XML JAXB structure representing an archive structure
	 * @param serverId - target server id from servers config xml
	 * @param pathToServersXML - path to the server values xml
	 * @param prefix - the prefix is a message modifier such as the invoking method name that gets prefixed to log messages
	 * @param propertyFile - property file that contains the configuration name value pairs (e.g. deploy.properties)
	 */
	public void takeArchiveAction(String actionName, ArchiveType archive, String serverId, String pathToServersXML, String prefix, String propertyFile) throws CompositeException;


}
