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
import com.tibco.ps.common.util.wsapi.CompositeServer;
import com.tibco.ps.deploytool.modules.RegressionQueriesType;
import com.tibco.ps.deploytool.modules.RegressionSecurityQueriesType;
import com.tibco.ps.deploytool.modules.RegressionTestType;

/**
 * Generates regression test input file in the same format as the pubtest input file
 * 
 * @author sst
 */
public interface RegressionInputFileDAO
{

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
	public String generateInputFile(CompositeServer cisServerConfig, RegressionTestType regressionConfig, RegressionQueriesType regressionQueries) throws CompositeException;
		

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
	public RegressionSecurityQueriesType generateSecurityQueriesXML(CompositeServer cisServerConfig, RegressionTestType regressionConfig, RegressionQueriesType regressionQueries, RegressionSecurityQueriesType regressionSecurityQueries, String mode) throws CompositeException ;

}  // end class
