
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for rbsGetFilterPolicyListResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rbsGetFilterPolicyListResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="filterPolicyList" type="{http://www.compositesw.com/services/system/util/security}rbsFilterPolicySummaryListType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rbsGetFilterPolicyListResponse", propOrder = {
    "filterPolicyList"
})
public class RbsGetFilterPolicyListResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected RbsFilterPolicySummaryListType filterPolicyList;

    /**
     * Gets the value of the filterPolicyList property.
     * 
     * @return
     *     possible object is
     *     {@link RbsFilterPolicySummaryListType }
     *     
     */
    public RbsFilterPolicySummaryListType getFilterPolicyList() {
        return filterPolicyList;
    }

    /**
     * Sets the value of the filterPolicyList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RbsFilterPolicySummaryListType }
     *     
     */
    public void setFilterPolicyList(RbsFilterPolicySummaryListType value) {
        this.filterPolicyList = value;
    }

}
