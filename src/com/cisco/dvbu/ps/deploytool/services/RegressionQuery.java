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
package com.cisco.dvbu.ps.deploytool.services;

/**
 *  a regression query object
 * 
 * @author mtinius
 *
 */
public class RegressionQuery
{
		public String key;			// combination of datasource.tableURL or datasource.wspath
		public String datasource;	// The datasource identifies which CIS published data source the query belongs to.
		public String durationDelta;//Default duration delta for all queries. Format must be as follows within brackets: [000 00:00:00.0000] 
									//When the difference between duration in file2 and file1 is greater than the default duration then it is an error (outside acceptable range).  
		public String query;		/* The query contains the SQL SELECT statement or the web service input.  
										For SQL queries, the FROM clause must contain the fully qualified Table or Procedure URL. 
										SQL example: SELECT * FROM CATALOG1.SCHEMA1.TABLE1... or SELECT * FROM CATALOG1.SCHEMA1.PROC1(1)
										WS example:
										<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:tem="http://tempuri.org/">
										   <soap:Header/>
										   <soap:Body>
										      <tem:LookupProduct>
										         <tem:LookupProductDesiredproduct>10</tem:LookupProductDesiredproduct>
										      </tem:LookupProduct>
										   </soap:Body>
										</soap:Envelope> */
		public String wsPath; 		/* The web service path is really the endpoint URL or the port path.  
										For Composite Legacy Web Services open the port container in CIS Studio, click on the info tab and look at Endpoint URL: http://localhost:9400/services/testWebService00/testService/testPort.ws.
										The wsPath=/services/testWebService00/testService/testPort.ws
										For Composite New Web Service (6.1 or higher) open the web service in Studio and click on the SOAP tab and Service panel and look at Endpoint and WSDL URLS: /testWebService
										For Soap11 wsPath=/soap11/testWebService
										For Soap12 wsPath=/soap12/testWebService */
		public String wsAction;		// The web service action is the operation to be executed.
		public String wsEncrypt;	// The web service encrypt determines if http (false) or https (true) should be used.
	    public String wsContentType;// The content type for a web service. Legacy Web Serivce and New Web Service soap11=text/xml;charset=UTF-8.  New Web Service soap12=application/soap+xml;charset=UTF-8
}


