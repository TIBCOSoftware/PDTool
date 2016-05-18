@echo off
REM # Validate environment variables
if not defined PDTOOL_MODULES (
   echo.PDTOOL_MODULES is not defined properly.
   echo.Execute "startEnv.cmd" to start a new command shell.
   exit /B 1
)
if not exist %PDTOOL_MODULES% (
   echo.PDTOOL_MODULES does not exist.  PDTOOL_MODULES=%PDTOOL_MODULES%
   echo.Execute "startEnv.cmd" to start a new command shell.
   exit /B 1
)
REM # Execute the change directory
cd %PDTOOL_MODULES%
echo.Current Dir: %CD%