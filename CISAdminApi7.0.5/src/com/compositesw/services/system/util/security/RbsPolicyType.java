
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rbsPolicyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rbsPolicyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="folder" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="form" type="{http://www.compositesw.com/services/system/util/security}rbsPolicyFormType"/>
 *         &lt;element name="policyGroup" type="{http://www.compositesw.com/services/system/util/security}rbsPolicyGroupType" minOccurs="0"/>
 *         &lt;element name="defaultRule" type="{http://www.compositesw.com/services/system/util/security}rbsDefaultRuleType" minOccurs="0"/>
 *         &lt;element name="memberRuleList" type="{http://www.compositesw.com/services/system/util/security}rbsMemberRuleListType"/>
 *         &lt;element name="assignmentList" type="{http://www.compositesw.com/services/system/util/security}rbsAssignmentListType"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rbsPolicyType", propOrder = {
    "name",
    "folder",
    "enabled",
    "form",
    "policyGroup",
    "defaultRule",
    "memberRuleList",
    "assignmentList",
    "notes"
})
public class RbsPolicyType {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String folder;
    @XmlElement(defaultValue = "false")
    protected boolean enabled;
    @XmlElement(required = true)
    protected RbsPolicyFormType form;
    protected RbsPolicyGroupType policyGroup;
    protected RbsDefaultRuleType defaultRule;
    @XmlElement(required = true)
    protected RbsMemberRuleListType memberRuleList;
    @XmlElement(required = true)
    protected RbsAssignmentListType assignmentList;
    protected String notes;

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
     * Gets the value of the folder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolder() {
        return folder;
    }

    /**
     * Sets the value of the folder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolder(String value) {
        this.folder = value;
    }

    /**
     * Gets the value of the enabled property.
     * 
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     */
    public void setEnabled(boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the form property.
     * 
     * @return
     *     possible object is
     *     {@link RbsPolicyFormType }
     *     
     */
    public RbsPolicyFormType getForm() {
        return form;
    }

    /**
     * Sets the value of the form property.
     * 
     * @param value
     *     allowed object is
     *     {@link RbsPolicyFormType }
     *     
     */
    public void setForm(RbsPolicyFormType value) {
        this.form = value;
    }

    /**
     * Gets the value of the policyGroup property.
     * 
     * @return
     *     possible object is
     *     {@link RbsPolicyGroupType }
     *     
     */
    public RbsPolicyGroupType getPolicyGroup() {
        return policyGroup;
    }

    /**
     * Sets the value of the policyGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link RbsPolicyGroupType }
     *     
     */
    public void setPolicyGroup(RbsPolicyGroupType value) {
        this.policyGroup = value;
    }

    /**
     * Gets the value of the defaultRule property.
     * 
     * @return
     *     possible object is
     *     {@link RbsDefaultRuleType }
     *     
     */
    public RbsDefaultRuleType getDefaultRule() {
        return defaultRule;
    }

    /**
     * Sets the value of the defaultRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link RbsDefaultRuleType }
     *     
     */
    public void setDefaultRule(RbsDefaultRuleType value) {
        this.defaultRule = value;
    }

    /**
     * Gets the value of the memberRuleList property.
     * 
     * @return
     *     possible object is
     *     {@link RbsMemberRuleListType }
     *     
     */
    public RbsMemberRuleListType getMemberRuleList() {
        return memberRuleList;
    }

    /**
     * Sets the value of the memberRuleList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RbsMemberRuleListType }
     *     
     */
    public void setMemberRuleList(RbsMemberRuleListType value) {
        this.memberRuleList = value;
    }

    /**
     * Gets the value of the assignmentList property.
     * 
     * @return
     *     possible object is
     *     {@link RbsAssignmentListType }
     *     
     */
    public RbsAssignmentListType getAssignmentList() {
        return assignmentList;
    }

    /**
     * Sets the value of the assignmentList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RbsAssignmentListType }
     *     
     */
    public void setAssignmentList(RbsAssignmentListType value) {
        this.assignmentList = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

}
