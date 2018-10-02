
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rbsPolicyGroupType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rbsPolicyGroupType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="joinType" type="{http://www.compositesw.com/services/system/util/security}rbsGroupJoinType"/>
 *         &lt;element name="policyList" type="{http://www.compositesw.com/services/system/util/security}rbsPolicyListType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rbsPolicyGroupType", propOrder = {
    "joinType",
    "policyList"
})
public class RbsPolicyGroupType {

    @XmlElement(required = true)
    protected RbsGroupJoinType joinType;
    @XmlElement(required = true)
    protected RbsPolicyListType policyList;

    /**
     * Gets the value of the joinType property.
     * 
     * @return
     *     possible object is
     *     {@link RbsGroupJoinType }
     *     
     */
    public RbsGroupJoinType getJoinType() {
        return joinType;
    }

    /**
     * Sets the value of the joinType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RbsGroupJoinType }
     *     
     */
    public void setJoinType(RbsGroupJoinType value) {
        this.joinType = value;
    }

    /**
     * Gets the value of the policyList property.
     * 
     * @return
     *     possible object is
     *     {@link RbsPolicyListType }
     *     
     */
    public RbsPolicyListType getPolicyList() {
        return policyList;
    }

    /**
     * Sets the value of the policyList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RbsPolicyListType }
     *     
     */
    public void setPolicyList(RbsPolicyListType value) {
        this.policyList = value;
    }

}
