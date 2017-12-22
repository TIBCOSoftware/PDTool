
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for destroyCustomDataSourceTypeResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="destroyCustomDataSourceTypeResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="destroyed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "destroyCustomDataSourceTypeResponse", propOrder = {
    "destroyed"
})
public class DestroyCustomDataSourceTypeResponse
    extends BaseResponse
{

    protected boolean destroyed;

    /**
     * Gets the value of the destroyed property.
     * 
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Sets the value of the destroyed property.
     * 
     */
    public void setDestroyed(boolean value) {
        this.destroyed = value;
    }

}
