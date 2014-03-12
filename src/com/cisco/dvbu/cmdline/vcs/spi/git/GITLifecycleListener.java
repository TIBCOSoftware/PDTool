/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.cmdline.vcs.spi.git;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.cisco.dvbu.cmdline.vcs.spi.AbstractLifecycleListener;

/**
 * A sample GIT lifecycle listener.
 * 
 * @author mtinius
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
    
    @Override
    public void handle(File file, Event event, Mode mode, boolean verbose) throws VCSException {
        switch(event) {
            case CREATE:
                switch (mode) {
                    case POST:
                        handle(file, GIT_ADD, 2, verbose);
                        
System.out.println("CALLBACK: " + file.getPath());                        
                        
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
        
        BufferedReader sr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        try {
            String line = null;
            while((line = sr.readLine()) != null) {                
                if (!line.contains("git: warning")) result.append(line).append(LS);
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
