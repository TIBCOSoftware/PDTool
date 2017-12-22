
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeList;


/**
 * <p>Java class for updateBasePortRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateBasePortRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}pathDetailRequest">
 *       &lt;sequence>
 *         &lt;element name="authMethods" type="{http://www.compositesw.com/services/system/admin/resource}authMethodList" minOccurs="0"/>
 *         &lt;element name="requireAllAuthMethods" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="transportSecurity" type="{http://www.compositesw.com/services/system/admin/resource}transportSecurityLevel" minOccurs="0"/>
 *         &lt;element name="bindingType" type="{http://www.compositesw.com/services/system/admin/resource}bindingType" minOccurs="0"/>
 *         &lt;element name="bindingProfileType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bindingProperties" type="{http://www.compositesw.com/services/system/admin/resource}portBindingProperties" minOccurs="0"/>
 *         &lt;element name="correlationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isConnectorGroup" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="connector" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attributes" type="{http://www.compositesw.com/services/system/util/common}attributeList" minOccurs="0"/>
 *         &lt;element name="annotation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateBasePortRequest", propOrder = {
    "authMethods",
    "requireAllAuthMethods",
    "transportSecurity",
    "bindingType",
    "bindingProfileType",
    "bindingProperties",
    "correlationType",
    "isConnectorGroup",
    "connector",
    "attributes",
    "annotation"
})
@XmlSeeAlso({
    UpdateDataServicePortRequest.class,
    UpdateDataSourcePortRequest.class
})
public class UpdateBasePortRequest
    extends PathDetailRequest
{

    protected String authMethods;
    protected Boolean requireAllAuthMethods;
    protected TransportSecurityLevel transportSecurity;
    protected BindingType bindingType;
    protected String bindingProfileType;
    protected PortBindingProperties bindingProperties;
    protected String correlationType;
    protected Boolean isConnectorGroup;
    protected String connector;
    protected AttributeList attributes;
    protected String annotation;

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
     * Gets the value of the attributes property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeList }
     *     
     */
    public AttributeList getAttributes() {
        return attributes;
    }

    /**
     * Sets the value of the attributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeList }
     *     
     */
    public void setAttributes(AttributeList value) {
        this.attributes = value;
    }

    /**
     * Gets the value of the annotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * Sets the value of the annotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnotation(String value) {
        this.annotation = value;
    }

}
