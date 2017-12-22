
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for updateConnectorResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateConnectorResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="connector" type="{http://www.compositesw.com/services/system/admin/resource}connector"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateConnectorResponse", propOrder = {
    "connector"
})
public class UpdateConnectorResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected Connector connector;

    /**
     * Gets the value of the connector property.
     * 
     * @return
     *     possible object is
     *     {@link Connector }
     *     
     */
    public Connector getConnector() {
        return connector;
    }

    /**
     * Sets the value of the connector property.
     * 
     * @param value
     *     allowed object is
     *     {@link Connector }
     *     
     */
    public void setConnector(Connector value) {
        this.connector = value;
    }

}
