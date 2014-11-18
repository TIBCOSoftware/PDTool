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
package com.cisco.dvbu.cmdline.vcs.spi.svn;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.cisco.dvbu.cmdline.vcs.spi.AbstractLifecycleListener;

/**
 * A sample SVN lifecycle listener.
 * <p>
 * NOTE: Warning handling may be specific to SVN 1.5.5.
 * 
 * @author panagiotis
 */
public class SVNLifecycleListener extends AbstractLifecycleListener {

    // The folder containing svn must be on PATH
    //private static final String SVN = (File.separatorChar=='/')?"svn":"svn.exe";
	// VCS_EXEC contains the full path and command.  It is not required to be in the path.
	private static final String SVN = VCS_EXEC;
	
    private static final String[] SVN_ADD    = new String[] { SVN, "add", ""};
    private static final String[] SVN_DELETE = new String[] { SVN, "delete", ""};
//    private static final String[] SVN_DELETE = new String[] { SVN, "delete", "--force", ""};
    private static final String[] SVN_COMMIT = new String[] { SVN, "commit", "-m 'Autocommitting preamble.'", ""};
        
    public SVNLifecycleListener() {}
    
//    @Override
    public void handle(File file, Event event, Mode mode, boolean verbose) throws VCSException {
        switch(event) {
            case CREATE:
                switch (mode) {
                    case POST:
                        handle(file, SVN_ADD, 2, verbose);
                        
System.out.println("CALLBACK: " + file.getPath());                        
                        
                        break;
                    default: // do nothing                        
                }
                break;
                
            case DELETE:
                switch (mode) {
                    case PRE:
                        handle(file, SVN_DELETE, 2, verbose);
                        break;
                    default: // do nothing                        
                }                
                break;
            default:  // do nothing
        }        
    }
    
    public void checkinPreambleFolder(File file, boolean verbose) throws VCSException {
        handle(file, SVN_COMMIT, 3, verbose);
    }
    
    private void handle(File file, String[] commandTemplate, int index, boolean verbose) throws VCSException {
        // customize svn command
        Map<Integer, String> commandConfiguration = new HashMap<Integer, String>();
       
        //commandConfiguration.put(index, file.getName());
        commandConfiguration.put(index, file.getAbsolutePath());
        String[] command = getConfiguredCommand(commandTemplate, commandConfiguration);
        
        // execute svn command
        execute(file.getParentFile(), command, verbose);        
    }

    protected String getErrorMessages(Process process) throws VCSException {
        StringBuilder result = new StringBuilder();
        
        BufferedReader sr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        try {
            String line = null;
            while((line = sr.readLine()) != null) {                
                if (!line.contains("svn: warning")) result.append(line).append(LS);
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
