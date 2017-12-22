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
import com.tibco.ps.deploytool.modules.PrivilegeModule;
import com.compositesw.services.system.admin.resource.GetResourcePrivilegesRequest.Entries;
import com.compositesw.services.system.admin.resource.UpdateResourcePrivilegesRequest.PrivilegeEntries;

public interface PrivilegeDAO {

   	/*
	 * mtinius: 2012-08-07, added static parameter (CaResourceUtil.RECURSE_DEPENDENCIES) for new api
	 * 						call to updateResourcePrivileges
	 */
	public static boolean RECURSE_DEPENDENCIES = false;
	
	public static enum action {UPDATE};

	/**
	 * Take Privilege Action method takes  passed action on the resource privilege with the passed in privilege attributes for the passed in target server name 
	 * @param actionname the type of action to take
	 * @param PrivilegeModule privilegeModule entry list
	 * @param serverId target server config name
	 * @param pathToServersXML path to the server values xml
	 * @return void
	 * @throws CompositeException
	 */
	public void takePrivilegeAction(String actionName, PrivilegeModule privilegeModule, String serverId, String pathToServersXML) throws CompositeException;

	/**
	 * Get all the resource privileges for the set of entries pass in
	 * @param privilegeEntries a list of resources to get privileges for (resourcePath, resourceType)
	 * @param filter A filter string.  The only legal values in this release are an empty string and "ALL_EXPLICIT"
	 * @param includeColumnPrivileges (true or false) on whether to include column privileges or not
	 * @param serverId target server config name
	 * @param pathToServersXML path to the server values xml
	 * @return PrivilegeEntries list with all the privileges for the passed in Entry list
	 * @throws CompositeException
	 */	
	public PrivilegeModule getResourcePrivileges(Entries privilegeEntries, String filter, boolean includeColumnPrivileges, String serverId, String pathToServersXML);

}
