
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;
import com.compositesw.services.system.util.common.MessageList;
import com.compositesw.services.system.util.common.OperationStatus;


/**
 * <p>Java class for testDataSourceConnectionResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="testDataSourceConnectionResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="status" type="{http://www.compositesw.com/services/system/util/common}operationStatus"/>
 *         &lt;element name="messages" type="{http://www.compositesw.com/services/system/util/common}messageList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "testDataSourceConnectionResponse", propOrder = {
    "status",
    "messages"
})
public class TestDataSourceConnectionResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected OperationStatus status;
    @XmlElement(required = true)
    protected MessageList messages;

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
     *     {@link MessageList }
     *     
     */
    public MessageList getMessages() {
        return messages;
    }

    /**
     * Sets the value of the messages property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageList }
     *     
     */
    public void setMessages(MessageList value) {
        this.messages = value;
    }

}
