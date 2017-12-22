
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for updateDataSourceStatisticsConfigResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateDataSourceStatisticsConfigResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="dataSourceStatisticsConfig" type="{http://www.compositesw.com/services/system/admin/resource}dataSourceStatisticsConfig" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateDataSourceStatisticsConfigResponse", propOrder = {
    "dataSourceStatisticsConfig"
})
public class UpdateDataSourceStatisticsConfigResponse
    extends BaseResponse
{

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
