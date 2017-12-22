
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateDataServicePortRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateDataServicePortRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}updateBasePortRequest">
 *       &lt;sequence>
 *         &lt;element name="implementation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="alternateURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateDataServicePortRequest", propOrder = {
    "implementation",
    "alternateURL"
})
public class UpdateDataServicePortRequest
    extends UpdateBasePortRequest
{

    protected String implementation;
    protected String alternateURL;

    /**
     * Gets the value of the implementation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImplementation() {
        return implementation;
    }

    /**
     * Sets the value of the implementation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImplementation(String value) {
        this.implementation = value;
    }

    /**
     * Gets the value of the alternateURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlternateURL() {
        return alternateURL;
    }

    /**
     * Sets the value of the alternateURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlternateURL(String value) {
        this.alternateURL = value;
    }

}
