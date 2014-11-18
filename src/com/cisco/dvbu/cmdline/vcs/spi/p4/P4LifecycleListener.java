/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 * 
 * This software is released under the Eclipse Public License. The details can be found in the file LICENSE. 
 * Any dependent libraries supplied by third parties are provided under their own open source licenses as 
 * described in their own LICENSE files, generally named .LICENSE.txt. The libraries supplied by Cisco as 
 * part of the Composite Information Server/Cisco Data Virtualization Server, particularly csadmin-XXXX.jar, 
 * csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar, csext-XXXX.jar, csjdbc-XXXX.jar, 
 * csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar, and customproc-XXXX.jar (where -XXXX is an 
 * optional version number) are provided as a convenience, but are covered under the licensing for the 
 * Composite Information Server/Cisco Data Virtualization Server. They cannot be used in any way except 
 * through a valid license for that product.
 * 
 * This software is released AS-IS!. Support for this software is not covered by standard maintenance agreements with Cisco. 
 * Any support for this software by Cisco would be covered by paid consulting agreements, and would be billable work.
 * 
 */
package com.cisco.dvbu.cmdline.vcs.spi.p4;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.cisco.dvbu.cmdline.vcs.spi.AbstractLifecycleListener;

/**
 * A Perforce lifecycle listener.
 * 
 * NOTE: Warning handling is specific to Perforce.
 *
 * @author panagiotis,cgoodrich,mtinius
 */
public class P4LifecycleListener extends AbstractLifecycleListener {

    // The folder containing p4.exe must be on PATH
    //private static final String P4 = ('/' == File.separatorChar) ? "p4" : "p4.exe";
	// VCS_EXEC contains the full path and command.  It is not required to be in the path.
    private static final String P4 = VCS_EXEC;
    
    private static final String[] P4_ADD    = new String[] { P4, "add", ""};
    private static final String[] P4_EDIT   = new String[] { P4, "edit", ""};
    private static final String[] P4_DELETE = new String[] { P4, "delete", ""};
    private static final String[] P4_SUBMIT = new String[] { P4, "submit", "-d 'Autocommitting preamble.'"};

//    @Override
    public void handle(File file, Event event, Mode mode, boolean verbose) throws VCSException {
        switch(event) {
            case CREATE:
                switch (mode) {
                    case POST:
                        handle(file, P4_ADD, 2, verbose);
                        break;
                    default: // do nothing                        
                }
                break;
                
            case UPDATE:
                switch (mode) {
                    case PRE:
                        handle(file, P4_EDIT, 2, verbose);
                        break;
                    default: // do nothing                        
                }
                break;

            case DELETE:
                switch (mode) {
                    case PRE:
                        handle(file, P4_DELETE, 2, verbose);
                        break;
                    default: // do nothing                        
                }                
                break;
            default:  // do nothing
        }                
    }
    
    public void checkinPreambleFolder(File file, boolean verbose) throws VCSException {
        // NOTE: This approach does not handle side-effect checkins if the user has other
        //       files opened in the default changelist under the preamble parent folder.
        //       This won't happen in the regular workflow scenarios. However, it may 
        //       happen if the user has used "prepare_checkin" kind of scripts, without
        //       performing the actual checkin before the preamble checkin occurs.
        // 
        //       If the above is the case, a more targeted approach is required, that will
        //       collect all the preamble folder files and have them checked in.
        handle(file, P4_SUBMIT, -1, verbose);
    }

    private void handle(File file, String[] commandTemplate, int index, boolean verbose) throws VCSException {
        if (file.isDirectory() && commandTemplate != P4_SUBMIT) return;
        
        // customize p4 command
        Map<Integer, String> commandConfiguration = new HashMap<Integer, String>(); 
        if (index != -1) commandConfiguration.put(index, file.getAbsolutePath());
        String[] command = getConfiguredCommand(commandTemplate, commandConfiguration);
        
        // execute p4 command
        execute(file.getParentFile(), command, verbose);        
    }

    @Override
    protected String getErrorMessages(Process process) throws VCSException {
        StringBuilder result = new StringBuilder();
        
        BufferedReader sr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        char[] buffer = new char[5120];
        int count = 0;
        
        try {
            while((count = sr.read(buffer)) != -1) {
                result.append(buffer, 0, count);
            }
        }
        catch(IOException e) {
            throw new VCSException(e);
        }        
        finally {
            try { sr.close(); } catch(IOException e) {throw new VCSException(e);} 
        }

        return result.toString();
    }
}
