@echo off
REM ############################################################################################################################
REM # (c) 2014 Cisco and/or its affiliates. All rights reserved.
REM # 
REM # This software is released under the Eclipse Public License. The details can be found in the file LICENSE. 
REM # Any dependent libraries supplied by third parties are provided under their own open source licenses as 
REM # described in their own LICENSE files, generally named .LICENSE.txt. The libraries supplied by Cisco as 
REM # part of the Composite Information Server/Cisco Data Virtualization Server, particularly csadmin-XXXX.jar, 
REM # csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar, csext-XXXX.jar, csjdbc-XXXX.jar, 
REM # csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar, and customproc-XXXX.jar (where -XXXX is an 
REM # optional version number) are provided as a convenience, but are covered under the licensing for the 
REM # Composite Information Server/Cisco Data Virtualization Server. They cannot be used in any way except 
REM # through a valid license for that product.
REM # 
REM # This software is released AS-IS!. Support for this software is not covered by standard maintenance agreements with Cisco. 
REM # Any support for this software by Cisco would be covered by paid consulting agreements, and would be billable work.
REM # 
REM ############################################################################################################################
REM #
REM # Encrypt a PDTool or PDToolStudio file
REM #
REM # encrypt.bat filename
REM #    Once "startEnv.cmd" has been run, this batch script can be run from any directory.
REM ############################################################################################################################
set FILENAME=%1
set PWD=%CD%
REM # Remove double quotes around arguments
setlocal EnableDelayedExpansion
	if defined FILENAME set LFILENAME=!FILENAME:"=!
endlocal & SET FILENAME=%LFILENAME%
if not defined FILENAME (
   echo.USAGE: The filename was not provided.
   exit /B 1
)
if not exist %FILENAME% (
   echo.USAGE: The filename=[%FILENAME%] does not exist.
   exit /B 1
)
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

cd %PDTOOL_BIN%
call ExecutePDTool.bat -encrypt %PWD%\%1
cd %PWD%