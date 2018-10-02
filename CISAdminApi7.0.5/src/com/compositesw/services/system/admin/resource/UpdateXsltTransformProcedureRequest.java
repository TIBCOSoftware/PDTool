
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeList;


/**
 * <p>Java class for updateXsltTransformProcedureRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateXsltTransformProcedureRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}pathDetailRequest">
 *       &lt;sequence>
 *         &lt;element name="transformSourcePath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="transformSourceType" type="{http://www.compositesw.com/services/system/admin/resource}resourceType"/>
 *         &lt;element name="xsltText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xsltModel" type="{http://www.compositesw.com/services/system/admin/resource}model" minOccurs="0"/>
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
@XmlType(name = "updateXsltTransformProcedureRequest", propOrder = {
    "transformSourcePath",
    "transformSourceType",
    "xsltText",
    "xsltModel",
    "annotation",
    "isExplicitDesign",
    "parameters",
    "attributes"
})
public class UpdateXsltTransformProcedureRequest
    extends PathDetailRequest
{

    @XmlElement(required = true)
    protected String transformSourcePath;
    @XmlElement(required = true)
    protected ResourceType transformSourceType;
    protected String xsltText;
    protected Model xsltModel;
    protected String annotation;
    protected Boolean isExplicitDesign;
    protected ParameterList parameters;
    protected AttributeList attributes;

    /**
     * Gets the value of the transformSourcePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransformSourcePath() {
        return transformSourcePath;
    }

    /**
     * Sets the value of the transformSourcePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransformSourcePath(String value) {
        this.transformSourcePath = value;
    }

    /**
     * Gets the value of the transformSourceType property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceType }
     *     
     */
    public ResourceType getTransformSourceType() {
        return transformSourceType;
    }

    /**
     * Sets the value of the transformSourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceType }
     *     
     */
    public void setTransformSourceType(ResourceType value) {
        this.transformSourceType = value;
    }

    /**
     * Gets the value of the xsltText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXsltText() {
        return xsltText;
    }

    /**
     * Sets the value of the xsltText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXsltText(String value) {
        this.xsltText = value;
    }

    /**
     * Gets the value of the xsltModel property.
     * 
     * @return
     *     possible object is
     *     {@link Model }
     *     
     */
    public Model getXsltModel() {
        return xsltModel;
    }

    /**
     * Sets the value of the xsltModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Model }
     *     
     */
    public void setXsltModel(Model value) {
        this.xsltModel = value;
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
