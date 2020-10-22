#!/bin/bash
############################################################################################################################
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
############################################################################################################################
#
#==========================================================
# FUNCTIONS
#==========================================================
#---------------------------------------------
# Return a printable password. 
# If encrypted print as is. 
# If not encryped return ******** for printing.
#---------------------------------------------
printablePassword() {
    pswd="$1"
    if [ "$pswd" != "" ]; then
        if [[ "$pswdSubstr" == *"Encrypted:"* ]]; then
            echo "$pswd"
        else
            echo "********"
        fi
    else
        echo ""
    fi
}
#==========================================================
# END FUNCTIONS
#==========================================================
SV="setMyPrePDToolVars.sh"
PRINT_VARS="1"
#
#==========================================================
# setMyPrePDToolVars.sh :: Set Environment Variables
#==========================================================
# This file gets invoked by setVars.sh before setVars.sh variables have been executed.
#
# Instructions: 
#    1. Modify variables as needed.
#    2. Add new variables to the function :writeOutput at the bottom of this batch file when new variables are added.
#    3. Copy this file to a location outside of the PDTool installation so that it is not overwritten during upgrade.
#    4. Modify setVars.sh "MY_VARS_HOME" variable to point to the directory that contains this file.
#    5. To encrypt the password in this file:
#       a) Open a windows command line
#       b) cd <path-to-pdtool>/PDTool8.3.0/bin
#       c) ExecutePDTool.sh -encrypt <path-to-file>/setMyPrePDToolVars.sh
#=======================================================================================================
# CREATE/MODIFY CUSTOM PRE-PROCESSING VARIABLES BELOW THIS POINT
#=======================================================================================================
#
################################
# GENERAL GROUP ENVIRONMENT
################################
# 0=Do not print this section, 1 or true=Print this section
GEN_PRINT="1"
# My Java Home
export MY_JAVA_HOME="/opt/TIBCO/TDV8.3/tdv1/jdk"
# PDTool Substitute Drive Letter used for making the path shorter
export PDTOOL_SUBSTITUTE_DRIVE=""
# PDTool Installation Home Directory
export PDTOOL_INSTALL_HOME="/opt/TIBCO/PDTool8.3.0_GIT"
# PDTool Home directory
export PDTOOL_HOME="/opt/TIBCO/PDTool8.3.0_GIT/PDTool"
# Name of the configuration property file located in PDTool/resources/config
#    e.g. Default=deploy.properties or SVN=deploy_SVN.properties or TFS=deploy_TFS.properties
export MY_CONFIG_PROPERTY_FILE="deploy_GIT_UAT1.properties"
# The editor to use for VCS viewing if needed.  Windows=notepad and UNIX=vi
export VCS_EDITOR="vi"
#
# NOVCS ENVIRONMENT~CONFIG PROPERTY PAIRS
# These pairs provide the ability to use a short environment name in place of the configuration property name when instructing PDTool on which environment to deploy to.
#    Format: [vcs-type-char][env-type]~[config-name]   Example: NDEV~deploy_NOVCS_DEV,NUAT~deploy_NOVCS_UAT,NPROD~deploy_NOVCS_PROD
#            Comma separated, no space and no double quotes.  Tilde separates pairs: ENV_TYPE~ConfigFileName. The .properties extension is not included and gets added automatically.
#            Format of pairs: XDEV=deploy_DEV,XUAT=deploy_UAT,XPROD=deploy_PROD
#               vcs-type-char = represents the first characters of the VCS environment. N=NOVCS,S=SVN,T=TFS,G=GIT,P=P4
#                    env-type = represents the CIS environment short name deploying to.  Examples: DEV, ATA, ADA, CIT, SIT, UAT, TT, PROD, DR
#               The combination of [vcs-type-char][env-type] makes it unique across all different env-config pair types.
#    Configuration property file names found in PDTool\resources\config folder.
#    It is up to the PDTool installer to modify these pairs to match your environment.
export NOVCS_VALID_ENV_CONFIG_PAIRS=""
#
################################
# CUSTOM USER DEFINED VARIABLES
################################
# if [ -f "$PDTOOL_HOME/bin/setCustomVars.sh" ]; then . ./$PDTOOL_HOME/bin/setCustomVars.sh; fi;
#
################################
# DV SERVER VARIABLES
################################
# 0=Do not print this section, 1 or true=Print this section
CIS_PRINT="1"
# DV Server Username
export CIS_USERNAME="admin"
# DV Server Password
export CIS_PASSWORD=""
# DV Server Domain
export CIS_DOMAIN="composite"
# Check to see if the ExecutePDToolRelease has set the CIS REL_ variables
# This allows the same CIS_ variables to be used in the servers.xml file.
if [ "$REL_CIS_USERNAME" != "" ]; then export CIS_USERNAME="$REL_CIS_USERNAME"; fi;
if [ "$REL_CIS_PASSWORD" != "" ]; then export CIS_PASSWORD="$REL_CIS_PASSWORD"; fi;
if [ "$REL_CIS_DOMAIN" != "" ]; then   export CIS_DOMAIN="$REL_CIS_DOMAIN"; fi;
#
################################
# GIT VARIABLES
################################
# 0=Do not print this section, 1 or true=Print this section
GIT_PRINT="0"
if [ "$REL_VCS_TYPE" == "GIT" ]; then export GIT_PRINT="1"; fi;
# The GIT Home folder where VCS client exists
export GIT_HOME=""
# The Git repository path at trunk or any folder designation within trunk.  Use 4 forward slashes in the URL https:////url/repo.git
export GIT_VCS_REPOSITORY_URL=""
# The Git folder path starting directly after the Git repo URL and ending where the DV base level root folders start
export GIT_VCS_PROJECT_ROOT="dv_objects"
# Git user name
export GIT_VCS_USERNAME=""
# Git user password.  See notes at top of this file to encrypt.
export GIT_VCS_PASSWORD=""
# Set the workspace home if applicable.  This variable points to the file system location parent folder to VCS_WORKSPACE_NAME
# e.g. If the workspace is in the PDTOOL_HOME then use the shortest path: $PDTOOL_SUBSTITUTE_HOME
export GIT_VCS_WORKSPACE_HOME="${PDTOOL_HOME}"
# Set the VCS Workspace name.  e.g. GITww7
export GIT_VCS_WORKSPACE_NAME="GITww7"
# Set the VCS TEMP directory by adding a "t" to the end of the concatenated workspace home and workspace name
# e.g. ${GIT_VCS_WORKSPACE_HOME}/${GIT_VCS_WORKSPACE_NAME}t
export GIT_VCS_TEMP_DIR="${GIT_VCS_WORKSPACE_HOME}/${GIT_VCS_WORKSPACE_NAME}t"
#
# GIT ENVIRONMENT-CONFIG PROPERTY PAIRS
# These pairs provide the ability to use a short environment name in place of the configuration property name when instructing PDTool on which environment to deploy to.
#    Format: [vcs-type-char][env-type]~[config-name]   Example: GDEV~deploy_GIT_DEV,GUAT~deploy_GIT_UAT,GPROD~deploy_GIT_PROD
#            Comma separated, no space and no double quotes.  Tilde separates pairs: ENV_TYPE~ConfigFileName. The .properties extension is not included and gets added automatically.
#            Format of pairs: XDEV=deploy_DEV,XUAT=deploy_UAT,XPROD=deploy_PROD
#               vcs-type-char = represents the first characters of the VCS environment. N=NOVCS,S=SVN,T=TFS,G=GIT,P=P4
#                    env-type = represents the CIS environment short name deploying to.  Examples: DEV, ATA, ADA, CIT, SIT, UAT, TT, PROD, DR
#               The combination of [vcs-type-char][env-type] makes it unique across all different env-config pair types.
#    Configuration property file names found in PDTool\resources\config folder.
#    It is up to the PDTool installer to modify these pairs to match your environment.
export GIT_VALID_ENV_CONFIG_PAIRS="GDEV~deploy_GIT_DEV1,GUAT~deploy_GIT_UAT1,GPROD~deploy_GIT_PROD1"
#
# ExecutePDToolRelease.sh OVERRIDE SECTION
#
# Check the release VCS repository URL is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_REPOSITORY_URL" != "" ]; then     export GIT_VCS_REPOSITORY_URL="$REL_VCS_REPOSITORY_URL"; fi;
# Check the release VCS project root is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_PROJECT_ROOT" != "" ]; then       export GIT_VCS_PROJECT_ROOT="$REL_VCS_PROJECT_ROOT"; fi;
# Check the release VCS username is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_USERNAME" != "" ]; then           export GIT_VCS_USERNAME="$REL_VCS_USERNAME"; fi;
# Check the release VCS user password is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_PASSWORD " != "" ]; then          export GIT_VCS_PASSWORD="$REL_VCS_PASSWORD"; fi;
# Check the release VCS workspace home is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_WORKSPACE_HOME" != "" ]; then     export GIT_VCS_WORKSPACE_HOME="$REL_VCS_WORKSPACE_HOME"; fi;
# Check the release VCS workspace name is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_WORKSPACE_NAME" != "" ]; then     export GIT_VCS_WORKSPACE_NAME="$REL_VCS_WORKSPACE_NAME"; fi;
# Check the release VCS temporary directory is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_TEMP_DIR" != "" ]; then           export GIT_VCS_TEMP_DIR="$REL_VCS_TEMP_DIR"; fi;
# Check the valid environment configuration pairs is set from ExecutePDToolRelease.sh
if [ "$REL_VALID_ENV_CONFIG_PAIRS" != "" ]; then export GIT_VALID_ENV_CONFIG_PAIRS="$REL_VALID_ENV_CONFIG_PAIRS"; fi;
#
################################
# SVN VARIABLES
################################
# 0=Do not print this section, 1 or true=Print this section
SVN_PRINT="0"
if [ "$REL_VCS_TYPE" == "SVN" ]; then export SVN_PRINT="1"; fi;
# The SVN Home folder where VCS client exists
export SVN_HOME=""
# The subversion repository path at trunk or any folder designation within trunk.  Use 4 forward slashes in the URL https:////url/repo
export SVN_VCS_REPOSITORY_URL=""
# The Subversion folder path starting directly after the Subversion repo URL and ending where the DV base level root folders start
export SVN_VCS_PROJECT_ROOT="dv_objects"
# Subversion user name
export SVN_VCS_USERNAME=""
# Subversion user password.  See notes at top of this file to encrypt.
export SVN_VCS_PASSWORD=""
# Set the workspace home if applicable.  This variable points to the file system location parent folder to VCS_WORKSPACE_NAME
# e.g. If the workspace is in the PDTOOL_HOME then use the shortest path: $PDTOOL_SUBSTITUTE_HOME
export SVN_VCS_WORKSPACE_HOME="${PDTOOL_HOME}"
# Set the VCS Workspace name.  e.g. SVNww7
export SVN_VCS_WORKSPACE_NAME="SVNww7"
# Set the VCS TEMP directory by adding a "t" to the end of the concatenated workspace home and workspace name
# e.g. ${SVN_VCS_WORKSPACE_HOME}/${SVN_VCS_WORKSPACE_NAME}t
export SVN_VCS_TEMP_DIR="${SVN_VCS_WORKSPACE_HOME}/${SVN_VCS_WORKSPACE_NAME}t"
#
# SVN ENVIRONMENT-CONFIG PROPERTY PAIRS
# These pairs provide the ability to use a short environment name in place of the configuration property name when instructing PDTool on which environment to deploy to.
#    Format: [vcs-type-char][env-type]~[config-name]   Example: SDEV~deploy_SVN_DEV,SUAT~deploy_SVN_UAT,SPROD~deploy_SVN_PROD
#            Comma separated, no space and no double quotes.  Tilde separates pairs: ENV_TYPE~ConfigFileName. The .properties extension is not included and gets added automatically.
#            Format of pairs: XDEV=deploy_DEV,XUAT=deploy_UAT,XPROD=deploy_PROD
#               vcs-type-char = represents the first characters of the VCS environment. N=NOVCS,S=SVN,T=TFS,G=GIT,P=P4
#                    env-type = represents the CIS environment short name deploying to.  Examples: DEV, ATA, ADA, CIT, SIT, UAT, TT, PROD, DR
#               The combination of [vcs-type-char][env-type] makes it unique across all different env-config pair types.
#    Configuration property file names found in PDTool\resources\config folder.
#    It is up to the PDTool installer to modify these pairs to match your environment.
export SVN_VALID_ENV_CONFIG_PAIRS=""
#
# ExecutePDToolRelease.sh OVERRIDE SECTION
#
# Check the release VCS repository URL is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_REPOSITORY_URL" != "" ]; then     export SVN_VCS_REPOSITORY_URL="$REL_VCS_REPOSITORY_URL"; fi;
# Check the release VCS project root is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_PROJECT_ROOT" != "" ]; then       export SVN_VCS_PROJECT_ROOT="$REL_VCS_PROJECT_ROOT"; fi;
# Check the release VCS username is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_USERNAME" != "" ]; then           export SVN_VCS_USERNAME="$REL_VCS_USERNAME"; fi;
# Check the release VCS user password is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_PASSWORD" != "" ]; then           export SVN_VCS_PASSWORD="$REL_VCS_PASSWORD"; fi;
# Check the release VCS workspace home is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_WORKSPACE_HOME" != "" ]; then     export SVN_VCS_WORKSPACE_HOME="$REL_VCS_WORKSPACE_HOME"; fi;
# Check the release VCS workspace name is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_WORKSPACE_NAME" != "" ]; then     export SVN_VCS_WORKSPACE_NAME="$REL_VCS_WORKSPACE_NAME"; fi;
# Check the release VCS temporary directory is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_TEMP_DIR" != "" ]; then           export SVN_VCS_TEMP_DIR="$REL_VCS_TEMP_DIR"; fi;
# Check the valid environment configuration pairs is set from ExecutePDToolRelease.sh
if [ "$REL_VALID_ENV_CONFIG_PAIRS" != "" ]; then export SVN_VALID_ENV_CONFIG_PAIRS="$REL_VALID_ENV_CONFIG_PAIRS"; fi;
#
################################
# TFS VARIABLES
################################
# 0=Do not print this section, 1 or true=Print this section
TFS_PRINT="0"
if [ "$REL_VCS_TYPE" == "TFS" ]; then export TFS_PRINT="1"; fi;
# The TFS Home folder where VCS client exists
export TFS_HOME=""
# The TFS repository URL pointing to the repository's collection.  Use 4 forward slashes in the URL https:////url/repo
export TFS_VCS_REPOSITORY_URL=""
# The TFS folder path starting at the TFS project and ending where the DV base level root folders start
export TFS_VCS_PROJECT_ROOT="dv_objects"
# TFS user name including the domain.  If LDAP it may need to include the domain user@domain.
export TFS_VCS_USERNAME=""
# TFS user password.  See notes at top of this file to encrypt.
export TFS_VCS_PASSWORD=""
# Set the workspace home if applicable.  This variable points to the file system location parent folder to VCS_WORKSPACE_NAME
# e.g. If the workspace is in the PDTOOL_HOME then use the shortest path: $PDTOOL_SUBSTITUTE_HOME
export TFS_VCS_WORKSPACE_HOME="${PDTOOL_HOME}"
# Set the VCS Workspace name.  e.g. TFSww7
export TFS_VCS_WORKSPACE_NAME="TFSww7"
# Set the VCS TEMP directory by adding a "t" to the end of the concatenated workspace home and workspace name
# e.g. ${TFS_VCS_WORKSPACE_HOME}/${TFS_VCS_WORKSPACE_NAME}t
export TFS_VCS_TEMP_DIR="${TFS_VCS_WORKSPACE_HOME}/${TFS_VCS_WORKSPACE_NAME}t"
#
# TFS ENVIRONMENT-CONFIG PROPERTY PAIRS
# These pairs provide the ability to use a short environment name in place of the configuration property name when instructing PDTool on which environment to deploy to.
#    Format: [vcs-type-char][env-type]~[config-name]   Example: TDEV~deploy_TFS_DEV,TUAT~deploy_TFS_UAT,TPROD~deploy_TFS_PROD
#            Comma separated, no space and no double quotes.  Tilde separates pairs: ENV_TYPE~ConfigFileName. The .properties extension is not included and gets added automatically.
#            Format of pairs: XDEV=deploy_DEV,XUAT=deploy_UAT,XPROD=deploy_PROD
#               vcs-type-char = represents the first characters of the VCS environment. N=NOVCS,S=SVN,T=TFS,G=GIT,P=P4
#                    env-type = represents the CIS environment short name deploying to.  Examples: DEV, ATA, ADA, CIT, SIT, UAT, TT, PROD, DR
#               The combination of [vcs-type-char][env-type] makes it unique across all different env-config pair types.
#    Configuration property file names found in PDTool\resources\config folder.
#    It is up to the PDTool installer to modify these pairs to match your environment.
export TFS_VALID_ENV_CONFIG_PAIRS=""
#
# ExecutePDToolRelease.sh OVERRIDE SECTION
#
# Check the release VCS repository URL is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_REPOSITORY_URL" != "" ]; then     export TFS_VCS_REPOSITORY_URL="$REL_VCS_REPOSITORY_URL"; fi;
# Check the release VCS project root is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_PROJECT_ROOT" != "" ]; then       export TFS_VCS_PROJECT_ROOT="$REL_VCS_PROJECT_ROOT"; fi;
# Check the release VCS username is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_USERNAME" != "" ]; then           export TFS_VCS_USERNAME="$REL_VCS_USERNAME"; fi;
# Check the release VCS user password is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_PASSWORD" != "" ]; then           export TFS_VCS_PASSWORD="$REL_VCS_PASSWORD"; fi;
# Check the release VCS workspace home is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_WORKSPACE_HOME" != "" ]; then     export TFS_VCS_WORKSPACE_HOME="$REL_VCS_WORKSPACE_HOME"; fi;
# Check the release VCS workspace name is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_WORKSPACE_NAME" != "" ]; then     export TFS_VCS_WORKSPACE_NAME="$REL_VCS_WORKSPACE_NAME"; fi;
# Check the release VCS temporary directory is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_TEMP_DIR" != "" ]; then           export TFS_VCS_TEMP_DIR="$REL_VCS_TEMP_DIR"; fi;
# Check the valid environment configuration pairs is set from ExecutePDToolRelease.sh
if [ "$REL_VALID_ENV_CONFIG_PAIRS" != "" ]; then export TFS_VALID_ENV_CONFIG_PAIRS="$REL_VALID_ENV_CONFIG_PAIRS"; fi;
#
################################
# P4 VARIABLES
################################
# 0=Do not print this section, 1 or true=Print this section
P4_PRINT="0"
if [ "$REL_VCS_TYPE" == "P4" ]; then export P4_PRINT="1"; fi;
# The Perforce Home folder where VCS client exists
export P4_HOME=""
# The subversion repository path at trunk or any folder designation within trunk
export P4_VCS_REPOSITORY_URL=""
# The Subversion folder path starting directly after the Subversion repo URL and ending where the DV base level root folders start
export P4_VCS_PROJECT_ROOT="dv_objects"
# Subversion user name
export P4_VCS_USERNAME=""
# Subversion user password.  See notes at top of this file to encrypt.
export P4_VCS_PASSWORD=""
# Set the workspace home if applicable.  This variable points to the file system location parent folder to VCS_WORKSPACE_NAME
# e.g. If the workspace is in the PDTOOL_HOME then use the shortest path: $PDTOOL_SUBSTITUTE_HOME
export P4_VCS_WORKSPACE_HOME="${PDTOOL_HOME}"
# Set the VCS Workspace name.  e.g. P4ww7
export P4_VCS_WORKSPACE_NAME="P4ww7"
# Set the VCS TEMP directory by adding a "t" to the end of the concatenated workspace home and workspace name
# e.g. ${P4_VCS_WORKSPACE_HOME}/${P4_VCS_WORKSPACE_NAME}t
export P4_VCS_TEMP_DIR="${P4_VCS_WORKSPACE_HOME}/${P4_VCS_WORKSPACE_NAME}t"
#
# P4 ENVIRONMENT-CONFIG PROPERTY PAIRS
# These pairs provide the ability to use a short environment name in place of the configuration property name when instructing PDTool on which environment to deploy to.
#    Format: [vcs-type-char][env-type]~[config-name]   Example: PDEV~deploy_P4_DEV,PUAT~deploy_P4_UAT,PPROD~deploy_P4_PROD
#            Comma separated, no space and no double quotes.  Tilde separates pairs: ENV_TYPE~ConfigFileName. The .properties extension is not included and gets added automatically.
#            Format of pairs: XDEV=deploy_DEV,XUAT=deploy_UAT,XPROD=deploy_PROD
#               vcs-type-char = represents the first characters of the VCS environment. N=NOVCS,S=SVN,T=TFS,G=GIT,P=P4
#                    env-type = represents the CIS environment short name deploying to.  Examples: DEV, ATA, ADA, CIT, SIT, UAT, TT, PROD, DR
#               The combination of [vcs-type-char][env-type] makes it unique across all different env-config pair types.
#    Configuration property file names found in PDTool\resources\config folder.
#    It is up to the PDTool installer to modify these pairs to match your environment.
export P4_VALID_ENV_CONFIG_PAIRS=""
#
# ExecutePDToolRelease.sh OVERRIDE SECTION
#
# Check the release VCS repository URL is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_REPOSITORY_URL" != "" ]; then     export P4_VCS_REPOSITORY_URL="$REL_VCS_REPOSITORY_URL"; fi;
# Check the release VCS project root is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_PROJECT_ROOT" != "" ]; then       export P4_VCS_PROJECT_ROOT="$REL_VCS_PROJECT_ROOT"; fi;
# Check the release VCS username is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_USERNAME" != "" ]; then           export P4_VCS_USERNAME="$REL_VCS_USERNAME"; fi;
# Check the release VCS user password is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_PASSWORD" != "" ]; then           export P4_VCS_PASSWORD="$REL_VCS_PASSWORD"; fi;
# Check the release VCS workspace home is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_WORKSPACE_HOME" != "" ]; then     export P4_VCS_WORKSPACE_HOME="$REL_VCS_WORKSPACE_HOME"; fi;
# Check the release VCS workspace name is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_WORKSPACE_NAME" != "" ]; then     export P4_VCS_WORKSPACE_NAME="$REL_VCS_WORKSPACE_NAME"; fi;
# Check the release VCS temporary directory is set from ExecutePDToolRelease.sh
if [ "$REL_VCS_TEMP_DIR" != "" ]; then           export P4_VCS_TEMP_DIR="$REL_VCS_TEMP_DIR"; fi;
# Check the valid environment configuration pairs is set from ExecutePDToolRelease.sh
if [ "$REL_VALID_ENV_CONFIG_PAIRS" != "" ]; then export P4_VALID_ENV_CONFIG_PAIRS="$REL_VALID_ENV_CONFIG_PAIRS"; fi;
#
#=======================================================================================================
# CREATE/MODIFY VARIABLES ABOVE THIS POINT
#=======================================================================================================
# Print out the variables
if [ "$PRINT_VARS" == "1" ]; then 
    MSG=""
    PR_PASSWORD=""
    # Convert true to 1
    if [ "$GEN_PRINT" == "true" ]; then GEN_PRINT="1"; fi;
    if [ "$CIS_PRINT" == "true" ]; then CIS_PRINT="1"; fi;
    if [ "$GIT_PRINT" == "true" ]; then GIT_PRINT="1"; fi;
    if [ "$TFS_PRINT" == "true" ]; then TFS_PRINT="1"; fi;
    if [ "$SVN_PRINT" == "true" ]; then SVN_PRINT="1"; fi;
    if [ "$P4_PRINT" == "true" ]; then P4_PRINT="1"; fi;
    MSG="${MSG}########################################################################################################################################\n"
    MSG="${MSG}${SV}: Setting pre-processing custom variables\n"
    MSG="${MSG}########################################################################################################################################\n"
    if [ "$GEN_PRINT" == "1" ]; then
        MSG="${MSG}------------------\n"
        MSG="${MSG}GENERAL VARIABLES\n"
        MSG="${MSG}------------------\n"
        MSG="${MSG}MY_JAVA_HOME                =${MY_JAVA_HOME}\n"
        MSG="${MSG}PDTOOL_SUBSTITUTE_DRIVE     =${PDTOOL_SUBSTITUTE_DRIVE}\n"
        MSG="${MSG}PDTOOL_INSTALL_HOME         =${PDTOOL_INSTALL_HOME}\n"
        MSG="${MSG}PDTOOL_HOME                 =${PDTOOL_HOME}\n"
        MSG="${MSG}MY_CONFIG_PROPERTY_FILE     =${MY_CONFIG_PROPERTY_FILE}\n"
        MSG="${MSG}VCS_EDITOR                  =${VCS_EDITOR}\n"
        MSG="${MSG}NOVCS_VALID_ENV_CONFIG_PAIRS=${NOVCS_VALID_ENV_CONFIG_PAIRS}\n"
    fi
    if [ "$CIS_PRINT" == "1" ]; then
        MSG="${MSG}------------------\n"
        MSG="${MSG}CIS VARIABLES\n"
        MSG="${MSG}------------------\n"
        MSG="${MSG}CIS_USERNAME                =${CIS_USERNAME}\n"
        PR_PASSWORD=$(printablePassword "$CIS_PASSWORD")
        MSG="${MSG}CIS_PASSWORD                =${PR_PASSWORD}\n"
        MSG="${MSG}CIS_DOMAIN                  =${CIS_DOMAIN}\n"
    fi
    if [ "$TFS_PRINT" == "1" ]; then
        MSG="${MSG}------------------\n"
        MSG="${MSG}TFS VARIABLES\n"
        MSG="${MSG}------------------\n"
        MSG="${MSG}TFS_HOME                    =${TFS_HOME}\n"
        MSG="${MSG}TFS_VCS_REPOSITORY_URL      =${TFS_VCS_REPOSITORY_URL}\n"
        MSG="${MSG}TFS_VCS_PROJECT_ROOT        =${TFS_VCS_PROJECT_ROOT}\n"
        MSG="${MSG}TFS_VCS_USERNAME            =${TFS_VCS_USERNAME}\n"
        PR_PASSWORD=$(printablePassword "$TFS_VCS_PASSWORD")
        MSG="${MSG}TFS_VCS_PASSWORD            =${PR_PASSWORD}\n"
        MSG="${MSG}TFS_VCS_WORKSPACE_HOME      =${TFS_VCS_WORKSPACE_HOME}\n"
        MSG="${MSG}TFS_VCS_WORKSPACE_NAME      =${TFS_VCS_WORKSPACE_NAME}\n"
        MSG="${MSG}TFS_VCS_TEMP_DIR            =${TFS_VCS_TEMP_DIR}\n"
        MSG="${MSG}TFS_VALID_ENV_CONFIG_PAIRS  =${TFS_VALID_ENV_CONFIG_PAIRS}\n"
    fi
    if [ "$SVN_PRINT" == "1" ]; then
        MSG="${MSG}------------------\n"
        MSG="${MSG}SVN VARIABLES\n"
        MSG="${MSG}------------------\n"
        MSG="${MSG}SVN_HOME                    =${SVN_HOME}\n"
        MSG="${MSG}SVN_VCS_REPOSITORY_URL      =${SVN_VCS_REPOSITORY_URL}\n"
        MSG="${MSG}SVN_VCS_PROJECT_ROOT        =${SVN_VCS_PROJECT_ROOT}\n"
        MSG="${MSG}SVN_VCS_USERNAME            =${SVN_VCS_USERNAME}\n"
        PR_PASSWORD=$(printablePassword "$SVN_VCS_PASSWORD")
        MSG="${MSG}SVN_VCS_PASSWORD            =${PR_PASSWORD}\n"
        MSG="${MSG}SVN_VCS_WORKSPACE_HOME      =${SVN_VCS_WORKSPACE_HOME}\n"
        MSG="${MSG}SVN_VCS_WORKSPACE_NAME      =${SVN_VCS_WORKSPACE_NAME}\n"
        MSG="${MSG}SVN_VCS_TEMP_DIR            =${SVN_VCS_TEMP_DIR}\n"
        MSG="${MSG}SVN_VALID_ENV_CONFIG_PAIRS  =${SVN_VALID_ENV_CONFIG_PAIRS}\n"
    fi
    if [ "$GIT_PRINT" == "1" ]; then
        MSG="${MSG}------------------\n"
        MSG="${MSG}GIT VARIABLES\n"
        MSG="${MSG}------------------\n"
        MSG="${MSG}GIT_HOME                    =${GIT_HOME}\n"
        MSG="${MSG}GIT_VCS_REPOSITORY_URL      =${GIT_VCS_REPOSITORY_URL}\n"
        MSG="${MSG}GIT_VCS_PROJECT_ROOT        =${GIT_VCS_PROJECT_ROOT}\n"
        MSG="${MSG}GIT_VCS_USERNAME            =${GIT_VCS_USERNAME}\n"
        PR_PASSWORD=$(printablePassword "$GIT_VCS_PASSWORD")
        MSG="${MSG}GIT_VCS_PASSWORD            =${PR_PASSWORD}\n"
        MSG="${MSG}GIT_VCS_WORKSPACE_HOME      =${GIT_VCS_WORKSPACE_HOME}\n"
        MSG="${MSG}GIT_VCS_WORKSPACE_NAME      =${GIT_VCS_WORKSPACE_NAME}\n"
        MSG="${MSG}GIT_VCS_TEMP_DIR            =${GIT_VCS_TEMP_DIR}\n"
        MSG="${MSG}GIT_VALID_ENV_CONFIG_PAIRS  =${GIT_VALID_ENV_CONFIG_PAIRS}\n"
    fi
    if [ "$P4_PRINT" == "1" ]; then
        MSG="${MSG}------------------\n"
        MSG="${MSG}PERFORCE VARIABLES\n"
        MSG="${MSG}------------------\n"
        MSG="${MSG}P4_HOME                     =${P4_HOME}\n"
        MSG="${MSG}P4_VCS_REPOSITORY_URL       =${P4_VCS_REPOSITORY_URL}\n"
        MSG="${MSG}P4_VCS_PROJECT_ROOT         =${P4_VCS_PROJECT_ROOT}\n"
        MSG="${MSG}P4_VCS_USERNAME             =${P4_VCS_USERNAME}\n"
        PR_PASSWORD=$(printablePassword "$P4_VCS_PASSWORD")
        MSG="${MSG}P4_VCS_PASSWORD             =${PR_PASSWORD}\n"
        MSG="${MSG}P4_VCS_WORKSPACE_HOME       =${P4_VCS_WORKSPACE_HOME}\n"
        MSG="${MSG}P4_VCS_WORKSPACE_NAME       =${P4_VCS_WORKSPACE_NAME}\n"
        MSG="${MSG}P4_VCS_TEMP_DIR             =${P4_VCS_TEMP_DIR}\n"
        MSG="${MSG}P4_VALID_ENV_CONFIG_PAIRS   =${P4_VALID_ENV_CONFIG_PAIRS}\n"
    fi
    echo -e "$MSG"
    # Output to the default log file if the variable DEFAULT_LOG_PATH is defined in ExecutePDTool.sh
    if [[ ("$DEFAULT_LOG_PATH" != "") && (-f "$DEFAULT_LOG_PATH") ]]; then
        echo -e "$MSG">>"$DEFAULT_LOG_PATH"
    fi
	MSG=""
fi
