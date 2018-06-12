@echo off
REM ############################################################################################################################
REM # (c) 2017 TIBCO Software Inc. All rights reserved.
REM # 
REM # Except as specified below, this software is licensed pursuant to the Eclipse Public License v. 1.0.
REM # The details can be found in the file LICENSE.
REM # 
REM # The following proprietary files are included as a convenience, and may not be used except pursuant
REM # to valid license to Composite Information Server or TIBCO(R) Data Virtualization Server:
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
REM #   e.g. set MY_VARS_HOME=c:\users\%USERNAME%\PDTool
set MY_VARS_HOME=C:\Users\%USERNAME%\PDToolRegression
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