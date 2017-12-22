
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resourceUpdates complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resourceUpdates">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addedResources" type="{http://www.compositesw.com/services/system/admin/resource}resourceList" minOccurs="0"/>
 *         &lt;element name="changedResources" type="{http://www.compositesw.com/services/system/admin/resource}resourceList" minOccurs="0"/>
 *         &lt;element name="movedResources" type="{http://www.compositesw.com/services/system/admin/resource}resourceList" minOccurs="0"/>
 *         &lt;element name="deletedResources" type="{http://www.compositesw.com/services/system/admin/resource}resourceIdList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resourceUpdates", propOrder = {
    "addedResources",
    "changedResources",
    "movedResources",
    "deletedResources"
})
public class ResourceUpdates {

    protected ResourceList addedResources;
    protected ResourceList changedResources;
    protected ResourceList movedResources;
    protected ResourceIdList deletedResources;

    /**
     * Gets the value of the addedResources property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceList }
     *     
     */
    public ResourceList getAddedResources() {
        return addedResources;
    }

    /**
     * Sets the value of the addedResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceList }
     *     
     */
    public void setAddedResources(ResourceList value) {
        this.addedResources = value;
    }

    /**
     * Gets the value of the changedResources property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceList }
     *     
     */
    public ResourceList getChangedResources() {
        return changedResources;
    }

    /**
     * Sets the value of the changedResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceList }
     *     
     */
    public void setChangedResources(ResourceList value) {
        this.changedResources = value;
    }

    /**
     * Gets the value of the movedResources property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceList }
     *     
     */
    public ResourceList getMovedResources() {
        return movedResources;
    }

    /**
     * Sets the value of the movedResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceList }
     *     
     */
    public void setMovedResources(ResourceList value) {
        this.movedResources = value;
    }

    /**
     * Gets the value of the deletedResources property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceIdList }
     *     
     */
    public ResourceIdList getDeletedResources() {
        return deletedResources;
    }

    /**
     * Sets the value of the deletedResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceIdList }
     *     
     */
    public void setDeletedResources(ResourceIdList value) {
        this.deletedResources = value;
    }

}
