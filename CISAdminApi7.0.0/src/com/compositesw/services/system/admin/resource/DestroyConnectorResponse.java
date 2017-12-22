
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for destroyConnectorResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="destroyConnectorResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="destroyedAll" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "destroyConnectorResponse", propOrder = {
    "destroyedAll"
})
public class DestroyConnectorResponse
    extends BaseResponse
{

    protected boolean destroyedAll;

    /**
     * Gets the value of the destroyedAll property.
     * 
     */
    public boolean isDestroyedAll() {
        return destroyedAll;
    }

    /**
     * Sets the value of the destroyedAll property.
     * 
     */
    public void setDestroyedAll(boolean value) {
        this.destroyedAll = value;
    }

}
