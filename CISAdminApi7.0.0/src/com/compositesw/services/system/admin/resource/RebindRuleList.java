
package com.compositesw.services.system.admin.resource;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rebindRuleList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rebindRuleList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rebindRule" type="{http://www.compositesw.com/services/system/admin/resource}rebindRule" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rebindRuleList", propOrder = {
    "rebindRule"
})
public class RebindRuleList {

    protected List<RebindRule> rebindRule;

    /**
     * Gets the value of the rebindRule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rebindRule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRebindRule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RebindRule }
     * 
     * 
     */
    public List<RebindRule> getRebindRule() {
        if (rebindRule == null) {
            rebindRule = new ArrayList<RebindRule>();
        }
        return this.rebindRule;
    }

}
