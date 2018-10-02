
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for cbsGetPoliciesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cbsGetPoliciesResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="policyList" type="{http://www.compositesw.com/services/system/util/security}cbsPolicyListReturnType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cbsGetPoliciesResponse", propOrder = {
    "policyList"
})
public class CbsGetPoliciesResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected CbsPolicyListReturnType policyList;

    /**
     * Gets the value of the policyList property.
     * 
     * @return
     *     possible object is
     *     {@link CbsPolicyListReturnType }
     *     
     */
    public CbsPolicyListReturnType getPolicyList() {
        return policyList;
    }

    /**
     * Sets the value of the policyList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CbsPolicyListReturnType }
     *     
     */
    public void setPolicyList(CbsPolicyListReturnType value) {
        this.policyList = value;
    }

}
