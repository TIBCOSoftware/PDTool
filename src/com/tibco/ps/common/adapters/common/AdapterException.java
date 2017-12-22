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

package com.tibco.ps.common.adapters.common;

import java.io.Serializable;

/**
 * @author vmadired, March 2015
 */

public class AdapterException extends Exception implements AdapterError, Serializable {
	private final int errorCode;
	private final String errorMessage;
	private static final long serialVersionUID = 1L;
	
	public AdapterException(int errorCode, String errorMessage, Throwable cause) {
		//super(errorCode + " " + errorMessage, cause);
		super("", cause);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	// mtinius - remove override
	//@Override
	public int getErrorCode() {
		return errorCode;
	}

	// mtinius - remove override
	//@Override
	public String getErrorMessage() {
		return errorMessage;
	}

    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append(errorCode);
        builder.append(": ");
        builder.append(errorMessage);
        builder.append('\n');


        //append root causes and text from this exception first.
        if(getMessage() != null) {
            builder.append('\n');
            if(getCause() == null){
                builder.append(getMessage());
            } else if(!getMessage().equals(getCause().toString())){
                builder.append(getMessage());
            }
        }
        appendException(builder, getCause());

        return builder.toString();
    }

    private void appendException(
            StringBuilder builder, Throwable throwable){
        if(throwable == null) return;
        appendException(builder, throwable.getCause());
        builder.append(throwable.toString());
        builder.append('\n');
    }	
}
