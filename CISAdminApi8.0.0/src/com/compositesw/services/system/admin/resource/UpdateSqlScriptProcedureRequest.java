
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeList;


/**
 * <p>Java class for updateSqlScriptProcedureRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateSqlScriptProcedureRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}pathDetailRequest">
 *       &lt;sequence>
 *         &lt;element name="scriptText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="scriptModel" type="{http://www.compositesw.com/services/system/admin/resource}model" minOccurs="0"/>
 *         &lt;element name="isExplicitDesign" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="parameters" type="{http://www.compositesw.com/services/system/admin/resource}parameterList" minOccurs="0"/>
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
@XmlType(name = "updateSqlScriptProcedureRequest", propOrder = {
    "scriptText",
    "scriptModel",
    "isExplicitDesign",
    "parameters",
    "annotation",
    "attributes"
})
public class UpdateSqlScriptProcedureRequest
    extends PathDetailRequest
{

    @XmlElement(required = true)
    protected String scriptText;
    protected Model scriptModel;
    protected Boolean isExplicitDesign;
    protected ParameterList parameters;
    protected String annotation;
    protected AttributeList attributes;

    /**
     * Gets the value of the scriptText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScriptText() {
        return scriptText;
    }

    /**
     * Sets the value of the scriptText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScriptText(String value) {
        this.scriptText = value;
    }

    /**
     * Gets the value of the scriptModel property.
     * 
     * @return
     *     possible object is
     *     {@link Model }
     *     
     */
    public Model getScriptModel() {
        return scriptModel;
    }

    /**
     * Sets the value of the scriptModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Model }
     *     
     */
    public void setScriptModel(Model value) {
        this.scriptModel = value;
    }

    /**
     * Gets the value of the isExplicitDesign property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsExplicitDesign() {
        return isExplicitDesign;
    }

    /**
     * Sets the value of the isExplicitDesign property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsExplicitDesign(Boolean value) {
        this.isExplicitDesign = value;
    }

    /**
     * Gets the value of the parameters property.
     * 
     * @return
     *     possible object is
     *     {@link ParameterList }
     *     
     */
    public ParameterList getParameters() {
        return parameters;
    }

    /**
     * Sets the value of the parameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParameterList }
     *     
     */
    public void setParameters(ParameterList value) {
        this.parameters = value;
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
