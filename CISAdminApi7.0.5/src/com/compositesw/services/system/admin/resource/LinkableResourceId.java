
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for linkableResourceId complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="linkableResourceId">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resourceId" type="{http://www.compositesw.com/services/system/admin/resource}pathTypeSubtype"/>
 *         &lt;element name="linkTargetPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "linkableResourceId", propOrder = {
    "resourceId",
    "linkTargetPath",
    "displayName"
})
public class LinkableResourceId {

    @XmlElement(required = true)
    protected PathTypeSubtype resourceId;
    protected String linkTargetPath;
    protected String displayName;

    /**
     * Gets the value of the resourceId property.
     * 
     * @return
     *     possible object is
     *     {@link PathTypeSubtype }
     *     
     */
    public PathTypeSubtype getResourceId() {
        return resourceId;
    }

    /**
     * Sets the value of the resourceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathTypeSubtype }
     *     
     */
    public void setResourceId(PathTypeSubtype value) {
        this.resourceId = value;
    }

    /**
     * Gets the value of the linkTargetPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkTargetPath() {
        return linkTargetPath;
    }

    /**
     * Sets the value of the linkTargetPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkTargetPath(String value) {
        this.linkTargetPath = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

}
