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
package com.cisco.dvbu.ps.deploytool.dao;

import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.deploytool.modules.RegressionQueriesType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityQueriesType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionTestType;

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
