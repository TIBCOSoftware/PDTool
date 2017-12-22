
package com.compositesw.services.system.admin.user;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for domainTypeList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="domainTypeList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="domainType" type="{http://www.compositesw.com/services/system/admin/user}domainType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "domainTypeList", propOrder = {
    "domainType"
})
public class DomainTypeList {

    protected List<DomainType> domainType;

    /**
     * Gets the value of the domainType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the domainType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDomainType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DomainType }
     * 
     * 
     */
    public List<DomainType> getDomainType() {
        if (domainType == null) {
            domainType = new ArrayList<DomainType>();
        }
        return this.domainType;
    }

}
