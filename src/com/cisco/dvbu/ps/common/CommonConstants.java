package com.cisco.dvbu.ps.common;

public class CommonConstants {

	// Config Root Property
	public static final String configRootProperty = "com.cisco.dvbu.ps.configroot";

	// Default Property File Name
	public static final String propertyFile = "deploy.properties";

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
	// Add a new Life Cycle Listener following this template
	public static final String NEWLifecycleListener = "com.cisco.dvbu.cmdline.vcs.spi.tfs.NEWLifecycleListener";
	
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
}
