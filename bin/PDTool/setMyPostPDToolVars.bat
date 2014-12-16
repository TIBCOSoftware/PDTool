@echo off
REM ###########################################################################################################################
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
REM #==========================================================
REM # setMyPostPDToolVars.bat :: Set Environment Variables
REM #==========================================================
REM # This file gets invoked by setVars.bat after setVars.bat has been executed.
REM # Use cases for post-processing:
REM #   1. Post-processing is generally only used in conjunction with changing the order of precendence in which variables are retrieved.
REM #         The default "propertyOrderPrecedence=JVM PROPERTY_FILE SYSTEM"
REM #         In some circumstances it may be necessary to override the default "propertyOrderPrecedence=SYSTEM JVM PROPERTY_FILE" in order to
REM #         retrieve variables from the files system "SYSTEM" first and ignore the variables set in the /config/*.properties or /modules/*.xml files.
REM #         This allows the users of PDTool to rewrite a variable that already exists in .properties or .xml files.
REM #
REM # Instructions: 
REM #    1. Modify variables as needed.
REM #    2. Add new variables to the function :writeOutput at the bottom of this batch file when new variables are added.
REM #    3. Copy this file to a location outside of the PDToolStudio installation so that it is not overwritten during upgrade.
REM #    4. Modify setVars.bat "MY_VARS_HOME" variable to point to the directory that contains this file.
REM #    5. To encrypt the password in this file:
REM #       a) Open a windows command line
REM #       b) cd <path-to-pdtool>\PDTool62\bin
REM #       c) ExecutePDTool.bat -encrypt <path-to-file>\setMyPostPDToolVars.bat
REM #=======================================================================================================
REM # CREATE/MODIFY CUSTOM POST-PROCESSING VARIABLES BELOW THIS POINT
REM #=======================================================================================================
REM # 0=Do not print this section, 1=Print this section
set GEN_PRINT=1
REM #---------------------------------------------
REM # Property Order Precedence
REM #---------------------------------------------
REM # The property order of precedence defines which properties are retrieved in what order.
REM #   JVM - These are properties that are set on the JVM command line with a -DVAR=value
REM #   PROPERTY_FILE - These are the variables set in the configuration property file like deploy.properties or in the VCSModule.xml
REM #   SYSTEM - These are variables that are set in batch files in the operating system prior to invocation of PDTool.
REM # If left blank, the default=JVM PROPERTY_FILE SYSTEM
REM # However, it may be necessary to be able to override what is in the property file and pick up an environment variable first.
rem set propertyOrderPrecedence=SYSTEM JVM PROPERTY_FILE
REM #
REM # Rewrite existing variables
rem set MODULE_HOME=%PROJECT_HOME%/deploy/%USERNAME%/modules
REM #
REM #=======================================================================================================
REM # CREATE/MODIFY VARIABLES ABOVE THIS POINT
REM #=======================================================================================================
REM #
if not defined PRINT_VARS echo PRINT_VARS is not defined.  Set default PRINT_VARS=1
if not defined PRINT_VARS set PRINT_VARS=1
REM # Print out the setVars.bat variables
if %PRINT_VARS%==1 call:writeOutput %0
REM #
goto END
REM #
REM #==========================================================
REM # FUNCTIONS
REM #==========================================================
:writeOutput
::#---------------------------------------------
::# Print out the setVars.bat variables
::# CALL:writeOutput %0
::# 
::# note: For printing passwords use the following processing style:
::#    1. Put quotes around firsr variable.
::#    2. Use PR_PASSWORD for the return variable
::#    3. Use delayed expansion !PR_PASSWORD! to print out return value.
::#    4. Example is shown below:
::#       CALL:printablePassword "%TFS_VCS_PASSWORD%" PR_PASSWORD
::#       set MSG=!MSG!TFS_VCS_PASSWORD          =!PR_PASSWORD!!LF!
::#---------------------------------------------
set filename=%1
set MSG=
SETLOCAL ENABLEDELAYEDEXPANSION
REM # LF must have 2 blank lines following it to create a line feed
set LF=^


set MSG=!MSG!########################################################################################################################################!LF!
set MSG=!MSG!%filename%: Setting post-processing custom variables!LF!
set MSG=!MSG!########################################################################################################################################!LF!
if %GEN_PRINT%==1 (
rem set MSG=!MSG!propertyOrderPrecedence  =%propertyOrderPrecedence%!LF!
rem set MSG=!MSG!MODULE_HOME              =%MODULE_HOME%!LF!
)
echo.!MSG!
REM # Output to the default log file if the variable DEFAULT_LOG_PATH is defined in ExecutePDTool.bat
if not defined DEFAULT_LOG_PATH goto WRITEOUTPUTEND
   echo.!MSG!>>%DEFAULT_LOG_PATH%
:WRITEOUTPUTEND
ENDLOCAL
GOTO:EOF
REM #
::#---------------------------------------------
:printablePassword
::#---------------------------------------------
::# Return a printable password. 
::# If encrypted print as is. 
::# If not encryped return ******** for printing.
::# CALL:printablePassword "%PASSWORD%" PR_PASSWORD
::#   pswd    [in]  Enclose input in double quotes
::#   pswdout [out] printable password
::#---------------------------------------------
set pswd=%1
REM # Remove double quotes
setlocal EnableDelayedExpansion
if defined pswd set tpswd=!pswd:"=!
endlocal & SET pswd=%tpswd%
set pswdSubstr=%pswd:~0,10%
set pswdout=
if "%pswd%" NEQ "" set pswdout=********
if "%pswdSubstr%" == "Encrypted:" set pswdout=%pswd%
set %2=%pswdout%
GOTO:EOF
REM #
REM #==========================================================
REM # END FUNCTIONS
REM #==========================================================
:END