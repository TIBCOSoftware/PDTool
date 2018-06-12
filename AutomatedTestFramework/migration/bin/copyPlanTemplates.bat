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
REM # Date:   June 2015
REM # PDTool Regression Module - Migration Automated Test Framework
REM #=======================================================================================
REM # copyPlanTemplates.bat
REM #
REM # Purpose:
REM #   The purpose of this batch file is to make it easy to copy plan templates for doing regression testing.
REM #   This batch file will automatically generate the PDTool Regression Module scripts for a given 
REM #   project/business line/business area/subject area as represented by the published database, catalog and schema.
REM #   This batch file will generate the necessary plan files, modules XML file and documentation from templates
REM #   found in the templates folder.
REM # Note: Executing this batch file more than once will overwrite any existing generated files.
REM #
REM #  Instructions:
REM # Automated:
REM #    copyPlanTemplates.bat [PROMPT_USER] [GENERATE_XSL_TRANSFORM] [GENERATE_TEMPLATES] [BusLineBusAreaSubjArea] [DATA_SOURCE_NAME] [RESOURCE_NAME]
REM #
REM #      PROMPT_USER - when doing automated set PROMPT_USER=false so that it does not prompt the user to edit the Regression Module XML file.  Values=true or false
REM #
REM #      GENERATE_XSL_TRANSFORM - Generate the server attributes XSL transformation only if this flag is set to true.  Values=true or false
REM #
REM #      GENERATE_TEMPLATES - Generate the BusLineBusAreaSubjArea templates only if this flag is set to true.  Values=true or false
REM #
REM #      BusLineBusAreaSubjArea -  The BusLineBusAreaSubjArea is the portion of the deployment plan file name that occurs prior to the mandatory test type descriptor.
REM #         Affix any prefix or postfix desired to BusLineBusAreaSubjArea such as prefix_BusLineBusAreaSubjArea_postfix
REM #         Test type descriptor: _1Smoke_gen.dp, _1Smoke_exec.dp, _2Regression_exec.dp, _2Regression_compare.dp, _3Performance_exec.dp, _3Performance_compare.dp, _4Security_gen.dp, _4Security_exec.dp
REM #         Example constructed Deployment plan: prefix_MyProject1_MySubject_post_3Performance_exec.dp
REM #                                              |____BusLineBusAreaSubjArea____|
REM #                                              |______________Deployment Plan File Name____________|
REM #
REM #      DATA_SOURCE_NAME - This is published Composite data source to connect to for generating or executing queries.
REM #
REM #      RESOURCE_NAME - This may be CATALOG.SCHEMA.* or it may just be CATALOG.*.  This is the filter based on Business Line and Business Area.
REM #
REM # Prompt User:
REM #    From windows explorer, double click on the copyPlanTemplates.bat and it will prompt the user for the following:
REM #        GENERATE_XSL_TRANSFORM
REM #        GENERATE_TEMPLATES
REM #        BusLineBusAreaSubjArea
REM #        DATA_SOURCE_NAME
REM #        RESOURCE_NAME
REM #=======================================================================================
REM #
REM ###############################################
REM # Environment Variable Section
REM ###############################################
REM #
REM # Invoke the script to set the variables
if not exist setVars.bat (
	echo.
	echo.ERROR:  Unable to locate and execute setVars.bat. 
	echo.        Execute from directory \AutomatedTestFramework\migration\bin
	echo.
	exit /b 1
)
call setVars.bat


REM ###############################################
REM # Validation Section
REM ###############################################
REM #
if not exist "%EDITOR%" (
   echo.######################################################################################################
   echo.%0
   echo.
   echo.The variable EDITOR contains a path that does not exist:
   echo.    EDITOR="%EDITOR%"
   echo.
   echo.Open copyPlanTemplates.bat and modify EDITOR to point to an editor in your file system which exists.
   echo.Example notepad is located here: %windir%\system32\notepad.exe
   echo.
   echo.######################################################################################################
   exit /b 1
)

set PROMPT_USER=%1
set GENERATE_XSL_TRANSFORM=%2
set GENERATE_TEMPLATES=%3
set BusLineBusAreaSubjArea=%4
set DATA_SOURCE_NAME=%5
set RESOURCE_NAME=%6

REM # Set the ATF_HOME to an actual path
cd %ATF_HOME%
set ATF_HOME=%CD%
cd %ATF_HOME%/bin

REM # Remove double quotes
setlocal EnableDelayedExpansion
if defined PROMPT_USER set PROMPT_USER=!PROMPT_USER:"=!
if defined GENERATE_XSL_TRANSFORM set GENERATE_XSL_TRANSFORM=!GENERATE_XSL_TRANSFORM:"=!
if defined GENERATE_TEMPLATES set GENERATE_TEMPLATES=!GENERATE_TEMPLATES:"=!
if defined BusLineBusAreaSubjArea set BusLineBusAreaSubjArea=!BusLineBusAreaSubjArea:"=!
if defined DATA_SOURCE_NAME set DATA_SOURCE_NAME=!DATA_SOURCE_NAME:"=!
if defined RESOURCE_NAME set RESOURCE_NAME=!RESOURCE_NAME:"=!

echo.###########################
echo.Input Parameters
echo.###########################
echo.PROMPT_USER=           [%PROMPT_USER%]
echo.GENERATE_XSL_TRANSFORM=[%GENERATE_XSL_TRANSFORM%]
echo.GENERATE_TEMPLATES=    [%GENERATE_TEMPLATES%]
echo.BusLineBusAreaSubjArea=[%BusLineBusAreaSubjArea%]
echo.DATA_SOURCE_NAME=      [%DATA_SOURCE_NAME%]
echo.RESOURCE_NAME=         [%RESOURCE_NAME%]

set SCRIPT_HOME=%ATF_HOME%
set PLANS_HOME=%SCRIPT_HOME%\plans
set MODULES_HOME=%SCRIPT_HOME%\modules
set XSLT_HOME=%SCRIPT_HOME%\bin\Xslt
set TEMPLATES=%SCRIPT_HOME%\templates
set DOCS_HOME=%SCRIPT_HOME%\docs
set REPLACE_HOME=%SCRIPT_HOME%\bin\ReplaceText
echo.
echo.###########################
echo.Additional Settings
echo.###########################
echo.SCRIPT_HOME=           [%SCRIPT_HOME%]
echo.PLANS_HOME=            [%PLANS_HOME%]
echo.MODULES_HOME=          [%MODULES_HOME%]
echo.XSLT_HOME=             [%XSLT_HOME%]
echo.TEMPLATES=             [%TEMPLATES%]
echo.DOCS_HOME=             [%DOCS_HOME%]
echo.REPLACE_HOME=          [%REPLACE_HOME%]

REM # Set default values
if not defined PROMPT_USER set PROMPT_USER=TRUE
CALL :UCase PROMPT_USER PROMPT_USER
  

REM # Get user input
if "%PROMPT_USER%" == "FALSE" goto INPUT_CONTINUE
:INPUT
IF NOT DEFINED GENERATE_XSL_TRANSFORM (
   echo.----------------------------------------------------------------------------------------
   echo.
   echo.Provide the flag [GENERATE_XSL_TRANSFORM] to generate server attributes XSL Transform or not: [true or false].
   echo.   Generate the server attributes XSL Transform only if this flag is set to true. 
   echo.
   set /P GENERATE_XSL_TRANSFORM=enter GENERATE_XSL_TRANSFORM: 
   if not defined GENERATE_XSL_TRANSFORM goto INPUT
)
CALL :UCase GENERATE_XSL_TRANSFORM GENERATE_XSL_TRANSFORM
set VALID_GENERATE_XSL_TRANSFORM=0
if "%GENERATE_XSL_TRANSFORM%"=="T" set GENERATE_XSL_TRANSFORM=TRUE
if "%GENERATE_XSL_TRANSFORM%"=="F" set GENERATE_XSL_TRANSFORM=FALSE
if "%GENERATE_XSL_TRANSFORM%"=="TRUE" set VALID_GENERATE_XSL_TRANSFORM=1
if "%GENERATE_XSL_TRANSFORM%"=="FALSE" set VALID_GENERATE_XSL_TRANSFORM=1
if "%DEBUG%"=="Y" echo.[DEBUG] VALID_GENERATE_XSL_TRANSFORM=[%VALID_GENERATE_XSL_TRANSFORM%]
if "%DEBUG%"=="Y" echo.[DEBUG] GENERATE_XSL_TRANSFORM=[%GENERATE_XSL_TRANSFORM%]
if %VALID_GENERATE_XSL_TRANSFORM% NEQ 1 (
   set GENERATE_XSL_TRANSFORM=
   goto INPUT
)

IF NOT DEFINED GENERATE_TEMPLATES (
   echo.----------------------------------------------------------------------------------------
   echo.
   echo.Provide the flag [GENERATE_TEMPLATES] to generate BusLineBusAreaSubjArea templates or not: [true or false].
   echo.   Generate the BusLineBusAreaSubjArea templates only if this flag is set to true.
   echo.
   set /P GENERATE_TEMPLATES=enter GENERATE_TEMPLATES: 
   if not defined GENERATE_TEMPLATES goto INPUT
)
CALL :UCase GENERATE_TEMPLATES GENERATE_TEMPLATES
set VALID_GENERATE_TEMPLATES=0
if "%GENERATE_TEMPLATES%"=="T" set GENERATE_TEMPLATES=TRUE
if "%GENERATE_TEMPLATES%"=="F" set GENERATE_TEMPLATES=FALSE
if "%GENERATE_TEMPLATES%"=="TRUE" set VALID_GENERATE_TEMPLATES=1
if "%GENERATE_TEMPLATES%"=="FALSE" set VALID_GENERATE_TEMPLATES=1
if "%DEBUG%"=="Y" echo.[DEBUG] VALID_GENERATE_TEMPLATES=[%VALID_GENERATE_TEMPLATES%]
if "%DEBUG%"=="Y" echo.[DEBUG] GENERATE_TEMPLATES=[%GENERATE_TEMPLATES%]
if %VALID_GENERATE_TEMPLATES% NEQ 1 (
   set GENERATE_TEMPLATES=
   goto INPUT
)
if "%GENERATE_TEMPLATES%"=="FALSE" goto INPUT_CONTINUE

IF NOT DEFINED BusLineBusAreaSubjArea (
   echo.----------------------------------------------------------------------------------------
   echo.
   echo.Provide the project identifier [BusLineBusAreaSubjArea] for a deployment plan.
   echo.   This is the portion of the deployment plan file name that occurs prior to the mandatory test type descriptor.
   echo.   1. Affix any prefix or postfix desired to BusLineBusAreaSubjArea such as prefix_BusLineBusAreaSubjArea_postfix
   echo.   2. Test type descriptor: _1Smoke_gen.dp, _1Smoke_exec.dp, _2Regression_exec.dp, _2Regression_compare.dp, 
   echo.                            _3Performance_exec.dp, _3Performance_compare.dp, _4Security_gen.dp, _4Security_exec.dp
   echo.   3. Example constructed Deployment plan: prefix_MyProject1_MySubject_post_3Performance_exec.dp
   echo.                                           :____BusLineBusAreaSubjArea____:
   echo.                                           :______________Deployment Plan File Name____________:
   echo.
   set /P BusLineBusAreaSubjArea=enter BusLineBusAreaSubjArea: 
   if not defined BusLineBusAreaSubjArea goto INPUT
)
if "%DEBUG%"=="Y" echo.[DEBUG] BusLineBusAreaSubjArea=[%BusLineBusAreaSubjArea%]

IF NOT DEFINED DATA_SOURCE_NAME (
   echo.----------------------------------------------------------------------------------------
   echo.
   echo.Provide the DATA_ [DATA_SOURCE_NAME] for the RegressionModule.xml.
   echo.This is published Composite data source to connect to for generating or executing queries.
   echo.
   set /P DATA_SOURCE_NAME=enter DATA_SOURCE_NAME: 
   if not defined DATA_SOURCE_NAME goto INPUT
)
if "%DEBUG%"=="Y" echo.[DEBUG] DATA_SOURCE_NAME=[%DATA_SOURCE_NAME%]

IF NOT DEFINED RESOURCE_NAME (
   echo.----------------------------------------------------------------------------------------
   echo.
   echo.Provide the CATALOG.SCHEMA.* [RESOURCE_NAME] for the RegressionModule.xml.
   echo.This may be CATALOG.SCHEMA.* or it may just be CATALOG.*.  This is the filter based on Business Line and Business Area.
   echo.
   set /P RESOURCE_NAME=enter RESOURCE_NAME: 
   if not defined RESOURCE_NAME goto INPUT
)
if "%DEBUG%"=="Y" echo.[DEBUG] RESOURCE_NAME=[%RESOURCE_NAME%]

:INPUT_CONTINUE
if not defined GENERATE_XSL_TRANSFORM set GENERATE_XSL_TRANSFORM=FALSE
if not defined GENERATE_TEMPLATES set GENERATE_TEMPLATES=FALSE
CALL :UCase GENERATE_XSL_TRANSFORM GENERATE_XSL_TRANSFORM
CALL :UCase GENERATE_TEMPLATES GENERATE_TEMPLATES

echo.
echo.###########################
echo.User Parameters
echo.###########################
echo.PROMPT_USER=           [%PROMPT_USER%]
echo.GENERATE_XSL_TRANSFORM=[%GENERATE_XSL_TRANSFORM%]
echo.GENERATE_TEMPLATES=    [%GENERATE_TEMPLATES%]
echo.BusLineBusAreaSubjArea=[%BusLineBusAreaSubjArea%]
echo.DATA_SOURCE_NAME=      [%DATA_SOURCE_NAME%]
echo.RESOURCE_NAME=         [%RESOURCE_NAME%]


REM ###############################################
REM # XSL Transformation File Section
REM ###############################################
REM #
if "%GENERATE_XSL_TRANSFORM%"=="TRUE" (
	call:XSL_TRANSFORM_TEMPLATE ERROR
	if %ERROR% NEQ 0 echo GENERATE XSL TRANSFORM ERROR=%ERROR%.  An error occurred creating the XSL Transformation template.
	if %ERROR% NEQ 0 goto END
)


REM ###############################################
REM # Create Templates Section
REM ###############################################
REM #
if "%GENERATE_TEMPLATES%"=="TRUE" (
	call:CREATE_TEMPLATES ERROR
	if %ERROR% NEQ 0 echo GENERATE TEMPLATE ERROR=%ERROR%.  An error occurred creating the BusLineBusAreaSubjArea templates.
	if %ERROR% NEQ 0 goto END
)

REM ###############################################
REM # End of Script Section
REM ###############################################
REM #
set ERROR=0
:END
ENDLOCAL &SET ERROR=%ERROR%
set PROMPT_USER=
set GENERATE_XSL_TRANSFORM=
set GENERATE_TEMPLATES=
set BusLineBusAreaSubjArea=
set DATA_SOURCE_NAME=
set RESOURCE_NAME=
if %ERROR% NEQ 0 (
	echo.
	echo.Script failed with error.   Address the error and try again.
	echo.
)
exit /B %ERROR%


REM ###############################################
REM #
REM # FUNCTIONS
REM #
REM ###############################################

::-----------------------------------------------
:CREATE_TEMPLATES
::-----------------------------------------------
:: Create the BusLineBusAreaSubjArea templates
::
REM ###############################################
REM # Create Documentation Section
REM ###############################################
REM #
echo.
echo.###########################
echo.Copying Documentation
echo.###########################
set DOCUMENTATION=%DOCS_HOME%\documentation_%BusLineBusAreaSubjArea%.txt
echo.Location of documentation: "%DOCUMENTATION%"
if exist "%DOCUMENTATION%" del /F "%DOCUMENTATION%"
echo.
call:CREATE_MIGRATION_DOCUMENTATION

REM ###############################################
REM # Plan File Section
REM ###############################################
REM #
echo.
echo.###########################
echo.Copying PLAN files
echo.###########################
echo.
CALL:COPY_FILE "%TEMPLATES%\BusLineBusAreaSubjArea_1Smoke_gen.dp"           "%PLANS_HOME%\%BusLineBusAreaSubjArea%_1Smoke_gen.dp"			"Smoke Generate deployment plan"		ERROR
if %ERROR%==1 goto END_ERROR
CALL:COPY_FILE "%TEMPLATES%\BusLineBusAreaSubjArea_2Regression_exec.dp"     "%PLANS_HOME%\%BusLineBusAreaSubjArea%_2Regression_exec.dp"		"Regression Execute deployment plan"	ERROR
if %ERROR%==1 goto END_ERROR
CALL:COPY_FILE "%TEMPLATES%\BusLineBusAreaSubjArea_2Regression_compare.dp"  "%PLANS_HOME%\%BusLineBusAreaSubjArea%_2Regression_compare.dp"	"Regression Compare deployment plan"	ERROR
if %ERROR%==1 goto END_ERROR
CALL:COPY_FILE "%TEMPLATES%\BusLineBusAreaSubjArea_3Performance_exec.dp"    "%PLANS_HOME%\%BusLineBusAreaSubjArea%_3Performance_exec.dp"	"Performance Execute deployment plan"	ERROR
if %ERROR%==1 goto END_ERROR
CALL:COPY_FILE "%TEMPLATES%\BusLineBusAreaSubjArea_3Performance_compare.dp" "%PLANS_HOME%\%BusLineBusAreaSubjArea%_3Performance_compare.dp"	"Performance Compare deployment plan"	ERROR

REM ###############################################
REM # XML Module File Section
REM ###############################################
REM #
echo.
echo.###########################
echo. Copying MODULE file
echo.###########################
echo.
CALL:COPY_FILE "%TEMPLATES%\BusLineBusAreaSubjArea_RegressionModule.xml"    "%MODULES_HOME%\%BusLineBusAreaSubjArea%_RegressionModule.xml"	"Regression Module XML file"			ERROR
if %ERROR%==1 goto END_ERROR
echo.
echo.###########################
echo. Replace Text in MODULE file
echo.###########################
set ERROR=1
call "%REPLACE_HOME%\ReplaceText.bat" "%REPLACE_HOME%" replaceAllText "%MODULES_HOME%\%BusLineBusAreaSubjArea%_RegressionModule.xml" "" "BusLineBusAreaSubjArea" "%BusLineBusAreaSubjArea%"
if %ERRORLEVEL%==1 goto END_ERROR
call "%REPLACE_HOME%\ReplaceText.bat" "%REPLACE_HOME%" replaceAllText "%MODULES_HOME%\%BusLineBusAreaSubjArea%_RegressionModule.xml" "" "DATA_SOURCE_NAME" "%DATA_SOURCE_NAME%"
if %ERRORLEVEL%==1 goto END_ERROR
call "%REPLACE_HOME%\ReplaceText.bat" "%REPLACE_HOME%" replaceAllText "%MODULES_HOME%\%BusLineBusAreaSubjArea%_RegressionModule.xml" "" "CATALOG.SCHEMA.*" "%RESOURCE_NAME%"
if %ERRORLEVEL%==1 goto END_ERROR

REM ###############################################
REM # Instructions Section
REM ###############################################
REM #
echo.
echo.###########################
echo. INSTRUCTIONS:
echo.###########################
echo.Review Regression Module for accuracy:
echo.    %MODULES_HOME%\%BusLineBusAreaSubjArea%_RegressionModule.xml
echo        1. Verify "BusLineBusAreaSubjArea" with your project identifier: %BusLineBusAreaSubjArea%
echo             This is the same name provided when copying the plan templates
echo        2. Verify "DATA_SOURCE_NAME" with the actual Composite published data source name.
echo        3. Verify "CATALOG.SCHEMA.*" with the actual Composite published catalog and schema if applicable.
echo             It could be just CATALOG.* if desired to get all schemas automatically.
echo        4. Save.
echo.
REM # Edit the Regression Module XML if PROMPT_USER=true or TRUE
if "%PROMPT_USER%" == "TRUE" call:EDIT_REGRESSION_MODULE
set ERROR=0
:END_ERROR
set %1=%ERROR%
GOTO:EOF



::-----------------------------------------------
:EDIT_REGRESSION_MODULE
::-----------------------------------------------
:: This function is used to edit the Regression Module XML
rem pause
"%EDITOR%" "%MODULES_HOME%\%BusLineBusAreaSubjArea%_RegressionModule.xml"
GOTO:EOF



::-----------------------------------------------
:COPY_FILE
::-----------------------------------------------
:: Copy source to target
set SOURCE=%1
set TARGET=%2
set MESSAGE=%3
if defined MESSAGE set MESSAGE=!MESSAGE:"=!
REM Perform the copy from source to target
echo.copy /Y %SOURCE% %TARGET%
copy /Y %SOURCE%  %TARGET%
set ERROR=%ERRORLEVEL%
if %ERROR% NEQ 0 echo.COPY ERROR: %MESSAGE%
set %4=%ERROR%
GOTO:EOF


::-----------------------------------------------
:CREATE_MIGRATION_DOCUMENTATION
::-----------------------------------------------
:: This function generates the documentation.
echo.=========================================================================>>"%DOCUMENTATION%"
echo.Migration Test Plan Documentation for %BusLineBusAreaSubjArea%>>"%DOCUMENTATION%"
echo.=========================================================================>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.######################################################################>>"%DOCUMENTATION%"
echo.# (c) 2017 TIBCO Software Inc. All rights reserved.>>"%DOCUMENTATION%"
echo.# >>"%DOCUMENTATION%"
echo.# Except as specified below, this software is licensed pursuant to the Eclipse Public License v. 1.0.>>"%DOCUMENTATION%"
echo.# The details can be found in the file LICENSE.>>"%DOCUMENTATION%"
echo.# >>"%DOCUMENTATION%"
echo.# The following proprietary files are included as a convenience, and may not be used except pursuant>>"%DOCUMENTATION%"
echo.# to valid license to Composite Information Server or TIBCO® Data Virtualization Server:>>"%DOCUMENTATION%"
echo.# csadmin-XXXX.jar, csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar,>>"%DOCUMENTATION%"
echo.# csext-XXXX.jar, csjdbc-XXXX.jar, csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar,>>"%DOCUMENTATION%"
echo.# and customproc-XXXX.jar (where -XXXX is an optional version number).  Any included third party files>>"%DOCUMENTATION%"
echo.# are licensed under the terms contained in their own accompanying LICENSE files, generally named .LICENSE.txt.>>"%DOCUMENTATION%"
echo.# >>"%DOCUMENTATION%"
echo.# This software is licensed AS-IS. Support for this software is not covered by standard maintenance agreements with TIBCO.>>"%DOCUMENTATION%"
echo.# If you would like to obtain assistance with this software, such assistance may be obtained through a separate paid consulting>>"%DOCUMENTATION%"
echo.# agreement with TIBCO.>>"%DOCUMENTATION%"
echo.# >>"%DOCUMENTATION%"
echo.######################################################################>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.------------------------------------------------------------------------------------>>"%DOCUMENTATION%"
echo.PURPOSE:>>"%DOCUMENTATION%"
echo.The purpose of these scripts are to perform "MIGRATION" testing when migrating from CIS 6.2 to CIS 7.0.>>"%DOCUMENTATION%"
echo.The "Migration Automated Test Framework" uses PDTool Regression Module to execute the scripts.>>"%DOCUMENTATION%"
echo.Migration testing assumes that both CIS 6.2 and CIS 7.0 instances are available across all of the environments that require testing.>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.The following steps are repeated for each Business Line, Business Area, Subject Area>>"%DOCUMENTATION%"
echo.------------------------------------------------------------------------------------>>"%DOCUMENTATION%"
echo.1. Prepare the test scripts>>"%DOCUMENTATION%"
echo.    a. Execute the script copyPlanTemplates.bat by double clicking on it.>>"%DOCUMENTATION%"
echo.         It will prompt for the "BusLineBusAreaSubjArea" project identifier.>>"%DOCUMENTATION%"
echo.         It will prompt for the "DATA_SOURCE_NAME" used to connect to generate or execute queries.>>"%DOCUMENTATION%"
echo.         It will prompt for the "RESOURCE_NAME" used as a filter for CATALOG.SCHEMA.*.>>"%DOCUMENTATION%"
echo.         It will prompt you to edit/verify the Regression Module XML file.>>"%DOCUMENTATION%"
echo.         It will copy the following files.>>"%DOCUMENTATION%"
echo."            \migration\templates\BusLineBusAreaSubjArea_1Smoke_gen.dp           --> \migration\plans\%BusLineBusAreaSubjArea%_1Smoke_gen.dp">>"%DOCUMENTATION%"
echo."            \migration\templates\BusLineBusAreaSubjArea_2Regression_exec.dp     --> \migration\plans\%BusLineBusAreaSubjArea%_2Regression_exec.dp">>"%DOCUMENTATION%"
echo."            \migration\templates\BusLineBusAreaSubjArea_2Regression_compare.dp  --> \migration\plans\%BusLineBusAreaSubjArea%_2Regression_compare.dp">>"%DOCUMENTATION%"
echo."            \migration\templates\BusLineBusAreaSubjArea_3Performance_exec.dp    --> \migration\plans\%BusLineBusAreaSubjArea%_3Performance_exec.dp">>"%DOCUMENTATION%"
echo."            \migration\templates\BusLineBusAreaSubjArea_3Performance_compare.dp --> \migration\plans\%BusLineBusAreaSubjArea%_3Performance_compare.dp">>"%DOCUMENTATION%"
echo."            \migration\templates\BusLineBusAreaSubjArea_RegressionModule.xml    --> \migration\modules\%BusLineBusAreaSubjArea%_RegressionModule.xml">>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.------------------------------------------------------------------------------------>>"%DOCUMENTATION%"
echo.Replacements performed:>>"%DOCUMENTATION%"
echo.------------------------------------------------------------------------------------>>"%DOCUMENTATION%"
echo.    BusLineBusAreaSubjArea=%BusLineBusAreaSubjArea%>>"%DOCUMENTATION%"
echo.    DATA_SOURCE_NAME=%DATA_SOURCE_NAME%>>"%DOCUMENTATION%"
echo.    RESOURCE_NAME=%RESOURCE_NAME%>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.2. Open command line window for script execution>>"%DOCUMENTATION%"
echo.    cd %ATF_HOME%>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.    Script Parameters General Information>>"%DOCUMENTATION%"
echo.    ------------------------------------->>"%DOCUMENTATION%"
echo.     script_test_62.bat and script_test_70.bat parameter definitions:>>"%DOCUMENTATION%"
echo.        ENV - Example: [DEV,UAT,PROD]>>"%DOCUMENTATION%"
echo.        DEPLOYMENT_PLAN - The name of the deployment plan such as:>>"%DOCUMENTATION%"
echo.              1. BusLineBusArea_1Smoke_gen.dp>>"%DOCUMENTATION%"
echo.              2. BusLineBusArea_2Regression_exec.dp>>"%DOCUMENTATION%"
echo.              3. BusLineBusArea_2Regression_compare.dp>>"%DOCUMENTATION%"
echo.              4. BusLineBusArea_3Performance_exec.dp>>"%DOCUMENTATION%"
echo.              5. BusLineBusArea_3Performance_compare.dp>>"%DOCUMENTATION%"
echo.     >>"%DOCUMENTATION%"
echo.        CUSTOM - [optional] variable>>"%DOCUMENTATION%"
echo.              1. blank or "" - generate or execute using SQL SELECT COUNT(1) cnt or SELECT COUNT(*) cnt.>>"%DOCUMENTATION%"
echo.              2. TOP - If the word TOP is provided then generate or execute using the SELECT TOP 1 * command.  Top is a special type of CUSTOM.>>"%DOCUMENTATION%"
echo.              3. CUSTOM value - If any other word is used then execute the SQL file using this pattern and the value of the CUSTOM variable:>>"%DOCUMENTATION%"
echo.                     Example: Developer creates a custom SQL file where the custom name = MyQueries>>"%DOCUMENTATION%"
echo.                              Template:                                             Example:>>"%DOCUMENTATION%"
echo.                              \sql\BusLineBusArea_RegressionTest_SQL_%%CUSTOM%%.txt = \sql\BusLineBusArea_RegressionTest_SQL_MyQueries.txt>>"%DOCUMENTATION%"
echo.                              \sql\BusLineBusArea_PerfTest_SQL_%%CUSTOM%%.txt       = \sql\BusLineBusArea_PerfTest_SQL_MyQueries.txt>>"%DOCUMENTATION%"
echo.     >>"%DOCUMENTATION%"
echo.        RENAME_REL - [optional] variable. Default=true>>"%DOCUMENTATION%"
echo.              1. blank or "" or true - force a rename of the release output folders upon each execution of this script.>>"%DOCUMENTATION%"
echo.              2. false - disable the rename function and allow the results to go to the existing RELEASE_FOLDER1 directory.>>"%DOCUMENTATION%"
echo.                 Example.  This can be useful when executing a series of tests for the same release such as the following:>>"%DOCUMENTATION%"
echo.                           Smoke Test, Regression Test, Performance Test and Security Test.>>"%DOCUMENTATION%"
echo.                 Note:     This script will automatically ignore renaming the output folder for the following and thus the RENAME_REL does not have to be set.>>"%DOCUMENTATION%"
echo.                           Generating Smoke Test, Compare Regression Test, Compare Performance Test and Generate Security Test.>>"%DOCUMENTATION%"
echo.     >>"%DOCUMENTATION%"
echo.        PAUSE - [optional] variable. Default=true >>"%DOCUMENTATION%" 
echo.              1. blank or "" or true - pause during script execution.>>"%DOCUMENTATION%"
echo.              2. false - no pause during script execution.>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.3.  Test 1: Generate Smoke Test SQL for ENV>>"%DOCUMENTATION%"
echo.      a. Generate Smoke Test for SELECT COUNT(*)>>"%DOCUMENTATION%"
echo.         script_test_62.bat ENV %BusLineBusAreaSubjArea%_1Smoke_gen.dp   ""   ""   [PAUSE]>>"%DOCUMENTATION%"
echo.         script_test_70.bat ENV %BusLineBusAreaSubjArea%_1Smoke_gen.dp   ""   ""   [PAUSE]>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.         If desired...>>"%DOCUMENTATION%"
echo.           Copy queries from %BusLineBusAreaSubjArea%_SmokeTest_SQL.txt into %BusLineBusAreaSubjArea%_RegressionTest_SQL.txt>>"%DOCUMENTATION%"
echo.           Copy queries from %BusLineBusAreaSubjArea%_SmokeTest_SQL.txt into %BusLineBusAreaSubjArea%_PerfTest_SQL.txt>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.      b. Generate Smoke Test for SELECT TOP 1 *>>"%DOCUMENTATION%"
echo.         script_test_62.bat ENV %BusLineBusAreaSubjArea%_1Smoke_gen.dp   TOP  ""   [PAUSE]>>"%DOCUMENTATION%"
echo.         script_test_70.bat ENV %BusLineBusAreaSubjArea%_1Smoke_gen.dp   TOP  ""   [PAUSE]>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.         If desired...>>"%DOCUMENTATION%"
echo.           Copy queries from %BusLineBusAreaSubjArea%_SmokeTest_SQL_TOP.txt into %BusLineBusAreaSubjArea%_RegressionTest_SQL_TOP.txt>>"%DOCUMENTATION%"
echo.           Copy queries from %BusLineBusAreaSubjArea%_SmokeTest_SQL_TOP.txt into %BusLineBusAreaSubjArea%_PerfTest_SQL_TOP.txt>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.4.  Test 2: Execute Regression Test for ENV>>"%DOCUMENTATION%"
echo.    6.2: script_test_62.bat ENV %BusLineBusAreaSubjArea%_2Regression_exec.dp   [CUSTOM]  [RENAME_REL]   [PAUSE]>>"%DOCUMENTATION%"
echo.    7.0: script_test_70.bat ENV %BusLineBusAreaSubjArea%_2Regression_exec.dp   [CUSTOM]  [RENAME_REL]   [PAUSE]>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.5.  Test 2: Execute Regression Test Compare for ENV - Compare 6.2 and 7.0 files and logs for difference>>"%DOCUMENTATION%"
echo.    7.0: script_test_70.bat ENV %BusLineBusAreaSubjArea%_2Regression_compare.dp   [CUSTOM]  ""   [PAUSE]>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.6.  Test 3: Execute Performance Test for ENV>>"%DOCUMENTATION%"
echo.    6.2: script_test_62.bat ENV %BusLineBusAreaSubjArea%_3Performance_exec.dp   [CUSTOM]  [RENAME_REL]   [PAUSE]>>"%DOCUMENTATION%"
echo.    7.0: script_test_70.bat ENV %BusLineBusAreaSubjArea%_3Performance_exec.dp   [CUSTOM]  [RENAME_REL]   [PAUSE]>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.7.  Test 3: Execute Performance Test Compare for ENV - Compare 6.2 and 7.0 logs for difference>>"%DOCUMENTATION%"
echo.    7.0: script_test_70.bat ENV %BusLineBusAreaSubjArea%_3Performance_compare.dp   [CUSTOM]  ""   [PAUSE]>>"%DOCUMENTATION%"
GOTO:EOF


REM ###############################################
REM # XSL Transformation File Function
REM ###############################################
REM #
::-----------------------------------------------
:XSL_TRANSFORM_TEMPLATE
::-----------------------------------------------
:: Copy the XSL Transform file and modify the various paths based on the variables set in setVars.bat
::
echo.###########################
echo. Copying XSL Transform file
echo.###########################
echo.
CALL:COPY_FILE "%TEMPLATES%\ServerAttributes_Transform_62_to_70.xsl"    "%XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%"	"Server Attribute XSL Transform file"		ERROR
if %ERRORLEVEL%==1 goto XSLT_ERROR_1
echo.
echo.####################################
echo. Replace Text in XSL Transform file
echo.####################################
REM # Removed double quotes
if defined CIS_PATH_PREV_VERSION_1 set CIS_PATH_PREV_VERSION_1=!CIS_PATH_PREV_VERSION_1:"=!
if defined CIS_PATH_NEW_VERSION_1  set CIS_PATH_NEW_VERSION_1=!CIS_PATH_NEW_VERSION_1:"=!
if defined CIS_PATH_PREV_VERSION_2 set CIS_PATH_PREV_VERSION_2=!CIS_PATH_PREV_VERSION_2:"=!
if defined CIS_PATH_NEW_VERSION_2  set CIS_PATH_NEW_VERSION_2=!CIS_PATH_NEW_VERSION_2:"=!
if defined CIS_PATH_PREV_VERSION_3 set CIS_PATH_PREV_VERSION_3=!CIS_PATH_PREV_VERSION_3:"=!
if defined CIS_PATH_NEW_VERSION_3  set CIS_PATH_NEW_VERSION_3=!CIS_PATH_NEW_VERSION_3:"=!
if defined CIS_PATH_PREV_VERSION_4 set CIS_PATH_PREV_VERSION_4=!CIS_PATH_PREV_VERSION_4:"=!
if defined CIS_PATH_NEW_VERSION_4  set CIS_PATH_NEW_VERSION_4=!CIS_PATH_NEW_VERSION_4:"=!
if defined CIS_PATH_PREV_VERSION_5 set CIS_PATH_PREV_VERSION_5=!CIS_PATH_PREV_VERSION_5:"=!
if defined CIS_PATH_NEW_VERSION_5  set CIS_PATH_NEW_VERSION_5=!CIS_PATH_NEW_VERSION_5:"=!

REM # Check for CIS Path Versions not defined or blank and set to a default value
if not defined CIS_PATH_PREV_VERSION_1 set CIS_PATH_PREV_VERSION_1=_NO_TRANSFORM_OPERATION_
if not defined CIS_PATH_NEW_VERSION_1  set CIS_PATH_NEW_VERSION_1=_NO_TRANSFORM_OPERATION_
if "%CIS_PATH_PREV_VERSION_1%"==""     set CIS_PATH_PREV_VERSION_1=_NO_TRANSFORM_OPERATION_
if "%CIS_PATH_NEW_VERSION_1%"==""      set CIS_PATH_NEW_VERSION_1=_NO_TRANSFORM_OPERATION_

if not defined CIS_PATH_PREV_VERSION_2 set CIS_PATH_PREV_VERSION_2=_NO_TRANSFORM_OPERATION_
if not defined CIS_PATH_NEW_VERSION_2  set CIS_PATH_NEW_VERSION_2=_NO_TRANSFORM_OPERATION_
if "%CIS_PATH_PREV_VERSION_2%"==""     set CIS_PATH_PREV_VERSION_2=_NO_TRANSFORM_OPERATION_
if "%CIS_PATH_NEW_VERSION_2%"==""      set CIS_PATH_NEW_VERSION_2=_NO_TRANSFORM_OPERATION_

if not defined CIS_PATH_PREV_VERSION_3 set CIS_PATH_PREV_VERSION_3=_NO_TRANSFORM_OPERATION_
if not defined CIS_PATH_NEW_VERSION_3  set CIS_PATH_NEW_VERSION_3=_NO_TRANSFORM_OPERATION_
if "%CIS_PATH_PREV_VERSION_3%"==""     set CIS_PATH_PREV_VERSION_3=_NO_TRANSFORM_OPERATION_
if "%CIS_PATH_NEW_VERSION_3%"==""      set CIS_PATH_NEW_VERSION_3=_NO_TRANSFORM_OPERATION_

if not defined CIS_PATH_PREV_VERSION_4 set CIS_PATH_PREV_VERSION_4=_NO_TRANSFORM_OPERATION_
if not defined CIS_PATH_NEW_VERSION_4  set CIS_PATH_NEW_VERSION_4=_NO_TRANSFORM_OPERATION_
if "%CIS_PATH_PREV_VERSION_4%"==""     set CIS_PATH_PREV_VERSION_4=_NO_TRANSFORM_OPERATION_
if "%CIS_PATH_NEW_VERSION_4%"==""      set CIS_PATH_NEW_VERSION_4=_NO_TRANSFORM_OPERATION_

if not defined CIS_PATH_PREV_VERSION_5 set CIS_PATH_PREV_VERSION_5=_NO_TRANSFORM_OPERATION_
if not defined CIS_PATH_NEW_VERSION_5  set CIS_PATH_NEW_VERSION_5=_NO_TRANSFORM_OPERATION_
if "%CIS_PATH_PREV_VERSION_5%"==""     set CIS_PATH_PREV_VERSION_5=_NO_TRANSFORM_OPERATION_
if "%CIS_PATH_NEW_VERSION_5%"==""      set CIS_PATH_NEW_VERSION_5=_NO_TRANSFORM_OPERATION_

REM #	The CIS_PREV_VERSION and CIS_NEW_VERSION represent the portion of the various CIS paths within the Server Attribute XML file.
REM #	        If CIS previous and new install are installed on the same base path then it will only be necessary to change the version as 
REM #			shown below.  However, if they are installed on different paths, then the full base path should be provided for both variables.
REM #
REM #	<!-- CIS Path Version 1 -->
REM #	<xsl:variable name="CIS_PATH_PREV_VERSION_1" select="'REPLACE_CIS_PATH_PREV_VERSION_1'"/>
REM #	<xsl:variable name="CIS_PATH_NEW_VERSION_1" select="'REPLACE_CIS_PATH_NEW_VERSION_1'"/>
call "%REPLACE_HOME%\ReplaceText.bat" "%REPLACE_HOME%" replaceAllText "%XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%" "" "REPLACE_CIS_PATH_PREV_VERSION_1" "%CIS_PATH_PREV_VERSION_1%"
if %ERRORLEVEL%==1 goto XSLT_ERROR_1
call "%REPLACE_HOME%\ReplaceText.bat" "%REPLACE_HOME%" replaceAllText "%XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%" "" "REPLACE_CIS_PATH_NEW_VERSION_1" "%CIS_PATH_NEW_VERSION_1%"
if %ERRORLEVEL%==1 goto XSLT_ERROR_1
REM #
REM #	<!-- CIS Path Version 2 -->
REM #	<xsl:variable name="CIS_PATH_PREV_VERSION_2" select="'REPLACE_CIS_PATH_PREV_VERSION_2'"/>
REM #	<xsl:variable name="CIS_PATH_NEW_VERSION_2" select="'REPLACE_CIS_PATH_NEW_VERSION_2'"/>
call "%REPLACE_HOME%\ReplaceText.bat" "%REPLACE_HOME%" replaceAllText "%XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%" "" "REPLACE_CIS_PATH_PREV_VERSION_2" "%CIS_PATH_PREV_VERSION_2%"
if %ERRORLEVEL%==1 goto XSLT_ERROR_1
call "%REPLACE_HOME%\ReplaceText.bat" "%REPLACE_HOME%" replaceAllText "%XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%" "" "REPLACE_CIS_PATH_NEW_VERSION_2" "%CIS_PATH_NEW_VERSION_2%"
if %ERRORLEVEL%==1 goto XSLT_ERROR_1
REM #
REM #	<!-- CIS Path Version 3 -->
REM #	<xsl:variable name="CIS_PATH_PREV_VERSION_3" select="'REPLACE_CIS_PATH_PREV_VERSION_3'"/>
REM #	<xsl:variable name="CIS_PATH_NEW_VERSION_3" select="'REPLACE_CIS_PATH_NEW_VERSION_3'"/>
call "%REPLACE_HOME%\ReplaceText.bat" "%REPLACE_HOME%" replaceAllText "%XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%" "" "REPLACE_CIS_PATH_PREV_VERSION_3" "%CIS_PATH_PREV_VERSION_3%"
if %ERRORLEVEL%==1 goto XSLT_ERROR_1
call "%REPLACE_HOME%\ReplaceText.bat" "%REPLACE_HOME%" replaceAllText "%XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%" "" "REPLACE_CIS_PATH_NEW_VERSION_3" "%CIS_PATH_NEW_VERSION_3%"
if %ERRORLEVEL%==1 goto XSLT_ERROR_1
REM #
REM #	<!-- CIS Path Version 4 -->
REM #	<xsl:variable name="CIS_PATH_PREV_VERSION_4" select="'REPLACE_CIS_PATH_PREV_VERSION_4'"/>
REM #	<xsl:variable name="CIS_PATH_NEW_VERSION_4" select="'REPLACE_CIS_PATH_NEW_VERSION_4'"/>
call "%REPLACE_HOME%\ReplaceText.bat" "%REPLACE_HOME%" replaceAllText "%XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%" "" "REPLACE_CIS_PATH_PREV_VERSION_4" "%CIS_PATH_PREV_VERSION_4%"
if %ERRORLEVEL%==1 goto XSLT_ERROR_1
call "%REPLACE_HOME%\ReplaceText.bat" "%REPLACE_HOME%" replaceAllText "%XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%" "" "REPLACE_CIS_PATH_NEW_VERSION_4" "%CIS_PATH_NEW_VERSION_4%"
if %ERRORLEVEL%==1 goto XSLT_ERROR_1
REM #
REM #	<!-- CIS Path Version 5 -->
REM #	<xsl:variable name="CIS_PATH_PREV_VERSION_5" select="'REPLACE_CIS_PATH_PREV_VERSION_5'"/>
REM #	<xsl:variable name="CIS_PATH_NEW_VERSION_5" select="'REPLACE_CIS_PATH_NEW_VERSION_5'"/>
call "%REPLACE_HOME%\ReplaceText.bat" "%REPLACE_HOME%" replaceAllText "%XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%" "" "REPLACE_CIS_PATH_PREV_VERSION_5" "%CIS_PATH_PREV_VERSION_5%"
if %ERRORLEVEL%==1 goto XSLT_ERROR_1
call "%REPLACE_HOME%\ReplaceText.bat" "%REPLACE_HOME%" replaceAllText "%XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%" "" "REPLACE_CIS_PATH_NEW_VERSION_5" "%CIS_PATH_NEW_VERSION_5%"
if %ERRORLEVEL%==1 goto XSLT_ERROR_1
REM # Edit the Regression Module XML if PROMPT_USER=true or TRUE
if "%PROMPT_USER%" == "true" "%EDITOR%" "%XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%"
if "%PROMPT_USER%" == "TRUE" "%EDITOR%" "%XSLT_HOME%\%XSL_TRANSFORM_SCRIPT%"
set ERROR=0
goto XSLT_END

:XSLT_ERROR_1
set ERROR=1
:XSLT_END
set %1=%ERROR%
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
