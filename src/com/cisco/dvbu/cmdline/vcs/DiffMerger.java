/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.cmdline.vcs;

//CODE DIFF: 6.1 and lower vs. 6.2 and higher in method: DeletionFilter()

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cisco.dvbu.cmdline.vcs.spi.LifecycleListener;
import com.cisco.dvbu.cmdline.vcs.spi.LifecycleListener.Event;
import com.cisco.dvbu.cmdline.vcs.spi.LifecycleListener.Mode;
import com.cisco.dvbu.cmdline.vcs.spi.LifecycleListener.VCSException;
import com.compositesw.common.namespace.NamespaceConstants;
import com.compositesw.common.repository.Path;
import com.compositesw.common.vcs.primitives.FilePrimitives;
import com.compositesw.common.vcs.primitives.ResourceType;

/**
 * @author panagiotis
 */
public class DiffMerger {

    private static final String ROOT_NAME = "root";
    
    private final File fromDir, toDir;
    
    private final LifecycleListener vcsListener;
    
    // Print out everything CREATE,UPDATE,DELETE,PRESERVE
   private final boolean verbose;
    
   // Only print out changes (not PRESERVE) -- CREATE,UPDATE,DELETE
    private final boolean verbosechanges;
    
    private final boolean checkinPreamble;
    
    /**
     * <tt>null</tt> if the value of the corresponding option was "." (dot); otherwise not <tt>null</tt>.
     */
    private final String selector;
       
    private DiffMerger(File f1, File f2, boolean invokeVCS, boolean verbose, boolean verbosechanges, boolean checkinPreamble, String selector) throws Exception {
        this.fromDir = f1;
        this.toDir = f2;
        
        if(invokeVCS) {
        	String prop = LifecycleListener.SYSTEM_PROPERTY;
            String vcsListenerClassName = System.getProperty(prop);
            if (vcsListenerClassName == null) throw new IllegalArgumentException("VCS listener cannot be initialized. " + 
                                                                                 "Please set system property: " + 
                                                                                 LifecycleListener.SYSTEM_PROPERTY);
            vcsListener = ((Class<LifecycleListener>) Class.forName(vcsListenerClassName)).newInstance();
        }
        else {
            vcsListener = null;
        }
        
        // Print out everything CREATE,UPDATE,DELETE,PRESERVE
        this.verbose = verbose;
        // Only print out changes (not PRESERVE) -- CREATE,UPDATE,DELETE
        this.verbosechanges = verbosechanges;

        this.checkinPreamble = checkinPreamble;
        
        this.selector = DiffMergerOptions.isRoot(selector)?null:selector;
    }

    private void diffMerge() throws Exception {
        File fromFile = selector==null?fromDir:new File(fromDir, selector);
        ResourceNode fromNode = ResourceNode.from(fromFile, FilePrimitives.CMF_FILTER, selector==null);
        File toFile = selector==null?toDir:new File(toDir, selector);
        ResourceNode toNode   = ResourceNode.from(toFile, FilePrimitives.CMF_FILTER, selector==null);
        
        if (verbose) {
            System.out.println("From Node: " + fromFile);
            System.out.println("To   Node: " + toFile);
        }
        
        DiffNode diffNode = ResourceNode.diffmerge(fromNode, toNode, vcsListener, fromDir, toDir, selector, verbose, verbosechanges, checkinPreamble);
        
        // rollback case
        if (vcsListener == null
            &&
            diffNode != null) {
            Set<StringBuilder> deletedPaths = new HashSet<StringBuilder>();
            Path contextPath = selector == null? new Path("/"): new Path("/" + selector);
            collectDeletedPaths(diffNode, contextPath, deletedPaths);
            
            if (verbose) System.out.println("Deleted Paths: " + deletedPaths);

            String message = RollbackCARBuilder.buildRollbackCAR(fromDir, 
                                                                 selector==null?null:Collections.<String>singleton(selector), 
                                                                 deletedPaths,
                                                                 verbose);
            
            if (verbose && message != null) System.out.println(message);
        }
    }
    
    /**
     * Collects the deleted resource paths under the specified <tt>diffNode</tt>.
     * @param diffNode Assumed not to be <tt>null</tt>.
     * @param diffNodePath The path corresponding to the specified <tt>diffNode</tt>.
     *                     <p>
     *                     Assumed not to be <tt>null</tt>.
     * @param deletedPaths An initially empty set used to collect the deleted resource paths.
     *                     The deleted paths are captured in VCS (encoded) form.
     */
    private static void collectDeletedPaths(DiffNode diffNode, Path diffNodePath, Set<StringBuilder> deletedPaths) {
        if (diffNode.kind == DiffNode.Kind.DELETE) {
            // enhance containers or datasources with generic type (_d)
            StringBuilder typeAwareDiffNodePath = new StringBuilder(diffNodePath.toString());
            if (diffNode.id.type == ResourceNode.Id.DIRECTORY) {
                typeAwareDiffNodePath.append(ResourceType.CONTAINER_OR_DATA_SOURCE.toFileCharacterForm());
            }
            
            if (DeletionFilter.accept(typeAwareDiffNodePath.toString())) {
                deletedPaths.add(new StringBuilder(typeAwareDiffNodePath));
            }
            return;
        }
        
        for (DiffNode child: diffNode.children) {
            diffNodePath.append(child.id.name);
            collectDeletedPaths(child, diffNodePath, deletedPaths);
            diffNodePath.remove(diffNodePath.getNumParts()-1);
        }
    }
    
    public static final void main(String[] args) throws Exception {

// long tm = System.currentTimeMillis();        
        
        DiffMergerOptions options = new DiffMergerOptions(args);                        
        
        DiffMerger diffMerger = new DiffMerger(options.fromRootDir, options.toRootDir, 
                                               options.notifyVCS, options.verbose, options.verbosechanges, options.checkinPreamble, 
                                               options.selector);

        diffMerger.diffMerge();
        
// System.out.println("DIFFMERGE TIME: " + (System.currentTimeMillis()-tm));

    }
    
    public static void startCommand(String baseDir, String homeDir, String[] args) throws Exception {
        main(args);
    }
    
    /**
     * Models a resource tree.
     */
    private static class ResourceNode {
                
        private static final String TAB = "  ";
        private static final String LS = System.getProperty("line.separator");
        
        private final Id id;
        private final PhysicalId physicalId;
        
        private final Set<ResourceNode> children;
        
        ResourceNode(Id id, PhysicalId physicalId) {            
            if (id == null) throw new IllegalArgumentException("Resource id may not be null.");
            if (physicalId == null) throw new IllegalArgumentException("Resource property may not be null.");
            
            this.id = id;
            this.physicalId = physicalId;             
            this.children = new LinkedHashSet<ResourceNode>(); 
        }
        
        void addChild(ResourceNode child) {
            if (child == null) throw new IllegalArgumentException("Child may not be null.");
            children.add(child);
        }
        
        PhysicalId getPhysicalId() { return physicalId; }
        
        public int hashCode() {
            return id.hashCode(); //  + 31 * property.hashCode();
        }
        
        public boolean equals(Object o) {
            if (super.equals(o)) return true;
            
            if (!(o instanceof ResourceNode)) return false;
            
            ResourceNode other = (ResourceNode) o;
            
            return id.equals(other.id);            
        }
        
        public String toString() { return id + "(" + physicalId + ")"; }
        
        public String asTree() { return asTree(0); }
        
        private String asTree(int offset) { 
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < offset; i++) result.append(TAB);
            result.append(toString()); result.append(LS);
            for (ResourceNode child: children) result.append(child.asTree(offset+1));
            return result.toString();
        }
        
        public static ResourceNode from(File file, FileFilter filter, boolean isRoot) {                
            if (!isRoot && filter != null && !filter.accept(file)) return null;
            
            ResourceNode result = new ResourceNode(new Id(isRoot?ROOT_NAME:file.getName(), Id.getType(file)),
                                                   new PhysicalId(file));
//                                                   new Date(file.lastModified()));
            
            List<Pair<ResourceNode, File>> todo = new ArrayList<Pair<ResourceNode, File>>();
            
            if (!file.isDirectory()) return result;
            
            todo.add(new Pair<ResourceNode, File>(result, file)); 
            
            while(!todo.isEmpty()) {
                Pair<ResourceNode, File> pair = todo.remove(0);
                                                
                for (File childFile: pair.s.listFiles()) {
                    if (filter != null && !filter.accept(childFile)) continue;
                    
                    ResourceNode childNode = new ResourceNode(new Id(childFile.getName(), Id.getType(childFile)),
                                                              new PhysicalId(childFile));
//                                                              new Date(childFile.lastModified()));
                    pair.f.addChild(childNode);
                    
                    if (childFile.isDirectory()) todo.add(0, new Pair<ResourceNode, File>(childNode, childFile));
                }
            }
         
            return result;
        }
        
        /** 
         * @return A resource node that encapsulates this node using its parent folder.
         */
        ResourceNode prependPreambleStep() {
            File preambleFolder = physicalId.getFile().getParentFile();
            ResourceNode result = new ResourceNode(new Id(preambleFolder.getName(), Id.DIRECTORY),
                                                   new PhysicalId(preambleFolder));
            
            result.addChild(this);
            return result;
        }
        
        /** 
         * @param r1 A resource node tree. If <tt>null</tt>, r2 should not be <tt>null</tt>.
         * @param r2 A resource node tree. If <tt>null</tt>, r1 should not be <tt>null</tt>.
         * @return The tree diff r2-r1 (i.e. the tree of create, delete and update modifications,
         *         that should be performed on tree r1 to become r2).
         *         <p>
         *         Both CREATE and DELETE subtrees are pruned. PRESERVE subtrees are fully exploded.
         *         <p>
         *         May not be <tt>null</tt>. In particular, identical resource trees result in
         *         an isomorphic, fully exploded PRESERVE tree.
         */
        public static DiffNode diff(ResourceNode r1, ResourceNode r2) {
            if (r1 == null && r2 == null) throw new IllegalArgumentException("Both resource trees may not be null.");
            
            if (r1 == null) return new DiffNode(r2.id, DiffNode.Kind.CREATE);
            if (r2 == null) return new DiffNode(r1.id, DiffNode.Kind.DELETE);
            
            if (!r1.id.equals(r2.id)) throw new IllegalArgumentException("If both resource trees are present they must have a common root." + 
                                                                         " Got R1: " + r1 + " - R2: " + r2);
            
            DiffNode result = new DiffNode(r1.id, DiffNode.Kind.PRESERVE);
            
            Map<ResourceNode.Id, ResourceNode> m1 = new HashMap<ResourceNode.Id, ResourceNode>(); 
            for (ResourceNode node: r1.children) m1.put(node.id, node);
            Map<ResourceNode.Id, ResourceNode> m2 = new HashMap<ResourceNode.Id, ResourceNode>(); 
            for (ResourceNode node: r2.children) m2.put(node.id, node);
            
            List<Triplet<DiffNode, Map<ResourceNode.Id, ResourceNode>, Map<ResourceNode.Id, ResourceNode>>> todo = 
                new ArrayList<Triplet<DiffNode, Map<ResourceNode.Id, ResourceNode>, Map<ResourceNode.Id, ResourceNode>>>();
            todo.add(new Triplet<DiffNode, Map<ResourceNode.Id, ResourceNode>, Map<ResourceNode.Id, ResourceNode>>(result, m1, m2));
            
            while(!todo.isEmpty()) {
                Triplet<DiffNode, Map<ResourceNode.Id, ResourceNode>, Map<ResourceNode.Id, ResourceNode>> triplet = todo.remove(0);
                
                for (Map.Entry<ResourceNode.Id, ResourceNode> entry1: triplet.s.entrySet()) {
                    ResourceNode resourceNode2 = triplet.t.remove(entry1.getKey());
                    
                    if (resourceNode2 == null) {
                        DiffNode childDiffNode = new DiffNode(entry1.getKey(), DiffNode.Kind.DELETE);
                        triplet.f.addChild(childDiffNode);
                    }
                    else {
                        if (resourceNode2.equals(entry1.getValue())) {
                            DiffNode childDiffNode = new DiffNode(entry1.getKey(), DiffNode.Kind.PRESERVE);
                            triplet.f.addChild(childDiffNode);
                            
                            m1 = new HashMap<ResourceNode.Id, ResourceNode>(); 
                            for (ResourceNode node: entry1.getValue().children) m1.put(node.id, node);
                            m2 = new HashMap<ResourceNode.Id, ResourceNode>(); 
                            for (ResourceNode node: resourceNode2.children) m2.put(node.id, node);                            
                            todo.add(new Triplet<DiffNode, Map<ResourceNode.Id, ResourceNode>, Map<ResourceNode.Id, ResourceNode>>(
                                         childDiffNode, m1, m2));
                        }
                        else {
                            DiffNode childDiffNode = new DiffNode(entry1.getKey(), DiffNode.Kind.UPDATE);
                            triplet.f.addChild(childDiffNode);
                        }
                    }
                }
                
                for (Map.Entry<ResourceNode.Id, ResourceNode> entry2: triplet.t.entrySet()) {
                    DiffNode childDiffNode = new DiffNode(entry2.getKey(), DiffNode.Kind.CREATE);
                    triplet.f.addChild(childDiffNode);
                }
            }
            
            return result;
        }

        /** 
         * Performs a merge between the specified resource trees and reports the related tree diff.
         * Conceptually, the merge target is mutated to match the merge source.
         * <p>
         * <b>Note:</b> When used in the VCS->CIS direction, this method prunes the PRESERVEd files 
         * in the CIS temp area, in order to allow the <tt>RollbackCARBuilder</tt> to generate the 
         * minimal import CAR file, or no CAR file, as applicable.
         * 
         * @param r1 A resource node tree. It acts as the merge target. 
         *           <p>
         *           May be <tt>null</tt>.
         * @param r2 A resource node tree. It acts as the merge source. 
         *           <p>
         *           May be <tt>null</tt>.
         * @param vcsListener The VCS lifecycle listener to call back.
         *                    <p>
         *                    <tt>null</tt> if this method is used in the VCS->CIS direction;
         *                    otherwise not <tt>null</tt>.
         * @param f1 The workspace root corresponding to resource r1.
         *           <p>
         *           Assumed to be an existing folder.
         * @param f2 The file location corresponding to resource r2.
         *           <p>
         *           Assumed to be an existing folder.
         * @param selector A relative path (e.g d1/d2).
         *                 <p>
         *                 May be <tt>null</tt>.
         * @param verbose <tt>true</tt> if logging messages should be generated; otherwise <tt>false</tt>.
         * @param verbose <tt>true</tt> if the preamble should be checked in using a separate checkin; 
         *                otherwise <tt>false</tt>.
         *                            
         * @return The tree diff r2-r1 (i.e. the tree of create, delete and update modifications,
         *         that should be performed on tree r1 to become r2).
         *         <p>
         *         Both CREATE and DELETE subtrees are pruned. PRESERVE subtrees are fully exploded.
         *         <p>
         *         May not be <tt>null</tt>. In particular, identical resource trees result in
         *         an isomorphic, fully exploded PRESERVE tree.
         *         
         * @throws IOException
         *         VCSException
         *         IllegalArgumentException If both <tt>r1</tt> and <tt>r2</tt> are <tt>null</tt>.          
         */
        public static DiffNode diffmerge(ResourceNode r1, ResourceNode r2, LifecycleListener vcsListener,
                                         File f1, File f2, String selector, boolean verbose, boolean verbosechanges, boolean checkinPreamble)
            throws IOException, VCSException {
            if (r1 == null && r2 == null) {
                nothingToDiff(selector); return null;
            }
            if (f1 == null) throw new IllegalArgumentException("File f1 may not be null.");
            
            // create case
            if (r1 == null) {
                int preambleLength = 0;
                if (selector != null) {
                    f1 = new File(f1, selector);
                    //if (f1.isFile()) f1 = f1.getParentFile();
                    //f1.mkdirs();
                    f1 = f1.getParentFile();
                    while(!f1.exists()) {
                        r2 = r2.prependPreambleStep(); preambleLength++;
                        f1 = f1.getParentFile();
                    }
                }
                if (!f1.isDirectory()) throw new IllegalArgumentException("Target " + f1 + " must be a folder.");
                
                DiffNode result = new DiffNode(r2.id, DiffNode.Kind.CREATE);
                merge(r2.getPhysicalId().getFile(), f1, preambleLength, vcsListener, verbose, checkinPreamble);

                if (verbose || verbosechanges) {printDiffNode(result, verbosechanges);}
                return result;
            }
            
            // delete case
            if (r2 == null) {
                DiffNode result = new DiffNode(r1.id, DiffNode.Kind.DELETE);
                merge(r1.getPhysicalId().getFile(), null, vcsListener, verbose);

                if (verbose || verbosechanges) {printDiffNode(result, verbosechanges);}
                return result;
            }
            
            // update case
            if (!r1.id.equals(r2.id)) throw new IllegalArgumentException("If both resource trees are present they must have a common root." + 
                                                                         " Got R1: " + r1 + " - R2: " + r2);
            
            // file case 
            if (r1.id.type == ResourceNode.Id.FILE) {
                if (r2.id.type != ResourceNode.Id.FILE) throw new IllegalStateException("Unexpected resource nodes: " + r1.id + " and " + r2.id);
                
                DiffNode result = null;
                if (r1.getPhysicalId().compare(r2.getPhysicalId())) {
                    result = new DiffNode(r1.id, DiffNode.Kind.PRESERVE);
                    // delete PRESERVE'd files in the VCS->CIS case
                    if (vcsListener == null) merge(r1.getPhysicalId().getFile(), null, vcsListener, verbose);
                }
                else {
                    result = new DiffNode(r1.id, DiffNode.Kind.UPDATE); 
                    merge(r2.getPhysicalId().getFile(), r1.getPhysicalId().getFile(), vcsListener, verbose);
                }

                if (verbose || verbosechanges) {printDiffNode(result, verbosechanges);}
                return result;
            }
            
            // folder case
            DiffNode result = new DiffNode(r1.id, DiffNode.Kind.PRESERVE);
            
            Map<ResourceNode.Id, ResourceNode> m1 = new HashMap<ResourceNode.Id, ResourceNode>(); 
            for (ResourceNode node: r1.children) m1.put(node.id, node);
            Map<ResourceNode.Id, ResourceNode> m2 = new HashMap<ResourceNode.Id, ResourceNode>(); 
            for (ResourceNode node: r2.children) m2.put(node.id, node);
            
            List<Quartet<DiffNode, Map<ResourceNode.Id, ResourceNode>, Map<ResourceNode.Id, ResourceNode>, File>> todo = 
                new ArrayList<Quartet<DiffNode, Map<ResourceNode.Id, ResourceNode>, Map<ResourceNode.Id, ResourceNode>, File>>();
            
            todo.add(new Quartet<DiffNode, Map<ResourceNode.Id, ResourceNode>, Map<ResourceNode.Id, ResourceNode>, File>(
                                 result, m1, m2, r1.getPhysicalId().getFile()));
            
            while(!todo.isEmpty()) {
                Quartet<DiffNode, Map<ResourceNode.Id, ResourceNode>, Map<ResourceNode.Id, ResourceNode>, File> quartet = todo.remove(0);
                
                for (Map.Entry<ResourceNode.Id, ResourceNode> entry1: quartet.s.entrySet()) {
                    ResourceNode resourceNode2 = quartet.t.remove(entry1.getKey());
                    
                    if (resourceNode2 == null) {
                        DiffNode childDiffNode = new DiffNode(entry1.getKey(), DiffNode.Kind.DELETE); 
                        merge(entry1.getValue().getPhysicalId().getFile(), null, vcsListener, verbose);
                        quartet.f.addChild(childDiffNode);
                    }
                    else {
                        if (resourceNode2.getPhysicalId().compare(entry1.getValue().getPhysicalId())) {
                            DiffNode childDiffNode = new DiffNode(entry1.getKey(), DiffNode.Kind.PRESERVE);
                            quartet.f.addChild(childDiffNode);
                            
                            m1 = new HashMap<ResourceNode.Id, ResourceNode>(); 
                            for (ResourceNode node: entry1.getValue().children) m1.put(node.id, node);
                            m2 = new HashMap<ResourceNode.Id, ResourceNode>(); 
                            for (ResourceNode node: resourceNode2.children) m2.put(node.id, node);                            
                            todo.add(new Quartet<DiffNode, Map<ResourceNode.Id, ResourceNode>, Map<ResourceNode.Id, ResourceNode>, File>(
                                                 childDiffNode, m1, m2, entry1.getValue().getPhysicalId().getFile()));
                            
                            // delete PRESERVE'd files in the VCS->CIS case
                            if (vcsListener == null
                                &&
                                entry1.getKey().type == Id.FILE) merge(entry1.getValue().getPhysicalId().getFile(), null, vcsListener, verbose);
                        }
                        else {
                            DiffNode childDiffNode = new DiffNode(entry1.getKey(), DiffNode.Kind.UPDATE); 
                            merge(resourceNode2.getPhysicalId().getFile(), entry1.getValue().getPhysicalId().getFile(), vcsListener, verbose);
                            quartet.f.addChild(childDiffNode);
                        }
                        resourceNode2.getPhysicalId().release();
                        entry1.getValue().getPhysicalId().release();
                    }
                }
                
                for (Map.Entry<ResourceNode.Id, ResourceNode> entry2: quartet.t.entrySet()) {
                    DiffNode childDiffNode = new DiffNode(entry2.getKey(), DiffNode.Kind.CREATE); 
                    merge(entry2.getValue().getPhysicalId().getFile(), quartet.q, vcsListener, verbose);
                    quartet.f.addChild(childDiffNode);
                }
            }
            
            if (verbose || verbosechanges) {printDiffNode(result, verbosechanges);}
            return result;
        }
        
        private static void printDiffNode(DiffNode diffNode, boolean verbosechanges) {
            System.out.println("Diff Node:"); System.out.println(diffNode.asTree(0, verbosechanges));
        }
        
        private static void nothingToDiff(String selector) {
            String message = "The specified resource " + selector + " could not be located." + FilePrimitives.LS +
                             "Please check whether one of the following reasons apply:" + FilePrimitives.LS +
                             "1. The resource path was mistyped." + FilePrimitives.LS + 
                             "2. The current user does not have proper access to the resource." + FilePrimitives.LS +
                             "3. The specified resource corresponds to a Designer project." + FilePrimitives.LS +
                             "4. The specified resource is built-in and hence not amenable to versioning.";
            
            System.err.println(message);
        }
        
        /** 
         * Performs DELETE, UPDATE and CREATE operations on the specified source and targets,
         * invoking pre-operation and post-operation callbacks to the VCS listener, if one is provided. 
         * <ul> 
         *   <li> DELETE: source is not <tt>null</tt> and target is <tt>null</tt>.
         *                The source file or subtree is deleted in a depth-first, post-order walk.                 
         *   <li> UPDATE: source is a file and target is a file.
         *                The target gets overwritten with the source contents.
         *   <li> CREATE: source is not <tt>null</tt> and target is a folder.
         *                The source file or subtree is created in a depth-first, pre-order walk
         *                under the specified target folder.              
         *                
         * @param source May not be <tt>null</tt>.
         * @param target May be <tt>null</tt>.
         * @param vcsListener May be <tt>null</tt>.
         */
        private static void merge(File source, File target, LifecycleListener vcsListener, boolean verbose) throws IOException, VCSException {
            merge(source, target, 0, vcsListener, verbose, false);
        }
        
        private static void merge(File source, File target, int preambleLength, LifecycleListener vcsListener,  
                                  boolean verbose, boolean checkinPreamble) throws IOException, VCSException {
            if (source == null) throw new IllegalArgumentException("Merge source may not be null.");
            
            if (target == null) { // delete case
                delete(source, vcsListener, verbose);
            }
            else {
                if (target.isFile()) {
                    if (!source.isFile()) throw new IllegalArgumentException("Merge source " + source + 
                                                                             " must be file, since target " + target +
                                                                             " is file.");
                    update(source, target, vcsListener, verbose);
                }
                else {
                    create(source, target, preambleLength, vcsListener, verbose, checkinPreamble);
                }
            }
        }
        
        /** 
         * @param file The file or subtree to be deleted. 
         *             <p>
         *             Assumed not to be <tt>null</tt>.
         * @param vcsListener May be <tt>null</tt>.
         * @throws VCSException
         */
        // depth-first, post-order
        private static void delete(File file, LifecycleListener vcsListener, boolean verbose) throws VCSException {
            
            if (file.isDirectory()) {
                for (File f: file.listFiles()) {
                    if (!FilePrimitives.CMF_FILTER.accept(f)) continue;
                    delete(f, vcsListener, verbose);
                }                
            }
            // folder or file
            if (vcsListener != null) vcsListener.handle(file, Event.DELETE, Mode.PRE, verbose);
            if (file.exists()) file.delete();
            if (vcsListener != null) vcsListener.handle(file, Event.DELETE, Mode.POST, verbose);
        }
        
        /**
         * Overwrites the contents of the target file with the contents of the source file.
         * @param source Assumed not to be <tt>null</tt>.
         * @param target Assumed not to be <tt>null</tt>.
         * @param vcsListener May be <tt>null</tt>.
         * @throws IOException
         * @throws VCSException
         */
        private static void update(File source, File target, LifecycleListener vcsListener, boolean verbose) 
            throws IOException, VCSException {
            
            if (vcsListener != null) vcsListener.handle(target, Event.UPDATE, Mode.PRE, verbose);
            byte[] sourceContents = FilePrimitives.readFully(source);
            FilePrimitives.writeFully(target, sourceContents, true);
            if (vcsListener != null) vcsListener.handle(target, Event.UPDATE, Mode.POST, verbose);
        }
        
        /**
         * Creates the source file or subtree under the specified target folder.
         * @param source Assumed not to be <tt>null</tt>.
         * @param target Assumed to be an existing folder.
         * @param preambleLength Applicable only if <tt>vcsListener</tt> is not null. May be 0.
         * @param vcsListener May be <tt>null</tt>.
         * @throws IOException
         * @throws VCSException
         */
        private static void create(File source, 
                                   File target,
                                   int preambleLength,
                                   LifecycleListener vcsListener, 
                                   boolean verbose,
                                   boolean checkinPreamble) throws IOException, VCSException {
            List<Pair<File, File>> todo = new ArrayList<Pair<File, File>>();
            todo.add(new Pair<File, File>(source, target));
            
            // effective preamble length
            preambleLength = 2*preambleLength;  // if 0 -> 0
            
            while(!todo.isEmpty()) {
                Pair<File, File> pair = todo.remove(0);
                
                File createdFile = new File(pair.s, pair.f.getName());
                if (vcsListener != null) vcsListener.handle(createdFile, Event.CREATE, Mode.PRE, verbose);
                if (pair.f.isDirectory()) {
                    createdFile.mkdir(); 
                }
                else {
                    byte[] sourceContents = FilePrimitives.readFully(pair.f);
                    FilePrimitives.writeFully(createdFile, sourceContents, true);
                }                
                if (vcsListener != null) {
                    preambleLength--;
                    vcsListener.handle(createdFile, Event.CREATE, Mode.POST, verbose);
                    if (preambleLength == 0
                        &&
                        checkinPreamble) vcsListener.checkinPreambleFolder(new File(target, source.getName()), verbose);
                }
                
                if (pair.f.isDirectory()) {
                    String folderFileName = null;
                    
                    for (File childFile: pair.f.listFiles()) {
                        if (!FilePrimitives.CMF_FILTER.accept(childFile)) continue;
                           
                        int folderFileJobIndex = -1;
                        if (preambleLength > 0 && checkinPreamble) {
                            // breadth-first, pre-order
                            todo.add(new Pair<File, File>(childFile, createdFile));
                            
                            // mark last preamble job                            
                            if (preambleLength == 1) {
                                if (folderFileName == null) folderFileName = pair.f.getName()+FilePrimitives.FILE_EXTENSION; 
                                if (childFile.getName().equals(folderFileName)) folderFileJobIndex = todo.size()-1;
                            }
                        }
                        else {
                            // depth-first, pre-order                
                            todo.add(0, new Pair<File, File>(childFile, createdFile));
                        }
                        
                        // swap last preamble job
                        if (folderFileJobIndex != -1) {
                            Pair<File, File> folderFileJob = todo.remove(folderFileJobIndex);
                            todo.add(0, folderFileJob);
                        }
                    }
                }
            } 
        } 
        
        private static class Id {
            private static final String FILE = "F";
            private static final String DIRECTORY = "D";
            
            private final String name;
            private final String type;
            
            Id(String name, String type) {
                if (name == null) throw new IllegalArgumentException("Resource name may not be null.");
                if (type == null) throw new IllegalArgumentException("Resource type may not be null.");
                
                this.name = name;
                this.type = type;
            }
            
            public int hashCode() {
                return name.hashCode() + 31 * type.hashCode();
            }
            
            public boolean equals(Object o) {
                if (super.equals(o)) return true;
                
                if (!(o instanceof Id)) return false;
                
                Id other = (Id) o;
                
                return name.equals(other.name) 
                       &&
                       type.equals(other.type);
            }
            
            public String toString() { return type + ":" + name; }
            
            static String getType(File file) {
                return file.isDirectory()?DIRECTORY:FILE;
            }
        }
        
        private static class PhysicalId {
            private final File file;
            private byte[] contents;
            private boolean released;
            
            PhysicalId(File file) {
                if (file == null) throw new IllegalArgumentException();
                this.file = file;
            }
            
            /**
             * Used to compare the physical id's of two resources with the same id's.
             * @param other
             * @return
             * @throws IOException
             */
            boolean compare(PhysicalId other) throws IOException {
                // resource nodes corresponding to folders with the same name are always equal 
                if (file.isDirectory()) return true; 
                
                // resource nodes corresponding to files are compared based on the file contents
                if (released) throw new IllegalStateException(file.getAbsolutePath());
                if (other.released) throw new IllegalStateException(other.file.getAbsolutePath());
                
                if (contents == null) contents = FilePrimitives.readFully(file);
                if (other.contents == null) other.contents = FilePrimitives.readFully(other.file);
                
                return Arrays.equals(contents, other.contents);
            }
            
            void release() {
                contents = null; 
                released = true;
            }
            
            File getFile() { return file; }

            public int hashCode() { throw new IllegalStateException(); }
            public boolean equals(Object o) { throw new IllegalStateException(); }
            
            public String toString() {
                return file.getAbsolutePath() + "[" + released + "]";
            }            
        }
    }
    
    /**
     * Models a resource tree diff.
     */
    private static class DiffNode {
        private enum Kind {
            PRESERVE,
            CREATE,
            UPDATE,
            DELETE
        }
        
        private final ResourceNode.Id id;
        private final Kind kind;
        
        private final Set<DiffNode> children;
        
        DiffNode(ResourceNode.Id id, Kind kind) {
            this.id = id;
            this.kind = kind;   
            
            children = new LinkedHashSet<DiffNode>();
        }
        
        void addChild(DiffNode child) {
            if (child == null) throw new IllegalArgumentException("Child may not be null.");
            children.add(child);
        }
        
        public String toString() {
            return id + "->" + kind;
        }
        
        public String asTree() { return asTree(0,false); }
        
        private String asTree(int offset, boolean verbosechanges) {
            StringBuilder result = new StringBuilder();
            // Only print out changes including (CREATE,UPDATE,DELETE)
            if (verbosechanges) {
	            if (!this.toString().contains("PRESERVE")) {
	                for (int i = 0; i < offset; i++) result.append(ResourceNode.TAB);
		            result.append(toString());
		            result.append(ResourceNode.LS);
	            }
	        // Print out everything including (PRESERVE,CREATE,UPDATE,DELETE)
            } else {
                for (int i = 0; i < offset; i++) result.append(ResourceNode.TAB);
	            result.append(toString());
	            result.append(ResourceNode.LS);
            }
            for (DiffNode child: children) result.append(child.asTree(offset+1, verbosechanges));
            return result.toString();
        }
    }
    
    private static class Pair<F, S> {
        final F f;
        final S s;
        
        Pair(F l, S r) {
            this.f = l;
            this.s = r;
        }
    }
    
    private static class Triplet<F, S, T> {
        final F f;
        final S s;
        final T t;
        
        Triplet(F l, S r, T t) {
            this.f = l;
            this.s = r;
            this.t = t;
        }
    }
    
    private static class Quartet<F, S, T, Q> {
        final F f;
        final S s;
        final T t;
        final Q q;
        
        Quartet(F l, S r, T t, Q q) {
            this.f = l;
            this.s = r;
            this.t = t;
            this.q = q;
        }
    }
    
    private static class DeletionFilter {
        private static final String ROOT_FOLDER_FILE = "/" + ROOT_NAME + FilePrimitives.FILE_EXTENSION;
// 2012-10-29 mtinius: uncomment for 6.2 higher
        private static final String POLICY_USER_FOLDER_FILE = NamespaceConstants.PATH_STRING_POLICY_SECURITY_USER + "_d";
        private static boolean accept(String s) {
// 2012-10-29 mtinius: uncomment for 6.1 and lower
//           return !ROOT_FOLDER_FILE.equals(s);
// 2012-10-29 mtinius: uncomment for 6.2 and higher
           return !ROOT_FOLDER_FILE.equals(s) && !POLICY_USER_FOLDER_FILE.equals(s) ;
        }
    }
}
