@echo off
REM ######################################################################
REM # (c) 2014 Cisco and/or its affiliates. All rights reserved.
REM ######################################################################
REM ###################################################
REM # Set customer variables
REM ###################################################
echo ########################################################################################################################################
echo.
echo Setting custom variables
echo.
echo ########################################################################################################################################

REM # CIS Port
REM # Used to define the SERVERID in deploy.properties
set PORT=9400
echo PORT=%PORT%

REM # HTTP_TYPE is set in the OS prior to execution giving greater flexibility to choose at runtime.  
REM #    set HTTP_TYPE=http to connect over regular http.
REM #    set HTTP_TYPE=https to connect PDTool over SSL (htts).
REM # This is used to compose the SERVERID for selecting the correct servers.xml id.
REM # This is used in ArchiveModule.xml for selecting the correct car file to import.
REM # This is used in DataSourceModule.xml for updating the URL for the web service data source.
set HTTP_TYPE=http
echo HTTP_TYPE=%HTTP_TYPE%

REM # Web Service Port
REM #   for HTTP set WSPORT=9400
REM #   for HTTPS set WSPORT=9402
REM # This is used in DataSourceModule.xml for updating the URL for the web service data source.
set WSPORT=9400
echo WSPORT=%WSPORT%

REM # VCS Qualified host name (host.domain.com)
set SVN_VCS_HOST=
echo SVN_VCS_HOST=%SVN_VCS_HOST%
set P4_VCS_HOST=
echo P4_VCS_HOST=%P4_VCS_HOST%
set CVS_VCS_HOST=
echo CVS_VCS_HOST=%CVS_VCS_HOST%
set TFS_VCS_HOST=
echo TFS_VCS_HOST=%TFS_VCS_HOST%

REM # Set the different VCS Users and Passwords as needed for testing
set SVN_VCS_USERNAME=
set SVN_VCS_PASSWORD=
echo SVN_VCS_USERNAME=%SVN_VCS_USERNAME%
set P4_VCS_USERNAME=
set P4_VCS_PASSWORD=
echo P4_VCS_USERNAME=%P4_VCS_USERNAME%
set CVS_VCS_USERNAME=
set CVS_VCS_PASSWORD=
echo CVS_VCS_USERNAME=%CVS_VCS_USERNAME%
set TFS_VCS_USERNAME=
set TFS_VCS_PASSWORD=
echo TFS_VCS_USERNAME=%TFS_VCS_USERNAME%
