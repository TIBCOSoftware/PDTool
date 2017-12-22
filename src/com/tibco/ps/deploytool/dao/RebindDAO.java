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
