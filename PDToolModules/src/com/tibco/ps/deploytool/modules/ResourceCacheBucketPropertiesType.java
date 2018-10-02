//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-257 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.09.30 at 05:10:56 PM EDT 
//


package com.tibco.ps.deploytool.modules;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Bucket Properties Type: Define the bucket properties Present when bucketMode is "AUTO_GEN", ignored otherwise.
 * 			
 * 
 * <p>Java class for ResourceCacheBucketPropertiesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResourceCacheBucketPropertiesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bucketCatalog" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bucketSchema" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bucketPrefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numBuckets" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResourceCacheBucketPropertiesType", propOrder = {
    "bucketCatalog",
    "bucketSchema",
    "bucketPrefix",
    "numBuckets"
})
public class ResourceCacheBucketPropertiesType {

    protected String bucketCatalog;
    protected String bucketSchema;
    protected String bucketPrefix;
    @XmlElement(required = true)
    protected BigInteger numBuckets;

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
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumBuckets() {
        return numBuckets;
    }

    /**
     * Sets the value of the numBuckets property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumBuckets(BigInteger value) {
        this.numBuckets = value;
    }

}
