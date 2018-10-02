
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rebindResourcesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rebindResourcesRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}multiPathTypeRequest">
 *       &lt;sequence>
 *         &lt;element name="rebindRules" type="{http://www.compositesw.com/services/system/admin/resource}rebindRuleList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rebindResourcesRequest", propOrder = {
    "rebindRules"
})
public class RebindResourcesRequest
    extends MultiPathTypeRequest
{

    @XmlElement(required = true)
    protected RebindRuleList rebindRules;

    /**
     * Gets the value of the rebindRules property.
     * 
     * @return
     *     possible object is
     *     {@link RebindRuleList }
     *     
     */
    public RebindRuleList getRebindRules() {
        return rebindRules;
    }

    /**
     * Sets the value of the rebindRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link RebindRuleList }
     *     
     */
    public void setRebindRules(RebindRuleList value) {
        this.rebindRules = value;
    }

}
