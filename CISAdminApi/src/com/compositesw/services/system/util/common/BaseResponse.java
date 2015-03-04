
package com.compositesw.services.system.util.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.admin.archive.ArchiveReportResponse;
import com.compositesw.services.system.admin.archive.CreateExportArchiveResponse;
import com.compositesw.services.system.admin.archive.CreateImportArchiveResponse;
import com.compositesw.services.system.admin.archive.GetArchiveContentsResponse;
import com.compositesw.services.system.admin.archive.GetArchiveExportSettingsResponse;
import com.compositesw.services.system.admin.archive.GetArchiveImportSettingsResponse;
import com.compositesw.services.system.admin.archive.UpdateArchiveExportSettingsResponse;
import com.compositesw.services.system.admin.archive.UpdateArchiveImportSettingsResponse;
import com.compositesw.services.system.admin.execute.BaseProceduralResultResponse;
import com.compositesw.services.system.admin.execute.BaseQueryPlanResponse;
import com.compositesw.services.system.admin.execute.BaseTabularResultResponse;
import com.compositesw.services.system.admin.execute.CloseResultResponse;
import com.compositesw.services.system.admin.execute.ParseSqlQueryResponse;
import com.compositesw.services.system.admin.resource.CancelResourceStatisticsResponse;
import com.compositesw.services.system.admin.resource.ClearIntrospectableResourceIdCacheResponse;
import com.compositesw.services.system.admin.resource.ClearResourceCacheResponse;
import com.compositesw.services.system.admin.resource.ClearResourceStatisticsResponse;
import com.compositesw.services.system.admin.resource.CopyResourcePrivilegesResponse;
import com.compositesw.services.system.admin.resource.CopyResourceResponse;
import com.compositesw.services.system.admin.resource.CopyResourcesResponse;
import com.compositesw.services.system.admin.resource.CreateConnectorResponse;
import com.compositesw.services.system.admin.resource.DestroyConnectorResponse;
import com.compositesw.services.system.admin.resource.DestroyCustomDataSourceTypeResponse;
import com.compositesw.services.system.admin.resource.DestroyResourceResponse;
import com.compositesw.services.system.admin.resource.DestroyResourcesResponse;
import com.compositesw.services.system.admin.resource.GetCachedResourceStatisticsConfigResponse;
import com.compositesw.services.system.admin.resource.GetConnectorGroupNamesResponse;
import com.compositesw.services.system.admin.resource.GetConnectorGroupResponse;
import com.compositesw.services.system.admin.resource.GetConnectorsResponse;
import com.compositesw.services.system.admin.resource.GetDataSourceAttributeDefsResponse;
import com.compositesw.services.system.admin.resource.GetDataSourceStatisticsConfigResponse;
import com.compositesw.services.system.admin.resource.GetDataSourceTypeAttributeDefsResponse;
import com.compositesw.services.system.admin.resource.GetDataSourceTypeCustomCapabilitiesResponse;
import com.compositesw.services.system.admin.resource.GetDataSourceTypesResponse;
import com.compositesw.services.system.admin.resource.GetExtendableDataSourceTypesResponse;
import com.compositesw.services.system.admin.resource.GetIntrospectionAttributeDefsResponse;
import com.compositesw.services.system.admin.resource.GetIntrospectionAttributesResponse;
import com.compositesw.services.system.admin.resource.GetMostRecentIntrospectionStatusResponse;
import com.compositesw.services.system.admin.resource.GetResourceCacheConfigResponse;
import com.compositesw.services.system.admin.resource.GetResourcePrivilegesResponse;
import com.compositesw.services.system.admin.resource.GetResourceStatisticsConfigResponse;
import com.compositesw.services.system.admin.resource.GetResourceUpdatesResponse;
import com.compositesw.services.system.admin.resource.GetSOAProjectsResponse;
import com.compositesw.services.system.admin.resource.MoveResourceResponse;
import com.compositesw.services.system.admin.resource.MoveResourcesResponse;
import com.compositesw.services.system.admin.resource.RebindResourcesResponse;
import com.compositesw.services.system.admin.resource.RefreshResourceCacheResponse;
import com.compositesw.services.system.admin.resource.RefreshResourceStatisticsResponse;
import com.compositesw.services.system.admin.resource.ReintrospectResponse;
import com.compositesw.services.system.admin.resource.RenameResourceResponse;
import com.compositesw.services.system.admin.resource.ResourceExistsResponse;
import com.compositesw.services.system.admin.resource.ResourcesResponse;
import com.compositesw.services.system.admin.resource.TestDataSourceConnectionResponse;
import com.compositesw.services.system.admin.resource.UpdateCachedResourceStatisticsConfigResponse;
import com.compositesw.services.system.admin.resource.UpdateConnectorResponse;
import com.compositesw.services.system.admin.resource.UpdateDataSourceChildInfosResponse;
import com.compositesw.services.system.admin.resource.UpdateDataSourceChildInfosWithFilterResponse;
import com.compositesw.services.system.admin.resource.UpdateDataSourceStatisticsConfigResponse;
import com.compositesw.services.system.admin.resource.UpdateDataSourceTypeCustomCapabilitiesResponse;
import com.compositesw.services.system.admin.resource.UpdateResourceCacheConfigResponse;
import com.compositesw.services.system.admin.resource.UpdateResourcePrivilegesResponse;
import com.compositesw.services.system.admin.resource.UpdateResourceStatisticsConfigResponse;
import com.compositesw.services.system.admin.resource.UpdateResourcesResponse;
import com.compositesw.services.system.admin.server.AddLicensesResponse;
import com.compositesw.services.system.admin.server.AttributeDefsResponse;
import com.compositesw.services.system.admin.server.AttributesResponse;
import com.compositesw.services.system.admin.server.CreateClusterResponse;
import com.compositesw.services.system.admin.server.CreateDBHealthMonitorTableResponse;
import com.compositesw.services.system.admin.server.GetClusterConfigResponse;
import com.compositesw.services.system.admin.server.GetCreateDBHealthMonitorTableSQLResponse;
import com.compositesw.services.system.admin.server.GetLicensesResponse;
import com.compositesw.services.system.admin.server.GetServerActionsResponse;
import com.compositesw.services.system.admin.server.GetServerNameResponse;
import com.compositesw.services.system.admin.server.JoinClusterResponse;
import com.compositesw.services.system.admin.server.PerformServerActionResponse;
import com.compositesw.services.system.admin.server.RemoveFromClusterResponse;
import com.compositesw.services.system.admin.server.RemoveLicensesResponse;
import com.compositesw.services.system.admin.server.RepairClusterResponse;
import com.compositesw.services.system.admin.server.UpdateClusterNameResponse;
import com.compositesw.services.system.admin.server.UpdateServerAttributesResponse;
import com.compositesw.services.system.admin.server.UpdateServerNameResponse;
import com.compositesw.services.system.admin.user.AddUserToGroupsResponse;
import com.compositesw.services.system.admin.user.AddUsersToGroupResponse;
import com.compositesw.services.system.admin.user.BaseGroupsResponse;
import com.compositesw.services.system.admin.user.BaseUserResponse;
import com.compositesw.services.system.admin.user.CancelCreateDomainResponse;
import com.compositesw.services.system.admin.user.CreateDomainResponse;
import com.compositesw.services.system.admin.user.CreateGroupResponse;
import com.compositesw.services.system.admin.user.CreateUserResponse;
import com.compositesw.services.system.admin.user.DestroyDomainResponse;
import com.compositesw.services.system.admin.user.DestroyGroupResponse;
import com.compositesw.services.system.admin.user.DestroyUserResponse;
import com.compositesw.services.system.admin.user.GetDomainGroupsResponse;
import com.compositesw.services.system.admin.user.GetDomainTypeAttributeDefsResponse;
import com.compositesw.services.system.admin.user.GetDomainTypesResponse;
import com.compositesw.services.system.admin.user.GetDomainUsersResponse;
import com.compositesw.services.system.admin.user.GetDomainsResponse;
import com.compositesw.services.system.admin.user.GetUserResponse;
import com.compositesw.services.system.admin.user.RemoveUserFromGroupsResponse;
import com.compositesw.services.system.admin.user.RemoveUsersFromGroupResponse;
import com.compositesw.services.system.admin.user.UpdateDomainResponse;
import com.compositesw.services.system.admin.user.UpdateGroupResponse;
import com.compositesw.services.system.admin.user.UpdateUserResponse;
import com.compositesw.services.system.util.security.AddLoginModuleResponse;
import com.compositesw.services.system.util.security.AddPrincipalMappingResponse;
import com.compositesw.services.system.util.security.GetAvailableLoginModuleNamesResponse;
import com.compositesw.services.system.util.security.GetGeneralSettingsResponse;
import com.compositesw.services.system.util.security.GetLoginModuleDefaultPropertiesResponse;
import com.compositesw.services.system.util.security.GetLoginModuleListResponse;
import com.compositesw.services.system.util.security.GetLoginModuleResponse;
import com.compositesw.services.system.util.security.GetPrincipalMappingListResponse;
import com.compositesw.services.system.util.security.GetPrincipalMappingResponse;
import com.compositesw.services.system.util.security.GetSecurityBundleListResponse;
import com.compositesw.services.system.util.security.GetSecurityBundleResponse;
import com.compositesw.services.system.util.security.RbsAssignFilterPolicyResponse;
import com.compositesw.services.system.util.security.RbsDeleteFilterPolicyResponse;
import com.compositesw.services.system.util.security.RbsGetFilterPolicyListResponse;
import com.compositesw.services.system.util.security.RbsGetFilterPolicyResponse;
import com.compositesw.services.system.util.security.RbsIsEnabledResponse;
import com.compositesw.services.system.util.security.RbsSetEnabledResponse;
import com.compositesw.services.system.util.security.RbsWriteFilterPolicyResponse;
import com.compositesw.services.system.util.security.RemoveLoginModuleResponse;
import com.compositesw.services.system.util.security.RemovePrincipalMappingResponse;
import com.compositesw.services.system.util.security.RemoveSecurityBundleResponse;
import com.compositesw.services.system.util.security.UpdateGeneralSettingsResponse;
import com.compositesw.services.system.util.security.UpdateLoginModuleListResponse;
import com.compositesw.services.system.util.security.UpdateLoginModuleResponse;
import com.compositesw.services.system.util.security.UpdatePrincipalMappingResponse;
import com.compositesw.services.system.util.security.UpdateSecurityBundleResponse;


/**
 * <p>Java class for baseResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="baseResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseResponse")
@XmlSeeAlso({
    UpdateArchiveImportSettingsResponse.class,
    CreateImportArchiveResponse.class,
    UpdateArchiveExportSettingsResponse.class,
    GetArchiveContentsResponse.class,
    GetArchiveImportSettingsResponse.class,
    GetArchiveExportSettingsResponse.class,
    CreateExportArchiveResponse.class,
    ArchiveReportResponse.class,
    AddLoginModuleResponse.class,
    RbsAssignFilterPolicyResponse.class,
    GetSecurityBundleResponse.class,
    RbsIsEnabledResponse.class,
    RbsGetFilterPolicyResponse.class,
    GetLoginModuleDefaultPropertiesResponse.class,
    GetPrincipalMappingListResponse.class,
    AddPrincipalMappingResponse.class,
    UpdateSecurityBundleResponse.class,
    GetPrincipalMappingResponse.class,
    RbsGetFilterPolicyListResponse.class,
    UpdateGeneralSettingsResponse.class,
    GetAvailableLoginModuleNamesResponse.class,
    UpdatePrincipalMappingResponse.class,
    UpdateLoginModuleResponse.class,
    RemoveSecurityBundleResponse.class,
    RbsSetEnabledResponse.class,
    UpdateLoginModuleListResponse.class,
    RbsDeleteFilterPolicyResponse.class,
    GetGeneralSettingsResponse.class,
    RemovePrincipalMappingResponse.class,
    RbsWriteFilterPolicyResponse.class,
    GetSecurityBundleListResponse.class,
    RemoveLoginModuleResponse.class,
    GetLoginModuleResponse.class,
    GetLoginModuleListResponse.class,
    CancelServerTaskResponse.class,
    UpdateDataSourceTypeCustomCapabilitiesResponse.class,
    GetConnectorGroupResponse.class,
    DestroyResourcesResponse.class,
    RefreshResourceCacheResponse.class,
    MoveResourceResponse.class,
    DestroyCustomDataSourceTypeResponse.class,
    TestDataSourceConnectionResponse.class,
    UpdateConnectorResponse.class,
    DestroyResourceResponse.class,
    GetConnectorGroupNamesResponse.class,
    GetResourceStatisticsConfigResponse.class,
    CopyResourceResponse.class,
    GetResourceCacheConfigResponse.class,
    RebindResourcesResponse.class,
    CopyResourcePrivilegesResponse.class,
    GetIntrospectionAttributesResponse.class,
    GetResourcePrivilegesResponse.class,
    GetConnectorsResponse.class,
    GetDataSourceStatisticsConfigResponse.class,
    MoveResourcesResponse.class,
    RenameResourceResponse.class,
    GetDataSourceTypeCustomCapabilitiesResponse.class,
    ClearResourceStatisticsResponse.class,
    GetDataSourceTypeAttributeDefsResponse.class,
    ResourceExistsResponse.class,
    GetDataSourceTypesResponse.class,
    UpdateResourceCacheConfigResponse.class,
    GetCachedResourceStatisticsConfigResponse.class,
    ClearResourceCacheResponse.class,
    GetResourceUpdatesResponse.class,
    RefreshResourceStatisticsResponse.class,
    GetMostRecentIntrospectionStatusResponse.class,
    UpdateResourceStatisticsConfigResponse.class,
    CreateConnectorResponse.class,
    ServerTaskResponse.class,
    GetDataSourceAttributeDefsResponse.class,
    UpdateCachedResourceStatisticsConfigResponse.class,
    UpdateDataSourceChildInfosResponse.class,
    UpdateDataSourceStatisticsConfigResponse.class,
    CopyResourcesResponse.class,
    ClearIntrospectableResourceIdCacheResponse.class,
    GetSOAProjectsResponse.class,
    GetIntrospectionAttributeDefsResponse.class,
    UpdateDataSourceChildInfosWithFilterResponse.class,
    DestroyConnectorResponse.class,
    UpdateResourcePrivilegesResponse.class,
    GetExtendableDataSourceTypesResponse.class,
    CancelResourceStatisticsResponse.class,
    UpdateResourcesResponse.class,
    ResourcesResponse.class,
    ReintrospectResponse.class,
    CloseResultResponse.class,
    ParseSqlQueryResponse.class,
    BaseQueryPlanResponse.class,
    BaseTabularResultResponse.class,
    BaseProceduralResultResponse.class,
    GetDomainGroupsResponse.class,
    RemoveUsersFromGroupResponse.class,
    DestroyUserResponse.class,
    GetDomainUsersResponse.class,
    DestroyGroupResponse.class,
    GetDomainsResponse.class,
    CreateUserResponse.class,
    GetDomainTypesResponse.class,
    GetUserResponse.class,
    AddUserToGroupsResponse.class,
    CancelCreateDomainResponse.class,
    UpdateDomainResponse.class,
    UpdateUserResponse.class,
    AddUsersToGroupResponse.class,
    RemoveUserFromGroupsResponse.class,
    CreateDomainResponse.class,
    CreateGroupResponse.class,
    GetDomainTypeAttributeDefsResponse.class,
    DestroyDomainResponse.class,
    UpdateGroupResponse.class,
    BaseGroupsResponse.class,
    BaseUserResponse.class,
    JoinClusterResponse.class,
    RemoveLicensesResponse.class,
    GetClusterConfigResponse.class,
    CreateDBHealthMonitorTableResponse.class,
    GetLicensesResponse.class,
    CreateClusterResponse.class,
    UpdateClusterNameResponse.class,
    RemoveFromClusterResponse.class,
    UpdateServerAttributesResponse.class,
    PerformServerActionResponse.class,
    GetServerActionsResponse.class,
    RepairClusterResponse.class,
    AddLicensesResponse.class,
    GetServerNameResponse.class,
    GetCreateDBHealthMonitorTableSQLResponse.class,
    UpdateServerNameResponse.class,
    AttributeDefsResponse.class,
    AttributesResponse.class
})
public class BaseResponse {


}
