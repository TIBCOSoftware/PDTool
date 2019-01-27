
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bucketPropertiesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bucketPropertiesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bucketCatalog" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bucketSchema" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bucketPrefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numBuckets" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bucketPropertiesType", propOrder = {
    "bucketCatalog",
    "bucketSchema",
    "bucketPrefix",
    "numBuckets"
})
public class BucketPropertiesType {

    protected String bucketCatalog;
    protected String bucketSchema;
    protected String bucketPrefix;
    protected int numBuckets;

    /**
     * Gets the value of the bucketCatalog property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBucketCatalog() {
        return bucketCatalog;
    }

    /**
     * Sets the value of the bucketCatalog property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBucketCatalog(String value) {
        this.bucketCatalog = value;
    }

    /**
     * Gets the value of the bucketSchema property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBucketSchema() {
        return bucketSchema;
    }

    /**
     * Sets the value of the bucketSchema property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBucketSchema(String value) {
        this.bucketSchema = value;
    }

    /**
     * Gets the value of the bucketPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBucketPrefix() {
        return bucketPrefix;
    }

    /**
     * Sets the value of the bucketPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBucketPrefix(String value) {
        this.bucketPrefix = value;
    }

    /**
     * Gets the value of the numBuckets property.
     * 
     */
    public int getNumBuckets() {
        return numBuckets;
    }

    /**
     * Sets the value of the numBuckets property.
     * 
     */
    public void setNumBuckets(int value) {
        this.numBuckets = value;
    }

}
