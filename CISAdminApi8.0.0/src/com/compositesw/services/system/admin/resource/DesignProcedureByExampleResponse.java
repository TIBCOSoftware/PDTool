
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for designProcedureByExampleResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="designProcedureByExampleResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="outputs" type="{http://www.compositesw.com/services/system/admin/resource}parameterList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "designProcedureByExampleResponse", propOrder = {
    "outputs"
})
public class DesignProcedureByExampleResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected ParameterList outputs;

    /**
     * Gets the value of the outputs property.
     * 
     * @return
     *     possible object is
     *     {@link ParameterList }
     *     
     */
    public ParameterList getOutputs() {
        return outputs;
    }

    /**
     * Sets the value of the outputs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParameterList }
     *     
     */
    public void setOutputs(ParameterList value) {
        this.outputs = value;
    }

}
