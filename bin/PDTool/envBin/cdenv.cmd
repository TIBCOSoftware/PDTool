@echo off
REM # Validate environment variables
if not defined PDTOOL_BIN_ENV (
   echo.PDTOOL_BIN_ENV is not defined properly.
   echo.Execute "startEnv.cmd" to start a new command shell.
   exit /B 1
)
if not exist %PDTOOL_BIN_ENV% (
   echo.PDTOOL_BIN_ENV does not exist.  PDTOOL_BIN_ENV=%PDTOOL_BIN_ENV%
   echo.Execute "startEnv.cmd" to start a new command shell.
   exit /B 1
)
REM # Execute the change directory
cd %PDTOOL_BIN_ENV%
echo.Current Dir: %CD%