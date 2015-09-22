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
REM #               arg4:: [-vcsCheckinOptions VCS_CHECKIN_OPTIONS] is used to inject any optional or required VCS_CHECKIN_OPTIONS onto the vcs check-in command line.
REM #               arg5:: [-vcsuser vcs-username] optional parameter
REM #               arg6:: [-vcspassword vcs-password] optional parameter
REM #
REM # Option 3 - Execute VCS Workspace property file encryption:
REM #            ExecutePDToolStudio.bat [-nopause] -encrypt config-property-file-path
REM #               arg1:: [-nopause] is an optional parameter used to execute the batch file without pausing at the end of the script.
REM #	            arg2:: -encrypt is used to encrypt the passwords in studio.properties or a Module XML property file
REM #	            arg3:: file path to studio.properties or XML property file (full or relative path)
REM #
REM # Option 4 - Create Composite Studio Enable VCS:
REM #
REM #            ExecutePDToolStudio.bat [-nopause] -enablevcs -winlogin windows_user_login -user composite_login_user -domain composite_domain -host composite_host_server
REM #                                    -includeResourceSecurity [true or false] [-vcsWorkspacePathOverride "vcs-workspace-project-root-path"] [-vcsScriptBinFolderOverride bin_folder]
REM # 
REM #            Example: ExecutePDToolStudio.bat -nopause -enablevcs -winlogin user001 -user admin -domain composite -host localhost 
REM #                                             -includeResourceSecurity true -vcsWorkspacePathOverride "C:/PDToolStudio62/svn_sworkspace/cis_objects" 
REM #                                             -vcsScriptBinFolderOverride bin_svn
REM # 
REM #               arg1:: [-nopause] is an optional parameter used to execute the batch file without pausing at the end of the script.
REM #               arg2:: -winlogin is the windows user login name where the script will be modified.
REM #               arg3:: -user is the user name that you login to Composite Studio with.
REM #               arg4:: -domain is the domain designated in the Composite Studio login.
REM #               arg5:: -host is the fully qualified host name designated in the Composite Studio login.
REM #               arg6:: -includeResourceSecurity is true or false and designates whether to turn on resource seucrity at checkin.
REM #               arg7:: [-vcsWorkspacePathOverride] [optional] is a way of overriding the vcs workspace path instead of allowing this method to construct from the
REM #               	        studio.properites file properties: VCS_WORKSPACE_DIR+"/"+VCS_PROJECT_ROOT.  It will use the substitute drive by default.
REM #               	        This parameter is optional and therefore can be left out if you want to use the default settings in studio.properties.
REM #               	        If a path is provided, use double quotes to surround the path.
REM #               arg8:: [-vcsScriptBinFolderOverride] [optional] - this is the bin folder name only for PDTool Studio. Since PDTool Studio can now support multiple hosts via multiple /bin folders, 
REM #                          it is optional to pass in the /bin folder location.  e.g. bin_host1, bin_host2.  The default will be bin if the input is null or blank.
REM #               Supported OS:  7, 8, 2008 R2
REM #               Windows XP is not supported for this command
REM #
REM # Editor: Set tab=4 in your text editor for this file to format properly
REM #=======================================================================================
REM #----------------------------------------------------------
REM #*********** DO NOT MODIFY BELOW THIS LINE ****************
REM #----------------------------------------------------------
REM #=======================================
REM # Set up the execution context for invoking common scripts
REM #=======================================
if not defined debug set debug=0
REM # CIS version [6.2, 7.0.0]
set CIS_VERSION=@version@
REM # Initialize variables
set SCRIPT=ExecutePDToolStudio
set SEP=::
REM # set the print function
set writeOutput=:writeOutput
REM # Initialize variables
set SEP=::
SET PDTOOL_CMD=
SET DEFAULT_SET_VARS_PATH=
SET PDTOOL_PROPERTY_FILE=
SET PDTOOL_VCS_USERNAME=
SET PDTOOL_VCS_PASSWORD=
SET PDTOOL_PAUSECMD=
SET PDTOOL_WINDOWS_LOGIN=
SET PDTOOL_USER=
SET PDTOOL_DOMAIN=
SET PDTOOL_HOST=
SET PDTOOL_INCLUDE_RESOURCE_SECURITY=
SET PDTOOL_VCS_WORKSPACE_PATH_OVERRIDE=
SET PDTOOL_CUSTOM_CIS_PATH_LIST=
SET PDTOOL_VCS_CHECKIN_OPTIONS=
SET PDTOOL_VCS_SCRIPT_BIN_FOLDER_OVERRIDE=
SET PR_VCS_PASSWORD=

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

REM # Print out the Banner
call %writeOutput% " " 
call %writeOutput% "----------------------------------------------------------" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "---                                                    ---" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "--- Cisco Advanced Services                            ---" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "--- PDToolStudio: Promotion and Deployment Tool Studio ---" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "---                                                    ---" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "----------------------------------------------------------" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "--- Execution path: %CURRENT_EXEC_PATH%" 																"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "----------------------------------------------------------" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "***** BEGIN COMMAND: %SCRIPT% *****" 																	"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% " " 

REM #=======================================
REM # Parse Input Parameters
REM #=======================================
echo ########################################################################################################################################
echo %SCRIPT%: Parse command line and set input variables:
echo ########################################################################################################################################

SET PDTOOL_PAUSECMD=true

REM # Get the list of parameters to be used for the USAGE function
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

REM # Set the default to perform a vcsinit if no parameters are specified
if "%PDTOOL_CMD%" == "" set PDTOOL_CMD=-vcsinit

REM # Validate the parameters
if "%PDTOOL_CMD%" == "-vcsinit" goto VALID_PARAMS
if "%PDTOOL_CMD%" == "-encrypt" goto VALID_PARAMS
if "%PDTOOL_CMD%" == "-vcsinitBaseFolders" goto VALID_PARAMS
if "%PDTOOL_CMD%" == "-enablevcs" goto VALID_PARAMS
set arg=1
set ERRORMSG=Execution Failed::No parameters provided.
GOTO USAGE
:VALID_PARAMS

REM # Print the parameters
CALL:printablePassword "%PDTOOL_VCS_PASSWORD%" PR_VCS_PASSWORD
call %writeOutput% "Command Line Arguments:"																				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   CMD=[%PDTOOL_CMD%]"																					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   PROPERTY_FILE=[%PDTOOL_PROPERTY_FILE%]" 																"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   PAUSECMD=[%PDTOOL_PAUSECMD%]" 																		"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   VCS_USERNAME=[%PDTOOL_VCS_USERNAME%]" 																"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   VCS_PASSWORD=[%PR_VCS_PASSWORD%]" 																	"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   WINDOWS_LOGIN=[%PDTOOL_WINDOWS_LOGIN%]" 																"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   USER=[%PDTOOL_USER%]" 																				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   DOMAIN=[%PDTOOL_DOMAIN%]" 																			"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   HOST=[%PDTOOL_HOST%]" 																				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   INCLUDE_RESOURCE_SECURITY=[%PDTOOL_INCLUDE_RESOURCE_SECURITY%]" 										"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   VCS_WORKSPACE_PATH_OVERRIDE=[%PDTOOL_VCS_WORKSPACE_PATH_OVERRIDE%]" 									"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   CUSTOM_CIS_PATH_LIST=[%PDTOOL_CUSTOM_CIS_PATH_LIST%]" 												"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   VCS_CHECKIN_OPTIONS=[%PDTOOL_VCS_CHECKIN_OPTIONS%]" 													"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   VCS_SCRIPT_BIN_FOLDER_OVERRIDE=[%PDTOOL_VCS_SCRIPT_BIN_FOLDER_OVERRIDE%]" 							"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% " "

REM # Assign parameters
if defined PDTOOL_PROPERTY_FILE						set PROPERTY_FILE=%PDTOOL_PROPERTY_FILE%
if defined PDTOOL_PAUSECMD							set PAUSECMD=%PDTOOL_PAUSECMD%
if defined PDTOOL_VCS_USERNAME						set VCS_USERNAME=%PDTOOL_VCS_USERNAME%
if defined PDTOOL_VCS_PASSWORD						set VCS_PASSWORD=%PDTOOL_VCS_PASSWORD%
if defined PDTOOL_WINDOWS_LOGIN						set WINDOWS_LOGIN=%PDTOOL_WINDOWS_LOGIN%
if defined PDTOOL_USER								set USER=%PDTOOL_USER%
if defined PDTOOL_DOMAIN							set DOMAIN=%PDTOOL_DOMAIN%
if defined PDTOOL_HOST								set HOST=%PDTOOL_HOST%
if defined PDTOOL_INCLUDE_RESOURCE_SECURITY			set INCLUDE_RESOURCE_SECURITY=%PDTOOL_INCLUDE_RESOURCE_SECURITY%
if defined PDTOOL_VCS_WORKSPACE_PATH_OVERRIDE		set VCS_WORKSPACE_PATH_OVERRIDE=%PDTOOL_VCS_WORKSPACE_PATH_OVERRIDE%
if defined PDTOOL_CUSTOM_CIS_PATH_LIST				set CUSTOM_CIS_PATH_LIST=%PDTOOL_CUSTOM_CIS_PATH_LIST%
if defined PDTOOL_VCS_CHECKIN_OPTIONS				set VCS_CHECKIN_OPTIONS=%PDTOOL_VCS_CHECKIN_OPTIONS%
if defined PDTOOL_VCS_SCRIPT_BIN_FOLDER_OVERRIDE	set VCS_SCRIPT_BIN_FOLDER_OVERRIDE=%PDTOOL_VCS_SCRIPT_BIN_FOLDER_OVERRIDE%

REM #=======================================
REM # Invoke setVars.bat
REM #=====================================================================================
if exist %DEFAULT_SET_VARS_PATH% goto INVOKE_SET_VARS

  set ARG=DEFAULT_SET_VARS_PATH
  set ERRORMSG=Execution Failed::Path does not exist: %DEFAULT_SET_VARS_PATH%
  call:USAGE
  exit /B 2
			
:INVOKE_SET_VARS
REM #---------------------------------------------
REM # Set environment variables
REM #---------------------------------------------
call %writeOutput% "----------------------------------------------------------" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "--- Invoking setVars.bat...%DEFAULT_SET_VARS_PATH%"														"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "----------------------------------------------------------" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %DEFAULT_SET_VARS_PATH% 

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
REM #   etc.
REM #=====================================================================================
call:resolveVariables "%PDTOOL_PROPERTY_FILE%" 		  			"%PROPERTY_FILE%" 					PROPERTY_FILE
call:resolveVariables "%PDTOOL_VCS_USERNAME%" 		  			"%VCS_USERNAME%" 					VCS_USERNAME
call:resolveVariables "%PDTOOL_VCS_PASSWORD%" 		  			"%VCS_PASSWORD%" 					VCS_PASSWORD
call:resolveVariables "%PDTOOL_PAUSECMD%" 						"%PAUSECMD%" 						PAUSECMD
call:resolveVariables "%PDTOOL_WINDOWS_LOGIN%" 	 				"%WINDOWS_LOGIN%" 					WINDOWS_LOGIN
call:resolveVariables "%PDTOOL_USER%" 	  						"%USER%" 							USER
call:resolveVariables "%PDTOOL_DOMAIN%" 	  					"%DOMAIN%" 							DOMAIN
call:resolveVariables "%PDTOOL_HOST%" 	  						"%HOST%" 							HOST
call:resolveVariables "%PDTOOL_INCLUDE_RESOURCE_SECURITY%" 	  	"%INCLUDE_RESOURCE_SECURITY%" 		INCLUDE_RESOURCE_SECURITY
call:resolveVariables "%PDTOOL_VCS_WORKSPACE_PATH_OVERRIDE%" 	"%VCS_WORKSPACE_PATH_OVERRIDE%" 	VCS_WORKSPACE_PATH_OVERRIDE
call:resolveVariables "%PDTOOL_CUSTOM_CIS_PATH_LIST%" 	  		"%CUSTOM_CIS_PATH_LIST%" 			CUSTOM_CIS_PATH_LIST
call:resolveVariables "%PDTOOL_VCS_CHECKIN_OPTIONS%" 	  		"%VCS_CHECKIN_OPTIONS%" 			VCS_CHECKIN_OPTIONS
call:resolveVariables "%PDTOOL_VCS_SCRIPT_BIN_FOLDER_OVERRIDE%" "%VCS_SCRIPT_BIN_FOLDER_OVERRIDE%" 	VCS_SCRIPT_BIN_FOLDER_OVERRIDE

REM # Print the parameters
CALL:printablePassword "%VCS_PASSWORD%" PR_VCS_PASSWORD
echo ########################################################################################################################################
echo %SCRIPT%: Command line resolved variables:
echo ########################################################################################################################################
call %writeOutput% "Resolved Arguments:"																					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   CMD=[%PDTOOL_CMD%]"																					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   PROPERTY_FILE=[%PROPERTY_FILE%]" 																	"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   PAUSECMD=[%PAUSECMD%]" 																				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   VCS_USERNAME=[%VCS_USERNAME%]" 																		"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   VCS_PASSWORD=[%PR_VCS_PASSWORD%]" 																	"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   WINDOWS_LOGIN=[%WINDOWS_LOGIN%]" 																	"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   USER=[%USER%]" 																						"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   DOMAIN=[%DOMAIN%]" 																					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   HOST=[%HOST%]" 																						"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   INCLUDE_RESOURCE_SECURITY=[%INCLUDE_RESOURCE_SECURITY%]" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   VCS_WORKSPACE_PATH_OVERRIDE=[%VCS_WORKSPACE_PATH_OVERRIDE%]" 										"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   CUSTOM_CIS_PATH_LIST=[%CUSTOM_CIS_PATH_LIST%]" 														"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   VCS_CHECKIN_OPTIONS=[%VCS_CHECKIN_OPTIONS%]" 														"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "   VCS_SCRIPT_BIN_FOLDER_OVERRIDE=[%VCS_SCRIPT_BIN_FOLDER_OVERRIDE%]" 									"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
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
   call %writeOutput% "Execution Failed::PROJECT_HOME does not exist: %PROJECT_HOME%" 										"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   ENDLOCAL
   exit /B 1
)
if NOT EXIST "%JAVA_HOME%" (
   call %writeOutput% "Execution Failed::JAVA_HOME does not exist: %JAVA_HOME%" 											"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
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
REM # Precedence evaluation
REM #=======================================
set PRECEDENCE=
if defined propertyOrderPrecedence set PRECEDENCE=-DpropertyOrderPrecedence="%propertyOrderPrecedence%"

REM #=======================================
REM # Parameter Validation
REM #=======================================
REM # Branch to the correct commmand area
if "%PDTOOL_CMD%" == "-vcsinit" goto SETUP_VCSINIT
if "%PDTOOL_CMD%" == "-vcsinitBaseFolders" goto SETUP_VCSINIT_BASE_FOLDERS
if "%PDTOOL_CMD%" == "-encrypt" goto SETUP_ENCRYPT
if "%PDTOOL_CMD%" == "-enablevcs" goto SETUP_ENABLE_VCS
set arg=1
set ERRORMSG=Execution Failed::Failed parameter validation.
GOTO USAGE

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
REM # Invoke: DeployManagerUtil vcsStudioInitWorkspace "%VCS_USERNAME%" "%VCS_PASSWORD%"
REM #***********************************************
set JAVA_ACTION=vcsStudioInitWorkspace
set   COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% %PRECEDENCE% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% "%VCS_USERNAME%" "%VCS_PASSWORD%"
set PRCOMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% %PRECEDENCE% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% "%VCS_USERNAME%" "%PR_VCS_PASSWORD%"
GOTO START_SCRIPT

:--------------------------
:SETUP_VCSINIT_BASE_FOLDERS
:--------------------------
call %writeOutput% " " 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% "----------- COMMAND-LINE VCS BASE FOLDER INITIALIZE --------------" 
call %writeOutput% "------------------------------------------------------------------" 
call %writeOutput% " " 

if not defined VCS_USERNAME set VCS_USERNAME= 
if not defined VCS_PASSWORD set VCS_PASSWORD= 
if not defined CUSTOM_CIS_PATH_LIST set CUSTOM_CIS_PATH_LIST= 
if not defined VCS_CHECKIN_OPTIONS set VCS_CHECKIN_OPTIONS= 
CALL:printablePassword "%VCS_PASSWORD%" PR_VCS_PASSWORD

REM #***********************************************
REM # Invoke: DeployManagerUtil vcsStudioInitializeBaseFolderCheckin "%CUSTOM_CIS_PATH_LIST%" "%VCS_USERNAME%" "%VCS_PASSWORD%"
REM #***********************************************
set JAVA_ACTION=vcsStudioInitializeBaseFolderCheckin
set   COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% %PRECEDENCE% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% "%CUSTOM_CIS_PATH_LIST%" "%VCS_CHECKIN_OPTIONS%" "%VCS_USERNAME%" "%VCS_PASSWORD%"
set PRCOMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% %PRECEDENCE% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_MANAGER% %JAVA_ACTION% "%CUSTOM_CIS_PATH_LIST%" "%VCS_CHECKIN_OPTIONS%" "%VCS_USERNAME%" "%PR_VCS_PASSWORD%"
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
   call %writeOutput% "Execution Failed::Property File does not exist: %PROPERTY_FILE%" 									"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   goto USAGE 
)
REM #***********************************************
REM # Invoke: DeployManagerUtil encryptPasswordsInFile "%PROPERTY_FILE%"
REM #***********************************************
set JAVA_ACTION=encryptPasswordsInFile
set COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% %PRECEDENCE% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_COMMON_UTIL% %JAVA_ACTION% "%PROPERTY_FILE%"
set PRCOMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% %PRECEDENCE% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_COMMON_UTIL% %JAVA_ACTION% "%PROPERTY_FILE%"
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
IF NOT DEFINED VCS_SCRIPT_BIN_FOLDER_OVERRIDE (
  set VCS_SCRIPT_BIN_FOLDER_OVERRIDE= 
)

REM # Set the property file full path.  e.g. C:\Users\<windows_login_user>\.compositesw\admin.composite.localhost.properties
REM #   Supports Windows 7, 8, 2008 R2
set PROPERTY_FILE_PATH=C:\Users\%WINDOWS_LOGIN%\.compositesw\%USER%.%DOMAIN%.%HOST%.properties
call %writeOutput% "PROPERTY_FILE_PATH=[%PROPERTY_FILE_PATH%]" 																"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"

REM #***********************************************
REM # Invoke: DeployManagerUtil createStudioEnableVCSPropertyFile
REM #***********************************************
set JAVA_ACTION=createStudioEnableVCSPropertyFile
set COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% %PRECEDENCE% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_COMMON_UTIL% %JAVA_ACTION% "%INCLUDE_RESOURCE_SECURITY%" "%VCS_WORKSPACE_PATH_OVERRIDE%" "%VCS_SCRIPT_BIN_FOLDER_OVERRIDE%" "%CONFIG_PROPERTY_FILE%" "%PROJECT_HOME_PHYSICAL%" "%PROPERTY_FILE_PATH%"
set PRCOMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  %DEPLOY_CLASSPATH% %CONFIG_ROOT% %CONFIG_LOG4J% %PRECEDENCE% -Djava.endorsed.dirs="%ENDORSED_DIR%" -DPROJECT_HOME="%PROJECT_HOME%" -DCONFIG_PROPERTY_FILE=%CONFIG_PROPERTY_FILE% %DEPLOY_COMMON_UTIL% %JAVA_ACTION% "%INCLUDE_RESOURCE_SECURITY%" "%VCS_WORKSPACE_PATH_OVERRIDE%" "%VCS_SCRIPT_BIN_FOLDER_OVERRIDE%" "%CONFIG_PROPERTY_FILE%" "%PROJECT_HOME_PHYSICAL%" "%PROPERTY_FILE_PATH%"
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
   call %writeOutput% "Script %SCRIPT% Failed. Abnormal Script Termination. Exit code is: %ERROR%." 						"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
   ENDLOCAL
   exit /B %ERROR%
)

REM #=======================================
REM # Successful script completion
REM #=======================================
call %writeOutput% " "
call %writeOutput% "-------------- SUCCESSFUL SCRIPT COMPLETION [%SCRIPT% %PDTOOL_CMD%] --------------" 					"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
call %writeOutput% "End of script." 																						"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
IF "%PAUSECMD%" == "true" pause
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
::# Example Execution Statement:
::# call :parse
::#
::# Parse parameters and validate input
::#----------------------------------------------------------
set SCRIPT_DEBUG=%0
set PARSE_ERROR=0
REM # Shift past the first 2 parameters: CURRENT_EXEC_PATH and DEFAULT_SET_VARS_PATH
shift
shift
:loop
	set ARG1=%1
	set ARG2=%2
	set TARG1=
	set TARG2=
	REM # Remove double quotes
	setlocal EnableDelayedExpansion
	if defined ARG1 set TARG1=!ARG1:"=!
	if defined ARG2 set TARG2=!ARG2:"=!
	endlocal & SET ARG1=%TARG1%& SET ARG2=%TARG2%

	REM # Display debug if on
	if %debug%==1 echo [DEBUG] %SCRIPT_DEBUG%: ARG1=[%ARG1%]       ARG2=[%ARG2%]

    if "%ARG1%" == "" GOTO:EOF
	
	REM # -nopause command
    if "%ARG1%" == "-nopause" GOTO NOPAUSE
	GOTO CONTINUE1
	:NOPAUSE
            SET PDTOOL_PAUSECMD=false
            GOTO:LOOPEND

	REM # -vcsinit command
    :CONTINUE1
    if "%ARG1%" == "-vcsinit" GOTO VCSINIT
	GOTO CONTINUE2
	:VCSINIT
            SET PDTOOL_CMD=%ARG1%
            GOTO:LOOPEND
	
	REM # -encrypt command
    :CONTINUE2
    if "%ARG1%" == "-encrypt" GOTO ENCRYPT
	GOTO CONTINUE3
	:ENCRYPT
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
	
	REM # Extract -vcsuser
    :CONTINUE3
    if "%ARG1%" == "-vcsuser" GOTO VCSUSER
 	GOTO CONTINUE4
	:VCSUSER
            SET PDTOOL_VCS_USERNAME=%ARG2%
			set PARSE_ERROR=1
			set ARG=VCS_USERNAME
			set ERRORMSG=Execution Failed::Missing parameter
			if "%ARG2%" NEQ "" set PARSE_ERROR=0
			if "%ARG2%" NEQ "" set ARG=
			if "%ARG2%" NEQ "" set ERRORMSG=
            shift
            GOTO:LOOPEND
	
	REM # Extract -vcspassword
	:CONTINUE4
     if "%ARG1%" == "-vcspassword" GOTO VCSPASSWORD
 	GOTO CONTINUE5
	:VCSPASSWORD
            SET PDTOOL_VCS_PASSWORD=%ARG2%
			set PARSE_ERROR=1
			set ARG=VCS_PASSWORD
			set ERRORMSG=Execution Failed::Missing parameter
			if "%ARG2%" NEQ "" set PARSE_ERROR=0
			if "%ARG2%" NEQ "" set ARG=
			if "%ARG2%" NEQ "" set ERRORMSG=
            shift
            GOTO:LOOPEND

	REM # -enablevcs command
    :CONTINUE5
    if "%ARG1%" == "-enablevcs" GOTO ENABLEVCS
	GOTO CONTINUE6
	:ENABLEVCS
            SET PDTOOL_CMD=%ARG1%
            GOTO:LOOPEND
	
	REM # Extract -winlogin
    :CONTINUE6
    if "%ARG1%" == "-winlogin" GOTO WINLOGIN
 	GOTO CONTINUE7
	:WINLOGIN
            SET PDTOOL_WINDOWS_LOGIN=%ARG2%
			set PARSE_ERROR=1
			set ARG=WINDOWS_LOGIN
			set ERRORMSG=Execution Failed::Missing parameter
			if "%ARG2%" NEQ "" set PARSE_ERROR=0
			if "%ARG2%" NEQ "" set ARG=
			if "%ARG2%" NEQ "" set ERRORMSG=
            shift
            GOTO:LOOPEND

	REM # Extract -user
    :CONTINUE7
    if "%ARG1%" == "-user" GOTO USER
 	GOTO CONTINUE8
	:USER
            SET PDTOOL_USER=%ARG2%
			set PARSE_ERROR=1
			set ARG=USER
			set ERRORMSG=Execution Failed::Missing parameter
			if "%ARG2%" NEQ "" set PARSE_ERROR=0
			if "%ARG2%" NEQ "" set ARG=
			if "%ARG2%" NEQ "" set ERRORMSG=
            shift
            GOTO:LOOPEND
	
	REM # Extract -domain
	:CONTINUE8
     if "%ARG1%" == "-domain" GOTO DOMAIN
 	GOTO CONTINUE9
	:DOMAIN
            SET PDTOOL_DOMAIN=%ARG2%
			set PARSE_ERROR=1
			set ARG=DOMAIN
			set ERRORMSG=Execution Failed::Missing parameter
			if "%ARG2%" NEQ "" set PARSE_ERROR=0
			if "%ARG2%" NEQ "" set ARG=
			if "%ARG2%" NEQ "" set ERRORMSG=
            shift
            GOTO:LOOPEND

	REM # Extract -host
	:CONTINUE9
     if "%ARG1%" == "-host" GOTO HOST
 	GOTO CONTINUE10
	:HOST
            SET PDTOOL_HOST=%ARG2%
			set PARSE_ERROR=1
			set ARG=HOST
			set ERRORMSG=Execution Failed::Missing parameter
			if "%ARG2%" NEQ "" set PARSE_ERROR=0
			if "%ARG2%" NEQ "" set ARG=
			if "%ARG2%" NEQ "" set ERRORMSG=
            shift
            GOTO:LOOPEND

	REM # Extract -includeResourceSecurity
	:CONTINUE10
     if "%ARG1%" == "-includeResourceSecurity" GOTO INCLUDE_RESOURCE_SECURITY
 	GOTO CONTINUE11
	:INCLUDE_RESOURCE_SECURITY
            SET PDTOOL_INCLUDE_RESOURCE_SECURITY=%ARG2%
			set PARSE_ERROR=1
			set ARG=INCLUDE_RESOURCE_SECURITY
			set ERRORMSG=Execution Failed::Missing parameter
			if "%ARG2%" NEQ "" set PARSE_ERROR=0
			if "%ARG2%" NEQ "" set ARG=
			if "%ARG2%" NEQ "" set ERRORMSG=
            shift
            GOTO:LOOPEND

	REM # Extract -vcsWorkspacePathOverride
	:CONTINUE11
     if "%ARG1%" == "-vcsWorkspacePathOverride" GOTO VCS_WORKSPACE_PATH_OVERRIDE
 	GOTO CONTINUE12
	:VCS_WORKSPACE_PATH_OVERRIDE
            SET PDTOOL_VCS_WORKSPACE_PATH_OVERRIDE=%ARG2%
			set PARSE_ERROR=1
			set ARG=VCS_WORKSPACE_PATH_OVERRIDE
			set ERRORMSG=Execution Failed::Missing parameter
			if "%ARG2%" NEQ "" set PARSE_ERROR=0
			if "%ARG2%" NEQ "" set ARG=
			if "%ARG2%" NEQ "" set ERRORMSG=
            shift
            GOTO:LOOPEND
			
	REM # -vcsinitBaseFolders command
    :CONTINUE12
    if "%ARG1%" == "-vcsinitBaseFolders" GOTO VCSINITBASEFOLDERS
	GOTO CONTINUE13
	:VCSINITBASEFOLDERS
            SET PDTOOL_CMD=%ARG1%
            GOTO:LOOPEND
			
	REM # Extract -customCisPathList
	:CONTINUE13
     if "%ARG1%" == "-customCisPathList" GOTO CUSTOMCISPATHLIST
 	GOTO CONTINUE14
	:CUSTOMCISPATHLIST
            SET PDTOOL_CUSTOM_CIS_PATH_LIST=%ARG2%
			set PARSE_ERROR=1
			set ARG=CUSTOM_CIS_PATH_LIST
			set ERRORMSG=Execution Failed::Missing parameter
			if "%ARG2%" NEQ "" set PARSE_ERROR=0
			if "%ARG2%" NEQ "" set ARG=
			if "%ARG2%" NEQ "" set ERRORMSG=
            shift
            GOTO:LOOPEND

	REM # Extract -vcsCheckinOptions
	:CONTINUE14
     if "%ARG1%" == "-vcsCheckinOptions" GOTO VCSCHECKINOPTIONS
 	GOTO CONTINUE15
	:VCSCHECKINOPTIONS
            SET PDTOOL_VCS_CHECKIN_OPTIONS=%ARG2%
			set PARSE_ERROR=1
			set ARG=VCS_CHECKIN_OPTIONS
			set ERRORMSG=Execution Failed::Missing parameter
			if "%ARG2%" NEQ "" set PARSE_ERROR=0
			if "%ARG2%" NEQ "" set ARG=
			if "%ARG2%" NEQ "" set ERRORMSG=
            shift
            GOTO:LOOPEND

	REM # Extract -vcsScriptBinFolderOverride
	:CONTINUE15
     if "%ARG1%" == "-vcsScriptBinFolderOverride" GOTO VCSSCRIPTBINFOLDEROVERRIDE
 	GOTO CONTINUE16
	:VCSSCRIPTBINFOLDEROVERRIDE
            SET PDTOOL_VCS_SCRIPT_BIN_FOLDER_OVERRIDE=%ARG2%
			set PARSE_ERROR=1
			set ARG=VCS_SCRIPT_BIN_FOLDER_OVERRIDE
			set ERRORMSG=Execution Failed::Missing parameter
			if "%ARG2%" NEQ "" set PARSE_ERROR=0
			if "%ARG2%" NEQ "" set ARG=
			if "%ARG2%" NEQ "" set ERRORMSG=
            shift
            GOTO:LOOPEND
			
	REM # Unknown paramter found
	:CONTINUE16
			set ARG=
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
	call %writeOutput% " USAGE: %SCRIPT%%ext% [-nopause] [-vcsinit|-encrypt|-enablevcs|-vcsinitBaseFolders] [various-arguments]"
	call %writeOutput% " "
	if defined ARG call %writeOutput% " Argument [%ARG%] is missing or invalid."
	call %writeOutput% " CMD: %SCRIPT%%ext% %PARAMS%"
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
	call %writeOutput% "            %SCRIPT%%ext% [-nopause] -vcsinitBaseFolders [-customCisPathList custom-path-list] [-vcsCheckinOptions VCS_CHECKIN_OPTIONS] [-vcsuser username] [-vcspassword password]"
	call %writeOutput% " "
	call %writeOutput% "            Example: %SCRIPT%%ext% -nopause -vcsinitBaseFolders -customCisPathList /shared/myfolder,/services/databases/mydb,/services/webservices/myfolder -vcsuser user -vcspassword password"
 	call %writeOutput% " "
	call %writeOutput% "               arg1:: [-nopause] is an optional parameter used to execute the batch file without pausing at the end of the script."
	call %writeOutput% "               arg2:: -vcsinitBaseFolders is used to initialize the vcs repository with the Composite repository base folders and custom folders."
	call %writeOutput% "               arg3:: [-customCisPathList custom-CIS-path-list] optional parameters.  Custom, comma separated list of CIS paths to add to the VCS repository."
	call %writeOutput% "               arg4:: [-vcsCheckinOptions VCS_CHECKIN_OPTIONS] is used to inject any optional or required VCS_CHECKIN_OPTIONS onto the vcs check-in command line."
	call %writeOutput% "               arg5:: [-vcsuser username] optional parameters"
	call %writeOutput% "               arg6:: [-vcspassword password] optional parameters"
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
	call %writeOutput% "               arg8:: [-vcsScriptBinFolderOverride] - this is the bin folder name only for PDTool Studio. Since PDTool Studio can now support multiple hosts via multiple /bin folders,"
	call %writeOutput% "                          it is required to pass in the /bin folder location.  e.g. bin_host1, bin_host2.  The default will be bin if the input is null or blank."
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
if %debug%==1 echo.[DEBUG] %0: substdrive=%substdrive%
if %debug%==1 echo.[DEBUG] %0:  substpath=%substpath%

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
   if defined spath call :REPLACE "\\%COMPUTER_NAME%\C$" "C:" "!spath!" spath 
   if !debug!==1 echo.[DEBUG] %0: spath=!spath!
 
   REM The drive was found
   if "!substdrive!" == "!sdrive!" (
     if !debug!==1 echo.[DEBUG] %0: !substdrive! drive found.  
     if !debug!==1 echo.[DEBUG] %0: subst cmd:      path=[!spath!]
     if !debug!==1 echo.[DEBUG] %0: Parameter: substpath=[%substpath%]
	 set msg=
     if "!substpath!" NEQ "!spath!" set msg=%PAD%The network path does not match subst drive list. drive=!substdrive! path=[!spath!].
	 rem if defined msg call %writeOutput% "!msg!" 																				"%SCRIPT%%SEP%%DATE%-%TIME%%SEP%"
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
