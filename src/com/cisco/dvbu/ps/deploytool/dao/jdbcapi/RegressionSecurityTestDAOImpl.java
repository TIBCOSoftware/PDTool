/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao.jdbcapi;

import java.sql.Connection;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.net.Authenticator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.BasicAuthenticator;
import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.jdbcapi.JdbcConnector;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.deploytool.dao.RegressionSecurityTestDAO;
import com.cisco.dvbu.ps.deploytool.services.RegressionItem;
import com.cisco.dvbu.ps.deploytool.services.RegressionManagerImpl;
import com.cisco.dvbu.ps.deploytool.services.RegressionManagerUtils;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityPlanTestType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityQueriesType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityQueryType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityPlansType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityPlanType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityUserType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityUsersType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionTestType;

/**
 * This class is used to execute a security test
 * 
 * @author mtinius
 * @modified 
 * 	2014-02-06 (mtinius): new
 */
public class RegressionSecurityTestDAOImpl implements RegressionSecurityTestDAO
{
	private static Log logger = LogFactory.getLog(RegressionSecurityTestDAOImpl.class);
	private static String propertyFile = RegressionManagerImpl.propertyFile;
	
 	/**
	 *  Map of published datasource names to established JDBC connections to CIS
	 */
	private HashMap<String,Connection> cisConnections = null;

    String logDelim = "|"; // Delimiter character

    private RegressionItem item;
	private CompositeServer cisServerConfig = null;
	private RegressionTestType regressionConfig = null;

	// Test Types from Regression XML schema
    static final String SECURITY = "security";

	/** 
	 * 
	 * 
	 * also @see com.cisco.dvbu.ps.deploytool.dao.RegressionSecurityTestDAO#executeSecurityTest()
	 */
//	@Override
	public void executeSecurityTest(CompositeServer cisServerConfig, RegressionTestType regressionConfig, RegressionSecurityType regressionSecurity, String pathToRegressionXML) throws CompositeException
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
        String prefix = "executeSecurityTest";
	
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
		boolean logAppendBool = RegressionManagerUtils.checkBooleanConfigParam(logAppend);
			
        String testType = CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getTestType(), propertyFile, false);
               
		// Check to see what should be executed
		boolean runQueries = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getRunQueries(), propertyFile, false));
		boolean runProcs = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getRunProcedures(), propertyFile, false));
		boolean runWs = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getRunWS(), propertyFile, false));
		boolean useAllDatasources = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getUseAllDatasources(), propertyFile, false));

		// Initialize variables
        String securityPlanIds = null;
		boolean securityOverallRatingException = false;
		boolean securityExecutionErrorException = false;
		
		// Get the optional securityExecution XML
		if (regressionConfig.getTestRunParams().getSecurityExecution() != null) 
        {
   			// Get the comma separated list of securityPlanIds
	       	if (regressionConfig.getTestRunParams().getSecurityExecution().getSecurityPlanIds() != null && regressionConfig.getTestRunParams().getSecurityExecution().getSecurityPlanIds().length() > 0) {
	       		securityPlanIds = CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getSecurityExecution().getSecurityPlanIds(), propertyFile, false);
			} else {
				throw new CompositeException("The Regression Seucurity Plan Ids are missing or blank for the regression config id="+regressionConfig.getId()+".  Please update the RegressionModule.xml with a list of <testRunParams><securityPlanIds> located at "+pathToRegressionXML);	    				
			}
	
	       	// Get the attribute that determines whether to throw an exception when the overall security rating=FAIL
	       	if (regressionConfig.getTestRunParams().getSecurityExecution().getSecurityOverallRatingException() != null && regressionConfig.getTestRunParams().getSecurityExecution().getSecurityOverallRatingException().length() > 0) {
	       		securityOverallRatingException = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getSecurityExecution().getSecurityOverallRatingException(), propertyFile, false));
	        	}
	       	// Get the attribute that determines whether to throw an exception there are execution errors
	       	if (regressionConfig.getTestRunParams().getSecurityExecution().getSecurityExecutionErrorException() != null && regressionConfig.getTestRunParams().getSecurityExecution().getSecurityExecutionErrorException().length() > 0) {
	       		securityExecutionErrorException = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionConfig.getTestRunParams().getSecurityExecution().getSecurityExecutionErrorException(), propertyFile, false));
	       	}
        }
        
       	if (SECURITY.equalsIgnoreCase(testType) && securityPlanIds != null) 
		{
       		List<RegressionSecurityPlanType> regressionSecurityPlanList = null;
    		if (regressionSecurity.getRegressionSecurityPlans() != null) {
    			RegressionSecurityPlansType regressionSecurityPlans = regressionSecurity.getRegressionSecurityPlans();
    			
    			if (regressionSecurityPlans.getRegressionSecurityPlan() != null && !regressionSecurityPlans.getRegressionSecurityPlan().isEmpty()) {
    				regressionSecurityPlanList = regressionSecurityPlans.getRegressionSecurityPlan();
    			}
    		}
 
    		if (regressionSecurityPlanList != null && regressionSecurityPlanList.size() > 0) {
    			
    			// Loop over the list of regression compare ids and perform the comparison between files based on the target data source resources
    			for (RegressionSecurityPlanType regressionSecurityPlan : regressionSecurityPlanList) {

    				// Initialize the overall security rating [PASS|FAIL]
	    			String overallSecurityRating = null;
	    			String securityPlanId = null;
	    			if (regressionSecurityPlan.getId() != null && regressionSecurityPlan.getId().length() > 0) {
	    				securityPlanId = CommonUtils.extractVariable(prefix, regressionSecurityPlan.getId(), propertyFile, false);
	    			} else {
	    				throw new CompositeException("The Regression Seucurity Plan Id is missing or blank.  Please update the RegressionModule.xml with a list of <regressionSecurityPlan><id> located at "+pathToRegressionXML);	    				
	    			}

			        // Initialize counters
					int i = 0;
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
					//   Initialize Failed Counters
					int totalFailedTests = 0;
					int totalFailedQueries = 0;
					int totalFailedProcs = 0;
					int totalFailedWS = 0;
					//   Initialize Error Counters
					int totalErrorTests = 0;
					int totalErrorQueries = 0;
					int totalErrorProcs = 0;
					int totalErrorWS = 0;

					/**
					 * Possible values for regression 
					 * 1. csv string like test1,test2 (we process only resource names which are passed in)
					 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
					 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
					 * 	  like -test1,test2 (we ignore passed in resources and process rest of the in the input xml
					 */
					 if(DeployUtil.canProcessResource(securityPlanIds, securityPlanId)){
						 
    					if(logger.isInfoEnabled()){
    						logger.info("-----------------------------------------------------------------------");
    						logger.info("Processing action \"executeSecurityTest\" for security plan id: "+securityPlanId);
    						logger.info("-----------------------------------------------------------------------");
    					}
   					   		
    					/* Construct the log header content
    					 * 
    					 * Result  |ExecutionStartTime        |Duration            |Rows                |Database                      |Query                                                                 |Type       |OutputFile                                        |Message
    					 * SKIPPED |2012-06-08 10:28:28.0630  |000 00:00:00.0000   |0                   |MYTEST                        |SELECT * FROM CAT1.SCH2.ViewSales WHERE ProductName like 'Mega%'      |QUERY      |C:/tmp/cis51/MYTEST/CAT1.SCH2.ViewSales.txt       |
    					 * PASS    |2012-06-08 10:28:28.0692  |000 00:00:00.0016   |1                   |MYTEST                        |SELECT * FROM getProductName(1)                                       |PROCEDURE  |C:/tmp/cis51/MYTEST/getProductName.txt            |
    					 * ERROR   |2012-05-09 01:00:00.000   |000 00:00:01.000    |0                   |MYDB                          |select count(*) from cat1.sch1.P1                                     |PROCEDURE  |C:/tmp/51/cat1.sch1.P1                            |<failure message if exists>
    					 */
    					String padChar = " "; // pad characters
    					String content = "# Processing action \"executeSecurityTest\" for security plan id: "+securityPlanId+"\r\n";
    					content = content +    						 
    								CommonUtils.rpad("Result", 				 8, padChar)+logDelim +
				       				CommonUtils.rpad("Actual",	 			 12,padChar)+logDelim +
    						       	CommonUtils.rpad("Expected", 			 8, padChar)+logDelim +
    						       	CommonUtils.rpad("Username", 			30, padChar)+logDelim +
    						    	CommonUtils.rpad("ExecutionStartTime", 	26, padChar)+logDelim +
    						    	CommonUtils.rpad("Duration", 			20, padChar)+logDelim +
    						    	CommonUtils.rpad("Rows", 				20, padChar)+logDelim +
    						    	CommonUtils.rpad("Database", 			30, padChar)+logDelim +
    						    	CommonUtils.rpad("Query", 				70, padChar)+logDelim +
    						    	CommonUtils.rpad("Type", 				11, padChar)+logDelim +
    						    	CommonUtils.rpad("OutputFile",			50, padChar)+logDelim +
    						    	"Message";
    					
    					// Write out the header log entry -- if it does not exist the sub-directories will automatically be created.
    					if (logAppendBool) {
    					   	CommonUtils.appendContentToFile(logFilePath, content);
    					} else {
    					   	// create a new file
    					   	CommonUtils.createFileWithContent(logFilePath, content+"\r\n");
    					}
    					 
    					// Get the list of security users
     					RegressionSecurityUsersType regressionSecurityUsers = null;
    					if (regressionSecurity.getRegressionSecurityUsers() != null) {
    						regressionSecurityUsers = regressionSecurity.getRegressionSecurityUsers();
    					}
    		       		List<RegressionSecurityUserType> regressionSecurityUserList = null;
    					if (regressionSecurityUsers != null && regressionSecurityUsers.getRegressionSecurityUser() != null && regressionSecurityUsers.getRegressionSecurityUser().size() > 0) {
    						regressionSecurityUserList = regressionSecurityUsers.getRegressionSecurityUser();
    					} else {
    						throw new CompositeException("There must be Regression Seucurity Users defined in order to execute a security test for security plan id="+regressionSecurityPlan.getId()+"  Please update the RegressionModule.xml with a list of <regressionSecurityUsers> located at "+pathToRegressionXML);
    					}
    					
    					// Get the list of security queries
    					RegressionSecurityQueriesType regressionSecurityQueries = null;
    					if (regressionSecurity.getRegressionSecurityQueries() != null) {
    						regressionSecurityQueries = regressionSecurity.getRegressionSecurityQueries();
    					}
    					List<RegressionSecurityQueryType> regressionSecurityQueryList = null;
    					if (regressionSecurityQueries != null && regressionSecurityQueries.getRegressionSecurityQuery() != null && regressionSecurityQueries.getRegressionSecurityQuery().size() > 0) {
    						regressionSecurityQueryList = regressionSecurityQueries.getRegressionSecurityQuery();
    					} else {
    						throw new CompositeException("There must be Regression Seucurity Queries defined in order to execute a security test.  Please update the RegressionModule.xml with a list of <regressionSecurityQueries> located at "+pathToRegressionXML);
    					}

    	   				// Get the list of security plan tests
    		       		List<RegressionSecurityPlanTestType> regressionSecurityPlanTestList = null;
    					if (regressionSecurityPlan.getRegressionSecurityPlanTest() != null && regressionSecurityPlan.getRegressionSecurityPlanTest().size() > 0) {
    						regressionSecurityPlanTestList = regressionSecurityPlan.getRegressionSecurityPlanTest();
    					} else {
    						throw new CompositeException("There must be Regression Seucurity Test Plan defined in order to execute a security test.  Please update the RegressionModule.xml with a list of <regressionSecurityTests> located at "+pathToRegressionXML);
    					}

    	    			// Loop over the list of regression security tests
    	    			for (RegressionSecurityPlanTestType regressionSecurityPlanTest : regressionSecurityPlanTestList) 
    	    			{
    	    				String sptid = null;
    	    				if (regressionSecurityPlanTest.getId() != null) {
    	    					sptid = CommonUtils.extractVariable(prefix, regressionSecurityPlanTest.getId(), propertyFile, false);
    	    				}
    	    				String userId = null;
    	    				if (regressionSecurityPlanTest.getUserId() != null) {
    	    					userId = CommonUtils.extractVariable(prefix, regressionSecurityPlanTest.getUserId(), propertyFile, false);
    	    				} else {
        						throw new CompositeException("There must be Regression Seucurity Test Plan \"userId\" defined in order to execute a security test for security plan id="+sptid+"  Please update the RegressionModule.xml with a list of <regressionSecurityTest><userId> located at "+pathToRegressionXML);
    	    				}
    	    				String queryId = null;
    	    				if (regressionSecurityPlanTest.getQueryId() != null) {
    	    					queryId = CommonUtils.extractVariable(prefix, regressionSecurityPlanTest.getQueryId(), propertyFile, false);
    	    				} else {
        						throw new CompositeException("There must be Regression Seucurity Test Plan \"queryId\" defined in order to execute a security test for security plan id="+sptid+"  Please update the RegressionModule.xml with a list of <regressionSecurityTest><queryId> located at "+pathToRegressionXML);
    	    				}
    	    				String expectedOutcome = null;
    	    				if (regressionSecurityPlanTest.getExpectedOutcome() != null) {
    	    					expectedOutcome = CommonUtils.extractVariable(prefix, regressionSecurityPlanTest.getExpectedOutcome(), propertyFile, false);
    	    				} else {
        						throw new CompositeException("There must be Regression Seucurity Test Plan \"expcectedOutcome\" defined in order to execute a security test for security plan id="+sptid+"  Please update the RegressionModule.xml with a list of <regressionSecurityTest><expectedOutcome> located at "+pathToRegressionXML);
    	    				}
    	    				
    	    				// Get the user profile for the passed in userId
    	    				RegressionSecurityUserType regressionSecurityUser = getSecurityUser(userId, regressionSecurityUserList);
    	    				if (regressionSecurityUser != null) {
	    	    				// Establish the new credentials for the current security user profile
    	    					if (regressionSecurityUser.getUserName() != null && regressionSecurityUser.getUserName().length() > 0) {
    	    						cisServerConfig.setUser(CommonUtils.extractVariable(prefix, regressionSecurityUser.getUserName(), propertyFile, false));
    	    					} else {
    	    						throw new CompositeException("The Security User, User Name is missing or blank for security plan id="+sptid+" and userId="+userId+".  Please update the RegressionModule.xml with a list of <regressionSecurityUser><userName> located at "+pathToRegressionXML);
    	    					}
    	    					if (regressionSecurityUser.getEncryptedPassword() != null && regressionSecurityUser.getEncryptedPassword().length() > 0) {
    	    						cisServerConfig.setPassword(CommonUtils.extractVariable(prefix, regressionSecurityUser.getEncryptedPassword(), propertyFile, false));
    	    					} else {
    	    						throw new CompositeException("The Security User, Encrypted Password is missing or blank for security plan id="+sptid+" and userId="+userId+".  Please update the RegressionModule.xml with a list of <regressionSecurityUser><encryptedPassword> located at "+pathToRegressionXML);
    	    					}
    	    					if (regressionSecurityUser.getDomain() != null && regressionSecurityUser.getDomain().length() > 0) {
    	    						cisServerConfig.setDomain(CommonUtils.extractVariable(prefix, regressionSecurityUser.getDomain(), propertyFile, false));
    	    					} else {
    	    						throw new CompositeException("The Security User, domain is missing or blank for security plan id="+sptid+" and userId="+userId+".  Please update the RegressionModule.xml with a list of <regressionSecurityUser><domain> located at "+pathToRegressionXML);
    	    					}
    	    				} else {
        						throw new CompositeException("The Security User profile is missing or blank for security plan id="+sptid+" and userId="+userId+".  Please update the RegressionModule.xml with a list of <regressionSecurityUser><id> located at "+pathToRegressionXML);
    	    				}
    	    				String userName = cisServerConfig.getUser();
  	    				
    	    				RegressionSecurityQueryType regressionSecurityQuery = getSecurityQuery(queryId, regressionSecurityQueryList);
    	    				if (regressionSecurityQuery == null) {
        						throw new CompositeException("The Security Query could not be found for security plan id="+sptid+" and queryId="+queryId+".  Please update the RegressionModule.xml with a list of <regressionSecurityQueries><id> located at "+pathToRegressionXML);   	    					
    	    				}
    	    														
					// 2. Execute items: 
    	    				// Initialize
    	    				item = new RegressionItem();
    	    				
    	    				// Get common items for queries, procedures and web services (mandatory)
				        	if (regressionSecurityQuery.getDatasource() != null && regressionSecurityQuery.getDatasource().length() > 0) {
					        	item.database = CommonUtils.extractVariable(prefix, regressionSecurityQuery.getDatasource(), propertyFile, false);
				        	} else {
				        		throw new CompositeException("The Security Query Datasource is missing or blank for query list queryId="+queryId+".  Please update the RegressionModule.xml with a list of <regressionSecurityQuery><datasource> located at "+pathToRegressionXML);
				        	}
				        	item.type = 0;
				        	String queryType = null;
				        	if (regressionSecurityQuery.getQueryType() != null && regressionSecurityQuery.getQueryType().length() > 0) {
				        		queryType = CommonUtils.extractVariable(prefix, regressionSecurityQuery.getQueryType(), propertyFile, false);
				        		if (queryType.equalsIgnoreCase("QUERY"))
				        			item.type = RegressionManagerUtils.TYPE_QUERY;
				        		if (queryType.equalsIgnoreCase("PROCEDURE"))
				        			item.type = RegressionManagerUtils.TYPE_PROCEDURE;
				        		if (queryType.equalsIgnoreCase("WEB_SERVICE"))
				        			item.type = RegressionManagerUtils.TYPE_WS;
				        	} else {
				        		throw new CompositeException("The Security Query Type is missing or blank for query list queryId="+queryId+".  Please update the RegressionModule.xml with a list of <regressionSecurityQuery><queryType> located at "+pathToRegressionXML);
				        	}
				        	if (regressionSecurityQuery.getQuery() != null && regressionSecurityQuery.getQuery().length() > 0) {
					        	item.input = CommonUtils.extractVariable(prefix, regressionSecurityQuery.getQuery(), propertyFile, false);
				        	} else {
				        		throw new CompositeException("The Security Query is missing or blank for query list queryId="+queryId+".  Please update the RegressionModule.xml with a list of <regressionSecurityQuery><query> located at "+pathToRegressionXML);
				        	}
				        	
				        	// Get procedure items (optional)
				        	if (regressionSecurityQuery.getProcOutTypes() != null && regressionSecurityQuery.getProcOutTypes().length() > 0) {
				        		item.outTypes = CommonUtils.extractVariable(prefix, regressionSecurityQuery.getProcOutTypes(), propertyFile, false).split(",");
				        	}
				        	
				        	// Get web service items (optional)
				        	if (queryType.equalsIgnoreCase("WEB_SERVICE")) {
					        	if (regressionSecurityQuery.getWsPath() != null && regressionSecurityQuery.getWsPath().length() > 0) {
						        	item.path = CommonUtils.extractVariable(prefix, regressionSecurityQuery.getWsPath(), propertyFile, false);
					        	} else {
					        		throw new CompositeException("The Security Query Web Service Path is missing or blank for query list queryId="+queryId+".  Please update the RegressionModule.xml with a list of <regressionSecurityQuery><wsPath> located at "+pathToRegressionXML);
					        	}
					        	// Initialize the item object
					        	if (regressionSecurityQuery.getWsAction() != null && regressionSecurityQuery.getWsAction().length() > 0) {
						        	item.action = CommonUtils.extractVariable(prefix, regressionSecurityQuery.getWsAction(), propertyFile, false);
					        	} else {
					        		throw new CompositeException("The Security Query Web Service Action is missing or blank for query list queryId="+queryId+".  Please update the RegressionModule.xml with a list of <regressionSecurityQuery><wsAction> located at "+pathToRegressionXML);
					        	}
				        		if (regressionSecurityQuery.getWsEncrypt() != null) {
						        	item.encrypt = Boolean.valueOf(CommonUtils.extractVariable(prefix, regressionSecurityQuery.getWsEncrypt(), propertyFile, false));
					        	}
					        	if (regressionSecurityQuery.getWsContentType() != null) {
						        	item.contentType = CommonUtils.extractVariable(prefix, regressionSecurityQuery.getWsContentType(), propertyFile, false);
					        	}
				        	}
				        	
				        	// Get resourcePath and resourceType
				        	if (regressionSecurityQuery.getResourcePath() != null) {
					        	item.resourcePath = CommonUtils.extractVariable(prefix, regressionSecurityQuery.getResourcePath(), propertyFile, false);
				        	}
				        	if (regressionSecurityQuery.getResourceType() != null) {
					        	item.resourceType = CommonUtils.extractVariable(prefix, regressionSecurityQuery.getResourceType(), propertyFile, false);
				        	}

				        	// Initialize variables
				        	boolean enablePlanTest = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regressionSecurityPlanTest.getEnabled(), propertyFile, false));
				        	String outputFile = null;
				           	String result = "SKIPPED"; 	// [SKIPPED,PASS,FAIL,ERROR]
				           	String actual = "";			// [NOT ENABLED, NO MATCH, PASS, FAIL, ERROR]
				        	String duration = "";
				        	String database = item.database;
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
				
				            	// Assign the query
				        		item.input = query;
				        		            	
				            	// Derive the base directory for the output file (remove any double quotes from the file name).
				            	if (baseDir != null) 
				            		outputFile = (baseDir + "/" + database.replaceAll("\"", "") + "/" + resourceURL.replaceAll("\"", "") + ".txt").replaceAll("//", "/");
				        	}
				        	// Execute Procedures
				        	if (item.type == RegressionManagerUtils.TYPE_PROCEDURE) {
				            	resourceType = "PROCEDURE";
				
				            	query = item.input.replaceAll("\n", " ");
				
				            	// Retrieve only the FROM clause procedure URL with no where clause and no SELECT * FROM projections and no parameters.
				            	resourceURL = RegressionManagerUtils.getTableUrl(query); 
				            	
				            	// Assign the query
				            	item.input = query;
				            	
				            	// Derive the base directory for the output file (remove any double quotes from the file name).
				            	if (baseDir != null) 
				            		outputFile = (baseDir + "/" + database.replaceAll("\"", "") + "/" + resourceURL.replaceAll("\"", "") + ".txt").replaceAll("//", "/");
				        	}
				        	// Execute Web Services
				        	if (item.type == RegressionManagerUtils.TYPE_WS) {
				            	resourceType = "WS";     
				            	query = (item.path+ "/" + item.action).replaceAll("//", "/");
				            	resourceURL = (item.path + "/" + item.action).replaceAll("//", "/").replaceAll("/", "."); // construct ws path from the path and action combined.
				            	if (resourceURL.indexOf(".") == 0)
				            		resourceURL = resourceURL.substring(1);
				            	// Derive the base directory for the output file (remove any double quotes from the file name).
				            	if (baseDir != null) 
				            		outputFile = (baseDir + "/" + database.replaceAll("\"", "") + "/" + resourceURL.replaceAll("\"", "") + ".txt").replaceAll("//", "/");
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
				                if (enablePlanTest && item.type == RegressionManagerUtils.TYPE_QUERY && runQueries && databaseMatch && resourceMatch)
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
				
				                	// Determine the outcome based on the actual result and expected outcome
				                	if (expectedOutcome.equalsIgnoreCase("PASS")) {
					                    actual = "PASS";
					                    result = "PASS";
					                    totalSuccessTests++;
					                    totalSuccessQueries++;
				                	} else { // Expected to FAIL but the query PASSED
				                		actual = "PASS";
				                		result = "FAIL";
						                totalFailedTests++;
						                totalFailedQueries++;
				                	}
				                }
				                else if (enablePlanTest && item.type == RegressionManagerUtils.TYPE_PROCEDURE && runProcs && databaseMatch && resourceMatch)
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

				                	// Determine the outcome based on the actual result and expected outcome
				                	if (expectedOutcome.equalsIgnoreCase("PASS")) {
					                	actual = "PASS";
					                    result = "PASS";
					                    totalSuccessTests++;
					                    totalSuccessProcs++;
				                	} else {  // Expected to FAIL but the query PASSED
					                	actual = "PASS";
				                		result = "FAIL";
						                totalFailedTests++;
						                totalFailedProcs++;
				                	}
				                }
				                else if (enablePlanTest && item.type == RegressionManagerUtils.TYPE_WS && runWs && databaseMatch && resourceMatch) 
				                {
				                	// Initialize the file
				                    if (outputFile != null)
				                    	CommonUtils.createFileWithContent(outputFile, "");
				                    
				                    // Print out the line to the command line
				    		        RegressionManagerUtils.printOutputStr(printOutputType, "summary", "Execute Web Service:  " + item.path, "");
				    		        RegressionManagerUtils.printOutputStr(printOutputType, "summary", item.input, "");
				
				                    // Execute the web service
				                	rows = RegressionManagerUtils.executeWs(item, outputFile, cisServerConfig, regressionConfig, outputDelimiter, printOutputType);          		
				                    
				                	// Determine the outcome based on the actual result and expected outcome
				                	if (expectedOutcome.equalsIgnoreCase("PASS")) {
					                    actual = "PASS";
					                    result = "PASS";
					                    totalSuccessTests++;
					                    totalSuccessWS++;
				                	} else {  // Expected to FAIL but the query PASSED
					                    actual = "PASS";
				                		result = "FAIL";
						                totalFailedTests++;
						                totalFailedWS++;
				                	}
				                }
				                else {
				                	// Skip this test
				                	if (item.type == RegressionManagerUtils.TYPE_WS) 
				                	{
				                		totalSkippedWS++;
				                		if (!enablePlanTest) {
				                			actual = "NOT ENABLED";
				                			message = "  ::Reason: NOT ENABLED: type="+resourceType+"  runWs="+runWs+"  databaseMatch="+databaseMatch+"  resourceMatch="+resourceMatch;
				                		} else {
				                			actual = "NO MATCH";
				                			message = "  ::Reason:    NO MATCH: type="+resourceType+"  runWs="+runWs+"  databaseMatch="+databaseMatch+"  resourceMatch="+resourceMatch;
				                		}
				                		RegressionManagerUtils.printOutputStr(printOutputType, "summary", "Test Skipped: "+ resourceURL + message + "\n", "");
				                	}
				                	else if (item.type == RegressionManagerUtils.TYPE_QUERY) 
				                	{
				                		totalSkippedQueries++;
				                		if (!enablePlanTest) {
				                			actual = "NOT ENABLED";
				                			message = "  ::Reason: NOT ENABLED: type="+resourceType+"  runQueries="+runQueries+"  databaseMatch="+databaseMatch+"  resourceMatch="+resourceMatch;
				                		} else {
				                			actual = "NO MATCH";
				                			message = "  ::Reason:    NO MATCH: type="+resourceType+"  runQueries="+runQueries+"  databaseMatch="+databaseMatch+"  resourceMatch="+resourceMatch;
				                		}
				                		RegressionManagerUtils.printOutputStr(printOutputType, "summary", "Test Skipped: "+ query + message + "\n", "");
				                	}
				                	else 
				                	{
				                		totalSkippedProcs++;
				                		if (!enablePlanTest) {
				                			actual = "NOT ENABLED";
				                			message = "  ::Reason: NOT ENABLED: type="+resourceType+"  runProcedures="+runProcs+"  databaseMatch="+databaseMatch+"  resourceMatch="+resourceMatch;
				                		} else {
				                			actual = "NO MATCH";
				                			message = "  ::Reason:    NO MATCH: type="+resourceType+"  runProcedures="+runProcs+"  databaseMatch="+databaseMatch+"  resourceMatch="+resourceMatch;
				                		}
				                		RegressionManagerUtils.printOutputStr(printOutputType, "summary", "Test Skipped: "+ query + message + "\n", "");               		
				                	}
				                	totalSkippedTests++;
				                }          
				        	}
				            catch (Exception e)
				            {
				            	if (e.getMessage().contains("insufficient privileges")) {
					            	actual = "FAIL";
					            	// Determine the outcome based on the actual result and expected outcome
				                	if (expectedOutcome.equalsIgnoreCase("PASS")) {
					                    result = "FAIL";
					                    totalFailedTests++;
						            	if (item.type == RegressionManagerUtils.TYPE_WS) {
						            		totalFailedWS++;
						            	}
						            	else if (item.type == RegressionManagerUtils.TYPE_QUERY) {
						            		totalFailedQueries++;
						            	}
						            	else {
						            		totalFailedProcs++;
						            	}
				                	} else {
				                		result = "PASS";
						                totalSuccessTests++;
						            	if (item.type == RegressionManagerUtils.TYPE_WS) {
						            		totalSuccessWS++;
						            	}
						            	else if (item.type == RegressionManagerUtils.TYPE_QUERY) {
						            		totalSuccessQueries++;
						            	}
						            	else {
						            		totalSuccessProcs++;
						            	}
				                	}
				            	} else {
					            	actual = "ERROR";
				            		result = "ERROR";
									totalErrorTests++;
					            	if (item.type == RegressionManagerUtils.TYPE_WS) {
					            		totalErrorWS++;
					            	}
					            	else if (item.type == RegressionManagerUtils.TYPE_QUERY) {
					            		totalErrorQueries++;
					            	}
					            	else {
					            		totalErrorProcs++;
					            	}
				            	}

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
					        	CommonUtils.rpad(actual, 			 12,padChar)+logDelim +
					        	CommonUtils.rpad(expectedOutcome,	 8, padChar)+logDelim +
   						       	CommonUtils.rpad(userName, 			30, padChar)+logDelim +
					        	CommonUtils.rpad(executionStartTime,26, padChar)+logDelim +
					        	CommonUtils.rpad(duration.trim(),	20, padChar)+logDelim +
					        	CommonUtils.rpad(""+rows,	 		20, padChar)+logDelim +
					        	CommonUtils.rpad(database, 			30, padChar)+logDelim +
					        	CommonUtils.rpad(query, 			70, padChar)+logDelim +
					        	CommonUtils.rpad(resourceType, 		11, padChar)+logDelim +
					        	CommonUtils.rpad(outputFile,		50, padChar)+logDelim +
					        	message;
				
					        	CommonUtils.appendContentToFile(logFilePath, content);
					            
					     } // end of process input file items loop: for (RegressionSecurityPlanTestType regressionSecurityPlanTest : regressionSecurityPlanTestList) 
					
    	    			// Determine overall security rating [PASS|FAIL]
    	    			overallSecurityRating = "PASS";
    	    			if (totalFailedTests > 0)
    	    				overallSecurityRating = "FAIL";
    	    			
    	    			// Output the overall security rating
    	    			CommonUtils.appendContentToFile(logFilePath, "# ");
    	    			content = "# Overall Security Rating="+overallSecurityRating+"   Security Plan Id="+securityPlanId;
    	    			
    					// Check for overall rating exception=FAIL
    					if (overallSecurityRating.equalsIgnoreCase("FAIL")) {
    						if (securityOverallRatingException)
    							CommonUtils.appendContentToFile(logFilePath, content+" - securityOverallRatingException="+securityOverallRatingException+":  EXCEPTION THROWN.");
    						else
    							CommonUtils.appendContentToFile(logFilePath, content+" - securityOverallRatingException="+securityOverallRatingException+":  EXCEPTION NOT THROWN.");
    					} else {
    						CommonUtils.appendContentToFile(logFilePath, content);
    					}
    					
						// Check for execution errors
						if (totalErrorTests > 0) {
							if (securityExecutionErrorException)
								CommonUtils.appendContentToFile(logFilePath, "# Total Error Tests="+totalErrorTests+" - securityExecutionErrorException="+securityExecutionErrorException+":  EXCEPTION THROWN.");
							else
								CommonUtils.appendContentToFile(logFilePath, "# Total Error Tests="+totalErrorTests+" - securityExecutionErrorException="+securityExecutionErrorException+":  EXCEPTION NOT THROWN.");
						} else {
							CommonUtils.appendContentToFile(logFilePath, "# Total Error Tests="+totalErrorTests);
						}
						CommonUtils.appendContentToFile(logFilePath, "# Summary: Total Success="+totalSuccessTests+"   Total Skipped="+totalSkippedTests+"   Total Failed="+totalFailedTests+"   Total Error="+totalErrorTests);
		        		CommonUtils.appendContentToFile(logFilePath, "# ");
    	    				
				        // Print out timings
				        String duration = CommonUtils.getElapsedTime(startDate);
				
				        String testTypeMessage = "";
				        if (SECURITY.equalsIgnoreCase(testType))
				        	testTypeMessage = "Execute a full query from the query list.";
				        
				        int len = 56;
								 logger.info("--------------------------------------------------------");
								 logger.info("---------- Regression Security Test Summary ------------");
								 logger.info("--------------------------------------------------------");
				logger.info(CommonUtils.rpad("Test Type: " + testType + "  Security Plan Id="+securityPlanId, len, " "));
				logger.info(CommonUtils.rpad("  " + testTypeMessage, len, " "));
								 logger.info("                                                        ");
				logger.info(CommonUtils.rpad("Total Successful        Queries: " + totalSuccessQueries, len, " "));
				logger.info(CommonUtils.rpad("Total Successful     Procedures: " + totalSuccessProcs, len, " "));
				logger.info(CommonUtils.rpad("Total Successful   Web Services: " + totalSuccessWS, len, " "));
								 logger.info("                                 ---------              ");
				logger.info(CommonUtils.rpad("Total Successful -------> Tests: " + totalSuccessTests, len, " "));
								 logger.info("                                                        ");
								 logger.info("                                                        ");
				logger.info(CommonUtils.rpad("Total Failed            Queries: " + totalFailedQueries, len, " "));
				logger.info(CommonUtils.rpad("Total Failed         Procedures: " + totalFailedProcs, len, " "));
				logger.info(CommonUtils.rpad("Total Failed       Web Services: " + totalFailedWS, len, " "));
								 logger.info("                                 ---------              ");
				logger.info(CommonUtils.rpad("Total Failed -----------> Tests: " + totalFailedTests, len, " "));
								 logger.info("                                                        ");
								 logger.info("                                                        ");
				logger.info(CommonUtils.rpad("Total Skipped           Queries: " + totalSkippedQueries, len, " "));
				logger.info(CommonUtils.rpad("Total Skipped        Procedures: " + totalSkippedProcs, len, " "));
				logger.info(CommonUtils.rpad("Total Skipped      Web Services: " + totalSkippedWS, len, " "));
								 logger.info("                                 ---------              ");
				logger.info(CommonUtils.rpad("Total Skipped ----------> Tests: " + totalSkippedTests, len, " "));
								 logger.info("                                                        ");
								 logger.info("                                                        ");
				logger.info(CommonUtils.rpad("Total Error             Queries: " + totalErrorQueries, len, " "));
				logger.info(CommonUtils.rpad("Total Error          Procedures: " + totalErrorProcs, len, " "));
				logger.info(CommonUtils.rpad("Total Error        Web Services: " + totalErrorWS, len, " "));
								 logger.info("                                 ---------              ");
				logger.info(CommonUtils.rpad("Total Error ------------> Tests: " + totalErrorTests, len, " "));
								 logger.info("                                                        ");
				logger.info(CommonUtils.rpad("Total Combined ---------> Tests: " + (totalSuccessTests+totalSkippedTests+totalFailedTests+totalErrorTests), len, " "));
								 logger.info("                                                        ");
				logger.info(CommonUtils.rpad("         Security Test duration: " + duration, len, " "));
								 logger.info("                                                        ");
								 logger.info("Review \"Security Test\" Summary: " + logFilePath);
								 logger.info("--------------------------------------------------------");
								 logger.info("-                                                      -");
								 logger.info("- Overall Security Test Rating: "+overallSecurityRating+"                   -");
								 logger.info("-                                                      -");
								 logger.info("--------------------------------------------------------");
				
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
				    	
						// Reset the logging so that it does not overwrite the log file when there are multiple security plan ids to process.
						logAppendBool = true;
					 }
					 
					// Check for overall rating exception=FAIL
					if (securityOverallRatingException && overallSecurityRating.equalsIgnoreCase("FAIL")) {
						throw new CompositeException("The Overall Security Rating=\"FAIL\" for security planId="+securityPlanId+".");						
					}
					// Check for execution errors
					if (securityExecutionErrorException && totalErrorTests > 0) {
						throw new CompositeException("Their were a total of ["+totalErrorTests+"] execution errors. for security planId="+securityPlanId+".");						
					}					
    			}
    		}
		}
	} // end method
	
	
	/****************************
	 * PRIVATE METHODS
	 ****************************/
	// get the requested user from the list of users
	private RegressionSecurityUserType getSecurityUser(String userId, List<RegressionSecurityUserType> regressionSecurityUserList) {
		RegressionSecurityUserType regressionSecurityUser = null;

		// Loop over the list of regression security users
		for (RegressionSecurityUserType regressionSecurityUserLoop : regressionSecurityUserList) {
			if (regressionSecurityUserLoop.getId() != null) {
				if (userId.equalsIgnoreCase(regressionSecurityUserLoop.getId())) {
					regressionSecurityUser = regressionSecurityUserLoop;
					break;
				}
			}
		}
		return regressionSecurityUser;
	}
	
	// get the requested query from the list of queries
	private RegressionSecurityQueryType getSecurityQuery(String queryId, List<RegressionSecurityQueryType> regressionSecurityQueryList) {
		RegressionSecurityQueryType regressionSecurityQuery = null;

		// Loop over the list of regression security queries
		for (RegressionSecurityQueryType regressionSecurityQueryListLoop : regressionSecurityQueryList) {
			if (regressionSecurityQueryListLoop.getId() != null) {
				if (queryId.equalsIgnoreCase(regressionSecurityQueryListLoop.getId())) {
					regressionSecurityQuery = regressionSecurityQueryListLoop;
					break;
				}
			}
		}
		return regressionSecurityQuery;
	}

}
