
package com.compositesw.services.system.util.security;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rbsFilterPolicySummaryListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rbsFilterPolicySummaryListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="filterPolicy" type="{http://www.compositesw.com/services/system/util/security}rbsFilterPolicySummaryType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rbsFilterPolicySummaryListType", propOrder = {
    "filterPolicy"
})
public class RbsFilterPolicySummaryListType {

    protected List<RbsFilterPolicySummaryType> filterPolicy;

    /**
     * Gets the value of the filterPolicy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the filterPolicy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFilterPolicy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RbsFilterPolicySummaryType }
     * 
     * 
     */
    public List<RbsFilterPolicySummaryType> getFilterPolicy() {
        if (filterPolicy == null) {
            filterPolicy = new ArrayList<RbsFilterPolicySummaryType>();
        }
        return this.filterPolicy;
    }

}
