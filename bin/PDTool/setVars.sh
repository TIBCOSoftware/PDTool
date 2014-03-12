#!/bin/bash
######################################################################
# (c) 2014 Cisco and/or its affiliates. All rights reserved.
######################################################################
# #==========================================================
# # setVars.bat :: Set Environment Variables
# #==========================================================
#
#----------------------------------------------------------
# USER MODIFIES ENVIRONMENT VARIABLES
#-----------------------------------------------------------
# For Command-line execution - Set to JRE 1.6 Home Directory
# For Ant execution - set to JDK 1.6 Home Directory
export JAVA_HOME="/home/qa/dev/projects/tools/linux-x86-64/jdk/jdk1.6.0_22"

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
PROJECT_HOME="`pwd`"
export PROJECT_HOME=`pwd`
export PROJECT_HOME_PHYSICAL=`pwd`
cd "${CURDIR}"

# -----------------------
# PDTool Over SSL (https)
# -----------------------
# If PDTool over SSL using HTTPS is required, then configure CERT_ARGS with the following correct CIS_HOME and trustStorePassword:
#   1. Weak TrustStore ships with CIS and PDTool
#      CERT_ARGS="-Djavax.net.ssl.trustStore=\"${PROJECT_HOME_PHYSICAL}/security/cis_studio_truststore.jks\" -Djavax.net.ssl.trustStorePassword=changeit"
#
#   2. Strong Encryption pack and strong TrustStore acquired from Cisco support.  Copy into PDTool \security directory.
#      CERT_ARGS="-Djavax.net.ssl.trustStore=\"${PROJECT_HOME_PHYSICAL}/security/cis_studio_truststore_strong.jks\" -Djavax.net.ssl.trustStorePassword=changeit"
#
export CERT_ARGS="-Djavax.net.ssl.trustStore=\"${PROJECT_HOME_PHYSICAL}/security/cis_studio_truststore.jks\" -Djavax.net.ssl.trustStorePassword=changeit"

# -----------------------
# PDTool Proxy Settings
# -----------------------
# Set the HTTP proxy settings for PDTool.  Determine if a proxyUser and proxyPassword is required and set accordingly.
#   HTTP_PROXY="-DproxySet=true -Dhttp.proxyHost=wwwproxy.mydomain.com -Dhttp.proxyPort=80 -Dhttp.proxyUser=mydomain\myuser -Dhttp.proxyPassword=mypassword"
export HTTP_PROXY=""

# Set the Java Options for the JVM command line
export JAVA_OPT="${MIN_MEMORY} ${MAX_MEMORY} ${CERT_ARGS} ${HTTP_PROXY}"
