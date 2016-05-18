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

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.CompositeLogger;
import com.cisco.dvbu.ps.common.util.wsapi.CisApiFactory;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.dao.GroupDAO;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.compositesw.services.system.admin.AddUserToGroupsSoapFault;
import com.compositesw.services.system.admin.CreateGroupSoapFault;
import com.compositesw.services.system.admin.DestroyGroupSoapFault;
import com.compositesw.services.system.admin.GetDomainGroupsSoapFault;
import com.compositesw.services.system.admin.GetDomainsSoapFault;
import com.compositesw.services.system.admin.GetGroupsByUserSoapFault;
import com.compositesw.services.system.admin.RemoveUserFromGroupsSoapFault;
import com.compositesw.services.system.admin.UpdateGroupSoapFault;
import com.compositesw.services.system.admin.UserPortType;
import com.compositesw.services.system.admin.user.Domain;
import com.compositesw.services.system.admin.user.DomainList;
import com.compositesw.services.system.admin.user.DomainMemberReference;
import com.compositesw.services.system.admin.user.DomainMemberReferenceList;
import com.compositesw.services.system.admin.user.Group;
import com.compositesw.services.system.admin.user.GroupList;
import com.compositesw.services.system.admin.user.ScopeValue;
import com.compositesw.services.system.util.common.DetailLevel;


public class GroupWSDAOImpl implements GroupDAO {

	private static Log logger = LogFactory.getLog(DataSourceWSDAOImpl.class);

	public void takeGroupAction(String actionName, String groupName, String groupDomain, String userNames, String privileges, String serverId, String pathToServersXML) throws CompositeException {

		if(logger.isDebugEnabled()) {
			logger.debug("GroupWSDAOImpl.takeGroupAction(actionName, groupName, groupDomain, userNames, privileges, serverId, pathToServersXML).  actionName="+actionName+"  groupName="+groupName+"  groupDomain="+groupDomain+"  userNames="+userNames+"  privileges="+privileges+"  serverId="+serverId+"  pathToServersXML="+pathToServersXML);
		}
		String command = null;
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "GroupWSDAOImpl.takeGroupAction("+actionName+")", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		UserPortType port = CisApiFactory.getUserPort(targetServer);

			try {
				if (actionName.equalsIgnoreCase(GroupDAO.action.CREATE.name()))
				{
					command = "createGroup";
					
					if(logger.isDebugEnabled()) {
						logger.debug("GroupWSDAOImpl.takeGroupAction("+actionName+").  Invoking port.createGroup(\""+groupDomain+"\", \""+groupName+"\", \""+privileges+"\", null).");
					}
					
					// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
					if (CommonUtils.isExecOperation()) 
					{					
						port.createGroup(groupDomain,groupName,privileges, null);
					
						if(logger.isDebugEnabled()) {
							logger.debug("GroupWSDAOImpl.takeGroupAction("+actionName+").  Success: port.createGroup().");
						}
					} else {
						logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
					}

				}
				else if(actionName.equalsIgnoreCase(GroupDAO.action.UPDATE.name()))
				{
					command = "updateGroup";
					
					if(logger.isDebugEnabled()) {
						logger.debug("GroupWSDAOImpl.takeGroupAction("+actionName+").  Invoking port.updateGroup(\""+groupDomain+"\", \""+groupName+"\", null, \""+privileges+"\", null).");
					}
					
					// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
					if (CommonUtils.isExecOperation()) 
					{					
						port.updateGroup(groupDomain, groupName, null, privileges, null);
	
						if(logger.isDebugEnabled()) {
							logger.debug("GroupWSDAOImpl.takeGroupAction("+actionName+").  Success: port.updateGroup().");
						}
					} else {
						logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
					}

				}
				else if(actionName.equalsIgnoreCase(GroupDAO.action.DELETE.name()))
				{
					command = "destroyGroup";
					
					if(logger.isDebugEnabled()) {
						logger.debug("GroupWSDAOImpl.takeGroupAction("+actionName+").  Invoking port.destroyGroup(\""+groupDomain+"\", \""+groupName+"\").");
					}

					// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
					if (CommonUtils.isExecOperation()) 
					{					
						port.destroyGroup(groupDomain, groupName);
	
						if(logger.isDebugEnabled()) {
							logger.debug("GroupWSDAOImpl.takeGroupAction("+actionName+").  Success: port.destroyGroup().");
						}
					} else {
						logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
					}
				} 
				else if(actionName.equalsIgnoreCase(GroupDAO.action.ADDUSER.name()))
				{
					command = "addUserToGroups";
					
					if(userNames!= null){
						StringTokenizer st = new StringTokenizer(userNames, ",");
						while (st.hasMoreTokens()){
							String userName = st.nextToken();
							if(userName != null){
								DomainMemberReferenceList domainMemberReferenceList = getDomainMemberReferenceList(groupName, groupDomain);
							
								if(logger.isDebugEnabled()) {
									String mbrList = "";
									if (domainMemberReferenceList != null && domainMemberReferenceList.getEntry() != null) {
										for (DomainMemberReference member:domainMemberReferenceList.getEntry()) {
											if (mbrList.length() != 0)
												mbrList = mbrList + ", ";
											mbrList = mbrList + member.getName() + "@" + member.getDomain();
										}
									}
									logger.debug("GroupWSDAOImpl.takeGroupAction("+actionName+").  Invoking port.addUserToGroups(\""+groupDomain+"\", \""+userName.trim()+"\", MEMBER_LIST:[\""+mbrList+"\"]).");
								}

								// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
								if (CommonUtils.isExecOperation()) 
								{					
									port.addUserToGroups(groupDomain, userName.trim(), domainMemberReferenceList);
	
									if(logger.isDebugEnabled()) {
										logger.debug("GroupWSDAOImpl.takeGroupAction("+actionName+").  Success: port.addUserToGroups().");
									}
								} else {
									logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
								}
							}
						}
					}

                } 
				else if(actionName.equalsIgnoreCase(GroupDAO.action.REMOVEUSER.name()))
				{
					command = "removeUserFromGroups";
					
					if(userNames!= null){
						StringTokenizer st = new StringTokenizer(userNames, ",");
						while (st.hasMoreTokens()){
							String userName = st.nextToken();
							if(userName != null){

			                	DomainMemberReferenceList domainMemberReferenceList = getDomainMemberReferenceList(groupName, groupDomain);

								if(logger.isDebugEnabled()) {
									String mbrList = "";
									if (domainMemberReferenceList != null && domainMemberReferenceList.getEntry() != null) {
										for (DomainMemberReference member:domainMemberReferenceList.getEntry()) {
											if (mbrList.length() != 0)
												mbrList = mbrList + ", ";
											mbrList = mbrList + member.getName() + "@" + member.getDomain();
										}
									}
									logger.debug("GroupWSDAOImpl.takeGroupAction("+actionName+").  Invoking port.removeUserFromGroups(\""+groupDomain+"\", \""+userName.trim()+"\", MEMBER_LIST:[\""+mbrList+"\"]).");
								}

								// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
								if (CommonUtils.isExecOperation()) 
								{					
									port.removeUserFromGroups(groupDomain, userName.trim(), domainMemberReferenceList);
	
									if(logger.isDebugEnabled()) {
										logger.debug("GroupWSDAOImpl.takeGroupAction("+actionName+").  Success: port.removeUserFromGroups().");
									}
								} else {
									logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
								}
							}
						}
					}
                }

			} catch (RemoveUserFromGroupsSoapFault e) {
				CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), actionName, "Group", groupName, targetServer),e.getFaultInfo());
				throw new ApplicationException(e.getMessage(), e);
				
			} catch (AddUserToGroupsSoapFault e) {				
				CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), actionName, "Group", groupName, targetServer),e.getFaultInfo());
				throw new ApplicationException(e.getMessage(), e);

			} catch (DestroyGroupSoapFault e) {
				CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), actionName, "Group", groupName, targetServer),e.getFaultInfo());
				throw new ApplicationException(e.getMessage(), e);

			} catch (UpdateGroupSoapFault e) {				
				CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), actionName, "Group", groupName, targetServer),e.getFaultInfo());
				throw new ApplicationException(e.getMessage(), e);
		
			} catch (CreateGroupSoapFault e) {
				CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), actionName, "Group", groupName, targetServer),e.getFaultInfo());
				throw new ApplicationException(e.getMessage(), e);
			}
	}

	private DomainMemberReferenceList getDomainMemberReferenceList(String groupName, String groupDomain) {
		DomainMemberReference domainMemberReference = new DomainMemberReference();
		domainMemberReference.setDomain(groupDomain);
		domainMemberReference.setName(groupName);

		DomainMemberReferenceList domainMemberReferenceList = new DomainMemberReferenceList();
		domainMemberReferenceList.getEntry().add(domainMemberReference);
		return domainMemberReferenceList;
	}

//	@Override
	public Group getGroup(String groupName, String groupDomain,String serverId, String pathToServersXML) {

		if(logger.isDebugEnabled()) {
			logger.debug("GroupWSDAOImpl.getGroup(groupName, groupDomain, serverId, pathToServersXML).  groupName="+groupName+"  groupDomain="+groupDomain+"  serverId="+serverId+"  pathToServersXML="+pathToServersXML);
		}

		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		UserPortType port = CisApiFactory.getUserPort(targetServer);
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("GroupWSDAOImpl.getGroup().  Invoking port.getDomainGroups(\""+groupDomain+"\", \"LOCAL_ONLY\").");
			}
			
			GroupList groupsList = port.getDomainGroups(groupDomain, ScopeValue.LOCAL_ONLY);

			if(logger.isDebugEnabled()) {
				logger.debug("GroupWSDAOImpl.getGroup().  Success: port.getDomainGroups().");
			}

			if(groupsList != null && groupsList.getGroup() != null){
				List<Group> groups = groupsList.getGroup();
				for (Group group : groups) {
					if(group.getName().equalsIgnoreCase(groupName)){
						return group;
					}
				}
			}
		} catch (GetDomainGroupsSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getGroup", "Group", groupName, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}
		return null;
	}

//	@Override
	public GroupList getAllGroups(String groupDomain, String serverId, String pathToServersXML) {

		if(logger.isDebugEnabled()) {
			logger.debug("GroupWSDAOImpl.getAllGroups(groupDomain, serverId, pathToServersXML).  groupDomain="+groupDomain+"  serverId="+serverId+"  pathToServersXML="+pathToServersXML);
		}

		GroupList groupsList = null;
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "GroupWSDAOImpl.getAllGroups", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		UserPortType port = CisApiFactory.getUserPort(targetServer);
		try {
			if(groupDomain != null && groupDomain.trim().length() > 0 && !groupDomain.trim().equals("\"\"")){

				if(logger.isDebugEnabled()) {
					logger.debug("GroupWSDAOImpl.getAllGroups().  Invoking port.getDomainGroups(\""+groupDomain+"\", \"LOCAL_ONLY\").");
				}

				groupsList = port.getDomainGroups(groupDomain, ScopeValue.LOCAL_ONLY);

				if(logger.isDebugEnabled()) {
					logger.debug("GroupWSDAOImpl.getAllGroups().  Success: port.getDomainGroups().");
				}

			}else{
				DomainList domainsList = port.getDomains(DetailLevel.SIMPLE);
				if(domainsList != null && domainsList.getDomain() != null){
					groupsList = new GroupList();
					List<Domain> domainList = domainsList.getDomain();
					for (Domain domain : domainList) {
						if(domain != null && domain.getName() != null)
						{
							GroupList domainGroupsList = port.getDomainGroups(domain.getName(), ScopeValue.LOCAL_ONLY);
							if(domainGroupsList != null && domainGroupsList.getGroup() != null){
								groupsList.getGroup().addAll(domainGroupsList.getGroup());
							}
						}
					}
				}
			}

		} catch (GetDomainGroupsSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getAllGroups", "Group", groupDomain, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		} catch (GetDomainsSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getAllGroups", "Group", groupDomain, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}
		 return groupsList;
	}
	
//	@Override
	public GroupList getGroupsByUser(String userName, String domainName, String serverId, String pathToServersXML) throws CompositeException {

		if(logger.isDebugEnabled()) {
			logger.debug("GroupWSDAOImpl.getGroupsByUser(groupDomain, serverId, pathToServersXML).  userName="+userName+"  domainName="+domainName+"  serverId="+serverId+"  pathToServersXML="+pathToServersXML);
		}

		GroupList groupList = null;
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		UserPortType port = CisApiFactory.getUserPort(targetServer);
		try {
			if(domainName != null && domainName.trim().length() > 0 && !domainName.trim().equals("\"\"") && userName != null && userName.trim().length() > 0){
				if(logger.isDebugEnabled()) {
					logger.debug("GroupWSDAOImpl.getGroupsByUser().  Invoking port.getGroupsByUser(\""+userName+"\", \""+domainName+"\").");
				}

				groupList = port.getGroupsByUser(userName, domainName);

				if(logger.isDebugEnabled()) {
					logger.debug("GroupWSDAOImpl.getGroupsByUser().  Success: port.getGroupsByUser().");
				}
			}
		} catch (GetGroupsByUserSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getGroupsByUser", "Group", userName+", "+domainName, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}
		 return groupList;
	}

}
