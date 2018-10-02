
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for jmsPortBindingProperties complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="jmsPortBindingProperties">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}portBindingProperties">
 *       &lt;sequence>
 *         &lt;element name="bindingPropertyDestinationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bindingPropertyReplyAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bindingPropertyTargetAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bindingPropertyFaultAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bindingPropertyMessageType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bindingPropertyAckMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bindingPropertyPriority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="bindingPropertyExpiry" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="bindingPropertyDelieveryMode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="operations" type="{http://www.compositesw.com/services/system/admin/resource}portOperationPropertyList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "jmsPortBindingProperties", propOrder = {
    "bindingPropertyDestinationType",
    "bindingPropertyReplyAddress",
    "bindingPropertyTargetAddress",
    "bindingPropertyFaultAddress",
    "bindingPropertyMessageType",
    "bindingPropertyAckMode",
    "bindingPropertyPriority",
    "bindingPropertyExpiry",
    "bindingPropertyDelieveryMode",
    "operations"
})
public class JmsPortBindingProperties
    extends PortBindingProperties
{

    protected String bindingPropertyDestinationType;
    protected String bindingPropertyReplyAddress;
    protected String bindingPropertyTargetAddress;
    protected String bindingPropertyFaultAddress;
    protected String bindingPropertyMessageType;
    protected String bindingPropertyAckMode;
    protected Integer bindingPropertyPriority;
    protected Long bindingPropertyExpiry;
    protected Integer bindingPropertyDelieveryMode;
    protected PortOperationPropertyList operations;

    /**
     * Gets the value of the bindingPropertyDestinationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBindingPropertyDestinationType() {
        return bindingPropertyDestinationType;
    }

    /**
     * Sets the value of the bindingPropertyDestinationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBindingPropertyDestinationType(String value) {
        this.bindingPropertyDestinationType = value;
    }

    /**
     * Gets the value of the bindingPropertyReplyAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBindingPropertyReplyAddress() {
        return bindingPropertyReplyAddress;
    }

    /**
     * Sets the value of the bindingPropertyReplyAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBindingPropertyReplyAddress(String value) {
        this.bindingPropertyReplyAddress = value;
    }

    /**
     * Gets the value of the bindingPropertyTargetAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBindingPropertyTargetAddress() {
        return bindingPropertyTargetAddress;
    }

    /**
     * Sets the value of the bindingPropertyTargetAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBindingPropertyTargetAddress(String value) {
        this.bindingPropertyTargetAddress = value;
    }

    /**
     * Gets the value of the bindingPropertyFaultAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBindingPropertyFaultAddress() {
        return bindingPropertyFaultAddress;
    }

    /**
     * Sets the value of the bindingPropertyFaultAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBindingPropertyFaultAddress(String value) {
        this.bindingPropertyFaultAddress = value;
    }

    /**
     * Gets the value of the bindingPropertyMessageType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBindingPropertyMessageType() {
        return bindingPropertyMessageType;
    }

    /**
     * Sets the value of the bindingPropertyMessageType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBindingPropertyMessageType(String value) {
        this.bindingPropertyMessageType = value;
    }

    /**
     * Gets the value of the bindingPropertyAckMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBindingPropertyAckMode() {
        return bindingPropertyAckMode;
    }

    /**
     * Sets the value of the bindingPropertyAckMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBindingPropertyAckMode(String value) {
        this.bindingPropertyAckMode = value;
    }

    /**
     * Gets the value of the bindingPropertyPriority property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBindingPropertyPriority() {
        return bindingPropertyPriority;
    }

    /**
     * Sets the value of the bindingPropertyPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBindingPropertyPriority(Integer value) {
        this.bindingPropertyPriority = value;
    }

    /**
     * Gets the value of the bindingPropertyExpiry property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBindingPropertyExpiry() {
        return bindingPropertyExpiry;
    }

    /**
     * Sets the value of the bindingPropertyExpiry property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBindingPropertyExpiry(Long value) {
        this.bindingPropertyExpiry = value;
    }

    /**
     * Gets the value of the bindingPropertyDelieveryMode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBindingPropertyDelieveryMode() {
        return bindingPropertyDelieveryMode;
    }

    /**
     * Sets the value of the bindingPropertyDelieveryMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBindingPropertyDelieveryMode(Integer value) {
        this.bindingPropertyDelieveryMode = value;
    }

    /**
     * Gets the value of the operations property.
     * 
     * @return
     *     possible object is
     *     {@link PortOperationPropertyList }
     *     
     */
    public PortOperationPropertyList getOperations() {
        return operations;
    }

    /**
     * Sets the value of the operations property.
     * 
     * @param value
     *     allowed object is
     *     {@link PortOperationPropertyList }
     *     
     */
    public void setOperations(PortOperationPropertyList value) {
        this.operations = value;
    }

}
