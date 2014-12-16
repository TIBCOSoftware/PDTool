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
REM #                                    -includeResourceSecurity [true or false] [-vcsWorkspacePathOverride "vcs-workspace-project-root-path"]  [-vcsScriptBinFolderOverride bin_folder]
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
REM #
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
REM #---------------------------------------------
REM # Get the full path to setVars.bat
REM #---------------------------------------------
for /f "tokens=* delims= " %%I in ("setVars.bat") do set DEFAULT_SET_VARS_PATH=%%~fI
REM #
REM #---------------------------------------------
REM # Invoke the actual script that does the work
REM #---------------------------------------------
call ..\bin_novars\ExecutePDToolStudio_novars.bat "%CD%" "%DEFAULT_SET_VARS_PATH%" %*
