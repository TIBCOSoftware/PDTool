
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeList;
import com.compositesw.services.system.util.common.ServerTaskRequest;


/**
 * <p>Java class for getIntrospectableResourceIdsTaskRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getIntrospectableResourceIdsTaskRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}serverTaskRequest">
 *       &lt;sequence>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="attributes" type="{http://www.compositesw.com/services/system/util/common}attributeList" minOccurs="0"/>
 *         &lt;element name="dsContainerId" type="{http://www.compositesw.com/services/system/admin/resource}pathTypeSubtype" minOccurs="0"/>
 *         &lt;element name="recurse" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getIntrospectableResourceIdsTaskRequest", propOrder = {
    "path",
    "attributes",
    "dsContainerId",
    "recurse"
})
public class GetIntrospectableResourceIdsTaskRequest
    extends ServerTaskRequest
{

    @XmlElement(required = true)
    protected String path;
    protected AttributeList attributes;
    protected PathTypeSubtype dsContainerId;
    protected Boolean recurse;

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
     * Gets the value of the attributes property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeList }
     *     
     */
    public AttributeList getAttributes() {
        return attributes;
    }

    /**
     * Sets the value of the attributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeList }
     *     
     */
    public void setAttributes(AttributeList value) {
        this.attributes = value;
    }

    /**
     * Gets the value of the dsContainerId property.
     * 
     * @return
     *     possible object is
     *     {@link PathTypeSubtype }
     *     
     */
    public PathTypeSubtype getDsContainerId() {
        return dsContainerId;
    }

    /**
     * Sets the value of the dsContainerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathTypeSubtype }
     *     
     */
    public void setDsContainerId(PathTypeSubtype value) {
        this.dsContainerId = value;
    }

    /**
     * Gets the value of the recurse property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRecurse() {
        return recurse;
    }

    /**
     * Sets the value of the recurse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRecurse(Boolean value) {
        this.recurse = value;
    }

}
