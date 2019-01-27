
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.admin.resource.PathTypePairList;
import com.compositesw.services.system.util.common.NameList;


/**
 * <p>Java class for archiveContents complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="archiveContents">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resources" type="{http://www.compositesw.com/services/system/admin/resource}pathTypePairList" minOccurs="0"/>
 *         &lt;element name="users" type="{http://www.compositesw.com/services/system/admin/archive}archiveUsers" minOccurs="0"/>
 *         &lt;element name="serverAttributes" type="{http://www.compositesw.com/services/system/util/common}nameList" minOccurs="0"/>
 *         &lt;element name="exportOptions" type="{http://www.compositesw.com/services/system/util/common}nameList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "archiveContents", propOrder = {
    "resources",
    "users",
    "serverAttributes",
    "exportOptions"
})
public class ArchiveContents {

    protected PathTypePairList resources;
    protected ArchiveUsers users;
    protected NameList serverAttributes;
    protected NameList exportOptions;

    /**
     * Gets the value of the resources property.
     * 
     * @return
     *     possible object is
     *     {@link PathTypePairList }
     *     
     */
    public PathTypePairList getResources() {
        return resources;
    }

    /**
     * Sets the value of the resources property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathTypePairList }
     *     
     */
    public void setResources(PathTypePairList value) {
        this.resources = value;
    }

    /**
     * Gets the value of the users property.
     * 
     * @return
     *     possible object is
     *     {@link ArchiveUsers }
     *     
     */
    public ArchiveUsers getUsers() {
        return users;
    }

    /**
     * Sets the value of the users property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArchiveUsers }
     *     
     */
    public void setUsers(ArchiveUsers value) {
        this.users = value;
    }

    /**
     * Gets the value of the serverAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link NameList }
     *     
     */
    public NameList getServerAttributes() {
        return serverAttributes;
    }

    /**
     * Sets the value of the serverAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameList }
     *     
     */
    public void setServerAttributes(NameList value) {
        this.serverAttributes = value;
    }

    /**
     * Gets the value of the exportOptions property.
     * 
     * @return
     *     possible object is
     *     {@link NameList }
     *     
     */
    public NameList getExportOptions() {
        return exportOptions;
    }

    /**
     * Sets the value of the exportOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameList }
     *     
     */
    public void setExportOptions(NameList value) {
        this.exportOptions = value;
    }

}
