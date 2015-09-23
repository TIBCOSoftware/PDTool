package com.cisco.dvbu.ps.common.exception;
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

/**
 * Composite exception is the base exception for all exceptions generated in the application.
 * This is a Runtime exception that can be directly instantiated or extended.
 * All exceptions in the application must extend from this Composite exception.
 * One of the uses of this exception is to use it to wrap 3rd party exceptions.
 * 
 */
public class CompositeException extends RuntimeException {

	private static final long serialVersionUID = 4758033733688442366L;

    /* cause exception class name */
    private String causeClassName = null;

	/** Constructs a new exception with <code>null</code> as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public CompositeException()
    {
        super();
        initialize();
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public CompositeException(String message, Throwable cause)
    {
        super(message, cause);
        initialize();
    }

    /** Constructs a new exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for 
     *          later retrieval by the {@link #getMessage()} method.
     */
    public CompositeException(String message)
    {
        super(message);
        initialize();
    }

    /** Constructs a new exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public CompositeException(Throwable cause)
    {
        super(cause);
        initialize();
    }

    /** an internal method to perform additional initializations for this exception.
     */
    protected void initialize()
    {
        if (getCause() != null)
        {
            causeClassName = getCause().getClass().getName();
        }
    }
	
    /**
     * @return Returns the causeClassName.
     */
    public String getCauseClassName()
    {
        return causeClassName;
    }

    /**
     * @param causeClassName The causeClassName to set.
     */
    public void setCauseClassName(String causeClassName)
    {
        this.causeClassName = causeClassName;
    }

}
