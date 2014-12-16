REM ######################################################################
REM # (c) 2014 Cisco and/or its affiliates. All rights reserved.
REM # 
REM # This software is released under the Eclipse Public License. The details can be found in the file LICENSE. 
REM # Any dependent libraries supplied by third parties are provided under their own open source licenses as 
REM # described in their own LICENSE files, generally named .LICENSE.txt. The libraries supplied by Cisco as 
REM # part of the Composite Information Server/Cisco Data Virtualization Server, particularly csadmin-XXXX.jar, 
REM # csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar, csext-XXXX.jar, csjdbc-XXXX.jar, 
REM # csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar, and customproc-XXXX.jar (where -XXXX is an 
REM # optional version number) are provided as a convenience, but are covered under the licensing for the 
REM # Composite Information Server/Cisco Data Virtualization Server. They cannot be used in any way except 
REM # through a valid license for that product.
REM # 
REM # This software is released AS-IS!. Support for this software is not covered by standard maintenance agreements with Cisco. 
REM # Any support for this software by Cisco would be covered by paid consulting agreements, and would be billable work.
REM # 
REM ######################################################################

The example files were created by invoking the following CIS Procedure:
   /shared/Utilities/repository/lowerLevelProcedures/getBasicResourceXML
   
The response was pasted into an XML Editor such as Stylus Studio to format the XML

The examples are useful for a programmer to see what elements and attributes exist for a given resource.

getBasicResourceXML:
	-- CIS Repository Helper Procedure --
	This procedure retrieves the XML resource metadata for a given resource.  An XML response is returned.
 	This is a lower level procedure that gets called by other higher level procedures including:
		getBasicResourceCursor(), getBasicResourceCursor_ActionAttributes(), getBasicResourceCursor_SQL_TABLE().
	Generally speaking you should be invoking one of those higher level procedures instead of this one unless you
	absolutely need the XML returned.

	Input:
		fullResourcePath - Full resource path which includes the path and the resource name  
			Values: e.g. /shared/Utilities/repository/examples/source
		resourceType - Type of CIS resource to be created
			Values: see "TYPES / SUBTYPES" listing below.  e.g. CONTAINER
	Output:
		resourceResponse - XML response containing the resource metadata
			Values: XML
	Exceptions:  none
	Author:      Mike Tinius
	Date:        8/2/2010
	CSW Version: 5.1.0


	TYPES / SUBTYPES:
	=================
	The following resource types/subtypes are supported by this operation.  
	Resources cannot be created under "/services" unless otherwise noted, and cannot be created	within a physical data source. 

	(Basic CIS folder)
	* CONTAINER / FOLDER_CONTAINER - A Composite folder.   Cannot be created anywhere under /services except in another FOLDER under /services/webservices.
	* CONTAINER / DIRECTORY_CONTAINER - A Composite directory.

	(Database)
	* CONTAINER / CATALOG_CONTAINER - A Composite catalog folder under a data source.  Can only be created within a data source under /services/databases.
	* CONTAINER / SCHEMA_CONTAINER - A Composite schema container.  Can only be created within a CATALOG that is under /services/databases.

	(Web Services)
	* CONTAINER / SERVICE_CONTAINER - A web service container for the service.  Can only be created within a Composite Web Services data source that is under /services/webservices.
	* CONTAINER / OPERATIONS_CONTAINER - A web service container for the operations
	* CONTAINER / PORT_CONTAINER - A Composite web service container for port.   Can only be created within a SERVICE under /services/webservices.

	(Connectors)
	* CONTAINER / CONNECTOR_CONTAINER - A Composite container for connectors.
	* CONNECTOR / JMS - A Composite JMS Connector.  Created with no connection information
	* CONNECTOR / HTTP - A Composite HTTP Connector.  Created with no connection information

	* DATA_SOURCE / RELATIONAL_DATA_SOURCE - A relational database source.
	* DATA_SOURCE / FILE_DATA_SOURCE - A comma separate file data source.
	* DATA_SOURCE / XML_FILE_DATA_SOURCE - An XML file data source.
	* DATA_SOURCE / WSDL_DATA_SOURCE - A Composite web service data source.
	* DATA_SOURCE / XML_HTTP_DATA_SOURCE - An HTTP XML data source.
	* DATA_SOURCE / NONE - A custom java procedure data source.

	* DEFINITION_SET / SQL_DEFINITION_SET - A Composite SQL Definition set.
	* DEFINITION_SET / XML_SCHEMA_DEFINITION_SET - A Composite XML Schema Defintion set.
	* DEFINITION_SET / WSDL_DEFINITION_SET - A Composite WSDL Definition set.
	* DEFINITION_SET / ABSTRACT_WSDL_DEFINITION_SET - A Composite Abstract WSDL Definition set such as the ones imported from Designer.
	* DEFINITION_SET / SCDL_DEFINITION_SET - A Composite SCA composite Definition set imported from Designer.

	* LINK / sub-type unknown - Used to link a Composite Data Service to a Composite resource such as a view or sql procedure.

	(CIS procedures)
	* PROCEDURE / SQL_SCRIPT_PROCEDURE - A Composite SQL Procedure.  Created with a simple default script body that is runnable.

	(Custom procedures)
	* PROCEDURE / JAVA_PROCEDURE - A Composite java data source procedure.  Created from a java data source (jar file).

	(Database procedures)
	* PROCEDURE / EXTERNAL_SQL_PROCEDURE - A Composite Packaged Query.  Created with no SQL text, so it is not runnable.  
	* PROCEDURE / DATABASE_PROCEDURE - A database stored procedure.

	(XML procedures)
	* PROCEDURE / BASIC_TRANSFORM_PROCEDURE - A Composite Basic XSLT Transformation procedure.  Created with no target procedure and no output columns, so it is not runnable.
	* PROCEDURE / XSLT_TRANSFORM_PROCEDURE - A Composite XSLT Transformation procedure.  Created with no target procedure and no output columns, so it is not runnable.
	* PROCEDURE / STREAM_TRANSFORM_PROCEDURE - A Composite XSLT Streaming Transformation procedure.  Created with no target procedure and no output columns, so it is not runnable.
	* PROCEDURE / XQUERY_TRANSFORM_PROCEDURE - A Composite XQUERY Transformation Procedure.  Created with no target schema and no model, so it is not runnable.

	(Misc procedures)
	* PROCEDURE / OPERATION_PROCEDURE - A Composite web service or HTTP procedure operation.

	* TABLE / SQL_TABLE - A Composite View.  Created with no SQL text or model, so it is not runnable.  
	* TABLE / DATABASE_TABLE - A Composite database table.
	* TABLE / DELIMITED_FILE_TABLE - A Composite delimited file table
	* TABLE / SYSTEM_TABLE - A Composite system table view.

	* TREE / XML_FILE_TREE - The XML tree structure associated with a file-XML data source.

	* TRIGGER / NONE - A Composite trigger.   Created disabled.
