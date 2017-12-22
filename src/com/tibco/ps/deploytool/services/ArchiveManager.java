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

public interface ArchiveManager {

	//--Begin::Archive Module-------------------------------------------------------------------------------------------------------
	/**
	 * Import a car file to a Composite server 
	 * @param serverId - target server id from servers config xml
	 * @param archiveIds - list of resource archive Ids (comma separated archive Ids) containing the archive instructions
	 * @param pathToArchiveXML - path to the archive module xml
	 * @param pathToServersXML - path to the server values xml
	 * @throws CompositeException
	 */
	public void pkg_import(String serverId, String archiveIds, String pathToArchiveXML, String pathToServersXML);
	
	/**
	 * Export a car file from a Composite server list of folders
	 * @param serverId - target server id from servers config xml
	 * @param archiveIds - list of resource archive Ids (comma separated archive Ids) containing the archive instructions
	 * @param pathToArchiveXML - path to the archive module xml
	 * @param pathToServersXML - path to the server values xml
	 * @throws CompositeException
	 */
	public void pkg_export(String serverId, String archiveIds, String pathToArchiveXML, String pathToServersXML) throws CompositeException;
	
	/**
	 * Restore a full server backup from a backup car file
	 * @param serverId - target server id from servers config xml
	 * @param archiveIds - list of resource archive Ids (comma separated archive Ids) containing the archive instructions
	 * @param pathToArchiveXML - path to the archive module xml
	 * @param pathToServersXML - path to the server values xml
	 * @throws CompositeException
	 */	
	public void backup_import(String serverId, String archiveIds, String pathToArchiveXML, String pathToServersXML) throws CompositeException;
	
	/**
	 * Create a full server backup of a CIS instance to a car file
	 * @param serverId - target server id from servers config xml
	 * @param archiveIds - list of resource archive Ids (comma separated archive Ids) containing the archive instructions
	 * @param pathToArchiveXML - path to the archive module xml
	 * @param pathToServersXML - path to the server values xml
	 * @throws CompositeException
	 */	
	public void backup_export(String serverId, String archiveIds, String pathToArchiveXML, String pathToServersXML) throws CompositeException;

}
