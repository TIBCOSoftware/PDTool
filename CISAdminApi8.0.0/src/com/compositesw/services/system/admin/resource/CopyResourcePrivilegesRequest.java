
package com.compositesw.services.system.admin.resource;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for copyResourcePrivilegesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="copyResourcePrivilegesRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="updateRecursively" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="copyPrivilegeEntries">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="copyPrivilegeEntry" type="{http://www.compositesw.com/services/system/admin/resource}copyPrivilegeEntryType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "copyResourcePrivilegesRequest", propOrder = {
    "updateRecursively",
    "copyPrivilegeEntries",
    "mode"
})
public class CopyResourcePrivilegesRequest
    extends BaseRequest
{

    protected boolean updateRecursively;
    @XmlElement(required = true)
    protected CopyResourcePrivilegesRequest.CopyPrivilegeEntries copyPrivilegeEntries;
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
     * Gets the value of the copyPrivilegeEntries property.
     * 
     * @return
     *     possible object is
     *     {@link CopyResourcePrivilegesRequest.CopyPrivilegeEntries }
     *     
     */
    public CopyResourcePrivilegesRequest.CopyPrivilegeEntries getCopyPrivilegeEntries() {
        return copyPrivilegeEntries;
    }

    /**
     * Sets the value of the copyPrivilegeEntries property.
     * 
     * @param value
     *     allowed object is
     *     {@link CopyResourcePrivilegesRequest.CopyPrivilegeEntries }
     *     
     */
    public void setCopyPrivilegeEntries(CopyResourcePrivilegesRequest.CopyPrivilegeEntries value) {
        this.copyPrivilegeEntries = value;
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
     *         &lt;element name="copyPrivilegeEntry" type="{http://www.compositesw.com/services/system/admin/resource}copyPrivilegeEntryType" maxOccurs="unbounded" minOccurs="0"/>
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
        "copyPrivilegeEntry"
    })
    public static class CopyPrivilegeEntries {

        protected List<CopyPrivilegeEntryType> copyPrivilegeEntry;

        /**
         * Gets the value of the copyPrivilegeEntry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the copyPrivilegeEntry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCopyPrivilegeEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CopyPrivilegeEntryType }
         * 
         * 
         */
        public List<CopyPrivilegeEntryType> getCopyPrivilegeEntry() {
            if (copyPrivilegeEntry == null) {
                copyPrivilegeEntry = new ArrayList<CopyPrivilegeEntryType>();
            }
            return this.copyPrivilegeEntry;
        }

    }

}
