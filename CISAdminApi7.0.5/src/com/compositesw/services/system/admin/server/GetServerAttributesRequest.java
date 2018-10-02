
package com.compositesw.services.system.admin.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getServerAttributesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getServerAttributesRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/server}pathsRequest">
 *       &lt;sequence>
 *         &lt;element name="getAllAttributes" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getServerAttributesRequest", propOrder = {
    "getAllAttributes"
})
public class GetServerAttributesRequest
    extends PathsRequest
{

    protected Boolean getAllAttributes;

    /**
     * Gets the value of the getAllAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGetAllAttributes() {
        return getAllAttributes;
    }

    /**
     * Sets the value of the getAllAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGetAllAttributes(Boolean value) {
        this.getAllAttributes = value;
    }

}
