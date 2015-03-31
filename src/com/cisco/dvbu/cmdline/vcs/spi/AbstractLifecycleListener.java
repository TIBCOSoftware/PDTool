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
package com.cisco.dvbu.cmdline.vcs.spi;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Map;

import com.cisco.dvbu.cmdline.vcs.spi.git.GITLifecycleListener;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.compositesw.common.vcs.primitives.IOPrimitives;
import com.compositesw.common.vcs.primitives.ProcessPrimitives;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractLifecycleListener implements LifecycleListener {

    protected static Log logger = LogFactory.getLog(AbstractLifecycleListener.class);
    protected static final String VCS_EXEC = System.getProperty("VCS_EXEC");
    protected static final String VCS_OPTIONS = System.getProperty("VCS_OPTIONS");
    protected static final String VCS_ENV = System.getProperty("VCS_ENV");
    protected static final String prefix = "AbstractLifecycleListener::";

    protected static final String LS = System.getProperty("line.separator");
    
    protected final ProcessBuilder processBuilder;

    protected AbstractLifecycleListener() {
        processBuilder = new ProcessBuilder();
    }
  
    /** 
     * @param commandTemplate Assumed not to be <tt>null</tt>.
     * @param commandConfiguration May be <tt>null</tt>.
     * @return An instance representing the specified command with the specified configuration parameters.
     *         <p>
     *         May not be <tt>null</tt>.
     */
    protected static String[] getConfiguredCommand(String[] commandTemplate, Map<Integer, String> commandConfiguration) {
        String[] result = getCommandFromTemplate(commandTemplate);
        
        if (commandConfiguration != null) {
            for (Map.Entry<Integer, String> entry: commandConfiguration.entrySet()) {
                result[entry.getKey()] = entry.getValue();
            }
        }
        
        return result;
    }
    
//    private static String printCommand(String[] command) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < command.length-1; i++) sb.append(command[i]).append(" ");
//        sb.append(command[command.length-1]);
//        return sb.toString();
//    }
    
    private static String[] getCommandFromTemplate(String[] template) {
        String[] command = new String[template.length]; 
        System.arraycopy(template, 0, command, 0, template.length);
        return command;
    }
    
    protected abstract String getErrorMessages(Process process) throws VCSException;
        
    /** 
     * @param contextFolder The folder to execute the specified command in.
     *                      <p>
     *                      May not be <tt>null</tt>.
     * @param command
     */
    protected void execute(File contextFolder, String[] command, boolean verbose) throws VCSException {
        if (contextFolder == null) throw new IllegalArgumentException("Context folder must be specified.");

		if (logger.isDebugEnabled()) {
	            logger.info(prefix+"-------------------------------------------------");
		 }
		
		// Set the full command including any VCS_OPTIONS
	    String[] newCommand = setCommannd(command);

	    /* 
	     * mtinius: 2014-02-28 resolve issue with long file paths.
	     * 
	     * A new process builder is required because the full command must be created on initialization.
	     * This resolves the "file too long" error that occurs in windows when executing a command line that exceeds 260 characters.
	     * Previously, the 2 commands were executed below which has the affect of doing a cd <long path> and then executing the VCS command.  
	     * That was the issue with windows.   Windows uses a different api when executing a cd to a path and it fails.
	     *     processBuilder.directory(contextFolder);
	     *     processBuilder.command(newCommand);  
	     *     
	     * By creating a process that includes the entire path as a single command, windows uses an API call that is not bound by the 260 character limit and
	     *    thus this command succeeds with very long paths.
	     * 
	     * To illustrate the point...
         *    This will succeed:  return new ProcessBuilder("svn", "add", new File(DIRECTORY, FILE).getAbsolutePath()).start();
         *                        This is basically doing a svn add path\file in one step.
         *                        This will work with paths longer than 260 characters
         * 
         *    This will fail:     return new ProcessBuilder("svn", "add", FILE).directory(new File(DIRECTORY)).start();
         *                        Fails with CreateProcess error=267, The directory name is invalid
		 *                        This is basically doing a cd <long path> and svn add file in two steps.
         *                        It will fail on cd <long path>
         *                        
         * cgoodric: 2014-09-08: The Git executable requires that its working directory is in the within the workspace that is
         *                       being worked in regardless of the path to the file(s) being worked on. NOTE: the directory()
         *                       method call MUST occur when the ProcessBuilder object is created. If it occurs later, the call
         *                       is ignored for some reason. :/
	     */
        ProcessBuilder processBuilder = (! GITLifecycleListener.isGitExecutable (VCS_EXEC))
        	? new ProcessBuilder (newCommand)
        	: new ProcessBuilder (newCommand).directory (contextFolder);
        
        // Set any environment variables that were specified by the user
        setEnvironment(processBuilder);

        if (logger.isDebugEnabled()) {
            logger.info(prefix+"-------------------------------------------------");
        }  
        
        Process process = execute(processBuilder);
        try {
            handleOutput(process, verbose);
            handleErrors(process);
        }
        finally {
            ProcessPrimitives.closeStreams(process, verbose);
        }
    }
    
    private Process execute(ProcessBuilder processBuilder) throws VCSException {
        Process result = null;
     
        try {
            result = processBuilder.start();
        }
        catch(IOException e) {
            throw new VCSException(e);
        }
        
        return result;
    }
    
    private void handleOutput(Process process, boolean verbose) throws VCSException {
        try {
            IOPrimitives.redirect(process.getInputStream(), verbose?System.out:null);
        }
        catch(IOException e) {
            throw new VCSException(e);
        }
    }
    
    private void handleErrors(Process process) throws VCSException {
        String errorMessages = getErrorMessages(process);
        if (errorMessages != null && errorMessages.length() > 0) {
        	// Mask the original command for any passwords
        	String command = CommonUtils.maskCommand(processBuilder.command().toString());	
            throw new VCSException(command + ": " + errorMessages);   
        }
    }    
    
    /** 
     * Set the command for the process to execute.
     * 
     * @param command[] - a list of command line arguments
     */
	private String[] setCommannd(String[] command) {
       
       // Count the VCS_OPTIONS in order to initialize the string array
		java.util.StringTokenizer st = new java.util.StringTokenizer(VCS_OPTIONS," ");
		int vcsOptionCount = 0;
		while(st.hasMoreTokens()){
			vcsOptionCount++;
			st.nextToken();
		}
		// Initialize a new command to hold existing command + VCS_OPTIONS
		String[] newCommand = new String[vcsOptionCount+command.length];
		
        // Setup the newCommand and extract the existing command to a string
    	String cmd = "";
    	for (int i=0; i < command.length; i++) {
    		newCommand[i] = command[i];
    		cmd = cmd + command[i].toString() + " ";
    	}
    	// Add on the VCS_OPTIONS to the end of the newCommand
		int tokenCount = command.length;
		st = new java.util.StringTokenizer(VCS_OPTIONS," ");
		while(st.hasMoreTokens()){
			String token = st.nextToken().toString();
    		newCommand[tokenCount] = token;
    		cmd = cmd + token + " ";
			tokenCount++;
		}
    	
        // Print out the command just prior to execution
        if (logger.isDebugEnabled()) {
			logger.debug("DEBUG::"+prefix+"Command: " + CommonUtils.maskCommand(cmd));
		}
		return newCommand;
	}
	
	
    /**
     * Set the environment variables for the process
     *  
     * @param processBuilder The process context
     */
	private void setEnvironment(ProcessBuilder processBuilder) {
       
		// Setup the environment variables
        Map<String, String> env = processBuilder.environment();
		 
        java.util.List<String> envList = new java.util.ArrayList<String>();
        // Retrieve the environment variables separated by a pipe
        envList = CommonUtils.getArgumentsList(envList, true, VCS_ENV, "|");
        // 2014-09-03 (cgoodric): make sure envList isn't empty
		if (envList != null) {
			// Loop through the list of VCS_ENV variables
			for (int i=0; i < envList.size(); i++) {
				String envVar = envList.get(i).toString();
				// Retrieve the name=value pair
				java.util.StringTokenizer st = new java.util.StringTokenizer(envVar,"=");
				if (st.hasMoreTokens()) {
					// Retrieve the variable name token
					String property = st.nextToken();
					String propertyVal = "";
					try {
						// Retrieve the variable value token
						propertyVal = st.nextToken();
					} catch (Exception e) {}
					
					// Put the environment variable (name=value) pair back to the environment
					env.put(property, propertyVal);
					
			        if (logger.isDebugEnabled()) {
			        	logger.info(prefix+"Env Var: "+CommonUtils.maskCommand(envVar));
					}
				}
			}
		}
	}

}
