
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createResourceRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createResourceRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}pathNameDetailRequest">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://www.compositesw.com/services/system/admin/resource}resourceType"/>
 *         &lt;element name="subtype" type="{http://www.compositesw.com/services/system/admin/resource}resourceSubType"/>
 *         &lt;element name="annotation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resource" type="{http://www.compositesw.com/services/system/admin/resource}resource" minOccurs="0"/>
 *         &lt;element name="resourceBundle" type="{http://www.compositesw.com/services/system/admin/resource}resourceBundle" minOccurs="0"/>
 *         &lt;element name="includeOwnership" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createResourceRequest", propOrder = {
    "type",
    "subtype",
    "annotation",
    "resource",
    "resourceBundle",
    "includeOwnership"
})
public class CreateResourceRequest
    extends PathNameDetailRequest
{

    @XmlElement(required = true)
    protected ResourceType type;
    @XmlElement(required = true)
    protected ResourceSubType subtype;
    protected String annotation;
    protected Resource resource;
    protected ResourceBundle resourceBundle;
    protected Boolean includeOwnership;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceType }
     *     
     */
    public ResourceType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceType }
     *     
     */
    public void setType(ResourceType value) {
        this.type = value;
    }

    /**
     * Gets the value of the subtype property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceSubType }
     *     
     */
    public ResourceSubType getSubtype() {
        return subtype;
    }

    /**
     * Sets the value of the subtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceSubType }
     *     
     */
    public void setSubtype(ResourceSubType value) {
        this.subtype = value;
    }

    /**
     * Gets the value of the annotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * Sets the value of the annotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnotation(String value) {
        this.annotation = value;
    }

    /**
     * Gets the value of the resource property.
     * 
     * @return
     *     possible object is
     *     {@link Resource }
     *     
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * Sets the value of the resource property.
     * 
     * @param value
     *     allowed object is
     *     {@link Resource }
     *     
     */
    public void setResource(Resource value) {
        this.resource = value;
    }

    /**
     * Gets the value of the resourceBundle property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceBundle }
     *     
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    /**
     * Sets the value of the resourceBundle property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceBundle }
     *     
     */
    public void setResourceBundle(ResourceBundle value) {
        this.resourceBundle = value;
    }

    /**
     * Gets the value of the includeOwnership property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeOwnership() {
        return includeOwnership;
    }

    /**
     * Sets the value of the includeOwnership property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeOwnership(Boolean value) {
        this.includeOwnership = value;
    }

}
