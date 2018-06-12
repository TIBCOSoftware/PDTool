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
REM # setMyPrePDToolVars.bat :: Set Environment Variables
REM #==========================================================
REM # This file gets invoked by setVars.bat before setVars.bat variables have been executed.
REM #
REM # Instructions: 
REM #    1. Modify variables as needed.
REM #    2. Add new variables to the function :writeOutput at the bottom of this batch file when new variables are added.
REM #    3. Copy this file to a location outside of the PDToolStudio installation so that it is not overwritten during upgrade.
REM #    4. Modify setVars.bat "MY_VARS_HOME" variable to point to the directory that contains this file.
REM #    5. To encrypt the password in this file:
REM #       a) Open a windows command line
REM #       b) cd <path-to-pdtool>\PDTool62\bin
REM #       c) ExecutePDTool.bat -encrypt <path-to-file>\setMyPrePDToolVars.bat
REM #=======================================================================================================
REM # CREATE/MODIFY CUSTOM PRE-PROCESSING VARIABLES BELOW THIS POINT
REM #=======================================================================================================
REM #
REM ################################
REM # GENERAL GROUP ENVIRONMENT
REM ################################
REM # 0=Do not print this section, 1 or true=Print this section
set GEN_PRINT=1
REM # My Java Home
set MY_JAVA_HOME=
REM # PDTool Studio Substitute Drive Letter used for making the path shorter
REM # PDTool and PDTool Studio must use different drive letters.
set PDTOOL_SUBSTITUTE_DRIVE=
REM # PDTool Installation Home Directory
set PDTOOL_INSTALL_HOME=
REM # PDTool Home directory
set PDTOOL_HOME=
REM # Name of the configuration property file located in PDToolStudio62/resources/config
REM #    e.g. Default=studio.properties or SVN=studio_SVN.properties or TFS=studio_TFS.properties
set MY_CONFIG_PROPERTY_FILE=
REM # The editor to use for VCS viewing if needed.  Windows=notepad and UNIX=vi
set VCS_EDITOR=
REM #
REM # NOVCS ENVIRONMENT~CONFIG PROPERTY PAIRS
REM # These pairs provide the ability to use a short environment name in place of the configuration property name when instructing PDTool on which environment to deploy to.
REM #    Format: [vcs-type-char][env-type]~[config-name]   Example: NDEV~deploy_NOVCS_DEV,NUAT~deploy_NOVCS_UAT,NPROD~deploy_NOVCS_PROD
REM #            Comma separated, no space and no double quotes.  Tilde separates pairs: ENV_TYPE~ConfigFileName. The .properties extension is not included and gets added automatically.
REM #            Format of pairs: XDEV=deploy_DEV,XUAT=deploy_UAT,XPROD=deploy_PROD
REM #               vcs-type-char = represents the first characters of the VCS environment. N=NOVCS,S=SVN,T=TFS,G=GIT,P=P4,C=CVS
REM #                    env-type = represents the CIS environment short name deploying to.  Examples: DEV, ATA, ADA, CIT, SIT, UAT, TT, PROD, DR
REM #               The combination of [vcs-type-char][env-type] makes it unique across all different env-config pair types.
REM #    Configuration property file names found in PDTool\resources\config folder.
REM #    It is up to the PDTool installer to modify these pairs to match your environment.
set NOVCS_VALID_ENV_CONFIG_PAIRS=
REM #
REM ################################
REM # CUSTOM USER DEFINED VARIABLES
REM ################################
REM # if exist "%PDTOOL_HOME%\bin\setCustomVars.bat" call "%PDTOOL_HOME%\bin\setCustomVars.bat"
REM #
REM ################################
REM # DV SERVER VARIABLES
REM ################################
REM # 0=Do not print this section, 1 or true=Print this section
set CIS_PRINT=1
REM # DV Server Username
set CIS_USERNAME=
REM # DV Server Password
set CIS_PASSWORD=
REM # DV Server Domain
set CIS_DOMAIN=
REM # Check to see if the ExecutePDToolRelease has set the CIS REL_ variables
REM # This allows the same CIS_ variables to be used in the servers.xml file.
if defined REL_CIS_USERNAME set CIS_USERNAME=%REL_CIS_USERNAME%
if defined REL_CIS_PASSWORD set CIS_PASSWORD=%REL_CIS_PASSWORD%
if defined REL_CIS_DOMAIN   set CIS_DOMAIN=%REL_CIS_DOMAIN%
REM #
REM ################################
REM # TFS VARIABLES
REM ################################
REM # 0=Do not print this section, 1 or true=Print this section
set TFS_PRINT=0
if "%REL_VCS_TYPE%"=="TFS" set TFS_PRINT=1
REM # The TFS Home folder where VCS client exists
set TFS_HOME=
REM # The TFS repository URL pointing to the repository's collection
set TFS_VCS_REPOSITORY_URL=
REM # The TFS folder path starting at the TFS project and ending where the DV base level root folders start
set TFS_VCS_PROJECT_ROOT=
REM # TFS user name including the domain.  If LDAP it may need to include the domain user@domain.
set TFS_VCS_USERNAME=
REM # TFS user password.  See notes at top of this file to encrypt.
set TFS_VCS_PASSWORD=
REM # Set the workspace home if applicable.  This variable points to the file system location parent folder to VCS_WORKSPACE_NAME
REM # e.g. If the workspace is in the PDTOOL_HOME then use the shortest path: %PDTOOL_SUBSTITUTE_HOME%
set TFS_VCS_WORKSPACE_HOME=
REM # Set the VCS Workspace name.  e.g. TFSww7
set TFS_VCS_WORKSPACE_NAME=
REM # Set the VCS TEMP directory by adding a "t" to the end of the concatenated workspace home and workspace name
REM # e.g. %TFS_VCS_WORKSPACE_HOME%\%TFS_VCS_WORKSPACE_NAME%t
set TFS_VCS_TEMP_DIR=
REM #
REM # TFS ENVIRONMENT-CONFIG PROPERTY PAIRS
REM # These pairs provide the ability to use a short environment name in place of the configuration property name when instructing PDTool on which environment to deploy to.
REM #    Format: [vcs-type-char][env-type]~[config-name]   Example: TDEV~deploy_TFS_DEV,TUAT~deploy_TFS_UAT,TPROD~deploy_TFS_PROD
REM #            Comma separated, no space and no double quotes.  Tilde separates pairs: ENV_TYPE~ConfigFileName. The .properties extension is not included and gets added automatically.
REM #            Format of pairs: XDEV=deploy_DEV,XUAT=deploy_UAT,XPROD=deploy_PROD
REM #               vcs-type-char = represents the first characters of the VCS environment. N=NOVCS,S=SVN,T=TFS,G=GIT,P=P4,C=CVS
REM #                    env-type = represents the CIS environment short name deploying to.  Examples: DEV, ATA, ADA, CIT, SIT, UAT, TT, PROD, DR
REM #               The combination of [vcs-type-char][env-type] makes it unique across all different env-config pair types.
REM #    Configuration property file names found in PDTool\resources\config folder.
REM #    It is up to the PDTool installer to modify these pairs to match your environment.
set TFS_VALID_ENV_CONFIG_PAIRS=
REM #
REM # ExecutePDToolRelease.bat OVERRIDE SECTION
REM #
REM # Check the release VCS repository URL is set from ExecutePDToolRelease.bat
if defined REL_VCS_REPOSITORY_URL     set TFS_VCS_REPOSITORY_URL=%REL_VCS_REPOSITORY_URL%
REM # Check the release VCS project root is set from ExecutePDToolRelease.bat
if defined REL_VCS_PROJECT_ROOT       set TFS_VCS_PROJECT_ROOT=%REL_VCS_PROJECT_ROOT%
REM # Check the release VCS username is set from ExecutePDToolRelease.bat
if defined REL_VCS_USERNAME           set TFS_VCS_USERNAME=%REL_VCS_USERNAME%
REM # Check the release VCS user password is set from ExecutePDToolRelease.bat
if defined REL_VCS_PASSWORD           set TFS_VCS_PASSWORD=%REL_VCS_PASSWORD%
REM # Check the release VCS workspace home is set from ExecutePDToolRelease.bat
if defined REL_VCS_WORKSPACE_HOME     set TFS_VCS_WORKSPACE_HOME=%REL_VCS_WORKSPACE_HOME%
REM # Check the release VCS workspace name is set from ExecutePDToolRelease.bat
if defined REL_VCS_WORKSPACE_NAME     set TFS_VCS_WORKSPACE_NAME=%REL_VCS_WORKSPACE_NAME%
REM # Check the release VCS temporary directory is set from ExecutePDToolRelease.bat
if defined REL_VCS_TEMP_DIR           set TFS_VCS_TEMP_DIR=%REL_VCS_TEMP_DIR%
REM # Check the valid environment configuration pairs is set from ExecutePDToolRelease.bat
if defined REL_VALID_ENV_CONFIG_PAIRS set TFS_VALID_ENV_CONFIG_PAIRS=%REL_VALID_ENV_CONFIG_PAIRS%
REM #
REM ################################
REM # SVN VARIABLES
REM ################################
REM # 0=Do not print this section, 1 or true=Print this section
set SVN_PRINT=0
if "%REL_VCS_TYPE%"=="SVN" set SVN_PRINT=1
REM # The SVN Home folder where VCS client exists
set SVN_HOME=
REM # The subversion repository path at trunk or any folder designation within trunk
set SVN_VCS_REPOSITORY_URL=
REM # The Subversion folder path starting directly after the Subversion repo URL and ending where the DV base level root folders start
set SVN_VCS_PROJECT_ROOT=
REM # Subversion user name
set SVN_VCS_USERNAME=
REM # Subversion user password.  See notes at top of this file to encrypt.
set SVN_VCS_PASSWORD=
REM # Set the workspace home if applicable.  This variable points to the file system location parent folder to VCS_WORKSPACE_NAME
REM # e.g. If the workspace is in the PDTOOL_HOME then use the shortest path: %PDTOOL_SUBSTITUTE_HOME%
set SVN_VCS_WORKSPACE_HOME=
REM # Set the VCS Workspace name.  e.g. SVNww7
set SVN_VCS_WORKSPACE_NAME=
REM # Set the VCS TEMP directory by adding a "t" to the end of the concatenated workspace home and workspace name
REM # e.g. %SVN_VCS_WORKSPACE_HOME%\%SVN_VCS_WORKSPACE_NAME%t
set SVN_VCS_TEMP_DIR=
REM #
REM # SVN ENVIRONMENT-CONFIG PROPERTY PAIRS
REM # These pairs provide the ability to use a short environment name in place of the configuration property name when instructing PDTool on which environment to deploy to.
REM #    Format: [vcs-type-char][env-type]~[config-name]   Example: SDEV~deploy_SVN_DEV,SUAT~deploy_SVN_UAT,SPROD~deploy_SVN_PROD
REM #            Comma separated, no space and no double quotes.  Tilde separates pairs: ENV_TYPE~ConfigFileName. The .properties extension is not included and gets added automatically.
REM #            Format of pairs: XDEV=deploy_DEV,XUAT=deploy_UAT,XPROD=deploy_PROD
REM #               vcs-type-char = represents the first characters of the VCS environment. N=NOVCS,S=SVN,T=TFS,G=GIT,P=P4,C=CVS
REM #                    env-type = represents the CIS environment short name deploying to.  Examples: DEV, ATA, ADA, CIT, SIT, UAT, TT, PROD, DR
REM #               The combination of [vcs-type-char][env-type] makes it unique across all different env-config pair types.
REM #    Configuration property file names found in PDTool\resources\config folder.
REM #    It is up to the PDTool installer to modify these pairs to match your environment.
set SVN_VALID_ENV_CONFIG_PAIRS=
REM #
REM # ExecutePDToolRelease.bat OVERRIDE SECTION
REM #
REM # Check the release VCS repository URL is set from ExecutePDToolRelease.bat
if defined REL_VCS_REPOSITORY_URL     set SVN_VCS_REPOSITORY_URL=%REL_VCS_REPOSITORY_URL%
REM # Check the release VCS project root is set from ExecutePDToolRelease.bat
if defined REL_VCS_PROJECT_ROOT       set SVN_VCS_PROJECT_ROOT=%REL_VCS_PROJECT_ROOT%
REM # Check the release VCS username is set from ExecutePDToolRelease.bat
if defined REL_VCS_USERNAME           set SVN_VCS_USERNAME=%REL_VCS_USERNAME%
REM # Check the release VCS user password is set from ExecutePDToolRelease.bat
if defined REL_VCS_PASSWORD           set SVN_VCS_PASSWORD=%REL_VCS_PASSWORD%
REM # Check the release VCS workspace home is set from ExecutePDToolRelease.bat
if defined REL_VCS_WORKSPACE_HOME     set SVN_VCS_WORKSPACE_HOME=%REL_VCS_WORKSPACE_HOME%
REM # Check the release VCS workspace name is set from ExecutePDToolRelease.bat
if defined REL_VCS_WORKSPACE_NAME     set SVN_VCS_WORKSPACE_NAME=%REL_VCS_WORKSPACE_NAME%
REM # Check the release VCS temporary directory is set from ExecutePDToolRelease.bat
if defined REL_VCS_TEMP_DIR           set SVN_VCS_TEMP_DIR=%REL_VCS_TEMP_DIR%
REM # Check the valid environment configuration pairs is set from ExecutePDToolRelease.bat
if defined REL_VALID_ENV_CONFIG_PAIRS set SVN_VALID_ENV_CONFIG_PAIRS=%REL_VALID_ENV_CONFIG_PAIRS%
REM #
REM ################################
REM # GIT VARIABLES
REM ################################
REM # 0=Do not print this section, 1 or true=Print this section
set GIT_PRINT=0
if "%REL_VCS_TYPE%"=="GIT" set GIT_PRINT=1
REM # The GIT Home folder where VCS client exists
set GIT_HOME=
REM # The Git repository path at trunk or any folder designation within trunk
set GIT_VCS_REPOSITORY_URL=
REM # The Git folder path starting directly after the Git repo URL and ending where the DV base level root folders start
set GIT_VCS_PROJECT_ROOT=
REM # Git user name
set GIT_VCS_USERNAME=
REM # Git user password.  See notes at top of this file to encrypt.
set GIT_VCS_PASSWORD=
REM # Set the workspace home if applicable.  This variable points to the file system location parent folder to VCS_WORKSPACE_NAME
REM # e.g. If the workspace is in the PDTOOL_HOME then use the shortest path: %PDTOOL_SUBSTITUTE_HOME%
set GIT_VCS_WORKSPACE_HOME=
REM # Set the VCS Workspace name.  e.g. GITww7
set GIT_VCS_WORKSPACE_NAME=
REM # Set the VCS TEMP directory by adding a "t" to the end of the concatenated workspace home and workspace name
REM # e.g. %GIT_VCS_WORKSPACE_HOME%\%GIT_VCS_WORKSPACE_NAME%t
set GIT_VCS_TEMP_DIR=
REM #
REM # GIT ENVIRONMENT-CONFIG PROPERTY PAIRS
REM # These pairs provide the ability to use a short environment name in place of the configuration property name when instructing PDTool on which environment to deploy to.
REM #    Format: [vcs-type-char][env-type]~[config-name]   Example: GDEV~deploy_GIT_DEV,GUAT~deploy_GIT_UAT,GPROD~deploy_GIT_PROD
REM #            Comma separated, no space and no double quotes.  Tilde separates pairs: ENV_TYPE~ConfigFileName. The .properties extension is not included and gets added automatically.
REM #            Format of pairs: XDEV=deploy_DEV,XUAT=deploy_UAT,XPROD=deploy_PROD
REM #               vcs-type-char = represents the first characters of the VCS environment. N=NOVCS,S=SVN,T=TFS,G=GIT,P=P4,C=CVS
REM #                    env-type = represents the CIS environment short name deploying to.  Examples: DEV, ATA, ADA, CIT, SIT, UAT, TT, PROD, DR
REM #               The combination of [vcs-type-char][env-type] makes it unique across all different env-config pair types.
REM #    Configuration property file names found in PDTool\resources\config folder.
REM #    It is up to the PDTool installer to modify these pairs to match your environment.
set GIT_VALID_ENV_CONFIG_PAIRS=
REM #
REM # ExecutePDToolRelease.bat OVERRIDE SECTION
REM #
REM # Check the release VCS repository URL is set from ExecutePDToolRelease.bat
if defined REL_VCS_REPOSITORY_URL     set GIT_VCS_REPOSITORY_URL=%REL_VCS_REPOSITORY_URL%
REM # Check the release VCS project root is set from ExecutePDToolRelease.bat
if defined REL_VCS_PROJECT_ROOT       set GIT_VCS_PROJECT_ROOT=%REL_VCS_PROJECT_ROOT%
REM # Check the release VCS username is set from ExecutePDToolRelease.bat
if defined REL_VCS_USERNAME           set GIT_VCS_USERNAME=%REL_VCS_USERNAME%
REM # Check the release VCS user password is set from ExecutePDToolRelease.bat
if defined REL_VCS_PASSWORD           set GIT_VCS_PASSWORD=%REL_VCS_PASSWORD%
REM # Check the release VCS workspace home is set from ExecutePDToolRelease.bat
if defined REL_VCS_WORKSPACE_HOME     set GIT_VCS_WORKSPACE_HOME=%REL_VCS_WORKSPACE_HOME%
REM # Check the release VCS workspace name is set from ExecutePDToolRelease.bat
if defined REL_VCS_WORKSPACE_NAME     set GIT_VCS_WORKSPACE_NAME=%REL_VCS_WORKSPACE_NAME%
REM # Check the release VCS temporary directory is set from ExecutePDToolRelease.bat
if defined REL_VCS_TEMP_DIR           set GIT_VCS_TEMP_DIR=%REL_VCS_TEMP_DIR%
REM # Check the valid environment configuration pairs is set from ExecutePDToolRelease.bat
if defined REL_VALID_ENV_CONFIG_PAIRS set GIT_VALID_ENV_CONFIG_PAIRS=%REL_VALID_ENV_CONFIG_PAIRS%
REM #
REM ################################
REM # P4 VARIABLES
REM ################################
REM # 0=Do not print this section, 1 or true=Print this section
set P4_PRINT=0
if "%REL_VCS_TYPE%"=="P4" set P4_PRINT=1
REM # The Perforce Home folder where VCS client exists
set P4_HOME=
REM # The subversion repository path at trunk or any folder designation within trunk
set P4_VCS_REPOSITORY_URL=
REM # The Subversion folder path starting directly after the Subversion repo URL and ending where the DV base level root folders start
set P4_VCS_PROJECT_ROOT=
REM # Subversion user name
set P4_VCS_USERNAME=
REM # Subversion user password.  See notes at top of this file to encrypt.
set P4_VCS_PASSWORD=
REM # Set the workspace home if applicable.  This variable points to the file system location parent folder to VCS_WORKSPACE_NAME
REM # e.g. If the workspace is in the PDTOOL_HOME then use the shortest path: %PDTOOL_SUBSTITUTE_HOME%
set P4_VCS_WORKSPACE_HOME=
REM # Set the VCS Workspace name.  e.g. P4ww7
set P4_VCS_WORKSPACE_NAME=
REM # Set the VCS TEMP directory by adding a "t" to the end of the concatenated workspace home and workspace name
REM # e.g. %P4_VCS_WORKSPACE_HOME%\%P4_VCS_WORKSPACE_NAME%t
set P4_VCS_TEMP_DIR=
REM #
REM # P4 ENVIRONMENT-CONFIG PROPERTY PAIRS
REM # These pairs provide the ability to use a short environment name in place of the configuration property name when instructing PDTool on which environment to deploy to.
REM #    Format: [vcs-type-char][env-type]~[config-name]   Example: PDEV~deploy_P4_DEV,PUAT~deploy_P4_UAT,PPROD~deploy_P4_PROD
REM #            Comma separated, no space and no double quotes.  Tilde separates pairs: ENV_TYPE~ConfigFileName. The .properties extension is not included and gets added automatically.
REM #            Format of pairs: XDEV=deploy_DEV,XUAT=deploy_UAT,XPROD=deploy_PROD
REM #               vcs-type-char = represents the first characters of the VCS environment. N=NOVCS,S=SVN,T=TFS,G=GIT,P=P4,C=CVS
REM #                    env-type = represents the CIS environment short name deploying to.  Examples: DEV, ATA, ADA, CIT, SIT, UAT, TT, PROD, DR
REM #               The combination of [vcs-type-char][env-type] makes it unique across all different env-config pair types.
REM #    Configuration property file names found in PDTool\resources\config folder.
REM #    It is up to the PDTool installer to modify these pairs to match your environment.
set P4_VALID_ENV_CONFIG_PAIRS=
REM #
REM # ExecutePDToolRelease.bat OVERRIDE SECTION
REM #
REM # Check the release VCS repository URL is set from ExecutePDToolRelease.bat
if defined REL_VCS_REPOSITORY_URL     set P4_VCS_REPOSITORY_URL=%REL_VCS_REPOSITORY_URL%
REM # Check the release VCS project root is set from ExecutePDToolRelease.bat
if defined REL_VCS_PROJECT_ROOT       set P4_VCS_PROJECT_ROOT=%REL_VCS_PROJECT_ROOT%
REM # Check the release VCS username is set from ExecutePDToolRelease.bat
if defined REL_VCS_USERNAME           set P4_VCS_USERNAME=%REL_VCS_USERNAME%
REM # Check the release VCS user password is set from ExecutePDToolRelease.bat
if defined REL_VCS_PASSWORD           set P4_VCS_PASSWORD=%REL_VCS_PASSWORD%
REM # Check the release VCS workspace home is set from ExecutePDToolRelease.bat
if defined REL_VCS_WORKSPACE_HOME     set P4_VCS_WORKSPACE_HOME=%REL_VCS_WORKSPACE_HOME%
REM # Check the release VCS workspace name is set from ExecutePDToolRelease.bat
if defined REL_VCS_WORKSPACE_NAME     set P4_VCS_WORKSPACE_NAME=%REL_VCS_WORKSPACE_NAME%
REM # Check the release VCS temporary directory is set from ExecutePDToolRelease.bat
if defined REL_VCS_TEMP_DIR           set P4_VCS_TEMP_DIR=%REL_VCS_TEMP_DIR%
REM # Check the valid environment configuration pairs is set from ExecutePDToolRelease.bat
if defined REL_VALID_ENV_CONFIG_PAIRS set P4_VALID_ENV_CONFIG_PAIRS=%REL_VALID_ENV_CONFIG_PAIRS%
REM #
REM ################################
REM # CVS VARIABLES
REM ################################
REM # 0=Do not print this section, 1 or true=Print this section
set CVS_PRINT=0
if "%REL_VCS_TYPE%"=="CVS" set CVS_PRINT=1
REM # The Perforce Home folder where VCS client exists
set CVS_HOME=
REM # The subversion repository path at trunk or any folder designation within trunk
set CVS_VCS_REPOSITORY_URL=
REM # The Subversion folder path starting directly after the Subversion repo URL and ending where the DV base level root folders start
set CVS_VCS_PROJECT_ROOT=
REM # Subversion user name
set CVS_VCS_USERNAME=
REM # Subversion user password.  See notes at top of this file to encrypt.
set CVS_VCS_PASSWORD=
REM # Set the workspace home if applicable.  This variable points to the file system location parent folder to VCS_WORKSPACE_NAME
REM # e.g. If the workspace is in the PDTOOL_HOME then use the shortest path: %PDTOOL_SUBSTITUTE_HOME%
set CVS_VCS_WORKSPACE_HOME=
REM # Set the VCS Workspace name.  e.g. CVSww7
set CVS_VCS_WORKSPACE_NAME=
REM # Set the VCS TEMP directory by adding a "t" to the end of the concatenated workspace home and workspace name
REM # e.g. %CVS_VCS_WORKSPACE_HOME%\%CVS_VCS_WORKSPACE_NAME%t
set CVS_VCS_TEMP_DIR=
REM #
REM # CVS ENVIRONMENT-CONFIG PROPERTY PAIRS
REM # These pairs provide the ability to use a short environment name in place of the configuration property name when instructing PDTool on which environment to deploy to.
REM #    Format: [vcs-type-char][env-type]~[config-name]   Example: CDEV~deploy_CVS_DEV,CUAT~deploy_CVS_UAT,CPROD~deploy_CVS_PROD
REM #            Comma separated, no space and no double quotes.  Tilde separates pairs: ENV_TYPE~ConfigFileName. The .properties extension is not included and gets added automatically.
REM #            Format of pairs: XDEV=deploy_DEV,XUAT=deploy_UAT,XPROD=deploy_PROD
REM #               vcs-type-char = represents the first characters of the VCS environment. N=NOVCS,S=SVN,T=TFS,G=GIT,P=P4,C=CVS
REM #                    env-type = represents the CIS environment short name deploying to.  Examples: DEV, ATA, ADA, CIT, SIT, UAT, TT, PROD, DR
REM #               The combination of [vcs-type-char][env-type] makes it unique across all different env-config pair types.
REM #    Configuration property file names found in PDTool\resources\config folder.
REM #    It is up to the PDTool installer to modify these pairs to match your environment.
set CVS_VALID_ENV_CONFIG_PAIRS=
REM #
REM # ExecutePDToolRelease.bat OVERRIDE SECTION
REM #
REM # Check the release VCS repository URL is set from ExecutePDToolRelease.bat
if defined REL_VCS_REPOSITORY_URL     set CVS_VCS_REPOSITORY_URL=%REL_VCS_REPOSITORY_URL%
REM # Check the release VCS project root is set from ExecutePDToolRelease.bat
if defined REL_VCS_PROJECT_ROOT       set CVS_VCS_PROJECT_ROOT=%REL_VCS_PROJECT_ROOT%
REM # Check the release VCS username is set from ExecutePDToolRelease.bat
if defined REL_VCS_USERNAME           set CVS_VCS_USERNAME=%REL_VCS_USERNAME%
REM # Check the release VCS user password is set from ExecutePDToolRelease.bat
if defined REL_VCS_PASSWORD           set CVS_VCS_PASSWORD=%REL_VCS_PASSWORD%
REM # Check the release VCS workspace home is set from ExecutePDToolRelease.bat
if defined REL_VCS_WORKSPACE_HOME     set CVS_VCS_WORKSPACE_HOME=%REL_VCS_WORKSPACE_HOME%
REM # Check the release VCS workspace name is set from ExecutePDToolRelease.bat
if defined REL_VCS_WORKSPACE_NAME     set CVS_VCS_WORKSPACE_NAME=%REL_VCS_WORKSPACE_NAME%
REM # Check the release VCS temporary directory is set from ExecutePDToolRelease.bat
if defined REL_VCS_TEMP_DIR           set CVS_VCS_TEMP_DIR=%REL_VCS_TEMP_DIR%
REM # Check the valid environment configuration pairs is set from ExecutePDToolRelease.bat
if defined REL_VALID_ENV_CONFIG_PAIRS set CVS_VALID_ENV_CONFIG_PAIRS=%REL_VALID_ENV_CONFIG_PAIRS%
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
set PR_PASSWORD=
REM # Convert true to 1
if "%GEN_PRINT%"=="true" set GEN_PRINT=1
if "%CIS_PRINT%"=="true" set CIS_PRINT=1
if "%TFS_PRINT%"=="true" set TFS_PRINT=1
if "%SVN_PRINT%"=="true" set SVN_PRINT=1
if "%GIT_PRINT%"=="true" set GIT_PRINT=1
if "%P4_PRINT%"=="true"  set P4_PRINT=1
if "%CVS_PRINT%"=="true" set CVS_PRINT=1
SETLOCAL ENABLEDELAYEDEXPANSION
REM # LF must have 2 blank lines following it to create a line feed
set LF=^


set MSG=!MSG!########################################################################################################################################!LF!
set MSG=!MSG!%filename%: Setting pre-processing custom variables!LF!
set MSG=!MSG!########################################################################################################################################!LF!
if "%GEN_PRINT%"=="1" (
set MSG=!MSG!------------------!LF!
set MSG=!MSG!GENERAL VARIABLES!LF!
set MSG=!MSG!------------------!LF!
set MSG=!MSG!MY_JAVA_HOME                =!MY_JAVA_HOME!!LF!
set MSG=!MSG!PDTOOL_SUBSTITUTE_DRIVE     =!PDTOOL_SUBSTITUTE_DRIVE!!LF!
set MSG=!MSG!PDTOOL_INSTALL_HOME         =!PDTOOL_INSTALL_HOME!!LF!
set MSG=!MSG!PDTOOL_HOME                 =!PDTOOL_HOME!!LF!
set MSG=!MSG!MY_CONFIG_PROPERTY_FILE     =!MY_CONFIG_PROPERTY_FILE!!LF!
set MSG=!MSG!VCS_EDITOR                  =!VCS_EDITOR!!LF!
set MSG=!MSG!NOVCS_VALID_ENV_CONFIG_PAIRS=!NOVCS_VALID_ENV_CONFIG_PAIRS!!LF!
)
if "%CIS_PRINT%"=="1" (
set MSG=!MSG!------------------!LF!
set MSG=!MSG!CIS VARIABLES!LF!
set MSG=!MSG!------------------!LF!
set MSG=!MSG!CIS_USERNAME                =!CIS_USERNAME!!LF!
CALL:printablePassword "%CIS_PASSWORD%" PR_PASSWORD
set MSG=!MSG!CIS_PASSWORD                =!PR_PASSWORD!!LF!
set MSG=!MSG!CIS_DOMAIN                  =!CIS_DOMAIN!!LF!
)
if "%TFS_PRINT%"=="1" (
set MSG=!MSG!------------------!LF!
set MSG=!MSG!TFS VARIABLES!LF!
set MSG=!MSG!------------------!LF!
set MSG=!MSG!TFS_HOME                    =!TFS_HOME!!LF!
set MSG=!MSG!TFS_VCS_REPOSITORY_URL      =!TFS_VCS_REPOSITORY_URL!!LF!
set MSG=!MSG!TFS_VCS_PROJECT_ROOT        =!TFS_VCS_PROJECT_ROOT!!LF!
set MSG=!MSG!TFS_VCS_USERNAME            =!TFS_VCS_USERNAME!!LF!
CALL:printablePassword "%TFS_VCS_PASSWORD%" PR_PASSWORD
set MSG=!MSG!TFS_VCS_PASSWORD            =!PR_PASSWORD!!LF!
set MSG=!MSG!TFS_VCS_WORKSPACE_HOME      =!TFS_VCS_WORKSPACE_HOME!!LF!
set MSG=!MSG!TFS_VCS_WORKSPACE_NAME      =!TFS_VCS_WORKSPACE_NAME!!LF!
set MSG=!MSG!TFS_VCS_TEMP_DIR            =!TFS_VCS_TEMP_DIR!!LF!
set MSG=!MSG!TFS_VALID_ENV_CONFIG_PAIRS  =!TFS_VALID_ENV_CONFIG_PAIRS!!LF!
)
if "%SVN_PRINT%"=="1" (
set MSG=!MSG!------------------!LF!
set MSG=!MSG!SVN VARIABLES!LF!
set MSG=!MSG!------------------!LF!
set MSG=!MSG!SVN_HOME                    =!SVN_HOME!!LF!
set MSG=!MSG!SVN_VCS_REPOSITORY_URL      =!SVN_VCS_REPOSITORY_URL!!LF!
set MSG=!MSG!SVN_VCS_PROJECT_ROOT        =!SVN_VCS_PROJECT_ROOT!!LF!
set MSG=!MSG!SVN_VCS_USERNAME            =!SVN_VCS_USERNAME!!LF!
CALL:printablePassword "%SVN_VCS_PASSWORD%" PR_PASSWORD
set MSG=!MSG!SVN_VCS_PASSWORD            =!PR_PASSWORD!!LF!
set MSG=!MSG!SVN_VCS_WORKSPACE_HOME      =!SVN_VCS_WORKSPACE_HOME!!LF!
set MSG=!MSG!SVN_VCS_WORKSPACE_NAME      =!SVN_VCS_WORKSPACE_NAME!!LF!
set MSG=!MSG!SVN_VCS_TEMP_DIR            =!SVN_VCS_TEMP_DIR!!LF!
set MSG=!MSG!SVN_VALID_ENV_CONFIG_PAIRS  =!SVN_VALID_ENV_CONFIG_PAIRS!!LF!
)
if "%GIT_PRINT%"=="1" (
set MSG=!MSG!------------------!LF!
set MSG=!MSG!GIT VARIABLES!LF!
set MSG=!MSG!------------------!LF!
set MSG=!MSG!GIT_HOME                    =!GIT_HOME!!LF!
set MSG=!MSG!GIT_VCS_REPOSITORY_URL      =!GIT_VCS_REPOSITORY_URL!!LF!
set MSG=!MSG!GIT_VCS_PROJECT_ROOT        =!GIT_VCS_PROJECT_ROOT!!LF!
set MSG=!MSG!GIT_VCS_USERNAME            =!GIT_VCS_USERNAME!!LF!
CALL:printablePassword "%GIT_VCS_PASSWORD%" PR_PASSWORD
set MSG=!MSG!GIT_VCS_PASSWORD            =!PR_PASSWORD!!LF!
set MSG=!MSG!GIT_VCS_WORKSPACE_HOME      =!GIT_VCS_WORKSPACE_HOME!!LF!
set MSG=!MSG!GIT_VCS_WORKSPACE_NAME      =!GIT_VCS_WORKSPACE_NAME!!LF!
set MSG=!MSG!GIT_VCS_TEMP_DIR            =!GIT_VCS_TEMP_DIR!!LF!
set MSG=!MSG!GIT_VALID_ENV_CONFIG_PAIRS  =!GIT_VALID_ENV_CONFIG_PAIRS!!LF!
)
if "%P4_PRINT%"=="1" (
set MSG=!MSG!------------------!LF!
set MSG=!MSG!PERFORCE VARIABLES!LF!
set MSG=!MSG!------------------!LF!
set MSG=!MSG!P4_HOME                     =!P4_HOME!!LF!
set MSG=!MSG!P4_VCS_REPOSITORY_URL       =!P4_VCS_REPOSITORY_URL!!LF!
set MSG=!MSG!P4_VCS_PROJECT_ROOT         =!P4_VCS_PROJECT_ROOT!!LF!
set MSG=!MSG!P4_VCS_USERNAME             =!P4_VCS_USERNAME!!LF!
CALL:printablePassword "%P4_VCS_PASSWORD%" PR_PASSWORD
set MSG=!MSG!P4_VCS_PASSWORD             =!PR_PASSWORD!!LF!
set MSG=!MSG!P4_VCS_WORKSPACE_HOME       =!P4_VCS_WORKSPACE_HOME!!LF!
set MSG=!MSG!P4_VCS_WORKSPACE_NAME       =!P4_VCS_WORKSPACE_NAME!!LF!
set MSG=!MSG!P4_VCS_TEMP_DIR             =!P4_VCS_TEMP_DIR!!LF!
set MSG=!MSG!P4_VALID_ENV_CONFIG_PAIRS   =!P4_VALID_ENV_CONFIG_PAIRS!!LF!
)
if "%CVS_PRINT%"=="1" (
set MSG=!MSG!------------------!LF!
set MSG=!MSG!CVS VARIABLES!LF!
set MSG=!MSG!------------------!LF!
set MSG=!MSG!CVS_HOME                    =!CVS_HOME!!LF!
set MSG=!MSG!CVS_VCS_REPOSITORY_URL      =!CVS_VCS_REPOSITORY_URL!!LF!
set MSG=!MSG!CVS_VCS_PROJECT_ROOT        =!CVS_VCS_PROJECT_ROOT!!LF!
set MSG=!MSG!CVS_VCS_USERNAME            =!CVS_VCS_USERNAME!!LF!
CALL:printablePassword "%CVS_VCS_PASSWORD%" PR_PASSWORD
set MSG=!MSG!CVS_VCS_PASSWORD            =!PR_PASSWORD!!LF!
set MSG=!MSG!CVS_VCS_WORKSPACE_HOME      =!CVS_VCS_WORKSPACE_HOME!!LF!
set MSG=!MSG!CVS_VCS_WORKSPACE_NAME      =!CVS_VCS_WORKSPACE_NAME!!LF!
set MSG=!MSG!CVS_VCS_TEMP_DIR            =!CVS_VCS_TEMP_DIR!!LF!
set MSG=!MSG!CVS_VALID_ENV_CONFIG_PAIRS  =!CVS_VALID_ENV_CONFIG_PAIRS!!LF!
)
echo.!MSG!
REM # Output to the default log file if the variable DEFAULT_LOG_PATH is defined in ExecutePDTool.bat
if not defined DEFAULT_LOG_PATH goto WRITEOUTPUTEND
if exist "%DEFAULT_LOG_PATH%" echo.!MSG!>>"%DEFAULT_LOG_PATH%"
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
