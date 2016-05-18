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
