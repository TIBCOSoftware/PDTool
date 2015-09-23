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
package com.cisco.dvbu.ps.deploytool.dao.jdbcapi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.XMLXPath;
import com.cisco.dvbu.ps.common.util.jdbcapi.JdbcConnector;
import com.cisco.dvbu.ps.common.util.wsapi.CisApiFactory;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.dao.RegressionInputFileDAO;
import com.cisco.dvbu.ps.deploytool.dao.ResourceDAO;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.ResourceWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.services.RegressionItem;
import com.cisco.dvbu.ps.deploytool.services.RegressionManagerImpl;
import com.cisco.dvbu.ps.deploytool.services.RegressionManagerUtils;
import com.cisco.dvbu.ps.deploytool.services.RegressionQuery;
import com.cisco.dvbu.ps.deploytool.modules.RegressionQueriesType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityQueriesType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityQueryType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionTestType;
import com.compositesw.services.system.util.common.Attribute;
import com.compositesw.services.system.util.common.DetailLevel;
import com.compositesw.services.system.admin.resource.DataSourceResource;
import com.compositesw.services.system.admin.resource.LinkResource;
import com.compositesw.services.system.admin.resource.Parameter;
import com.compositesw.services.system.admin.resource.ParameterList;
import com.compositesw.services.system.admin.resource.ResourceType;
import com.compositesw.services.system.admin.resource.ResourceList;
import com.compositesw.services.system.admin.ResourcePortType;
import com.compositesw.services.system.admin.resource.Resource;

/**
 * Generates regression test input file in the same format as the pubtest input file
 * 
 * @author sst
 * @modified 
 * 	2013-02-13 (mtinius): added support for variables for all fields in RegressionModule.xml
 *  2013-11-27 (mtinius): resolved resource URLs with spaces and periods.  Added better support for parsing complex FROM clauses.
 *  2014-02-03 (mtinius): added ability to filter on resources for generateInputFile().
 */
public class RegressionInputFileJdbcDAOImpl implements RegressionInputFileDAO
{
	private static Log logger = LogFactory.getLog(RegressionInputFileJdbcDAOImpl.class);
	private static String propertyFile = RegressionManagerImpl.propertyFile;
	private ResourceDAO resourceDAO = null;

    // Debug parameters
    private static String suppress = "";
    private static boolean debug1 = false;
    private static boolean debug2 = false;
    private static boolean debug3 = false;
 
	// Public Regression Module Constants
	public static final int TYPE_QUERY = 1;
    public static final int TYPE_WS = 2;
    public static final int TYPE_PROCEDURE = 3;

	/**
	 * Integer number corresponding to procedures parameter direction IN as defined in CIS.
	 * Used in CIS system table ALL_PARAMETERS 
	 */
	public static String PROC_PARAM_DIRECTION_IN = "1";
	
	/**
	 * Integer number corresponding to procedures parameter direction INOUT as defined in CIS.
	 * Used in CIS system table ALL_PARAMETERS 
	 */
	public static String PROC_PARAM_DIRECTION_INOUT = "2";	
	
	/**
	 * Sql to CIS system tables to obtain information about all published procedures.
	 * Used to create procedure requests for CALL syntax. Here we get all types of params (IN, INOUT, OUT, STRUCT).
	 */
	private static String compositeSqlProcsRequestForCallSyntax = 
		"SELECT " +
			"procs.DATASOURCE_NAME, " +	// position 1
			"procs.CATALOG_NAME, " +	// position 2
			"procs.SCHEMA_NAME, " +		// position 3
			"procs.PROCEDURE_ID, " +	// position 4
			"procs.PROCEDURE_NAME, " +	// position 5
			"params.PARAMETER_NAME, " +	// position 6
			"params.DATA_TYPE, " +		// position 7
			"procs.PARENT_PATH " +		// position 8
		"FROM " +
			"ALL_PROCEDURES procs, " +
			"ALL_PARAMETERS params " +
		"WHERE " +
			"params.PROCEDURE_ID = procs.PROCEDURE_ID " +
		"AND (params.DIRECTION = " + PROC_PARAM_DIRECTION_IN + 
		      " OR params.DIRECTION = " + PROC_PARAM_DIRECTION_INOUT + ") ";
		
	/**
	 * Sql to CIS system tables to obtain information about all published procedures.
	 * Used to create procedure requests for SELECT syntax. Here we only get IN and INOUT params.
	 */
	private static String compositeSqlProcsRequestForSelectSyntax = 
		"SELECT " +
			"procs.DATASOURCE_NAME, " +	// position 1
			"procs.CATALOG_NAME, " +	// position 2
			"procs.SCHEMA_NAME, " +		// position 3
			"procs.PROCEDURE_ID, " +	// position 4
			"procs.PROCEDURE_NAME, " +	// position 5
			"params.DATA_TYPE, " +		// position 6
			"procs.PARENT_PATH " +		// position 7
		"FROM " +
			"ALL_PROCEDURES procs, " +
			"ALL_PARAMETERS params " +
		"WHERE " +
			"params.PROCEDURE_ID = procs.PROCEDURE_ID " +
		"AND (params.DIRECTION = " + PROC_PARAM_DIRECTION_IN + 
		      " OR params.DIRECTION = " + PROC_PARAM_DIRECTION_INOUT + ") ";
	
	/**
	 * Sql to CIS system tables to obtain names of all WSDL operations. 
	 */
	private static String compositeSqlWsRequest = 
		"SELECT "+
			"ALL_WSDL_OPERATIONS.DATASOURCE_NAME, "+// position 1
			"ALL_WSDL_OPERATIONS.OPERATION_NAME, "+	// position 2
			"ALL_WSDL_OPERATIONS.PARENT_PATH, "+	// position 3
			"ALL_DATASOURCES.DATASOURCE_TYPE, "+	// position 4
			"ALL_WSDL_OPERATIONS.OWNER, "+			// position 5
			"ALL_WSDL_OPERATIONS.PARENT_PATH " +	// position 6
		"FROM "+
		"	ALL_WSDL_OPERATIONS ALL_WSDL_OPERATIONS INNER JOIN "+
		"	  ALL_DATASOURCES ALL_DATASOURCES "+
		"	ON ALL_WSDL_OPERATIONS.DATASOURCE_ID = ALL_DATASOURCES.DATASOURCE_ID ";
		
	/**
	 *  Statements ending for procs.
	 *  
	 *  This ORDER BY is necessary for the correct building of the
	 *  input file entries from the resultset, since the comparison criteria of next argument
	 *  for the same procedure vs. starting a new procedure file entry is based on comparison of the PROCEDURE_NAMEs
	 *  for the current and previous ResultSet rows.  
	 */
	private static String orderByForProcs = " ORDER BY procs.PROCEDURE_ID, params.ORDINAL_POSITION";
	
	/**
	 * Statement ending for Web Services
	 */
	private static String orderByForWs = " ORDER BY ALL_WSDL_OPERATIONS.DATASOURCE_NAME, ALL_WSDL_OPERATIONS.PARENT_PATH, ALL_WSDL_OPERATIONS.OPERATION_NAME ";
	
	/**
	 * Sql to CIS system tables to obtain information about all published views or tables.
	 */
	private static String stmtStrAllPublishedTablesExceptSystem = 
		//      position 1       position 2    position 3   position 4  position 5
		"SELECT DATASOURCE_NAME, CATALOG_NAME, SCHEMA_NAME, TABLE_NAME, PARENT_PATH " +
		"FROM ALL_TABLES " +
		"WHERE DATASOURCE_NAME  <> 'system' ";

	/**
	 * Filter on the above Sql for views/tables to get those for only one 
	 * specific published datasource (virtual database)
	 */
	private static String stmtStrPublishedTablesForListOfDSources =
		//      position 1       position 2    position 3   position 4  position 5
		"SELECT DATASOURCE_NAME, CATALOG_NAME, SCHEMA_NAME, TABLE_NAME, PARENT_PATH " +
		"FROM ALL_TABLES " +
		"WHERE DATASOURCE_NAME IN "; // will be appended with a list of data sources in the IN clause.

	/**
	 * Statement ending for Queries
	 */
	private static String orderByForQuery = " ORDER BY PARENT_PATH ";

	/**
	 * Filter on the above Sql for procedures to get procedures for only 
	 * specific published datasources (virtual database)
	 */
	private static String procsDatasourceSql  = " AND procs.DATASOURCE_NAME IN ";
		
	/**
	 * Filter on the above Sql for ws to get ws for only  
	 * specific published web services 
	 */
	private static String wsDatasourceSqlWhereBasic  = " WHERE ALL_WSDL_OPERATIONS.OWNER <> 'system' ";

	private static String wsDatasourceSqlWhereIn  = " WHERE ALL_WSDL_OPERATIONS.OWNER <> 'system' AND ALL_WSDL_OPERATIONS.DATASOURCE_NAME IN ";

	private static String defaultNamespaceUrl = "http://www.compositesw.com";

	// The soap 11 soap header.  Note: TARGET_NAMESPACE_URL will get replaced in upon usage
	private static String soap11MsgStart = 	"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"TARGET_NAMESPACE_URL\">\n" +
											"	<soapenv:Header/>\n"+
											"    <soapenv:Body>\n";

	// The soap 12 soap header.  Note: TARGET_NAMESPACE_URL will get replaced in upon usage
	private static String soap12MsgStart = 	"<soapenv:Envelope xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:ns1=\"TARGET_NAMESPACE_URL\">\n" +
											"	<soapenv:Header/>\n"+
											"    <soapenv:Body>\n";
	
	private static String soapMsgEnd =   	"    </soapenv:Body>\n" +
										 	"</soapenv:Envelope>";
		
	private static String contentTypeSoap11 = "text/xml;charset=UTF-8";
	private static String contentTypeSoap12 = "application/soap+xml;charset=UTF-8";
	
	private static String LEGACY_WEB_SERVICE_TYPE="VirtualWsdl";
	private static String COMPOSITE_WEB_SERVICE_TYPE="CompositeWebService";
	
	/**
	 * query to be generated for each published view or table.
	 */
	public String publishedViewQry;  
	
	/**
	 * query to be generated for each published CIS procedure if useSelectForProcs is true. Otherwise ignored.
	 */
	public String publishedProcQry;         
		
	/**
	 * HashMap containing default values for each CIS data type
	 */
	public HashMap<String,String> defaultParamValuesMap;

	/**
	 * ArrayList of RegressionQuery items read from the RegressionModule.xml file (or equivalent).
	 * This is the list of queries that will be used to generate the input file.
	 */
	private ArrayList<RegressionQuery> regressionQueryList = new ArrayList<RegressionQuery>();
	
	/**
	 * True if we want to generate calls to published CIS procedures as 
	 * SELECT * FROM procName(params).
	 * False if we want to use {CALL procName(params)}
	 */
	public boolean 		 useSelectForProcs	  = true;		  		   
	
	/**
	 * Variables that define which types of entries we create in the input file. 
	 */
    boolean needQueries = true;
    boolean needProcs = true;
    boolean needWs = true;
    
	/**
	 * Variables for counting the generated queries for the input file
	 */
    int totalQueriesGenerated = 0;
	int totalProceduresGenerated = 0;
	int totalWebServicesGenerated = 0;
	
	/**
	 * Initializes object variable with default values.
	 */
	public RegressionInputFileJdbcDAOImpl()
	{
		publishedViewQry = "SELECT count(1) cnt FROM ";
		publishedProcQry = "SELECT count(*) cnt FROM "; 
		
		defaultParamValuesMap = new HashMap<String,String>();
		
		defaultParamValuesMap.put("BIT", "1"); // 0 or 1

		defaultParamValuesMap.put("VARCHAR", "'a'");
		defaultParamValuesMap.put("CHAR", "'a'");
		defaultParamValuesMap.put("CLOB", "'A'");
		
		defaultParamValuesMap.put("INTEGER", "1");
		defaultParamValuesMap.put("INT", "1");
		defaultParamValuesMap.put("BIGINT", "1");
		defaultParamValuesMap.put("SMALLINT", "1");
		defaultParamValuesMap.put("TINYINT", "1");

		defaultParamValuesMap.put("DECIMAL", "1.0");
		defaultParamValuesMap.put("NUMERIC", "1.0");
		defaultParamValuesMap.put("REAL", "1.0");
		defaultParamValuesMap.put("FLOAT", "1.0");
		defaultParamValuesMap.put("DOUBLE", "1.0");		
		
		defaultParamValuesMap.put("DATE", "'2011-01-01'");  
		defaultParamValuesMap.put("TIME", "'00:00:00'");  		
		defaultParamValuesMap.put("TIMESTAMP", "'2011-01-01 00:00:00'");  // ANSI/ISO SQL Standard format is: TIMESTAMP 'YYYY-MM-DD HH:MM:SS [.fractional-seconds]'
				
		defaultParamValuesMap.put("BINARY", "''"); 
		defaultParamValuesMap.put("VARBINARY", "''");
		defaultParamValuesMap.put("BLOB", "''");
		
		defaultParamValuesMap.put("XML", "''");
	}
	
	/**
	 * Generates pubtest input file for a given CIS instance and other input parameters, such as domain, user, published datasource and others. 
	 * 
	 * @param cisServerConfig   		composite server object used for connections
	 * @param regressionConfig			regression config object
	 * @param regressionQueries 		regression query object
	 * 
	 * @return String representation of the input file
	 * 
	 * @throws CompositeException
	 */
	public String generateInputFile(CompositeServer cisServerConfig, RegressionTestType regressionConfig, RegressionQueriesType regressionQueries) // String serverId, String dsList, String pathToRegressionXML, String pathToServersXML)
	throws CompositeException
	{
// First check the input parameter values:
		if (cisServerConfig == null || regressionConfig == null)
		{
			throw new CompositeException(
					"XML Configuration objects are not initialized " +
					"when trying to generate Regression input file.");
		}
        
		// Initialize start time and format
        Date startDate = new Date();

		// Initialize all variables
        String prefix = "generateInputFile";
        String outString = null;               				  // Output String the above buffer is converted to.   

        String queriesStr = "";
        String proceduresStr = "";
        String wsStr = "";
		boolean getActualLinkType = false;

		// Get the DEBUG3 value from the property file
        setGlobalProperties();
        
        populateConfigValues(regressionConfig, regressionQueries);
                
		totalQueriesGenerated = 0;
		totalProceduresGenerated = 0;
		totalWebServicesGenerated = 0;
		
		// Begin the input file generation
        if(this.needQueries)
        {
        	/**
        	 * [QUERY]
			 * database=MYTEST
			 * SELECT count(1) cnt FROM CAT1.SCH1.customers
        	 */
    		RegressionItem[] items = buildQueriesString(cisServerConfig, regressionConfig);
    		
			// Output the query to the input file
            for (int i=0; i<items.length; i++)
            {
           		StringBuffer buf = new StringBuffer();      
            	RegressionItem item = new RegressionItem();
            	item = items[i];
				buf.append("[QUERY]\n");
	        	buf.append("database=" + item.database + "\n");	// datasource
	        	if (item.outputFilename != null)
	        		buf.append("outputFilename="+item.outputFilename+"\n"); // outputFilename
	        	buf.append(item.input + "\n\n"); // patterns: table | schema.table | cat.schema.table
	        	queriesStr = queriesStr + buf.toString();

	        	// Add debug statement to log output when debug3=true
		        CommonUtils.writeOutput("Added query to query list:           resource path="+item.resourcePath+"  type="+item.resourceType+"  query="+item.input, prefix,"-debug3",logger,debug1,debug2,debug3);
            }
        }
        if(this.needProcs)
        {
        	if (this.useSelectForProcs)
        	{        
            	/**
            	 * [PROCEDURE]
    			 * database=MYTEST
    			 * SELECT * FROM CAT1.SCH1.LookupProduct(1)
            	 */
        		RegressionItem[] items = buildProcsStringSelectSyntax(cisServerConfig, regressionConfig);
        		
				// Output the query to the input file
                for (int i=0; i<items.length; i++)
                {
               		StringBuffer buf = new StringBuffer();      
                	RegressionItem item = new RegressionItem();
                	item = items[i];
					buf.append("[PROCEDURE]\n");
		        	buf.append("database=" + item.database + "\n");	// datasource
		        	if (item.outTypes != null && item.outTypes.length > 0) {
		        		String outTypes = null;
		        		for (int j=0; j < item.outTypes.length; j++) {
		        			if (outTypes == null) {
		        				outTypes = "";
		        			} else {
		        				outTypes = outTypes + ", ";
		        			}
		        			outTypes = outTypes + item.outTypes[j];
		        		}
		        		buf.append("outTypes="+outTypes + "\n");
		        	}
		        	if (item.outputFilename != null)
		        		buf.append("outputFilename="+item.outputFilename+"\n"); // outputFilename
		        	buf.append(item.input + "\n\n"); // patterns: table | schema.table | cat.schema.table
		        	proceduresStr = proceduresStr + buf.toString();
					
		        	// Add debug statement to log output when debug3=true
			        CommonUtils.writeOutput("Added procedure to query list:       resource path="+item.resourcePath+"  type="+item.resourceType+"  query="+item.input, prefix,"-debug3",logger,debug1,debug2,debug3);
                }
        	}
        	else
        	{
            	/**
            	 * [PROCEDURE]
    			 * database=MYTEST
    			 * CALL CAT1.SCH1.LookupProduct(1)
            	 */
         		RegressionItem[] items = buildProcsStringCallSyntax(cisServerConfig, regressionConfig);
        		
				// Output the query to the input file
                for (int i=0; i<items.length; i++)
                {
               		StringBuffer buf = new StringBuffer();      
                	RegressionItem item = new RegressionItem();
                	item = items[i];
					buf.append("[PROCEDURE]\n");
		        	buf.append("database=" + item.database + "\n");	// datasource
		        	if (item.outTypes != null && item.outTypes.length > 0) {
		        		String outTypes = null;
		        		for (int j=0; j < item.outTypes.length; j++) {
		        			if (outTypes == null) {
		        				outTypes = "";
		        			} else {
		        				outTypes = outTypes + ", ";
		        			}
		        			outTypes = outTypes + item.outTypes[j];
		        		}
		        		buf.append("outTypes="+outTypes + "\n");
		        	}
		        	if (item.outputFilename != null)
		        		buf.append("outputFilename="+item.outputFilename+"\n"); // outputFilename
		        	buf.append(item.input + "\n\n"); // patterns: table | schema.table | cat.schema.table
		        	proceduresStr = proceduresStr + buf.toString();
		        	 
		        	// Add debug statement to log output when debug3=true
			        CommonUtils.writeOutput("Added call procedure to query list:  resource path="+item.resourcePath+"  type="+item.resourceType+"  query="+item.input, prefix,"-debug3",logger,debug1,debug2,debug3);
               }
        	}
        }
        if (this.needWs)
        {
        	/**
        	 * [WEB_SERVICE]
			 *	database=testWebService00_NoParams_wrapped
			 *	path=/soap11/testWebService00_NoParams_wrapped
			 *	action=ViewSales
			 *	encrypt=false
			 *	contentType=text/xml;charset=UTF-8
			 *	<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://tempuri.org/">
			 *		<soapenv:Header/>
			 *	    <soapenv:Body>
			 *	        <ns1:ViewSales>
			 *	            <ns1:ViewSalesInput></ns1:ViewSalesInput>
			 *	        </ns1:ViewSales>
			 *	    </soapenv:Body>
			 *	</soapenv:Envelope>
        	 */
    		RegressionItem[] items = buildWsString(cisServerConfig, regressionConfig, getActualLinkType);
    		
			// Output the query to the input file
            for (int i=0; i<items.length; i++)
            {
        		StringBuffer buf = new StringBuffer();      
            	RegressionItem item = new RegressionItem();
            	item = items[i];
				buf.append("[WEB_SERVICE]\n");
	    		buf.append("database=" + item.database + "\n");
	        	buf.append("path=" + item.path + "\n"); // name of the web service port with path is the path in the input file 
	        	buf.append("action=" + item.action + "\n");
	        	buf.append("encrypt="+ item.encrypt + "\n");
	        	buf.append("contentType=" + item.contentType + "\n");
	        	if (item.outputFilename != null)
	        		buf.append("outputFilename="+item.outputFilename+"\n"); // outputFilename
	        	buf.append(item.input + "\n\n");
	        	wsStr = wsStr + buf.toString();
	        	
	        	// Add debug statement to log output when debug3=true
	        	String queryNoLines = item.input.replaceAll(Pattern.quote("\n"), Matcher.quoteReplacement(""));
		        CommonUtils.writeOutput("Added web service to query list:     resource path="+item.resourcePath+"  type="+item.resourceType+"  query="+queryNoLines, prefix,"-debug3",logger,debug1,debug2,debug3);
            }
        }
		
        // Write the pubtest input file to the file system.
        outString = new String(fileDescription + queriesStr + proceduresStr + wsStr);	// Built String
        String filePath =  CommonUtils.extractVariable(prefix, regressionConfig.getInputFilePath(), propertyFile, true);
        CommonUtils.createFileWithContent(filePath, outString);

        // Print out timings
        String duration = CommonUtils.getElapsedTime(startDate);
        
        		 int len = 56;
				 logger.info("--------------------------------------------------------");
				 logger.info("------------ Regression Generation Summary -------------");
				 logger.info("--------------------------------------------------------");
				 logger.info("                                                        ");
logger.info(CommonUtils.rpad("            Total Queries Generated: " + totalQueriesGenerated, len, " "));
logger.info(CommonUtils.rpad("         Total Procedures Generated: " + totalProceduresGenerated, len, " "));
logger.info(CommonUtils.rpad("       Total Web Services Generated: " + totalWebServicesGenerated, len, " "));
				 logger.info("                                     ---------          ");
logger.info(CommonUtils.rpad("Total Combined ---------> Generated: " + (totalQueriesGenerated+totalProceduresGenerated+totalWebServicesGenerated), len, " "));
				 logger.info("                                                        ");
logger.info(CommonUtils.rpad("     Input file generation duration: " + duration, len, " "));
				 logger.info("                                                        ");
				 logger.info("Review input file: "+filePath);
				 logger.info("--------------------------------------------------------");

		String moduleActionMessage = "MODULE_INFO: Generate Summary: Queries="+totalQueriesGenerated+" Procedures="+totalProceduresGenerated+" WebServices="+totalWebServicesGenerated;
		System.setProperty("MODULE_ACTION_MESSAGE", moduleActionMessage);

		return outString;
	} // end method
	
	
	
	/**
	 * Generates security query XML for a given CIS instance and other input parameters, such as domain, user, published datasource and others.
	 * This method uses the same <newFileParams> structure as "generateInputFile" does and the same rules for getting the Queries, Procedures and Web Services
	 * 
	 * @param cisServerConfig   		composite server object used for connections
	 * @param regressionConfig			regression config object
	 * @param regressionQueries 		regression query object
	 * @param regressionSecurityQueries regression security query object
	 * @param mode						"OVERWRITE" or "APPEND" to the existing regressionSecurityQueries object
	 * 
	 * @return RegressionSecurityQueryType updated regression security object
	 * 
	 * @throws CompositeException
	 */
	public RegressionSecurityQueriesType generateSecurityQueriesXML(CompositeServer cisServerConfig, RegressionTestType regressionConfig, RegressionQueriesType regressionQueries, RegressionSecurityQueriesType regressionSecurityQueries, String mode) throws CompositeException 
	{
		RegressionSecurityQueriesType regressionSecurityQueriesReturn = new RegressionSecurityQueriesType();
			
		// Initialize all variables
        String prefix = "generateSecurityQueryXML";
		String rsqId = null;
		String rsqIds = "";
		int rsqIdsCount = 0;
		totalQueriesGenerated = 0;
		totalProceduresGenerated = 0;
		totalWebServicesGenerated = 0;
		boolean getActualLinkType = true;
      
		// Get the DEBUG3 value from the property file
        setGlobalProperties();

        populateConfigValues(regressionConfig, regressionQueries);
                
        // If the mode=APPEND, then determine how may many regression security queries Ids (rsqIds) currently exist and create a list of the ids to be used during appending.
        if (mode.equalsIgnoreCase("APPEND")) {
            if (regressionSecurityQueries.getRegressionSecurityQuery() != null && regressionSecurityQueries.getRegressionSecurityQuery().size() > 0) {
				List<RegressionSecurityQueryType> regressionSecurityQueryList = regressionSecurityQueries.getRegressionSecurityQuery();

				// Loop over the list of regression security queries
				for (RegressionSecurityQueryType regressionSecurityQueryListLoop : regressionSecurityQueryList) 
				{
					if (regressionSecurityQueryListLoop.getId() != null && regressionSecurityQueryListLoop.getId().length() > 0) {
						rsqIds = rsqIds + regressionSecurityQueryListLoop.getId() + " ";
						rsqIdsCount++;

						// Add the existing item to the return variable
						regressionSecurityQueriesReturn.getRegressionSecurityQuery().add(regressionSecurityQueryListLoop);
					}
				}
            }        	
        }
        
        
		// Begin the input file generation
        if(this.needQueries)
        {
        	/**
        	 * [QUERY]
			 * database=MYTEST
			 * SELECT count(1) cnt FROM CAT1.SCH1.customers
        	 */
    		RegressionItem[] items = buildQueriesString(cisServerConfig, regressionConfig);
    		
			// Output the query to the input file
            for (int i=0; i<items.length; i++)
            {
            	// Initialize variables
            	RegressionSecurityQueryType rsq = new RegressionSecurityQueryType();
             	RegressionItem item = new RegressionItem();
            	item = items[i];
            	
            	boolean queryFound = queryExistsInRegressionSecurityList(item, regressionSecurityQueries);
            	
            	if (!queryFound) 
            	{
	            	// Calculate the rsqId
	            	rsqId = "";
	            	while (true) {
	            		++rsqIdsCount;
	            		rsqId = "rsq" + rsqIdsCount;
	            		if (!rsqIds.toLowerCase().contains(rsqId)) {
	            			break;
	            		}
	            	}
	            	
	            	// Set the Regression Security Query Type 
	            	rsq.setQueryType("QUERY");
	            	rsq.setId(rsqId);
	            	rsq.setDatasource(item.database);
	            	rsq.setQuery(item.input);	// patterns: table | schema.table | cat.schema.table
	            	rsq.setResourcePath(item.resourcePath);
	            	rsq.setResourceType(item.resourceType);
	            	
					// Add the existing item to the return variable
					regressionSecurityQueriesReturn.getRegressionSecurityQuery().add(rsq);

		        	// Add debug statement to log output when debug3=true
			        CommonUtils.writeOutput("Added query to query list:           resource path="+item.resourcePath+"  type="+item.resourceType+"  query="+item.input, prefix,"-debug3",logger,debug1,debug2,debug3);
            	}
            }
        }
        if(this.needProcs)
        {
        	if (this.useSelectForProcs)
        	{        
            	/**
            	 * [PROCEDURE]
    			 * database=MYTEST
    			 * SELECT * FROM CAT1.SCH1.LookupProduct(1)
            	 */
        		RegressionItem[] items = buildProcsStringSelectSyntax(cisServerConfig, regressionConfig);
        		
				// Output the query to the input file
                for (int i=0; i<items.length; i++)
                {
                	// Initialize variables
                	RegressionSecurityQueryType rsq = new RegressionSecurityQueryType();
                 	RegressionItem item = new RegressionItem();
                	item = items[i];
                	
                	boolean queryFound = queryExistsInRegressionSecurityList(item, regressionSecurityQueries);
                	
                	if (!queryFound) 
                	{
	                	// Calculate the rsqId
	                	rsqId = "";
	                	while (true) {
	                		++rsqIdsCount;
	                		rsqId = "rsq" + rsqIdsCount;
	                		if (!rsqIds.toLowerCase().contains(rsqId)) {
	                			break;
	                		}
	                	}
	                	
	                	// Set the Regression Security Query Type 
	                	rsq.setQueryType("PROCEDURE");
	                	rsq.setId(rsqId);
	                	rsq.setDatasource(item.database);
	                	rsq.setQuery(item.input);	// patterns: table | schema.table | cat.schema.table
			        	if (item.outTypes != null && item.outTypes.length > 0) {
			        		String outTypes = null;
			        		for (int j=0; j < item.outTypes.length; j++) {
			        			if (outTypes == null) {
			        				outTypes = "";
			        			} else {
			        				outTypes = outTypes + ", ";
			        			}
			        			outTypes = outTypes + item.outTypes[j];
			        		}
			        		rsq.setProcOutTypes(outTypes);
			        	}
		            	rsq.setResourcePath(item.resourcePath);
		            	rsq.setResourceType(item.resourceType);
	
			        	// Add the existing item to the return variable
						regressionSecurityQueriesReturn.getRegressionSecurityQuery().add(rsq);
						
			        	// Add debug statement to log output when debug3=true
				        CommonUtils.writeOutput("Added procedure to query list:       resource path="+item.resourcePath+"  type="+item.resourceType+"  query="+item.input, prefix,"-debug3",logger,debug1,debug2,debug3);
                	}
               }
        	} else {
            	/**
            	 * [PROCEDURE]
    			 * database=MYTEST
    			 * CALL CAT1.SCH1.LookupProduct(1)
            	 */
         		RegressionItem[] items = buildProcsStringCallSyntax(cisServerConfig, regressionConfig);
        		
				// Output the query to the input file
                for (int i=0; i<items.length; i++)
                {
                	// Initialize variables
                	RegressionSecurityQueryType rsq = new RegressionSecurityQueryType();
                 	RegressionItem item = new RegressionItem();
                	item = items[i];
                	
                	boolean queryFound = queryExistsInRegressionSecurityList(item, regressionSecurityQueries);
                	
                	if (!queryFound) 
                	{
	                	// Calculate the rsqId
	                	rsqId = "";
	                	while (true) {
	                		++rsqIdsCount;
	                		rsqId = "rsq" + rsqIdsCount;
	                		if (!rsqIds.toLowerCase().contains(rsqId)) {
	                			break;
	                		}
	                	}
	                	
	                	// Set the Regression Security Query Type 
	                	rsq.setQueryType("PROCEDURE");
	                	rsq.setId(rsqId);
	                	rsq.setDatasource(item.database);
	                	rsq.setQuery(item.input);	// patterns: table | schema.table | cat.schema.table
			        	if (item.outTypes != null && item.outTypes.length > 0) {
			        		String outTypes = null;
			        		for (int j=0; j < item.outTypes.length; j++) {
			        			if (outTypes == null) {
			        				outTypes = "";
			        			} else {
			        				outTypes = outTypes + ", ";
			        			}
			        			outTypes = outTypes + item.outTypes[j];
			        		}
			        		rsq.setProcOutTypes(outTypes);
			        	}
		            	rsq.setResourcePath(item.resourcePath);
		            	rsq.setResourceType(item.resourceType);
	
			        	// Add the existing item to the return variable
						regressionSecurityQueriesReturn.getRegressionSecurityQuery().add(rsq);
				        
			        	// Add debug statement to log output when debug3=true
				        CommonUtils.writeOutput("Added call procedure to query list:  resource path="+item.resourcePath+"  type="+item.resourceType+"  query="+item.input, prefix,"-debug3",logger,debug1,debug2,debug3);
                	}
                }
        	}
        }
        if (this.needWs)
        {
        	/**
        	 * [WEB_SERVICE]
			 *	database=testWebService00_NoParams_wrapped
			 *	path=/soap11/testWebService00_NoParams_wrapped
			 *	action=ViewSales
			 *	encrypt=false
			 *	contentType=text/xml;charset=UTF-8
			 *	<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://tempuri.org/">
			 *		<soapenv:Header/>
			 *	    <soapenv:Body>
			 *	        <ns1:ViewSales>
			 *	            <ns1:ViewSalesInput></ns1:ViewSalesInput>
			 *	        </ns1:ViewSales>
			 *	    </soapenv:Body>
			 *	</soapenv:Envelope>
        	 */
    		RegressionItem[] items = buildWsString(cisServerConfig, regressionConfig, getActualLinkType);
    		
			// Output the query to the input file
            for (int i=0; i<items.length; i++)
            {
            	// Initialize variables
            	RegressionSecurityQueryType rsq = new RegressionSecurityQueryType();
             	RegressionItem item = new RegressionItem();
            	item = items[i];
            	
            	boolean queryFound = queryExistsInRegressionSecurityList(item, regressionSecurityQueries);
            	
            	if (!queryFound) 
            	{
	            	// Calculate the rsqId
	            	rsqId = "";
	            	while (true) {
	            		++rsqIdsCount;
	            		rsqId = "rsq" + rsqIdsCount;
	            		if (!rsqIds.toLowerCase().contains(rsqId)) {
	            			break;
	            		}
	            	}
	            	
	            	// Set the Regression Security Query Type 
	            	rsq.setQueryType("WEB_SERVICE");
	            	rsq.setId(rsqId);
	            	rsq.setDatasource(item.database);
	            	rsq.setWsPath(item.path);
	            	rsq.setWsAction(item.action);
	            	rsq.setWsEncrypt(String.valueOf(item.encrypt));
	            	rsq.setWsContentType(item.contentType);
	            	rsq.setQuery(item.input);
	            	rsq.setResourcePath(item.resourcePath);
	            	rsq.setResourceType(item.resourceType);
	
	            	// Add the existing item to the return variable
					regressionSecurityQueriesReturn.getRegressionSecurityQuery().add(rsq);
		        	
		        	// Add debug statement to log output when debug3=true
		        	String queryNoLines = item.input.replaceAll(Pattern.quote("\n"), Matcher.quoteReplacement(""));
			        CommonUtils.writeOutput("Added web service to query list:     resource path="+item.resourcePath+"  type="+item.resourceType+"  query="+queryNoLines, prefix,"-debug3",logger,debug1,debug2,debug3);
            	}
            }
        }	
		return regressionSecurityQueriesReturn;
	}
	
	/**
	 * Determine if the query already exists in the regressionSecurityList XML.
	 * 
	 * @param query
	 * @param regressionSecurityQueries
	 * @return
	 */
	private boolean queryExistsInRegressionSecurityList(RegressionItem item, RegressionSecurityQueriesType regressionSecurityQueries) {
		
		String resourcePath = item.resourcePath; 
		String resourceType = item.resourceType;
		String query = item.input;
		String path = item.path;
		String action = item.action;
		
		boolean queryFound = false;
        if (	resourcePath!= null && resourcePath.length() > 0 && 
        		resourceType != null && resourceType.length() > 0 &&
        		regressionSecurityQueries.getRegressionSecurityQuery() != null && regressionSecurityQueries.getRegressionSecurityQuery().size() > 0) 
        {
			List<RegressionSecurityQueryType> regressionSecurityQueryList = regressionSecurityQueries.getRegressionSecurityQuery();

			// Loop over the list of regression security queries
			for (RegressionSecurityQueryType regressionSecurityQueryListLoop : regressionSecurityQueryList) 
			{
				String rsqResourcePath = null;
				if (regressionSecurityQueryListLoop.getResourcePath() != null)
					rsqResourcePath = regressionSecurityQueryListLoop.getResourcePath();
				String rsqResourceType = null;
				if (regressionSecurityQueryListLoop.getResourceType() != null)
					rsqResourceType = regressionSecurityQueryListLoop.getResourceType();
				
				if (rsqResourcePath != null && rsqResourceType != null) {
					if (resourcePath.equals(rsqResourcePath) && resourceType.equals(rsqResourceType)) {
						queryFound = true;
						break;
					}					
				} else {
					if (regressionSecurityQueryListLoop.getQuery() != null) {
						String rsqQuery = regressionSecurityQueryListLoop.getQuery();
						String rsqWsPath = regressionSecurityQueryListLoop.getWsPath();
						String rsqAction = regressionSecurityQueryListLoop.getWsAction();
						
						if (item.type == TYPE_WS) 
						{						
							// Compare the ws path and ws input query
							if (path != null && action != null && path.equals(rsqWsPath) && action.equals(rsqAction)) {
								queryFound = true;
								break;
							}
						} else {
							if (query.equals(rsqQuery)) {
								queryFound = true;
								break;
							}	
						}
					}
				}
			}
        }        	
        return queryFound;
	}
	
	/**
	 * Processes a ResultSet of a specific query
	 * (defined by class variable compositeSqlTablesRequest)
	 * and builds a StringBuffer for the QUERY part of the pubtest input file.  
	 * 
	 * @param views
	 * @return
	 * @throws SQLException
	 */
	private RegressionItem[] buildQueriesString(CompositeServer cisServerConfig, RegressionTestType regressionConfig) throws CompositeException
	{
		String prefix = "buildQueriesString";
		JdbcConnector connector = new JdbcConnector();
		Connection conn = null;
		
	    PreparedStatement getViewsStmt = null;        
	    ResultSet rsViews = null;
	    List items = new ArrayList();
	    
        boolean useAllDatasources = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getUseAllDatasources(), propertyFile, false));
        boolean useDefaultViewQuery = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getUseDefaultViewQuery(), propertyFile, false));
        
        try
        {
        	conn = connector.connectToCis(cisServerConfig, "system");            
            if (useAllDatasources)
            {
            	getViewsStmt = conn.prepareStatement(stmtStrAllPublishedTablesExceptSystem + orderByForQuery);
            }
            else // need a filter for list of datasources
            {
            	String dsListStr = RegressionManagerUtils.createDsListString(regressionConfig.getNewFileParams().getDatasources(), propertyFile);	
            	getViewsStmt = conn.prepareStatement(stmtStrPublishedTablesForListOfDSources + " (" + dsListStr + ") " + orderByForQuery);         	            	
            }
            
            rsViews = getViewsStmt.executeQuery();
	            
			if (rsViews == null)		{	return null;}
			
			while (rsViews.next())
	        {
				// (1) DATASOURCE_NAME, (2) CATALOG_NAME, (3) SCHEMA_NAME, (4) TABLE_NAME (5) PARENT_PATH
				// Apply the reserved list to the path - double quote special characters (periods), embedded spaces and reserved words
				String datasourceName = CommonUtils.applyReservedListToPath(rsViews.getString(1), "/");
				String catalogName = CommonUtils.applyReservedListToPath(rsViews.getString(2), "/");
				String schemaName = CommonUtils.applyReservedListToPath(rsViews.getString(3), "/");
				String tableName = CommonUtils.applyReservedListToPath(rsViews.getString(4), "/");
				String resourcePath = rsViews.getString(5) + "/" + rsViews.getString(4);
				String resourceType = "TABLE";

				/* Determine if the specific resource should be compared by checking the XML resource list.
				 * If the resourceURL pattern matches what is in this list then process it.
				 * 		<resources>
				 *			<resource>TEST1.*</resource>
				 *			<resource>TEST1.SCH.*</resource>
				 *			<resource>TEST1.SCH.VIEW1</resource>
				 *		</resources>
				 */
				String resourceURL = "";
				if (catalogName != null) {
					resourceURL = resourceURL + catalogName + ".";
				}
				if (schemaName != null) {
					resourceURL = resourceURL + schemaName + ".";
					
				}
				if (tableName != null) {
					resourceURL = resourceURL + tableName;
				}
	        	boolean resourceMatch = RegressionManagerUtils.findResourceMatch(resourceURL, regressionConfig.getNewFileParams().getResources(), propertyFile);

	        	if (resourceMatch) {
					// Derive the from clause from the catalog, schema and table name
					String fromClause = RegressionManagerUtils.constructKey(catalogName, schemaName, tableName, null);
					String key = RegressionManagerUtils.constructKey(datasourceName, catalogName, schemaName, tableName);
					RegressionQuery rquery = RegressionManagerUtils.getRegressionQuery(key, regressionQueryList);
					String viewQry = null;    
					if (rquery != null && !useDefaultViewQuery) {
						viewQry = rquery.query;
					} else {
						viewQry = publishedViewQry + fromClause;
					}
	            	/*  Item Class         	[QUERY]
	            	 *  ----------         	---------------
	            	 *  item.type        	= TYPE_QUERY       
					 *	item.database    	= database=MYTEST
					 *  item.outputFilename = outputFilename=CAT1.SCH1.ViewSales.txt
					 *	iemt.input       	= SELECT * FROM CAT1.SCH1.ViewSales
					 */
					// Output the query to the input file
					RegressionItem item = new RegressionItem();
					item.type = TYPE_QUERY;
					item.database = datasourceName;
					item.input = viewQry;
					item.resourcePath = resourcePath;
					item.resourceType = resourceType;

		        	// Retrieve only the FROM clause table URL with no where clause and no projections in order to create a file name.
		        	item.outputFilename = RegressionManagerUtils.getTableUrl(item.input).replaceAll("\"", "") + ".txt";

		        	items.add(item);
		        	
		        	// Count a query
		        	totalQueriesGenerated++;
	        	}
	        }
			connector.closeJdbcConnection(conn);
        }
        catch (SQLException e)
        {
        	throw new CompositeException("Problem executing query: \n" + e.getMessage());
        }
        catch (Exception e)
        {
        	throw new CompositeException("Exception caught: \n" + e.getMessage());
        }
        
		return (RegressionItem[])items.toArray(new RegressionItem[items.size()]); //buf.toString();
	}
	
	/**
	 * Connects to CIS, obtains necessary info from the system tables
	 * and builds a StringBuffer for the PROCEDURE part of the pubtest input file.  
	 * 
	 * @param procs
	 * @return
	 * @throws SQLException
	 */
	private RegressionItem[] buildProcsStringSelectSyntax(CompositeServer cisServerConfig, RegressionTestType regressionConfig) throws CompositeException
	{
		String prefix = "buildProcsStringSelectSyntax";
		JdbcConnector connector = new JdbcConnector();
		Connection conn = null;	
        PreparedStatement getProceduresStmt = null;   
        ResultSet rsProcs = null;
	    List items = new ArrayList();
	    
        boolean useAllDatasources = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getUseAllDatasources(), propertyFile, false));
        boolean useDefaultProcQuery = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getUseDefaultProcQuery(), propertyFile, false));

        try
        {
        	conn = connector.connectToCis(cisServerConfig, "system");            
            if (useAllDatasources)
            {
            	getProceduresStmt = conn.prepareStatement(compositeSqlProcsRequestForSelectSyntax + orderByForProcs);           	
            }
            else // need a filter for list of datasources
            {
            	String dsListStr = RegressionManagerUtils.createDsListString(regressionConfig.getNewFileParams().getDatasources(), propertyFile);	
            	
        		getProceduresStmt = conn.prepareStatement(
        				compositeSqlProcsRequestForSelectSyntax + 
        				procsDatasourceSql + " (" + dsListStr + ") " +
        				orderByForProcs);       	            	
            }
            
            rsProcs = getProceduresStmt.executeQuery();            
 		
			if (rsProcs == null)		{	return null;}
			
			RegressionItem item = new RegressionItem();
			boolean queryProvided = false;
		    int currentProcId = -1;
		    int lastProcId = -1;
	        while (rsProcs.next())
	        {
				// (1) DATASOURCE_NAME, (2) CATALOG_NAME, (3) SCHEMA_NAME, (4) PROCEDURE_ID, (5) PROCEDURE_NAME, (6) DATA_TYPE, (7) PARENT_PATH
				// Apply the reserved list to the path - double quote special characters (periods), embedded spaces and reserved words
				String datasourceName = CommonUtils.applyReservedListToPath(rsProcs.getString(1), "/");
				String catalogName = CommonUtils.applyReservedListToPath(rsProcs.getString(2), "/");
				String schemaName = CommonUtils.applyReservedListToPath(rsProcs.getString(3), "/");
				currentProcId = rsProcs.getInt(4);
				String procedureName = CommonUtils.applyReservedListToPath(rsProcs.getString(5), "/");
				String dataType = rsProcs.getString(6);
				String resourcePath = rsProcs.getString(7) + "/" + rsProcs.getString(5);
				String resourceType = "PROCEDURE";

				/* Determine if the specific resource should be compared by checking the XML resource list.
				 * If the resourceURL pattern matches what is in this list then process it.
				 * 		<resources>
				 *			<resource>TEST1.*</resource>
				 *			<resource>TEST1.SCH.*</resource>
				 *			<resource>TEST1.SCH.VIEW1</resource>
				 *		</resources>
				 */
				String resourceURL = "";
				if (catalogName != null) {
					resourceURL = resourceURL + catalogName + ".";
				}
				if (schemaName != null) {
					resourceURL = resourceURL + schemaName + ".";
					
				}
				if (procedureName != null) {
					resourceURL = resourceURL + procedureName;
				}
	        	boolean resourceMatch = RegressionManagerUtils.findResourceMatch(resourceURL, regressionConfig.getNewFileParams().getResources(), propertyFile);

	        	if (resourceMatch) {
					
		        	if(lastProcId != currentProcId)  // first iteration or new procedure
		        	{
		        		// Count a new procedure
		        		totalProceduresGenerated++;
		        		
		        		if(lastProcId != -1)   // new procedure, not first iteration
		        		{
		        			if (!queryProvided) {
		        				item.input = item.input + ")";
		        			}
		    	        	// Retrieve only the FROM clause procedure URL with no where clause and no projections in order to create a file name.
		    	        	item.outputFilename = RegressionManagerUtils.getTableUrl(item.input).replaceAll("\"", "") + ".txt";

		    	        	// Add to the list of items
							items.add(item);
							item = new RegressionItem();
		        		}
						// Derive the from clause from the catalog, schema and table name
						String fromClause = RegressionManagerUtils.constructKey(catalogName, schemaName, procedureName, null);
						String key = RegressionManagerUtils.constructKey(datasourceName, catalogName, schemaName, procedureName);
						RegressionQuery rquery = RegressionManagerUtils.getRegressionQuery(key, regressionQueryList); 
						String procQry = null;    
						if (rquery != null && !useDefaultProcQuery) {
							procQry = rquery.query;
							queryProvided = true;
						} else {
							procQry = publishedProcQry + fromClause + "( ";
							queryProvided = false;
						}
		               	/*  Item Class         	[PROCEDURE]
		            	 *  ----------         	---------------
		            	 *  item.type        	= TYPE_PROCEDURE       
						 *	item.database    	= database=MYTEST
						 *  item.outTypes    	= outTypes=INTEGER
						 *  item.outputFilename = outputFilename=CAT1.SCH1.LookupProduct.txt
						 *	item.input       	= SELECT count(*) cnt FROM CAT1.SCH1.LookupProduct( 1  ) 
						 */
						// Output the query to the input file
						item.type = TYPE_PROCEDURE;
						item.database = datasourceName;
						item.input = procQry;
						item.resourcePath = resourcePath;
						item.resourceType = resourceType;
		        	}
		        	else
		        	{
		        		if (!queryProvided) {
		        			item.input = item.input + ", ";
		        		}
		        	}
		        	if (!queryProvided) {
			        	String defaultParamValue = defaultParamValuesMap.get(dataType); // default value for the given param type
			        	if (defaultParamValue == null)
			        	{
			        		throw new CompositeException("defaultParamValuesMap doesn't contain param type " + dataType);
			        	}
						item.input = item.input + defaultParamValue + " ";

		        	}
		        	lastProcId = currentProcId;
	        	}
	        }
	        if(lastProcId != -1)   // we had at least one procedure.
        	{
	        	if (!queryProvided) {
	        		item.input = item.input + ")";
	        	}
	        	// Retrieve only the FROM clause procedure URL with no where clause and no projections in order to create a file name.
	        	item.outputFilename = RegressionManagerUtils.getTableUrl(item.input).replaceAll("\"", "") + ".txt";
	        	
	        	// Add to the list of items
				items.add(item);
        	}
	        // Close connections                
	        connector.closeJdbcConnection(conn);
        }
        catch (SQLException e)
        {
        	System.out.println("Problem executing query");
        	System.out.println(e.getMessage());
        	throw new CompositeException("Problem executing query: \n" + e.getMessage());
        }
        catch (Exception e)
        {
        	System.out.println("Exception caught: \n");
        	System.out.println(e.getMessage());
        	throw new CompositeException("Exception caught: \n" + e.getMessage());
        }
        
		return (RegressionItem[])items.toArray(new RegressionItem[items.size()]); //buf.toString();		
	}
	
     /**
	 * 
	 * @param procs
	 * @return
	 * @throws SQLException
	 */
	private RegressionItem[] buildProcsStringCallSyntax(CompositeServer cisServerConfig, RegressionTestType regressionConfig) throws CompositeException
	{
		String prefix = "buildProcsStringCallSyntax";
		JdbcConnector connector = new JdbcConnector();
		Connection conn = null;	
        PreparedStatement getProceduresStmt = null;   
        ResultSet rsProcs = null;
	    List items = new ArrayList();
	    
        boolean useAllDatasources = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getUseAllDatasources(), propertyFile, false));
        boolean useDefaultProcQuery = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getUseDefaultProcQuery(), propertyFile, false));
		
	    try
	    {
        	conn = connector.connectToCis(cisServerConfig, "system");            
            if (useAllDatasources)
            {
            	getProceduresStmt = conn.prepareStatement(compositeSqlProcsRequestForCallSyntax + orderByForProcs);
            }
            else // need a filter for list of datasources
            {
            	String dsListStr = RegressionManagerUtils.createDsListString(regressionConfig.getNewFileParams().getDatasources(), propertyFile);	
            	
        		getProceduresStmt = conn.prepareStatement(
        				compositeSqlProcsRequestForCallSyntax + 
        				procsDatasourceSql + " (" + dsListStr + ") " +
        				orderByForProcs);
            }
	            
	        rsProcs = getProceduresStmt.executeQuery();
	            		
			if (rsProcs == null)		{	return null;	}
			
			RegressionItem item = new RegressionItem();
			boolean queryProvided = false;
	        int currentProcId = -1;
	        int lastProcId = -1;
	        while (rsProcs.next())
	        {
				// (1) DATASOURCE_NAME, (2) CATALOG_NAME, (3) SCHEMA_NAME, (4) PROCEDURE_ID, (5) PROCEDURE_NAME, (6) PARAMETER_NAME, (7) DATA_TYPE, (8) PARENT_PATH
				String datasourceName = CommonUtils.applyReservedListToPath(rsProcs.getString(1), "/");
				String catalogName = CommonUtils.applyReservedListToPath(rsProcs.getString(2), "/");
				String schemaName = CommonUtils.applyReservedListToPath(rsProcs.getString(3), "/");
				currentProcId = rsProcs.getInt(4);
				String procedureName = CommonUtils.applyReservedListToPath(rsProcs.getString(5), "/");
				String paramName = rsProcs.getString(6);
				String dataType = rsProcs.getString(7);
				String resourcePath = rsProcs.getString(8) + "/" + rsProcs.getString(5);
				String resourceType = "PROCEDURE";

				/* Determine if the specific resource should be compared by checking the XML resource list.
				 * If the resourceURL pattern matches what is in this list then process it.
				 * 		<resources>
				 *			<resource>TEST1.*</resource>
				 *			<resource>TEST1.SCH.*</resource>
				 *			<resource>TEST1.SCH.VIEW1</resource>
				 *		</resources>
				 */
				String resourceURL = "";
				if (catalogName != null) {
					resourceURL = resourceURL + catalogName + ".";
				}
				if (schemaName != null) {
					resourceURL = resourceURL + schemaName + ".";
					
				}
				if (procedureName != null) {
					resourceURL = resourceURL + procedureName;
				}
	        	boolean resourceMatch = RegressionManagerUtils.findResourceMatch(resourceURL, regressionConfig.getNewFileParams().getResources(), propertyFile);

	        	if (resourceMatch) {
		        	if(lastProcId != currentProcId)  // first iteration or new procedure
		        	{
		        		// Count a new procedure
		        		totalProceduresGenerated++;
		        		
		        		if(lastProcId != -1)   // new procedure, not first iteration
		        		{
		        			if (!queryProvided) {
			        			item.input = item.input + " )}";
		        			}
		    	        	// Retrieve only the FROM clause procedure URL with no where clause and no projections in order to create a file name.
		    	        	item.outputFilename = RegressionManagerUtils.getTableUrl(item.input).replaceAll("\"", "") + ".txt";

		    	        	// Add to the list of items
							items.add(item);
							item = new RegressionItem();
		        		}
						// Derive the from clause from the catalog, schema and table name
						String fromClause = RegressionManagerUtils.constructKey(catalogName, schemaName, procedureName, null);
						String key = RegressionManagerUtils.constructKey(datasourceName, catalogName, schemaName, procedureName);
						RegressionQuery rquery = RegressionManagerUtils.getRegressionQuery(key, regressionQueryList); 
						String procQry = null;    
						if (rquery != null && rquery.query.toUpperCase().contains("CALL ") && !useDefaultProcQuery) {
							procQry = rquery.query;
							queryProvided = true;
						} else {
							procQry = "{ CALL " + fromClause + "( "; 
							queryProvided = false;
						}        		
		               	/*  Item Class         	[PROCEDURE]
		            	 *  ----------         	---------------
		            	 *  item.type        	= TYPE_PROCEDURE       
						 *	item.database    	= database=MYTEST
						 *  item.outTypes    	= outTypes=INTEGER
						 *  item.outputFilename = outputFilename=LookupProduct.txt
						 *	item.input       	= { CALL LookupProduct(1) }
						 */
						// Output the query to the input file
						item.type = TYPE_PROCEDURE;
						item.database = datasourceName;	// datasource
						item.input = procQry;			//  call procedure
						item.resourcePath = resourcePath;
						item.resourceType = resourceType;					
		        	}
		        	else
		        	{
	        			if (!queryProvided) {
	        				item.input = item.input + ", ";
	        			}
		        	}
	    			if (!queryProvided) {
			        	String defaultParamValue = defaultParamValuesMap.get(dataType); // default value for the given param type
			        	if (defaultParamValue == null)
			        	{
			        		throw new CompositeException("defaultParamValuesMap doesn't contain param type " + dataType);
			        	}
						item.input = item.input + defaultParamValue + " ";
	    			}
		        	lastProcId = currentProcId;
	        	}
	        }
	        if(lastProcId != -1)   // we had at least one procedure.
        	{
	        	if (!queryProvided) {
	        		item.input = item.input + ")}";
	        	}
	        	// Retrieve only the FROM clause procedure URL with no where clause and no projections in order to create a file name.
	        	item.outputFilename = RegressionManagerUtils.getTableUrl(item.input).replaceAll("\"", "") + ".txt";

	        	// Add to the list of items
				items.add(item);
        	}
	        // Close connections
	        connector.closeJdbcConnection(conn);
	    }
	    catch (SQLException e)
        {
        	System.out.println("Problem executing query");
        	System.out.println(e.getMessage());
        	throw new CompositeException("Problem executing query: \n" + e.getMessage());
        }
        catch (Exception e)
        {
        	System.out.println("Exception caught: \n");
        	System.out.println(e.getMessage());
        	throw new CompositeException("Exception caught: \n" + e.getMessage());
        }
	    
        return (RegressionItem[])items.toArray(new RegressionItem[items.size()]); //buf.toString();
	}
	
	/**
	 * Connects to CIS via JDBC, obtains necessary info from the system table
	 * and builds a StringBuffer for the WEB_SERVICE part of the pubtest input file.
	 * Also uses CIS admin API WS call to get parameter list for an operation, which is
	 * not empty for operations of type published procedure. Such WS system calls could 
	 * have also be used instead of making a JDBC call to the system table, but in that case 
	 * we would have to traverse the CIS resource tree, which is less convenient, especially
	 * if we need to create calls for all published WS datasources: in that case published 
	 * Web Services are not always on the top of the tree under the publsihed WS datasource,
	 * they may be deeper in the tree under folders, like for example 'admin' and 'util' 
	 * Web Services are under the 'system' forlder for the admin API itself.  
	 * 
	 * @param cisServerConfig
	 * @param regressionConfig
	 * @param getActualLinkType - true=invoke the server to get the actual type (only set true for generateSecurityQueriesXML), false=leave as link
	 * @return
	 * @throws CompositeException
	 */
	private RegressionItem[] buildWsString(CompositeServer cisServerConfig, RegressionTestType regressionConfig, boolean getActualLinkType) throws CompositeException
	{	
		String prefix = "buildWsString";
		JdbcConnector connector = new JdbcConnector();
		Connection conn = null;	
		PreparedStatement getWsStmt = null;   
		ResultSet rsWs = null;
		ResourceList wsDatasourceDetails = null;  // List of WS data source details
		ResourceList wsOperationDetails = null;  // List of all operations for a given WS
		String wsFullPortPath = null;
		String wsPath = null;
		String namespaceUrl = null;
		String soapMsgStart = null;
		boolean queryProvided = false;
		boolean encrypt = false;
		int pos = 0;
	    List items = new ArrayList();
	    int r = 0;
		
		// These variables are for extracting values out of the new composite web service bindingModel XML
		String xpathInput = null;
		boolean wrapped = false;
		String portBindingType = null;
		String parameterStyle = null;
		String wrappedElementName = null;
		String targetNamespace = null;
		boolean paramsInit = false;
		HashMap<Integer,ProcParams> paramMap = null; //sequence number, (actual element name, schema input name, schema type, value (not used) )
		String indent = CommonConstants.indent;	
	    
		// Get the users decision about whether to use all datasources or the specified ones.
        boolean useAllDatasources = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getUseAllDatasources(), propertyFile, false));
        boolean useDefaultWSQuery = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getUseDefaultWSQuery(), propertyFile, false));

        // Get the users decision about which soap protocol to use. [all|soap11|soap12]
		String soapType = CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getCreateSoapType(), propertyFile, false);

		// This is the temporary file to write out the bindingModel XML.  
		// It will go in the same location at the generated regression input file
		String tempFileName = "bindingModelTemp.xml";
		String tempFilePath = null;
		// Use the <tempDirPath> when it is available in the file otherwise revert to previous code.
		if (regressionConfig.getTempDirPath() != null && regressionConfig.getTempDirPath().trim().length() > 0) 
		{
			tempFilePath = CommonUtils.extractVariable(prefix, regressionConfig.getTempDirPath(), propertyFile, true);

			if (tempFilePath.contains("/")) {
				tempFilePath = tempFilePath + "/" + tempFileName;
				tempFilePath = tempFilePath.replaceAll("//", "/");
			}
			if (tempFilePath.contains("\\")) {
				tempFilePath = tempFilePath + "\\\\" + tempFileName;
				tempFilePath = tempFilePath.replaceAll("\\\\\\\\", "\\\\");
			}
		} 
		// Extract the temporary path from the input file path when temp directory is not available.  Maintain backward compatibility.
		else {
			tempFilePath =  CommonUtils.extractVariable(prefix, regressionConfig.getInputFilePath(), propertyFile, true);
			int pos1 = tempFilePath.lastIndexOf("/");
			int pos2 = tempFilePath.lastIndexOf("\\");
			if (pos1 < 0 && pos2 < 0) {
				tempFilePath = tempFileName;
			} else if (pos1 > pos2) {
				tempFilePath = tempFilePath.substring(0, pos+1) + tempFileName;
			} else if (pos2 > pos1) {
				tempFilePath = tempFilePath.substring(0, pos2+1) + tempFileName;
			} 
			tempFilePath = tempFilePath.replaceAll("//", "/");
			tempFilePath = tempFilePath.replaceAll("\\\\\\\\", "\\\\");
		}
	
	    try
	    {
	    	// Create a JDBC connection to the Composite server as system
        	conn = connector.connectToCis(cisServerConfig, "system");
        	
        	/* Execute the following SELECT statement to retrieve all operations for the web service data sources
        	 * 
        	 *		"SELECT "+
        	 *			"ALL_WSDL_OPERATIONS.DATASOURCE_NAME, "+// position 1
        	 *			"ALL_WSDL_OPERATIONS.OPERATION_NAME, "+	// position 2
        	 *			"ALL_WSDL_OPERATIONS.PARENT_PATH, "+	// position 3
        	 *			"ALL_DATASOURCES.DATASOURCE_TYPE, "+	// position 4
        	 *			"ALL_WSDL_OPERATIONS.OWNER, "+			// position 5
        	 *			"ALL_WSDL_OPERATIONS.PARENT_PATH " +	// position 6
        	 *		"FROM "+
        	 *		"	ALL_WSDL_OPERATIONS ALL_WSDL_OPERATIONS INNER JOIN "+
        	 *		"	  ALL_DATASOURCES ALL_DATASOURCES "+
        	 *		"	ON ALL_WSDL_OPERATIONS.DATASOURCE_ID = ALL_DATASOURCES.DATASOURCE_ID ";
        	 */
            if (useAllDatasources)
            {
            	/*
            	 * Prepare the query statement with the following WHERE clause
            	 *
            	 * " WHERE ALL_WSDL_OPERATIONS.OWNER <> 'system' "
            	 * " ORDER BY ALL_WSDL_OPERATIONS.DATASOURCE_NAME, ALL_WSDL_OPERATIONS.PARENT_PATH, ALL_WSDL_OPERATIONS.OPERATION_NAME "
            	 */
            	getWsStmt = conn.prepareStatement(compositeSqlWsRequest + wsDatasourceSqlWhereBasic + orderByForWs);
            }
            else // need a filter for list of datasources
            {
            	// Retrieve the list of data sources and create a comma separate list
            	String dsListStr = RegressionManagerUtils.createDsListString(regressionConfig.getNewFileParams().getDatasources(), propertyFile);	
            	
            	/*
            	 * Prepare the query statement with the following WHERE clause
            	 * 
            	 * " WHERE ALL_WSDL_OPERATIONS.OWNER <> 'system' AND ALL_WSDL_OPERATIONS.DATASOURCE_NAME IN (" + dsListStr + ") "
            	 * " ORDER BY ALL_WSDL_OPERATIONS.DATASOURCE_NAME, ALL_WSDL_OPERATIONS.PARENT_PATH, ALL_WSDL_OPERATIONS.OPERATION_NAME "
            	 */
        		getWsStmt = conn.prepareStatement(compositeSqlWsRequest + wsDatasourceSqlWhereIn + " (" + dsListStr + ") " +	orderByForWs);            	       	            	
            }
	            
            // Execute the query
	        rsWs = getWsStmt.executeQuery();
	        
			if (rsWs == null) {	
				return null;	
			}

			// Get a WS stub for Resource Port that contains getChildResources operation (system WS call):
			ResourcePortType wsOperationsPort = CisApiFactory.getResourcePort(cisServerConfig);			

			/* One iteration per Web Service operation 
			 * 
			 * Note: A datasource may have more than one operation.  
			 *       This is accounted for in the original query which will order by the datasource parent path and operation name.
			 */
			while (rsWs.next())  
	        {
				// Initialize variables including the parameter map for each operation
				paramMap = new HashMap<Integer,ProcParams>();
				paramsInit = false;
				
				/* (1) DATASOURCE_NAME, (2) OPERATION_NAME, (3) PARENT_PATH (4) DATASOURCE_TYPE (5) OWNER
				 	DATASOURCE_TYPE:
				 		VirtualWsdl			= Legacy Web Service
				 		CompositeWebService	= New Composite Web Service 6.1 and higher
				 	
				 	Example output from the SQL statement:
				 	
				 	DATASOURCE_NAME,	OPERATION_NAME,	PARENT_PATH,													DATASOURCE_TYPE,		OWNER
				 	-------------------	---------------	---------------------------------------------------------------	-----------------------	-----
					ProductWebService,	LookupProduct,	/services/webservices/ProductWebService,						CompositeWebService,	admin
					ProductWebService,	LookupProduct2,	/services/webservices/ProductWebService,						CompositeWebService,	admin
					test,				ViewOrder,		/services/webservices/test,										CompositeWebService,	admin
					testWebService00,	testprocecho,	/services/webservices/testWebService00/testService/operations,	VirtualWsdl,			admin
					testWebService00,	testprocsimple,	/services/webservices/testWebService00/testService/operations,	VirtualWsdl,			admin
				*/
				String wsDatasource = rsWs.getString(1);
				String wsOperation = rsWs.getString(2);
				String wsParentPath = rsWs.getString(3);
				String wsType = rsWs.getString(4); // [VirtualWsdl|CompositeWebService]
				String dsOwner = rsWs.getString(5);
				String resourcePath = rsWs.getString(6) + "/" + wsOperation;
				String resourceType = "LINK";

				/* Determine if the specific resource should be compared by checking the XML resource list.
				 * If the resourceURL pattern matches what is in this list then process it.
				 * 		<resources>
				 *			<resource>/TEST/testWebService00</resource> 		<-- this is a legacy web service with a path
				 *			<resource>testWebService00_NoParams_bare</resource>	<-- new composite soap 11 web service with specific path
				 *			<resource>testWebService00*</resource>				<-- new composite soap 11 web service matching a wild card
				 *		</resources>
				 */
				// Remove the leading path "/services/webservices/"
				// Change all double slashes to single slashes
				// Change all slashes to periods
				String resourceURL = resourcePath.replaceAll("/services/webservices/", "").replaceAll("//", "/").replaceAll("/", "."); // construct ws path from the path and action combined.
				
				// Remove the leading "." in the path
				if (resourceURL.startsWith(".")) {
					resourceURL = resourceURL.substring(1);
				}
				
				if (logger.isDebugEnabled()) {
					if (r == 0) {
						System.out.println(indent+"Regression Module /newFileParams/resources:");
						for (r=0; r < regressionConfig.getNewFileParams().getResources().getResource().size(); r++){
							System.out.println(indent+indent+"["+regressionConfig.getNewFileParams().getResources().getResource().get(r)+"]");
						}
						System.out.println("");
					}
					System.out.println(indent+"Web Service:  resourceURL=["+resourceURL+"]             resourcePath=["+resourcePath+"]");
				}

				// Look for a match with the list of resources
	        	boolean resourceMatch = RegressionManagerUtils.findResourceMatch(resourceURL, regressionConfig.getNewFileParams().getResources(), propertyFile);
	        	
	        	if (resourceMatch) 
	        	{        	       	
					// Get the actual resource type
					if (getActualLinkType) 
					{
						Resource resource = getResourceDAO().getResourceCompositeServer(cisServerConfig, resourcePath);
						LinkResource linkResource = (LinkResource) resource;
						if (linkResource.getTargetType() != null)
							resourceType = linkResource.getTargetType().name();
					}
	
					if (logger.isDebugEnabled()) {
						System.out.println("");
						System.out.println("-----------------------------------------------------");
						System.out.println("SQL Invocation for Web Service List:");
						System.out.println("-----------------------------------------------------");
						System.out.println(indent+"Web Service datasource: "+wsDatasource);
						System.out.println(indent+"Web Service  operation: "+wsOperation);
						System.out.println(indent+"Web Service parentPath: "+wsParentPath);
						System.out.println(indent+"Web Service     dsType: "+wsType); 
						System.out.println(indent+"Web Service    dsOwner: "+dsOwner);
					}
	
					/* 
					 * Get the web service datasource "FULL" details
					 */
					wsDatasourceDetails = wsOperationsPort.getResource(wsParentPath, null, DetailLevel.FULL); // system ws parent datasource call
					
					/*
					 *  Iterate over the "resource" details
					 */
					int size = wsDatasourceDetails.getResource().size();
					for (int i=0; i < size; i++) 
					{
						String wsResourceType = wsDatasourceDetails.getResource().get(i).getType().name(); 
					
						if (logger.isDebugEnabled()) {
							System.out.println("");
							System.out.println(indent+"-----------------------------------------------------");
							System.out.println(indent+"Admin API Invocation for Web Service Operation Level:");
							System.out.println(indent+"-----------------------------------------------------");
							System.out.println(indent+"Resource Type: "+ wsResourceType);
						}
						
						/*
						 * Extract Details from the "New Composite Web Service"
						 * 
						 * The New Composite Web Service resource is of type DATA_SOURCE.
						 * The Legacy web service resource is of type CONTAINER.
						 */
						if (wsResourceType.equalsIgnoreCase("DATA_SOURCE")) {
							DataSourceResource ds = (DataSourceResource) wsDatasourceDetails.getResource().get(i);
							if (logger.isDebugEnabled())
								System.out.println(indent+"Data Source Type: "+ ds.getDataSourceType().toString()); // COMPOSITE_WEB_SERVICE
	
							// Get the list of attributes from the DataSourceResource response
							List<Attribute> attributeList =  ds.getAttributes().getAttribute(); 
							
							// Iterate through the attribute list looking for "bindingModel"
							for (Attribute attr : attributeList) {
								String name = attr.getName();
								if (logger.isDebugEnabled())
									System.out.println(indent+"Attribute Name: "+ name);
								
								// Only extract the "bindingModel" Attribute
								if (name.equals("bindingModel")) {
									String bindingModelString = attr.getValue().toString();
	
									CommonUtils.createFileWithContent(tempFilePath, bindingModelString);
	
									// Gets 1st iteration of type :: soapPortBinding | restfulPortBinding
									xpathInput = "/bindingModel/portBindings/portBinding[@xsi:type='soapPortBinding']/@xsi:type";
									portBindingType = XMLXPath.xpath(xpathInput, tempFilePath);
	
									// Gets 1st iteration of targetNamespace :: http://tempuri.org/
									xpathInput = "/bindingModel/portBindings/portBinding[@xsi:type='soapPortBinding']/@targetNamespace";
									targetNamespace = XMLXPath.xpath(xpathInput, tempFilePath);
	
									namespaceUrl = "";
									if (targetNamespace != null)
										namespaceUrl = targetNamespace;
									
									// Get parameterStyle for the given resourceName :: WRAPPED | BARE
									xpathInput = "/bindingModel/portBindings/portBinding[@xsi:type='soapPortBinding']/operationBindings/operationBinding[@resourceName='"+wsOperation+"']/inputBinding/@parameterStyle";
									parameterStyle = XMLXPath.xpath(xpathInput, tempFilePath);
									
									if (parameterStyle.equalsIgnoreCase("WRAPPED"))
										wrapped = true;
	
									if (parameterStyle.equalsIgnoreCase("BARE"))
										wrapped = false;
	
									// Get wrapped element name for the given resourceName :: {http://tempuri.org/}LookupProduct
									xpathInput = "/bindingModel/portBindings/portBinding[@xsi:type='soapPortBinding']/operationBindings/operationBinding[@resourceName='"+wsOperation+"']/inputBinding/wrapper/@elementName";
									wrappedElementName = XMLXPath.xpath(xpathInput, tempFilePath);
									// Extract the value to the right of any namespace {http://tempuri.org/} if it exists
									wrappedElementName = getParameter(wrappedElementName);
									
									// Execute the next section in a loop (p) to get each parameter
									/*
									 * Extract Parameter List.
									 * 
									 * Keep incrementing the loop until no more values are extracted...When the type comes back null.
									 */
									boolean moreFound = true;	// Loop control variable
									int p = 1; 					// XML array iteration value for "parameterBinding".  Always starts at 1 for XML.
									int numParams = 0; 			// Number of parameters found and the key value for the HashMap.  Always starts at 0 for java arrays.
									while (moreFound) {
										
										// Extract the port binding type in order to provide a loop control.  Finished when null.
										//  <portBinding xsi:type="soapPortBinding"
										xpathInput = "/bindingModel/portBindings/portBinding[@xsi:type='soapPortBinding']/operationBindings/operationBinding[@resourceName='"+wsOperation+"']/parameterBindings/parameterBinding["+p+"]/@xsi:type";
										String type = XMLXPath.xpath(xpathInput, tempFilePath);
	
										if (type != null) {
											// Get the direction for a variable :: INPUT | OUTPUT
											//   direction="INPUT"
											xpathInput = "/bindingModel/portBindings/portBinding[@xsi:type='soapPortBinding']/operationBindings/operationBinding[@resourceName='"+wsOperation+"']/parameterBindings/parameterBinding["+p+"]/@direction";
											String direction = XMLXPath.xpath(xpathInput, tempFilePath);
	
											if (direction.equalsIgnoreCase("INPUT")) {
												// Get the actual parameter name as per the resource procedure :: desiredProduct
												//   name="desiredProduct"
												xpathInput = "/bindingModel/portBindings/portBinding[@xsi:type='soapPortBinding']/operationBindings/operationBinding[@resourceName='"+wsOperation+"']/parameterBindings/parameterBinding["+p+"]/@name";
												String inputParamName = XMLXPath.xpath(xpathInput, tempFilePath);
												// Extract the value to the right of any namespace {http://tempuri.org/} if it exists
												inputParamName = getParameter(inputParamName);
	
												// Get XML parameter name for the resource input variable :: {http://tempuri.org/}LookupProductDesiredproduct
												//   elementName="{http://tempuri.org/}LookupProductDesiredproduct"
												xpathInput = "/bindingModel/portBindings/portBinding[@xsi:type='soapPortBinding']/operationBindings/operationBinding[@resourceName='"+wsOperation+"']/parameterBindings/parameterBinding["+p+"]/@elementName";
												String inputParamSchema = XMLXPath.xpath(xpathInput, tempFilePath);
												// Extract the value to the right of any namespace {http://tempuri.org/} if it exists
												inputParamSchema = getParameter(inputParamSchema);
											
												// Create the Parameter HashMap
												if (inputParamSchema != null) {
													// Actual Param Name, WS Schema Name, Param Type, Param Default Value
													ProcParams procParams = new ProcParams(inputParamName, inputParamSchema, null, null);
													paramMap.put(numParams, procParams); 
													numParams++;
													
													// Let's the downstream code know that this was already initialized (Only done when WS=New Composite Web Service and not Legacy)
													paramsInit = true;
												}
											}
											p++;
										} else {
											moreFound = false;
										}
									}
	
									// Remove the temporary file
									CommonUtils.removeFile(tempFilePath);
									
									if (logger.isDebugEnabled()) {
										System.out.println(indent+"----------------------------------------");
										System.out.println(indent+"   portBindingType="+portBindingType);
										System.out.println(indent+"   targetNamespace="+targetNamespace);
										System.out.println(indent+"    parameterStyle="+parameterStyle);
										System.out.println(indent+"wrappedElementName="+wrappedElementName);
										System.out.println(indent+"Print Parameter List:");
	
										for (int j=0; j < paramMap.size(); j++) {
											System.out.println(indent+"   param name="+paramMap.get(j).paramName);
											System.out.println(indent+"  schema name="+paramMap.get(j).schemaName);
										}
									}
									
									/*
									 * Throw an exception as this combination is not allowed:
									 * 
									 * New Composite Web Service:
									 *   1. Input Parameter Style = BARE
									 *   2. Multiple input parameters for the procedure defined
									 *   
									 *   The parameter style must be defined as WRAPPED when there are multiple input parameters.
									 */
									if (parameterStyle.equalsIgnoreCase("BARE") && numParams > 1) {
										throw new CompositeException("Generate Regression Input File Error: For New Composite Web Services, the parameter style must be defined as WRAPPED when there are multiple input parameters.");
									}
								}
							}
						}
					}
	
					/*
					 *  Get the web service operation
					 */
					wsPath = wsParentPath + "/" + wsOperation;
					wsOperationDetails = wsOperationsPort.getResource(wsPath, null, DetailLevel.SIMPLE); // system ws operations call
					
					// LEGACY WS PARENT_PATH:                      /services/webservices/testWebService00/testService/operations
					//          Endpoint URL: http://localhost:9420/services/testWebService00/testService/testPort.ws
					//      Input file path:                       /services/testWebService00/testService/testPort.ws
					//
					//    NEW WS PARENT_PATH:        /services/webservices/ProductWebService
					//          Endpoint URL: http://localhost:9420/soap11/ProductWebService
					//          Endpoint URL: http://localhost:9420/soap12/ProductWebService
					//      Input file path:                       /soap12/ProductWebService
					String soap = "";
					String contentType = "";
	
					// Get the web service operations
					size = wsOperationDetails.getResource().size();
					for (int i=0; i < size; i++) {
						String type = wsOperationDetails.getResource().get(i).getType().name(); 
	
						if (type.equalsIgnoreCase("LINK")) {
							LinkResource link = (LinkResource) wsOperationDetails.getResource().get(i);
							
							ParameterList params = new ParameterList();
							// Get the parameter list for the link procedure
							if (link.getParameters() != null)
								params = link.getParameters();
							
							// Extract the parameters from the LINK procedure and compare with the bindingModel parameters.
							//   The objective is to get the parameter type and default value which was not possible with the bindingModel parameters 
							//   because the bindingModel does not contain types.
				        	int numParams = 0;
							int psize = params.getParameter().size();
							for (int j=0; j < psize; j++) {
								Parameter param = (Parameter) params.getParameter().get(j);
								String pname = param.getName();
						
								// Get the parameter type and determine the default value
								String sqlParamType = "VARCHAR"; // set the default param type
								String defaultValue = null;
			        			if( param.getDirection() != null && 
				        			!param.getDirection().toString().isEmpty() &&
				        			(param.getDirection().toString().equalsIgnoreCase("IN") || 
				        			 param.getDirection().toString().equalsIgnoreCase("INOUT")
				        			 ) && param.getDataType().getSqlType() != null)				// at least for now we'll only populate SQL params
				        		{	        				
			        				sqlParamType = param.getDataType().getSqlType().getDefinition().toString();
			        				String baseSqlParamType = sqlParamType.trim().toUpperCase();
			        				pos = sqlParamType.indexOf("(");
			        				if (pos > 0) {
			        					baseSqlParamType = sqlParamType.substring(0, pos).trim().toUpperCase();
			        				}
			        				defaultValue = CommonUtils.escapeXML(defaultParamValuesMap.get(baseSqlParamType));  // default value for this type.
	
				        			// Search for an existing entry in the parameter map which originated in the bindingModel code previously
									int paramNum = -1;
									if (paramsInit) {
										for (int k=0; k < paramMap.size() || paramNum < 0; k++) {
											if (pname.equalsIgnoreCase(paramMap.get(k).paramName)) {
												paramNum = k;
											}
										}
									}
	
			        				// Assign the parameter name, schema input name, parameter type and default value
				        			ProcParams procParams = null;
				        			
				        			if (!paramsInit) {		// Create a new entry if the params have not been previous initialized (for legacy ws)
				        				procParams = new ProcParams(pname, pname, sqlParamType, defaultValue);
				        				paramMap.put(numParams, procParams);
				        			
				        			} else { 				// Update an existing entry (for new composite ws)
				        				procParams = paramMap.get(paramNum);
				        				procParams.paramType = sqlParamType;
				        				procParams.defaultValue = defaultValue;
				        				paramMap.put(paramNum, procParams);
				        			}
									numParams++;						
				        		}        			
			        		}
							
							if (logger.isDebugEnabled()) {
								System.out.println(indent+"   Web Service Operation Level:");
								System.out.println(indent+"                 Resource Type: "+ type);
								System.out.println(indent+"                Operation path: "+ link.getPath());
								System.out.println(indent+"Completed Print Parameter List:");
								for (int j=0; j < paramMap.size(); j++) {
									System.out.println(indent+"     param name="+paramMap.get(j).paramName);
									System.out.println(indent+"    schema name="+paramMap.get(j).schemaName);
									System.out.println(indent+"     param type="+paramMap.get(j).paramType);
									System.out.println(indent+"  default value="+paramMap.get(j).defaultValue);
								}
							}
	
						}
					}
			
					/*
					 *  Establish the default protocol list for "all" protocols that are supported by CIS
					 *  
					 *  This is used to control what protocols are generated.  This list is pruned based on
					 *  what is found in the RegressionModule.xml and the type of web services.
					 *  
					 *  1. If the Legacy then only soap11 is generated.
					 *  2. If new composite web service then when RegressionModule.createSoapType=all then generate soap11 and soap12
					 *  3. If new composite web service then when RegressionModule.createSoapType=soap11 then generate soap11
					 *  4. If new composite web service then when RegressionModule.createSoapType=soap12 then generate soap12
					 *  
					 *  The soap type specifies whether to generate web services using soap11 (default), soap12 or all.  
					 *  	soap12 is only applicable for CIS 6.1 and higher and only if a CIS New Composite Web Service has been created.
					 *  <createSoapType>all</createSoapType>
					 */
					ArrayList<String> protocolList = new ArrayList<String>();
					protocolList.add("/soap11");			
					protocolList.add("/soap12");
					
					// Begin the protocol generation loop.  This is the loop that actually constructs the entry in the input file
					boolean doMoreSoapProtocols = true;
					while (doMoreSoapProtocols && protocolList.size() > 0) 
					{
						// Count a web service
						totalWebServicesGenerated++;
						
						/* 
						 * LEGACY WEB SERVICE - WRAPPED (default)
						 * 
						 * web service input file entry for legacy ws using wrapped input elements.
						 * 
						 [WEB_SERVICE]
						 database=testWebService00
						 path=/services/testWebService00/testService/testPort.ws
						 action=testprocecho
						 encrypt=false
						 contentType=text/xml;charset=UTF-8
						 <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.compositesw.com/services/webservices/testWebService00/testService">     
						 	<soapenv:Body>         
						 		<ns1:testprocecho>             
						 			<ns1:arg1Varchar>test_string</ns1:arg1Varchar>             
						 			<ns1:arg2Integer>5</ns1:arg2Integer>         
						 		</ns1:testprocecho>     
						 	</soapenv:Body> 
						 </soapenv:Envelope>
						 */
	
						// When type=VirtualWsdl
						if (wsType.equalsIgnoreCase(LEGACY_WEB_SERVICE_TYPE)) {
							// Default to soap11 for Legacy
							soap = "/soap11";
							// Set the content type
							contentType = contentTypeSoap11;
							
							// Extract the port path up from the web service parent path
							String wsPortPath = wsParentPath.substring(0, wsParentPath.indexOf("/operations"));     // removes "/operations" ending.
							// List of 2 elements: WS port and a List of all operations
							ResourceList wsChildrenList = wsOperationsPort.getChildResources(wsPortPath, ResourceType.CONTAINER, DetailLevel.FULL); // system ws call
							// Construct the required path: /services/testWebService00/testService/testPort.ws
							wsFullPortPath = wsPortPath.replace("/webservices", "") + "/" + getWsPortName(wsChildrenList);    // path as required in the input file.
							
							// The legacy web service namespace is the composite default namespace + the web service port path
							namespaceUrl = defaultNamespaceUrl + wsFullPortPath;
							
							// Replace "TARGET_NAMESPACE_URL" with the actual namespace url
							soapMsgStart = soap11MsgStart.replaceAll("TARGET_NAMESPACE_URL", namespaceUrl);
							
							// Set the element name that will wrap the input parameters and use the operation name as the wrapped element name
							wrapped = true;
							wrappedElementName = wsOperation;
							
							// Only go through the loop once for the default soap11
							doMoreSoapProtocols = false;
						} 
	
						/* 
						 * NEW COMPOSITE WEB SERVICE - WRAPPED
						 * 
						 * soap11 and soap12 input file entry using WRAPPED input
						 * 
						[WEB_SERVICE]
						 database=ProductWebService
						 path=/soap11/ProductWebService
						 action=LookupProduct
						 encrypt=false
						 contentType=text/xml;charset=UTF-8
						 <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://tempuri.org/">
						 	<soapenv:Header/>
						 	<soapenv:Body>
						 		<ns1:LookupProduct>
						 			<ns1:LookupProductDesiredproduct>10</ns1:LookupProductDesiredproduct>
						 		</ns1:LookupProduct>
						 	</soapenv:Body>
						 </soapenv:Envelope>
	
						 [WEB_SERVICE]
						 database=ProductWebService
						 path=/soap12/ProductWebService
						 action=LookupProduct
						 encrypt=false
						 contentType=application/soap+xml;charset=UTF-8
						 <soapenv:Envelope xmlns:soapenv="http://www.w3.org/2003/05/soap-envelope" xmlns:ns1="http://tempuri.org/">
						 	<soapenv:Header/>
						 	<soapenv:Body>
						 		<ns1:LookupProduct>
						 			<ns1:LookupProductDesiredproduct>10</ns1:LookupProductDesiredproduct>
						 		</ns1:LookupProduct>
						 		</soapenv:Body>
						 </soapenv:Envelope>
						 
						/* 
						 * NEW COMPOSITE WEB SERVICE - BARE
						 * 
						 * soap 11 and soap12 input file entry using BARE input
						 * 
						 * Note: Composite will throw an exception if BARE is selected in the Web Service configuration
						 *       and there is more than one parameter.
						 * 
						[WEB_SERVICE]
						database=ProductWebService
						path=/soap11/ProductWebService
						action=LookupProduct
						encrypt=false
						contentType=text/xml;charset=UTF-8
						<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://tempuri.org/">
							<soapenv:Header/>
						    <soapenv:Body>
						            <ns1:LookupProductDesiredproduct>1</ns1:LookupProductDesiredproduct>
						    </soapenv:Body>
						</soapenv:Envelope>
					 	
					 	[WEB_SERVICE]
						database=ProductWebService
						path=/soap12/ProductWebService
						action=LookupProduct
						encrypt=false
						contentType=application/soap+xml;charset=UTF-8
						<soapenv:Envelope xmlns:soapenv="http://www.w3.org/2003/05/soap-envelope" xmlns:ns1="http://tempuri.org/">
							<soapenv:Header/>
						    <soapenv:Body>
						            <ns1:LookupProductDesiredproduct>1</ns1:LookupProductDesiredproduct>
						    </soapenv:Body>
						</soapenv:Envelope>
	
	*/
						// When type=CompositeWebService
						if (wsType.equalsIgnoreCase(COMPOSITE_WEB_SERVICE_TYPE)) {
										
							// Determine the soap protocol
							soap = "/soap11"; // default to soap11
							if (soapType.equalsIgnoreCase("soap11")) {
								soap = "/soap11";
								doMoreSoapProtocols = false; // done after generating 1 web service for soap11
							}
							if (soapType.equalsIgnoreCase("soap12")) {
								soap = "/soap12";
								doMoreSoapProtocols = false; // done after generating 1 web service for soap11
							}
							if (soapType.equalsIgnoreCase("all")) { // generate a web service for all protocols in the soap protocol list
								soap = protocolList.get(0);
								protocolList.remove(0);
							}
		
							/*
							 *  Derive the full port path using the soap type and the parent path 
							 *  
							 *    soap11: /soap11/ProductWebService
							 *    soap12: /soap12/ProductWebService
							 */
							wsFullPortPath = soap + wsParentPath.replace("/services/webservices", "");
							
							/*
							 * Set content type and soap message start
							 */
							if (soap.toLowerCase().contains("soap11")) {
								// Set the soap starting message and content type for soap11
								contentType = contentTypeSoap11;
	
								// Replace "TARGET_NAMESPACE_URL" with the actual namespace url
								soapMsgStart = soap11MsgStart.replaceAll("TARGET_NAMESPACE_URL", namespaceUrl);
							} 
							if (soap.toLowerCase().contains("soap12")) {
								// Set the soap starting message and content type for soap12
								contentType = contentTypeSoap12;
	
								// Replace "TARGET_NAMESPACE_URL" with the actual namespace url
								soapMsgStart = soap12MsgStart.replaceAll("TARGET_NAMESPACE_URL", namespaceUrl);
							}
							
							// Check for the existence of the wrapped element name and default to the operation name if null;
							if (wrappedElementName == null)
								wrappedElementName = wsOperation;
						}
		
						// Do a lookup on RegressionModule.RegressionQueries to see if one exists for this web service operation.  
						//   The key consists of the datasource.wsFullPortPath.wsOperation
						String key = RegressionManagerUtils.constructKey(wsDatasource, wsFullPortPath, wsOperation, null);
						RegressionQuery rquery = RegressionManagerUtils.getRegressionQuery(key, regressionQueryList);
						
						// If a regressionQuery was found in the RegressionModule.xml then use this query instead of constructing a default query
						String wsQry = null;    
						if (rquery != null && !useDefaultWSQuery) {
							wsQry = rquery.query.trim();
							if (rquery.wsAction != null)
								wsOperation = rquery.wsAction;
							if (rquery.wsEncrypt != null)
								encrypt = Boolean.valueOf(rquery.wsEncrypt);
							if (rquery.wsContentType != null)
								contentType = rquery.wsContentType;
							queryProvided = true;
						} else {
							queryProvided = false;
						}
	
		            	/*  Item Class         	[WEB_SERVICE]
		            	 *  ----------         	---------------
		            	 *  item.type        	= TYPE_WS        
						 *	item.database    	= database=ProductWebService
						 *	item.path        	= path=/soap12/ProductWebService
						 *	item.action      	= action=LookupProduct
						 *	item.contentType 	= contentType=application/soap+xml;charset=UTF-8
						 *  item.outputFilename = outputFilename=soap12.ProductWebService.LookupProduct.txt
						 *	item.input       	= <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ns1="http://tempuri.org/">
						 *						   <soap:Header/>
						 *						   <soap:Body>
						 *						      <ns1:LookupProduct>
						 *						         <ns1:LookupProductDesiredproduct>10</ns1:LookupProductDesiredproduct>
						 *						      </ns1:LookupProduct>
						 *						   </soap:Body>
						 *						</soap:Envelope>
		            	 */
						RegressionItem item = new RegressionItem();
		        		item.type = TYPE_WS;
		        		item.database = wsDatasource;
		        		item.path = wsFullPortPath;
		        		item.action = wsOperation;
		        		item.encrypt = encrypt;
		        		item.contentType = contentType;
		        		item.resourcePath = resourcePath;
		        		item.resourceType = resourceType;
	
			        	// Retrieve only the WS path and action in order to create a file name.
		            	String wsURL = (item.path + "/" + item.action).replaceAll("//", "/").replaceAll("/", "."); // construct ws path from the path and action combined.
		            	if (wsURL.indexOf(".") == 0)
		            		wsURL = wsURL.substring(1);
		               	item.outputFilename = wsURL.replaceAll("\"", "") + ".txt";

			        	// The query was provided by the RegressionModule.xml (or equivalent) file.
		        		StringBuffer buf = new StringBuffer("");
			        	if (queryProvided) 
			        	{
			        		buf.append(wsQry);
			        	} 
			        	// Construct the default query from CIS parameter information
			        	else 
			        	{
			        		// Create the soap message start
				        	buf.append(soapMsgStart);
				        	
				        	// Create the wrapped parameter style input (Legacy Web Serivce or New Composite Web Service where parameterStype=WRAPPED
				        	//    If New Composite Web Service parameterStyle=BARE then don't put the wrapped element name
				        	if (wrapped)
				        		buf.append("        <ns1:"+wrappedElementName);
				        	
				        	// Create the parameters if they exist
				        	int numParams = paramMap.size();
				        	if (numParams > 0) {
				        		if (wrapped)
				        			buf.append(">\n");  // no forward slash here since this element will have children
	
				        		for (int j=0; j < paramMap.size(); j++) {						
			        				buf.append("            <ns1:" + paramMap.get(j).schemaName + ">");
			        				if (paramMap.get(j).defaultValue != null)
			        					buf.append(paramMap.get(j).defaultValue);  // default value for this type.
			        				buf.append("</ns1:" + paramMap.get(j).schemaName + ">\n");
								}
	
					        	// Create the end tag wrapped parameter style input (Legacy Web Serivce or New Composite Web Service where parameterStype=WRAPPED
					        	//    If New Composite Web Service parameterStyle=BARE then don't put the wrapped element name end tag
				        		if (wrapped)
				        			buf.append("        </ns1:"+wrappedElementName+">\n");
				        	}
				        	else  // this is not a published procedure, so we won't provide parameters for this operation
				        	{
				        		buf.append("/>\n");  // forward slash inside since this element doesn't not have children 
				        	}	 
				        	// Create the soap message end
				        	buf.append(soapMsgEnd);
			        	}
			        	item.input = buf.toString();
			        	items.add(item);
					} //  while (doMoreSoapProtocols && protocolList.size() > 0) 
	        	} //if (resourceMatch) {
	        } // while (rsWs.next()) 
			connector.closeJdbcConnection(conn);
        }
        catch (SQLException e)
        {
        	System.out.println("Problem executing query");
        	System.out.println(e.getMessage());
        	throw new CompositeException("Problem executing query: \n" + e.getMessage());
        }
        catch (Exception e)
        {
        	System.out.println("Exception caught: \n");
        	System.out.println(e.getMessage());
        	throw new CompositeException("Exception caught: \n" + e.getMessage());
        }
     		
        return (RegressionItem[])items.toArray(new RegressionItem[items.size()]); //buf.toString();
	}
	
	/**
	 * Extracts the parameter name from a string containing a namespace and parameter: "{namespace}parameter"
	 *  example 1:
	 * 		 IN: {http://tempuri.org/}LookupProductDesiredproduct
	 * 		OUT: LookupProductDesiredproduct
	 *  example 1:
	 * 		 IN: LookupProductDesiredproduct
	 * 		OUT: LookupProductDesiredproduct
	 *  example 1:
	 * 		 IN: null
	 * 		OUT: null
	 * 
	 * @param param
	 * @return
	 */
	private String getParameter(String param) {
		
		if (param != null) {
			int pos = param.indexOf("}") + 1;
			if (pos > 0) {
				param = param.substring(pos);
			} 
		}
		return param;
	}
	
	/**
	 * Looks through a list of child resources to find a web service port name
	 * 
	 * @param wsChildrenList
	 * @return
	 */
	private String getWsPortName(ResourceList wsChildrenList)
	{
		String wsPortName = null;
		List<Resource> resList = wsChildrenList.getResource();
		String resourceSubType = null;
		
		for(Resource res : resList)
		{
			resourceSubType = res.getSubtype().name(); 
			if (resourceSubType.equalsIgnoreCase("PORT_CONTAINER"))
			{
				wsPortName = res.getName();  // should end with Port
				break;
			}				
		}
		return wsPortName + ".ws";
	}
		
	// Set the global suppress and debug properties used throughout this class
	private static void setGlobalProperties() {
		
    	//set to -suppress if the property file contains a SUPPRESS_COMMENTS=true
		suppress="";
		String SUPPRESS_COMMENTS = CommonUtils.getFileOrSystemPropertyValue(propertyFile, "SUPPRESS_COMMENTS");
		if (SUPPRESS_COMMENTS != null && SUPPRESS_COMMENTS.equalsIgnoreCase("true")) {
			suppress=" -suppress";
		} 
		
		// Set the global debug properties used throughout this class
		String validOptions = "true,false";
	    // Get the property from the property file
	    String debug = CommonUtils.getFileOrSystemPropertyValue(propertyFile, "DEBUG1");
	    debug1 = false;
	    if (debug != null && validOptions.contains(debug)) {
	    	debug1 = Boolean.valueOf(debug);
	    }
	    debug = CommonUtils.getFileOrSystemPropertyValue(propertyFile, "DEBUG2");
	    debug2 = false;
	    if (debug != null && validOptions.contains(debug)) {
	    	debug2 = Boolean.valueOf(debug);
	    }
	    debug = CommonUtils.getFileOrSystemPropertyValue(propertyFile, "DEBUG3");
	    debug3 = false;
	    if (debug != null && validOptions.contains(debug)) {
	    	debug3 = Boolean.valueOf(debug);
	    }
	}

	/**
	 * Repopulates the class variable map defaultParamValuesMap
	 * with values from the config file if they are there. If a value is not found in the config file,
	 * the original default value remains. 
	 * 
	 * @param regressionConfig	config file for Regression module
	 * @throws CompositeException
	 */
	private void populateConfigValues(RegressionTestType regressionConfig, RegressionQueriesType regressionQueries) throws CompositeException
	{
		if (regressionConfig == null)
		{
			throw new CompositeException(
					"regressionConfig is null when trying to populate the config values from RegressionModule.xml");
		}
		String prefix = "populateConfigValues";

        boolean createQryCalls = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getCreateQueries(), propertyFile, false));
        boolean createProcsCalls = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getCreateProcedures(), propertyFile, false));
        boolean createWsCalls = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getCreateWS(), propertyFile, false));
		
        this.needQueries = createQryCalls; // these 3 params are mandatory in the config file, so we don't check for null or empty here - it has already been checked.
        this.needProcs = createProcsCalls;
        this.needWs = createWsCalls;
        
		String publishedViewQuery = CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getPublishedViewQry(), propertyFile, false);
		if (publishedViewQuery != null && !publishedViewQuery.isEmpty())
		{
			this.publishedViewQry = publishedViewQuery.trim()+" "; 
		}
		
		String publishedProcQuery = CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getPublishedProcQry(), propertyFile, false);
		if (publishedProcQuery != null && !publishedProcQuery.isEmpty())
		{
			this.publishedProcQry = publishedProcQuery.trim()+" "; 
		}
							
		addParamValuesMapEntry("BIT", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getBit(), propertyFile, false));		

		addParamValuesMapEntry("VARCHAR", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getVarchar(), propertyFile, false));
		addParamValuesMapEntry("CHAR", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getChar(), propertyFile, false));
		addParamValuesMapEntry("CLOB", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getClob(), propertyFile, false));

		addParamValuesMapEntry("INTEGER", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getInteger(), propertyFile, false));
		addParamValuesMapEntry("INT", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getInt(), propertyFile, false));
		addParamValuesMapEntry("BIGINT", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getBigint(), propertyFile, false));
		addParamValuesMapEntry("SMALLINT", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getSmallint(), propertyFile, false));
		addParamValuesMapEntry("TINYINT", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getTinyint(), propertyFile, false));
		
		addParamValuesMapEntry("DECIMAL", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getDecimal(), propertyFile, false));
		addParamValuesMapEntry("NUMERIC", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getNumeric(), propertyFile, false));
		addParamValuesMapEntry("REAL", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getReal(), propertyFile, false));
		addParamValuesMapEntry("FLOAT", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getFloat(), propertyFile, false));
		addParamValuesMapEntry("DOUBLE", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getDouble(), propertyFile, false));

		addParamValuesMapEntry("DATE", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getDate(), propertyFile, false));
		addParamValuesMapEntry("TIME", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getTime(), propertyFile, false));
		addParamValuesMapEntry("TIMESTAMP", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getTimestamp(), propertyFile, false));
		
		addParamValuesMapEntry("BINARY", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getBinary(), propertyFile, false));
		addParamValuesMapEntry("VARBINARY", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getVarbinary(), propertyFile, false));
		addParamValuesMapEntry("BLOB", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getBlob(), propertyFile, false));
		
		addParamValuesMapEntry("XML", CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getDefaultProcParamValues().getXml(), propertyFile, false));		
		// Populate the reqressionQuery ArrayList
		regressionQueryList = RegressionManagerUtils.populateRegressionQueryList(regressionQueries, regressionQueryList, propertyFile);
	}
	
	/**
	 * Adds a key-value pair to the Map defaultParamValuesMap 
	 * If key or value is empty, just skips, no exception is thrown in that case. 
	 * That is because this Map should already have default values populated, so
	 * here we overwrite a value, but only if it is passed. 
	 * 
	 * @param key
	 * @param value
	 */
	private void addParamValuesMapEntry(String key, String value)
	{
		if(key != null && value != null && !key.isEmpty() && !value.isEmpty())
		{
			defaultParamValuesMap.put(key, value);
		}
	}
	
	/**
	 *  Long string describing the input file. Goes to the beginning of the file. 
	 */
	private static String fileDescription =
		"# PubTest Input File \n" +
		"# Version: \n" +
		"# \n" +
		"# There are two types of entries allowed in this file: \n" + 
		"#   SQL queries and web service calls. \n" +
		"# \n" +
		"# The format of a SQL query is \n" +
		"# \n" +
		"#   [QUERY] \n" +
		"#   database=<name of published database> \n" +
		"#   outputFilename=<(optional) The name of the file to be used for Regression Test output otherwise filename is constructed as best effort from the \"FROM\" clause.> \n" +
		"#   <one or more lines containing the query ended by a blank line.> \n" +
		"# \n" +
		"#    Query Governor:  Use SELECT TOP n column-list to limit the number of rows returned. \n" +
		"# \n" +
		"# The format of a SQL stored procedure call is \n" +
		"# \n" +
		"#   [PROCEDURE] \n" +
		"#   database=<name of published database> \n" +
		"#   outTypes=<list of comma separated java.sql.Types JDBC type names> \n" +
		"#   outputFilename=<(optional) The name of the file to be used for Regression Test output otherwise filename is constructed as best effort from the \"FROM\" clause.> \n" +
		"#   <one or more lines containing the call statement or select statement ended by a blank line.> \n" +
		"# \n" +
		"#    Query Governor:  Use SELECT TOP n column-list to limit the number of rows returned.\n" +
		"# \n" +
		"#    For SELECT or CALL statements only IN and INOUT parameter values need to be provided. \n" +
		"#    Select will not work for procedures containg more than one OUT or INOUT parameter if one of them is a cursor. \n" +
		"# \n" +
		"#    outTypes should only be used if the procedure has non-cursor output values. \n" +
		"#    Each non-cursor output value is represented by a ? in the query. \n" +
		"#    The number of type names in outTypes should match the number of ? in the query. \n" +
		"# \n" +
		"# The format of a web service call is \n" +
		"# \n" +
		"#   [WEB_SERVICE] \n" +
		"#   database=<name of published web service> \n" +
		"#   path=<path to the port of the published web service.  Legacy WS uses soap11.  CIS 6.1 and higher WS may use soap11 or soap12. > \n" +
		"#   action=<the web service operation to execute> \n" +
		"#   encrypt=<use http (unencrypted) or https (encrypted) [true|false] > \n" +
		"#   contentType=<soap header content type: [soap11=text/xml;charset=UTF-8 | soap12=application/soap+xml;charset=UTF-8] > \n" +
		"#   outputFilename=<(optional) The name of the file to be used for Regression Test output otherwise filename is constructed as best effort from the \"path\" + \"action\".> \n" +
		"#   <input document> \n" +
		"# \n" +
		"# The path will be prefixed with \"http://<host>:<wsPort>/\". \n" +
		"# The input can span multiple lines. A blank line terminates the input. \n" +
		"#  \n\n"; 	
		
	/**
	 * @return the resourceDAO
	 */
	public ResourceDAO getResourceDAO() {
		if(this.resourceDAO == null){
			this.resourceDAO = new ResourceWSDAOImpl();
		}
		return resourceDAO;
	}

	/**
	 * @param resourceDAO the resourceDAO to set
	 */
	public void setResourceDAO(ResourceDAO resourceDAO) {
		this.resourceDAO = resourceDAO;
	}

}  // end class

class ProcParams
{
	// name:  parameter name
	// sname: schema name
	// type:  parameter type
	// value: default value
	ProcParams(String name, String sname, String type, String value)
	{
		paramName = name; schemaName = sname; paramType = type; defaultValue = value;
	}
	
	String paramName;
	String schemaName;
	String paramType;
	String defaultValue;

/*
 * This is an example of a "bindingModel" XML used by the local method "buildWsString()".
 * 
 * The bindingModel XML is stored in a Resource Attribute List of the data source.  
 * 
 * Step 1.  Get the web service datasource resource information:
 *          wsDatasourceDetails = wsOperationsPort.getResource(wsParentPath, null, DetailLevel.FULL)
 * 
 * Step 2.  Cast the resource to a DataSourceResource
 *          DataSourceResource ds = (DataSourceResource) wsDatasourceDetails.getResource().get(i);
 *          
 * Step 3.  Get the Attribute List
 *          List<Attribute> attributeList =  ds.getAttributes().getAttribute(); //datasource.getRelationalDataSource().getGenericAttribute();
 *          
 * Step 4.  Iterate over the Attribute List
 *          for (Attribute attr : attributeList) {
 *          
 * Step 5.  Extract the Binding Model XML
 *          String name = attr.getName();  
 *          if (name.equals("bindingModel")) {
 *          
 * Step 6.  Extract variouis variables using XPath statements:
 * 
 *  // Gets 1st iteration of targetNamespace :: http://tempuri.org/
 *  xpathInput = "/bindingModel/portBindings/portBinding[@xsi:type='soapPortBinding']/@targetNamespace";
 *  	
 *  	
 *  // Get parameterStyle for the given resourceName :: WRAPPED | BARE
 *  xpathInput = "/bindingModel/portBindings/portBinding[@xsi:type='soapPortBinding']/operationBindings/operationBinding[@resourceName='"+wsOperation+"']/inputBinding/@parameterStyle";
 *  
 *  // Get wrapped element name for the given resourceName :: {http://tempuri.org/}LookupProduct
 *  xpathInput = "/bindingModel/portBindings/portBinding[@xsi:type='soapPortBinding']/operationBindings/operationBinding[@resourceName='"+wsOperation+"']/inputBinding/wrapper/@elementName";
 *  
 *  
 *  // Execute the next section in a loop (p) to get each parameter
 *  
 *  // Extract the port binding type in order to provide a loop control.  Finished when null.
 *  //  <portBinding xsi:type="soapPortBinding"
 *  xpathInput = "/bindingModel/portBindings/portBinding[@xsi:type='soapPortBinding']/operationBindings/operationBinding[@resourceName='"+wsOperation+"']/parameterBindings/parameterBinding["+p+"]/@xsi:type";
 *   
 *  
 *  // Get the direction for a variable :: INPUT | OUTPUT
 *  //   direction="INPUT"
 *  xpathInput = "/bindingModel/portBindings/portBinding[@xsi:type='soapPortBinding']/operationBindings/operationBinding[@resourceName='"+wsOperation+"']/parameterBindings/parameterBinding["+p+"]/@direction";
 *  
 *  
 *  // Get the actual parameter name as per the resource procedure :: desiredProduct
 *  //   name="desiredProduct"
 *  xpathInput = "/bindingModel/portBindings/portBinding[@xsi:type='soapPortBinding']/operationBindings/operationBinding[@resourceName='"+wsOperation+"']/parameterBindings/parameterBinding["+p+"]/@name";
 *  
 *  
 *  // Get XML parameter name for the resource input variable :: {http://tempuri.org/}LookupProductDesiredproduct
 *  //   elementName="{http://tempuri.org/}LookupProductDesiredproduct"
 *  xpathInput = "/bindingModel/portBindings/portBinding[@xsi:type='soapPortBinding']/operationBindings/operationBinding[@resourceName='"+wsOperation+"']/parameterBindings/parameterBinding["+p+"]/@elementName";
 *  
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<bindingModel>
	<portBindings>
		<portBinding xsi:type="soapPortBinding" bindingName="{http://tempuri.org/}ProductWebServiceBinding" contractFirst="false" contractStyle="ABSTRACT" enableMTOM="false" implementationFolder="/shared/impl/ProductWebService"
		             policyName="/policy/security/system/Http-Basic-Authentication.xml" portName="ProductWebServicePort" portTypeName="{http://tempuri.org/}ProductWebServicePortType" serviceName="ProductWebService" targetNamespace="http://tempuri.org/"
		             timeout="0" bindingStyles="WSDL11_SOAP11_HTTP WSDL11_SOAP12_HTTP" enabled="true" endpointUrlPath="/ProductWebService" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<operationBindings>
				<operationBinding xsi:type="soapProcedureBinding" enableFastInfoset="false" enableMTOM="false" soapAction="" timeout="0" useInputEnvelop="false" useOutputEnvelop="false" resourceName="LookupProduct">
					<inputBinding xsi:type="soapMessageBinding" parameterStyle="BARE">
						<wrapper xsi:type="soapWrapper" messageName="{http://tempuri.org/}LookupProductInput" partName="parameters" elementName="{http://tempuri.org/}LookupProduct" elementTypeName="{http://tempuri.org/}LookupProductType"/>
					</inputBinding>
					<outputBinding xsi:type="soapMessageBinding" parameterStyle="BARE">
						<wrapper xsi:type="soapWrapper" messageName="{http://tempuri.org/}LookupProductOutput" partName="parameters" elementName="{http://tempuri.org/}LookupProductResponse" elementTypeName="{http://tempuri.org/}LookupProductResponseType"/>
					</outputBinding>
					<parameterBindings>
						<parameterBinding xsi:type="soapParameterBinding" faultName="desiredProduct" location="body" messageName="{http://tempuri.org/}LookupProductInput" partName="desiredProduct" useChildParameter="false" direction="INPUT"
						                  elementName="{http://tempuri.org/}LookupProductDesiredproduct" name="desiredProduct"/>
						<parameterBinding xsi:type="soapParameterBinding" faultName="result" location="body" messageName="{http://tempuri.org/}LookupProductOutput" partName="result" useChildParameter="false" direction="OUTPUT"
						                  elementName="{http://tempuri.org/}LookupProductResult" name="result">
							<cursorBinding cursorTypeName="{http://tempuri.org/}LookupProductResultCursorType" rowElementName="row" rowElementTypeName="{http://tempuri.org/}LookupProductResultRowType">
								<columnBindings>
									<columnBinding xsi:type="soapColumnBinding" elementName="{http://tempuri.org/}ProductName" name="ProductName"/>
									<columnBinding xsi:type="soapColumnBinding" elementName="{http://tempuri.org/}ProductID" name="ProductID"/>
									<columnBinding xsi:type="soapColumnBinding" elementName="{http://tempuri.org/}ProductDescription" name="ProductDescription"/>
								</columnBindings>
							</cursorBinding>
						</parameterBinding>
					</parameterBindings>
				</operationBinding>
				<operationBinding xsi:type="soapProcedureBinding" enableFastInfoset="false" enableMTOM="false" timeout="0" useInputEnvelop="false" useOutputEnvelop="false" resourceName="LookupProduct2">
					<inputBinding xsi:type="soapMessageBinding" parameterStyle="WRAPPED">
						<wrapper xsi:type="soapWrapper" messageName="{http://tempuri.org/}LookupProduct2Input" partName="parameters" elementName="{http://tempuri.org/}WrapperedLookupproduct2"
						         elementTypeName="{http://tempuri.org/}WrapperedLookupproduct2Type"/>
					</inputBinding>
					<outputBinding xsi:type="soapMessageBinding" parameterStyle="WRAPPED">
						<wrapper xsi:type="soapWrapper" messageName="{http://tempuri.org/}LookupProduct2Output" partName="parameters" elementName="{http://tempuri.org/}WrapperedLookupproduct2Response"
						         elementTypeName="{http://tempuri.org/}WrapperedLookupproduct2ResponseType"/>
					</outputBinding>
					<parameterBindings>
						<parameterBinding xsi:type="soapParameterBinding" faultName="desiredProduct" location="body" messageName="{http://tempuri.org/}LookupProduct2Input" partName="desiredProduct" useChildParameter="false" direction="INPUT"
						                  elementName="{http://tempuri.org/}LookupProduct2Desiredproduct" name="desiredProduct"/>
						<parameterBinding xsi:type="soapParameterBinding" faultName="debug" location="body" messageName="{http://tempuri.org/}LookupProduct2Input" partName="debug" useChildParameter="false" direction="INPUT"
						                  elementName="{http://tempuri.org/}LookupProduct2Debug" name="debug"/>
						<parameterBinding xsi:type="soapParameterBinding" faultName="result" location="body" messageName="{http://tempuri.org/}LookupProduct2Output" partName="result" useChildParameter="false" direction="OUTPUT"
						                  elementName="{http://tempuri.org/}LookupProduct2Result" name="result">
							<cursorBinding cursorTypeName="{http://tempuri.org/}LookupProduct2ResultCursorType" rowElementName="row" rowElementTypeName="{http://tempuri.org/}LookupProduct2ResultRowType">
								<columnBindings>
									<columnBinding xsi:type="soapColumnBinding" elementName="ProductName" name="ProductName"/>
									<columnBinding xsi:type="soapColumnBinding" elementName="ProductID" name="ProductID"/>
									<columnBinding xsi:type="soapColumnBinding" elementName="ProductDescription" name="ProductDescription"/>
								</columnBindings>
							</cursorBinding>
						</parameterBinding>
					</parameterBindings>
				</operationBinding>
			</operationBindings>
		</portBinding>
		<portBinding xsi:type="restfulPortBinding" jsonPackageName="ProductWebService" serviceName="ProductWebService" targetNamespace="http://tempuri.org/" bindingStyles="REST_XML REST_JSON" enabled="true" endpointUrlPath="/ProductWebService"
		             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<operationBindings>
				<operationBinding xsi:type="restfulProcedureBinding" httpMethod="GET" operationPathTemplate="/LookupProduct" resourceName="LookupProduct">
					<inputBinding xsi:type="restfulMessageBinding" parameterStyle="WRAPPED">
						<wrapper xsi:type="restfulWrapper" elementName="{http://tempuri.org/}LookupProduct" elementTypeName="{http://tempuri.org/}LookupProductType"/>
					</inputBinding>
					<outputBinding xsi:type="restfulMessageBinding" parameterStyle="WRAPPED">
						<wrapper xsi:type="restfulWrapper" elementName="{http://tempuri.org/}LookupProductResponse" elementTypeName="{http://tempuri.org/}LookupProductResponseType"/>
					</outputBinding>
					<parameterBindings>
						<parameterBinding xsi:type="restfulParameterBinding" bindingLocation="query" nulLable="true" queryName="desiredProduct" xml="false" direction="INPUT" elementName="{http://tempuri.org/}LookupProductDesiredproduct"
						                  name="desiredProduct" typeName="{http://www.w3.org/2001/XMLSchema}int"/>
						<parameterBinding xsi:type="restfulParameterBinding" bindingLocation="entity" nulLable="false" xml="false" direction="OUTPUT" elementName="{http://tempuri.org/}LookupProductResult" name="result"
						                  typeName="{http://tempuri.org/}LookupProductResultCursorType">
							<cursorBinding cursorTypeName="{http://tempuri.org/}LookupProductResultCursorType" rowElementName="row" rowElementTypeName="{http://tempuri.org/}LookupProductResultRowType">
								<columnBindings>
									<columnBinding xsi:type="restfulColumnBinding" elementName="ProductName" name="ProductName" typeName="{http://www.w3.org/2001/XMLSchema}string"/>
									<columnBinding xsi:type="restfulColumnBinding" elementName="ProductID" name="ProductID" typeName="{http://www.w3.org/2001/XMLSchema}int"/>
									<columnBinding xsi:type="restfulColumnBinding" elementName="ProductDescription" name="ProductDescription" typeName="{http://www.w3.org/2001/XMLSchema}string"/>
								</columnBindings>
							</cursorBinding>
						</parameterBinding>
					</parameterBindings>
				</operationBinding>
				<operationBinding xsi:type="restfulProcedureBinding" httpMethod="GET" operationPathTemplate="/LookupProduct2" resourceName="LookupProduct2">
					<inputBinding xsi:type="restfulMessageBinding" parameterStyle="WRAPPED">
						<wrapper xsi:type="restfulWrapper" elementName="{http://tempuri.org/}LookupProduct2" elementTypeName="{http://tempuri.org/}LookupProduct2Type"/>
					</inputBinding>
					<outputBinding xsi:type="restfulMessageBinding" parameterStyle="WRAPPED">
						<wrapper xsi:type="restfulWrapper" elementName="{http://tempuri.org/}LookupProduct2Response" elementTypeName="{http://tempuri.org/}LookupProduct2ResponseType"/>
					</outputBinding>
					<parameterBindings>
						<parameterBinding xsi:type="restfulParameterBinding" bindingLocation="query" nulLable="true" queryName="desiredProduct" xml="false" direction="INPUT" elementName="{http://tempuri.org/}LookupProduct2Desiredproduct"
						                  name="desiredProduct" typeName="{http://www.w3.org/2001/XMLSchema}int"/>
						<parameterBinding xsi:type="restfulParameterBinding" bindingLocation="query" nulLable="true" queryName="debug" xml="false" direction="INPUT" elementName="{http://tempuri.org/}LookupProduct2Debug" name="debug"
						                  typeName="{http://www.w3.org/2001/XMLSchema}string"/>
						<parameterBinding xsi:type="restfulParameterBinding" bindingLocation="entity" nulLable="false" xml="false" direction="OUTPUT" elementName="{http://tempuri.org/}LookupProduct2Result" name="result"
						                  typeName="{http://tempuri.org/}LookupProduct2ResultCursorType">
							<cursorBinding cursorTypeName="{http://tempuri.org/}LookupProduct2ResultCursorType" rowElementName="row" rowElementTypeName="{http://tempuri.org/}LookupProduct2ResultRowType">
								<columnBindings>
									<columnBinding xsi:type="restfulColumnBinding" elementName="ProductName" name="ProductName" typeName="{http://www.w3.org/2001/XMLSchema}string"/>
									<columnBinding xsi:type="restfulColumnBinding" elementName="ProductID" name="ProductID" typeName="{http://www.w3.org/2001/XMLSchema}int"/>
									<columnBinding xsi:type="restfulColumnBinding" elementName="ProductDescription" name="ProductDescription" typeName="{http://www.w3.org/2001/XMLSchema}string"/>
								</columnBindings>
							</cursorBinding>
						</parameterBinding>
					</parameterBindings>
				</operationBinding>
			</operationBindings>
			<httpSecurityModel authSchemes="BASIC" transportSecurity="ENABLED"/>
		</portBinding>
	</portBindings>
</bindingModel>
*/
}

