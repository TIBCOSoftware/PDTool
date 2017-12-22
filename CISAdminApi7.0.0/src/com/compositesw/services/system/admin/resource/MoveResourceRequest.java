
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for moveResourceRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="moveResourceRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}pathTypeRequest">
 *       &lt;sequence>
 *         &lt;element name="targetContainerPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="newName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "moveResourceRequest", propOrder = {
    "targetContainerPath",
    "newName",
    "overwrite"
})
public class MoveResourceRequest
    extends PathTypeRequest
{

    @XmlElement(required = true)
    protected String targetContainerPath;
    @XmlElement(required = true)
    protected String newName;
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
     * Gets the value of the newName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewName() {
        return newName;
    }

    /**
     * Sets the value of the newName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewName(String value) {
        this.newName = value;
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
