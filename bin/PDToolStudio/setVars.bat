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
echo.
echo ########################################################################################################################################
echo %0: Setting PDToolStudio variables
echo ########################################################################################################################################
REM #
REM #----------------------------------------------------------
REM # PDToolStudio CUSTOM MULTI-HOST ENVIRONMENT VARIABLES
REM #----------------------------------------------------------
REM # Name of the configuration property file located in PDToolStudio62/resources/config
set CONFIG_PROPERTY_FILE=studio.properties
REM #
REM # HOST Variables - 
REM # These are minimum set of variables needed to abstract the CONFIG_PROPERTY_FILE.
REM # Use these variables in the designated CONFIG_PROPERTY_FILE to achieve reusability of that file for different hosts.
set CONFIG_VCS_WORKSPACE_NAME=SVNsw
set CONFIG_VCS_REPOSITORY_URL=http:////%SVN_VCS_HOST%/svn/sandbox/PDTOOL/6_2
set CONFIG_VCS_PROJECT_ROOT=cis_objects
REM #
echo CONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE%
echo CONFIG_VCS_WORKSPACE_NAME=%CONFIG_VCS_WORKSPACE_NAME%
echo CONFIG_VCS_REPOSITORY_URL=%CONFIG_VCS_REPOSITORY_URL%
echo CONFIG_VCS_PROJECT_ROOT=%CONFIG_VCS_PROJECT_ROOT%
REM #
REM #----------------------------------------------------------
REM # PDToolStudio STANDARD ENVIRONMENT VARIABLES
REM #----------------------------------------------------------
REM #
REM # Set to JRE 1.6 Home Directory
set JAVA_HOME=C:\Program Files\Java\jre7
echo JAVA_HOME=%JAVA_HOME%
REM #
REM #----------------------------------------------------------
REM # PDTool Substitute Drive
REM #----------------------------------------------------------
REM # Remember the current directory .../PDToolStudio62/bin
set CURRDIR=%CD%
REM # Currently in PDToolStudio62/bin directory so back up one level to ../PDToolStudio62 which is PROJECT_HOME
cd ..
REM # Remember the PROJECT_HOME_PHYSICAL as it points to PROJECT_HOME full path 
set PROJECT_HOME_PHYSICAL=%CD%
echo PROJECT_HOME_PHYSICAL=%PROJECT_HOME_PHYSICAL%
REM #
REM #=======================================
REM # Derive PROJECT_HOME from a substituted drive letter in order to shorten the overall path in an attempt to avert the "too long file name" errors
REM #=======================================
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
set SUBSTITUTE_DRIVE=S:
echo SUBSTITUTE_DRIVE=%SUBSTITUTE_DRIVE%
REM # Set the PROJECT_HOME so that it points to the substituted path variable
set PROJECT_HOME=%SUBSTITUTE_DRIVE%
if not defined SUBSTITUTE_DRIVE ( 
  set PROJECT_HOME=%CD%
)
echo PROJECT_HOME=%PROJECT_HOME%
REM #
REM # Remember the PROJECT_DIR as it points to PROJECT_HOME full path 
set PROJECT_DIR=%CD%
echo PROJECT_DIR=%PROJECT_DIR%
REM # Mapping a network drive
REM -- set PROJECT_DIR=\\%COMPUTERNAME%\%CD%
REM #
cd %CURRDIR%
REM #
REM #----------------------------------------------------------
REM # Configure the Java Heap Min and Max memory
REM #----------------------------------------------------------
set MIN_MEMORY=-Xms256m
set MAX_MEMORY=-Xmx512m
echo MIN_MEMORY=%MIN_MEMORY%
echo MAX_MEMORY=%MAX_MEMORY%
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
echo CERT_ARGS=%CERT_ARGS%
REM #
set JAVA_OPT=%MIN_MEMORY% %MAX_MEMORY% %CERT_ARGS%
echo JAVA_OPT=%JAVA_OPT%
