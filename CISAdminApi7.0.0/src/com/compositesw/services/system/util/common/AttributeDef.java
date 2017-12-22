
package com.compositesw.services.system.util.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attributeDef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attributeDef">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.compositesw.com/services/system/util/common}attributeType"/>
 *         &lt;element name="updateRule" type="{http://www.compositesw.com/services/system/util/common}attributeUpdateRule"/>
 *         &lt;element name="annotation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="required" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="defaultValue" type="{http://www.compositesw.com/services/system/util/common}attributeSimpleValue" minOccurs="0"/>
 *         &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="minValue" type="{http://www.compositesw.com/services/system/util/common}attributeSimpleValue" minOccurs="0"/>
 *         &lt;element name="maxValue" type="{http://www.compositesw.com/services/system/util/common}attributeSimpleValue" minOccurs="0"/>
 *         &lt;element name="allowedValues" type="{http://www.compositesw.com/services/system/util/common}attributeSimpleValueList" minOccurs="0"/>
 *         &lt;element name="suggestedValues" type="{http://www.compositesw.com/services/system/util/common}attributeSimpleValueList" minOccurs="0"/>
 *         &lt;element name="condition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unitName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="visible" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="editorHint" type="{http://www.compositesw.com/services/system/util/common}attributeEditorHint" minOccurs="0"/>
 *         &lt;element name="dependencyExpression" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attributeDef", propOrder = {
    "name",
    "type",
    "updateRule",
    "annotation",
    "required",
    "defaultValue",
    "pattern",
    "minValue",
    "maxValue",
    "allowedValues",
    "suggestedValues",
    "condition",
    "displayName",
    "unitName",
    "parentName",
    "visible",
    "editorHint",
    "dependencyExpression"
})
public class AttributeDef {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected AttributeType type;
    @XmlElement(required = true)
    protected AttributeUpdateRule updateRule;
    protected String annotation;
    protected Boolean required;
    protected String defaultValue;
    protected String pattern;
    protected String minValue;
    protected String maxValue;
    protected AttributeSimpleValueList allowedValues;
    protected AttributeSimpleValueList suggestedValues;
    protected String condition;
    protected String displayName;
    protected String unitName;
    protected String parentName;
    protected Boolean visible;
    protected AttributeEditorHint editorHint;
    protected String dependencyExpression;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeType }
     *     
     */
    public AttributeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeType }
     *     
     */
    public void setType(AttributeType value) {
        this.type = value;
    }

    /**
     * Gets the value of the updateRule property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeUpdateRule }
     *     
     */
    public AttributeUpdateRule getUpdateRule() {
        return updateRule;
    }

    /**
     * Sets the value of the updateRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeUpdateRule }
     *     
     */
    public void setUpdateRule(AttributeUpdateRule value) {
        this.updateRule = value;
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
     * Gets the value of the required property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRequired() {
        return required;
    }

    /**
     * Sets the value of the required property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRequired(Boolean value) {
        this.required = value;
    }

    /**
     * Gets the value of the defaultValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the value of the defaultValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    /**
     * Gets the value of the pattern property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Sets the value of the pattern property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPattern(String value) {
        this.pattern = value;
    }

    /**
     * Gets the value of the minValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinValue() {
        return minValue;
    }

    /**
     * Sets the value of the minValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinValue(String value) {
        this.minValue = value;
    }

    /**
     * Gets the value of the maxValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxValue() {
        return maxValue;
    }

    /**
     * Sets the value of the maxValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxValue(String value) {
        this.maxValue = value;
    }

    /**
     * Gets the value of the allowedValues property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeSimpleValueList }
     *     
     */
    public AttributeSimpleValueList getAllowedValues() {
        return allowedValues;
    }

    /**
     * Sets the value of the allowedValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeSimpleValueList }
     *     
     */
    public void setAllowedValues(AttributeSimpleValueList value) {
        this.allowedValues = value;
    }

    /**
     * Gets the value of the suggestedValues property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeSimpleValueList }
     *     
     */
    public AttributeSimpleValueList getSuggestedValues() {
        return suggestedValues;
    }

    /**
     * Sets the value of the suggestedValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeSimpleValueList }
     *     
     */
    public void setSuggestedValues(AttributeSimpleValueList value) {
        this.suggestedValues = value;
    }

    /**
     * Gets the value of the condition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Sets the value of the condition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCondition(String value) {
        this.condition = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the unitName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * Sets the value of the unitName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitName(String value) {
        this.unitName = value;
    }

    /**
     * Gets the value of the parentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * Sets the value of the parentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentName(String value) {
        this.parentName = value;
    }

    /**
     * Gets the value of the visible property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVisible() {
        return visible;
    }

    /**
     * Sets the value of the visible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVisible(Boolean value) {
        this.visible = value;
    }

    /**
     * Gets the value of the editorHint property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeEditorHint }
     *     
     */
    public AttributeEditorHint getEditorHint() {
        return editorHint;
    }

    /**
     * Sets the value of the editorHint property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeEditorHint }
     *     
     */
    public void setEditorHint(AttributeEditorHint value) {
        this.editorHint = value;
    }

    /**
     * Gets the value of the dependencyExpression property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDependencyExpression() {
        return dependencyExpression;
    }

    /**
     * Sets the value of the dependencyExpression property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDependencyExpression(String value) {
        this.dependencyExpression = value;
    }

}
