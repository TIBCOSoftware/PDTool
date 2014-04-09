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
package com.cisco.dvbu.ps.deploytool.dao.wsapi;

import com.compositesw.cmdline.archive.BackupCommand;
import com.compositesw.cmdline.archive.ExportCommand;
import com.compositesw.cmdline.archive.ImportCommand;
import com.compositesw.cmdline.archive.RestoreCommand;
import com.compositesw.cdms.webapi.WebapiException;
import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.LogOutputStream;
import com.cisco.dvbu.ps.common.NoExitSecurityExceptionStatusNonZero;
import com.cisco.dvbu.ps.common.NoExitSecurityExceptionStatusZero;
import com.cisco.dvbu.ps.common.NoExitSecurityManager;
import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.CompositeLogger;
import com.cisco.dvbu.ps.common.util.PropertyManager;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.dao.ArchiveDAO;
import com.cisco.dvbu.ps.deploytool.dao.ServerDAO;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.cisco.dvbu.ps.deploytool.modules.ArchiveType;
import com.cisco.dvbu.ps.deploytool.modules.ArchiveRelocateResourcePathType;
import com.cisco.dvbu.ps.deploytool.modules.ArchiveRebindResourcePathType;
import com.cisco.dvbu.ps.deploytool.modules.ArchiveResourceAttributeModificationType;
import com.cisco.dvbu.ps.deploytool.modules.ArchiveExportSecurityType;
import com.cisco.dvbu.ps.deploytool.modules.ArchiveResourceModificationType;

import java.util.ArrayList;
import java.util.List;
import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Archive module implements for PDT functionality found in Composite command-line utilities:
 * backup_export, backup_import, pkg_export and pkg_import 
 * 
 * @author Composite Software
 * 
 * Modification History
 * =========================
 * 07/06/2012	Alex	Separated parameter parsing into operation-dependent methods
 * 						Added multiple optional parameters
 * 02/14/2014   mtinius Removed the existing PDTool Archive code base.
 * 						Added a security manager around the Composite native archive code to catch System.out.println and System.exit.
 */

public class ArchiveWSDAOImpl implements ArchiveDAO {

	private static Log logger = LogFactory.getLog(ArchiveWSDAOImpl.class);
    private static boolean debug1 = false;
    private static boolean debug2 = false;
    private static boolean debug3 = false;
	private ServerDAO serverDAO = null;
	// Get the configuration property file set in the environment with a default of deploy.properties
	String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");


	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ArchiveDAO#takeArchiveAction(java.lang.String,java.lang.String, com.compositesw.services.system.util.common.AttributeList, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void takeArchiveAction(String actionName, ArchiveType archive, String serverId, String pathToServersXML, String prefix, String propertyFile) throws CompositeException {
		
		// Set the debug options
		setDebug();
		
// Read target server properties from xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ArchiveWSDAOImpl.takeArchiveAction("+actionName+")", logger);
//
// DA@20120610 Comment unnecessary ping - if server is down the backup/restore command will fail as fast as ping
// Ping the Server to make sure it is alive and the values are correct.
//		WsApiHelperObjects.pingServer(targetServer, true);
		
		// Get the offset location of the java.policy file [offset from PDTool home].
		String javaPolicyOffset = CommonConstants.javaPolicy;
		String javaPolicyLocation = CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"PROJECT_HOME_PHYSICAL"), propertyFile, true) + javaPolicyOffset;	

		
		String identifier = "ArchiveWSDAOImpl.takeArchiveAction"; // some unique identifier that characterizes this invocation.
		try {
	
			if(logger.isDebugEnabled() || debug3) {
				CommonUtils.writeOutput(":: executing action: "+actionName,prefix,"-debug3",logger,debug1,debug2,debug3);
				//logger.debug(identifier+":: executing action: "+actionName);
			}

			List<String> argsList = getCommonArchiveParameters(archive,targetServer) ;			
			
			if(actionName.equalsIgnoreCase(ArchiveDAO.action.IMPORT.name())) 
			{
// pkg_import
				List<String> parms = getPackageImportParameters(archive) ;
				argsList.addAll(parms) ;
				String[] args = argsList.toArray(new String[0]) ;
				String maskedArgList = CommonUtils.getArgumentListMasked(argsList);
				if ( logger.isDebugEnabled() || debug3 ) {
					CommonUtils.writeOutput(identifier+":: "+ArchiveDAO.action.IMPORT.name().toString()+" argument list=[" + maskedArgList+"]",prefix,"-debug3",logger,debug1,debug2,debug3);
					//logger.debug(identifier+":: "+ArchiveDAO.action.IMPORT.name().toString()+" argument list=[" + maskedArgList+"]" );
				}
				/*
				 * 2014-02-14 (mtinius): Removed the PDTool Archive capability
				 */
//				ImportCommand.startCommand(".", ".", args);
				/*
				 * 2014-02-14 (mtinius): Added security manager around the Composite native Archive code because
				 * 						 it has System.out.println and System.exit commands.  Need to trap both.
				 */
				// Get the existing security manager
		        SecurityManager sm = System.getSecurityManager();
		        PrintStream originalOut = System.out;
		        PrintStream originalErr = System.err;
				String command = "ImportCommand.startCommand";
		        try {
					// Set the java security policy
					System.getProperties().setProperty("java.security.policy", javaPolicyLocation);  
					
					// Create a new System.out Logger
		            Logger importLogger = Logger.getLogger(ImportCommand.class);
		            System.setOut(new PrintStream(new LogOutputStream(importLogger, Level.INFO)));
		            System.setErr(new PrintStream(new LogOutputStream(importLogger, Level.ERROR)));
					// Create a new security manager
		            System.setSecurityManager(new NoExitSecurityManager());

		            // Invoke the Composite native import command.
		            ImportCommand.startCommand(".", ".", args);
		        }
		        catch (NoExitSecurityExceptionStatusNonZero nesesnz) {
		        	String error = identifier+":: Exited with exception from System.exit(): "+command+"(\".\", \".\", "+maskedArgList+")";
		            logger.error(error);
		            throw new CompositeException(error);
		        }
		        catch (NoExitSecurityExceptionStatusZero nesezero) {
					if ( logger.isDebugEnabled() || debug3 ) {
						CommonUtils.writeOutput(identifier+":: Exited successfully from System.exit(): "+command+"(\".\", \".\", "+maskedArgList+")" ,prefix,"-debug3",logger,debug1,debug2,debug3);
						//logger.debug(identifier+":: Exited successfully from System.exit(): "+command+"(\".\", \".\", "+maskedArgList+")");
					}
		        }
		        finally {
		            System.setSecurityManager(sm);
		            System.setOut(originalOut);
		            System.setErr(originalErr);
		        }

			}
			else if(actionName.equalsIgnoreCase(ArchiveDAO.action.RESTORE.name())) 
			{
// backup_import
				List<String> parms = getBackupImportParameters(archive) ;
				argsList.addAll(parms) ;
				String[] args = argsList.toArray(new String[0]) ;
				String maskedArgList = CommonUtils.getArgumentListMasked(argsList);
				if ( logger.isDebugEnabled() || debug3 ) {
					CommonUtils.writeOutput(identifier+":: "+ArchiveDAO.action.RESTORE.name().toString()+" argument list=[" + maskedArgList+"]" ,prefix,"-debug3",logger,debug1,debug2,debug3);
					//logger.debug(identifier+":: "+ArchiveDAO.action.RESTORE.name().toString()+" argument list=[" + maskedArgList+"]" );
				}
				
				/*
				 * 2014-02-14 (mtinius): Removed the PDTool Archive capability
				 */
//				RestoreCommand.startCommand(".", ".", args) ;
				/*
				 * 2014-02-14 (mtinius): Added security manager around the Composite native Archive code because
				 * 						 it has System.out.println and System.exit commands.  Need to trap both.
				 */
				// Get the existing security manager
		        SecurityManager sm = System.getSecurityManager();
		        PrintStream originalOut = System.out;
		        PrintStream originalErr = System.err;
				String command = "RestoreCommand.startCommand";
		        try {
					// Set the java security policy
					System.getProperties().setProperty("java.security.policy", javaPolicyLocation);  
					
					// Create a new System.out Logger
		            Logger restoreLogger = Logger.getLogger(RestoreCommand.class);
		            System.setOut(new PrintStream(new LogOutputStream(restoreLogger, Level.INFO)));
		            System.setErr(new PrintStream(new LogOutputStream(restoreLogger, Level.ERROR)));
					// Create a new security manager
		            System.setSecurityManager(new NoExitSecurityManager());

		            // Invoke the Composite native restore command.
		            RestoreCommand.startCommand(".", ".", args) ;
		        }
		        catch (NoExitSecurityExceptionStatusNonZero nesesnz) {
		        	String error = identifier+":: Exited with exception from System.exit(): "+command+"(\".\", \".\", "+maskedArgList+")";
		            logger.error(error);
		            throw new CompositeException(error);
		        }
		        catch (NoExitSecurityExceptionStatusZero nesezero) {
					if ( logger.isDebugEnabled() || debug3 ) {
						CommonUtils.writeOutput(identifier+":: Exited successfully from System.exit(): "+command+"(\".\", \".\", "+maskedArgList+")" ,prefix,"-debug3",logger,debug1,debug2,debug3);
						//logger.debug(identifier+":: Exited successfully from System.exit(): "+command+"(\".\", \".\", "+maskedArgList+")");
					}
		        }
		        finally {
		            System.setSecurityManager(sm);
		            System.setOut(originalOut);
		            System.setErr(originalErr);
		        }

			}
			else if(actionName.equalsIgnoreCase(ArchiveDAO.action.EXPORT.name())) 
			{
// pkg_export
				List<String> parms = getPackageExportParameters(archive) ;
				argsList.addAll(parms) ;
				String[] args = argsList.toArray(new String[0]) ;
				String maskedArgList = CommonUtils.getArgumentListMasked(argsList);
				if ( logger.isDebugEnabled() || debug3 ) {
					CommonUtils.writeOutput(identifier+":: "+ArchiveDAO.action.EXPORT.name().toString()+" argument list=[" + maskedArgList+"]" ,prefix,"-debug3",logger,debug1,debug2,debug3);
					//logger.debug(identifier+":: "+ArchiveDAO.action.EXPORT.name().toString()+" argument list=[" + maskedArgList+"]" );
				}
				
				/*
				 * 2014-02-14 (mtinius): Removed the PDTool Archive capability
				 */
//				ExportCommand.startCommand(".", ".", args);
				/*
				 * 2014-02-14 (mtinius): Added security manager around the Composite native Archive code because
				 * 						 it has System.out.println and System.exit commands.  Need to trap both.
				 */			
				// Get the existing security manager
		        SecurityManager sm = System.getSecurityManager();
		        PrintStream originalOut = System.out;
		        PrintStream originalErr = System.err;
				String command = "ExportCommand.startCommand";
		        try {
					// Set the java security policy
					System.getProperties().setProperty("java.security.policy", javaPolicyLocation);  
					
					// Create a new System.out Logger
		            Logger exportLogger = Logger.getLogger(ExportCommand.class);
		            System.setOut(new PrintStream(new LogOutputStream(exportLogger, Level.INFO)));
		            System.setErr(new PrintStream(new LogOutputStream(exportLogger, Level.ERROR)));
					// Create a new security manager
		            System.setSecurityManager(new NoExitSecurityManager());

		            // Invoke the Composite native export command.
		            ExportCommand.startCommand(".", ".", args);
		        }
		        catch (NoExitSecurityExceptionStatusNonZero nesesnz) {
		        	String error = identifier+":: Exited with exception from System.exit(): "+command+"(\".\", \".\", "+maskedArgList+")";
		            logger.error(error);
		            throw new CompositeException(error);
		        }
		        catch (NoExitSecurityExceptionStatusZero nesezero) {
					if ( logger.isDebugEnabled() || debug3 ) {
						CommonUtils.writeOutput(identifier+":: Exited successfully from System.exit(): "+command+"(\".\", \".\", "+maskedArgList+")" ,prefix,"-debug3",logger,debug1,debug2,debug3);
						//logger.debug(identifier+":: Exited successfully from System.exit(): "+command+"(\".\", \".\", "+maskedArgList+")");
					}
		        }
		        finally {
		            System.setSecurityManager(sm);
		            System.setOut(originalOut);
		            System.setErr(originalErr);
		        }

			}
			else if(actionName.equalsIgnoreCase(ArchiveDAO.action.BACKUP.name())) 
			{			
// backup_export
				List<String> parms = getBackupExportParameters(archive) ;
				argsList.addAll(parms) ;
				String[] args = argsList.toArray(new String[0]) ;
				String maskedArgList = CommonUtils.getArgumentListMasked(argsList);
				if ( logger.isDebugEnabled() || debug3 ) {
					CommonUtils.writeOutput(identifier+":: "+ArchiveDAO.action.BACKUP.name().toString()+" argument list=[" + maskedArgList+"]" ,prefix,"-debug3",logger,debug1,debug2,debug3);
					//logger.debug(identifier+":: "+ArchiveDAO.action.BACKUP.name().toString()+" argument list=[" + maskedArgList+"]" );
				}
				
				/*
				 * 2014-02-14 (mtinius): Removed the PDTool Archive capability
				 */
//				BackupCommand.startCommand(".", ".", args);
				/*
				 * 2014-02-14 (mtinius): Added security manager around the Composite native Archive code because
				 * 						 it has System.out.println and System.exit commands.  Need to trap both.
				 */
				// Get the existing security manager
		        SecurityManager sm = System.getSecurityManager();
		        PrintStream originalOut = System.out;
		        PrintStream originalErr = System.err;
				String command = "BackupCommand.startCommand";
		        try {
					// Set the java security policy
					System.getProperties().setProperty("java.security.policy", javaPolicyLocation);  
					
					// Create a new System.out Logger
		            Logger backupLogger = Logger.getLogger(BackupCommand.class);
		            System.setOut(new PrintStream(new LogOutputStream(backupLogger, Level.INFO)));
		            System.setErr(new PrintStream(new LogOutputStream(backupLogger, Level.ERROR)));
					// Create a new security manager
		            System.setSecurityManager(new NoExitSecurityManager());

		            // Invoke the Composite native backup command.
		            BackupCommand.startCommand(".", ".", args);
		        }
		        catch (NoExitSecurityExceptionStatusNonZero nesesnz) {
		        	String error = identifier+":: Exited with exception from System.exit(): "+command+"(\".\", \".\", "+maskedArgList+")";
		            logger.error(error);
		            throw new CompositeException(error);
		        }
		        catch (NoExitSecurityExceptionStatusZero nesezero) {
					if ( logger.isDebugEnabled() || debug3 ) {
						CommonUtils.writeOutput(identifier+":: Exited successfully from System.exit(): "+command+"(\".\", \".\", "+maskedArgList+")" ,prefix,"-debug3",logger,debug1,debug2,debug3);
						//logger.debug(identifier+":: Exited successfully from System.exit(): "+command+"(\".\", \".\", "+maskedArgList+")");
					}
		        }
		        finally {
		            System.setSecurityManager(sm);
		            System.setOut(originalOut);
		            System.setErr(originalErr);
		        }

			}
			
			if ( logger.isDebugEnabled() || debug3 ) {
				CommonUtils.writeOutput(identifier+":: completed " + actionName ,prefix,"-debug3",logger,debug1,debug2,debug3);
				//logger.debug(identifier+":: completed " + actionName );
			}

		} 
		// TO DO: Implement specific catch clauses based on implementation 
		catch (Exception e) 
		{
			// TODO: Be more specific about error messages being returned
			// TODO: null - this is where soap-faults get passed - modify if you return a soap-fault (e.g. e.getFaultInfo())
			if ( e.getCause() != null && e.getCause() instanceof WebapiException ) 
			{
//				CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), actionName, "Archive", identifier, targetServer),e.getCause());
				CompositeLogger.logException(e.getCause(), DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), actionName, "Archive", identifier, targetServer));
			}
			else 
			{
				CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), actionName, "Archive", identifier, targetServer));
			}
			throw new ApplicationException(e.getMessage(), e);
		}

	}
	
	/**
	 * @return serverDAO
	 */
	public ServerDAO getServerDAO() {
		if(this.serverDAO == null){
			this.serverDAO = new ServerWSDAOImpl();
		}
		return serverDAO;
	}


	
/**
 *  Collects pkg_import parameters for importing package from a CAR file
 *	pkg_import -pkgfile <D:/irectory/Path/and/File_Name.car>...
 *	-server <HostName> [-port <PortNumber>] [-encrypt]
 *	-user <UserName> -password <Password> [-domain <Domain>]
 *	[-relocate <OldPath> <NewPath>] ...
 *	[-rebind <OldPath> <NewPath>] ...
 *	[-set <path> <DataType> <Attribute> <Value>] ...
 *	[-optfile <D:/irectory/Path/and/File_Name>]
 *	[-printinfo] [-printroots] [-printusers]
 *	[-printcontents] [-printreferences]
 *	[-includeaccess] [-nocaching]
 *	[-excludejars] [-nosourceinfo] 
 *	[-overwrite] [-overrideLocks] [-messagesonly]
 *	[-verbose] [-quiet]				
 * 	
 * @param archive Archive definition
 * @return list of parameters specified for pkg_export invocation
 */
	private List<String> getPackageImportParameters(ArchiveType archive) {
		
		String prefix = "getPackageImportParameters";

		List<String> argsList = new ArrayList<String>() ;
		if (archive.isIncludeaccess() != null && archive.isIncludeaccess() == true ) {
			argsList.add("-includeaccess");	
		}
	// Caching is imported by default, so the inversed logic here is intentional
		if (archive.isIncludecaching() != null && archive.isIncludecaching() == false ) {
			argsList.add("-nocaching");	
		}
	// JARs are imported by default, so the inversed logic here is intentional
		if (archive.isIncludejars() != null && archive.isIncludejars() == false ) {
			argsList.add("-excludejars");
		}
	// Data source definitions are imported by default, so the inversed logic here is intentional
		if (archive.isIncludesourceinfo() != null && archive.isIncludesourceinfo() == false ) {
			argsList.add("-nosourceinfo");
		}
		if (archive.isMessagesonly() != null && archive.isMessagesonly() == true ) {
			argsList.add("-messagesonly");
		}
		if (archive.isOverridelocks() != null && archive.isOverridelocks() == true ) {
			argsList.add("-overrideLocks");
		}
		if (archive.isOverwrite() != null && archive.isOverwrite() == true ) {
			argsList.add("-overwrite");
		}
	// "prints" disable actual import, and provide corresponding output in logger files 
	// (see log4j.properties for their location)
		if (archive.isPrintinfo() != null && archive.isPrintinfo() == true ) {
			argsList.add("-printinfo");	
		}
		if (archive.isPrintroots() != null && archive.isPrintroots() == true ) {
			argsList.add("-printroots");	
		}
		if (archive.isPrintusers() != null && archive.isPrintusers() == true ) {
			argsList.add("-printusers");	
		}
		if (archive.isPrintcontents() != null && archive.isPrintcontents() == true ) {
			argsList.add("-printcontents");	
		}
		if (archive.isPrintreferences() != null && archive.isPrintreferences() == true ) {
			argsList.add("-printreferences");	
		}
		if ( archive.getResources() != null && archive.getResources().getExportOrRelocateOrRebind() != null ) {
			for ( Object o : archive.getResources().getExportOrRelocateOrRebind() ) {
				if ( o instanceof ArchiveRelocateResourcePathType ) {
					ArchiveRelocateResourcePathType relocate = (ArchiveRelocateResourcePathType)o ;
					argsList.add("-relocate") ;
					argsList.add(CommonUtils.extractVariable(prefix, relocate.getOldPath(), propertyFile, false));
					argsList.add(CommonUtils.extractVariable(prefix, relocate.getNewPath(), propertyFile, false));
				}
				else
				if ( o instanceof ArchiveRebindResourcePathType ) {
					ArchiveRebindResourcePathType rebind = (ArchiveRebindResourcePathType)o ;
					argsList.add("-rebind") ;
					argsList.add(CommonUtils.extractVariable(prefix, rebind.getOldPath(), propertyFile, false));
					argsList.add(CommonUtils.extractVariable(prefix, rebind.getNewPath(), propertyFile, false));
				}
			}
		}
		if ( archive.getSetAttributes() != null ) {
			ArchiveResourceModificationType arm = archive.getSetAttributes() ; 
			for ( ArchiveResourceAttributeModificationType aram : arm.getResourceAttribute()) {
				argsList.add("-set") ;
				argsList.add(CommonUtils.extractVariable(prefix, aram.getResourcePath(), propertyFile, false));
				argsList.add(CommonUtils.extractVariable(prefix, aram.getResourceType(), propertyFile, false));
				argsList.add(CommonUtils.extractVariable(prefix, aram.getAttribute(), propertyFile, false));
				argsList.add(CommonUtils.extractVariable(prefix, aram.getValue(), propertyFile, false));
			}
		}
	// Importing user domain/group/user definitions
		if (archive.getUsers() != null && archive.getUsers().getExportOrImport() != null ) {
			for ( Object o : archive.getUsers().getExportOrImport() ) {
				if ( o instanceof ArchiveExportSecurityType.Export )
					continue ;
				String importStyle = (String)o ;
				if ( "merge".equalsIgnoreCase(importStyle) ) {
					argsList.add("-mergeusers") ;
				}
				else {
					if ( "overwrite".equalsIgnoreCase(importStyle) ) {
						argsList.add("-includeusers") ;
					}
				}
			}
		}
		return argsList ;
	}
/**
 *  Collects pkg_export parameters for exporting package into a CAR file
 *		pkg_export -pkgfile <FileName>
 *		<NamespacePath> [...]
 *		-server <hostname> [-port <port>] [-encrypt]
 *		-user <user> -password <password> [-domain <domain>]
 *		[-pkgname <name>] [-description <text>]
 *		[-optfile <filename>] [...]
 *		[-rebindable <path> <description>] ...
 *		[-includeaccess] [-includecaching]
 *		[-includesourceinfo] [-includejars]
 *		[-includeAllUsers] [-includeUser <domain> <user>] ...
 *		[-includeGroup <domain> <group>] ...
 *		[-includeDomain <domain>] ...
 *		[-includeRequiredUsers] [-includeDependencies]
 *		[-includeStatistics]
 *		[-verbose] [-quiet]
 * 	
 * @param archive Archive definition
 * @return list of parameters specified for pkg_export invocation
 */
	private List<String> getPackageExportParameters(ArchiveType archive) {

		String prefix = "getPackageExportParameters";

		List<String> argsList = new ArrayList<String>() ;
		if (archive.isIncludeDependencies() != null && archive.isIncludeDependencies() == true ) {
			argsList.add("-includeDependencies");	
		}
		if (archive.getDescription() != null && archive.getDescription().trim().length() > 0 ) {
			argsList.add("-description");	
			argsList.add(archive.getDescription().trim());	
		}
		if (archive.isIncludeallusers() != null && archive.isIncludeallusers() == true ) {
			argsList.add("-includeAllUsers");	
		}
		if (archive.isIncluderequiredusers() != null && archive.isIncluderequiredusers() == true ) {
			argsList.add("-includeRequiredUsers");	
		}
		if (archive.isIncludeaccess() != null && archive.isIncludeaccess() == true ) {
			argsList.add("-includeaccess");	
		}
		if (archive.isIncludecaching() != null && archive.isIncludecaching() == true ) {
			argsList.add("-includecaching");	
		}
		if (archive.isIncludejars() != null && archive.isIncludejars() == true ) {
			argsList.add("-includejars");	
		}
		if (archive.isIncludesourceinfo() != null && archive.isIncludesourceinfo() == true ) {
			argsList.add("-includesourceinfo");	
		}
		if (archive.isIncludestatistics() != null && archive.isIncludestatistics() == true ) {
			argsList.add("-includeStatistics");	
		}
		if (archive.getPkgName() != null && archive.getPkgName().trim().length() > 0 ) {
			argsList.add("-pkgname");	
			argsList.add(archive.getPkgName());	
		}
		// process resource lists
		if (archive.getResources() != null) {
			for (Object resourcePath: archive.getResources().getExportOrRelocateOrRebind()  ) {
				if ( resourcePath instanceof String ) {
					String s = (String)resourcePath ;
					if ( s.trim().length() > 0 ) 
						argsList.add(CommonUtils.extractVariable(prefix, s, propertyFile, false));
				}
			}
		}
		
		if ( archive.getUsers() != null ) {
			for ( Object o : archive.getUsers().getExportOrImport() ) {
				if ( o instanceof ArchiveExportSecurityType.Export ) {
					ArchiveExportSecurityType.Export export = (ArchiveExportSecurityType.Export)o ;
					if ( export.getUser() != null && export.getUser().trim().length() > 0 ) {
						argsList.add("-includeUser");	
						argsList.add(CommonUtils.extractVariable(prefix, export.getDomain(), propertyFile, false));
						argsList.add(CommonUtils.extractVariable(prefix, export.getUser().trim(), propertyFile, false));
					}
					else if ( export.getGroup() != null && export.getGroup().trim().length() > 0 ) {
						argsList.add("-includeGroup");	
						argsList.add(CommonUtils.extractVariable(prefix, export.getDomain(), propertyFile, false));
						argsList.add(CommonUtils.extractVariable(prefix, export.getGroup().trim(), propertyFile, false));
					}
					else {
						argsList.add("-includeDomain");	
						argsList.add(CommonUtils.extractVariable(prefix, export.getDomain(), propertyFile, false));
					}
				}
			}
		}
		
		return argsList ;
	}
/**
 *  Collects backup_import parameters for importing full server backup from CAR file
 *	backup_import 
 *		-pkgfile <target/file name>
 *		-user <user name> 
 *		-password <password> 
 *		-server <hostname> 
 *		[-port <port number>] 
 *		[-encrypt]
 *		[-domain <domain>]
 *		[-relocate <oldPath> <newPath>] ...
 *		[-optfile <filename>] ...
 *		[-set <path> <type> <attribute> <value>]
 *		[-printinfo] [-overwrite] [-verbose]
 *
 * 	
 * @param archive Archive definition
 * @return list of parameters specified for backup_export invocation
 */
	private List<String> getBackupImportParameters(ArchiveType archive) {
		String prefix = "getBackupImportParameters";
		List<String> argsList = new ArrayList<String>() ;
		if (archive.isPrintinfo() != null && archive.isPrintinfo() == true ) {
			argsList.add("-printinfo");	
		}
		if (archive.isOverwrite() != null && archive.isOverwrite() == true ) {
			argsList.add("-overwrite");	
		}
		if ( archive.getResources() != null && archive.getResources().getExportOrRelocateOrRebind() != null ) {
			for ( Object o : archive.getResources().getExportOrRelocateOrRebind() ) {
				if ( o instanceof ArchiveRelocateResourcePathType ) {
					ArchiveRelocateResourcePathType relocate = (ArchiveRelocateResourcePathType)o ;
					argsList.add("-relocate") ;
					argsList.add(CommonUtils.extractVariable(prefix, relocate.getOldPath(), propertyFile, false));
					argsList.add(CommonUtils.extractVariable(prefix, relocate.getNewPath(), propertyFile, false));
				}
			}
		}
		if ( archive.getSetAttributes() != null ) {
			ArchiveResourceModificationType arm = archive.getSetAttributes() ; 
			for ( ArchiveResourceAttributeModificationType aram : arm.getResourceAttribute()) {
				argsList.add("-set") ;
				argsList.add(CommonUtils.extractVariable(prefix, aram.getResourcePath(), propertyFile, false));
				argsList.add(CommonUtils.extractVariable(prefix, aram.getResourceType(), propertyFile, false));
				argsList.add(CommonUtils.extractVariable(prefix, aram.getAttribute(), propertyFile, false));
				argsList.add(CommonUtils.extractVariable(prefix, aram.getValue(), propertyFile, false));
			}
		}
		return argsList ;
	}

/**
 *  Collects backup_export parameters for a full server backup into CAR file
 *  	backup_export 
 *		-server <hostname> [-port <port number>] [-encrypt]
 *		-pkgfile <file name> 
 *		-user <user name> -password <password> [-domain <domain>]
 *		[-pkgname <name>] [-description <text>]
 *		[-optfile <file name>] [-excludejars] 
 *		[-includeStatistics] [-verbose]
 * 	
 * @param archive Archive definition
 * @return list of parameters specified for backup_export invocation
 */
	private List<String> getBackupExportParameters(ArchiveType archive) {
		String prefix = "getBackupExportParameters";
		List<String> argsList = new ArrayList<String>() ;
		if (archive.getDescription() != null && archive.getDescription().trim().length() > 0 ) {
			argsList.add("-description");	
			argsList.add(CommonUtils.extractVariable(prefix, archive.getDescription().trim(), propertyFile, false));
		}
	// JARs are exported by default, so the inversed logic here is intentional
		if (archive.isIncludejars() != null && archive.isIncludejars() == false ) {
			argsList.add("-excludejars");
		}
		if (archive.isIncludestatistics() != null && archive.isIncludestatistics() == true ) {
			argsList.add("-includeStatistics");	
		}
		if (archive.getPkgName() !=null && archive.getPkgName().trim().length() > 0 ) {
			argsList.add("-pkgname");	
			argsList.add(CommonUtils.extractVariable(prefix, archive.getPkgName(), propertyFile, false));
		}
		return argsList ;
	}
	
/**
 * Collects parameters common to any archive command
 * 
 * @return list of parameters common to any archive command invocation
 */
	private List<String> getCommonArchiveParameters(ArchiveType archive,CompositeServer targetServer) {
		List<String> argsList = new ArrayList<String>() ;
		argsList.add("-pkgfile");
		argsList.add(archive.getArchiveFileName());
		argsList.add("-user");
		argsList.add(targetServer.getUser());
		if ( targetServer.getDomain() != null && targetServer.getDomain().trim().length() > 0 ) {
			argsList.add("-domain");
			argsList.add(targetServer.getDomain());
		}
		argsList.add("-password");
		argsList.add(targetServer.getPassword());
		argsList.add("-server");
		argsList.add(targetServer.getHostname());
		argsList.add("-port");
		argsList.add(String.valueOf(targetServer.getPort()));	
		
		// 1/29/2014 (mtinius) - PDTool 6.2 only: override archive module setting when useHttps is true
		if (targetServer.isUseHttps()) {
			if ( logger.isInfoEnabled() ) {
				String message = "";
				if (archive.isEncrypt() != null) {
					message = "The servers.xml flag <useHttps>true</useHttps> overrides the original <encrypt>"+archive.isEncrypt()+"</encrypt> and set it to <encrypt>true</encrypt>.";
				} else {
					message = "The servers.xml flag <useHttps>true</useHttps> overrides the original <encrypt/> not set and set it to <encrypt>true</encrypt>.";
				}
				
				logger.info("ArchiveWSDAOImpl.getCommonArchiveParameters: "+message);
			}
			argsList.add("-encrypt");	
		} else {
			if (archive.isEncrypt() != null && archive.isEncrypt() == true)
				argsList.add("-encrypt");	
		}
		return argsList ;
	}
	
	private void setDebug() {
		// Validate the configuration property file exists
		String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");
		if (!PropertyManager.getInstance().doesPropertyFileExist(propertyFile)) {
			throw new ApplicationException("The property file does not exist for CONFIG_PROPERTY_FILE="+propertyFile);
		}
		
		// Set the global suppress and debug properties used throughout this class
		String validOptions = "true,false";
	    // Get the property from the property file
	    String debug = CommonUtils.getFileOrSystemPropertyValue(propertyFile, "DEBUG1");
	    debug1 = false;
	    if (debug != null && validOptions.contains(debug)) {
	    	debug1 = Boolean.valueOf(debug);
	    }
	    debug = CommonUtils.getFileOrSystemPropertyValue(propertyFile, "DEBUG2");
	    debug2 = false;
	    if (debug != null && validOptions.contains(debug)) {
	    	debug2 = Boolean.valueOf(debug);
	    }
	    debug = CommonUtils.getFileOrSystemPropertyValue(propertyFile, "DEBUG3");
	    debug3 = false;
	    if (debug != null && validOptions.contains(debug)) {
	    	debug3 = Boolean.valueOf(debug);
	    }

	}
}
