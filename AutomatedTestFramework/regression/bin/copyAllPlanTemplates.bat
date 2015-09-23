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
REM # PDTool Regression Module - Regression Automated Test Framework
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
REM #     call copyPlanTemplates.bat   BusLineBusAreaSubjArea        DATA_SOURCE_NAME    RESOURCE_NAME       PROMPT_USER
REM #     call copyPlanTemplates.bat   MyProject1SubProj             "MY DB"             "MY_CATALOG.*"      false
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
REM #      PROMPT_USER - when doing automated set PROMPT_USER=false so that it does not prompt the user to edit the Regression Module XML file.
REM #
REM #  2. Execute "copyAllPlanTemplates.bat" with no parameters.
REM #########################################################################
cls
REM # Invoke the script to set the variables
REM # Invoke the script to set the variables
if not exist setVars.bat (
	echo.
	echo.ERROR:  Unable to locate and execute setVars.bat. 
	echo.        Execute from directory \AutomatedTestFramework\regression\bin
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

REM  copyPlanTemplates.bat BusLineBusAreaSubjArea        DATA_SOURCE_NAME    RESOURCE_NAME            PROMPT_USER
call copyPlanTemplates.bat MyProject1SubProj             "MY DB"             "MY CATALOG.MY SCHEMA.*" false
IF %ERRORLEVEL% NEQ 0 exit /b 1
call copyPlanTemplates.bat MyProject2SubProj             MY_DB               "MY_CATALOG.*"           false
