
package com.compositesw.services.system.admin.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for createDBHealthMonitorTableRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createDBHealthMonitorTableRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="tablePath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tableName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createDBHealthMonitorTableRequest", propOrder = {
    "tablePath",
    "tableName"
})
public class CreateDBHealthMonitorTableRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected String tablePath;
    @XmlElement(required = true)
    protected String tableName;

    /**
     * Gets the value of the tablePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTablePath() {
        return tablePath;
    }

    /**
     * Sets the value of the tablePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTablePath(String value) {
        this.tablePath = value;
    }

    /**
     * Gets the value of the tableName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets the value of the tableName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTableName(String value) {
        this.tableName = value;
    }

}
