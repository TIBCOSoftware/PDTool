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
package com.tibco.cmdline.vcs;

import com.tibco.cmdline.vcs.primitives.AbstractOptions;
import com.compositesw.common.vcs.primitives.FilePrimitives;
import com.compositesw.common.repository.Path;
import java.io.File;


class DiffMergerOptions extends AbstractOptions {

    //
    // Options
    //
    
    /**
     * Required.
     * <p>
     * Must be an existing folder.
     */    
    String fromRoot;
    
    /**
     * Required.
     * <p>
     * Must be an existing folder.
     */    
    String toRoot;    
    
    /**
     * Optional.
     */
    boolean notifyVCS = false;
    
    /**
     * Optional.
     */
    boolean verbose = false;
    
    /**
     * Optional.
     */
    boolean verbosechanges = false;

    /**
     * Optional.
     */
    boolean checkinPreamble = true;
    
    /**
     * Required.
     * <p>
     * Must be either "." (dot) or a syntactically valid file system path.
     */
    String selector;
    
    //
    // Derived fields.
    //
    File fromRootDir;
    
    File toRootDir;
    
    DiffMergerOptions(String[] args) {
        if (args == null) usage(true);
        
        parse(args);
        
        validate();        
    }
    
    private void parse(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-h")) {
                usage(false);
            }
            else if (args[i].equalsIgnoreCase("-from")) {
                i++;
                if (i == args.length) {
                    missingArgumentError(args[i - 1]);
                    usage(true);
                }
                this.fromRoot = args[i];
            }
            else if (args[i].equalsIgnoreCase("-to")) {
                i++;
                if (i == args.length) {
                    missingArgumentError(args[i - 1]);
                    usage(true);
                }
                this.toRoot = args[i];
            } else if (args[i].equalsIgnoreCase("-selector")) {
                i++;
                if (i == args.length) {
                    missingArgumentError(args[i - 1]);
                    usage(true);
                }
                if (selector == null) {
                    selector = args[i];
                }
                else {
                    multipleSelectors(selector, args[i]);
                    usage(true);
                }
            } else if (args[i].equalsIgnoreCase("-notifyVCS")) {
                this.notifyVCS = true;
            } else if (args[i].equalsIgnoreCase("-verbose")) {
                this.verbose = true;
	        } else if (args[i].equalsIgnoreCase("-verbosechanges")) {
	            this.verbosechanges = true;
	        } 
            else if (args[i].equalsIgnoreCase("-noPreambleCheckin")) {
                this.checkinPreamble = false;
            } 
            else if (args[i].equals("")) {
                /* ignore */
            } else {
                unknownOptionError(args[i]);
                usage(true);
            }
        }
    }
    
    private void validate() {
        // check required options
        checkRequired(fromRoot, "from");
        checkRequired(toRoot, "to");
        checkRequired(selector, "selector");
        
        // check dir constraints
        fromRootDir = new File(fromRoot);
        checkExistingDir(fromRootDir);

        toRootDir = new File(toRoot);
        checkExistingDir(toRootDir);
        
        // check selector
        if (!isRoot(selector)) new Path(selector);
    }
    
    private static void multipleSelectors(String path1, String path2) {
        System.err.println("Found multiple selectors: " + path1 + ", " + path2 + ". Expected exactly one.");
    }
    
    private void checkExistingDir(File dir) {
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println(dir + " must be an existing directory.");
            usage(true);
        }
    }
    
    @Override
    protected void usage(boolean error) {
        System.out
              .println("DiffMerger -from <fromRoot> -to <toRoot>" + FilePrimitives.LS +
                       "[ -notifyVCS ] [ -verbose|-verbosechanges ]" + FilePrimitives.LS +
                       "-selector <selector>");

        if (error) System.exit(ERROR_INVALID_ARGS_ON_CMD_LINE);
        else System.exit(EXIT_WITH_NO_ERRORS);        
    }
    
}
