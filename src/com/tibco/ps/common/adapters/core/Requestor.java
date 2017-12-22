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

package com.tibco.ps.common.adapters.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tibco.ps.common.adapters.common.AdapterConstants;
import com.tibco.ps.common.adapters.common.AdapterException;
import com.tibco.ps.common.adapters.connect.Connector;

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
