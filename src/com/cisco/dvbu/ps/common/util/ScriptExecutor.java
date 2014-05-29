/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.common.util;

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
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.CompositeLogger;

public class ScriptExecutor {

	private String execFromDir;
	private List<String> envList;
	private List<String> scriptArgsList;
	private ScriptStreamHandler inputStreamHandler;
	private ScriptStreamHandler errorStreamHandler;
    private static Log logger = LogFactory.getLog(ScriptExecutor.class);

	public ScriptExecutor(final String execFromDir, final List<String> scriptArgsList, final List<String> envList) {
		if (scriptArgsList == null)
			throw new NullPointerException("The scriptArgsList is required.");
		this.scriptArgsList = scriptArgsList;
		this.execFromDir = execFromDir;
		this.envList = envList;
	}

	public int executeCommand(String errorFile){
		int exitValue = -99;
       	
		String prefix = "ScriptExecutor::";
		String command = "";
		try {
			 // Print out the command and execution directory
			 for (int i=0; i < scriptArgsList.size(); i++) {
				 command = command + scriptArgsList.get(i) + " ";
			 }
			 if (logger.isDebugEnabled()) {
				 logger.debug(prefix+"-------------------------------------------------");
				 logger.debug(prefix+"Command:  "+CommonUtils.maskCommand(command));
				 logger.debug(prefix+"Exec Dir: "+execFromDir.toString());
			}
			 	
			// Build a new process to execute
			ProcessBuilder pb = new ProcessBuilder(scriptArgsList);
			
			// Setup the environment variables
			 Map<String, String> env = pb.environment();
			 for (int i=0; i < envList.size(); i++) {
				String envVar = envList.get(i).toString();
				StringTokenizer st = new StringTokenizer(envVar,"=");
				if (st.hasMoreTokens()) {
					String property = st.nextToken();
					String propertyVal = "";
					try {
						propertyVal = st.nextToken();
					} catch (Exception e) {}
					env.put(property, propertyVal);
					
					if (logger.isDebugEnabled()) {
						logger.debug(prefix+"Env Var:  "+CommonUtils.maskCommand(envVar));
					}
				}
			 }
			if (logger.isDebugEnabled()) {
		        logger.debug(prefix+"-------------------------------------------------");
			}

			 // Setup up the execute from directory
			 File execDir = new File(execFromDir);
			 pb.directory(execDir);

				if (logger.isDebugEnabled()) {
					logger.debug("");
					logger.debug("ProcessBuilder::pb.command:                    "+CommonUtils.maskCommand(pb.command().toString()));
					logger.debug("ProcessBuilder::pb.directory:                  "+pb.directory().toString());
					logger.debug("ProcessBuilder::pb.directory.getAbsolutePath:  "+pb.directory().getAbsolutePath());
					logger.debug("ProcessBuilder::pb.directory.getCanonicalPath: "+pb.directory().getCanonicalPath());
					logger.debug("");
					logger.debug("ProcessBuilder::pb.environment:                "+CommonUtils.maskCommand(pb.environment().toString()));
					logger.debug(prefix+"-------------------------------------------------");
					logger.debug("");
				}


			// Execute the command
			Process process = pb.start();

			OutputStream stdOutput = process.getOutputStream();

			InputStream inputStream = process.getInputStream();
			InputStream errorStream = process.getErrorStream();

			inputStreamHandler = new ScriptStreamHandler(inputStream, stdOutput);
			errorStreamHandler = new ScriptStreamHandler(errorStream);

			inputStreamHandler.start();
			errorStreamHandler.start();

			exitValue = process.waitFor();
			
			if (logger.isDebugEnabled()) {
				logger.debug(prefix+"exitValue for process.waitFor is: " + exitValue);
			}

			if (exitValue > 0) {
				logger.error("Error executing command="+CommonUtils.maskCommand(command));
				logger.error("Error="+CommonUtils.maskCommand(getStandardErrorFromCommand().toString()));
			} else {
				if (logger.isInfoEnabled()) {
					logger.info("Successfully executed command:\n"+CommonUtils.maskCommand(command));
					logger.info("Output:\n"+getStandardOutputFromCommand().toString());
				}
			}
			
		} catch (IOException e) {
			CompositeLogger.logException(e, e.getMessage());
			throw new CompositeException(e);
		} catch (InterruptedException e) {
			CompositeLogger.logException(e, e.getMessage());
			throw new CompositeException(e);
		}
		return exitValue;

	}

	/**
	 * Get the standard output (stdout) from the command you just exec'd.
	 */
	public StringBuilder getStandardOutputFromCommand() {
		return inputStreamHandler.getOutputBuffer();
	}

	/**
	 * Get the standard error (stderr) from the command you just exec'd.
	 */
	public StringBuilder getStandardErrorFromCommand() {
		return errorStreamHandler.getOutputBuffer();
	}

}
