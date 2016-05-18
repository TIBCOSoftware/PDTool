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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;

import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.exception.ValidationException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.PropertyManager;
import com.cisco.dvbu.ps.common.util.XMLUtils;
import com.cisco.dvbu.ps.deploytool.dao.GroupDAO;
import com.cisco.dvbu.ps.deploytool.dao.UserDAO;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.GroupWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.UserWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.cisco.dvbu.ps.deploytool.modules.AccessRightsValidationList;
import com.cisco.dvbu.ps.deploytool.modules.ObjectFactory;
import com.cisco.dvbu.ps.deploytool.modules.UserModule;
import com.cisco.dvbu.ps.deploytool.modules.UserType;
import com.cisco.dvbu.ps.deploytool.modules.GroupMembershipType;
import com.compositesw.services.system.admin.user.DomainMemberReference;
import com.compositesw.services.system.admin.user.DomainMemberReferenceList;
import com.compositesw.services.system.admin.user.Group;
import com.compositesw.services.system.admin.user.GroupList;
import com.compositesw.services.system.admin.user.User;
import com.compositesw.services.system.admin.user.UserList;

public class UserManagerImpl implements UserManager {

	private static Log logger = LogFactory.getLog(UserManagerImpl.class);
	
    private UserDAO userDAO = null;
    private GroupDAO groupDAO = null;

	// There is a bug whereby the getDomainUsers is not returning group name and domainName values for the GroupMembershipList.Entry nodes 
	private boolean cisBug_getDomainUsers_fixed = false;
	
  
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.ModuleManager#createOrUpdateResource(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void createOrUpdateUsers(String serverId, String userIds, String pathToUsersXML, String pathToServersXML) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering UserManagerImpl.createOrUpdateUsers() with following params "+" serverName: "+serverId+", userIds: "+userIds+", pathToUsersXML: "+pathToUsersXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
			userAction(UserDAO.action.CREATEORUPDATE.name(), serverId, userIds, pathToUsersXML, pathToServersXML);
		} catch (CompositeException e) {
			logger.error("Error while deleting user: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.cisco.dvbu.ps.deploytool.resource.ModuleManager#deleteUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void deleteUsers(String serverId, String userIds, String pathToUsersXML, String pathToServersXML) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug("Entering UserManagerImpl.createOrUpdateUsers() with following params "+" serverName: "+serverId+", userIds: "+userIds+", pathToUsersXML: "+pathToUsersXML+", pathToServersXML: "+pathToServersXML);
		}
		try {
			userAction(UserDAO.action.DELETE.name(), serverId, userIds, pathToUsersXML, pathToServersXML);
		} catch (CompositeException e) {
			logger.error("Error while deleting user: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}		
	}
		
	private void userAction(String actionName, String serverId, String userIds, String pathToUsersXML, String pathToServersXML) throws CompositeException {

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToUsersXML)) {
			throw new CompositeException("File ["+pathToUsersXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		try {
			List<UserType> userModuleList = getUserModuleUsers(pathToUsersXML);

			// Used to construct an array of valid users from the UserModule.xml and based on which users in the list can be processed
			ArrayList<String> validUsers = new ArrayList<String>();
			// This is an array of User/Domain objects that were retrieved from CIS based on the validUsers list.  
			// It is possible that a user will not exist in this array even though they are in the validUsers list (means they don't exist in the domain) 
			ArrayList<UserDomain> userArray = new ArrayList<UserDomain>();

			String prefix = "userAction";
			String processedIds = null;

			// Get the configuration property file set in the environment with a default of deploy.properties
			String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");

			// Extract variables for the userIds
			userIds = CommonUtils.extractVariable(prefix, userIds, propertyFile, true);

			// Set the Module Action Objective
			String s1 = (userIds == null) ? "no_userIds" : "Ids="+userIds;
			System.setProperty("MODULE_ACTION_OBJECTIVE", actionName+" : "+s1);

			// Pre-process the UserModule XML list to determine the number of users to be processed so as to compare with the efficiency threshold
			int processNumUsers = 0;
			
			for (UserType currUser : userModuleList) 
			{
				// Get the identifier and convert any $VARIABLES
				String identifier = CommonUtils.extractVariable(prefix, currUser.getId(), propertyFile, true);
				
				/**
				 * Possible values for archives 
				 * 1. csv string like import1,import2 (we process only resource names which are passed in)
				 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
				 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
				 * 	  like -import1,import3 (we ignore passed in resources and process rest of the in the input xml
				 */
				if(DeployUtil.canProcessResource(userIds, identifier)) {
					// Count the user which can be processed as a valid user
					processNumUsers++;
					// Add the valid user to the validUser list - this will be used to get the set of users from CIS
					validUsers.add(currUser.getUserName().toLowerCase());
				}
			}
			
		    // option 1=get the entire CIS user list up front. 1 server invocation, option 2=invoke CIS for each user in the UserModule.xml file.
			int getUserOption = 1; 
			// This is the threshold of the number of users where it is more efficient to go with option 1 and retrieve all users at once vs. option 2 which calls the API to retrieve 1 user at at time 
			int getUserOptionThreshold = 0;

			String userOptionThreshold = PropertyManager.getInstance().getProperty(propertyFile,"userOptionThreshold");
			if (userOptionThreshold != null) {
				try {
					getUserOptionThreshold = Integer.valueOf(userOptionThreshold);
				}
				catch (Exception e) {
					getUserOptionThreshold = 0;
				}
			}
			// Balance between the amount of memory required vs. efficiency of invocation to the CIS server
			if (processNumUsers > getUserOptionThreshold) {
				// If the number of users to be processed in the UserModule XML file exceeds the threshold then
				//   set the getUserOption=1 and so as to retrieve the entire CIS user list one time
				//   This options requires storing all CIS user and domain in memory.  If there are 1000's of users, this could take a lot of memory.
				getUserOption = 1;
			} else {
				// If the number of users to be processed in the UserModule XML file is less than the threshold then
				//   set the getUserOption=2 and so as to retrieve each the user info on each invocation of a user in the list
				//   This requires a web service invocation to the CIS server for each user to be processed.
				getUserOption = 2;
			}
			
			if (userModuleList != null && userModuleList.size() > 0) {
	
				String userName = null;
				String oldPassword = null;
				String password = null;
				String domainName = null;
				DomainMemberReferenceList groupNames = null;
				String accessRights = null;
				String annotation = null;
				boolean userArrayListPopulated = false;
			
				// Loop over the list of users and create/update them.
				for (UserType currUser : userModuleList) {
	
					// Get the identifier and convert any $VARIABLES
					String identifier = CommonUtils.extractVariable(prefix, currUser.getId(), propertyFile, true);
					
					/**
					 * Possible values for archives 
					 * 1. csv string like import1,import2 (we process only resource names which are passed in)
					 * 2. '*' or what ever is configured to indicate all resources (we process all resources in this case)
					 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
					 * 	  like -import1,import3 (we ignore passed in resources and process rest of the in the input xml
					 */
					if(DeployUtil.canProcessResource(userIds, identifier)) 
					{
						// Add to the list of processed ids
						if (processedIds == null)
							processedIds = "";
						else
							processedIds = processedIds + ",";
						processedIds = processedIds + identifier;

						// Set the user name
						userName = currUser.getUserName().toLowerCase();
						
						// Add a domain name if it exists
						if (currUser.getDomainName() != null) {
							domainName = currUser.getDomainName();
						}

						// Set the Module Action Objective
						s1 = identifier+"=" + ((userName == null) ? "no_userName" : userName+"@"+domainName);
						System.setProperty("MODULE_ACTION_OBJECTIVE", actionName+" : "+s1);

						// Populate the User Array List based on the option
						// Option 1: Get the entire list of users from CIS and store in a memory ArrayList.  This will only get populated once per invocation
						if (getUserOption == 1) {
							populateUserArrayList(userArrayListPopulated, validUsers, userArray, domainName, serverId, pathToServersXML);
							userArrayListPopulated = true; 				// Only populate the user array once through the loop
						} else{
						// Option 2: Get the user from CIS for the current user.  Repeated calls to CIS for each user in this loop.
							validUsers = new ArrayList<String>(); 		// Initialize valid Users each time through the loop
							validUsers.add(currUser.getUserName()); 	// Add the current valid user
							userArray = new ArrayList<UserDomain>(); 	// Initialize user array
							populateUserArrayList(userArrayListPopulated, validUsers, userArray, domainName, serverId, pathToServersXML);
							userArrayListPopulated = false;				// populate the user array each time through the loop
						}
						
						// Create the group list if it exists
						if (currUser.getGroupMembershipList() != null) {
							groupNames = new DomainMemberReferenceList();
							for (GroupMembershipType groups : currUser.getGroupMembershipList()) {
								DomainMemberReference domain = new DomainMemberReference();
								domain.setName(groups.getGroupName().toLowerCase());
								domain.setDomain(groups.getGroupDomain());
								groupNames.getEntry().add(domain);
							}
						}
						
						accessRights = "";
						// Convert the list of privileges into a comma separated list if they exist
						if (currUser.getPrivilege() != null && currUser.getPrivilege().size() > 0) {
							// Set the privileges as a space separate list							
							for (AccessRightsValidationList privilege : currUser.getPrivilege()) {
								accessRights = accessRights+privilege+" ";
							}
							if (accessRights.length() > 0) {
								accessRights = accessRights.substring(0, accessRights.length()-1);
							}
						}
					
						// Add an annotation if it exists
						if (currUser.getAnnotation() != null) {
							annotation = currUser.getAnnotation();
						}
	
						// Search for the user to see if it exists or not
						boolean userFound = false;
						// Lookup the user from a master list of all users that were already acquired from the CIS server and stored in a memory ArrayList
						for (UserDomain ud : userArray) {
							if (ud.findUserDomain(userName, domainName)) {
								// User / domain combination was found
								userFound = true;
								break;
							}
						}
					
						if(actionName.equalsIgnoreCase(UserDAO.action.CREATEORUPDATE.name())) {
							String actionName2 = null;

							// Extract the group list into a string for logging purposes
							String groupList = "";
							int i=0;
							for (DomainMemberReference groupMember : groupNames.getEntry()) {
								if (i > 0) {
									groupList = groupList + ", ";
								}
								groupList = groupList + groupMember.getName();
								i++;
							}

							if (userFound) {
								actionName2 = UserDAO.action.UPDATE.name();
								if (currUser.isForcePassword()) {
									password = CommonUtils.decrypt(currUser.getEncryptedPassword());
								}
								
								// Set the Module Action Objective
								s1 = identifier+"=" + ((userName == null) ? "no_userName" : userName+"@"+domainName);
								System.setProperty("MODULE_ACTION_OBJECTIVE", actionName2+" : "+s1);

								// Update the user information
								getUserDAO().takeUserAction(actionName2, userName, oldPassword, password, domainName, groupNames, accessRights, annotation, serverId, pathToServersXML);
								logger.info("Success: action="+actionName2+"  user: "+userName+"  update groups:"+groupList);
							} else {
								
								// Create a new user
								actionName2 = UserDAO.action.CREATE.name();
								password = CommonUtils.decrypt(currUser.getEncryptedPassword());

								// Set the Module Action Objective
								s1 = identifier+"=" + ((userName == null) ? "no_userName" : userName+"@"+domainName);
								System.setProperty("MODULE_ACTION_OBJECTIVE", actionName2+" : "+s1);

								// Check to make sure the password is not null or empty
								if (password == null || password.length() == 0) {
									throw new ValidationException("Password cannot be null or empty when creating a new user.");
								}
								getUserDAO().takeUserAction(actionName2, userName, oldPassword, password, domainName, groupNames, accessRights, annotation, serverId, pathToServersXML);
								logger.info("Success: action="+actionName2+"  user: "+userName);
								
								// Once the user is created, an update must be performed to modify their group affiliation
								actionName2 = UserDAO.action.UPDATE.name();
								
								// Set the Module Action Objective
								s1 = identifier+"=" + ((userName == null) ? "no_userName" : userName+"@"+domainName);
								System.setProperty("MODULE_ACTION_OBJECTIVE", actionName2+" : "+s1);

								// Perform the update action on the User to update the group affiliation for the user.
								getUserDAO().takeUserAction(actionName2, userName, null, null, domainName, groupNames, null, null, serverId, pathToServersXML);
								logger.info("Success: action="+actionName2+"  user: "+userName+"  update groups:"+groupList);
							}
						}
	
						if(actionName.equalsIgnoreCase(UserDAO.action.DELETE.name())) {
							if (userFound) {
								// Set the Module Action Objective
								s1 = identifier+"=" + ((userName == null) ? "no_userName" : userName+"@"+domainName);
								System.setProperty("MODULE_ACTION_OBJECTIVE", actionName+" : "+s1);

								getUserDAO().takeUserAction(actionName, userName, oldPassword, password, domainName, groupNames, accessRights, annotation, serverId, pathToServersXML);
								logger.info("Success: action="+actionName+"  user: "+userName);

							} else {
								// Set the Module Action Objective
								s1 = identifier+"=" + ((userName == null) ? "no_userName" : userName+"@"+domainName+" not found.");
								System.setProperty("MODULE_ACTION_OBJECTIVE", actionName+" : "+s1);

								logger.info("No "+actionName+" action taken due to user ("+userName+") not found");	
							}
						}
					}
				}
				// Determine if any resourceIds were not processed and report on this
				if (processedIds != null) {
					if(logger.isInfoEnabled()){
						logger.info("User entries processed="+processedIds);
					}
				} else {
					if(logger.isInfoEnabled()){
						String msg = "Warning: No user entries were processed for the input list.  userIds="+userIds;
						logger.info(msg);
						System.setProperty("MODULE_ACTION_MESSAGE", msg);
					}		
				}
			} else {
				if(logger.isInfoEnabled()){
					String msg = "Warning: No user entries found for User Module XML at path="+pathToUsersXML;
					logger.info(msg);
					System.setProperty("MODULE_ACTION_MESSAGE", msg);
				}									
			}
		} catch (CompositeException e) {
			logger.error("Error while executing action="+actionName , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
	}

	public void generateUsersXML(String serverId, String domainName, String pathToUsersXML, String pathToServersXML) throws CompositeException {

		// Set the command and action name
		String command = "generateUsersXML";
		String actionName = "CREATE_XML";

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// Set the Module Action Objective
		String s1 = (domainName == null) ? "no_domainName" : "Domain="+domainName;
		System.setProperty("MODULE_ACTION_OBJECTIVE", "GENERATE : "+s1);

		try {
			// Prepare a local UserModule XML variable for creating a list of "User" nodes
			// This XML variable will be written out to the specified file. 
			UserModule userModule = new ObjectFactory().createUserModule();
	
			// Get the list of CIS Users
			UserList cisUserList = getUserDAO().getAllUsers(null, domainName, serverId, pathToServersXML);
	
			// Loop over the list of CIS users and generate the UserModule XML
			int count = 1;
			for (User cisUser : cisUserList.getUser()) {
				UserType u = new UserType();
				u.setId("user"+count++);
				u.setUserName(cisUser.getName().toLowerCase());
				u.setDomainName(cisUser.getDomainName());
				u.setAnnotation(cisUser.getAnnotation());
				u.setEncryptedPassword("");
				u.setForcePassword(false);
				// Create the list of privilege access rights
				if (cisUser.getExplicitRights() != null) {
					StringTokenizer st = new StringTokenizer(cisUser.getExplicitRights(), " ");
				    while (st.hasMoreTokens()) {
				    	String token = st.nextToken();
						u.getPrivilege().add(AccessRightsValidationList.valueOf(token));
				    }		
				}
				
				if (cisBug_getDomainUsers_fixed) {
					// BUG:: bug in CIS Admin API getDomainUsers() which returns the Group Membership List but group name and domain are always null even though they should have values 
					if (cisUser.getGroupNames() != null) {
						if (cisUser.getGroupNames().getEntry() != null) {
							// Create the list of Groups
							for (DomainMemberReference entry : cisUser.getGroupNames().getEntry()) {
								GroupMembershipType g = new GroupMembershipType();
								g.setGroupDomain(entry.getDomain());
								g.setGroupName(entry.getName());
								u.getGroupMembershipList().add(g);
							}					
						}
					}
				} else {
					// Alternative 1
					GroupList groupList = getGroupDAO().getGroupsByUser(cisUser.getName(), cisUser.getDomainName(), serverId, pathToServersXML);
					for (Group group : groupList.getGroup()) {
						GroupMembershipType g = new GroupMembershipType();
						g.setGroupDomain(group.getDomainName());
						g.setGroupName(group.getName());
						u.getGroupMembershipList().add(g);
					}					
					
					// Alternative 2
					// BUG:: bug in CIS Admin API getUsers() which returns the User info and Group Membership List but group name and domain are always null even though they should have values
					/*  This code is left here for documentation purposes so as not to lose track of the issue and provide a way in which to test a fix.
					UserList userList = getUserDAO().getUsers(cisUser.getName(), cisUser.getDomainName(), serverId, pathToServersXML);
					if(userList.getUser() != null){
						for (User user : userList.getUser()) {
							if (user.getName().equalsIgnoreCase(cisUser.getName()) && user.getDomainName().equalsIgnoreCase(domainName)) {
								for (DomainMemberReference entry : user.getGroupNames().getEntry()) {
									GroupMembershipType g = new GroupMembershipType();
									g.setGroupDomain(entry.getDomain());
									g.setGroupName(entry.getName());
									u.getGroupMembershipList().add(g);
								}					
							}
						}							
					}
					*/
				}
				// Add the user node
				userModule.getUser().add(u);
			}

			// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
			if (CommonUtils.isExecOperation()) 
			{					
				// Generate the XML file
				XMLUtils.createXMLFromModuleType(userModule, pathToUsersXML);
			} else {
				logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
			}
		} catch (CompositeException e) {
			logger.error("Error while parsing user xml" , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
	}

	// UserModule XML users
	private List<UserType> getUserModuleUsers( String pathToUsersXML) {
		// validate incoming arguments
		if(pathToUsersXML == null || pathToUsersXML.trim().length() == 0){
			throw new ValidationException("Invalid Arguments");
		}

		try {
			//using jaxb convert xml to corresponding java objects
			UserModule userModuleType = (UserModule)XMLUtils.getModuleTypeFromXML(pathToUsersXML);
			if(userModuleType != null && userModuleType.getUser() != null && !userModuleType.getUser().isEmpty()){
				return userModuleType.getUser();
			}
		} catch (CompositeException e) {
			logger.error("Error while parsing user xml" , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
		return null;
	}

	// Populate the CIS User Array List
	private void populateUserArrayList(boolean userArrayListPopulated, ArrayList<String> validUsers, ArrayList<UserDomain> userArray, String domainName, String serverId, String pathToServersXML) throws ApplicationContextException {
		if (!userArrayListPopulated) {
			try {
				UserList cisUserList = getUserDAO().getAllUsers(validUsers, domainName, serverId, pathToServersXML);
				for (User user : cisUserList.getUser()) {
					UserDomain userDomain = new UserDomain();
					userDomain.setUsername(user.getName());
					userDomain.setDomain(user.getDomainName());
					userArray.add(userDomain);
				}
			} catch (CompositeException e) {
				logger.error("Error while retrieving CIS user list" , e);
				throw new ApplicationContextException("Error while retrieving CIS user list"+e.getMessage(), e);
			}
		}
	}

	// UserDomain object is used to hold a user and domain
	public class UserDomain {
	    public String username = null;
	    public String domain = null;
	    //constructor
	    public UserDomain() {
	    	username = null;
	    	domain = null;
	    }
	    public void setUsername(String u) {
	    	username = u;
	    }
	    public void setDomain(String d) {
	    	domain = d;
	    }
	    public String getUsername() {
	    	return this.username;
	    }
	    public String getDomain() {
	    	return this.domain;
	    }
	    public boolean findUserDomain(String u, String d) {
	    	boolean found = false;
	    	if (u != null && d != null) {
	    		if (this.username.equalsIgnoreCase(u) && this.domain.equalsIgnoreCase(d)) {
		    		found = true;
	    		}
	    	}
	    	return found;
	    }
	}

	public UserDAO getUserDAO() {
		if(userDAO == null){
			userDAO = new UserWSDAOImpl();
		}
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
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
