@echo off
REM ############################################################################################################################
REM # (c) 2014 Cisco and/or its affiliates. All rights reserved.
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
REM # Execute a PDTool .dp plan file
REM #
REM # Once "startEnv.cmd" has been run, this batch script can be run from any directory.
REM #
REM # envExec.cmd deploy-plan-file [-list or env_type or -config deploy.properties] [-vcsuser username] [-vcspassword password] [-release RELEASE_FOLDER] 
REM # 
REM # For the examples below, 
REM #    Example 1 and Example 2 are functionally equivalent.  Example 1 uses the short name for the configuration property environment.
REM #    Example 3 shows a VCS use case using the short name by specifying the VCS user and password along with a release folder in VCS.
REM #
REM #       Example 1: envExec UnitTest-Datasource.dp UAT
REM #       Example 2: envExec UnitTest-Datasource.dp -config deploy_NOVCS_UAT1.properties
REM #       Example 3: envExec UnitTest-VCSModule.dp UAT -vcsuser user1 -vcspassword password -release 20160401
REM #       Example 4: envExec -list
REM #
REM #	            arg1=  -list or the orchestration property file path (full or relative path)
REM #				arg2=  [-list or env_type]
REM #                      [-list] optional parameter to list out the environment type / config property file mapping pairs
REM #                      [env_type] optional parameter when used must be the 2nd parameter.  If used then -config is not used.
REM #                                 env_type is an environment short name that maps to a configuration property file.  
REM #                                 Example=DEV~deploy_NOVCS_DEV1,UAT~deploy_NOVCS_UAT1,PROD~deploy_NOVCS_PROD1
REM #				arg2-3=[-config deploy.properties] optional parameter specifying the deployment configuration property file.  
REM #                      If used then "env_type" is not used.
REM #               arg4-5=[-vcsuser username] optional parameter specifying the vcs username
REM #               arg6-7=[-vcspassword password] optional parameter specifying the vcs password
REM #				arg8-9=[-release YYYYMMDD] optional parameter used to specify the release folder for the VCS
REM ############################################################################################################################

REM # Set debug=1 to print out debug statements
set debug=0

REM # Get the input parameters
set FILENAME=%1
set P1=%1
set P2=%2
set P3=%3
set P4=%4
set P5=%5
set P6=%6
set P7=%7
set P8=%8
set P9=%9
set PWD=%CD%
REM # Remove double quotes around arguments
setlocal EnableDelayedExpansion
	if defined FILENAME set LFILENAME=!FILENAME:"=!
endlocal & SET FILENAME=%LFILENAME%

REM # Validate environment variables
if not defined PDTOOL_BIN (
   echo.PDTOOL_BIN is not defined properly.
   echo.Execute "startEnv.cmd" to start a new command shell.
   exit /B 1
)
if not exist %PDTOOL_BIN% (
   echo.PDTOOL_BIN does not exist.  PDTOOL_BIN=%PDTOOL_BIN%
   echo.Execute "startEnv.cmd" to start a new command shell.
   exit /B 1
)

REM # Determine if Param1 or Param2 = -list
if "%P1%"=="-list" (
   call envConfig.cmd %P1%
   exit /B 1
)
if "%P2%"=="-list" (
   call envConfig.cmd %P2%
   exit /B 1
)

REM # Validate the input FILENAME variable
if not defined FILENAME (
   echo.ERROR: The filename was not provided.
   goto USAGE
)
REM # Check to see if the file exists in the current directory
if exist "%FILENAME%" (
   set FILENAME=%PWD%\%FILENAME%
   goto CONTINUE
)
REM # If the file does not exist and the current directory is the PDTOOL_PLANS directory then assume the file is not found and throw an error.
if "%CD%"=="%PDTOOL_PLANS%" goto ERROR1
REM # Assume the directory is not PDTOOL_PLANS so automatically search for it in PDTOOL_PLANS\FILENAME and if not there then throw an error.
if not exist "%PDTOOL_PLANS%\%FILENAME%" goto ERROR1
set FILENAME=%PDTOOL_PLANS%\%FILENAME%
goto CONTINUE

:ERROR1
   echo.USAGE: The filename=[%FILENAME%] does not exist.
   goto USAGE
 
:USAGE
   echo.
   echo.Execute a deployment plan with an optional release folder:
   echo.USAGE 1: %0 [deploy-plan.dp] [config-property-short-name or -config config-property-file.properties] [-vcsuser user1] [-vcspassword password] [-release VCS_RELEASE_FOLDER]
   echo.
   echo.List the Environment Configuration pairs:
   echo.USAGE 2: %0 [-list]
   exit /B 1
   
:CONTINUE
if "%debug%"=="1" echo.[DEBUG] %0:: FILENAME=%FILENAME%

cd %PDTOOL_BIN%
REM # Determine what the -config config.properties should be set to
call envConfig.cmd %P2%
SET ERROR=%ERRORLEVEL%
IF %ERROR% GTR 0 exit /B %ERROR%
if defined CONFIG_PROPERTY_NAME set P2=-config %CONFIG_PROPERTY_NAME%

REM # Invoke the PDTool command
echo.%0 COMMAND: call ExecutePDTool.bat -exec %FILENAME% %P2% %P3% %P4% %P5% %P6% %P7% %P8% %P9%
echo.
call ExecutePDTool.bat -exec %FILENAME% %P2% %P3% %P4% %P5% %P6% %P7% %P8% %P9%
cd %PWD%