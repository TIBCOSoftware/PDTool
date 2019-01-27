
package com.compositesw.services.system.util.security;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getSecurityBundleResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSecurityBundleResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="projectRoot" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bundleName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isUpdateable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="instanceCount" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="annotation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSecurityBundleResponse", propOrder = {
    "id",
    "projectRoot",
    "bundleName",
    "isEnabled",
    "isUpdateable",
    "instanceCount",
    "annotation"
})
public class GetSecurityBundleResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String projectRoot;
    @XmlElement(required = true)
    protected String bundleName;
    protected boolean isEnabled;
    protected boolean isUpdateable;
    @XmlElement(required = true)
    protected BigInteger instanceCount;
    protected String annotation;

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
     * Gets the value of the projectRoot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectRoot() {
        return projectRoot;
    }

    /**
     * Sets the value of the projectRoot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectRoot(String value) {
        this.projectRoot = value;
    }

    /**
     * Gets the value of the bundleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBundleName() {
        return bundleName;
    }

    /**
     * Sets the value of the bundleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBundleName(String value) {
        this.bundleName = value;
    }

    /**
     * Gets the value of the isEnabled property.
     * 
     */
    public boolean isIsEnabled() {
        return isEnabled;
    }

    /**
     * Sets the value of the isEnabled property.
     * 
     */
    public void setIsEnabled(boolean value) {
        this.isEnabled = value;
    }

    /**
     * Gets the value of the isUpdateable property.
     * 
     */
    public boolean isIsUpdateable() {
        return isUpdateable;
    }

    /**
     * Sets the value of the isUpdateable property.
     * 
     */
    public void setIsUpdateable(boolean value) {
        this.isUpdateable = value;
    }

    /**
     * Gets the value of the instanceCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getInstanceCount() {
        return instanceCount;
    }

    /**
     * Sets the value of the instanceCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setInstanceCount(BigInteger value) {
        this.instanceCount = value;
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

}
