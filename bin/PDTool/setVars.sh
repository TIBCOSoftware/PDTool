#!/bin/bash
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
#==========================================================
# setVars.sh :: Set Environment Variables
#==========================================================
SV="setVars.sh"
PRINT_VARS="1"
#
#=======================================================================================================
# CREATE/MODIFY VARIABLES BELOW THIS POINT
#=======================================================================================================
# Initialize variables to unset them [required]
MY_PRE_VARS_PATH=""
MY_POST_VARS_PATH=""
#
# The My Vars path provides with the user the ability to set specific environment variables for their login
#   The location of these batch files is typically outside of the PDTOOL_HOME in the user's directory space.
#   e.g. set MY_VARS_HOME=c:\users\%USERNAME%\.compositesw\PDTool<ver>_<vcs> or set MY_VARS_HOME=c:\users\%USERNAME%\.compositesw\PDTool<ver>_<vcs>\bin
MY_VARS_HOME="/opt/TIBCO/PDTool8.3.0_GIT/PDTool/bin"
#
if [ "$MY_VARS_HOME" != "" ]; then
    MY_PRE_VARS_PATH="$MY_VARS_HOME/setMyPrePDToolVars.sh"
    MY_POST_VARS_PATH="$MY_VARS_HOME/setMyPostPDToolVars.sh"
    if [[ ( "$MY_PRE_VARS_PATH" != "" ) && ( -f  "$MY_PRE_VARS_PATH" ) ]]; then
        source "$MY_PRE_VARS_PATH"
    fi
fi
#
# For Command-line execution - Set to JRE 1.6 Home Directory
# For Ant execution - set to JDK 1.6 Home Directory
export JAVA_HOME="/opt/TIBCO/TDV8.3/tdv1/jdk"

# Configure the Java Heap Min and Max memory
export MIN_MEMORY="-Xms256m"
export MAX_MEMORY="-Xmx512m"

# Default name of the configuration property file located in CisDeployTool/resources/config
#   Note: This property may be overwritten by using -config <prop_name.properties> on the command line
export CONFIG_PROPERTY_FILE=deploy.properties

#=======================================
# Derive PROJECT_HOME from current dir
#=======================================
export CURDIR=`pwd`
cd ..
if [ "$PROJECT_HOME" == "" ]; then export PROJECT_HOME=`pwd`; fi;
if [ "$PROJECT_HOME_PHYSICAL" == "" ]; then export PROJECT_HOME_PHYSICAL=`pwd`; fi;
cd "${CURDIR}"

# -----------------------
# PDTool Over SSL (https)
# -----------------------
# If PDTool over SSL using HTTPS is required, then configure CERT_ARGS with the following correct CIS_HOME and trustStorePassword:
#   1. Weak TrustStore ships with CIS and PDTool
#      CERT_ARGS="-Djavax.net.ssl.trustStore=\"${PROJECT_HOME_PHYSICAL}/security/cis_studio_truststore.jks\" -Djavax.net.ssl.trustStorePassword=changeit"
#
#   2. Strong Encryption pack and strong TrustStore acquired from TIBCO support.  Copy into PDTool \security directory.
#      CERT_ARGS="-Djavax.net.ssl.trustStore=\"${PROJECT_HOME_PHYSICAL}/security/cis_studio_truststore_strong.jks\" -Djavax.net.ssl.trustStorePassword=changeit"
#
export CERT_ARGS="-Djavax.net.ssl.trustStore=\"${PROJECT_HOME_PHYSICAL}/security/cis_studio_truststore_strong.jks\" -Djavax.net.ssl.trustStorePassword=changeit"

# -----------------------
# PDTool Proxy Settings
# -----------------------
# Set the HTTP proxy settings for PDTool.  Determine if a proxyUser and proxyPassword is required and set accordingly.
#   HTTP_PROXY="-DproxySet=true -Dhttp.proxyHost=wwwproxy.mydomain.com -Dhttp.proxyPort=80 -Dhttp.proxyUser=mydomain\myuser -Dhttp.proxyPassword=mypassword"
export HTTP_PROXY=""

# Set the Java Options for the JVM command line
export JAVA_OPT="${MIN_MEMORY} ${MAX_MEMORY} ${CERT_ARGS} ${HTTP_PROXY}"

#=======================================================================================================
# CREATE/MODIFY VARIABLES ABOVE THIS POINT
#=======================================================================================================
#---------------------------------------------
# Print out the setVars.sh variables
#---------------------------------------------
if [ "$PRINT_VARS" == "1" ]; then
    MSG=""
    MSG="${MSG}########################################################################################################################################\n"
    MSG="${MSG}$0: Setting PDTool variables\n"
    MSG="${MSG}########################################################################################################################################\n"
    MSG="${MSG}MY_VARS_HOME               =${MY_VARS_HOME}\n"
    MSG="${MSG}MY_PRE_VARS_PATH           =${MY_PRE_VARS_PATH}\n"
    MSG="${MSG}MY_POST_VARS_PATH          =${MY_POST_VARS_PATH}\n"
    MSG="${MSG}JAVA_HOME                  =${JAVA_HOME}\n"
    MSG="${MSG}CONFIG_PROPERTY_FILE       =${CONFIG_PROPERTY_FILE}\n"
    MSG="${MSG}PROJECT_HOME               =${PROJECT_HOME}\n"
    MSG="${MSG}PROJECT_HOME_PHYSICAL      =${PROJECT_HOME_PHYSICAL}\n"
    MSG="${MSG}MIN_MEMORY                 =${MIN_MEMORY}\n"
    MSG="${MSG}MAX_MEMORY                 =${MAX_MEMORY}\n"
    MSG="${MSG}CERT_ARGS                  =${CERT_ARGS}\n"
    MSG="${MSG}HTTP_PROXY                 =${HTTP_PROXY}\n"
    MSG="${MSG}JAVA_OPT                   =${JAVA_OPT}\n"
    echo -e "$MSG"
    # Output to the default log file if the variable DEFAULT_LOG_PATH is defined in ExecutePDTool.sh
    if [[ ("$DEFAULT_LOG_PATH" != "") && (-f "$DEFAULT_LOG_PATH") ]]; then
        echo -e "$MSG">>"$DEFAULT_LOG_PATH"
    fi
	MSG=""
fi
#---------------------------------------------
# POST-PROCESSING CUSTOM VARIABLES:
#---------------------------------------------
if [ "$MY_VARS_HOME" != "" ]; then
    if [[ ( "$MY_POST_VARS_PATH" != "" ) && ( -f  "$MY_POST_VARS_PATH" ) ]]; then
        source "$MY_POST_VARS_PATH"
    fi
fi
