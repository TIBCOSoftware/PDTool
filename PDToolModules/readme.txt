######################################################################
# (c) 2014 Cisco and/or its affiliates. All rights reserved.
# 
# This software is released under the Eclipse Public License. The details can be found in the file LICENSE. 
# Any dependent libraries supplied by third parties are provided under their own open source licenses as 
# described in their own LICENSE files, generally named .LICENSE.txt. The libraries supplied by Cisco as 
# part of the Composite Information Server/Cisco Data Virtualization Server, particularly csadmin-XXXX.jar, 
# csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar, csext-XXXX.jar, csjdbc-XXXX.jar, 
# csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar, and customproc-XXXX.jar (where -XXXX is an 
# optional version number) are provided as a convenience, but are covered under the licensing for the 
# Composite Information Server/Cisco Data Virtualization Server. They cannot be used in any way except 
# through a valid license for that product.
# 
# This software is released AS-IS!. Support for this software is not covered by standard maintenance agreements with Cisco. 
# Any support for this software by Cisco would be covered by paid consulting agreements, and would be billable work.
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

Distribute documentation to the PDTool_6_2:
----------------------------------------------------------------------------
Run the "build.xml" in the PDToolModules directory
Right click on build.xml and select "Run as --> Ant Build"

The following operations will be performed:

1) Generate the JAXB libraries 
2) Distribute /dist/PDToolModules.jar to PDTool_6_2/lib 
3) The docs will be pulled in when PDTool_6_2 is built
 