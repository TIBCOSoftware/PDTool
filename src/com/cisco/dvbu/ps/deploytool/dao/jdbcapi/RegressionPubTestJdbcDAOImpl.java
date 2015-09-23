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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.net.Authenticator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.BasicAuthenticator;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.jdbcapi.JdbcConnector;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.deploytool.dao.RegressionPubTestDAO;
import com.cisco.dvbu.ps.deploytool.services.RegressionItem;
import com.cisco.dvbu.ps.deploytool.services.RegressionManagerImpl;
import com.cisco.dvbu.ps.deploytool.services.RegressionManagerUtils;
import com.cisco.dvbu.ps.deploytool.modules.RegressionTestType;

/**
 * This class is based in the original Pubtest utility. Is uses most its functionality;  
 * modifications were made in exception types, parameter passing, and JDBC connection
 * handling. 
 * 
 * @author sst
 * @modified 
 * 	2013-02-13 (mtinius): added support for variables for all fields in RegressionModule.xml
 *  2013-11-27 (mtinius): resolved resource URLs with spaces and periods.  Added better support for parsing complex FROM clauses.
 *  2014-01-09 (mtinius): changed code to replace "\n" with space " " for queries and procedures so as to support multi-line sql without the query failing during execution.
 *  							From: query = item.input.replaceAll("\n", ""); 
 *  							  To: query = item.input.replaceAll("\n", " ");
 */
public class RegressionPubTestJdbcDAOImpl implements RegressionPubTestDAO
{
	private static Log logger = LogFactory.getLog(RegressionPubTestJdbcDAOImpl.class);
	private static String propertyFile = RegressionManagerImpl.propertyFile;
	
 	/**
	 *  Map of published datasource names to established JDBC connections to CIS
	 */
	private HashMap<String,Connection> cisConnections = null;

    String logDelim = "|"; // Delimiter character

    private RegressionItem item;
	private CompositeServer cisServerConfig = null;
	private RegressionTestType regressionConfig = null;
	// Use these default queries if the <newFileParams> is not in the regression XML.
    private String publishedViewQry = "SELECT count(1) cnt FROM";
    private String publishedProcQry = "SELECT count(*) cnt FROM";

	// Test Types from Regression XML schema
    static final String FUNCTIONAL = "functional";
    static final String MIGRATION = "migration";
    static final String PERFORMANCE = "performance";

	/** 
	 * This implementation differs from the original Pubtest utility in that
	 * here we don't create a new JDBC connection for every query or procedure 
	 * or WS. Instead, we create one connection per published data source and keep
	 * these connections open for the duration of the test. When, for example, 
	 * a query is found in the input file, its datasource name is looked up in
	 * the list of connections and corresponding connection is used. That way
	 * we still execute on a connection to a specific published data source, 
	 * but we don't open and close a lot of connections all the time during the test.  
	 * 
	 * also @see com.cisco.dvbu.ps.deploytool.dao.RegressionPubTestDAO#executeAll()
	 */
//	@Override
	public void executeAll(CompositeServer cisServerConfig, RegressionTestType regressionConfig) throws CompositeException
	{
// 0. Check the input parameter values:
		if (cisServerConfig == null || regressionConfig == null)
		{
			throw new CompositeException(
					"XML Configuration objects are not initialized " +
					"when trying to run Regression test.");
		}
		if (this.cisServerConfig == null)	{	this.cisServerConfig = cisServerConfig;	} 
		if (this.regressionConfig == null)	{	this.regressionConfig = regressionConfig; }
						
// To do: take a look at the authenticator from the original pubtest
		Authenticator.setDefault(new BasicAuthenticator(cisServerConfig));

		// Initialize start time and format
        Date startDate = new Date();
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS");

// 1. Initialize configuration items: 
        String prefix = "executeAll";
        // Get items from config file:
		// Get input file path
		String inputFilePath =  CommonUtils.extractVariable(prefix, regressionConfig.getInputFilePath(), propertyFile, true);
		// Test for zero length before testing for null
		if (inputFilePath != null && inputFilePath.length() == 0)
			inputFilePath = null;
		// Now test for null
		if (inputFilePath == null)
			throw new CompositeException("Input file path is not defined in the regression XML file.");
	
		// Get the base directory where the files should be stored
		String baseDir = null;
		if ( regressionConfig.getTestRunParams().getBaseDir() != null) {
			baseDir = CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getBaseDir().trim(), propertyFile, true);
	        if (baseDir != null && baseDir.length() > 0) {
	        	baseDir = baseDir.replaceAll(Matcher.quoteReplacement("\\\\"), Matcher.quoteReplacement("/"));
	        	baseDir = baseDir.replaceAll(Matcher.quoteReplacement("\\"), Matcher.quoteReplacement("/"));
		        // Make the sub-directory for the base directory which is where the result files go for each execution
		        boolean res = CommonUtils.mkdirs(baseDir);
	        } else {
	        	baseDir = null;
	        }
		}
		
        // Get log file delimiter
		if ( regressionConfig.getTestRunParams().getLogDelimiter() != null) {
			logDelim = RegressionManagerUtils.getDelimiter(CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getLogDelimiter().toString(), propertyFile, false));
		}

        // Get output file delimiter
        String outputDelimiter = "|";
		if ( regressionConfig.getTestRunParams().getDelimiter() != null) {
			outputDelimiter = RegressionManagerUtils.getDelimiter(CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getDelimiter().toString(), propertyFile, false));
		}
        
		// Get the printOutput variable
		String printOutputType = "verbose";
		if (regressionConfig.getTestRunParams().getPrintOutput() != null)
			printOutputType = CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getPrintOutput(), propertyFile, false);
		
        // Get the regression log location
    	String logFilePath = CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getLogFilePath(), propertyFile, true); 
        if (logFilePath == null || logFilePath.length() == 0) {
			throw new CompositeException("The log file path testRunParams.logFilePath may not be null or empty in the regression XML file.");
        }
        String logAppend = CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getLogAppend(), propertyFile, false); 
        if (logAppend == null || logAppend.length() == 0) {
			throw new CompositeException("The log file append testRunParams.logAppend may not be null or empty in the regression XML file.");
        }
		
        String testType = CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getTestType(), propertyFile, false);
        if (FUNCTIONAL.equalsIgnoreCase(testType)) 
        {
        	if (regressionConfig.getNewFileParams() != null) {
        		if (regressionConfig.getNewFileParams().getPublishedViewQry() != null && regressionConfig.getNewFileParams().getPublishedViewQry().length() > 0) {
        			publishedViewQry = CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getPublishedViewQry(), propertyFile, false);
        		}
        		if (regressionConfig.getNewFileParams().getPublishedProcQry() != null && regressionConfig.getNewFileParams().getPublishedProcQry().length() > 0) {
        			publishedProcQry = CommonUtils.extractVariable(prefix, regressionConfig.getNewFileParams().getPublishedProcQry(), propertyFile, false);
        		}
        	}
        	
        }
        
/* Construct the log header content
 * 
 * Result  |ExecutionStartTime        |Duration            |Rows                |Database                      |Query                                                                 |Type       |OutputFile                                        |Message
 * SKIPPED |2012-06-08 10:28:28.0630  |000 00:00:00.0000   |0                   |MYTEST                        |SELECT * FROM CAT1.SCH2.ViewSales WHERE ProductName like 'Mega%'      |QUERY      |C:/tmp/cis51/MYTEST/CAT1.SCH2.ViewSales.txt       |
 * SUCCESS |2012-06-08 10:28:28.0692  |000 00:00:00.0016   |1                   |MYTEST                        |SELECT * FROM getProductName(1)                                       |PROCEDURE  |C:/tmp/cis51/MYTEST/getProductName.txt            |
 * ERROR   |2012-05-09 01:00:00.000   |000 00:00:01.000    |0                   |MYDB                          |select count(*) from cat1.sch1.P1                                     |PROCEDURE  |C:/tmp/51/cat1.sch1.P1                            |<failure message if exists>
 */
        String padChar = " "; // pad characters
        String content = 
	       	CommonUtils.rpad("Result", 				 8, padChar)+logDelim +
	    	CommonUtils.rpad("ExecutionStartTime", 	26, padChar)+logDelim +
	    	CommonUtils.rpad("Duration", 			20, padChar)+logDelim +
	    	CommonUtils.rpad("Rows", 				20, padChar)+logDelim +
	    	CommonUtils.rpad("Database", 			30, padChar)+logDelim +
	    	CommonUtils.rpad("Query", 				70, padChar)+logDelim +
	    	CommonUtils.rpad("Type", 				11, padChar)+logDelim +
	    	CommonUtils.rpad("OutputFile",			50, padChar)+logDelim +
	    	"Message"+"\r\n";

        // Write out the header log entry -- if it does not exist the sub-directories will automatically be created.
        if (RegressionManagerUtils.checkBooleanConfigParam(logAppend)) {
	        	CommonUtils.appendContentToFile(logFilePath, content);
         } else {
        	// create a new file
        	CommonUtils.createFileWithContent(logFilePath, content);
        }

		// Check to see what should be executed
		boolean runQueries = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getRunQueries(), propertyFile, false));
		boolean runProcs = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getRunProcedures(), propertyFile, false));
		boolean runWs = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getRunWS(), propertyFile, false));
		boolean useAllDatasources = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getUseAllDatasources(), propertyFile, false));

		// Get the list of items from the input file
        RegressionItem[] items = RegressionManagerUtils.parseItems(inputFilePath);
		
        // Initialize counters
        //   Initialize Success Counters
		int totalSuccessTests = 0;
		int totalSuccessQueries = 0;
		int totalSuccessProcs = 0;
		int totalSuccessWS = 0;
		//   Initialize Skipped Counters
		int totalSkippedTests = 0;
		int totalSkippedQueries = 0;
		int totalSkippedProcs = 0;
		int totalSkippedWS = 0;
		//   Initialize Error Counters
		int totalFailedTests = 0;
		int totalFailedQueries = 0;
		int totalFailedProcs = 0;
		int totalFailedWS = 0;

// 2. Execute items: 
        // Execute each item from the input file
        for (int i=0; i<items.length; i++)
        {
        	// Initialize the item object
        	item = items[i];

        	String outputFile = null;
           	String result = "SKIPPED"; // [SKIPPED,SUCCESS,ERROR]
        	String duration = "";
        	String database = item.database;
        	String outputFilename = item.outputFilename;
        	int rows = 0;
        	String query = "";     	
        	String resourceType = "";
           	String resourceURL = "";
        	// Execute Queries
        	if (item.type == RegressionManagerUtils.TYPE_QUERY) {
            	resourceType = "QUERY";
            	
            	query = item.input.replaceAll("\n", " ");
            	
            	// Retrieve only the FROM clause table URL with no where clause and no SELECT * FROM projections
            	resourceURL = RegressionManagerUtils.getTableUrl(query);
            	if (outputFilename == null || outputFilename.length() == 0)
            		outputFilename = resourceURL.replaceAll("\"", "") + ".txt";

            	// Only use the this for functional testing
            	if (FUNCTIONAL.equalsIgnoreCase(testType)) 
            	{
            		query = publishedViewQry + " " + resourceURL;
            	}
        		item.input = query;
        		            	
            	// Derive the base directory for the output file (remove any double quotes from the file name).
            	if (baseDir != null) 
            		outputFile = (baseDir + "/" + database.replaceAll("\"", "") + "/" + outputFilename).replaceAll("//", "/");
        	}
        	// Execute Procedures
        	if (item.type == RegressionManagerUtils.TYPE_PROCEDURE) {
            	resourceType = "PROCEDURE";

            	query = item.input.replaceAll("\n", " ");

            	// Retrieve only the FROM clause procedure URL with no where clause and no SELECT * FROM projections and no parameters.
            	resourceURL = RegressionManagerUtils.getTableUrl(query); 
            	if (outputFilename == null)
            		outputFilename = resourceURL.replaceAll("\"", "") + ".txt";
          	
            	// Only use the this for functional testing
            	if (FUNCTIONAL.equalsIgnoreCase(testType)) 
            	{
            		query = publishedProcQry + " " + RegressionManagerUtils.getProcedure(query);
            	}
            	item.input = query;
            	
            	// Derive the base directory for the output file (remove any double quotes from the file name).
            	if (baseDir != null) 
            		outputFile = (baseDir + "/" + database.replaceAll("\"", "") + "/" + outputFilename).replaceAll("//", "/");
        	}
        	// Execute Web Services
        	if (item.type == RegressionManagerUtils.TYPE_WS) {
            	resourceType = "WS";     
            	query = (item.path+ "/" + item.action).replaceAll("//", "/");
            	resourceURL = (item.path + "/" + item.action).replaceAll("//", "/").replaceAll("/", "."); // construct ws path from the path and action combined.
            	if (resourceURL.indexOf(".") == 0)
            		resourceURL = resourceURL.substring(1);
               	if (outputFilename == null)
            		outputFilename = resourceURL.replaceAll("\"", "") + ".txt";

               	// Derive the base directory for the output file (remove any double quotes from the file name).
            	if (baseDir != null) 
            		outputFile = (baseDir + "/" + database.replaceAll("\"", "") + "/" + outputFilename).replaceAll("//", "/");
        	}
        	
        	String message = "";
            Date beginDate = new Date();
        	String executionStartTime = formatter.format(beginDate);

        	/*
        	 *  If testRunParams.useAllDatasources is set to true in then use all datasource queries in the input file 
        	 *  otherwise determine if the database in the input file is in the testRunParams.datasource list in the XML file
        	 *  and process it if it is.
        	 *  
        	 *  See if database exists in this list then process if it is.
        	 * 			<datasources>
			 *				<dsName>MYTEST</dsName>
			 *				<dsName>testWebService00</dsName>
			 *			</datasources>	
        	 */
        	boolean databaseMatch = true;
        	if (!useAllDatasources) 
        		databaseMatch = RegressionManagerUtils.findDatabaseMatch(database, regressionConfig.getTestRunParams().getDatasources(), propertyFile);

			/* Determine if the specific resource should be compared by checking the XML resource list.
			 * If the resourceURL pattern matches what is in this list then process it.
			 * 		<resources>
			 *			<resource>TEST1.*</resource>
			 *			<resource>TEST1.SCH.*</resource>
			 *			<resource>TEST1.SCH.VIEW1</resource>
			 *		</resources>
			 */
        	boolean resourceMatch = RegressionManagerUtils.findResourceMatch(resourceURL, regressionConfig.getTestRunParams().getResources(), propertyFile);

        	try
            {                
            	RegressionManagerUtils.printOutputStr(printOutputType, "summary", "------------------------ Test "+(i+1)+" -----------------------------","Test "+(i+1)+" ... ");
                if (item.type == RegressionManagerUtils.TYPE_QUERY && runQueries && databaseMatch && resourceMatch)
                {
                	// Initialize the file
                    if (outputFile != null)
                    	CommonUtils.createFileWithContent(outputFile, "");
                    
                    // Establish a JDBC connection for this database
    	            cisConnections = RegressionManagerUtils.establishJdbcConnection(item.database, cisConnections, cisServerConfig, regressionConfig, propertyFile);  // don't need to check for null here.

    	            // Print out the line to the command line
    		        RegressionManagerUtils.printOutputStr(printOutputType, "summary", "Execute Query:  " + item.input + "\n", "");

                	String r = RegressionManagerUtils.executeQuery(item, cisConnections, outputFile, outputDelimiter, printOutputType);
                	String results[] = r.split(":");
                	if (results.length > 1) {
                		rows = Integer.valueOf(results[0]);
                	}            		

                    result = "SUCCESS";
                    totalSuccessTests++;
                    totalSuccessQueries++;
                }
                else if (item.type == RegressionManagerUtils.TYPE_PROCEDURE && runProcs && databaseMatch && resourceMatch)
                {
                	// Initialize the file
                    if (outputFile != null)
                    	CommonUtils.createFileWithContent(outputFile, "");

                    // Establish a JDBC connection for this database
    	            cisConnections = RegressionManagerUtils.establishJdbcConnection(item.database, cisConnections, cisServerConfig, regressionConfig, propertyFile);  // don't need to check for null here.

    	            // Print out the line to the command line
    		        RegressionManagerUtils.printOutputStr(printOutputType, "summary", "Execute Procedure:  " + item.input + "\n", "");

                    // Execute the procedure
                	String r = RegressionManagerUtils.executeProcedure(item, cisConnections, outputFile, outputDelimiter, printOutputType);
                	String results[] = r.split(":");
                	if (results.length > 1) {
                		rows = Integer.valueOf(results[0]);
                	}            		

                	result = "SUCCESS";
                    totalSuccessTests++;
                    totalSuccessProcs++;
                }
                else if (item.type == RegressionManagerUtils.TYPE_WS && runWs && databaseMatch && resourceMatch) 
                {
                	// Initialize the file
                    if (outputFile != null)
                    	CommonUtils.createFileWithContent(outputFile, "");
                    
                    // Print out the line to the command line
    		        RegressionManagerUtils.printOutputStr(printOutputType, "summary", "Execute Web Service:  " + item.path, "");
    		        RegressionManagerUtils.printOutputStr(printOutputType, "summary", item.input, "");

                    // Execute the web service
                	rows = RegressionManagerUtils.executeWs(item, outputFile, cisServerConfig, regressionConfig, outputDelimiter, printOutputType);          		
                    
                    result = "SUCCESS";
                    totalSuccessTests++;
                    totalSuccessWS++;
                }
                else {
                	// Skip this test
                	if (item.type == RegressionManagerUtils.TYPE_WS) 
                	{
                		totalSkippedWS++;
                		message = "  ::Reason: type="+resourceType+"  runWs="+runWs+"  databaseMatch="+databaseMatch+"  resourceMatch="+resourceMatch;
                		RegressionManagerUtils.printOutputStr(printOutputType, "summary", "Test Skipped: "+ resourceURL + message + "\n", "");
                	}
                	else if (item.type == RegressionManagerUtils.TYPE_QUERY) 
                	{
                		totalSkippedQueries++;
                		message = "  ::Reason: type="+resourceType+"  runQueries="+runQueries+"  databaseMatch="+databaseMatch+"  resourceMatch="+resourceMatch;
                		RegressionManagerUtils.printOutputStr(printOutputType, "summary", "Test Skipped: "+ query + message + "\n", "");
                	}
                	else 
                	{
                		totalSkippedProcs++;
                		message = "  ::Reason: type="+resourceType+"  runProcedures="+runProcs+"  databaseMatch="+databaseMatch+"  resourceMatch="+resourceMatch;
                		RegressionManagerUtils.printOutputStr(printOutputType, "summary", "Test Skipped: "+ query + message + "\n", "");               		
                	}
                	totalSkippedTests++;
                }          
        	}
            catch (Exception e)
            {
                result = "ERROR";
            	if (item.type == RegressionManagerUtils.TYPE_WS) 
            	{
            		totalFailedWS++;
            	}
            	else if (item.type == RegressionManagerUtils.TYPE_QUERY) 
            	{
            		totalFailedQueries++;
            	}
            	else 
            	{
            		totalFailedProcs++;
            	}
                totalFailedTests++;
                message = e.getMessage().replace("\n", " ").replaceAll("\r", " ");
                logger.error(message);
                logger.error("Item Input Details: "+item.toString());
            }
            
            // Setup content line to be output to the log file
            if (outputFile == null)
            	outputFile = "";            
            duration = CommonUtils.getElapsedTime(beginDate);
            
            // Output the log entry
	        content = 
	        	CommonUtils.rpad(result, 			 8, padChar)+logDelim +
	        	CommonUtils.rpad(executionStartTime,26, padChar)+logDelim +
	        	CommonUtils.rpad(duration.trim(),	20, padChar)+logDelim +
	        	CommonUtils.rpad(""+rows,	 		20, padChar)+logDelim +
	        	CommonUtils.rpad(database, 			30, padChar)+logDelim +
	        	CommonUtils.rpad(query, 			70, padChar)+logDelim +
	        	CommonUtils.rpad(resourceType, 		11, padChar)+logDelim +
	        	CommonUtils.rpad(outputFile,		50, padChar)+logDelim +
	        	message;

	        	CommonUtils.appendContentToFile(logFilePath, content);
            
        } // end of process input file items loop

        // Print out timings
        String duration = CommonUtils.getElapsedTime(startDate);

        String testTypeMessage = "";
        if (FUNCTIONAL.equalsIgnoreCase(testType)) 
        	testTypeMessage = "Execute a default query: SELECT COUNT(1) FROM...";
        if (MIGRATION.equalsIgnoreCase(testType))
        	testTypeMessage = "Execute a full query from the query list.";
        
        		 int len = 56;
				 logger.info("--------------------------------------------------------");
				 logger.info("---------- Regression Published Test Summary -----------");
				 logger.info("--------------------------------------------------------");
logger.info(CommonUtils.rpad("Test Type: " + testType, len, " "));
logger.info(CommonUtils.rpad("  " + testTypeMessage, len, " "));
				 logger.info("                                                        ");
logger.info(CommonUtils.rpad("Total Successful        Queries: " + totalSuccessQueries, len, " "));
logger.info(CommonUtils.rpad("Total Successful     Procedures: " + totalSuccessProcs, len, " "));
logger.info(CommonUtils.rpad("Total Successful   Web Services: " + totalSuccessWS, len, " "));
				 logger.info("                                 ---------              ");
logger.info(CommonUtils.rpad("Total Successful -------> Tests: " + totalSuccessTests, len, " "));
				 logger.info("                                                        ");
logger.info(CommonUtils.rpad("Total Skipped           Queries: " + totalSkippedQueries, len, " "));
logger.info(CommonUtils.rpad("Total Skipped        Procedures: " + totalSkippedProcs, len, " "));
logger.info(CommonUtils.rpad("Total Skipped      Web Services: " + totalSkippedWS, len, " "));
				 logger.info("                                 ---------              ");
logger.info(CommonUtils.rpad("Total Skipped ----------> Tests: " + totalSkippedTests, len, " "));
				 logger.info("                                                        ");
logger.info(CommonUtils.rpad("Total Failed            Queries: " + totalFailedQueries, len, " "));
logger.info(CommonUtils.rpad("Total Failed         Procedures: " + totalFailedProcs, len, " "));
logger.info(CommonUtils.rpad("Total Failed       Web Services: " + totalFailedWS, len, " "));
				 logger.info("                                 ---------              ");
logger.info(CommonUtils.rpad("Total Failed -----------> Tests: " + totalFailedTests, len, " "));
				 logger.info("                                                        ");
logger.info(CommonUtils.rpad("Total Combined ---------> Tests: " + (totalSuccessTests+totalSkippedTests+totalFailedTests), len, " "));
				 logger.info("                                                        ");
logger.info(CommonUtils.rpad("        Published Test duration: " + duration, len, " "));
				 logger.info("                                                        ");
				 logger.info("Review \"pubtest\" Summary: " + logFilePath);
				 logger.info("--------------------------------------------------------");

		String moduleActionMessage = "MODULE_INFO: Regression Summary: Successful="+totalSuccessTests+" Skipped="+totalSkippedTests+" Failed="+totalFailedTests;
		System.setProperty("MODULE_ACTION_MESSAGE", moduleActionMessage);
				 
// 3. Close all connections: 
        JdbcConnector connector = new JdbcConnector();
        if (this.cisConnections != null)
        {
        	for(Connection nextConnection : this.cisConnections.values())  // getting all non-null values
        	{
        		connector.closeJdbcConnection(nextConnection);
        	}
        	this.cisConnections = null;
        }
    	RegressionManagerUtils.printOutputStr(printOutputType, "summary", "\nCompleted ExecuteAll()", "");
	} // end method
 
}
