PDTool
====================
(c) 2017 TIBCO Software Inc. All rights reserved.

Except as specified below, this software is licensed pursuant to the Eclipse Public License v. 1.0.
The details can be found in the file LICENSE.

The following proprietary files are included as a convenience, and may not be used except pursuant
to valid license to Composite Information Server or TIBCO® Data Virtualization Server:
csadmin-XXXX.jar, csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar,
csext-XXXX.jar, csjdbc-XXXX.jar, csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar,
and customproc-XXXX.jar (where -XXXX is an optional version number).  Any included third party files
are licensed under the terms contained in their own accompanying LICENSE files, generally named .LICENSE.txt.

This software is licensed AS-IS. Support for this software is not covered by standard maintenance agreements with TIBCO.
If you would like to obtain assistance with this software, such assistance may be obtained through a separate paid consulting
agreement with TIBCO.

PURPOSE
=======
The Promotion and Deployment Tool (PDTool) supports Data Virtualization (DV) and consists of two major components:
1.	PDTool – PDTool provides an out-of-the-box, automated, configurable, promotion and deployment tool-kit to allow customers 
	to promote DV resources to target DV servers such as test and production.  This capability seeks to satisfy 90% of customer’s 
	requirements for promoting DV resources from one environment to another without the customer having to write any custom scripts.
2.	PDTool Testing – PDTool Regression Module provide the ability to perform testing against on a target DV server.  
	Testing can be broken down into these primary areas:
	o	Functional Testing – test whether a published virtual view, procedure or web service is functional.  
		This is a basic smoke test.
	o	Migration Testing – test and compare the results from one release of DV to another release of DV.  
		Insure there are no differences in results.
	o	Regression Testing – test and compare the results from one release of code to the next.  
		Insure there are no differences in results.
	o	Performance Testing – test the performance of a set of queries or web services.  
		Compare the overall response times from one set of tests with another to determine if performance increased, 
		decreased, or was within an acceptable range.
	o	Security Testing – test the accessibility by different users/groups across a range of groups and queries.  
		Determine if a group is not set correctly or if there is a security hole.
