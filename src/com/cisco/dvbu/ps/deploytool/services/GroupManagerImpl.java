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

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;

import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.exception.ValidationException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.XMLUtils;
import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;
import com.cisco.dvbu.ps.common.util.wsapi.WsApiHelperObjects;
import com.cisco.dvbu.ps.deploytool.dao.GroupDAO;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.GroupWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.cisco.dvbu.ps.deploytool.modules.AccessRightsValidationList;
import com.cisco.dvbu.ps.deploytool.modules.GroupModule;
import com.cisco.dvbu.ps.deploytool.modules.GroupType;
import com.cisco.dvbu.ps.deploytool.modules.ObjectFactory;
import com.compositesw.services.system.admin.user.Group;
import com.compositesw.services.system.admin.user.GroupList;


public class GroupManagerImpl implements GroupManager {

	private static Log logger = LogFactory.getLog(GroupManagerImpl.class);

    private GroupDAO groupDAO = null;

	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.GroupManager#createOrUpdateResource(java.lang.String)
	 */
//	@Override
	public void createOrUpdateGroups(String serverId, String groupIds, String pathToGroupsXML, String pathToServersXML) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering GroupManagerImpl.createOrUpdateGroups() with following params "+" serverId: "+serverId+", groupIds: "+groupIds+", pathToGroupsXML: "+pathToGroupsXML+", pathToServersXML: "+pathToServersXML);
		}

		doGroupAction(GroupDAO.action.CREATEORUPDATE.name(), serverId, groupIds,null, pathToGroupsXML, pathToServersXML);
	}

	public void deleteGroups(String serverId, String groupIds, String pathToGroupsXML, String pathToServersXML) throws CompositeException {
		doGroupAction(GroupDAO.action.DELETE.name(), serverId, groupIds,null, pathToGroupsXML, pathToServersXML);
	}

//	@Override
	public void addUsersToGroups(String serverId, String groupIds,String userName, String pathToGroupsXML, String pathToServersXML) throws CompositeException {
		doGroupAction(GroupDAO.action.ADDUSER.name(), serverId, groupIds,userName, pathToGroupsXML, pathToServersXML);
	}

//	@Override
	public void deleteUsersFromGroups(String serverId, String groupIds,String userName, String pathToGroupsXML, String pathToServersXML) throws CompositeException {
		doGroupAction(GroupDAO.action.REMOVEUSER.name(), serverId, groupIds,userName, pathToGroupsXML, pathToServersXML);

	}

	public void generateGroupsXML(String serverId, String domainName, String pathToGroupsXML, String pathToServersXML) throws CompositeException {

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// Set the Module Action Objective
		String s1 = (domainName == null) ? "no_domainName" : "Domain="+domainName;
		System.setProperty("MODULE_ACTION_OBJECTIVE", "GENERATE : "+s1);

		GroupList groupList = getGroupDAO().getAllGroups(domainName, serverId, pathToServersXML);

		GroupModule groupModule = new ObjectFactory().createGroupModule();

		if(groupList != null && groupList.getGroup() != null){

			List<Group> groups = groupList.getGroup();

			int i = 0;
			for (Group group : groups) {

				GroupType groupType = new GroupType();
				groupType.setId(group.getName()+"-"+i);
				groupType.setGroupDomain(group.getDomainName());
				if (group.getDomainName().equalsIgnoreCase("composite")) {
					groupType.setGroupName(group.getName().toLowerCase());
				} else {
					groupType.setGroupName(group.getName());
				}

				// Create the list of privilege access rights
				if(group.getExplicitRights() != null){
					StringTokenizer st = new StringTokenizer(group.getExplicitRights(), " ");
					while (st.hasMoreTokens()){
						String token = st.nextToken();
						groupType.getPrivilege().add(AccessRightsValidationList.valueOf(token));
					}
				}

				i ++;
				groupModule.getGroup().add(groupType);
			}
			XMLUtils.createXMLFromModuleType(groupModule, pathToGroupsXML);
		}
	}

	private void doGroupAction(String actionName, String serverId, String groupIds, String userName, String pathToGroupsXML, String pathToServersXML) throws CompositeException {

		String prefix = "doGroupAction";
		String processedIds = null;
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToGroupsXML)) {
			throw new CompositeException("File ["+pathToGroupsXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		List<GroupType> groupsList = getGroups(serverId, groupIds, pathToGroupsXML, pathToServersXML);
		// Get the configuration property file set in the environment with a default of deploy.properties
		String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");

		// Extract variables for the groupIds
		groupIds = CommonUtils.extractVariable(prefix, groupIds, propertyFile, true);

		// Set the Module Action Objective
		String s1 = (groupIds == null) ? "no_groupIds" : "Ids="+groupIds;
		System.setProperty("MODULE_ACTION_OBJECTIVE", actionName+" : "+s1);

		if (groupsList != null && groupsList.size() > 0) {

			String groupName = null;
			String groupDomain = null;
			String accessRights = null;
			
			for (GroupType currGroup : groupsList) {

				// Get the identifier and convert any $VARIABLES
				String identifier = CommonUtils.extractVariable(prefix, currGroup.getId(), propertyFile, true);
					
				if(DeployUtil.canProcessResource(groupIds, identifier))
				{
					// Add to the list of processed ids
					if (processedIds == null)
						processedIds = "";
					else
						processedIds = processedIds + ",";
					processedIds = processedIds + identifier;

					//groupName = currGroup.getGroupName().toLowerCase();
					//Change to lower case only if the domain is composite. AD domain can have mixed case domain names
					if (currGroup.getGroupDomain().equalsIgnoreCase("composite")) {
						groupName = currGroup.getGroupName().toLowerCase();
					} else {
						groupName = currGroup.getGroupName();
					}
					groupDomain = currGroup.getGroupDomain();

					accessRights = "";
					// Get the list of group privilege access rights
					if (currGroup.getPrivilege() != null && currGroup.getPrivilege().size() > 0) {
						// Set the privileges as a space separate list						
						for (AccessRightsValidationList privilege : currGroup.getPrivilege()) {
							accessRights = accessRights+privilege+" ";
						}
						if (accessRights.length() > 0) {
							accessRights = accessRights.substring(0, accessRights.length()-1);
						}
					}

					String actionName2 = actionName;
					if(actionName.equalsIgnoreCase(GroupDAO.action.CREATEORUPDATE.name())){
						Group group = getGroupDAO().getGroup(groupName, groupDomain, serverId, pathToServersXML);
						actionName2 = GroupDAO.action.CREATE.name();

						if(group != null && group.getId() > 0){
							actionName2 = GroupDAO.action.UPDATE.name();
							//logger.info("Group: "+groupName+"  found in getGroup. Switching from create to update. Retreived group name is: "+group.getName()+" group id is: "+group.getId()+" domain name is: "+group.getDomainName());
						}
					}

					if(actionName.equalsIgnoreCase(GroupDAO.action.DELETE.name())) {
						Group group = getGroupDAO().getGroup(groupName, groupDomain, serverId, pathToServersXML);
						if (group != null) {
							// Set the Module Action Objective
							s1 = identifier+"="+userName+"-->"+groupName+"@"+groupDomain;
							System.setProperty("MODULE_ACTION_OBJECTIVE", actionName2+" : "+s1);

							getGroupDAO().takeGroupAction(actionName2, groupName, groupDomain, userName, accessRights, serverId, pathToServersXML);
							logger.info("Success: action="+actionName+"  user: "+userName);

						} else {
							// Set the Module Action Objective
							s1 = identifier+"="+groupName+"@"+groupDomain+" not found.";
							System.setProperty("MODULE_ACTION_OBJECTIVE", actionName+" : "+s1);
							
							logger.info("No "+actionName+" action taken due to group ("+groupName+") not found.");	
						}
					} else {
						// Set the Module Action Objective
						s1 = identifier+"="+userName+"-->"+groupName+"@"+groupDomain;
						System.setProperty("MODULE_ACTION_OBJECTIVE", actionName2+" : "+s1);

						getGroupDAO().takeGroupAction(actionName2, groupName, groupDomain, userName, accessRights, serverId, pathToServersXML);
						logger.info("Successfully performed action="+actionName2+" for the group: "+groupName);
					}
				}
			}
			// Determine if any resourceIds were not processed and report on this
			if (processedIds != null) {
				if(logger.isInfoEnabled()){
					logger.info("Group entries processed="+processedIds);
				}
			} else {
				if(logger.isInfoEnabled()){
					String msg = "Warning: No group entries were processed for the input list.  groupIds="+groupIds;
					logger.info(msg);
					System.setProperty("MODULE_ACTION_MESSAGE", msg);
				}		
			}
		} else {
			if(logger.isInfoEnabled()){
				String msg = "Warning: No group entries found for Group Module XML at path="+pathToGroupsXML;
				logger.info(msg);
				System.setProperty("MODULE_ACTION_MESSAGE", msg);
			}		
		}
	}

	private List<GroupType> getGroups(String serverId, String groups,	String pathToGroupsXML, String pathToServersXML) {
		// validate incoming arguments
		if(serverId == null || serverId.trim().length() ==0 || groups == null || groups.trim().length() ==0 || pathToServersXML == null || pathToServersXML.trim().length() ==0 || pathToGroupsXML == null || pathToGroupsXML.trim().length() ==0){
			throw new ValidationException("Invalid Arguments");
		}

		try {
			//using jaxb convert xml to corresponding java objects
			GroupModule groupModuleType = (GroupModule)XMLUtils.getModuleTypeFromXML(pathToGroupsXML);
			if(groupModuleType != null && groupModuleType.getGroup() != null && !groupModuleType.getGroup().isEmpty()){
				return groupModuleType.getGroup();
			}
		} catch (CompositeException e) {
			CompositeServer targetServer = WsApiHelperObjects.getServer(serverId, pathToServersXML);
			String errorMessage = DeployUtil.constructMessage(DeployUtil.MessageType.ERROR.name(), "Parse Group XML", "GroupManagerImpl", pathToGroupsXML, targetServer);

			logger.error(errorMessage , e);
			throw new ApplicationContextException(errorMessage, e);
		}
		return null;
	}

	public GroupDAO getGroupDAO() {
		if(groupDAO == null){
			groupDAO = new GroupWSDAOImpl();
		}
		return groupDAO;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}


}
