
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for resourcesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resourcesResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="resources" type="{http://www.compositesw.com/services/system/admin/resource}resourceList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resourcesResponse", propOrder = {
    "resources"
})
@XmlSeeAlso({
    UpdateTriggerResponse.class,
    GetResourcesResponse.class,
    UpdateExternalSqlProcedureResponse.class,
    UpdateBasicTransformProcedureResponse.class,
    CreateCustomDataSourceTypeResponse.class,
    UpdateXQueryTransformProcedureResponse.class,
    UpdateDataServicePortResponse.class,
    CreateLinkResponse.class,
    UpdateResourceEnabledResponse.class,
    UpdateDataSourceResponse.class,
    GetParentDataSourceTypeResponse.class,
    LockResourcesResponse.class,
    UpdateDataSourcePortResponse.class,
    UpdateImplementationContainerResponse.class,
    GetChildResourcesResponse.class,
    UpdateResourceAnnotationResponse.class,
    UnlockResourcesResponse.class,
    GetDataSourceChildResourcesResponse.class,
    UpdateTransformProcedureResponse.class,
    GetParentResourceResponse.class,
    GetUsedDSResourcesResponse.class,
    UpdateXQueryProcedureResponse.class,
    CreateDataSourceResponse.class,
    LockResourceResponse.class,
    UpdateXsltTransformProcedureResponse.class,
    GetDependentResourcesResponse.class,
    UpdateSqlScriptProcedureResponse.class,
    GetResourceResponse.class,
    UpdateDefinitionSetResponse.class,
    GetLockedResourcesResponse.class,
    UpdateLinkResponse.class,
    GetUsedResourcesResponse.class,
    GetAncestorResourcesResponse.class,
    CreateResourceResponse.class,
    UpdateXSLTProcedureResponse.class,
    GetAllResourcesByPathResponse.class,
    CreateLinksRecursivelyResponse.class,
    UpdateStreamTransformProcedureResponse.class,
    UpdateCustomDataSourceTypeResponse.class,
    ChangeResourceOwnerResponse.class,
    UpdateSqlTableResponse.class,
    UnlockResourceResponse.class,
    UpdateOperationProcedureResponse.class
})
public class ResourcesResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected ResourceList resources;

    /**
     * Gets the value of the resources property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceList }
     *     
     */
    public ResourceList getResources() {
        return resources;
    }

    /**
     * Sets the value of the resources property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceList }
     *     
     */
    public void setResources(ResourceList value) {
        this.resources = value;
    }

}
