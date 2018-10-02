
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for lockState complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lockState">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lockOwnerDomain" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lockOwnerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lockCreateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="lockParentPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lockState", propOrder = {
    "lockOwnerDomain",
    "lockOwnerName",
    "lockCreateTime",
    "lockParentPath"
})
public class LockState {

    @XmlElement(required = true)
    protected String lockOwnerDomain;
    @XmlElement(required = true)
    protected String lockOwnerName;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lockCreateTime;
    protected String lockParentPath;

    /**
     * Gets the value of the lockOwnerDomain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLockOwnerDomain() {
        return lockOwnerDomain;
    }

    /**
     * Sets the value of the lockOwnerDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLockOwnerDomain(String value) {
        this.lockOwnerDomain = value;
    }

    /**
     * Gets the value of the lockOwnerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLockOwnerName() {
        return lockOwnerName;
    }

    /**
     * Sets the value of the lockOwnerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLockOwnerName(String value) {
        this.lockOwnerName = value;
    }

    /**
     * Gets the value of the lockCreateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLockCreateTime() {
        return lockCreateTime;
    }

    /**
     * Sets the value of the lockCreateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLockCreateTime(XMLGregorianCalendar value) {
        this.lockCreateTime = value;
    }

    /**
     * Gets the value of the lockParentPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLockParentPath() {
        return lockParentPath;
    }

    /**
     * Sets the value of the lockParentPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLockParentPath(String value) {
        this.lockParentPath = value;
    }

}
