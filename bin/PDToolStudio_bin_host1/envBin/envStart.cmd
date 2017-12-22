@echo off
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
REM #
REM # Start New PDTool/PDToolStudio Command Shell Window
REM #
REM # envStart.cmd XX env.cmd
REM #  param1=XX
REM #    where XX specifies TWO hex digits for the color of the command line window.
REM #    The default setting for this PDTool Environment Command window is: 17 
REM #    This corresponds to Blue background and White foreground.
REM #    The first digit corresponds to the background; the second the foreground. 
REM #    Each digit can be any of the following values:
REM #      0 = Black       8 = Gray
REM #      1 = Blue        9 = Light Blue
REM #      2 = Green       A = Light Green
REM #      3 = Aqua        B = Light Aqua
REM #      4 = Red         C = Light Red
REM #      5 = Purple      D = Light Purple
REM #      6 = Yellow      E = Light Yellow
REM #      7 = White       F = Bright White
REM #  param2=env.cmd
REM #    The standard environment variable command file unless a different command file is provided.
REM #    Additionally, the env.cmd file automatically invokes envCustom.cmd if it exists which allows the 
REM #    customer to set their own variables.
REM ############################################################################################################################
set COLOR=%1
set ENV=%2
set COLOR_DEFAULT=17
set ENV_DEFAULT=env.cmd
REM # Remove double quotes around arguments
setlocal EnableDelayedExpansion
	if defined COLOR set LCOLOR=!COLOR:"=!
	if defined ENV   set LENV=!ENV:"=!
endlocal & SET COLOR=%LCOLOR%& SET ENV=%LENV%
REM # Validate COLOR
if not defined COLOR (
   set COLOR=%COLOR_DEFAULT%
   goto VALIDATE
)
if "%COLOR%"==" " set COLOR=%COLOR_DEFAULT%
REM # Validate ENV
:VALIDATE
if defined ENV goto VALIDATE_EXIST
set ENV=%ENV_DEFAULT%
:VALIDATE_EXIST
if exist %ENV% goto MAIN
   echo.####################################################################
   echo.# ERROR: Command or batch file does not exist.  file=[%ENV%]
   echo.####################################################################
   goto END
:MAIN

REM # Open the new command shell with specified color.
if defined ENV CMD /T:%COLOR% /K "%ENV%"
:END
REM # Return to the original color
COLOR
