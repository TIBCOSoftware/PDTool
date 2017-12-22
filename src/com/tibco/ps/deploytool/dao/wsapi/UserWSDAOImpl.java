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
package com.tibco.ps.deploytool.dao.wsapi;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tibco.ps.common.exception.ApplicationException;
import com.tibco.ps.common.exception.CompositeException;
import com.tibco.ps.common.util.CommonUtils;
import com.tibco.ps.common.util.CompositeLogger;
import com.tibco.ps.common.util.wsapi.CisApiFactory;
import com.tibco.ps.common.util.wsapi.CompositeServer;
import com.tibco.ps.common.util.wsapi.WsApiHelperObjects;
import com.tibco.ps.deploytool.dao.UserDAO;
import com.tibco.ps.deploytool.util.DeployUtil;
import com.compositesw.services.system.admin.CreateUserSoapFault;
import com.compositesw.services.system.admin.DestroyUserSoapFault;
import com.compositesw.services.system.admin.GetDomainUsersSoapFault;
import com.compositesw.services.system.admin.GetDomainsSoapFault;
import com.compositesw.services.system.admin.GetUsersSoapFault;
import com.compositesw.services.system.admin.UpdateUserSoapFault;
import com.compositesw.services.system.admin.UserPortType;
import com.compositesw.services.system.admin.user.Domain;
import com.compositesw.services.system.admin.user.DomainList;
import com.compositesw.services.system.admin.user.DomainMemberReference;
import com.compositesw.services.system.admin.user.DomainMemberReferenceList;
import com.compositesw.services.system.admin.user.ScopeValue;
import com.compositesw.services.system.admin.user.User;
import com.compositesw.services.system.admin.user.UserList;
import com.compositesw.services.system.util.common.DetailLevel;
import com.compositesw.services.system.util.common.NameList;

public class UserWSDAOImpl implements UserDAO {
	
	private static Log logger = LogFactory.getLog(UserWSDAOImpl.class);

	public void takeUserAction(String actionName, String userName, String oldPassword, String password, String domainName, DomainMemberReferenceList groupNames, String explicitRights, String annotation, String serverId, String pathToServersXML) throws CompositeException {
		
		if(logger.isDebugEnabled()) {
			String annotationStr = (annotation == null) ? null : "\""+annotation+"\"";
			int groupNamesSize = 0;
			if (groupNames != null && groupNames.getEntry() != null)
				groupNamesSize = groupNames.getEntry().size();
			
			logger.debug("UserWSDAOImpl.takeUserAction(actionName , userName, oldPassword, password, domainName, groupNames, explicitRights, annotation, serverId, pathToServersXML).  actionName="+actionName+"  userName="+userName+"  oldPassword=********"+"  password=********"+"  domainName="+domainName+"  #groupNames="+groupNamesSize+"  explicitRights="+explicitRights+"  annotation="+annotationStr+"  serverId="+serverId+"  pathToServersXML="+pathToServersXML);
		}
		String command = null;

		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "UserWSDAOImpl.takeUserAction("+actionName+")", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		UserPortType port = CisApiFactory.getUserPort(targetServer);

			try {
				if (actionName.equalsIgnoreCase(UserDAO.action.CREATE.name()))
				{
					command = "createUser";

					if(logger.isDebugEnabled()) {
						String annotationStr = (annotation == null) ? null : "\""+annotation+"\"";
						String explicitRightsStr = (explicitRights == null) ? null : "\""+explicitRights+"\"";
						logger.debug("UserWSDAOImpl.takeUserAction(\""+actionName+"\").  Invoking port.createUser(\""+domainName+"\", \""+userName+"\", ********, "+explicitRightsStr+", "+annotationStr+").");
					}

					// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
					if (CommonUtils.isExecOperation()) 
					{					
						port.createUser(domainName, userName, password, explicitRights, annotation);
	
						if(logger.isDebugEnabled()) {
							logger.debug("UserWSDAOImpl.takeUserAction(\""+actionName+"\").  Success: port.createUser().");
						}
					} else {
						logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
					}
				}
				else if(actionName.equalsIgnoreCase(UserDAO.action.UPDATE.name()))
				{
					command = "updateUser";

					if(logger.isDebugEnabled()) {
						String annotationStr = (annotation == null) ? null : "\""+annotation+"\"";
						String explicitRightsStr = (explicitRights == null) ? null : "\""+explicitRights+"\"";
						String groupList = "";
						if (groupNames != null && groupNames.getEntry() != null) {
							for (DomainMemberReference name:groupNames.getEntry()) {
								if (groupList.length() != 0)
									groupList = groupList + ", ";
								groupList = groupList + name.getName()+"@"+name.getDomain();
							}
						}
						logger.debug("UserWSDAOImpl.takeUserAction(\""+actionName+"\").  Invoking port.updateUser(\""+domainName+"\", \""+userName+"\", ********, *********, \"GROUP_NAMES:["+groupList+"]\", "+explicitRightsStr+", "+annotationStr+").");
					}
					
					// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
					if (CommonUtils.isExecOperation()) 
					{					
						port.updateUser(domainName, userName, oldPassword, password, groupNames, explicitRights, annotation);
	
						if(logger.isDebugEnabled()) {
							logger.debug("UserWSDAOImpl.takeUserAction(\""+actionName+"\").  Success: port.updateUser().");
						}
					} else {
						logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
					}
				}
				else if(actionName.equalsIgnoreCase(UserDAO.action.DELETE.name()))
				{
					command = "destroyUser";

					if(logger.isDebugEnabled()) {
						logger.debug("UserWSDAOImpl.takeUserAction(\""+actionName+"\").  Invoking port.destroyUser(\""+domainName+"\", \""+userName+"\").");
					}
					
					// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
					if (CommonUtils.isExecOperation()) 
					{					
						port.destroyUser(domainName, userName);
						
						if(logger.isDebugEnabled()) {
							logger.debug("UserWSDAOImpl.takeUserAction(\""+actionName+"\").  Success: port.destroyUser().");
						}
					} else {
						logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
					}
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

//	@Override
	public UserList getUsers(String userName, String domainName, String serverId, String pathToServersXML) throws CompositeException {

		if(logger.isDebugEnabled()) {
			logger.debug("UserWSDAOImpl.getUsers(userName, domainName, serverId, pathToServersXML).  userName="+userName+"  domainName="+domainName+"  serverId="+serverId+"  pathToServersXML="+pathToServersXML);
		}

		UserList userList = null;
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "UserWSDAOImpl.getUsers", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		UserPortType port = CisApiFactory.getUserPort(targetServer);
		try {
			if(domainName != null && domainName.trim().length() > 0 && !domainName.trim().equals("\"\"") && userName != null && userName.trim().length() > 0){
				NameList names = new NameList();
				names.getName().add(userName);
				if(logger.isDebugEnabled()) {
					logger.debug("UserWSDAOImpl.getUsers().  Invoking port.getUsers(\""+domainName+"\", \""+userName+"\").");
				}
				
				userList = port.getUsers(domainName, names);
				
				if(logger.isDebugEnabled()) {
					logger.debug("UserWSDAOImpl.getUsers().  Success: port.getUsers().");
				}
			}
		} catch (GetUsersSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getUsers", "User", userName, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}
		 return userList;
	}

//	@Override
	public UserList getAllUsers(ArrayList<String> validUsers, String domainName, String serverId, String pathToServersXML) throws CompositeException {

		if(logger.isDebugEnabled()) {
			int validUsersSize = 0;
			if (validUsers != null)
				validUsersSize = validUsers.size();
			
			logger.debug("UserWSDAOImpl.getAllUsers(validUsers, domainName, serverId, pathToServersXML).  #validUsers="+validUsersSize+"  domainName="+domainName+"  serverId="+serverId+"  pathToServersXML="+pathToServersXML);
		}

		UserList userList = null;
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "UserWSDAOImpl.getAllUsers", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		UserPortType port = CisApiFactory.getUserPort(targetServer);
		try {
			userList = new UserList();
			if(domainName != null && domainName.trim().length() > 0 && !domainName.trim().equals("\"\""))
			{
				if(logger.isDebugEnabled()) {
					logger.debug("UserWSDAOImpl.getAllUsers().  Invoking port.getDomainUsers(\""+domainName+"\", \"LOCAL_ONLY\").");
				}
				
				// Since the domainName is not null or empty then only get users for this domain
				UserList userTempList1 = port.getDomainUsers(domainName, ScopeValue.LOCAL_ONLY);
				
				if(logger.isDebugEnabled()) {
					logger.debug("UserWSDAOImpl.getAllUsers().  Success: port.getDomainUsers().");
				}
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
				if(logger.isDebugEnabled()) {
					logger.debug("UserWSDAOImpl.getAllUsers().  Invoking port.getDomains(DetailLevel.SIMPLE).");
				}
				
				// Since domainName is NULL, then get the list of domains in the CIS instance
				DomainList domainsList = port.getDomains(DetailLevel.SIMPLE);
				
				if(logger.isDebugEnabled()) {
					logger.debug("UserWSDAOImpl.getAllUsers().  Success: port.getDomains().");
				}
				if(domainsList != null && domainsList.getDomain() != null){
					List<Domain> domainList = domainsList.getDomain();
					for (Domain domain : domainList) {
						if(domain != null && domain.getName() != null)
						{
							if(logger.isDebugEnabled()) {
								logger.debug("UserWSDAOImpl.getAllUsers().  Invoking port.getDomainUsers(\""+domain.getName()+"\", \"LOCAL_ONLY\").");
							}
							
							// Get all the users for the domain
							UserList userTempList2 = port.getDomainUsers(domain.getName(), ScopeValue.LOCAL_ONLY);
							
							if(logger.isDebugEnabled()) {
								logger.debug("UserWSDAOImpl.getAllUsers().  Success: port.getDomainUsers().");
							}
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
