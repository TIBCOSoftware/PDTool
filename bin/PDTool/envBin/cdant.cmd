@echo off
REM # Validate environment variables
if not defined PDTOOL_ANT (
   echo.PDTOOL_ANT is not defined properly.
   echo.Execute "startEnv.cmd" to start a new command shell.
   exit /B 1
)
if not exist %PDTOOL_ANT% (
   echo.PDTOOL_ANT does not exist.  PDTOOL_ANT=%PDTOOL_ANT%
   echo.Execute "startEnv.cmd" to start a new command shell.
   exit /B 1
)
REM # Execute the change directory
cd %PDTOOL_ANT%
echo.Current Dir: %CD%