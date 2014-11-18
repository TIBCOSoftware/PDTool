#!/bin/bash 
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
#=======================================================================================
# Example Execution Statement:
#
# Option 1 - Execute VCS Workspace initialization:
#            ExecutePDToolStudio.sh -vcsinit [vcs-username] [vcs-password]
#	            arg1=-vcsinit is used to initialize the vcs workspace and link it to the repository
#               arg2=[vcs-username] optional parameters
#               arg3=[vcs-password] optional parameters
#
# Option 2 - Execute VCS Workspace property file encryption:
#            ExecutePDToolStudio.sh -encrypt property-file-path
#	            arg1=-encrypt is used to encrypt the passwords in studio.properties or a Module XML property file
#	            arg2=file path to studio.properties or XML property file (full or relative path)
#
## Editor: Set tab=4 in your text editor for this file to format properly
#=======================================================================================
#
#----------------------------------------------------------
#*********** DO NOT MODIFY BELOW THIS LINE ****************
#----------------------------------------------------------
#
#---------------------------------------------
# Set environment variables
#---------------------------------------------
if [ ! -f setVars.sh ]; then
   echo "Cannot find setVars.sh environment variable file."
   exit 1
fi
. ./setVars.sh
#
#----------------------------------------------------------
# Functions 
#-----------------------------------------------------------
usage() {
#Usage Exit
	ext=".sh"
 	writeOutput " -----------------------------------------------------------------------------------------------------"
	writeOutput " "
	writeOutput " USAGE: ${SCRIPT}${ext} [-vcsinit|-encrypt] [property file name] [vcs-username] [vcs-password]"
	writeOutput " "
 	writeOutput " Argument [$ARG] is missing or invalid."
	writeOutput " CMD: $PARAMS"
	writeOutput " "
	writeOutput " -----------------------------------------------------------------------------------------------------"
	writeOutput " Option 1 - Execute VCS Workspace initialization:"
	writeOutput " "
	writeOutput "            ${SCRIPT}${ext} -vcsinit [-vcsuser username] [-vcspassword password] [-config deploy.properties]"
	writeOutput " "
	writeOutput "            Example: ${SCRIPT}${ext} -vcsuser user -vcspassword password -config deploy.properties"
 	writeOutput " "
	writeOutput "               arg1:: -vcsinit is used to initialize the vcs workspace and link it to the repository"
	writeOutput  "              arg2::   [-vcsuser username] optional parameters"
	writeOutput  "              arg3-4:: [-vcspassword password] optional parameters"
	writeOutput  "              arg5-6:: [-config deploy.properties] optional parameters"
 	writeOutput " -----------------------------------------------------------------------------------------------------"
	writeOutput " Option 2 - Execute Encrypt Property File:"
	writeOutput " "
	writeOutput "            ${SCRIPT}${ext} -encrypt property-file-path"
	writeOutput " "
	writeOutput "            Example: ${SCRIPT}${ext} -encrypt ../resources/config/studio.properties"
 	writeOutput " "
	writeOutput "               arg1:: -encrypt is used to encrypt the passwords in studio.properties or a Module XML property file"
	writeOutput "               arg2:: file path to studio.properties or XML property file [full or relative path]"
 	writeOutput " -----------------------------------------------------------------------------------------------------"
	writeOutput " "
	exit 1
}
writeOutput() { 
   # arg1=text to echo to command line and logfile
   # arg2=prefix to prepend to the text (usually the script name).  Leave blank "" for no prefix.
   # arg3=separator
   # arg4=-nodate
   # arg5=-debug
  PREFIX="$2"
  WSEP="$3"
  DEBUG=""
  if [ "$2" != "" ]; then  PREFIX="$2$WSEP"; fi
  DT="`date '+%a %b %d %Y %r %Z'`"
  DT="$DT$WSEP"
  if [ "$4" != "" -a "$4" == "-nodate" ]
  then  
     DT=""
  fi
  if [ "$5" != "" -a "$5" == "-nodate" ]
  then
     DT=""
  fi
  if [ "$4" != "" -a "$4" == "-debug" ]; then DEBUG="DEBUG$WSEP"; fi
  if [ "$5" != "" -a "$5" == "-debug" ]; then DEBUG="DEBUG$WSEP"; fi
  
  output="$DEBUG$PREFIX$DT$1"
  echo "$output"
}


#----------------------------------------------------------
# BEGIN SCRIPT
#-----------------------------------------------------------
#=======================================
# Set up the execution context for invoking common scripts
#=======================================
SCRIPT="ExecutePDToolStudio"
SEP="::"
REM # Print out the Banner
writeOutput " " 																							"" $SEP -nodate
writeOutput "----------------------------------------------------------" 									"" $SEP -nodate
writeOutput "----                                                   ---" 									"" $SEP -nodate
writeOutput "---- Composite PS Promotion and Deployment Tool Studio ---" 									"" $SEP -nodate
writeOutput "----                                                   ---" 									"" $SEP -nodate
writeOutput "----------------------------------------------------------" 									"" $SEP -nodate
writeOutput " " 																							"" $SEP -nodate
writeOutput " " 																							"" $SEP -nodate
writeOutput "***** BEGIN COMMAND: $SCRIPT *****" 															$SCRIPT $SEP
writeOutput " " 																							"" $SEP -nodate
#=======================================
# Derive PROJECT_HOME from current dir
#=======================================
export CURDIR=`pwd`
cd ..
export PROJECT_HOME=`pwd`
cd "${CURDIR}"
#=======================================
# Validate Paths exist
#=======================================
if [ ! -d "${PROJECT_HOME}" ]; then
   writeOutput "Execution Failed::PROJECT_HOME does not exist: ${PROJECT_HOME}" 							$SCRIPT $SEP
   exit 1
fi
if [ ! -d "$JAVA_HOME" ]; then
   writeOutput "Execution Failed::JAVA_HOME does not exist: $JAVA_HOME" 									$SCRIPT $SEP
   exit 1
fi
echo " "
#=======================================
# Display Licenses
#=======================================
#writeOutput " " 
#writeOutput "------------------------------------------------------------------"							"" $SEP -nodate 
#writeOutput "------------------------ PD Tool Licenses ------------------------" 							"" $SEP -nodate
#writeOutput "------------------------------------------------------------------" 							"" $SEP -nodate
#cat "${PROJECT_HOME}/licenses/Composite_License.txt"
#writeOutput " " 																							"" $SEP -nodate
#cat "${PROJECT_HOME}/licenses/Project_Specific_License.txt"
#writeOutput " " 																							"" $SEP -nodate

#=======================================
# Set DeployManager Environment Variables
#=======================================
DEPLOY_CLASSPATH="${PROJECT_HOME}/dist/*:${PROJECT_HOME}/lib/*"
ENDORSED_DIR="${PROJECT_HOME}/lib/endorsed"
DEPLOY_MANAGER="com.cisco.dvbu.ps.deploytool.DeployManagerUtil"
DEPLOY_COMMON_UTIL="com.cisco.dvbu.ps.common.scriptutil.ScriptUtil"
CONFIG_LOG4J="-Dlog4j.configuration=\"file:${PROJECT_HOME}/resources/config/log4j.properties\""
CONFIG_ROOT="-Dcom.cisco.dvbu.ps.configroot=\"${PROJECT_HOME}/resources/config\""
#=======================================
# Parameter Validation
#=======================================
export PARAMS="$0 $*"
error="0"

if [ "$#" -eq 0 ]
then   # Script needs at least one command-line argument.
  usage 1
fi  

while [ ! -z "$1" ]
do
  case "$1" in
    -encrypt)
		export CMD="-encrypt"
		export PROPERTY_FILE="$2"
		#echo "CMD=$CMD   PROPERTY_FILE=$PROPERTY_FILE"
		ARG="PROPERTY_FILE"
		if [ "$PROPERTY_FILE" == "" ]; then
			error="1"
		fi
		shift
		;;
    -vcsinit)
		export CMD="-vcsinit"
		#echo "CMD=$CMD"
		;;
    -vcsuser)
		export VCS_USERNAME="$2"
		#echo "VCS_USERNAME=$VCS_USERNAME"
		ARG="VCS_USERNAME"
		if [ "$VCS_USERNAME" == "" ]; then
			error="1"
		fi
		shift
		;;
    -vcspassword)
		export VCS_PASSWORD="$2"
		ARG="VCS_PASSWORD"
		#echo "VCS_PASSWORD=$VCS_PASSWORD"
		if [ "$VCS_PASSWORD" == "" ]; then
			error="1"
		fi
		shift
		;;
    *) 
		# unknown option
		writeOutput " " 																					"" $SEP -nodate
		writeOutput "Unknown parameter: $1"																	$SCRIPT $SEP
		writeOutput " " 																					"" $SEP -nodate
		usage $1
		exit 2 
		;;
  esac
  
  if [ "$error" == "1" ]; then
	writeOutput " " 																						"" $SEP -nodate
	writeOutput "Script Parameters are incorrect. "															$SCRIPT $SEP
	writeOutput " " 																						"" $SEP -nodate
	usage $ARG
  fi

  shift
done

# Set the default to perform a vcsinit if no parameters are specified
if [ "${CMD}" == "" ]; then
	export CMD="-vcsinit"
fi

# Branch to the correct commmand area

# Assign variables for init vcs workspace
if [ "${CMD}" == "-vcsinit" ]; then
	PR_VCS_PASSWORD=""
	if [ "${VCS_PASSWORD}" != "" ]; then PR_VCS_PASSWORD="********"; fi
	#***********************************************
	# Invoke: DeployManagerUtil vcsStudioInitWorkspace "${VCS_USERNAME}" "${VCS_PASSWORD}"
	#***********************************************
	JAVA_ACTION="vcsStudioInitWorkspace"
	  COMMAND="\"$JAVA_HOME/bin/java\" ${JAVA_OPT} -classpath \"${DEPLOY_CLASSPATH}\" ${CONFIG_ROOT} ${CONFIG_LOG4J} -Djava.endorsed.dirs=\"${ENDORSED_DIR}\" -DPROJECT_HOME=\"${PROJECT_HOME}\" -DCONFIG_PROPERTY_FILE=\"${CONFIG_PROPERTY_FILE}\" ${DEPLOY_MANAGER} ${JAVA_ACTION} \"${VCS_USERNAME}\" \"${VCS_PASSWORD}\""
	PRCOMMAND="\"$JAVA_HOME/bin/java\" ${JAVA_OPT} -classpath \"${DEPLOY_CLASSPATH}\" ${CONFIG_ROOT} ${CONFIG_LOG4J} -Djava.endorsed.dirs=\"${ENDORSED_DIR}\" -DPROJECT_HOME=\"${PROJECT_HOME}\" -DCONFIG_PROPERTY_FILE=\"${CONFIG_PROPERTY_FILE}\" ${DEPLOY_MANAGER} ${JAVA_ACTION} \"${VCS_USERNAME}\" \"${PR_VCS_PASSWORD}\""
fi

# Assign variables for encrypting a property file
if [ "${CMD}" == "-encrypt" ]; then
	ARG="PROPERTY_FILE"
	if [ ! -f "${PROPERTY_FILE}" ]; then
	   writeOutput "Execution Failed::Property File does not exist: ${PROPERTY_FILE}" 						$SCRIPT $SEP
	   usage $ARG
	fi
	#***********************************************
	# Invoke: ScriptUtil encryptPasswordsInFile "${PROPERTY_FILE}"
	#***********************************************
	JAVA_ACTION="encryptPasswordsInFile"
	  COMMAND="\"$JAVA_HOME/bin/java\" ${JAVA_OPT} -classpath \"${DEPLOY_CLASSPATH}\" ${CONFIG_ROOT} ${CONFIG_LOG4J} -Djava.endorsed.dirs=\"${ENDORSED_DIR}\" -DPROJECT_HOME=\"${PROJECT_HOME}\" -DCONFIG_PROPERTY_FILE=\"studio.properties\" ${DEPLOY_COMMON_UTIL} ${JAVA_ACTION} \"${PROPERTY_FILE}\""
	PRCOMMAND="${COMMAND}"
fi

# Print out the command line
writeOutput " "																								"" $SEP -nodate
writeOutput "-- COMMAND: ${JAVA_ACTION} ----------------------------" 										"" $SEP -nodate
writeOutput " "																								"" $SEP -nodate
writeOutput "${PRCOMMAND}" 																					"" $SEP -nodate
writeOutput " "																								"" $SEP -nodate
writeOutput "-- BEGIN OUTPUT ------------------------------------" 											"" $SEP -nodate
writeOutput " "																								"" $SEP -nodate

#------------------------------------------------------------------------
# Execute the command line CIS script
#------------------------------------------------------------------------
eval ${COMMAND}
ERROR=$?
if [ $ERROR -ne 0 ]; then
    writeOutput "Script ${SCRIPT} Failed. Abnormal Script Termination. Exit code is: ${ERROR}"				$SCRIPT $SEP
    exit ${ERROR}
fi

#=======================================
# Successful script completion
#=======================================
writeOutput " " 																								"" $SEP -nodate
writeOutput "-------------- SUCCESSFUL SCRIPT COMPLETION [${SCRIPT} ${CMD}] --------------" 					$SCRIPT $SEP
writeOutput "End of script."																					$SCRIPT $SEP
exit 0															
