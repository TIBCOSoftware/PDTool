
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getCachedResourceStatisticsConfigResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getCachedResourceStatisticsConfigResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="cachedStatisticsConfig" type="{http://www.compositesw.com/services/system/admin/resource}cachedStatisticsConfig"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCachedResourceStatisticsConfigResponse", propOrder = {
    "cachedStatisticsConfig"
})
public class GetCachedResourceStatisticsConfigResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected CachedStatisticsConfig cachedStatisticsConfig;

    /**
     * Gets the value of the cachedStatisticsConfig property.
     * 
     * @return
     *     possible object is
     *     {@link CachedStatisticsConfig }
     *     
     */
    public CachedStatisticsConfig getCachedStatisticsConfig() {
        return cachedStatisticsConfig;
    }

    /**
     * Sets the value of the cachedStatisticsConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link CachedStatisticsConfig }
     *     
     */
    public void setCachedStatisticsConfig(CachedStatisticsConfig value) {
        this.cachedStatisticsConfig = value;
    }

}
