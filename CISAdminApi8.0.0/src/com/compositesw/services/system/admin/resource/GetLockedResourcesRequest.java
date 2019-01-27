
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;
import com.compositesw.services.system.util.common.DetailLevel;


/**
 * <p>Java class for getLockedResourcesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getLockedResourcesRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="detail" type="{http://www.compositesw.com/services/system/util/common}detailLevel"/>
 *         &lt;element name="includeOnlyUnlockableResources" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLockedResourcesRequest", propOrder = {
    "detail",
    "includeOnlyUnlockableResources"
})
public class GetLockedResourcesRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected DetailLevel detail;
    protected Boolean includeOnlyUnlockableResources;

    /**
     * Gets the value of the detail property.
     * 
     * @return
     *     possible object is
     *     {@link DetailLevel }
     *     
     */
    public DetailLevel getDetail() {
        return detail;
    }

    /**
     * Sets the value of the detail property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailLevel }
     *     
     */
    public void setDetail(DetailLevel value) {
        this.detail = value;
    }

    /**
     * Gets the value of the includeOnlyUnlockableResources property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeOnlyUnlockableResources() {
        return includeOnlyUnlockableResources;
    }

    /**
     * Sets the value of the includeOnlyUnlockableResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeOnlyUnlockableResources(Boolean value) {
        this.includeOnlyUnlockableResources = value;
    }

}
