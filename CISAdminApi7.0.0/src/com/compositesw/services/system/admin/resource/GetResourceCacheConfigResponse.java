
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getResourceCacheConfigResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getResourceCacheConfigResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="cacheConfig" type="{http://www.compositesw.com/services/system/admin/resource}cacheConfig"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getResourceCacheConfigResponse", propOrder = {
    "cacheConfig"
})
public class GetResourceCacheConfigResponse
    extends BaseResponse
{

    @XmlElement(required = true)
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
