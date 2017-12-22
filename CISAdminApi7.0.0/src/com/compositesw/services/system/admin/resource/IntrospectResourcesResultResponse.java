
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.ServerTaskResultResponse;


/**
 * <p>Java class for introspectResourcesResultResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="introspectResourcesResultResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}serverTaskResultResponse">
 *       &lt;sequence>
 *         &lt;element name="status" type="{http://www.compositesw.com/services/system/admin/resource}introspectionStatus"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "introspectResourcesResultResponse", propOrder = {
    "status"
})
public class IntrospectResourcesResultResponse
    extends ServerTaskResultResponse
{

    @XmlElement(required = true)
    protected IntrospectionStatus status;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link IntrospectionStatus }
     *     
     */
    public IntrospectionStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntrospectionStatus }
     *     
     */
    public void setStatus(IntrospectionStatus value) {
        this.status = value;
    }

}
