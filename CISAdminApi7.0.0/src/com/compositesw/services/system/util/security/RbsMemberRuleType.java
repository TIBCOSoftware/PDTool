
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rbsMemberRuleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rbsMemberRuleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="member" type="{http://www.compositesw.com/services/system/util/security}rbsMatchingIdType"/>
 *         &lt;element name="filter" type="{http://www.compositesw.com/services/system/util/security}rbsFilterType"/>
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rbsMemberRuleType", propOrder = {
    "member",
    "filter",
    "data"
})
public class RbsMemberRuleType {

    @XmlElement(required = true)
    protected RbsMatchingIdType member;
    @XmlElement(required = true)
    protected RbsFilterType filter;
    protected String data;

    /**
     * Gets the value of the member property.
     * 
     * @return
     *     possible object is
     *     {@link RbsMatchingIdType }
     *     
     */
    public RbsMatchingIdType getMember() {
        return member;
    }

    /**
     * Sets the value of the member property.
     * 
     * @param value
     *     allowed object is
     *     {@link RbsMatchingIdType }
     *     
     */
    public void setMember(RbsMatchingIdType value) {
        this.member = value;
    }

    /**
     * Gets the value of the filter property.
     * 
     * @return
     *     possible object is
     *     {@link RbsFilterType }
     *     
     */
    public RbsFilterType getFilter() {
        return filter;
    }

    /**
     * Sets the value of the filter property.
     * 
     * @param value
     *     allowed object is
     *     {@link RbsFilterType }
     *     
     */
    public void setFilter(RbsFilterType value) {
        this.filter = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setData(String value) {
        this.data = value;
    }

}
