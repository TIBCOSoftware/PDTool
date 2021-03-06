//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.13 at 12:40:03 PM EDT 
//


package com.tibco.ps.deploytool.modules;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 			Attribute Definitions Data Source Type: an iteration of display attributes from getDataSourceAttributeDefs.
 * 		
 * 
 * &lt;p&gt;Java class for AttributeDefsDataSourceType complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="AttributeDefsDataSourceType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="resourcePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="resourceType" type="{http://www.tibco.com/ps/deploytool/modules}ResourceTypeSimpleType" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="subtype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="childCount" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="dataSourceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="attributeDefs" type="{http://www.tibco.com/ps/deploytool/modules}AttributeDefDataSourceType" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributeDefsDataSourceType", propOrder = {
    "id",
    "resourcePath",
    "resourceType",
    "subtype",
    "childCount",
    "dataSourceType",
    "attributeDefs"
})
public class AttributeDefsDataSourceType {

    @XmlElement(required = true)
    protected String id;
    protected String resourcePath;
    @XmlSchemaType(name = "string")
    protected ResourceTypeSimpleType resourceType;
    protected String subtype;
    protected BigInteger childCount;
    protected String dataSourceType;
    protected AttributeDefDataSourceType attributeDefs;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the resourcePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourcePath() {
        return resourcePath;
    }

    /**
     * Sets the value of the resourcePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourcePath(String value) {
        this.resourcePath = value;
    }

    /**
     * Gets the value of the resourceType property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceTypeSimpleType }
     *     
     */
    public ResourceTypeSimpleType getResourceType() {
        return resourceType;
    }

    /**
     * Sets the value of the resourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceTypeSimpleType }
     *     
     */
    public void setResourceType(ResourceTypeSimpleType value) {
        this.resourceType = value;
    }

    /**
     * Gets the value of the subtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubtype() {
        return subtype;
    }

    /**
     * Sets the value of the subtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubtype(String value) {
        this.subtype = value;
    }

    /**
     * Gets the value of the childCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getChildCount() {
        return childCount;
    }

    /**
     * Sets the value of the childCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setChildCount(BigInteger value) {
        this.childCount = value;
    }

    /**
     * Gets the value of the dataSourceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSourceType() {
        return dataSourceType;
    }

    /**
     * Sets the value of the dataSourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSourceType(String value) {
        this.dataSourceType = value;
    }

    /**
     * Gets the value of the attributeDefs property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeDefDataSourceType }
     *     
     */
    public AttributeDefDataSourceType getAttributeDefs() {
        return attributeDefs;
    }

    /**
     * Sets the value of the attributeDefs property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeDefDataSourceType }
     *     
     */
    public void setAttributeDefs(AttributeDefDataSourceType value) {
        this.attributeDefs = value;
    }

}
