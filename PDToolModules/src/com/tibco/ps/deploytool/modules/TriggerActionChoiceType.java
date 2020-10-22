//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.13 at 12:40:03 PM EDT 
//


package com.tibco.ps.deploytool.modules;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 					resource:actionType=PROCEDURE, STATISTICS, REINTROSPECT, EMAIL
 * 				
 * 
 * &lt;p&gt;Java class for TriggerActionChoiceType complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TriggerActionChoiceType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;choice&amp;gt;
 *         &amp;lt;element name="executeProcedure" type="{http://www.tibco.com/ps/deploytool/modules}TriggerActionExecuteProcedureType"/&amp;gt;
 *         &amp;lt;element name="gatherStatistics" type="{http://www.tibco.com/ps/deploytool/modules}TriggerActionGatherStatisticsType"/&amp;gt;
 *         &amp;lt;element name="reintrospectDatasource" type="{http://www.tibco.com/ps/deploytool/modules}TriggerActionReintrospectDatasourceType"/&amp;gt;
 *         &amp;lt;element name="sendEmail" type="{http://www.tibco.com/ps/deploytool/modules}TriggerActionSendEmailType"/&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TriggerActionChoiceType", propOrder = {
    "executeProcedure",
    "gatherStatistics",
    "reintrospectDatasource",
    "sendEmail"
})
public class TriggerActionChoiceType {

    protected TriggerActionExecuteProcedureType executeProcedure;
    protected TriggerActionGatherStatisticsType gatherStatistics;
    protected TriggerActionReintrospectDatasourceType reintrospectDatasource;
    protected TriggerActionSendEmailType sendEmail;

    /**
     * Gets the value of the executeProcedure property.
     * 
     * @return
     *     possible object is
     *     {@link TriggerActionExecuteProcedureType }
     *     
     */
    public TriggerActionExecuteProcedureType getExecuteProcedure() {
        return executeProcedure;
    }

    /**
     * Sets the value of the executeProcedure property.
     * 
     * @param value
     *     allowed object is
     *     {@link TriggerActionExecuteProcedureType }
     *     
     */
    public void setExecuteProcedure(TriggerActionExecuteProcedureType value) {
        this.executeProcedure = value;
    }

    /**
     * Gets the value of the gatherStatistics property.
     * 
     * @return
     *     possible object is
     *     {@link TriggerActionGatherStatisticsType }
     *     
     */
    public TriggerActionGatherStatisticsType getGatherStatistics() {
        return gatherStatistics;
    }

    /**
     * Sets the value of the gatherStatistics property.
     * 
     * @param value
     *     allowed object is
     *     {@link TriggerActionGatherStatisticsType }
     *     
     */
    public void setGatherStatistics(TriggerActionGatherStatisticsType value) {
        this.gatherStatistics = value;
    }

    /**
     * Gets the value of the reintrospectDatasource property.
     * 
     * @return
     *     possible object is
     *     {@link TriggerActionReintrospectDatasourceType }
     *     
     */
    public TriggerActionReintrospectDatasourceType getReintrospectDatasource() {
        return reintrospectDatasource;
    }

    /**
     * Sets the value of the reintrospectDatasource property.
     * 
     * @param value
     *     allowed object is
     *     {@link TriggerActionReintrospectDatasourceType }
     *     
     */
    public void setReintrospectDatasource(TriggerActionReintrospectDatasourceType value) {
        this.reintrospectDatasource = value;
    }

    /**
     * Gets the value of the sendEmail property.
     * 
     * @return
     *     possible object is
     *     {@link TriggerActionSendEmailType }
     *     
     */
    public TriggerActionSendEmailType getSendEmail() {
        return sendEmail;
    }

    /**
     * Sets the value of the sendEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link TriggerActionSendEmailType }
     *     
     */
    public void setSendEmail(TriggerActionSendEmailType value) {
        this.sendEmail = value;
    }

}
