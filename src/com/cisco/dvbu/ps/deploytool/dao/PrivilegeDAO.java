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

import com.cisco.dvbu.ps.common.exception.CompositeException;
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
	 * @param recurse informs how to process the resource children
	 * @param updateDependenciesRecursively If "true", then all dependencies of the given resources will recursively be updated with the privileges assigned to their parent.
	 * @param updateDependentsRecursively If "true", then all dependents of the given resources will recursively be updated with the privileges assigned to their parent.
	 * @param mode determines whether privileges are merged with existing ones 
	 *        "OVERWRITE_APPEND" (default) merges and does not update privileges for users or groups not mentioned.  
		      "SET_EXACTLY" makes privileges look exactly like those provided in the call.
	 * @param PrivilegeEntries privilege entry list
	 * @param serverId target server config name
	 * @param pathToServersXML path to the server values xml
	 * @param CisVersion a numeric representation of the CIS Version.  6.2.0.00.29 = 6200029
	 * @return void
	 * @throws CompositeException
	 */
	public void takePrivilegeAction(String actionName, boolean recurse, boolean updateDependenciesRecursively, boolean updateDependentsRecursively, String mode, PrivilegeEntries privilegeEntries, String serverId, String pathToServersXML, Integer CisVersion) throws CompositeException;
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
	public com.compositesw.services.system.admin.resource.GetResourcePrivilegesResponse.PrivilegeEntries
		getResourcePrivileges(Entries privilegeEntries, String filter, boolean includeColumnPrivileges, String serverId, String pathToServersXML);

}
