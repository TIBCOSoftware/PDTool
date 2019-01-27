
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.Marker;


/**
 * <p>Java class for archiveResources complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="archiveResources">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="all" type="{http://www.compositesw.com/services/system/util/common}marker" minOccurs="0"/>
 *         &lt;element name="resources" type="{http://www.compositesw.com/services/system/admin/archive}exportResourceList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "archiveResources", propOrder = {
    "all",
    "resources"
})
public class ArchiveResources {

    protected Marker all;
    protected ExportResourceList resources;

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
     * Gets the value of the resources property.
     * 
     * @return
     *     possible object is
     *     {@link ExportResourceList }
     *     
     */
    public ExportResourceList getResources() {
        return resources;
    }

    /**
     * Sets the value of the resources property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportResourceList }
     *     
     */
    public void setResources(ExportResourceList value) {
        this.resources = value;
    }

}
