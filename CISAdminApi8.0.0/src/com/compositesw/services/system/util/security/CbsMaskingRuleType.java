
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cbsMaskingRuleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cbsMaskingRuleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="domainName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userGroupName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isGroup" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isDefaultRule" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="selectableString" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ruleType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cbsMaskingRuleType", propOrder = {
    "domainName",
    "userGroupName",
    "isGroup",
    "isDefaultRule",
    "selectableString",
    "ruleType"
})
public class CbsMaskingRuleType {

    @XmlElement(required = true)
    protected String domainName;
    @XmlElement(required = true)
    protected String userGroupName;
    protected boolean isGroup;
    protected boolean isDefaultRule;
    @XmlElement(required = true)
    protected String selectableString;
    @XmlElement(required = true)
    protected String ruleType;

    /**
     * Gets the value of the domainName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * Sets the value of the domainName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomainName(String value) {
        this.domainName = value;
    }

    /**
     * Gets the value of the userGroupName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserGroupName() {
        return userGroupName;
    }

    /**
     * Sets the value of the userGroupName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserGroupName(String value) {
        this.userGroupName = value;
    }

    /**
     * Gets the value of the isGroup property.
     * 
     */
    public boolean isIsGroup() {
        return isGroup;
    }

    /**
     * Sets the value of the isGroup property.
     * 
     */
    public void setIsGroup(boolean value) {
        this.isGroup = value;
    }

    /**
     * Gets the value of the isDefaultRule property.
     * 
     */
    public boolean isIsDefaultRule() {
        return isDefaultRule;
    }

    /**
     * Sets the value of the isDefaultRule property.
     * 
     */
    public void setIsDefaultRule(boolean value) {
        this.isDefaultRule = value;
    }

    /**
     * Gets the value of the selectableString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelectableString() {
        return selectableString;
    }

    /**
     * Sets the value of the selectableString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelectableString(String value) {
        this.selectableString = value;
    }

    /**
     * Gets the value of the ruleType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuleType() {
        return ruleType;
    }

    /**
     * Sets the value of the ruleType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuleType(String value) {
        this.ruleType = value;
    }

}
