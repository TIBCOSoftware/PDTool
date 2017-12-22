@echo off
REM ############################################################################################################################
REM # (c) 2017 TIBCO Software Inc. All rights reserved.
REM # 
REM # Except as specified below, this software is licensed pursuant to the Eclipse Public License v. 1.0.
REM # The details can be found in the file LICENSE.
REM # 
REM # The following proprietary files are included as a convenience, and may not be used except pursuant
REM # to valid license to Composite Information Server or TIBCO® Data Virtualization Server:
REM # csadmin-XXXX.jar, csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar,
REM # csext-XXXX.jar, csjdbc-XXXX.jar, csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar,
REM # and customproc-XXXX.jar (where -XXXX is an optional version number).  Any included third party files
REM # are licensed under the terms contained in their own accompanying LICENSE files, generally named .LICENSE.txt.
REM # 
REM # This software is licensed AS-IS. Support for this software is not covered by standard maintenance agreements with TIBCO.
REM # If you would like to obtain assistance with this software, such assistance may be obtained through a separate paid consulting
REM # agreement with TIBCO.
REM #
REM ############################################################################################################################
REM # NOTE: This script must be executed from the envBin directory for it to work properly.
REM #       It is directory context sensative.
REM ############################################################################################
REM #
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
if not exist "%PDTOOL_BIN%" set PDTOOL_BIN=
if not exist "%PDTOOL_BIN_ENV%" set PDTOOL_BIN_ENV=

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
echo.PDTOOL_BIN_ENV=%PDTOOL_BIN_ENV%
echo.VCS_HOME=%VCS_HOME%
echo.PATH=%PATH%
echo.

REM ###########################################
REM # Set CUSTOM VARIABLES
REM ###########################################
if exist envCustom.cmd call envCustom.cmd
:END