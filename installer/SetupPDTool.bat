@echo off
REM ############################################################################################################################
REM #
REM # PDTool Installation
REM #
REM # Execute: SetupPDTool.bat "[I_PDTOOL_INSTALL_TYPE]" "[I_PDTOOL_INSTALL_HOME]" "[I_JAVA_HOME]" "[I_PDTOOL_DESTINATION_HOME]" "[I_PDTOOL_DESTINATION_DIR]" "[I_CONFIGURE_VCS]" "[I_VCS_BASE_TYPE]" "[I_VCS_HOME]" "[I_VCS_REPOSITORY_URL]" "[I_VCS_PROJECT_ROOT]" "[I_RELEASE_FOLDER]" "[I_VCS_WORKSPACE_HOME]" "[I_VCS_WORKSPACE_NAME]" "[I_VCS_TEMP_DIR]" "[I_VCS_USERNAME]" "[I_VCS_DOMAIN]" "[I_VCS_PASSWORD]" "[I_CIS_USERNAME]" "[I_CIS_DOMAIN]" "[I_CIS_PASSWORD]" "[I_CIS_HOSTNAME]" "[I_CONFIG_PROPERTY_FILE]" "[I_VCS_EDITOR]" "[I_VALID_ENV_CONFIG_PAIRS]" "[debug]" "[debugReplaceText]"
REM # 
REM # I_PDTOOL_INSTALL_TYPE - [PDTool or PDToolStudio]. Determine which installer type to use.
REM # I_PDTOOL_INSTALL_HOME - The location of the PDTool installation scripts.
REM # I_JAVA_HOME - The JRE Home used for executing java. e.g. C:\Program Files\Java\jre6
REM # I_PDTOOL_DESTINATION_HOME - The home folder for the PDTool installation and associated binaries. e.g. C:\Users\%USERNAME%\.compositesw\PDTool7.0.0_SVN
REM # I_PDTOOL_DESTINATION_DIR - The PDTool directory name.  e.g. PDTool7.0.0
REM # I_CONFIGURE_VCS - [Y or N] - Y=Install PDTool with VCS. N=Install PDTool with no VCS such as for regression testing only.
REM # I_VCS_BASE_TYPE - The version control type [SVN|TFS|GIT|P4|CVS]
REM # I_VCS_HOME - The location of the VCS executable.
REM # I_VCS_REPOSITORY_URL - The version control repository URL.
REM # I_VCS_PROJECT_ROOT - The version control project root folder.
REM # I_RELEASE_FOLDER - The name of the release folder under /Rel.  e.g. 20140605
REM # I_VCS_WORKSPACE_HOME - Typically this is set to the PDTOOL_HOME variable but may be set to a different folder.  This is the parent folder to where the workspace name folder "I_VCS_WORKSPACE_NAME" is a child.
REM # I_VCS_WORKSPACE_NAME - VCS Workspace name.  e.g. TFSww or SVNww. (Shorter the better as it counts towards total length limit of 259 for TFS only)
REM # I_VCS_TEMP_DIR - Typically this is the set to the PDTOOL_HOME variable + I_VCS_WORKSPACE_NAME + t indicating temp
REM # I_VCS_USERNAME - The Version Control user name.
REM # I_VCS_DOMAIN - To be appended to the VCS_USERNAME as in user@domain or leave blank if not applicable. e.g. @CORP
REM # I_VCS_PASSWORD - The version control password.
REM # I_CIS_USERNAME - CIS username.
REM # I_CIS_DOMAIN -  CIS Domain used for connection by CIS_USERNAME
REM # I_CIS_PASSWORD - CIS password.
REM # I_CIS_HOSTNAME - CIS hostname used for PDToolStudio only.
REM # I_CONFIG_PROPERTY_FILE - VCS Configuration property file used for connecting PDTool to CIS
REM # I_VCS_EDITOR - VCS default editor to use if required. Windows=notepad and UNIX=vi
REM # I_VALID_ENV_CONFIG_PAIRS - The valid environment and configuration property file pairs provides the ability to use a short environment
REM #                            name in place of the configuration property name when instructing PDTool on which environment to deploy to.
REM # debug - 0=no debug, 1=debug
REM # debugReplaceText - 0=no debug, 1=debug replacement text
REM ############################################################################################################################
REM # (c) 2017 TIBCO Software Inc. All rights reserved.
REM # 
REM # Except as specified below, this software is licensed pursuant to the Eclipse Public License v. 1.0.
REM # The details can be found in the file LICENSE.
REM # 
REM # The following proprietary files are included as a convenience, and may not be used except pursuant
REM # to valid license to Composite Information Server or TIBCO® Data Virtualization Server:
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
cls
REM ############################################
REM # Initialize the variables
REM ############################################
CALL :InitVariables
set SCRIPT_NAME=%0
pushd .

REM ############################################
REM # Only display input if the first 
REM #   variable contains a value.
REM ############################################
if exist cisVersion.bat call cisVersion.bat
set I_BASE_CIS_VERSION=
if defined DEFAULT_CIS_VERSION set I_BASE_CIS_VERSION=%DEFAULT_CIS_VERSION:~0,1%

REM ############################################
REM # Get command line input values
REM ############################################
set I_PDTOOL_INSTALL_TYPE=%1
set I_PDTOOL_INSTALL_HOME=%2
set I_JAVA_HOME=%3
set I_PDTOOL_DESTINATION_HOME=%4
set I_PDTOOL_DESTINATION_DIR=%5
set I_CONFIGURE_VCS=%6
set I_VCS_BASE_TYPE=%7
set I_VCS_HOME=%8
set I_VCS_REPOSITORY_URL=%9
shift
set I_VCS_PROJECT_ROOT=%9
shift
set I_RELEASE_FOLDER=%9
shift
set I_VCS_WORKSPACE_HOME=%9
shift
set I_VCS_WORKSPACE_NAME=%9
shift
set I_VCS_TEMP_DIR=%9
shift
set I_VCS_USERNAME=%9
shift
set I_VCS_DOMAIN=%9
shift
set I_VCS_PASSWORD=%9
shift
set I_CIS_USERNAME=%9
shift
set I_CIS_DOMAIN=%9
shift
set I_CIS_PASSWORD=%9
shift
set I_CIS_HOSTNAME=%9
shift
set I_CONFIG_PROPERTY_FILE=%9
shift
set I_VCS_EDITOR=%9
shift
set I_VALID_ENV_CONFIG_PAIRS=%9
shift
set debug=%9
shift
set debugReplaceText=%9

REM ############################################
REM # Remove double quotes
REM ############################################
setlocal enabledelayedexpansion
if defined I_PDTOOL_INSTALL_TYPE set I_PDTOOL_INSTALL_TYPE=!I_PDTOOL_INSTALL_TYPE:"=!
if defined I_PDTOOL_INSTALL_HOME set I_PDTOOL_INSTALL_HOME=!I_PDTOOL_INSTALL_HOME:"=!
if defined I_JAVA_HOME set I_JAVA_HOME=!I_JAVA_HOME:"=!
if defined I_PDTOOL_DESTINATION_HOME set I_PDTOOL_DESTINATION_HOME=!I_PDTOOL_DESTINATION_HOME:"=!
if defined I_PDTOOL_DESTINATION_DIR set I_PDTOOL_DESTINATION_DIR=!I_PDTOOL_DESTINATION_DIR:"=!
if defined I_CONFIGURE_VCS set I_CONFIGURE_VCS=!I_CONFIGURE_VCS:"=!
if defined I_VCS_BASE_TYPE set I_VCS_BASE_TYPE=!I_VCS_BASE_TYPE:"=!
if defined I_VCS_HOME set I_VCS_HOME=!I_VCS_HOME:"=!
if defined I_VCS_REPOSITORY_URL set I_VCS_REPOSITORY_URL=!I_VCS_REPOSITORY_URL:"=!
if defined I_VCS_PROJECT_ROOT set I_VCS_PROJECT_ROOT=!I_VCS_PROJECT_ROOT:"=!
if defined I_RELEASE_FOLDER set I_RELEASE_FOLDER=!I_RELEASE_FOLDER:"=!
if defined I_VCS_WORKSPACE_HOME set I_VCS_WORKSPACE_HOME=!I_VCS_WORKSPACE_HOME:"=!
if defined I_VCS_WORKSPACE_NAME set I_VCS_WORKSPACE_NAME=!I_VCS_WORKSPACE_NAME:"=!
if defined I_VCS_TEMP_DIR set I_VCS_TEMP_DIR=!I_VCS_TEMP_DIR:"=!
if defined I_VCS_USERNAME set I_VCS_USERNAME=!I_VCS_USERNAME:"=!
if defined I_VCS_DOMAIN set I_VCS_DOMAIN=!I_VCS_DOMAIN:"=!
if defined I_VCS_PASSWORD set I_VCS_PASSWORD=!I_VCS_PASSWORD:"=!
if defined I_CIS_USERNAME set I_CIS_USERNAME=!I_CIS_USERNAME:"=!
if defined I_CIS_DOMAIN set I_CIS_DOMAIN=!I_CIS_DOMAIN:"=!
if defined I_CIS_PASSWORD set I_CIS_PASSWORD=!I_CIS_PASSWORD:"=!
if defined I_CIS_HOSTNAME set I_CIS_HOSTNAME=!I_CIS_HOSTNAME:"=!
if defined I_CONFIG_PROPERTY_FILE set I_CONFIG_PROPERTY_FILE=!I_CONFIG_PROPERTY_FILE:"=!
if defined I_VCS_EDITOR set I_VCS_EDITOR=!I_VCS_EDITOR:"=!
if defined I_VALID_ENV_CONFIG_PAIRS set I_VALID_ENV_CONFIG_PAIRS=!I_VALID_ENV_CONFIG_PAIRS:"=!
if defined debug set debug=!debug:"=!
if defined debugReplaceText set debugReplaceText=!debugReplaceText:"=!

REM ############################################
REM # Display banner
REM ############################################
set I_PDTOOL_INSTALL_TYPE_PAD="%I_PDTOOL_INSTALL_TYPE%%DEFAULT_CIS_VERSION%"
call :RPAD I_PDTOOL_INSTALL_TYPE_PAD 20
echo.----------------------------------------------------------------------
echo.-----------                                                -----------
echo.-----------   Professional Services %I_PDTOOL_INSTALL_TYPE_PAD%   -----------
echo.-----------           Promotion and Deployment Tool        -----------
echo.-----------                                                -----------    
echo.----------- Installation and Setup for %I_PDTOOL_INSTALL_TYPE_PAD%-----------
echo.----------------------------------------------------------------------
echo.
echo.----------------------------------------------------------------------
echo. Script: %SCRIPT_NAME%
echo.----------------------------------------------------------------------
echo.

REM ############################################
REM # Only display input if the first 
REM #   variable contains a value.
REM ############################################
pause
if "%I_PDTOOL_INSTALL_HOME%" == "" goto DISPLAY_CONTINUE
	echo.--------------------------------------------------------------------------------------
	echo.%SCRIPT_NAME% Input Variables Defined for %I_PDTOOL_INSTALL_TYPE%:
	echo.--------------------------------------------------------------------------------------
	echo.debug=                     [%debug%]
	echo.debugReplaceText=          [%debugReplaceText%]
	echo.I_BASE_CIS_VERSION=        [%I_BASE_CIS_VERSION%]
	echo.I_PDTOOL_INSTALL_TYPE=     [%I_PDTOOL_INSTALL_TYPE%]
	echo.I_PDTOOL_INSTALL_HOME=     [%I_PDTOOL_INSTALL_HOME%]
	echo.I_JAVA_HOME=               [%I_JAVA_HOME%]
	echo.I_PDTOOL_DESTINATION_HOME= [%I_PDTOOL_DESTINATION_HOME%]
	echo.I_PDTOOL_DESTINATION_DIR=  [%I_PDTOOL_DESTINATION_DIR%]
	echo.I_CONFIGURE_VCS=           [%I_CONFIGURE_VCS%]
	echo.I_VCS_BASE_TYPE=           [%I_VCS_BASE_TYPE%]
	echo.I_VCS_HOME=                [%I_VCS_HOME%]
	echo.I_VCS_REPOSITORY_URL=      [%I_VCS_REPOSITORY_URL%]
	echo.I_VCS_PROJECT_ROOT=        [%I_VCS_PROJECT_ROOT%]
	if "%I_PDTOOL_INSTALL_TYPE%"=="PDTool" echo.I_RELEASE_FOLDER=          [%I_RELEASE_FOLDER%]
	if "%I_PDTOOL_INSTALL_TYPE%"=="PDTool" echo.I_VCS_WORKSPACE_HOME=      [%I_VCS_WORKSPACE_HOME%]
	echo.I_VCS_WORKSPACE_NAME=      [%I_VCS_WORKSPACE_NAME%]
	if "%I_PDTOOL_INSTALL_TYPE%"=="PDTool" echo.I_VCS_TEMP_DIR=            [%I_VCS_TEMP_DIR%]
	echo.I_VCS_USERNAME=            [%I_VCS_USERNAME%]
	echo.I_VCS_DOMAIN=              [%I_VCS_DOMAIN%]
	if not defined I_VCS_PASSWORD echo.I_VCS_PASSWORD=            []
	if defined I_VCS_PASSWORD echo.I_VCS_PASSWORD=            [********]
	if "%I_PDTOOL_INSTALL_TYPE%"=="PDTool" echo.I_CIS_USERNAME=            [%I_CIS_USERNAME%]
	echo.I_CIS_DOMAIN=              [%I_CIS_DOMAIN%]
	if "%I_PDTOOL_INSTALL_TYPE%"=="PDTool" (
		if not defined I_CIS_PASSWORD echo.I_CIS_PASSWORD=            []
		if defined I_CIS_PASSWORD echo.I_CIS_PASSWORD=            [********]
	)
	if "%I_PDTOOL_INSTALL_TYPE%"=="PDToolStudio" echo.I_CIS_HOSTNAME=            [%I_CIS_HOSTNAME%]
	echo.I_CONFIG_PROPERTY_FILE=    [%I_CONFIG_PROPERTY_FILE%]
	echo.I_VCS_EDITOR=              [%I_VCS_EDITOR%]
	if "%I_PDTOOL_INSTALL_TYPE%"=="PDTool" echo.I_VALID_ENV_CONFIG_PAIRS=  [%I_VALID_ENV_CONFIG_PAIRS%]
:DISPLAY_CONTINUE

REM ############################################
REM # Instruction Section
REM ############################################
echo.
echo.##############################################################################
echo. INSTRUCTIONS:
echo.        
echo. Default values are provided for some input within brackets [default value].
echo.   Simply press Enter to accept the default.
echo.   To change the default, Enter your own value and press return.
echo.
echo. Be sure to provide correct values for all inputs.
echo.##############################################################################
echo.
pause
REM ############################################
REM # Validate input
REM ############################################
set LICENSE_ACKNOWLEDGE=N
set I_OVERWRITE_DECISION=N
:VALIDATE_INPUT1
REM #---------------------------------------
REM # Input for I_PDTOOL_INSTALL_HOME
REM #---------------------------------------
if not defined I_PDTOOL_INSTALL_HOME (
   echo.
   echo.       %I_PDTOOL_INSTALL_TYPE% installation script home location.
   echo.
   set /P I_PDTOOL_INSTALL_HOME=enter I_PDTOOL_INSTALL_HOME [!DEF_PDTOOL_INSTALL_HOME!]: 
   if not defined I_PDTOOL_INSTALL_HOME set I_PDTOOL_INSTALL_HOME=!DEF_PDTOOL_INSTALL_HOME!
   goto VALIDATE_INPUT1
)
if not exist "%I_PDTOOL_INSTALL_HOME%" (
   echo The %I_PDTOOL_INSTALL_TYPE% installation path does not exist: "!I_PDTOOL_INSTALL_HOME!" 
   set I_PDTOOL_INSTALL_HOME=
   goto VALIDATE_INPUT1
)
REM # Static variables - DO NOT CHANGE
set   I_PDTOOL_INSTALL_SCRIPTS=!I_PDTOOL_INSTALL_HOME!\installer

REM ############################################
REM # Display PDTool license
REM ############################################
IF "%LICENSE_ACKNOWLEDGE%" == "Y" GOTO LICENSE_ACKNOWLEDGED
more  %I_PDTOOL_INSTALL_SCRIPTS%\LICENSE.txt
:ACKNOWLEDGE_LICENSE
echo.
echo.**************************************************************************************
set /P LICENSE_ACKNOWLEDGE=Acknowledge PDTool License [Y/N]: 
echo.**************************************************************************************
echo.
if defined LICENSE_ACKNOWLEDGE CALL :UCase LICENSE_ACKNOWLEDGE LICENSE_ACKNOWLEDGE
IF "%LICENSE_ACKNOWLEDGE%" == "N" GOTO EXIT_SCRIPT
IF "%LICENSE_ACKNOWLEDGE%" == "Y" GOTO LICENSE_ACKNOWLEDGED
GOTO ACKNOWLEDGE_LICENSE
:LICENSE_ACKNOWLEDGED

REM ############################################
REM # Validate Input variables
REM ############################################
:VALIDATE_INPUT2
REM #---------------------------------------
REM # Input for I_JAVA_HOME
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_JAVA_HOME=!I_JAVA_HOME!
if not defined I_JAVA_HOME (
   echo.
   echo.
   echo.       Installed JREs:  
   pushd .
   c:
   cd "C:\Program Files"
   dir java.exe /s /b
   popd
   echo.
   echo.       Preferred: JRE7
   echo.
   set /P I_JAVA_HOME=Enter I_JAVA_HOME [!DEF_JAVA_HOME!]: 
   if not defined I_JAVA_HOME set I_JAVA_HOME=!DEF_JAVA_HOME!
   goto VALIDATE_INPUT2
)
if not exist "%I_JAVA_HOME%\bin\java.exe" (
   echo The java executable does not exist: "%I_JAVA_HOME%\bin\java.exe" 
   set I_JAVA_HOME=
   goto VALIDATE_INPUT2
)
REM #---------------------------------------
REM # Input for I_PDTOOL_DESTINATION_HOME
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_PDTOOL_DESTINATION_HOME=!I_PDTOOL_DESTINATION_HOME!
if not defined I_PDTOOL_DESTINATION_HOME (
   echo.
   echo.
   echo.       %I_PDTOOL_INSTALL_TYPE% Target Home:  
   echo.
   set /P I_PDTOOL_DESTINATION_HOME=Enter I_PDTOOL_DESTINATION_HOME [!DEF_PDTOOL_DESTINATION_HOME!]: 
   if not defined I_PDTOOL_DESTINATION_HOME set I_PDTOOL_DESTINATION_HOME=!DEF_PDTOOL_DESTINATION_HOME!
   goto VALIDATE_INPUT2
)
REM #---------------------------------------
REM # Input for I_PDTOOL_DESTINATION_DIR
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_PDTOOL_DESTINATION_DIR=!I_PDTOOL_DESTINATION_DIR!
if not defined I_PDTOOL_DESTINATION_DIR (
   echo.
   echo.
   echo.       %I_PDTOOL_INSTALL_TYPE% Target Directory Name:  
   echo.
   set /P I_PDTOOL_DESTINATION_DIR=Enter I_PDTOOL_DESTINATION_DIR !DEF_PDTOOL_DESTINATION_DIR!]: 
   if not defined I_PDTOOL_DESTINATION_DIR set I_PDTOOL_DESTINATION_DIR=!DEF_PDTOOL_DESTINATION_DIR!
   goto VALIDATE_INPUT2
)
REM #---------------------------------------
REM # Input for I_OVERWRITE_DECISION
REM #---------------------------------------
REM # If PDTOOL_HOME exists, ask user if they want to overwrite.
set  I_PDTOOL_HOME=!I_PDTOOL_DESTINATION_HOME!\!I_PDTOOL_DESTINATION_DIR!
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_PDTOOL_HOME=!I_PDTOOL_HOME!
if not exist "%I_PDTOOL_HOME%" goto :VALIDATE_INPUT3
   set I_OVERWRITE_DECISION=N
   echo.
   echo.       The %I_PDTOOL_INSTALL_TYPE% directory exists.  PDTOOL_HOME=!I_PDTOOL_HOME!
   echo.
   echo.       Dow you wan to overwrite the existing %I_PDTOOL_INSTALL_TYPE% directory [Y or N]^?  
   echo.
   set /P I_OVERWRITE_DECISION=Enter I_OVERWRITE_DECISION [%I_OVERWRITE_DECISION%]: 
   if defined I_OVERWRITE_DECISION CALL :UCase I_OVERWRITE_DECISION I_OVERWRITE_DECISION
   if "%I_OVERWRITE_DECISION%" == "Y" goto VALIDATE_INPUT3
REM # Goto the end of the script as the decision was made to NOT overwrite the existing PDTool
   echo.
   echo.--------------------------------------------------------------------------------------
   echo.The user chose to exit PDTool Installation.
   echo.--------------------------------------------------------------------------------------
   echo.
   goto POST_PROCESS

:VALIDATE_INPUT3
REM #---------------------------------------
REM # Input for I_CONFIGURE_VCS
REM #---------------------------------------
REM # Automatically set I_CONFIGURE_VCS from the default DEF_CONFIGURE_VCS and don't ask the user.
if defined DEF_CONFIGURE_VCS (
	CALL :UCase DEF_CONFIGURE_VCS DEF_CONFIGURE_VCS
	set I_CONFIGURE_VCS=!DEF_CONFIGURE_VCS!
)
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_CONFIGURE_VCS=!I_CONFIGURE_VCS!
if not defined I_CONFIGURE_VCS (
   echo.
   echo.       Do you want to configure Version Control with %I_PDTOOL_INSTALL_TYPE% [Y or N]
   echo.
   set /P I_CONFIGURE_VCS=Enter I_CONFIGURE_VCS Decision [%DEF_CONFIGURE_VCS%]: 
   if not defined I_CONFIGURE_VCS set I_CONFIGURE_VCS=%DEF_CONFIGURE_VCS%
   goto VALIDATE_INPUT3
)
   if defined I_CONFIGURE_VCS CALL :UCase I_CONFIGURE_VCS I_CONFIGURE_VCS
   if "%I_CONFIGURE_VCS%"=="Y" goto NEXT_VALIDATION1
   if "%I_CONFIGURE_VCS%"=="N" goto NEXT_VALIDATION3
   set I_CONFIGURE_VCS=
   goto VALIDATE_INPUT3

:NEXT_VALIDATION1
REM #---------------------------------------
REM # Input for I_VCS_BASE_TYPE
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_VCS_BASE_TYPE=!I_VCS_BASE_TYPE!
if defined I_VCS_BASE_TYPE goto NEXT_VALIDATION1_1
   echo.
   echo.
   echo.       I_VCS_BASE_TYPE=[SVN,TFS,GIT,P4,CVS]
   echo.
   set /P I_VCS_BASE_TYPE=Enter I_VCS_BASE_TYPE [%DEF_VCS_BASE_TYPE%]: 
   if not defined I_VCS_BASE_TYPE set I_VCS_BASE_TYPE=%DEF_VCS_BASE_TYPE%
   if defined I_VCS_BASE_TYPE CALL :UCase I_VCS_BASE_TYPE I_VCS_BASE_TYPE
   set I_VCS_BASE_TYPE_VALID=0
   if "%I_VCS_BASE_TYPE%"=="SVN" set I_VCS_BASE_TYPE_VALID=1
   if "%I_VCS_BASE_TYPE%"=="TFS" set I_VCS_BASE_TYPE_VALID=1
   if "%I_VCS_BASE_TYPE%"=="GIT" set I_VCS_BASE_TYPE_VALID=1
   if "%I_VCS_BASE_TYPE%"=="P4" set I_VCS_BASE_TYPE_VALID=1
   if "%I_VCS_BASE_TYPE%"=="CVS" set I_VCS_BASE_TYPE_VALID=1
   if %I_VCS_BASE_TYPE_VALID%==0 echo.ERROR: Invalid VCS_TYPE=%I_VCS_BASE_TYPE%.  Valid types=[SVN,TFS,GIT,P4,CVS]
   if %I_VCS_BASE_TYPE_VALID%==0 set I_VCS_BASE_TYPE_VALID=
   goto VALIDATE_INPUT3

:NEXT_VALIDATION1_1
REM #---------------------------------------
REM # Input for I_VCS_HOME
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_VCS_HOME=!I_VCS_HOME!
if not defined I_VCS_HOME (
   echo.
   echo.
   echo.       I_VCS_HOME=[VCS Home - Path to VCS executable]
   echo.
   set /P I_VCS_HOME=Enter I_VCS_HOME [!DEF_VCS_HOME!]: 
   if not defined I_VCS_HOME set I_VCS_HOME=!DEF_VCS_HOME!
   goto VALIDATE_INPUT3
)

REM #---------------------------------------
REM # Input for I_VCS_REPOSITORY_URL
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_VCS_REPOSITORY_URL=!I_VCS_REPOSITORY_URL!
if not defined I_VCS_REPOSITORY_URL (
   echo.
   echo.
   echo.       I_VCS_REPOSITORY_URL=[VCS Repository URL - host:port/path]
   if "%I_VCS_BASE_TYPE%"=="TFS" echo.            When TFS: The TFS repository URL pointing to the repository collection.
   if "%I_VCS_BASE_TYPE%"=="TFS" echo.                      e.g. http:////hostname:8080/tfs/DefaultCollection

   if "%I_VCS_BASE_TYPE%"=="SVN" echo.            When SVN: The subversion repository path at trunk or any folder designation within trunk.
   if "%I_VCS_BASE_TYPE%"=="SVN" echo.                      e.g. https:////hostname.domain/svn/basename

   if "%I_VCS_BASE_TYPE%"=="GIT" echo.            When GIT: The subversion repository path at trunk or any folder designation within trunk.
   if "%I_VCS_BASE_TYPE%"=="GIT" echo.                      e.g. https://user1:password@remotehost:443/path/to/repo.git/

   if "%I_VCS_BASE_TYPE%"=="P4" echo.            When P4: The subversion repository path at trunk or any folder designation within trunk.
   if "%I_VCS_BASE_TYPE%"=="P4" echo.                      e.g. hostname:port

   if "%I_VCS_BASE_TYPE%"=="CVS" echo.            When CVS: The subversion repository path at trunk or any folder designation within trunk.
   if "%I_VCS_BASE_TYPE%"=="CVS" echo.                      e.g. :pserver:user1:password@remotehost:2401/home/cvs
   echo.
   set /P I_VCS_REPOSITORY_URL=Enter I_VCS_REPOSITORY_URL [!DEF_VCS_REPOSITORY_URL!]: 
   if not defined I_VCS_REPOSITORY_URL set I_VCS_REPOSITORY_URL=!DEF_VCS_REPOSITORY_URL!
   goto VALIDATE_INPUT3
)
REM #---------------------------------------
REM # Input for I_VCS_PROJECT_ROOT
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_VCS_PROJECT_ROOT=!I_VCS_PROJECT_ROOT!
if not defined I_VCS_PROJECT_ROOT (
   echo.
   echo.
   echo.       I_VCS_PROJECT_ROOT=[VCS Paths leading up to where the CIS base level root folders start]
   echo.            When tfs: The TFS folder path starting at the TFS project and ending where the CIS base level root folders start
   echo.            When svn: The Subversion folder path starting directly after the Subversion repo URL and ending where the CIS base level root folders start
   echo.
   set /P I_VCS_PROJECT_ROOT=Enter I_VCS_PROJECT_ROOT [!DEF_VCS_PROJECT_ROOT!]: 
   if not defined I_VCS_PROJECT_ROOT set I_VCS_PROJECT_ROOT=!DEF_VCS_PROJECT_ROOT!
   goto VALIDATE_INPUT3
)

if "%I_PDTOOL_INSTALL_TYPE%"=="PDToolStudio" goto I_RELEASE_FOLDER_BYPASS
REM #---------------------------------------
REM # Input for I_RELEASE_FOLDER
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_RELEASE_FOLDER=!I_RELEASE_FOLDER!
if not defined I_RELEASE_FOLDER (
   echo.
   echo.USAGE: Enter the release folder for the project root
   echo.
   echo.       I_RELEASE_FOLDER=[Name of the release folder within the Project Root e.g. YYYYMMDD.  Enter a space for no folder name.]
   echo.
   set /P I_RELEASE_FOLDER=Enter I_RELEASE_FOLDER [!DEF_RELEASE_FOLDER!]: 
   if not defined I_RELEASE_FOLDER set I_RELEASE_FOLDER=!DEF_RELEASE_FOLDER!
   goto VALIDATE_INPUT3
)
:I_RELEASE_FOLDER_BYPASS

REM #---------------------------------------
REM # Input for I_VCS_EDITOR
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_VCS_EDITOR=!I_VCS_EDITOR!
if not defined I_VCS_EDITOR (
   echo.
   echo.
   echo.       I_VCS_EDITOR=[Default VCS Editor.  Windows: notepad and UNIX: vi]
   echo.
   set /P I_VCS_EDITOR=enter I_VCS_EDITOR [!DEF_VCS_EDITOR!]: 
   if not defined I_VCS_EDITOR set I_VCS_EDITOR=!DEF_VCS_EDITOR!
   goto VALIDATE_INPUT3
)
REM #---------------------------------------
REM # Input for I_VCS_USERNAME
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_VCS_USERNAME=!I_VCS_USERNAME!
if not defined I_VCS_USERNAME (
   echo.
   echo.
   echo.       I_VCS_USERNAME=[VCS username]
   echo.            This is your VCS username.
   echo.
   set /P I_VCS_USERNAME=enter I_VCS_USERNAME [!DEF_VCS_USERNAME!]: 
   if not defined I_VCS_USERNAME set I_VCS_USERNAME=!DEF_VCS_USERNAME!
   goto VALIDATE_INPUT3
)
REM #---------------------------------------
REM # Input for I_VCS_DOMAIN
REM #---------------------------------------
if not defined DEF_VCS_DOMAIN set I_VCS_DOMAIN= 
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_VCS_DOMAIN=!I_VCS_DOMAIN!
if not defined I_VCS_DOMAIN (
   echo.
   echo.
   echo.       I_VCS_DOMAIN=[To be appended to the I_VCS_USERNAME as in user@domain or leave blank if not applicable.  e.g. @CORP]
   echo.
   set /P I_VCS_DOMAIN=enter I_VCS_DOMAIN [!DEF_VCS_DOMAIN!]: 
   if not defined I_VCS_DOMAIN set I_VCS_DOMAIN=!DEF_VCS_DOMAIN!
   goto VALIDATE_INPUT3
)
REM #---------------------------------------
REM # Input for I_VCS_PASSWORD
REM #---------------------------------------
if defined I_VCS_PASSWORD GOTO NEXT_VALIDATION2
   echo.
   echo.
   echo.       I_VCS_PASSWORD=[VCS user password]
   echo.            This is your VCS user password which will be encrypted for I_VCS_USERNAME=!I_VCS_USERNAME!
   echo.
   powershell -Command $pword = read-host "enter I_VCS_PASSWORD" -AsSecureString ; $BSTR=[System.Runtime.InteropServices.Marshal]::SecureStringToBSTR($pword) ; [System.Runtime.InteropServices.Marshal]::PtrToStringAuto($BSTR) > .tmp.txt & set /p I_VCS_PASSWORD=<.tmp.txt & del .tmp.txt
   goto VALIDATE_INPUT3
   
:NEXT_VALIDATION2

if "%I_PDTOOL_INSTALL_TYPE%"=="PDToolStudio" goto I_VCS_WORKSPACE_HOME_BYPASS
REM #---------------------------------------
REM # Input for I_VCS_WORKSPACE_HOME
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_VCS_WORKSPACE_HOME=!I_VCS_WORKSPACE_HOME!
if not defined I_VCS_WORKSPACE_HOME (
   set DEF_VCS_WORKSPACE_HOME_RESULT=
   call :resolveVariableDelayedExpansion %DEF_VCS_WORKSPACE_HOME% DEF_VCS_WORKSPACE_HOME_RESULT
   echo.
   echo.
   echo.       I_VCS_WORKSPACE_HOME=[The VCS workspace name.  The workspace name is composed of %DEF_VCS_WORKSPACE_HOME%
   echo.
   set /P I_VCS_WORKSPACE_HOME=enter I_VCS_WORKSPACE_HOME [!DEF_VCS_WORKSPACE_HOME_RESULT!]: 
   if not defined I_VCS_WORKSPACE_HOME set I_VCS_WORKSPACE_HOME=!DEF_VCS_WORKSPACE_HOME_RESULT!
   rem echo I_VCS_WORKSPACE_HOME=%I_VCS_WORKSPACE_HOME%
   set DEF_VCS_WORKSPACE_HOME_RESULT=
   goto VALIDATE_INPUT3
)
:I_VCS_WORKSPACE_HOME_BYPASS

REM #---------------------------------------
REM # Input for I_VCS_WORKSPACE_NAME
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_VCS_WORKSPACE_NAME=!I_VCS_WORKSPACE_NAME!
if not defined I_VCS_WORKSPACE_NAME (
   set DEF_VCS_WORKSPACE_NAME_RESULT=
   call :resolveVariableDelayedExpansion %DEF_VCS_WORKSPACE_NAME% DEF_VCS_WORKSPACE_NAME_RESULT
   echo.
   echo.
   echo.       I_VCS_WORKSPACE_NAME=[The VCS workspace name.  The workspace name is composed of %DEF_VCS_WORKSPACE_NAME%
   echo.
   set /P I_VCS_WORKSPACE_NAME=enter I_VCS_WORKSPACE_NAME [!DEF_VCS_WORKSPACE_NAME_RESULT!]: 
   if not defined I_VCS_WORKSPACE_NAME set I_VCS_WORKSPACE_NAME=!DEF_VCS_WORKSPACE_NAME_RESULT!
   rem echo I_VCS_WORKSPACE_NAME=%I_VCS_WORKSPACE_NAME%
   set DEF_VCS_WORKSPACE_NAME_RESULT=
   goto VALIDATE_INPUT3
)

if "%I_PDTOOL_INSTALL_TYPE%"=="PDToolStudio" goto I_VCS_TEMP_DIR_BYPASS
REM #---------------------------------------
REM # Input for I_VCS_TEMP_DIR
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_VCS_TEMP_DIR=!I_VCS_TEMP_DIR!
if not defined I_VCS_TEMP_DIR (
   set DEF_VCS_TEMP_DIR_RESULT=
   call :resolveVariableDelayedExpansion %DEF_VCS_TEMP_DIR% DEF_VCS_TEMP_DIR_RESULT
   echo.
   echo.
   echo.       I_VCS_TEMP_DIR=[The VCS workspace name.  The workspace name is composed of %DEF_VCS_TEMP_DIR%
   echo.
   set /P I_VCS_TEMP_DIR=enter I_VCS_TEMP_DIR [!DEF_VCS_TEMP_DIR_RESULT!]: 
   if not defined I_VCS_TEMP_DIR set I_VCS_TEMP_DIR=!DEF_VCS_TEMP_DIR_RESULT!
   rem echo I_VCS_TEMP_DIR=%I_VCS_TEMP_DIR%
   set DEF_VCS_TEMP_DIR_RESULT=
   goto VALIDATE_INPUT3
)
:I_VCS_TEMP_DIR_BYPASS

:NEXT_VALIDATION3

REM #---------------------------------------
REM # Input for I_CIS_USERNAME
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_CIS_USERNAME=!I_CIS_USERNAME!
if not defined I_CIS_USERNAME ( 
   echo.
   echo.
   echo. [Data Virtualization] CIS_USERNAME=[username]
   echo.            This is your CIS username.
   echo.
   set /P I_CIS_USERNAME=Enter CIS_USERNAME [!DEF_CIS_USERNAME!]: 
   if not defined I_CIS_USERNAME set I_CIS_USERNAME=!DEF_CIS_USERNAME!
   goto VALIDATE_INPUT3
)

REM #---------------------------------------
REM # Input for I_CIS_DOMAIN
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_CIS_DOMAIN=!I_CIS_DOMAIN!
if not defined I_CIS_DOMAIN (
   echo.
   echo.
   echo.       I_CIS_DOMAIN=[Default CIS Domain used for connection by CIS_USERNAME.]
   echo.
   set /P I_CIS_DOMAIN=enter I_CIS_DOMAIN [!DEF_CIS_DOMAIN!]: 
   if not defined I_CIS_DOMAIN set I_CIS_DOMAIN=!DEF_CIS_DOMAIN!
   goto VALIDATE_INPUT3
)
if "%I_PDTOOL_INSTALL_TYPE%"=="PDTool" goto I_CIS_HOSTNAME_BYPASS
REM #---------------------------------------
REM # Input for I_CIS_HOSTNAME
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_CIS_HOSTNAME=!I_CIS_HOSTNAME!
if not defined I_CIS_HOSTNAME (
   echo.
   echo.
   echo.       I_CIS_HOSTNAME=[Default CIS hostname used for setting up Studio properties file.]
   echo.
   set /P I_CIS_HOSTNAME=enter I_CIS_HOSTNAME [!DEF_CIS_HOSTNAME!]: 
   if not defined I_CIS_HOSTNAME set I_CIS_HOSTNAME=!DEF_CIS_HOSTNAME!
   goto VALIDATE_INPUT3
)
:I_CIS_HOSTNAME_BYPASS

if "%I_PDTOOL_INSTALL_TYPE%"=="PDToolStudio" goto I_CIS_PASSWORD_BYPASS
REM #---------------------------------------
REM # Input for I_CIS_PASSWORD
REM #---------------------------------------
if defined I_CIS_PASSWORD GOTO I_CIS_PASSWORD_BYPASS
   echo.
   echo.
   echo. [Data Virtualization] CIS_PASSWORD=[username password]
   echo.            This is your CIS username password which will be encrypted for I_CIS_USERNAME=!I_CIS_USERNAME!
   echo.
   powershell -Command $pword = read-host "enter I_CIS_PASSWORD" -AsSecureString ; $BSTR=[System.Runtime.InteropServices.Marshal]::SecureStringToBSTR($pword) ; [System.Runtime.InteropServices.Marshal]::PtrToStringAuto($BSTR) > .tmp.txt & set /p I_CIS_PASSWORD=<.tmp.txt & del .tmp.txt
   goto VALIDATE_INPUT3
:I_CIS_PASSWORD_BYPASS

REM #---------------------------------------
REM # Input for I_CONFIG_PROPERTY_FILE
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_CONFIG_PROPERTY_FILE=!I_CONFIG_PROPERTY_FILE!
if not defined I_CONFIG_PROPERTY_FILE (
   echo.
   echo.
   echo.       I_CONFIG_PROPERTY_FILE=[Default VCS Configuration property file used for connecting PDTool to CIS.]
   echo.
   set /P I_CONFIG_PROPERTY_FILE=enter I_CONFIG_PROPERTY_FILE [!DEF_CONFIG_PROPERTY_FILE!]: 
   if not defined I_CONFIG_PROPERTY_FILE set I_CONFIG_PROPERTY_FILE=!DEF_CONFIG_PROPERTY_FILE!
   goto VALIDATE_INPUT3
)
if "%I_PDTOOL_INSTALL_TYPE%"=="PDToolStudio" goto I_VALID_ENV_CONFIG_PAIRS_BYPASS
REM #---------------------------------------
REM # Input for I_VALID_ENV_CONFIG_PAIRS
REM #---------------------------------------
if "%debug%"=="1" echo [DEBUG] INPUT REQUEST/VALIDATE:  I_VALID_ENV_CONFIG_PAIRS=!I_VALID_ENV_CONFIG_PAIRS!
if not defined I_VALID_ENV_CONFIG_PAIRS (
   echo.
   echo.
   echo.       I_VALID_ENV_CONFIG_PAIRS=[Default VCS Editor.  Windows: notepad and UNIX: vi]
   echo.
   set /P I_VALID_ENV_CONFIG_PAIRS=enter I_VALID_ENV_CONFIG_PAIRS [!DEF_VALID_ENV_CONFIG_PAIRS!]: 
   if not defined I_VALID_ENV_CONFIG_PAIRS set I_VALID_ENV_CONFIG_PAIRS=!DEF_VALID_ENV_CONFIG_PAIRS!
   goto VALIDATE_INPUT3
)
:I_VALID_ENV_CONFIG_PAIRS_BYPASS


:BEGIN_SCRIPT
REM ############################################
REM # Make final assignments
REM ############################################
if not defined DEF_VCS_DOMAIN set I_VCS_DOMAIN=

REM #=======================================
REM # Remove ending / for VCS_REPOSITORY_URL
REM #=======================================
CALL:REMOVE_SEPARATOR "%I_VCS_REPOSITORY_URL%" "/" I_VCS_REPOSITORY_URL

REM ############################################
REM # Static variables - DO NOT CHANGE
REM ############################################
set      I_PDTOOL_SOURCE_FILES=!I_PDTOOL_INSTALL_HOME!\installer_source
set          I_PDTOOL_WKS_HOME=!I_PDTOOL_HOME!\!I_VCS_WORKSPACE_NAME!
set             I_WINDOWS_USER=!USERNAME!
REM # Get the Drive letter for PDTOOL_HOME
for /F "usebackq delims==" %%I IN (`echo "!I_PDTOOL_HOME!"`) DO (
  set I_PDTOOL_HOME_DRIVE=%%~dI
)
set I_PDTOOL_HOME_DRIVE_SHARE=%I_PDTOOL_HOME_DRIVE::=$%

REM #=======================================
REM # Convert VCS_USERNAME to lowercase
REM #=======================================
if "%debug%"=="1" (
	echo.
	echo.-------------------------------------------
	echo.Convert usernames to lowercase
	echo.-------------------------------------------
)
if defined I_WINDOWS_USER CALL :LCase I_WINDOWS_USER I_WINDOWS_USER
if defined I_VCS_USERNAME CALL :LCase I_VCS_USERNAME I_VCS_USERNAME
if defined I_VCS_DOMAIN   CALL :LCase I_VCS_DOMAIN   I_VCS_DOMAIN
if defined I_CIS_USERNAME CALL :LCase I_CIS_USERNAME I_CIS_USERNAME
if "%debug%"=="1" (
	echo.[DEBUG] I_WINDOWS_USER=!I_WINDOWS_USER!
	echo.[DEBUG] I_VCS_USERNAME=!I_VCS_USERNAME!
	echo.[DEBUG] I_VCS_DOMAIN=!I_VCS_DOMAIN!
	echo.[DEBUG] I_CIS_USERNAME=!I_CIS_USERNAME!
	echo.[DEBUG]
)

REM ############################################
REM # Display Variables
REM ############################################
set I_PR_CIS_PASSWORD=
if defined I_CIS_PASSWORD set I_PR_CIS_PASSWORD=********
if "%debug%"=="1" set I_PR_CIS_PASSWORD=!I_CIS_PASSWORD!

set I_PR_VCS_PASSWORD=
if defined I_VCS_PASSWORD set I_PR_VCS_PASSWORD=********
if "%debug%"=="1" set I_PR_VCS_PASSWORD=!I_VCS_PASSWORD!

echo.
echo.--------------------------------------------------------------------------------------
echo.%SCRIPT_NAME% Variables Defined for %I_PDTOOL_INSTALL_TYPE%:
echo.--------------------------------------------------------------------------------------
    echo.I_BASE_CIS_VERSION=        [%I_BASE_CIS_VERSION%]
	echo.I_PDTOOL_INSTALL_TYPE=     [%I_PDTOOL_INSTALL_TYPE%]
	echo.I_PDTOOL_INSTALL_HOME=     [%I_PDTOOL_INSTALL_HOME%]
	echo.I_PDTOOL_INSTALL_SCRIPTS=  [%I_PDTOOL_INSTALL_SCRIPTS%]
	echo.I_JAVA_HOME=               [%I_JAVA_HOME%]
	echo.I_PDTOOL_DESTINATION_HOME= [%I_PDTOOL_DESTINATION_HOME%]
	echo.I_PDTOOL_DESTINATION_DIR=  [%I_PDTOOL_DESTINATION_DIR%]
	echo.I_CONFIGURE_VCS=           [%I_CONFIGURE_VCS%]
if "%I_CONFIGURE_VCS%"=="N" goto DISPLAY_NO_VCS
	echo.I_VCS_BASE_TYPE=           [%I_VCS_BASE_TYPE%]
	echo.I_VCS_HOME=                [%I_VCS_HOME%]
	echo.I_VCS_REPOSITORY_URL=      [%I_VCS_REPOSITORY_URL%]
	echo.I_VCS_PROJECT_ROOT=        [%I_VCS_PROJECT_ROOT%]
	if "%I_PDTOOL_INSTALL_TYPE%"=="PDTool" echo.I_RELEASE_FOLDER=          [%I_RELEASE_FOLDER%]
	echo.I_VCS_EDITOR=              [%I_VCS_EDITOR%]
	if "%I_PDTOOL_INSTALL_TYPE%"=="PDTool" echo.I_VCS_WORKSPACE_HOME=      [%I_VCS_WORKSPACE_HOME%]
	echo.I_VCS_WORKSPACE_NAME=      [%I_VCS_WORKSPACE_NAME%]
	if "%I_PDTOOL_INSTALL_TYPE%"=="PDTool" echo.I_VCS_TEMP_DIR=            [%I_VCS_TEMP_DIR%]
	echo.I_VCS_USERNAME=            [%I_VCS_USERNAME%]
	echo.I_VCS_DOMAIN=              [%I_VCS_DOMAIN%]
	echo.I_VCS_PASSWORD=            [!I_PR_VCS_PASSWORD!]
:DISPLAY_NO_VCS
	echo.I_CIS_USERNAME=            [%I_CIS_USERNAME%]
	if "%I_PDTOOL_INSTALL_TYPE%"=="PDTool" echo.I_CIS_PASSWORD=            [!I_PR_CIS_PASSWORD!]
	echo.I_CIS_DOMAIN=              [%I_CIS_DOMAIN%]
	if "%I_PDTOOL_INSTALL_TYPE%"=="PDToolStudio" echo.I_CIS_HOSTNAME=            [%I_CIS_HOSTNAME%]
	echo.I_CONFIG_PROPERTY_FILE=    [%I_CONFIG_PROPERTY_FILE%]
    if "%I_PDTOOL_INSTALL_TYPE%"=="PDTool" echo.I_VALID_ENV_CONFIG_PAIRS=  [%I_VALID_ENV_CONFIG_PAIRS%]
	echo.
	echo.Derived Variables Defined:
	echo.I_WINDOWS_USER=            [%I_WINDOWS_USER%]
	echo.I_PDTOOL_SOURCE_FILES=     [%I_PDTOOL_SOURCE_FILES%]
	echo.I_PDTOOL_HOME=             [%I_PDTOOL_HOME%]
	if "%I_CONFIGURE_VCS%"=="Y" echo.I_PDTOOL_WKS_HOME=         [%I_PDTOOL_WKS_HOME%]
	echo.I_PDTOOL_HOME_DRIVE=       [%I_PDTOOL_HOME_DRIVE%]
	echo.I_PDTOOL_HOME_DRIVE_SHARE= [%I_PDTOOL_HOME_DRIVE_SHARE%]

REM ############################################
REM # Determine if variables are correct
REM ############################################
set I_VARS_DECISION=Y
echo.
echo.Are the variables you entered correct^?  
echo.
set /P I_VARS_DECISION=Enter I_VARS_DECISION [Y or N]: 
if defined I_VARS_DECISION CALL :UCase I_VARS_DECISION I_VARS_DECISION

REM # Goto MAIN SCRIPT if user decision is Y
if "%I_VARS_DECISION%" == "Y" goto MAIN_SCRIPT_EXECUTION

REM # Otherwise Initialize the variables and start over requesting input for variables.
CALL :InitVariables
goto VALIDATE_INPUT1

REM ############################################
REM # Script Execution
REM ############################################
:MAIN_SCRIPT_EXECUTION
echo.--------------------------------------------------------------------------------------
echo.
echo.Execute Main Script for SetupPDTool.bat
echo.
echo.--------------------------------------------------------------------------------------
REM ###############################
REM #
REM # Start the main script
REM #
REM ###############################

REM # Define the static variables for PDTool and PDToolStudio
if "%I_PDTOOL_INSTALL_TYPE%"=="PDTool" (
	set MODIFY_SET_VARS=setVars.bat
	set MODIFY_ATF_SET_VARS=setVars.bat
	set PDTOOL_EXEC_SCRIPT=ExecutePDTool.bat
	set MODIFY_SET_MY_PRE_VARS=setMyPrePDToolVars.bat
	set MODIFY_SET_MY_POST_VARS=setMyPostPDToolVars.bat
	set INSTALLER_SOURCE_PDTOOL_DIR=PDTool
	set INSTALLER_SOURCE_VCSCLIENTS_DIR=VCSClients
	set INIT_DEPLOYMENT_PLAN=vcs-initialize.dp
)
if "%I_PDTOOL_INSTALL_TYPE%"=="PDToolStudio" (
	set MODIFY_SET_VARS=setVars.bat
	set PDTOOL_EXEC_SCRIPT=ExecutePDToolStudio.bat
	set MODIFY_SET_MY_PRE_VARS=setMyPrePDToolStudioVars.bat
	set MODIFY_SET_MY_POST_VARS=setMyPostPDToolStudioVars.bat
	set INSTALLER_SOURCE_PDTOOL_DIR=PDToolStudio
	set INSTALLER_SOURCE_VCSCLIENTS_DIR=VCSClients
)

REM ####################################################################
REM #
REM # Make sure the PDTool Source Files location exists
REM #
REM ####################################################################
if exist "%I_PDTOOL_SOURCE_FILES%\%INSTALLER_SOURCE_PDTOOL_DIR%" GOTO PDTOOL_SOURCE_FILES_EXIST
   echo.
   echo.################################################################################################
   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
   echo.#
   echo.# The I_PDTOOL_SOURCE_FILES directory does not exist: "%I_PDTOOL_SOURCE_FILES%\%INSTALLER_SOURCE_PDTOOL_DIR%" 
   echo.#
   echo.################################################################################################
   GOTO ERROR_EXIT_SCRIPT
  
:PDTOOL_SOURCE_FILES_EXIST
if exist "%I_PDTOOL_SOURCE_FILES%\%INSTALLER_SOURCE_VCSCLIENTS_DIR%" GOTO PDTOOL_SOURCE_FILES_EXIST_COMPLETE
   echo.
   echo.################################################################################################
   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
   echo.#
   echo.# The I_PDTOOL_SOURCE_FILES directory does not exist: "%I_PDTOOL_SOURCE_FILES%\%INSTALLER_SOURCE_VCSCLIENTS_DIR%" 
   echo.#
   echo.################################################################################################
   GOTO ERROR_EXIT_SCRIPT
:PDTOOL_SOURCE_FILES_EXIST_COMPLETE

REM ####################################################################
REM #
REM # Make sure the PDTool home location has been created
REM #
REM ####################################################################
if exist "%I_PDTOOL_HOME%" GOTO MKDIR_PDTOOL_HOME
	echo.
	echo.
	echo.################################################################################################
	echo.#
	echo.# EXECUTE:  Create %I_PDTOOL_INSTALL_TYPE% destination Home Directory
	echo.#
	echo.#==============================================================================================
	echo.#
	echo.# COMMAND:  mkdir "%I_PDTOOL_HOME%"
	echo.#
	echo.################################################################################################
	echo.
	mkdir "%I_PDTOOL_HOME%"

:MKDIR_PDTOOL_HOME
if exist "%I_PDTOOL_HOME%" GOTO MKDIR_PDTOOL_HOME_COMPLETE
	echo.
	echo.################################################################################################
	echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
	echo.#
	echo.# Unable to make directory "%I_PDTOOL_HOME%"
	echo.#
	echo.################################################################################################
	GOTO ERROR_EXIT_SCRIPT
:MKDIR_PDTOOL_HOME_COMPLETE 

REM ####################################################################
REM #
REM # Make sure the PDTool VCSClients destination location has been created
REM #
REM ####################################################################
if exist "%I_PDTOOL_DESTINATION_HOME%\%INSTALLER_SOURCE_VCSCLIENTS_DIR%" GOTO MKDIR_VCSCLIENTS
	echo.
	echo.
	echo.################################################################################################
	echo.#
	echo.# EXECUTE:  Create %I_PDTOOL_INSTALL_TYPE% destination VCSClients Directory
	echo.#
	echo.#==============================================================================================
	echo.#
	echo.# COMMAND:  mkdir "%I_PDTOOL_DESTINATION_HOME%\%INSTALLER_SOURCE_VCSCLIENTS_DIR%"
	echo.#
	echo.################################################################################################
	echo.
	mkdir "%I_PDTOOL_DESTINATION_HOME%\%INSTALLER_SOURCE_VCSCLIENTS_DIR%"

:MKDIR_VCSCLIENTS
if exist "%I_PDTOOL_DESTINATION_HOME%\%INSTALLER_SOURCE_VCSCLIENTS_DIR%" GOTO MKDIR_VCSCLIENTS_COMPLETE
	echo.
	echo.################################################################################################
	echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
	echo.#
	echo.# Unable to make directory "%I_PDTOOL_DESTINATION_HOME%\%INSTALLER_SOURCE_VCSCLIENTS_DIR%"
	echo.#
	echo.################################################################################################
	GOTO ERROR_EXIT_SCRIPT
:MKDIR_VCSCLIENTS_COMPLETE

REM ####################################################################
REM #
REM # If the PDTool Target Home directory already exists which means
REM #   the installation is overwriting the directory, then 
REM #   detect an existing mapped drive and reuse it.
REM # 
REM ####################################################################
set PDTOOL_SUBSTITUTE_DRIVE=
set FOUND_SET_VARS=N
if not exist "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" goto CONTINUE_EXISTING_PDTOOL_HOME
	REM # Initialize the test values to be able to pick up what is set in setMyPrePDToolVars.bat
	set PDTOOL_INSTALL_HOME=
	set PDTOOL_HOME=
	echo.
	echo.--------------------------------------------------------------------------------------
	echo.Getting current mapped drive variable "PDTOOL_SUBSTITUTE_DRIVE" for
	echo.  %I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%
	echo.--------------------------------------------------------------------------------------
	call "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%"
	echo.
	echo Existing PDTOOL_SUBSTITUTE_DRIVE=%PDTOOL_SUBSTITUTE_DRIVE%
	set FOUND_SET_VARS=Y
	
	set /A notSetCount=0
	if not defined PDTOOL_SUBSTITUTE_DRIVE set /A notSetCount+=1
	if "%PDTOOL_SUBSTITUTE_DRIVE%"=="" set /A notSetCount+=1
	if not defined MY_JAVA_HOME set /A notSetCount+=1 
	if "!MY_JAVA_HOME!"=="" set /A notSetCount+=1
	if not defined PDTOOL_INSTALL_HOME set /A notSetCount+=1
	if "!PDTOOL_INSTALL_HOME!"=="" set /A notSetCount+=1
	if not defined PDTOOL_HOME set /A notSetCount+=1
	if "!I_PDTOOL_HOME!"=="" set /A notSetCount+=1
	if %notSetCount% GEQ 4 set FOUND_SET_VARS=N
	
	if "%debug%"=="1" (
		echo.[DEBUG] Results from executing %MODIFY_SET_MY_PRE_VARS%:
		echo.[DEBUG]         FOUND_SET_VARS=%FOUND_SET_VARS%
		echo.[DEBUG]           MY_JAVA_HOME=!MY_JAVA_HOME!
		echo.[DEBUG]    PDTOOL_INSTALL_HOME=!PDTOOL_INSTALL_HOME!
		echo.[DEBUG]            PDTOOL_HOME=!I_PDTOOL_HOME!
	)
	echo.--------------------------------------------------------------------------------------
:CONTINUE_EXISTING_PDTOOL_HOME

REM ####################################################################
REM #
REM # Find the next available drive letter for NET USE to use or
REM #   Reuse the existing one if the directory is being overwritten.
REM #
REM # net use I: \\%COMPUTERNAME%\C$\Users\%USERNAME%\.compositesw\PDTool_COE /PERSISTENT:YES
REM # net use I: /DELETE
REM ####################################################################
set NetworkDrivesError=
:FIND_SUBSTITUTE_DRIVE
set VALID_PDTOOL_SUBSTITUTE_DRIVE=N
REM # If setVars.bat was found in an existing directory that is being overwritten then the default value must be used no matter what even if it is blank.
if "%FOUND_SET_VARS%"=="Y" set VALID_PDTOOL_SUBSTITUTE_DRIVE=Y
REM # If setVars.bat was not found then get the next available drive letter.
if "%FOUND_SET_VARS%"=="N" (
	call :FindAvailableDrive "%NetworkDrivesError%" PDTOOL_SUBSTITUTE_DRIVE
	if defined PDTOOL_SUBSTITUTE_DRIVE set VALID_PDTOOL_SUBSTITUTE_DRIVE=Y
)
if "%VALID_PDTOOL_SUBSTITUTE_DRIVE%"=="N" (
   echo.
   echo.################################################################################################
   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
   echo.#
   echo.# An available drive letter could not be determined.  Please make a drive letter available and re-execute this script.
   echo.#
   echo.################################################################################################
   GOTO ERROR_EXIT_SCRIPT
)
:VALIDATE_SUBSTITUTE_DRIVE
   echo.
   echo. PDTool network substitute drive letter.
   echo.    Objective: Used to shorten the overall path to workspace folder mainly for TFS.
   echo.
   echo.         Note: If PDTool installation is executed more than once, it will permanently map drive letters to the PDTool location.
   echo.               It may be necessary to unmap a drive letter and free it up before proceeding with installation again.
   echo.               To remove a drive manually execute this command from a command window: net use [drive_letter]: /DELETE        e.g. net use L: /DELETE
   echo.               Restart the installation so that PDTool will recognize the freed up drive letter.
   echo.
   set /P PDTOOL_SUBSTITUTE_DRIVE_DECISION=Do you want to use the substitute drive letter="%PDTOOL_SUBSTITUTE_DRIVE%"  [Y or N]:
   call:UCase PDTOOL_SUBSTITUTE_DRIVE_DECISION PDTOOL_SUBSTITUTE_DRIVE_DECISION
   if "%PDTOOL_SUBSTITUTE_DRIVE_DECISION%"=="N" (
      set PDTOOL_SUBSTITUTE_DRIVE=
	  goto CONTINIUE_SUBSTITUTE_DRIVE
   )
   if "%PDTOOL_SUBSTITUTE_DRIVE_DECISION%" NEQ "Y" goto VALIDATE_SUBSTITUTE_DRIVE
   
   if "%debug%"=="1" (
	  echo.
	  echo.COMMAND: call:MapNetworkDrive "%I_PDTOOL_HOME_DRIVE%" "%I_PDTOOL_HOME_DRIVE_SHARE%" "%PDTOOL_SUBSTITUTE_DRIVE%" "%I_PDTOOL_HOME%" ERROR
	  echo.
   )
   call:MapNetworkDrive "%I_PDTOOL_HOME_DRIVE%" "%I_PDTOOL_HOME_DRIVE_SHARE%" "%PDTOOL_SUBSTITUTE_DRIVE%" "%I_PDTOOL_HOME%" ERROR
   if "%ERROR%"=="2" (
	   set NetworkDrivesError=%NetworkDrivesError% %PDTOOL_SUBSTITUTE_DRIVE% 
	   goto FIND_SUBSTITUTE_DRIVE
   )
   if "%ERROR%"=="1" (
	   echo.
	   echo.################################################################################################
	   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
	   echo.#
	   echo.# An error occurred while attempting to map a network drive.  Fix the error and retry.
	   echo.#
	   echo.################################################################################################
	   GOTO ERROR_EXIT_SCRIPT
   )
   echo.################################################################################################
:CONTINIUE_SUBSTITUTE_DRIVE

REM ####################################################################
REM #
REM # Set the PDTool Workspace actual and Substitute drive
REM #
REM ####################################################################
if "%I_VCS_WORKSPACE_NAME%"=="" goto INSTALLER_DISPLAY_WORKSPACE_BYPASS
echo.
echo.
echo.################################################################################################
echo.#
echo.# EXECUTE:  Set the %I_PDTOOL_INSTALL_TYPE% Workspace actual and Substitute drive
echo.#
echo.#==============================================================================================
REM # Set the PDTool Workspace folder by combining the PDTool Workspace Home and VCS Project Root folders together.
set I_PDTOOL_WKS=!I_PDTOOL_WKS_HOME!\!I_VCS_PROJECT_ROOT!
echo.#
echo.# I_PDTOOL_WKS=%I_PDTOOL_WKS%
echo.#
if defined PDTOOL_SUBSTITUTE_DRIVE (
   echo.# %I_PDTOOL_INSTALL_TYPE% workspace using PDTOOL_SUBSTITUTE_DRIVE=!PDTOOL_SUBSTITUTE_DRIVE!\!I_VCS_WORKSPACE_NAME!\!I_VCS_PROJECT_ROOT!
   echo.#
)
echo.################################################################################################
:INSTALLER_DISPLAY_WORKSPACE_BYPASS
echo.
echo.

REM ####################################################################
REM #
REM # PDTool and PDToolStudio: Backup I_PDTOOL_HOME\bin\setVars.bat
REM #
REM ####################################################################
REM # Determine the backup file name
set BACKUP_FILENAME=%MODIFY_SET_VARS%.bak.1

REM # Perform the backup
if not exist "%I_PDTOOL_HOME%\bin\%MODIFY_SET_VARS%" goto INSTALLER_END_BACKUP1
	call:INCREMENT_BACKUP_FILE_NAME "%I_PDTOOL_HOME%\bin" "%MODIFY_SET_VARS%.bak" BACKUP_FILENAME
	echo.################################################################################################
	echo.#
	echo.# EXECUTE: Backup PDTOOL_HOME\bin\%MODIFY_SET_VARS%
	echo.#
	echo.#==============================================================================================
	echo.#
	echo.# COMMAND: copy /Y "%I_PDTOOL_HOME%\bin\%MODIFY_SET_VARS%" "%I_PDTOOL_HOME%\bin\%BACKUP_FILENAME%"
	echo.#
	echo.################################################################################################
	echo.
	copy /Y "%I_PDTOOL_HOME%\bin\%MODIFY_SET_VARS%" "%I_PDTOOL_HOME%\bin\%BACKUP_FILENAME%"
	set ERROR=%ERRORLEVEL%
	if %ERROR% NEQ 0 (
	   echo.
	   echo.################################################################################################
	   echo.# ERROR: PDTOOL INSTALLATION FAILED
	   echo.#
	   echo.# Copy bin\%MODIFY_SET_VARS% - Command Failed with error=%ERROR%
	   echo.#
	   echo.################################################################################################
	   GOTO ERROR_EXIT_SCRIPT
	)
	echo.
	echo.
:INSTALLER_END_BACKUP1

REM ####################################################################
REM #
REM # PDToolStudio: Backup I_PDTOOL_HOME\bin_host1\setVars.bat
REM #
REM ####################################################################
REM # Determine the backup file name
set BACKUP_FILENAME=%MODIFY_SET_VARS%.bak.1

REM # Perform the backup
if not exist "%I_PDTOOL_HOME%\bin_host1\%MODIFY_SET_VARS%" goto INSTALLER_END_BACKUP2
	call:INCREMENT_BACKUP_FILE_NAME "%I_PDTOOL_HOME%\bin_host1" "%MODIFY_SET_VARS%.bak" BACKUP_FILENAME
	echo.################################################################################################
	echo.#
	echo.# EXECUTE: Backup PDTOOL_HOME\bin_host1\%MODIFY_SET_VARS%
	echo.#
	echo.#==============================================================================================
	echo.#
	echo.# COMMAND: copy /Y "%I_PDTOOL_HOME%\bin_host1\%MODIFY_SET_VARS%" "%I_PDTOOL_HOME%\bin_host1\%BACKUP_FILENAME%"
	echo.#
	echo.################################################################################################
	echo.
	copy /Y "%I_PDTOOL_HOME%\bin_host1\%MODIFY_SET_VARS%" "%I_PDTOOL_HOME%\bin_host1\%BACKUP_FILENAME%"
	set ERROR=%ERRORLEVEL%
	if %ERROR% NEQ 0 (
	   echo.
	   echo.################################################################################################
	   echo.# ERROR: PDTOOL INSTALLATION FAILED
	   echo.#
	   echo.# Copy bin_host1\%MODIFY_SET_VARS% - Command Failed with error=%ERROR%
	   echo.#
	   echo.################################################################################################
	   GOTO ERROR_EXIT_SCRIPT
	)
	echo.
	echo.
:INSTALLER_END_BACKUP2

REM ####################################################################
REM #
REM # PDToolStudio: Backup I_PDTOOL_HOME\bin_host2\setVars.bat
REM #
REM ####################################################################
REM # Determine the backup file name
set BACKUP_FILENAME=%MODIFY_SET_VARS%.bak.1

REM # Perform the backup
if not exist "%I_PDTOOL_HOME%\bin_host2\%MODIFY_SET_VARS%" goto INSTALLER_END_BACKUP3
	call:INCREMENT_BACKUP_FILE_NAME "%I_PDTOOL_HOME%\bin_host2" "%MODIFY_SET_VARS%.bak" BACKUP_FILENAME
	echo.################################################################################################
	echo.#
	echo.# EXECUTE: Backup PDTOOL_HOME\bin_host2\%MODIFY_SET_VARS%
	echo.#
	echo.#==============================================================================================
	echo.#
	echo.# COMMAND: copy /Y "%I_PDTOOL_HOME%\bin_host2\%MODIFY_SET_VARS%" "%I_PDTOOL_HOME%\bin_host2\%BACKUP_FILENAME%"
	echo.#
	echo.################################################################################################
	echo.
	copy /Y "%I_PDTOOL_HOME%\bin_host2\%MODIFY_SET_VARS%" "%I_PDTOOL_HOME%\bin_host2\%BACKUP_FILENAME%"
	set ERROR=%ERRORLEVEL%
	if %ERROR% NEQ 0 (
	   echo.
	   echo.################################################################################################
	   echo.# ERROR: PDTOOL INSTALLATION FAILED
	   echo.#
	   echo.# Copy bin_host2\%MODIFY_SET_VARS% - Command Failed with error=%ERROR%
	   echo.#
	   echo.################################################################################################
	   GOTO ERROR_EXIT_SCRIPT
	)
	echo.
	echo.
:INSTALLER_END_BACKUP3

REM ####################################################################
REM #
REM # PDTool:       Backup I_PDTOOL_DESTINATION_HOME\setMyPrePDToolVars.bat
REM # PDToolStudio: Backup I_PDTOOL_DESTINATION_HOME\setMyPrePDToolStudioVars
REM #
REM ####################################################################
REM # Determine the backup file name
set  BACKUP_FILENAME=%MODIFY_SET_MY_PRE_VARS%.bak.1

REM # Perform the backup
if not exist "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" goto INSTALLER_END_BACKUP4
	call:INCREMENT_BACKUP_FILE_NAME "%I_PDTOOL_DESTINATION_HOME%" "%MODIFY_SET_MY_PRE_VARS%.bak"  BACKUP_FILENAME
	echo.################################################################################################
	echo.#
	echo.# EXECUTE: Backup PDTOOL_DESTINATION_HOME\%MODIFY_SET_MY_PRE_VARS%
	echo.#
	echo.#==============================================================================================
	echo.#
	echo.# COMMAND: copy /Y "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%BACKUP_FILENAME%"
	echo.#
	echo.################################################################################################
	echo.
	copy /Y "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%BACKUP_FILENAME%"
	set ERROR=%ERRORLEVEL%
	if %ERROR% NEQ 0 (
	   echo.
	   echo.################################################################################################
	   echo.# ERROR: PDTOOL INSTALLATION FAILED
	   echo.#
	   echo.# Copy %MODIFY_SET_MY_PRE_VARS% - Command Failed with error=%ERROR%
	   echo.#
	   echo.################################################################################################
	   GOTO ERROR_EXIT_SCRIPT
	)
	echo.
	echo.
:INSTALLER_END_BACKUP4

REM ####################################################################
REM #
REM # PDTool:       Backup I_PDTOOL_DESTINATION_HOME\setMyPostPDToolVars.bat
REM # PDToolStudio: Backup I_PDTOOL_DESTINATION_HOME\setMyPostPDToolStudioVars
REM #
REM ####################################################################
REM # Determine the backup file name
set BACKUP_FILENAME=%MODIFY_SET_MY_POST_VARS%.bak.1

REM # Perform the backup
if not exist "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" goto INSTALLER_END_BACKUP5
	call:INCREMENT_BACKUP_FILE_NAME "%I_PDTOOL_DESTINATION_HOME%" "%MODIFY_SET_MY_POST_VARS%.bak" BACKUP_FILENAME
	echo.################################################################################################
	echo.#
	echo.# EXECUTE: Backup PDTOOL_DESTINATION_HOME\%MODIFY_SET_MY_POST_VARS%
	echo.#
	echo.#==============================================================================================
	echo.#
	echo.# COMMAND: copy /Y "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_POST_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%BACKUP_FILENAME%"
	echo.#
	echo.################################################################################################
	echo.
	copy /Y "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_POST_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%BACKUP_FILENAME%"
	set ERROR=%ERRORLEVEL%
	if %ERROR% NEQ 0 (
	   echo.
	   echo.################################################################################################
	   echo.# ERROR: PDTOOL INSTALLATION FAILED
	   echo.#
	   echo.# Copy %MODIFY_SET_MY_POST_VARS% - Command Failed with error=%ERROR%
	   echo.#
	   echo.################################################################################################
	   GOTO ERROR_EXIT_SCRIPT
	)
	echo.
	echo.
:INSTALLER_END_BACKUP5

REM ####################################################################
REM #
REM # PDTool: Backup I_PDTOOL_HOME\AutomatedTestFramework\regression\bin\setVars.bat
REM #
REM ####################################################################
REM # Determine the backup file name
set BACKUP_FILENAME=%MODIFY_SET_VARS%.bak.1

REM # Perform the backup
if not exist "%I_PDTOOL_HOME%\AutomatedTestFramework\regression\bin\%MODIFY_SET_VARS%" goto INSTALLER_END_BACKUP6
	call:INCREMENT_BACKUP_FILE_NAME "%I_PDTOOL_HOME%\AutomatedTestFramework\regression\bin" "%MODIFY_SET_VARS%.bak" BACKUP_FILENAME
	echo.################################################################################################
	echo.#
	echo.# EXECUTE: Backup PDTOOL_HOME\AutomatedTestFramework\regression\bin\%MODIFY_SET_VARS%
	echo.#
	echo.#==============================================================================================
	echo.#
	echo.# COMMAND: copy /Y "%I_PDTOOL_HOME%\AutomatedTestFramework\regression\bin\%MODIFY_SET_VARS%" "%I_PDTOOL_HOME%\AutomatedTestFramework\regression\bin\%BACKUP_FILENAME%"
	echo.#
	echo.################################################################################################
	echo.
	copy /Y "%I_PDTOOL_HOME%\AutomatedTestFramework\regression\bin\%MODIFY_SET_VARS%" "%I_PDTOOL_HOME%\AutomatedTestFramework\regression\bin\%BACKUP_FILENAME%"
	set ERROR=%ERRORLEVEL%
	if %ERROR% NEQ 0 (
	   echo.
	   echo.################################################################################################
	   echo.# ERROR: PDTOOL INSTALLATION FAILED
	   echo.#
	   echo.# Copy %MODIFY_SET_VARS% - Command Failed with error=%ERROR%
	   echo.#
	   echo.################################################################################################
	   GOTO ERROR_EXIT_SCRIPT
	)
	echo.
	echo.
:INSTALLER_END_BACKUP6

REM ####################################################################
REM #
REM # PDTool: Backup I_PDTOOL_HOME\AutomatedTestFramework\migration\bin\setVars.bat
REM #
REM ####################################################################
REM # Determine the backup file name
set BACKUP_FILENAME=%MODIFY_SET_VARS%.bak.1

REM # Perform the backup
if not exist "%I_PDTOOL_HOME%\AutomatedTestFramework\migration\bin\%MODIFY_SET_VARS%" goto INSTALLER_END_BACKUP7
	call:INCREMENT_BACKUP_FILE_NAME "%I_PDTOOL_HOME%\AutomatedTestFramework\migration\bin" "%MODIFY_SET_VARS%.bak" BACKUP_FILENAME
	echo.################################################################################################
	echo.#
	echo.# EXECUTE: Backup PDTOOL_HOME\AutomatedTestFramework\migration\bin\%MODIFY_SET_VARS%
	echo.#
	echo.#==============================================================================================
	echo.#
	echo.# COMMAND: copy /Y "%I_PDTOOL_HOME%\AutomatedTestFramework\migration\bin\%MODIFY_SET_VARS%" "%I_PDTOOL_HOME%\AutomatedTestFramework\migration\bin\%BACKUP_FILENAME%"
	echo.#
	echo.################################################################################################
	echo.
	copy /Y "%I_PDTOOL_HOME%\AutomatedTestFramework\migration\bin\%MODIFY_SET_VARS%" "%I_PDTOOL_HOME%\AutomatedTestFramework\migration\bin\%BACKUP_FILENAME%"
	set ERROR=%ERRORLEVEL%
	if %ERROR% NEQ 0 (
	   echo.
	   echo.################################################################################################
	   echo.# ERROR: PDTOOL INSTALLATION FAILED
	   echo.#
	   echo.# Copy %MODIFY_SET_VARS% - Command Failed with error=%ERROR%
	   echo.#
	   echo.################################################################################################
	   GOTO ERROR_EXIT_SCRIPT
	)
	echo.
	echo.
:INSTALLER_END_BACKUP7

REM ####################################################################
REM #
REM # PDTool: Backup I_PDTOOL_HOME\resources\modules\servers.xml
REM #
REM ####################################################################
REM # Determine the backup file name
set SERVERS_XML=servers.xml
set BACKUP_FILENAME=%SERVERS_XML%.bak.1

REM # Perform the backup
if not exist "%I_PDTOOL_HOME%\resources\modules\%SERVERS_XML%" goto INSTALLER_END_BACKUP8
	call:INCREMENT_BACKUP_FILE_NAME "%I_PDTOOL_HOME%\resources\modules" "%SERVERS_XML%.bak" BACKUP_FILENAME
	echo.################################################################################################
	echo.#
	echo.# EXECUTE: Backup PDTOOL_HOME\resources\modules\%SERVERS_XML%
	echo.#
	echo.#==============================================================================================
	echo.#
	echo.# COMMAND: copy /Y "%I_PDTOOL_HOME%\resources\modules\%SERVERS_XML%" "%I_PDTOOL_HOME%\resources\modules\%BACKUP_FILENAME%"
	echo.#
	echo.################################################################################################
	echo.
	copy /Y "%I_PDTOOL_HOME%\resources\modules\%SERVERS_XML%" "%I_PDTOOL_HOME%\resources\modules\%BACKUP_FILENAME%"
	set ERROR=%ERRORLEVEL%
	if %ERROR% NEQ 0 (
	   echo.
	   echo.################################################################################################
	   echo.# ERROR: PDTOOL INSTALLATION FAILED
	   echo.#
	   echo.# Copy %SERVERS_XML% - Command Failed with error=%ERROR%
	   echo.#
	   echo.################################################################################################
	   GOTO ERROR_EXIT_SCRIPT
	)
	echo.
	echo.
:INSTALLER_END_BACKUP8

echo.################################################################################################
echo.#
echo.# EXECUTE: Copy installer source directory "%INSTALLER_SOURCE_PDTOOL_DIR%" to "%I_PDTOOL_HOME%"
echo.#
echo.#==============================================================================================
echo.#
echo.# COMMAND: robocopy /E /IS "%I_PDTOOL_SOURCE_FILES%\%INSTALLER_SOURCE_PDTOOL_DIR%" "%I_PDTOOL_HOME%"
echo.#
echo.################################################################################################
echo.
robocopy /E /IS "%I_PDTOOL_SOURCE_FILES%\%INSTALLER_SOURCE_PDTOOL_DIR%" "%I_PDTOOL_HOME%"
if exist "%I_PDTOOL_HOME%\bin\%PDTOOL_EXEC_SCRIPT%" GOTO COPY_INSTALLER_SOURCE_CONTINUE
   echo.
   echo.################################################################################################
   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
   echo.#
   echo.# The file "%I_PDTOOL_HOME%\bin\%PDTOOL_EXEC_SCRIPT%" does not exist.
   echo.# robocopy command did not properly copy the files.
   echo.#
   echo.################################################################################################
   GOTO ERROR_EXIT_SCRIPT

:COPY_INSTALLER_SOURCE_CONTINUE
IF "%I_CONFIGURE_VCS%" == "N" GOTO INSTALLER_COPY_SOURCE_BYPASS
echo.
echo.
echo.################################################################################################
echo.#
echo.# EXECUTE: Copy installer source directory "%INSTALLER_SOURCE_VCSCLIENTS_DIR%" to "%I_PDTOOL_DESTINATION_HOME%\%INSTALLER_SOURCE_VCSCLIENTS_DIR%"
echo.#
echo.#==============================================================================================
echo.#
echo.# COMMAND: robocopy /E /IS "%I_PDTOOL_SOURCE_FILES%\%INSTALLER_SOURCE_VCSCLIENTS_DIR%" "%I_PDTOOL_DESTINATION_HOME%\%INSTALLER_SOURCE_VCSCLIENTS_DIR%"
echo.#
echo.################################################################################################
echo.
robocopy /E /IS "%I_PDTOOL_SOURCE_FILES%\%INSTALLER_SOURCE_VCSCLIENTS_DIR%" "%I_PDTOOL_DESTINATION_HOME%\%INSTALLER_SOURCE_VCSCLIENTS_DIR%"
if exist "%I_PDTOOL_DESTINATION_HOME%\%INSTALLER_SOURCE_VCSCLIENTS_DIR%" GOTO INSTALLER_COPY_SOURCE_BYPASS
   echo.
   echo.################################################################################################
   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
   echo.#
   echo.# The directory "%I_PDTOOL_DESTINATION_HOME%\%INSTALLER_SOURCE_VCSCLIENTS_DIR%" does not exist.
   echo.# robocopy command did not properly copy the files.
   echo.#
   echo.################################################################################################
   GOTO ERROR_EXIT_SCRIPT

:INSTALLER_COPY_SOURCE_BYPASS
echo.
echo.

REM ####################################################################
REM #
REM # Copy setMyPrePDToolVars.bat and setMyPostPDToolVars.bat
REM #
REM ####################################################################
echo.################################################################################################
echo.#
echo.# EXECUTE: Copy %MODIFY_SET_MY_PRE_VARS% and %MODIFY_SET_MY_POST_VARS% to "%I_PDTOOL_DESTINATION_HOME%"
echo.#
echo.#==============================================================================================
echo.#
echo.# COMMAND: Copy /Y "%I_PDTOOL_HOME%\bin\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%"
echo.# COMMAND: Copy /Y "%I_PDTOOL_HOME%\bin\%MODIFY_SET_MY_POST_VARS%" "%I_PDTOOL_DESTINATION_HOME%"
echo.#
echo.################################################################################################
echo.
Copy /Y "%I_PDTOOL_HOME%\bin\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%"
if exist "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" GOTO COPY_FILE1_CONTINUE
   echo.
   echo.################################################################################################
   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
   echo.#
   echo.# The file "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" does not exist.
   echo.# Copy command did not properly copy the file.
   echo.#
   echo.################################################################################################
   GOTO ERROR_EXIT_SCRIPT

:COPY_FILE1_CONTINUE
Copy /Y "%I_PDTOOL_HOME%\bin\%MODIFY_SET_MY_POST_VARS%" "%I_PDTOOL_DESTINATION_HOME%"
if exist "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_POST_VARS%" GOTO COPY_FILE2_CONTINUE
   echo.
   echo.################################################################################################
   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
   echo.#
   echo.# The file "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_POST_VARS%" does not exist.
   echo.# Copy command did not properly copy the file.
   echo.#
   echo.################################################################################################
   GOTO ERROR_EXIT_SCRIPT
   
:COPY_FILE2_CONTINUE

REM ####################################################################
REM #
REM # Replace text in setMyPrePDToolVars.bat
REM #
REM ####################################################################
echo.
echo.
echo.################################################################################################
echo.#
echo.# EXECUTE: Replace text in %MODIFY_SET_MY_PRE_VARS%
echo.#
echo.################################################################################################

setlocal ENABLEEXTENSIONS DISABLEDELAYEDEXPANSION

REM #---------------------------------------
REM # set GEN_PRINT
REM #---------------------------------------
echo.
echo.Replace "GEN_PRINT" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set GEN_PRINT=
set I_REPLACE_TEXT=set GEN_PRINT=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=true
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # set MY_JAVA_HOME
REM #---------------------------------------
echo.
echo.Replace "MY_JAVA_HOME" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set MY_JAVA_HOME=
set I_REPLACE_TEXT=set MY_JAVA_HOME=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_JAVA_HOME!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

echo.
echo.Replace "PDTOOL_SUBSTITUTE_DRIVE" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set PDTOOL_SUBSTITUTE_DRIVE=
set I_REPLACE_TEXT=set PDTOOL_SUBSTITUTE_DRIVE=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!PDTOOL_SUBSTITUTE_DRIVE!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # set PDTOOL_INSTALL_HOME
REM #---------------------------------------
echo.
echo.Replace "PDTOOL_INSTALL_HOME" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set PDTOOL_INSTALL_HOME=
set I_REPLACE_TEXT=set PDTOOL_INSTALL_HOME=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_PDTOOL_DESTINATION_HOME!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # set PDTOOL_HOME
REM #---------------------------------------
echo.
echo.Replace "PDTOOL_HOME" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set PDTOOL_HOME=
set I_REPLACE_TEXT=set PDTOOL_HOME=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_PDTOOL_HOME!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # Set MY_CONFIG_PROPERTY_FILE
REM #---------------------------------------
echo.
echo.Replace "MY_CONFIG_PROPERTY_FILE" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set MY_CONFIG_PROPERTY_FILE=
set I_REPLACE_TEXT=set MY_CONFIG_PROPERTY_FILE=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_CONFIG_PROPERTY_FILE!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # set CIS_PRINT
REM #---------------------------------------
echo.
echo.Replace "CIS_PRINT" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set CIS_PRINT=
set I_REPLACE_TEXT=set CIS_PRINT=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=true
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # Set CIS_USERNAME
REM #---------------------------------------
if "%I_PDTOOL_INSTALL_TYPE%"=="PDToolStudio" goto INSTALLER_CIS_USERNAME_BYPASS
echo.
echo.Replace "CIS_USERNAME" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set CIS_USERNAME=
set I_REPLACE_TEXT=set CIS_USERNAME=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_CIS_USERNAME!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

:INSTALLER_CIS_USERNAME_BYPASS

REM #---------------------------------------
REM # Set CIS_PASSWORD
REM #---------------------------------------
if "%I_PDTOOL_INSTALL_TYPE%"=="PDToolStudio" goto INSTALLER_CIS_PASSWORD_BYPASS
echo.
echo.Replace "CIS_PASSWORD" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set CIS_PASSWORD=
set I_REPLACE_TEXT=set CIS_PASSWORD=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_CIS_PASSWORD!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_PR_CIS_PASSWORD!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1
:INSTALLER_CIS_PASSWORD_BYPASS

REM #---------------------------------------
REM # Set CIS_DOMAIN
REM #---------------------------------------
if "%I_PDTOOL_INSTALL_TYPE%"=="PDToolStudio" goto INSTALLER_CIS_DOMAIN_BYPASS
echo.
echo.Replace "CIS_DOMAIN" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set CIS_DOMAIN=
set I_REPLACE_TEXT=set CIS_DOMAIN=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_CIS_DOMAIN!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

:INSTALLER_CIS_DOMAIN_BYPASS

REM #---------------------------------------
REM # Setup VCS [SVN,TFS,GIT,P4,CVS]
REM #---------------------------------------
set SVN_HOME=
set TFS_HOME=
set GIT_HOME=
set P4_HOME=
set CVS_HOME=
if "%I_VCS_BASE_TYPE%"=="SVN" set SVN_HOME=%I_VCS_HOME%
if "%I_VCS_BASE_TYPE%"=="TFS" set TFS_HOME=%I_VCS_HOME%
if "%I_VCS_BASE_TYPE%"=="GIT" set GIT_HOME=%I_VCS_HOME%
if "%I_VCS_BASE_TYPE%"=="P4"  set P4_HOME=%I_VCS_HOME%
if "%I_VCS_BASE_TYPE%"=="CVS" set CVS_HOME=%I_VCS_HOME%
if "%I_VCS_BASE_TYPE%"=="" (
	REM #---------------------------------------
	REM # Set NOVCS_VALID_ENV_CONFIG_PAIRS
	REM #---------------------------------------
	echo.
	echo.Replace "NOVCS_VALID_ENV_CONFIG_PAIRS" in %MODIFY_SET_MY_PRE_VARS%
	echo.
	set    I_FIND_TEXT=set NOVCS_VALID_ENV_CONFIG_PAIRS=
	set I_REPLACE_TEXT=set NOVCS_VALID_ENV_CONFIG_PAIRS=
	setlocal enabledelayedexpansion
	set I_REPLACE_VALUE=!I_VALID_ENV_CONFIG_PAIRS!
	echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
				  CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
	set ERROR=%ERRORLEVEL%
	endlocal& set ERROR=%ERROR%
	if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1
    goto INSTALLER_MODIFY_SET_VARS
)

REM #---------------------------------------
REM # Set [SVN,TFS,GIT,P4,CVS]_PRINT
REM #---------------------------------------
echo.
echo.Replace "%I_VCS_BASE_TYPE%_PRINT" in %MODIFY_SET_MY_PRE_VARS%
echo.
REM # Check for %I_VCS_BASE_TYPE%_PRINT=0
set    I_FIND_TEXT=set %I_VCS_BASE_TYPE%_PRINT=0
set I_REPLACE_TEXT=set %I_VCS_BASE_TYPE%_PRINT=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=true
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # Set [SVN,TFS,GIT,P4,CVS]_HOME
REM #---------------------------------------
echo.
echo.Replace "%I_VCS_BASE_TYPE%_HOME" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set %I_VCS_BASE_TYPE%_HOME=
set I_REPLACE_TEXT=set %I_VCS_BASE_TYPE%_HOME=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_VCS_HOME!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # Set [SVN,TFS,GIT,P4,CVS]_VCS_REPOSITORY_URL
REM #---------------------------------------
echo.
echo.Replace "%I_VCS_BASE_TYPE%_VCS_REPOSITORY_URL" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set %I_VCS_BASE_TYPE%_VCS_REPOSITORY_URL=
set I_REPLACE_TEXT=set %I_VCS_BASE_TYPE%_VCS_REPOSITORY_URL=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_VCS_REPOSITORY_URL!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # Set [SVN,TFS,GIT,P4,CVS]_VCS_PROJECT_ROOT
REM #---------------------------------------
echo.
echo.Replace "%I_VCS_BASE_TYPE%_VCS_PROJECT_ROOT" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set %I_VCS_BASE_TYPE%_VCS_PROJECT_ROOT=
set I_REPLACE_TEXT=set %I_VCS_BASE_TYPE%_VCS_PROJECT_ROOT=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_VCS_PROJECT_ROOT!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # Set [SVN,TFS,GIT,P4,CVS]_VCS_WORKSPACE_HOME
REM #---------------------------------------
echo.
echo.Replace "%I_VCS_BASE_TYPE%_VCS_WORKSPACE_HOME" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set %I_VCS_BASE_TYPE%_VCS_WORKSPACE_HOME=
set I_REPLACE_TEXT=set %I_VCS_BASE_TYPE%_VCS_WORKSPACE_HOME=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_VCS_WORKSPACE_HOME!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # Set [SVN,TFS,GIT,P4,CVS]_VCS_WORKSPACE_NAME
REM #---------------------------------------
echo.
echo.Replace "%I_VCS_BASE_TYPE%_VCS_WORKSPACE_NAME" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set %I_VCS_BASE_TYPE%_VCS_WORKSPACE_NAME=
set I_REPLACE_TEXT=set %I_VCS_BASE_TYPE%_VCS_WORKSPACE_NAME=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_VCS_WORKSPACE_NAME!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # Set [SVN,TFS,GIT,P4,CVS]_VCS_TEMP_DIR
REM #---------------------------------------
echo.
echo.Replace "%I_VCS_BASE_TYPE%_VCS_TEMP_DIR" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set %I_VCS_BASE_TYPE%_VCS_TEMP_DIR=
set I_REPLACE_TEXT=set %I_VCS_BASE_TYPE%_VCS_TEMP_DIR=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_VCS_TEMP_DIR!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # Set [SVN,TFS,GIT,P4,CVS]_VCS_USERNAME
REM #---------------------------------------
REM # Trim the domain before using it
SETLOCAL ENABLEDELAYEDEXPANSION
CALL:LTRIM I_VCS_DOMAIN I_VCS_DOMAIN
CALL:RTRIM I_VCS_DOMAIN I_VCS_DOMAIN
ENDLOCAL& set I_VCS_DOMAIN=%I_VCS_DOMAIN%
echo.
echo.Replace "%I_VCS_BASE_TYPE%_VCS_USERNAME" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set %I_VCS_BASE_TYPE%_VCS_USERNAME=
set I_REPLACE_TEXT=set %I_VCS_BASE_TYPE%_VCS_USERNAME=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_VCS_USERNAME!!I_VCS_DOMAIN!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # Set [SVN,TFS,GIT,P4,CVS]_VCS_PASSWORD
REM #---------------------------------------
echo.
echo.Replace "%I_VCS_BASE_TYPE%_VCS_PASSWORD" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set %I_VCS_BASE_TYPE%_VCS_PASSWORD=
set I_REPLACE_TEXT=set %I_VCS_BASE_TYPE%_VCS_PASSWORD=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_VCS_PASSWORD!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_PR_VCS_PASSWORD!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # Set VCS_EDITOR
REM #---------------------------------------
echo.
echo.Replace "VCS_EDITOR" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set VCS_EDITOR=
set I_REPLACE_TEXT=set VCS_EDITOR=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_VCS_EDITOR!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # Set [SVN,TFS,GIT,P4,CVS]_VALID_ENV_CONFIG_PAIRS
REM #---------------------------------------
echo.
echo.Replace "%I_VCS_BASE_TYPE%_VALID_ENV_CONFIG_PAIRS" in %MODIFY_SET_MY_PRE_VARS%
echo.
set    I_FIND_TEXT=set %I_VCS_BASE_TYPE%_VALID_ENV_CONFIG_PAIRS=
set I_REPLACE_TEXT=set %I_VCS_BASE_TYPE%_VALID_ENV_CONFIG_PAIRS=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_VALID_ENV_CONFIG_PAIRS!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR1

REM #---------------------------------------
REM # Continue with setup
REM #---------------------------------------
GOTO INSTALLER_MODIFY_SET_VARS
:INSTALLER_REPLACE_ERROR1
   echo.
   echo.################################################################################################
   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
   echo.#
   echo.# replaceText %MODIFY_SET_MY_PRE_VARS% - Command Failed with error=%ERROR%
   echo.#
   echo.################################################################################################
   GOTO ERROR_EXIT_SCRIPT

:INSTALLER_MODIFY_SET_VARS
echo.
echo.
echo.################################################################################################
echo.#
echo.# EXECUTE: Replace text in \bin\%MODIFY_SET_VARS%
echo.#
echo.################################################################################################
REM #---------------------------------------
REM # set the setVars.bat - MY_VARS_HOME
REM #---------------------------------------
echo.
echo.Replace "MY_VARS_HOME" in %MODIFY_SET_VARS%
echo.
set    I_FIND_TEXT=set MY_VARS_HOME=
set I_REPLACE_TEXT=set MY_VARS_HOME=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_PDTOOL_DESTINATION_HOME!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_HOME%\bin\%MODIFY_SET_VARS%" "%I_PDTOOL_HOME%\bin\%MODIFY_SET_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_HOME%\bin\%MODIFY_SET_VARS%" "%I_PDTOOL_HOME%\bin\%MODIFY_SET_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR2

GOTO INSTALLER_MODIFY_ATF_SET_VARS

:INSTALLER_REPLACE_ERROR2
   echo.
   echo.################################################################################################
   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
   echo.#
   echo.# replaceText \bin\%MODIFY_SET_VARS% - Command Failed with error=%ERROR%
   echo.#
   echo.################################################################################################
   GOTO ERROR_EXIT_SCRIPT

:INSTALLER_MODIFY_ATF_SET_VARS
if not exist "%I_PDTOOL_HOME%\AutomatedTestFramework\regression\bin\%MODIFY_ATF_SET_VARS%" goto INSTALLER_CONTINUE_SCRIPT
echo.
echo.
echo.################################################################################################
echo.#
echo.# EXECUTE: Replace text in \AutomatedTestFramework\regression\bin\%MODIFY_ATF_SET_VARS%
echo.#
echo.################################################################################################
REM #---------------------------------------
REM # set the ATF setVars.bat - VALID_ENV_CONFIG_PAIRS_#
REM #---------------------------------------
set VAID_PAIR_NAME=VALID_ENV_CONFIG_PAIRS_%I_BASE_CIS_VERSION%
echo.
echo.Replace "VALID_ENV_CONFIG_PAIRS_%I_BASE_CIS_VERSION%" in %MODIFY_ATF_SET_VARS%
echo.
set    I_FIND_TEXT=set VALID_ENV_CONFIG_PAIRS_%I_BASE_CIS_VERSION%=
set I_REPLACE_TEXT=set VALID_ENV_CONFIG_PAIRS_%I_BASE_CIS_VERSION%=
setlocal enabledelayedexpansion
set I_REPLACE_VALUE=!I_VALID_ENV_CONFIG_PAIRS!
echo.COMMAND: CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_HOME%\AutomatedTestFramework\regression\bin\%MODIFY_ATF_SET_VARS%" "%I_PDTOOL_HOME%\AutomatedTestFramework\regression\bin\%MODIFY_ATF_SET_VARS%" "%I_FIND_TEXT%" "%I_REPLACE_TEXT%!I_REPLACE_VALUE!"
              CALL:ParseLineReplaceText %debugReplaceText% "%I_PDTOOL_HOME%\AutomatedTestFramework\regression\bin\%MODIFY_ATF_SET_VARS%" "%I_PDTOOL_HOME%\AutomatedTestFramework\regression\bin\%MODIFY_ATF_SET_VARS%" "%I_FIND_TEXT%"
set ERROR=%ERRORLEVEL%
endlocal& set ERROR=%ERROR%
if %ERROR% NEQ 0 GOTO INSTALLER_REPLACE_ERROR3

GOTO INSTALLER_CONTINUE_SCRIPT

:INSTALLER_REPLACE_ERROR3
   echo.
   echo.################################################################################################
   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
   echo.#
   echo.# replaceText \AutomatedTestFramework\regression\bin\%MODIFY_ATF_SET_VARS% - Command Failed with error=%ERROR%
   echo.#
   echo.################################################################################################
   GOTO ERROR_EXIT_SCRIPT


:INSTALLER_CONTINUE_SCRIPT
set PDTOOL_EXEC_OPTIONS=
if "%I_PDTOOL_INSTALL_TYPE%"=="PDToolStudio" set PDTOOL_EXEC_OPTIONS=-nopause
echo.
echo.
echo.################################################################################################
echo.#
echo.# EXECUTE: Encrypt %MODIFY_SET_MY_PRE_VARS%
echo.#
echo.#==============================================================================================
echo.#
echo.# %I_PDTOOL_HOME_DRIVE%
echo.# cd "%I_PDTOOL_HOME%\bin"
echo.#
echo.# COMMAND: call %PDTOOL_EXEC_SCRIPT% %PDTOOL_EXEC_OPTIONS% -encrypt "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%"
echo.#
echo.################################################################################################
echo.
REM Change drives
%I_PDTOOL_HOME_DRIVE%
REM Change directories
cd "%I_PDTOOL_HOME%\bin"
REM Execute PDTool batch file
call %PDTOOL_EXEC_SCRIPT% %PDTOOL_EXEC_OPTIONS% -encrypt "%I_PDTOOL_DESTINATION_HOME%\%MODIFY_SET_MY_PRE_VARS%"
set ERROR=%ERRORLEVEL%
if %ERROR% NEQ 0 (
   echo.
   echo.################################################################################################
   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
   echo.#
   echo.# %PDTOOL_EXEC_SCRIPT% - Command Failed with error=%ERROR%
   echo.#
   echo.################################################################################################
   GOTO ERROR_EXIT_SCRIPT
)


REM ####################################################################
REM #
REM # Determine whether to initialize VCS or not
REM #
REM ####################################################################
if "%I_CONFIGURE_VCS%" == "N" goto INSTALLER_BYPASS_CONFIGURE_VCS


REM ####################################################################
REM #
REM # PDToolStudio Enable Studio VCS Property File
REM #
REM ####################################################################
if "%I_PDTOOL_INSTALL_TYPE%"=="PDTool" goto INSTALLER_PDTOOLSTUDIO_VCS_PROPERTY_FILE_BYPASS
echo.
echo.
echo.################################################################################################
echo.#
echo.# EXECUTE: %I_PDTOOL_INSTALL_TYPE% Create "Enable VCS" Property File
echo.#
echo.#==============================================================================================
echo.#
echo.# COMMAND: call %PDTOOL_EXEC_SCRIPT% -nopause -enablevcs -winlogin %I_WINDOWS_USER% -user %I_CIS_USERNAME% -domain %I_CIS_DOMAIN% -host %I_CIS_HOSTNAME% -includeResourceSecurity true -vcsWorkspacePathOverride "%I_PDTOOL_WKS%"
echo.#
echo.################################################################################################
echo.
c:
cd "%PDTOOL_HOME%\bin"
call %PDTOOL_EXEC_SCRIPT% -nopause -enablevcs -winlogin %I_WINDOWS_USER% -user %I_CIS_USERNAME% -domain %I_CIS_DOMAIN% -host %I_CIS_HOSTNAME% -includeResourceSecurity true -vcsWorkspacePathOverride "%I_PDTOOL_WKS%"
set ERROR=%ERRORLEVEL%
if %ERROR% NEQ 0 (
   echo.
   echo.################################################################################################
   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
   echo.#
   echo.# %PDTOOL_EXEC_SCRIPT% - Command Failed with error=%ERROR%
   echo.#
   echo.################################################################################################
   GOTO ERROR_EXIT_SCRIPT
)
:INSTALLER_PDTOOLSTUDIO_VCS_PROPERTY_FILE_BYPASS


REM ####################################################################
REM #
REM # Remove workspace directory
REM #
REM ####################################################################
echo.
echo.
echo.################################################################################################
echo.#
echo.# EXECUTE: Remove VCS Workspace directory
echo.#
echo.#==============================================================================================
echo.#
if exist "%I_PDTOOL_WKS_HOME%"  echo.# COMMAND: rmdir /S /Q "%I_PDTOOL_WKS_HOME%"
if not exist "%I_PDTOOL_WKS_HOME%"  echo.# COMMAND: No command executed due to directory does not exist.
echo.#
echo.################################################################################################
echo.
set ERROR=0
if not exist "%I_PDTOOL_WKS_HOME%" GOTO REMOVE_WORKSPACE_CONTINUE
	if exist "%I_PDTOOL_WKS%" rmdir /S /Q "%I_PDTOOL_WKS%"
	rmdir /S /Q "%I_PDTOOL_WKS_HOME%"
	set ERROR=%ERRORLEVEL%

:REMOVE_WORKSPACE_CONTINUE
if %ERROR% NEQ 0 (
   echo.
   echo.################################################################################################
   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
   echo.#
   echo.# rmdir - Command Failed with error=%ERROR%
   echo.#
   echo.################################################################################################
   GOTO ERROR_EXIT_SCRIPT
)

if not defined TFS_HOME goto SETUP_INITIALIZE_WORKSPACE
  echo.
  echo.
  echo.################################################################################################
  echo.#
  echo.# EXECUTE: Accept TFS End User License
  echo.#
  echo.#==============================================================================================
  echo.#
  if exist "%TFS_HOME%\tf.cmd" GOTO TFS_EULA_CONTINUE
     echo.
     echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
     echo.#
     echo.# %PDTOOL_EXEC_SCRIPT% - Command does not exist: 
	 echo.#     "%TFS_HOME%\tf.cmd"
     echo.#
     echo.################################################################################################
	 GOTO ERROR_EXIT_SCRIPT

  :TFS_EULA_CONTINUE
  echo.call "%TFS_HOME%\tf.cmd" eula /accept
  call "%TFS_HOME%\tf.cmd" eula /accept
  echo.#
  echo.################################################################################################
  echo.


REM ####################################################################
REM #
REM # PDTool Workspace Initialization
REM #
REM ####################################################################
:SETUP_INITIALIZE_WORKSPACE
if "%I_PDTOOL_INSTALL_TYPE%"=="PDToolStudio" goto INSTALLER_PDTOOL_WORKSPACE_INIT_BYPASS
echo.
echo.
echo.################################################################################################
echo.#
echo.# EXECUTE: Initialize the %I_PDTOOL_INSTALL_TYPE% VCS Workspace
echo.#
echo.#==============================================================================================
echo.#
echo.# %I_PDTOOL_HOME_DRIVE%
echo.# cd "%I_PDTOOL_HOME%\bin"
echo.# COMMAND: call %PDTOOL_EXEC_SCRIPT% -exec "%I_PDTOOL_HOME%\resources\plans\%INIT_DEPLOYMENT_PLAN%" -release "%I_RELEASE_FOLDER%" -config %I_CONFIG_PROPERTY_FILE%
echo.#
echo.################################################################################################
REM Change drives
%I_PDTOOL_HOME_DRIVE%
REM Change directories
cd "%I_PDTOOL_HOME%\bin"
if exist "%I_PDTOOL_HOME%\resources\plans\%INIT_DEPLOYMENT_PLAN%" GOTO SETUP_INITIALIZE_WORKSPACE_CONTINUE
   echo.
   echo.################################################################################################
   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
   echo.#
   echo.# The deployment plan does not exist: 
   echo.#       %I_PDTOOL_HOME%\resources\plans\%INIT_DEPLOYMENT_PLAN%
   echo.#
   echo.################################################################################################
   GOTO ERROR_EXIT_SCRIPT


REM Execute PDTool batch file
:SETUP_INITIALIZE_WORKSPACE_CONTINUE
call "%PDTOOL_EXEC_SCRIPT%" -exec "%I_PDTOOL_HOME%\resources\plans\%INIT_DEPLOYMENT_PLAN%" -release "%I_RELEASE_FOLDER%" -config %I_CONFIG_PROPERTY_FILE%
set ERROR=%ERRORLEVEL%
if %ERROR% NEQ 0 (
   echo.
   echo.################################################################################################
   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
   echo.#
   echo.# %PDTOOL_EXEC_SCRIPT% - Command Failed with error=%ERROR%
   echo.#
   echo.################################################################################################
   GOTO ERROR_EXIT_SCRIPT
)
:INSTALLER_PDTOOL_WORKSPACE_INIT_BYPASS


REM ####################################################################
REM #
REM # PDToolStudio Workspace Initialization
REM #
REM ####################################################################
if "%I_PDTOOL_INSTALL_TYPE%"=="PDTool" goto INSTALLER_PDTOOLSTUDIO_WORKSPACE_INIT_BYPASS
echo.
echo.
echo.################################################################################################
echo.#
echo.# EXECUTE: Initialize the %I_PDTOOL_INSTALL_TYPE% VCS Workspace
echo.#
echo.#==============================================================================================
echo.#
echo.# %I_PDTOOL_HOME_DRIVE%
echo.# cd "%I_PDTOOL_HOME%\bin"
echo.# COMMAND: call "%PDTOOL_EXEC_SCRIPT%" -nopause -vcsinit
echo.#
echo.################################################################################################
echo.
REM Change drives
%I_PDTOOL_HOME_DRIVE%
REM Change directories
cd "%I_PDTOOL_HOME%\bin"
REM Execute PDTool batch file
call "%PDTOOL_EXEC_SCRIPT%" -nopause -vcsinit
set ERROR=%ERRORLEVEL%
if %ERROR% NEQ 0 (
   echo.
   echo.################################################################################################
   echo.# ERROR: %I_PDTOOL_INSTALL_TYPE% INSTALLATION FAILED
   echo.#
   echo.# %PDTOOL_EXEC_SCRIPT% - Command Failed with error=%ERROR%
   echo.#
   echo.################################################################################################
   GOTO ERROR_EXIT_SCRIPT
)
:INSTALLER_PDTOOLSTUDIO_WORKSPACE_INIT_BYPASS


:INSTALLER_BYPASS_CONFIGURE_VCS
ENDLOCAL
REM ############################################
REM # END OF MAIN SCRIPT
REM ############################################
GOTO SUCCESS_EXIT_SCRIPT

REM ############################################
REM # Error Notification
REM ############################################
:ERROR_EXIT_SCRIPT
set ERROR=1
GOTO POST_PROCESS

REM ############################################
REM # License Notification
REM ############################################
:LICENSE_EXIT_SCRIPT
echo.
echo.The license has not been acknowledged therefore PDTool cannot be installed. Exiting.
echo.
set ERROR=0
GOTO POST_PROCESS

REM ############################################
REM # Success Notification
REM ############################################
:SUCCESS_EXIT_SCRIPT
echo.
echo.
echo.################################################################################################
echo.#
echo.# PDTOOL INSTALLATION COMPLETED SUCCESSFULLY
echo.#
echo.################################################################################################
set ERROR=0
GOTO POST_PROCESS

REM ############################################
REM # Post-process Initialization
REM ############################################
:POST_PROCESS
REM # Pop the saved directory off the stack
popd
REM # Initialize the variables
CALL :InitVariables
exit /b %ERROR%


REM ####################################################################
REM #
REM # Functions
REM #
REM ####################################################################

::#--------------------------------------------------------------------
:LCase
:UCase
::#--------------------------------------------------------------------
::# Converts to upper/lower case variable contents
::# Syntax: CALL :UCase _VAR1 _VAR2
::# Syntax: CALL :LCase _VAR1 _VAR2
::#   _VAR1 = Variable NAME whose VALUE is to be converted to upper/lower case
::#   _VAR2 = NAME of variable to hold the converted value
::# Note: Use variable NAMES in the CALL, not values (pass "by reference")
::#####################################################################
SET _UCase=A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
SET _LCase=a b c d e f g h i j k l m n o p q r s t u v w x y z
SET _Lib_UCase_Tmp=!%1!
IF /I "%0"==":UCase" SET _Abet=%_UCase%
IF /I "%0"==":LCase" SET _Abet=%_LCase%
FOR %%Z IN (%_Abet%) DO SET _Lib_UCase_Tmp=!_Lib_UCase_Tmp:%%Z=%%Z!
SET %2=%_Lib_UCase_Tmp%
GOTO:EOF

::#--------------------------------------------------------------------
:resolveVariableDelayedExpansion
::#--------------------------------------------------------------------
::# Resolve delayed variable expansion for variables containing double percent signs around the variable
::# call:resolveVariableDelayedExpansion %VAR% result
::#####################################################################
SETLOCAL ENABLEDELAYEDEXPANSION
set VAR=%1
rem echo resolveVariableDelayedExpansion.VAR=!VAR!
set result=!VAR!
ENDLOCAL &set result=%result%
rem echo resolveVariableDelayedExpansion.result=%result%
set %2=%result%
GOTO:EOF


::#--------------------------------------------------------------------
:MapNetworkDrive
::#--------------------------------------------------------------------
::# This section creates a substitute drive using "net use"
::# The advantage over "subst" is that this is permanent while
::#   "subst" is temporary in that id does not survive reboots. 
::#####################################################################
set PDTOOL_HOME_DRIVE=%1
set PDTOOL_HOME_DRIVE_SHARE=%2
set NETWORK_DRIVE=%3
set PDTOOL_HOME_PHYSICAL=%4
SETLOCAL ENABLEDELAYEDEXPANSION
REM Remove double quotes around path
set PDTOOL_HOME_DRIVE=!PDTOOL_HOME_DRIVE:"=!
set PDTOOL_HOME_DRIVE_SHARE=!PDTOOL_HOME_DRIVE_SHARE:"=!
set NETWORK_DRIVE=!NETWORK_DRIVE:"=!
set PDTOOL_HOME_PHYSICAL=!PDTOOL_HOME_PHYSICAL:"=!
set ERROR=0
set PAD=         
if not defined NETWORK_DRIVE goto MAP_NETWORK_DRIVE_END
	echo.
	echo.    Section: Network Drives
	echo.%PAD%Evaluate the network drive=!NETWORK_DRIVE! path=!PDTOOL_HOME_PHYSICAL!
	echo.
	REM Only map the drive when necessary...not every time
	if NOT EXIST "!NETWORK_DRIVE!" goto MAP_NETWORK_DRIVE
	REM Evaluate if current mapped drive utilizes the correct path
	if "%debug%"=="1" echo.[DEBUG] COMMAND: call:EVALUATE_NETWORK_DRIVES "!PDTOOL_HOME_DRIVE!" "!PDTOOL_HOME_DRIVE_SHARE!" "!NETWORK_DRIVE!" "!PDTOOL_HOME_PHYSICAL!" %debug% drivePathFound
	if "%debug%"=="1" echo.
	set drivePathFound=false
	call:EVALUATE_NETWORK_DRIVES "!PDTOOL_HOME_DRIVE!" "!PDTOOL_HOME_DRIVE_SHARE!" "!NETWORK_DRIVE!" "!PDTOOL_HOME_PHYSICAL!" %debug% drivePathFound

	if "%debug%"=="1" echo.[DEBUG] drivePathFound=!drivePathFound!
	if "!drivePathFound!" == "true" (
		echo.%PAD%Network drive "!NETWORK_DRIVE!" is already mapped to path:!PDTOOL_HOME_PHYSICAL!
		goto MAP_NETWORK_DRIVE_END
	)
	REM The drive is already mapped to a different path so exit with an error and have the user unmap the drive manually
	echo.%PAD%The network drive=!NETWORK_DRIVE! is mapped to a different path.  There are two options:
	echo.%PAD%  Option 1: Change the drive letter designated by the variable NETWORK_DRIVE in setVars.bat.
	echo.%PAD%  Option 2: Unmap the drive by executing this command manually: net use !NETWORK_DRIVE! /DELETE
	echo.%PAD%Re-execute the installation script.
    set ERROR=1
	goto MAP_NETWORK_DRIVE_END
	
:MAP_NETWORK_DRIVE
	if not exist "\\%COMPUTERNAME%\!PDTOOL_HOME_DRIVE_SHARE!" (
		REM The share drive does not exist
		echo.%PAD%The network share "\\%COMPUTERNAME%\!PDTOOL_HOME_DRIVE_SHARE!" does not exist.
		echo.%PAD%   Create the share drive=!PDTOOL_HOME_DRIVE_SHARE! for the installation drive=!PDTOOL_HOME_DRIVE! as administrator.
		echo.%PAD%   Command run as administrator: net share !PDTOOL_HOME_DRIVE_SHARE!=!PDTOOL_HOME_DRIVE!\
		echo.%PAD%Re-execute the installation script.
		set ERROR=1
		goto MAP_NETWORK_DRIVE_END
	)
	
	if "%debug%"=="1" echo.[DEBUG] call :replace "!PDTOOL_HOME_DRIVE!" "\\%COMPUTERNAME%\!PDTOOL_HOME_DRIVE_SHARE!" "!PDTOOL_HOME_PHYSICAL!" PDTOOL_HOME_PHYSICAL_NEW

	call:replace "!PDTOOL_HOME_DRIVE!" "\\%COMPUTERNAME%\!PDTOOL_HOME_DRIVE_SHARE!" "!PDTOOL_HOME_PHYSICAL!" PDTOOL_HOME_PHYSICAL_NEW
	echo.%PAD%Execute network drive command:  net use "!NETWORK_DRIVE!" "%PDTOOL_HOME_PHYSICAL_NEW%" /PERSISTENT:YES
    net use "!NETWORK_DRIVE!" "%PDTOOL_HOME_PHYSICAL_NEW%" /PERSISTENT:YES
    set ERROR=%ERRORLEVEL%
	if "%debug%"=="1" pause
	if "%ERROR%" == "0" goto MAP_NETWORK_DRIVE_END
	echo.%PAD%An error=%ERROR% occurred executing command: net use "!NETWORK_DRIVE!" "%PDTOOL_HOME_PHYSICAL_NEW%" /PERSISTENT:YES
	rem echo.%PAD%Execute this command manually: net use %NETWORK_DRIVE% %PDTOOL_HOME_PHYSICAL_NEW% /PERSISTENT:YES
	rem echo.%PAD%Re-execute the installation script.
:MAP_NETWORK_DRIVE_END
ENDLOCAL &set ERROR=%ERROR%
set %5=%ERROR%
REM # Initialize
set PDTOOL_HOME_DRIVE=
set PDTOOL_HOME_DRIVE_SHARE=
set NETWORK_DRIVE=
set PDTOOL_HOME_PHYSICAL=
GOTO:EOF


::#----------------------------------------------------------
:replace
::#----------------------------------------------------------
::# replace - parses a string and replaces old string with new string
::#           and returns the value in the outvariable that gets passed in
::# syntax:  call :replace "oldstring" "newstring" "searchstring" outvariable
::# example: call :replace "_" "__" "%searchStr%" outStr 
::#    oldStr [in] - string to be replaced
::#    newStr [in] - string to replace with
::#    searchStr [in] - String to search
::#    outvar [out] - name of the variable to place the results
::#
::# Remove double quotes (") for incoming SearchStr argument
::#####################################################################
set oldStr=%1
set newStr=%2
set searchStr=%3
SETLOCAL EnableDelayedExpansion
set oldStr=!oldStr:"=!
set newStr=!newStr:"=!
set searchStr=!searchStr:"=!

if "%debug%"=="1" echo.[DEBUG] replace: oldStr=!oldStr!
if "%debug%"=="1" echo.[DEBUG] replace: newStr=!newStr!
if "%debug%"=="1" echo.[DEBUG] replace: searchStr=!searchStr!

REM # Replace old string with new string within search string
if "!oldStr!"=="" findstr "^::" "%~f0"&GOTO:EOF
   for /f "tokens=1,* delims=]" %%A in ('"echo.%searchStr%|find /n /v """') do (
   set "line=%%B"
   if defined line (
      call set "outvar=%%line:%oldStr%=%newStr%%%"
   ) ELSE (
      call set "outvar="
   )
)
ENDLOCAL& set outvar=%outvar%
set %4=%outvar%
GOTO:EOF


::#--------------------------------------------------------------------
:FindAvailableDrive
::#--------------------------------------------------------------------
::# Find the next available drive letter for SUBST to use
::# Syntax CALL :FindAvailableDrive PDTOOL_SUBSTITUTE_DRIVE
::#####################################################################
set _PDTOOL_SUBSTITUTE_DRIVE=
set _NetworkDrivesError=%1
if "%PDTOOL_SUBSTITUTE_DRIVE_LIST%"=="" goto FIND_AVAILABLE_DRIVE_END
SETLOCAL ENABLEDELAYEDEXPANSION
set _NetworkDrivesError=!_NetworkDrivesError:"=!
set NetworkDrivesInUse=!_NetworkDrivesError!
echo.
echo.
echo.################################################################################################
echo.#
echo.# EXECUTE:  Find the next available drive letter for SUBST or NET USE to use
echo.#
echo.#==============================================================================================
echo.#
REM # Find all of the network drives in use
for /F "tokens=2 skip=2" %%d IN ('net use') do (
   set drive=%%d
rem echo drive=!drive!
   if "!drive!"=="Local" set drive=
   if "!drive!"=="Windows" set drive=
   if "!drive!"=="command" set drive=
   if "!drive!"=="are" set drive=
   if "!drive!"=="connections" set drive=
   if defined drive set NetworkDrivesInUse=!NetworkDrivesInUse!!drive! 
)
echo.# NetworkDrivesInUse=!NetworkDrivesInUse!
echo.#
REM # Find all  of the local drives in use and cross-reference with network drives
REM #  to come up with the list of available drives.
REM #  Start the availalbe list with I:
set AvailableDrives=
set LocalNetworkDrivesInUse=
REM # Loop through the list of drives to find an available drive
for %%a IN (%PDTOOL_SUBSTITUTE_DRIVE_LIST%) do (
    set useDrive=1
    if exist %%a set useDrive=0
    for %%d IN (!NetworkDrivesInUse!) do (
	  if "%%a"=="%%d" set useDrive=0
	)
	for /F "tokens=1 delims=:" %%e IN ('subst') do (
	  set sdrive=%%e:
	  if "%%a"=="!sdrive!" set useDrive=0
	)
	if "!useDrive!"=="0" set LocalNetworkDrivesInUse=!LocalNetworkDrivesInUse!%%a 
	if "!useDrive!"=="1" set AvailableDrives=!AvailableDrives!%%a 
)
echo.# LocalNetworkDrivesInUse=%LocalNetworkDrivesInUse% 
echo.#
echo.# AvailableDrives=%AvailableDrives% 
echo.#
REM # Use the first available drive from the available list.
set _PDTOOL_SUBSTITUTE_DRIVE=
for %%a in (!AvailableDrives!) do (
   if not defined _PDTOOL_SUBSTITUTE_DRIVE set _PDTOOL_SUBSTITUTE_DRIVE=%%a
)
echo.# Assigned PDTOOL_SUBSTITUTE_DRIVE=%_PDTOOL_SUBSTITUTE_DRIVE%
echo.#
echo.################################################################################################
ENDLOCAL &set _PDTOOL_SUBSTITUTE_DRIVE=%_PDTOOL_SUBSTITUTE_DRIVE%
:FIND_AVAILABLE_DRIVE_END
SET %2=%_PDTOOL_SUBSTITUTE_DRIVE%
GOTO:EOF


::#-------------------------------------------------------------
:EVALUATE_NETWORK_DRIVES
::#-------------------------------------------------------------
::# Evaluate the network drives
::#
::# Description: call:EVALUATE_NETWORK_DRIVES homeDrive homeDriveShare destDrive destPath debug pathFound  
::#      -- homeDrive [in] - The drive letter representing the actual path to the pdtool installation folder
::#      -- homeDriveShare [in] - The share name representing the homeDrive.  This is how the drive is mapped for a network share.
::#      -- destDrive [in]  - The drive letter such as P: that is to be used for a network drive
::#      -- destPath  [in]  - The path that is to be mapped to the network drive.
::#      -- debug      [in]  - 1=print debug, 0=do not pring debug
::#      -- pathFound  [out] - lower case "true" or "false" indicating whether the network drive and path combination was found in the network drive list.
::#####################################################################
REM Get the input parameters
set homeDrive=%1
set homeDriveShare=%2
set destDrive=%3
set destPath=%4
set debug=%5
REM Set default variables
set trim=1
set pathfound=false

SETLOCAL ENABLEDELAYEDEXPANSION
REM Remove double quotes around path
set homeDrive=!homeDrive:"=!
set homeDriveShare=!homeDriveShare:"=!
set destDrive=!destDrive:"=!
set destPath=!destPath:"=!
set debug=!debug:"=!

if "%debug%"=="1" echo.[DEBUG] %0: param1      homeDrive=!homeDrive!
if "%debug%"=="1" echo.[DEBUG] %0: param2 homeDriveShare=!homeDriveShare!
if "%debug%"=="1" echo.[DEBUG] %0: param3      destDrive=!destDrive!
if "%debug%"=="1" echo.[DEBUG] %0: param4       destPath=!destPath!
if "%debug%"=="1" echo.[DEBUG] %0: param5          debug=!debug!

REM Iterate over the net use command
for /F "tokens=2,3* skip=2" %%a IN ('net use') do (
	set pathfound=false
	set sdrive=%%a
	set spath=%%b
	set spathRemainder=%%c
	call:RTRIM spathRemainder spathRemainder
	if "!spathRemainder!" NEQ "" set spath=!spath! !spathRemainder!
	if "!sdrive!"=="Local" set sdrive=
	if "!sdrive!"=="Windows" set sdrive=
	if "!sdrive!"=="command" set sdrive=
	if "!sdrive!"=="are" set sdrive=
	if "!sdrive!"=="connections" set sdrive=
	if not defined sdrive set spath=
	if defined sdrive call :EVALUATE_NETWORK_DRIVES_LOGIC pathfound
	if !debug!==1 echo.[DEBUG] %0:             pathfound=[!pathfound!]
	if "!pathfound!"=="true" goto EVALUATE_NETWORK_DRIVES_LOOP_END
)
:EVALUATE_NETWORK_DRIVES_LOOP_END
ENDLOCAL & SET pathfound=%pathfound%
if "%debug%"=="1" echo.[DEBUG] %0: return parameter [%6=%pathfound%]
set %6=%pathfound%
GOTO:EOF

::#--------------------------------------------------------------------
:EVALUATE_NETWORK_DRIVES_LOGIC
::#--------------------------------------------------------------------
::# Sub-procedure to EVALUATE_NETWORK_DRIVES
::#####################################################################
	set pathfound=false

	if !debug!==1 echo.[DEBUG] %0: network: drive=[!sdrive!]   path=[!spath!]
	if !debug!==1 echo.[DEBUG] %0: call :replace "\\%COMPUTERNAME%\!homeDriveShare!" "!homeDrive!" "!spath!" spath
	call :replace "\\%COMPUTERNAME%\!homeDriveShare!" "!homeDrive!" "!spath!" spath
	if !debug!==1 echo.[DEBUG] %0: replaced network path=!spath!

	REM The drive was found
	if "!destDrive!" NEQ "!sdrive!" goto EVALUATE_NETWORK_DRIVES_LOOP_CONTINUE
	
	if !debug!==1 echo.[DEBUG] %0:   network drive found=[!sdrive!]
	if !debug!==1 echo.[DEBUG] %0:    network drive path=[!spath!]
	if !debug!==1 echo.[DEBUG] %0: Parameter:   destPath=[!destPath!]
	if "!destPath!" NEQ "!spath!" echo.%PAD%The network path does not match subst drive list. dest drive=[!destDrive!]  dest path=[!spath!].
	if "!destPath!" EQU "!spath!" set pathfound=true

:EVALUATE_NETWORK_DRIVES_LOOP_CONTINUE
if !debug!==1 echo.[DEBUG] %0:             pathfound=[%pathfound%]
set %1=%pathfound%
GOTO:EOF


::#--------------------------------------------------------------------
:escStr
::#--------------------------------------------------------------------
::# Adds %% to a string to escape a variable
::# Description: call:escStr debug string string
::#  e.g. call:escStr %debug% I_JAVA_HOME I_JAVA_HOME
::#  -- debug  [in] - 1=debug, 0=no debug
::#  -- string [in]  - variable name containing the string being escaped
::#  -- string [out] - variable to be used to return the escaped string
::#####################################################################
	set debug=%1
(   
	SETLOCAL ENABLEDELAYEDEXPANSION
    set "str=!%~2!"
	call :strLen str len
	if "%debug%"=="1" echo.[DEBUG] %0: str=len=!len!   %2="!str!"
	set /a len-=1
	for /l %%a in (0,1,!len!) do if "!str:~%%a,1!"=="%%" (
			if "%debug%"=="1" echo.[DEBUG] %0: char=!str:~%%a,1!%%
			set str2=!str2!!str:~%%a,1!%%
		) else (
			if "%debug%"=="1" echo.[DEBUG] %0: char=!str:~%%a,1!
			set str2=!str2!!str:~%%a,1!
		)
)
( ENDLOCAL & REM RETURN VALUES
    IF "%~3" NEQ "" SET %~3="%str2%"
	if "%debug%"=="1" echo.[DEBUG] %0: %3="%str2%"
	if "%debug%"=="1" echo.
)
GOTO:EOF


::#--------------------------------------------------------------------
:strLen string len -- returns the length of a string
::#--------------------------------------------------------------------
::# Description: call:strLen string len  
::#  -- string [in]  - variable name containing the string being measured for length
::#  -- len    [out] - variable to be used to return the string length
::#####################################################################
(   SETLOCAL ENABLEDELAYEDEXPANSION
    set "str=A!%~1!"&rem keep the A up front to ensure we get the length and not the upper bound
                     rem it also avoids trouble in case of empty string
    set "len=0"
    for /L %%A in (12,-1,0) do (
        set /a "len|=1<<%%A"
        for %%B in (!len!) do if "!str:~%%B,1!"=="" set /a "len&=~1<<%%A"
    )
)
( ENDLOCAL & REM RETURN VALUES
    IF "%~2" NEQ "" SET /a %~2=%len%
)
GOTO:EOF


::#--------------------------------------------------------------------
:LPAD string width padCharacter
::#--------------------------------------------------------------------
::# -- pads to the left of a string with specified characters to the width specified
::# -- string [in,out] - string to be left padded
::# -- width [in] - width of resulting string
::# -- character [in,opt] - character to pad with, default is space
::#####################################################################
SETLOCAL ENABLEDELAYEDEXPANSION
call set inStr=%%%~1%%
set width=%2
set /a width-=1
set pad=%3
if "%pad%"=="" set pad= &::space
for /l %%i in (0,1,%width%) do (
   if "!inStr:~%%i,1!"=="" set inStr=%pad%!inStr!
)
( ENDLOCAL & REM -- RETURN VALUES
   IF "%~1" NEQ "" SET "%~1=%inStr%"
)
GOTO:EOF


::#--------------------------------------------------------------------
:RPAD string width padCharacter
::#--------------------------------------------------------------------
::# -- pads to the right of a string with specified characters to the width specified
::# -- string [in,out] - string to be right padded
::# -- width [in] - width of resulting string
::# -- character [in,opt] - character to pad with, default is space
::#####################################################################
SETLOCAL ENABLEDELAYEDEXPANSION
call set inStr=%%%~1%%
set width=%2
set /a width-=1
set pad=%3
if "%pad%"=="" set pad= &::space

for /l %%i in (0,1,%width%) do (
   if "!inStr:~%%i,1!"=="" set inStr=!inStr!%pad%
)
( ENDLOCAL & REM -- RETURN VALUES    
   IF "%~1" NEQ "" SET "%~1=%inStr%"
)
GOTO:EOF


::#-------------------------------------------------------------
:RTRIM
::#-------------------------------------------------------------
::# Trim right
::# Description: call:RTRIM instring outstring  
::#      -- instring  [in]  - variable name containing the string to be trimmed on the right
::#      -- outstring [out] - variable name containing the result string
::#####################################################################
SET str=!%1!
rem echo."%str%"
for /l %%a in (1,1,31) do if "!str:~-1!"==" " set str=!str:~0,-1!
rem echo."%str%"
SET %2=%str%
GOTO:EOF


::#-------------------------------------------------------------
:LTRIM
::#-------------------------------------------------------------
::# Trim left
::# Description: call:LTRIM instring outstring  
::#      -- instring  [in]  - variable name containing the string to be trimmed on the left
::#      -- outstring [out] - variable name containing the result string
::#####################################################################
SET str=!%1!
rem echo."%str%"
for /f "tokens=* delims= " %%a in ("%str%") do set str=%%a
rem echo."%str%"
SET %2=%str%
GOTO:EOF

::#-------------------------------------------------------------
:REMOVE_SEPARATOR
::#-------------------------------------------------------------
::# Remove ending / or \ for VCS_REPOSITORY_URL
::# Description: call:REMOVE_SEPARATOR instr sep outstr  
::#      -- instr  [in]  - the string value to be evaluated for an ending separator
::#      -- sep    [in]  - the separator value such as / or \
::#      -- outstr [out] - variable name containing the result string
::#####################################################################
set instr=%1
set sep=%2
SETLOCAL ENABLEDELAYEDEXPANSION
REM Remove double quotes around path
set instr=!instr:"=!
set sep=!sep:"=!
ENDLOCAL &set instr=%instr%&set sep=%sep%
IF NOT DEFINED sep set sep=/
IF NOT DEFINED instr GOTO REMOVE_SEPARATOR_END

  call:strLen instr LEN
  SET _start=%LEN%
  SET /a _start=_start-1
  SET _len=1
  CALL SET LAST_CHAR=%%instr:~%_start%,%_len%%%
  SET _beg=0
  SET _len=%_start%
  IF "%LAST_CHAR%"=="%sep%" CALL SET instr=%%instr:~%_beg%,%_len%%%
  
:REMOVE_SEPARATOR_END
  SET %3=%instr%
GOTO:EOF


::#--------------------------------------------------------------------
:INCREMENT_BACKUP_FILE_NAME
::#--------------------------------------------------------------------
::# Increment the backup file name
::# Description: call:INCREMENT_BACKUP_FILE_NAME indir inbackupfile outvariable  
::#      -- indir        [in]  - the directory in which to search for files
::#      -- inbackupfile [in]  - the string name of the backup file to look for. A .* is added to the end of the name.
::#      -- outvariable  [out] - the variable name in which to return the new backup file name
::#####################################################################
set TARGET_DIR=%1
set TARGET_FILE=%2
set TARGET_EXT=0
setlocal enabledelayedexpansion
if defined TARGET_DIR set TARGET_DIR=!TARGET_DIR:"=!
if defined TARGET_FILE set TARGET_FILE=!TARGET_FILE:"=!

set TARGET_INCR_FILE=
REM # Check for at least 1 file to exist
if not exist "!TARGET_DIR!\!TARGET_FILE!.1" goto INCREMENT_EXT_NUM
REM # Loop through the files to get the highest backup file number
for /F %%a in ('dir /B "!TARGET_DIR!\!TARGET_FILE!.*"') do (
	set TARGET_INCR_FILE=%%a
	for %%i in ("!TARGET_INCR_FILE!") do set TARGET_EXT=%%~xi
	set TARGET_EXT=!TARGET_EXT:~1!
	if debug==1 [DEBUG] echo INTERNAL: FILE=!TARGET_INCR_FILE!   EXT=!TARGET_EXT!
)
:INCREMENT_EXT_NUM
set /A TARGET_EXT=TARGET_EXT+1
set TARGET_FILE=!TARGET_FILE!.%TARGET_EXT%
if debug==1 echo [DEBUG] EXT=%TARGET_EXT%    TARGET_FILE=%TARGET_FILE%
ENDLOCAL& set TARGET_FILE=%TARGET_FILE%
set %3=%TARGET_FILE%
GOTO:EOF


::#--------------------------------------------------------------------
:ParseLineReplaceText
::#--------------------------------------------------------------------
::# Parse the input file and search for the
::# Description: call:ParseLineReplaceText debug I_REPLACE_INPUT_FILE I_REPLACE_FIND_THIS I_REPLACE_OUTPUT_FILE
::#      -- debugParse             [in] - debug flag. 1=debug, 0=no debug
::#      -- I_REPLACE_INPUT_FILE   [in] - the input file path string
::#      -- I_REPLACE_FIND_THIS    [in] - the string to search for
::#      -- I_REPLACE_OUTPUT_FILE  [in] - the output file path string
::#      -- !I_REPLACE_TEXT!!I_REPLACE_VALUE! 
::#               [environment variable] - the string to replace which is set prior to invoking this function.
::#                This is necessary to retain the original value which may contain values such as "!$%" which
::#                get resolved by the command interpreter and thus the values are lost.
::#####################################################################
REM #=======================================
REM # Assign input variables
REM #=======================================
setlocal ENABLEEXTENSIONS DISABLEDELAYEDEXPANSION
set debugParse=%1
set I_REPLACE_INPUT_FILE=%2
set I_REPLACE_OUTPUT_FILE=%3
set I_REPLACE_FIND_THIS=%4

REM #=======================================
REM # Remove double quotes
REM #=======================================
setlocal enabledelayedexpansion
if defined I_REPLACE_INPUT_FILE  set I_REPLACE_INPUT_FILE=!I_REPLACE_INPUT_FILE:"=!
if defined I_REPLACE_OUTPUT_FILE set I_REPLACE_OUTPUT_FILE=!I_REPLACE_OUTPUT_FILE:"=!
if defined I_REPLACE_FIND_THIS   set I_REPLACE_FIND_THIS=!I_REPLACE_FIND_THIS:"=!
endlocal& set I_REPLACE_INPUT_FILE=%I_REPLACE_INPUT_FILE%& set I_REPLACE_OUTPUT_FILE=%I_REPLACE_OUTPUT_FILE%& set I_REPLACE_FIND_THIS=%I_REPLACE_FIND_THIS%

REM #=======================================
REM # Display input values
REM #=======================================
if %debugParse%==1 echo.[DEBUG]            debugParse=[%debugParse%]
if %debugParse%==1 echo.[DEBUG]  I_REPLACE_INPUT_FILE=[%I_REPLACE_INPUT_FILE%]
if %debugParse%==1 echo.[DEBUG] I_REPLACE_OUTPUT_FILE=[%I_REPLACE_OUTPUT_FILE%]
if %debugParse%==1 echo.[DEBUG]   I_REPLACE_FIND_THIS=[%I_REPLACE_FIND_THIS%]
if %debugParse%==1 echo.[DEBUG]        I_REPLACE_TEXT=[%I_REPLACE_TEXT%]

REM #=======================================
REM # Validate input values
REM #=======================================
if not defined debugParse  (echo USAGE: Parameter 1, "debugParse" was not provided...)&goto :eof
if not defined I_REPLACE_INPUT_FILE   (echo USAGE: Parameter 2, "I_REPLACE_INPUT_FILE" was not provided...)&goto :eof
if not exist "%I_REPLACE_INPUT_FILE%" (echo USAGE: The input file "%I_REPLACE_INPUT_FILE%" file does not exist...)&goto :eof
if not defined I_REPLACE_OUTPUT_FILE  (echo USAGE: Parameter 3, "I_REPLACE_OUTPUT_FILE" was not provided...)&goto :eof
if not defined I_REPLACE_FIND_THIS    (echo USAGE: Parameter 4, "I_REPLACE_FIND_THIS" was not provided...)&goto :eof
if not defined I_REPLACE_TEXT (echo USAGE: Parameter 5, "I_REPLACE_TEXT" was not provided...)&goto :eof

REM #=======================================
REM # Write out file in order to preserve
REM #  blank lines
REM #=======================================
set /A Counter=0
type "%I_REPLACE_INPUT_FILE%" | find /V /N "" > "%I_REPLACE_INPUT_FILE%.tmp"

REM #=======================================
REM # Loop through file
REM #=======================================
for /F "usebackq tokens=* delims=" %%a in ("%I_REPLACE_INPUT_FILE%.tmp") DO (
	set "line=%%a"
	REM # Increment Counter
	set /A Counter+=1 
	setlocal ENABLEEXTENSIONS ENABLEDELAYEDEXPANSION
	REM # Remove the line numbers format [#]
	set "line=!line:*]=!"
	REM # Assign value to variable
	if "!line!"==" " set line=
	set parseFound=0
	if [!line!]==[!I_REPLACE_FIND_THIS!] set parseFound=1
	if %debugParse%==1 ( if !parseFound!==1 echo ****************MATCH FOUND****************)
	if %debugParse%==1 echo.[DEBUG !Counter!][!line!]
	if !parseFound!==1 CALL:ParseLineLoopWriteReplaceLine %debugParse% !Counter! "!I_REPLACE_OUTPUT_FILE!"
	if !parseFound!==0 (if !Counter! equ 1 echo.!line!>"!I_REPLACE_OUTPUT_FILE!")
	if !parseFound!==0 (if !Counter! gtr 1 echo.!line!>>"!I_REPLACE_OUTPUT_FILE!")
	endlocal
)
if exist "%I_REPLACE_INPUT_FILE%.tmp" del "%I_REPLACE_INPUT_FILE%.tmp"
GOTO:EOF

::#--------------------------------------------------------------------
:ParseLineLoopWriteReplaceLine
::#--------------------------------------------------------------------
set debugParse=%1
set Counter=%2
set I_REPLACE_OUTPUT_FILE=%3
if %debugParse%==2 set debugParse=1
if defined I_REPLACE_OUTPUT_FILE  set I_REPLACE_OUTPUT_FILE=!I_REPLACE_OUTPUT_FILE:"=!
if %debugParse%==1 echo.[DEBUG %Counter%]   I_REPLACE_TEXT=%I_REPLACE_TEXT%!I_REPLACE_VALUE!
if %debugParse%==1 echo.[DEBUG %Counter%]   I_REPLACE_OUTPUT_FILE=%I_REPLACE_OUTPUT_FILE% 
if %debugParse%==1 echo.
if %Counter% equ 1 echo.%I_REPLACE_TEXT%!I_REPLACE_VALUE!>"%I_REPLACE_OUTPUT_FILE%"
if %Counter% gtr 1 echo.%I_REPLACE_TEXT%!I_REPLACE_VALUE!>>"%I_REPLACE_OUTPUT_FILE%"
GOTO:EOF


::#--------------------------------------------------------------------
:InitVariables
::#--------------------------------------------------------------------
::# Initialize the variables
::#####################################################################
set I_PDTOOL_SOURCE_FILES=
set I_PDTOOL_INSTALL_HOME=
set I_PDTOOL_INSTALL_SCRIPTS=
set I_JAVA_HOME=
set I_PDTOOL_DESTINATION_HOME=
set I_PDTOOL_DESTINATION_DIR=
set I_OVERWRITE_DECISION=
set I_CONFIGURE_VCS=
set I_VCS_BASE_TYPE=
set I_VCS_HOME=
set I_VCS_REPOSITORY_URL=
set I_VCS_PROJECT_ROOT=
set I_RELEASE_FOLDER=
set I_VCS_WORKSPACE_HOME=
set I_VCS_WORKSPACE_NAME=
set I_VCS_TEMP_DIR=
set I_VCS_USERNAME=
set I_VCS_DOMAIN=
set I_VCS_PASSWORD=
set I_RELEASE_FOLDER=
set I_CIS_USERNAME=
set I_CIS_DOMAIN=
set I_CIS_PASSWORD=
set I_CIS_HOSTNAME=
set I_CONFIG_PROPERTY_FILE=
set I_WINDOWS_USER=
set I_PDTOOL_HOME=
set I_PDTOOL_HOME_DRIVE=
set I_PDTOOL_HOME_DRIVE_SHARE=
set I_PDTOOL_WKS_HOME=
::# Internal variables
set MODIFY_SET_VARS=
set PDTOOL_EXEC_SCRIPT=
set MODIFY_SET_MY_PRE_VARS=
set MODIFY_SET_MY_POST_VARS=
set INSTALLER_SOURCE_PDTOOL_DIR=
set INSTALLER_SOURCE_VCSCLIENTS_DIR=
set I_REPLACE_TEXT=
set I_REPLACE_VALUE=
set I_REPLACE_INPUT_FILE=
set I_REPLACE_OUTPUT_FILE=
set I_REPLACE_FIND_THIS=
set debug=
set debugParse=
GOTO:EOF
