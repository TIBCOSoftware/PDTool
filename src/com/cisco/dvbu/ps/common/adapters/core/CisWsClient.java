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

import java.io.FileInputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import com.cisco.dvbu.ps.common.adapters.common.AdapterConstants;
import com.cisco.dvbu.ps.common.adapters.common.AdapterException;
import com.cisco.dvbu.ps.common.adapters.config.AdapterConfig;
import com.cisco.dvbu.ps.common.adapters.config.ConnectorConfig;
import com.cisco.dvbu.ps.common.adapters.connect.AdapterConnectionPool;
import com.cisco.dvbu.ps.common.adapters.connect.Connector;
import com.cisco.dvbu.ps.common.adapters.connect.SoapHttpConnector;

/**
 * @author vmadired, March 2015
 */

public class CisWsClient {

	public static final Log log								= LogFactory.getLog(CisWsClient.class);
	private AdapterConnectionPool connPool;
	private AdapterConfig config;
	
	private CisWsClient() {}
	
	public CisWsClient(Properties props, String cfgFileName) throws AdapterException {
		log.debug("Entering CisWsClient constructor");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc;
		try {
			FileInputStream in = new FileInputStream(cfgFileName);
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(in);
		} catch (Exception e) {
			log.error("Configuration File Error!");
			throw new AdapterException(1, "Configuration File Error!", null);
		}
		init(props, doc);
		log.debug("Exiting CisWsClient constructor");
		
	}
	
	private void init(Properties props, Document cfgFile) throws AdapterException {
		log.debug("Entering init");
		config = new AdapterConfig(props, cfgFile);
		for(ConnectorConfig connConfig : config.getConnectorConfigs().values()) {
			Connector conn;
			if (AdapterConstants.CONNECTOR_TYPE_SOAPHTTP.equalsIgnoreCase(connConfig.getType())) {
				conn = new SoapHttpConnector(connConfig); 
			} else {
				log.error("Invalid connector type defined in the configuration file!");
				throw new AdapterException(2, "Invalid connector type defined in the configuration file!", null);
			}
			connPool = new AdapterConnectionPool(connConfig);
			connPool.init(conn);
			
		}
		log.debug("Exiting init");
	}
	

	public String sendRequest(String ep, String op, String requestXml) throws AdapterException {
		
		Requestor rq = new Requestor(this);
		return rq.execute(ep, op, requestXml);
	}
	
	protected void finalize() throws Throwable {
		log.debug("Entered finalize");
		if (connPool!=null) {
			connPool.shutdown();
			connPool = null;
		}
		log.debug("Exiting finalize");
	}
	
	protected Connector borrowConnector() throws AdapterException {
		try {
			return connPool.borrowConnector();
		} catch (Exception e) {
			throw new AdapterException(3, "Error borrowing a connector from the pool", e);
		}
	}
	protected void returnConnector(Connector conn) throws AdapterException {
		try {
			connPool.returnConnector(conn);
		} catch (Exception e) {
			throw new AdapterException(4, "Error returning a connector to the pool", e);
		}
	}
	
	public static void main(String[] args) throws Exception {
	    if (args.length < 8) {
	        System.err.println("usage: java com.cisco.dvbu.ps.common.adapters.core.CisWsClient endpointName endpointMethod configXml requestXml host port user password <domain>");
	        System.exit(1);
        }
		org.apache.log4j.BasicConfigurator.configure();
		// DEBUG, INFO, ERROR
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.DEBUG);
		String endpointName = args[0]; // "server"
		String endpointMethod = args[1]; // "getServerAttributes"
		String adapterConfigPath = args[2]; // E:\dev\Workspaces\PDToolGitTest\PDTool_poc\resources\config\6.2.0\cis_adapter_config.xml
		String requestXMLPath = args[3]; // path to request xml
		
		Properties props = new Properties();
		props.setProperty(AdapterConstants.ADAPTER_HOST, args[4]);
		props.setProperty(AdapterConstants.ADAPTER_PORT, args[5]);
		props.setProperty(AdapterConstants.ADAPTER_USER, args[6]);
		props.setProperty(AdapterConstants.ADAPTER_PSWD, args[7]);
		if (args.length == 9)
			props.setProperty(AdapterConstants.ADAPTER_DOMAIN, args[8]);

		// Read the request xml file
		FileInputStream input = new FileInputStream(requestXMLPath);
		byte[] fileData = new byte[input.available()];
		input.read(fileData);
		input.close();
		String requestXml = new String(fileData, "UTF-8");

		// Execute the CIS Web Service Client for an adapter configuration
		CisWsClient cisclient = new CisWsClient(props, adapterConfigPath);
		
		System.out.println("Request: " + requestXml);
		// String requestXml = "<?xml version=\"1.0\"?> <p1:ServerAttributeModule xmlns:p1=\"http://www.dvbu.cisco.com/ps/deploytool/modules\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.dvbu.cisco.com/ps/deploytool/modules file:///e:/dev/Workspaces/PDToolGitTest/PDToolModules/schema/PDToolModules.xsd\">     <!--Element serverAttribute, maxOccurs=unbounded-->     <serverAttribute>         <id>sa1</id>         <name>/server/event/generation/sessions/sessionLoginFail</name>         <type>UNKNOWN</type>         <!--Element value is optional-->         <value>string</value>         <!--Element valueArray is optional-->         <valueArray>             <!--Element item is optional, maxOccurs=unbounded-->             <item>string</item>             <item>string</item>             <item>string</item>         </valueArray>         <!--Element valueList is optional-->         <valueList>             <!--Element item is optional, maxOccurs=unbounded-->             <item>                 <!--Element type is optional-->                 <type>UNKNOWN</type>                 <!--Element value is optional-->                 <value>string</value>             </item>             <item>                 <!--Element type is optional-->                 <type>UNKNOWN</type>                 <!--Element value is optional-->                 <value>string</value>             </item>             <item>                 <!--Element type is optional-->                 <type>UNKNOWN</type>                 <!--Element value is optional-->                 <value>string</value>             </item>         </valueList>         <!--Element valueMap is optional-->         <valueMap>             <!--Element entry is optional, maxOccurs=unbounded-->             <entry>                 <!--Element key is optional-->                 <key>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </key>                 <!--Element value is optional-->                 <value>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </value>             </entry>             <entry>                 <!--Element key is optional-->                 <key>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </key>                 <!--Element value is optional-->                 <value>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </value>             </entry>             <entry>                 <!--Element key is optional-->                 <key>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </key>                 <!--Element value is optional-->                 <value>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </value>             </entry>         </valueMap>     </serverAttribute> </p1:ServerAttributeModule>";
		String response = cisclient.sendRequest(endpointName, endpointMethod, requestXml);
		System.out.println("Response: " + response);
	}

}
