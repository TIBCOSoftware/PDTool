
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for updateDataSourceTypeCustomCapabilitiesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateDataSourceTypeCustomCapabilitiesResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="updated" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateDataSourceTypeCustomCapabilitiesResponse", propOrder = {
    "updated"
})
public class UpdateDataSourceTypeCustomCapabilitiesResponse
    extends BaseResponse
{

    protected boolean updated;

    /**
     * Gets the value of the updated property.
     * 
     */
    public boolean isUpdated() {
        return updated;
    }

    /**
     * Sets the value of the updated property.
     * 
     */
    public void setUpdated(boolean value) {
        this.updated = value;
    }

}
