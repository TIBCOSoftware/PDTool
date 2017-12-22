
package com.compositesw.services.system.admin.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getClusterConfigResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getClusterConfigResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="clusterConfig" type="{http://www.compositesw.com/services/system/admin/server}clusterConfig"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getClusterConfigResponse", propOrder = {
    "clusterConfig"
})
public class GetClusterConfigResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected ClusterConfig clusterConfig;

    /**
     * Gets the value of the clusterConfig property.
     * 
     * @return
     *     possible object is
     *     {@link ClusterConfig }
     *     
     */
    public ClusterConfig getClusterConfig() {
        return clusterConfig;
    }

    /**
     * Sets the value of the clusterConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClusterConfig }
     *     
     */
    public void setClusterConfig(ClusterConfig value) {
        this.clusterConfig = value;
    }

}
