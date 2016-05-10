@echo off
SETLOCAL
REM ######################################################################
REM # (c) 2014 Cisco and/or its affiliates. All rights reserved.
REM # 
REM # This software is released under the Eclipse Public License. The details can be found in the file LICENSE. 
REM # Any dependent libraries supplied by third parties are provided under their own open source licenses as 
REM # described in their own LICENSE files, generally named .LICENSE.txt. The libraries supplied by Cisco as 
REM # part of the Composite Information Server/Cisco Data Virtualization Server, particularly csadmin-XXXX.jar, 
REM # csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar, csext-XXXX.jar, csjdbc-XXXX.jar, 
REM # csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar, and customproc-XXXX.jar (where -XXXX is an 
REM # optional version number) are provided as a convenience, but are covered under the licensing for the 
REM # Composite Information Server/Cisco Data Virtualization Server. They cannot be used in any way except 
REM # through a valid license for that product.
REM # 
REM # This software is released AS-IS!. Support for this software is not covered by standard maintenance agreements with Cisco. 
REM # Any support for this software by Cisco would be covered by paid consulting agreements, and would be billable work.
REM # 
REM ######################################################################
REM #
REM # ExecutePDToolRelease.bat REL_DESCRIPTION  REL_FORCE_WORKSPACE_INIT  REL_VCS_TYPE  REL_VCS_REPOSITORY_URL  REL_VCS_PROJECT_ROOT  REL_VCS_USERNAME  REL_VCS_PASSWORD  REL_RELEASE_FOLDER  REL_VCS_WORKSPACE_CONF_DIR  REL_VCS_WORKSPACE_SRC_DIR  REL_VCS_TEMP_DIR  REL_CIS_USERNAME  REL_CIS_PASSWORD  REL_CIS_DOMAIN  REL_ENV_CONFIG_FILE_NAME  REL_PLAN_FILE_NAME  REL_NO_OPERATION  REL_VALID_ENV_CONFIG_PAIRS
REM #
REM #    Description:
REM #       This batch file is used to integrate with homegrown or 3rd party VCS tools that perform VCS continuous integration and deployment tasks.
REM #       The reason this file is needed is that it not only translates the various directory paths to required variables but it also executes a workspace intialization for each release.
REM #       The workspace initialization is a key task to insure that the workspace is up-to-date based on the release folder being deployed.  However, it is also effficient
REM #       as possible in that if the workspace is defined in such a way that it is reused across environments then the initialization only has to be done once per release.
REM #       If multiple users can be executing a deployment at the same time then it is important to insure the workspace is a combination of the username and the release folder
REM #       so that the workspace is unique for each person deploying simulataneously.
REM #       
REM #    Preview Mode [contains -noop at the end]
REM #       Example 1: ExecutePDToolRelease.bat "release description" true TFS "20160415"     "" "" "" "" "D:\wks\jdoe20160415r"     "D:\wks\jdoe20160415"     "D:\wks\jdoe20160415t"     cisuser cispassword composite TUAT deploy.dp -noop
REM #       Example 2: ExecutePDToolRelease.bat "release description" true SVN "20160415\src" "" "" "" "" "D:\wks\jdoe20160415\conf" "D:\wks\jdoe20160415\src" "D:\wks\jdoe20160415\temp" cisuser cispassword composite SUAT deploy.dp -noop
REM #    Execute Mode
REM #       Example 1: ExecutePDToolRelease.bat "release description" true TFS "20160415"     "" "" "" "" "D:\wks\jdoe20160415r"     "D:\wks\jdoe20160415"     "D:\wks\jdoe20160415t"     cisuser cispassword composite TUAT deploy.dp
REM #       Example 2: ExecutePDToolRelease.bat "release description" true SVN "20160415\src" "" "" "" "" "D:\wks\jdoe20160415\conf" "D:\wks\jdoe20160415\src" "D:\wks\jdoe20160415\temp" cisuser cispassword composite SUAT deploy.dp
REM #
REM # Input Variable Order is required:
REM # ---------------------------------
REM #   Note: This nomenclature refers to selecting 1 and only 1 within the brackets: [NOVCS|TFS|SVN|GIT|P4|CVS]
REM #
REM #   1.  REL_DESCRIPTION - Provides a way of passing in a description of the deployment or identifying the organization who is deploying. 
REM #
REM #   2.  REL_FORCE_WORKSPACE_INIT - [true|false] Force workspace initialization. 
REM #           Whenever there is a change to the VCS release folder and the workspace was previously intialized, this setting will force a re-initialization to pick up new resources.
REM #
REM #   3.  REL_VCS_TYPE - [SVN,TFS,CVS,P4,GIT] The type of VCS system being used.  
REM #           Note: Used to set VCS_TYPE and other specific VCS related variables used in setMyPrePDToolVars.bat.
REM #
REM #   4.  REL_VCS_REPOSITORY_URL - [Optional].  The VCS repository URL pointing to the repository's collection or base folder path
REM #           Note: Leave this blank to use the default values for [TFS|SVN|GIT|P4|CVS]_VCS_REPOSITORY_URL in setMyPrePDToolVars.bat
REM #                 Always use 4 forward slashes to escape https://url --> https:////url and no slash at the end.
REM #                      http://tfs-hostname/tfs/DefaultCollection/WorkTracking/CiscoDV/Rel/20160516m ----->|--/services
REM #                      |---------------------------------------| |----------------------| |-------|       |  /shared
REM #           Variables: VCS_REPOSITORY_URL                        VCS_PROJECT_ROOT         RELEASE_FOLDER  |--/user
REM #
REM #                      http://svn-hostname/svnrepos/myrepo/DataServices7/branches/R.16.05.16.1_IND/src -->|--/services
REM #                      |-----------------------------------------------| |------| |------------------|    |  /shared
REM #           Variables: VCS_REPOSITORY_URL                                ^        RELEASE_FOLDER          |--/user
REM #                                                                        VCS_PROJECT_ROOT
REM #
REM #   5.  REL_VCS_PROJECT_ROOT - [Optional].  The VCS relative folder path starts directly after the VCS Repository URL and ending where the Cisco base level root folders [/shared] start
REM #           Note: Leave this blank to use the default values for [TFS|SVN|GIT|P4|CVS]_VCS_PROJECT_ROOT in setMyPrePDToolVars.bat
REM #                 The project root does not start with a slash.
REM #                      http://tfs-hostname/tfs/DefaultCollection/WorkTracking/CiscoDV/Rel/20160516m ----->|--/services
REM #                      |---------------------------------------| |----------------------| |-------|       |  /shared
REM #           Variables: VCS_REPOSITORY_URL                        VCS_PROJECT_ROOT         RELEASE_FOLDER  |--/user
REM #
REM #                      http://svn-hostname/svnrepos/myrepo/DataServices7/branches/R.16.05.16.1_IND/src -->|--/services
REM #                      |-----------------------------------------------| |------| |------------------|    |  /shared
REM #           Variables: VCS_REPOSITORY_URL                                ^        RELEASE_FOLDER          |--/user
REM #                                                                        VCS_PROJECT_ROOT
REM #
REM #   6.  REL_VCS_USERNAME - [Optional].  VCS user name including the domain if applicable.  For a VCS that authenticates against an LDAP it may need to include the domain user@domain.
REM #           Note: Leave this blank to use the default values for [TFS|SVN|GIT|P4|CVS]_VCS_USERNAME in setMyPrePDToolVars.bat
REM #
REM #   7.  REL_VCS_PASSWORD - [Optional].  VCS user password.
REM #           Note: Leave this blank to use the default values for [TFS|SVN|GIT|P4|CVS]_VCS_PASSWORD in setMyPrePDToolVars.bat
REM #
REM #   8.  REL_RELEASE_FOLDER - The release folder identifies which VCS release folders will be used for deployment.  
REM #           It is the relative path into the VCS and the local workspace that specifies where the Cisco DV source files begin.
REM #             Identify the folder in the VCS that changes on a per release basis and include any subfolders leading up to 
REM #             where the Cisco DV repo folders begin such as /shared and you have identified the release folder(s).
REM #           Note: This will replace RELEASE_FOLDER in VCSModule.xml.
REM #
REM #           TFS Example: YYYYMMDD[m,i,o] - This is a relative path into the VCS URL.  
REM #                      REL_RELEASE_FOLDER=20160516m
REM #                      Full TFS URL for a given release where the Cisco DV source begins:  
REM #                      http://tfs-hostname/tfs/DefaultCollection/WorkTracking/CiscoDV/Rel/20160516m ----->|--/services
REM #                      |---------------------------------------| |----------------------| |-------|       |  /shared
REM #           Variables: VCS_REPOSITORY_URL                        VCS_PROJECT_ROOT         RELEASE_FOLDER  |--/user
REM #
REM #           SVN Example: R.YY.MM.DD.n_[INT,IND,OTH]/src - This is a relative path into the VCS URL.  
REM #                      REL_RELEASE_FOLDER=R.16.05.16.1_IND/src
REM #                      Full SVN URL for a given release where the Cisco DV source begins:  
REM #                      http://svn-hostname/svnrepos/myrepo/DataServices7/branches/R.16.05.16.1_IND/src -->|--/services
REM #                      |-----------------------------------------------| |------| |------------------|    |  /shared
REM #           Variables: VCS_REPOSITORY_URL                                ^        RELEASE_FOLDER          |--/user
REM #                                                                        VCS_PROJECT_ROOT
REM #
REM #   9.  REL_VCS_WORKSPACE_CONF_DIR - Full path to the PDTool resource configuration directory containing the required /modules and /plans as child folders.  
REM #           Note: This will be used to set REL_MODULE_HOME which is used in the deployment plans (.dp) to replace MODULE_HOME as the location of the .xml module files.
REM #                 This will be used to set REL_PLAN_HOME which may be used externally to reference other required resources in the plan directory.
REM #           Example .dp file entry:
REM #               PASS	TRUE	ExecuteAction  	vcsCheckout2  $SERVERID $VCONN /shared/test00/my_proc_1 "PROCEDURE" HEAD "$REL_MODULE_HOME/VCSModule.xml"	"$MODULE_HOME/servers.xml"
REM #                                                                                                                         |---------------| --> set in ExecutePDToolRelease.bat
REM #           TFS Example: 
REM #                 REL_MODULE_HOME=C:\TEMP\TFS7\usernameYYYYMMDDmr --------------------------------------->|--/modules
REM #                   REL_PLAN_HOME=C:\TEMP\TFS7\usernameYYYYMMDDmr --------------------------------------->|--/plans
REM #           SVN Example: 
REM #                 REL_MODULE_HOME=C:\TEMP\SVN7\unique#_app_component_env\branches\R.16.05.16.1_IND\conf-->|--/modules
REM #                   REL_PLAN_HOME=C:\TEMP\SVN7\unique#_app_component_env\branches\R.16.05.16.1_IND\conf-->|--/plans
REM #
REM #   10. REL_VCS_WORKSPACE_SRC_DIR - Full path to the PDTool workspace directory as defined by the invoking program.  
REM #           Note: This script will derive REL_VCS_WORKSPACE_HOME and REL_VCS_WORKSPACE_NAME from this path.
REM #                 This will replace [TFS|SVN|GIT|P4|CVS]_WORKSPACE_HOME and [TFS|SVN|GIT|P4|CVS]_WORKSPACE_NAME in setMyPrePDToolVars.bat
REM #           TFS Example: 
REM #               VCS_WORKSPACE_DIR=C:\TEMP\TFS7\usernameYYYYMMDDm\WorkTracking\CiscoDV\Rel\20160516m ----->|--/services
REM #                                                                                                         |  /shared
REM #                                                                                                         |--/user
REM #           SVN Example: 
REM #               VCS_WORKSPACE_DIR=C:\TEMP\SVN7\unique#_app_component_env\branches\R.16.05.16.1_IND\src -->|--/services
REM #                                                                                                         |  /shared
REM #                                                                                                         |--/user
REM #
REM #   11. REL_VCS_TEMP_DIR - Full path to the PDTool workspace CIS temp directory as defined by the invoking program.  
REM #           Note: This will replace [TFS|SVN|GIT|P4|CVS]_VCS_TEMP_DIR in setMyPrePDToolVars.bat
REM #           TFS Example: 
REM #                    VCS_TEMP_DIR=C:\TEMP\TFS7\usernameYYYYMMDDmt --------------------------------------->|--checkout.car
REM #           SVN Example: 
REM #                    VCS_TEMP_DIR=C:\TEMP\SVN7\unique#_app_component_env\branches\R.16.05.16.1_IND\temp-->|--checkout.car
REM #
REM #   12. REL_CIS_USERNAME - The CIS username which will be used to connect to the target composite server.    
REM #           Note: This will replace CIS_USERNAME in setMyPrePDToolVars.bat. Must have privileges to import resources and execute privilege script.
REM #
REM #   13. REL_CIS_PASSWORD - The CIS password for the REL_CIS_USERNAME user.  
REM #           Note: This will replace CIS_PASSWORD in setMyPrePDToolVars.bat.
REM #
REM #   14. REL_CIS_DOMAIN - The CIS domain for the REL_CIS_USERNAME user.  
REM #           Note: This will replace CIS_DOMAIN in setMyPrePDToolVars.bat.
REM #
REM #   15. REL_ENV_CONFIG_FILE_NAME - Instructs "ExecutePDTool.bat" to use a specific configuration property file which specifies an environment to connect to.  
REM #           Note: This will be used to set CONFIG_PROPERTY_FILE in ExecutePDTool.bat and short names are mapped to the full property file name.
REM #                 This will be validated against [NOVCS|TFS|SVN|GIT|P4|CVS]_VALID_ENV_CONFIG_PAIRS in setMyPrePDToolVars.bat
REM #
REM #           The configuration property files are located in /PDTool/resources/config by default.  Short names or long names may be used.
REM #           Short name mappings are specified in the setMyPrePDToolVars.bat with the following format:
REM #             Format of pairs: XDEV=deploy_VCSTYPE_DEV,XUAT=deploy_VCSTYPE_UAT,XPROD=deploy_VCSTYPE_PROD
REM #                 X represents the VCS environment. N=NOVCS,S=SVN,T=TFS,G=GIT,P=P4,C=CVS
REM #                 This makes the ENV_TYPE unique across all different list types.
REM #             Examples:
REM #                 TFS_VALID_ENV_CONFIG_PAIRS=TDEV~deploy_TFS_DEV,TUAT~deploy_TFS_UAT,TPROD~deploy_TFS_PROD
REM #                 SVN_VALID_ENV_CONFIG_PAIRS=SDEV~deploy_SVN_DEV,SUAT~deploy_SVN_UAT,SPROD~deploy_SVN_PROD
REM #                 GIT_VALID_ENV_CONFIG_PAIRS=GDEV~deploy_GIT_DEV,GUAT~deploy_GIT_UAT,GPROD~deploy_GIT_PROD
REM #                 CVS_VALID_ENV_CONFIG_PAIRS=CDEV~deploy_CVS_DEV,CUAT~deploy_CVS_UAT,CPROD~deploy_CVS_PROD
REM #                  P4_VALID_ENV_CONFIG_PAIRS=PDEV~deploy_P4_DEV,PUAT~deploy_P4_UAT,PPROD~deploy_P4_PROD
REM #
REM #   16. REL_PLAN_FILE_NAME - Instructs "ExecutePDTool.bat" to execute a specific deployment plan.  
REM #           Note: The plan file location is constructed in this script using this local variable.
REM #
REM #   17. REL_NO_OPERATION - [-noop] - [Optional] This is an optional parameter that instructs PDTool to not perform the operation but go through the motions of performing it.
REM #
REM #   18. REL_VALID_ENV_CONFIG_PAIRS - [Optional] This optional parameter allows the invoking program to specify the environment configuration property file pairs for a given
REM #           organization.   For example deploy7.0.3_SVN_ORG1_UAT.properties file represents the subversion organization 1 variables for the UAT environment.  These property
REM #           files contain variables that establish the connection details to a CIS instance therefore they are key in determining where PDTool is going to connect.
REM #             Examples:
REM #                 TFS_VALID_ENV_CONFIG_PAIRS=TDEV~deploy_TFS_DEV,TUAT~deploy_TFS_UAT,TPROD~deploy_TFS_PROD
REM #                 SVN_VALID_ENV_CONFIG_PAIRS=SDEV~deploy_SVN_DEV,SUAT~deploy_SVN_UAT,SPROD~deploy_SVN_PROD
REM #                 GIT_VALID_ENV_CONFIG_PAIRS=GDEV~deploy_GIT_DEV,GUAT~deploy_GIT_UAT,GPROD~deploy_GIT_PROD
REM #                 CVS_VALID_ENV_CONFIG_PAIRS=CDEV~deploy_CVS_DEV,CUAT~deploy_CVS_UAT,CPROD~deploy_CVS_PROD
REM #                  P4_VALID_ENV_CONFIG_PAIRS=PDEV~deploy_P4_DEV,PUAT~deploy_P4_UAT,PPROD~deploy_P4_PROD
REM #
REM #=======================================================================================
REM #  Key variables related to setting workspace and VCS variables.
REM #     These variables are set in either ExecutePDToolRelease.bat or setMyPrePDToolVars.bat and used in VCSModule.xml:
REM #
REM #                 <VCS_REPOSITORY_URL>$[TFS|SVN|GIT|P4|CVS]_VCS_REPOSITORY_URL</VCS_REPOSITORY_URL>
REM #                                     |--------------------------------------| 
REM #                                     |--> set as REL_VCS_REPOSITORY_URL in ExecutePDToolRelease.bat first and then in setMyPrePDToolVars.bat
REM #
REM #                 <VCS_PROJECT_ROOT>$[TFS|SVN|GIT|P4|CVS]_VCS_PROJECT_ROOT$%RELEASE_FOLDER%</VCS_PROJECT_ROOT>
REM #                                   |-------------------------------------||--------------| 
REM #                                   |                                      |--> set as RELEASE_FOLDER in ExecutePDToolRelease.bat
REM #                                   | --> set as REL_VCS_PROJECT_ROOT in ExecutePDToolRelease.bat first and then in setMyPrePDToolVars.bat
REM #
REM #                 <VCS_WORKSPACE_HOME>$[TFS|SVN|GIT|P4|CVS]_VCS_WORKSPACE_HOME</VCS_WORKSPACE_HOME>
REM #                                     |--------------------------------------| 
REM #                                     |--> set as REL_WORKSPACE_HOME in ExecutePDToolRelease.bat first and then in setMyPrePDToolVars.bat
REM #
REM #                 <VCS_WORKSPACE_NAME>$[TFS|SVN|GIT|P4|CVS]_VCS_WORKSPACE_NAME</VCS_WORKSPACE_NAME>
REM #                                     |--------------------------------------| 
REM #                                     |--> set as REL_WORKSPACE_NAME in ExecutePDToolRelease.bat first and then in setMyPrePDToolVars.bat
REM #
REM #                 <VCS_WORKSPACE_DIR>$VCS_WORKSPACE_HOME/$VCS_WORKSPACE_NAME</VCS_WORKSPACE_DIR>
REM #                                    |-------------------------------------| 
REM #                                    |--> set in VCSModule.bat
REM #
REM #                 <VCS_TEMP_DIR>$[TFS|SVN|GIT|P4|CVS]_VCS_TEMP_DIR</VCS_TEMP_DIR>
REM #                               |--------------------------------| 
REM #                               |--> set as REL_VCS_TEMP_DIR in ExecutePDToolRelease.bat first and then in setMyPrePDToolVars.bat
REM #                 
REM # Editor: Set tab=4 in your text editor for this file to format properly
REM #=======================================================================================
REM # Variable References:
REM #   REL_DESCRIPTION              is used in this batch file
REM #   REL_FORCE_WORKSPACE_INIT     is used in this batch file
REM #   REL_VCS_TYPE 			     is used in setMyPrePDToolVars.bat
REM #   REL_VCS_REPOSITORY_URL 	     is used in setMyPrePDToolVars.bat
REM #   REL_VCS_PROJECT_ROOT 	     is used in setMyPrePDToolVars.bat
REM #   REL_VCS_USERNAME 	         is used in setMyPrePDToolVars.bat
REM #   REL_VCS_PASSWORD 	         is used in setMyPrePDToolVars.bat
REM #   REL_RELEASE_FOLDER 	         is used in setMyPrePDToolVars.bat
REM #   REL_VCS_WORKSPACE_CONF_DIR 	 is used in setMyPrePDToolVars.bat
REM #   REL_VCS_WORKSPACE_SRC_DIR 	 is used in setMyPrePDToolVars.bat
REM #   REL_VCS_TEMP_DIR             is used in setMyPrePDToolVars.bat
REM #   REL_CIS_USERNAME 		     is used in setMyPrePDToolVars.bat
REM #   REL_CIS_PASSWORD 		     is used in setMyPrePDToolVars.bat
REM #   REL_CIS_DOMAIN 			     is used in setMyPrePDToolVars.bat
REM #   REL_ENV_CONFIG_FILE_NAME     is used in this batch file
REM #   REL_PLAN_FILE_NAME           is used in this batch file
REM #   REL_NO_OPERATION             is used in ExecutePDTool.bat
REM #   REL_VALID_ENV_CONFIG_PAIRS   is used in setMyPrePDToolVars.bat
REM #
REM # External variables
REM #   PDTOOL_HOME                  is used in this batch file but was set externally.
REM #   REL_VCS_MODULE_NAME          is used in the vcs-initialize-release.dp deployment plan.
REM #   REL_VCS_WORKSPACE_HOME       is used in setMyPrePDToolVars.bat
REM #   REL_VCS_WORKSPACE_NAME 	     is used in setMyPrePDToolVars.bat
REM #   REL_MODULE_HOME 		     is used in custom deployment plans
REM #   REL_PLAN_HOME 		 	     is used in this batch file and may be used externally.
REM #   REL_ANT_HOME 		 	     is used may be used externally.
REM #
REM # Local variables
REM #   REL_VCS_INIT_PLAN_NAME       is used in this batch file
REM #   REL_PLAN_FILE_EXT            is used in this batch file
REM #   REL_INIT_WORKSPACE           is used in this batch file
REM #   REL_CONFIG_CMD               is used in this batch file
REM #   REL_DERIVED_VCS_PROJECT_ROOT is used in this batch file
REM #   REL_VCS_WORKSPACE_DIR        is used in this batch file
REM #
REM #----------------------------------------------------------
REM #******** MODIFY CUSTOM VARIABLES AS REQUIRED *************
REM #----------------------------------------------------------
REM # Debug=[1,0]  1=debug on, 0=debug off
set debug=0
REM # Set the name of the VCS initialization deployment plan.  The default one ships with PDTool.
set REL_VCS_INIT_PLAN_NAME=vcs-initialize-release.dp
REM # Set the name of the VCS Module XML to that provides the VCS configuration.
set REL_VCS_MODULE_NAME=VCSModule.xml
REM #
REM #----------------------------------------------------------
REM #*********** DO NOT MODIFY BELOW THIS LINE ****************
REM #----------------------------------------------------------
REM # CIS version [6.2, 7.0.0] - set DEFAULT_CIS_VERSION
if exist cisVersion.bat call cisVersion.bat
REM #
REM # The name of this batch file.
for %%a in (%0) do set REL_BATCH_FILE_NAME=%%~na
for %%a in (%0) do set REL_BATCH_FILE_EXT=%%~xa
if "%REL_BATCH_FILE_EXT%"=="" set REL_BATCH_FILE_NAME=%REL_BATCH_FILE_NAME%.bat
if "%REL_BATCH_FILE_EXT%" NEQ "" set REL_BATCH_FILE_NAME=%REL_BATCH_FILE_NAME%%REL_BATCH_FILE_EXT%
REM #
REM # Set the present working directory (current directory)
set PWD=%CD%
REM #
REM # Save the current directory and invoke the PDTool variables to get the VCS variables.
REM #    Setting PDTOOL_HOME and TFS_HOME and [SVN|TFS|GIT|CVS|P4]_VCS_PROJECT_ROOT
REM #    [...]_VCS_PROJECT_ROOT is used to calculate the REL_VCS_WORKSPACE_HOME and REL_VCS_WORKSPACE_NAME
pushd %CD%
if not exist setVars.bat (
   cls
   echo.%REL_BATCH_FILE_NAME%:: ERROR: Could not find setVars.bat.
   set ERROR=1
   goto END
)
call setVars.bat
cls
popd

REM # Begin the script execution.
cls
echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------
echo.%REL_BATCH_FILE_NAME%::-----------                                            -----------
echo.%REL_BATCH_FILE_NAME%::----------- Cisco Advanced Services                    -----------
echo.%REL_BATCH_FILE_NAME%::----------- PDTool: Promotion and Deployment Tool      -----------
echo.%REL_BATCH_FILE_NAME%::-----------                                            -----------
echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------
echo.%REL_BATCH_FILE_NAME%::
echo.%REL_BATCH_FILE_NAME%:: -------------------------------------------------
echo.%REL_BATCH_FILE_NAME%:: Execute PDTool %CIS_VERSION% Release Batch Interface
echo.%REL_BATCH_FILE_NAME%::
if not exist %PDTOOL_HOME% (
   echo.%REL_BATCH_FILE_NAME%:: -------------------------------------------------
   echo.%REL_BATCH_FILE_NAME%::
   echo.%REL_BATCH_FILE_NAME%::-------------------------------------------------------------------------------------
   echo.%REL_BATCH_FILE_NAME%:: Execution Failed: Error - The directory does not exist.
   echo.%REL_BATCH_FILE_NAME%::    PDTOOL_HOME=%PDTOOL_HOME%
   echo.%REL_BATCH_FILE_NAME%::
   echo.%REL_BATCH_FILE_NAME%:: Reconfigure PDTOOL_HOME to point to the correct location.
   echo.%REL_BATCH_FILE_NAME%::-------------------------------------------------------------------------------------
   set ERROR=1
   goto END
)

REM #---------------------------------------------
REM # Property Order Precedence
REM #---------------------------------------------
REM # The property order of precedence defines which properties are retrieved in what order.
REM #   JVM - These are properties that are set on the JVM command line with a -DVAR=value
REM #   PROPERTY_FILE - These are the variables set in the configuration property file like deploy.properties or in the VCSModule.xml
REM #   SYSTEM - These are variables that are set in batch files in the operating system prior to invocation of PDTool.
REM # If left blank, the default=JVM PROPERTY_FILE SYSTEM
REM # However, it may be necessary to be able to override what is in the property file and pick up an environment variable first.
set propertyOrderPrecedence=JVM SYSTEM PROPERTY_FILE

REM #---------------------------------------------
REM # Initalize input variables
REM #---------------------------------------------
set REL_DESCRIPTION=
set REL_FORCE_WORKSPACE_INIT=
set REL_VCS_TYPE=
set REL_VCS_REPOSITORY_URL=
set REL_VCS_PROJECT_ROOT=
set REL_VCS_USERNAME=
set REL_VCS_PASSWORD=
set REL_RELEASE_FOLDER=
set REL_VCS_WORKSPACE_CONF_DIR=
set REL_VCS_WORKSPACE_SRC_DIR=
set REL_VCS_TEMP_DIR=
set REL_CIS_USERNAME=
set REL_CIS_PASSWORD=
set REL_CIS_DOMAIN=
set REL_ENV_CONFIG_FILE_NAME=
set REL_PLAN_FILE_NAME=
set REL_NO_OPERATION=
set REL_VALID_ENV_CONFIG_PAIRS=
REM # Initialize external variables
set REL_VCS_WORKSPACE_HOME=
set REL_VCS_WORKSPACE_NAME=
set REL_MODULE_HOME=
REM # Initalize local variables
set REL_PLAN_HOME=
set REL_ANT_HOME=
set REL_PLAN_FILE_EXT=
set REL_INIT_WORKSPACE=
set REL_CONFIG_CMD=
set ERROR=0


REM #---------------------------------------------
REM # Process Input Parameters
REM #---------------------------------------------
REM #   1.  REL_DESCRIPTION - Provides a way of passing in a description of the deployment or identifying the organization who is deploying. 
REM #   2.  REL_FORCE_WORKSPACE_INIT - [true|false] Force workspace initialization. Whenever there is a change to the VCS release folder and the workspace was previously intialized, this setting will force a re-initialization to pick up new resources.
REM #   3.  REL_VCS_TYPE - [SVN,TFS,CVS,P4,GIT] The type of VCS system being used.   Used to construct the workspace path.  It does not replace any other variables.  Used in VCSModuleRelease.xml with VCS_WORKSPACE_HOME variable.
REM #   4.  REL_VCS_REPOSITORY_URL - The VCS repository URL pointing to the repository's collection or base folder path
REM #   5.  REL_VCS_PROJECT_ROOT - The VCS relative folder path starting directly after the VCS Repository URL and ending where the Cisco base level root folders [/shared] start
REM #   6.  REL_VCS_USERNAME - VCS user name including the domain if applicable.  For a VCS that authenticates against an LDAP it may need to include the domain user@domain.
REM #   7.  REL_VCS_PASSWORD - VCS user password.
REM #   8.  REL_RELEASE_FOLDER - The release folder identifies which VCS release folder will be used for deployment.  e.g. YYYYMMDD, YYYYMMDDi, YYYYMMDDm.  Used to construct the workspace path.
REM #   9.  REL_VCS_WORKSPACE_CONF_DIR - Full path to the PDTool resource configuration directory containing the required /modules and /plans as child folders.
REM #   10. REL_VCS_WORKSPACE_SRC_DIR - Full path to the PDTool workspace directory as defined by the invoking program.  
REM #   11. REL_VCS_TEMP_DIR - Full path to the PDTool workspace CIS temp directory as defined by the invoking program.
REM #   12. REL_CIS_USERNAME - The CIS username which will be used to connect to the target composite server.    This will replace CIS_USERNAME in setMyPrePDToolVars.bat. Must have privileges to import resources and execute privilege script.
REM #   13. REL_CIS_PASSWORD - The CIS password for the REL_CIS_USERNAME user.  This will replace CIS_PASSWORD in setMyPrePDToolVars.bat.
REM #   14. REL_CIS_DOMAIN - The CIS domain for the REL_CIS_USERNAME user.  This will replace CIS_DOMAIN in setMyPrePDToolVars.bat.
REM #   15. REL_ENV_CONFIG_FILE_NAME - Instructs "ExecutePDTool.bat" to use a specific configuration property file which specifies an environment to connect to.  They are located in /PDTool/resources/config by default.
REM #   16. REL_PLAN_FILE_NAME - Instructs "ExecutePDTool.bat" to execute a specific deployment plan.  The plan file directory is constructed in this script.
REM #   17. REL_NO_OPERATION - [-noop] - This is an optional parameter that instructs PDTool to not perform the operation but go through the motions of performing it.
REM #   18. REL_VALID_ENV_CONFIG_PAIRS - [Optional] This optional parameter allows the invoking program to specify the environment configuration property file pairs for a given organization.
set REL_DESCRIPTION=%1
shift
set REL_FORCE_WORKSPACE_INIT=%1
shift
set REL_VCS_TYPE=%1
shift
set REL_VCS_REPOSITORY_URL=%1
shift
set REL_VCS_PROJECT_ROOT=%1
shift
set REL_VCS_USERNAME=%1
shift
set REL_VCS_PASSWORD=%1
shift
set REL_RELEASE_FOLDER=%1
shift
set REL_VCS_WORKSPACE_CONF_DIR=%1
shift
set REL_VCS_WORKSPACE_SRC_DIR=%1
shift
set REL_VCS_TEMP_DIR=%1
shift
set REL_CIS_USERNAME=%1
shift
set REL_CIS_PASSWORD=%1
shift
set REL_CIS_DOMAIN=%1
shift
set REL_ENV_CONFIG_FILE_NAME=%1
shift
set REL_PLAN_FILE_NAME=%1
shift
set REL_NO_OPERATION=%1
shift
set REL_VALID_ENV_CONFIG_PAIRS=%1
shift

REM # Remove double quotes
setlocal EnableDelayedExpansion
if defined REL_DESCRIPTION            set REL_DESCRIPTION=!REL_DESCRIPTION:"=!
if defined REL_FORCE_WORKSPACE_INIT   set REL_FORCE_WORKSPACE_INIT=!REL_FORCE_WORKSPACE_INIT:"=!
if defined REL_VCS_TYPE               set REL_VCS_TYPE=!REL_VCS_TYPE:"=!
if defined REL_VCS_REPOSITORY_URL     set REL_VCS_REPOSITORY_URL=!REL_VCS_REPOSITORY_URL:"=!
if defined REL_VCS_PROJECT_ROOT       set REL_VCS_PROJECT_ROOT=!REL_VCS_PROJECT_ROOT:"=!
if defined REL_VCS_USERNAME           set REL_VCS_USERNAME=!REL_VCS_USERNAME:"=!
if defined REL_VCS_PASSWORD           set REL_VCS_PASSWORD=!REL_VCS_PASSWORD:"=!
if defined REL_RELEASE_FOLDER         set REL_RELEASE_FOLDER=!REL_RELEASE_FOLDER:"=!
if defined REL_VCS_WORKSPACE_CONF_DIR set REL_VCS_WORKSPACE_CONF_DIR=!REL_VCS_WORKSPACE_CONF_DIR:"=!
if defined REL_VCS_WORKSPACE_SRC_DIR  set REL_VCS_WORKSPACE_SRC_DIR=!REL_VCS_WORKSPACE_SRC_DIR:"=!
if defined REL_VCS_TEMP_DIR           set REL_VCS_TEMP_DIR=!REL_VCS_TEMP_DIR:"=!
if defined REL_CIS_USERNAME           set REL_CIS_USERNAME=!REL_CIS_USERNAME:"=!
if defined REL_CIS_PASSWORD           set REL_CIS_PASSWORD=!REL_CIS_PASSWORD:"=!
if defined REL_CIS_DOMAIN             set REL_CIS_DOMAIN=!REL_CIS_DOMAIN:"=!
if defined REL_ENV_CONFIG_FILE_NAME   set REL_ENV_CONFIG_FILE_NAME=!REL_ENV_CONFIG_FILE_NAME:"=!
if defined REL_PLAN_FILE_NAME         set REL_PLAN_FILE_NAME=!REL_PLAN_FILE_NAME:"=!
if defined REL_PLAN_FILE_NAME         for %%a in (%REL_PLAN_FILE_NAME%) do set REL_PLAN_FILE_EXT=%%~xa
if defined REL_NO_OPERATION           set REL_NO_OPERATION=!REL_NO_OPERATION:"=!
if defined REL_VALID_ENV_CONFIG_PAIRS set REL_VALID_ENV_CONFIG_PAIRS=!REL_VALID_ENV_CONFIG_PAIRS:"=!
setlocal

REM # Display the release description
echo.%REL_BATCH_FILE_NAME%:: Description: %REL_DESCRIPTION%
echo.%REL_BATCH_FILE_NAME%:: -------------------------------------------------
echo.%REL_BATCH_FILE_NAME%::

REM # Set the printing password
set PR_REL_VCS_PASSWORD=%REL_VCS_PASSWORD%
if defined REL_VCS_PASSWORD set PR_REL_VCS_PASSWORD=********
set PR_REL_CIS_PASSWORD=%REL_CIS_PASSWORD%
if defined REL_CIS_PASSWORD set PR_REL_CIS_PASSWORD=********

REM # Capture the parameters
set REL_INPUT_PARAMS="%REL_FORCE_WORKSPACE_INIT%" "%REL_VCS_TYPE%" "%REL_VCS_REPOSITORY_URL%" "%REL_VCS_PROJECT_ROOT%" "%REL_VCS_USERNAME%" "%PR_REL_VCS_PASSWORD%" "%REL_RELEASE_FOLDER%" "%REL_VCS_WORKSPACE_CONF_DIR%" "%REL_VCS_WORKSPACE_SRC_DIR%" "%REL_VCS_TEMP_DIR%" "%REL_CIS_USERNAME%" "%PR_REL_CIS_PASSWORD%" "%P_CIS_DOMAIN%" "%REL_ENV_CONFIG_FILE_NAME%" "%REL_PLAN_FILE_NAME%" "%REL_NO_OPERATION%" "%REL_VALID_ENV_CONFIG_PAIRS%"

REM #---------------------------------------------
REM # Validate input and set REL_VCS_WORKSPACE_NAME
REM #---------------------------------------------
if not defined REL_FORCE_WORKSPACE_INIT (
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   echo.%REL_BATCH_FILE_NAME%:: Execution Failed: REL_FORCE_WORKSPACE_INIT is not set.
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   set ERROR=1
   goto END
)
if not defined REL_VCS_TYPE (
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   echo.%REL_BATCH_FILE_NAME%:: Execution Failed: REL_VCS_TYPE is not set.
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   set ERROR=1
   goto END
)
if not defined REL_RELEASE_FOLDER (
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   echo.%REL_BATCH_FILE_NAME%:: Execution Failed: REL_RELEASE_FOLDER is not set.
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   set ERROR=1
   goto END
)
if not defined REL_VCS_WORKSPACE_CONF_DIR (
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   echo.%REL_BATCH_FILE_NAME%:: Execution Failed: REL_VCS_WORKSPACE_CONF_DIR is not set.
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   set ERROR=1
   goto END
)
if not defined REL_VCS_WORKSPACE_SRC_DIR (
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   echo.%REL_BATCH_FILE_NAME%:: Execution Failed: REL_VCS_WORKSPACE_SRC_DIR is not set.
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   set ERROR=1
   goto END
)
if not defined REL_VCS_TEMP_DIR (
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   echo.%REL_BATCH_FILE_NAME%:: Execution Failed: REL_VCS_TEMP_DIR is not set.
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   set ERROR=1
   goto END
)
if not defined REL_ENV_CONFIG_FILE_NAME (
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   echo.%REL_BATCH_FILE_NAME%:: Execution Failed: REL_ENV_CONFIG_FILE_NAME is not set.
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   set ERROR=1
   goto END
)
if not defined REL_PLAN_FILE_NAME (
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   echo.%REL_BATCH_FILE_NAME%:: Execution Failed: REL_PLAN_FILE_NAME is not set.
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   set ERROR=1
   goto END
)
if not defined REL_PLAN_FILE_EXT (
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   echo.%REL_BATCH_FILE_NAME%:: Execution Failed: REL_PLAN_FILE_EXT is not set.
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   set ERROR=1
   goto END
)

REM # Evaluate the parameter REL_FORCE_WORKSPACE_INIT for various values
set REL_INIT_WORKSPACE=0
if "%REL_FORCE_WORKSPACE_INIT%"=="T"		set REL_INIT_WORKSPACE=1
if "%REL_FORCE_WORKSPACE_INIT%"=="t"		set REL_INIT_WORKSPACE=1
if "%REL_FORCE_WORKSPACE_INIT%"=="TRUE"		set REL_INIT_WORKSPACE=1
if "%REL_FORCE_WORKSPACE_INIT%"=="true"		set REL_INIT_WORKSPACE=1
if "%REL_FORCE_WORKSPACE_INIT%"=="1"		set REL_INIT_WORKSPACE=1

REM # Determine if a configuration property file was passed in and set the -config command accordingly
set REL_CONFIG_CMD=
if defined REL_ENV_CONFIG_FILE_NAME set REL_CONFIG_CMD=-config %REL_ENV_CONFIG_FILE_NAME%

REM #---------------------------------------------
REM # Set workspace variables
REM #---------------------------------------------
REM #
REM # Resolve the REL_DERIVED_VCS_PROJECT_ROOT based on the incoming REL_VCS_TYPE
if "%REL_VCS_TYPE%"=="TFS" set REL_DERIVED_VCS_PROJECT_ROOT=%TFS_VCS_PROJECT_ROOT%
if "%REL_VCS_TYPE%"=="SVN" set REL_DERIVED_VCS_PROJECT_ROOT=%SVN_VCS_PROJECT_ROOT%
if "%REL_VCS_TYPE%"=="GIT" set REL_DERIVED_VCS_PROJECT_ROOT=%GIT_VCS_PROJECT_ROOT%
if "%REL_VCS_TYPE%"=="CVS" set REL_DERIVED_VCS_PROJECT_ROOT=%CVS_VCS_PROJECT_ROOT%
if "%REL_VCS_TYPE%"=="P4"  set REL_DERIVED_VCS_PROJECT_ROOT=%P4_VCS_PROJECT_ROOT%
if "%debug%"=="1" echo [DEBUG] REL_DERIVED_VCS_PROJECT_ROOT=%REL_DERIVED_VCS_PROJECT_ROOT%

REM # Change forward slash to back slash for REL_VCS_WORKSPACE_SRC_DIR, REL_VCS_WORKSPACE_DIR, REL_DERIVED_VCS_PROJECT_ROOT, REL_RELEASE_FOLDER
REM # syntax call:replace "oldstring" "newstring" "searchstring" outvariable
call:replace "%REL_VCS_WORKSPACE_SRC_DIR%"    "\" "/" REL_VCS_WORKSPACE_SRC_DIR
call:replace "%REL_VCS_WORKSPACE_DIR%"        "\" "/" REL_VCS_WORKSPACE_DIR
call:replace "%REL_DERIVED_VCS_PROJECT_ROOT%" "\" "/" REL_DERIVED_VCS_PROJECT_ROOT
call:replace "%REL_RELEASE_FOLDER%"           "\" "/" REL_RELEASE_FOLDER

REM # Remove the RELEASE_FOLDER from the end of the path
if "%debug%"=="1" echo [DEBUG] REL_RELEASE_FOLDER=%REL_RELEASE_FOLDER%
call:replace "%REL_VCS_WORKSPACE_SRC_DIR%" "" "\%REL_RELEASE_FOLDER%" REL_VCS_WORKSPACE_DIR
if "%debug%"=="1" echo [DEBUG] Remove RELEASE_FODER: REL_VCS_WORKSPACE_DIR=%REL_VCS_WORKSPACE_DIR%

REM # Remove the VCS_PROJECT_ROOT from the end of the path - Result is the VCS_WORKSPACE_DIR
call:replace "%REL_VCS_WORKSPACE_DIR%"     "" "\%REL_DERIVED_VCS_PROJECT_ROOT%"   REL_VCS_WORKSPACE_DIR
if "%debug%"=="1" echo [DEBUG] Remove VCS_PROJECT_ROOT: REL_VCS_WORKSPACE_DIR=%REL_VCS_WORKSPACE_DIR%

REM # Remove any separators at the end of the path
if "%REL_VCS_WORKSPACE_DIR:~-1%"=="\" CALL:REMOVE_LAST_SEPARATOR "%REL_VCS_WORKSPACE_DIR%" "\" REL_VCS_WORKSPACE_DIR
if "%REL_VCS_WORKSPACE_DIR:~-1%"=="/" CALL:REMOVE_LAST_SEPARATOR "%REL_VCS_WORKSPACE_DIR%" "/" REL_VCS_WORKSPACE_DIR
if "%debug%"=="1" echo [DEBUG] REL_VCS_WORKSPACE_DIR=%REL_VCS_WORKSPACE_DIR%

REM # Extract the VCS_WORKSPACE_NAME from REL_VCS_WORKSPACE_DIR
for %%a in (%REL_VCS_WORKSPACE_DIR%) do set REL_VCS_WORKSPACE_NAME=%%~na%%~xa
if "%debug%"=="1" echo [DEBUG] REL_VCS_WORKSPACE_NAME=%REL_VCS_WORKSPACE_NAME%

REM # Extract the VCS_WORKSPACE_HOME from REL_VCS_WORKSPACE_DIR
call:replace "%REL_VCS_WORKSPACE_DIR%"     "" "%REL_VCS_WORKSPACE_NAME%" REL_VCS_WORKSPACE_HOME
if "%debug%"=="1" echo [DEBUG] REL_VCS_WORKSPACE_HOME=%REL_VCS_WORKSPACE_HOME%

rem set REL_VCS_WORKSPACE_HOME=%WKS_DRIVE%%WKS_PATH%
if "%REL_VCS_WORKSPACE_HOME:~-1%"=="\" CALL:REMOVE_LAST_SEPARATOR "%REL_VCS_WORKSPACE_HOME%" "\" REL_VCS_WORKSPACE_HOME
if "%REL_VCS_WORKSPACE_HOME:~-1%"=="/" CALL:REMOVE_LAST_SEPARATOR "%REL_VCS_WORKSPACE_HOME%" "/" REL_VCS_WORKSPACE_HOME
if "%debug%"=="1" echo [DEBUG] REL_VCS_WORKSPACE_HOME=%REL_VCS_WORKSPACE_HOME%

REM # Workspace module home example:    C:\PDTool\SVN\usernameYYYYMMDDr\modules
set       REL_MODULE_HOME=%REL_VCS_WORKSPACE_CONF_DIR%\modules
REM # Workspace plans home example:     C:\PDTool\SVN\usernameYYYYMMDDr\plans
set         REL_PLAN_HOME=%REL_VCS_WORKSPACE_CONF_DIR%\plans
REM # Workspace ant home example:     C:\PDTool\SVN\usernameYYYYMMDDr\ant
set          REL_ANT_HOME=%REL_VCS_WORKSPACE_CONF_DIR%\ant

REM # Create the modules and plans and ant directories if they do not exist
if not exist "%REL_MODULE_HOME%" mkdir "%REL_MODULE_HOME%"
if not exist "%REL_PLAN_HOME%"   mkdir "%REL_PLAN_HOME%"
REM # Support for ANT is a future capability
REM **FUTURE** if not exist "%REL_ANT_HOME%"    mkdir "%REL_ANT_HOME%"

REM #---------------------------------------------
REM # Determine command type [ANT or DP]
REM #---------------------------------------------
REM # Determine if an execution property file extension is .dp or .xml
REM # Use -exec for .dp files and -ant for .xml files.  
REM #   When using -ant, a JDK must be defined by MY_JAVA_HOME in setMyPrePDToolVars.bat.
set REL_EXEC_CMD=
if "%REL_PLAN_FILE_EXT%"==".dp"  set REL_EXEC_CMD=-exec %REL_PLAN_HOME%\%REL_PLAN_FILE_NAME%
REM # Support for ANT is a future capability
REM **FUTURE** if "%REL_PLAN_FILE_EXT%"==".xml" set REL_EXEC_CMD=-ant %REL_ANT_HOME%\%REL_PLAN_FILE_NAME%
if "%REL_PLAN_FILE_EXT%"==".xml" (
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   echo.%REL_BATCH_FILE_NAME%:: Execution Failed: ANT Execution is not currently supported.
   echo.%REL_BATCH_FILE_NAME%::   Plan name=%REL_PLAN_FILE_NAME%
   echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
   set ERROR=1
   goto END
)


REM #---------------------------------------------
REM # Print out Environment Variables
REM #---------------------------------------------
REM #
echo.%REL_BATCH_FILE_NAME%:: --------------------------------------------
echo.%REL_BATCH_FILE_NAME%:: Input Prameters:
echo.%REL_BATCH_FILE_NAME%:: --------------------------------------------
echo.%REL_BATCH_FILE_NAME%::    %REL_INPUT_PARAMS%
echo.%REL_BATCH_FILE_NAME%::    P01: REL_DESCRIPTION=%REL_DESCRIPTION%
echo.%REL_BATCH_FILE_NAME%::    P02: REL_FORCE_WORKSPACE_INIT=%REL_FORCE_WORKSPACE_INIT%
echo.%REL_BATCH_FILE_NAME%::    P03: REL_VCS_TYPE=%REL_VCS_TYPE%
echo.%REL_BATCH_FILE_NAME%::    P04: REL_VCS_REPOSITORY_URL=%REL_VCS_REPOSITORY_URL%
echo.%REL_BATCH_FILE_NAME%::    P05: REL_VCS_PROJECT_ROOT=%REL_VCS_PROJECT_ROOT%
echo.%REL_BATCH_FILE_NAME%::    P06: REL_VCS_USERNAME=%REL_VCS_USERNAME%
echo.%REL_BATCH_FILE_NAME%::    P07: REL_VCS_PASSWORD=%PR_REL_VCS_PASSWORD%
echo.%REL_BATCH_FILE_NAME%::    P08: REL_RELEASE_FOLDER=%REL_RELEASE_FOLDER%
echo.%REL_BATCH_FILE_NAME%::    P09: REL_VCS_WORKSPACE_CONF_DIR=%REL_VCS_WORKSPACE_CONF_DIR%
echo.%REL_BATCH_FILE_NAME%::    P10: REL_VCS_WORKSPACE_SRC_DIR=%REL_VCS_WORKSPACE_SRC_DIR%
echo.%REL_BATCH_FILE_NAME%::    P11: REL_VCS_TEMP_DIR=%REL_VCS_TEMP_DIR%
echo.%REL_BATCH_FILE_NAME%::    P12: REL_CIS_USERNAME=%REL_CIS_USERNAME%
echo.%REL_BATCH_FILE_NAME%::    P13: REL_CIS_PASSWORD=%PR_REL_CIS_PASSWORD%
echo.%REL_BATCH_FILE_NAME%::    P14: REL_CIS_DOMAIN=%REL_CIS_DOMAIN%
echo.%REL_BATCH_FILE_NAME%::    P15: REL_ENV_CONFIG_FILE_NAME=%REL_ENV_CONFIG_FILE_NAME%
echo.%REL_BATCH_FILE_NAME%::    P16: REL_PLAN_FILE_NAME=%REL_PLAN_FILE_NAME%
echo.%REL_BATCH_FILE_NAME%::    P17: REL_NO_OPERATION=%REL_NO_OPERATION%
echo.%REL_BATCH_FILE_NAME%::    P18: REL_VALID_ENV_CONFIG_PAIRS=%REL_VALID_ENV_CONFIG_PAIRS%
echo.%REL_BATCH_FILE_NAME%:: --------------------------------------------
echo.%REL_BATCH_FILE_NAME%:: External Variables:
echo.%REL_BATCH_FILE_NAME%:: --------------------------------------------
echo.%REL_BATCH_FILE_NAME%::    PDTOOL_HOME=%PDTOOL_HOME%
echo.%REL_BATCH_FILE_NAME%::    REL_VCS_MODULE_NAME=%REL_VCS_MODULE_NAME%
echo.%REL_BATCH_FILE_NAME%::    REL_VCS_WORKSPACE_HOME=%REL_VCS_WORKSPACE_HOME%
echo.%REL_BATCH_FILE_NAME%::    REL_VCS_WORKSPACE_NAME=%REL_VCS_WORKSPACE_NAME%
echo.%REL_BATCH_FILE_NAME%::    REL_MODULE_HOME=%REL_MODULE_HOME%
echo.%REL_BATCH_FILE_NAME%::    REL_PLAN_HOME=%REL_PLAN_HOME%
echo.%REL_BATCH_FILE_NAME%::    REL_ANT_HOME=%REL_ANT_HOME%
echo.%REL_BATCH_FILE_NAME%:: --------------------------------------------
echo.%REL_BATCH_FILE_NAME%:: Local Variables:
echo.%REL_BATCH_FILE_NAME%:: --------------------------------------------
echo.%REL_BATCH_FILE_NAME%::    REL_VCS_INIT_PLAN_NAME=%REL_VCS_INIT_PLAN_NAME%
echo.%REL_BATCH_FILE_NAME%::    REL_PLAN_FILE_EXT=%REL_PLAN_FILE_EXT%
echo.%REL_BATCH_FILE_NAME%::    REL_INIT_WORKSPACE=%REL_INIT_WORKSPACE%
echo.%REL_BATCH_FILE_NAME%::    REL_CONFIG_CMD=%REL_CONFIG_CMD%
echo.%REL_BATCH_FILE_NAME%::    REL_VCS_PROJECT_ROOT=%REL_VCS_PROJECT_ROOT%
echo.%REL_BATCH_FILE_NAME%::    REL_DERIVED_VCS_PROJECT_ROOT=%REL_DERIVED_VCS_PROJECT_ROOT%
echo.%REL_BATCH_FILE_NAME%::
echo.%REL_BATCH_FILE_NAME%::

REM #---------------------------------------------
REM # Change directories to PDTool/bin
REM #---------------------------------------------
REM # Execute the Workspace initialization when the workspace folder does not exist
cd %PDTOOL_HOME%\bin

REM #---------------------------------------------
REM # Execute the TFS license acceptance
REM #---------------------------------------------
if "%REL_VCS_TYPE%" == "TFS" (
  echo.%REL_BATCH_FILE_NAME%:: --------------------------------------------
  echo.%REL_BATCH_FILE_NAME%:: Accept the TFS license:
  echo.%REL_BATCH_FILE_NAME%:: --------------------------------------------
  echo.%REL_BATCH_FILE_NAME%::   "%TFS_HOME%\tf.cmd" eula -accept
  if not exist "%TFS_HOME%\tf.cmd" (
     echo.%REL_BATCH_FILE_NAME%:: Execution Failed: Command not found "%TFS_HOME%\tf.cmd"
     goto END
  ) 
  call "%TFS_HOME%\tf.cmd" eula -accept
  set ERROR=%ERRORLEVEL%
  if %ERROR% GTR 0 (
     echo.%REL_BATCH_FILE_NAME%:: Execution Failed: Error detected executing tf.cmd" eula -accept
     goto END
  ) 
  echo.%REL_BATCH_FILE_NAME%::
  echo.%REL_BATCH_FILE_NAME%::
)

REM #---------------------------------------------
REM # Execute the Workspace initialization if the folder does not exist.
REM #    Initialization is required for each new release and each user
REM #    who initiates a deployment.
REM #---------------------------------------------
REM # Check to see if workspace already exists
echo.%REL_BATCH_FILE_NAME%:: --------------------------------------------
echo.%REL_BATCH_FILE_NAME%:: Checking workspace:
echo.%REL_BATCH_FILE_NAME%:: --------------------------------------------
echo.%REL_BATCH_FILE_NAME%::   REL_VCS_WORKSPACE_SRC_DIR=%REL_VCS_WORKSPACE_SRC_DIR%
echo.%REL_BATCH_FILE_NAME%::

REM # If the force workspace initialization flag is set then goto workspace initialization
if %REL_INIT_WORKSPACE%==1 (
	echo.%REL_BATCH_FILE_NAME%::   Force workspace initialization.
	echo.%REL_BATCH_FILE_NAME%::
	goto INITIALIZE_WORKSPACE
)

REM # Check to see if workspace already exists
if exist %REL_VCS_WORKSPACE_SRC_DIR% (
	echo.%REL_BATCH_FILE_NAME%::   Workspace has already been initalized.
	echo.%REL_BATCH_FILE_NAME%::
	goto CONTINUE_DEPLOYMENT2
)

REM #---------------------------------------------
REM # Initialize the workspace
REM #---------------------------------------------
:INITIALIZE_WORKSPACE
REM # Execute the Workspace initialization when the workspace folder does not exist
  if not exist %REL_VCS_WORKSPACE_SRC_DIR% echo.%REL_BATCH_FILE_NAME%::   Workspace does not exist.
  if exist %REL_VCS_WORKSPACE_SRC_DIR% echo.%REL_BATCH_FILE_NAME%::   Workspace exists.
  if not defined REL_VCS_MODULE_NAME (
     echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
     echo.%REL_BATCH_FILE_NAME%:: Execution Failed: Error - REL_VCS_MODULE_NAME must be defined.
     echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
	 goto END
  )
  echo.%REL_BATCH_FILE_NAME%::
  echo.%REL_BATCH_FILE_NAME%:: --------------------------------------------
  echo.%REL_BATCH_FILE_NAME%:: Initialize the Workspace.
  echo.%REL_BATCH_FILE_NAME%:: --------------------------------------------
  echo.%REL_BATCH_FILE_NAME%::
  echo.%REL_BATCH_FILE_NAME%::   call "%PDTOOL_HOME%\bin\ExecutePDTool.bat" -exec "%PDTOOL_HOME%\resources\plans\%REL_VCS_INIT_PLAN_NAME%" %REL_CONFIG_CMD% -release %REL_RELEASE_FOLDER% %REL_NO_OPERATION%
  echo.%REL_BATCH_FILE_NAME%::
  echo.########################################################################################################################################
  echo.
  call "%PDTOOL_HOME%\bin\ExecutePDTool.bat" -exec "%PDTOOL_HOME%\resources\plans\%REL_VCS_INIT_PLAN_NAME%" %REL_CONFIG_CMD% -release %REL_RELEASE_FOLDER% %REL_NO_OPERATION%
  set ERROR=%ERRORLEVEL%
  echo.########################################################################################################################################
  if %ERROR% GTR 0 (
     echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
     echo.%REL_BATCH_FILE_NAME%:: Execution Failed: Error detected during VCS Workspace Initialization.
     echo.%REL_BATCH_FILE_NAME%::------------------------------------------------------------------------------
     goto END
  )

:CONTINUE_DEPLOYMENT2
echo.%REL_BATCH_FILE_NAME%::
echo.%REL_BATCH_FILE_NAME%::

REM #---------------------------------------------
REM # Execute the Deployment Plan
REM #---------------------------------------------
echo.%REL_BATCH_FILE_NAME%:: --------------------------------------------
echo.%REL_BATCH_FILE_NAME%:: Execute the PDTool Deployment Plan
echo.%REL_BATCH_FILE_NAME%:: --------------------------------------------
echo.%REL_BATCH_FILE_NAME%::
if "%REL_NO_OPERATION%" == "-noop" (
   echo.%REL_BATCH_FILE_NAME%::   *************************************************
   echo.%REL_BATCH_FILE_NAME%::   ** PREVIEW MODE ONLY.  NO IMPORT WILL BE DONE. **
   echo.%REL_BATCH_FILE_NAME%::   *************************************************
   echo.%REL_BATCH_FILE_NAME%::
)
if "%REL_NO_OPERATION%" == "" (
   echo.%REL_BATCH_FILE_NAME%::   ******************************************************
   echo.%REL_BATCH_FILE_NAME%::   ** IMPORT MODE ACTIVE.  RESOURCES WILL BE IMPORTED. **
   echo.%REL_BATCH_FILE_NAME%::   ******************************************************
   echo.%REL_BATCH_FILE_NAME%::
)
echo.%REL_BATCH_FILE_NAME%::   call "%PDTOOL_HOME%\bin\ExecutePDTool.bat" %REL_EXEC_CMD% %REL_CONFIG_CMD% %1 %2 %3 %4 %5 %6 %7 %8 %9 -release %REL_RELEASE_FOLDER% %REL_NO_OPERATION%
echo.%REL_BATCH_FILE_NAME%::
echo.########################################################################################################################################
echo.
call "%PDTOOL_HOME%\bin\ExecutePDTool.bat" %REL_EXEC_CMD% %REL_CONFIG_CMD% %1 %2 %3 %4 %5 %6 %7 %8 %9 -release %REL_RELEASE_FOLDER% %REL_NO_OPERATION%
set ERROR=%ERRORLEVEL%
echo.########################################################################################################################################
echo.%REL_BATCH_FILE_NAME%::
echo.%REL_BATCH_FILE_NAME%::
if %ERROR% GTR 0 (
   echo.%REL_BATCH_FILE_NAME%:: Execution Failed: Error detected during VCS Deployment
   goto END
)
if %ERROR% EQU 0 (
   echo.%REL_BATCH_FILE_NAME%:: Successfully completed release deployment.
   goto END
)
goto END


REM #---------------------------------------------
REM # Functions
REM #---------------------------------------------

REM #--------------------------------------------------------------------
:strLen
REM #--------------------------------------------------------------------
REM # Returns the length of a string
REM # Description: call:strLen string_variable len_variable  
REM #  -- string_variable [in]  - variable name containing the string being measured for length
REM #  -- len_variable    [out] - variable to be used to return the string length
REM #####################################################################
(   SETLOCAL ENABLEDELAYEDEXPANSION
    set "str=A!%~1!"&rem keep the A up front to ensure we get the length and not the upper bound
                     rem it also avoids trouble in case of empty string
    set "len=0"
    for /L %%A in (12,-1,0) do (
        set /a "len|=1<<%%A"
        for %%B in (!len!) do if "!str:~%%B,1!"=="" set /a "len&=~1<<%%A"
    )
)
( ENDLOCAL & REM RETURN VALUES
    IF "%~2" NEQ "" SET /a %~2=%len%
)
GOTO:EOF


REM #-------------------------------------------------------------
:REMOVE_LAST_SEPARATOR
REM #-------------------------------------------------------------
REM # Remove ending / or \ for VCS_REPOSITORY_URL
REM # Description: call:REMOVE_LAST_SEPARATOR instr sep outvariable  
REM #      -- instr       [in]  - the string value to be evaluated for an ending separator
REM #      -- sep         [in]  - the separator value such as / or \
REM #      -- outvariable [out] - variable name containing the result string
set instr=%1
set sep=%2
SETLOCAL ENABLEDELAYEDEXPANSION
REM Remove double quotes around path
set instr=!instr:"=!
set sep=!sep:"=!
ENDLOCAL &set instr=%instr%&set sep=%sep%
IF NOT DEFINED sep set sep=/
IF NOT DEFINED instr GOTO REMOVE_LAST_SEPARATOR_END

  call:strLen instr LEN
  SET _start=%LEN%
  SET /a _start=_start-1
  SET _len=1
  CALL SET LAST_CHAR=%%instr:~%_start%,%_len%%%
  SET _beg=0
  SET _len=%_start%
  IF "%LAST_CHAR%"=="%sep%" CALL SET instr=%%instr:~%_beg%,%_len%%%
  
:REMOVE_LAST_SEPARATOR_END
  SET %3=%instr%
GOTO:EOF


REM #----------------------------------------------------------
:replace
REM #----------------------------------------------------------
REM # replace - parses a string and replaces old string with new string
REM #           and returns the value in the outvariable that gets passed in
REM # syntax:  call :replace "oldstring" "newstring" "searchstring" outvariable
REM # example: call :replace "_" "__" "%searchStr%" outStr 
REM #    oldstring    [in] - string to be replaced
REM #    newstring    [in] - string to replace with
REM #    searchstring [in] - String to search
REM #    outvariable [out] - name of the variable to place the results
REM #
REM # Remove double quotes (") for incoming SearchStr argument
REM #####################################################################
set oldStr=%1
set newStr=%2
set searchStr=%3
SETLOCAL EnableDelayedExpansion
set oldStr=!oldStr:"=!
set newStr=!newStr:"=!
set searchStr=!searchStr:"=!

if "%debug%"=="1" echo.[DEBUG] replace:    oldStr=!oldStr!
if "%debug%"=="1" echo.[DEBUG] replace:    newStr=!newStr!
if "%debug%"=="1" echo.[DEBUG] replace: searchStr=!searchStr!
REM # Replace old string with new string within search string
if "!oldStr!"=="" findstr "^::" "%~f0"&GOTO:EOF
   for /f "tokens=1,* delims=]" %%A in ('"echo.%searchStr%|find /n /v """') do (
   set "line=%%B"
   if "%debug%"=="1" echo.[DEBUG] replace:      line=!line!
   if defined line (
	  set "outvar=!oldStr:%searchStr%=%newStr%!"
   ) ELSE (
      call set "outvar="
   )
)
ENDLOCAL& set outvar=%outvar%
set %4=%outvar%
GOTO:EOF


:END
REM #---------------------------------------------
REM # Initalize all variables
REM #---------------------------------------------
REM # Initalize input variables
set REL_DESCRIPTION=
set REL_FORCE_WORKSPACE_INIT=
set REL_VCS_TYPE=
set REL_VCS_REPOSITORY_URL=
set REL_VCS_PROJECT_ROOT=
set REL_VCS_USERNAME=
set REL_VCS_PASSWORD=
set REL_RELEASE_FOLDER=
set REL_VCS_WORKSPACE_CONF_DIR=
set REL_VCS_WORKSPACE_SRC_DIR=
set REL_VCS_TEMP_DIR=
set REL_CIS_USERNAME=
set REL_CIS_PASSWORD=
set REL_CIS_DOMAIN=
set REL_ENV_CONFIG_FILE_NAME=
set REL_PLAN_FILE_NAME=
set REL_NO_OPERATION=
set REL_VALID_ENV_CONFIG_PAIRS=
REM # Initalize other local variables
set REL_VCS_INIT_PLAN_NAME=
set REL_VCS_MODULE_NAME=
set REL_VCS_WORKSPACE_HOME=
set REL_VCS_WORKSPACE_NAME=
set REL_DERIVED_VCS_PROJECT_ROOT=
set REL_MODULE_HOME=
set REL_PLAN_HOME=
set REL_ANT_HOME=
set REL_PLAN_FILE_EXT=
set REL_INIT_WORKSPACE=
set REL_CONFIG_CMD=
set REL_BATCH_FILE_NAME=
set REL_BATCH_FILE_EXT=
set REL_EXEC_CMD=

REM # ERROR is initialized to 0 at the beginning otherwise it is an error when not 0
if %ERROR% GTR 0 (
   ENDLOCAL
   exit /B 1
)
ENDLOCAL
exit /B 0
