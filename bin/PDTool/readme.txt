############################################################################################################################
# (c) 2017 TIBCO Software Inc. All rights reserved.
# 
# Except as specified below, this software is licensed pursuant to the Eclipse Public License v. 1.0.
# The details can be found in the file LICENSE.
# 
# The following proprietary files are included as a convenience, and may not be used except pursuant
# to valid license to Composite Information Server or TIBCO® Data Virtualization Server:
# csadmin-XXXX.jar, csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar,
# csext-XXXX.jar, csjdbc-XXXX.jar, csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar,
# and customproc-XXXX.jar (where -XXXX is an optional version number).  Any included third party files
# are licensed under the terms contained in their own accompanying LICENSE files, generally named .LICENSE.txt.
# 
# This software is licensed AS-IS. Support for this software is not covered by standard maintenance agreements with TIBCO.
# If you would like to obtain assistance with this software, such assistance may be obtained through a separate paid consulting
# agreement with TIBCO.
#
############################################################################################################################

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
# CIS version [6.2, 7.0.0]
set DEFAULT_CIS_VERSION=@version@
    
The build automatically creates PDTool.zip distribution file which will contain the correct
    /bin folder with ExecutePDTool.bat, ExecutePDTool.sh and configureScripts.sh