@echo off
SETLOCAL
REM ######################################################################
REM # (c) 2014 Cisco and/or its affiliates. All rights reserved.
REM ######################################################################
REM #=======================================================================================
REM # Example Execution Statement:
REM #
REM # Option 1 - Execute VCS Workspace initialization:
REM #            ExecutePDToolStudio.bat [-nopause] -vcsinit [-vcsuser vcs-username] [-vcspassword vcs-password]
REM #               arg1:: [-nopause] is an optional parameter used to execute the batch file without pausing at the end of the script.
REM #	            arg2:: -vcsinit is used to initialize the vcs workspace and link it to the repository
REM #               arg3:: [-vcsuser vcs-username] optional parameter
REM #               arg4:: [-vcspassword vcs-password] optional parameter
REM #
REM # Option 2 - Execute VCS Base Folder initialization:
REM #            ExecutePDToolStudio.bat [-nopause] -vcsinitBaseFolders [-customCisPathList "custom-CIS-path-list"] [-vcsuser vcs-username] [-vcspassword vcs-password]
REM #               arg1:: [-nopause] is an optional parameter used to execute the batch file without pausing at the end of the script.
REM #	            arg2:: -vcsinitBaseFolders is used to initialize the vcs repository with the Composite repository base folders and custom folders.
REM #               arg3:: [-customCisPathList custom-CIS-path-list] optional parameter.   Custom, comma separated list of CIS paths to add to the VCS repository.
REM #               arg4:: [-vcsuser vcs-username] optional parameter
REM #               arg5:: [-vcspassword vcs-password] optional parameter
REM #
REM # Option 3 - Execute VCS Workspace property file encryption:
REM #            ExecutePDToolStudio.bat [-nopause] -encrypt config-property-file-path
REM #               arg1:: [-nopause] is an optional parameter used to execute the batch file without pausing at the end of the script.
REM #	            arg2:: -encrypt is used to encrypt the passwords in studio.properties or a Module XML property file
REM #	            arg3:: file path to studio.properties or XML property file (full or relative path)
REM #
REM # Option 4 - Create Composite Studio Enable VCS:"
REM #
REM #            ExecutePDToolStudio.bat [-nopause] -enablevcs -winlogin windows_user_login -user composite_login_user -domain composite_domain -host composite_host_server
REM #                                    -includeResourceSecurity [true or false] [-vcsWorkspacePathOverride "vcs-workspace-project-root-path"]
REM # 
REM #            Example: ExecutePDToolStudio.bat -nopause -enablevcs -winlogin user001 -user admin -domain composite -host localhost 
REM #                                             -includeResourceSecurity true -vcsWorkspacePathOverride "C:/PDToolStudio62/svn_sworkspace/cis_objects"
REM # 
REM #               arg1:: [-nopause] is an optional parameter used to execute the batch file without pausing at the end of the script.
REM #               arg2:: -winlogin is the windows user login name where the script will be modified.
REM #               arg3:: -user is the user name that you login to Composite Studio with.
REM #               arg4:: -domain is the domain designated in the Composite Studio login.
REM #               arg5:: -host is the fully qualified host name designated in the Composite Studio login.
REM #               arg6:: -includeResourceSecurity is true or false and designates whether to turn on resource seucrity at checkin.
REM #               arg7:: -vcsWorkspacePathOverride [optional] is a way of overriding the vcs workspace path instead of allowing this method to construct from the
REM #               	        studio.properites file properties: VCS_WORKSPACE_DIR+"/"+VCS_PROJECT_ROOT.  It will use the substitute drive by default.
REM #               	        This parameter is optional and therefore can be left out if you want to use the default settings in studio.properties.
REM #               	        If a path is provided, use double quotes to surround the path.
REM #               Supported OS:  7, 8, 2008 R2
REM #               Windows XP is not supported for this command
REM #
REM # Editor: Set tab=4 in your text editor for this file to format properly
REM #=======================================================================================
REM #
REM #----------------------------------------------------------
REM #*********** DO NOT MODIFY BELOW THIS LINE ****************
REM #----------------------------------------------------------
REM #
REM #---------------------------------------------
REM # Set environment variables
REM #---------------------------------------------
call setVars.bat
REM #
GOTO BEGIN
REM #-----------------------------------------------------------
REM # Functions 
REM #-----------------------------------------------------------
REM #Usage Exit
:--------------
:USAGE
:--------------
	set ext=.bat
 	call %writeOutput% " -----------------------------------------------------------------------------------------------------"
	call %writeOutput% " "
 	call %writeOutput% " ERROR: Argument [%ARG%] is missing or invalid."
 	call %writeOutput% " "
	call %writeOutput% " USAGE: %SCRIPT%%ext% [-nopause] [-vcsinit|-encrypt|-enablevcs] [various-arguments]"
	call %writeOutput% " "
	call %writeOutput% " CMD: %PARAMS%"
 	call %writeOutput% " "
	call %writeOutput% " -----------------------------------------------------------------------------------------------------"
	call %writeOutput% " Option 1 - Execute VCS Workspace initialization:"
	call %writeOutput% " "
	call %writeOutput% "            %SCRIPT%%ext% [-nopause] -vcsinit [-vcsuser username] [-vcspassword password]"
	call %writeOutput% " "
	call %writeOutput% "            Example: %SCRIPT%%ext% -nopause -vcsinit -vcsuser user -vcspassword password"
 	call %writeOutput% " "
	call %writeOutput% "               arg1:: [-nopause] is an optional parameter used to execute the batch file without pausing at the end of the script."
	call %writeOutput% "               arg2:: -vcsinit is used to initialize the vcs workspace and link it to the repository"
	call %writeOutput% "               arg3:: [-vcsuser username] optional parameters"
	call %writeOutput% "               arg4:: [-vcspassword password] optional parameters"
	call %writeOutput% " -----------------------------------------------------------------------------------------------------"
	call %writeOutput% " Option 2 - VCS Base Folder initialization:"
	call %writeOutput% " "
	call %writeOutput% "            %SCRIPT%%ext% [-nopause] -vcsinitBaseFolders [-customCisPathList custom-path-list] [-vcsuser username] [-vcspassword password]"
	call %writeOutput% " "
	call %writeOutput% "            Example: %SCRIPT%%ext% -nopause -vcsinit -vcsuser user -vcspassword password"
 	call %writeOutput% " "
	call %writeOutput% "               arg1:: [-nopause] is an optional parameter used to execute the batch file without pausing at the end of the script."
	call %writeOutput% "               arg2:: -vcsinitBaseFolders is used to initialize the vcs repository with the Composite repository base folders and custom folders."
	call %writeOutput% "               arg3:: [-customCisPathList custom-CIS-path-list] optional parameters.  Custom, comma separated list of CIS paths to add to the VCS repository."
	call %writeOutput% "               arg4:: [-vcsuser username] optional parameters"
	call %writeOutput% "               arg5:: [-vcspassword password] optional parameters"
	call %writeOutput% " -----------------------------------------------------------------------------------------------------"
	call %writeOutput% " Option 3 - Execute Encrypt Property File:"
	call %writeOutput% " "
	call %writeOutput% "            %SCRIPT%%ext% [-nopause] -encrypt config-property-file-path"
	call %writeOutput% " "
	call %writeOutput% "            Example: %SCRIPT%%ext% -nopause -encrypt ../resources/config/deploy.properties"
 	call %writeOutput% " "
	call %writeOutput% "               arg1:: [-nopause] is an optional parameter used to execute the batch file without pausing at the end of the script."
	call %writeOutput% "               arg2:: -encrypt is used to encrypt the passwords in deploy.properties or a Module XML property file"
	call %writeOutput% "               arg3:: file path to deploy.properties or XML property file [full or relative path]"
	call %writeOutput% " -----------------------------------------------------------------------------------------------------"
	call %writeOutput% " Option 4 - Create Composite Studio Enable VCS:"
	call %writeOutput% "            %SCRIPT%%ext% [-nopause] -enablevcs -winlogin windows_user_login -user composite_login_user -domain composite_domain -host composite_host_server"
	call %writeOutput% "                          -includeResourceSecurity [true or false] [-vcsWorkspacePathOverride "vcs-workspace-project-root-path"]"
	call %writeOutput% " "
	call %writeOutput% "            Example: %SCRIPT%%ext% -nopause -enablevcs -winlogin user001 -user admin -domain composite -host localhost -includeResourceSecurity true -vcsWorkspacePathOverride C:/PDToolStudio62/svn_sworkspace/cis_objects"
 	call %writeOutput% " "
	call %writeOutput% "               arg1:: [-nopause] is an optional parameter used to execute the batch file without pausing at the end of the script."
	call %writeOutput% "               arg2:: -winlogin is the windows user login name where the script will be modified."
	call %writeOutput% "               arg3:: -user is the user name that you login to Composite Studio with."
	call %writeOutput% "               arg4:: -domain is the domain designated in the Composite Studio login."
	call %writeOutput% "               arg5:: -host is the host name designated in the Composite Studio login."
	call %writeOutput% "               arg6:: -includeResourceSecurity is true or false and designates whether to turn on resource seucrity at checkin."
	call %writeOutput% "               arg7:: [-vcsWorkspacePathOverride] is an optional way of overriding the vcs workspace path instead of allowing this method to construct"
	call %writeOutput% "               	        from the studio.properites file properties: VCS_WORKSPACE_DIR/VCS_PROJECT_ROOT.  It will use the substitute drive by default."
	call %writeOutput% "               	        This parameter is optional and therefore can be left out if you want to use the default settings in studio.properties."
	call %writeOutput% "               	        If a path is provided, use double quotes to surround the path."
	call %writeOutput% "               Supported OS: Windows 7, 8, 2008 R2"
	call %writeOutput% "               Windows XP is not supported for this command"
	call %writeOutput% " -----------------------------------------------------------------------------------------------------"
	call %writeOutput% " "
	ENDLOCAL
	exit /B 1

:: -------------------------------------------------------------
:writeOutput
:: -------------------------------------------------------------
::# Example Execution Statement:
::# call :writeOutput "text to echo" "prefix text"
::#
::#	arg1="any text enclosed in double quotes"
::#	arg2="any text prefix enclosed in double quotes"
::#	Note:  if the text contains a double quote it must be escaped prior to invocation by 
::#          setting 2 double quotes.   "print "this" text" would become "print ""this"" text"
::#
::#   Use the following DOS command to achieve turning 1 double quote into 2 double quotes
::#    set TEXT=print "this" text
::#    call writeOutput "%TEXT:"=""%" "PrefixText"
::#       gets passed into like this:  print ""this"" text
::#           output looks like this:  print "this" text
::#----------------------------------------------------------
SETLOCAL EnableDelayedExpansion

REM #----------------------------------------------------------
REM Get the parameters
REM #----------------------------------------------------------
set output=%1
set logprefix=%2

REM #----------------------------------------------------------
REM Add double quotes if nothing passed in or if "" is passed in
REM #----------------------------------------------------------
if (%output%) == () set output=" "
if (%output%) == ("") set output=" "
if (%logprefix%) == () set logprefix=" "
if (%logprefix%) == ("") set logprefix=" "

REM #----------------------------------------------------------
REM Remove double quotes (") at beginning and end of argument
REM #----------------------------------------------------------
SET output=###!output!###
SET output=!output:"###=!
SET output=!output:###"=!
SET output=!output:###=!
SET logprefix=###!logprefix!###
SET logprefix=!logprefix:"###=!
SET logprefix=!logprefix:###"=!
SET logprefix=!logprefix:###=!

REM #----------------------------------------------------------
REM Replace 2 double quotes ("") within the text with a single double quote (")
REM #----------------------------------------------------------
SET logprefix=!logprefix:""="!
SET output=!output:""="!

REM #----------------------------------------------------------
REM remove escape characters if they exist
REM #----------------------------------------------------------
set output=!output:^^=!

REM #----------------------------------------------------------
REM # Echo the line to the command window
REM #-----------------------------------------------------------
echo.!logprefix!!output!
ENDLOCAL
GOTO:EOF

:: -------------------------------------------------------------
:parse
:: -------------------------------------------------------------
::# Example Execution Statement:
::# call :parse
::#
::# Parse parameters and validate input
::#----------------------------------------------------------
set error=0
:loop
	set ARG1=%1
	set ARG2=%2
	REM removed double quotes from around the parameter
	set ARG2X=%ARG2%
REM	set ARG2X=%ARG2:"=%
REM	echo ARG2X=%ARG2X%
    if "%1"=="" GOTO:EOF
	
	REM # -nopause command
    if "%1"=="-nopause" GOTO NOPAUSE
	GOTO CONTINUE1
	:NOPAUSE
            SET PAUSECMD=false
            GOTO:LOOPEND

	REM # -vcsinit command
    :CONTINUE1
    if "%1"=="-vcsinit" GOTO VCSINIT
	GOTO CONTINUE2
	:VCSINIT
            SET CMD=%ARG1%
            GOTO:LOOPEND
	
	REM # -encrypt command
    :CONTINUE2
    if "%1"=="-encrypt" GOTO ENCRYPT
	GOTO CONTINUE3
	:ENCRYPT
			set ARG=PROPERTY_FILE
			set CMD=%ARG1%
            set PROPERTY_FILE=%ARG2X%
			if not defined PROPERTY_FILE (
			   set error=1
			) else (
				REM removed double quotes from around the parameter
			   	set PROPERTY_FILE=%PROPERTY_FILE:"=%
			)
            shift
            GOTO:LOOPEND
	
	REM # Extract -vcsuser
    :CONTINUE3
    if "%1"=="-vcsuser" GOTO VCSUSER
 	GOTO CONTINUE4
	:VCSUSER
			set ARG=vcsuser
            set VCS_USERNAME=%ARG2X%
			if not defined VCS_USERNAME (
			   set error=1
			) else (
				REM removed double quotes from around the parameter
			   	set VCS_USERNAME=%VCS_USERNAME:"=%
			)
            shift
            GOTO:LOOPEND
	
	REM # Extract -vcspassword
	:CONTINUE4
     if "%1"=="-vcspassword" GOTO VCSPASSWORD
 	GOTO CONTINUE5
	:VCSPASSWORD
			set ARG=vcspassword
            set VCS_PASSWORD=%ARG2X%
			if not defined VCS_PASSWORD (
			   set error=1
			) else (
				REM removed double quotes from around the parameter
			   	set VCS_PASSWORD=%VCS_PASSWORD:"=%
			)
            shift
            GOTO:LOOPEND
	
	REM # -enablevcs command
    :CONTINUE5
    if "%1"=="-enablevcs" GOTO ENABLEVCS
	GOTO CONTINUE6
	:ENABLEVCS
            SET CMD=%ARG1%
            GOTO:LOOPEND
	
	REM # Extract -winlogin
    :CONTINUE6
    if "%1"=="-winlogin" GOTO WINLOGIN
 	GOTO CONTINUE7
	:WINLOGIN
			set ARG=winlogin
            set WINDOWS_LOGIN=%ARG2X%
			if not defined WINDOWS_LOGIN (
			   set error=1
			) else (
				REM removed double quotes from around the parameter
			   	set WINDOWS_LOGIN=%WINDOWS_LOGIN:"=%
			)
            shift
            GOTO:LOOPEND
	
	REM # Extract -user
    :CONTINUE7
    if "%1"=="-user" GOTO USER
 	GOTO CONTINUE8
	:USER
			set ARG=user
            set USER=%ARG2X%
			if not defined USER (
			   set error=1
			) else (
				REM removed double quotes from around the parameter
			   	set USER=%USER:"=%
			)
            shift
            GOTO:LOOPEND
	
	REM # Extract -domain
	:CONTINUE8
     if "%1"=="-domain" GOTO DOMAIN
 	GOTO CONTINUE9
	:DOMAIN
			set ARG=domain
            set DOMAIN=%ARG2X%
			if not defined DOMAIN (
			   set error=1
			) else (
				REM removed double quotes from around the parameter
			   	set DOMAIN=%DOMAIN:"=%
			)
            shift
            GOTO:LOOPEND

	REM # Extract -host
	:CONTINUE9
     if "%1"=="-host" GOTO HOST
 	GOTO CONTINUE10
	:HOST
			set ARG=host
            set HOST=%ARG2X%
			if not defined HOST (
			   set error=1
			) else (
				REM removed double quotes from around the parameter
			   	set HOST=%HOST:"=%
			)
            shift
            GOTO:LOOPEND

	REM # Extract -includeResourceSecurity
	:CONTINUE10
     if "%1"=="-includeResourceSecurity" GOTO INCLUDE_RESOURCE_SECURITY
 	GOTO CONTINUE11
	:INCLUDE_RESOURCE_SECURITY
			set ARG=includeResourceSecurity
            set INCLUDE_RESOURCE_SECURITY=%ARG2X%
			if not defined INCLUDE_RESOURCE_SECURITY (
			   set error=1
			) else (
				REM removed double quotes from around the parameter
			   	set INCLUDE_RESOURCE_SECURITY=%INCLUDE_RESOURCE_SECURITY:"=%
			)
            shift
            GOTO:LOOPEND

	REM # Extract -vcsWorkspacePathOverride
	:CONTINUE11
     if "%1"=="-vcsWorkspacePathOverride" GOTO VCS_WORKSPACE_PATH_OVERRIDE
 	GOTO CONTINUE12
	:VCS_WORKSPACE_PATH_OVERRIDE
			set ARG=vcsWorkspacePathOverride
            set VCS_WORKSPACE_PATH_OVERRIDE=%ARG2X%
			if not defined VCS_WORKSPACE_PATH_OVERRIDE (
			   set error=1
			) else (
				REM removed double quotes from around the parameter
			   	set VCS_WORKSPACE_PATH_OVERRIDE=%VCS_WORKSPACE_PATH_OVERRIDE:"=%
			)
            shift
            GOTO:LOOPEND
			
	REM # -vcsinitBaseFolders command
    :CONTINUE12
    if "%1"=="-vcsinitBaseFolders" GOTO VCSINITBASEFOLDERS
	GOTO CONTINUE13
	:VCSINITBASEFOLDERS
            SET CMD=%ARG1%
            GOTO:LOOPEND
			
	REM # Extract -customCisPathList
	:CONTINUE13
     if "%1"=="-customCisPathList" GOTO CUSTOMCISPATHLIST
 	GOTO CONTINUE14
	:CUSTOMCISPATHLIST
			set ARG=customCisPathList
            set CUSTOM_CIS_PATH_LIST=%ARG2X%
			if not defined CUSTOM_CIS_PATH_LIST (
			   set error=1
			) else (
				REM removed double quotes from around the parameter
			   	set CUSTOM_CIS_PATH_LIST=%CUSTOM_CIS_PATH_LIST:"=%
			)
            shift
            GOTO:LOOPEND

	REM # Unknown paramter found
	:CONTINUE14
 			echo Unknown parameter: %1
			call:USAGE
			exit /B 2
 
:LOOPEND
    shift
	REM # Continue loop when no error
	if "%error%"=="0" GOTO LOOP
	
	REM # Error has occurred - goto USAGE and exit
		call:USAGE
		exit /B 2

GOTO:LOOP


REM #----------------------------------------------------------
REM # BEGIN SCRIPT
REM #-----------------------------------------------------------
:BEGIN
REM #=======================================
REM # Set up the execution context for invoking common scripts
REM #=======================================
set SCRIPT=ExecutePDToolStudio
REM # set the print function
set writeOutput=:writeOutput
set SEP=::
REM # Print out the Banner
call %writeOutput% " " 
call %writeOutput% " " 
call %writeOutput% "---------------------------------------------------------" 
call %writeOutput% "---                                                   ---" 
call %writeOutput% "--- Composite PS Promotion and Deployment Tool Studio ---" 
call %writeOutput% "---                                                   ---" 
call %writeOutput% "---------------------------------------------------------" 
call %writeOutput% " " 
call %writeOutput% "***** BEGIN COMMAND: %SCRIPT% *****" 																"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% " " 
REM #=======================================
REM # Substitute PROJECT_HOME path
REM #=======================================
REM #   The PD Tool PROJECT HOME is a substituted
REM #   path in order to shorten the path and
REM #   prevent "too long a file name" error
REM #=======================================
if defined SUBSTITUTE_DRIVE ( 
	if EXIST %SUBSTITUTE_DRIVE% subst /D %SUBSTITUTE_DRIVE%
	echo Substitute drive %SUBSTITUTE_DRIVE% for path "%PROJECT_DIR%"
	subst %SUBSTITUTE_DRIVE% "%PROJECT_DIR%"
)

REM #=======================================
REM # Validate Paths exist
REM #=======================================
if NOT EXIST "%PROJECT_HOME%" (
   call %writeOutput% "Execution Failed::PROJECT_HOME does not exist: %PROJECT_HOME%" 									"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   ENDLOCAL
   exit /B 1
)
if NOT EXIST "%JAVA_HOME%" (
   call %writeOutput% "Execution Failed::JAVA_HOME does not exist: %JAVA_HOME%" 										"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   ENDLOCAL
   exit /B 1
)
REM #=======================================
REM # Display Licenses
REM #=======================================
call %writeOutput% " " 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% "------------------------ PD Tool Licenses ------------------------" 
call %writeOutput% "------------------------------------------------------------------" 
type "%PROJECT_HOME%\licenses\Composite_License.txt"
call %writeOutput% " " 
type "%PROJECT_HOME%\licenses\Project_Specific_License.txt"
call %writeOutput% " " 

REM #=======================================
REM # Set DeployManager Environment Variables
REM #=======================================
set DEPLOY_CLASSPATH="%PROJECT_HOME%\dist\*;%PROJECT_HOME%\lib\*"
set ENDORSED_DIR=%PROJECT_HOME%\lib\endorsed
set DEPLOY_MANAGER=com.cisco.dvbu.ps.deploytool.DeployManagerUtil
set DEPLOY_COMMON_UTIL=com.cisco.dvbu.ps.common.scriptutil.ScriptUtil
set CONFIG_LOG4J=-Dlog4j.configuration="file:%PROJECT_HOME%\resources\config\log4j.properties" 
set CONFIG_ROOT=-Dcom.cisco.dvbu.ps.configroot="%PROJECT_HOME%\resources\config"
REM #=======================================
REM # Parameter Validation
REM #=======================================
SET PAUSECMD=true

set PARAMS=%0 %1 %2 %3 %4 %5 %6 %7 %8 %9
call:parse %*
IF ERRORLEVEL 2 ( exit /B %ERRORLEVEL% )

REM # Set the default to perform a vcsinit if no parameters are specified
if "%CMD%" == "" set CMD=-vcsinit

REM # Branch to the correct commmand area
if "%CMD%" == "-vcsinit" goto SETUP_VCSINIT
if "%CMD%" == "-vcsinitBaseFolders" goto SETUP_VCSINIT_BASE_FOLDERS
if "%CMD%" == "-encrypt" goto SETUP_ENCRYPT
if "%CMD%" == "-enablevcs" goto SETUP_ENABLE_VCS
set arg=1
GOTO USAGE

:--------------
:SETUP_VCSINIT
:--------------
call %writeOutput% " " 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% "------------------ COMMAND-LINE VCS INITIALIZE -------------------" 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% " " 

set	PR_VCS_PASSWORD= 
if defined VCS_PASSWORD set PR_VCS_PASSWORD=********
if not defined VCS_USERNAME set VCS_USERNAME= 
if not defined VCS_PASSWORD set VCS_PASSWORD= 

REM #***********************************************
REM # Invoke: DeployManagerUtil vcsStudioInitWorkspace "%VCS_USERNAME%" "%VCS_PASSWORD%"
REM #***********************************************
set JAVA_ACTION=vcsStudioInitWorkspace
set   COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% "%VCS_USERNAME%" "%VCS_PASSWORD%"
set PRCOMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% "%VCS_USERNAME%" "%PR_VCS_PASSWORD%"
GOTO START_SCRIPT

:--------------------------
:SETUP_VCSINIT_BASE_FOLDERS
:--------------------------
call %writeOutput% " " 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% "----------- COMMAND-LINE VCS BASE FOLDER INITIALIZE --------------" 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% " " 

set	PR_VCS_PASSWORD= 
if defined VCS_PASSWORD set PR_VCS_PASSWORD=********
if not defined VCS_USERNAME set VCS_USERNAME= 
if not defined VCS_PASSWORD set VCS_PASSWORD= 
if not defined CUSTOM_CIS_PATH_LIST set CUSTOM_CIS_PATH_LIST= 

REM #***********************************************
REM # Invoke: DeployManagerUtil vcsStudioInitializeBaseFolderCheckin "%CUSTOM_CIS_PATH_LIST%" "%VCS_USERNAME%" "%VCS_PASSWORD%"
REM #***********************************************
set JAVA_ACTION=vcsStudioInitializeBaseFolderCheckin
set   COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% "%CUSTOM_CIS_PATH_LIST%" "%VCS_USERNAME%" "%VCS_PASSWORD%"
set PRCOMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% "%CUSTOM_CIS_PATH_LIST%" "%VCS_USERNAME%" "%PR_VCS_PASSWORD%"
GOTO START_SCRIPT

:--------------
:SETUP_ENCRYPT
:--------------
call %writeOutput% " " 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% "--------------------- COMMAND-LINE ENCRYPT -----------------------" 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% " " 

REM # Goto usage if PROPERTY_FILE is blank or does not exist
set ARG=PROPERTY_FILE
if not defined PROPERTY_FILE goto USAGE 
if NOT EXIST "%PROPERTY_FILE%" (
   call %writeOutput% "Execution Failed::Property File does not exist: %PROPERTY_FILE%" 					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   goto USAGE 
)
REM #***********************************************
REM # Invoke: DeployManagerUtil encryptPasswordsInFile "%PROPERTY_FILE%"
REM #***********************************************
set JAVA_ACTION=encryptPasswordsInFile
set COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_COMMON_UTIL% %JAVA_ACTION% "%PROPERTY_FILE%"
set PRCOMMAND=%COMMAND%
GOTO START_SCRIPT

:----------------
:SETUP_ENABLE_VCS
:----------------
call %writeOutput% " " 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% "--------------------- COMMAND-LINE ENCRYPT -----------------------" 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% " " 

REM # Validate parameters are present
IF NOT DEFINED USERPROFILE (
  set ARG=USERPROFILE
  call :USAGE
  exit /B 1
)
IF NOT DEFINED WINDOWS_LOGIN (
  set ARG=winlogin
  call :USAGE
  exit /B 1
)
IF NOT DEFINED USER (
  set ARG=user
  call :USAGE
  exit /B 1
)
IF NOT DEFINED DOMAIN (
  set ARG=domain
  call :USAGE
  exit /B 1
)
IF NOT DEFINED HOST (
  set ARG=host
  call :USAGE
  exit /B 1
)
IF NOT DEFINED INCLUDE_RESOURCE_SECURITY (
  set ARG=includeResourceSecurity
  call :USAGE
  exit /B 1
)
IF NOT DEFINED VCS_WORKSPACE_PATH_OVERRIDE (
  set VCS_WORKSPACE_PATH_OVERRIDE= 
)


REM # Set the property file full path.  e.g. C:\Users\<windows_login_user>\.compositesw\admin.composite.localhost.properties
REM #   Supports Windows 7, 8, 2008 R2
set PROPERTY_FILE_PATH=C:\Users\%WINDOWS_LOGIN%\.compositesw\%USER%.%DOMAIN%.%HOST%.properties

REM #***********************************************
REM # Invoke: DeployManagerUtil createStudioEnableVCSPropertyFile
REM #***********************************************
set JAVA_ACTION=createStudioEnableVCSPropertyFile
set COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_COMMON_UTIL% %JAVA_ACTION% "%INCLUDE_RESOURCE_SECURITY%" "%VCS_WORKSPACE_PATH_OVERRIDE%" "%CONFIG_PROPERTY_FILE%" "%PROJECT_HOME_PHYSICAL%" "%PROPERTY_FILE_PATH%"
set PRCOMMAND=%COMMAND%
GOTO START_SCRIPT


:--------------
:START_SCRIPT   
:--------------
REM #=======================================
REM # Execute the script
REM #=======================================
REM Escape (") in the COMMAND with ("") before printing
call %writeOutput% " "
call %writeOutput% "-- COMMAND: %JAVA_ACTION% ----------------------" 
call %writeOutput% " "
call %writeOutput% "%PRCOMMAND:"=""%" 
call %writeOutput% " "
call %writeOutput% "-- BEGIN OUTPUT ------------------------------------" 
call %writeOutput% " "

REM #------------------------------------------------------------------------
REM # Execute the command line CIS script
REM #------------------------------------------------------------------------
call %COMMAND%
set ERROR=%ERRORLEVEL%
if %ERROR% NEQ 0 (
   call %writeOutput% "Script %SCRIPT% Failed. Abnormal Script Termination. Exit code is: %ERROR%." 					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   ENDLOCAL
   exit /B %ERROR%
)

REM #=======================================
REM # Successful script completion
REM #=======================================
call %writeOutput% " "
call %writeOutput% "-------------- SUCCESSFUL SCRIPT COMPLETION [%SCRIPT% %CMD%] --------------" 						"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "End of script." 																					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
IF "%PAUSECMD%" == "true" pause
ENDLOCAL
exit /B 0