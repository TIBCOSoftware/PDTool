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
#=======================================================================================
# Example Execution Statement:
# Option 1 - Execute a command line property file:
#
#            ExecutePDTool.sh -exec property-file-path [-vcsuser username] [-vcspassword password] [-config deploy.properties] [-ver CIS_VERSION]
#
#               arg1=-exec is used to execute a property file
#	            arg2=orchestration propertiy file path (full or relative path)
#               arg3-4=[-vcsuser username] optional parameter
#               arg5-6=[-vcspassword password] optional parameter
#				arg7-8=[-config deploy.properties] optional parameter
#               arg9-10=[-ver CIS_VERSION] optional parameter  e.g. 7.0.0, 8.0.0
#
# Option 2 - Execute VCS Workspace initialization:
#
#            ExecutePDTool.sh -vcsinit [-vcsuser username] [-vcspassword password] [-config deploy.properties]
#
#	            arg1=-vcsinit is used to initialize the vcs workspace and link it to the repository
#               arg3-4=[-vcsuser username] optional parameter
#               arg5-6=[-vcspassword password] optional parameter
#				arg7-8=[-config deploy.properties] optional parameter
#
# Option 3 - Execute property file encryption:
#
#            ExecutePDTool.sh -encrypt property-file-path [-config deploy.properties] [-bypass "string1,string2"]
#
#	            arg1=-encrypt is used to encrypt the passwords in deploy.properties or a Module XML property file
#	            arg2=file path to deploy.properties or XML property file (full or relative path)
#				arg3-4=[-config deploy.properties] optional parameter
#				arg4-5=[-bypass "string1,string2"] optional parameter specifying a quoted, comma-separated list of strings to bypass
#                                                  that are found within a variable or XML element designated for passwords.
#
# Option 4 - Execute an Ant build file:
#
#            ExecutePDTool.sh -ant build-file-path [-vcsuser username] [-vcspassword password] [-config deploy.properties] [-ver CIS_VERSION]
#
#               arg1=-ant is used to execute an Ant build file
#	            arg2=orchestration build file path (full or relative path)
#               arg3-4=[-vcsuser username] optional parameter
#               arg5-6=[-vcspassword password] optional parameter
#				arg7-8=[-config deploy.properties] optional parameter
#               arg9-10=[-ver CIS_VERSION] optional parameter  e.g. 7.0.0, 8.0.0
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
# CIS version [7.0.0,8.0.0]
if [ ! -f cisVersion.sh ]; then
   echo "Cannot find cisVersion.sh environment variable file."
   exit 1
fi
. ./cisVersion.sh

# Initialize variables
export RELEASE_FOLDER=""
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
	ARG="$1"
 	writeOutput " -----------------------------------------------------------------------------------------------------"
	writeOutput " "
	writeOutput " USAGE: ${SCRIPT}${ext} [-exec|-vcsinit|-encrypt|-ant] [property file name] [-vcsuser username] [-vcspassword password] [-config deploy.properties]"
	writeOutput " "
 	writeOutput " Argument [$ARG] is missing or invalid."
	writeOutput " CMD: $PARAMS"
	writeOutput " "
 	writeOutput  " "
	writeOutput  " -----------------------------------------------------------------------------------------------------"
	writeOutput  " Option 1 - Execute a command line deploy plan file:"
	writeOutput  " "
	writeOutput  "            ${SCRIPT}${ext} -exec deploy-plan-file-path [-vcsuser username] [-vcspassword password] [-config deploy.properties] [-ver CIS_VERSION]"
	writeOutput  " "
	writeOutput  "            Example: ${SCRIPT}${ext} -exec ../resources/properties/myplan.dp -vcsuser user -vcspassword password -config deploy.properties"
	writeOutput  " "
	writeOutput  "               arg1::   -exec is used to execute a deploy plan file"
	writeOutput  "               arg2::   orchestration propertiy file path [full or relative path]"
	writeOutput  "               arg3-4:: [-vcsuser username] optional parameter"
	writeOutput  "               arg5-6:: [-vcspassword password] optional parameter"
	writeOutput  "               arg7-8:: [-config deploy.properties] optional parameter"
	writeOutput  "               arg9-10::[-ver CIS_VERSION] optional parameter e.g. 7.0.0, 8.0.0"
	writeOutput  " -----------------------------------------------------------------------------------------------------"
	writeOutput  " Option 2 - Execute VCS Workspace initialization:"
	writeOutput  " "
	writeOutput  "            ${SCRIPT}${ext} -vcsinit [-vcsuser username] [-vcspassword password] [-config deploy.properties]"
	writeOutput  " "
	writeOutput  "            Example: ${SCRIPT}${ext} -vcsinit -vcsuser user -vcspassword password -config deploy.properties"
 	writeOutput  " "
	writeOutput  "               arg1::   -vcsinit is used to initialize the vcs workspace and link it to the repository"
	writeOutput  "               arg2::   [-vcsuser username] optional parameter"
	writeOutput  "               arg3-4:: [-vcspassword password] optional parameter"
	writeOutput  "               arg5-6:: [-config deploy.properties] optional parameter"
	writeOutput  " -----------------------------------------------------------------------------------------------------"
	writeOutput  " Option 3 - Execute Encrypt Property File:"
	writeOutput  " "
	writeOutput  "            ${SCRIPT}${ext} -encrypt property-file-path [-config deploy.properties] [-bypass \"string1,string2\""
	writeOutput  " "
	writeOutput  "            Example: ${SCRIPT}${ext} -encrypt ../resources/config/deploy.properties -config deploy.properties"
 	writeOutput  " "
	writeOutput  "               arg1::   -encrypt is used to encrypt the passwords in deploy.properties or a Module XML property file"
	writeOutput  "               arg2::   file path to deploy.properties or XML property file [full or relative path]"
	writeOutput  "               arg3-4:: [-config deploy.properties] optional parameter"
	writeOutput "                arg4-5:: [-bypass \"string1,string2\"] optional parameter specifying a quoted, comma-separated list of strings to bypass"
	writeOutput "                                                     that are found within a variable or XML element designated for passwords."
	writeOutput  " -----------------------------------------------------------------------------------------------------"
	writeOutput  " Option 4 - Execute an Ant build file:"
	writeOutput  " "
	writeOutput  "            ${SCRIPT}${ext} -ant build-file-path [-vcsuser username] [-vcspassword password] [-config deploy.properties] [-ver CIS_VERSION]"
	writeOutput  " "
	writeOutput  "            Example: ${SCRIPT}${ext} -ant ../resources/ant/build.xml -vcsuser user -vcspassword password -config deploy.properties"
	writeOutput  " "
	writeOutput  "               arg1::   -ant is used to execute an Ant build file"
	writeOutput  "               arg2::   orchestration build file path (full or relative path)"
	writeOutput  "               arg3-4:: [-vcsuser username] optional parameter"
	writeOutput  "               arg5-6:: [-vcspassword password] optional parameter"
	writeOutput  "               arg7-8:: [-config deploy.properties] optional parameter"
	writeOutput  "               arg9-10::[-ver CIS_VERSION] optional parameter e.g. 7.0.0, 8.0.0"
	writeOutput  " -----------------------------------------------------------------------------------------------------"
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
SCRIPT="ExecutePDTool"
SEP="::"
# Print out the Banner
writeOutput " " 																							"" $SEP -nodate
writeOutput "------------------------------------------------------------------" 							"" $SEP -nodate
writeOutput "-----------                                            -----------" 							"" $SEP -nodate
writeOutput "----------- TIBCO Professiobnal Services               -----------" 							"" $SEP -nodate
writeOutput "----------- PDTool: Promotion and Deployment Tool      -----------" 							"" $SEP -nodate
writeOutput "-----------                                            -----------" 							"" $SEP -nodate
writeOutput "------------------------------------------------------------------" 							"" $SEP -nodate
writeOutput " " 																							"" $SEP -nodate
writeOutput " " 																							"" $SEP -nodate
writeOutput "***** BEGIN COMMAND: ${SCRIPT} *****" 															$SCRIPT $SEP
writeOutput " " 																							"" $SEP -nodate
#=======================================
# Derive PROJECT_HOME from current dir
#=======================================
export CURDIR=`pwd`
cd ..
PROJECT_HOME="`pwd`"
export PROJECT_HOME=`pwd`
export PROJECT_HOME_PHYSICAL=`pwd`
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
writeOutput " " 	
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
DEPLOY_MANAGER="com.tibco.ps.deploytool.DeployManagerUtil"
DEPLOY_COMMON_UTIL="com.tibco.ps.common.scriptutil.ScriptUtil"
CONFIG_LOG4J="-Dlog4j.configuration=\"file:${PROJECT_HOME}/resources/config/log4j.properties\""
CONFIG_ROOT="-Dcom.tibco.ps.configroot=\"${PROJECT_HOME}/resources/config\""
#=======================================
# Parameter Parsing and Validation
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
    -exec)
		export CMD="-exec"
		export PROPERTY_FILE="$2"
		#echo "CMD=$CMD   DEPLOY_PLAN_FILE=$PROPERTY_FILE"
		ARG="DEPLOY_PLAN_FILE"
		if [ "$PROPERTY_FILE" == "" ]; then
			error="1"
		fi
		shift
		;;
    -ant)
		export CMD="-ant"
		export PROPERTY_FILE="$2"
		#echo "CMD=$CMD   PROPERTY_FILE=$PROPERTY_FILE"
		ARG="PROPERTY_FILE"
		if [ "$PROPERTY_FILE" == "" ]; then
			error="1"
		fi
		shift
		;;
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
    -bypass)
		export ENCRYPT_BYPASS_STRING="$2"
		ARG="ENCRYPT_BYPASS_STRING"
		#echo "ENCRYPT_BYPASS_STRING=$ENCRYPT_BYPASS_STRING"
		if [ "$ENCRYPT_BYPASS_STRING" == "" ]; then
			error="1"
		fi
		shift
		;;
    -release)
		export CMD="-release"
		export RELEASE_FOLDER="$2"
		#echo "CMD=$CMD   RELEASE_FOLDER=$RELEASE_FOLDER"
		ARG="RELEASE_FOLDER"
		if [ "$RELEASE_FOLDER" == "" ]; then
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
    -config)
		export CONFIG_PROPERTY_FILE="$2"
		ARG="CONFIG_PROPERTY_FILE"
		#echo "CONFIG_PROPERTY_FILE=$CONFIG_PROPERTY_FILE"
		if [ "$CONFIG_PROPERTY_FILE" == "" ]; then
			error="1"
		fi
		shift
		;;
    -ver)
		export CIS_VERSION="$2"
		ARG="CIS_VERSION"
		#echo "CIS_VERSION=$CIS_VERSION"
		if [ "$CIS_VERSION" == "" ]; then
			error="1"
		fi
		shift
		;;
    *) 
		# unknown option
		writeOutput " " 																					"" $SEP -nodate
		writeOutput "Execution Failed::Unknown parameter: $1"																	$SCRIPT $SEP
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

# Test to make sure the command is not blank
if [ "${CMD}" == "" ]; then
	writeOutput " " 																						"" $SEP -nodate
	writeOutput "Please provide a command [-exec|-vcsinit|-encrypt|-ant]. "									$SCRIPT $SEP
	writeOutput " " 																						"" $SEP -nodate
	usage 1
fi

# ==================================================
# Assign variables for execute
# ==================================================
if [ "${CMD}" == "-exec" ]; then
	writeOutput " " 																						"" $SEP -nodate
	writeOutput "------------------------------------------------------------------" 						"" $SEP -nodate
	writeOutput "-------------------- COMMAND-LINE DEPLOYMENT ---------------------" 						"" $SEP -nodate
	writeOutput "------------------------------------------------------------------" 						"" $SEP -nodate
	writeOutput " " 																						"" $SEP -nodate

	# Go to usage if the property file was missing
	ARG="DEPLOY_PLAN_FILE"
	PR_VCS_PASSWORD=""
	if [ "${VCS_PASSWORD}" != "" ]; then PR_VCS_PASSWORD="********"; fi
	if [ "${VCS_USERNAME}" == "" ]; then VCS_USERNAME=" "; fi
	if [ "${VCS_PASSWORD}" == "" ]; then VCS_PASSWORD=" "; fi
	if [ ! -f "${PROPERTY_FILE}" ]; then
		writeOutput " " 																					"" $SEP -nodate
		writeOutput "Execution Failed::Orchestration deploy plan file does not exist: ${PROPERTY_FILE}" 	$SCRIPT $SEP
		writeOutput " " 																					"" $SEP -nodate
	   usage $ARG
	fi
	#***********************************************
	# Invoke: DeployManagerUtil execCisDeployTool "${PROPERTY_FILE}" "${VCS_USERNAME}" "${VCS_PASSWORD}"
	#***********************************************
	JAVA_ACTION="execCisDeployTool"
	  COMMAND="\"$JAVA_HOME/bin/java\" ${JAVA_OPT} -classpath \"${DEPLOY_CLASSPATH}\" ${CONFIG_ROOT} ${CONFIG_LOG4J} -Djava.endorsed.dirs=\"${ENDORSED_DIR}\" -DPROJECT_HOME=\"${PROJECT_HOME}\" -DPROJECT_HOME_PHYSICAL=\"${PROJECT_HOME_PHYSICAL}\" -DCONFIG_PROPERTY_FILE=${CONFIG_PROPERTY_FILE} ${DEPLOY_MANAGER} ${JAVA_ACTION} \"${PROPERTY_FILE}\" \"${VCS_USERNAME}\" \"${VCS_PASSWORD}\""
	PRCOMMAND="\"$JAVA_HOME/bin/java\" ${JAVA_OPT} -classpath \"${DEPLOY_CLASSPATH}\" ${CONFIG_ROOT} ${CONFIG_LOG4J} -Djava.endorsed.dirs=\"${ENDORSED_DIR}\" -DPROJECT_HOME=\"${PROJECT_HOME}\" -DPROJECT_HOME_PHYSICAL=\"${PROJECT_HOME_PHYSICAL}\" -DCONFIG_PROPERTY_FILE=${CONFIG_PROPERTY_FILE} ${DEPLOY_MANAGER} ${JAVA_ACTION} \"${PROPERTY_FILE}\" \"${VCS_USERNAME}\" \"${PR_VCS_PASSWORD}\""
fi

# ==================================================
# Assign variables for init vcs workspace
# ==================================================
if [ "${CMD}" == "-vcsinit" ]; then
	writeOutput " " 																						"" $SEP -nodate
	writeOutput "------------------------------------------------------------------" 						"" $SEP -nodate
	writeOutput "------------------ COMMAND-LINE VCS INITIALIZE -------------------" 						"" $SEP -nodate
	writeOutput "------------------------------------------------------------------" 						"" $SEP -nodate
	writeOutput " " 																						"" $SEP -nodate

	PR_VCS_PASSWORD=""
	if [ "${VCS_PASSWORD}" != "" ]; then PR_VCS_PASSWORD="********"; fi
	if [ "${VCS_USERNAME}" == "" ]; then VCS_USERNAME=" "; fi
	if [ "${VCS_PASSWORD}" == "" ]; then VCS_PASSWORD=" "; fi

	#***********************************************
	# Invoke: DeployManagerUtil vcsInitWorkspace "${VCS_USERNAME}" "${VCS_PASSWORD}"
	#***********************************************
	JAVA_ACTION="vcsInitWorkspace"
	  COMMAND="\"$JAVA_HOME/bin/java\" ${JAVA_OPT} -classpath \"${DEPLOY_CLASSPATH}\" ${CONFIG_ROOT} ${CONFIG_LOG4J} -Djava.endorsed.dirs=\"${ENDORSED_DIR}\" -DPROJECT_HOME=\"${PROJECT_HOME}\" -DPROJECT_HOME_PHYSICAL=\"${PROJECT_HOME_PHYSICAL}\" -DCONFIG_PROPERTY_FILE=${CONFIG_PROPERTY_FILE} ${DEPLOY_MANAGER} ${JAVA_ACTION} \"${VCS_USERNAME}\" \"${VCS_PASSWORD}\""
	PRCOMMAND="\"$JAVA_HOME/bin/java\" ${JAVA_OPT} -classpath \"${DEPLOY_CLASSPATH}\" ${CONFIG_ROOT} ${CONFIG_LOG4J} -Djava.endorsed.dirs=\"${ENDORSED_DIR}\" -DPROJECT_HOME=\"${PROJECT_HOME}\" -DPROJECT_HOME_PHYSICAL=\"${PROJECT_HOME_PHYSICAL}\" -DCONFIG_PROPERTY_FILE=${CONFIG_PROPERTY_FILE} ${DEPLOY_MANAGER} ${JAVA_ACTION} \"${VCS_USERNAME}\" \"${PR_VCS_PASSWORD}\""
fi

# ==================================================
# Assign variables for encrypting a property file
# ==================================================
if [ "${CMD}" == "-encrypt" ]; then
	writeOutput " " 																						"" $SEP -nodate
	writeOutput "------------------------------------------------------------------" 						"" $SEP -nodate
	writeOutput "--------------------- COMMAND-LINE ENCRYPT -----------------------"  						"" $SEP -nodate
	writeOutput "------------------------------------------------------------------" 						"" $SEP -nodate
	writeOutput " " 																						"" $SEP -nodate

	ARG="PROPERTY_FILE"
	if [ ! -f "${PROPERTY_FILE}" ]; then
		writeOutput " " 																					"" $SEP -nodate
		writeOutput "Execution Failed::Property file does not exist: ${PROPERTY_FILE}" 						$SCRIPT $SEP
		writeOutput " " 																					"" $SEP -nodate
		usage $ARG
	fi
	#***********************************************
	# Invoke: ScriptUtil encryptPasswordsInFileBypass "${PROPERTY_FILE}" "${ENCRYPT_BYPASS_STRING}"
	#***********************************************
	JAVA_ACTION="encryptPasswordsInFileBypass"
	  COMMAND="\"$JAVA_HOME/bin/java\" ${JAVA_OPT} -classpath \"${DEPLOY_CLASSPATH}\" ${CONFIG_ROOT} ${CONFIG_LOG4J} -Djava.endorsed.dirs=\"${ENDORSED_DIR}\" -DPROJECT_HOME=\"${PROJECT_HOME}\" -DPROJECT_HOME_PHYSICAL=\"${PROJECT_HOME_PHYSICAL}\" -DCONFIG_PROPERTY_FILE=${CONFIG_PROPERTY_FILE} ${DEPLOY_COMMON_UTIL} ${JAVA_ACTION} \"${PROPERTY_FILE}\" \"${ENCRYPT_BYPASS_STRING}\""
	PRCOMMAND="${COMMAND}"
fi

# ==================================================
# Assign variables for invoking an ant build file
# ==================================================
if [ "${CMD}" == "-ant" ]; then
	writeOutput " " 																						"" $SEP -nodate
	writeOutput "------------------------------------------------------------------" 						"" $SEP -nodate
	writeOutput "------------------------- ANT DEPLOYMENT -------------------------" 						"" $SEP -nodate
	writeOutput "------------------------------------------------------------------" 						"" $SEP -nodate
	writeOutput " " 																						"" $SEP -nodate

	ARG="DEPLOY_BUILD_FILE"
	PR_VCS_PASSWORD=""
	if [ "${VCS_PASSWORD}" != "" ]; then PR_VCS_PASSWORD="********"; fi
	if [ "${VCS_USERNAME}" == "" ]; then VCS_USERNAME=" "; fi
	if [ "${VCS_PASSWORD}" == "" ]; then VCS_PASSWORD=" "; fi

	if [ ! -f "${PROPERTY_FILE}" ]; then
		writeOutput " " 																					"" $SEP -nodate
		writeOutput "Execution Failed::Ant deploy plan build file does not exist: ${PROPERTY_FILE}" 		$SCRIPT $SEP
		writeOutput " " 																					"" $SEP -nodate
	   usage $ARG
	fi
	
	#=======================================
	# Derive ANT_HOME from PROJECT_HOME
	#=======================================
	# Ant ships with the CisDeployTool so the directory is an offset to the Project Home
	export ANT_HOME="$PROJECT_HOME/ext/ant"
	if [ ! -d "$ANT_HOME" ]; then
		writeOutput " " 																					"" $SEP -nodate
		writeOutput "Execution Failed::ANT_HOME does not exist: $ANT_HOME" 									$SCRIPT $SEP
		writeOutput " " 																					"" $SEP -nodate
		exit 1
	fi
	#ANT_CP="../lib../dist" - original - why no colons?
	# Set the Ant classpath and options
	ANT_CLASSPATH="${PROJECT_HOME}/dist:${PROJECT_HOME}/lib:${PROJECT_HOME}/lib/endorsed:${ANT_HOME}/lib"
	export ANT_OPTS="$CONFIG_LOG4J $CONFIG_ROOT -Djava.endorsed.dirs=\"${ENDORSED_DIR}\""
	
	#***********************************************
	# Invoke: ant -buildfile
	#***********************************************
	  COMMAND="\"${ANT_HOME}/bin/ant\" -lib \"${ANT_CLASSPATH}\" -DPROJECT_HOME=\"${PROJECT_HOME}\" -DPROJECT_HOME_PHYSICAL=\"${PROJECT_HOME_PHYSICAL}\" -DCONFIG_PROPERTY_FILE=${CONFIG_PROPERTY_FILE} -DVCS_USERNAME=\"${VCS_USERNAME}\" -DVCS_PASSWORD=\"${VCS_PASSWORD}\" -buildfile \"${PROPERTY_FILE}\" "

	PRCOMMAND="\"${ANT_HOME}/bin/ant\" -lib \"${ANT_CLASSPATH}\" -DPROJECT_HOME=\"${PROJECT_HOME}\" -DPROJECT_HOME_PHYSICAL=\"${PROJECT_HOME_PHYSICAL}\" -DCONFIG_PROPERTY_FILE=${CONFIG_PROPERTY_FILE} -DVCS_USERNAME=\"${VCS_USERNAME}\" -DVCS_PASSWORD=\"${PR_VCS_PASSWORD}\" -buildfile \"${PROPERTY_FILE}\" "
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
    writeOutput "Execution Failed::Script ${SCRIPT} Failed. Abnormal Script Termination. Exit code is: ${ERROR}"				$SCRIPT $SEP
    exit ${ERROR}
fi

#=======================================
# Successful script completion
#=======================================
writeOutput " " 																							"" $SEP -nodate
writeOutput "-------------- SUCCESSFUL SCRIPT COMPLETION [${SCRIPT} ${CMD}] --------------" 				$SCRIPT $SEP
writeOutput "End of script."																				$SCRIPT $SEP
exit 0															