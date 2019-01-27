
package com.compositesw.services.system.admin.execute;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getResultSetPlanRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getResultSetPlanRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/execute}baseQueryPlanRequest">
 *       &lt;sequence>
 *         &lt;element name="resultId" type="{http://www.compositesw.com/services/system/admin/execute}resultId"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getResultSetPlanRequest", propOrder = {
    "resultId"
})
public class GetResultSetPlanRequest
    extends BaseQueryPlanRequest
{

    @XmlElement(required = true)
    protected String resultId;

    /**
     * Gets the value of the resultId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultId() {
        return resultId;
    }

    /**
     * Sets the value of the resultId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultId(String value) {
        this.resultId = value;
    }

}
