
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeList;


/**
 * <p>Java class for updateSqlTableRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateSqlTableRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}pathDetailRequest">
 *       &lt;sequence>
 *         &lt;element name="sqlText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sqlModel" type="{http://www.compositesw.com/services/system/admin/resource}model" minOccurs="0"/>
 *         &lt;element name="isExplicitDesign" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="columns" type="{http://www.compositesw.com/services/system/admin/resource}columnList" minOccurs="0"/>
 *         &lt;element name="annotation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attributes" type="{http://www.compositesw.com/services/system/util/common}attributeList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateSqlTableRequest", propOrder = {
    "sqlText",
    "sqlModel",
    "isExplicitDesign",
    "columns",
    "annotation",
    "attributes"
})
public class UpdateSqlTableRequest
    extends PathDetailRequest
{

    @XmlElement(required = true)
    protected String sqlText;
    protected Model sqlModel;
    protected Boolean isExplicitDesign;
    protected ColumnList columns;
    protected String annotation;
    protected AttributeList attributes;

    /**
     * Gets the value of the sqlText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSqlText() {
        return sqlText;
    }

    /**
     * Sets the value of the sqlText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSqlText(String value) {
        this.sqlText = value;
    }

    /**
     * Gets the value of the sqlModel property.
     * 
     * @return
     *     possible object is
     *     {@link Model }
     *     
     */
    public Model getSqlModel() {
        return sqlModel;
    }

    /**
     * Sets the value of the sqlModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Model }
     *     
     */
    public void setSqlModel(Model value) {
        this.sqlModel = value;
    }

    /**
     * Gets the value of the isExplicitDesign property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsExplicitDesign() {
        return isExplicitDesign;
    }

    /**
     * Sets the value of the isExplicitDesign property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsExplicitDesign(Boolean value) {
        this.isExplicitDesign = value;
    }

    /**
     * Gets the value of the columns property.
     * 
     * @return
     *     possible object is
     *     {@link ColumnList }
     *     
     */
    public ColumnList getColumns() {
        return columns;
    }

    /**
     * Sets the value of the columns property.
     * 
     * @param value
     *     allowed object is
     *     {@link ColumnList }
     *     
     */
    public void setColumns(ColumnList value) {
        this.columns = value;
    }

    /**
     * Gets the value of the annotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * Sets the value of the annotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnotation(String value) {
        this.annotation = value;
    }

    /**
     * Gets the value of the attributes property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeList }
     *     
     */
    public AttributeList getAttributes() {
        return attributes;
    }

    /**
     * Sets the value of the attributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeList }
     *     
     */
    public void setAttributes(AttributeList value) {
        this.attributes = value;
    }

}
