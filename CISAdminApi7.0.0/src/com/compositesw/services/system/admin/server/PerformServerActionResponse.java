
package com.compositesw.services.system.admin.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;
import com.compositesw.services.system.util.common.MessageEntryList;
import com.compositesw.services.system.util.common.OperationStatus;


/**
 * <p>Java class for performServerActionResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="performServerActionResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="status" type="{http://www.compositesw.com/services/system/util/common}operationStatus"/>
 *         &lt;element name="messages" type="{http://www.compositesw.com/services/system/util/common}messageEntryList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "performServerActionResponse", propOrder = {
    "status",
    "messages"
})
public class PerformServerActionResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected OperationStatus status;
    @XmlElement(required = true)
    protected MessageEntryList messages;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link OperationStatus }
     *     
     */
    public OperationStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationStatus }
     *     
     */
    public void setStatus(OperationStatus value) {
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
