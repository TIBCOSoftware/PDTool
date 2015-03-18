/**
 * (c) 2015 Cisco and/or its affiliates. All rights reserved.
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

package com.cisco.dvbu.ps.common.adapters.common;

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
