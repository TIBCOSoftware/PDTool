
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for statsColumn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="statsColumn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flags" type="{http://www.compositesw.com/services/system/admin/resource}statsColumnConfigType"/>
 *         &lt;element name="columnMin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="columnMax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="columnDistinct" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numBuckets" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="enableColumnOverride" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="columnDataType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "statsColumn", propOrder = {
    "name",
    "flags",
    "columnMin",
    "columnMax",
    "columnDistinct",
    "numBuckets",
    "enableColumnOverride",
    "columnDataType"
})
public class StatsColumn {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected StatsColumnConfigType flags;
    protected String columnMin;
    protected String columnMax;
    protected Integer columnDistinct;
    protected Integer numBuckets;
    protected Boolean enableColumnOverride;
    protected String columnDataType;

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
     * Gets the value of the flags property.
     * 
     * @return
     *     possible object is
     *     {@link StatsColumnConfigType }
     *     
     */
    public StatsColumnConfigType getFlags() {
        return flags;
    }

    /**
     * Sets the value of the flags property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatsColumnConfigType }
     *     
     */
    public void setFlags(StatsColumnConfigType value) {
        this.flags = value;
    }

    /**
     * Gets the value of the columnMin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnMin() {
        return columnMin;
    }

    /**
     * Sets the value of the columnMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnMin(String value) {
        this.columnMin = value;
    }

    /**
     * Gets the value of the columnMax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnMax() {
        return columnMax;
    }

    /**
     * Sets the value of the columnMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnMax(String value) {
        this.columnMax = value;
    }

    /**
     * Gets the value of the columnDistinct property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getColumnDistinct() {
        return columnDistinct;
    }

    /**
     * Sets the value of the columnDistinct property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setColumnDistinct(Integer value) {
        this.columnDistinct = value;
    }

    /**
     * Gets the value of the numBuckets property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumBuckets() {
        return numBuckets;
    }

    /**
     * Sets the value of the numBuckets property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumBuckets(Integer value) {
        this.numBuckets = value;
    }

    /**
     * Gets the value of the enableColumnOverride property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnableColumnOverride() {
        return enableColumnOverride;
    }

    /**
     * Sets the value of the enableColumnOverride property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnableColumnOverride(Boolean value) {
        this.enableColumnOverride = value;
    }

    /**
     * Gets the value of the columnDataType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnDataType() {
        return columnDataType;
    }

    /**
     * Sets the value of the columnDataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnDataType(String value) {
        this.columnDataType = value;
    }

}
