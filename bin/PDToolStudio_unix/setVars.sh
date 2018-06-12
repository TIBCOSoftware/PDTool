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
# #==========================================================
# # setVars.bat :: Set Environment Variables
# #==========================================================
#
# #----------------------------------------------------------
# # USER MODIFIES ENVIRONMENT VARIABLES
# #----------------------------------------------------------
# # Set to JRE 1.6 Home Directory
export JAVA_HOME="/usr/lib/jvm/java-6-sun"

# Configure the Java Heap Min and Max memory
export MIN_MEMORY="-Xms256m"
export MAX_MEMORY="-Xmx512m"

# Name of the configuration property file located in CisDeployTool/resources/config
export CONFIG_PROPERTY_FILE=studio.properties

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
#   2. Strong Encryption pack and strong TrustStore acquired from TIBCO support.  Copy into PDTool \security directory.
#      CERT_ARGS="-Djavax.net.ssl.trustStore=\"${PROJECT_HOME_PHYSICAL}/security/cis_studio_truststore_strong.jks\" -Djavax.net.ssl.trustStorePassword=changeit"
#
export CERT_ARGS=
export JAVA_OPT="${MIN_MEMORY} ${MAX_MEMORY} ${CERT_ARGS}"
