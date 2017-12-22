
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getResourceStatisticsConfigResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getResourceStatisticsConfigResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="statisticsConfig" type="{http://www.compositesw.com/services/system/admin/resource}resourceStatisticsConfig"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getResourceStatisticsConfigResponse", propOrder = {
    "statisticsConfig"
})
public class GetResourceStatisticsConfigResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected ResourceStatisticsConfig statisticsConfig;

    /**
     * Gets the value of the statisticsConfig property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceStatisticsConfig }
     *     
     */
    public ResourceStatisticsConfig getStatisticsConfig() {
        return statisticsConfig;
    }

    /**
     * Sets the value of the statisticsConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceStatisticsConfig }
     *     
     */
    public void setStatisticsConfig(ResourceStatisticsConfig value) {
        this.statisticsConfig = value;
    }

}
