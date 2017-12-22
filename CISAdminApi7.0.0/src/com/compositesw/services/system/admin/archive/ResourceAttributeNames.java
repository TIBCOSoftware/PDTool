
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.admin.resource.PathTypePair;
import com.compositesw.services.system.util.common.NameList;


/**
 * <p>Java class for resourceAttributeNames complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resourceAttributeNames">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}pathTypePair">
 *       &lt;sequence>
 *         &lt;element name="attributeNames" type="{http://www.compositesw.com/services/system/util/common}nameList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resourceAttributeNames", propOrder = {
    "attributeNames"
})
public class ResourceAttributeNames
    extends PathTypePair
{

    @XmlElement(required = true)
    protected NameList attributeNames;

    /**
     * Gets the value of the attributeNames property.
     * 
     * @return
     *     possible object is
     *     {@link NameList }
     *     
     */
    public NameList getAttributeNames() {
        return attributeNames;
    }

    /**
     * Sets the value of the attributeNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameList }
     *     
     */
    public void setAttributeNames(NameList value) {
        this.attributeNames = value;
    }

}
