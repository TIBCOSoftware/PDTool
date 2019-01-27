
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for cbsGetPoliciesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cbsGetPoliciesRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="policyPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cbsGetPoliciesRequest", propOrder = {
    "policyPath"
})
public class CbsGetPoliciesRequest
    extends BaseRequest
{

    protected String policyPath;

    /**
     * Gets the value of the policyPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyPath() {
        return policyPath;
    }

    /**
     * Sets the value of the policyPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyPath(String value) {
        this.policyPath = value;
    }

}
