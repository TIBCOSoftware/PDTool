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
package com.tibco.ps.common.scriptutil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tibco.ps.common.CommonConstants;
import com.tibco.ps.common.adapters.util.XslTransformUtility;
import com.tibco.ps.common.exception.ApplicationException;
import com.tibco.ps.common.exception.CompositeException;
import com.tibco.ps.common.exception.ValidationException;
import com.tibco.ps.common.util.CommonUtils;
import com.tibco.ps.common.util.XMLUtils;
/**
 * This class has bunch of methods used by the scripting framework. Please do not delete the System.out.println 
 * statements, they are used by the scripts.
 * 
 * This class has been moved into its own package so that log4j can be specifically set to ERROR.  
 * If it is set to anything else like INFO or DEBUG, then the output will contain unwanted messages instead of the desired results.
 * 
 *	Note: This should be left at ERROR
 *	log4j.logger.com.tibco.ps.common.scriptutil=ERROR
 */
public class ScriptUtil {

	private static Log logger = LogFactory.getLog(ScriptUtil.class);

	/**
	 * Create a command file for UNIX or Windows from name value pairs strings constructed based on passed in xml content
	 * @param xmlFilePath xml File Path
	 * @param nodeName String return name values for passed in node name and node value. * is treated as all nodes
	 * @param nodeValue String return name values for passed in node name and node value. * is treated as all node values
	 * @param options additional options to return the node name or attributes such as "-noid -noattributes"
	 * @param commandOutputFile The fully qualifed path of the command output file
	 * @param commandHeader The command file header such as #!/bin/bash
	 * @param commandType The type of command file [UNIX|WINDOWS] {use export for UNIX, use set for WINDOWS)
	 *           UNIX: export name="value"
	 *        WINDOWS: set name=value
	 * usage createCommandFileFromXML xmlFilePath * * "-noid -noattributes" /Users/rthummal/mywork/clients/ps/CisDeployTool/resources/abc.sh #!/bin/bash UNIX
	 * usage createCommandFileFromXML xmlFilePath hostname localhost "" C:\opt\pdtool/abc.bat "echo off" 	WINDOWS
	 * usage createCommandFileFromXML xmlFilePath hostname * "-noid" /opt/pdtool/abc.sh #!/bin/bash UNIX
	 */
	public static void createCommandFileFromXML(String xmlFilePath, String nodeName, String nodeValue, String options, String commandOutputFile, String commandHeader, String commandType){
		boolean win = false;
		String cmdPrefix = "export ";
		if(commandType != null && commandType.equals("WINDOWS")){
			win = true;   
			cmdPrefix = "set ";
		}
		
		String nameValuePairs = XMLUtils.getNameValuePairsFromXML(xmlFilePath, null, null,nodeName,nodeValue,options); 
		if(nameValuePairs != null){
			StringBuffer sb = new StringBuffer();
			sb.append(commandHeader+"\n");
			StringTokenizer st = new StringTokenizer(nameValuePairs, "|");
			while(st.hasMoreTokens()){
				sb.append(cmdPrefix);

				String nameValuePair = st.nextToken();

				if(!win){
					nameValuePair = nameValuePair.replace("=", "=\"");
					nameValuePair+="\"";
				}
				sb.append(nameValuePair+"\n");
			}
			
		    try {
				Writer out = new OutputStreamWriter(new FileOutputStream(commandOutputFile));
				out.write(sb.toString());
				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				logger.error("Could not wirte to command file "+commandOutputFile, e);
				throw new ValidationException(e.getMessage(),e);

			} catch (IOException e) {
				logger.error("Could not wirte to command file "+commandOutputFile, e);
				throw new ValidationException(e.getMessage(),e);
			}

		
		}
		
	}

	/**
	 * Create the Composite Studio Enable VCS property file.  The property file name is of the format <composite_user>.<domain>.<composite_server_host>.properties.
	 * @param vcsIncludeResourceSecurity - the value is either "true" or "false"
	 * @param vcsWorkspacePathOverride - this is a way of overriding the vcs workspace path.  The vcsWorkspacePath is derived from the studio.properties file properties:
	 *                                   VCS_WORKSPACE_DIR+"/"+VCS_PROJECT_ROOT.  It will use the substitute drive by default.
	 * @param vcsScriptBinFolderOverride - this is the bin folder name only for PDTool Studio.  Since PDTool Studio can now support multiple hosts via multiple /bin folders,
	 * 									 it is optional to pass in the /bin folder location.  e.g. bin_host1, bin_host2.  The default will be bin if the input is null or blank.                                 
	 * @param studioPropertyName - the name of the PDTool configuration property file.  The default is studio.properties
	 * @param PDToolHomeDir - the directory of the PDToolStudio home directory
	 * @param propertyFilePath - this is the full path to the output file.  For windows 7 the location will be in %USERPROFILE%\.compositesw
	 * 
	 * usage createStudioEnableVCSPropertyFile true studio.properties D/:/CompositeSoftware/CIS6.2.0/conf/studio/PDToolStudio62 C:/Users/mike/.compositesw/admin.composite.localhost.properties
	 */
	public static void createStudioEnableVCSPropertyFile(String vcsIncludeResourceSecurity, String vcsWorkspacePathOverride, String vcsScriptBinFolderOverride, String studioPropertyName, String PDToolHomeDir, String propertyFilePath){

		String prefix = "createStudioEnableVCSPropertyFile";
		// Set the global suppress and debug properties used throughout this class
		String validOptions = "true,false";
	    // Get the property from the property file
	    String debug = CommonUtils.getFileOrSystemPropertyValue(studioPropertyName, "DEBUG1");
	    boolean debug1 = false;
	    if (debug != null && validOptions.contains(debug)) {
	    	debug1 = Boolean.valueOf(debug);
	    }

		if (!vcsIncludeResourceSecurity.equals("true") && !vcsIncludeResourceSecurity.equals("false")) {
			throw new ApplicationException("The variable vcsIncludeResourceSecurity may only be set to \"true\" or \"false\"");
		}
		if (vcsWorkspacePathOverride != null)
			vcsWorkspacePathOverride = vcsWorkspacePathOverride.trim();

		if (vcsScriptBinFolderOverride != null)
			vcsScriptBinFolderOverride = vcsScriptBinFolderOverride.trim();
		
		CommonUtils.writeOutput("",prefix,"-debug1",logger,debug1,false,false);
		CommonUtils.writeOutput("--- INPUT PARAMETERS ----",prefix,"-debug1",logger,debug1,false,false);
		CommonUtils.writeOutput("",prefix,"-debug1",logger,debug1,false,false);
		CommonUtils.writeOutput("    vcsIncludeResourceSecurity="+vcsIncludeResourceSecurity,prefix,"-debug1",logger,debug1,false,false);
		CommonUtils.writeOutput("    vcsWorkspacePathOverride=["+vcsWorkspacePathOverride+"]  len="+vcsWorkspacePathOverride.length(),prefix,"-debug1",logger,debug1,false,false);
		CommonUtils.writeOutput("    vcsScriptFolderOverride=["+vcsScriptBinFolderOverride+"]  len="+vcsScriptBinFolderOverride.length(),prefix,"-debug1",logger,debug1,false,false);
		CommonUtils.writeOutput("    studioPropertyName="+studioPropertyName,prefix,"-debug1",logger,debug1,false,false);
		CommonUtils.writeOutput("    PDToolHomeDir="+PDToolHomeDir,prefix,"-debug1",logger,debug1,false,false);
		CommonUtils.writeOutput("    propertyFilePath="+propertyFilePath,prefix,"-debug1",logger,debug1,false,false);
		CommonUtils.writeOutput("",prefix,"-debug1",logger,debug1,false,false);

		StringBuffer sb = new StringBuffer();
		try {
			Format formatter;
			Date date = new Date();
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String dt = formatter.format(date);
				
			String VCS_WORKSPACE_DIR = CommonUtils.extractVariable("", CommonUtils.getFileOrSystemPropertyValue(studioPropertyName,"VCS_WORKSPACE_DIR"), "studio.properties", true);
			File absFile = new File(VCS_WORKSPACE_DIR);
			String VCS_WORKSPACE_DIR_ABS = CommonUtils.setCanonicalPath(absFile.getCanonicalPath());
	
			String VCS_PROJECT_ROOT = CommonUtils.extractVariable("", CommonUtils.getFileOrSystemPropertyValue(studioPropertyName,"VCS_PROJECT_ROOT"), "studio.properties", true);

			// Set the workspace path to the workspace override
			String vcsWorkspacePath = vcsWorkspacePathOverride;

			// If the vcs workspace path override is null then derive the path from the studio.properties.
			if (vcsWorkspacePath == null || vcsWorkspacePath.length() == 0) {
				vcsWorkspacePath = (VCS_WORKSPACE_DIR_ABS+"/"+VCS_PROJECT_ROOT);
				System.out.println("VCS_WORKSPACE_DIR="+VCS_WORKSPACE_DIR_ABS);
				System.out.println("VCS_PROJECT_ROOT="+VCS_PROJECT_ROOT);			
				System.out.println("vcsWorkspacePath=["+vcsWorkspacePath+"]");
			}
			
			CommonUtils.writeOutput("--- INTERNAL WORKING PARAMETERS ----",prefix,"-debug1",logger,debug1,false,false);
			CommonUtils.writeOutput("",prefix,"-debug1",logger,debug1,false,false);
			CommonUtils.writeOutput("VCS_WORKSPACE_DIR="+VCS_WORKSPACE_DIR_ABS,prefix,"-debug1",logger,debug1,false,false);
			CommonUtils.writeOutput("VCS_PROJECT_ROOT="+VCS_PROJECT_ROOT,prefix,"-debug1",logger,debug1,false,false);
			CommonUtils.writeOutput("pre-mod: vcsWorkspacePath=["+vcsWorkspacePath+"]",prefix,"-debug1",logger,debug1,false,false);

			// Change all backslash to forward slashes to normalize the path
			vcsWorkspacePath = vcsWorkspacePath.replaceAll(Matcher.quoteReplacement("\\\\"), Matcher.quoteReplacement("/"));
//			System.out.println("vcsWorkspacePath="+vcsWorkspacePath);

			// Change all backslash to forward slashes to normalize the path
			vcsWorkspacePath = vcsWorkspacePath.replaceAll(Matcher.quoteReplacement("\\"), Matcher.quoteReplacement("/"));
//			System.out.println("vcsWorkspacePath="+vcsWorkspacePath);

			// Create a double backslash for paths
			vcsWorkspacePath = vcsWorkspacePath.replaceAll(Matcher.quoteReplacement("/"), Matcher.quoteReplacement("\\\\"));
//			System.out.println("vcsWorkspacePath="+vcsWorkspacePath);

			// Add \ in front of the C: so it will be C\:
			if (!vcsWorkspacePath.contains("\\:"))
				vcsWorkspacePath = vcsWorkspacePath.replaceAll(Matcher.quoteReplacement(":"), Matcher.quoteReplacement("\\:"));
//			System.out.println("vcsWorkspacePath="+vcsWorkspacePath);

			// Change C\\: to C\:
			if (vcsWorkspacePath.contains("\\\\:"))
				vcsWorkspacePath = vcsWorkspacePath.replaceAll(Matcher.quoteReplacement("\\\\:"), Matcher.quoteReplacement("\\:"));
//			System.out.println("vcsWorkspacePath="+vcsWorkspacePath);

//			System.out.println("vcsWorkspacePath=["+vcsWorkspacePath+"]");
			CommonUtils.writeOutput("post-mod: vcsWorkspacePath=["+vcsWorkspacePath+"]",prefix,"-debug1",logger,debug1,false,false);

			// If the vcs script bin folder name override is null then derive the path from the default /bin location.
			String vcsScriptFolder = (PDToolHomeDir+"/bin");
			if (vcsScriptBinFolderOverride != null && vcsScriptBinFolderOverride.length() > 0)
				vcsScriptFolder = (PDToolHomeDir+"/"+ vcsScriptBinFolderOverride);
				
			CommonUtils.writeOutput("pre-mod: vcsScriptFolder=["+vcsScriptFolder+"]",prefix,"-debug1",logger,debug1,false,false);
//			System.out.println("vcsScriptFolder="+vcsScriptFolder);

			// Change all backslash to forward slashes to normalize the path
			vcsScriptFolder = vcsScriptFolder.replaceAll(Matcher.quoteReplacement("\\\\"), Matcher.quoteReplacement("/"));
//			System.out.println("vcsScriptFolder="+vcsScriptFolder);

			// Change all backslash to forward slashes to normalize the path
			vcsScriptFolder = vcsScriptFolder.replaceAll(Matcher.quoteReplacement("\\"), Matcher.quoteReplacement("/"));
//			System.out.println("vcsScriptFolder="+vcsScriptFolder);

			// Create a double backslash for paths
			vcsScriptFolder = vcsScriptFolder.replaceAll(Matcher.quoteReplacement("/"), Matcher.quoteReplacement("\\\\"));
		
			// Add \ in front of the C: so it will be C\:
			if (!vcsScriptFolder.contains("\\:"))
				vcsScriptFolder = vcsScriptFolder.replaceAll(Matcher.quoteReplacement(":"), Matcher.quoteReplacement("\\:"));
//			System.out.println("vcsScriptFolder="+vcsScriptFolder);

			// Change C\\: to C\:
			if (vcsScriptFolder.contains("\\\\:"))
				vcsScriptFolder = vcsScriptFolder.replaceAll(Matcher.quoteReplacement("\\\\:"), Matcher.quoteReplacement("\\:"));
//			System.out.println("vcsScriptFolder="+vcsScriptFolder);

//			System.out.println("vcsScriptFolder=["+vcsScriptFolder+"]");
			CommonUtils.writeOutput("post-mod: vcsScriptFolder=["+vcsScriptFolder+"]",prefix,"-debug1",logger,debug1,false,false);

			sb.append("# Generated by ExecutePDToolStudio.bat\n");
			sb.append("# "+dt+"\n");
			sb.append("vcsIncludeResourceSecurity="+vcsIncludeResourceSecurity+"\n");
			sb.append("vcsScriptFolder="+vcsScriptFolder+"\n");
			sb.append("vcsWorkspacePath="+vcsWorkspacePath+"\n");
			sb.append("enableVCS=true"+"\n");
		} catch(Exception e) {
			throw new ValidationException(e.getMessage(),e);		
		}
			
	    try {
			Writer out = new OutputStreamWriter(new FileOutputStream(propertyFilePath));
			out.write(sb.toString());
			out.flush();
			
			String fileContents = CommonUtils.getFileAsString(propertyFilePath);
			System.out.println("");
			System.out.println("");
			System.out.println("-----------------------------------------------------------------------------------------------------");
			System.out.println("Property File Contents For ["+propertyFilePath+"]");
			System.out.println("-----------------------------------------------------------------------------------------------------");
			System.out.println(fileContents);
			System.out.println("");
			CommonUtils.writeOutput("Property File Contents For ["+propertyFilePath+"]",prefix,"-debug1",logger,debug1,false,false);
			CommonUtils.writeOutput(fileContents,prefix,"-debug1",logger,debug1,false,false);
			out.close();
			
		} catch (FileNotFoundException e) {
			logger.error("Could not wirte to property file "+propertyFilePath, e);
			throw new ValidationException(e.getMessage(),e);
		} catch (IOException e) {
			logger.error("Could not wirte to property file "+propertyFilePath, e);
			throw new ValidationException(e.getMessage(),e);
		}		
	}

	/**
	 * Return a flat name value pairs string constructed based on passed in xml content and separators 
	 * "=","|" are defaulted as separators 1 and 2 if the values are not passed. Here is the flattened servers.xml
	 * hostname=localhost|port=9400|usage=DEV|user=admin|encryptedpassword=7F6324FFD300BE8F|domain=composite|
	 * hostname=localhost|port=9460|usage=DEV|user=admin|encryptedpassword=7F6324FFD300BE8F|domain=composite
	 * @param xmlFilePath xml File Path
	 * @param seperator1 Separator of name and value
	 * @param seperator2 Separator for name value pair
	 * @param nodeName String return name values for passed in node name and node value. * is treated as all nodes
	 * @param nodeValue String return name values for passed in node name and node value. * is treated as all node values
	 * @param options additional options to return the node name or attributes such as "-noid -noattributes"
	 * @return flattened xml 
	 * usage getNameValuePairsFromXML xmlFilePath sep1 sep2 * * "-noid -noattributes"
	 * usage getNameValuePairsFromXML xmlFilePath sep1 sep2 hostname localhost ""
	 * usage getNameValuePairsFromXML xmlFilePath sep1 sep2 hostname * "-noid"
	 */
	public static String getNameValuePairsFromXML(String xmlFilePath, String seperator1, String seperator2, String nodeName, String nodeValue, String options){
		String nameValuePairs = XMLUtils.getNameValuePairsFromXML(xmlFilePath, seperator1, seperator2,nodeName,nodeValue,options); 
		System.out.println(nameValuePairs);
		return nameValuePairs;
	}

	/**
	 * isPathSet returns true or false depending on whether the pathToFind is contained within the listofPaths
	 * @param pathToFind string that contains the path to search for
	 * @param listOfPaths string that contains all paths for a given environment
	 * @return pathFound [true or false]
	 */
	public static String isPathSet(String pathToFind, String listOfPaths){
		String pathFound = "false";
		if (listOfPaths.contains(pathToFind)) {
			pathFound = "true";
		}
		System.out.println(pathFound);
		return pathFound;
	}
	/**
	 * Encrypt passed in string using Composite EncryptionManager
	 * @param unencrypted string that needs to be encrypted
	 * @return encrypted String
	 */
	public static String encryptString(String unencrypted){
		String encryptedString = CommonUtils.encrypt(unencrypted);
		System.out.println(encryptedString);
		return encryptedString;
	}
	
	/**
	 * Traverse through the input file and encrypt all passwords using 
	 * Composite Encryption Manager and write file back with encrypted passwords
	 * @param String inputFile - the path to the input file used to search for strings to encrypt based on CommonConstants.encryptPropertiesList
	 */
	public static void encryptPasswordsInFile(String filePath){
		encryptPasswordsInFileBypass(filePath, null);
	}
	
	/**
	 * Traverse through the input file and encrypt all passwords using 
	 * Composite Encryption Manager and write file back with encrypted passwords
	 * @param String inputFile - the path to the input file used to search for strings to encrypt based on CommonConstants.encryptPropertiesList
	 * @param String bypassStr - a comma separated list of strings to bypass if found in one of the target variables or elements to encrypt.
	 */
	public static void encryptPasswordsInFileBypass(String filePath, String bypassStr){
		if(filePath == null || filePath.trim().length() ==0){
			return;
		}
		
		int dotpos= filePath.lastIndexOf(".");
		String ext=filePath.substring(dotpos+1,filePath.length());  

		if(ext.equalsIgnoreCase("xml")){
			XMLUtils.encryptOrDecryptPasswordsInInputFile(filePath, true, bypassStr);
		}else{
			CommonUtils.encryptPasswordsInPropertyFile(filePath, bypassStr);
		}
	}

	
	/**
	 * Find Lines Containing passed in match String from the passed in file
	 * @param XMLFileSource file path to XML document
	 * @param XSLFileSource file path to XSL style sheet
	 */
	public static void XslTransformUtility(String XMLFileSource, String XSLFileSource) {

		XslTransformUtility.executeXslTransformUtility(XMLFileSource, XSLFileSource);

	}
	
	public static void main(String[] args) {
	
		if(args == null || args.length <=1)
		{
			throw new ValidationException("Invalid Arguments");
		}
	
		try {
			boolean validMethod = false;
			Class<ScriptUtil> scriptUtilClass = ScriptUtil.class;
			Method[] methods = scriptUtilClass.getMethods();
			for (int i = 0; i < methods.length; i++) {
				String name = methods[i].getName();
				if(name.equals(args[0])){
					validMethod = true;
					try {
						String[] methodArgs = new String[args.length - 1];
						for (int j = 0; j < methodArgs.length; j++) {
							methodArgs[j] = args[j+1];
						}
						if (logger.isDebugEnabled()) {
							logger.debug(" Invoking method "+args[0]+" With args"+methodArgs);
						}

						Object returnValue = methods[i].invoke(scriptUtilClass, (Object[])methodArgs);
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
				logger.error("Passed in method "+args[0]+" does not exist");
				throw new ValidationException("Passed in method  "+args[0]+" does not exist ");
			}
			
		} catch (CompositeException e) {
			logger.error("Error occured while executing ", e);
			throw e;
		}
	}
}
