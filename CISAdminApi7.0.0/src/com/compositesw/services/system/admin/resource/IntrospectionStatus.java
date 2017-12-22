
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.compositesw.services.system.util.common.OperationStatus;


/**
 * <p>Java class for introspectionStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="introspectionStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="status" type="{http://www.compositesw.com/services/system/util/common}operationStatus"/>
 *         &lt;element name="introspectorVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="addedCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="removedCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="updatedCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="skippedCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="totalCompletedCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="toBeAddedCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="toBeRemovedCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="toBeUpdatedCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="totalToBeCompletedCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="warningCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="errorCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="report" type="{http://www.compositesw.com/services/system/admin/resource}introspectionChangeEntryList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "introspectionStatus", propOrder = {
    "status",
    "introspectorVersion",
    "startTime",
    "endTime",
    "addedCount",
    "removedCount",
    "updatedCount",
    "skippedCount",
    "totalCompletedCount",
    "toBeAddedCount",
    "toBeRemovedCount",
    "toBeUpdatedCount",
    "totalToBeCompletedCount",
    "warningCount",
    "errorCount",
    "report"
})
public class IntrospectionStatus {

    @XmlElement(required = true)
    protected OperationStatus status;
    protected int introspectorVersion;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endTime;
    protected int addedCount;
    protected int removedCount;
    protected int updatedCount;
    protected int skippedCount;
    protected int totalCompletedCount;
    protected int toBeAddedCount;
    protected int toBeRemovedCount;
    protected int toBeUpdatedCount;
    protected int totalToBeCompletedCount;
    protected int warningCount;
    protected int errorCount;
    protected IntrospectionChangeEntryList report;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link OperationStatus }
     *     
     */
    public OperationStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationStatus }
     *     
     */
    public void setStatus(OperationStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the introspectorVersion property.
     * 
     */
    public int getIntrospectorVersion() {
        return introspectorVersion;
    }

    /**
     * Sets the value of the introspectorVersion property.
     * 
     */
    public void setIntrospectorVersion(int value) {
        this.introspectorVersion = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndTime(XMLGregorianCalendar value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the addedCount property.
     * 
     */
    public int getAddedCount() {
        return addedCount;
    }

    /**
     * Sets the value of the addedCount property.
     * 
     */
    public void setAddedCount(int value) {
        this.addedCount = value;
    }

    /**
     * Gets the value of the removedCount property.
     * 
     */
    public int getRemovedCount() {
        return removedCount;
    }

    /**
     * Sets the value of the removedCount property.
     * 
     */
    public void setRemovedCount(int value) {
        this.removedCount = value;
    }

    /**
     * Gets the value of the updatedCount property.
     * 
     */
    public int getUpdatedCount() {
        return updatedCount;
    }

    /**
     * Sets the value of the updatedCount property.
     * 
     */
    public void setUpdatedCount(int value) {
        this.updatedCount = value;
    }

    /**
     * Gets the value of the skippedCount property.
     * 
     */
    public int getSkippedCount() {
        return skippedCount;
    }

    /**
     * Sets the value of the skippedCount property.
     * 
     */
    public void setSkippedCount(int value) {
        this.skippedCount = value;
    }

    /**
     * Gets the value of the totalCompletedCount property.
     * 
     */
    public int getTotalCompletedCount() {
        return totalCompletedCount;
    }

    /**
     * Sets the value of the totalCompletedCount property.
     * 
     */
    public void setTotalCompletedCount(int value) {
        this.totalCompletedCount = value;
    }

    /**
     * Gets the value of the toBeAddedCount property.
     * 
     */
    public int getToBeAddedCount() {
        return toBeAddedCount;
    }

    /**
     * Sets the value of the toBeAddedCount property.
     * 
     */
    public void setToBeAddedCount(int value) {
        this.toBeAddedCount = value;
    }

    /**
     * Gets the value of the toBeRemovedCount property.
     * 
     */
    public int getToBeRemovedCount() {
        return toBeRemovedCount;
    }

    /**
     * Sets the value of the toBeRemovedCount property.
     * 
     */
    public void setToBeRemovedCount(int value) {
        this.toBeRemovedCount = value;
    }

    /**
     * Gets the value of the toBeUpdatedCount property.
     * 
     */
    public int getToBeUpdatedCount() {
        return toBeUpdatedCount;
    }

    /**
     * Sets the value of the toBeUpdatedCount property.
     * 
     */
    public void setToBeUpdatedCount(int value) {
        this.toBeUpdatedCount = value;
    }

    /**
     * Gets the value of the totalToBeCompletedCount property.
     * 
     */
    public int getTotalToBeCompletedCount() {
        return totalToBeCompletedCount;
    }

    /**
     * Sets the value of the totalToBeCompletedCount property.
     * 
     */
    public void setTotalToBeCompletedCount(int value) {
        this.totalToBeCompletedCount = value;
    }

    /**
     * Gets the value of the warningCount property.
     * 
     */
    public int getWarningCount() {
        return warningCount;
    }

    /**
     * Sets the value of the warningCount property.
     * 
     */
    public void setWarningCount(int value) {
        this.warningCount = value;
    }

    /**
     * Gets the value of the errorCount property.
     * 
     */
    public int getErrorCount() {
        return errorCount;
    }

    /**
     * Sets the value of the errorCount property.
     * 
     */
    public void setErrorCount(int value) {
        this.errorCount = value;
    }

    /**
     * Gets the value of the report property.
     * 
     * @return
     *     possible object is
     *     {@link IntrospectionChangeEntryList }
     *     
     */
    public IntrospectionChangeEntryList getReport() {
        return report;
    }

    /**
     * Sets the value of the report property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntrospectionChangeEntryList }
     *     
     */
    public void setReport(IntrospectionChangeEntryList value) {
        this.report = value;
    }

}
