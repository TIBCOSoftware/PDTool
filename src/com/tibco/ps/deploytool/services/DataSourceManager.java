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
package com.tibco.ps.deploytool.services;

import com.tibco.ps.common.exception.CompositeException;
import com.compositesw.services.system.admin.resource.ResourceList;

public interface DataSourceManager {

	//---------------------------------------------------------------------------------------------------------
	/**
	 * Update Data Source method updates data sources configurations for the passed in 
	 * data sources Ids list found in the the passed in datasources.xml file for the 
	 * target server Id 
	 * @param serverId target server id from servers config xml
	 * @param dataSourceIds list of data sources Ids(comma separated data source Ids)
	 * @param pathToDataSourceXML path to the data source xml
	 * @param pathToServersXML path to the server values xml
	 */
	public void updateDataSources(String serverId, String dataSourceIds, String pathToDataSourceXML, String pathToServersXML);

	/**
	 * Enable Data Source method enables data sources for the passed in data source ids list found 
	 * in the the passed in datasources.xml file for the 
	 * target server Id 
	 * @param serverId target server id from servers config xml
	 * @param dataSourceIds list of data sources Ids(comma separated data source Ids)
	 * @param pathToDataSourceXML path to the data source xml
	 * @param pathToServersXML path to the server values xml
	 */
	public void enableDataSources(String serverId, String dataSourceIds, String pathToDataSourceXML, String pathToServersXML);


	/**
	 * Re-introspect Data Source method re-introspects data sources for the passed in data source ids
	 * list found in the the passed in datasources.xml file for the 
	 * target server Id 
	 * @param serverId target server id from servers config xml
	 * @param dataSourceIds list of data sources Ids(comma separated data source Ids)
	 * @param pathToDataSourceXML path to the data source xml
	 * @param pathToServersXML path to the server values xml
	 */
	public void reIntrospectDataSources(String serverId, String dataSourceIds, String pathToDataSourceXML, String pathToServersXML);

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
	 * @param resourceType type of resource for resourcePath
	 * @param childResourceType resource type here is the list of valid resource types 
	 * CONTAINER,DATA_SOURCE,DEFINITION_SET,LINK,PROCEDURE,TABLE,TREE,TRIGGER
	 * if childResourceType is not passed then all resources are returned
	 * @param detail Level resource detail Level here is the list of valid detail levels 
	 * FULL, SMIPLE, NONE. if detail level is not passed NONE is defaulted to detail level
	 * @param pathToServersXML path to the server values xml
	 * @return resource list with all child resources of passed in resource type from the data sources in the resource tree from the starting resource path
	 */	
	public ResourceList getDataSourcesChildren(String serverId,String resourcePath, String resourceType, String childResourceType, String detailLevel,String pathToServersXML);

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
	 * Generate Data Source Attribute Definitions for the passed in starting path and the target server Id.
	 *   The method allows the invoker generate a file of data source attribute definitions.  This method will be useful when the user wants to determine what
	 *   valid attributes are available for a given data source.
	 * @param serverId target server id from servers config xml
	 * @param resourcePath starting resource path if no resource path is passed /shared is defaulted to resource path
	 * @param pathToDataSourceAttrDefs path to the data attribute defintions xml
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	void generateDataSourceAttributeDefs(String serverId, String resourcePath, String pathToDataSourceAttrDefs, String pathToServersXML) throws CompositeException;

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


}
