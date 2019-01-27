
package com.compositesw.services.system.admin.resource;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for privilegeEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="privilegeEntry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.compositesw.com/services/system/admin/resource}resourceOrColumnType"/>
 *         &lt;element name="privileges">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="privilege" type="{http://www.compositesw.com/services/system/admin/resource}privilege" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "privilegeEntry", propOrder = {
    "path",
    "type",
    "privileges"
})
public class PrivilegeEntry {

    @XmlElement(required = true)
    protected String path;
    @XmlElement(required = true)
    protected ResourceOrColumnType type;
    @XmlElement(required = true)
    protected PrivilegeEntry.Privileges privileges;

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceOrColumnType }
     *     
     */
    public ResourceOrColumnType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceOrColumnType }
     *     
     */
    public void setType(ResourceOrColumnType value) {
        this.type = value;
    }

    /**
     * Gets the value of the privileges property.
     * 
     * @return
     *     possible object is
     *     {@link PrivilegeEntry.Privileges }
     *     
     */
    public PrivilegeEntry.Privileges getPrivileges() {
        return privileges;
    }

    /**
     * Sets the value of the privileges property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrivilegeEntry.Privileges }
     *     
     */
    public void setPrivileges(PrivilegeEntry.Privileges value) {
        this.privileges = value;
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
     *         &lt;element name="privilege" type="{http://www.compositesw.com/services/system/admin/resource}privilege" maxOccurs="unbounded" minOccurs="0"/>
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
        "privilege"
    })
    public static class Privileges {

        protected List<Privilege> privilege;

        /**
         * Gets the value of the privilege property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the privilege property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPrivilege().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Privilege }
         * 
         * 
         */
        public List<Privilege> getPrivilege() {
            if (privilege == null) {
                privilege = new ArrayList<Privilege>();
            }
            return this.privilege;
        }

    }

}
