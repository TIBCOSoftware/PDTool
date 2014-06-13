/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao;

import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.compositesw.services.system.admin.resource.CacheConfig;

public interface ResourceCacheDAO {

	public static enum action {UPDATE,REFRESH,CLEAR,ENABLE_DISABLE};

	/**
	 * Take action for Resource Cache based on the resourceCachPath and attribute list that is passed in for a give target server name 
	 * @param resourceCachePath resource path
	 * @param resourceCacheType resource type (PROCEDURE or TABLE)
	 * @param attributeList resource attribute list
	 * @param serverId target server config name
	 * @param pathToServersXML path to the server values xml
	 * @param validateResourceExists true/false to validate whether a resource exists before acting on the resource cache
	 */
	public void takeResourceCacheAction(String actionName, String resourceCachePath,  String resourceCacheType, CacheConfig resourceCacheAttributes, String serverId, String pathToServersXML, Boolean validateResourceExists) throws CompositeException;

	/**
	 * Get the ResourceCacheConfig response for the path and type that is passed in
	 * @param resourceCachePath resource path
	 * @param resourceCacheType resource type (PROCEDURE or TABLE)
	 * @param serverId target server config name
	 * @param pathToServersXML path to the server values xml
	 * @param validateResourceExists true/false to validate whether a resource exists before getting the resource cache info
	 */
	public CacheConfig getResourceCacheConfig(String resourceCachePath,  String resourceCacheType, String serverId, String pathToServersXML, Boolean validateResourceExists) throws CompositeException;

	/**
	 * Determine if the cache is enable for the path and type that is passed in
	 * @param resourceCachePath resource path
	 * @param resourceCacheType resource type (PROCEDURE or TABLE)
	 * @param serverId target server config name
	 * @param pathToServersXML path to the server values xml
	 */
	boolean isCacheEnabled(String resourceCachePath, String resourceCacheType, String serverId, String pathToServersXML) throws CompositeException;

}
