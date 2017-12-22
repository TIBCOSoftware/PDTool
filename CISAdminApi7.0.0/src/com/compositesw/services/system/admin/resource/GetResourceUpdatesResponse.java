
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getResourceUpdatesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getResourceUpdatesResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="resourceUpdates" type="{http://www.compositesw.com/services/system/admin/resource}resourceUpdates" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getResourceUpdatesResponse", propOrder = {
    "resourceUpdates"
})
public class GetResourceUpdatesResponse
    extends BaseResponse
{

    protected ResourceUpdates resourceUpdates;

    /**
     * Gets the value of the resourceUpdates property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceUpdates }
     *     
     */
    public ResourceUpdates getResourceUpdates() {
        return resourceUpdates;
    }

    /**
     * Sets the value of the resourceUpdates property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceUpdates }
     *     
     */
    public void setResourceUpdates(ResourceUpdates value) {
        this.resourceUpdates = value;
    }

}
