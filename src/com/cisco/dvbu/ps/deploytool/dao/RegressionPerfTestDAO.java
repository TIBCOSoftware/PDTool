/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao;

import java.util.List;

import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.deploytool.modules.RegressionTestType;

public interface RegressionPerfTestDAO
{


	void executePerformanceTest(CompositeServer cisServerConfig, RegressionTestType regressionConfig, List<RegressionTestType> regressionList) throws CompositeException;
}
