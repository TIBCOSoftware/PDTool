
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for getDataSourceReintrospectResultRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getDataSourceReintrospectResultRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="reintrospectId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isBlocking" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDataSourceReintrospectResultRequest", propOrder = {
    "reintrospectId",
    "isBlocking"
})
public class GetDataSourceReintrospectResultRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected String reintrospectId;
    protected boolean isBlocking;

    /**
     * Gets the value of the reintrospectId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReintrospectId() {
        return reintrospectId;
    }

    /**
     * Sets the value of the reintrospectId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReintrospectId(String value) {
        this.reintrospectId = value;
    }

    /**
     * Gets the value of the isBlocking property.
     * 
     */
    public boolean isIsBlocking() {
        return isBlocking;
    }

    /**
     * Sets the value of the isBlocking property.
     * 
     */
    public void setIsBlocking(boolean value) {
        this.isBlocking = value;
    }

}
