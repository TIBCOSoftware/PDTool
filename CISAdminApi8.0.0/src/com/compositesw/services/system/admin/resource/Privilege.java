
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for privilege complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="privilege">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="domain" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nameType" type="{http://www.compositesw.com/services/system/admin/resource}userNameType"/>
 *         &lt;element name="privs" type="{http://www.compositesw.com/services/system/admin/resource}privList"/>
 *         &lt;element name="combinedPrivs" type="{http://www.compositesw.com/services/system/admin/resource}privList" minOccurs="0"/>
 *         &lt;element name="inheritedPrivs" type="{http://www.compositesw.com/services/system/admin/resource}privList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "privilege", propOrder = {
    "domain",
    "name",
    "nameType",
    "privs",
    "combinedPrivs",
    "inheritedPrivs"
})
public class Privilege {

    @XmlElement(required = true)
    protected String domain;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected UserNameType nameType;
    @XmlElement(required = true)
    protected String privs;
    protected String combinedPrivs;
    protected String inheritedPrivs;

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomain(String value) {
        this.domain = value;
    }

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
     * Gets the value of the nameType property.
     * 
     * @return
     *     possible object is
     *     {@link UserNameType }
     *     
     */
    public UserNameType getNameType() {
        return nameType;
    }

    /**
     * Sets the value of the nameType property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserNameType }
     *     
     */
    public void setNameType(UserNameType value) {
        this.nameType = value;
    }

    /**
     * Gets the value of the privs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrivs() {
        return privs;
    }

    /**
     * Sets the value of the privs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrivs(String value) {
        this.privs = value;
    }

    /**
     * Gets the value of the combinedPrivs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCombinedPrivs() {
        return combinedPrivs;
    }

    /**
     * Sets the value of the combinedPrivs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCombinedPrivs(String value) {
        this.combinedPrivs = value;
    }

    /**
     * Gets the value of the inheritedPrivs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInheritedPrivs() {
        return inheritedPrivs;
    }

    /**
     * Sets the value of the inheritedPrivs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInheritedPrivs(String value) {
        this.inheritedPrivs = value;
    }

}
