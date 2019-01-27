
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeList;
import com.compositesw.services.system.util.common.ServerTaskRequest;


/**
 * <p>Java class for introspectResourcesTaskRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="introspectResourcesTaskRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}serverTaskRequest">
 *       &lt;sequence>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="plan" type="{http://www.compositesw.com/services/system/admin/resource}introspectionPlan"/>
 *         &lt;element name="runInBackgroundTransaction" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
@XmlType(name = "introspectResourcesTaskRequest", propOrder = {
    "path",
    "plan",
    "runInBackgroundTransaction",
    "attributes"
})
public class IntrospectResourcesTaskRequest
    extends ServerTaskRequest
{

    @XmlElement(required = true)
    protected String path;
    @XmlElement(required = true)
    protected IntrospectionPlan plan;
    protected boolean runInBackgroundTransaction;
    protected AttributeList attributes;

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
     * Gets the value of the plan property.
     * 
     * @return
     *     possible object is
     *     {@link IntrospectionPlan }
     *     
     */
    public IntrospectionPlan getPlan() {
        return plan;
    }

    /**
     * Sets the value of the plan property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntrospectionPlan }
     *     
     */
    public void setPlan(IntrospectionPlan value) {
        this.plan = value;
    }

    /**
     * Gets the value of the runInBackgroundTransaction property.
     * 
     */
    public boolean isRunInBackgroundTransaction() {
        return runInBackgroundTransaction;
    }

    /**
     * Sets the value of the runInBackgroundTransaction property.
     * 
     */
    public void setRunInBackgroundTransaction(boolean value) {
        this.runInBackgroundTransaction = value;
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
