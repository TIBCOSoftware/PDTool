
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for changeResourceOwnerRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="changeResourceOwnerRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}pathTypeDetailRequest">
 *       &lt;sequence>
 *         &lt;element name="newOwnerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="newOwnerDomain" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currentOwnerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currentOwnerDomain" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recurse" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "changeResourceOwnerRequest", propOrder = {
    "newOwnerName",
    "newOwnerDomain",
    "currentOwnerName",
    "currentOwnerDomain",
    "recurse"
})
public class ChangeResourceOwnerRequest
    extends PathTypeDetailRequest
{

    @XmlElement(required = true)
    protected String newOwnerName;
    protected String newOwnerDomain;
    protected String currentOwnerName;
    protected String currentOwnerDomain;
    @XmlElement(defaultValue = "false")
    protected Boolean recurse;

    /**
     * Gets the value of the newOwnerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewOwnerName() {
        return newOwnerName;
    }

    /**
     * Sets the value of the newOwnerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewOwnerName(String value) {
        this.newOwnerName = value;
    }

    /**
     * Gets the value of the newOwnerDomain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewOwnerDomain() {
        return newOwnerDomain;
    }

    /**
     * Sets the value of the newOwnerDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewOwnerDomain(String value) {
        this.newOwnerDomain = value;
    }

    /**
     * Gets the value of the currentOwnerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentOwnerName() {
        return currentOwnerName;
    }

    /**
     * Sets the value of the currentOwnerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentOwnerName(String value) {
        this.currentOwnerName = value;
    }

    /**
     * Gets the value of the currentOwnerDomain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentOwnerDomain() {
        return currentOwnerDomain;
    }

    /**
     * Sets the value of the currentOwnerDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentOwnerDomain(String value) {
        this.currentOwnerDomain = value;
    }

    /**
     * Gets the value of the recurse property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRecurse() {
        return recurse;
    }

    /**
     * Sets the value of the recurse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRecurse(Boolean value) {
        this.recurse = value;
    }

}
