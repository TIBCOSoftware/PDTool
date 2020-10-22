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
 * 				Parameter settings for comparing regression execution result files.
 * 			
 * 
 * <p>Java class for RegressionCompareFilesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RegressionCompareFilesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="logFilePath" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="logDelimiter" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="COMMA"/&gt;
 *               &lt;enumeration value=","/&gt;
 *               &lt;enumeration value="PIPE"/&gt;
 *               &lt;enumeration value="|"/&gt;
 *               &lt;enumeration value="TAB"/&gt;
 *               &lt;enumeration value="TILDE"/&gt;
 *               &lt;enumeration value="~"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="logAppend"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="yes"/&gt;
 *               &lt;enumeration value="no"/&gt;
 *               &lt;enumeration value="true"/&gt;
 *               &lt;enumeration value="false"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="baseDir1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="baseDir2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="compareQueries"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="yes"/&gt;
 *               &lt;enumeration value="no"/&gt;
 *               &lt;enumeration value="true"/&gt;
 *               &lt;enumeration value="false"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="compareProcedures"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="yes"/&gt;
 *               &lt;enumeration value="no"/&gt;
 *               &lt;enumeration value="true"/&gt;
 *               &lt;enumeration value="false"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="compareWS"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="yes"/&gt;
 *               &lt;enumeration value="no"/&gt;
 *               &lt;enumeration value="true"/&gt;
 *               &lt;enumeration value="false"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="useAllDatasources"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="yes"/&gt;
 *               &lt;enumeration value="no"/&gt;
 *               &lt;enumeration value="true"/&gt;
 *               &lt;enumeration value="false"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="datasources" type="{http://www.tibco.com/ps/deploytool/modules}RegressionDatasourcesType" minOccurs="0"/&gt;
 *         &lt;element name="resources" type="{http://www.tibco.com/ps/deploytool/modules}RegressionResourcesType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegressionCompareFilesType", propOrder = {
    "logFilePath",
    "logDelimiter",
    "logAppend",
    "baseDir1",
    "baseDir2",
    "compareQueries",
    "compareProcedures",
    "compareWS",
    "useAllDatasources",
    "datasources",
    "resources"
})
public class RegressionCompareFilesType {

    @XmlElement(required = true)
    protected String logFilePath;
    protected String logDelimiter;
    @XmlElement(required = true)
    protected String logAppend;
    @XmlElement(required = true)
    protected String baseDir1;
    @XmlElement(required = true)
    protected String baseDir2;
    @XmlElement(required = true)
    protected String compareQueries;
    @XmlElement(required = true)
    protected String compareProcedures;
    @XmlElement(required = true)
    protected String compareWS;
    @XmlElement(required = true)
    protected String useAllDatasources;
    protected RegressionDatasourcesType datasources;
    protected RegressionResourcesType resources;

    /**
     * Gets the value of the logFilePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogFilePath() {
        return logFilePath;
    }

    /**
     * Sets the value of the logFilePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogFilePath(String value) {
        this.logFilePath = value;
    }

    /**
     * Gets the value of the logDelimiter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogDelimiter() {
        return logDelimiter;
    }

    /**
     * Sets the value of the logDelimiter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogDelimiter(String value) {
        this.logDelimiter = value;
    }

    /**
     * Gets the value of the logAppend property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogAppend() {
        return logAppend;
    }

    /**
     * Sets the value of the logAppend property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogAppend(String value) {
        this.logAppend = value;
    }

    /**
     * Gets the value of the baseDir1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseDir1() {
        return baseDir1;
    }

    /**
     * Sets the value of the baseDir1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseDir1(String value) {
        this.baseDir1 = value;
    }

    /**
     * Gets the value of the baseDir2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseDir2() {
        return baseDir2;
    }

    /**
     * Sets the value of the baseDir2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseDir2(String value) {
        this.baseDir2 = value;
    }

    /**
     * Gets the value of the compareQueries property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompareQueries() {
        return compareQueries;
    }

    /**
     * Sets the value of the compareQueries property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompareQueries(String value) {
        this.compareQueries = value;
    }

    /**
     * Gets the value of the compareProcedures property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompareProcedures() {
        return compareProcedures;
    }

    /**
     * Sets the value of the compareProcedures property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompareProcedures(String value) {
        this.compareProcedures = value;
    }

    /**
     * Gets the value of the compareWS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompareWS() {
        return compareWS;
    }

    /**
     * Sets the value of the compareWS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompareWS(String value) {
        this.compareWS = value;
    }

    /**
     * Gets the value of the useAllDatasources property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseAllDatasources() {
        return useAllDatasources;
    }

    /**
     * Sets the value of the useAllDatasources property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseAllDatasources(String value) {
        this.useAllDatasources = value;
    }

    /**
     * Gets the value of the datasources property.
     * 
     * @return
     *     possible object is
     *     {@link RegressionDatasourcesType }
     *     
     */
    public RegressionDatasourcesType getDatasources() {
        return datasources;
    }

    /**
     * Sets the value of the datasources property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegressionDatasourcesType }
     *     
     */
    public void setDatasources(RegressionDatasourcesType value) {
        this.datasources = value;
    }

    /**
     * Gets the value of the resources property.
     * 
     * @return
     *     possible object is
     *     {@link RegressionResourcesType }
     *     
     */
    public RegressionResourcesType getResources() {
        return resources;
    }

    /**
     * Sets the value of the resources property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegressionResourcesType }
     *     
     */
    public void setResources(RegressionResourcesType value) {
        this.resources = value;
    }

}
