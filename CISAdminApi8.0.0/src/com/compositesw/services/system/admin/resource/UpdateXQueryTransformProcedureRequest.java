
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeList;


/**
 * <p>Java class for updateXQueryTransformProcedureRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateXQueryTransformProcedureRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}pathDetailRequest">
 *       &lt;sequence>
 *         &lt;element name="xqueryText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xqueryModel" type="{http://www.compositesw.com/services/system/admin/resource}model" minOccurs="0"/>
 *         &lt;element name="annotation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isExplicitDesign" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="parameters" type="{http://www.compositesw.com/services/system/admin/resource}parameterList" minOccurs="0"/>
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
@XmlType(name = "updateXQueryTransformProcedureRequest", propOrder = {
    "xqueryText",
    "xqueryModel",
    "annotation",
    "isExplicitDesign",
    "parameters",
    "attributes"
})
public class UpdateXQueryTransformProcedureRequest
    extends PathDetailRequest
{

    protected String xqueryText;
    protected Model xqueryModel;
    protected String annotation;
    protected Boolean isExplicitDesign;
    protected ParameterList parameters;
    protected AttributeList attributes;

    /**
     * Gets the value of the xqueryText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXqueryText() {
        return xqueryText;
    }

    /**
     * Sets the value of the xqueryText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXqueryText(String value) {
        this.xqueryText = value;
    }

    /**
     * Gets the value of the xqueryModel property.
     * 
     * @return
     *     possible object is
     *     {@link Model }
     *     
     */
    public Model getXqueryModel() {
        return xqueryModel;
    }

    /**
     * Sets the value of the xqueryModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Model }
     *     
     */
    public void setXqueryModel(Model value) {
        this.xqueryModel = value;
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
