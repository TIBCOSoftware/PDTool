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

package com.cisco.dvbu.ps.common.adapters.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.adapters.common.AdapterConstants;
import com.cisco.dvbu.ps.common.adapters.common.AdapterException;
import com.cisco.dvbu.ps.common.adapters.connect.Connector;

/**
 * @author vmadired, March 2015
 */

public class Requestor {

	private static Log log	 									= LogFactory.getLog(Requestor.class);
	
	private CisWsClient ca;
	
	public Requestor(CisWsClient ca) {
		this.ca = ca;
	}
	
	protected String execute(String ep, String op, String requestXml) throws AdapterException {
		log.debug("Entered execute: " + ep + AdapterConstants.CONNECTOR_EP_SEPARATOR + op);
		String responseXml = null;
		Connector conn = null;
		try {
			conn = ca.borrowConnector();
			log.debug("requestXml: "+requestXml);
			responseXml = conn.execute(ep + AdapterConstants.CONNECTOR_EP_SEPARATOR + op, requestXml);
			log.debug("responseXml: "+responseXml);
		} catch (AdapterException e) {
			throw e;
		} finally {
			if (conn != null)
				ca.returnConnector(conn);
		}
		log.debug("Exiting execute: " + ep + AdapterConstants.CONNECTOR_EP_SEPARATOR + op);
		return responseXml;
	}
}
