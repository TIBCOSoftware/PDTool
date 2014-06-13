/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.cmdline.vcs.primitives;

import com.compositesw.cmdline.archive.PackageCommandConstants;

public abstract class AbstractOptions implements PackageCommandConstants {
    
    private static final String DOT = ".";
    private static final String SLASH = "/";
    
    public static boolean isRoot(String selector) {
        return DOT.equals(selector)
               ||
               SLASH.equals(selector);
    }

    protected void checkRequired(Object arg, String label) {
        if (arg == null) {
            missingArgumentError(label);
            usage(true);
        }        
    }
        
    protected void missingArgumentError(String arg) {
        System.err.println("Missing argument: " + arg);
    }
    
    protected void unknownOptionError(String arg) {
        System.err.println("Unknown option: " + arg);
    }
        
    protected abstract void usage(boolean error);
}
