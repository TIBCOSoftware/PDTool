/*******************************************************************************
* Copyright (c) 2014 Cisco Systems
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* PDTool project commiters - initial release
*******************************************************************************/
/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao;

import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.deploytool.modules.RegressionSecurityType;
import com.cisco.dvbu.ps.deploytool.modules.RegressionTestType;

public interface RegressionSecurityTestDAO
{
	/**
	 * Executes a security test for all queries, procedures and web services in the regression security section 
	 * of the Regression Module XML.  It will only execute against published datasources and web services (virtual data services).
	 * Uses regressionConfig to control different aspects of execution.
	 * 
	 * @param cisServerConfig - a specific instance of the CIS server configuration.
	 * @param regressionConfig - the regression configuration XML.
	 * @param regressionSecurity - the regression security XML section.
	 * @param pathToRegressionXML - the path to the Regression Module XML.
	 * @throws CompositeException
	 */
	public void executeSecurityTest(CompositeServer cisServerConfig, RegressionTestType regressionConfig, RegressionSecurityType regressionSecurity, String pathToRegressionXML) throws CompositeException;
	

}
