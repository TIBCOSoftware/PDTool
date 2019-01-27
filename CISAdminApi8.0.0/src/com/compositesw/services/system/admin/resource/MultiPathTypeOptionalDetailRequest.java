
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.DetailLevel;


/**
 * <p>Java class for multiPathTypeOptionalDetailRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="multiPathTypeOptionalDetailRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}multiPathTypeOptionalRequest">
 *       &lt;sequence>
 *         &lt;element name="detail" type="{http://www.compositesw.com/services/system/util/common}detailLevel"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "multiPathTypeOptionalDetailRequest", propOrder = {
    "detail"
})
@XmlSeeAlso({
    GetResourcesRequest.class
})
public class MultiPathTypeOptionalDetailRequest
    extends MultiPathTypeOptionalRequest
{

    @XmlElement(required = true)
    protected DetailLevel detail;

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

}
