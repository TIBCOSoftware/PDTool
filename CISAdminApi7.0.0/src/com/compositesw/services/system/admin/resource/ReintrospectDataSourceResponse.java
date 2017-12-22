
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reintrospectDataSourceResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reintrospectDataSourceResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}reintrospectResponse">
 *       &lt;sequence>
 *         &lt;element name="reintrospectId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reintrospectDataSourceResponse", propOrder = {
    "reintrospectId"
})
public class ReintrospectDataSourceResponse
    extends ReintrospectResponse
{

    protected String reintrospectId;

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

}
