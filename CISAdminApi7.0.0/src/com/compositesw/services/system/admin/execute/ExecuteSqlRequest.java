
package com.compositesw.services.system.admin.execute;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for executeSqlRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="executeSqlRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/execute}baseExecuteSqlRequest">
 *       &lt;sequence>
 *         &lt;element name="dataServiceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "executeSqlRequest", propOrder = {
    "dataServiceName"
})
public class ExecuteSqlRequest
    extends BaseExecuteSqlRequest
{

    protected String dataServiceName;

    /**
     * Gets the value of the dataServiceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataServiceName() {
        return dataServiceName;
    }

    /**
     * Sets the value of the dataServiceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataServiceName(String value) {
        this.dataServiceName = value;
    }

}
