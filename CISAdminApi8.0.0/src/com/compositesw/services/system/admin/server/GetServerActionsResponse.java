
package com.compositesw.services.system.admin.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getServerActionsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getServerActionsResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="actions" type="{http://www.compositesw.com/services/system/admin/server}serverActionList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getServerActionsResponse", propOrder = {
    "actions"
})
public class GetServerActionsResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected ServerActionList actions;

    /**
     * Gets the value of the actions property.
     * 
     * @return
     *     possible object is
     *     {@link ServerActionList }
     *     
     */
    public ServerActionList getActions() {
        return actions;
    }

    /**
     * Sets the value of the actions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServerActionList }
     *     
     */
    public void setActions(ServerActionList value) {
        this.actions = value;
    }

}
