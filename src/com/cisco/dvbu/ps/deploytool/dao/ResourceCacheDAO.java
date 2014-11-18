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
