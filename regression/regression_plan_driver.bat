@echo off
setlocal
REM ######################################################################
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
REM ######################################################################
REM ##############################################################################################
REM # script_name: regression_plan_driver.bat
REM #
REM # author:      Mike Tinius
REM # date:        February 15, 2013
REM # description: This script gets invoked by regression_config_driver.bat|.sh.
REM #              The purpose of this script is to execute the plan files found in the /plan_lists directory.
REM #              The regression_config_driver associates a PDTool config file with a plan file list.
REM #              This script loops through the plan file list and executes each PDTool deployment plan
REM #              and logs the PASS/FAIL results into a log file that can be quickly inspected for overall
REM #              regression results.
REM #instructions:
REM #			  Invoked by regression_config_driver.bat|.sh
REM #			  1. Invoked from the regression directory
REM #             2. regression_plan_driver.bat [CONFIG_FILE]  [PLAN_FILE_LIST]  [PDTOOL_HOME]  [REGRESSION_HOME]  [LOG_PATH] [LOG_HOME] [STATUS_DIR] [DEBUG]
REM #                  param 1: PDTool configuration property file.
REM #                  param 2: Regression plan file list.  This file contains a list of deployment plans to execute.
REM #                  param 3: The PDTool home directory.  Can be either PDTool61 or PDTool62.
REM #                  param 4: The Regression home directory.
REM #                  param 5: The full path to the log file.
REM #                  param 6: The Log home directory.
REM #                  param 7: The name of the status directory "status".
REM #                  param 8: A debug indicator which is either Y or N.
REM #
REM ##############################################################################################
goto BEGIN

#########################
# FUNCTION: debug()
#   print a debug message
#   with n blank lines following
#   e.g. debug "msg" 1
##########################
:: -------------------------------------------------------------
:debug
:: -------------------------------------------------------------
  REM # print the message
  if "%DEBUG%" == "Y" echo.DEBUG:: %~1
  REM # $2=echo blank lines
  set c=%2
  if "%c%" == "" goto endDebug
  if "%c%" == "0" goto endDebug
  SET /A XCOUNT=0
:: ------
:loopDebug
:: ------
  SET /A XCOUNT+=1
  IF %XCOUNT% GTR %c% (
    GOTO endDebug
  ) ELSE (
     echo.
    GOTO loopDebug
  )
:: ------
:endDebug
:: ------
GOTO:EOF

:: -------------------------------------------------------------
:ExecPDTool
:: -------------------------------------------------------------
setlocal
REM Get the parameters and remove surrounding quotes
set _CONFIG_FILE=%~2
set _PDTOOL_HOME=%~3
set _PLAN_FILE_PATH=%~4
set _LOG_PATH=%~5

set CURR_DIR=%CD%
cd %_PDTOOL_HOME%\bin
call :debug "call ExecutePDTool.bat -exec [PLAN_FILE_PATH] -config [CONFIG_FILE]"
echo "call ExecutePDTool.bat -exec %_PLAN_FILE_PATH% -config %_CONFIG_FILE%"
call ExecutePDTool.bat -exec %_PLAN_FILE_PATH% -config %_CONFIG_FILE%
if %errorlevel% NEQ 0 (
  set ret=FAIL
) else (
  set ret=PASS
)
cd %CURR_DIR%
echo %ret%: %_PLAN_FILE_PATH% >> %_LOG_PATH%
call :debug "" 0
call :debug "%0 %ret%: %_PLAN_FILE_PATH%" 0
( endlocal
  if "%ret%" == "FAIL" echo %ret% > %STATUS_DIR%\status_plan_overall.txt
  if "%ret%" == "FAIL" echo %ret% > %STATUS_DIR%\status_config_overall.txt
  call :debug "ExecPDTool: return=%ret%" 0
  set %~1=%ret%
)
exit /b

:: -------------------------------------------------------------
:BEGIN
:: -------------------------------------------------------------
REM Get the parameters and remove surrounding quotes
set P=CONFIG_FILE
set CONFIG_FILE=%~1
if "%CONFIG_FILE%" == "" goto USAGE

set P=PLAN_FILE_LIST
set PLAN_FILE_LIST=%~2
if "%PLAN_FILE_LIST%" == "" goto USAGE

set P=INP_PDTOOL_HOME
set INP_PDTOOL_HOME=%~3
if "%INP_PDTOOL_HOME%" == "" goto USAGE

set P=REGRESSION_HOME
set REGRESSION_HOME=%~4
if "%REGRESSION_HOME%" == "" goto USAGE

set P=LOG_PATH
set LOG_PATH=%~5
if "%LOG_PATH%" == "" goto USAGE

set P=LOG_HOME
set LOG_HOME=%~6
if "%LOG_HOME%" == "" goto USAGE

set P=STATUS_DIR
set STATUS_DIR=%~7
if "%STATUS_DIR%" == "" goto USAGE

set P=DEBUG
set DEBUG=%~8
if "%DEBUG%" == "" set DEBUG=N

REM # Set environment variables
set CONFIG_FILE_PROP=%CONFIG_FILE%.properties
set REGRESSION_CONFIG=%REGRESSION_HOME%\config
set REGRESSION_PLANS=%REGRESSION_HOME%\plans
set REGRESSION_PLAN_LISTS=%REGRESSION_HOME%\plan_lists
set PDTOOL_CONFIG=%INP_PDTOOL_HOME%\resources\config

REM # Check existence of files and directories
IF NOT EXIST status mkdir status
if NOT EXIST %REGRESSION_HOME% (
  echo The regression home directory that was provided does not exist: %REGRESSION_HOME%
  echo TRUE > %STATUS_DIR%\status_exit.txt
  exit /B 3
)
if NOT EXIST %INP_PDTOOL_HOME% (
  echo The PDtool home directory that was provided does not exist: %INP_PDTOOL_HOME%
  echo TRUE > %STATUS_DIR%\status_exit.txt
  exit /B 3
)
if NOT EXIST %REGRESSION_CONFIG%\%CONFIG_FILE_PROP% (
  echo The PDTool configuration file that was provided does not exist: %CONFIG_FILE_PROP%
  echo PDTool regression config directory: %REGRESSION_CONFIG%
  echo TRUE > %STATUS_DIR%\status_exit.txt
  exit /B 3
)
if NOT EXIST %REGRESSION_PLAN_LISTS%\%PLAN_FILE_LIST% (
  echo The regression list file that was provided does not exist: %PLAN_FILE_LIST%
  echo PDTool regression plan list directory: %REGRESSION_PLAN_LISTS%
  echo TRUE > %STATUS_DIR%\status_exit.txt
  exit /B 3
)
if NOT EXIST %LOG_PATH% (
  echo The regression log file that was provided does not exist: %LOG_PATH%
  echo TRUE > %STATUS_DIR%\status_exit.txt
  exit /B 3
)
if NOT EXIST %REGRESSION_PLANS% (
  echo The regression deployment plans directory does not exist: %REGRESSION_PLANS%
  echo TRUE > %STATUS_DIR%\status_exit.txt
  exit /B 3
)
if NOT EXIST %STATUS_DIR% (
  echo The status directory provided does not exist: %STATUS_DIR%
  echo TRUE > %STATUS_DIR%\status_exit.txt
  exit /B 3
)
if NOT EXIST %LOG_HOME% (
  echo The regression log file that was provided does not exist: %LOG_HOME%
  echo TRUE > %STATUS_DIR%\status_exit.txt
  exit /B 3
)

cd %REGRESSION_HOME%

REM # Save the current log file
copy %INP_PDTOOL_HOME%\logs\app.log  %INP_PDTOOL_HOME%\logs\app.log.sav

REM # Copy the regression configuration file to PDTool\config
copy /Y %REGRESSION_CONFIG%\%CONFIG_FILE_PROP% %PDTOOL_CONFIG%

REM # Initialize the log file for this configuration execution
echo ----------------------------------------------------- >> %LOG_PATH%
echo Regression Test for %CONFIG_FILE_PROP% >> %LOG_PATH%
echo %DATE% %TIME% >> %LOG_PATH%
echo ----------------------------------------------------- >> %LOG_PATH%

set /P STATUS_PLAN_OVERALL=< %STATUS_DIR%\status_plan_overall.txt
set /P STATUS_EXIT=< %STATUS_DIR%\status_exit.txt
call :debug "STATUS_OVERALL=%STATUS_PLAN_OVERALL%" 0
call :debug "STATUS_EXIT=%STATUS_EXIT%" 0
set /A NUM_PLANS=0

FOR /F "eol=# tokens=1,2*" %%i IN (%REGRESSION_PLAN_LISTS%\%PLAN_FILE_LIST%) DO (
  @echo.
  @echo.###########################################################################
  @echo Execute: 
  @echo    Plan file Name = %%i
  @echo.###########################################################################
  @echo.
  REM # Initialize the PDTool log file (app.log)
  echo.> %INP_PDTOOL_HOME%\logs\app.log

  if NOT EXIST %REGRESSION_PLANS%\%%i (
	echo FAIL: Plan file does not exist at location: %REGRESSION_PLANS%\%%i >> %LOG_PATH%
    echo Plan file does not exist at location: %REGRESSION_PLANS%\%%i >> %INP_PDTOOL_HOME%\logs\app.log 
	echo Plan file does not exist at location: %REGRESSION_PLANS%\%%i 
	set STATUS_EXIT=TRUE
	echo TRUE > %STATUS_DIR%\status_exit.txt
	set STATUS_PLAN_OVERALL=FAIL
    echo FAIL > %STATUS_DIR%\status_plan_overall.txt
    echo FAIL > %STATUS_DIR%\status_config_overall.txt
	goto BYPASS
  )
  
  call :ExecPDTool retval %CONFIG_FILE_PROP% "%INP_PDTOOL_HOME%" "%REGRESSION_PLANS%\%%i" "%LOG_PATH%"
  
  REM # Increment the number of plans executed by 1
  SET /A NUM_PLANS+=1

  :: ------
  :BYPASS 
  :: ------
  REM # Copy the PDTool log (app.log) to the specified plan file log
  copy %INP_PDTOOL_HOME%\logs\app.log  %LOG_HOME%\%CONFIG_FILE%-%%i.log
  
  REM # Check for exit status
  set /P STATUS_EXIT=< %STATUS_DIR%\status_exit.txt
  if "%STATUS_EXIT%" == "TRUE" goto FINISH
)

:: ------
:FINISH
:: ------
echo.%NUM_PLANS% >> %STATUS_DIR%\num_plans.txt
REM # Output the end date/time
echo ----------------------------------------------------- >> %LOG_PATH%
echo %DATE% %TIME%  Number Plans Executed: %NUM_PLANS% >> %LOG_PATH%

REM # Overall status for this configuration and plan file list
set /P STATUS_PLAN_OVERALL=< %STATUS_DIR%\status_plan_overall.txt
call :debug "OVERALL PLAN STATUS=%STATUS_PLAN_OVERALL%" 0
echo %STATUS_PLAN_OVERALL%: Overall status for %CONFIG_FILE_PROP% and %PLAN_FILE_LIST% >> %LOG_PATH%
echo ----------------------------------------------------- >> %LOG_PATH%
echo.>> %LOG_PATH%

REM # Restore the current log file
move %INP_PDTOOL_HOME%\logs\app.log.sav  %INP_PDTOOL_HOME%\logs\app.log

if "%STATUS_PLAN_OVERALL%" == "FAIL" goto FAIL2
  call :debug "return OVERALL PLAN STATUS=%STATUS_PLAN_OVERALL%" 0
  endlocal
  exit /B 0
:: ------
:FAIL2
:: ------
  call :debug "return OVERAL PLAN STATUS=%STATUS_PLAN_OVERALL%" 0
  endlocal
  exit /B 1

:: -------------------------------------------------------------
:USAGE
:: -------------------------------------------------------------
  echo USAGE: %0 [CONFIG_FILE]  [PLAN_FILE_LIST]  [PDTOOL_HOME]  [REGRESSION_HOME]  [LOG_PATH] [LOG_HOME] [DEBUG}
  echo    Missing parameter: %P%
  echo TRUE > %STATUS_DIR%\status_exit.txt
  endlocal
  exit /B 2