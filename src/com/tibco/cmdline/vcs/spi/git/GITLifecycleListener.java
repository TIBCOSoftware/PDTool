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
package com.tibco.cmdline.vcs.spi.git;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cmdline.vcs.spi.AbstractLifecycleListener;

/**
 * A sample GIT lifecycle listener.
 * 
 * @author mtinius,cgoodric
 */
public class GITLifecycleListener extends AbstractLifecycleListener {

    // The folder containing svn must be on PATH
    //private static final String GIT = (File.separatorChar=='/')?"git":"git.exe";
	// VCS_EXEC contains the full path and command.  It is not required to be in the path.
	private static final String GIT = VCS_EXEC;
	
    private static final String[] GIT_ADD    = new String[] { GIT, "add", ""};
    private static final String[] GIT_DELETE = new String[] { GIT, "rm", ""};
    private static final String[] GIT_COMMIT = new String[] { GIT, "commit", "-m Autocommitting_preamble", ""};
        
    public GITLifecycleListener() {}
    
//    @Override
    public void handle(File file, Event event, Mode mode, boolean verbose) throws VCSException {
    	if (file.getName().equals(".git")) return;

        switch(event) {
            case CREATE:
            case UPDATE: // updates in Git have to be "added" the same way that new files do.
                switch (mode) {
                    case POST:
                        handle(file, GIT_ADD, 2, verbose);
                        
                        break;
                    default: // do nothing                        
                }
                break;
                
            case DELETE:
                switch (mode) {
                    case PRE:
                        handle(file, GIT_DELETE, 2, verbose);
                        break;
                    default: // do nothing                        
                }                
                break;
            default:  // do nothing
        }        
    }
    
    public void checkinPreambleFolder(File file, boolean verbose) throws VCSException {
        handle(file, GIT_COMMIT, 3, verbose);
    }
    
    private void handle(File file, String[] commandTemplate, int index, boolean verbose) throws VCSException {
        // customize git command
        Map<Integer, String> commandConfiguration = new HashMap<Integer, String>();
       
//        commandConfiguration.put(index, file.getName());
        commandConfiguration.put(index, file.getAbsolutePath());
        String[] command = getConfiguredCommand(commandTemplate, commandConfiguration);
        
        // execute git command
        execute(file.getParentFile(), command, verbose);        
    }

    protected String getErrorMessages(Process process) throws VCSException {
        StringBuilder result = new StringBuilder();
        
        // not all errors from Git show up in stderr, need to check stdout as well
        //
        BufferedReader bir = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader ber = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        try {
            String line = null;
            while((line = bir.readLine()) != null) {                
                if (!line.contains("git: warning")) result.append(line).append(LS);
            }
            while((line = ber.readLine()) != null) {                
                if (!line.contains("git: warning")) result.append(line).append(LS);
            }
        }
        catch(IOException e) {
            throw new VCSException(e);
        }        
        finally {
            try { bir.close(); } catch(IOException e) {throw new VCSException(e);} 
            try { ber.close(); } catch(IOException e) {throw new VCSException(e);} 
        }
        
        return result.toString();
    }
    
    // expose a static method so that the AbstractLifecycleListner can detect whether a GIT executable is being run.
    // git must be run from within a git repository folder in order to function correctly (for some reason it can't
    // figure out what the repository is based on the file being worked on. stupid.)
    //
    public static boolean isGitExecutable (String path) {
    	return (! path.endsWith ("git") ||
                ! path.endsWith ("git.sh") ||
                ! path.endsWith ("git.exe") ||
                ! path.endsWith ("git.bat")
               );
    }
}
