@echo off
REM ############################################################################################################################
REM # NOTE: SPECIAL VERSION
REM #
REM # This is a special ExecutePDTool.bat file specifically modified for PDTool_GitTest/bin.
REM # It accommodates testing with lib6.2 folders as the DEFAULT_CIS_VERSION has been set to 6.2.
REM # It allows testing from the main Github folder so the developer does not have to perform a build and deploy for testing.
REM ############################################################################################################################
SETLOCAL
REM ############################################################################################################################
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
REM ############################################################################################################################
REM #
REM #=======================================================================================
REM # Example Execution Statement:
REM # Option 1 - Execute a command line deploy plan file:
REM #
REM #            ExecutePDTool.bat -exec deploy-plan-file-path [-vcsuser username] [-vcspassword password] [-config deploy.properties] [-release RELEASE_FOLDER] 
REM #
REM #               arg1=-exec is used to execute a deploy plan file
REM #	            arg2=orchestration property file path (full or relative path)
REM #               arg3-4=[-vcsuser username] optional parameters
REM #               arg5-6=[-vcspassword password] optional parameter
REM #				arg7-8=[-config deploy.properties] optional parameter
REM #				arg9-10=[-release YYYYMMDD] optional parameter used to specify the release folder for the VCS
REM #
REM # Option 2 - Execute VCS Workspace initialization:
REM #
REM #            ExecutePDTool.bat -vcsinit [-vcsuser username] [-vcspassword password] [-config deploy.properties] [-release RELEASE_FOLDER] 
REM #
REM #	            arg1=-vcsinit is used to initialize the vcs workspace and link it to the repository
REM #               arg3-4=[-vcsuser username] optional parameters
REM #               arg5-6=[-vcspassword password] optional parameter
REM #				arg7-8=[-config deploy.properties] optional parameter
REM #				arg9-10=[-release YYYYMMDD] optional parameter used to specify the release folder for the VCS
REM #
REM # Option 3 - Execute property file encryption:
REM #
REM #            ExecutePDTool.bat -encrypt property-file-path [-config deploy.properties]
REM #
REM #	            arg1=-encrypt is used to encrypt the passwords in deploy.properties or a Module XML property file
REM #	            arg2=file path to deploy.properties or XML property file (full or relative path)
REM #				arg3-4=[-config deploy.properties] optional parameter
REM #
REM # Option 4 - Execute an Ant build file:
REM #
REM #            ExecutePDTool.bat -ant build-file-path [-vcsuser username] [-vcspassword password] [-config deploy.properties] [-release RELEASE_FOLDER] 
REM #
REM #               arg1=-ant is used to execute an Ant build file
REM #	            arg2=orchestration build file path (full or relative path)
REM #               arg3-4=[-vcsuser username] optional parameter
REM #               arg5-6=[-vcspassword password] optional parameter
REM #				arg7-8=[-config deploy.properties] optional parameter
REM #				arg9-10=[-release YYYYMMDD] optional parameter used to specify the release folder for the VCS
REM #
REM # Editor: Set tab=4 in your text editor for this file to format properly
REM #=======================================================================================
REM #
REM # 0=debug off, 1=debug on
set debug=0
REM # 0=do not print variable output, 1=do print variable output
set PRINT_VARS=1
REM #
REM # [OPTIONAL] Default log location relative path from bin directory ../logs/app.log
rem #            If set then bath file messages will be written to the log
set DEFAULT_LOG_PATH=../logs/app.log
REM #
REM #----------------------------------------------------------
REM #*********** DO NOT MODIFY BELOW THIS LINE ****************
REM #----------------------------------------------------------
REM #=======================================
REM # Set up the execution context for invoking common scripts
REM #=======================================
REM # CIS version [6.2, 7.0.0]
set DEFAULT_CIS_VERSION=6.2
REM # Script name
set SCRIPT=ExecutePDTool
REM # set the print function
set writeOutput=:writeOutput
REM # Initialize variables
set SEP=::
SET PDTOOL_CMD=
SET PDTOOL_PROPERTY_FILE=
SET PDTOOL_VCS_USERNAME=
SET PDTOOL_VCS_PASSWORD=
SET PDTOOL_CONFIG_PROPERTY_FILE=
SET PDTOOL_RELEASE_FOLDER=
SET PR_VCS_PASSWORD=

REM # Get the full path to setVars.bat
for /f "tokens=* delims= " %%I in ("setVars.bat") do set DEFAULT_SET_VARS_PATH=%%~fI

REM # Print out the Banner
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% "-----------                                            -----------" 
call %writeOutput% "----------- Cisco Advanced Services                    -----------" 
call %writeOutput% "----------- PDTool: Promotion and Deployment Tool      -----------" 
call %writeOutput% "-----------                                            -----------" 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% " " 
call %writeOutput% "***** BEGIN COMMAND: %SCRIPT% *****" 																	"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% " " 
REM #
REM #=======================================
REM # Parse Input Parameters
REM #=======================================
REM # Get the list of parameters to be used for the USAGE function
call %writeOutput% "########################################################################################################################################"
call %writeOutput% "%SCRIPT%: Parse command line and set input variables:"
call %writeOutput% "########################################################################################################################################"
set PARAMS=
call :getParams %*
if %debug%==1 echo.[DEBUG] %SCRIPT%: PARAMS=%PARAMS%

REM # Parse the parameters
set PARSE_ERROR=0
set ARG=
set ERRORMSG=
call :parse %*
SET ERROR=%ERRORLEVEL%
IF %ERROR% GTR 0 ( exit /B %ERROR% )

REM # Validate the parameters
if "%PDTOOL_CMD%" == "-exec" goto VALID_PARAMS
if "%PDTOOL_CMD%" == "-vcsinit" goto VALID_PARAMS
if "%PDTOOL_CMD%" == "-encrypt" goto VALID_PARAMS
if "%PDTOOL_CMD%" == "-ant" goto VALID_PARAMS
if "%PDTOOL_CMD%" == "-xform" goto VALID_PARAMS
set arg=1
set ERRORMSG=Execution Failed::No parameters provided.
GOTO USAGE
:VALID_PARAMS

REM # Print the parameters
CALL:printablePassword "%PDTOOL_VCS_PASSWORD%" PR_VCS_PASSWORD
call %writeOutput% "Command Line Arguments:"																				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   CMD=[%PDTOOL_CMD%]"																					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   PROPERTY_FILE=[%PDTOOL_PROPERTY_FILE%]" 																"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   VCS_USERNAME=[%PDTOOL_VCS_USERNAME%]" 																"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   VCS_PASSWORD=[%PR_VCS_PASSWORD%]" 																	"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   CONFIG_PROPERTY_FILE=[%PDTOOL_CONFIG_PROPERTY_FILE%]" 												"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   RELEASE_FOLDER=[%PDTOOL_RELEASE_FOLDER%]" 															"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   CIS_VERSION=[%CIS_VERSION%]" 																		"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% " "

REM # Assign parameters
if defined PDTOOL_PROPERTY_FILE         set PROPERTY_FILE=%PDTOOL_PROPERTY_FILE%
if defined PDTOOL_VCS_USERNAME          set VCS_USERNAME=%PDTOOL_VCS_USERNAME%
if defined PDTOOL_VCS_PASSWORD          set VCS_PASSWORD=%PDTOOL_VCS_PASSWORD%
if defined PDTOOL_CONFIG_PROPERTY_FILE  set CONFIG_PROPERTY_FILE=%PDTOOL_CONFIG_PROPERTY_FILE%
if defined PDTOOL_RELEASE_FOLDER        set RELEASE_FOLDER=%PDTOOL_RELEASE_FOLDER%
if defined DEFAULT_CIS_VERSION       	set CIS_VERSION=%DEFAULT_CIS_VERSION%

REM #=======================================
REM # Invoke setVars.bat
REM #=====================================================================================
if exist %DEFAULT_SET_VARS_PATH% goto INVOKE_SET_VARS

  set ARG=DEFAULT_SET_VARS_PATH
  set ERRORMSG=Execution Failed::Path does not exist: %ARG1%
  call:USAGE
  exit /B 2
			
:INVOKE_SET_VARS
REM #---------------------------------------------
REM # Set environment variables
REM #---------------------------------------------
call %writeOutput% "----------------------------------------------------------" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "Section: Invoking setVars.bat...%DEFAULT_SET_VARS_PATH%" 												"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "----------------------------------------------------------" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %DEFAULT_SET_VARS_PATH% 
REM #
REM #=======================================
REM # Variable Resolution
REM #=====================================================================================
REM #   If external "setVars.bat" variables are set and internal ExecutePDTool.bat
REM #   variables are not set then use the external variables.  Do not override
REM #   internal variables with external ones.
REM #   INTERNAL VAR                 --> EXTERNAL VAR
REM #   ---------------------------      --------------------
REM #   PDTOOL_PROPERTY_FILE         --> PROPERTY_FILE
REM #   PDTOOL_VCS_USERNAME          --> VCS_USERNAME
REM #   PDTOOL_VCS_PASSWORD          --> VCS_PASSWORD
REM #   PDTOOL_CONFIG_PROPERTY_FILE  --> CONFIG_PROPERTY_FILE
REM #   PDTOOL_RELEASE_FOLDER        --> RELEASE_FOLDER
REM #   DEFAULT_CIS_VERSION          --> CIS_VERSION
REM #=====================================================================================
call:resolveVariables "%PDTOOL_PROPERTY_FILE%" 		  "%PROPERTY_FILE%" 		PROPERTY_FILE
call:resolveVariables "%PDTOOL_VCS_USERNAME%" 		  "%VCS_USERNAME%" 			VCS_USERNAME
call:resolveVariables "%PDTOOL_VCS_PASSWORD%" 		  "%VCS_PASSWORD%" 			VCS_PASSWORD
call:resolveVariables "%PDTOOL_CONFIG_PROPERTY_FILE%" "%CONFIG_PROPERTY_FILE%" 	CONFIG_PROPERTY_FILE
call:resolveVariables "%PDTOOL_RELEASE_FOLDER%" 	  "%RELEASE_FOLDER%" 		RELEASE_FOLDER
call:resolveVariables "%DEFAULT_CIS_VERSION%" 	  	  "%CIS_VERSION%" 			CIS_VERSION

REM # Print the parameters
CALL:printablePassword "%VCS_PASSWORD%" PR_VCS_PASSWORD
call %writeOutput% "########################################################################################################################################"
call %writeOutput% "%SCRIPT%: Command line resolved variables:"
call %writeOutput% "########################################################################################################################################"
call %writeOutput% "Resolved Arguments:"																					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   CMD=[%PDTOOL_CMD%]"																					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   PROPERTY_FILE=[%PROPERTY_FILE%]" 																	"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   VCS_USERNAME=[%VCS_USERNAME%]" 																		"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   VCS_PASSWORD=[%PR_VCS_PASSWORD%]" 																	"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   CONFIG_PROPERTY_FILE=[%CONFIG_PROPERTY_FILE%]" 														"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   RELEASE_FOLDER=[%RELEASE_FOLDER%]" 																	"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   CIS_VERSION=[%CIS_VERSION%]" 																		"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% " "


REM #=======================================
REM # Substitute PROJECT_HOME path
REM #=====================================================================================
REM #   The PDTool PROJECT HOME is a substituted path in order to shorten the path and
REM #   prevent "too long a file name" error
REM #=====================================================================================
set PAD=         
REM #-----------------------------------------------------------
REM # This section creates a substitute drive using "subst"
REM # This code was left here for backwards compatibility in the
REM #   event that "net use" does not work properly in a particular
REM #   environment.
REM #-----------------------------------------------------------
if not defined SUBSTITUTE_DRIVE goto MAP_NETWORK_DRIVE_BEGIN
	call %writeOutput% "Section: Substitute Drives" 																		"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%Evaluate the substitute drive=%SUBSTITUTE_DRIVE% path=%PROJECT_HOME_PHYSICAL%" 				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	REM 2014-12-04 mtinius - changed logic to only map the drive when necessary...not every time
	if NOT EXIST %SUBSTITUTE_DRIVE% goto MAP_SUBSTITUTE_DRIVE
	REM 2014-12-04 mtinius - changed logic to evaluate if current mapped drive utilizes the correct path
	call :EVALUATE_SUBST_DRIVES %SUBSTITUTE_DRIVE% "%PROJECT_HOME_PHYSICAL%" %debug% drivePathFound

	if "%drivePathFound%" == "true" (
		call %writeOutput% "%PAD%Substitute drive %SUBSTITUTE_DRIVE% is already mapped to path:%PROJECT_HOME_PHYSICAL%" "%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
		goto MAPSUCCESS
	)
	REM The drive is already mapped to a different path so exit with an error and have the user unmap the drive manually
	call %writeOutput% "%PAD%The substitute drive=%SUBSTITUTE_DRIVE% is mapped to a different path.  There are two options:" "%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%  Option 1: Change the drive letter designated by the variable SUBSTITUTE_DRIVE in setVars.bat." "%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%  Option 2: Unmap the drive by executing this command manually: subst /D %SUBSTITUTE_DRIVE%" 	"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%Re-execute %0" 																				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
    exit /b 1
:MAP_SUBSTITUTE_DRIVE
	call %writeOutput% "%PAD%Execute substitute drive command:  subst %SUBSTITUTE_DRIVE% %PROJECT_HOME_PHYSICAL%" 			"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
    subst %SUBSTITUTE_DRIVE% "%PROJECT_HOME_PHYSICAL%"
    set ERROR=%ERRORLEVEL%
    if "%ERROR%" == "0" goto MAPSUCCESS
	call %writeOutput% "%PAD%An error=%ERROR% occurred executing command: subst %SUBSTITUTE_DRIVE% %PROJECT_HOME_PHYSICAL%" "%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%Execute this command manually: subst %SUBSTITUTE_DRIVE% %PROJECT_HOME_PHYSICAL%"				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%Re-execute %0" 																				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
    exit /b 1

REM #-----------------------------------------------------------
REM # This section creates a substitute drive using "net use"
REM # The advantage over "subst" is that this is permanent while
REM #   "subst" is temporary in that id does not survive reboots.
REM #-----------------------------------------------------------
:MAP_NETWORK_DRIVE_BEGIN
if not defined NETWORK_DRIVE goto MAPSUCCESS
	call %writeOutput% "Section: Network Drives" 																			"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%Evaluate the network drive=%NETWORK_DRIVE% path=%PROJECT_HOME_PHYSICAL%" 						"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	REM Only map the drive when necessary...not every time
	if NOT EXIST %NETWORK_DRIVE% goto MAP_NETWORK_DRIVE
	REM Evaluate if current mapped drive utilizes the correct path
	call :EVALUATE_NETWORK_DRIVES %NETWORK_DRIVE% "%PROJECT_HOME_PHYSICAL%" %debug% drivePathFound

	if "%drivePathFound%" == "true" (
		call %writeOutput% "%PAD%Network drive %NETWORK_DRIVE% is already mapped to path:%PROJECT_HOME_PHYSICAL%" 			"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
		goto MAPSUCCESS
	)
	REM The drive is already mapped to a different path so exit with an error and have the user unmap the drive manually
	call %writeOutput% "%PAD%The network drive=%NETWORK_DRIVE% is mapped to a different path.  There are two options:" 		"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%  Option 1: Change the drive letter designated by the variable NETWORK_DRIVE in setVars.bat." 	"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%  Option 2: Unmap the drive by executing this command manually: net use %NETWORK_DRIVE% /DELETE" "%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%Re-execute %0" 																				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
    exit /b 1
:MAP_NETWORK_DRIVE
	REM # Get the Drive letter for PROJECT_HOME_PHYSICAL
	for /F "usebackq delims==" %%I IN (`echo %PROJECT_HOME_PHYSICAL%`) DO (
		set PDTOOL_HOME_DRIVE=%%~dI
		REM echo PDTOOL_HOME_DRIVE=%PDTOOL_HOME_DRIVE%
	)
	set PDTOOL_HOME_DRIVE_SHARE=%PDTOOL_HOME_DRIVE::=$%
	if not exist "\\%COMPUTERNAME%\%PDTOOL_HOME_DRIVE_SHARE%" (
		REM The share drive does not exist
		call %writeOutput% "%PAD%The network share ^"\\%COMPUTERNAME%\%PDTOOL_HOME_DRIVE_SHARE%^" does not exist." 				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
		call %writeOutput% "%PAD%   Create the share drive=%PDTOOL_HOME_DRIVE_SHARE% for the installation drive=%PDTOOL_HOME_DRIVE% as administrator."%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
		call %writeOutput% "%PAD%   Command run as administrator: net share %PDTOOL_HOME_DRIVE_SHARE%=%PDTOOL_HOME_DRIVE%\" 	"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
		call %writeOutput% "%PAD%Re-execute %0" 																				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
		exit /b 1
	)
	
	call :REPLACE "%PDTOOL_HOME_DRIVE%" "\\%COMPUTERNAME%\%PDTOOL_HOME_DRIVE_SHARE%" "%PROJECT_HOME_PHYSICAL%" PROJECT_HOME_PHYSICAL_NEW
	call %writeOutput% "%PAD%Execute network drive command:  net use %NETWORK_DRIVE% %PROJECT_HOME_PHYSICAL_NEW% /PERSISTENT:YES" "%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
    net use %NETWORK_DRIVE% %PROJECT_HOME_PHYSICAL_NEW% /PERSISTENT:YES
    set ERROR=%ERRORLEVEL%
    if "%ERROR%" == "0" goto MAPSUCCESS
	call %writeOutput% "%PAD%An error=%ERROR% occurred executing command: net use %NETWORK_DRIVE% %PROJECT_HOME_PHYSICAL_NEW% /PERSISTENT:YES" "%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%Execute this command manually: net use %NETWORK_DRIVE% %PROJECT_HOME_PHYSICAL_NEW% /PERSISTENT:YES" "%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%Re-execute %0" 																					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
    exit /b 1

:MAPSUCCESS   


REM #=======================================
REM # Validate Paths exist
REM #=======================================
if NOT EXIST "%PROJECT_HOME%" (
   call %writeOutput% "Execution Failed::PROJECT_HOME does not exist: %PROJECT_HOME%" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   ENDLOCAL
   exit /B 1
)
if NOT EXIST "%JAVA_HOME%" (
   call %writeOutput% "Execution Failed::JAVA_HOME does not exist: %JAVA_HOME%" 												"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   ENDLOCAL
   exit /B 1
)
echo.

REM #=======================================
REM # Display Licenses
REM #=======================================
REM #call %writeOutput% " " 
REM #call %writeOutput% "------------------------------------------------------------------" 
REM #call %writeOutput% "------------------------ PD Tool Licenses ------------------------" 
REM #call %writeOutput% "------------------------------------------------------------------" 
REM #type "%PROJECT_HOME%\licenses\Composite_License.txt"
REM #call %writeOutput% " " 
REM #type "%PROJECT_HOME%\licenses\Project_Specific_License.txt"
REM #call %writeOutput% " " 

REM #=======================================
REM # Set DeployManager Environment Variables
REM #=======================================
set DEPLOY_CLASSPATH="%PROJECT_HOME%\dist\PDTool%DEFAULT_CIS_VERSION%.jar;%PROJECT_HOME%\lib%DEFAULT_CIS_VERSION%\*;%PROJECT_HOME%\libcommon\*"
set ENDORSED_DIR=%PROJECT_HOME%\lib%DEFAULT_CIS_VERSION%\endorsed
set DEPLOY_MANAGER=com.cisco.dvbu.ps.deploytool.DeployManagerUtil
set DEPLOY_COMMON_UTIL=com.cisco.dvbu.ps.common.scriptutil.ScriptUtil
set CONFIG_LOG4J=-Dlog4j.configuration="file:%PROJECT_HOME%\resources\config\log4j.properties" 
set CONFIG_ROOT=-Dcom.cisco.dvbu.ps.configroot="%PROJECT_HOME%\resources\config"
REM #=======================================
REM # Precedence evaluation
REM #=======================================
set PRECEDENCE=
if defined propertyOrderPrecedence set PRECEDENCE=-DpropertyOrderPrecedence="%propertyOrderPrecedence%"
REM #=======================================
REM # Parameter Validation
REM #=======================================
if "%PDTOOL_CMD%" == "-exec" goto SETUP_EXEC
if "%PDTOOL_CMD%" == "-vcsinit" goto SETUP_VCSINIT
if "%PDTOOL_CMD%" == "-encrypt" goto SETUP_ENCRYPT
if "%PDTOOL_CMD%" == "-ant" goto SETUP_ANT
if "%PDTOOL_CMD%" == "-xform" goto SETUP_XFORM
set arg=1
set ERRORMSG=Execution Failed::Failed parameter validation.
GOTO USAGE

:--------------
:SETUP_EXEC
:--------------
call %writeOutput% " " 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% "-------------------- COMMAND-LINE DEPLOYMENT ---------------------" 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% " " 

CALL:printablePassword "%VCS_PASSWORD%" PR_VCS_PASSWORD
REM # Goto usage if PROPERTY_FILE is blank or does not exist
set ARG=PROPERTY_FILE
if not defined PROPERTY_FILE goto USAGE 
if NOT EXIST "%PROPERTY_FILE%" (
   call %writeOutput% "Execution Failed::Orchestration deploy plan file does not exist: %PROPERTY_FILE%" 			"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   goto USAGE 
)
REM #***********************************************
REM # Invoke: DeployManagerUtil execCisDeployTool "%PROPERTY_FILE%" "%VCS_USERNAME%" "%VCS_PASSWORD%"
REM #***********************************************
set JAVA_ACTION=execCisDeployTool
set   COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% %PRECEDENCE% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DPROJECT_HOME_PHYSICAL="%PROJECT_HOME_PHYSICAL%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% "%PROPERTY_FILE%" "%VCS_USERNAME%" "%VCS_PASSWORD%"
set PRCOMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% %PRECEDENCE% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DPROJECT_HOME_PHYSICAL="%PROJECT_HOME_PHYSICAL%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% "%PROPERTY_FILE%" "%VCS_USERNAME%" "%PR_VCS_PASSWORD%"
GOTO START_SCRIPT

:--------------
:SETUP_VCSINIT
:--------------
call %writeOutput% " " 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% "------------------ COMMAND-LINE VCS INITIALIZE -------------------" 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% " " 

if not defined VCS_USERNAME set VCS_USERNAME= 
if not defined VCS_PASSWORD set VCS_PASSWORD= 
CALL:printablePassword "%VCS_PASSWORD%" PR_VCS_PASSWORD

REM #***********************************************
REM # Invoke: DeployManagerUtil vcsInitWorkspace "%VCS_USERNAME%" "%VCS_PASSWORD%"
REM #***********************************************
set JAVA_ACTION=vcsInitWorkspace
set   COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% %PRECEDENCE% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DPROJECT_HOME_PHYSICAL="%PROJECT_HOME_PHYSICAL%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% "%VCS_USERNAME%" "%VCS_PASSWORD%"
set PRCOMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% %PRECEDENCE% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DPROJECT_HOME_PHYSICAL="%PROJECT_HOME_PHYSICAL%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% "%VCS_USERNAME%" "%PR_VCS_PASSWORD%"
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
   call %writeOutput% "Execution Failed::Property file does not exist: %PROPERTY_FILE%" 								"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   goto USAGE 
)
REM #***********************************************
REM # Invoke: ScriptUtil encryptPasswordsInFile "%PROPERTY_FILE%"
REM #***********************************************
set JAVA_ACTION=encryptPasswordsInFile
set COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% %PRECEDENCE% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DPROJECT_HOME_PHYSICAL="%PROJECT_HOME_PHYSICAL%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_COMMON_UTIL% %JAVA_ACTION% "%PROPERTY_FILE%"
set PRCOMMAND=%COMMAND%
GOTO START_SCRIPT


:--------------
:SETUP_ANT
:--------------
call %writeOutput% " " 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% "------------------------- ANT DEPLOYMENT -------------------------" 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% " " 

if not defined VCS_USERNAME set VCS_USERNAME= 
if not defined VCS_PASSWORD set VCS_PASSWORD= 
CALL:printablePassword "%VCS_PASSWORD%" PR_VCS_PASSWORD

REM # Goto usage if PROPERTY_FILE is blank or does not exist
set ARG=PROPERTY_FILE
if not defined PROPERTY_FILE goto USAGE 
if NOT EXIST "%PROPERTY_FILE%" (
   call %writeOutput% "Execution Failed::Ant deploy plan build file does not exist: %PROPERTY_FILE%" 				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   goto USAGE 
)
REM #=======================================
REM # Derive ANT_HOME from PROJECT_HOME
REM #=======================================
REM # Ant ships with the CisDeployTool so the directory is an offset to the Project Home
set ANT_HOME=%PROJECT_HOME%/ext/ant
if NOT EXIST "%ANT_HOME%" (
   call %writeOutput% "Execution Failed::ANT_HOME does not exist: %ANT_HOME%" 												"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   ENDLOCAL
   exit /B 1
)
REM # Set the Ant classpath and options
set ANT_CLASSPATH=%PROJECT_HOME%\dist\PDTool%DEFAULT_CIS_VERSION%.jar;%PROJECT_HOME%\lib%DEFAULT_CIS_VERSION%;%PROJECT_HOME%\lib%DEFAULT_CIS_VERSION%\endorsed;%PROJECT_HOME%\libcommon;%ANT_HOME%\lib
set ANT_OPTS=%CONFIG_LOG4J% %CONFIG_ROOT% -Djava.endorsed.dirs="%ENDORSED_DIR%"

REM #***********************************************
REM # Invoke: ant -buildfile
REM #***********************************************

set   COMMAND="%ANT_HOME%/bin/ant" -lib "%ANT_CLASSPATH%" %PRECEDENCE% -DPROJECT_HOME="%PROJECT_HOME%" -DPROJECT_HOME_PHYSICAL="%PROJECT_HOME_PHYSICAL%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% -DVCS_USERNAME="%VCS_USERNAME%" -DVCS_PASSWORD="%VCS_PASSWORD%" -buildfile "%PROPERTY_FILE%"
set PRCOMMAND="%ANT_HOME%/bin/ant" -lib "%ANT_CLASSPATH%" %PRECEDENCE% -DPROJECT_HOME="%PROJECT_HOME%" -DPROJECT_HOME_PHYSICAL="%PROJECT_HOME_PHYSICAL%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% -DVCS_USERNAME="%VCS_USERNAME%" -DVCS_PASSWORD="%PR_VCS_PASSWORD%" -buildfile "%PROPERTY_FILE%"
GOTO START_SCRIPT

:--------------
:SETUP_XFORM
:--------------
call %writeOutput% " " 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% "--------------------- COMMAND-LINE XSL TRANSFORMATION -----------------------" 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% " " 

REM # Goto usage if XML_FILE_SOURCE is blank or does not exist
if not defined XML_FILE_SOURCE (
   call %writeOutput% "Failed::Input argument was not defined: XML_FILE_SOURCE" 												"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   goto XFORM_USAGE
)
if NOT EXIST "%XML_FILE_SOURCE%" (
   call %writeOutput% "Execution Failed::XML source file does not exist: %XML_FILE_SOURCE%" 									"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   goto XFORM_USAGE
)
REM # Goto usage if XSL_FILE_SOURCE is blank or does not exist
if not defined XSL_FILE_SOURCE (
   call %writeOutput% "Failed::Input argument was not defined: XSL_FILE_SOURCE" 												"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   goto XFORM_USAGE 
)
if NOT EXIST "%XSL_FILE_SOURCE%" (
   call %writeOutput% "Execution Failed::XSL style sheet file does not exist: %XSL_FILE_SOURCE%" 								"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   goto XFORM_USAGE
)

REM #***********************************************
REM # Invoke: ScriptUtil XslTransformUtility "%XML_FILE_SOURCE%" "%XSL_FILE_SOURCE%"
REM #***********************************************
set JAVA_ACTION=XslTransformUtility
set COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% %PRECEDENCE% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DPROJECT_HOME_PHYSICAL="%PROJECT_HOME_PHYSICAL%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_COMMON_UTIL% %JAVA_ACTION% "%XML_FILE_SOURCE%" "%XSL_FILE_SOURCE%"
set PRCOMMAND=%COMMAND%
GOTO START_SCRIPT

:XFORM_USAGE
	call %writeOutput% "            %SCRIPT%%ext% -xform xml-document-path xsl-style-sheet-path"
	call %writeOutput% " "
	call %writeOutput% "            Example: %SCRIPT%%ext% -xform test-doc.xml test-xform.xsl
   ENDLOCAL
   exit /B 1

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
   call %writeOutput% "Execution Failed::Script %SCRIPT% Failed. Abnormal Script Termination. Exit code is: %ERROR%." 		"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   ENDLOCAL
   exit /B %ERROR%
)

REM #=======================================
REM # Successful script completion
REM #=======================================
call %writeOutput% " "
call %writeOutput% "-------------- SUCCESSFUL SCRIPT COMPLETION [%SCRIPT% %PDTOOL_CMD%] --------------" 							"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "End of script." 																						"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
ENDLOCAL
exit /B 0


REM ############################################################
REM # FUNCTIONS: BEGIN
REM ############################################################
:: -------------------------------------------------------------
:getParams
:: -------------------------------------------------------------
::# parse the input parameters to get the list for display purposes
::#
::# Example Execution Statement:
::#   call :getParams %*
::#----------------------------------------------------------
set SCRIPT_DEBUG=%0
set blank= 
:GET_PARAMS_LOOP
	set ARG1=%1
	set ARG2=%2
	set TARG1=
	set TARG2=
	REM # Remove double quotes
	setlocal EnableDelayedExpansion
	if defined ARG1 set TARG1=!ARG1:"=!
	if defined ARG2 set TARG2=!ARG2:"=!
	endlocal & SET TARG1=%TARG1%& SET TARG2=%TARG2%
	if %debug%==1 echo.[DEBUG] %SCRIPT_DEBUG%: ARG1=[%ARG1%]  Removed Quotes: VALUE=[%TARG1%]
	if %debug%==1 echo.[DEBUG] %SCRIPT_DEBUG%: ARG2=[%ARG2%]  Removed Quotes: VALUE=[%TARG2%]
	if "%TARG1%" == "" (
	    if "%TARG2%" NEQ "" goto GET_PARAMS_CONT
		GOTO:EOF
	)
:GET_PARAMS_CONT
    set PARAMS=%PARAMS%%ARG1%%blank%
    shift
GOTO GET_PARAMS_LOOP
GOTO:getParamsLoop

:: -------------------------------------------------------------
:parse
:: -------------------------------------------------------------
::# parse the input parameters and validate the input
::#
::# Example Execution Statement:
::# call :parse %*
::#----------------------------------------------------------
set SCRIPT_DEBUG=%0
:LOOP
	SET ARG1=%1
	SET ARG2=%2
	SET ARG3=%3
	set TARG1=
	set TARG2=
	set TARG3=
	REM # Remove double quotes
	setlocal EnableDelayedExpansion
	if defined ARG1 set TARG1=!ARG1:"=!
	if defined ARG2 set TARG2=!ARG2:"=!
	if defined ARG3 set TARG3=!ARG3:"=!
	endlocal & SET ARG1=%TARG1%& SET ARG2=%TARG2%& SET ARG3=%TARG3%
	REM # Display debug if on
	if %debug%==1 echo.[DEBUG] %SCRIPT_DEBUG%: ARG1=[%ARG1%] ARG2=[%ARG2%] ARG3=[%ARG3%]
	
	REM # Check for no more values and return from :parse.
	if "%ARG1%" == "" GOTO:EOF

    if "%ARG1%" == "-exec" (
				SET PDTOOL_CMD=%ARG1%
                SET PDTOOL_PROPERTY_FILE=%ARG2%
				set PARSE_ERROR=1
				set ARG=PROPERTY_FILE
				set ERRORMSG=Execution Failed::Missing parameter
				if "%ARG2%" NEQ "" set PARSE_ERROR=0
				if "%ARG2%" NEQ "" set ARG=
				if "%ARG2%" NEQ "" set ERRORMSG=
                shift
                GOTO:LOOPEND
            )
    if "%ARG1%" == "-ant" (
 				SET PDTOOL_CMD=%ARG1%
                SET PDTOOL_PROPERTY_FILE=%ARG2%
				set PARSE_ERROR=1
				set ARG=PROPERTY_FILE
				set ERRORMSG=Execution Failed::Missing parameter
				if "%ARG2%" NEQ "" set PARSE_ERROR=0
				if "%ARG2%" NEQ "" set ARG=
				if "%ARG2%" NEQ "" set ERRORMSG=
                shift
                GOTO:LOOPEND
            )
    if "%ARG1%" == "-encrypt" (
				SET PDTOOL_CMD=%ARG1%
                SET PDTOOL_PROPERTY_FILE=%ARG2%
				set PARSE_ERROR=1
				set ARG=PROPERTY_FILE
				set ERRORMSG=Execution Failed::Missing parameter
				if "%ARG2%" NEQ "" set PARSE_ERROR=0
				if "%ARG2%" NEQ "" set ARG=
				if "%ARG2%" NEQ "" set ERRORMSG=
                shift
                GOTO:LOOPEND
            )
    if "%ARG1%" == "-vcsinit" (
                SET PDTOOL_CMD=%ARG1%
                GOTO:LOOPEND
            )
    if "%ARG1%" == "-vcsuser" (
                SET PDTOOL_VCS_USERNAME=%ARG2%
				set PARSE_ERROR=1
				set ARG=VCS_USERNAME
				set ERRORMSG=Execution Failed::Missing parameter
				if "%ARG2%" NEQ "" set PARSE_ERROR=0
				if "%ARG2%" NEQ "" set ARG=
				if "%ARG2%" NEQ "" set ERRORMSG=
                shift
                GOTO:LOOPEND
            )
    if "%ARG1%" == "-vcspassword" (
                SET PDTOOL_VCS_PASSWORD=%ARG2%
				set PARSE_ERROR=1
				set ARG=VCS_PASSWORD
				set ERRORMSG=Execution Failed::Missing parameter
				if "%ARG2%" NEQ "" set PARSE_ERROR=0
				if "%ARG2%" NEQ "" set ARG=
				if "%ARG2%" NEQ "" set ERRORMSG=
                shift
                GOTO:LOOPEND
            )
    if "%ARG1%" == "-config" (
                SET PDTOOL_CONFIG_PROPERTY_FILE=%ARG2%
				set PARSE_ERROR=1
				set ARG=CONFIG_PROPERTY_FILE
				set ERRORMSG=Execution Failed::Missing parameter
				if "%ARG2%" NEQ "" set PARSE_ERROR=0
				if "%ARG2%" NEQ "" set ARG=
				if "%ARG2%" NEQ "" set ERRORMSG=
                shift
                GOTO:LOOPEND
            )

    if "%ARG1%" == "-release" (
                SET PDTOOL_RELEASE_FOLDER=%ARG2%
				set PARSE_ERROR=1
				set ARG=RELEASE_FOLDER
				set ERRORMSG=Execution Failed::Missing parameter
				if "%ARG2%" NEQ "" set PARSE_ERROR=0
				if "%ARG2%" NEQ "" set ARG=
				if "%ARG2%" NEQ "" set ERRORMSG=
                shift
                GOTO:LOOPEND
            )
		
    if "%ARG1%" == "-ver" (
                SET DEFAULT_CIS_VERSION=%ARG2%
				set PARSE_ERROR=1
				set ARG=CIS_VERSION
				set ERRORMSG=Execution Failed::Missing parameter
				if "%ARG2%" NEQ "" set PARSE_ERROR=0
				if "%ARG2%" NEQ "" set ARG=
				if "%ARG2%" NEQ "" set ERRORMSG=
                shift
                GOTO:LOOPEND
            )

    if "%ARG1%" == "-xform" (
				SET PDTOOL_CMD=%ARG1%
                SET XML_FILE_SOURCE=%ARG2%
                SET XSL_FILE_SOURCE=%ARG3%
				set PARSE_ERROR=1
				set ARG=xform
				set ERRORMSG=Execution Failed::Missing parameter
				if "%ARG2%" NEQ "" set PARSE_ERROR=0
				if "%ARG2%" NEQ "" set ARG=
				if "%ARG2%" NEQ "" set ERRORMSG=
 				if "%ARG2%" EQU "" set ARG=XML_FILE_SOURCE
 				set PARSE_ERROR=1			
				if "%ARG3%" NEQ "" set PARSE_ERROR=0
				if "%ARG3%" NEQ "" set ARG=
				if "%ARG3%" NEQ "" set ERRORMSG=
 				if "%ARG3%" EQU "" set ARG=XSL_FILE_SOURCE
                shift
				shift
                GOTO:LOOPEND

            )
			
	set ERRORMSG=Execution Failed::Unknown parameter: %ARG1%
    call:USAGE
    exit /B 2
 
:LOOPEND
    shift
	if %debug%==1 echo.[DEBUG] %SCRIPT_DEBUG%: PARSE_ERROR=[%PARSE_ERROR%]  ARG=[%ARG%]  ERRORMSG=[%ERRORMSG%]
	if "%PARSE_ERROR%" == "1" (
		call:USAGE
		exit /B 2
	)
GOTO:LOOP

:: -------------------------------------------------------------
:USAGE
:: -------------------------------------------------------------
::# Usage Exit
:: -------------------------------------------------------------
	set ext=.bat
 	call %writeOutput% " -----------------------------------------------------------------------------------------------------"
	call %writeOutput% " "
 	if defined ERRORMSG call %writeOutput% " [%ERRORMSG%]"
	call %writeOutput% " "
	call %writeOutput% " USAGE: %SCRIPT%%ext% [-exec|-vcsinit|-encrypt|-ant] [property file name] [-vcsuser username] [-vcspassword password] [-config deploy.properties] [-release RELEASE_FOLDER] [-ver CIS_VERSION]"
	call %writeOutput% " "
 	if defined ARG call %writeOutput% " Argument [%ARG%] is missing or invalid."
	call %writeOutput% " CMD: %PARAMS%"
 	call %writeOutput% " "
	call %writeOutput% " -----------------------------------------------------------------------------------------------------"
	call %writeOutput% " Option 1 - Execute a command line deploy plan file:"
	call %writeOutput% " "
	call %writeOutput% "            %SCRIPT%%ext% -exec deploy-plan-file-path [-vcsuser username] [-vcspassword password] [-config deploy.properties] [-release RELEASE_FOLDER] [-ver CIS_VERSION]"
	call %writeOutput% " "
	call %writeOutput% "            Example: %SCRIPT%%ext% -exec ../resources/properties/myplan.dp -vcsuser user -vcspassword password -config deploy.properties -release 20141201 -ver 7.0.0"
	call %writeOutput% " "
	call %writeOutput% "               arg1::    -exec is used to execute a deploy plan file"
	call %writeOutput% "               arg2::    orchestration property file path [full or relative path]"
	call %writeOutput% "               arg3-4::  [-vcsuser username] optional parameters"
	call %writeOutput% "               arg5-6::  [-vcspassword password] optional parameters"
	call %writeOutput% "               arg7-8::  [-config deploy.properties] optional parameters"
	call %writeOutput% "               arg9-10:: [-release YYYYMMDD] optional parameter used to specify the release folder for the VCS"
	call %writeOutput% "               arg10-11::[-ver 7.0.0] optional parameter used to specify the version of CIS to connect to.
	call %writeOutput% " -----------------------------------------------------------------------------------------------------"
	call %writeOutput% " Option 2 - Execute VCS Workspace initialization:"
	call %writeOutput% " "
	call %writeOutput% "            %SCRIPT%%ext% -vcsinit [-vcsuser username] [-vcspassword password] [-config deploy.properties] [-release RELEASE_FOLDER]"
	call %writeOutput% " "
	call %writeOutput% "            Example: %SCRIPT%%ext% -vcsinit -vcsuser user -vcspassword password -config deploy.properties -release 20141201"
 	call %writeOutput% " "
	call %writeOutput% "               arg1::   -vcsinit is used to initialize the vcs workspace and link it to the repository"
	call %writeOutput% "               arg2::   [-vcsuser username] optional parameters"
	call %writeOutput% "               arg3-4:: [-vcspassword password] optional parameters"
	call %writeOutput% "               arg5-6:: [-config deploy.properties] optional parameters"
	call %writeOutput% "               arg7-8:: [-release YYYYMMDD] optional parameter used to specify the release folder for the VCS"
	call %writeOutput% " -----------------------------------------------------------------------------------------------------"
	call %writeOutput% " Option 3 - Execute Encrypt Property File:"
	call %writeOutput% " "
	call %writeOutput% "            %SCRIPT%%ext% -encrypt property-file-path [-config deploy.properties]"
	call %writeOutput% " "
	call %writeOutput% "            Example: %SCRIPT%%ext% -encrypt ../resources/config/deploy.properties -config deploy.properties"
 	call %writeOutput% " "
	call %writeOutput% "               arg1::   -encrypt is used to encrypt the passwords in deploy.properties or a Module XML property file"
	call %writeOutput% "               arg2::   file path to deploy.properties or XML property file [full or relative path]"
	call %writeOutput% "               arg3-4:: [-config deploy.properties] optional parameters"
	call %writeOutput% " -----------------------------------------------------------------------------------------------------"
	call %writeOutput% " Option 4 - Execute an Ant build file:"
	call %writeOutput% " "
	call %writeOutput% "            %SCRIPT%%ext% -ant build-file-path [-vcsuser username] [-vcspassword password] [-config deploy.properties] [-release RELEASE_FOLDER] [-ver CIS_VERSION]"
	call %writeOutput% " "
	call %writeOutput% "            Example: %SCRIPT%%ext% -ant ../resources/ant/build.xml -vcsuser user -vcspassword password -config deploy.properties -release 20141201 -ver 7.0.0"
	call %writeOutput% " "
	call %writeOutput% "               arg1::    -ant is used to execute an Ant build file"
	call %writeOutput% "               arg2::    orchestration build file path (full or relative path)"
	call %writeOutput% "               arg3-4::  [-vcsuser username] optional parameters"
	call %writeOutput% "               arg5-6::  [-vcspassword password] optional parameters"
	call %writeOutput% "               arg7-8::  [-config deploy.properties] optional parameters"
	call %writeOutput% "               arg9-10:: [-release YYYYMMDD] optional parameter used to specify the release folder for the VCS"
	call %writeOutput% "               arg10-11::[-ver 7.0.0] optional parameter used to specify the version of CIS to connect to.
	call %writeOutput% " -----------------------------------------------------------------------------------------------------"
	call %writeOutput% " "
	ENDLOCAL
	exit /B 1

:: -------------------------------------------------------------
:writeOutput
:: -------------------------------------------------------------
::# Write output to the console window
::#
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

REM # Get the parameters
set output=%1
set logprefix=%2

REM # Remove double quotes from the beginning and end of the string
if defined output set output=!output:~1,-1!
if defined logprefix set logprefix=!logprefix:~1,-1!

REM # Change 2 double quotes to a single quote
if defined output set output=!output:""="!

REM # Remove escape characters if they exist
if defined output set output=!output:^^=!

REM # Echo the line to the command window
echo.!logprefix!!output!

REM # Output to the default log file if the variable is defined
if not defined DEFAULT_LOG_PATH goto WRITEOUTPUTEND
if exist %DEFAULT_LOG_PATH% goto WRITEOUTPUT
REM # Create log directory and log file
for /f "tokens=* delims= " %%I in ("%DEFAULT_LOG_PATH%") do set DEFAULT_LOG_DRIVE=%%~dI
for /f "tokens=* delims= " %%I in ("%DEFAULT_LOG_PATH%") do set DEFAULT_LOG_DIR=%%~pI
set DEFAULT_LOG_DIR=%DEFAULT_LOG_DRIVE%%DEFAULT_LOG_DIR%
if not exist %DEFAULT_LOG_DIR% echo.mkdir %DEFAULT_LOG_DIR%
if not exist %DEFAULT_LOG_DIR% mkdir %DEFAULT_LOG_DIR%
echo.Initialize log file: %DEFAULT_LOG_PATH%
echo.>%DEFAULT_LOG_PATH%
:WRITEOUTPUT
if exist %DEFAULT_LOG_PATH% echo.!logprefix!!output!>>%DEFAULT_LOG_PATH%
:WRITEOUTPUTEND
ENDLOCAL
GOTO:EOF


:: -------------------------------------------------------------
:EVALUATE_SUBST_DRIVES
:: -------------------------------------------------------------
::# Evaluate the substitute drives
::#
::# Description: call:EVALUATE_SUBST_DRIVES substdrive substpath debug pathFound  
::#      -- substdrive [in]  - The drive letter such as P: that is to be used for a substitute drive
::#      -- substpath  [in]  - The path that is to be mapped to the substitute drive.
::#      -- debug      [in]  - 1=print debug, 0=do not pring debug
::#      -- pathFound  [out] - lower case "true" or "false" indicating whether the substitute drive and path combination was found in the substitute list.
::#
REM Get the input parameters
set substdrive=%1
set substpath=%2
set debug=%3

REM Set default variables
set trim=1
set _pathfound=false

SETLOCAL ENABLEDELAYEDEXPANSION
REM Remove double quotes around path
set substpath=!substpath:"=!
set pathfound=false
if %debug%==1 echo.[DEBUG] %0: substdrive=%substdrive%
if %debug%==1 echo.[DEBUG] %0:  substpath=%substpath%

REM Iterate over the subst command
for /f tokens^=*^ delims^=^ eol^= %%a in ('subst') do (
   set ln=%%a
   if !debug!==1 echo.[DEBUG] %0: Parse the following line: !ln!
   CALL :SPLIT_BY_TOKEN_EQU_GTR ln trim debug sdrive spath
   
   REM The drive was found
   if "!substdrive!\:" == "!sdrive!" (
     if !debug!==1 echo.[DEBUG] %0: !substdrive! drive found.  
     if !debug!==1 echo.[DEBUG] %0: subst cmd:      path=[!spath!]
     if !debug!==1 echo.[DEBUG] %0: Parameter: substpath=[%substpath%]
	 set msg=
     if "!substpath!" NEQ "!spath!" set msg=%PAD%The substitute path does not match subst drive list. drive=!substdrive! path=[!spath!].
	 if defined msg call %writeOutput% "!msg!" 																				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	 if "!substpath!" EQU "!spath!" set pathfound=true
	 if "!substpath!" NEQ "!spath!" set pathfound=false
	 goto LOOP_END
   )
)
:LOOP_END
ENDLOCAL & SET _pathfound=%pathfound%

if %debug%==1 echo.[DEBUG] %0: return parameter %4=%_pathfound%
set %4=%_pathfound%
GOTO:EOF


:: -------------------------------------------------------------
:EVALUATE_NETWORK_DRIVES
:: -------------------------------------------------------------
::# Evaluate the network drives
::#
::# Description: call:EVALUATE_NETWORK_DRIVES substdrive substpath debug pathFound  
::#      -- substdrive [in]  - The drive letter such as P: that is to be used for a network drive
::#      -- substpath  [in]  - The path that is to be mapped to the network drive.
::#      -- debug      [in]  - 1=print debug, 0=do not pring debug
::#      -- pathFound  [out] - lower case "true" or "false" indicating whether the network drive and path combination was found in the network drive list.
::#
REM Get the input parameters
set substdrive=%1
set substpath=%2
set debug=%3

REM Set default variables
set trim=1
set _pathfound=false
set COMPUTER_NAME=%COMPUTERNAME%

SETLOCAL ENABLEDELAYEDEXPANSION
REM Remove double quotes around path
set substpath=!substpath:"=!
set pathfound=false
set driveletter=!substpath:~0,1!
if %debug%==1 echo.[DEBUG] %0: substdrive=%substdrive%
if %debug%==1 echo.[DEBUG] %0:  substpath=%substpath%
if %debug%==1 echo.[DEBUG] %0:driveletter=%driveletter%

REM Iterate over the net use command
for /F "tokens=2,3 skip=2" %%a IN ('net use') do (
   set sdrive=%%a
   set spath=%%b
   if "!sdrive!"=="Local" set sdrive=
   if "!sdrive!"=="Windows" set sdrive=
   if "!sdrive!"=="command" set sdrive=
   if "!sdrive!"=="are" set sdrive=
   if "!sdrive!"=="connections" set sdrive=
   if not defined sdrive set spath=
   if !debug!==1 echo.[DEBUG] %0: sdrive=!sdrive!   path=!spath!

   set outStr=
   if defined spath call :REPLACE "\\%COMPUTER_NAME%\%driveletter%$" "%driveletter%:" "!spath!" spath 
   if !debug!==1 echo.[DEBUG] %0: spath=!spath!

   REM The drive was found
   if "!substdrive!" == "!sdrive!" (
     if !debug!==1 echo.[DEBUG] %0: !substdrive! drive found.  
     if !debug!==1 echo.[DEBUG] %0: subst cmd:      path=[!spath!]
     if !debug!==1 echo.[DEBUG] %0: Parameter: substpath=[%substpath%]
                set msg=
    if "!substpath!" NEQ "!spath!" set msg=%PAD%The network path does not match subst drive list. drive=!substdrive! path=[!spath!].
                rem if defined msg call %writeOutput% "!msg!"                                                                                                                                                                                                                                                                                                                               "%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
                if "!substpath!" EQU "!spath!" set pathfound=true
                if "!substpath!" NEQ "!spath!" set pathfound=false
                goto LOOP_END
   )
)
:LOOP_END
ENDLOCAL & SET _pathfound=%pathfound%

if %debug%==1 echo.[DEBUG] %0: return parameter %4=%_pathfound%
set %4=%_pathfound%
GOTO:EOF


::#----------------------------------------------------------
:REPLACE
::#----------------------------------------------------------
::# replace - parses a string and replaces old string with new string
::#           and returns the value in the outvariable that gets passed in
::# syntax:  call :REPLACE "oldstring" "newstring" "searchstring" outvariable
::# example: call :REPLACE "_" "__" "%searchStr%" outStr 
::# OldStr [in] - string to be replaced
::# NewStr [in] - string to replace with
::# SearchStr [in] - String to search
::# outvar [out] - name of the variable to place the results
::#
::# Remove double quotes (") for incoming SearchStr argument
SETLOCAL EnableDelayedExpansion
SET oldstring=%1
SET oldstring=!oldstring:"=!
SET newstring=%2
SET newstring=!newstring:"=!
SET searchstring=%3
SET searchstring=!searchstring:"=!
SET outvar=
if !debug!==1 echo.[DEBUG] %0: oldstring=[%oldstring%]  newstring=[%newstring%]  searchstring=[%searchstring%]

::# Don't continue if no text was passed in for the oldstring and searchstring.
if "%oldstring%   "=="" goto REPLACE_END
if "%searchstring%"=="" goto REPLACE_END

::# Perform the text replacement
call set "outvar=%%searchstring:%oldstring%=%newstring%%%"
if !debug!==1 echo.[DEBUG] %0:  REPLACE: outvar=!outvar!

:REPLACE_END
ENDLOCAL& set outvar=%outvar%
if !debug!==1 echo.[DEBUG] %0: ENDLOCAL: outvar=%outvar%
set %4=%outvar%
GOTO:EOF


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

::#---------------------------------------------
:resolveVariables
::#---------------------------------------------
::# Resolve variable usage.  Variable one is primary
::# Variable two is secondary.  If var one is not empty
::# then use it.  If var one is empty and var two is not
::# then use var two.
::# CALL:resolveVariable "%var1%" "%var2%" vret
::#   var1  [in] primary variable. use if not empty.
::#   var2  [in] secondary variable. use if var1 is empty.
::#   vret [out] return the variable
::#---------------------------------------------
set var1=%1
set var2=%2
REM # Remove double quotes
setlocal EnableDelayedExpansion
if defined var1 set tvar1=!var1:"=!
if defined var2 set tvar2=!var2:"=!
endlocal & SET var1=%tvar1%& SET var2=%tvar2%
set vret=
if "%var1%" NEQ "" set vret=%var1%
if "%var1%" EQU "" set vret=%var2%
set %3=%vret%
GOTO:EOF


:: -------------------------------------------------------------
:SPLIT_BY_TOKEN_EQU_GTR
:: -------------------------------------------------------------
::# Split the string based on the token [=>]
::# Description: call:SPLIT_BY_TOKEN instring trim lstring rstring
::#      -- instring [in]  - variable name containing the string to be split
::#      -- trim     [in]  - 1=trim, 0=do not trim the strings
::#      -- debug    [in]  - 1=print debug, 0=do not pring debug
::#      -- lstring  [out] - variable name containing the result of the left split string
::#      -- rstring  [out] - variable name containing the result of the right split string
SET str=!%1!
SET trim=!%2!
SET debug=!%3!
if %debug%==1 echo.[DEBUG]    split the string based on a token
if %debug%==1 echo.[DEBUG]    trim=%trim%, debug=%debug%, str=!str!
for /f "tokens=1,2 delims==>" %%a in ("%str%") do set leftStr=%%a&set rightStr=%%b
if %trim% == 1 (
  if %debug%==1 echo.[DEBUG]    leftStr=[%leftStr%]
  if %debug%==1 echo.[DEBUG]    rightStr=[%rightStr%]
  if %debug%==1 echo.[DEBUG]    Trim strings
  CALL:LTRIM leftStr leftStr
  CALL:RTRIM leftStr leftStr
  CALL:LTRIM rightStr rightStr
  CALL:RTRIM rightStr rightStr
)
if %debug%==1 echo.[DEBUG]    leftStr=[%leftStr%]
if %debug%==1 echo.[DEBUG]    rightStr=[%rightStr%]
SET %4=%leftStr%
SET %5=%rightStr%
GOTO:EOF

:: -------------------------------------------------------------
:LTRIM
:: -------------------------------------------------------------
::# Trim right
::# Description: call:LTRIM instring outstring  
::#      -- instring  [in]  - variable name containing the string to be trimmed on the left
::#      -- outstring [out] - variable name containing the result string
SET str=!%1!
rem echo."%str%"
for /l %%a in (1,1,31) do if "!str:~-1!"==" " set str=!str:~0,-1!
rem echo."%str%"
SET %2=%str%
GOTO:EOF

:: -------------------------------------------------------------
:RTRIM
:: -------------------------------------------------------------
::# Trim left
::# Description: call:RTRIM instring outstring  
::#      -- instring  [in]  - variable name containing the string to be trimmed on the right
::#      -- outstring [out] - variable name containing the result string
SET str=!%1!
rem echo."%str%"
for /f "tokens=* delims= " %%a in ("%str%") do set str=%%a
rem echo."%str%"
SET %2=%str%
GOTO:EOF

REM ############################################################
REM # FUNCTIONS: END
REM ############################################################
