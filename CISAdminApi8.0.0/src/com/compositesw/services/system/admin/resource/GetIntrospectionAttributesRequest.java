
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for getIntrospectionAttributesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getIntrospectionAttributesRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dsPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dsType" type="{http://www.compositesw.com/services/system/admin/resource}resourceType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getIntrospectionAttributesRequest", propOrder = {
    "path",
    "dsPath",
    "dsType"
})
public class GetIntrospectionAttributesRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected String path;
    @XmlElement(required = true)
    protected String dsPath;
    @XmlElement(required = true)
    protected ResourceType dsType;

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Gets the value of the dsPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsPath() {
        return dsPath;
    }

    /**
     * Sets the value of the dsPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsPath(String value) {
        this.dsPath = value;
    }

    /**
     * Gets the value of the dsType property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceType }
     *     
     */
    public ResourceType getDsType() {
        return dsType;
    }

    /**
     * Sets the value of the dsType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceType }
     *     
     */
    public void setDsType(ResourceType value) {
        this.dsType = value;
    }

}
