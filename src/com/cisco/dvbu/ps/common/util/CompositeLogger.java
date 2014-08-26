/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.common.util;

import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.compositesw.services.system.util.common.Fault;
import com.compositesw.services.system.util.common.MessageEntry;

/**
 * Helper methods to support exception logging. Intended for use by internal
 * application exception handling classes.
 */

public class CompositeLogger  {

	/** default error message */
	private static String defaultMessage = "Error";
	/** logger instance */
	private static Log logger = LogFactory.getLog(CompositeLogger.class);

	/**
	 * log information about the passed exception
	 * 
	 * @param e exception to be logged (ignore if null)
	 * @param logMessage detailed message to be logged (can be null). Contain 
	 * additional information about the issue to help the developer in diagnosing 
	 * the problem
	 */
	public static void logException(Throwable ex, String logMessage) {
		String prependMsg = CommonConstants.applicationErrorPrependMessage;
		Throwable e = ex;
		if (e == null) {
			if (logger.isErrorEnabled()) {
				logger.error(prependMsg+logMessage);
			}
			return;
		}

		// if exception is a soap fault exception then extract error message from message entry
		
			
		
		// if the exception is a remote exception, extract with cause and work
		// with that
		if (e instanceof RemoteException) {
			Throwable remoteCause = e.getCause();
			if (remoteCause != null) {
				e = remoteCause;
			}
		}

		
		String message = e.getMessage();
		if (message == null) {
			message = prependMsg+defaultMessage;
		}
		if (logMessage != null) {
			message = prependMsg+message + "\n" + logMessage;
		}


        Throwable cause = e.getCause();
        if (e instanceof CompositeException)
        {
            if (e instanceof ApplicationException || (cause != null && cause instanceof ApplicationException))
            {
                if (logger.isWarnEnabled())
                {
                    logger.warn(message, e);
                }
            }
            else
            {
                if (logger.isErrorEnabled())
                {
                    logger.error(message, e);
                }                
            }
        }
        else
        {
            if (logger.isErrorEnabled())
            {
                logger.error(message, e);
            }
        }

	}
	
	/**
	 * log information about the passed in soap fault
	 * 
	 * @param e exception to be logged (ignore if null)
	 * @param logMessage detailed message to be logged (can be null). 
	 * @param SoapFault Fault Object contains additional information about the issue to help the developer in diagnosing the problem
	 */
	public static void logException(Throwable ex,String logMessage, Fault f) {

		logException(ex, logMessage);

		if (f.getErrorEntry() != null) {
			logger.error("\n" + ex.getClass().getName() +" ------------------- MessageEntry Begin -------------------\n");
			logger.error(getFaultException(ex, f));
			logger.error(ex.getClass().getName() +" ------------------- MessageEntry End -------------------\n");
	}
		
	}
	
	/**
	 * get the log information about the passed in soap fault
	 * 
	 * @param e exception to be logged (ignore if null)
	 * @param SoapFault Fault Object contains additional information about the issue to help the developer in diagnosing the problem
	 * @return Message String
	 */
	public static String getFaultException(Throwable ex, Fault f) {

		String logMessage = "";
		if (f.getErrorEntry() != null) {
			for (MessageEntry entry : f.getErrorEntry()) {
				logMessage = logMessage + ex.getMessage() + " (detail) " + entry.getName()
							+ ":" + entry.getCode() + ":" + entry.getClass() + ":"
							+ entry.getMessage() + ":\n" + entry.getDetail() + "\n";
		      }                      
		}
		return logMessage;
	}

	/**
	 * get the log information about the passed in soap fault
	 * 
	 * @param e exception to be logged (ignore if null)
	 * @param SoapFault Fault Object contains additional information about the issue to help the developer in diagnosing the problem
	 * @return Message String
	 */
	public static String getFaultMessage(Throwable ex, Fault f) {

		String logMessage = "";
		if (f.getErrorEntry() != null) {
			for (MessageEntry entry : f.getErrorEntry()) {
				logMessage = logMessage + ex.getMessage() + " (detail) " + entry.getName() + ":" + entry.getCode() + ":" + entry.getMessage();
		      }                      
		}
		return logMessage;
	}

	/**
	 * log information about the passed message
	 */
	public static void logInfoMessage(String logMessage) {

		if (logger.isInfoEnabled()) {
			logger.info(logMessage);
		}
	}
}
