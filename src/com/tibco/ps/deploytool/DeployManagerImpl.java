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
package com.tibco.ps.deploytool;

import com.tibco.ps.common.exception.CompositeException;
import com.tibco.ps.deploytool.services.ArchiveManager;
import com.tibco.ps.deploytool.services.ArchiveManagerImpl;
import com.tibco.ps.deploytool.services.DataSourceManager;
import com.tibco.ps.deploytool.services.DataSourceManagerImpl;
import com.tibco.ps.deploytool.services.GroupManager;
import com.tibco.ps.deploytool.services.GroupManagerImpl;
import com.tibco.ps.deploytool.services.PrivilegeManager;
import com.tibco.ps.deploytool.services.PrivilegeManagerImpl;
import com.tibco.ps.deploytool.services.RebindManager;
import com.tibco.ps.deploytool.services.RebindManagerImpl;
import com.tibco.ps.deploytool.services.RegressionManager;
import com.tibco.ps.deploytool.services.RegressionManagerImpl;
import com.tibco.ps.deploytool.services.ResourceCacheManager;
import com.tibco.ps.deploytool.services.ResourceCacheManagerImpl;
import com.tibco.ps.deploytool.services.ResourceManager;
import com.tibco.ps.deploytool.services.ResourceManagerImpl;
import com.tibco.ps.deploytool.services.ServerAttributeManager;
import com.tibco.ps.deploytool.services.ServerAttributeManagerImpl;
import com.tibco.ps.deploytool.services.ServerManager;
import com.tibco.ps.deploytool.services.ServerManagerImpl;
import com.tibco.ps.deploytool.services.TriggerManager;
import com.tibco.ps.deploytool.services.TriggerManagerImpl;
import com.tibco.ps.deploytool.services.UserManager;
import com.tibco.ps.deploytool.services.UserManagerImpl;
import com.tibco.ps.deploytool.services.VCSManager;
import com.tibco.ps.deploytool.services.VCSManagerImpl;
import com.compositesw.services.system.admin.resource.ResourceList;

public class DeployManagerImpl implements DeployManager{

	private ArchiveManager archiveManager = null;
	private DataSourceManager dataSourceManager = null;
	private GroupManager groupManager = null;
	private PrivilegeManager privilegeManager = null;
	private RebindManager rebindManager = null;
	private RegressionManager regressionManager = null;
	private ResourceManager resourceManager = null;
	private ResourceCacheManager resourceCacheManager = null;
	private ServerAttributeManager serverAttributeManager = null;
	private ServerManager serverManager = null;
	private TriggerManager triggerManager = null;
	private UserManager userManager = null;
	private VCSManager vcsManager = null;

	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::CisDeployTool Orchestrator---------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#CisDeployTool(java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void execCisDeployTool(String file, String vcsUser, String vcsPassword) throws CompositeException {
		// Execute the CisDeployTool command-line orchestration utility (replaces batch and shell scripts) 
		CisDeployTool.execCisDeployTool(file, vcsUser, vcsPassword);
	}	
 	//--------------------------------------------------------------------------------------------------------------------------------
	//--End::CisDeployTool Orchestrator-----------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------

	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::Archive Module---------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#pkg_import(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void pkg_import(String serverId, String archiveIds, String pathToArchiveXML, String pathToServersXML) throws CompositeException {
		getArchiveManager().pkg_import(serverId, archiveIds, pathToArchiveXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#pkg_export(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void pkg_export(String serverId, String archiveIds, String pathToArchiveXML, String pathToServersXML) throws CompositeException {
		getArchiveManager().pkg_export(serverId, archiveIds, pathToArchiveXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#backup_import(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void backup_import(String serverId, String archiveIds, String pathToArchiveXML, String pathToServersXML)	throws CompositeException {
		getArchiveManager().backup_import(serverId, archiveIds, pathToArchiveXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#backup_export(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void backup_export(String serverId, String archiveIds, String pathToArchiveXML, String pathToServersXML) throws CompositeException {
		getArchiveManager().backup_export(serverId, archiveIds, pathToArchiveXML, pathToServersXML);
	}
	
	/**
	 * @return the archiveManager
	 */
	public ArchiveManager getArchiveManager() {
		if(archiveManager == null){
			archiveManager = new ArchiveManagerImpl();
		}
		return archiveManager;
	}
	/**
	 * @param set the archiveManager
	 */
	public void setArchiveManager(ArchiveManager archiveManager) {
		this.archiveManager = archiveManager;
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	//--End::Archive Module-----------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------

	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::DataSource Module------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#updateDataSource(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void updateDataSources(String serverId, String dataSourceIds, String pathToDataSourceXML, String pathToServersXML)  throws CompositeException {
		getDataSourceManager().updateDataSources(serverId, dataSourceIds, pathToDataSourceXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#enableDataSources(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void enableDataSources(String serverId, String dataSourceIds,String pathToDataSourceXML, String pathToServersXML)  throws CompositeException {
		getDataSourceManager().enableDataSources(serverId, dataSourceIds, pathToDataSourceXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#reIntrospectDataSources(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void reIntrospectDataSources(String serverId,String dataSourceIds, String pathToDataSourceXML,String pathToServersXML)  throws CompositeException {
		getDataSourceManager().reIntrospectDataSources(serverId, dataSourceIds, pathToDataSourceXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#introspectDataSources(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void introspectDataSources(String serverId,String dataSourceIds, String pathToDataSourceXML,String pathToServersXML)  throws CompositeException {
		getDataSourceManager().introspectDataSources(serverId, dataSourceIds, pathToDataSourceXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#generateDataSourcesXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateDataSourcesXML(String serverId, String startPath, String pathToDataSourceXML, String pathToServersXML) throws CompositeException {
		getDataSourceManager().generateDataSourcesXML(serverId, startPath, pathToDataSourceXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#getDataSourcesChildren(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public ResourceList getDataSourcesChildren(String serverId,String resourcePath, String resourceType, String childResourceType, String detailLevel,String pathToServersXML)  throws CompositeException {
		return getDataSourceManager().getDataSourcesChildren(serverId, resourcePath, resourceType, childResourceType, detailLevel, pathToServersXML);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#generateDataSourceAttributeDefs(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateDataSourceAttributeDefs(String serverId, String startPath, String pathToDataSourceAttrDefs, String pathToServersXML) throws CompositeException {
		getDataSourceManager().generateDataSourceAttributeDefs(serverId, startPath, pathToDataSourceAttrDefs, pathToServersXML);		
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#generateDataSourceAttributeDefsByDataSourceType(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateDataSourceAttributeDefsByDataSourceType(String serverId, String dataSourceType, String pathToDataSourceAttrDefs, String pathToServersXML) throws CompositeException {
		getDataSourceManager().generateDataSourceAttributeDefsByDataSourceType(serverId, dataSourceType, pathToDataSourceAttrDefs, pathToServersXML);		
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#generateDataSourceTypes(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateDataSourceTypes(String serverId, String pathToDataSourceTypesXML, String pathToServersXML) throws CompositeException {
		getDataSourceManager().generateDataSourceTypes(serverId, pathToDataSourceTypesXML, pathToServersXML);		
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#generateDataSourcesResourceListXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateDataSourcesResourceListXML(String serverId, String startPath, String pathToDataSourceResourceListXML, String pathToServersXML) throws CompositeException {
		getDataSourceManager().generateDataSourcesResourceListXML(serverId, startPath, pathToDataSourceResourceListXML, pathToServersXML);
	}

	/**
	 * @return the dataSourceManager
	 */
	public DataSourceManager getDataSourceManager() {
		if(dataSourceManager == null){
			dataSourceManager = new DataSourceManagerImpl();
		}
		return dataSourceManager;
	}

	/**
	 * @param dataSourceManager the dataSourceManager to set
	 */
	public void setDataSourceManager(DataSourceManager dataSourceManager) {
		this.dataSourceManager = dataSourceManager;
	}

	//--------------------------------------------------------------------------------------------------------------------------------
	//--End::DataSource Module--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------

	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::Group Module-----------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#createOrUpdateGroups(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void createOrUpdateGroups(String serverId, String groupIds,String pathToGroupsXML, String pathToServersXML) throws CompositeException {
		getGroupManager().createOrUpdateGroups(serverId, groupIds,pathToGroupsXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#deleteGroups(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void deleteGroups(String serverId, String groupIds, String pathToGroupsXML, String pathToServersXML) throws CompositeException {
		getGroupManager().deleteGroups(serverId, groupIds, pathToGroupsXML,pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#addUsersToGroups(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void addUsersToGroups(String serverId, String groupIds, String userNames, String pathToGroupsXML, String pathToServersXML) throws CompositeException {
		getGroupManager().addUsersToGroups(serverId, groupIds, userNames, pathToGroupsXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#deleteUsersFromGroups(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void deleteUsersFromGroups(String serverId, String groupIds, String userNames, String pathToGroupsXML, String pathToServersXML) throws CompositeException {
		getGroupManager().deleteUsersFromGroups(serverId, groupIds, userNames,pathToGroupsXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#generateGroupsXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateGroupsXML(String serverId, String domainName, String pathToGroupsXML, String pathToServersXML) throws CompositeException {
		getGroupManager().generateGroupsXML(serverId, domainName,pathToGroupsXML, pathToServersXML);
	}
	

	public GroupManager getGroupManager() {
		if(this.groupManager == null){
			groupManager = new GroupManagerImpl();
		}
		return groupManager;
	}

	public void setGroupManager(GroupManager groupManager) {
		this.groupManager = groupManager;
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	//--End::Group Module-------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------

	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::Privilege Module-------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#updatePrivileges(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void updatePrivileges(String serverId, String privilegeIds, String pathToPrivilegeXML, String pathToServersXML) throws CompositeException 
	{
		getPrivilegeManager().updatePrivileges(serverId, privilegeIds, pathToPrivilegeXML, pathToServersXML);		
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#generatePrivilegesXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generatePrivilegesXML(String serverId, String startPath, String pathToPrivilegeXML, String pathToServersXML, String filter, String options, String domainList) throws CompositeException 
	{
		getPrivilegeManager().generatePrivilegesXML(serverId, startPath, pathToPrivilegeXML, pathToServersXML, filter, options, domainList);		
	}
	
	/**
	 * @return the privilegeManager
	 */
	public PrivilegeManager getPrivilegeManager() {
		if(privilegeManager == null){
			privilegeManager = new PrivilegeManagerImpl();
		}
		return privilegeManager;
	}

	/**
	 * @param the privilegeManager to set
	 */
	public void setPrivilegeManager(PrivilegeManager privilegeManager) {
		this.privilegeManager = privilegeManager;
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	//--End::Privilege Module---------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	

	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::Rebind Module----------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#generateRebindXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateRebindXML(String serverId, String startPath, String pathToRebindXML, String pathToServersXML)  throws CompositeException {
		getRebindManager().generateRebindXML(serverId, startPath, pathToRebindXML, pathToServersXML);	
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#rebindResources(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void rebindResources(String serverId, String rebindIds, String pathToRebindXml, String pathToServersXML)  throws CompositeException {
		getRebindManager().rebindResources(serverId, rebindIds, pathToRebindXml, pathToServersXML);		
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#rebindFolder(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void rebindFolder(String serverId, String rebindIds, String pathToRebindXml, String pathToServersXML)  throws CompositeException {
		getRebindManager().rebindFolder(serverId, rebindIds, pathToRebindXml, pathToServersXML);		
	}
		
	public RebindManager getRebindManager() {
		if(this.rebindManager == null){
			rebindManager = new RebindManagerImpl();
		}
		return rebindManager;
	}
	
	public void setRebindManager(RebindManager rebindManager) {
		this.rebindManager = rebindManager;
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	//--End::Rebind Module------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::Regression Module--------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#createRegressionInputFile(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void createRegressionInputFile(String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException
	{
		getRegressionManager().generateInputFile(serverId, regressionIds, pathToRegressionXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#executeRegressionTest(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void executeRegressionTest(String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException
	{
		getRegressionManager().executeRegressionTest(serverId, regressionIds, pathToRegressionXML, pathToServersXML);
	}	
	
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#compareRegressionFiles(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void compareRegressionFiles(String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException
	{
		getRegressionManager().compareRegressionFiles(serverId, regressionIds, pathToRegressionXML, pathToServersXML);
	}	

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#compareRegressionLogs(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void compareRegressionLogs(String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException
	{
		getRegressionManager().compareRegressionLogs(serverId, regressionIds, pathToRegressionXML, pathToServersXML);
	}	

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#executeSecurityTest(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void executeSecurityTest(String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException
	{
		getRegressionManager().executeSecurityTest(serverId, regressionIds, pathToRegressionXML, pathToServersXML);
	}	

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#generateRegressionSecurityXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateRegressionSecurityXML(String serverId, String regressionIds, String pathToSourceRegressionXML, String pathToServersXML) throws CompositeException
	{
		getRegressionManager().generateRegressionSecurityXML(serverId, regressionIds, pathToSourceRegressionXML, pathToServersXML);
	}	
	
	
//	@Override
	public void executePerformanceTest(String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException 
	{
		getRegressionManager().executePerformanceTest(serverId, regressionIds, pathToRegressionXML, pathToServersXML);
	}

	public RegressionManager getRegressionManager() {
		if(this.regressionManager == null){
			this.regressionManager = new RegressionManagerImpl();
		}
		return regressionManager;
	}

	public void setRegressionManager(RegressionManager regressionManager) {
		this.regressionManager = regressionManager;
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	//--End::RegressionTest Module----------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------

	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::Resource Module--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#executeConfiguredProcedure(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void executeConfiguredProcedures(String serverId,String procedureIds, String pathToResourceXML,String pathToServersXML) throws CompositeException {
		getResourceManager().executeConfiguredProcedures(serverId, procedureIds, pathToResourceXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#executeProcedure(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void executeProcedure(String serverId, String procedureName,String dataServiceName,  String pathToServersXML,String arguments) throws CompositeException {
		getResourceManager().executeProcedure(serverId, procedureName, dataServiceName, pathToServersXML, arguments);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#executeProcedure(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void executeProcedure(String serverId, String procedureName,String dataServiceName,  String pathToServersXML,String arguments, String outputReturnVariables) throws CompositeException {
		getResourceManager().executeProcedure(serverId, procedureName, dataServiceName, pathToServersXML, arguments, outputReturnVariables);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#deleteResource(java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void deleteResource(String serverId, String resourcePath, String pathToServersXML) throws CompositeException {
		getResourceManager().deleteResource(serverId, resourcePath, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#deleteResources(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void deleteResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException {
		getResourceManager().deleteResources(serverId, resourceIds, pathToResourceXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceManager#renameResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void renameResource(String serverId, String resourcePath, String pathToServersXML, String newName) throws CompositeException {
		getResourceManager().renameResource(serverId, resourcePath, pathToServersXML, newName);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#renameResources(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void renameResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException {
		getResourceManager().renameResources(serverId, resourceIds, pathToResourceXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceManager#copyResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void copyResource(String serverId, String resourcePath, String pathToServersXML, String targetContainerPath, String newName, String copyMode) throws CompositeException {
		getResourceManager().copyResource(serverId, resourcePath, pathToServersXML, targetContainerPath, newName, copyMode);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceManager#copyResources(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void copyResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException {
		getResourceManager().copyResources(serverId, resourceIds, pathToResourceXML, pathToServersXML);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceManager#moveResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void moveResource(String serverId, String resourcePath, String pathToServersXML, String targetContainerPath, String newName) throws CompositeException {
		getResourceManager().moveResource(serverId, resourcePath, pathToServersXML, targetContainerPath, newName);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceManager#moveResources(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void moveResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException {
		getResourceManager().moveResources(serverId, resourceIds, pathToResourceXML, pathToServersXML);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#doResourceExist(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public boolean resourceExists(String serverId, String resourcePath, String resourceType, String pathToServersXML) throws CompositeException {
		return getResourceManager().resourceExists(serverId, resourcePath, resourceType, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#doResourceExist(java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public boolean doResourceExist(String serverId, String resourcePath, String pathToServersXML) throws CompositeException {
		return getResourceManager().doResourceExist(serverId, resourcePath, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#doResourcesExist(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void doResourcesExist(String serverId, String resourceIds,String pathToResourceXML, String pathToServersXML) throws CompositeException {
		getResourceManager().doResourcesExist(serverId, resourceIds, pathToResourceXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceManager#lockResource(java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void lockResource(String serverId, String resourcePath, String pathToServersXML) throws CompositeException {
		getResourceManager().lockResource(serverId, resourcePath, pathToServersXML);
	}
		
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceManager#lockResources(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void lockResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException {
		getResourceManager().lockResources(serverId, resourceIds, pathToResourceXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceManager#unlockResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void unlockResource(String serverId, String resourcePath, String pathToServersXML, String comment) throws CompositeException {
		getResourceManager().unlockResource(serverId, resourcePath, pathToServersXML, comment);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceManager#unlockResources(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void unlockResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException {
		getResourceManager().unlockResources(serverId, resourceIds, pathToResourceXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#getResourcesFromPath(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public ResourceList getResourcesFromPath(String serverId, String resourcePath, String resourceType, String resourceTypeFilter, String detailLevel, String pathToServersXML)  throws CompositeException {
		return getResourceManager().getResourcesFromPath(serverId, resourcePath, resourceType, resourceTypeFilter, detailLevel, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#getImmediateResourcesFromPath(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public ResourceList getImmediateResourcesFromPath(String serverId, String resourcePath, String resourceType, String detailLevel, String pathToServersXML)  throws CompositeException {
		return getResourceManager().getImmediateResourcesFromPath(serverId, resourcePath, resourceType, detailLevel, pathToServersXML);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceManager#createFolder(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void createFolder(String serverId, String resourcePath, String pathToServersXML, String recursive) throws CompositeException {
		getResourceManager().createFolder(serverId, resourcePath, pathToServersXML, recursive);		
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceManager#createFolder(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void createFolder(String serverId, String resourcePath, String pathToServersXML, String recursive, String ignoreErrors) throws CompositeException {
		getResourceManager().createFolder(serverId, resourcePath, pathToServersXML, recursive, ignoreErrors);		
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceManager#createFolders(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void createFolders(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException {
		getResourceManager().createFolders(serverId, resourceIds, pathToResourceXML, pathToServersXML);		
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
	//--------------------------------------------------------------------------------------------------------------------------------
	//--End::Resource Module----------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::ResourceCache Module---------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#updateResourceCache(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void updateResourceCache(String serverId, String resourceIds, String pathToResourceCacheXML, String pathToServersXML)  throws CompositeException 
	{
		getResourceCacheManager().updateResourceCache(serverId, resourceIds, pathToResourceCacheXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#refreshResourceCache(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void refreshResourceCache(String serverId, String resourceIds, String pathToResourceCacheXML, String pathToServersXML)  throws CompositeException 
	{
		getResourceCacheManager().refreshResourceCache(serverId, resourceIds, pathToResourceCacheXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#clearResourceCache(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void clearResourceCache(String serverId, String resourceIds, String pathToResourceCacheXML, String pathToServersXML)  throws CompositeException 
	{
		getResourceCacheManager().clearResourceCache(serverId, resourceIds, pathToResourceCacheXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#updateResourceCacheEnabled(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void updateResourceCacheEnabled(String serverId, String resourceIds, String pathToResourceCacheXML, String pathToServersXML)  throws CompositeException 
	{
		getResourceCacheManager().updateResourceCacheEnabled(serverId, resourceIds, pathToResourceCacheXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#generateResourceCacheXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateResourceCacheXML(String serverId, String startPath, String pathToResourceCacheXML, String pathToServersXML, String options) throws CompositeException 
	{
		getResourceCacheManager().generateResourceCacheXML(serverId, startPath, pathToResourceCacheXML, pathToServersXML, options);
	}
	
	
	/**
	 * @return the resourceCacheManager
	 */
	public ResourceCacheManager getResourceCacheManager() {
		if(resourceCacheManager == null){
			resourceCacheManager = new ResourceCacheManagerImpl();
		}
		return resourceCacheManager;
	}

	/**
	 * @param the resourceCacheManager to set
	 */
	public void setResourceCacheManager(ResourceCacheManager resourceCacheManager) {
		this.resourceCacheManager = resourceCacheManager;
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	//--End::ResourceCache Module-----------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::ServerAttribute Module-------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#updateServerAttributes(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void updateServerAttributes(String serverId, String serverAttributeIds, String pathToServerAttributeXML, String pathToServersXML) throws CompositeException 
	{
		getServerAttributeManager().updateServerAttributes(serverId, serverAttributeIds, pathToServerAttributeXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#generateServerAttributesXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateServerAttributesXML(String serverId, String startPath, String pathToServerAttributeXML, String pathToServersXML, String updateRule) throws CompositeException 
	{
		getServerAttributeManager().generateServerAttributesXML(serverId, startPath, pathToServerAttributeXML, pathToServersXML, updateRule);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#generateServerAttributeDefinitionsXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateServerAttributeDefinitionsXML(String serverId, String startPath, String pathToServerAttributeXML, String pathToServersXML, String updateRule) throws CompositeException 
	{
		getServerAttributeManager().generateServerAttributeDefinitionsXML(serverId, startPath, pathToServerAttributeXML, pathToServersXML, updateRule);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#getServerVersion(java.lang.String, java.lang.String)
	 */
//	@Override
	public String getServerVersion(String serverId, String pathToServersXML) throws CompositeException 
	{
		return(getServerAttributeManager().getServerVersion(serverId, pathToServersXML));
	}

	/**
	 * @return the serverAttributeManager
	 */
	public ServerAttributeManager getServerAttributeManager() {
		if(serverAttributeManager == null){
			serverAttributeManager = new ServerAttributeManagerImpl();
		}
		return serverAttributeManager;
	}

	/**
	 * @param the serverAttributeManager to set
	 */
	public void setServerAttributeManager(ServerAttributeManager serverAttributeManager) {
		this.serverAttributeManager = serverAttributeManager;
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	//--End::ServerAttribute Module---------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------

	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::Server Manager Module--------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#startServer(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void startServer(String serverId, String pathToServersXML) throws CompositeException{
		getServerManager().startServer(serverId, pathToServersXML);
		
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#stopServer(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void stopServer(String serverId, String pathToServersXML) throws CompositeException{
		getServerManager().stopServer(serverId, pathToServersXML);
		
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#restartServer(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void restartServer(String serverId, String pathToServersXML)  throws CompositeException {
		getServerManager().restartServer(serverId, pathToServersXML);
		
	}		

	/**
	 * @return the serverManager
	 */
	public ServerManager getServerManager() {
		if(serverManager == null){
			serverManager = new ServerManagerImpl();
		}
		return serverManager;
	}

	/**
	 * @param the serverManager to set
	 */
	public void setServerManager(ServerManager serverManager) {
		this.serverManager = serverManager;
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	//--End::Server Manager Module----------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------

	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::Trigger Module---------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#enableTriggers(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void enableTriggers(String serverId, String triggerIds, String pathToTriggersXML, String pathToServersXML)  throws CompositeException {
		getTriggerManager().enableTriggers(serverId, triggerIds, pathToTriggersXML, pathToServersXML);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#updateTriggers(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void updateTriggers(String serverId, String triggerIds, String pathToTriggersXML, String pathToServersXML)  throws CompositeException {
		getTriggerManager().updateTriggers(serverId, triggerIds, pathToTriggersXML, pathToServersXML);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#generateTriggersXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateTriggersXML(String serverId, String startPath, String pathToTriggersXML, String pathToServersXML)  throws CompositeException {
		getTriggerManager().generateTriggersXML(serverId, startPath, pathToTriggersXML, pathToServersXML);
	}
	
	public TriggerManager getTriggerManager() {
		if(this.triggerManager == null){
			triggerManager = new TriggerManagerImpl();
		}
		return triggerManager;
	}
	
	public void setTriggerManager(TriggerManager triggerManager) {
		this.triggerManager = triggerManager;
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	//--End::Trigger Module-----------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::User Module------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#createOrUpdateUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void createOrUpdateUsers(String serverId, String userIds, String pathToUsersXML, String pathToServersXML) throws CompositeException {
		getUserManager().createOrUpdateUsers(serverId, userIds, pathToUsersXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#deleteUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void deleteUsers(String serverId, String userIds, String pathToUsersXML, String pathToServersXML) throws CompositeException {
		getUserManager().deleteUsers(serverId, userIds, pathToUsersXML, pathToServersXML);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#generateUsersXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateUsersXML(String serverId, String domainName, String pathToUsersXML, String pathToServersXML) throws CompositeException {
		getUserManager().generateUsersXML(serverId, domainName, pathToUsersXML, pathToServersXML);
	}
	
	/**
	 * @return the userManager
	 */
	public UserManager getUserManager() {
		if(userManager == null){
			userManager = new UserManagerImpl();
		}
		return userManager;
	}

	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	//--End::User Module--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	

	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::VCS Module-------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/*******************************************************
	 * 
	 * PDTOOL VCS INTEGRATION METHODS
	 *
	 *******************************************************/
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsInitializeWorkspace(java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsInitWorkspace(String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsInitWorkspace(vcsUser, vcsPassword);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsInitializeWorkspace2(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsInitWorkspace2(String vcsConnectionId, String pathToVcsXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsInitWorkspace2(vcsConnectionId, pathToVcsXML, vcsUser, vcsPassword);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsInitializeBaseFolderCheckin(java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsInitializeBaseFolderCheckin(String customPathList, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsInitializeBaseFolderCheckin(customPathList, vcsUser, vcsPassword);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsInitializeBaseFolderCheckin(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsInitializeBaseFolderCheckin2(String vcsConnectionId, String customPathList, String pathToVcsXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsInitializeBaseFolderCheckin2(vcsConnectionId, customPathList, pathToVcsXML, vcsUser, vcsPassword);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsCheckout(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckout(String serverId, String vcsResourcePath, String vcsResourceType, String vcsRevision, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsCheckout(serverId, vcsResourcePath, vcsResourceType, vcsRevision, pathToServersXML, vcsUser, vcsPassword);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsCheckout(java.lang.String, java.lang.String, java.lang.String, jjava.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckout(String serverId, String vcsResourcePath, String vcsResourceType, String vcsLabel, String vcsRevision, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsCheckout(serverId, vcsResourcePath, vcsResourceType, vcsLabel, vcsRevision, pathToServersXML, vcsUser, vcsPassword);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsCheckout2(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckout2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String vcsRevision, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsCheckout2(serverId, vcsConnectionId, vcsResourcePath, vcsResourceType, vcsRevision, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsCheckout2(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckout2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String vcsLabel, String vcsRevision, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsCheckout2(serverId, vcsConnectionId, vcsResourcePath, vcsResourceType, vcsLabel, vcsRevision, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsCheckouts(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckouts(String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsCheckouts(serverId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
	}

//	@Override
	public void vcsCheckouts2(String serverId, String vcsConnectionId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsCheckouts2(serverId, vcsConnectionId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);		
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsCheckin(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckin(String serverId, String vcsResourcePath, String vcsResourceType, String vcsMessage, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsCheckin(serverId, vcsResourcePath, vcsResourceType, vcsMessage, pathToServersXML, vcsUser, vcsPassword);
	}

//	@Override
	public void vcsCheckin2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String vcsMessage, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsCheckin2(serverId, vcsConnectionId, vcsResourcePath, vcsResourceType, vcsMessage, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);		
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsCheckins(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsCheckins(String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsCheckins(serverId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
	}

//	@Override
	public void vcsCheckins2(String serverId, String vcsConnectionId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsCheckins2(serverId, vcsConnectionId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsForcedCheckin(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsForcedCheckin(String serverId, String vcsResourcePath, String vcsResourceType, String vcsMessage, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsForcedCheckin(serverId, vcsResourcePath, vcsResourceType, vcsMessage, pathToServersXML, vcsUser, vcsPassword);
	}

//	@Override
	public void vcsForcedCheckin2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String vcsMessage, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsForcedCheckin2(serverId, vcsConnectionId, vcsResourcePath, vcsResourceType, vcsMessage, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);		
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsForcedCheckins(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsForcedCheckins(String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser,	String vcsPassword) throws CompositeException {
		getVCSManager().vcsForcedCheckins(serverId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
	}

//	@Override
	public void vcsForcedCheckins2(String serverId, String vcsConnectionId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsForcedCheckins2(serverId, vcsConnectionId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);		
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsPrepareCheckin(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsPrepareCheckin(String serverId, String vcsResourcePath, String vcsResourceType, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsPrepareCheckin(serverId, vcsResourcePath, vcsResourceType, pathToServersXML, vcsUser, vcsPassword);
	}
	
//	@Override
	public void vcsPrepareCheckin2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsPrepareCheckin2(serverId, vcsConnectionId, vcsResourcePath, vcsResourceType, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsPrepareCheckins(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsPrepareCheckins(String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsPrepareCheckins(serverId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);
	}
	
//	@Override
	public void vcsPrepareCheckins2(String serverId, String vcsConnectionId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsPrepareCheckins2(serverId, vcsConnectionId, vcsIds, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#generateVCSXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateVCSXML(String serverId, String startPath, String pathToVCSXML, String pathToServersXML) throws CompositeException {
		getVCSManager().generateVCSXML(serverId, startPath, pathToVCSXML, pathToServersXML);		
	}	
	
//	@Override
	public void generateVCSXML2(String serverId, String vcsConnectionId, String startPath, String pathToVcsXML, String pathToServersXML) throws CompositeException {
		getVCSManager().generateVCSXML2(serverId, vcsConnectionId, startPath, pathToVcsXML, pathToServersXML);
	}

	/*******************************************************
	 * 
	 * PDTOOL STUDIO VCS INTEGRATION METHODS
	 *
	 *******************************************************/
	/*
	 * (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsStudioInitWorkspace(java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsStudioInitWorkspace(String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsStudioInitWorkspace(vcsUser, vcsPassword);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsStudioInitializeBaseFolderCheckin(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsStudioInitializeBaseFolderCheckin(String customPathList, String vcsCheckinOptions, String vcsUser, String vcsPassword) throws CompositeException {
		getVCSManager().vcsStudioInitializeBaseFolderCheckin(customPathList, vcsCheckinOptions, vcsUser, vcsPassword);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsStudioCheckout(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsStudioCheckout(String resourcePath, String resourceType, String revision, String vcsWorkspace, String vcsWorkspaceTemp) throws CompositeException {
		getVCSManager().vcsStudioCheckout(resourcePath, resourceType, revision, vcsWorkspace, vcsWorkspaceTemp);		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsStudioCheckin(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsStudioCheckin(String resourcePath, String resourceType, String message, String vcsWorkspace, String vcsWorkspaceTemp) throws CompositeException {
		getVCSManager().vcsStudioCheckin(resourcePath, resourceType, message, vcsWorkspace, vcsWorkspaceTemp);		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsStudioForcedCheckin(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsStudioForcedCheckin(String resourcePath, String resourceType, String message, String vcsWorkspace, String vcsWorkspaceTemp) throws CompositeException {
		getVCSManager().vcsStudioForcedCheckin(resourcePath, resourceType, message, vcsWorkspace, vcsWorkspaceTemp);		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsScanPathLength(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsScanPathLength(String serverId, String vcsMaxPathLength, String vcsResourcePathList, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		int ivcsMaxPathLength = 0;
		if (vcsMaxPathLength != null && vcsMaxPathLength.trim().length() > 0)
			ivcsMaxPathLength = Integer.valueOf(vcsMaxPathLength.trim());
		getVCSManager().vcsScanPathLength(serverId, ivcsMaxPathLength, vcsResourcePathList, pathToServersXML, vcsUser, vcsPassword);		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tibco.ps.deploytool.DeployManager#vcsScanPathLength2(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void vcsScanPathLength2(String serverId, String vcsConnectionId, String vcsMaxPathLength, String vcsResourcePathList, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException {
		int ivcsMaxPathLength = 0;
		if (vcsMaxPathLength != null && vcsMaxPathLength.trim().length() > 0)
			ivcsMaxPathLength = Integer.valueOf(vcsMaxPathLength.trim());
		getVCSManager().vcsScanPathLength2(serverId, vcsConnectionId, ivcsMaxPathLength, vcsResourcePathList, pathToVcsXML, pathToServersXML, vcsUser, vcsPassword);		
	}

	/**
	 * @return the vcsManager
	 */
	public VCSManager getVCSManager() {
		if(vcsManager == null){
			vcsManager = new VCSManagerImpl();
		}
		return vcsManager;
	}
	/**
	 * @param vcsManager the vcsManager to set
	 */
	public void setVCSManager(VCSManager vcsManager) {
		this.vcsManager = vcsManager;
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	//--End::VCS Module---------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------


	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::?? Module--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------

	//--------------------------------------------------------------------------------------------------------------------------------
	//--End::?? Module----------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	

}
