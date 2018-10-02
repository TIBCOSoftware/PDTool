
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateDataSourceStatisticsConfigRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateDataSourceStatisticsConfigRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}pathTypeDetailRequest">
 *       &lt;sequence>
 *         &lt;element name="dataSourceStatisticsConfig" type="{http://www.compositesw.com/services/system/admin/resource}dataSourceStatisticsConfig"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateDataSourceStatisticsConfigRequest", propOrder = {
    "dataSourceStatisticsConfig"
})
public class UpdateDataSourceStatisticsConfigRequest
    extends PathTypeDetailRequest
{

    @XmlElement(required = true)
    protected DataSourceStatisticsConfig dataSourceStatisticsConfig;

    /**
     * Gets the value of the dataSourceStatisticsConfig property.
     * 
     * @return
     *     possible object is
     *     {@link DataSourceStatisticsConfig }
     *     
     */
    public DataSourceStatisticsConfig getDataSourceStatisticsConfig() {
        return dataSourceStatisticsConfig;
    }

    /**
     * Sets the value of the dataSourceStatisticsConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataSourceStatisticsConfig }
     *     
     */
    public void setDataSourceStatisticsConfig(DataSourceStatisticsConfig value) {
        this.dataSourceStatisticsConfig = value;
    }

}
