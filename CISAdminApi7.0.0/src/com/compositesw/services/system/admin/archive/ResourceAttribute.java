
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.admin.resource.PathTypePair;
import com.compositesw.services.system.util.common.Attribute;


/**
 * <p>Java class for resourceAttribute complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resourceAttribute">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resource" type="{http://www.compositesw.com/services/system/admin/resource}pathTypePair"/>
 *         &lt;element name="attribute" type="{http://www.compositesw.com/services/system/util/common}attribute"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resourceAttribute", propOrder = {
    "resource",
    "attribute"
})
public class ResourceAttribute {

    @XmlElement(required = true)
    protected PathTypePair resource;
    @XmlElement(required = true)
    protected Attribute attribute;

    /**
     * Gets the value of the resource property.
     * 
     * @return
     *     possible object is
     *     {@link PathTypePair }
     *     
     */
    public PathTypePair getResource() {
        return resource;
    }

    /**
     * Sets the value of the resource property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathTypePair }
     *     
     */
    public void setResource(PathTypePair value) {
        this.resource = value;
    }

    /**
     * Gets the value of the attribute property.
     * 
     * @return
     *     possible object is
     *     {@link Attribute }
     *     
     */
    public Attribute getAttribute() {
        return attribute;
    }

    /**
     * Sets the value of the attribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link Attribute }
     *     
     */
    public void setAttribute(Attribute value) {
        this.attribute = value;
    }

}
