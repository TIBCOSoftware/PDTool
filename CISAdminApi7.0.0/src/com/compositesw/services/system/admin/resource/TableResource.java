
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tableResource complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tableResource">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}resource">
 *       &lt;sequence>
 *         &lt;element name="columns" type="{http://www.compositesw.com/services/system/admin/resource}columnList" minOccurs="0"/>
 *         &lt;element name="tableType" type="{http://www.compositesw.com/services/system/admin/resource}tableType" minOccurs="0"/>
 *         &lt;element name="explicitlyDesigned" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="sqlText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sqlModel" type="{http://www.compositesw.com/services/system/admin/resource}model" minOccurs="0"/>
 *         &lt;element name="sqlIndexes" type="{http://www.compositesw.com/services/system/admin/resource}indexList" minOccurs="0"/>
 *         &lt;element name="sqlForeignKeys" type="{http://www.compositesw.com/services/system/admin/resource}foreignKeyList" minOccurs="0"/>
 *         &lt;element name="fileUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileCharset" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileDelimiter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileHeader" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="fileQualifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileEol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tableResource", propOrder = {
    "columns",
    "tableType",
    "explicitlyDesigned",
    "sqlText",
    "sqlModel",
    "sqlIndexes",
    "sqlForeignKeys",
    "fileUrl",
    "filePath",
    "fileCharset",
    "fileDelimiter",
    "fileHeader",
    "fileQualifier",
    "fileEol"
})
public class TableResource
    extends Resource
{

    protected ColumnList columns;
    protected TableType tableType;
    protected Boolean explicitlyDesigned;
    protected String sqlText;
    protected Model sqlModel;
    protected IndexList sqlIndexes;
    protected ForeignKeyList sqlForeignKeys;
    protected String fileUrl;
    protected String filePath;
    protected String fileCharset;
    protected String fileDelimiter;
    protected Boolean fileHeader;
    protected String fileQualifier;
    protected String fileEol;

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
     * Gets the value of the tableType property.
     * 
     * @return
     *     possible object is
     *     {@link TableType }
     *     
     */
    public TableType getTableType() {
        return tableType;
    }

    /**
     * Sets the value of the tableType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TableType }
     *     
     */
    public void setTableType(TableType value) {
        this.tableType = value;
    }

    /**
     * Gets the value of the explicitlyDesigned property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExplicitlyDesigned() {
        return explicitlyDesigned;
    }

    /**
     * Sets the value of the explicitlyDesigned property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExplicitlyDesigned(Boolean value) {
        this.explicitlyDesigned = value;
    }

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
     * Gets the value of the sqlIndexes property.
     * 
     * @return
     *     possible object is
     *     {@link IndexList }
     *     
     */
    public IndexList getSqlIndexes() {
        return sqlIndexes;
    }

    /**
     * Sets the value of the sqlIndexes property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndexList }
     *     
     */
    public void setSqlIndexes(IndexList value) {
        this.sqlIndexes = value;
    }

    /**
     * Gets the value of the sqlForeignKeys property.
     * 
     * @return
     *     possible object is
     *     {@link ForeignKeyList }
     *     
     */
    public ForeignKeyList getSqlForeignKeys() {
        return sqlForeignKeys;
    }

    /**
     * Sets the value of the sqlForeignKeys property.
     * 
     * @param value
     *     allowed object is
     *     {@link ForeignKeyList }
     *     
     */
    public void setSqlForeignKeys(ForeignKeyList value) {
        this.sqlForeignKeys = value;
    }

    /**
     * Gets the value of the fileUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * Sets the value of the fileUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileUrl(String value) {
        this.fileUrl = value;
    }

    /**
     * Gets the value of the filePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the value of the filePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilePath(String value) {
        this.filePath = value;
    }

    /**
     * Gets the value of the fileCharset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileCharset() {
        return fileCharset;
    }

    /**
     * Sets the value of the fileCharset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileCharset(String value) {
        this.fileCharset = value;
    }

    /**
     * Gets the value of the fileDelimiter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileDelimiter() {
        return fileDelimiter;
    }

    /**
     * Sets the value of the fileDelimiter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileDelimiter(String value) {
        this.fileDelimiter = value;
    }

    /**
     * Gets the value of the fileHeader property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFileHeader() {
        return fileHeader;
    }

    /**
     * Sets the value of the fileHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFileHeader(Boolean value) {
        this.fileHeader = value;
    }

    /**
     * Gets the value of the fileQualifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileQualifier() {
        return fileQualifier;
    }

    /**
     * Sets the value of the fileQualifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileQualifier(String value) {
        this.fileQualifier = value;
    }

    /**
     * Gets the value of the fileEol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileEol() {
        return fileEol;
    }

    /**
     * Sets the value of the fileEol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileEol(String value) {
        this.fileEol = value;
    }

}
