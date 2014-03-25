/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.services;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;

import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.exception.ValidationException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.XMLUtils;
import com.cisco.dvbu.ps.deploytool.dao.ArchiveDAO;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.ArchiveWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.cisco.dvbu.ps.deploytool.modules.ArchiveModule;
import com.cisco.dvbu.ps.deploytool.modules.ArchiveType;

public class ArchiveManagerImpl implements ArchiveManager{

	private static Log logger = LogFactory.getLog(ArchiveManagerImpl.class);
    private static String propertyFile = CommonConstants.propertyFile;
	
    private ArchiveDAO archiveDAO = null;
    
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.ArchiveManager#pkg_import(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void pkg_import(String serverId, String archiveIds, String pathToArchiveXML, String pathToServersXML) throws CompositeException {
		String prefix = "pkg_import";
		if(logger.isDebugEnabled()){
			logger.debug(" Entering ArchiveManagerImpl.pkg_import() with following params "+" serverId: "+serverId+", archiveIds: "+archiveIds+", pathToArchiveXML: "+pathToArchiveXML+", pathToServersXML: "+pathToServersXML);
		}
		archiveAction(ArchiveDAO.action.IMPORT.name(), serverId, archiveIds, pathToArchiveXML, pathToServersXML, prefix);
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.ArchiveManager#pkg_export(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void pkg_export(String serverId, String archiveIds, String pathToArchiveXML, String pathToServersXML) throws CompositeException {
		String prefix = "pkg_export";
		if(logger.isDebugEnabled()){
			logger.debug(" Entering ArchiveManagerImpl.pkg_export() with following params "+" serverId: "+serverId+", archiveIds: "+archiveIds+", pathToArchiveXML: "+pathToArchiveXML+", pathToServersXML: "+pathToServersXML);
		}
		archiveAction(ArchiveDAO.action.EXPORT.name(), serverId, archiveIds, pathToArchiveXML, pathToServersXML, prefix);
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.ArchiveManager#backup_import(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void backup_import(String serverId,String archiveIds, String pathToArchiveXML,String pathToServersXML) throws CompositeException {
		String prefix = "backup_import";
		if(logger.isDebugEnabled()){
			logger.debug(" Entering ArchiveManagerImpl.backup_import() with following params "+" serverId: "+serverId+", archiveIds: "+archiveIds+", pathToArchiveXML: "+pathToArchiveXML+", pathToServersXML: "+pathToServersXML);
		}
		archiveAction(ArchiveDAO.action.RESTORE.name(), serverId, archiveIds, pathToArchiveXML, pathToServersXML, prefix);
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.ArchiveManager#backup_export(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void backup_export(String serverId,String archiveIds, String pathToArchiveXML,String pathToServersXML) throws CompositeException {
		String prefix = "backup_export";
		if(logger.isDebugEnabled()){
			logger.debug(" Entering ArchiveManagerImpl.backup_export() with following params "+" serverId: "+serverId+", archiveIds: "+archiveIds+", pathToArchiveXML: "+pathToArchiveXML+", pathToServersXML: "+pathToServersXML);
		}
		 archiveAction(ArchiveDAO.action.BACKUP.name(), serverId, archiveIds, pathToArchiveXML, pathToServersXML, prefix);
	}


	private void archiveAction(String actionName, String serverId, String archiveIds, String pathToArchiveXML, String pathToServersXML, String commandPrefix) throws CompositeException {

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToArchiveXML)) {
			throw new CompositeException("File ["+pathToArchiveXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		String prefix = "archiveAction";
		// Get the configuration property file set in the environment with a default of deploy.properties
		String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");

		// Extract variables for the archiveIds
		archiveIds = CommonUtils.extractVariable(prefix, archiveIds, propertyFile, true);

		List<ArchiveType> archiveList = getArchiveEntries(serverId, archiveIds, pathToArchiveXML, pathToServersXML);
		if (archiveList != null && archiveList.size() > 0) {
			//returnResList = new ResourceList();

			// -- using structure of xml as represented in jaxb class - no need to translate 
			//    into another structure 
			String message = "Archive Ids="+archiveIds+" not found.";

			// Loop over the list of archive entries and apply their attributes to the target CIS instance.		
			for (ArchiveType archive : archiveList) {

				// Get the identifier and convert any $VARIABLES
				String identifier = CommonUtils.extractVariable(prefix, archive.getId(), propertyFile, true);
				
				/**
				 * Possible values for archives 
				 * 1. csv string like import1,import2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -import1,import3 (we ignore passed in resources and process rest of the in the input xml
				 */
				 if(DeployUtil.canProcessResource(archiveIds, identifier)){
					 message = null;
					 
					if(logger.isInfoEnabled()){
						logger.info("processing action "+actionName+" on archive "+identifier);
					}

					// -- for now, we use a common xml input file. Result is we need to validate that the
					//    input is appropriate for the desired operation.
					// -- handling EXPORT as first case 
					if (actionName == ArchiveDAO.action.EXPORT.name()) {
						
						// -- all we need is at least one resource and a filename
						//    overwrite and dependencies should have default
						String archiveFileName = archive.getArchiveFileName();
// 20120629@DA Modifications to support relocate and rebind options
//						int noResources = archive.getResources().getResourcePath().size();
						int noResources = 0 ;
						for ( Object o : archive.getResources().getExportOrRelocateOrRebind() )
							if ( o instanceof String )
								noResources ++ ;
// 20120629@DA End
						
						archiveFileName = (archiveFileName==null) ? "" : archiveFileName;
						
						// Extract and resolve variables used in the XML archive file name
						archiveFileName = CommonUtils.extractVariable(prefix, archiveFileName, propertyFile, true);
						archive.setArchiveFileName(archiveFileName);
						
						if (archiveFileName.length() == 0 || noResources == 0 ) {
							// TODO make this a little more descriptive
							throw new CompositeException("The input file for Archive is not valid.");
						}
						
					}
					// -- TODO continue validation of input xml for other operations
					if (actionName == ArchiveDAO.action.IMPORT.name() ||
						actionName == ArchiveDAO.action.BACKUP.name() || 
						actionName == ArchiveDAO.action.RESTORE.name()) 
					{
						String archiveFileName = archive.getArchiveFileName();
						if (archiveFileName == null || archiveFileName.length() == 0) {
							throw new CompositeException("The configuration file for Archive is not valid.");
						}
						// Extract and resolve variables used in the XML archive file name
						archiveFileName = CommonUtils.extractVariable(prefix, archiveFileName, propertyFile, true);
						archive.setArchiveFileName(archiveFileName);
					}
					// thats all for now
					
					getArchiveDAO().takeArchiveAction(actionName, archive, serverId, pathToServersXML, commandPrefix, propertyFile);
					
				}
			}
			if(logger.isInfoEnabled()){
				if (message != null)
					logger.info(message);
			}
		}
	}

	private List<ArchiveType> getArchiveEntries(String serverId, String archiveIds, String pathToArchiveXML, String pathToServersXML) {

		// validate incoming arguments
		if(serverId == null || serverId.trim().length() ==0 || archiveIds == null || archiveIds.trim().length() ==0 || pathToServersXML == null || pathToServersXML.trim().length() ==0 || pathToArchiveXML == null || pathToArchiveXML.trim().length() ==0){
			throw new ValidationException("Invalid Arguments");
		}

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToArchiveXML)) {
			throw new CompositeException("File ["+pathToArchiveXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		try {
			//using jaxb convert xml to corresponding java objects
			
			ArchiveModule archiveModule = (ArchiveModule)XMLUtils.getModuleTypeFromXML(pathToArchiveXML);
			if(archiveModule != null && archiveModule.getArchive() != null && !archiveModule.getArchive().isEmpty()){
				return archiveModule.getArchive();
			}
		} catch (CompositeException e) {
			logger.error("Error while parsing ArchiveModule XML: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
		return null;
	}

	/*
	private AttributeList populateAttributeList(ArchiveType archive) {

		// Set up the attribute list that specifies the desired config values for the archive implementation
		// TO DO:  Determine what structure the Archive DAO requires to pass into it and then map ArchiveModule XML to it.
		AttributeList attribs = new AttributeList();
		

		// TO DO: Implement getting the ArchiveModule.xml property file and assigning it to the Specific structure required by 
		//        the ArchiveWSDAOImpl.java
		
		return attribs;
	}
    */

	

	/**
	 * @return the archiveDAO
	 */
	public ArchiveDAO getArchiveDAO() {
		if(archiveDAO == null){
			archiveDAO = new ArchiveWSDAOImpl();
		}
		return archiveDAO;
	}

	/**
	 * @param archiveDAO the archiveDAO to set
	 */
	public void setArchiveDAO(ArchiveDAO archiveDAO) {
		this.archiveDAO = archiveDAO;
	}

}
