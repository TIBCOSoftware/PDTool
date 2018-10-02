
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for portOperationProperty complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="portOperationProperty">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="operationName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operationStyle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operationSoapAction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operationMessageType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operationAckMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operationTimeout" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="operationPriority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="operationExpiry" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="operationDelieveryMode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "portOperationProperty", propOrder = {
    "operationName",
    "operationStyle",
    "operationSoapAction",
    "operationMessageType",
    "operationAckMode",
    "operationTimeout",
    "operationPriority",
    "operationExpiry",
    "operationDelieveryMode"
})
public class PortOperationProperty {

    @XmlElement(required = true)
    protected String operationName;
    protected String operationStyle;
    protected String operationSoapAction;
    protected String operationMessageType;
    protected String operationAckMode;
    protected Long operationTimeout;
    protected Integer operationPriority;
    protected Long operationExpiry;
    protected Integer operationDelieveryMode;

    /**
     * Gets the value of the operationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationName() {
        return operationName;
    }

    /**
     * Sets the value of the operationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationName(String value) {
        this.operationName = value;
    }

    /**
     * Gets the value of the operationStyle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationStyle() {
        return operationStyle;
    }

    /**
     * Sets the value of the operationStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationStyle(String value) {
        this.operationStyle = value;
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
     * Gets the value of the operationMessageType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationMessageType() {
        return operationMessageType;
    }

    /**
     * Sets the value of the operationMessageType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationMessageType(String value) {
        this.operationMessageType = value;
    }

    /**
     * Gets the value of the operationAckMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationAckMode() {
        return operationAckMode;
    }

    /**
     * Sets the value of the operationAckMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationAckMode(String value) {
        this.operationAckMode = value;
    }

    /**
     * Gets the value of the operationTimeout property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOperationTimeout() {
        return operationTimeout;
    }

    /**
     * Sets the value of the operationTimeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOperationTimeout(Long value) {
        this.operationTimeout = value;
    }

    /**
     * Gets the value of the operationPriority property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOperationPriority() {
        return operationPriority;
    }

    /**
     * Sets the value of the operationPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOperationPriority(Integer value) {
        this.operationPriority = value;
    }

    /**
     * Gets the value of the operationExpiry property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOperationExpiry() {
        return operationExpiry;
    }

    /**
     * Sets the value of the operationExpiry property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOperationExpiry(Long value) {
        this.operationExpiry = value;
    }

    /**
     * Gets the value of the operationDelieveryMode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOperationDelieveryMode() {
        return operationDelieveryMode;
    }

    /**
     * Sets the value of the operationDelieveryMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOperationDelieveryMode(Integer value) {
        this.operationDelieveryMode = value;
    }

}
