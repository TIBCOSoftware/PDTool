//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.13 at 12:40:03 PM EDT 
//


package com.tibco.ps.deploytool.modules;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 			Introspect Data Source Plan Entry Resource Id Type: The path, type, and subtype of the resource to be introspected.
 * 			Resource paths are relative to the data source.  An empty path (i.e. "") identifies the data source itself.
 * 		
 * 
 * &lt;p&gt;Java class for IntrospectDataSourcePlanEntryResourceIdType complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="IntrospectDataSourcePlanEntryResourceIdType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="resourcePath" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="resourceType" type="{http://www.tibco.com/ps/deploytool/modules}ResourceTypeSimpleType"/&amp;gt;
 *         &amp;lt;element name="subtype" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IntrospectDataSourcePlanEntryResourceIdType", propOrder = {
    "resourcePath",
    "resourceType",
    "subtype"
})
public class IntrospectDataSourcePlanEntryResourceIdType {

    @XmlElement(required = true, nillable = true)
    protected String resourcePath;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ResourceTypeSimpleType resourceType;
    @XmlElement(required = true, nillable = true)
    protected String subtype;

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

}
