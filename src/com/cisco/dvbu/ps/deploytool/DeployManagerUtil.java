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
package com.cisco.dvbu.ps.deploytool;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.exception.ValidationException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
/**
 * Utility Class to invoke methods from DeployManager
 *
 */
public class DeployManagerUtil {
	
	private static Log logger = LogFactory.getLog(DeployManagerUtil.class);

	private static ApplicationContext context = null;
	
	public static final String configRootProperty = CommonConstants.configRootProperty;
	
	public static final String deployManagerName = CommonConstants.deployManagerName;

	public static final String springConfigFile = CommonConstants.springConfigFile;

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
	
		if (logger.isDebugEnabled()) {
			logger.debug("Entering Main method");
		}
	
		if(args == null || args.length <=1)
		{
			throw new ValidationException("Invalid Arguments");
		}
	
		initSpring();
		
		logger.info("--------------------------------------------------------");
		logger.info("------------------ DEPLOYMENT MANAGER ------------------");
		logger.info("--------------------------------------------------------");

		try {
			boolean validMethod = false;
			initAdapter();
			DeployManager deployManager = getDeployManager();
			Class deployManagerClass = deployManager.getClass();
			Method[] methods = deployManagerClass.getMethods();
			// Number of method arguments
			int numInputArgs = args.length - 1;
			for (int i = 0; i < methods.length; i++) {
				String method = methods[i].getName();
				int numMethodArgs = methods[i].getParameterTypes().length;
				if(methods[i].getName().equals(args[0]) && numMethodArgs == numInputArgs){
					validMethod = true;
					try {

						if(logger.isInfoEnabled()){
							logger.info("Calling Action "+ args[0]);
					    }	

						// Determine which parameter is the password parameter when invoking methods.  
						// The list and parameter number are contained in DeployManager.methodList.
						int maskParamNum = CommonUtils.getMaskParamNum(args[0], numInputArgs, DeployManager.methodList);

						String[] methodArgs = new String[args.length - 1];
						for (int j = 0; j < args.length; j++) {
							//Skip arg[0] - method name
							if (j > 0) {
								methodArgs[j-1] = args[j];
								
								String arg = "";
								if (args[j] != null) {
									arg = args[j].trim();
								}
								if(logger.isDebugEnabled()){
									// This is a special case.  If a method in the DeployManager.methodList is being invoked then it has the potential of 
									//  containing a password.  If it does that password is blanked out on display with "********".
									if (maskParamNum != j) {
										logger.debug("arg["+j+"]="+ arg);
									} else {
										if (arg.length() == 0) {
											logger.debug("arg["+j+"]="+ arg);
										} else {
											logger.debug("arg["+j+"]=********");						
										}					
									}
								}
							}
						}

						// Invoke the action
						Object returnValue = methods[i].invoke(deployManager, (Object[])methodArgs);
						
						if (logger.isDebugEnabled()) {
							logger.debug(" Result from invoking method "+args[0]+" "+returnValue);
						}

					} catch (IllegalArgumentException e) {
						logger.error("Error while invoking method "+args[0], e);
						throw new ValidationException(e.getMessage(),e);
					} catch (IllegalAccessException e) {
						logger.error("Error while invoking method "+args[0], e);
						throw new ValidationException(e.getMessage(),e);
					} catch (InvocationTargetException e) {
						logger.error("Error while invoking method "+args[0], e);
						throw new ValidationException(e.getMessage(),e);
					} catch (Throwable e) {
						throw new ValidationException(e.getMessage(),e);
					}
				}
			}
			
			if(!validMethod){
				logger.error("Passed in method "+args[0]+" does not exist or does not match the number of required arguments.");
				throw new ValidationException("Passed in method  "+args[0]+" does not exist or does not match the number of required arguments.");
			}
			
		} catch (CompositeException e) {
			logger.error("Error occured while executing ", e);
			throw e;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Leaving Main method");
		}

	}
	
	private static void initSpring()
	{
		try {
			String configDirRoot = System.getProperty(configRootProperty);
			if (configDirRoot != null && configDirRoot.length() > 0) {
				File dir = new File(configDirRoot);
				
				// Set the file system separator type
				@SuppressWarnings("static-access")
				String sep = dir.separator;
				System.setProperty("FILE_SYSTEM_SEPARATOR", sep);
				
				if (dir.exists() && dir.isDirectory()) {
					File file = new File(dir,springConfigFile);
	
					if(logger.isDebugEnabled()){
						logger.debug("Config root "+dir.getPath());
						logger.debug("Loading Spring Config File "+file.getPath());
					}
					String[] contextFiles = { "file:"+file.getPath() };
					context = new FileSystemXmlApplicationContext(contextFiles);
				}
			}
		} catch (BeansException e) {
			logger.warn("spring initialization failed due to "+e.getMessage());
		}
	}

	/**
	 * Return an instance, which may be shared or independent, of the given bean name.
	 * This method allows a Spring BeanFactory to be used as a replacement for the
	 * Singleton or Prototype design pattern.
	 * <p>Callers may retain references to returned objects in the case of Singleton beans.
	 * <p>Translates aliases back to the corresponding canonical bean name.
	 * Will ask the parent factory if the bean cannot be found in this factory instance.
	 * @param name the name of the bean to return
	 * @return the instance of the bean
	 * @throws NoSuchBeanDefinitionException if there is no bean definition
	 * with the specified name
	 * @throws BeansException if the bean could not be obtained
	 */
	public static Object getBean(String beanName) throws BeansException
	{
        Object bean = null;
        if(context != null){
        	bean = context.getBean(beanName);	
        }
        return bean;
	}

	public static DeployManager getDeployManager(){
		DeployManager deployManager = null;
		try {
			deployManager = (DeployManager)getBean(deployManagerName);
		} catch (BeansException e) {
			logger.error("Unable to get deployManager from spring config due to ", e);
		}
		if(deployManager == null){
			deployManager = new DeployManagerImpl();
		}
		return deployManager;
	}
	
	private static void initAdapter() {

		String prefix = "initAdapter";
		
		// Get the CIS version set in the environment
		String cisVersion = CommonUtils.getFileOrSystemPropertyValue(null, "CIS_VERSION");

		if (cisVersion == null)
			throw new CompositeException(prefix+"::The environment variable \"CIS_VERSION\" may not be null.");

		if(logger.isDebugEnabled()){
			logger.debug("Environment variable CIS_VERSION="+cisVersion);
		}

		// Get the adapter path from the adapter.properties file according to the CIS version mapping
		String adapterPath = CommonUtils.extractVariable(prefix, CommonUtils.getFileOrSystemPropertyValue(CommonConstants.adapterPropertyFile, cisVersion), null, true);

		if (adapterPath == null || adapterPath.trim().length() == 0) {
			throw new CompositeException(prefix+"::PDTool adapter configuraiton properties \""+CommonConstants.adapterPropertyFile+"\" does not contain a valid version/path mapping for CIS version="+cisVersion);
		}
		
		// Check file exists
		if (CommonUtils.fileExists(adapterPath)) {
			// Set the environment property for the PDTool adapter config path for use by PDTool modules
			System.setProperty("PDTOOL_ADAPTER_CONFIG_PATH", adapterPath);		
			String envAdapterPath = System.getProperty("PDTOOL_ADAPTER_CONFIG_PATH");
			if (envAdapterPath == null)
				throw new CompositeException(prefix+"::The enviornment variable PDTOOL_ADAPTER_CONFIG_PATH was not set properly for adapter path="+adapterPath);
			
			if(logger.isInfoEnabled()){
				logger.info("Set environment variable PDTOOL_ADAPTER_CONFIG_PATH="+envAdapterPath);
		    }	
		} else {
			throw new CompositeException(prefix+"::PDTool adapter configuraiton properties \""+CommonConstants.adapterPropertyFile+"\" does not contain a valid mapping for CIS version="+cisVersion+" and path="+adapterPath);
		}

	}
}
