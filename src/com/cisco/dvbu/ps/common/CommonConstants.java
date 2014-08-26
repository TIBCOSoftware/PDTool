package com.cisco.dvbu.ps.common;

public class CommonConstants {

	// Config Root Property
	public static final String configRootProperty = "com.cisco.dvbu.ps.configroot";

	// Default Property File Name from the JAVA system environment.  This gets set by the ExecutePDTool.bat or ExecutePDToolStudio.bat with a -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE%
	public static final String propertyFile = System.getProperty("CONFIG_PROPERTY_FILE");

	// Default deploy manager name
	public static final String deployManagerName = "deployManager";

	// Default spring configuration bootstrap file
	public static final String springConfigFile = "applicationContextList.xml";

	// Location of java security policy file offset from PDTool home location
	public static final String javaPolicy = "/security/java.policy";
	
	// Set the default VCS Life Cycle Listeners
	public static final String SVNLifecycleListener = "com.cisco.dvbu.cmdline.vcs.spi.svn.SVNLifecycleListener";
	public static final String CVSLifecycleListener = "com.cisco.dvbu.cmdline.vcs.spi.cvs.CVSLifecycleListener";
	public static final String P4LifecycleListener  = "com.cisco.dvbu.cmdline.vcs.spi.p4.P4LifecycleListener";
	public static final String TFSLifecycleListener = "com.cisco.dvbu.cmdline.vcs.spi.tfs.TFSLifecycleListener";
	public static final String GITLifecycleListener = "com.cisco.dvbu.cmdline.vcs.spi.git.GITLifecycleListener";
	// Add a new Life Cycle Listener following this template
	public static final String NEWLifecycleListener = "com.cisco.dvbu.cmdline.vcs.spi.new.NEWLifecycleListener";
	
	// List of constants for encrypting passwords
	public static final String encryptPropertiesList = "VCS_PASSWORD encryptedPassword PASSWORD_STRING SVN_VCS_PASSWORD P4_VCS_PASSWORD CVS_VCS_PASSWORD TFS_VCS_PASSWORD GIT_VCS_PASSWORD CIS_PASSWORD";
	
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
}
