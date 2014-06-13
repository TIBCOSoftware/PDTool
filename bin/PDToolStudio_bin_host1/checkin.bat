@echo off
REM ######################################################################
REM # (c) 2014 Cisco and/or its affiliates. All rights reserved.
REM ######################################################################
REM #=======================================================================================
REM # Example Execution Statement:
REM # checkin.bat [CIS-resource-path] [CIS-resource-type] [message] [vcs-workspace-project-folder] [vcs-temp-folder]
REM # 
REM # Parameters
REM # ----------
REM # %1 ->  Resource path 			(e.g. /shared/MyFolder/My__View), using file system (encoded) names
REM # %2 ->  Resource type 			(e.g. FOLDER, table, procedure etc.)
REM # %3 ->  Checkin message 		(e.g. Adding MyFolder)
REM # %4 ->  VCS Workspace Folder 	(e.g. C:\Temp\workspaces\workspace_CIS)
REM # %5 ->  VCS Temp Folder 		(e.g. C:\Temp\workspaces\temp_CIS)
REM #=======================================================================================
REM # Print out the Banner
echo. 
echo.---------------------------------------------------------
echo.---                                                   ---
echo.--- Composite PS Promotion and Deployment Tool Studio ---
echo.---                                                   ---
echo.--- Hostname Abstractor for path: %CD%
echo.---                                                   ---
echo.---------------------------------------------------------
echo. 

REM #---------------------------------------------
REM # Set environment variables for this host
REM #---------------------------------------------
call setVars.bat

REM #---------------------------------------------
REM # Invoke the actual script that does the work
REM #---------------------------------------------
call ..\bin_novars\checkin_novars.bat %1 %2 %3 %4 %5
