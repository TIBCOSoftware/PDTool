
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for model complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="model">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="type" type="{http://www.compositesw.com/services/system/admin/resource}modelType"/>
 *         &lt;element name="proprietaryModel" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "model", propOrder = {
    "version",
    "type",
    "proprietaryModel"
})
public class Model {

    protected int version;
    @XmlElement(required = true)
    protected ModelType type;
    protected byte[] proprietaryModel;

    /**
     * Gets the value of the version property.
     * 
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     */
    public void setVersion(int value) {
        this.version = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ModelType }
     *     
     */
    public ModelType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModelType }
     *     
     */
    public void setType(ModelType value) {
        this.type = value;
    }

    /**
     * Gets the value of the proprietaryModel property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getProprietaryModel() {
        return proprietaryModel;
    }

    /**
     * Sets the value of the proprietaryModel property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setProprietaryModel(byte[] value) {
        this.proprietaryModel = ((byte[]) value);
    }

}
