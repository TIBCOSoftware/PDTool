#!/bin/sh
######################################################################
# (c) 2014 Cisco and/or its affiliates. All rights reserved.
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

# VCS Qualified host name (host.domain.com)
export SVN_VCS_HOST=""
echo SVN_VCS_HOST="${SVN_VCS_HOST}"
export P4_VCS_HOST=""
echo P4_VCS_HOST="${P4_VCS_HOST}"
export CVS_VCS_HOST=""
echo CVS_VCS_HOST="${CVS_VCS_HOST}"
export TFS_VCS_HOST=""
echo TFS_VCS_HOST="${TFS_VCS_HOST}"

# Set the different VCS Users and Passwords as needed for testing
export SVN_VCS_USERNAME=""
export SVN_VCS_PASSWORD=""
echo SVN_VCS_USERNAME="${SVN_VCS_USERNAME}"
export P4_VCS_USERNAME=""
export P4_VCS_PASSWORD=""
echo P4_VCS_USERNAME="${P4_VCS_USERNAME}"
export CVS_VCS_USERNAME=""
export CVS_VCS_PASSWORD=""
echo CVS_VCS_USERNAME="${CVS_VCS_USERNAME}"
export TFS_VCS_USERNAME=""
export TFS_VCS_PASSWORD=""
echo TFS_VCS_USERNAME="${TFS_VCS_USERNAME}"
