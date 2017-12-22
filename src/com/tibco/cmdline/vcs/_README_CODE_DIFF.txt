/**
 * (c) 2017 TIBCO Software Inc. All rights reserved.
 * 
 * Except as specified below, this software is licensed pursuant to the Eclipse Public License v. 1.0.
 * The details can be found in the file LICENSE.
 * 
 * The following proprietary files are included as a convenience, and may not be used except pursuant
 * to valid license to Composite Information Server or TIBCO® Data Virtualization Server: 
 * csadmin-XXXX.jar, csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar, 
 * csext-XXXX.jar, csjdbc-XXXX.jar, csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar, 
 * and customproc-XXXX.jar (where -XXXX is an optional version number).  Any included third party files 
 * are licensed under the terms contained in their own accompanying LICENSE files, generally named .LICENSE.txt.
 * 
 * This software is licensed AS-IS. Support for this software is not covered by standard maintenance agreements with TIBCO.
 * If you would like to obtain assistance with this software, such assistance may be obtained through a separate paid consulting
 * agreement with TIBCO.  
 * 
 */
The following provides documentation for the "vcs" files that shows where code is different for PDTool vs. engineering main-line code:

key
----
I=Identical to engineering
D=Difference from engineering
V=Version difference between PDTool 6.1 and PDTool 6.2
N=New to PDTool and not in the engineering code base

	com.tibco.cmdline.vcs
DV	DiffMerger.java				[PDTOOL CODE DIFF]
D	DiffMergerOptions.java
I	RollbackCARBuilder.java

	com.tibco.cmdline.vcs.client
DV	ExportCommand.java			[PDTOOL CODE DIFF]
D	ExportOptions.java

	com.tibco.cmdline.vcs.primitives
I	AbstractOptions.java

	com.tibco.cmdline.vcs.spi
D	AbstractLifecycleListener.java
I	LifecycleListener.java
	
	com.tibco.cmdline.vcs.spi.cvs
N	CVSLifecycleListener.java
	
	com.tibco.cmdline.vcs.spi.p4
D	P4LifecycleListener.java

	com.tibco.cmdline.vcs.spi.svn
D	SVNLifecycleListener.java	
	
	com.tibco.cmdline.vcs.spi.tfs
N	TFSLifecycleListener.java
	
	com.tibco.cmdline.vcs.spi.git
N	GITLifecycleListener.java
	
	com.tibco.cmdline.vcs.spi.clc
N	CLCLifecycleListener.java
	
	com.tibco.cmdline.vcs.util
I	NameCodecCommand.java

	