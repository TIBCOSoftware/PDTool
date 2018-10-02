
package com.compositesw.services.system.admin.resource;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeList;


/**
 * <p>Java class for updateTriggerRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateTriggerRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}pathDetailRequest">
 *       &lt;sequence>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="conditionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="conditionSchedule" type="{http://www.compositesw.com/services/system/admin/resource}schedule" minOccurs="0"/>
 *         &lt;element name="conditionAttributes" type="{http://www.compositesw.com/services/system/util/common}attributeList" minOccurs="0"/>
 *         &lt;element name="actionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="actionAttributes" type="{http://www.compositesw.com/services/system/util/common}attributeList" minOccurs="0"/>
 *         &lt;element name="maxEventsQueued" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="annotation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attributes" type="{http://www.compositesw.com/services/system/util/common}attributeList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateTriggerRequest", propOrder = {
    "enabled",
    "conditionType",
    "conditionSchedule",
    "conditionAttributes",
    "actionType",
    "actionAttributes",
    "maxEventsQueued",
    "annotation",
    "attributes"
})
public class UpdateTriggerRequest
    extends PathDetailRequest
{

    protected Boolean enabled;
    protected String conditionType;
    protected Schedule conditionSchedule;
    protected AttributeList conditionAttributes;
    protected String actionType;
    protected AttributeList actionAttributes;
    protected BigInteger maxEventsQueued;
    protected String annotation;
    protected AttributeList attributes;

    /**
     * Gets the value of the enabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the conditionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditionType() {
        return conditionType;
    }

    /**
     * Sets the value of the conditionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditionType(String value) {
        this.conditionType = value;
    }

    /**
     * Gets the value of the conditionSchedule property.
     * 
     * @return
     *     possible object is
     *     {@link Schedule }
     *     
     */
    public Schedule getConditionSchedule() {
        return conditionSchedule;
    }

    /**
     * Sets the value of the conditionSchedule property.
     * 
     * @param value
     *     allowed object is
     *     {@link Schedule }
     *     
     */
    public void setConditionSchedule(Schedule value) {
        this.conditionSchedule = value;
    }

    /**
     * Gets the value of the conditionAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeList }
     *     
     */
    public AttributeList getConditionAttributes() {
        return conditionAttributes;
    }

    /**
     * Sets the value of the conditionAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeList }
     *     
     */
    public void setConditionAttributes(AttributeList value) {
        this.conditionAttributes = value;
    }

    /**
     * Gets the value of the actionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * Sets the value of the actionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionType(String value) {
        this.actionType = value;
    }

    /**
     * Gets the value of the actionAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeList }
     *     
     */
    public AttributeList getActionAttributes() {
        return actionAttributes;
    }

    /**
     * Sets the value of the actionAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeList }
     *     
     */
    public void setActionAttributes(AttributeList value) {
        this.actionAttributes = value;
    }

    /**
     * Gets the value of the maxEventsQueued property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxEventsQueued() {
        return maxEventsQueued;
    }

    /**
     * Sets the value of the maxEventsQueued property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxEventsQueued(BigInteger value) {
        this.maxEventsQueued = value;
    }

    /**
     * Gets the value of the annotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * Sets the value of the annotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnotation(String value) {
        this.annotation = value;
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
