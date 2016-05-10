@echo off
REM # Validate environment variables
if not defined PDTOOL_CONFIG (
   echo.PDTOOL_CONFIG is not defined properly.
   echo.Execute "startEnv.cmd" to start a new command shell.
   exit /B 1
)
if not exist %PDTOOL_CONFIG% (
   echo.PDTOOL_CONFIG does not exist.  PDTOOL_CONFIG=%PDTOOL_CONFIG%
   echo.Execute "startEnv.cmd" to start a new command shell.
   exit /B 1
)
REM # Execute the change directory
cd %PDTOOL_CONFIG%
echo.Current Dir: %CD%