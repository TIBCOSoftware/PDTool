REM ######################################################################
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
REM ######################################################################

Master Folder:

-----------
Testing
-----------
1. For testing, it is required to perform a build within Eclipse and copy the resulting release zip file to a separate path to configure and test.
2. For simplicity of maintenance, the /bin directory batch files have been removed.

-----------
Build
-----------
/bin/PDTool - this is the master folder.  All edits should be done in this folder.

Edits should be performed manually on the ExecutePDTool.template.bat and ExecutePDTool.template.sh because of the version variable that are replaced at build time.
REM # CIS version [6.2, 7.0.0]
set DEFAULT_CIS_VERSION=@version@
    
The build automatically creates PDTool.zip distribution file which will contain the correct
    /bin folder with ExecutePDTool.bat, ExecutePDTool.sh and configureScripts.sh