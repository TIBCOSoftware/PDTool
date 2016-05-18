@echo off
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
REM # Execute the change directory
cd %PDTOOL_BIN%
echo.Current Dir: %CD%