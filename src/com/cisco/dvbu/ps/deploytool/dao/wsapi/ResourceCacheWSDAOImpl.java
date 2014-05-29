/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao.wsapi;

/** Initial Version:	Mike Tinius :: 6/8/2011		
 * Modifications:	initials :: Date
 * 
 */

import javax.xml.ws.Holder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CompositeLogger;
import com.cisco.dvbu.ps.common.util.wsapi.CisApiFactory;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.DeployManagerUtil;
import com.cisco.dvbu.ps.deploytool.dao.ResourceCacheDAO;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.compositesw.services.system.admin.GetResourceCacheConfigSoapFault;
import com.compositesw.services.system.admin.UpdateResourceCacheConfigSoapFault;
import com.compositesw.services.system.admin.ClearResourceCacheSoapFault;
import com.compositesw.services.system.admin.RefreshResourceCacheSoapFault;
import com.compositesw.services.system.admin.ResourcePortType;
import com.compositesw.services.system.admin.resource.CacheConfig;
import com.compositesw.services.system.admin.resource.ResourceType;
import com.compositesw.services.system.util.common.DetailLevel;

public class ResourceCacheWSDAOImpl implements ResourceCacheDAO {

	private static Log logger = LogFactory.getLog(ResourceCacheWSDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceCacheDAO#takeResourceCacheAction(java.lang.String, java.lang.String, java.lang.String, import com.compositesw.services.system.admin.resource.CacheConfig, java.lang.String, java.lang.String, Boolean)
	 */
	public void takeResourceCacheAction(String actionName, String resourceCachePath, String resourceCacheType, CacheConfig resourceCacheConfig, String serverId, String pathToServersXML, Boolean validateResourceExists) throws CompositeException {
		
		// read target server properties from xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "ResourceCacheWSDAOImpl.takeResourceCacheAction("+actionName+")", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		try {
			
			if (validateResourceExists) {
				// Make sure the resource exists before executing any actions
				if (!DeployManagerUtil.getDeployManager().doResourceExist(serverId, resourceCachePath, pathToServersXML)) {
					throw new ApplicationException("The resource "+resourceCachePath+" does not exist.");
				}
			}
			
			if(actionName.equalsIgnoreCase(ResourceCacheDAO.action.UPDATE.name())){

				Holder<CacheConfig> cacheConfig = new Holder<CacheConfig>(resourceCacheConfig);			
				
				port.updateResourceCacheConfig(resourceCachePath, ResourceType.valueOf(resourceCacheType), DetailLevel.FULL, cacheConfig);
				/*
				Update the cache configuration for a resource.
				
				Request Elements:
				    path: The path of a cacheable resource.
				    type: The type of a cached resource may be "TABLE" or "PROCEDURE".	
				    detail: The level of detail of resources in the response.  May be "NONE", "SIMPLE",
				       or "FULL".
				    cacheConfig: The cache configuration of the given resource.
				        configured (optional): "true" if caching should be configured for the given
				           resource; otherwise "false".  If not provided, the configured setting will
				           be left unaltered.  If configured is "false" all other elements will be ignored.
				        enabled (optional): "true" if the cache is enabled; otherwise "false".
				           If not provided, the enable setting will be left unaltered.
				        storage (optional): How the cached is stored.  If not provided, the storage
				           settings will be left unaltered.
				            mode: Storage type used for the cache. May be either "AUTOMATIC" or "DATA_SOURCE"
				            storageDataSourcePath (optional): If the mode is "DATA_SOURCE", this
				               identifies the path to the data source being used to store cache data.
				            storageTargets (optional): If the mode is "DATA_SOURCE", this identifies
				               the tables used for storing cache data
				                entry:
				                    targetName: For a TABLE resource this is always "result".
				                       For a PROCEDURE resource, this is the name of a cursor parameter,
				                       or an empty string for the scalar output parameters.
				                    path: The path to the table used for storing this data.
				                    type: Always "TABLE"
				        refresh (optional): How the cache should be refreshed.  If not provided, the
				           refresh settings will be left unaltered.
				            mode: How the cache should be refreshed.  May be "MANUAL" or "SCHEDULED".
				            schedule (optional): If the mode is "SCHEDULED", this element will exist
				                with the following child elements:
				                mode: Always "INTERVAL".
				                startTime: When the first refresh should occur.
				                interval: The number of seconds between refreshes.
				        expirationPeriod: The amount of time in milliseconds that the cache will be
				           cleared after it is refreshed.  If less than zero, the period will be set to
				           zero.  If zero then the cache will never expire.  If not provided, the enable
				           setting will be left unaltered.
				        clearRule: One of "NONE", "ON_LOAD", or "ON_FAILURE".  Normally old cache data
				           is cleared on expiration and when a cache refresh successfully completes.
				           In the latter case the old cache data is replaced by the new cached data.
				           If "NONE", then the normal behavior will be used.  If "ON_LOAD", in addition
				           to the normal behavior the old cache data will be cleared when a refresh is
				           started.  If "ON_FAILURE", in addition to the normal behavior the old cache
				           data will be cleared when a refresh fails.  If not provided, the enable
				           setting will be left unaltered.
				
				Response Elements:
				    cacheConfig (optional): The cache configuration of the given resource.  This element
				       is only present in the response if the detail level is not "NONE".
				
				Faults:
				    IllegalArgument: If the path is malformed or an illegal type is provided.
				    IllegalState: If the resource type does not support caching.
				    NotFound: If the resource or any portion of the path to the resource does not exist.
				    Security: The user must have READ access on all items in the path.
				    Security: If the user does not have WRITE access on the last item in the path.
				    Security: If the user does not have the ACCESS_TOOLS right.	
				 */
			}else if(actionName.equalsIgnoreCase(ResourceCacheDAO.action.REFRESH.name())){
		
				if (isCacheEnabled(resourceCachePath, resourceCacheType, serverId, pathToServersXML)) {
					port.refreshResourceCache(resourceCachePath, ResourceType.valueOf(resourceCacheType));
				} 
				/*
				Refreshes the cache on a resource.

				Also see "updateResourceCacheConfig" for enabling and disabling caching.

				Request Elements:
				    path: The path of the resource with caching enabled.
				    type: The type of the resource may be either "TABLE" or "PROCEDURE".

				Response Elements:
				    N/A

				Faults:
				    IllegalArgument: If the path is malformed or an illegal type is provided.
				    IllegalState: If the cache is disabled or the resource type does not support caching.
				    NotFound: If the resource or any portion of the path to the resource does not exist.
				    Security: If the user does not have READ access on all items in the path other than
				       the last one.
				    Security: If the user does not have WRITE access to the last item in the path.
				    Security: If the user does not have the ACCESS_TOOLS right.
				    ServerError: If any problems with connecting to or retrieving data from the data
				       source when refreshing.
				*/
			} else if(actionName.equalsIgnoreCase(ResourceCacheDAO.action.CLEAR.name())){

				if (isCacheEnabled(resourceCachePath, resourceCacheType, serverId, pathToServersXML)) {
					port.clearResourceCache(resourceCachePath, ResourceType.valueOf(resourceCacheType));
				} 
				/*
				Clears an existing resource cache. Only purposefully configured resources of type TABLE and
				PROCEDURE support caching in this release.  Procedure variants are cleared from the cache along 
				with any cached results.
				
				Use the refreshResourceCache procedure to initiate an immediate refresh of a table or cached view,
				or allow the cache to get refreshed on next use of the resource.
				
				Request Elements:
				    path: The path of the resource configured to use caching.
				    type: The type of the resource. Valid values are "TABLE" and "PROCEDURE".
				
				Response Elements:
				    N/A
				
				Faults:
				    IllegalArgument: If the path is malformed or an illegal type is provided.
				    IllegalState: If the cache is disabled.
				    NotAllowed: The resource type must support caching.  Only TABLE and PROCEDURE
				       resources support caching in this release.
				    NotFound: If the resource or any portion of the path to the resource does not exist.
				    Security: If the user does not have READ access on all items in the path other than
				       the last one.
				    Security: If the user does not have WRITE access to the last item in the path.
				    Security: If the user does not have the ACCESS_TOOLS right.
				 */
			}
			if(logger.isInfoEnabled())
			{
				logger.debug("ResourceCacheWSDAOImpl.takeResourceCacheAction::success for action="+actionName);
			}
		} catch (UpdateResourceCacheConfigSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), ResourceCacheDAO.action.UPDATE.name(), "ResourceCache", resourceCachePath, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);

		}catch (RefreshResourceCacheSoapFault e){
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), ResourceCacheDAO.action.REFRESH.name(), "ResourceCache", resourceCachePath, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		
		} catch (ClearResourceCacheSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), ResourceCacheDAO.action.CLEAR.name(), "ResourceCache", resourceCachePath, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceCacheDAO#getResourceCacheConfig(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, Boolean)
	 */
//	@Override
	public CacheConfig getResourceCacheConfig(String resourceCachePath,	String resourceCacheType, String serverId, String pathToServersXML, Boolean validateResourceExists) throws CompositeException {
/*
Only TABLE and PROCEDURE resources support caching in this release.

Request Elements:
    path: The path of a cacheable resource.
    type: The type of the resource. A valid input is either "TABLE" or "PROCEDURE".

Response Elements:
    cacheConfig: The cache configuration of the given resource.
        configured: "true" if caching should be configured for the given resource;
           otherwise "false".  If configured is "false" all other elements will be
           ignored.
        enabled: "true" if the cache is enabled; otherwise "false".
        storage: How the cached is stored.
            mode: The type of storage used for the cache.  May be "AUTOMATIC" or
                  "DATA_SOURCE"
            storageDataSourcePath: If the mode is "DATA_SOURCE", this identifies the
               path to the data source being used to store cache data.
            storageTargets: If the mode is "DATA_SOURCE", this identifies the tables
               used for storing cache data
                entry:
                    targetName: For a TABLE resource this is always "result".
                       For a PROCEDURE resource, this is the name of a cursor parameter,
                       or an empty string for the scalar output parameters.
                    path: The path to the table used for storing this data.
                    type: Always "TABLE"
        refresh: How the cache should be refreshed.
            mode: How the cache should be refreshed.  May be "MANUAL" or "SCHEDULED".
            schedule (optional): If the mode is "SCHEDULED", this element will exist
               with the following child elements:
                mode: Always "INTERVAL".
                startTime: When the first refresh should occur.
                interval: The number of seconds between refreshes.
        expirationPeriod: The amount of time in milliseconds that the cache will be
           cleared after it is refreshed.  If zero then the cache will never expire.
        clearRule: One of "NONE", "ON_LOAD", or "ON_FAILURE".  Normally old cache
           data is cleared on expiration and when a cache refresh successfully completes.
           In the latter case the old cache data is replaced by the new cached data.
           If "NONE", then the normal behavior will be used.  If "ON_LOAD", in addition
           to the normal behavior the old cache data will be cleared when a refresh is
           started.  If "ON_FAILURE", in addition to the normal behavior the old cache
           data will be cleared when a refresh fails.

Faults:
    IllegalArgument: If the path is malformed or an illegal type is provided.
    IllegalState: If the resource type does not support caching.
    NotFound: If the resource or any portion of the path to the resource does not exist.
    Security: If the user does not have READ access on all items in the path.
    Security: If the user does not have the ACCESS_TOOLS right.
*/

		// read target server properties from xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);

		CacheConfig cacheConfig = null;
		try {
			if (validateResourceExists) {
				// Make sure the resource exists before executing any actions
				if (!DeployManagerUtil.getDeployManager().doResourceExist(serverId, resourceCachePath, pathToServersXML)) {
					throw new ApplicationException("The resource "+resourceCachePath+" does not exist.");
				}
			}
			
			// Get the cache configuration for the resource path
			cacheConfig = port.getResourceCacheConfig(resourceCachePath, ResourceType.valueOf(resourceCacheType));			
			
			if(logger.isDebugEnabled())
			{
				logger.debug("ResourceCacheWSDAOImpl.getResourceCacheConfig::resourceCachePath="+resourceCachePath);
			}
		} catch (GetResourceCacheConfigSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getResourceCacheConfig", "ResourceCache", resourceCachePath, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}
		return cacheConfig;
	}
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ResourceCacheDAO#getResourceCacheConfig(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public boolean isCacheEnabled(String resourceCachePath,	String resourceCacheType, String serverId, String pathToServersXML) throws CompositeException {

		boolean enabled = false;
		try {
			// Make sure the resource exists before executing any actions
			if (DeployManagerUtil.getDeployManager().doResourceExist(serverId, resourceCachePath, pathToServersXML)) {
				
				CacheConfig cacheConfig = getResourceCacheConfig(resourceCachePath, resourceCacheType, serverId, pathToServersXML, true);
				if (cacheConfig != null) {
					if (cacheConfig.isEnabled() != null) {
						if (cacheConfig.isEnabled()) {
							enabled = true;
						}
					}
				}
				if(logger.isInfoEnabled())
				{
					logger.debug("ResourceCacheWSDAOImpl.isCacheEnabled::enabled="+enabled+"  resourcePath="+resourceCachePath);
				}
			} else {
				throw new ApplicationException("The resource "+resourceCachePath+" does not exist.");
			}

		} catch (ApplicationException e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "isCacheEnabled", "ResourceCache", resourceCachePath, null));
			throw new ApplicationException(e.getMessage(), e);
		}
		return enabled;
	}
	
}
