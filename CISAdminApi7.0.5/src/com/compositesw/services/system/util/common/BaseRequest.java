
package com.compositesw.services.system.util.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.admin.archive.ArchiveRequest;
import com.compositesw.services.system.admin.archive.CreateExportArchiveRequest;
import com.compositesw.services.system.admin.archive.CreateImportArchiveRequest;
import com.compositesw.services.system.admin.archive.ExportArchiveRequest;
import com.compositesw.services.system.admin.archive.ImportArchiveRequest;
import com.compositesw.services.system.admin.execute.BaseExecuteSqlRequest;
import com.compositesw.services.system.admin.execute.BaseQueryPlanRequest;
import com.compositesw.services.system.admin.execute.CloseResultRequest;
import com.compositesw.services.system.admin.execute.ExecuteProcedureRequest;
import com.compositesw.services.system.admin.execute.ExecuteSqlScriptRequest;
import com.compositesw.services.system.admin.execute.GetProceduralResultRequest;
import com.compositesw.services.system.admin.execute.GetTabularResultRequest;
import com.compositesw.services.system.admin.execute.ParseSqlQueryRequest;
import com.compositesw.services.system.admin.resource.CancelDataSourceReintrospectRequest;
import com.compositesw.services.system.admin.resource.ClearIntrospectableResourceIdCacheRequest;
import com.compositesw.services.system.admin.resource.CopyResourcePrivilegesRequest;
import com.compositesw.services.system.admin.resource.CreateConnectorRequest;
import com.compositesw.services.system.admin.resource.CreateCustomDataSourceTypeRequest;
import com.compositesw.services.system.admin.resource.DestroyConnectorRequest;
import com.compositesw.services.system.admin.resource.DestroyCustomDataSourceTypeRequest;
import com.compositesw.services.system.admin.resource.GetConnectorGroupNamesRequest;
import com.compositesw.services.system.admin.resource.GetConnectorGroupRequest;
import com.compositesw.services.system.admin.resource.GetConnectorsRequest;
import com.compositesw.services.system.admin.resource.GetDataSourceAttributeDefsRequest;
import com.compositesw.services.system.admin.resource.GetDataSourceChildResourcesRequest;
import com.compositesw.services.system.admin.resource.GetDataSourceReintrospectResultRequest;
import com.compositesw.services.system.admin.resource.GetDataSourceTypeAttributeDefsRequest;
import com.compositesw.services.system.admin.resource.GetDataSourceTypeCustomCapabilitiesRequest;
import com.compositesw.services.system.admin.resource.GetDataSourceTypesRequest;
import com.compositesw.services.system.admin.resource.GetExtendableDataSourceTypesRequest;
import com.compositesw.services.system.admin.resource.GetIntrospectionAttributeDefsRequest;
import com.compositesw.services.system.admin.resource.GetIntrospectionAttributesRequest;
import com.compositesw.services.system.admin.resource.GetLockedResourcesRequest;
import com.compositesw.services.system.admin.resource.GetParentDataSourceTypeRequest;
import com.compositesw.services.system.admin.resource.GetResourcePrivilegesRequest;
import com.compositesw.services.system.admin.resource.GetResourceUpdatesRequest;
import com.compositesw.services.system.admin.resource.GetTransformFunctionsRequest;
import com.compositesw.services.system.admin.resource.MultiPathTypeOptionalRequest;
import com.compositesw.services.system.admin.resource.MultiPathTypeRequest;
import com.compositesw.services.system.admin.resource.PathDetailRequest;
import com.compositesw.services.system.admin.resource.PathNameDetailRequest;
import com.compositesw.services.system.admin.resource.PathTypeDetailRequest;
import com.compositesw.services.system.admin.resource.PathTypeDetailVersionRequest;
import com.compositesw.services.system.admin.resource.PathTypeOptionalDetailRequest;
import com.compositesw.services.system.admin.resource.PathTypeRequest;
import com.compositesw.services.system.admin.resource.PathTypeVersionRequest;
import com.compositesw.services.system.admin.resource.ReintrospectDataSourceRequest;
import com.compositesw.services.system.admin.resource.TestDataSourceConnectionRequest;
import com.compositesw.services.system.admin.resource.UpdateConnectorRequest;
import com.compositesw.services.system.admin.resource.UpdateCustomDataSourceTypeRequest;
import com.compositesw.services.system.admin.resource.UpdateDataSourceChildInfosRequest;
import com.compositesw.services.system.admin.resource.UpdateDataSourceChildInfosWithFilterRequest;
import com.compositesw.services.system.admin.resource.UpdateDataSourceTypeCustomCapabilitiesRequest;
import com.compositesw.services.system.admin.resource.UpdateResourcePrivilegesRequest;
import com.compositesw.services.system.admin.resource.UpdateResourcesRequest;
import com.compositesw.services.system.admin.server.AddLicensesRequest;
import com.compositesw.services.system.admin.server.CreateClusterRequest;
import com.compositesw.services.system.admin.server.CreateDBHealthMonitorTableRequest;
import com.compositesw.services.system.admin.server.GetClusterConfigRequest;
import com.compositesw.services.system.admin.server.GetCreateDBHealthMonitorTableSQLRequest;
import com.compositesw.services.system.admin.server.GetLicensesRequest;
import com.compositesw.services.system.admin.server.GetServerActionsRequest;
import com.compositesw.services.system.admin.server.GetServerAttributeDefChildrenRequest;
import com.compositesw.services.system.admin.server.GetServerNameRequest;
import com.compositesw.services.system.admin.server.JoinClusterRequest;
import com.compositesw.services.system.admin.server.PathsRequest;
import com.compositesw.services.system.admin.server.PerformServerActionRequest;
import com.compositesw.services.system.admin.server.RemoveFromClusterRequest;
import com.compositesw.services.system.admin.server.RemoveLicensesRequest;
import com.compositesw.services.system.admin.server.RepairClusterRequest;
import com.compositesw.services.system.admin.server.UpdateClusterNameRequest;
import com.compositesw.services.system.admin.server.UpdateServerAttributesRequest;
import com.compositesw.services.system.admin.server.UpdateServerNameRequest;
import com.compositesw.services.system.admin.user.DestroyPrincipalsRequest;
import com.compositesw.services.system.admin.user.DomainDetailRequest;
import com.compositesw.services.system.admin.user.DomainGroupRequest;
import com.compositesw.services.system.admin.user.DomainMultiNameRequest;
import com.compositesw.services.system.admin.user.DomainRequest;
import com.compositesw.services.system.admin.user.DomainUserRequest;
import com.compositesw.services.system.admin.user.GetDomainTypeAttributeDefsRequest;
import com.compositesw.services.system.admin.user.GetDomainTypesRequest;
import com.compositesw.services.system.admin.user.GetDomainsRequest;
import com.compositesw.services.system.admin.user.GetGroupsByUserRequest;
import com.compositesw.services.system.admin.user.GetUserRequest;
import com.compositesw.services.system.admin.user.GetUsersByGroupRequest;
import com.compositesw.services.system.admin.user.UpdateUserLockStateRequest;
import com.compositesw.services.system.util.security.AddLoginModuleRequest;
import com.compositesw.services.system.util.security.AddPrincipalMappingRequest;
import com.compositesw.services.system.util.security.CbsAddAssignmentsRequest;
import com.compositesw.services.system.util.security.CbsCreatePoliciesRequest;
import com.compositesw.services.system.util.security.CbsDeletePoliciesRequest;
import com.compositesw.services.system.util.security.CbsEditPoliciesRequest;
import com.compositesw.services.system.util.security.CbsGetAssignmentsRequest;
import com.compositesw.services.system.util.security.CbsGetPoliciesRequest;
import com.compositesw.services.system.util.security.CbsIsEnabledRequest;
import com.compositesw.services.system.util.security.CbsRemoveAssignmentsRequest;
import com.compositesw.services.system.util.security.CbsSetEnabledRequest;
import com.compositesw.services.system.util.security.CbsUpdateAssignmentsRequest;
import com.compositesw.services.system.util.security.GetAvailableLoginModuleNamesRequest;
import com.compositesw.services.system.util.security.GetGeneralSettingsRequest;
import com.compositesw.services.system.util.security.GetLoginModuleDefaultPropertiesRequest;
import com.compositesw.services.system.util.security.GetLoginModuleListRequest;
import com.compositesw.services.system.util.security.GetLoginModuleRequest;
import com.compositesw.services.system.util.security.GetPrincipalMappingListRequest;
import com.compositesw.services.system.util.security.GetPrincipalMappingRequest;
import com.compositesw.services.system.util.security.GetSecurityBundleListRequest;
import com.compositesw.services.system.util.security.GetSecurityBundleRequest;
import com.compositesw.services.system.util.security.RbsAssignFilterPolicyRequest;
import com.compositesw.services.system.util.security.RbsDeleteFilterPolicyRequest;
import com.compositesw.services.system.util.security.RbsGetFilterPolicyListRequest;
import com.compositesw.services.system.util.security.RbsGetFilterPolicyRequest;
import com.compositesw.services.system.util.security.RbsIsEnabledRequest;
import com.compositesw.services.system.util.security.RbsSetEnabledRequest;
import com.compositesw.services.system.util.security.RbsWriteFilterPolicyRequest;
import com.compositesw.services.system.util.security.RemoveLoginModuleRequest;
import com.compositesw.services.system.util.security.RemovePrincipalMappingRequest;
import com.compositesw.services.system.util.security.RemoveSecurityBundleRequest;
import com.compositesw.services.system.util.security.UpdateGeneralSettingsRequest;
import com.compositesw.services.system.util.security.UpdateLoginModuleListRequest;
import com.compositesw.services.system.util.security.UpdateLoginModuleRequest;
import com.compositesw.services.system.util.security.UpdatePrincipalMappingRequest;
import com.compositesw.services.system.util.security.UpdateSecurityBundleRequest;


/**
 * <p>Java class for baseRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="baseRequest">
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
@XmlType(name = "baseRequest")
@XmlSeeAlso({
    GetProceduralResultRequest.class,
    GetTabularResultRequest.class,
    ExecuteProcedureRequest.class,
    ExecuteSqlScriptRequest.class,
    CloseResultRequest.class,
    ParseSqlQueryRequest.class,
    BaseQueryPlanRequest.class,
    BaseExecuteSqlRequest.class,
    CreateExportArchiveRequest.class,
    CreateImportArchiveRequest.class,
    ExportArchiveRequest.class,
    ImportArchiveRequest.class,
    ArchiveRequest.class,
    UpdateServerAttributesRequest.class,
    GetServerActionsRequest.class,
    PerformServerActionRequest.class,
    GetServerNameRequest.class,
    RemoveFromClusterRequest.class,
    AddLicensesRequest.class,
    UpdateClusterNameRequest.class,
    RemoveLicensesRequest.class,
    JoinClusterRequest.class,
    GetClusterConfigRequest.class,
    GetCreateDBHealthMonitorTableSQLRequest.class,
    RepairClusterRequest.class,
    GetLicensesRequest.class,
    UpdateServerNameRequest.class,
    GetServerAttributeDefChildrenRequest.class,
    CreateClusterRequest.class,
    CreateDBHealthMonitorTableRequest.class,
    PathsRequest.class,
    UpdateCustomDataSourceTypeRequest.class,
    UpdateDataSourceChildInfosRequest.class,
    GetLockedResourcesRequest.class,
    GetDataSourceTypesRequest.class,
    UpdateResourcePrivilegesRequest.class,
    GetResourcePrivilegesRequest.class,
    GetDataSourceTypeAttributeDefsRequest.class,
    GetDataSourceAttributeDefsRequest.class,
    GetParentDataSourceTypeRequest.class,
    TestDataSourceConnectionRequest.class,
    CreateConnectorRequest.class,
    GetIntrospectionAttributeDefsRequest.class,
    CreateCustomDataSourceTypeRequest.class,
    UpdateConnectorRequest.class,
    GetConnectorGroupNamesRequest.class,
    ReintrospectDataSourceRequest.class,
    GetResourceUpdatesRequest.class,
    GetIntrospectionAttributesRequest.class,
    DestroyCustomDataSourceTypeRequest.class,
    UpdateResourcesRequest.class,
    DestroyConnectorRequest.class,
    GetDataSourceTypeCustomCapabilitiesRequest.class,
    GetDataSourceReintrospectResultRequest.class,
    GetTransformFunctionsRequest.class,
    CopyResourcePrivilegesRequest.class,
    UpdateDataSourceTypeCustomCapabilitiesRequest.class,
    GetConnectorGroupRequest.class,
    GetExtendableDataSourceTypesRequest.class,
    GetDataSourceChildResourcesRequest.class,
    GetConnectorsRequest.class,
    CancelDataSourceReintrospectRequest.class,
    UpdateDataSourceChildInfosWithFilterRequest.class,
    ClearIntrospectableResourceIdCacheRequest.class,
    PathTypeRequest.class,
    PathTypeDetailVersionRequest.class,
    PathTypeVersionRequest.class,
    PathTypeDetailRequest.class,
    PathNameDetailRequest.class,
    PathTypeOptionalDetailRequest.class,
    MultiPathTypeRequest.class,
    MultiPathTypeOptionalRequest.class,
    PathDetailRequest.class,
    CancelServerTaskRequest.class,
    ServerTaskResultRequest.class,
    ServerTaskRequest.class,
    UpdateUserLockStateRequest.class,
    DestroyPrincipalsRequest.class,
    GetDomainsRequest.class,
    GetDomainTypesRequest.class,
    GetUserRequest.class,
    GetDomainTypeAttributeDefsRequest.class,
    GetUsersByGroupRequest.class,
    GetGroupsByUserRequest.class,
    DomainGroupRequest.class,
    DomainDetailRequest.class,
    DomainUserRequest.class,
    DomainMultiNameRequest.class,
    DomainRequest.class,
    CbsRemoveAssignmentsRequest.class,
    RbsDeleteFilterPolicyRequest.class,
    GetSecurityBundleListRequest.class,
    GetGeneralSettingsRequest.class,
    RbsWriteFilterPolicyRequest.class,
    GetAvailableLoginModuleNamesRequest.class,
    AddLoginModuleRequest.class,
    CbsIsEnabledRequest.class,
    RemoveLoginModuleRequest.class,
    RemoveSecurityBundleRequest.class,
    RbsSetEnabledRequest.class,
    UpdateGeneralSettingsRequest.class,
    CbsSetEnabledRequest.class,
    UpdatePrincipalMappingRequest.class,
    GetPrincipalMappingListRequest.class,
    RbsGetFilterPolicyListRequest.class,
    CbsGetPoliciesRequest.class,
    AddPrincipalMappingRequest.class,
    RbsIsEnabledRequest.class,
    UpdateSecurityBundleRequest.class,
    RbsGetFilterPolicyRequest.class,
    CbsDeletePoliciesRequest.class,
    UpdateLoginModuleRequest.class,
    GetLoginModuleDefaultPropertiesRequest.class,
    UpdateLoginModuleListRequest.class,
    RemovePrincipalMappingRequest.class,
    CbsAddAssignmentsRequest.class,
    GetLoginModuleRequest.class,
    GetLoginModuleListRequest.class,
    CbsUpdateAssignmentsRequest.class,
    CbsGetAssignmentsRequest.class,
    GetSecurityBundleRequest.class,
    GetPrincipalMappingRequest.class,
    CbsEditPoliciesRequest.class,
    RbsAssignFilterPolicyRequest.class,
    CbsCreatePoliciesRequest.class
})
public class BaseRequest {


}
