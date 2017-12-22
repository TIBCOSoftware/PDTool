@echo off
SETLOCAL
REM ############################################################################################################################
REM #
REM ############################################################################################################################
REM #
REM #===========================================================================================================================
REM # Option 1 - Replace all text in a file.
REM #            ReplaceText.bat ["REPLACE_HOME"] replaceAllText "FROM_FILE" ["TO_FILE"] "SEARCH_TEXT" "REPLACEMENT_TEXT"
REM #
REM # Option 2 - Replace an occurrence of text in a file.
REM #            ReplaceText.bat ["REPLACE_HOME"] replaceOccurrenceText "FROM_FILE" ["TO_FILE"] "SEARCH_TEXT" "REPLACEMENT_TEXT" "OCCURRENCE"
REM # PARAMETER DEFINITIONS
REM #   REPLACE_HOME - Use double quotes "" to default to %CD% current directory or supply the path to this batch file.
REM #   REPLACE_CMD - replaceAllText or replaceOccurrenceText
REM #   FROM_FILE - The source file to read and perform the replace text on.
REM #   TO_FILE - Use double quotes "" to default to the same file as the FROM_FILE or supply a different file name to write the replace results to.
REM #   SEARCH_TEXT - The text to search for.
REM #   REPLACEMENT_TEXT - The text to replace with.
REM #   OCCURRENCE - The specific occurrence to replace using the method "replaceOccurrenceText".
REM #
REM # INSTRUCTIONS:
REM #   Modify the JAVA_HOME with the correct JRE7 or higher.
REM #   Modify the JAVA_OPT if necessary.
REM #============================================================================================================================
REM # Set customizable variables for JAVA_HOME
if not defined JAVA_HOME set JAVA_HOME=C:\Program Files\Java\jre7
REM # Set the Java Options for the JVM command line: Min and Max memory
set JAVA_OPT=-Xms256m -Xmx1024m
REM # Set the DEBUG flag locally if it is not defined by the invoking program.
if not defined DEBUG set DEBUG=N

REM # Get command line parameters
set REPLACE_HOME=%1
set REPLACE_CMD=%2
set FROM_FILE=%3
set TO_FILE=%4
set SEARCH_TEXT=%5
set REPLACEMENT_TEXT=%6
set OCCURRENCE=%7

REM # Remove double quotes
setlocal EnableDelayedExpansion
if defined REPLACE_HOME set REPLACE_HOME=!REPLACE_HOME:"=!
if defined REPLACE_CMD set REPLACE_CMD=!REPLACE_CMD:"=!
if defined FROM_FILE set FROM_FILE=!FROM_FILE:"=!
if defined TO_FILE set TO_FILE=!TO_FILE:"=!
if defined SEARCH_TEXT set SEARCH_TEXT=!SEARCH_TEXT:"=!
if defined REPLACEMENT_TEXT set REPLACEMENT_TEXT=!REPLACEMENT_TEXT:"=!
if defined OCCURRENCE set OCCURRENCE=!OCCURRENCE:"=!

REM # Print the intro and parameters
if "%DEBUG%"=="Y" (
	echo.
	echo.------------------------------------------------------------------
	echo.----------------- COMMAND-LINE TEXT REPLACEMENT ------------------
	echo.------------------------------------------------------------------
	echo.Parameters:
	echo     REPLACE_HOME="%REPLACE_HOME%"
	echo     REPLACE_CMD="%REPLACE_CMD%"
	echo     FROM_FILE="%FROM_FILE%"
	echo     TO_FILE="%TO_FILE%"
	echo     SEARCH_TEXT="%SEARCH_TEXT%"
	echo     REPLACEMENT_TEXT="%REPLACEMENT_TEXT%"
)
if "%REPLACE_CMD%" == "replaceOccurrenceText" echo     OCCURRENCE="%OCCURRENCE%"

REM # Validate parameters and set default values
if not defined REPLACE_HOME set REPLACE_HOME=%CD%
if defined REPLACE_HOME (
   set ERROR_MSG=
   if "%REPLACE_HOME%" == "replaceAllText" set ERROR_MSG=REPLACE_HOME parameter requires double quotes or an actual path.
   if "%REPLACE_HOME%" == "replaceOccurrenceText" set ERROR_MSG=REPLACE_HOME parameter requires double quotes or an actual path.
   if defined ERROR_MSG goto USAGE
)
if not defined FROM_FILE (
   set ERROR_MSG=The parameter FROM_FILE must be set.
   goto USAGE
)
if not defined SEARCH_TEXT (
   set ERROR_MSG=The parameter SEARCH_TEXT must be set.
   goto USAGE
)

if not defined TO_FILE set TO_FILE=%FROM_FILE%
if not defined OCCURRENCE set OCCURRENCE=0

if not defined JAVA_HOME (
	set ERROR_MSG=The variable JAVA_HOME must be set.
	goto USAGE
)
if not exist "%JAVA_HOME%" (
	set ERROR_MSG=The path could not be found for JAVA_HOME=%JAVA_HOME%
	goto USAGE
)

REM #=======================================
REM # Set DeployManager Environment Variables
REM #=======================================
set REPLACE_CLASSPATH=%REPLACE_HOME%\dist\ReplaceText.jar
set REPLACE_UTIL=com.tibco.ps.common.util.ReplaceText
REM #=======================================
REM # Parameter Validation
REM #=======================================
if "%REPLACE_CMD%" == "replaceAllText" goto EXEC_REPLACE_ALL_TEXT
if "%REPLACE_CMD%" == "replaceOccurrenceText" goto EXEC_REPLACE_OCCURRENCE_TEXT
set ERROR_MSG=Execution Failed::REPLACE_CMD="%REPLACE_CMD%" is invalid.

:---------------------------------
:USAGE
:---------------------------------
echo.
if defined ERROR_MSG echo.ERROR: %ERROR_MSG%
echo.
echo.USAGE:
echo.
echo.      Option 1 - Replace all text in a file.
echo.                 ReplaceText.bat ["REPLACE_HOME"] replaceAllText "FROM_FILE" ["TO_FILE"] "SEARCH_TEXT" "REPLACEMENT_TEXT"
echo.      
echo.      Option 2 - Replace an occurrence of text in a file.
echo.                 ReplaceText.bat ["REPLACE_HOME"] replaceOccurrenceText "FROM_FILE" ["TO_FILE"] "SEARCH_TEXT" "REPLACEMENT_TEXT" "OCCURRENCE"
echo.
set ERROR=1
goto ERROR_SCRIPT

:---------------------------------
:EXEC_REPLACE_ALL_TEXT
:---------------------------------
REM #***********************************************
REM # Invoke: ReplaceText.bat replaceAllText "%FROM_FILE%" "%TO_FILE%" "%SEARCH_TEXT%" "%REPLACEMENT_TEXT%"
REM #***********************************************
set JAVA_ACTION=replaceAllText
set   COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  "%REPLACE_CLASSPATH%" %REPLACE_UTIL% %JAVA_ACTION% "%FROM_FILE%" "%TO_FILE%" "%SEARCH_TEXT%" "%REPLACEMENT_TEXT%"
set PRCOMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  "%REPLACE_CLASSPATH%" %REPLACE_UTIL% %JAVA_ACTION% "%FROM_FILE%" "%TO_FILE%" "%SEARCH_TEXT%" "%REPLACEMENT_TEXT%"
GOTO START_SCRIPT

:---------------------------------
:EXEC_REPLACE_OCCURRENCE_TEXT
:---------------------------------
REM #***********************************************
REM # Invoke: ReplaceText replaceOccurrenceText "%FROM_FILE%" "%TO_FILE%" "%SEARCH_TEXT%" "%REPLACEMENT_TEXT%" "%OCCURRENCE%"
REM #***********************************************
set JAVA_ACTION=replaceOccurrenceText
set   COMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  "%REPLACE_CLASSPATH%" %REPLACE_UTIL% %JAVA_ACTION% "%FROM_FILE%" "%TO_FILE%" "%SEARCH_TEXT%" "%REPLACEMENT_TEXT%" "%OCCURRENCE%"
set PRCOMMAND="%JAVA_HOME%\bin\java" %JAVA_OPT% -cp  "%REPLACE_CLASSPATH%" %REPLACE_UTIL% %JAVA_ACTION% "%FROM_FILE%" "%TO_FILE%" "%SEARCH_TEXT%" "%REPLACEMENT_TEXT%" "%OCCURRENCE%"
GOTO START_SCRIPT

:--------------
:START_SCRIPT   
:--------------
REM #=======================================
REM # Execute the script
REM #=======================================
REM Escape (") in the COMMAND with ("") before printing
if "%DEBUG%"=="Y" (
	echo.
	echo.-- COMMAND: %JAVA_ACTION% ----------------------
	echo.
	echo.%PRCOMMAND%
	echo.
	echo.-- BEGIN OUTPUT ------------------------------------
	echo.
)

REM #------------------------------------------------------------------------
REM # Execute the command line CIS script
REM #------------------------------------------------------------------------
call %COMMAND%
set ERROR=%ERRORLEVEL%
:ERROR_SCRIPT
if %ERROR% NEQ 0 (
   echo.Execution Failed::Script %0 Failed. Abnormal Script Termination. Exit code is: %ERROR%.
   ENDLOCAL
   exit /B %ERROR%
)

REM #=======================================
REM # Successful script completion
REM #=======================================
if "%DEBUG%"=="Y" (
	echo.
	echo.-------------- SUCCESSFUL SCRIPT COMPLETION [%0 %JAVA_ACTION%] --------------
	echo.End of script.
)
ENDLOCAL
exit /B 0