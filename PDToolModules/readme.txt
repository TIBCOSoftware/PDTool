######################################################################
# (c) 2017 TIBCO Software Inc. All rights reserved.
# 
# Except as specified below, this software is licensed pursuant to the Eclipse Public License v. 1.0.
# The details can be found in the file LICENSE.
# 
# The following proprietary files are included as a convenience, and may not be used except pursuant
# to valid license to Composite Information Server or TIBCO(R) Data Virtualization Server:
# csadmin-XXXX.jar, csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar,
# csext-XXXX.jar, csjdbc-XXXX.jar, csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar,
# and customproc-XXXX.jar (where -XXXX is an optional version number).  Any included third party files
# are licensed under the terms contained in their own accompanying LICENSE files, generally named .LICENSE.txt.
# 
# This software is licensed AS-IS. Support for this software is not covered by standard maintenance agreements with TIBCO.
# If you would like to obtain assistance with this software, such assistance may be obtained through a separate paid consulting
# agreement with TIBCO.
#
######################################################################

To Developers:

Generate schema documentation, use Stylus Studio.
------------------------------------------------------
1) Open the schema in Stylus studio
2) Click on the Documentation tab in the center window
3) Select the "Save Documentation" icon in the upper left corner of the window
4) Browse to PDToolModules/docs
5) Select PDToolModules.xsd.html
6) Click Save
7) Click Yes to overwrite

Distribute documentation to the PDTool:
----------------------------------------------------------------------------
Run the "build.xml" in the PDToolModules directory
Right click on build.xml and select "Run as --> Ant Build"

The following operations will be performed:

1) Generate the JAXB libraries 
2) Distribute /dist/PDToolModules.jar to PDTool/lib 
3) The docs will be pulled in when PDTool is built
 