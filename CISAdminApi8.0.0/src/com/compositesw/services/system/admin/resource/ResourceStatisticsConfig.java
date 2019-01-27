
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resourceStatisticsConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resourceStatisticsConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cardinalityMin" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cardinalityMax" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cardinalityExpected" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="gatherEnabled" type="{http://www.compositesw.com/services/system/admin/resource}statsGatherType" minOccurs="0"/>
 *         &lt;element name="maxTime" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="columns" type="{http://www.compositesw.com/services/system/admin/resource}statsColumnList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resourceStatisticsConfig", propOrder = {
    "cardinalityMin",
    "cardinalityMax",
    "cardinalityExpected",
    "gatherEnabled",
    "maxTime",
    "columns"
})
public class ResourceStatisticsConfig {

    protected Integer cardinalityMin;
    protected Integer cardinalityMax;
    protected Integer cardinalityExpected;
    protected StatsGatherType gatherEnabled;
    protected Integer maxTime;
    protected StatsColumnList columns;

    /**
     * Gets the value of the cardinalityMin property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCardinalityMin() {
        return cardinalityMin;
    }

    /**
     * Sets the value of the cardinalityMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCardinalityMin(Integer value) {
        this.cardinalityMin = value;
    }

    /**
     * Gets the value of the cardinalityMax property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCardinalityMax() {
        return cardinalityMax;
    }

    /**
     * Sets the value of the cardinalityMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCardinalityMax(Integer value) {
        this.cardinalityMax = value;
    }

    /**
     * Gets the value of the cardinalityExpected property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCardinalityExpected() {
        return cardinalityExpected;
    }

    /**
     * Sets the value of the cardinalityExpected property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCardinalityExpected(Integer value) {
        this.cardinalityExpected = value;
    }

    /**
     * Gets the value of the gatherEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link StatsGatherType }
     *     
     */
    public StatsGatherType getGatherEnabled() {
        return gatherEnabled;
    }

    /**
     * Sets the value of the gatherEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatsGatherType }
     *     
     */
    public void setGatherEnabled(StatsGatherType value) {
        this.gatherEnabled = value;
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
     * Gets the value of the columns property.
     * 
     * @return
     *     possible object is
     *     {@link StatsColumnList }
     *     
     */
    public StatsColumnList getColumns() {
        return columns;
    }

    /**
     * Sets the value of the columns property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatsColumnList }
     *     
     */
    public void setColumns(StatsColumnList value) {
        this.columns = value;
    }

}
