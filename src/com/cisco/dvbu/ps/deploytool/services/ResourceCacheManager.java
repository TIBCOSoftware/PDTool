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
package com.cisco.dvbu.ps.deploytool.services;

import com.cisco.dvbu.ps.common.exception.CompositeException;

/* Debug within Eclipse:
 * 
generateResourceCacheXML localhost /shared/test1  resources/modules/getResourceCacheModule.xml resources/modules/servers.xml "CONFIGURED,TABLE,PROCEDURE"

refreshResourceCache localhost  shared1  resources/modules/ResourceCacheModule.xml resources/modules/servers.xml

clearResourceCache localhost  shared1  resources/modules/ResourceCacheModule.xml resources/modules/servers.xml

updateResourceCache localhost  shared1  resources/modules/ResourceCacheModule.xml resources/modules/servers.xml
 */
public interface ResourceCacheManager {
	
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

}
