@echo off
REM ############################################################################################################################
REM # (c) 2015 Cisco and/or its affiliates. All rights reserved.
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
REM # Author: Mike Tinius, Data Virtualization Business Unit, Advanced Services
REM # Date:   June 2015
REM # PDTool Regression Module - Migration Automated Test Framework
REM #=======================================================================================
REM # Instructions: setVars.bat 
REM #   
REM # Set environment variables
REM # 
REM ########################################################
REM BEGIN: 6.2 USER DEFINED VARIABLE SECTION
REM ########################################################
REM # Set environment variables for PDTool 6.2
REM # 
REM # Set the location of PDTool 6.2
set PDTOOL_INSTALL_HOME_6=..\..\..\
REM # List of valid Environments~Config property file name pairs.   Comma separated, no space and no double quotes.  Tilde separates pairs: ENV~ConfigFileName
REM # These are the property file names configured in the PDTool6.2\resources\config folder minus the .properties extension.
REM #  Example: DEV~deploy_NOVCS_DEV1,UAT~deploy_NOVCS_UAT1,PROD~deploy_NOVCS_PROD1
set VALID_ENV_CONFIG_PAIRS_6=
REM Set the release folders to indicate which version is being tested
REM   Release folder 1 is designated as the CIS instance current folder.  R1 designates it is a release 1 primary, current folder.
REM   Release folder 2 is designated as the CIS instance previous folder.  R2 designates it is a release 2 secondary, previous folder.
set RELEASE_FOLDER1_6=626R1
set RELEASE_FOLDER2_6=626R2
REM ########################################################
REM END: 6.2 USER DEFINED VARIABLE SECTION
REM ########################################################

REM ########################################################
REM BEGIN: 7.0 USER DEFINED VARIABLE SECTION
REM ########################################################
REM # Set environment variables for PDTool 7.0
REM # 
REM # Set the location of PDTool 7.0
set PDTOOL_INSTALL_HOME_7=..\..\..\
REM # List of valid Environments~Config property file name pairs.   Comma separated, no space and no double quotes.  Tilde separates pairs: ENV~ConfigFileName
REM # These are the property file names configured in the PDTool7.0.0\resources\config folder minus the .properties extension.
REM #  Example: DEV~deploy_NOVCS_DEV1,UAT~deploy_NOVCS_UAT1,PROD~deploy_NOVCS_PROD1
set VALID_ENV_CONFIG_PAIRS_7=
REM Set the release folders to indicate which version is being tested
REM   Release folder 1 is designated as the CIS instance current folder.  R1 designates it is a release 1 primary, current folder.
REM   Release folder 2 is designated as the CIS instance previous folder.  R2 designates it is a release 2 secondary, previous folder.
set RELEASE_FOLDER1_7=701R1
set RELEASE_FOLDER2_7=701R2
REM ########################################################
REM END: 7.0 USER DEFINED VARIABLE SECTION
REM ########################################################

REM ########################################################
REM BEGIN: COMMON USER DEFINED VARIABLE SECTION
REM ########################################################
REM # Set environment variables for Automated Test Framework
REM # 
REM # Automated Test Framework Home.  This folder may be independent of where PDTOOL_INSTALL_HOME is located.
set ATF_HOME=..\..\..\AutomatedTestFramework\regression
REM # Used by copyPlanTemplates.bat
REM # Set JAVA_HOME to JRE7
if not defined JAVA_HOME set JAVA_HOME=C:\Program Files\Java\jre7
REM # Used by copyPlanTemplates.bat
REM # Use one or the other or provide your own text editor path.  
REM #    If you have notepad++ it is a much better editor than notepad.
REM #    Do not put double quotes around path.  The script takes care of that.
set EDITOR=C:\Program Files (x86)\Notepad++\notepad++.exe
if not exist "%EDITOR%" set EDITOR=%windir%\system32\notepad.exe

REM # Script Main Activity
set SCRIPT_ACTIVITY=Execute Regression Test
REM # Debug=Y or N.  Default=N
set DEBUG=N
REM ########################################################
REM END: COMMON USER DEFINED VARIABLE SECTION
REM ########################################################
