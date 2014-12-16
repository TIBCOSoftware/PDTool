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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;

import com.cisco.dvbu.ps.common.CommonConstants;
import com.cisco.dvbu.ps.common.exception.CompositeException;
import com.cisco.dvbu.ps.common.exception.ValidationException;
import com.cisco.dvbu.ps.common.util.CommonUtils;
import com.cisco.dvbu.ps.common.util.XMLUtils;
import com.cisco.dvbu.ps.deploytool.DeployManagerUtil;
import com.cisco.dvbu.ps.deploytool.dao.PrivilegeDAO;
import com.cisco.dvbu.ps.deploytool.dao.ResourceDAO;
import com.cisco.dvbu.ps.deploytool.dao.ServerAttributeDAO;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.PrivilegeWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.ResourceWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.dao.wsapi.ServerAttributeWSDAOImpl;
import com.cisco.dvbu.ps.deploytool.util.DeployUtil;
import com.cisco.dvbu.ps.deploytool.modules.ObjectFactory;
import com.cisco.dvbu.ps.deploytool.modules.PrivilegeEntryType;
import com.cisco.dvbu.ps.deploytool.modules.PrivilegeModeValidationList;
import com.cisco.dvbu.ps.deploytool.modules.PrivilegeModule;
import com.cisco.dvbu.ps.deploytool.modules.PrivilegeNameTypeValidationList;
import com.cisco.dvbu.ps.deploytool.modules.PrivilegeType;
import com.cisco.dvbu.ps.deploytool.modules.PrivilegeValidationList;
import com.cisco.dvbu.ps.deploytool.modules.ResourceTypeSimpleType;
import com.compositesw.services.system.admin.resource.Column;
import com.compositesw.services.system.admin.resource.ColumnList;
import com.compositesw.services.system.admin.resource.GetResourcePrivilegesRequest.Entries;
import com.compositesw.services.system.admin.resource.PathTypeOrColumnPair;
import com.compositesw.services.system.admin.resource.Privilege;
import com.compositesw.services.system.admin.resource.PrivilegeEntry;
import com.compositesw.services.system.admin.resource.PrivilegeEntry.Privileges;
import com.compositesw.services.system.admin.resource.Resource;
import com.compositesw.services.system.admin.resource.ResourceList;
import com.compositesw.services.system.admin.resource.ResourceOrColumnType;
import com.compositesw.services.system.admin.resource.ResourceType;
import com.compositesw.services.system.admin.resource.TableResource;
import com.compositesw.services.system.admin.resource.UpdateResourcePrivilegesRequest.PrivilegeEntries;
import com.compositesw.services.system.admin.resource.UserNameType;
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
	 * @see com.cisco.dvbu.ps.deploytool.services.PrivilegeManagerImp#updatePrivileges(java.lang.String)
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
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToPrivilegeXML)) {
			throw new CompositeException("File ["+pathToPrivilegeXML+"] does not exist.");
		}
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}

		String CisVersionString = getServerAttributeDAO().getServerVersion(serverId, pathToServersXML).trim();
		int idx = CisVersionString.indexOf(" ");
		if (idx <= 0)
			idx = CisVersionString.length();
		CisVersionString = CisVersionString.substring(0, idx).trim();
		CisVersionString = CisVersionString.replaceAll(Pattern.quote("."), Matcher.quoteReplacement(""));
		Integer CisVersion = Integer.valueOf(CisVersionString);
		
		// Extract variables for the privilegeIds
		privilegeIds = CommonUtils.extractVariable(prefix, privilegeIds, propertyFile, true);

		String resourcePath = null;
		try {

			// Get the list of privileges from the PrivilegeModule.xml property file
			List<PrivilegeEntryType> privilegeList = getPrvileges(serverId, privilegeIds, pathToPrivilegeXML, pathToServersXML);
			if (privilegeList != null && privilegeList.size() > 0) {

				// Loop over the list of privileges and apply their attributes to the target CIS instance.
				String mode = "OVERWRITE_APPEND";
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
					 if(DeployUtil.canProcessResource(privilegeIds, identifier)){
						 
						if(logger.isInfoEnabled()){
							logger.info("processing action "+actionName+" on privilege "+identifier);
						}
						
						// Validate the privilege coming from the PrivilegeModule.xml property file
						validatePrivilege(privilege);
						
						// Construct the Privilege Entries
						PrivilegeEntries privilegeEntries = new PrivilegeEntries();
						PrivilegeEntry pe = new PrivilegeEntry();
						if (privilege.getResourcePath() != null) {
							// Get the resource path
							resourcePath = CommonUtils.extractVariable(prefix, privilege.getResourcePath(), propertyFile, true);
							pe.setPath(resourcePath);							
						
							// Get the resource type
							if (privilege.getResourceType() == null || privilege.getResourceType().toString().length() == 0) {
								// Get the Resource Type for the Resource Path
								String resourceType = getResourceManager().getResourceType(serverId, resourcePath, pathToServersXML);
								pe.setType(ResourceOrColumnType.valueOf(resourceType));
							} else {
								pe.setType(ResourceOrColumnType.valueOf(privilege.getResourceType().toString()));
							}
						}
						
						// Set the child recursion
						boolean recurse = true;
						if (privilege.isRecurse() != null)
							recurse = privilege.isRecurse();
						
						// Set the dependencies recursion
						boolean updateDependenciesRecursively = false;
						if (privilege.isUpdateDependenciesRecursively() != null)
							updateDependenciesRecursively = privilege.isUpdateDependenciesRecursively();

						// Set the dependents recursion
						boolean updateDependentsRecursively = false;
/* 
 * Need to wait on the implement of this until it is known if 
 * both 6.2.0 and 6.2.3 CisAdminApi schemas can be supported at the same time.
 * 
						if (privilege.isUpdateDependentsRecursively() != null)
							updateDependentsRecursively = privilege.isUpdateDependentsRecursively();
*/
						// Set mode
						if (privilege.getMode() != null) {
							mode = CommonUtils.extractVariable(prefix, privilege.getMode().name(), propertyFile, true);
						}

						Privileges privs = new Privileges();
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
								Privilege priv = new Privilege();
								String domain = null;
								if (privType.getDomain() != null) {
									domain = CommonUtils.extractVariable(prefix, privType.getDomain(), propertyFile, true);
									priv.setDomain(domain);
								}
								if (privType.getName() != null) {
									// -- 2011-11-18 - grose - fixing bug - setting name to lower case
									//    will break most LDAP names
									String name = CommonUtils.extractVariable(prefix, privType.getName(), propertyFile, true);
									if (domain != null) {
										if (domain.equals("composite"))
											priv.setName(name.toLowerCase());	
										else
											priv.setName(name);
									} else {
										priv.setName(name);
									}

								}
								if (privType.getNameType() != null) 
								{
									String nameType = CommonUtils.extractVariable(prefix, privType.getNameType().name(), propertyFile, true);
									priv.setNameType(UserNameType.valueOf(nameType.toUpperCase()));							
								}
	
								// Set privileges if not null
								if (privType.getPrivileges() != null && privType.getPrivileges().size() > 0) {
									// Set the privileges as a space separate list
									String pList = "";
									for (PrivilegeValidationList pt : privType.getPrivileges()) {
										pList = pList.concat(pt.name()+" ");
									}
									pList.substring(0, pList.length()-1);
									priv.setPrivs(pList);
								}
								
	/* Note:
	 * Neither CombinedPrivileges nore Inherited Privileges get updated as per info tab of updateResourcePrivileges()
	 * The "combinedPrivs" and "inheritedPrivs" elements on each "privilegeEntry" will be ignored and can be left unset	
	 * 
								// Set Combined privileges if not null
								if (privType.getCombinedPrivileges() != null && privType.getCombinedPrivileges().size() > 0) {
									// Set the Combined privileges as a space separate list
									String pList = "";
									for (PrivilegeValidationList pt : privType.getCombinedPrivileges()) {
										pList = pList.concat(pt.name()+" ");
									}
									pList.substring(0, pList.length()-1);
									priv.setCombinedPrivs(pList);
								}
								
								// Set Inherited privileges if not null
								if (privType.getInheritedPrivileges() != null && privType.getInheritedPrivileges().size() > 0) {
									// Set the Inherited privileges as a space separate list
									String pList = "";
									for (PrivilegeValidationList pt : privType.getInheritedPrivileges()) {
										pList = pList.concat(pt.name()+" ");
									}
									pList.substring(0, pList.length()-1);
									priv.setInheritedPrivs(pList);								
								}
	*/							
								// Add privileges to the object list
								privs.getPrivilege().add(priv);
							}
						}
						pe.setPrivileges(privs);
						
						privilegeEntries.getPrivilegeEntry().add(pe);
						// Invoke DAO to take action
						getPrivilegeDAO().takePrivilegeAction(actionName, recurse, updateDependenciesRecursively, updateDependentsRecursively, mode, privilegeEntries, serverId, pathToServersXML, CisVersion);					
					}
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
	 * @see com.cisco.dvbu.ps.deploytool.services.PrivilegeManagerImpl#generatePrivilegesXML(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
	public void generatePrivilegesXML(String serverId, String startPath, String pathToPrivilegeXML, String pathToServersXML, String filter, String options, String domainList) throws CompositeException {
		
		String prefix = "generatePrivilegesXML";
		
		// Validate whether the files exist or not
		if (!CommonUtils.fileExists(pathToServersXML)) {
			throw new CompositeException("File ["+pathToServersXML+"] does not exist.");
		}
		
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
			
			// Determine what type the current resourcePath is 
			Resource currResourcePath = getResourceDAO().getResource(serverId, startPath, pathToServersXML);
			
			// Recursively walk the folder tree and get "ALL" resources by passing in null for resourceType
			ResourceList resourceList = new ResourceList();
			resourceList.getResource().addAll(DeployManagerUtil.getDeployManager().getResourcesFromPath(serverId, startPath, currResourcePath.getType().name(), dependentFilter, DetailLevel.FULL.name(), pathToServersXML).getResource());

			if(resourceList != null && resourceList.getResource() != null && !resourceList.getResource().isEmpty()) {
			
				String privFilter = null; // only valid values are ALL_EXPLICIT and null
				boolean includeColumnPrivileges = false;
				// Generate a list of Entries from the getResourcesFromPath()
				// This list will be used go get the resource privileges list
				Entries entries = new Entries();

				for (Resource resource : resourceList.getResource()) {
					
					// If the type of resource returned is a member of the filter list or the filter contains ALL then process that resource
					// Additionally, if the filter contains a COLUMN or TABLE then allow the dependency interrogation to pass through
					if (filter.contains(resource.getType().name()) || dependentResource) {

						// Only add the resource to the list if it is contained in the original filter
						if ( filter.contains(resource.getType().name()) ) {
								PathTypeOrColumnPair pathPair = new PathTypeOrColumnPair();
								pathPair.setPath(resource.getPath());
								pathPair.setType(ResourceOrColumnType.valueOf(resource.getType().name()));
								entries.getEntry().add(pathPair);
						}
				
						// If the current resource is of type TABLE and the filter contains COLUMN then process the columns
						if ( resource.getType().name().equalsIgnoreCase("TABLE") && filter.contains("COLUMN") ) {
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
		

				com.compositesw.services.system.admin.resource.GetResourcePrivilegesResponse.PrivilegeEntries privilegeEntries = null;
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
