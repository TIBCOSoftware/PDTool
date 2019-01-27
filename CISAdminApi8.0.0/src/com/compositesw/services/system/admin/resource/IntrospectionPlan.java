
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for introspectionPlan complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="introspectionPlan">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="updateAllIntrospectedResources" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="failFast" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="commitOnFailure" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="autoRollback" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="scanForNewResourcesToAutoAdd" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="entries" type="{http://www.compositesw.com/services/system/admin/resource}introspectionPlanEntries" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "introspectionPlan", propOrder = {
    "updateAllIntrospectedResources",
    "failFast",
    "commitOnFailure",
    "autoRollback",
    "scanForNewResourcesToAutoAdd",
    "entries"
})
public class IntrospectionPlan {

    @XmlElement(defaultValue = "false")
    protected Boolean updateAllIntrospectedResources;
    @XmlElement(defaultValue = "false")
    protected Boolean failFast;
    @XmlElement(defaultValue = "false")
    protected Boolean commitOnFailure;
    @XmlElement(defaultValue = "false")
    protected Boolean autoRollback;
    @XmlElement(defaultValue = "false")
    protected Boolean scanForNewResourcesToAutoAdd;
    protected IntrospectionPlanEntries entries;

    /**
     * Gets the value of the updateAllIntrospectedResources property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUpdateAllIntrospectedResources() {
        return updateAllIntrospectedResources;
    }

    /**
     * Sets the value of the updateAllIntrospectedResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUpdateAllIntrospectedResources(Boolean value) {
        this.updateAllIntrospectedResources = value;
    }

    /**
     * Gets the value of the failFast property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFailFast() {
        return failFast;
    }

    /**
     * Sets the value of the failFast property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFailFast(Boolean value) {
        this.failFast = value;
    }

    /**
     * Gets the value of the commitOnFailure property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCommitOnFailure() {
        return commitOnFailure;
    }

    /**
     * Sets the value of the commitOnFailure property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCommitOnFailure(Boolean value) {
        this.commitOnFailure = value;
    }

    /**
     * Gets the value of the autoRollback property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAutoRollback() {
        return autoRollback;
    }

    /**
     * Sets the value of the autoRollback property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAutoRollback(Boolean value) {
        this.autoRollback = value;
    }

    /**
     * Gets the value of the scanForNewResourcesToAutoAdd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isScanForNewResourcesToAutoAdd() {
        return scanForNewResourcesToAutoAdd;
    }

    /**
     * Sets the value of the scanForNewResourcesToAutoAdd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setScanForNewResourcesToAutoAdd(Boolean value) {
        this.scanForNewResourcesToAutoAdd = value;
    }

    /**
     * Gets the value of the entries property.
     * 
     * @return
     *     possible object is
     *     {@link IntrospectionPlanEntries }
     *     
     */
    public IntrospectionPlanEntries getEntries() {
        return entries;
    }

    /**
     * Sets the value of the entries property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntrospectionPlanEntries }
     *     
     */
    public void setEntries(IntrospectionPlanEntries value) {
        this.entries = value;
    }

}
