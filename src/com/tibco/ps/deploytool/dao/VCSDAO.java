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

import java.util.List;

import com.tibco.ps.common.exception.CompositeException;

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
	public StringBuilder execCommandLineVCS(String prefix, String execFromDir, String command, List<String> args, List<String> envList, String vcsIgnoreMessages) throws CompositeException;
	
	/**
	 * @param serverId target server id from servers config xml
	 * @param startPath starting path of the resource e.g /shared
	 * @param pathToDataSourceXML path including name to the VCS source xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void generateVCSXML(String serverId, String startPath, String pathToVCSXML, String pathToServersXML) throws CompositeException;	
	

}
