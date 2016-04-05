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

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.adapters.common.AdapterException;
import com.cisco.dvbu.ps.common.adapters.core.CisWsClient;
import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.CompositeLogger;
import com.cisco.dvbu.ps.common.util.XMLUtils;
import com.cisco.dvbu.ps.common.util.wsapi.CisApiFactory;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.dao.PrivilegeDAO;
import com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO;
import com.cisco.dvbu.ps.deploytool.modules.PrivilegeModule;
import com.cisco.dvbu.ps.deploytool.modules.ServerAttributeModule;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.compositesw.services.system.admin.GetResourcePrivilegesSoapFault;
import com.compositesw.services.system.admin.ResourcePortType;
import com.compositesw.services.system.admin.UpdateResourcePrivilegesSoapFault;
import com.compositesw.services.system.admin.resource.GetResourcePrivilegesRequest.Entries;
import com.compositesw.services.system.admin.resource.PathTypeOrColumnPair;
import com.compositesw.services.system.admin.resource.Privilege;
import com.compositesw.services.system.admin.resource.PrivilegeEntry;
import com.compositesw.services.system.admin.resource.PrivilegeEntry.Privileges;
import com.compositesw.services.system.admin.resource.UpdateResourcePrivilegesRequest.PrivilegeEntries;
import com.compositesw.services.system.admin.resource.UpdatePrivilegesMode;
import com.compositesw.services.system.util.common.AttributeTypeValueMap.Entry;

/**
 * Privilege module implements the ability to get and set privileges using the HTTP adapter style
 * of connection to CIS. 
 * 
 * @author mtinius
 * 
 * Modification History
 * =========================
 * 01/30/2016	mtinius	Code affected: getResourcePrivileges
 * 						Modified the XSLT in cis_adapter_config.xml to return a null PrivilegeModule node
 * 						when there are no privilege entries in the response.  It was throwing a schema validation
 * 						exception otherwise.
 */

public class PrivilegeWSDAOImpl implements PrivilegeDAO {

	private static String prefix = "PrivilegeWSDAOImpl";
	private static Log logger = LogFactory.getLog(PrivilegeWSDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.dao.PrivilegeDAO#takePrivilegeAction(java.lang.String,java.lang.String, boolean, boolean, boolean, com.compositesw.services.system.util.common.AttributeList, java.lang.String, java.lang.String, int)
	 *  Set the privilege information for a list of resources.
		Only a user with GRANT privilege on a resource can modify the privileges for that
		resource.  The owner of a resource always has GRANT privilege, as do users with the
		MODIFY_ALL_RESOURCES right.
		
		When "mode" is 'OVERWRITE_APPEND", or is not supplied, privileges are applied on a
		per-user or per-group basis, so that updating privileges for one user or group does not
		alter privileges from any other user or group.  The privileges applied for a user or
		group replace the previous value for that user or group. When "mode" is "SET_EXACTLY",
		all privileges on the resource are made to look exactly like the provided privileges.
		
		When "updateRecursively" is "false", the privileges are applied only the specified
		resources.  When it is "true", the privileges are recursively applied into any CONTAINER
		or DATA_SOURCE resource specified.  When recursively applying privileges, the privilege
		change is ignored for any resource the user lacks owner privileges for.
		
		Privileges that are not applicable for a given resource type are automatically stripped
		down to the set that is legal for each resource.  TABLE resources support NONE, READ,
		WRITE, SELECT, INSERT, UPDATE, and DELETE.  PROCEDURE resources support NONE, READ,
		WRITE, and EXECUTE.  All other resource types only support NONE, READ, and WRITE.
		
		The "combinedPrivs" and "inheritedPrivs" elements on each "privilegeEntry" will be
		ignored and can be left unset.
		
		Request Elements:
		    updateRecursively: If "true", then all children of the given resources will
		       recursively be updated with the privileges assigned to their parent.
		    privilegeEntries: A list of resource names, types, and the privileges.
		    mode (optional): determines whether privileges are merged with existing ones,
		       default is "OVERWRITE_APPEND", which merges and does not update privileges for
		       users or groups not mentioned.  "SET_EXACTLY" makes privileges look exactly like
		       those provided in the call.
		
		Response Elements:
		    N/A
		
		Faults:
		    IllegalArgument: If any path is malformed or any type or privilege entry is illegal,
		       or mode is not one of the legal values.
		    NotAllowed: If an attempt is made to use this operation with an insufficient license.
		    NotFound: If a path refers to a resource that does not exist.
		    NotFound: If an unknown domain is provided.
		    NotFound: If an unknown user is provided.
		    NotFound: If an unknown group is provided.
		    Security: If for a given entry path the user does not have READ access on any item
		       in a path other than the last item, or does not have GRANT access on the last item.
		    Security: If the user does not have the ACCESS_TOOLS right.
	 */
	public void takePrivilegeAction(String actionName, PrivilegeModule privilegeModule, String serverId, String pathToServersXML) throws CompositeException {
		
		String methodName = "takePrivilegeAction";
		// Set the web service endpoint and method
		String endpointName = "resource";
		String endpointMethodUpdatePrivs = "updateResourcePrivileges";
		String endpointMethodChangeOwner = "changeResourceOwner";


		int privSize = 0;
		String resourcePath = null;
		if (privilegeModule != null && privilegeModule.getResourcePrivilege() != null && privilegeModule.getResourcePrivilege().get(0).getPrivilege() != null) {
			privSize = privilegeModule.getResourcePrivilege().get(0).getPrivilege().size();
			resourcePath = privilegeModule.getResourcePrivilege().get(0).getResourcePath();
		}
		if(logger.isDebugEnabled()) {
			logger.debug(prefix+"."+methodName+"(actionName , recurse, privilegeModule, serverId, pathToServersXML).  actionName="+actionName+"  #privilegeModule="+privSize+"  serverId="+serverId+"  pathToServersXML="+pathToServersXML);
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
	
			if(actionName.equalsIgnoreCase(PrivilegeDAO.action.UPDATE.name())){
				
				/*****************************************************
				 * Web Service Client Connection
				 *****************************************************/
				if(logger.isDebugEnabled()) {
					logger.debug(prefix+"."+methodName+"().  Creating CIS client web service connection.");
				}
				// Execute the CIS Web Service Client for an adapter configuration connection
				CisWsClient cisclient = new CisWsClient(props, adapterConfigPath);
	
				// Setup the request
				String requestXML = XMLUtils.getStringFromDocument(privilegeModule);
	
				/*****************************************************
				 * Invoke Method=updateResourcePrivileges
				 *****************************************************/
				if(logger.isDebugEnabled()) {
					logger.debug(prefix+"."+methodName+"().  Invoking web service endpoint name="+endpointName+" and method="+endpointMethodUpdatePrivs+"  Request:\n" + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(requestXML))+"\n");
				}
				// Invoke the web service method
				String response1 = cisclient.sendRequest(endpointName, endpointMethodUpdatePrivs, requestXML);
		
				if(logger.isDebugEnabled()) {
					// Format for XML pretty print
					logger.debug(prefix+"."+methodName+"().  Response: " + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(response1)));
				}
				
				/*****************************************************
				 * Invoke Method=changeResourceOwner
				 *****************************************************/
				if (privilegeModule.getResourcePrivilege().get(0).getResourceOwner() != null && 
						privilegeModule.getResourcePrivilege().get(0).getResourceOwner().getResourceOwnerName() != null && 
						privilegeModule.getResourcePrivilege().get(0).getResourceOwner().getResourceOwnerDomain() != null) {
					if(logger.isDebugEnabled()) {
						logger.debug(prefix+"."+methodName+"().  Invoking web service endpoint name="+endpointName+" and method="+endpointMethodChangeOwner+"  Request:\n" + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(requestXML))+"\n");
					}
					// Invoke the web service method
					String response2 = cisclient.sendRequest(endpointName, endpointMethodChangeOwner, requestXML);
			
					if(logger.isDebugEnabled()) {
						// Format for XML pretty print
						logger.debug(prefix+"."+methodName+"().  Response: " + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(response2)));
					}
				}
			}		
		} catch (AdapterException e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), PrivilegeDAO.action.UPDATE.name(), "Privilege", resourcePath, targetServer));
			throw new ApplicationException(e.getMessage(), e);
		} catch (Exception e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), PrivilegeDAO.action.UPDATE.name(), "Privilege", resourcePath, targetServer));
			throw new ApplicationException(e.getMessage(), e);
		}
	}

	/*
	 * Get the privilege information for any number of resources.

		The returned privileges per user or group are the privileges specifically given to that
		user or group.  In each "privilegeEntry", the "combinedPrivs" element contains the
		effective privileges for that user or group based on their membership in all other groups.
		In each "privilegeEntry", the "inheritedPrivs" element only contains the privileges that
		were inherited due to group membership.  Logically OR'ing the "privs" and
		"inheritedPrivs" is the same as the "combinedPrivs".
		
		A user with GRANT privilege or with READ_ALL_RESOURCES right will receive all privilege
		information for all users for a that resource.  Other users will only receive their own
		privilege information.
		
		Request Elements:
		    entries: A list of path and type pairs to get privilege information for.
		    filter (optional): A filter string.  The only legal values in this release are an
		    empty string and "ALL_EXPLICIT".
		
		Response Elements:
		    privilegeEntries: A list with the privilege information for each of the requested
		    resources.
		
		Faults:
		    IllegalArgument: If any path is malformed or type is illegal.
		    NotFound: If any one of the provided resources does not exist.
		    Security: If the user does not have READ access on all items in each path other
		       than the last one.
		    Security: If the user does not have the ACCESS_TOOLS right.
	 */
	public PrivilegeModule getResourcePrivileges(Entries privilegeEntries, String filter, boolean includeColumnPrivileges, String serverId, String pathToServersXML){

		String methodName = "getResourcePrivileges";
		// Set the web service endpoint and method
		String endpointName = "resource";
		String endpointMethodGetPrivs = "getResourcePrivileges";
		String endpointMethodGetResource = "getResource";

		// For debugging
		int privSize = 0;
		if(logger.isDebugEnabled()) {
			if (privilegeEntries != null & privilegeEntries.getEntry() != null) 
				privSize = privilegeEntries.getEntry().size();
			logger.debug(prefix+"."+methodName+"(privilegeEntries , filter, includeColumnPrivileges, serverId, pathToServersXML).  #privilegeEntries="+privSize+"  filter="+filter+"  includeColumnPrivileges="+includeColumnPrivileges+"  serverId="+serverId+"  pathToServersXML="+pathToServersXML);
		}

		// read target server properties from xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);

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
							"<getResourcePrivileges xmlns:resource=\"http://www.compositesw.com/services/system/admin/resource\">"+
						"    <entries>";
			if (privilegeEntries != null && privilegeEntries.getEntry() != null) {
				for (PathTypeOrColumnPair pathType:privilegeEntries.getEntry()) {
					requestXML = requestXML + 
						"      <entry>"+
						"        <path>"+pathType.getPath()+"</path>"+
						"        <type>"+pathType.getType().toString()+"</type>"+
						"      </entry>";
				}
				requestXML = requestXML + 
						"    </entries>";
				if (filter != null && filter.trim().length() > 0) {
					requestXML = requestXML + 
						"    <filter>"+filter+"</filter>";
				}
				requestXML = requestXML + 
						"    <includeColumnPrivileges>"+includeColumnPrivileges+"</includeColumnPrivileges>";
			}	
			requestXML = requestXML + 
						"</getResourcePrivileges>";

			/*****************************************************
			 * Invoke Method=getResourcePrivileges
			 *****************************************************/
			if(logger.isDebugEnabled()) {
				logger.debug(prefix+"."+methodName+"().  Invoking web service endpoint name="+endpointName+" and method="+endpointMethodGetPrivs+"  Request:\n" + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(requestXML))+"\n");
			}
			// Invoke the web service method
			/* String requestXml = "<?xml version=\"1.0\"?> 
			 * <p1:PrivilegeModule xmlns:p1="http://www.dvbu.cisco.com/ps/deploytool/modules" 
			 * xmlns:resource="http://www.compositesw.com/services/system/admin/resource">
			  <resourcePrivilege>
			    <id>priv1</id>
			    <resourcePath>/services/webservices/TEST00/CAT1/SCH1/CustomerWS/customers</resourcePath>
			    <resourceType>TABLE</resourceType>
			    <privilege>
			    ....
			    </privilege>
			  </resourcePrivilege>
			</p1:PrivilegeModule>"
			*/
			String response1 = cisclient.sendRequest(endpointName, endpointMethodGetPrivs, requestXML);
	
			if(logger.isDebugEnabled()) {
				// Format for XML pretty print
				logger.debug(prefix+"."+methodName+"().  Response: " + XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(response1)));
			}
			
			// Convert XML String to PrivilegeModule Object for easier processing
			Object obj = XMLUtils.getJavaObjectFromXML(response1);
			PrivilegeModule retPrivilegeEntries = (PrivilegeModule) obj;
				
			return retPrivilegeEntries;
			
		} catch (Exception e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), methodName, "Privilege", "privilegeEntries", targetServer));
			throw new ApplicationException(e.getMessage(), e);
		}	
	}
}
