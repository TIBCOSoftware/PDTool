/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.cmdline.vcs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.compositesw.common.archive.ArchiveConstants;
import com.compositesw.common.repository.Path;
import com.compositesw.common.vcs.primitives.FilePrimitives;
import com.compositesw.common.vcs.primitives.PathPrimitives;
import com.compositesw.common.vcs.primitives.ResourceNameCodec;
import com.compositesw.common.vcs.primitives.ResourceType;
import com.compositesw.common.vcs.primitives.ZipPrimitives;
import com.compositesw.xml.xs.XsConstants;

class RollbackCARBuilder {
    private static final Comparator<File> FILE_DEPTH_COMPARATOR = new FileDepthComparator();
    
    private static int XML_DECL_LENGTH;
    static {
        try {
            XML_DECL_LENGTH = "<?xml version=\"1.1\" encoding=\"UTF-8\"?>".getBytes(ArchiveConstants.ENCODING).length;
        }
        catch(Exception e) {}
    }
    
    private static final String CONTENTS_XML = "contents.xml";
    private static final String METADATA_XML = "metadata.xml";
    
    private static String CONTENTS_FILE_CONTENTS = 
        "<?xml version=\"1.1\" encoding=\"UTF-8\"?>" + FilePrimitives.LS +
        "<" + ArchiveConstants.ELEM_ARCHIVE_CONTENTS  + ">" + FilePrimitives.LS +
        "<" + ArchiveConstants.ELEM_HEADER_VERSION + ">" + ArchiveConstants.VALUE_HEADER_VERSION + "</" + ArchiveConstants.ELEM_HEADER_VERSION + ">" + FilePrimitives.LS +
        "<" + ArchiveConstants.ELEM_ARCHIVE_TYPE + " " + ArchiveConstants.ATTR_ARCHIVE_TYPE + "=\"" + ArchiveConstants.ARCHIVE_TYPE_FRAGMENT + "\"/>" + FilePrimitives.LS +
        "<" + ArchiveConstants.ELEM_PACKAGE_NAME + ">" + "Composite Server Archive File</" + ArchiveConstants.ELEM_PACKAGE_NAME + ">" +  FilePrimitives.LS +
//      <packageDescription/>
//      <creationDate>Mon Jul 06 14:37:43 PDT 2009</creationDate>
//      <sourceServer>panagiotis:9400</sourceServer>
//      <sourceOpSystem>Windows XP</sourceOpSystem>
//      <sourceJavaVersion>10.0-b23</sourceJavaVersion>
//      <userDomain>composite</userDomain>
//      <userName>admin</userName>
//      <serverVersion>5.0.0</serverVersion>
        "<" + ArchiveConstants.ELEM_INCLUDE_CACHING + ">true</" + ArchiveConstants.ELEM_INCLUDE_CACHING + ">" + FilePrimitives.LS +
        "<" + ArchiveConstants.ELEM_INCLUDE_PHYSICAL_SOURCE_INFO + ">false</" + ArchiveConstants.ELEM_INCLUDE_PHYSICAL_SOURCE_INFO + ">" + FilePrimitives.LS +
        "<" + ArchiveConstants.ELEM_INCLUDE_CUSTOM_JARS + ">false</" + ArchiveConstants.ELEM_INCLUDE_CUSTOM_JARS + ">" + FilePrimitives.LS +
        "<" + ArchiveConstants.ELEM_INCLUDE_SECURITY + ">false</" + ArchiveConstants.ELEM_INCLUDE_SECURITY + ">" + FilePrimitives.LS +
        "<" + ArchiveConstants.ELEM_INCLUDE_STATISTICS + ">false</" + ArchiveConstants.ELEM_INCLUDE_STATISTICS + ">" + FilePrimitives.LS +
        "<" + ArchiveConstants.ELEM_INCLUDE_DEPENDENCIES + ">false</" + ArchiveConstants.ELEM_INCLUDE_DEPENDENCIES + ">" + FilePrimitives.LS +
        "<" + ArchiveConstants.ELEM_INCLUDE_REQUIRED_USERS + ">false</" + ArchiveConstants.ELEM_INCLUDE_REQUIRED_USERS + ">" + FilePrimitives.LS +
        "<" + ArchiveConstants.ELEM_METADATA_FILES + ">" + FilePrimitives.LS +
        "<" + ArchiveConstants.ELEM_FILE + ">" + METADATA_XML + "</" + ArchiveConstants.ELEM_FILE + ">" + FilePrimitives.LS +
        "</" + ArchiveConstants.ELEM_METADATA_FILES + ">" + FilePrimitives.LS +
//      <adapterFiles>
//        <file>adapter.xml</file>
//      </adapterFiles>
        "</" + ArchiveConstants.ELEM_ARCHIVE_CONTENTS + ">";
    
    private static String METADATA_FILE_PREFIX = 
        "<?xml version=\"1.1\" encoding=\"UTF-8\"?>" + FilePrimitives.LS +
        "<" + ArchiveConstants.ELEM_METADATA_ROOT + " xmlns:xsi=\"" + XsConstants.NS_URI_XSI_2001 + "\">" + FilePrimitives.LS + 
        "<" + ArchiveConstants.ELEM_HEADER_VERSION + ">" + ArchiveConstants.VALUE_HEADER_VERSION + "</" + ArchiveConstants.ELEM_HEADER_VERSION + ">" + FilePrimitives.LS;

    private static String METADATA_FILE_SUFFIX =
        "</" + ArchiveConstants.ELEM_METADATA_ROOT + ">";
    
    private RollbackCARBuilder() {}
    
    /**
     * Converts a specified set of resource files under a specified root and a set of paths 
     * of deleted resources to a CAR file that encapsulates both the resources and the deletions.
     * <p> 
     * The generated "rollback.car" is placed under <tt>root</tt> and the rest of the <tt>root</tt>
     * contents are deleted.  
     *  
     * @param root Must be an existing folder containing at least one CMF file. 
     *             It corresponds to the root of the CIS resource tree. 
     * @param selectors <tt>null</tt>; otherwise, a set of existing relative paths,
     *                  with respect to <tt>root</tt>, that point either to folders 
     *                  that contain CMF files or a CMF file.
     * @param deletedPaths <tt>null</tt>; otherwise a set of resource paths in VCS (encoded) form, 
     *                     that should not intersect with the paths of the selected resources, 
     *                     as defined relative to the <tt>root</tt>.
     * @param verbose <tt>true</tt> if logging messages should be generated; otherwise <tt>false</tt>.
     * @return A status message.
     * 
     * @throws IllegalArgumentException If any of the above constraints are violated, or a "rollback.car" already exists. 
     * @throws IOException If a resource file cannot be read or the rollback car file cannot be written.
     *                     <p>           
     *                     If an exception is thrown, the file tree under <tt>root</tt> remains intact.
     */
    static String buildRollbackCAR(File root, Set<String> selectors, Set<StringBuilder> deletedPaths, boolean verbose) 
        throws IOException {
        StringBuilder result = new StringBuilder();
        
        if (root == null) throw new IllegalArgumentException("Root may not be null.");
        if (!root.exists()) throw new IllegalArgumentException("Root " + root + " does not exist.");
        if (!root.isDirectory()) throw new IllegalArgumentException("Root " + root + " is not a directory.");
        
        // collect top level resource files and folders
        TreeSet<File> todoResourceFiles = new TreeSet<File>(FILE_DEPTH_COMPARATOR);
        List<File> todoResourceFolders = new ArrayList<File>();
        if (selectors == null) {
            todoResourceFolders.add(root);
        }
        else {
            for (String selector: selectors) {
                File descendant = new File(root, selector);
                if (!descendant.exists()) {
                    // ok for pure deletions
                    if (verbose) System.out.println("Selector " + descendant + " does not exist.");
                    continue;
                }
                
                if (descendant.isFile()) {                    
                    if (!FilePrimitives.CMF_FILE_FILTER.accept(descendant)) throw new IllegalArgumentException("Selector " + descendant + " points to illegal file.");
                    todoResourceFiles.add(descendant);
                }
                else {
                    todoResourceFolders.add(descendant);
                }
            }
        }
        
        // collect child resource files by recursing through sub-folders
        while(!todoResourceFolders.isEmpty()) {
            File todoResourceFolder = todoResourceFolders.remove(0);
            
            for (File child: todoResourceFolder.listFiles(FilePrimitives.CMF_FILE_FILTER)) {
                if (child.isFile()) {
                    todoResourceFiles.add(child);
                }
                else {
                    todoResourceFolders.add(0, child);
                }
            }
        }
        
        // if nothing to do, return
        if (todoResourceFiles.isEmpty() 
            &&
            (deletedPaths == null
             ||
             deletedPaths.isEmpty())) {
            result.append("No resource operation was performed.");
            return result.toString();
        }
     
        // check if car file already exists
        File rollbackCar = new File(root, ZipPrimitives.CHECKOUT_CAR);
        if (rollbackCar.exists()) throw new IllegalArgumentException(rollbackCar + " already exists.");
        
        // write car file contents
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(rollbackCar));
            
            ZipEntry entry = new ZipEntry(CONTENTS_XML);
            zos.putNextEntry(entry);
            zos.write(CONTENTS_FILE_CONTENTS.getBytes(ArchiveConstants.ENCODING));
            zos.closeEntry();
            
            entry = new ZipEntry(METADATA_XML);
            zos.putNextEntry(entry);
            zos.write(buildMetadataFile(todoResourceFiles, deletedPaths, verbose));
            zos.closeEntry();
        }
        finally {
            if (zos != null) zos.close();
        }
        
        // report
        result.append("Processed " + todoResourceFiles.size() + " resource entries.");
        if (deletedPaths != null) result.append("Marked " + deletedPaths.size() + " resources for deletion.");
        
        // remove all other contents under root
        for (File child: root.listFiles()) {
            if (!child.equals(rollbackCar)) {
                boolean deleted = FilePrimitives.deleteRecursively(child);
                if (!deleted) result.append(FilePrimitives.LS).append("Could not delete " + child);
            }
        }
        
        return result.toString();
    }
    
    private static byte[] buildMetadataFile(TreeSet<File> resourceFiles, Set<StringBuilder> deletedPaths, boolean verbose) 
        throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        
        result.write(METADATA_FILE_PREFIX.getBytes(ArchiveConstants.ENCODING));
        
        if (deletedPaths != null) {
            result.write(DeletionFragmentBuilder.buildDeletionFragment(deletedPaths, verbose).getBytes(ArchiveConstants.ENCODING));
        }
        
        for (File resourceFile: resourceFiles) {
            byte[] resourceFileContents = FilePrimitives.readFully(resourceFile); 
            result.write(resourceFileContents, XML_DECL_LENGTH, resourceFileContents.length-XML_DECL_LENGTH);
        }
        
        result.write(METADATA_FILE_SUFFIX.getBytes(ArchiveConstants.ENCODING));
        
        return result.toByteArray();
    }
        
    private static class DeletionFragmentBuilder {
        
        private DeletionFragmentBuilder() {}
        
        private static final String DELETES_START = "<" + ArchiveConstants.ELEM_METADATA_DELETES + ">";
        private static final String DELETES_END   = "</" + ArchiveConstants.ELEM_METADATA_DELETES + ">";
        
        private static final String DELETE_PART_1 = "<" + ArchiveConstants.ELEM_METADATA_DELETE + " " +
                                                    ArchiveConstants.ATTR_METADATA_PATH + "=\"";
        private static final String DELETE_PART_2 = "\" " + ArchiveConstants.ATTR_METADATA_TYPE + "=\"";
        private static final String DELETE_PART_3 = "\"/>";
        
        /** 
         * @param deletedPaths A set of file resource paths in VCS (encoded) form.
         *                     <p>
         *                     Assumed not to be <tt>null</tt>. May not contain <tt>null</tt>.
         * @param verbose <tt>true</tt> if logging messages should be generated; otherwise <tt>false</tt>.                     
         *                     
         * @throws IllegalArgumentException If the resource type in a specified path is not legal.
         *                                  <br/> 
         *                                  Here is an example of a legal path: "/folder/resource_table.cmf".
         *                                  <br/>
         *                                  Here is an example of a illegal path: "/folder/resource_tbl.cmf".                       
         */
        private static String buildDeletionFragment(Set<StringBuilder> deletedPaths, boolean verbose) {
            StringBuilder result = new StringBuilder();
            result.append(DELETES_START).append(FilePrimitives.LS);
            
            for (StringBuilder deletedPath: deletedPaths) {
                if (deletedPath == null) throw new IllegalArgumentException("Encountered null path in " + deletedPaths.toString());
                
                PathPrimitives.ResourcePathParts resourcePathParts = PathPrimitives.splitResourcePath(deletedPath);
                String resourceTypeInCarCharacterForm = ResourceType.fromFileCharacterForm(resourcePathParts.resourceType).toCarCharacterForm();
                
                result.append(DELETE_PART_1).append(ResourceNameCodec.decode(
                                                    new Path(resourcePathParts.cisResourcePath.toString())).
                                                    toString()).
                       append(DELETE_PART_2).append(resourceTypeInCarCharacterForm).
                       append(DELETE_PART_3).append(FilePrimitives.LS);
            }
            
            result.append(DELETES_END);    
            
            if (verbose) System.out.println("Built deletion fragment: " + result);            
            
            return result.toString();
        }
    }
    
    private static class FileDepthComparator implements Comparator<File> {

        private FileDepthComparator() {}
        
        /**
         * Compares files (or folders) based on their ancestor rank (i.e. number of ancestors),
         * followed by their alphabetic order.
         * <p>
         * Files with lower rank are ordered lower. 
         * <p>
         * Files corresponding to containers (folders or data sources) have the rank of the 
         * corresponding container. 
         * 
         * @param f1 May not be <tt>null</tt>.
         * @param f2 May not be <tt>null</tt>.
         * @return The result of the comparison based on the <tt>Comparator</tt> contract.
         */
        @Override
        public int compare(File f1, File f2) {
            if (f1 == null || f2 == null) throw new IllegalArgumentException("File arguments must not be null: [F1 = " + f1 + 
                                                                             ", F2 = " + f2 + "]");
            int rankComparison = ancestorRank(f1) - ancestorRank(f2);
            if (rankComparison != 0) return rankComparison;
            
            return f1.getAbsolutePath().compareTo(f2.getAbsolutePath());
        }
        
        /** 
         * @param f Assumed not to be <tt>null</tt>.
         * @return
         */
        private static int ancestorRank(File f) {
            int count = 0; if (isContainerFile(f)) count--;
            
            while((f = f.getParentFile()) != null) count++;
            
            return count;
        }
        
        private static boolean isContainerFile(File f) {
            File parentFile = f.getParentFile();
            if (parentFile == null) return false;
            
            String fileName = f.getName();
            if (!fileName.endsWith(FilePrimitives.FILE_EXTENSION)) return false;
            
            // otherwise
            return parentFile.getName().equals(fileName.substring(0, fileName.length()-FilePrimitives.FILE_EXTENSION.length()));
        }
    }
}
