/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.cmdline.vcs.client;

import java.io.File;

import com.cisco.dvbu.cmdline.vcs.primitives.AbstractOptions;
import com.compositesw.common.vcs.primitives.FilePrimitives;
import com.compositesw.common.vcs.primitives.ResourceType;
import com.compositesw.common.repository.MetadataConstants;
import com.compositesw.common.repository.Path;
import com.compositesw.common.vcs.primitives.ResourceNameCodec;

class ExportOptions extends AbstractOptions {
    
    //
    // Options
    //
    
    /**
     * Required. 
     * <p>
     * Must be a non-existing folder, or an existing empty folder.
     */
    String tempDir;
    
    /**
     * Required.
     */
    String server;
    
    /**
     * Optional.
     */
    int port = DEFAULT_PORT;
    
    /**
     * Optional.
     */
    boolean encrypt = false;
    
    /**
     * Required.
     */
    String username;
    
    /**
     * Required.
     */
    String password;
    
    /**
     * Optional.
     */
    String domain = DEFAULT_DOMAIN;
    
    /**
     * Optional.
     */
    boolean useFileSystemNames = false;
    
    /**
     * Optional.
     */
    String resourceType;
    
    short resourceTypeType = MetadataConstants.TYPE_NONE;
    
    /**
     * Required.
     */
    String namespacePath;        
    
    //
    // Derived fields
    //
    File tempDirFile;
    
    Path namespacePathPath;
    
    ExportOptions(String[] args) {
        if (args == null) usage(true);
               
        parse(args);
                
        validate();
        
        normalize();
    }
 
    private void parse(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-h")) {
                usage(false);
            }
            else if (args[i].equalsIgnoreCase("-tempDir")) {
                i++;
                if (i == args.length) {
                    missingArgumentError(args[i - 1]);
                    usage(true);
                }
                this.tempDir = args[i];
            }
            else if (args[i].equalsIgnoreCase("-server")) {
                i++;
                if (i == args.length) {
                    missingArgumentError(args[i - 1]);
                    usage(true);
                }
                this.server = args[i];
            } else if (args[i].equalsIgnoreCase("-port")) {
                i++;
                if (i == args.length) {
                    missingArgumentError(args[i - 1]);
                    usage(true);
                }
                try {
                    this.port = Integer.parseInt(args[i]);
                } catch (Throwable e) {
                    usage(true);
                }
            } else if (args[i].equalsIgnoreCase("-encrypt")) {
                this.encrypt = true;
            } else if (args[i].equalsIgnoreCase("-user")) {
                i++;
                if (i == args.length) {
                    missingArgumentError(args[i - 1]);
                    usage(true);
                }
                this.username = args[i];
            } else if (args[i].equalsIgnoreCase("-password")) {
                i++;
                if (i == args.length) {
                    missingArgumentError(args[i - 1]);
                    usage(true);
                }
                this.password = args[i];
            } else if (args[i].equalsIgnoreCase("-domain")) {
                i++;
                if (i == args.length) {
                    missingArgumentError(args[i - 1]);
                    usage(true);
                }
                this.domain = args[i];
            } else if (args[i].equalsIgnoreCase("-resourceType")) {
                i++;
                if (i == args.length) {
                    missingArgumentError(args[i - 1]);
                    usage(true);
                }
                this.resourceType = args[i];
            } else if (args[i].equalsIgnoreCase("-useFileSystemNames")) {
                this.useFileSystemNames = true;
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
    
    private void validate() {
        // check required options
        checkRequired(tempDir, "tempDir");
        checkRequired(server, "server");
        checkRequired(username, "user");
        checkRequired(password, "password");
        checkRequired(namespacePath, "namespacePath");
        
        // check tempDir constraints
        tempDirFile = new File(tempDir);
        if (tempDirFile.exists()) checkTempDir(tempDirFile);         
        else makeTempDir(tempDirFile);
        
        // check resource type
        checkResourceType();
        
        // check namespacePath
        namespacePathPath = isRoot(namespacePath)? new Path("/"):new Path(namespacePath);
    }
    
    private void checkResourceType() {
        if (resourceType != null) {
            try {    
            	// 2012-10-29 mtinius: added a conversion of the resource type to the numeric form instead of converting the file character form.
            	//    Note: prior to invocation of this method the VCSManagerImpl.java converts FOLDER to CONTAINER and "definitions" to "DEFINITION_SET", as well as converting lower case types to upper case.  
            	//    For this to work, the only valid types are case sensitive and as follows:     
            	//         CONTAINER_OR_DATA_SOURCE, CONTAINER, DATA_SOURCE, DEFINITION_SET, LINK, PROCEDURE, TABLE, TREE, TRIGGER, RELATIONSHIP, MODEL, POLICY 
            	//    Convert the character form of resource type to the numeric form which is used by the export.
            	resourceTypeType = ResourceType.valueOf(resourceType).toInMemoryNumericForm();
 //             System.out.println("checkResourceType:: Resource Type found: " + resourceType + "  InMemoryNumericForm="+resourceTypeType);
            	// Original code:
//                resourceTypeType = ResourceType.fromFileCharacterForm(((ResourceType.FILE_CHARACTER_FORM_DELIMITER+resourceType)).toCharArray()).toInMemoryNumericForm();                
            }
            catch(Exception e) {
                System.err.println("Unknown resource type: " + resourceType);
                usage(true);
            }
        }
    }
    
    private void normalize() {
        if (useFileSystemNames) {
            namespacePath = ResourceNameCodec.decode(namespacePathPath).toString();
        }
    }
        
    /** 
     * @param file An existing file.
     */
    private void checkTempDir(File file) {
        if (!file.isDirectory() || file.list().length != 0) {
            System.err.println("tempDir " + file + " must be an empty directory.");  
            usage(true);
        }
    }
    
    private void makeTempDir(File file) {
        boolean success = file.mkdirs();
        if (!success) {
            System.err.println("Unable to create tempDir: " + file);
            usage(true);
        }
    }
        
    private static void multiplePaths(String path1, String path2) {
        System.err.println("Found multiple paths: " + path1 + ", " + path2 + ". Expected exactly one.");
    }
    
    protected void usage(boolean error) {
        System.out.println("Usage: vcs_export -tempDir <empty_directory>" + FilePrimitives.LS +
                       "-server <hostname> [ -port <port> ] [ -encrypt ]" + FilePrimitives.LS +
                       "-user <user> -password <password> [ -domain <domain> ]" + FilePrimitives.LS +
//                      + "[ -verbose ] [ -quiet ]\n                  "
                       "[ -resourceType <resourceType> ] [ -useFileSystemNames ]" + FilePrimitives.LS +
                       "<namespacePath>");

        if (error) System.exit(ERROR_INVALID_ARGS_ON_CMD_LINE);
        else System.exit(EXIT_WITH_NO_ERRORS);
    }
}
