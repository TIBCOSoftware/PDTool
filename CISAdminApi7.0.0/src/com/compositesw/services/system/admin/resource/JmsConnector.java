
package com.compositesw.services.system.admin.resource;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeList;


/**
 * <p>Java class for jmsConnector complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="jmsConnector">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}connector">
 *       &lt;sequence>
 *         &lt;element name="useJNDI" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="jmsConnectionUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jmsUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jmsPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jmsClientID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jmsProperties" type="{http://www.compositesw.com/services/system/util/common}attributeList" minOccurs="0"/>
 *         &lt;element name="queueConnectionFactory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="topicConnectionFactory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jndiContextFactory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jndiProviderUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jndiUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jndiPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jndiProperties" type="{http://www.compositesw.com/services/system/util/common}attributeList" minOccurs="0"/>
 *         &lt;element name="minPool" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="maxPool" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="poolTimeout" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "jmsConnector", propOrder = {
    "useJNDI",
    "jmsConnectionUrl",
    "jmsUser",
    "jmsPassword",
    "jmsClientID",
    "jmsProperties",
    "queueConnectionFactory",
    "topicConnectionFactory",
    "jndiContextFactory",
    "jndiProviderUrl",
    "jndiUser",
    "jndiPassword",
    "jndiProperties",
    "minPool",
    "maxPool",
    "poolTimeout"
})
public class JmsConnector
    extends Connector
{

    protected Boolean useJNDI;
    protected String jmsConnectionUrl;
    protected String jmsUser;
    protected String jmsPassword;
    protected String jmsClientID;
    protected AttributeList jmsProperties;
    protected String queueConnectionFactory;
    protected String topicConnectionFactory;
    protected String jndiContextFactory;
    protected String jndiProviderUrl;
    protected String jndiUser;
    protected String jndiPassword;
    protected AttributeList jndiProperties;
    protected BigInteger minPool;
    protected BigInteger maxPool;
    protected BigInteger poolTimeout;

    /**
     * Gets the value of the useJNDI property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseJNDI() {
        return useJNDI;
    }

    /**
     * Sets the value of the useJNDI property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseJNDI(Boolean value) {
        this.useJNDI = value;
    }

    /**
     * Gets the value of the jmsConnectionUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJmsConnectionUrl() {
        return jmsConnectionUrl;
    }

    /**
     * Sets the value of the jmsConnectionUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJmsConnectionUrl(String value) {
        this.jmsConnectionUrl = value;
    }

    /**
     * Gets the value of the jmsUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJmsUser() {
        return jmsUser;
    }

    /**
     * Sets the value of the jmsUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJmsUser(String value) {
        this.jmsUser = value;
    }

    /**
     * Gets the value of the jmsPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJmsPassword() {
        return jmsPassword;
    }

    /**
     * Sets the value of the jmsPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJmsPassword(String value) {
        this.jmsPassword = value;
    }

    /**
     * Gets the value of the jmsClientID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJmsClientID() {
        return jmsClientID;
    }

    /**
     * Sets the value of the jmsClientID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJmsClientID(String value) {
        this.jmsClientID = value;
    }

    /**
     * Gets the value of the jmsProperties property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeList }
     *     
     */
    public AttributeList getJmsProperties() {
        return jmsProperties;
    }

    /**
     * Sets the value of the jmsProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeList }
     *     
     */
    public void setJmsProperties(AttributeList value) {
        this.jmsProperties = value;
    }

    /**
     * Gets the value of the queueConnectionFactory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueueConnectionFactory() {
        return queueConnectionFactory;
    }

    /**
     * Sets the value of the queueConnectionFactory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueueConnectionFactory(String value) {
        this.queueConnectionFactory = value;
    }

    /**
     * Gets the value of the topicConnectionFactory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTopicConnectionFactory() {
        return topicConnectionFactory;
    }

    /**
     * Sets the value of the topicConnectionFactory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTopicConnectionFactory(String value) {
        this.topicConnectionFactory = value;
    }

    /**
     * Gets the value of the jndiContextFactory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJndiContextFactory() {
        return jndiContextFactory;
    }

    /**
     * Sets the value of the jndiContextFactory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJndiContextFactory(String value) {
        this.jndiContextFactory = value;
    }

    /**
     * Gets the value of the jndiProviderUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJndiProviderUrl() {
        return jndiProviderUrl;
    }

    /**
     * Sets the value of the jndiProviderUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJndiProviderUrl(String value) {
        this.jndiProviderUrl = value;
    }

    /**
     * Gets the value of the jndiUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJndiUser() {
        return jndiUser;
    }

    /**
     * Sets the value of the jndiUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJndiUser(String value) {
        this.jndiUser = value;
    }

    /**
     * Gets the value of the jndiPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJndiPassword() {
        return jndiPassword;
    }

    /**
     * Sets the value of the jndiPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJndiPassword(String value) {
        this.jndiPassword = value;
    }

    /**
     * Gets the value of the jndiProperties property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeList }
     *     
     */
    public AttributeList getJndiProperties() {
        return jndiProperties;
    }

    /**
     * Sets the value of the jndiProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeList }
     *     
     */
    public void setJndiProperties(AttributeList value) {
        this.jndiProperties = value;
    }

    /**
     * Gets the value of the minPool property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMinPool() {
        return minPool;
    }

    /**
     * Sets the value of the minPool property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMinPool(BigInteger value) {
        this.minPool = value;
    }

    /**
     * Gets the value of the maxPool property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxPool() {
        return maxPool;
    }

    /**
     * Sets the value of the maxPool property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxPool(BigInteger value) {
        this.maxPool = value;
    }

    /**
     * Gets the value of the poolTimeout property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPoolTimeout() {
        return poolTimeout;
    }

    /**
     * Sets the value of the poolTimeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPoolTimeout(BigInteger value) {
        this.poolTimeout = value;
    }

}
