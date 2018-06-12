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
REM # PDTool Regression Module - Regression Automated Test Framework
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
REM #    copyPlanTemplates.bat [BusLineBusAreaSubjArea] [DATA_SOURCE_NAME] [RESOURCE_NAME] [PROMPT_USER]
REM #
REM #      BusLineBusAreaSubjArea -  The BusLineBusAreaSubjArea is the portion of the deployment plan file name that occurs prior to the mandatory test type descriptor.
REM #         Affix any prefix or postfix desired to BusLineBusAreaSubjArea such as prefix_BusLineBusAreaSubjArea_postfix
REM #         Test type descriptor: _1Smoke_gen.dp, _1Smoke_exec.dp, _1SmokeAsIs.dp, _2Regression_exec.dp, _2Regression_compare.dp, _3Performance_exec.dp, _3Performance_compare.dp, _4Security_gen.dp, _4Security_exec.dp
REM #         Example constructed Deployment plan: prefix_MyProject1_MySubject_post_3Performance_exec.dp
REM #                                              |____BusLineBusAreaSubjArea____|
REM #                                              |______________Deployment Plan File Name____________|
REM #
REM #      DATA_SOURCE_NAME - This is published Composite data source to connect to for generating or executing queries.
REM #
REM #      RESOURCE_NAME - This may be CATALOG.SCHEMA.* or it may just be CATALOG.*.  This is the filter based on Business Line and Business Area.
REM #
REM #      PROMPT_USER - when doing automated set PROMPT_USER=false so that it does not prompt the user to edit the Regression Module XML file.
REM #
REM # Prompt User:
REM #    From windows explorer, double click on the copyPlanTemplates.bat and it will prompt the user for the following:
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
	echo.        Execute from directory \AutomatedTestFramework\regression\bin
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

set BusLineBusAreaSubjArea=%1
set DATA_SOURCE_NAME=%2
set RESOURCE_NAME=%3
set PROMPT_USER=%4

REM # Set the ATF_HOME to an actual path
cd %ATF_HOME%
set ATF_HOME=%CD%
cd %ATF_HOME%/bin

REM # Remove double quotes
setlocal EnableDelayedExpansion
if defined BusLineBusAreaSubjArea set BusLineBusAreaSubjArea=!BusLineBusAreaSubjArea:"=!
if defined DATA_SOURCE_NAME set DATA_SOURCE_NAME=!DATA_SOURCE_NAME:"=!
if defined RESOURCE_NAME set RESOURCE_NAME=!RESOURCE_NAME:"=!
if defined PROMPT_USER set PROMPT_USER=!PROMPT_USER:"=!

echo.###########################
echo.Input Parameters
echo.###########################
echo.BusLineBusAreaSubjArea=[%BusLineBusAreaSubjArea%]
echo.DATA_SOURCE_NAME=      [%DATA_SOURCE_NAME%]
echo.RESOURCE_NAME=         [%RESOURCE_NAME%]
echo.PROMPT_USER=           [%PROMPT_USER%]

set SCRIPT_HOME=%ATF_HOME%
set PLANS_HOME=%SCRIPT_HOME%\plans
set MODULES_HOME=%SCRIPT_HOME%\modules
set TEMPLATES=%SCRIPT_HOME%\templates
set DOCS_HOME=%SCRIPT_HOME%\docs
set REPLACE_HOME=%SCRIPT_HOME%\bin\ReplaceText
echo.
echo.SCRIPT_HOME=           [%SCRIPT_HOME%]
echo.PLANS_HOME=            [%PLANS_HOME%]
echo.MODULES_HOME=          [%MODULES_HOME%]
echo.TEMPLATES=             [%TEMPLATES%]
echo.DOCS_HOME=             [%DOCS_HOME%]
echo.REPLACE_HOME=          [%REPLACE_HOME%]

if not defined PROMPT_USER set PROMPT_USER=true

if "%PROMPT_USER%" == "false" goto INPUT_CONTINUE
if "%PROMPT_USER%" == "FALSE" goto INPUT_CONTINUE

:INPUT
echo.----------------------------------------------------------------------------------------
IF NOT DEFINED BusLineBusAreaSubjArea (
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
echo.----------------------------------------------------------------------------------------
IF NOT DEFINED DATA_SOURCE_NAME (
   echo.
   echo.Provide the DATA_ [DATA_SOURCE_NAME] for the RegressionModule.xml.
   echo.This is published Composite data source to connect to for generating or executing queries.
   echo.
   set /P DATA_SOURCE_NAME=enter DATA_SOURCE_NAME: 
   if not defined DATA_SOURCE_NAME goto INPUT
)
echo.----------------------------------------------------------------------------------------
IF NOT DEFINED RESOURCE_NAME (
   echo.
   echo.Provide the CATALOG.SCHEMA.* [RESOURCE_NAME] for the RegressionModule.xml.
   echo.This may be CATALOG.SCHEMA.* or it may just be CATALOG.*.  This is the filter based on Business Line and Business Area.
   echo.
   set /P RESOURCE_NAME=enter RESOURCE_NAME: 
   if not defined RESOURCE_NAME goto INPUT
)
echo.
echo.###########################
echo.User Parameters
echo.###########################
echo.BusLineBusAreaSubjArea=[%BusLineBusAreaSubjArea%]
echo.DATA_SOURCE_NAME=      [%DATA_SOURCE_NAME%]
echo.RESOURCE_NAME=         [%RESOURCE_NAME%]
echo.PROMPT_USER=           [%PROMPT_USER%]
:INPUT_CONTINUE


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
call:CREATE_REGRESSION_DOCUMENTATION


REM ###############################################
REM # Plan File Section
REM ###############################################
REM #
echo.
echo.###########################
echo.Copying PLAN files
echo.###########################
echo.
CALL:COPY_FILE "%TEMPLATES%\BusLineBusAreaSubjArea_1Smoke_gen.dp"            "%PLANS_HOME%\%BusLineBusAreaSubjArea%_1Smoke_gen.dp"			"Smoke Generate deployment plan"		ERROR
if %ERROR%==1 goto END
CALL:COPY_FILE "%TEMPLATES%\BusLineBusAreaSubjArea_1Smoke_exec.dp"           "%PLANS_HOME%\%BusLineBusAreaSubjArea%_1Smoke_exec.dp"			"Smoke Execute deployment plan"			ERROR
if %ERROR%==1 goto END
CALL:COPY_FILE "%TEMPLATES%\BusLineBusAreaSubjArea_1SmokeAsIs_exec.dp"       "%PLANS_HOME%\%BusLineBusAreaSubjArea%_1SmokeAsIs_exec.dp"		"Smoke As Is Execute deployment plan"	ERROR
if %ERROR%==1 goto END
CALL:COPY_FILE "%TEMPLATES%\BusLineBusAreaSubjArea_2Regression_exec.dp"     "%PLANS_HOME%\%BusLineBusAreaSubjArea%_2Regression_exec.dp"		"Regression Execute deployment plan"	ERROR
if %ERROR%==1 goto END
CALL:COPY_FILE "%TEMPLATES%\BusLineBusAreaSubjArea_2Regression_compare.dp"  "%PLANS_HOME%\%BusLineBusAreaSubjArea%_2Regression_compare.dp"	"Regression Compare deployment plan"	ERROR
if %ERROR%==1 goto END
CALL:COPY_FILE "%TEMPLATES%\BusLineBusAreaSubjArea_3Performance_exec.dp"    "%PLANS_HOME%\%BusLineBusAreaSubjArea%_3Performance_exec.dp"	"Performance Execute deployment plan"	ERROR
if %ERROR%==1 goto END
CALL:COPY_FILE "%TEMPLATES%\BusLineBusAreaSubjArea_3Performance_compare.dp" "%PLANS_HOME%\%BusLineBusAreaSubjArea%_3Performance_compare.dp"	"Performance Compare deployment plan"	ERROR
if %ERROR%==1 goto END
CALL:COPY_FILE "%TEMPLATES%\BusLineBusAreaSubjArea_4Security_gen.dp"        "%PLANS_HOME%\%BusLineBusAreaSubjArea%_4Security_gen.dp"		"Security Generate deployment plan"		ERROR
if %ERROR%==1 goto END
CALL:COPY_FILE "%TEMPLATES%\BusLineBusAreaSubjArea_4Security_exec.dp"       "%PLANS_HOME%\%BusLineBusAreaSubjArea%_4Security_exec.dp"		"Security Execute deployment plan"		ERROR
if %ERROR%==1 goto END


REM ###############################################
REM # XML Module File Section
REM ###############################################
REM #
echo.
echo.###########################
echo. Copying MODULE file
echo.###########################
echo.
CALL:COPY_FILE "%TEMPLATES%\BusLineBusAreaSubjArea_RegressionModule.xml"    "%MODULES_HOME%\%BusLineBusAreaSubjArea%_RegressionModule.xml"	"Regression Module XML"					ERROR
if %ERROR%==1 goto END
echo.
echo.###########################
echo. Replace Text in MODULE file
echo.###########################
call "%REPLACE_HOME%\ReplaceText.bat" "%REPLACE_HOME%" replaceAllText "%MODULES_HOME%\%BusLineBusAreaSubjArea%_RegressionModule.xml" "" "BusLineBusAreaSubjArea" "%BusLineBusAreaSubjArea%"
if %ERRORLEVEL%==1 goto END_ERROR_1
call "%REPLACE_HOME%\ReplaceText.bat" "%REPLACE_HOME%" replaceAllText "%MODULES_HOME%\%BusLineBusAreaSubjArea%_RegressionModule.xml" "" "DATA_SOURCE_NAME" "%DATA_SOURCE_NAME%"
if %ERRORLEVEL%==1 goto END_ERROR_1
call "%REPLACE_HOME%\ReplaceText.bat" "%REPLACE_HOME%" replaceAllText "%MODULES_HOME%\%BusLineBusAreaSubjArea%_RegressionModule.xml" "" "CATALOG.SCHEMA.*" "%RESOURCE_NAME%"
if %ERRORLEVEL%==1 goto END_ERROR_1

	
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
if "%PROMPT_USER%" == "true" call:EDIT_REGRESSION_MODULE
if "%PROMPT_USER%" == "TRUE" call:EDIT_REGRESSION_MODULE
set ERROR=0
goto END

REM ###############################################
REM # End of Script Section
REM ###############################################
REM #
:END_ERROR_1
set ERROR=1
:END
ENDLOCAL &SET ERROR=%ERROR%
set BusLineBusAreaSubjArea=
set DATA_SOURCE_NAME=
set RESOURCE_NAME=
set PROMPT_USER=
if %ERROR% NEQ 0 (
	echo.
	echo.Script failed with error.   Address the error and try again.
	echo.
)
exit /B %ERROR%


REM ########################
REM # FUNCTIONS
REM ########################

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
:CREATE_REGRESSION_DOCUMENTATION
::-----------------------------------------------
:: This function generates the documentation.
echo.=========================================================================>>"%DOCUMENTATION%"
echo.Regression Test Plan Documentation for %BusLineBusAreaSubjArea%>>"%DOCUMENTATION%"
echo.=========================================================================>>"%DOCUMENTATION%"
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
echo.The purpose of these scripts are to perform "REGRESSION" testing when promoting from one CIS code release to another.>>"%DOCUMENTATION%"
echo.The "Regression Automated Test Framework" uses PDTool Regression Module to execute the scripts.>>"%DOCUMENTATION%"
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
echo."            \regression\templates\BusLineBusAreaSubjArea_1Smoke_gen.dp           --> \regression\plans\%BusLineBusAreaSubjArea%_1Smoke_gen.dp">>"%DOCUMENTATION%"
echo."            \regression\templates\BusLineBusAreaSubjArea_1Smoke_exec.dp          --> \regression\plans\%BusLineBusAreaSubjArea%_1Smoke_exec.dp">>"%DOCUMENTATION%"
echo."            \regression\templates\BusLineBusAreaSubjArea_1SmokeAsIs_exec.dp      --> \regression\plans\%BusLineBusAreaSubjArea%_1SmokeAsIs_exec.dp">>"%DOCUMENTATION%"
echo."            \regression\templates\BusLineBusAreaSubjArea_2Regression_exec.dp     --> \regression\plans\%BusLineBusAreaSubjArea%_2Regression_exec.dp">>"%DOCUMENTATION%"
echo."            \regression\templates\BusLineBusAreaSubjArea_2Regression_compare.dp  --> \regression\plans\%BusLineBusAreaSubjArea%_2Regression_compare.dp">>"%DOCUMENTATION%"
echo."            \regression\templates\BusLineBusAreaSubjArea_3Performance_exec.dp    --> \regression\plans\%BusLineBusAreaSubjArea%_3Performance_exec.dp">>"%DOCUMENTATION%"
echo."            \regression\templates\BusLineBusAreaSubjArea_3Performance_compare.dp --> \regression\plans\%BusLineBusAreaSubjArea%_3Performance_compare.dp">>"%DOCUMENTATION%"
echo."            \regression\templates\BusLineBusAreaSubjArea_4Security_gen.dp        --> \regression\plans\%BusLineBusAreaSubjArea%_4Security_gen.dp">>"%DOCUMENTATION%"
echo."            \regression\templates\BusLineBusAreaSubjArea_4Security_exec.dp       --> \regression\plans\%BusLineBusAreaSubjArea%_4Security_exec.dp">>"%DOCUMENTATION%"
echo."            \regression\templates\BusLineBusAreaSubjArea_RegressionModule.xml    --> \regression\modules\%BusLineBusAreaSubjArea%_RegressionModule.xml">>"%DOCUMENTATION%"
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
echo.              2. BusLineBusArea_1Smoke_exec.dp>>"%DOCUMENTATION%"
echo.              3. BusLineBusArea_1SmokeAsIs_exec.dp>>"%DOCUMENTATION%"
echo.              4. BusLineBusArea_2Regression_exec.dp>>"%DOCUMENTATION%"
echo.              5. BusLineBusArea_2Regression_compare.dp>>"%DOCUMENTATION%"
echo.              6. BusLineBusArea_3Performance_exec.dp>>"%DOCUMENTATION%"
echo.              7. BusLineBusArea_3Performance_compare.dp>>"%DOCUMENTATION%"
echo.              8. BusLineBusArea_4Security_gen.dp>>"%DOCUMENTATION%"
echo.              9. BusLineBusArea_4Security_exec.dp>>"%DOCUMENTATION%"
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
echo.    a. Generate Smoke Test for SELECT COUNT(*)>>"%DOCUMENTATION%"
echo.       script_test_62.bat ENV %BusLineBusAreaSubjArea%_1Smoke_gen.dp   ""   ""   [PAUSE]>>"%DOCUMENTATION%"
echo.       script_test_70.bat ENV %BusLineBusAreaSubjArea%_1Smoke_gen.dp   ""   ""   [PAUSE]>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.       If desired...>>"%DOCUMENTATION%"
echo.         Copy queries from %BusLineBusAreaSubjArea%_SmokeTest_SQL.txt into %BusLineBusAreaSubjArea%_RegressionTest_SQL.txt>>"%DOCUMENTATION%"
echo.         Copy queries from %BusLineBusAreaSubjArea%_SmokeTest_SQL.txt into %BusLineBusAreaSubjArea%_PerfTest_SQL.txt>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.    b. Generate Smoke Test for SELECT TOP 1 *>>"%DOCUMENTATION%"
echo.       script_test_62.bat ENV %BusLineBusAreaSubjArea%_1Smoke_gen.dp   TOP  ""   [PAUSE]>>"%DOCUMENTATION%"
echo.       script_test_70.bat ENV %BusLineBusAreaSubjArea%_1Smoke_gen.dp   TOP  ""   [PAUSE]>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.       If desired...>>"%DOCUMENTATION%"
echo.         Copy queries from %BusLineBusAreaSubjArea%_SmokeTest_SQL_TOP.txt into %BusLineBusAreaSubjArea%_RegressionTest_SQL_TOP.txt>>"%DOCUMENTATION%"
echo.         Copy queries from %BusLineBusAreaSubjArea%_SmokeTest_SQL_TOP.txt into %BusLineBusAreaSubjArea%_PerfTest_SQL_TOP.txt>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.4.  Test 1: Execute Smoke Test for ENV>>"%DOCUMENTATION%"
echo.    a.[OPTIONAL] Execute Default Smoke Test rewriting query as SELECT COUNT(*) FROM "table" or "procedure".>>"%DOCUMENTATION%"
echo.      script_test_62.bat ENV %BusLineBusAreaSubjArea%_1Smoke_exec.dp   ""  [RENAME_REL]   [PAUSE]>>"%DOCUMENTATION%"
echo.      script_test_70.bat ENV %BusLineBusAreaSubjArea%_1Smoke_exec.dp   ""  [RENAME_REL]   [PAUSE]>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.    b.[OPTIONAL] Execute Smoke Test "As Is" using query directly from input file without rewriting.>>"%DOCUMENTATION%"
echo.      script_test_62.bat ENV %BusLineBusAreaSubjArea%_1SmokeAsIs_exec.dp   ""  [RENAME_REL]   [PAUSE]>>"%DOCUMENTATION%"
echo.      script_test_70.bat ENV %BusLineBusAreaSubjArea%_1SmokeAsIs_exec.dp   ""  [RENAME_REL]   [PAUSE]>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.      Executing the smoke test is redundant as the regression test incorprates the same test.>>"%DOCUMENTATION%"
echo.      Additionally, a smoke-test automatically executes a SELECT COUNT(*) regardless of the query provided.>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.5.  Test 2: Execute Regression Test for ENV>>"%DOCUMENTATION%"
echo.    6.2: script_test_62.bat ENV %BusLineBusAreaSubjArea%_2Regression_exec.dp   [CUSTOM]  [RENAME_REL]   [PAUSE]>>"%DOCUMENTATION%"
echo.    7.0: script_test_70.bat ENV %BusLineBusAreaSubjArea%_2Regression_exec.dp   [CUSTOM]  [RENAME_REL]   [PAUSE]>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.6.  Test 2: Execute Regression Test Compare for ENV - Compare files and logs for difference>>"%DOCUMENTATION%"
echo.    6.2: script_test_62.bat ENV %BusLineBusAreaSubjArea%_2Regression_compare.dp   [CUSTOM]  ""   [PAUSE]>>"%DOCUMENTATION%"
echo.    7.0: script_test_70.bat ENV %BusLineBusAreaSubjArea%_2Regression_compare.dp   [CUSTOM]  ""   [PAUSE]>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.7.  Test 3: Execute Performance Test for ENV>>"%DOCUMENTATION%"
echo.    6.2: script_test_62.bat ENV %BusLineBusAreaSubjArea%_3Performance_exec.dp   [CUSTOM]  [RENAME_REL]   [PAUSE]>>"%DOCUMENTATION%"
echo.    7.0: script_test_70.bat ENV %BusLineBusAreaSubjArea%_3Performance_exec.dp   [CUSTOM]  [RENAME_REL]   [PAUSE]>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.8.  Test 3: Execute Performance Test Compare for ENV - Compare logs for difference>>"%DOCUMENTATION%"
echo.    6.2: script_test_62.bat ENV %BusLineBusAreaSubjArea%_3Performance_compare.dp   [CUSTOM]  ""   [PAUSE]>>"%DOCUMENTATION%"
echo.    7.0: script_test_70.bat ENV %BusLineBusAreaSubjArea%_3Performance_compare.dp   [CUSTOM]  ""   [PAUSE]>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.9.  Test 4: Generate Security Test for ENV>>"%DOCUMENTATION%"
echo.    6.2: script_test_62.bat ENV %BusLineBusAreaSubjArea%_4Security_gen.dp   ""  ""   [PAUSE]>>"%DOCUMENTATION%"
echo.    7.0: script_test_70.bat ENV %BusLineBusAreaSubjArea%_4Security_gen.dp   ""  ""   [PAUSE]>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.         Manually modify the XML generated to add different LDAP app id users/passwords for the security test.>>"%DOCUMENTATION%"
echo.         Since there are only 4 LDAP users to work with the test may need to be split up and run at different>>"%DOCUMENTATION%"
echo.         times.  This will require adding and removing LDAP app id users in and out of different groups.>>"%DOCUMENTATION%"
echo.>>"%DOCUMENTATION%"
echo.10. Test 4: Execute Security Test for ENV>>"%DOCUMENTATION%"
echo.    6.2: script_test_62.bat ENV %BusLineBusAreaSubjArea%_4Security_exec.dp   ""  [RENAME_REL]   [PAUSE]>>"%DOCUMENTATION%"
echo.    7.0: script_test_70.bat ENV %BusLineBusAreaSubjArea%_4Security_exec.dp   ""  [RENAME_REL]   [PAUSE]>>"%DOCUMENTATION%"
GOTO:EOF