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
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Regression Query Type: A regression query consists a list of queries that are used to populate the regression input file.  This list is a single list for all datasources.
 * 			
 * 
 * <p>Java class for RegressionQueryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RegressionQueryType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="datasource" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="query" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="durationDelta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="wsPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="wsAction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="wsEncrypt" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="true"/&gt;
 *               &lt;enumeration value="false"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="wsContentType" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="text/xml;charset=UTF-8"/&gt;
 *               &lt;enumeration value="application/soap+xml;charset=UTF-8"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegressionQueryType", propOrder = {
    "datasource",
    "query",
    "durationDelta",
    "wsPath",
    "wsAction",
    "wsEncrypt",
    "wsContentType"
})
public class RegressionQueryType {

    @XmlElement(required = true)
    protected String datasource;
    @XmlElement(required = true)
    protected String query;
    protected String durationDelta;
    protected String wsPath;
    protected String wsAction;
    protected String wsEncrypt;
    protected String wsContentType;

    /**
     * Gets the value of the datasource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatasource() {
        return datasource;
    }

    /**
     * Sets the value of the datasource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatasource(String value) {
        this.datasource = value;
    }

    /**
     * Gets the value of the query property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the value of the query property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuery(String value) {
        this.query = value;
    }

    /**
     * Gets the value of the durationDelta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDurationDelta() {
        return durationDelta;
    }

    /**
     * Sets the value of the durationDelta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDurationDelta(String value) {
        this.durationDelta = value;
    }

    /**
     * Gets the value of the wsPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWsPath() {
        return wsPath;
    }

    /**
     * Sets the value of the wsPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWsPath(String value) {
        this.wsPath = value;
    }

    /**
     * Gets the value of the wsAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWsAction() {
        return wsAction;
    }

    /**
     * Sets the value of the wsAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWsAction(String value) {
        this.wsAction = value;
    }

    /**
     * Gets the value of the wsEncrypt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWsEncrypt() {
        return wsEncrypt;
    }

    /**
     * Sets the value of the wsEncrypt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWsEncrypt(String value) {
        this.wsEncrypt = value;
    }

    /**
     * Gets the value of the wsContentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWsContentType() {
        return wsContentType;
    }

    /**
     * Sets the value of the wsContentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWsContentType(String value) {
        this.wsContentType = value;
    }

}
