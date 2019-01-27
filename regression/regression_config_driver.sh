#!/bin/sh
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
##############################################################################################
# script_name: regression_config_driver.sh
#
# author:      Mike Tinius
# date:        February 15, 2013
# description: The scripts in the directory are for executing regression tests against PDTool
#              and all of the various environment combinations including windows and unix and the various 
#              VCS software.  The regression script folder (/regression) may be placed anywhere on your
#              computer as long as the regressioin_confi_driver.bat|.sh is executed from within the 
#              regression directory.  This allows you to have one copy of the regression scripts and perform
#              a regression against any PDTool directory since the PDTool home dir is passed into the script.
#instructions:
#			  This script must be executed from the PDTool regression directory
#			  1. cd /regression
#             2. ./regression_config_driver.sh [regression_config_unix.txt] "[PDTOOL_HOME]" [Y|N]
#                  param 1: Regression configuration list file.  Associates a PDTool config file with a plan file list.
#                           Review regression/config_lists for parameter 1
#                  param 2: Enclose the full path of PDTool Home dir in double quotes
#                  param 3: Debug parameter is Y or N.  If nothing is provided the default is N
#
##############################################################################################
#
# Retrieve input variables and remove double quotes
SCRIPT_NAME=$0
CONFIG_FILE_LIST=$1
PDTOOL_HOME=$2
DEBUG=$3
export INP_VERSION=$4

#########################
# FUNCTION: debug()
#   print a debug message
##########################
debug(){
  # print the message
  if [ "${DEBUG}" = "Y" ]; then
    echo "DEBUG:: $1"
  fi
  # $2=echo blank lines
  c=$2
  x=0
  if [ "${c}" != "" ]; then
    while [ ${x} -lt ${c} ]
    do
      echo ""
      x="`expr ${x} + 1`"
    done
  fi
}

#########################
# FUNCTION: usage()
#   print a usage message
##########################
usage() {
  param=$1
  msg=$2
  echo ""
  echo "usage: ${SCRIPT_NAME} [regression_config_list.txt] [PDTOOL_HOME] [DEBUG=Y|N] [INP_VERSION]"
  echo "   Missing parameter: $param"
  echo "$msg"
  echo ""
  echo "   e.g. ./regression_config_driver.sh regression_win_8.2_config.txt \"/u01/qa/home/PDTool6.2\" Y 6.2"
  echo "TRUE" > status_exit_${INP_VERSION}.txt
  exit 2
}

#########################
# FUNCTION: logDate()
#   log the date
##########################
logDate() {
  _LOG_PATH=$1
  _TEXT=$2
  echo "`date +%Y-%m-%d-%H:%M:%S`${_TEXT}" >> "${_LOG_PATH}"
}

#########################
# FUNCTION: generateFileName()
#   generate a file name with a date
##########################
generateFileName() {
# Generate a unique file name with a date and timestamp
# PREPEND=Prepend the date/time to the file name
# POSTPEND=Postpend the date/time to the file name
# Syntax: CALL :generateFileName [PREPEND|POSTPEND] baseFileName ext OUT_VAR_NAME
# Example: CALL :generateFileName PREPEND file txt FILE_NAME
  append=$1
  baseFileName=$2
  extension=$3
  DATE_TIME="`date +%Y%m%d_%H%M%S`"
  # Default is to POSTPEND date/time
  if [ "${append}" = "PREPEND" ]; then 
    FILE_NAME="${DATE_TIME}_${baseFileName}.${extension}"
  else
    FILE_NAME="${baseFileName}_${DATE_TIME}.${extension}"
  fi
  debug "generateFileName: FILE_NAME=${FILE_NAME}" 0
}

#########################
# FUNCTION: getBaseFileName()
#   get the base file name
##########################
getBaseFileName() {
# -------------------------------------------------------------
# Get the base file name for a file like file.txt
# Syntax: CALL :getBaseFileName filename OUT_VAR_NAME
# Example: CALL :generateFileName file.txt FILE_NAME
  FILE_NAME=$1
  IDX="`expr index ${FILE_NAME} "."`"
  if [ ${IDX} -gt 0 ]; then
    IDX="`expr ${IDX} - 1`"
    FILE_NAME="${FILE_NAME:0:$IDX}"
  fi
  debug "getBaseFileName: FILE_NAME=${FILE_NAME}" 0
}

#########################
# BEGIN MAIN
##########################
# Set regresion environment variables
REGRESSION_HOME="`pwd`"
REGRESSION_CONFIG="${REGRESSION_HOME}/config"
REGRESSION_PLANS="${REGRESSION_HOME}/plans"
REGRESSION_CONFIG_LISTS="${REGRESSION_HOME}/config_lists"

# Validate the input parameters
if [ "${CONFIG_FILE_LIST}" = "" ]; then 
  usage "CONFIG_FILE_LIST" "   Provide a regression list of deployment plan files"
fi
if [ ! -f "${REGRESSION_CONFIG_LISTS}/${CONFIG_FILE_LIST}" ]; then
  echo ""
  echo "The regression config list file that was provided does not exist: ${CONFIG_FILE_LIST}"
  echo "Regression configuration list directory: ${REGRESSION_CONFIG_LISTS}"
  exit 2
fi

if [ "${PDTOOL_HOME}" = "" ]; then 
  usage "PDTOOL_HOME" "   Provide the PDTOOL_HOME directory in quotes." 
fi
if [ ! -d "${PDTOOL_HOME}" ]; then
  echo "The PDTool Home directory does not exist: ${PDTOOL_HOME}"
  exit 2
fi

if [ "${DEBUG}" = "" ]; then DEBUG="N"; fi

if [ "${INP_VERSION}" = "" ]; then 
  usage "INP_VERSION" "   Provide the input version INP_VERSION." 
  exit 2
fi

# Set PDTOOL_HOME environment variables
PDTOOL_CONFIG="${PDTOOL_HOME}/resources/config"

debug "${SCRIPT_NAME}: input: CONFIG_FILE_LIST=${CONFIG_FILE_LIST}" 0
debug "${SCRIPT_NAME}: input: PDTOOL_HOME=${PDTOOL_HOME}" 0
debug "${SCRIPT_NAME}: input: DEBUG=${DEBUG}" 0
debug "${SCRIPT_NAME}: REGRESSION_HOME=${REGRESSION_HOME}" 0
debug "${SCRIPT_NAME}: REGRESSION_CONFIG=${REGRESSION_CONFIG}" 0
debug "${SCRIPT_NAME}: REGRESSION_PLANS=${REGRESSION_PLANS}" 0
debug "${SCRIPT_NAME}: REGRESSION_CONFIG_LISTS=${REGRESSION_CONFIG_LISTS}" 0
debug "${SCRIPT_NAME}: PDTOOL_CONFIG=${PDTOOL_CONFIG}" 0

# Initialize the log file
getBaseFileName "${CONFIG_FILE_LIST}"
BASE_FILE_NAME="${FILE_NAME}"
generateFileName "PREPEND" "${BASE_FILE_NAME}" "log"
LOG_NAME="${FILE_NAME}"
LOG_HOME="${REGRESSION_HOME}/logs/${BASE_FILE_NAME}"
if [ ! -d "${LOG_HOME}" ]; then
  mkdir -p "${LOG_HOME}"
fi
LOG_PATH="${LOG_HOME}/${LOG_NAME}"

# Provide instruction on how to access the current log
echo ""
echo "########################################################################################################################################"
echo ""
echo "To view the log path use this command: "
echo "--------------------------------------"
echo ""
echo "tail -f -n 500 \"${LOG_PATH}\""
echo ""
echo "########################################################################################################################################"
echo ""

# Begin writing to the log file.
echo "Regression Log File Results" > "${LOG_PATH}"
logDate "${LOG_PATH}" ""
echo "---------------------------" >> "${LOG_PATH}"
echo "" >> "${LOG_PATH}"

STATUS_CONFIG_OVERALL="PASS"
STATUS_EXIT="FALSE"
# directory where status files are stored temporarily
STATUS_DIR="status" 
if [ ! -d ${STATUS_DIR} ]; then
  mkdir -p ${STATUS_DIR}
fi
echo "${STATUS_CONFIG_OVERALL}" > ${STATUS_DIR}/status_config_overall_${INP_VERSION}.txt
echo "${STATUS_EXIT}" > ${STATUS_DIR}/status_exit_${INP_VERSION}.txt
TOTAL_PLANS=0

# Read the file
IFS="\n"
while read fileline
do
  # Remove leading/trailing white space
  filelineTrim="`echo ${fileline} | sed 's/^ *//g'`"
  # Test for comment (#) in 1st character or blank line
  CHAR1="`echo ${filelineTrim} | cut -b 0-1`"
  proceed="Y"
  if [ "${CHAR1}" == "#" ]; then proceed="N"; fi
  if [ "${filelineTrim}" == "" ]; then proceed="N"; fi

  # Proceed if not a comment or blank line
  if [ "${proceed}" == "Y" ]; then
    c=0
    IFS=" "
    for word in ${fileline}
    do
      c="`expr ${c} + 1`"
      if [ ${c} -eq 1 ]; then
          CONFIG_FILE="${word}"
      fi
      if [ ${c} -eq 2 ]; then
          PLAN_FILE_LIST="${word}"
      fi
    done

    echo ""
    echo "###########################################################################"
    echo "Execute: "
    echo "   Config file Name = ${CONFIG_FILE}.properties"
    echo "     Plan file list = ${PLAN_FILE_LIST}"
    echo "###########################################################################"
    echo "" 

    STATUS_PLAN_OVERALL="PASS"
    echo "${STATUS_PLAN_OVERALL}" > ${STATUS_DIR}/status_plan_overall_${INP_VERSION}.txt

    # Execute the plan driver script: regression_plan_driver.sh [CONFIG_FILE]  [PLAN_FILE_LIST]  [PDTOOL_HOME]  [REGRESSION_HOME]  [LOG_PATH] [STATUS_DIR] [LOG_HOME] 
	debug "${SCRIPT_NAME}: ./regression_plan_driver.sh \"${CONFIG_FILE}\" \"${PLAN_FILE_LIST}\" \"${PDTOOL_HOME}\" \"${REGRESSION_HOME}\" \"${LOG_PATH}\" \"${LOG_HOME}\" \"${STATUS_DIR}\" \"${DEBUG}\"" 1
    ./regression_plan_driver.sh "${CONFIG_FILE}" "${PLAN_FILE_LIST}" "${PDTOOL_HOME}" "${REGRESSION_HOME}" "${LOG_PATH}" "${LOG_HOME}" "${STATUS_DIR}" "${DEBUG}"

	# Add up the total number of plans executed
	NUM_PLANS="`cat ${STATUS_DIR}/num_plans_${INP_VERSION}.txt`"
	TOTAL_PLANS="`expr ${TOTAL_PLANS} + ${NUM_PLANS}`"

    debug "" 0
    STATUS_PLAN_OVERALL="`cat ${STATUS_DIR}/status_plan_overall_${INP_VERSION}.txt`"
    debug "${SCRIPT_NAME}: STATUS_PLAN_OVERALL=${STATUS_PLAN_OVERALL}" 0

    # Check for exit status
    STATUS_EXIT="`cat ${STATUS_DIR}/status_exit_${INP_VERSION}.txt`"
    debug "${SCRIPT_NAME}: STATUS_EXIT=${STATUS_EXIT}" 0
    if [ "${STATUS_EXIT}" = "TRUE" ]; then
	  break
    fi
  fi
  IFS="\n"
done < "${REGRESSION_CONFIG_LISTS}/${CONFIG_FILE_LIST}"

# Output the end date/time
echo "-----------------------------------------------------" >> "${LOG_PATH}"
logDate "${LOG_PATH}" "  Total Plans Executed: ${TOTAL_PLANS}"

# Overall status for this regression
STATUS_CONFIG_OVERALL="`cat ${STATUS_DIR}/status_config_overall_${INP_VERSION}.txt`"
debug "${SCRIPT_NAME}: OVERALL CONFIG STATUS=${STATUS_CONFIG_OVERALL}" 0
echo "${STATUS_CONFIG_OVERALL}: Overall status for this regression" >> "${LOG_PATH}"

# Clean up status files...delete them
if [ -f ${STATUS_DIR}/status_exit_${INP_VERSION}.txt ]; then rm -rf ${STATUS_DIR}/status_exit_${INP_VERSION}.txt; fi
if [ -f ${STATUS_DIR}/status_config_overall_${INP_VERSION}.txt ]; then rm -rf ${STATUS_DIR}/status_config_overall_${INP_VERSION}.txt; fi
if [ -f ${STATUS_DIR}/status_plan_overall_${INP_VERSION}.txt ]; then rm -rf ${STATUS_DIR}/status_plan_overall_${INP_VERSION}.txt; fi
if [ -f ${STATUS_DIR}/num_plans_${INP_VERSION}.txt ]; then rm -rf ${STATUS_DIR}/num_plans_${INP_VERSION}.txt; fi

# Provide instruction on how to access the current log
echo ""
echo "########################################################################################################################################"
echo "`date +%Y-%m-%d-%H:%M:%S`  Total Plans Executed: ${TOTAL_PLANS}"
echo ""
echo "To view the log path use this command: "
echo "--------------------------------------"
echo ""
echo "tail -f -n 500 \"${LOG_PATH}\""
echo ""
echo "########################################################################################################################################"
echo ""

# Provide an exit code for programs that may invoke this script
if [ "${STATUS_CONFIG_OVERALL}" = "PASS" ]; then
  exit 0
else
  exit 1
fi
