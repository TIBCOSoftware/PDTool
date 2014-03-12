/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao.jdbcapi;

import java.math.*;
import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.net.Authenticator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.BasicAuthenticator;
import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.jdbcapi.JdbcConnector;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.deploytool.dao.RegressionPerfTestDAO;
import com.cisco.dvbu.ps.deploytool.services.RegressionItem;
import com.cisco.dvbu.ps.deploytool.services.RegressionManagerImpl;
import com.cisco.dvbu.ps.deploytool.services.RegressionManagerUtils;
import com.cisco.dvbu.ps.deploytool.modules.RegressionTestType;

/**
 * This class is based on the original Perftest utility. Is uses most its functionality;  
 * modifications were made in exception types, parameter passing, and JDBC connection handling. 
 * 
 * @author Mike Tinius
 * @since  2012-06-05
 * @modified 
 * 	2013-02-13 (mtinius): added support for variables for all fields in RegressionModule.xml
 *  2013-05-08 (mtinius): fixed issue with catalog, schema, view, ws operation names containing spaces, special characters and reserved words by double quoting them.
 */
public class RegressionPerfTestDAOImpl implements RegressionPerfTestDAO
{
	private static Log logger = LogFactory.getLog(RegressionPerfTestDAOImpl.class);
	private static String propertyFile = RegressionManagerImpl.propertyFile;
	  
    static RegressionItem item;
    // Stats per print execution
    static AtomicInteger numStatExecs = new AtomicInteger();
    static AtomicInteger numExecs = new AtomicInteger();
    static AtomicInteger numRows = new AtomicInteger();
    static AtomicLong numLatency = new AtomicLong();
    static AtomicLong firstRowLatency = new AtomicLong(); 
    // Totals stats per query exeuction
    static int execsTotal = 0;
    static BigDecimal tpsTotal = new BigDecimal(0);
    static BigDecimal rptTotal = new BigDecimal(0);
    static BigDecimal latTotal = new BigDecimal(0);
    static BigDecimal frTotal = new BigDecimal(0);
    static BigDecimal durTotal = new BigDecimal(0);

    static long endTime;
    static String logDelim = "|"; 		// Log File delimiter character
    static String outputDelimiter = "|";// Output File delimiter
    static String padChar = " ";		// pad characters
    static String HEADER = "  HEADER";
    static String DETAIL = "  DETAIL";
    static String TOTALS = "  TOTALS";
    static String outputFile;
    static int perfTestThreads;		// The number of threads to create when doing performance testing.
    static int perfTestDuration; 	// The duration in seconds to execute the performance test for.
    static int perfTestSleepPrint;	// The number of seconds to sleep in between printing stats when executing the performance test.
    static int perfTestSleepExec; 	// The number of seconds to sleep in between query executions when executing the performance test.
    static int totalRows=0;
    static String errorMessage = null;
    static boolean errorFound = false;
	// Test Types from Regression XML schema
    static final String FUNCTIONAL = "functional";
    static final String MIGRATION = "migration";
    static final String PERFORMANCE = "performance";

    static final String QUERY = "QUERY";
    static final String PROCEDURE = "PROCEDURE";
    static final String WS = "WS";

	private static CompositeServer cisServerConfig = null;
	private static RegressionTestType regressionConfig = null;
	private static String printOutputType = "verbose";

 	/**
	 *  Map of published datasource names to established JDBC connections to CIS
	 */
	private static HashMap<String,Connection> cisConnections = null;

	@Override
	public void executePerformanceTest( CompositeServer cisServerConfig, RegressionTestType regressionConfig, List<RegressionTestType> regressionList) throws CompositeException
	{	
		// 0. Check the input parameter values:
		if (cisServerConfig == null || regressionConfig == null)
		{
			throw new CompositeException(
					"XML Configuration objects are not initialized when trying to run Regression test.");
		}
		if (this.cisServerConfig == null)	{	this.cisServerConfig = cisServerConfig;	} 
		if (this.regressionConfig == null)	{	this.regressionConfig = regressionConfig; }
			
// To do: take a look at the authenticator from the original pubtest
		Authenticator.setDefault(new BasicAuthenticator(cisServerConfig));

		// Initialize start time and format
		java.util.Date startDate = new java.util.Date();
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS");

     // 1. Initialize configuration items: 
		String prefix = "executePerformanceTest";
        // Get items from config file:
		// Get input file path
		String inputFilePath =  CommonUtils.extractVariable(prefix, regressionConfig.getInputFilePath(), propertyFile, true);
		// Test for zero length before testing for null
		if (inputFilePath != null && inputFilePath.length() == 0)
			inputFilePath = null;
		// Now test for null
		if (inputFilePath == null)
			throw new CompositeException("Input file path is not defined in the regression XML file.");
		
		// Get the test type
		String testType = CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getTestType(), propertyFile, false);

		// Get the base directory where the files should be stored
		String baseDir = null;
		if ( regressionConfig.getTestRunParams().getBaseDir() != null) {
			baseDir = CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getBaseDir().trim(), propertyFile, true);
	        if (baseDir != null && baseDir.length() > 0) {
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
		if ( regressionConfig.getTestRunParams().getDelimiter() != null) {
			outputDelimiter = RegressionManagerUtils.getDelimiter(CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getDelimiter().toString(), propertyFile, false));
		}
        
		// Get the printOutput variable
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

		// Check for performance test threads and duration
        perfTestThreads = 1;
        if (regressionConfig.getTestRunParams().getPerfTestThreads() != null)
        	perfTestThreads = regressionConfig.getTestRunParams().getPerfTestThreads();
        perfTestDuration = 60;
        if (regressionConfig.getTestRunParams().getPerfTestThreads() != null)
        	perfTestDuration = regressionConfig.getTestRunParams().getPerfTestDuration();
        perfTestSleepPrint = 5;
       if (regressionConfig.getTestRunParams().getPerfTestSleepPrint() != null) {
    	   perfTestSleepPrint = regressionConfig.getTestRunParams().getPerfTestSleepPrint();
    	   // Must be a minimum of 5 seconds otherwise too much log activity will be generated
    	   if (perfTestSleepPrint == 0)
    		   perfTestSleepPrint = 5;
       }
       perfTestSleepExec = 0;
       if (regressionConfig.getTestRunParams().getPerfTestSleepExec() != null)
    	   perfTestSleepExec = regressionConfig.getTestRunParams().getPerfTestSleepExec();
      
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
        	// Initialize the overall start time
            java.util.Date beginDate = new java.util.Date();
        	String executionStartTime = formatter.format(beginDate);
        	// Initialize the item object
        	item = items[i];
        	       	
        	/*
        	 * For Performance Test we do not write to an output file.
        	 */   		
        	outputFile = null;
        	
        	String message = null;
        	errorMessage = null;
        	errorFound = false;
    		String detailContent = null;
           	String result = "SKIPPED"; // [SKIPPED,SUCCESS,ERROR,HEADER,DETAIL,TOTALS]
        	String duration = "";
        	String database = item.database;
        	totalRows = 0;
        	String query = "";     	
        	String resourceType = "";
           	String resourceURL = "";
     	
           	// Setup Query
        	if (item.type == RegressionManagerUtils.TYPE_QUERY) {
             	resourceType = QUERY;
            	query = item.input.replaceAll("\n", "");
            	resourceURL = RegressionManagerUtils.getTableUrl(query); // Retrieve only the FROM clause table URL with no where clause and no SELECT * FROM projections
            	/*
            	 * For Performance Test we do not write to an output file.
            	 */
            	//if (baseDir != null) 
            	//	outputFile = (baseDir + "/" + database + "/" + resourceURL + ".txt").replaceAll("//", "/");
     	}
        	
        	// Setup Procedures
        	if (item.type == RegressionManagerUtils.TYPE_PROCEDURE) {
            	resourceType = PROCEDURE;
            	query = item.input.replaceAll("\n", "");
            	resourceURL = RegressionManagerUtils.getTableUrl(query); // Retrieve only the FROM clause procedure URL with no where clause and no SELECT * FROM projections and no parameters.
            	/*
            	 * For Performance Test we do not write to an output file.
            	 */
            	//if (baseDir != null) 
            	//	outputFile = (baseDir + "/" + database + "/" + resourceURL + ".txt").replaceAll("//", "/");            	
        	}
        	
        	// Setup Web Services
        	if (item.type == RegressionManagerUtils.TYPE_WS) {
            	resourceType = WS;     
            	query = (item.path+ "/" + item.action).replaceAll("//", "/");
            	resourceURL = (item.path + "/" + item.action).replaceAll("//", "/").replaceAll("/", "."); // construct ws path from the path and action combined.
            	if (resourceURL.indexOf(".") == 0)
            		resourceURL = resourceURL.substring(1);
            	/*
            	 * For Performance Test we do not write to an output file.
            	 */
            	//if (baseDir != null) 
            	//	outputFile = (baseDir + "/" + database + "/" + resourceURL + ".txt").replaceAll("//", "/");          	
         	}

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
        		RegressionManagerUtils.printOutputStr(printOutputType, "summary", "------------------------ Test "+(i+1)+" -----------------------------","Test "+(i+1)+" ... " );
                if (item.type == RegressionManagerUtils.TYPE_QUERY && runQueries && databaseMatch && resourceMatch)
                {
                	// Initialize the file
                    if (outputFile != null)
                    	CommonUtils.createFileWithContent(outputFile, "");
                    
                    // Establish a JDBC connection for this database
    	            cisConnections = RegressionManagerUtils.establishJdbcConnection(item.database, cisConnections, cisServerConfig, regressionConfig, propertyFile);  // don't need to check for null here.

    	            // Print out the line to the command line
    		        RegressionManagerUtils.printOutputStr(printOutputType, "summary", "Execute Query:  " + item.input, "");

        	        // Execute the performance test for a query
        	        detailContent = executePerformanceTestWorkers();
        	        
                    if (errorMessage == null) {
	                    result = "SUCCESS";
	                    totalSuccessTests++;
	                    totalSuccessQueries++;
                    }
                    else {
                    	result = "ERROR";
                		totalFailedQueries++;
                        totalFailedTests++;
                        logger.error(errorMessage);
                        logger.error("Item Input Details: "+item.toString());

                    }
                }
                else if (item.type == RegressionManagerUtils.TYPE_PROCEDURE && runProcs && databaseMatch && resourceMatch)
                {
                	// Initialize the file
                    if (outputFile != null)
                    	CommonUtils.createFileWithContent(outputFile, "");
                    
                    // Establish a JDBC connection for this database
    	            cisConnections = RegressionManagerUtils.establishJdbcConnection(item.database, cisConnections, cisServerConfig, regressionConfig, propertyFile);  // don't need to check for null here.

    	            // Print out the line to the command line
    		        RegressionManagerUtils.printOutputStr(printOutputType, "summary", "Execute Procedure:  " + item.input, "");

        	        // Execute the performance test for a procedure
                    detailContent = executePerformanceTestWorkers();
        	        
                    if (errorMessage == null) {
	                    result = "SUCCESS";
	                    totalSuccessTests++;
	                    totalSuccessProcs++;
                    }
                    else {
                    	result = "ERROR";
                		totalFailedProcs++;
                        totalFailedTests++;
                        logger.error(errorMessage);
                        logger.error("Item Input Details: "+item.toString());                  	
                   }
                }
                else if (item.type == RegressionManagerUtils.TYPE_WS && runWs && databaseMatch && resourceMatch) 
                {
                	// Initialize the file
                    if (outputFile != null)
                    	CommonUtils.createFileWithContent(outputFile, "");

                    // Print out the line to the command line
    		        RegressionManagerUtils.printOutputStr(printOutputType, "summary", "Execute Web Service:  " + item.path, "");
    		        RegressionManagerUtils.printOutputStr(printOutputType, "summary", item.input, "");

        	        // Execute the performance test for a web service
                    detailContent = executePerformanceTestWorkers();
        	        
                    if (errorMessage == null) {
	                    result = "SUCCESS";
	                    totalSuccessTests++;
	                    totalSuccessWS++;
                    }
                    else {
                    	result = "ERROR";
                		totalFailedWS++;
                        totalFailedTests++;
                        logger.error(errorMessage);
                        logger.error("Item Input Details: "+item.toString());                  	
                   }
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
                errorMessage = e.getMessage().replace("\n", " ").replaceAll("\r", " ");
                totalFailedTests++;
                logger.error(errorMessage);
                logger.error("Item Input Details: "+item.toString());
            }
            
            // Setup message line to be output to the log file
            if (message == null)
            	message = "";            
            if (errorMessage != null) {
            	message = "  ::ERROR: " + errorMessage + "  " + message;
            	// Don't output the detail content if no DETAIL entries exist
            	if (detailContent != null && !detailContent.contains("DETAIL"))
            		detailContent = null;
            }
            
            // Setup outputFile to be blank if it was never set in the first place which is valid.
            if (outputFile == null)
            	outputFile = "";            
            
			// Setup the detailContent rows
            if (detailContent != null) {
            	detailContent = "\n" + detailContent;
            	if (detailContent.lastIndexOf("\n") > 0) {
            		detailContent = detailContent.substring(0,detailContent.length()-1);
            	}
            } else {
            	detailContent = "";
            }
            
            // Get the final total duration
            duration = CommonUtils.getElapsedTime(beginDate);

            // Output the log entry
	        content = 
	        	CommonUtils.rpad(result, 			 8, padChar)+logDelim +
	        	CommonUtils.rpad(executionStartTime,26, padChar)+logDelim +
	        	CommonUtils.rpad(duration.trim(),	20, padChar)+logDelim +
	        	CommonUtils.rpad(""+totalRows, 		20, padChar)+logDelim +
	        	CommonUtils.rpad(database, 			30, padChar)+logDelim +
	        	CommonUtils.rpad(query, 			70, padChar)+logDelim +
	        	CommonUtils.rpad(resourceType, 		11, padChar)+logDelim +
	        	CommonUtils.rpad(outputFile,		50, padChar)+logDelim +
	        	message;
	        // content contains the overall output message
	        // detailContent contains the DETAIL messages from the performance test
	        // The log is written in a way that the overall message is displayed first followed by the content
            CommonUtils.appendContentToFile(logFilePath, content + detailContent);
            // Since the display is being printed in real-time, the detail messages come out first followed by the overall content message.
	        RegressionManagerUtils.printOutputStr(printOutputType, "summary", "\n"+content, "");
            
        } // end of process input file items loop

        // Print out timings
        String duration = CommonUtils.getElapsedTime(startDate);
        String testTypeMessage = "";
        if (PERFORMANCE.equalsIgnoreCase(testType)) 
        	testTypeMessage = "Execute a full query from the query list.";

        		 int len = 56;
				 logger.info("--------------------------------------------------------");
				 logger.info("--------- Regression Performance Test Summary ----------");
				 logger.info("--------------------------------------------------------");
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
logger.info(CommonUtils.rpad("      Performance Test duration: " + duration, len, " "));
				 logger.info("                                                        ");
				 logger.info("Review \"perftest\" Summary: " + logFilePath);
				 logger.info("--------------------------------------------------------");

		// 3. Close all connections: 
        JdbcConnector connector = new JdbcConnector();
        if (cisConnections != null)
        {
        	for(Connection nextConnection : cisConnections.values())  // getting all non-null values
        	{
        		connector.closeJdbcConnection(nextConnection);
        	}
        	cisConnections = null;
        }
    	RegressionManagerUtils.printOutputStr(printOutputType, "summary", "\nCompleted executePerformanceTest()", "");
	}
	
    private String executePerformanceTestWorkers() throws CompositeException
    {
    	try {
	        // Create all the connections
	        Worker[] workers = new Worker[perfTestThreads];
	        for (int i = 0; i < workers.length; i++) {
	            workers[i] = new Worker();
	        }
	        // Pause in between query executions
    		Thread.sleep(perfTestSleepExec*1000);
	
	        // Start the clock
	        long startTime = System.currentTimeMillis();
	        endTime = startTime + perfTestDuration * 1000;
	
	        // Start the workers
	        for (int i = 0; i < workers.length; i++) {
	            workers[i].start();
	        }
	
	        // Output the Header Rows
	        String content = CommonUtils.lpad(HEADER, 7, padChar)+logDelim+
	        	CommonUtils.rpad("Threads="+perfTestThreads, 12, padChar)+logDelim+
	        	CommonUtils.rpad("Duration (s)="+perfTestDuration, 17, padChar)+logDelim+
	        	CommonUtils.rpad("Print Sleep (s)="+perfTestSleepPrint,19, padChar)+logDelim+
	        	CommonUtils.rpad("Exec Sleep (s)="+perfTestSleepExec,18, padChar)+logDelim+
	        	logDelim+logDelim+logDelim;
	        
    		RegressionManagerUtils.printOutputStr(printOutputType, "summary", content, "");
        	StringBuffer buf = new StringBuffer();
	        buf.append(content+"\n");
	        
	        content = 
	        	CommonUtils.lpad(HEADER, 8, padChar)+logDelim+
	        	CommonUtils.rpad("Execs", 12, padChar)+logDelim+
	        	CommonUtils.rpad("Execs/sec", 12, padChar)+logDelim+
	        	CommonUtils.rpad("Rows/exec", 12, padChar)+logDelim+
	        	CommonUtils.rpad("Latency (ms)", 13, padChar)+logDelim+
	        	CommonUtils.rpad("1st row (ms)", 13, padChar)+logDelim+
	        	CommonUtils.rpad("Duration (ms)", 14, padChar)+logDelim+
	        	logDelim;
	        
    		RegressionManagerUtils.printOutputStr(printOutputType, "summary", content, "");
	        buf.append(content+"\n");
	        
	        // Initialize the totals before each query execution.
	        numStatExecs.set(0);
	        execsTotal = 0;
	        tpsTotal = new BigDecimal(0);
	        rptTotal = new BigDecimal(0);
	        latTotal = new BigDecimal(0);
	        frTotal = new BigDecimal(0);
	        durTotal = new BigDecimal(0);
	        
	        /* Safeguard: determine the number of loops by taking the "total duration" divided by the "print stat sleep interval"
	         *   This is important because sometimes the timing is off when the end time is calculated and when worker threads are 
	         *   still running.  The issue is pointed out below (-->) when there is an extra line printed that throws off the stats.
	         *   
	         *    HEADER|Threads=1   |Duration (s)=10  |Print Sleep (s)=5  |Exec Sleep (s)=0  ||||
  			 *    HEADER|Execs       |Execs/sec   |Rows/exec   |Latency (ms) |1st row (ms) |Duration (ms) ||
  			 *    DETAIL|4780        |957.91      |1.00        |1.04         |1.03         |4990.00             ||
  			 *    DETAIL|5146        |1027.96     |1.00        |0.97         |0.96         |5006.00             ||
  			 * -->DETAIL|11          |2.19        |1.00        |0.90         |0.90         |5018.00             ||
  			 *    TOTALS|3312.33     |662.68      |1.00        |0.97         |0.96         |5004.66             ||
	         */
	        int totalExecLoops = perfTestDuration / perfTestSleepPrint;
	        
	        // Print stats periodically
	        errorFound = false;
	        int loopCounter = 0;
	        while (System.currentTimeMillis() < endTime && !errorFound && loopCounter < totalExecLoops) {
	            Thread.sleep(perfTestSleepPrint*1000);
	            content = printStats(startTime);
		        if (content != null)
		        	buf.append(content);
	
	            // Reset print stat counters		        
	            numExecs.set(0);
	            numLatency.set(0);
	            firstRowLatency.set(0);
	            numRows.set(0);
	            startTime = System.currentTimeMillis();
	            loopCounter++;
	        }
	
	        // Wait for the workers to finish
	        for (int i = 0; i < workers.length; i++) {
	            workers[i].join();
	        }
	
	        // Print stats
	        /*
	         * This is @deprecated as it was determined that the last stat line was inconsistent and throwing off the numbers
	        content = printStats(startTime);
	        if (content != null)
	        	buf.append(content);
			*/
	        
	        
	        // Calculate the Total Average Stats for each run and output as a TOTALS line
	        if (numStatExecs.get() > 0) 
	        {
		        // Calculate total average executions
		        BigDecimal execAvg = new BigDecimal(execsTotal);
		        execAvg = execAvg.divide(new BigDecimal(numStatExecs.get()), 5, BigDecimal.ROUND_FLOOR);
		        execAvg = execAvg.setScale(2, BigDecimal.ROUND_DOWN);
	
		        // Calculate total average execs/sec or tps
		        BigDecimal tpsAvg = tpsTotal;
		        tpsAvg = tpsAvg.divide(new BigDecimal(numStatExecs.get()), 5, BigDecimal.ROUND_FLOOR);
		        tpsAvg = tpsAvg.setScale(2, BigDecimal.ROUND_DOWN);
	
		        // Calculate total average Rows per Execution
		        BigDecimal rptAvg = rptTotal;
		        rptAvg = rptAvg.divide(new BigDecimal(numStatExecs.get()), 5, BigDecimal.ROUND_FLOOR);
		        rptAvg = rptAvg.setScale(2, BigDecimal.ROUND_DOWN);
		        
		        // Calculate total average Latency
		        BigDecimal latAvg = latTotal;
		        latAvg = latAvg.divide(new BigDecimal(numStatExecs.get()), 5, BigDecimal.ROUND_FLOOR);
		        latAvg = latAvg.setScale(2, BigDecimal.ROUND_DOWN);
		        
		        // Calculate total average First Row Latency
		        BigDecimal frAvg = frTotal;
		        frAvg = frAvg.divide(new BigDecimal(numStatExecs.get()), 5, BigDecimal.ROUND_FLOOR);
		        frAvg = frAvg.setScale(2, BigDecimal.ROUND_DOWN);
	
		        // Calculate total average Duration
		        BigDecimal durAvg = durTotal;
		        durAvg = durAvg.divide(new BigDecimal(numStatExecs.get()), 5, BigDecimal.ROUND_FLOOR);
		        durAvg = durAvg.setScale(2, BigDecimal.ROUND_DOWN);       
		        
		        // Print out the summary Totals line
		        content = 
		        	CommonUtils.lpad(TOTALS, 8, padChar)+logDelim+
		        	CommonUtils.rpad(""+execAvg,12, padChar)+logDelim+
		        	CommonUtils.rpad(""+tpsAvg, 12, padChar)+logDelim+
		        	CommonUtils.rpad(""+rptAvg, 12, padChar)+logDelim+
		        	CommonUtils.rpad(""+latAvg, 13, padChar)+logDelim+
		        	CommonUtils.rpad(""+frAvg, 13, padChar)+logDelim+
		        	CommonUtils.rpad(""+durAvg, 20, padChar)+logDelim+
		        	logDelim;
		        
		        RegressionManagerUtils.printOutputStr(printOutputType, "summary", content, "");
		        buf.append(content+"\n");
	        }
	        
	        return buf.toString();
    	} 
        catch (Exception e) {
        	throw new ApplicationException(e);
        }
    }

    public static String printStats(long startTime) {
    	String content = null;
        long duration = System.currentTimeMillis() - startTime;
        if (numExecs.get() == 0 || startTime <= 0 || duration <= 0) {
            return content;
        }
        // Increment the number of print stats executions which is used for averaging totals
        numStatExecs.addAndGet(1);
        
        // Calculate total number of executions
        execsTotal = execsTotal + numExecs.get();
        
        // Calculate Executions per Second or TPS - transactions per second
        double tps = numExecs.get() * 1000.0 / duration;
        BigDecimal tpsBd = new BigDecimal(tps);
        tpsBd = tpsBd.setScale(2, BigDecimal.ROUND_DOWN);
        tpsTotal = tpsTotal.add(tpsBd);
        
        // Calculate Rows per Execution
        double rpt = ((double) numRows.get()) / numExecs.get();
        BigDecimal rptBd = new BigDecimal(rpt);
        rptBd = rptBd.setScale(2, BigDecimal.ROUND_DOWN);
        rptTotal = rptTotal.add(rptBd);

        // Calculate Latency
        double latency = ((double) numLatency.get()) / numExecs.get();
        BigDecimal latBd = new BigDecimal(latency);
        latBd = latBd.setScale(2, BigDecimal.ROUND_DOWN);
        latTotal = latTotal.add(latBd);
        
        // Calculate First Row Latency
        double firstRow = ((double) firstRowLatency.get()) / numExecs.get();
        BigDecimal frBd = new BigDecimal(firstRow);
        frBd = frBd.setScale(2, BigDecimal.ROUND_DOWN);
        frTotal = frTotal.add(frBd);
        
        // Calculate Duration
        BigDecimal durBd = new BigDecimal(duration);
        durBd = durBd.setScale(2, BigDecimal.ROUND_DOWN);
        durTotal = durTotal.add(durBd);
        
        content = 
        	CommonUtils.lpad(DETAIL, 8, padChar)+logDelim+
        	CommonUtils.rpad(""+numExecs.get(),12, padChar)+logDelim+
        	CommonUtils.rpad(""+tpsBd, 12, padChar)+logDelim+
        	CommonUtils.rpad(""+rptBd, 12, padChar)+logDelim+
        	CommonUtils.rpad(""+latBd, 13, padChar)+logDelim+
        	CommonUtils.rpad(""+frBd, 13, padChar)+logDelim+
        	CommonUtils.rpad(""+durBd, 20, padChar)+logDelim+
        	logDelim;
		RegressionManagerUtils.printOutputStr(printOutputType, "summary", content, "");
        content+="\n";
        /*
         * Execs       Execs/sec   Rows/exec   Latency (ms) 1st row (ms) Duration (ms)
		 *	1191        237.86      9.00        4.19         4.19        5000 
		 *	1387        276.90      9.00        3.61         3.61        5003
         */
        return content;
    }
 
    /************************* workers ****************************/
    static class Worker extends Thread {
 
        Worker() throws Exception { }

        public void run() {
        	
            long start;
            try {
                while ((start = System.currentTimeMillis()) < endTime) {
                    int rowCount = 0;

                    if (item.type == RegressionManagerUtils.TYPE_QUERY) 
                    {
                    	RegressionManagerUtils.printOutputStr(printOutputType, "debug", "DEBUG: Thread="+Worker.this.getId()+"  SQL="+item.input, "");
                    	String result = RegressionManagerUtils.executeQuery(item, cisConnections, outputFile, outputDelimiter, printOutputType);
                    	String results[] = result.split(":");
                    	if (results.length > 1) {
                    		rowCount = Integer.valueOf(results[0]);
                       		firstRowLatency.addAndGet(Long.parseLong(results[1]));              		
                    	}            		
                    }
                    else if (item.type == RegressionManagerUtils.TYPE_PROCEDURE) 
                    {
                    	String result = RegressionManagerUtils.executeProcedure(item, cisConnections, outputFile, outputDelimiter, printOutputType);
                    	String results[] = result.split(":");
                    	if (results.length > 1) {
                    		rowCount = Integer.valueOf(results[0]);
                       		firstRowLatency.addAndGet(Long.parseLong(results[1]));              		
                    	}            		

                    }              
                    else if (item.type == RegressionManagerUtils.TYPE_WS) 
                    {
                   		rowCount = RegressionManagerUtils.executeWs(item, outputFile, cisServerConfig, regressionConfig, outputDelimiter, printOutputType);                    	                      
                    }
                    else {
                    	throw new ApplicationException("The query execution type is not supported: "+item.type);
                    }

                    totalRows = rowCount;
                    numRows.addAndGet(rowCount);
                    numExecs.incrementAndGet();
                    numLatency.addAndGet(System.currentTimeMillis() - start);
                }

            } catch (Exception e) {
            	errorFound = true;
				if (e != null && e.getMessage() != null)
					errorMessage = e.getMessage().replace("\n", " ").replaceAll("\r", " ");
//debug:            	System.out.println("*************** ERROR ENCOUNTERED IN WORKER THREAD FOR TYPE:"+item.type+" *****************");
                throw new ApplicationException(e);
            }
        }       
    } // End of Worker Class

}
