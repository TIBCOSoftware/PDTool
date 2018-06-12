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
REM # Author: Mike Tinius, Data Virtualization Business Unit, Advanced Services
REM # Date:   July 2015
REM # PDTool Regression Module - Migration Automated Test Framework
REM #=======================================================================================
REM # Instructions: 
REM #   script_test_transform.bat ENV_TYPE  SOURCE_SERVER_ATTRIBUTE_FILENAME  [PAUSE]
REM #
REM #   Example:  script_test_transform.bat UAT ServerAttributes_UAT.xml
REM #      This will produce the transformed output file: ServerAttributes_UAT_xform.xml
REM # 
REM # Parameters:
REM #   ENV_TYPE - Example: [DEV,UAT,PROD]
REM #         1. The environment type should be all upper case and match what used to generate the server attributes XML file.
REM #
REM #   SOURCE_SERVER_ATTRIBUTE_FILENAME - The name of the source server attribute file to be transformed.  
REM #			The location is assumed to be in the /migration/modules directory
REM #           Examples:  ServerAttributes_UAT.xml or the test version ServerAttributes_TEST_UAT.xml
REM #
REM #   PAUSE - [optional] variable. Default=true 
REM #         1. blank or "" or true - pause during script execution.
REM #         2. false - no pause during script execution.
REM #=======================================================================================
REM #
REM ######################################
REM EXECUTE THE SCRIPT
REM ######################################
REM # Invoke the script to set the variables
if not exist setVars.bat (
	echo.
	echo.ERROR:  Unable to locate and execute setVars.bat. 
	echo.        Execute from directory \AutomatedTestFramework\migration\bin
	echo.
	exit /b 1
)
call setVars.bat

REM ######################################
REM --- DO NOT CHANGE BELOW THIS LINE ---
REM ######################################
REM # The name of the source server attribute file to be transformed. 
set SOURCE_SERVER_ATTRIBUTE_FILENAME=%2
REM # Perform the XSLT transform:  ONLY=Only perform transform and no PDTool operations.  
REM #   DO NOT CHANGE.  This is the only valid setting for this batch file.
set XSL_PERFORM=ONLY
REM # This is the script definition and invocation parameters
set SCRIPT_ORIGINAL_DEFINITION=SCRIPT_NAME  ENV_TYPE  SOURCE_SERVER_ATTRIBUTE_FILENAME  [PAUSE]
set SCRIPT_ORIGINAL_INVOCATION=%0 %1 %2 [%3]
REM                         SCRIPT_NAME  RELEASE_FOLDER2  ENV_TYPE DEPLOYMENT_PLAN   [CUSTOM] [RENAME_REL] [PAUSE]
call script_test_common.bat %0           ""               %1       ""                ""       FALSE        %3
set ERROR=%ERRORLEVEL%
if %ERROR% NEQ 0 exit /b 1
exit /b 0
