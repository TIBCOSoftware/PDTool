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
REM #
REM # Encrypt a PDTool or PDToolStudio file
REM #
REM # encrypt.bat filename [-bypass "string1,string2]
REM #    Once "startEnv.cmd" has been run, this batch script can be run from any directory.
REM ############################################################################################################################
set FILENAME=%1
shift
set P2=%1
set P3=%2
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
echo.COMMAND: call ExecutePDTool.bat -encrypt %PWD%\%FILENAME% %P2% %P3%
echo.
call ExecutePDToolStudio.bat -encrypt %PWD%\%FILENAME% %P2% %P3%
cd %PWD%