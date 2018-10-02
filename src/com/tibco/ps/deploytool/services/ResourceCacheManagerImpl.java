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

/**
 * Initial Version:	Mike Tinius :: 6/8/2011		
 * Modifications:	initials :: Date :: reason
 * mtinius :: 1/27/2014 :: Added updateResourceCacheEnabled
 * 
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;

import com.tibco.ps.common.CommonConstants;
import com.tibco.ps.common.exception.CompositeException;
import com.tibco.ps.common.exception.ValidationException;
import com.tibco.ps.common.util.CommonUtils;
import com.tibco.ps.common.util.XMLUtils;
import com.tibco.ps.deploytool.DeployManagerUtil;
import com.tibco.ps.deploytool.dao.ResourceCacheDAO;
import com.tibco.ps.deploytool.dao.wsapi.ResourceCacheWSDAOImpl;
import com.tibco.ps.deploytool.util.DeployUtil;
import com.tibco.ps.deploytool.modules.ObjectFactory;
import com.tibco.ps.deploytool.modules.ResourceCacheBucketPropertiesType;
import com.tibco.ps.deploytool.modules.ResourceCacheCalendarPeriodType;
import com.tibco.ps.deploytool.modules.ResourceCacheConfigType;
import com.tibco.ps.deploytool.modules.ResourceCacheModule;
import com.tibco.ps.deploytool.modules.ResourceCacheRefreshScheduleType;
import com.tibco.ps.deploytool.modules.ResourceCacheRefreshType;
import com.tibco.ps.deploytool.modules.ResourceCacheStorageTargetsType;
import com.tibco.ps.deploytool.modules.ResourceCacheStorageType;
import com.tibco.ps.deploytool.modules.ResourceCacheType;
import com.tibco.ps.deploytool.modules.ResourceCacheBucketPropertiesType;
import com.tibco.ps.deploytool.modules.ResourceTypeSimpleType;
import com.compositesw.services.system.admin.resource.BucketModeType;
import com.compositesw.services.system.admin.resource.BucketPropertiesType;
import com.compositesw.services.system.admin.resource.CacheConfig;
import com.compositesw.services.system.admin.resource.CacheConfig.Refresh;
import com.compositesw.services.system.admin.resource.CacheConfig.Storage;
import com.compositesw.services.system.admin.resource.CalendarPeriod;
import com.compositesw.services.system.admin.resource.ClearRule;
import com.compositesw.services.system.admin.resource.RefreshMode;
import com.compositesw.services.system.admin.resource.Resource;
import com.compositesw.services.system.admin.resource.ResourceList;
import com.compositesw.services.system.admin.resource.ResourceType;
import com.compositesw.services.system.admin.resource.Schedule;
import com.compositesw.services.system.admin.resource.ScheduleMode;
import com.compositesw.services.system.admin.resource.StorageMode;
import com.compositesw.services.system.admin.resource.TargetPathTypePair;
import com.compositesw.services.system.admin.resource.TargetPathTypePairList;
import com.compositesw.services.system.util.common.DetailLevel;


public class ResourceCacheManagerImpl implements ResourceCacheManager{

	private ResourceCacheDAO resourceCacheDAO = null;
	private ArrayList<String> tokenType = new ArrayList<String>();
	private ArrayList<Integer> tokenNum = new ArrayList<Integer>();
	
	private static Log logger = LogFactory.getLog(ResourceCacheManagerImpl.class);

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceCacheManager#updateResourceCache(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void updateResourceCache(String serverId, String resourceIds, String pathToResourceCacheXML, String pathToServersXML) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug(" Entering ResourceCacheManagerImpl.updateResourceCache() with following params "+" serverId: "+serverId+", resourceIds: "+resourceIds+", pathToResourceCacheXML: "+pathToResourceCacheXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
			resourceCacheAction(ResourceCacheDAO.action.UPDATE.name(), serverId, resourceIds, pathToResourceCacheXML, pathToServersXML);
		} catch (CompositeException e) {
			logger.error("Error while updating resource cache: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}				
	}

	
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceCacheManager#clearResourceCache(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void clearResourceCache(String serverId, String resourceIds, String pathToResourceCacheXML, String pathToServersXML) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug(" Entering ResourceCacheManagerImpl.clearResourceCache() with following params "+" serverId: "+serverId+", resourceIds: "+resourceIds+", pathToResourceCacheXML: "+pathToResourceCacheXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
			resourceCacheAction(ResourceCacheDAO.action.CLEAR.name(), serverId, resourceIds, pathToResourceCacheXML, pathToServersXML);
		} catch (CompositeException e) {
			logger.error("Error while clearing resource cache: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}		
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceCacheManager#refreshResourceCache(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void refreshResourceCache(String serverId, String resourceIds, String pathToResourceCacheXML, String pathToServersXML) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug(" Entering ResourceCacheManagerImpl.refreshResourceCache() with following params "+" serverId: "+serverId+", resourceIds: "+resourceIds+", pathToResourceCacheXML: "+pathToResourceCacheXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
			resourceCacheAction(ResourceCacheDAO.action.REFRESH.name(), serverId, resourceIds, pathToResourceCacheXML, pathToServersXML);
		} catch (CompositeException e) {
			logger.error("Error while refreshing resource cache: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}		
	}

	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceCacheManager#updateResourceCacheEnabled(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void updateResourceCacheEnabled(String serverId, String resourceIds, String pathToResourceCacheXML, String pathToServersXML) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug(" Entering ResourceCacheManagerImpl.updateResourceCacheEnabled() with following params "+" serverId: "+serverId+", resourceIds: "+resourceIds+", pathToResourceCacheXML: "+pathToResourceCacheXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
			resourceCacheAction(ResourceCacheDAO.action.ENABLE_DISABLE.name(), serverId, resourceIds, pathToResourceCacheXML, pathToServersXML);
		} catch (CompositeException e) {
			logger.error("Error while enabling/disabling resource cache: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}				
	}

	private void resourceCacheAction(String actionName, String serverId, String resourceIds, String pathToResourceCacheXML, String pathToServersXML) throws CompositeException {

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToResourceCacheXML)) {
			throw new CompositeException("File ["+pathToResourceCacheXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		String prefix = "dataSourceAction";
		String processedIds = null;

		// Get the configuration property file set in the environment with a default of deploy.properties
		String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");

		// Extract variables for the resourceIds
		resourceIds = CommonUtils.extractVariable(prefix, resourceIds, propertyFile, true);

		// Set the Module Action Objective
		String s1 = (resourceIds == null) ? "no_resourceIds" : "Ids="+resourceIds;
		System.setProperty("MODULE_ACTION_OBJECTIVE", actionName+" : "+s1);

		try {
			List<ResourceCacheType> resourceCacheModuleList = getResourceCache(serverId, resourceIds, pathToResourceCacheXML, pathToServersXML);

			if (resourceCacheModuleList != null && resourceCacheModuleList.size() > 0) {
	
				// Loop over the list of resource cache entries and apply their configurations to the target CIS instance.
				for (ResourceCacheType resourceCacheModule : resourceCacheModuleList) {

					// Get the identifier and convert any $VARIABLES
					String identifier = CommonUtils.extractVariable(prefix, resourceCacheModule.getId(), propertyFile, true);
					
					/**
					 * Possible values for resource cache 
					 * 1. csv string like rc1,rc2 (we process only resource names which are passed in)
					 * 2. '*' or whatever is configured to indicate all resources (we process all resources in this case)
					 * 3. csv string with '-' or whatever is configured to indicate exclude resources as prefix 
					 * 	  like -rc1,rc2 (we ignore passed in resources and process rest of the in the input xml
					 */
					 if(DeployUtil.canProcessResource(resourceIds, identifier)) 
					 {
						// Add to the list of processed ids
						if (processedIds == null)
							processedIds = "";
						else
							processedIds = processedIds + ",";
						processedIds = processedIds + identifier;
						 
						CacheConfig cacheConfig = new CacheConfig();			 
						String resourceCachePath = CommonUtils.extractVariable(prefix, resourceCacheModule.getResourcePath(), propertyFile, true);
						String resourceCacheType = CommonUtils.extractVariable(prefix, resourceCacheModule.getResourceType().toString(), propertyFile, false);
						
						// Set the Module Action Objective
						s1 = identifier+"=" + ((resourceCachePath == null) ? "no_resourceCachePath" : resourceCachePath);
						System.setProperty("MODULE_ACTION_OBJECTIVE", actionName+" : "+s1);

						if(logger.isInfoEnabled()){
							logger.info("processing action "+actionName+" on resource cache "+resourceCachePath);
						}
						if (actionName.equals(ResourceCacheDAO.action.ENABLE_DISABLE.name().toString())) {
							
							if (resourceCacheModule.getCacheConfig() != null) {
								// Set enabled flag
								Boolean enabled = null;
								if (resourceCacheModule.getCacheConfig().isEnabled() != null) {
									enabled = resourceCacheModule.getCacheConfig().isEnabled();
								}
								
								// Set the Module Action Objective
								s1 = identifier+"=" + ((resourceCachePath == null) ? "no_resourceCachePath" : resourceCachePath);
								String enable_disabled_action = (enabled == true) ? "ENABLE" : "DISABLE";
								System.setProperty("MODULE_ACTION_OBJECTIVE", enable_disabled_action+" : "+s1);

								updateResourceCacheEnabledAll(serverId, resourceCachePath, resourceCacheType, pathToServersXML, enabled);
							}
						} else {
							if (resourceCacheModule.getCacheConfig() != null) {

								// Set allOrNothing if it exists
								if (resourceCacheModule.getCacheConfig().isAllOrNothing() != null) {
									cacheConfig.setAllOrNothing(resourceCacheModule.getCacheConfig().isAllOrNothing());
								}
								// Set configured if it exists
								if (resourceCacheModule.getCacheConfig().isConfigured() != null) {
									cacheConfig.setConfigured(resourceCacheModule.getCacheConfig().isConfigured());
								}
								// Set enabled if it exists
								if (resourceCacheModule.getCacheConfig().isEnabled() != null) {
									cacheConfig.setEnabled(resourceCacheModule.getCacheConfig().isEnabled());
								}
								// Set incremental if it exists
								if (resourceCacheModule.getCacheConfig().isIncremental() != null) {
									cacheConfig.setIncremental(resourceCacheModule.getCacheConfig().isIncremental());
								}
								// Set the storage if it exists
								if (resourceCacheModule.getCacheConfig().getStorage() != null) {
									Storage storage = new Storage();
									// Set use default cache storage if it exists
									if (resourceCacheModule.getCacheConfig().getStorage().isUseDefaultCacheStorage() != null) {
										storage.setUseDefaultCacheStorage(resourceCacheModule.getCacheConfig().getStorage().isUseDefaultCacheStorage());
									}
									if (resourceCacheModule.getCacheConfig().getStorage().getMode() != null) {
										storage.setMode(StorageMode.valueOf(resourceCacheModule.getCacheConfig().getStorage().getMode()));
									}
									// Set the bucket mode: AUTO_GEN or MANUAL
									if (resourceCacheModule.getCacheConfig().getStorage().getBucketMode() != null) {
										storage.setBucketMode(BucketModeType.valueOf(resourceCacheModule.getCacheConfig().getStorage().getBucketMode()));
									}
									// Set the bucket properties if it exists
									if (resourceCacheModule.getCacheConfig().getStorage().getBucketProperties() != null) {
										BucketPropertiesType bucketProperties = new BucketPropertiesType();
										if (resourceCacheModule.getCacheConfig().getStorage().getBucketProperties().getBucketCatalog() != null) {
											bucketProperties.setBucketCatalog(resourceCacheModule.getCacheConfig().getStorage().getBucketProperties().getBucketCatalog());
										}
										if (resourceCacheModule.getCacheConfig().getStorage().getBucketProperties().getBucketSchema() != null) {
											bucketProperties.setBucketSchema(resourceCacheModule.getCacheConfig().getStorage().getBucketProperties().getBucketSchema());
										}
										if (resourceCacheModule.getCacheConfig().getStorage().getBucketProperties().getBucketPrefix() != null) {
											bucketProperties.setBucketPrefix(resourceCacheModule.getCacheConfig().getStorage().getBucketProperties().getBucketPrefix());
										}
										if (resourceCacheModule.getCacheConfig().getStorage().getBucketProperties().getNumBuckets() != null) {
											bucketProperties.setNumBuckets(resourceCacheModule.getCacheConfig().getStorage().getBucketProperties().getNumBuckets().intValue());
										}
										storage.setBucketProperties(bucketProperties);
									}
									// Set drop create index if it exists
									if (resourceCacheModule.getCacheConfig().getStorage().isDropCreateIdx() != null) {
										storage.setDropCreateIdx(resourceCacheModule.getCacheConfig().getStorage().isDropCreateIdx());
									}
									// Set the storage data source path
									if (resourceCacheModule.getCacheConfig().getStorage().getStorageDataSourcePath() != null) {
										storage.setStorageDataSourcePath(CommonUtils.extractVariable(prefix, resourceCacheModule.getCacheConfig().getStorage().getStorageDataSourcePath(), propertyFile, true));
									}
									if (resourceCacheModule.getCacheConfig().getStorage().getStorageTargets() != null) {
										// Define the Target Storage List
										TargetPathTypePairList entry = new TargetPathTypePairList();
		
										for (ResourceCacheStorageTargetsType storageTarget : resourceCacheModule.getCacheConfig().getStorage().getStorageTargets()) {
											// Define the Target Storage Entry
											TargetPathTypePair targetPair = new TargetPathTypePair();
											// Set the target pair entry
											targetPair.setPath(CommonUtils.extractVariable(prefix, storageTarget.getPath(), propertyFile, true));
											targetPair.setTargetName(CommonUtils.extractVariable(prefix, storageTarget.getTargetName(), propertyFile, false));
											targetPair.setType(ResourceType.valueOf(storageTarget.getType().toUpperCase()));
											// Add the target pair entry to the list
											entry.getEntry().add(targetPair);						
										}
										storage.setStorageTargets(entry);
									}
									cacheConfig.setStorage(storage);

								} //end::if (resourceCacheModule.getCacheConfig().getStorage() != null) {
								
								// Set the refresh if it exists
								if (resourceCacheModule.getCacheConfig().getRefresh() != null) {
									Refresh refresh = new Refresh();
									String refreshMode = resourceCacheModule.getCacheConfig().getRefresh().getMode().toUpperCase();
									refresh.setMode(RefreshMode.valueOf(refreshMode));
									
									if (resourceCacheModule.getCacheConfig().getRefresh().getSchedule() != null) {
										if (refreshMode.equalsIgnoreCase("SCHEDULED")) {
											
											Schedule schedule = new Schedule();
		
											if (resourceCacheModule.getCacheConfig().getRefresh().getSchedule().getStartTime() != null) {							
												schedule.setStartTime(resourceCacheModule.getCacheConfig().getRefresh().getSchedule().getStartTime());
											}
		
											if (resourceCacheModule.getCacheConfig().getRefresh().getSchedule().getRefreshPeriod().getPeriod() != null) {
												String period = resourceCacheModule.getCacheConfig().getRefresh().getSchedule().getRefreshPeriod().getPeriod().toUpperCase();
												
												// Set the mode to INTERVAL
												if (period.equalsIgnoreCase("SECOND") || period.equalsIgnoreCase("MINUTE")) {
													schedule.setMode(ScheduleMode.valueOf("INTERVAL"));
													Long interval = convertPeriodCount(period, resourceCacheModule.getCacheConfig().getRefresh().getSchedule().getRefreshPeriod().getCount(), "seconds");
													schedule.setInterval(interval.intValue());
												} else {
													schedule.setMode(ScheduleMode.valueOf("CALENDAR"));
													schedule.setPeriod(CalendarPeriod.valueOf(resourceCacheModule.getCacheConfig().getRefresh().getSchedule().getRefreshPeriod().getPeriod().toUpperCase()));
													Integer count = (int) resourceCacheModule.getCacheConfig().getRefresh().getSchedule().getRefreshPeriod().getCount();
													schedule.setCount(count);
												}
											}
											refresh.setSchedule(schedule);
										} 
									}
									cacheConfig.setRefresh(refresh);
								} //end::if (resourceCacheModule.getCacheConfig().getRefresh() != null) {
	
								// Set the Expiration if it exists
								if (resourceCacheModule.getCacheConfig().getExpirationPeriod() != null) {
									Long milliCount = convertPeriodCount(resourceCacheModule.getCacheConfig().getExpirationPeriod().getPeriod(), resourceCacheModule.getCacheConfig().getExpirationPeriod().getCount(), "milliseconds");
									cacheConfig.setExpirationPeriod(milliCount);
								}
								// Set the First Refresh Callback if it exists
								if (resourceCacheModule.getCacheConfig().getFirstRefreshCallback() != null) {
									cacheConfig.setFirstRefreshCallback(resourceCacheModule.getCacheConfig().getFirstRefreshCallback());
								}
								// Set the Second Refresh Callback if it exists
								if (resourceCacheModule.getCacheConfig().getSecondRefreshCallback() != null) {
									cacheConfig.setSecondRefreshCallback(resourceCacheModule.getCacheConfig().getSecondRefreshCallback());
								}
								// Set the clear rule if it exists
								if (resourceCacheModule.getCacheConfig().getClearRule() != null) {
									cacheConfig.setClearRule(ClearRule.valueOf(resourceCacheModule.getCacheConfig().getClearRule().toUpperCase()));
								}
							} //end::if (resourceCacheModule.getCacheConfig() != null) {
							
							// Validate that the resource exists before acting on it.
							Boolean validateResourceExists = true;

							// Execute takeResourceCacheAction()
							getResourceCacheDAO().takeResourceCacheAction(actionName, resourceCachePath, resourceCacheType, cacheConfig, serverId, pathToServersXML, validateResourceExists);
							
						} // end:: if (actionName.equals(ResourceCacheDAO.action.ENABLE_DISABLE.name().toString())) {
					}  // end:: if(DeployUtil.canProcessResource(resourceIds, identifier)) {
				} // end:: for (ResourceCacheType resourceCache : resourceCacheList) {
				
				// Determine if any resourceIds were not processed and report on this
				if (processedIds != null) {
					if(logger.isInfoEnabled()){
						logger.info("ResourceCache entries processed="+processedIds);
					}
				} else {
					if(logger.isInfoEnabled()){
						String msg = "Warning: No resource cache entries were processed for the input list.  resourceIds="+resourceIds;
						logger.info(msg);
						System.setProperty("MODULE_ACTION_MESSAGE", msg);
					}		
				}
			} else {
				if(logger.isInfoEnabled()){
					String msg = "Warning: No resource cache entries found for ResourceCache Module XML at path="+pathToResourceCacheXML;
					logger.info(msg);
					System.setProperty("MODULE_ACTION_MESSAGE", msg);
				}				
			}
		} catch (CompositeException e) {
			logger.error("Error on resource cache action ("+actionName+"): " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
	}

	private List<ResourceCacheType> getResourceCache(String serverId, String resourceIds,	String pathToResourceCacheXML, String pathToServersXML) {
		// validate incoming arguments
		if(serverId == null || serverId.trim().length() ==0 || resourceIds == null || resourceIds.trim().length() ==0 || pathToServersXML == null || pathToServersXML.trim().length() ==0 || pathToResourceCacheXML == null || pathToResourceCacheXML.trim().length() ==0){
			throw new ValidationException("Invalid Arguments");
		}

		try {
			//using jaxb convert xml to corresponding java objects
			ResourceCacheModule resourceCacheModuleType = (ResourceCacheModule)XMLUtils.getModuleTypeFromXML(pathToResourceCacheXML);
			if(resourceCacheModuleType != null && resourceCacheModuleType.getResourceCache() != null && !resourceCacheModuleType.getResourceCache().isEmpty()){
				return resourceCacheModuleType.getResourceCache();
			}
		} catch (CompositeException e) {
			logger.error("Error while parsing resource cache xml" , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
		return null;
	}
	
	
	private void updateResourceCacheEnabledAll(String serverId, String resourcePath, String resourceType, String pathToServersXML, Boolean enabled) {
	
		// Set the cache enabled flag for all cache configured resources found within this starting resource path
		if (resourceType.equalsIgnoreCase(ResourceType.CONTAINER.name())) {

			// Don't set any filters
			String filter = null;
			
			// Don't validate the paths exists since the list of paths has already been acquired from the server.
			Boolean validateResourceExists = false;

			// Retrieve the list of Resources by invoking the CIS Web Service API
			ResourceList resourceList = new ResourceList();
			
			// Get all resources in the given resource path
			resourceList.getResource().addAll(DeployManagerUtil.getDeployManager().getResourcesFromPath(serverId, resourcePath, resourceType, filter, DetailLevel.FULL.name(), pathToServersXML).getResource());
	
			// Continue if there is a list
			if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty()){
	
				// Assign the list of resources to a local Attribute type variable
				List<Resource> resources = resourceList.getResource();			
					
				// Iterate over the retrieved Server Attribute List
				for (Resource resource : resources) {
	
					// Set the resource type
					String resourceCachePath = resource.getPath();
					// Set the resource path
					String resourceCacheType = resource.getType().name();
					// Define the original cache enabled flag
					Boolean enabledOrig = null;
	
					// Get the resource cache configuration from the CIS server for a given path and type
					CacheConfig cacheConfig = getResourceCacheDAO().getResourceCacheConfig(resourceCachePath, resourceCacheType, serverId, pathToServersXML, validateResourceExists);
	
					if (cacheConfig != null) {
						
						// Only set cacheConfig objects if it is configured
						if (cacheConfig.isConfigured()) {
	
							// Set the original enabled value
							if (cacheConfig.isEnabled() != null) {
								enabledOrig = cacheConfig.isEnabled();
							}
							CacheConfig cacheConfigNew = new CacheConfig();
							
							// Set the cache config enabled to the enabled value passed in.
							cacheConfigNew.setEnabled(enabled);
							
							// Execute takeResourceCacheAction()
							getResourceCacheDAO().takeResourceCacheAction(ResourceCacheDAO.action.UPDATE.name(), resourceCachePath, resourceCacheType, cacheConfigNew, serverId, pathToServersXML, validateResourceExists);
	
							// Output an info line with the results
							if(logger.isInfoEnabled()){
								logger.info("Cache operation (enable)="+enabled+"  prevStatus="+enabledOrig+"  currStatus="+enabled+"  resourceType="+resourceCacheType+"  resourcePath="+resourceCachePath);
							}
						}
					}
				}
			}	
		// Set the cache enabled flag for this cache configured resource found at this resource path
		} else {
			// Define the original cache enabled flag
			Boolean enabledOrig = null;

			// Validate the path exists prior to continuing.
			Boolean validateResourceExists = true;

			// Get the resource cache configuration from the CIS server for a given path and type
			CacheConfig cacheConfig = getResourceCacheDAO().getResourceCacheConfig(resourcePath, resourceType, serverId, pathToServersXML, validateResourceExists);

			if (cacheConfig != null) {
				
				// Only set cacheConfig objects if it is configured
				if (cacheConfig.isConfigured()) {

					// Set the original enabled value
					if (cacheConfig.isEnabled() != null) {
						enabledOrig = cacheConfig.isEnabled();
					}
					
					CacheConfig cacheConfigNew = new CacheConfig();
					
					// Set the cache config enabled to the enabled value passed in.
					cacheConfigNew.setEnabled(enabled);
					
					// The resource has already been validated once.
					validateResourceExists = false;
					
					// Execute takeResourceCacheAction()
					getResourceCacheDAO().takeResourceCacheAction(ResourceCacheDAO.action.UPDATE.name(), resourcePath, resourceType, cacheConfigNew, serverId, pathToServersXML, validateResourceExists);

					// Output an info line with the results
					if(logger.isInfoEnabled()){
						logger.info("Cache operation (enable)="+enabled+"  prevStatus="+enabledOrig+"  currStatus="+enabled+"  resourceType="+resourceType+"  resourcePath="+resourcePath);
					}
				}
			}			
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.ResourceCacheManager#generateResourceCacheXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateResourceCacheXML(String serverId, String startPath, String pathToResourceCacheXML, String pathToServersXML, String options) throws CompositeException {

		// Set the command and action name
		String command = "generateResourceCacheXML";
		String actionName = "CREATE_XML";

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// Set the Module Action Objective
		String s1 = (startPath == null) ? "no_startPath" : "Path="+startPath;
		System.setProperty("MODULE_ACTION_OBJECTIVE", "GENERATE : "+s1);

		String prefix = "generateResourceCacheXML";
		// Get the configuration property file set in the environment with a default of deploy.properties
		String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");
		// Extract any variables from the startPath
		startPath = CommonUtils.extractVariable(prefix, startPath, propertyFile, true);
		
		// Prepare a local ResourceCacheModule XML variable for creating a list of "ResourceCache" nodes
		// This XML variable will be written out to the specified file. 
		ResourceCacheModule resourceCacheModule = new ObjectFactory().createResourceCacheModule();

		// Use the options list to set boolean variables for getting resources based on TABLE,PROCEDURE,CONFIGURED and NONCONFIGURED
		Boolean getResourceType_TABLE = false;
		Boolean getResourceType_PROCDURE = false;
		Boolean getConfigured = false;
		Boolean getNonConfigured = false;
		String filter = null;
		// If the options field is null or empty the default is to get both resource types and both configured and non-configured resource cache types
		if (options == null || options.isEmpty() || options.equalsIgnoreCase("")) {
			// TABLE and PROCEDURE are the valid resource types that can have caching configured
			getResourceType_TABLE = true;
			getResourceType_PROCDURE = true;
			getConfigured = true;
			getNonConfigured = true;
		} else {
			// Insure there are no spaces on the end and replace any space in between with commas to insure a comma separated list
			options = options.trim().replaceAll(Matcher.quoteReplacement(" "), ",");
			StringTokenizer st = new StringTokenizer(options, ",");
		    while (st.hasMoreTokens()) {
		    	String token = st.nextToken();
				if (token.equalsIgnoreCase("TABLE")) {
					getResourceType_TABLE = true;
					filter = "TABLE";
				}
				if (token.equalsIgnoreCase("PROCEDURE")) {
					getResourceType_PROCDURE = true;
					if (filter == null) {
						filter = "PROCEDURE";
					} else {
						filter = filter+",PROCEDURE";
					}
				}
				if (token.equalsIgnoreCase("CONFIGURED")) {
					getConfigured = true;
				}
				if (token.equalsIgnoreCase("NONCONFIGURED")) {
					getNonConfigured = true;
				}
		    }
			// If neither TABLE or PROCEDURE is found the default is to get both resource types
			if (!getResourceType_TABLE && !getResourceType_PROCDURE) {
				getResourceType_TABLE = true;
				getResourceType_PROCDURE = true;
				filter = "TABLE,PROCEDURE";
			}
			// If neither CONFIGURED or NONCONFIGURED is found the default is to get both configured and non-configured cache resources
			if (!getConfigured && !getNonConfigured) {
				getConfigured = true;
				getNonConfigured = true;
			}
		}
		
		// Retrieve the list of Resources by invoking the CIS Web Service API
		ResourceList resourceList = new ResourceList();

		if (getResourceType_TABLE) {
			resourceList.getResource().addAll(DeployManagerUtil.getDeployManager().getResourcesFromPath(serverId, startPath, ResourceType.CONTAINER.name(), filter, DetailLevel.FULL.name(), pathToServersXML).getResource());
		}

		// Continue if there is a list
		if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty()){
			
			// Don't validate the resource exists since the paths have already been acquired from the server.
			Boolean validateResourceExists = false;

			// Assign the list of resources to a local Attribute type variable
			List<Resource> resources = resourceList.getResource();			
				
			// Iterate over the retrieved Server Attribute List
			for (Resource resource : resources) {
				// Define the resource cache type
				ResourceCacheType resourceCacheType = new ResourceCacheType();
				
				// Set the resource path
				resourceCacheType.setResourcePath(resource.getPath());
				// Set the resource type
				resourceCacheType.setResourceType(ResourceTypeSimpleType.valueOf(resource.getType().toString()));

				// Get the resource cache configuration from the CIS server for a given path and type
				CacheConfig cacheConfig = getResourceCacheDAO().getResourceCacheConfig(resource.getPath(), resource.getType().toString(), serverId, pathToServersXML, validateResourceExists);

				if (cacheConfig != null) {
					// Define the resource cache config type
					ResourceCacheConfigType resourceCacheConfigType = new ResourceCacheConfigType();
					
					// Set the cache configured element
					resourceCacheConfigType.setConfigured(cacheConfig.isConfigured());
					
					// Only set cacheConfig objects if it is configured
					if (cacheConfig.isConfigured()) {

						if (cacheConfig.isAllOrNothing() != null) {
							resourceCacheConfigType.setAllOrNothing(cacheConfig.isAllOrNothing());
						}
						if (cacheConfig.isEnabled() != null) {
							resourceCacheConfigType.setEnabled(cacheConfig.isEnabled());
						}
						if (cacheConfig.isIncremental() != null) {
							resourceCacheConfigType.setIncremental(cacheConfig.isIncremental());
						}
						if (cacheConfig.getExpirationPeriod() != null) {
							// Define the calendar period type
							ResourceCacheCalendarPeriodType calendarPeriod = new ResourceCacheCalendarPeriodType();
							
							Period period = new Period();
							period = period.getCalandarPeriod(cacheConfig.getExpirationPeriod(), "milliseconds");
							calendarPeriod.setPeriod(period.getPeriod());
							calendarPeriod.setCount(period.getCount());
							// Set the expiration period
							resourceCacheConfigType.setExpirationPeriod(calendarPeriod);
						}
						if (cacheConfig.getFirstRefreshCallback() != null) {
							resourceCacheConfigType.setFirstRefreshCallback(cacheConfig.getFirstRefreshCallback().toString());
						}
						if (cacheConfig.getSecondRefreshCallback() != null) {
							resourceCacheConfigType.setSecondRefreshCallback(cacheConfig.getSecondRefreshCallback().toString());
						}
						if (cacheConfig.getClearRule() != null) {
							resourceCacheConfigType.setClearRule(cacheConfig.getClearRule().toString());
						}
						
						if (cacheConfig.getRefresh() != null) {
							// Define the resource refresh type
							ResourceCacheRefreshType resourceCacheRefreshType = new ResourceCacheRefreshType();
							
							// Modes: MANUAL or SCHEDULED
							if (cacheConfig.getRefresh().getMode() != null) {
								resourceCacheRefreshType.setMode(cacheConfig.getRefresh().getMode().toString());
							}
							
							// Continue if not null and the mode is SCHEDULED -- no need to print out a schedule if MANUAL
							if (cacheConfig.getRefresh().getSchedule() != null) {
								if (cacheConfig.getRefresh().getMode().toString().equalsIgnoreCase("SCHEDULED")) {
									// Define the resource refresh schedule
									ResourceCacheRefreshScheduleType resourceCacheRefreshScheduleType = new ResourceCacheRefreshScheduleType();
									
									if (cacheConfig.getRefresh().getSchedule().getStartTime() != null) {
										resourceCacheRefreshScheduleType.setStartTime(cacheConfig.getRefresh().getSchedule().getStartTime());
									}

									if (cacheConfig.getRefresh().getSchedule().getMode().toString().equalsIgnoreCase("INTERVAL")) {
										// Define the calendar period type
										ResourceCacheCalendarPeriodType calendarPeriod = new ResourceCacheCalendarPeriodType();

										
										if (cacheConfig.getRefresh().getSchedule().getInterval() != null) {
											Period period = new Period();
											period = period.getCalandarPeriod(cacheConfig.getExpirationPeriod(), "seconds");
											calendarPeriod.setPeriod(period.getPeriod());
											calendarPeriod.setCount(period.getCount());
											// Set the refresh period
											resourceCacheRefreshScheduleType.setRefreshPeriod(calendarPeriod);
										}
									}

									if (cacheConfig.getRefresh().getSchedule().getMode().toString().equalsIgnoreCase("CALENDAR")) {
										// Define the calendar period type
										ResourceCacheCalendarPeriodType calendarPeriod = new ResourceCacheCalendarPeriodType();

										if (cacheConfig.getRefresh().getSchedule().getPeriod() != null) {
											calendarPeriod.setPeriod(cacheConfig.getRefresh().getSchedule().getPeriod().toString());
											calendarPeriod.setCount(cacheConfig.getRefresh().getSchedule().getCount());
											// Set the refresh period
											resourceCacheRefreshScheduleType.setRefreshPeriod(calendarPeriod);
										}
									}
									// set the resource cache refresh schedule
									resourceCacheRefreshType.setSchedule(resourceCacheRefreshScheduleType);										
								}
							}
							// set the resource refresh
							resourceCacheConfigType.setRefresh(resourceCacheRefreshType);
						}
						
						if (cacheConfig.getStorage() != null) {
							// Define the resource storage type
							ResourceCacheStorageType resourceCacheStorageType = new ResourceCacheStorageType();
							
							if (cacheConfig.getStorage().isUseDefaultCacheStorage() != null) {
								resourceCacheStorageType.setUseDefaultCacheStorage(cacheConfig.getStorage().isUseDefaultCacheStorage());
							}
							if (cacheConfig.getStorage().getMode() != null) {
								resourceCacheStorageType.setMode(cacheConfig.getStorage().getMode().toString());
							}
							if (cacheConfig.getStorage().getBucketMode() != null) {
								resourceCacheStorageType.setBucketMode(cacheConfig.getStorage().getBucketMode().toString());
							}
							if (cacheConfig.getStorage().getBucketProperties() != null) {								
								ResourceCacheBucketPropertiesType bucketProperties = new ResourceCacheBucketPropertiesType();
								
								if (cacheConfig.getStorage().getBucketProperties().getBucketCatalog() != null) {
									bucketProperties.setBucketCatalog(cacheConfig.getStorage().getBucketProperties().getBucketCatalog());
								}
								if (cacheConfig.getStorage().getBucketProperties().getBucketSchema() != null) {
									bucketProperties.setBucketSchema(cacheConfig.getStorage().getBucketProperties().getBucketSchema());
								}
								if (cacheConfig.getStorage().getBucketProperties().getBucketPrefix() != null) {
									bucketProperties.setBucketPrefix(cacheConfig.getStorage().getBucketProperties().getBucketPrefix());
								}
								bucketProperties.setNumBuckets(BigInteger.valueOf(cacheConfig.getStorage().getBucketProperties().getNumBuckets()));

								// Set the bucket properties
								resourceCacheStorageType.setBucketProperties(bucketProperties);
							}
							if (cacheConfig.getStorage().isDropCreateIdx() != null) {
								resourceCacheStorageType.setDropCreateIdx(cacheConfig.getStorage().isDropCreateIdx());
							}
							if (cacheConfig.getStorage().getStorageDataSourcePath() != null) {
								resourceCacheStorageType.setStorageDataSourcePath(cacheConfig.getStorage().getStorageDataSourcePath());
							}
							
							if (cacheConfig.getStorage().getStorageTargets() != null) {

								for (TargetPathTypePair storageTarget : cacheConfig.getStorage().getStorageTargets().getEntry()) {
									// Define the storage target type
									ResourceCacheStorageTargetsType entry = new ResourceCacheStorageTargetsType();
									// Set the storage target entry
									entry.setPath(storageTarget.getPath());
									entry.setTargetName(storageTarget.getTargetName());
									entry.setType(storageTarget.getType().toString());
									resourceCacheStorageType.getStorageTargets().add(entry);
								}
							}
							// Set the resource storage type
							resourceCacheConfigType.setStorage(resourceCacheStorageType );
						}
					}
					// Set the resource cache configuration
					resourceCacheType.setCacheConfig(resourceCacheConfigType );
					
					// Add an XML node if the resource is configured for cache and the user is requesting CONFIGURED
					if (cacheConfig.isConfigured() && getConfigured) {
						// Set basic values for Id, Path and Type					
						resourceCacheType.setId(getTokenId("cache"));

						// Add a new row to the XML for Resource Cache
						resourceCacheModule.getResourceCache().add(resourceCacheType);						
					}
					// Add an XML node if the resource is not configured for cache and the user is requesting NONCONFIGURED
					if (!cacheConfig.isConfigured() && getNonConfigured) {
						// Set basic values for Id, Path and Type
						resourceCacheType.setId(getTokenId("cache"));

						// Add a new row to the XML for Resource Cache
						resourceCacheModule.getResourceCache().add(resourceCacheType);						
					}
				}
			}
		}

		// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
		if (CommonUtils.isExecOperation()) 
		{					
			// Generate the XML file
			XMLUtils.createXMLFromModuleType(resourceCacheModule, pathToResourceCacheXML);
		} else {
			logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
		}
	}

	/**
	 * @return the token and numerical Id 
	 * Using an ArrayList, track the various tokens that are passed in and increment a number.
	 * Return the token and the number concatenated together
	 * services
	 * shared
	 * users
	 */
	private String getTokenId(String name) {
		for (int i=0; i < tokenType.size(); i++) {
			if (tokenType.get(i).equalsIgnoreCase(name)) {
				int n = tokenNum.get(i).intValue();
				tokenNum.set(i, Integer.valueOf(++n));
				return name+n;
			}
		}
		tokenType.add(name);
		tokenNum.add(Integer.valueOf(1));
	    return name+1;
	}

	/**
	 * @return the conversion in seconds or milliseconds for a period and count
	 * period [SECOND,MINUTE,HOUR,DAY,WEEK,MONTH,YEAR]
	 * periodCount is measured in either milliseconds or seconds
	 * qualifier is either "milliseconds" or "seconds"
	 * Based on the period count that is passed, calculate what the period is
	 */
	private Long convertPeriodCount(String period, Long periodCount, String qualifier) {

		// Assume "SECOND" as the default
		Long count = periodCount;

		if (period.equalsIgnoreCase("MINUTE")) {
			count = periodCount * 60;
		}
		if (period.equalsIgnoreCase("HOUR")) {
			count = periodCount * 3600;
		}
		if (period.equalsIgnoreCase("DAY")) {
			count = periodCount * 86400;
		}
		if (period.equalsIgnoreCase("WEEK")) {
			count = periodCount * 604800;
		}
		if (period.equalsIgnoreCase("MONTH")) {
			count = periodCount * 2592000;
		}
		if (period.equalsIgnoreCase("YEAR")) {
			count = periodCount * 31536000;
		}
		// if negative then set to 0
		if (count < 0) {
			count = (long) 0;
		}
		// if the qualifier is "milliseconds" multiply by 1000
		if (qualifier.equalsIgnoreCase("milliseconds")) {
			count = count * 1000;
		}		
		return count;
	}

	// period object is used to hold a period and count
	public class Period {
	    public String period = null;
	    public Long count = null;
	    //constructor
	    public Period() {
	    	period = null;
	    	count = null;
	    }
	    public void setPeriod(String u) {
	    	period = u;
	    }
	    public void setCount(Long c) {
	    	count = c;
	    }
	    public String getPeriod() {
	    	return this.period;
	    }
	    public Long getCount() {
	    	return this.count;
	    }
		/**
		 * @return the Calendar period count for the given period [SECOND,MINUTE,HOUR,DAY,WEEK,MONTH,YEAR]
		 * periodCount is measured in either milliseconds or seconds
		 * qualifier is either "milliseconds" or "seconds"
		 * Based on the period count that is passed, calculate what the period is
		 */
		public Period getCalandarPeriod(Long periodCount, String qualifier) {
			// Initialize a new return variable
			Period period = new Period();
			Long seconds = null;
			// When calculating expiration use milliseconds
			if (qualifier.equalsIgnoreCase("milliseconds")) { // periodCount is "milliseconds"
				seconds = periodCount / 1000;
			} else {   // periodCount is "seconds"
				seconds = periodCount;
			}
			//If the the periodCount is not divisible by 60 seconds then assume the period is seconds
			Long remainder = seconds % 60;
			if (remainder > 0) {
				period.setPeriod("SECOND");
				period.setCount(seconds);
				return period;
			}
			if (seconds < 60) { // SECOND
				period.setPeriod("SECOND");
				if (seconds < 0) {
					period.setCount((long) 0);
				} else {
					period.setCount(seconds);
				}
				return period;
			}
			if (seconds >= 60 && seconds < 3600) { // MINUTE
				period.setCount(seconds / 60);
				period.setPeriod("MINUTE");
				return period;
			}
			if (seconds >= 3600 && seconds < 86400) { // HOUR
				period.setCount(seconds / 3600);
				period.setPeriod("HOUR");
				return period;
			}
			if (seconds >= 86400 && seconds < 604800) { // DAY
				period.setCount(seconds / 86400);
				period.setPeriod("DAY");
				return period;
			}
			if (seconds >= 604800 && seconds < 2592000) { // WEEK
				period.setCount(seconds / 604800);
				period.setPeriod("WEEK");
				return period;
			}
			if (seconds >= 2592000 && seconds < 31536000) { // MONTH
				period.setCount(seconds / 2592000);
				period.setPeriod("MONTH");
				return period;
			}
			if (seconds >= 31536000) { // YEAR
				period.setCount(seconds / 31536000);
				period.setPeriod("YEAR");
				return period;
			}		
			return period;
		}
	}
	/**
	 * @return the resourceCacheDAO
	 */
	public ResourceCacheDAO getResourceCacheDAO() {
		if(this.resourceCacheDAO == null){
			this.resourceCacheDAO = new ResourceCacheWSDAOImpl();
		}
		return resourceCacheDAO;
	}

	/**
	 * @param resourceCacheDAO the resourceCacheDAO to set
	 */
	public void setResourceCacheDAO(ResourceCacheDAO resourceCacheDAO) {
		this.resourceCacheDAO = resourceCacheDAO;
	}
}