
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.DataType;


/**
 * <p>Java class for treeResource complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="treeResource">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}resource">
 *       &lt;sequence>
 *         &lt;element name="dataType" type="{http://www.compositesw.com/services/system/util/common}dataType" minOccurs="0"/>
 *         &lt;element name="xmlFilePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xmlFileUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xmlCharset" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xmlSchemaLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xmlNoNamespaceSchemaLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "treeResource", propOrder = {
    "dataType",
    "xmlFilePath",
    "xmlFileUrl",
    "xmlCharset",
    "xmlSchemaLocation",
    "xmlNoNamespaceSchemaLocation"
})
public class TreeResource
    extends Resource
{

    protected DataType dataType;
    protected String xmlFilePath;
    protected String xmlFileUrl;
    protected String xmlCharset;
    protected String xmlSchemaLocation;
    protected String xmlNoNamespaceSchemaLocation;

    /**
     * Gets the value of the dataType property.
     * 
     * @return
     *     possible object is
     *     {@link DataType }
     *     
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataType }
     *     
     */
    public void setDataType(DataType value) {
        this.dataType = value;
    }

    /**
     * Gets the value of the xmlFilePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlFilePath() {
        return xmlFilePath;
    }

    /**
     * Sets the value of the xmlFilePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlFilePath(String value) {
        this.xmlFilePath = value;
    }

    /**
     * Gets the value of the xmlFileUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlFileUrl() {
        return xmlFileUrl;
    }

    /**
     * Sets the value of the xmlFileUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlFileUrl(String value) {
        this.xmlFileUrl = value;
    }

    /**
     * Gets the value of the xmlCharset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlCharset() {
        return xmlCharset;
    }

    /**
     * Sets the value of the xmlCharset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlCharset(String value) {
        this.xmlCharset = value;
    }

    /**
     * Gets the value of the xmlSchemaLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlSchemaLocation() {
        return xmlSchemaLocation;
    }

    /**
     * Sets the value of the xmlSchemaLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlSchemaLocation(String value) {
        this.xmlSchemaLocation = value;
    }

    /**
     * Gets the value of the xmlNoNamespaceSchemaLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlNoNamespaceSchemaLocation() {
        return xmlNoNamespaceSchemaLocation;
    }

    /**
     * Sets the value of the xmlNoNamespaceSchemaLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlNoNamespaceSchemaLocation(String value) {
        this.xmlNoNamespaceSchemaLocation = value;
    }

}
