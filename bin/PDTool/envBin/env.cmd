@echo off
REM ###########################################
REM # Set STANDARD VARIABLES
REM ###########################################

REM # Save the current directory and invoke the PDTool variables to get the VCS variables.
pushd %CD%
cd ..
for /f "tokens=* delims= " %%I in ("%CD%") do set BINDIR=%%~nI
call setVars.bat
cls
popd

REM # Set popular default PDTool folder variables
set PDTOOL_BIN=%PDTOOL_HOME%\%BINDIR%
set PDTOOL_BIN_ENV=%PDTOOL_BIN%\envBin
set PDTOOL_PLANS=%PDTOOL_HOME%\resources\plans
set PDTOOL_ANT=%PDTOOL_HOME%\resources\ant
set PDTOOL_MODULES=%PDTOOL_HOME%\resources\modules
set PDTOOL_CONFIG=%PDTOOL_HOME%\resources\config
if not exist "%PDTOOL_BIN%" set PDTOOL_BIN=
if not exist "%PDTOOL_BIN_ENV%" set PDTOOL_BIN_ENV=
if not exist "%PDTOOL_PLANS%" set PDTOOL_PLANS=
if not exist "%PDTOOL_ANT%" set PDTOOL_ANT=
if not exist "%PDTOOL_MODULES%" set PDTOOL_MODULES=
if not exist "%PDTOOL_CONFIG%" set PDTOOL_CONFIG=

REM # Set VCS_HOME with any VCS home that is currently set
set VCS_HOME=
if defined TFS_HOME set VCS_HOME=%TFS_HOME%;%VCS_HOME%
if defined SVN_HOME set VCS_HOME=%SVN_HOME%;%VCS_HOME%
if defined GIT_HOME set VCS_HOME=%GIT_HOME%;%VCS_HOME%
if defined P4_HOME set VCS_HOME=%P4_HOME%;%VCS_HOME%
if defined CVS_HOME set VCS_HOME=%CVS_HOME%;%VCS_HOME%
if defined VCS_HOME set PATH=%VCS_HOME%;%PATH%
if defined PDTOOL_BIN set PATH=%PDTOOL_BIN%;%PATH%
if defined PDTOOL_BIN_ENV set PATH=%PDTOOL_BIN_ENV%;%PATH%
echo.COMMAND: CMD /T:%COLOR% /K "%ENV%"
echo.PDTOOL_HOME=%PDTOOL_HOME%
echo.PDTOOL_BIN=%PDTOOL_BIN%
echo.PDTOOL_BIN_ENV=%PDTOOL_BIN_ENV%
echo.PDTOOL_PLANS=%PDTOOL_PLANS%
echo.PDTOOL_ANT=%PDTOOL_ANT%
echo.PDTOOL_MODULES=%PDTOOL_MODULES%
echo.PDTOOL_CONFIG=%PDTOOL_CONFIG%
echo.VCS_HOME=%VCS_HOME%
echo.PATH=%PATH%
echo.

REM ###########################################
REM # Invoke CUSTOM VARIABLES
REM ###########################################
REM # Invoke custom variables
if exist envCustom.cmd call envCustom.cmd
:END