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
REM # Author: Mike Tinius, Data Virtualization Business Unit, Advanced Services
REM # Date:   June 2015
REM # PDTool Regression Module - Migration Automated Test Framework
REM #=======================================================================================
REM # Instructions: 
REM #   XsltTransform.bat MIGRATION_TEST_XSLT_MODULE   MIGRATION_TEST_XSLT_XSLT  [MIGRATION_TEST_XSLT_OUTPUT]
REM #
REM #       MIGRATION_TEST_XSLT_MODULE - Server Attribure XML File to be transformed.  e.g. ServerAttributes_6.2_UAT.xml
REM #		MIGRATION_TEST_XSLT_XSLT - XSLT Transformation script file.  e.g. ServerAttributes_Transform_62_to_70.xsl
REM #		MIGRATION_TEST_XSLT_OUTPUT - Output file path for transformed file.   e.g. ServerAttributes_6.2_UAT_update.xml
REM #			If this is left blank the transformed XML will display to the console window.
REM #=======================================================================================
REM #
REM ######################################
REM EXECUTE THE SCRIPT
REM ######################################
set MIGRATION_TEST_XSLT_MODULE=%1
set MIGRATION_TEST_XSLT_XSLT=%2
set MIGRATION_TEST_XSLT_OUTPUT=%3
if not defined MIGRATION_TEST_XSLT_HOME set MIGRATION_TEST_XSLT_HOME=%CD%
if not defined MIGRATION_TEST_XSLT_MODULE goto XSLT_NOT_DEFINED
if not defined MIGRATION_TEST_XSLT_XSLT goto XSLT_NOT_DEFINED
if not exist %MIGRATION_TEST_XSLT_MODULE% goto XSLT_NOT_EXIST_XML
if not exist %MIGRATION_TEST_XSLT_XSLT% goto XSLT_NOT_EXIST_XSLT
set ERROR=1

rem pushd "%~dp0"
if not defined MIGRATION_TEST_XSLT_OUTPUT goto XSLT_EXEC_NO_OUTPUT
if defined MIGRATION_TEST_XSLT_OUTPUT goto XSLT_EXEC_OUTPUT
goto XSLT_POST_PROCESSING

:XSLT_EXEC_NO_OUTPUT
	java -classpath "%MIGRATION_TEST_XSLT_HOME%\xt20051206.jar;%MIGRATION_TEST_XSLT_HOME%\xp.jar" com.jclark.xsl.sax.Driver %MIGRATION_TEST_XSLT_MODULE% %MIGRATION_TEST_XSLT_XSLT%
	set ERROR=%ERRORLEVEL%
	if %DEBUG%==Y echo.%0 ERROR=%ERROR% ... code section: "XSLT_EXEC_NO_OUTPUT"
goto XSLT_POST_PROCESSING

:XSLT_EXEC_OUTPUT
	java -classpath "%MIGRATION_TEST_XSLT_HOME%\xt20051206.jar;%MIGRATION_TEST_XSLT_HOME%\xp.jar" com.jclark.xsl.sax.Driver %MIGRATION_TEST_XSLT_MODULE% %MIGRATION_TEST_XSLT_XSLT% > %MIGRATION_TEST_XSLT_OUTPUT%
	set ERROR=%ERRORLEVEL%
	if %DEBUG%==Y echo.%0 ERROR=%ERROR% ... code section: "XSLT_EXEC_OUTPUT"

:XSLT_POST_PROCESSING
if %DEBUG%==Y echo.%0 ERROR=%ERROR% ... code section: "XSLT_POST_PROCESSING"
if %ERROR% NEQ 0 goto XSLT_ERROR
exit /b 0


:XSLT_NOT_DEFINED
echo.
echo.USAGE: %0 XML_Document XSLT_Transform [OUTPUT_FILE]
echo.
echo.   If MIGRATION_TEST_XSLT_OUTPUT is not provided the output will go to the command line window.
exit /b 1

:XSLT_NOT_EXIST_XML
echo.
echo.ERROR: MIGRATION_TEST_XSLT_MODULE file does not exist: %MIGRATION_TEST_XSLT_MODULE%
exit /b 1

:XSLT_NOT_EXIST_XSLT
echo.
echo.ERROR: MIGRATION_TEST_XSLT_XSLT file does not exist: %MIGRATION_TEST_XSLT_XSLT%
exit /b 1

:XSLT_ERROR
echo.
echo.ERROR: Transformation error occurred.
exit /b 1
