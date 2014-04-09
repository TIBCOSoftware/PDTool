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
package com.cisco.dvbu.ps.deploytool.dao;

import java.util.List;

import com.cisco.dvbu.ps.common.exception.CompositeException;

public interface VCSDAO {

	/**
	 * Perform an Archive Import
	 * @param prefix - the prefix is a message modifier such as the invoking method name that gets prefixed to log messages
	 * @param arguments - space separate list of arguments for a command execution
	 * @param vcsIgnoreMessages - comma separate list of error messages to ignore upon command execution
	 * @param propertyFile - property file that contains the configuration name value pairs (e.g. deploy.properties)
	 * @throws CompositeException
	 */
	public void vcsImportCommand(String prefix, String arguments, String vcsIgnoreMessages, String propertyFile) throws CompositeException;
	
	/**
	 * Perform an Archive Export
	 * @param prefix - the prefix is a message modifier such as the invoking method name that gets prefixed to log messages
	 * @param arguments - space separate list of arguments for a command execution
	 * @param vcsIgnoreMessages - comma separate list of error messages to ignore upon command execution
	 * @param propertyFile - property file that contains the configuration name value pairs (e.g. deploy.properties)
	 * @throws CompositeException
	 */
	public void vcsExportCommand(String prefix, String arguments, String vcsIgnoreMessages, String propertyFile) throws CompositeException;

	/**
	 * Perform a VCS Differ Merger
	 * @param prefix - the prefix is a message modifier such as the invoking method name that gets prefixed to log messages
	 * @param arguments - space separate list of arguments for a command execution
	 * @param vcsIgnoreMessages - comma separate list of error messages to ignore upon command execution
	 * @param propertyFile - property file that contains the configuration name value pairs (e.g. deploy.properties)
	 * @throws CompositeException
	 */
	public void vcsDiffMergerCommand(String prefix, String arguments, String vcsIgnoreMessages, String propertyFile) throws CompositeException;

	/**
	 * Execute a VCS command line
	 * @param prefix - the prefix is a message modifier such as the invoking method name that gets prefixed to log messages
	 * @param execFromDir - execute the command from this directory
	 * @param command - command to execute e.g. svn,p4,cvs
	 * @param args - argument list
	 * @param envList - environment variable list
	 * @param vcsIgnoreMessages - comma separate list of error messages to ignore upon command execution
	 * @throws CompositeException
	 */
	public void execCommandLineVCS(String prefix, String execFromDir, String command, List<String> args, List<String> envList, String vcsIgnoreMessages) throws CompositeException;
	
	/**
	 * @param serverId target server id from servers config xml
	 * @param startPath starting path of the resource e.g /shared
	 * @param pathToDataSourceXML path including name to the VCS source xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void generateVCSXML(String serverId, String startPath, String pathToVCSXML, String pathToServersXML) throws CompositeException;	
	

}
