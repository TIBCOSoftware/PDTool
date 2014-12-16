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
