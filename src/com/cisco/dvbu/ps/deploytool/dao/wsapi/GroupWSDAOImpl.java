/*******************************************************************************
* Copyright (c) 2014 Cisco Systems
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* PDTool project commiters - initial release
*******************************************************************************/
/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.deploytool.dao.wsapi;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.cisco.dvbu.ps.common.exception.CompositeException;
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

		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "GroupWSDAOImpl.takeGroupAction("+actionName+")", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		UserPortType port = CisApiFactory.getUserPort(targetServer);

			try {
				if(actionName.equalsIgnoreCase(GroupDAO.action.CREATE.name())){

					port.createGroup(groupDomain,groupName,privileges, null);

				}else if(actionName.equalsIgnoreCase(GroupDAO.action.UPDATE.name())){

					port.updateGroup(groupDomain, groupName, null, privileges, null);

				}else if(actionName.equalsIgnoreCase(GroupDAO.action.DELETE.name())){

					port.destroyGroup(groupDomain, groupName);

				}else if(actionName.equalsIgnoreCase(GroupDAO.action.ADDUSER.name())){

					if(userNames!= null){
						StringTokenizer st = new StringTokenizer(userNames, ",");
						while (st.hasMoreTokens()){
							String userName = st.nextToken();
							if(userName != null){
								DomainMemberReferenceList domainMemberReferenceList = getDomainMemberReferenceList(groupName, groupDomain);
								port.addUserToGroups(groupDomain, userName.trim(), domainMemberReferenceList);

							}
						}
					}

                }else if(actionName.equalsIgnoreCase(GroupDAO.action.REMOVEUSER.name())){

					if(userNames!= null){
						StringTokenizer st = new StringTokenizer(userNames, ",");
						while (st.hasMoreTokens()){
							String userName = st.nextToken();
							if(userName != null){

			                	DomainMemberReferenceList domainMemberReferenceList = getDomainMemberReferenceList(groupName, groupDomain);
								port.removeUserFromGroups(groupDomain, userName.trim(), domainMemberReferenceList);
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
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		UserPortType port = CisApiFactory.getUserPort(targetServer);
		try {
			GroupList groupsList = port.getDomainGroups(groupDomain, ScopeValue.LOCAL_ONLY);
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
		GroupList groupsList = null;
		CompositeServer targetServer = WsApiHelperObjects.getServerLogger(serverId, pathToServersXML, "GroupWSDAOImpl.getAllGroups", logger);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		UserPortType port = CisApiFactory.getUserPort(targetServer);
		try {
			if(groupDomain != null && groupDomain.trim().length() > 0 && !groupDomain.trim().equals("\"\"")){
				groupsList = port.getDomainGroups(groupDomain, ScopeValue.LOCAL_ONLY);

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
		GroupList groupList = null;
		CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
		// Ping the Server to make sure it is alive and the values are correct.
		WsApiHelperObjects.pingServer(targetServer, true);

		UserPortType port = CisApiFactory.getUserPort(targetServer);
		try {
			if(domainName != null && domainName.trim().length() > 0 && !domainName.trim().equals("\"\"") && userName != null && userName.trim().length() > 0){
				groupList = port.getGroupsByUser(userName, domainName);
			}
		} catch (GetGroupsByUserSoapFault e) {
			CompositeLogger.logException(e, DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "getGroupsByUser", "Group", userName+", "+domainName, targetServer),e.getFaultInfo());
			throw new ApplicationException(e.getMessage(), e);
		}
		 return groupList;
	}

}
