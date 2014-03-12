REM ######################################################################
REM # (c) 2014 Cisco and/or its affiliates. All rights reserved.
REM ######################################################################

Master Folder:

/bin/PDTool - this is the master folder.  All edits should be done in this folder.

When a build is performed on CisDeployTool project, it will automatically copy ExecutePDTool.bat
    to the /bin directory for testing purposes.   If edits are made in /bin, they must be
    copied to /bin/PDTool or the edits will be lost.
    
The build automatically creates PDTool.zip distribution file which will contain the correct
    /bin folder with ExecutePDTool.bat, ExecutePDTool.sh and configureScripts.sh