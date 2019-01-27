
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeList;


/**
 * <p>Java class for introspectionPlanEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="introspectionPlanEntry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resourceId" type="{http://www.compositesw.com/services/system/admin/resource}pathTypeSubtype"/>
 *         &lt;element name="action" type="{http://www.compositesw.com/services/system/admin/resource}introspectionPlanAction"/>
 *         &lt;element name="attributes" type="{http://www.compositesw.com/services/system/util/common}attributeList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "introspectionPlanEntry", propOrder = {
    "resourceId",
    "action",
    "attributes"
})
public class IntrospectionPlanEntry {

    @XmlElement(required = true)
    protected PathTypeSubtype resourceId;
    @XmlElement(required = true)
    protected IntrospectionPlanAction action;
    protected AttributeList attributes;

    /**
     * Gets the value of the resourceId property.
     * 
     * @return
     *     possible object is
     *     {@link PathTypeSubtype }
     *     
     */
    public PathTypeSubtype getResourceId() {
        return resourceId;
    }

    /**
     * Sets the value of the resourceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathTypeSubtype }
     *     
     */
    public void setResourceId(PathTypeSubtype value) {
        this.resourceId = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link IntrospectionPlanAction }
     *     
     */
    public IntrospectionPlanAction getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntrospectionPlanAction }
     *     
     */
    public void setAction(IntrospectionPlanAction value) {
        this.action = value;
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

}
