@echo off
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
REM #=======================================================================================
REM # Example Execution Statement:
REM # checkout.bat [CIS-resource-path] [CIS-resource-type] [rollback-revision] [vcs-workspace-project-folder] [vcs-temp-folder]
REM # 
REM # Parameters
REM # ----------
REM # %1 ->  Resource path 			(e.g. /shared/MyFolder/My__View), using file system (encoded) names
REM # %2 ->  Resource type 			(e.g. FOLDER, table, procedure etc.)
REM # %3 ->  Rollback revision  	(e.g. HEAD)
REM # %4 ->  VCS Workspace Folder 	(e.g. C:\Temp\workspaces\workspace_CIS)
REM # %5 ->  VCS Temp Folder 		(e.g. C:\Temp\workspaces\temp_CIS)
REM #=======================================================================================
REM #------------------------------------------
REM # Set constants
REM #------------------------------------------
if not defined debug set debug=0
REM # CIS version [6.2, 7.0.0] - set CIS_VERSION
if exist cisVersion.bat call cisVersion.bat
REM # Initialize variables
set SCRIPT=checkout_novars.bat
set SEP=::
set PREFIX_ERR=Execution Failed::%SCRIPT%%SEP%
REM # set the print function
set writeOutput=:writeOutput

REM #------------------------------------------
REM # Set incoming arguments
REM #------------------------------------------
REM # The first parameter is the current execution path
set CURRENT_EXEC_PATH=%1
shift
REM # Remove double quotes
setlocal EnableDelayedExpansion
if defined CURRENT_EXEC_PATH set TCURRENT_EXEC_PATH=!CURRENT_EXEC_PATH:"=!
endlocal & SET CURRENT_EXEC_PATH=%TCURRENT_EXEC_PATH%

REM # The second parameter is the path to setVars.bat
set DEFAULT_SET_VARS_PATH=%1
shift
REM # Remove double quotes
setlocal EnableDelayedExpansion
if defined DEFAULT_SET_VARS_PATH set TDEFAULT_SET_VARS_PATH=!DEFAULT_SET_VARS_PATH:"=!
endlocal & SET DEFAULT_SET_VARS_PATH=%TDEFAULT_SET_VARS_PATH%

REM # Get the rest of the variables passed in from CIS Studio
SET ResPath=%1
SET ResType=%2
SET Revision=%3
SET Workspace=%4
SET VcsTemp=%5

REM # Convert Revision to upper case
if "%Revision%" == "head" set Revision=HEAD
if "%Revision%" == "Head" set Revision=HEAD

REM #---------------------------------------------
REM # Display Arguments
REM #---------------------------------------------
REM # Print out the Banner
call %writeOutput% " " 
call %writeOutput% "----------------------------------------------------------" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "---                                                    ---" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "--- Cisco Advanced Services                            ---" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "--- PDToolStudio: Promotion and Deployment Tool Studio ---" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "---                                                    ---" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "----------------------------------------------------------" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "--- Execution path: [%CURRENT_EXEC_PATH%]" 																"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "--- CIS_VERSION=[%CIS_VERSION%]" 																		"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "----------------------------------------------------------" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "***** BEGIN COMMAND: %SCRIPT% *****" 																	"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% " " 
call %writeOutput% "......................................" 																"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "Input Arguments:" 																						"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "......................................" 																"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "Resource Path="%ResPath%"" 																				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "Resource Type="%ResType%"" 																				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "Revision="%Revision%"" 																					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "Workspace="%Workspace%"" 																				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "Temp Folder="%VcsTemp%"" 																				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% " " 

REM #------------------------------------------
REM # Perform argument validations
REM #------------------------------------------
call %writeOutput% "......................................" 																"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "Perform argument validations:" 																			"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "......................................" 																"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"

if not defined ResPath (
   call %writeOutput% "%PREFIX_ERR%ResPath has no value. Error=111." 														"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   exit /B 111
)
if not defined ResType (
   call %writeOutput% "%PREFIX_ERR%ResType has no value. Error=112." 														"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   exit /B 112
)
if not defined Workspace (
   call %writeOutput% "%PREFIX_ERR%Workspace has no value.. Error=113." 													"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   exit /B 113
)
if not defined VcsTemp (
   call %writeOutput% "%PREFIX_ERR%VcsTemp has no value.. Error=115." 														"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   exit /B 115
)
if not defined Revision (
   call %writeOutput% "%PREFIX_ERR%Revision has no value.  Set to HEAD or a revision # [e.g. 1]. Error=124." 				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   exit /B 124
)
call %writeOutput% "Arguments are valid." 																					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"

REM #=======================================
REM # Invoke setVars.bat
REM #=====================================================================================
if exist %DEFAULT_SET_VARS_PATH% goto INVOKE_SET_VARS
   call %writeOutput% "%PREFIX_ERR%::Path does not exist: %DEFAULT_SET_VARS_PATH%" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   ENDLOCAL
   exit /B 2
			
:INVOKE_SET_VARS
REM #---------------------------------------------
REM # Set environment variables
REM #---------------------------------------------
call %writeOutput% "----------------------------------------------------------" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "--- Invoking setVars.bat...%DEFAULT_SET_VARS_PATH%"			 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "----------------------------------------------------------" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %DEFAULT_SET_VARS_PATH% 


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
	call %writeOutput% "Section: Substitute Drives" 																							"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%Evaluate the substitute drive=%SUBSTITUTE_DRIVE% path=[%PROJECT_HOME_PHYSICAL%]" 									"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	REM 2014-12-04 mtinius - changed logic to only map the drive when necessary...not every time
	if NOT EXIST %SUBSTITUTE_DRIVE% goto MAP_SUBSTITUTE_DRIVE
	REM 2014-12-04 mtinius - changed logic to evaluate if current mapped drive utilizes the correct path
	call :EVALUATE_SUBST_DRIVES %SUBSTITUTE_DRIVE% "%PROJECT_HOME_PHYSICAL%" %debug% drivePathFound

	if "%drivePathFound%" == "false" goto MAP_SUBSTITUTE_DRIVE_ERROR
	call %writeOutput% "%PAD%Substitute drive %SUBSTITUTE_DRIVE% is already mapped to path:[%PROJECT_HOME_PHYSICAL%]" 							"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	goto MAPSUCCESS

	REM The drive is already mapped to a different path so exit with an error and have the user unmap the drive manually
	:MAP_SUBSTITUTE_DRIVE_ERROR
	call %writeOutput% "%PAD%The substitute drive=%SUBSTITUTE_DRIVE% is mapped to a different path.  There are two options:" 					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%  Option 1: Change the drive letter designated by the variable SUBSTITUTE_DRIVE in setVars.bat." 					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%  Option 2: Unmap the drive by executing this command manually: subst /D %SUBSTITUTE_DRIVE%" 						"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%Re-execute %SCRIPT%.bat" 																							"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	if defined PWD cd %PWD%
    exit /b 1
:MAP_SUBSTITUTE_DRIVE
	call %writeOutput% "%PAD%Execute substitute drive command:  subst %SUBSTITUTE_DRIVE% [%PROJECT_HOME_PHYSICAL%]" 							"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
    subst %SUBSTITUTE_DRIVE% "%PROJECT_HOME_PHYSICAL%"
    set ERROR=%ERRORLEVEL%
    if "%ERROR%" == "0" goto MAPSUCCESS
	call %writeOutput% "%PAD%An error=%ERROR% occurred executing command: subst %SUBSTITUTE_DRIVE% [%PROJECT_HOME_PHYSICAL%]" 					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%Execute this command manually: subst %SUBSTITUTE_DRIVE% [%PROJECT_HOME_PHYSICAL%]"									"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%Re-execute %SCRIPT%.bat" 																							"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	if defined PWD cd %PWD%
    exit /b 1

REM #-----------------------------------------------------------
REM # This section creates a substitute drive using "net use"
REM # The advantage over "subst" is that this is permanent while
REM #   "subst" is temporary in that id does not survive reboots.
REM #-----------------------------------------------------------
:MAP_NETWORK_DRIVE_BEGIN
if not defined NETWORK_DRIVE goto MAPSUCCESS
	call %writeOutput% "Section: Network Drives" 																								"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%Evaluate the network drive=%NETWORK_DRIVE% path=[%PROJECT_HOME_PHYSICAL%]"											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	REM Only map the drive when necessary...not every time
	if NOT EXIST "%NETWORK_DRIVE%" goto MAP_NETWORK_DRIVE
	REM Evaluate if current mapped drive utilizes the correct path
	call :EVALUATE_NETWORK_DRIVES %NETWORK_DRIVE% "%PROJECT_HOME_PHYSICAL%" %debug% drivePathFound

	if "%drivePathFound%" == "false" goto MAP_NETWORK_DRIVE_ERROR
	call %writeOutput% "%PAD%Network drive %NETWORK_DRIVE% is already mapped to path: [%PROJECT_HOME_PHYSICAL%]" 								"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	goto MAPSUCCESS

	REM The drive is already mapped to a different path so exit with an error and have the user unmap the drive manually
	:MAP_NETWORK_DRIVE_ERROR
	call %writeOutput% "%PAD%The network drive=%NETWORK_DRIVE% is mapped to a different path.  There are two options:" 							"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%  Option 1: Change the drive letter designated by the variable NETWORK_DRIVE in setVars.bat." 						"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%  Option 2: Unmap the drive by executing this command manually: net use %NETWORK_DRIVE% /DELETE" 					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%Re-execute %SCRIPT%.bat" 																							"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	if defined PWD cd %PWD%
    exit /b 1
:MAP_NETWORK_DRIVE
	REM # Get the Drive letter for PROJECT_HOME_PHYSICAL
	for /F "usebackq delims==" %%I IN (`echo "%PROJECT_HOME_PHYSICAL%"`) DO (
		set PDTOOL_HOME_DRIVE=%%~dI
		REM echo PDTOOL_HOME_DRIVE=%PDTOOL_HOME_DRIVE%
	)
	set PDTOOL_HOME_DRIVE_SHARE=%PDTOOL_HOME_DRIVE::=$%
	if not exist "\\%COMPUTERNAME%\%PDTOOL_HOME_DRIVE_SHARE%" (
		REM The share drive does not exist
		call %writeOutput% "%PAD%The network share [\\%COMPUTERNAME%\%PDTOOL_HOME_DRIVE_SHARE%] does not exist." 								"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
		call %writeOutput% "%PAD%   Create the share drive=[%PDTOOL_HOME_DRIVE_SHARE%] for the installation drive=[%PDTOOL_HOME_DRIVE%] as administrator."%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
		call %writeOutput% "%PAD%   Command run as administrator: net share [%PDTOOL_HOME_DRIVE_SHARE%=%PDTOOL_HOME_DRIVE%]"	 				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
		call %writeOutput% "%PAD%Re-execute %SCRIPT%.bat" 																						"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
		if defined PWD cd %PWD%
		exit /b 1
	)
	
	call :REPLACE "%PDTOOL_HOME_DRIVE%" "\\%COMPUTERNAME%\%PDTOOL_HOME_DRIVE_SHARE%" "%PROJECT_HOME_PHYSICAL%" PROJECT_HOME_PHYSICAL_NEW
	call %writeOutput% "%PAD%Execute network drive command:  net use %NETWORK_DRIVE% [%PROJECT_HOME_PHYSICAL_NEW%] /PERSISTENT:YES"				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
    net use %NETWORK_DRIVE% "%PROJECT_HOME_PHYSICAL_NEW%" /PERSISTENT:YES
    set ERROR=%ERRORLEVEL%
    if "%ERROR%" == "0" goto MAPSUCCESS
	call %writeOutput% "%PAD%An error=%ERROR% occurred executing command: net use %NETWORK_DRIVE% [%PROJECT_HOME_PHYSICAL_NEW%] /PERSISTENT:YES" "%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%Execute this command manually: net use %NETWORK_DRIVE% [%PROJECT_HOME_PHYSICAL_NEW%] /PERSISTENT:YES" 				 "%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	call %writeOutput% "%PAD%Re-execute %SCRIPT%.bat" 																							 "%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	if defined PWD cd %PWD%
    exit /b 1

:MAPSUCCESS


REM #=======================================
REM # Validate Paths exist
REM #=======================================
if NOT EXIST "%PROJECT_HOME%" (
   call %writeOutput% "%PREFIX_ERR%::PPROJECT_HOME does not exist: %PROJECT_HOME%" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   ENDLOCAL
   exit /B 1
)
if NOT EXIST "%JAVA_HOME%" (
   call %writeOutput% "%PREFIX_ERR%::JAVA_HOME does not exist: %JAVA_HOME%" 												"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   ENDLOCAL
   exit /B 1
)
call %writeOutput% " "  

REM #=======================================
REM # Set DeployManager Environment Variables
REM #=======================================
set DEPLOY_CLASSPATH="%PROJECT_HOME%\dist\*;%PROJECT_HOME%\lib\*"
set ENDORSED_DIR=%PROJECT_HOME%\lib\endorsed
set DEPLOY_MANAGER=com.cisco.dvbu.ps.deploytool.DeployManagerUtil
set CONFIG_LOG4J=-Dlog4j.configuration="file:%PROJECT_HOME%\resources\config\log4j.properties" 
set CONFIG_ROOT=-Dcom.cisco.dvbu.ps.configroot="%PROJECT_HOME%\resources\config"

REM #=======================================
REM # Precedence evaluation
REM #=======================================
set PRECEDENCE=
if defined propertyOrderPrecedence set PRECEDENCE=-DpropertyOrderPrecedence="%propertyOrderPrecedence%"

REM #---------------------------------------------
REM # Begin Processing
REM #---------------------------------------------

REM #***********************************************
REM # Invoke: DeployManagerUtil vcsStudioCheckout
REM #***********************************************
set JAVA_ACTION=vcsStudioCheckout
set COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% %PRECEDENCE% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% %ResPath% %ResType% %Revision% %Workspace% %VcsTemp%

REM #=======================================
REM # Execute the script
REM #=======================================
REM Escape (") in the COMMAND with ("") before printing
call %writeOutput% " " 
call %writeOutput% "-- COMMAND: %JAVA_ACTION% ----------------------" 														"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% " " 
call %writeOutput% "%COMMAND:"=""%" 
call %writeOutput% " " 
call %writeOutput% "-- BEGIN OUTPUT ------------------------------------" 													"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% " " 

REM #------------------------------------------------------------------------
REM # Execute the command line CIS script
REM #------------------------------------------------------------------------
call %COMMAND%
set ERROR=%ERRORLEVEL%
if %ERROR% NEQ 0 (
   call %writeOutput% "%PREFIX_ERR%::Abnormal Script Termination. Exit code is: %ERROR%." 									"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"	
   ENDLOCAL
   exit /B %ERROR%
)

REM #=======================================
REM # Successful script completion
REM #=======================================
call %writeOutput% " " 
call %writeOutput% "-------------- SUCCESSFUL SCRIPT COMPLETION [%SCRIPT% %CMD%] --------------" 							"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "End of script." 																						"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
ENDLOCAL
exit /B 0

REM ############################################################
REM # FUNCTIONS: BEGIN
REM ############################################################

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
if exist "%DEFAULT_LOG_PATH%" goto WRITEOUTPUT
REM # Create log directory and log file
for /f "tokens=* delims= " %%I in ("%DEFAULT_LOG_PATH%") do set DEFAULT_LOG_DRIVE=%%~dI
for /f "tokens=* delims= " %%I in ("%DEFAULT_LOG_PATH%") do set DEFAULT_LOG_DIR=%%~pI
set DEFAULT_LOG_DIR=%DEFAULT_LOG_DRIVE%%DEFAULT_LOG_DIR%
if not exist "%DEFAULT_LOG_DIR%" echo.mkdir "%DEFAULT_LOG_DIR%"
if not exist "%DEFAULT_LOG_DIR%" mkdir "%DEFAULT_LOG_DIR%"
echo.Initialize log file: %DEFAULT_LOG_PATH%
echo.>"%DEFAULT_LOG_PATH%"
:WRITEOUTPUT
if exist "%DEFAULT_LOG_PATH%" echo.!logprefix!!output!>>"%DEFAULT_LOG_PATH%"
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
	 if defined msg call %writeOutput% "!msg!" 																	"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
	 if "!substpath!" EQU "!spath!" set pathfound=true
	 if "!substpath!" NEQ "!spath!" set pathfound=false
	 goto EVALUATE_SUBST_DRIVES_LOOP_END
   )
)
:EVALUATE_SUBST_DRIVES_LOOP_END
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
for /F "tokens=2,3* skip=2" %%a IN ('net use') do (
	set sdrive=%%a
	set spath=%%b
	set spathRemainder=%%c
	call:RTRIM spath spath
	call:RTRIM spathRemainder spathRemainder
	if !debug!==1 echo.[DEBUG] %0: spathRemainder=[!spathRemainder!]
	if "!spathRemainder!" NEQ "" set spath=!spath! !spathRemainder!
	if "!sdrive!"=="Local" set sdrive=
	if "!sdrive!"=="Windows" set sdrive=
	if "!sdrive!"=="command" set sdrive=
	if "!sdrive!"=="are" set sdrive=
	if "!sdrive!"=="connections" set sdrive=
	if not defined sdrive set spath=
	if !debug!==1 echo.[DEBUG] %0: sdrive=[!sdrive!]  path=[!spath!]
	set outStr=
	if defined sdrive call :EVALUATE_NETWORK_DRIVES_LOGIC pathfound
	if !debug!==1 echo.[DEBUG] %0:             pathfound=[!pathfound!]
	if "!pathfound!"=="true" goto EVALUATE_NETWORK_DRIVES_LOOP_END
)
:EVALUATE_NETWORK_DRIVES_LOOP_END
ENDLOCAL & SET _pathfound=%pathfound%

if %debug%==1 echo.[DEBUG] %0: return parameter %4=%_pathfound%
set %4=%_pathfound%
GOTO:EOF

:: --------------------------------------------------------------------
:EVALUATE_NETWORK_DRIVES_LOGIC
:: --------------------------------------------------------------------
::# Sub-procedure to EVALUATE_NETWORK_DRIVES
::#####################################################################
	set pathfound=false
	if !debug!==1 echo.[DEBUG] %0: network: drive=[!sdrive!]   path=[!spath!]
	if !debug!==1 echo.[DEBUG] %0: call:REPLACE "\\%COMPUTER_NAME%\%driveletter%$" "%driveletter%:" "!spath!" spath
	call :REPLACE "\\%COMPUTER_NAME%\%driveletter%$" "%driveletter%:" "!spath!" spath
	if !debug!==1 echo.[DEBUG] %0: replaced network path=[!spath!]
	if !debug!==1 echo.[DEBUG] %0:  Parameter: substpath=[%substpath%]
	set msg=
	if "%substpath%" NEQ "!spath!" set msg=%PAD%The network path does not match subst drive list. drive=!substdrive! path=[!spath!].
	if "%substpath%" EQU "!spath!" set pathfound=true
	if "%substpath%" NEQ "!spath!" set pathfound=false

:EVALUATE_NETWORK_DRIVES_LOOP_CONTINUE
if !debug!==1 echo.[DEBUG] %0:             pathfound=[%pathfound%]
set %1=%pathfound%
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
:RTRIM
:: -------------------------------------------------------------
::# Trim right
::# Description: call:RTRIM instring outstring  
::#      -- instring  [in]  - variable name containing the string to be trimmed on the right
::#      -- outstring [out] - variable name containing the result string
SET str=!%1!
rem echo."%str%"
for /l %%a in (1,1,31) do if "!str:~-1!"==" " set str=!str:~0,-1!
rem echo."%str%"
SET %2=%str%
GOTO:EOF


:: -------------------------------------------------------------
:LTRIM
:: -------------------------------------------------------------
::# Trim left
::# Description: call:LTRIM instring outstring  
::#      -- instring  [in]  - variable name containing the string to be trimmed on the left
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
