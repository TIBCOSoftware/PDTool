#!/bin/bash
######################################################################
#
# ./configScripts.sh [OWNER] [GROUP]
# Example:
#    ./configScripts.sh tibco tibco
#
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

BASEDIR=..

# Get the Owner permission variable
if [ "$1" == "" ]
then
   OWNER="tibco"
else
   OWNER="$1"
fi

# Get the Group permission variable
if [ "$2" == "" ]
then
   GROUP="tibco"
else
   GROUP="$2"
fi
echo "Command=$0 $1 $2"

# List of target directories to process
#   make sure each directory ends in a slash
#   list must be enclosed in double quotes
#
ANT_HOME="${BASEDIR}/ext/ant"

TARGETLIST_BASE="${BASEDIR}/bin/ ${BASEDIR}/resources/config/ ${BASEDIR}/resources/modules/ ${BASEDIR}/resources/plans/ ${BASEDIR}/resources/ant/"
TARGETLIST="${TARGETLIST_BASE}"

# Permission to set executable files (*.sh and *.bin)
PERMISSION="744"

#=========================================================================
# FUNCTION LIBRARY
#=========================================================================
#--------------------------------------------
# FUNCTION::Convert files from dos2unix
#--------------------------------------------
function convertDos2Unix {
  echo "convert dos to unix with sed"
  LIST=`ls -lp --format=single-column | grep '\.sh\|\.txt\|\.properties\|\.xml\|\.cmd\|\.dp'`
  #echo "LIST=${LIST}"
  IFS=$'\n'
  for fileName in ${LIST}
  do
    echo "  sed: Convert ${fileName}"
	sed -i 's/\r//' "${fileName}"
  done
}

#--------------------------------------------
# FUNCTION::Set permissions
#--------------------------------------------
function setPermission {
  permission=$2
  MSG=""
  MSG="${MSG}set permission::chmod in dir=`pwd`\n"
  numFiles1=`ls -lp --format=single-column | grep '\.sh' | wc -l`
  if [ ${numFiles1} -gt 0 ]; then
    MSG="${MSG}  chmod ${PERMISSION} *.sh\n"
    chmod ${PERMISSION} *.sh
  fi
  numFiles2=`ls -lp --format=single-column | grep '\.bin' | wc -l`
  if [ ${numFiles2} -gt 0 ]; then
    MSG="${MSG}  chmod ${PERMISSION} *.bin\n"
    chmod ${PERMISSION} *.bin
  fi
  numFiles3=`ls -lp --format=single-column | grep '\.cmd' | wc -l`
  if [ ${numFiles3} -gt 0 ]; then
    MSG="${MSG}  chmod ${PERMISSION} *.cmd\n"
    chmod ${PERMISSION} *.cmd
  fi
  if [[ (${numFiles1} -gt 0) || (${numFiles2} -gt 0) || (${numFiles3} -gt 0) ]]; then
      echo -e $MSG
  fi
}


#=========================================================================
# BEGIN SCRIPT
#=========================================================================
echo "-----------------------------------"
echo "Begin installation configuration..."
echo "-----------------------------------"
echo "--------------------------------------"
echo "configure owner:group=chown -R ${OWNER}:${GROUP} ../../PDTool"
echo "--------------------------------------"
chown -R ${OWNER}:${GROUP} ../../PDTool
echo "-----------------------------------"
echo "Target Folder List::[${TARGETLIST}]"
echo "-----------------------------------"
if [ "${TARGETLIST}" != "" ]
then
   # Dynamically get the list of deployment directories
   for FOLDER in ${TARGETLIST}
     do
       echo "----------------------------------"
       echo "configure ${FOLDER} ..."
       echo "----------------------------------"
       currdir=`pwd` 
       cd "${FOLDER}"
       ERROR=$?
       if [ "${ERROR}" == "0" ]
       then
          convertDos2Unix
          setPermission ${PERMISSION}
       else
          echo "Folder does not exist: ${FOLDER}"
       fi
       cd "${currdir}"
   done
fi

# If ANT_HOME exists then set the permission on the ant executable
if [ -d "${ANT_HOME}" ]; then
   echo "--------------------------------------"
   echo "Set permissions on ${ANT_HOME}/bin/ant"
   echo "  chmod 744 \"${ANT_HOME}/bin/ant\""
   echo "--------------------------------------"
   chmod 744 "${ANT_HOME}/bin/ant"
fi

echo "--------------------------------------"
echo "Finished installation configuration..."
echo "--------------------------------------"
