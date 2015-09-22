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
package com.cisco.dvbu.ps.deploytool.dao.wsapi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.compositesw.cmdline.archive.ImportCommand;
import com.cisco.dvbu.cmdline.vcs.DiffMerger;
import com.cisco.dvbu.cmdline.vcs.client.ExportCommand;
//import com.compositesw.cmdline.vcs.client.ExportCommand;
import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.LogOutputStream;
import com.cisco.dvbu.ps.common.NoExitSecurityExceptionStatusNonZero;
import com.cisco.dvbu.ps.common.NoExitSecurityExceptionStatusZero;
import com.cisco.dvbu.ps.common.NoExitSecurityManager;
import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.ScriptExecutor;
import com.cisco.dvbu.ps.common.util.wsapi.CisApiFactory;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.dao.ArchiveDAO;
import com.cisco.dvbu.ps.deploytool.dao.VCSDAO;
import com.cisco.dvbu.ps.deploytool.modules.VCSModule;
import com.cisco.dvbu.ps.deploytool.modules.VCSResourceType;
import com.compositesw.services.system.admin.GetChildResourcesSoapFault;
import com.compositesw.services.system.admin.GetResourceSoapFault;
import com.compositesw.services.system.admin.GetUsedResourcesSoapFault;
import com.compositesw.services.system.admin.ResourceExistsSoapFault;
import com.compositesw.services.system.admin.ResourcePortType;
import com.compositesw.services.system.admin.resource.Resource;
import com.compositesw.services.system.admin.resource.ResourceType;
import com.compositesw.services.system.util.common.DetailLevel;


public class VCSWSDAOImpl implements VCSDAO {
	
	private static Log logger = LogFactory.getLog(VCSWSDAOImpl.class);
	private enum ResourceTypeFilterType {INCLUDE, EXCLUDE};

	// -- we need to identify which resources should be 
	//    added to the list of resources to be processed
	//    by VCS
	// -- this is the list of resources that must be added
	private ArrayList<Resource> distinctResourcesToAdd;
	// -- this is the list of resources in the tree represented
	//    by the path of the root vcs path. They don't have to
	//    be added, however, all resources that are linked
	//    must be resolved to what they link to add those
	//    resources must be added to list above
	private ArrayList<Resource> resourcesRecursedFromVcsPath;
	// -- this is the list of resources referenced by resources
	//    in the two previous lists. Any object in this list
	//    not already present in distinctResourcesToAdd or 
	//    recursedResourcesFromVcsPath must be added to 
	//    distinctResourcesToAdd
	private ArrayList<Resource> distinctUsedResources;
	
	public void vcsImportCommand(String prefix, String arguments, String vcsIgnoreMessages, String propertyFile) throws CompositeException {

		String identifier = "VCSWSDAOImpl.vcsImportCommand"; // some unique identifier that characterizes this invocation.
			
		try {				
		    boolean preserveQuotes = false;
		    boolean initArgsList = true;
			List<String> argsList = new ArrayList<String>() ;
			argsList = CommonUtils.parseArguments(argsList, initArgsList, arguments, preserveQuotes, propertyFile);
			String[] args = argsList.toArray(new String[0]) ;
			
			/*
			 * 2014-02-14 (mtinius): Removed the PDTool Archive capability
			 */
//			ImportCommand.startCommand(null, null, args);
			/*
			 * 2014-02-14 (mtinius): Added security manager around the Composite native Archive code because
			 * 						 it has System.out.println and System.exit commands.  Need to trap both.
			 */
			String maskedargsList = CommonUtils.getArgumentListMasked(argsList);
			if ( logger.isDebugEnabled() ) {
				logger.debug(identifier+"(prefix, arguments, vcsIngoreMessages, propertyFile).  prefix="+prefix+"  arguments=[" + maskedargsList+"]"+"  vcsIgnoreMessages="+vcsIgnoreMessages+"  propertyFile="+propertyFile);
			}
			
			// Get the existing security manager
			SecurityManager sm = System.getSecurityManager();
	        PrintStream originalOut = System.out;
	        PrintStream originalErr = System.err;
			String command = "ImportCommand.startCommand";
	        try {
	    		// Get the offset location of the java.policy file [offset from PDTool home].
	    		String javaPolicyOffset = CommonConstants.javaPolicy;
	    		String javaPolicyLocation = CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"PROJECT_HOME_PHYSICAL"), propertyFile, true) + javaPolicyOffset;	
				// Set the java security policy
				System.getProperties().setProperty("java.security.policy", javaPolicyLocation);  

				// Create a new System.out Logger
	            Logger exportLogger = Logger.getLogger(ImportCommand.class);
	            System.setOut(new PrintStream(new LogOutputStream(exportLogger, Level.INFO)));
	            System.setErr(new PrintStream(new LogOutputStream(exportLogger, Level.ERROR)));
				// Create a new security manager
	            System.setSecurityManager(new NoExitSecurityManager());

	            // Invoke the Composite native import command.
	            ImportCommand.startCommand(null, null, args);
	        }
	        catch (NoExitSecurityExceptionStatusNonZero nesesnz) {
	        	String error = identifier+":: Exited with exception from System.exit(): "+command+"(null, null, "+maskedargsList+")";
	            logger.error(error);
	            throw new CompositeException(error);
	        }
	        catch (NoExitSecurityExceptionStatusZero nesezero) {
				if ( logger.isDebugEnabled() ) {
					logger.debug(identifier+":: Exited successfully from System.exit(): "+command+"(null, null, "+maskedargsList+")");
				}
	        }
	        finally {
	            System.setSecurityManager(sm);
	            System.setOut(originalOut);
	            System.setErr(originalErr);
	        }

			
		} catch (Exception e) {
			if (resolveExecCommandLineError(prefix, e.getMessage().toString(), vcsIgnoreMessages)) {
				ApplicationException applicationException = new ApplicationException("ImportCommand execution returned an error="+e.getMessage().toString());
				if (logger.isErrorEnabled()) {
					logger.error(applicationException);
				}
			    throw applicationException;					
			}
		}
	}

	public void vcsExportCommand(String prefix, String arguments, String vcsIgnoreMessages, String propertyFile) throws CompositeException {
		
		String identifier = "VCSWSDAOImpl.vcsExportCommand"; // some unique identifier that characterizes this invocation.

		try {
		    boolean preserveQuotes = false;
		    boolean initArgsList = true;
			List<String> argsList = new ArrayList<String>() ;
			argsList = CommonUtils.parseArguments(argsList, initArgsList, arguments, preserveQuotes, propertyFile);
			String[] args = argsList.toArray(new String[0]) ;

			/*
			 * 2014-02-14 (mtinius): Removed the PDTool Archive capability
			 */
//			ExportCommand.startCommand(null, null, args);
			/*
			 * 2014-02-14 (mtinius): Added security manager around the Composite native Archive code because
			 * 						 it has System.out.println and System.exit commands.  Need to trap both.
			 */
			String maskedargsList = CommonUtils.getArgumentListMasked(argsList);
			if ( logger.isDebugEnabled() ) {
				logger.debug(identifier+"(prefix, arguments, vcsIgnoreMessages, propertyFile).  prefix="+prefix+"  arguments=[" + maskedargsList+"]"+"  vcsIgnoreMessages="+vcsIgnoreMessages+"  propertyFile="+propertyFile);
			}
			
			// Get the existing security manager
	        SecurityManager sm = System.getSecurityManager();
	        PrintStream originalOut = System.out;
	        PrintStream originalErr = System.err;
			String command = "ExportCommand.startCommand";
	        try {
	    		// Get the offset location of the java.policy file [offset from PDTool home].
	    		String javaPolicyOffset = CommonConstants.javaPolicy;
	    		String javaPolicyLocation = CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"PROJECT_HOME_PHYSICAL"), propertyFile, true) + javaPolicyOffset;	
				// Set the java security policy
				System.getProperties().setProperty("java.security.policy", javaPolicyLocation);  

				// Create a new System.out Logger
				Logger exportLogger = Logger.getLogger(ExportCommand.class);
	            System.setOut(new PrintStream(new LogOutputStream(exportLogger, Level.INFO)));
	            System.setErr(new PrintStream(new LogOutputStream(exportLogger, Level.ERROR)));
				// Create a new security manager
	            System.setSecurityManager(new NoExitSecurityManager());

	            // Invoke the Composite native export command.
	            ExportCommand.startCommand(null, null, args);
	        }
	        catch (NoExitSecurityExceptionStatusNonZero nesesnz) {
	        	String error = identifier+":: Exited with exception from System.exit(): "+command+"(null, null, "+maskedargsList+")";
	            logger.error(error);
	            throw new CompositeException(error);
	        }
	        catch (NoExitSecurityExceptionStatusZero nesezero) {
				if ( logger.isDebugEnabled() ) {
					logger.debug(identifier+":: Exited successfully from System.exit(): "+command+"(null, null, "+maskedargsList+")");
				}
	        }
	        finally {
	            System.setSecurityManager(sm);
	            System.setOut(originalOut);
	            System.setErr(originalErr);
	        }

		} catch (Exception e) {
			if (resolveExecCommandLineError(prefix, e.getMessage().toString(), vcsIgnoreMessages)) {
			    ApplicationException applicationException = new ApplicationException("ExportCommand execution returned an error="+e.getMessage().toString());
				if (logger.isErrorEnabled()) {
					logger.error(applicationException);
				}
			    throw applicationException;					
			}
		}
	}

	public void vcsDiffMergerCommand(String prefix, String arguments, String vcsIgnoreMessages, String propertyFile) throws CompositeException {

		String identifier = "VCSWSDAOImpl.vcsDiffMergerCommand"; // some unique identifier that characterizes this invocation.

		try {
		    boolean preserveQuotes = false;
		    boolean initArgsList = true;
			List<String> argsList = new ArrayList<String>() ;
			argsList = CommonUtils.parseArguments(argsList, initArgsList, arguments, preserveQuotes, propertyFile);

			String[] args = argsList.toArray(new String[0]) ;
			
			/*
			 * 2014-06-30 (mtinius): Removed the PDTool Diffmerger capability
			 */
//			DiffMerger.startCommand(null, null, args);
			/*
			 * 2014-06-30 (mtinius): Added security manager around the Composite native Diffmerger code because
			 * 						 it has System.out.println and System.exit commands.  Need to trap both.
			 */
			String maskedargsList = CommonUtils.getArgumentListMasked(argsList);
			if ( logger.isDebugEnabled() ) {
				logger.debug(identifier+"(prefix, arguments, vcsIgnoreMessages, propertyFile).  prefix="+prefix+"  arguments=[" + maskedargsList+"]"+"  vcsIgnoreMessages="+vcsIgnoreMessages+"  propertyFile="+propertyFile);
			}
			
			// Get the existing security manager
	        SecurityManager sm = System.getSecurityManager();
	        PrintStream originalOut = System.out;
	        PrintStream originalErr = System.err;
			String command = "DiffMerger.startCommand";
	        try {
	    		// Get the offset location of the java.policy file [offset from PDTool home].
	    		String javaPolicyOffset = CommonConstants.javaPolicy;
	    		String javaPolicyLocation = CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"PROJECT_HOME_PHYSICAL"), propertyFile, true) + javaPolicyOffset;	
				// Set the java security policy
				System.getProperties().setProperty("java.security.policy", javaPolicyLocation);  

				// Create a new System.out Logger
				Logger exportLogger = Logger.getLogger(DiffMerger.class);
	            System.setOut(new PrintStream(new LogOutputStream(exportLogger, Level.INFO)));
	            System.setErr(new PrintStream(new LogOutputStream(exportLogger, Level.ERROR)));
				// Create a new security manager
	            System.setSecurityManager(new NoExitSecurityManager());

	            // Invoke the Composite native DiffMerger command.
	            DiffMerger.startCommand(null, null, args);
	        }
	        catch (NoExitSecurityExceptionStatusNonZero nesesnz) {
	        	String error = identifier+":: Exited with exception from System.exit(): "+command+"(null, null, "+maskedargsList+")";
	            logger.error(error);
	            throw new CompositeException(error);
	        }
	        catch (NoExitSecurityExceptionStatusZero nesezero) {
				if ( logger.isDebugEnabled() ) {
					logger.debug(identifier+":: Exited successfully from System.exit(): "+command+"(null, null, "+maskedargsList+")");
				}
	        }
	        finally {
	            System.setSecurityManager(sm);
	            System.setOut(originalOut);
	            System.setErr(originalErr);
	        }
	        
		} catch (Exception e) {
			if (resolveExecCommandLineError(prefix, e.getMessage().toString(), vcsIgnoreMessages)) {
				ApplicationException applicationException = new ApplicationException("DiffMerger execution returned an error="+e.getMessage().toString());
				if (logger.isErrorEnabled()) {
					logger.error(applicationException);
				}
			    throw applicationException;					
			}
		}
	}

	// Execute the Command Line for VCS
	public StringBuilder execCommandLineVCS(String prefix, String execFromDir, String command, List<String> args, List<String> envList, String vcsIgnoreMessages) throws CompositeException {
		
		String identifier = "VCSWSDAOImpl.execCommandLineVCS"; // some unique identifier that characterizes this invocation.
		StringBuilder stdout = new StringBuilder();
		// For debugging
		if ( logger.isDebugEnabled() ) {
			String[] argsString = args.toArray(new String[0]);
			String maskedArgsList = "";
			for (String arg:argsString) {
				maskedArgsList = maskedArgsList + " " + CommonUtils.maskCommand(arg);
			}
			String[] envString = envList.toArray(new String[0]);
			String maskedEnvList = "";
			for (String env:envString) {
				maskedEnvList = maskedEnvList + " " + CommonUtils.maskCommand(env);
			}

			logger.debug(identifier+"(prefix, execFromDir, command, args, envList, vcsIgnoreMessages).  prefix="+prefix+"  execFromDir="+execFromDir+"  args=[" + maskedArgsList+"]  envList=["+envList+"]  vcsIgnoreMessages="+vcsIgnoreMessages);
		}
		
		if (prefix == null) {
			prefix = "";
		} else {
			prefix = prefix+"::";
		}

		try {
			// Invoke the ScriptExecute command
			ScriptExecutor se = new ScriptExecutor(execFromDir, args, envList);
			int result = se.executeCommand(CommonUtils.getUniqueFilename("error", "txt"));

			if(result > 0){
				StringBuilder stderr = se.getStandardErrorFromCommand();
				if (resolveExecCommandLineError(prefix, stderr.toString(), vcsIgnoreMessages)) {
					ApplicationException applicationException = new ApplicationException(CommonUtils.maskCommand(command)+" Execution Returned an Error="+stderr.toString());
				    throw applicationException;					
				}
			}else{
			    stdout = se.getStandardOutputFromCommand();
			    if (logger.isDebugEnabled()) {
			    	logger.debug(identifier+": "+prefix+CommonUtils.maskCommand(command)+" executed successfully");
			    }
			}
		} catch (CompositeException e) {
			throw new ApplicationException(e);
		}
		return stdout;
	}	
	
	/*
	 * (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.VCSDAO#generateVCSXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateVCSXML(String serverId, String startPath, String pathToVCSXML, String pathToServersXML) throws CompositeException {
		
		// Set the Module Action Objective for the VCS Module
		System.setProperty("MODULE_ACTION_OBJECTIVE", startPath);

		// For debugging
		if(logger.isDebugEnabled()) {
			logger.debug("VCSWSDAOImpl.generateVCSXML(serverId , startPath, pathToVCSXML, pathToServersXML).  serverId="+serverId+"  startPath="+startPath+"  pathToVCSXML="+pathToVCSXML+"  pathToServersXML="+pathToServersXML);
		}
		// -- these are class level to avoid excessive object creation
		distinctResourcesToAdd = new ArrayList<Resource>();
		resourcesRecursedFromVcsPath = new ArrayList<Resource>();
		distinctUsedResources = new ArrayList<Resource>();
		
		ArrayList<Resource> startResourceList;
		
		Resource startResource = null;
		
		VCSModule vcsModule = new VCSModule();
		VCSResourceType vcsResource = new VCSResourceType();
		
		// -- get server and port
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);
		
		//System.out.println("Starting with: " + startPath);
		
		// -- get parent resource
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("VCSWSDAOImpl.generateVCSXML().  Invoking port.getResource(\""+startPath+"\", null, \"SIMPLE)\".getResource().");
			}
			
			startResourceList = (ArrayList<Resource>) port.getResource(startPath, null, DetailLevel.SIMPLE).getResource();
			
			if(logger.isDebugEnabled()) {
				logger.debug("VCSWSDAOImpl.generateVCSXML().  Success: port.getResource().");
			}
		} catch (GetResourceSoapFault e) {
			throw new ApplicationException("Error getting resource: " + e.getMessage());
		}
		
		// -- validate return result - however, should never be null or empty as
		//    conditions that would result in that would also result in exception above
		if (startResourceList == null) {
			throw new ApplicationException("The parent resource path provided is not valid.");	
		}
		
		// -- this is the object representation of startPath 
		startResource = startResourceList.get(0);
		
		// -- perhaps check for something silly like this - system database or web service
		//if (startResource.getPath().startsWith("/services/databases/system")) {
		//	throw new ApplicationException("Can not include system database.");	
		//}		
		
		// -- is this a container or a resource? Is it from /services or from /shared, /home?
		ResourceType startResourceType = startResource.getType();
		
		// -- make sure main object of interest is included
		distinctResourcesToAdd.add(startResource);
		if (startResourceType.equals(ResourceType.CONTAINER) ||  
			(startResourceType.equals(ResourceType.DATA_SOURCE) &&
					startPath.startsWith("/services/")	)) {
			// -- we need to recurse and find all child objects. This
			//    populates resourcesRecursedFromVcsPath
    		getChildResourcesRecursive(startResource, port);
					
    		// -- not a container. Is it a link (/servers/...) or simply
    		//    a resource under /shared or /home?
		} else {
				
				// -- if it's not a container, simply add.
				//    in this case, user is checking in resource,
				//    and all we need to do is find used resources
				resourcesRecursedFromVcsPath.add(startResource);
		
		}
		
		// -- OK, we now have all resources - now what resources to those resources depend
		//    on, or use. For a large project, this could take a few minutes
		for (Resource resourceToRecurse: resourcesRecursedFromVcsPath) {
			// -- this populates distinctUsedResources  
			getUsedResourcesRecursive(resourceToRecurse, port);
		}
		
		// -- when a sub-type shows you have a child of a data source,
		//    does it make more sense simply to work up to chain
		//    of parent objects to find data source and just add it?
		for (Resource potentialResourceToAdd: distinctUsedResources) {
			// -- may already be in folder hierarchy of startPath
			if (!distinctResourcesToAdd.contains(potentialResourceToAdd) &&
					!resourcesRecursedFromVcsPath.contains(potentialResourceToAdd)) {
				distinctResourcesToAdd.add(potentialResourceToAdd);
			}
		}		
	
		NumberFormat formatter = new DecimalFormat("00000");
		for (int i = 0; i < distinctResourcesToAdd.size(); i++) {
			
			Resource resourceToAdd = distinctResourcesToAdd.get(i);
			
			vcsResource = new VCSResourceType();
			vcsResource.setId("checkin_" + formatter.format(i + 1));
			vcsResource.setMessage("Entry generated by Composite PS Promotion and Deployment Tool");
			vcsResource.setResourcePath(resourceToAdd.getPath());
			vcsResource.setResourceType(resourceToAdd.getType().name());
			vcsResource.setRevision("HEAD");
			
			vcsModule.getVcsResource().add(vcsResource);
		}
		
		JAXBContext jaxbContext;
		Marshaller marshaller;
		try {
			jaxbContext = JAXBContext.newInstance("com.cisco.dvbu.ps.deploytool.modules");
			marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					   new Boolean(true));
			marshaller.marshal(vcsModule,
					   new FileOutputStream(pathToVCSXML));
			//System.out.println("Wrote file: " + pathToVCSXML);
		} catch (JAXBException e) {
			throw new ApplicationException("Error retrieving XML: " + e.getMessage());
		} catch (FileNotFoundException e) {
			throw new ApplicationException("Unable to create XML file: " + e.getMessage());
		}

	}	
	
	// -- recurse to find all resources used by resource passed in
    private void getUsedResourcesRecursive(Resource parentResource, ResourcePortType port) {

    	ArrayList<Resource> usedResources = null;
		try {
			String path = parentResource.getPath();
			ResourceType type = parentResource.getType();
			if (port.resourceExists(path, type, null)) {
			usedResources = (ArrayList<Resource>) port.getUsedResources(
					path, type, 
					DetailLevel.SIMPLE).getResource();
			} else {
				// -- log and return
				logger.info("! Important - resource \"" + path + "\" does not exist in getUsedResources in generateVCSXML.");
				return;
			}
		} catch (GetUsedResourcesSoapFault e) {
			throw new ApplicationException("Error getting dependent resources: " + e.getMessage());
		} catch (ResourceExistsSoapFault e) {
			throw new ApplicationException("Error determining if resource exists: " + e.getMessage());
		}
    	
        for(Resource usedResource : usedResources) {
        	// -- we could do a little simple checking to eliminate
        	//    status and tracking tables that do not match
        	//    these names, as well as to detect cached views
        	//    and add the view that is cached instead
        	if (!distinctUsedResources.contains(usedResource) && 
        			!usedResource.getPath().endsWith("cache_tracking") &&
        			!usedResource.getPath().endsWith("cache_status") &&
        			// -- recursing into system procedures results in infinite loop
        			!usedResource.getPath().startsWith("/lib/")) {
        		distinctUsedResources.add(usedResource);
        		//System.out.println("Adding used resource: " + usedResource.getPath());
        		// -- for each used resource, I must get the resources it uses
        		getUsedResourcesRecursive(usedResource, port);
        	}
        }
        
    }	

	// -- recurse to find all non-container resources in folder hiearchy
    //    starting at path of parentResource
    private void getChildResourcesRecursive(Resource parentResource, 
    		ResourcePortType port //, 
    		//ArrayList<ResourceType> resourceTypesFilter, 
    		//ResourceTypeFilterType resourceTypeFilterType
    		) {
        
    	// -- possibly support include/exclude filters in future 
    	//if ((resourceTypeFilterType != null && resourceTypesFilter == null) ||
    	//	(resourceTypeFilterType == null && resourceTypesFilter != null)) {
    	//	throw new ApplicationException("If the ResourceTypeFilter provided " +
    	//			" then the ResourceTypeFilterType must be provided as well.");
    	//}
    	
    	// -- this is only relevant for containers
    	if (!	
    		(	
    		parentResource.getType().equals(ResourceType.CONTAINER) || 
    		(parentResource.getType().equals(ResourceType.DATA_SOURCE) &&
    		parentResource.getPath().startsWith("/services/")))
    		)
    		{
    		return;
    	}
    	
    	// -- get this container's child resources
    	ArrayList<Resource> childResources = null;
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("VCSWSDAOImpl.getChildResourcesRecursive().  Invoking port.getChildResources(\""+parentResource.getPath()+"\", \""+parentResource.getType()+"\", \"SIMPLE\").getResource().");
			}
			
			// -- this is the list of child resources
			childResources = (ArrayList<Resource>) port.getChildResources(parentResource.getPath(), parentResource.getType(), DetailLevel.SIMPLE).getResource();
			
			if(logger.isDebugEnabled()) {
				logger.debug("VCSWSDAOImpl.getChildResourcesRecursive().  Success: port.getChildResources().");
			}
		} catch (GetChildResourcesSoapFault e) {
			throw new ApplicationException("Error getting child resources: " + e.getMessage());
		}
    	
        for(Resource childResource : childResources) {
        	// -- we could do a little simple checking to eliminate
        	//    status and tracking tables that do not match
        	//    these names, as well as to detect cached views
        	//    and add the view that is cached instead
        	String path = childResource.getPath();
        	ResourceType type = childResource.getType();
        	
        	// -- contains test should not be necessary
        	if (!resourcesRecursedFromVcsPath.contains(childResource) && 
        			// -- anything that's not a container - that's
        			//    contained - 
        			!type.equals(ResourceType.CONTAINER) &&
        			// -- recursing into system procedures results in infinite loop
        			!path.startsWith("/lib/")) {
        		
        		// -- do we want to include this resource based on resource type?
        		//boolean addRes = false;
        		
        		//if (resourceTypesFilter != null) {
        			
        		//	if (resourceTypeFilterType == ResourceTypeFilterType.INCLUDE && 
        		//			resourceTypesFilter.contains(type)) {
        		//		addRes = true;
        		//	} else if (!resourceTypesFilter.contains(type)) {
        		//		addRes = true;
        		//	}
        			
        		//}

        		//if (addRes) {
        			//System.out.println("Adding child resource: " + childResource.getPath());
        			resourcesRecursedFromVcsPath.add(childResource);
        		//}
        	
        	} else if (type.equals(ResourceType.CONTAINER))	{
        		// -- for each dependent resource, I must get its dependencies
        		getChildResourcesRecursive(childResource, port //, 
        				//resourceTypesFilter, resourceTypeFilterType
        				);
        		
        	}
        }
    
    }	

    
    // Resolve the command line error message - if it is in the list of ignore messages then don't throw the original error
	private static boolean resolveExecCommandLineError(String prefix, String error, String vcsIgnoreMessages) {
		boolean throwOriginalError = true;
		
		if (prefix == null) {
			prefix = "";
		} else {
			prefix = prefix+"::";
		}

		StringTokenizer st = new StringTokenizer(vcsIgnoreMessages,",");
		while(st.hasMoreTokens()){
			String message = st.nextToken().trim();
			if (error.toLowerCase().contains(message.toLowerCase())) {
				throwOriginalError = false;
				if (logger.isErrorEnabled()) {
					logger.info(prefix+"Warning::Error message ignored.  Error Message matches VCS_IGNORE_MESSAGES="+message);
				}
			}
		}
		return throwOriginalError;
	}


}
