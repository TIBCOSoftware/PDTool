@echo off
SETLOCAL
REM ######################################################################
REM # (c) 2014 Cisco and/or its affiliates. All rights reserved.
REM ######################################################################
REM #=======================================================================================
REM # Example Execution Statement:
REM # Option 1 - Execute a command line deploy plan file:
REM #
REM #            ExecutePDTool.bat -exec deploy-plan-file-path [-vcsuser username] [-vcspassword password] [-config deploy.properties]
REM #
REM #               arg1=-exec is used to execute a deploy plan file
REM #	            arg2=orchestration propertiy file path (full or relative path)
REM #               arg3-4=[-vcsuser username] optional parameters
REM #               arg5-6=[-vcspassword password] optional parameters
REM #				arg7-8=[-config deploy.properties] optional parameters"
REM #
REM # Option 2 - Execute VCS Workspace initialization:
REM #
REM #            ExecutePDTool.bat -vcsinit [-vcsuser username] [-vcspassword password] [-config deploy.properties]
REM #
REM #	            arg1=-vcsinit is used to initialize the vcs workspace and link it to the repository
REM #               arg3-4=[-vcsuser username] optional parameters
REM #               arg5-6=[-vcspassword password] optional parameters
REM #				arg7-8=[-config deploy.properties] optional parameters"
REM #
REM # Option 3 - Execute property file encryption:
REM #
REM #            ExecutePDTool.bat -encrypt property-file-path [-config deploy.properties]
REM #
REM #	            arg1=-encrypt is used to encrypt the passwords in deploy.properties or a Module XML property file
REM #	            arg2=file path to deploy.properties or XML property file (full or relative path)
REM #				arg3-4=[-config deploy.properties] optional parameters"
REM #
REM # Option 4 - Execute an Ant build file:
REM #
REM #            ExecutePDTool.bat -ant build-file-path [-vcsuser username] [-vcspassword password] [-config deploy.properties]
REM #
REM #               arg1=-ant is used to execute an Ant build file
REM #	            arg2=orchestration build file path (full or relative path)
REM #               arg3-4=[-vcsuser username] optional parameters
REM #               arg5-6=[-vcspassword password] optional parameters
REM #				arg7-8=[-config deploy.properties] optional parameters"
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
	call %writeOutput% " USAGE: %SCRIPT%%ext% [-exec|-vcsinit|-encrypt|-ant] [property file name] [-vcsuser username] [-vcspassword password] [-config deploy.properties]"
	call %writeOutput% " "
 	call %writeOutput% " Argument [%ARG%] is missing or invalid."
	call %writeOutput% " CMD: %PARAMS%"
 	call %writeOutput% " "
	call %writeOutput% " -----------------------------------------------------------------------------------------------------"
	call %writeOutput% " Option 1 - Execute a command line deploy plan file:"
	call %writeOutput% " "
	call %writeOutput% "            %SCRIPT%%ext% -exec deploy-plan-file-path [-vcsuser username] [-vcspassword password] [-config deploy.properties]"
	call %writeOutput% " "
	call %writeOutput% "            Example: %SCRIPT%%ext% -exec ../resources/properties/myplan.dp -vcsuser user -vcspassword password -config deploy.properties"
	call %writeOutput% " "
	call %writeOutput% "               arg1::   -exec is used to execute a deploy plan file"
	call %writeOutput% "               arg2::   orchestration propertiy file path [full or relative path]"
	call %writeOutput% "               arg3-4:: [-vcsuser username] optional parameters"
	call %writeOutput% "               arg5-6:: [-vcspassword password] optional parameters"
	call %writeOutput% "               arg7-8:: [-config deploy.properties] optional parameters"
	call %writeOutput% " -----------------------------------------------------------------------------------------------------"
	call %writeOutput% " Option 2 - Execute VCS Workspace initialization:"
	call %writeOutput% " "
	call %writeOutput% "            %SCRIPT%%ext% -vcsinit [-vcsuser username] [-vcspassword password] [-config deploy.properties]"
	call %writeOutput% " "
	call %writeOutput% "            Example: %SCRIPT%%ext% -vcsinit -vcsuser user -vcspassword password -config deploy.properties"
 	call %writeOutput% " "
	call %writeOutput% "               arg1::   -vcsinit is used to initialize the vcs workspace and link it to the repository"
	call %writeOutput% "               arg2::   [-vcsuser username] optional parameters"
	call %writeOutput% "               arg3-4:: [-vcspassword password] optional parameters"
	call %writeOutput% "               arg5-6:: [-config deploy.properties] optional parameters"
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
	call %writeOutput% "            %SCRIPT%%ext% -ant build-file-path [-vcsuser username] [-vcspassword password] [-config deploy.properties]"
	call %writeOutput% " "
	call %writeOutput% "            Example: %SCRIPT%%ext% -ant ../resources/ant/build.xml -vcsuser user -vcspassword password -config deploy.properties"
	call %writeOutput% " "
	call %writeOutput% "               arg1::   -ant is used to execute an Ant build file"
	call %writeOutput% "               arg2::   orchestration build file path (full or relative path)"
	call %writeOutput% "               arg3-4:: [-vcsuser username] optional parameters"
	call %writeOutput% "               arg5-6:: [-vcspassword password] optional parameters"
	call %writeOutput% "               arg7-8:: [-config deploy.properties] optional parameters"
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
    if "%1"=="" GOTO:EOF
    if "%1"=="-exec" (
				SET CMD=%1
                SET PROPERTY_FILE=%2
				if not defined PROPERTY_FILE set error=1
				set ARG=PROPERTY_FILE
                shift
                GOTO:LOOPEND
            )
    if "%1"=="-ant" (
 				SET CMD=%1
				SET PROPERTY_FILE=%2
				if not defined PROPERTY_FILE set error=1
				set ARG=PROPERTY_FILE
                shift
                GOTO:LOOPEND
            )
    if "%1"=="-encrypt" (
				SET CMD=%1
                SET PROPERTY_FILE=%2
				if not defined PROPERTY_FILE set error=1
				set ARG=PROPERTY_FILE
                shift
                GOTO:LOOPEND
            )
    if "%1"=="-vcsinit" (
                SET CMD=%1
                GOTO:LOOPEND
            )
    if "%1"=="-vcsuser" (
                SET VCS_USERNAME=%2
				if not defined VCS_USERNAME set error=1
				set ARG=VCS_USERNAME
                shift
                GOTO:LOOPEND
            )
    if "%1"=="-vcspassword" (
                SET VCS_PASSWORD=%2
				if not defined VCS_PASSWORD set error=1
				set ARG=VCS_PASSWORD
                shift
                GOTO:LOOPEND
            )
    if "%1"=="-config" (
                SET CONFIG_PROPERTY_FILE=%2
				if not defined CONFIG_PROPERTY_FILE set error=1
				set ARG=CONFIG_PROPERTY_FILE
                shift
                GOTO:LOOPEND
            )
    echo Unknown parameter: %1
    call:USAGE
    exit /B 2
 
:LOOPEND
    shift
	if "%error%"=="1" (
		call:USAGE
		exit /B 2
	)
GOTO:LOOP


REM #----------------------------------------------------------
REM # BEGIN SCRIPT
REM #-----------------------------------------------------------
:BEGIN
REM #=======================================
REM # Set up the execution context for invoking common scripts
REM #=======================================
set SCRIPT=ExecutePDTool
REM # set the print function
set writeOutput=:writeOutput
set SEP=::
REM # Print out the Banner
call %writeOutput% " " 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% "-----------                                            -----------" 
call %writeOutput% "----------- Composite PS Promotion and Deployment Tool -----------" 
call %writeOutput% "-----------                                            -----------" 
call %writeOutput% "------------------------------------------------------------------" 
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
    echo Section: Substitute Drives
    if EXIST %SUBSTITUTE_DRIVE% goto UNMAP
    GOTO MAP
:UNMAP
    subst /D %SUBSTITUTE_DRIVE%
    set ERROR=%ERRORLEVEL%
    if "%ERROR%"=="0" goto MAP
    echo An error occurred trying to unmap substitute drive=%SUBSTITUTE_DRIVE% ERROR=%ERROR%
    echo Execute this command manually: subst /D %SUBSTITUTE_DRIVE%
    exit /b 1
:MAP
    echo Substitute drive %SUBSTITUTE_DRIVE% for path "%PROJECT_HOME_PHYSICAL%"
    subst %SUBSTITUTE_DRIVE% "%PROJECT_HOME_PHYSICAL%"
    set ERROR=%ERRORLEVEL%
    if "%ERROR%"=="0" goto MAPSUCCESS
    echo An error occurred trying to map substitute drive=%SUBSTITUTE_DRIVE% ERROR=%ERROR%
    echo If drive %SUBSTITUTE_DRIVE% exists then execute command: subst /D %SUBSTITUTE_DRIVE%
    echo Execute this command manually: subst %SUBSTITUTE_DRIVE% "%PROJECT_HOME_PHYSICAL%"
    echo Re-execute %0
    exit /b 1
)
:MAPSUCCESS   

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
call %writeOutput% " " 
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
set DEPLOY_CLASSPATH="%PROJECT_HOME%\dist\*;%PROJECT_HOME%\lib\*"
set ENDORSED_DIR=%PROJECT_HOME%\lib\endorsed
set DEPLOY_MANAGER=com.cisco.dvbu.ps.deploytool.DeployManagerUtil
set DEPLOY_COMMON_UTIL=com.cisco.dvbu.ps.common.scriptutil.ScriptUtil
set CONFIG_LOG4J=-Dlog4j.configuration="file:%PROJECT_HOME%\resources\config\log4j.properties" 
set CONFIG_ROOT=-Dcom.cisco.dvbu.ps.configroot="%PROJECT_HOME%\resources\config"
REM #=======================================
REM # Parameter Validation
REM #=======================================
set PARAMS=%0 %1 %2 %3 %4 %5 %6 %7 %8 %9
call :parse %*
IF ERRORLEVEL 2 ( exit /B %ERRORLEVEL% )

if "%CMD%" == "-exec" goto SETUP_EXEC
if "%CMD%" == "-vcsinit" goto SETUP_VCSINIT
if "%CMD%" == "-encrypt" goto SETUP_ENCRYPT
if "%CMD%" == "-ant" goto SETUP_ANT
set arg=1
GOTO USAGE

:--------------
:SETUP_EXEC
:--------------
call %writeOutput% " " 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% "-------------------- COMMAND-LINE DEPLOYMENT ---------------------" 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% " " 

set	PR_VCS_PASSWORD= 
if defined VCS_PASSWORD set PR_VCS_PASSWORD=********
REM # Goto usage if PROPERTY_FILE is blank or does not exist
set ARG=PROPERTY_FILE
if not defined PROPERTY_FILE goto USAGE 
if NOT EXIST "%PROPERTY_FILE%" (
   call %writeOutput% "Execution Failed::Orchestration deploy plan file does not exist: %PROPERTY_FILE%" 					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   goto USAGE 
)
REM #***********************************************
REM # Invoke: DeployManagerUtil execCisDeployTool "%PROPERTY_FILE%" "%VCS_USERNAME%" "%VCS_PASSWORD%"
REM #***********************************************
set JAVA_ACTION=execCisDeployTool
set   COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DPROJECT_HOME_PHYSICAL="%PROJECT_HOME_PHYSICAL%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% "%PROPERTY_FILE%" "%VCS_USERNAME%" "%VCS_PASSWORD%"
set PRCOMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DPROJECT_HOME_PHYSICAL="%PROJECT_HOME_PHYSICAL%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% "%PROPERTY_FILE%" "%VCS_USERNAME%" "%PR_VCS_PASSWORD%"
GOTO START_SCRIPT

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
REM # Invoke: DeployManagerUtil vcsInitWorkspace "%VCS_USERNAME%" "%VCS_PASSWORD%"
REM #***********************************************
set JAVA_ACTION=vcsInitWorkspace
set   COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DPROJECT_HOME_PHYSICAL="%PROJECT_HOME_PHYSICAL%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% "%VCS_USERNAME%" "%VCS_PASSWORD%"
set PRCOMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DPROJECT_HOME_PHYSICAL="%PROJECT_HOME_PHYSICAL%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% "%VCS_USERNAME%" "%PR_VCS_PASSWORD%"
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
   call %writeOutput% "Execution Failed::Property file does not exist: %PROPERTY_FILE%" 									"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   goto USAGE 
)
REM #***********************************************
REM # Invoke: DeployManagerUtil encryptPasswordsInFile "%PROPERTY_FILE%"
REM #***********************************************
set JAVA_ACTION=encryptPasswordsInFile
set COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DPROJECT_HOME_PHYSICAL="%PROJECT_HOME_PHYSICAL%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_COMMON_UTIL% %JAVA_ACTION% "%PROPERTY_FILE%"
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

set	PR_VCS_PASSWORD= 
if defined VCS_PASSWORD set PR_VCS_PASSWORD=********
if not defined VCS_USERNAME set VCS_USERNAME= 
if not defined VCS_PASSWORD set VCS_PASSWORD= 

REM # Goto usage if PROPERTY_FILE is blank or does not exist
set ARG=PROPERTY_FILE
if not defined PROPERTY_FILE goto USAGE 
if NOT EXIST "%PROPERTY_FILE%" (
   call %writeOutput% "Execution Failed::Ant deploy plan build file does not exist: %PROPERTY_FILE%" 						"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
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
set ANT_CLASSPATH=%PROJECT_HOME%\dist;%PROJECT_HOME%\lib;%PROJECT_HOME%\lib\endorsed;%ANT_HOME%\lib
set ANT_OPTS=%CONFIG_LOG4J% %CONFIG_ROOT% -Djava.endorsed.dirs="%ENDORSED_DIR%"

REM #***********************************************
REM # Invoke: ant -buildfile
REM #***********************************************

set   COMMAND="%ANT_HOME%/bin/ant" -lib "%ANT_CLASSPATH%" -DPROJECT_HOME="%PROJECT_HOME%" -DPROJECT_HOME_PHYSICAL="%PROJECT_HOME_PHYSICAL%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% -DVCS_USERNAME="%VCS_USERNAME%" -DVCS_PASSWORD="%VCS_PASSWORD%" -buildfile "%PROPERTY_FILE%"
set PRCOMMAND="%ANT_HOME%/bin/ant" -lib "%ANT_CLASSPATH%" -DPROJECT_HOME="%PROJECT_HOME%" -DPROJECT_HOME_PHYSICAL="%PROJECT_HOME_PHYSICAL%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% -DVCS_USERNAME="%VCS_USERNAME%" -DVCS_PASSWORD="%PR_VCS_PASSWORD%" -buildfile "%PROPERTY_FILE%"
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
ENDLOCAL
exit /B 0