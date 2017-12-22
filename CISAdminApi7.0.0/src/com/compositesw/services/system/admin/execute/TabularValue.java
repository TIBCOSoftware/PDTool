
package com.compositesw.services.system.admin.execute;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tabularValue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tabularValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="hasMoreRows" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="totalRowCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="rows" type="{http://www.compositesw.com/services/system/admin/execute}rowValueList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tabularValue", propOrder = {
    "hasMoreRows",
    "totalRowCount",
    "rows"
})
public class TabularValue {

    protected boolean hasMoreRows;
    protected long totalRowCount;
    @XmlElement(required = true)
    protected RowValueList rows;

    /**
     * Gets the value of the hasMoreRows property.
     * 
     */
    public boolean isHasMoreRows() {
        return hasMoreRows;
    }

    /**
     * Sets the value of the hasMoreRows property.
     * 
     */
    public void setHasMoreRows(boolean value) {
        this.hasMoreRows = value;
    }

    /**
     * Gets the value of the totalRowCount property.
     * 
     */
    public long getTotalRowCount() {
        return totalRowCount;
    }

    /**
     * Sets the value of the totalRowCount property.
     * 
     */
    public void setTotalRowCount(long value) {
        this.totalRowCount = value;
    }

    /**
     * Gets the value of the rows property.
     * 
     * @return
     *     possible object is
     *     {@link RowValueList }
     *     
     */
    public RowValueList getRows() {
        return rows;
    }

    /**
     * Sets the value of the rows property.
     * 
     * @param value
     *     allowed object is
     *     {@link RowValueList }
     *     
     */
    public void setRows(RowValueList value) {
        this.rows = value;
    }

}
