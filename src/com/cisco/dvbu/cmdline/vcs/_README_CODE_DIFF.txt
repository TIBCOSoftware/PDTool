/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 * 
 * This software is released under the Eclipse Public License. The details can be found in the file LICENSE. 
 * Any dependent libraries supplied by third parties are provided under their own open source licenses as 
 * described in their own LICENSE files, generally named .LICENSE.txt. The libraries supplied by Cisco as 
 * part of the Composite Information Server/Cisco Data Virtualization Server, particularly csadmin-XXXX.jar, 
 * csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar, csext-XXXX.jar, csjdbc-XXXX.jar, 
 * csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar, and customproc-XXXX.jar (where -XXXX is an 
 * optional version number) are provided as a convenience, but are covered under the licensing for the 
 * Composite Information Server/Cisco Data Virtualization Server. They cannot be used in any way except 
 * through a valid license for that product.
 * 
 * This software is released AS-IS!. Support for this software is not covered by standard maintenance agreements with Cisco. 
 * Any support for this software by Cisco would be covered by paid consulting agreements, and would be billable work.
 * 
 */
The following provides documentation for the "vcs" files that shows where code is different for PDTool vs. engineering main-line code:

key
----
I=Identical to engineering
D=Difference from engineering
V=Version difference between PDTool 6.1 and PDTool 6.2
N=New to PDTool and not in the engineering code base

	com.cisco.dvbu.cmdline.vcs
DV	DiffMerger.java				[PDTOOL CODE DIFF]
D	DiffMergerOptions.java
I	RollbackCARBuilder.java

	com.cisco.dvbu.cmdline.vcs.client
DV	ExportCommand.java			[PDTOOL CODE DIFF]
D	ExportOptions.java

	com.cisco.dvbu.cmdline.vcs.primitives
I	AbstractOptions.java

	com.cisco.dvbu.cmdline.vcs.spi
D	AbstractLifecycleListener.java
I	LifecycleListener.java
	
	com.cisco.dvbu.cmdline.vcs.spi.cvs
N	CVSLifecycleListener.java
	
	com.cisco.dvbu.cmdline.vcs.spi.p4
D	P4LifecycleListener.java

	com.cisco.dvbu.cmdline.vcs.spi.svn
D	SVNLifecycleListener.java	
	
	com.cisco.dvbu.cmdline.vcs.spi.tfs
N	TFSLifecycleListener.java
	
	com.cisco.dvbu.cmdline.vcs.spi.git
N	GITLifecycleListener.java
	
	com.cisco.dvbu.cmdline.vcs.spi.clc
N	CLCLifecycleListener.java
	
	com.cisco.dvbu.cmdline.vcs.util
I	NameCodecCommand.java

	