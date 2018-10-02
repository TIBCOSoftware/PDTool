
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getIntrospectionAttributeDefsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getIntrospectionAttributeDefsResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="subTypeAttributeDefs" type="{http://www.compositesw.com/services/system/admin/resource}introspectionAttributeDefsList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getIntrospectionAttributeDefsResponse", propOrder = {
    "subTypeAttributeDefs"
})
public class GetIntrospectionAttributeDefsResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected IntrospectionAttributeDefsList subTypeAttributeDefs;

    /**
     * Gets the value of the subTypeAttributeDefs property.
     * 
     * @return
     *     possible object is
     *     {@link IntrospectionAttributeDefsList }
     *     
     */
    public IntrospectionAttributeDefsList getSubTypeAttributeDefs() {
        return subTypeAttributeDefs;
    }

    /**
     * Sets the value of the subTypeAttributeDefs property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntrospectionAttributeDefsList }
     *     
     */
    public void setSubTypeAttributeDefs(IntrospectionAttributeDefsList value) {
        this.subTypeAttributeDefs = value;
    }

}
