
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createLinksRecursivelyRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createLinksRecursivelyRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}pathTypeDetailRequest">
 *       &lt;sequence>
 *         &lt;element name="targetContainerPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="createMode" type="{http://www.compositesw.com/services/system/admin/resource}copyMode"/>
 *         &lt;element name="includeRoot" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="copyAnnotations" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createLinksRecursivelyRequest", propOrder = {
    "targetContainerPath",
    "createMode",
    "includeRoot",
    "copyAnnotations"
})
public class CreateLinksRecursivelyRequest
    extends PathTypeDetailRequest
{

    @XmlElement(required = true)
    protected String targetContainerPath;
    @XmlElement(required = true)
    protected CopyMode createMode;
    protected boolean includeRoot;
    protected boolean copyAnnotations;

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
     * Gets the value of the createMode property.
     * 
     * @return
     *     possible object is
     *     {@link CopyMode }
     *     
     */
    public CopyMode getCreateMode() {
        return createMode;
    }

    /**
     * Sets the value of the createMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CopyMode }
     *     
     */
    public void setCreateMode(CopyMode value) {
        this.createMode = value;
    }

    /**
     * Gets the value of the includeRoot property.
     * 
     */
    public boolean isIncludeRoot() {
        return includeRoot;
    }

    /**
     * Sets the value of the includeRoot property.
     * 
     */
    public void setIncludeRoot(boolean value) {
        this.includeRoot = value;
    }

    /**
     * Gets the value of the copyAnnotations property.
     * 
     */
    public boolean isCopyAnnotations() {
        return copyAnnotations;
    }

    /**
     * Sets the value of the copyAnnotations property.
     * 
     */
    public void setCopyAnnotations(boolean value) {
        this.copyAnnotations = value;
    }

}
