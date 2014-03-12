#!/bin/bash
######################################################################
# (c) 2014 Cisco and/or its affiliates. All rights reserved.
######################################################################
#  #=======================================================================================
#  # Example Execution Statement:
#  # checkin.bat [CIS-resource-path] [CIS-resource-type] [message] [vcs-workspace-project-folder] [vcs-temp-folder]
#  # 
#  # Parameters
#  # ----------
#  # %1 ->  Resource path 			(e.g. /shared/MyFolder/My__View), using file system (encoded) names
#  # %2 ->  Resource type 			(e.g. FOLDER, table, procedure etc.)
#  # %3 ->  Checkin message 		(e.g. Adding MyFolder)
#  # %4 ->  VCS Workspace Folder 	(e.g. C:\Temp\workspaces\workspace_CIS)
#  # %5 ->  VCS Temp Folder 		(e.g. C:\Temp\workspaces\temp_CIS)
#  #=======================================================================================
#  #------------------------------------------
#  # Set constants
#  #------------------------------------------
SCRIPT1=checkin.sh
PREFIX1=$SCRIPT1::execute::
PREFIX1_ERR=ERROR::$SCRIPT1::

# Print out the Banner
echo "" 
echo "---------------------------------------------------------"
echo "---                                                   ---"
echo "--- Composite PS Promotion and Deployment Tool Studio ---"
echo "---                                                   ---"
echo "---------------------------------------------------------"
echo "" 

#  #---------------------------------------------
#  # Set environment variables
#  #---------------------------------------------
if [ ! -f setVars.sh ]; then
   echo "Cannot find setVars.sh environment variable file."
   exit 1
fi
. ./setVars.sh
#  #=======================================
#  # Derive PROJECT_HOME from current dir
#  #=======================================
CURDIR=`pwd`
cd ..
PROJECT_HOME=`pwd`
cd $CURDIR
#  #=======================================
#  # Validate Paths exist
#  #=======================================
if [[ !  -d "$PROJECT_HOME" ]]; then
	echo "Execution Failed::PROJECT_HOME does not exist: $PROJECT_HOME"
	exit 1
fi
if [[ !  -d "$JAVA_HOME" ]]; then
        echo $JAVA_HOME
	echo "Execution Failed::JAVA_HOME does not exist: $JAVA_HOME"
	exit 1
fi
echo "PROJECT_HOME=$PROJECT_HOME"
echo "JAVA_HOME=$JAVA_HOME"
#=======================================
# Display Licenses
#=======================================
echo " "
echo "------------------------------------------------------------------"
echo "------------------------ PD Tool Licenses ------------------------"
echo "------------------------------------------------------------------"
cat "${PROJECT_HOME}/licenses/Composite_License.txt"
echo " "
cat "${PROJECT_HOME}/licenses/Project_Specific_License.txt"
echo " "

#  #=======================================
#  # Set DeployManager Environment Variables
#  #=======================================
DEPLOY_CLASSPATH="$PROJECT_HOME/dist/*:$PROJECT_HOME/lib/*"
ENDORSED_DIR="${PROJECT_HOME}/lib/endorsed"
DEPLOY_MANAGER=com.cisco.dvbu.ps.deploytool.DeployManagerUtil
CONFIG_LOG4J="-Dlog4j.configuration=file:$PROJECT_HOME/resources/config/log4j.properties" 
CONFIG_ROOT="-Dcom.cisco.dvbu.ps.configroot=$PROJECT_HOME/resources/config"
#  #------------------------------------------
#  # Set incoming arguments
#  #------------------------------------------
ResPath=$1
ResType=$2
Message=$3
Workspace=$4
VcsTemp=$5
#  #---------------------------------------------
#  # Display Arguments
#  #---------------------------------------------
echo "......................................"
echo "$SCRIPT1 Arguments:"
echo "......................................"
echo "Script Path=$0"
echo "Resource Path=$ResPath"
echo "Resource Type=$ResType"
echo "Checkin Msg=$Message"
echo "Workspace=$Workspace"
echo "Temp Folder=$VcsTemp"
#  #------------------------------------------
#  # Perform argument validations
#  #------------------------------------------
echo "......................................"
echo "Perform argument validations:"
echo "......................................"
if [ ! -n "$ResPath" ]; then
echo "$PREFIX1_ERR ResPath has no value. Error=111."
   exit  111
fi
if [ ! -n "$ResType" ]; then
echo "$PREFIX1_ERR ResType has no value. Error=112."
  exit 112
fi
if [ ! -n "$Workspace" ]; then
echo "$PREFIX1_ERR Workspace has no value. Error=113."
   exit 113
fi
if [ ! -n "$VcsTemp" ]; then
echo "$PREFIX1_ERR VcsTemp has no value. Error=115."
   exit 115
fi
echo "Arguments are valid."
#  #---------------------------------------------
#  # Begin Processing
#  #---------------------------------------------
#  #***********************************************
#  # Invoke: DeployManagerUtil vcsStudioCheckin
#  #***********************************************
JAVA_ACTION=vcsStudioCheckin
COMMAND="\"${JAVA_HOME}\"/bin/java ${JAVA_OPT} -classpath \"${DEPLOY_CLASSPATH}\" ${CONFIG_ROOT} ${CONFIG_LOG4J} -Djava.endorsed.dirs=\"${ENDORSED_DIR}\" -DPROJECT_HOME=\"${PROJECT_HOME}\" -DCONFIG_PROPERTY_FILE=\"${CONFIG_PROPERTY_FILE}\" ${DEPLOY_MANAGER} ${JAVA_ACTION} \"${ResPath}\" \"${ResType}\" \"${Message}\" \"${Workspace}\" \"${VcsTemp}\""
#  #=======================================
#  # Execute the script
#  #=======================================
#  Escape (") in the COMMAND with ("") before printing
echo "$PREFIX1 "
echo "$PREFIX1-- COMMAND: $JAVA_ACTION ----------------------"
echo "$PREFIX1 "
echo "$COMMAND "
echo "$PREFIX1 "
echo "$PREFIX1-- BEGIN OUTPUT ------------------------------------"
echo "$PREFIX1 "
#  #------------------------------------------------------------------------
#  # Execute the command line CIS script
#  #------------------------------------------------------------------------
eval ${COMMAND}
ERROR=$?
if [ $ERROR -ne 0 ]; then
echo "$PREFIX1_ERR Script $SCRIPT Failed. Abnormal Script Termination. Exit code is: $ERROR.		"
   exit $ERROR
fi
#  #=======================================
#  # Successful script completion
#  #=======================================
echo "$PREFIX1 "
echo "$PREFIX1-------------- SUCCESSFUL SCRIPT COMPLETION [$SCRIPT $COMMAND] --------------"
echo "$PREFIX1 End of script." 
exit 0

