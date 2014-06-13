package com.cisco.dvbu.ps.deploytool.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;

import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.exception.ValidationException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.PropertyManager;
import com.cisco.dvbu.ps.common.util.XMLUtils;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.dao.GroupDAO;
import com.cisco.dvbu.ps.deploytool.dao.PrivilegeDAO;
import com.cisco.dvbu.ps.deploytool.dao.RegressionInputFileDAO;
import com.cisco.dvbu.ps.deploytool.dao.RegressionPerfTestDAO;
import com.cisco.dvbu.ps.deploytool.dao.RegressionPubTestDAO;
import com.cisco.dvbu.ps.deploytool.dao.RegressionSecurityTestDAO;
import com.cisco.dvbu.ps.deploytool.dao.ResourceDAO;
import com.cisco.dvbu.ps.deploytool.dao.UserDAO;
import com.cisco.dvbu.ps.deploytool.dao.jdbcapi.RegressionInputFileJdbcDAOImpl;
import com.cisco.dvbu.ps.deploytool.dao.jdbcapi.RegressionPerfTestDAOImpl;
import com.cisco.dvbu.ps.deploytool.dao.jdbcapi.RegressionPubTestJdbcDAOImpl;
import com.cisco.dvbu.ps.deploytool.dao.jdbcapi.RegressionSecurityTestDAOImpl;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.GroupWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.PrivilegeWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.ResourceWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.UserWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.cisco.dvbu.ps.deploytool.modules.RegressionModule;
import com.cisco.dvbu.ps.deploytool.modules.RegressionQueriesType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityGenerationOptionsType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityPlanTestType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityPlanType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityPlansType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityQueriesType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityQueryType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityUserType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityUsersType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionTestType;
import com.compositesw.services.system.admin.user.Group;
import com.compositesw.services.system.admin.user.GroupList;
import com.compositesw.services.system.admin.user.User;
import com.compositesw.services.system.admin.user.UserList;
import com.compositesw.services.system.admin.resource.GetResourcePrivilegesRequest.Entries;
import com.compositesw.services.system.admin.resource.PathTypeOrColumnPair;
import com.compositesw.services.system.admin.resource.Privilege;
import com.compositesw.services.system.admin.resource.PrivilegeEntry;
import com.compositesw.services.system.admin.resource.ResourceOrColumnType;
import com.compositesw.services.system.admin.resource.GetResourcePrivilegesResponse.PrivilegeEntries;

/** 
 * This class is an implementation of RegressionManager that uses standard Composite Pubtest utility's
 * general approach, some code and input file format.
 * This implementation differs from Pubtest in the following ways:
 * - instead of processing command line arguments, it uses xml configuration files and runs inside the CIS deployer
 * - it has additional functionality to generate the input file
 * 
 * Also see comments for the RegressionManager interface
 * 
 * @author sst
 * @since April 2011
 * @modified 
 * 	2013-02-13 (mtinius): added support for variables for all fields in RegressionModule.xml
 */
public class RegressionManagerImpl implements RegressionManager
{
	private static Log logger = LogFactory.getLog(RegressionManagerImpl.class);
    public static String propertyFile = initializePropertyFile();
	private RegressionInputFileDAO regressionInputFileDAO = null;
	private RegressionPubTestDAO testRunDao = null;
	private RegressionPerfTestDAO perfTestDao =null;
	private RegressionSecurityTestDAO securityTestDao =null;
	private CompositeServer cisServerConfig = null;
	private RegressionTestType regressionConfig = null;
	private RegressionQueriesType regressionQueries = null;
    private UserDAO userDAO = null;
    private GroupDAO groupDAO = null;
    private PrivilegeDAO privilegeDAO = null;
	private ResourceDAO resourceDAO = null;
	
	// Create a hash map of privileges for a given key=resourcePath + " " + resourceType
	HashMap<String, PrivilegeEntries> privilegeEntriesList = new HashMap<String, PrivilegeEntries>();

	// Test Types from Regression XML schema
    static final String FUNCTIONAL = "functional";
    static final String MIGRATION = "migration";
    static final String REGRESSION = "regression";
    static final String PERFORMANCE = "performance";
    static final String SECURITY = "security";
    
    // Debug parameters
    private static String suppress = "";
    private static boolean debug1 = false;
    private static boolean debug2 = false;
    private static boolean debug3 = false;

	/**
	 * Default constructor.  
	 */
	public RegressionManagerImpl() {}
	
	/**
	 * ArrayList of RegressionQuery items read from the RegressionModule.xml file (or equivalent).
	 * This is the list of queries that will be used to generate the input file.
	 */
	private ArrayList<RegressionQuery> regressionQueryList = new ArrayList<RegressionQuery>();

	private static String initializePropertyFile() throws CompositeException
	{
        String propertyFile = CommonUtils.getFileOrSystemPropertyValue(null, "CONFIG_PROPERTY_FILE");
        if (propertyFile == null || propertyFile.length() == 0)
        	propertyFile = CommonConstants.propertyFile;
		if (!PropertyManager.getInstance().doesPropertyFileExist(propertyFile)) {
			throw new ApplicationException("The property file does not exist for CONFIG_PROPERTY_FILE="+propertyFile);
		}
		logger.info("");
		logger.info("----------------------------------------------");
		logger.info("CONFIG_PROPERTY_FILE="+propertyFile);
		logger.info("----------------------------------------------");
		return propertyFile;
	}
	
/** 
	 * Generates input file similar to the one that Composite Pubtest uses.
	 * 
	 * @param serverId   	    serverId from servers.xml
	 * @param regressionIds		list of published regression ids for which we need to generate tests
	 * @param pathToServersXML  path to servers.xml
	 * 
	 * Also see comments for  
	 * com.cisco.dvbu.ps.deploytool.services.RegressionManager#generateInputFile(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generateInputFile(String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException
	{
		// Initialize prefix
		String prefix = "generateInputFile";
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// Set the CIS Server Configuration
		setCisServerConfig(serverId, pathToServersXML);

		// Extract variables for the regressionIds
		regressionIds = CommonUtils.extractVariable(prefix, regressionIds, propertyFile, true);

		List<RegressionTestType> regressionList = getRegression(serverId, regressionIds, pathToRegressionXML, pathToServersXML);
		if (regressionList != null && regressionList.size() > 0) {

			// Loop over the list of regression compare ids and perform the comparison between files based on the target data source resources
			for (RegressionTestType regression : regressionList) {

				// Get the identifier and convert any $VARIABLES
				String identifier = CommonUtils.extractVariable(prefix, regression.getId(), propertyFile, true);

				/**
				 * Possible values for regression 
				 * 1. csv string like test1,test2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -test1,test2 (we ignore passed in resources and process rest of the in the input xml
				 */
				 if(DeployUtil.canProcessResource(regressionIds, identifier)){

					if(logger.isInfoEnabled()){
						logger.info("-----------------------------------------------------------------------");
						logger.info("Processing action \"generate input file\" for regression id: "+identifier);
						logger.info("-----------------------------------------------------------------------");
					}

					// Verify that the newFileParams exists
					if (regression.getNewFileParams() == null) {
						throw new CompositeException("The <newFileParams> entry does not exist in the regression XML file.");
					}

					// Set the Regression Configuration for this ID
					this.regressionConfig = regression;		

					// Check to see if a new file should be created or not
					boolean isNewInputFileNeeded = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, this.regressionConfig.getCreateNewFile(), propertyFile, false));
					if (isNewInputFileNeeded)
					{
						// Generate the Regression Input file
						RegressionInputFileDAO inputFileDao = getRegressionInputFileJdbcDAO();
						String inputFileStr = inputFileDao.generateInputFile(this.cisServerConfig, this.regressionConfig, this.regressionQueries);
						if(logger.isInfoEnabled()){
							logger.debug(inputFileStr);
						}
					}
					// return inputFileStr;
					else
					{
						if(logger.isInfoEnabled()){
							logger.info("Configuration setting for new test file creation was set to false, so no new file was created.");
						}
					}
				 }
			}
		}
	}

	/** 
	 * Executes all views, procedures and Web Services that are specified in the input file. 
	 * 
	 * Also see comments for 
	 * com.cisco.dvbu.ps.deploytool.services.RegressionManager#executeRegressionTest(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 *
	 */
//	@Override
	public void executeRegressionTest( String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException
	{
		// Initialize prefix
		String prefix = "executeRegressionTest";
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToRegressionXML)) {
			throw new CompositeException("File ["+pathToRegressionXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// Set the CIS Server Configuration
		setCisServerConfig(serverId, pathToServersXML);

		// Extract variables for the regressionIds
		regressionIds = CommonUtils.extractVariable(prefix, regressionIds, propertyFile, true);

		List<RegressionTestType> regressionList = getRegression(serverId, regressionIds, pathToRegressionXML, pathToServersXML);
		if (regressionList != null && regressionList.size() > 0) {

			// Loop over the list of regression compare ids and perform the comparison between files based on the target data source resources
			for (RegressionTestType regression : regressionList) {

				// Get the identifier and convert any $VARIABLES
				String identifier = CommonUtils.extractVariable(prefix, regression.getId(), propertyFile, true);

				/**
				 * Possible values for regression 
				 * 1. csv string like test1,test2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -test1,test2 (we ignore passed in resources and process rest of the in the input xml
				 */
				 if(DeployUtil.canProcessResource(regressionIds, identifier)){
					if(logger.isInfoEnabled()){
						logger.info("-----------------------------------------------------------------------");
						logger.info("Processing action \"execute test\" for regression id: "+identifier);
						logger.info("-----------------------------------------------------------------------");
					}
					
					// Verify that testRunParams exists in the regression xml file		
					if (regression.getTestRunParams() == null) {
						throw new CompositeException("The <testRunParams> entry does not exist in the regression XML file.");
					}
					
					// Set the Regression Configuration for this ID;
					this.regressionConfig = regression;	
					String testType = CommonUtils.extractVariable(prefix, this.regressionConfig.getTestRunParams().getTestType(), propertyFile, false);
					
					// Execute the functional or migration test if the test type is set to "functional" or "migration"
					if (FUNCTIONAL.equalsIgnoreCase(testType) || MIGRATION.equalsIgnoreCase(testType) || REGRESSION.equalsIgnoreCase(testType)) 
					{
						// Execute the functional regression test for the given Regression ID
						RegressionPubTestDAO testDAO = getRegressionPubTestJdbcDAO();
						testDAO.executeAll(this.cisServerConfig, this.regressionConfig);
					}
					else
					{
						logger.info("Skipping regression id="+identifier+" because the test type is not set to \"functional\" or \"migration\".");
					}

				 }
			}
		}
	}

	/** 
	 * compareRegressionFiles:
	 * 
	 * Compares the contents of the regression files to determine if they are the same or not. 
	 *
	 * The objective of this method is to retrieve the Regression XML for the specified identifier and loop
	 * over views and procedures identified by the list of databases.   This method will derive the list of
	 * views, procedures and web services which will be used to get the files from the specified base directories.
	 * The base directories inform the method where the files have been placed.   During the execution of the regression
	 * test, the database name used to create a sub-directory.  The combination of any catalogs, schemas and resource
	 * names will be used as sub-directories and the actual file names with a .txt extension.  Each catalog becomes
	 * a sub-directory and each schema becomes a sub-directory.  Nesting is implied by the way the user has defined
	 * the nesting in the Database structure.
	 * 
	 * As an example let's define the following:
	 * 		BaseDir1=C:\TMP\CIS51
	 * 		BaseDir2=C:\TMP\CIS61
	 * 		Database: TEST
	 * 			Catalog: TEST1
	 * 				Schema: SCH
	 * 					Table: VIEW1, VIEW2
	 * 					Procedure: myProc1
	 * 			Catalog: TEST2
	 * 				Schema: SCH
	 * 					Table: VIEWX, VIEWY
	 * 					Procedure: myProcZ
	 * 
	 * Therefore, the following directories and files would be created at regression execution time.  These
	 * same directories are consumed during the compare regression files phase.  
	 * 		C:\TMP\CIS51\TEST\
	 * 			TEST1.SCH.VIEW1
	 * 			TEST1.SCH.VIEW2
	 * 			TEST1.SCH.myProc1

	 * 			TEST2.SCH.VIEWX
	 * 			TEST2.SCH.VIEWY
	 * 			TEST2.SCH.myProcZ
	 * 
	 * 		C:\TMP\CIS61\TEST
	 * 			TEST1.SCH.VIEW1
	 * 			TEST1.SCH.VIEW2
	 * 			TEST1.SCH.myProc1

	 * 			TEST2.SCH.VIEWX
	 * 			TEST2.SCH.VIEWY
	 * 			TEST2.SCH.myProcZ
	 * 
	 * Also see comments for 
	 * com.cisco.dvbu.ps.deploytool.services.RegressionManager#compareRegressionFiles(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void compareRegressionFiles(String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML)
			throws CompositeException {
				
		// Initialize prefix
		String prefix = "compareRegressionFiles";
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToRegressionXML)) {
			throw new CompositeException("File ["+pathToRegressionXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}
		// Set the CIS server information
		setCisServerConfig(serverId, pathToServersXML);

		// Extract variables for the regressionIds
		regressionIds = CommonUtils.extractVariable(prefix, regressionIds, propertyFile, true);
		
		List<RegressionTestType> regressionList = getRegression(serverId, regressionIds, pathToRegressionXML, pathToServersXML);
		if (regressionList != null && regressionList.size() > 0) {

			// Loop over the list of regression compare ids and perform the comparison between files based on the target data source resources
			for (RegressionTestType regression : regressionList) {

				// Get the identifier and convert any $VARIABLES
				String identifier = CommonUtils.extractVariable(prefix, regression.getId(), propertyFile, true);

				/**
				 * Possible values for regression 
				 * 1. csv string like test1,test2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -test1,test2 (we ignore passed in resources and process rest of the in the input xml
				 */
				 if(DeployUtil.canProcessResource(regressionIds, identifier)){
					 
					// Initialize variables
					int totalSuccessComparisons = 0;
					int totalFailureComparisons = 0;
					int totalErrorComparisons = 0;
					int totalSkippedComparisons = 0;
					
		            Date startDate = new Date();
					
					if(logger.isInfoEnabled()){
						logger.info("-----------------------------------------------------------------------");
						logger.info("processing action \"compare result files\" for regression id: "+identifier);
						logger.info("-----------------------------------------------------------------------");
					}
					// Get input file attributes
					String inputFilePath = CommonUtils.extractVariable(prefix, regression.getInputFilePath(), propertyFile, true);
					if (inputFilePath == null || inputFilePath.length() == 0) {
						throw new CompositeException("The input file path inputFilePath + inputFileName may not be null or empty in the regression XML file.");
					}
					if (!CommonUtils.fileExists(inputFilePath)) {
						throw new CompositeException("The input file path does not exist: ["+inputFilePath+"] as defined in the regression XML file");
					}

					// Verify that the compareFiles exists in the regression XML file.
					if (regression.getCompareFiles() == null) {
						throw new CompositeException("The <compareFiles> entry does not exist in the regression XML file.");
					}
					
					// Get the regression log location
			        String logFilePath = CommonUtils.extractVariable(prefix, regression.getCompareFiles().getLogFilePath(), propertyFile, true); 
			        if (logFilePath == null || logFilePath.length() == 0) {
						throw new CompositeException("The log file path compareFiles.logFilePath may not be null or empty in the regression XML file.");
			        }
			        
			        // Get output log file delimiter for the compareFiles summary log file
			        String logDelim = "|";
					if ( regression.getCompareFiles().getLogDelimiter() != null) {
						logDelim = RegressionManagerUtils.getDelimiter(CommonUtils.extractVariable(prefix, regression.getCompareFiles().getLogDelimiter().toString(), propertyFile, false));
					}

			        // Get the log append parameter
			        String logAppend = CommonUtils.extractVariable(prefix, regression.getCompareFiles().getLogAppend(), propertyFile, false); 
			        if (logAppend == null || logAppend.length() == 0) {
						throw new CompositeException("The log file append compareFiles.logAppend may not be null or empty in the regression XML file.");
			        }

			        // Get the base directory for instance 1 and verify it exists
					String baseDir1 = CommonUtils.extractVariable(prefix, regression.getCompareFiles().getBaseDir1(), propertyFile, true);
					if (baseDir1 == null || baseDir1.length() == 0) {
						throw new CompositeException("The base dir 1 path may not be null or empty in the regression XML file.");
					}
					if (!CommonUtils.fileExists(baseDir1)) {
						throw new CompositeException("The base dir 1 path does not exist: ["+baseDir1+"] as defined in the regression XML file");
					}
			        // Get the base directory for instance 2 and verify it exists
					String baseDir2 = CommonUtils.extractVariable(prefix, regression.getCompareFiles().getBaseDir2(), propertyFile, true);
					if (baseDir2 == null || baseDir2.length() == 0) {
						throw new CompositeException("The base dir 2 path may not be null or empty in the regression XML file.");
					}
					if (!CommonUtils.fileExists(baseDir2)) {
						throw new CompositeException("The base dir 2 path does not exist: ["+baseDir2+"] as defined in the regression XML file");
					}
										
					// Check to see what should be executed
					boolean compareQueries = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regression.getCompareFiles().getCompareQueries(), propertyFile, false));
					boolean compareProcs = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regression.getCompareFiles().getCompareProcedures(), propertyFile, false));
					boolean compareWs = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regression.getCompareFiles().getCompareWS(), propertyFile, false));
					boolean useAllDatasources = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regression.getCompareFiles().getUseAllDatasources(), propertyFile, false));
					
			        // Write out the header log entry
					/*
					 * Result,  CompareStartTime,         Database,  URL,              Type,         File1              File2             Message
					 * SUCCESS| 2012-05-09 01:00:00.0000| MYDB|      CAT.SCHEMA.V1|    TABLE|        C:\tmp\51\V1.txt|  C:\tmp\61\V1.txt|
					 * FAILURE| 2012-05-09 01:00:00.0000| MYDB|      CAT.SCHEMA.P1|    PROCEDURE|    C:\tmp\51\P1.txt|  C:\tmp\61\P1.txt|
					 */
			        String padChar = " "; // pad characters
			        String content = 
			        	CommonUtils.rpad("Result", 			 8, padChar) + logDelim +
			        	CommonUtils.rpad("CompareStartTime",26, padChar) + logDelim +
			        	CommonUtils.rpad("Database", 		30, padChar) + logDelim +
			        	CommonUtils.rpad("ResourceURL", 	50, padChar) + logDelim +
			        	CommonUtils.rpad("Type", 			11, padChar) + logDelim +
			        	CommonUtils.rpad("File1", 			50, padChar) + logDelim +
			        	CommonUtils.rpad("File2", 			50, padChar) + logDelim +
			        	"Message"+"\r\n";

			        // Write out the header log entry -- if it does not exist the sub-directories will automatically be created.
			        if (RegressionManagerUtils.checkBooleanConfigParam(logAppend)) {
				        	CommonUtils.appendContentToFile(logFilePath, content);
			         } else {
			        	// create a new file
			        	CommonUtils.createFileWithContent(logFilePath, content);
			        }
				
					// Get the list of published datasource resources
			        RegressionItem[] items = RegressionManagerUtils.parseItems(inputFilePath);

			        // Execute each item which represents a query in the input file
			        for (int i=0; i<items.length; i++) {
			           	String result = ""; // SKIPPED (compare skipped), SUCCESS (files match), FAILURE (files do not match) or ERROR (error processing)
			        	String database = items[i].database;
			        	String query = items[i].input.replaceAll("\n", "");
			        	String resourceURL = "";
			        	String resourceType = "";
			        	if (items[i].type == RegressionManagerUtils.TYPE_QUERY) {
		                	resourceType = "QUERY";
		                	resourceURL = RegressionManagerUtils.getTableUrl(query);
			        	}
			        	if (items[i].type == RegressionManagerUtils.TYPE_PROCEDURE) {
		                	resourceType = "PROCEDURE";
		                	resourceURL = RegressionManagerUtils.getTableUrl(query);
			        	}
			        	if (items[i].type == RegressionManagerUtils.TYPE_WS) {
		                	resourceType = "WS";                	
		                	resourceURL = (items[i].path + "/" + items[i].action).replaceAll("//", "/").replaceAll("/", ".");
		                	if (resourceURL.indexOf(".") == 0)
		                		resourceURL = resourceURL.substring(1);
			        	}
			        	String message = "";
			        	String filePath1 = (baseDir1 + "/" + database + "/" + resourceURL + ".txt").replaceAll("//", "/");
			        	String filePath2 = (baseDir2 + "/" + database + "/" + resourceURL + ".txt").replaceAll("//", "/");
				        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS");
				        Date beginDate = new Date();
			        	String executionStartTime = formatter.format(beginDate);

			        	/*
			        	 *  If compareFiles.useAllDatasources is set to true in then use all datasource queries in the input file 
			        	 *  otherwise determine if the database in the input file is in the compareFiles.datasource list in the XML file
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
			        		databaseMatch = RegressionManagerUtils.findDatabaseMatch(database, regression.getCompareFiles().getDatasources(), propertyFile);
			        	
						/* Determine if the specific resource should be compared by checking the XML resource list.
						 * If the resourceURL pattern matches what is in this list then process it.
						 * 		<resources>
						 *			<resource>TEST1.*</resource>
						 *			<resource>TEST1.SCH.*</resource>
						 *			<resource>TEST1.SCH.VIEW1</resource>
						 *		</resources>
						 */
			        	boolean resourceMatch = RegressionManagerUtils.findResourceMatch(resourceURL, regression.getCompareFiles().getResources(), propertyFile);

						// Invoke the compare function if the resource is in the compare list
			        	try  {
			        		result = "SKIPPED";
			                if (items[i].type == RegressionManagerUtils.TYPE_QUERY && compareQueries && resourceMatch && databaseMatch)
			                {
			                	// Compare the two files
			                	if (CommonUtils.compareFiles(filePath1, filePath2)) {
			                		result = "SUCCESS";
			                		totalSuccessComparisons++;
			                	} else {
			                		result = "FAILURE";
			                		message = "File checksums are different.";
			                		totalFailureComparisons++;
			                	}
			                }
			                else if (items[i].type == RegressionManagerUtils.TYPE_PROCEDURE && compareProcs && resourceMatch && databaseMatch)
			                {
			                	// Compare the two files
			                	if (CommonUtils.compareFiles(filePath1, filePath2)) {
			                		result = "SUCCESS";
			                		totalSuccessComparisons++;
			                	} else {
			                		result = "FAILURE";
			                		message = "File checksums are different.";
			                		totalFailureComparisons++;
			                	}
			                }
			                else if (items[i].type == RegressionManagerUtils.TYPE_WS && compareWs && resourceMatch && databaseMatch) 
			                {
			                	// Compare the two files
			                	if (CommonUtils.compareFiles(filePath1, filePath2)) {
			                		result = "SUCCESS";
			                		totalSuccessComparisons++;
			                	} else {
			                		result = "FAILURE";
			                		message = "File checksums are different.";
			                		totalFailureComparisons++;
			                	}
			                }
			                if (result.equals("SKIPPED"))
			                	totalSkippedComparisons++;
			        	}
			            catch (Exception e) {
			                result = "ERROR";
			                message = e.getMessage().replace("\n", " ").replaceAll("\r", " ");
			                totalErrorComparisons++;
			            }

						// Output results to the compare log
				        content = 
				        	CommonUtils.rpad(result, 			 8, padChar) + logDelim +
				        	CommonUtils.rpad(executionStartTime,26, padChar) + logDelim +
				        	CommonUtils.rpad(database, 			30, padChar) + logDelim +
				        	CommonUtils.rpad(resourceURL, 		50, padChar) + logDelim +
				        	CommonUtils.rpad(resourceType, 		11, padChar) + logDelim +
				        	CommonUtils.rpad(filePath1, 		50, padChar) + logDelim +
				        	CommonUtils.rpad(filePath2, 		50, padChar) + logDelim +
				        	message;
				        
				        CommonUtils.appendContentToFile(logFilePath, content);
				        
			        } // end for (int i=0; i<items.length; i++)
			        
			        // Print out timings
		            String duration = CommonUtils.getElapsedTime(startDate);

					if(logger.isInfoEnabled()){
						int len = 56;
						logger.info("--------------------------------------------------------");
						logger.info("----- Regression Query Content Comparison Summary ------");
						logger.info("--------------------------------------------------------");
						logger.info("                                                        ");
						logger.info("Compares the contents of two regression files to        ");
						logger.info("determine if they are the same or not.                  ");
						logger.info("                                                        ");
						logger.info("      Success=checksums are the same.                   ");
						logger.info("      Skipped=did not match database or resource filter.");
						logger.info("      Failure=file checksums are different.             ");
						logger.info("      Error=Unknown issue encountered.                  ");
						logger.info("                                                        ");
	   logger.info(CommonUtils.rpad("      Total Successful Comparisons: " + totalSuccessComparisons, len, " "));
	   logger.info(CommonUtils.rpad("         Total Skipped Comparisons: " + totalSkippedComparisons, len, " "));
	   logger.info(CommonUtils.rpad("         Total Failure Comparisons: " + totalFailureComparisons, len, " "));
	   logger.info(CommonUtils.rpad("           Total Error Comparisons: " + totalErrorComparisons, len, " "));
						logger.info("                                    ---------           ");
	   logger.info(CommonUtils.rpad("                 Total Comparisons: " + (totalSuccessComparisons+totalFailureComparisons+totalErrorComparisons+totalSkippedComparisons), len, " "));
						logger.info("                                                        ");
	   logger.info(CommonUtils.rpad("    Regression comparsion duration: " + duration, len, " "));
						logger.info("                                                        ");
						logger.info("Review \"content comparison\" Summary: "+logFilePath);
						logger.info("--------------------------------------------------------");
					}

				} // end if(DeployUtil.canProcessResource(regressionIds, identifier))
				 
			} // end for (RegressionTestType regression : regressionList)
			
		} // end if (regressionList != null && regressionList.size() > 0)
		
	}// end method
	
	/** 
	 * Compare the Query Execution log files for two separate execution runs.
	 * Determine if queries executed in for two similar but separate tests are within the acceptable delta level.
	 * Compare each similar result duration and apply a +- delta level to see if it falls within the acceptable range.
	 * 
	 * Also see comments for 
	 * com.cisco.dvbu.ps.deploytool.services.RegressionManager#compareRegressionLogs(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 *
	 */
//	@Override
	public void compareRegressionLogs(String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException {
		
		// Initialize prefix
		String prefix = "compareRegressionLogs";
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToRegressionXML)) {
			throw new CompositeException("File ["+pathToRegressionXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// Set the CIS Server Configuration
		setCisServerConfig(serverId, pathToServersXML);

		// Extract variables for the regressionIds
		regressionIds = CommonUtils.extractVariable(prefix, regressionIds, propertyFile, true);

		List<RegressionTestType> regressionList = getRegression(serverId, regressionIds, pathToRegressionXML, pathToServersXML);
		
		// Populate the reqressionQuery ArrayList (this only needs to be done once per invocation.)
		regressionQueryList = RegressionManagerUtils.populateRegressionQueryList(regressionQueries, regressionQueryList, propertyFile);

		if (regressionList != null && regressionList.size() > 0) {

			// Loop over the list of regression compare ids and perform the comparison between log files based on the target data source resources
			for (RegressionTestType regression : regressionList) {

				// Get the identifier and convert any $VARIABLES
				String identifier = CommonUtils.extractVariable(prefix, regression.getId(), propertyFile, true);

				/**
				 * Possible values for regression 
				 * 1. csv string like test1,test2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -test1,test2 (we ignore passed in resources and process rest of the in the input xml
				 */
				 if(DeployUtil.canProcessResource(regressionIds, identifier)){
					if(logger.isInfoEnabled()){
						logger.info("-----------------------------------------------------------------------");
						logger.info("Processing action \"compare log files\" for regression id: "+identifier);
						logger.info("-----------------------------------------------------------------------");
					}
					
					// Verify that testRunParams exists in the regression xml file		
					if (regression.getCompareLogs() == null) {
						throw new CompositeException("The <compareLogs> entry does not exist in the regression XML file.");
					}

					// Get the log file path for creating the summary log file
					String logFilePath = CommonUtils.extractVariable(prefix, regression.getCompareLogs().getLogFilePath(), propertyFile, true);
					
			        // Get output log file delimiter for the compareLogs summary log file
			        String logDelimiter = "|";
					if ( regression.getCompareLogs().getLogDelimiter() != null) {
						logDelimiter = RegressionManagerUtils.getDelimiter(CommonUtils.extractVariable(prefix, regression.getCompareLogs().getLogDelimiter().toString(), propertyFile, false));
					}
					
					// Get the log append parameter
					boolean logAppend = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, regression.getCompareLogs().getLogAppend(), propertyFile, false));

					// Get the details for comparing the query execution log files for instance 1 and 2.
					String logFilePath1 = CommonUtils.extractVariable(prefix, regression.getCompareLogs().getLogFilePath1(), propertyFile, true);
					String logFilePath2 = CommonUtils.extractVariable(prefix, regression.getCompareLogs().getLogFilePath2(), propertyFile, true);
									
			        // Get delimiter used within log file 1
			        String logDelimiter1 = "|";
					if ( regression.getCompareLogs().getDelimiter1() != null) {
						logDelimiter1 = RegressionManagerUtils.getDelimiter(CommonUtils.extractVariable(prefix, regression.getCompareLogs().getDelimiter1().toString(), propertyFile, false));
					}
					// <logDelimiter1> takes precedence over the deprecated <delimiter1>
					if ( regression.getCompareLogs().getLogDelimiter1() != null) {
						logDelimiter1 = RegressionManagerUtils.getDelimiter(CommonUtils.extractVariable(prefix, regression.getCompareLogs().getLogDelimiter1().toString(), propertyFile, false));
					}
			        // Get delimiter used within log file 2
			        String logDelimiter2 = "|";
					if ( regression.getCompareLogs().getDelimiter2() != null) {
						logDelimiter2 = RegressionManagerUtils.getDelimiter(CommonUtils.extractVariable(prefix, regression.getCompareLogs().getDelimiter2().toString(), propertyFile, false));
					}
					// <logDelimiter2> takes precedence over the deprecated <delimiter2>
					if ( regression.getCompareLogs().getLogDelimiter2() != null) {
						logDelimiter2 = RegressionManagerUtils.getDelimiter(CommonUtils.extractVariable(prefix, regression.getCompareLogs().getLogDelimiter2().toString(), propertyFile, false));
					}

					// Get the duration delta (acceptable +-duration range)
					String durationDelta = CommonUtils.extractVariable(prefix, regression.getCompareLogs().getDurationDelta(), propertyFile, false);
					if (durationDelta == null) 
						durationDelta = "";
					
					// Execute the log file comparison for the given Regression ID
					compareLogs(logFilePath, logDelimiter, logAppend, logFilePath1, logDelimiter1, logFilePath2, logDelimiter2, durationDelta);
				 }
			}
		}

	}
	
	/** 
	 * Compare the Query Execution log files for two separate execution runs.
	 * Determine if queries executed for two "same" queries but separate tests are within the acceptable delta level.
	 * Compare each "same" query result duration and apply a +- delta level to see if it falls within the acceptable range.
	 * 
	 * Also see comments for 
	 * com.cisco.dvbu.ps.deploytool.services.RegressionManager#compareLogs(java.lang.String, java.lang.boolean, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 *
	 */
	private void compareLogs(String logFilePath, String logDelim, boolean logAppend, String filePath1, String delimiter1, String filePath2, String delimiter2, String defaultDurationDelta) throws CompositeException {

/* Query Execution Log File Structure
 * 
------------------------------------------------
Query Execution Log files look like this:
------------------------------------------------
Result |ExecutionStartTime     |       Duration|          Rows|               Database|                     Query|                                                                Type|      OutputFile|                                       Message
SUCCESS| 2012-06-03 15:08:38.0416| 000 00:00:00.0016| 9|                  MYTEST|                       SELECT * FROM ViewSales WHERE ReorderLevel <= 3|                      QUERY|     C:/tmp/cis61/MYTEST/ViewSales.txt|                
SUCCESS| 2012-06-03 15:08:38.0432| 000 00:00:00.0031| 50|                 MYTEST|                       SELECT * FROM SCH1.ViewOrder|                                         QUERY|     C:/tmp/cis61/MYTEST/SCH1.ViewOrder.txt|           
SUCCESS| 2012-06-03 15:08:38.0463| 000 00:00:00.0015| 14|                 MYTEST|                       SELECT * FROM SCH1.ViewSales WHERE CategoryID = 7|                    QUERY|     C:/tmp/cis61/MYTEST/SCH1.ViewSales.txt|           
SUCCESS| 2012-06-03 15:08:38.0478| 000 00:00:00.0016| 23|                 MYTEST|                       SELECT * FROM CAT1.SCH1.ViewSales where Discount > 0|                 QUERY|     C:/tmp/cis61/MYTEST/CAT1.SCH1.ViewSales.txt|      
SUCCESS| 2012-06-03 15:08:38.0494| 000 00:00:00.0000| 4|                  MYTEST|                       SELECT * FROM CAT1.SCH2.ViewSales WHERE ProductName like 'Mega%'|     QUERY|     C:/tmp/cis61/MYTEST/CAT1.SCH2.ViewSales.txt|      
SUCCESS| 2012-06-03 15:08:38.0510| 000 00:00:00.0015| 35|                 MYTEST|                       SELECT * FROM CAT1.SCH2.ViewSupplier|                                 QUERY|     C:/tmp/cis61/MYTEST/CAT1.SCH2.ViewSupplier.txt|   
SKIPPED| 2012-06-03 15:08:38.0525| 000 00:00:00.0000| 0|                  MYTEST|                       SELECT * FROM getProductName(1)|                                      PROCEDURE| C:/tmp/cis61/MYTEST/getProductName.txt|           
SUCCESS| 2012-06-03 15:08:38.0525| 000 00:00:00.0016| 1|                  MYTEST|                       SELECT count(*) cnt FROM CAT1.SCH1.LookupProduct( 1 )|                PROCEDURE| C:/tmp/cis61/MYTEST/CAT1.SCH1.LookupProduct.txt|  
SUCCESS| 2012-06-03 15:08:38.0541| 000 00:00:00.0000| 1|                  MYTEST|                       SELECT count(*) cnt FROM CAT1.SCH2.LookupProduct( 1 )|                PROCEDURE| C:/tmp/cis61/MYTEST/CAT1.SCH2.LookupProduct.txt|  
SUCCESS| 2012-06-03 15:08:38.0541| 000 00:00:00.0015| 1|                  MYTEST|                       SELECT count(*) cnt FROM SCH1.LookupProduct( 1 )|                     PROCEDURE| C:/tmp/cis61/MYTEST/SCH1.LookupProduct.txt|       
SUCCESS| 2012-06-03 15:08:38.0556| 000 00:00:00.0000| 1|                  MYTEST|                       SELECT count(*) cnt FROM LookupProduct( 1 )|                          PROCEDURE| C:/tmp/cis61/MYTEST/LookupProduct.txt|            
SKIPPED| 2012-06-03 15:08:38.0556| 000 00:00:00.0016| 0|                  testWebService00|             /services/testWebService00/testService/testPort.ws/testprocecho|      WS|        C:/tmp/cis61/testWebService00/services.testWebService00.testService.testPort.ws.testprocecho.txt|
SKIPPED| 2012-06-03 15:08:38.0572| 000 00:00:00.0000| 0|                  testWebService00|             /services/testWebService00/testService/testPort.ws/testprocsimple|    WS|        C:/tmp/cis61/testWebService00/services.testWebService00.testService.testPort.ws.testprocsimple.txt|
  ERROR| ....

------------------------------------------------
Performance Log files look like this:
------------------------------------------------
Result |  ExecutionStartTime|       Duration|          Rows|               Database|                     Query|                                                                Type|      OutputFile|                                       Message
SKIPPED|2012-06-08 09:22:25.0322  |000 00:00:00.0016   |0                   |MYTEST                        |SELECT * FROM CAT1.SCH2.ViewSupplier                                  |QUERY      |                                                  |  ::Reason: type=QUERY  runQueries=false  databaseMatch=true  resourceMatch=true
SUCCESS|2012-06-08 09:22:25.0338  |000 00:00:15.0017   |1                   |MYTEST                        |{ CALL SCH1.LookupProduct( 3 ) }                                      |PROCEDURE  |                                                  |
 HEADER|Threads=10  |Duration (s)=10  |Print Sleep (s)=5  |Exec Sleep (s)=0  ||||
 HEADER|Execs       |Execs/sec   |Rows/exec   |Latency (ms) |1st row (ms) |Duration (ms) ||
 DETAIL|12483       |2498.29     |1.00        |3.99         |0.44         |4995.00             ||
 DETAIL|11966       |2393.19     |1.00        |4.15         |0.43         |5000.00             ||
 DETAIL|21          |4.18        |1.00        |20.47        |6.19         |5022.00             ||
 TOTALS|8155.33     |1631.88     |1.00        |9.53         |2.35         |5005.66             ||
ERROR  | ....

 */
		/**
		 * ArrayList of QueryExecLog items read from the query execution log
		 */
		ArrayList<QueryExecLog> queryLogList2 = new ArrayList<QueryExecLog>();
		int totalNoMatch = 0;
		int totalSuccessComparisons = 0;
		int totalFailureComparisons = 0;
        Date startDate = new Date();
	
		String currFile = null;
		try {		
			// Process file2 and place the key and duration in an ArrayList
			currFile = filePath2;
	        List<String> lines = new ArrayList<String>();
	        String line;
	        File f = new File(currFile);
	        BufferedReader rd = new BufferedReader(new FileReader(f));
	        while ((line = rd.readLine()) != null) {
	        	lines.add(line.trim());
	        }
	        rd.close();

	        // Process the lines for file2
	        while (lines.size() > 0)
	        {
	            line = (String)lines.remove(0);
	            if (line.length() == 0 || line.trim().startsWith("Result")) 
	            {
	                continue;
	            }
				QueryExecLog queryLog2 = extractQueryExecLog(line, delimiter2);
            	queryLogList2.add(queryLog2);
	        }
	        
			// Process file1 and use this as the list to drive the comparison (file1 is the baseline)
        	currFile = filePath1;
	        lines = new ArrayList<String>();
	        f = new File(currFile);
	        rd = new BufferedReader(new FileReader(f));
	        while ((line = rd.readLine()) != null) {
	        	lines.add(line.trim());
	        }
	        rd.close();
	        
	        // Set up the header log entry.
	        String padChar = " "; // pad characters
	        String content = 
	        	CommonUtils.rpad("Result", 			 8, padChar) + logDelim +
	        	CommonUtils.rpad("Message", 		25, padChar) + logDelim +
	        	CommonUtils.rpad("BaselineDuration",19, padChar) + logDelim +
	        	CommonUtils.rpad("CompareDuration", 19, padChar) + logDelim +
	        	CommonUtils.rpad("DiffDuration", 	20, padChar) + logDelim +
	        	CommonUtils.rpad("DurationDelta", 	19, padChar) + logDelim +
	        	CommonUtils.rpad("Database", 		30, padChar) + logDelim +
	        	CommonUtils.rpad("ResourceURL", 	50, padChar) + logDelim +
	        	CommonUtils.rpad("Type", 			11, padChar) + logDelim +
	        	CommonUtils.rpad("Query1", 			70, padChar) + logDelim +
	        	"Query2" + "\r\n";
	
	        // Write out the header log entry -- if it does not exist the sub-directories will automatically be created.
	        if (logAppend) {
		        	CommonUtils.appendContentToFile(logFilePath, content);
	         } else {
	        	// create a new file
	        	CommonUtils.createFileWithContent(logFilePath, content);
	        }

	        // Process the lines for file1 to drive the comparison
	        while (lines.size() > 0)
	        {
	            line = (String)lines.remove(0);
	            if (line.length() == 0 || line.trim().startsWith("Result")) {
	                continue;
	            }
	            if (line.trim().startsWith("ERROR") || line.trim().startsWith("SKIPPED") || line.trim().startsWith("HEADER") || line.trim().startsWith("DETAIL") || line.trim().startsWith("TOTALS")) {
	            	// ****** TO DO ****** Log the fact that there was an ERROR or SKIPPED entry in the base line query log
	            	continue;
	            }
	            // Initialize variables
	            String result = ""; // [SUCCESS (durations match or are within the accepted range), FAILURE (durations are out of the accepted range), NO MATCH (could not find a matching query)]
	            String message = "";
	            String durationDelta = defaultDurationDelta;
	            String durationStr1 = "";
	            String durationStr2 = "";
	            long deltaDuration = 0L;
	            String diffDuration = "";
	            String database = "";
	            String resourceURL = "";
	            String type = "";
	            String query1 = "";
	            String query2 = "";
	            
	            // Retrieve the query log for the current line from file1
				QueryExecLog queryLog1 = extractQueryExecLog(line, delimiter1);
				if (queryLog1 != null) {
					// Get the basics for the query
    				database = queryLog1.database;
    				resourceURL = queryLog1.key;
    				type = queryLog1.type;
    				query1 = queryLog1.query;
				}
				
				// Find a matching query from file2
				QueryExecLog queryLog2 = getExactQueryExecLog(queryLog1.key, queryLog1.query, queryLogList2);
				
				// Query1 != Query2 : No match found or No durations are available to compare
				if (queryLog1 == null || queryLog2 == null || queryLog1.duration == null || queryLog2.duration == null) {
					// **** TO DO ****** No matching query log entry found for base line query log
					result = "NO MATCH";
					totalNoMatch++;
					if (queryLog2 == null)
						message = "query 2 entry not found";
					if (queryLog1.duration == null)
						message = "duration1 not set";
					if (queryLog2 != null && queryLog2.duration == null)
						message = "duration2 not set";
					
					QueryExecLog queryLogKey = getKeyQueryExecLog(queryLog1.key, queryLogList2);
					if (queryLogKey != null && queryLogKey.query != null)
							query2 = queryLogKey.query;

					System.out.println(result+"-"+message);
				// Query1 == Query2 : Match found
				} else {
					query2 = "Same as query1";
					
    				// Extract and compute durations
    				durationStr1 = queryLog1.duration;
					durationStr2 = queryLog2.duration;			
    				long duration1 = CommonUtils.getLongDuration(queryLog1.duration);
    				long duration2 = CommonUtils.getLongDuration(queryLog2.duration);
    				
    				// Check to see if this query has a specific durationDelta defined.  Use it if it does otherwise stay with the default durationDelta.
    				RegressionQuery rquery = RegressionManagerUtils.getRegressionQuery(queryLog1.key, regressionQueryList);
    				if (rquery != null) {
    					if (rquery.durationDelta != null && rquery.durationDelta.length() > 0) {
    						durationDelta = rquery.durationDelta;
    					}
    				}
    				deltaDuration = CommonUtils.getLongDuration(durationDelta);
    				
    				// Compute the differences
    				diffDuration = CommonUtils.getElapsedDuration(duration2 - duration1);
    				
    				// Make decisions on what is SUCCESS and FAILURE
					/* 
					 * Duration Match
					 */
    				if (duration1 == duration2) {
    					result = "SUCCESS";
    					totalSuccessComparisons++;
    					message = "duration match";
    					System.out.println(result+"-"+message+": "+queryLog1.key);
    				}
					/*
					 *  Performance got worse
					 */
    				if (duration2 > duration1) {
     					if (duration2-duration1 > deltaDuration) {
    						// Failure - tolerance exceeded
        					result = "FAILURE";
        					totalFailureComparisons++;
        					message = "exceeded range";
        					System.out.println(result+"-"+message+": "+queryLog1.key);
    					} else {
    						// Success - tolerance within acceptable range
        					result = "SUCCESS";
        					totalSuccessComparisons++;
        					message = "within accepted range";
        					System.out.println(result+"-"+message+": "+queryLog1.key);
    					}
    				}
					/*
					 *  Performance improved
					 */
    				if (duration2 < duration1) {
       					result = "SUCCESS";
       					totalSuccessComparisons++;
    					message = "performance improved";
    					System.out.println(result+"-"+message+": "+queryLog1.key);
    				}
				}
			
				// Output results to the compare log
		        content = 
	        		CommonUtils.rpad(result, 		 8, padChar) + logDelim +
	        		CommonUtils.rpad(message, 		25, padChar) + logDelim +
	        		CommonUtils.rpad(durationStr1, 	19, padChar) + logDelim +
	        		CommonUtils.rpad(durationStr2, 	19, padChar) + logDelim +
		        	CommonUtils.rpad(diffDuration, 	20, padChar) + logDelim +
	        		CommonUtils.rpad(durationDelta, 19, padChar) + logDelim +
	        		CommonUtils.rpad(database, 		30, padChar) + logDelim +
	        		CommonUtils.rpad(resourceURL, 	50, padChar) + logDelim +
	        		CommonUtils.rpad(type, 			11, padChar) + logDelim +
	        		CommonUtils.rpad(query1, 		70, padChar) + logDelim +
	        		query2;

		        CommonUtils.appendContentToFile(logFilePath, content);

	        }

	        // Print out timings
            String duration = CommonUtils.getElapsedTime(startDate);

            if(logger.isInfoEnabled()){		
            	 int len = 56;
				 logger.info("--------------------------------------------------------");
				 logger.info("-------- Regression Log File Comparison Summary --------");
				 logger.info("--------------------------------------------------------");
				 logger.info("                                                        ");
				 logger.info("Compare the query execution log files for two separate  ");
				 logger.info("\"Published Test\" execution runs.  Compare each query    ");
				 logger.info("result duration and apply a +- delta level to see if it ");
				 logger.info("falls within the acceptable range.  Log File 1 is the   ");
				 logger.info("baseline. Log File 2 is used to compare with log file 1.");
				 logger.info("                                                        ");
				 logger.info("  Success = query duration met the following criteria:  ");
				 logger.info("    performance match:     duration2 = duration1        ");
				 logger.info("    performance improved:  duration2 < duration1        ");
				 logger.info("    performance in acceptable range:                    ");
				 logger.info("                    duration2 > duration1 and           ");
				 logger.info("                    duration2-duration1 <= deltaDuration");
				 logger.info("                                                        ");
				 logger.info("  Failure = query duration met the following criteria:  ");
				 logger.info("    performance worsened: duration2 > duration1         ");
				 logger.info("    performance out of acceptable range:                ");
				 logger.info("                    duration2 > duration1 and           ");
				 logger.info("                    duration2-duration1 > deltaDuration ");
				 logger.info("                                                        ");
				 logger.info("  No Match = query could not be matched in either file. ");
				 logger.info("                                                        ");
logger.info(CommonUtils.rpad("     Total Successful Comparisons: " + totalSuccessComparisons, len, " "));
logger.info(CommonUtils.rpad("        Total Failure Comparisons: " + totalFailureComparisons, len, " "));
				 logger.info("                                   ---------            ");
logger.info(CommonUtils.rpad("                Total Comparisons: " + (totalSuccessComparisons+totalFailureComparisons), len, " "));
				 logger.info("                                                        ");
logger.info(CommonUtils.rpad("           Total No Match Queries: " + totalNoMatch, len, " "));
				 logger.info("                                                        ");
logger.info(CommonUtils.rpad("   Regression comparsion duration: " + duration, len, " "));
				 logger.info("                                                        ");
				 logger.info("Review \"log comparison\" Summary: "+logFilePath);
				 logger.info("--------------------------------------------------------");
			}
		} catch (FileNotFoundException e) {
			throw new CompositeException("Unable to find the query execution log file located at: "+currFile, e);
		} catch (IOException e) {
			throw new CompositeException(e.getMessage(),e);
		}
	}// end method
	
	/**
	 * executePerformanceTest - execute a performance test
	 * 
	 * @param serverId
	 * @param regressionIds
	 * @param pathToRegressionXML
	 * @param pathToServersXML
	 * @throws CompositeException
	 */
	public void executePerformanceTest( String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException
	{
		// Initialize prefix
		String prefix = "executePerformanceTest";

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToRegressionXML)) {
			throw new CompositeException("File ["+pathToRegressionXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// Set the CIS Server Configuration
		setCisServerConfig(serverId, pathToServersXML);

		// Extract variables for the regressionIds
		regressionIds = CommonUtils.extractVariable(prefix, regressionIds, propertyFile, true);

		List<RegressionTestType> regressionList = getRegression(serverId, regressionIds, pathToRegressionXML, pathToServersXML);
		if (regressionList != null && regressionList.size() > 0) {

			// Loop over the list of regression compare ids and perform the comparison between files based on the target data source resources
			for (RegressionTestType regression : regressionList) {

				// Get the identifier and convert any $VARIABLES
				String identifier = CommonUtils.extractVariable(prefix, regression.getId(), propertyFile, true);

				/**
				 * Possible values for regression 
				 * 1. csv string like test1,test2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -test1,test2 (we ignore passed in resources and process rest of the in the input xml
				 */
				 if(DeployUtil.canProcessResource(regressionIds, identifier)){
					if(logger.isInfoEnabled()){
						logger.info("-----------------------------------------------------------------------");
						logger.info("Processing action \"execute performance test\" for regression id: "+identifier);
						logger.info("-----------------------------------------------------------------------");
					}
					
					// Verify that testRunParams exists in the regression xml file		
					if (regression.getTestRunParams() == null) {
						throw new CompositeException("The <testRunParams> entry does not exist in the regression XML file.");
					}

					// Set the Regression Configuration for this ID;
					this.regressionConfig = regression;	
					
					// Execute the regression test for the given Regression ID
					RegressionPerfTestDAO perfTestDAO = getRegressionPerfTestDAO();

					// Only execute the performance test if the test type is set to "performance"
					if (PERFORMANCE.equalsIgnoreCase(CommonUtils.extractVariable(prefix, this.regressionConfig.getTestRunParams().getTestType(), propertyFile, false))) 
					{
						perfTestDAO.executePerformanceTest(this.cisServerConfig, this.regressionConfig, regressionList);						
					}
					else 
					{
						logger.info("Skipping regression id="+identifier+" because the test type is not set to \"performance\".");
					}
				 }
			}
		}

	}

	/** 
	 * Executes a security test based on the regressionSecurity users, queries and plans found in the RegressionModule.xml 
	 * 
	 * Also see comments for 
	 * com.cisco.dvbu.ps.deploytool.services.RegressionManager#executeSecurityTest(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 *
	 */
//	@Override
	public void executeSecurityTest(String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) throws CompositeException
	{
		// Initialize prefix
		String prefix = "executeSecurityTest";
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToRegressionXML)) {
			throw new CompositeException("File ["+pathToRegressionXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// Set the CIS Server Configuration
		setCisServerConfig(serverId, pathToServersXML);

		// Extract variables for the regressionIds
		regressionIds = CommonUtils.extractVariable(prefix, regressionIds, propertyFile, true);

		// Get the regression security
		RegressionSecurityType regressionSecurity = getRegressionSecurity(serverId, regressionIds, pathToRegressionXML, pathToServersXML);

		List<RegressionTestType> regressionList = getRegression(serverId, regressionIds, pathToRegressionXML, pathToServersXML);
		if (regressionList != null && regressionList.size() > 0) {

			// Loop over the list of regression compare ids and perform the comparison between files based on the target data source resources
			for (RegressionTestType regression : regressionList) {

				// Get the identifier and convert any $VARIABLES
				String identifier = CommonUtils.extractVariable(prefix, regression.getId(), propertyFile, true);

				/**
				 * Possible values for regression 
				 * 1. csv string like test1,test2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -test1,test2 (we ignore passed in resources and process rest of the in the input xml
				 */
				 if(DeployUtil.canProcessResource(regressionIds, identifier)){
					if(logger.isInfoEnabled()){
						logger.info("-----------------------------------------------------------------------");
						logger.info("Processing action \"execute test\" for regression id: "+identifier);
						logger.info("-----------------------------------------------------------------------");
					}
					
					// Verify that testRunParams exists in the regression xml file		
					if (regression.getTestRunParams() == null) {
						throw new CompositeException("The <testRunParams> entry does not exist in the regression XML file.");
					}
					
					// Set the Regression Configuration for this ID;
					this.regressionConfig = regression;	
					String testType = CommonUtils.extractVariable(prefix, this.regressionConfig.getTestRunParams().getTestType(), propertyFile, false);
					

					// Execute the security test if the test type is set to "security"
					if (SECURITY.equalsIgnoreCase(testType)) 
					{
						// Execute the functional regression test for the given Regression ID
						getRegressionSecurityTestDAO().executeSecurityTest(this.cisServerConfig, this.regressionConfig, regressionSecurity, pathToRegressionXML);
					}
					else
					{
						logger.info("Skipping regression id="+identifier+" because the test type is not set to \"security\".");
					}
				 }
			}
		}
	}

	/**
	 * Generates the Regression Security XML section of the RegressionModule.xml.
	 *   Generates Regression Security Users from the given filter applying the xml schema userMode=[NOEXEC|OVERWRITE|APPEND].
	 *   Generates Regression Security Queries from the given filter applying the xml schema queryMode=[NOEXEC|OVERWRITE|APPEND].
	 *   Generates a Cartesian product for the Regression Security Plans applying the xml schema planMode=[NOEXEC|OVERWRITE|APPEND] 
	 *     and planModeType=[SINGLEPLAN|MULTIPLAN].
	 *   Generates to a different RegressionModule.xml file than the source file so that formatting can be maintained in the XML in the source.
	 *     This is based on the xml schema pathToTargetRegressionXML.
	 *   It is recommended that the users copy the results out of the target, generated file and paste into the source file as needed.
	 *   A Cartesian product is where each user contains an execution for all of the queries. 
	 *   A security plan is as follows:
	 *     A security plan consists of executing all queries for a single user.
	 *     A Cartesian product involves creating a plan for each user with all queries.
	 *     
	 * RegressionModule.xml - ns2:RegressionModule/regressionTest/securityGenerationOptions
	 * Schema element: encryptedPassword		Encrypted Password - An optional security user default password.  It will be encrypted when the 
	 * 												ExecutePDTool.bat -encrypt ..\resources\modules\RegressionModule.xml is executed.
	 * Schema element: userFilter				Security user filter - This optional filter is a comma separated list of users or wild card users that 
	 * 												can be used to filter the generation of the Regression Security User list.
	 * Schema element: pathToTargetRegressionXML Path to Target Regression Module XML - a required path to the target configuration file for the regression module.  
	 * 												Provides a way of writing to a different file than the source or original RegressionModule.xml.
	 * Schema element: userMode					[NOEXEC|OVERWRITE|APPEND] - NOEXEC (default)=do nothing, don't execute. 
	 * 												OVERWRITE=overwrite existing security user XML, 
	 * 												APPEND=add to existing security user XML if the user does not exist.
	 * Schema element: queryMode				[NOEXEC|OVERWRITE|APPEND] - NOEXEC (default)=do nothing, don't execute. 
	 * 												OVERWRITE=overwrite existing security query XML, 
	 * 												APPEND=add to existing security query XML if the query does not exist.
	 * Schema element: planMode					[NOEXEC|OVERWRITE|APPEND] - NOEXEC (default)=do nothing, don't execute. 
	 * 												OVERWRITE=overwrite existing security plan XML, 
	 * 												APPEND=add to existing security plan XML if the plan does not exist.
	 * Schema element: planModeType				[SINGLEPLAN|MULTIPLAN] - SINGLEPLAN=Generate the Cartesian plan as a single plan. 
	 * 												MULTIPLAN=Generate the Cartesian plan as multiple plans for each user who has the same set of queries.  
	 *					e.g. when planMode=OVERWRITE and planModeType=MULTIPLAN - will produce a new list where each user is a security plan with the full set of queries.  
	 *					e.g. when planMode=APPEND and planModeType=SINGLEPLAN - will produce a new plan appended to the existing set of plans where this plan will contain a Cartesian product of users and queries.
	 * 
	 * @param serverId     				server Id from servers.xml
	 * @param regressionIds       		comma-separated list of the published regression identifiers to run test against
	 * @param pathToSourceRegressionXML path to the source configuration file for the regression module.  Provides a way of maintaining existing files without overwriting.
	 * @param pathToServersXML     		path to servers.xml
	 * @throws CompositeException
	 */
	public void generateRegressionSecurityXML(String serverId, String regressionIds, String pathToSourceRegressionXML, String pathToServersXML) throws CompositeException 
	{
		// Initialize prefix
		String prefix = "generateRegressionSecurityXML";
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToSourceRegressionXML)) {
			throw new CompositeException("File source regression model xml ["+pathToSourceRegressionXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}
		// validate incoming arguments
		if(serverId == null || serverId.trim().length() ==0 || regressionIds == null || regressionIds.trim().length() ==0 || pathToSourceRegressionXML.trim().length() ==0){
			throw new ValidationException("Invalid Arguments");
		}

		// Set debug properties
		setGlobalProperties();
		
		// Set the CIS Server Configuration
		setCisServerConfig(serverId, pathToServersXML);

		// Initialize the Regression Module XML
		RegressionModule regressionModule = null;
		
		// Initialize the Regression Security XML
		RegressionSecurityType regressionSecurity = null;
		
		// Extract variables for the regressionIds
		regressionIds = CommonUtils.extractVariable(prefix, regressionIds, propertyFile, true);

		try {
			//using jaxb convert xml to corresponding java objects
			regressionModule = (RegressionModule)XMLUtils.getModuleTypeFromXML(pathToSourceRegressionXML);
			
			// Get the regression tests
			if(regressionModule != null && regressionModule.getRegressionSecurity() != null){
				regressionSecurity = regressionModule.getRegressionSecurity();
			} else {
				regressionSecurity = new RegressionSecurityType();
			}
		} catch (CompositeException e) {
			logger.error("Error while parsing RegressionModule XML: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}

		
		List<RegressionTestType> regressionList = getRegression(serverId, regressionIds, pathToSourceRegressionXML, pathToServersXML);
		if (regressionList != null && regressionList.size() > 0) 
		{
			// Loop over the list of regression compare ids and perform the comparison between files based on the target data source resources
			for (RegressionTestType regression : regressionList) 
			{
				// Get the identifier and convert any $VARIABLES
				String identifier = CommonUtils.extractVariable(prefix, regression.getId(), propertyFile, true);

				/**
				 * Possible values for regression 
				 * 1. csv string like test1,test2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -test1,test2 (we ignore passed in resources and process rest of the in the input xml
				 */
				if (DeployUtil.canProcessResource(regressionIds, identifier))
				{
					if (logger.isInfoEnabled())
					{
						logger.info("-----------------------------------------------------------------------");
						logger.info("Processing action \"generate security XML\" for regression id: "+identifier);
						logger.info("-----------------------------------------------------------------------");
					}
					
					// Initialize variables
					String defaultEncryptedPassword = "";
					String userFilter = "";
			        String pathToTargetRegressionXML = null;
					String userMode = null;
					String queryMode = null;
			        String planMode = null;
			        String planModeType = null;
			        boolean planGenerateExpectedOutcome = false;
			        boolean flattenSecurityUsersXML = true;
			        boolean flattenSecurityQueryQueriesXML = true;
			        boolean flattenSecurityQueryProceduresXML = true;
			        boolean flattenSecurityQueryWebServicesXML = true;
			        boolean flattenSecurityPlansXML = true;

					// Verify that testRunParams exists in the regression xml file		
					if (regression.getTestRunParams() == null) {
						throw new CompositeException("The <testRunParams> entry does not exist in the regression XML file.");
					}
					
					// Set the Regression Configuration for this ID;
					this.regressionConfig = regression;	
					String testType = CommonUtils.extractVariable(prefix, this.regressionConfig.getTestRunParams().getTestType(), propertyFile, false);

					// Execute the security test if the test type is set to "security"
					if (SECURITY.equalsIgnoreCase(testType)) 
					{
						
						RegressionSecurityGenerationOptionsType securityGenerationOptions = new RegressionSecurityGenerationOptionsType();
						if (this.regressionConfig.getNewFileParams()!= null && this.regressionConfig.getNewFileParams().getSecurityGenerationOptions() != null) 
						{
							securityGenerationOptions = this.regressionConfig.getNewFileParams().getSecurityGenerationOptions();
						} else {
							throw new CompositeException("The <securityGenerationOptions> entry does not exist in the regression XML file.  It is required for generating the regression security XML.");
						}
						
						// Retrieve the Path to the Target Regression Module XML
						if (securityGenerationOptions.getPathToTargetRegressionXML() != null && securityGenerationOptions.getPathToTargetRegressionXML().length() > 0) 
						{
							pathToTargetRegressionXML = CommonUtils.extractVariable(prefix, securityGenerationOptions.getPathToTargetRegressionXML(), propertyFile, false);					
						} else {
							throw new CompositeException("The <securityGenerationOptions><getPathToTargetRegressionXML> entry does not exist in the regression XML file.  It is required for generating the regression security XML.");
						}
						
						// Retrieve the default encrypted password
						if (securityGenerationOptions.getEncryptedPassword() != null && securityGenerationOptions.getEncryptedPassword().length() > 0) 
						{
							defaultEncryptedPassword = CommonUtils.encrypt(CommonUtils.extractVariable(prefix, securityGenerationOptions.getEncryptedPassword(), propertyFile, false));						
						}

						// Retrieve the user filter
						if (securityGenerationOptions.getUserFilter() != null && securityGenerationOptions.getUserFilter().length() > 0) 
						{
							userFilter = CommonUtils.extractVariable(prefix, securityGenerationOptions.getUserFilter(), propertyFile, false);						
						} 

						// Retrieve the domain filter
						String domainFilter = "composite";
						if (securityGenerationOptions.getDomainFilter() != null && securityGenerationOptions.getDomainFilter().length() > 0) 
						{
							domainFilter = CommonUtils.extractVariable(prefix, securityGenerationOptions.getDomainFilter(), propertyFile, false);						
						} 

						// Retrieve the user mode
						if (securityGenerationOptions.getUserMode() != null && securityGenerationOptions.getUserMode().length() > 0) 
						{
							userMode = CommonUtils.extractVariable(prefix, securityGenerationOptions.getUserMode(), propertyFile, false);						
						} else {
							throw new CompositeException("The <securityGenerationOptions><userMode> entry does not exist in the regression XML file.  It is required for generating the regression security XML.");
						}

						// Retrieve the query mode
						if (securityGenerationOptions.getQueryMode() != null && securityGenerationOptions.getQueryMode().length() > 0) 
						{
							queryMode = CommonUtils.extractVariable(prefix, securityGenerationOptions.getQueryMode(), propertyFile, false);						
						} else {
							throw new CompositeException("The <securityGenerationOptions><queryMode> entry does not exist in the regression XML file.  It is required for generating the regression security XML.");
						}

						// Retrieve the plan mode
						if (securityGenerationOptions.getPlanMode() != null && securityGenerationOptions.getPlanMode().length() > 0) 
						{
							planMode = CommonUtils.extractVariable(prefix, securityGenerationOptions.getPlanMode(), propertyFile, false);						
						} else {
							throw new CompositeException("The <securityGenerationOptions><planMode> entry does not exist in the regression XML file.  It is required for generating the regression security XML.");
						}

						// Retrieve the plan mode type
						if (securityGenerationOptions.getPlanModeType() != null && securityGenerationOptions.getPlanModeType().length() > 0) 
						{
							planModeType = CommonUtils.extractVariable(prefix, securityGenerationOptions.getPlanModeType(), propertyFile, false);						
						} else {
							throw new CompositeException("The <securityGenerationOptions><planModeType> entry does not exist in the regression XML file.  It is required for generating the regression security XML.");
						}

						// Retrieve the default security plan id prefix
						String defaultPlanIdPrefix = "sp";
						if (securityGenerationOptions.getPlanIdPrefix() != null && securityGenerationOptions.getPlanIdPrefix().length() > 0) 
						{
							defaultPlanIdPrefix = CommonUtils.extractVariable(prefix, securityGenerationOptions.getPlanIdPrefix(), propertyFile, false);
						} 

						// Retrieve the flatten security users XML decision
						if (securityGenerationOptions.getPlanGenerateExpectedOutcome() != null && securityGenerationOptions.getPlanGenerateExpectedOutcome().length() > 0) 
						{
							planGenerateExpectedOutcome = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, securityGenerationOptions.getPlanGenerateExpectedOutcome(), propertyFile, false));
						} 
						
						// Retrieve the flatten security users XML decision
						if (securityGenerationOptions.getFlattenSecurityUsersXML() != null && securityGenerationOptions.getFlattenSecurityUsersXML().length() > 0) 
						{
							flattenSecurityUsersXML = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, securityGenerationOptions.getFlattenSecurityUsersXML(), propertyFile, false));
						} 

						// Retrieve the flatten security query (SQL Queries) XML decision
						if (securityGenerationOptions.getFlattenSecurityQueryQueriesXML() != null && securityGenerationOptions.getFlattenSecurityQueryQueriesXML().length() > 0) 
						{
							flattenSecurityQueryQueriesXML = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, securityGenerationOptions.getFlattenSecurityQueryQueriesXML(), propertyFile, false));
						} 

						// Retrieve the flatten security query (SQL Procedures) XML decision
						if (securityGenerationOptions.getFlattenSecurityQueryProceduresXML() != null && securityGenerationOptions.getFlattenSecurityQueryProceduresXML().length() > 0) 
						{
							flattenSecurityQueryProceduresXML = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, securityGenerationOptions.getFlattenSecurityQueryProceduresXML(), propertyFile, false));
						} 

						// Retrieve the flatten security query (Web Services) XML decision
						if (securityGenerationOptions.getFlattenSecurityQueryWebServicesXML() != null && securityGenerationOptions.getFlattenSecurityQueryWebServicesXML().length() > 0) 
						{
							flattenSecurityQueryWebServicesXML = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, securityGenerationOptions.getFlattenSecurityQueryWebServicesXML(), propertyFile, false));
						} 

						// Retrieve the flatten security plans XML decision
						if (securityGenerationOptions.getFlattenSecurityPlansXML() != null && securityGenerationOptions.getFlattenSecurityPlansXML().length() > 0) 
						{
							flattenSecurityPlansXML = RegressionManagerUtils.checkBooleanConfigParam(CommonUtils.extractVariable(prefix, securityGenerationOptions.getFlattenSecurityPlansXML(), propertyFile, false));
						} 

						/************************
						 * Generate the users
						 ************************/
						// OVERWRITE = Overwrite all users thus creating a new set of users
						// APPEND = Keep the original users and create a new users at then end of the list if they don't already exist.  This is like a merge.
						if (userMode != null && userMode.length() > 0 && (userMode.equalsIgnoreCase("OVERWRITE") || userMode.equalsIgnoreCase("APPEND")) ) 
						{	
							// Get the current regression security users and determine whether to OVERWRITE or APPEND
							RegressionSecurityUsersType regressionSecurityUsers = new RegressionSecurityUsersType(); 
							if (userMode.equalsIgnoreCase("APPEND") && regressionSecurity.getRegressionSecurityUsers() != null && 
									regressionSecurity.getRegressionSecurityUsers().getRegressionSecurityUser() != null &&
									regressionSecurity.getRegressionSecurityUsers().getRegressionSecurityUser().size() > 0) 
							{
								regressionSecurityUsers.getRegressionSecurityUser().addAll(regressionSecurity.getRegressionSecurityUsers().getRegressionSecurityUser());
							}
	
							
					        CommonUtils.writeOutput("---------------------------------------------------",	prefix,"-debug2",logger,debug1,debug2,debug3);
					        CommonUtils.writeOutput("Generate users with userMode="+userMode,				prefix,"-debug2",logger,debug1,debug2,debug3);
					        CommonUtils.writeOutput("---------------------------------------------------",	prefix,"-debug2",logger,debug1,debug2,debug3);

							// Get the list of users from the CIS server
							UserList cisUserList = null;
							UserList cisUserListFiltered = new UserList();
							try {
								ArrayList<String> validUsers = null;
								// Get the list of users from the server
								cisUserList = getUserDAO().getAllUsers(validUsers, domainFilter, serverId, pathToServersXML);
								
								// Iterate through the list of users
								for (User user : cisUserList.getUser()) {
									// Remove the user from the list if it does not meet the user filter criteria
									if (DeployUtil.canProcessResource(userFilter, user.getName())) {
										cisUserListFiltered.getUser().add(user);
									}
								}
							} catch (CompositeException e) {
								logger.error("Error while retrieving CIS user list" , e);
								throw new ApplicationContextException("Error while retrieving CIS user list"+e.getMessage(), e);
							}

							// Get the list of user ids and count
							String userIds = "";
							int userCount=0;
							for (int i=0; i < regressionSecurityUsers.getRegressionSecurityUser().size(); i++) 
							{
								String userId = regressionSecurityUsers.getRegressionSecurityUser().get(i).getId();
								if (userId != null && userId.length() > 0) {
									userIds = userIds + userId + " ";
									userCount++;									
								}
							}
						
							// Iterate through the list of users
							for (User user : cisUserListFiltered.getUser()) 
							{
								String userId = null;
								boolean userFound = false;
								
								/* Process the server user list against the current regression module users
								 * 	<regressionSecurityUsers>
								 *		<regressionSecurityUser>
								 *			<id>rsu1</id><userName>user1</userName><encryptedPassword>Encrypted:B0873483C56F7498</encryptedPassword>
								 *		</regressionSecurityUser>
								 *		...
								 *	</regressionSecurityUsers>	
								 */
								List<RegressionSecurityUserType> regressionSecurityUserList = regressionSecurityUsers.getRegressionSecurityUser();
								for (RegressionSecurityUserType regressionSecurityUser : regressionSecurityUserList)
								{
									userId = regressionSecurityUser.getId();
									String userName = regressionSecurityUser.getUserName();
									if (userName.equalsIgnoreCase(user.getName())) {
										userFound = true;
										break;
									}
								} // for (RegressionSecurityUserType regressionSecurityUser : regressionSecurityUserList)
								
								// Add the user to the security user XML
								if (!userFound) 
								{
					            	// Calculate the userId
									userId = "";
					            	while (true) 
					            	{
					            		++userCount;
					            		userId = "rsu" + userCount;
					            		if (!userIds.toLowerCase().contains(userId)) {
					            			break;
					            		}
					            	} // while (true) 

							        CommonUtils.writeOutput("Generate user with userId="+userId+"  user name="+user.getName(),	prefix,"-debug3",logger,debug1,debug2,debug3);
							        
							        // Add the user to the XML
					            	RegressionSecurityUserType regressionSecurityUserNew = new RegressionSecurityUserType();
					            	regressionSecurityUserNew.setId(userId);
					            	regressionSecurityUserNew.setUserName(user.getName());
					            	regressionSecurityUserNew.setEncryptedPassword(defaultEncryptedPassword);
					            	regressionSecurityUserNew.setDomain(user.getDomainName());
					            	regressionSecurityUsers.getRegressionSecurityUser().add(regressionSecurityUserNew);
								} // if (!userFound)
							} // for (User user : cisUserListFiltered.getUser()) 
							
							// Set the Regression Security Users
							regressionSecurity.setRegressionSecurityUsers(regressionSecurityUsers);

							// Update the Regression Security XML
							regressionModule.setRegressionSecurity(regressionSecurity);
							// Write the Regression Module XML back out to the target file.
							writeRegressionXMLFile(prefix, regressionModule, pathToTargetRegressionXML, 
									flattenSecurityUsersXML, flattenSecurityQueryQueriesXML, flattenSecurityQueryProceduresXML, flattenSecurityQueryWebServicesXML, flattenSecurityPlansXML);
						} // if (userMode != null && userMode.length() > 0 && ...

						/************************
						 * Generate the queries
						 ************************/
						// OVERWRITE = Overwrite all queries thus creating a new set of queries
						// APPEND = Keep the original queries and create new queries at the end of the existing list of queries
						if (queryMode != null && queryMode.length() > 0 && (queryMode.equalsIgnoreCase("OVERWRITE") || queryMode.equalsIgnoreCase("APPEND")) ) 
						{	
							// Get the current regression security Queries and determine whether to OVERWRITE or APPEND
							RegressionSecurityQueriesType regressionSecurityQueries = new RegressionSecurityQueriesType();
							if (queryMode.equalsIgnoreCase("APPEND") && regressionSecurity.getRegressionSecurityQueries() != null && 
								regressionSecurity.getRegressionSecurityQueries().getRegressionSecurityQuery() != null &&
								regressionSecurity.getRegressionSecurityQueries().getRegressionSecurityQuery().size() > 0) 
							{
								regressionSecurityQueries.getRegressionSecurityQuery().addAll(regressionSecurity.getRegressionSecurityQueries().getRegressionSecurityQuery());
							}
							
					        CommonUtils.writeOutput("---------------------------------------------------",	prefix,"-debug2",logger,debug1,debug2,debug3);
					        CommonUtils.writeOutput("Generate queries with queryMode="+queryMode,			prefix,"-debug2",logger,debug1,debug2,debug3);
					        CommonUtils.writeOutput("---------------------------------------------------",	prefix,"-debug2",logger,debug1,debug2,debug3);

							/* Execute the generate Security Queries XML to create or update the security queries
							 * 	<regressionSecurityQueries>	
							 *		<regressionSecurityQuery>
							 *			<id>rsq1</id><datasource>TEST00</datasource><queryType>QUERY</queryType><query>SELECT count(1) cnt FROM CAT1.SCH1.customers</query>
							 *		</regressionSecurityQuery>
							 *		...
							 *	</regressionSecurityQueries>	
							 */
							RegressionInputFileDAO inputDAO = getRegressionInputFileJdbcDAO();
							regressionSecurityQueries = inputDAO.generateSecurityQueriesXML(cisServerConfig, regression, regressionQueries, regressionSecurityQueries, queryMode);
							
							// Set the Regression Security Queries
							regressionSecurity.setRegressionSecurityQueries(regressionSecurityQueries);

							// Update the Regression Security XML
							regressionModule.setRegressionSecurity(regressionSecurity);
							// Write the Regression Module XML back out to the target file.
							writeRegressionXMLFile(prefix, regressionModule, pathToTargetRegressionXML, 
									flattenSecurityUsersXML, flattenSecurityQueryQueriesXML, flattenSecurityQueryProceduresXML, flattenSecurityQueryWebServicesXML, flattenSecurityPlansXML);
						} // if (queryMode != null && queryMode.length() > 0 && ...

						/************************
						 * Generate the plans
						 ************************/
						// OVERWRITE = Overwrite all plans thus creating a new plan
						// APPEND = Keep the original plans and create a new plan that is a Cartesian product of users and queries
						// SINGLEPLAN = Generate the Cartesian plan as a single plan
						// MULTIPLAN = Generate the Cartesian plan as multiple plans for each user who has the same set of queries
						if (planMode != null && planMode.length() > 0 && (planMode.equalsIgnoreCase("OVERWRITE") || planMode.equalsIgnoreCase("APPEND")) ) 
						{	
							// Initialize variables
							int planCount = 0;
							
							// Get the current regression security plans and determine whether to OVERWRITE or APPEND
							RegressionSecurityPlansType regressionSecurityPlans = new RegressionSecurityPlansType();
							if (planMode.equalsIgnoreCase("APPEND") && regressionSecurity.getRegressionSecurityPlans() != null && 
									regressionSecurity.getRegressionSecurityPlans().getRegressionSecurityPlan() != null &&
									regressionSecurity.getRegressionSecurityPlans().getRegressionSecurityPlan().size() > 0) 
							{
								regressionSecurityPlans.getRegressionSecurityPlan().addAll(regressionSecurity.getRegressionSecurityPlans().getRegressionSecurityPlan());
								planCount = regressionSecurityPlans.getRegressionSecurityPlan().size();
							}
							
					        CommonUtils.writeOutput("---------------------------------------------------",	prefix,"-debug2",logger,debug1,debug2,debug3);
					        CommonUtils.writeOutput("Generate plans with planMode="+planMode+" and planModeType="+planModeType,	prefix,"-debug2",logger,debug1,debug2,debug3);
					        CommonUtils.writeOutput("---------------------------------------------------",	prefix,"-debug2",logger,debug1,debug2,debug3);

							// Get the list of plan ids
							String planIds = "";
							for (int i=0; i < regressionSecurityPlans.getRegressionSecurityPlan().size(); i++) 
							{
								String planId = regressionSecurityPlans.getRegressionSecurityPlan().get(i).getId();
								if (planId != null && planId.length() > 0) {
									planIds = planIds + planId + " ";
								}
							}


							/* Execute the generate Security Plans XML to create or update the security plans creating a Cartesian product of users and queries
							 *	</regressionSecurityPlans>
							 *	 	<regressionSecurityPlan>
							 *			<id>sp2</id>
							 *			<regressionSecurityPlanTest>
							 *				<id>rst1</id><enabled>false</enabled><userId>rsu2</userId><queryId>rsq1</queryId><expectedOutcome>FAIL</expectedOutcome>
							 *			</regressionSecurityPlanTest>
							 * 		</regressionSecurityPlan>
							 * 		...
							 * 	</regressionSecurityPlans>
							 */						
							// Get the list of queries
							List<RegressionSecurityQueryType> regressionSecurityQueryList = null;
							if (regressionSecurity.getRegressionSecurityQueries() != null && regressionSecurity.getRegressionSecurityQueries().getRegressionSecurityQuery() != null) 
							{
								regressionSecurityQueryList = regressionSecurity.getRegressionSecurityQueries().getRegressionSecurityQuery();
							}
							
							// Get the list of users
							List<RegressionSecurityUserType> regressionSecurityUserList = null;
							if (regressionSecurity.getRegressionSecurityUsers() != null && regressionSecurity.getRegressionSecurityUsers().getRegressionSecurityUser() != null) 
							{
								regressionSecurityUserList = regressionSecurity.getRegressionSecurityUsers().getRegressionSecurityUser();
							}
							
							// Continue as long as there are users and queries to build a plan from
							if (regressionSecurityUserList != null && regressionSecurityUserList != null && regressionSecurityUserList.size() > 0 && regressionSecurityUserList.size() > 0) 
							{
								// Initialize variables
								String planId = null;
								RegressionSecurityPlanType regressionSecurityPlanNew = null;
								int rstCount = 0;							
								int planNumber = -1;
								int planTestSize = 0;
								RegressionSecurityPlansType regressionSecurityPlansVerify = new RegressionSecurityPlansType();

								// Create a new SINGLEPLAN for all users
								if (planModeType.equalsIgnoreCase("SINGLEPLAN")) {

									regressionSecurityPlanNew = new RegressionSecurityPlanType();
									
									// Determine the plan id when overwriting for a single plan. OVERWRITE all plans in the RegressionModule.xml.
									if (planMode.equalsIgnoreCase("OVERWRITE")) 
									{
										planId = calculatePlanId(defaultPlanIdPrefix, planIds, planCount);
										planCount = Integer.valueOf(planId.replace(defaultPlanIdPrefix, ""));
										regressionSecurityPlanNew.setId(planId);
								        CommonUtils.writeOutput("Single Plan OVERWRITE - Generate plan with planId="+planId,	prefix,"-debug3",logger,debug1,debug2,debug3);
										
										// Set the regression verification
										regressionSecurityPlansVerify = regressionSecurityPlans;
									}
									
							        // Determine how to append a given plan test:  APPEND the last plan found in the RegressionModule.xml.
									if (planMode.equalsIgnoreCase("APPEND")) 
									{
										int numPlans = 0;
										if (regressionSecurityPlans != null && regressionSecurityPlans.getRegressionSecurityPlan() != null &&
												regressionSecurityPlans.getRegressionSecurityPlan().size() > 0) {
											numPlans = regressionSecurityPlans.getRegressionSecurityPlan().size();
										}
										if (numPlans > 0) {
											planNumber = numPlans - 1;
											regressionSecurityPlanNew = regressionSecurityPlans.getRegressionSecurityPlan().get(planNumber);
											// Initialize the number of plan tests for the last current plan
											if (planNumber >= 0)
												planTestSize = regressionSecurityPlans.getRegressionSecurityPlan().get(planNumber).getRegressionSecurityPlanTest().size();
											
											planId = regressionSecurityPlanNew.getId();
										    CommonUtils.writeOutput("Single plan APPEND - Append existing plan with planId="+planId,	prefix,"-debug3",logger,debug1,debug2,debug3);												
											
											// Add only the last plan in the list for verification purposes
											regressionSecurityPlansVerify.getRegressionSecurityPlan().add(regressionSecurityPlanNew);
										} else {
											// Calculate a new plan id
											planId = calculatePlanId(defaultPlanIdPrefix, planIds, planCount);
											planCount = Integer.valueOf(planId.replace(defaultPlanIdPrefix, ""));
											regressionSecurityPlanNew.setId(planId);
										    CommonUtils.writeOutput("Single plan APPEND - Generate new plan with planId="+planId,	prefix,"-debug3",logger,debug1,debug2,debug3);												
											regressionSecurityPlansVerify = regressionSecurityPlans;
										}
									}
								}

								// Create the Cartesian product of users and queries
								for (RegressionSecurityUserType regressionSecurityUser : regressionSecurityUserList)
								{
									// Get the associated groups for this user
									GroupList groupList = null;
									if (planGenerateExpectedOutcome) {
										groupList = getGroupDAO().getGroupsByUser(regressionSecurityUser.getUserName(), regressionSecurityUser.getDomain(), serverId, pathToServersXML);
									}

									// Create MULTIPLE PLANS, one for each user
									if (planModeType.equalsIgnoreCase("MULTIPLAN")) {

										regressionSecurityPlanNew = new RegressionSecurityPlanType();

										// Determine the plan id when overwriting for a multi-plan.  OVERWRITE all plans in the Regression.xml.
										if (planMode.equalsIgnoreCase("OVERWRITE")) 
										{
											planId = calculatePlanId(defaultPlanIdPrefix, planIds, planCount);
											planCount = Integer.valueOf(planId.replace(defaultPlanIdPrefix, ""));
											regressionSecurityPlanNew.setId(planId);
									        CommonUtils.writeOutput("Multi-plan OVERWRITE - Generate plan with planId="+planId,	prefix,"-debug3",logger,debug1,debug2,debug3);
											
											// Set the regression verification
											regressionSecurityPlansVerify = regressionSecurityPlans;
										}
										
								        // Determine how to append a given plan test:  If the userId exists in this plan then APPEND the query if it does not already exist.  Create new plans as needed for different users.
										if (planMode.equalsIgnoreCase("APPEND")) 
										{
											planNumber = -1;
											planTestSize = 0;
											// Initialize the number of plan tests for this user id
											planNumber = planNumberInSecurityRegressionPlans(regressionSecurityUser.getId(), regressionSecurityPlans);
											if (planNumber >= 0) {
												regressionSecurityPlanNew = regressionSecurityPlans.getRegressionSecurityPlan().get(planNumber);
												planTestSize = regressionSecurityPlanNew.getRegressionSecurityPlanTest().size();
												planId = regressionSecurityPlanNew.getId();
											    CommonUtils.writeOutput("Multi-plan APPEND - Append existing plan with planId="+planId,	prefix,"-debug3",logger,debug1,debug2,debug3);												
												
												// Add only the last plan in the list for verification purposes
												regressionSecurityPlansVerify.getRegressionSecurityPlan().add(regressionSecurityPlanNew);
											} 
											else
											{
												// Calculate a new plan id
												planId = calculatePlanId(defaultPlanIdPrefix, planIds, planCount);
												planCount = Integer.valueOf(planId.replace(defaultPlanIdPrefix, ""));
												regressionSecurityPlanNew.setId(planId);
											    CommonUtils.writeOutput("Multi-plan APPEND - Generate new plan with planId="+planId,	prefix,"-debug3",logger,debug1,debug2,debug3);												
												
												// Set the regression verification
												regressionSecurityPlansVerify = regressionSecurityPlans;
											}
										}
									}
									
									// Iterate over the list of queries for each user
									for (RegressionSecurityQueryType regressionSecurityQuery : regressionSecurityQueryList) 
									{
										// Determine if the plan exists or not
										boolean planExists = planExistsInSecurityRegressionPlans(regressionSecurityUser.getId(), regressionSecurityQuery.getId(), regressionSecurityPlansVerify);
										
										if (!planExists) 
										{											
											RegressionSecurityPlanTestType regressionSecurityPlanTest = new RegressionSecurityPlanTestType();
											
											// Increment plan test id
											++rstCount;
											String platTestId = "rst" + (rstCount + planTestSize);
											// Set the regression security plan test id
											regressionSecurityPlanTest.setId(platTestId);
											// Set the plan test to be true
											regressionSecurityPlanTest.setEnabled("true");
											// Set the user id
											regressionSecurityPlanTest.setUserId(regressionSecurityUser.getId());
											// Set the query id
											regressionSecurityPlanTest.setQueryId(regressionSecurityQuery.getId());

											// Get the expected outcome
											String[] result = getExpectedOutcome(planGenerateExpectedOutcome, regressionSecurityUser.getId(), regressionSecurityQuery.getId(), groupList, regressionSecurityUser, regressionSecurityQuery, serverId, pathToServersXML);
											
											// Set the expected outcome
											String expectedOutcome = result[0];
											regressionSecurityPlanTest.setExpectedOutcome(expectedOutcome);
											
											// Set the description
											String description = result[1];
											regressionSecurityPlanTest.setDescription(description);

											// Add the new security plan test
											regressionSecurityPlanNew.getRegressionSecurityPlanTest().add(regressionSecurityPlanTest);
										}
									}
									
									// Add the plan to the list for each user
									if (planModeType.equalsIgnoreCase("MULTIPLAN")) 
									{
										// Only write out the plan if there were plan tests created
										if (rstCount > 0) 
										{
											if (planNumber >= 0) 
											{
												regressionSecurityPlans.getRegressionSecurityPlan().set(planNumber, regressionSecurityPlanNew);
												--planCount;
											} else {
												// Add to the list of Security Plans
												regressionSecurityPlans.getRegressionSecurityPlan().add(regressionSecurityPlanNew);												
											}
											rstCount = 0;
	
											// Set the Regression Security Plans
											regressionSecurity.setRegressionSecurityPlans(regressionSecurityPlans);
											// Update the Regression Security XML
											regressionModule.setRegressionSecurity(regressionSecurity);
											// Write the Regression Module XML back out to the target file.
											writeRegressionXMLFile(prefix, regressionModule, pathToTargetRegressionXML, 
												flattenSecurityUsersXML, flattenSecurityQueryQueriesXML, flattenSecurityQueryProceduresXML, flattenSecurityQueryWebServicesXML, flattenSecurityPlansXML);
									
										} else {
											--planCount;
										}
									}
								}
								// Add the single plan to the list of plans
								if (planModeType.equalsIgnoreCase("SINGLEPLAN")) 
								{
									if (planNumber >= 0) 
									{
										regressionSecurityPlans.getRegressionSecurityPlan().set(planNumber, regressionSecurityPlanNew);
									} else {
										// Add to the list of Security Plans
										regressionSecurityPlans.getRegressionSecurityPlan().add(regressionSecurityPlanNew);
									}
								}
							}

							// Set the Regression Security Plans
							regressionSecurity.setRegressionSecurityPlans(regressionSecurityPlans);
							
						} // if (planMode != null && planMode.length() > 0 && ...

						/********
						 * Commenting out this section of code so that the resourcePath and resourceType are retained in the XML.
						 * When the user decides to do APPEND for plans and generate the expected outcome, then if the resource path and type are not present
						 * then the outcome cannot be generated.
						 * 
						// Remove the resourcePath and resourceType from the XML since this is optional and is only used to establish the plan test expected outcome
						if (regressionSecurity.getRegressionSecurityQueries() != null && 
							regressionSecurity.getRegressionSecurityQueries().getRegressionSecurityQuery() != null && 
							regressionSecurity.getRegressionSecurityQueries().getRegressionSecurityQuery().size() > 0) 
						{
							for (RegressionSecurityQueryType regressionSecurityQuery : regressionSecurity.getRegressionSecurityQueries().getRegressionSecurityQuery()) {
								if (regressionSecurityQuery.getResourcePath() != null)
									regressionSecurityQuery.setResourcePath(null);
								if (regressionSecurityQuery.getResourceType() != null)
									regressionSecurityQuery.setResourceType(null);
							}
						}
						*/
						
						// Update the Regression Security XML
						regressionModule.setRegressionSecurity(regressionSecurity);

					    CommonUtils.writeOutput("---------------------------------------------------",	prefix,"-debug2",logger,debug1,debug2,debug3);
					    CommonUtils.writeOutput("Flatten the XML for table-like viewing.",				prefix,"-debug2",logger,debug1,debug2,debug3);
					    CommonUtils.writeOutput("   Flatten <regressionSecurityUser>                XML: "+flattenSecurityUsersXML,				prefix,"-debug2",logger,debug1,debug2,debug3);
					    CommonUtils.writeOutput("   Flatten <regressionSecurityQuery>[QUERY]        XML: "+flattenSecurityQueryQueriesXML,		prefix,"-debug2",logger,debug1,debug2,debug3);
					    CommonUtils.writeOutput("   Flatten <regressionSecurityQuery>[PROCEDURES]   XML: "+flattenSecurityQueryProceduresXML,	prefix,"-debug2",logger,debug1,debug2,debug3);
					    CommonUtils.writeOutput("   Flatten <regressionSecurityQuery>[WEB_SERVICES] XML: "+flattenSecurityQueryWebServicesXML,	prefix,"-debug2",logger,debug1,debug2,debug3);
					    CommonUtils.writeOutput("   Flatten <regressionSecurityPlanTest>            XML: "+flattenSecurityPlansXML,				prefix,"-debug2",logger,debug1,debug2,debug3);
					    CommonUtils.writeOutput("---------------------------------------------------",	prefix,"-debug2",logger,debug1,debug2,debug3);
						writeRegressionXMLFile(prefix, regressionModule, pathToTargetRegressionXML, 
								flattenSecurityUsersXML, flattenSecurityQueryQueriesXML, flattenSecurityQueryProceduresXML, flattenSecurityQueryWebServicesXML, flattenSecurityPlansXML);
					} // if (SECURITY.equalsIgnoreCase(testType))
					else
					{
						logger.info("Skipping regression id="+identifier+" because the test type is not set to \"security\".");
					} // else
				} // if(DeployUtil.canProcessResource(regressionIds, identifier)){
			} // for (RegressionTestType regression : regressionList) {
		} // if (regressionList != null && regressionList.size() > 0) {
	}

	/****************************
	 * PRIVATE METHODS
	 ****************************/
		
	/**
	 * getSecurityUserByName - Search through the list of security users for the passed in userName.
	 * 
	 * @param userName - user name to search for
	 * @param regressionSecurityUserList - list of security users
	 * @return userName - null if no user found, else the security user object
	 */
	@SuppressWarnings("null")
	// get the requested user from the list of users
	private RegressionSecurityUserType getSecurityUserByName(String userName, List<RegressionSecurityUserType> regressionSecurityUserList) {
		RegressionSecurityUserType regressionSecurityUser = null;

		// Loop over the list of regression security users
		for (RegressionSecurityUserType regressionSecurityUserLoop : regressionSecurityUserList) {
			if (regressionSecurityUserLoop.getId() != null) {
				if (userName.equalsIgnoreCase(regressionSecurityUserLoop.getUserName())) {
					regressionSecurityUser = regressionSecurityUserLoop;
					break;
				}
			}
		}
		return regressionSecurityUser;
	}


	/**
	 * Calculate the plan id
	 * 
	 * @param defaultPlanIdPrefix
	 * @param planIds
	 * @param planCount
	 * @return
	 */
	private String calculatePlanId(String defaultPlanIdPrefix, String planIds, int planCount) {
		// Calculate the planId
		String planId = "";
    	while (true) 
    	{
    		planId = defaultPlanIdPrefix + ++planCount;
    		if (!planIds.toLowerCase().contains(planId)) {
    			break;
    		}
    	} // while (true) 
    	return planId;
	}
	
	/**
	 * Determine if the plan exists in the regression security plans XML
	 * 
	 * @param planId
	 * @param platTestId
	 * @param userId
	 * @param queryId
	 * @param regressionSecurityPlans
	 * @return
	 */
	private boolean planExistsInSecurityRegressionPlans(String userId, String queryId, RegressionSecurityPlansType regressionSecurityPlans) {
		boolean planFound = false;

		if (userId !=null && queryId != null) 
		{
			for (int i=0; i < regressionSecurityPlans.getRegressionSecurityPlan().size(); i++) 
			{			
				if (regressionSecurityPlans.getRegressionSecurityPlan().get(i).getRegressionSecurityPlanTest() != null) {
					List<RegressionSecurityPlanTestType> regressionSecurityPlanTests = regressionSecurityPlans.getRegressionSecurityPlan().get(i).getRegressionSecurityPlanTest();
					for(RegressionSecurityPlanTestType regressionSecurityPlanTest : regressionSecurityPlanTests) 
					{
						String rspUserId = regressionSecurityPlanTest.getUserId();
						String rspQueryId = regressionSecurityPlanTest.getQueryId();
						
						// Look for plan equality
						if (userId.equals(rspUserId) && queryId.equals(rspQueryId)) {
							planFound = true;
							break;
						}
					}
					if (planFound)
						break;
				}
			}
		}
		return planFound;
	}

	/**
	 * Determine if the plan exists in the regression security plans XML
	 * 
	 * @param planId
	 * @param platTestId
	 * @param userId
	 * @param queryId
	 * @param regressionSecurityPlans
	 * @return
	 */
	private int planNumberInSecurityRegressionPlans(String userId, RegressionSecurityPlansType regressionSecurityPlans) {
		int planNumber = -1;

		if (userId !=null) 
		{
			for (int i=0; i < regressionSecurityPlans.getRegressionSecurityPlan().size(); i++) 
			{			
				if (regressionSecurityPlans.getRegressionSecurityPlan().get(i).getRegressionSecurityPlanTest() != null) {
					List<RegressionSecurityPlanTestType> regressionSecurityPlanTests = regressionSecurityPlans.getRegressionSecurityPlan().get(i).getRegressionSecurityPlanTest();
					for(RegressionSecurityPlanTestType regressionSecurityPlanTest : regressionSecurityPlanTests) 
					{
						String rspUserId = regressionSecurityPlanTest.getUserId();
						
						// Look for plan equality
						if (userId.equals(rspUserId)) {
							planNumber = i;
							break;
						}
					}
					if (planNumber >=0)
						break;
				}
			}
		}
		return planNumber;
	}

	
	/**
	 * Determine the expected outcome for a given user and query based on the privileges for the resource path of the query and user.
	 * 
	 * @param userId
	 * @param queryId
	 * @param regressionSecurityUser
	 * @param regressionSecurityQuery
	 * @return
	 */
	private String[] getExpectedOutcome(boolean planGenerateExpectedOutcome, String userId, String queryId, GroupList groups, RegressionSecurityUserType regressionSecurityUser, RegressionSecurityQueryType regressionSecurityQuery, String serverId, String pathToServersXML) {
		
		// Initialize result
		String[] result = new String[2];
		
		// Get the user information
		String userName = regressionSecurityUser.getUserName();
		String domainName = regressionSecurityUser.getDomain();
		
		// Get the query information
		String resourcePath = regressionSecurityQuery.getResourcePath();
		String resourceType = regressionSecurityQuery.getResourceType();
		
		// Warning
		if (resourcePath == null || resourceType == null)
			logger.warn("Unable to generate the expected outcome because the resource Path or Type is null for queryId="+queryId);
		else if (resourcePath.length() == 0 || resourceType.length() == 0)
			logger.warn("Unable to generate the expected outcome because the resource Path or Type is empty for queryId="+queryId);
		
		// Initialize the result description
		result[1] = userName + " :: " + resourcePath;
		
		// Initialize the expected outcome
		String expectedOutcome = "";

		if (planGenerateExpectedOutcome && resourcePath != null && resourceType != null) 
		{				
			// Retrieve the Resource Privileges
			PrivilegeEntries privilegeEntries = getPrivileges(resourcePath, resourceType, serverId, pathToServersXML);
				
			expectedOutcome = "FAIL";
			// Loop through the list of resource privileges
			for (PrivilegeEntry privs : privilegeEntries.getPrivilegeEntry()) 
			{
				for (Privilege priv : privs.getPrivileges().getPrivilege()) 
				{
					String privDomain = priv.getDomain();
					String privName = priv.getName();
					String privType = priv.getNameType().toString();
					String privileges = priv.getPrivs();
					
					String resolvedPrivs = privileges;
					//String combinedPrivs = priv.getCombinedPrivs();
					//if (privileges.equals("NONE") && !combinedPrivs.equals("NONE"))
					//	resolvedPrivs = combinedPrivs;
			
					if (privType.equalsIgnoreCase("USER") && userName.equals(privName) && domainName.equals(privDomain)) 
					{
						if (resourceType.equalsIgnoreCase("LINK") && resolvedPrivs.contains("READ")) {
							expectedOutcome = "PASS";
							break;
						}
						if (resourceType.equalsIgnoreCase("TABLE") && resolvedPrivs.contains("READ") && resolvedPrivs.contains("SELECT")) {
							expectedOutcome = "PASS";
							break;
						}
						if (resourceType.equalsIgnoreCase("PROCEDURE") && resolvedPrivs.contains("READ") && resolvedPrivs.contains("EXECUTE")) {
							expectedOutcome = "PASS";
							break;
						}
					}
					if (privType.equalsIgnoreCase("GROUP")) 
					{
						for (Group group : groups.getGroup()) {
							String groupName = group.getName();
							String groupDomain = group.getDomainName();
	
							if (groupName.equals(privName) && groupDomain.equals(privDomain)) {
								if (resourceType.equalsIgnoreCase("LINK") && resolvedPrivs.contains("READ")) {
									expectedOutcome = "PASS";
									break;					
								}
								if (resourceType.equalsIgnoreCase("TABLE") && resolvedPrivs.contains("READ") && resolvedPrivs.contains("SELECT")) {
									expectedOutcome = "PASS";
									break;
								}
								if (resourceType.equalsIgnoreCase("PROCEDURE") && resolvedPrivs.contains("READ") && resolvedPrivs.contains("EXECUTE")) {
									expectedOutcome = "PASS";
									break;
								}
							}
						}
						if (expectedOutcome.equalsIgnoreCase("PASS")) {
							break;
						}
					}
					if (expectedOutcome.equalsIgnoreCase("PASS")) {
						break;
					}
				}
				if (expectedOutcome.equalsIgnoreCase("PASS")) {
					break;
				}
			}
		}

		// Initialize the result expected outcome
		result[0] = expectedOutcome;
		
		return result;
	}
	
	/**
	 * Retrieve the privilege entries from an array or from the server.
	 * 
	 * @param resourcePath
	 * @param resourceType
	 * @param serverId
	 * @param pathToServersXML
	 * @return
	 */
	private PrivilegeEntries getPrivileges(String resourcePath, String resourceType, String serverId, String pathToServersXML) {
		
		PrivilegeEntries privilegeEntries = null;
		String key = resourcePath + " " + resourceType;
			
		boolean foundInList = false;
		if (privilegeEntriesList != null && privilegeEntriesList.size() > 0) {
			if (privilegeEntriesList.containsKey(key)) 
			{
				privilegeEntries = privilegeEntriesList.get(key);
				foundInList = true;
			}
		}

		if (!foundInList) {
			// Get the associated privileges for this query 
			Entries entries = new Entries();
			String filter = null;
			boolean includeColumnPrivileges = false;
			
			PathTypeOrColumnPair pathPair = new PathTypeOrColumnPair();
			pathPair.setPath(resourcePath);
			pathPair.setType(ResourceOrColumnType.valueOf(resourceType));
			entries.getEntry().add(pathPair);
	
			// Retrieve the Resource Privileges
			privilegeEntries = getPrivilegeDAO().getResourcePrivileges(entries, filter, includeColumnPrivileges, serverId, pathToServersXML);
			
			// Put the object in the privilege list
			privilegeEntriesList.put(key, privilegeEntries);
		}
		return privilegeEntries;
	}
	/**
	 * Write out the Regression Module XML File and flatten the regression XML as directed by the user input.
	 * 
	 * @param prefix
	 * @param regressionModule
	 * @param pathToTargetRegressionXML
	 * @param flattenSecurityUsersXML
	 * @param flattenSecurityQueryQueriesXML
	 * @param flattenSecurityQueryProceduresXML
	 * @param flattenSecurityQueryWebServicesXML
	 * @param flattenSecurityPlansXML
	 * 
	 * @return
	 */
	private void writeRegressionXMLFile(String prefix, RegressionModule regressionModule, String pathToTargetRegressionXML, 
			boolean flattenSecurityUsersXML, boolean flattenSecurityQueryQueriesXML, boolean flattenSecurityQueryProceduresXML, boolean flattenSecurityQueryWebServicesXML, boolean flattenSecurityPlansXML) {

	// Write the Regression Module XML back out to the target file.
	XMLUtils.createXMLFromModuleType(regressionModule, pathToTargetRegressionXML);
	
	// Parse the XML file and flatten out the XML for Users and Plans so that they don't take up as much real-estate and are easier to view
	StringBuilder stringBuilder = new StringBuilder();
	StringBuilder queryBuilderWithSep = null;
	StringBuilder queryBuilderNoSep = null;					
	boolean processingUsers = false;
	boolean processingQueries = false;
	boolean processingQueryType = false;
	boolean processingProcType = false;
	boolean processingWSType = false;
	boolean processingPlans = false;
	String begUserNode = "<regressionSecurityUser>";
	String endUserNode = "</regressionSecurityUser>";
	String begQueryNode = "<regressionSecurityQuery>";
	String endQueryNode = "</regressionSecurityQuery>";
	String begPlanNode = "<regressionSecurityPlanTest>";
	String endPlanNode = "</regressionSecurityPlanTest>";	
	String queryTypeNode = "<queryType>";
	int userTrimCount = 0;
	int queryTrimCount = 0;
	int planTrimCount = 0;
	try {
		BufferedReader reader = new BufferedReader(new FileReader(pathToTargetRegressionXML));
		String line = null;
		String trimLine = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) 
		{
			// Begin node found for users: <regressionSecurityUser>
			if (line.contains(begUserNode) && flattenSecurityUsersXML)
				processingUsers = true;
			// Count the number of lines (nodes) found for users
			if (processingUsers)
				++userTrimCount;
			
			// Begin node found for queries: <regressionSecurityQuery>
			if (line.contains(begQueryNode) && (flattenSecurityQueryQueriesXML || flattenSecurityQueryProceduresXML || flattenSecurityQueryWebServicesXML)) {
				processingQueries = true;
				queryBuilderWithSep = new StringBuilder();
				queryBuilderNoSep = new StringBuilder();
			}
			// Count the number of lines (nodes) found for queries
			if (processingQueries) {
				++queryTrimCount;										
				//<queryType>QUERY</queryType>
				if (flattenSecurityQueryQueriesXML && line.contains(queryTypeNode) && line.contains("QUERY")) {
					processingQueryType = true;
				}
				//<queryType>PROCEDURE</queryType>
				if (flattenSecurityQueryProceduresXML && line.contains(queryTypeNode) && line.contains("PROCEDURE")) {
					processingProcType = true;
				}
				//<queryType>WEB_SERVICE</queryType>
				if (flattenSecurityQueryWebServicesXML && line.contains(queryTypeNode) && line.contains("WEB_SERVICE")) {
					processingWSType = true;
				}
			}
			
			// Begin node found for plans: <regressionSecurityPlanTest>
			if (line.contains(begPlanNode) && flattenSecurityPlansXML)
				processingPlans = true;
			// Count the number of lines (nodes) found for plans
			if (processingPlans)
				++planTrimCount;

			// Trim the line so there are no spaces separating nodes
			if (userTrimCount > 1 || planTrimCount > 1 || queryTrimCount > 1) {
				trimLine = line.trim();
			}

			// Add the line to the buffer
			if (processingQueries) {
				queryBuilderWithSep.append(line);
				if (trimLine != null)
					queryBuilderNoSep.append(trimLine);
				else
					queryBuilderNoSep.append(line);
			} else if (processingUsers || processingPlans) { 
				if (userTrimCount > 1 || planTrimCount > 1) {
					stringBuilder.append(trimLine);
				} else{
					stringBuilder.append(line);
				}
			} else{
				stringBuilder.append(line);
			}
			
			// End node found for users: <regressionSecurityUser>
			if (line.contains(endUserNode) && flattenSecurityUsersXML) {
				processingUsers = false;
				userTrimCount = 0;
				trimLine = null;
			}
			// End node found for queries: <regressionSecurityQuery>
			if (line.contains(endQueryNode) && (flattenSecurityQueryQueriesXML || flattenSecurityQueryProceduresXML || flattenSecurityQueryWebServicesXML)) {
				if (processingQueryType) {
					stringBuilder.append(queryBuilderNoSep.toString());
					processingQueryType = false;
				}
				else if (processingProcType) {
					stringBuilder.append(queryBuilderNoSep.toString());
					processingProcType = false;
				} 
				else if (processingWSType) {
					stringBuilder.append(queryBuilderNoSep.toString());
					processingWSType = false;
				} else {
					stringBuilder.append(queryBuilderWithSep.toString());										
				}
				processingQueries = false;	
				queryTrimCount = 0;
				queryBuilderWithSep = null;
				queryBuilderNoSep = null;
				trimLine = null;
			}
			// End node found for plans: <regressionSecurityPlanTest>
			if (line.contains(endPlanNode) && flattenSecurityPlansXML) {
				processingPlans = false;									
				planTrimCount = 0;
				trimLine = null;
			}
			// Only write out a line separator when both processingUsers=false and processingPlans=false and processingQueries=false
			//   In other words, users, queries and plans are not being processed in this iteration so write out a line separator.
			if (!processingUsers && !processingPlans && !processingQueries) {
				stringBuilder.append(ls);
			} else 	if (processingQueries) {
				queryBuilderWithSep.append(ls);
			}
		}
	} catch (FileNotFoundException e) {
		throw new CompositeException(e.getMessage(),e);
	} catch (IOException e) {
		throw new CompositeException(e.getMessage(),e);
	}
	CommonUtils.createFileWithContent(pathToTargetRegressionXML, stringBuilder.toString());
}
	
	/**
	 * getQueryExecLog - Derive a query execution log object from a line extracted from the query execution log.
	 * 
	 * @param line
	 * @param delimiter
	 * @return
	 */
	@SuppressWarnings("null")
	private QueryExecLog extractQueryExecLog(String line, String delimiter) {
		QueryExecLog queryLog = new QueryExecLog();
		boolean delimiterFound = false;
		String requiredFieldList = "";

		// Determine if the delimiter is contained within the line.  First check of verification.
		if (line.contains(delimiter)) {
			delimiterFound = true;
		}
		// Parse the line
        String[] fields = line.split("\\"+delimiter);
        
    	// Validate the line to insure the delimiter is present in the line
        if (!delimiterFound) {
        	throw new ValidationException("The delimiter ["+delimiter+"] was not found in the log file content.");
        }
    	// Validate the number of fields expected.  The number of fields should never 1.  This indicates that the delimiter is incorrect.
        if (fields.length <= 1) {
        	throw new ValidationException("The delimiter ["+delimiter+"] resulted in an incorrect number of fields ["+fields.length+"] parsed from the log file ");
        }
     
        // Assign fields to the queryLog record
        for (int i = 0; i < fields.length; i++) {
        	switch (i) {
        	  case 0: queryLog.result = fields[i].trim();  
        	  	if (queryLog.result == null && queryLog.result.length() <= 0) 
        	  		requiredFieldList = requiredFieldList + "result ";
        	  	break;
        	  case 1: queryLog.executionStartTime = fields[i].trim(); 
	      	  	if (queryLog.executionStartTime == null && queryLog.executionStartTime.length() <= 0) 
	       	  		requiredFieldList = requiredFieldList + "executionStartTime ";
        	  	break;
        	  case 2: queryLog.duration = fields[i].trim(); 	
	      	  	if (queryLog.duration == null && queryLog.duration.length() <= 0) 
	       	  		requiredFieldList = requiredFieldList + "duration ";
        	  	break;
        	  case 3: queryLog.rows = fields[i].trim();
        		break;
        	  case 4: queryLog.database = fields[i].trim();
        		break;
        	  case 5: queryLog.query = fields[i].trim();		
        	  	break;
        	  case 6: queryLog.type = fields[i].trim();		
        	  	break;
        	  case 7: queryLog.outputFile = fields[i].trim();		
        	  	break;
        	  case 8: queryLog.message = fields[i].trim();		
        	  	break;
        	  default:
        	}
        	
        	// Validate the required fields expected.  If there was no data for the required fields then throw an exception
            if (requiredFieldList.trim().length() > 0) {
            	throw new ValidationException("The following required fields were not populated with log file data ["+requiredFieldList.trim()+"]");
            }

        	if (queryLog.key == null && queryLog.type != null && queryLog.query != null) {
            	if (queryLog.type.equalsIgnoreCase("QUERY"))
            		queryLog.key = RegressionManagerUtils.constructKey(queryLog.database, RegressionManagerUtils.getTableUrl(queryLog.query), null,null); // Retrieve only the FROM clause table URL with no where clause and no SELECT * FROM projections
            	if (queryLog.type.equalsIgnoreCase("PROCEDURE"))
            		queryLog.key = RegressionManagerUtils.constructKey(queryLog.database, RegressionManagerUtils.getTableUrl(queryLog.query), null,null); // Retrieve only the FROM clause procedure URL with no where clause and no SELECT * FROM projections and no parameters.
            	if (queryLog.type.equalsIgnoreCase("WS")) {
            		queryLog.key = queryLog.query.replaceAll("/", "."); // construct ws path from the path and action combined.
	            	if (queryLog.key.indexOf(".") == 0)
	            		queryLog.key = RegressionManagerUtils.constructKey(queryLog.database, queryLog.key.substring(1), null,null);
            	}
        	}
        	
        }
		return queryLog;
	}
	
	/**
	 * getExactQueryExecLog - retrieve the query log entry based on the passed in key and the query string "Exact Match".
	 * 
	 * @param key
	 * @return
	 */
	private QueryExecLog getExactQueryExecLog(String key, String query, ArrayList<QueryExecLog> queryLogList) {
		QueryExecLog queryLog = null;
		
		for (int i=0; i < queryLogList.size(); i++) {
			if (key.equalsIgnoreCase(queryLogList.get(i).key) && query.equalsIgnoreCase(queryLogList.get(i).query)) {
				queryLog = queryLogList.get(i);
				break;
			}
		}
		return queryLog;
	}
	
	/**
	 * getKeyQueryExecLog - retrieve the query log entry based on the passed in key.
	 * 
	 * @param key
	 * @return
	 */
	private QueryExecLog getKeyQueryExecLog(String key, ArrayList<QueryExecLog> queryLogList) {
		QueryExecLog queryLog = null;
		
		for (int i=0; i < queryLogList.size(); i++) {
			if (key.equalsIgnoreCase(queryLogList.get(i).key)) {
				queryLog = queryLogList.get(i);
				break;
			}
		}
		return queryLog;
	}

	
	/*
	 * Get the regression XML list "RegressionTestType"
	 */
	private List<RegressionTestType> getRegression(String serverId, String regressionIds,	String pathToRegressionXML, String pathToServersXML) {

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToRegressionXML)) {
			throw new CompositeException("File ["+pathToRegressionXML+"] does not exist.");
		}

		// validate incoming arguments
		if(serverId == null || serverId.trim().length() ==0 || regressionIds == null || regressionIds.trim().length() ==0 || pathToRegressionXML.trim().length() ==0){
			throw new ValidationException("Invalid Arguments");
		}

		try {
			//using jaxb convert xml to corresponding java objects
			RegressionModule regressionModule = (RegressionModule)XMLUtils.getModuleTypeFromXML(pathToRegressionXML);
			
			// Get the regression queries
			if (regressionModule.getRegressionQueries() != null) {
				this.regressionQueries = regressionModule.getRegressionQueries();
			}

			// Get the regression tests
			if(regressionModule != null && regressionModule.getRegressionTest() != null && !regressionModule.getRegressionTest().isEmpty()){
				return regressionModule.getRegressionTest();
			}
		} catch (CompositeException e) {
			logger.error("Error while parsing RegressionModule XML: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
		return null;
	}

	/*
	 * Get the regression XML "RegressionSecurityType"
	 */
	private RegressionSecurityType getRegressionSecurity(String serverId, String regressionIds, String pathToRegressionXML, String pathToServersXML) {

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToRegressionXML)) {
			throw new CompositeException("File ["+pathToRegressionXML+"] does not exist.");
		}

		// validate incoming arguments
		if(serverId == null || serverId.trim().length() ==0 || regressionIds == null || regressionIds.trim().length() ==0 || pathToRegressionXML.trim().length() ==0){
			throw new ValidationException("Invalid Arguments");
		}

		try {
			//using jaxb convert xml to corresponding java objects
			RegressionModule regressionModule = (RegressionModule)XMLUtils.getModuleTypeFromXML(pathToRegressionXML);
			
			// Get the regression tests
			if(regressionModule != null && regressionModule.getRegressionSecurity() != null){
				return regressionModule.getRegressionSecurity();
			}
		} catch (CompositeException e) {
			logger.error("Error while parsing RegressionModule XML: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
		return null;
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
	 * Obtains configuration settings from the XML config file servers.xml.
	 * 
	 * @param serverId
	 * @param pathToServersXML
	 */
	private void setCisServerConfig(String serverId, String pathToServersXML)
	{
		if (this.cisServerConfig != null) 	{	return; 	} // the object already exists.
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		if (targetServer == null)
		{
			throw new CompositeException("Composite server configuration was not populated, can't proceed.");
		}
		this.cisServerConfig = targetServer;
	}
	
	/**
	 * 
	 * @param serverId
	 * @param pathToServersXML
	 * @return
	 */
	public CompositeServer getCisServerConfig() throws CompositeException
	{
		if(this.cisServerConfig == null)
		{
			throw new CompositeException("cisServerConfig object is not initialized.");
		}
		return this.cisServerConfig;
	}
	

	/**
	 * Getter method 
	 * 
	 * @return
	 * @throws CompositeException
	 */
	public RegressionTestType getRegressionConfig() throws CompositeException
	{
		if(this.regressionConfig == null)
		{
			throw new CompositeException("regressionConfig object is not initialized.");
		}
		return this.regressionConfig;
	}
	
 //   <property name="regressionInputFileJdbcDAO" ref="regressionInputFileJdbcDAO" />
 //   <property name="regressionPubTestJdbcDAO" ref="regressionPubTestJdbcDAO" />
 //   <property name="regressionPerfTestDAO" ref="regressionPerfTestDAO" />
 //   <property name="regressionSecurityTestDAO" ref="regressionSecurityTestDAO" />

	
	/**
	 * @return the regressionInputFileDAO
	 */
	public RegressionInputFileDAO getRegressionInputFileJdbcDAO()
	{
		if(this.regressionInputFileDAO == null)
		{
			this.regressionInputFileDAO = new RegressionInputFileJdbcDAOImpl();
		}
		return regressionInputFileDAO;
	}

	/**
	 * @param regressionInputFileDAO the regressionInputFileDAO to set
	 */
	public void setRegressionInputFileJdbcDAO(RegressionInputFileDAO inputFileDao)
	{
		this.regressionInputFileDAO = inputFileDao;
	}
	
	/**
	 * @return the testRunDao
	 */
	public RegressionPubTestDAO getRegressionPubTestJdbcDAO()
	{
		if(this.testRunDao == null)
		{
			this.testRunDao = new RegressionPubTestJdbcDAOImpl();
		}
		return testRunDao;
	}

	/**
	 * @param testRunDao the testRunDao to set
	 */
	public void setRegressionPubTestJdbcDAO(RegressionPubTestDAO testDao)
	{
		this.testRunDao = testDao;
	}
	
	/**
	 * @return the perfTestDao
	 */
	public RegressionPerfTestDAO getRegressionPerfTestDAO()
	{
		if(this.perfTestDao == null)
		{
			this.perfTestDao = new RegressionPerfTestDAOImpl();
		}
		return perfTestDao;
	}

	/**
	 * @param perfTestDao the perfTestDao to set
	 */
	public void setRegressionPerfTestDAO(RegressionPerfTestDAO perfTestDao)
	{
		this.perfTestDao = perfTestDao;
	}
	
	/**
	 * @return the securityTestDao  regressionSecurityTestRunDAO
	 */
	public RegressionSecurityTestDAO getRegressionSecurityTestDAO()
	{
		if(this.securityTestDao == null)
		{
			this.securityTestDao = new RegressionSecurityTestDAOImpl();
		}
		return securityTestDao;
	}

	/**
	 * @param securityTestDao the securityTestDao to set
	 */
	public void setRegressionSecurityTestDAO(RegressionSecurityTestDAO securityTestDao)
	{
		this.securityTestDao = securityTestDao;
	}

	/**
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		if(userDAO == null){
			userDAO = new UserWSDAOImpl();
		}
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * @return the userDAO
	 */
	public GroupDAO getGroupDAO() {
		if(groupDAO == null){
			groupDAO = new GroupWSDAOImpl();
		}
		return groupDAO;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}


	/**
	 * @return the privilegeWSDAO
	 */
	public PrivilegeDAO getPrivilegeDAO() {
		if(privilegeDAO == null){
			privilegeDAO = new PrivilegeWSDAOImpl();
		}
		return privilegeDAO;
	}

	/**
	 * @param privilegeWSDAO the privilegeWSDAO to set
	 */
	public void setPrivilegeDAO(PrivilegeDAO privilegeDAO) {
		this.privilegeDAO = privilegeDAO;
	}
	
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
	
	class QueryExecLog
	{
		//Result|ExecutionStartTime|Duration|Rows|Database|Query|Type|OutputFile|Message
		public String key;				 // a unique key contain the query URL
		public String result;			 // The result of the query [SUCCESS,SKIPPED,ERROR]
		public String executionStartTime;// Execution start time
		public String duration; 		 // The duration of the query in the format:
		public String rows;				 // Number of rows processed
		public String database;			 // The datasource identifies which CIS published data source the query belongs to.
		public String query;			 // The query that was executed
		public String type;				 // The type of query [TABLE,PROCEDURE,WS]
		public String outputFile;		 // The location of the ouptput result file
		public String message;			 // Error message
	}
	
}
