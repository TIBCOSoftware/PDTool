
package com.compositesw.services.system.admin.resource;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.compositesw.services.system.util.common.AttributeList;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.compositesw.services.system.admin.resource package. 
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

    private final static QName _UpdateDataSourceStatisticsConfigResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateDataSourceStatisticsConfigResponse");
    private final static QName _RefreshResourceCacheResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "refreshResourceCacheResponse");
    private final static QName _UpdateSqlTable_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateSqlTable");
    private final static QName _GetMostRecentIntrospectionStatus_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getMostRecentIntrospectionStatus");
    private final static QName _UpdateLink_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateLink");
    private final static QName _UnlockResource_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "unlockResource");
    private final static QName _MoveResources_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "moveResources");
    private final static QName _CreateDataSource_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "createDataSource");
    private final static QName _UpdateDataServicePortResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateDataServicePortResponse");
    private final static QName _UpdateBasicTransformProcedureResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateBasicTransformProcedureResponse");
    private final static QName _CancelResourceStatisticsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "cancelResourceStatisticsResponse");
    private final static QName _GetResource_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getResource");
    private final static QName _UpdateCustomDataSourceType_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateCustomDataSourceType");
    private final static QName _UpdateExternalSqlProcedureResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateExternalSqlProcedureResponse");
    private final static QName _CopyResource_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "copyResource");
    private final static QName _LockResourcesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "lockResourcesResponse");
    private final static QName _GetDataSourceTypeAttributeDefs_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getDataSourceTypeAttributeDefs");
    private final static QName _GetDataSourceStatisticsConfigResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getDataSourceStatisticsConfigResponse");
    private final static QName _GetResourceCacheConfig_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getResourceCacheConfig");
    private final static QName _UpdateDataSourceResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateDataSourceResponse");
    private final static QName _GetResources_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getResources");
    private final static QName _GetDataSourceTypesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getDataSourceTypesResponse");
    private final static QName _GetParentDataSourceTypeResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getParentDataSourceTypeResponse");
    private final static QName _GetResourcePrivileges_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getResourcePrivileges");
    private final static QName _CreateLinkResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "createLinkResponse");
    private final static QName _ClearIntrospectableResourceIdCacheResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "clearIntrospectableResourceIdCacheResponse");
    private final static QName _UpdateResourceEnabledResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateResourceEnabledResponse");
    private final static QName _GetDataSourceTypes_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getDataSourceTypes");
    private final static QName _UpdateDataSourceTypeCustomCapabilitiesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateDataSourceTypeCustomCapabilitiesResponse");
    private final static QName _RefreshResourceCache_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "refreshResourceCache");
    private final static QName _GetIntrospectionAttributeDefs_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getIntrospectionAttributeDefs");
    private final static QName _GetIntrospectedResourceIdsResult_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getIntrospectedResourceIdsResult");
    private final static QName _GetDataSourceReintrospectResultResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getDataSourceReintrospectResultResponse");
    private final static QName _UnlockResourcesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "unlockResourcesResponse");
    private final static QName _ClearResourceStatistics_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "clearResourceStatistics");
    private final static QName _GetIntrospectableResourceIdsTaskResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getIntrospectableResourceIdsTaskResponse");
    private final static QName _GetChildResourcesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getChildResourcesResponse");
    private final static QName _GetIntrospectedResourceIdsTaskResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getIntrospectedResourceIdsTaskResponse");
    private final static QName _IntrospectResourcesResultResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "introspectResourcesResultResponse");
    private final static QName _TestDataSourceConnection_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "testDataSourceConnection");
    private final static QName _CreateLink_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "createLink");
    private final static QName _LockResourceResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "lockResourceResponse");
    private final static QName _UpdateResources_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateResources");
    private final static QName _GetUsedDSResourcesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getUsedDSResourcesResponse");
    private final static QName _UpdateXQueryProcedureResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateXQueryProcedureResponse");
    private final static QName _GetResourceUpdates_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getResourceUpdates");
    private final static QName _GetIntrospectionAttributes_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getIntrospectionAttributes");
    private final static QName _UpdateConnectorResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateConnectorResponse");
    private final static QName _GetDataSourceTypeAttributeDefsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getDataSourceTypeAttributeDefsResponse");
    private final static QName _DestroyResourceResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "destroyResourceResponse");
    private final static QName _GetConnectorGroupNames_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getConnectorGroupNames");
    private final static QName _GetDependentResources_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getDependentResources");
    private final static QName _UpdateDefinitionSet_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateDefinitionSet");
    private final static QName _DestroyResource_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "destroyResource");
    private final static QName _ClearResourceStatisticsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "clearResourceStatisticsResponse");
    private final static QName _UpdateDataSourceChildInfosResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateDataSourceChildInfosResponse");
    private final static QName _UpdateXQueryProcedure_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateXQueryProcedure");
    private final static QName _GetDataSourceReintrospectResult_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getDataSourceReintrospectResult");
    private final static QName _ReintrospectDataSourceResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "reintrospectDataSourceResponse");
    private final static QName _UpdateTransformProcedure_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateTransformProcedure");
    private final static QName _RebindResourcesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "rebindResourcesResponse");
    private final static QName _GetDependentResourcesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getDependentResourcesResponse");
    private final static QName _DestroyConnector_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "destroyConnector");
    private final static QName _UnlockResources_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "unlockResources");
    private final static QName _UpdateXsltTransformProcedureResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateXsltTransformProcedureResponse");
    private final static QName _UpdateTrigger_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateTrigger");
    private final static QName _CreateLinksRecursively_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "createLinksRecursively");
    private final static QName _GetUsedResources_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getUsedResources");
    private final static QName _GetResourceStatsSummaryResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getResourceStatsSummaryResponse");
    private final static QName _IntrospectResourcesResult_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "introspectResourcesResult");
    private final static QName _MoveResourcesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "moveResourcesResponse");
    private final static QName _GetIntrospectableResourceIdsTask_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getIntrospectableResourceIdsTask");
    private final static QName _GetParentResource_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getParentResource");
    private final static QName _CancelDataSourceReintrospectResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "cancelDataSourceReintrospectResponse");
    private final static QName _GetDataSourceAttributeDefsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getDataSourceAttributeDefsResponse");
    private final static QName _UpdateDataSourcePort_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateDataSourcePort");
    private final static QName _ChangeResourceOwner_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "changeResourceOwner");
    private final static QName _UpdateLinkResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateLinkResponse");
    private final static QName _ClearResourceCacheResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "clearResourceCacheResponse");
    private final static QName _CopyResourcePrivileges_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "copyResourcePrivileges");
    private final static QName _CreateConnectorResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "createConnectorResponse");
    private final static QName _CopyResourceResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "copyResourceResponse");
    private final static QName _GetCachedResourceStatisticsConfig_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getCachedResourceStatisticsConfig");
    private final static QName _IntrospectResourcesTaskResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "introspectResourcesTaskResponse");
    private final static QName _RefreshResourceStatisticsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "refreshResourceStatisticsResponse");
    private final static QName _TestDataSourceConnectionResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "testDataSourceConnectionResponse");
    private final static QName _CopyResourcePrivilegesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "copyResourcePrivilegesResponse");
    private final static QName _UpdateXSLTProcedureResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateXSLTProcedureResponse");
    private final static QName _GetExtendableDataSourceTypes_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getExtendableDataSourceTypes");
    private final static QName _UpdateResourcePrivilegesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateResourcePrivilegesResponse");
    private final static QName _GetChildResources_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getChildResources");
    private final static QName _CreateResource_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "createResource");
    private final static QName _UpdateCachedResourceStatisticsConfig_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateCachedResourceStatisticsConfig");
    private final static QName _GetUsedDataSources_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getUsedDataSources");
    private final static QName _GetResourcePrivilegesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getResourcePrivilegesResponse");
    private final static QName _ResourceExists_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "resourceExists");
    private final static QName _UpdateStreamTransformProcedure_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateStreamTransformProcedure");
    private final static QName _UpdateResourceStatisticsConfig_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateResourceStatisticsConfig");
    private final static QName _UpdateResourceAnnotation_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateResourceAnnotation");
    private final static QName _UpdateCustomDataSourceTypeResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateCustomDataSourceTypeResponse");
    private final static QName _UpdateResourceEnabled_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateResourceEnabled");
    private final static QName _GetUsedDataSourcesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getUsedDataSourcesResponse");
    private final static QName _CancelDataSourceReintrospect_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "cancelDataSourceReintrospect");
    private final static QName _CreateLinksRecursivelyResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "createLinksRecursivelyResponse");
    private final static QName _GetAncestorResources_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getAncestorResources");
    private final static QName _UpdateStreamTransformProcedureResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateStreamTransformProcedureResponse");
    private final static QName _GetLockedResources_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getLockedResources");
    private final static QName _UpdateDataSourceChildInfos_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateDataSourceChildInfos");
    private final static QName _CreateCustomDataSourceTypeResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "createCustomDataSourceTypeResponse");
    private final static QName _GetConnectorsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getConnectorsResponse");
    private final static QName _UpdateXQueryTransformProcedureResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateXQueryTransformProcedureResponse");
    private final static QName _GetMostRecentIntrospectionStatusResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getMostRecentIntrospectionStatusResponse");
    private final static QName _GetIntrospectedResourceIdsTask_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getIntrospectedResourceIdsTask");
    private final static QName _DestroyCustomDataSourceTypeResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "destroyCustomDataSourceTypeResponse");
    private final static QName _UpdateTriggerResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateTriggerResponse");
    private final static QName _GetResourcesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getResourcesResponse");
    private final static QName _UpdateDataSourceStatisticsConfig_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateDataSourceStatisticsConfig");
    private final static QName _DestroyConnectorResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "destroyConnectorResponse");
    private final static QName _GetDataSourceAttributeDefs_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getDataSourceAttributeDefs");
    private final static QName _GetParentDataSourceType_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getParentDataSourceType");
    private final static QName _UpdateResourceCacheConfig_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateResourceCacheConfig");
    private final static QName _RenameResource_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "renameResource");
    private final static QName _UpdateResourcePrivileges_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateResourcePrivileges");
    private final static QName _GetAllResourcesByPath_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getAllResourcesByPath");
    private final static QName _UpdateResourceStatisticsConfigResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateResourceStatisticsConfigResponse");
    private final static QName _UpdateXQueryTransformProcedure_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateXQueryTransformProcedure");
    private final static QName _GetTransformFunctionsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getTransformFunctionsResponse");
    private final static QName _ResourceExistsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "resourceExistsResponse");
    private final static QName _GetResourceCacheConfigResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getResourceCacheConfigResponse");
    private final static QName _UpdateResourceAnnotationResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateResourceAnnotationResponse");
    private final static QName _GetDataSourceChildResourcesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getDataSourceChildResourcesResponse");
    private final static QName _CreateCustomDataSourceType_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "createCustomDataSourceType");
    private final static QName _UpdateResourceCacheConfigResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateResourceCacheConfigResponse");
    private final static QName _CopyResourcesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "copyResourcesResponse");
    private final static QName _GetResourceStatisticsConfigResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getResourceStatisticsConfigResponse");
    private final static QName _GetCachedResourceStatisticsConfigResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getCachedResourceStatisticsConfigResponse");
    private final static QName _GetDataSourceTypeCustomCapabilitiesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getDataSourceTypeCustomCapabilitiesResponse");
    private final static QName _LockResources_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "lockResources");
    private final static QName _CreateConnector_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "createConnector");
    private final static QName _GetIntrospectableResourceIdsResultResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getIntrospectableResourceIdsResultResponse");
    private final static QName _UpdateDataSourcePortResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateDataSourcePortResponse");
    private final static QName _CopyResources_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "copyResources");
    private final static QName _UpdateImplementationContainerResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateImplementationContainerResponse");
    private final static QName _DestroyCustomDataSourceType_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "destroyCustomDataSourceType");
    private final static QName _LockResource_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "lockResource");
    private final static QName _GetIntrospectableResourceIdsResult_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getIntrospectableResourceIdsResult");
    private final static QName _ReintrospectDataSource_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "reintrospectDataSource");
    private final static QName _RefreshResourceStatistics_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "refreshResourceStatistics");
    private final static QName _CreateDataSourceResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "createDataSourceResponse");
    private final static QName _UpdateConnector_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateConnector");
    private final static QName _MoveResource_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "moveResource");
    private final static QName _GetResourceStatisticsConfig_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getResourceStatisticsConfig");
    private final static QName _UpdateTransformProcedureResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateTransformProcedureResponse");
    private final static QName _GetParentResourceResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getParentResourceResponse");
    private final static QName _IntrospectResourcesTask_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "introspectResourcesTask");
    private final static QName _UpdateXsltTransformProcedure_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateXsltTransformProcedure");
    private final static QName _GetDataSourceStatisticsConfig_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getDataSourceStatisticsConfig");
    private final static QName _ClearResourceCache_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "clearResourceCache");
    private final static QName _UpdateDefinitionSetResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateDefinitionSetResponse");
    private final static QName _GetLockedResourcesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getLockedResourcesResponse");
    private final static QName _GetTransformFunctions_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getTransformFunctions");
    private final static QName _GetDataSourceTypeCustomCapabilities_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getDataSourceTypeCustomCapabilities");
    private final static QName _UpdateSqlScriptProcedureResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateSqlScriptProcedureResponse");
    private final static QName _GetResourceResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getResourceResponse");
    private final static QName _UpdateXSLTProcedure_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateXSLTProcedure");
    private final static QName _RenameResourceResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "renameResourceResponse");
    private final static QName _CancelResourceStatistics_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "cancelResourceStatistics");
    private final static QName _GetResourceUpdatesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getResourceUpdatesResponse");
    private final static QName _GetConnectorGroupResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getConnectorGroupResponse");
    private final static QName _DestroyResourcesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "destroyResourcesResponse");
    private final static QName _GetUsedResourcesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getUsedResourcesResponse");
    private final static QName _GetResourceStatsSummary_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getResourceStatsSummary");
    private final static QName _RebindResources_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "rebindResources");
    private final static QName _GetExtendableDataSourceTypesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getExtendableDataSourceTypesResponse");
    private final static QName _UpdateDataSourceChildInfosWithFilterResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateDataSourceChildInfosWithFilterResponse");
    private final static QName _GetUsedDSResources_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getUsedDSResources");
    private final static QName _UpdateDataSource_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateDataSource");
    private final static QName _UpdateDataServicePort_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateDataServicePort");
    private final static QName _UpdateDataSourceTypeCustomCapabilities_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateDataSourceTypeCustomCapabilities");
    private final static QName _GetIntrospectedResourceIdsResultResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getIntrospectedResourceIdsResultResponse");
    private final static QName _GetConnectors_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getConnectors");
    private final static QName _GetAllResourcesByPathResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getAllResourcesByPathResponse");
    private final static QName _MoveResourceResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "moveResourceResponse");
    private final static QName _GetConnectorGroupNamesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getConnectorGroupNamesResponse");
    private final static QName _GetConnectorGroup_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getConnectorGroup");
    private final static QName _UpdateBasicTransformProcedure_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateBasicTransformProcedure");
    private final static QName _GetDataSourceChildResources_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getDataSourceChildResources");
    private final static QName _CreateResourceResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "createResourceResponse");
    private final static QName _GetAncestorResourcesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getAncestorResourcesResponse");
    private final static QName _GetIntrospectionAttributesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getIntrospectionAttributesResponse");
    private final static QName _DestroyResources_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "destroyResources");
    private final static QName _UnlockResourceResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "unlockResourceResponse");
    private final static QName _UpdateCachedResourceStatisticsConfigResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateCachedResourceStatisticsConfigResponse");
    private final static QName _ClearIntrospectableResourceIdCache_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "clearIntrospectableResourceIdCache");
    private final static QName _ChangeResourceOwnerResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "changeResourceOwnerResponse");
    private final static QName _UpdateDataSourceChildInfosWithFilter_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateDataSourceChildInfosWithFilter");
    private final static QName _UpdateSqlTableResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateSqlTableResponse");
    private final static QName _UpdateResourcesResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateResourcesResponse");
    private final static QName _GetIntrospectionAttributeDefsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "getIntrospectionAttributeDefsResponse");
    private final static QName _UpdateSqlScriptProcedure_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateSqlScriptProcedure");
    private final static QName _UpdateExternalSqlProcedure_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateExternalSqlProcedure");
    private final static QName _UpdateImplementationContainer_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "updateImplementationContainer");
    private final static QName _TriggerResourceActionType_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "actionType");
    private final static QName _TriggerResourceConditionAttributes_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "conditionAttributes");
    private final static QName _TriggerResourceConditionSchedule_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "conditionSchedule");
    private final static QName _TriggerResourceMaxEventsQueued_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "maxEventsQueued");
    private final static QName _TriggerResourceConditionType_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "conditionType");
    private final static QName _TriggerResourceActionAttributes_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "actionAttributes");
    private final static QName _TriggerResourceEnabled_QNAME = new QName("http://www.compositesw.com/services/system/admin/resource", "enabled");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.compositesw.services.system.admin.resource
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetExtendableDataSourceTypesResponse.DataSourceTypes }
     * 
     */
    public GetExtendableDataSourceTypesResponse.DataSourceTypes createGetExtendableDataSourceTypesResponseDataSourceTypes() {
        return new GetExtendableDataSourceTypesResponse.DataSourceTypes();
    }

    /**
     * Create an instance of {@link Schedule }
     * 
     */
    public Schedule createSchedule() {
        return new Schedule();
    }

    /**
     * Create an instance of {@link GetUsedDSResourcesRequest }
     * 
     */
    public GetUsedDSResourcesRequest createGetUsedDSResourcesRequest() {
        return new GetUsedDSResourcesRequest();
    }

    /**
     * Create an instance of {@link UpdateSqlTableResponse }
     * 
     */
    public UpdateSqlTableResponse createUpdateSqlTableResponse() {
        return new UpdateSqlTableResponse();
    }

    /**
     * Create an instance of {@link IntrospectResourcesTaskRequest }
     * 
     */
    public IntrospectResourcesTaskRequest createIntrospectResourcesTaskRequest() {
        return new IntrospectResourcesTaskRequest();
    }

    /**
     * Create an instance of {@link UpdateResourceEnabledRequest }
     * 
     */
    public UpdateResourceEnabledRequest createUpdateResourceEnabledRequest() {
        return new UpdateResourceEnabledRequest();
    }

    /**
     * Create an instance of {@link RenameResourceResponse }
     * 
     */
    public RenameResourceResponse createRenameResourceResponse() {
        return new RenameResourceResponse();
    }

    /**
     * Create an instance of {@link MultiPathTypeRequest.Entries }
     * 
     */
    public MultiPathTypeRequest.Entries createMultiPathTypeRequestEntries() {
        return new MultiPathTypeRequest.Entries();
    }

    /**
     * Create an instance of {@link UpdateXsltTransformProcedureResponse }
     * 
     */
    public UpdateXsltTransformProcedureResponse createUpdateXsltTransformProcedureResponse() {
        return new UpdateXsltTransformProcedureResponse();
    }

    /**
     * Create an instance of {@link UpdateDataSourceChildInfosResponse }
     * 
     */
    public UpdateDataSourceChildInfosResponse createUpdateDataSourceChildInfosResponse() {
        return new UpdateDataSourceChildInfosResponse();
    }

    /**
     * Create an instance of {@link UpdateExternalSqlProcedureRequest }
     * 
     */
    public UpdateExternalSqlProcedureRequest createUpdateExternalSqlProcedureRequest() {
        return new UpdateExternalSqlProcedureRequest();
    }

    /**
     * Create an instance of {@link IntrospectionPlan }
     * 
     */
    public IntrospectionPlan createIntrospectionPlan() {
        return new IntrospectionPlan();
    }

    /**
     * Create an instance of {@link UpdateResourceAnnotationRequest }
     * 
     */
    public UpdateResourceAnnotationRequest createUpdateResourceAnnotationRequest() {
        return new UpdateResourceAnnotationRequest();
    }

    /**
     * Create an instance of {@link IntrospectionAttributeDefs }
     * 
     */
    public IntrospectionAttributeDefs createIntrospectionAttributeDefs() {
        return new IntrospectionAttributeDefs();
    }

    /**
     * Create an instance of {@link UpdateDefinitionSetResponse }
     * 
     */
    public UpdateDefinitionSetResponse createUpdateDefinitionSetResponse() {
        return new UpdateDefinitionSetResponse();
    }

    /**
     * Create an instance of {@link UnlockResourceRequest }
     * 
     */
    public UnlockResourceRequest createUnlockResourceRequest() {
        return new UnlockResourceRequest();
    }

    /**
     * Create an instance of {@link ClearIntrospectableResourceIdCacheResponse }
     * 
     */
    public ClearIntrospectableResourceIdCacheResponse createClearIntrospectableResourceIdCacheResponse() {
        return new ClearIntrospectableResourceIdCacheResponse();
    }

    /**
     * Create an instance of {@link ResourceExistsResponse }
     * 
     */
    public ResourceExistsResponse createResourceExistsResponse() {
        return new ResourceExistsResponse();
    }

    /**
     * Create an instance of {@link UpdateImplementationContainerRequest }
     * 
     */
    public UpdateImplementationContainerRequest createUpdateImplementationContainerRequest() {
        return new UpdateImplementationContainerRequest();
    }

    /**
     * Create an instance of {@link UpdateSqlScriptProcedureResponse }
     * 
     */
    public UpdateSqlScriptProcedureResponse createUpdateSqlScriptProcedureResponse() {
        return new UpdateSqlScriptProcedureResponse();
    }

    /**
     * Create an instance of {@link TestDataSourceConnectionResponse }
     * 
     */
    public TestDataSourceConnectionResponse createTestDataSourceConnectionResponse() {
        return new TestDataSourceConnectionResponse();
    }

    /**
     * Create an instance of {@link UpdateResourceStatisticsConfigResponse }
     * 
     */
    public UpdateResourceStatisticsConfigResponse createUpdateResourceStatisticsConfigResponse() {
        return new UpdateResourceStatisticsConfigResponse();
    }

    /**
     * Create an instance of {@link IntrospectionChangeEntryList }
     * 
     */
    public IntrospectionChangeEntryList createIntrospectionChangeEntryList() {
        return new IntrospectionChangeEntryList();
    }

    /**
     * Create an instance of {@link LineageResourceList }
     * 
     */
    public LineageResourceList createLineageResourceList() {
        return new LineageResourceList();
    }

    /**
     * Create an instance of {@link ReintrospectResponse }
     * 
     */
    public ReintrospectResponse createReintrospectResponse() {
        return new ReintrospectResponse();
    }

    /**
     * Create an instance of {@link GetResourceStatsSummaryRequest }
     * 
     */
    public GetResourceStatsSummaryRequest createGetResourceStatsSummaryRequest() {
        return new GetResourceStatsSummaryRequest();
    }

    /**
     * Create an instance of {@link RebindRuleList }
     * 
     */
    public RebindRuleList createRebindRuleList() {
        return new RebindRuleList();
    }

    /**
     * Create an instance of {@link RefreshResourceStatisticsRequest }
     * 
     */
    public RefreshResourceStatisticsRequest createRefreshResourceStatisticsRequest() {
        return new RefreshResourceStatisticsRequest();
    }

    /**
     * Create an instance of {@link PathTypePair }
     * 
     */
    public PathTypePair createPathTypePair() {
        return new PathTypePair();
    }

    /**
     * Create an instance of {@link LockResourcesRequest }
     * 
     */
    public LockResourcesRequest createLockResourcesRequest() {
        return new LockResourcesRequest();
    }

    /**
     * Create an instance of {@link CacheConfig }
     * 
     */
    public CacheConfig createCacheConfig() {
        return new CacheConfig();
    }

    /**
     * Create an instance of {@link IntrospectionPlanEntry }
     * 
     */
    public IntrospectionPlanEntry createIntrospectionPlanEntry() {
        return new IntrospectionPlanEntry();
    }

    /**
     * Create an instance of {@link UpdateTransformProcedureResponse }
     * 
     */
    public UpdateTransformProcedureResponse createUpdateTransformProcedureResponse() {
        return new UpdateTransformProcedureResponse();
    }

    /**
     * Create an instance of {@link IntrospectionPlanEntries }
     * 
     */
    public IntrospectionPlanEntries createIntrospectionPlanEntries() {
        return new IntrospectionPlanEntries();
    }

    /**
     * Create an instance of {@link TreeResource }
     * 
     */
    public TreeResource createTreeResource() {
        return new TreeResource();
    }

    /**
     * Create an instance of {@link UpdateConnectorRequest }
     * 
     */
    public UpdateConnectorRequest createUpdateConnectorRequest() {
        return new UpdateConnectorRequest();
    }

    /**
     * Create an instance of {@link CreateConnectorResponse }
     * 
     */
    public CreateConnectorResponse createCreateConnectorResponse() {
        return new CreateConnectorResponse();
    }

    /**
     * Create an instance of {@link CancelResourceStatisticsResponse }
     * 
     */
    public CancelResourceStatisticsResponse createCancelResourceStatisticsResponse() {
        return new CancelResourceStatisticsResponse();
    }

    /**
     * Create an instance of {@link ParameterList }
     * 
     */
    public ParameterList createParameterList() {
        return new ParameterList();
    }

    /**
     * Create an instance of {@link ResourcesResponse }
     * 
     */
    public ResourcesResponse createResourcesResponse() {
        return new ResourcesResponse();
    }

    /**
     * Create an instance of {@link RefreshResourceCacheRequest }
     * 
     */
    public RefreshResourceCacheRequest createRefreshResourceCacheRequest() {
        return new RefreshResourceCacheRequest();
    }

    /**
     * Create an instance of {@link LineageResource }
     * 
     */
    public LineageResource createLineageResource() {
        return new LineageResource();
    }

    /**
     * Create an instance of {@link Project }
     * 
     */
    public Project createProject() {
        return new Project();
    }

    /**
     * Create an instance of {@link IntrospectResourcesTaskResponse }
     * 
     */
    public IntrospectResourcesTaskResponse createIntrospectResourcesTaskResponse() {
        return new IntrospectResourcesTaskResponse();
    }

    /**
     * Create an instance of {@link UpdateResourceCacheConfigRequest }
     * 
     */
    public UpdateResourceCacheConfigRequest createUpdateResourceCacheConfigRequest() {
        return new UpdateResourceCacheConfigRequest();
    }

    /**
     * Create an instance of {@link GetIntrospectionAttributesResponse }
     * 
     */
    public GetIntrospectionAttributesResponse createGetIntrospectionAttributesResponse() {
        return new GetIntrospectionAttributesResponse();
    }

    /**
     * Create an instance of {@link UpdateDataSourceRequest }
     * 
     */
    public UpdateDataSourceRequest createUpdateDataSourceRequest() {
        return new UpdateDataSourceRequest();
    }

    /**
     * Create an instance of {@link StatsColumn }
     * 
     */
    public StatsColumn createStatsColumn() {
        return new StatsColumn();
    }

    /**
     * Create an instance of {@link Resource }
     * 
     */
    public Resource createResource() {
        return new Resource();
    }

    /**
     * Create an instance of {@link PrivilegeEntry.Privileges }
     * 
     */
    public PrivilegeEntry.Privileges createPrivilegeEntryPrivileges() {
        return new PrivilegeEntry.Privileges();
    }

    /**
     * Create an instance of {@link DataSourceStatisticsConfig }
     * 
     */
    public DataSourceStatisticsConfig createDataSourceStatisticsConfig() {
        return new DataSourceStatisticsConfig();
    }

    /**
     * Create an instance of {@link DestroyCustomDataSourceTypeResponse }
     * 
     */
    public DestroyCustomDataSourceTypeResponse createDestroyCustomDataSourceTypeResponse() {
        return new DestroyCustomDataSourceTypeResponse();
    }

    /**
     * Create an instance of {@link CopyResourceResponse }
     * 
     */
    public CopyResourceResponse createCopyResourceResponse() {
        return new CopyResourceResponse();
    }

    /**
     * Create an instance of {@link PortBindingProperties }
     * 
     */
    public PortBindingProperties createPortBindingProperties() {
        return new PortBindingProperties();
    }

    /**
     * Create an instance of {@link ResourceBundle }
     * 
     */
    public ResourceBundle createResourceBundle() {
        return new ResourceBundle();
    }

    /**
     * Create an instance of {@link CacheConfig.Refresh }
     * 
     */
    public CacheConfig.Refresh createCacheConfigRefresh() {
        return new CacheConfig.Refresh();
    }

    /**
     * Create an instance of {@link GetParentResourceRequest }
     * 
     */
    public GetParentResourceRequest createGetParentResourceRequest() {
        return new GetParentResourceRequest();
    }

    /**
     * Create an instance of {@link GetDataSourceReintrospectResultRequest }
     * 
     */
    public GetDataSourceReintrospectResultRequest createGetDataSourceReintrospectResultRequest() {
        return new GetDataSourceReintrospectResultRequest();
    }

    /**
     * Create an instance of {@link WsdlDocumentSet.ImportedDocuments }
     * 
     */
    public WsdlDocumentSet.ImportedDocuments createWsdlDocumentSetImportedDocuments() {
        return new WsdlDocumentSet.ImportedDocuments();
    }

    /**
     * Create an instance of {@link ResourceIdList }
     * 
     */
    public ResourceIdList createResourceIdList() {
        return new ResourceIdList();
    }

    /**
     * Create an instance of {@link GetChildResourcesResponse }
     * 
     */
    public GetChildResourcesResponse createGetChildResourcesResponse() {
        return new GetChildResourcesResponse();
    }

    /**
     * Create an instance of {@link GetIntrospectedResourceIdsTaskResponse }
     * 
     */
    public GetIntrospectedResourceIdsTaskResponse createGetIntrospectedResourceIdsTaskResponse() {
        return new GetIntrospectedResourceIdsTaskResponse();
    }

    /**
     * Create an instance of {@link GetAllResourcesByPathRequest }
     * 
     */
    public GetAllResourcesByPathRequest createGetAllResourcesByPathRequest() {
        return new GetAllResourcesByPathRequest();
    }

    /**
     * Create an instance of {@link Privilege }
     * 
     */
    public Privilege createPrivilege() {
        return new Privilege();
    }

    /**
     * Create an instance of {@link DefinitionSetSourceList }
     * 
     */
    public DefinitionSetSourceList createDefinitionSetSourceList() {
        return new DefinitionSetSourceList();
    }

    /**
     * Create an instance of {@link StatsColumnList }
     * 
     */
    public StatsColumnList createStatsColumnList() {
        return new StatsColumnList();
    }

    /**
     * Create an instance of {@link GetExtendableDataSourceTypesRequest }
     * 
     */
    public GetExtendableDataSourceTypesRequest createGetExtendableDataSourceTypesRequest() {
        return new GetExtendableDataSourceTypesRequest();
    }

    /**
     * Create an instance of {@link PortOperationPropertyList }
     * 
     */
    public PortOperationPropertyList createPortOperationPropertyList() {
        return new PortOperationPropertyList();
    }

    /**
     * Create an instance of {@link GetTransformFunctionsRequest }
     * 
     */
    public GetTransformFunctionsRequest createGetTransformFunctionsRequest() {
        return new GetTransformFunctionsRequest();
    }

    /**
     * Create an instance of {@link MoveResourceResponse }
     * 
     */
    public MoveResourceResponse createMoveResourceResponse() {
        return new MoveResourceResponse();
    }

    /**
     * Create an instance of {@link RenameResourceRequest }
     * 
     */
    public RenameResourceRequest createRenameResourceRequest() {
        return new RenameResourceRequest();
    }

    /**
     * Create an instance of {@link RebindRule }
     * 
     */
    public RebindRule createRebindRule() {
        return new RebindRule();
    }

    /**
     * Create an instance of {@link UpdateStreamTransformProcedureResponse }
     * 
     */
    public UpdateStreamTransformProcedureResponse createUpdateStreamTransformProcedureResponse() {
        return new UpdateStreamTransformProcedureResponse();
    }

    /**
     * Create an instance of {@link CreateConnectorRequest }
     * 
     */
    public CreateConnectorRequest createCreateConnectorRequest() {
        return new CreateConnectorRequest();
    }

    /**
     * Create an instance of {@link LinkableResourceId }
     * 
     */
    public LinkableResourceId createLinkableResourceId() {
        return new LinkableResourceId();
    }

    /**
     * Create an instance of {@link CacheConfig.Storage }
     * 
     */
    public CacheConfig.Storage createCacheConfigStorage() {
        return new CacheConfig.Storage();
    }

    /**
     * Create an instance of {@link GetResourcePrivilegesRequest.Entries }
     * 
     */
    public GetResourcePrivilegesRequest.Entries createGetResourcePrivilegesRequestEntries() {
        return new GetResourcePrivilegesRequest.Entries();
    }

    /**
     * Create an instance of {@link PathDetailRequest }
     * 
     */
    public PathDetailRequest createPathDetailRequest() {
        return new PathDetailRequest();
    }

    /**
     * Create an instance of {@link ChangeResourceOwnerRequest }
     * 
     */
    public ChangeResourceOwnerRequest createChangeResourceOwnerRequest() {
        return new ChangeResourceOwnerRequest();
    }

    /**
     * Create an instance of {@link UpdateResourcePrivilegesRequest.PrivilegeEntries }
     * 
     */
    public UpdateResourcePrivilegesRequest.PrivilegeEntries createUpdateResourcePrivilegesRequestPrivilegeEntries() {
        return new UpdateResourcePrivilegesRequest.PrivilegeEntries();
    }

    /**
     * Create an instance of {@link DestroyResourcesRequest }
     * 
     */
    public DestroyResourcesRequest createDestroyResourcesRequest() {
        return new DestroyResourcesRequest();
    }

    /**
     * Create an instance of {@link ResourceStatisticsConfig }
     * 
     */
    public ResourceStatisticsConfig createResourceStatisticsConfig() {
        return new ResourceStatisticsConfig();
    }

    /**
     * Create an instance of {@link GetAllResourcesByPathResponse }
     * 
     */
    public GetAllResourcesByPathResponse createGetAllResourcesByPathResponse() {
        return new GetAllResourcesByPathResponse();
    }

    /**
     * Create an instance of {@link PathTypeRequest }
     * 
     */
    public PathTypeRequest createPathTypeRequest() {
        return new PathTypeRequest();
    }

    /**
     * Create an instance of {@link WsdlDocumentSet }
     * 
     */
    public WsdlDocumentSet createWsdlDocumentSet() {
        return new WsdlDocumentSet();
    }

    /**
     * Create an instance of {@link CopyResourcePrivilegesRequest }
     * 
     */
    public CopyResourcePrivilegesRequest createCopyResourcePrivilegesRequest() {
        return new CopyResourcePrivilegesRequest();
    }

    /**
     * Create an instance of {@link ReintrospectDataSourceResponse }
     * 
     */
    public ReintrospectDataSourceResponse createReintrospectDataSourceResponse() {
        return new ReintrospectDataSourceResponse();
    }

    /**
     * Create an instance of {@link ConnectorList }
     * 
     */
    public ConnectorList createConnectorList() {
        return new ConnectorList();
    }

    /**
     * Create an instance of {@link CreateLinkResponse }
     * 
     */
    public CreateLinkResponse createCreateLinkResponse() {
        return new CreateLinkResponse();
    }

    /**
     * Create an instance of {@link PathTypeDetailRequest }
     * 
     */
    public PathTypeDetailRequest createPathTypeDetailRequest() {
        return new PathTypeDetailRequest();
    }

    /**
     * Create an instance of {@link DataSource }
     * 
     */
    public DataSource createDataSource() {
        return new DataSource();
    }

    /**
     * Create an instance of {@link UpdateDataSourceStatisticsConfigRequest }
     * 
     */
    public UpdateDataSourceStatisticsConfigRequest createUpdateDataSourceStatisticsConfigRequest() {
        return new UpdateDataSourceStatisticsConfigRequest();
    }

    /**
     * Create an instance of {@link ResourceUpdateInfoList }
     * 
     */
    public ResourceUpdateInfoList createResourceUpdateInfoList() {
        return new ResourceUpdateInfoList();
    }

    /**
     * Create an instance of {@link TargetPathTypePair }
     * 
     */
    public TargetPathTypePair createTargetPathTypePair() {
        return new TargetPathTypePair();
    }

    /**
     * Create an instance of {@link ChangeResourceOwnerResponse }
     * 
     */
    public ChangeResourceOwnerResponse createChangeResourceOwnerResponse() {
        return new ChangeResourceOwnerResponse();
    }

    /**
     * Create an instance of {@link DestroyCustomDataSourceTypeRequest }
     * 
     */
    public DestroyCustomDataSourceTypeRequest createDestroyCustomDataSourceTypeRequest() {
        return new DestroyCustomDataSourceTypeRequest();
    }

    /**
     * Create an instance of {@link IntrospectResourcesResultResponse }
     * 
     */
    public IntrospectResourcesResultResponse createIntrospectResourcesResultResponse() {
        return new IntrospectResourcesResultResponse();
    }

    /**
     * Create an instance of {@link GetResourceStatisticsConfigRequest }
     * 
     */
    public GetResourceStatisticsConfigRequest createGetResourceStatisticsConfigRequest() {
        return new GetResourceStatisticsConfigRequest();
    }

    /**
     * Create an instance of {@link CreateDataSourceRequest }
     * 
     */
    public CreateDataSourceRequest createCreateDataSourceRequest() {
        return new CreateDataSourceRequest();
    }

    /**
     * Create an instance of {@link LinkResource }
     * 
     */
    public LinkResource createLinkResource() {
        return new LinkResource();
    }

    /**
     * Create an instance of {@link GetDataSourceTypeAttributeDefsResponse }
     * 
     */
    public GetDataSourceTypeAttributeDefsResponse createGetDataSourceTypeAttributeDefsResponse() {
        return new GetDataSourceTypeAttributeDefsResponse();
    }

    /**
     * Create an instance of {@link GetIntrospectionAttributeDefsResponse }
     * 
     */
    public GetIntrospectionAttributeDefsResponse createGetIntrospectionAttributeDefsResponse() {
        return new GetIntrospectionAttributeDefsResponse();
    }

    /**
     * Create an instance of {@link CopyResourcePrivilegesResponse }
     * 
     */
    public CopyResourcePrivilegesResponse createCopyResourcePrivilegesResponse() {
        return new CopyResourcePrivilegesResponse();
    }

    /**
     * Create an instance of {@link UpdateXQueryProcedureRequest }
     * 
     */
    public UpdateXQueryProcedureRequest createUpdateXQueryProcedureRequest() {
        return new UpdateXQueryProcedureRequest();
    }

    /**
     * Create an instance of {@link MultiPathTypeDetailRequest }
     * 
     */
    public MultiPathTypeDetailRequest createMultiPathTypeDetailRequest() {
        return new MultiPathTypeDetailRequest();
    }

    /**
     * Create an instance of {@link GetResourceUpdatesResponse }
     * 
     */
    public GetResourceUpdatesResponse createGetResourceUpdatesResponse() {
        return new GetResourceUpdatesResponse();
    }

    /**
     * Create an instance of {@link GetDependentResourcesResponse }
     * 
     */
    public GetDependentResourcesResponse createGetDependentResourcesResponse() {
        return new GetDependentResourcesResponse();
    }

    /**
     * Create an instance of {@link DataSourceList }
     * 
     */
    public DataSourceList createDataSourceList() {
        return new DataSourceList();
    }

    /**
     * Create an instance of {@link CachedStatisticsConfig }
     * 
     */
    public CachedStatisticsConfig createCachedStatisticsConfig() {
        return new CachedStatisticsConfig();
    }

    /**
     * Create an instance of {@link CopyResourceRequest }
     * 
     */
    public CopyResourceRequest createCopyResourceRequest() {
        return new CopyResourceRequest();
    }

    /**
     * Create an instance of {@link CopyResourcesResponse }
     * 
     */
    public CopyResourcesResponse createCopyResourcesResponse() {
        return new CopyResourcesResponse();
    }

    /**
     * Create an instance of {@link DefinitionList }
     * 
     */
    public DefinitionList createDefinitionList() {
        return new DefinitionList();
    }

    /**
     * Create an instance of {@link GetIntrospectableResourceIdsResultResponse }
     * 
     */
    public GetIntrospectableResourceIdsResultResponse createGetIntrospectableResourceIdsResultResponse() {
        return new GetIntrospectableResourceIdsResultResponse();
    }

    /**
     * Create an instance of {@link ClearResourceCacheRequest }
     * 
     */
    public ClearResourceCacheRequest createClearResourceCacheRequest() {
        return new ClearResourceCacheRequest();
    }

    /**
     * Create an instance of {@link CreateResourceRequest }
     * 
     */
    public CreateResourceRequest createCreateResourceRequest() {
        return new CreateResourceRequest();
    }

    /**
     * Create an instance of {@link UpdateCachedResourceStatisticsConfigResponse }
     * 
     */
    public UpdateCachedResourceStatisticsConfigResponse createUpdateCachedResourceStatisticsConfigResponse() {
        return new UpdateCachedResourceStatisticsConfigResponse();
    }

    /**
     * Create an instance of {@link ForeignKeyList }
     * 
     */
    public ForeignKeyList createForeignKeyList() {
        return new ForeignKeyList();
    }

    /**
     * Create an instance of {@link GetAncestorResourcesResponse }
     * 
     */
    public GetAncestorResourcesResponse createGetAncestorResourcesResponse() {
        return new GetAncestorResourcesResponse();
    }

    /**
     * Create an instance of {@link UpdateXsltTransformProcedureRequest }
     * 
     */
    public UpdateXsltTransformProcedureRequest createUpdateXsltTransformProcedureRequest() {
        return new UpdateXsltTransformProcedureRequest();
    }

    /**
     * Create an instance of {@link UpdateBasicTransformProcedureRequest }
     * 
     */
    public UpdateBasicTransformProcedureRequest createUpdateBasicTransformProcedureRequest() {
        return new UpdateBasicTransformProcedureRequest();
    }

    /**
     * Create an instance of {@link UpdateDataSourcePortRequest }
     * 
     */
    public UpdateDataSourcePortRequest createUpdateDataSourcePortRequest() {
        return new UpdateDataSourcePortRequest();
    }

    /**
     * Create an instance of {@link GetUsedDataSourcesRequest }
     * 
     */
    public GetUsedDataSourcesRequest createGetUsedDataSourcesRequest() {
        return new GetUsedDataSourcesRequest();
    }

    /**
     * Create an instance of {@link MultiPathTypeRequest }
     * 
     */
    public MultiPathTypeRequest createMultiPathTypeRequest() {
        return new MultiPathTypeRequest();
    }

    /**
     * Create an instance of {@link UpdateResourceEnabledResponse }
     * 
     */
    public UpdateResourceEnabledResponse createUpdateResourceEnabledResponse() {
        return new UpdateResourceEnabledResponse();
    }

    /**
     * Create an instance of {@link MultiPathTypeOptionalDetailRequest }
     * 
     */
    public MultiPathTypeOptionalDetailRequest createMultiPathTypeOptionalDetailRequest() {
        return new MultiPathTypeOptionalDetailRequest();
    }

    /**
     * Create an instance of {@link PrivilegeEntry }
     * 
     */
    public PrivilegeEntry createPrivilegeEntry() {
        return new PrivilegeEntry();
    }

    /**
     * Create an instance of {@link GetResourcesRequest }
     * 
     */
    public GetResourcesRequest createGetResourcesRequest() {
        return new GetResourcesRequest();
    }

    /**
     * Create an instance of {@link GetIntrospectableResourceIdsTaskRequest }
     * 
     */
    public GetIntrospectableResourceIdsTaskRequest createGetIntrospectableResourceIdsTaskRequest() {
        return new GetIntrospectableResourceIdsTaskRequest();
    }

    /**
     * Create an instance of {@link GetConnectorGroupRequest }
     * 
     */
    public GetConnectorGroupRequest createGetConnectorGroupRequest() {
        return new GetConnectorGroupRequest();
    }

    /**
     * Create an instance of {@link GetCachedResourceStatisticsConfigResponse }
     * 
     */
    public GetCachedResourceStatisticsConfigResponse createGetCachedResourceStatisticsConfigResponse() {
        return new GetCachedResourceStatisticsConfigResponse();
    }

    /**
     * Create an instance of {@link ClearResourceCacheResponse }
     * 
     */
    public ClearResourceCacheResponse createClearResourceCacheResponse() {
        return new ClearResourceCacheResponse();
    }

    /**
     * Create an instance of {@link IntrospectionStatus }
     * 
     */
    public IntrospectionStatus createIntrospectionStatus() {
        return new IntrospectionStatus();
    }

    /**
     * Create an instance of {@link DestroyResourceResponse }
     * 
     */
    public DestroyResourceResponse createDestroyResourceResponse() {
        return new DestroyResourceResponse();
    }

    /**
     * Create an instance of {@link GetConnectorsRequest }
     * 
     */
    public GetConnectorsRequest createGetConnectorsRequest() {
        return new GetConnectorsRequest();
    }

    /**
     * Create an instance of {@link Function }
     * 
     */
    public Function createFunction() {
        return new Function();
    }

    /**
     * Create an instance of {@link CreateCustomDataSourceTypeResponse }
     * 
     */
    public CreateCustomDataSourceTypeResponse createCreateCustomDataSourceTypeResponse() {
        return new CreateCustomDataSourceTypeResponse();
    }

    /**
     * Create an instance of {@link Index.Columns }
     * 
     */
    public Index.Columns createIndexColumns() {
        return new Index.Columns();
    }

    /**
     * Create an instance of {@link UpdateStreamTransformProcedureRequest }
     * 
     */
    public UpdateStreamTransformProcedureRequest createUpdateStreamTransformProcedureRequest() {
        return new UpdateStreamTransformProcedureRequest();
    }

    /**
     * Create an instance of {@link RefreshResourceStatisticsResponse }
     * 
     */
    public RefreshResourceStatisticsResponse createRefreshResourceStatisticsResponse() {
        return new RefreshResourceStatisticsResponse();
    }

    /**
     * Create an instance of {@link GetDataSourceChildResourcesResponse }
     * 
     */
    public GetDataSourceChildResourcesResponse createGetDataSourceChildResourcesResponse() {
        return new GetDataSourceChildResourcesResponse();
    }

    /**
     * Create an instance of {@link JmsPortBindingProperties }
     * 
     */
    public JmsPortBindingProperties createJmsPortBindingProperties() {
        return new JmsPortBindingProperties();
    }

    /**
     * Create an instance of {@link GetUsedResourcesRequest }
     * 
     */
    public GetUsedResourcesRequest createGetUsedResourcesRequest() {
        return new GetUsedResourcesRequest();
    }

    /**
     * Create an instance of {@link ChildResource }
     * 
     */
    public ChildResource createChildResource() {
        return new ChildResource();
    }

    /**
     * Create an instance of {@link GetDataSourceTypeCustomCapabilitiesResponse }
     * 
     */
    public GetDataSourceTypeCustomCapabilitiesResponse createGetDataSourceTypeCustomCapabilitiesResponse() {
        return new GetDataSourceTypeCustomCapabilitiesResponse();
    }

    /**
     * Create an instance of {@link GetResourceUpdatesRequest }
     * 
     */
    public GetResourceUpdatesRequest createGetResourceUpdatesRequest() {
        return new GetResourceUpdatesRequest();
    }

    /**
     * Create an instance of {@link CreateLinksRecursivelyResponse }
     * 
     */
    public CreateLinksRecursivelyResponse createCreateLinksRecursivelyResponse() {
        return new CreateLinksRecursivelyResponse();
    }

    /**
     * Create an instance of {@link ChildrenResources }
     * 
     */
    public ChildrenResources createChildrenResources() {
        return new ChildrenResources();
    }

    /**
     * Create an instance of {@link GetIntrospectedResourceIdsResultResponse }
     * 
     */
    public GetIntrospectedResourceIdsResultResponse createGetIntrospectedResourceIdsResultResponse() {
        return new GetIntrospectedResourceIdsResultResponse();
    }

    /**
     * Create an instance of {@link ResourceList }
     * 
     */
    public ResourceList createResourceList() {
        return new ResourceList();
    }

    /**
     * Create an instance of {@link UpdateResourcesResponse }
     * 
     */
    public UpdateResourcesResponse createUpdateResourcesResponse() {
        return new UpdateResourcesResponse();
    }

    /**
     * Create an instance of {@link UpdateTransformProcedureRequest }
     * 
     */
    public UpdateTransformProcedureRequest createUpdateTransformProcedureRequest() {
        return new UpdateTransformProcedureRequest();
    }

    /**
     * Create an instance of {@link GetUsedDataSourcesResponse }
     * 
     */
    public GetUsedDataSourcesResponse createGetUsedDataSourcesResponse() {
        return new GetUsedDataSourcesResponse();
    }

    /**
     * Create an instance of {@link Model }
     * 
     */
    public Model createModel() {
        return new Model();
    }

    /**
     * Create an instance of {@link ClearIntrospectableResourceIdCacheRequest }
     * 
     */
    public ClearIntrospectableResourceIdCacheRequest createClearIntrospectableResourceIdCacheRequest() {
        return new ClearIntrospectableResourceIdCacheRequest();
    }

    /**
     * Create an instance of {@link GetConnectorGroupNamesRequest }
     * 
     */
    public GetConnectorGroupNamesRequest createGetConnectorGroupNamesRequest() {
        return new GetConnectorGroupNamesRequest();
    }

    /**
     * Create an instance of {@link ClearResourceStatisticsRequest }
     * 
     */
    public ClearResourceStatisticsRequest createClearResourceStatisticsRequest() {
        return new ClearResourceStatisticsRequest();
    }

    /**
     * Create an instance of {@link GetResourceRequest }
     * 
     */
    public GetResourceRequest createGetResourceRequest() {
        return new GetResourceRequest();
    }

    /**
     * Create an instance of {@link ConnectorGroupNameList }
     * 
     */
    public ConnectorGroupNameList createConnectorGroupNameList() {
        return new ConnectorGroupNameList();
    }

    /**
     * Create an instance of {@link UpdateResourceAnnotationResponse }
     * 
     */
    public UpdateResourceAnnotationResponse createUpdateResourceAnnotationResponse() {
        return new UpdateResourceAnnotationResponse();
    }

    /**
     * Create an instance of {@link LockResourceResponse }
     * 
     */
    public LockResourceResponse createLockResourceResponse() {
        return new LockResourceResponse();
    }

    /**
     * Create an instance of {@link UnlockResourceResponse }
     * 
     */
    public UnlockResourceResponse createUnlockResourceResponse() {
        return new UnlockResourceResponse();
    }

    /**
     * Create an instance of {@link GetDataSourceAttributeDefsRequest }
     * 
     */
    public GetDataSourceAttributeDefsRequest createGetDataSourceAttributeDefsRequest() {
        return new GetDataSourceAttributeDefsRequest();
    }

    /**
     * Create an instance of {@link ColumnList }
     * 
     */
    public ColumnList createColumnList() {
        return new ColumnList();
    }

    /**
     * Create an instance of {@link GetDataSourceTypesRequest }
     * 
     */
    public GetDataSourceTypesRequest createGetDataSourceTypesRequest() {
        return new GetDataSourceTypesRequest();
    }

    /**
     * Create an instance of {@link LockResourcesResponse }
     * 
     */
    public LockResourcesResponse createLockResourcesResponse() {
        return new LockResourcesResponse();
    }

    /**
     * Create an instance of {@link FunctionList }
     * 
     */
    public FunctionList createFunctionList() {
        return new FunctionList();
    }

    /**
     * Create an instance of {@link CancelDataSourceReintrospectResponse }
     * 
     */
    public CancelDataSourceReintrospectResponse createCancelDataSourceReintrospectResponse() {
        return new CancelDataSourceReintrospectResponse();
    }

    /**
     * Create an instance of {@link UpdateXQueryProcedureResponse }
     * 
     */
    public UpdateXQueryProcedureResponse createUpdateXQueryProcedureResponse() {
        return new UpdateXQueryProcedureResponse();
    }

    /**
     * Create an instance of {@link GetIntrospectableResourceIdsTaskResponse }
     * 
     */
    public GetIntrospectableResourceIdsTaskResponse createGetIntrospectableResourceIdsTaskResponse() {
        return new GetIntrospectableResourceIdsTaskResponse();
    }

    /**
     * Create an instance of {@link GetUsedResourcesResponse }
     * 
     */
    public GetUsedResourcesResponse createGetUsedResourcesResponse() {
        return new GetUsedResourcesResponse();
    }

    /**
     * Create an instance of {@link DataSourceResource }
     * 
     */
    public DataSourceResource createDataSourceResource() {
        return new DataSourceResource();
    }

    /**
     * Create an instance of {@link UpdateDataSourcePortResponse }
     * 
     */
    public UpdateDataSourcePortResponse createUpdateDataSourcePortResponse() {
        return new UpdateDataSourcePortResponse();
    }

    /**
     * Create an instance of {@link DestroyResourcesResponse }
     * 
     */
    public DestroyResourcesResponse createDestroyResourcesResponse() {
        return new DestroyResourcesResponse();
    }

    /**
     * Create an instance of {@link GetParentDataSourceTypeResponse }
     * 
     */
    public GetParentDataSourceTypeResponse createGetParentDataSourceTypeResponse() {
        return new GetParentDataSourceTypeResponse();
    }

    /**
     * Create an instance of {@link GetExtendableDataSourceTypesResponse }
     * 
     */
    public GetExtendableDataSourceTypesResponse createGetExtendableDataSourceTypesResponse() {
        return new GetExtendableDataSourceTypesResponse();
    }

    /**
     * Create an instance of {@link GetConnectorsResponse }
     * 
     */
    public GetConnectorsResponse createGetConnectorsResponse() {
        return new GetConnectorsResponse();
    }

    /**
     * Create an instance of {@link UpdateDataSourceStatisticsConfigResponse }
     * 
     */
    public UpdateDataSourceStatisticsConfigResponse createUpdateDataSourceStatisticsConfigResponse() {
        return new UpdateDataSourceStatisticsConfigResponse();
    }

    /**
     * Create an instance of {@link GetIntrospectionAttributeDefsRequest }
     * 
     */
    public GetIntrospectionAttributeDefsRequest createGetIntrospectionAttributeDefsRequest() {
        return new GetIntrospectionAttributeDefsRequest();
    }

    /**
     * Create an instance of {@link PathTypeOrColumnPair }
     * 
     */
    public PathTypeOrColumnPair createPathTypeOrColumnPair() {
        return new PathTypeOrColumnPair();
    }

    /**
     * Create an instance of {@link RefreshResourceCacheResponse }
     * 
     */
    public RefreshResourceCacheResponse createRefreshResourceCacheResponse() {
        return new RefreshResourceCacheResponse();
    }

    /**
     * Create an instance of {@link UpdateBasePortRequest }
     * 
     */
    public UpdateBasePortRequest createUpdateBasePortRequest() {
        return new UpdateBasePortRequest();
    }

    /**
     * Create an instance of {@link CreateLinkRequest }
     * 
     */
    public CreateLinkRequest createCreateLinkRequest() {
        return new CreateLinkRequest();
    }

    /**
     * Create an instance of {@link CreateCustomDataSourceTypeRequest }
     * 
     */
    public CreateCustomDataSourceTypeRequest createCreateCustomDataSourceTypeRequest() {
        return new CreateCustomDataSourceTypeRequest();
    }

    /**
     * Create an instance of {@link MoveResourceRequest }
     * 
     */
    public MoveResourceRequest createMoveResourceRequest() {
        return new MoveResourceRequest();
    }

    /**
     * Create an instance of {@link GetResourceCacheConfigResponse }
     * 
     */
    public GetResourceCacheConfigResponse createGetResourceCacheConfigResponse() {
        return new GetResourceCacheConfigResponse();
    }

    /**
     * Create an instance of {@link JmsConnector }
     * 
     */
    public JmsConnector createJmsConnector() {
        return new JmsConnector();
    }

    /**
     * Create an instance of {@link Column }
     * 
     */
    public Column createColumn() {
        return new Column();
    }

    /**
     * Create an instance of {@link Definition }
     * 
     */
    public Definition createDefinition() {
        return new Definition();
    }

    /**
     * Create an instance of {@link IndexList }
     * 
     */
    public IndexList createIndexList() {
        return new IndexList();
    }

    /**
     * Create an instance of {@link DefinitionSetResource }
     * 
     */
    public DefinitionSetResource createDefinitionSetResource() {
        return new DefinitionSetResource();
    }

    /**
     * Create an instance of {@link GetDataSourceReintrospectResultResponse }
     * 
     */
    public GetDataSourceReintrospectResultResponse createGetDataSourceReintrospectResultResponse() {
        return new GetDataSourceReintrospectResultResponse();
    }

    /**
     * Create an instance of {@link PathTypeSubtype }
     * 
     */
    public PathTypeSubtype createPathTypeSubtype() {
        return new PathTypeSubtype();
    }

    /**
     * Create an instance of {@link XmlSchemaDocumentSet }
     * 
     */
    public XmlSchemaDocumentSet createXmlSchemaDocumentSet() {
        return new XmlSchemaDocumentSet();
    }

    /**
     * Create an instance of {@link Index }
     * 
     */
    public Index createIndex() {
        return new Index();
    }

    /**
     * Create an instance of {@link GetResourcePrivilegesRequest }
     * 
     */
    public GetResourcePrivilegesRequest createGetResourcePrivilegesRequest() {
        return new GetResourcePrivilegesRequest();
    }

    /**
     * Create an instance of {@link XmlDocumentSet }
     * 
     */
    public XmlDocumentSet createXmlDocumentSet() {
        return new XmlDocumentSet();
    }

    /**
     * Create an instance of {@link TestDataSourceConnectionRequest }
     * 
     */
    public TestDataSourceConnectionRequest createTestDataSourceConnectionRequest() {
        return new TestDataSourceConnectionRequest();
    }

    /**
     * Create an instance of {@link GetIntrospectableResourceIdsResultRequest }
     * 
     */
    public GetIntrospectableResourceIdsResultRequest createGetIntrospectableResourceIdsResultRequest() {
        return new GetIntrospectableResourceIdsResultRequest();
    }

    /**
     * Create an instance of {@link TableResource }
     * 
     */
    public TableResource createTableResource() {
        return new TableResource();
    }

    /**
     * Create an instance of {@link UpdateDataServicePortRequest }
     * 
     */
    public UpdateDataServicePortRequest createUpdateDataServicePortRequest() {
        return new UpdateDataServicePortRequest();
    }

    /**
     * Create an instance of {@link GetUsedDSResourcesResponse }
     * 
     */
    public GetUsedDSResourcesResponse createGetUsedDSResourcesResponse() {
        return new GetUsedDSResourcesResponse();
    }

    /**
     * Create an instance of {@link UpdateOperationProcedureResponse }
     * 
     */
    public UpdateOperationProcedureResponse createUpdateOperationProcedureResponse() {
        return new UpdateOperationProcedureResponse();
    }

    /**
     * Create an instance of {@link CancelDataSourceReintrospectRequest }
     * 
     */
    public CancelDataSourceReintrospectRequest createCancelDataSourceReintrospectRequest() {
        return new CancelDataSourceReintrospectRequest();
    }

    /**
     * Create an instance of {@link UpdateResourceStatisticsConfigRequest }
     * 
     */
    public UpdateResourceStatisticsConfigRequest createUpdateResourceStatisticsConfigRequest() {
        return new UpdateResourceStatisticsConfigRequest();
    }

    /**
     * Create an instance of {@link UpdateDataSourceChildInfosRequest }
     * 
     */
    public UpdateDataSourceChildInfosRequest createUpdateDataSourceChildInfosRequest() {
        return new UpdateDataSourceChildInfosRequest();
    }

    /**
     * Create an instance of {@link UpdateOperationProcedureRequest }
     * 
     */
    public UpdateOperationProcedureRequest createUpdateOperationProcedureRequest() {
        return new UpdateOperationProcedureRequest();
    }

    /**
     * Create an instance of {@link UpdateExternalSqlProcedureResponse }
     * 
     */
    public UpdateExternalSqlProcedureResponse createUpdateExternalSqlProcedureResponse() {
        return new UpdateExternalSqlProcedureResponse();
    }

    /**
     * Create an instance of {@link PathTypeVersionRequest }
     * 
     */
    public PathTypeVersionRequest createPathTypeVersionRequest() {
        return new PathTypeVersionRequest();
    }

    /**
     * Create an instance of {@link UpdateResourceCacheConfigResponse }
     * 
     */
    public UpdateResourceCacheConfigResponse createUpdateResourceCacheConfigResponse() {
        return new UpdateResourceCacheConfigResponse();
    }

    /**
     * Create an instance of {@link GetDataSourceTypeCustomCapabilitiesRequest }
     * 
     */
    public GetDataSourceTypeCustomCapabilitiesRequest createGetDataSourceTypeCustomCapabilitiesRequest() {
        return new GetDataSourceTypeCustomCapabilitiesRequest();
    }

    /**
     * Create an instance of {@link Resource.Hints }
     * 
     */
    public Resource.Hints createResourceHints() {
        return new Resource.Hints();
    }

    /**
     * Create an instance of {@link CopyPrivilegeEntryType }
     * 
     */
    public CopyPrivilegeEntryType createCopyPrivilegeEntryType() {
        return new CopyPrivilegeEntryType();
    }

    /**
     * Create an instance of {@link GetConnectorGroupResponse }
     * 
     */
    public GetConnectorGroupResponse createGetConnectorGroupResponse() {
        return new GetConnectorGroupResponse();
    }

    /**
     * Create an instance of {@link com.compositesw.services.system.admin.resource.Refresh }
     * 
     */
    public com.compositesw.services.system.admin.resource.Refresh createRefresh() {
        return new com.compositesw.services.system.admin.resource.Refresh();
    }

    /**
     * Create an instance of {@link PathTypeOptionalDetailRequest }
     * 
     */
    public PathTypeOptionalDetailRequest createPathTypeOptionalDetailRequest() {
        return new PathTypeOptionalDetailRequest();
    }

    /**
     * Create an instance of {@link UpdateImplementationContainerResponse }
     * 
     */
    public UpdateImplementationContainerResponse createUpdateImplementationContainerResponse() {
        return new UpdateImplementationContainerResponse();
    }

    /**
     * Create an instance of {@link ContainerResource }
     * 
     */
    public ContainerResource createContainerResource() {
        return new ContainerResource();
    }

    /**
     * Create an instance of {@link ResourceUpdateInfo }
     * 
     */
    public ResourceUpdateInfo createResourceUpdateInfo() {
        return new ResourceUpdateInfo();
    }

    /**
     * Create an instance of {@link IntrospectionChangeEntry }
     * 
     */
    public IntrospectionChangeEntry createIntrospectionChangeEntry() {
        return new IntrospectionChangeEntry();
    }

    /**
     * Create an instance of {@link IntrospectResourcesResultRequest }
     * 
     */
    public IntrospectResourcesResultRequest createIntrospectResourcesResultRequest() {
        return new IntrospectResourcesResultRequest();
    }

    /**
     * Create an instance of {@link DestroyConnectorRequest }
     * 
     */
    public DestroyConnectorRequest createDestroyConnectorRequest() {
        return new DestroyConnectorRequest();
    }

    /**
     * Create an instance of {@link GetConnectorGroupNamesResponse }
     * 
     */
    public GetConnectorGroupNamesResponse createGetConnectorGroupNamesResponse() {
        return new GetConnectorGroupNamesResponse();
    }

    /**
     * Create an instance of {@link GetDataSourceStatisticsConfigRequest }
     * 
     */
    public GetDataSourceStatisticsConfigRequest createGetDataSourceStatisticsConfigRequest() {
        return new GetDataSourceStatisticsConfigRequest();
    }

    /**
     * Create an instance of {@link UpdateDataSourceChildInfosWithFilterResponse }
     * 
     */
    public UpdateDataSourceChildInfosWithFilterResponse createUpdateDataSourceChildInfosWithFilterResponse() {
        return new UpdateDataSourceChildInfosWithFilterResponse();
    }

    /**
     * Create an instance of {@link UpdateDataSourceChildInfosWithFilterRequest.ChildInfos }
     * 
     */
    public UpdateDataSourceChildInfosWithFilterRequest.ChildInfos createUpdateDataSourceChildInfosWithFilterRequestChildInfos() {
        return new UpdateDataSourceChildInfosWithFilterRequest.ChildInfos();
    }

    /**
     * Create an instance of {@link UpdateXQueryTransformProcedureRequest }
     * 
     */
    public UpdateXQueryTransformProcedureRequest createUpdateXQueryTransformProcedureRequest() {
        return new UpdateXQueryTransformProcedureRequest();
    }

    /**
     * Create an instance of {@link GetMostRecentIntrospectionStatusResponse }
     * 
     */
    public GetMostRecentIntrospectionStatusResponse createGetMostRecentIntrospectionStatusResponse() {
        return new GetMostRecentIntrospectionStatusResponse();
    }

    /**
     * Create an instance of {@link XmlSchemaDocumentSet.RedefinedDocuments }
     * 
     */
    public XmlSchemaDocumentSet.RedefinedDocuments createXmlSchemaDocumentSetRedefinedDocuments() {
        return new XmlSchemaDocumentSet.RedefinedDocuments();
    }

    /**
     * Create an instance of {@link RebindResourcesRequest }
     * 
     */
    public RebindResourcesRequest createRebindResourcesRequest() {
        return new RebindResourcesRequest();
    }

    /**
     * Create an instance of {@link GetDataSourceAttributeDefsResponse }
     * 
     */
    public GetDataSourceAttributeDefsResponse createGetDataSourceAttributeDefsResponse() {
        return new GetDataSourceAttributeDefsResponse();
    }

    /**
     * Create an instance of {@link XmlSchemaDocumentSet.ImportedDocuments }
     * 
     */
    public XmlSchemaDocumentSet.ImportedDocuments createXmlSchemaDocumentSetImportedDocuments() {
        return new XmlSchemaDocumentSet.ImportedDocuments();
    }

    /**
     * Create an instance of {@link TriggerResource }
     * 
     */
    public TriggerResource createTriggerResource() {
        return new TriggerResource();
    }

    /**
     * Create an instance of {@link GetResourceResponse }
     * 
     */
    public GetResourceResponse createGetResourceResponse() {
        return new GetResourceResponse();
    }

    /**
     * Create an instance of {@link ReintrospectChangeEntry }
     * 
     */
    public ReintrospectChangeEntry createReintrospectChangeEntry() {
        return new ReintrospectChangeEntry();
    }

    /**
     * Create an instance of {@link Connector }
     * 
     */
    public Connector createConnector() {
        return new Connector();
    }

    /**
     * Create an instance of {@link DataSourceTypeInfo }
     * 
     */
    public DataSourceTypeInfo createDataSourceTypeInfo() {
        return new DataSourceTypeInfo();
    }

    /**
     * Create an instance of {@link UpdateDataSourceChildInfosRequest.ChildInfos }
     * 
     */
    public UpdateDataSourceChildInfosRequest.ChildInfos createUpdateDataSourceChildInfosRequestChildInfos() {
        return new UpdateDataSourceChildInfosRequest.ChildInfos();
    }

    /**
     * Create an instance of {@link GetResourceStatisticsConfigResponse }
     * 
     */
    public GetResourceStatisticsConfigResponse createGetResourceStatisticsConfigResponse() {
        return new GetResourceStatisticsConfigResponse();
    }

    /**
     * Create an instance of {@link LockResourceRequest }
     * 
     */
    public LockResourceRequest createLockResourceRequest() {
        return new LockResourceRequest();
    }

    /**
     * Create an instance of {@link ForeignKey.Columns }
     * 
     */
    public ForeignKey.Columns createForeignKeyColumns() {
        return new ForeignKey.Columns();
    }

    /**
     * Create an instance of {@link ResourceBundle.PrivilegeEntries }
     * 
     */
    public ResourceBundle.PrivilegeEntries createResourceBundlePrivilegeEntries() {
        return new ResourceBundle.PrivilegeEntries();
    }

    /**
     * Create an instance of {@link LockState }
     * 
     */
    public LockState createLockState() {
        return new LockState();
    }

    /**
     * Create an instance of {@link UpdateXQueryTransformProcedureResponse }
     * 
     */
    public UpdateXQueryTransformProcedureResponse createUpdateXQueryTransformProcedureResponse() {
        return new UpdateXQueryTransformProcedureResponse();
    }

    /**
     * Create an instance of {@link GetDataSourceChildResourcesRequest }
     * 
     */
    public GetDataSourceChildResourcesRequest createGetDataSourceChildResourcesRequest() {
        return new GetDataSourceChildResourcesRequest();
    }

    /**
     * Create an instance of {@link GetResourceCacheConfigRequest }
     * 
     */
    public GetResourceCacheConfigRequest createGetResourceCacheConfigRequest() {
        return new GetResourceCacheConfigRequest();
    }

    /**
     * Create an instance of {@link ReintrospectDataSourceRequest }
     * 
     */
    public ReintrospectDataSourceRequest createReintrospectDataSourceRequest() {
        return new ReintrospectDataSourceRequest();
    }

    /**
     * Create an instance of {@link RebindResourcesResponse }
     * 
     */
    public RebindResourcesResponse createRebindResourcesResponse() {
        return new RebindResourcesResponse();
    }

    /**
     * Create an instance of {@link UpdateDataSourceTypeCustomCapabilitiesResponse }
     * 
     */
    public UpdateDataSourceTypeCustomCapabilitiesResponse createUpdateDataSourceTypeCustomCapabilitiesResponse() {
        return new UpdateDataSourceTypeCustomCapabilitiesResponse();
    }

    /**
     * Create an instance of {@link DefinitionSetSource }
     * 
     */
    public DefinitionSetSource createDefinitionSetSource() {
        return new DefinitionSetSource();
    }

    /**
     * Create an instance of {@link GetChildResourcesRequest }
     * 
     */
    public GetChildResourcesRequest createGetChildResourcesRequest() {
        return new GetChildResourcesRequest();
    }

    /**
     * Create an instance of {@link PathTypeDetailVersionRequest }
     * 
     */
    public PathTypeDetailVersionRequest createPathTypeDetailVersionRequest() {
        return new PathTypeDetailVersionRequest();
    }

    /**
     * Create an instance of {@link GetLockedResourcesResponse }
     * 
     */
    public GetLockedResourcesResponse createGetLockedResourcesResponse() {
        return new GetLockedResourcesResponse();
    }

    /**
     * Create an instance of {@link GetResourceStatsSummaryResponse }
     * 
     */
    public GetResourceStatsSummaryResponse createGetResourceStatsSummaryResponse() {
        return new GetResourceStatsSummaryResponse();
    }

    /**
     * Create an instance of {@link UpdateResourcePrivilegesResponse }
     * 
     */
    public UpdateResourcePrivilegesResponse createUpdateResourcePrivilegesResponse() {
        return new UpdateResourcePrivilegesResponse();
    }

    /**
     * Create an instance of {@link UpdateSqlTableRequest }
     * 
     */
    public UpdateSqlTableRequest createUpdateSqlTableRequest() {
        return new UpdateSqlTableRequest();
    }

    /**
     * Create an instance of {@link UpdateBasicTransformProcedureResponse }
     * 
     */
    public UpdateBasicTransformProcedureResponse createUpdateBasicTransformProcedureResponse() {
        return new UpdateBasicTransformProcedureResponse();
    }

    /**
     * Create an instance of {@link CreateDataSourceResponse }
     * 
     */
    public CreateDataSourceResponse createCreateDataSourceResponse() {
        return new CreateDataSourceResponse();
    }

    /**
     * Create an instance of {@link DestroyConnectorResponse }
     * 
     */
    public DestroyConnectorResponse createDestroyConnectorResponse() {
        return new DestroyConnectorResponse();
    }

    /**
     * Create an instance of {@link GetCachedResourceStatisticsConfigRequest }
     * 
     */
    public GetCachedResourceStatisticsConfigRequest createGetCachedResourceStatisticsConfigRequest() {
        return new GetCachedResourceStatisticsConfigRequest();
    }

    /**
     * Create an instance of {@link UpdateDataServicePortResponse }
     * 
     */
    public UpdateDataServicePortResponse createUpdateDataServicePortResponse() {
        return new UpdateDataServicePortResponse();
    }

    /**
     * Create an instance of {@link MultiPathTypeOptionalRequest }
     * 
     */
    public MultiPathTypeOptionalRequest createMultiPathTypeOptionalRequest() {
        return new MultiPathTypeOptionalRequest();
    }

    /**
     * Create an instance of {@link PathNameDetailRequest }
     * 
     */
    public PathNameDetailRequest createPathNameDetailRequest() {
        return new PathNameDetailRequest();
    }

    /**
     * Create an instance of {@link UpdateDataSourceResponse }
     * 
     */
    public UpdateDataSourceResponse createUpdateDataSourceResponse() {
        return new UpdateDataSourceResponse();
    }

    /**
     * Create an instance of {@link PathTypePairList }
     * 
     */
    public PathTypePairList createPathTypePairList() {
        return new PathTypePairList();
    }

    /**
     * Create an instance of {@link UpdateLinkRequest }
     * 
     */
    public UpdateLinkRequest createUpdateLinkRequest() {
        return new UpdateLinkRequest();
    }

    /**
     * Create an instance of {@link PortOperationProperty }
     * 
     */
    public PortOperationProperty createPortOperationProperty() {
        return new PortOperationProperty();
    }

    /**
     * Create an instance of {@link PathTypeOptionalPair }
     * 
     */
    public PathTypeOptionalPair createPathTypeOptionalPair() {
        return new PathTypeOptionalPair();
    }

    /**
     * Create an instance of {@link UpdateXSLTProcedureRequest }
     * 
     */
    public UpdateXSLTProcedureRequest createUpdateXSLTProcedureRequest() {
        return new UpdateXSLTProcedureRequest();
    }

    /**
     * Create an instance of {@link UpdateConnectorResponse }
     * 
     */
    public UpdateConnectorResponse createUpdateConnectorResponse() {
        return new UpdateConnectorResponse();
    }

    /**
     * Create an instance of {@link GetDataSourceTypeAttributeDefsRequest }
     * 
     */
    public GetDataSourceTypeAttributeDefsRequest createGetDataSourceTypeAttributeDefsRequest() {
        return new GetDataSourceTypeAttributeDefsRequest();
    }

    /**
     * Create an instance of {@link CreateLinksRecursivelyRequest }
     * 
     */
    public CreateLinksRecursivelyRequest createCreateLinksRecursivelyRequest() {
        return new CreateLinksRecursivelyRequest();
    }

    /**
     * Create an instance of {@link GetLockedResourcesRequest }
     * 
     */
    public GetLockedResourcesRequest createGetLockedResourcesRequest() {
        return new GetLockedResourcesRequest();
    }

    /**
     * Create an instance of {@link ResourceAttributesList }
     * 
     */
    public ResourceAttributesList createResourceAttributesList() {
        return new ResourceAttributesList();
    }

    /**
     * Create an instance of {@link NameDirectionPair }
     * 
     */
    public NameDirectionPair createNameDirectionPair() {
        return new NameDirectionPair();
    }

    /**
     * Create an instance of {@link GetResourcePrivilegesResponse }
     * 
     */
    public GetResourcePrivilegesResponse createGetResourcePrivilegesResponse() {
        return new GetResourcePrivilegesResponse();
    }

    /**
     * Create an instance of {@link UpdateTriggerResponse }
     * 
     */
    public UpdateTriggerResponse createUpdateTriggerResponse() {
        return new UpdateTriggerResponse();
    }

    /**
     * Create an instance of {@link GetTransformFunctionsResponse }
     * 
     */
    public GetTransformFunctionsResponse createGetTransformFunctionsResponse() {
        return new GetTransformFunctionsResponse();
    }

    /**
     * Create an instance of {@link UpdateCustomDataSourceTypeResponse }
     * 
     */
    public UpdateCustomDataSourceTypeResponse createUpdateCustomDataSourceTypeResponse() {
        return new UpdateCustomDataSourceTypeResponse();
    }

    /**
     * Create an instance of {@link GetIntrospectionAttributesRequest }
     * 
     */
    public GetIntrospectionAttributesRequest createGetIntrospectionAttributesRequest() {
        return new GetIntrospectionAttributesRequest();
    }

    /**
     * Create an instance of {@link XmlSchemaDocumentSet.IncludedDocuments }
     * 
     */
    public XmlSchemaDocumentSet.IncludedDocuments createXmlSchemaDocumentSetIncludedDocuments() {
        return new XmlSchemaDocumentSet.IncludedDocuments();
    }

    /**
     * Create an instance of {@link UpdateDataSourceTypeCustomCapabilitiesRequest }
     * 
     */
    public UpdateDataSourceTypeCustomCapabilitiesRequest createUpdateDataSourceTypeCustomCapabilitiesRequest() {
        return new UpdateDataSourceTypeCustomCapabilitiesRequest();
    }

    /**
     * Create an instance of {@link DataSourceChildInfo }
     * 
     */
    public DataSourceChildInfo createDataSourceChildInfo() {
        return new DataSourceChildInfo();
    }

    /**
     * Create an instance of {@link ClearResourceStatisticsResponse }
     * 
     */
    public ClearResourceStatisticsResponse createClearResourceStatisticsResponse() {
        return new ClearResourceStatisticsResponse();
    }

    /**
     * Create an instance of {@link BucketPropertiesType }
     * 
     */
    public BucketPropertiesType createBucketPropertiesType() {
        return new BucketPropertiesType();
    }

    /**
     * Create an instance of {@link GetParentDataSourceTypeRequest }
     * 
     */
    public GetParentDataSourceTypeRequest createGetParentDataSourceTypeRequest() {
        return new GetParentDataSourceTypeRequest();
    }

    /**
     * Create an instance of {@link MultiPathTypeOptionalRequest.Entries }
     * 
     */
    public MultiPathTypeOptionalRequest.Entries createMultiPathTypeOptionalRequestEntries() {
        return new MultiPathTypeOptionalRequest.Entries();
    }

    /**
     * Create an instance of {@link CancelResourceStatisticsRequest }
     * 
     */
    public CancelResourceStatisticsRequest createCancelResourceStatisticsRequest() {
        return new CancelResourceStatisticsRequest();
    }

    /**
     * Create an instance of {@link ResourceUpdates }
     * 
     */
    public ResourceUpdates createResourceUpdates() {
        return new ResourceUpdates();
    }

    /**
     * Create an instance of {@link LinkableResourceIdList }
     * 
     */
    public LinkableResourceIdList createLinkableResourceIdList() {
        return new LinkableResourceIdList();
    }

    /**
     * Create an instance of {@link GetParentResourceResponse }
     * 
     */
    public GetParentResourceResponse createGetParentResourceResponse() {
        return new GetParentResourceResponse();
    }

    /**
     * Create an instance of {@link GetResourcesResponse }
     * 
     */
    public GetResourcesResponse createGetResourcesResponse() {
        return new GetResourcesResponse();
    }

    /**
     * Create an instance of {@link MoveResourcesResponse }
     * 
     */
    public MoveResourcesResponse createMoveResourcesResponse() {
        return new MoveResourcesResponse();
    }

    /**
     * Create an instance of {@link GetDataSourceStatisticsConfigResponse }
     * 
     */
    public GetDataSourceStatisticsConfigResponse createGetDataSourceStatisticsConfigResponse() {
        return new GetDataSourceStatisticsConfigResponse();
    }

    /**
     * Create an instance of {@link GetAncestorResourcesRequest }
     * 
     */
    public GetAncestorResourcesRequest createGetAncestorResourcesRequest() {
        return new GetAncestorResourcesRequest();
    }

    /**
     * Create an instance of {@link ResourceAttributes }
     * 
     */
    public ResourceAttributes createResourceAttributes() {
        return new ResourceAttributes();
    }

    /**
     * Create an instance of {@link ResourceExistsRequest }
     * 
     */
    public ResourceExistsRequest createResourceExistsRequest() {
        return new ResourceExistsRequest();
    }

    /**
     * Create an instance of {@link GetIntrospectedResourceIdsResultRequest }
     * 
     */
    public GetIntrospectedResourceIdsResultRequest createGetIntrospectedResourceIdsResultRequest() {
        return new GetIntrospectedResourceIdsResultRequest();
    }

    /**
     * Create an instance of {@link MoveResourcesRequest }
     * 
     */
    public MoveResourcesRequest createMoveResourcesRequest() {
        return new MoveResourcesRequest();
    }

    /**
     * Create an instance of {@link Parameter }
     * 
     */
    public Parameter createParameter() {
        return new Parameter();
    }

    /**
     * Create an instance of {@link GetDataSourceTypesResponse }
     * 
     */
    public GetDataSourceTypesResponse createGetDataSourceTypesResponse() {
        return new GetDataSourceTypesResponse();
    }

    /**
     * Create an instance of {@link CopyResourcesRequest }
     * 
     */
    public CopyResourcesRequest createCopyResourcesRequest() {
        return new CopyResourcesRequest();
    }

    /**
     * Create an instance of {@link GetDependentResourcesRequest }
     * 
     */
    public GetDependentResourcesRequest createGetDependentResourcesRequest() {
        return new GetDependentResourcesRequest();
    }

    /**
     * Create an instance of {@link IntrospectionAttributeDefsList }
     * 
     */
    public IntrospectionAttributeDefsList createIntrospectionAttributeDefsList() {
        return new IntrospectionAttributeDefsList();
    }

    /**
     * Create an instance of {@link GetIntrospectedResourceIdsTaskRequest }
     * 
     */
    public GetIntrospectedResourceIdsTaskRequest createGetIntrospectedResourceIdsTaskRequest() {
        return new GetIntrospectedResourceIdsTaskRequest();
    }

    /**
     * Create an instance of {@link ReintrospectResponse.ReintrospectReport }
     * 
     */
    public ReintrospectResponse.ReintrospectReport createReintrospectResponseReintrospectReport() {
        return new ReintrospectResponse.ReintrospectReport();
    }

    /**
     * Create an instance of {@link UpdateDefinitionSetRequest.Definitions }
     * 
     */
    public UpdateDefinitionSetRequest.Definitions createUpdateDefinitionSetRequestDefinitions() {
        return new UpdateDefinitionSetRequest.Definitions();
    }

    /**
     * Create an instance of {@link UnlockResourcesResponse }
     * 
     */
    public UnlockResourcesResponse createUnlockResourcesResponse() {
        return new UnlockResourcesResponse();
    }

    /**
     * Create an instance of {@link UpdateXSLTProcedureResponse }
     * 
     */
    public UpdateXSLTProcedureResponse createUpdateXSLTProcedureResponse() {
        return new UpdateXSLTProcedureResponse();
    }

    /**
     * Create an instance of {@link ForeignKey }
     * 
     */
    public ForeignKey createForeignKey() {
        return new ForeignKey();
    }

    /**
     * Create an instance of {@link UpdateTriggerRequest }
     * 
     */
    public UpdateTriggerRequest createUpdateTriggerRequest() {
        return new UpdateTriggerRequest();
    }

    /**
     * Create an instance of {@link UpdateCachedResourceStatisticsConfigRequest }
     * 
     */
    public UpdateCachedResourceStatisticsConfigRequest createUpdateCachedResourceStatisticsConfigRequest() {
        return new UpdateCachedResourceStatisticsConfigRequest();
    }

    /**
     * Create an instance of {@link TargetPathTypePairList }
     * 
     */
    public TargetPathTypePairList createTargetPathTypePairList() {
        return new TargetPathTypePairList();
    }

    /**
     * Create an instance of {@link UpdateDefinitionSetRequest }
     * 
     */
    public UpdateDefinitionSetRequest createUpdateDefinitionSetRequest() {
        return new UpdateDefinitionSetRequest();
    }

    /**
     * Create an instance of {@link GetDataSourceTypesResponse.DataSourceTypes }
     * 
     */
    public GetDataSourceTypesResponse.DataSourceTypes createGetDataSourceTypesResponseDataSourceTypes() {
        return new GetDataSourceTypesResponse.DataSourceTypes();
    }

    /**
     * Create an instance of {@link ProcedureResource }
     * 
     */
    public ProcedureResource createProcedureResource() {
        return new ProcedureResource();
    }

    /**
     * Create an instance of {@link DestroyResourceRequest }
     * 
     */
    public DestroyResourceRequest createDestroyResourceRequest() {
        return new DestroyResourceRequest();
    }

    /**
     * Create an instance of {@link ProjectList }
     * 
     */
    public ProjectList createProjectList() {
        return new ProjectList();
    }

    /**
     * Create an instance of {@link UpdateResourcesRequest }
     * 
     */
    public UpdateResourcesRequest createUpdateResourcesRequest() {
        return new UpdateResourcesRequest();
    }

    /**
     * Create an instance of {@link CreateResourceResponse }
     * 
     */
    public CreateResourceResponse createCreateResourceResponse() {
        return new CreateResourceResponse();
    }

    /**
     * Create an instance of {@link UpdateCustomDataSourceTypeRequest }
     * 
     */
    public UpdateCustomDataSourceTypeRequest createUpdateCustomDataSourceTypeRequest() {
        return new UpdateCustomDataSourceTypeRequest();
    }

    /**
     * Create an instance of {@link CopyResourcePrivilegesRequest.CopyPrivilegeEntries }
     * 
     */
    public CopyResourcePrivilegesRequest.CopyPrivilegeEntries createCopyResourcePrivilegesRequestCopyPrivilegeEntries() {
        return new CopyResourcePrivilegesRequest.CopyPrivilegeEntries();
    }

    /**
     * Create an instance of {@link UnlockResourcesRequest }
     * 
     */
    public UnlockResourcesRequest createUnlockResourcesRequest() {
        return new UnlockResourcesRequest();
    }

    /**
     * Create an instance of {@link UpdateLinkResponse }
     * 
     */
    public UpdateLinkResponse createUpdateLinkResponse() {
        return new UpdateLinkResponse();
    }

    /**
     * Create an instance of {@link UpdateDataSourceChildInfosWithFilterRequest }
     * 
     */
    public UpdateDataSourceChildInfosWithFilterRequest createUpdateDataSourceChildInfosWithFilterRequest() {
        return new UpdateDataSourceChildInfosWithFilterRequest();
    }

    /**
     * Create an instance of {@link UpdateResourcePrivilegesRequest }
     * 
     */
    public UpdateResourcePrivilegesRequest createUpdateResourcePrivilegesRequest() {
        return new UpdateResourcePrivilegesRequest();
    }

    /**
     * Create an instance of {@link UpdateSqlScriptProcedureRequest }
     * 
     */
    public UpdateSqlScriptProcedureRequest createUpdateSqlScriptProcedureRequest() {
        return new UpdateSqlScriptProcedureRequest();
    }

    /**
     * Create an instance of {@link ForeignKeyColumn }
     * 
     */
    public ForeignKeyColumn createForeignKeyColumn() {
        return new ForeignKeyColumn();
    }

    /**
     * Create an instance of {@link GetMostRecentIntrospectionStatusRequest }
     * 
     */
    public GetMostRecentIntrospectionStatusRequest createGetMostRecentIntrospectionStatusRequest() {
        return new GetMostRecentIntrospectionStatusRequest();
    }

    /**
     * Create an instance of {@link GetResourcePrivilegesResponse.PrivilegeEntries }
     * 
     */
    public GetResourcePrivilegesResponse.PrivilegeEntries createGetResourcePrivilegesResponsePrivilegeEntries() {
        return new GetResourcePrivilegesResponse.PrivilegeEntries();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDataSourceStatisticsConfigResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateDataSourceStatisticsConfigResponse")
    public JAXBElement<UpdateDataSourceStatisticsConfigResponse> createUpdateDataSourceStatisticsConfigResponse(UpdateDataSourceStatisticsConfigResponse value) {
        return new JAXBElement<UpdateDataSourceStatisticsConfigResponse>(_UpdateDataSourceStatisticsConfigResponse_QNAME, UpdateDataSourceStatisticsConfigResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RefreshResourceCacheResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "refreshResourceCacheResponse")
    public JAXBElement<RefreshResourceCacheResponse> createRefreshResourceCacheResponse(RefreshResourceCacheResponse value) {
        return new JAXBElement<RefreshResourceCacheResponse>(_RefreshResourceCacheResponse_QNAME, RefreshResourceCacheResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSqlTableRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateSqlTable")
    public JAXBElement<UpdateSqlTableRequest> createUpdateSqlTable(UpdateSqlTableRequest value) {
        return new JAXBElement<UpdateSqlTableRequest>(_UpdateSqlTable_QNAME, UpdateSqlTableRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMostRecentIntrospectionStatusRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getMostRecentIntrospectionStatus")
    public JAXBElement<GetMostRecentIntrospectionStatusRequest> createGetMostRecentIntrospectionStatus(GetMostRecentIntrospectionStatusRequest value) {
        return new JAXBElement<GetMostRecentIntrospectionStatusRequest>(_GetMostRecentIntrospectionStatus_QNAME, GetMostRecentIntrospectionStatusRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateLinkRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateLink")
    public JAXBElement<UpdateLinkRequest> createUpdateLink(UpdateLinkRequest value) {
        return new JAXBElement<UpdateLinkRequest>(_UpdateLink_QNAME, UpdateLinkRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnlockResourceRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "unlockResource")
    public JAXBElement<UnlockResourceRequest> createUnlockResource(UnlockResourceRequest value) {
        return new JAXBElement<UnlockResourceRequest>(_UnlockResource_QNAME, UnlockResourceRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveResourcesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "moveResources")
    public JAXBElement<MoveResourcesRequest> createMoveResources(MoveResourcesRequest value) {
        return new JAXBElement<MoveResourcesRequest>(_MoveResources_QNAME, MoveResourcesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateDataSourceRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "createDataSource")
    public JAXBElement<CreateDataSourceRequest> createCreateDataSource(CreateDataSourceRequest value) {
        return new JAXBElement<CreateDataSourceRequest>(_CreateDataSource_QNAME, CreateDataSourceRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDataServicePortResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateDataServicePortResponse")
    public JAXBElement<UpdateDataServicePortResponse> createUpdateDataServicePortResponse(UpdateDataServicePortResponse value) {
        return new JAXBElement<UpdateDataServicePortResponse>(_UpdateDataServicePortResponse_QNAME, UpdateDataServicePortResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateBasicTransformProcedureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateBasicTransformProcedureResponse")
    public JAXBElement<UpdateBasicTransformProcedureResponse> createUpdateBasicTransformProcedureResponse(UpdateBasicTransformProcedureResponse value) {
        return new JAXBElement<UpdateBasicTransformProcedureResponse>(_UpdateBasicTransformProcedureResponse_QNAME, UpdateBasicTransformProcedureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelResourceStatisticsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "cancelResourceStatisticsResponse")
    public JAXBElement<CancelResourceStatisticsResponse> createCancelResourceStatisticsResponse(CancelResourceStatisticsResponse value) {
        return new JAXBElement<CancelResourceStatisticsResponse>(_CancelResourceStatisticsResponse_QNAME, CancelResourceStatisticsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourceRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getResource")
    public JAXBElement<GetResourceRequest> createGetResource(GetResourceRequest value) {
        return new JAXBElement<GetResourceRequest>(_GetResource_QNAME, GetResourceRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCustomDataSourceTypeRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateCustomDataSourceType")
    public JAXBElement<UpdateCustomDataSourceTypeRequest> createUpdateCustomDataSourceType(UpdateCustomDataSourceTypeRequest value) {
        return new JAXBElement<UpdateCustomDataSourceTypeRequest>(_UpdateCustomDataSourceType_QNAME, UpdateCustomDataSourceTypeRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateExternalSqlProcedureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateExternalSqlProcedureResponse")
    public JAXBElement<UpdateExternalSqlProcedureResponse> createUpdateExternalSqlProcedureResponse(UpdateExternalSqlProcedureResponse value) {
        return new JAXBElement<UpdateExternalSqlProcedureResponse>(_UpdateExternalSqlProcedureResponse_QNAME, UpdateExternalSqlProcedureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyResourceRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "copyResource")
    public JAXBElement<CopyResourceRequest> createCopyResource(CopyResourceRequest value) {
        return new JAXBElement<CopyResourceRequest>(_CopyResource_QNAME, CopyResourceRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LockResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "lockResourcesResponse")
    public JAXBElement<LockResourcesResponse> createLockResourcesResponse(LockResourcesResponse value) {
        return new JAXBElement<LockResourcesResponse>(_LockResourcesResponse_QNAME, LockResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataSourceTypeAttributeDefsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getDataSourceTypeAttributeDefs")
    public JAXBElement<GetDataSourceTypeAttributeDefsRequest> createGetDataSourceTypeAttributeDefs(GetDataSourceTypeAttributeDefsRequest value) {
        return new JAXBElement<GetDataSourceTypeAttributeDefsRequest>(_GetDataSourceTypeAttributeDefs_QNAME, GetDataSourceTypeAttributeDefsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataSourceStatisticsConfigResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getDataSourceStatisticsConfigResponse")
    public JAXBElement<GetDataSourceStatisticsConfigResponse> createGetDataSourceStatisticsConfigResponse(GetDataSourceStatisticsConfigResponse value) {
        return new JAXBElement<GetDataSourceStatisticsConfigResponse>(_GetDataSourceStatisticsConfigResponse_QNAME, GetDataSourceStatisticsConfigResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourceCacheConfigRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getResourceCacheConfig")
    public JAXBElement<GetResourceCacheConfigRequest> createGetResourceCacheConfig(GetResourceCacheConfigRequest value) {
        return new JAXBElement<GetResourceCacheConfigRequest>(_GetResourceCacheConfig_QNAME, GetResourceCacheConfigRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDataSourceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateDataSourceResponse")
    public JAXBElement<UpdateDataSourceResponse> createUpdateDataSourceResponse(UpdateDataSourceResponse value) {
        return new JAXBElement<UpdateDataSourceResponse>(_UpdateDataSourceResponse_QNAME, UpdateDataSourceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourcesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getResources")
    public JAXBElement<GetResourcesRequest> createGetResources(GetResourcesRequest value) {
        return new JAXBElement<GetResourcesRequest>(_GetResources_QNAME, GetResourcesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataSourceTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getDataSourceTypesResponse")
    public JAXBElement<GetDataSourceTypesResponse> createGetDataSourceTypesResponse(GetDataSourceTypesResponse value) {
        return new JAXBElement<GetDataSourceTypesResponse>(_GetDataSourceTypesResponse_QNAME, GetDataSourceTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetParentDataSourceTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getParentDataSourceTypeResponse")
    public JAXBElement<GetParentDataSourceTypeResponse> createGetParentDataSourceTypeResponse(GetParentDataSourceTypeResponse value) {
        return new JAXBElement<GetParentDataSourceTypeResponse>(_GetParentDataSourceTypeResponse_QNAME, GetParentDataSourceTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourcePrivilegesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getResourcePrivileges")
    public JAXBElement<GetResourcePrivilegesRequest> createGetResourcePrivileges(GetResourcePrivilegesRequest value) {
        return new JAXBElement<GetResourcePrivilegesRequest>(_GetResourcePrivileges_QNAME, GetResourcePrivilegesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "createLinkResponse")
    public JAXBElement<CreateLinkResponse> createCreateLinkResponse(CreateLinkResponse value) {
        return new JAXBElement<CreateLinkResponse>(_CreateLinkResponse_QNAME, CreateLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearIntrospectableResourceIdCacheResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "clearIntrospectableResourceIdCacheResponse")
    public JAXBElement<ClearIntrospectableResourceIdCacheResponse> createClearIntrospectableResourceIdCacheResponse(ClearIntrospectableResourceIdCacheResponse value) {
        return new JAXBElement<ClearIntrospectableResourceIdCacheResponse>(_ClearIntrospectableResourceIdCacheResponse_QNAME, ClearIntrospectableResourceIdCacheResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResourceEnabledResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateResourceEnabledResponse")
    public JAXBElement<UpdateResourceEnabledResponse> createUpdateResourceEnabledResponse(UpdateResourceEnabledResponse value) {
        return new JAXBElement<UpdateResourceEnabledResponse>(_UpdateResourceEnabledResponse_QNAME, UpdateResourceEnabledResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataSourceTypesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getDataSourceTypes")
    public JAXBElement<GetDataSourceTypesRequest> createGetDataSourceTypes(GetDataSourceTypesRequest value) {
        return new JAXBElement<GetDataSourceTypesRequest>(_GetDataSourceTypes_QNAME, GetDataSourceTypesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDataSourceTypeCustomCapabilitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateDataSourceTypeCustomCapabilitiesResponse")
    public JAXBElement<UpdateDataSourceTypeCustomCapabilitiesResponse> createUpdateDataSourceTypeCustomCapabilitiesResponse(UpdateDataSourceTypeCustomCapabilitiesResponse value) {
        return new JAXBElement<UpdateDataSourceTypeCustomCapabilitiesResponse>(_UpdateDataSourceTypeCustomCapabilitiesResponse_QNAME, UpdateDataSourceTypeCustomCapabilitiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RefreshResourceCacheRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "refreshResourceCache")
    public JAXBElement<RefreshResourceCacheRequest> createRefreshResourceCache(RefreshResourceCacheRequest value) {
        return new JAXBElement<RefreshResourceCacheRequest>(_RefreshResourceCache_QNAME, RefreshResourceCacheRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIntrospectionAttributeDefsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getIntrospectionAttributeDefs")
    public JAXBElement<GetIntrospectionAttributeDefsRequest> createGetIntrospectionAttributeDefs(GetIntrospectionAttributeDefsRequest value) {
        return new JAXBElement<GetIntrospectionAttributeDefsRequest>(_GetIntrospectionAttributeDefs_QNAME, GetIntrospectionAttributeDefsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIntrospectedResourceIdsResultRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getIntrospectedResourceIdsResult")
    public JAXBElement<GetIntrospectedResourceIdsResultRequest> createGetIntrospectedResourceIdsResult(GetIntrospectedResourceIdsResultRequest value) {
        return new JAXBElement<GetIntrospectedResourceIdsResultRequest>(_GetIntrospectedResourceIdsResult_QNAME, GetIntrospectedResourceIdsResultRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataSourceReintrospectResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getDataSourceReintrospectResultResponse")
    public JAXBElement<GetDataSourceReintrospectResultResponse> createGetDataSourceReintrospectResultResponse(GetDataSourceReintrospectResultResponse value) {
        return new JAXBElement<GetDataSourceReintrospectResultResponse>(_GetDataSourceReintrospectResultResponse_QNAME, GetDataSourceReintrospectResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnlockResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "unlockResourcesResponse")
    public JAXBElement<UnlockResourcesResponse> createUnlockResourcesResponse(UnlockResourcesResponse value) {
        return new JAXBElement<UnlockResourcesResponse>(_UnlockResourcesResponse_QNAME, UnlockResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearResourceStatisticsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "clearResourceStatistics")
    public JAXBElement<ClearResourceStatisticsRequest> createClearResourceStatistics(ClearResourceStatisticsRequest value) {
        return new JAXBElement<ClearResourceStatisticsRequest>(_ClearResourceStatistics_QNAME, ClearResourceStatisticsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIntrospectableResourceIdsTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getIntrospectableResourceIdsTaskResponse")
    public JAXBElement<GetIntrospectableResourceIdsTaskResponse> createGetIntrospectableResourceIdsTaskResponse(GetIntrospectableResourceIdsTaskResponse value) {
        return new JAXBElement<GetIntrospectableResourceIdsTaskResponse>(_GetIntrospectableResourceIdsTaskResponse_QNAME, GetIntrospectableResourceIdsTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChildResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getChildResourcesResponse")
    public JAXBElement<GetChildResourcesResponse> createGetChildResourcesResponse(GetChildResourcesResponse value) {
        return new JAXBElement<GetChildResourcesResponse>(_GetChildResourcesResponse_QNAME, GetChildResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIntrospectedResourceIdsTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getIntrospectedResourceIdsTaskResponse")
    public JAXBElement<GetIntrospectedResourceIdsTaskResponse> createGetIntrospectedResourceIdsTaskResponse(GetIntrospectedResourceIdsTaskResponse value) {
        return new JAXBElement<GetIntrospectedResourceIdsTaskResponse>(_GetIntrospectedResourceIdsTaskResponse_QNAME, GetIntrospectedResourceIdsTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IntrospectResourcesResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "introspectResourcesResultResponse")
    public JAXBElement<IntrospectResourcesResultResponse> createIntrospectResourcesResultResponse(IntrospectResourcesResultResponse value) {
        return new JAXBElement<IntrospectResourcesResultResponse>(_IntrospectResourcesResultResponse_QNAME, IntrospectResourcesResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestDataSourceConnectionRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "testDataSourceConnection")
    public JAXBElement<TestDataSourceConnectionRequest> createTestDataSourceConnection(TestDataSourceConnectionRequest value) {
        return new JAXBElement<TestDataSourceConnectionRequest>(_TestDataSourceConnection_QNAME, TestDataSourceConnectionRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateLinkRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "createLink")
    public JAXBElement<CreateLinkRequest> createCreateLink(CreateLinkRequest value) {
        return new JAXBElement<CreateLinkRequest>(_CreateLink_QNAME, CreateLinkRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LockResourceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "lockResourceResponse")
    public JAXBElement<LockResourceResponse> createLockResourceResponse(LockResourceResponse value) {
        return new JAXBElement<LockResourceResponse>(_LockResourceResponse_QNAME, LockResourceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResourcesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateResources")
    public JAXBElement<UpdateResourcesRequest> createUpdateResources(UpdateResourcesRequest value) {
        return new JAXBElement<UpdateResourcesRequest>(_UpdateResources_QNAME, UpdateResourcesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsedDSResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getUsedDSResourcesResponse")
    public JAXBElement<GetUsedDSResourcesResponse> createGetUsedDSResourcesResponse(GetUsedDSResourcesResponse value) {
        return new JAXBElement<GetUsedDSResourcesResponse>(_GetUsedDSResourcesResponse_QNAME, GetUsedDSResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateXQueryProcedureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateXQueryProcedureResponse")
    public JAXBElement<UpdateXQueryProcedureResponse> createUpdateXQueryProcedureResponse(UpdateXQueryProcedureResponse value) {
        return new JAXBElement<UpdateXQueryProcedureResponse>(_UpdateXQueryProcedureResponse_QNAME, UpdateXQueryProcedureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourceUpdatesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getResourceUpdates")
    public JAXBElement<GetResourceUpdatesRequest> createGetResourceUpdates(GetResourceUpdatesRequest value) {
        return new JAXBElement<GetResourceUpdatesRequest>(_GetResourceUpdates_QNAME, GetResourceUpdatesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIntrospectionAttributesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getIntrospectionAttributes")
    public JAXBElement<GetIntrospectionAttributesRequest> createGetIntrospectionAttributes(GetIntrospectionAttributesRequest value) {
        return new JAXBElement<GetIntrospectionAttributesRequest>(_GetIntrospectionAttributes_QNAME, GetIntrospectionAttributesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateConnectorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateConnectorResponse")
    public JAXBElement<UpdateConnectorResponse> createUpdateConnectorResponse(UpdateConnectorResponse value) {
        return new JAXBElement<UpdateConnectorResponse>(_UpdateConnectorResponse_QNAME, UpdateConnectorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataSourceTypeAttributeDefsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getDataSourceTypeAttributeDefsResponse")
    public JAXBElement<GetDataSourceTypeAttributeDefsResponse> createGetDataSourceTypeAttributeDefsResponse(GetDataSourceTypeAttributeDefsResponse value) {
        return new JAXBElement<GetDataSourceTypeAttributeDefsResponse>(_GetDataSourceTypeAttributeDefsResponse_QNAME, GetDataSourceTypeAttributeDefsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyResourceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "destroyResourceResponse")
    public JAXBElement<DestroyResourceResponse> createDestroyResourceResponse(DestroyResourceResponse value) {
        return new JAXBElement<DestroyResourceResponse>(_DestroyResourceResponse_QNAME, DestroyResourceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConnectorGroupNamesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getConnectorGroupNames")
    public JAXBElement<GetConnectorGroupNamesRequest> createGetConnectorGroupNames(GetConnectorGroupNamesRequest value) {
        return new JAXBElement<GetConnectorGroupNamesRequest>(_GetConnectorGroupNames_QNAME, GetConnectorGroupNamesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDependentResourcesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getDependentResources")
    public JAXBElement<GetDependentResourcesRequest> createGetDependentResources(GetDependentResourcesRequest value) {
        return new JAXBElement<GetDependentResourcesRequest>(_GetDependentResources_QNAME, GetDependentResourcesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDefinitionSetRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateDefinitionSet")
    public JAXBElement<UpdateDefinitionSetRequest> createUpdateDefinitionSet(UpdateDefinitionSetRequest value) {
        return new JAXBElement<UpdateDefinitionSetRequest>(_UpdateDefinitionSet_QNAME, UpdateDefinitionSetRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyResourceRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "destroyResource")
    public JAXBElement<DestroyResourceRequest> createDestroyResource(DestroyResourceRequest value) {
        return new JAXBElement<DestroyResourceRequest>(_DestroyResource_QNAME, DestroyResourceRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearResourceStatisticsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "clearResourceStatisticsResponse")
    public JAXBElement<ClearResourceStatisticsResponse> createClearResourceStatisticsResponse(ClearResourceStatisticsResponse value) {
        return new JAXBElement<ClearResourceStatisticsResponse>(_ClearResourceStatisticsResponse_QNAME, ClearResourceStatisticsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDataSourceChildInfosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateDataSourceChildInfosResponse")
    public JAXBElement<UpdateDataSourceChildInfosResponse> createUpdateDataSourceChildInfosResponse(UpdateDataSourceChildInfosResponse value) {
        return new JAXBElement<UpdateDataSourceChildInfosResponse>(_UpdateDataSourceChildInfosResponse_QNAME, UpdateDataSourceChildInfosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateXQueryProcedureRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateXQueryProcedure")
    public JAXBElement<UpdateXQueryProcedureRequest> createUpdateXQueryProcedure(UpdateXQueryProcedureRequest value) {
        return new JAXBElement<UpdateXQueryProcedureRequest>(_UpdateXQueryProcedure_QNAME, UpdateXQueryProcedureRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataSourceReintrospectResultRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getDataSourceReintrospectResult")
    public JAXBElement<GetDataSourceReintrospectResultRequest> createGetDataSourceReintrospectResult(GetDataSourceReintrospectResultRequest value) {
        return new JAXBElement<GetDataSourceReintrospectResultRequest>(_GetDataSourceReintrospectResult_QNAME, GetDataSourceReintrospectResultRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReintrospectDataSourceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "reintrospectDataSourceResponse")
    public JAXBElement<ReintrospectDataSourceResponse> createReintrospectDataSourceResponse(ReintrospectDataSourceResponse value) {
        return new JAXBElement<ReintrospectDataSourceResponse>(_ReintrospectDataSourceResponse_QNAME, ReintrospectDataSourceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTransformProcedureRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateTransformProcedure")
    public JAXBElement<UpdateTransformProcedureRequest> createUpdateTransformProcedure(UpdateTransformProcedureRequest value) {
        return new JAXBElement<UpdateTransformProcedureRequest>(_UpdateTransformProcedure_QNAME, UpdateTransformProcedureRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RebindResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "rebindResourcesResponse")
    public JAXBElement<RebindResourcesResponse> createRebindResourcesResponse(RebindResourcesResponse value) {
        return new JAXBElement<RebindResourcesResponse>(_RebindResourcesResponse_QNAME, RebindResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDependentResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getDependentResourcesResponse")
    public JAXBElement<GetDependentResourcesResponse> createGetDependentResourcesResponse(GetDependentResourcesResponse value) {
        return new JAXBElement<GetDependentResourcesResponse>(_GetDependentResourcesResponse_QNAME, GetDependentResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyConnectorRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "destroyConnector")
    public JAXBElement<DestroyConnectorRequest> createDestroyConnector(DestroyConnectorRequest value) {
        return new JAXBElement<DestroyConnectorRequest>(_DestroyConnector_QNAME, DestroyConnectorRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnlockResourcesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "unlockResources")
    public JAXBElement<UnlockResourcesRequest> createUnlockResources(UnlockResourcesRequest value) {
        return new JAXBElement<UnlockResourcesRequest>(_UnlockResources_QNAME, UnlockResourcesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateXsltTransformProcedureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateXsltTransformProcedureResponse")
    public JAXBElement<UpdateXsltTransformProcedureResponse> createUpdateXsltTransformProcedureResponse(UpdateXsltTransformProcedureResponse value) {
        return new JAXBElement<UpdateXsltTransformProcedureResponse>(_UpdateXsltTransformProcedureResponse_QNAME, UpdateXsltTransformProcedureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTriggerRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateTrigger")
    public JAXBElement<UpdateTriggerRequest> createUpdateTrigger(UpdateTriggerRequest value) {
        return new JAXBElement<UpdateTriggerRequest>(_UpdateTrigger_QNAME, UpdateTriggerRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateLinksRecursivelyRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "createLinksRecursively")
    public JAXBElement<CreateLinksRecursivelyRequest> createCreateLinksRecursively(CreateLinksRecursivelyRequest value) {
        return new JAXBElement<CreateLinksRecursivelyRequest>(_CreateLinksRecursively_QNAME, CreateLinksRecursivelyRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsedResourcesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getUsedResources")
    public JAXBElement<GetUsedResourcesRequest> createGetUsedResources(GetUsedResourcesRequest value) {
        return new JAXBElement<GetUsedResourcesRequest>(_GetUsedResources_QNAME, GetUsedResourcesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourceStatsSummaryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getResourceStatsSummaryResponse")
    public JAXBElement<GetResourceStatsSummaryResponse> createGetResourceStatsSummaryResponse(GetResourceStatsSummaryResponse value) {
        return new JAXBElement<GetResourceStatsSummaryResponse>(_GetResourceStatsSummaryResponse_QNAME, GetResourceStatsSummaryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IntrospectResourcesResultRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "introspectResourcesResult")
    public JAXBElement<IntrospectResourcesResultRequest> createIntrospectResourcesResult(IntrospectResourcesResultRequest value) {
        return new JAXBElement<IntrospectResourcesResultRequest>(_IntrospectResourcesResult_QNAME, IntrospectResourcesResultRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "moveResourcesResponse")
    public JAXBElement<MoveResourcesResponse> createMoveResourcesResponse(MoveResourcesResponse value) {
        return new JAXBElement<MoveResourcesResponse>(_MoveResourcesResponse_QNAME, MoveResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIntrospectableResourceIdsTaskRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getIntrospectableResourceIdsTask")
    public JAXBElement<GetIntrospectableResourceIdsTaskRequest> createGetIntrospectableResourceIdsTask(GetIntrospectableResourceIdsTaskRequest value) {
        return new JAXBElement<GetIntrospectableResourceIdsTaskRequest>(_GetIntrospectableResourceIdsTask_QNAME, GetIntrospectableResourceIdsTaskRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetParentResourceRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getParentResource")
    public JAXBElement<GetParentResourceRequest> createGetParentResource(GetParentResourceRequest value) {
        return new JAXBElement<GetParentResourceRequest>(_GetParentResource_QNAME, GetParentResourceRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelDataSourceReintrospectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "cancelDataSourceReintrospectResponse")
    public JAXBElement<CancelDataSourceReintrospectResponse> createCancelDataSourceReintrospectResponse(CancelDataSourceReintrospectResponse value) {
        return new JAXBElement<CancelDataSourceReintrospectResponse>(_CancelDataSourceReintrospectResponse_QNAME, CancelDataSourceReintrospectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataSourceAttributeDefsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getDataSourceAttributeDefsResponse")
    public JAXBElement<GetDataSourceAttributeDefsResponse> createGetDataSourceAttributeDefsResponse(GetDataSourceAttributeDefsResponse value) {
        return new JAXBElement<GetDataSourceAttributeDefsResponse>(_GetDataSourceAttributeDefsResponse_QNAME, GetDataSourceAttributeDefsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDataSourcePortRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateDataSourcePort")
    public JAXBElement<UpdateDataSourcePortRequest> createUpdateDataSourcePort(UpdateDataSourcePortRequest value) {
        return new JAXBElement<UpdateDataSourcePortRequest>(_UpdateDataSourcePort_QNAME, UpdateDataSourcePortRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeResourceOwnerRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "changeResourceOwner")
    public JAXBElement<ChangeResourceOwnerRequest> createChangeResourceOwner(ChangeResourceOwnerRequest value) {
        return new JAXBElement<ChangeResourceOwnerRequest>(_ChangeResourceOwner_QNAME, ChangeResourceOwnerRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateLinkResponse")
    public JAXBElement<UpdateLinkResponse> createUpdateLinkResponse(UpdateLinkResponse value) {
        return new JAXBElement<UpdateLinkResponse>(_UpdateLinkResponse_QNAME, UpdateLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearResourceCacheResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "clearResourceCacheResponse")
    public JAXBElement<ClearResourceCacheResponse> createClearResourceCacheResponse(ClearResourceCacheResponse value) {
        return new JAXBElement<ClearResourceCacheResponse>(_ClearResourceCacheResponse_QNAME, ClearResourceCacheResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyResourcePrivilegesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "copyResourcePrivileges")
    public JAXBElement<CopyResourcePrivilegesRequest> createCopyResourcePrivileges(CopyResourcePrivilegesRequest value) {
        return new JAXBElement<CopyResourcePrivilegesRequest>(_CopyResourcePrivileges_QNAME, CopyResourcePrivilegesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateConnectorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "createConnectorResponse")
    public JAXBElement<CreateConnectorResponse> createCreateConnectorResponse(CreateConnectorResponse value) {
        return new JAXBElement<CreateConnectorResponse>(_CreateConnectorResponse_QNAME, CreateConnectorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyResourceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "copyResourceResponse")
    public JAXBElement<CopyResourceResponse> createCopyResourceResponse(CopyResourceResponse value) {
        return new JAXBElement<CopyResourceResponse>(_CopyResourceResponse_QNAME, CopyResourceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCachedResourceStatisticsConfigRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getCachedResourceStatisticsConfig")
    public JAXBElement<GetCachedResourceStatisticsConfigRequest> createGetCachedResourceStatisticsConfig(GetCachedResourceStatisticsConfigRequest value) {
        return new JAXBElement<GetCachedResourceStatisticsConfigRequest>(_GetCachedResourceStatisticsConfig_QNAME, GetCachedResourceStatisticsConfigRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IntrospectResourcesTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "introspectResourcesTaskResponse")
    public JAXBElement<IntrospectResourcesTaskResponse> createIntrospectResourcesTaskResponse(IntrospectResourcesTaskResponse value) {
        return new JAXBElement<IntrospectResourcesTaskResponse>(_IntrospectResourcesTaskResponse_QNAME, IntrospectResourcesTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RefreshResourceStatisticsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "refreshResourceStatisticsResponse")
    public JAXBElement<RefreshResourceStatisticsResponse> createRefreshResourceStatisticsResponse(RefreshResourceStatisticsResponse value) {
        return new JAXBElement<RefreshResourceStatisticsResponse>(_RefreshResourceStatisticsResponse_QNAME, RefreshResourceStatisticsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestDataSourceConnectionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "testDataSourceConnectionResponse")
    public JAXBElement<TestDataSourceConnectionResponse> createTestDataSourceConnectionResponse(TestDataSourceConnectionResponse value) {
        return new JAXBElement<TestDataSourceConnectionResponse>(_TestDataSourceConnectionResponse_QNAME, TestDataSourceConnectionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyResourcePrivilegesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "copyResourcePrivilegesResponse")
    public JAXBElement<CopyResourcePrivilegesResponse> createCopyResourcePrivilegesResponse(CopyResourcePrivilegesResponse value) {
        return new JAXBElement<CopyResourcePrivilegesResponse>(_CopyResourcePrivilegesResponse_QNAME, CopyResourcePrivilegesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateXSLTProcedureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateXSLTProcedureResponse")
    public JAXBElement<UpdateXSLTProcedureResponse> createUpdateXSLTProcedureResponse(UpdateXSLTProcedureResponse value) {
        return new JAXBElement<UpdateXSLTProcedureResponse>(_UpdateXSLTProcedureResponse_QNAME, UpdateXSLTProcedureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetExtendableDataSourceTypesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getExtendableDataSourceTypes")
    public JAXBElement<GetExtendableDataSourceTypesRequest> createGetExtendableDataSourceTypes(GetExtendableDataSourceTypesRequest value) {
        return new JAXBElement<GetExtendableDataSourceTypesRequest>(_GetExtendableDataSourceTypes_QNAME, GetExtendableDataSourceTypesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResourcePrivilegesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateResourcePrivilegesResponse")
    public JAXBElement<UpdateResourcePrivilegesResponse> createUpdateResourcePrivilegesResponse(UpdateResourcePrivilegesResponse value) {
        return new JAXBElement<UpdateResourcePrivilegesResponse>(_UpdateResourcePrivilegesResponse_QNAME, UpdateResourcePrivilegesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChildResourcesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getChildResources")
    public JAXBElement<GetChildResourcesRequest> createGetChildResources(GetChildResourcesRequest value) {
        return new JAXBElement<GetChildResourcesRequest>(_GetChildResources_QNAME, GetChildResourcesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateResourceRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "createResource")
    public JAXBElement<CreateResourceRequest> createCreateResource(CreateResourceRequest value) {
        return new JAXBElement<CreateResourceRequest>(_CreateResource_QNAME, CreateResourceRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCachedResourceStatisticsConfigRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateCachedResourceStatisticsConfig")
    public JAXBElement<UpdateCachedResourceStatisticsConfigRequest> createUpdateCachedResourceStatisticsConfig(UpdateCachedResourceStatisticsConfigRequest value) {
        return new JAXBElement<UpdateCachedResourceStatisticsConfigRequest>(_UpdateCachedResourceStatisticsConfig_QNAME, UpdateCachedResourceStatisticsConfigRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsedDataSourcesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getUsedDataSources")
    public JAXBElement<GetUsedDataSourcesRequest> createGetUsedDataSources(GetUsedDataSourcesRequest value) {
        return new JAXBElement<GetUsedDataSourcesRequest>(_GetUsedDataSources_QNAME, GetUsedDataSourcesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourcePrivilegesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getResourcePrivilegesResponse")
    public JAXBElement<GetResourcePrivilegesResponse> createGetResourcePrivilegesResponse(GetResourcePrivilegesResponse value) {
        return new JAXBElement<GetResourcePrivilegesResponse>(_GetResourcePrivilegesResponse_QNAME, GetResourcePrivilegesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResourceExistsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "resourceExists")
    public JAXBElement<ResourceExistsRequest> createResourceExists(ResourceExistsRequest value) {
        return new JAXBElement<ResourceExistsRequest>(_ResourceExists_QNAME, ResourceExistsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStreamTransformProcedureRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateStreamTransformProcedure")
    public JAXBElement<UpdateStreamTransformProcedureRequest> createUpdateStreamTransformProcedure(UpdateStreamTransformProcedureRequest value) {
        return new JAXBElement<UpdateStreamTransformProcedureRequest>(_UpdateStreamTransformProcedure_QNAME, UpdateStreamTransformProcedureRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResourceStatisticsConfigRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateResourceStatisticsConfig")
    public JAXBElement<UpdateResourceStatisticsConfigRequest> createUpdateResourceStatisticsConfig(UpdateResourceStatisticsConfigRequest value) {
        return new JAXBElement<UpdateResourceStatisticsConfigRequest>(_UpdateResourceStatisticsConfig_QNAME, UpdateResourceStatisticsConfigRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResourceAnnotationRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateResourceAnnotation")
    public JAXBElement<UpdateResourceAnnotationRequest> createUpdateResourceAnnotation(UpdateResourceAnnotationRequest value) {
        return new JAXBElement<UpdateResourceAnnotationRequest>(_UpdateResourceAnnotation_QNAME, UpdateResourceAnnotationRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCustomDataSourceTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateCustomDataSourceTypeResponse")
    public JAXBElement<UpdateCustomDataSourceTypeResponse> createUpdateCustomDataSourceTypeResponse(UpdateCustomDataSourceTypeResponse value) {
        return new JAXBElement<UpdateCustomDataSourceTypeResponse>(_UpdateCustomDataSourceTypeResponse_QNAME, UpdateCustomDataSourceTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResourceEnabledRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateResourceEnabled")
    public JAXBElement<UpdateResourceEnabledRequest> createUpdateResourceEnabled(UpdateResourceEnabledRequest value) {
        return new JAXBElement<UpdateResourceEnabledRequest>(_UpdateResourceEnabled_QNAME, UpdateResourceEnabledRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsedDataSourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getUsedDataSourcesResponse")
    public JAXBElement<GetUsedDataSourcesResponse> createGetUsedDataSourcesResponse(GetUsedDataSourcesResponse value) {
        return new JAXBElement<GetUsedDataSourcesResponse>(_GetUsedDataSourcesResponse_QNAME, GetUsedDataSourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelDataSourceReintrospectRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "cancelDataSourceReintrospect")
    public JAXBElement<CancelDataSourceReintrospectRequest> createCancelDataSourceReintrospect(CancelDataSourceReintrospectRequest value) {
        return new JAXBElement<CancelDataSourceReintrospectRequest>(_CancelDataSourceReintrospect_QNAME, CancelDataSourceReintrospectRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateLinksRecursivelyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "createLinksRecursivelyResponse")
    public JAXBElement<CreateLinksRecursivelyResponse> createCreateLinksRecursivelyResponse(CreateLinksRecursivelyResponse value) {
        return new JAXBElement<CreateLinksRecursivelyResponse>(_CreateLinksRecursivelyResponse_QNAME, CreateLinksRecursivelyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAncestorResourcesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getAncestorResources")
    public JAXBElement<GetAncestorResourcesRequest> createGetAncestorResources(GetAncestorResourcesRequest value) {
        return new JAXBElement<GetAncestorResourcesRequest>(_GetAncestorResources_QNAME, GetAncestorResourcesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStreamTransformProcedureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateStreamTransformProcedureResponse")
    public JAXBElement<UpdateStreamTransformProcedureResponse> createUpdateStreamTransformProcedureResponse(UpdateStreamTransformProcedureResponse value) {
        return new JAXBElement<UpdateStreamTransformProcedureResponse>(_UpdateStreamTransformProcedureResponse_QNAME, UpdateStreamTransformProcedureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLockedResourcesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getLockedResources")
    public JAXBElement<GetLockedResourcesRequest> createGetLockedResources(GetLockedResourcesRequest value) {
        return new JAXBElement<GetLockedResourcesRequest>(_GetLockedResources_QNAME, GetLockedResourcesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDataSourceChildInfosRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateDataSourceChildInfos")
    public JAXBElement<UpdateDataSourceChildInfosRequest> createUpdateDataSourceChildInfos(UpdateDataSourceChildInfosRequest value) {
        return new JAXBElement<UpdateDataSourceChildInfosRequest>(_UpdateDataSourceChildInfos_QNAME, UpdateDataSourceChildInfosRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCustomDataSourceTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "createCustomDataSourceTypeResponse")
    public JAXBElement<CreateCustomDataSourceTypeResponse> createCreateCustomDataSourceTypeResponse(CreateCustomDataSourceTypeResponse value) {
        return new JAXBElement<CreateCustomDataSourceTypeResponse>(_CreateCustomDataSourceTypeResponse_QNAME, CreateCustomDataSourceTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConnectorsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getConnectorsResponse")
    public JAXBElement<GetConnectorsResponse> createGetConnectorsResponse(GetConnectorsResponse value) {
        return new JAXBElement<GetConnectorsResponse>(_GetConnectorsResponse_QNAME, GetConnectorsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateXQueryTransformProcedureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateXQueryTransformProcedureResponse")
    public JAXBElement<UpdateXQueryTransformProcedureResponse> createUpdateXQueryTransformProcedureResponse(UpdateXQueryTransformProcedureResponse value) {
        return new JAXBElement<UpdateXQueryTransformProcedureResponse>(_UpdateXQueryTransformProcedureResponse_QNAME, UpdateXQueryTransformProcedureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMostRecentIntrospectionStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getMostRecentIntrospectionStatusResponse")
    public JAXBElement<GetMostRecentIntrospectionStatusResponse> createGetMostRecentIntrospectionStatusResponse(GetMostRecentIntrospectionStatusResponse value) {
        return new JAXBElement<GetMostRecentIntrospectionStatusResponse>(_GetMostRecentIntrospectionStatusResponse_QNAME, GetMostRecentIntrospectionStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIntrospectedResourceIdsTaskRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getIntrospectedResourceIdsTask")
    public JAXBElement<GetIntrospectedResourceIdsTaskRequest> createGetIntrospectedResourceIdsTask(GetIntrospectedResourceIdsTaskRequest value) {
        return new JAXBElement<GetIntrospectedResourceIdsTaskRequest>(_GetIntrospectedResourceIdsTask_QNAME, GetIntrospectedResourceIdsTaskRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyCustomDataSourceTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "destroyCustomDataSourceTypeResponse")
    public JAXBElement<DestroyCustomDataSourceTypeResponse> createDestroyCustomDataSourceTypeResponse(DestroyCustomDataSourceTypeResponse value) {
        return new JAXBElement<DestroyCustomDataSourceTypeResponse>(_DestroyCustomDataSourceTypeResponse_QNAME, DestroyCustomDataSourceTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTriggerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateTriggerResponse")
    public JAXBElement<UpdateTriggerResponse> createUpdateTriggerResponse(UpdateTriggerResponse value) {
        return new JAXBElement<UpdateTriggerResponse>(_UpdateTriggerResponse_QNAME, UpdateTriggerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getResourcesResponse")
    public JAXBElement<GetResourcesResponse> createGetResourcesResponse(GetResourcesResponse value) {
        return new JAXBElement<GetResourcesResponse>(_GetResourcesResponse_QNAME, GetResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDataSourceStatisticsConfigRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateDataSourceStatisticsConfig")
    public JAXBElement<UpdateDataSourceStatisticsConfigRequest> createUpdateDataSourceStatisticsConfig(UpdateDataSourceStatisticsConfigRequest value) {
        return new JAXBElement<UpdateDataSourceStatisticsConfigRequest>(_UpdateDataSourceStatisticsConfig_QNAME, UpdateDataSourceStatisticsConfigRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyConnectorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "destroyConnectorResponse")
    public JAXBElement<DestroyConnectorResponse> createDestroyConnectorResponse(DestroyConnectorResponse value) {
        return new JAXBElement<DestroyConnectorResponse>(_DestroyConnectorResponse_QNAME, DestroyConnectorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataSourceAttributeDefsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getDataSourceAttributeDefs")
    public JAXBElement<GetDataSourceAttributeDefsRequest> createGetDataSourceAttributeDefs(GetDataSourceAttributeDefsRequest value) {
        return new JAXBElement<GetDataSourceAttributeDefsRequest>(_GetDataSourceAttributeDefs_QNAME, GetDataSourceAttributeDefsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetParentDataSourceTypeRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getParentDataSourceType")
    public JAXBElement<GetParentDataSourceTypeRequest> createGetParentDataSourceType(GetParentDataSourceTypeRequest value) {
        return new JAXBElement<GetParentDataSourceTypeRequest>(_GetParentDataSourceType_QNAME, GetParentDataSourceTypeRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResourceCacheConfigRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateResourceCacheConfig")
    public JAXBElement<UpdateResourceCacheConfigRequest> createUpdateResourceCacheConfig(UpdateResourceCacheConfigRequest value) {
        return new JAXBElement<UpdateResourceCacheConfigRequest>(_UpdateResourceCacheConfig_QNAME, UpdateResourceCacheConfigRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenameResourceRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "renameResource")
    public JAXBElement<RenameResourceRequest> createRenameResource(RenameResourceRequest value) {
        return new JAXBElement<RenameResourceRequest>(_RenameResource_QNAME, RenameResourceRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResourcePrivilegesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateResourcePrivileges")
    public JAXBElement<UpdateResourcePrivilegesRequest> createUpdateResourcePrivileges(UpdateResourcePrivilegesRequest value) {
        return new JAXBElement<UpdateResourcePrivilegesRequest>(_UpdateResourcePrivileges_QNAME, UpdateResourcePrivilegesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllResourcesByPathRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getAllResourcesByPath")
    public JAXBElement<GetAllResourcesByPathRequest> createGetAllResourcesByPath(GetAllResourcesByPathRequest value) {
        return new JAXBElement<GetAllResourcesByPathRequest>(_GetAllResourcesByPath_QNAME, GetAllResourcesByPathRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResourceStatisticsConfigResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateResourceStatisticsConfigResponse")
    public JAXBElement<UpdateResourceStatisticsConfigResponse> createUpdateResourceStatisticsConfigResponse(UpdateResourceStatisticsConfigResponse value) {
        return new JAXBElement<UpdateResourceStatisticsConfigResponse>(_UpdateResourceStatisticsConfigResponse_QNAME, UpdateResourceStatisticsConfigResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateXQueryTransformProcedureRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateXQueryTransformProcedure")
    public JAXBElement<UpdateXQueryTransformProcedureRequest> createUpdateXQueryTransformProcedure(UpdateXQueryTransformProcedureRequest value) {
        return new JAXBElement<UpdateXQueryTransformProcedureRequest>(_UpdateXQueryTransformProcedure_QNAME, UpdateXQueryTransformProcedureRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransformFunctionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getTransformFunctionsResponse")
    public JAXBElement<GetTransformFunctionsResponse> createGetTransformFunctionsResponse(GetTransformFunctionsResponse value) {
        return new JAXBElement<GetTransformFunctionsResponse>(_GetTransformFunctionsResponse_QNAME, GetTransformFunctionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResourceExistsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "resourceExistsResponse")
    public JAXBElement<ResourceExistsResponse> createResourceExistsResponse(ResourceExistsResponse value) {
        return new JAXBElement<ResourceExistsResponse>(_ResourceExistsResponse_QNAME, ResourceExistsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourceCacheConfigResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getResourceCacheConfigResponse")
    public JAXBElement<GetResourceCacheConfigResponse> createGetResourceCacheConfigResponse(GetResourceCacheConfigResponse value) {
        return new JAXBElement<GetResourceCacheConfigResponse>(_GetResourceCacheConfigResponse_QNAME, GetResourceCacheConfigResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResourceAnnotationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateResourceAnnotationResponse")
    public JAXBElement<UpdateResourceAnnotationResponse> createUpdateResourceAnnotationResponse(UpdateResourceAnnotationResponse value) {
        return new JAXBElement<UpdateResourceAnnotationResponse>(_UpdateResourceAnnotationResponse_QNAME, UpdateResourceAnnotationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataSourceChildResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getDataSourceChildResourcesResponse")
    public JAXBElement<GetDataSourceChildResourcesResponse> createGetDataSourceChildResourcesResponse(GetDataSourceChildResourcesResponse value) {
        return new JAXBElement<GetDataSourceChildResourcesResponse>(_GetDataSourceChildResourcesResponse_QNAME, GetDataSourceChildResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCustomDataSourceTypeRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "createCustomDataSourceType")
    public JAXBElement<CreateCustomDataSourceTypeRequest> createCreateCustomDataSourceType(CreateCustomDataSourceTypeRequest value) {
        return new JAXBElement<CreateCustomDataSourceTypeRequest>(_CreateCustomDataSourceType_QNAME, CreateCustomDataSourceTypeRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResourceCacheConfigResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateResourceCacheConfigResponse")
    public JAXBElement<UpdateResourceCacheConfigResponse> createUpdateResourceCacheConfigResponse(UpdateResourceCacheConfigResponse value) {
        return new JAXBElement<UpdateResourceCacheConfigResponse>(_UpdateResourceCacheConfigResponse_QNAME, UpdateResourceCacheConfigResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "copyResourcesResponse")
    public JAXBElement<CopyResourcesResponse> createCopyResourcesResponse(CopyResourcesResponse value) {
        return new JAXBElement<CopyResourcesResponse>(_CopyResourcesResponse_QNAME, CopyResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourceStatisticsConfigResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getResourceStatisticsConfigResponse")
    public JAXBElement<GetResourceStatisticsConfigResponse> createGetResourceStatisticsConfigResponse(GetResourceStatisticsConfigResponse value) {
        return new JAXBElement<GetResourceStatisticsConfigResponse>(_GetResourceStatisticsConfigResponse_QNAME, GetResourceStatisticsConfigResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCachedResourceStatisticsConfigResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getCachedResourceStatisticsConfigResponse")
    public JAXBElement<GetCachedResourceStatisticsConfigResponse> createGetCachedResourceStatisticsConfigResponse(GetCachedResourceStatisticsConfigResponse value) {
        return new JAXBElement<GetCachedResourceStatisticsConfigResponse>(_GetCachedResourceStatisticsConfigResponse_QNAME, GetCachedResourceStatisticsConfigResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataSourceTypeCustomCapabilitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getDataSourceTypeCustomCapabilitiesResponse")
    public JAXBElement<GetDataSourceTypeCustomCapabilitiesResponse> createGetDataSourceTypeCustomCapabilitiesResponse(GetDataSourceTypeCustomCapabilitiesResponse value) {
        return new JAXBElement<GetDataSourceTypeCustomCapabilitiesResponse>(_GetDataSourceTypeCustomCapabilitiesResponse_QNAME, GetDataSourceTypeCustomCapabilitiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LockResourcesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "lockResources")
    public JAXBElement<LockResourcesRequest> createLockResources(LockResourcesRequest value) {
        return new JAXBElement<LockResourcesRequest>(_LockResources_QNAME, LockResourcesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateConnectorRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "createConnector")
    public JAXBElement<CreateConnectorRequest> createCreateConnector(CreateConnectorRequest value) {
        return new JAXBElement<CreateConnectorRequest>(_CreateConnector_QNAME, CreateConnectorRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIntrospectableResourceIdsResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getIntrospectableResourceIdsResultResponse")
    public JAXBElement<GetIntrospectableResourceIdsResultResponse> createGetIntrospectableResourceIdsResultResponse(GetIntrospectableResourceIdsResultResponse value) {
        return new JAXBElement<GetIntrospectableResourceIdsResultResponse>(_GetIntrospectableResourceIdsResultResponse_QNAME, GetIntrospectableResourceIdsResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDataSourcePortResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateDataSourcePortResponse")
    public JAXBElement<UpdateDataSourcePortResponse> createUpdateDataSourcePortResponse(UpdateDataSourcePortResponse value) {
        return new JAXBElement<UpdateDataSourcePortResponse>(_UpdateDataSourcePortResponse_QNAME, UpdateDataSourcePortResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyResourcesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "copyResources")
    public JAXBElement<CopyResourcesRequest> createCopyResources(CopyResourcesRequest value) {
        return new JAXBElement<CopyResourcesRequest>(_CopyResources_QNAME, CopyResourcesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateImplementationContainerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateImplementationContainerResponse")
    public JAXBElement<UpdateImplementationContainerResponse> createUpdateImplementationContainerResponse(UpdateImplementationContainerResponse value) {
        return new JAXBElement<UpdateImplementationContainerResponse>(_UpdateImplementationContainerResponse_QNAME, UpdateImplementationContainerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyCustomDataSourceTypeRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "destroyCustomDataSourceType")
    public JAXBElement<DestroyCustomDataSourceTypeRequest> createDestroyCustomDataSourceType(DestroyCustomDataSourceTypeRequest value) {
        return new JAXBElement<DestroyCustomDataSourceTypeRequest>(_DestroyCustomDataSourceType_QNAME, DestroyCustomDataSourceTypeRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LockResourceRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "lockResource")
    public JAXBElement<LockResourceRequest> createLockResource(LockResourceRequest value) {
        return new JAXBElement<LockResourceRequest>(_LockResource_QNAME, LockResourceRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIntrospectableResourceIdsResultRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getIntrospectableResourceIdsResult")
    public JAXBElement<GetIntrospectableResourceIdsResultRequest> createGetIntrospectableResourceIdsResult(GetIntrospectableResourceIdsResultRequest value) {
        return new JAXBElement<GetIntrospectableResourceIdsResultRequest>(_GetIntrospectableResourceIdsResult_QNAME, GetIntrospectableResourceIdsResultRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReintrospectDataSourceRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "reintrospectDataSource")
    public JAXBElement<ReintrospectDataSourceRequest> createReintrospectDataSource(ReintrospectDataSourceRequest value) {
        return new JAXBElement<ReintrospectDataSourceRequest>(_ReintrospectDataSource_QNAME, ReintrospectDataSourceRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RefreshResourceStatisticsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "refreshResourceStatistics")
    public JAXBElement<RefreshResourceStatisticsRequest> createRefreshResourceStatistics(RefreshResourceStatisticsRequest value) {
        return new JAXBElement<RefreshResourceStatisticsRequest>(_RefreshResourceStatistics_QNAME, RefreshResourceStatisticsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateDataSourceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "createDataSourceResponse")
    public JAXBElement<CreateDataSourceResponse> createCreateDataSourceResponse(CreateDataSourceResponse value) {
        return new JAXBElement<CreateDataSourceResponse>(_CreateDataSourceResponse_QNAME, CreateDataSourceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateConnectorRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateConnector")
    public JAXBElement<UpdateConnectorRequest> createUpdateConnector(UpdateConnectorRequest value) {
        return new JAXBElement<UpdateConnectorRequest>(_UpdateConnector_QNAME, UpdateConnectorRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveResourceRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "moveResource")
    public JAXBElement<MoveResourceRequest> createMoveResource(MoveResourceRequest value) {
        return new JAXBElement<MoveResourceRequest>(_MoveResource_QNAME, MoveResourceRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourceStatisticsConfigRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getResourceStatisticsConfig")
    public JAXBElement<GetResourceStatisticsConfigRequest> createGetResourceStatisticsConfig(GetResourceStatisticsConfigRequest value) {
        return new JAXBElement<GetResourceStatisticsConfigRequest>(_GetResourceStatisticsConfig_QNAME, GetResourceStatisticsConfigRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTransformProcedureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateTransformProcedureResponse")
    public JAXBElement<UpdateTransformProcedureResponse> createUpdateTransformProcedureResponse(UpdateTransformProcedureResponse value) {
        return new JAXBElement<UpdateTransformProcedureResponse>(_UpdateTransformProcedureResponse_QNAME, UpdateTransformProcedureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetParentResourceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getParentResourceResponse")
    public JAXBElement<GetParentResourceResponse> createGetParentResourceResponse(GetParentResourceResponse value) {
        return new JAXBElement<GetParentResourceResponse>(_GetParentResourceResponse_QNAME, GetParentResourceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IntrospectResourcesTaskRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "introspectResourcesTask")
    public JAXBElement<IntrospectResourcesTaskRequest> createIntrospectResourcesTask(IntrospectResourcesTaskRequest value) {
        return new JAXBElement<IntrospectResourcesTaskRequest>(_IntrospectResourcesTask_QNAME, IntrospectResourcesTaskRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateXsltTransformProcedureRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateXsltTransformProcedure")
    public JAXBElement<UpdateXsltTransformProcedureRequest> createUpdateXsltTransformProcedure(UpdateXsltTransformProcedureRequest value) {
        return new JAXBElement<UpdateXsltTransformProcedureRequest>(_UpdateXsltTransformProcedure_QNAME, UpdateXsltTransformProcedureRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataSourceStatisticsConfigRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getDataSourceStatisticsConfig")
    public JAXBElement<GetDataSourceStatisticsConfigRequest> createGetDataSourceStatisticsConfig(GetDataSourceStatisticsConfigRequest value) {
        return new JAXBElement<GetDataSourceStatisticsConfigRequest>(_GetDataSourceStatisticsConfig_QNAME, GetDataSourceStatisticsConfigRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearResourceCacheRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "clearResourceCache")
    public JAXBElement<ClearResourceCacheRequest> createClearResourceCache(ClearResourceCacheRequest value) {
        return new JAXBElement<ClearResourceCacheRequest>(_ClearResourceCache_QNAME, ClearResourceCacheRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDefinitionSetResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateDefinitionSetResponse")
    public JAXBElement<UpdateDefinitionSetResponse> createUpdateDefinitionSetResponse(UpdateDefinitionSetResponse value) {
        return new JAXBElement<UpdateDefinitionSetResponse>(_UpdateDefinitionSetResponse_QNAME, UpdateDefinitionSetResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLockedResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getLockedResourcesResponse")
    public JAXBElement<GetLockedResourcesResponse> createGetLockedResourcesResponse(GetLockedResourcesResponse value) {
        return new JAXBElement<GetLockedResourcesResponse>(_GetLockedResourcesResponse_QNAME, GetLockedResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransformFunctionsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getTransformFunctions")
    public JAXBElement<GetTransformFunctionsRequest> createGetTransformFunctions(GetTransformFunctionsRequest value) {
        return new JAXBElement<GetTransformFunctionsRequest>(_GetTransformFunctions_QNAME, GetTransformFunctionsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataSourceTypeCustomCapabilitiesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getDataSourceTypeCustomCapabilities")
    public JAXBElement<GetDataSourceTypeCustomCapabilitiesRequest> createGetDataSourceTypeCustomCapabilities(GetDataSourceTypeCustomCapabilitiesRequest value) {
        return new JAXBElement<GetDataSourceTypeCustomCapabilitiesRequest>(_GetDataSourceTypeCustomCapabilities_QNAME, GetDataSourceTypeCustomCapabilitiesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSqlScriptProcedureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateSqlScriptProcedureResponse")
    public JAXBElement<UpdateSqlScriptProcedureResponse> createUpdateSqlScriptProcedureResponse(UpdateSqlScriptProcedureResponse value) {
        return new JAXBElement<UpdateSqlScriptProcedureResponse>(_UpdateSqlScriptProcedureResponse_QNAME, UpdateSqlScriptProcedureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getResourceResponse")
    public JAXBElement<GetResourceResponse> createGetResourceResponse(GetResourceResponse value) {
        return new JAXBElement<GetResourceResponse>(_GetResourceResponse_QNAME, GetResourceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateXSLTProcedureRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateXSLTProcedure")
    public JAXBElement<UpdateXSLTProcedureRequest> createUpdateXSLTProcedure(UpdateXSLTProcedureRequest value) {
        return new JAXBElement<UpdateXSLTProcedureRequest>(_UpdateXSLTProcedure_QNAME, UpdateXSLTProcedureRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenameResourceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "renameResourceResponse")
    public JAXBElement<RenameResourceResponse> createRenameResourceResponse(RenameResourceResponse value) {
        return new JAXBElement<RenameResourceResponse>(_RenameResourceResponse_QNAME, RenameResourceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelResourceStatisticsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "cancelResourceStatistics")
    public JAXBElement<CancelResourceStatisticsRequest> createCancelResourceStatistics(CancelResourceStatisticsRequest value) {
        return new JAXBElement<CancelResourceStatisticsRequest>(_CancelResourceStatistics_QNAME, CancelResourceStatisticsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourceUpdatesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getResourceUpdatesResponse")
    public JAXBElement<GetResourceUpdatesResponse> createGetResourceUpdatesResponse(GetResourceUpdatesResponse value) {
        return new JAXBElement<GetResourceUpdatesResponse>(_GetResourceUpdatesResponse_QNAME, GetResourceUpdatesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConnectorGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getConnectorGroupResponse")
    public JAXBElement<GetConnectorGroupResponse> createGetConnectorGroupResponse(GetConnectorGroupResponse value) {
        return new JAXBElement<GetConnectorGroupResponse>(_GetConnectorGroupResponse_QNAME, GetConnectorGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "destroyResourcesResponse")
    public JAXBElement<DestroyResourcesResponse> createDestroyResourcesResponse(DestroyResourcesResponse value) {
        return new JAXBElement<DestroyResourcesResponse>(_DestroyResourcesResponse_QNAME, DestroyResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsedResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getUsedResourcesResponse")
    public JAXBElement<GetUsedResourcesResponse> createGetUsedResourcesResponse(GetUsedResourcesResponse value) {
        return new JAXBElement<GetUsedResourcesResponse>(_GetUsedResourcesResponse_QNAME, GetUsedResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourceStatsSummaryRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getResourceStatsSummary")
    public JAXBElement<GetResourceStatsSummaryRequest> createGetResourceStatsSummary(GetResourceStatsSummaryRequest value) {
        return new JAXBElement<GetResourceStatsSummaryRequest>(_GetResourceStatsSummary_QNAME, GetResourceStatsSummaryRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RebindResourcesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "rebindResources")
    public JAXBElement<RebindResourcesRequest> createRebindResources(RebindResourcesRequest value) {
        return new JAXBElement<RebindResourcesRequest>(_RebindResources_QNAME, RebindResourcesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetExtendableDataSourceTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getExtendableDataSourceTypesResponse")
    public JAXBElement<GetExtendableDataSourceTypesResponse> createGetExtendableDataSourceTypesResponse(GetExtendableDataSourceTypesResponse value) {
        return new JAXBElement<GetExtendableDataSourceTypesResponse>(_GetExtendableDataSourceTypesResponse_QNAME, GetExtendableDataSourceTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDataSourceChildInfosWithFilterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateDataSourceChildInfosWithFilterResponse")
    public JAXBElement<UpdateDataSourceChildInfosWithFilterResponse> createUpdateDataSourceChildInfosWithFilterResponse(UpdateDataSourceChildInfosWithFilterResponse value) {
        return new JAXBElement<UpdateDataSourceChildInfosWithFilterResponse>(_UpdateDataSourceChildInfosWithFilterResponse_QNAME, UpdateDataSourceChildInfosWithFilterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsedDSResourcesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getUsedDSResources")
    public JAXBElement<GetUsedDSResourcesRequest> createGetUsedDSResources(GetUsedDSResourcesRequest value) {
        return new JAXBElement<GetUsedDSResourcesRequest>(_GetUsedDSResources_QNAME, GetUsedDSResourcesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDataSourceRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateDataSource")
    public JAXBElement<UpdateDataSourceRequest> createUpdateDataSource(UpdateDataSourceRequest value) {
        return new JAXBElement<UpdateDataSourceRequest>(_UpdateDataSource_QNAME, UpdateDataSourceRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDataServicePortRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateDataServicePort")
    public JAXBElement<UpdateDataServicePortRequest> createUpdateDataServicePort(UpdateDataServicePortRequest value) {
        return new JAXBElement<UpdateDataServicePortRequest>(_UpdateDataServicePort_QNAME, UpdateDataServicePortRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDataSourceTypeCustomCapabilitiesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateDataSourceTypeCustomCapabilities")
    public JAXBElement<UpdateDataSourceTypeCustomCapabilitiesRequest> createUpdateDataSourceTypeCustomCapabilities(UpdateDataSourceTypeCustomCapabilitiesRequest value) {
        return new JAXBElement<UpdateDataSourceTypeCustomCapabilitiesRequest>(_UpdateDataSourceTypeCustomCapabilities_QNAME, UpdateDataSourceTypeCustomCapabilitiesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIntrospectedResourceIdsResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getIntrospectedResourceIdsResultResponse")
    public JAXBElement<GetIntrospectedResourceIdsResultResponse> createGetIntrospectedResourceIdsResultResponse(GetIntrospectedResourceIdsResultResponse value) {
        return new JAXBElement<GetIntrospectedResourceIdsResultResponse>(_GetIntrospectedResourceIdsResultResponse_QNAME, GetIntrospectedResourceIdsResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConnectorsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getConnectors")
    public JAXBElement<GetConnectorsRequest> createGetConnectors(GetConnectorsRequest value) {
        return new JAXBElement<GetConnectorsRequest>(_GetConnectors_QNAME, GetConnectorsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllResourcesByPathResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getAllResourcesByPathResponse")
    public JAXBElement<GetAllResourcesByPathResponse> createGetAllResourcesByPathResponse(GetAllResourcesByPathResponse value) {
        return new JAXBElement<GetAllResourcesByPathResponse>(_GetAllResourcesByPathResponse_QNAME, GetAllResourcesByPathResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveResourceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "moveResourceResponse")
    public JAXBElement<MoveResourceResponse> createMoveResourceResponse(MoveResourceResponse value) {
        return new JAXBElement<MoveResourceResponse>(_MoveResourceResponse_QNAME, MoveResourceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConnectorGroupNamesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getConnectorGroupNamesResponse")
    public JAXBElement<GetConnectorGroupNamesResponse> createGetConnectorGroupNamesResponse(GetConnectorGroupNamesResponse value) {
        return new JAXBElement<GetConnectorGroupNamesResponse>(_GetConnectorGroupNamesResponse_QNAME, GetConnectorGroupNamesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConnectorGroupRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getConnectorGroup")
    public JAXBElement<GetConnectorGroupRequest> createGetConnectorGroup(GetConnectorGroupRequest value) {
        return new JAXBElement<GetConnectorGroupRequest>(_GetConnectorGroup_QNAME, GetConnectorGroupRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateBasicTransformProcedureRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateBasicTransformProcedure")
    public JAXBElement<UpdateBasicTransformProcedureRequest> createUpdateBasicTransformProcedure(UpdateBasicTransformProcedureRequest value) {
        return new JAXBElement<UpdateBasicTransformProcedureRequest>(_UpdateBasicTransformProcedure_QNAME, UpdateBasicTransformProcedureRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataSourceChildResourcesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getDataSourceChildResources")
    public JAXBElement<GetDataSourceChildResourcesRequest> createGetDataSourceChildResources(GetDataSourceChildResourcesRequest value) {
        return new JAXBElement<GetDataSourceChildResourcesRequest>(_GetDataSourceChildResources_QNAME, GetDataSourceChildResourcesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateResourceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "createResourceResponse")
    public JAXBElement<CreateResourceResponse> createCreateResourceResponse(CreateResourceResponse value) {
        return new JAXBElement<CreateResourceResponse>(_CreateResourceResponse_QNAME, CreateResourceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAncestorResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getAncestorResourcesResponse")
    public JAXBElement<GetAncestorResourcesResponse> createGetAncestorResourcesResponse(GetAncestorResourcesResponse value) {
        return new JAXBElement<GetAncestorResourcesResponse>(_GetAncestorResourcesResponse_QNAME, GetAncestorResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIntrospectionAttributesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getIntrospectionAttributesResponse")
    public JAXBElement<GetIntrospectionAttributesResponse> createGetIntrospectionAttributesResponse(GetIntrospectionAttributesResponse value) {
        return new JAXBElement<GetIntrospectionAttributesResponse>(_GetIntrospectionAttributesResponse_QNAME, GetIntrospectionAttributesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyResourcesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "destroyResources")
    public JAXBElement<DestroyResourcesRequest> createDestroyResources(DestroyResourcesRequest value) {
        return new JAXBElement<DestroyResourcesRequest>(_DestroyResources_QNAME, DestroyResourcesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnlockResourceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "unlockResourceResponse")
    public JAXBElement<UnlockResourceResponse> createUnlockResourceResponse(UnlockResourceResponse value) {
        return new JAXBElement<UnlockResourceResponse>(_UnlockResourceResponse_QNAME, UnlockResourceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCachedResourceStatisticsConfigResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateCachedResourceStatisticsConfigResponse")
    public JAXBElement<UpdateCachedResourceStatisticsConfigResponse> createUpdateCachedResourceStatisticsConfigResponse(UpdateCachedResourceStatisticsConfigResponse value) {
        return new JAXBElement<UpdateCachedResourceStatisticsConfigResponse>(_UpdateCachedResourceStatisticsConfigResponse_QNAME, UpdateCachedResourceStatisticsConfigResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearIntrospectableResourceIdCacheRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "clearIntrospectableResourceIdCache")
    public JAXBElement<ClearIntrospectableResourceIdCacheRequest> createClearIntrospectableResourceIdCache(ClearIntrospectableResourceIdCacheRequest value) {
        return new JAXBElement<ClearIntrospectableResourceIdCacheRequest>(_ClearIntrospectableResourceIdCache_QNAME, ClearIntrospectableResourceIdCacheRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeResourceOwnerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "changeResourceOwnerResponse")
    public JAXBElement<ChangeResourceOwnerResponse> createChangeResourceOwnerResponse(ChangeResourceOwnerResponse value) {
        return new JAXBElement<ChangeResourceOwnerResponse>(_ChangeResourceOwnerResponse_QNAME, ChangeResourceOwnerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDataSourceChildInfosWithFilterRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateDataSourceChildInfosWithFilter")
    public JAXBElement<UpdateDataSourceChildInfosWithFilterRequest> createUpdateDataSourceChildInfosWithFilter(UpdateDataSourceChildInfosWithFilterRequest value) {
        return new JAXBElement<UpdateDataSourceChildInfosWithFilterRequest>(_UpdateDataSourceChildInfosWithFilter_QNAME, UpdateDataSourceChildInfosWithFilterRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSqlTableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateSqlTableResponse")
    public JAXBElement<UpdateSqlTableResponse> createUpdateSqlTableResponse(UpdateSqlTableResponse value) {
        return new JAXBElement<UpdateSqlTableResponse>(_UpdateSqlTableResponse_QNAME, UpdateSqlTableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateResourcesResponse")
    public JAXBElement<UpdateResourcesResponse> createUpdateResourcesResponse(UpdateResourcesResponse value) {
        return new JAXBElement<UpdateResourcesResponse>(_UpdateResourcesResponse_QNAME, UpdateResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIntrospectionAttributeDefsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "getIntrospectionAttributeDefsResponse")
    public JAXBElement<GetIntrospectionAttributeDefsResponse> createGetIntrospectionAttributeDefsResponse(GetIntrospectionAttributeDefsResponse value) {
        return new JAXBElement<GetIntrospectionAttributeDefsResponse>(_GetIntrospectionAttributeDefsResponse_QNAME, GetIntrospectionAttributeDefsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSqlScriptProcedureRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateSqlScriptProcedure")
    public JAXBElement<UpdateSqlScriptProcedureRequest> createUpdateSqlScriptProcedure(UpdateSqlScriptProcedureRequest value) {
        return new JAXBElement<UpdateSqlScriptProcedureRequest>(_UpdateSqlScriptProcedure_QNAME, UpdateSqlScriptProcedureRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateExternalSqlProcedureRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateExternalSqlProcedure")
    public JAXBElement<UpdateExternalSqlProcedureRequest> createUpdateExternalSqlProcedure(UpdateExternalSqlProcedureRequest value) {
        return new JAXBElement<UpdateExternalSqlProcedureRequest>(_UpdateExternalSqlProcedure_QNAME, UpdateExternalSqlProcedureRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateImplementationContainerRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "updateImplementationContainer")
    public JAXBElement<UpdateImplementationContainerRequest> createUpdateImplementationContainer(UpdateImplementationContainerRequest value) {
        return new JAXBElement<UpdateImplementationContainerRequest>(_UpdateImplementationContainer_QNAME, UpdateImplementationContainerRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "actionType", scope = TriggerResource.class)
    public JAXBElement<String> createTriggerResourceActionType(String value) {
        return new JAXBElement<String>(_TriggerResourceActionType_QNAME, String.class, TriggerResource.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "conditionAttributes", scope = TriggerResource.class)
    public JAXBElement<AttributeList> createTriggerResourceConditionAttributes(AttributeList value) {
        return new JAXBElement<AttributeList>(_TriggerResourceConditionAttributes_QNAME, AttributeList.class, TriggerResource.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Schedule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "conditionSchedule", scope = TriggerResource.class)
    public JAXBElement<Schedule> createTriggerResourceConditionSchedule(Schedule value) {
        return new JAXBElement<Schedule>(_TriggerResourceConditionSchedule_QNAME, Schedule.class, TriggerResource.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "maxEventsQueued", scope = TriggerResource.class)
    public JAXBElement<BigInteger> createTriggerResourceMaxEventsQueued(BigInteger value) {
        return new JAXBElement<BigInteger>(_TriggerResourceMaxEventsQueued_QNAME, BigInteger.class, TriggerResource.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "conditionType", scope = TriggerResource.class)
    public JAXBElement<String> createTriggerResourceConditionType(String value) {
        return new JAXBElement<String>(_TriggerResourceConditionType_QNAME, String.class, TriggerResource.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "actionAttributes", scope = TriggerResource.class)
    public JAXBElement<AttributeList> createTriggerResourceActionAttributes(AttributeList value) {
        return new JAXBElement<AttributeList>(_TriggerResourceActionAttributes_QNAME, AttributeList.class, TriggerResource.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/resource", name = "enabled", scope = TriggerResource.class)
    public JAXBElement<Boolean> createTriggerResourceEnabled(Boolean value) {
        return new JAXBElement<Boolean>(_TriggerResourceEnabled_QNAME, Boolean.class, TriggerResource.class, value);
    }

}
