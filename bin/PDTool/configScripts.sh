#!/bin/bash
######################################################################
# (c) 2014 Cisco and/or its affiliates. All rights reserved.
######################################################################

BASEDIR=..

# Get the Owner permission variable
if [ "$1" == "" ]
then
   OWNER=composite
else
   OWNER=$1
fi

# Get the Group permission variable
if [ "$2" == "" ]
then
   GROUP=composite
else
   GROUP=$2
fi
echo "Command=$0 $1 $2"

# List of target directories to process
#   make sure each directory ends in a slash
#   list must be enclosed in double quotes
#
ANT_HOME=${BASEDIR}/ext/ant

TARGETLIST_BASE="${BASEDIR}/bin/ ${BASEDIR}/resources/config/ ${BASEDIR}/resources/properties/ ${BASEDIR}/resources/modules/ ${BASEDIR}/resources/ant/"
TARGETLIST="${TARGETLIST_BASE}"

# Permission to set executable files (*.sh and *.bin)
PERMISSION=744

PLATFORM=$(uname -s | tr [A-Z] [a-z])

DOS2UNIX=$(which dos2unix 2> /dev/null)
FROMDOS=$(which fromdos 2> /dev/null)
DOS2UX=$(which dos2ux 2> /dev/null)

#if [ ! -x "$DOS2UNIX" ] && [ ! -x "$FROMDOS" ] && [ $PLATFORM = "linux" ]; then
#
#	cat << END
#
#Please install dos2unix.
#
#To install on Redhat/Fedora:
#yum install dos2unix
#
#To install on SuSE:
#zypper install dos2unix
#
#To install on Debian/Ubuntu:
#apt-get install dos2unix
#or
#apt-get install tofrodos
#END
#	exit 1
#fi
#=========================================================================
# FUNCTION LIBRARY
#=========================================================================
#--------------------------------------------
# FUNCTION::Convert files from DOS/Windows to Unix using the respective known 
# commands on particular platforms. If known conversion commands on the  
# respective platforms are not found then it'll fall back to tr
#--------------------------------------------
function dos2Unix {
	case $PLATFORM in
  		sunos)
  			if [ -x "$DOS2UNIX" ]; then
  				${DOS2UNIX} $1 $1
  				return 0
  			fi
  			;;
  		linux)
			if [ -x "$DOS2UNIX" ]; then
				${DOS2UNIX} $1
				return 0
			elif [ -x "$FROMDOS" ]; then
				${FROMDOS} $1
				return 0
			fi
			;;
		hp-ux)
			if [ -x "$DOS2UX" ]; then
				baseFileName=$(basename $1)
				tmpFile=$(mktemp -t $baseFileName.XXXXXX)
				if ${DOS2UX} $1 >"$tmpFile" ; then
					cp -a -f "$tmpFile" "$1"
				fi
				rm -f "$tmpFile"
				return 0
			fi
			;;
# Try tr with if the platform is not Linux/Solaris/HP-UX
		*)
		    baseFileName=$(basename $1)
			tmpFile=$(mktemp -t $baseFileName.XXXXXX)
			if tr -d '\15\32' <"$1" >"$tmpFile" ; then
				cp -a -f "$tmpFile" "$1"
			fi
			rm -f "$tmpFile"
			return 0
	esac
	# If the platform is Linux/Solaris/HP-UX but the commands are not available then use tr
	baseFileName=$(basename $0)
	tmpFile=$(mktemp -t $baseFileName.XXXXXX)
	if tr -d '\15\32' <"$1" >"$tmpFile" ; then
		cp -a -f "$tmpFile" "$1"
	fi
	rm -f "$tmpFile"
	return 0
}

#=========================================================================
# FUNCTION LIBRARY
#=========================================================================
#--------------------------------------------
# FUNCTION::Convert files from dos2unix
#--------------------------------------------
function convertDos2Unix {
  echo "--------------------------------------"
  echo "convert dos to unix::dos2unix"
  echo "--------------------------------------"
  LIST=`ls *.sh *.txt *.properties *.xml *.cmd`
  echo "LIST=${LIST}"
  for fileName in ${LIST}
  do
    echo "dos2unix: Convert ${fileName}"
    dos2Unix "${fileName}"
  done
}

#--------------------------------------------
# FUNCTION::Set permissions
#--------------------------------------------
function setPermission {
  permission=$2
  
  echo "--------------------------------------"
  echo "set permission::chmod"
  echo "--------------------------------------"
  existSH=`ls *.sh | wc -l`
  if [ "${existSH}" -gt "0" ]
  then
    echo "chmod ${PERMISSION} *.sh"
    chmod ${PERMISSION} *.sh
  fi
  existBIN=`ls *.bin | wc -l`
  if [ "${existBIN}" -gt "0" ]
  then
    echo "chmod ${PERMISSION} *.bin"
    chmod ${PERMISSION} *.bin
  fi
  existCMD=`ls *.cmd | wc -l`
  if [ "${existCMD}" -gt "0" ]
  then
    echo "chmod ${PERMISSION} *.cmd"
    chmod ${PERMISSION} *.cmd
  fi
  echo ""
}

#--------------------------------------------
# FUNCTION::Change Group
#--------------------------------------------
function changeGroup {
  group=$2

  echo "--------------------------------------"
  echo "configure groups::chgrp -R ${GROUP} *"
  echo "--------------------------------------"
  chgrp -R ${GROUP} *
  echo ""
}

#--------------------------------------------
# FUNCTION::Change Owner
#--------------------------------------------
function changeOwner {
  owner=$2

  echo "--------------------------------------"
  echo "configure owner::chown -R ${OWNER} *"
  echo "--------------------------------------"
  chown -R ${OWNER} *
  echo ""
}



#=========================================================================
# BEGIN SCRIPT
#=========================================================================
echo "-----------------------------------"
echo "Begin installation configuration..."
echo "-----------------------------------"

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
          changeGroup ${GROUP}
          changeOwner ${OWNER}
       else
          echo "Folder does not exist: ${FOLDER}"
       fi
       cd "${currdir}"
   done
fi

# If ANT_HOME exists then set the permission on the ant executable
if [ -d "${ANT_HOME}" ]
then
   echo "--------------------------------------"
   echo "Set permissions on ${ANT_HOME}/bin/ant..."
   echo "--------------------------------------"
   chmod 744 "${ANT_HOME}/bin/ant"
fi

echo "--------------------------------------"
echo "Finished installation configuration..."
echo "--------------------------------------"
