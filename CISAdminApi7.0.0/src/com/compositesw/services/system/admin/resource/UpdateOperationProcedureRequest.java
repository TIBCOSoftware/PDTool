
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateOperationProcedureRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateOperationProcedureRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}pathDetailRequest">
 *       &lt;sequence>
 *         &lt;element name="usePortMappings" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="inputPipeline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inputMappingType" type="{http://www.compositesw.com/services/system/admin/resource}messageMappingType" minOccurs="0"/>
 *         &lt;element name="inputMappingOptions" type="{http://www.compositesw.com/services/system/admin/resource}messageMappingOptionList" minOccurs="0"/>
 *         &lt;element name="outputPipeline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outputMappingType" type="{http://www.compositesw.com/services/system/admin/resource}messageMappingType" minOccurs="0"/>
 *         &lt;element name="outputMappingOptions" type="{http://www.compositesw.com/services/system/admin/resource}messageMappingOptionList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateOperationProcedureRequest", propOrder = {
    "usePortMappings",
    "inputPipeline",
    "inputMappingType",
    "inputMappingOptions",
    "outputPipeline",
    "outputMappingType",
    "outputMappingOptions"
})
public class UpdateOperationProcedureRequest
    extends PathDetailRequest
{

    protected Boolean usePortMappings;
    protected String inputPipeline;
    protected MessageMappingType inputMappingType;
    protected String inputMappingOptions;
    protected String outputPipeline;
    protected MessageMappingType outputMappingType;
    protected String outputMappingOptions;

    /**
     * Gets the value of the usePortMappings property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUsePortMappings() {
        return usePortMappings;
    }

    /**
     * Sets the value of the usePortMappings property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUsePortMappings(Boolean value) {
        this.usePortMappings = value;
    }

    /**
     * Gets the value of the inputPipeline property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputPipeline() {
        return inputPipeline;
    }

    /**
     * Sets the value of the inputPipeline property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputPipeline(String value) {
        this.inputPipeline = value;
    }

    /**
     * Gets the value of the inputMappingType property.
     * 
     * @return
     *     possible object is
     *     {@link MessageMappingType }
     *     
     */
    public MessageMappingType getInputMappingType() {
        return inputMappingType;
    }

    /**
     * Sets the value of the inputMappingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageMappingType }
     *     
     */
    public void setInputMappingType(MessageMappingType value) {
        this.inputMappingType = value;
    }

    /**
     * Gets the value of the inputMappingOptions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputMappingOptions() {
        return inputMappingOptions;
    }

    /**
     * Sets the value of the inputMappingOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputMappingOptions(String value) {
        this.inputMappingOptions = value;
    }

    /**
     * Gets the value of the outputPipeline property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputPipeline() {
        return outputPipeline;
    }

    /**
     * Sets the value of the outputPipeline property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputPipeline(String value) {
        this.outputPipeline = value;
    }

    /**
     * Gets the value of the outputMappingType property.
     * 
     * @return
     *     possible object is
     *     {@link MessageMappingType }
     *     
     */
    public MessageMappingType getOutputMappingType() {
        return outputMappingType;
    }

    /**
     * Sets the value of the outputMappingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageMappingType }
     *     
     */
    public void setOutputMappingType(MessageMappingType value) {
        this.outputMappingType = value;
    }

    /**
     * Gets the value of the outputMappingOptions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputMappingOptions() {
        return outputMappingOptions;
    }

    /**
     * Sets the value of the outputMappingOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputMappingOptions(String value) {
        this.outputMappingOptions = value;
    }

}
