
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for copyResourcesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="copyResourcesRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}multiPathTypeRequest">
 *       &lt;sequence>
 *         &lt;element name="targetContainerPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="copyMode" type="{http://www.compositesw.com/services/system/admin/resource}copyMode"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "copyResourcesRequest", propOrder = {
    "targetContainerPath",
    "copyMode"
})
public class CopyResourcesRequest
    extends MultiPathTypeRequest
{

    @XmlElement(required = true)
    protected String targetContainerPath;
    @XmlElement(required = true)
    protected CopyMode copyMode;

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
     * Gets the value of the copyMode property.
     * 
     * @return
     *     possible object is
     *     {@link CopyMode }
     *     
     */
    public CopyMode getCopyMode() {
        return copyMode;
    }

    /**
     * Sets the value of the copyMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CopyMode }
     *     
     */
    public void setCopyMode(CopyMode value) {
        this.copyMode = value;
    }

}
