
package com.compositesw.services.system.admin.resource;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getResourcePrivilegesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getResourcePrivilegesResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="privilegeEntries">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="privilegeEntry" type="{http://www.compositesw.com/services/system/admin/resource}privilegeEntry" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getResourcePrivilegesResponse", propOrder = {
    "privilegeEntries"
})
public class GetResourcePrivilegesResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected GetResourcePrivilegesResponse.PrivilegeEntries privilegeEntries;

    /**
     * Gets the value of the privilegeEntries property.
     * 
     * @return
     *     possible object is
     *     {@link GetResourcePrivilegesResponse.PrivilegeEntries }
     *     
     */
    public GetResourcePrivilegesResponse.PrivilegeEntries getPrivilegeEntries() {
        return privilegeEntries;
    }

    /**
     * Sets the value of the privilegeEntries property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetResourcePrivilegesResponse.PrivilegeEntries }
     *     
     */
    public void setPrivilegeEntries(GetResourcePrivilegesResponse.PrivilegeEntries value) {
        this.privilegeEntries = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="privilegeEntry" type="{http://www.compositesw.com/services/system/admin/resource}privilegeEntry" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "privilegeEntry"
    })
    public static class PrivilegeEntries {

        protected List<PrivilegeEntry> privilegeEntry;

        /**
         * Gets the value of the privilegeEntry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the privilegeEntry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPrivilegeEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PrivilegeEntry }
         * 
         * 
         */
        public List<PrivilegeEntry> getPrivilegeEntry() {
            if (privilegeEntry == null) {
                privilegeEntry = new ArrayList<PrivilegeEntry>();
            }
            return this.privilegeEntry;
        }

    }

}
