/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool;

import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.compositesw.services.system.admin.resource.ResourceList;

public interface DeployManager 
{
	/* 
	 * MethodList provides a listing of the methods where the developer wants to insure that password parameters are printed with "******" for debug and standard out printing
	 *   when DeployManagerUtil is invoked.
	 *
	 *   The method list contains the method name, the total number of parameters and the specific parameter # where a password is passed in.
	 *      Method List Format: method1[totalParams|passwordParam],  method2[totalParams|passwordParam]
	 */
	String methodList=
		// General PD Tool execution with a password
		"execCisDeployTool[3|3]"+
		// PD Tool version 1 original methods with a password
		",vcsInitWorkspace[2|2],vcsInitializeBaseFolderCheckin[3|3],vcsScanPathLength[6|6],vcsCheckout[7|7],vcsCheckout[8|8],vcsCheckouts[6|6],vcsCheckin[7|7],vcsCheckins[6|6],vcsForcedCheckin[7|7],vcsForcedCheckins[6|6],vcsPrepareCheckin[6|6],vcsPrepareCheckins[6|6]"+
		// PD Tool version 2 methods with a password
		",vcsInitWorkspace2[4|4],vcsInitializeBaseFolderCheckin2[5|5],vcsScanPathLength2[8|8],vcsCheckout2[9|9],vcsCheckout2[10|10],vcsCheckouts2[7|7],vcsCheckin2[9|9],vcsCheckins2[7|7],vcsForcedCheckin2[9|9],vcsForcedCheckins2[7|7],vcsPrepareCheckin2[8|8],vcsPrepareCheckins2[7|7]"+
		// PD Tool Studio methods with a password
		",vcsStudioInitWorkspace[2|2]";
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::CisDeployTool Orchestrator---------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/**
	 * CisDeployTool is the provides the script-based property file orchestration capability.
	 * @param file location of the orchestration property file
	 * @param vcsUser provides ability to pass in the VCS user from the command line (may be null).
	 * @param vcsPassword provides the ability to pass in the VCS password from the command line (may be null).
	 * @throws CompositeException
	 */
	public void execCisDeployTool(String file, String vcsUser, String vcsPassword) throws CompositeException;
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::Archive Module---------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Import a car file to a Composite server 
	 * @param serverId - target server id from servers config xml
	 * @param archiveIds - list of resource archive Ids (comma separated archive Ids) containing the archive instructions
	 * @param pathToArchiveXML - path to the archive module xml
	 * @param pathToServersXML - path to the server values xml
	 * @throws CompositeException
	 */
	public void pkg_import(String serverId, String archiveIds, String pathToArchiveXML, String pathToServersXML) throws CompositeException;
	
	/**
	 * Export a car file from a Composite server list of folders
	 * @param serverId - target server id from servers config xml
	 * @param archiveIds - list of resource archive Ids (comma separated archive Ids) containing the archive instructions
	 * @param pathToArchiveXML - path to the archive module xml
	 * @param pathToServersXML - path to the server values xml
	 * @throws CompositeException
	 */
	public void pkg_export(String serverId, String archiveIds, String pathToArchiveXML, String pathToServersXML) throws CompositeException;
	
	/**
	 * Restore a full server backup from a backup car file
	 * @param serverId - target server id from servers config xml
	 * @param archiveIds - list of resource archive Ids (comma separated archive Ids) containing the archive instructions
	 * @param pathToArchiveXML - path to the archive module xml
	 * @param pathToServersXML - path to the server values xml
	 * @throws CompositeException
	 */	
	public void backup_import(String serverId, String archiveIds, String pathToArchiveXML, String pathToServersXML) throws CompositeException;
	
	/**
	 * Create a full server backup of a CIS instance to a car file
	 * @param serverId - target server id from servers config xml
	 * @param archiveIds - list of resource archive Ids (comma separated archive Ids) containing the archive instructions
	 * @param pathToArchiveXML - path to the archive module xml
	 * @param pathToServersXML - path to the server values xml
	 * @throws CompositeException
	 */	
	public void backup_export(String serverId, String archiveIds, String pathToArchiveXML, String pathToServersXML) throws CompositeException;

	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::DataSource Module------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Update Data Source method updates data sources configurations for the passed in 
	 * data sources Ids list found in the the passed in datasources.xml file for the 
	 * target server Id 
	 * @param serverId target server id from servers config xml
	 * @param dataSourceIds list of data sources Ids(comma separated data source Ids)
	 * @param pathToDataSourceXML path to the data source xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void updateDataSources(String serverId, String dataSourceIds, String pathToDataSourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * Enable Data Source method enables data sources for the passed in data source ids list found 
	 * in the the passed in datasources.xml file for the 
	 * target server Id 
	 * @param serverId target server id from servers config xml
	 * @param dataSourceIds list of data sources Ids(comma separated data source Ids)
	 * @param pathToDataSourceXML path to the data source xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void enableDataSources(String serverId, String dataSourceIds, String pathToDataSourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * Re-introspect Data Source method re-introspects data sources for the passed in data source ids
	 * list found in the the passed in datasources.xml file for the 
	 * target server Id 
	 * @param serverId target server id from servers config xml
	 * @param dataSourceIds list of data sources Ids(comma separated data source Ids)
	 * @param pathToDataSourceXML path to the data source xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void reIntrospectDataSources(String serverId, String dataSourceIds, String pathToDataSourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * Introspect Data Source method introspects data sources for the passed in data source ids list found in the the passed in datasources.xml file for the target server Id.
	 *   The method allows the invoker to add, update or remove resources.
	 * @param serverId target server id from servers config xml
	 * @param dataSourceIds list of data sources Ids(comma separated data source Ids)
	 * @param pathToDataSourceXML path to the data source xml
	 * @param pathToServersXML path to the server values xml
	 */
	public void introspectDataSources(String serverId, String dataSourceIds, String pathToDataSourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * @param serverId target server id from servers config xml
	 * @param startPath starting path of the resource e.g /shared
	 * @param pathToDataSourceXML path including name to the data source xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void generateDataSourcesXML(String serverId, String startPath, String pathToDataSourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * Get all child resources of passed in resource type from all datasources in passed in resource path, this method traverses the resource tree from the 
	 * starting resource path and builds a resource list of passed in resource type from all datasources that exisit in that path/tree
	 * @param serverId target server config name
	 * @param resourcePath starting resource path if no resource path is passed /shared is defaulted to resource path
	 * @param resourceType the type of resource for the resourcePath
	 * @param childResourceType resource type here is the list of valid resource types 
	 * CONTAINER,DATA_SOURCE,DEFINITION_SET,LINK,PROCEDURE,TABLE,TREE,TRIGGER
	 * if childResourceType is not passed then all resources are returned
	 * @param detail Level resource detail Level here is the list of valid detail levels 
	 * FULL, SMIPLE, NONE. if detail level is not passed NONE is defaulted to detail level
	 * @param pathToServersXML path to the server values xml
	 * @return resource list with all child resources of passed in resource type from the data sources in the resource tree from the starting resource path
	 */	
	public ResourceList getDataSourcesChildren(String serverId, String resourcePath, String resourceType, String childResourceType, String detailLevel, String pathToServersXML);
	
	/**
	 * Generate Data Source Attribute Definitions for the passed in starting path and the target server Id.
	 *   The method allows the invoker generate a file of data source attribute definitions.  This method will be useful when the user wants to determine what
	 *   valid attributes are available for a given data source.
	 * @param serverId target server id from servers config xml
	 * @param startPath starting resource path. If no resource path is passed /shared is defaulted to resource path
	 * @param pathToDataSourceAttrDefs path to the data attribute defintions xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	void generateDataSourceAttributeDefs(String serverId, String startPath, String pathToDataSourceAttrDefs, String pathToServersXML) throws CompositeException;

	/**
	 * Generate Data Source Attribute Definitions for the passed in data source type and the target server Id.
	 *   The method allows the invoker generate a file of data source attribute definitions.  This method will be useful when the user wants to determine what
	 *   valid attributes are available for a given data source.
	 * @param serverId target server id from servers config xml
	 * @param dataSourceType a valid data source type which can be found in "getDataSourceTypes" output
	 * @param pathToDataSourceAttrDefs path to the data attribute defintions xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	void generateDataSourceAttributeDefsByDataSourceType(String serverId, String dataSourceType, String pathToDataSourceAttrDefs, String pathToServersXML) throws CompositeException;

	/**
	 * Generate Data Source Types for the Composite Server.
	 *   The method allows the invoker generate a file of data source types for the Composite Server.
	 * @param serverId target server id from servers config xml
	 * @param pathToDataSourceTypesXML path to the data source types xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	void generateDataSourceTypes(String serverId, String pathToDataSourceTypesXML, String pathToServersXML) throws CompositeException;
	
	/** 
	 * Generate Data Sources Resource List for a starting path and target server Id will export all of the Data Sources found and their children.
	 *   This will be useful to know the type and subtype for a particular data source child when constructing the "plan" entries for the 
	 *   "introspectDataSources" method.
	 * @param serverId target server id from servers config xml
	 * @param startPath starting path of the resource e.g /shared
	 * @param pathToDataSourceResourceListXML path including name to the data source resource list xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	void generateDataSourcesResourceListXML(String serverId, String startPath, String pathToDataSourceResourceListXML, String pathToServersXML)	throws CompositeException;
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::Group Module-----------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Create CIS groups. If they already exist, update them instead.
	 * @param serverId target server id from servers config xml
	 * @param groupIds comma separated list of group Ids(comma separated group Ids configured in GroupXML)
	 * @param pathToGroupsXML path to the groups xml
	 * @param pathToServersXML path to the server values xml
	 * @return void
	 * @throws CompositeException
	 */
	public void createOrUpdateGroups(String serverId, String groupIds, String pathToGroupsXML, String pathToServersXML) throws CompositeException;

	/**
	 * Delete CIS groups from a specified domain.
	 * @param serverId target server id from servers config xml
	 * @param groupIds comma separated list of group Ids(comma separated group Ids configured in GroupXML)
	 * @param pathToGroupsXML path to the groups xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void deleteGroups(String serverId, String groupIds, String pathToGroupsXML, String pathToServersXML) throws CompositeException;

	/**
	 * Add passed in users to passed in groups associated with group ids to target server associated with passed in server Id
	 * @param serverId target server id from servers config xml
	 * @param groupIds comma separated list of group Ids(comma separated group Ids configured in GroupXML)
	 * @param userNames comma separated user names
	 * @param pathToGroupsXML path to the groups xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void addUsersToGroups(String serverId, String groupIds, String userNames, String pathToGroupsXML, String pathToServersXML) throws CompositeException;

	/**
	 * Delete passed in users from passed in groups associated with group ids to target server associated with passed in server Id
	 * @param serverId target server id from servers config xml
	 * @param groupIds comma separated list of group Ids(comma separated group Ids configured in GroupXML)
	 * @param userNames comma separated user names like username1,username2
	 * @param pathToGroupsXML path to the groups xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void deleteUsersFromGroups(String serverId, String groupIds, String userNames, String pathToGroupsXML, String pathToServersXML) throws CompositeException;
	
	/**
	 * Export existing CIS groups to a XML file based on the list of passed in group ids and server id
	 * @param serverId target server id from servers config xml
	 * @param domain domain name
	 * if domain is not passed then all groups are included
	 * @param pathToGroupsXML path including name to the groups xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void generateGroupsXML(String serverId, String domainName, String pathToGroupsXML, String pathToServersXML) throws CompositeException;
	

	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::Privilege Module-------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Update Resource Privileges method updates privileges on resources for the passed in 
	 * privilege Ids list found in the the passed in Privilege.xml file for the target server Id 
	 * @param serverId target server id from servers config xml
	 * @param privilegeIds list of resource privilege Ids(comma separated data privilege Ids)
	 * @param pathToPrivilegeXML path to the privilege xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void updatePrivileges(String serverId, String privilegeIds, String pathToPrivilegeXML, String pathToServersXML) throws CompositeException;

	/**
	 * @param serverId target server id from servers config xml
	 * @param startPath starting path of the resource e.g /shared
	 * @param pathToPrivilegeXML path including name to the privilege xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * 
	 * @param filter - a filter to return all or restrict the types of resources that are returned.
	 *        The filter list is a space or comma separated list which may include one or more of the following
	 *        [ALL CONTAINER DATA_SOURCE DEFINITION_SET LINK PROCEDURE TABLE TREE TRIGGER COLUMN]
	 *        If the list contains ALL anywhere in the list then ALL resource types are returned and the rest of the list is ignored.
	 *        
	 * @param options specify behavior.
	 *        Note: admin privileges are never generated and never updated 
	 *        Options contain a space or comma separated list of one or more options to generate privileges for 
	 *           [USER GROUP SYSTEM NONSYSTEM PARENT CHILD]
	 *        
	 *   nameType
	 * 		  USER  - generate nameType=USER privileges for a given resource
	 *        GROUP - generate nameType=GROUP privileges for a given resource (Default behavior if nothing is specified)
	 *        Note: Set both USER,GROUP to generate both
	 *        
	 *   System vs. NonSystem nameTypes
	 *        SYSTEM -    generate SYSTEM nameTypes
	 *                    group=all
	 *                    users=anonymous, monitor
	 *                    
	 *        NONSYSTEM - generate NONSYSTEM nameTypes (all users and groups that are not SYSTEM) (Default behavior if nothing is specified)
	 *        Note: Set both SYSTEM,NONSYSTEM to generate both groups
	 *        
	 *   Path hierarchy
	 *        PARENT    - generate only the parent (starting path) according to the filter (Default behavior if nothing is specified)
	 *        CHILD     - generate privileges for all children of the starting path according to the filter
	 *        Note: Set both PARENT,CHILD if you want to generate the Parent along with its children
	 *        
	 * @param domainList a space or comma separated list of domains for which to generate privileges for (Default=composite)
	 * 
	 * @throws CompositeException
	 */
	public void generatePrivilegesXML(String serverId, String startPath, String pathToPrivilegeXML, String pathToServersXML, String filter, String options, String domainList) throws CompositeException;

	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::Rebind Module----------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Write rebinds to an XML file from the server associated with passed in target server id
	 * @param serverId target server id from servers config xml
	 * @oaram startPath starting path of the resource e.g /shared
	 * @param pathToRebindXml path including name to the data source xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void generateRebindXML(String serverId, String startPath, String pathToRebindXML, String pathToServersXML) throws CompositeException;
	
	/**
	 * Changes binding of resources on a server.
	 * Both the old and new resources must exist.
	 * 
	 * @param serverId target server config name
	 * @param rebindIds list of rebind Ids(comma separated)
	 * @param pathToRebindXml The path to the rebinds XML
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void rebindResources(String serverId, String rebindIds, String pathToRebindXml, String pathToServersXML) throws CompositeException;

	/**
	 * Does a textual rebind of all resources within a folder tree.
	 * The textual rebind does not require the resources to exist.
	 * 
	 * @param serverId target server config name
	 * @param rebindIds list of rebind Ids(comma separated)
	 * @param pathToRebindXml The path to the rebinds XML
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void rebindFolder(String serverId, String rebindIds, String pathToRebindXml, String pathToServersXML) throws CompositeException;

	
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::Regression Module--------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Generates input file for regression tests for one published datasource 
	 * on a given CIS server for a given user. The server connection information
	 * comes from servers.xml by serverId parameter, the other parameter 
	 * specifies the name of the published data source on that server. 
	 * 
	 * @param serverId   -  server Id from servers.xml
	 * @param regressionIds -  comma-separated list of the published regression identifiers to run test against
	 * @param pathToRegressionXML - path to the config file of this module
	 * @param pathToServersXML  -   path to servers.xml
	 * @throws CompositeException
	 */
	public void createRegressionInputFile(String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException;	
	 
	 /**
	  * Runs a regression test for a given CIS server. Before the test is run,
	  * the regression input file is generated for that server, containing all
	  * published views, procedures and web services for a given published datasource 
	  * on that server.
	  * This method is for tests where no manual changes of the input file are necessary,
	  * and the generated file is ready for execution right away, 
	  * so input file generation and test run are part of one step. This degree of automation 
	  * insures that the input file never gets out of date.  
	  * 
	  * @param serverId   -  server Id from servers.xml
	  * @param regressionIds -  comma-separated list of the published regression identifiers to run test against
	  * @param pathToRegressionXML - path to the config file of this module
	  * @param pathToServersXML  -   path to servers.xml
	  * @throws CompositeException
	  */
	 public void executeRegressionTest(String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException;

     /**
      * Compares the contents of two files for a given CIS server. The objective of the
      * comparison is to compare the before and after results.  This method should be
      * invoked in the context of a higher-level process where PD Tool is invoked to
      * executeRegressionTest() and generate a file.  PD Tool is invoked again at a
      * different point in time or against a different CIS server and produces another
      * file.  Then PD Tool is executed to compare the results of the two files
      * and determine if they are a match or not.  The results of this are output
      * to a result file.  The result of each regression comparison is either SUCCESS when 
      * the files match or FAILURE when the files do not match.
      * 
      * @param serverId   -  server Id from servers.xml
      * @param regressionIds -  comma-separated list of the regression identifiers to execute the file comparison
      * @param pathToRegressionXML - path to the config file of this module
      * @param pathToServersXML  -   path to servers.xml
      * @throws CompositeException
      */
    public void compareRegressionFiles(String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException;

	/** 
	 * Compare the Query Execution log files for two separate execution runs.
	 * Determine if queries executed in for two similar but separate tests are within the acceptable delta level.
	 * Compare each similar result duration and apply a +- delta level to see if it falls within the acceptable range.
	 * 
      * @param serverId   -  server Id from servers.xml
      * @param regressionIds -  comma-separated list of the regression identifiers to execute the log comparison
      * @param pathToRegressionXML - path to the config file of this module
      * @param pathToServersXML  -   path to servers.xml
      * @throws CompositeException
	 */
	public void compareRegressionLogs(String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException;

	/**
	 * executePerformanceTest - execute a performance test
	 * 
     * @param serverId   -  server Id from servers.xml
     * @param regressionIds -  comma-separated list of the regression identifiers to execute the log comparison
     * @param pathToRegressionXML - path to the config file of this module
     * @param pathToServersXML  -   path to servers.xml
	 * @throws CompositeException
	 */
	public void executePerformanceTest( String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException;

	/**
	 * executeSecurityTest - execute a security test
	 * 
     * @param serverId   -  server Id from servers.xml
     * @param regressionIds -  comma-separated list of the regression identifiers to execute the log comparison
     * @param pathToRegressionXML - path to the config file of this module
     * @param pathToServersXML  -   path to servers.xml
	 * @throws CompositeException
	 */
	public void executeSecurityTest( String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException;
	
	/**
	 * Generates the Regression Security XML section of the RegressionModule.xml.
	 *   Generates Regression Security Users from the given filter applying the xml schema userMode=[NOEXEC|OVERWRITE|APPEND].
	 *   Generates Regression Security Queries from the given filter applying the xml schema queryMode=[NOEXEC|OVERWRITE|APPEND].
	 *   Generates a Cartesian product for the Regression Security Plans applying the xml schema planMode=[NOEXEC|OVERWRITE|APPEND] 
	 *     and planModeType=[SINGLEPLAN|MULTIPLAN].
	 *   Generates to a different RegressionModule.xml file than the source file so that formatting can be maintained in the XML in the source.
	 *     This is based on the xml schema pathToTargetRegressionXML.
	 *   It is recommended that the users copy the results out of the target, generated file and paste into the source file as needed.
	 *   A Cartesian product is where each user contains an execution for all of the queries. 
	 *   A security plan is as follows:
	 *     A security plan consists of executing all queries for a single user.
	 *     A Cartesian product involves creating a plan for each user with all queries.
	 * 
	 * @param serverId     				server Id from servers.xml
	 * @param regressionIds       		comma-separated list of the published regression identifiers to run test against
	 * @param pathToSourceRegressionXML path to the source configuration file for the regression module.  Provides a way of maintaining existing files without overwriting.
	 * @param pathToServersXML     		path to servers.xml
	 * @throws CompositeException
	 */
	public void generateRegressionSecurityXML(String serverId, String regressionIds, String pathToSourceRegressionXML, String pathToServersXML) throws CompositeException; 

	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::Resource Module--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Execute published procedures associated with passed in procedure ids in resource xml along with passed in arguments and
	 * arguments should be passed in the format 'arg1','arg2','arg3'....
	 * @param serverId target server id from servers config xml
	 * @param procedureIds procedure ids in the resourceXML
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if the execution of the procedure fails
	 */
	public void executeConfiguredProcedures(String serverId, String proceduresId, String pathToResourceXML, String pathToServersXML) throws CompositeException;
	
	/**
	 * Execute a published procedure associated with passed in name along with passed in arguments and
	 * arguments should be passed in the format 'arg1','arg2','arg3'....
	 * @param serverId target server id from servers config xml
	 * @param procedureName published procedure name
	 * @param dataServiceName data service name (equivalent to schema name)
	 * @param pathToServersXML path to the server values xml
	 * @param arguments string with arguments in the format 'arg1','arg2','arg3'....
	 * @throws CompositeException if the execution of the procedure fails
	 */
	public void executeProcedure(String serverId, String procedureName, String dataServiceName, String pathToServersXML, String arguments) throws CompositeException;

	/**
	 * Execute a published procedure that outputs the return variable values to the log.
	 * The executed procdure is associated with passed in name along with passed in arguments and
	 * arguments should be passed in the format 'arg1','arg2','arg3'....
	 * @param serverId target server config name
	 * @param procedureName published procedure name
	 * @param dataServiceName data service name (equivalent to schema name)
	 * @param pathToServersXML path to the server values xml
	 * @param arguments string with arguments in the format 'arg1','arg2','arg3'....
	 * @param outputReturnVariables true=(default) output the values of the return variables from the procedure call, false=do not output the return variable values.
	 * @throws CompositeException if the execution of the procedure fails
	 */
	public void executeProcedure(String serverId,String procedureName, String dataServiceName, String pathToServersXML, String arguments, String outputReturnVariables) throws CompositeException;

	/**
	 * Delete a resource associated with passed in resource path 
	 * @param serverId target server id from servers config xml
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if deletion of the resource fails
	 */
	public void deleteResource(String serverId, String resourcePath, String pathToServersXML) throws CompositeException;

	/**
	 * Delete  resources associated with passed in resource id from resource xml 
	 * @param serverId target server id from servers config xml
	 * @param resourceIds comma separated list of resource ids from the resource xml
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if deletion of the resource fails
	 */
	public void deleteResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * Rename a resource associated with the passed inpath
	 * @param serverId target server id from servers config xml
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @param newName the new name of the resource (this is not a path)
	 * @throws CompositeException if rename of the resource fails
	 */
	void renameResource(String serverId, String resourcePath, String pathToServersXML, String newName) throws CompositeException;

	/**
	 * Rename resources associated with passed in resource id from resource xml 
	 * @param serverId target server id from servers config xml
	 * @param resourceIds comma separated list of resource ids from the resource xml
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if rename of the resource fails
	 */
	public void renameResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException;
	
	/**
	 * Copy a resource associated with passed in resource path, target container path and new name
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @param targetContainerPath the target CIS folder path to copy the resource to
	 * @param newName the new name of the resource being copied
	 * @param copyMode the mode by which a copy is to be executed
	 * @throws CompositeException if resource copy fails
	 */
	void copyResource(String serverId, String resourcePath, String pathToServersXML, String targetContainerPath, String newName, String copyMode) throws CompositeException;

	/**
	 * Copy a resource associated with the passed in resource ids and path
	 * @param serverId target server config name
	 * @param resourceIds comma separated list of resource ids from the resource xml
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if resource copy fails
	*/
	void copyResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * Move a resource associated with passed in resource path, target container path and new name 
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @param targetContainerPath the target CIS folder path to copy the resource to
	 * @param newName the new name of the resource being copied
	 * @throws CompositeException if resource move fails
	 */
	void moveResource(String serverId, String resourcePath, String pathToServersXML, String targetContainerPath, String newName) throws CompositeException;

	/**
	 * Move a resource associated with the passed in resource ids and path
	 * @param serverId target server config name
	 * @param resourceIds comma separated list of resource ids from the resource xml
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if resource move fails
	*/
	void moveResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * Checks for existence of a resource associated with passed in resource path
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param resourceType resource type here is the list of valid resource types 
	 * @param pathToServersXML path to the server values xml
	 * @return true if the resource exists else false
	 */
	public boolean resourceExists(String serverId, String resourcePath, String resourceType, String pathToServersXML);

	/**
	 * Checks for existence of a resource associated with passed in resource path
	 * @param serverId target server id from servers config xml
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @return true if the resource exists else false
	 */
	public boolean doResourceExist(String serverId, String resourcePath, String pathToServersXML);

	/**
	 * Checks for existence of a resources associated with passed in resource ids
	 * @param serverId target server id from servers config xml
	 * @param resourceIds resource ids from the resource xml
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if resource cannot be found
	 */
	public void doResourcesExist(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * Lock a resource associated with the passed in resource path
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if resource cannot be found
	 */
	void lockResource(String serverId, String resourcePath, String pathToServersXML) throws CompositeException;

	/**
	 * Lock a resource associated with the passed in resource ids and path
	 * @param serverId target server config name
	 * @param resourceIds comma separated list of resource ids from the resource xml
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	void lockResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * Unlock a resource associated with the passed in resource path
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @param comment description/comment for the unlock action
	 * @throws CompositeException
	 */
	void unlockResource(String serverId, String resourcePath, String pathToServersXML, String comment) throws CompositeException;

	/**
	 * Unlock a resource associated with the passed in resource ids and path
	 * @param serverId target server config name
	 * @param resourceIds comma separated list of resource ids from the resource xml
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	void unlockResources(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException;

	/**
	 * Get all resources of passed in resource type from passed in resource path, this method traverses the resource tree from the 
	 * starting resource path and builds a resource list of passed in resource type
	 * @param serverId target server id from servers config xml
	 * @param resourcePath starting resource path if no resource path is passed /shared is defaulted to resource path
	 * @param resourceType type of resource for resourcePath
	 * @param resourceTypeFilterString resource type here is the list of valid resource types 
	 * CONTAINER,DATA_SOURCE,DEFINITION_SET,LINK,PROCEDURE,TABLE,TREE,TRIGGER
	 * if resourceTypeFilterString is not passed then all resources are returned
	 * @param detail Level resource detail Level here is the list of valid detail levels 
	 * FULL, SMIPLE, NONE. if detail level is not passed NONE is defaulted to detail level
	 * @param pathToServersXML path to the server values xml
	 * @return resource list with all resources of passed in resource type in the resource tree from the starting resource path
	 */	
	public ResourceList getResourcesFromPath(String serverId, String resourcePath, String resourceType, String resourceTypeFilterString, String detailLevel, String pathToServersXML);
	
	/**
	 * Get all resources of passed in resource type from passed in resource path, this method builds resource list
	 * starting resource path and builds a resource list of passed in resource type
	 * @param serverId target server config name
	 * @param resourcePath starting resource path if no resource path is passed /shared is defaulted to resource path
	 * @param resourceType resource type here is the list of valid resource types 
	 * CONTAINER,DATA_SOURCE,DEFINITION_SET,LINK,PROCEDURE,TABLE,TREE,TRIGGER
	 * if resourceType is not passed then all resources are returned
	 * @param detail Level resource detail Level here is the list of valid detail levels 
	 * FULL, SMIPLE, NONE. if detail level is not passed NONE is defaulted to detail level
	 * @param pathToServersXML path to the server values xml
	 * @return resource list with immediate resources of passed in resource type in the resource tree from the starting resource path
	 */	
	public ResourceList getImmediateResourcesFromPath(String serverId, String resourcePath, String resourceType, String detailLevel, String pathToServersXML);	

	/**
	 * Create all folders in the path associated with passed in resource path.
	 * @param serverId target server config name
	 * @param resourcePath resource path
	 * @param pathToServersXML path to the server values xml
	 * @param recursive false=only create the folder specified, true=create all folders recursively
	 * @throws CompositeException if resource create folder fails (if intermediate folders do not exist and recursive=0 an exception is thrown)
	 */
	void createFolder(String serverId, String resourcePath, String pathToServersXML, String recursive) throws CompositeException;

	/**
	 * Create all folders in the path associated with the passed in resource ids.
	 * @param serverId target server config name
	 * @param resourceIds comma separated list of resource ids from the resource xml
	 * @param pathToResourceXML path to the resource xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if resource move fails
	*/
	void createFolders(String serverId, String resourceIds, String pathToResourceXML, String pathToServersXML) throws CompositeException;


	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::ResourceCache Module---------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Update Resource Cache method updates CIS server with cache configuration for a given set of resource ids
	 * @param serverId - target server id from server XML configuration file
	 * @param resourceIds - list of resource cache Ids (comma separated list of Ids)
	 * @param pathToResourceCacheXML - path to the resource cache module XML configuration file.
	 * @param pathToServersXML - path to the server XML configuration file.
	 * @throws CompositeException
	 */
	public void updateResourceCache(String serverId, String resourceIds, String pathToResourceCacheXML, String pathToServersXML)  throws CompositeException;

	/**
	 * Clear Resource Cache method clears the cache for a given set of resource ids
	 * @param serverId - target server id from server XML configuration file
	 * @param resourceIds - list of resource cache Ids (comma separated list of Ids)
	 * @param pathToResourceCacheXML - path to the resource cache module XML configuration file.
	 * @param pathToServersXML - path to the server XML configuration file.
	 * @throws CompositeException
	 */
	public void clearResourceCache(String serverId, String resourceIds, String pathToResourceCacheXML, String pathToServersXML)  throws CompositeException;

	/**
	 * Refresh Resource Cache method refreshes the cache for a given set of resource ids
	 * @param serverId - target server id from server XML configuration file
	 * @param resourceIds - list of resource cache Ids (comma separated list of Ids)
	 * @param pathToResourceCacheXML - path to the resource cache module XML configuration file.
	 * @param pathToServersXML - path to the server XML configuration file.
	 * @throws CompositeException
	 */
	public void refreshResourceCache(String serverId, String resourceIds, String pathToResourceCacheXML, String pathToServersXML)  throws CompositeException;

	/**
	 * Enable/Disable Resource Cache method updates CIS server with for a given set of resource ids
	 * @param serverId - target server id from server XML configuration file
	 * @param resourceIds - list of resource cache Ids (comma separated list of Ids)
	 * @param pathToResourceCacheXML - path to the resource cache module XML configuration file.
	 * @param pathToServersXML - path to the server XML configuration file.
	 * @throws CompositeException
	 */
	public void updateResourceCacheEnabled(String serverId, String resourceIds, String pathToResourceCacheXML, String pathToServersXML)  throws CompositeException;

	/**
	 * Generate a file containing all the resource cache configurations for a given path
	 * @param serverId - target server id from server XML configuration file
	 * @param startPath - starting path for the resources to generate
	 * @param pathToResourceCacheXML - path including name to the resource cache XML which will be generated
	 * @param pathToServersXML - path to the server XML configuration file.
	 * @param options - space or comma separated list of options [CONFIGURED NONCONFIGURED TABLE PROCEDURE]. 
	 *     If left blank, the default is to produce an XML for all tables and procedures whether they are cached configured or not
	 *     CONFIGURED - generate XML for cache configured resources
	 *     NONCONFIGURED - generate XML for non-configured cache resources
	 *     TABLE - generate XML for TABLE (VIEW)
	 *     PROCEDURE - generate XML for PROCEDURE
	 * @throws CompositeException
	 */
	public void generateResourceCacheXML(String serverId, String startPath, String pathToResourceCacheXML, String pathToServersXML, String options) throws CompositeException;
	

	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::ServerAttribute Module-------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Update Server Attributes method updates the server attribute configurations for the passed in 
	 * server attribute Ids list found in the the passed in ServerAttributeModule.xml file for the 
	 * target server Id.  
	 * Note: An exception will be thrown if an attribute is attempted to be udpated that is defined with an update rule of READ_ONLY.
	 * @param serverId - target server id from server XML configuration file
	 * @param serverAttributeIds - list of server attributes Ids (comma separated  server attributes Ids)
	 * @param pathToServerAttributesXML - path to the server attribute module XML configuration file.
	 * @param pathToServersXML - path to the server XML configuration file.
	 * @throws CompositeException
	 */
	public void updateServerAttributes(String serverId, String serverAttributeIds, String pathToServerAttributesXML, String pathToServersXML)  throws CompositeException;

	/**
	 * Generate a file containing all the server attributes for a given starting path and a given update rule.
	 *  /cms - Composite Change Management Service configuration attributes
	 *  /discovery - Composite Discovery configuration attributes
	 *  /monitor - Composite Monitor configuration attributes
	 *  /server - Composite Information Server configuration attributes
	 *  /sources - Composite Data Sources configuration attributes
	 *  /studio - Composite Studio configuration attributes
	 * @param serverId - target server id from server XML configuration file
	 * @param startPath - starting path of the server attribute folder e.g /server
	 * @param pathToServerAttributeXML - path including name to the server attributes XML which will be generated
	 * @param pathToServersXML - path to the server XML configuration file.
	 * @param updateRule - the type of rule described by the attribute definition
	 * 		READ_ONLY - only get attributes where updateRule=READ_ONLY
	 *      READ_WRITE - only get attributes where updateRule=READ_WRITE (this should be considered the default behavior because you cannot update rules that are READ_ONLY)
	 *      * - get all attributes
	 * @throws CompositeException
	 */
	public void generateServerAttributesXML(String serverId, String startPath, String pathToServerAttributeXML, String pathToServersXML, String updateRule) throws CompositeException;

	/**
	 * Generate a file containing all the server attribute definitions for a given starting path and a given update rule.
	 *  /cms - Composite Change Management Service configuration attributes
	 *  /discovery - Composite Discovery configuration attributes
	 *  /monitor - Composite Monitor configuration attributes
	 *  /server - Composite Information Server configuration attributes
	 *  /sources - Composite Data Sources configuration attributes
	 *  /studio - Composite Studio configuration attributes
	 * @param serverId - target server id from server XML configuration file
	 * @param startPath - starting path of the server attribute folder e.g /server
	 * @param pathToServerAttributeXML - path including name to the server attribute defintions XML which will be generated
	 * @param pathToServersXML - path to the server XML configuration file.
	 * @param updateRule - the type of rule described by the attribute definition
	 * 		READ_ONLY - only get attribute definitions where updateRule=READ_ONLY
	 *      READ_WRITE - only get attribute definitions where updateRule=READ_WRITE
	 *      * - get all attribute definitions
	 * @throws CompositeException
	 */
	public void generateServerAttributeDefinitionsXML(String serverId, String startPath, String pathToServerAttributeXML, String pathToServersXML, String updateRule) throws CompositeException;


	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::ServerManager Module---------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Start method starts specified server  
	 * @param serverId target server id from servers config xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void startServer(String serverId, String pathToServersXML) throws CompositeException;
	
	/**
	 * Stop method stops specified server  
	 * @param serverId target server id from servers config xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void stopServer(String serverId, String pathToServersXML) throws CompositeException;
	
	/**
	 * Restart method restarts specified server  
	 * @param serverId target server id from servers config xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */	
	public void restartServer(String serverId, String pathToServersXML) throws CompositeException;

	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::User Module------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Create CIS users. If they already exist, update them instead. 
	 * @param serverId target server id from servers config xml
	 * @param userIds list of user names/Ids (comma separated)
	 * @param pathToUsersXML path to the users xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void createOrUpdateUsers(String serverId, String userIds, String pathToUsersXML, String pathToServersXML) throws CompositeException;

	/**
	 * Delete CIS users from a specified domain.
	 * @param serverId target server id from servers config xml
	 * @param userIds list of user names/Ids (comma separated)
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void deleteUsers(String serverId, String userIds, String pathToUsersXML, String pathToServersXML) throws CompositeException;

	/**
	 * Export existing CIS users to a XML file based on the list of passed in user ids and server id
	 * @param serverId target server id from servers config xml
	 * @param domainName domainName from which to get all users.  If null then get all users from all domains.
	 * @param pathToUsersXML path including name to the users xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void generateUsersXML(String serverId, String domainName, String pathToUsersXML, String pathToServersXML) throws CompositeException;


	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::Trigger Module---------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Update Trigger method updates trigger configurations for the passed in 
	 * trigger Ids list found in the the passed in triggers.xml file for the 
	 * target server Id 
	 * @param serverId target server id from servers config xml
	 * @param triggerIds list of data sources Ids(comma separated trigger Ids)
	 * @param pathToTriggersXML path to the trigger xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void updateTriggers(String serverId, String triggerIds, String pathToTriggersXML, String pathToServersXML) throws CompositeException;

	/**
	 * Enable Trigger method enables triggers for the passed in trigger ids list found 
	 * in the the passed in triggers.xml file for the 
	 * target server Id 
	 * @param serverId target server id from servers config xml
	 * @param triggerIds list of trigger Ids(comma separated trigger Ids)
	 * @param pathToTriggersXML path to the trigger xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void enableTriggers(String serverId, String triggerIds, String pathToTriggersXML, String pathToServersXML) throws CompositeException;

	/**
	 * Create existing CIS triggers  to an XML file from the server associated with passed in target server id
	 * @param serverId target server id from servers config xml
	 * @param startPath namespace location at which to start recursive search for trigger resources
	 * @param pathToTriggersXML path including name to the groups xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void generateTriggersXML(String serverId, String startPath, String pathToTriggersXML, String pathToServersXML) throws CompositeException;

		
	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::VCS Module-------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	/*******************************************************
	 * 
	 * PDTOOL VCS INTEGRATION METHODS
	 *
	 *******************************************************/
	/**
	 * Initialize the VCS local workspace on the deployment server by linking a local folder with a VCS repository project
	 * and checking out all the resources from the VCS repository into the local workspace folder.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsInitWorkspace(String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Initialize the VCS local workspace on the deployment server by linking a local folder with a VCS repository project
	 * and checking out all the resources from the VCS repository into the local workspace folder.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param vcsConnectionId - VCS Connection property information
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	public void vcsInitWorkspace2(String vcsConnectionId, String pathToVcsXML, String vcsUser, String vcsPassword) throws CompositeException;	

	/**
	 * Initialize base folder checkin from the local workspace to the VCS repository.  This provides an alternative way to establish the Composite repository base folders
	 * into the VCS repository without checking in the entire Composite repository.  This can be useful for multi-tenant environments where only certain folders will be
	 * held under version control.  The issue is that all the base-level folders must first be checked in into prior to any user-level folders being checked in.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param customPathList - a comma separated list of paths that are added to the base paths of /shared or /services/databases or /services/webservices 
	 *                         these paths and their corresponding .cmf file will be created during initialization of the workspace and vcs repository. 
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsInitializeBaseFolderCheckin(String customPathList, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Initialize base folder checkin from the local workspace to the VCS repository.  This provides an alternative way to establish the Composite repository base folders
	 * into the VCS repository without checking in the entire Composite repository.  This can be useful for multi-tenant environments where only certain folders will be
	 * held under version control.  The issue is that all the base-level folders must first be checked in into prior to any user-level folders being checked in.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param vcsConnectionId - VCS Connection property information
	 * @param customPathList - a comma separated list of paths that are added to the base paths of /shared or /services/databases or /services/webservices 
	 *                         these paths and their corresponding .cmf file will be created during initialization of the workspace and vcs repository. 
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsInitializeBaseFolderCheckin2(String vcsConnectionId, String customPathList, String pathToVcsXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Checkout changes from the repository and import the differences into the CIS server.
	 * If folders are present in CIS that are not present in the repository, those folders will be deleted in CIS.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsResourcePath - the actual CIS resource path (not encoded)
	 * @param vcsResourceType - the resource type
	 * @param vcsRevision - the revision from the VCS (HEAD or an integer value representing the revision number.)
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsCheckout(String serverId, String vcsResourcePath, String vcsResourceType, String vcsRevision, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * This method contains an additional parameter "vcsLabel", therefore resourcePath and resourceType are left null.
	 * 
	 * Checkout changes from the repository and import the differences into the CIS server.
	 * If folders are present in CIS that are not present in the repository, those folders will be deleted in CIS.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsResourcePath - Needs to be left blank using double quotes - ""
	 * @param vcsResourceType - Needs to be left blank using double quotes - ""
	 * @param vcsLabel - the VCS label that associates an entire release of CIS resources together.  
	 *                   If not null, then vcsResourcePath and vcsResourceType must be null otherwise vcsResourcePath takes precedence.
	 * @param vcsRevision - the revision from the VCS (HEAD or an integer value representing the revision number.)
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsCheckout(String serverId, String vcsResourcePath, String vcsResourceType,  String vcsLabel, String vcsRevision, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Checkout changes from the repository and import the differences into the CIS server.
	 * If folders are present in CIS that are not present in the repository, those folders will be deleted in CIS.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsResourcePath - the actual CIS resource path (not encoded)
	 * @param vcsResourceType - the resource type
	 * @param vcsRevision - the revision from the VCS (HEAD or an integer value representing the revision number.)
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	void vcsCheckout2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String vcsRevision, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;


	/**
	 * This method contains an additional parameter "vcsLabel", therefore resourcePath and resourceType are left null.
	 * 
	 * Checkout changes from the repository and import the differences into the CIS server.
	 * If folders are present in CIS that are not present in the repository, those folders will be deleted in CIS.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsResourcePath - the actual CIS resource path (not encoded)
	 * @param vcsResourceType - the resource type
	 * @param vcsLabel - the VCS label that associates an entire release of CIS resources together.  
	 *                   If not null, then vcsResourcePath and vcsResourceType must be null otherwise vcsResourcePath takes precedence.
	 * @param vcsRevision - the revision from the VCS (HEAD or an integer value representing the revision number.)
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	void vcsCheckout2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String vcsLabel, String vcsRevision, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;


	/**
	 * References multiple vcsIds from the VCSModule.xml to checkout changes from the repository and import the differences into the CIS server.
	 * If folders are present in CIS that are not present in the repository, those folders will be deleted in CIS.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsIds - comma separated list of VCS identifiers.
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsCheckouts(String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;
	
	/**
	 * References multiple vcsIds from the VCSModule.xml to checkout changes from the repository and import the differences into the CIS server.
	 * If folders are present in CIS that are not present in the repository, those folders will be deleted in CIS.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsIds - comma separated list of VCS identifiers.
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	void vcsCheckouts2(String serverId, String vcsConnectionId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Checkin the changes from the local workspace to the VCS repository.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsResourcePath - the actual CIS resource path (not encoded)
	 * @param vcsResourceType - the resource type
	 * @param vcsMessage - the message that describes the checkin
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsCheckin(String serverId, String vcsResourcePath, String vcsResourceType, String vcsMessage, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Checkin the changes from the local workspace to the VCS repository.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsResourcePath - the actual CIS resource path (not encoded)
	 * @param vcsResourceType - the resource type
	 * @param vcsMessage - the message that describes the checkin
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	void vcsCheckin2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String vcsMessage, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * References multiple vcsIds in the VCSModule.xml to checkin the changes from the local workspace to the VCS repository.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsIds - comma separated list of VCS identifiers.
	 * @param vcsMessage - the message that describes the checkin
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsCheckins(String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * References multiple vcsIds in the VCSModule.xml to checkin the changes from the local workspace to the VCS repository.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsIds - comma separated list of VCS identifiers.
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	void vcsCheckins2(String serverId, String vcsConnectionId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Forced checkin based on what is in the local workspace for CIS vs. the repository.  
	 * Overwrite differences in the VCS repository favor of what is in the CIS server. 
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsResourcePath - the actual CIS resource path (not encoded)
	 * @param vcsResourceType - the resource type
	 * @param vcsMessage - the message that describes the checkin
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsForcedCheckin(String serverId, String vcsResourcePath, String vcsResourceType, String vcsMessage, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Forced checkin based on what is in the local workspace for CIS vs. the repository.  
	 * Overwrite differences in the VCS repository favor of what is in the CIS server. 
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsResourcePath - the actual CIS resource path (not encoded)
	 * @param vcsResourceType - the resource type
	 * @param vcsMessage - the message that describes the checkin
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	void vcsForcedCheckin2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String vcsMessage, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * References multiple vcsIds in the VCSModule.xml to force checkin based on what is in the local workspace for CIS vs. the repository.  
	 * Overwrite differences in the VCS repository favor of what is in the CIS server. 
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsIds - comma separated list of VCS identifiers.
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsForcedCheckins(String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * References multiple vcsIds in the VCSModule.xml to force checkin based on what is in the local workspace for CIS vs. the repository.  
	 * Overwrite differences in the VCS repository favor of what is in the CIS server. 
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsIds - comma separated list of VCS identifiers.
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	void vcsForcedCheckins2(String serverId, String vcsConnectionId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Prepare checkin by updating the local workspace copy and comparing with VCS but don't checkin.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsResourcePath - comma separated list of VCS identifiers.
	 * @param vcsResourceType - the resource type
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsPrepareCheckin(String serverId, String vcsResourcePath, String vcsResourceType, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Prepare checkin by updating the local workspace copy and comparing with VCS but don't checkin.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsResourcePath - comma separated list of VCS identifiers.
	 * @param vcsResourceType - the resource type
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	void vcsPrepareCheckin2(String serverId, String vcsConnectionId, String vcsResourcePath, String vcsResourceType, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * References multiple vcsIds in the VCSModule.xml to prepare checkin by updating the local workspace copy and comparing with VCS but don't checkin.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsIds - comma separated list of VCS identifiers.
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsPrepareCheckins(String serverId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * References multiple vcsIds in the VCSModule.xml to prepare checkin by updating the local workspace copy and comparing with VCS but don't checkin.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 * 
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information
	 * @param vcsIds - comma separated list of VCS identifiers.
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @throws CompositeException
	 */
	void vcsPrepareCheckins2(String serverId, String vcsConnectionId, String vcsIds, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;	

	/**
	 * Generate a VCS Module XML
	 *
	 * @param serverId target server id from servers config xml
	 * @param startPath starting path of the resource e.g /shared
	 * @param pathToDataSourceXML path including name to the VCS source xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void generateVCSXML(String serverId, String startPath, String pathToVCSXML, String pathToServersXML) throws CompositeException;

	/**
	 * Generate a VCS Module XML
	 *
	 * @param serverId target server id from servers config xml
	 * @param vcsConnectionId - VCS Connection property information
	 * @param startPath starting path of the resource e.g /shared
	 * @param pathToDataSourceXML path including name to the VCS source xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	void generateVCSXML2(String serverId, String vcsConnectionId, String startPath, String pathToVcsXML, String pathToServersXML) throws CompositeException;

	
	/*******************************************************
	 * 
	 * PDTOOL STUDIO VCS INTEGRATION METHODS
	 *
	 *******************************************************/
	/**
	 * Initialize the VCS local workspace on the deployment server by linking a local folder with a VCS repository project
	 * and checking out all the resources from the VCS repository into the local workspace folder.
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */	
	public void vcsStudioInitWorkspace(String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Initialize base folder checkin from the local workspace to the VCS repository.  This provides an alternative way to establish the Composite repository base folders
	 * into the VCS repository without checking in the entire Composite repository.  This can be useful for multi-tenant environments where only certain folders will be
	 * held under version control.  The issue is that all the base-level folders must first be checked in into prior to any user-level folders being checked in.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 * 
	 * @param customPathList - a comma separated list of paths that are added to the base paths of /shared or /services/databases or /services/webservices 
	 *                         these paths and their corresponding .cmf file will be created during initialization of the workspace and vcs repository. 
	 * @param vcsCheckinOptions - a space separated list of check-in options that are put on the VCS command line at the time of execution.  
	 * 							  However, if a value exists in the studio.properties file, these values are not used. 
	 * @param vcsUser - the VCS user passed in from the command line
	 * @param vcsPassword - the VCS user passed in from the command line
	 * @return void
	 * @throws CompositeException
	 */	
	public void vcsStudioInitializeBaseFolderCheckin(String customPathList, String vcsCheckinOptions, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 * Composite Studio integrates with vcsStudioCheckout to checkout the changes from the repository and import the differences into the CIS server.
	 * If folders are present in CIS that are not present in the repository, those folders will be deleted in CIS.
	 * 
	 * @param resourcePath - CIS resource path 				(e.g. /shared/MyFolder/My__View), using file system (encoded) names
	 * @param resourceType - CIS resource type 				(e.g. FOLDER, table, procedure etc.)
	 * @param revision - checkout revision					(e.g. HEAD or 3177)
	 * @param vcsWorkspace - path to the workspace folder 	(e.g. C:\Temp\workspaces\workspace_CIS)
	 * @param vcsWorkspaceTemp - path to the workspace temp (e.g. C:\Temp\workspaces\temp_CIS)
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsStudioCheckout(String resourcePath, String resourceType, String revision, String vcsWorkspace, String vcsWorkspaceTemp) throws CompositeException;
	
	/**
	 * Composite Studio integrates with vcsStudioCheckin to checkin the changes from the local Studio workspace to the VCS repository.
	 * 
	 * @param resourcePath - CIS resource path 				(e.g. /shared/MyFolder/My__View), using file system (encoded) names
	 * @param resourceType - CIS resource type 				(e.g. FOLDER, table, procedure etc.)
	 * @param message - checkin message						(e.g. Adding My View)
	 * @param vcsWorkspace - path to the workspace folder 	(e.g. C:\Temp\workspaces\workspace_CIS)
	 * @param vcsWorkspaceTemp - path to the workspace temp (e.g. C:\Temp\workspaces\temp_CIS)
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsStudioCheckin(String resourcePath, String resourceType, String message, String vcsWorkspace, String vcsWorkspaceTemp) throws CompositeException;

	/**
	 * Composite Studio integrates with vcsStudioForcedCheckin to force a checkin of the changes from the local Studio workspace to the VCS repository.
	 * Force checkin based on what is in the local workspace for CIS vs. the repository.  
	 * Overwrite differences in the VCS repository favor of what is in the CIS server. 
	 * 
	 * @param resourcePath - CIS resource path 				(e.g. /shared/MyFolder/My__View), using file system (encoded) names
	 * @param resourceType - CIS resource type 				(e.g. FOLDER, table, procedure etc.)
	 * @param message - checkin message						(e.g. Adding My View)
	 * @param vcsWorkspace - path to the workspace folder 	(e.g. C:\Temp\workspaces\workspace_CIS)
	 * @param vcsWorkspaceTemp - path to the workspace temp (e.g. C:\Temp\workspaces\temp_CIS)
	 * @return void
	 * @throws CompositeException
	 */
	public void vcsStudioForcedCheckin(String resourcePath, String resourceType, String message, String vcsWorkspace, String vcsWorkspaceTemp) throws CompositeException;

	/**
	 *  This method handles scanning the Composite path and searching for encoded paths
	 *  that equal or exceed the windows 259 character limit.  If found this routine reports those paths.
	 *  The 259 character limit is only a limitation for windows-based implementations of VCS
	 *  like TFS.  Subversion does not have this issue.
	 * 
	 * This method uses the deployment configuration property file "deploy.properties" for VCS connection properties.
	 *  
	 * @param serverId - target server name
	 * @param vcsMaxPathLength - a positive integer length from which to compare path lengths found in vcsResourcePathList.  When 0, use the default CommonConstants.maxWindowsPathLen=259.
	 * @param vcsResourcePathList -  a comma separated list of CIS resource paths to scan
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * 			[Optional parameter when values are set in studio.properties, deploy.properties or VCSModule.xml.  pass in null.]
	 * @param vcsPassword - the VCS user passed in from the command line
	 * 			[Optional parameter when values are set in studio.properties, deploy.properties or VCSModule.xml.  pass in null.]
	 * @throws CompositeException
	 */
	public void vcsScanPathLength(String serverId, String vcsMaxPathLength, String vcsResourcePathList, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	/**
	 *  This method handles scanning the Composite path and searching for encoded paths
	 *  that equal or exceed the windows 259 character limit.  If found this routine reports those paths.
	 *  The 259 character limit is only a limitation for windows-based implementations of VCS
	 *  like TFS.  Subversion does not have this issue.
	 * 
	 * This method uses VCSModule.xml for VCS connection properties.
	 *  
	 * @param serverId - target server name
	 * @param vcsConnectionId - VCS Connection property information 
	 * @param vcsMaxPathLength - a positive integer length from which to compare path lengths found in vcsResourcePathList.  When 0, use the default CommonConstants.maxWindowsPathLen=259.
	 * @param vcsResourcePathList -  a comma separated list of CIS resource paths to scan
	 * @param pathToVcsXML - path including name to the VCS Module XML containing a list of vcsIds to execute against. 
	 * @param pathToServersXML - path to the server values XML
	 * @param vcsUser - the VCS user passed in from the command line
	 * 			[Optional parameter when values are set in studio.properties, deploy.properties or VCSModule.xml.  pass in null.]
	 * @param vcsPassword - the VCS user passed in from the command line
	 * 			[Optional parameter when values are set in studio.properties, deploy.properties or VCSModule.xml.  pass in null.]
	 * @throws CompositeException
	 */
	public void vcsScanPathLength2(String serverId, String vcsConnectionId, String vcsMaxPathLength, String vcsResourcePathList, String pathToVcsXML, String pathToServersXML, String vcsUser, String vcsPassword) throws CompositeException;

	//--------------------------------------------------------------------------------------------------------------------------------
	//--Begin::??? Module-------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	
}
