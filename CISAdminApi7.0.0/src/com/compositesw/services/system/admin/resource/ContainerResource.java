
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for containerResource complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="containerResource">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}resource">
 *       &lt;sequence>
 *         &lt;element name="childCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="authMethods" type="{http://www.compositesw.com/services/system/admin/resource}authMethodList" minOccurs="0"/>
 *         &lt;element name="requireAllAuthMethods" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="transportSecurity" type="{http://www.compositesw.com/services/system/admin/resource}transportSecurityLevel" minOccurs="0"/>
 *         &lt;element name="bindingType" type="{http://www.compositesw.com/services/system/admin/resource}bindingType" minOccurs="0"/>
 *         &lt;element name="bindingProfileType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bindingProperties" type="{http://www.compositesw.com/services/system/admin/resource}portBindingProperties" minOccurs="0"/>
 *         &lt;element name="correlationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isConnectorGroup" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="connector" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="alternateURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="timeout" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inputPipeline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inputMappingType" type="{http://www.compositesw.com/services/system/admin/resource}messageMappingType" minOccurs="0"/>
 *         &lt;element name="inputMappingOptions" type="{http://www.compositesw.com/services/system/admin/resource}messageMappingOptionList" minOccurs="0"/>
 *         &lt;element name="outputPipeline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outputMappingType" type="{http://www.compositesw.com/services/system/admin/resource}messageMappingType" minOccurs="0"/>
 *         &lt;element name="outputMappingOptions" type="{http://www.compositesw.com/services/system/admin/resource}messageMappingOptionList" minOccurs="0"/>
 *         &lt;element name="implementation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "containerResource", propOrder = {
    "childCount",
    "authMethods",
    "requireAllAuthMethods",
    "transportSecurity",
    "bindingType",
    "bindingProfileType",
    "bindingProperties",
    "correlationType",
    "isConnectorGroup",
    "connector",
    "alternateURL",
    "timeout",
    "inputPipeline",
    "inputMappingType",
    "inputMappingOptions",
    "outputPipeline",
    "outputMappingType",
    "outputMappingOptions",
    "implementation"
})
@XmlSeeAlso({
    DataSourceResource.class
})
public class ContainerResource
    extends Resource
{

    protected Integer childCount;
    protected String authMethods;
    protected Boolean requireAllAuthMethods;
    protected TransportSecurityLevel transportSecurity;
    protected BindingType bindingType;
    protected String bindingProfileType;
    protected PortBindingProperties bindingProperties;
    protected String correlationType;
    protected Boolean isConnectorGroup;
    protected String connector;
    protected String alternateURL;
    protected String timeout;
    protected String inputPipeline;
    protected MessageMappingType inputMappingType;
    protected String inputMappingOptions;
    protected String outputPipeline;
    protected MessageMappingType outputMappingType;
    protected String outputMappingOptions;
    protected String implementation;

    /**
     * Gets the value of the childCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getChildCount() {
        return childCount;
    }

    /**
     * Sets the value of the childCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setChildCount(Integer value) {
        this.childCount = value;
    }

    /**
     * Gets the value of the authMethods property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthMethods() {
        return authMethods;
    }

    /**
     * Sets the value of the authMethods property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthMethods(String value) {
        this.authMethods = value;
    }

    /**
     * Gets the value of the requireAllAuthMethods property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRequireAllAuthMethods() {
        return requireAllAuthMethods;
    }

    /**
     * Sets the value of the requireAllAuthMethods property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRequireAllAuthMethods(Boolean value) {
        this.requireAllAuthMethods = value;
    }

    /**
     * Gets the value of the transportSecurity property.
     * 
     * @return
     *     possible object is
     *     {@link TransportSecurityLevel }
     *     
     */
    public TransportSecurityLevel getTransportSecurity() {
        return transportSecurity;
    }

    /**
     * Sets the value of the transportSecurity property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportSecurityLevel }
     *     
     */
    public void setTransportSecurity(TransportSecurityLevel value) {
        this.transportSecurity = value;
    }

    /**
     * Gets the value of the bindingType property.
     * 
     * @return
     *     possible object is
     *     {@link BindingType }
     *     
     */
    public BindingType getBindingType() {
        return bindingType;
    }

    /**
     * Sets the value of the bindingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BindingType }
     *     
     */
    public void setBindingType(BindingType value) {
        this.bindingType = value;
    }

    /**
     * Gets the value of the bindingProfileType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBindingProfileType() {
        return bindingProfileType;
    }

    /**
     * Sets the value of the bindingProfileType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBindingProfileType(String value) {
        this.bindingProfileType = value;
    }

    /**
     * Gets the value of the bindingProperties property.
     * 
     * @return
     *     possible object is
     *     {@link PortBindingProperties }
     *     
     */
    public PortBindingProperties getBindingProperties() {
        return bindingProperties;
    }

    /**
     * Sets the value of the bindingProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link PortBindingProperties }
     *     
     */
    public void setBindingProperties(PortBindingProperties value) {
        this.bindingProperties = value;
    }

    /**
     * Gets the value of the correlationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrelationType() {
        return correlationType;
    }

    /**
     * Sets the value of the correlationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrelationType(String value) {
        this.correlationType = value;
    }

    /**
     * Gets the value of the isConnectorGroup property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsConnectorGroup() {
        return isConnectorGroup;
    }

    /**
     * Sets the value of the isConnectorGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsConnectorGroup(Boolean value) {
        this.isConnectorGroup = value;
    }

    /**
     * Gets the value of the connector property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnector() {
        return connector;
    }

    /**
     * Sets the value of the connector property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnector(String value) {
        this.connector = value;
    }

    /**
     * Gets the value of the alternateURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlternateURL() {
        return alternateURL;
    }

    /**
     * Sets the value of the alternateURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlternateURL(String value) {
        this.alternateURL = value;
    }

    /**
     * Gets the value of the timeout property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeout() {
        return timeout;
    }

    /**
     * Sets the value of the timeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeout(String value) {
        this.timeout = value;
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
     * Gets the value of the implementation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImplementation() {
        return implementation;
    }

    /**
     * Sets the value of the implementation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImplementation(String value) {
        this.implementation = value;
    }

}
