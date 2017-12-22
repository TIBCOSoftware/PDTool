
package com.compositesw.services.system.admin.resource;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for updateResourcePrivilegesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateResourcePrivilegesRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="updateRecursively" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="updateDependenciesRecursively" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="updateDependentsRecursively" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
 *         &lt;element name="mode" type="{http://www.compositesw.com/services/system/admin/resource}updatePrivilegesMode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateResourcePrivilegesRequest", propOrder = {
    "updateRecursively",
    "updateDependenciesRecursively",
    "updateDependentsRecursively",
    "privilegeEntries",
    "mode"
})
public class UpdateResourcePrivilegesRequest
    extends BaseRequest
{

    protected boolean updateRecursively;
    protected Boolean updateDependenciesRecursively;
    protected Boolean updateDependentsRecursively;
    @XmlElement(required = true)
    protected UpdateResourcePrivilegesRequest.PrivilegeEntries privilegeEntries;
    protected UpdatePrivilegesMode mode;

    /**
     * Gets the value of the updateRecursively property.
     * 
     */
    public boolean isUpdateRecursively() {
        return updateRecursively;
    }

    /**
     * Sets the value of the updateRecursively property.
     * 
     */
    public void setUpdateRecursively(boolean value) {
        this.updateRecursively = value;
    }

    /**
     * Gets the value of the updateDependenciesRecursively property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUpdateDependenciesRecursively() {
        return updateDependenciesRecursively;
    }

    /**
     * Sets the value of the updateDependenciesRecursively property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUpdateDependenciesRecursively(Boolean value) {
        this.updateDependenciesRecursively = value;
    }

    /**
     * Gets the value of the updateDependentsRecursively property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUpdateDependentsRecursively() {
        return updateDependentsRecursively;
    }

    /**
     * Sets the value of the updateDependentsRecursively property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUpdateDependentsRecursively(Boolean value) {
        this.updateDependentsRecursively = value;
    }

    /**
     * Gets the value of the privilegeEntries property.
     * 
     * @return
     *     possible object is
     *     {@link UpdateResourcePrivilegesRequest.PrivilegeEntries }
     *     
     */
    public UpdateResourcePrivilegesRequest.PrivilegeEntries getPrivilegeEntries() {
        return privilegeEntries;
    }

    /**
     * Sets the value of the privilegeEntries property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpdateResourcePrivilegesRequest.PrivilegeEntries }
     *     
     */
    public void setPrivilegeEntries(UpdateResourcePrivilegesRequest.PrivilegeEntries value) {
        this.privilegeEntries = value;
    }

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link UpdatePrivilegesMode }
     *     
     */
    public UpdatePrivilegesMode getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpdatePrivilegesMode }
     *     
     */
    public void setMode(UpdatePrivilegesMode value) {
        this.mode = value;
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
