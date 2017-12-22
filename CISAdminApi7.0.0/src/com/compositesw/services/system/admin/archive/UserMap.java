
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userMap complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="userMap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="from" type="{http://www.compositesw.com/services/system/admin/archive}domainUserPair"/>
 *         &lt;element name="to" type="{http://www.compositesw.com/services/system/admin/archive}domainUserPair"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userMap", propOrder = {
    "from",
    "to"
})
public class UserMap {

    @XmlElement(required = true)
    protected DomainUserPair from;
    @XmlElement(required = true)
    protected DomainUserPair to;

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link DomainUserPair }
     *     
     */
    public DomainUserPair getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link DomainUserPair }
     *     
     */
    public void setFrom(DomainUserPair value) {
        this.from = value;
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link DomainUserPair }
     *     
     */
    public DomainUserPair getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link DomainUserPair }
     *     
     */
    public void setTo(DomainUserPair value) {
        this.to = value;
    }

}
