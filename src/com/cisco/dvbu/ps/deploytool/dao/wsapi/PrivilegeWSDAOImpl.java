/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao.wsapi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CompositeLogger;
import com.cisco.dvbu.ps.common.util.wsapi.CisApiFactory;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.dao.PrivilegeDAO;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.compositesw.services.system.admin.GetResourcePrivilegesSoapFault;
import com.compositesw.services.system.admin.ResourcePortType;
import com.compositesw.services.system.admin.UpdateResourcePrivilegesSoapFault;
import com.compositesw.services.system.admin.resource.GetResourcePrivilegesRequest.Entries;
import com.compositesw.services.system.admin.resource.Privilege;
import com.compositesw.services.system.admin.resource.PrivilegeEntry;
import com.compositesw.services.system.admin.resource.UpdateResourcePrivilegesRequest.PrivilegeEntries;
import com.compositesw.services.system.admin.resource.UpdatePrivilegesMode;

public class PrivilegeWSDAOImpl implements PrivilegeDAO {

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
	public void takePrivilegeAction(String actionName, boolean recurse, boolean updateDependenciesRecursively, boolean updateDependentsRecursively, String mode, PrivilegeEntries privilegeEntries, String serverId, String pathToServersXML, Integer CisVersion) throws CompositeException {
		
		// read target server properties from xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "PrivilegeWSDAOImpl.takePrivilegeAction("+actionName+")", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);
		
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);
/*
		ResourcePortType port = null;
		if (CisVersion>= 6200000 && CisVersion < 6230000)
			port = CisApiFactory.getResourcePort(targetServer);

			
		com.compositesw._623.services.system.admin.ResourcePortType port623 = null;
		if (CisVersion >= 6230000)
			port623 = CisApiFactory.getResourcePort623(targetServer);
*/		
		try {
	
			if(actionName.equalsIgnoreCase(PrivilegeDAO.action.UPDATE.name())){
				
				/*
		    	 * mtinius: 2012-08-07, added parameter (PrivilegeDAO.RECURSE_DEPENDENCIES) for new api
		    	 * 						call to updateResourcePrivileges
		    	 * 
		    	 * Invocation for CIS 6.2 and higher
		    	 */
/*
				if (CisVersion >= 6230000)
				{
					// Create a privilege entries for 6.2.0
					com.compositesw._623.services.system.admin.resource.UpdateResourcePrivilegesRequest.PrivilegeEntries privEntries623 = new com.compositesw._623.services.system.admin.resource.UpdateResourcePrivilegesRequest.PrivilegeEntries();
					
					for (PrivilegeEntry privEntry : privilegeEntries.getPrivilegeEntry()) 
					{
						// Create a new privilege entry
						com.compositesw._623.services.system.admin.resource.PrivilegeEntry privEntry623 = new com.compositesw._623.services.system.admin.resource.PrivilegeEntry();
						
						if (privEntry.getPath() != null)
							privEntry623.setPath(privEntry.getPath());
						if (privEntry.getType() != null)
							privEntry623.setType(com.compositesw._623.services.system.admin.resource.ResourceOrColumnType.valueOf(privEntry.getType().toString()));
						
						if (privEntry.getPrivileges() != null) {
							// Create a new privileges object for 6.2.0
							com.compositesw._623.services.system.admin.resource.PrivilegeEntry.Privileges privileges623 = new com.compositesw._623.services.system.admin.resource.PrivilegeEntry.Privileges();
							
							for (Privilege privilege : privEntry.getPrivileges().getPrivilege()) {
								com.compositesw._623.services.system.admin.resource.Privilege privilege623 = new com.compositesw._623.services.system.admin.resource.Privilege();
								
								if (privilege.getName() != null)
									privilege623.setName(privilege.getName());
								if (privilege.getNameType() != null)
									privilege623.setNameType(com.compositesw._623.services.system.admin.resource.UserNameType.valueOf(privilege.getNameType().toString()));
								if (privilege.getDomain() != null)
									privilege623.setDomain(privilege.getDomain());
								if (privilege.getPrivs() != null)
									privilege623.setPrivs(privilege.getPrivs());
								if (privilege.getCombinedPrivs() != null)
									privilege623.setCombinedPrivs(privilege.getCombinedPrivs());
								if (privilege.getInheritedPrivs() != null)
									privilege623.setInheritedPrivs(privilege.getInheritedPrivs());
								
								// Add the privilege entry
								privileges623.getPrivilege().add(privilege623);
							}
							// Add the privileges for 6.2.0
							privEntry623.setPrivileges(privileges623);
						}
						// Add the privilege entry for 6.2.0
						privEntries623.getPrivilegeEntry().add(privEntry623);
					}
							
//					privEntries623 = (com.compositesw._623.services.system.admin.resource.UpdateResourcePrivilegesRequest.PrivilegeEntries) privilegeEntries; 
				
					// 6.2.3 and higher
					port623.updateResourcePrivileges(recurse, 
							updateDependenciesRecursively, 
							updateDependentsRecursively, 
							privEntries623, 
							com.compositesw._623.services.system.admin.resource.UpdatePrivilegesMode.valueOf(mode));
				}
				
				if (CisVersion>= 6200000 && CisVersion < 6230000) {
					port.updateResourcePrivileges(recurse, updateDependenciesRecursively, privilegeEntries, UpdatePrivilegesMode.valueOf(mode));
				}
*/
				port.updateResourcePrivileges(recurse, updateDependenciesRecursively, privilegeEntries, UpdatePrivilegesMode.valueOf(mode));
}
		} catch (UpdateResourcePrivilegesSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), PrivilegeDAO.action.UPDATE.name(), "Privilege", "recurse:"+recurse+" mode:"+mode, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
//		} catch (com.compositesw._623.services.system.admin.UpdateResourcePrivilegesSoapFault e) {
//			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), PrivilegeDAO.action.UPDATE.name(), "Privilege", "recurse:"+recurse+" mode:"+mode, targetServer)/*,e.getFaultInfo()*/);
//			throw new ApplicationException(e.getMessage(), e);
		} catch (Exception e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), PrivilegeDAO.action.UPDATE.name(), "Privilege", "recurse:"+recurse+" mode:"+mode, targetServer));
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
	public com.compositesw.services.system.admin.resource.GetResourcePrivilegesResponse.PrivilegeEntries
			getResourcePrivileges(Entries privilegeEntries, String filter, boolean includeColumnPrivileges, String serverId, String pathToServersXML){

		com.compositesw.services.system.admin.resource.GetResourcePrivilegesResponse.PrivilegeEntries privlegeEntries = null;

		// read target server properties from xml and build target server object based on target server name 
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		// Construct the resource port based on target server name
		ResourcePortType port = CisApiFactory.getResourcePort(targetServer);
		
		try {
			privlegeEntries = port.getResourcePrivileges(privilegeEntries, filter, includeColumnPrivileges);

		} catch (GetResourcePrivilegesSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getResourcePrivileges", "Privilege", "privilegeEntries", targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}	
		return privlegeEntries;
	}

}
