
package com.compositesw.services.system.admin.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getServerAttributeDefsRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getServerAttributeDefsRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/server}pathsRequest">
 *       &lt;sequence>
 *         &lt;element name="getAllAttributeDefs" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getServerAttributeDefsRequest", propOrder = {
    "getAllAttributeDefs"
})
public class GetServerAttributeDefsRequest
    extends PathsRequest
{

    protected Boolean getAllAttributeDefs;

    /**
     * Gets the value of the getAllAttributeDefs property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGetAllAttributeDefs() {
        return getAllAttributeDefs;
    }

    /**
     * Sets the value of the getAllAttributeDefs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGetAllAttributeDefs(Boolean value) {
        this.getAllAttributeDefs = value;
    }

}
