@echo off
REM ############################################################################################################################
REM # (c) 2015 Cisco and/or its affiliates. All rights reserved.
REM # 
REM # This software is released under the Eclipse Public License. The details can be found in the file LICENSE. 
REM # Any dependent libraries supplied by third parties are provided under their own open source licenses as 
REM # described in their own LICENSE files, generally named .LICENSE.txt. The libraries supplied by Cisco as 
REM # part of the Composite Information Server/Cisco Data Virtualization Server, particularly csadmin-XXXX.jar, 
REM # csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar, csext-XXXX.jar, csjdbc-XXXX.jar, 
REM # csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar, and customproc-XXXX.jar (where -XXXX is an 
REM # optional version number) are provided as a convenience, but are covered under the licensing for the 
REM # Composite Information Server/Cisco Data Virtualization Server. They cannot be used in any way except 
REM # through a valid license for that product.
REM # 
REM # This software is released AS-IS!. Support for this software is not covered by standard maintenance agreements with Cisco. 
REM # Any support for this software by Cisco would be covered by paid consulting agreements, and would be billable work.
REM # 
REM ############################################################################################################################
REM # Author: Mike Tinius, Data Virtualization Business Unit, Advanced Services
REM # Date:   June 2015
REM # PDTool Regression Module - Migration Automated Test Framework
REM #=======================================================================================
REM # Instructions: 
REM #   script_test_common.bat INVOKING_SCRIPT  RELEASE_FOLDER  ENV_TYPE  DEPLOYMENT_PLAN  [CUSTOM]  [RENAME_REL]  [PAUSE]
REM # 
REM # Parameters:
REM #   INVOKING_SCRIPT - The name of the script invoking this script.
REM #
REM #   RELEASE_FOLDER - Primary release folder
REM #
REM #   ENV_TYPE - Example: [DEV,UAT,PROD]
REM #         1. The valid values are defined as a result of the variable: VALID_ENV_CONFIG_PAIRS
REM #
REM #   DEPLOYMENT_PLAN - The name of the deployment plan such as:
REM #         1. BusLineBusArea_1Smoke_gen.dp
REM #         2. BusLineBusArea_2Regression_exec.dp
REM #         3. BusLineBusArea_2Regression_compare.dp
REM #         4. BusLineBusArea_3Performance_exec.dp
REM #         5. BusLineBusArea_3Performance_compare.dp
REM #
REM #   CUSTOM - [optional] variable 
REM #         1. blank or "" - generate or execute using SQL SELECT COUNT(1) cnt or SELECT COUNT(*) cnt.
REM #         2. TOP - If the word TOP is provided then generate or execute using the SELECT TOP 1 * command.  Top is a special type of CUSTOM.
REM #         3. CUSTOM value - If any other word is used then execute the SQL file using this pattern and the value of the CUSTOM variable:
REM #                Example: Developer creates a custom SQL file where the custom name = MyQueries
REM #                         Template:                                             Example:
REM #                         \sql\BusLineBusArea_RegressionTest_SQL_%CUSTOM%.txt = \sql\BusLineBusArea_RegressionTest_SQL_MyQueries.txt
REM #                         \sql\BusLineBusArea_PerfTest_SQL_%CUSTOM%.txt       = \sql\BusLineBusArea_PerfTest_SQL_MyQueries.txt
REM #
REM #   RENAME_REL - [optional] variable. Default=true
REM #         1. blank or "" or true - force a rename of the release output folders upon each execution of this script.
REM #         2. false - disable the rename function and allow the results to go to the existing RELEASE_FOLDER1 directory.  
REM #              Example.  This can be useful when executing a series of tests for the same release such as the following:
REM #                        Smoke Test, Regression Test, Performance Test and Security Test.
REM #              Note:     This script will automatically ignore renaming the output folder for the following and thus the RENAME_REL does not have to be set.
REM #                        Generating Smoke Test, Compare Regression Test, Compare Performance Test and Generate Security Test.
REM #
REM #   PAUSE - [optional] variable. Default=true 
REM #         1. blank or "" or true - pause during script execution.
REM #         2. false - no pause during script execution.
REM #=======================================================================================
REM #
REM ######################################
REM Begin common script
REM ######################################
pushd %CD%
cls
echo.########################################################################################################################################
echo.PDTool Migration Automated Test Framework:
echo.########################################################################################################################################
echo.
REM ######################################
REM Validate input
REM ######################################
set INVOKING_SCRIPT=%1
set RELEASE_FOLDER=%2
set ENV_TYPE=%3
set DEPLOYMENT_PLAN=%4
set CUSTOM=%5
set RENAME_REL=%6
set PAUSE=%7

REM # The TOKEN variable is used for splitting the pairs of environment type and configuration proper file name.
REM #    Example: DEV~deploy_DEV
set TOKEN=~


REM # Remove double quotes and set default values where appropriate
SETLOCAL EnableDelayedExpansion
if defined PDTOOL_INSTALL_HOME set PDTOOL_INSTALL_HOME=!PDTOOL_INSTALL_HOME:"=!
if defined ATF_HOME set ATF_HOME=!ATF_HOME:"=!
if defined VALID_ENV_CONFIG_PAIRS set VALID_ENV_CONFIG_PAIRS=!VALID_ENV_CONFIG_PAIRS:"=!
if defined SCRIPT_ACTIVITY set SCRIPT_ACTIVITY=!SCRIPT_ACTIVITY:"=!
if defined SCRIPT_CIS_VERSION set SCRIPT_CIS_VERSION=!SCRIPT_CIS_VERSION:"=!
if defined RELEASE_FOLDER1 set RELEASE_FOLDER1=!RELEASE_FOLDER1:"=!
if defined RELEASE_FOLDER2 set RELEASE_FOLDER2=!RELEASE_FOLDER2:"=!
if defined DEBUG set DEBUG=!DEBUG:"=!
if defined XSL_PERFORM set XSL_PERFORM=!XSL_PERFORM:"=!
if defined XSL_TRANSFORM_SCRIPT set XSL_TRANSFORM_SCRIPT=!XSL_TRANSFORM_SCRIPT:"=!
REM # mtinius - All references to XSL_FROM_CIS_VERSION are commented out but left in tact if determined to be useful.
REM #    script_test_62.bat:  set XSL_FROM_CIS_VERSION=6.2
REM #    script_test_70.bat:  set XSL_FROM_CIS_VERSION=6.2
REM #    script_test_transform.bat - no references required
rem if defined XSL_FROM_CIS_VERSION set XSL_FROM_CIS_VERSION=!XSL_FROM_CIS_VERSION:"=!
if defined SOURCE_SERVER_ATTRIBUTE_FILENAME set SOURCE_SERVER_ATTRIBUTE_FILENAME=!SOURCE_SERVER_ATTRIBUTE_FILENAME:"=!

REM # Display command line input
echo.=======================================
echo.%SCRIPT_ACTIVITY% %SCRIPT_CIS_VERSION%
echo.=======================================
echo.
call :padHeader "%SCRIPT_ORIGINAL_DEFINITION%" "%SCRIPT_ORIGINAL_INVOCATION%" new_SCRIPT_ORIGINAL_DEFINITION new_SCRIPT_ORIGINAL_INVOCATION ERROR
if %ERROR%==1 (
	echo.Error detected within the header. Exiting.
	goto USAGE
)
echo.Original Command:             %new_SCRIPT_ORIGINAL_DEFINITION%
echo.                              %new_SCRIPT_ORIGINAL_INVOCATION%
echo.
call :padHeader "SCRIPT_NAME RELEASE_FOLDER ENV_TYPE DEPLOYMENT_PLAN [CUSTOM] [RENAME_REL] [PAUSE]" "%0 [%RELEASE_FOLDER%] [%ENV_TYPE%] [%DEPLOYMENT_PLAN%] [%CUSTOM%] [%RENAME_REL%] [%PAUSE%]" new_SCRIPT_ORIGINAL_DEFINITION new_SCRIPT_ORIGINAL_INVOCATION ERROR
if %ERROR%==1 (
	echo.Error detected within the header. Exiting.
	goto USAGE
)
echo.Common Command                %new_SCRIPT_ORIGINAL_DEFINITION%
echo.                              %new_SCRIPT_ORIGINAL_INVOCATION%
echo.


REM # Variables used for validation error messages.
set errorMsg=
set TAB=       
REM # LF must have 2 blank lines following it to create a line feed
set LF=^


REM # Remove double quotes and set default values for input parameters
if defined INVOKING_SCRIPT set INVOKING_SCRIPT=!INVOKING_SCRIPT:"=!
if defined RELEASE_FOLDER set RELEASE_FOLDER=!RELEASE_FOLDER:"=!
if defined ENV_TYPE set ENV_TYPE=!ENV_TYPE:"=!
if defined DEPLOYMENT_PLAN set DEPLOYMENT_PLAN=!DEPLOYMENT_PLAN:"=!
if defined CUSTOM set CUSTOM=!CUSTOM:"=!
if not defined RENAME_REL goto LBL_RENAME_REL
   set RENAME_REL=!RENAME_REL:"=!
   if "%RENAME_REL%" == ""       set RENAME_REL=TRUE
   CALL :UCase RENAME_REL RENAME_REL
   if "%RENAME_REL%" == "F"      set RENAME_REL=FALSE
   if "%RENAME_REL%" == "T"      set RENAME_REL=TRUE
   if "%RENAME_REL%" == "TRUE"   goto LBL_RENAME_REL
   if "%RENAME_REL%" == "FALSE"  goto LBL_RENAME_REL
   set errorMsg=!errorMsg!%TAB%Parameter RENAME_REL=%RENAME_REL% is invalid.  Set to true or false.!LF!
:LBL_RENAME_REL
REM # Default value for RENAME_REL if nothing is set
if not defined RENAME_REL set RENAME_REL=TRUE
if not defined PAUSE goto LBL_PAUSE
   set PAUSE=!PAUSE:"=!
   if "%PAUSE%" == ""       set PAUSE=TRUE
   CALL :UCase PAUSE PAUSE
   if "%PAUSE%" == "F"      set PAUSE=FALSE
   if "%PAUSE%" == "T"      set PAUSE=TRUE
   if "%PAUSE%" == "TRUE"   goto LBL_PAUSE
   if "%PAUSE%" == "FALSE"  goto LBL_PAUSE
   set errorMsg=!errorMsg!%TAB%Parameter PAUSE=%PAUSE% is invalid.  Set to true or false.!LF!
:LBL_PAUSE
REM # Default value for PAUSE if nothing is set
if not defined PAUSE set PAUSE=TRUE


REM # Convert ENV_TYPE to upper case
CALL :UCase ENV_TYPE ENV_TYPE 


REM # Resolve the absolute path for PDTOOL_INSTALL_HOME
pushd .
cd %PDTOOL_INSTALL_HOME%
set PDTOOL_INSTALL_HOME=%CD%
popd
REM # Resolve the absolute path for ATF_HOME
pushd .
cd %ATF_HOME%
set ATF_HOME=%CD%
popd


REM ########################################
REM # MIGRATION SERVER ATTRIBUTE ONLY CODE
REM ########################################
REM # The following test are to look for deployment plans not supported for Migration
SET SERVER_ATTRIBUTE_CMD=FALSE
REM # Convert XSL_PERFORM to upper case if defined
if not defined XSL_PERFORM SET SOURCE_SERVER_ATTRIBUTE_FILENAME=
if not defined XSL_PERFORM goto CONTINUE_VALIDATE_VARIABLES

	CALL :UCase XSL_PERFORM XSL_PERFORM 
	if "%XSL_PERFORM%" == "BEFORE" (
		SET SOURCE_SERVER_ATTRIBUTE_FILENAME=
		SET SERVER_ATTRIBUTE_CMD=TRUE
	)
	if "%XSL_PERFORM%" == "AFTER"  (
		SET SOURCE_SERVER_ATTRIBUTE_FILENAME=
		SET SERVER_ATTRIBUTE_CMD=TRUE
	)
	if "%XSL_PERFORM%" == "ONLY" goto CONTINUE_XSL_ONLY1
	goto CONTINUE_VALIDATE_VARIABLES
:CONTINUE_XSL_ONLY1
		SET SERVER_ATTRIBUTE_CMD=TRUE
		SET FOUND_STR=0
		REM # Set the default server attribute plan
		SET DEPLOYMENT_PLAN=CIS_Transform_ServerAttributes.dp
		call :findString %SOURCE_SERVER_ATTRIBUTE_FILENAME% TEST FOUND_STR
		REM # Set the default test server attribute plan
		if %FOUND_STR%==1 SET DEPLOYMENT_PLAN=CIS_Transform_ServerAttributes_TEST.dp
	
	
REM # Validate variables and input parameters
:CONTINUE_VALIDATE_VARIABLES
if not defined ATF_HOME					set errorMsg=!errorMsg!%TAB%The variable ATF_HOME has not been defined.!LF!
if not exist "%ATF_HOME%" 				set errorMsg=!errorMsg!%TAB%Directory does not exist for ATF_HOME=%ATF_HOME%!LF!
if not defined XSL_PERFORM				set errorMsg=!errorMsg!%TAB%The variable XSL_PERFORM has not been defined.!LF!
if not defined XSL_TRANSFORM_SCRIPT		set errorMsg=!errorMsg!%TAB%The variable XSL_TRANSFORM_SCRIPT has not been defined.!LF!
if not defined SCRIPT_ACTIVITY			set errorMsg=!errorMsg!%TAB%The variable SCRIPT_ACTIVITY has not been defined.!LF!
if not defined DEBUG					set errorMsg=!errorMsg!%TAB%The variable DEBUG has not been defined.!LF!
if not defined INVOKING_SCRIPT			set errorMsg=!errorMsg!%TAB%The variable INVOKING_SCRIPT has not been defined.!LF!
if not defined ENV_TYPE					set errorMsg=!errorMsg!%TAB%The variable ENV_TYPE has not been defined.!LF!
if not defined PAUSE					set errorMsg=!errorMsg!%TAB%The variable PAUSE has not been defined.!LF!

REM ### Check for XLS_PERFOM=ONLY
if "%XSL_PERFORM%" == "ONLY" goto CONTINUE_XSL_ONLY2

if not defined VALID_ENV_CONFIG_PAIRS	set errorMsg=!errorMsg!%TAB%The variable VALID_ENV_CONFIG_PAIRS has not been defined.!LF!
rem if not defined XSL_FROM_CIS_VERSION		set errorMsg=!errorMsg!%TAB%The variable XSL_FROM_CIS_VERSION has not been defined.!LF!
if not defined PDTOOL_INSTALL_HOME		set errorMsg=!errorMsg!%TAB%The variable PDTOOL_INSTALL_HOME has not been defined.!LF!
if not exist "%PDTOOL_INSTALL_HOME%" 	set errorMsg=!errorMsg!%TAB%Directory does not exist for PDTOOL_INSTALL_HOME=%PDTOOL_INSTALL_HOME%!LF!
if not defined RELEASE_FOLDER1			set errorMsg=!errorMsg!%TAB%The variable RELEASE_FOLDER1 has not been defined.!LF!
if not defined RELEASE_FOLDER2			set errorMsg=!errorMsg!%TAB%The variable RELEASE_FOLDER2 has not been defined.!LF!
if not defined DEPLOYMENT_PLAN			set errorMsg=!errorMsg!%TAB%The variable DEPLOYMENT_PLAN has not been defined.!LF!
if not defined RENAME_REL				set errorMsg=!errorMsg!%TAB%The variable RENAME_REL has not been defined.!LF!


REM # Set various default variables
:CONTINUE_XSL_ONLY2
set MIGRATION_TEST_HOME=%ATF_HOME%
set MIGRATION_TEST_PLANS=%MIGRATION_TEST_HOME%\plans
set MIGRATION_TEST_LOGS=%MIGRATION_TEST_HOME%\logs
set MIGRATION_TEST_MODULES=%MIGRATION_TEST_HOME%\modules
set MIGRATION_TEST_OUTPUT=%MIGRATION_TEST_HOME%\output
set MIGRATION_TEST_XSLT_HOME=%MIGRATION_TEST_HOME%\bin\Xslt
set MIGRATION_TEST_SQL=%MIGRATION_TEST_HOME%\sql


REM ### Check for XLS_PERFOM=ONLY
if "%XSL_PERFORM%" == "ONLY" (
	set CONFIG_PROPERTY_NAME=TRANSFORM_ONLY_%ENV_TYPE%
	goto CONTINUE_XSL_ONLY3
)


REM # Extract the environment type list
set FUNCTION_NAME1=[DEBUG] EXTRACT_ENV_TYPE_LIST      
if %DEBUG%==Y echo.%FUNCTION_NAME1%    BEGIN: ------------------------------------------
CALL :EXTRACT_ENV_TYPE_LIST "%VALID_ENV_CONFIG_PAIRS%" VALID_ENV_TYPES
if %DEBUG%==Y echo.%FUNCTION_NAME1%   RETURN: %VALID_ENV_TYPES%
if %DEBUG%==Y echo.%FUNCTION_NAME1%      END: ------------------------------------------
if %DEBUG%==Y echo.


REM # Validate the environment config pairs
set VALID_ENV=0
set CONFIG_PROPERTY_NAME=
set FUNCTION_NAME1=[DEBUG] VALIDATE_CONFIG_PROPERTY_NAME      
if %DEBUG%==Y echo.%FUNCTION_NAME1%    BEGIN: ------------------------------------------
CALL :VALIDATE_CONFIG_PROPERTY_NAME "%ENV_TYPE%" "%VALID_ENV_CONFIG_PAIRS%" VALID_ENV CONFIG_PROPERTY_NAME
if %DEBUG%==Y echo.%FUNCTION_NAME1%   RETURN: %VALID_ENV% %CONFIG_PROPERTY_NAME%
if %DEBUG%==Y echo.%FUNCTION_NAME1%      END: ------------------------------------------
if %DEBUG%==Y echo.
if %VALID_ENV%==0 set errorMsg=!errorMsg!%TAB%Parameter ENV_TYPE=%ENV_TYPE% is invalid.!LF!


REM # Check to see if any error messages have been compiled.
:CONTINUE_XSL_ONLY3
if "!errorMsg!" NEQ "" goto USAGE


REM # Determine what command is being executed based on the name in the deployment plan
call :findString %DEPLOYMENT_PLAN% Smoke_gen 			FOUND_STR_SMOKE_GEN
call :findString %DEPLOYMENT_PLAN% Smoke_exec 			FOUND_STR_SMOKE_EXEC
call :findString %DEPLOYMENT_PLAN% Regression_exec 		FOUND_STR_REGRESSION_EXEC
call :findString %DEPLOYMENT_PLAN% Regression_compare 	FOUND_STR_REGRESSION_COMP
call :findString %DEPLOYMENT_PLAN% Performance_exec 	FOUND_STR_PERFORMANCE_EXEC
call :findString %DEPLOYMENT_PLAN% Performance_compare 	FOUND_STR_PERFORMANCE_COMP
call :findString %DEPLOYMENT_PLAN% Security_gen 		FOUND_STR_SECURITY_GEN
call :findString %DEPLOYMENT_PLAN% Security_exec 		FOUND_STR_SECURITY_EXEC
call :findString %DEPLOYMENT_PLAN% ServerAttribute 		FOUND_STR_SERVER_ATTRIBUTE
if %FOUND_STR_SERVER_ATTRIBUTE%==1 SET SERVER_ATTRIBUTE_CMD=TRUE
if %DEBUG%==Y (
	echo.[DEBUG] FOUND_STR_SMOKE_GEN=%FOUND_STR_SMOKE_GEN%
	echo.[DEBUG] FOUND_STR_SMOKE_EXEC=%FOUND_STR_SMOKE_EXEC%
	echo.[DEBUG] FOUND_STR_REGRESSION_EXEC=%FOUND_STR_REGRESSION_EXEC%
	echo.[DEBUG] FOUND_STR_REGRESSION_COMP=%FOUND_STR_REGRESSION_COMP%
	echo.[DEBUG] FOUND_STR_PERFORMANCE_EXEC=%FOUND_STR_PERFORMANCE_EXEC%
	echo.[DEBUG] FOUND_STR_PERFORMANCE_COMP=%FOUND_STR_PERFORMANCE_COMP%
	echo.[DEBUG] FOUND_STR_SECURITY_GEN=%FOUND_STR_SECURITY_GEN%
	echo.[DEBUG] FOUND_STR_SECURITY_EXEC=%FOUND_STR_SECURITY_EXEC%
	echo.[DEBUG] FOUND_STR_SERVER_ATTRIBUTE=%FOUND_STR_SERVER_ATTRIBUTE%
)


REM # Replace text on both sides of the deployment plan with blanks to determine the project identifier: BusLineBusAreaSubjArea
set BusLineBusAreaSubjArea=%DEPLOYMENT_PLAN%
if %FOUND_STR_SMOKE_GEN%==1 (
	set MIGRATION_TEST_TYPE=Smoke Test Generate
 	call :replace _1Smoke_gen.dp           "" "%BusLineBusAreaSubjArea%" BusLineBusAreaSubjArea
)
if %FOUND_STR_SMOKE_EXEC%==1 (
	set MIGRATION_TEST_TYPE=Smoke Test Execute
	call :replace _1Smoke_exec.dp          "" "%BusLineBusAreaSubjArea%" BusLineBusAreaSubjArea
)
if %FOUND_STR_REGRESSION_EXEC%==1 (
	set MIGRATION_TEST_TYPE=Regression Test Execute
	call :replace _2Regression_exec.dp     "" "%BusLineBusAreaSubjArea%" BusLineBusAreaSubjArea
)
if %FOUND_STR_REGRESSION_COMP%==1 (
	set MIGRATION_TEST_TYPE=Regression Test Compare
	call :replace _2Regression_compare.dp  "" "%BusLineBusAreaSubjArea%" BusLineBusAreaSubjArea
)
if %FOUND_STR_PERFORMANCE_EXEC%==1 (
	set MIGRATION_TEST_TYPE=Performance Test Execute
	call :replace _3Performance_exec.dp    "" "%BusLineBusAreaSubjArea%" BusLineBusAreaSubjArea
)
if %FOUND_STR_PERFORMANCE_COMP%==1 (
	set MIGRATION_TEST_TYPE=Performance Test Compare
	call :replace _3Performance_compare.dp "" "%BusLineBusAreaSubjArea%" BusLineBusAreaSubjArea
)
if %FOUND_STR_SECURITY_GEN%==1 (
	set MIGRATION_TEST_TYPE=Security Test Generate
	call :replace _4Security_gen.dp        "" "%BusLineBusAreaSubjArea%" BusLineBusAreaSubjArea
)
if %FOUND_STR_SECURITY_EXEC%==1 (
	set MIGRATION_TEST_TYPE=Security Test Execute
	call :replace _4Security_exec.dp       "" "%BusLineBusAreaSubjArea%" BusLineBusAreaSubjArea
)
if %DEBUG%==Y (
	echo.
	echo.[DEBUG] BusLineBusAreaSubjArea=%BusLineBusAreaSubjArea%
)
if not defined BusLineBusAreaSubjArea (
	set errorMsg=!errorMsg!%TAB%BusLineBusAreaSubjArea could not be determined.!LF!
	goto USAGE
)


REM # Determine whether to rename the output release folder based on the type of command being executed
SET RENAME_FOLDER_CMD=FALSE
if %FOUND_STR_SMOKE_EXEC%==1 		SET RENAME_FOLDER_CMD=TRUE
if %FOUND_STR_REGRESSION_EXEC%==1 	SET RENAME_FOLDER_CMD=TRUE
if %FOUND_STR_PERFORMANCE_EXEC%==1 	SET RENAME_FOLDER_CMD=TRUE
if %FOUND_STR_SECURITY_EXEC%==1 	SET RENAME_FOLDER_CMD=TRUE
REM # Set the rename folder command for renaming the release folder based on user input which can override the settings above.
if "%RENAME_REL%"=="FALSE" SET RENAME_FOLDER_CMD=FALSE


REM # Validate the CUSTOM variable and set text for user output
set CUSTOM_TEXT1=Use SELECT COUNT(*) cnt
if not defined CUSTOM goto CUSTOM_CONTINUE
	if "%CUSTOM%" == "top"  set CUSTOM=TOP
	if "%CUSTOM%" == "TOP"  set CUSTOM_TEXT1=Use SELECT TOP 1 *
	if "%CUSTOM%" NEQ "TOP" set CUSTOM_TEXT1=Use custom queries. CUSTOM=%CUSTOM%
	set CUSTOM=_%CUSTOM%
	if %FOUND_STR_SECURITY_EXEC%==1 set CUSTOM=
:CUSTOM_CONTINUE
if not defined CUSTOM set CUSTOM=
REM # No need to set the CUSTOM message if performing a server attribute function.
if "%SERVER_ATTRIBUTE_CMD%"=="TRUE" (
	set CUSTOM=
	set CUSTOM_TEXT1=
)


REM ########################################
REM # MIGRATION ONLY CODE
REM ########################################
REM # The following test are to look for deployment plans not supported for Migration
SET NOT_SUPPORTED_CMD=FALSE
if %FOUND_STR_SMOKE_EXEC%==1 	SET NOT_SUPPORTED_CMD=TRUE
if %FOUND_STR_SECURITY_GEN%==1 	SET NOT_SUPPORTED_CMD=TRUE
if %FOUND_STR_SECURITY_EXEC%==1 SET NOT_SUPPORTED_CMD=TRUE
if "%NOT_SUPPORTED_CMD%" == "TRUE" (
   set errorMsg=!errorMsg!%TAB%The specified deployment plan [%DEPLOYMENT_PLAN%] is not supported for migration testing."!LF!
   goto USAGE
)

REM ########################################
REM # MIGRATION SERVER ATTRIBUTE ONLY CODE
REM ########################################
REM # The following test are to look for deployment plans not supported for Migration
REM # Validate environemnt variables and paths
if not defined CONFIG_PROPERTY_NAME 						set errorMsg=!errorMsg!%TAB%The configuration property name is invalid.  The variable CONFIG_PROPERTY_NAME is not defined.!LF!
if "%CONFIG_PROPERTY_NAME%" == "" 							set errorMsg=!errorMsg!%TAB%The configuration property name is invalid.  The variable CONFIG_PROPERTY_NAME is blank.!LF!
if not exist "%MIGRATION_TEST_HOME%" 						set errorMsg=!errorMsg!%TAB%Directory does not exist for MIGRATION_TEST_HOME=%MIGRATION_TEST_HOME%!LF!
if not exist "%MIGRATION_TEST_PLANS%" 						set errorMsg=!errorMsg!%TAB%Directory does not exist for MIGRATION_TEST_PLANS=%MIGRATION_TEST_PLANS%!LF!
if not exist "%MIGRATION_TEST_LOGS%" 						set errorMsg=!errorMsg!%TAB%Directory does not exist for MIGRATION_TEST_LOGS=%MIGRATION_TEST_LOGS%!LF!
if not exist "%MIGRATION_TEST_MODULES%" 					set errorMsg=!errorMsg!%TAB%Directory does not exist for MIGRATION_TEST_MODULES=%MIGRATION_TEST_MODULES%!LF!
if not exist "%MIGRATION_TEST_OUTPUT%" 						set errorMsg=!errorMsg!%TAB%Directory does not exist for MIGRATION_TEST_OUTPUT=%MIGRATION_TEST_OUTPUT%!LF!
if not exist "%MIGRATION_TEST_SQL%" 						set errorMsg=!errorMsg!%TAB%Directory does not exist for MIGRATION_TEST_SQL=%MIGRATION_TEST_SQL%!LF!
if not exist "%MIGRATION_TEST_PLANS%\%DEPLOYMENT_PLAN%" 	set errorMsg=!errorMsg!%TAB%The deployment plan does not exist: "%MIGRATION_TEST_PLANS%\%DEPLOYMENT_PLAN%"!LF!
set MODULE_NAME=
if "%SERVER_ATTRIBUTE_CMD%" == "FALSE" (
	set MODULE_NAME=%BusLineBusAreaSubjArea%_RegressionModule.xml
	if not exist "%MIGRATION_TEST_MODULES%\%MODULE_NAME%" 	set errorMsg=!errorMsg!%TAB%The module XML does not exist: "%MIGRATION_TEST_MODULES%\%MODULE_NAME%"!LF!
)
if "%SERVER_ATTRIBUTE_CMD%" == "TRUE" (
	if not exist "%MIGRATION_TEST_XSLT_HOME%" 					set errorMsg=!errorMsg!%TAB%Directory does not exist for MIGRATION_TEST_XSLT_HOME=%MIGRATION_TEST_XSLT_HOME%!LF!
	if not exist "%MIGRATION_TEST_XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%" set errorMsg=!errorMsg!%TAB%The XSL Transform does not exist: "%MIGRATION_TEST_XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%"!LF!
)


REM # Construct the SQL Query Input File Name
SET MIGRATION_TEST_SQL_FILE_NAME=
if %FOUND_STR_SMOKE_GEN%==1 		SET MIGRATION_TEST_SQL_FILE_NAME=%BusLineBusAreaSubjArea%_SmokeTest_SQL%CUSTOM%.txt
if %FOUND_STR_SMOKE_EXEC%==1 		SET MIGRATION_TEST_SQL_FILE_NAME=%BusLineBusAreaSubjArea%_SmokeTest_SQL%CUSTOM%.txt
if %FOUND_STR_REGRESSION_EXEC%==1 	SET MIGRATION_TEST_SQL_FILE_NAME=%BusLineBusAreaSubjArea%_RegressionTest_SQL%CUSTOM%.txt
if %FOUND_STR_REGRESSION_COMP%==1 	SET MIGRATION_TEST_SQL_FILE_NAME=%BusLineBusAreaSubjArea%_RegressionTest_SQL%CUSTOM%.txt
if %FOUND_STR_PERFORMANCE_EXEC%==1 	SET MIGRATION_TEST_SQL_FILE_NAME=%BusLineBusAreaSubjArea%_PerfTest_SQL%CUSTOM%.txt
if %FOUND_STR_PERFORMANCE_COMP%==1 	SET MIGRATION_TEST_SQL_FILE_NAME=%BusLineBusAreaSubjArea%_PerfTest_SQL%CUSTOM%.txt
REM # Check whether executing Smoke, Regression or Performance test and validate tha the SQL input file exists.
if %FOUND_STR_SMOKE_EXEC%==1 		goto SQL_INPUT_FILE_VALIDATE
if %FOUND_STR_REGRESSION_EXEC%==1  	goto SQL_INPUT_FILE_VALIDATE
if %FOUND_STR_REGRESSION_COMP%==1  	goto SQL_INPUT_FILE_VALIDATE
if %FOUND_STR_PERFORMANCE_EXEC%==1  goto SQL_INPUT_FILE_VALIDATE
if %FOUND_STR_PERFORMANCE_COMP%==1  goto SQL_INPUT_FILE_VALIDATE
goto SQL_INPUT_FILE_CONTINUE
:SQL_INPUT_FILE_VALIDATE
	if not exist "%MIGRATION_TEST_SQL%\%MIGRATION_TEST_SQL_FILE_NAME%"	set errorMsg=!errorMsg!%TAB%The SQL input file does not exist: "%MIGRATION_TEST_SQL%\%MIGRATION_TEST_SQL_FILE_NAME%"!LF!
:SQL_INPUT_FILE_CONTINUE


REM # Set the log file name
call :replace .dp "" "%DEPLOYMENT_PLAN%" BusLineBusAreaSubjArea_Log_Name 
	SET LOG_FILE_NAME=%CONFIG_PROPERTY_NAME%_%BusLineBusAreaSubjArea_Log_Name%%CUSTOM%
	SET XSL_LOG_FILE_NAME=
	if "%XSL_PERFORM%" == "ONLY"   SET XSL_LOG_FILE_NAME=%LOG_FILE_NAME%.log
	if "%XSL_PERFORM%" == "BEFORE" SET XSL_LOG_FILE_NAME=%LOG_FILE_NAME%_XSL_BEFORE.log
	if "%XSL_PERFORM%" == "AFTER"  SET XSL_LOG_FILE_NAME=%LOG_FILE_NAME%_XSL_AFTER.log
	SET LOG_FILE_NAME=%LOG_FILE_NAME%.log
	
REM # Check to see if any error messages have been compiled.
if "!errorMsg!" NEQ "" goto USAGE
set ERROR=0


REM # Display variables
echo.Input Variables:..............
echo.ENV_TYPE=                     %ENV_TYPE%
echo.DEPLOYMENT_PLAN=              %DEPLOYMENT_PLAN%
echo.CUSTOM=                       %CUSTOM%
echo.RENAME_REL=                   %RENAME_REL%
echo.PAUSE=                        %PAUSE%
echo.
echo.Dervied Variables:............
echo.Test Type=                    %MIGRATION_TEST_TYPE%
echo.BusLineBusAreaSubjArea=       %BusLineBusAreaSubjArea%
echo.Query Style=                  %CUSTOM_TEXT1%
echo.RENAME_REL Override Command=  %RENAME_FOLDER_CMD%
echo.
echo.Path Information:.............
echo.MIGRATION_TEST_PLANS=         %MIGRATION_TEST_PLANS%
echo.DEPLOYMENT_PLAN=              %MIGRATION_TEST_PLANS%\%DEPLOYMENT_PLAN%
echo.MIGRATION_TEST_MODULES=       %MIGRATION_TEST_MODULES%
echo.MODULE_NAME=                  %MIGRATION_TEST_MODULES%\%MODULE_NAME%
echo.MIGRATION_TEST_SQL=           %MIGRATION_TEST_SQL%
echo.MIGRATION_TEST_SQL_FILE_NAME= %MIGRATION_TEST_SQL%\%MIGRATION_TEST_SQL_FILE_NAME%
echo.MIGRATION_TEST_OUTPUT=        %MIGRATION_TEST_OUTPUT%
echo.RELEASE_FOLDER1 Location=     %MIGRATION_TEST_OUTPUT%\%BusLineBusAreaSubjArea%\%RELEASE_FOLDER1%
echo.RELEASE_FOLDER2 Location=     %MIGRATION_TEST_OUTPUT%\%BusLineBusAreaSubjArea%\%RELEASE_FOLDER2%
echo.MIGRATION_TEST_LOGS=          %MIGRATION_TEST_LOGS%
echo.MIGRATION_TEST_LOG_FILE=      %MIGRATION_TEST_LOGS%\%LOG_FILE_NAME%
if "%XSL_LOG_FILE_NAME%" == ""  echo.XSL_LOG_FILE_NAME=
if "%XSL_LOG_FILE_NAME%" NEQ "" echo.XSL_LOG_FILE_NAME=            %MIGRATION_TEST_LOGS%\%XSL_LOG_FILE_NAME%
echo.PDTOOL_INSTALL_HOME=          %PDTOOL_INSTALL_HOME%
echo.PDTool Execution Directory=   %PDTOOL_INSTALL_HOME%\bin
echo.
REM # Check if pause is required
if "%PAUSE%" == "TRUE" pause


REM #########################################################
REM Execute the PDTool command
REM #########################################################
REM # Invoke the regular PDTool command when not processing server attributes
if "%SERVER_ATTRIBUTE_CMD%" == "FALSE" CALL :EXEC_PDTOOL ERROR

REM # Invoke the special PDTool command when processing server attributes
REM #   which will perform an XML transformation on the server attribute module XML.
if "%SERVER_ATTRIBUTE_CMD%" == "TRUE" CALL :EXEC_PDTOOL_SERVER_ATTRIBUTES ERROR
goto END

REM #########################################################
REM # Usage function
REM #########################################################
:USAGE
	echo.ERROR:
	echo.!errorMsg!
	echo.
	echo.USAGE: %INVOKING_SCRIPT% [%VALID_ENV_TYPES%] [DEPLOYMENT_PLAN] [CUSTOM] [RENAME_REL] [PAUSE]
	set ERROR=1
	
REM # End function
:END
echo.########################################################################################################################################
ENDLOCAL &set ERROR=%ERROR%

REM # Initialize variables
set ATF_HOME=
set PDTOOL_INSTALL_HOME=
set PDTOOL_INSTALL_HOME_6=
set PDTOOL_INSTALL_HOME_7=
set VALID_ENV_CONFIG_PAIRS=
set VALID_ENV_CONFIG_PAIRS_6=
set VALID_ENV_CONFIG_PAIRS_7=
set EDITOR=
set SCRIPT_ACTIVITY=
set RELEASE_FOLDER1=
set RELEASE_FOLDER2=
set DEBUG=
set SCRIPT_CIS_VERSION=
set XSL_PERFORM=
set XSL_TRANSFORM_SCRIPT=
rem set XSL_FROM_CIS_VERSION=
set SOURCE_SERVER_ATTRIBUTE_FILENAME=
set SCRIPT_ORIGINAL_DEFINITION=
set SCRIPT_ORIGINAL_INVOCATION=
set DEPLOYMENT_PLAN=

REM # Exit the script
exit /b %ERROR%



REM ##############################################################################################
REM
REM BEGIN: FUNCTION IMPLEMENTATION
REM
REM ##############################################################################################


REM # Usage function
:USAGE
	echo.ERROR:
	echo.!errorMsg!
	echo.
	echo.USAGE: %INVOKING_SCRIPT% [%VALID_ENV_TYPES%] [DEPLOYMENT_PLAN] [CUSTOM] [RENAME_REL] [PAUSE]
	set ERROR=1
	goto END

::=====================================================================
:EXEC_PDTOOL
::=====================================================================
:: Execute PDTool
	REM #########################################################
	REM Rename the release folders and create a history
	REM Rules:
	REM    1) If RELEASE_FOLDER does not exist then
	REM       No backups are created
	REM 
	REM    2) IF RELEASE_FOLDER exists then 
	REM         Rename to RELEASE_FOLDER.000N
	REM #########################################################
	if "%RENAME_FOLDER_CMD%" == "TRUE" CALL :RENAME_FOLDER_MIGRATION %RELEASE_FOLDER%

	echo.
	echo.---------------------------------------------------------------------------------------------------------------------------------------- 
	REM ### INVOKE PDTOOL ###
	echo.#  EXECUTE PDTOOL FOR ENV=%ENV_TYPE%  TIMESTAMP=%DATE% %TIME%  #
	pushd %CD%
	cd "%PDTOOL_INSTALL_HOME%\bin"
	echo.%DATE% %TIME% COMMAND: call ExecutePDTool.bat -exec "%MIGRATION_TEST_PLANS%\%DEPLOYMENT_PLAN%" -config %CONFIG_PROPERTY_NAME%.properties > "%MIGRATION_TEST_LOGS%\%LOG_FILE_NAME%"
	call ExecutePDTool.bat -exec "%MIGRATION_TEST_PLANS%\%DEPLOYMENT_PLAN%" -config %CONFIG_PROPERTY_NAME%.properties > "%MIGRATION_TEST_LOGS%\%LOG_FILE_NAME%"
	set ERROR=%ERRORLEVEL%
	popd
	if %ERROR% NEQ 0 echo.%DATE% %TIME% ERROR: %ENV_TYPE% - An Error occurred for activity: %SCRIPT_ACTIVITY%
	if %ERROR% EQU 0 (
	   echo.%DATE% %TIME% SUCCESS: %ENV_TYPE% - Activity: %SCRIPT_ACTIVITY%
	)
	set %1=%ERROR%
GOTO:EOF


::=====================================================================
:EXEC_PDTOOL_SERVER_ATTRIBUTES
::=====================================================================
:: Execute PDTool and transform the server attribute XML

	REM # The following test are to look for deployment plans not supported for Migration
	SET SERVER_ATTRIBUTE_TEST=
	SET FOUND_STR=0
	call :findString %DEPLOYMENT_PLAN% TEST FOUND_STR
	if %FOUND_STR%==1 SET SERVER_ATTRIBUTE_TEST=TEST_
	REM # If the source server attribute filename is not defined then constuct the name from various variables
	if "%SOURCE_SERVER_ATTRIBUTE_FILENAME%" == "" SET SOURCE_SERVER_ATTRIBUTE_FILENAME_BASE=ServerAttributes_%SERVER_ATTRIBUTE_TEST%%ENV_TYPE%
	REM # If the source server attribute filename *is* defined then use the name as is without the .xml extension
	if "%SOURCE_SERVER_ATTRIBUTE_FILENAME%" NEQ "" call :replace ".xml" "" "%SOURCE_SERVER_ATTRIBUTE_FILENAME%" SOURCE_SERVER_ATTRIBUTE_FILENAME_BASE 
	REM # Construct the output paths
	set MIGRATION_TEST_SERVER_ATTRIBUTE_OUTPUT_BASE=%MIGRATION_TEST_MODULES%/%SOURCE_SERVER_ATTRIBUTE_FILENAME_BASE%
	set MIGRATION_TEST_SERVER_ATTRIBUTE_OUTPUT_FILE=%MIGRATION_TEST_SERVER_ATTRIBUTE_OUTPUT_BASE%.xml
	set MIGRATION_TEST_SERVER_ATTRIBUTE_OUTPUT_UPDATE_FILE=%MIGRATION_TEST_SERVER_ATTRIBUTE_OUTPUT_BASE%_xform.xml
	echo.
	echo.---------------------------------------------------------------------------------------------------------------------------------------- 
	
	REM ### IF XSL_PERFORM=ONLY THEN perform XSL Transformation and goto the end ###
	if "%XSL_PERFORM%" == "ONLY" CALL :EXEC_XSL_TRANSFORMATION_SERVER_ATTRIBUTES ERROR
	if "%XSL_PERFORM%" == "ONLY" goto EXEC_PDTOOL_SERVER_ATTRIBUTES_END

	REM ### IF XSL_PERFORM=BEFORE THEN perform XSL Transformation before PDTool ###
	if "%XSL_PERFORM%" == "BEFORE" CALL :EXEC_XSL_TRANSFORMATION_SERVER_ATTRIBUTES ERROR
	if %ERROR% NEQ 0 goto EXEC_PDTOOL_SERVER_ATTRIBUTES_END
	if "%XSL_PERFORM%" == "BEFORE" echo.
	
	REM ### INVOKE PDTOOL ###
:EXEC_PDTOOL_SERVER_ATTRIBUTES_INVOKE
	echo.#  EXECUTE PDTOOL FOR ENV=%ENV_TYPE%  TIMESTAMP=%DATE% %TIME%  #
	pushd %CD%
	cd "%PDTOOL_INSTALL_HOME%\bin"
	echo.%DATE% %TIME% COMMAND: call ExecutePDTool.bat -exec "%MIGRATION_TEST_PLANS%\%DEPLOYMENT_PLAN%" -config %CONFIG_PROPERTY_NAME%.properties > "%MIGRATION_TEST_LOGS%\%LOG_FILE_NAME%"
	call ExecutePDTool.bat -exec "%MIGRATION_TEST_PLANS%\%DEPLOYMENT_PLAN%" -config %CONFIG_PROPERTY_NAME%.properties > "%MIGRATION_TEST_LOGS%\%LOG_FILE_NAME%"
	set ERROR=%ERRORLEVEL%
	popd
	if %ERROR% NEQ 0 (
		echo.%DATE% %TIME% ERROR: %ENV_TYPE% - An Error occurred for activity: %SCRIPT_ACTIVITY%
		goto EXEC_PDTOOL_SERVER_ATTRIBUTES_END
	)
	echo.%DATE% %TIME% SUCCESS: %ENV_TYPE% - Activity: %SCRIPT_ACTIVITY% - Server Attributes
	echo.%DATE% %TIME% SUCCESS: %ENV_TYPE% - Executed: %DEPLOYMENT_PLAN%

	REM ### IF XSL_PERFORM=AFTER THEN PERFORM XSL TRANSFORMATION AFTER PDTOOL ###
	if "%XSL_PERFORM%" == "AFTER" echo.
	if "%XSL_PERFORM%" == "AFTER" CALL :EXEC_XSL_TRANSFORMATION_SERVER_ATTRIBUTES ERROR

:EXEC_PDTOOL_SERVER_ATTRIBUTES_END
	set %1=%ERROR%
GOTO:EOF


::=====================================================================
:EXEC_XSL_TRANSFORMATION_SERVER_ATTRIBUTES
::=====================================================================
:: Execute the XSL transform the server attribute XML
	echo.########################################################################################################################################> "%MIGRATION_TEST_LOGS%\%XSL_LOG_FILE_NAME%"
	set COMMAND=#  XSL TRANSFORMATION %XSL_PERFORM% FOR ENV=%ENV_TYPE%  TIMESTAMP=%DATE% %TIME%  #
	echo.%COMMAND%>> "%MIGRATION_TEST_LOGS%\%XSL_LOG_FILE_NAME%"
	echo.%COMMAND%
	echo.########################################################################################################################################>> "%MIGRATION_TEST_LOGS%\%XSL_LOG_FILE_NAME%"
	set MSG=
	if not exist "%MIGRATION_TEST_XSLT_HOME%\XsltTransform.bat" (
		set ERROR=1
		set MSG=ERROR: The migration XSLT batch file does not exist: "%MIGRATION_TEST_XSLT_HOME%\XsltTransform.bat"
		goto EXEC_XSL_TRANSFORMATION_SERVER_ATTRIBUTES_END
	)
	if not exist "%MIGRATION_TEST_XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%" (
		set ERROR=1
		set MSG=ERROR: The migration XSLT transformation file does not exist: "%MIGRATION_TEST_XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%"
		goto EXEC_XSL_TRANSFORMATION_SERVER_ATTRIBUTES_END
	)
	
	set COMMAND=%DATE% %TIME% COMMAND: call "%MIGRATION_TEST_XSLT_HOME%\XsltTransform.bat" "%MIGRATION_TEST_SERVER_ATTRIBUTE_OUTPUT_FILE%"  "%MIGRATION_TEST_XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%"  "%MIGRATION_TEST_SERVER_ATTRIBUTE_OUTPUT_UPDATE_FILE%"
	echo.%COMMAND%>> "%MIGRATION_TEST_LOGS%\%XSL_LOG_FILE_NAME%"
	echo.%COMMAND%
	call "%MIGRATION_TEST_XSLT_HOME%\XsltTransform.bat" "%MIGRATION_TEST_SERVER_ATTRIBUTE_OUTPUT_FILE%"  "%MIGRATION_TEST_XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%"  "%MIGRATION_TEST_SERVER_ATTRIBUTE_OUTPUT_UPDATE_FILE%"
	set ERROR=%ERRORLEVEL%
	if %ERROR% NEQ 0 set MSG=%DATE% %TIME% ERROR: %ENV_TYPE% - An Error occurred transforming "%MIGRATION_TEST_SERVER_ATTRIBUTE_OUTPUT_FILE%"
	if %ERROR% EQU 0 (
		SET MSG=!MSG!%DATE% %TIME% SUCCESS: %ENV_TYPE% - Transformed: SOURCE="%MIGRATION_TEST_SERVER_ATTRIBUTE_OUTPUT_FILE%"!LF!
		SET MSG=!MSG!%DATE% %TIME% SUCCESS: %ENV_TYPE% - Transformed: TARGET="%MIGRATION_TEST_SERVER_ATTRIBUTE_OUTPUT_UPDATE_FILE%"
	)
:EXEC_XSL_TRANSFORMATION_SERVER_ATTRIBUTES_END
	if defined MSG echo.!MSG! >> "%MIGRATION_TEST_LOGS%\%XSL_LOG_FILE_NAME%"
	echo.########################################################################################################################################>> "%MIGRATION_TEST_LOGS%\%XSL_LOG_FILE_NAME%"
	echo.!MSG!
	set %1=%ERROR%
GOTO:EOF


::=====================================================================
:EXTRACT_ENV_TYPE_LIST
::=====================================================================
:: Extract the environment type list from the environment/configuration 
::   Property File Name pair combinations variable: VALID_ENV_CONFIG_PAIRS
::   Format of pairs: DEV=deploy_DEV,UAT=deploy_UAT,PROD=deploy_PROD
::
set FUNCTION_NAME1=[DEBUG] EXTRACT_ENV_TYPE_LIST      
set FUNCTION_NAME2=[DEBUG] PARSE_EXTRACT_ENV_TYPE_LIST
set FUNCTION_NAME3=[DEBUG] SUB_EXTRACT_ENV_TYPE_LIST  

SETLOCAL EnableDelayedExpansion
REM Get input parameter and remove double quotes
set _INP_VALID_ENV_TYPES=%1
set _INP_VALID_ENV_TYPES=!_INP_VALID_ENV_TYPES:"=!
REM Set default values
set _VALID_ENV_LIST=

call :PARSE_EXTRACT_ENV_TYPE_LIST %1 _VALID_ENV_LIST

if %DEBUG%==Y echo.%FUNCTION_NAME1% output 4: LIST=%_VALID_ENV_LIST%
ENDLOCAL & SET _VALID_ENV_LIST=%_VALID_ENV_LIST%
if %DEBUG%==Y echo.%FUNCTION_NAME1% output 5: LIST=%_VALID_ENV_LIST%
SET %2=%_VALID_ENV_LIST%
GOTO:EOF

:PARSE_EXTRACT_ENV_TYPE_LIST
REM Get input parameters and remove double quotes
set _INP_VALID_ENV_TYPES=%1
set _INP_VALID_ENV_TYPES=!_INP_VALID_ENV_TYPES:"=!

REM Display parameters
if %DEBUG%==Y echo.%FUNCTION_NAME2%   params: _INP_VALID_ENV_TYPES=%_INP_VALID_ENV_TYPES%

FOR /f "eol=; tokens=1* delims=," %%a IN ("!_INP_VALID_ENV_TYPES!") DO (
    if %DEBUG%==Y echo.%FUNCTION_NAME2% output 2: LIST a=%%a   LIST b=%%b
	if not "%%a" == "" call :SUB_EXTRACT_ENV_TYPE_LIST %%a  
	if not "%%b" == "" call :PARSE_EXTRACT_ENV_TYPE_LIST "%%b"
	if %DEBUG%==Y echo.%FUNCTION_NAME2% output 2: LIST=%_VALID_ENV_LIST%
)
goto:eos

:SUB_EXTRACT_ENV_TYPE_LIST
	for /f "tokens=1,2 delims=%TOKEN%" %%a in ("%1") do set _ENV_TYPE=%%a&set _CONFIG_NAME=%%b
	REM Set the environment type list
	if "%_VALID_ENV_LIST%" NEQ "" set _VALID_ENV_LIST=%_VALID_ENV_LIST%,
	if "!_ENV_TYPE!" NEQ "" set _VALID_ENV_LIST=%_VALID_ENV_LIST%!_ENV_TYPE!
	if %DEBUG%==Y echo.%FUNCTION_NAME3% output 1: LIST=%_VALID_ENV_LIST%
GOTO:EOF

:eos
if %DEBUG%==Y echo.%FUNCTION_NAME1% output 3: LIST=%_VALID_ENV_LIST%
GOTO:EOF



::=====================================================================
:VALIDATE_CONFIG_PROPERTY_NAME
::=====================================================================
:: Parse and validate the environment/configuration Property File Name pair combinations
::   Format of pairs: DEV=deploy_DEV,UAT=deploy_UAT,PROD=deploy_PROD
::
set FUNCTION_NAME1=[DEBUG] VALIDATE_CONFIG_PROPERTY_NAME      
set FUNCTION_NAME2=[DEBUG] PARSE_VALIDATE_CONFIG_PROPERTY_NAME
set FUNCTION_NAME3=[DEBUG] SUB_VALIDATE_CONFIG_PROPERTY_NAME  

SETLOCAL EnableDelayedExpansion
REM Get input parameters
set _INP_ENV_TYPE=%1
set _INP_VALID_ENV_TYPES=%2
REM Remove double quotes
set _INP_ENV_TYPE=!_INP_ENV_TYPE:"=!
set _INP_VALID_ENV_TYPES=!_INP_VALID_ENV_TYPES:"=!
REM Set default values
set _VALID_ENV=0
set _CONFIG_PROPERTY_NAME=

call :PARSE_VALIDATE_CONFIG_PROPERTY_NAME %1 %2 _VALID_ENV _CONFIG_PROPERTY_NAME

if %DEBUG%==Y echo.%FUNCTION_NAME1% output 4: %_VALID_ENV% %_CONFIG_PROPERTY_NAME%
ENDLOCAL & SET _VALID_ENV=%_VALID_ENV%& SET _CONFIG_PROPERTY_NAME=%_CONFIG_PROPERTY_NAME%
if %DEBUG%==Y echo.%FUNCTION_NAME1% output 5: %_VALID_ENV% %_CONFIG_PROPERTY_NAME%
SET %3=%_VALID_ENV%
SET %4=%_CONFIG_PROPERTY_NAME%
GOTO:EOF

:PARSE_VALIDATE_CONFIG_PROPERTY_NAME
REM Get input parameters
set _INP_ENV_TYPE=%1
set _INP_VALID_ENV_TYPES=%2
REM Remove double quotes
set _INP_ENV_TYPE=!_INP_ENV_TYPE:"=!
set _INP_VALID_ENV_TYPES=!_INP_VALID_ENV_TYPES:"=!

REM Display parameters
if %DEBUG%==Y echo.%FUNCTION_NAME2%   params:_INP_ENV_TYPE=%_INP_ENV_TYPE%    _INP_VALID_ENV_TYPES=%_INP_VALID_ENV_TYPES%

FOR /f "eol=; tokens=1* delims=," %%a IN ("!_INP_VALID_ENV_TYPES!") DO (
    if %DEBUG%==Y echo.%FUNCTION_NAME2%  input 1: _INP_ENV_TYPE=%_INP_ENV_TYPE%    LIST a=%%a   LIST b=%%b
	if not "%%a" == "" call :SUB_VALIDATE_CONFIG_PROPERTY_NAME !_INP_ENV_TYPE! %%a  
	if not "%%b" == "" call :PARSE_VALIDATE_CONFIG_PROPERTY_NAME "!_INP_ENV_TYPE!" "%%b"
	if %DEBUG%==Y echo.%FUNCTION_NAME2% output 2: %_VALID_ENV% %_CONFIG_PROPERTY_NAME%
)
goto:eos

:SUB_VALIDATE_CONFIG_PROPERTY_NAME
	set _INP_ENV_TYPE=%1
	for /f "tokens=1,2 delims=%TOKEN%" %%a in ("%2") do set _ENV_TYPE=%%a&set _CONFIG_NAME=%%b
	if "%_INP_ENV_TYPE%" == "!_ENV_TYPE!" set _VALID_ENV=1
    if "%_INP_ENV_TYPE%" == "!_ENV_TYPE!" set _CONFIG_PROPERTY_NAME=!_CONFIG_NAME!
	if %DEBUG%==N goto NO_DEBUG
	   echo.%FUNCTION_NAME3% output 1: ENV_TYPE=[!_ENV_TYPE!]    CONFIG_NAME=[!_CONFIG_NAME!]
	   echo.%FUNCTION_NAME3% output 1: MATCH FOUND FOR ENV_TYPE=!_ENV_TYPE!
	   echo.%FUNCTION_NAME3% output 1: %_VALID_ENV% %_CONFIG_PROPERTY_NAME%
	:NO_DEBUG
GOTO:EOF

:eos
if %DEBUG%==Y echo.%FUNCTION_NAME1% output 3: %_VALID_ENV% %_CONFIG_PROPERTY_NAME%
GOTO:EOF



::=====================================================================
:LCase
:UCase
::=====================================================================
:: Converts to upper/lower case variable contents
:: Syntax: CALL :UCase _VAR1 _VAR2
:: Syntax: CALL :LCase _VAR1 _VAR2
:: _VAR1 = Variable NAME whose VALUE is to be converted to upper/lower case
:: _VAR2 = NAME of variable to hold the converted value
:: Note: Use variable NAMES in the CALL, not values (pass "by reference")

SET _UCase=A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
SET _LCase=a b c d e f g h i j k l m n o p q r s t u v w x y z
SET _Lib_UCase_Tmp=!%1!

IF /I "%0"==":UCase" SET _Abet=%_UCase%
IF /I "%0"==":LCase" SET _Abet=%_LCase%
FOR %%Z IN (%_Abet%) DO SET _Lib_UCase_Tmp=!_Lib_UCase_Tmp:%%Z=%%Z!
SET %2=%_Lib_UCase_Tmp%
GOTO:EOF


::=====================================================================
:replace
::=====================================================================
:: replace - parses a string and replaces old string with new string
::           and returns the value in the outvariable that gets passed in
:: syntax:  call :replace "oldstring" "newstring" "searchstring" outvariable
:: example: call :replace "_" "__" "%searchStr%" outStr 
:: OldStr [in] - string to be replaced
:: NewStr [in] - string to replace with
:: SearchStr [in] - String to search
:: outvar [out] - name of the variable to place the results
::
:: Remove double quotes (") for incoming SearchStr argument
	SET _SearchStr=%3
	SET _SearchStr=###%_SearchStr%###
	SET _SearchStr=%_SearchStr:"###=%
	SET _SearchStr=%_SearchStr:###"=%
	if "%_SearchStr%" NEQ "" (
	   SET _SearchStr=%_SearchStr:###=%
	)
	if "%_SearchStr%" NEQ "" (
	   set _SearchStr=%_SearchStr:""="%
	)
	:: Replace old string with new string within search string
	if "%~1"=="" findstr "^::" "%~f0"&GOTO:EOF
	   for /f "tokens=1,* delims=]" %%A in ('"echo.%_SearchStr%|find /n /v """') do (
		 set "line=%%B"
		 if defined line (
			call set "%4=%%line:%~1=%~2%%"
		 ) ELSE (
			call set "%4="
		 )
	   )
GOTO:EOF


::=====================================================================
:RENAME_FOLDER_MIGRATION
::=====================================================================
:: Rename the release folder if it exists
::     1) If RELEASE_FOLDER does not exist then
::        No backups are created
::  
::     2) IF RELEASE_FOLDER exists then 
::          Rename to RELEASE_FOLDER.000N
set RELEASE_FOLDER=%1
set FUNCTION_NAME1=[DEBUG] RENAME_FOLDER
set FUNCTION_NAME2=[DEBUG] RENAME       
if %DEBUG%==Y echo.%FUNCTION_NAME1%    BEGIN: ------------------------------------------
	set TAB1=    
	set TAB2=          
	echo.%TAB1%================================================================================
	echo.%TAB1%RENAME_FOLDER:Log...
	echo.
	if not exist "%MIGRATION_TEST_OUTPUT%\%BusLineBusAreaSubjArea%\%RELEASE_FOLDER%" (
	   echo.%TAB1%RELEASE_FOLDER=%RELEASE_FOLDER% does not exist.  No action taken.
	   goto :END_RENAME_FOLDER
	)

	REM # Create a history of release folders:
	REM # If RELEASE_FOLDER exists then rename it to RELEASE_FOLDER.000N where N is the number of RELEASE_FOLDER folders in the directory. 
	SetLocal EnableDelayedExpansion
	set cnt=0
	for /F %%a IN ('dir /b "%MIGRATION_TEST_OUTPUT%\%BusLineBusAreaSubjArea%\%RELEASE_FOLDER%*"') do (
	   set /a cnt+=1
	   if "%DEBUG%" == "Y" echo.%FUNCTION_NAME1%    EXIST: cnt=%%a - !cnt!
	)
	if "%DEBUG%" == "Y" echo.%FUNCTION_NAME1%    EXIST: cnt=%cnt%
	if %cnt% GTR 0 (
	   set padcnt=%cnt%
	   call :padStringLeft padcnt 4 0
	   if "%DEBUG%" == "Y" echo.%FUNCTION_NAME1%    EXIST: padcnt=!padcnt!
	   if "%DEBUG%" == "Y" echo.%FUNCTION_NAME1%    EXIST: source=%MIGRATION_TEST_OUTPUT%\%BusLineBusAreaSubjArea%\%RELEASE_FOLDER%
	   if "%DEBUG%" == "Y" echo.%FUNCTION_NAME1%    EXIST: target=%MIGRATION_TEST_OUTPUT%\%BusLineBusAreaSubjArea%\%RELEASE_FOLDER%.!padcnt!
	   echo.%TAB1%Backing up %RELEASE_FOLDER% to %RELEASE_FOLDER%.!padcnt!
	   echo.%TAB1%call :RENAME "%MIGRATION_TEST_OUTPUT%\%BusLineBusAreaSubjArea%\%RELEASE_FOLDER%" "%MIGRATION_TEST_OUTPUT%\%BusLineBusAreaSubjArea%\%RELEASE_FOLDER%.!padcnt!"
	   call :RENAME "%MIGRATION_TEST_OUTPUT%\%BusLineBusAreaSubjArea%\%RELEASE_FOLDER%" "%MIGRATION_TEST_OUTPUT%\%BusLineBusAreaSubjArea%\%RELEASE_FOLDER%.!padcnt!"
	)

:END_RENAME_FOLDER
echo.%TAB1%================================================================================
if %DEBUG%==Y echo.%FUNCTION_NAME1%      END: ------------------------------------------
if %DEBUG%==Y echo.
GOTO:EOF


::=====================================================================
:RENAME sourceDir targetDir
::-- Uses Copy and Remove to rename due to errors 
::   occurring with RENAME and "Access is Denied"
::=====================================================================
::  -- sourceDir [in] - The source directory path
::  -- targetDir [in] - The target directory path
	SET ARG1=%1
	SET ARG2=%2
	SET TARG1=
	SET TARG2=
	set cnt=0
	REM # Remove double quotes
	setlocal EnableDelayedExpansion
	if defined ARG1 set TARG1=!ARG1:"=!
	if defined ARG2 set TARG2=!ARG2:"=!
	endlocal & SET ARG1=%TARG1%& SET ARG2=%TARG2%
	set sourceDir=%ARG1%
	set targetDir=%ARG2%
	if "%DEBUG%" == "Y" echo.%FUNCTION_NAME2%   rename: sourceDir=%sourceDir%
	if "%DEBUG%" == "Y" echo.%FUNCTION_NAME2%   rename: targetDir=%targetDir%
	echo.%TAB2%RENAME: robocopy /MOVE /E /NFL /NDL /NJH /NJS "%sourceDir%" "%targetDir%"
	robocopy /MOVE /E /NFL /NDL /NJH /NJS "%sourceDir%" "%targetDir%"

:REMOVE_DIR
	if exist "%sourceDir%" (
	    set /a cnt+=1
		if %cnt% GTR 20 goto RENAME_ERROR
		echo.%TAB2%RENAME: rmdir /S /Q "%sourceDir%"
		rmdir /S /Q "%sourceDir%"
		if exist "%sourceDir%" GOTO REMOVE_DIR
	)
	GOTO RENAME_END
	
:RENAME_ERROR
	echo.
	echo.%TAB2%RENAME: WARNING...Unable to remove "%sourceDir%". 
	echo.%TAB2%RENAME: WARNING...PDTool Regression Scripts will overwrite "%sourceDir%". 
	echo.
:RENAME_END
GOTO:EOF


::=====================================================================
:padStringLeft string width padCharacter
::-- pads the front of a string with specified characters to the width specified
::=====================================================================
::  -- string [in,out] - time to be formatted, mutated
::  -- width [in] - width of resulting string
::  -- character [in,opt] - character to pad with, default is space
	SETLOCAL ENABLEDELAYEDEXPANSION
	call set inStr=%%%~1%%
	set width=%2
	set /a width-=1
	set pad=%3
	if "%pad%"=="" set pad= &::space
	for /l %%i in (0,1,%width%) do (
	   if "!inStr:~%%i,1!"=="" set inStr=%pad%!inStr!
	)
	( ENDLOCAL & REM -- RETURN VALUES
	   IF "%~1" NEQ "" SET "%~1=%inStr%"
	)
GOTO:EOF


::=====================================================================
:findString
::=====================================================================
:: Find a string within another string
::    return 0=string not found
::    return 1=string found
set FUNCTION_NAME1=[DEBUG] findString
set stringToSearch=%1
set findStr=%2
set _return=0

if %DEBUG%==Y echo.%FUNCTION_NAME1%    BEGIN: ------------------------------------------
if %DEBUG%==Y echo.%FUNCTION_NAME1% output 1: findStr=%findStr%    stringToSearch=%stringToSearch%

Echo.%stringToSearch% | findstr /C:"%findStr%">nul && (
	set _return=1
) || (
	set _return=0
)
if %_return%==1 set msg=FOUND: %findStr%
if %_return%==0 set msg=NOT FOUND: %findStr%
if %DEBUG%==Y echo.%FUNCTION_NAME1% output 2: %msg%

endlocal & SET _return=%_return%
if %DEBUG%==Y echo.%FUNCTION_NAME1%   RETURN: %_return%
if %DEBUG%==Y echo.%FUNCTION_NAME1%      END: ------------------------------------------
if %DEBUG%==Y echo.
set %3=%_return%
GOTO:EOF


::=======================================================
:padHeader header content new_header new_content ERROR
::=======================================================
:: Description - Pad a two line header so that both header rows
::   are aligned one on top of the other. 
:: Invocation: call :padHeader "%inHeader%" "%inContent%" header content ERROR
::   header - [in] This is the first header row with static field names.
::   content - [in] This is the second header row with the variable content.
::   new_header - [out] This is the new padded header.
::   new_content - [out] This is the new padded content row.
::   ERROR - [out] This provides an error code. 1=error, 0=success.
set header=%1
set content=%2
if defined header set header=!header:"=!
if defined content set content=!content:"=!
rem echo  in_header=%header%
rem echo in_content=%content%
rem echo.
set max_params=7
set /A counter=0
set new_header=
set new_content=
set ERROR=0

SETLOCAL ENABLEDELAYEDEXPANSION

:PAD_LOOP
set /A counter+=1
rem for /f "tokens=1-8 delims= " %%a in ("%str%") do (
for /f "tokens=1,*" %%i in ("%header%")  do set ih=%%i&set jh=%%j
for /f "tokens=1,*" %%m in ("%content%") do set mc=%%m&set nc=%%n
rem	echo %counter%   ih=%ih%  jh=%jh%
rem	echo %counter%   mc=%mc%  nn=%nc%
	set header=%jh%
	set content=%nc%

	rem Process
	CALL :strlen ih il
	CALL :strlen mc ml
	CALL :compareLen %il% %ml% len
	set /A len+=2
	CALL :RPAD ih %len%
	CALL :RPAD mc %len%
	set new_header=%new_header%%ih%
	set new_content=%new_content%%mc%
	set ih=
	set mc=
	
	rem Check if there are any more parameters
	set /A blank=0
	if "%jh%" == "" set /A blank+=1
	if "%nc%" == "" set /A blank+=1
	if %blank%==2 goto PAD_LOOP_CHECK
	if %counter% GTR %max_params% goto PAD_LOOP_CHECK
	GOTO:PAD_LOOP

:PAD_LOOP_CHECK
	if %counter% GTR %max_params% (
		echo Error...Additional parameters detected greater than the %max_params% allowed.
		set ERROR=1
		goto PAD_LOOP_END
	)
:PAD_LOOP_END

ENDLOCAL &set ERROR=%ERROR%&set new_header=%new_header%&set new_content=%new_content%
rem echo ERROR=%ERROR%
set %3=%new_header%
set %4=%new_content%
set %5=%ERROR%
GOTO:EOF



::=======================================================
:strLen string len 
::=======================================================
::  Returns the length of a string
::  Description: call:strLen string len  
::  -- string [in]  - variable name containing the string being measured for length
::  -- len    [out] - variable to be used to return the string length
(   SETLOCAL ENABLEDELAYEDEXPANSION
    set "str=A!%~1!"&rem keep the A up front to ensure we get the length and not the upper bound
                     rem it also avoids trouble in case of empty string
    set "len=0"
    for /L %%A in (12,-1,0) do (
        set /a "len|=1<<%%A"
        for %%B in (!len!) do if "!str:~%%B,1!"=="" set /a "len&=~1<<%%A"
    )
)
( ENDLOCAL & REM RETURN VALUES
    IF "%~2" NEQ "" SET /a %~2=%len%
)
GOTO:EOF


::=======================================================
:compareLen len1 len2 result
::=======================================================
::  Compare two numbers and return the greater number
::  Description: call:compareLen len1 len2 result
::  -- len1 [in] - length param 1
::  -- len2 [in] - length param 2
::  -- result [in,out] - resulting number is the greater of the two
set /A len1=%1
set /A len2=%2
if %len1% GEQ %len2% SET result=%len1%
if %len2% GEQ %len1% SET result=%len2%
set %3=%result%
GOTO:EOF


::=======================================================
:RPAD string width padCharacter
::    author: wwjdcsk
::                 -- pads the back of a string with specified characters to the width specified
::=======================================================
::  -- string [in,out] - time to be formatted, mutated
::  -- width [in] - width of resulting string
::  -- character [in,opt] - character to pad with, default is space

SETLOCAL ENABLEDELAYEDEXPANSION
call set inStr=%%%~1%%
set width=%2
set /a width-=1
set pad=%3
if "%pad%"=="" set pad= &::space

for /l %%i in (0,1,%width%) do (
   if "!inStr:~%%i,1!"=="" set inStr=!inStr!%pad%
)
( ENDLOCAL & REM -- RETURN VALUES    
   IF "%~1" NEQ "" SET "%~1=%inStr%"
)
GOTO:EOF


REM ##############################################################################################
REM
REM END: FUNCTION IMPLEMENTATION
REM
REM ##############################################################################################
