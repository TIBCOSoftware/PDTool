
package com.compositesw.services.system.admin.execute;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.admin.resource.ParameterList;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for baseProceduralResultResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="baseProceduralResultResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="completed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="requestStatus" type="{http://www.compositesw.com/services/system/admin/execute}requestStatus"/>
 *         &lt;element name="metadata" type="{http://www.compositesw.com/services/system/admin/resource}parameterList" minOccurs="0"/>
 *         &lt;element name="rowsAffected" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="outputs" type="{http://www.compositesw.com/services/system/admin/execute}valueList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseProceduralResultResponse", propOrder = {
    "completed",
    "requestStatus",
    "metadata",
    "rowsAffected",
    "outputs"
})
@XmlSeeAlso({
    GetProceduralResultResponse.class,
    BaseExecuteProcedureResponse.class
})
public class BaseProceduralResultResponse
    extends BaseResponse
{

    protected boolean completed;
    @XmlElement(required = true)
    protected RequestStatus requestStatus;
    protected ParameterList metadata;
    protected Integer rowsAffected;
    protected ValueList outputs;

    /**
     * Gets the value of the completed property.
     * 
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the value of the completed property.
     * 
     */
    public void setCompleted(boolean value) {
        this.completed = value;
    }

    /**
     * Gets the value of the requestStatus property.
     * 
     * @return
     *     possible object is
     *     {@link RequestStatus }
     *     
     */
    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    /**
     * Sets the value of the requestStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestStatus }
     *     
     */
    public void setRequestStatus(RequestStatus value) {
        this.requestStatus = value;
    }

    /**
     * Gets the value of the metadata property.
     * 
     * @return
     *     possible object is
     *     {@link ParameterList }
     *     
     */
    public ParameterList getMetadata() {
        return metadata;
    }

    /**
     * Sets the value of the metadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParameterList }
     *     
     */
    public void setMetadata(ParameterList value) {
        this.metadata = value;
    }

    /**
     * Gets the value of the rowsAffected property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRowsAffected() {
        return rowsAffected;
    }

    /**
     * Sets the value of the rowsAffected property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRowsAffected(Integer value) {
        this.rowsAffected = value;
    }

    /**
     * Gets the value of the outputs property.
     * 
     * @return
     *     possible object is
     *     {@link ValueList }
     *     
     */
    public ValueList getOutputs() {
        return outputs;
    }

    /**
     * Sets the value of the outputs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueList }
     *     
     */
    public void setOutputs(ValueList value) {
        this.outputs = value;
    }

}
