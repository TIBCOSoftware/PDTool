
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.admin.resource.PathTypePairList;


/**
 * <p>Java class for importHints complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="importHints">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rebindResources" type="{http://www.compositesw.com/services/system/admin/resource}pathTypePairList" minOccurs="0"/>
 *         &lt;element name="rebindUsers" type="{http://www.compositesw.com/services/system/admin/archive}archiveDomainUserList" minOccurs="0"/>
 *         &lt;element name="remapAttributes" type="{http://www.compositesw.com/services/system/admin/archive}resourceAttributeNamesList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "importHints", propOrder = {
    "rebindResources",
    "rebindUsers",
    "remapAttributes"
})
public class ImportHints {

    protected PathTypePairList rebindResources;
    protected ArchiveDomainUserList rebindUsers;
    protected ResourceAttributeNamesList remapAttributes;

    /**
     * Gets the value of the rebindResources property.
     * 
     * @return
     *     possible object is
     *     {@link PathTypePairList }
     *     
     */
    public PathTypePairList getRebindResources() {
        return rebindResources;
    }

    /**
     * Sets the value of the rebindResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathTypePairList }
     *     
     */
    public void setRebindResources(PathTypePairList value) {
        this.rebindResources = value;
    }

    /**
     * Gets the value of the rebindUsers property.
     * 
     * @return
     *     possible object is
     *     {@link ArchiveDomainUserList }
     *     
     */
    public ArchiveDomainUserList getRebindUsers() {
        return rebindUsers;
    }

    /**
     * Sets the value of the rebindUsers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArchiveDomainUserList }
     *     
     */
    public void setRebindUsers(ArchiveDomainUserList value) {
        this.rebindUsers = value;
    }

    /**
     * Gets the value of the remapAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceAttributeNamesList }
     *     
     */
    public ResourceAttributeNamesList getRemapAttributes() {
        return remapAttributes;
    }

    /**
     * Sets the value of the remapAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceAttributeNamesList }
     *     
     */
    public void setRemapAttributes(ResourceAttributeNamesList value) {
        this.remapAttributes = value;
    }

}
