//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-257 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.18 at 06:41:50 AM EST 
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
 * <p>Java class for TriggerActionChoiceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TriggerActionChoiceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="executeProcedure" type="{http://www.tibco.com/ps/deploytool/modules}TriggerActionExecuteProcedureType"/>
 *         &lt;element name="gatherStatistics" type="{http://www.tibco.com/ps/deploytool/modules}TriggerActionGatherStatisticsType"/>
 *         &lt;element name="reintrospectDatasource" type="{http://www.tibco.com/ps/deploytool/modules}TriggerActionReintrospectDatasourceType"/>
 *         &lt;element name="sendEmail" type="{http://www.tibco.com/ps/deploytool/modules}TriggerActionSendEmailType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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