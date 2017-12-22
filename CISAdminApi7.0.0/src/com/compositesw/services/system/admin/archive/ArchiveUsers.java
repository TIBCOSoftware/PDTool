
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.Marker;


/**
 * <p>Java class for archiveUsers complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="archiveUsers">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="all" type="{http://www.compositesw.com/services/system/util/common}marker" minOccurs="0"/>
 *         &lt;element name="domains" type="{http://www.compositesw.com/services/system/admin/archive}archiveDomainList" minOccurs="0"/>
 *         &lt;element name="users" type="{http://www.compositesw.com/services/system/admin/archive}archiveDomainUserList" minOccurs="0"/>
 *         &lt;element name="groups" type="{http://www.compositesw.com/services/system/admin/archive}archiveDomainGroupList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "archiveUsers", propOrder = {
    "all",
    "domains",
    "users",
    "groups"
})
public class ArchiveUsers {

    protected Marker all;
    protected ArchiveDomainList domains;
    protected ArchiveDomainUserList users;
    protected ArchiveDomainGroupList groups;

    /**
     * Gets the value of the all property.
     * 
     * @return
     *     possible object is
     *     {@link Marker }
     *     
     */
    public Marker getAll() {
        return all;
    }

    /**
     * Sets the value of the all property.
     * 
     * @param value
     *     allowed object is
     *     {@link Marker }
     *     
     */
    public void setAll(Marker value) {
        this.all = value;
    }

    /**
     * Gets the value of the domains property.
     * 
     * @return
     *     possible object is
     *     {@link ArchiveDomainList }
     *     
     */
    public ArchiveDomainList getDomains() {
        return domains;
    }

    /**
     * Sets the value of the domains property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArchiveDomainList }
     *     
     */
    public void setDomains(ArchiveDomainList value) {
        this.domains = value;
    }

    /**
     * Gets the value of the users property.
     * 
     * @return
     *     possible object is
     *     {@link ArchiveDomainUserList }
     *     
     */
    public ArchiveDomainUserList getUsers() {
        return users;
    }

    /**
     * Sets the value of the users property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArchiveDomainUserList }
     *     
     */
    public void setUsers(ArchiveDomainUserList value) {
        this.users = value;
    }

    /**
     * Gets the value of the groups property.
     * 
     * @return
     *     possible object is
     *     {@link ArchiveDomainGroupList }
     *     
     */
    public ArchiveDomainGroupList getGroups() {
        return groups;
    }

    /**
     * Sets the value of the groups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArchiveDomainGroupList }
     *     
     */
    public void setGroups(ArchiveDomainGroupList value) {
        this.groups = value;
    }

}
