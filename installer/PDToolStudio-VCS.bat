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
REM #
REM #######################################################
REM # Default Input Values for PDTool Installation
REM #######################################################
REM # Force SetupPDTool.bat to use default values when present and bypass prompting the user. Values=[Y or N].  This will streamline setup.
REM # Set to "Y" to bypass prompting for defaults values that are set with a value.
set DEF_FORCE_PROMPT_BYPASS=N
REM # Default for I_JAVA_HOME
set DEF_JAVA_HOME=C:\Program Files\Java\jre7
REM # Default for I_PDTOOL_DESTINATION_HOME
set DEF_PDTOOL_DESTINATION_HOME=C:\Users\%USERNAME%\.compositesw\PDToolStudio7.0.0_TFS
REM # Default for I_PDTOOL_DESTINATION_DIR
set DEF_PDTOOL_DESTINATION_DIR=PDToolStudio
REM # Default for I_CONFIGURE_VCS.  Bypass VCS variables when set to "N".
set DEF_CONFIGURE_VCS=Y
REM # Default for I_VCS_BASE_TYPE=[SVN|TFS|GIT|P4|CVS]
set DEF_VCS_BASE_TYPE=TFS
REM # Default for I_VCS_HOME is the location of where the VCS client executable is located
set DEF_VCS_HOME=%DEF_PDTOOL_DESTINATION_HOME%\VCSClients\TFS_TEE_client
REM # Default for I_VCS_REPOSITORY_URL - Always use 4 forward slashes to escape https://url --> https:////url and no slash at the end.
set DEF_VCS_REPOSITORY_URL=http:////localhost:8080/tfs/CompositeCollection
REM # Default for I_VCS_PROJECT_ROOT
set DEF_VCS_PROJECT_ROOT=Composite_7.0.0/cis_objects
REM # Default for I_VCS_WORKSPACE_NAME.  The name of the workspace.
REM #   To use variable delayed expansion put 2 %% signs around each variable name otherwise simply use a value.
REM #   Example: Combination of VCS username and release folder to make a unique workspace name=%%I_VCS_USERNAME%%%%I_RELEASE_FOLDER%%
set DEF_VCS_WORKSPACE_NAME=TFSsw7
REM # Default for I_VCS_USERNAME.  Generally this will be the standard computer USERNAME value.
set DEF_VCS_USERNAME=%USERNAME%
REM # To be appended to the I_VCS_USERNAME as in user@domain or leave a blank space if not applicable.  TFS requires this.
set DEF_VCS_DOMAIN= 
REM # Default for I_CIS_USERNAME used for setup of the Studio VCS property file
set DEF_CIS_USERNAME=admin
REM # Default CIS Domain  used for setup of the Studio VCS property file
set DEF_CIS_DOMAIN=composite
REM # Default CIS Hostname used for setup of the Studio VCS property file
set DEF_CIS_HOSTNAME=localhost
REM # Default VCS Configuration property file used for connecting PDToolStudio to VCS
REM # Existing standard, parameterized, configuration property templates: 
REM #    studio_SVN.properties, studio_TFS.properties, studio_GIT.properties, studio_P4.properties, studio_CVS.properties
REM # This is a default value only and may be overridden during PDToolStudio execution.
set DEF_CONFIG_PROPERTY_FILE=studio_TFS.properties
REM # This is the list of drive letters that PDTool will use to search for the first available drive.  
REM # It will be used as a substitute drive to shorten the overall path to the workspace.
REM # Depending on how /PDToolStudio/bin/setVars.bat is configured, this may be used for "subst" or it may be used for "net use".
REM # It is recommended to use "net use" because it survives log offs and reboots.
set PDTOOL_SUBSTITUTE_DRIVE_LIST=I: J: K: L: M: N: O: P: R: S: T: U: V: W: X: Y: Z:



REM #######################################################
REM # Invoke the PDTool Setup script
REM #######################################################
REM # Set debug...1=print debug statements.  0=do not print debug statements
set debug=0
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
if "%DEF_FORCE_PROMPT_BYPASS%"=="N" call %PDTOOL_SETUP_SCRIPT% PDToolStudio
if "%DEF_FORCE_PROMPT_BYPASS%"=="Y" call %PDTOOL_SETUP_SCRIPT% PDToolStudio "%DEF_PDTOOL_INSTALL_HOME%" "%DEF_JAVA_HOME%" "%DEF_PDTOOL_DESTINATION_HOME%" "%DEF_PDTOOL_DESTINATION_DIR%" "%DEF_CONFIGURE_VCS%" "%DEF_VCS_BASE_TYPE%" "%DEF_VCS_HOME%" "%DEF_VCS_REPOSITORY_URL%" "%DEF_VCS_PROJECT_ROOT%" ""                     "%DEF_VCS_WORKSPACE_NAME%" "%DEF_VCS_USERNAME%" "%DEF_VCS_DOMAIN%" "" "%DEF_CIS_USERNAME%" "%DEF_CIS_DOMAIN%" "" "%DEF_CIS_HOSTNAME%" "%DEF_CONFIG_PROPERTY_FILE%"
pause