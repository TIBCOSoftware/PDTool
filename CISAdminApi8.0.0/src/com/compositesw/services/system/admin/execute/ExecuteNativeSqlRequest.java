
package com.compositesw.services.system.admin.execute;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for executeNativeSqlRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="executeNativeSqlRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/execute}baseExecuteSqlRequest">
 *       &lt;sequence>
 *         &lt;element name="dataSourcePath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "executeNativeSqlRequest", propOrder = {
    "dataSourcePath"
})
public class ExecuteNativeSqlRequest
    extends BaseExecuteSqlRequest
{

    @XmlElement(required = true)
    protected String dataSourcePath;

    /**
     * Gets the value of the dataSourcePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSourcePath() {
        return dataSourcePath;
    }

    /**
     * Sets the value of the dataSourcePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSourcePath(String value) {
        this.dataSourcePath = value;
    }

}
