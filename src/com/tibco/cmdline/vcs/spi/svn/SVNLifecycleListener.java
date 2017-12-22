/**
 * (c) 2017 TIBCO Software Inc. All rights reserved.
 * 
 * Except as specified below, this software is licensed pursuant to the Eclipse Public License v. 1.0.
 * The details can be found in the file LICENSE.
 * 
 * The following proprietary files are included as a convenience, and may not be used except pursuant
 * to valid license to Composite Information Server or TIBCO(R) Data Virtualization Server:
 * csadmin-XXXX.jar, csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar,
 * csext-XXXX.jar, csjdbc-XXXX.jar, csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar,
 * and customproc-XXXX.jar (where -XXXX is an optional version number).  Any included third party files
 * are licensed under the terms contained in their own accompanying LICENSE files, generally named .LICENSE.txt.
 * 
 * This software is licensed AS-IS. Support for this software is not covered by standard maintenance agreements with TIBCO.
 * If you would like to obtain assistance with this software, such assistance may be obtained through a separate paid consulting
 * agreement with TIBCO.
 * 
 */
package com.tibco.cmdline.vcs.spi.svn;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cmdline.vcs.spi.AbstractLifecycleListener;

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
