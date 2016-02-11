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

import java.math.BigInteger;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;

import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.exception.ValidationException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.PropertyManager;
import com.cisco.dvbu.ps.common.util.XMLUtils;
import com.cisco.dvbu.ps.deploytool.DeployManagerUtil;
import com.cisco.dvbu.ps.deploytool.dao.DataSourceDAO;
import com.cisco.dvbu.ps.deploytool.dao.ResourceDAO;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.DataSourceWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.ResourceWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.cisco.dvbu.ps.deploytool.modules.AttributeDefDataSourceType;
import com.cisco.dvbu.ps.deploytool.modules.AttributeDefEntryDataSourceType;
import com.cisco.dvbu.ps.deploytool.modules.AttributeDefType;
import com.cisco.dvbu.ps.deploytool.modules.AttributeDefsDataSourceType;
import com.cisco.dvbu.ps.deploytool.modules.AttributeTypeSimpleType;
import com.cisco.dvbu.ps.deploytool.modules.DataSourceChoiceType;
import com.cisco.dvbu.ps.deploytool.modules.DataSourceTypeType;
import com.cisco.dvbu.ps.deploytool.modules.DataSourceTypesType;
import com.cisco.dvbu.ps.deploytool.modules.DatasourceModule;
import com.cisco.dvbu.ps.deploytool.modules.GenericDataSourceType;
import com.cisco.dvbu.ps.deploytool.modules.IntrospectDataSourcePlanEntryType;
import com.cisco.dvbu.ps.deploytool.modules.ObjectFactory;
import com.cisco.dvbu.ps.deploytool.modules.RelationalDataSourceType;
import com.cisco.dvbu.ps.deploytool.modules.ResourceTypeSimpleType;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeValueArray;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeValueList;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeValueListItemType;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeValueMap;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeValueMapEntryKeyType;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeValueMapEntryType;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeValueMapEntryValueType;
import com.compositesw.services.system.admin.resource.DataSourceResource;
import com.compositesw.services.system.admin.resource.DataSourceTypeInfo;
import com.compositesw.services.system.admin.resource.IntrospectionPlan;
import com.compositesw.services.system.admin.resource.IntrospectionPlanAction;
import com.compositesw.services.system.admin.resource.IntrospectionPlanEntries;
import com.compositesw.services.system.admin.resource.IntrospectionPlanEntry;
import com.compositesw.services.system.admin.resource.PathTypeSubtype;
import com.compositesw.services.system.admin.resource.Resource;
import com.compositesw.services.system.admin.resource.ResourceList;
import com.compositesw.services.system.admin.resource.ResourceSubType;
import com.compositesw.services.system.admin.resource.ResourceType;
import com.compositesw.services.system.util.common.Attribute;
import com.compositesw.services.system.util.common.AttributeDef;
import com.compositesw.services.system.util.common.AttributeList;
import com.compositesw.services.system.util.common.AttributeSimpleValueList;
import com.compositesw.services.system.util.common.AttributeType;
import com.compositesw.services.system.util.common.AttributeTypeValue;
import com.compositesw.services.system.util.common.AttributeTypeValueList;
import com.compositesw.services.system.util.common.AttributeTypeValueMap;
import com.compositesw.services.system.util.common.AttributeTypeValueMap.Entry;
import com.compositesw.services.system.util.common.AttributeUpdateRule;
import com.compositesw.services.system.util.common.DetailLevel;

/** 
 * This class is an implementation of DataSourceManager that provides the ability to generate
 * the DataSourceManager.xml and update data sources.
 * 
 * @author rthummal
 * @param <IntrospectDataSourcePlanEntries>
 * @since 2012-06-05
 * @modified 
 * 	2013-05-08 (mtinius): added support for data source attributes with ValueArray, ValueList and ValueMap
 *  2013-08-20 (mtinius): added introspectDataSources and several generate....methods
 */

public class DataSourceManagerImpl<IntrospectDataSourcePlanEntries> implements DataSourceManager{

	private static Log logger = LogFactory.getLog(DataSourceManagerImpl.class);
	
    private DataSourceDAO dataSourceDAO = null;
    private ResourceDAO resourceDAO = null;
        
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.ModuleManager#updateDataSources(java.lang.String)
	 */
//	@Override
	public void updateDataSources(String serverId, String dataSourceIds, String pathToDataSourceXML, String pathToServersXML) throws CompositeException {

		if(logger.isDebugEnabled()){
			logger.debug(" Entering DataSourceManagerImpl.updateDataSources() with following params "+" serverId: "+serverId+", dataSourceIds: "+dataSourceIds+", pathToDataSourceXML: "+pathToDataSourceXML+", pathToServersXML: "+pathToServersXML);
		}
		dataSourceAction(DataSourceDAO.action.UPDATE.name(), serverId, dataSourceIds, pathToDataSourceXML, pathToServersXML);
	}

	private void dataSourceAction(String actionName,String serverId, String dataSourceIds, String pathToDataSourceXML, String pathToServersXML) throws CompositeException {
		ResourceList returnResList = null;

		// Set the Module Action Objective
		String s1 = (dataSourceIds == null) ? "no_Ids" : "Ids="+dataSourceIds;
		System.setProperty("MODULE_ACTION_OBJECTIVE", actionName + " : " + s1);

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToDataSourceXML)) {
			throw new CompositeException("File ["+pathToDataSourceXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		List<DataSourceChoiceType> dataSourceList = getDataSources(serverId, dataSourceIds, pathToDataSourceXML, pathToServersXML);
		if (dataSourceList != null && dataSourceList.size() > 0) {
			returnResList = new ResourceList();
		
			// Initialize variables.
			String processedIds = null;
			String identifier = null;
			String dsResPath = null;
			IntrospectionPlan plan = null;
			boolean runInBackgroundTransaction = false;
			AttributeList dsAttributeList = null;
			String reportDetail = null;
			String prefix = "dataSourceAction";
			// Get the configuration property file set in the environment with a default of deploy.properties
			String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");

			// Extract variables for the dataSourceIds
			dataSourceIds = CommonUtils.extractVariable(prefix, dataSourceIds, propertyFile, true);

			// Loop over the list of data sources and apply their attributes to the target CIS instance.
			for (DataSourceChoiceType datasource : dataSourceList) {

				if(datasource.getRelationalDataSource() != null){
				
					// Get the data source identifier and convert any $VARIABLES
					identifier = CommonUtils.extractVariable(prefix, datasource.getRelationalDataSource().getId(), propertyFile, true);
					dsResPath = CommonUtils.extractVariable(prefix, datasource.getRelationalDataSource().getResourcePath(), propertyFile, true);

				}else if(datasource.getGenericDataSource() != null){

					// Get the data source identifier and convert any $VARIABLES
					identifier = CommonUtils.extractVariable(prefix, datasource.getGenericDataSource().getId(), propertyFile, true);
					dsResPath = CommonUtils.extractVariable(prefix, datasource.getGenericDataSource().getResourcePath(), propertyFile, true);
					
				}else if(datasource.getIntrospectDataSource() != null){

					// Get the data source identifier and convert any $VARIABLES
					identifier = CommonUtils.extractVariable(prefix, datasource.getIntrospectDataSource().getId(), propertyFile, true);

					dsResPath = CommonUtils.extractVariable(prefix, datasource.getIntrospectDataSource().getResourcePath(), propertyFile, true);
					
					// Even though the user may try to set this value to true, default it to false as no processes should run in the background with PDTool.
					runInBackgroundTransaction = false;
					reportDetail = datasource.getIntrospectDataSource().getReportDetail().toString();
				}
				/**
				 * Possible values for datasources 
				 * 1. csv string like datasource1,datasource2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -datasource1,datasource2 (we ignore passed in resources and process rest of the in the input xml
				 */
				 if(DeployUtil.canProcessResource(dataSourceIds, identifier)){
					 
					if(logger.isInfoEnabled()){
						logger.info("processing action "+actionName+" on datasource "+identifier);
					}

					// Set the Module Action Objective
					s1 = (dsResPath == null) ? "no_dsResPath" : identifier+"="+dsResPath;
					System.setProperty("MODULE_ACTION_OBJECTIVE", actionName + " : " + s1);
					
					// Add to the list of processed ids
					if (processedIds == null)
						processedIds = "";
					else
						processedIds = processedIds + ",";
					processedIds = processedIds + identifier;
					
					if (actionName.equals(DataSourceDAO.action.INTROSPECT.name())) {
						dsAttributeList = populateAttributeList(datasource, serverId, pathToServersXML);					
						plan = populateIntrospectionPlan(datasource, serverId, pathToServersXML);					
						
					} else {
						dsAttributeList = populateAttributeList(datasource, serverId, pathToServersXML);					
					}
					ResourceList resourceList = getDataSourceDAO().takeDataSourceAction(actionName, dsResPath, plan, runInBackgroundTransaction, reportDetail, dsAttributeList, serverId, pathToServersXML);

					if(resourceList != null){
						returnResList.getResource().addAll(resourceList.getResource());
					}
				} 					
			}
			
			// Determine if any resourceIds were not processed and report on this
			if (processedIds != null) {
				if(logger.isInfoEnabled()){
					logger.info("Datasource entries processed="+processedIds);
				}
			} else {
				if(logger.isInfoEnabled()){
					String msg = "Warning: No datasource entries were processed for the input list.  dataSourceIds="+dataSourceIds;
					logger.info(msg);
					System.setProperty("MODULE_ACTION_MESSAGE", msg);
				}		
			}

		} else {
			if(logger.isInfoEnabled()){
				String msg = "Warning: No datasource entries were processed for the input list.  dataSourceIds="+dataSourceIds;
				logger.info(msg);
				System.setProperty("MODULE_ACTION_MESSAGE", msg);
			}
		}
	}

	private List<DataSourceChoiceType> getDataSources(String serverId, String dataSourceIds,	String pathToDataSourceXML, String pathToServersXML) {

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToDataSourceXML)) {
			throw new CompositeException("File ["+pathToDataSourceXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// validate incoming arguments
		if(serverId == null || serverId.trim().length() ==0 || dataSourceIds == null || dataSourceIds.trim().length() ==0 || pathToServersXML == null || pathToServersXML.trim().length() ==0 || pathToDataSourceXML == null || pathToDataSourceXML.trim().length() ==0){
			throw new ValidationException("Invalid Arguments");
		}

		try {
			//using jaxb convert xml to corresponding java objects
			DatasourceModule dataSourceModuleType = (DatasourceModule)XMLUtils.getModuleTypeFromXML(pathToDataSourceXML);
			if(dataSourceModuleType != null && dataSourceModuleType.getDatasource() != null && !dataSourceModuleType.getDatasource().isEmpty()){
				return dataSourceModuleType.getDatasource();
			}
		} catch (CompositeException e) {
			logger.error("Error while parsing DataSourceModule XML: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
		return null;
	}

	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.DataSourceManager#enableDataSource(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void enableDataSources(String serverId, String dataSourceIds,	String pathToDataSourceXML, String pathToServersXML) throws CompositeException {

		dataSourceAction(DataSourceDAO.action.ENABLE.name(), serverId, dataSourceIds, pathToDataSourceXML, pathToServersXML);
	}
	

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.DataSourceManager#reIntrospectDataSource(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void reIntrospectDataSources(String serverId,String dataSourceIds, String pathToDataSourceXML,String pathToServersXML) throws CompositeException {
		 dataSourceAction(DataSourceDAO.action.REINTROSPECT.name(), serverId, dataSourceIds, pathToDataSourceXML, pathToServersXML);
	}


	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.DataSourceManager#introspectDataSources(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void introspectDataSources(String serverId,String dataSourceIds, String pathToDataSourceXML,String pathToServersXML) throws CompositeException {
		 dataSourceAction(DataSourceDAO.action.INTROSPECT.name(), serverId, dataSourceIds, pathToDataSourceXML, pathToServersXML);
	}

	/*************************************************************************************
	 * populateAttributeList()
	 * 
	 * This method is used for reading the DataSourceModule.xml file and populating
	 * the internal structure for invocation of the UPDATE data source action.
	 *************************************************************************************/
	// Populate the Attribute List for Data Source Actions such as UPDATE
	private AttributeList populateAttributeList(DataSourceChoiceType datasource, String serverId, String pathToServersXML) {

		String defaultPropertyFile = CommonConstants.propertyFile;
		/*  Determine the property file name for this environment
		 *    1. Start with default file CommonConstants.propertyFile
		 *    2. Get Java Environment variables
		 *    3. Get OS System Environment variables
		 */
        String propertyFile = CommonUtils.getFileOrSystemPropertyValue(defaultPropertyFile, "CONFIG_PROPERTY_FILE");
        // Set a default value if not found
        if (propertyFile == null || propertyFile.trim().length() == 0) {
        	propertyFile = defaultPropertyFile;
        }
		String prefix = "DataSourcemanagerImpl.populateAttributeList";

		// Set up the attribute list that specify the desired config values for
		// this data source.
		AttributeList dsAttributeList = new AttributeList();
		
		if (datasource.getRelationalDataSource() != null) {

			// Get the datasource path and extract in variables in the path
			String dsResPath = CommonUtils.extractVariable(prefix, datasource.getRelationalDataSource().getResourcePath(), propertyFile, true);

			//populate attributes from relational data source
			populateAttributesFromRelationalDataSource(datasource,dsAttributeList);
			
			//populate attributes from generic attributes from relational data source
			if(datasource.getRelationalDataSource().getGenericAttribute() != null){
				

				// Get the Resource definition for this data source path
				DataSourceResource currentDataSource = (DataSourceResource) getResourceDAO().getResource(serverId, dsResPath, pathToServersXML);		
				/* Retrieve the actual Composite Data Source Type Name for the current data source by locating an attribute with name=type
				 * This is retrieved from the previous invocation to getResource() and casting the response to DataSourceResource type
				 * 
				 *	<resource:getResourceResponse xmlns:resource="http://www.compositesw.com/services/system/admin/resource">
				 *		<resource:resources>
				 *			<resource:resource xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="resource:dataSourceResource">
				 *				<resource:name>ServerAttributeDefinitions</resource:name>
				 *				<resource:path>/shared/test00/DataSources/ServerAttributeDefinitions</resource:path>
				 *				<resource:type>DATA_SOURCE</resource:type>
				 *				<resource:subtype>XML_FILE_DATA_SOURCE</resource:subtype>
				 *				...elements removed
				 *				<resource:childCount>1</resource:childCount>
				 *				
			 	 *			--> <resource:dataSourceType>File-XML</resource:dataSourceType>
				 *				
				 *			</resource:resource>
				 *		</resource:resources>
				 *	</resource:getResourceResponse>
				 */
				String dsTypeName = currentDataSource.getDataSourceType();

				// Retrieve the full list of attribute definitions and update rules for this type of data source (resource:dataSourceType)
				List<AttributeDef> attributeDefList = getDataSourceDAO().getDataSourceAttributeDefs(serverId, pathToServersXML, dsTypeName);

				// Get the list of attributes from the DataSourceModule.xml file
				List<AttributeDefType> attributeTypes = datasource.getRelationalDataSource().getGenericAttribute();
				
				// Iterate through the DataSourceModule.xml file attributes
				for (AttributeDefType attribute : attributeTypes) {

					/* Retrieve the Attribute Definition list for a given DataSource Attribute Name (e.g. WSDL, XML/HTTP, File-XML, MySQL 5.0, Oracle 11g (Thin Driver))
					 * Below is an example of an Attribute Definition for the "type=MySql".  
					 * The associated Attribute Def Type Name=MySQL 5.0 which is what is needed to pass into getAttributeDef to get the update rules.
						<resource:dataSourceType>
							<resource:name>MySQL 5.0</resource:name>
							<resource:type>MySql</resource:type>
							... <resource:attributes> removed from this documentation
							</resource:attributes>
						</resource:dataSourceType>
					 */
					// Get the Attribute Definition and Update Rules for the specific DataSource type
					AttributeDef attributeDef = getAttributeDef(attributeDefList, attribute.getName());

					// Only set the attribute if the attribute is (READ_WRITE or WRITE_ON_CREATE) and the attribute is not in the "Non-Updateable Attribute List"
					if ((attributeDef != null
							&& (attributeDef.getUpdateRule() == AttributeUpdateRule.READ_WRITE) )
							// WRITE_ON_CREATE is not allowed on updates - It will throw a null pointer exception.  This line is commented out:
//							&& (attributeDef.getUpdateRule() == AttributeUpdateRule.READ_WRITE || attributeDef.getUpdateRule() == AttributeUpdateRule.WRITE_ON_CREATE)) 
						&&	!PropertyManager.getInstance().containsPropertyValue(propertyFile, "DataSourceModule_NonUpdateableAttributes", attribute.getName())) {
												
						// Create the generic attribute structure from the DataSourceModule.xml
						Attribute genericAttribute = populateGenericAttributeForUpdate(attribute, attributeDef, prefix, propertyFile);
						if (genericAttribute != null) {
							// Add the generic attribute to the data source object
							dsAttributeList.getAttribute().add(genericAttribute);
						}
					}
				}
			}
		} else if(datasource.getGenericDataSource() != null) {

			// Get the datasource path and extract in variables in the path
			String dsResPath = CommonUtils.extractVariable(prefix, datasource.getGenericDataSource().getResourcePath(), propertyFile, true);

			//populate attributes from generic attributes from generic data source
			if (datasource.getGenericDataSource().getGenericAttribute() != null) {

				// Get the Resource definition for this data source path
				DataSourceResource currentDataSource = (DataSourceResource) getResourceDAO().getResource(serverId, dsResPath, pathToServersXML);
				/* Retrieve the actual Composite Data Source Type Name for the current data source by locating an attribute with name=type
				 * This is retrieved from the previous invocation to getResource() and casting the response to DataSourceResource type
				 * 
				 *	<resource:getResourceResponse xmlns:resource="http://www.compositesw.com/services/system/admin/resource">
				 *		<resource:resources>
				 *			<resource:resource xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="resource:dataSourceResource">
				 *				<resource:name>ServerAttributeDefinitions</resource:name>
				 *				<resource:path>/shared/test00/DataSources/ServerAttributeDefinitions</resource:path>
				 *				<resource:type>DATA_SOURCE</resource:type>
				 *				<resource:subtype>XML_FILE_DATA_SOURCE</resource:subtype>
				 *				...elements removed
				 *				<resource:childCount>1</resource:childCount>
				 *				
			 	 *			--> <resource:dataSourceType>File-XML</resource:dataSourceType>
				 *				
				 *			</resource:resource>
				 *		</resource:resources>
				 *	</resource:getResourceResponse>
				 */
				String dsTypeName = currentDataSource.getDataSourceType();

				// Retrieve the full list of attribute definitions and update rules for this type of data source (resource:dataSourceType)
				List<AttributeDef> attributeDefList = getDataSourceDAO().getDataSourceAttributeDefs(serverId, pathToServersXML, dsTypeName);
			
				// Get the list of generic attributes from the DataSourceModule.xml file
				List<AttributeDefType> attributeTypes = datasource.getGenericDataSource().getGenericAttribute();

				// Iterate through the DataSourceModule.xml file attributes
				for (AttributeDefType attribute : attributeTypes) {

					/* Retrieve the Attribute Definition list for a given DataSource Attribute Name (e.g. WSDL, XML/HTTP, File-XML, MySQL 5.0, Oracle 11g (Thin Driver))
					 * Below is an example of an Attribute Definition for the "type=MySql".  
					 * The associated Attribute Def Type Name=MySQL 5.0 which is what is needed to pass into getAttributeDef to get the update rules.
						<resource:dataSourceType>
							<resource:name>MySQL 5.0</resource:name>
							<resource:type>MySql</resource:type>
							... <resource:attributes> removed from this documentation
							</resource:attributes>
						</resource:dataSourceType>
					 */
					// Get the Attribute Definition and Update Rules for the specific DataSource type
					AttributeDef attributeDef = getAttributeDef(attributeDefList, attribute.getName());

					// Only set the attribute if the attribute is (READ_WRITE or WRITE_ON_CREATE) and the attribute is not in the "Non-Updateable Attribute List"
					if ((attributeDef != null
							&& (attributeDef.getUpdateRule() == AttributeUpdateRule.READ_WRITE) )
							// WRITE_ON_CREATE is not allowed on updates - It will throw a null pointer exception.  This line is commented out:
//							&& (attributeDef.getUpdateRule() == AttributeUpdateRule.READ_WRITE || attributeDef.getUpdateRule() == AttributeUpdateRule.WRITE_ON_CREATE)) 
							
						&& !PropertyManager.getInstance().containsPropertyValue(propertyFile, "DataSourceModule_NonUpdateableAttributes", attribute.getName())) {
						
						// Create the generic attribute structure from the DataSourceModule.xml
						Attribute genericAttribute = populateGenericAttributeForUpdate(attribute, attributeDef, prefix, propertyFile);
						if (genericAttribute != null) {
							// Add the generic attribute to the data source object
							dsAttributeList.getAttribute().add(genericAttribute);
						}
					}
				}
			}
		} else if(datasource.getIntrospectDataSource() != null) {

			// Get the datasource path and extract in variables in the path
			String dsResPath = CommonUtils.extractVariable(prefix, datasource.getIntrospectDataSource().getResourcePath(), propertyFile, true);

			// Get the Resource definition for this data source path
			DataSourceResource currentDataSource = (DataSourceResource) getResourceDAO().getResource(serverId, dsResPath, pathToServersXML);
			/* Retrieve the actual Composite Data Source Type Name for the current data source by locating an attribute with name=type
			 * This is retrieved from the previous invocation to getResource() and casting the response to DataSourceResource type
			 * 
			 *	<resource:getResourceResponse xmlns:resource="http://www.compositesw.com/services/system/admin/resource">
			 *		<resource:resources>
			 *			<resource:resource xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="resource:dataSourceResource">
			 *				<resource:name>ServerAttributeDefinitions</resource:name>
			 *				<resource:path>/shared/test00/DataSources/ServerAttributeDefinitions</resource:path>
			 *				<resource:type>DATA_SOURCE</resource:type>
			 *				<resource:subtype>XML_FILE_DATA_SOURCE</resource:subtype>
			 *				...elements removed
			 *				<resource:childCount>1</resource:childCount>
			 *				
		 	 *			--> <resource:dataSourceType>File-XML</resource:dataSourceType>
			 *				
			 *			</resource:resource>
			 *		</resource:resources>
			 *	</resource:getResourceResponse>
			 */
			String dsTypeName = currentDataSource.getDataSourceType();

			// Retrieve the full list of attribute definitions and update rules for this type of data source (resource:dataSourceType)
			List<AttributeDef> attributeDefList = getDataSourceDAO().getDataSourceAttributeDefs(serverId, pathToServersXML, dsTypeName);
		
			// Get the list of generic attributes from the DataSourceModule.xml file
			List<AttributeDefType> attributeTypes = datasource.getIntrospectDataSource().getGenericAttribute();

			// Iterate through the DataSourceModule.xml file attributes
			for (AttributeDefType attribute : attributeTypes) {

				/* Retrieve the Attribute Definition list for a given DataSource Attribute Name (e.g. WSDL, XML/HTTP, File-XML, MySQL 5.0, Oracle 11g (Thin Driver))
				 * Below is an example of an Attribute Definition for the "type=MySql".  
				 * The associated Attribute Def Type Name=MySQL 5.0 which is what is needed to pass into getAttributeDef to get the update rules.
					<resource:dataSourceType>
						<resource:name>MySQL 5.0</resource:name>
						<resource:type>MySql</resource:type>
						... <resource:attributes> removed from this documentation
						</resource:attributes>
					</resource:dataSourceType>
				 */
				// Get the Attribute Definition and Update Rules for the specific DataSource type
				AttributeDef attributeDef = getAttributeDef(attributeDefList, attribute.getName());

				// Only set the attribute if the attribute is (READ_WRITE or WRITE_ON_CREATE) and the attribute is not in the "Non-Updateable Attribute List"
				if ((attributeDef != null
						&& (attributeDef.getUpdateRule() == AttributeUpdateRule.READ_WRITE) )
						// WRITE_ON_CREATE is not allowed on updates - It will throw a null pointer exception.  This line is commented out:
//						&& (attributeDef.getUpdateRule() == AttributeUpdateRule.READ_WRITE || attributeDef.getUpdateRule() == AttributeUpdateRule.WRITE_ON_CREATE)) 
						
					&& !PropertyManager.getInstance().containsPropertyValue(propertyFile, "DataSourceModule_NonUpdateableAttributes", attribute.getName())) {
					
					// Create the generic attribute structure from the DataSourceModule.xml
					Attribute genericAttribute = populateGenericAttributeForUpdate(attribute, attributeDef, prefix, propertyFile);
					if (genericAttribute != null) {
						// Add the generic attribute to the data source object
						dsAttributeList.getAttribute().add(genericAttribute);
					}
				}
			}	
		}
		
		return dsAttributeList;
	}

	// Populate a Generic Attribute for Data Source Actions such as UPDATE
	// Create the generic attribute structure from the DataSourceModule.xml
	private Attribute populateGenericAttributeForUpdate(AttributeDefType attribute, AttributeDef attributeDef, String prefix, String propertyFile) {
		Attribute genericAttribute = null;
		
		if (attribute != null) {
			
			// Create a generic attribute structure to allow for the following structures:
			//		Value
			//		ValueArray
			//		ValueList
			//		ValueMap
			genericAttribute = new Attribute();
			
			// Set the attribute if it has a "Value"
			if (attribute.getValue() != null && attribute.getValue().length() > 0) {
				
				// Set the attribute name
				genericAttribute.setName(attribute.getName());
				
				/*
				 * Retrieve the type from the Attribute Definition instead of the attribute itself.
				 * This is because there is currently a bug in Composite where the attribute returned by getResource() is different
				 * than the Attribute Definition returned by getDataSourceAttributeDefs().  CIS will validate the attribute during updateDataSource()
				 * based on the Attribute Definition.  It will throw a null pointer exception if the there is a mismatch.
				 * 
				 * Original line of code is now commented out:
				 *  genericAttribute.setType(AttributeType.fromValue(attributeType.getType().name()));
				 */
				genericAttribute.setType(AttributeType.fromValue(attributeDef.getType().name()));

				// Check for a password type and decrypt if found
				if (attribute.getType().name().equalsIgnoreCase(AttributeType.PASSWORD_STRING.name())) {
					// Add then Value to the Attribute Value
					genericAttribute.setValue(CommonUtils.decrypt(attribute.getValue()));
				} else {
					// Extract and resolve variables used in the value attribute
					String value = CommonUtils.extractVariable(prefix, attribute.getValue(), propertyFile, false);
					// Add then Value to the Attribute Value
					genericAttribute.setValue(value);
				}
			}
			
			// Set the attribute if it has a "ValueArray"
			if (attribute.getValueArray() != null) {
				
				// Set the attribute name
				genericAttribute.setName(attribute.getName());
				/*
				 * Retrieve the type from the Attribute Definition instead of the attribute itself.
				 * This is because there is currently a bug in Composite where the attribute returned by getResource() is different
				 * than the Attribute Definition returned by getDataSourceAttributeDefs().  CIS will validate the attribute during updateDataSource()
				 * based on the Attribute Definition.  It will throw a null pointer exception if the there is a mismatch.
				 * 
				 * It is better to generate the XML with the correct attribute type than for the error to occur during updateDataSource().
				 * 
				 * Original line of code is now commented out:
				 *  genericAttribute.setType(AttributeTypeSimpleType.valueOf(attribute.getType().name()));
				 */
				genericAttribute.setType(AttributeType.fromValue(attributeDef.getType().name()));

				// Create the Value Array structure
				AttributeSimpleValueList attributeSimpleValueList = new AttributeSimpleValueList();
				for (String item : attribute.getValueArray().getItem()) {
					// Extract and resolve variables used in the value attribute
					String arrayValue = CommonUtils.extractVariable(prefix, item, propertyFile, false);
					attributeSimpleValueList.getItem().add(arrayValue);
				}
				// Add then Value Array to the Attribute Value Array
				genericAttribute.setValueArray(attributeSimpleValueList);
			}
			
			// Set the attribute if it has a "ValueList"
			if (attribute.getValueList() != null) {
				
				// Set the attribute name
				genericAttribute.setName(attribute.getName());
				/*
				 * Retrieve the type from the Attribute Definition instead of the attribute itself.
				 * This is because there is currently a bug in Composite where the attribute returned by getResource() is different
				 * than the Attribute Definition returned by getDataSourceAttributeDefs().  CIS will validate the attribute during updateDataSource()
				 * based on the Attribute Definition.  It will throw a null pointer exception if the there is a mismatch.
				 * 
				 * It is better to generate the XML with the correct attribute type than for the error to occur during updateDataSource().
				 * 
				 * Original line of code is now commented out:
				 *  genericAttribute.setType(AttributeTypeSimpleType.valueOf(attribute.getType().name()));
				 */
				genericAttribute.setType(AttributeType.fromValue(attributeDef.getType().name()));

				// Create the Value List structure
				AttributeTypeValueList attributeTypeValueList = new AttributeTypeValueList();
				for (ServerAttributeValueListItemType item : attribute.getValueList().getItem()) {
					
					// Set the Value Map Entry
					AttributeTypeValue attributeTypeValue = new AttributeTypeValue();
					// Get the item type
					attributeTypeValue.setType(AttributeType.fromValue(item.getType().name()));
					// Get the item value
					String itemValue = CommonUtils.extractVariable(prefix, item.getValue(), propertyFile, false);
					attributeTypeValue.setValue(itemValue);
					
					// Add the Value List Entry to the Entry list
					attributeTypeValueList.getItem().add(attributeTypeValue);
				}
				// Add then Value List to the Attribute Value List
				genericAttribute.setValueList(attributeTypeValueList);
			}
			
			// Set the attribute if it has a "ValueMap"
			if (attribute.getValueMap() != null) {
				
				// Set the attribute name
				genericAttribute.setName(attribute.getName());
				/*
				 * Retrieve the type from the Attribute Definition instead of the attribute itself.
				 * This is because there is currently a bug in Composite where the attribute returned by getResource() is different
				 * than the Attribute Definition returned by getDataSourceAttributeDefs().  CIS will validate the attribute during updateDataSource()
				 * based on the Attribute Definition.  It will throw a null pointer exception if the there is a mismatch.
				 * 
				 * It is better to generate the XML with the correct attribute type than for the error to occur during updateDataSource().
				 * 
				 * Original line of code is now commented out:
				 *  genericAttribute.setType(AttributeTypeSimpleType.valueOf(attribute.getType().name()));
				 */
				genericAttribute.setType(AttributeType.fromValue(attributeDef.getType().name()));
				
				// Create the Value Map structure
				AttributeTypeValueMap attributeTypeValueMap = new AttributeTypeValueMap();
				for (ServerAttributeValueMapEntryType serverAttributeValueMapEntryType : attribute.getValueMap().getEntry()) {
					
					AttributeTypeValueMap.Entry entry = new AttributeTypeValueMap.Entry();
					AttributeTypeValue attributeTypeKeyValue = new AttributeTypeValue();
													
					// Get the entry key
					attributeTypeKeyValue.setType(AttributeType.fromValue(serverAttributeValueMapEntryType.getKey().getType().name()));
					// Get the entry key name value
					String keyValue = CommonUtils.extractVariable(prefix, serverAttributeValueMapEntryType.getKey().getValue(), propertyFile, false);
					attributeTypeKeyValue.setValue(keyValue);
					// Set the entry for key
					entry.setKey(attributeTypeKeyValue);
					
					AttributeTypeValue attributeTypeValueValue = new AttributeTypeValue();
					// Get the entry value
					attributeTypeValueValue.setType(AttributeType.fromValue(serverAttributeValueMapEntryType.getValue().getType().name()));
					// Get the entry value value
					String valueValue = CommonUtils.extractVariable(prefix, serverAttributeValueMapEntryType.getValue().getValue(), propertyFile, false);
					attributeTypeValueValue.setValue(valueValue);
					// Set the entry for value
					entry.setValue(attributeTypeValueValue);
					
					// Add the Value Map Entry to the Entry list
					attributeTypeValueMap.getEntry().add(entry);
				}
				// Add then Value Map to the Attribute Value Map
				genericAttribute.setValueMap(attributeTypeValueMap);
			}
		}
		return genericAttribute;
	}
	
	// Populate the Relational Attribute List for Data Source Actions such as UPDATE
	private void populateAttributesFromRelationalDataSource(DataSourceChoiceType datasource, AttributeList dsAttributeList) {
	
		String defaultPropertyFile = CommonConstants.propertyFile;
		/*  Determine the property file name for this environment
		 *    1. Start with default file CommonConstants.propertyFile
		 *    2. Get Java Environment variables
		 *    3. Get OS System Environment variables
		 */
        String propertyFile = CommonUtils.getFileOrSystemPropertyValue(defaultPropertyFile, "CONFIG_PROPERTY_FILE");
        // Set a default value if not found
        if (propertyFile == null || propertyFile.trim().length() == 0) {
        	propertyFile = defaultPropertyFile;
        }
		String prefix = "DataSourcemanagerImpl.populateAttributesFromRelationalDataSource";

		// Add attribute if not null and empty
		if (datasource.getRelationalDataSource().getHostname() != null && datasource.getRelationalDataSource().getHostname().length() > 0) {
			Attribute attrHost = new Attribute();
			attrHost.setName("urlIP");
			attrHost.setType(AttributeType.STRING);
			// Extract and resolve variables used in the value attribute
			String value = CommonUtils.extractVariable(prefix, datasource.getRelationalDataSource().getHostname(), propertyFile, false);
			attrHost.setValue(value);
			dsAttributeList.getAttribute().add(attrHost);
		}
		
		// Add attribute if not null and greater than 0
		if (datasource.getRelationalDataSource().getPort() != null) {
			Attribute attrUrlPort = new Attribute();
			attrUrlPort.setName("urlPort");
			attrUrlPort.setType(AttributeType.INTEGER);
			// Extract and resolve variables used in the value attribute
			String value = CommonUtils.extractVariable(prefix, datasource.getRelationalDataSource().getPort().toString(), propertyFile, false);
			// Convert the resolved string value to big integer to verify that it is an integer value.
			BigInteger intPort = new BigInteger(value);
			// Only set the value if the port number is greater than 0
			if (intPort.compareTo(BigInteger.ZERO) == 1) {
				attrUrlPort.setValue(value);
				dsAttributeList.getAttribute().add(attrUrlPort);
			}
		}
		
		// Add attribute if not null and empty
		if (datasource.getRelationalDataSource().getDatabaseName() != null && datasource.getRelationalDataSource().getDatabaseName().length() > 0) {
			Attribute attrDbName = new Attribute();
			attrDbName.setName("urlDatabaseName");
			attrDbName.setType(AttributeType.STRING);
			// Extract and resolve variables used in the value attribute
			String value = CommonUtils.extractVariable(prefix, datasource.getRelationalDataSource().getDatabaseName(), propertyFile, false);
			attrDbName.setValue(value);
			dsAttributeList.getAttribute().add(attrDbName);
		}
		
		// Add attribute if not null and empty
		if (datasource.getRelationalDataSource().getLogin() != null && datasource.getRelationalDataSource().getLogin().length() > 0) {
			Attribute attrLogin = new Attribute();
			attrLogin.setName("login");
			attrLogin.setType(AttributeType.STRING);
			// Extract and resolve variables used in the value attribute
			String value = CommonUtils.extractVariable(prefix, datasource.getRelationalDataSource().getLogin(), propertyFile, false);
			attrLogin.setValue(value);
			dsAttributeList.getAttribute().add(attrLogin);
		}
		
		// Add attribute if not null and empty
		if (datasource.getRelationalDataSource().getEncryptedPassword() != null && datasource.getRelationalDataSource().getEncryptedPassword().length() > 0) {
			Attribute attrPassword = new Attribute();
			attrPassword.setName("password");
			attrPassword.setType(AttributeType.PASSWORD_STRING);
			attrPassword.setValue(CommonUtils.decrypt(datasource.getRelationalDataSource().getEncryptedPassword()));
			dsAttributeList.getAttribute().add(attrPassword);
		}
		
		// Add attribute if not null and empty
		if (datasource.getRelationalDataSource().getValQuery() != null && datasource.getRelationalDataSource().getValQuery().length() > 0) {
			Attribute attrValQuery = new Attribute();
			attrValQuery.setName("connValidateQuery");
			attrValQuery.setType(AttributeType.STRING);
			// Extract and resolve variables used in the value attribute
			String value = CommonUtils.extractVariable(prefix, datasource.getRelationalDataSource().getValQuery(), propertyFile, false);
			attrValQuery.setValue(value);
			dsAttributeList.getAttribute().add(attrValQuery);
		}
	}

	/*************************************************************************************
	 * populateIntrospectionPlan()
	 * 
	 * This method is used for reading the DataSourceModule.xml file and populating
	 * the internal structure for invocation of the INTROSPECT data source action.
	 * 
	 * This plan shows how to recursively add or update a data source with no schema or catalog.
	 * 		The path is left blank.  The action ADD_OR_UPDATE_RECURSIVELY is specified.
	      <resource:entry>
	        <resource:resourceId>
	          <resource:path></resource:path>
	          <resource:type>DATA_SOURCE</resource:type>
	          <resource:subtype>RELATIONAL_DATA_SOURCE</resource:subtype>
	        </resource:resourceId>
	        <resource:action>ADD_OR_UPDATE_RECURSIVELY</resource:action>
	      </resource:entry>


</resource:introspectResourcesTask>
	 *************************************************************************************/
	// Populate the Attribute List for Data Source Actions such as UPDATE
	private IntrospectionPlan populateIntrospectionPlan(DataSourceChoiceType datasource, String serverId, String pathToServersXML) {

		String defaultPropertyFile = CommonConstants.propertyFile;
		/*  Determine the property file name for this environment
		 *    1. Start with default file CommonConstants.propertyFile
		 *    2. Get Java Environment variables
		 *    3. Get OS System Environment variables
		 */
        String propertyFile = CommonUtils.getFileOrSystemPropertyValue(defaultPropertyFile, "CONFIG_PROPERTY_FILE");
        // Set a default value if not found
        if (propertyFile == null || propertyFile.trim().length() == 0) {
        	propertyFile = defaultPropertyFile;
        }
		String prefix = "DataSourcemanagerImpl.populateIntrospectionPlan";
		
		// Initialize the plan to null
		IntrospectionPlan plan = null;
		
		// Set up the introspection plan for this data source.	
		if (datasource.getIntrospectDataSource() != null) {
			
			//populate attributes from generic attributes from relational data source
			if(datasource.getIntrospectDataSource().getPlan() != null){
				
				// Initialize a new plan
				plan = new IntrospectionPlan();
				IntrospectionPlanEntries planEntries = new IntrospectionPlanEntries();

				// Set the plan boolean values
				plan.setAutoRollback(datasource.getIntrospectDataSource().getPlan().isAutoRollback());
				plan.setCommitOnFailure(datasource.getIntrospectDataSource().getPlan().isCommitOnFailure());
				plan.setFailFast(datasource.getIntrospectDataSource().getPlan().isFailFast());
				plan.setScanForNewResourcesToAutoAdd(datasource.getIntrospectDataSource().getPlan().isScanForNewResourcesToAutoAdd());
				plan.setUpdateAllIntrospectedResources(datasource.getIntrospectDataSource().getPlan().isUpdateAllIntrospectedResources());	
				
				// Get the list of Introspection Entries from the DataSourceModule.xml
				List<IntrospectDataSourcePlanEntryType> dsXmlEntries = datasource.getIntrospectDataSource().getPlan().getPlanEntry();
							
				// Iterate through the DataSourceModule.xml file introspection entries
				for (IntrospectDataSourcePlanEntryType dsXmlEntry : dsXmlEntries) {
					/* Retrieve the introspection plan entries list)
					    <planEntry>
					        <resourceId>
					            <resourcePath>string</resourcePath>
					            <!--Element resourceType is optional-->
					            <resourceType>TRIGGER</resourceType>
					            <subtype>string</subtype>
					        </resourceId>
					        <action>string</action>
					        <!--Element attributes is optional, maxOccurs=unbounded-->
					        <genericAttribute>
					            <name>string</name>
					            <type>UNKNOWN</type>
					            <!--Element value is optional-->
					            <value>string</value>
					            <!--Element valueArray is optional-->
					            <valueArray>
					                <!--Element item is optional, maxOccurs=unbounded-->
					                <item>string</item>
					                <item>string</item>
					                <item>string</item>
					            </valueArray>
					            <!--Element valueList is optional-->
					            <valueList>
					                <!--Element item is optional, maxOccurs=unbounded-->
					                <item>
					                    <!--Element type is optional-->
					                    <type>UNKNOWN</type>
					                    <!--Element value is optional-->
					                    <value>string</value>
					                </item>
					                <item>
					                    <!--Element type is optional-->
					                    <type>UNKNOWN</type>
					                    <!--Element value is optional-->
					                    <value>string</value>
					                </item>
					                <item>
					                    <!--Element type is optional-->
					                    <type>UNKNOWN</type>
					                    <!--Element value is optional-->
					                    <value>string</value>
					                </item>
					            </valueList>
					            <!--Element valueMap is optional-->
					            <valueMap>
					                <!--Element entry is optional, maxOccurs=unbounded-->
					                <entry>
					                    <!--Element key is optional-->
					                    <key>
					                        <!--Element type is optional-->
					                        <type>UNKNOWN</type>
					                        <!--Element value is optional-->
					                        <value>string</value>
					                    </key>
					                    <!--Element value is optional-->
					                    <value>
					                        <!--Element type is optional-->
					                        <type>UNKNOWN</type>
					                        <!--Element value is optional-->
					                        <value>string</value>
					                    </value>
					                </entry>
					                <entry>
					                    <!--Element key is optional-->
					                    <key>
					                        <!--Element type is optional-->
					                        <type>UNKNOWN</type>
					                        <!--Element value is optional-->
					                        <value>string</value>
					                    </key>
					                    <!--Element value is optional-->
					                    <value>
					                        <!--Element type is optional-->
					                        <type>UNKNOWN</type>
					                        <!--Element value is optional-->
					                        <value>string</value>
					                    </value>
					                </entry>
					                <entry>
					                    <!--Element key is optional-->
					                    <key>
					                        <!--Element type is optional-->
					                        <type>UNKNOWN</type>
					                        <!--Element value is optional-->
					                        <value>string</value>
					                    </key>
					                    <!--Element value is optional-->
					                    <value>
					                        <!--Element type is optional-->
					                        <type>UNKNOWN</type>
					                        <!--Element value is optional-->
					                        <value>string</value>
					                    </value>
					                </entry>
					            </valueMap>
					        </genericAttribute>
					    </planEntry>
					 */
					// Initialize a plan entry
					IntrospectionPlanEntry planEntry = new IntrospectionPlanEntry();
					
					// Set the action
					if (dsXmlEntry.getAction()!= null) {
						planEntry.setAction(IntrospectionPlanAction.valueOf(dsXmlEntry.getAction().toString()));
					}
					
					PathTypeSubtype pathTypeSubtype = new PathTypeSubtype();
					// Set the Resource Path [Note: path must be relative to the data source path.  It is not a full path.]
					if (dsXmlEntry.getResourceId().getResourcePath() != null) 
					{
						String resourcePath = CommonUtils.extractVariable(prefix, datasource.getIntrospectDataSource().getResourcePath(), propertyFile, true)+"/";
						String entryPath = CommonUtils.extractVariable(prefix, dsXmlEntry.getResourceId().getResourcePath(), propertyFile, true);
						
						// This code will make sure that a relative path is being used by parsing the data source path and comparing to this resource path
						if (entryPath.contains(resourcePath)) {
							entryPath = entryPath.replaceAll(Matcher.quoteReplacement(resourcePath), Matcher.quoteReplacement(""));							
						}
						/*
						List<String> argList = null;
						argList = CommonUtils.getArgumentsList(argList, true, dsXmlEntry.getResourceId().getResourcePath(), "/");
						for (int i=0; i < argList.size(); i++) {
							String part = argList.get(i);
							if (resourcePath.contains("/"+part+"/")) {
								resourcePath = resourcePath.replaceAll(Matcher.quoteReplacement("/"+part+"/"), Matcher.quoteReplacement(""));

							}
						}
						*/
						
						// Set the resource Path
						pathTypeSubtype.setPath(entryPath);
					}
					// Set the Resource Type
					if (dsXmlEntry.getResourceId().getResourceType() != null) 
						pathTypeSubtype.setType(ResourceType.valueOf(CommonUtils.extractVariable(prefix, dsXmlEntry.getResourceId().getResourceType().value(), propertyFile, true)));
					// Set the Subtype
					if (dsXmlEntry.getResourceId().getSubtype() != null)
						pathTypeSubtype.setSubtype(ResourceSubType.valueOf(CommonUtils.extractVariable(prefix, dsXmlEntry.getResourceId().getSubtype().toString(), propertyFile, true)));
					planEntry.setResourceId(pathTypeSubtype);
					
					if (dsXmlEntry.getGenericAttribute() != null) {
						// Initialize the attribute list
						AttributeList dsAttributeList = new AttributeList();

						// Get the Resource definition for this data source path
						DataSourceResource currentDataSource = (DataSourceResource) getResourceDAO().getResource(serverId, datasource.getIntrospectDataSource().getResourcePath(), pathToServersXML);
						/* Retrieve the actual Composite Data Source Type Name for the current data source by locating an attribute with name=type
						 * This is retrieved from the previous invocation to getResource() and casting the response to DataSourceResource type
						 * 
						 *	<resource:getResourceResponse xmlns:resource="http://www.compositesw.com/services/system/admin/resource">
						 *		<resource:resources>
						 *			<resource:resource xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="resource:dataSourceResource">
						 *				<resource:name>ServerAttributeDefinitions</resource:name>
						 *				<resource:path>/shared/test00/DataSources/ServerAttributeDefinitions</resource:path>
						 *				<resource:type>DATA_SOURCE</resource:type>
						 *				<resource:subtype>XML_FILE_DATA_SOURCE</resource:subtype>
						 *				...elements removed
						 *				<resource:childCount>1</resource:childCount>
						 *				
					 	 *			--> <resource:dataSourceType>File-XML</resource:dataSourceType>
						 *				
						 *			</resource:resource>
						 *		</resource:resources>
						 *	</resource:getResourceResponse>
						 */
						String dsTypeName = currentDataSource.getDataSourceType();
	
						// Retrieve the full list of attribute definitions and update rules for this type of data source (resource:dataSourceType)
						List<AttributeDef> attributeDefList = getDataSourceDAO().getDataSourceAttributeDefs(serverId, pathToServersXML, dsTypeName);
					
						// Get the list of generic attributes from the plan entry attributes in the DataSourceModule.xml file
						List<AttributeDefType> attributeTypes = dsXmlEntry.getGenericAttribute();
	
						// Iterate through the DataSourceModule.xml file attributes
						for (AttributeDefType attribute : attributeTypes) {
	
							/* Retrieve the Attribute Definition list for a given DataSource Attribute Name (e.g. WSDL, XML/HTTP, File-XML, MySQL 5.0, Oracle 11g (Thin Driver))
							 * Below is an example of an Attribute Definition for the "type=MySql".  
							 * The associated Attribute Def Type Name=MySQL 5.0 which is what is needed to pass into getAttributeDef to get the update rules.
								<resource:dataSourceType>
									<resource:name>MySQL 5.0</resource:name>
									<resource:type>MySql</resource:type>
									... <resource:attributes> removed from this documentation
									</resource:attributes>
								</resource:dataSourceType>
							 */
							// Get the Attribute Definition and Update Rules for the specific DataSource type
							AttributeDef attributeDef = getAttributeDef(attributeDefList, attribute.getName());
	
							// Only set the attribute if the attribute is (READ_WRITE or WRITE_ON_CREATE) and the attribute is not in the "Non-Updateable Attribute List"
							if ((attributeDef != null
									&& (attributeDef.getUpdateRule() == AttributeUpdateRule.READ_WRITE) )
									// WRITE_ON_CREATE is not allowed on updates - It will throw a null pointer exception.  This line is commented out:
	//								&& (attributeDef.getUpdateRule() == AttributeUpdateRule.READ_WRITE || attributeDef.getUpdateRule() == AttributeUpdateRule.WRITE_ON_CREATE)) 
									
								&& !PropertyManager.getInstance().containsPropertyValue(propertyFile, "DataSourceModule_NonUpdateableAttributes", attribute.getName())) {
								
								// Create the generic attribute structure from the DataSourceModule.xml
								Attribute genericAttribute = populateGenericAttributeForUpdate(attribute, attributeDef, prefix, propertyFile);
								if (genericAttribute != null) {
									// Add the generic attribute to the data source object
									dsAttributeList.getAttribute().add(genericAttribute);
								}
							}
						}
						planEntry.setAttributes(dsAttributeList);
					}
					// Add a new plan entry to the list
					planEntries.getEntry().add(planEntry);
				}
				plan.setEntries(planEntries);
			}		
		}
		return plan;
	}
	
	/*************************************************************************************
	 * generateDataSourcesXML()
	 * 
	 * All methods below this section are used in support of generateDataSourcesXML()
	 * 
	 *************************************************************************************/
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.DataSourceManager#generateDataSourceXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateDataSourcesXML(String serverId, String startPath, String pathToDataSourceXML, String pathToServersXML) throws CompositeException {
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// Set the Module Action Objective
		String s1 = (startPath == null) ? "no_startPath" : "Path="+startPath;
		System.setProperty("MODULE_ACTION_OBJECTIVE", "GENERATE : "+s1);

		ResourceList resourceList = DeployManagerUtil.getDeployManager().getResourcesFromPath(serverId, startPath, ResourceType.CONTAINER.name(), ResourceType.DATA_SOURCE.name(), DetailLevel.FULL.name(), pathToServersXML);

		if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty()){

			List<Resource> resources = resourceList.getResource();			
			DatasourceModule dataSourceModule = new ObjectFactory().createDatasourceModule();

			Integer seqId = 0;
			for (Resource resource : resources) {

				DataSourceResource dsResource = (DataSourceResource)resource;
				DataSourceChoiceType dataSourceChoiceType = new DataSourceChoiceType();
				dataSourceModule.getDatasource().add(dataSourceChoiceType);
				
				populateNameAndPathAttributes(++seqId, dsResource,dataSourceChoiceType);
				populateAttributes(dsResource,dataSourceChoiceType, serverId, pathToServersXML);
			}
			XMLUtils.createXMLFromModuleType(dataSourceModule, pathToDataSourceXML);
		}
			
	}

	private void populateNameAndPathAttributes(Integer seqId, DataSourceResource dsResource,DataSourceChoiceType dataSourceChoiceType) {
		
		if(dsResource.getSubtype() != null && dsResource.getSubtype().name().equalsIgnoreCase(ResourceSubType.RELATIONAL_DATA_SOURCE.name())){

			RelationalDataSourceType relationalDataSourceType = new RelationalDataSourceType();
			relationalDataSourceType.setId("ds"+seqId);
			dataSourceChoiceType.setRelationalDataSource(relationalDataSourceType);
			relationalDataSourceType.setResourcePath(dsResource.getPath());
			if (dsResource.getType() != null)
				relationalDataSourceType.setResourceType(dsResource.getType().value());
			if (dsResource.getSubtype() != null)
				relationalDataSourceType.setSubType(dsResource.getSubtype().value());
			if (dsResource.getDataSourceType() != null)
				relationalDataSourceType.setDataSourceType(dsResource.getDataSourceType());

		}else{

			GenericDataSourceType genericDataSourceType = new GenericDataSourceType();
			genericDataSourceType.setId("ds"+seqId);
			dataSourceChoiceType.setGenericDataSource(genericDataSourceType);
			genericDataSourceType.setResourcePath(dsResource.getPath());
			if (dsResource.getType() != null)
				genericDataSourceType.setResourceType(dsResource.getType().value());
			if (dsResource.getSubtype() != null)
				genericDataSourceType.setSubType(dsResource.getSubtype().value());
			if (dsResource.getDataSourceType() != null)
				genericDataSourceType.setDataSourceType(dsResource.getDataSourceType());
		}
	}
	
	// Populate Attributes for generateDataSourceXML
	private void populateAttributes(DataSourceResource dsResource, DataSourceChoiceType dataSourceChoiceType, String serverId, String pathToServersXML) {

		String defaultPropertyFile = CommonConstants.propertyFile;
		/*  Determine the property file name for this environment
		 *    1. Start with default file CommonConstants.propertyFile
		 *    2. Get Java Environment variables
		 *    3. Get OS System Environment variables
		 */
        String propertyFile = CommonUtils.getFileOrSystemPropertyValue(defaultPropertyFile, "CONFIG_PROPERTY_FILE");
        // Set a default value if not found
        if (propertyFile == null || propertyFile.trim().length() == 0) {
        	propertyFile = defaultPropertyFile;
        }

		// Get the list of attributes retrieved from the server
		List<Attribute> attributes = dsResource.getAttributes().getAttribute();

		// Retrieve the actual Composite Data Source Type Name for the current data source
		String dsTypeName = dsResource.getDataSourceType();

		// Retrieve the full list of attribute definitions and update rules for this type of data source (resource:dataSourceType)
		List<AttributeDef> attributeDefList = getDataSourceDAO().getDataSourceAttributeDefs(serverId, pathToServersXML, dsTypeName);

		// Iterate over the list of Data Source Attributes retrieved from the Server
		for (Attribute attribute : attributes) {
			if(dsResource.getSubtype() != null && dsResource.getSubtype().name().equalsIgnoreCase(ResourceSubType.RELATIONAL_DATA_SOURCE.name())){
				
				poupulateRelationalDataSourceTypeAttributes(dataSourceChoiceType, attribute, attributeDefList);

			}else{

				/* Retrieve the Attribute Definition list for a given DataSource Attribute Name (e.g. WSDL, XML/HTTP, File-XML, MySQL 5.0, Oracle 11g (Thin Driver))
				 * Below is an example of an Attribute Definition for the "type=MySql".  
				 * The associated Attribute Def Type Name=MySQL 5.0 which is what is needed to pass into getAttributeDef to get the update rules.
					<resource:dataSourceType>
						<resource:name>MySQL 5.0</resource:name>
						<resource:type>MySql</resource:type>
						... <resource:attributes> removed from this documentation
						</resource:attributes>
					</resource:dataSourceType>
				 */
				// Get the Attribute Definition and Update Rules for the specific DataSource type
				AttributeDef attributeDef = getAttributeDef(attributeDefList, attribute.getName());

				// Only set the attribute if the attribute is (READ_WRITE or WRITE_ON_CREATE) and the attribute is not in the "Non-Updateable Attribute List"
				if ((attributeDef != null
						&& (attributeDef.getUpdateRule() == AttributeUpdateRule.READ_WRITE) )
							// WRITE_ON_CREATE is not allowed on updates - It will throw a null pointer exception.  This line is commented out:
//						&& (attributeDef.getUpdateRule() == AttributeUpdateRule.READ_WRITE || attributeDef.getUpdateRule() == AttributeUpdateRule.WRITE_ON_CREATE)) 
					&& !PropertyManager.getInstance().containsPropertyValue(propertyFile, "DataSourceModule_NonUpdateableAttributes", attribute.getName())) {

					// Create the generic attribute structure from the DataSourceModule.xml
					AttributeDefType genericAttribute = populateGenericAttributeForGenerate(attribute, attributeDef, propertyFile);
					if (genericAttribute != null) {
						// Add the generic attribute to the data source object
						dataSourceChoiceType.getGenericDataSource().getGenericAttribute().add(genericAttribute);
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.DataSourceManager#getDataSourcesChildren(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public ResourceList getDataSourcesChildren(String serverId,String resourcePath, String resourceType, String childResourceType, String detailLevel,String pathToServersXML) {
		ResourceList returnResourceList = new ResourceList();
		ResourceList resourceList = DeployManagerUtil.getDeployManager().getResourcesFromPath(serverId, resourcePath, resourceType, childResourceType, DetailLevel.SIMPLE.name(), pathToServersXML);
		boolean includeContainers = false;
		
		if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty()){

			List<Resource> resources = resourceList.getResource();		
			for (Resource resource : resources) {
				ResourceList currResourceList = getDataSourceDAO().getDataSourceChildResourcesFromPath(serverId, resource.getPath(), resource.getType().name(), childResourceType, includeContainers, DetailLevel.FULL.name(), pathToServersXML);
				if(currResourceList != null && currResourceList.getResource() != null && !currResourceList.getResource().isEmpty()){
					returnResourceList.getResource().addAll(currResourceList.getResource());
				} else {
					returnResourceList.getResource().add(resource);
				}
			}
		}
		return returnResourceList;
	}

	// Populate Relational DataSource Attributes for generateDataSourceXML
	private void poupulateRelationalDataSourceTypeAttributes(DataSourceChoiceType dataSourceChoiceType, Attribute attribute, List<AttributeDef> attributeDefList) {

		String defaultPropertyFile = CommonConstants.propertyFile;
		/*  Determine the property file name for this environment
		 *    1. Start with default file CommonConstants.propertyFile
		 *    2. Get Java Environment variables
		 *    3. Get OS System Environment variables
		 */
        String propertyFile = CommonUtils.getFileOrSystemPropertyValue(defaultPropertyFile, "CONFIG_PROPERTY_FILE");
        // Set a default value if not found
        if (propertyFile == null || propertyFile.trim().length() == 0) {
        	propertyFile = defaultPropertyFile;
        }

		if (attribute.getName().equalsIgnoreCase("urlIP")) {

			dataSourceChoiceType.getRelationalDataSource().setHostname(attribute.getValue());

		} else if (attribute.getName().equalsIgnoreCase("urlPort")) {

			dataSourceChoiceType.getRelationalDataSource().setPort(attribute.getValue());

		} else if (attribute.getName().equalsIgnoreCase("urlDatabaseName")) {

			dataSourceChoiceType.getRelationalDataSource().setDatabaseName(attribute.getValue());

		} else if (attribute.getName().equalsIgnoreCase("login")) {

			dataSourceChoiceType.getRelationalDataSource().setLogin(attribute.getValue());

		} else if (attribute.getName().equalsIgnoreCase("password")) {

			dataSourceChoiceType.getRelationalDataSource().setEncryptedPassword(attribute.getValue());

		} else if (attribute.getName().equalsIgnoreCase("connValidateQuery")) {

			dataSourceChoiceType.getRelationalDataSource().setValQuery(attribute.getValue());
		} else {

			/* Retrieve the Attribute Definition list for a given DataSource Attribute Name (e.g. WSDL, XML/HTTP, File-XML, MySQL 5.0, Oracle 11g (Thin Driver))
			 * Below is an example of an Attribute Definition for the "type=MySql".  
			 * The associated Attribute Def Type Name=MySQL 5.0 which is what is needed to pass into getAttributeDef to get the update rules.
				<resource:dataSourceType>
					<resource:name>MySQL 5.0</resource:name>
					<resource:type>MySql</resource:type>
					... <resource:attributes> removed from this documentation
					</resource:attributes>
				</resource:dataSourceType>
			 */
			// Get the Attribute Definition and Update Rules for the specific DataSource type
			AttributeDef attributeDef = getAttributeDef(attributeDefList, attribute.getName());

			// Only set the attribute if the attribute is (READ_WRITE or WRITE_ON_CREATE) and the attribute is not in the "Non-Updateable Attribute List"
			if ((attributeDef != null
					&& (attributeDef.getUpdateRule() == AttributeUpdateRule.READ_WRITE) )
					// WRITE_ON_CREATE is not allowed on updates
//					&& (attributeDef.getUpdateRule() == AttributeUpdateRule.READ_WRITE || attributeDef.getUpdateRule() == AttributeUpdateRule.WRITE_ON_CREATE)) 
				&& !PropertyManager.getInstance().containsPropertyValue(propertyFile, "DataSourceModule_NonUpdateableAttributes", attribute.getName())) {
					
				
				// Create the generic attribute structure from the DataSourceModule.xml
				AttributeDefType genericAttribute = populateGenericAttributeForGenerate(attribute, attributeDef, propertyFile);
				if (genericAttribute != null) {
					// Add the generic attribute to the data source object
					dataSourceChoiceType.getRelationalDataSource().getGenericAttribute().add(genericAttribute);
				}
			}
		}
	}

	AttributeDefType populateGenericAttributeForGenerate(Attribute attribute, AttributeDef attributeDef, String propertyFile) {
		
		AttributeDefType genericAttribute = null;
		
		if (attribute != null) {
		
			if (attribute.getValue() != null && attribute.getValue().trim().length() > 0) {	

				// Initialize the genericAttribute object
				genericAttribute = new AttributeDefType();
				
				// Set the attribute if it has a "Value"
				genericAttribute.setName(attribute.getName());
				/*
				 * Retrieve the type from the Attribute Definition instead of the attribute itself.
				 * This is because there is currently a bug in Composite where the attribute returned by getResource() is different
				 * than the Attribute Definition returned by getDataSourceAttributeDefs().  CIS will validate the attribute during updateDataSource()
				 * based on the Attribute Definition.  It will throw a null pointer exception if the there is a mismatch.
				 * 
				 * It is better to generate the XML with the correct attribute type than for the error to occur during updateDataSource().
				 * 
				 * Original line of code is now commented out:
				 *  genericAttribute.setType(AttributeTypeSimpleType.valueOf(attribute.getType().name()));
				 */
				genericAttribute.setType(AttributeTypeSimpleType.fromValue(attributeDef.getType().name()));
	
				// Set the value
				genericAttribute.setValue(attribute.getValue());
			} 
			
			// Set the attribute if it has a "ValueArray"
			if (attribute.getValueArray() != null) {
				
				// Initialize the genericAttribute object
				genericAttribute = new AttributeDefType();
				
				// Set the attribute name
				genericAttribute.setName(attribute.getName());
				/*
				 * Retrieve the type from the Attribute Definition instead of the attribute itself.
				 * This is because there is currently a bug in Composite where the attribute returned by getResource() is different
				 * than the Attribute Definition returned by getDataSourceAttributeDefs().  CIS will validate the attribute during updateDataSource()
				 * based on the Attribute Definition.  It will throw a null pointer exception if the there is a mismatch.
				 * 
				 * It is better to generate the XML with the correct attribute type than for the error to occur during updateDataSource().
				 * 
				 * Original line of code is now commented out:
				 *  genericAttribute.setType(AttributeTypeSimpleType.valueOf(attribute.getType().name()));
				 */
				genericAttribute.setType(AttributeTypeSimpleType.fromValue(attributeDef.getType().name()));
	
				// Create the Value Array structure
				ServerAttributeValueArray serverAttributeValueArray = new ServerAttributeValueArray();
				for (String item : attribute.getValueArray().getItem()) {
					serverAttributeValueArray.getItem().add(item);
				}
				// Add then Value Array to the Attribute Value
				genericAttribute.setValueArray(serverAttributeValueArray);
			}
	
			// Set the attribute if it has a "ValueList"
			if (attribute.getValueList() != null) {
				
				// Initialize the genericAttribute object
				genericAttribute = new AttributeDefType();
				
				// Set the attribute name
				genericAttribute.setName(attribute.getName());
				/*
				 * Retrieve the type from the Attribute Definition instead of the attribute itself.
				 * This is because there is currently a bug in Composite where the attribute returned by getResource() is different
				 * than the Attribute Definition returned by getDataSourceAttributeDefs().  CIS will validate the attribute during updateDataSource()
				 * based on the Attribute Definition.  It will throw a null pointer exception if the there is a mismatch.
				 * 
				 * It is better to generate the XML with the correct attribute type than for the error to occur during updateDataSource().
				 * 
				 * Original line of code is now commented out:
				 *  genericAttribute.setType(AttributeTypeSimpleType.valueOf(attribute.getType().name()));
				 */
				genericAttribute.setType(AttributeTypeSimpleType.fromValue(attributeDef.getType().name()));
	
				// Create the Value List structure
				ServerAttributeValueList serverAttributeValueList = new ServerAttributeValueList();
				for (AttributeTypeValue item : attribute.getValueList().getItem()) {
					
					// Set the Value Map Entry
					ServerAttributeValueListItemType serverAttributeValueListItemType = new ServerAttributeValueListItemType();
					serverAttributeValueListItemType.setType(AttributeTypeSimpleType.fromValue(item.getType().name()));
					serverAttributeValueListItemType.setValue(item.getValue());					
	
					// Add the Value List Entry to the Entry list
					serverAttributeValueList.getItem().add(serverAttributeValueListItemType);
				}
				// Add then Value List to the Attribute Value
				genericAttribute.setValueList(serverAttributeValueList);
			}
			
			// Set the attribute if it has a "ValueMap"
			if (attribute.getValueMap() != null) {
				
				// Initialize the genericAttribute object
				genericAttribute = new AttributeDefType();
				
				// Set the attribute name
				genericAttribute.setName(attribute.getName());
				/*
				 * Retrieve the type from the Attribute Definition instead of the attribute itself.
				 * This is because there is currently a bug in Composite where the attribute returned by getResource() is different
				 * than the Attribute Definition returned by getDataSourceAttributeDefs().  CIS will validate the attribute during updateDataSource()
				 * based on the Attribute Definition.  It will throw a null pointer exception if the there is a mismatch.
				 * 
				 * It is better to generate the XML with the correct attribute type than for the error to occur during updateDataSource().
				 * 
				 * Original line of code is now commented out:
				 *  genericAttribute.setType(AttributeTypeSimpleType.valueOf(attribute.getType().name()));
				 */
				genericAttribute.setType(AttributeTypeSimpleType.fromValue(attributeDef.getType().name()));
				
				// Create the Value Map structure
				ServerAttributeValueMap serverAttributeValueMap = new ServerAttributeValueMap();
				for (Entry entry : attribute.getValueMap().getEntry()) {
					
					ServerAttributeValueMapEntryType serverAttributeValueMapEntryType = new ServerAttributeValueMapEntryType();
					ServerAttributeValueMapEntryKeyType serverAttributeValueMapEntryKeyType = new ServerAttributeValueMapEntryKeyType();
					
					// Get the entry key
					serverAttributeValueMapEntryKeyType.setType(AttributeTypeSimpleType.fromValue(entry.getKey().getType().name()));
					serverAttributeValueMapEntryKeyType.setValue(entry.getKey().getValue());		
					serverAttributeValueMapEntryType.setKey(serverAttributeValueMapEntryKeyType);
					
					// Get the entry value
					ServerAttributeValueMapEntryValueType serverAttributeValueMapEntryValueType = new ServerAttributeValueMapEntryValueType();
					serverAttributeValueMapEntryValueType.setType(AttributeTypeSimpleType.fromValue(entry.getValue().getType().name()));
					serverAttributeValueMapEntryValueType.setValue(entry.getValue().getValue());
					
					// Set the Value Map Entry
					serverAttributeValueMapEntryType.setValue(serverAttributeValueMapEntryValueType);
	
					// Add the Value Map Entry to the Entry list
					serverAttributeValueMap.getEntry().add(serverAttributeValueMapEntryType);
				}
				// Add then Value Map to the Attribute Value
				genericAttribute.setValueMap(serverAttributeValueMap);
			}
		}
		
		return genericAttribute;
	}
	
	/**
	 * Find the attributeDef from the list of attributeDefs that matches the given attribute name
	 * @param attributeDefList Attribute Def List
	 * @param attributeName Attribute Name
	 * @return AttributeDef that matches the attribute Name, null if not found
	 */
	private AttributeDef getAttributeDef(List<AttributeDef> attributeDefList, String attributeName) {

		if (attributeDefList == null || attributeName == null) {
			return null;
		}
		for (AttributeDef attributeDef : attributeDefList) {
			
			if (attributeDef.getName().equalsIgnoreCase(attributeName)) {
				return attributeDef;
			}
		}
		return null;
	}
	

	/*************************************************************************************
	 * generateDataSourceAttributeDefs()
	 * 
	 * All methods below this section are used in support of generateDataSourceAttributeDefs()
	 * 
	 *************************************************************************************/
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.DataSourceManager#generateDataSourceAttributeDefs(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateDataSourceAttributeDefs(String serverId, String startPath, String pathToDataSourceAttrDefs, String pathToServersXML) throws CompositeException {
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// Get all data source resources for a given starting path
		ResourceList resourceList = DeployManagerUtil.getDeployManager().getResourcesFromPath(serverId, startPath, ResourceType.CONTAINER.name(), ResourceType.DATA_SOURCE.name(), DetailLevel.FULL.name(), pathToServersXML);

		if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty()){

			// Generated Output File: Instantiate a new DataSourceModule XML
			DatasourceModule dataSourceModule = new ObjectFactory().createDatasourceModule();

			// set the id starting at 0 for the generated output file
			Integer seqId = 0;
			
			// Loop through the data source resource list
			List<Resource> resources = resourceList.getResource();			
			for (Resource resource : resources) {

				// From Composite Server: Cast a resources as a Data Source Resource
				DataSourceResource dsResource = (DataSourceResource)resource;
				
				// Generated Output File: Instantiate a new data source module type
				DataSourceChoiceType dataSourceChoiceType = new DataSourceChoiceType();
				dataSourceModule.getDatasource().add(dataSourceChoiceType);
				
				// Generated Output File: Create the Attribute Defintion list for the data source based on the "Data Source Type"
				AttributeDefsDataSourceType attributeDefsDataSourceType = new AttributeDefsDataSourceType();
				
				// Generated Output File: Set values for each data source
				attributeDefsDataSourceType.setId("ds"+ ++seqId);
				
				if (dsResource.getPath() != null)
					attributeDefsDataSourceType.setResourcePath(dsResource.getPath());
				
				if (dsResource.getType() != null)
					attributeDefsDataSourceType.setResourceType(ResourceTypeSimpleType.valueOf(dsResource.getType().toString()));
				
				if (dsResource.getSubtype() != null)
					attributeDefsDataSourceType.setSubtype(dsResource.getSubtype().toString());

				if (dsResource.getChildCount() != null)
					attributeDefsDataSourceType.setChildCount(BigInteger.valueOf(dsResource.getChildCount()));

				// Generated Output File: Get the Data Source Type and assign attribute definitions
				if (dsResource.getDataSourceType() != null) {
					attributeDefsDataSourceType.setDataSourceType(dsResource.getDataSourceType());
				
					// A single instantiation of an attribute definition for the generated attribute output file
					AttributeDefDataSourceType attributeDefDataSourceType = new AttributeDefDataSourceType();

					// From Composite server: Get the list of attribute definitions for the specific data source type of the data source resource
					List<AttributeDef> attributeDefs = getDataSourceDAO().getDataSourceAttributeDefs(serverId, pathToServersXML, dsResource.getDataSourceType());
					// From Composite server: loop over getDataSourceAttributeDefs
					for (AttributeDef attributeDef : attributeDefs) {
						
						// Generated Output File: Instantiate a new attribute definition entry
						AttributeDefEntryDataSourceType attributeDefEntryDataSourceType = new AttributeDefEntryDataSourceType();

						// Generated Output File: Set the values for the attribute definition entry
						if (attributeDef.getName() != null && attributeDef.getName().length() > 0)
							attributeDefEntryDataSourceType.setName(attributeDef.getName());
						
						if (attributeDef.getType() != null && attributeDef.getType().name().length() > 0)
							attributeDefEntryDataSourceType.setType(attributeDef.getType().name().toString());
						
						if (attributeDef.getUpdateRule() != null && attributeDef.getUpdateRule().name().length() > 0)
							attributeDefEntryDataSourceType.setUpdateRule(attributeDef.getUpdateRule().name().toString());
						
						if (attributeDef.isRequired() != null)
							attributeDefEntryDataSourceType.setRequired(attributeDef.isRequired().toString());
						
						if (attributeDef.getDisplayName() != null && attributeDef.getDisplayName().length() > 0)
							attributeDefEntryDataSourceType.setDisplayName(attributeDef.getDisplayName());
						
						if (attributeDef.isVisible() != null)
							attributeDefEntryDataSourceType.setVisible(attributeDef.isVisible().toString());
						
						attributeDefDataSourceType.getAttributeDef().add(attributeDefEntryDataSourceType);						
					}
					// Assign Attribute Definitions
					attributeDefsDataSourceType.setAttributeDefs(attributeDefDataSourceType);
					
					// Assign data source info and attributes
					dataSourceChoiceType.setAttributeDefsDataSource(attributeDefsDataSourceType);
				}
			}
			// Create the Attribute Defintion List file
			XMLUtils.createXMLFromModuleType(dataSourceModule, pathToDataSourceAttrDefs);
		}	
	}

	/*************************************************************************************
	 * generateDataSourceAttributeDefsByDataSourceType()
	 * 
	 * All methods below this section are used in support of generateDataSourceAttributeDefsByDataSourceType()
	 * 
	 *************************************************************************************/
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.DataSourceManager#generateDataSourceAttributeDefsByDataSourceType(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateDataSourceAttributeDefsByDataSourceType(String serverId, String dataSourceType, String pathToDataSourceAttrDefs, String pathToServersXML) throws CompositeException {
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// Get the Data Source Types
		List<DataSourceTypeInfo> dataSourceTypeInfoList = getDataSourceDAO().getDataSourceTypes(serverId, pathToServersXML);
		// Loop through the data source type list		
		boolean foundDataSourceType = false;
		for (DataSourceTypeInfo dataSourceTypeInfo : dataSourceTypeInfoList) {
			if (dataSourceTypeInfo.getName().toString().equalsIgnoreCase(dataSourceType))
				foundDataSourceType = true;
		}
		
		if (!foundDataSourceType)
			throw new CompositeException("Invalid Data Source Type provided=["+dataSourceType+"].");
		
		// Generated Output File: Instantiate a new DataSourceModule XML
		DatasourceModule dataSourceModule = new ObjectFactory().createDatasourceModule();

		// set the id starting at 0 for the generated output file
		Integer seqId = 0;
			
		// Generated Output File: Instantiate a new data source module type
		DataSourceChoiceType dataSourceChoiceType = new DataSourceChoiceType();
		dataSourceModule.getDatasource().add(dataSourceChoiceType);
				
		// Generated Output File: Create the Attribute Defintion list for the data source based on the "Data Source Type"
		AttributeDefsDataSourceType attributeDefsDataSourceType = new AttributeDefsDataSourceType();
				
		// Generated Output File: Set values for each data source
		attributeDefsDataSourceType.setId("ds"+ ++seqId);
		attributeDefsDataSourceType.setResourcePath(null);
		attributeDefsDataSourceType.setResourceType(null);
		attributeDefsDataSourceType.setSubtype(null);
		attributeDefsDataSourceType.setChildCount(null);
		attributeDefsDataSourceType.setDataSourceType(dataSourceType);
				
		// A single instantiation of an attribute definition for the generated attribute output file
		AttributeDefDataSourceType attributeDefDataSourceType = new AttributeDefDataSourceType();

		// From Composite server: Get the list of attribute definitions for the specific data source type of the data source resource
		List<AttributeDef> attributeDefs = getDataSourceDAO().getDataSourceAttributeDefs(serverId, pathToServersXML, dataSourceType);
		// From Composite server: loop over getDataSourceAttributeDefs
		for (AttributeDef attributeDef : attributeDefs) {
						
			// Generated Output File: Instantiate a new attribute definition entry
			AttributeDefEntryDataSourceType attributeDefEntryDataSourceType = new AttributeDefEntryDataSourceType();

			// Generated Output File: Set the values for the attribute definition entry
			if (attributeDef.getName() != null && attributeDef.getName().length() > 0)
				attributeDefEntryDataSourceType.setName(attributeDef.getName());
						
			if (attributeDef.getType() != null && attributeDef.getType().name().length() > 0)
				attributeDefEntryDataSourceType.setType(attributeDef.getType().name().toString());
					
			if (attributeDef.getUpdateRule() != null && attributeDef.getUpdateRule().name().length() > 0)
				attributeDefEntryDataSourceType.setUpdateRule(attributeDef.getUpdateRule().name().toString());
						
			if (attributeDef.isRequired() != null)
				attributeDefEntryDataSourceType.setRequired(attributeDef.isRequired().toString());
				
			if (attributeDef.getDisplayName() != null && attributeDef.getDisplayName().length() > 0)
				attributeDefEntryDataSourceType.setDisplayName(attributeDef.getDisplayName());
						
			if (attributeDef.isVisible() != null)
				attributeDefEntryDataSourceType.setVisible(attributeDef.isVisible().toString());
						
			attributeDefDataSourceType.getAttributeDef().add(attributeDefEntryDataSourceType);						
		}
		// Assign Attribute Definitions
		attributeDefsDataSourceType.setAttributeDefs(attributeDefDataSourceType);

		// Set the attribute definitions for the attribute defnition data source
		dataSourceChoiceType.setAttributeDefsDataSource(attributeDefsDataSourceType);

		// Create the Attribute Defintion List file
		XMLUtils.createXMLFromModuleType(dataSourceModule, pathToDataSourceAttrDefs);
	}

	
	/*************************************************************************************
	 * generateDataSourceTypes()
	 * 
	 * All methods below this section are used in support of generateDataSourceTypes()
	 * 
	 *************************************************************************************/
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.DataSourceManager#generateDataSourceTypes(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateDataSourceTypes(String serverId, String pathToDataSourceTypesXML, String pathToServersXML) throws CompositeException {
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// Get the Data Source Types
		List<DataSourceTypeInfo> dataSourceTypeInfoList = getDataSourceDAO().getDataSourceTypes(serverId, pathToServersXML);

		if(dataSourceTypeInfoList != null){

			// Generated Output File: Instantiate a new DataSourceModule XML
			DatasourceModule dataSourceModule = new ObjectFactory().createDatasourceModule();

			// Generated Output File: Instantiate a new data source module type
			DataSourceChoiceType dataSourceChoiceType = new DataSourceChoiceType();
			dataSourceModule.getDatasource().add(dataSourceChoiceType);

			// Generated Output File: 
			DataSourceTypesType dataSourceTypesType = new DataSourceTypesType();

			// Loop through the data source type list		
			for (DataSourceTypeInfo dataSourceTypeInfo : dataSourceTypeInfoList) {
			
				// Generated Output File: 
				DataSourceTypeType dataSourceTypeType = new DataSourceTypeType();
			
				// Set the name
				if (dataSourceTypeInfo.getName() != null)
					dataSourceTypeType.setName(dataSourceTypeInfo.getName());

				// Set the type
				if (dataSourceTypeInfo.getType() != null)
					dataSourceTypeType.setType(dataSourceTypeInfo.getType());

				// Set the attributes
				if (dataSourceTypeInfo.getAttributes() != null) {
					for (Attribute attribute : dataSourceTypeInfo.getAttributes().getAttribute()) {
						AttributeDefType dstattribute = new AttributeDefType();
						if (attribute.getName() != null)
							dstattribute.setName(attribute.getName());
						if (attribute.getType() != null)
							dstattribute.setType(AttributeTypeSimpleType.valueOf(attribute.getType().value()));
						if (attribute.getValue() != null)
							dstattribute.setValue(attribute.getValue());
						dataSourceTypeType.getAttribute().add(dstattribute);
					}
				}
				// Assign Data Source Ty[es
				dataSourceTypesType.getDataSourceType().add(dataSourceTypeType);
			}

			dataSourceChoiceType.setDataSourceTypesDataSource(dataSourceTypesType);

			// Create the Data Source Types List file
			XMLUtils.createXMLFromModuleType(dataSourceModule, pathToDataSourceTypesXML);
		}			
	}
	
	/*************************************************************************************
	 * generateDataSourcesResourceListXML()
	 * 
	 * All methods below this section are used in support of generateDataSourcesResourceListXML()
	 * 
	 *************************************************************************************/
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.services.DataSourceManager#generateDataSourcesResourceListXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateDataSourcesResourceListXML(String serverId, String startPath, String pathToDataSourceResourceListXML, String pathToServersXML) throws CompositeException {
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		ResourceList resourceList = DeployManagerUtil.getDeployManager().getResourcesFromPath(serverId, startPath, ResourceType.CONTAINER.name(), ResourceType.DATA_SOURCE.name(), DetailLevel.FULL.name(), pathToServersXML);

		if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty()){

			boolean includeContainers = true;
			String detailLevel = "SIMPLE";
			
			List<Resource> resources = resourceList.getResource();			
			DatasourceModule dataSourceModule = new ObjectFactory().createDatasourceModule();

			Integer seqId = 0;
			for (Resource resource : resources) {

				DataSourceResource dsResource = (DataSourceResource)resource;
				DataSourceChoiceType dataSourceChoiceType = new DataSourceChoiceType();
				
				// Create a generatic structure of the data source resource list
				GenericDataSourceType genericDataSourceType = new GenericDataSourceType();
				genericDataSourceType.setId("ds"+ ++seqId);
				genericDataSourceType.setResourcePath(dsResource.getPath());
				if (dsResource.getType() != null)
					genericDataSourceType.setResourceType(dsResource.getType().value());
				if (dsResource.getSubtype() != null)
					genericDataSourceType.setSubType(dsResource.getSubtype().value());
				if (dsResource.getDataSourceType() != null)
					genericDataSourceType.setDataSourceType(dsResource.getDataSourceType());
				dataSourceChoiceType.setGenericDataSource(genericDataSourceType);
				dataSourceModule.getDatasource().add(dataSourceChoiceType);

				ResourceList childList = getDataSourceDAO().getDataSourceChildResourcesFromPath(serverId, dsResource.getPath(), dsResource.getType().value(), null, includeContainers, detailLevel, pathToServersXML);
				for (Resource child : childList.getResource()) {
					DataSourceChoiceType childDataSourceChoiceType = new DataSourceChoiceType();
					
					// Create a generatic structure of the data source resource list
					GenericDataSourceType childGenericDataSourceType = new GenericDataSourceType();
					childGenericDataSourceType.setId("ds"+ ++seqId);
					childGenericDataSourceType.setResourcePath(child.getPath());
					if (child.getType() != null)
						childGenericDataSourceType.setResourceType(child.getType().value());
					if (child.getSubtype() != null)
						childGenericDataSourceType.setSubType(child.getSubtype().value());
					childDataSourceChoiceType.setGenericDataSource(childGenericDataSourceType);
					dataSourceModule.getDatasource().add(childDataSourceChoiceType);
				}
			}
			XMLUtils.createXMLFromModuleType(dataSourceModule, pathToDataSourceResourceListXML);
		}
			
	}

	/*
	 * Instantiate DAO classes
	 */
	
	/**
	 * @return the dataSourceWSDAO
	 */
	public DataSourceDAO getDataSourceDAO() {
		if(dataSourceDAO == null){
			dataSourceDAO = new DataSourceWSDAOImpl();
		}
		return dataSourceDAO;
	}

	/**
	 * @param dataSourceWSDAO the dataSourceWSDAO to set
	 */
	public void setDataSourceDAO(DataSourceDAO dataSourceDAO) {
		this.dataSourceDAO = dataSourceDAO;
	}
	
	/**
	 * @return the resourceDAO
	 */
	public ResourceDAO getResourceDAO() {
		if(this.resourceDAO == null){
			this.resourceDAO = new ResourceWSDAOImpl();
		}
		return resourceDAO;
	}

	/**
	 * @param resourceDAO the resourceDAO to set
	 */
	public void setResourceDAO(ResourceDAO resourceDAO) {
		this.resourceDAO = resourceDAO;
	}

}
