/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
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
