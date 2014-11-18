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
package com.cisco.dvbu.cmdline.vcs.spi.cvs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.cisco.dvbu.cmdline.vcs.spi.AbstractLifecycleListener;

/**
 * A CVS lifecycle listener.
 * 
 * NOTE: Warning handling is specific to CVS.
 *
 * @author panagiotis,cgoodrich,mtinius
 *
 */
public class CVSLifecycleListener extends AbstractLifecycleListener {

  // The folder containing "cvs.exe" or "cvs" must be on PATH
  //private static final String VCS = (File.pathSeparatorChar=='/')?"cvs":"cvs.exe";  
  // VCS_EXEC contains the full path and command.  It is not required to be in the path.
  private static final String CVS = VCS_EXEC;

  private static final String[] CVS_ADD = new String[] { CVS, "add", ""};
  private static final String[] CVS_DELETE = new String[] { CVS, "delete", "-f", ""};
  private static final String[] CVS_COMMIT = new String[] { CVS, "commit", "-m Autocommitting_preamble", ""};

  public CVSLifecycleListener() {}

// there's no abstract handle() method in the abstract class we're extending that needs to be overridden.
//@Override
  public void handle (
    File file,
    Event event,
    Mode mode,
    boolean verbose
  ) throws VCSException {

    switch (event) {

      case CREATE:
        switch (mode) {
          case POST:
            handle (file, CVS_ADD, 2, verbose); // commit is done in scripts once all adds/updates/deletes are complete.
            break;
          default: // do nothing
        }
        break;

      case DELETE:
        switch (mode) {
          case PRE:
            handle (file, CVS_DELETE, 3, verbose); // commit is done in scripts once all adds/updates/deletes are complete.
            break;
          default: // do nothing
        }
        break;
      default: // do nothing
    }
  }

  public void checkinPreambleFolder (
    File file,
    boolean verbose
  ) throws VCSException {
    handle (file, CVS_COMMIT, 3, verbose);
  }

  protected void handle (
    File file,
    String[] commandTemplate,
    int index,
    boolean verbose
  ) throws VCSException {

    // customize VCS command
    Map<Integer, String> commandConfiguration = new HashMap<Integer, String>();

//    commandConfiguration.put (index, file.getName());
    commandConfiguration.put(index, file.getAbsolutePath());

    String[] command = getConfiguredCommand (commandTemplate, commandConfiguration);

    // execute VCS command
    execute (file.getParentFile(), command, verbose);
  }

  protected String getErrorMessages (Process process) throws VCSException {
    StringBuilder result = new StringBuilder();
    BufferedReader sr = new BufferedReader (new InputStreamReader (process.getErrorStream()));

    try {
      String line = null;

      while ((line = sr.readLine()) != null) {

        // for some reason, CVS sends all it's messages to stderr (always has and always will, apparently.)
        // SVN apparently fixed that glaring oversight. All lines containing any of the following content
        // will be ignored as error messages.
        //
        if (!line.contains("cvs warning:") &&
        	!line.contains("cvs.exe warning:") &&

            // checkout
            !line.contains("cvs checkout:") &&
            !line.contains("cvs co:") &&
            !line.contains("cvs get:") &&
            !line.contains("cvs.exe checkout:") &&
            !line.contains("cvs.exe co:") &&
            !line.contains("cvs.exe get:") &&

            // add
            !line.contains("cvs ad:") &&
            !line.contains("cvs add:") &&
            !line.contains("cvs new:") &&
            !line.contains("cvs.exe ad:") &&
            !line.contains("cvs.exe add:") &&
            !line.contains("cvs.exe new:") &&

            // delete
            !line.contains("cvs delete:") &&
            !line.contains("cvs rm:") &&
            !line.contains("cvs remove:") &&
            !line.contains("cvs.exe delete:") &&
            !line.contains("cvs.exe rm:") &&
            !line.contains("cvs.exe remove:") &&

            // check in
            !line.contains("cvs commit:") &&
            !line.contains("cvs ci:") &&
            !line.contains("cvs com:") &&
            !line.contains("cvs.exe commit:") &&
            !line.contains("cvs.exe ci:") &&
            !line.contains("cvs.exe com:") &&

            // update
            !line.contains("cvs update:") &&
            !line.contains("cvs upd:") &&
            !line.contains("cvs up:") &&
            !line.contains("cvs.exe update:") &&
            !line.contains("cvs.exe upd:") &&
            !line.contains("cvs.exe up:"))
         result.append (line).append(LS);
      }

    } catch (IOException e) {
      throw new VCSException(e);
    } finally {
      try { sr.close(); } catch (IOException e) {throw new VCSException (e);}
    }

    return result.toString();
  }

}