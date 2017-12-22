
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.admin.resource.PathTypePairList;
import com.compositesw.services.system.util.common.NameList;


/**
 * <p>Java class for importSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="importSettings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="excludeResources" type="{http://www.compositesw.com/services/system/admin/resource}pathTypePairList" minOccurs="0"/>
 *         &lt;element name="relocateResources" type="{http://www.compositesw.com/services/system/admin/archive}pathTypeMapList" minOccurs="0"/>
 *         &lt;element name="rebindResources" type="{http://www.compositesw.com/services/system/admin/archive}pathTypeMapList" minOccurs="0"/>
 *         &lt;element name="rebindUsers" type="{http://www.compositesw.com/services/system/admin/archive}userMapList" minOccurs="0"/>
 *         &lt;element name="remapAttributes" type="{http://www.compositesw.com/services/system/admin/archive}resourceAttributeList" minOccurs="0"/>
 *         &lt;element name="importOptions" type="{http://www.compositesw.com/services/system/util/common}nameList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "importSettings", propOrder = {
    "excludeResources",
    "relocateResources",
    "rebindResources",
    "rebindUsers",
    "remapAttributes",
    "importOptions"
})
public class ImportSettings {

    protected PathTypePairList excludeResources;
    protected PathTypeMapList relocateResources;
    protected PathTypeMapList rebindResources;
    protected UserMapList rebindUsers;
    protected ResourceAttributeList remapAttributes;
    protected NameList importOptions;

    /**
     * Gets the value of the excludeResources property.
     * 
     * @return
     *     possible object is
     *     {@link PathTypePairList }
     *     
     */
    public PathTypePairList getExcludeResources() {
        return excludeResources;
    }

    /**
     * Sets the value of the excludeResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathTypePairList }
     *     
     */
    public void setExcludeResources(PathTypePairList value) {
        this.excludeResources = value;
    }

    /**
     * Gets the value of the relocateResources property.
     * 
     * @return
     *     possible object is
     *     {@link PathTypeMapList }
     *     
     */
    public PathTypeMapList getRelocateResources() {
        return relocateResources;
    }

    /**
     * Sets the value of the relocateResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathTypeMapList }
     *     
     */
    public void setRelocateResources(PathTypeMapList value) {
        this.relocateResources = value;
    }

    /**
     * Gets the value of the rebindResources property.
     * 
     * @return
     *     possible object is
     *     {@link PathTypeMapList }
     *     
     */
    public PathTypeMapList getRebindResources() {
        return rebindResources;
    }

    /**
     * Sets the value of the rebindResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathTypeMapList }
     *     
     */
    public void setRebindResources(PathTypeMapList value) {
        this.rebindResources = value;
    }

    /**
     * Gets the value of the rebindUsers property.
     * 
     * @return
     *     possible object is
     *     {@link UserMapList }
     *     
     */
    public UserMapList getRebindUsers() {
        return rebindUsers;
    }

    /**
     * Sets the value of the rebindUsers property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserMapList }
     *     
     */
    public void setRebindUsers(UserMapList value) {
        this.rebindUsers = value;
    }

    /**
     * Gets the value of the remapAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceAttributeList }
     *     
     */
    public ResourceAttributeList getRemapAttributes() {
        return remapAttributes;
    }

    /**
     * Sets the value of the remapAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceAttributeList }
     *     
     */
    public void setRemapAttributes(ResourceAttributeList value) {
        this.remapAttributes = value;
    }

    /**
     * Gets the value of the importOptions property.
     * 
     * @return
     *     possible object is
     *     {@link NameList }
     *     
     */
    public NameList getImportOptions() {
        return importOptions;
    }

    /**
     * Sets the value of the importOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameList }
     *     
     */
    public void setImportOptions(NameList value) {
        this.importOptions = value;
    }

}
