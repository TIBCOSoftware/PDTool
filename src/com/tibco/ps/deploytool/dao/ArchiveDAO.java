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
package com.tibco.ps.deploytool.dao;

import com.tibco.ps.common.exception.CompositeException;
import com.tibco.ps.deploytool.modules.ArchiveType;

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
