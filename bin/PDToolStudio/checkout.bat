@echo off
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
REM # 0=debug off, 1=debug on
set debug=0
REM # 0=do not print variable output, 1=do print variable output
set PRINT_VARS=1
REM # [OPTIONAL] Default log location relative path from bin directory ../logs/app.log
rem #            If set then bath file messages will be written to the log
set DEFAULT_LOG_PATH=../logs/app.log
REM #
REM #----------------------------------------------------------
REM #*********** DO NOT MODIFY BELOW THIS LINE ****************
REM #----------------------------------------------------------
REM #
REM # CIS version [6.2, 7.0.0] - set CIS_VERSION
call ..\bin_novars\cisVersion.bat
REM #
REM #---------------------------------------------
REM # Get the full path to setVars.bat
REM #---------------------------------------------
for /f "tokens=* delims= " %%I in ("setVars.bat") do set DEFAULT_SET_VARS_PATH=%%~fI
REM #
REM #---------------------------------------------
REM # Invoke the actual script that does the work
REM #---------------------------------------------
call ..\bin_novars\checkout_novars.bat "%CD%" "%DEFAULT_SET_VARS_PATH%" %1 %2 %3 %4 %5
