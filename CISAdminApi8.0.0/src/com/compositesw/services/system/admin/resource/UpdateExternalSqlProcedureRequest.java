
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeList;


/**
 * <p>Java class for updateExternalSqlProcedureRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateExternalSqlProcedureRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}pathDetailRequest">
 *       &lt;sequence>
 *         &lt;element name="externalSqlText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="externalDataSourcePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "updateExternalSqlProcedureRequest", propOrder = {
    "externalSqlText",
    "externalDataSourcePath",
    "parameters",
    "annotation",
    "attributes"
})
public class UpdateExternalSqlProcedureRequest
    extends PathDetailRequest
{

    @XmlElement(required = true)
    protected String externalSqlText;
    protected String externalDataSourcePath;
    protected ParameterList parameters;
    protected String annotation;
    protected AttributeList attributes;

    /**
     * Gets the value of the externalSqlText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalSqlText() {
        return externalSqlText;
    }

    /**
     * Sets the value of the externalSqlText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalSqlText(String value) {
        this.externalSqlText = value;
    }

    /**
     * Gets the value of the externalDataSourcePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalDataSourcePath() {
        return externalDataSourcePath;
    }

    /**
     * Sets the value of the externalDataSourcePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalDataSourcePath(String value) {
        this.externalDataSourcePath = value;
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
