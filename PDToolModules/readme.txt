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

----------------------------------------------------------------------------
Generate schema documentation, use Stylus Studio.
------------------------------------------------------
1) Open the schema in Stylus studio
2) Click on the Documentation tab in the center window
3) Select the "Save Documentation" icon in the upper left corner of the window
4) Browse to PDToolModules/docs
5) Select PDToolModules.xsd.html
6) Click Save
7) Click Yes to overwrite


----------------------------------------------------------------------------
Build the PDToolModules.jar
----------------------------------------------------------------------------
Right click on "pom.xml" Run as "Maven install"
	a) This will perform the following:
		Generate the JAXB .java source in /src
		Compile the target in /target
		Build the PDToolModules.jar
		Copy target/PDToolModules.jar ../libcommon
		Copy /schema/PDToolModules.xsd ../resources/schema
		
One Time only - copy runtime jar files to /libcommon
	jakarta.activation-1.2.2.jar
	jaxb-api-2.3.1.jar
	jaxb-runtime-2.3.2.jar
	