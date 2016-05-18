#!/bin/sh
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
###################################################
# Set customer variables
###################################################
echo "########################################################################################################################################"
echo ""
echo "Setting custom variables"
echo ""
echo "########################################################################################################################################"

# The student number for the Lab-PD [01-12]
export STUDENTID="00"
echo STUDENTID="${STUDENTID}"

# CIS Port
# Used to define the SERVERID in deploy.properties
export PORT="9400"
echo PORT="${PORT}"

# HTTP_TYPE is set in the OS prior to execution giving greater flexibility to choose at runtime.  
#    set HTTP_TYPE=http to connect over regular http.
#    set HTTP_TYPE=https to connect PDTool over SSL (htts).
# This is used to compose the SERVERID for selecting the correct servers.xml id.
# This is used in ArchiveModule.xml for selecting the correct car file to import.
# This is used in DataSourceModule.xml for updating the URL for the web service data source.
export HTTP_TYPE="http"
echo HTTP_TYPE="${HTTP_TYPE}"

# Web Service Port
#   for HTTP set WSPORT=9400
#   for HTTPS set WSPORT=9402
# This is used in DataSourceModule.xml for updating the URL for the web service data source.
export WSPORT="9400"
echo WSPORT="${WSPORT}"

export VCS_EDITOR="vi"
echo VCS_EDITOR="${VCS_EDITOR}"

# Set the SVN variables as needed for testing
export SVN_HOME=""
export SVN_VCS_USERNAME=""
export SVN_VCS_PASSWORD=""
export SVN_VCS_REPOSITORY_URL=""
export SVN_VCS_PROJECT_ROOT=""
export SVN_VCS_WORKSPACE_NAME=""
export SVN_VCS_WORKSPACE_SUBFOLDERS=""
export SVN_VCS_WORKSPACE_NAME_TEMP=""
# Display the SVN variables
echo SVN_HOME="${SVN_HOME}"
echo SVN_VCS_USERNAME="${SVN_VCS_USERNAME}"
echo SVN_VCS_PASSWORD="********"
echo SVN_VCS_REPOSITORY_URL="${SVN_VCS_REPOSITORY_URL}"
echo SVN_VCS_PROJECT_ROOT="${SVN_VCS_PROJECT_ROOT}"
echo SVN_VCS_WORKSPACE_NAME="${SVN_VCS_WORKSPACE_NAME}"
echo SVN_VCS_WORKSPACE_SUBFOLDERS="${SVN_VCS_WORKSPACE_SUBFOLDERS}"
echo SVN_VCS_WORKSPACE_NAME_TEMP="${SVN_VCS_WORKSPACE_NAME_TEMP}"

# Set the TFS variables as needed for testing
export TFS_HOME=""
export TFS_VCS_USERNAME=""
export TFS_VCS_PASSWORD=""
export TFS_VCS_REPOSITORY_URL=""
export TFS_VCS_PROJECT_ROOT=""
export TFS_VCS_WORKSPACE_NAME=""
export TFS_VCS_WORKSPACE_SUBFOLDERS=""
export TFS_VCS_WORKSPACE_NAME_TEMP=""
# Display the TFS variables
echo TFS_HOME="${TFS_HOME}"
echo TFS_VCS_USERNAME="${TFS_VCS_USERNAME}"
echo TFS_VCS_PASSWORD="********"
echo TFS_VCS_REPOSITORY_URL="${TFS_VCS_REPOSITORY_URL}"
echo TFS_VCS_PROJECT_ROOT="${TFS_VCS_PROJECT_ROOT}"
echo TFS_VCS_WORKSPACE_NAME="${TFS_VCS_WORKSPACE_NAME}"
echo TFS_VCS_WORKSPACE_SUBFOLDERS="${TFS_VCS_WORKSPACE_SUBFOLDERS}"
echo TFS_VCS_WORKSPACE_NAME_TEMP="${TFS_VCS_WORKSPACE_NAME_TEMP}"

# Set the GIT variables as needed for testing
export GIT_HOME=""
export GIT_VCS_USERNAME=""
export GIT_VCS_PASSWORD=""
export GIT_VCS_REPOSITORY_URL="https://github.com/$GIT_VCS_USERNAME/$GIT_VCS_PROJECT.git"
export GIT_VCS_PROJECT_ROOT=""
export GIT_VCS_WORKSPACE_NAME=""
export GIT_VCS_WORKSPACE_SUBFOLDERS=""
export GIT_VCS_WORKSPACE_NAME_TEMP=""
# Display the GIT variables
echo GIT_HOME="${GIT_HOME}"
echo GIT_VCS_USERNAME="${GIT_VCS_USERNAME}"
echo GIT_VCS_PASSWORD="********"
echo GIT_VCS_REPOSITORY_URL="${GIT_VCS_REPOSITORY_URL}"
echo GIT_VCS_PROJECT_ROOT="${GIT_VCS_PROJECT_ROOT}"
echo GIT_VCS_WORKSPACE_NAME="${GIT_VCS_WORKSPACE_NAME}"
echo GIT_VCS_WORKSPACE_SUBFOLDERS="${GIT_VCS_WORKSPACE_SUBFOLDERS}"
echo GIT_VCS_WORKSPACE_NAME_TEMP="${GIT_VCS_WORKSPACE_NAME_TEMP}"

# Set the P4 variables as needed for testing
export P4_HOME=""
export P4_VCS_USERNAME=""
export P4_VCS_PASSWORD=""
export P4_VCS_REPOSITORY_URL=""
export P4_VCS_PROJECT_ROOT=""
export P4_VCS_WORKSPACE_NAME=""
export P4_VCS_WORKSPACE_SUBFOLDERS=""
export P4_VCS_WORKSPACE_NAME_TEMP=""
# Display the P4 variables
echo P4_HOME="${P4_HOME}"
echo P4_VCS_USERNAME="${P4_VCS_USERNAME}"
echo P4_VCS_PASSWORD="********"
echo P4_VCS_REPOSITORY_URL="${P4_VCS_REPOSITORY_URL}"
echo P4_VCS_PROJECT_ROOT="${P4_VCS_PROJECT_ROOT}"
echo P4_VCS_WORKSPACE_NAME="${P4_VCS_WORKSPACE_NAME}"
echo P4_VCS_WORKSPACE_SUBFOLDERS="${P4_VCS_WORKSPACE_SUBFOLDERS}"
echo P4_VCS_WORKSPACE_NAME_TEMP="${P4_VCS_WORKSPACE_NAME_TEMP}"

# Set the CVS variables as needed for testing
export CVS_HOME=""
export CVS_VCS_USERNAME=""
export CVS_VCS_PASSWORD=""
export CVS_VCS_REPOSITORY_URL=""
export CVS_VCS_PROJECT_ROOT=""
export CVS_VCS_WORKSPACE_NAME=""
export CVS_VCS_WORKSPACE_SUBFOLDERS=""
export CVS_VCS_WORKSPACE_NAME_TEMP=""
# Display the CVS variables
echo CVS_HOME="${CVS_HOME}"
echo CVS_VCS_USERNAME="${CVS_VCS_USERNAME}"
echo CVS_VCS_PASSWORD="********"
echo CVS_VCS_REPOSITORY_URL="${CVS_VCS_REPOSITORY_URL}"
echo CVS_VCS_PROJECT_ROOT="${CVS_VCS_PROJECT_ROOT}"
echo CVS_VCS_WORKSPACE_NAME="${CVS_VCS_WORKSPACE_NAME}"
echo CVS_VCS_WORKSPACE_SUBFOLDERS="${CVS_VCS_WORKSPACE_SUBFOLDERS}"
echo CVS_VCS_WORKSPACE_NAME_TEMP="${CVS_VCS_WORKSPACE_NAME_TEMP}"


