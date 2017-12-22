
package com.compositesw.services.system.admin.resource;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resourceBundle complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resourceBundle">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resource" type="{http://www.compositesw.com/services/system/admin/resource}resource"/>
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
 *         &lt;element name="cacheConfig" type="{http://www.compositesw.com/services/system/admin/resource}cacheConfig" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resourceBundle", propOrder = {
    "resource",
    "privilegeEntries",
    "cacheConfig"
})
public class ResourceBundle {

    @XmlElement(required = true)
    protected Resource resource;
    @XmlElement(required = true)
    protected ResourceBundle.PrivilegeEntries privilegeEntries;
    protected CacheConfig cacheConfig;

    /**
     * Gets the value of the resource property.
     * 
     * @return
     *     possible object is
     *     {@link Resource }
     *     
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * Sets the value of the resource property.
     * 
     * @param value
     *     allowed object is
     *     {@link Resource }
     *     
     */
    public void setResource(Resource value) {
        this.resource = value;
    }

    /**
     * Gets the value of the privilegeEntries property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceBundle.PrivilegeEntries }
     *     
     */
    public ResourceBundle.PrivilegeEntries getPrivilegeEntries() {
        return privilegeEntries;
    }

    /**
     * Sets the value of the privilegeEntries property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceBundle.PrivilegeEntries }
     *     
     */
    public void setPrivilegeEntries(ResourceBundle.PrivilegeEntries value) {
        this.privilegeEntries = value;
    }

    /**
     * Gets the value of the cacheConfig property.
     * 
     * @return
     *     possible object is
     *     {@link CacheConfig }
     *     
     */
    public CacheConfig getCacheConfig() {
        return cacheConfig;
    }

    /**
     * Sets the value of the cacheConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link CacheConfig }
     *     
     */
    public void setCacheConfig(CacheConfig value) {
        this.cacheConfig = value;
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
