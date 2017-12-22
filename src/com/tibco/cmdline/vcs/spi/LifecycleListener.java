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
package com.tibco.cmdline.vcs.spi;

import java.io.File;

/**
 * Implementations of this interface will receive callbacks regarding 
 * the lifecycle operations (i.e. create, update or delete operations)
 * that will be performed by the <tt>DiffMerger</tt> tool against the
 * artifacts maintained in a workspace, that reflects the current contents
 * of a version control system (VCS), in order to synchronize them with
 * the current version of these artifacts, as reflected by Composite Studio.
 * <p>
 * The listeners should use these callbacks to issue VCS commands, that will 
 * prepare the workspace for the submission of the next revision into the VCS.
 * For example, an SVN listener could invoke an <pre>svn add</pre> after a new 
 * file has been created in the workspace by the <tt>DiffMerger</tt>, so that 
 * it will be submitted to the VCS by the next <pre>svn commit</pre>.
 * <p>
 * Callbacks are invoked in two modes:
 * <ul>
 *   <li> Before the lifecycle operation is applied (PRE).
 *   <li> After the lifecycle operation is applied (POST).
 * </ul>
 * <p>
 * Besides handling lifecycle events, the listeners are also expected to commit
 * preamble folders. Preamble folders are part of the preamble (or prefix) of 
 * the resource or resource tree being checked into the VCS. 
 * <p>
 * For example, if a folder f3, located at /shared/f1/f2/f3, is being checked into VCS,
 * while none of the folders /shared, /shared/f1 and /shared/f1/f2 have already been 
 * checked into VCS, the corresponding preamble will be: <code>/shared/f1/f2</code>.
 * In this example, all three preamble folders will be checked in by the callback.
 * <p>
 * The {@link #checkinPreambleFolder(File, boolean)} callback will be called 
 * <i>at most once</i> per commit session (i.e. only if a preamble exists).    
 * <p>
 * In order to be invoked, listeners should:
 * <ul>
 *   <li> Register with the <tt>DiffMerger</tt> tool, by providing their
 *        fully qualified class name as the value of the system property
 *        by name {@link #SYSTEM_PROPERTY}.
 *        For example:
 *        <pre> -Dcom.compositesw.cmdline.vcs.spi.LifecycleListener=com.compositesw.cmdline.vcs.spi.svn.SVNLifecycleListener </pre>
 *   <li> Provide a public, default (i.e. empty) constructor.
 * </ul> 
 */
public interface LifecycleListener {

    /**
     * The name of the system property to use in order to register a specific
     * listener with the <tt>DiffMerger</tt> tool.
     */
    String SYSTEM_PROPERTY = LifecycleListener.class.getName();
   
    /**
     * Lifecycle event kinds. 
     */
    enum Event {
        CREATE,
        UPDATE,
        DELETE
    }
    
    /**
     * Callback mode kinds. 
     */
    enum Mode {
        PRE,
        POST
    }
    
    /**
     * Listeners may use this exception type to report any problems 
     * encountered while performing VCS operations. 
     */
    @SuppressWarnings("serial")
    class VCSException extends Exception {
        
        public VCSException(String message) {
            super(message);
        }
        
        public VCSException(Throwable cause) {
            super(cause);
        }
    }
    
    /**
     * The callback method.
     * @param file The file in the VCS workspace concerned by this method.
     *             <p>
     *             Can be safely assumed not to be <tt>null</tt>.
     * @param event The lifecycle event.
     *              <p>
     *              Can be safely assumed not to be <tt>null</tt>.
     * @param mode The callback mode.
     *             <p>
     *             Can be safely assumed not to be <tt>null</tt>.
     * @param verbose <tt>true</tt> if status messages should be reported;
     *                otherwise <tt>false</tt>.            
     * @throws VCSException If a problem occurs while performing VCS operations. 
     */
    void handle(File file, Event event, Mode mode, boolean verbose) throws VCSException;
    
    /**
     * Preamble folder checkin callback method.
     * @param file An existing folder. 
     * @param verbose <tt>true</tt> if status messages should be reported;
     *                otherwise <tt>false</tt>.            
     * @throws VCSException
     */
    void checkinPreambleFolder(File file, boolean verbose) throws VCSException;
}
