//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.13 at 12:40:03 PM EDT 
//


package com.tibco.ps.deploytool.modules;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Regression Security Plans Type: A list of security plans that correlate users with queries for execution.
 * 			
 * 
 * &lt;p&gt;Java class for RegressionSecurityPlansType complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RegressionSecurityPlansType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="regressionSecurityPlan" type="{http://www.tibco.com/ps/deploytool/modules}RegressionSecurityPlanType" maxOccurs="unbounded"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegressionSecurityPlansType", propOrder = {
    "regressionSecurityPlan"
})
public class RegressionSecurityPlansType {

    @XmlElement(required = true)
    protected List<RegressionSecurityPlanType> regressionSecurityPlan;

    /**
     * Gets the value of the regressionSecurityPlan property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the regressionSecurityPlan property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getRegressionSecurityPlan().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RegressionSecurityPlanType }
     * 
     * 
     */
    public List<RegressionSecurityPlanType> getRegressionSecurityPlan() {
        if (regressionSecurityPlan == null) {
            regressionSecurityPlan = new ArrayList<RegressionSecurityPlanType>();
        }
        return this.regressionSecurityPlan;
    }

}
