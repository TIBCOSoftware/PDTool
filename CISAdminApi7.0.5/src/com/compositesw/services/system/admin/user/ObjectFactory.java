
package com.compositesw.services.system.admin.user;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.compositesw.services.system.admin.user package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetUsersByGroupResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getUsersByGroupResponse");
    private final static QName _RemoveUsersFromGroupResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "removeUsersFromGroupResponse");
    private final static QName _GetGroupsByUserResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getGroupsByUserResponse");
    private final static QName _RemoveUsersFromGroup_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "removeUsersFromGroup");
    private final static QName _GetDomains_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getDomains");
    private final static QName _UpdateUserResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "updateUserResponse");
    private final static QName _DestroyGroupResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "destroyGroupResponse");
    private final static QName _UpdateDomain_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "updateDomain");
    private final static QName _CancelCreateDomain_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "cancelCreateDomain");
    private final static QName _CreateUserResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "createUserResponse");
    private final static QName _GetUsersByGroup_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getUsersByGroup");
    private final static QName _GetUsers_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getUsers");
    private final static QName _GetGroupsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getGroupsResponse");
    private final static QName _CreateDomain_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "createDomain");
    private final static QName _GetUserResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getUserResponse");
    private final static QName _AddUserToGroupsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "addUserToGroupsResponse");
    private final static QName _AddUsersToGroup_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "addUsersToGroup");
    private final static QName _UpdateUserLockState_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "updateUserLockState");
    private final static QName _UpdateUserLockStateResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "updateUserLockStateResponse");
    private final static QName _GetDomainUsers_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getDomainUsers");
    private final static QName _GetGroupsByUser_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getGroupsByUser");
    private final static QName _GetDomainTypeAttributeDefsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getDomainTypeAttributeDefsResponse");
    private final static QName _GetDomainGroupsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getDomainGroupsResponse");
    private final static QName _GetDomainTypeAttributeDefs_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getDomainTypeAttributeDefs");
    private final static QName _DestroyDomain_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "destroyDomain");
    private final static QName _RemoveUserFromGroups_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "removeUserFromGroups");
    private final static QName _GetDomainTypesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getDomainTypesResponse");
    private final static QName _UpdateUser_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "updateUser");
    private final static QName _CreateDomainResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "createDomainResponse");
    private final static QName _GetDomainsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getDomainsResponse");
    private final static QName _DestroyPrincipalsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "destroyPrincipalsResponse");
    private final static QName _GetDomainGroups_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getDomainGroups");
    private final static QName _GetDomainUsersResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getDomainUsersResponse");
    private final static QName _CreateGroupResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "createGroupResponse");
    private final static QName _DestroyGroup_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "destroyGroup");
    private final static QName _UpdateDomainResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "updateDomainResponse");
    private final static QName _DestroyUser_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "destroyUser");
    private final static QName _AddUserToGroups_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "addUserToGroups");
    private final static QName _CreateUser_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "createUser");
    private final static QName _UpdateGroupResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "updateGroupResponse");
    private final static QName _DestroyDomainResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "destroyDomainResponse");
    private final static QName _UpdateGroup_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "updateGroup");
    private final static QName _DestroyUserResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "destroyUserResponse");
    private final static QName _CancelCreateDomainResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "cancelCreateDomainResponse");
    private final static QName _GetDomainTypes_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getDomainTypes");
    private final static QName _CreateGroup_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "createGroup");
    private final static QName _AddUsersToGroupResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "addUsersToGroupResponse");
    private final static QName _GetGroups_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getGroups");
    private final static QName _DestroyPrincipals_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "destroyPrincipals");
    private final static QName _GetUsersResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getUsersResponse");
    private final static QName _RemoveUserFromGroupsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "removeUserFromGroupsResponse");
    private final static QName _GetUser_QNAME = new QName("http://www.compositesw.com/services/system/admin/user", "getUser");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.compositesw.services.system.admin.user
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DestroyDomainResponse }
     * 
     */
    public DestroyDomainResponse createDestroyDomainResponse() {
        return new DestroyDomainResponse();
    }

    /**
     * Create an instance of {@link DestroyUserResponse }
     * 
     */
    public DestroyUserResponse createDestroyUserResponse() {
        return new DestroyUserResponse();
    }

    /**
     * Create an instance of {@link GetDomainUsersResponse }
     * 
     */
    public GetDomainUsersResponse createGetDomainUsersResponse() {
        return new GetDomainUsersResponse();
    }

    /**
     * Create an instance of {@link UpdateUserLockStateRequest }
     * 
     */
    public UpdateUserLockStateRequest createUpdateUserLockStateRequest() {
        return new UpdateUserLockStateRequest();
    }

    /**
     * Create an instance of {@link DomainTypeList }
     * 
     */
    public DomainTypeList createDomainTypeList() {
        return new DomainTypeList();
    }

    /**
     * Create an instance of {@link GetDomainTypeAttributeDefsRequest }
     * 
     */
    public GetDomainTypeAttributeDefsRequest createGetDomainTypeAttributeDefsRequest() {
        return new GetDomainTypeAttributeDefsRequest();
    }

    /**
     * Create an instance of {@link CreateUserRequest }
     * 
     */
    public CreateUserRequest createCreateUserRequest() {
        return new CreateUserRequest();
    }

    /**
     * Create an instance of {@link DomainMultiNameRequest }
     * 
     */
    public DomainMultiNameRequest createDomainMultiNameRequest() {
        return new DomainMultiNameRequest();
    }

    /**
     * Create an instance of {@link BaseUserResponse }
     * 
     */
    public BaseUserResponse createBaseUserResponse() {
        return new BaseUserResponse();
    }

    /**
     * Create an instance of {@link RemoveUsersFromGroupRequest }
     * 
     */
    public RemoveUsersFromGroupRequest createRemoveUsersFromGroupRequest() {
        return new RemoveUsersFromGroupRequest();
    }

    /**
     * Create an instance of {@link BaseGroupsResponse }
     * 
     */
    public BaseGroupsResponse createBaseGroupsResponse() {
        return new BaseGroupsResponse();
    }

    /**
     * Create an instance of {@link CreateUserResponse }
     * 
     */
    public CreateUserResponse createCreateUserResponse() {
        return new CreateUserResponse();
    }

    /**
     * Create an instance of {@link UpdateDomainRequest }
     * 
     */
    public UpdateDomainRequest createUpdateDomainRequest() {
        return new UpdateDomainRequest();
    }

    /**
     * Create an instance of {@link GetUsersByGroupResponse }
     * 
     */
    public GetUsersByGroupResponse createGetUsersByGroupResponse() {
        return new GetUsersByGroupResponse();
    }

    /**
     * Create an instance of {@link GetDomainTypesResponse }
     * 
     */
    public GetDomainTypesResponse createGetDomainTypesResponse() {
        return new GetDomainTypesResponse();
    }

    /**
     * Create an instance of {@link DestroyPrincipalsResponse }
     * 
     */
    public DestroyPrincipalsResponse createDestroyPrincipalsResponse() {
        return new DestroyPrincipalsResponse();
    }

    /**
     * Create an instance of {@link CreateDomainRequest }
     * 
     */
    public CreateDomainRequest createCreateDomainRequest() {
        return new CreateDomainRequest();
    }

    /**
     * Create an instance of {@link DomainList }
     * 
     */
    public DomainList createDomainList() {
        return new DomainList();
    }

    /**
     * Create an instance of {@link GetUserResponse }
     * 
     */
    public GetUserResponse createGetUserResponse() {
        return new GetUserResponse();
    }

    /**
     * Create an instance of {@link GetDomainGroupsResponse }
     * 
     */
    public GetDomainGroupsResponse createGetDomainGroupsResponse() {
        return new GetDomainGroupsResponse();
    }

    /**
     * Create an instance of {@link DestroyDomainRequest }
     * 
     */
    public DestroyDomainRequest createDestroyDomainRequest() {
        return new DestroyDomainRequest();
    }

    /**
     * Create an instance of {@link RemoveUserFromGroupsResponse }
     * 
     */
    public RemoveUserFromGroupsResponse createRemoveUserFromGroupsResponse() {
        return new RemoveUserFromGroupsResponse();
    }

    /**
     * Create an instance of {@link CreateGroupRequest }
     * 
     */
    public CreateGroupRequest createCreateGroupRequest() {
        return new CreateGroupRequest();
    }

    /**
     * Create an instance of {@link DestroyGroupResponse }
     * 
     */
    public DestroyGroupResponse createDestroyGroupResponse() {
        return new DestroyGroupResponse();
    }

    /**
     * Create an instance of {@link DomainGroupRequest }
     * 
     */
    public DomainGroupRequest createDomainGroupRequest() {
        return new DomainGroupRequest();
    }

    /**
     * Create an instance of {@link CreateDomainResponse }
     * 
     */
    public CreateDomainResponse createCreateDomainResponse() {
        return new CreateDomainResponse();
    }

    /**
     * Create an instance of {@link RemoveUserFromGroupsRequest }
     * 
     */
    public RemoveUserFromGroupsRequest createRemoveUserFromGroupsRequest() {
        return new RemoveUserFromGroupsRequest();
    }

    /**
     * Create an instance of {@link UpdateUserResponse }
     * 
     */
    public UpdateUserResponse createUpdateUserResponse() {
        return new UpdateUserResponse();
    }

    /**
     * Create an instance of {@link GetDomainsRequest }
     * 
     */
    public GetDomainsRequest createGetDomainsRequest() {
        return new GetDomainsRequest();
    }

    /**
     * Create an instance of {@link GetUserRequest }
     * 
     */
    public GetUserRequest createGetUserRequest() {
        return new GetUserRequest();
    }

    /**
     * Create an instance of {@link AddUsersToGroupRequest }
     * 
     */
    public AddUsersToGroupRequest createAddUsersToGroupRequest() {
        return new AddUsersToGroupRequest();
    }

    /**
     * Create an instance of {@link GetDomainsResponse }
     * 
     */
    public GetDomainsResponse createGetDomainsResponse() {
        return new GetDomainsResponse();
    }

    /**
     * Create an instance of {@link UpdateUserRequest }
     * 
     */
    public UpdateUserRequest createUpdateUserRequest() {
        return new UpdateUserRequest();
    }

    /**
     * Create an instance of {@link GetDomainTypesRequest }
     * 
     */
    public GetDomainTypesRequest createGetDomainTypesRequest() {
        return new GetDomainTypesRequest();
    }

    /**
     * Create an instance of {@link Group }
     * 
     */
    public Group createGroup() {
        return new Group();
    }

    /**
     * Create an instance of {@link AddUserToGroupsResponse }
     * 
     */
    public AddUserToGroupsResponse createAddUserToGroupsResponse() {
        return new AddUserToGroupsResponse();
    }

    /**
     * Create an instance of {@link GetDomainTypeAttributeDefsResponse }
     * 
     */
    public GetDomainTypeAttributeDefsResponse createGetDomainTypeAttributeDefsResponse() {
        return new GetDomainTypeAttributeDefsResponse();
    }

    /**
     * Create an instance of {@link AddUserToGroupsRequest }
     * 
     */
    public AddUserToGroupsRequest createAddUserToGroupsRequest() {
        return new AddUserToGroupsRequest();
    }

    /**
     * Create an instance of {@link GetGroupsByUserResponse }
     * 
     */
    public GetGroupsByUserResponse createGetGroupsByUserResponse() {
        return new GetGroupsByUserResponse();
    }

    /**
     * Create an instance of {@link DomainMemberReference }
     * 
     */
    public DomainMemberReference createDomainMemberReference() {
        return new DomainMemberReference();
    }

    /**
     * Create an instance of {@link DomainMemberReferenceList }
     * 
     */
    public DomainMemberReferenceList createDomainMemberReferenceList() {
        return new DomainMemberReferenceList();
    }

    /**
     * Create an instance of {@link UpdateUserLockStateResponse }
     * 
     */
    public UpdateUserLockStateResponse createUpdateUserLockStateResponse() {
        return new UpdateUserLockStateResponse();
    }

    /**
     * Create an instance of {@link RemoveUsersFromGroupResponse }
     * 
     */
    public RemoveUsersFromGroupResponse createRemoveUsersFromGroupResponse() {
        return new RemoveUsersFromGroupResponse();
    }

    /**
     * Create an instance of {@link DomainDetailRequest }
     * 
     */
    public DomainDetailRequest createDomainDetailRequest() {
        return new DomainDetailRequest();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link DomainUserRequest }
     * 
     */
    public DomainUserRequest createDomainUserRequest() {
        return new DomainUserRequest();
    }

    /**
     * Create an instance of {@link GetUsersByGroupRequest }
     * 
     */
    public GetUsersByGroupRequest createGetUsersByGroupRequest() {
        return new GetUsersByGroupRequest();
    }

    /**
     * Create an instance of {@link GetUsersResponse }
     * 
     */
    public GetUsersResponse createGetUsersResponse() {
        return new GetUsersResponse();
    }

    /**
     * Create an instance of {@link UserList }
     * 
     */
    public UserList createUserList() {
        return new UserList();
    }

    /**
     * Create an instance of {@link GetDomainUsersRequest }
     * 
     */
    public GetDomainUsersRequest createGetDomainUsersRequest() {
        return new GetDomainUsersRequest();
    }

    /**
     * Create an instance of {@link GetUsersRequest }
     * 
     */
    public GetUsersRequest createGetUsersRequest() {
        return new GetUsersRequest();
    }

    /**
     * Create an instance of {@link GetGroupsRequest }
     * 
     */
    public GetGroupsRequest createGetGroupsRequest() {
        return new GetGroupsRequest();
    }

    /**
     * Create an instance of {@link Domain }
     * 
     */
    public Domain createDomain() {
        return new Domain();
    }

    /**
     * Create an instance of {@link GetGroupsResponse }
     * 
     */
    public GetGroupsResponse createGetGroupsResponse() {
        return new GetGroupsResponse();
    }

    /**
     * Create an instance of {@link DomainType }
     * 
     */
    public DomainType createDomainType() {
        return new DomainType();
    }

    /**
     * Create an instance of {@link CreateGroupResponse }
     * 
     */
    public CreateGroupResponse createCreateGroupResponse() {
        return new CreateGroupResponse();
    }

    /**
     * Create an instance of {@link UpdateGroupResponse }
     * 
     */
    public UpdateGroupResponse createUpdateGroupResponse() {
        return new UpdateGroupResponse();
    }

    /**
     * Create an instance of {@link AddUsersToGroupResponse }
     * 
     */
    public AddUsersToGroupResponse createAddUsersToGroupResponse() {
        return new AddUsersToGroupResponse();
    }

    /**
     * Create an instance of {@link CancelCreateDomainResponse }
     * 
     */
    public CancelCreateDomainResponse createCancelCreateDomainResponse() {
        return new CancelCreateDomainResponse();
    }

    /**
     * Create an instance of {@link UpdateGroupRequest }
     * 
     */
    public UpdateGroupRequest createUpdateGroupRequest() {
        return new UpdateGroupRequest();
    }

    /**
     * Create an instance of {@link GetGroupsByUserRequest }
     * 
     */
    public GetGroupsByUserRequest createGetGroupsByUserRequest() {
        return new GetGroupsByUserRequest();
    }

    /**
     * Create an instance of {@link UpdateDomainResponse }
     * 
     */
    public UpdateDomainResponse createUpdateDomainResponse() {
        return new UpdateDomainResponse();
    }

    /**
     * Create an instance of {@link DestroyGroupRequest }
     * 
     */
    public DestroyGroupRequest createDestroyGroupRequest() {
        return new DestroyGroupRequest();
    }

    /**
     * Create an instance of {@link GroupList }
     * 
     */
    public GroupList createGroupList() {
        return new GroupList();
    }

    /**
     * Create an instance of {@link DestroyUserRequest }
     * 
     */
    public DestroyUserRequest createDestroyUserRequest() {
        return new DestroyUserRequest();
    }

    /**
     * Create an instance of {@link DomainRequest }
     * 
     */
    public DomainRequest createDomainRequest() {
        return new DomainRequest();
    }

    /**
     * Create an instance of {@link CancelCreateDomainRequest }
     * 
     */
    public CancelCreateDomainRequest createCancelCreateDomainRequest() {
        return new CancelCreateDomainRequest();
    }

    /**
     * Create an instance of {@link DestroyPrincipalsRequest }
     * 
     */
    public DestroyPrincipalsRequest createDestroyPrincipalsRequest() {
        return new DestroyPrincipalsRequest();
    }

    /**
     * Create an instance of {@link GetDomainGroupsRequest }
     * 
     */
    public GetDomainGroupsRequest createGetDomainGroupsRequest() {
        return new GetDomainGroupsRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersByGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getUsersByGroupResponse")
    public JAXBElement<GetUsersByGroupResponse> createGetUsersByGroupResponse(GetUsersByGroupResponse value) {
        return new JAXBElement<GetUsersByGroupResponse>(_GetUsersByGroupResponse_QNAME, GetUsersByGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveUsersFromGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "removeUsersFromGroupResponse")
    public JAXBElement<RemoveUsersFromGroupResponse> createRemoveUsersFromGroupResponse(RemoveUsersFromGroupResponse value) {
        return new JAXBElement<RemoveUsersFromGroupResponse>(_RemoveUsersFromGroupResponse_QNAME, RemoveUsersFromGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGroupsByUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getGroupsByUserResponse")
    public JAXBElement<GetGroupsByUserResponse> createGetGroupsByUserResponse(GetGroupsByUserResponse value) {
        return new JAXBElement<GetGroupsByUserResponse>(_GetGroupsByUserResponse_QNAME, GetGroupsByUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveUsersFromGroupRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "removeUsersFromGroup")
    public JAXBElement<RemoveUsersFromGroupRequest> createRemoveUsersFromGroup(RemoveUsersFromGroupRequest value) {
        return new JAXBElement<RemoveUsersFromGroupRequest>(_RemoveUsersFromGroup_QNAME, RemoveUsersFromGroupRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDomainsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getDomains")
    public JAXBElement<GetDomainsRequest> createGetDomains(GetDomainsRequest value) {
        return new JAXBElement<GetDomainsRequest>(_GetDomains_QNAME, GetDomainsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "updateUserResponse")
    public JAXBElement<UpdateUserResponse> createUpdateUserResponse(UpdateUserResponse value) {
        return new JAXBElement<UpdateUserResponse>(_UpdateUserResponse_QNAME, UpdateUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "destroyGroupResponse")
    public JAXBElement<DestroyGroupResponse> createDestroyGroupResponse(DestroyGroupResponse value) {
        return new JAXBElement<DestroyGroupResponse>(_DestroyGroupResponse_QNAME, DestroyGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDomainRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "updateDomain")
    public JAXBElement<UpdateDomainRequest> createUpdateDomain(UpdateDomainRequest value) {
        return new JAXBElement<UpdateDomainRequest>(_UpdateDomain_QNAME, UpdateDomainRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelCreateDomainRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "cancelCreateDomain")
    public JAXBElement<CancelCreateDomainRequest> createCancelCreateDomain(CancelCreateDomainRequest value) {
        return new JAXBElement<CancelCreateDomainRequest>(_CancelCreateDomain_QNAME, CancelCreateDomainRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "createUserResponse")
    public JAXBElement<CreateUserResponse> createCreateUserResponse(CreateUserResponse value) {
        return new JAXBElement<CreateUserResponse>(_CreateUserResponse_QNAME, CreateUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersByGroupRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getUsersByGroup")
    public JAXBElement<GetUsersByGroupRequest> createGetUsersByGroup(GetUsersByGroupRequest value) {
        return new JAXBElement<GetUsersByGroupRequest>(_GetUsersByGroup_QNAME, GetUsersByGroupRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getUsers")
    public JAXBElement<GetUsersRequest> createGetUsers(GetUsersRequest value) {
        return new JAXBElement<GetUsersRequest>(_GetUsers_QNAME, GetUsersRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGroupsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getGroupsResponse")
    public JAXBElement<GetGroupsResponse> createGetGroupsResponse(GetGroupsResponse value) {
        return new JAXBElement<GetGroupsResponse>(_GetGroupsResponse_QNAME, GetGroupsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateDomainRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "createDomain")
    public JAXBElement<CreateDomainRequest> createCreateDomain(CreateDomainRequest value) {
        return new JAXBElement<CreateDomainRequest>(_CreateDomain_QNAME, CreateDomainRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getUserResponse")
    public JAXBElement<GetUserResponse> createGetUserResponse(GetUserResponse value) {
        return new JAXBElement<GetUserResponse>(_GetUserResponse_QNAME, GetUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddUserToGroupsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "addUserToGroupsResponse")
    public JAXBElement<AddUserToGroupsResponse> createAddUserToGroupsResponse(AddUserToGroupsResponse value) {
        return new JAXBElement<AddUserToGroupsResponse>(_AddUserToGroupsResponse_QNAME, AddUserToGroupsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddUsersToGroupRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "addUsersToGroup")
    public JAXBElement<AddUsersToGroupRequest> createAddUsersToGroup(AddUsersToGroupRequest value) {
        return new JAXBElement<AddUsersToGroupRequest>(_AddUsersToGroup_QNAME, AddUsersToGroupRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserLockStateRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "updateUserLockState")
    public JAXBElement<UpdateUserLockStateRequest> createUpdateUserLockState(UpdateUserLockStateRequest value) {
        return new JAXBElement<UpdateUserLockStateRequest>(_UpdateUserLockState_QNAME, UpdateUserLockStateRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserLockStateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "updateUserLockStateResponse")
    public JAXBElement<UpdateUserLockStateResponse> createUpdateUserLockStateResponse(UpdateUserLockStateResponse value) {
        return new JAXBElement<UpdateUserLockStateResponse>(_UpdateUserLockStateResponse_QNAME, UpdateUserLockStateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDomainUsersRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getDomainUsers")
    public JAXBElement<GetDomainUsersRequest> createGetDomainUsers(GetDomainUsersRequest value) {
        return new JAXBElement<GetDomainUsersRequest>(_GetDomainUsers_QNAME, GetDomainUsersRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGroupsByUserRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getGroupsByUser")
    public JAXBElement<GetGroupsByUserRequest> createGetGroupsByUser(GetGroupsByUserRequest value) {
        return new JAXBElement<GetGroupsByUserRequest>(_GetGroupsByUser_QNAME, GetGroupsByUserRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDomainTypeAttributeDefsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getDomainTypeAttributeDefsResponse")
    public JAXBElement<GetDomainTypeAttributeDefsResponse> createGetDomainTypeAttributeDefsResponse(GetDomainTypeAttributeDefsResponse value) {
        return new JAXBElement<GetDomainTypeAttributeDefsResponse>(_GetDomainTypeAttributeDefsResponse_QNAME, GetDomainTypeAttributeDefsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDomainGroupsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getDomainGroupsResponse")
    public JAXBElement<GetDomainGroupsResponse> createGetDomainGroupsResponse(GetDomainGroupsResponse value) {
        return new JAXBElement<GetDomainGroupsResponse>(_GetDomainGroupsResponse_QNAME, GetDomainGroupsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDomainTypeAttributeDefsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getDomainTypeAttributeDefs")
    public JAXBElement<GetDomainTypeAttributeDefsRequest> createGetDomainTypeAttributeDefs(GetDomainTypeAttributeDefsRequest value) {
        return new JAXBElement<GetDomainTypeAttributeDefsRequest>(_GetDomainTypeAttributeDefs_QNAME, GetDomainTypeAttributeDefsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyDomainRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "destroyDomain")
    public JAXBElement<DestroyDomainRequest> createDestroyDomain(DestroyDomainRequest value) {
        return new JAXBElement<DestroyDomainRequest>(_DestroyDomain_QNAME, DestroyDomainRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveUserFromGroupsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "removeUserFromGroups")
    public JAXBElement<RemoveUserFromGroupsRequest> createRemoveUserFromGroups(RemoveUserFromGroupsRequest value) {
        return new JAXBElement<RemoveUserFromGroupsRequest>(_RemoveUserFromGroups_QNAME, RemoveUserFromGroupsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDomainTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getDomainTypesResponse")
    public JAXBElement<GetDomainTypesResponse> createGetDomainTypesResponse(GetDomainTypesResponse value) {
        return new JAXBElement<GetDomainTypesResponse>(_GetDomainTypesResponse_QNAME, GetDomainTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "updateUser")
    public JAXBElement<UpdateUserRequest> createUpdateUser(UpdateUserRequest value) {
        return new JAXBElement<UpdateUserRequest>(_UpdateUser_QNAME, UpdateUserRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateDomainResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "createDomainResponse")
    public JAXBElement<CreateDomainResponse> createCreateDomainResponse(CreateDomainResponse value) {
        return new JAXBElement<CreateDomainResponse>(_CreateDomainResponse_QNAME, CreateDomainResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDomainsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getDomainsResponse")
    public JAXBElement<GetDomainsResponse> createGetDomainsResponse(GetDomainsResponse value) {
        return new JAXBElement<GetDomainsResponse>(_GetDomainsResponse_QNAME, GetDomainsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyPrincipalsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "destroyPrincipalsResponse")
    public JAXBElement<DestroyPrincipalsResponse> createDestroyPrincipalsResponse(DestroyPrincipalsResponse value) {
        return new JAXBElement<DestroyPrincipalsResponse>(_DestroyPrincipalsResponse_QNAME, DestroyPrincipalsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDomainGroupsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getDomainGroups")
    public JAXBElement<GetDomainGroupsRequest> createGetDomainGroups(GetDomainGroupsRequest value) {
        return new JAXBElement<GetDomainGroupsRequest>(_GetDomainGroups_QNAME, GetDomainGroupsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDomainUsersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getDomainUsersResponse")
    public JAXBElement<GetDomainUsersResponse> createGetDomainUsersResponse(GetDomainUsersResponse value) {
        return new JAXBElement<GetDomainUsersResponse>(_GetDomainUsersResponse_QNAME, GetDomainUsersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "createGroupResponse")
    public JAXBElement<CreateGroupResponse> createCreateGroupResponse(CreateGroupResponse value) {
        return new JAXBElement<CreateGroupResponse>(_CreateGroupResponse_QNAME, CreateGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyGroupRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "destroyGroup")
    public JAXBElement<DestroyGroupRequest> createDestroyGroup(DestroyGroupRequest value) {
        return new JAXBElement<DestroyGroupRequest>(_DestroyGroup_QNAME, DestroyGroupRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDomainResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "updateDomainResponse")
    public JAXBElement<UpdateDomainResponse> createUpdateDomainResponse(UpdateDomainResponse value) {
        return new JAXBElement<UpdateDomainResponse>(_UpdateDomainResponse_QNAME, UpdateDomainResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyUserRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "destroyUser")
    public JAXBElement<DestroyUserRequest> createDestroyUser(DestroyUserRequest value) {
        return new JAXBElement<DestroyUserRequest>(_DestroyUser_QNAME, DestroyUserRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddUserToGroupsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "addUserToGroups")
    public JAXBElement<AddUserToGroupsRequest> createAddUserToGroups(AddUserToGroupsRequest value) {
        return new JAXBElement<AddUserToGroupsRequest>(_AddUserToGroups_QNAME, AddUserToGroupsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUserRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "createUser")
    public JAXBElement<CreateUserRequest> createCreateUser(CreateUserRequest value) {
        return new JAXBElement<CreateUserRequest>(_CreateUser_QNAME, CreateUserRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "updateGroupResponse")
    public JAXBElement<UpdateGroupResponse> createUpdateGroupResponse(UpdateGroupResponse value) {
        return new JAXBElement<UpdateGroupResponse>(_UpdateGroupResponse_QNAME, UpdateGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyDomainResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "destroyDomainResponse")
    public JAXBElement<DestroyDomainResponse> createDestroyDomainResponse(DestroyDomainResponse value) {
        return new JAXBElement<DestroyDomainResponse>(_DestroyDomainResponse_QNAME, DestroyDomainResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateGroupRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "updateGroup")
    public JAXBElement<UpdateGroupRequest> createUpdateGroup(UpdateGroupRequest value) {
        return new JAXBElement<UpdateGroupRequest>(_UpdateGroup_QNAME, UpdateGroupRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "destroyUserResponse")
    public JAXBElement<DestroyUserResponse> createDestroyUserResponse(DestroyUserResponse value) {
        return new JAXBElement<DestroyUserResponse>(_DestroyUserResponse_QNAME, DestroyUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelCreateDomainResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "cancelCreateDomainResponse")
    public JAXBElement<CancelCreateDomainResponse> createCancelCreateDomainResponse(CancelCreateDomainResponse value) {
        return new JAXBElement<CancelCreateDomainResponse>(_CancelCreateDomainResponse_QNAME, CancelCreateDomainResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDomainTypesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getDomainTypes")
    public JAXBElement<GetDomainTypesRequest> createGetDomainTypes(GetDomainTypesRequest value) {
        return new JAXBElement<GetDomainTypesRequest>(_GetDomainTypes_QNAME, GetDomainTypesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateGroupRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "createGroup")
    public JAXBElement<CreateGroupRequest> createCreateGroup(CreateGroupRequest value) {
        return new JAXBElement<CreateGroupRequest>(_CreateGroup_QNAME, CreateGroupRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddUsersToGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "addUsersToGroupResponse")
    public JAXBElement<AddUsersToGroupResponse> createAddUsersToGroupResponse(AddUsersToGroupResponse value) {
        return new JAXBElement<AddUsersToGroupResponse>(_AddUsersToGroupResponse_QNAME, AddUsersToGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGroupsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getGroups")
    public JAXBElement<GetGroupsRequest> createGetGroups(GetGroupsRequest value) {
        return new JAXBElement<GetGroupsRequest>(_GetGroups_QNAME, GetGroupsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyPrincipalsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "destroyPrincipals")
    public JAXBElement<DestroyPrincipalsRequest> createDestroyPrincipals(DestroyPrincipalsRequest value) {
        return new JAXBElement<DestroyPrincipalsRequest>(_DestroyPrincipals_QNAME, DestroyPrincipalsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getUsersResponse")
    public JAXBElement<GetUsersResponse> createGetUsersResponse(GetUsersResponse value) {
        return new JAXBElement<GetUsersResponse>(_GetUsersResponse_QNAME, GetUsersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveUserFromGroupsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "removeUserFromGroupsResponse")
    public JAXBElement<RemoveUserFromGroupsResponse> createRemoveUserFromGroupsResponse(RemoveUserFromGroupsResponse value) {
        return new JAXBElement<RemoveUserFromGroupsResponse>(_RemoveUserFromGroupsResponse_QNAME, RemoveUserFromGroupsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/user", name = "getUser")
    public JAXBElement<GetUserRequest> createGetUser(GetUserRequest value) {
        return new JAXBElement<GetUserRequest>(_GetUser_QNAME, GetUserRequest.class, null, value);
    }

}
