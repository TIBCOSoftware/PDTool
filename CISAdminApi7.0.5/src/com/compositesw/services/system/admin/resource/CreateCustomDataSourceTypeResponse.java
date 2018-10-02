
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createCustomDataSourceTypeResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createCustomDataSourceTypeResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}resourcesResponse">
 *       &lt;sequence>
 *         &lt;element name="dataSourceType" type="{http://www.compositesw.com/services/system/admin/resource}dataSourceTypeInfo"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createCustomDataSourceTypeResponse", propOrder = {
    "dataSourceType"
})
public class CreateCustomDataSourceTypeResponse
    extends ResourcesResponse
{

    @XmlElement(required = true)
    protected DataSourceTypeInfo dataSourceType;

    /**
     * Gets the value of the dataSourceType property.
     * 
     * @return
     *     possible object is
     *     {@link DataSourceTypeInfo }
     *     
     */
    public DataSourceTypeInfo getDataSourceType() {
        return dataSourceType;
    }

    /**
     * Sets the value of the dataSourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataSourceTypeInfo }
     *     
     */
    public void setDataSourceType(DataSourceTypeInfo value) {
        this.dataSourceType = value;
    }

}
