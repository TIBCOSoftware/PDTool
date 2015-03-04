
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
    UpdateDefinitionSetResponse.class,
    UpdateCustomDataSourceTypeResponse.class,
    UpdateLinkResponse.class,
    GetAllResourcesByPathResponse.class,
    UpdateSqlScriptProcedureResponse.class,
    GetResourceResponse.class,
    CreateLinkResponse.class,
    GetResourcesResponse.class,
    UpdateBasicTransformProcedureResponse.class,
    LockResourceResponse.class,
    LockResourcesResponse.class,
    GetParentDataSourceTypeResponse.class,
    CreateResourceResponse.class,
    GetDependentResourcesResponse.class,
    UpdateTriggerResponse.class,
    UpdateXQueryProcedureResponse.class,
    UpdateTransformProcedureResponse.class,
    GetParentResourceResponse.class,
    CreateLinksRecursivelyResponse.class,
    UpdateXQueryTransformProcedureResponse.class,
    UpdateXsltTransformProcedureResponse.class,
    UnlockResourceResponse.class,
    UpdateResourceEnabledResponse.class,
    UpdateDataSourcePortResponse.class,
    UpdateSqlTableResponse.class,
    UpdateStreamTransformProcedureResponse.class,
    CreateCustomDataSourceTypeResponse.class,
    GetLockedResourcesResponse.class,
    UpdateImplementationContainerResponse.class,
    GetUsedResourcesResponse.class,
    UpdateDataServicePortResponse.class,
    GetAncestorResourcesResponse.class,
    GetChildResourcesResponse.class,
    UnlockResourcesResponse.class,
    ChangeResourceOwnerResponse.class,
    UpdateExternalSqlProcedureResponse.class,
    GetDataSourceChildResourcesResponse.class,
    UpdateDataSourceResponse.class,
    UpdateResourceAnnotationResponse.class,
    CreateDataSourceResponse.class,
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
