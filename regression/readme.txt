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
---------------------------------------------------------------
PDTool Regression Testing Scripts for PDTool7.0.0 and PDTool8.0.0
---------------------------------------------------------------
@copyright    (c) 2017 TIBCO Software Inc. All rights reserved.
@author:      Mike Tinius
@date:        January 12, 2019

@description: The scripts in the directory are for executing regression tests against PDTool7.0.0 and PDTool8.0.0
              and all of the various environment combinations including windows and unix and the various 
              VCS software.  The regression script folder (/regression) may be placed anywhere on your
              computer as long as the regressioin_confi_driver.bat|.sh is executed from within the 
              regression directory.  This allows you to have one copy of the regression scripts and perform
              a regression against any PDTool directory since the PDTool home dir is passed into the script.
			  
			  Note: If 2 separate windows are opened on the command line within the regression folder, a test
			  may be run in parallel against multiple versions since the status files are now appended with 
			  the version number thus causing no conflict.  This is true as long as the target PDTool directory
			  is also a separate version/folder that is being tested.
        	  
@installation:
              1. Unzip regression-YYYY-MM-DD.zip to any directory on the machine where PDTool7.0.0 or PDTool8.0.0
                 has been installed.
              2. Insure that the regression/config/*.property files have the proper configuration parameters
                 set for each environment to be tested.
			  3. Verify the /modules/servers.xml to insure you have the correct list of servers
				 Pattern: [hostname][port][http|https] e.g. localhost9400http or localhost9400https
				 Provide an entry for both http and https.  When using https, the useHttps=true is set.
			  4. Verify the /config_lists/regression_[win|unix]_[7|8].0.0_config_[type].txt to make sure the correct set of lists are uncommented.
					config type:
						NOVCS_HTTP  = no vcs testing using http protocol
						NOVCS_HTTPS = no vcs testing using https protocol
						CUSTOM      = a custom set of deployment plans to exeucte
						GIT         = vcs GIT testing only
						SVN         = vcs Subversion testing only
						TFS         = vcs TFS testing only
						P4          = vcs Perforce testing only
						CVS         = vcs CVS testing only
			  5. Verify the target PDTool/security folder and CIS target instance to determine whether it requires weak or strong trust store.
				 Copy the cis_studio_truststore_strong.jks into the PDTool/security folder for https testing if the target CIS is using strong encryption pack.
				 Configure the PDTool/bin/setVars.bat or setVars.sh with the correct trust store.
              6. Proceed to "instructions" for execution.
			  7. NOTE: PDTool regression will use the variable settings from /PDTool[7|8].0.0_yyyymmdd/PDTool/bin/setVars.bat and /PDTool[7|8].0.0_yyyymmdd/setMyPrePDToolVars.bat
        	  
@instructions:
              1. This script must be executed from the PDTool regression directory
                 cd /regression
              2. For Windows, proceed to the section below on "How to execute on Windows:" 
              3. For UNIX, proceed to the section below on "How to execute on Unix (Linux):" 

---------------------------------
How to execute on Windows:
---------------------------------
CIS:    Regression Config Driver Script   Configuration List                         PDTool Home Dir             Debug Version
====    ===============================   ========================================== ====================        ===== =======
7.0.0:  regression_config_driver.bat      regression_win_7.0.0_config_NOVCS_HTTP.txt "D:\dev\PDTool7.0.0\PDTool" Y|N   7.0.0 <-- Represents a regression of PDTool for DV 7.0 on windows
8.0.0:  regression_config_driver.bat      regression_win_8.0.0_config_NOVCS_HTTP.txt "D:\dev\PDTool8.0.0\PDTool" Y|N   8.0.0 <-- Represents a regression of PDTool for DV 8.0 on windows

---------------------------------
How to execute on Unix (Linux):
---------------------------------
CIS:    Regression Config Driver Script   Configuration List                         PDTool Home Dir                   Debug Version
====    ===============================   ========================================== ====================              ===== =======
7.0.0:  ./regression_config_driver.sh     regression_unix_7.0.0_config.txt           "/u01/home/qa/PDTool7.0.0\PDTool" Y|N   7.0.0 <-- Represents a regression of PDTool for DV 7.0 on unix
8.0.0:  ./regression_config_driver.sh     regression_unix_8.0.0_config.txt           "/u01/home/qa/PDTool8.0.0\PDTool" Y|N   8.0.0 <-- Represents a regression of PDTool for DV 8.0 on unix

------------------------------------------------------------
Configuration List File:  regression_unix_7.0.0_config.txt
------------------------------------------------------------
The configuration list file contains the PDTool configuration property name and
the regression plan file list. The configuration property file instructs PDTool
how to connect to CIS and provides VCS connection information also.  The regression
plan file list is a list of PDTool plans to execute.  The list provided below
will result in an execution of CIS 7.0 with the "baseline" plan files, followed by
specific VCS types including subversion (svn), CVS (cvs) and Perforce (p4).  If this
were on windows, it would also execute Team Foundation Server (tfs).  Lines are 
commented out by using a # in the first character.  To run a short test to verify that
the configuration is working properly, uncomment the "test" plan file lists.   These
will execute a couple of commands to verify connectivity.    Once satisfied that the
connection is working, then comment out the "test" plan files and uncomment the base
plan files.

# PDTool Config File        PDTool Plan File List
=====================       ===============================
#regression_unix_7.0.0      regression_plan_test_list.txt         <-- execute a configuration test for baseline plans
regression_unix_7.0.0       regression_plan_base_list.txt         <-- execute baseline plans 

#regression_unix_7.0.0_svn  regression_plan_vcs_test_list.txt     <-- execute a VCS test for subversion
regression_unix_7.0.0_svn   regression_plan_vcs_base_list.txt     <-- execute the VCS baseline plans for subversion

#regression_unix_7.0.0_cvs  regression_plan_vcs_test_list.txt     <-- execute a VCS test for CVS
regression_unix_7.0.0_cvs   regression_plan_vcs_base_list.txt     <-- execute the VCS baseline plans for CVS

#regression_unix_7.0.0_p4   regression_plan_vcs_test_list.txt     <-- execute a VCS test for perforce
regression_unix_7.0.0_p4    regression_plan_vcs_base_list.txt     <-- execute the VCS baseline plans for perforce
#regression_unix_7.0.0_p4   regression_plan_vcs_perforce_list.txt <-- execute the perforce labels test

-----------------------------------------------------------
Regression Plan File List: regression_plan_base_list.txt
-----------------------------------------------------------
The plan file list is a list of PDTool plans to execute.
There is one operation per plan file (.dp) so that execution status can be logged on individual operations
# Provides the order of execution for the plan files
_initialize.dp
ArchiveModule-01.dp
ArchiveModule-02.dp
ArchiveModule-03.dp
ArchiveModule-04.dp
GroupModule-01.dp
GroupModule-02.dp
GroupModule-03.dp
...
ServerManagerModule-01.dp
ServerManagerModule-02.dp
ServerManagerModule-03.dp

-----------------------------------------------------------
Logs
-----------------------------------------------------------
Logs are generated by each execution configuration.  A directory is created using the base file name of the configuration list file.
For example if the command line is: ./regression_config_driver.sh regression_unix_7.0.0_config.txt
then the regression log directory containing all the log file is: regression_unix_7.0.0_config

/regression/logs
    /regression_unix_7.0.0_config/
        20130212_165124_regression_unix_7.0.0_config.log

That directory contains a dated log file for each execution.  The format is YYYYMMDD_HH24MMSS_base-config-name.log.
The contents of the log provide a PASS/FAIL for each execution line.  Additionally after each "regression plan file" is executed
a PASS/FAIL summary is provided.  Finally, at the end an overall PASS/FAIL status is provided for the entire execution of the configuration
file.

Additionally, the same directory contains the individual (app.log) results for the deployment plan execution.  Each deployment plan
execution is preceeded by the "configuration property file name" extracted out of the configuration list file.  The log file name
is then concatenated (associated) with the plan file name which comes out of the "Plan File List".  Together it is easy to associate a 
plan with a specific configuration.  The contents of the log come from the PDTool app.log as a single execution.  Please note that
the deployment plan log files are only the logs for the most recent execution.  The historical logs files are not saved.

/regression/logs
    /regression_unix_7.0.0_config/
        regression_unix_7.0.0-_initialize.dp.log
        regression_unix_7.0.0-ArchiveModule-01.dp.log
        ...
        regression_unix_7.0.0_cvs-_initialize_vcs.dp.log
        regression_unix_7.0.0_cvs-VCSModule-01.dp.log

If there is a failure, it will be easy to spot the word FAIL in the main log file (e.g. 20130212_165124_regression_unix_7.0.0_config.log)
which will then refer you to the specific log file so that you can see the actual error which comes from PDTool.

Sample log output:
------------------
        Log File: D:\dev\PDTool_Test\regression\logs\regression_win_7.0.0_config\20130215_090000_regression_win_7.0.0_config.log
        
        Regression Log File Results 
        Thu 02/15/2013 09:00:00.61 
        --------------------------- 

        ----------------------------------------------------- 
        Regression Test for regression_win_7.0.0.properties 
        Thu 02/15/2013 09:00:00.64 
        ----------------------------------------------------- 
        PASS: D:\dev\PDTool_Test\regression\plans\_initialize.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\ArchiveModule-01.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\GroupModule-01.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\UserModule-01.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\ServerAttribureModule-01.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\DataSourceModule-01.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\ResourceModule-01.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\RebindModule-01.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\ResourceCacheModule-01.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\TriggerModule-01.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\PrivilegeModule-01.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\RegressionModule-01.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\ServerManagerModule-01.dp 
        ...
        ----------------------------------------------------- 
        Thu 02/15/2013 09:20:37.29  Number Plans Executed: 67 
        PASS : Overall status for regression_win_6_2.properties and regression_plan_base_list.txt 
        ----------------------------------------------------- 

        ----------------------------------------------------- 
        Regression Test for regression_win_6_2_svn.properties 
        Thu 02/15/2013 09:20:37.32 
        ----------------------------------------------------- 
        PASS: D:\dev\PDTool_Test\regression\plans\_initialize_vcs.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\VCSModule-01.dp 
        ...
        PASS: D:\dev\PDTool_Test\regression\plans\VCSModule-18.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\VCSModuleConn-01.dp 
        ... 
        PASS: D:\dev\PDTool_Test\regression\plans\VCSModuleConn-18.dp 
        ----------------------------------------------------- 
        Thu 02/15/2013 09:27:55.89  Number Plans Executed: 35 
        PASS : Overall status for regression_win_6_2_svn.properties and regression_plan_vcs_base_list.txt 
        ----------------------------------------------------- 

        ----------------------------------------------------- 
        Regression Test for regression_win_6_2_cvs.properties 
        Thu 02/15/2013 09:27:55.92 
        ----------------------------------------------------- 
        PASS: D:\dev\PDTool_Test\regression\plans\_initialize_vcs.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\VCSModule-01.dp 
        ...
        PASS: D:\dev\PDTool_Test\regression\plans\VCSModule-18.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\VCSModuleConn-01.dp 
        ...
        PASS: D:\dev\PDTool_Test\regression\plans\VCSModuleConn-18.dp 
        ----------------------------------------------------- 
        Thu 02/15/2013 09:35:17.67  Number Plans Executed: 35 
        PASS : Overall status for regression_win_6_2_cvs.properties and regression_plan_vcs_base_list.txt 
        ----------------------------------------------------- 

        ----------------------------------------------------- 
        Regression Test for regression_win_6_2_p4.properties 
        Thu 02/15/2013 09:35:17.71 
        ----------------------------------------------------- 
        PASS: D:\dev\PDTool_Test\regression\plans\_initialize_vcs.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\VCSModule-01.dp 
        ... 
        PASS: D:\dev\PDTool_Test\regression\plans\VCSModule-18.dp 
        PASS: D:\dev\PDTool_Test\regression\plans\VCSModuleConn-01.dp 
        ... 
        PASS: D:\dev\PDTool_Test\regression\plans\VCSModuleConn-18.dp 
        ----------------------------------------------------- 
        Thu 02/15/2013 09:48:04.14  Number Plans Executed: 35 
        PASS : Overall status for regression_win_6_2_p4.properties and regression_plan_vcs_base_list.txt 
        ----------------------------------------------------- 

        ----------------------------------------------------- 
        Thu 02/15/2013 09:48:04.16  Total Plans Executed: 172 
        PASS : Overall status for this regression 

-----------------------------------------------------------
Debug
-----------------------------------------------------------
Debug is turned on and off by passing in a sindle character Y or N to regression_config_driver.bat or .sh
If no parameter is passed in then the default is DEBUG=N.
