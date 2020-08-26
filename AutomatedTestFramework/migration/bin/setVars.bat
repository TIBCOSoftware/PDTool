@echo off
REM ############################################################################################################################
REM # (c) 2017 TIBCO Software Inc. All rights reserved.
REM # 
REM # Except as specified below, this software is licensed pursuant to the Eclipse Public License v. 1.0.
REM # The details can be found in the file LICENSE.
REM # 
REM # The following proprietary files are included as a convenience, and may not be used except pursuant
REM # to valid license to Composite Information Server or TIBCO(R) Data Virtualization Server:
REM # csadmin-XXXX.jar, csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar,
REM # csext-XXXX.jar, csjdbc-XXXX.jar, csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar,
REM # and customproc-XXXX.jar (where -XXXX is an optional version number).  Any included third party files
REM # are licensed under the terms contained in their own accompanying LICENSE files, generally named .LICENSE.txt.
REM # 
REM # This software is licensed AS-IS. Support for this software is not covered by standard maintenance agreements with TIBCO.
REM # If you would like to obtain assistance with this software, such assistance may be obtained through a separate paid consulting
REM # agreement with TIBCO.
REM #
REM ############################################################################################################################
REM # Date:   June 2015
REM # PDTool Regression Module - Migration Automated Test Framework
REM #=======================================================================================
REM # Instructions: setVars.bat 
REM #   
REM # Set environment variables
REM # 
REM ########################################################
REM BEGIN: 7.0 USER DEFINED VARIABLE SECTION
REM ########################################################
REM # Set environment variables for PDTool 7.0
REM # 
REM # Set the location of PDTool 7.0
set PDTOOL_INSTALL_HOME_SOURCE=..\..\..\
REM # List of valid Environments~Config property file name pairs.   Comma separated, no space and no double quotes.  Tilde separates pairs: ENV~ConfigFileName
REM # These are the property file names configured in the PDTool7.0\resources\config folder minus the .properties extension.
set VALID_ENV_CONFIG_PAIRS_SOURCE=DEV~deploy_NOVCS_DEV1,UAT~deploy_NOVCS_UAT1,PROD~deploy_NOVCS_PROD1
REM ########################################################
REM END: 7.0 USER DEFINED VARIABLE SECTION
REM ########################################################

REM ########################################################
REM BEGIN: 8.0 USER DEFINED VARIABLE SECTION
REM ########################################################
REM # Set environment variables for PDTool 8.0
REM # 
REM # Set the location of PDTool 8.0
set PDTOOL_INSTALL_HOME_TARGET=..\..\..\
REM # List of valid Environments~Config property file name pairs.   Comma separated, no space and no double quotes.  Tilde separates pairs: ENV~ConfigFileName
REM # These are the property file names configured in the PDTool8.0.0\resources\config folder minus the .properties extension.
set VALID_ENV_CONFIG_PAIRS_TARGET=DEV~deploy_NOVCS_DEV1,UAT~deploy_NOVCS_UAT1,PROD~deploy_NOVCS_PROD1
REM ########################################################
REM END: 8.0 USER DEFINED VARIABLE SECTION
REM ########################################################

REM ########################################################
REM BEGIN: COMMON USER DEFINED VARIABLE SECTION
REM ########################################################
REM # Set environment variables for Automated Test Framework
REM # 
REM # Automated Test Framework Home.  This folder may be independent of where PDTOOL_INSTALL_HOME is located.
set ATF_HOME=..\..\..\AutomatedTestFramework\migration
REM # Used by copyPlanTemplates.bat
REM # Set JAVA_HOME to JRE7
if not defined JAVA_HOME set JAVA_HOME=C:\Program Files\Java\jre8
REM # Used by copyPlanTemplates.bat
REM # Use one or the other or provide your own text editor path.  
REM #    If you have notepad++ it is a much better editor than notepad.
REM #    Do not put double quotes around path.  The script takes care of that.
set EDITOR=C:\Program Files (x86)\Notepad++\notepad++.exe
if not exist "%EDITOR%" set EDITOR=%windir%\system32\notepad.exe

REM # Script Main Activity
set SCRIPT_ACTIVITY=Execute Migration Test
REM # Set the release folders to indicate which version is being tested
REM #   Release folder 1 is designated as the CIS 6 instance migrating from.  R1 designates it is a release 1 primary folder.
REM #   Release folder 2 is designated as the CIS 7 instance migrating to.  R1 designates it is a release 1 primary folder.
set RELEASE_FOLDER1=708R1
set RELEASE_FOLDER2=830R1
REM # Debug=Y or N.  Default=N
set DEBUG=N
REM ########################################################
REM END: COMMON USER DEFINED VARIABLE SECTION
REM ########################################################

REM ########################################################
REM BEGIN: SERVER ATTRIBUTES USER DEFINED VARIABLE SECTION
REM ########################################################
REM # Set environment variables for Automated Test Framework Server Attribute Migration
REM # 
REM # This section should be set if planning to use the Automated Test Framework for migrating server attributes.
REM # When migrating server attributes from CIS 6.x to CIS 7.x there are a series of hard-coded file system paths in the server attributes.
REM # There are 5 sets of paths provided below that allow up to 5 different path configurations.  
REM # If more than 5 path combinations are required then these files need to be modified:
REM #   \migration\bin\setVars.bat
REM #   \migration\bin\copyPlanTemplates.bat
REM #   \migration\templates\ServerAttributes_Transform_62_to_70.xsl  generate into ==> \migration\bin\Xslt\ServerAttributes_Transform_62_to_70.xsl
REM #
REM # The CIS_PREV_VERSION and CIS_NEW_VERSION represent the portion of the various CIS paths within the Server Attribute XML file.
REM #    If CIS previous and new install are installed on the same base path then it will only be necessary to change the version as 
REM #    shown below.  However, if they are installed on different paths, then the full base path should be provided for both variables.
REM # Leave the pair blank with double quotes if there is no pair to be defined.  This results in the text '_NO_TRANSFORM_OPERATION_' being
REM #    placed into the .xsl file for that variable.  In effect, this satisfies the XSLT rules but has no affect because the text will not
REM #    be found in the source file.
REM #
REM # CIS Path Version 1
set CIS_PATH_PREV_VERSION_1="CIS_7.0"
set CIS_PATH_NEW_VERSION_1="CIS_8.0"
REM # CIS Path Version 2
set CIS_PATH_PREV_VERSION_2="7.0.5"
set CIS_PATH_NEW_VERSION_2="8.0.0"
REM # CIS Path Version 3
set CIS_PATH_PREV_VERSION_3="7.0.6"
set CIS_PATH_NEW_VERSION_3="8.0.0"
REM # CIS Path Version 4
set CIS_PATH_PREV_VERSION_4="7.0.7"
set CIS_PATH_NEW_VERSION_4="8.0.0"
REM # CIS Path Version 5
set CIS_PATH_PREV_VERSION_5="7.0.8"
set CIS_PATH_NEW_VERSION_5="8.0.0"
REM XSL Transformation script
set XSL_TRANSFORM_SCRIPT=ServerAttributes_Transform_70_to_80.xsl

