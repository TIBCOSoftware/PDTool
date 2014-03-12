@echo off
setlocal
REM ######################################################################
REM # (c) 2014 Cisco and/or its affiliates. All rights reserved.
REM ######################################################################
REM ##############################################################################################
REM # script_name: regression_config_driver.bat
REM #
REM # author:      Mike Tinius
REM # date:        February 15, 2013
REM # description: The scripts in the directory are for executing regression tests against PDTool61 and PDTool62
REM #              and all of the various environment combinations including windows and unix and the various 
REM #              VCS software.  The regression script folder (/regression) may be placed anywhere on your
REM #              computer as long as the regressioin_confi_driver.bat|.sh is executed from within the 
REM #              regression directory.  This allows you to have one copy of the regression scripts and perform
REM #              a regression against any PDTool directory since the PDTool home dir is passed into the script.
REM #instructions:
REM #			  This script must be executed from the PDTool regression directory
REM #			  1. cd /regression
REM #             2. regression_config_driver.bat [regression_config_windows.txt] "[PDTOOL_HOME]" [Y|N]
REM #                  param 1: Review regression/config_lists for parameter 1
REM #                  param 2: Enclose the full path of PDTool Home dir in double quotes
REM #                  param 3: Debug parameter is Y or N.  If nothing is provided the default is N
REM #
REM ##############################################################################################
REM #
REM # Retrieve input variables and remove double quotes
set CONFIG_FILE_LIST=%~1
set PDTOOL_HOME=%~2
REM # Debug Y or N
set DEBUG=%~3

REM # Set custom variables
call setVars.bat

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
:generateFileName
:: -------------------------------------------------------------
:: Generate a unique file name with a date and timestamp
:: PREPEND=Prepend the date/time to the file name
:: POSTPEND=Postpend the date/time to the file name
:: Syntax: CALL :generateFileName [PREPEND|POSTPEND] baseFileName ext OUT_VAR_NAME
:: Example: CALL :generateFileName PREPEND file txt FILE_NAME
SET append=%1
SET baseFileName=%2
SET extension=%3
SET DATE_YYYYMMDD=%date:~10,4%%date:~4,2%%date:~7,2%
SET TIME_TEMP=%TIME::=%
SET TIME_HHMMSS=%TIME_TEMP:~0,6%
REM # Default is to POSTPEND date/time
SET _FILE_NAME=%baseFileName%_%DATE_YYYYMMDD%_%TIME_HHMMSS%
IF "%append%" == "PREPEND" SET _FILE_NAME=%DATE_YYYYMMDD%_%TIME_HHMMSS%_%baseFileName%
SET %4=%_FILE_NAME: =_%.%extension%
GOTO:EOF

:: -------------------------------------------------------------
:getBaseFileName
:: -------------------------------------------------------------
:: Get the base file name for a file like file.txt
:: Syntax: CALL :getBaseFileName filename OUT_VAR_NAME
:: Example: CALL :generateFileName file.txt FILE_NAME
SET baseFileName=%~n1
SET %2=%baseFileName%
GOTO:EOF

:: -------------------------------------------------------------
:BEGIN
:: -------------------------------------------------------------
REM # Set regresion environment variables
set REGRESSION_HOME=%CD%
set REGRESSION_CONFIG=%REGRESSION_HOME%\config
set REGRESSION_PLANS=%REGRESSION_HOME%\plans
set REGRESSION_CONFIG_LISTS=%REGRESSION_HOME%\config_lists

REM # Validate the input parameters
if "%CONFIG_FILE_LIST%" == "" (
  echo.
  echo USAGE: %0 [regression_config_list.txt] [PDTOOL_HOME] [DEBUG=Y or N]
  echo    Provide a regression list of deployment plan files
  echo.
  echo    e.g. regression_config_driver.bat regression_win_6_2_config.txt "D:\dev\PDTool62" Y
  exit /B 2
)
if NOT EXIST %REGRESSION_CONFIG_LISTS%\%CONFIG_FILE_LIST% (
  echo.
  echo The regression config list file that was provided does not exist: %CONFIG_FILE_LIST%
  echo Regression configuration list directory: %REGRESSION_CONFIG_LISTS%
  exit /B 2
)
if "%PDTOOL_HOME%" == "" (
  echo.
  echo USAGE: %0 [regression_config_list.txt] [PDTOOL_HOME] [DEBUG=Y or N]
  echo    Provide the PDTOOL_HOME directory in quotes.
  echo.
  echo    e.g. regression_config_driver.bat regression_win_6_2_config.txt "D:\dev\PDTool62" Y
  exit /B 2
)
if NOT EXIST %PDTOOL_HOME% (
  echo.
  echo The PDTOOL_HOME directory does not exist: %PDTOOL_HOME%
  exit /B 2
)
if "%DEBUG%" == "" set DEBUG=N

REM # Set PDTOOL_HOME environment variables
set PDTOOL_CONFIG=%PDTOOL_HOME%\resources\config

REM # Initialize the log file
CALL :getBaseFileName %CONFIG_FILE_LIST% BASE_FILE_NAME
CALL :generateFileName PREPEND %BASE_FILE_NAME% log LOG_NAME
set LOG_HOME=%REGRESSION_HOME%\logs\%BASE_FILE_NAME%
IF NOT EXIST %LOG_HOME% mkdir %LOG_HOME%
set LOG_PATH=%LOG_HOME%\%LOG_NAME%

REM # Provide instruction on how to access the current log
echo.
echo ########################################################################################################################################
echo.
echo To view the log path use this command: 
echo.--------------------------------------
echo.
echo more %LOG_PATH%
echo.
echo ########################################################################################################################################
echo.

REM # Begin writing to the log file.
echo Regression Log File Results > %LOG_PATH%
echo %DATE% %TIME% >> %LOG_PATH%
echo --------------------------- >> %LOG_PATH%
echo.>> %LOG_PATH%

set STATUS_CONFIG_OVERALL=PASS
set STATUS_EXIT=FALSE
REM # REM directory where status files are stored temporarily
set STATUS_DIR=status
if NOT EXIST status mkdir %STATUS_DIR%
echo %STATUS_CONFIG_OVERALL% > %STATUS_DIR%\status_config_overall.txt
echo %STATUS_EXIT% > %STATUS_DIR%\status_exit.txt
echo 0 > %STATUS_DIR%\num_plans.txt

FOR /F "eol=# tokens=1,2,3*" %%i  IN (%REGRESSION_CONFIG_LISTS%\%CONFIG_FILE_LIST%) DO (
  @echo.
  @echo.###########################################################################
  @echo Execute: 
  @echo    Config file Name = %%i.properties
  @echo      Plan file list = %%j
  @echo.###########################################################################
  @echo. 

  set STATUS_PLAN_OVERALL=PASS
  echo PASS > %STATUS_DIR%\status_plan_overall.txt

  REM # Execute the plan driver script: 
  call :debug "call regression_plan_driver.bat [CONFIG_FILE]  [PLAN_FILE_LIST]  [PDTOOL_HOME]  [REGRESSION_HOME]  [LOG_PATH] [LOG_HOME] [STATUS_DIR] [DEBUG]"
  call :debug "call regression_plan_driver.bat %%i %%j "%PDTOOL_HOME%" "%REGRESSION_HOME%" "%LOG_PATH%" "%LOG_HOME%" "%STATUS_DIR%" "%DEBUG%"" 1
  call regression_plan_driver.bat %%i %%j "%PDTOOL_HOME%" "%REGRESSION_HOME%" "%LOG_PATH%" "%LOG_HOME%" "%STATUS_DIR%" "%DEBUG%"
  
  echo.
  set /P STATUS_PLAN_OVERALL=< %STATUS_DIR%\status_plan_overall.txt
  call :debug "STATUS_PLAN_OVERALL=%STATUS_PLAN_OVERALL%" 0

  REM # Check for exit status
  set /P STATUS_EXIT=< %STATUS_DIR%\status_exit.txt
  call :debug "STATUS_EXIT=%STATUS_EXIT%" 0
  if "%STATUS_EXIT%" == "TRUE" goto FINISH
)
:: ------
:FINISH
:: ------
REM # Add up all of the plan counts to get the total plans executed
set /A TOTAL_PLANS=0
FOR /F "eol=# tokens=1,2*" %%i  IN (%STATUS_DIR%\num_plans.txt) DO (
   set /A TOTAL_PLANS+=%%i
)

REM # Output the end date/time
echo ----------------------------------------------------- >> %LOG_PATH%
echo %DATE% %TIME%  Total Plans Executed: %TOTAL_PLANS% >> %LOG_PATH%

REM # Overall status for this regression
set /P STATUS_CONFIG_OVERALL=< %STATUS_DIR%\status_config_overall.txt
echo %STATUS_CONFIG_OVERALL%: Overall status for this regression >> %LOG_PATH%
call :debug "OVERALL CONFIG STATUS=%STATUS_CONFIG_OVERALL%" 0

REM # Clean up status files...delete them
if EXIST %STATUS_DIR%\status_exit.txt del /F /Q %STATUS_DIR%\status_exit.txt
if EXIST %STATUS_DIR%\status_config_overall.txt del /F /Q %STATUS_DIR%\status_config_overall.txt
if EXIST %STATUS_DIR%\status_plan_overall.txt del /F /Q %STATUS_DIR%\status_plan_overall.txt
if EXIST %STATUS_DIR%\num_plans.txt del /F /Q %STATUS_DIR%\num_plans.txt

REM # Provide instruction on how to access the current log
echo.
echo ########################################################################################################################################
echo %DATE% %TIME%  Total Plans Executed: %TOTAL_PLANS%
echo.
echo To view the log path use this command: 
echo.--------------------------------------
echo.
echo more %LOG_PATH%
echo.
echo ########################################################################################################################################
echo.

REM # Provide an exit code for programs that may invoke this script
if "%STATUS_CONFIG_OVERALL%" == "FAIL" goto FAIL
  exit /B 0
:: ------
:FAIL
:: ------
  exit /B 1
