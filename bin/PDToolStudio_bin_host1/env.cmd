@echo off
REM ###########################################
REM # Set STANDARD VARIABLES
REM ###########################################
REM # Set the existing variables in order to get VCS_HOME settings.
pushd %CD%
call setVars.bat
popd
cls
REM # Set PDTOOL_HOME and PDTOOL_BIN
pushd %CD%
REM # Dynamically get the current directory such as bin
for %%a in (.) do set CURRDIR=%%~na
cd ..
set PDTOOL_HOME=%CD%
set PDTOOL_BIN=%PDTOOL_HOME%\%CURRDIR%
popd

REM # Set VCS_HOME with any VCS home that is currently set
set VCS_HOME=
if defined TFS_HOME set VCS_HOME=%TFS_HOME%;%VCS_HOME%
if defined SVN_HOME set VCS_HOME=%SVN_HOME%;%VCS_HOME%
if defined GIT_HOME set VCS_HOME=%GIT_HOME%;%VCS_HOME%
if defined P4_HOME set VCS_HOME=%P4_HOME%;%VCS_HOME%
if defined CVS_HOME set VCS_HOME=%CVS_HOME%;%VCS_HOME%
if defined VCS_HOME set PATH=%VCS_HOME%;%PATH%
if defined PDTOOL_BIN set PATH=%PDTOOL_BIN%;%PATH%
echo.COMMAND: CMD /T:%COLOR% /K "%ENV%"
echo.PDTOOL_HOME=%PDTOOL_HOME%
echo.PDTOOL_BIN=%PDTOOL_BIN%
echo.VCS_HOME=%VCS_HOME%
echo.PATH=%PATH%
echo.

REM ###########################################
REM # Set CUSTOM VARIABLES
REM ###########################################
if exist envCustom.cmd call envCustom.cmd
:END