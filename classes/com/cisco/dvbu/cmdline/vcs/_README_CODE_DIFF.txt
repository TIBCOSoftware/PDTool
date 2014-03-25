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
	
	com.cisco.dvbu.cmdline.vcs.util
I	NameCodecCommand.java

	