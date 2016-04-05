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
REM # Start New PDTool/PDToolStudio Environment Command Window
REM #
REM # startEnvCMD.bat XX
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
REM ############################################################################################################################
set COLOR=%1
if not defined COLOR set COLOR=17
REM # Set the existing variables in order to get PDTOOL_HOME and various VCS_HOME settings.
pushd %CD%
call setVars.bat
popd
cls
REM # Make sure PDTOO_HOME is set properly
if not defined PDTOOL_HOME (
   pushd %CD%
   cd ..
   set PDTOOL_HOME=%CD%
   popd
)
REM # Set VCS_HOME with any VCS home that is currently set
set VCS_HOME=
if defined TFS_HOME set VCS_HOME=%VCS_HOME%;%TFS_HOME%
if defined SVN_HOME set VCS_HOME=%VCS_HOME%;%SVN_HOME%
if defined GIT_HOME set VCS_HOME=%VCS_HOME%;%GIT_HOME%
if defined P4_HOME set VCS_HOME=%VCS_HOME%;%P4_HOME%
if defined CVS_HOME set VCS_HOME=%VCS_HOME%;%CVS_HOME%
REM # Open the new command shell with specified color.
echo PDTOOL_HOME=%PDTOOL_HOME%
echo VCS_HOME=%VCS_HOME%
CMD /T:%COLOR% /K "SET PATH=%PDTOOL_HOME%\bin;%VCS_HOME%;%PATH%"
REM # Return to the original color
COLOR
set PDTOOL_HOME=
set VCS_HOME=
