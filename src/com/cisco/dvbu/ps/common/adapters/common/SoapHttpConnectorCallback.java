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

import com.cisco.dvbu.ps.common.adapters.protocol.SoapEnvelope;

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
