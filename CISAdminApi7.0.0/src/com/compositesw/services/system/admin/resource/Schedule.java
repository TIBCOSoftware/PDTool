
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for schedule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="schedule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mode" type="{http://www.compositesw.com/services/system/admin/resource}scheduleMode" minOccurs="0"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fromTimeInADay" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="endTimeInADay" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="recurringDay" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="interval" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="period" type="{http://www.compositesw.com/services/system/admin/resource}calendarPeriod" minOccurs="0"/>
 *         &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isCluster" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "schedule", propOrder = {
    "mode",
    "startTime",
    "fromTimeInADay",
    "endTimeInADay",
    "recurringDay",
    "interval",
    "period",
    "count",
    "isCluster"
})
public class Schedule {

    protected ScheduleMode mode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;
    protected Long fromTimeInADay;
    protected Long endTimeInADay;
    protected Integer recurringDay;
    protected Integer interval;
    protected CalendarPeriod period;
    protected Integer count;
    protected Boolean isCluster;

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link ScheduleMode }
     *     
     */
    public ScheduleMode getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduleMode }
     *     
     */
    public void setMode(ScheduleMode value) {
        this.mode = value;
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
     * Gets the value of the fromTimeInADay property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFromTimeInADay() {
        return fromTimeInADay;
    }

    /**
     * Sets the value of the fromTimeInADay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFromTimeInADay(Long value) {
        this.fromTimeInADay = value;
    }

    /**
     * Gets the value of the endTimeInADay property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEndTimeInADay() {
        return endTimeInADay;
    }

    /**
     * Sets the value of the endTimeInADay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEndTimeInADay(Long value) {
        this.endTimeInADay = value;
    }

    /**
     * Gets the value of the recurringDay property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRecurringDay() {
        return recurringDay;
    }

    /**
     * Sets the value of the recurringDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRecurringDay(Integer value) {
        this.recurringDay = value;
    }

    /**
     * Gets the value of the interval property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInterval() {
        return interval;
    }

    /**
     * Sets the value of the interval property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInterval(Integer value) {
        this.interval = value;
    }

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link CalendarPeriod }
     *     
     */
    public CalendarPeriod getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link CalendarPeriod }
     *     
     */
    public void setPeriod(CalendarPeriod value) {
        this.period = value;
    }

    /**
     * Gets the value of the count property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCount(Integer value) {
        this.count = value;
    }

    /**
     * Gets the value of the isCluster property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsCluster() {
        return isCluster;
    }

    /**
     * Sets the value of the isCluster property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsCluster(Boolean value) {
        this.isCluster = value;
    }

}
