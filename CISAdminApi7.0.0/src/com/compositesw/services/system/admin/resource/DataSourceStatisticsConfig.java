
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataSourceStatisticsConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataSourceStatisticsConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="configured" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="useEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="tableGatherDefault" type="{http://www.compositesw.com/services/system/admin/resource}statsDataSourceDefault" minOccurs="0"/>
 *         &lt;element name="numThreads" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="maxTime" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="refresh" type="{http://www.compositesw.com/services/system/admin/resource}refresh" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataSourceStatisticsConfig", propOrder = {
    "configured",
    "useEnabled",
    "tableGatherDefault",
    "numThreads",
    "maxTime",
    "refresh"
})
public class DataSourceStatisticsConfig {

    protected Boolean configured;
    protected Boolean useEnabled;
    protected StatsDataSourceDefault tableGatherDefault;
    protected Integer numThreads;
    protected Integer maxTime;
    protected Refresh refresh;

    /**
     * Gets the value of the configured property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isConfigured() {
        return configured;
    }

    /**
     * Sets the value of the configured property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setConfigured(Boolean value) {
        this.configured = value;
    }

    /**
     * Gets the value of the useEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseEnabled() {
        return useEnabled;
    }

    /**
     * Sets the value of the useEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseEnabled(Boolean value) {
        this.useEnabled = value;
    }

    /**
     * Gets the value of the tableGatherDefault property.
     * 
     * @return
     *     possible object is
     *     {@link StatsDataSourceDefault }
     *     
     */
    public StatsDataSourceDefault getTableGatherDefault() {
        return tableGatherDefault;
    }

    /**
     * Sets the value of the tableGatherDefault property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatsDataSourceDefault }
     *     
     */
    public void setTableGatherDefault(StatsDataSourceDefault value) {
        this.tableGatherDefault = value;
    }

    /**
     * Gets the value of the numThreads property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumThreads() {
        return numThreads;
    }

    /**
     * Sets the value of the numThreads property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumThreads(Integer value) {
        this.numThreads = value;
    }

    /**
     * Gets the value of the maxTime property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxTime() {
        return maxTime;
    }

    /**
     * Sets the value of the maxTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxTime(Integer value) {
        this.maxTime = value;
    }

    /**
     * Gets the value of the refresh property.
     * 
     * @return
     *     possible object is
     *     {@link Refresh }
     *     
     */
    public Refresh getRefresh() {
        return refresh;
    }

    /**
     * Sets the value of the refresh property.
     * 
     * @param value
     *     allowed object is
     *     {@link Refresh }
     *     
     */
    public void setRefresh(Refresh value) {
        this.refresh = value;
    }

}
