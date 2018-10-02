
package com.compositesw.services.system.util.security;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cbsMaskingRuleListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cbsMaskingRuleListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="maskingRule" type="{http://www.compositesw.com/services/system/util/security}cbsMaskingRuleType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cbsMaskingRuleListType", propOrder = {
    "maskingRule"
})
public class CbsMaskingRuleListType {

    protected List<CbsMaskingRuleType> maskingRule;

    /**
     * Gets the value of the maskingRule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the maskingRule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMaskingRule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CbsMaskingRuleType }
     * 
     * 
     */
    public List<CbsMaskingRuleType> getMaskingRule() {
        if (maskingRule == null) {
            maskingRule = new ArrayList<CbsMaskingRuleType>();
        }
        return this.maskingRule;
    }

}
