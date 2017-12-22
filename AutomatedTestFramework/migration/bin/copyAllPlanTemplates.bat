@echo off
REM ############################################################################################################################
REM # (c) 2017 TIBCO Software Inc. All rights reserved.
REM # 
REM # Except as specified below, this software is licensed pursuant to the Eclipse Public License v. 1.0.
REM # The details can be found in the file LICENSE.
REM # 
REM # The following proprietary files are included as a convenience, and may not be used except pursuant
REM # to valid license to Composite Information Server or TIBCO® Data Virtualization Server:
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
REM #
REM #########################################################################
REM # The purpose of this script is to automatically generate the PDTool Regression Module scripts
REM #   for a given project/business line/business area/subject area as represented by the published
REM #   database, catalog and schema.  This batch file will generate the necessary plan files, modules
REM #   XML file and documentation from templates found in the templates folder.
REM # Note: Executing this batch file more than once will overwrite any existing generated files.
REM #
REM #  Instructions:
REM #  1. Add lines as shown below that represent each project published database, catalog, schema that you want to test.
REM #     call copyPlanTemplates.bat   PROMPT_USER  GENERATE_XSL_TRANSFORM   GENERATE_TEMPLATES	BusLineBusAreaSubjArea        DATA_SOURCE_NAME    RESOURCE_NAME       
REM #     call copyPlanTemplates.bat   false        false                    true                MyProject1SubProj            "MY DB"             "MY_CATALOG.*"      
REM #
REM #      PROMPT_USER - when doing automated set PROMPT_USER=false so that it does not prompt the user to edit the Regression Module XML file.
REM #
REM #      GENERATE_XSL_TRANSFORM - Generate the server attributes XSL transformation only if this flag is set to true.  Values=TRUE or FALSE
REM #
REM #      GENERATE_TEMPLATES - Generate the BusLineBusAreaSubjArea templates.  Values=TRUE or FALSE
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
REM #  2. Execute "copyAllPlanTemplates.bat" with no parameters.
REM #########################################################################
cls
REM # Invoke the script to set the variables
REM # Invoke the script to set the variables
if not exist setVars.bat (
	echo.
	echo.ERROR:  Unable to locate and execute setVars.bat. 
	echo.        Execute from directory \AutomatedTestFramework\migration\bin
	echo.
	exit /b 1
)
call setVars.bat

set I_CONTINUE=
if not defined I_CONTINUE (
   echo.
   echo.Do you want to copy/overwrite ALL templates for all Business Line-Business Areas?
   echo.
   set /P I_CONTINUE=Enter Y or N: 
)
echo.
if "%I_CONTINUE%" == "Y" goto CONTINUE
if "%I_CONTINUE%" == "y" goto CONTINUE
exit /b 0

:CONTINUE

REM # Generate the XSL Transformation template
REM  copyPlanTemplates.bat PROMPT_USER  GENERATE_XSL_TRANSFORM  GENERATE_TEMPLATES	BusLineBusAreaSubjArea        DATA_SOURCE_NAME    RESOURCE_NAME            
call copyPlanTemplates.bat false        true                   false                ""                            ""                  ""
IF %ERRORLEVEL% NEQ 0 exit /b 1

REM # Generate the BusLineBusAreaSubjArea Templates
REM  copyPlanTemplates.bat PROMPT_USER  GENERATE_XSL_TRANSFORM  GENERATE_TEMPLATES	BusLineBusAreaSubjArea        DATA_SOURCE_NAME    RESOURCE_NAME            
call copyPlanTemplates.bat false        false                  true                 MyProject1SubProj             "MY DB"             "MY CATALOG.MY SCHEMA.*"
IF %ERRORLEVEL% NEQ 0 exit /b 1
call copyPlanTemplates.bat false        false                  true                 MyProject2SubProj             MY_DB               "MY_CATALOG.*"
IF %ERRORLEVEL% NEQ 0 exit /b 1
