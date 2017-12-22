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
package com.tibco.ps.deploytool.services;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;

import com.tibco.ps.common.CommonConstants;
import com.tibco.ps.common.exception.CompositeException;
import com.tibco.ps.common.exception.ValidationException;
import com.tibco.ps.common.util.CommonUtils;
import com.tibco.ps.common.util.XMLUtils;
import com.tibco.ps.deploytool.DeployManagerUtil;
import com.tibco.ps.deploytool.dao.PrivilegeDAO;
import com.tibco.ps.deploytool.dao.ResourceDAO;
import com.tibco.ps.deploytool.dao.ServerAttributeDAO;
import com.tibco.ps.deploytool.dao.wsapi.PrivilegeWSDAOImpl;
import com.tibco.ps.deploytool.dao.wsapi.ResourceWSDAOImpl;
import com.tibco.ps.deploytool.dao.wsapi.ServerAttributeWSDAOImpl;
import com.tibco.ps.deploytool.util.DeployUtil;
import com.tibco.ps.deploytool.modules.ObjectFactory;
import com.tibco.ps.deploytool.modules.PrivilegeEntryType;
import com.tibco.ps.deploytool.modules.PrivilegeModeValidationList;
import com.tibco.ps.deploytool.modules.PrivilegeModule;
import com.tibco.ps.deploytool.modules.PrivilegeNameTypeValidationList;
import com.tibco.ps.deploytool.modules.PrivilegeType;
import com.tibco.ps.deploytool.modules.PrivilegeValidationList;
import com.tibco.ps.deploytool.modules.ResourceOwnerType;
import com.tibco.ps.deploytool.modules.ResourceTypeSimpleType;
import com.compositesw.services.system.admin.resource.Column;
import com.compositesw.services.system.admin.resource.ColumnList;
import com.compositesw.services.system.admin.resource.GetResourcePrivilegesRequest.Entries;
import com.compositesw.services.system.admin.resource.PathTypeOrColumnPair;
import com.compositesw.services.system.admin.resource.Resource;
import com.compositesw.services.system.admin.resource.ResourceList;
import com.compositesw.services.system.admin.resource.ResourceOrColumnType;
import com.compositesw.services.system.admin.resource.ResourceType;
import com.compositesw.services.system.admin.resource.TableResource;
import com.compositesw.services.system.util.common.DetailLevel;

public class PrivilegeManagerImpl implements PrivilegeManager{

	private static String className = "PrivilegeManagerImpl";
	
	private static Log logger = LogFactory.getLog(PrivilegeManagerImpl.class);
	
	// Get the configuration property file set in the environment with a default of deploy.properties
	String propertyFile = CommonUtils.getFileOrSystemPropertyValue(CommonConstants.propertyFile, "CONFIG_PROPERTY_FILE");

    private PrivilegeDAO privilegeDAO = null;
	private ResourceDAO resourceDAO = null;
	private ResourceManager resourceManager = null;
	private ServerAttributeDAO serverAttributeDAO = null;
	
	// Do Not generate privileges or update privileges for Users or Groups in these lists.
	// An exception gets thrown on update of privileges owned by admin user or group
	private String doNotGenerateUsersList = "admin";
	private String doNotGenerateGroupsList = "admin";
	// List of System Users and Groups
	private String systemUserList = "monitor,anonymous";
	private String systemGroupList = "all";
	
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.PrivilegeManagerImp#updatePrivileges(java.lang.String)
	 */
//	@Override
	public void updatePrivileges(String serverId, String privilegeIds, String pathToPrivilegeXML, String pathToServersXML) throws CompositeException {
		if(logger.isDebugEnabled()){
			logger.debug(" Entering PrivilegeManagerImpl.updateDataSources() with following params "+" serverId: "+serverId+", privilegeIds: "+privilegeIds+", pathToPrivilegeXML: "+pathToPrivilegeXML+", pathToServersXML: "+pathToServersXML);
		}
		privilegeAction(PrivilegeDAO.action.UPDATE.name(), serverId, privilegeIds, pathToPrivilegeXML, pathToServersXML);
	}

	// Perform a privilege action on a resource
	private void privilegeAction(String actionName, String serverId, String privilegeIds, String pathToPrivilegeXML, String pathToServersXML) throws CompositeException {

		String prefix = "privilegeAction";
		String processedIds = null;
			
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToPrivilegeXML)) {
			throw new CompositeException("File ["+pathToPrivilegeXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}
		
		// Extract variables for the privilegeIds
		privilegeIds = CommonUtils.extractVariable(prefix, privilegeIds, propertyFile, true);

		// Set the Module Action Objective
		String s1 = (privilegeIds == null) ? "no_privilegeIds" : "Ids="+privilegeIds;
		System.setProperty("MODULE_ACTION_OBJECTIVE", actionName+" : "+s1);

		String resourcePath = null;
		try {

			// Get the list of privileges from the PrivilegeModule.xml property file
			List<PrivilegeEntryType> privilegeList = getPrvileges(serverId, privilegeIds, pathToPrivilegeXML, pathToServersXML);
			if (privilegeList != null && privilegeList.size() > 0) {

				// Set the default mode is if no mode is provided.
				String mode = "OVERWRITE_APPEND";

				// Loop over the list of privileges and apply their attributes to the target CIS instance.
				for (PrivilegeEntryType privilege : privilegeList) 
				{
					// Get the identifier and convert any $VARIABLES
					String identifier = CommonUtils.extractVariable(prefix, privilege.getId(), propertyFile, true);

					/**
					 * Possible values for privileges 
					 * 1. csv string like priv1,priv2 (we process only resource names which are passed in)
					 * 2. " * " or what ever is configured to indicate all resources (we process all resources in this case)
					 * 3. csv string with '-' or what ever is configured to indicate exclude resources as prefix 
					 * 	  like -priv1,priv2 (we ignore passed in resources and process rest of the in the input xml
					 * 4. wild card - prefix/postfix any label with a "*"
					 */
					 if(DeployUtil.canProcessResource(privilegeIds, identifier))
					 {
						// Add to the list of processed ids
						if (processedIds == null)
							processedIds = "";
						else
							processedIds = processedIds + ",";
						processedIds = processedIds + identifier;
						 
						if(logger.isInfoEnabled()){
							logger.info("processing action "+actionName+" on privilege "+identifier);
						}
						
						// Validate the privilege coming from the PrivilegeModule.xml property file
						validatePrivilege(privilege);
						
						// Construct the Privilege Entries
						PrivilegeModule updPrivilegeModule = new PrivilegeModule();
						PrivilegeEntryType updPrivilegeEntry = new PrivilegeEntryType();
						String resourceType = null;
						
						if (privilege.getResourcePath() != null) {
							// Get the resource path
							resourcePath = CommonUtils.extractVariable(prefix, privilege.getResourcePath(), propertyFile, true);
							updPrivilegeEntry.setResourcePath(resourcePath);							
						
							// Get the resource type
							if (privilege.getResourceType() == null || privilege.getResourceType().toString().length() == 0) {
								// Get the Resource Type for the Resource Path
								resourceType = getResourceManager().getResourceType(serverId, resourcePath, pathToServersXML);
								updPrivilegeEntry.setResourceType(ResourceTypeSimpleType.valueOf(resourceType));
							} else {
								updPrivilegeEntry.setResourceType(ResourceTypeSimpleType.valueOf(privilege.getResourceType().toString()));
							}
						}
						resourceType = updPrivilegeEntry.getResourceType().toString();
								
						// Set the Module Action Objective
						s1 = identifier+"=" + ((resourcePath == null) ? "no_resourcePath" : resourcePath);
						System.setProperty("MODULE_ACTION_OBJECTIVE", actionName+" : "+s1);

						// Set the resource owner and domain. Both must be non-null in order to set the target
						if (privilege.getResourceOwner() != null) {
							if (privilege.getResourceOwner().getResourceOwnerApply() != null) {
								// Get the "resourceOwnerApply" and check for variables and then change to lower.
								String resourceOwnerApply = CommonUtils.extractVariable(prefix, privilege.getResourceOwner().getResourceOwnerApply(), propertyFile, true).toLowerCase();
								// Validate "resourceOwnerApply"
								if (resourceOwnerApply.equalsIgnoreCase("true") || resourceOwnerApply.equalsIgnoreCase("false")) {
									// Continue applying the resource ownership only if "resourceOwnerApply" == true
									if (resourceOwnerApply.equalsIgnoreCase("true")) {
										// Make sure both resourceOwnerName and resourceOwnerDomain are not null before continuing
										if (privilege.getResourceOwner().getResourceOwnerName() != null && privilege.getResourceOwner().getResourceOwnerDomain() == null)
											throw new CompositeException("Resource Owner Domain \"resourceOwnerDomain\" may not be null when Resource Owner \"resourceOwner\" is not null for resourcePath="+resourcePath);
										if (privilege.getResourceOwner().getResourceOwnerName() == null && privilege.getResourceOwner().getResourceOwnerDomain() != null)
											throw new CompositeException("Resource Owner \"resourceOwner\" may not be null when Resource Owner Domain \"resourceOwnerDomain\" is not null for resourcePath="+resourcePath);
										if (privilege.getResourceOwner().getResourceOwnerName() != null && privilege.getResourceOwner().getResourceOwnerDomain() != null) {
											if (resourceType.equalsIgnoreCase("COLUMN")) {
												throw new CompositeException("Resource type of COLUMN is not permitted when setting \"resourceOwner\" and \"resourceOwnerDomain\" for resourcePath="+resourcePath);
											}
											ResourceOwnerType resourceOwner = new ResourceOwnerType();
											
											resourceOwner.setResourceOwnerName(CommonUtils.extractVariable(prefix, privilege.getResourceOwner().getResourceOwnerName(), propertyFile, true));
											resourceOwner.setResourceOwnerDomain(CommonUtils.extractVariable(prefix, privilege.getResourceOwner().getResourceOwnerDomain(), propertyFile, true));
											if (privilege.getResourceOwner().getResourceOwnerRecurse() != null) {
												String resourceOwnerRecurse = CommonUtils.extractVariable(prefix, privilege.getResourceOwner().getResourceOwnerRecurse().toLowerCase(), propertyFile, true).toLowerCase();
												if (resourceOwnerRecurse.equalsIgnoreCase("true") || resourceOwnerRecurse.equalsIgnoreCase("false")) {
													resourceOwner.setResourceOwnerRecurse(resourceOwnerRecurse);
												}
												else {
													throw new CompositeException("Resource Owner Recurse \"resourceOwnerRecurse\" must be either [true or false] for resourcePath="+resourcePath);
												}
											}
											else {
												resourceOwner.setResourceOwnerRecurse("false");
											}
											updPrivilegeEntry.setResourceOwner(resourceOwner);
										}
									}
								}
								else {
									throw new CompositeException("Resource Owner Apply \"resourceOwnerApply\" must either be [true or false] for resourcePath="+resourcePath);								
								}
							}
						}
						// Set the child recursion
						if (privilege.isRecurse() != null)
							updPrivilegeEntry.setRecurse(privilege.isRecurse());
						
						// Set the dependencies recursion
						if (privilege.isUpdateDependenciesRecursively() != null)
							updPrivilegeEntry.setUpdateDependenciesRecursively(privilege.isUpdateDependenciesRecursively());

						// Set the dependents recursion
						if (privilege.isUpdateDependentsRecursively() != null)
							updPrivilegeEntry.setUpdateDependentsRecursively(privilege.isUpdateDependentsRecursively());

						// Set mode
						if (privilege.getMode() != null) {
							mode = CommonUtils.extractVariable(prefix, privilege.getMode().name(), propertyFile, true);
						}
						updPrivilegeEntry.setMode(PrivilegeModeValidationList.valueOf(mode));

						for (PrivilegeType privType : privilege.getPrivilege()) {	
							
							boolean updatePrivilege = false;
							if (privType.getNameType().name().toString().equalsIgnoreCase("USER") 
									&& !doNotGenerateUsersList.contains(privType.getName().toLowerCase())) {
								updatePrivilege = true;
							}
							if (privType.getNameType().name().toString().equalsIgnoreCase("GROUP") 
									&& !doNotGenerateGroupsList.contains(privType.getName().toLowerCase())) {
								updatePrivilege = true;
							}
							if (updatePrivilege) {	
								PrivilegeType updPrivilege = new PrivilegeType();
								String domain = null;
								if (privType.getDomain() != null) {
									domain = CommonUtils.extractVariable(prefix, privType.getDomain(), propertyFile, true);
									updPrivilege.setDomain(domain);
								}
								if (privType.getName() != null) {
									// -- 2011-11-18 - grose - fixing bug - setting name to lower case
									//    will break most LDAP names
									String name = CommonUtils.extractVariable(prefix, privType.getName(), propertyFile, true);
									if (domain != null) {
										if (domain.equals("composite"))
											updPrivilege.setName(name.toLowerCase());	
										else
											updPrivilege.setName(name);
									} else {
										updPrivilege.setName(name);
									}
								}
								if (privType.getNameType() != null) 
								{
									String nameType = CommonUtils.extractVariable(prefix, privType.getNameType().name(), propertyFile, true);
									updPrivilege.setNameType(PrivilegeNameTypeValidationList.valueOf(nameType.toUpperCase()));							
								}
	
								// Set privileges if not null
								/* Note:
								 * Neither CombinedPrivileges nore Inherited Privileges get updated as per info tab of updateResourcePrivileges()
								 * The "combinedPrivs" and "inheritedPrivs" elements on each "privilegeEntry" will be ignored and can be left unset	
								 */
								if (privType.getPrivileges() != null && privType.getPrivileges().size() > 0) {
									updPrivilege.getPrivileges().addAll(privType.getPrivileges());
								}
								// Add privileges to the object list
								updPrivilegeEntry.getPrivilege().add(updPrivilege);
							}
						}
					
						updPrivilegeModule.getResourcePrivilege().add(updPrivilegeEntry);

						/***************************************
						 * 
						 * PrivilegeWSDAOImpl Invocation
						 * 
						 ***************************************/
						// Invoke DAO to take action
						getPrivilegeDAO().takePrivilegeAction(actionName, updPrivilegeModule, serverId, pathToServersXML);					
					}					 
				}
				// Determine if any resourceIds were not processed and report on this
				if (processedIds != null) {
					if(logger.isInfoEnabled()){
						logger.info("Privilege entries processed="+processedIds);
					}
				} else {
					if(logger.isInfoEnabled()){
						String msg = "Warning: No privilege entries were processed for the input list.  privilegeIds="+privilegeIds;
						logger.info(msg);
						System.setProperty("MODULE_ACTION_MESSAGE", msg);
					}		
				}
			} else {
				if(logger.isInfoEnabled()){
					String msg = "Warning: No privilege entries found for Privilege Module XML at path="+pathToPrivilegeXML;
					logger.info(msg);
					System.setProperty("MODULE_ACTION_MESSAGE", msg);
				}					
			}
		} catch (CompositeException e) {
			logger.error("Error performing action="+actionName+" for privileges on resource: "+resourcePath , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
	}

	// Validate a privilege node from the PrivilegeModule.xml property file
	private void validatePrivilege(PrivilegeEntryType privilege) throws ValidationException {
		
		boolean isValid = false;
		String invalidPrivilegeMember = null;
		String prefix = "validatePrivilege";

		// Get the identifier and convert any $VARIABLES
		String identifier = CommonUtils.extractVariable(prefix, privilege.getId(), propertyFile, true);

		// Validate path
		if (privilege.getResourcePath() == null || privilege.getResourcePath().trim().length() == 0) {
			logger.error("Error processing privilege id: " + privilege.getId() + ". Resource Path is empty.");
			throw new ValidationException("Error processing privilege id: " + identifier + ". Resource Path is empty.");
		}

		/* Validate resource type
		 * 
		 * List of valid resource types include:
		 * 	<xs:enumeration value="COLUMN"/>
		 * 	<xs:enumeration value="CONTAINER"/>
		 * 	<xs:enumeration value="DATA_SOURCE"/>
		 * 	<xs:enumeration value="DEFINITION_SET"/>
		 * 	<xs:enumeration value="LINK"/>
		 * 	<xs:enumeration value="PROCEDURE"/>
		 * 	<xs:enumeration value="TABLE"/>
		 * 	<xs:enumeration value="TREE"/>
		 * 	<xs:enumeration value="TRIGGER"/>
		 */
		if (privilege.getResourceType() != null && privilege.getResourceType().toString().length() > 0) {
			isValid = false;
			String resourceType = CommonUtils.extractVariable(prefix, privilege.getResourceType().name(), propertyFile, true);
			for (ResourceTypeSimpleType type : ResourceTypeSimpleType.values()) {
				if (resourceType.equalsIgnoreCase(type.name()) ) {
					isValid = true;
				}
			}
			if (!isValid) {
				logger.error("Error processing privilege id: " + privilege.getId() + ". Resource Type is invalid: " + resourceType);
				throw new ValidationException("Error processing privilege id: " + identifier + ". Resource Type is invalid: " + resourceType);
			}
		}

		
		// Validate recurse
		if (privilege.isRecurse() == null) {
			logger.error("Error processing privilege id: " + privilege.getId() + ". Recurse is empty.");
			throw new ValidationException("Error processing privilege id: " + identifier + ". Recurse is empty.");
		}

		// Validate mode
		if (privilege.getMode() != null) {
			isValid = false;
			String privMode = CommonUtils.extractVariable(prefix, privilege.getMode().name(), propertyFile, true);
			for (PrivilegeModeValidationList mode : PrivilegeModeValidationList.values()) {
				if (privMode.equalsIgnoreCase(mode.name()) ) {
					isValid = true;
				}
			}
			if (!isValid) {
				logger.error("Error processing privilege id: " + privilege.getId() + ". Mode is invalid: " + privMode);
				throw new ValidationException("Error processing privilege id: " + identifier + ". Mode is invalid: " + privMode);
			}
		}

		// Validate the list of privileges
		for (PrivilegeType privType : privilege.getPrivilege()) {					
	
			// Validate domain
			if (privType.getDomain() == null || privType.getDomain().length() == 0) {
				logger.error("Error processing privilege id: " + privilege.getId() + ". Domain is empty.");
				throw new ValidationException("Error processing privilege id: " + identifier + ". Domain is empty.");
			}

			// Validate domain
			if (privType.getName() == null || privType.getName().length() == 0) {
				logger.error("Error processing privilege id: " + privilege.getId() + ". Name is empty.");
				throw new ValidationException("Error processing privilege id: " + identifier + ". Name is empty.");
			}

			// Validate the name type
			if (privType.getNameType() == null) {
				logger.error("Error processing privilege id: " + privilege.getId() + ". Name Type is empty.");
				throw new ValidationException("Error processing privilege id: " + identifier + ". Name Type is empty.");
			} else {
				isValid = false;
				String privNameType = CommonUtils.extractVariable(prefix, privType.getNameType().name(), propertyFile, true);
				for (PrivilegeNameTypeValidationList nameType : PrivilegeNameTypeValidationList.values()) {
					if (privNameType.equalsIgnoreCase(nameType.name())) {
						isValid = true;
					}
				}
				if (!isValid) {
					logger.error("Error processing privilege id: " + privilege.getId() + ". Name Type is invalid: " + privNameType);
					throw new ValidationException("Error processing privilege id: " + identifier + ". Name Type is invalid: " + privNameType);
				}
			}
			
			// Validate the list of privileges against the XML Schema valid privilege list
			if (privType.getPrivileges() == null) {
				logger.error("Error processing privilege id: " + identifier + ". Privileges is empty.");
				throw new ValidationException("Error processing privilege id: " + identifier + ". Privileges is empty.");			
			} else {
				for (PrivilegeValidationList extractedPriv : privType.getPrivileges()) {
					invalidPrivilegeMember = extractedPriv.name();
					isValid = false;
					for (PrivilegeValidationList validatePriv : PrivilegeValidationList.values()) {
						if (extractedPriv.name().equalsIgnoreCase(validatePriv.name())) {
							isValid = true;
							break;
						}
					}
					if (!isValid) {
						logger.error("Error processing privilege id: " + identifier + ". Privilege member is invalid: " + invalidPrivilegeMember);
						throw new ValidationException("Error processing privilege id: " + identifier + ". Privilege member is invalid: " + invalidPrivilegeMember);
					}
				}
			}
			
			// Validate the list of privileges against the XML Schema valid privilege list
			if (privType.getCombinedPrivileges() != null) {
				for (PrivilegeValidationList extractedPriv : privType.getCombinedPrivileges()) {
					invalidPrivilegeMember = extractedPriv.name();
					isValid = false;
					for (PrivilegeValidationList validatePriv : PrivilegeValidationList.values()) {
						if (extractedPriv.name().equalsIgnoreCase(validatePriv.name())) {
							isValid = true;
							break;
						}
					}
					if (!isValid) {
						logger.error("Error processing privilege id: " + identifier + ". Combined Privilege member is invalid: " + invalidPrivilegeMember);
						throw new ValidationException("Error processing privilege id: " + identifier + ". Combined Privilege member is invalid: " + invalidPrivilegeMember);
					}
				}
			}
	
			// Validate the list of privileges against the  XML Schema valid privilege list
			if (privType.getInheritedPrivileges() != null) {
				for (PrivilegeValidationList extractedPriv : privType.getInheritedPrivileges()) {
					invalidPrivilegeMember = extractedPriv.name();
					isValid = false;
					for (PrivilegeValidationList validatePriv : PrivilegeValidationList.values()) {
						if (extractedPriv.name().equalsIgnoreCase(validatePriv.name())) {
							isValid = true;
							break;
						}
					}
					if (!isValid) {
						logger.error("Error processing privilege id: " + identifier + ". Inherited Privilege member is invalid: " + invalidPrivilegeMember);
						throw new ValidationException("Error processing privilege id: " + identifier + ". Inherited Privilege member is invalid: " + invalidPrivilegeMember);
					}
				}
			}
		}
	}
	
	// Retrieve the list of privileges from the PrivilegeModule.xml
	private List<PrivilegeEntryType> getPrvileges(String serverId, String privilegeIds,	String pathToPrivilegeXML, String pathToServersXML) {
		// validate incoming arguments
		if(serverId == null || serverId.trim().length() ==0 || privilegeIds == null || privilegeIds.trim().length() ==0 || pathToServersXML == null || pathToServersXML.trim().length() ==0 || pathToPrivilegeXML == null || pathToPrivilegeXML.trim().length() ==0){
			throw new ValidationException("Invalid Arguments");
		}

		try {
			//using jaxb convert xml to corresponding java objects
			PrivilegeModule privilegeModule = (PrivilegeModule)XMLUtils.getModuleTypeFromXML(pathToPrivilegeXML);
			if(privilegeModule != null && privilegeModule.getResourcePrivilege() != null && !privilegeModule.getResourcePrivilege().isEmpty()){
				return privilegeModule.getResourcePrivilege();
			}
		} catch (CompositeException e) {
			logger.error("Error while parsing PrivilegeModule XML: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see com.tibco.ps.deploytool.services.PrivilegeManagerImpl#generatePrivilegesXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generatePrivilegesXML(String serverId, String startPath, String pathToPrivilegeXML, String pathToServersXML, String filter, String options, String domainList) throws CompositeException {
		
		String prefix = "generatePrivilegesXML";
		
		// Set the command and action name
		String command = "generatePrivilegesXML";
		String actionName = "CREATE_XML";

		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		// Set the Module Action Objective
		String s1 = (startPath == null) ? "no_startPath" : "Path="+startPath;
		System.setProperty("MODULE_ACTION_OBJECTIVE", "GENERATE : "+s1);

		String dependentFilter = null;
		try {
			boolean dependentResource = false;
			// If the filter is null or empty, then assume the user wants ALL resources
			if (filter == null || filter.trim().length() == 0) {
				filter = "";
				for (ResourceType value : ResourceType.values()) {
					filter = filter+value.name()+","; // Create a comma separated list of all valid ResourceTypes				
				}
				filter = filter+"COLUMN";
				dependentFilter = filter;
			} else 
				// If the filter contains ALL then set the filter to all ResourceTypes including COLUMN
				if ( filter.contains("ALL") ) {
				filter = "";
				for (ResourceType value : ResourceType.values()) {
					filter = filter+value.name()+","; // Create a comma separated list of all valid ResourceTypes				
				}
				filter = filter+"COLUMN";
				dependentFilter = filter;			
			} else 	
				// If the filters contains COLUMN make sure the dependent filter includes TABLE because TABLE resources contain the columns
				if ( filter.contains("COLUMN") ) {
					dependentResource = true;
					dependentFilter = filter+",TABLE";
			} else {
				// otherwise just set the dependent filter to the filter
				dependentFilter = filter;
			}

			// Prepare options for parsing
			if (options == null || options.trim().length() == 0) {
				options = "";
			} else {
				options = options.trim();
			}
			// Set Options for USER and GROUP
			boolean generateUser = false;
			boolean generateGroup = false;
			// Set Options for SYSTEM and NONSYSTEM
			boolean generateSystem = false;
			boolean generateNonSystem = false;
			// Set Options for PARENT and CHILD
			boolean generateParent = false;
			boolean generateChild = false;
			// Change space separators to comma separators
			options = options.replaceAll(" ", ",");
			StringTokenizer st = new StringTokenizer(options, ",");
			while (st.hasMoreTokens()) {
				String token = st.nextToken().trim().toUpperCase();
				if (token.equalsIgnoreCase("USER")) {
					generateUser = true;
				}
				if (token.equalsIgnoreCase("GROUP")) {
					generateGroup = true;
				}
				if (token.equalsIgnoreCase("SYSTEM")) {
					generateSystem = true;
				}
				if (token.equalsIgnoreCase("NONSYSTEM")) {
					generateNonSystem = true;
				}
				if (token.equalsIgnoreCase("PARENT")) {
					generateParent = true;
				}
				if (token.equalsIgnoreCase("CHILD")) {
					generateChild = true;
				}

			}
			// If both USER and GROUP is not present then set the default to GROUP
			if (!generateUser && !generateGroup) {
				generateGroup = true;
			}
			// If both SYSTEM and NONSYSTEM is not present then set the default to NONSYSTEM
			if (!generateSystem && !generateNonSystem) {
				generateNonSystem = true;
			}
			// If both PARENT and CHILD is not present then set the default to PARENT
			if (!generateParent && !generateChild) {
				generateParent = true;
			}
			
			// Setup the domainList (default=composite)
			if (domainList == null || domainList.trim().length() == 0) {
				domainList = "composite";
			}
			
			/***************************************
			 * 
			 * ResourceWSDAOImpl Invocation
			 * 
			 ***************************************/
			// Determine what type the current resourcePath is 
			Resource currResourcePath = getResourceDAO().getResource(serverId, startPath, pathToServersXML);		
			
			/***************************************
			 * 
			 * ResourceWSDAOImpl Invocation
			 * 
			 ***************************************/
			// Recursively walk the folder tree and get "ALL" resources by passing in null for resourceType
			ResourceList resourceList = new ResourceList();
			resourceList.getResource().addAll(DeployManagerUtil.getDeployManager().getResourcesFromPath(serverId, startPath, currResourcePath.getType().name(), dependentFilter, DetailLevel.FULL.name(), pathToServersXML).getResource());
			HashMap<String,String> resourceOwnerMap = new HashMap<String,String>();
			
			if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty()) {
			
				String privFilter = null; // only valid values are ALL_EXPLICIT and null
				boolean includeColumnPrivileges = false;
				// Generate a list of Entries from the getResourcesFromPath()
				// This list will be used go get the resource privileges list
				Entries entries = new Entries();

				for (Resource resource : resourceList.getResource()) {

					String resourcePath = resource.getPath();
					String resourceType = resource.getType().name();
					String resourceOwner = null;
					String resourceOwnerDomain = null;
					
					if (resource.getOwnerName() != null)
						resourceOwner = resource.getOwnerName().toString();
					if (resource.getOwnerDomain() != null)
						resourceOwnerDomain = resource.getOwnerDomain().toString();
					
					// If the type of resource returned is a member of the filter list or the filter contains ALL then process that resource
					// Additionally, if the filter contains a COLUMN or TABLE then allow the dependency interrogation to pass through
					if (filter.contains(resourceType) || dependentResource) {

						// Only add the resource to the list if it is contained in the original filter
						if ( filter.contains(resourceType) ) {
								PathTypeOrColumnPair pathPair = new PathTypeOrColumnPair();
								pathPair.setPath(resourcePath);
								pathPair.setType(ResourceOrColumnType.valueOf(resourceType));
								entries.getEntry().add(pathPair);
								
								// Now add the resource owner and owner domain to a hashmap for later retrieval
								if (resourceOwner != null && resourceOwnerDomain != null)
									resourceOwnerMap.put(resourcePath+"::"+resourceType, resourceOwner+"::"+resourceOwnerDomain);
								else
									resourceOwnerMap.put(resourcePath+"::"+resourceType, null);
						}
				
						// If the current resource is of type TABLE and the filter contains COLUMN then process the columns
						if ( resourceType.equalsIgnoreCase("TABLE") && filter.contains("COLUMN") ) {
							TableResource tableResource = (TableResource) resource;
													
							// Loop over the list of columns
							ColumnList columns = tableResource.getColumns();
							for (Column column : columns.getColumn()) {
								// Extract ColumnList from table
								String columnName = column.getName();
								if (columnName.contains("/") || columnName.contains("\\")) {
									columnName = "\"" + columnName + "\"";
								}
							 
								// Add a table resource
								PathTypeOrColumnPair pathPairColumn = new PathTypeOrColumnPair();
								pathPairColumn.setPath(tableResource.getPath()+"/"+columnName);
								pathPairColumn.setType(ResourceOrColumnType.fromValue("COLUMN"));
								entries.getEntry().add(pathPairColumn);
							}
						}
					}
				}
		
				/***************************************
				 * 
				 * PrivilegeWSDAOImpl Invocation
				 * 
				 ***************************************/
				// Retrieve the Resource Privileges
				PrivilegeModule retPrivilegeModule = getPrivilegeDAO().getResourcePrivileges(entries , privFilter, includeColumnPrivileges, serverId, pathToServersXML);
				List<PrivilegeEntryType> privilegeEntries = retPrivilegeModule.getResourcePrivilege();
			
				// Process OUT privilegeModule
				PrivilegeModule privilegeModule = new ObjectFactory().createPrivilegeModule();
				int seq=1;
				// Loop through the list of resource privileges
				for (PrivilegeEntryType retPrivs : privilegeEntries) {
					
					boolean processThisResourcePrivilege = false;
					if (generateParent && retPrivs.getResourcePath().equalsIgnoreCase(startPath)) {
						processThisResourcePrivilege = true;
					}
					if (generateChild && !retPrivs.getResourcePath().equalsIgnoreCase(startPath)) {
						processThisResourcePrivilege = true;
					}
				
					if (processThisResourcePrivilege) {
						
						PrivilegeEntryType resourcePrivilege = new PrivilegeEntryType();
						resourcePrivilege.setId("priv"+seq++);
	
						String resourcePath = retPrivs.getResourcePath();
						
						resourcePrivilege.setResourcePath(resourcePath);
						// Generate resourceType.  Required to do this since COLUMN cannot be looked up by getAllResourcePaths
						//   This is an optional element to maintain compatibility.
						String resourceType = null;
						if (retPrivs.getResourceType() != null) {
							resourceType = retPrivs.getResourceType().toString();
							resourcePrivilege.setResourceType(ResourceTypeSimpleType.valueOf(resourceType));
						}
						
						// Based on the results of the resourceOwner and resourceOwnerDomain, add them into the PrivilegeModule XML response.
						if (resourcePath != null && resourceType != null) {
							String resOwner = resourceOwnerMap.get(resourcePath+"::"+resourceType);
							String resourceOwnerName = null;
							String resourceOwnerDomain = null;
							// Split out the owner and domain from the result
							if (resOwner != null) {
								String[] resArry = resOwner.split("::",2);
								resourceOwnerName = resArry[0];
								resourceOwnerDomain = resArry[1];
							}
							// Add the XML tags for "resourceOwner" and "resourceOwnerDomain"
							if (resourceOwnerName != null && resourceOwnerDomain != null) {
								ResourceOwnerType resourceOwner = new ResourceOwnerType();
								// Set the resource ownership XML section
								resourceOwner.setResourceOwnerApply("true");
								resourceOwner.setResourceOwnerName(resourceOwnerName);
								resourceOwner.setResourceOwnerDomain(resourceOwnerDomain);
								resourceOwner.setResourceOwnerRecurse("false");
								resourcePrivilege.setResourceOwner(resourceOwner);
							}
						}
						
						// Set the recurse XML tag
						resourcePrivilege.setRecurse(true);
						
						//Loop through privileges
						List<PrivilegeType> retPrivList = retPrivs.getPrivilege(); 
						for (PrivilegeType retPriv : retPrivList) 
						{
							PrivilegeType privilege = new PrivilegeType();
	
							// Get the privilege if domain, name, type and privilege are not null
							if (retPriv.getDomain() != null && retPriv.getName() != null && retPriv.getNameType() != null && retPriv.getPrivileges() != null) {
								
								boolean generatePrivilege = false;
								if (retPriv.getNameType().name().toString().equalsIgnoreCase("USER") 
										&& !doNotGenerateUsersList.contains(retPriv.getName().toLowerCase())
										&& generateUser) {
									
									generatePrivilege = true;
								}
								if (retPriv.getNameType().name().toString().equalsIgnoreCase("GROUP") 
										&& !doNotGenerateGroupsList.contains(retPriv.getName().toLowerCase())
										&& generateGroup) {
									
									generatePrivilege = true;
								}
								
								// Generate privilege is qualified by generateUser, generateGroup, doNotGenerateUsersList and  doNotGenerateGroupsList
								if (generatePrivilege) {
									
									boolean generatePrivilegeQualified = false;
									if (generateSystem && generateNonSystem) {
										
										generatePrivilegeQualified = true;
										
									} else if (generateNonSystem) {
										
										if (retPriv.getNameType().name().toString().equalsIgnoreCase("USER") 
												&& !systemUserList.contains(retPriv.getName().toLowerCase())) {
									
											generatePrivilegeQualified = true;
										}
										if (retPriv.getNameType().name().toString().equalsIgnoreCase("GROUP") 
												&& !systemGroupList.contains(retPriv.getName().toLowerCase())) {
									
											generatePrivilegeQualified = true;
										}

									} else if (generateSystem) {
										
										if (retPriv.getNameType().name().toString().equalsIgnoreCase("USER") 
												&& systemUserList.contains(retPriv.getName().toLowerCase())) {
									
											generatePrivilegeQualified = true;
										}
										if (retPriv.getNameType().name().toString().equalsIgnoreCase("GROUP") 
												&& systemGroupList.contains(retPriv.getName().toLowerCase())) {
									
											generatePrivilegeQualified = true;
										}
									}
	
									// Generate  privilege is qualified by generateSystem and generateNonSystem
									if (generatePrivilegeQualified) {
										
										// Finally, check that the domain is in the valid domainList before generating the privilege
										if (domainList.contains(retPriv.getDomain())) {
											
											// Set the Domain
											privilege.setDomain(retPriv.getDomain());	
				
											// Set the Name
											privilege.setName(retPriv.getName());							
				
											// Set the Name Type
											privilege.setNameType(PrivilegeNameTypeValidationList.valueOf(retPriv.getNameType().name()));							
											
											// Set the Privileges
											privilege.getPrivileges().addAll(retPriv.getPrivileges());
													
											// Set the Combined Privileges if it exists
											if (retPriv.getCombinedPrivileges() != null) {
												privilege.getCombinedPrivileges().addAll(retPriv.getCombinedPrivileges());
											}
											
											// Set the Inherited Privileges if it exists
											if (retPriv.getInheritedPrivileges() != null) {
												privilege.getInheritedPrivileges().addAll(retPriv.getInheritedPrivileges());
											}
				
											// Add the privilege node to the XML output
											resourcePrivilege.getPrivilege().add(privilege);
										}
									}
								}
							}
						}
						privilegeModule.getResourcePrivilege().add(resourcePrivilege);
					}
				}

				// Don't execute if -noop (NO_OPERATION) has been set otherwise execute under normal operation.
				if (CommonUtils.isExecOperation()) 
				{					
					XMLUtils.createXMLFromModuleType(privilegeModule, pathToPrivilegeXML);
				} else {
					logger.info("\n\nWARNING - NO_OPERATION: COMMAND ["+command+"], ACTION ["+actionName+"] WAS NOT PERFORMED.\n");						
				}

/*
				// Retrieve the Resource Privileges
				privilegeEntries = getPrivilegeDAO().getResourcePrivileges(entries , privFilter, includeColumnPrivileges, serverId, pathToServersXML);
				
				PrivilegeModule privilegeModule = new ObjectFactory().createPrivilegeModule();
				int seq=1;
				// Loop through the list of resource privileges
				for (PrivilegeEntry privs : privilegeEntries.getPrivilegeEntry()) {
					
					boolean processThisResourcePrivilege = false;
					if (generateParent && privs.getPath().equalsIgnoreCase(startPath)) {
						processThisResourcePrivilege = true;
					}
					if (generateChild && !privs.getPath().equalsIgnoreCase(startPath)) {
						processThisResourcePrivilege = true;
					}
				
					if (processThisResourcePrivilege) {
						
						PrivilegeEntryType resourcePrivilege = new PrivilegeEntryType();
						resourcePrivilege.setId("priv"+seq++);
	
						resourcePrivilege.setResourcePath(privs.getPath());
						// Generate resourceType.  Required to do this since COLUMN cannot be looked up by getAllResourcePaths
						//   This is an optional element to maintain compatibility.
						if (privs.getType() != null) {
							resourcePrivilege.setResourceType(ResourceTypeSimpleType.valueOf(privs.getType().toString()));
						}
						resourcePrivilege.setRecurse(true);
						
						//Loop through privileges
						for (Privilege priv : privs.getPrivileges().getPrivilege()) {
							PrivilegeType privilege = new PrivilegeType();
	
							// Get the privilege if domain, name, type and privilege are not null
							if (priv.getDomain() != null && priv.getName() != null && priv.getNameType() != null && priv.getPrivs() != null) {
								
								boolean generatePrivilege = false;
								if (priv.getNameType().name().toString().equalsIgnoreCase("USER") 
										&& !doNotGenerateUsersList.contains(priv.getName().toLowerCase())
										&& generateUser) {
									
									generatePrivilege = true;
								}
								if (priv.getNameType().name().toString().equalsIgnoreCase("GROUP") 
										&& !doNotGenerateGroupsList.contains(priv.getName().toLowerCase())
										&& generateGroup) {
									
									generatePrivilege = true;
								}
								
								// Generate privilege is qualified by generateUser, generateGroup, doNotGenerateUsersList and  doNotGenerateGroupsList
								if (generatePrivilege) {
									
									boolean generatePrivilegeQualified = false;
									if (generateSystem && generateNonSystem) {
										
										generatePrivilegeQualified = true;
										
									} else if (generateNonSystem) {
										
										if (priv.getNameType().name().toString().equalsIgnoreCase("USER") 
												&& !systemUserList.contains(priv.getName().toLowerCase())) {
									
											generatePrivilegeQualified = true;
										}
										if (priv.getNameType().name().toString().equalsIgnoreCase("GROUP") 
												&& !systemGroupList.contains(priv.getName().toLowerCase())) {
									
											generatePrivilegeQualified = true;
										}

										
									} else if (generateSystem) {
										
										if (priv.getNameType().name().toString().equalsIgnoreCase("USER") 
												&& systemUserList.contains(priv.getName().toLowerCase())) {
									
											generatePrivilegeQualified = true;
										}
										if (priv.getNameType().name().toString().equalsIgnoreCase("GROUP") 
												&& systemGroupList.contains(priv.getName().toLowerCase())) {
									
											generatePrivilegeQualified = true;
										}
										
									}
	
									// Generate  privilege is qualified by generateSystem and generateNonSystem
									if (generatePrivilegeQualified) {
										
										// Finally, check that the domain is in the valid domainList before generating the privilege
										if (domainList.contains(priv.getDomain())) {
											
											// Set the Domain
											privilege.setDomain(priv.getDomain());	
				
											// Set the Name
											privilege.setName(priv.getName());							
				
											// Set the Name Type
											privilege.setNameType(PrivilegeNameTypeValidationList.valueOf(priv.getNameType().name()));							
											
											// Set the Privileges
											// Tokenize a privilege list based on " " separator
											st = new StringTokenizer(priv.getPrivs(), " ");
											while (st.hasMoreTokens()) {
											   	String token = st.nextToken();
												privilege.getPrivileges().add(PrivilegeValidationList.valueOf(token));
											}		
				
											// Set the Combined Privileges if it exists
											if (priv.getCombinedPrivs() != null) {
												// Tokenize a privilege list based on " " separator
												st = new StringTokenizer(priv.getCombinedPrivs(), " ");
											    while (st.hasMoreTokens()) {
											    	String token = st.nextToken();
													privilege.getCombinedPrivileges().add(PrivilegeValidationList.valueOf(token));
											    }		
											}
											
											// Set the Inherited Privileges if it exists
											if (priv.getInheritedPrivs() != null) {
												// Tokenize a privilege list based on " " separator
												st = new StringTokenizer(priv.getInheritedPrivs(), " ");
											    while (st.hasMoreTokens()) {
											    	String token = st.nextToken();
													privilege.getInheritedPrivileges().add(PrivilegeValidationList.valueOf(token));
											    }		
											}
				
											// Add the privilege node to the XML output
											resourcePrivilege.getPrivilege().add(privilege);
										}
									}
								}
							}
							
						}
	
						privilegeModule.getResourcePrivilege().add(resourcePrivilege);
					}
				}
				XMLUtils.createXMLFromModuleType(privilegeModule, pathToPrivilegeXML);				
 */
			}
		} catch (CompositeException e) {
			logger.error("Error generating PrivilegeModule XML: " , e);
			throw new ApplicationContextException(e.getMessage(), e);
		}
	}


	/**
	 * @return the privilegeWSDAO
	 */
	public PrivilegeDAO getPrivilegeDAO() {
		if(privilegeDAO == null){
			privilegeDAO = new PrivilegeWSDAOImpl();
		}
		return privilegeDAO;
	}

	/**
	 * @param privilegeWSDAO the privilegeWSDAO to set
	 */
	public void setPrivilegeDAO(PrivilegeDAO privilegeDAO) {
		this.privilegeDAO = privilegeDAO;
	}
	
	/**
	 * @return the resourceDAO
	 */
	public ResourceDAO getResourceDAO() {
		if(this.resourceDAO == null){
			this.resourceDAO = new ResourceWSDAOImpl();
		}
		return resourceDAO;
	}
	
	
	
	/**
	 * @return the resourceManager
	 */
	public ResourceManager getResourceManager() {
		if(this.resourceManager == null){
			this.resourceManager = new ResourceManagerImpl();
		}
		return resourceManager;
	}
	/**
	 * @param resourceManager the resourceManager to set
	 */
	public void setResourceManager(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}
	
	/**
	 * @return the serverAttributeDAO
	 */
	public ServerAttributeDAO getServerAttributeDAO() {
		if(this.serverAttributeDAO == null){
			this.serverAttributeDAO = new ServerAttributeWSDAOImpl();
		}
		return serverAttributeDAO;
	}

	/**
	 * @param serverAttributeDAO the serverAttributeDAO to set
	 */
	public void setServerAttributeDAO(ServerAttributeDAO serverAttributeDAO) {
		this.serverAttributeDAO = serverAttributeDAO;
	}
	
}
