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
package com.cisco.dvbu.ps.deploytool.dao.wsapi;

/**
 * Initial Version:	Mike Tinius :: 6/8/2011		
 * Modifications:	initials :: Date
 * 
 * Modified to use pure Soap invocation instead of CisAdminApi.port calls : mtinius :: 3/13/15
 * 
 */

import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import com.cisco.dvbu.ps.common.adapters.common.AdapterException;
import com.cisco.dvbu.ps.common.adapters.core.CisWsClient;
import com.cisco.dvbu.ps.common.adapters.util.XmlUtils;
import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.CompositeLogger;
import com.cisco.dvbu.ps.common.util.XMLUtils;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeDefType;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeModule;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeType;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.compositesw.services.system.util.common.Fault;

public class ServerAttributeWSDAOImpl implements ServerAttributeDAO {

	private static String prefix = "ServerAttributeWSDAOImpl";
	private static Log logger = LogFactory.getLog(ServerAttributeWSDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO#takeServerAttributeAction(java.lang.String, ServerAttributeModule, java.lang.String, java.lang.String)
	 */
//	@Override
	public void takeServerAttributeAction(String actionName, ServerAttributeModule serverAttributeModule, String serverId, String pathToServersXML) throws CompositeException {
		
		String methodName = "takeServerAttributeAction";
		// Set the web service endpoint and method
		String endpointName = "server";
		String endpointMethod = "updateServerAttributes";

		int attrSize = 0;
		if(logger.isDebugEnabled()) {
			if (serverAttributeModule != null && serverAttributeModule.getServerAttribute() != null)
				attrSize = serverAttributeModule.getServerAttribute().size();
			
			logger.debug(prefix+"."+methodName+"(actionName , serverAttributeModule, serverId, pathToServersXML).  actionName="+actionName+"  #serverAttributeModule.getServerAttribute()="+attrSize+"  serverId="+serverId+"  pathToServersXML="+pathToServersXML);
		}

		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, prefix+"."+methodName, logger);

		// Ping the Server to make sure it is alive and the values are correct.
		//WsApiHelperObjects.pingServer(targetServer, true);

		// Get server properties from the target server
		Properties props = WsApiHelperObjects.getServerProperties(targetServer);

		// Get the adapter config path environment variable
		String adapterConfigPath = System.getProperty("PDTOOL_ADAPTER_CONFIG_PATH");
		if (adapterConfigPath == null) 
			throw new CompositeException(prefix+"::The enviornment variable PDTOOL_ADAPTER_CONFIG_PATH was not set properly during DeployManagerUtil.initAdapter.");

		try {	
			if(actionName.equalsIgnoreCase(ServerAttributeDAO.action.UPDATE.name())){
				
				// For display debug purposes only
				if(logger.isDebugEnabled()) {
					String attrList = "";
					if (serverAttributeModule != null && serverAttributeModule.getServerAttribute() != null) {
						List<ServerAttributeType> attributeList = serverAttributeModule.getServerAttribute(); 
						for (ServerAttributeType attr : attributeList) {
							if (attrList.length() != 0)
								attrList = attrList + ", ";
							if (attr.getType().toString().equalsIgnoreCase("PASSWORD_STRING"))
								attrList = attrList + attr.getName() + "=********";
							else {
								if (attr.getValue() != null)
									attrList = attrList + attr.getName() + "=" + attr.getValue();
								else
									attrList = attrList + attr.getName() + "=" + attr.getType();
							}
						}
					}
					logger.debug(prefix+"."+methodName+"().  Invoking "+endpointMethod+"(\"["+attrList+"]\").  #attributeList="+attrSize);
				}

				/*****************************************************
				 * Web Service Client Connection
				 *****************************************************/
				if(logger.isDebugEnabled()) {
					logger.debug(prefix+"."+methodName+"().  Creating CIS client web service connection.");
				}
				// Execute the CIS Web Service Client for an adapter configuration connection
				CisWsClient cisclient = new CisWsClient(props, adapterConfigPath);
	
				// Setup the request
				String requestXML = XMLUtils.getStringFromDocument(serverAttributeModule);
	
				if(logger.isDebugEnabled()) {
					logger.debug(prefix+"."+methodName+"().  Invoking web service endpoint name="+endpointName+" and method="+endpointMethod+"  Request:\n" + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(requestXML))+"\n");
				}
				/*****************************************************
				 * Invoke Method=updateServerAttributes
				 *****************************************************/
				String command = endpointMethod;
				
				// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
				if (CommonUtils.isExecOperation()) 
				{					
					// Invoke the web service method
					String response = cisclient.sendRequest(endpointName, endpointMethod, requestXML);
			
					if(logger.isDebugEnabled()) {
						// Format for XML pretty print
						logger.debug(prefix+"."+methodName+"().  Response: " + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(response)));
					}
				} else {
					logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
				}
			}		
		} catch (AdapterException e) {
			// Convert XML String to ServerAttributeModule Object for easier processing
			//String schemaPropertyName = "ADMIN_API_LOCATION";
			//Object obj = XMLUtils.getJavaObjectFromXMLOtherSchema(e.getErrorMessage(), schemaPropertyName);
			//Fault fault = (Fault) obj;
			//CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), ServerAttributeDAO.action.UPDATE.name(), "ServerAttribute", "serverAttributeModule.getServerAttribute()", targetServer), fault);

			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), ServerAttributeDAO.action.UPDATE.name(), "ServerAttribute", "serverAttributeModule.getServerAttribute()", targetServer));
			throw new ApplicationException(e.getMessage(), e);
		} catch (Exception e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), ServerAttributeDAO.action.UPDATE.name(), "ServerAttribute", "serverAttributeModule.getServerAttribute()", targetServer));
			throw new ApplicationException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO#getServerAttribute(java.lang.String, java.lang.String, java.lang.String)
	 * Get server attributes for the given paths.
	 */
//	@Override
	public ServerAttributeModule getServerAttribute(String serverId, String serverAttrPath, String pathToServersXML)  throws CompositeException {

		String methodName = "getServerAttribute";
		// Set the web service endpoint and method
		String endpointName = "server";
		String endpointMethod = "getServerAttributes";

		if(logger.isDebugEnabled()) {		
			logger.debug(prefix+"."+methodName+"(serverId, serverAttrPath, pathToServersXML).  serverId="+serverId+"  serverAttrPath="+serverAttrPath+"  pathToServersXML="+pathToServersXML);
		}
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, prefix+"."+methodName, logger);

		// Ping the Server to make sure it is alive and the values are correct.
		//WsApiHelperObjects.pingServer(targetServer, true);

		// Get server properties from the target server
		Properties props = WsApiHelperObjects.getServerProperties(targetServer);

		// Get the adapter config path environment variable
		String adapterConfigPath = System.getProperty("PDTOOL_ADAPTER_CONFIG_PATH");
		if (adapterConfigPath == null) 
			throw new CompositeException(prefix+"::The enviornment variable PDTOOL_ADAPTER_CONFIG_PATH was not set properly during DeployManagerUtil.initAdapter.");

		try {	
			/*****************************************************
			 * Web Service Client Connection
			 *****************************************************/
			if(logger.isDebugEnabled()) {
				logger.debug(prefix+"."+methodName+"().  Creating CIS client web service connection.");
			}
			// Execute the CIS Web Service Client for an adapter configuration connection
			CisWsClient cisclient = new CisWsClient(props, adapterConfigPath);

			// Setup the request
			String requestXML =
					"<?xml version=\"1.0\"?>"+
					"<p1:ServerAttributeModule xmlns:p1=\"http://www.dvbu.cisco.com/ps/deploytool/modules\">"+
					"    <serverAttribute>"+
					"        <id>id1</id>"+
					"        <name>"+serverAttrPath+"</name>"+
					"        <type>STRING</type>"+
					"    </serverAttribute>"+
					"</p1:ServerAttributeModule>";

			if(logger.isDebugEnabled()) {
				logger.debug(prefix+"."+methodName+"().  Invoking web service endpoint name="+endpointName+" and method="+endpointMethod+"  Request:\n" + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(requestXML))+"\n");
			}
			/*****************************************************
			 * Invoke Method=getServerAttributes
			 *****************************************************/
			// Invoke the web service method
			// String requestXml = "<?xml version=\"1.0\"?> <p1:ServerAttributeModule xmlns:p1=\"http://www.dvbu.cisco.com/ps/deploytool/modules\">  <serverAttribute>         <id>sa1</id>         <name>/server/event/generation/sessions/sessionLoginFail</name>         <type>UNKNOWN</type>         <!--Element value is optional-->         <value>string</value>         <!--Element valueArray is optional-->         <valueArray>             <!--Element item is optional, maxOccurs=unbounded-->             <item>string</item>             <item>string</item>             <item>string</item>         </valueArray>         <!--Element valueList is optional-->         <valueList>             <!--Element item is optional, maxOccurs=unbounded-->             <item>                 <!--Element type is optional-->                 <type>UNKNOWN</type>                 <!--Element value is optional-->                 <value>string</value>             </item>             <item>                 <!--Element type is optional-->                 <type>UNKNOWN</type>                 <!--Element value is optional-->                 <value>string</value>             </item>             <item>                 <!--Element type is optional-->                 <type>UNKNOWN</type>                 <!--Element value is optional-->                 <value>string</value>             </item>         </valueList>         <!--Element valueMap is optional-->         <valueMap>             <!--Element entry is optional, maxOccurs=unbounded-->             <entry>                 <!--Element key is optional-->                 <key>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </key>                 <!--Element value is optional-->                 <value>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </value>             </entry>             <entry>                 <!--Element key is optional-->                 <key>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </key>                 <!--Element value is optional-->                 <value>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </value>             </entry>             <entry>                 <!--Element key is optional-->                 <key>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </key>                 <!--Element value is optional-->                 <value>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </value>             </entry>         </valueMap>     </serverAttribute> </p1:ServerAttributeModule>";
			String response = cisclient.sendRequest(endpointName, endpointMethod, requestXML);
	
			if(logger.isDebugEnabled()) {
				// Format for XML pretty print
				logger.debug(prefix+"."+methodName+"().  Response: " + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(response)));
			}
			
			// Convert XML String to ServerAttributeModule Object for easier processing
			Object obj = XMLUtils.getJavaObjectFromXML(response);
			ServerAttributeModule serverAttribute = (ServerAttributeModule) obj;
			
			if (serverAttribute.getServerAttribute() != null && serverAttribute.getServerAttribute().size() > 0) {
				// Generate and ID using the first token in the server attribute definition path name
				String id = CommonUtils.getToken(1, serverAttribute.getServerAttribute().get(0).getName()) + "1";
				// Set the id
				serverAttribute.getServerAttribute().get(0).setId(id);
			}
			
			return serverAttribute;
			
		} catch (Exception e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), methodName, "ServerAttribute", serverAttrPath, targetServer));
			throw new ApplicationException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO#getServerVersion(java.lang.String, java.lang.String, java.lang.String)
	 * Get server attributes for the given paths.
	 */
//	@Override
	public String getServerVersion(String serverId, String pathToServersXML)  throws CompositeException {

		String methodName = "getServerVersion";
		// Set the web service endpoint and method
		String endpointName = "server";
		String endpointMethod = "getServerAttributes";
		String serverAttrPath = "/server/config/info/versionFull";

		if(logger.isDebugEnabled()) {		
			logger.debug(prefix+"."+methodName+"(serverId, pathToServersXML).  serverId="+serverId+"  pathToServersXML="+pathToServersXML);
		}
		// Set the CIS Version
		String version = null;
		
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, prefix+"."+methodName, logger);

		// Ping the Server to make sure it is alive and the values are correct.
		//WsApiHelperObjects.pingServer(targetServer, true);
		
		// Get server properties from the target server
		Properties props = WsApiHelperObjects.getServerProperties(targetServer);

		// Get the adapter config path environment variable
		String adapterConfigPath = System.getProperty("PDTOOL_ADAPTER_CONFIG_PATH");
		if (adapterConfigPath == null) 
			throw new CompositeException(prefix+"::The enviornment variable PDTOOL_ADAPTER_CONFIG_PATH was not set properly during DeployManagerUtil.initAdapter.");

		try {	
			/*****************************************************
			 * Web Service Client Connection
			 *****************************************************/
			if(logger.isDebugEnabled()) {
				logger.debug(prefix+"."+methodName+"().  Creating CIS client web service connection.");
			}
			// Execute the CIS Web Service Client for an adapter configuration connection
			CisWsClient cisclient = new CisWsClient(props, adapterConfigPath);
		
			// Setup the request
			String requestXML =
					"<?xml version=\"1.0\"?>"+
					"<p1:ServerAttributeModule xmlns:p1=\"http://www.dvbu.cisco.com/ps/deploytool/modules\">"+
					"    <serverAttribute>"+
					"        <id>id1</id>"+
					"        <name>"+serverAttrPath+"</name>"+
					"        <type>STRING</type>"+
					"    </serverAttribute>"+
					"</p1:ServerAttributeModule>";

			if(logger.isDebugEnabled()) {
				logger.debug(prefix+"."+methodName+"().  Invoking web service endpoint name="+endpointName+" and method="+endpointMethod+"  Request:\n" + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(requestXML))+"\n");
			}
			/*****************************************************
			 * Invoke Method=getServerAttributes
			 *****************************************************/
			// Invoke the web service method
			// String requestXml = "<?xml version=\"1.0\"?> <p1:ServerAttributeModule xmlns:p1=\"http://www.dvbu.cisco.com/ps/deploytool/modules\">  <serverAttribute>         <id>sa1</id>         <name>/server/event/generation/sessions/sessionLoginFail</name>         <type>UNKNOWN</type>         <!--Element value is optional-->         <value>string</value>         <!--Element valueArray is optional-->         <valueArray>             <!--Element item is optional, maxOccurs=unbounded-->             <item>string</item>             <item>string</item>             <item>string</item>         </valueArray>         <!--Element valueList is optional-->         <valueList>             <!--Element item is optional, maxOccurs=unbounded-->             <item>                 <!--Element type is optional-->                 <type>UNKNOWN</type>                 <!--Element value is optional-->                 <value>string</value>             </item>             <item>                 <!--Element type is optional-->                 <type>UNKNOWN</type>                 <!--Element value is optional-->                 <value>string</value>             </item>             <item>                 <!--Element type is optional-->                 <type>UNKNOWN</type>                 <!--Element value is optional-->                 <value>string</value>             </item>         </valueList>         <!--Element valueMap is optional-->         <valueMap>             <!--Element entry is optional, maxOccurs=unbounded-->             <entry>                 <!--Element key is optional-->                 <key>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </key>                 <!--Element value is optional-->                 <value>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </value>             </entry>             <entry>                 <!--Element key is optional-->                 <key>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </key>                 <!--Element value is optional-->                 <value>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </value>             </entry>             <entry>                 <!--Element key is optional-->                 <key>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </key>                 <!--Element value is optional-->                 <value>                     <!--Element type is optional-->                     <type>UNKNOWN</type>                     <!--Element value is optional-->                     <value>string</value>                 </value>             </entry>         </valueMap>     </serverAttribute> </p1:ServerAttributeModule>";
			String response = cisclient.sendRequest(endpointName, endpointMethod, requestXML);
	
			if(logger.isDebugEnabled()) {
				// Format for XML pretty print
				logger.debug(prefix+"."+methodName+"().  Response: " + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(response)));
			}

			// Convert string to a DOM object
			Document responseXML = XmlUtils.stringToDocument(response);
			// Get the XPath for "value"
			version = XmlUtils.getXpathValue("/ServerAttributeModule/serverAttribute/value", responseXML);
			
			return version;
			
		} catch (Exception e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), methodName, "ServerAttribute", serverAttrPath, targetServer));
			throw new ApplicationException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO#getServerAttributesFromPath(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 * Get the child server attributes of the given path.  This will be an empty list for attribute definitions that are not of the "FOLDER" type.
	 */
//	@Override
	public ServerAttributeModule getServerAttributesFromPath(String serverId, String startpath, String pathToServersXML, String updateRule) {
		
		String methodName = "getServerAttributesFromPath";
			
		if(logger.isDebugEnabled()) {		
			logger.debug(prefix+"."+methodName+"(serverId, startpath, pathToServersXML, updateRule).  serverId="+serverId+"  startpath="+startpath+"  pathToServersXML="+pathToServersXML+"  updateRule="+updateRule);
		}
		ServerAttributeModule serverAttribute = new ServerAttributeModule();
		
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, prefix+"."+methodName, logger);

		// Ping the Server to make sure it is alive and the values are correct.
		//WsApiHelperObjects.pingServer(targetServer, true);

		// Get server properties from the target server
		Properties props = WsApiHelperObjects.getServerProperties(targetServer);

		// Get the adapter config path environment variable
		String adapterConfigPath = System.getProperty("PDTOOL_ADAPTER_CONFIG_PATH");
		if (adapterConfigPath == null) 
			throw new CompositeException(prefix+"::The enviornment variable PDTOOL_ADAPTER_CONFIG_PATH was not set properly during DeployManagerUtil.initAdapter.");
		
		try {	
			/*****************************************************
			 * Web Service Client Connection
			 *****************************************************/
			if(logger.isDebugEnabled()) {
				logger.debug(prefix+"."+methodName+"().  Creating CIS client web service connection.");
			}
			// Execute the CIS Web Service Client for an adapter configuration connection
			CisWsClient cisclient = new CisWsClient(props, adapterConfigPath);

			addServerAttributesFromPath(cisclient, updateRule, serverAttribute, startpath, targetServer);
			
			return serverAttribute;
		} 
		catch (Exception e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), methodName, "ServerAttribute", startpath, targetServer));
			throw new ApplicationException(e.getMessage(), e);
		}
	}
	
	private void addServerAttributesFromPath(CisWsClient cisclient, String updateRule, ServerAttributeModule serverAttribute, String startpath, CompositeServer targetServer){

		String methodName = "addServerAttributesFromPath";
		// Set the web service endpoint and method
		String endpointName = "server";
		String endpointMethod = "getServerAttributeDefChildren";

		try {	
			// Setup the request
			String requestXML =
					"<?xml version=\"1.0\"?>"+
					"<p1:ServerAttributeModule xmlns:p1=\"http://www.dvbu.cisco.com/ps/deploytool/modules\">"+
					"    <serverAttributeDef>"+
					"        <id>id1</id>"+
					"        <name>"+startpath+"</name>"+
					"        <type>STRING</type>"+
					"    </serverAttributeDef>"+
					"</p1:ServerAttributeModule>";

			if(logger.isDebugEnabled()) {
				logger.debug(prefix+"."+methodName+"().  Invoking web service endpoint name="+endpointName+" and method="+endpointMethod+"  Request:\n" + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(requestXML))+"\n");
			}

			/*****************************************************
			 * Invoke Method=getServerAttributeDefChildren
			 *****************************************************/
			// Invoke the web service method
			String response = cisclient.sendRequest(endpointName, endpointMethod, requestXML);

			if(logger.isDebugEnabled()) {
				// Format for XML pretty print
				logger.debug(prefix+"."+methodName+"().  Response: " + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(response)));
			}
			
			// Convert XML String to ServerAttributeModule Object for easier processing
			Object obj1 = XMLUtils.getJavaObjectFromXML(response);
			ServerAttributeModule sam1 = (ServerAttributeModule) obj1;
			
			// Iterate over the ServerAttributeModule Response document
			if (sam1 != null && sam1.getServerAttributeDef() != null && sam1.getServerAttributeDef().size() > 0) {
				List<ServerAttributeDefType> saDefList = sam1.getServerAttributeDef();
				for (ServerAttributeDefType saDef : saDefList) {

					// Get current attribute definition path and type
					String currentPath = saDef.getName();
					String currentType = saDef.getType().toString();

					// Determine the server "updateRule"
					String updateRuleFromServer = null;
					if (saDef.getUpdateRule() == null) {
						logger.debug("Update Rule is null for serverAttributeDef.getUpdateRule()");
					} else {
						updateRuleFromServer = saDef.getUpdateRule().toString();
						logger.debug(CommonUtils.rpad("rule="+updateRule, 20, " ") + "definition="+updateRuleFromServer + "::" + currentType + "::" + currentPath );
					}

					// Determine if the CIS server attribute definition "updateRule" is contained within the user's passed in "updateRule"
					boolean getAttribute = false;
					// If the passed in updateRule is null, empty, blank or contains an asterisk then assume then get all attribute in the Server Attribute List for writing to the XML file
					if (updateRule == null) {
						getAttribute = true;
					} else {
						if (updateRule.trim().contains("*") || updateRule.length() == 0) {
							getAttribute = true;
						}
						// If a Server Attribute Definition updateRule was found and it matches what was passed in then get this attributeDef for writing to the XML file
						if (updateRuleFromServer != null && updateRule.contains(updateRuleFromServer)) {
							getAttribute = true;
						}
					}

					if(currentType.equalsIgnoreCase("FOLDER")) {

						addServerAttributesFromPath(cisclient, updateRule, serverAttribute, currentPath, targetServer);

					} else {
						// Get the Server Attribute and compose a ServerAttribute type node to write to the XML file.
						if (getAttribute) {
							// Set the web service endpoint and method
							endpointName = "server";
							endpointMethod = "getServerAttributes";
	
							// Setup the request
							requestXML =
									"<?xml version=\"1.0\"?>"+
									"<p1:ServerAttributeModule xmlns:p1=\"http://www.dvbu.cisco.com/ps/deploytool/modules\">"+
									"    <serverAttribute>"+
									"        <id>id1</id>"+
									"        <name>"+currentPath+"</name>"+
									"        <type>STRING</type>"+
									"    </serverAttribute>"+
									"</p1:ServerAttributeModule>";
	
							if(logger.isDebugEnabled()) {
								logger.debug(prefix+"."+methodName+"().  Invoking web service endpoint name="+endpointName+" and method="+endpointMethod+"  Request:\n" + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(requestXML))+"\n");
							}
	
							/*****************************************************
							 * Invoke Method=getServerAttributes
							 *****************************************************/
							// Invoke the web service method
							response = cisclient.sendRequest(endpointName, endpointMethod, requestXML);
	
							if(logger.isDebugEnabled()) {
								// Format for XML pretty print
								logger.debug(prefix+"."+methodName+"().  Response: " + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(response)));
							}
	
							// Convert XML String to ServerAttributeModule Object for easier processing
							Object obj2 = XMLUtils.getJavaObjectFromXML(response);
							ServerAttributeModule sam2 = (ServerAttributeModule) obj2;
							
							// Add the server attribute to the existing "serverAttribute" object list and continue iterating through attribute definition paths
							if (sam2.getServerAttribute() != null && sam2.getServerAttribute().size() > 0)
								serverAttribute.getServerAttribute().addAll(sam2.getServerAttribute());
						}
					}
				}
			}
		} catch (Exception e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), methodName, "ServerAttribute", startpath, targetServer));
			throw new ApplicationException(e.getMessage(), e);
		}		
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO#getServerAttributeDefinition(java.lang.String, java.lang.String, java.lang.String)
	 * Get server attribute definitions for the given paths.
	 */
//	@Override
	public ServerAttributeModule getServerAttributeDefinition(String serverId, String serverAttrPath, String pathToServersXML) {

		String methodName = "getServerAttributeDefinition";
		// Set the web service endpoint and method
		String endpointName = "server";
		String endpointMethod = "getServerAttributeDefs";
		
		if(logger.isDebugEnabled()) {		
			logger.debug(prefix+"."+methodName+"(serverId, serverAttrPath, pathToServersXML).  serverId="+serverId+"  serverAttrDefPath="+serverAttrPath+"  pathToServersXML="+pathToServersXML);
		}
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, prefix+"."+methodName, logger);

		// Ping the Server to make sure it is alive and the values are correct.
		//WsApiHelperObjects.pingServer(targetServer, true);

		// Get server properties from the target server
		Properties props = WsApiHelperObjects.getServerProperties(targetServer);

		// Get the adapter config path environment variable
		String adapterConfigPath = System.getProperty("PDTOOL_ADAPTER_CONFIG_PATH");
		if (adapterConfigPath == null) 
			throw new CompositeException(prefix+"::The enviornment variable PDTOOL_ADAPTER_CONFIG_PATH was not set properly during DeployManagerUtil.initAdapter.");

		try {	
			/*****************************************************
			 * Web Service Client Connection
			 *****************************************************/
			if(logger.isDebugEnabled()) {
				logger.debug(prefix+"."+methodName+"().  Creating CIS client web service connection.");
			}
			// Execute the CIS Web Service Client for an adapter configuration connection
			CisWsClient cisclient = new CisWsClient(props, adapterConfigPath);

			// Setup the request
			String requestXML =
					"<?xml version=\"1.0\"?>"+
					"<p1:ServerAttributeModule xmlns:p1=\"http://www.dvbu.cisco.com/ps/deploytool/modules\">"+
					"    <serverAttributeDef>"+
					"        <id>id1</id>"+
					"        <name>"+serverAttrPath+"</name>"+
					"        <type>STRING</type>"+
					"    </serverAttributeDef>"+
					"</p1:ServerAttributeModule>";

			if(logger.isDebugEnabled()) {
				logger.debug(prefix+"."+methodName+"().  Invoking web service endpoint name="+endpointName+" and method="+endpointMethod+"  Request:\n" + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(requestXML))+"\n");
			}
			/*****************************************************
			 * Invoke Method=getServerAttributeDefs
			 *****************************************************/
			// Invoke the web service method
			String response = cisclient.sendRequest(endpointName, endpointMethod, requestXML);
	
			if(logger.isDebugEnabled()) {
				// Format for XML pretty print
				logger.debug(prefix+"."+methodName+"().  Response: " + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(response)));
			}
			
			// Convert XML String to ServerAttributeModule Object for easier processing
			Object obj = XMLUtils.getJavaObjectFromXML(response);
			ServerAttributeModule serverAttributeDefs = (ServerAttributeModule) obj;
			
			if (serverAttributeDefs.getServerAttributeDef() != null && serverAttributeDefs.getServerAttributeDef().size() > 0) {
				// Generate and ID using the first token in the server attribute definition path name
				String id = CommonUtils.getToken(1, serverAttributeDefs.getServerAttributeDef().get(0).getName()) + "1";
				// Set the id
				serverAttributeDefs.getServerAttributeDef().get(0).setId(id);
			}

			return serverAttributeDefs;
			
		} catch (Exception e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), methodName, "ServerAttributeDef", serverAttrPath, targetServer));
			throw new ApplicationException(e.getMessage(), e);
		}			
	}

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO#getServerAttributeDefsFromPath(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 * Get the child server attribute definitions of the given path.  This will be an empty list for attribute definitions that are not of the "FOLDER" type.
	 */
//	@Override
	public ServerAttributeModule getServerAttributeDefsFromPath(String serverId, String startpath, String pathToServersXML, String updateRule) {

		String methodName = "getServerAttributeDefsFromPath";
		
		if(logger.isDebugEnabled()) {		
			logger.debug(prefix+"."+methodName+"(serverId, startpath, pathToServersXML, updateRule).  serverId="+serverId+"  startpath="+startpath+"  pathToServersXML="+pathToServersXML+"  updateRule="+updateRule);
		}
		ServerAttributeModule serverAttribute = new ServerAttributeModule();
		
		// read target server properties from server xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, prefix+"."+methodName, logger);

		// Ping the Server to make sure it is alive and the values are correct.
		//WsApiHelperObjects.pingServer(targetServer, true);

		// Get server properties from the target server
		Properties props = WsApiHelperObjects.getServerProperties(targetServer);

		// Get the adapter config path environment variable
		String adapterConfigPath = System.getProperty("PDTOOL_ADAPTER_CONFIG_PATH");
		if (adapterConfigPath == null) 
			throw new CompositeException(prefix+"::The enviornment variable PDTOOL_ADAPTER_CONFIG_PATH was not set properly during DeployManagerUtil.initAdapter.");
		
		try {	
			/*****************************************************
			 * Web Service Client Connection
			 *****************************************************/
			if(logger.isDebugEnabled()) {
				logger.debug(prefix+"."+methodName+"().  Creating CIS client web service connection.");
			}
			// Execute the CIS Web Service Client for an adapter configuration connection
			CisWsClient cisclient = new CisWsClient(props, adapterConfigPath);

			addServerAttributeDefsFromPath(cisclient, updateRule, serverAttribute, startpath, targetServer);
			
			return serverAttribute;
		} 
		catch (Exception e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), methodName, "ServerAttributeDef", startpath, targetServer));
			throw new ApplicationException(e.getMessage(), e);
		}
	}
	
	private void addServerAttributeDefsFromPath(CisWsClient cisclient, String updateRule, ServerAttributeModule serverAttribute, String startpath, CompositeServer targetServer){

		String methodName = "addServerAttributeDefsFromPath";
		// Set the web service endpoint and method
		String endpointName = "server";
		String endpointMethod = "getServerAttributeDefChildren";
				
		try {
			// Setup the request
			String requestXML =
					"<?xml version=\"1.0\"?>"+
					"<p1:ServerAttributeModule xmlns:p1=\"http://www.dvbu.cisco.com/ps/deploytool/modules\">"+
					"    <serverAttributeDef>"+
					"        <id>id1</id>"+
					"        <name>"+startpath+"</name>"+
					"        <type>STRING</type>"+
					"    </serverAttributeDef>"+
					"</p1:ServerAttributeModule>";

			if(logger.isDebugEnabled()) {
				logger.debug(prefix+"."+methodName+"().  Invoking web service endpoint name="+endpointName+" and method="+endpointMethod+"  Request:\n" + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(requestXML))+"\n");
			}

			/*****************************************************
			 * Invoke Method=getServerAttributeDefChildren
			 *****************************************************/
			// Invoke the web service method
			String response = cisclient.sendRequest(endpointName, endpointMethod, requestXML);

			if(logger.isDebugEnabled()) {
				// Format for XML pretty print
				logger.debug(prefix+"."+methodName+"().  Response: " + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(response)));
			}
			
			// Convert XML String to ServerAttributeModule Object for easier processing
			Object obj1 = XMLUtils.getJavaObjectFromXML(response);
			ServerAttributeModule sam1 = (ServerAttributeModule) obj1;
			
			// Iterate over the ServerAttributeModule Response document
			if (sam1 != null && sam1.getServerAttributeDef() != null && sam1.getServerAttributeDef().size() > 0) {
				List<ServerAttributeDefType> saDefList = sam1.getServerAttributeDef();
				for (ServerAttributeDefType saDef : saDefList) {

					// Get current attribute definition path and type
					String currentPath = saDef.getName();
					String currentType = saDef.getType().toString();

					// Determine the server "updateRule"
					String updateRuleFromServer = null;
					if (saDef.getUpdateRule() == null) {
						logger.debug("Update Rule is null for serverAttributeDef.getUpdateRule()");
					} else {
						updateRuleFromServer = saDef.getUpdateRule().toString();
						logger.debug(CommonUtils.rpad("rule="+updateRule, 20, " ") + "definition="+updateRuleFromServer + "::" + currentType + "::" + currentPath );
					}

					// Determine if the CIS server attribute definition "updateRule" is contained within the user's passed in "updateRule"
					boolean getAttribute = false;
					// If the passed in updateRule is null, empty, blank or contains an asterisk then assume then get all attribute in the Server Attribute List for writing to the XML file
					if (updateRule == null) {
						getAttribute = true;
					} else {
						if (updateRule.trim().contains("*") || updateRule.length() == 0) {
							getAttribute = true;
						}
						// If a Server Attribute Definition updateRule was found and it matches what was passed in then get this attributeDef for writing to the XML file
						if (updateRuleFromServer != null && updateRule.contains(updateRuleFromServer)) {
							getAttribute = true;
						}
					}

					if(currentType.equalsIgnoreCase("FOLDER")) {

						addServerAttributeDefsFromPath(cisclient, updateRule, serverAttribute, currentPath, targetServer);

					} else {
						// Get the Server Attribute and compose a ServerAttribute type node to write to the XML file.
						if (getAttribute) {
							
							// Set the web service endpoint and method
							endpointName = "server";
							endpointMethod = "getServerAttributeDefs";
	
							// Setup the request
							requestXML =
									"<?xml version=\"1.0\"?>"+
									"<p1:ServerAttributeModule xmlns:p1=\"http://www.dvbu.cisco.com/ps/deploytool/modules\">"+
									"    <serverAttributeDef>"+
									"        <id>id1</id>"+
									"        <name>"+currentPath+"</name>"+
									"        <type>STRING</type>"+
									"    </serverAttributeDef>"+
									"</p1:ServerAttributeModule>";
	
							if(logger.isDebugEnabled()) {
								logger.debug(prefix+"."+methodName+"().  Invoking web service endpoint name="+endpointName+" and method="+endpointMethod+"  Request:\n" + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(requestXML))+"\n");
							}
	
							/*****************************************************
							 * Invoke Method=getServerAttributeDefs
							 *****************************************************/
							// Invoke the web service method
							response = cisclient.sendRequest(endpointName, endpointMethod, requestXML);
	
							if(logger.isDebugEnabled()) {
								// Format for XML pretty print
								logger.debug(prefix+"."+methodName+"().  Response: " + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(response)));
							}
	
							// Convert XML String to ServerAttributeModule Object for easier processing
							Object obj2 = XMLUtils.getJavaObjectFromXML(response);
							ServerAttributeModule sam2 = (ServerAttributeModule) obj2;
							
							// Add the server attribute to the existing "serverAttribute" object list and continue iterating through attribute definition paths
							if (sam2.getServerAttributeDef() != null && sam2.getServerAttributeDef().size() > 0)
								serverAttribute.getServerAttributeDef().addAll(sam2.getServerAttributeDef());
						}
					}
				}
			}
		} catch (Exception e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), methodName, "ServerAttributeDef", startpath, targetServer));
			throw new ApplicationException(e.getMessage(), e);
		}			
	}
}
