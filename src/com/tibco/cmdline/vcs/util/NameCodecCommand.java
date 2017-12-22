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
package com.tibco.cmdline.vcs.util;

import com.tibco.cmdline.vcs.primitives.AbstractOptions;
import com.compositesw.cmdline.archive.PackageCommandConstants;
import com.compositesw.common.repository.Path;
import com.compositesw.common.vcs.primitives.ResourceNameCodec;

/**
 * @author panagiotis
 */
public class NameCodecCommand {
    
    public static void startCommand(String baseDir, String homeDir, String[] args) throws Exception {
        Options options = new Options(args);
        
        int exitCode = PackageCommandConstants.EXIT_WITH_NO_ERRORS;
        try {
            if (options.encode) {
                System.out.println("Encoded form: " + ResourceNameCodec.encode(options.namespacePathPath));
            }
            else {
                System.out.println("Decoded form: " + ResourceNameCodec.decode(options.namespacePathPath));
            }            
        }
        catch(RuntimeException e) {
            exitCode = PackageCommandConstants.PKGFILE_EXCEPTION;
            
            System.err.println("Encountered problem: " + e.getMessage());            
        }
        
        System.exit(exitCode);
    }
    
    private static class Options extends AbstractOptions {

        /**
         * Optional.
         */
        private boolean encode = true;

        private String namespacePath;
        
        private Path namespacePathPath;
        
        Options(String[] args) {
            if (args == null) usage(true);
            
            parse(args);
            
            validate();
        }
        
        @Override
        protected void usage(boolean error) {
            System.out.println("Usage: vcs_name_codec [-encode ] [-decode ] <namespacePath>");

            if (error) System.exit(ERROR_INVALID_ARGS_ON_CMD_LINE);
            else System.exit(EXIT_WITH_NO_ERRORS);            
        }        
        
        private void parse(String[] args) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].startsWith("-h")) {
                    usage(false);
                }
                else if (args[i].equalsIgnoreCase("-encode")) {
                    this.encode = true;
                } 
                else if (args[i].equalsIgnoreCase("-decode")) {
                    this.encode = false;
                } 
                else if (args[i].equals("")) {
                    /* ignore */
                } else if (args[i].startsWith("-")) {
                    unknownOptionError(args[i]);
                    usage(true);
                }
                // need to parse this at end since there is no command line
                // option for this
                else {
                    if (namespacePath == null) {
                        namespacePath = args[i];
                    }
                    else {
                        multiplePaths(namespacePath, args[i]);
                        usage(true);
                    }
                }
            }
        }
        
        private static void multiplePaths(String path1, String path2) {
            System.err.println("Found multiple paths: " + path1 + ", " + path2 + ". Expected exactly one.");
        }
        
        private void validate() {
            // check namespacePath
            namespacePathPath = new Path(namespacePath);
        }
    }
}
