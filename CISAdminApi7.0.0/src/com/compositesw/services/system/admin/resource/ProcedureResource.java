
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for procedureResource complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="procedureResource">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}resource">
 *       &lt;sequence>
 *         &lt;element name="parameters" type="{http://www.compositesw.com/services/system/admin/resource}parameterList" minOccurs="0"/>
 *         &lt;element name="explicitlyDesigned" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="externalDataSourcePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="externalSqlText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="javaClassName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operationSoapStyle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operationSoapAction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operationSourceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="usePortMappings" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="usePortPipelines" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="inputPipeline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inputMappingType" type="{http://www.compositesw.com/services/system/admin/resource}messageMappingType" minOccurs="0"/>
 *         &lt;element name="inputMappingOptions" type="{http://www.compositesw.com/services/system/admin/resource}messageMappingOptionList" minOccurs="0"/>
 *         &lt;element name="outputPipeline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outputMappingType" type="{http://www.compositesw.com/services/system/admin/resource}messageMappingType" minOccurs="0"/>
 *         &lt;element name="outputMappingOptions" type="{http://www.compositesw.com/services/system/admin/resource}messageMappingOptionList" minOccurs="0"/>
 *         &lt;element name="scriptText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scriptModel" type="{http://www.compositesw.com/services/system/admin/resource}model" minOccurs="0"/>
 *         &lt;element name="transformModel" type="{http://www.compositesw.com/services/system/admin/resource}model" minOccurs="0"/>
 *         &lt;element name="transformText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transformSourcePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transformSourceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="streamText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="streamModel" type="{http://www.compositesw.com/services/system/admin/resource}model" minOccurs="0"/>
 *         &lt;element name="xqueryText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xqueryModel" type="{http://www.compositesw.com/services/system/admin/resource}model" minOccurs="0"/>
 *         &lt;element name="xsltText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xsltModel" type="{http://www.compositesw.com/services/system/admin/resource}model" minOccurs="0"/>
 *         &lt;element name="isFunction" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "procedureResource", propOrder = {
    "parameters",
    "explicitlyDesigned",
    "externalDataSourcePath",
    "externalSqlText",
    "javaClassName",
    "operationSoapStyle",
    "operationSoapAction",
    "operationSourceName",
    "usePortMappings",
    "usePortPipelines",
    "inputPipeline",
    "inputMappingType",
    "inputMappingOptions",
    "outputPipeline",
    "outputMappingType",
    "outputMappingOptions",
    "scriptText",
    "scriptModel",
    "transformModel",
    "transformText",
    "transformSourcePath",
    "transformSourceType",
    "streamText",
    "streamModel",
    "xqueryText",
    "xqueryModel",
    "xsltText",
    "xsltModel",
    "isFunction"
})
public class ProcedureResource
    extends Resource
{

    protected ParameterList parameters;
    protected Boolean explicitlyDesigned;
    protected String externalDataSourcePath;
    protected String externalSqlText;
    protected String javaClassName;
    protected String operationSoapStyle;
    protected String operationSoapAction;
    protected String operationSourceName;
    protected Boolean usePortMappings;
    protected Boolean usePortPipelines;
    protected String inputPipeline;
    protected MessageMappingType inputMappingType;
    protected String inputMappingOptions;
    protected String outputPipeline;
    protected MessageMappingType outputMappingType;
    protected String outputMappingOptions;
    protected String scriptText;
    protected Model scriptModel;
    protected Model transformModel;
    protected String transformText;
    protected String transformSourcePath;
    protected String transformSourceType;
    protected String streamText;
    protected Model streamModel;
    protected String xqueryText;
    protected Model xqueryModel;
    protected String xsltText;
    protected Model xsltModel;
    protected Boolean isFunction;

    /**
     * Gets the value of the parameters property.
     * 
     * @return
     *     possible object is
     *     {@link ParameterList }
     *     
     */
    public ParameterList getParameters() {
        return parameters;
    }

    /**
     * Sets the value of the parameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParameterList }
     *     
     */
    public void setParameters(ParameterList value) {
        this.parameters = value;
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
     * Gets the value of the externalDataSourcePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalDataSourcePath() {
        return externalDataSourcePath;
    }

    /**
     * Sets the value of the externalDataSourcePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalDataSourcePath(String value) {
        this.externalDataSourcePath = value;
    }

    /**
     * Gets the value of the externalSqlText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalSqlText() {
        return externalSqlText;
    }

    /**
     * Sets the value of the externalSqlText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalSqlText(String value) {
        this.externalSqlText = value;
    }

    /**
     * Gets the value of the javaClassName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJavaClassName() {
        return javaClassName;
    }

    /**
     * Sets the value of the javaClassName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJavaClassName(String value) {
        this.javaClassName = value;
    }

    /**
     * Gets the value of the operationSoapStyle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationSoapStyle() {
        return operationSoapStyle;
    }

    /**
     * Sets the value of the operationSoapStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationSoapStyle(String value) {
        this.operationSoapStyle = value;
    }

    /**
     * Gets the value of the operationSoapAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationSoapAction() {
        return operationSoapAction;
    }

    /**
     * Sets the value of the operationSoapAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationSoapAction(String value) {
        this.operationSoapAction = value;
    }

    /**
     * Gets the value of the operationSourceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationSourceName() {
        return operationSourceName;
    }

    /**
     * Sets the value of the operationSourceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationSourceName(String value) {
        this.operationSourceName = value;
    }

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
     * Gets the value of the usePortPipelines property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUsePortPipelines() {
        return usePortPipelines;
    }

    /**
     * Sets the value of the usePortPipelines property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUsePortPipelines(Boolean value) {
        this.usePortPipelines = value;
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

    /**
     * Gets the value of the scriptText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScriptText() {
        return scriptText;
    }

    /**
     * Sets the value of the scriptText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScriptText(String value) {
        this.scriptText = value;
    }

    /**
     * Gets the value of the scriptModel property.
     * 
     * @return
     *     possible object is
     *     {@link Model }
     *     
     */
    public Model getScriptModel() {
        return scriptModel;
    }

    /**
     * Sets the value of the scriptModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Model }
     *     
     */
    public void setScriptModel(Model value) {
        this.scriptModel = value;
    }

    /**
     * Gets the value of the transformModel property.
     * 
     * @return
     *     possible object is
     *     {@link Model }
     *     
     */
    public Model getTransformModel() {
        return transformModel;
    }

    /**
     * Sets the value of the transformModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Model }
     *     
     */
    public void setTransformModel(Model value) {
        this.transformModel = value;
    }

    /**
     * Gets the value of the transformText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransformText() {
        return transformText;
    }

    /**
     * Sets the value of the transformText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransformText(String value) {
        this.transformText = value;
    }

    /**
     * Gets the value of the transformSourcePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransformSourcePath() {
        return transformSourcePath;
    }

    /**
     * Sets the value of the transformSourcePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransformSourcePath(String value) {
        this.transformSourcePath = value;
    }

    /**
     * Gets the value of the transformSourceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransformSourceType() {
        return transformSourceType;
    }

    /**
     * Sets the value of the transformSourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransformSourceType(String value) {
        this.transformSourceType = value;
    }

    /**
     * Gets the value of the streamText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreamText() {
        return streamText;
    }

    /**
     * Sets the value of the streamText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreamText(String value) {
        this.streamText = value;
    }

    /**
     * Gets the value of the streamModel property.
     * 
     * @return
     *     possible object is
     *     {@link Model }
     *     
     */
    public Model getStreamModel() {
        return streamModel;
    }

    /**
     * Sets the value of the streamModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Model }
     *     
     */
    public void setStreamModel(Model value) {
        this.streamModel = value;
    }

    /**
     * Gets the value of the xqueryText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXqueryText() {
        return xqueryText;
    }

    /**
     * Sets the value of the xqueryText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXqueryText(String value) {
        this.xqueryText = value;
    }

    /**
     * Gets the value of the xqueryModel property.
     * 
     * @return
     *     possible object is
     *     {@link Model }
     *     
     */
    public Model getXqueryModel() {
        return xqueryModel;
    }

    /**
     * Sets the value of the xqueryModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Model }
     *     
     */
    public void setXqueryModel(Model value) {
        this.xqueryModel = value;
    }

    /**
     * Gets the value of the xsltText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXsltText() {
        return xsltText;
    }

    /**
     * Sets the value of the xsltText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXsltText(String value) {
        this.xsltText = value;
    }

    /**
     * Gets the value of the xsltModel property.
     * 
     * @return
     *     possible object is
     *     {@link Model }
     *     
     */
    public Model getXsltModel() {
        return xsltModel;
    }

    /**
     * Sets the value of the xsltModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Model }
     *     
     */
    public void setXsltModel(Model value) {
        this.xsltModel = value;
    }

    /**
     * Gets the value of the isFunction property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsFunction() {
        return isFunction;
    }

    /**
     * Sets the value of the isFunction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsFunction(Boolean value) {
        this.isFunction = value;
    }

}
