
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getParentDataSourceTypeResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getParentDataSourceTypeResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}resourcesResponse">
 *       &lt;sequence>
 *         &lt;element name="parentDataSourceType" type="{http://www.compositesw.com/services/system/admin/resource}dataSourceTypeInfo"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getParentDataSourceTypeResponse", propOrder = {
    "parentDataSourceType"
})
public class GetParentDataSourceTypeResponse
    extends ResourcesResponse
{

    @XmlElement(required = true)
    protected DataSourceTypeInfo parentDataSourceType;

    /**
     * Gets the value of the parentDataSourceType property.
     * 
     * @return
     *     possible object is
     *     {@link DataSourceTypeInfo }
     *     
     */
    public DataSourceTypeInfo getParentDataSourceType() {
        return parentDataSourceType;
    }

    /**
     * Sets the value of the parentDataSourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataSourceTypeInfo }
     *     
     */
    public void setParentDataSourceType(DataSourceTypeInfo value) {
        this.parentDataSourceType = value;
    }

}
