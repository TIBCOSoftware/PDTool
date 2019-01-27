
package com.compositesw.services.system.admin.execute;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for baseExecuteProcedureResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="baseExecuteProcedureResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/execute}baseProceduralResultResponse">
 *       &lt;sequence>
 *         &lt;element name="resultId" type="{http://www.compositesw.com/services/system/admin/execute}resultId"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseExecuteProcedureResponse", propOrder = {
    "resultId",
    "requestId"
})
@XmlSeeAlso({
    ExecuteProcedureResponse.class,
    ExecuteSqlScriptResponse.class
})
public class BaseExecuteProcedureResponse
    extends BaseProceduralResultResponse
{

    @XmlElement(required = true)
    protected String resultId;
    protected long requestId;

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

    /**
     * Gets the value of the requestId property.
     * 
     */
    public long getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     */
    public void setRequestId(long value) {
        this.requestId = value;
    }

}
