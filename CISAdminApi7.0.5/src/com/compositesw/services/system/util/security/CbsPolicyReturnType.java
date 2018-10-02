
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cbsPolicyReturnType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cbsPolicyReturnType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="policyPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="maskingRuleList" type="{http://www.compositesw.com/services/system/util/security}cbsMaskingRuleListType"/>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="annotation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="assignmentList" type="{http://www.compositesw.com/services/system/util/security}cbsAssignmentWithResourcePathTypeListType"/>
 *         &lt;element name="impactMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="impactLevel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parameters" type="{http://www.compositesw.com/services/system/util/security}cbsParameterListType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cbsPolicyReturnType", propOrder = {
    "policyPath",
    "dateType",
    "maskingRuleList",
    "enabled",
    "annotation",
    "assignmentList",
    "impactMessage",
    "impactLevel",
    "parameters"
})
public class CbsPolicyReturnType {

    @XmlElement(required = true)
    protected String policyPath;
    @XmlElement(required = true)
    protected String dateType;
    @XmlElement(required = true)
    protected CbsMaskingRuleListType maskingRuleList;
    protected boolean enabled;
    @XmlElement(required = true)
    protected String annotation;
    @XmlElement(required = true)
    protected CbsAssignmentWithResourcePathTypeListType assignmentList;
    @XmlElement(required = true)
    protected String impactMessage;
    @XmlElement(required = true)
    protected String impactLevel;
    @XmlElement(required = true)
    protected CbsParameterListType parameters;

    /**
     * Gets the value of the policyPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyPath() {
        return policyPath;
    }

    /**
     * Sets the value of the policyPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyPath(String value) {
        this.policyPath = value;
    }

    /**
     * Gets the value of the dateType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateType() {
        return dateType;
    }

    /**
     * Sets the value of the dateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateType(String value) {
        this.dateType = value;
    }

    /**
     * Gets the value of the maskingRuleList property.
     * 
     * @return
     *     possible object is
     *     {@link CbsMaskingRuleListType }
     *     
     */
    public CbsMaskingRuleListType getMaskingRuleList() {
        return maskingRuleList;
    }

    /**
     * Sets the value of the maskingRuleList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CbsMaskingRuleListType }
     *     
     */
    public void setMaskingRuleList(CbsMaskingRuleListType value) {
        this.maskingRuleList = value;
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

    /**
     * Gets the value of the assignmentList property.
     * 
     * @return
     *     possible object is
     *     {@link CbsAssignmentWithResourcePathTypeListType }
     *     
     */
    public CbsAssignmentWithResourcePathTypeListType getAssignmentList() {
        return assignmentList;
    }

    /**
     * Sets the value of the assignmentList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CbsAssignmentWithResourcePathTypeListType }
     *     
     */
    public void setAssignmentList(CbsAssignmentWithResourcePathTypeListType value) {
        this.assignmentList = value;
    }

    /**
     * Gets the value of the impactMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImpactMessage() {
        return impactMessage;
    }

    /**
     * Sets the value of the impactMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImpactMessage(String value) {
        this.impactMessage = value;
    }

    /**
     * Gets the value of the impactLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImpactLevel() {
        return impactLevel;
    }

    /**
     * Sets the value of the impactLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImpactLevel(String value) {
        this.impactLevel = value;
    }

    /**
     * Gets the value of the parameters property.
     * 
     * @return
     *     possible object is
     *     {@link CbsParameterListType }
     *     
     */
    public CbsParameterListType getParameters() {
        return parameters;
    }

    /**
     * Sets the value of the parameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link CbsParameterListType }
     *     
     */
    public void setParameters(CbsParameterListType value) {
        this.parameters = value;
    }

}
