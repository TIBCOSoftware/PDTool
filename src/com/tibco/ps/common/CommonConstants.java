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
package com.tibco.ps.common;

public class CommonConstants {

	// Config Root Property
	public static final String configRootProperty = "com.tibco.ps.configroot";

	// Default Property File Name from the JAVA system environment.  This gets set by the ExecutePDTool.bat or ExecutePDToolStudio.bat with a -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE%
	public static final String propertyFile = System.getProperty("CONFIG_PROPERTY_FILE");

	// Default deploy manager name
	public static final String deployManagerName = "deployManager";

	// Default spring configuration bootstrap file
	public static final String springConfigFile = "applicationContextList.xml";

	// Location of java security policy file offset from PDTool home location
	public static final String javaPolicy = "/security/java.policy";

	// Location of adapter property file offset from PDTool home location
	public static final String adapterPropertyFile = "adapter.properties";

	// Set the default VCS Life Cycle Listeners
	public static final String SVNLifecycleListener = "com.tibco.cmdline.vcs.spi.svn.SVNLifecycleListener";
	public static final String CVSLifecycleListener = "com.tibco.cmdline.vcs.spi.cvs.CVSLifecycleListener";
	public static final String P4LifecycleListener  = "com.tibco.cmdline.vcs.spi.p4.P4LifecycleListener";
	public static final String TFSLifecycleListener = "com.tibco.cmdline.vcs.spi.tfs.TFSLifecycleListener";
	public static final String GITLifecycleListener = "com.tibco.cmdline.vcs.spi.git.GITLifecycleListener";
	public static final String CLCLifecycleListener = "com.tibco.cmdline.vcs.spi.clc.CLCLifecycleListener";
	// Add a new Life Cycle Listener following this template
	public static final String NEWLifecycleListener = "com.tibco.cmdline.vcs.spi.new.NEWLifecycleListener";
	
	// List of constants for encrypting passwords
	public static final String encryptPropertiesList = "VCS_PASSWORD encryptedPassword keystorePassword KEYSTORE_PASSWORD PASSWORD_STRING SVN_VCS_PASSWORD P4_VCS_PASSWORD CVS_VCS_PASSWORD TFS_VCS_PASSWORD GIT_VCS_PASSWORD CLC_VCS_PASSWORD CIS_PASSWORD";
	
	// Common indent 
	public static final String indent = "    ";	
	
	// Encode/Decode CIS paths.  This is an array of {"symbol", "encodedValue"}
	public static final String[][] pathCodes = new String[][] {
        { "$", "_0024"}
        };

	// Maximum windows path length
	public static final int maxWindowsPathLen = 259;
	
	// Default order of precedence for retrieving property files
	public static final String propertyOrderPrecedenceDefault = "DEFAULT: JVM PROPERTY_FILE SYSTEM";
	
	// PDTool-wide string which prepends all errors.
	public static final String applicationErrorPrependMessage = "Application Error::";
	
	// TFS Workspace Messages
	public static final String TFS_MSG_NO_WORKSPACE_MATCHING = "No workspace matching";
}
