
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for getParentDataSourceTypeRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getParentDataSourceTypeRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="selfDataSourceTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getParentDataSourceTypeRequest", propOrder = {
    "selfDataSourceTypeName"
})
public class GetParentDataSourceTypeRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected String selfDataSourceTypeName;

    /**
     * Gets the value of the selfDataSourceTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelfDataSourceTypeName() {
        return selfDataSourceTypeName;
    }

    /**
     * Sets the value of the selfDataSourceTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelfDataSourceTypeName(String value) {
        this.selfDataSourceTypeName = value;
    }

}
