
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for cbsCreatePoliciesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cbsCreatePoliciesRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="policyList" type="{http://www.compositesw.com/services/system/util/security}cbsPolicyListTypeRequest"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cbsCreatePoliciesRequest", propOrder = {
    "policyList"
})
public class CbsCreatePoliciesRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected CbsPolicyListTypeRequest policyList;

    /**
     * Gets the value of the policyList property.
     * 
     * @return
     *     possible object is
     *     {@link CbsPolicyListTypeRequest }
     *     
     */
    public CbsPolicyListTypeRequest getPolicyList() {
        return policyList;
    }

    /**
     * Sets the value of the policyList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CbsPolicyListTypeRequest }
     *     
     */
    public void setPolicyList(CbsPolicyListTypeRequest value) {
        this.policyList = value;
    }

}
