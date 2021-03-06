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
REM #   e.g. set MY_VARS_HOME=c:\users\%USERNAME%\.compositesw\PDTool<ver>_<vcs> or set MY_VARS_HOME=c:\users\%USERNAME%\.compositesw\PDTool<ver>_<vcs>\bin
set MY_VARS_HOME=
REM #
if not defined MY_VARS_HOME goto MAIN
  set MY_PRE_VARS_PATH=%MY_VARS_HOME%\setMyPrePDToolVars.bat
  set MY_POST_VARS_PATH=%MY_VARS_HOME%\setMyPostPDToolVars.bat
  if not defined MY_PRE_VARS_PATH goto MAIN
  if exist "%MY_PRE_VARS_PATH%" call "%MY_PRE_VARS_PATH%"
:MAIN
REM #
REM # For Command-line execution - Set to JRE 1.6 or 1.7 Home Directory
set JAVA_HOME=%MY_JAVA_HOME%
REM #
REM # Default name of the configuration property file located in CisDeployTool/resources/config
REM #   Note: This property may be overwritten by using -config <prop_name.properties> on the command line
set CONFIG_PROPERTY_FILE=%MY_CONFIG_PROPERTY_FILE%
REM #
REM # -----------------------
REM # PDTool Substitute Drive
REM # -----------------------
REM # Derive PROJECT_HOME from a substituted drive letter in order to shorten the overall path in an attempt to avert the "too long file name" errors
REM #
REM # Set the substituted path variable such as S: for PDToolStudio or P: for PDTool
REM # Change this variable if you already have an S: or P: mapped.  It can be any unused drive letter.
REM # The actual subst command is performed in the ExecutePDTool batch file.
REM # CAUTION: Each instance of PDTool or PDToolStudio on a single host must use its own unique substitution letter and have its own workspace.
REM #          PDTool and PDToolStudio must NOT share the same workspace.
REM # DEPRECATED:
REM #    SUBSTITUTE_DRIVE remains here for backward compatibility in case there are issues assigning a network drive.
set SUBSTITUTE_DRIVE=
REM # Use network drive to permantly assign a drive letter to a PDTool installation folder.
REM #    Currently only C: or D: drive is supported for PDTool installation.  This requires the standard share C$ or D$ to be present.
REM #    Create command used: net use %NETWORK_DRIVE% "\\%COMPUTERNAME%\%PDTOOL_HOME_DRIVE_SHARE%\%PROJECT_HOME_PHYSICAL%" /PERSISTENT:YES
REM #    Delete command used: net use %NETWORK_DRIVE% /DELETE
set NETWORK_DRIVE=%PDTOOL_SUBSTITUTE_DRIVE%
REM #
REM # Set the PROJECT_HOME so that it points to the substituted path variable.  Use either the subtitute drive or network drive but not both.
set PROJECT_HOME=
if defined SUBSTITUTE_DRIVE set PROJECT_HOME=%SUBSTITUTE_DRIVE%
if defined NETWORK_DRIVE    set PROJECT_HOME=%NETWORK_DRIVE%
if not defined PROJECT_HOME set PROJECT_HOME=%PDTOOL_HOME%
REM #
REM # Remember the PROJECT_HOME_PHYSICAL as it points to PROJECT_HOME full path 
set PROJECT_HOME_PHYSICAL=%PDTOOL_HOME%
REM #
REM #----------------------------------------------------------
REM # Configure the Java Heap Min and Max memory
REM #----------------------------------------------------------
set MIN_MEMORY=-Xms256m
set MAX_MEMORY=-Xmx512m
REM #
REM # -----------------------
REM # PDTool Over SSL (https)
REM # -----------------------
REM # If PDTool over SSL using HTTPS is required, then configure CERT_ARGS with the following correct CIS_HOME and trustStorePassword:
REM #   1. Weak TrustStore ships with CIS and PDTool
REM #      CERT_ARGS=-Djavax.net.ssl.trustStore="%PROJECT_HOME_PHYSICAL%\security\cis_studio_truststore.jks" -Djavax.net.ssl.trustStorePassword=changeit
REM #
REM #   2. Strong Encryption pack and strong TrustStore acquired from TIBCO support.  Copy into PDTool \security directory.
REM #      CERT_ARGS=-Djavax.net.ssl.trustStore="%PROJECT_HOME_PHYSICAL%\security\cis_studio_truststore_strong.jks" -Djavax.net.ssl.trustStorePassword=changeit
REM #
set CERT_ARGS=-Djavax.net.ssl.trustStore="%PROJECT_HOME_PHYSICAL%\security\cis_studio_truststore_strong.jks" -Djavax.net.ssl.trustStorePassword=changeit
REM #
REM # -----------------------
REM # PDTool Proxy Settings
REM # -----------------------
REM # Set the HTTP proxy settings for PDTool.  Determine if a proxyUser and proxyPassword is required and set accordingly.
REM # set HTTP_PROXY=-DproxySet=true -Dhttp.proxyHost=wwwproxy.mydomain.com -Dhttp.proxyPort=80 -Dhttp.proxyUser=mydomain\myuser -Dhttp.proxyPassword=mypassword
set HTTP_PROXY=
REM #
REM # Set the Java Options for the JVM command line
set JAVA_OPT=%MIN_MEMORY% %MAX_MEMORY% %CERT_ARGS% %HTTP_PROXY%
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
set MSG=!MSG!MY_VARS_HOME               =!MY_VARS_HOME!!LF!
set MSG=!MSG!MY_PRE_VARS_PATH           =!MY_PRE_VARS_PATH!!LF!
set MSG=!MSG!MY_POST_VARS_PATH          =!MY_POST_VARS_PATH!!LF!
set MSG=!MSG!JAVA_HOME                  =!JAVA_HOME!!LF!
set MSG=!MSG!CONFIG_PROPERTY_FILE       =!CONFIG_PROPERTY_FILE!!LF!
set MSG=!MSG!SUBSTITUTE_DRIVE           =!SUBSTITUTE_DRIVE!!LF!
set MSG=!MSG!NETWORK_DRIVE              =!NETWORK_DRIVE!!LF!
set MSG=!MSG!PROJECT_HOME               =!PROJECT_HOME!!LF!
set MSG=!MSG!PROJECT_HOME_PHYSICAL      =!PROJECT_HOME_PHYSICAL!!LF!
set MSG=!MSG!MIN_MEMORY                 =!MIN_MEMORY!!LF!
set MSG=!MSG!MAX_MEMORY                 =!MAX_MEMORY!!LF!
set MSG=!MSG!CERT_ARGS                  =!CERT_ARGS!!LF!
set MSG=!MSG!HTTP_PROXY                 =!HTTP_PROXY!!LF!
set MSG=!MSG!JAVA_OPT                   =!JAVA_OPT!!LF!
echo.!MSG!
REM # Output to the default log file if the variable DEFAULT_LOG_PATH is defined in ExecutePDTool.bat
if not defined DEFAULT_LOG_PATH goto WRITEOUTPUTEND
if exist "%DEFAULT_LOG_PATH%" echo.!MSG!>>"%DEFAULT_LOG_PATH%"
:WRITEOUTPUTEND
ENDLOCAL
GOTO:EOF
REM #==========================================================
REM # END FUNCTIONS
REM #==========================================================
:END