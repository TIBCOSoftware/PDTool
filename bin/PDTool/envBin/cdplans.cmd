@echo off
REM # Validate environment variables
if not defined PDTOOL_PLANS (
   echo.PDTOOL_PLANS is not defined properly.
   echo.Execute "startEnv.cmd" to start a new command shell.
   exit /B 1
)
if not exist %PDTOOL_PLANS% (
   echo.PDTOOL_PLANS does not exist.  PDTOOL_PLANS=%PDTOOL_PLANS%
   echo.Execute "startEnv.cmd" to start a new command shell.
   exit /B 1
)
REM # Execute the change directory
cd %PDTOOL_PLANS%
echo.Current Dir: %CD%