@echo off
REM ######################################################################
REM # (c) 2014 Cisco and/or its affiliates. All rights reserved.
REM ######################################################################

set CURR=%CD%
set P4CLIENT=p4_workspace
set P4USER=qa
set P4PASSWD=password
set P4EDITOR=notepad
set P4PORT=kauai:1666

cd ..\p4_workspace\cis_objects

REM cd %CURR%