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
package com.cisco.dvbu.ps.deploytool.dao;

import java.util.List;

import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.compositesw.services.system.admin.resource.DataSourceTypeInfo;
import com.compositesw.services.system.admin.resource.IntrospectionPlan;
import com.compositesw.services.system.admin.resource.ResourceList;
import com.compositesw.services.system.util.common.AttributeDef;
import com.compositesw.services.system.util.common.AttributeList;

public interface DataSourceDAO {

	public static enum action {UPDATE,ENABLE,REINTROSPECT,INTROSPECT};

	/**
	 * Take Data Source Action method takes  passed action on the data source with the passed in data source 
	 * attributes for the passed in target server name 
	 * @param actionName the action to be performed [UPDATE,ENABLE,REINTROSPECT,INTROSPECT]
	 * @param dataSourcePath data source path
	 * @param plan for INTROSPECT, a collection of plan entries which determine what resource to ADD, UPDATE or REMOVE
	 * @param runInBackgroundTransaction for INTROSPECT, determines whether to run the INTROSPECT in a background process or not
	 * @param reportDetail for INTROSPECT, determines what level of report to print out to the log [SUMMARY, SIMPLE, SIMPLE_COMPRESSED, FULL]
	 * @param dataSourceAttributes resource attribute list
	 * @param serverId target server config name
	 * @param pathToServersXML path to the server values xml
	 */
	public ResourceList takeDataSourceAction(String actionName, String dataSourcePath, IntrospectionPlan plan, boolean runInBackgroundTransaction, String reportDetail, AttributeList dataSourceAttributes, String serverId,String pathToServersXML) throws CompositeException;

	/**
	 * Get all child resources of passed in resource type from all datasources in passed in resource path, this method traverses the resource tree from the 
	 * starting resource path and builds a resource list of passed in resource type from all datasources that exisit in that path/tree
	 * @param serverId target server config name
	 * @param resourcePath starting resource path if no resource path is passed /shared is defaulted to resource path
	 * @param parentResourceType parent resource type here is the list of valid resource types 
	 * 		CONTAINER,DATA_SOURCE,DEFINITION_SET,LINK,PROCEDURE,TABLE,TREE,TRIGGER
	 * @param resourceTypeFilter resource type here is the list of valid resource types 
	 * CONTAINER,DATA_SOURCE,DEFINITION_SET,LINK,PROCEDURE,TABLE,TREE,TRIGGER
	 * 		if resourceTypeFilter is not passed then all resources are returned
	 * @param detail Level resource detail Level here is the list of valid detail levels 
	 * 		FULL, SMIPLE, NONE. if detail level is not passed NONE is defaulted to detail level
	 * @param pathToServersXML path to the server values xml
	 * @return resource list with all child resources of passed in resource type from the data sources in the resource tree from the starting resource path
	 */	
	public ResourceList getDataSourceChildResourcesFromPath(String serverId, String resourcePath, String parentResourceType, String resourceTypeFilter, boolean includeContainers, String detailLevel, String pathToServersXML);
	
	/**
	 * Get the attribute definitions for data sources of the given data source
	 * type. These attributes definitions are used when creating and updating
	 * data sources, such as the host and port to connect to.
	 * @param serverId
	 *            target server config name
	 * @param pathToServersXML
	 *            path to the server values xml
	 * @param dataSourceType
	 *            Valid input value is a resource:name from getDataSourceTypes
	 *            operation.
	 * @return attributeDefs
	 *            A list of attribute definitions.
	 * @throws CompositeException
	 */
	public List<AttributeDef> getDataSourceAttributeDefs(String serverId, String pathToServersXML, String dataSourceType) throws CompositeException;

	/**
	 * Get a list of the available data source types.  These data source types may be used to
	 * create new data sources.
	 * @param serverId
	 *            target server config name
	 * @param pathToServersXML
	 *            path to the server values xml
	 * @return dataSourceTypeInfoList A list of data source types known by the server
	 * @throws CompositeException
	 */
	public List<DataSourceTypeInfo> getDataSourceTypes(String serverId, String pathToServersXML) throws CompositeException;
	
}
