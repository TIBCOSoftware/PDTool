/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.Base64EncodeDecode;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.jdbcapi.JdbcConnector;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.deploytool.modules.RegressionDatasourcesType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionQueriesType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionQueryType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionResourcesType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionTestType;

/** 
 * This class is an implementation of RegressionManager that provides common utilities
 * for the Regression Module.
 * 
 * Also see comments for the RegressionManager interface
 * 
 * @author mtinius
 * @since 2012-06-05
 * @modified 
 * 	2013-02-13 (mtinius): added support for variables for all fields in RegressionModule.xml
 *  2013-11-27 (mtinius): resolved resource URLs with spaces and periods.  Added better support for parsing complex FROM clauses.
 *  2014-01-31 (mtinius): added a check for useHttps to override encrypt flag when useHttps=true.
 *  2014-02-03 (mtinius): findResourceMatch() - Fixed resourceURL comparison to use equals when no wildcard "*" is present instead of startsWith.
 *  2014-02-09 (mtinius): executeWS() - added base64 encoding and basic authorization to the URL connection to allow for connections with new users.
 *  2014-06-30 (mtinius): Enhanced parseItems so that the position of the option does not matter and added the option: outputFilename=
 */

public class RegressionManagerUtils {

	private static Log logger = LogFactory.getLog(RegressionManagerUtils.class);

	// Public Regression Module Constants
	public static final int TYPE_QUERY = 1;
    public static final int TYPE_WS = 2;
    public static final int TYPE_PROCEDURE = 3;
    // Private constants
    private static boolean printOutput = true;
    private static AtomicLong numLatency = new AtomicLong();
    private static AtomicLong firstRowLatency = new AtomicLong();
    private static long start;

    /**
     * Parses the pubtest input file into an Item array
     * 
     * @param filePath - the full path to the file
     * @return
     * @throws Exception
     */
    public static RegressionItem[] parseItems(String filePath) throws CompositeException
    {
        int[] lineNum = new int[]{0};
        List items = new ArrayList();

// Read all the lines
        
        List<String> lines = new ArrayList<String>();
        String line;	// trimmed line
        String oline; 	// original line
        try
        {
        	File f = new File(filePath);
        	BufferedReader rd = new BufferedReader(new FileReader(f));
        	while ((line = rd.readLine()) != null)
        	{
        		lines.add(line);
        	}
        	rd.close();
        }
        catch (Exception e)
        {
        	throw new CompositeException("Unable to read the pubtest input file.");
        }
        
        while (lines.size() > 0)
        {
            lineNum[0]++;
            oline = (String)lines.remove(0);
            line = oline.trim();
            if (line.length() == 0 || line.startsWith("#")) 
            {
                continue;
            }
            RegressionItem item = new RegressionItem();
            item.lineNum = lineNum[0];
            if (line.equals("[QUERY]"))
            {
            	/*  Item Class         [QUERY]
            	 *  ----------         ---------------
            	 *  item.type           = TYPE_QUERY       
				 *	item.database       = database=MYTEST
				 *  item.outputFilename = outputFilename=CAT1.SCH1.ViewSales.txt (optional parameter)
				 *	item.input          = SELECT * FROM CAT1.SCH1.ViewSales
				 */
            	item.type = TYPE_QUERY;
            	while (lines.size() > 0 && (
                        ((String)lines.get(0)).startsWith("database=") ||
                        ((String)lines.get(0)).startsWith("outputFilename=")
                        ))
                {
                    if ( ((String)lines.get(0)).startsWith("database=") )
                     	item.database = getAttr(lines, lineNum);
                    if ( ((String)lines.get(0)).startsWith("outputFilename=") )
                     	item.outputFilename = getAttr(lines, lineNum);
                }
             }
            else if (line.equals("[WEB_SERVICE]"))
            {
            	/*  Item Class         [WEB_SERVICE]
            	 *  ----------         ---------------
            	 *  item.type           = TYPE_WS        
				 *	item.database       = database=ProductWebService
				 *	item.path           = path=/soap12/ProductWebService
				 *	item.action         = action=LookupProduct
				 *  item.encrypt        = encrypt=false
				 *	item.contentType    = contentType=application/soap+xml;charset=UTF-8
				 *  item.outputFilename = outputFilename=LookupProduct.txt (optional parameter)
				 *	item.input          = <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:tem="http://tempuri.org/">
				 *			  			      <soap:Header/>
				 *						      <soap:Body>
				 *						         <tem:LookupProduct>
				 *						         <tem:LookupProductDesiredproduct>10</tem:LookupProductDesiredproduct>
				 *						         </tem:LookupProduct>
				 *						      </soap:Body>
				 *						   </soap:Envelope>
            	 */
                item.type = TYPE_WS;
            	while (lines.size() > 0 && (
            			((String)lines.get(0)).startsWith("database=") ||
                    	((String)lines.get(0)).startsWith("path=") ||
                        ((String)lines.get(0)).startsWith("action=") ||
                        ((String)lines.get(0)).startsWith("encrypt=") ||
                        ((String)lines.get(0)).startsWith("contentType=") ||
                        ((String)lines.get(0)).startsWith("outputFilename=")
                                                ))
                {
                    if ( ((String)lines.get(0)).startsWith("database=") )
                     	item.database = getAttr(lines, lineNum);
                    if ( ((String)lines.get(0)).startsWith("path=") )
                     	item.path = getAttr(lines, lineNum);
                    if ( ((String)lines.get(0)).startsWith("action=") )
                     	item.action = getAttr(lines, lineNum);
                    if ( ((String)lines.get(0)).startsWith("encrypt=") )
                    	item.encrypt = Boolean.valueOf(getAttr(lines, lineNum));
                    if ( ((String)lines.get(0)).startsWith("contentType=") )
                     	item.contentType = getAttr(lines, lineNum);
                    if ( ((String)lines.get(0)).startsWith("outputFilename=") )
                     	item.outputFilename = getAttr(lines, lineNum);
               }
            }
            else if (line.equals("[PROCEDURE]")) {
               	/*  Item Class         [PROCEDURE]
            	 *  ----------         ---------------
            	 *  item.type           = TYPE_PROCEDURE       
				 *	item.database       = database=MYTEST
				 *  item.outTypes       = outTypes=INTEGER
				 *  item.outputFilename = outputFilename=CAT1.SCH1.LookupProduct.txt (optional parameter)
				 *	item.input          = SELECT count(*) cnt FROM CAT1.SCH1.LookupProduct( 1  ) 
				 */
                item.type = TYPE_PROCEDURE;
            	while (lines.size() > 0 && (
                        ((String)lines.get(0)).startsWith("database=") ||
                    	((String)lines.get(0)).startsWith("outTypes=") ||
                        ((String)lines.get(0)).startsWith("outputFilename=")
                                           	))
               {
            	   if ( ((String)lines.get(0)).startsWith("database=") )
                    	item.database = getAttr(lines, lineNum);
            	   if ( ((String)lines.get(0)).startsWith("outTypes=") )
            		   item.outTypes = getAttr(lines, lineNum).split(",");
                   if ( ((String)lines.get(0)).startsWith("outputFilename=") )
                    	item.outputFilename = getAttr(lines, lineNum);
               }
            }
            else
            {
                error(lineNum[0], line, "Expected [QUERY] or [PROCEDURE] or [WEB_SERVICE]");
            }

            // Get input "item.input"
            StringBuffer buf = new StringBuffer();
            while (lines.size() > 0) {
                lineNum[0]++;
                oline = (String)lines.remove(0);
                line = oline.trim();
                if (line.length() == 0) {
                    break;
                }
                if (line.length() > 0 && !line.startsWith("#")) {
	                buf.append(oline+"\n");
                }
            }
            item.input = buf.toString();
            if (item.input.length() == 0) {
                error(lineNum[0], null, "No input");
            }
            items.add(item);
        }
        
        // Validate input file required fields
        for (int i=0; i < items.size(); i++) {
        	RegressionItem item = (RegressionItem) items.get(i);
        	if (item.type == TYPE_QUERY) {
            	if (item.database == null || item.database.isEmpty() || item.database.length() <= 0) {
                	throw new CompositeException("The input file category [QUERY] and entry [database=] cannot be null or empty.");        		
            	}
            	if (item.input == null || item.input.isEmpty() || item.input.length() <= 0) {
                	throw new CompositeException("The input file category [QUERY] and entry containing the query text cannot be null or empty.");        		
            	}
        	}
        	if (item.type == TYPE_PROCEDURE) {
            	if (item.database == null || item.database.isEmpty() || item.database.length() <= 0) {
                	throw new CompositeException("The input file category [PROCEDURE] and entry [database=] cannot be null or empty.");        		
            	}
            	if (item.input == null || item.input.isEmpty() || item.input.length() <= 0) {
                	throw new CompositeException("The input file category [PROCEDURE] and entry containing the procedure text cannot be null or empty.");        		
            	}
        	}
        	if (item.type == TYPE_WS) {
            	if (item.database == null || item.database.isEmpty() || item.database.length() <= 0) {
                	throw new CompositeException("The input file category [WEB_SERVICE] and entry [database=] cannot be null or empty.");        		
            	}
            	if (item.input == null || item.input.isEmpty() || item.input.length() <= 0) {
                	throw new CompositeException("The input file category [WEB_SERVICE] and entry containing the web service input text cannot be null or empty.");        		
            	}
            	if (item.path == null || item.path.isEmpty() || item.path.length() <= 0) {
                	throw new CompositeException("The input file category [WEB_SERVICE] and entry [path=] cannot be null or empty.");        		
            	}
            	if (item.action == null || item.action.isEmpty() || item.action.length() <= 0) {
                	throw new CompositeException("The input file category [WEB_SERVICE] and entry [action=] cannot be null or empty.");        		
            	}
            	if (item.contentType == null || item.contentType.isEmpty() || item.contentType.length() <= 0) {
                	throw new CompositeException("The input file category [WEB_SERVICE] and entry [contentType=] cannot be null or empty.");        		
            	} else {
            		// The content type must be one or the other
            		if (!item.contentType.equalsIgnoreCase("text/xml;charset=UTF-8") && !item.contentType.equalsIgnoreCase("application/soap+xml;charset=UTF-8")) {
            			throw new CompositeException("The input file category [WEB_SERVICE] and entry [contentType=] must contain one of the following text entries [text/xml;charset=UTF-8 or application/soap+xml;charset=UTF-8].");  
            		}
            	}
        	}
        }

        return (RegressionItem[])items.toArray(new RegressionItem[items.size()]);
    }

 
    /**
     * Parses a line of the form a=b and return b.
     */
    private static String getAttr(List<String> lines, int[] lineNum) throws CompositeException
    {
        lineNum[0]++;
        if (lines.size() == 0)
        {
            error(lineNum[0], null, "getAttr(): Unexpected EOF");
        }
        String line = (String)lines.remove(0);
        if (line.indexOf("=") < 0)
        {
            error(lineNum[0], line, "getAttr(): Syntax error");
        }
        String[] ss = line.split("=",2);
        if (ss.length == 1)
        {
            return "";
        } else {
        	ss[1] = ss[1].trim();
        }
        return ss[1];
    }

	/**
	 * Converts boolean configuration parameters from String to boolean.
	 * Most of the boolean configuration parameters for this module accept 
	 * "yes" and "true" String values which correspond to true. "no" and "false"
	 * correspond to false.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean checkBooleanConfigParam(String value) throws CompositeException
	{
		if (value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true"))
		{
			return true;
		}
		else if (value.equalsIgnoreCase("no") || value.equalsIgnoreCase("false"))
		{
			return false;
		}
		else
		{
			throw new CompositeException("Unexpected config parameter value " + value +
					". Should be one of {true, false, yes, no}.");
		}
	}

	/**
	 * error - print out the error line.
	 * 
	 * @param lineNum
	 * @param line
	 * @param error
	 */
    public static void error(int lineNum, String line, String error) {
        System.err.println("------------- PARSING ERROR -------------");
        System.err.println(error);
        System.err.println("Line #"+lineNum+": "+line);
        throw new CompositeException(error);
    }

    /**
     * get the from clause for a view and the procedure path without the parameters if it is a procedure.
     * 
     * Examples:
     *   incoming query string										    outgoing result
     *   -----------------------										----------------
     *   SELECT COUNT(*) FROM VIEW1									--> VIEW1
     *   SELECT COUNT(*) FROM CAT1.SCH1.VIEW1						--> CAT1.SCH1.VIEW1
     *   SELECT COUNT(*) FROM CAT1.SCH1.VIEW1 WHERE X=1				--> CAT1.SCH1.VIEW1
     *   SELECT COUNT(*) FROM SCH1.LookupProcedure ( 1 ) 			--> SCH1.LookupProcedure
     *   SELECT COUNT(*) FROM SCH1.LookupProcedure ( 1 )  WHERE x=1	--> SCH1.LookupProcedure
     *   { CALL SCH1.LookupProcedure ( 1 ) }						--> SCH1.LookupProcedure
     *   
     * @param query
     * @return tableURL
     */
    public static String getTableUrl(String query) {
    	String tableUrl = null;
    	int regexSize = 102400;
    	Pattern p = null;
    	Matcher m = null;
    	
    	if (query.toUpperCase().indexOf("FROM") > 0) {
    		String fromClause = query.substring(query.toUpperCase().indexOf("FROM")+4).trim();

	        // Create a pattern to match a space within double quotes
	        p = Pattern.compile(" "+"(?=[^\"]{0,"+regexSize+"}\"(?:[^\"\r\n]{0,"+regexSize+"}\"[^\"]{0,"+regexSize+"}\"){0,"+regexSize+"}[^\"\r\n]{0,"+regexSize+"}$)");
	        // Create a matcher with an input string
	        m = p.matcher(fromClause);
    		// Encode all spaces within double quotes "" with _0020
	        fromClause = m.replaceAll("_0020");

	        // Create a pattern to match a period within double quotes
	        p = Pattern.compile("\\."+"(?=[^\"]{0,"+regexSize+"}\"(?:[^\"\r\n]{0,"+regexSize+"}\"[^\"]{0,"+regexSize+"}\"){0,"+regexSize+"}[^\"\r\n]{0,"+regexSize+"}$)");
	        // Create a matcher with an input string
	        m = p.matcher(fromClause);
    		// Encode all periods within double quotes "" with _002e
	        fromClause = m.replaceAll("_002e");

    		//Loop through the fromClause and replace all occurrences of white space before and after a period separator
        	boolean found = true;
    		while (found) {
    			if (fromClause.contains(" .") || fromClause.contains(". ")) {

			        // Create a pattern to match " ."
			        p = Pattern.compile(" \\.");
			        // Create a matcher with an input string
			        m = p.matcher(fromClause);
    	    		// Replace all white space before a period
    				fromClause = m.replaceAll("\\.");

			        // Create a pattern to match ". "
			        p = Pattern.compile("\\. ");
			        // Create a matcher with an input string
			        m = p.matcher(fromClause);
    	    		// Replace all white space after a period
			        fromClause = m.replaceAll("\\.");
    				found = true;
    			} else {
    				found = false;
    			}  			
    		}
    		// Extract the from clause
    		int pos = fromClause.indexOf(" ");
    	   	if (pos >= 0) {
    	   		tableUrl = fromClause.substring(0, pos).trim();
     	   	} else {
    	   		tableUrl = fromClause.trim();
    	   	}

    	   	// Extract the procedure name from the open parenthesis
    	   	int parenPos = tableUrl.indexOf("(");
    	   	if (parenPos >= 0) {
    	   		tableUrl = tableUrl.substring(0, parenPos).trim();
    	   	}

    	   	// Decode all encoded spaces _0020 with a space " "
    	   	tableUrl = tableUrl.replaceAll("_0020", " ");
    	   	// Decode all encoded periods _002e with a period "."
    	   	tableUrl = tableUrl.replaceAll("_002e", ".");
}
    	if (query.toUpperCase().indexOf("CALL") > 0) {
    		String proc = query.substring(query.toUpperCase().indexOf("CALL")+4).trim();
    	   	int pos = proc.indexOf("(");
    	   	if (pos >= 0) {
    	   		tableUrl = proc.substring(0, pos).trim();
    	   	}
    	}
    	return tableUrl;
    }

    /**
     * get the procedure with its parameters.
     * 
     * Examples:
     *   incoming query string										outgoing result
     *   -----------------------									----------------
     *   SELECT * FROM SCH1.LookupProcedure ( 1 ) 				--> SCH1.LookupProcedure ( 1 )
     *   SELECT * FROM SCH1.LookupProcedure ( 1 )  WHERE x=1	--> SCH1.LookupProcedure ( 1 )
     *   { CALL SCH1.LookupProcedure ( 1 ) }					--> SCH1.LookupProcedure ( 1 )
     *   
     * @param query
     * @return procedure - return the procedure and parameters or the original query if no SELECT or CALL is found
     */
    public static String getProcedure(String query) {
    	String procedure = query;  // return the original string if no condition is met.
    	
    	if (query != null) {
	    	query = query.trim();
	    	
			// { CALL SCH1.LookupProduct( 3 ) } --> SCH1.LookupProduce( 3 )
	    	if (query.toUpperCase().indexOf("{") >= 0 && query.toUpperCase().indexOf("CALL") > 0 && query.toUpperCase().indexOf("}") > 0) 
	    	{
	    		int pos = query.toUpperCase().indexOf("CALL");
	    		procedure = query.replaceAll("}", "").substring(pos+4).trim();
	    	} 
	    	// SELECT * FROM SCH1.LookupProcedure ( 1 )  WHERE x=1	--> SCH1.LookupProcedure ( 1 )
	    	else if (query.toUpperCase().indexOf("SELECT") >= 0 && query.toUpperCase().indexOf("FROM") > 0) 
	    	{
	    		String fromClause = query.substring(query.toUpperCase().indexOf("FROM")+4).trim();
	    	   	int wherePos = fromClause.toUpperCase().indexOf("WHERE");
	    	   	if (wherePos >= 0) {
	    	   		procedure = fromClause.substring(0, wherePos).trim();
	    	   	} else {
	    	   		procedure = fromClause.trim();
	    	   	}
	    	}
    	}
   	
    	return procedure;
    }



	
	/**
	 * Creates a String with comma-separated list of datasources from a RegressionDatasourcesType List.   
	 * 
	 * @param dsList - list of comma-separated datasources.
	 * @param propertyFile - the name of the PDTool property file being used by this invocation
	 * @return dsListStr - comma-separate list of datasources.
	 */
	public static String createDsListString(RegressionDatasourcesType dsList, String propertyFile) throws CompositeException
	{
		String dsListStr = null;

		

		if (dsList != null) {
			List<String> dsListArray = dsList.getDsName();
			StringBuffer buf = new StringBuffer();
			for(String ds : dsListArray)
			{
				if(!ds.isEmpty()) {	buf.append("'" + CommonUtils.extractVariable("createDsListString", ds, propertyFile, false) + "'" + ",");	}
			}
			if (buf.length() > 1)  // at least one ds is populated
			{
				buf.deleteCharAt(buf.length()-1);	// remove the last comma
			}
			dsListStr = buf.toString();
		}
		return dsListStr;
	}
	
	/** 
	 * findResourceMatch - Determine if the specific resource should be compared by checking the XML resource list.
	 * If the resourceURL pattern matches what is in this list then process it.
	 * 		<resources>
	 *			<resource>TEST1.*</resource>
	 *			<resource>TEST1.SCH.*</resource>
	 *			<resource>TEST1.SCH.VIEW1</resource>
	 *		</resources>
	 *
	 * @param resourceURL - this is the published resource URL such as CAT.SCHEMA.VIEW
	 * @param resources - this is an XML list of resources as shown above
	 * @param propertyFile - the name of the PDTool property file being used by this invocation
	 * @return result - the result of the match: true=found, false=not found
	 */
	public static boolean findResourceMatch(String resourceURL, RegressionResourcesType resources, String propertyFile) {
		boolean result = false;
		String indent = CommonConstants.indent;	

		String res = null;
		String resBeg = null;
		String resEnd = null;
		if (resourceURL != null && resources != null) {
			// Apply the reserved list to the path - double quote special characters (periods), embedded spaces and reserved words
			resourceURL = res = CommonUtils.applyReservedListToPath(resourceURL.toUpperCase(), ".");
			
			// Get the resource list
			List<String> resourceList = resources.getResource();
			if (resourceList.size() > 0) {
				for (String resource : resourceList) {
					// Translate any variables into actual values
					resource= CommonUtils.extractVariable("findResourceMatch", resource, propertyFile, false);
					boolean wildCardBeg = false;
					boolean wildCardEnd = false;
					boolean wildCardMid = false;
					
					int pos = resource.indexOf("*");
					res = "";
					if (pos >= 0) {
						if (resource.startsWith("*")) {
							resEnd = encodePeriods(resource.substring(pos+1).toUpperCase());
							wildCardBeg = true;
						}
						else if (resource.endsWith("*")) {
							resBeg = encodePeriods(resource.substring(0, pos).toUpperCase());
							wildCardEnd = true;
						} 
						else {
							resBeg = encodePeriods(resource.substring(0, pos).toUpperCase());
							resEnd = encodePeriods(resource.substring(pos+1).toUpperCase());
							wildCardMid = true;
						}
					} else {
						res = encodePeriods(resource.toUpperCase());
					}

					if (pos >= 0) {
						if (wildCardEnd && resourceURL.startsWith(resBeg)) {
							result = true;
							if (logger.isDebugEnabled()) {
								System.out.println(indent+indent+"findResourceMatch="+result+"   resourceURL=["+resourceURL+"]            Rule: resourceURL.startsWith("+resBeg+")");
							}
							break;
						}
						if (wildCardBeg && resourceURL.endsWith(resEnd)) {
							result = true;
							if (logger.isDebugEnabled()) {
								System.out.println(indent+indent+"findResourceMatch="+result+"   resourceURL=["+resourceURL+"]            Rule: resourceURL.endsWith("+resEnd+")");
							}
							break;
						}
						if (wildCardMid && resourceURL.startsWith(resBeg) && resourceURL.endsWith(resEnd)) {
							result = true;
							if (logger.isDebugEnabled()) {
								System.out.println(indent+indent+"findResourceMatch="+result+"   resourceURL=["+resourceURL+"]            Rule: resourceURL.startsWith("+resBeg+") && resourceURL.endsWith("+resEnd+")");
							}
							break;
						}
					} else {
						if (resourceURL.equals(res)) {
							result = true;
							if (logger.isDebugEnabled()) {
								System.out.println(indent+indent+"findResourceMatch="+result+"   resourceURL=["+resourceURL+"]            Rule: resourceURL.equals("+res+")");
							}
							break;
						}						
					}
					if (logger.isDebugEnabled() && !result) {
						System.out.println(indent+indent+"findResourceMatch="+result+"  resourceURL=["+resourceURL+"]            resourcelist=["+res+"]");
					}
				}
			} else {
				// Allow for the use case when there are no items in the list.
				result = true;
			}
		} else {
			// Allow for the use case when there are no items in the list.
			result = true;
		}
		return result;
	}
	
	private static String encodePeriods(String resource) {
    	int regexSize = 102400;
    	Pattern p = null;
    	Matcher m = null;
    	
        // Create a pattern to match a period within double quotes
        p = Pattern.compile("\\."+"(?=[^\"]{0,"+regexSize+"}\"(?:[^\"\r\n]{0,"+regexSize+"}\"[^\"]{0,"+regexSize+"}\"){0,"+regexSize+"}[^\"\r\n]{0,"+regexSize+"}$)");
        // Create a matcher with an input string
        m = p.matcher(resource);
		// Encode all periods within double quotes "" with _002e
        resource = m.replaceAll("_002e");

		// Apply the reserved list to the path - double quote special characters (periods), embedded spaces and reserved words
        resource = CommonUtils.applyReservedListToPath(resource, ".");
		
		// Decode all encoded periods "_002e" with an actual period "."
        resource = resource.replaceAll("_002e", "\\.");
        
        return resource;
	}
	
	/**
	 *  findDatabaseMatch - Determine if the datasource in the input file is in the datasource list to compare with in the datasource section of the XML
	 *  
	 *  See if items[i].database exists in this list and process if it does.
	 * 			<datasources>
	 *				<dsName>MYTEST</dsName>
	 *				<dsName>testWebService00</dsName>
	 *			</datasources>	
	 * 
	 * @param database - this is the published database name
	 * @param dsList - this is an XML list of datasources as shown above
     * @param propertyFile - the name of the PDTool property file being used by this invocation
	 * @return result - the result of the match: true=found, false=not found
	 */
	public static boolean findDatabaseMatch(String database, RegressionDatasourcesType dsList, String propertyFile) {
		boolean result = false;

		if (database != null && dsList != null) {
			// Remove any double quotes around the database name
			database = database.replaceAll("\"", "");
			
			List<String> datasourceList = dsList.getDsName();
			for (String datasource : datasourceList) {
				
				// Translate any variables into actual values otherwise if no variable just return the actual value passed in
				String db = CommonUtils.extractVariable("findDatabaseMatch", datasource, propertyFile, false);
				if (database.equalsIgnoreCase(db)) { 
					result = true;
				}
			}
		}
		return result;
	}
	
	// Get the delimiter
	public static String getDelimiter(String delimiterType) {
		// Set the default delimiter
		String delimiter = "|";
        // Get output file delimiter
		if ( delimiterType != null) {
	        if (delimiterType.equalsIgnoreCase("COMMA") || delimiterType.equalsIgnoreCase(",")) 
	        	delimiter = ",";
	        if (delimiterType.equalsIgnoreCase("PIPE") || delimiterType.equalsIgnoreCase("|")) 
	        	delimiter = "|";
	        if (delimiterType.equalsIgnoreCase("TAB")) 
	        	delimiter = "\t";
	        if (delimiterType.equalsIgnoreCase("SPACE") || delimiterType.equalsIgnoreCase(" ")) 
	        	delimiter = " ";
	        if (delimiterType.equalsIgnoreCase("TILDE") || delimiterType.equalsIgnoreCase("~")) 
	        	delimiter = "~";
		}
		return delimiter;
	}
	// Write an output message to a log
	public static void writeRegressionLog(String message, String prefix, String options, String regressLogLocation) 
	  throws CompositeException {

		// Determine if there is a prefix to prepend
		if (prefix == null) {
			prefix = "";
		} else {
			prefix = prefix+"::";
		}
		//Write out the log if not suppressed
		if (options == null || !options.contains("-suppress")) {
			
			//Write to log when -summary
			CommonUtils.appendContentToFile(regressLogLocation, message);
		}
	}

	/**
	 * populate the array list with entries from the regression XML RegressionQueries section
	 * 
	 * @param regressionQueries
	 * @param regressionQueryList
     * @param propertyFile - the name of the PDTool property file being used by this invocation
	 * @return ArrayList<RegressionQuery>
	 * @throws CompositeException
	 */
	public static ArrayList<RegressionQuery> populateRegressionQueryList(RegressionQueriesType regressionQueries, ArrayList<RegressionQuery> regressionQueryList, String propertyFile) 
	  throws CompositeException {
		// Populate the reqressionQuery ArrayList
		if (regressionQueries != null) {
			if (regressionQueries.getRegressionQuery() != null) {
				for (int i=0; i < regressionQueries.getRegressionQuery().size(); i++) {
					RegressionQueryType query = regressionQueries.getRegressionQuery().get(i);
					RegressionManagerUtils.addRegressionQueryListEntry(query, regressionQueryList, propertyFile);
				}
			}
		}
		return regressionQueryList;		
	}
	
	/**
	 * Adds a RegressionQuery object to the Map regressionQueryList
	 * If key or value is empty, just skips, no exception is thrown in that case. 
	 * That is because this Map should already have default values populated, so
	 * here we overwrite a value, but only if it is passed. 
	 * 
	 * @param regressionQuery
	 * @param ArrayList<RegressionQuery>
     * @param propertyFile - the name of the PDTool property file being used by this invocation
	 * @return ArrayList<RegressionQuery>
	 */
	public static ArrayList<RegressionQuery> addRegressionQueryListEntry(RegressionQueryType regressionQuery, ArrayList<RegressionQuery> regressionQueryList, String propertyFile) 
	  throws CompositeException {
		RegressionQuery query = new RegressionQuery();
		boolean skip = false;
		String prefix = "addRegressionQueryListEntry";
		
		if (regressionQuery.getDatasource() == null || regressionQuery.getDatasource().length() == 0) 
			skip = true;
		if (regressionQuery.getQuery() == null || regressionQuery.getQuery().length() == 0) 
			skip = true;
		
		if (!skip) {
			// Construct the key: Combination of datasource and SQL FROM or Web Service Path
			// Query key is constructed from SQL FROM clause or CALL statement
			String q = regressionQuery.getQuery().toUpperCase();
			if ( (q.contains("SELECT") || q.contains("CALL")) && !q.contains("SOAP")) {
				if (regressionQuery.getQuery() != null) {
					query.key = constructKey(regressionQuery.getDatasource(), RegressionManagerUtils.getTableUrl(regressionQuery.getQuery()), regressionQuery.getWsAction(),null);	
				}
			// Query key is constructed from Web Service Path by replacing "/" with "."
			} else {
				if (regressionQuery.getWsPath() != null) {
					query.key = constructKey(regressionQuery.getDatasource(), regressionQuery.getWsPath(), regressionQuery.getWsAction(),null);	
				}
			}

			// Set datasource
			query.datasource = CommonUtils.extractVariable(prefix, regressionQuery.getDatasource(), propertyFile, false);
			/* Set the query.  
			 *   A query may have a SQL wildcard % contained in the query.  Therefore, it must be converted to the phrase 
			 *   !S!Q!L!_!W!I!L!D!C!A!R!D! prior to passing it into extractVariable.  The reason is that % is considered 
			 *   a variable designator.  Therefore, the regressionQuery.query string may only use a $ indicator for designating 
			 *   variables and not %.  The % will be interpreted as the SQL wildcard. Once the query is parsed for variables, 
			 *   the !S!Q!L!_!W!I!L!D!C!A!R!D! phase can be translated back to a %.
			 *   
			 *    This is not quite orthodox but it was quick and easy.  
			 *    Just as long as this pattern never shows up in the actual text it will all work. Playing the odds.
			 */
			String replacePercentProperty = "!S!Q!L!_!W!I!L!D!C!A!R!D!";	
			query.query = CommonUtils.extractVariable(prefix, regressionQuery.getQuery().replaceAll("%", replacePercentProperty), propertyFile, false).replaceAll(replacePercentProperty, "%");
	
			// Get optional query parameters
			if (regressionQuery.getDurationDelta() != null && regressionQuery.getDurationDelta().length() > 0) 
				query.durationDelta = CommonUtils.extractVariable(prefix, regressionQuery.getDurationDelta(), propertyFile, false);

			// Get Web Service attributes if not null or empty
			if (regressionQuery.getWsAction() != null && regressionQuery.getWsAction().length() > 0) 
				query.wsAction = CommonUtils.extractVariable(prefix, regressionQuery.getWsAction(), propertyFile, false);
			if (regressionQuery.getWsContentType() != null && regressionQuery.getWsContentType().length() > 0) 
				query.wsContentType = CommonUtils.extractVariable(prefix, regressionQuery.getWsContentType(), propertyFile, false);
			if (regressionQuery.getWsEncrypt() != null && regressionQuery.getWsEncrypt().length() > 0) 
				query.wsEncrypt = CommonUtils.extractVariable(prefix, regressionQuery.getWsEncrypt(), propertyFile, false);
			if (regressionQuery.getWsPath() != null && regressionQuery.getWsPath().length() > 0) 
				query.wsPath = CommonUtils.extractVariable(prefix, regressionQuery.getWsPath(), propertyFile, false);
			
			if (query.key != null && !existRegressionQuery(query.key, regressionQueryList)) {
				regressionQueryList.add(query);
			}
		}
		return regressionQueryList;
	}
	
	public static boolean existRegressionQuery(String key, ArrayList<RegressionQuery> regressionQueryList) {
		boolean result = false;
		
		if (regressionQueryList != null) {
			for (int i=0; i < regressionQueryList.size(); i++) {
				if (key.equalsIgnoreCase(regressionQueryList.get(i).key)) {
					result = true;
				}		
			}
		}
		return result;
	}
	
	public static RegressionQuery getRegressionQuery(String key, ArrayList<RegressionQuery> regressionQueryList) {
		RegressionQuery result = null;
		
		if (regressionQueryList != null) {
			for (int i=0; i < regressionQueryList.size(); i++) {
				if (key.equalsIgnoreCase(regressionQueryList.get(i).key)) {
					result = regressionQueryList.get(i);
				}		
			}
		}
		return result;
	}
	
	/**
	 * constructKey - take in strings and replace any "/" with "."
	 * Example:  s1=TEST,  s2=CAT.SCHEMA.TABLE, s3=null, s4=null                --> TEST.CAT.SCHEMA.TABLE
	 * Example:  s1=TestWebService, s2=/soap11/TestWebService, s3=null, s4=null --> TestWebService.soap11.TestWebService
	 * @param s1 - any string
	 * @param s2 - any string
	 * @param s3 - any string
	 * @param s4 - any string
	 * @return result - a URL with the format of S1.S2.S3.S4
	 */
	public static String constructKey(String s1, String s2, String s3, String s4) {
		String result = "";
    	int regexSize = 102400;
    	Pattern p = null;
    	Matcher m = null;
		
		ArrayList<String> ss = new ArrayList<String>();
		if (s1 != null)
			ss.add(s1);
		if (s2 != null)
			ss.add(s2);
		if (s3 != null)
			ss.add(s3);
		if (s4 != null)
			ss.add(s4);
		
		for (int i=0; i < ss.size(); i++) {
			// Replace all "/" with "." in the string
			String s = ss.get(i).replaceAll("/", ".");

	        // Create a pattern to match a period within double quotes
	        p = Pattern.compile("\\."+"(?=[^\"]{0,"+regexSize+"}\"(?:[^\"\r\n]{0,"+regexSize+"}\"[^\"]{0,"+regexSize+"}\"){0,"+regexSize+"}[^\"\r\n]{0,"+regexSize+"}$)");
	        // Create a matcher with an input string
	        m = p.matcher(s);
			// Encode all periods within double quotes "" with _002e
	        s = m.replaceAll("_002e");

	        // Remove the leading "." in the string
			if (s.indexOf(".") == 0) 
				s = s.substring(1);
			// Add this string to the result with a "." separator
			if (i > 0)
				result = result + ".";
			result = result + s;
		}
		if (result.length() == 0) 
			result = null;
		
		if (result != null) {
	        // Double quote any reserved words or paths containing special characters like spaces or periods
			result = CommonUtils.applyReservedListToPath(result, ".");
			
			// Decode all encoded periods "_002e" with an actual period "."
			result = result.replaceAll("_002e", "\\.");
		}
		return result;
	}
	
    /**
     * Prints and/or logs output messages. 
     *   [verbose,summary,silent] 
     *   	verbose=print summary and results, 
     *   	summary=print query context, 
     *   	silent=nothing is printed to the command line.
     *  
     * @param verboseStr
     * @param nonVerboseStr
     */
    public static void printOutputStr(String printOutputType, String verboseType, String verboseStr, String nonVerboseStr)
    {
    	// Print if verbose not silent.  This overrides all settings.
    	if (!printOutputType.equalsIgnoreCase("silent")) 
    	{
	    	if(printOutput && verboseStr.length() !=0)
	    	{
	    		// Always print if the user chose verbose for printOutput in the RegressionModule.xml (or equivalent)
	    		if (printOutputType.equalsIgnoreCase("verbose")) 
	    		{
	    			System.out.println(verboseStr);
	    		} 
	    		// Only print if the print statement verbose type matches what the user chose in the printOutput attribute in the RegressionModule.xml (or equivalent)
	    		//		Example: printOutputType=summary and verboseType=summary then print
	    		//		Example: printOutputType=summary and verboseType=results then don't print
	    		else if (printOutputType.equalsIgnoreCase(verboseType)) 
	    		{
	    			System.out.println(verboseStr);
	    		}
	    	}
	    	else if(!printOutput && nonVerboseStr.length() != 0)
	    	{	
	    		System.out.println(nonVerboseStr);
	    	}
    	}
    }	
    
	/**
	 * Similar to the same method in original pubtest utility, but doesn't throw an exception if 0 rows are returned
	 * and uses existing(established) JDBC connection corresponding to its published datasource name.
	 * 
	 * @param item
	 * 
	 * @return result - A string containing a formatted response with the rows and first row latency:  <rows>:<firstRowLatency>
	 */
	public static String executeQuery(RegressionItem item, HashMap<String,Connection> cisConnections, String outputFile, String delimiter, String printOutputType) throws CompositeException
	{
		int rows = 0;
		String result = null;
		Connection conn = null;
		Statement stmt = null;  
	    ResultSet rs = null;
    	start = System.currentTimeMillis();
    	long firstRowLatency = 0L;
		    	
		try
		{
		    conn = getJdbcConnection(item.database, cisConnections);  // don't need to check for null here.

			String URL = null;
			String userName = null;
			if (conn.getMetaData() != null) {
				if (conn.getMetaData().getURL() != null)
					URL = conn.getMetaData().getURL();
				if (conn.getMetaData().getUserName() != null)
					userName = conn.getMetaData().getUserName();
			}
			RegressionManagerUtils.printOutputStr(printOutputType, "debug", "RegressionManagerUtils.executeQuery(item, cisConnections, outputFile, delimiter, printOutputType).  item.database="+item.database+"  cisConnections.URL="+URL+"  cisConnections.userName="+userName+"  outputFile="+outputFile+"  delimiter="+delimiter+"  printOutputType="+printOutputType, "");
			RegressionManagerUtils.printOutputStr(printOutputType, "debug", "DEBUG: connection to DB successful", "");

		    stmt = conn.createStatement();
	        stmt.execute(item.input.replaceAll("\n", " "));
			rs = stmt.getResultSet();
	        ResultSetMetaData rsmd = rs.getMetaData();
	        int columns = rsmd.getColumnCount();
	        RegressionManagerUtils.printOutputStr(printOutputType, "debug", "DEBUG: number metadata columns="+columns, "");
	
// Get the column metadata	        
            boolean addSep = false;
            String content = "";
            RegressionManagerUtils.printOutputStr(printOutputType, "debug", "DEBUG: Get column metadata.", "");
	        for (int i=0; i < columns; i++) {
            	if (addSep) {
            		content += delimiter;
            	}
            	if (rsmd.getColumnName(i+1) != null)
            		content += rsmd.getColumnName(i+1).toString();
            	else
            		content += "";
            	addSep = true;
            }
            if (outputFile != null)
            	CommonUtils.appendContentToFile(outputFile, content);
        	RegressionManagerUtils.printOutputStr(printOutputType, "results", content, "");

	        
// Read the values
        	boolean firstRow = true;
        	RegressionManagerUtils.printOutputStr(printOutputType, "debug", "DEBUG: Begin Query Loop.", "");
	        while (rs.next())
	        {
                if (firstRow) {
                	firstRowLatency = System.currentTimeMillis() - start;
    	        	firstRow = false;
    	        	RegressionManagerUtils.printOutputStr(printOutputType, "debug", "DEBUG: Set first row latency time="+firstRowLatency, "");
                }
	            addSep = false;
	            content = "";
                for (int i=0; i<columns; i++) {
                	if (addSep) {
                		content += delimiter;
                	}
                	if (rs.getObject(i+1) != null)
                		content += rs.getObject(i+1).toString();
                	else
                		content += "";
                	addSep = true;
                }
                if (outputFile != null)
                	CommonUtils.appendContentToFile(outputFile, content);
            	RegressionManagerUtils.printOutputStr(printOutputType, "results", content, "");
	           
	            rows++;
	        }        
		} 
		catch (SQLException e)
		{
			RegressionManagerUtils.printOutputStr(printOutputType, "debug", "DEBUG: Exception caught in RegressionManagerUtils.executeQuery:", "");
			RegressionManagerUtils.printOutputStr(printOutputType, "debug", e.getMessage(), "");
			throw new CompositeException("executeQuery(): " + e.getMessage());
		}
		
		finally
		{
			try
			{
				if (rs != null)		{	rs.close();   }	 	
				if (stmt != null) 	{   stmt.close(); }
			}
			catch (SQLException e)
			{
				rs = null; stmt = null;
				throw new CompositeException("executeQuery(): unable to close ResultSet or Statement" + e.getMessage());
			}
		}
		RegressionManagerUtils.printOutputStr(printOutputType, "results", "\nCompleted executeQuery()", "");
		
		// <rows>:<firstRowLatency>
		result = ""+rows+":"+firstRowLatency;
		return result;
/* Note: to process this result string on the client invocation side use the following pattern:
 * 
 * 	String result = RegressionManagerUtils.executeQuery(item, cisConnections, outputFile, delim, printOutputType, "results");
	String results[] = result.split(":");
	if (results.length > 1) {
		rowCount = Integer.valueOf(results[0]);
   		firstRowLatency.addAndGet(Long.parseLong(results[1]));              		
	}            		
 */
	}
 
	/**
	 * Similar to the same method in original pubtest utility, but doesn't throw an exception if 0 rows are returned
	 * and uses existing(established) JDBC connection corresponding to its published datasource name.
	 * 
	 */
	public static String executeProcedure(RegressionItem item, HashMap<String,Connection> cisConnections, String outputFile, String delimiter, String printOutputType) throws CompositeException
	{
        int rows = 0;
        String result = null;
		Connection conn = null;
		CallableStatement stmt = null;  
	    ResultSet rs = null;
    	start = System.currentTimeMillis();
    	long firstRowLatency = 0L;
		
		try
		{
		    conn = getJdbcConnection(item.database, cisConnections);  // don't need to check for null here.
		    
			String URL = null;
			String userName = null;
			if (conn.getMetaData() != null) {
				if (conn.getMetaData().getURL() != null)
					URL = conn.getMetaData().getURL();
				if (conn.getMetaData().getUserName() != null)
					userName = conn.getMetaData().getUserName();
			}
			RegressionManagerUtils.printOutputStr(printOutputType, "debug", "RegressionManagerUtils.executeQuery(item, cisConnections, outputFile, delimiter, printOutputType).  item.database="+item.database+"  cisConnections.URL="+URL+"  cisConnections.userName="+userName+"  outputFile="+outputFile+"  delimiter="+delimiter+"  printOutputType="+printOutputType, "");
			RegressionManagerUtils.printOutputStr(printOutputType, "debug", "DEBUG: connection to DB successful", "");

		    String query = item.input.replaceAll("\n", " ");
			// Convert a CALL statement into a SELECT * FROM statement
			
			// { CALL SCH1.LookupProduct( 3 ) } --> SCH1.LookupProduce( 3 )
			if (query.toUpperCase().contains("CALL")) 
			{
				query = "SELECT * FROM " + RegressionManagerUtils.getProcedure(query);;
			}
			
			// Prepare the query
			stmt = (CallableStatement)conn.prepareCall(query);

// Register output parameter types
            for (int i=0; i<item.outTypes.length; i++)
            {
                if (!"-".equals(item.outTypes[i]))
                {
                    int jdbcType = -1;
                    try
                    {
                        jdbcType = Types.class.getField(item.outTypes[i]).getInt(null);
                    }
                    catch (Exception e)
                    {
                    	RegressionManagerUtils.error(item.lineNum, item.outTypes[i], 
                              "No such JDBC type in java.sql.Types");
                    }
                    stmt.registerOutParameter(i+1, jdbcType);
                }
            }
            stmt.executeQuery();

// Print scalars
            ParameterMetaData pmd = stmt.getParameterMetaData();
            int params = pmd.getParameterCount();
            boolean addSep = false;
            String content = "";
            for (int i=0; i<params; i++)
            {  
               	if (addSep) {
            		content += delimiter;
            	}
            	if (stmt.getObject(i+1) != null)
            		content += stmt.getObject(i+1).toString();
            	else
            		content += "";
            	addSep = true;
            }
            if (outputFile != null)
            	CommonUtils.appendContentToFile(outputFile, content);
        	RegressionManagerUtils.printOutputStr(printOutputType, "results", content, "");

// Get the result cursor and metadata cursor        
            rs = stmt.getResultSet();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            
// Get the column metadata	                   
            addSep = false;
            content = "";
	        for (int i=0; i < columns; i++) {
            	if (addSep) {
            		content += delimiter;
            	}
            	if (rsmd.getColumnName(i+1) != null)
            		content += rsmd.getColumnName(i+1).toString();
            	else
            		content += "";
            	addSep = true;
            }
            if (outputFile != null)
            	CommonUtils.appendContentToFile(outputFile, content);
        	RegressionManagerUtils.printOutputStr(printOutputType, "results", content, "");

// Print cursors
        	boolean firstRow = true;
            while (rs != null)
            {
// Read the values
                while (rs.next())
                {
                    if (firstRow) {
                    	firstRowLatency = System.currentTimeMillis() - start;
        	        	firstRow = false;
                    }
                    addSep = false;
                    content = "";
                    for (int i=0; i<columns; i++) 
                    {
                       	if (addSep) {
                    		content += delimiter;
                    	}
                    	if (rs.getObject(i+1) != null)
                    		content += rs.getObject(i+1).toString();
                    	else
                    		content += "";
                    	addSep = true;
                    }
                    if (outputFile != null)
                    	CommonUtils.appendContentToFile(outputFile, content);
                    RegressionManagerUtils.printOutputStr(printOutputType, "results", content, "");
                    rows++;
                }
                stmt.getMoreResults();
                rs = stmt.getResultSet();
            }
        }
		catch (SQLException e)
		{
			throw new CompositeException("executeProcedure(): " + e.getMessage());
		}
		catch (Exception e)
		{
			throw new CompositeException("executeProcedure(): " + e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)		{	rs.close();   }	 	
				if (stmt != null) 	{   stmt.close(); }
			}
			catch (SQLException e)
			{
				rs = null; stmt = null;
				throw new CompositeException("executeProcedure(): unable to close ResultSet or Statement" + e.getMessage());
			}
		}
		RegressionManagerUtils.printOutputStr(printOutputType, "results", "\nCompleted executeProcedure()", "");
		
		// <rows>:<firstRowLatency>
		result = ""+rows+":"+firstRowLatency;
		return result;
/* Note: to process this result string on the client invocation side use the following pattern:
 * 
 * 	String result = RegressionManagerUtils.executeQuery(item, cisConnections, outputFile, delim, printOutputType, "results");
	String results[] = result.split(":");
	if (results.length > 1) {
		rowCount = Integer.valueOf(results[0]);
   		firstRowLatency.addAndGet(Long.parseLong(results[1]));              		
	}            		
 */
	}

    /**
     * Establish a JDBC connection to CIS for a given published datasource (DS).
     * If it doesn't exist, it is established in this method. There is one connection per DS.
     * 
     * In both case whether useAllDatasources is set to "yes"/"true" or "no"/"false", then we populate
     * the connection map in a lazy fashion as this method is called with new data sources.
     * 
     * When useAllDatasources is set to "yes"/"true" we don't restrict the list of dsNames passed in.
     * When useAllDatasources is set to "no"/"false" we restrict the list of dsNames passed in to
     * the list found in the regression XML configuration file.
     * 
     * The entire list is not populated all at once based on the config file because some of the 
     * datasources are web service data sources and so a JDBC connection cannot be made to those.
     * 
     * @param dsName - a datasource name
     * @param cisConnedtions - a hashmap of existing CIS connections
     * @param cisServerConfig - the CIS server configuration structure
     * @param propertyFile - the name of the PDTool property file being used by this invocation
     * 
     * @return  HashMap<String,Connection>  - a CIS JDBC Connection Map
     */
    public static HashMap<String,Connection> establishJdbcConnection(String dsName, HashMap<String,Connection> cisConnections, CompositeServer cisServerConfig, RegressionTestType regressionConfig, String propertyFile) throws CompositeException
    {
        if (dsName.isEmpty())
        {
        	throw new CompositeException("DataSource name is empty when trying to get JDBC connection to CIS.");
        }
		// Remove any double quotes around the database name
        dsName = dsName.replaceAll("\"", "");
    	
    	boolean useAllDatasources = RegressionManagerUtils.checkBooleanConfigParam(regressionConfig.getTestRunParams().getUseAllDatasources());
    	
    	Connection conn = null;
    	// Check the passed dsName against the populated Connection Map      		
    	if (cisConnections != null)
    		conn = cisConnections.get(dsName);
    	else
    		cisConnections = new HashMap<String,Connection>();	
    	
    	// Determine if the connection was found or not
		if (conn != null)	
		{	
			return cisConnections;
		}
		else
		{
            if(useAllDatasources)	  // only establish a connection to one datasource and populate the map with it. 
            {
            	JdbcConnector connector = new JdbcConnector();
            	conn = connector.connectToCis(cisServerConfig, dsName);
            	cisConnections.put(dsName, conn);
            	return cisConnections;
            }
            else	// only run against datasources from the config file:
            {
            	if (RegressionManagerUtils.findDatabaseMatch(dsName, regressionConfig.getTestRunParams().getDatasources(), propertyFile)) {
            		JdbcConnector connector = new JdbcConnector();
            		conn = connector.connectToCis(cisServerConfig, dsName);
    				cisConnections.put(dsName, conn);
    				return cisConnections;
               	} 
        		else
        		{
        			throw new CompositeException("datasource " + dsName + " is not found in JDBC connection map.");
        		}
            }		
		}
    }  // end method.

    
    /**
     * Obtains a JDBC connection to CIS for a given published datasource (DS).
     * If it doesn't exist, an exception is thrown
     * 
     * @param dsName - a datasource name
     * @param cisConnections - a CIS JDBC Connection Map
     * @return  java.sql.Connection object  - live connection to CIS for the given dsName
     * @throws SQLException 
     */
    public static Connection getJdbcConnection(String dsName, HashMap<String,Connection> cisConnections) throws CompositeException, SQLException
    {
        if (dsName.isEmpty())
        {
        	throw new CompositeException("DataSource name is empty when trying to get JDBC connection to CIS.");
        }
		// Remove any double quotes around the database name
        dsName = dsName.replaceAll("\"", "");

        // Now we need to check the passed dsName against the map we just populated:       		
		Connection conn = cisConnections.get(dsName);
		if (conn != null)
		{
			return conn;
		}
		else
		{
			throw new CompositeException("datasource " + dsName + " is not found in JDBC connection map.");
		}    	
    }  // end method.
    
	/**
	 * 
	 * also @see com.compositesw.ps.deploytool.dao.RegressionPubTestDAO#executeWs(com.compositesw.ps.deploytool.dao.RegressionPubTestDAO.Item, String, String)
	 */
	public static int executeWs(RegressionItem item, String outputFile, CompositeServer cisServerConfig, RegressionTestType regressionConfig, String delimiter, String printOutputType) throws CompositeException
	{		  	
// Check the input parameter values:
		if (cisServerConfig == null || regressionConfig == null)
		{
			throw new CompositeException(
					"XML Configuration objects are not initialized when trying to run Regression test.");
		}		

		URLConnection urlConn = null;
		BufferedReader rd = null;
		OutputStreamWriter wr = null;
		int rows = 0;
	    String host = cisServerConfig.getHostname();
	    int wsPort = cisServerConfig.getPort();   		// port in servers.xml defines WS port
		boolean useHttps = cisServerConfig.isUseHttps();
		
		  // Execute the webservice
		   try
		   {
		        boolean encrypt = item.encrypt;
		        // Override the encrypt flag when useHttps is set from an overall PDTool over SSL (https) setting.
		        if (useHttps && !encrypt) {
		        	encrypt = true;
		        	RegressionManagerUtils.printOutputStr(printOutputType, "summary", "The regression input file encrypt=false has been overridden by useHttps=true for path="+item.path, "");
		        }
		        
			    String urlString = "http://"+host+":"+wsPort+item.path;
		        if (encrypt) {
		            urlString = "https://"+host+":"+(wsPort+2)+item.path;
		        }
	        	RegressionManagerUtils.printOutputStr(printOutputType, "summary", "urlString="+urlString, "");
		        URL url = new URL(urlString);
		        urlConn = url.openConnection();
		        if (encrypt) {
		            // disable hostname verification
		            ((HttpsURLConnection) urlConn)
		                    .setHostnameVerifier(new HostnameVerifier() {
		                        public boolean verify(String urlHostName,
		                                SSLSession session) {
		                            return true;
		                        }
		                    });

		        }
		        // 2014-02-09 (mtinius) - added basic authorization to allow for connections with new users
		        String credentials = cisServerConfig.getUser() + ":" + CommonUtils.decrypt(cisServerConfig.getPassword());
		        String encoded = Base64EncodeDecode.encodeString(credentials);
		        urlConn.setRequestProperty("Authorization", "Basic " + encoded);

		        urlConn.setRequestProperty("SOAPAction", item.action); 
		        urlConn.setRequestProperty("Content-Type", item.contentType);
		        urlConn.setDoOutput(true);
	
		        wr = new OutputStreamWriter(urlConn.getOutputStream());
		        wr.write(item.input);
		        wr.flush();

		        // Get the response
		        rd = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		        String line;
		        StringBuffer buf = new StringBuffer();
		        while ((line = rd.readLine()) != null)
		        {
		        	rows++;
		            buf.append(line);
	                if (outputFile != null)
	                	CommonUtils.appendContentToFile(outputFile, line);
		        }
		        line = buf.toString();
		        RegressionManagerUtils.printOutputStr(printOutputType, "results", line, "");
		        if (line.indexOf("<fault") >= 0 || line.indexOf(":fault") >= 0)
		        {
		        	if (rd != null) { rd.close(); }
		        	if (wr != null) { wr.close(); }
		            throw new IllegalStateException("Fault encountered.");
		        }
		        if (line.trim().length() == 0)
		        {
		        	if (rd != null) { rd.close(); }
		        	if (wr != null) { wr.close(); }
		            throw new IllegalStateException("No response document.");
		        }		   
		        urlConn.getInputStream().close();
//		        urlConn.getOutputStream().flush();
		        wr.close();
		        rd.close();
				RegressionManagerUtils.printOutputStr(printOutputType, "results", "\nCompleted executeWs()", "");
		        return rows;
		   } 
		   catch (IOException e)
		   { 
			   try {
				   HttpURLConnection httpConn = (HttpURLConnection) urlConn;
			       BufferedReader brd = new BufferedReader(new InputStreamReader(httpConn.getErrorStream()));
			       String line;
			       StringBuffer buf = new StringBuffer();
			       while ((line = brd.readLine()) != null)  {
			            buf.append(line+"\n");
			       }
			       brd.close();
			       String error = buf.toString();
				   throw new ApplicationException("executeWs(): " + error, e);
				   
			   } catch (Exception err) {
				   String error = e.getMessage() + "\n"+"DETAILED_MESSAGE=["+err.getMessage()+"]";
//debug:				   System.out.println("*************** ERROR ENCOUNTERED IN executeWs THREAD FOR TYPE:webservice *****************");
				   throw new ApplicationException("executeWs(): " + error, err);
			   }
		   }
		   finally
		   {
				try
				{
		        	if (rd != null) { rd.close(); }
		        	if (wr != null) { wr.close(); }
				}
				catch (Exception e)
				{
					rd = null;
					wr = null;
					throw new CompositeException("executeWs(): unable to close BufferedReader (rd) and OutputStreamWriter (wr): " + e.getMessage());
				}
		   }
	}
   
	
}

