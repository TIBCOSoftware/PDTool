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

import com.tibco.ps.common.adapters.protocol.SoapEnvelope;

/**
 * @author vmadired, March 2015
 */

public class SoapHttpConnectorCallback implements ConnectorCallback {
	private String name;
	private String operation;
	private String ep;
	private String action = null;
	private SoapEnvelope request 		= new SoapEnvelope();
	private SoapEnvelope response		= new SoapEnvelope();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public String getEndpoint() {
		return ep;
	}
	
	public void setEndpoint(String ep) {
		this.ep = ep;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getRequestBodyXsl() {
		return request.getBodyXsl();
	}
	
	public void setRequestBodyXsl(String xsl) {
		request.setBodyXsl(xsl);
	}
	
	public String getResponseBodyXsl() {
		return response.getBodyXsl();
	}
	
	public void setResponseBodyXsl(String xsl) {
		response.setBodyXsl(xsl);
	}
}
