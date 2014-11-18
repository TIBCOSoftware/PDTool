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
import com.compositesw.services.system.admin.resource.ColumnList;
import com.compositesw.services.system.admin.resource.Model;
import com.compositesw.services.system.admin.resource.ParameterList;
import com.compositesw.services.system.admin.resource.PathTypePair;
import com.compositesw.services.system.admin.resource.RebindRuleList;
import com.compositesw.services.system.util.common.AttributeList;
import com.compositesw.services.system.util.common.DetailLevel;

public interface RebindDAO {

	public static enum resourceType {TABLE,PROCEDURE,LINK,DEFINITION_SET,DATA_SOURCE,TRIGGER};
	public static enum action {SQL_TABLE,SQL_SCRIPT_PROCEDURE,EXTERNAL_SQL_PROCEDURE,BASIC_TRANSFORM_PROCEDURE,XSLT_TRANSFORM_PROCEDURE,STREAM_TRANSFORM_PROCEDURE};

	/**
	 * Changes binding of resources
	 * @param serverId target server config name
	 * @param source The path/type pair of the resource to be rebound.
	 * @param rebinds a RebindRuleList of old/new resources to be used
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if rebind of the resource fails
	 */
	public void rebindResource(String serverId, PathTypePair source, RebindRuleList rebinds, String pathToServersXML) throws CompositeException;

	/**
	 * 	The following resource types and sub-types are supported:
	 *	resourceType = 'PROCEDURE'
	 *		subtype = 'SQL_SCRIPT_PROCEDURE' -- Get Regular Procedure
	 *		subtype = 'EXTERNAL_SQL_PROCEDURE' -- Get Packaged Query Procedure
	 *		subtype = 'BASIC_TRANSFORM_PROCEDURE' -- Get XSLT Basic Transformation definition
	 *		subtype = 'XSLT_TRANSFORM_PROCEDURE' -- Get XSLT Transformation text
	 *		subtype = 'STREAM_TRANSFORM_PROCEDURE' -- Get XSLT Stream Transformation text
	 *
	 *	resourceType = 'TABLE'
	 *		subtype = 'SQL_TABLE' -- Get Regular View
	 *
	 * @param serverId
	 * @param pathToServersXML
	 * @param actionName
	 * @param resourcePath
	 * @param detailLevel
	 * @param procedureText
	 * @param usedResourcePath
	 * @param usedResourceType
	 * @param isExplicitDesign
	 * @param model
	 * @param columns
	 * @param parameters
	 * @param annotations
	 * @param attributes
	 * @throws CompositeException
	 */
	void takeRebindFolderAction(String serverId, String pathToServersXML,
			String actionName, String resourcePath, DetailLevel detailLevel,
			String procedureText, String usedResourcePath,
			String usedResourceType, Boolean isExplicitDesign, Model model,
			ColumnList columns, ParameterList parameters, String annotations,
			AttributeList attributes) throws CompositeException;

}
