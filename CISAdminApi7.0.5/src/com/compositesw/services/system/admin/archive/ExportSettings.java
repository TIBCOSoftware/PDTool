
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.NameList;


/**
 * <p>Java class for exportSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exportSettings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.compositesw.com/services/system/admin/archive}archiveTypes"/>
 *         &lt;element name="resources" type="{http://www.compositesw.com/services/system/admin/archive}archiveResources" minOccurs="0"/>
 *         &lt;element name="users" type="{http://www.compositesw.com/services/system/admin/archive}archiveUsers" minOccurs="0"/>
 *         &lt;element name="serverAttributes" type="{http://www.compositesw.com/services/system/admin/archive}exportServerAttributes" minOccurs="0"/>
 *         &lt;element name="exportOptions" type="{http://www.compositesw.com/services/system/util/common}nameList" minOccurs="0"/>
 *         &lt;element name="importHints" type="{http://www.compositesw.com/services/system/admin/archive}importHints" minOccurs="0"/>
 *         &lt;element name="createInfo" type="{http://www.compositesw.com/services/system/admin/archive}exportCreateInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exportSettings", propOrder = {
    "name",
    "description",
    "type",
    "resources",
    "users",
    "serverAttributes",
    "exportOptions",
    "importHints",
    "createInfo"
})
public class ExportSettings {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected ArchiveTypes type;
    protected ArchiveResources resources;
    protected ArchiveUsers users;
    protected ExportServerAttributes serverAttributes;
    protected NameList exportOptions;
    protected ImportHints importHints;
    protected ExportCreateInfo createInfo;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ArchiveTypes }
     *     
     */
    public ArchiveTypes getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArchiveTypes }
     *     
     */
    public void setType(ArchiveTypes value) {
        this.type = value;
    }

    /**
     * Gets the value of the resources property.
     * 
     * @return
     *     possible object is
     *     {@link ArchiveResources }
     *     
     */
    public ArchiveResources getResources() {
        return resources;
    }

    /**
     * Sets the value of the resources property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArchiveResources }
     *     
     */
    public void setResources(ArchiveResources value) {
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
     *     {@link ExportServerAttributes }
     *     
     */
    public ExportServerAttributes getServerAttributes() {
        return serverAttributes;
    }

    /**
     * Sets the value of the serverAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportServerAttributes }
     *     
     */
    public void setServerAttributes(ExportServerAttributes value) {
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

    /**
     * Gets the value of the importHints property.
     * 
     * @return
     *     possible object is
     *     {@link ImportHints }
     *     
     */
    public ImportHints getImportHints() {
        return importHints;
    }

    /**
     * Sets the value of the importHints property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImportHints }
     *     
     */
    public void setImportHints(ImportHints value) {
        this.importHints = value;
    }

    /**
     * Gets the value of the createInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ExportCreateInfo }
     *     
     */
    public ExportCreateInfo getCreateInfo() {
        return createInfo;
    }

    /**
     * Sets the value of the createInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportCreateInfo }
     *     
     */
    public void setCreateInfo(ExportCreateInfo value) {
        this.createInfo = value;
    }

}
