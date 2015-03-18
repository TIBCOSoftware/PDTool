
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
import com.compositesw.services.system.admin.resource.GetSOAProjectsRequest;
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
import com.compositesw.services.system.util.security.AddLoginModuleRequest;
import com.compositesw.services.system.util.security.AddPrincipalMappingRequest;
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
    GetCreateDBHealthMonitorTableSQLRequest.class,
    RemoveLicensesRequest.class,
    UpdateServerNameRequest.class,
    CreateDBHealthMonitorTableRequest.class,
    UpdateClusterNameRequest.class,
    GetServerAttributeDefChildrenRequest.class,
    GetClusterConfigRequest.class,
    PerformServerActionRequest.class,
    GetServerNameRequest.class,
    AddLicensesRequest.class,
    CreateClusterRequest.class,
    GetLicensesRequest.class,
    RepairClusterRequest.class,
    JoinClusterRequest.class,
    RemoveFromClusterRequest.class,
    GetServerActionsRequest.class,
    UpdateServerAttributesRequest.class,
    PathsRequest.class,
    GetDomainTypesRequest.class,
    GetGroupsByUserRequest.class,
    GetDomainTypeAttributeDefsRequest.class,
    GetUsersByGroupRequest.class,
    GetUserRequest.class,
    GetDomainsRequest.class,
    DomainRequest.class,
    DomainGroupRequest.class,
    DomainUserRequest.class,
    DomainMultiNameRequest.class,
    DomainDetailRequest.class,
    CancelServerTaskRequest.class,
    RemoveLoginModuleRequest.class,
    UpdateLoginModuleRequest.class,
    GetGeneralSettingsRequest.class,
    RbsAssignFilterPolicyRequest.class,
    GetSecurityBundleListRequest.class,
    RbsSetEnabledRequest.class,
    RbsGetFilterPolicyRequest.class,
    RemoveSecurityBundleRequest.class,
    UpdateSecurityBundleRequest.class,
    GetLoginModuleDefaultPropertiesRequest.class,
    RbsGetFilterPolicyListRequest.class,
    UpdateLoginModuleListRequest.class,
    UpdateGeneralSettingsRequest.class,
    RbsWriteFilterPolicyRequest.class,
    RbsDeleteFilterPolicyRequest.class,
    UpdatePrincipalMappingRequest.class,
    AddLoginModuleRequest.class,
    RemovePrincipalMappingRequest.class,
    GetLoginModuleListRequest.class,
    GetPrincipalMappingListRequest.class,
    AddPrincipalMappingRequest.class,
    GetAvailableLoginModuleNamesRequest.class,
    GetPrincipalMappingRequest.class,
    GetLoginModuleRequest.class,
    GetSecurityBundleRequest.class,
    RbsIsEnabledRequest.class,
    ReintrospectDataSourceRequest.class,
    GetDataSourceTypesRequest.class,
    GetDataSourceTypeCustomCapabilitiesRequest.class,
    GetIntrospectionAttributeDefsRequest.class,
    UpdateDataSourceChildInfosRequest.class,
    GetDataSourceAttributeDefsRequest.class,
    CreateCustomDataSourceTypeRequest.class,
    UpdateConnectorRequest.class,
    GetConnectorGroupNamesRequest.class,
    GetDataSourceReintrospectResultRequest.class,
    GetExtendableDataSourceTypesRequest.class,
    GetParentDataSourceTypeRequest.class,
    UpdateResourcesRequest.class,
    UpdateResourcePrivilegesRequest.class,
    UpdateCustomDataSourceTypeRequest.class,
    GetResourceUpdatesRequest.class,
    GetLockedResourcesRequest.class,
    GetIntrospectionAttributesRequest.class,
    UpdateDataSourceChildInfosWithFilterRequest.class,
    GetResourcePrivilegesRequest.class,
    GetDataSourceChildResourcesRequest.class,
    CopyResourcePrivilegesRequest.class,
    GetConnectorsRequest.class,
    UpdateDataSourceTypeCustomCapabilitiesRequest.class,
    DestroyCustomDataSourceTypeRequest.class,
    CancelDataSourceReintrospectRequest.class,
    CreateConnectorRequest.class,
    ServerTaskRequest.class,
    TestDataSourceConnectionRequest.class,
    ServerTaskResultRequest.class,
    ClearIntrospectableResourceIdCacheRequest.class,
    GetSOAProjectsRequest.class,
    DestroyConnectorRequest.class,
    GetDataSourceTypeAttributeDefsRequest.class,
    GetConnectorGroupRequest.class,
    PathNameDetailRequest.class,
    PathTypeVersionRequest.class,
    PathTypeDetailVersionRequest.class,
    PathTypeOptionalDetailRequest.class,
    MultiPathTypeRequest.class,
    PathDetailRequest.class,
    MultiPathTypeOptionalRequest.class,
    PathTypeRequest.class,
    PathTypeDetailRequest.class,
    ExecuteProcedureRequest.class,
    ParseSqlQueryRequest.class,
    ExecuteSqlScriptRequest.class,
    CloseResultRequest.class,
    GetTabularResultRequest.class,
    GetProceduralResultRequest.class,
    BaseQueryPlanRequest.class,
    BaseExecuteSqlRequest.class,
    CreateExportArchiveRequest.class,
    CreateImportArchiveRequest.class,
    ExportArchiveRequest.class,
    ArchiveRequest.class,
    ImportArchiveRequest.class
})
public class BaseRequest {


}
