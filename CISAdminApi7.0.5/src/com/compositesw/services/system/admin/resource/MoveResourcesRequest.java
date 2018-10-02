
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for moveResourcesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="moveResourcesRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}multiPathTypeRequest">
 *       &lt;sequence>
 *         &lt;element name="targetContainerPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="overwrite" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "moveResourcesRequest", propOrder = {
    "targetContainerPath",
    "overwrite"
})
public class MoveResourcesRequest
    extends MultiPathTypeRequest
{

    @XmlElement(required = true)
    protected String targetContainerPath;
    protected boolean overwrite;

    /**
     * Gets the value of the targetContainerPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetContainerPath() {
        return targetContainerPath;
    }

    /**
     * Sets the value of the targetContainerPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetContainerPath(String value) {
        this.targetContainerPath = value;
    }

    /**
     * Gets the value of the overwrite property.
     * 
     */
    public boolean isOverwrite() {
        return overwrite;
    }

    /**
     * Sets the value of the overwrite property.
     * 
     */
    public void setOverwrite(boolean value) {
        this.overwrite = value;
    }

}
