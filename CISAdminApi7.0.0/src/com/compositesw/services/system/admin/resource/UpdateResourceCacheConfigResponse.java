
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for updateResourceCacheConfigResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateResourceCacheConfigResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="cacheConfig" type="{http://www.compositesw.com/services/system/admin/resource}cacheConfig" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateResourceCacheConfigResponse", propOrder = {
    "cacheConfig"
})
public class UpdateResourceCacheConfigResponse
    extends BaseResponse
{

    protected CacheConfig cacheConfig;

    /**
     * Gets the value of the cacheConfig property.
     * 
     * @return
     *     possible object is
     *     {@link CacheConfig }
     *     
     */
    public CacheConfig getCacheConfig() {
        return cacheConfig;
    }

    /**
     * Sets the value of the cacheConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link CacheConfig }
     *     
     */
    public void setCacheConfig(CacheConfig value) {
        this.cacheConfig = value;
    }

}
