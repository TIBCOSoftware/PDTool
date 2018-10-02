
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
import com.compositesw.services.system.admin.resource.GetResourceStatsSummaryResponse;
import com.compositesw.services.system.admin.resource.GetResourceUpdatesResponse;
import com.compositesw.services.system.admin.resource.GetTransformFunctionsResponse;
import com.compositesw.services.system.admin.resource.GetUsedDataSourcesResponse;
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
import com.compositesw.services.system.admin.user.DestroyPrincipalsResponse;
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
import com.compositesw.services.system.admin.user.UpdateUserLockStateResponse;
import com.compositesw.services.system.admin.user.UpdateUserResponse;
import com.compositesw.services.system.util.security.AddLoginModuleResponse;
import com.compositesw.services.system.util.security.AddPrincipalMappingResponse;
import com.compositesw.services.system.util.security.CbsAddAssignmentsResponse;
import com.compositesw.services.system.util.security.CbsCreatePoliciesResponse;
import com.compositesw.services.system.util.security.CbsDeletePoliciesResponse;
import com.compositesw.services.system.util.security.CbsEditPoliciesResponse;
import com.compositesw.services.system.util.security.CbsGetAssignmentsResponse;
import com.compositesw.services.system.util.security.CbsGetPoliciesResponse;
import com.compositesw.services.system.util.security.CbsIsEnabledResponse;
import com.compositesw.services.system.util.security.CbsRemoveAssignmentsResponse;
import com.compositesw.services.system.util.security.CbsSetEnabledResponse;
import com.compositesw.services.system.util.security.CbsUpdateAssignmentsResponse;
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
    ParseSqlQueryResponse.class,
    CloseResultResponse.class,
    BaseQueryPlanResponse.class,
    BaseTabularResultResponse.class,
    BaseProceduralResultResponse.class,
    GetArchiveImportSettingsResponse.class,
    GetArchiveExportSettingsResponse.class,
    UpdateArchiveImportSettingsResponse.class,
    GetArchiveContentsResponse.class,
    CreateImportArchiveResponse.class,
    CreateExportArchiveResponse.class,
    UpdateArchiveExportSettingsResponse.class,
    ArchiveReportResponse.class,
    GetClusterConfigResponse.class,
    GetCreateDBHealthMonitorTableSQLResponse.class,
    CreateDBHealthMonitorTableResponse.class,
    GetLicensesResponse.class,
    RemoveLicensesResponse.class,
    RemoveFromClusterResponse.class,
    UpdateClusterNameResponse.class,
    JoinClusterResponse.class,
    UpdateServerAttributesResponse.class,
    PerformServerActionResponse.class,
    CreateClusterResponse.class,
    UpdateServerNameResponse.class,
    GetServerActionsResponse.class,
    RepairClusterResponse.class,
    AddLicensesResponse.class,
    GetServerNameResponse.class,
    AttributeDefsResponse.class,
    AttributesResponse.class,
    GetMostRecentIntrospectionStatusResponse.class,
    CancelResourceStatisticsResponse.class,
    DestroyCustomDataSourceTypeResponse.class,
    GetConnectorsResponse.class,
    UpdateDataSourceStatisticsConfigResponse.class,
    RefreshResourceCacheResponse.class,
    ClearIntrospectableResourceIdCacheResponse.class,
    GetTransformFunctionsResponse.class,
    UpdateDataSourceTypeCustomCapabilitiesResponse.class,
    ResourceExistsResponse.class,
    GetResourceCacheConfigResponse.class,
    GetDataSourceStatisticsConfigResponse.class,
    UpdateResourceStatisticsConfigResponse.class,
    GetDataSourceTypesResponse.class,
    DestroyConnectorResponse.class,
    GetCachedResourceStatisticsConfigResponse.class,
    GetDataSourceTypeCustomCapabilitiesResponse.class,
    CopyResourcesResponse.class,
    GetResourceStatisticsConfigResponse.class,
    UpdateResourceCacheConfigResponse.class,
    GetDataSourceTypeAttributeDefsResponse.class,
    UpdateConnectorResponse.class,
    DestroyResourceResponse.class,
    GetResourceStatsSummaryResponse.class,
    GetConnectorGroupResponse.class,
    RenameResourceResponse.class,
    RebindResourcesResponse.class,
    GetResourceUpdatesResponse.class,
    UpdateDataSourceChildInfosResponse.class,
    ClearResourceStatisticsResponse.class,
    ClearResourceCacheResponse.class,
    CreateConnectorResponse.class,
    CopyResourceResponse.class,
    GetDataSourceAttributeDefsResponse.class,
    GetExtendableDataSourceTypesResponse.class,
    UpdateDataSourceChildInfosWithFilterResponse.class,
    DestroyResourcesResponse.class,
    MoveResourcesResponse.class,
    GetIntrospectionAttributesResponse.class,
    UpdateResourcePrivilegesResponse.class,
    RefreshResourceStatisticsResponse.class,
    MoveResourceResponse.class,
    GetConnectorGroupNamesResponse.class,
    CopyResourcePrivilegesResponse.class,
    TestDataSourceConnectionResponse.class,
    GetUsedDataSourcesResponse.class,
    UpdateResourcesResponse.class,
    GetIntrospectionAttributeDefsResponse.class,
    GetResourcePrivilegesResponse.class,
    UpdateCachedResourceStatisticsConfigResponse.class,
    ResourcesResponse.class,
    ReintrospectResponse.class,
    CancelServerTaskResponse.class,
    ServerTaskResponse.class,
    UpdateUserLockStateResponse.class,
    RemoveUsersFromGroupResponse.class,
    DestroyGroupResponse.class,
    UpdateUserResponse.class,
    GetDomainUsersResponse.class,
    AddUsersToGroupResponse.class,
    AddUserToGroupsResponse.class,
    DestroyPrincipalsResponse.class,
    CancelCreateDomainResponse.class,
    GetUserResponse.class,
    DestroyUserResponse.class,
    DestroyDomainResponse.class,
    GetDomainsResponse.class,
    GetDomainGroupsResponse.class,
    GetDomainTypeAttributeDefsResponse.class,
    CreateUserResponse.class,
    CreateDomainResponse.class,
    GetDomainTypesResponse.class,
    UpdateDomainResponse.class,
    RemoveUserFromGroupsResponse.class,
    UpdateGroupResponse.class,
    CreateGroupResponse.class,
    BaseUserResponse.class,
    BaseGroupsResponse.class,
    CbsGetPoliciesResponse.class,
    GetGeneralSettingsResponse.class,
    GetPrincipalMappingResponse.class,
    CbsEditPoliciesResponse.class,
    CbsSetEnabledResponse.class,
    RbsDeleteFilterPolicyResponse.class,
    CbsGetAssignmentsResponse.class,
    GetPrincipalMappingListResponse.class,
    UpdateGeneralSettingsResponse.class,
    RbsWriteFilterPolicyResponse.class,
    GetLoginModuleResponse.class,
    CbsUpdateAssignmentsResponse.class,
    CbsIsEnabledResponse.class,
    CbsRemoveAssignmentsResponse.class,
    RemoveLoginModuleResponse.class,
    GetLoginModuleListResponse.class,
    UpdatePrincipalMappingResponse.class,
    UpdateLoginModuleListResponse.class,
    UpdateLoginModuleResponse.class,
    UpdateSecurityBundleResponse.class,
    RbsIsEnabledResponse.class,
    AddPrincipalMappingResponse.class,
    RbsGetFilterPolicyResponse.class,
    GetSecurityBundleListResponse.class,
    GetAvailableLoginModuleNamesResponse.class,
    CbsDeletePoliciesResponse.class,
    RbsAssignFilterPolicyResponse.class,
    RemoveSecurityBundleResponse.class,
    RbsGetFilterPolicyListResponse.class,
    RbsSetEnabledResponse.class,
    GetSecurityBundleResponse.class,
    AddLoginModuleResponse.class,
    CbsCreatePoliciesResponse.class,
    CbsAddAssignmentsResponse.class,
    RemovePrincipalMappingResponse.class,
    GetLoginModuleDefaultPropertiesResponse.class
})
public class BaseResponse {


}
