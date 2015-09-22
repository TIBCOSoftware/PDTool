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
REM #   e.g. set MY_VARS_HOME=c:\users\%USERNAME%\.compositesw\PDToolStudio<ver>
set MY_VARS_HOME=
REM #
if not defined MY_VARS_HOME goto MAIN
  set MY_PRE_VARS_PATH=%MY_VARS_HOME%\setMyPrePDToolStudioVars_host2.bat
  set MY_POST_VARS_PATH=%MY_VARS_HOME%\setMyPostPDToolStudioVars_host2.bat
  if not defined MY_PRE_VARS_PATH goto MAIN
  if exist "%MY_PRE_VARS_PATH%" call "%MY_PRE_VARS_PATH%"
:MAIN
REM #
REM #----------------------------------------------------------
REM # PDToolStudio CUSTOM MULTI-HOST ENVIRONMENT VARIABLES
REM #----------------------------------------------------------
REM # Name of the configuration property file located in PDToolStudio/resources/config
set CONFIG_PROPERTY_FILE=%MY_CONFIG_PROPERTY_FILE%
REM #
REM #----------------------------------------------------------
REM # PDToolStudio STANDARD ENVIRONMENT VARIABLES
REM #----------------------------------------------------------
REM #
REM # Set to JRE 1.7 Home Directory
set JAVA_HOME=%MY_JAVA_HOME%
REM #
REM #----------------------------------------------------------
REM # PDTool Substitute Drive
REM #----------------------------------------------------------
REM # Derive PROJECT_HOME from a substituted drive letter in order to shorten the overall path in an attempt to avert the "too long file name" errors
REM #
REM # ****** IMPORTANT ******
REM # This is only necessary when using TFS.   Subversion, Perforce and CVS do not require the use of a mapped drive to shorten the overall path length.
REM #
REM # Instructions:
REM #   1. Set the substituted path variable such as S: for PDToolStudio or P: for PDTool.
REM #   2. If configuring multiple bin_host then each /bin_host/set_vars.bat must have a unique drive letter.
REM #   3. Change this variable if you already have an S: or P: mapped.  It can be any unused drive letter.
REM #   4. The actual subst command is performed in the ExecutePDToolStudio batch file.
REM # CAUTION: Each instance of PDTool or PDToolStudio on a single host must use its own unique substitution letter and have its own workspace.
REM #          PDTool and PDToolStudio must NOT share the same workspace when installed on the same machine.       
REM # DEPRECATED:
REM #    SUBSTITUTE_DRIVE remains here for backward compatibility in case there are issues assigning a network drive.
set SUBSTITUTE_DRIVE=
REM # Use network drive to permantly assign a drive letter to a PDTool installation folder.
REM #    Currently only C: or D: drive is supported for PDTool installation.  This requires the standard share C$ or D$ to be present.
REM #    Create command used: net use %NETWORK_DRIVE% "\\%COMPUTERNAME%\%PDTOOL_HOME_DRIVE_SHARE%\%PROJECT_HOME_PHYSICAL%" /PERSISTENT:YES
REM #    Delete command used: net use %NETWORK_DRIVE% /DELETE
set NETWORK_DRIVE=%PDTOOL_SUBSTITUTE_DRIVE%
REM #
REM # Set the PROJECT_HOME so that it points to the substituted path variable
set PROJECT_HOME=
if defined SUBSTITUTE_DRIVE set PROJECT_HOME=%SUBSTITUTE_DRIVE%
if defined NETWORK_DRIVE set PROJECT_HOME=%NETWORK_DRIVE%
if not defined PROJECT_HOME ( 
  set PROJECT_HOME=%PDTOOL_HOME%
)
REM # Remember the PROJECT_HOME_PHYSICAL as it points to PROJECT_HOME full path 
set PROJECT_HOME_PHYSICAL=%PDTOOL_HOME%
REM #
REM #----------------------------------------------------------
REM # Configure the Java Heap Min and Max memory
REM #----------------------------------------------------------
set MIN_MEMORY=-Xms256m
set MAX_MEMORY=-Xmx512m
REM #
REM #----------------------------------------------------------
REM # PDTool Over SSL (https)
REM #----------------------------------------------------------
REM # If PDTool over SSL using HTTPS is required, then configure CERT_ARGS with the following correct CIS_HOME and trustStorePassword:
REM #   1. Weak TrustStore ships with CIS and PDTool
REM #      CERT_ARGS=-Djavax.net.ssl.trustStore="%PROJECT_HOME_PHYSICAL%\security\cis_studio_truststore.jks" -Djavax.net.ssl.trustStorePassword=changeit
REM #
REM #   2. Strong Encryption pack and strong TrustStore acquired from Cisco support.  Copy into PDTool \security directory.
REM #      CERT_ARGS=-Djavax.net.ssl.trustStore="%PROJECT_HOME_PHYSICAL%\security\cis_studio_truststore_strong.jks" -Djavax.net.ssl.trustStorePassword=changeit
REM #
REM #
set CERT_ARGS=
REM #
set JAVA_OPT=%MIN_MEMORY% %MAX_MEMORY% %CERT_ARGS%
REM #
REM #=======================================================================================================
REM # CREATE/MODIFY VARIABLES ABOVE THIS POINT
REM #=======================================================================================================
REM #
if not defined PRINT_VARS echo PRINT_VARS is not defined.  Set default PRINT_VARS=1
if not defined PRINT_VARS set PRINT_VARS=1
REM # Print out the setVars.bat variables
if "%PRINT_VARS%"=="1" call:writeOutput %0
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
set MSG=!MSG!%filename%: Setting PDToolStudio variables!LF!
set MSG=!MSG!########################################################################################################################################!LF!
set MSG=!MSG!MY_VARS_HOME              =%MY_VARS_HOME%!LF!
set MSG=!MSG!MY_PRE_VARS_PATH          =%MY_PRE_VARS_PATH%!LF!
set MSG=!MSG!MY_POST_VARS_PATH         =%MY_POST_VARS_PATH%!LF!
set MSG=!MSG!CONFIG_PROPERTY_FILE      =%CONFIG_PROPERTY_FILE%!LF!
set MSG=!MSG!JAVA_HOME                 =%JAVA_HOME%!LF!
set MSG=!MSG!CONFIG_PROPERTY_FILE      =%CONFIG_PROPERTY_FILE%!LF!
set MSG=!MSG!SUBSTITUTE_DRIVE          =%SUBSTITUTE_DRIVE%!LF!
set MSG=!MSG!NETWORK_DRIVE             =%NETWORK_DRIVE%!LF!
set MSG=!MSG!PROJECT_HOME              =%PROJECT_HOME%!LF!
set MSG=!MSG!PROJECT_HOME_PHYSICAL     =%PROJECT_HOME_PHYSICAL%!LF!
set MSG=!MSG!MIN_MEMORY                =%MIN_MEMORY%!LF!
set MSG=!MSG!MAX_MEMORY                =%MAX_MEMORY%!LF!
set MSG=!MSG!CERT_ARGS                 =%CERT_ARGS%!LF!
set MSG=!MSG!HTTP_PROXY                =%HTTP_PROXY%!LF!
set MSG=!MSG!JAVA_OPT                  =%JAVA_OPT%!LF!
echo.!MSG!
REM # Output to the default log file if the variable DEFAULT_LOG_PATH is defined in ExecutePDTool.bat
if not defined DEFAULT_LOG_PATH goto WRITEOUTPUTEND
if exist %DEFAULT_LOG_PATH% echo.!MSG!>>%DEFAULT_LOG_PATH%
:WRITEOUTPUTEND
ENDLOCAL
GOTO:EOF
REM #==========================================================
REM # END FUNCTIONS
REM #==========================================================
:END