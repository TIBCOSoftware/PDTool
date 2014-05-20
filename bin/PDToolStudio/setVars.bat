@echo off
REM ######################################################################
REM # (c) 2014 Cisco and/or its affiliates. All rights reserved.
REM ######################################################################
REM #==========================================================
REM # setVars.bat :: Set Environment Variables
REM #==========================================================
REM #----------------------------------------------------------
REM # USER MODIFIES ENVIRONMENT VARIABLES
REM #----------------------------------------------------------
REM #
REM # The My Vars path provides the user with the ability to set specific environment variables for their login
REM # e.g. set MY_VARS_PATH=c:\users\%USERNAME%\.compositesw\setMyPDToolStudioVars.bat
set MY_VARS_PATH=
if not defined MY_VARS_PATH goto CONTINUE
if not exist %MY_VARS_PATH% echo.Unknown path=%MY_VARS_PATH%
if exist %MY_VARS_PATH% echo.Invoking %MY_VARS_PATH%
if exist %MY_VARS_PATH% call %MY_VARS_PATH%
:CONTINUE
REM #
REM # Set to JRE 1.6 Home Directory
set JAVA_HOME=C:\Program Files\Java\jre6
REM #
REM # Configure the Java Heap Min and Max memory
set MIN_MEMORY=-Xms256m
set MAX_MEMORY=-Xmx512m
REM #
REM # Name of the configuration property file located in CisDeployTool/resources/config
set CONFIG_PROPERTY_FILE=studio.properties
REM #
REM # -----------------------
REM # PDTool Substitute Drive
REM # -----------------------
REM # Remember the current directory .../PDToolStudio/bin
set CURRDIR=%CD%
REM # Currently in PDToolStudio/bin directory so back up one level to .../PDToolStudio which is PROJECT_HOME
cd ..
REM # Remember the PROJECT_HOME_PHYSICAL as it points to PROJECT_HOME full path 
set PROJECT_HOME_PHYSICAL=%CD%
REM #
REM #=======================================
REM # Derive PROJECT_HOME from a substituted drive letter in order to shorten the overall path in an attempt to avert the "too long file name" errors
REM #=======================================
REM # Set the substituted path variable such as S: for PDToolStudio or P: for PDTool
REM # Change this variable if you already have an S: or P: mapped.  It can be any unused drive letter.
REM # The actual subst command is performed in the ExecutePDToolStudio batch file.
REM # CAUTION: Each instance of PDTool or PDToolStudio on a single host must use its own unique substitution letter and have its own workspace.
REM #          PDTool and PDToolStudio must NOT share the same workspace.       
set SUBSTITUTE_DRIVE=S:
REM # Set the PROJECT_HOME so that it points to the substituted path variable
set PROJECT_HOME=%SUBSTITUTE_DRIVE%
if not defined SUBSTITUTE_DRIVE ( 
  set PROJECT_HOME=%CD%
)
REM #
REM # Remember the PROJECT_DIR as it points to PROJECT_HOME full path 
set PROJECT_DIR=%CD%
REM # Mapping a network drive
REM -- set PROJECT_DIR=\\%COMPUTERNAME%\%CD%
REM #
cd %CURRDIR%
REM #
REM # -----------------------
REM # PDTool Over SSL (https)
REM # -----------------------
REM # If PDTool over SSL using HTTPS is required, then configure CERT_ARGS with the following correct CIS_HOME and trustStorePassword:
REM #   1. Weak TrustStore ships with CIS and PDTool
REM #      CERT_ARGS=-Djavax.net.ssl.trustStore="%PROJECT_HOME_PHYSICAL%\security\cis_studio_truststore.jks" -Djavax.net.ssl.trustStorePassword=changeit
REM #
REM #   2. Strong Encryption pack and strong TrustStore acquired from Cisco support.  Copy into PDTool \security directory.
REM #      CERT_ARGS=-Djavax.net.ssl.trustStore="%PROJECT_HOME_PHYSICAL%\security\cis_studio_truststore_strong.jks" -Djavax.net.ssl.trustStorePassword=changeit
REM #
set CERT_ARGS=
set JAVA_OPT=%MIN_MEMORY% %MAX_MEMORY% %CERT_ARGS%
