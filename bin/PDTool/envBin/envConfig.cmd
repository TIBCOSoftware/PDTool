@echo off
REM ############################################################################################################################
REM # (c) 2017 TIBCO Software Inc. All rights reserved.
REM # 
REM # Except as specified below, this software is licensed pursuant to the Eclipse Public License v. 1.0.
REM # The details can be found in the file LICENSE.
REM # 
REM # The following proprietary files are included as a convenience, and may not be used except pursuant
REM # to valid license to Composite Information Server or TIBCO(R) Data Virtualization Server:
REM # csadmin-XXXX.jar, csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar,
REM # csext-XXXX.jar, csjdbc-XXXX.jar, csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar,
REM # and customproc-XXXX.jar (where -XXXX is an optional version number).  Any included third party files
REM # are licensed under the terms contained in their own accompanying LICENSE files, generally named .LICENSE.txt.
REM # 
REM # This software is licensed AS-IS. Support for this software is not covered by standard maintenance agreements with TIBCO.
REM # If you would like to obtain assistance with this software, such assistance may be obtained through a separate paid consulting
REM # agreement with TIBCO.
REM #
REM ############################################################################################################################
REM # Author: Mike Tinius, Data Virtualization Business Unit, Advanced Services
REM # Date:   April 2016
REM #======================================================================================================================
REM #   envConfig.cmd [-list or ENV_TYPE]
REM #      Example: envConfig UAT
REM #
REM #   Pre-Requisiste: The invoking batch script must have already executed "setMyPrePDToolVars.bat"
REM # 
REM #      1. Result: The variable CONFIG_PROPERTY_NAME is set upon execution of this batch file and is used with -config operator.
REM #      2. The valid variables are set in "setMyPrePDToolVars.bat" and must be unique across all variables: 
REM #           Format of pairs: XDEV=deploy_VCSTYPE_DEV,XUAT=deploy_VCSTYPE_UAT,XPROD=deploy_VCSTYPE_PROD
REM #               X represents the VCS environment. N=NOVCS,S=SVN,T=TFS,G=GIT,P=P4,C=CVS
REM #               This makes the ENV_TYPE unique across all different list types.
REM #           Examples:
REM #             NOVCS_VALID_ENV_CONFIG_PAIRS=NDEV~deploy_NOVCS_DEV,NUAT~deploy_NOVCS_UAT,NPROD~deploy_NOVCS_PROD
REM #               TFS_VALID_ENV_CONFIG_PAIRS=TDEV~deploy_TFS_DEV,TUAT~deploy_TFS_UAT,TPROD~deploy_TFS_PROD
REM #               SVN_VALID_ENV_CONFIG_PAIRS=SDEV~deploy_SVN_DEV,SUAT~deploy_SVN_UAT,SPROD~deploy_SVN_PROD
REM #               GIT_VALID_ENV_CONFIG_PAIRS=GDEV~deploy_GIT_DEV,GUAT~deploy_GIT_UAT,GPROD~deploy_GIT_PROD
REM #               CVS_VALID_ENV_CONFIG_PAIRS=CDEV~deploy_CVS_DEV,CUAT~deploy_CVS_UAT,CPROD~deploy_CVS_PROD
REM #                P4_VALID_ENV_CONFIG_PAIRS=PDEV~deploy_P4_DEV,PUAT~deploy_P4_UAT,PPROD~deploy_P4_PROD
REM #      3. It is up to the installer of PDTool to set these values.
REM #      4. When envConfig -list is used it will list out the environment type/config property file mapping pairs.
REM #======================================================================================================================

REM ######################################
REM Validate input
REM ######################################
set ENV_TYPE=%1
REM # Remove double quotes and set default values where appropriate
SETLOCAL EnableDelayedExpansion
if defined ENV_TYPE set ENV_TYPE=!ENV_TYPE:"=!
ENDLOCAL &set ENV_TYPE=%ENV_TYPE%

REM # The name of this batch file.
for %%a in (%0) do set batchFileName=%%~na
for %%a in (%0) do set batchFileExt=%%~xa
if "%batchFileExt%"=="" set batchFileName=%batchFileName%.bat
if "%batchFileExt%" NEQ "" set batchFileName=%batchFileName%%batchFileExt%

REM # The TOKEN variable is used for splitting the pairs of environment type and configuration proper file name.
REM #    Example: DEV~deploy_DEV
set TOKEN=~
set ERROR=0
set ENV_LIST=0
set CONFIG_PROPERTY_NAME=
if not defined debug SET debug=0

REM # Determine if the input parameters is already a .properties file and if so bypass processing
for %%a in (%ENV_TYPE%) do set ENV_TPYE_EXT=%%~xa
if "%ENV_TPYE_EXT%"==".properties" (
   set CONFIG_PROPERTY_NAME=%ENV_TYPE%
   goto END
)
if "%ENV_TPYE_EXT%"==".PROPERTIES" (
   set CONFIG_PROPERTY_NAME=%ENV_TYPE%
   goto END
)
REM # Process each of the VALID_ENV_CONFIG_PAIRS variables and search for a matching pair.
:PROCESS1
   if not defined NOVCS_VALID_ENV_CONFIG_PAIRS goto PROCESS2
   CALL :PROCESS_SINGLE_ENV_TYPE_LIST %ENV_TYPE% "%NOVCS_VALID_ENV_CONFIG_PAIRS%" NOVCS_VALID_ENV_CONFIG_PAIRS
   if defined CONFIG_PROPERTY_NAME goto PROCESS_COMPLETE

:PROCESS2
   if not defined TFS_VALID_ENV_CONFIG_PAIRS goto PROCESS3
   CALL :PROCESS_SINGLE_ENV_TYPE_LIST %ENV_TYPE% "%TFS_VALID_ENV_CONFIG_PAIRS%" TFS_VALID_ENV_CONFIG_PAIRS
   if defined CONFIG_PROPERTY_NAME goto PROCESS_COMPLETE

:PROCESS3
   if not defined SVN_VALID_ENV_CONFIG_PAIRS goto PROCESS4
   CALL :PROCESS_SINGLE_ENV_TYPE_LIST %ENV_TYPE% "%SVN_VALID_ENV_CONFIG_PAIRS%" SVN_VALID_ENV_CONFIG_PAIRS
   if defined CONFIG_PROPERTY_NAME goto PROCESS_COMPLETE

:PROCESS4
   if not defined GIT_VALID_ENV_CONFIG_PAIRS goto PROCESS5
   CALL :PROCESS_SINGLE_ENV_TYPE_LIST %ENV_TYPE% "%GIT_VALID_ENV_CONFIG_PAIRS%" GIT_VALID_ENV_CONFIG_PAIRS
   if defined CONFIG_PROPERTY_NAME goto PROCESS_COMPLETE

:PROCESS5
   if not defined P4_VALID_ENV_CONFIG_PAIRS goto PROCESS6
   CALL :PROCESS_SINGLE_ENV_TYPE_LIST %ENV_TYPE% "%P4_VALID_ENV_CONFIG_PAIRS%" P4_VALID_ENV_CONFIG_PAIRS
   if defined CONFIG_PROPERTY_NAME goto PROCESS_COMPLETE

:PROCESS6
   if not defined CVS_VALID_ENV_CONFIG_PAIRS goto PROCESS7
   CALL :PROCESS_SINGLE_ENV_TYPE_LIST %ENV_TYPE% "%CVS_VALID_ENV_CONFIG_PAIRS%" CVS_VALID_ENV_CONFIG_PAIRS
   if defined CONFIG_PROPERTY_NAME goto PROCESS_COMPLETE
:PROCESS7
   set ERROR=0
   if "%ENV_LIST%"=="1" goto END

   if not defined CONFIG_PROPERTY_NAME (
      set ERROR=1
	  echo.%batchFileName%:: ERROR: No CONFIG_PROPERTY_NAME was set for %ENV_TYPE%.  Check variables in setMyPrePDToolVars.bat.
   )
:PROCESS_COMPLETE
REM # Check for a .properties extension just in case.  Add .properties if it does not exist.
if not defined CONFIG_PROPERTY_NAME goto END
   for %%a in (%CONFIG_PROPERTY_NAME%) do set ENV_TPYE_EXT=%%~xa
   if "%ENV_TPYE_EXT%"==".properties" goto END
   if "%ENV_TPYE_EXT%"==".PROPERTIES" goto END
   SET CONFIG_PROPERTY_NAME=%CONFIG_PROPERTY_NAME%.properties

:END
if %debug%==1 echo.[DEBUG] %0 CONFIG_PROPERTY_NAME=%CONFIG_PROPERTY_NAME%
exit /b %ERROR%

REM ##############################################################################################
REM
REM BEGIN: FUNCTION IMPLEMENTATION
REM
REM ##############################################################################################

::=====================================================================
:PROCESS_SINGLE_ENV_TYPE_LIST
::=====================================================================
:: Process a single environment type list that is passed in. 
::   Property File Name pair combinations variable: VALID_ENV_CONFIG_PAIRS
::   Format of pairs: XDEV=deploy_DEV,XUAT=deploy_UAT,XPROD=deploy_PROD
::      X represents the VCS environment. N=NOVCS,S=SVN,T=TFS,G=GIT,P=P4,C=CVS
::      This makes the ENV_TYPE unique across all different list types.
::
set ENV_TYPE=%1
set VALID_ENV_CONFIG_PAIRS=%2
set VALID_ENV_CONFIG_PAIRS_NAME=%3

REM # Remove double quotes and set default values where appropriate
SETLOCAL EnableDelayedExpansion
if defined VALID_ENV_CONFIG_PAIRS set VALID_ENV_CONFIG_PAIRS=!VALID_ENV_CONFIG_PAIRS:"=!
if defined ENV_TYPE set ENV_TYPE=!ENV_TYPE:"=!
if %debug%==1 echo.[DEBUG] %0 VALID_ENV_CONFIG_PAIRS=%VALID_ENV_CONFIG_PAIRS%

REM # Validate the input variable to insure it is not blank and not a command by seeing if the first character is a dash.
if "%ENV_TYPE%"=="" goto PROCESS_END

if "%ENV_TYPE%"=="-list" (
   echo.  ENV_TYPE LIST: %VALID_ENV_CONFIG_PAIRS_NAME%
   CALL :LIST_VALIDATE_CONFIG_PROPERTY_NAME "%VALID_ENV_CONFIG_PAIRS%"
   goto PROCESS_LIST
)
set ISDASH=%ENV_TYPE:~0,1%
if "%ISDASH%"=="-" (
   echo.Invalid command found: %ENV_TYPE%
   goto PROCESS_LIST
)

REM # Convert execute type to upper case
CALL :UCase ENV_TYPE ENV_TYPE 
if %debug%==1 echo.[DEBUG] %0 ENV_TYPE=%ENV_TYPE%

REM # Validate variables and input parameters
if not defined VALID_ENV_CONFIG_PAIRS (
   echo.[WARNING] %0 The variable VALID_ENV_CONFIG_PAIRS has not been defined.
   ENDLOCAL
   goto PROCESS_END
)

REM # Extract the environment type list
set FUNCTION_NAME1=[DEBUG] EXTRACT_ENV_TYPE_LIST      
if %debug%==1 echo.%FUNCTION_NAME1%    BEGIN: ------------------------------------------
CALL :EXTRACT_ENV_TYPE_LIST "%VALID_ENV_CONFIG_PAIRS%" VALID_ENV_TYPES
if %debug%==1 echo.%FUNCTION_NAME1%   RETURN: %VALID_ENV_TYPES%
if %debug%==1 echo.%FUNCTION_NAME1%      END: ------------------------------------------
if %debug%==1 echo.

REM # Validate the input parameters
set VALID_ENV=0
set CONFIG_PROPERTY_NAME=
set FUNCTION_NAME1=[DEBUG] VALIDATE_CONFIG_PROPERTY_NAME      
if %debug%==1 echo.%FUNCTION_NAME1%    BEGIN: ------------------------------------------
CALL :VALIDATE_CONFIG_PROPERTY_NAME "%ENV_TYPE%" "%VALID_ENV_CONFIG_PAIRS%" VALID_ENV CONFIG_PROPERTY_NAME
if %debug%==1 echo.%FUNCTION_NAME1%   RETURN: %VALID_ENV% %CONFIG_PROPERTY_NAME%
if %debug%==1 echo.%FUNCTION_NAME1%      END: ------------------------------------------
if %debug%==1 echo.
ENDLOCAL &set CONFIG_PROPERTY_NAME=%CONFIG_PROPERTY_NAME%&set ERROR=%ERROR%
goto PROCESS_END

:PROCESS_LIST
ENDLOCAL
set ENV_LIST=1

:PROCESS_END
GOTO:EOF


::=====================================================================
:EXTRACT_ENV_TYPE_LIST
::=====================================================================
:: Extract the environment type list from the environment/configuration 
::   Property File Name pair combinations variable: VALID_ENV_CONFIG_PAIRS
::   Format of pairs: DEV=deploy_DEV,UAT=deploy_UAT,PROD=deploy_PROD
::
set FUNCTION_NAME1=[DEBUG] EXTRACT_ENV_TYPE_LIST      
set FUNCTION_NAME2=[DEBUG] PARSE_EXTRACT_ENV_TYPE_LIST
set FUNCTION_NAME3=[DEBUG] SUB_EXTRACT_ENV_TYPE_LIST  

SETLOCAL EnableDelayedExpansion
REM Get input parameter and remove double quotes
set _INP_VALID_ENV_TYPES=%1
set _INP_VALID_ENV_TYPES=!_INP_VALID_ENV_TYPES:"=!
REM Set default values
set _VALID_ENV_LIST=

call :PARSE_EXTRACT_ENV_TYPE_LIST %1 _VALID_ENV_LIST

if %debug%==1 echo.%FUNCTION_NAME1% output 4: LIST=%_VALID_ENV_LIST%
ENDLOCAL & SET _VALID_ENV_LIST=%_VALID_ENV_LIST%
if %debug%==1 echo.%FUNCTION_NAME1% output 5: LIST=%_VALID_ENV_LIST%
SET %2=%_VALID_ENV_LIST%
GOTO:EOF

:PARSE_EXTRACT_ENV_TYPE_LIST
REM Get input parameters and remove double quotes
set _INP_VALID_ENV_TYPES=%1
set _INP_VALID_ENV_TYPES=!_INP_VALID_ENV_TYPES:"=!

REM Display parameters
if %debug%==1 echo.%FUNCTION_NAME2%   params: _INP_VALID_ENV_TYPES=%_INP_VALID_ENV_TYPES%

FOR /f "eol=; tokens=1* delims=," %%a IN ("!_INP_VALID_ENV_TYPES!") DO (
    if %debug%==1 echo.%FUNCTION_NAME2% output 2: LIST a=%%a   LIST b=%%b
	if not "%%a" == "" call :SUB_EXTRACT_ENV_TYPE_LIST %%a  
	if not "%%b" == "" call :PARSE_EXTRACT_ENV_TYPE_LIST "%%b"
	if %debug%==1 echo.%FUNCTION_NAME2% output 2: LIST=%_VALID_ENV_LIST%
)
goto:eos

:SUB_EXTRACT_ENV_TYPE_LIST
	for /f "tokens=1,2 delims=%TOKEN%" %%a in ("%1") do set _ENV_TYPE=%%a&set _CONFIG_NAME=%%b
	REM Set the environment type list
	if "%_VALID_ENV_LIST%" NEQ "" set _VALID_ENV_LIST=%_VALID_ENV_LIST%,
	if "!_ENV_TYPE!" NEQ "" set _VALID_ENV_LIST=%_VALID_ENV_LIST%!_ENV_TYPE!
	if %debug%==1 echo.%FUNCTION_NAME3% output 1: LIST=%_VALID_ENV_LIST%
GOTO:EOF

:eos
if %debug%==1 echo.%FUNCTION_NAME1% output 3: LIST=%_VALID_ENV_LIST%
GOTO:EOF


::=====================================================================
:VALIDATE_CONFIG_PROPERTY_NAME
::=====================================================================
:: Parse and validate the environment/configuration Property File Name pair combinations
::   Format of pairs: DEV=deploy_DEV,UAT=deploy_UAT,PROD=deploy_PROD
::
set FUNCTION_NAME1=[DEBUG] VALIDATE_CONFIG_PROPERTY_NAME      
set FUNCTION_NAME2=[DEBUG] PARSE_VALIDATE_CONFIG_PROPERTY_NAME
set FUNCTION_NAME3=[DEBUG] SUB_VALIDATE_CONFIG_PROPERTY_NAME  

SETLOCAL EnableDelayedExpansion
REM Get input parameters
set _INP_ENV_TYPE=%1
set _INP_VALID_ENV_TYPES=%2
REM Remove double quotes
set _INP_ENV_TYPE=!_INP_ENV_TYPE:"=!
set _INP_VALID_ENV_TYPES=!_INP_VALID_ENV_TYPES:"=!
REM Set default values
set _VALID_ENV=0
set _CONFIG_PROPERTY_NAME=

call :PARSE_VALIDATE_CONFIG_PROPERTY_NAME %1 %2 _VALID_ENV _CONFIG_PROPERTY_NAME

if %debug%==1 echo.%FUNCTION_NAME1% output 4: %_VALID_ENV% %_CONFIG_PROPERTY_NAME%
ENDLOCAL & SET _VALID_ENV=%_VALID_ENV%& SET _CONFIG_PROPERTY_NAME=%_CONFIG_PROPERTY_NAME%
if %debug%==1 echo.%FUNCTION_NAME1% output 5: %_VALID_ENV% %_CONFIG_PROPERTY_NAME%
SET %3=%_VALID_ENV%
SET %4=%_CONFIG_PROPERTY_NAME%
GOTO:EOF

:PARSE_VALIDATE_CONFIG_PROPERTY_NAME
REM Get input parameters
set _INP_ENV_TYPE=%1
set _INP_VALID_ENV_TYPES=%2
REM Remove double quotes
set _INP_ENV_TYPE=!_INP_ENV_TYPE:"=!
set _INP_VALID_ENV_TYPES=!_INP_VALID_ENV_TYPES:"=!

REM Display parameters
if %debug%==1 echo.%FUNCTION_NAME2%   params:_INP_ENV_TYPE=%_INP_ENV_TYPE%    _INP_VALID_ENV_TYPES=%_INP_VALID_ENV_TYPES%

FOR /f "eol=; tokens=1* delims=," %%a IN ("!_INP_VALID_ENV_TYPES!") DO (
    if %debug%==1 echo.%FUNCTION_NAME2%  input 1: _INP_ENV_TYPE=%_INP_ENV_TYPE%    LIST a=%%a   LIST b=%%b
	if not "%%a" == "" call :SUB_VALIDATE_CONFIG_PROPERTY_NAME !_INP_ENV_TYPE! %%a  
	if not "%%b" == "" call :PARSE_VALIDATE_CONFIG_PROPERTY_NAME "!_INP_ENV_TYPE!" "%%b"
	if %debug%==1 echo.%FUNCTION_NAME2% output 2: %_VALID_ENV% %_CONFIG_PROPERTY_NAME%
)
goto:eos

:SUB_VALIDATE_CONFIG_PROPERTY_NAME
	set _INP_ENV_TYPE=%1
	for /f "tokens=1,2 delims=%TOKEN%" %%a in ("%2") do set _ENV_TYPE=%%a&set _CONFIG_NAME=%%b
	if "%_INP_ENV_TYPE%" == "!_ENV_TYPE!" set _VALID_ENV=1
    if "%_INP_ENV_TYPE%" == "!_ENV_TYPE!" set _CONFIG_PROPERTY_NAME=!_CONFIG_NAME!
	if %debug%==0 goto NO_DEBUG
	   echo.%FUNCTION_NAME3% output 1: ENV_TYPE=[!_ENV_TYPE!]    CONFIG_NAME=[!_CONFIG_NAME!]
	   echo.%FUNCTION_NAME3% output 1: MATCH FOUND FOR ENV_TYPE=!_ENV_TYPE!
	   echo.%FUNCTION_NAME3% output 1: %_VALID_ENV% %_CONFIG_PROPERTY_NAME%
	:NO_DEBUG
GOTO:EOF

:eos
if %debug%==1 echo.%FUNCTION_NAME1% output 3: %_VALID_ENV% %_CONFIG_PROPERTY_NAME%
GOTO:EOF


:LIST_VALIDATE_CONFIG_PROPERTY_NAME
:: List the environment/configuration Property File Name pair combinations
::   Format of pairs: DEV=deploy_DEV,UAT=deploy_UAT,PROD=deploy_PROD
::
set FUNCTION_NAME1=[DEBUG] LIST_VALIDATE_CONFIG_PROPERTY_NAME      
set FUNCTION_NAME2=[DEBUG] LIST_SUB_VALIDATE_CONFIG_PROPERTY_NAME

REM Get input parameters
set _INP_VALID_ENV_TYPES=%1
REM Remove double quotes
set _INP_VALID_ENV_TYPES=!_INP_VALID_ENV_TYPES:"=!


REM Display parameters
if %debug%==1 echo.%FUNCTION_NAME2%   params:_INP_ENV_TYPE=%_INP_ENV_TYPE%    _INP_VALID_ENV_TYPES=%_INP_VALID_ENV_TYPES%

FOR /f "eol=; tokens=1* delims=," %%a IN ("!_INP_VALID_ENV_TYPES!") DO (
    if %debug%==1 echo.%FUNCTION_NAME2%  input 1: LIST a=%%a   LIST b=%%b
	if not "%%a" == "" call :LIST_SUB_VALIDATE_CONFIG_PROPERTY_NAME %%a  
	if not "%%b" == "" call :LIST_VALIDATE_CONFIG_PROPERTY_NAME "%%b"
)
goto:eos2

:LIST_SUB_VALIDATE_CONFIG_PROPERTY_NAME
	for /f "tokens=1,2 delims=%TOKEN%" %%a in ("%1") do set _ENV_TYPE=%%a&set _CONFIG_NAME=%%b
    echo.    ENV_TYPE=[!_ENV_TYPE!]    CONFIG_NAME=[!_CONFIG_NAME!.properties]
GOTO:EOF

:eos2
GOTO:EOF


::=====================================================================
:LCase
:UCase
::=====================================================================
:: Converts to upper/lower case variable contents
:: Syntax: CALL :UCase _VAR1 _VAR2
:: Syntax: CALL :LCase _VAR1 _VAR2
:: _VAR1 = Variable NAME whose VALUE is to be converted to upper/lower case
:: _VAR2 = NAME of variable to hold the converted value
:: Note: Use variable NAMES in the CALL, not values (pass "by reference")

SET _UCase=A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
SET _LCase=a b c d e f g h i j k l m n o p q r s t u v w x y z
SET _Lib_UCase_Tmp=!%1!

IF /I "%0"==":UCase" SET _Abet=%_UCase%
IF /I "%0"==":LCase" SET _Abet=%_LCase%
FOR %%Z IN (%_Abet%) DO SET _Lib_UCase_Tmp=!_Lib_UCase_Tmp:%%Z=%%Z!
SET %2=%_Lib_UCase_Tmp%
GOTO:EOF
