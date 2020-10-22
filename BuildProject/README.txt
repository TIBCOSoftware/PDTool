------------------------------------------
To Build Various Eclipse Projects:
------------------------------------------


------------------------------------------
Build PDToolModules
------------------------------------------
1. Modify the schema as needed
2. Follow instructions in /PDToolModules/readme.txt


------------------------------------------
Build PDTool 7.0.0
------------------------------------------
1. Change the Java Build Path Library to lib7.0.0
	a. Select the current lib8.0.0 or lib8.3.0 and click "Remove Libary"
	b. "Add library" "User Libraries" Select lib7.0.0 "Finish" "Apply and Close"
2. Eclipse Menu "Project" "Clean" "PDTool" Click "Clean"
3. To build /dist/PDTool7.0.0.jar only, right-click on build7.0.0.xml select "Run As" "Ant Build"
4. To build the PDToolRelease, 
    a. refer to instructions in PDToolRelease/_general_release/README.txt
    b. Right-click on build_release7.0.0.xml
    
------------------------------------------
Build PDTool 8.0.0
------------------------------------------
1. Change the Java Build Path Library to lib8.0.0
	a. Select the current lib7.0.0 or lib8.3.0 and click "Remove Libary"
	b. "Add library" "User Libraries" Select lib8.0.0 "Finish" "Apply and Close"
2. Eclipse Menu "Project" "Clean" "PDTool" Click "Clean"
3. To build /dist/PDTool8.0.0.jar only, right-click on build8.0.0.xml select "Run As" "Ant Build"
4. To build the PDToolRelease, 
    a. refer to instructions in PDToolRelease/_general_release/README.txt
    b. Right-click on build_release8.0.0.xml

------------------------------------------
Build PDTool 8.3.0
------------------------------------------
1. Change the Java Build Path Library to lib8.3.0
	a. Select the current lib7.0.0 or lib8.0.0 and click "Remove Libary"
	b. "Add library" "User Libraries" Select lib8.3.0 "Finish" "Apply and Close"
2. Eclipse Menu "Project" "Clean" "PDTool" Click "Clean"
3. To build /dist/PDTool8.3.0.jar only, right-click on build8.3.0.xml select "Run As" "Ant Build"
4. To build the PDToolRelease, 
    a. refer to instructions in PDToolRelease/_general_release/README.txt
    b. Right-click on build_release8.3.0.xml

    
------------------------------------------
JVM Version mismatch info:
------------------------------------------
Example of what you might see at runtime when running with a JRE/JDK that is mismatched with how the code was compiled.

This is just a version mismatch. You have compiled your code using java version 9 and your current JRE is version 8. Try upgrading your JRE to 9.

49 = Java 5
50 = Java 6
51 = Java 7
52 = Java 8
53 = Java 9
54 = Java 10
55 = Java 11
56 = Java 12
57 = Java 13
58 = Java 14


------------------------------------------
TDV procedure resource TYPES and SUBTYPES:
------------------------------------------
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
