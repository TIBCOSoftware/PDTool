
package com.compositesw.services.system.admin.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeDefList;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for attributeDefsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attributeDefsResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="attributeDefs" type="{http://www.compositesw.com/services/system/util/common}attributeDefList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attributeDefsResponse", propOrder = {
    "attributeDefs"
})
@XmlSeeAlso({
    GetServerAttributeDefsResponse.class,
    GetServerAttributeDefChildrenResponse.class
})
public class AttributeDefsResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected AttributeDefList attributeDefs;

    /**
     * Gets the value of the attributeDefs property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeDefList }
     *     
     */
    public AttributeDefList getAttributeDefs() {
        return attributeDefs;
    }

    /**
     * Sets the value of the attributeDefs property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeDefList }
     *     
     */
    public void setAttributeDefs(AttributeDefList value) {
        this.attributeDefs = value;
    }

}
