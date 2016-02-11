@echo off
REM ######################################################################
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
REM ######################################################################
REM #==========================================================
REM # setVars.bat :: Set Environment Variables
REM #==========================================================
REM # Instructions: 
REM #    1. Modify variables as needed.
REM #    2. Add new variables to the function :writeOutput at the bottom of this batch file when new variables are added.
REM #=======================================================================================================
REM # CREATE/MODIFY VARIABLES BELOW THIS POINT
REM #=======================================================================================================
REM # Initialize variables to unset them [required]
set MY_PRE_VARS_PATH=
set MY_POST_VARS_PATH=
REM #
REM # The My Vars path provides with the user the ability to set specific environment variables for their login
REM #   The location of these batch files is typically outside of the PDTOOL_HOME in the user's directory space.
REM #   e.g. set MY_VARS_HOME=c:\users\%USERNAME%\.compositesw\PDTool
set MY_VARS_HOME=C:\Users\%USERNAME%\.compositesw\PDToolRegression
REM #
if not defined MY_VARS_HOME goto MAIN
  set MY_PRE_VARS_PATH=%MY_VARS_HOME%\setMyPrePDToolVars_%VERSION%.bat
  set MY_POST_VARS_PATH=%MY_VARS_HOME%\setMyPostPDToolVars_%VERSION%.bat
  if not defined MY_PRE_VARS_PATH goto MAIN
  if exist "%MY_PRE_VARS_PATH%" call "%MY_PRE_VARS_PATH%"
:MAIN
REM ###################################################
REM # Set customer variables and add to writeOutput
REM ###################################################

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
REM #---------------------------------------------
REM # POST-PROCESSING CUSTOM VARIABLES:
REM #---------------------------------------------
if not defined MY_POST_VARS_PATH goto END
  if exist "%MY_POST_VARS_PATH%" call "%MY_POST_VARS_PATH%"
REM #
goto END
REM #==========================================================
REM # FUNCTIONS
REM #==========================================================
:writeOutput
::#---------------------------------------------
::# Print out the setVars.bat variables
::#---------------------------------------------
set filename=%1
SETLOCAL ENABLEDELAYEDEXPANSION
REM # LF must have 2 blank lines following it to create a line feed
set LF=^


set MSG=!MSG!########################################################################################################################################!LF!
set MSG=!MSG!%filename%: Setting PDTool variables!LF!
set MSG=!MSG!########################################################################################################################################!LF!
set MSG=!MSG!MY_VARS_HOME         =%MY_VARS_HOME%!LF!
set MSG=!MSG!MY_PRE_VARS_PATH     =%MY_PRE_VARS_PATH%!LF!
set MSG=!MSG!MY_POST_VARS_PATH    =%MY_POST_VARS_PATH%!LF!

echo.!MSG!
REM # Output to the default log file if the variable DEFAULT_LOG_PATH is defined in ExecutePDTool.bat
if not defined DEFAULT_LOG_PATH goto WRITEOUTPUTEND
   echo.!MSG!>>%DEFAULT_LOG_PATH%
:WRITEOUTPUTEND
ENDLOCAL
GOTO:EOF
REM #==========================================================
REM # END FUNCTIONS
REM #==========================================================
:END