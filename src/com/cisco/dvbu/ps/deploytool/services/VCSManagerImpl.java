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
package com.cisco.dvbu.ps.deploytool.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;

import com.cisco.dvbu.cmdline.vcs.spi.LifecycleListener;
import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.exception.ValidationException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.PropertyManager;
import com.cisco.dvbu.ps.common.util.Sleep;
import com.cisco.dvbu.ps.common.util.XMLUtils;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.dao.VCSDAO;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.VCSWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.compositesw.common.vcs.primitives.ResourceNameCodec;
import com.compositesw.services.system.admin.resource.Resource;
import com.compositesw.services.system.admin.resource.ResourceList;
import com.compositesw.services.system.util.common.DetailLevel;
import com.cisco.dvbu.ps.deploytool.modules.VCSConnectionEnvNameValuePairType;
import com.cisco.dvbu.ps.deploytool.modules.VCSConnectionType;
import com.cisco.dvbu.ps.deploytool.modules.VCSModule;
import com.cisco.dvbu.ps.deploytool.modules.VCSResourceType;

public class VCSManagerImpl implements VCSManager {

    private VCSDAO vcsDAO = null;
	private ResourceManager resourceManager = null;
      
	private static Log logger = LogFactory.getLog(VCSManagerImpl.class);
    private static int milliSeconds = 5000;

    private static String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");
    private static String suppress = "";
    private static boolean debug1 = false;
    private static boolean debug2 = false;
    private static boolean debug3 = false;
    private static boolean diffmergerVerbose = true;
    private static boolean diffmergerVerboseChanges = false;
    private static boolean vcsMultiUserTopology = false;
    private static boolean vcsMultiUserDisableCheckout = false;  
    private static boolean pdToolStudio = false;
    private static boolean vcsV2Method = false;
    private static String  vcsConnId = null;
    private static boolean vcsStudioInitializeBaseFolderCheckin = false;
    // VCS Resource Type variables
    // 2012-10-29 mtinius: added container,data_source,relationship,model,policy to valid types
	private static String validResourceTypes="container,FOLDER,data_source,definitions,link,procedure,table,tree,trigger,relationship,model,policy";
    private static String externalVcsResourceTypeFolderList = "";
    private static String externalVcsResourceTypeDataSourceList = "";
    private static String externalVcsResourceTypeDefinitionsList = "";
    private static String externalVcsResourceTypeLinkList = "";
    private static String externalVcsResourceTypeProcedureList = "";
    private static String externalVcsResourceTypeTableList = "";
    private static String externalVcsResourceTypeTreeList = "";
    private static String externalVcsResourceTypeTriggerList = "";
    private static String externalVcsResourceTypeRelationshipList = "";
    private static String externalVcsResourceTypeModelList = "";
    private static String externalVcsResourceTypePolicyList = "";
 
/******************************************************************************************
 *  PUBLIC INTERFACES
 *  
 *  PROVIDE INTEGRATION FOR PD TOOL SCRIPTS
 *  
 *  These methods provide the public interface for PDTool.
 * 
 *******************************************************************************************/

    /* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsInitWorkspace(java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsInitWorkspace(String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsInitWorkspace()");
		}
		try {       
			// Set the global suppress and debug flags for this class based on properties found in the property file
			setGlobalProperties();
			if (!vcsV2Method)
				vcsConnId = null;

			vcsInitWorkspaceCommon(vcsUser, vcsPassword);
			
		} catch (CompositeException e) {
			logger.error("Initialize VCS Workspace Failed. Error: ",e);
			throw new CompositeException(e.getMessage(),e);
		}
	}

    /* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsInitWorkspace2(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsInitWorkspace2(String vcsConnectionId, String pathToVcsXML, String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsInitWorkspace2() with following params "+" vcsConnectionId: "+vcsConnectionId+", pathToVcsXML: "+pathToVcsXML);
		}
		try {  
			// Set the VCS Module XML Connection Properties in the JVM Environment
			setVCSConnectionProperties(vcsConnectionId, pathToVcsXML);
			vcsConnId = vcsConnectionId;

			// Invoke the command
			vcsInitWorkspace(vcsUser, vcsPassword);
		} catch (CompositeException e) {
			logger.error("Initialize VCS Workspace Failed. Error: ",e);
			throw new CompositeException(e.getMessage(),e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.VCSManager#generateVCSXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateVCSXML(String serverId, String startPath, String pathToVcsXML, String pathToServersXML) throws CompositeException {
		if (!vcsV2Method)
			vcsConnId = null;

		// Set the Module Action Objective
		String s1 = (startPath == null) ? "no_startPath" : "Path="+startPath;
		System.setProperty("MODULE_ACTION_OBJECTIVE", "GENERATE : "+s1);

		getVCSDAO().generateVCSXML(serverId, startPath, pathToVcsXML, pathToServersXML);		
	}		
	
	/*
	 * (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.VCSManager#generateVCSXML2(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateVCSXML2(String serverId, String vcsConnectionId, String startPath, String pathToVcsXML, String pathToServersXML) throws CompositeException {
		// Set the VCS Module XML Connection Properties in the JVM Environment
		setVCSConnectionProperties(vcsConnectionId, pathToVcsXML);
		vcsConnId = vcsConnectionId;

		// Set the Module Action Objective
		String s1 = (startPath == null) ? "no_startPath" : "Path="+startPath;
		System.setProperty("MODULE_ACTION_OBJECTIVE", "GENERATE : "+s1);

		// Invoke the command
		getVCSDAO().generateVCSXML(serverId, startPath, pathToVcsXML, pathToServersXML);		
	}		

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsCheckout(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckout(String serverId, String vcsResourcePath, String vcsResourceType, String vcsRevision, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		if (!vcsV2Method)
			vcsConnId = null;

		vcsCheckoutImpl(serverId, vcsResourcePath, vcsResourceType, null, vcsRevision, pathToServersXML, vcsUser, vcsPassword);
	}

	/* 
	 * This function is overloaded and supports vcs checkout using labels (Perforce only)
	 * 
	 * (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsCheckout(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckout(String serverId, String vcsResourcePath, String vcsResourceType, String vcsLabel, String vcsRevision, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		if (!vcsV2Method)
			vcsConnId = null;

		vcsCheckoutImpl(serverId, vcsResourcePath, vcsResourceType, vcsLabel, vcsRevision, pathToServersXML, vcsUser, vcsPassword);
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsCheckout(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	private void vcsCheckoutImpl(String serverId, String vcsResourcePath, String vcsResourceType, String vcsLabel, String vcsRevision, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsCheckoutImpl() with following params "+" serverId: "+serverId+", vcsResourcePath: "+vcsResourcePath+", vcsResourceType: ,"+vcsResourceType+", vcsLabel: "+vcsLabel+", vcsRevision: "+vcsRevision+", pathToServersXML: "+pathToServersXML);
		}
		try {
			String prefix = "vcsCheckout";
			String actionName = "vcsCheckout";
		
			// Set the Module Action Objective
			String s1 = "";
			if (vcsLabel != null)
				s1 = "Label="+vcsLabel;
			if (vcsRevision != null)
				s1 = "Rev="+vcsRevision;
			String s2 = (vcsResourcePath == null) ? "no_vcsResourcePath" : vcsResourcePath;
			System.setProperty("MODULE_ACTION_OBJECTIVE", "CHECKOUT : "+s1+" : "+s2);

			// Set the global suppress and debug flags for this class based on properties found in the property file
			setGlobalProperties();

			CommonUtils.writeOutput("***** BEGIN COMMAND: "+actionName+" *****",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);

			/*****************************************
			 * VALIDATE COMMAND-LINE VARIABLES
			 *****************************************/
			// Validate the arguments
			if(serverId == null || serverId.trim().length() ==0){
				throw new ValidationException("Server Id cannot be null or empty for "+prefix);
			}
			if(pathToServersXML == null || pathToServersXML.trim().length() ==0){
				throw new ValidationException("Path to Server XML file cannot be null or empty for "+prefix);
			}
			// Validate whether the files exist or not
			if (!CommonUtils.fileExists(pathToServersXML)) {
				throw new ValidationException("File ["+pathToServersXML+"] does not exist.");
			}

			/*****************************************
			 * PRINT OUT COMMAND-LINE VARIABLES
			 *****************************************/
			// Print out Debug input parameters
			CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---Input Variables from deployment plan file: ",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("    SERVER_ID=    "+serverId,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("    SERVER_PATH=  "+pathToServersXML,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("    VCONN_ID=     "+vcsConnId,prefix,"-debug2",logger,debug1,debug2,debug3);
		
			// Get the server info from the servers.xml file
			CompositeServer serverInfo = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, prefix, logger);
			serverInfo = validateServerProperties(serverInfo);
/*
            CommonUtils.writeOutput("---Server Info:",prefix,"-debug2",logger,debug1,debug2,debug3);
            CommonUtils.writeOutput("      Server-user=    "+serverInfo.getUser(),prefix,"-debug2",logger,debug1,debug2,debug3);
            CommonUtils.writeOutput("      Server-password=********",prefix,"-debug2",logger,debug1,debug2,debug3);
            CommonUtils.writeOutput("      Server-domain=  "+serverInfo.getDomain(),prefix,"-debug2",logger,debug1,debug2,debug3);
            CommonUtils.writeOutput("      Server-host=    "+serverInfo.getHostname(),prefix,"-debug2",logger,debug1,debug2,debug3);
            CommonUtils.writeOutput("      Server-port=    "+String.valueOf(serverInfo.getPort()),prefix,"-debug2",logger,debug1,debug2,debug3);
            CommonUtils.writeOutput("      Server-useHttps="+serverInfo.isUseHttps(),prefix,"-debug2",logger,debug1,debug2,debug3);
            CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
*/            
			// Ping the Server to make sure it is alive and the values are correct.
			WsApiHelperObjects.pingServer(serverInfo, true);

			/*****************************************
			 * INITIALIZE VCS STRUCTURE VARIABLE
			 *****************************************/
			// Initialize a new VCS structure for passing to the methods
			VcsStruct vcsStruct = (new VCSManagerImpl()).new VcsStruct();
			// Load the VCS structure and decrypt the vcs password -- Retrieve properties from the deploy.properties file
			vcsStruct.loadVcs(prefix, vcsUser, vcsPassword);
			
	        /*****************************************
			 * DISPLAY/VALIDATE VCS VARIABLES
			 *****************************************/	        
	        // Validate, Create VCS Workspace and Temp directories
	        vcsStruct.validateVcs(prefix);
	        // Resolve Absolute paths
	        vcsStruct.resolveCanonicalPathsVcs(prefix);
			// Display VCS variables
	        vcsStruct.displayVcs(prefix);

	        String origResourceType = null;
			String origResourcePath = null;

			// Validate VCS Label settings
			if (vcsLabel == null || vcsLabel.length() == 0) {
				vcsLabel = null;
				if (vcsResourcePath == null || vcsResourcePath.trim().length() ==0 || vcsRevision == null || vcsRevision.trim().length() ==0)
					throw new ValidationException("Resource Path cannot be null or empty for "+prefix);
				if (vcsResourcePath == null || vcsResourcePath.trim().length() ==0 || vcsRevision == null || vcsRevision.trim().length() ==0)
					throw new ValidationException("Revision cannot be null or empty for "+prefix);
			} else {

				// If the VCS Type is anything other than perforce and the label is being used then throw an exception.
				if (!vcsStruct.getVcsType().equalsIgnoreCase("P4")) {
					throw new ValidationException("VCS Resource Type=["+vcsStruct.getVcsType()+"] is not supported for the use of \"labels\".");							
				}

				// Set default values for label to be CIS root folder and revision "HEAD"
				vcsResourcePath = "/";
				vcsResourceType = "FOLDER";
				vcsRevision = "HEAD";
			}

			//********************************************************************************************
			// Validate vcsResourcePath settings prior to invocation of vcs scripts
			//********************************************************************************************
			origResourcePath = vcsResourcePath;
			if (vcsResourcePath == null || vcsResourcePath.length() == 0) {
				throw new ValidationException("VCS Resource Path is null or empty.");							
			}
			// Encode the VCS resource path
			vcsResourcePath = ResourceNameCodec.encode(vcsResourcePath);
			vcsResourcePath = vcsResourcePath.replaceAll("_002F", "/");

			//********************************************************************************************
			// Validate vcsResourceType settings prior to invocation of vcs scripts
			//********************************************************************************************
			// Get the Resource Type for the VCS Resource Path
			//vcsResourceType = getResourceManager().getResourceType(serverId, vcsResourcePath, pathToServersXML);
			origResourceType = vcsResourceType;
			vcsResourceType = getConvertedVcsResourceType(vcsResourceType);

			// Validate the resource type
			if (vcsResourceType == null || vcsResourceType.length() == 0) {
				throw new ValidationException("VCS Resource Type is null or empty.");							
			}
			if (!validResourceTypes.contains(vcsResourceType)) {
				throw new ValidationException("VCS Resource Type=["+vcsResourceType+"] is not valid.  Valid Resource Types=["+validResourceTypes+"]");							
			}

			//********************************************************************************************
			// Validate Revision settings prior to invocation of vcs scripts
			//********************************************************************************************
			if (vcsRevision == null || vcsRevision.length() == 0) {
				throw new ValidationException("VCS Revision is null or empty.");							
			}
			if (vcsRevision.equalsIgnoreCase("HEAD")) {
				vcsRevision = vcsRevision.toUpperCase();
			} else {
				try {
					double d = Double.valueOf(vcsRevision);
//					int i = Integer.parseInt(vcsRevision);
				} catch (Exception e) {
					throw new ValidationException("Unable to convert Revision ["+vcsRevision+"] to an decimal value.  Make sure there is only an integer or decimal value in this field.");														
				}
			}

			// Print out info statements
			CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---VCS Module ID Arguments:",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Resource Path          ="+origResourcePath,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Encoded Resource Path  ="+vcsResourcePath,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Resource Type          ="+origResourceType,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Converted Resource Type="+vcsResourceType,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Label                  ="+vcsLabel,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Revision               ="+vcsRevision,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("-- BEGIN OUTPUT ------------------------------------",prefix,"-info",logger,debug1,debug2,debug3);					

			//--------------------------------------------------------------
			// checkout
			//--------------------------------------------------------------
			checkout(vcsResourcePath, vcsResourceType, vcsLabel, vcsRevision, vcsStruct, serverId, pathToServersXML);

			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("Successfully completed "+actionName+".",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);

		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}			
	}

 
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsCheckout2(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckout2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String vcsRevision, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsCheckout2() with following params "+" serverName: "+serverId+" vcsConnectionId: "+vcsConnectionId+", vcsResourcePath: "+vcsResourcePath+", vcsResourceType: "+vcsResourceType+", vcsRevision: "+vcsRevision+", pathToVcsXML: "+pathToVcsXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
			// Set the VCS Module XML Connection Properties in the JVM Environment
			setVCSConnectionProperties(vcsConnectionId, pathToVcsXML);
			vcsConnId = vcsConnectionId;

			// Invoke the command
			vcsCheckoutImpl(serverId, vcsResourcePath, vcsResourceType, null, vcsRevision, pathToServersXML, vcsUser, vcsPassword);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}			
	}

	/* 
	 * This function is overloaded and supports vcs checkout using labels (Perforce only)
	 * 
	 * (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsCheckout2(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckout2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String vcsLabel, String vcsRevision, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsCheckout2() with following params "+" serverName: "+serverId+" vcsConnectionId: "+vcsConnectionId+", vcsResourcePath: "+vcsResourcePath+", vcsResourceType: "+vcsResourceType+", vcsLabel: "+vcsLabel+", vcsRevision: "+vcsRevision+", pathToVcsXML: "+pathToVcsXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
			// Set the VCS Module XML Connection Properties in the JVM Environment
			setVCSConnectionProperties(vcsConnectionId, pathToVcsXML);
			vcsConnId = vcsConnectionId;

			// Invoke the command
			vcsCheckoutImpl(serverId, vcsResourcePath, vcsResourceType, vcsLabel, vcsRevision, pathToServersXML, vcsUser, vcsPassword);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}			
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsCheckouts(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckouts(String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsCheckouts() with following params "+" serverName: "+serverId+", vcsIds: "+vcsIds+", pathToVcsXML: "+pathToVcsXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
			// Set the global suppress and debug flags for this class based on properties found in the property file
			setGlobalProperties();
			if (!vcsV2Method)
				vcsConnId = null;
			
			executeVCS(VCSManager.action.CHECKOUT.name(), serverId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}			
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsCheckouts2(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckouts2(String serverId, String vcsConnectionId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsCheckouts2() with following params "+" serverName: "+serverId+" vcsConnectionId: "+vcsConnectionId+", vcsIds: "+vcsIds+", pathToVcsXML: "+pathToVcsXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
			// Set the VCS Module XML Connection Properties in the JVM Environment
			setVCSConnectionProperties(vcsConnectionId, pathToVcsXML);
			vcsConnId = vcsConnectionId;

			// Invoke the command
			vcsCheckouts(serverId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}			
	}

	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsCheckin(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckin(String serverId, String vcsResourcePath, String vcsResourceType, String vcsMessage, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsCheckin() with following params "+" serverName: "+serverId+", vcsResourcePath: "+vcsResourcePath+", vcsMessage: "+vcsMessage+", pathToServersXML: "+pathToServersXML);
		}
		try {
			String prefix = "vcsCheckin";
			String actionName = "vcsCheckin";

			// Set the Module Action Objective
			String s1 = (vcsResourcePath == null) ? "no_vcsResourcePath" : vcsResourcePath;
			System.setProperty("MODULE_ACTION_OBJECTIVE", "CHECKIN : "+s1);

			// Set the global suppress and debug flags for this class based on properties found in the property file
			setGlobalProperties();
			
			CommonUtils.writeOutput("***** BEGIN COMMAND: "+actionName+" *****",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);

			/*****************************************
			 * VALIDATE COMMAND-LINE VARIABLES
			 *****************************************/
			// Validate the arguments
			if(serverId == null || serverId.trim().length() ==0 || vcsResourcePath == null || vcsResourcePath.trim().length() ==0 || vcsMessage == null || vcsMessage.trim().length() ==0 || pathToServersXML == null || pathToServersXML.trim().length() ==0){
				throw new ValidationException("Invalid Arguments for "+prefix);
			}
			// Validate whether the files exist or not
			if (!CommonUtils.fileExists(pathToServersXML)) {
				throw new ValidationException("File ["+pathToServersXML+"] does not exist.");
			}

			/*****************************************
			 * PRINT OUT COMMAND-LINE VARIABLES
			 *****************************************/
			// Print out Debug input parameters
			CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---Input Variables from deployment plan file: ",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("    SERVER_ID=    "+serverId,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("    SERVER_PATH=  "+pathToServersXML,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("    VCONN_ID=     "+vcsConnId,prefix,"-debug2",logger,debug1,debug2,debug3);

			// Get the server info from the servers.xml file
			CompositeServer serverInfo = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, prefix, logger);
			serverInfo = validateServerProperties(serverInfo);

			// Ping the Server to make sure it is alive and the values are correct.
			WsApiHelperObjects.pingServer(serverInfo, true);

			/*****************************************
			 * INITIALIZE VCS STRUCTURE VARIABLE
			 *****************************************/
			// Initialize a new VCS structure for passing to the methods
			VcsStruct vcsStruct = (new VCSManagerImpl()).new VcsStruct();
			// Load the VCS structure and decrypt the vcs password -- Retrieve properties from the deploy.properties file
			vcsStruct.loadVcs(prefix, vcsUser, vcsPassword);
			
	        /*****************************************
			 * DISPLAY/VALIDATE VCS VARIABLES
			 *****************************************/	        
	        // Validate, Create VCS Workspace and Temp directories
	        vcsStruct.validateVcs(prefix);
	        // Resolve Absolute paths
	        vcsStruct.resolveCanonicalPathsVcs(prefix);
			// Display VCS variables
	        vcsStruct.displayVcs(prefix);

			String origResourceType = null;
			String origResourcePath = null;

			//********************************************************************************************
			// Validate vcsResourcePath settings prior to invocation of vcs scripts
			//********************************************************************************************
			origResourcePath = vcsResourcePath;

			if (vcsResourcePath == null || vcsResourcePath.length() == 0) {
				throw new ValidationException("VCS Resource Path is null or empty.");							
			}
			// Encode the VCS resource path
			vcsResourcePath = ResourceNameCodec.encode(vcsResourcePath);
			vcsResourcePath = vcsResourcePath.replaceAll("_002F", "/");

			//********************************************************************************************
			// Validate vcsResourceType settings prior to invocation of vcs scripts
			//********************************************************************************************
			// Get the Resource Type for the VCS Resource Path
			origResourceType = vcsResourceType;
			vcsResourceType = getConvertedVcsResourceType(vcsResourceType);
		
			// Validate the resource type
			if (vcsResourceType == null || vcsResourceType.length() == 0) {
				throw new ValidationException("VCS Resource Type is null or empty.");							
			}
			if (!validResourceTypes.contains(vcsResourceType)) {
				throw new ValidationException("VCS Resource Type=["+vcsResourceType+"] is not valid.  Valid Resource Types=["+validResourceTypes+"]");							
			}
			
			//********************************************************************************************
			// Validate Message settings prior to invocation of vcs scripts
			//********************************************************************************************
	        // Check the VCS_MESSAGE_MANDATORY=true and determine whether the incoming message is null or empty and throw an exception.
			/* 3-7-2012: may not need 		
	        String messageMandatory = vcsStruct.getVcsMessageMandatory();
	        if (messageMandatory.equalsIgnoreCase("true")) {
	        	if (vcsMessage == null || vcsMessage.length() == 0) {
	        		throw new ValidationException("The comment message may not be null or empty.");
	        	}
	        }
			*/
			
	        // Prepend the property VCS_MESSAGE_PREPEND to message
	        String messagePrepend = vcsStruct.getVcsMessagePrepend();
	        if (messagePrepend != null && messagePrepend.length() > 0) {
	        	if (vcsMessage == null) {
		        	vcsMessage = messagePrepend;
	        	} else {
		        	vcsMessage = messagePrepend+" "+vcsMessage;	        		
	        	}
	        }
	        // Validate the message is not null or emtpy
			if (vcsMessage == null || vcsMessage.length() == 0) {
				throw new ValidationException("VCS Message is null or empty.");		
			}

			// Print out info statements
			CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---VCS Module ID Arguments:",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Resource Path          ="+origResourcePath,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Encoded Resource Path  ="+vcsResourcePath,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Resource Type          ="+origResourceType,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Converted Resource Type="+vcsResourceType,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Message                ="+vcsMessage,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("-- BEGIN OUTPUT ------------------------------------",prefix,"-info",logger,debug1,debug2,debug3);					

			//--------------------------------------------------------------
			// checkin
			//--------------------------------------------------------------
			checkin(vcsResourcePath, vcsResourceType, vcsMessage, vcsStruct, serverId, pathToServersXML);

			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("Successfully completed "+actionName+".",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}	
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsCheckin2(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckin2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String vcsMessage, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsCheckin2() with following params "+" serverName: "+serverId+" vcsConnectionId: "+vcsConnectionId+", vcsResourcePath: "+vcsResourcePath+", vcsMessage: "+vcsMessage+", pathToServersXML: "+pathToServersXML);
		}
		try {
			// Set the VCS Module XML Connection Properties in the JVM Environment
			setVCSConnectionProperties(vcsConnectionId, pathToVcsXML);
			vcsConnId = vcsConnectionId;

			// Invoke the command
			vcsCheckin(serverId, vcsResourcePath, vcsResourceType, vcsMessage, pathToServersXML, vcsUser, vcsPassword);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}			
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsCheckins(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckins(String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsCheckins() with following params "+" serverName: "+serverId+", vcsIds: "+vcsIds+", pathToVcsXML: "+pathToVcsXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
	        // Set the global suppress and debug flags for this class based on properties found in the property file
			setGlobalProperties();
			if (!vcsV2Method)
				vcsConnId = null;

			// Redirect processing  to 
			if (vcsMultiUserTopology) {
				logger.info("Multi-User Direct Access Topology is configured.  Redirecting to vcsForcedCheckins().");
				vcsForcedCheckins(serverId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
			} else {
				executeVCS(VCSManager.action.CHECKIN.name(), serverId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
			}
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}	
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsCheckins2(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckins2(String serverId, String vcsConnectionId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsCheckins2() with following params "+" serverName: "+serverId+" vcsConnectionId: "+vcsConnectionId+", vcsIds: "+vcsIds+", pathToVcsXML: "+pathToVcsXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
			// Set the VCS Module XML Connection Properties in the JVM Environment
			setVCSConnectionProperties(vcsConnectionId, pathToVcsXML);
			vcsConnId = vcsConnectionId;

			// Invoke the command
			vcsCheckins(serverId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}			
	}
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsForcedCheckin(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsForcedCheckin(String serverId, String vcsResourcePath, String vcsResourceType, String vcsMessage, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsForcedCheckin() with following params "+" serverName: "+serverId+", vcsResourcePath: "+vcsResourcePath+", vcsMessage: "+vcsMessage+", pathToServersXML: "+pathToServersXML);
		}
		try {
			String prefix = "vcsForcedCheckin";
			String actionName = "vcsForcedCheckin";

			// Set the Module Action Objective
			String s1 = (vcsResourcePath == null) ? "no_vcsResourcePath" : vcsResourcePath;
			System.setProperty("MODULE_ACTION_OBJECTIVE", "FORCED_CHECKIN : "+s1);

			// Set the global suppress and debug flags for this class based on properties found in the property file
			setGlobalProperties();

			CommonUtils.writeOutput("***** BEGIN COMMAND: "+actionName+" *****",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);

			/*****************************************
			 * VALIDATE COMMAND-LINE VARIABLES
			 *****************************************/
			// Validate the arguments
			if(serverId == null || serverId.trim().length() ==0 || vcsResourcePath == null || vcsResourcePath.trim().length() ==0 || vcsMessage == null || vcsMessage.trim().length() ==0 || pathToServersXML == null || pathToServersXML.trim().length() ==0){
				throw new ValidationException("Invalid Arguments for "+prefix);
			}
			// Validate whether the files exist or not
			if (!CommonUtils.fileExists(pathToServersXML)) {
				throw new ValidationException("File ["+pathToServersXML+"] does not exist.");
			}

			/*****************************************
			 * PRINT OUT COMMAND-LINE VARIABLES
			 *****************************************/
			// Print out Debug input parameters
			CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---Input Variables from deployment plan file: ",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("    SERVER_ID=    "+serverId,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("    SERVER_PATH=  "+pathToServersXML,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("    VCONN_ID=     "+vcsConnId,prefix,"-debug2",logger,debug1,debug2,debug3);

			// Get the server info from the servers.xml file
			CompositeServer serverInfo = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, prefix, logger);
			serverInfo = validateServerProperties(serverInfo);

			// Ping the Server to make sure it is alive and the values are correct.
			WsApiHelperObjects.pingServer(serverInfo, true);

			/*****************************************
			 * INITIALIZE VCS STRUCTURE VARIABLE
			 *****************************************/
			// Initialize a new VCS structure for passing to the methods
			VcsStruct vcsStruct = (new VCSManagerImpl()).new VcsStruct();
			// Load the VCS structure and decrypt the vcs password -- Retrieve properties from the deploy.properties file
			vcsStruct.loadVcs(prefix, vcsUser, vcsPassword);
			
	        /*****************************************
			 * DISPLAY/VALIDATE VCS VARIABLES
			 *****************************************/	        
	        // Validate, Create VCS Workspace and Temp directories
	        vcsStruct.validateVcs(prefix);
	        // Resolve Absolute paths
	        vcsStruct.resolveCanonicalPathsVcs(prefix);
			// Display VCS variables
	        vcsStruct.displayVcs(prefix);

			String origResourceType = null;
			String origResourcePath = null;

			//********************************************************************************************
			// Validate vcsResourcePath settings prior to invocation of vcs scripts
			//********************************************************************************************
			origResourcePath = vcsResourcePath;

			if (vcsResourcePath == null || vcsResourcePath.length() == 0) {
				throw new ValidationException("VCS Resource Path is null or empty.");							
			}
			// Encode the VCS resource path
			vcsResourcePath = ResourceNameCodec.encode(vcsResourcePath);
			vcsResourcePath = vcsResourcePath.replaceAll("_002F", "/");

			//********************************************************************************************
			// Validate vcsResourceType settings prior to invocation of vcs scripts
			//********************************************************************************************
			// Get the Resource Type for the VCS Resource Path
			origResourceType = vcsResourceType;
			vcsResourceType = getConvertedVcsResourceType(vcsResourceType);
			
			// Validate the resource type
			if (vcsResourceType == null || vcsResourceType.length() == 0) {
				throw new ValidationException("VCS Resource Type is null or empty.");							
			}
			if (!validResourceTypes.contains(vcsResourceType)) {
				throw new ValidationException("VCS Resource Type=["+vcsResourceType+"] is not valid.  Valid Resource Types=["+validResourceTypes+"]");							
			}
			        
			//********************************************************************************************
			// Validate Message settings prior to invocation of vcs scripts
			//********************************************************************************************
	        // Check the VCS_MESSAGE_MANDATORY=true and determine whether the incoming message is null or empty and throw an exception.
			/* 3-7-2012: may not need 		
	        String messageMandatory = vcsStruct.getVcsMessageMandatory();
	        if (messageMandatory.equalsIgnoreCase("true")) {
	        	if (vcsMessage == null || vcsMessage.length() == 0) {
	        		throw new ValidationException("The comment message may not be null or empty.");
	        	}
	        }
			*/
			
	        // Prepend the property VCS_MESSAGE_PREPEND to message
	        String messagePrepend = vcsStruct.getVcsMessagePrepend();
	        if (messagePrepend != null && messagePrepend.length() > 0) {
	        	if (vcsMessage == null) {
		        	vcsMessage = messagePrepend;
	        	} else {
		        	vcsMessage = messagePrepend+" "+vcsMessage;	        		
	        	}
	        }
	        // Validate the message is not null or emtpy
			if (vcsMessage == null || vcsMessage.length() == 0) {
				throw new ValidationException("VCS Message is null or empty.");		
			}

			// Print out info statements
			CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---VCS Module ID Arguments:",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Resource Path          ="+origResourcePath,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Encoded Resource Path  ="+vcsResourcePath,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Resource Type          ="+origResourceType,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Converted Resource Type="+vcsResourceType,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Message                ="+vcsMessage,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("-- BEGIN OUTPUT ------------------------------------",prefix,"-info",logger,debug1,debug2,debug3);					

			//--------------------------------------------------------------
			// forced checkin
			//--------------------------------------------------------------
			forced_checkin(vcsResourcePath, vcsResourceType, vcsMessage, vcsStruct, serverId, pathToServersXML);

			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("Successfully completed "+actionName+".",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}	
	}


	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsForcedCheckin2(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsForcedCheckin2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String vcsMessage, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsForcedCheckin2() with following params "+" serverName: "+serverId+" vcsConnectionId: "+vcsConnectionId+", vcsResourcePath: "+vcsResourcePath+", vcsMessage: "+vcsMessage+", pathToServersXML: "+pathToServersXML);
		}
		try {
			// Set the VCS Module XML Connection Properties in the JVM Environment
			setVCSConnectionProperties(vcsConnectionId, pathToVcsXML);
			vcsConnId = vcsConnectionId;

			// Invoke the command
			vcsForcedCheckin(serverId, vcsResourcePath, vcsResourceType, vcsMessage, pathToServersXML, vcsUser, vcsPassword);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}			
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsForcedCheckins(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsForcedCheckins(String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsForcedCheckins() with following params "+" serverName: "+serverId+", vcsIds: "+vcsIds+", pathToVcsXML: "+pathToVcsXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
	        // Set the global suppress and debug flags for this class based on properties found in the property file
			setGlobalProperties();
			if (!vcsV2Method)
				vcsConnId = null;

			executeVCS(VCSManager.action.FORCED_CHECKIN.name(), serverId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}	
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsForcedCheckins2(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsForcedCheckins2(String serverId, String vcsConnectionId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsForcedCheckins2() with following params "+" serverName: "+serverId+" vcsConnectionId: "+vcsConnectionId+", vcsIds: "+vcsIds+", pathToVcsXML: "+pathToVcsXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
			// Set the VCS Module XML Connection Properties in the JVM Environment
			setVCSConnectionProperties(vcsConnectionId, pathToVcsXML);
			vcsConnId = vcsConnectionId;

			// Invoke the command
			vcsForcedCheckins(serverId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}			
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsPrepareCheckin(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsPrepareCheckin(String serverId, String vcsResourcePath, String vcsResourceType, String pathToServersXML, String vcsUser, String vcsPassword)	throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsPrepareCheckin() with following params "+" serverName: "+serverId+", vcsResourcePath: "+vcsResourcePath+", pathToServersXML: "+pathToServersXML);
		}
		try {
			String prefix = "vcsPrepareCheckin";
			String actionName = "vcsPrepareCheckin";

			// Set the Module Action Objective
			String s1 = (vcsResourcePath == null) ? "no_vcsResourcePath" : vcsResourcePath;
			System.setProperty("MODULE_ACTION_OBJECTIVE", "PREPARE_CHECKIN : "+s1);

			// Set the global suppress and debug flags for this class based on properties found in the property file
			setGlobalProperties();
			if (!vcsV2Method)
				vcsConnId = null;

			CommonUtils.writeOutput("***** BEGIN COMMAND: "+actionName+" *****",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);

			/*****************************************
			 * VALIDATE COMMAND-LINE VARIABLES
			 *****************************************/
			// Validate the arguments
			if(serverId == null || serverId.trim().length() ==0 || vcsResourcePath == null || vcsResourcePath.trim().length() ==0 || pathToServersXML == null || pathToServersXML.trim().length() ==0){
				throw new ValidationException("Invalid Arguments for "+prefix);
			}
			// Validate whether the files exist or not
			if (!CommonUtils.fileExists(pathToServersXML)) {
				throw new ValidationException("File ["+pathToServersXML+"] does not exist.");
			}

			/*****************************************
			 * PRINT OUT COMMAND-LINE VARIABLES
			 *****************************************/
			// Print out Debug input parameters
			CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---Input Variables from deployment plan file: ",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("    SERVER_ID=    "+serverId,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("    SERVER_PATH=  "+pathToServersXML,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("    VCONN_ID=     "+vcsConnId,prefix,"-debug2",logger,debug1,debug2,debug3);

			// Get the server info from the servers.xml file
			CompositeServer serverInfo = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, prefix, logger);
			serverInfo = validateServerProperties(serverInfo);

			// Ping the Server to make sure it is alive and the values are correct.
			WsApiHelperObjects.pingServer(serverInfo, true);

			/*****************************************
			 * INITIALIZE VCS STRUCTURE VARIABLE
			 *****************************************/
			// Initialize a new VCS structure for passing to the methods
			VcsStruct vcsStruct = (new VCSManagerImpl()).new VcsStruct();
			// Load the VCS structure and decrypt the vcs password -- Retrieve properties from the deploy.properties file
			vcsStruct.loadVcs(prefix, vcsUser, vcsPassword);
			
	        /*****************************************
			 * DISPLAY/VALIDATE VCS VARIABLES
			 *****************************************/	        
	        // Validate, Create VCS Workspace and Temp directories
	        vcsStruct.validateVcs(prefix);
	        // Resolve Absolute paths
	        vcsStruct.resolveCanonicalPathsVcs(prefix);
			// Display VCS variables
	        vcsStruct.displayVcs(prefix);

			String origResourceType = null;
			String origResourcePath = null;

			//********************************************************************************************
			// Validate vcsResourcePath settings prior to invocation of vcs scripts
			//********************************************************************************************
			origResourcePath = vcsResourcePath;
			if (vcsResourcePath == null || vcsResourcePath.length() == 0) {
				throw new ValidationException("VCS Resource Path is null or empty.");							
			}
			// Encode the VCS resource path
			vcsResourcePath = ResourceNameCodec.encode(vcsResourcePath);
			vcsResourcePath = vcsResourcePath.replaceAll("_002F", "/");

			//********************************************************************************************
			// Validate vcsResourceType settings prior to invocation of vcs scripts
			//********************************************************************************************
			// Get the Resource Type for the VCS Resource Path
			origResourceType = vcsResourceType;
			vcsResourceType = getConvertedVcsResourceType(vcsResourceType);
			
			// Validate the resource type
			if (vcsResourceType == null || vcsResourceType.length() == 0) {
				throw new ValidationException("VCS Resource Type is null or empty.");							
			}
			if (!validResourceTypes.contains(vcsResourceType)) {
				throw new ValidationException("VCS Resource Type=["+vcsResourceType+"] is not valid.  Valid Resource Types=["+validResourceTypes+"]");							
			}

			// Print out info statements
			CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---VCS Module ID Arguments:",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Resource Path          ="+origResourcePath,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Encoded Resource Path  ="+vcsResourcePath,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Resource Type          ="+origResourceType,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Converted Resource Type="+vcsResourceType,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("-- BEGIN OUTPUT ------------------------------------",prefix,"-info",logger,debug1,debug2,debug3);					
			//--------------------------------------------------------------
			// prepare_checkin
			//--------------------------------------------------------------
			prepare_checkin(vcsResourcePath, vcsResourceType, vcsStruct, serverId, pathToServersXML);

			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("Successfully completed "+actionName+".",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}	
	}
  
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsPrepareCheckin2(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsPrepareCheckin2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword)	throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsPrepareCheckin2() with following params "+" serverName: "+serverId+" vcsConnectionId: "+vcsConnectionId+", vcsResourcePath: "+vcsResourcePath+", pathToServersXML: "+pathToServersXML);
		}
		try {
			// Set the VCS Module XML Connection Properties in the JVM Environment
			setVCSConnectionProperties(vcsConnectionId, pathToVcsXML);
			vcsConnId = vcsConnectionId;

			// Invoke the command
			vcsPrepareCheckin(serverId,vcsResourcePath, vcsResourceType, pathToServersXML, vcsUser, vcsPassword);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}			
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsPrepareCheckins(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsPrepareCheckins(String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword)	throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsPrepareCheckins() with following params "+" serverName: "+serverId+", vcsIds: "+vcsIds+", pathToVcsXML: "+pathToVcsXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
	        // Set the global suppress and debug flags for this class based on properties found in the property file
			setGlobalProperties();
			if (!vcsV2Method)
				vcsConnId = null;

			executeVCS(VCSManager.action.PREPARE_CHECKIN.name(), serverId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}	
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsPrepareCheckins2(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsPrepareCheckins2(String serverId, String vcsConnectionId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword)	throws CompositeException {	
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsPrepareCheckins2() with following params "+" serverName: "+serverId+" vcsConnectionId: "+vcsConnectionId+", vcsIds: "+vcsIds+", pathToVcsXML: "+pathToVcsXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
			// Set the VCS Module XML Connection Properties in the JVM Environment
			setVCSConnectionProperties(vcsConnectionId, pathToVcsXML);
			vcsConnId = vcsConnectionId;

			// Invoke the command
			vcsPrepareCheckins(serverId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}			
	}

	

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsInitializeBaseFolderCheckin2(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsInitializeBaseFolderCheckin2(String vcsConnectionId, String customPathList, String pathToVcsXML, String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsInitializeBaseFolderCheckin2() with following params: vcsConnectionId: "+vcsConnectionId+", customPathList: "+ customPathList);
		}
		try {
			// Set the VCS Module XML Connection Properties in the JVM Environment
			setVCSConnectionProperties(vcsConnectionId, pathToVcsXML);
			vcsConnId = vcsConnectionId;

			// Invoke the command
			vcsInitializeBaseFolderCheckin(customPathList, vcsUser, vcsPassword);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}	
	}
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsInitializeBaseFolderCheckin(java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsInitializeBaseFolderCheckin(String customPathList, String vcsUser, String vcsPassword) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering VCSManagerImpl.vcsInitializeBaseFolderCheckin() with following params: customPathList:"+customPathList);
		}
		try {
			/*****************************************
			 * INITIALIZE VARIABLES
			 *****************************************/
			// Trim input variables to remove spaces
			vcsUser = vcsUser.trim();
			vcsPassword = vcsPassword.trim();

			String prefix = "vcsInitializeBaseFolderCheckin";
			String actionName = "vcsInitializeBaseFolderCheckin";

			// Always initialize starting at the root (/) folder
			String vcsResourcePath = "/";
			String vcsResourceType = "CONTAINER";
			String vcsMessage = "VCS Base Folder Initialization.";
	
			// Set the global suppress and debug flags for this class based on properties found in the property file
			setGlobalProperties();
			
			CommonUtils.writeOutput("***** BEGIN COMMAND: "+actionName+" *****",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			
			/*****************************************
			 * PRINT OUT COMMAND-LINE VARIABLES
			 *****************************************/
			// Print out Debug input parameters
			CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---Input Variables from deployment plan file: ",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("    VCONN_ID=     "+vcsConnId,prefix,"-debug2",logger,debug1,debug2,debug3);

			/*****************************************
			 * INITIALIZE VCS STRUCTURE VARIABLE
			 *****************************************/
			// Initialize a new VCS structure for passing to the methods
			VcsStruct vcsStruct = (new VCSManagerImpl()).new VcsStruct();
			// Load the VCS structure and decrypt the vcs password -- Retrieve properties from the deploy.properties file
			vcsStruct.loadVcs(prefix, vcsUser, vcsPassword);

			// Set the Module Action Objective
			String s1 = (vcsStruct.getVcsUsername() == null) ? "no_User" : "User="+vcsStruct.getVcsUsername();
			System.setProperty("MODULE_ACTION_OBJECTIVE", "INIT_BASE : "+s1);

	        /*****************************************
			 * DISPLAY/VALIDATE VCS VARIABLES
			 *****************************************/	        
	        // Validate, Create VCS Workspace and Temp directories
	        vcsStruct.validateVcs(prefix);
	        // Resolve Absolute paths
	        vcsStruct.resolveCanonicalPathsVcs(prefix);
			// Display VCS variables
	        vcsStruct.displayVcs(prefix);

			String origResourceType = null;
			String origResourcePath = null;

			//********************************************************************************************
			// Validate vcsResourcePath settings prior to invocation of vcs scripts
			//********************************************************************************************
			origResourcePath = vcsResourcePath;

			if (vcsResourcePath == null || vcsResourcePath.length() == 0) {
				throw new ValidationException("VCS Resource Path is null or empty.");							
			}
			// Encode the VCS resource path
			vcsResourcePath = ResourceNameCodec.encode(vcsResourcePath);
			vcsResourcePath = vcsResourcePath.replaceAll("_002F", "/");

			//********************************************************************************************
			// Validate vcsResourceType settings prior to invocation of vcs scripts
			//********************************************************************************************
			// Get the Resource Type for the VCS Resource Path
			origResourceType = vcsResourceType;
			vcsResourceType = getConvertedVcsResourceType(vcsResourceType);
		
			// Validate the resource type
			if (vcsResourceType == null || vcsResourceType.length() == 0) {
				throw new ValidationException("VCS Resource Type is null or empty.");							
			}
			if (!validResourceTypes.contains(vcsResourceType)) {
				throw new ValidationException("VCS Resource Type=["+vcsResourceType+"] is not valid.  Valid Resource Types=["+validResourceTypes+"]");							
			}
					
	        // Prepend the property VCS_MESSAGE_PREPEND to message
	        String messagePrepend = vcsStruct.getVcsMessagePrepend();
	        if (messagePrepend != null && messagePrepend.length() > 0) {
	        	if (vcsMessage == null) {
		        	vcsMessage = messagePrepend;
	        	} else {
		        	vcsMessage = messagePrepend+" "+vcsMessage;	        		
	        	}
	        }
	        // Validate the message is not null or emtpy
			if (vcsMessage == null || vcsMessage.length() == 0) {
				throw new ValidationException("VCS Message is null or empty.");		
			}

			// Print out info statements
			CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---VCS Module ID Arguments:",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Resource Path          ="+origResourcePath,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Encoded Resource Path  ="+vcsResourcePath,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Resource Type          ="+origResourceType,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Converted Resource Type="+vcsResourceType,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   Message                ="+vcsMessage,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("   customPathList         ="+customPathList,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("-- BEGIN OUTPUT ------------------------------------",prefix,"-info",logger,debug1,debug2,debug3);					

			//--------------------------------------------------------------
			// checkin
			//--------------------------------------------------------------
			initialize_checkin(vcsResourcePath, vcsResourceType, vcsMessage, customPathList, vcsStruct);

			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("Successfully completed "+actionName+".",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			
		} catch (CompositeException e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}	
	}
	
	
/******************************************************************************************
 *  PUBLIC INTERFACES
 *  
 *  PROVIDE INTEGRATION FOR COMPOSITE STUDIO 
 *  
 *  These methods provide the public interface for Composite Studio integration.
 * 
 *******************************************************************************************/
		
		/* (non-Javadoc)
		 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsStudioInitWorkspace(java.lang.String, java.lang.String)
		 */
	//	@Override
		public void vcsStudioInitWorkspace(String vcsUser, String vcsPassword) throws CompositeException {

			try {
				logger.info("--------------------------------------------------------");
				logger.info("---------------- STUDIO/VCS INTEGRATION ----------------");
				logger.info("--------------------------------------------------------");

				// Gets set only when PD Tool Studio is being invoked
				pdToolStudio = true;
		
				// Set the global suppress and debug flags for this class based on properties found in the property file
				setGlobalProperties();

				vcsInitWorkspaceCommon(vcsUser, vcsPassword);
				
			} catch (CompositeException e) {
				logger.error("Initialize VCS Workspace Failed. Error: ",e);
				throw new CompositeException(e.getMessage(),e);
			}
		}

		/* (non-Javadoc)
		 * @see com.cisco.dvbu.ps.deploytool.resource.VCSManager#vcsStudioInitializeBaseFolderCheckin(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
		 */
	//	@Override
		public void vcsStudioInitializeBaseFolderCheckin(String customPathList, String vcsCheckinOptions, String vcsUser, String vcsPassword) throws CompositeException {

			try {
				logger.info("--------------------------------------------------------");
				logger.info("-------- STUDIO/VCS BASE FOLDER INITIALIZATION ---------");
				logger.info("--------------------------------------------------------");

				// Gets set only when PD Tool Studio is being invoked
				pdToolStudio = true;
				vcsStudioInitializeBaseFolderCheckin = true;
				
				// Set the global suppress and debug flags for this class based on properties found in the property file
				setGlobalProperties();

				String oldProp = System.setProperty("VCS_CHECKIN_OPTIONS", CommonUtils.extractVariable("vcsStudioInitializeBaseFolderCheckin", vcsCheckinOptions, propertyFile, true));
				
				vcsInitializeBaseFolderCheckin(customPathList, vcsUser, vcsPassword);
				
			} catch (CompositeException e) {
				logger.error("Studio/VCS Base Folder Initialize Failed. Error: ",e);
				throw new CompositeException(e.getMessage(),e);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.cisco.dvbu.ps.deploytool.services.VCSManager#vcsStudioCheckout(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
		 */
	//	@Override
		public void vcsStudioCheckout(String resourcePath, String resourceType, String revision, String vcsWorkspace, String vcsWorkspaceTemp) throws CompositeException {
			/* Derived from generalized_scripts_studio
			 * 
				echo ============= VCS CHECKOUT =============
				call vcs_checkin_checkout_%VCS_TYPE%\vcs_checkout.bat %resourcePath% %resourceType% %Revision% %Workspace%

				echo ============= DIFFMERGER ROLLBACK =============
				call diffmerger\rollback.bat %resourcePath% %resourceType% %Workspace% %VcsTemp%
			 */
			logger.info("--------------------------------------------------------");
			logger.info("---------------- STUDIO/VCS INTEGRATION ----------------");
			logger.info("--------------------------------------------------------");

			String prefix = "vcsStudioCheckout";
			
			// Gets set only when PD Tool Studio is being invoked
			pdToolStudio = true;

			// Set the global suppress and debug flags for this class based on properties found in the property file
			setGlobalProperties();

	        try {
	        	if (vcsMultiUserDisableCheckout) {
	        		
					CommonUtils.writeOutput("============================================================================",	prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("VCS Studio Checkout has been disabled.",										prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("Please contact your CIS Administrator to assist with a managed VCS checkout.",	prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("============================================================================",	prefix,"-info",logger,debug1,debug2,debug3);
	   		
	        	} else {
					/*****************************************
					 * INITIALIZE VCS STRUCTURE VARIABLE
					 *****************************************/
					// Initialize a new VCS structure for passing to the methods
					VcsStruct vcsStruct = (new VCSManagerImpl()).new VcsStruct();
					// Load the VCS structure and decrypt the vcs password -- Retrieve properties from the property file
					vcsStruct.loadVcs(prefix, null, null);
					// Override Workspace Project and Temp with supplied variables from Studio
					vcsStruct.setVcsWorkspaceProject(vcsWorkspace);
					vcsStruct.setVcsTemp(vcsWorkspaceTemp);
					
			        /*****************************************
					 * DISPLAY/VALIDATE VCS VARIABLES
					 *****************************************/	        
			        // Validate, Create VCS Workspace and Temp directories
			        vcsStruct.validateVcs(prefix);
			        // Resolve Absolute paths
			        vcsStruct.resolveCanonicalPathsVcs(prefix);
					// Display VCS variables
			        vcsStruct.displayVcs(prefix);
		
					/*****************************************
					 * PERFORM VCS STUDIO CHECKOUT
					 *****************************************/
					CommonUtils.writeOutput("======== BEGIN VCS ["+vcsStruct.getVcsType()+"] Studio Checkout ========",prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("---VCS Arguments:",prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      resourcePath=       "+resourcePath,prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      resourceType=       "+resourceType,prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      Revision=           "+revision,prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      VcsWorkspace=       "+vcsStruct.getVcsWorkspace(),prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      VcsWorkspaceProject="+vcsStruct.getVcsWorkspaceProject(),prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      VcsTemp=            "+vcsStruct.getVcsTemp(),prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      VcsUser=            "+vcsStruct.getVcsUsername(),prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      VcsPassword=        ********",prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
							
					CommonUtils.writeOutput("==============  VCS CHECKOUT =============",prefix,"-debug2",logger,debug1,debug2,debug3);
			        vcs_checkin_checkout__vcs_checkout(resourcePath, resourceType, null, revision,  vcsStruct);
			        
					CommonUtils.writeOutput("==========  VCS CHECKOUT VALIDATE ========",prefix,"-debug2",logger,debug1,debug2,debug3);
			        vcs_checkout_validate(resourcePath, resourceType, vcsStruct);

			        CommonUtils.writeOutput("========== DIFFMERGER ROLLBACK ===========",prefix,"-debug2",logger,debug1,debug2,debug3);
			        diffmerger__rollback(resourcePath, resourceType, vcsStruct);
			        
					CommonUtils.writeOutput("====== COMPLETED VCS ["+vcsStruct.getVcsType()+"] Studio Checkout ======",prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
	        	}
			} catch (CompositeException e) {
			    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
				logger.error("Failed executing "+prefix+".",e);
				throw new CompositeException(e.getMessage(), e);
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.cisco.dvbu.ps.deploytool.services.VCSManager#vcsStudioCheckin(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
		 */
	//	@Override
		public void vcsStudioCheckin(String resourcePath, String resourceType, String message, String vcsWorkspace, String vcsWorkspaceTemp) throws CompositeException {
			
			/* Derived from generalized_scripts_studio
			 * 
				echo ============= DIFFMERGER CHECKIN =============
				call diffmerger\checkin.bat %resourcePath% %resourceType% %Workspace% %VcsTemp%
				
				echo ============= VCS CHECKOUT =============
				call vcs_checkin_checkout_%VCS_TYPE%\vcs_checkout.bat %resourcePath% %resourceType% HEAD %Workspace%
			
				echo ============= VCS CHECKIN =============	
				call vcs_checkin_checkout_%VCS_TYPE%\vcs_checkin.bat %resourcePath% %resourceType% %Message% %Workspace%
			 */
			
			String prefix = "vcsStudioCheckin";
			
			// Gets set only when PD Tool Studio is being invoked
			pdToolStudio = true;

	        // Set the global suppress and debug flags for this class based on properties found in the property file
			setGlobalProperties();

			// Redirect processing to vcsStudioForcedCheckin only if the property VCS_MULTI_USER_TOPOLOGY=true
			if (vcsMultiUserTopology) {
				logger.info("Multi-User Direct Access Topology is configured.  Redirecting to vcsStudioForcedCheckin().");
				vcsStudioForcedCheckin(resourcePath, resourceType, message, vcsWorkspace, vcsWorkspaceTemp);
			} else {
			
				logger.info("--------------------------------------------------------");
				logger.info("---------------- STUDIO/VCS INTEGRATION ----------------");
				logger.info("--------------------------------------------------------");
		
		        try {
					/*****************************************
					 * INITIALIZE VCS STRUCTURE VARIABLE
					 *****************************************/
					// Initialize a new VCS structure for passing to the methods
					VcsStruct vcsStruct = (new VCSManagerImpl()).new VcsStruct();
					// Load the VCS structure and decrypt the vcs password -- Retrieve properties from the property file
					vcsStruct.loadVcs(prefix, null, null);
					// Override Workspace Project and Temp with supplied variables from Studio
					vcsStruct.setVcsWorkspaceProject(vcsWorkspace);
					vcsStruct.setVcsTemp(vcsWorkspaceTemp);
					
			        /*****************************************
					 * DISPLAY/VALIDATE VCS VARIABLES
					 *****************************************/	        
			        // Validate, Create VCS Workspace and Temp directories
			        vcsStruct.validateVcs(prefix);
			        // Resolve Absolute paths
			        vcsStruct.resolveCanonicalPathsVcs(prefix);
					// Display VCS variables
			        vcsStruct.displayVcs(prefix);

			        // Check the VCS_MESSAGE_MANDATORY=true and determine whether the incoming message is null or empty and throw an exception.
			        /* 3-7-2012: may not need 		
			        String messageMandatory = vcsStruct.getVcsMessageMandatory();
			        if (messageMandatory.equalsIgnoreCase("true")) {
			        	if (message == null || message.length() == 0) {
			        		throw new ValidationException("The comment message may not be null or empty.");
			        	}
			        }
			       	*/
			        
	        		// Extract the vcs checkin command template from the studio comment:  VCS_CHECKIN_OPTIONS(<command>)
	        		if (message.toUpperCase().contains("VCS_CHECKIN_OPTIONS")) 
	        		{
	        			String tempMessage = "";
	        			int beg = message.indexOf("VCS_CHECKIN_OPTIONS");
	        			if (beg >= 0) 
	        			{
	        				int pbeg = message.indexOf("(", beg);
	        				int pend = message.indexOf(")", beg);
	        				if (beg > 0)
	        					tempMessage = message.substring(0, beg);
	        				if (pbeg > 0 && pend > 0 && pbeg != pend) {
	        					String command = message.substring(pbeg+1, pend);
	        					String vcsCheckinOptions = "";
	        					if (vcsStruct.getVcsCheckinOptions() != null) 
	        					{
	        						vcsCheckinOptions = vcsStruct.getVcsCheckinOptions().trim();
	        						if (!command.equalsIgnoreCase(vcsCheckinOptions)) {
	        							if (vcsCheckinOptions.toLowerCase().contains(command.toLowerCase())) 
	        							{
	        								command = vcsCheckinOptions;
	        							} else {
	        								if (command.toLowerCase().contains(vcsCheckinOptions.toLowerCase())) {
	        									// no operation required since the command contains all commands
	        								} else {
	        									command = command + " " + vcsCheckinOptions;
	        								}
	        							}
	        						}
	        					}
	        					
	        					// Add the studio command to any existing command options
	        					vcsStruct.setVcsCheckinOptions(command);
	        				}
	        				message = tempMessage + message.substring(pend+1);
	        			}
	        		}
	        
			        // Prepend the property VCS_MESSAGE_PREPEND to message
			        String messagePrepend = vcsStruct.getVcsMessagePrepend();
			        if (messagePrepend != null && messagePrepend.length() > 0) {
			        	if (message == null) {
			        		message = messagePrepend;
			        	} else {
			        		message = messagePrepend+" "+message;	        		
			        	}
			        }

			        /*****************************************
					 * PERFORM VCS STUDIO CHECKIN
					 *****************************************/
					CommonUtils.writeOutput("======== BEGIN VCS ["+vcsStruct.getVcsType()+"] Studio Checkin ========",prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("---VCS Arguments:",prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      resourcePath=             "+resourcePath,prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      resourceType=             "+resourceType,prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      Message=                  "+message,prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      VcsCheckinOptions=        "+vcsStruct.getVcsCheckinOptions(),prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      VcsCheckinOptionsRequired="+vcsStruct.getVcsCheckinOptionsRequired(),prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      VcsWorkspace=             "+vcsStruct.getVcsWorkspace(),prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      VcsWorkspaceProject=      "+vcsStruct.getVcsWorkspaceProject(),prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      VcsTemp=                  "+vcsStruct.getVcsTemp(),prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      VcsUser=                  "+vcsStruct.getVcsUsername(),prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("      VcsPassword=              ********",prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
									
					CommonUtils.writeOutput("========== DIFFMERGER CHECKIN ============",prefix,"-debug2",logger,debug1,debug2,debug3);
			        diffmerger__checkin(resourcePath, resourceType, vcsStruct);
			        
					CommonUtils.writeOutput("==============  VCS CHECKOUT =============",prefix,"-debug2",logger,debug1,debug2,debug3);
			        vcs_checkin_checkout__vcs_checkout(resourcePath, resourceType, null, "HEAD",  vcsStruct);
		
					CommonUtils.writeOutput("==========  VCS CHECKOUT VALIDATE ========",prefix,"-debug2",logger,debug1,debug2,debug3);
			        vcs_checkout_validate(resourcePath, resourceType, vcsStruct);

			        CommonUtils.writeOutput("============== VCS CHECKIN ===============",prefix,"-debug2",logger,debug1,debug2,debug3);
					vcs_checkin_checkout__vcs_checkin(resourcePath, resourceType, message, vcsStruct);
		
					CommonUtils.writeOutput("====== COMPLETED VCS ["+vcsStruct.getVcsType()+"] Studio Checkin ======",prefix,"-debug2",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
				} catch (CompositeException e) {
				    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
					logger.error("Failed executing "+prefix+".",e);
					throw new CompositeException(e.getMessage(), e);
				}
	        }
		}

		/*
		 * (non-Javadoc)
		 * @see com.cisco.dvbu.ps.deploytool.services.VCSManager#vcsStudioForcedCheckin(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
		 */
	//	@Override
		public void vcsStudioForcedCheckin(String resourcePath, String resourceType, String message, String vcsWorkspace, String vcsWorkspaceTemp) throws CompositeException {
			/* Derived from generalized_scripts_studio
			 * 
				echo ============= VCS CHECKOUT =============
				call vcs_checkin_checkout_%VCS_TYPE%\vcs_checkout.bat %resourcePath% %resourceType% HEAD %Workspace%
				
				echo ============= DIFFMERGER CHECKIN =============
				call diffmerger\checkin.bat %resourcePath% %resourceType% %Workspace% %VcsTemp%
				
				echo ============= VCS CHECKIN =============
				call vcs_checkin_checkout_%VCS_TYPE%\vcs_checkin.bat %resourcePath% %resourceType% %Message% %Workspace%
			 */
			logger.info("--------------------------------------------------------");
			logger.info("---------------- STUDIO/VCS INTEGRATION ----------------");
			logger.info("--------------------------------------------------------");

			String prefix = "vcsStudioForcedCheckin";
			
			// Gets set only when PD Tool Studio is being invoked
			pdToolStudio = true;
			
			// Set the global suppress and debug flags for this class based on properties found in the property file
			setGlobalProperties();

	        try {
				/*****************************************
				 * INITIALIZE VCS STRUCTURE VARIABLE
				 *****************************************/
				// Initialize a new VCS structure for passing to the methods
				VcsStruct vcsStruct = (new VCSManagerImpl()).new VcsStruct();
				// Load the VCS structure and decrypt the vcs password -- Retrieve properties from the property file
				vcsStruct.loadVcs(prefix, null, null);
				// Override Workspace Project and Temp with supplied variables from Studio
				vcsStruct.setVcsWorkspaceProject(vcsWorkspace);
				vcsStruct.setVcsTemp(vcsWorkspaceTemp);
				
		        /*****************************************
				 * DISPLAY/VALIDATE VCS VARIABLES
				 *****************************************/	        
		        // Validate, Create VCS Workspace and Temp directories
		        vcsStruct.validateVcs(prefix);
		        // Resolve Absolute paths
		        vcsStruct.resolveCanonicalPathsVcs(prefix);
				// Display VCS variables
		        vcsStruct.displayVcs(prefix);
		        
/* 3-7-2012: may not need 		
		        // Check the VCS_MESSAGE_MANDATORY=true and determine whether the incoming message is null or empty and throw an exception.
		        String messageMandatory = vcsStruct.getVcsMessageMandatory();
		        if (messageMandatory.equalsIgnoreCase("true")) {
		        	if (message == null || message.length() == 0) {
		        		throw new ValidationException("The comment message may not be null or empty.");
		        	}
		        }
*/
        		// Extract the vcs checkin command template from the studio comment:  VCS_CHECKIN_OPTIONS(<command>)
        		if (message.toUpperCase().contains("VCS_CHECKIN_OPTIONS")) 
        		{
        			String tempMessage = "";
        			int beg = message.indexOf("VCS_CHECKIN_OPTIONS");
        			if (beg >= 0) 
        			{
        				int pbeg = message.indexOf("(", beg);
        				int pend = message.indexOf(")", beg);
        				if (beg > 0)
        					tempMessage = message.substring(0, beg);
        				if (pbeg > 0 && pend > 0 && pbeg != pend) {
        					String command = message.substring(pbeg+1, pend);
        					String vcsCheckinOptions = "";
        					if (vcsStruct.getVcsCheckinOptions() != null) 
        					{
        						vcsCheckinOptions = vcsStruct.getVcsCheckinOptions().trim();
        						if (!command.equalsIgnoreCase(vcsCheckinOptions)) {
        							if (vcsCheckinOptions.toLowerCase().contains(command.toLowerCase())) 
        							{
        								command = vcsCheckinOptions;
        							} else {
        								if (command.toLowerCase().contains(vcsCheckinOptions.toLowerCase())) {
        									// no operation required since the command contains all commands
        								} else {
        									command = command + " " + vcsCheckinOptions;
        								}
        							}
        						}
        					}
        					
        					// Add the studio command to any existing command options
        					vcsStruct.setVcsCheckinOptions(command);
        				}
        				message = tempMessage + message.substring(pend+1);
        			}
        		}

        		// Prepend the property VCS_MESSAGE_PREPEND to message
		        String messagePrepend = vcsStruct.getVcsMessagePrepend();
		        if (messagePrepend != null && messagePrepend.length() > 0) {
		        	if (message == null) {
		        		message = messagePrepend;
		        	} else {
		        		message = messagePrepend+" "+message;	        		
		        	}
		        }
		        
				/*****************************************
				 * PERFORM VCS STUDIO FORCED CHECKIN
				 *****************************************/
				CommonUtils.writeOutput("======== BEGIN VCS ["+vcsStruct.getVcsType()+"] Studio Forced Checkin ========",prefix,"-debug2",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("---VCS Arguments:",prefix,"-debug2",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("      resourcePath=             "+resourcePath,prefix,"-debug2",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("      resourceType=             "+resourceType,prefix,"-debug2",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("      Message=                  "+message,prefix,"-debug2",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("      VcsCheckinOptions=        "+vcsStruct.getVcsCheckinOptions(),prefix,"-debug2",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("      VcsCheckinOptionsRequired="+vcsStruct.getVcsCheckinOptionsRequired(),prefix,"-debug2",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("      VcsWorkspace=             "+vcsStruct.getVcsWorkspace(),prefix,"-debug2",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("      VcsWorkspaceProject=      "+vcsStruct.getVcsWorkspaceProject(),prefix,"-debug2",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("      VcsTemp=                  "+vcsStruct.getVcsTemp(),prefix,"-debug2",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("      VcsUser=                  "+vcsStruct.getVcsUsername(),prefix,"-debug2",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("      VcsPassword=              ********",prefix,"-debug2",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
			
				CommonUtils.writeOutput("==============  VCS CHECKOUT =============",prefix,"-debug2",logger,debug1,debug2,debug3);
		        vcs_checkin_checkout__vcs_checkout(resourcePath, resourceType, null, "HEAD",  vcsStruct);

				CommonUtils.writeOutput("==========  VCS CHECKOUT VALIDATE ========",prefix,"-debug2",logger,debug1,debug2,debug3);
		        vcs_checkout_validate(resourcePath, resourceType, vcsStruct);

				CommonUtils.writeOutput("========== DIFFMERGER CHECKIN ============",prefix,"-debug2",logger,debug1,debug2,debug3);
		        diffmerger__checkin(resourcePath, resourceType, vcsStruct);
		        
		        CommonUtils.writeOutput("============== VCS CHECKIN ===============",prefix,"-debug2",logger,debug1,debug2,debug3);
				vcs_checkin_checkout__vcs_checkin(resourcePath, resourceType, message, vcsStruct);

				CommonUtils.writeOutput("====== COMPLETED VCS ["+vcsStruct.getVcsType()+"] Studio Forced Checkin ======",prefix,"-debug2",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
	        
			} catch (CompositeException e) {
			    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
				logger.error("Failed executing "+prefix+".",e);
				throw new CompositeException(e.getMessage(), e);
			}
		}

/******************************************************************************************
 *  PUBLIC IMPLEMENTATION
 *  
 *  VCS SCAN PATH
 *******************************************************************************************/
		/**
		 *  This method handles scanning the Composite path and searching for encoded paths
		 *  that equal or exceed the windows 259 character limit.  If found this routine reports those paths.
		 *  The 259 character limit is only a limitation for windows-based implementations of VCS
		 *  like TFS.  Subversion does not have this issue.
		 *  
		 * @param serverId - target server name
		 * @param vcsConnectionId - VCS Connection property information 
		 * 			[Optional parameter when studio.properties or deploy.properties is being used.  pass in null.]
		 * @param vcsMaxPathLength - a positive integer length from which to compare path lengths found in vcsResourcePathList.  When 0, use the default CommonConstants.maxWindowsPathLen=259.
		 * @param vcsResourcePathList - a comma separated list of CIS resource paths to scan
		 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against. 
		 * 			[Optional parameter when studio.properties or deploy.properties is being used.  pass in null.]
		 * @param pathToServersXML - path to the server values XML
		 * @param vcsUser - the VCS user passed in from the command line
		 * 			[Optional parameter when values are set in studio.properties, deploy.properties or VCSModule.xml.  pass in null.]
		 * @param vcsPassword - the VCS user passed in from the command line
		 * 			[Optional parameter when values are set in studio.properties, deploy.properties or VCSModule.xml.  pass in null.]
		 * @throws CompositeException
		 */
		/* (non-Javadoc)
		 * @see com.cisco.dvbu.ps.deploytool.services.VCSManager#vcsScanPathLength2(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
		 */
		public void vcsScanPathLength2(String serverId, String vcsConnectionId, int vcsMaxPathLength, String vcsResourcePathList, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword)	throws CompositeException {	
			if(logger.isDebugEnabled()){
				logger.debug("Entering VCSManagerImpl.vcsCheckin() with following params "+" serverName: "+serverId+", vcsConnectionId="+vcsConnectionId+", vcsResourcePathList: "+vcsResourcePathList+", pathToVcsXML="+pathToVcsXML+", pathToServersXML: "+pathToServersXML+", vcsUser="+vcsUser);
			}
			try {
				// Set the VCS Module XML Connection Properties in the JVM Environment
				setVCSConnectionProperties(vcsConnectionId, pathToVcsXML);
				vcsConnId = vcsConnectionId;

				// Invoke the command
				vcsScanPathLength(serverId, vcsMaxPathLength, vcsResourcePathList, pathToServersXML, vcsUser, vcsPassword);
				
			} catch (CompositeException e) {
				throw new ApplicationContextException(e.getMessage(), e);
			}			
		}

		/**
		 *  This method handles scanning the Composite path and searching for encoded paths
		 *  that equal or exceed the windows 259 character limit.  If found this routine reports those paths.
		 *  The 259 character limit is only a limitation for windows-based implementations of VCS
		 *  like TFS.  Subversion does not have this issue.
		 *  
		 * @param serverId - target server name
		 * @param vcsMaxPathLength - a positive integer length from which to compare path lengths found in vcsResourcePathList.  When 0, use the default CommonConstants.maxWindowsPathLen=259.
		 * @param vcsResourcePathList - a comma separated list of CIS resource paths to scan
		 * @param pathToServersXML - path to the server values XML
		 * @param vcsUser - the VCS user passed in from the command line
		 * 			[Optional parameter when values are set in studio.properties, deploy.properties or VCSModule.xml.  pass in null.]
		 * @param vcsPassword - the VCS user passed in from the command line
		 * 			[Optional parameter when values are set in studio.properties, deploy.properties or VCSModule.xml.  pass in null.]
		 * @throws CompositeException
		 */
		/*
		 * (non-Javadoc)
		 * @see com.cisco.dvbu.ps.deploytool.services.VCSManager#vcsScanPathLength(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
		 */
		public void vcsScanPathLength(String serverId, int vcsMaxPathLength, String vcsResourcePathList, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
			if(logger.isDebugEnabled()){
				logger.debug("Entering VCSManagerImpl.vcsCheckin() with following params "+" serverName: "+serverId+", vcsResourcePathList: "+vcsResourcePathList+", pathToServersXML: "+pathToServersXML+", vcsUser="+vcsUser);
			}
		
			try {
				String prefix = "vcsScanPathLength";

				// Set the global suppress and debug flags for this class based on properties found in the property file
				setGlobalProperties();
				
				CommonUtils.writeOutput("***** BEGIN COMMAND *****",prefix,"-info",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);

				/*****************************************
				 * VALIDATE COMMAND-LINE VARIABLES
				 *****************************************/
				// Validate the arguments
				if(serverId == null || serverId.trim().length() ==0 || vcsResourcePathList == null || vcsResourcePathList.trim().length() ==0 || pathToServersXML == null || pathToServersXML.trim().length() ==0){
					throw new ValidationException("Invalid Arguments for "+prefix);
				}
				// Validate whether the files exist or not
				if (!CommonUtils.fileExists(pathToServersXML)) {
					throw new ValidationException("File ["+pathToServersXML+"] does not exist.");
				}

				/*****************************************
				 * PRINT OUT COMMAND-LINE VARIABLES
				 *****************************************/
				// Print out Debug input parameters
				CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("---Input Variables from deployment plan file: ",prefix,"-debug2",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("    SERVER_ID=    "+serverId,prefix,"-debug2",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("    SERVER_PATH=  "+pathToServersXML,prefix,"-debug2",logger,debug1,debug2,debug3);

				// Get the server info from the servers.xml file
				CompositeServer serverInfo = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, prefix, logger);
				serverInfo = validateServerProperties(serverInfo);

				/*****************************************
				 * INITIALIZE VCS STRUCTURE VARIABLE
				 *****************************************/
				// Initialize a new VCS structure for passing to the methods
				VcsStruct vcsStruct = (new VCSManagerImpl()).new VcsStruct();
				// Load the VCS structure and decrypt the vcs password -- Retrieve properties from the deploy.properties file
				vcsStruct.loadVcs(prefix, vcsUser, vcsPassword);
				
				// Set the Module Action Objective
				String s1 = (vcsStruct.getVcsUsername() == null) ? "no_User" : "User="+vcsStruct.getVcsUsername();
				System.setProperty("MODULE_ACTION_OBJECTIVE", "SCAN_PATH_LEN : "+s1);

		        /*****************************************
				 * DISPLAY/VALIDATE VCS VARIABLES
				 *****************************************/	        
		        // Validate, Create VCS Workspace and Temp directories
		        vcsStruct.validateVcs(prefix);
		        // Resolve Absolute paths
		        vcsStruct.resolveCanonicalPathsVcs(prefix);
				// Display VCS variables
		        vcsStruct.displayVcs(prefix);

		        /*****************************************
				 * INITIALIZE VARIABLES
				 *****************************************/	        
				String vcsResourceType=null;
				String resourceTypeFilter = null;
				String workspaceProjectDir = vcsStruct.getVcsWorkspaceProject();
				int workspaceProjectDirLen = workspaceProjectDir.length();
				int totalPathsTooLong = 0;
				// If the max path length passed in is zero then set it to the default max windows path length
				if (vcsMaxPathLength == 0)
					vcsMaxPathLength = CommonConstants.maxWindowsPathLen;
				// If the max path length passed in is greater than the max windows path length then set it to the default max windows path length
				if (vcsMaxPathLength > CommonConstants.maxWindowsPathLen)
					vcsMaxPathLength = CommonConstants.maxWindowsPathLen;

				/*****************************************
				 * PRINT OUT VCS INFO VARIABLES
				 *****************************************/
				CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("---VCS Module ID Arguments:",prefix,"-info",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("      vcsResourcePathList=            "+vcsResourcePathList,prefix,"-info",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("      VCS_WORKSPACE_PROJECT=          "+vcsStruct.getVcsWorkspaceProject(),prefix,"-info",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("      VCS_WORKSPACE_PROJECT Length=   "+workspaceProjectDirLen,prefix,"-info",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("     Maximum Path Length to verify=   "+vcsMaxPathLength,prefix,"-info",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
				
				//--------------------------------------------------------------
				// vcs scan path length
				//--------------------------------------------------------------	
				// Extract individual paths from the resource path list
				StringTokenizer st = new StringTokenizer(vcsResourcePathList, ",");
				while(st.hasMoreTokens()) 
				{
					// Get the next path from the list
					String resourcePath = st.nextToken();
					int pathsTooLong = 0;

					if (resourcePath != null && resourcePath.length() > 0) 
					{
						// Get the resource type
						vcsResourceType = getResourceManager().getResourceType(serverId, resourcePath, pathToServersXML);
												
						ResourceList resourceList = getResourceManager().getResourcesFromPath(serverId, resourcePath, vcsResourceType, resourceTypeFilter, DetailLevel.FULL.name(), pathToServersXML);
			
						if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty()) 
						{
							CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
							CommonUtils.writeOutput("CIS resource to scan:  type="+vcsResourceType+"  path="+resourcePath,prefix,"-info",logger,debug1,debug2,debug3);
//							CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);

							for (Resource resource : resourceList.getResource()) 
							{
								// Encode the VCS resource path
								String vcsResourcePath = ResourceNameCodec.encode(resource.getPath());
								vcsResourcePath = vcsResourcePath.replaceAll("_002F", "/");
								
								// Get the length of the encoded path
								int vcsResourcePathLen = vcsResourcePath.length();
								int vcsTotalPathLen = workspaceProjectDirLen + vcsResourcePathLen;
								
								// Validate the length against the maximum length allowed
								if (vcsTotalPathLen >= vcsMaxPathLength) {
									CommonUtils.writeOutput("      TotalPathLen="+CommonUtils.rpad(Integer.toString(vcsTotalPathLen), 4, " ")+"   EncodedPathLen="+CommonUtils.rpad(Integer.toString(vcsResourcePathLen), 4, " ")+"  ResourceType="+resource.getType()+"  EncodedPath="+vcsResourcePath+"  *****OriginalPath*****="+resource.getPath(),prefix,"-info",logger,debug1,debug2,debug3);
									++pathsTooLong;
									++totalPathsTooLong;
								}
							}
							if (pathsTooLong == 0)
								CommonUtils.writeOutput("      No paths found exceeding maximum.",prefix,"-info",logger,debug1,debug2,debug3);
						}
						CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
					}
				}
				
				// Display the final message
				CommonUtils.writeOutput("Final Scan Report:",prefix,"-info",logger,debug1,debug2,debug3);				
				CommonUtils.writeOutput("      CIS Paths found >= "+vcsMaxPathLength+" chars.  Total="+totalPathsTooLong,prefix,"-info",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("      Note: Subversion has no limitations with long path names.",prefix,"-info",logger,debug1,debug2,debug3);				
				CommonUtils.writeOutput("      Note: TFS implementation on windows is affected by long path names.",prefix,"-info",logger,debug1,debug2,debug3);				
				
			} catch (CompositeException e) {
				throw new ApplicationContextException(e.getMessage(), e);
			}				
		}

/******************************************************************************************
 *  PRIVATE IMPLEMENTATION
 *  
 *  VCS INITIALIZATION
 *  
 *  This method handles VCS file system initialization for PDTool and PDTool Studio.
 *  It also interfaces with the VCS server and initialize the file system with a local 
 *  copy of the VCS Repository as configured by the configuration property file (deploy.properties).
 * 
 *******************************************************************************************/

		// Initialize the VCS workspace
		private void vcsInitWorkspaceCommon(String vcsUser, String vcsPassword) throws CompositeException {
		/***************************************************************************************************************
		 * Initialize the local VCS Workspace by linking and checking out CIS resources from the VCS server
		 * 
		 * @param vcsUser - the VCS user passed in from the command-line invocation
		 * @param vcsPassword - the VCS password passed in from the command-line invocation
		 ***************************************************************************************************************/

			// Initialize variables
			String prefix = "vcsInitWorkspaceCommon";
		    List<String> argList = new ArrayList<String>();
		    List<String> envList = new ArrayList<String>();
		    boolean initArgList = true;
		    boolean preserveQuotes = false;
		    String command = null;
		    String arguments = null;
		    String execFromDir = null;
		    String commandDesc = null;
		    boolean initializeWorkspaceLink = true;
		    
			try {
				/*****************************************
				 * INITIALIZE VARIABLES
				 *****************************************/
				// Trim input variables to remove spaces
				vcsUser = vcsUser.trim();
				vcsPassword = vcsPassword.trim();
				
				/*****************************************
				 * INITIALIZE DEBUG LOG EXECUTION
				 *****************************************/
		        CommonUtils.writeOutput("-------------------------------------------------------------------------------",prefix,"-info",logger,debug1,debug2,debug3);
		        CommonUtils.writeOutput("***** BEGIN VCS WORKSPACE INITIALIZATION *****",prefix,"-info",logger,debug1,debug2,debug3);
		        CommonUtils.writeOutput("-------------------------------------------------------------------------------",prefix,"-info",logger,debug1,debug2,debug3);
				
				/*****************************************
				 * INITIALIZE VCS STRUCTURE VARIABLE
				 *****************************************/
				// Initialize a new VCS structure for passing to the methods
				VcsStruct vcsStruct = (new VCSManagerImpl()).new VcsStruct();
				// Load the VCS structure and decrypt the vcs password -- Retrieve properties from the property file
				vcsStruct.loadVcs(prefix, vcsUser, vcsPassword);

				// Set the Module Action Objective
				String s1 = (vcsStruct.getVcsUsername() == null) ? "no_User" : "User="+vcsStruct.getVcsUsername();
				System.setProperty("MODULE_ACTION_OBJECTIVE", "INIT_WORKSPACE : "+s1);
				

		        CommonUtils.writeOutput("............................................",prefix,"-info",logger,debug1,debug2,debug3);
		        CommonUtils.writeOutput("Initialize workspace for VCS_TYPE="+vcsStruct.getVcsType(),prefix,"-info",logger,debug1,debug2,debug3);
		        CommonUtils.writeOutput("............................................",prefix,"-info",logger,debug1,debug2,debug3);
		        	        
		        /*****************************************
				 * DISPLAY/VALIDATE VCS VARIABLES
				 *****************************************/	        
		        // Validate, Create VCS Workspace and Temp directories
		        vcsStruct.validateVcs(prefix);
		        // Resolve Absolute paths
		        vcsStruct.resolveCanonicalPathsVcs(prefix);
				// Display VCS variables
		        vcsStruct.displayVcs(prefix);

				/**********************************************************
				 * [CVS] vcsInitWorkspaceCommon:
				 *      INIT VCS WORKSPACE FOR CONCURRENT VERSIONS SYSTEMS
				 **********************************************************/
				if (vcsStruct.getVcsType().equalsIgnoreCase("CVS")) {
														   
			        // Explicitly remove the workspace directory
			        removeDirectory(prefix, vcsStruct.getVcsWorkspace());
			        
			        // Only remove the VCS Temp directory with PD Tool as PD Tool Studio VCS Temp is managed by Studio
			        if (!pdToolStudio) {
			        	// Explicitly remove the workspace temp directory
			        	removeDirectory(prefix, vcsStruct.getVcsTemp());	        	
			        }
			        
			        // Create the workspace directory
			        createDirectory(prefix, vcsStruct.getVcsWorkspace());

			        // Only create the VCS Temp directory with PD Tool as PD Tool Studio VCS Temp is managed by Studio
			        if (!pdToolStudio) {
			        	// Create the workspace temp directory
			        	createDirectory(prefix, vcsStruct.getVcsTemp());
			        }
			        
			        // Set the directory to execute from the workspace directory
					execFromDir = vcsStruct.getVcsWorkspace();
					
					// Set the VCS command
					command = vcsStruct.getVcsExecCommand();
					
					// Link the VCS Repository URL and Project Root to the local workspace
					// cvs import -m "linking workspace to the VCS repository" ${VCS_PROJECT_ROOT} INITIAL start ${VCS_WORKSPACE_INIT_LINK_OPTIONS}
					arguments=" import -m \"Linking workspace to the VCS repository\" "+vcsStruct.getVcsProjectRoot()+" INITIAL start" + " " + vcsStruct.getVcsWorkspaceInitLinkOptions();
					commandDesc = "    Linking local workspace to VCS Repository...";
					
					CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());

					// Check out the repository to the local workspace
					//   cvs co ${VCS_PROJECT_ROOT} ${VCS_WORKSPACE_INIT_GET_OPTIONS}
					arguments=" co "+vcsStruct.getVcsProjectRoot() + " " + vcsStruct.getVcsWorkspaceInitGetOptions();
					commandDesc = "    Checking out the repository to the local workspace...";

					// Print out command
					CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	 
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
				}
				
				/**********************************************************
				 * [P4] vcsInitWorkspaceCommon:
				 *      INIT VCS WORKSPACE FOR PERFORCE
				 **********************************************************/
				if (vcsStruct.getVcsType().equalsIgnoreCase("P4")) {
		
					try {			    
					    // ------------------------------------------------------------------
						// Delete the perforce workspace (removes depot relationship to this workspace)
						// ------------------------------------------------------------------

						// Set the directory to execute from the workspace home directory
						execFromDir = ".";
						
						// Set the VCS Command
						command = vcsStruct.getVcsExecCommand();

						// Delete the perforce workspace link
						// p4 client -f -d ${VCS_WORKSPACE_NAME}
						// mtinius: 2012-01-26: removed -f flag from the command and made it a perforce option (P4DEL_LINK_OPTIONS)
						//	   orig: arguments=" client -f -d "+vcsStruct.getVcsWorkspaceName(); 
						String P4DEL_LINK_OPTIONS = CommonUtils.getFileOrSystemPropertyValue(propertyFile,"P4DEL_LINK_OPTIONS");
						if (P4DEL_LINK_OPTIONS != null && P4DEL_LINK_OPTIONS.length() > 0) {
							// Remove any duplicate "-d" options before appending to command
							String regex = "-d"; 
							String options = P4DEL_LINK_OPTIONS.replaceAll(regex, ""); // make sure the user does not place another -d flag in the options 
							// Append the options to the command
							arguments=" client "+options+" -d "+vcsStruct.getVcsWorkspaceName(); 
						} else {
							arguments=" client -d "+vcsStruct.getVcsWorkspaceName(); 
						} 
						commandDesc = "    Delete the Perforce workspace link...";

						// Print out command
						CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, false, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
				
					} catch (CompositeException e) {
						if (!e.getMessage().contains("Client '"+vcsStruct.getVcsWorkspaceName()+"' doesn't exist.")) {
							throw new ApplicationException(e);
						}
					}

				    // ------------------------------------------------------------------
					// Physically remove and recreate the workspace from the machine
					// ------------------------------------------------------------------

			        // Explicitly remove the workspace directory
			        removeDirectory(prefix, vcsStruct.getVcsWorkspace());
			        
			        // Only remove the VCS Temp directory with PD Tool as PD Tool Studio VCS Temp is managed by Studio
			        if (!pdToolStudio) {
			        	// Explicitly remove the workspace temp directory
			        	removeDirectory(prefix, vcsStruct.getVcsTemp());	        	
			        }
			        
			        // Create the workspace directory
			        createDirectory(prefix, vcsStruct.getVcsWorkspace());

			        // Only create the VCS Temp directory with PD Tool as PD Tool Studio VCS Temp is managed by Studio
			        if (!pdToolStudio) {
			        	// Create the workspace temp directory
			        	createDirectory(prefix, vcsStruct.getVcsTemp());
			        }
			        
					// Create the VCS Workspace Project directory
			        //   e.g: vcsWorkspaceProject:  D:/PDTool/p4_workspace/cis_objects
					createDirectory(prefix, vcsStruct.getVcsWorkspaceProject());

				    // ------------------------------------------------------------------
					// Link the Perforce depot to the local workspace name and directory
					// ------------------------------------------------------------------
					commandDesc = "    Linking local workspace to Perforce Depot...";
					argList = new ArrayList<String>();
					String commandOption = "";
					if (File.separatorChar == '/') {
						// Setup UNIX processing
						command = "/bin/sh"; // UNIX Shell
						commandOption = "-c";
						execFromDir = vcsStruct.getVcsWorkspace();
						// Execute this format so that an editor thread is not spawned.  Perforce wants the user to accept the template before proceeding.
					    // "/bin/sh" -c "/usr/bin/p4" client -o | "/usr/bin/p4" client -i
						//
						// Execute the p4 client to send the template to standard out.  Redirect the output to the p4 client that accepts input from standard-in.
						// This is done so that the p4 client command does not open a text editor by default as this will not show up in the UNIX process space like it does in Windows.
						// p4 client -o ${VCS_WORKSPACE_INIT_LINK_OPTIONS} | p4 client -i ${VCS_WORKSPACE_INIT_LINK_OPTIONS}
						arguments=vcsStruct.getVcsExecCommand() + " client -o " + vcsStruct.getVcsWorkspaceInitLinkOptions()+ " | " + 
								  vcsStruct.getVcsExecCommand() + " client -i " + vcsStruct.getVcsWorkspaceInitLinkOptions(); 
						
					    // Add the command arguments into a list
						argList.add(command);
						argList.add(commandOption);
						argList.add(arguments);
						
					    // Parse the environment arguments into a list
					    envList = CommonUtils.getArgumentsList(envList, true, vcsStruct.getVcsEnvironment(), "|");

						// Print out command
						CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+commandOption+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);

						// Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());

					} else {
					/*
						// Setup windows processing (processBuilder throws an IllegalArguments exception with the code that is commented out.
						command = "cmd.exe";  // Windows Command cmd.exe /C - Carries out the command specified by string and then terminates
						commandOption = "/C";
					    // "D:/dev/vcs/perforce/p4.exe" client -o | "D:/dev/vcs/perforce/p4.exe" client -i
						arguments="\""+vcsStruct.getVcsExecCommand()+"\" client -o | \""+vcsStruct.getVcsExecCommand()+"\" client -i"; 
					*/
						// Setup the perforce command.  Put double quotes around a path containing spaces or a full path.
					    // D:/dev/vcs/perforce/p4.exe or /usr/bin/p4
						command = vcsStruct.getVcsExecCommand();

				        // Set the directory to execute from the workspace directory
						execFromDir = vcsStruct.getVcsWorkspace();

						// Link the Perforce depot client to the current directory
						// p4 client
						// or
						// p4 client workspacename ${VCS_WORKSPACE_INIT_LINK_OPTIONS}
						arguments=" client "+vcsStruct.getVcsWorkspaceName() + " " + vcsStruct.getVcsWorkspaceInitLinkOptions();
					
						// Print out command
						CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					}

				    // ------------------------------------------------------------------
					// Synchronize the perforce depot with the local workspace by
					//   copying files from the depot to the local workspace directory
					// ------------------------------------------------------------------
					commandDesc = "    Synchronizing workspace with Perforce Depot...";

					// Setup the perforce command.
				    // D:/dev/vcs/perforce/p4.exe or /usr/bin/p4
					command = vcsStruct.getVcsExecCommand();

			        // Set the directory to execute from the workspace directory
					//   e.g: vcsWorkspaceProject:  D:/PDTool/p4_workspace/cis_objects
					execFromDir = vcsStruct.getVcsWorkspaceProject();
					
					// Synchronize (Check out) the repository to the local workspace
					// p4 sync ${VCS_WORKSPACE_INIT_GET_OPTIONS}
					arguments=" sync" + " " + vcsStruct.getVcsWorkspaceInitGetOptions();

					// Print out command
					CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);

					// Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	    

				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
				}
				
				/**********************************************************
				 * [SVN] vcsInitWorkspaceCommon:
				 *      INIT VCS WORKSPACE FOR SUBVERSION
				 **********************************************************/
				if (vcsStruct.getVcsType().equalsIgnoreCase("SVN")) {
						   
			        // Explicitly remove the workspace directory
			        removeDirectory(prefix, vcsStruct.getVcsWorkspace());
			        
			        // Only remove the VCS Temp directory with PD Tool as PD Tool Studio VCS Temp is managed by Studio
			        if (!pdToolStudio) {
			        	// Explicitly remove the workspace temp directory
			        	removeDirectory(prefix, vcsStruct.getVcsTemp());	        	
			        }
			        
			        // Set the workspace directory path to the workspace + vcs project root (e.g. cis_objects)
			        String workspaceDir = (vcsStruct.getVcsWorkspace()+"/"+vcsStruct.getVcsProjectRoot()).replaceAll("//", "/");
			        
			        // Create the workspace directory
			        createDirectory(prefix, workspaceDir);

			        // Only create the VCS Temp directory with PD Tool as PD Tool Studio VCS Temp is managed by Studio
			        if (!pdToolStudio) {
			        	// Create the workspace temp directory
			        	createDirectory(prefix, vcsStruct.getVcsTemp());
			        }
			        
			        // Set the directory to execute from the workspace directory
					execFromDir = workspaceDir;
					
					// Set the VCS command
					command = vcsStruct.getVcsExecCommand();
				
					// If the special option "-IGNORE_INIT_LINK" is set then do not execute the initialization linking of the workspace.
					//   Otherwise maintain backward compatibility.
					if (vcsStruct.getVcsWorkspaceInitLinkOptions() != null && vcsStruct.getVcsWorkspaceInitLinkOptions().contains("-IGNORE_INIT_LINK"))
						initializeWorkspaceLink = false;
					
					// To maintain backward compatibility, execute the VCS workspace initialization to link the workspace with the repository.
					//   However, this requires Read/Write access to the repository folders.
					if (initializeWorkspaceLink) {
						// Link the VCS Repository URL and Project Root to the local workspace						
						// svn import -m "linking workspace to the VCS repository" . "${VCS_REPOSITORY_URL}/${VCS_PROJECT_ROOT}" ${SVN_OPTIONS} ${SVN_AUTH} ${VCS_WORKSPACE_INIT_LINK_OPTIONS}
						arguments=" import . "+vcsStruct.getVcsRepositoryUrl()+"/"+vcsStruct.getVcsProjectRoot()+" --message Linking_workspace_to_VCS_repository "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsWorkspaceInitLinkOptions();
	
						// Print out command
						commandDesc = "    Linking local worksapce to VCS Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					}
					
					// Check out the repository to the local workspace						
					// svn co "${VCS_REPOSITORY_URL}/${VCS_PROJECT_ROOT}" ${SVN_OPTIONS} ${SVN_AUTH} ${VCS_WORKSPACE_INIT_GET_OPTIONS}
					arguments=" co "+vcsStruct.getVcsRepositoryUrl()+"/"+vcsStruct.getVcsProjectRoot()+" "+execFromDir+" "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsWorkspaceInitGetOptions();
					// (prevous code): arguments=" co "+vcsStruct.getVcsRepositoryUrl()+"/"+vcsStruct.getVcsProjectRoot()+" "+vcsStruct.getVcsOptions();

					// Print out command
					commandDesc = "    Checking out the repository to the local workspace...";
					CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
				}

				/**********************************************************
				 * [TFS] vcsInitWorkspaceCommon:
				 *      INIT VCS WORKSPACE FOR MICROSOFT TEAM FOUNDATION SERVER 2010, 2012, 2013
				 **********************************************************/
				if (vcsStruct.getVcsType().equalsIgnoreCase("TFS2010") || 
					vcsStruct.getVcsType().equalsIgnoreCase("TFS2012") || 
					vcsStruct.getVcsType().equalsIgnoreCase("TFS2013") ) 
				{
			        // Set the directory to execute from the workspace home directory
					execFromDir = vcsStruct.getVcsWorkspaceHome();
					
					// Set the VCS Command
					command = vcsStruct.getVcsExecCommand();	

			        // Bypass deleting and mapping the workspace when TFS_USE_EXISTING_WORKSPACE=true.  Only perform checkout to get latest resources.
			        if (vcsStruct.getVcsUseExistingWorkdspace().equals("true")) {
			        	if (logger.isInfoEnabled())
			        		logger.info("Info: Workspace will not be unmapped or deleted when variable \"TFS_USE_EXISTING_WORKSPACE=true\" is set.");

				        /********************
				         * WORKSPACE EXISTS
				         ********************/
					    // Retrieve workspace status
			        	// tf.cmd workspaces -collection:http://hostname:8080/tfs/DefaultCollection TFSww7 -noprompt -login:username,password
					    // tf.cmd workspaces -collection:${TFS_SERVER_URL} ${VCS_WORKSPACE_NAME} -noprompt -login:${VCS_USERNAME},${VCS_PASSWORD}
						arguments=" workspaces -collection:" + vcsStruct.getVcsRepositoryUrl() + " " + vcsStruct.getVcsWorkspaceName() + " -noprompt "+vcsStruct.getVcsOptions();
						commandDesc = "    Check if workspace exists...";

						// Print out command
						CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");
					    
					    // Execute the command line
					    StringBuilder stdout = getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					    
						// Determine if there is any status messages containing an "add" status which indicates a pending change that is not committed.
					    // This results from a file or folder that is committed but the parent folder or folders have not yet been committed.
					    if (stdout != null) {
							boolean workspaceInitialized = false;
					    	try {
						    	String vcsout = stdout.toString();
						    	StringReader is = new StringReader(vcsout);

								BufferedReader reader = new BufferedReader(is);
								String line = null;
								int lnCount = 0;
								// Iterate through the orchestration property file
								while ( (line = reader.readLine()) != null) {
									lnCount++;
									// Make sure the workspace name contains a space so that the search yields a unique entry and not a name within a name.
							    	int idx = line.indexOf(vcsStruct.getVcsWorkspaceName()+" "); // "TFSww7 "
							    	
							    	// Continue if the workspace exists in TFS.
									if (idx >= 0 && !workspaceInitialized) 
									{
										// Continue as long as the line does not contain the message "No workspace matching " + workspace name
								    	if (line.indexOf(CommonConstants.TFS_MSG_NO_WORKSPACE_MATCHING+" "+vcsStruct.getVcsWorkspaceName()) < 0) 
								    	{
									        // Set the execution directory
									        //	 e.g: vcsWorkspaceProject:  D:/PDTool/tfs_workspace/cis_objects
									        execFromDir = vcsStruct.getVcsWorkspaceProject();
	
									        // Create the workspace directory
									        createDirectory(prefix, vcsStruct.getVcsWorkspace());
	
											// Create the VCS Workspace Project directory
									        //	 e.g: vcsWorkspaceProject:  D:/PDTool/tfs_workspace/cis_objects
											createDirectory(prefix, vcsStruct.getVcsWorkspaceProject());
	
											// Only create the VCS Temp directory with PD Tool as PD Tool Studio VCS Temp is managed by Studio
									        if (!pdToolStudio) {
									        	// Create the workspace temp directory
									        	createDirectory(prefix, vcsStruct.getVcsTemp());
									        }
	
									        /********************
									         * CHECKOUT WORKSPACE
									         ********************/
											// Check out the repository to the local workspace	    
										    // Retrieves a read-only copy of a file from the Team Foundation Server to the workspace and creates folders on disk to contain it.
										    // tf.cmd get -all -recursive ${TFS_SERVER_URL} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS} ${VCS_WORKSPACE_INIT_GET_OPTIONS}
											arguments=" get -all -recursive " + vcsStruct.getTfsServerUrl() + " -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsWorkspaceInitGetOptions();
											commandDesc = "    Checking out the repository to the local workspace...";
	
											// Print out command
											CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
											CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
											CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
											
										    // Parse the command arguments into a list
										    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
										    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");
										    
										    // Execute the command line
										    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
										    
										    // Set the workspace initialized flag to true
										    workspaceInitialized = true;
								    	}
									}
								}
					    	} 
					    	catch (Exception e) {
								throw new CompositeException(e.getMessage(), e);
					    	}
					    	if (!workspaceInitialized) {
								throw new ApplicationException("ERROR: Workspace Initialization error.  Workspace="+vcsStruct.getVcsWorkspaceName()+" not found for user="+vcsStruct.getVcsUsername()+" when \"TFS_USE_EXISTING_WORKSPACE=true\".   Map the workspace for this user="+vcsStruct.getVcsUsername()+" and computer to resolve the issue or set \"TFS_USE_EXISTING_WORKSPACE=false\".");
					    	}
					    } // if (stdout != null)
					    else
					    {
							throw new ApplicationException("ERROR: Workspace Initialization error.  No workspaces found for user="+vcsStruct.getVcsUsername()+" when \"TFS_USE_EXISTING_WORKSPACE=true\".");
					    }
			        } 
			        // Perform the full workspace initialization when TFS_USE_EXISTING_WORKSPACE=false
			        else  
			        {
						try {
					        /********************
					         * DELETE LINK
					         ********************/
							// Delete the TFS workspace link
							// tf workspace /delete ${VCS_WORKSPACE_NAME} ${VCS_OPTIONS}
							// Note: All the tfs parameters can be preceded by / or -. While / works only on Windows,
							// - works on both Windows and non-Windows platform. So for the sake of platform independent code
							// it is changed to -
							arguments="  workspace -delete -collection:"+vcsStruct.getVcsRepositoryUrl()+ " " +vcsStruct.getVcsWorkspaceName()+" -noprompt "+vcsStruct.getVcsOptions(); 
							commandDesc = "    Delete the TFS workspace link...";
	
							// Print out command
							CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
							CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
							CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
							
						    // Parse the command arguments into a list
						    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
						    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");
						    
						    // Execute the command line
						    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
							
						} catch (CompositeException e) {
							boolean errorFound = false;
							String ignoreMessage = "";
							if (e.getMessage().contains("does not exist")) {
								errorFound = true;
								ignoreMessage = "does not exist";
							}
							if (e.getMessage().contains("could not be found")) {
								errorFound = true;
								ignoreMessage = "could not be found";
							}
							if (e.getMessage().contains("Unable to determine the source control server")) {
								errorFound = true;
								ignoreMessage = "Unable to determine the source control server";
							}
							if (errorFound) {
								//continue processing, this is OK
								if (logger.isErrorEnabled()) {
									logger.info(prefix+"::  "+"Warning: Error ignored.  Error message matches ignore message string=\""+ignoreMessage+"\"");
							    }
							} else {
								throw new ApplicationException(e);
							}
						}
			        
						try {
	
					        /********************
					         * UNMAP WORKSPACE
					         ********************/
							// Delete the TFS workfold link
							// tf workfold -unmap  -collection:{VCS_REPOSITORY_URL} ${VCS_WORKSPACE_DIR} -workspace:${VCS_WORKSPACE_NAME} ${VCS_OPTIONS}
							//    e.g: vcsWorkspaceProject:  D:/PDTool/tfs_workspace/cis_objects
							arguments="  workfold -unmap -collection:"+vcsStruct.getVcsRepositoryUrl()+ " " +vcsStruct.getVcsWorkspaceProject() + " -workspace:" +
								vcsStruct.getVcsWorkspaceName() + " -noprompt "+vcsStruct.getVcsOptions(); 
							commandDesc = "    Unmap the TFS workfold link...";
	
							// Print out command
							CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
							CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
							CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
							
						    // Parse the command arguments into a list
						    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
						    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");
						    
						    // Execute the command line
						    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
							
						} catch (CompositeException e) {
							boolean errorFound = false;
							String ignoreMessage = "";
							if (e.getMessage().contains("No working folder assigned")) {
								errorFound = true;
								ignoreMessage = "No working folder assigned";
							}
							if (e.getMessage().contains("could not be found")) {
								errorFound = true;
								ignoreMessage = "could not be found";
							}
							if (e.getMessage().contains("Unable to determine the source control server")) {
								errorFound = true;
								ignoreMessage = "Unable to determine the source control server";
							}
							if (errorFound) {
								//continue processing, this is OK
								if (logger.isErrorEnabled()) {
									logger.info(prefix+"::  "+"Warning: Error ignored.  Error message matches ignore message string=\""+ignoreMessage+"\"");
							    }
							} else {
								throw new ApplicationException(e);
							}
						}
						
				        /*********************
				         * PREPARE DIRECTORIES
				         *********************/
				        // Explicitly remove the workspace directory
				        removeDirectory(prefix, vcsStruct.getVcsWorkspace());
				        
				        // Only remove the VCS Temp directory with PD Tool as PD Tool Studio VCS Temp is managed by Studio
				        if (!pdToolStudio) {
				        	// Explicitly remove the workspace temp directory
				        	removeDirectory(prefix, vcsStruct.getVcsTemp());	        	
				        }
				        			        
				        // Create the workspace directory
				        createDirectory(prefix, vcsStruct.getVcsWorkspace());
	
						// Create the VCS Workspace Project directory
				        //	 e.g: vcsWorkspaceProject:  D:/PDTool/tfs_workspace/cis_objects
						createDirectory(prefix, vcsStruct.getVcsWorkspaceProject());
	
						// Only create the VCS Temp directory with PD Tool as PD Tool Studio VCS Temp is managed by Studio
				        if (!pdToolStudio) {
				        	// Create the workspace temp directory
				        	createDirectory(prefix, vcsStruct.getVcsTemp());
				        }			        
	
						// Link the VCS Repository URL and Project Root to the local workspace		
						/*
						 * Creating a new workspace
						 * http://msdn.microsoft.com/en-US/library/y901w7se(v=VS.80).aspx
							Before you can add files to the source control server or check out items on the server in order to edit them, 
							you must create a workspace or associate an existing one with the current directory. 
							For more information, see How to: Create a Workspace.
							http://msdn.microsoft.com/en-US/library/ms181384(v=VS.80).aspx
	
							To make the current directory a working folder for an existing workspace on your computer, 
							type tf workspace workspacename, where workspacename is the name of the existing workspace, 
							click click here to enter a new working folder, type the server path of which you want to map 
							the current directory in the Team Foundation source control server Folder box, 
							type the current directory in the Local Folder box, and then click OK.
	
							When you create a new workspace, you can specify a template workspace as part of the /new option. 
							When you specify a template workspace, Team Foundation creates a new workspace on the current computer, 
							sets the owner to the current owner, and replicates the following workspace properties into the new workspace 
							from the template workspace: mappings and comment. If no name is specified, a name based on the current computer name is used. 
							When you create a workspace using a template, Team Foundation does not retrieve the files to which it maps from the server. 
							Use the Get Command to synchronize the new workspace with the latest version on the server.
						 */
	
				        // Set the execution directory
				        //	 e.g: vcsWorkspaceProject:  D:/PDTool/tfs_workspace/cis_objects
				        execFromDir = vcsStruct.getVcsWorkspaceProject();
				        
				        /********************
				         * LINK WORKSPACE
				         ********************/
						// tf.cmd workspace -new -collection:${VCS_REPOSITORY_URL} ${VCS_WORKSPACE_NAME} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS} ${VCS_WORKSPACE_INIT_NEW_OPTIONS}
				        //	 e.g: vcsWorkspaceProject:  D:/PDTool/tfs_workspace/cis_objects
						arguments=" workspace -new -collection:"+vcsStruct.getVcsRepositoryUrl()+" "+vcsStruct.getVcsWorkspaceName()+" -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsWorkspaceInitNewOptions();
	
						commandDesc = "    Linking local worksapce to VCS Repository...";
	
						// Print out command
						CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					    
				        /********************
				         * MAP WORKSPACE
				         ********************/
					    // tf.cmd workfold -map -collection:{VCS_REPOSITORY_URL} ${TFS_SERVER_URL} ${VCS_WORKSPACE_DIR}+"/"+${VCS_PROJECT_ROOT} -workspace:${VCS_WORKSPACE_NAME} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS} ${VCS_WORKSPACE_INIT_LINK_OPTIONS}
					    //	 e.g: vcsWorkspaceProject:  D:/PDTool/tfs_workspace/cis_objects
						arguments="  workfold -map -collection:"+vcsStruct.getVcsRepositoryUrl()+ " " +vcsStruct.getTfsServerUrl() + " " + vcsStruct.getVcsWorkspaceProject() +
							 " -workspace:" + vcsStruct.getVcsWorkspaceName() + " -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsWorkspaceInitLinkOptions();
	
						commandDesc = "    Mapping local workfold to VCS Repository...";
	
						// Print out command
						CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
	
				        /********************
				         * CHECKOUT WORKSPACE
				         ********************/
						// Check out the repository to the local workspace	    
					    // Retrieves a read-only copy of a file from the Team Foundation Server to the workspace and creates folders on disk to contain it.
					    // tf.cmd get -all -recursive ${TFS_SERVER_URL} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS} ${VCS_WORKSPACE_INIT_GET_OPTIONS}
						arguments=" get -all -recursive " + vcsStruct.getTfsServerUrl() + " -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsWorkspaceInitGetOptions();
						commandDesc = "    Checking out the repository to the local workspace...";
	
						// Print out command
						CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
			        } // End: if (pdToolStudio && vcsStruct.getVcsUseExistingWorkdspace().equals("true")) {
				}
				
				/**********************************************************
				 * [TFS] vcsInitWorkspaceCommon:
				 *      INIT VCS WORKSPACE FOR MICROSOFT TEAM FOUNDATION SERVER 2005
				 **********************************************************/
				if (vcsStruct.getVcsType().equalsIgnoreCase("TFS2005")) {
					
			        // Set the directory to execute from the workspace home directory
		        	//execFromDir = ".";
					execFromDir = vcsStruct.getVcsWorkspaceHome();
					
					// Set the VCS Command
					command = vcsStruct.getVcsExecCommand();

			        // Bypass deleting and mapping the workspace when TFS_USE_EXISTING_WORKSPACE=true.  Only perform checkout to get latest resources.
			        if (vcsStruct.getVcsUseExistingWorkdspace().equals("true")) {
			        	if (logger.isInfoEnabled())
			        		logger.info("Info: Workspace will not be unmapped or deleted when variable \"TFS_USE_EXISTING_WORKSPACE=true\" is set.");

				        /********************
				         * WORKSPACE EXISTS
				         ********************/
					    // Retrieve workspace status
			        	// tf.cmd workspaces -collection:http://hostname:8080/tfs/DefaultCollection TFSww7 -noprompt -login:username,password
					    // tf.cmd workspaces -collection:${TFS_SERVER_URL} ${VCS_WORKSPACE_NAME} -noprompt -login:${VCS_USERNAME},${VCS_PASSWORD}
						arguments=" workspaces -collection:" + vcsStruct.getVcsRepositoryUrl() + " " + vcsStruct.getVcsWorkspaceName() + " -noprompt "+vcsStruct.getVcsOptions();
						commandDesc = "    Check if workspace exists...";

						// Print out command
						CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");
					    
					    // Execute the command line
					    StringBuilder stdout = getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					    
						// Determine if there is any status messages containing an "add" status which indicates a pending change that is not committed.
					    // This results from a file or folder that is committed but the parent folder or folders have not yet been committed.
					    if (stdout != null) {
							boolean workspaceInitialized = false;
					    	try {
						    	String vcsout = stdout.toString();
						    	StringReader is = new StringReader(vcsout);

								BufferedReader reader = new BufferedReader(is);
								String line = null;
								int lnCount = 0;
								// Iterate through the orchestration property file
								while ( (line = reader.readLine()) != null) {
									lnCount++;
									// Make sure the workspace name contains a space so that the search yields a unique entry and not a name within a name.
							    	int idx = line.indexOf(vcsStruct.getVcsWorkspaceName()+" "); // "TFSww7 "
							    	
							    	// Continue if the workspace exists in TFS.
									if (idx >= 0 && !workspaceInitialized) 
									{
										// Continue as long as the line does not contain the message "No workspace matching " + workspace name
								    	if (line.indexOf(CommonConstants.TFS_MSG_NO_WORKSPACE_MATCHING+" "+vcsStruct.getVcsWorkspaceName()) < 0) 
								    	{
									        // Set the execution directory
									        //	 e.g: vcsWorkspaceProject:  D:/PDTool/tfs_workspace/cis_objects
									        execFromDir = vcsStruct.getVcsWorkspaceProject();
	
									        // Create the workspace directory
									        createDirectory(prefix, vcsStruct.getVcsWorkspace());
	
											// Create the VCS Workspace Project directory
									        //	 e.g: vcsWorkspaceProject:  D:/PDTool/tfs_workspace/cis_objects
											createDirectory(prefix, vcsStruct.getVcsWorkspaceProject());
	
											// Only create the VCS Temp directory with PD Tool as PD Tool Studio VCS Temp is managed by Studio
									        if (!pdToolStudio) {
									        	// Create the workspace temp directory
									        	createDirectory(prefix, vcsStruct.getVcsTemp());
									        }
	
									        /********************
									         * CHECKOUT WORKSPACE
									         ********************/
											// Check out the repository to the local workspace	    
										    // Retrieves a read-only copy of a file from the Team Foundation Server to the workspace and creates folders on disk to contain it.
										    // tf.cmd get -all -recursive ${TFS_SERVER_URL} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS} ${VCS_WORKSPACE_INIT_GET_OPTIONS}
											arguments=" get -all -recursive " + vcsStruct.getTfsServerUrl() + " -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsWorkspaceInitGetOptions();
											commandDesc = "    Checking out the repository to the local workspace...";
	
											// Print out command
											CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
											CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
											CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
											
										    // Parse the command arguments into a list
										    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
										    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");
										    
										    // Execute the command line
										    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
										    
										    // Set the workspace initialized flag to true
										    workspaceInitialized = true;
								    	}
									}
								}
					    	} 
					    	catch (Exception e) {
								throw new CompositeException(e.getMessage(), e);
					    	}
					    	if (!workspaceInitialized) {
								throw new ApplicationException("ERROR: Workspace Initialization error.  Workspace="+vcsStruct.getVcsWorkspaceName()+" not found for user="+vcsStruct.getVcsUsername()+" when \"TFS_USE_EXISTING_WORKSPACE=true\".   Map the workspace for this user="+vcsStruct.getVcsUsername()+" and computer to resolve the issue or set \"TFS_USE_EXISTING_WORKSPACE=false\".");
					    	}
					    } // if (stdout != null)
					    else
					    {
							throw new ApplicationException("ERROR: Workspace Initialization error.  No workspaces found for user="+vcsStruct.getVcsUsername()+" when \"TFS_USE_EXISTING_WORKSPACE=true\".");
					    }
			        } 
			        // Perform the full workspace initialization when TFS_USE_EXISTING_WORKSPACE=false
			        else  
			        {

			        	try {
					        /********************
					         * DELETE WORKSPACE
					         ********************/
							// Delete the TFS workspace link
							// tf workspace /delete ${VCS_WORKSPACE_NAME} ${VCS_OPTIONS}
							arguments="  workspace /delete "+vcsStruct.getVcsWorkspaceName()+" /noprompt "+vcsStruct.getVcsOptions(); 
							commandDesc = "    Delete the TFS workspace link...";
	
							// Print out command
							CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
							CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
							CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
							
						    // Parse the command arguments into a list
						    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
						    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");
						    
						    // Execute the command line
						    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
							
						} catch (CompositeException e) {
							boolean errorFound = false;
							String ignoreMessage = "";
							if (e.getMessage().contains("does not exist")) {
								errorFound = true;
								ignoreMessage = "does not exist";
							}
							if (e.getMessage().contains("Unable to determine the source control server")) {
								errorFound = true;
								ignoreMessage = "Unable to determine the source control server";
							}
							if (errorFound) {
								//continue processing, this is OK
								if (logger.isErrorEnabled()) {
									logger.info(prefix+"::  "+"Warning: Error ignored.  Error message matches ignore message string=\""+ignoreMessage+"\"");
							    }
							} else {
								throw new ApplicationException(e);
							}
						} // End: if (pdToolStudio && vcsStruct.getVcsUseExistingWorkdspace().equals("true")) {
						
				        // Explicitly remove the workspace directory
				        removeDirectory(prefix, vcsStruct.getVcsWorkspace());
				        
				        // Only remove the VCS Temp directory with PD Tool as PD Tool Studio VCS Temp is managed by Studio
				        if (!pdToolStudio) {
				        	// Explicitly remove the workspace temp directory
				        	removeDirectory(prefix, vcsStruct.getVcsTemp());	        	
				        }

				        /*********************
				         * PREPARE DIRECTORIES
				         *********************/
				        // Create the workspace directory
				        createDirectory(prefix, vcsStruct.getVcsWorkspace());
	
						// Create the VCS Workspace Project directory
				        //	  e.g: vcsWorkspaceProject:  D:/PDTool/tfs_workspace/cis_objects
				        createDirectory(prefix, vcsStruct.getVcsWorkspaceProject());
	
						// Only create the VCS Temp directory with PD Tool as PD Tool Studio VCS Temp is managed by Studio
				        if (!pdToolStudio) {
				        	// Create the workspace temp directory
				        	createDirectory(prefix, vcsStruct.getVcsTemp());
				        }
				        
				        // Set the directory to execute from the workspace directory
						execFromDir = vcsStruct.getVcsWorkspace();
						
						// Set the VCS command
						command = vcsStruct.getVcsExecCommand();
	
						// Link the VCS Repository URL and Project Root to the local workspace		
						/*
						 * Creating a new workspace
						 * http://msdn.microsoft.com/en-US/library/y901w7se(v=VS.80).aspx
							Before you can add files to the source control server or check out items on the server in order to edit them, 
							you must create a workspace or associate an existing one with the current directory. 
							For more information, see How to: Create a Workspace.
							http://msdn.microsoft.com/en-US/library/ms181384(v=VS.80).aspx
	
							To make the current directory a working folder for an existing workspace on your computer, 
							type tf workspace workspacename, where workspacename is the name of the existing workspace, 
							click click here to enter a new working folder, type the server path of which you want to map 
							the current directory in the Team Foundation source control server Folder box, 
							type the current directory in the Local Folder box, and then click OK.
	
							When you create a new workspace, you can specify a template workspace as part of the /new option. 
							When you specify a template workspace, Team Foundation creates a new workspace on the current computer, 
							sets the owner to the current owner, and replicates the following workspace properties into the new workspace 
							from the template workspace: mappings and comment. If no name is specified, a name based on the current computer name is used. 
							When you create a workspace using a template, Team Foundation does not retrieve the files to which it maps from the server. 
							Use the Get Command to synchronize the new workspace with the latest version on the server.
						 */
	
				        /********************
				         * CREATE WORKSPACE
				         ********************/
						// tf workspace /new /collection:${VCS_REPOSITORY_URL} $VCS_WORKSPACE /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_WORKSPACE_INIT_LINK_OPTIONS}
						arguments=" workspace /new /s:"+vcsStruct.getVcsRepositoryUrl()+"/ "+vcsStruct.getVcsWorkspaceName()+" /noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsWorkspaceInitLinkOptions();
	
						commandDesc = "    Linking local worksapce to VCS Repository...";
	
						// Print out command
						CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
	
				        /********************
				         * CHECKOUT WORKSPACE
				         ********************/
						// Check out the repository to the local workspace	    
					    // Retrieves a read-only copy of a file from the Team Foundation Server to the workspace and creates folders on disk to contain it.
					    // tf get /all /force /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_WORKSPACE_INIT_GET_OPTIONS}    
						arguments=" get /all /noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsWorkspaceInitGetOptions();
						commandDesc = "    Checking out the repository to the local workspace...";
	
						// Print out command
						CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
			        }
					
				}

				/**********************************************************
				 * [GIT] vcsInitWorkspaceCommon:
				 *      INIT VCS WORKSPACE FOR GIT [git]
				 **********************************************************/
				if (vcsStruct.getVcsType().equalsIgnoreCase("GIT")) {
						   
			        // Explicitly remove the workspace directory
			        removeDirectory(prefix, vcsStruct.getVcsWorkspace());
			        
			        // Only remove the VCS Temp directory with PD Tool as PD Tool Studio VCS Temp is managed by Studio
			        if (!pdToolStudio) {
			        	// Explicitly remove the workspace temp directory
			        	removeDirectory(prefix, vcsStruct.getVcsTemp());	        	
			        }
			        
			        // Set the workspace directory path to the workspace + vcs project root (e.g. cis_objects)
			        String workspaceDir = vcsStruct.getVcsWorkspace().replaceAll("//", "/");
			        
			        // Create the workspace directory
			        createDirectory(prefix, workspaceDir);

			        // Only create the VCS Temp directory with PD Tool as PD Tool Studio VCS Temp is managed by Studio
			        if (!pdToolStudio) {
			        	// Create the workspace temp directory
			        	createDirectory(prefix, vcsStruct.getVcsTemp());
			        }
			        
			        // Set the directory to execute from the workspace directory
					execFromDir = workspaceDir;
					
					// Set the VCS command
					command = vcsStruct.getVcsExecCommand();
					
					// Substitute the username and password (if any). Also don't make the substitution if the repo
					// URL already has username and password information embedded using the "@" notation.
					//
					String vcsRepositoryUrl = vcsStruct.getVcsRepositoryUrl();
					if (vcsStruct.getVcsUsername() != null && vcsStruct.getVcsUsername().length() > 0 && ! vcsRepositoryUrl.contains ("@")) {
						String encUser = null, encPass = null;

                        // URL encode the username and password so they don't cause problems on the command line
						try {
							encUser = URLEncoder.encode(vcsStruct.getVcsUsername(), "UTF-8");
	                        encPass = URLEncoder.encode(vcsStruct.getVcsPassword(), "UTF-8");
                        } catch (Exception ignored) {;}
                        
						// Git supports a number of different URL protocols so we'll need to make some substitutions on the 
						// ones that support username/password in the URL itself.
						//
						// SSH
						//
						if (vcsRepositoryUrl.startsWith("ssh://")) {
							vcsRepositoryUrl = vcsRepositoryUrl.replaceFirst("ssh://", "ssh://" + encUser + "@");
						}
						//
						// GIT
						//
						else if (vcsRepositoryUrl.startsWith("git://")) {
							// git protocol doesn't support user/pass
						}
						//
						// HTTP(S)
						//
						else if (vcsRepositoryUrl.matches("^http[s]?://.*")) {
							vcsRepositoryUrl = vcsRepositoryUrl.replaceFirst("(http[s]?)://", "$1://" + encUser + ":" + encPass + "@");
						} 
						//
						// FTP(S)
						//
						else if (vcsRepositoryUrl.matches("^ftp[s]?://.*")) {
							vcsRepositoryUrl = vcsRepositoryUrl.replaceFirst("(ftp[s]?)://", "$1://" + encUser + ":" + encPass + "@");
						} 
						//
						// RSYNC
						//
						else if (vcsRepositoryUrl.startsWith("rsync://")) {
							// rsync protocol doesn't support user/pass
						} 
						//
						// SCP format (SSH)
						//
						// this format it a bit hard to detect in that it starts with a host name and a colon. The Git rule says
						// that it will match this format if there are no "/" chars in the text before the first ":" char (and 
						// this text is not one of the protocols above.) 
						//
						// the format is also not a URL so the username is not URL encoded (taking a chance that username doesn't
						// contain odd characters.)
						//
						else if (vcsRepositoryUrl.matches("^[^/]+:.*")) {
							vcsRepositoryUrl = vcsStruct.getVcsUsername() + "@" + vcsRepositoryUrl;
						}
					}
					
					// determine the remote branch to check out. if user doesn't specify the branch with VCS_WORKSPACE_INIT_GET_OPTIONS
					// then use "master"
					//
					String remoteBranch = (vcsStruct.getVcsWorkspaceInitGetOptions() != null && vcsStruct.getVcsWorkspaceInitGetOptions().length() > 0)
			                  ? vcsStruct.getVcsWorkspaceInitGetOptions()
			                  : "master";
					
					// if using https on windows, use the wincred facility to store the user's credentials (so that we're not prompted
					// for them when performing pushes.)
					//
			        if (System.getProperty("os.name").startsWith("Windows")) {
			        	
			        	// git config --global credential.helper wincred
						arguments=" config --global credential.helper wincred";

						// Print out command
						commandDesc = "    Enabling the storing of credentials using WinCred...";
						CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
			        }
					
					// Link the VCS Repository URL and Project Root to the local workspace. Creates an empty clone (nothing checked out.)					
					// git clone ${GIT_OPTIONS} ${VCS_WORKSPACE_INIT_LINK_OPTIONS} "${VCS_REPOSITORY_URL}/${VCS_PROJECT_ROOT}" .
					arguments=" clone -n " + vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsWorkspaceInitLinkOptions() + " " + vcsRepositoryUrl + " .";

					// Print out command
					commandDesc = "    Linking local worksapce to VCS Repository...";
					CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());

					// Check out the desired branch (or other "tree-ish") to the local workspace						
					// git checkout ${GIT_OPTIONS} ${VCS_WORKSPACE_INIT_GET_OPTIONS} ${VCS_PROJECT_ROOT}
					arguments=" checkout " + vcsStruct.getVcsOptions() + " " + remoteBranch + " " + vcsStruct.getVcsProjectRoot();

					// Print out command
					commandDesc = "    Checking out VCS Repository resources ...";
					CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());

/*
					// Git doesn't check out empty folders so we need to create the workspace folder if it didn't get checked out.
				    // this is so that subsequent Git commands
					//
					if (! CommonUtils.fileExists (vcsStruct.getVcsProjectRoot())) {
						CommonUtils.writeOutput("Project root folder, \"" + vcsStruct.getVcsProjectRoot() + "\", did not get checked out, creating.",prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.mkdirs (vcsStruct.getVcsProjectRoot());
					}
*/		
					// Set automatic conversion of LF (Unix) to CRLF (Windows) to false. This causes issues otherwise.
					// git config --local core.autocrlf=false 
					arguments=" config --local core.autocrlf false";

					// Print out command
					commandDesc = "    Setting line termination handling ...";
					CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
				    		    
				    // Need to set this to get rid of warnings during pushes
				    // git config --global push.default simple
					arguments=" config --global push.default simple";

					// Print out command
					commandDesc = "    Setting push default to \"simple\" to prevent warnings during pushes ...";
					CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
				    

				}

				/**********************************************************
				 * [CLC] vcsInitWorkspaceCommon:
				 *      INIT VCS WORKSPACE FOR CLEARCASE
				 **********************************************************/
				if (vcsStruct.getVcsType().equalsIgnoreCase("CLC")) {
						   
			        // Explicitly remove the workspace directory
			        removeDirectory(prefix, vcsStruct.getVcsWorkspace());
			        
			        // Only remove the VCS Temp directory with PD Tool as PD Tool Studio VCS Temp is managed by Studio
			        if (!pdToolStudio) {
			        	// Explicitly remove the workspace temp directory
			        	removeDirectory(prefix, vcsStruct.getVcsTemp());	        	
			        }
			        
			        // Set the workspace directory path to the workspace + vcs project root (e.g. cis_objects)
			        String workspaceDir = (vcsStruct.getVcsWorkspace()+"/"+vcsStruct.getVcsProjectRoot()).replaceAll("//", "/");
			        
			        // Create the workspace directory
			        createDirectory(prefix, workspaceDir);

			        // Only create the VCS Temp directory with PD Tool as PD Tool Studio VCS Temp is managed by Studio
			        if (!pdToolStudio) {
			        	// Create the workspace temp directory
			        	createDirectory(prefix, vcsStruct.getVcsTemp());
			        }
			        
			        // Set the directory to execute from the workspace directory
					execFromDir = workspaceDir;
					
					// Set the VCS command
					command = vcsStruct.getVcsExecCommand();
				
					// If the special option "-IGNORE_INIT_LINK" is set then do not execute the initialization linking of the workspace.
					//   Otherwise maintain backward compatibility.
					if (vcsStruct.getVcsWorkspaceInitLinkOptions() != null && vcsStruct.getVcsWorkspaceInitLinkOptions().contains("-IGNORE_INIT_LINK"))
						initializeWorkspaceLink = false;
					
					// To maintain backward compatibility, execute the VCS workspace initialization to link the workspace with the repository.
					//   However, this requires Read/Write access to the repository folders.
					if (initializeWorkspaceLink) {
						// Link the VCS Repository URL and Project Root to the local workspace						
/**************************
 *  CLEARCASE LINK TO REPO					
 **************************/
						// cleatool mkview . -m "linking workspace to the VCS repository" "${VCS_REPOSITORY_URL}/${VCS_PROJECT_ROOT}" ${CLC_OPTIONS} ${CLC_AUTH} ${VCS_WORKSPACE_INIT_LINK_OPTIONS}
						arguments=" mkview "+vcsStruct.getVcsRepositoryUrl()+"/"+vcsStruct.getVcsProjectRoot()+" --message Linking_workspace_to_VCS_repository "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsWorkspaceInitLinkOptions();
	
						// Print out command
						commandDesc = "    Linking local worksapce to VCS Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					}
/**************************
 *  CLEARCASE CHECKOUT					
 **************************/
					// Check out the repository to the local workspace						
					// svn co "${VCS_REPOSITORY_URL}/${VCS_PROJECT_ROOT}" ${CLC_OPTIONS} ${CLC_AUTH} ${VCS_WORKSPACE_INIT_GET_OPTIONS}
					arguments=" co "+vcsStruct.getVcsRepositoryUrl()+"/"+vcsStruct.getVcsProjectRoot()+" "+execFromDir+" "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsWorkspaceInitGetOptions();
					// (prevous code): arguments=" co "+vcsStruct.getVcsRepositoryUrl()+"/"+vcsStruct.getVcsProjectRoot()+" "+vcsStruct.getVcsOptions();

					// Print out command
					commandDesc = "    Checking out the repository to the local workspace...";
					CommonUtils.writeOutput(commandDesc,prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-info",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-info",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
				}

				/**********************************************************
				 * vcsInitWorkspaceCommon:
				 *      INIT VCS WORKSPACE FOR "NEW VCS TYPE" [NEW_VCS_TYPE]
				 **********************************************************/
				if (vcsStruct.getVcsType().equalsIgnoreCase("NEW_VCS_TYPE")) {
					//Implement Initialization for NEW VCS TYPE HERE
				}
				
		        CommonUtils.writeOutput("............................................",prefix,"-info",logger,debug1,debug2,debug3);
		        CommonUtils.writeOutput("Successfully Initialized workspace for VCS_TYPE="+vcsStruct.getVcsType(),prefix,"-info",logger,debug1,debug2,debug3);
		        CommonUtils.writeOutput("............................................",prefix,"-info",logger,debug1,debug2,debug3);

			} catch (CompositeException e) {
				logger.error("Initialize VCS Workspace Failed. Error: ",e);
				throw new ApplicationException(e.getMessage(),e);
			}
		}
		
		
/******************************************************************************************
 *  PRIVATE IMPLEMENTATION
 *  
 *  GENERALIZED IMPLEMENTATION LOGIC FOR HANDLING MULTIPLE VCS ENTRIES FROM THE PLAN FILE
 *  
 *  Process multipe VCS IDs when invoked from one of the VCS methods for doing multiple
 *  VCS commands (e.g. vcsCheckins, vcsCheckouts, etc.)
 * 
 *******************************************************************************************/
		
	// ***********************************************************************************************
	// Execute a VCS Action
	// ***********************************************************************************************
	private void executeVCS(String actionName, String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {

		String prefix = "executeVCS";
 
		CommonUtils.writeOutput("***** BEGIN COMMAND: "+actionName+" *****",prefix,"-info",logger,debug1,debug2,debug3);
		CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);

		/*****************************************
		 * VALIDATE COMMAND-LINE VARIABLES
		 *****************************************/
		// Validate the arguments
		if(serverId == null || serverId.trim().length() ==0 || vcsIds == null || vcsIds.trim().length() ==0 || pathToServersXML == null || pathToServersXML.trim().length() ==0 || pathToVcsXML == null || pathToVcsXML.trim().length() ==0){
			throw new ValidationException("Invalid Arguments");
		}
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToVcsXML)) {
			throw new ValidationException("File ["+pathToVcsXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new ValidationException("File ["+pathToServersXML+"] does not exist.");
		}

		// Extract variables for the vcsIds
		vcsIds = CommonUtils.extractVariable(prefix, vcsIds, propertyFile, true);

		/*****************************************
		 * PRINT OUT COMMAND-LINE VARIABLES
		 *****************************************/
		// Print out Debug input parameters
		CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
		CommonUtils.writeOutput("---Input Variables from deployment plan file: ",prefix,"-debug2",logger,debug1,debug2,debug3);
		CommonUtils.writeOutput("    SERVER_ID=  "+serverId,prefix,"-debug2",logger,debug1,debug2,debug3);
		CommonUtils.writeOutput("    VCONN_ID=   "+vcsConnId,prefix,"-debug2",logger,debug1,debug2,debug3);
		CommonUtils.writeOutput("    MODULE_ID=  "+vcsIds,prefix,"-debug2",logger,debug1,debug2,debug3);
		CommonUtils.writeOutput("    MODULE_PATH="+pathToVcsXML,prefix,"-debug2",logger,debug1,debug2,debug3);
		CommonUtils.writeOutput("    SERVER_PATH="+pathToServersXML,prefix,"-debug2",logger,debug1,debug2,debug3);

		// Get the server info from the servers.xml file
		CompositeServer serverInfo = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, prefix, logger);
		serverInfo = validateServerProperties(serverInfo);

		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(serverInfo, true);

		/*****************************************
		 * INITIALIZE VCS STRUCTURE VARIABLE
		 *****************************************/
		// Initialize a new VCS structure for passing to the methods
		VcsStruct vcsStruct = (new VCSManagerImpl()).new VcsStruct();
		// Load the VCS structure and decrypt the vcs password -- Retrieve properties from the deploy.properties file
		vcsStruct.loadVcs(prefix, vcsUser, vcsPassword);
		
		// Set the Module Action Objective
		String s1 = (vcsIds == null) ? "no_Ids" : "Ids="+vcsIds;
		System.setProperty("MODULE_ACTION_OBJECTIVE", actionName+" : "+s1);

        /*****************************************
		 * DISPLAY/VALIDATE VCS VARIABLES
		 *****************************************/	        
        // Validate, Create VCS Workspace and Temp directories
        vcsStruct.validateVcs(prefix);
        // Resolve Absolute paths
        vcsStruct.resolveCanonicalPathsVcs(prefix);
		// Display VCS variables
        vcsStruct.displayVcs(prefix);
		
		/*****************************************
		 * EXECUTE VCS
		 *****************************************/
		try {
			//	Parse the VCSModule.xml property file using jaxb to convert XML to corresponding java objects
			List<VCSResourceType> vcsResourceList = getVCSResources(serverId, vcsIds, pathToVcsXML, pathToServersXML);
			
			// Track which vcsIds got processed
			String processedVcsIds="";
			
			if (vcsResourceList != null && vcsResourceList.size() > 0) {
			
				// Iterate over the XML entries
				for (VCSResourceType vcsResource : vcsResourceList) {
					String vcsId = null;
					String vcsMessage = null;
					String vcsRevision = null;
					String vcsResourcePath = null;
					String vcsResourceType = null;
					String origResourceType = null;
					String origResourcePath = null;
					String vcsLabel = null;

					// Get the identifier and convert any $VARIABLES
					String identifier = CommonUtils.extractVariable(prefix, vcsResource.getId(), propertyFile, true);
					
					/**
					 * Possible values for archives 
					 * 1. csv string like import1,import2 (we process only resource names which are passed in)
					 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
					 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
					 * 	  like -import1,import3 (we ignore passed in resources and process rest of the in the input xml
					 */
					// Determine if the XML entry is in the list of vcsIds passed in
					if(DeployUtil.canProcessResource(vcsIds, identifier)){
								
						processedVcsIds = processedVcsIds + identifier + ", " ;
					
						vcsId = identifier;
						//********************************************************************************************
						// Validate vcsLabel settings prior to invocation of vcs scripts
						//********************************************************************************************
						if (vcsResource.getVcsLabel() != null) {
							
							// If the VCS Type is anything other than perforce and the label is being used then throw an exception.
							if (!vcsStruct.getVcsType().equalsIgnoreCase("P4")) {
								throw new ValidationException("VCS Resource Type=["+vcsStruct.getVcsType()+"] is not supported for the use with \"labels\".");							
							}
							
							vcsLabel = vcsResource.getVcsLabel();
							if (vcsLabel.length() == 0) {
								vcsLabel = null;
							} else {
								vcsResourcePath = "/";
								vcsResourceType = "FOLDER";
							}
						}
						
						if (vcsLabel == null) {
							//********************************************************************************************
							// Validate vcsResourcePath settings prior to invocation of vcs scripts
							//********************************************************************************************
							if (vcsResource.getResourcePath() != null) {
								// Get the resource path and convert any $VARIABLES
								vcsResourcePath = CommonUtils.extractVariable(prefix, vcsResource.getResourcePath(), propertyFile, true);
								origResourcePath = vcsResourcePath;
								if (vcsResourcePath.length() == 0) {
									vcsResourcePath = null;
								} else {
									// Encode the VCS resource path
									vcsResourcePath = ResourceNameCodec.encode(vcsResourcePath);
									vcsResourcePath = vcsResourcePath.replaceAll("_002F", "/");
								}
							}
							// Validate the resource path
							if (vcsLabel == null && vcsResourcePath == null) {
								throw new ValidationException("VCS Resource Path is null or empty for VCS Module ID="+vcsId);							
							}
	
							//********************************************************************************************
							// Validate vcsResourceType settings prior to invocation of vcs scripts
							//********************************************************************************************
							// Get the Resource Type for the VCS Resource Path
							if (vcsResource.getResourceType() != null) {
								// Get the resource type and convert any $VARIABLES
								vcsResourceType = CommonUtils.extractVariable(prefix, vcsResource.getResourceType().trim(), propertyFile, true);
								if (vcsResourceType.length() == 0) 
									vcsResourceType = null;
								origResourceType = vcsResourceType;
								vcsResourceType = getConvertedVcsResourceType(vcsResourceType);
								if (vcsResourceType == null || vcsResourceType.length() == 0) {
									vcsResourceType = null;
								} else {
									if (!validResourceTypes.contains(vcsResourceType)) {
										throw new ValidationException("VCS Resource Type=["+vcsResourceType+"] is not valid for VCS Module ID="+vcsId+"  Valid Resource Types=["+validResourceTypes+"]");							
									}
								}
							}
							// Validate the resource type
							if (vcsLabel == null && vcsResourceType == null) {
								throw new ValidationException("VCS Resource Type is null or empty for VCS Module ID="+vcsId);							
							}
						}

						//********************************************************************************************
						// Validate Message settings prior to invocation of vcs scripts
						//********************************************************************************************
						if (vcsResource.getMessage() != null) {
							// Get the message and convert any $VARIABLES
							vcsMessage = CommonUtils.extractVariable(prefix, vcsResource.getMessage(), propertyFile, true);
						}
						
						//********************************************************************************************
						// Validate Revision settings prior to invocation of vcs scripts
						//********************************************************************************************
						if (vcsResource.getRevision() != null && vcsResource.getRevision().length() > 0) {
							// Get the resource type and convert any $VARIABLES
							vcsRevision = CommonUtils.extractVariable(prefix, vcsResource.getRevision().trim(), propertyFile, true);
							
							if (vcsRevision.equalsIgnoreCase("HEAD")) {
								vcsRevision = vcsRevision.toUpperCase();
							} else {
								try {
									double d = Double.valueOf(vcsRevision);
//									int i = Integer.parseInt(vcsRevision);
								} catch (Exception e) {
									throw new ValidationException("Unable to convert Revision ["+vcsRevision+"] to an decimal value.  Make sure there is only an integer or decimal value in this field.");														
								}
							}
						}

						// Print out info statements
						CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("---VCS Module ID Arguments:",prefix,"-debug2",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("   VCS ID                 ="+vcsId,prefix,"-debug2",logger,debug1,debug2,debug3); 
						CommonUtils.writeOutput("   Resource Path          ="+origResourcePath,prefix,"-debug2",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("   Encoded Resource Path  ="+vcsResourcePath,prefix,"-debug2",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("   Resource Type          ="+origResourceType,prefix,"-debug2",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("   Converted Resource Type="+vcsResourceType,prefix,"-debug2",logger,debug1,debug2,debug3);
						if ( actionName.equalsIgnoreCase(VCSManager.action.CHECKOUT.name()) ) {
						CommonUtils.writeOutput("   Label                  ="+vcsLabel,prefix,"-debug2",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("   Revision               ="+vcsRevision,prefix,"-debug2",logger,debug1,debug2,debug3);
						}
						if ( actionName.equalsIgnoreCase(VCSManager.action.CHECKIN.name()) || actionName.equalsIgnoreCase(VCSManager.action.FORCED_CHECKIN.name()) )
						CommonUtils.writeOutput("   Message                ="+vcsMessage,prefix,"-debug2",logger,debug1,debug2,debug3);
						
						CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("-- BEGIN OUTPUT ------------------------------------",prefix,"-info",logger,debug1,debug2,debug3);					
							
						// Set the Module Action Objective
						s1 = "";
						if (vcsLabel != null)
							s1 = "Label="+vcsLabel;
						if (vcsRevision != null)
							s1 = "Rev="+vcsRevision;
						System.setProperty("MODULE_ACTION_OBJECTIVE", actionName+" : "+s1+" : "+origResourcePath);
						
						//--------------------------------------------------------------
						// checkin
						//--------------------------------------------------------------
						if (actionName.equalsIgnoreCase(VCSManager.action.CHECKIN.name())) {

							checkin(vcsResourcePath, vcsResourceType, vcsMessage, vcsStruct, serverId, pathToServersXML);
						} 	
						//--------------------------------------------------------------
						// checkout
						//--------------------------------------------------------------
						if (actionName.equalsIgnoreCase(VCSManager.action.CHECKOUT.name())) {

							if (vcsRevision == null || vcsRevision.length() == 0) {
								throw new ValidationException("VCS Revision is null or empty for VCS Module ID="+vcsId);							
							}
							checkout(vcsResourcePath, vcsResourceType, vcsLabel, vcsRevision, vcsStruct, serverId, pathToServersXML);
						}
						//--------------------------------------------------------------
						// forced_checkin
						//--------------------------------------------------------------
						if (actionName.equalsIgnoreCase(VCSManager.action.FORCED_CHECKIN.name())) {

							forced_checkin(vcsResourcePath, vcsResourceType, vcsMessage, vcsStruct, serverId, pathToServersXML);
						}
						//--------------------------------------------------------------
						// prepare_checkin
						//--------------------------------------------------------------
						if (actionName.equalsIgnoreCase(VCSManager.action.PREPARE_CHECKIN.name())) {

							prepare_checkin(vcsResourcePath, vcsResourceType, vcsStruct, serverId, pathToServersXML);
						}
					 	
						CommonUtils.writeOutput("Successfully performed action="+actionName+" for VCSModule ID="+vcsId,prefix,"-info",logger,debug1,debug2,debug3);
						
					} // end if(DeployUtil.canProcessResource(vcsIds, identifier)){
					
				} // end for (VCSResourceType vcsResource : vcsResourceList) {
					
			} // end if (vcsResourceList != null && vcsResourceList.size() > 0) {
		
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			processedVcsIds = processedVcsIds.trim();
			if (processedVcsIds.length() > 0) {
				processedVcsIds = processedVcsIds.substring(0, processedVcsIds.length()-1);
				CommonUtils.writeOutput("Processed the following VCS Identifiers="+processedVcsIds+".",prefix,"-info",logger,debug1,debug2,debug3);				
			} else {
				String msg = "Warning: No VCS Identifiers were processed.  Cross-check the property/build file against the VCSModule.xml.";
				CommonUtils.writeOutput(msg,prefix,"-info",logger,debug1,debug2,debug3);				
				System.setProperty("MODULE_ACTION_MESSAGE", msg);
			}
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("Successfully completed "+actionName+".",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-info",logger,debug1,debug2,debug3);

		} catch (CompositeException e) {
			    CommonUtils.writeOutput("Action ["+actionName+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
				logger.error("Error occured while executing "+ actionName,e);
				throw new CompositeException(e.getMessage(),e);
		}
	}

	// Retrieve the VCSModule.xml file and all of its nodes
	private List<VCSResourceType> getVCSResources(String serverId, String vcsId, String pathToVcsXML, String pathToServersXML) {
		// validate incoming arguments
		if(serverId == null || serverId.trim().length() ==0 || vcsId == null || vcsId.trim().length() ==0 || pathToServersXML == null || pathToServersXML.trim().length() ==0 || pathToVcsXML == null || pathToVcsXML.trim().length() ==0){
			throw new ValidationException("Invalid Arguments");
		}

		try {
			//using jaxb convert xml to corresponding java objects
			VCSModule vcsModuleType = (VCSModule)XMLUtils.getModuleTypeFromXML(pathToVcsXML);
			
			if(vcsModuleType != null && vcsModuleType.getVcsResource() != null && !vcsModuleType.getVcsResource().isEmpty()){
				return vcsModuleType.getVcsResource();
			}
		} catch (CompositeException e) {
			CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
			String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Parse VCSModule XML", "getVCSResources", pathToVcsXML, targetServer);

			logger.error(errorMessage , e);
			throw new ApplicationContextException(errorMessage, e);
		}
		return null;
	}

/******************************************************************************************
 *  PRIVATE IMPLEMENTATION
 *  
 *  VCS GENERALIZED SCRIPTS [checkin, checkout, forced_checkin, prepare_checkin]
 *  
 *  These scripts implement the overall process for version control
 * 
 *******************************************************************************************/

	// ***********************************************************************************************
	// Execute a VCS Generalized Scripts ** CHECKIN **
	// ***********************************************************************************************
	private void checkin(String resourcePath, String resourceType, String message, VcsStruct vcsStruct, String serverId, String pathToServersXML) throws CompositeException {
	/*
	 * Original Shell Script logic (This method is patterned after the shell script)
	 * 
		# $1 ->  Resource path 			(e.g. "/shared/MyFolder/My__View"), using file system (encoded) names
		# $2 ->  Resource type 			(e.g. "CONTAINER", "TABLE", "PROCEDURE" etc.)
		# $3 ->  Checkin message 		(e.g. "Adding MyFolder")
		# $4 ->  VCS Workspace Folder	(e.g. "/tmp/vcs_svn/cisVcsWorkspace/cis_objects")
		# $5 ->  VCS Temp Folder		(e.g. "/tmp/vcs_svn/cisVcsTemp")
		# $6 ->  CIS user name			(e.g. "admin")
		# $7 ->  CIS user password		(e.g. "admin")
		# $8 ->  CIS user domain		(e.g. "composite")
		# $9 ->  CIS server host name	(e.g. "localhost
		#------------------------------------------
		# Execute the script
		#------------------------------------------
		echo "${PREFIX1}============= CIS EXPORT ============="
		"${VCS_SCRIPT_HOME}/cis_import_export/vcs_export.sh" $resourcePath "$VcsTemp" $User $Password $Domain $Host $Port

		echo "${PREFIX1}============= DIFFMERGER CHECKIN ============="
		"${VCS_SCRIPT_HOME}/diffmerger/checkin.sh" $resourcePath $resourceType "$Workspace" "$VcsTemp"

		echo "${PREFIX1}============= VCS CHECKOUT ============="
		"${VCS_SCRIPT_HOME}/vcs_checkin_checkout_${VCS_TYPE}/vcs_checkout.sh" $resourcePath $resourceType HEAD "$Workspace"

		echo "${PREFIX1}============= VCS CHECKIN ============="
		"${VCS_SCRIPT_HOME}/vcs_checkin_checkout_${VCS_TYPE}/vcs_checkin.sh" $resourcePath $resourceType "$Message" "$Workspace"

		echo "${PREFIX1}============= SUCCESSFULLY COMPLETED ${SCRIPT1} ============="
	*/
		String prefix = "checkin";
		try {
			CommonUtils.writeOutput("======== BEGIN VCS ["+vcsStruct.getVcsType()+"] checkin =========",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---VCS Arguments:",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      resourcePath=       "+resourcePath,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      resourceType=       "+resourceType,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      Message=            "+message,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsWorkspace=       "+vcsStruct.getVcsWorkspace(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsWorkspaceProject="+vcsStruct.getVcsWorkspaceProject(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsTemp=            "+vcsStruct.getVcsTemp(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsUser=            "+vcsStruct.getVcsUsername(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsPassword=        ********",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      serverId=           "+serverId,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      pathToServersXML=   "+pathToServersXML,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-debug3",logger,debug1,debug2,debug3);

			CommonUtils.writeOutput("=============== CIS EXPORT ===============",prefix,"-debug3",logger,debug1,debug2,debug3);
			cis_import_export__export(resourcePath, resourceType, vcsStruct, serverId, pathToServersXML);
			
			CommonUtils.writeOutput("=========== DIFFMERGER CHECKIN ===========",prefix,"-debug3",logger,debug1,debug2,debug3);
			diffmerger__checkin(resourcePath, resourceType, vcsStruct);
					
			CommonUtils.writeOutput("============== VCS CHECKOUT ==============",prefix,"-debug3",logger,debug1,debug2,debug3);
			vcs_checkin_checkout__vcs_checkout(resourcePath, resourceType, null, "HEAD", vcsStruct);
			
			CommonUtils.writeOutput("==========  VCS CHECKOUT VALIDATE ========",prefix,"-debug2",logger,debug1,debug2,debug3);
	        vcs_checkout_validate(resourcePath, resourceType, vcsStruct);

			CommonUtils.writeOutput("============== VCS CHECKIN ===============",prefix,"-debug3",logger,debug1,debug2,debug3);
			vcs_checkin_checkout__vcs_checkin(resourcePath, resourceType, message, vcsStruct);
	
			CommonUtils.writeOutput("======= COMPLETED VCS ["+vcsStruct.getVcsType()+"] checkin ======",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-debug3",logger,debug1,debug2,debug3);
			
		} catch (CompositeException e) {
		    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
			logger.error("Failed executing "+prefix+".",e);
			throw new CompositeException(e.getMessage(), e);
		}
	}

	// ***********************************************************************************************
	// Execute a VCS Generalized Scripts ** CHECKOUT **
	// ***********************************************************************************************
	private void checkout(String resourcePath, String resourceType, String vcsLabel, String revision, VcsStruct vcsStruct, String serverId, String pathToServersXML) throws CompositeException {
	/* 
	 * Original Shell Script logic (This method is patterned after the shell script)
	 * 
		# $1 ->  Resource path 			(e.g. "/shared/MyFolder/My__View"), using file system (encoded) names
		# $2 ->  Resource type 			(e.g. "CONTAINER", "TABLE", "PROCEDURE" etc.)
		# $3 ->  Rollback revision  	(e.g. "HEAD")
		# $4 ->  VCS Workspace Folder	(e.g. "/tmp/vcs_svn/cisVcsWorkspace/cis_objects")
		# $5 ->  VCS Temp Folder		(e.g. "/tmp/vcs_svn/cisVcsTemp")
		# $6 ->  CIS user name			(e.g. "admin")
		# $7 ->  CIS user password		(e.g. "admin")
		# $8 ->  CIS user domain		(e.g. "composite")
		# $9 ->  CIS server host name	(e.g. "localhost")
		# $10 -> CIS server port		(e.g. "9400")
		#------------------------------------------
		# Execute the script
		#------------------------------------------
		echo "${PREFIX1}============= VCS CHECKOUT ============="
		"$VCS_SCRIPT_HOME/vcs_checkin_checkout_${VCS_TYPE}/vcs_checkout.sh" $resourcePath $resourceType $Revision "$Workspace"

		echo "${PREFIX1}============= CIS EXPORT ============="
		"$VCS_SCRIPT_HOME/cis_import_export/vcs_export.sh" $resourcePath "$VcsTemp" $User $Password $Domain $Host $Port

		echo "${PREFIX1}============ DIFFMERGER ROLLBACK ============="
		"$VCS_SCRIPT_HOME/diffmerger/rollback.sh" $resourcePath $resourceType "$Workspace" "$VcsTemp"

		echo "${PREFIX1}============= CIS IMPORT ============="
		"$VCS_SCRIPT_HOME/cis_import_export/import.sh" "$VcsTemp" $User $Password $Domain $Host $Port

		echo "${PREFIX1}============= SUCCESSFULLY COMPLETED ${SCRIPT1} ============="
	*/
		String prefix = "checkout";
		try {
			CommonUtils.writeOutput("======== BEGIN VCS ["+vcsStruct.getVcsType()+"] checkout ========",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---VCS Arguments:",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      resourcePath=       "+resourcePath,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      resourceType=       "+resourceType,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      Revision=           "+revision,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      Label=              "+vcsLabel,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsWorkspace=       "+vcsStruct.getVcsWorkspace(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsWorkspaceProject="+vcsStruct.getVcsWorkspaceProject(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsTemp=            "+vcsStruct.getVcsTemp(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsUser=            "+vcsStruct.getVcsUsername(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsPassword=        ********",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      serverId=           "+serverId,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      pathToServersXML=   "+pathToServersXML,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-debug3",logger,debug1,debug2,debug3);
	
			CommonUtils.writeOutput("==============  VCS CHECKOUT =============",prefix,"-debug3",logger,debug1,debug2,debug3);
			vcs_checkin_checkout__vcs_checkout(resourcePath, resourceType, vcsLabel, revision, vcsStruct);
			
			CommonUtils.writeOutput("==========  VCS CHECKOUT VALIDATE ========",prefix,"-debug2",logger,debug1,debug2,debug3);
	        vcs_checkout_validate(resourcePath, resourceType, vcsStruct);

	        CommonUtils.writeOutput("=============== CIS EXPORT ===============",prefix,"-debug3",logger,debug1,debug2,debug3);
			cis_import_export__export(resourcePath, resourceType, vcsStruct, serverId, pathToServersXML);
					
			CommonUtils.writeOutput("========== DIFFMERGER ROLLBACK ===========",prefix,"-debug3",logger,debug1,debug2,debug3);
			diffmerger__rollback(resourcePath, resourceType, vcsStruct);
			
			CommonUtils.writeOutput("=============== CIS IMPORT ===============",prefix,"-debug3",logger,debug1,debug2,debug3);
			cis_import_export__import(vcsStruct, serverId, pathToServersXML);
			
			CommonUtils.writeOutput("====== COMPLETED VCS ["+vcsStruct.getVcsType()+"] checkout ======",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-debug3",logger,debug1,debug2,debug3);
			
		} catch (CompositeException e) {
		    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
			logger.error("Failed executing "+prefix+".",e);
			throw new CompositeException(e.getMessage(), e);
		}
	}

	// ***********************************************************************************************
	// Execute a VCS Generalized Scripts ** FORCED_CHECKIN **
	// ***********************************************************************************************
	private void forced_checkin(String resourcePath, String resourceType, String message, VcsStruct vcsStruct, String serverId, String pathToServersXML) throws CompositeException {
	/*
	 * Original Shell Script logic (This method is patterned after the shell script)
	 * 
		# $1 ->  Resource path 			(e.g. "/shared/MyFolder/My__View"), using file system (encoded) names
		# $2 ->  Resource type 			(e.g. "CONTAINER", "TABLE", "PROCEDURE" etc.)
		# $3 ->  Checkin message 		(e.g. "Adding MyFolder")
		# $4 ->  VCS Workspace Folder	(e.g. "/tmp/vcs_svn/cisVcsWorkspace/cis_objects")
		# $5 ->  VCS Temp Folder		(e.g. "/tmp/vcs_svn/cisVcsTemp")
		# $6 ->  CIS user name			(e.g. "admin")
		# $7 ->  CIS user password		(e.g. "admin")
		# $8 ->  CIS user domain		(e.g. "composite")
		# $9 ->  CIS server host name	(e.g. "localhost")
		# $10 -> CIS server port		(e.g. "9400")
		#------------------------------------------
		# Execute the script
		#------------------------------------------		
		echo "${PREFIX1}============= VCS CHECKOUT ============="
		"${VCS_SCRIPT_HOME}/vcs_checkin_checkout_${VCS_TYPE}/vcs_checkout.sh" $resourcePath $resourceType HEAD "$Workspace"

		echo "${PREFIX1}============= CIS EXPORT ============="
		"${VCS_SCRIPT_HOME}/cis_import_export/vcs_export.sh" $resourcePath "$VcsTemp" $User $Password $Domain $Host $Port

		echo "${PREFIX1}============= DIFFMERGER CHECKIN ============="
		"${VCS_SCRIPT_HOME}/diffmerger/checkin.sh" $resourcePath $resourceType "$Workspace" "$VcsTemp"

		echo "${PREFIX1}============= VCS CHECKIN ============="
		"${VCS_SCRIPT_HOME}/vcs_checkin_checkout_${VCS_TYPE}/vcs_checkin.sh" $resourcePath $resourceType "$Message" "$Workspace"

		echo "${PREFIX1}============= SUCCESSFULLY COMPLETED ${SCRIPT1} ============="
	*/
		String prefix = "forced_checkin";
		try {
			CommonUtils.writeOutput("====== BEGIN VCS ["+vcsStruct.getVcsType()+"] checkout ======",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---VCS Arguments:",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      resourcePath=       "+resourcePath,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      resourceType=       "+resourceType,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      Message=            "+message,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsWorkspace=       "+vcsStruct.getVcsWorkspace(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsWorkspaceProject="+vcsStruct.getVcsWorkspaceProject(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsTemp=            "+vcsStruct.getVcsTemp(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsUser=            "+vcsStruct.getVcsUsername(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsPassword=        ********",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      serverId=           "+serverId,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      pathToServersXML=   "+pathToServersXML,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-debug3",logger,debug1,debug2,debug3);
	
			CommonUtils.writeOutput("==============  VCS CHECKOUT =============",prefix,"-debug3",logger,debug1,debug2,debug3);
			vcs_checkin_checkout__vcs_checkout(resourcePath, resourceType, null, "HEAD", vcsStruct);
			
			CommonUtils.writeOutput("==========  VCS CHECKOUT VALIDATE ========",prefix,"-debug2",logger,debug1,debug2,debug3);
	        vcs_checkout_validate(resourcePath, resourceType, vcsStruct);

			CommonUtils.writeOutput("=============== CIS EXPORT ===============",prefix,"-debug3",logger,debug1,debug2,debug3);
			cis_import_export__export(resourcePath, resourceType, vcsStruct, serverId, pathToServersXML);
					
			CommonUtils.writeOutput("=========== DIFFMERGER CHECKIN ===========",prefix,"-debug3",logger,debug1,debug2,debug3);
			diffmerger__checkin(resourcePath, resourceType, vcsStruct);
		
			CommonUtils.writeOutput("============== VCS CHECKIN ===============",prefix,"-debug3",logger,debug1,debug2,debug3);
			vcs_checkin_checkout__vcs_checkin(resourcePath, resourceType, message, vcsStruct);
			
			CommonUtils.writeOutput("==== COMPLETED VCS ["+vcsStruct.getVcsType()+"] checkout ====",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-debug3",logger,debug1,debug2,debug3);
			
		} catch (CompositeException e) {
		    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
			logger.error("Failed executing "+prefix+".",e);
			throw new CompositeException(e.getMessage(), e);
		}
	}	
	
	// ***********************************************************************************************
	// Execute a VCS Generalized Scripts ** PREPARE_CHECKIN **
	// ***********************************************************************************************
	private void prepare_checkin(String resourcePath, String resourceType, VcsStruct vcsStruct, String serverId, String pathToServersXML) throws CompositeException {
	/*
	 * Original Shell Script logic (This method is patterned after the shell script)
	 * 
		# $1 ->  Resource path 			(e.g. "/shared/MyFolder/My__View"), using file system (encoded) names
		# $2 ->  Resource type 			(e.g. "CONTAINER", "TABLE", "PROCEDURE" etc.)
		# $3 ->  VCS Workspace Folder	(e.g. "/tmp/vcs_svn/cisVcsWorkspace/cis_objects")
		# $4 ->  VCS Temp Folder		(e.g. "/tmp/vcs_svn/cisVcsTemp")
		# $5 ->  CIS user name			(e.g. "admin")
		# $6 ->  CIS user password		(e.g. "admin")
		# $7 ->  CIS user domain		(e.g. "composite")
		# $8 ->  CIS server host name	(e.g. "localhost")
		# $9 ->  CIS server port		(e.g. "9400")
		#------------------------------------------
		# Execute the script
		#------------------------------------------
		echo "${PREFIX1}============= VCS CHECKOUT ============="
		"${VCS_SCRIPT_HOME}/vcs_checkin_checkout_${VCS_TYPE}/vcs_checkout.sh" ${resourcePath} ${resourceType} HEAD "${Workspace}"

		echo "${PREFIX1}============= CIS EXPORT ============="
		"${VCS_SCRIPT_HOME}/cis_import_export/vcs_export.sh" ${resourcePath} "${VcsTemp}" ${User} ${Password} ${Domain} ${Host} ${Port}

		echo "${PREFIX1}============= DIFFMERGER CHECKIN ============="
		"${VCS_SCRIPT_HOME}/diffmerger/checkin.sh" ${resourcePath} ${resourceType} "${Workspace}" "${VcsTemp}"

		echo "${PREFIX1}============= SUCCESSFULLY COMPLETED ${SCRIPT1} ============="
	*/
		String prefix = "prepare_checkin";
		try {
			CommonUtils.writeOutput("===== BEGIN VCS ["+vcsStruct.getVcsType()+"] checkout ====",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---VCS Arguments:",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      resourcePath=       "+resourcePath,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      resourceType=       "+resourceType,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsWorkspace=       "+vcsStruct.getVcsWorkspace(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsWorkspaceProject="+vcsStruct.getVcsWorkspaceProject(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsTemp=            "+vcsStruct.getVcsTemp(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsUser=            "+vcsStruct.getVcsUsername(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsPassword=        ********",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      serverId=           "+serverId,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      pathToServersXML=   "+pathToServersXML,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-debug3",logger,debug1,debug2,debug3);
	
			CommonUtils.writeOutput("==============  VCS CHECKOUT =============",prefix,"-debug3",logger,debug1,debug2,debug3);
			vcs_checkin_checkout__vcs_checkout(resourcePath, resourceType, null, "HEAD", vcsStruct);
			
			CommonUtils.writeOutput("==========  VCS CHECKOUT VALIDATE ========",prefix,"-debug2",logger,debug1,debug2,debug3);
	        vcs_checkout_validate(resourcePath, resourceType, vcsStruct);

			CommonUtils.writeOutput("=============== CIS EXPORT ===============",prefix,"-debug3",logger,debug1,debug2,debug3);
			cis_import_export__export(resourcePath, resourceType, vcsStruct, serverId, pathToServersXML);
					
			CommonUtils.writeOutput("=========== DIFFMERGER CHECKIN ===========",prefix,"-debug3",logger,debug1,debug2,debug3);
			diffmerger__checkin(resourcePath, resourceType, vcsStruct);
			
			CommonUtils.writeOutput("=== COMPLETED VCS ["+vcsStruct.getVcsType()+"] checkout ==",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-debug3",logger,debug1,debug2,debug3);
			
		} catch (CompositeException e) {
		    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
			logger.error("Failed executing "+prefix+".",e);
			throw new CompositeException(e.getMessage(), e);
		}
	}	
	
	// ***********************************************************************************************
	// Execute a VCS Generalized Scripts ** INITIALIZE CHECKIN **
	// ***********************************************************************************************
	private void initialize_checkin(String resourcePath, String resourceType, String message, String customPathList, VcsStruct vcsStruct) throws CompositeException {
	/*
	 * Initialize checkin is used to checkin the Composite base folder structure into VCS.  This is required in order to establish the baseline structure so
	 * that the user does not have to checkin the entire Composite repository at one time.  This allows the user to be more flexible to determine what folders
	 * they want to checkin within the /shared, /services/databases or /services/webservices folder structure.  The following steps are executed in order to achieve
	 * the initial checkin.
	 * 
	 * Copy base folders from /resources/vcs_initial/baseFolders/* into workspace $VCS_WORKSPACE_DIR/$VCS_PROJECT_ROOT
	 * 
	 * Add files: $VCS_WORKSPACE_DIR/$VCS_PROJECT_ROOT
	 * E:/dev/vcs/TEE-CLC-11.0.0/tf.cmd add R:\TFSww\Composite_62\cis_objects -recursive /login:user,********
	 * 
	 * Check-out Head: E:/dev/vcs/TEE-CLC-11.0.0/tf.cmd get R:/TFSww/Composite_62/cis_objects/ -version:T -recursive -noprompt /login:user,********
	 *
	 * Check-in:
	 *    Create a comment file in the /bin folder
	 *    Check-out lock:  E:/dev/vcs/TEE-CLC-11.0.0/tf.cmd checkout R:/TFSww/Composite_62/cis_objects/ -lock:Checkout -recursive -noprompt /login:user,********
	 *    Check-in:  E:/dev/vcs/TEE-CLC-11.0.0/tf.cmd checkin R:/TFSww/Composite_62/cis_objects/ -comment:@comment.txt -recursive -noprompt /login:user,********
	 * 
		# $1 ->  Resource path 			(e.g. "/shared/MyFolder/My__View"), using file system (encoded) names
		# $2 ->  Resource type 			(e.g. "CONTAINER", "TABLE", "PROCEDURE" etc.)
		# $3 ->  Checkin message 		(e.g. "Adding MyFolder")
		# $4 ->  VCS Workspace Folder	(e.g. "/tmp/vcs_svn/cisVcsWorkspace/cis_objects")
		# $5 ->  VCS Temp Folder		(e.g. "/tmp/vcs_svn/cisVcsTemp")
		# $6 ->  CIS user name			(e.g. "admin")
		# $7 ->  CIS user password		(e.g. "admin")
		# $8 ->  CIS user domain		(e.g. "composite")
		# $9 ->  CIS server host name	(e.g. "localhost
	*/
		String prefix = "initialize_checkin";
		String baseFolderPath = vcsStruct.getProjectHome()+"/resources/vcs_initial/baseFolders";
		String toFolderPath = vcsStruct.getVcsWorkspaceProject();
		String templateFolderPath = vcsStruct.getProjectHome()+"/resources/vcs_initial/templateFolder";
		
		try {
			CommonUtils.writeOutput("======== BEGIN VCS ["+vcsStruct.getVcsType()+"] checkin =========",prefix,"-info",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---VCS Arguments:",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      resourcePath=       "+resourcePath,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      resourceType=       "+resourceType,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      Message=            "+message,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      customPathList=     "+customPathList,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsWorkspace=       "+vcsStruct.getVcsWorkspace(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsWorkspaceProject="+vcsStruct.getVcsWorkspaceProject(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsTemp=            "+vcsStruct.getVcsTemp(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsUser=            "+vcsStruct.getVcsUsername(),prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VcsPassword=        ********",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      baseFolderPath=     "+baseFolderPath,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      toFolderPath=       "+toFolderPath,prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-debug3",logger,debug1,debug2,debug3);

			// Validate that the Project Folder Base Folder exists
			if (!CommonUtils.fileExists(baseFolderPath)) {
				throw new ApplicationException("The VCS Initialize Base Folder does not exist.  Base Folder="+baseFolderPath);
			}
			
			// Validate that the Template Folder File exists
			if (!CommonUtils.fileExists(templateFolderPath)) {
				throw new ApplicationException("The VCS Initialize Template File \"folder.cmf\" does not exist.  Path="+templateFolderPath);
			}

			CommonUtils.writeOutput("============== REMOVE BASE FOLDERS =================",prefix,"-debug3",logger,debug1,debug2,debug3);
			File fromFile = new File(baseFolderPath);
			File toWorkspaceFile = new File(toFolderPath);
			File toTempFile = new File(vcsStruct.getVcsTemp());
		    File dir = new File(baseFolderPath);
		    String excludeFileList = "";
		    boolean includeParentDir = false;
		    boolean includeFiles = true;
		    boolean includeDirs = true;
		    boolean recursive = false;
		    File[] files = CommonUtils.getFilesParent(dir, excludeFileList, includeParentDir, includeFiles, includeDirs, recursive);
			
			// Remove the folder/file from the workspace and vcs temp folder if it exists based on the lowest level resources found in \resources\vcs_initial\baseFolders
			if (CommonUtils.fileExists(toFolderPath)) {
				for (int i=0; i < files.length; i++) 
				{
					String removeWorkspaceFile = (toFolderPath + "/" + files[i].getName()).replaceAll("//", "/");
					String removeTempFile = (vcsStruct.getVcsTemp() + "/" + files[i].getName()).replaceAll("//", "/");
					if (removeWorkspaceFile.contains(".cmf")) 
					{
						CommonUtils.removeFile(removeTempFile);
						CommonUtils.removeFile(removeWorkspaceFile);
						CommonUtils.writeOutput("Removed      file \"to\" path["+removeWorkspaceFile+"]",prefix,"-debug3",logger,debug1,debug2,debug3);						
					} else {
						File remTempDir = new File(removeTempFile);
						CommonUtils.removeDirectory(remTempDir);
						File remWorkspaceDir = new File(removeWorkspaceFile);
						CommonUtils.removeDirectory(remWorkspaceDir);
						CommonUtils.writeOutput("Removed directory \"to\" path["+removeWorkspaceFile+"]",prefix,"-debug3",logger,debug1,debug2,debug3);
					}
				}
			}
			
			CommonUtils.writeOutput("============== VCS CHECKOUT ==============",prefix,"-debug3",logger,debug1,debug2,debug3);
			vcs_add__vcs_checkout(resourcePath, resourceType, null, "HEAD", vcsStruct);

			CommonUtils.writeOutput("============== COPY BASE FOLDERS ===================",prefix,"-debug3",logger,debug1,debug2,debug3);
			boolean forceCopy = false;
			CommonUtils.recursiveCopy(fromFile, toTempFile, forceCopy);
			CommonUtils.writeOutput("Recursively copied directories and files \"from\" path=["+baseFolderPath+"] \"to\"   vcsTemp path["+vcsStruct.getVcsTemp()+"]",prefix,"-debug3",logger,debug1,debug2,debug3);

			forceCopy = false;
			CommonUtils.recursiveCopy(fromFile, toWorkspaceFile, forceCopy);
			CommonUtils.writeOutput("Recursively copied directories and files \"from\" path=["+baseFolderPath+"] \"to\" workspace path["+toFolderPath+"]",prefix,"-debug3",logger,debug1,debug2,debug3);

			forceCopy = false;
			CommonUtils.processTemplateFolder(customPathList, templateFolderPath, vcsStruct.getVcsTemp(), forceCopy);
			CommonUtils.writeOutput("Processed custom folder paths \"custom\" path list=["+customPathList+"] \"to\"   vcsTemp path["+vcsStruct.getVcsTemp()+"]",prefix,"-debug3",logger,debug1,debug2,debug3);

			forceCopy = false;
			CommonUtils.processTemplateFolder(customPathList, templateFolderPath, toFolderPath, forceCopy);
			CommonUtils.writeOutput("Processed custom folder paths \"custom\" path list=["+customPathList+"] \"to\" workspace path["+toFolderPath+"]",prefix,"-debug3",logger,debug1,debug2,debug3);
			
			CommonUtils.writeOutput("============== VCS ADD ===================",prefix,"-debug3",logger,debug1,debug2,debug3);
			vcs_add__vcs_checkin(resourcePath, resourceType, vcsStruct.getVcsTemp(), vcsStruct);
			
			CommonUtils.writeOutput("============== VCS CHECKIN ===============",prefix,"-debug3",logger,debug1,debug2,debug3);
			vcs_checkin_checkout__vcs_checkin(resourcePath, resourceType, message, vcsStruct);
	
			CommonUtils.writeOutput("======= COMPLETED VCS ["+vcsStruct.getVcsType()+"] initial checkin ======",prefix,"-debug3",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-debug3",logger,debug1,debug2,debug3);
			
		} catch (CompositeException e) {
		    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
			logger.error("Failed executing "+prefix+".",e);
			throw new CompositeException(e.getMessage(), e);
		}
	}

/******************************************************************************************
 *  PRIVATE IMPLEMENTATION
 *  
 *  VCS CIS IMPORT EXPORT SCRIPTS [import, vcs_export]
 * 
 *******************************************************************************************/
	
// ***********************************************************************************************
// VCS CIS_Import_Export:: 
// ***********************************************************************************************

	// ***********************************************************************************************
	// Execute a VCS Generalized Scripts  ** CIS_Import_Export IMPORT **
	// ***********************************************************************************************
	private void cis_import_export__import(VcsStruct vcsStruct, String serverId, String pathToServersXML) throws CompositeException {
	/*
		# $1 -> VCS Temp Folder  (e.g. /tmp/workspaces/temp_CIS)
		# $2 -> CIS User 		 (e.g. admin)
		# $3 -> CIS Password 	 (e.g. admin)
		# $4 -> CIS Domain		 (e.g. composite)
		# $5 -> CIS Host		 (e.g. localhost)
		# $6 -> CIS Port		 (e.g. 9400)
	*/
		// 2014-05-28 mtinius: Externalized import options
		// Space separated list of Import Options
		String importCommandOptions = ""; //Default value was "-includeaccess";
		if (vcsStruct.getVcsCisImportOptions() != null)
			importCommandOptions = vcsStruct.getVcsCisImportOptions();

		String prefix = "cis_import_export__import";
		try {	
			// Get the server info from the servers.xml file
			CompositeServer serverInfo = WsApiHelperObjects.getServer(serverId, pathToServersXML);
			serverInfo = validateServerProperties(serverInfo);
			// Ping the Server to make sure it is alive and the values are correct.
			WsApiHelperObjects.pingServer(serverInfo, true);

			CommonUtils.writeOutput("Check files exists="+vcsStruct.getVcsTemp()+"/checkout.car",prefix,"-debug3",logger,debug1,debug2,debug3);
			
			if (CommonUtils.fileExists(vcsStruct.getVcsTemp()+"/checkout.car")) {

				//Derived from script:
				//"${INSTALL_DIR}/bin/pkg_import.sh" -pkgfile "${VcsTemp}/checkout.car" -user ${User} -password ${Password} -domain ${Domain} -server ${Host} -port ${Port}
				// Invoke the ArchiveModule pkg_import
				String arguments = "-pkgfile \""+vcsStruct.getVcsTemp()+"/checkout.car\" "+importCommandOptions+" -user "+serverInfo.getUser()+" -password "+serverInfo.getPassword()+" -domain "+serverInfo.getDomain()+" -server "+serverInfo.getHostname()+" -port "+String.valueOf(serverInfo.getPort())+((serverInfo.isUseHttps()) ? " -encrypt" : "");

				CommonUtils.writeOutput("Invoke vcsImportCommand with arguments="+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);

				getVCSDAO().vcsImportCommand(prefix, arguments, vcsStruct.getVcsIgnoreMessages(), propertyFile);

			} else {
				String msg = "Warning: Checkout.car does not exist.  Nothing to import.";
				CommonUtils.writeOutput(msg,prefix,"-debug3",logger,debug1,debug2,debug3);
				System.setProperty("MODULE_ACTION_MESSAGE", msg);
			}
			
		} catch (Exception e) {
		    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
			logger.error("Failed executing "+prefix+".",e);		
			throw new CompositeException(e.getMessage(), e);
		}

	}
	// ***********************************************************************************************
	// Execute a VCS Generalized Scripts  ** CIS_Import_Export EXPORT **
	// ***********************************************************************************************
	private void cis_import_export__export(String resourcePath, String resourceType, VcsStruct vcsStruct, String serverId, String pathToServersXML) throws CompositeException {
	/*
		# $1 -> Resource path 	 (e.g. /shared/MyFolder/My__View), using file system (encoded) names
		# $2 -> VCS Temp Folder  (e.g. /tmp/workspaces/temp_CIS)
		# $3 -> CIS User 		 (e.g. admin)
		# $4 -> CIS Password 	 (e.g. admin)
		# $5 -> CIS Domain		 (e.g. composite)
		# $6 -> CIS Host		 (e.g. localhost)
		# $7 -> CIS Port		 (e.g. 9400)
	*/
		// 2014-05-28 mtinius: Externalized export options
		// Space separated list of Export Options
		String exportCommandOptions = "";
		if (vcsStruct.getVcsCisExportOptions() != null)
			exportCommandOptions = vcsStruct.getVcsCisExportOptions();
				
		String prefix = "cis_import_export__export";
		try {
			// Get the server info from the servers.xml file
			CompositeServer serverInfo = WsApiHelperObjects.getServer(serverId, pathToServersXML);
			serverInfo = validateServerProperties(serverInfo);
			// Ping the Server to make sure it is alive and the values are correct.
			WsApiHelperObjects.pingServer(serverInfo, true);

			// vcs_export must have an empty directory to work properly
			//rmdir /S /Q %VcsTemp%
			removeDirectory(prefix, vcsStruct.getVcsTemp());
			
			//mkdir %VcsTemp%
			createDirectory(prefix, vcsStruct.getVcsTemp());
			
			// 2012-10-29 mtinius: added conversion just prior to invoking the export
			//   Convert "FOLDER" to "CONTAINER".
			//   Convert "definitions" to "DEFINITION_SET"
			//   Convert lower case to upper case so that the "ExportOptions.java" resourceType validation passes.
        	//    For "ExportOptions.checkResourceType()" to work, the only valid types are case sensitive and as follows:     
        	//         CONTAINER_OR_DATA_SOURCE, CONTAINER, DATA_SOURCE, DEFINITION_SET, LINK, PROCEDURE, TABLE, TREE, TRIGGER, RELATIONSHIP, MODEL, POLICY
			resourceType = getCisResourceTypeFromVcsResourceType(resourceType);

			// 2012-10-29 mtinius: add -resourceType
			//Derived from script:
			//"%INSTALL_DIR%\bin\vcs_export.bat" -tempDir %VcsTemp% -user %User% -password %Password% -domain %Domain% -server %Host% -port %Port% -useFileSystemNames -resourceType %resourceType% %resourcePath%
			String arguments = "-tempDir \""+vcsStruct.getVcsTemp()+"\" "+exportCommandOptions + " -user "+serverInfo.getUser()+" -password "+serverInfo.getPassword()+" -domain "+serverInfo.getDomain()+" -server "+serverInfo.getHostname()+" -port "+String.valueOf(serverInfo.getPort())+((serverInfo.isUseHttps()) ? " -encrypt" : "")+" -useFileSystemNames "+" -resourceType \""+resourceType+"\" "+resourcePath;

			CommonUtils.writeOutput("Invoke vcsExportCommand with arguments="+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);

			getVCSDAO().vcsExportCommand(prefix, arguments, vcsStruct.getVcsIgnoreMessages(), propertyFile);

		} catch (Exception e) {
		    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
			logger.error("Failed executing "+prefix+".",e);
			throw new ApplicationException(e.getMessage(), e);
		}
	}	
	
	
/******************************************************************************************
 *  PRIVATE IMPLEMENTATION
 *  
 *  VCS DIFFMERGER SCRIPTS [checkin, rollback]
 * 
 *******************************************************************************************/
	
	// ***********************************************************************************************
	// Execute a VCS Generalized Scripts  ** Diffmerger CHECKIN **
	// ***********************************************************************************************
	private void diffmerger__checkin(String resourcePath, String resourceType, VcsStruct vcsStruct) throws CompositeException {
	/*
		# $1 -> Resource path 		 (e.g. /shared/MyFolder/My__View), using file system (encoded) names
		# $2 -> Resource type 		 (e.g. FOLDER, table etc.)
		# $3 -> VCS Workspace Folder (e.g. /tmp/workspaces/workspace_CIS)
		# $4 -> VCS Temp Folder 	 (e.g. /tmp/workspaces/temp_CIS)
	*/
		String prefix = "diffmerger__checkin";
		
		// LifecycleListener must be set prior to invocation - depends on VCS_TYPE
		// com.compositesw.cmdline.vcs.spi.LifecycleListener=com.compositesw.cmdline.vcs.spi.svn.SVNLifecycleListener
		// com.compositesw.cmdline.vcs.spi.LifecycleListener=com.compositesw.cmdline.vcs.spi.p4.P4LifecycleListener
		// com.compositesw.cmdline.vcs.spi.LifecycleListener=com.compositesw.cmdline.vcs.spi.cvs.CVSLifecycleListener
		// com.compositesw.cmdline.vcs.spi.LifecycleListener=com.compositesw.cmdline.vcs.spi.tfs.TFSLifecycleListener
		// com.compositesw.cmdline.vcs.spi.LifecycleListener=com.compositesw.cmdline.vcs.spi.git.GITLifecycleListener
		System.setProperty(LifecycleListener.SYSTEM_PROPERTY, vcsStruct.getVcsLifecycleListener());
		
		// Set System environment properties for VCS_EXEC, VCS_OPTIONS, and VCS_ENV 
		//    Note: VCS_ENV - pipe separated list VAR1=val1|VAR2=val2|VAR3=val3
		System.setProperty("VCS_EXEC", vcsStruct.getVcsExecCommand());
		System.setProperty("VCS_OPTIONS", vcsStruct.getVcsOptions());
		System.setProperty("VCS_ENV", vcsStruct.getVcsEnvironment());
		
		String diffMergerOptions = "";
		if (diffmergerVerbose) {
			diffMergerOptions = "-verbose";
		} else if (diffmergerVerboseChanges) {
			diffMergerOptions = "-verbosechanges";
		}
		try {
			// 2012-10-29 mtinius: differentiate between folder and data_source
			if (resourceType.equalsIgnoreCase("FOLDER") || resourceType.equalsIgnoreCase("data_source")) {
				//------------------------------------
				// Perform DiffMerger on FOLDER
				//------------------------------------
				//Derived from script:
				//"${INSTALL_DIR}/bin/vcs_diffmerger.sh" -from "${Workspace}" -to "${VcsTemp}" -notifyVCS -verbose -selector ${resourcePath}
				//	  e.g: vcsWorkspaceProject:  D:/PDTool/<vcs_type>_workspace/cis_objects
				String arguments = "-from \""+vcsStruct.getVcsWorkspaceProject()+"\" -to \""+vcsStruct.getVcsTemp()+"\" -notifyVCS "+diffMergerOptions+" -selector "+resourcePath;

				CommonUtils.writeOutput("Invoke vcsDiffMergerCommand with arguments="+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);

				getVCSDAO().vcsDiffMergerCommand(prefix, arguments, vcsStruct.getVcsIgnoreMessages(), propertyFile);
				
			} else {
				//------------------------------------
				// Perform DiffMerger on FILE
				//------------------------------------
				//Derived from script:
				//"${INSTALL_DIR}/bin/vcs_diffmerger.sh" -from "${Workspace}" -to "${VcsTemp}" -notifyVCS -verbose -selector  ${resourcePath}_${resourceType}.cmf
				//	 e.g: vcsWorkspaceProject:  D:/PDTool/<vcs_type>_workspace/cis_objects
				String arguments = "-from \""+vcsStruct.getVcsWorkspaceProject()+"\" -to \""+vcsStruct.getVcsTemp()+"\" -notifyVCS "+diffMergerOptions+" -selector "+resourcePath+"_"+resourceType+".cmf";

				CommonUtils.writeOutput("Invoke vcsDiffMergerCommand with arguments="+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);

				getVCSDAO().vcsDiffMergerCommand(prefix, arguments, vcsStruct.getVcsIgnoreMessages(), propertyFile);
			}
			
		} catch (Exception e) {
		    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
			logger.error("Failed executing "+prefix+".",e);
			throw new CompositeException(e.getMessage(), e);
		}	
	}
	// ***********************************************************************************************
	// Execute a VCS Generalized Scripts  ** Diffmerger ROLLBACK **
	// ***********************************************************************************************
	private void diffmerger__rollback(String resourcePath, String resourceType, VcsStruct vcsStruct) throws CompositeException {
	/*
		# $1 -> Resource path 		 (e.g. /shared/MyFolder/My__View), using file system (encoded) names
		# $2 -> Resource type 		 (e.g. FOLDER, table etc.)
		# $3 -> VCS Workspace Folder (e.g. /tmp/workspaces/workspace_CIS)
		# $4 -> VCS Temp Folder 	 (e.g. /tmp/workspaces/temp_CIS)
	*/
		String prefix = "diffmerger__rollback";
		
		// LifecycleListener must be set prior to invocation - depends on VCS_TYPE
		// com.compositesw.cmdline.vcs.spi.LifecycleListener=com.compositesw.cmdline.vcs.spi.svn.SVNLifecycleListener
		// com.compositesw.cmdline.vcs.spi.LifecycleListener=com.compositesw.cmdline.vcs.spi.p4.P4LifecycleListener
		// com.compositesw.cmdline.vcs.spi.LifecycleListener=com.compositesw.cmdline.vcs.spi.cvs.CVSLifecycleListener
		// com.compositesw.cmdline.vcs.spi.LifecycleListener=com.compositesw.cmdline.vcs.spi.tfs.TFSLifecycleListener
		// com.compositesw.cmdline.vcs.spi.LifecycleListener=com.compositesw.cmdline.vcs.spi.git.GITLifecycleListener
		System.setProperty(LifecycleListener.SYSTEM_PROPERTY, vcsStruct.getVcsLifecycleListener());
		
		// Set System environment properties for VCS_EXEC, VCS_OPTIONS, and VCS_ENV 
		//    Note: VCS_ENV - pipe separated list VAR1=val1|VAR2=val2|VAR3=val3
		System.setProperty("VCS_EXEC", vcsStruct.getVcsExecCommand());
		System.setProperty("VCS_OPTIONS", vcsStruct.getVcsOptions());
		System.setProperty("VCS_ENV", vcsStruct.getVcsEnvironment());

		// 2014-09-03 (cgoodric): Git operations must be executed from within the Git workspace
		//
		System.setProperty("VCS_EXEC_DIR", vcsStruct.getVcsWorkspace());

		String diffMergerOptions = "";
		if (diffmergerVerbose) {
			diffMergerOptions = "-verbose";
		} else if (diffmergerVerboseChanges) {
			diffMergerOptions = "-verbosechanges";
		}
		try {
			// 2012-10-29 mtinius: differentiate between folder and data_source
			if (resourceType.equalsIgnoreCase("FOLDER") || resourceType.equalsIgnoreCase("data_source")) {
				//------------------------------------
				// Perform DiffMerger on FOLDER
				//------------------------------------
				//Derived from script:
				//"${INSTALL_DIR}/bin/vcs_diffmerger.sh" -from "${VcsTemp}" -to "${Workspace}" -verbose -selector ${resourcePath}
				//	 e.g: vcsWorkspaceProject:  D:/PDTool/<vcs_type>_workspace/cis_objects
				String arguments = "-from \""+vcsStruct.getVcsTemp()+"\" -to \""+vcsStruct.getVcsWorkspaceProject()+"\" "+diffMergerOptions+" -selector "+resourcePath;

				CommonUtils.writeOutput("Invoke vcsDiffMergerCommand with arguments="+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);

				getVCSDAO().vcsDiffMergerCommand(prefix, arguments, vcsStruct.getVcsIgnoreMessages(), propertyFile);
				
			} else {
				//------------------------------------
				// Perform DiffMerger on FILE
				//------------------------------------
				//Derived from script:
				//"${INSTALL_DIR}/bin/vcs_diffmerger.sh" -from "${VcsTemp}" -to "${Workspace}" -verbose -selector  ${resourcePath}_${resourceType}.cmf
				//	 e.g: vcsWorkspaceProject:  D:/PDTool/<vcs_type>_workspace/cis_objects
				String arguments = "-from \""+vcsStruct.getVcsTemp()+"\" -to \""+vcsStruct.getVcsWorkspaceProject()+"\" "+diffMergerOptions+" -selector "+resourcePath+"_"+resourceType+".cmf";

				CommonUtils.writeOutput("Invoke vcsDiffMergerCommand with arguments="+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);

				getVCSDAO().vcsDiffMergerCommand(prefix, arguments, vcsStruct.getVcsIgnoreMessages(), propertyFile);
				
			}
			
		} catch (Exception e) {
		    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
			logger.error("Failed executing "+prefix+".",e);
			throw new CompositeException(e.getMessage(), e);
		}
	}		

/******************************************************************************************
 *  PRIVATE IMPLEMENTATION
 *  
 *  VCS CHECKIN/CHECKOUT [vcs_checkin, vcs_checkout]
 *  
 *  Specific implementation for a particular Version Control System (VCS)
 * 
 *******************************************************************************************/

	// ***********************************************************************************************
	// Execute a VCS Generalized Scripts  ** vcs_checkin_checkout_cvs vcs_checkin **
	// ***********************************************************************************************
	private void vcs_checkin_checkout__vcs_checkin(String resourcePath, String resourceType, String message, VcsStruct vcsStruct) throws CompositeException {
	/*
		# $1 -> Resource path 		 (e.g. /shared/MyFolder/My__View), using file system (encoded) names
		# $2 -> Resource type 		 (e.g. FOLDER, table etc.)
		# $3 -> Checkin message 	 (e.g. Adding MyFolder)
		# $4 -> VCS Workspace Folder (e.g. /tmp/workspaces/workspace_CIS)
	*/
		String prefix = "vcs_checkin_checkout__vcs_checkin_"+vcsStruct.getVcsType();
	    List<String> argList = new ArrayList<String>();
	    List<String> envList = new ArrayList<String>();
	    boolean initArgList = true;
	    boolean preserveQuotes = false;
	    String command = vcsStruct.getVcsExecCommand();
	    String arguments = null;
	    String execFromDir = null;
	    String commandDesc = null;
	    String commentFilePath = null;
	    if (message == null)
	    	message = "";
	    
		try {
			/********************************************
			 * [CVS] vcs_checkin_checkout__vcs_checkin: 
			 *     CVS=Concurrent Versions System
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("CVS")) {
				//cd "${Workspace}"
				//	 e.g: vcsWorkspaceProject:  D:/PDTool/cvs_workspace/cis_objects
				execFromDir = vcsStruct.getVcsWorkspaceProject();
				
				// 2012-10-29 mtinius: differentiate between folder and data_source
				if (resourceType.equalsIgnoreCase("FOLDER") || resourceType.equalsIgnoreCase("data_source")) {
					//------------------------------------------
					// Check in Folder
					//------------------------------------------
					
				    // Validate the VCS_CHECKIN_OPTIONS against the VCS_CHECKIN_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckinRequired(vcsStruct.getVcsCheckinOptions(), vcsStruct.getVcsCheckinOptionsRequired());
				    
					String fullResourcePath = (execFromDir+"/"+resourcePath).replaceAll("//", "/");
					//Derived from script:
					// cvs commit -m "${Message}" ${fullResourcePath} ${VCS_CHECKIN_OPTIONS}
					arguments=" commit -m \""+message+"\" "+fullResourcePath + " " + vcsStruct.getVcsCheckinOptions();
					// cvs commit -m ${Message} ./${resourcePath} ${VCS_CHECKIN_OPTIONS}
					// (previous code): arguments=" commit -m \""+message+"\" ./"+resourcePath + " " + vcsStruct.getVcsCheckinOptions();
					
					commandDesc = "    Commit folder changes to the CVS Repository...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());

				} else {
					//------------------------------------------
					// Check in File
					//------------------------------------------

				    // Validate the VCS_CHECKIN_OPTIONS against the VCS_CHECKIN_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckinRequired(vcsStruct.getVcsCheckinOptions(), vcsStruct.getVcsCheckinOptionsRequired());
				    
					String fullResourcePath = (execFromDir+"/"+resourcePath+"_"+resourceType+".cmf").replaceAll("//", "/");
					//Derived from script:
					// cvs commit -m "${Message}" ${fullResourcePath} ${VCS_CHECKIN_OPTIONS}
					arguments=" commit -m \""+message+"\" "+fullResourcePath+" " + vcsStruct.getVcsCheckinOptions();
					// cvs commit -m ${Message} ./${resourcePath}_${resourceType}.cmf ${VCS_CHECKIN_OPTIONS}
					// (previous code): arguments=" commit -m \""+message+"\" ./"+resourcePath+"_"+resourceType+".cmf" + " " + vcsStruct.getVcsCheckinOptions();
					
					commandDesc = "    Commit file changes to the CVS Repository...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
				}
			}
			
			/********************************************
			 * [P4] vcs_checkin_checkout__vcs_checkin:
			 *      P4=Perforce
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("P4")) {
				//cd "${Workspace}"
				//   e.g: vcsWorkspaceProject:  D:/PDTool/p4_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();

				//------------------------------------------
				// Submit the changelist to perforce
				//------------------------------------------

			    // Validate the VCS_CHECKIN_OPTIONS against the VCS_CHECKIN_OPTIONS_REQUIRED and throw an exception if a required option is not found
			    validateCheckinRequired(vcsStruct.getVcsCheckinOptions(), vcsStruct.getVcsCheckinOptionsRequired());

				/* 
				 * mtinius: 2012-02-22 - modified command to include a concatenated message [message + CIS path]
				 *		This syntax did not include the passed in message but used the path as the message
				 *			arguments=" submit -d ./"+resourcePath;
				 *
				 *		This syntax is not correct and results in errors and no files being submitted.
				 *			arguments=" submit -d \""+message+"\" ./"+resourcePath;
				 *			Error=No files to submit from the default changelist.
				 *			<vcs_checkin_checkout__vcs_checkin_p4::::Error message ignored.  Error Message matches VCS_IGNORE_MESSAGES=No files to submit
				 *
				 *		This syntax works and combines the message and path into a single quoted message
				 *			arguments=" submit -d \""+message+" ./"+resourcePath+"\"";
				 *			example: submit -d "changed procedure .//shared/testsvn"
				 */		    
				String fullResourcePath = (execFromDir+"/"+resourcePath).replaceAll("//", "/");
				//Derived from script:
				// p4 submit -d "${Message}" ${fullResourcePath} ${VCS_CHECKIN_OPTIONS}
				arguments=" submit -d \""+message+" "+fullResourcePath+"\"" + " " + vcsStruct.getVcsCheckinOptions();
				// p4 submit -d "${Message}" ./${resourcePath} ${VCS_CHECKIN_OPTIONS}
				// (previous code): arguments=" submit -d \""+message+" ./"+resourcePath+"\"" + " " + vcsStruct.getVcsCheckinOptions();
				preserveQuotes = true;
				
				commandDesc = "    Submit the change list to the Perforce Repository...";
				CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
				CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
				
			    // Parse the command arguments into a list
			    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
			    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
			    
			    // Execute the command line
			    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
			}

			
			/********************************************
			 * [SVN] vcs_checkin_checkout__vcs_checkin:
			 *      SVN=Subversion
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("SVN")) {
				//cd "${Workspace}"
				//   e.g: vcsWorkspaceProject:  D:/PDTool/svn_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
				
				// 2012-10-29 mtinius: differentiate between folder and data_source
				if (resourceType.equalsIgnoreCase("FOLDER") || resourceType.equalsIgnoreCase("data_source")) {
					//------------------------------------------
					// Check in Folder
					//------------------------------------------
					
				    // Validate the VCS_CHECKIN_OPTIONS against the VCS_CHECKIN_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckinRequired(vcsStruct.getVcsCheckinOptions(), vcsStruct.getVcsCheckinOptionsRequired());
				    
					String fullResourcePath = (execFromDir+"/"+resourcePath).replaceAll("//", "/");
					//Derived from script:
				    // svn commit ${fullResourcePath} -m "${Message}" ${SVN_AUTH} ${VCS_OPTIONS} ${VCS_CHECKIN_OPTIONS}
					arguments=" commit "+fullResourcePath+" -m \""+message+"\" "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckinOptions();
				    // svn commit ./${resourcePath} -m "${Message}" ${SVN_OPTIONS} ${SVN_AUTH}
					// (previous code): arguments=" commit ./"+resourcePath+" -m \""+message+"\" "+vcsStruct.getVcsOptions();

					commandDesc = "    Commit folder changes to the Subversion Repository...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					
				} else {
					//------------------------------------------
					// Check in File
					//------------------------------------------
					
				    // Validate the VCS_CHECKIN_OPTIONS against the VCS_CHECKIN_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckinRequired(vcsStruct.getVcsCheckinOptions(), vcsStruct.getVcsCheckinOptionsRequired());
				    
					String fullResourcePath = (execFromDir+"/"+resourcePath+"_"+resourceType+".cmf").replaceAll("//", "/");
					//Derived from script:
				    // svn commit ${fullResourcePath} -m "${Message}"  ${SVN_AUTH} ${VCS_OPTIONS} ${VCS_CHECKIN_OPTIONS}
					arguments=" commit "+fullResourcePath+" -m \""+message+"\" "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckinOptions();
				    // svn commit ./${resourcePath}_${resourceType}.cmf -m "${Message}" ${SVN_OPTIONS} ${SVN_AUTH}
					// (previous code): arguments=" commit ./"+resourcePath+"_"+resourceType+".cmf -m \""+message+"\" "+vcsStruct.getVcsOptions();

					commandDesc = "    Commit file changes to the Subversion Repository...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
				}
			}
			
			/********************************************
			 * [TFS] vcs_checkin_checkout__vcs_checkin:
			 *      TFS=Team Foundation Server
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("TFS2010") || 
				vcsStruct.getVcsType().equalsIgnoreCase("TFS2012") ||
				vcsStruct.getVcsType().equalsIgnoreCase("TFS2013") ||
				vcsStruct.getVcsType().equalsIgnoreCase("TFS2005")) 
			{
				//cd "${Workspace}"
				//	 e.g: vcsWorkspaceProject:  D:/PDTool/tfs_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();			
				preserveQuotes = true;
				String filename = null;
				
				// 2012-10-29 mtinius: differentiate between folder and data_source
				if (resourceType.equalsIgnoreCase("FOLDER") || resourceType.equalsIgnoreCase("data_source")) {
					
				    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());

				    // Validate the VCS_CHECKIN_OPTIONS against the VCS_CHECKIN_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckinRequired(vcsStruct.getVcsCheckinOptions(), vcsStruct.getVcsCheckinOptionsRequired());
				    
					//------------------------------------------
					// Check out Folder for editing
					//------------------------------------------

				    String fullResourcePath = (execFromDir+"/"+resourcePath).replaceAll("//", "/");
					//Derived from script:
				    // tf.cmd checkout ${fullResourcePath} -lock:Checkout -recursive -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS} ${VCS_CHECKOUT_OPTIONS}
					arguments=" checkout "+fullResourcePath+" -lock:Checkout -recursive -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckoutOptions();
				    // tf.cmd checkout ./${resourcePath} -lock:Checkout -recursive -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS}
					//(previous code:) arguments=" checkout ./"+resourcePath+" -lock:Checkout -recursive -noprompt "+vcsStruct.getVcsOptions();

					commandDesc = "    Checkout for editing the folder from the Team Foundation Server Repository...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
					//logger.debug("TFS Unparsed Arguments: " + arguments);
					
					// Only checkin the resource folder workspace when TFS_USE_EXISTING_WORKSPACE=false
			        if (vcsStruct.getVcsUseExistingWorkdspace().equals("true")) {
			        	if (logger.isInfoEnabled()) {
			        		logger.info("Warning: Resource folder will not be checked-in when variable \"TFS_USE_EXISTING_WORKSPACE=true\" is set.");
			        		logger.info("Warning: User must commit resources using TFS Team Explorer.");
			        	}
			        } else {

			        	// Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    //logger.debug("TFS Argument List: " + argList);
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
						
						//------------------------------------------
						// Check in Folder
						//------------------------------------------
					    
						// Create a file in the execution directory with the checkin message
						filename = CommonUtils.getUniqueFilename("comment", "txt");
						commentFilePath = (execFromDir+"/"+filename).replaceAll("//", "/");
						CommonUtils.createFileWithContent(commentFilePath, message);
						String commentCommand = " -comment:@"+filename;
						
						//Derived from script:				
					    // tf.cmd checkin ${fullResourcePath} -comment:@${filename} -recursive -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS} ${VCS_CHECKIN_OPTIONS}
						arguments=" checkin "+fullResourcePath+commentCommand+" -recursive -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckinOptions();
					    // tf.cmd checkin ./${resourcePath} -comment:@${filename} -recursive -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS}
						//(previous code:) arguments=" checkin ./"+resourcePath+commentCommand+" -recursive -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getTfsCheckinOptions();
	
						commandDesc = "    Commit folder changes to the Team Foundation Server Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
						//logger.debug("TFS Unparsed Arguments: " + arguments);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    //logger.debug("TFS Argument List: " + argList);
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());

					    // Remove the comment file
						boolean result = CommonUtils.removeFile(commentFilePath);
						
						//------------------------------------------
						// Status of Folder - Get Pending changes
						//------------------------------------------
					    
						// Get the status which will provide a list of pending changes for the parent folders that were checked in but not committed.
					    //   The use case is occurs when a folder is checked in but the parent folder lineage is new and therefore does not yet exist
					    //   in TFS.  The folder.cmf files get placed into TFS in a pending state but are not committed.  This process is intended
					    //   to get the list of pending changes and then commit them if they fall within the parent path of the fullResourcePath.

					    //arguments=" status -collection:"+vcsStruct.getVcsRepositoryUrl()+ " -workspace:" +
						arguments=" status -collection:"+vcsStruct.getVcsRepositoryUrl()+ " -workspace:" +
							vcsStruct.getVcsWorkspaceName() +" -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckinOptions();
					    // tf.cmd status -collection:{VCS_REPOSITORY_URL} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS}
						
						commandDesc = "    Status of pending changes to the Team Foundation Server Repository...";					
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    StringBuilder stdout = getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					    
						// Determine if there is any status messages containing an "add" status which indicates a pending change that is not committed.
					    // This results from a file or folder that is committed but the parent folder or folders have not yet been committed.
					    if (stdout != null) {
					    	try {
						    	String vcsout = stdout.toString();
						    	StringReader is = new StringReader(vcsout);

								BufferedReader reader = new BufferedReader(is);
								String line = null;
								int lnCount = 0;
								String statusFilepath = null;
								// Iterate through the orchestration property file
								while ( (line = reader.readLine()) != null) {
									lnCount++;
							    	int idx = line.indexOf(" add ");
									if (idx >= 0) {
										String chkinFilename = line.substring(0, idx).trim();
										statusFilepath = line.substring(idx+5, line.length()).trim();
										statusFilepath = statusFilepath.replaceAll(Matcher.quoteReplacement("\\\\"), "/");
										statusFilepath = statusFilepath.replaceAll(Matcher.quoteReplacement("\\"), "/");

										// Get the next parent directory to validate
										String statusFilePathDir = CommonUtils.getDirectory(statusFilepath);

										// Only commit when the status pending path is contained within the full resource path of the original resource.
										if (fullResourcePath.contains(statusFilePathDir)) {
											//------------------------------------------
											// Check in File for Parent Lineage Folder
											//------------------------------------------
										    
											// Create a file in the execution directory with the checkin message
											filename = CommonUtils.getUniqueFilename("comment", "txt");
											commentFilePath = (execFromDir+"/"+filename).replaceAll("//", "/");
											CommonUtils.createFileWithContent(commentFilePath, message+"  Check-in parent folder="+chkinFilename);
											commentCommand = " -comment:@"+filename;
											
											//Derived from script:
										    // tf.cmd checkin ${fullResourcePath} -comment:@${filename} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS} ${VCS_CHECKIN_OPTIONS}
											arguments=" checkin "+statusFilepath+commentCommand+" -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckinOptions();
										    // tf.cmd checkin ./${resourcePath}_${resourceType}.cmf -comment:@${filename} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS}
											//(previous code:) arguments=" checkin ./"+resourcePath+"_"+resourceType+".cmf"+commentCommand+" -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getTfsCheckinOptions();
											
											commandDesc = "    Commit parent lineage folder changes to the Team Foundation Server Repository...";					
											CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
											CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
											CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
											
										    // Parse the command arguments into a list
										    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
										    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
										    
										    // Execute the command line
										    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());

										    // Remove the comment file
											result = CommonUtils.removeFile(commentFilePath);
										}
									}
								}
					    	} 
					    	catch (Exception e) {
								throw new CompositeException(e.getMessage(), e);
					    	}
					    } // if (stdout != null)
			        }
			        
				} else {
					
				    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());

				    // Validate the VCS_CHECKIN_OPTIONS against the VCS_CHECKIN_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckinRequired(vcsStruct.getVcsCheckinOptions(), vcsStruct.getVcsCheckinOptionsRequired());

					//------------------------------------------
					// Check out File for editing
					//------------------------------------------

					String fullResourcePath = (execFromDir+"/"+resourcePath+"_"+resourceType+".cmf").replaceAll("//", "/");
					//Derived from script:
				    // tf.cmd checkout ${fullResourcePath} -lock:Checkout -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS} ${VCS_CHECKOUT_OPTIONS}
					arguments=" checkout "+fullResourcePath+" -lock:Checkout -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckoutOptions();
				    // tf.cmd checkout ./${resourcePath}_${resourceType}.cmf -lock:Checkout -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS}
					//(previous code:) arguments=" checkout ./"+resourcePath+"_"+resourceType+".cmf -lock:Checkout -noprompt "+vcsStruct.getVcsOptions();
					
					commandDesc = "    Checkout for editing the file from the Team Foundation Server Repository...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
					// Only checkin the resource file workspace when TFS_USE_EXISTING_WORKSPACE=false
			        if (vcsStruct.getVcsUseExistingWorkdspace().equals("true")) {
			        	if (logger.isInfoEnabled()) {
			        		logger.info("Warning: Resource file will not be checked-in when variable \"TFS_USE_EXISTING_WORKSPACE=true\" is set.");
			        		logger.info("Warning: User must commit resources using TFS Team Explorer.");
			        	}
			        } else {
			        	
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					    
						//------------------------------------------
						// Check in File
						//------------------------------------------
					    
						// Create a file in the execution directory with the checkin message
						filename = CommonUtils.getUniqueFilename("comment", "txt");
						commentFilePath = (execFromDir+"/"+filename).replaceAll("//", "/");
						CommonUtils.createFileWithContent(commentFilePath, message);
						String commentCommand = " -comment:@"+filename;
						
						//Derived from script:
					    // tf.cmd checkin ${fullResourcePath} -comment:@${filename} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS} ${VCS_CHECKIN_OPTIONS}
						arguments=" checkin "+fullResourcePath+commentCommand+" -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckinOptions();
					    // tf.cmd checkin ./${resourcePath}_${resourceType}.cmf -comment:@${filename} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS}
						//(previous code:) arguments=" checkin ./"+resourcePath+"_"+resourceType+".cmf"+commentCommand+" -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getTfsCheckinOptions();
						
						commandDesc = "    Commit file changes to the Team Foundation Server Repository...";					
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());

					    // Remove the comment file
						boolean result = CommonUtils.removeFile(commentFilePath);

						//------------------------------------------
						// Status of File - Get Pending changes
						//------------------------------------------
					    
						// Get the status which will provide a list of pending changes for the parent folders that were checked in but not committed.
					    //   The use case is occurs when a file is checked in but the parent folder lineage is new and therefore does not yet exist
					    //   in TFS.  The folder.cmf files get placed into TFS in a pending state but are not committed.  This process is intended
					    //   to get the list of pending changes and then commit them if they fall within the parent path of the fullResourcePath.

					    //arguments=" status -collection:"+vcsStruct.getVcsRepositoryUrl()+ " -workspace:" +
						arguments=" status -collection:"+vcsStruct.getVcsRepositoryUrl()+ " -workspace:" +
							vcsStruct.getVcsWorkspaceName() +" -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckinOptions();
					    // tf.cmd status --collection:{VCS_REPOSITORY_URL} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS}
						
						commandDesc = "    Status of pending changes to the Team Foundation Server Repository...";					
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    StringBuilder stdout = getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					    
						// Determine if there is any status messages containing an "add" status which indicates a pending change that is not committed.
					    // This results from a file or folder that is committed but the parent folder or folders have not yet been committed.
					    if (stdout != null) {
					    	try {
						    	String vcsout = stdout.toString();
						    	StringReader is = new StringReader(vcsout);

								BufferedReader reader = new BufferedReader(is);
								String line = null;
								int lnCount = 0;
								String statusFilepath = null;
								// Iterate through the orchestration property file
								while ( (line = reader.readLine()) != null) {
									lnCount++;
							    	int idx = line.indexOf(" add ");
									if (idx >= 0) {
										String chkinFilename = line.substring(0, idx).trim();
										statusFilepath = line.substring(idx+5, line.length()).trim();
										statusFilepath = statusFilepath.replaceAll(Matcher.quoteReplacement("\\\\"), "/");
										statusFilepath = statusFilepath.replaceAll(Matcher.quoteReplacement("\\"), "/");

										// Get the next parent directory to validate
										String statusFilePathDir = CommonUtils.getDirectory(statusFilepath);

										// Only commit when the status pending path is contained within the full resource path of the original resource.
										if (fullResourcePath.contains(statusFilePathDir)) {
											//------------------------------------------
											// Check in File for Parent Lineage Folder
											//------------------------------------------
										    
											// Create a file in the execution directory with the checkin message
											filename = CommonUtils.getUniqueFilename("comment", "txt");
											commentFilePath = (execFromDir+"/"+filename).replaceAll("//", "/");
											CommonUtils.createFileWithContent(commentFilePath, message+"  Check-in parent folder="+chkinFilename);
											commentCommand = " -comment:@"+filename;
											
											//Derived from script:
										    // tf.cmd checkin ${fullResourcePath} -comment:@${filename} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS} ${VCS_CHECKIN_OPTIONS}
											arguments=" checkin "+statusFilepath+commentCommand+" -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckinOptions();
										    // tf.cmd checkin ./${resourcePath}_${resourceType}.cmf -comment:@${filename} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS}
											//(previous code:) arguments=" checkin ./"+resourcePath+"_"+resourceType+".cmf"+commentCommand+" -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getTfsCheckinOptions();
											
											commandDesc = "    Commit parent lineage file changes to the Team Foundation Server Repository...";					
											CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
											CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
											CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
											
										    // Parse the command arguments into a list
										    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
										    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
										    
										    // Execute the command line
										    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());

										    // Remove the comment file
											result = CommonUtils.removeFile(commentFilePath);
										}
									}
								}
					    	} 
					    	catch (Exception e) {
								throw new CompositeException(e.getMessage(), e);
					    	}
					    } // if (stdout != null)
			        }
				}
			}
			
			/********************************************
			 * [GIT] vcs_checkin_checkout__vcs_checkin:
			 *      GIT=Git
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("GIT")) {
				//cd "${Workspace}"
				//   e.g: vcsWorkspaceProject:  D:/PDTool/GITuw/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
				
				// 2012-10-29 mtinius: differentiate between folder and data_source
				if (resourceType.equalsIgnoreCase("FOLDER") || resourceType.equalsIgnoreCase("data_source")) {
					//------------------------------------------
					// Check in Folder
					//------------------------------------------
					
				    // Validate the VCS_CHECKIN_OPTIONS against the VCS_CHECKIN_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckinRequired(vcsStruct.getVcsCheckinOptions(), vcsStruct.getVcsCheckinOptionsRequired());
				    
					String fullResourcePath = (execFromDir+"/"+resourcePath).replaceAll("//", "/");
					//Derived from script:
				    // git commit -m "${Message}" ${VCS_OPTIONS} ${VCS_CHECKIN_OPTIONS} ${fullResourcePath}
					arguments = " commit -m \"" + message + "\" " + vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckinOptions() + " " + fullResourcePath;

					commandDesc = "    Commit folder changes to the local Git clone ...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					
				    // git push
					arguments = " push";

					commandDesc = "    Push folder changes to the Git Repository ...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());

				} else {
					//------------------------------------------
					// Check in File
					//------------------------------------------
					
				    // Validate the VCS_CHECKIN_OPTIONS against the VCS_CHECKIN_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckinRequired(vcsStruct.getVcsCheckinOptions(), vcsStruct.getVcsCheckinOptionsRequired());
				    
					String fullResourcePath = (execFromDir+"/"+resourcePath+"_"+resourceType+".cmf").replaceAll("//", "/");
					//Derived from script:
				    // git commit -m "${Message}"  ${SVN_AUTH} ${VCS_OPTIONS} ${VCS_CHECKIN_OPTIONS} ${fullResourcePath}
					arguments = " commit -m \"" + message + "\" "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckinOptions() + " " + fullResourcePath;

					commandDesc = "    Commit file changes to the local Git clone ...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());

				    // git push
					arguments = " push";

					commandDesc = "    Push file changes to the Git Repository ...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
				}
			}

			/********************************************
			 * [CLC] vcs_checkin_checkout__vcs_checkin:
			 *      CLC=Clearcase
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("CLC")) {
				//cd "${Workspace}"
				//   e.g: vcsWorkspaceProject:  D:/PDTool/svn_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
				
				// 2012-10-29 mtinius: differentiate between folder and data_source
				if (resourceType.equalsIgnoreCase("FOLDER") || resourceType.equalsIgnoreCase("data_source")) {
					//------------------------------------------
					// Check in Folder
					//------------------------------------------
					
				    // Validate the VCS_CHECKIN_OPTIONS against the VCS_CHECKIN_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckinRequired(vcsStruct.getVcsCheckinOptions(), vcsStruct.getVcsCheckinOptionsRequired());
				    
					String fullResourcePath = (execFromDir+"/"+resourcePath).replaceAll("//", "/");
/**************************
 *  CLEARCASE CHECKIN					
 **************************/
					//Derived from script:
				    // cleatool ci ./${resourcePath} -c "${Message}" ${CLC_OPTIONS} ${CLC_AUTH}
				    // cleatool ci ${fullResourcePath} -c "${Message}" ${CLC_AUTH} ${VCS_OPTIONS} ${VCS_CHECKIN_OPTIONS}
					arguments=" ci "+fullResourcePath+" -c \""+message+"\" "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckinOptions();

					commandDesc = "    Commit folder changes to the Clearcase Repository...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					
				} else {
					//------------------------------------------
					// Check in File
					//------------------------------------------
					
				    // Validate the VCS_CHECKIN_OPTIONS against the VCS_CHECKIN_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckinRequired(vcsStruct.getVcsCheckinOptions(), vcsStruct.getVcsCheckinOptionsRequired());
				    
					String fullResourcePath = (execFromDir+"/"+resourcePath+"_"+resourceType+".cmf").replaceAll("//", "/");
/**************************
 *  CLEARCASE CHECKIN					
 **************************/
					//Derived from script:
				    // cleartool ci ./${resourcePath}_${resourceType}.cmf -m "${Message}" ${CLC_OPTIONS} ${CLC_AUTH}
				    // cleatool ci ${fullResourcePath} -c "${Message}"  ${CLC_AUTH} ${VCS_OPTIONS} ${VCS_CHECKIN_OPTIONS}
					arguments=" ci "+fullResourcePath+" -c \""+message+"\" "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckinOptions();

					commandDesc = "    Commit file changes to the Clearcase Repository...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
				}
			}
			
			/********************************************
			 * vcs_checkin_checkout__vcs_checkin:
			 *      NEW_VCS_TYPE=New VCS Type
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("NEW_VCS_TYPE")) {
				// Implement VCS Checkin here
			}
			
		} catch (CompositeException e) {
		    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
			logger.error("Failed executing "+prefix+".",e);
			boolean result = CommonUtils.removeFile(commentFilePath);
			throw new CompositeException(e.getMessage(), e);
		}
	}
	
	
	// ***********************************************************************************************
	// Execute a VCS Generalized Scripts  ** vcs_checkin_checkout_cvs vcs_checkout **
	// ***********************************************************************************************
	private void vcs_checkin_checkout__vcs_checkout(String resourcePath, String resourceType, String vcsLabel, String revision,  VcsStruct vcsStruct) throws CompositeException {
	/*
		# $1 -> Resource path 		 (e.g. /shared/MyFolder/My__View), using file system (encoded) names
		# $2 -> Resource type 		 (e.g. FOLDER, table etc.)
		# $3 -> Rollback revision 	 (e.g. HEAD, 827)
		# $4 -> VCS Workspace Folder (e.g. /tmp/workspaces/workspace_CIS)
	*/
		String prefix = "vcs_checkin_checkout__vcs_checkout_"+vcsStruct.getVcsType();
	    List<String> argList = new ArrayList<String>();
	    List<String> envList = new ArrayList<String>();
	    boolean initArgList = true;
	    boolean preserveQuotes = false;
	    String command = vcsStruct.getVcsExecCommand();
	    String arguments = null;
	    String execFromDir = null;
	    String commandDesc = null;

		try {
			/********************************************
			 * [CVS] vcs_checkin_checkout__vcs_checkout:
			 *      CVS=Concurrent Versions System
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("CVS")) {

				// Execute from the Workspace Project Directory
				//   e.g: vcsWorkspaceProject:  D:/PDTool/cvs_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
			
				//------------------------------------------
				// Check out Label
				//------------------------------------------
				if (vcsLabel != null) {
					throw new ApplicationException("The option for using vcs labels has not been implemented for CVS.  Use resourcePath and resourceType instead.");
				} 
				else 
				{
					// 2012-10-29 mtinius: differentiate between folder and data_source
					if (resourceType.equalsIgnoreCase("FOLDER") || resourceType.equalsIgnoreCase("data_source")) {
						//------------------------------------------
						// Check out Folder
						//------------------------------------------

					    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
					    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());

						String fullResourcePath = (execFromDir+"/"+resourcePath).replaceAll("//", "/");
						//Derived from script:
					    // cvs update -j${Revision} ${fullResourcePath} ${VCS_CHECKOUT_OPTIONS}
						arguments=" update -j"+revision+" "+fullResourcePath+" "+vcsStruct.getVcsCheckoutOptions();
					    // cvs update -j${Revision} ./${resourcePath} ${VCS_CHECKOUT_OPTIONS}
						// (previous code): arguments=" update -j"+revision+" ./"+resourcePath+" "+vcsStruct.getVcsCheckoutOptions();
						
						commandDesc = "    Update folder revisions to the CVS Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
						
					} else {
						//------------------------------------------
						// Check out File
						//------------------------------------------

					    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
					    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());

						String fullResourcePath = (execFromDir+"/"+resourcePath+"_"+resourceType+".cmf").replaceAll("//", "/");
						//Derived from script:
					    // cvs update -j${Revision} ${fullResourcePath} ${VCS_CHECKOUT_OPTIONS}
						arguments=" update -j"+revision+" "+fullResourcePath+" "+vcsStruct.getVcsCheckoutOptions();
					    // cvs update -j${Revision} ./${resourcePath}_${resourceType}.cmf ${VCS_CHECKOUT_OPTIONS}
						// (previous code): arguments=" update -j"+revision+" ./"+resourcePath+"_"+resourceType+".cmf"+" "+vcsStruct.getVcsCheckoutOptions();

						commandDesc = "    Update file revsions to the CVS Repository...";	
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					}
				}
			}
			
			/********************************************
			 * [P4] vcs_checkin_checkout__vcs_checkout:
			 *      P4=Perforce
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("P4")) {
				//cd "${Workspace}"
				//   e.g: vcsWorkspaceProject:  D:/PDTool/p4_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
	
				//------------------------------------------
				// Check out Label
				//------------------------------------------
				if (vcsLabel != null) {
					
				    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());

					/*
					 * For perforce labels, the file system must be cleaned out when performing a p4 sync @label.
					 * The reason has to do with the fact that a label may contain siblings like /shared and /services.  
					 * Additionally, a label will only contain the resources that the user wishes to deploy even if there are
					 * additional resources checked into the Perforce depot.   Any residual directories even if empty will 
					 * throw off diff_merger and cause an issue with the import of resources.  It will indescriminantly mark
					 * resources for deletion that you don't want marked.
					 * 
					 * Therefore, it is necessary to perform a remove and create directory prior to performing the p4 sync @label.
					 * 
					 * Also, when dealing with labels, you must make sure that you do a checking at Composite root "localhost:9400 (/)".
					 */
			        // Explicitly remove the workspace project directory
					//   e.g: vcsWorkspaceProject:  D:/PDTool/p4_workspace/cis_objects
					removeDirectory(prefix, vcsStruct.getVcsWorkspaceProject());
		        
					// Create the VCS Workspace Project directory
					//   e.g: vcsWorkspaceProject:  D:/PDTool/p4_workspace/cis_objects
					createDirectory(prefix, vcsStruct.getVcsWorkspaceProject());

					//Derived from script:
					// p4 sync @label ${VCS_CHECKOUT_OPTIONS}
					arguments=" sync -f @"+vcsLabel+" "+vcsStruct.getVcsCheckoutOptions();
					
					commandDesc = "    Sync Label @"+vcsLabel+" with the Perforce Repository...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
				} 
				else 
				{
					//------------------------------------------
					// Check out Folder
					//------------------------------------------
					// 2012-10-29 mtinius: differentiate between folder and data_source
					if (resourceType.equalsIgnoreCase("FOLDER") || resourceType.equalsIgnoreCase("data_source")) {
						//---------------
						// FOLDER
						//---------------					
						//cd ./${resourcePath}
						// Execute from directory.  Make sure there are no double slashes
						execFromDir=(execFromDir+"/"+resourcePath).replaceAll("//", "/");
						
						if (revision.equalsIgnoreCase("HEAD")) {
							//---------------
							// FOLDER_HEAD
							//---------------

							// Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
						    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());

							//Derived from script:
							// p4 sync ${VCS_CHECKOUT_OPTIONS}
							arguments=" sync"+" "+vcsStruct.getVcsCheckoutOptions();
							
							commandDesc = "    Sync folder HEAD with the Perforce Repository...";
							CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
							CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
							CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
							
						    // Parse the command arguments into a list
						    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
						    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
						    
						    // Execute the command line
						    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
							
						} else {
							//---------------
							// FOLDER_NO_HEAD
							//---------------

							// Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
						    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());

							//Derived from script:
							// p4 sync @${Revision} ${VCS_CHECKOUT_OPTIONS}
							arguments=" sync @"+revision+" "+vcsStruct.getVcsCheckoutOptions();
							
							commandDesc = "    Sync folder NO_HEAD with the Perforce Repository...";
							CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
							CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
							CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
							
						    // Parse the command arguments into a list
						    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
						    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
						    
						    // Execute the command line
						    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
						}
					//------------------------------------------
					// Check out File
					//------------------------------------------
					} else {
						//---------------
						// FILE
						//---------------
						if (revision.equalsIgnoreCase("HEAD")) {
							//---------------
							// FILE_HEAD
							//---------------

							// Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
						    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());
							
							String fullResourcePath = (execFromDir+"/"+resourcePath+"_"+resourceType+".cmf").replaceAll("//", "/");
							//Derived from script:
							// p4 sync "${fullResourcePath}" ${VCS_CHECKOUT_OPTIONS}
							//   e.g: vcsWorkspaceProject:  D:/PDTool/p4_workspace/cis_objects
							arguments=" sync "+"\""+fullResourcePath+"\""+" "+vcsStruct.getVcsCheckoutOptions();
							// p4 sync "${Workspace}${resourcePath}_${resourceType}.cmf" ${VCS_CHECKOUT_OPTIONS}
							// (previous code): arguments=" sync "+"\""+vcsStruct.getVcsWorkspaceProject()+resourcePath+"_"+resourceType+".cmf"+"\""+" "+vcsStruct.getVcsCheckoutOptions();
							
							commandDesc = "    Sync file HEAD with the Perforce Repository...";
							CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
							CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
							CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
							
						    // Parse the command arguments into a list
						    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
						    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
						    
						    // Execute the command line
						    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
							
						} else {
							//---------------
							// FILE_NO_HEAD
							//---------------

						    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
						    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());
							
							String fullResourcePath = (execFromDir+"/"+resourcePath+"_"+resourceType+".cmf").replaceAll("//", "/");
						    //Derived from script:
							// p4 sync "${fullResourcePath}@${Revision}" ${VCS_CHECKOUT_OPTIONS}
							//   e.g: vcsWorkspaceProject:  D:/PDTool/p4_workspace/cis_objects
							arguments=" sync "+"\""+fullResourcePath+"@"+revision+"\""+" "+vcsStruct.getVcsCheckoutOptions();
							// p4 sync "${Workspace}${resourcePath}_${resourceType}.cmf@${Revision}" ${VCS_CHECKOUT_OPTIONS}
							// (previous code): arguments=" sync "+"\""+vcsStruct.getVcsWorkspaceProject()+resourcePath+"_"+resourceType+".cmf"+"@"+revision+"\""+" "+vcsStruct.getVcsCheckoutOptions();
							
							commandDesc = "    Sync file NO_HEAD with the Perforce Repository...";
							CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
							CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
							CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
							
						    // Parse the command arguments into a list
						    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
						    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
						    
						    // Execute the command line
						    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
						}
					}
				}
			}
			
			/********************************************
			 * [SVN] vcs_checkin_checkout__vcs_checkout:
			 *      SVN=Subversion
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("SVN")) {
				//cd "${Workspace}"
				//	 e.g: vcsWorkspaceProject:  D:/PDTool/svn_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
				
				if (vcsLabel != null) {
					throw new ApplicationException("The option for using vcs labels has not been implemented for Subversion.  Use resourcePath and resourceType instead.");
				} 
				else 
				{
					// 2012-10-29 mtinius: differentiate between folder and data_source
					if (resourceType.equalsIgnoreCase("FOLDER") || resourceType.equalsIgnoreCase("data_source")) {
						//------------------------------------------
						// Check out Folder
						//------------------------------------------

					    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
					    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());

						String fullResourcePath = (execFromDir+"/"+resourcePath).replaceAll("//", "/");
						//Derived from script:
					    // svn update ${fullResourcePath} -r ${Revision} ${SVN_AUTH} ${VCS_OPTIONS} ${VCS_CHECKOUT_OPTIONS}
						arguments=" update "+fullResourcePath+" -r "+revision+" "+vcsStruct.getVcsOptions()+" "+vcsStruct.getVcsCheckoutOptions();
					    // svn update ./${resourcePath} -r ${Revision} ${SVN_OPTIONS} ${SVN_AUTH}
						// (previous code): arguments=" update ./"+resourcePath+" -r "+revision+" "+vcsStruct.getVcsOptions();

						commandDesc = "    Update folder revisions to the Subversion Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
						
					} else {
						//------------------------------------------
						// Check out File
						//------------------------------------------
						
					    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
					    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());
						
						String fullResourcePath = (execFromDir+"/"+resourcePath+"_"+resourceType+".cmf").replaceAll("//", "/");
						//Derived from script:
					    // svn update ${fullResourcePath} -r ${Revision} ${SVN_AUTH} ${VCS_OPTIONS} ${VCS_CHECKOUT_OPTIONS}
						arguments=" update "+fullResourcePath+" -r "+revision+" "+vcsStruct.getVcsOptions()+" "+vcsStruct.getVcsCheckoutOptions();
					    // svn update ./${resourcePath}_${resourceType}.cmf -r ${Revision} ${SVN_OPTIONS} ${SVN_AUTH}
						// (previous code): arguments=" update ./"+resourcePath+"_"+resourceType+".cmf -r "+revision+" "+vcsStruct.getVcsOptions();

						commandDesc = "    Update file revisions to the Subversion Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					}				
				}
			}
			
			/********************************************
			 * [TFS] vcs_checkin_checkout__vcs_checkout:
			 *      TFS=Team Foundation Server
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("TFS2010") || 
				vcsStruct.getVcsType().equalsIgnoreCase("TFS2012") ||
				vcsStruct.getVcsType().equalsIgnoreCase("TFS2013") ||
				vcsStruct.getVcsType().equalsIgnoreCase("TFS2005")) 
			{
				//cd "${Workspace}"
				//	 e.g: vcsWorkspaceProject:  D:/PDTool/tfs_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
				
				if (revision.equalsIgnoreCase("HEAD"))
					revision = "T";
				
				if (vcsLabel != null) {
					throw new ApplicationException("The option for using vcs labels has not been implemented for TFS.  Use resourcePath and resourceType instead.");
				} 
				else 
				{
					// 2012-10-29 mtinius: differentiate between folder and data_source
					if (resourceType.equalsIgnoreCase("FOLDER") || resourceType.equalsIgnoreCase("data_source")) {
						//------------------------------------------
						// Check out Folder
						//------------------------------------------
						
					    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
					    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());

						String fullResourcePath = (execFromDir+"/"+resourcePath).replaceAll("//", "/");
						//Derived from script:
					    // tf.cmd get ${fullResourcePath} -version:${Revision} -recursive -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS} ${VCS_CHECKOUT_OPTIONS}
						arguments=" get "+fullResourcePath+" -version:"+revision+" -recursive -noprompt "+vcsStruct.getVcsOptions()+" "+vcsStruct.getVcsCheckoutOptions();					
					    // tf.cmd get ./${resourcePath} -version:${Revision} -recursive -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS}
						//(previous code): arguments=" get ./"+resourcePath+" -version:"+revision+" -recursive -noprompt "+vcsStruct.getVcsOptions();
						
						commandDesc = "    Update folder revisions to the Team Foundation Server Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
						
					} else {
						//------------------------------------------
						// Check out File
						//------------------------------------------
						
					    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
					    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());
						
						String fullResourcePath = (execFromDir+"/"+resourcePath+"_"+resourceType+".cmf").replaceAll("//", "/");
						//Derived from script:
					    // tf.cmd get ${fullResourcePath} -version:${Revision} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS} ${VCS_CHECKOUT_OPTIONS}
						arguments=" get "+fullResourcePath+" -version:"+revision+" -noprompt "+vcsStruct.getVcsOptions()+" "+vcsStruct.getVcsCheckoutOptions();
					    // tf.cmd get ./${resourcePath}_${resourceType}.cmf -version:${Revision} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS}
						//(previous code): arguments=" get ./"+resourcePath+"_"+resourceType+".cmf -version:"+revision+" -noprompt "+vcsStruct.getVcsOptions();

						commandDesc = "    Update file revisions to the Team Foundation Server Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    logger.debug("TFS Argument List: " + argList);
					    logger.debug("TFS Environment List: " + envList);
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					}				
				}
			}
			
			/********************************************
			 * [GIT] vcs_checkin_checkout__vcs_checkout:
			 *      GIT=Git
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("GIT")) {
				//cd "${Workspace}"
				//	 e.g: vcsWorkspaceProject:  D:/PDTool/GITuw/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
				
				if (vcsLabel != null) {
					throw new ApplicationException("The option for using vcs labels has not been implemented for Git.  Use resourcePath and resourceType instead.");
				} 
				else 
				{
					// do a hard reset to the remote Git repository's HEAD (this will undo any checking out of specific commits and
					// revert to the HEAD of origin.) We're assuming a remote name of "origin" here. This should properly run "git remote" 
					// to get the list of remote repositories and their names and match it to the URL used to clone the repository (user
					// can use -o <name> when cloning to rename the remote name to something other than "origin".)
					//
				    // git reset --hard origin/HEAD
					arguments=" reset --hard origin/HEAD";

					commandDesc = "    Perform hard reset to Git Repository HEAD...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					
					// 2012-10-29 mtinius: differentiate between folder and data_source
					if (resourceType.equalsIgnoreCase("FOLDER") || resourceType.equalsIgnoreCase("data_source")) {
						//------------------------------------------
						// Check out Folder
						//------------------------------------------

					    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
					    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());

						String fullResourcePath = (execFromDir+"/"+resourcePath).replaceAll("//", "/");

					    //Derived from script:
					    // git checkout ${VCS_OPTIONS} ${VCS_CHECKOUT_OPTIONS} ${Revision} ${fullResourcePath}
						arguments=" checkout " + vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckoutOptions() + " " + revision + " " + fullResourcePath;

						commandDesc = "    Update folder revisions to the Git Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
						
					} else {
						//------------------------------------------
						// Check out File
						//------------------------------------------
						
					    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
					    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());
						
						String fullResourcePath = (execFromDir+"/"+resourcePath+"_"+resourceType+".cmf").replaceAll("//", "/");

						//Derived from script:
					    // git checkout ${VCS_OPTIONS} ${VCS_CHECKOUT_OPTIONS} ${Revision} ${fullResourcePath}
						arguments=" checkout " + vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckoutOptions() + " " + revision + " " + fullResourcePath;

						commandDesc = "    Update file revisions to the Git Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					}				
				}
			}

			/********************************************
			 * [CLC] vcs_checkin_checkout__vcs_checkout:
			 *      CLC=Clearcase
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("CLC")) {
				//cd "${Workspace}"
				//	 e.g: vcsWorkspaceProject:  D:/PDTool/svn_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
				
				if (vcsLabel != null) {
					throw new ApplicationException("The option for using vcs labels has not been implemented for Clearcase.  Use resourcePath and resourceType instead.");
				} 
				else 
				{
					// 2012-10-29 mtinius: differentiate between folder and data_source
					if (resourceType.equalsIgnoreCase("FOLDER") || resourceType.equalsIgnoreCase("data_source")) {
						//------------------------------------------
						// Check out Folder
						//------------------------------------------

					    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
					    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());

						String fullResourcePath = (execFromDir+"/"+resourcePath).replaceAll("//", "/");
/**************************
 *  CLEARCASE CHECKOUT					
 **************************/
						//Derived from script:
					    // cleartool co ./${resourcePath} -ver ${Revision} ${CLC_OPTIONS} ${CLC_AUTH}
					    // cleartool co ${fullResourcePath} -ver ${Revision} ${CLC_AUTH} ${VCS_OPTIONS} ${VCS_CHECKOUT_OPTIONS}
						arguments=" co "+fullResourcePath+" -ver "+revision+" "+vcsStruct.getVcsOptions()+" "+vcsStruct.getVcsCheckoutOptions();

						commandDesc = "    Check out folder revisions from the Clearcase Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
						
					} else {
						//------------------------------------------
						// Check out File
						//------------------------------------------
						
					    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
					    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());
						
						String fullResourcePath = (execFromDir+"/"+resourcePath+"_"+resourceType+".cmf").replaceAll("//", "/");
/**************************
 *  CLEARCASE CHECKOUT					
 **************************/
						//Derived from script:
					    // cleartool co ./${resourcePath}_${resourceType}.cmf -ver ${Revision} ${CLC_OPTIONS} ${CLC_AUTH}
					    // cleartool co ${fullResourcePath} -ver ${Revision} ${CLC_AUTH} ${VCS_OPTIONS} ${VCS_CHECKOUT_OPTIONS}
						arguments=" co "+fullResourcePath+" -ver "+revision+" "+vcsStruct.getVcsOptions()+" "+vcsStruct.getVcsCheckoutOptions();

						commandDesc = "    Check out file revisions from the Clearcase Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					}				
				}
			}
			
			/********************************************
			 * vcs_checkin_checkout__vcs_checkout:
			 *      NEW_VCS_TYPE=New VCS Type
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("NEW_VCS_TYPE")) {
				// Implement VCS Checkout here
				if (vcsLabel != null) {
					throw new ApplicationException("The option for using vcs labels has not been implemented for NEW_VCS_TYPE.  Use resourcePath and resourceType instead.");
				} 
				else 
				{
					// 2012-10-29 mtinius: differentiate between folder and data_source
					if (resourceType.equalsIgnoreCase("FOLDER") || resourceType.equalsIgnoreCase("data_source")) {
						// Code goes here
						
					}
					else {
						
					}
				}
			}
			
		} catch (CompositeException e) {
		    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
			logger.error("Failed executing "+prefix+".",e);
			throw new CompositeException(e.getMessage(), e);
		}
	}

	
	// ***********************************************************************************************
	// Execute a VCS Validation Scripts  ** vcs_checkout_validate **
	// ***********************************************************************************************
	// This method is used to validate that the "folder.cmf" files exist for each folder and parent folder in the resourcePath.
	// If a "folder.cmf" file does not exit, unusual and unexpected results happen with a VCS Checkout.  In some cases,
	//   the resources won't import and in other cases, the resources may get deleted.
	private void vcs_checkout_validate(String resourcePath, String resourceType, VcsStruct vcsStruct) throws CompositeException 
	{
		String prefix = "vcs_checkout_validate";
	    String execFromDir = null;
	    String directory = null;
	    String directoryCMF = null;
	    String invalidDirectoryList = null;
		
		try {
			/********************************************
			 * [CVS] vcs_checkin_checkout__vcs_checkout:
			 *      CVS=Concurrent Versions System
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("CVS")) {

				// Execute from the Workspace Project Directory
				//   e.g: vcsWorkspaceProject:  D:/PDTool/cvs_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
			}
						
			/********************************************
			 * [P4] vcs_checkin_checkout__vcs_checkout:
			 *      P4=Perforce
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("P4")) {
				//cd "${Workspace}"
				//   e.g: vcsWorkspaceProject:  D:/PDTool/p4_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
			}
			/********************************************
			 * [SVN] vcs_checkin_checkout__vcs_checkout:
			 *      SVN=Subversion
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("SVN")) {
				//cd "${Workspace}"
				//	 e.g: vcsWorkspaceProject:  D:/PDTool/svn_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
			}
			/********************************************
			 * [TFS] vcs_checkin_checkout__vcs_checkout:
			 *      TFS=Team Foundation Server
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("TFS2010") || 
				vcsStruct.getVcsType().equalsIgnoreCase("TFS2012") ||
				vcsStruct.getVcsType().equalsIgnoreCase("TFS2013") ||
				vcsStruct.getVcsType().equalsIgnoreCase("TFS2005")) 
			{
				//cd "${Workspace}"
				//	 e.g: vcsWorkspaceProject:  D:/PDTool/tfs_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
			}
			
			/********************************************
			 * [GIT] vcs_checkin_checkout__vcs_checkout:
			 *      GIT=Git
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("GIT")) {
				//cd "${Workspace}"
				//	 e.g: vcsWorkspaceProject:  D:/PDTool/GITuw/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
			}
			/********************************************
			 * [CLC] vcs_checkin_checkout__vcs_checkout:
			 *      CLC=Clearcase
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("CLC")) {
				//cd "${Workspace}"
				//	 e.g: vcsWorkspaceProject:  D:/PDTool/svn_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
			}

			if (resourceType.equalsIgnoreCase("FOLDER") || resourceType.equalsIgnoreCase("data_source")) {
				//------------------------------------------
				// Validate Folder
				//------------------------------------------

				String fullResourcePath = (execFromDir+"/"+resourcePath).replaceAll("//", "/");
				CommonUtils.writeOutput("    VCS Checkout Validate [FOLDER] fullResourcePath="+fullResourcePath,prefix,"-debug3",logger,debug1,debug2,debug3);
				directory = fullResourcePath;

			} else {
				//------------------------------------------
				// Validate File
				//------------------------------------------
				
				String fullResourcePath = (execFromDir+"/"+resourcePath+"_"+resourceType+".cmf").replaceAll("//", "/");
				CommonUtils.writeOutput("    VCS Checkout Validate [FILE] fullResourcePath="+fullResourcePath,prefix,"-debug3",logger,debug1,debug2,debug3);
				directory = CommonUtils.getDirectory(fullResourcePath);
			}				

			// Validate the workspace structure and insure that the "folder.cmf" file exists for each parent folder of the passed in resourcePath.  
			//   This is required for a validate VCS operation to take place.
			//   Throw an exception if any parent "folder.cmf" file is missing.
			while (directory != null && directory.length() > 0 && !directory.equals(execFromDir)) {
				directoryCMF = CommonUtils.getPathName(directory) + ".cmf";
				String directoryFullPath = directory+"/"+directoryCMF;
				boolean dirExists = CommonUtils.fileExists(directory);
				if (dirExists) {
					boolean fileExists = CommonUtils.fileExists(directoryFullPath);
					if (!fileExists) {
						if (invalidDirectoryList == null)
							invalidDirectoryList = "Invalid Workspace Folder List:\r\n";
						else
							invalidDirectoryList = invalidDirectoryList + "\r\n";
						
						invalidDirectoryList = invalidDirectoryList + "   Workspace folder ["+directoryCMF+"] does not exist.  Path="+directoryFullPath;					
					}
				}
				// Get the next parent directory to validate
				directory = CommonUtils.getDirectory(directory);
			}

			if (invalidDirectoryList != null)
				throw new CompositeException(invalidDirectoryList);
			
		} catch (CompositeException e) {
		    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
			logger.error("Failed executing "+prefix+".",e);
			throw new CompositeException(e.getMessage(), e);
		}
	}
	
	
	/******************************************************************************************
	 *  PRIVATE IMPLEMENTATION
	 *  
	 *  VCS ADD [vcs_add]
	 *  
	 *  Specific implementation for a particular Version Control System (VCS)
	 * 
	 *******************************************************************************************/

	// ***********************************************************************************************
	// Execute a VCS Generalized Scripts  ** vcs_add__vcs_checkout **
	// ***********************************************************************************************
	private void vcs_add__vcs_checkout(String resourcePath, String resourceType, String vcsLabel, String revision,  VcsStruct vcsStruct) throws CompositeException {
	/*
		# $1 -> Resource path 		 (e.g. /shared/MyFolder/My__View), using file system (encoded) names
		# $2 -> Resource type 		 (e.g. FOLDER, table etc.)
		# $3 -> Rollback revision 	 (e.g. HEAD, 827)
		# $4 -> VCS Workspace Folder (e.g. /tmp/workspaces/workspace_CIS)
	*/
		String prefix = "vcs_add__vcs_checkout_"+vcsStruct.getVcsType();
	    List<String> argList = new ArrayList<String>();
	    List<String> envList = new ArrayList<String>();
	    boolean initArgList = true;
	    boolean preserveQuotes = false;
	    String command = vcsStruct.getVcsExecCommand();
	    String arguments = null;
	    String execFromDir = null;
	    String commandDesc = null;

		try {
			/********************************************
			 * [CVS] vcs_add__vcs_checkout:
			 *      CVS=Concurrent Versions System
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("CVS")) {

				// Execute from the Workspace Project Directory
				//   e.g: vcsWorkspaceProject:  D:/PDTool/cvs_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
			
				//------------------------------------------
				// Check out Label
				//------------------------------------------
				if (vcsLabel != null) {
					throw new ApplicationException("The option for using vcs labels has not been implemented for CVS.  Use resourcePath and resourceType instead.");
				} 
				else 
				{
					//------------------------------------------
					// Check out Folder
					//------------------------------------------

				    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());

					String fullResourcePath = (execFromDir+"/"+resourcePath).replaceAll("//", "/");
					//Derived from script:
				    // cvs update -jHEAD ${fullResourcePath} ${VCS_CHECKOUT_OPTIONS}
//					arguments=" update -jHEAD "+fullResourcePath+" "+vcsStruct.getVcsCheckoutOptions();
//					arguments=" update -jHEAD . "+vcsStruct.getVcsCheckoutOptions();
//					commandDesc = "    Update folder revisions to the CVS Repository...";
				
					// Check out the repository to the local workspace
					//   cvs co ${VCS_PROJECT_ROOT} ${VCS_WORKSPACE_INIT_GET_OPTIONS}
					arguments=" co "+vcsStruct.getVcsProjectRoot() + " " + vcsStruct.getVcsWorkspaceInitGetOptions();
					commandDesc = "    Checking out the repository to the local workspace...";

					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
				}
			}
			
			/********************************************
			 * [P4] vcs_add__vcs_checkout:
			 *      P4=Perforce
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("P4")) {
				//   e.g: vcsWorkspaceProject:  D:/PDTool/p4_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
	
				if (vcsLabel != null) {
					throw new ApplicationException("The option for using vcs labels has not been implemented for CVS.  Use resourcePath and resourceType instead.");
				} 
				else 
				{
					//------------------------------------------
					// Check out Folder
					//------------------------------------------
					// Execute from directory.  Make sure there are no double slashes
					execFromDir=(execFromDir+"/"+resourcePath).replaceAll("//", "/");

					// Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());
			
					// Synchronize (Check out) the repository to the local workspace
					// p4 sync -f ${VCS_WORKSPACE_INIT_GET_OPTIONS}
					arguments=" sync -f " + vcsStruct.getVcsWorkspaceInitGetOptions();

					commandDesc = "    Sync folder HEAD with the Perforce Repository...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
				}
			}
			
			/********************************************
			 * [SVN] vcs_add__vcs_checkout:
			 *      SVN=Subversion
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("SVN")) {
				//	 e.g: vcsWorkspaceProject:  D:/PDTool/svn_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
				
				if (vcsLabel != null) {
					throw new ApplicationException("The option for using vcs labels has not been implemented for Subversion.  Use resourcePath and resourceType instead.");
				} 
				else 
				{
					//------------------------------------------
					// Check out Folder
					//------------------------------------------

				    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());

					String fullResourcePath = (execFromDir+"/"+resourcePath).replaceAll("//", "/");
					//Derived from script:
				    // svn update ${fullResourcePath} -r HEAD ${SVN_AUTH} ${VCS_OPTIONS} ${VCS_CHECKOUT_OPTIONS}
					arguments=" update "+fullResourcePath+" -r HEAD "+vcsStruct.getVcsOptions()+" "+vcsStruct.getVcsCheckoutOptions();

					commandDesc = "    Update folder revisions to the Subversion Repository...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());						
				}
			}
			
			/********************************************
			 * [TFS] vcs_add__vcs_checkout:
			 *      TFS=Team Foundation Server
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("TFS2010") || 
				vcsStruct.getVcsType().equalsIgnoreCase("TFS2012") ||
				vcsStruct.getVcsType().equalsIgnoreCase("TFS2013") ||
				vcsStruct.getVcsType().equalsIgnoreCase("TFS2005")) 
			{
				//	 e.g: vcsWorkspaceProject:  D:/PDTool/tfs_workspace/cis_objects
				execFromDir=vcsStruct.getVcsWorkspaceProject();
				
				if (vcsLabel != null) {
					throw new ApplicationException("The option for using vcs labels has not been implemented for TFS.  Use resourcePath and resourceType instead.");
				} 
				else 
				{
					//------------------------------------------
					// Check out Folder
					//------------------------------------------
					
				    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());

					// Check out the repository to the local workspace	    
				    // Retrieves a read-only copy of a file from the Team Foundation Server to the workspace and creates folders on disk to contain it.
				    // tf.cmd get -all -recursive -force ${TFS_SERVER_URL} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS} ${VCS_WORKSPACE_INIT_GET_OPTIONS}
					arguments=" get -all -recursive -force " + vcsStruct.getTfsServerUrl() + " -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsWorkspaceInitGetOptions();
					commandDesc = "    Checking out the repository to the local workspace...";

					commandDesc = "    Update folder revisions to the Team Foundation Server Repository...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());						
				}
			}
			
			/********************************************
			 * [GIT] vcs_add__vcs_checkout:
			 *      GIT=Git
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("GIT")) {
				//	 e.g: vcsWorkspace:  D:/PDTool/GITuw
				execFromDir=vcsStruct.getVcsWorkspace(); // execute from the workspace root ($PDTOOL_HOME/GITuw not the project sub-folder.)
				
				if (vcsLabel != null) {
					throw new ApplicationException("The option for using vcs labels has not been implemented for Git.  Use resourcePath and resourceType instead.");
				} else {
					//------------------------------------------
					// Check out Folder
					//------------------------------------------

				    // Validate the VCS_CHECKOUT_OPTIONS against the VCS_CHECKOUT_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckoutRequired(vcsStruct.getVcsCheckoutOptions(), vcsStruct.getVcsCheckoutOptionsRequired());

					String fullResourcePath = (execFromDir+"/"+resourcePath).replaceAll("//", "/");
					//Derived from script:
				    // git checkout ${VCS_OPTIONS} ${VCS_CHECKOUT_OPTIONS} master ${fullResourcePath}
					arguments=" checkout " + vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsCheckoutOptions() + " master " + fullResourcePath;

					commandDesc = "    Update folder revisions to the Git Repository...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
				}
			}
			
			/********************************************
			 * vcs_add__vcs_checkout:
			 *      NEW_VCS_TYPE=New VCS Type
			 ********************************************/
			if (vcsStruct.getVcsType().equalsIgnoreCase("NEW_VCS_TYPE")) {
				// Implement VCS Checkout here
				if (vcsLabel != null) {
					throw new ApplicationException("The option for using vcs labels has not been implemented for NEW_VCS_TYPE.  Use resourcePath and resourceType instead.");
				} 
				else 
				{
					// Code goes here
				}
			}
			
		} catch (CompositeException e) {
		    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
			logger.error("Failed executing "+prefix+".",e);
			throw new CompositeException(e.getMessage(), e);
		}
	}
	
		// ***********************************************************************************************
		// Execute a VCS Generalized Scripts  ** vcs_add vcs_checkin **
		// ***********************************************************************************************
		private void vcs_add__vcs_checkin(String resourcePath, String resourceType, String baseFolderPath, VcsStruct vcsStruct) throws CompositeException {
		/*
			# $1 -> Resource path 		 (e.g. /shared/MyFolder/My__View), using file system (encoded) names
			# $2 -> Resource type 		 (e.g. FOLDER, table etc.)
			# $3 -> Checkin message 	 (e.g. Adding MyFolder)
			# $4 -> VCS Workspace Folder (e.g. /tmp/workspaces/workspace_CIS)
		*/
			String prefix = "vcs_add__vcs_checkin_"+vcsStruct.getVcsType();
		    List<String> argList = new ArrayList<String>();
		    List<String> envList = new ArrayList<String>();
		    boolean initArgList = true;
		    boolean preserveQuotes = false;
		    String command = vcsStruct.getVcsExecCommand();
		    String arguments = null;
		    String execFromDir = null;
		    String commandDesc = null;
		    
			try {
				/********************************************
				 * [CVS] vcs_add__vcs_checkin: 
				 *     CVS=Concurrent Versions System
				 ********************************************/
				if (vcsStruct.getVcsType().equalsIgnoreCase("CVS")) {
					//cd "${Workspace}"
					//	 e.g: vcsWorkspaceProject:  D:/PDTool/cvs_workspace/cis_objects
					execFromDir = vcsStruct.getVcsWorkspaceProject();
					
					//------------------------------------------
					// Add Files or Folders
					//------------------------------------------
								    
				    File dir = new File(baseFolderPath);
				    String excludeFileList = "CVS/Entries,CVS/Entries.Extra,CVS/Entries.Extra.old,CVS/Entries.Old,CVS/Repository,CVS/root";
				    boolean includeParentDir = false;
				    boolean includeFiles = true;
				    boolean includeDirs = false;
				    boolean recursive = true;
				    // Get the /resources/vcs_initial/baseFolders + additional processed folders by using vcsTemp as a checklist against the workspace
				    File[] files = CommonUtils.getFilesParent(dir, excludeFileList, includeParentDir, includeFiles, includeDirs, recursive);
				    
				    // Iterate over the checklist of files and add them into the VCS
				    for (int i=0; i < files.length; i++) 
					{
						String remainderPath = files[i].toString();			
						remainderPath = remainderPath.replaceAll(Matcher.quoteReplacement("\\"), "/");
						remainderPath = remainderPath.replaceAll(baseFolderPath, "");

						String fullResourcePath = (execFromDir+"/"+remainderPath).replaceAll("//", "/");

						//Derived from script:
						// cvs add ${fullResourcePath} ${VCS_BASE_FOLDER_INIT_ADD}
						arguments=" add "+fullResourcePath + " " + vcsStruct.getVcsBaseFolderInitAddOptions();
						
						commandDesc = "    Add folder changes to the CVS Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					}
				}
				
				/********************************************
				 * [P4] vcs_add__vcs_checkin:
				 *      P4=Perforce
				 ********************************************/
				if (vcsStruct.getVcsType().equalsIgnoreCase("P4")) {
					//cd "${Workspace}"
					//   e.g: vcsWorkspaceProject:  D:/PDTool/p4_workspace/cis_objects
					execFromDir=vcsStruct.getVcsWorkspaceProject();

					//------------------------------------------
					// Add Files or Folders
					//------------------------------------------

				    File dir = new File(baseFolderPath);
				    String excludeFileList = "";
				    boolean includeParentDir = false;
				    boolean includeFiles = true;
				    boolean includeDirs = false;
				    boolean recursive = true;
				    // Get the /resources/vcs_initial/baseFolders + additional processed folders by using vcsTemp as a checklist against the workspace
				    File[] files = CommonUtils.getFilesParent(dir, excludeFileList, includeParentDir, includeFiles, includeDirs, recursive);
				    
				    // Iterate over the checklist of files and add them into the VCS
				    for (int i=0; i < files.length; i++) 
					{
						String remainderPath = files[i].toString();			
						remainderPath = remainderPath.replaceAll(Matcher.quoteReplacement("\\"), "/");
						remainderPath = remainderPath.replaceAll(baseFolderPath, "");

						String fullResourcePath = (execFromDir+"/"+remainderPath).replaceAll("//", "/");

						//Derived from script:
						// p4 add ${fullResourcePath} ${VCS_BASE_FOLDER_INIT_ADD}
						arguments=" add "+fullResourcePath+"\"" + " " + vcsStruct.getVcsBaseFolderInitAddOptions();
						preserveQuotes = true;
						
						commandDesc = "    Add the change list to the Perforce Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					}
				}

				
				/********************************************
				 * [SVN] vcs_add__vcs_checkin:
				 *      SVN=Subversion
				 ********************************************/
				if (vcsStruct.getVcsType().equalsIgnoreCase("SVN")) {
					//cd "${Workspace}"
					//   e.g: vcsWorkspaceProject:  D:/PDTool/svn_workspace/cis_objects
					execFromDir=vcsStruct.getVcsWorkspaceProject();
					
					//------------------------------------------
					// Add Files or Folders
					//------------------------------------------
					
				    File dir = new File(baseFolderPath);
				    String excludeFileList = ".svn";
				    boolean includeParentDir = false;
				    boolean includeFiles = true;
				    boolean includeDirs = true;
				    boolean recursive = true;
				    // Get the /resources/vcs_initial/baseFolders + additional processed folders by using vcsTemp as a checklist against the workspace
				    File[] files = CommonUtils.getFilesParent(dir, excludeFileList, includeParentDir, includeFiles, includeDirs, recursive);
				    
				    // Iterate over the checklist of files and add them into the VCS
				    for (int i=0; i < files.length; i++) 
					{
						String remainderPath = files[i].toString();			
						remainderPath = remainderPath.replaceAll(Matcher.quoteReplacement("\\"), "/");
						remainderPath = remainderPath.replaceAll(baseFolderPath, "");

						String fullResourcePath = (execFromDir+"/"+remainderPath).replaceAll("//", "/");
						//Derived from script:
					    // svn add ${fullResourcePath} ${SVN_AUTH} ${VCS_OPTIONS} ${VCS_BASE_FOLDER_INIT_ADD}
						arguments=" add "+fullResourcePath+" "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsBaseFolderInitAddOptions();

						commandDesc = "    Add folder changes to the Subversion Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					}					
				}
				
				/********************************************
				 * [TFS] vcs_add__vcs_checkin:
				 *      TFS=Team Foundation Server
				 ********************************************/
				if (vcsStruct.getVcsType().equalsIgnoreCase("TFS2010") || 
					vcsStruct.getVcsType().equalsIgnoreCase("TFS2012") ||
					vcsStruct.getVcsType().equalsIgnoreCase("TFS2013") ||
					vcsStruct.getVcsType().equalsIgnoreCase("TFS2005")) 
				{
					//cd "${Workspace}"
					//	 e.g: vcsWorkspaceProject:  D:/PDTool/tfs_workspace/cis_objects
					execFromDir=vcsStruct.getVcsWorkspaceProject();
					preserveQuotes = true;
				
					//------------------------------------------
					// Add Files or Folders
					//------------------------------------------
					
				    // Validate the VCS_CHECKIN_OPTIONS against the VCS_CHECKIN_OPTIONS_REQUIRED and throw an exception if a required option is not found
				    validateCheckinRequired(vcsStruct.getVcsCheckinOptions(), vcsStruct.getVcsCheckinOptionsRequired());
				    
				    File dir = new File(baseFolderPath);
				    String excludeFileList = "";
				    boolean includeParentDir = false;
				    boolean includeFiles = true;
				    boolean includeDirs = false;
				    boolean recursive = true;
				    // Get the /resources/vcs_initial/baseFolders + additional processed folders by using vcsTemp as a checklist against the workspace
				    File[] files = CommonUtils.getFilesParent(dir, excludeFileList, includeParentDir, includeFiles, includeDirs, recursive);
				    
				    // Iterate over the checklist of files and add them into the VCS
				    for (int i=0; i < files.length; i++) 
					{
						String remainderPath = files[i].toString();			
						remainderPath = remainderPath.replaceAll(Matcher.quoteReplacement("\\"), "/");
						remainderPath = remainderPath.replaceAll(baseFolderPath, "");

						String fullResourcePath = (execFromDir+"/"+remainderPath).replaceAll("//", "/");
						//Derived from script:				
					    // tf.cmd add ${fullResourcePath} -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS} ${VCS_BASE_FOLDER_INIT_ADD}
						arguments=" add "+fullResourcePath+" -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsBaseFolderInitAddOptions();

						commandDesc = "    Add folder changes to the Team Foundation Server Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
						//logger.debug("TFS Unparsed Arguments: " + arguments);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());	
					}				    	
				    	
				    /*
					//------------------------------------------
					// Add Folders recursively
					//------------------------------------------
				    
				    String fullResourcePath = (execFromDir+"/"+resourcePath).replaceAll("//", "/");
					//Derived from script:				
				    // tf.cmd add ${fullResourcePath} -recursive -noprompt /login:${VCS_USERNAME},${VCS_PASSWORD} ${VCS_OPTIONS} ${VCS_BASE_FOLDER_INIT_ADD}
					arguments=" add "+fullResourcePath+" -recursive -noprompt "+vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsBaseFolderInitAddOptions();

					commandDesc = "    Add folder changes to the Team Foundation Server Repository...";
					CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
					CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
					
					//logger.debug("TFS Unparsed Arguments: " + arguments);
					
				    // Parse the command arguments into a list
				    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
				    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
				    
				    //logger.debug("TFS Argument List: " + argList);
				    
				    // Execute the command line
				    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());	
				    */					
				}
				
				/********************************************
				 * [GIT] vcs_add__vcs_checkin:
				 *      GIT=Git
				 ********************************************/
				if (vcsStruct.getVcsType().equalsIgnoreCase("GIT")) {
					//cd "${Workspace}"
					//   e.g: vcsWorkspaceProject:  D:/PDTool/GITuw/cis_objects
					execFromDir=vcsStruct.getVcsWorkspaceProject();
					
					//------------------------------------------
					// Add Files or Folders
					//------------------------------------------
					
				    File dir = new File(baseFolderPath);
				    String excludeFileList = "";
				    boolean includeParentDir = false;
				    boolean includeFiles = true;
				    boolean includeDirs = true;
				    boolean recursive = true;
				    // Get the /resources/vcs_initial/baseFolders + additional processed folders by using vcsTemp as a checklist against the workspace
				    File[] files = CommonUtils.getFilesParent(dir, excludeFileList, includeParentDir, includeFiles, includeDirs, recursive);
				    
				    // Iterate over the checklist of files and add them into the VCS
				    for (int i=0; i < files.length; i++) 
					{
						String remainderPath = files[i].toString();			
						remainderPath = remainderPath.replaceAll(Matcher.quoteReplacement("\\"), "/");
						remainderPath = remainderPath.replaceAll(baseFolderPath, "");

						String fullResourcePath = (execFromDir+"/"+remainderPath).replaceAll("//", "/");
						//Derived from script:
					    // svn add ${VCS_OPTIONS} ${VCS_BASE_FOLDER_INIT_ADD} ${fullResourcePath}
						arguments=" add " + vcsStruct.getVcsOptions() + " " + vcsStruct.getVcsBaseFolderInitAddOptions() + " " + fullResourcePath;

						commandDesc = "    Add folder changes to the Subversion Repository...";
						CommonUtils.writeOutput(commandDesc,prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Command="+command+" "+CommonUtils.maskCommand(arguments),prefix,"-debug3",logger,debug1,debug2,debug3);
						CommonUtils.writeOutput("    VCS Execute Directory="+execFromDir,prefix,"-debug3",logger,debug1,debug2,debug3);
						
					    // Parse the command arguments into a list
					    argList = CommonUtils.parseArguments(argList, initArgList, command+" "+arguments, preserveQuotes, propertyFile);
					    envList = CommonUtils.getArgumentsList(envList, initArgList, vcsStruct.getVcsEnvironment(), "|");	
					    
					    // Execute the command line
					    getVCSDAO().execCommandLineVCS(prefix, execFromDir, command, argList, envList, vcsStruct.getVcsIgnoreMessages());
					}					
				}
				
				/********************************************
				 * vcs_add__vcs_checkin:
				 *      NEW_VCS_TYPE=New VCS Type
				 ********************************************/
				if (vcsStruct.getVcsType().equalsIgnoreCase("NEW_VCS_TYPE")) {
					// Implement VCS Checkin here
				}
				
			} catch (CompositeException e) {
			    CommonUtils.writeOutput("Action ["+prefix+"] Failed.",prefix,"-error",logger,debug1,debug2,debug3);
				logger.error("Failed executing "+prefix+".",e);
				throw new CompositeException(e.getMessage(), e);
			}
		}
	
/******************************************************************************************
 *  PRIVATE IMPLEMENTATION
 *  
 *  COMMON METHODS
 * 
 *******************************************************************************************/

	// Set the global suppress and debug properties used throughout this class
	private static void setGlobalProperties() {
		
		/*  Determine the property file name for this environment
		 *    1. Start with default file CommonConstants.propertyFile
		 *    2. Get Java Environment variables
		 *    3. Get OS System Environment variables
		 */

    	// Clear out any VCS Java Environment variables before stating;
    	clearVCSConnectionProperties();

		// Validate the configuration property file exists
        propertyFile = CommonUtils.getFileOrSystemPropertyValue(null, "CONFIG_PROPERTY_FILE");
        String propertyOrderPrecedence = CommonUtils.getFileOrSystemPropertyValue(propertyFile, "propertyOrderPrecedence");
        if (propertyOrderPrecedence == null || propertyOrderPrecedence.trim().length() == 0)
        	propertyOrderPrecedence = CommonConstants.propertyOrderPrecedenceDefault;

		logger.info("");
		logger.info("----------------------------------------------");
		logger.info("CONFIG_PROPERTY_FILE="+propertyFile);
		logger.info("propertyOrderPrecedence="+propertyOrderPrecedence);
		logger.info("----------------------------------------------");
		if (!PropertyManager.getInstance().doesPropertyFileExist(propertyFile)) {
			throw new ApplicationException("The property file does not exist for CONFIG_PROPERTY_FILE="+propertyFile);
		}
	
    	//set to -suppress if the property file contains a SUPPRESS_COMMENTS=true
		suppress="";
		String SUPPRESS_COMMENTS = CommonUtils.getFileOrSystemPropertyValue(propertyFile, "SUPPRESS_COMMENTS");
		if (SUPPRESS_COMMENTS != null && SUPPRESS_COMMENTS.equalsIgnoreCase("true")) {
			suppress=" -suppress";
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

	    // Diffmerger Verbose allows the VCS Diffmerger process to output more information when set to true [Default=true]
	    String verbose = CommonUtils.getFileOrSystemPropertyValue(propertyFile, "DIFFMERGER_VERBOSE");
	    diffmergerVerbose = true;
	    if (verbose != null && validOptions.contains(verbose)) {
	    	diffmergerVerbose = Boolean.valueOf(verbose);
	    }

	    // Diffmerger Verbose allows the VCS Diffmerger process to output more information when set to true [Default=true]
	    String verbosechanges = CommonUtils.getFileOrSystemPropertyValue(propertyFile, "DIFFMERGER_VERBOSE_CHANGES");
	    diffmergerVerboseChanges = false;
	    if (verbosechanges != null && validOptions.contains(verbosechanges)) {
	    	diffmergerVerboseChanges = Boolean.valueOf(verbosechanges);
	    }

	    /* There are four VCS scenarios described below.  What is important is
	     * whether the VCS Multi-User [Direct VCS Access] Topology is being used (true) or not (false).
	     * The default is to set VCS_MULTI_USER_TOPOLOGY=false.
	     * 1. Single-Node Topology:  			VCS_MULTI_USER_TOPOLOGY=false
	     * 2. Multi-Node Topology:  			VCS_MULTI_USER_TOPOLOGY=false
	     * 3. Multi-User (Managed) Topology:  	VCS_MULTI_USER_TOPOLOGY=false
	     * 4. Multi-User (Direct) Topology:  	VCS_MULTI_USER_TOPOLOGY=true
	    */
	    String topology = CommonUtils.getFileOrSystemPropertyValue(propertyFile, "VCS_MULTI_USER_TOPOLOGY");
	    vcsMultiUserTopology = false;
	    if (topology != null && validOptions.contains(topology)) {
	    	vcsMultiUserTopology = Boolean.valueOf(topology);
	    }

	    /* VCS_MULTI_USER_DISABLE_CHECKOUT - In a multi-user, central CIS development server environment
	     * it may be advantageous to disable the ability for users to perform checkout so that they
	     *  do no inadvertantly remove resources from other people.  In this scenario, it would be preferred
	     *  to have a single CIS administrator checkout resources into the central CIS repository.
	    */
	    String checkout = CommonUtils.getFileOrSystemPropertyValue(propertyFile, "VCS_MULTI_USER_DISABLE_CHECKOUT");
	    vcsMultiUserDisableCheckout = false;
	    if (checkout != null && validOptions.contains(checkout)) {
	    	vcsMultiUserDisableCheckout = Boolean.valueOf(checkout);
	    }
	 
	    /* VCSModule_ExternalVcsResourceTypeList - 
	     *  This provides an externalized mechanism to teach PD Tool about new Resource Types and how 
		 *  they are associated with the basic VCS Resource Types.  The basic VCS Resource Types include:
		 *     folder,definitions,link,procedure,table,tree,trigger
		 *	
		 *	Each Studio Resource contains an info tab with a resource path and a display type.
		 *	Use the Resource path in the info tab as input into the following Studio Web Service API
		 *	to discover the CIS Resource Type.
		 *	  /services/webservices/system/admin/resource/operations/getResource()
		 *	
		 *	The CIS Resource Type is mapped to one of the basic VCS Resource Types provided above.
		 *	Finally, provide the name value pair in the form of "VCS Resource Type=Studio Display Resource Type"
		 *	Create a comma separate list of these name=value pairs.  For example:
		 *	folder=Data Source,folder=Composite Database,procedure=Basic Transformation 
		 *
	     * This is a fall back position where a customer may find
	     * additional Resource Types that were not specified in the code.  This mechanism externalizes
	     * the ability to get those external VCS resource types.  The list will be a comma separate list
	     * in the form of vcsResourceType=StudioDisplayResourceType.  
	     * 
		 * Valid VCS Resource Types: folder,definitions,link,procedure,table,tree,trigger
		 * 
		 * For example:
	     * folder=Data Source,folder=Composite Database,procedure=Basic Transformation
	     * 
		 */
	    String vcsResourceList = CommonUtils.getFileOrSystemPropertyValue(propertyFile, "VCSModule_ExternalVcsResourceTypeList");
	    if (vcsResourceList != null && vcsResourceList.length() > 0) {
	    	StringTokenizer tokens = new StringTokenizer(vcsResourceList, ",");
	    	while (tokens.hasMoreTokens()) {
	    		String token = tokens.nextToken();
	    		// Only the process the token if it contains an = (vcsType=displayType)
	    		if (token.contains("=")) {
	    			int idx = token.indexOf("=");
	    			String vcsType = token.substring(0,idx).trim();
	    			String displayType = token.substring(idx+1).trim();
	    			/* 
	    			 * Valid VCS Resource Types: FOLDER,definitions,link,procedure,table,tree,trigger,data_source,relationship,model,policy
	    			 * 
	    			 */
	    			if (vcsType.equalsIgnoreCase("folder")) {
	    				externalVcsResourceTypeFolderList = externalVcsResourceTypeFolderList + displayType + ",";	    				
	    			}
	    			if (vcsType.equalsIgnoreCase("definitions")) {
	    				externalVcsResourceTypeDefinitionsList = externalVcsResourceTypeDefinitionsList + displayType + ",";	    				
	    			}
	    			if (vcsType.equalsIgnoreCase("link")) {
	    				externalVcsResourceTypeLinkList = externalVcsResourceTypeLinkList + displayType + ",";	    				
	    			}
	    			if (vcsType.equalsIgnoreCase("procedure")) {
	    				externalVcsResourceTypeProcedureList = externalVcsResourceTypeProcedureList + displayType + ",";	    				
	    			}
	    			if (vcsType.equalsIgnoreCase("table")) {
	    				externalVcsResourceTypeTableList = externalVcsResourceTypeTableList + displayType + ",";	    				
	    			}
	    			if (vcsType.equalsIgnoreCase("tree")) {
	    				externalVcsResourceTypeTreeList = externalVcsResourceTypeTreeList + displayType + ",";    				
	    			}
	    			if (vcsType.equalsIgnoreCase("trigger")) {
	    				externalVcsResourceTypeTriggerList = externalVcsResourceTypeTriggerList + displayType + ",";
	    			}
	    			// 2012-10-29 mtinius: added data_source, relationship, model and policy
	    			if (vcsType.equalsIgnoreCase("data_source")) {
    					externalVcsResourceTypeFolderList = externalVcsResourceTypeDataSourceList + displayType + ",";	    				
    				}
	    			if (vcsType.equalsIgnoreCase("relationship")) {
	    				externalVcsResourceTypeRelationshipList = externalVcsResourceTypeRelationshipList + displayType + ",";
	    			}
	    			if (vcsType.equalsIgnoreCase("model")) {
	    				externalVcsResourceTypeModelList = externalVcsResourceTypeModelList + displayType + ",";
	    			}
	    			if (vcsType.equalsIgnoreCase("policy")) {
	    				externalVcsResourceTypePolicyList = externalVcsResourceTypePolicyList + displayType + ",";
	    			}
	    			
	    		}
	    	}
	    } 
	}

	// Clear the JVM VCS environment properties which are used by the VCS Version 2 methods
	private static void clearVCSConnectionProperties() {

		// Only clear the Java Environment when the non-V2 methods are being invoked
		if (!vcsV2Method) {
			String oldProp = null;
			// VCS_TYPE
			oldProp = System.clearProperty("VCS_TYPE");
			// VCS_BASE_TYPE
			oldProp = System.clearProperty("VCS_BASE_TYPE");
			// VCS_HOME
			oldProp = System.clearProperty("VCS_HOME");
			// VCS_COMMAND
			oldProp = System.clearProperty("VCS_COMMAND");
			// VCS_EXEC_FULL_PATH
			oldProp = System.clearProperty("VCS_EXEC_FULL_PATH");
			// VCS_REPOSITORY_URL
			oldProp = System.clearProperty("VCS_REPOSITORY_URL");
			// VCS_PROJECT_ROOT
			oldProp = System.clearProperty("VCS_PROJECT_ROOT");
			// VCS_WORKSPACE_HOME
			oldProp = System.clearProperty("VCS_WORKSPACE_HOME");
			// VCS_WORKSPACE_DIR
			oldProp = System.clearProperty("VCS_WORKSPACE_NAME");
			// VCS_WORKSPACE_DIR
			oldProp = System.clearProperty("VCS_WORKSPACE_DIR");
			// VCS_TEMP_DIR
			oldProp = System.clearProperty("VCS_TEMP_DIR");
			// VCS_OPTIONS
			oldProp = System.clearProperty("VCS_OPTIONS");

			// VCS_WORKSPACE_INIT_NEW_OPTIONS
			oldProp = System.clearProperty("VCS_WORKSPACE_INIT_NEW_OPTIONS");
			// VCS_WORKSPACE_INIT_LINK_OPTIONS
			oldProp = System.clearProperty("VCS_WORKSPACE_INIT_LINK_OPTIONS");
			// VCS_WORKSPACE_INIT_GET_OPTIONS
			oldProp = System.clearProperty("VCS_WORKSPACE_INIT_GET_OPTIONS");
			// VCS_BASE_FOLDER_INIT_ADD
			oldProp = System.clearProperty("VCS_BASE_FOLDER_INIT_ADD");
			// VCS_CHECKIN_OPTIONS
			oldProp = System.clearProperty("VCS_CHECKIN_OPTIONS");
			// VCS_CHECKIN_OPTIONS_REQUIRED
			oldProp = System.clearProperty("VCS_CHECKIN_OPTIONS_REQUIRED");
			// VCS_CHECKOUT_OPTIONS
			oldProp = System.clearProperty("VCS_CHECKOUT_OPTIONS");
			// VCS_CHECKOUT_OPTIONS_REQUIRED
			oldProp = System.clearProperty("VCS_CHECKOUT_OPTIONS_REQUIRED");
			// VCS_CIS_IMPORT_OPTIONS
			oldProp = System.clearProperty("VCS_CIS_IMPORT_OPTIONS");
			// VCS_CIS_EXPORT_OPTIONS
			oldProp = System.clearProperty("VCS_CIS_EXPORT_OPTIONS");

			// VCS_USERNAME
			oldProp = System.clearProperty("VCS_USERNAME");
			// VCS_PASSWORD
			oldProp = System.clearProperty("VCS_PASSWORD");
			// VCS_IGNORE_MESSAGES
			oldProp = System.clearProperty("VCS_IGNORE_MESSAGES");
			// VCS_MESSAGE_PREPEND
			oldProp = System.clearProperty("VCS_MESSAGE_PREPEND");
			// TFS_SERVER_URL
			oldProp = System.clearProperty("TFS_SERVER_URL");
			// VCS_MESSAGE_MANDATORY
/* 3-7-2012: may not need 		
			oldProp = System.clearProperty("VCS_MESSAGE_MANDATORY");
*/
		}
	}
	
	// Retrieve the VCSModule.xml file and locate the requested vcsConnectionId and set the corresponding JVM environment properties
	// This is used by the VCS Version 2 methods
	private static void setVCSConnectionProperties(String vcsConnectionId, String pathToVcsXML) {
		// Set the global variable that indicates that a VCS Version 2 method is invoking this procedure.
		// Original VCS methods do not invoke this method
		vcsV2Method = true;
		boolean vcsConnectionFound = false;
		String prefix = "setVCSConnectionProperties";
		
		// validate incoming arguments
		if(vcsConnectionId == null || vcsConnectionId.trim().length() ==0 || pathToVcsXML == null || pathToVcsXML.trim().length() ==0){
			throw new ValidationException("Invalid Arguments");
		}

		// Extract variables for the vcsConnectionId
		vcsConnectionId = CommonUtils.extractVariable(prefix, vcsConnectionId, propertyFile, true);

		try {	
			//using jaxb convert xml to corresponding java objects
			VCSModule vcsModuleType = (VCSModule)XMLUtils.getModuleTypeFromXML(pathToVcsXML);
				
			if(vcsModuleType != null && vcsModuleType.getVcsConnections() != null && !vcsModuleType.getVcsConnections().getVcsConnection().isEmpty()){
				
				if (vcsModuleType.getVcsConnections().getVcsConnection().size() > 0) {
					List<VCSConnectionType> vcsConnectionList = vcsModuleType.getVcsConnections().getVcsConnection();

					for (VCSConnectionType vcsConnection : vcsConnectionList) {

						// Get the identifier and convert any $VARIABLES
						String identifier = CommonUtils.extractVariable(prefix, vcsConnection.getId(), propertyFile, true);
						
						/**
						 * Possible values for archives 
						 * 1. csv string like import1,import2 (we process only resource names which are passed in)
						 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
						 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
						 * 	  like -import1,import3 (we ignore passed in resources and process rest of the in the input xml
						 */
						// Determine if the XML entry is in the list of vcsConnectionId passed in
						if(DeployUtil.canProcessResource(vcsConnectionId, identifier)){
											
							// Set the vcs connection found flag
							vcsConnectionFound = true;
							
							// VCS_TYPE
							String vcsType = vcsConnection.getVCSTYPE();
							if (vcsType == null || vcsType.isEmpty()) {
								throw new ValidationException("VCSModule XML Connection="+vcsConnectionId+": VCS_TYPE cannot be emtpy.");						
							}
							String oldProp = System.setProperty("VCS_TYPE", CommonUtils.extractVariable(prefix, vcsConnection.getVCSTYPE(), propertyFile, true));

							// VCS_BASE_TYPE
							String vcsBaseType = vcsConnection.getVCSBASETYPE();
							if (vcsBaseType == null || vcsBaseType.isEmpty()) {
								// If the VCS_BASE_TYPE does not exist then use the VCS_TYPE value.
								System.setProperty("VCS_BASE_TYPE", vcsType);				
							} else {
								oldProp = System.setProperty("VCS_BASE_TYPE", CommonUtils.extractVariable(prefix, vcsConnection.getVCSBASETYPE(), propertyFile, true));
							}
							
							// VCS_HOME
							if (vcsConnection.getVCSHOME() == null || vcsConnection.getVCSHOME().isEmpty()) {
								throw new ValidationException("VCSModule XML Connection="+vcsConnectionId+": VCS_HOME cannot be emtpy.");						
							}
							oldProp = System.setProperty("VCS_HOME", CommonUtils.extractVariable(prefix, vcsConnection.getVCSHOME(), propertyFile, true));

							// VCS_COMMAND
							if (vcsConnection.getVCSCOMMAND() == null || vcsConnection.getVCSCOMMAND().isEmpty()) {
								throw new ValidationException("VCSModule XML Connection="+vcsConnectionId+": VCS_COMMAND cannot be emtpy.");						
							}
							oldProp = System.setProperty("VCS_COMMAND", CommonUtils.extractVariable(prefix, vcsConnection.getVCSCOMMAND(), propertyFile, true));

							// VCS_EXEC_FULL_PATH
							if (vcsConnection.getVCSEXECFULLPATH() == null || vcsConnection.getVCSEXECFULLPATH().isEmpty()) {
								throw new ValidationException("VCSModule XML Connection="+vcsConnectionId+": VCS_EXEC_FULL_PATH cannot be emtpy.");						
							}
							oldProp = System.setProperty("VCS_EXEC_FULL_PATH", CommonUtils.extractVariable(prefix, vcsConnection.getVCSEXECFULLPATH(), propertyFile, true));

							// VCS_REPOSITORY_URL
							if (vcsConnection.getVCSREPOSITORYURL() == null || vcsConnection.getVCSREPOSITORYURL().isEmpty()) {
								throw new ValidationException("VCSModule XML Connection="+vcsConnectionId+": VCS_REPOSITORY_URL cannot be emtpy.");						
							}
							oldProp = System.setProperty("VCS_REPOSITORY_URL", CommonUtils.extractVariable(prefix, vcsConnection.getVCSREPOSITORYURL(), propertyFile, true));

							// VCS_PROJECT_ROOT
							if (vcsConnection.getVCSPROJECTROOT() == null || vcsConnection.getVCSPROJECTROOT().isEmpty()) {
								throw new ValidationException("VCSModule XML Connection="+vcsConnectionId+": VCS_PROJECT_ROOT cannot be emtpy.");						
							}
							oldProp = System.setProperty("VCS_PROJECT_ROOT", CommonUtils.extractVariable(prefix, vcsConnection.getVCSPROJECTROOT(), propertyFile, true));

							// VCS_WORKSPACE_HOME
							if (vcsConnection.getVCSWORKSPACEHOME() == null || vcsConnection.getVCSWORKSPACEHOME().isEmpty()) {
								throw new ValidationException("VCSModule XML Connection="+vcsConnectionId+": VCS_WORKSPACE_HOME cannot be emtpy.");						
							}
							oldProp = System.setProperty("VCS_WORKSPACE_HOME", CommonUtils.extractVariable(prefix, vcsConnection.getVCSWORKSPACEHOME(), propertyFile, true));

							// VCS_WORKSPACE_NAME
							if (vcsConnection.getVCSWORKSPACENAME() == null || vcsConnection.getVCSWORKSPACENAME().isEmpty()) {
								throw new ValidationException("VCSModule XML Connection="+vcsConnectionId+": VCS_WORKSPACE_NAME cannot be emtpy.");						
							}
							oldProp = System.setProperty("VCS_WORKSPACE_NAME", CommonUtils.extractVariable(prefix, vcsConnection.getVCSWORKSPACENAME(), propertyFile, true));

							// VCS_WORKSPACE_DIR
							if (vcsConnection.getVCSWORKSPACEDIR() == null || vcsConnection.getVCSWORKSPACEDIR().isEmpty()) {
								throw new ValidationException("VCSModule XML Connection="+vcsConnectionId+": VCS_WORKSPACE_DIR cannot be emtpy.");						
							}
							oldProp = System.setProperty("VCS_WORKSPACE_DIR", CommonUtils.extractVariable(prefix, vcsConnection.getVCSWORKSPACEDIR(), propertyFile, true));
							
							// VCS_TEMP_DIR
							if (vcsConnection.getVCSTEMPDIR() == null || vcsConnection.getVCSTEMPDIR().isEmpty()) {
								throw new ValidationException("VCSModule XML Connection="+vcsConnectionId+": VCS_TEMP_DIR cannot be emtpy.");						
							}
							oldProp = System.setProperty("VCS_TEMP_DIR", CommonUtils.extractVariable(prefix, vcsConnection.getVCSTEMPDIR(), propertyFile, true));

							// VCS_OPTIONS
							oldProp = System.setProperty("VCS_OPTIONS", CommonUtils.extractVariable(prefix, vcsConnection.getVCSOPTIONS(), propertyFile, true));

							// VCS_WORKSPACE_INIT_NEW_OPTIONS
							if (vcsConnection.getVCSWORKSPACEINITNEWOPTIONS() != null)
								oldProp = System.setProperty("VCS_WORKSPACE_INIT_NEW_OPTIONS", CommonUtils.extractVariable(prefix, vcsConnection.getVCSWORKSPACEINITNEWOPTIONS(), propertyFile, true));
							// VCS_WORKSPACE_INIT_LINK_OPTIONS
							if (vcsConnection.getVCSWORKSPACEINITLINKOPTIONS() != null)
								oldProp = System.setProperty("VCS_WORKSPACE_INIT_LINK_OPTIONS", CommonUtils.extractVariable(prefix, vcsConnection.getVCSWORKSPACEINITLINKOPTIONS(), propertyFile, true));
							// VCS_WORKSPACE_INIT_GET_OPTIONS
							if (vcsConnection.getVCSWORKSPACEINITGETOPTIONS() != null)
								oldProp = System.setProperty("VCS_WORKSPACE_INIT_GET_OPTIONS", CommonUtils.extractVariable(prefix, vcsConnection.getVCSWORKSPACEINITGETOPTIONS(), propertyFile, true));						
							// VCS_BASE_FOLDER_INIT_ADD
							if (vcsConnection.getVCSBASEFOLDERINITADD() != null)
								oldProp = System.setProperty("VCS_BASE_FOLDER_INIT_ADD", CommonUtils.extractVariable(prefix, vcsConnection.getVCSBASEFOLDERINITADD(), propertyFile, true));
							// VCS_CHECKIN_OPTIONS
							if (vcsConnection.getVCSCHECKINOPTIONS() != null)
								oldProp = System.setProperty("VCS_CHECKIN_OPTIONS", CommonUtils.extractVariable(prefix, vcsConnection.getVCSCHECKINOPTIONS(), propertyFile, true));
							// VCS_CHECKIN_OPTIONS_REQUIRED
							if (vcsConnection.getVCSCHECKINOPTIONSREQUIRED() != null)
								oldProp = System.setProperty("VCS_CHECKIN_OPTIONS_REQUIRED", CommonUtils.extractVariable(prefix, vcsConnection.getVCSCHECKINOPTIONSREQUIRED(), propertyFile, true));
							// VCS_CHECKOUT_OPTIONS
							if (vcsConnection.getVCSCHECKOUTOPTIONS() != null)
								oldProp = System.setProperty("VCS_CHECKOUT_OPTIONS", CommonUtils.extractVariable(prefix, vcsConnection.getVCSCHECKOUTOPTIONS(), propertyFile, true));
							// VCS_CHECKOUT_OPTIONS_REQUIRED
							if (vcsConnection.getVCSCHECKOUTOPTIONSREQUIRED() != null)
								oldProp = System.setProperty("VCS_CHECKOUT_OPTIONS_REQUIRED", CommonUtils.extractVariable(prefix, vcsConnection.getVCSCHECKOUTOPTIONSREQUIRED(), propertyFile, true));
							// VCS_CIS_IMPORT_OPTIONS
							if (vcsConnection.getVCSCISIMPORTOPTIONS() != null)
								oldProp = System.setProperty("VCS_CIS_IMPORT_OPTIONS", CommonUtils.extractVariable(prefix, vcsConnection.getVCSCISIMPORTOPTIONS(), propertyFile, true));
							// VCS_CIS_EXPORT_OPTIONS
							if (vcsConnection.getVCSCISEXPORTOPTIONS() != null)
								oldProp = System.setProperty("VCS_CIS_EXPORT_OPTIONS", CommonUtils.extractVariable(prefix, vcsConnection.getVCSCISEXPORTOPTIONS(), propertyFile, true));

							// VCS_USERNAME
							oldProp = System.setProperty("VCS_USERNAME", CommonUtils.extractVariable(prefix, vcsConnection.getVCSUSERNAME(), propertyFile, true));
							// VCS_PASSWORD
							oldProp = System.setProperty("VCS_PASSWORD", CommonUtils.extractVariable(prefix, vcsConnection.getVCSPASSWORD(), propertyFile, true));
							// VCS_IGNORE_MESSAGES
							oldProp = System.setProperty("VCS_IGNORE_MESSAGES", CommonUtils.extractVariable(prefix, vcsConnection.getVCSIGNOREMESSAGES(), propertyFile, true));
							// VCS_MESSAGE_PREPEND
							oldProp = System.setProperty("VCS_MESSAGE_PREPEND", CommonUtils.extractVariable(prefix, vcsConnection.getVCSMESSAGEPREPEND(), propertyFile, true));
							// VCS_MESSAGE_MANDATORY
							/* 3-7-2012: may not need 		
							//oldProp = System.setProperty("VCS_MESSAGE_MANDATORY", vcsConnection.getVCSMESSAGEMANDATORY());
							*/
							// Extract the user defined name value pairs P4_ENV, CVS_ENV, SVN_ENV, TFS_ENV, or GIT_ENV
							if (vcsConnection.getVcsSpecificEnvVars() != null && !vcsConnection.getVcsSpecificEnvVars().getEnvVar().isEmpty()) {
								
								String envList = "";
								List<VCSConnectionEnvNameValuePairType> nameValuePairList = vcsConnection.getVcsSpecificEnvVars().getEnvVar();			
								
								// Loop through the user-defined property list and concatenate them to an environment list
								// This is similar to how they are handled in the deploy.properties or studio.properties file
								for (VCSConnectionEnvNameValuePairType nameValue : nameValuePairList) {
									
									String envName = nameValue.getEnvName();
									String envValue = CommonUtils.extractVariable(prefix, nameValue.getEnvValue(), propertyFile, true);
								    if (vcsType.equalsIgnoreCase("P4") && envName.equalsIgnoreCase("P4PASSWD")) {
								    	envValue = CommonUtils.decrypt(envValue);
								    }
									oldProp = System.setProperty(envName, envValue);
									if (envList.length() > 0) {
										envList = envList + ",";
									}
									envList = envList + envName;
								}
						    	// Set then JVM property for the VCS type being used [SVN, P4, CVS, TFS2005, TFS2010, TFS2012, TFS2013, etc]
								if (envList.length() > 0) {
								    if (vcsType.equalsIgnoreCase("P4")) {
										oldProp = System.setProperty("P4_ENV", envList);
								    }
								    if (vcsType.equalsIgnoreCase("CVS")) {
										oldProp = System.setProperty("CVS_ENV", envList);
								    	
								    }
						    		if (vcsType.equalsIgnoreCase("SVN")) {
										oldProp = System.setProperty("SVN_ENV", envList);
						    		}
						    		
						    		if (vcsType.equalsIgnoreCase("TFS2005") || 
						    			vcsType.equalsIgnoreCase("TFS2010") || 
						    			vcsType.equalsIgnoreCase("TFS2012") || 
						    			vcsType.equalsIgnoreCase("TFS2013") ) 
						    		{
						    			// set TFS_ENV
										oldProp = System.setProperty("TFS_ENV", envList);
						    		}
						    		if (vcsType.equalsIgnoreCase("GIT")) {
										oldProp = System.setProperty("GIT_ENV", envList);
						    		}
						    		if (vcsType.equalsIgnoreCase("CLC")) {
										oldProp = System.setProperty("CLC_ENV", envList);
						    		}
						    		//--------------------------------------------------------------
						    		// setVCSConnectionProperties: New VCS Type (new) specific settings
						    		//--------------------------------------------------------------
						    		if (vcsType.equalsIgnoreCase("ABC")) {
										oldProp = System.setProperty("ABC_ENV", envList);
						    		}
								}
							}
						}
					}
				}	
			}
			
			if (!vcsConnectionFound)
				throw new ApplicationContextException("The selected VCS Connection Id (VCONN) \""+vcsConnectionId+"\" was not found in the file "+pathToVcsXML);

		} catch (CompositeException e) {
			String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Parse VCSModule XML", "setVCSConnectionProperties", pathToVcsXML, null);

			logger.error(errorMessage , e);
			throw new ApplicationContextException(errorMessage, e);
		}
	}

	// Validate the server properties to insure there are no null values - return default values for null values
	private String getConvertedVcsResourceType(String resourceType) throws ValidationException {

		/*
		 * The official list of valid types:
		 *     	CONTAINER_OR_DATA_SOURCE
			    CONTAINER
			    DATA_SOURCE
			    DEFINITION_SET
			    LINK
			    PROCEDURE
			    TABLE
			    TREE
			    TRIGGER
			    RELATIONSHIP
			    MODEL
			    POLICY

		 * 	TYPES / SUBTYPES:
		=================
		The following resource types/subtypes are supported by this operation.  Resources cannot be created under "/services" unless otherwise noted, and cannot be created	within a physical data source. 
		
		(Datasource table columns)
		* COLUMN / n/a - The column type is only used when updating privileges on a table column.
	
		(Basic CIS folder)
		* CONTAINER / FOLDER_CONTAINER - A Composite folder.   Cannot be created anywhere under /services except in another FOLDER under /services/webservices.
		* CONTAINER / DIRECTORY_CONTAINER - A Composite directory.
		(Database)
		* CONTAINER / CATALOG_CONTAINER - A Composite catalog folder under a data source.  Can only be created within a data source under /services/databases.
		* CONTAINER / SCHEMA_CONTAINER - A Composite schema container.  Can only be created within a CATALOG that is under /services/databases.
		(Web Services)
		* CONTAINER / SERVICE_CONTAINER - A web service container for the service.  Can only be created within a Composite Web Services data source that is under /services/webservices.
		* CONTAINER / OPERATIONS_CONTAINER - A web service container for the operations
		* CONTAINER / PORT_CONTAINER - A Composite web service container for port.   Can only be created within a SERVICE under /services/webservices.
		(Connectors)
		* CONTAINER / CONNECTOR_CONTAINER - A Composite container for connectors.
	
		* CONNECTOR / JMS - A Composite JMS Connector.  Created with no connection information
		* CONNECTOR / HTTP - A Composite HTTP Connector.  Created with no connection information
	
		* DATA_SOURCE / RELATIONAL_DATA_SOURCE - A relational database source.
		* DATA_SOURCE / FILE_DATA_SOURCE - A comma separate file data source.
		* DATA_SOURCE / XML_FILE_DATA_SOURCE - An XML file data source.
		* DATA_SOURCE / WSDL_DATA_SOURCE - A Composite web service data source.
		* DATA_SOURCE / XML_HTTP_DATA_SOURCE - An HTTP XML data source.
		* DATA_SOURCE / NONE - A custom java procedure data source.
	
		* DEFINITION_SET / SQL_DEFINITION_SET - A Composite SQL Definition set.
		* DEFINITION_SET / XML_SCHEMA_DEFINITION_SET - A Composite XML Schema Defintion set.
		* DEFINITION_SET / WSDL_DEFINITION_SET - A Composite WSDL Definition set.
		* DEFINITION_SET / ABSTRACT_WSDL_DEFINITION_SET - A Composite Abstract WSDL Definition set such as the ones imported from Designer.
		* DEFINITION_SET / SCDL_DEFINITION_SET - A Composite SCA composite Definition set imported from Designer.
	
		* LINK / sub-type unknown - Used to link a Composite Data Service to a Composite resource such as a view or sql procedure.
	
		(CIS procedures)
		* PROCEDURE / SQL_SCRIPT_PROCEDURE - A Composite SQL Procedure.  Created with a simple default script body that is runnable.
		(Custom procedures)
		* PROCEDURE / JAVA_PROCEDURE - A Composite java data source procedure.  Created from a java data source (jar file).
		(Database procedures)
		* PROCEDURE / EXTERNAL_SQL_PROCEDURE - A Composite Packaged Query.  Created with no SQL text, so it is not runnable.  
		* PROCEDURE / DATABASE_PROCEDURE - A database stored procedure.
		(XML procedures)
		* PROCEDURE / BASIC_TRANSFORM_PROCEDURE - A Composite Basic XSLT Transformation procedure.  Created with no target procedure and no output columns, so it is not runnable.
		* PROCEDURE / XSLT_TRANSFORM_PROCEDURE - A Composite XSLT Transformation procedure.  Created with no target procedure and no output columns, so it is not runnable.
		* PROCEDURE / STREAM_TRANSFORM_PROCEDURE - A Composite XSLT Streaming Transformation procedure.  Created with no target procedure and no output columns, so it is not runnable.
		* PROCEDURE / XQUERY_TRANSFORM_PROCEDURE - A Composite XQUERY Transformation Procedure.  Created with no target schema and no model, so it is not runnable.
		(Misc procedures)
		* PROCEDURE / OPERATION_PROCEDURE - A Composite web service or HTTP procedure operation.
	
		* TABLE / SQL_TABLE - A Composite View.  Created with no SQL text or model, so it is not runnable.  
		* TABLE / DATABASE_TABLE - A Composite database table.
		* TABLE / DELIMITED_FILE_TABLE - A Composite delimited file table
		* TABLE / SYSTEM_TABLE - A Composite system table view.
	
		* TREE / XML_FILE_TREE - The XML tree structure associated with a file-XML data source.
	
		* TRIGGER / NONE - A Composite trigger.   Created disabled.
		*/

		
		/* 
		 * Valid VCS Resource Types: FOLDER,definitions,link,procedure,table,tree,trigger
		 * 
		 */

		if (resourceType == null)
			return null;
		
		// Folder	
		// 2012-10-29 mtinius:
		String validInternalFolderList = "FOLDER,CONTAINER";
		String validDisplayFolderList = "Folder,Web Service Service,Web Service Operations,Web Service Port";
		
		// Allow External Resource Type definitions to override Internal Resource Type definitions
		if (externalVcsResourceTypeFolderList.contains(resourceType)) {
			return "FOLDER";
		} else  if (validInternalFolderList.contains(resourceType) || validDisplayFolderList.contains(resourceType)) {
			return "FOLDER";
		}
		
		// 2012-10-29 mtinius:
		// Data_Source
		String validInternalDataSourceList = "DATA_SOURCE";
		String validDisplayDataSourceList = "Data Source,Composite Database,Composite Web Service,Legacy Composite Web Service";
		// Allow External Resource Type definitions to override Internal Resource Type definitions
		if (externalVcsResourceTypeFolderList.contains(resourceType)) {
			return "data_source";
		} else  if (validInternalDataSourceList.contains(resourceType) || validDisplayDataSourceList.contains(resourceType)) {
			return "data_source";
		}

		// Definitions
		String validInternalDefinitionList = "DEFINITIONS,DEFINITION_SET,SQL_DEFINITION_SET,XML_SCHEMA_DEFINITION_SET,WSDL_DEFINITION_SET,ABSTRACT_WSDL_DEFINITION_SET,SCDL_DEFINITION_SET";
		String validDisplayDefinitionList = "XML Schema Definition Set,SQL Definition Set,Web Service Definitions";
		// Allow External Resource Type definitions to override Internal Resource Type definitions
		if (externalVcsResourceTypeDefinitionsList.contains(resourceType)) {
			return "definitions";
		} else  if (validInternalDefinitionList.contains(resourceType) || validDisplayDefinitionList.contains(resourceType)) {
			return "definitions";
		}

		// Procedure
		String validInternalProcedureList = "PROCEDURE,SQL_SCRIPT_PROCEDURE,JAVA_PROCEDURE,EXTERNAL_SQL_PROCEDURE,DATABASE_PROCEDURE,BASIC_TRANSFORM_PROCEDURE,XSLT_TRANSFORM_PROCEDURE,STREAM_TRANSFORM_PROCEDURE,XQUERY_TRANSFORM_PROCEDURE,OPERATION_PROCEDURE";
		String validDisplayProcedureList = "Script,Basic Transformation,XSLT Transformation,Web Service Operation,Packaged Query,XQuery Transformation,Parameterized Query";
		// Allow External Resource Type definitions to override Internal Resource Type definitions
		if (externalVcsResourceTypeProcedureList.contains(resourceType)) {
			return "procedure";
		} else 	if (validInternalProcedureList.contains(resourceType) || validDisplayProcedureList.contains(resourceType)) {
			return "procedure";
		}
	
		// Views/Tables
		String validInternalTableList = "VIEW,TABLE,SQL_TABLE,DATABASE_TABLE,DELIMITED_FILE_TABLE,SYSTEM_TABLE";
		String validDisplayTableList = "View,Table";
		if (externalVcsResourceTypeTableList.contains(resourceType)) {
			return "table";
		} else  if (validInternalTableList.contains(resourceType) || validDisplayTableList.contains(resourceType)) {
			return "table";
		}
	
		// Link
		String validInternalLinkList = "LINK";
		String validDisplayLinkList = "Link,Published Resource";
		if (externalVcsResourceTypeLinkList.contains(resourceType)) {
			return "link";
		} else  if (validInternalLinkList.contains(resourceType) || validDisplayLinkList.contains(resourceType)) {
			return "link";
		}
	
		// Tree
		String validInternalTreeList = "TREE,XML_FILE_TREE";
		String validDisplayTreeList = "Hierarchical";
		if (externalVcsResourceTypeTreeList.contains(resourceType)) {
			return "tree";
		} else  if (validInternalTreeList.contains(resourceType) || validDisplayTreeList.contains(resourceType)) {
			return "tree";
		}
	
		// Trigger
		String validInternalTriggerList = "TRIGGER";
		String validDisplayTriggerList = "Trigger";
		if (externalVcsResourceTypeTriggerList.contains(resourceType)) {
			return "trigger";
		} else  if (validInternalTriggerList.contains(resourceType) || validDisplayTriggerList.contains(resourceType)) {
			return "trigger";
		}

		// 2012-10-29 mtinius:
		// Relationship
		String validInternalRelationshipList = "RELATIONSHIP";
		String validDisplayRelationshipList = "Relationship";
		if (externalVcsResourceTypeRelationshipList.contains(resourceType)) {
			return "relationship";
		} else  if (validInternalRelationshipList.contains(resourceType) || validDisplayRelationshipList.contains(resourceType)) {
			return "relationship";
		}

		// 2012-10-29 mtinius:
		// Model
		String validInternalModelList = "MODEL";
		String validDisplayModelList = "Model";
		if (externalVcsResourceTypeModelList.contains(resourceType)) {
			return "model";
		} else  if (validInternalModelList.contains(resourceType) || validDisplayModelList.contains(resourceType)) {
			return "model";
		}

		// 2012-10-29 mtinius:
		// Policy
		String validInternalPolicyList = "POLICY";
		String validDisplayPolicyList = "Policy";
		if (externalVcsResourceTypePolicyList.contains(resourceType)) {
			return "policy";
		} else  if (validInternalPolicyList.contains(resourceType) || validDisplayPolicyList.contains(resourceType)) {
			return "policy";
		}

		return resourceType;
	}
	
	// 2012-10-29 mtinius:
	// Convert from "vcs file resource type" to CIS resource type
	private String getCisResourceTypeFromVcsResourceType(String resourceType) {

		if (resourceType.equalsIgnoreCase("FOLDER"))
			return "CONTAINER";
		if (resourceType.equalsIgnoreCase("definitions"))
			return "DEFINITION_SET";
		else
			return resourceType.toUpperCase(); 
	}
	
	
	// Validate the server properties to insure there are no null values - return default values for null values
	private CompositeServer validateServerProperties(CompositeServer serverInfo) throws ValidationException {
		/* Example XML Entry
		   	<id>localhost</id>
		    <hostname>localhost</hostname>
		    <port>9400</port>
		    <usage>DEV</usage>
		    <user>admin</user>
		    <encryptedpassword>Encrypted:7F6324FFD300BE8F</encryptedpassword>
		    <domain>composite</domain>
		    <cishome>D:/CompositeSoftware/CIS5.2.0</cishome>
		    <clustername>cluster2</clustername>
		    <site>US East</site>
    */
		String prefix="validateServerProperties";
		
		if (serverInfo.getDomain() == null || serverInfo.getDomain().length() == 0) {
			serverInfo.setDomain("composite");
			CommonUtils.writeOutput("The property <domain> is null or blank in the servers.xml property file.  Using default value of 'composite'.",prefix,"-info",logger,debug1,debug2,debug3);
		}
		if (serverInfo.getHostname() == null || serverInfo.getHostname().length() == 0) {
			throw new ValidationException("The property <hostname> is null or blank in the servers.xml property file for server info identified by serverId=["+serverInfo.getId()+"].");
		}
		if (serverInfo.getPort() <= 0) {
			throw new ValidationException("The property <port> contains a value <=0 in the servers.xml property file for server info identified by serverId=["+serverInfo.getId()+"].");
		}
		if (serverInfo.getUser() == null || serverInfo.getUser().length() == 0) {
			throw new ValidationException("The property <user> is null or blank in the servers.xml property file for server info identified by serverId=["+serverInfo.getId()+"].");
		}
		if (serverInfo.getPassword() == null || serverInfo.getPassword().length() == 0) {
			throw new ValidationException("The property <encryptedpassword> is null or blank in the servers.xml property file for server info identified by serverId=["+serverInfo.getId()+"].");
		}
		return serverInfo;
	}	

	// Create a directory
	private static void createDirectory(String prefix, String directory) throws CompositeException {
       // Create the directory
        if (!CommonUtils.fileExists(directory)) {
	        int maxTries = 3;
	        int attempts = 1;
	        boolean exitLoop = false;
	        while (maxTries > 0 && !exitLoop) {
				CommonUtils.writeOutput("Make directory attempt="+attempts+" :: create directory="+directory,prefix,"-debug3",logger,debug1,debug2,debug3);
				if (CommonUtils.mkdirs(directory)) {
		        	exitLoop = true;
		        }
				try {
					if (!exitLoop)
						Thread.sleep(milliSeconds);
				} 
				catch (Exception e){
				}
				maxTries--;
				attempts++;
	        }
	        if (!CommonUtils.fileExists(directory)) {
	           	throw new ValidationException("The directory ["+directory+"] could not be created.");	        	
	        }
        } else {
			CommonUtils.writeOutput("Make Directory::  Directory exists="+directory,prefix,"-debug3",logger,debug1,debug2,debug3);
        }

	}
	
	// Remove a directory
	private static void removeDirectory(String prefix, String directory) throws CompositeException {
        // Remove the directory
        if (CommonUtils.fileExists(directory)) {
			File dir = new File(directory);
	        int maxTries = 3;
	        int attempts = 1;
	        boolean exitLoop = false;
	        while (maxTries > 0 && !exitLoop) {
				CommonUtils.writeOutput("Remove directory attempt="+attempts+" :: delete directory="+directory,prefix,"-debug3",logger,debug1,debug2,debug3);
		        if (CommonUtils.removeDirectory(dir)) {
		        	exitLoop = true;
		        }
				try {
					if (!exitLoop)
						Thread.sleep(milliSeconds);
				} 
				catch (Exception e){
				}
				maxTries--;
				attempts++;
	        }
	        if (CommonUtils.fileExists(directory)) {
	           	throw new ValidationException("The directory ["+directory+"] could not be removed.");	        	
	        }	
        } else {
			CommonUtils.writeOutput("Remove directory:: Directory does not exist="+directory,prefix,"-debug3",logger,debug1,debug2,debug3);       	
        }
	}
	
	// Perform variable substitution
	private static String substituteVariables(String stringToReplace, HashMap<String, String> vcsSubstitutionVars) {
		if (vcsSubstitutionVars != null) {
			
			String[] keys = new String[vcsSubstitutionVars.keySet().size()];
			vcsSubstitutionVars.keySet().toArray(keys);

			for (int i=0; i < keys.length; i++) {
				String key = keys[i].toString();
				String value = vcsSubstitutionVars.get(key);
				if (value != null) {
					stringToReplace = stringToReplace.replaceAll(Matcher.quoteReplacement(key), Matcher.quoteReplacement(value));
				}
			}
		}
		return stringToReplace;
	}
		
	// Validate the required command line for checkin options
	private static void validateCheckinRequired(String vcsCheckinOptions, String vcsCheckinOptionsRequired) throws ValidationException
	{
		boolean valid = true;
		String validationMessage = "The following \"checkin\" commands are required: ";
		String invalidList = null;
		
		if (vcsCheckinOptionsRequired != null && vcsCheckinOptionsRequired.trim().length() > 0) 
		{
			if (vcsCheckinOptions != null && vcsCheckinOptions.length() > 0) {
				StringTokenizer st = new StringTokenizer(vcsCheckinOptionsRequired.trim(), ",");
				while (st.hasMoreTokens()) {
					String requiredOption = st.nextToken();
					// Look for the required option within the vcsCheckinOptions passed in
					if (!vcsCheckinOptions.toLowerCase().trim().contains(requiredOption.toLowerCase().trim())) {
						// if a required option cannot be found in the passed in vcsCheckinOptions then it is not valid.
						if (invalidList == null) {
							invalidList = "";
						} else {
							invalidList = invalidList + ", ";
						}
						invalidList = invalidList + requiredOption;
						valid = false;
					}
				}
				if (!valid)
					validationMessage = validationMessage + invalidList;
			} else {
				validationMessage = validationMessage + vcsCheckinOptionsRequired.trim();
				valid = false;
			}
		}
		if (!valid)
			throw new ValidationException(validationMessage);
	}
	
	// Validate the required command line for checkout options
	private static void validateCheckoutRequired(String vcsCheckoutOptions, String vcsCheckoutOptionsRequired) throws ValidationException
	{
		boolean valid = true;
		String validationMessage = "The following \"checkout\" commands are required: ";
		String invalidList = null;
		
		if (vcsCheckoutOptionsRequired != null && vcsCheckoutOptionsRequired.trim().length() > 0) 
		{
			if (vcsCheckoutOptions != null && vcsCheckoutOptions.length() > 0) {
				StringTokenizer st = new StringTokenizer(vcsCheckoutOptionsRequired, ",");
				while (st.hasMoreTokens()) {
					String requiredOption = st.nextToken();
					// Look for the required option within the vcsCheckoutOptions passed in
					if (!vcsCheckoutOptions.toLowerCase().trim().contains(requiredOption.toLowerCase().trim())) {
						// if a required option cannot be found in the passed in vcsCheckoutOptions then it is not valid.
						if (invalidList == null) {
							invalidList = "";
						} else {
							invalidList = invalidList + ", ";
						}
						invalidList = invalidList + requiredOption;
						valid = false;
					}
				}
				if (!valid)
					validationMessage = validationMessage + invalidList;
			} else {
				validationMessage = validationMessage + vcsCheckoutOptionsRequired.trim();
				valid = false;
			}
		}
		if (!valid)
			throw new ValidationException(validationMessage);
	}
	
	
	// Class to hold the various VCS parameters to make it easier to pass to methods
	private class VcsStruct {
		// Acquired from the configuration property file
		private String systemPath;
	    private String projectHome;
	    private String vcsType;
	    private String vcsBaseType;
	    private String vcsHome;
	    private String vcsCommand;
	    private String vcsExecFullPath;
	    private String vcsOptions;
	    private String vcsWorkspaceInitNewOptions;
	    private String vcsWorkspaceInitLinkOptions;
	    private String vcsWorkspaceInitGetOptions;
	    private String vcsBaseFolderInitAddOptions;
	    private String vcsCheckinOptions;
	    private String vcsCheckinOptionsRequired;
	    private String vcsCheckoutOptions;
	    private String vcsCheckoutOptionsRequired;
	    private String vcsCisImportOptions;
	    private String vcsCisExportOptions;
	    private String vcsRepositoryUrl;
	    private String vcsWorkspaceHome;
	    private String vcsProjectRoot;
	    private String vcsUsername;
	    private String vcsPassword;
	    private String vcsIgnoreMessages;
	    private String vcsMessagePrepend;
	    private String vcsUseExistingWorkspace;
	    /* 3-7-2012: may not need 		
	    private String vcsMessageMandatory;
	    */
	    // Derived
	    private String vcsEnvironment;
	    private String vcsExecCommand;
	    private String vcsTemp;
	    private String vcsWorkspace;
	    private String vcsWorkspaceName;
	    private String vcsWorkspaceProject;
	    private String vcsLifecycleListener;
		private HashMap<String, String> vcsSubstitutionVars;
		private String tfsCheckinOptions;
		private String tfsServerUrl;

	    //constructor
	    private VcsStruct() {
			// Acquired from configuration property file
	    	systemPath = null;
	    	projectHome = null;
		    vcsType = null;
		    vcsBaseType = null;
		    vcsHome = null;
		    vcsCommand = null;
		    vcsExecFullPath = null;
		    vcsOptions = null;
		    vcsWorkspaceInitNewOptions = null;
		    vcsWorkspaceInitLinkOptions = null;
		    vcsWorkspaceInitGetOptions = null;
		    vcsBaseFolderInitAddOptions = null;
		    vcsCheckinOptions = null;
		    vcsCheckinOptionsRequired = null;
		    vcsCheckoutOptions = null;
		    vcsCheckoutOptionsRequired = null;
		    vcsCisImportOptions = null;
		    vcsCisExportOptions = null;
		    vcsRepositoryUrl = null;
		    vcsWorkspaceHome = null;
		    vcsProjectRoot = null;
		    vcsUsername = null;
		    vcsPassword = null;
		    vcsIgnoreMessages = null;
		    vcsMessagePrepend = null;
		    tfsCheckinOptions = null;
		    vcsUseExistingWorkspace = "false";
		    
		    tfsServerUrl = null;
		    /* 3-7-2012: may not need 		
		    vcsMessageMandatory = null;
		    */
		    // Derived
		    vcsEnvironment = null;
		    vcsExecCommand = null;
		    vcsWorkspace = null;
		    vcsTemp = null;
		    vcsWorkspaceName = null;
		    vcsWorkspaceProject = null;
		    vcsLifecycleListener = null;
		    vcsSubstitutionVars = new HashMap<String, String>();
	    }
		//-------------------------------------------------------------------
		// loadVcs: Derived variables
		//-------------------------------------------------------------------	
	    private void loadVcs(String prefix, String user, String password) {
	    	// Get the VCS_USERNAME from the (1) command or (2) Java Env or (3) Property File
	    	// Trim the username
			if (user == null || user.length() == 0) {
				this.setVcsUsername(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_USERNAME").trim(), propertyFile, true));
			} else {
				setVcsUsername(user.trim());
			}
			// Set the username in Java Env space so that it gets resolved when using it in the context of other commands for substitution
			System.setProperty("VCS_USERNAME", this.getVcsUsername());
			
	    	// Get the VCS_PASSWORD from the (1) command or (2) Java Env or (3) Property File
			// Trim the password
			if (password == null || password.length() == 0) {
				this.setVcsPassword(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_PASSWORD").trim(), propertyFile, true));
			} else {
				this.setVcsPassword(password.trim());
			}
			
			// Decrypt the VCS encrypted password string if the string contains encryptedpassword:
			this.setVcsPassword(CommonUtils.decrypt(this.getVcsPassword()));
			// Set the password in Java Env space so that it gets resolved when using it in the context of other commands for substitution
			System.setProperty("VCS_PASSWORD", this.getVcsPassword());

			//-------------------------------------------------------------------
			// loadVcs: Assign VCS specific environment variables from the configuration property file or from Java Environment variables
			//-------------------------------------------------------------------
	    	this.setSystemPath(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"PATH"), propertyFile, true));
	    	this.setProjectHome(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"PROJECT_HOME"), propertyFile, true));
	    	this.setVcsType(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_TYPE"), propertyFile, true));
	    	String vcsBaseType = CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_BASE_TYPE"), propertyFile, true);
	    	// If the VCS_BASE_TYPE is null or empty then set it to the VCS_TYPE
	    	if (vcsBaseType == null || vcsBaseType.length() == 0)
	    		vcsBaseType = this.getVcsType();
	    	this.setVcsBaseType(vcsBaseType);
	    	this.setVcsHome(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_HOME"), propertyFile, true));
	    	this.setVcsCommand(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_COMMAND"), propertyFile, true));
	    	this.setVcsExecFullPath(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_EXEC_FULL_PATH"), propertyFile, true));
	    	this.setVcsOptions(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_OPTIONS"), propertyFile, true));

	    	// 2014-03-06 mtinius: added generic capability for adding command line options at execution time.
	    	this.setVcsWorkspaceInitNewOptions(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_WORKSPACE_INIT_NEW_OPTIONS"), propertyFile, true));
	    	this.setVcsWorkspaceInitLinkOptions(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_WORKSPACE_INIT_LINK_OPTIONS"), propertyFile, true));
	    	this.setVcsWorkspaceInitGetOptions(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_WORKSPACE_INIT_GET_OPTIONS"), propertyFile, true));
	    	this.setVcsBaseFolderInitAddOptions(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_BASE_FOLDER_INIT_ADD"), propertyFile, true));    	
	    	this.setVcsCheckinOptions(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_CHECKIN_OPTIONS"), propertyFile, true));
	    	this.setVcsCheckinOptionsRequired(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_CHECKIN_OPTIONS_REQUIRED"), propertyFile, true));
	    	this.setVcsCheckoutOptions(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_CHECKOUT_OPTIONS"), propertyFile, true));
	    	this.setVcsCheckoutOptionsRequired(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_CHECKOUT_OPTIONS_REQUIRED"), propertyFile, true));
	    	this.setVcsCisImportOptions(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_CIS_IMPORT_OPTIONS"), propertyFile, true));
	    	this.setVcsCisExportOptions(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_CIS_EXPORT_OPTIONS"), propertyFile, true));

	    	this.setVcsRepositoryUrl(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_REPOSITORY_URL"), propertyFile, true));
	    	this.setVcsProjectRoot(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_PROJECT_ROOT"), propertyFile, true));
	    	this.setVcsWorkspaceHome(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_WORKSPACE_HOME"), propertyFile, true));
	    	this.setVcsIgnoreMessages(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_IGNORE_MESSAGES"), propertyFile, true));
	    	this.setVcsMessagePrepend(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_MESSAGE_PREPEND"), propertyFile, true));
	    	/* 3-7-2012: may not need 		
	    	this.setVcsMessageMandatory(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_MESSAGE_MANDATORY"), propertyFile, true));
	    	 */	    	
			//Perform substitions on on the variables <VAR>
			this.setVcsSubstitutionVars("<VCS_USERNAME>", this.getVcsUsername());
			this.setVcsSubstitutionVars("<VCS_PASSWORD>", this.getVcsPassword());
			this.setVcsRepositoryUrl(substituteVariables(getVcsRepositoryUrl(), getVcsSubstitutionVars()));

			//-------------------------------------------------------------------
			// loadVcs: Perform a cursory validation of required variables
			//-------------------------------------------------------------------	
			// Validate that the VCS_TYPE is not null
			if (this.getVcsType() == null || this.getVcsType().length() == 0) {
				throw new ValidationException("VCS_TYPE is null or empty.  VCS_TYPE must be set via the "+propertyFile+" file.");
			}
	    	//Validate the VCS_TYPE - The type of VCS being used [SVN, P4, CVS, TFS2005, TFS2010, TFS2012, TFS2013, GIT, CLC, etc]
			if (
				!this.getVcsType().equalsIgnoreCase("SVN")
				&& !this.getVcsType().equalsIgnoreCase("P4")
				&& !this.getVcsType().equalsIgnoreCase("CVS")
				&& !this.getVcsType().equalsIgnoreCase("TFS2005")
				&& !this.getVcsType().equalsIgnoreCase("TFS2010")
				&& !this.getVcsType().equalsIgnoreCase("TFS2012")
				&& !this.getVcsType().equalsIgnoreCase("TFS2013")
				&& !this.getVcsType().equalsIgnoreCase("GIT")
				&& !this.getVcsType().equalsIgnoreCase("CLC")
				) {
				throw new ValidationException("VCS_TYPE must be in the set of values [SVN, P4, CVS, TFS2005, TFS2010, TFS2012, TFS2013, GIT, CLC].  The VCS_TYPE="+this.getVcsType());							
			}
			// Validate that the VCS_PROJECT_ROOT is not null
			this.setVcsProjectRoot(CommonUtils.setCanonicalPath(this.getVcsProjectRoot()));
			if (this.getVcsProjectRoot() == null || this.getVcsProjectRoot().length() == 0) {
				throw new ValidationException("VCS_PROJECT_ROOT is null or empty.  VCS_PROJECT_ROOT must be set via the "+propertyFile+" file.");
			}

			// Set up the workspace directory
		    // Automatically this will setup the Workspace Project Folder which is derived from vcsWorkspace + "/" + vcsProjectRoot
			this.setVcsWorkspace(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_WORKSPACE_DIR"), propertyFile, true));
			if (this.getVcsWorkspace() == null || this.getVcsWorkspace().length() == 0) {
				throw new ValidationException("VCS_WORKSPACE_DIR is null or empty.  VCS_WORKSPACE_DIR must be set via the "+propertyFile+" file.");
			}
			
			this.setVcsWorkspace(CommonUtils.setCanonicalPath(this.getVcsWorkspace()));
			// Validate that the VCS_WORKSPACE is not null
			if (getVcsWorkspace() == null || getVcsWorkspace().length() == 0) {
				throw new ValidationException("VCS_WORKSPACE_DIR is null or empty.  VCS_WORKSPACE_DIR must be set via the "+propertyFile+" file.");
			}
			
			// Set up the workspace name (this variable is used by perforce to delete a workspace)
			//this.setVcsWorkspaceName(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_WORKSPACE_NAME"), propertyFile));
			int idx = this.getVcsWorkspace().lastIndexOf("/");
			if (idx < 0) {
				throw new ValidationException("VCS_WORKSPACE_NAME could not be determined from VCS_WORKSPACE_DIR.  VCS_WORKSPACE_DIR must be set via the "+propertyFile+" file in the format of [$VCS_WORKSPACE_HOME/$VCS_TYPE$_cisVcsWorkspace].");				
			}
			this.setVcsWorkspaceName((this.getVcsWorkspace().substring(idx+1)).trim());
			// Validate that the VCS_WORKSPACE_NAME is not null
			if (this.getVcsWorkspaceName() == null || this.getVcsWorkspaceName().length() == 0) {
				throw new ValidationException("VCS_WORKSPACE_NAME could not be determined from VCS_WORKSPACE_DIR.  VCS_WORKSPACE_DIR must be set via the "+propertyFile+" file in the format of [$VCS_WORKSPACE_HOME/<vcs-workspace-dir-name].");				
			}

			if (!pdToolStudio || vcsStudioInitializeBaseFolderCheckin) {
				// Set up the workspace temp directory
				this.setVcsTemp(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"VCS_TEMP_DIR"), propertyFile, true));
				this.setVcsTemp(CommonUtils.setCanonicalPath(this.getVcsTemp()));
				// Validate that the VCS_TEMP_DIR is not null
				if (this.getVcsTemp() == null || this.getVcsTemp().length() == 0) {
					throw new ValidationException("VCS_TEMP_DIR is null or empty.  VCS_TEMP_DIR must be set via the "+propertyFile+" file.");
				}
			}
			
		    // Initialize the VCS Execute Command based on VCS_EXEC_FULL_PATH being true or false.  The default is to execute VCS with full path to the VCS command.
			// Set the execute command to just the VCS Command when VCS_EXEC_FULL_PATH is false
		    if (this.getVcsExecFullPath() != null && this.getVcsExecFullPath().equalsIgnoreCase("false")) {
	    		this.setVcsExecCommand(this.getVcsCommand());		    		
		    } else {
		    	// Set the VCS command with the canonical path
				this.setVcsExecCommand(CommonUtils.setCanonicalPath(this.getVcsHome() + "/" + this.getVcsCommand()));
				
				// Setup the VCS command.  Put double quotes around a path containing spaces or a full path.
			    //   Example: "C:/Program Files/Perforce/p4.exe"
				if (this.getVcsExecCommand().contains(" ")) {
					this.setVcsExecCommand("\""+this.getVcsExecCommand()+"\"");
				} else {
					this.setVcsExecCommand(this.getVcsExecCommand());
				}		    	
		    }

		    setVcsEnvironment("");
			String envPropSep = "|";
			//-------------------------------------------------------------------
			// [CVS] loadVcs: Concurrent Versions Systems (cvs) specific settings
			//-------------------------------------------------------------------
			if (this.getVcsType().equalsIgnoreCase("CVS")) {
				// Get the VCS LifecycleListener class used by DiffMerger
				this.setVcsLifecycleListener(CommonConstants.CVSLifecycleListener);

				String propertyValue = null;
				// When the non-V2 methods are being invoked, make sure there are no Java Environment variables set prior getting the dynamic property.
				// This is to insure backward compatability with the original VCS methods
				if (!vcsV2Method) {
					propertyValue = System.clearProperty("CVS_ENV");
				}
				// Get the CVS specific environment variables
				String envVars = CommonUtils.getFileOrSystemPropertyValue(propertyFile,"CVS_ENV");
				// Remove any inadvertant $ or % signs from the variables.  This is a list of variable.  Not actual variables for substitution
				envVars = envVars.replaceAll(Matcher.quoteReplacement("$"), "");
				envVars = envVars.replaceAll(Matcher.quoteReplacement("%"), "");
				// Get the list of variables
				StringTokenizer st = new StringTokenizer(envVars,",");
				while(st.hasMoreTokens()){
					String property = st.nextToken().trim();
					// When the non-V2 methods are being invoked, make sure there are no Java Environment variables set prior getting the dynamic property.
					// This is to insure backward compatability with the original VCS methods
					if (!vcsV2Method) {
						propertyValue = System.clearProperty(property);
					}
					// Resolve the variables in the list
					propertyValue = CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,property), propertyFile, true);
					this.setVcsEnvironment(this.getVcsEnvironment() + substituteVariables(property + "=" + propertyValue, this.getVcsSubstitutionVars()) + envPropSep);
				}
				// If no options are set then set the default
				if (this.getVcsOptions() == null || this.getVcsOptions().length() == 0) {
					this.setVcsOptions("");
				}
			}
			
			//-------------------------------------------------------------------
			// [P4] loadVcs: Perforce (p4) specific settings
			//-------------------------------------------------------------------
			if (this.getVcsType().equalsIgnoreCase("P4")) {
				// Get the VCS LifecycleListener class used by DiffMerger
				this.setVcsLifecycleListener(CommonConstants.P4LifecycleListener);

				String propertyValue = null;
				// When the non-V2 methods are being invoked, make sure there are no Java Environment variables set prior getting the dynamic property.
				// This is to insure backward compatability with the original VCS methods
				if (!vcsV2Method) {
					propertyValue = System.clearProperty("P4_ENV");
				}
				// Get the Perforce specific environment variables
				String envVars = CommonUtils.getFileOrSystemPropertyValue(propertyFile,"P4_ENV");
				// Remove any inadvertant $ or % signs from the variables.  This is a list of variable.  Not actual variables for substitution
				envVars = envVars.replaceAll(Matcher.quoteReplacement("$"), "");
				envVars = envVars.replaceAll(Matcher.quoteReplacement("%"), "");
				// Get the list of variables
				StringTokenizer st = new StringTokenizer(envVars,",");
				while(st.hasMoreTokens()){
					String property = st.nextToken().trim();
					// When the non-V2 methods are being invoked, make sure there are no Java Environment variables set prior getting the dynamic property.
					// This is to insure backward compatability with the original VCS methods
					if (!vcsV2Method) {
						propertyValue = System.clearProperty(property);
					}
					// Resolve the variables in the list
					propertyValue = CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,property), propertyFile, true);
					this.setVcsEnvironment(this.getVcsEnvironment() + substituteVariables(property + "=" + propertyValue, this.getVcsSubstitutionVars()) + envPropSep);
				}
				// If no options are set then set the default
				if (this.getVcsOptions() == null || this.getVcsOptions().length() == 0) {
					this.setVcsOptions("");
				}
			}
			
			//-------------------------------------------------------------------
			// [SVN] loadVcs: Subversion (svn) specific settings
			//-------------------------------------------------------------------
			if (this.getVcsType().equalsIgnoreCase("SVN")) {
				// Get the VCS LifecycleListener class used by DiffMerger
				this.setVcsLifecycleListener(CommonConstants.SVNLifecycleListener);
				
				String propertyValue = null;
				// When the non-V2 methods are being invoked, make sure there are no Java Environment variables set prior getting the dynamic property.
				// This is to insure backward compatability with the original VCS methods
				if (!vcsV2Method) {
					propertyValue = System.clearProperty("SVN_ENV");
				}
				// Get the Subversion specific environment variables
				String envVars = CommonUtils.getFileOrSystemPropertyValue(propertyFile,"SVN_ENV");
				// Remove any inadvertant $ or % signs from the variables.  This is a list of variable.  Not actual variables for substitution
				envVars = envVars.replaceAll(Matcher.quoteReplacement("$"), "");
				envVars = envVars.replaceAll(Matcher.quoteReplacement("%"), "");
				// Get the list of variables
				StringTokenizer st = new StringTokenizer(envVars,","); 
				while(st.hasMoreTokens()){
					String property = st.nextToken().trim();
					// When the non-V2 methods are being invoked, make sure there are no Java Environment variables set prior getting the dynamic property.
					// This is to insure backward compatability with the original VCS methods
					if (!vcsV2Method) {
						propertyValue = System.clearProperty(property);
					}
					// Resolve the variables in the list
					propertyValue = CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,property), propertyFile, true);
					this.setVcsEnvironment(this.getVcsEnvironment() + substituteVariables(property + "=" + propertyValue, this.getVcsSubstitutionVars()) + envPropSep);
				}

				// mtinius: 5/24/2012 - this can be set by the user in Options
				/*
				// If no options are set then set the default
				if (this.getVcsOptions() == null || this.getVcsOptions().length() == 0) {
					this.setVcsOptions("--non-interactive");
				}
				*/
				if (getVcsUsername() != null && getVcsUsername().length() > 0) {
					this.setVcsOptions(this.getVcsOptions() + " --username "+this.getVcsUsername());					
				}
				if (getVcsPassword() != null && getVcsPassword().length() > 0) {
					this.setVcsOptions(this.getVcsOptions() + " --password "+this.getVcsPassword());			
				}
			}
			
			//-------------------------------------------------------------------
			// [TFS] loadVcs: Team Foundation Server (tfs) specific settings
			//-------------------------------------------------------------------
			if (this.getVcsType().equalsIgnoreCase("TFS2005") || 
				this.getVcsType().equalsIgnoreCase("TFS2010") ||
				this.getVcsType().equalsIgnoreCase("TFS2012") ||
				this.getVcsType().equalsIgnoreCase("TFS2013")) 
			{
				// Get the VCS LifecycleListener class used by DiffMerger
				this.setVcsLifecycleListener(CommonConstants.TFSLifecycleListener);
				
				String propertyValue = null;
				// When the non-V2 methods are being invoked, make sure there are no Java Environment variables set prior getting the dynamic property.
				// This is to insure backward compatability with the original VCS methods
				if (!vcsV2Method) {
					propertyValue = System.clearProperty("TFS_ENV");
				}
				// Get the Subversion specific environment variables
				String envVars = CommonUtils.getFileOrSystemPropertyValue(propertyFile,"TFS_ENV");
				// Remove any inadvertant $ or % signs from the variables.  This is a list of variable.  Not actual variables for substitution
				envVars = envVars.replaceAll(Matcher.quoteReplacement("$"), "");
				envVars = envVars.replaceAll(Matcher.quoteReplacement("%"), "");
				// Get the list of variables
				StringTokenizer st = new StringTokenizer(envVars,","); 
				while(st.hasMoreTokens()){
					String property = st.nextToken().trim();
					// When the non-V2 methods are being invoked, make sure there are no Java Environment variables set prior getting the dynamic property.
					// This is to insure backward compatability with the original VCS methods
					if (!vcsV2Method) {
						propertyValue = System.clearProperty(property);
					}
					// Resolve the variables in the list
					propertyValue = CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,property), propertyFile, true);
					this.setVcsEnvironment(this.getVcsEnvironment() + substituteVariables(property + "=" + propertyValue, this.getVcsSubstitutionVars()) + envPropSep);
				}

				// If no options are set then set the default
				if (this.getVcsOptions() == null || this.getVcsOptions().length() == 0) {
					this.setVcsOptions("");
				}
				// login is used to specify the user name and password to authenticate the user with the Team Foundation Server. 
				// The usage is /login:username[,password]. 
				// If /login:username is supplied but /noprompt is not, the user is prompted for the password. 
				// If /login:username,password is specified, Team Foundation Server uses the supplied parameters.

				String separator = CommonUtils.getFileOrSystemPropertyValue(propertyFile,"FILE_SYSTEM_SEPARATOR");
				// Default the login switch to Windows style login
				String tfsLoginSwitch = "/login:";
				if (separator.equalsIgnoreCase("/") || separator.equalsIgnoreCase("//")) {
					// Set the loging switch to UNIX style login
					tfsLoginSwitch = "-login:";	
				}
				
				if (getVcsUsername() != null && getVcsUsername().length() > 0) {
					this.setVcsOptions(this.getVcsOptions() + " " + tfsLoginSwitch+this.getVcsUsername());					
				}
				if (getVcsPassword() != null && getVcsPassword().length() > 0) {
					this.setVcsOptions(this.getVcsOptions() + ","+this.getVcsPassword());			
				}
				// We cannot use VCS_OPTIONS for this because checkin option like /override gives errors when used on other TFS commands
				// TFS_CHECKIN_OPTIONS has been deprecated.  Still supported but not used.  The primary capability is VCS_CHECKIN_OPTIONS which is generic for all VCS.
				// this.setTfsCheckinOptions(CommonUtils.getFileOrSystemPropertyValue(propertyFile,"TFS_CHECKIN_OPTIONS"));
				String TfsCheckinOptions = CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"TFS_CHECKIN_OPTIONS"), propertyFile, true);
				if (TfsCheckinOptions == null) 
					TfsCheckinOptions = "";
				String vcsCheckinOptions = this.getVcsCheckinOptions();
				if (vcsCheckinOptions == null) 
					vcsCheckinOptions = "";
				if (vcsCheckinOptions.equalsIgnoreCase(TfsCheckinOptions)) 
					this.setVcsCheckinOptions(vcsCheckinOptions);
				else
					this.setVcsCheckinOptions(vcsCheckinOptions + " " + TfsCheckinOptions);
				
				this.setTfsServerUrl(CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,"TFS_SERVER_URL"), propertyFile, true));
				if (this.getTfsServerUrl() == null || this.getTfsServerUrl().equals("")) {
					this.setTfsServerUrl("$");
				}
				
				//Set the Use Existing Workspace variable - TFS_USE_EXISTING_WORKSPACE
				String useExistingWorkspace = CommonUtils.getFileOrSystemPropertyValue(propertyFile,"TFS_USE_EXISTING_WORKSPACE").toLowerCase();
				if (useExistingWorkspace.equals("true") || useExistingWorkspace.equals("false"))
					this.setVcsUseExistingWorkdspace(useExistingWorkspace);
			}
						
			//-------------------------------------------------------------------
			// loadVcs: GitHub (git) specific settings
			//-------------------------------------------------------------------
			if (this.getVcsType().equalsIgnoreCase("GIT")) {
				
				// Get the VCS LifecycleListener class used by DiffMerger
				this.setVcsLifecycleListener(CommonConstants.GITLifecycleListener);
				
				String propertyValue = null;
				// When the non-V2 methods are being invoked, make sure there are no Java Environment variables set prior getting the dynamic property.
				// This is to insure backward compatability with the original VCS methods
				if (!vcsV2Method) {
					propertyValue = System.clearProperty("GIT_ENV");
				}
				// Get the Subversion specific environment variables
				String envVars = CommonUtils.getFileOrSystemPropertyValue(propertyFile,"GIT_ENV");
				// Remove any inadvertant $ or % signs from the variables.  This is a list of variable.  Not actual variables for substitution
				envVars = envVars.replaceAll(Matcher.quoteReplacement("$"), "");
				envVars = envVars.replaceAll(Matcher.quoteReplacement("%"), "");
				// Get the list of variables
				StringTokenizer st = new StringTokenizer(envVars,","); 
				while(st.hasMoreTokens()){
					String property = st.nextToken().trim();
					// When the non-V2 methods are being invoked, make sure there are no Java Environment variables set prior getting the dynamic property.
					// This is to insure backward compatability with the original VCS methods
					if (!vcsV2Method) {
						propertyValue = System.clearProperty(property);
					}
					// Resolve the variables in the list
					propertyValue = CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,property), propertyFile, true);
					this.setVcsEnvironment(this.getVcsEnvironment() + substituteVariables(property + "=" + propertyValue, this.getVcsSubstitutionVars()) + envPropSep);
				}

				// Set the username/password authorization
				/* 
				 * Git uses various methods to authenticate with the GitHub server to initialize the workspace. However, the user/pass 
				 * is then embedded in the workspace, so no username/password options are available
				 * 
				if (getVcsUsername() != null && getVcsUsername().length() > 0) {
					this.setVcsOptions(this.getVcsOptions() + " --username "+this.getVcsUsername());					
				}
				if (getVcsPassword() != null && getVcsPassword().length() > 0) {
					this.setVcsOptions(this.getVcsOptions() + " --password "+this.getVcsPassword());			
				}
				*/
			}
			
			//-------------------------------------------------------------------
			// [CLC] loadVcs: Clearcase (clc) specific settings
			//-------------------------------------------------------------------
			if (this.getVcsType().equalsIgnoreCase("CLC")) {
				// Get the VCS LifecycleListener class used by DiffMerger
				this.setVcsLifecycleListener(CommonConstants.CLCLifecycleListener);
				
				String propertyValue = null;
				// When the non-V2 methods are being invoked, make sure there are no Java Environment variables set prior getting the dynamic property.
				// This is to insure backward compatability with the original VCS methods
				if (!vcsV2Method) {
					propertyValue = System.clearProperty("CLC_ENV");
				}
				// Get the Subversion specific environment variables
				String envVars = CommonUtils.getFileOrSystemPropertyValue(propertyFile,"CLC_ENV");
				// Remove any inadvertant $ or % signs from the variables.  This is a list of variable.  Not actual variables for substitution
				envVars = envVars.replaceAll(Matcher.quoteReplacement("$"), "");
				envVars = envVars.replaceAll(Matcher.quoteReplacement("%"), "");
				// Get the list of variables
				StringTokenizer st = new StringTokenizer(envVars,","); 
				while(st.hasMoreTokens()){
					String property = st.nextToken().trim();
					// When the non-V2 methods are being invoked, make sure there are no Java Environment variables set prior getting the dynamic property.
					// This is to insure backward compatability with the original VCS methods
					if (!vcsV2Method) {
						propertyValue = System.clearProperty(property);
					}
					// Resolve the variables in the list
					propertyValue = CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,property), propertyFile, true);
					this.setVcsEnvironment(this.getVcsEnvironment() + substituteVariables(property + "=" + propertyValue, this.getVcsSubstitutionVars()) + envPropSep);
				}
				// If no options are set then set the default
				if (this.getVcsOptions() == null || this.getVcsOptions().length() == 0) {
					this.setVcsOptions("");
				}

				if (getVcsUsername() != null && getVcsUsername().length() > 0) {
					this.setVcsOptions(this.getVcsOptions() + " --username "+this.getVcsUsername());					
				}
				if (getVcsPassword() != null && getVcsPassword().length() > 0) {
					this.setVcsOptions(this.getVcsOptions() + " --password "+this.getVcsPassword());			
				}
			}

			/*
			 * This section is a template.  Copy this template for the new VCS and modify it as needed.
			 * 
			 * 	Modify the following lines
			 * 		if (this.getVcsType().equalsIgnoreCase("NEW_VCS_TYPE")) {
			 * 		this.setVcsLifecycleListener("com.compositesw.cmdline.vcs.spi.abc.ABCLifecycleListener");
			 * 		propertyValue = System.clearProperty("ABC_ENV");
			 * 		String envVars = CommonUtils.getFileOrSystemPropertyValue(propertyFile,"ABC_ENV");
			 * 		this.setVcsOptions(this.getVcsOptions() + " --username "+this.getVcsUsername());
			 * 		this.setVcsOptions(this.getVcsOptions() + " --password "+this.getVcsPassword());
			 * 	
			 * Add any new lines that are specific to environment variable parsing if needed.		
			 */
			//-------------------------------------------------------------------
			// loadVcs: New VCS Type (abc) specific settings
			//-------------------------------------------------------------------
			if (this.getVcsType().equalsIgnoreCase("NEW_VCS_TYPE")) {
				
				// Get the VCS LifecycleListener class used by DiffMerger
				this.setVcsLifecycleListener(CommonConstants.NEWLifecycleListener);
				
				String propertyValue = null;
				// When the non-V2 methods are being invoked, make sure there are no Java Environment variables set prior getting the dynamic property.
				// This is to insure backward compatability with the original VCS methods
				if (!vcsV2Method) {
					propertyValue = System.clearProperty("ABC_ENV");
				}
				// Get the Subversion specific environment variables
				String envVars = CommonUtils.getFileOrSystemPropertyValue(propertyFile,"ABC_ENV");
				// Remove any inadvertant $ or % signs from the variables.  This is a list of variable.  Not actual variables for substitution
				envVars = envVars.replaceAll(Matcher.quoteReplacement("$"), "");
				envVars = envVars.replaceAll(Matcher.quoteReplacement("%"), "");
				// Get the list of variables
				StringTokenizer st = new StringTokenizer(envVars,","); 
				while(st.hasMoreTokens()){
					String property = st.nextToken().trim();
					// When the non-V2 methods are being invoked, make sure there are no Java Environment variables set prior getting the dynamic property.
					// This is to insure backward compatability with the original VCS methods
					if (!vcsV2Method) {
						propertyValue = System.clearProperty(property);
					}
					// Resolve the variables in the list
					propertyValue = CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,property), propertyFile, true);
					this.setVcsEnvironment(this.getVcsEnvironment() + substituteVariables(property + "=" + propertyValue, this.getVcsSubstitutionVars()) + envPropSep);
				}

				// Set the username/password authorization
				if (getVcsUsername() != null && getVcsUsername().length() > 0) {
					this.setVcsOptions(this.getVcsOptions() + " --username "+this.getVcsUsername());					
				}
				if (getVcsPassword() != null && getVcsPassword().length() > 0) {
					this.setVcsOptions(this.getVcsOptions() + " --password "+this.getVcsPassword());			
				}
			}
			
			
			// Remove the final environment property separator at the end of the line if it exists
			if (this.getVcsEnvironment() != null && this.getVcsEnvironment().length() > 0) {
				if (this.getVcsEnvironment().lastIndexOf(envPropSep) == this.getVcsEnvironment().length()-1) {
					this.setVcsEnvironment(this.getVcsEnvironment().substring(0,this.getVcsEnvironment().lastIndexOf(envPropSep)));
				}
			}
			

	    }

		// Display the VCS variables (property file properties + derived properties)
	    private void displayVcs(String prefix) {
			/*****************************************
			 * PRINT OUT VCS VARIABLES
			 *****************************************/
	    	// Extract out the custom Environment variables that are separated by "|".
	    	// Determine if any individual name=value pair contains a password and mask
	    	// Put the environment string back together with masked values for display
			List<String> envList = new ArrayList<String>();
			envList = CommonUtils.getArgumentsList(envList, true, this.getVcsEnvironment(), "|");
			String env = "";
			if (envList != null) {
				for (int i=0; i < envList.size(); i++ ) {
					if (env.length() != 0) {
						env = env + "|"; 
					}
					env = env + CommonUtils.maskCommand(envList.get(i).toString());			
				}
			}

			// Print out Debug input parameters
			CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---VCS Input Variables from "+propertyFile+" properties file: ",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_TYPE=                       "+this.getVcsType(),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_BASE_TYPE=                  "+this.getVcsBaseType(),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_HOME=                       "+this.getVcsHome(),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_COMMAND=                    "+this.getVcsCommand(),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_EXEC_FULL_PATH=             "+this.getVcsExecFullPath(),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_OPTIONS=                    "+CommonUtils.maskCommand(this.getVcsOptions()),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_WORKSPACE_INIT_NEW_OPTIONS= "+CommonUtils.maskCommand(this.getVcsWorkspaceInitNewOptions()),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_WORKSPACE_INIT_LINK_OPTIONS="+CommonUtils.maskCommand(this.getVcsWorkspaceInitLinkOptions()),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_WORKSPACE_INIT_GET_OPTIONS= "+CommonUtils.maskCommand(this.getVcsWorkspaceInitGetOptions()),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_BASE_FOLDER_INIT_ADD=       "+CommonUtils.maskCommand(this.getVcsBaseFolderInitAddOptions()),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_CHECKIN_OPTIONS=            "+CommonUtils.maskCommand(this.getVcsCheckinOptions()),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_CHECKIN_OPTIONS_REQUIRED=   "+CommonUtils.maskCommand(this.getVcsCheckinOptionsRequired()),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_CHECKOUT_OPTIONS=           "+CommonUtils.maskCommand(this.getVcsCheckoutOptions()),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_CHECKOUT_OPTIONS_REQUIRED=  "+CommonUtils.maskCommand(this.getVcsCheckoutOptionsRequired()),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_CIS_IMPORT_OPTIONS=         "+CommonUtils.maskCommand(this.getVcsCisImportOptions()),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_CIS_EXPORT_OPTIONS=         "+CommonUtils.maskCommand(this.getVcsCisExportOptions()),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_USER=                       "+this.getVcsUsername(),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_PASSWORD=                   ********",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_REPOSITORY_URL=             "+CommonUtils.maskCommand(this.getVcsRepositoryUrl()),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_PROJECT_ROOT=               "+this.getVcsProjectRoot(),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_WORKSPACE_HOME=             "+this.getVcsWorkspaceHome(),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_IGNORE_MESSAGES=            "+this.getVcsIgnoreMessages(),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_MESSAGE_PREPEND=            "+this.getVcsMessagePrepend(),prefix,"-debug2",logger,debug1,debug2,debug3);
			/* 3-7-2012: may not need 		
			CommonUtils.writeOutput("      VCS_MESSAGE_MANDATORY=  "+this.getVcsMessageMandatory(),prefix,"-debug2",logger,debug1,debug2,debug3);
			*/
			CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("---VCS Derived Variables:             ",prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_EXEC_COMMAND=               "+this.getVcsExecCommand(),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS Environment=                "+env,prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_WORKSPACE_DIR=              "+this.getVcsWorkspace(),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_WORKSPACE_NAME=             "+this.getVcsWorkspaceName(),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_WORKSPACE_PROJECT=          "+this.getVcsWorkspaceProject(),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS_TEMP_DIR=                   "+this.getVcsTemp(),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("      VCS LifecycleListener=          "+this.getVcsLifecycleListener(),prefix,"-debug2",logger,debug1,debug2,debug3);
			CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
			
			//Define the VCS Custom Environment Property Type
			if (this.getVcsType() != null && this.getVcsType().length() > 0) {
				String vcsEnvPropertyType = this.getVcsType() + "_ENV";
				CommonUtils.writeOutput("---VCS Custom Variables:             ",prefix,"-debug2",logger,debug1,debug2,debug3);
				displayVcsCustomEnvVars(prefix, vcsEnvPropertyType);
				CommonUtils.writeOutput("",prefix,"-debug2",logger,debug1,debug2,debug3);
			}
	    }

		// Display the custom VCS environment property variables: TFS_ENV, SVN_ENV, GIT_ENV, CVS_ENV, P4_ENV
	    private void displayVcsCustomEnvVars(String prefix, String customEnvProperty) {
			// When the non-V2 methods are being invoked, make sure there are no Java Environment variables set prior getting the dynamic property.
			// This is to insure backward compatability with the original VCS methods
	    	String propertyValue = null;
			if (!vcsV2Method) {
				propertyValue = System.clearProperty(customEnvProperty);
			}
			// Get the VCS specific environment variables
			String envVars = CommonUtils.getFileOrSystemPropertyValue(propertyFile,customEnvProperty);
			// Remove any inadvertant $ or % signs from the variables.  This is a list of variable.  Not actual variables for substitution
			envVars = envVars.replaceAll(Matcher.quoteReplacement("$"), "");
			envVars = envVars.replaceAll(Matcher.quoteReplacement("%"), "");
			// Get the list of variables
			StringTokenizer st = new StringTokenizer(envVars,","); 
			while(st.hasMoreTokens()){
				String property = st.nextToken().trim();
				// When the non-V2 methods are being invoked, make sure there are no Java Environment variables set prior getting the dynamic property.
				// This is to insure backward compatability with the original VCS methods
				if (!vcsV2Method) {
					propertyValue = System.clearProperty(property);
				}
				// Resolve the variables in the list
				propertyValue = CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(propertyFile,property), propertyFile, true);
				CommonUtils.writeOutput("      "+property+"=          "+propertyValue,prefix,"-debug2",logger,debug1,debug2,debug3);
			}
	
	    }
	    
	    
		// Validate the VCS variables (property file + derived properties)
	    private void validateVcs(String prefix) {
			/*****************************************
			 * VALIDATE VCS VARIABLES
			 *****************************************/
			// Validate that the VCS_TYPE is not null
			if (this.getVcsType() == null || this.getVcsType().length() == 0) {
				throw new ValidationException("VCS_TYPE is null or empty.  VCS_TYPE must be set via the "+propertyFile+" file.");
			}
			// Validate that the VCS_HOME is not null
			if (this.getVcsHome() == null || this.getVcsHome().length() == 0) {
				throw new ValidationException("VCS_HOME is null or empty.  VCS_HOME must be set via the "+propertyFile+" file.");
			}
			// Validate that the VCS_COMMAND is not null
			if (this.getVcsCommand() == null || this.getVcsCommand().length() == 0) {
				throw new ValidationException("VCS_COMMAND is null or empty.  VCS_COMMAND must be set via the "+propertyFile+" file.");
			}
			/* mtinius: 5/21/2012 - remove password check to allow the system to use cached users/passwords
			// Validate that the VCS user is not null
			if (this.getVcsUsername() == null || this.getVcsUsername().length() == 0) {
				throw new ValidationException("VCS User is null or empty.  VCS User must be set via command line or "+propertyFile+" file.");
			}
			// Validate that the VCS password is not null
			if (this.getVcsPassword() == null || this.getVcsPassword().length() == 0) {
				throw new ValidationException("VCS Password is null or empty.  VCS Password must be set via command line or "+propertyFile+" file.");
			}
			*/
			// Validate that the VCS_REPOSITORY_URL is not null
			if (this.getVcsRepositoryUrl() == null || this.getVcsRepositoryUrl().length() == 0) {
				throw new ValidationException("VCS_REPOSITORY_URL is null or empty.  VCS_REPOSITORY_URL must be set via the "+propertyFile+" file.");
			}
			// Validate that the VCS_PROJECT_ROOT is not null
			if (this.getVcsProjectRoot() == null || this.getVcsProjectRoot().length() == 0) {
				throw new ValidationException("VCS_PROJECT_ROOT is null or empty.  VCS_PROJECT_ROOT must be set via the "+propertyFile+" file.");
			}
			// Validate that the VCS_WORKSPACE_HOME is not null
			if (this.getVcsWorkspaceHome() == null || this.getVcsWorkspaceHome().length() == 0) {
				throw new ValidationException("VCS_WORKSPACE_HOME is null or empty.  VCS_WORKSPACE_HOME must be set via the "+propertyFile+" file.");
			}
			// Validate that the VCS_WORKSPACE_NAME is not null
			if (this.getVcsWorkspaceName() == null || this.getVcsWorkspaceName().length() == 0) {
				throw new ValidationException("VCS_WORKSPACE_NAME is null or empty.  VCS_WORKSPACE_NAME must be set via the "+propertyFile+" file.");
			}			
			// Validate that the VCS_WORKSPACE_DIR is not null
			if (this.getVcsWorkspace() == null || this.getVcsWorkspace().length() == 0) {
				throw new ValidationException("VCS_WORKSPACE_DIR is null or empty.  VCS_WORKSPACE_DIR must be set via the "+propertyFile+" file.");
			}
			if (!pdToolStudio) {
				// Validate that the VCS_TEMP_DIR is not null
				if (this.getVcsTemp() == null || this.getVcsTemp().length() == 0) {
					throw new ValidationException("VCS_TEMP_DIR is null or empty.  VCS_TEMP_DIR must be set via the "+propertyFile+" file.");
				}
			}
			
			// Validate that PROJECT_HOME and VCS_WORKSPACE_DIR are not the same
			if ( this.getProjectHome().equalsIgnoreCase(this.getVcsWorkspace()) ) {
				throw new ValidationException("VCS_WORKSPACE_DIR may not be set to the location of PROJECT_HOME.  Please evaluate the settings for VCS_WORKSPACE_DIR="+this.getVcsWorkspace()+ "  PROJECT_HOME is set by default by the Execute script.  PROJECT_HOME="+this.getProjectHome());
			} 

			// Validate that PROJECT_HOME and VCS_TEMP are not the same
			if ( this.getProjectHome().equalsIgnoreCase(this.getVcsTemp()) ) {
				throw new ValidationException("VCS_TEMP may not be set to the location of PROJECT_HOME.  Please evaluate the settings for VCS_TEMP="+this.getVcsTemp()+ "  PROJECT_HOME is set by default by the Execute script.  PROJECT_HOME="+this.getProjectHome());
			} 
			
			// Validate that VCS_HOME exists
			if (this.getVcsHome() != null && !CommonUtils.fileExists(this.getVcsHome())) {
				throw new ValidationException("VCS_HOME Directory ["+this.getVcsHome()+"] does not exist.");
			} 
			// Validate that the VCS Workspace exists and create it if it does not
			if (!CommonUtils.fileExists(this.getVcsWorkspace())) {
				createDirectory(prefix, this.getVcsWorkspace());
			}
			if (!pdToolStudio) {
				// Validate that the VCS Workspace Temp exists and create it if it does not
				if (!CommonUtils.fileExists(this.getVcsTemp())) {
					createDirectory(prefix, this.getVcsTemp());
				}
			}
			
			if (!pdToolStudio) {
				// Validate that the VCS Workspace is not equal to the VCS Temp directory
				if (this.getVcsWorkspace().equalsIgnoreCase(this.getVcsTemp())) {
					throw new ValidationException("The VCS_WORKSPACE_DIR Directory ["+this.getVcsWorkspace()+"] cannot be the same as the VCS_TEMP_DIR Directory ["+this.getVcsTemp()+"].");
				}
			}

	    }
	
		/* Resolve the canonical paths for various VCS variables (property file + derived properties)
		 * For example in UNIX if there are paths point to another physical location as shown below, the getCanonicalPath() will resolve this
		 * lrwxrwxrwx  1 root root   28 Jul 18 13:10 Composite_Software -> /u01/opt/Composite_Software/
		 * lrwxrwxrwx  1 root root   21 Jul 18 18:30 Deploy Tool -> /u01/opt/Deploy Tool/
		 * 
		 * It is reccommended to invoke this after validateVCS so that the initial path validation is completed first.
	     */
	    private void resolveCanonicalPathsVcs(String prefix) throws CompositeException {

			try {
				File absFile = new File(this.getVcsWorkspace());
				this.setVcsWorkspace(CommonUtils.setCanonicalPath(absFile.getCanonicalPath()));
				
				if (!pdToolStudio) {
					absFile = new File(this.getVcsTemp());
					this.setVcsTemp(CommonUtils.setCanonicalPath(absFile.getCanonicalPath()));
				}
				
				absFile = new File(this.getVcsWorkspaceProject());
				this.setVcsWorkspaceProject(CommonUtils.setCanonicalPath(absFile.getCanonicalPath()));			
				
			} catch (IOException e) {
				throw new ValidationException(e);
			}
	    }

	    // Set/Get systemPath
	    private void setSystemPath(String s) {
	    	this.systemPath = s;
	    }
	    private String getSystemPath() {
	    	return this.systemPath;
	    }
    
	    // Set/Get projectHome
	    private void setProjectHome(String s) {
	    	this.projectHome = CommonUtils.setCanonicalPath(s);
	    }
	    private String getProjectHome() {
	    	return this.projectHome;
	    }
    
	    // Set/Get vcsType
	    private void setVcsType(String s) {
	    	this.vcsType = s;
	    }
	    private String getVcsType() {
	    	return this.vcsType;
	    }
	    // Set/Get vcsBaseType
	    private void setVcsBaseType(String s) {
	    	this.vcsBaseType = s;
	    }
	    private String getVcsBaseType() {
	    	return this.vcsBaseType;
	    }
	    
	    // Set/Get vcsHome
	    private void setVcsHome(String s) {
	    	if (s.startsWith("VCS_HOME=")) {
	    		s = s.substring("VCS_HOME=".length());
	    	}
	    	this.vcsHome = s;
	    }
	    private String getVcsHome() {
	    	return this.vcsHome;
	    }
	    // Set/Get vcsExecFullPath
	    private void setVcsExecFullPath(String s) {
	    	this.vcsExecFullPath = s;
	    }
	    private String getVcsExecFullPath() {
	    	return this.vcsExecFullPath;
	    }
	    // Set/Get vcsCommand
	    private void setVcsCommand(String s) {
	    	this.vcsCommand = s;
	    }
	    private String getVcsCommand() {
	    	return this.vcsCommand;
	    }
	    // Set/Get vcsOptions
	    private void setVcsOptions(String s) {
	    	this.vcsOptions = s;
	    }
	    private String getVcsOptions() {
	    	return this.vcsOptions;
	    }
	    
	    // Set/Get vcsWorkspaceInitNewOptions
	    private void setVcsWorkspaceInitNewOptions(String s) {
	    	this.vcsWorkspaceInitNewOptions = s;
	    }
	    private String getVcsWorkspaceInitNewOptions() {
	    	return this.vcsWorkspaceInitNewOptions;
	    }
	    // Set/Get vcsWorkspaceInitLinkOptions
	    private void setVcsWorkspaceInitLinkOptions(String s) {
	    	this.vcsWorkspaceInitLinkOptions = s;
	    }
	    private String getVcsWorkspaceInitLinkOptions() {
	    	return this.vcsWorkspaceInitLinkOptions;
	    }
	    // Set/Get vcsWorkspaceInitGetOptions
	    private void setVcsWorkspaceInitGetOptions(String s) {
	    	this.vcsWorkspaceInitGetOptions = s;
	    }
	    private String getVcsWorkspaceInitGetOptions() {
	    	return this.vcsWorkspaceInitGetOptions;
	    }
	    // Set/Get vcsBaseFolderInitAddOptions
	    private void setVcsBaseFolderInitAddOptions(String s) {
	    	this.vcsBaseFolderInitAddOptions = s;
	    }
	    private String getVcsBaseFolderInitAddOptions() {
	    	return this.vcsBaseFolderInitAddOptions;
	    }
	    // Set/Get vcsCheckinOptions
	    private void setVcsCheckinOptions(String s) {
	    	this.vcsCheckinOptions = s;
	    }
	    private String getVcsCheckinOptions() {
	    	return this.vcsCheckinOptions;
	    }
	    // Set/Get vcsCheckinOptionsRequired
	    private void setVcsCheckinOptionsRequired(String s) {
	    	this.vcsCheckinOptionsRequired = s;
	    }
	    private String getVcsCheckinOptionsRequired() {
	    	return this.vcsCheckinOptionsRequired;
	    }
	    // Set/Get vcsCheckoutOptions
	    private void setVcsCheckoutOptions(String s) {
	    	this.vcsCheckoutOptions = s;
	    }
	    private String getVcsCheckoutOptions() {
	    	return this.vcsCheckoutOptions;
	    }
	    // Set/Get vcsCheckoutOptionsRequired
	    private void setVcsCheckoutOptionsRequired(String s) {
	    	this.vcsCheckoutOptionsRequired = s;
	    }
	    private String getVcsCheckoutOptionsRequired() {
	    	return this.vcsCheckoutOptionsRequired;
	    }

	    // Set/Get vcsCisImportOptions
	    private void setVcsCisImportOptions(String s) {
	    	this.vcsCisImportOptions = s;
	    }
	    private String getVcsCisImportOptions() {
	    	return this.vcsCisImportOptions;
	    }
	    // Set/Get vcsCisExportOptions
	    private void setVcsCisExportOptions(String s) {
	    	this.vcsCisExportOptions = s;
	    }
	    private String getVcsCisExportOptions() {
	    	return this.vcsCisExportOptions;
	    }

	    
	    // Set/Get vcsRepositoryUrl
	    private void setVcsRepositoryUrl(String s) {
	    	this.vcsRepositoryUrl = s;
	    }
	    private String getVcsRepositoryUrl() {
	    	return this.vcsRepositoryUrl;
	    }
	    // Set/Get vcsWorkspaceHome
	    private void setVcsWorkspaceHome(String s) {
	    	this.vcsWorkspaceHome = s;
	    }
	    private String getVcsWorkspaceHome() {
	    	return this.vcsWorkspaceHome;
	    }
	    // Set/Get vcsProjectRoot
	    private void setVcsProjectRoot(String s) {
	    	this.vcsProjectRoot = s;
	    }
	    private String getVcsProjectRoot() {
	    	return this.vcsProjectRoot;
	    }
	    // Set/Get vcsUsername
	    private void setVcsUsername(String s) {
	    	this.vcsUsername = s;
	    }
	    private String getVcsUsername() {
	    	return this.vcsUsername;
	    }
	    // Set/Get vcsPassword
	    private void setVcsPassword(String s) {
	    	this.vcsPassword = s;
	    }  
	    private String getVcsPassword() {
	    	return this.vcsPassword;
	    }
	    // Set/Get vcsIgnoreMessage
	    private void setVcsIgnoreMessages(String s) {
	    	this.vcsIgnoreMessages = s;
	    }  
	    private String getVcsIgnoreMessages() {
	    	return this.vcsIgnoreMessages;
	    }
	    // Set/Get vcsMessagePrepend
	    private void setVcsMessagePrepend(String s) {
	    	this.vcsMessagePrepend = s;
	    }  
	    private String getVcsMessagePrepend() {
	    	return this.vcsMessagePrepend;
	    }
	    /* 3-7-2012: may not need 		
	    // Set/Get vcsMessageMandatory
	    private void setVcsMessageMandatory(String s) {
	    	this.vcsMessageMandatory = s;
	    }  
	    private String getVcsMessageMandatory() {
	    	return this.vcsMessageMandatory;
	    } 
	    */
	    // Set/Get vcsEnvironment
	    private void setVcsEnvironment(String s) {
	    	this.vcsEnvironment = s;
	    }  
	    private String getVcsEnvironment() {
	    	return this.vcsEnvironment;
	    }
	    // Set/Get vcsExecCommand
	    private void setVcsExecCommand(String s) {
	    	this.vcsExecCommand = s;
	    }  
	    private String getVcsExecCommand() {
	    	return this.vcsExecCommand;
	    }
	    // Set/Get vcsTemp
	    private void setVcsTemp(String s) {
	    	this.vcsTemp = s;
	    }
	    private String getVcsTemp() {
	    	return this.vcsTemp;
	    }
	    // Set/Get vcsWorkspace
	    private void setVcsWorkspace(String s) {
	    	this.vcsWorkspace = s;
		    //Folder where the VCS Project Root begins is an offset to VCS Workspace
	    	// e.g: vcsWorkspaceProject:  D:/PDTool/svn_workspace/cis_objects
			this.setVcsWorkspaceProject((CommonUtils.setCanonicalPath(this.getVcsWorkspace() + "/" + this.getVcsProjectRoot())).replaceAll("//", "/"));
	    }
	    private String getVcsWorkspace() {
	    	return this.vcsWorkspace;
	    }
	    // Set/Get vcsWorkspaceProject
	    private void setVcsWorkspaceProject(String s) {
	    	this.vcsWorkspaceProject = s;
	    }
	    private String getVcsWorkspaceProject() {
	    	return this.vcsWorkspaceProject;
	    }
	    // Set/Get vcsWorkspaceName
	    private void setVcsWorkspaceName(String s) {
	    	this.vcsWorkspaceName = s;
	    }
	    private String getVcsWorkspaceName() {
	    	return this.vcsWorkspaceName;
	    }
	    // Set/Get vcsLifecycleListener
	    private void setVcsLifecycleListener(String s) {
	    	this.vcsLifecycleListener = s;
	    }  
	    private String getVcsLifecycleListener() {
	    	return this.vcsLifecycleListener;
	    }
	    // Set/Get vcsSubstitutionVars
	    private void setVcsSubstitutionVars(String prop, String val) {
	    	this.vcsSubstitutionVars.put(prop, val);
	    }  
	    private HashMap<String, String> getVcsSubstitutionVars() {
	    	return this.vcsSubstitutionVars;
	    }
	    // Set/Get TfsCheckinOptions
	    private void setTfsCheckinOptions(String s) {
	    	this.tfsCheckinOptions = s;
	    }  
	    private String getTfsCheckinOptions() {
	    	return this.tfsCheckinOptions;
	    }
	    // Set/Get TfsServerUrl
	    private void setTfsServerUrl(String s) {
	    	this.tfsServerUrl = s;
	    }  
	    private String getTfsServerUrl() {
	    	return this.tfsServerUrl;
	    }
	    // Set/Get vcsUseExistingWorkspace
	    private void setVcsUseExistingWorkdspace(String s) {
	    	this.vcsUseExistingWorkspace = s;
	    }  
	    private String getVcsUseExistingWorkdspace() {
	    	return this.vcsUseExistingWorkspace;
	    }
	}

	/**
	 * @return the VCSWSDAO
	 */
	public VCSDAO getVCSDAO() {
		if(vcsDAO == null){
			vcsDAO = new VCSWSDAOImpl();
		}
		return vcsDAO;
	}
	/**
	 * @param vcsDAO the vcsDAO to set
	 */
	public void setVCSDAO(VCSDAO vcsDAO) {
		this.vcsDAO = vcsDAO;
	}

	
	/**
	 * @return the resourceManager
	 */
	public ResourceManager getResourceManager() {
		if(this.resourceManager == null){
			this.resourceManager = new ResourceManagerImpl();
		}
		return resourceManager;
	}
	/**
	 * @param resourceManager the resourceManager to set
	 */
	public void setResourceManager(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}	

}
