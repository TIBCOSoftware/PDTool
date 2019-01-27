
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for rbsWriteFilterPolicyRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rbsWriteFilterPolicyRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="policy" type="{http://www.compositesw.com/services/system/util/security}rbsPolicyType"/>
 *         &lt;element name="originalPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rbsWriteFilterPolicyRequest", propOrder = {
    "policy",
    "originalPath"
})
public class RbsWriteFilterPolicyRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected RbsPolicyType policy;
    protected String originalPath;

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

    /**
     * Gets the value of the originalPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalPath() {
        return originalPath;
    }

    /**
     * Sets the value of the originalPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalPath(String value) {
        this.originalPath = value;
    }

}
