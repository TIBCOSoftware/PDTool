
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for rbsGetFilterPolicyResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rbsGetFilterPolicyResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="policy" type="{http://www.compositesw.com/services/system/util/security}rbsPolicyType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rbsGetFilterPolicyResponse", propOrder = {
    "policy"
})
public class RbsGetFilterPolicyResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected RbsPolicyType policy;

    /**
     * Gets the value of the policy property.
     * 
     * @return
     *     possible object is
     *     {@link RbsPolicyType }
     *     
     */
    public RbsPolicyType getPolicy() {
        return policy;
    }

    /**
     * Sets the value of the policy property.
     * 
     * @param value
     *     allowed object is
     *     {@link RbsPolicyType }
     *     
     */
    public void setPolicy(RbsPolicyType value) {
        this.policy = value;
    }

}
