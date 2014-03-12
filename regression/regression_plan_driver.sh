#!/bin/sh
######################################################################
# (c) 2014 Cisco and/or its affiliates. All rights reserved.
######################################################################
##############################################################################################
# script_name: regression_plan_driver.sh
#
# author:      Mike Tinius
# date:        February 15, 2013
# description: This script gets invoked by regression_config_driver.bat|.sh.
#              The purpose of this script is to execute the plan files found in the /plan_lists directory.
#              The regression_config_driver associates a PDTool config file with a plan file list.
#              This script loops through the plan file list and executes each PDTool deployment plan
#              and logs the PASS/FAIL results into a log file that can be quickly inspected for overall
#              regression results.
# instructions:
#             Invoked by regression_config_driver.bat|.sh
#             1. Invoked from the regression directory
#             2. regression_plan_driver.sh [CONFIG_FILE]  [PLAN_FILE_LIST]  [PDTOOL_HOME]  [REGRESSION_HOME]  [LOG_PATH] [LOG_HOME] [STATUS_DIR] [DEBUG]
#                  param 1: PDTool configuration property file.
#                  param 2: Regression plan file list.  This file contains a list of deployment plans to execute.
#                  param 3: The PDTool home directory.  Can be either PDTool61 or PDTool62.
#                  param 4: The Regression home directory.
#                  param 5: The full path to the log file.
#                  param 6: The Log home directory.
#                  param 7: The name of the status directory "status".
#                  param 8: A debug indicator which is either Y or N.
#
##############################################################################################
SCRIPT_NAME=$0

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
  echo "usage: ${SCRIPT_NAME} [CONFIG_FILE]  [PLAN_FILE_LIST]  [PDTOOL_HOME]  [REGRESSION_HOME]  [LOG_PATH] [DEBUG]"
  echo "   Missing parameter: $1"
  echo "TRUE" > status/status_exit.txt
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
# BEGIN MAIN
##########################
# Get the parameters and remove surrounding quotes
CONFIG_FILE=$1
if [ "${CONFIG_FILE}" = "" ]; then usage "CONFIG_FILE"; fi

PLAN_FILE_LIST=$2
if [ "${PLAN_FILE_LIST}" = "" ]; then usage "PLAN_FILE_LIST"; fi

PDTOOL_HOME=$3
if [ "${PDTOOL_HOME}" = "" ]; then usage "PDTOOL_HOME"; fi

export REGRESSION_HOME=$4
if [ "${REGRESSION_HOME}" = "" ]; then usage "REGRESSION_HOME"; fi

LOG_PATH=$5
if [ "${LOG_PATH}" = "" ]; then usage "LOG_PATH"; fi

LOG_HOME=$6
if [ "${LOG_HOME}" = "" ]; then usage "LOG_HOME"; fi

STATUS_DIR=$7
if [ "${STATUS_DIR}" = "" ]; then usage "STATUS_DIR"; fi

DEBUG=$8
if [ "${LOG_HOME}" = "" ]; then DEBUG="N"; fi

# Set environment variables
CONFIG_FILE_PROP="${CONFIG_FILE}.properties"
REGRESSION_CONFIG="${REGRESSION_HOME}/config"
REGRESSION_PLANS="${REGRESSION_HOME}/plans"
REGRESSION_PLAN_LISTS="${REGRESSION_HOME}/plan_lists"
PDTOOL_CONFIG="${PDTOOL_HOME}/resources/config"

# Check existence of files and directories
if [ ! -d "${REGRESSION_HOME}" ]; then
  echo "The regression home directory that was provided does not exist: ${REGRESSION_HOME}"
  echo "TRUE" > ${STATUS_DIR}/status_exit.txt
  exit 3
fi
if [ ! -d "${PDTOOL_HOME}" ]; then
  echo "The PDtool home directory that was provided does not exist: ${PDTOOL_HOME}"
  echo "TRUE" > ${STATUS_DIR}/status_exit.txt
  exit 3
fi
if [ ! -f "${REGRESSION_CONFIG}/${CONFIG_FILE_PROP}" ]; then
  echo "The PDTool configuration file that was provided does not exist: ${CONFIG_FILE_PROP}"
  echo "PDTool regression config directory: ${REGRESSION_CONFIG}"
  echo "TRUE" > ${STATUS_DIR}/status_exit.txt
  exit 3
fi
if [ ! -f "${REGRESSION_PLAN_LISTS}/${PLAN_FILE_LIST}" ]; then
  echo "The regression list file that was provided does not exist: ${PLAN_FILE_LIST}"
  echo "PDTool regression plan list directory: ${REGRESSION_PLAN_LISTS}"
  echo "TRUE" > ${STATUS_DIR}/status_exit.txt
  exit 3
fi
if [ ! -f "${LOG_PATH}" ]; then
  echo "The regression log file that was provided does not exist: ${LOG_PATH}"
  echo "TRUE" > ${STATUS_DIR}/status_exit.txt
  exit 3
fi
if [ ! -d "${REGRESSION_PLANS}" ]; then
  echo "The regression deployment plans directory does not exist: ${REGRESSION_PLANS}"
  echo "TRUE" > ${STATUS_DIR}/status_exit.txt
  exit 3
fi
if [ ! -d "${LOG_HOME}" ]; then
  echo "The regression log file that was provided does not exist: ${LOG_HOME}"
  echo "TRUE" > ${STATUS_DIR}/status_exit.txt
  exit 3
fi

cd "${REGRESSION_HOME}"

# Save the current log file
if [ ! -d "${PDTOOL_HOME}/logs" ]; then
  mkdir -p "${PDTOOL_HOME}/logs"
fi
if [ -f "${PDTOOL_HOME}/logs/app.log" ]; then
  cp -f "${PDTOOL_HOME}/logs/app.log"  "${PDTOOL_HOME}/logs/app.log.sav"
fi

# Copy the regression configuration file to PDTool/config
cp -f "${REGRESSION_CONFIG}/${CONFIG_FILE_PROP}" "${PDTOOL_CONFIG}"

# Initialize the log file for this configuration execution
echo "-----------------------------------------------------" >> "${LOG_PATH}"
echo "Regression Test for ${CONFIG_FILE_PROP}" >> "${LOG_PATH}"
logDate "${LOG_PATH}" ""
echo "-----------------------------------------------------" >> "${LOG_PATH}"

STATUS_PLAN_OVERALL="`cat ${STATUS_DIR}/status_plan_overall.txt`"
STATUS_EXIT="`cat ${STATUS_DIR}/status_exit.txt`"
debug "${SCRIPT_NAME}: STATUS_PLAN_OVERALL=${STATUS_PLAN_OVERALL}" 0
debug "${SCRIPT_NAME}: STATUS_EXIT=${STATUS_EXIT}" 0

NUM_PLANS=0
echo "${NUM_PLANS}">${STATUS_DIR}/num_plans.txt

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
          planFile="${word}"
      fi
    done

	# Execute the plan file using PDTool ExecutePDTool.sh script
    echo ""
    echo "###########################################################################"
    echo "Execute: "
    echo "   Plan file Name = ${planFile}"
    echo "###########################################################################"
    echo ""
    # Initialize the PDTool log file (app.log)
    echo "" > "${PDTOOL_HOME}/logs/app.log"
	
    PLAN_FILE_PATH="${REGRESSION_PLANS}/${planFile}"
	
    if [ -f "${PLAN_FILE_PATH}" ]; then
	  CURR_DIR="`pwd`"
	  cd "${PDTOOL_HOME}/bin"
	  echo "./ExecutePDTool.sh -exec \"${PLAN_FILE_PATH}\" -config ${CONFIG_FILE_PROP}"
	  echo ""
	  ./ExecutePDTool.sh -exec "${PLAN_FILE_PATH}" -config ${CONFIG_FILE_PROP}
	  ERROR=$?
	  if [ $ERROR -ne 0 ]; then
		ret="FAIL"
	  else
		ret="PASS"
	  fi
	  
	  # Increment the number of plans executed by 1
	  NUM_PLANS="`expr ${NUM_PLANS} + 1`"

	  cd "${CURR_DIR}"
	  MSG="${ret}: ${PLAN_FILE_PATH}"
	  echo "$MSG" >> "${LOG_PATH}"
	  debug "" 0
	  debug "${SCRIPT_NAME}: $MSG" 0
	  if [ "${ret}" = "FAIL"  ]; then 
	    echo "${ret}" > ${STATUS_DIR}/status_plan_overall.txt
	    echo "${ret}" > ${STATUS_DIR}/status_config_overall.txt
	  fi
    else
	  MSG="FAIL: Plan file does not exist at location: ${PLAN_FILE_PATH}"
	  echo "$MSG" >> "${LOG_PATH}"
      echo "$MSG" >> "${PDTOOL_HOME}/logs/app.log"
	  echo "$MSG"
      STATUS_EXIT="TRUE"
	  echo "${STATUS_EXIT}" > ${STATUS_DIR}/status_exit.txt
	  STATUS_PLAN_OVERALL="FAIL"
      echo "${STATUS_PLAN_OVERALL}" > ${STATUS_DIR}/status_plan_overall.txt
      echo "${STATUS_PLAN_OVERALL}" > ${STATUS_DIR}/status_config_overall.txt
    fi

    # Copy the PDTool log (app.log) to the specified plan file log
    cp -f "${PDTOOL_HOME}/logs/app.log"  "${LOG_HOME}/${CONFIG_FILE}-${planFile}.log"
  
    # Check for exit status
    if [ "${STATUS_EXIT}" = "TRUE" ]; then 
	  break; 
	fi
  fi
  IFS="\n"
done < "${REGRESSION_PLAN_LISTS}/${PLAN_FILE_LIST}"
  
echo "${NUM_PLANS}">${STATUS_DIR}/num_plans.txt

# Output the end date/time
echo "-----------------------------------------------------" >> "${LOG_PATH}"
logDate "${LOG_PATH}" "  Number Plans Executed: ${NUM_PLANS}"

# Overall status for this configuration and plan file list
STATUS_PLAN_OVERALL="`cat ${STATUS_DIR}/status_plan_overall.txt`"
debug "${SCRIPT_NAME}: STATUS_PLAN_OVERALL=${STATUS_PLAN_OVERALL}" 0
echo "${STATUS_PLAN_OVERALL}: Overall status for ${CONFIG_FILE_PROP} and ${PLAN_FILE_LIST}" >> "${LOG_PATH}"
echo "-----------------------------------------------------" >> "${LOG_PATH}"
echo "" >> "${LOG_PATH}"

# Restore the current log file
mv "${PDTOOL_HOME}/logs/app.log.sav"  "${PDTOOL_HOME}/logs/app.log"

if [ "${STATUS_PLAN_OVERALL}" = "PASS" ]; then
  exit 0
else
  exit 1
fi
