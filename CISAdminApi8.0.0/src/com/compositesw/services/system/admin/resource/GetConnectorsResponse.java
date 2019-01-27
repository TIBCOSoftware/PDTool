
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getConnectorsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getConnectorsResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="connectors" type="{http://www.compositesw.com/services/system/admin/resource}connectorList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getConnectorsResponse", propOrder = {
    "connectors"
})
public class GetConnectorsResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected ConnectorList connectors;

    /**
     * Gets the value of the connectors property.
     * 
     * @return
     *     possible object is
     *     {@link ConnectorList }
     *     
     */
    public ConnectorList getConnectors() {
        return connectors;
    }

    /**
     * Sets the value of the connectors property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConnectorList }
     *     
     */
    public void setConnectors(ConnectorList value) {
        this.connectors = value;
    }

}
