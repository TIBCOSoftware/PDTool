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
import com.tibco.ps.deploytool.modules.RegressionTestType;

public interface RegressionPubTestDAO
{
	/**
	 * Executes all queries, procedures and web services in the input file,
	 * but only for published datasources (virtual databases). 
	 * Uses regressionConfig to control different aspects of execution,
	 * for example can skip some items from the input file if they
	 * are not from a predefined list of datasources.
	 * In that, this method is different from the original Pubtest utility
	 * which executes everything in the input file without restriction.
	 * 
	 * @param serverId
	 * @param dsList
	 * @param pathToRegressionXML
	 * @param pathToServersXML
	 * @throws CompositeException
	 */
	public void executeAll(CompositeServer cisServerConfig, RegressionTestType regressionConfig) throws CompositeException;
	

}
