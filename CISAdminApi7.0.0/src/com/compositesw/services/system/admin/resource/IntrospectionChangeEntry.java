
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.MessageEntryList;
import com.compositesw.services.system.util.common.MessageSeverity;


/**
 * <p>Java class for introspectionChangeEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="introspectionChangeEntry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.compositesw.com/services/system/admin/resource}resourceType"/>
 *         &lt;element name="subtype" type="{http://www.compositesw.com/services/system/admin/resource}resourceSubType"/>
 *         &lt;element name="action" type="{http://www.compositesw.com/services/system/admin/resource}introspectionAction"/>
 *         &lt;element name="durationMs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="status" type="{http://www.compositesw.com/services/system/util/common}messageSeverity"/>
 *         &lt;element name="messages" type="{http://www.compositesw.com/services/system/util/common}messageEntryList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "introspectionChangeEntry", propOrder = {
    "path",
    "type",
    "subtype",
    "action",
    "durationMs",
    "status",
    "messages"
})
public class IntrospectionChangeEntry {

    @XmlElement(required = true)
    protected String path;
    @XmlElement(required = true)
    protected ResourceType type;
    @XmlElement(required = true)
    protected ResourceSubType subtype;
    @XmlElement(required = true)
    protected IntrospectionAction action;
    protected int durationMs;
    @XmlElement(required = true)
    protected MessageSeverity status;
    protected MessageEntryList messages;

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceType }
     *     
     */
    public ResourceType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceType }
     *     
     */
    public void setType(ResourceType value) {
        this.type = value;
    }

    /**
     * Gets the value of the subtype property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceSubType }
     *     
     */
    public ResourceSubType getSubtype() {
        return subtype;
    }

    /**
     * Sets the value of the subtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceSubType }
     *     
     */
    public void setSubtype(ResourceSubType value) {
        this.subtype = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link IntrospectionAction }
     *     
     */
    public IntrospectionAction getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntrospectionAction }
     *     
     */
    public void setAction(IntrospectionAction value) {
        this.action = value;
    }

    /**
     * Gets the value of the durationMs property.
     * 
     */
    public int getDurationMs() {
        return durationMs;
    }

    /**
     * Sets the value of the durationMs property.
     * 
     */
    public void setDurationMs(int value) {
        this.durationMs = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link MessageSeverity }
     *     
     */
    public MessageSeverity getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageSeverity }
     *     
     */
    public void setStatus(MessageSeverity value) {
        this.status = value;
    }

    /**
     * Gets the value of the messages property.
     * 
     * @return
     *     possible object is
     *     {@link MessageEntryList }
     *     
     */
    public MessageEntryList getMessages() {
        return messages;
    }

    /**
     * Sets the value of the messages property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageEntryList }
     *     
     */
    public void setMessages(MessageEntryList value) {
        this.messages = value;
    }

}
