
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeList;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getDataSourceTypeCustomCapabilitiesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getDataSourceTypeCustomCapabilitiesResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="customCapabilities" type="{http://www.compositesw.com/services/system/util/common}attributeList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDataSourceTypeCustomCapabilitiesResponse", propOrder = {
    "customCapabilities"
})
public class GetDataSourceTypeCustomCapabilitiesResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected AttributeList customCapabilities;

    /**
     * Gets the value of the customCapabilities property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeList }
     *     
     */
    public AttributeList getCustomCapabilities() {
        return customCapabilities;
    }

    /**
     * Sets the value of the customCapabilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeList }
     *     
     */
    public void setCustomCapabilities(AttributeList value) {
        this.customCapabilities = value;
    }

}
