#!/bin/bash
############################################################################################################################
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
############################################################################################################################
#
#==========================================================
# FUNCTIONS
#==========================================================
#---------------------------------------------
# Return a printable password. 
# If encrypted print as is. 
# If not encryped return ******** for printing.
#---------------------------------------------
printablePassword() {
    pswd="$1"
    if [ "$pswd" != "" ]; then
        if [[ "$pswdSubstr" == *"Encrypted:"* ]]; then
            echo "$pswd"
        else
            echo "********"
        fi
    else
        echo ""
    fi
}
#==========================================================
# END FUNCTIONS
#==========================================================
#
SV="setMyPostPDToolVars.sh"
PRINT_VARS="1"
#==========================================================
# setMyPostPDToolVars.sh :: Set Environment Variables
#==========================================================
# This file gets invoked by setVars.sh after setVars.sh has been executed.
# Use cases for post-processing:
#   1. Post-processing is generally only used in conjunction with changing the order of precendence in which variables are retrieved.
#         The default "propertyOrderPrecedence=JVM PROPERTY_FILE SYSTEM"
#         In some circumstances it may be necessary to override the default "propertyOrderPrecedence=SYSTEM JVM PROPERTY_FILE" in order to
#         retrieve variables from the files system "SYSTEM" first and ignore the variables set in the /config/*.properties or /modules/*.xml files.
#         This allows the users of PDTool to rewrite a variable that already exists in .properties or .xml files.
#
# Instructions: 
#    1. Modify variables as needed.
#    2. Add new variables to the function :writeOutput at the bottom of this batch file when new variables are added.
#    3. Copy this file to a location outside of the PDTool installation so that it is not overwritten during upgrade.
#    4. Modify setVars.sh "MY_VARS_HOME" variable to point to the directory that contains this file.
#    5. To encrypt the password in this file:
#       a) Open a windows command line
#       b) cd <path-to-pdtool>/PDTool8.3.0/bin
#       c) ExecutePDTool.sh -encrypt <path-to-file>/setMyPrePDToolVars.sh
#=======================================================================================================
# CREATE/MODIFY CUSTOM POST-PROCESSING VARIABLES BELOW THIS POINT
#=======================================================================================================
# 0=Do not print this section, 1 or true=Print this section
GEN_PRINT="1"
#---------------------------------------------
# Property Order Precedence
#---------------------------------------------
# The property order of precedence defines which properties are retrieved in what order.
#   JVM - These are properties that are set on the JVM command line with a -DVAR=value
#   PROPERTY_FILE - These are the variables set in the configuration property file like deploy.properties or in the VCSModule.xml
#   SYSTEM - These are variables that are set in batch files in the operating system prior to invocation of PDTool.
# If left blank, the default=JVM PROPERTY_FILE SYSTEM
# However, it may be necessary to be able to override what is in the property file and pick up an environment variable first.
export propertyOrderPrecedence=""
#
# Rewrite existing variables
export MODULE_HOME=""
#
#=======================================================================================================
# CREATE/MODIFY VARIABLES ABOVE THIS POINT
#=======================================================================================================
# Print out the variables
if [ "$PRINT_VARS" == "1" ]; then 
    MSG=""
    # Convert true to 1
    if [ "$GEN_PRINT" == "true" ]; then GEN_PRINT="1"; fi;
    MSG="${MSG}########################################################################################################################################\n"
    MSG="${MSG}${SV}: Setting post-processing custom variables\n"
    MSG="${MSG}########################################################################################################################################\n"
    if [ "$GEN_PRINT" == "1" ]; then
		MSG="${MSG}propertyOrderPrecedence    =${propertyOrderPrecedence}\n"
		MSG="${MSG}MODULE_HOME                =${MODULE_HOME}\n"
	fi
    echo -e "$MSG"
    # Output to the default log file if the variable DEFAULT_LOG_PATH is defined in ExecutePDTool.sh
    if [[ ("$DEFAULT_LOG_PATH" != "") && (-f "$DEFAULT_LOG_PATH") ]]; then
        echo -e "$MSG">>"$DEFAULT_LOG_PATH"
    fi
	MSG=""
fi
