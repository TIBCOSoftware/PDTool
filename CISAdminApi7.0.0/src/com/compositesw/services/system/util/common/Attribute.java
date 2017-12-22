
package com.compositesw.services.system.util.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attribute complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attribute">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.compositesw.com/services/system/util/common}attributeType"/>
 *         &lt;element name="value" type="{http://www.compositesw.com/services/system/util/common}attributeSimpleValue" minOccurs="0"/>
 *         &lt;element name="valueList" type="{http://www.compositesw.com/services/system/util/common}attributeTypeValueList" minOccurs="0"/>
 *         &lt;element name="valueMap" type="{http://www.compositesw.com/services/system/util/common}attributeTypeValueMap" minOccurs="0"/>
 *         &lt;element name="valueArray" type="{http://www.compositesw.com/services/system/util/common}attributeSimpleValueList" minOccurs="0"/>
 *         &lt;element name="unset" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attribute", propOrder = {
    "name",
    "type",
    "value",
    "valueList",
    "valueMap",
    "valueArray",
    "unset"
})
public class Attribute {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected AttributeType type;
    protected String value;
    protected AttributeTypeValueList valueList;
    protected AttributeTypeValueMap valueMap;
    protected AttributeSimpleValueList valueArray;
    protected Boolean unset;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeType }
     *     
     */
    public AttributeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeType }
     *     
     */
    public void setType(AttributeType value) {
        this.type = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the valueList property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeTypeValueList }
     *     
     */
    public AttributeTypeValueList getValueList() {
        return valueList;
    }

    /**
     * Sets the value of the valueList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeTypeValueList }
     *     
     */
    public void setValueList(AttributeTypeValueList value) {
        this.valueList = value;
    }

    /**
     * Gets the value of the valueMap property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeTypeValueMap }
     *     
     */
    public AttributeTypeValueMap getValueMap() {
        return valueMap;
    }

    /**
     * Sets the value of the valueMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeTypeValueMap }
     *     
     */
    public void setValueMap(AttributeTypeValueMap value) {
        this.valueMap = value;
    }

    /**
     * Gets the value of the valueArray property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeSimpleValueList }
     *     
     */
    public AttributeSimpleValueList getValueArray() {
        return valueArray;
    }

    /**
     * Sets the value of the valueArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeSimpleValueList }
     *     
     */
    public void setValueArray(AttributeSimpleValueList value) {
        this.valueArray = value;
    }

    /**
     * Gets the value of the unset property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUnset() {
        return unset;
    }

    /**
     * Sets the value of the unset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUnset(Boolean value) {
        this.unset = value;
    }

}
