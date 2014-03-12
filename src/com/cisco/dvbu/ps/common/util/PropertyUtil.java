/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.common.util;

import com.cisco.dvbu.ps.common.CommonConstants;

public class PropertyUtil {

	public static String getAllResourcesIndicatorString()
	{
		String defaultPropertyFile = CommonConstants.propertyFile;
		/*  Determine the property file name for this environment
		 *    1. Start with default file CommonConstants.propertyFile
		 *    2. Get Java Environment variables
		 *    3. Get OS System Environment variables
		 */
        String propertyFile = CommonUtils.getFileOrSystemPropertyValue(defaultPropertyFile, "CONFIG_PROPERTY_FILE");
        // Set a default value if not found
        if (propertyFile == null || propertyFile.trim().length() == 0) {
        	propertyFile = defaultPropertyFile;
        }

		return PropertyManager.getInstance().getProperty(propertyFile, "allResourcesIndicator");
	}

	public static String getExcludeResourcesIndicatorString()
	{
		String defaultPropertyFile = CommonConstants.propertyFile;
		/*  Determine the property file name for this environment
		 *    1. Start with default file CommonConstants.propertyFile
		 *    2. Get Java Environment variables
		 *    3. Get OS System Environment variables
		 */
        String propertyFile = CommonUtils.getFileOrSystemPropertyValue(defaultPropertyFile, "CONFIG_PROPERTY_FILE");
        // Set a default value if not found
        if (propertyFile == null || propertyFile.trim().length() == 0) {
        	propertyFile = defaultPropertyFile;
        }
		return PropertyManager.getInstance().getProperty(propertyFile, "exculdeResourcesIndiator");
	}

}
