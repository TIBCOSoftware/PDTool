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
REM # ---------------------
REM # SETUP INSTRUCTIONS:
REM # ---------------------
REM # 1. Modify the default variables to satisfy the PDTool setup requirements for your organization and environment.
REM #
REM # 2. Variable Usage:
REM #      a. Variables with a single % are variables from the command window environment that gets resolved immediately or
REM #            variables within this setup batch file that the person insalling wants to use.
REM #            Example 1: %USERNAME% is an example of a variable set in by the command window automaticaly.
REM #                set DEF_PDTOOL_DESTINATION_HOME=C:\Users\%USERNAME%\PDTool8.0.0_SVN
REM #            Example 2: %DEF_VCS_WORKSPACE_NAME%t is used to construct the temporary workspace name from the workspace name
REM #                set DEF_VCS_TEMP_DIR=%%%%PDTOOL_SUBSTITUTE_DRIVE%%%%\%DEF_VCS_WORKSPACE_NAME%t
REM #
REM #      b. Escaping variables for use within the target batch file setMyPrePDToolVars.bat
REM #            Use 4 percent signs on either side of the target variable so that it resolves correctly and results in the actual variable name
REM #            being replaced into the target setMyPrePDToolVars.bat file as: %PDTOOL_SUBSTITUTE_DRIVE%
REM #            Example 3: %%%%PDTOOL_SUBSTITUTE_DRIVE%%%% - resolves to %PDTOOL_SUBSTITUTE_DRIVE%
REM #                set DEF_VCS_TEMP_DIR=%%%%PDTOOL_SUBSTITUTE_DRIVE%%%%\%%DEF_VCS_WORKSPACE_NAME%%t
REM #
REM # 3. PDTool setup installation includes:
REM #      a. Person installing reads and accepts license.
REM #      b. Person installing accepts "net use" substitute drive letter to shorten path to workspace folder.
REM #      c. PDTool performs backups of the following files:
REM #           PDTool_HOME\bin\setVars.bat
REM #           PDTOOL_DESTINATION_HOME\setMyPrePDToolVars.bat
REM #           PDTOOL_DESTINATION_HOME\setMyPostPDToolVars.bat
REM #           PDTOOL_HOME\AutomatedTestFramework\regression\bin\setVars.bat
REM #      d. PDTool copies installer source files to the target PDTool directory.
REM #      e. PDTool copies default setMyPrePDToolVars.bat and setMyPostPDToolVars.bat to the target PDTool installation home directory.
REM #      f. PDTool modifes the following files:
REM #           %DEF_PDTOOL_DESTINATION_HOME%\setMyPrePDToolVars.bat
REM #              Example: C:\Users\username\PDTool8.0.0_SVN\setMyPrePDToolVars.bat
REM #           %DEF_PDTOOL_DESTINATION_HOME%\%DEF_PDTOOL_DESTINATION_DIR%\bin\setVars.bat
REM #              Example: C:\Users\username\PDTool8.0.0_SVN\PDTool\bin\setVars.bat
REM #           %DEF_PDTOOL_DESTINATION_HOME%\%DEF_PDTOOL_DESTINATION_DIR%\AutomatedTestFramework\regression\bin\setVars.bat
REM #              Example: C:\Users\username\PDTool8.0.0_SVN\PDTool\AutomatedTestFramework\regression\bin\setVars.bat
REM #      g. PDTool encrypts setMyPrePDToolVars.bat
REM #      h. If VCS configuration, PDTool removes the workspace directory.
REM #      i. If VCS configuration and VCS is TFS, PDTool requests use to accept TFS eula agreement
REM #      j. If VCS configuration, PDTool initializes the workspace directory
REM #######################################################
REM # Default Input Values for PDTool Installation
REM #######################################################
REM # Force SetupPDTool.bat to use default values when present and bypass prompting the user. Values=[Y or N].  This will streamline setup.
REM # Set to "Y" to bypass prompting for defaults values that are set with a value.
set DEF_FORCE_PROMPT_BYPASS=Y
REM # Default for I_JAVA_HOME.  Use %JAVA_HOME% if it is set in the environment and is JRE7.  Another alternative is to set to DV Studio JRE path.
set DEF_JAVA_HOME=C:\Program Files\Java\jdk-11.0.8
REM # Default for I_PDTOOL_DESTINATION_HOME.  This is the base PDTool installation folder.
set DEF_PDTOOL_DESTINATION_HOME=C:\Users\%USERNAME%\PDTool8.0.0_NOVCS
REM # Default for I_PDTOOL_DESTINATION_DIR.  This is the default PDTool directory name.
set DEF_PDTOOL_DESTINATION_DIR=PDTool
REM # Default for I_VALID_ENV_CONFIG_PAIRS
REM # These pairs provide the ability to use a short environment name in place of the configuration property name when instructing PDTool on which environment to deploy to.
REM #    Applicable to NOVCS and VCS installations.
REM #    Format: [vcs-type-char][env-type]~[config-name]   Example: NDEV~deploy_NOVCS_DEV,NUAT~deploy_NOVCS_UAT,NPROD~deploy_NOVCS_PROD
REM #            Comma separated, no space and no double quotes.  Tilde separates pairs: ENV_TYPE~ConfigFileName. The .properties extension is not included and gets added automatically.
REM #               vcs-type-char = represents the first characters of the VCS environment. N=NOVCS,S=SVN,T=TFS,G=GIT,P=P4
REM #                    env-type = represents the CIS environment short name deploying to.  Examples: DEV, ATA, ADA, CIT, SIT, UAT, TT, PROD, DR
REM #               The combination of [vcs-type-char][env-type] makes it unique across all different env-config pair types.
REM #    Configuration property file names found in PDTool\resources\config folder.
set DEF_VALID_ENV_CONFIG_PAIRS=NDEV~deploy_NOVCS_DEV1,NUAT~deploy_NOVCS_UAT1,NPROD~deploy_NOVCS_PROD1
REM # Default for I_CONFIG_PROPERTY_FILE - VCS Configuration property file used for connecting PDTool to CIS and VCS
set DEF_CONFIG_PROPERTY_FILE=deploy_NOVCS_UAT1.properties
REM # Default for I_CIS_USERNAME.  Generally this will be the standard computer USERNAME value.
set DEF_CIS_USERNAME=admin
REM # Default for I_CIS_DOMAIN.  CIS Domain used for connection by CIS_USERNAME
set DEF_CIS_DOMAIN=composite
REM # Default for I_CONFIGURE_VCS
set DEF_CONFIGURE_VCS=N
REM # Default for I_VCS_BASE_TYPE=[SVN|TFS|GIT|P4]
set DEF_VCS_BASE_TYPE=
REM # Default for I_VCS_EDITOR. Windows=notepad and UNIX=vi
set DEF_VCS_EDITOR=
REM # Default for I_VCS_HOME is the location of where the VCS client executable is located
set DEF_VCS_HOME=
REM # Default for I_VCS_REPOSITORY_URL - Always use 4 forward slashes to escape https://url --> https:////url and no slash at the end.
set DEF_VCS_REPOSITORY_URL=
REM # Default for I_VCS_PROJECT_ROOT - One or more relative VCS folders that extend from DEF_VCS_REPOSITORY_URL.
set DEF_VCS_PROJECT_ROOT=
REM # Default for I_RELEASE_FOLDER - The relative VCS folders path that makes up the variable portion for a given release.  This is simply a default folder to start with.
REM # During normal operation RELEASE_FOLDER is dynamically changed by using -release option with ExecutePDTool.bat
set DEF_RELEASE_FOLDER=
REM # Default for I_VCS_USERNAME.  Generally this will be the standard computer USERNAME value.
set DEF_VCS_USERNAME=
REM # Default for I_VCS_DOMAIN.  To be appended to the I_VCS_USERNAME as in user@domain or leave blank if not applicable.  TFS requires this.
set DEF_VCS_DOMAIN= 
REM # Default for I_VCS_WORKSPACE_HOME path.  Typically this is set to the PDTOOL_HOME variable but may be set to a different folder.  
REM # Alternatively set it to the short path substitute drive path PDTOOL_SUBSTITUTE_DRIVE referenced in setMyPrePDToolVars.bat
REM # This is the parent folder to where the workspace name folder "I_VCS_WORKSPACE_NAME" is a child.
set DEF_VCS_WORKSPACE_HOME=
REM # Default for I_VCS_WORKSPACE_NAME.  The name of the workspace.
set DEF_VCS_WORKSPACE_NAME=
REM # Default for I_VCS_TEMP_DIR path.  Typically this is the set to the PDTOOL_HOME variable + DEF_VCS_WORKSPACE_NAME + t indicating temp
REM # Alternatively set it to the short path substitute drive path PDTOOL_SUBSTITUTE_DRIVE referenced in setMyPrePDToolVars.bat + DEF_VCS_WORKSPACE_NAME + t indicating temp
REM # This would resolved to something like J:\SVNwt
set DEF_VCS_TEMP_DIR=
REM # This is the list of drive letters that PDTool will use to search for the first available drive.  
REM # It will be used as a substitute drive to shorten the overall path to the workspace.
set PDTOOL_SUBSTITUTE_DRIVE_LIST=I: J: K: L: M: N: O: P: R: S: T: U: V: W: X: Y: Z:
REM #
REM #
REM #######################################################
REM # Invoke the PDTool Setup script
REM #######################################################
REM # Set debug...1=print debug statements.  0=do not print debug statements
set debug=0
REM # Set debugReplaceText...0=do not pring debug statements for ParseLineReplaceText. 1=print all ParseLineReplaceText debug.  2=print FOUND only ParseLineReplaceText debug.
set debugReplaceText=0
REM # Setup script name
set PDTOOL_SETUP_SCRIPT=SetupPDTool.bat
pushd .
cd ..
REM # Default for I_PDTOOL_INSTALL_HOME that is the location of where PDTool was originally unzipped.  It is one folder level above the /installer directory.
set DEF_PDTOOL_INSTALL_HOME=%CD%
popd
if not exist %PDTOOL_SETUP_SCRIPT% (
	echo. %PDTOOL_SETUP_SCRIPT% does not exist in the current directory=%CD%
	exit /b 1
)
if "%DEF_FORCE_PROMPT_BYPASS%"=="n" set DEF_FORCE_PROMPT_BYPASS=N
if "%DEF_FORCE_PROMPT_BYPASS%"=="Y" set DEF_FORCE_PROMPT_BYPASS=Y
if "%DEF_FORCE_PROMPT_BYPASS%"=="N" call %PDTOOL_SETUP_SCRIPT% PDTool
if "%DEF_FORCE_PROMPT_BYPASS%"=="Y" call %PDTOOL_SETUP_SCRIPT% PDTool "%DEF_PDTOOL_INSTALL_HOME%" "%DEF_JAVA_HOME%" "%DEF_PDTOOL_DESTINATION_HOME%" "%DEF_PDTOOL_DESTINATION_DIR%" "%DEF_CONFIGURE_VCS%" "%DEF_VCS_BASE_TYPE%" "%DEF_VCS_HOME%" "%DEF_VCS_REPOSITORY_URL%" "%DEF_VCS_PROJECT_ROOT%" "%DEF_RELEASE_FOLDER%" "%DEF_VCS_WORKSPACE_HOME%" "%DEF_VCS_WORKSPACE_NAME%" "%DEF_VCS_TEMP_DIR%" "%DEF_VCS_USERNAME%" "%DEF_VCS_DOMAIN%" "" "%DEF_CIS_USERNAME%" "%DEF_CIS_DOMAIN%" "" "" "%DEF_CONFIG_PROPERTY_FILE%" "%DEF_VCS_EDITOR%"  "%DEF_VALID_ENV_CONFIG_PAIRS%"  "%debug%" "%debugReplaceText%"
pause