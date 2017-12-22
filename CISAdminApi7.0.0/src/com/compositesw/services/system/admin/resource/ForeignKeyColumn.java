
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for foreignKeyColumn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="foreignKeyColumn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="foreignKeyColumnName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="primaryKeyColumnName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "foreignKeyColumn", propOrder = {
    "foreignKeyColumnName",
    "primaryKeyColumnName"
})
public class ForeignKeyColumn {

    @XmlElement(required = true)
    protected String foreignKeyColumnName;
    @XmlElement(required = true)
    protected String primaryKeyColumnName;

    /**
     * Gets the value of the foreignKeyColumnName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForeignKeyColumnName() {
        return foreignKeyColumnName;
    }

    /**
     * Sets the value of the foreignKeyColumnName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForeignKeyColumnName(String value) {
        this.foreignKeyColumnName = value;
    }

    /**
     * Gets the value of the primaryKeyColumnName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryKeyColumnName() {
        return primaryKeyColumnName;
    }

    /**
     * Sets the value of the primaryKeyColumnName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryKeyColumnName(String value) {
        this.primaryKeyColumnName = value;
    }

}
