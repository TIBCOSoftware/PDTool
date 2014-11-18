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
REM #----------------------------------------------------------
REM # USER MODIFIES ENVIRONMENT VARIABLES
REM #-----------------------------------------------------------
REM #
REM # The My Vars path provides with the user the ability to set specific environment variables for their login
REM # e.g. set MY_VARS_PATH=c:\users\%USERNAME%\.compositesw\setMyPDToolVars.bat
set MY_VARS_PATH=E:\dev\Workspaces\PDToolGitTest\PDTool\bin\setMyVars.bat
if not defined MY_VARS_PATH goto CONTINUE
if not exist %MY_VARS_PATH% echo.Unknown path=%MY_VARS_PATH%
if exist %MY_VARS_PATH% echo.Invoking %MY_VARS_PATH%
if exist %MY_VARS_PATH% call %MY_VARS_PATH%
:CONTINUE
REM #
REM # For Command-line execution - Set to JRE 1.6 or 1.7 Home Directory
set JAVA_HOME=C:\Program Files\Java\jre7
REM #
REM # Configure the Java Heap Min and Max memory
set MIN_MEMORY=-Xms256m
set MAX_MEMORY=-Xmx1024m
REM #
REM # Default name of the configuration property file located in CisDeployTool/resources/config
REM #   Note: This property may be overwritten by using -config <prop_name.properties> on the command line
set CONFIG_PROPERTY_FILE=deploy.properties
REM #
REM # -----------------------
REM # PDTool Substitute Drive
REM # -----------------------
REM # Remember the current directory .../PDTool/bin
set CURRDIR=%CD%
REM # Currently in PDTool/bin directory so back up one level to .../PDTool which is PROJECT_HOME
cd ..
REM #=======================================
REM # Derive PROJECT_HOME from a substituted drive letter in order to shorten the overall path in an attempt to avert the "too long file name" errors
REM #=======================================
REM # Set the substituted path variable such as S: for PDToolStudio or P: for PDTool
REM # Change this variable if you already have an S: or P: mapped.  It can be any unused drive letter.
REM # The actual subst command is performed in the ExecutePDTool batch file.
REM # CAUTION: Each instance of PDTool or PDToolStudio on a single host must use its own unique substitution letter and have its own workspace.
REM #          PDTool and PDToolStudio must NOT share the same workspace.     
set SUBSTITUTE_DRIVE=P:
REM #
REM # Set the PROJECT_HOME so that it points to the substituted path variable
set PROJECT_HOME=%SUBSTITUTE_DRIVE%
if not defined SUBSTITUTE_DRIVE ( 
  set PROJECT_HOME=%CD%
)
REM # If substitute drives are not allowed then set to the actual path and comment out both 'set SUBSTITUTE_DRIVE' and 'set PROJECT_HOME=%SUBSTITUTE_DRIVE%
REM set PROJECT_HOME=%CD%
REM #
REM # Remember the PROJECT_HOME_PHYSICAL as it points to PROJECT_HOME full path 
set PROJECT_HOME_PHYSICAL=%CD%
REM # Mapping a network drive
REM -- set PROJECT_HOME_PHYSICAL=\\%COMPUTERNAME%\%CD%
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
set CERT_ARGS=-Djavax.net.ssl.trustStore="%PROJECT_HOME_PHYSICAL%\security\cis_studio_truststore.jks" -Djavax.net.ssl.trustStorePassword=changeit
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
