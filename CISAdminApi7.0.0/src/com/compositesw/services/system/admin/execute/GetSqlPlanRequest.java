
package com.compositesw.services.system.admin.execute;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSqlPlanRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSqlPlanRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/execute}baseQueryPlanRequest">
 *       &lt;sequence>
 *         &lt;element name="sqlText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parameters" type="{http://www.compositesw.com/services/system/admin/execute}parameterList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSqlPlanRequest", propOrder = {
    "sqlText",
    "parameters"
})
public class GetSqlPlanRequest
    extends BaseQueryPlanRequest
{

    @XmlElement(required = true)
    protected String sqlText;
    protected ParameterList parameters;

    /**
     * Gets the value of the sqlText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSqlText() {
        return sqlText;
    }

    /**
     * Sets the value of the sqlText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSqlText(String value) {
        this.sqlText = value;
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

}
