
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.admin.execute.ParameterList;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for designProcedureByExampleRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="designProcedureByExampleRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="inputs" type="{http://www.compositesw.com/services/system/admin/execute}parameterList" minOccurs="0"/>
 *         &lt;element name="commitChanges" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "designProcedureByExampleRequest", propOrder = {
    "path",
    "inputs",
    "commitChanges"
})
public class DesignProcedureByExampleRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected String path;
    protected ParameterList inputs;
    protected boolean commitChanges;

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
     * Gets the value of the inputs property.
     * 
     * @return
     *     possible object is
     *     {@link ParameterList }
     *     
     */
    public ParameterList getInputs() {
        return inputs;
    }

    /**
     * Sets the value of the inputs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParameterList }
     *     
     */
    public void setInputs(ParameterList value) {
        this.inputs = value;
    }

    /**
     * Gets the value of the commitChanges property.
     * 
     */
    public boolean isCommitChanges() {
        return commitChanges;
    }

    /**
     * Sets the value of the commitChanges property.
     * 
     */
    public void setCommitChanges(boolean value) {
        this.commitChanges = value;
    }

}
