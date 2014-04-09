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
