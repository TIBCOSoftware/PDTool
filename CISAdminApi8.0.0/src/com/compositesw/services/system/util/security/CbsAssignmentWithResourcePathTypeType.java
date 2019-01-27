
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cbsAssignmentWithResourcePathTypeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cbsAssignmentWithResourcePathTypeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resourcePath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resourceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="columnName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paramList" type="{http://www.compositesw.com/services/system/util/security}cbsParamListType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cbsAssignmentWithResourcePathTypeType", propOrder = {
    "resourcePath",
    "resourceType",
    "columnName",
    "paramList"
})
public class CbsAssignmentWithResourcePathTypeType {

    @XmlElement(required = true)
    protected String resourcePath;
    @XmlElement(required = true)
    protected String resourceType;
    @XmlElement(required = true)
    protected String columnName;
    @XmlElement(required = true)
    protected CbsParamListType paramList;

    /**
     * Gets the value of the resourcePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourcePath() {
        return resourcePath;
    }

    /**
     * Sets the value of the resourcePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourcePath(String value) {
        this.resourcePath = value;
    }

    /**
     * Gets the value of the resourceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * Sets the value of the resourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceType(String value) {
        this.resourceType = value;
    }

    /**
     * Gets the value of the columnName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Sets the value of the columnName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnName(String value) {
        this.columnName = value;
    }

    /**
     * Gets the value of the paramList property.
     * 
     * @return
     *     possible object is
     *     {@link CbsParamListType }
     *     
     */
    public CbsParamListType getParamList() {
        return paramList;
    }

    /**
     * Sets the value of the paramList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CbsParamListType }
     *     
     */
    public void setParamList(CbsParamListType value) {
        this.paramList = value;
    }

}
