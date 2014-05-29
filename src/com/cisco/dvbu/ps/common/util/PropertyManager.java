/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.CompositeException;

/**
 * Property manager to provide helper methods to access properties loaded from the classpath or an external file located under the config root. Property files are cached once
 * loaded. Loading is done on demand the first time. Access to this class is done by calling the getInstance method.
 * 
 */
public class PropertyManager
{
	
    /** manager instance variable */
    private static PropertyManager mgrInstance                 = null;

    /** cached map of all the property files that were loaded keyed by resource name */
    private static Map             allProperties               = null;

    /** the name of the System property that identifies an external config directory */
    private static final String    configRootProperty = CommonConstants.configRootProperty;

    /** the file system path to the config root directory */
    private static String          configDirRoot               = null;

    /** a flag indicating whether to check the external config directory when loading properties */
    private static boolean         useConfigRoot               = false;

    private static Log             logger                      = LogFactory.getLog("PropertyManager");

    /**
     * returns an instance of the class
     * 
     * @return an instance of PropertyManager
     */
    public static PropertyManager getInstance()
    {
        if (mgrInstance == null)
        {
            synchronized (PropertyManager.class)
            {
                if (mgrInstance == null)
                {
                    mgrInstance = new PropertyManager();
                }
            }
        }
        return mgrInstance;
    }

    /**
     * private constructor to prevent direct instantiations. The constructor perfoms some internal initializations and is called from the getInstance method
     */
    private PropertyManager()
    {
        allProperties = new HashMap();

        // check to see if an external config dir has been specified via system property
        configDirRoot = System.getProperty(configRootProperty);
        if (configDirRoot != null && configDirRoot.length() > 0)
        {
            File dir = new File(configDirRoot);
            if (dir.exists() && dir.isDirectory())
            {
                useConfigRoot = true;
            }
        }
    }

    /**
     * get the property value with the specified key from the specified properties resource name. If the key is not found in this property resource a null value is returned.
     * 
     * @param resourceName - the name of the properties file that contains the property
     * @param key - the property key
     * @return the property value for the provided key, or null if not found in the resource
     */
    @SuppressWarnings("unchecked")
    public String getProperty(String resourceName, String key)
    {
        Properties properties = (Properties) allProperties.get(resourceName);
        if (properties == null)
        {
            synchronized (PropertyManager.class)
            {
                properties = (Properties) allProperties.get(resourceName);
                if (properties == null)
                {
                    properties = loadProperties(resourceName);
                    if (properties != null)
                    {
                        allProperties.put(resourceName, properties);
                    }
                }
            }
        }

        if (properties == null || properties.size() == 0)
        {
            return null;
        }
        return properties.getProperty(key);
    }

    /**
     * Returns the String value of the property key. The def value will be
     * returned if the key does not have a value.
     *
     * @param resourceName - the name of the properties file that contains the property
     * @param key - the property key
     * @param def - default value
     * @return String
     */
    public String getProperty(String resourceName, String key, String def)
    {
        String p = this.getProperty(resourceName, key);

        if (null == p)
        {
            return def;
        }

        return p;
    }

    /**
     * Returns a boolean true if the value passed into the method is contained 
     * within the value of the designated property key.   
     * If not found then return false.
     * If the property is not found then return false.
     *
     *Usage:  This method should be called by all modules that need to assess whether an
     *  attribute that is to be updated or output during generatexML is contained within
     *  the "Module Non-Updateable Attribute" property.  If not contained then the attribute
     *  can be generated or updated.  If it is contained then do not update it nor generate it
     *  in the XML.  An example property would be as follows:
     *    DataSourceModule_NonUpdateableAttributes=url,autoAddChildren,anyCharWildCard
     * @param resourceName - the name of the properties file that contains the property
     * @param key - the property key
     * @param value
     * @return boolean
     */
    public boolean containsPropertyValue(String resourceName, String key, String value)
    {
        Properties properties = (Properties) allProperties.get(resourceName);
        if (properties == null)
        {
            synchronized (PropertyManager.class)
            {
                properties = (Properties) allProperties.get(resourceName);
                if (properties == null)
                {
                    properties = loadProperties(resourceName);
                    if (properties != null)
                    {
                        allProperties.put(resourceName, properties);
                    }
                }
            }
        }

        if (properties == null || properties.size() == 0) {
            return false;
        } else {
        	String property = properties.getProperty(key);
        	if (property == null || property.length() == 0) {
        		return false;
        	}
        	if (property.contains(value)) {
        		return true;
        	} else {
        		return false;
        	}
        } 
    }
    
    /**
     * an internal method to load the properties of a given resource. The method loads the resouce from the classpath first, then loads from the file system if an external config
     * directory was set.
     * 
     * @param resourceName - the name of the properties file that contains the property
     * @return a non-empty Properties object that contains the properties loaded from the given resource
     */
    private Properties loadProperties(String resourceName)
    {
        if (resourceName == null || resourceName.length() == 0)
        {
            return null;
        }
        Properties properties = new Properties();
        loadPropertiesFromClassPath(properties, resourceName);
        if (useConfigRoot)
        {
            loadPropertiesFromFileSystem(properties, resourceName);
        }
        return properties;
    }

    /**
     * an internal method to load the properties of a given resource on the classpath
     * 
     * @param properties - object to load the properties in (must not be null)
     * @param resourceName - the name of the properties file that contains the property
     * @return true if the pass properties object was updated, otherwise false
     */
    private boolean loadPropertiesFromClassPath(Properties properties, String resourceName)
    {
        boolean status = false;
        if (resourceName == null || resourceName.length() == 0 || properties == null)
        {
            return false;
        }
        InputStream inpStream = null;
        try
        {
            ClassLoader cl = getClass().getClassLoader();
            inpStream = cl.getResourceAsStream(resourceName);
            if (inpStream != null)
            {
                properties.load(inpStream);
                status = true;
            }
        }
        catch (Exception e)
        {
        	logger.error(e.getMessage(), e);
        }
        finally
        {
            if (inpStream != null)
            {
                try
                {
                    inpStream.close();
                }
                catch (IOException e)
                {
                    // TODO log exception
                }
            }
        }
        return status;
    }

    /**
     * an internal method to load the properties of a given resource in the file system
     * 
     * @param properties - object to load the properties in (must not be null)
     * @param resourceName - properties file name
     * @return true if the pass properties object was updated, otherwise false
     */
    private boolean loadPropertiesFromFileSystem(Properties properties, String resourceName)
    {
        boolean status = false;
        if (!useConfigRoot || resourceName == null || resourceName.length() == 0 || properties == null)
        {
            return false;
        }
        InputStream inpStream = null;
        try
        {
			// Repair the property file if any "set VAR=VALUE" or "export VAR=VALUE" are found.
			repairPropertyFile(configDirRoot+"/"+resourceName);

			// Retrieve the properties that are now repaired
			File file = new File(configDirRoot, resourceName);

            if (file.exists() && file.isFile())
            {
                inpStream = new FileInputStream(file);
                properties.load(inpStream);
                status = true;
            }
        }
        catch (Exception e)
        {
        	logger.error(e.getMessage(), e);
           
        }
        finally
        {
            if (inpStream != null)
            {
                try
                {
                    inpStream.close();
                }
                catch (IOException e)
                {
                    // TODO log exception
                }
            }
        }
        return status;
    }

	// Repair the property file by removing any "set VAR=VALUE" or "export VAR=VALUE" in the property file.
	private static void repairPropertyFile(String file) throws CompositeException {
		StringBuilder stringBuilder = null;
		boolean repairSetNeeded = false;
		boolean repairExportNeeded = false;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			stringBuilder = new StringBuilder();
			String ls = System.getProperty("line.separator");
			while ((line = reader.readLine()) != null) {
				if (!line.trim().startsWith("#")) {
					if (line.trim().toLowerCase().startsWith("set ")) {
						line = line.replace("set ", "").trim();
						repairSetNeeded = true;
					}
					if (line.trim().toLowerCase().startsWith("export ") ) {
						line = line.replace("export ", "").trim();
						repairExportNeeded = true;
					}
				}
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			if (stringBuilder != null && (repairSetNeeded || repairExportNeeded)) {
				// close the original file
				reader.close();

				// write out the repaired file
				StringBuilder repairStringBuilder = new StringBuilder();;
				String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";
				repairStringBuilder.append("# PDTool repaired file ("+CommonUtils.getCurrentDateAsString(dateFormat)+"):");
				if (repairSetNeeded)
					repairStringBuilder.append("  [Removed \"set \"]");
				if (repairExportNeeded)
					repairStringBuilder.append("  [Removed \"export \"]");
				repairStringBuilder.append(ls);

				CommonUtils.createFileWithContent(file, repairStringBuilder.toString());		
				CommonUtils.appendContentToFile(file, stringBuilder.toString());
			}
		} catch (FileNotFoundException e) {
			throw new CompositeException(e.getMessage(),e);
		} catch (IOException e) {
			throw new CompositeException(e.getMessage(),e);
		}
	}

    /**
     * Returns the boolean value of the property key.
     * 
     * @param resourceName - the name of the properties file that contains the property
     * @param key - the property key
     * @return boolean
     */
    public boolean getBooleanProperty(String resourceName, String key)
    {
        String p = this.getProperty(resourceName, key);

        return Boolean.valueOf(p).booleanValue();
    }

    /**
     * Returns the boolean value of the property key. The def value will be returned if the key does not have a value.
     * 
     * @param resourceName - the name of the properties file that contains the property
     * @param key - the property key
     * @param def - default value
     * @return boolean
     */
    public boolean getBooleanProperty(String resourceName, String key, boolean def)
    {
        String p = this.getProperty(resourceName, key);

        if (null == p)
        {
            return def;
        }

        return Boolean.valueOf(p).booleanValue();
    }

    /**
     * Returns the int value of the property key.
     * 
     * @param resourceName - the name of the properties file that contains the property
     * @param key - the property key
     * @return int
     */
   public int getIntProperty(String resourceName, String key)
    {
        String p = this.getProperty(resourceName, key);

        try
        {
            int pv = Integer.parseInt(p);

            return pv;
        }
        catch (NumberFormatException e)
        {
            logger.warn("WARNING: expected numeric property (resourceName='" + resourceName + "', key='" + key + "'), got '" + p + "'");
            return (0);
        }
    }

    /**
     * Returns the int value of the property key. The def value will be returned if the key does not have a value.
     * 
     * @param resourceName - the name of the properties file that contains the property
     * @param key - the property key
     * @param def - default value
     * @return int
     */
    public int getIntProperty(String resourceName, String key, int def)
    {
        String p = this.getProperty(resourceName, key);

        if (null == p)
        {
            return def;
        }

        try
        {
            int pv = Integer.parseInt(p);

            return pv;
        }
        catch (NumberFormatException e)
        {
            logger.warn("WARNING: expected numeric property (resourceName='" + resourceName + "', key='" + key + "'), got '" + p + "'");
            return def;
        }
    }

    /**
     * Returns the long value of the property key. 
     * 
     * @param resourceName - the name of the properties file that contains the property
     * @param key - the property key
     * @return long value of the property key
     */
    public long getLongProperty(String resourceName, String key)
    {
        String p = this.getProperty(resourceName, key);

        try
        {
            long pv = Long.parseLong(p);

            return pv;
        }
        catch (NumberFormatException e)
        {
            logger.warn("WARNING: expected numeric property (resourceName='" + resourceName + "', key='" + key + "'), got '" + p + "'");
            return (0);
        }
    }

    /**
     * Returns the long value of the property key. The def value will be returned if the key does not have a value.
     * 
     * @param resourceName - the name of the properties file that contains the property
     * @param key - the property key
     * @param def - default value
     * @return long
     */
    public long getLongProperty(String resourceName, String key, long def)
    {
        String p = this.getProperty(resourceName, key);

        if (null == p)
        {
            return def;
        }

        try
        {
            long pv = Long.parseLong(p);

            return pv;
        }
        catch (NumberFormatException e)
        {
            logger.warn("WARNING: expected numeric property (resourceName='" + resourceName + "', key='" + key + "'), got '" + p + "'");
            return def;
        }
    }
    
    /**
     * Returns the float value of the property key. The def value will be returned if the key does not have a value.
     * 
     * @param resourceName - the name of the properties file that contains the property
     * @param key - the property key
     * @param def - default value
     * @return float value of the property key
     */
    public float getFloatProperty(String resourceName, String key, float def)
    {
        String p = this.getProperty(resourceName, key);

        if (null == p)
        {
            return def;
        }

        try
        {
            float pv = Float.parseFloat(p);

            return pv;
        }
        catch (NumberFormatException e)
        {
            logger.warn("WARNING: expected numeric property (resourceName='" + resourceName + "', key='" + key + "'), got '" + p + "'");
            return def;
        }
    }

    /**
     * Returns the double value of the property key. The def value will be returned if the key does not have a value.
     * 
     * @param resourceName - the name of the properties file that contains the property
     * @param key - the property key
     * @param def - default value
     * @return double value of the property key
     */
    public double getDoubleProperty(String resourceName, String key, double def)
    {
        String p = this.getProperty(resourceName, key);

        if (null == p)
        {
            return def;
        }

        try
        {
            float pv = Float.parseFloat(p);

            return pv;
        }
        catch (NumberFormatException e)
        {
            logger.warn("WARNING: expected numeric property (resourceName='" + resourceName + "', key='" + key + "'), got '" + p + "'");
            return def;
        }
    }
    
    /**
     * This method checks if the passed in property file exists
     * @param resourceName property file name
     * @return true if property file exists else false
     */
    public boolean doesPropertyFileExist(String resourceName){
        boolean propertyFileExists = false;
    	
    	if (resourceName == null || resourceName.trim().length() == 0)
        {
            return propertyFileExists;
        }

    	Properties properties = new Properties();
        propertyFileExists = loadPropertiesFromClassPath(properties, resourceName);
        
        if (useConfigRoot && !propertyFileExists)
        {
        	propertyFileExists =  loadPropertiesFromFileSystem(properties, resourceName);
        }
    	
    	return propertyFileExists;
    }
    
}
