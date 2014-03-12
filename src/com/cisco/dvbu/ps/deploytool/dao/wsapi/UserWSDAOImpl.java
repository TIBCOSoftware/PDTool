/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao.wsapi;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CompositeLogger;
import com.cisco.dvbu.ps.common.util.wsapi.CisApiFactory;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.dao.UserDAO;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.compositesw.services.system.admin.CreateUserSoapFault;
import com.compositesw.services.system.admin.DestroyUserSoapFault;
import com.compositesw.services.system.admin.GetDomainUsersSoapFault;
import com.compositesw.services.system.admin.GetDomainsSoapFault;
import com.compositesw.services.system.admin.GetUsersSoapFault;
import com.compositesw.services.system.admin.UpdateUserSoapFault;
import com.compositesw.services.system.admin.UserPortType;
import com.compositesw.services.system.admin.user.Domain;
import com.compositesw.services.system.admin.user.DomainList;
import com.compositesw.services.system.admin.user.DomainMemberReferenceList;
import com.compositesw.services.system.admin.user.ScopeValue;
import com.compositesw.services.system.admin.user.User;
import com.compositesw.services.system.admin.user.UserList;
import com.compositesw.services.system.util.common.DetailLevel;
import com.compositesw.services.system.util.common.NameList;

public class UserWSDAOImpl implements UserDAO {
	
	private static Log logger = LogFactory.getLog(UserWSDAOImpl.class);

	public void takeUserAction(String actionName, String userName, String oldPassword, String password, String domainName, DomainMemberReferenceList groupNames, String explicitRights, String annotation, String serverId, String pathToServersXML) throws CompositeException {
		
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "UserWSDAOImpl.takeUserAction("+actionName+")", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		UserPortType port = CisApiFactory.getUserPort(targetServer);

			try {
				if(actionName.equalsIgnoreCase(UserDAO.action.CREATE.name())){

					port.createUser(domainName, userName, password, explicitRights, annotation);
					
				}else if(actionName.equalsIgnoreCase(UserDAO.action.UPDATE.name())){

					port.updateUser(domainName, userName, oldPassword, password, groupNames, explicitRights, annotation);

				}else if(actionName.equalsIgnoreCase(UserDAO.action.DELETE.name())){

					port.destroyUser(domainName, userName);
                } else {
                	throw new ApplicationException("Error: actionName is null", null);
                }
			} catch (CreateUserSoapFault e) {
				CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), actionName, "User", userName, targetServer),e.getFaultInfo());
				throw new ApplicationException(e.getMessage(), e);
			} catch (UpdateUserSoapFault e) {
				CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), actionName, "User", userName, targetServer),e.getFaultInfo());
				throw new ApplicationException(e.getMessage(), e);
			} catch (DestroyUserSoapFault e) {
				CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), actionName, "User", userName, targetServer),e.getFaultInfo());
				throw new ApplicationException(e.getMessage(), e);
			}
	}

	@Override
	public UserList getUsers(String userName, String domainName, String serverId, String pathToServersXML) throws CompositeException {
		UserList userList = null;
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "UserWSDAOImpl.getUsers", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		UserPortType port = CisApiFactory.getUserPort(targetServer);
		try {
			if(domainName != null && domainName.trim().length() > 0 && !domainName.trim().equals("\"\"") && userName != null && userName.trim().length() > 0){
				NameList names = new NameList();
				names.getName().add(userName);
				userList = port.getUsers(domainName, names);
			}
		} catch (GetUsersSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getUsers", "User", userName, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}
		 return userList;
	}

	@Override
	public UserList getAllUsers(ArrayList<String> validUsers, String domainName, String serverId, String pathToServersXML) throws CompositeException {
		UserList userList = null;
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "UserWSDAOImpl.getAllUsers", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		UserPortType port = CisApiFactory.getUserPort(targetServer);
		try {
			userList = new UserList();
			if(domainName != null && domainName.trim().length() > 0 && !domainName.trim().equals("\"\"")){
				// Since the domainName is not null or empty then only get users for this domain
				UserList userTempList1 = port.getDomainUsers(domainName, ScopeValue.LOCAL_ONLY);
				if (userTempList1 != null && userTempList1.getUser() != null) {
					// If the validUsers list is null then get all the users
					if (validUsers == null) {
						userList.getUser().addAll(userTempList1.getUser());
					} else {
						// Only get the users from CIS that are in the validUsers list
						for (User user : userTempList1.getUser()) {
							if (validUsers.contains(user.getName())) {
								userList.getUser().add(user);
							}
						}						
					}
				}
			} else {
				// Since domainName is NULL, then get the list of domains in the CIS instance
				DomainList domainsList = port.getDomains(DetailLevel.SIMPLE);
				if(domainsList != null && domainsList.getDomain() != null){
					List<Domain> domainList = domainsList.getDomain();
					for (Domain domain : domainList) {
						if(domain != null && domain.getName() != null)
						{
							// Get all the users for the domain
							UserList userTempList2 = port.getDomainUsers(domain.getName(), ScopeValue.LOCAL_ONLY);
							if (userTempList2 != null && userTempList2.getUser() != null) {
								// If the validUsers list is null then get all the users
								if (validUsers == null) {
									userList.getUser().addAll(userTempList2.getUser());
								} else {
									// Only get the users from CIS that are in the validUsers list
									for (User user : userTempList2.getUser()) {
										if (validUsers.contains(user.getName())) {
											userList.getUser().add(user);
										}
									}						
								}
							}
						}
					}
				}
			}
/*	used to verify Bug:SUP-12301		
			for (User user : userList.getUser()) {
				if (user.getGroupNames() != null) {
					if (user.getGroupNames().getEntry() != null) {
						// Create the list of Groups
						for (DomainMemberReference entry : user.getGroupNames().getEntry()) {
							GroupMembershipType g = new GroupMembershipType();
							String group = entry.getDomain(); // Always NULL
							String name = entry.getName(); // Always NULL
							String x = null;
						}					
					}
				}
			}
*/			
		} catch (GetDomainsSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getAllUsers", "User", domainName, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		} catch (GetDomainUsersSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getAllUsers", "User", domainName, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}
		 return userList;
	}

}
