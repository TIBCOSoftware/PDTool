//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.16 at 10:25:54 AM EDT 
//


package com.tibco.ps.deploytool.modules;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Data Source Choice Type: This selection provides a choice between the a relational source and a generic source.
 * 			
 * 
 * <p>Java class for DataSourceChoiceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataSourceChoiceType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="relationalDataSource" type="{http://www.tibco.com/ps/deploytool/modules}RelationalDataSourceType" minOccurs="0"/&gt;
 *         &lt;element name="genericDataSource" type="{http://www.tibco.com/ps/deploytool/modules}GenericDataSourceType" minOccurs="0"/&gt;
 *         &lt;element name="introspectDataSource" type="{http://www.tibco.com/ps/deploytool/modules}IntrospectDataSourceType" minOccurs="0"/&gt;
 *         &lt;element name="attributeDefsDataSource" type="{http://www.tibco.com/ps/deploytool/modules}AttributeDefsDataSourceType" minOccurs="0"/&gt;
 *         &lt;element name="dataSourceTypesDataSource" type="{http://www.tibco.com/ps/deploytool/modules}DataSourceTypesType" minOccurs="0"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataSourceChoiceType", propOrder = {
    "relationalDataSource",
    "genericDataSource",
    "introspectDataSource",
    "attributeDefsDataSource",
    "dataSourceTypesDataSource"
})
public class DataSourceChoiceType {

    protected RelationalDataSourceType relationalDataSource;
    protected GenericDataSourceType genericDataSource;
    protected IntrospectDataSourceType introspectDataSource;
    protected AttributeDefsDataSourceType attributeDefsDataSource;
    protected DataSourceTypesType dataSourceTypesDataSource;

    /**
     * Gets the value of the relationalDataSource property.
     * 
     * @return
     *     possible object is
     *     {@link RelationalDataSourceType }
     *     
     */
    public RelationalDataSourceType getRelationalDataSource() {
        return relationalDataSource;
    }

    /**
     * Sets the value of the relationalDataSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelationalDataSourceType }
     *     
     */
    public void setRelationalDataSource(RelationalDataSourceType value) {
        this.relationalDataSource = value;
    }

    /**
     * Gets the value of the genericDataSource property.
     * 
     * @return
     *     possible object is
     *     {@link GenericDataSourceType }
     *     
     */
    public GenericDataSourceType getGenericDataSource() {
        return genericDataSource;
    }

    /**
     * Sets the value of the genericDataSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericDataSourceType }
     *     
     */
    public void setGenericDataSource(GenericDataSourceType value) {
        this.genericDataSource = value;
    }

    /**
     * Gets the value of the introspectDataSource property.
     * 
     * @return
     *     possible object is
     *     {@link IntrospectDataSourceType }
     *     
     */
    public IntrospectDataSourceType getIntrospectDataSource() {
        return introspectDataSource;
    }

    /**
     * Sets the value of the introspectDataSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntrospectDataSourceType }
     *     
     */
    public void setIntrospectDataSource(IntrospectDataSourceType value) {
        this.introspectDataSource = value;
    }

    /**
     * Gets the value of the attributeDefsDataSource property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeDefsDataSourceType }
     *     
     */
    public AttributeDefsDataSourceType getAttributeDefsDataSource() {
        return attributeDefsDataSource;
    }

    /**
     * Sets the value of the attributeDefsDataSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeDefsDataSourceType }
     *     
     */
    public void setAttributeDefsDataSource(AttributeDefsDataSourceType value) {
        this.attributeDefsDataSource = value;
    }

    /**
     * Gets the value of the dataSourceTypesDataSource property.
     * 
     * @return
     *     possible object is
     *     {@link DataSourceTypesType }
     *     
     */
    public DataSourceTypesType getDataSourceTypesDataSource() {
        return dataSourceTypesDataSource;
    }

    /**
     * Sets the value of the dataSourceTypesDataSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataSourceTypesType }
     *     
     */
    public void setDataSourceTypesDataSource(DataSourceTypesType value) {
        this.dataSourceTypesDataSource = value;
    }

}
