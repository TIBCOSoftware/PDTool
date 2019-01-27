
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getConnectorGroupNamesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getConnectorGroupNamesResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="names" type="{http://www.compositesw.com/services/system/admin/resource}connectorGroupNameList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getConnectorGroupNamesResponse", propOrder = {
    "names"
})
public class GetConnectorGroupNamesResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected ConnectorGroupNameList names;

    /**
     * Gets the value of the names property.
     * 
     * @return
     *     possible object is
     *     {@link ConnectorGroupNameList }
     *     
     */
    public ConnectorGroupNameList getNames() {
        return names;
    }

    /**
     * Sets the value of the names property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConnectorGroupNameList }
     *     
     */
    public void setNames(ConnectorGroupNameList value) {
        this.names = value;
    }

}
