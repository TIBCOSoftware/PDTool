@echo off
REM ################################################
REM #
REM # replaceText debug inputfile "search_text" "replace_text" outputfile
REM #
REM # debug - 0=no do not display debug output, 1=yes display debug output, 
REM # inputfile - the location and name of the file to replace text in or just the name if in current directory.
REM # search_text - the text line to search for - must be the entire line.  cannot find partial line text.
REM # replace_text - the text line to replace with.
REM # outputfile - the output file location and name or just name if to be placed in current directory.
REM #
REM #    Directions: Enclose parameters in double quotes when they contain spaces.
REM #    Limitations: This script is not able to handle an empty line with no characters.
REM ################################################
setlocal enabledelayedexpansion

REM #=======================================
REM # Assign input variables
REM #=======================================
set debug=%1
set inputfile=%2
set findthis=%3
set replacewith=%4
set outputfile=%5
REM #=======================================
REM # Remove double quotes
REM #=======================================
if defined debug set debug=!debug:"=!
if defined inputfile set inputfile=!inputfile:"=!
if defined findthis set findthis=!findthis:"=!
if defined replacewith set replacewith=!replacewith:"=!
if defined outputfile set outputfile=!outputfile:"=!

REM #=======================================
REM # Display input values
REM #=======================================
if %debug%==1 echo debug=[%debug%]
if %debug%==1 echo inputfile=[%inputfile%]
if %debug%==1 echo findthis=[%findthis%]
if %debug%==1 echo replacewith=[%replacewith%]
if %debug%==1 echo outputfile=[%outputfile%]

REM #=======================================
REM # Validate input values
REM #=======================================
if not defined debug (echo USAGE: Parameter 1, "debug" was not provided...)&goto :eof
if not defined inputfile (echo USAGE: Parameter 2, "inputfile" was not provided...)&goto :eof
if not exist "%inputfile%" (echo USAGE: The input file [%inputfile%] file does not exist...)&goto :eof
if not defined findthis (echo USAGE: Parameter 3, "findthis" was not provided...)&goto :eof
if not defined replacewith (echo USAGE: Parameter 4, "replacewith" was not provided...)&goto :eof
if not defined outputfile set outputfile=%~n1.replaced%~x1
echo outputfile=[%outputfile%]

setlocal ENABLEEXTENSIONS
CALL :ParseFile "%%inputfile%%" "%%outputfile%%" "%%findthis%%" "%%replacewith%%"
setlocal
exit /b 0

:ParseFile
@echo off & setlocal ENABLEEXTENSIONS DISABLEDELAYEDEXPANSION
set "FileToParse=%~1"
set "OutputFile=%~2"
set "NewExtension=%~3"
set /A Counter=0

REM #=======================================
REM # Write out file with line numbers [#]
REM #  in order to preserve blank lines
REM #=======================================
type "%FileToParse%" | find /V /N "" > "%FileToParse%.tmp"

REM #=======================================
REM # Loop through file
REM #=======================================
for /F "usebackq tokens=* delims=" %%a in ("%FileToParse%.tmp") DO (
   set "line=%%a"
   REM # Increment Counter
   set /A Counter+=1 
   setlocal ENABLEEXTENSIONS ENABLEDELAYEDEXPANSION
   REM # Remove the line numbers format [#]
   set "line=!line:*]=!"
   @REM Assign value to variable
   if "!line!"==" " set line=
   if %debug%==1 ( if [!line!]==[!findthis!] echo ****************MATCH FOUND**************** )
   if [!line!]==[!findthis!] set line=!replacewith!
   if %debug%==1 echo.[DEBUG !Counter!][!line!]
   if %debug%==1 ( if [!line!]==[!findthis!] echo ******************************************* )
   rem echo.!Counter!:!line!
   if !Counter! equ 1 echo.!line!>%outputfile%
   if !Counter! gtr 1 echo.!line!>>%outputfile%
  endlocal
)
del %FileToParse%.tmp
endlocal &GOTO:EOF
