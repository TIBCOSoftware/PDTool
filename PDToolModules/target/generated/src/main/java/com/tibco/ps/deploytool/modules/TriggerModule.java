//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.16 at 10:25:54 AM EDT 
//


package com.tibco.ps.deploytool.modules;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="triggerList" type="{http://www.tibco.com/ps/deploytool/modules}TriggerListType"/&gt;
 *         &lt;element name="scheduleList" type="{http://www.tibco.com/ps/deploytool/modules}TriggerScheduleListType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "triggerList",
    "scheduleList"
})
@XmlRootElement(name = "TriggerModule")
public class TriggerModule {

    @XmlElement(required = true)
    protected TriggerListType triggerList;
    @XmlElement(required = true)
    protected TriggerScheduleListType scheduleList;

    /**
     * Gets the value of the triggerList property.
     * 
     * @return
     *     possible object is
     *     {@link TriggerListType }
     *     
     */
    public TriggerListType getTriggerList() {
        return triggerList;
    }

    /**
     * Sets the value of the triggerList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TriggerListType }
     *     
     */
    public void setTriggerList(TriggerListType value) {
        this.triggerList = value;
    }

    /**
     * Gets the value of the scheduleList property.
     * 
     * @return
     *     possible object is
     *     {@link TriggerScheduleListType }
     *     
     */
    public TriggerScheduleListType getScheduleList() {
        return scheduleList;
    }

    /**
     * Sets the value of the scheduleList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TriggerScheduleListType }
     *     
     */
    public void setScheduleList(TriggerScheduleListType value) {
        this.scheduleList = value;
    }

}
