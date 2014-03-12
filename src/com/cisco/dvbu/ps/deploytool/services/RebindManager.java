/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.services;

import com.cisco.dvbu.ps.common.exception.CompositeException;

public interface RebindManager {
	
	
	/**
	 * Write rebinds to an XML file from the server associated with passed in target server id
	 * @param serverId target server id from servers config xml
	 * @oaram startPath starting path of the resource e.g /shared
	 * @param pathToRebindXml path including name to the data source xml which needs to be created
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException
	 */
	public void generateRebindXML(String serverId, String startPath, String pathToRebindXML, String pathToServersXML) throws CompositeException;

	/**
	 * Changes binding of resources on a server.
	 * Both the old and new resources must exist.
	 * 
	 * @param serverId target server config name
	 * @param rebindIds list of rebind Ids(comma separated)
	 * @param pathToRebindXml The path to the rebinds XML
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if deletion of the resource fails
	 */
	public void rebindResources(String serverId, String rebindIds, String pathToRebindXml, String pathToServersXML) throws CompositeException;
	
	/**
	 * Changes binding of resources on a server for an entire folder tree.
	 * Both the old and new resources must exist.
	 * 
	 * @param serverId target server config name
	 * @param rebindIds list of rebind Ids(comma separated)
	 * @param pathToRebindXml The path to the rebinds XML
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if deletion of the resource fails
	 */
	//void rebindResourcesFolder(String serverId, String rebindIds, String pathToRebindXml, String pathToServersXML) throws CompositeException;
	
	/**
	 * Rebind all of the resources in a given starting folder and its sub-folders to a designated "toFolder"
	 *   where the path of the resource discovered in the starting folder path matches the "fromFolder".
	 *   
	 *   Objective 1 - When both the "fromFolder" exists and the "toFolder" exists use the rebindResource API.
	 *                 The rebindResource API requires that both the from path and to path exist.
	 *   Objective 2 - When the "fromFolder" does not exist and the "toFolder" exists perform a textual path
	 *                 replacement on the script text or transformation source path and then rebind by invoking 
	 *                 the specific CIS API for the resource sub-type as seen in the section "Textual Path
	 *                 Replacement Rules" below.
	 *   Objective 3 - When the "toFolder" does not exist in all cases, throw an exception because it is not
	 *                 permitted to rebind to a target folder that does not exist.
	 *      
	 * Textual Path Replacement Rules:
	 * ------------------------------
	 *  Caveat:
	 *      For textual path replacement, when the "fromFolder" does not exist and the SQL_TABLE (Views) and 
	 *      SQL_SCRIPT_PROCEDURE (Procedures) have models, the model is lost.  The reason is there is no way
	 *      to programatically create a model in the API.   For parameterized queries this is unfortunate as
	 *      there is no way to rebuild the model once removed.  For Views, the model can be regenerated in
	 *      most cases.
	 * 
	 *	resourceType = 'TABLE'
	 *		subtype = 'SQL_TABLE' -- Get Regular View
	 *			procedureTextCurr = tableResource.getSqlText();
	 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
	 *			procedureText = procedureTextCurr.replaceAll(fromFolder, toFolder);
	 *			port.updateSqlTable(resourcePath, detailLevel, procedureText, model, isExplicitDesign, columns, annotation, attributes);
	 *
	 *	resourceType = 'PROCEDURE'
	 *		subtype = 'SQL_SCRIPT_PROCEDURE' -- Update Regular Procedure
	 *			procedureTextCurr = procedureResource.getScriptText();
	 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
	 *			procedureText = procedureTextCurr.replaceAll(fromFolder, toFolder);
	 *			port.updateSqlScriptProcedure(resourcePath, detailLevel, procedureText, model, isExplicitDesign, parameters, annotation, attributes);
	 *
	 *		subtype = 'EXTERNAL_SQL_PROCEDURE' -- Update Packaged Query Procedure
	 *			usedResourcePathCurr = procedureResource.getExternalDataSourcePath();
	 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
	 *			usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
	 *			port.updateExternalSqlProcedure(resourcePath, detailLevel, procedureText, usedResourcePath, parameters, annotation, attributes);
	 *
	 *		subtype = 'BASIC_TRANSFORM_PROCEDURE' -- Update XSLT Basic Transformation definition
	 *			usedResourcePathCurr = procedureResource.getTransformSourcePath();
	 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
	 *			usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
	 *			port.updateBasicTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, annotation, attributes);
	 *
	 *		subtype = 'XSLT_TRANSFORM_PROCEDURE' -- Update XSLT Transformation text
	 *			usedResourcePathCurr = procedureResource.getTransformSourcePath();
	 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
	 *			usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
	 *			port.updateXsltTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, procedureText, model, annotation, isExplicitDesign, parameters, attributes);
	 *
	 *		subtype = 'STREAM_TRANSFORM_PROCEDURE' -- Update XSLT Stream Transformation text
	 *			usedResourcePathCurr = procedureResource.getTransformSourcePath();
	 *			// Replace all of the matching old "fromFolder" paths with the new "toFolder" path
	 *			usedResourcePath = usedResourcePathCurr.replaceAll(fromFolder, toFolder);
	 *			port.updateStreamTransformProcedure(resourcePath, detailLevel, usedResourcePath, resourceType, model, isExplicitDesign, parameters, annotation, attributes);
	 *      
	 * @param serverId target server config name
	 * @param rebindIds list of rebind Ids(comma separated)
	 * @param pathToRebindXml The path to the rebinds XML
	 * @param pathToServersXML path to the server values xml
	 * @throws CompositeException if deletion of the resource fails
	 */
	public void rebindFolder(String serverId, String rebindIds, String pathToRebindXml, String pathToServersXML) throws CompositeException;

}
