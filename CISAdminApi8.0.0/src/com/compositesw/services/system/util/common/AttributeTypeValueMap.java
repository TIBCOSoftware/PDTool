
package com.compositesw.services.system.util.common;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attributeTypeValueMap complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attributeTypeValueMap">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}attributeComplexValue">
 *       &lt;sequence>
 *         &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="key" type="{http://www.compositesw.com/services/system/util/common}attributeTypeValue"/>
 *                   &lt;element name="value" type="{http://www.compositesw.com/services/system/util/common}attributeTypeValue"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attributeTypeValueMap", propOrder = {
    "entry"
})
public class AttributeTypeValueMap
    extends AttributeComplexValue
{

    protected List<AttributeTypeValueMap.Entry> entry;

    /**
     * Gets the value of the entry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the entry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeTypeValueMap.Entry }
     * 
     * 
     */
    public List<AttributeTypeValueMap.Entry> getEntry() {
        if (entry == null) {
            entry = new ArrayList<AttributeTypeValueMap.Entry>();
        }
        return this.entry;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="key" type="{http://www.compositesw.com/services/system/util/common}attributeTypeValue"/>
     *         &lt;element name="value" type="{http://www.compositesw.com/services/system/util/common}attributeTypeValue"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "key",
        "value"
    })
    public static class Entry {

        @XmlElement(required = true)
        protected AttributeTypeValue key;
        @XmlElement(required = true)
        protected AttributeTypeValue value;

        /**
         * Gets the value of the key property.
         * 
         * @return
         *     possible object is
         *     {@link AttributeTypeValue }
         *     
         */
        public AttributeTypeValue getKey() {
            return key;
        }

        /**
         * Sets the value of the key property.
         * 
         * @param value
         *     allowed object is
         *     {@link AttributeTypeValue }
         *     
         */
        public void setKey(AttributeTypeValue value) {
            this.key = value;
        }

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link AttributeTypeValue }
         *     
         */
        public AttributeTypeValue getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link AttributeTypeValue }
         *     
         */
        public void setValue(AttributeTypeValue value) {
            this.value = value;
        }

    }

}
