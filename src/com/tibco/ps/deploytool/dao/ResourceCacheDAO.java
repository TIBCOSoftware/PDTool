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
package com.tibco.ps.deploytool.dao;

import com.tibco.ps.common.exception.CompositeException;
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
