
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeList;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for updateDataSourceTypeCustomCapabilitiesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateDataSourceTypeCustomCapabilitiesRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="dataSourceTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "updateDataSourceTypeCustomCapabilitiesRequest", propOrder = {
    "dataSourceTypeName",
    "customCapabilities"
})
public class UpdateDataSourceTypeCustomCapabilitiesRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected String dataSourceTypeName;
    @XmlElement(required = true)
    protected AttributeList customCapabilities;

    /**
     * Gets the value of the dataSourceTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSourceTypeName() {
        return dataSourceTypeName;
    }

    /**
     * Sets the value of the dataSourceTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSourceTypeName(String value) {
        this.dataSourceTypeName = value;
    }

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
