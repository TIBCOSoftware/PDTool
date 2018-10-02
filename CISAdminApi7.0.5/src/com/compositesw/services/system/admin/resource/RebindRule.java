
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rebindRule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rebindRule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oldPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oldType" type="{http://www.compositesw.com/services/system/admin/resource}resourceType"/>
 *         &lt;element name="newPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="newType" type="{http://www.compositesw.com/services/system/admin/resource}resourceType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rebindRule", propOrder = {
    "oldPath",
    "oldType",
    "newPath",
    "newType"
})
public class RebindRule {

    @XmlElement(required = true)
    protected String oldPath;
    @XmlElement(required = true)
    protected ResourceType oldType;
    @XmlElement(required = true)
    protected String newPath;
    @XmlElement(required = true)
    protected ResourceType newType;

    /**
     * Gets the value of the oldPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldPath() {
        return oldPath;
    }

    /**
     * Sets the value of the oldPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldPath(String value) {
        this.oldPath = value;
    }

    /**
     * Gets the value of the oldType property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceType }
     *     
     */
    public ResourceType getOldType() {
        return oldType;
    }

    /**
     * Sets the value of the oldType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceType }
     *     
     */
    public void setOldType(ResourceType value) {
        this.oldType = value;
    }

    /**
     * Gets the value of the newPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewPath() {
        return newPath;
    }

    /**
     * Sets the value of the newPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewPath(String value) {
        this.newPath = value;
    }

    /**
     * Gets the value of the newType property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceType }
     *     
     */
    public ResourceType getNewType() {
        return newType;
    }

    /**
     * Sets the value of the newType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceType }
     *     
     */
    public void setNewType(ResourceType value) {
        this.newType = value;
    }

}
