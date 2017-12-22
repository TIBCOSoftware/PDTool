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
