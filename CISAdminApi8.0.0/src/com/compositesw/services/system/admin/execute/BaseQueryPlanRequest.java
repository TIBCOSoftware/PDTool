
package com.compositesw.services.system.admin.execute;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for baseQueryPlanRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="baseQueryPlanRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="rootNodeTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseQueryPlanRequest", propOrder = {
    "rootNodeTitle"
})
@XmlSeeAlso({
    GetSqlPlanRequest.class,
    GetResourcePlanRequest.class,
    GetResultSetPlanRequest.class
})
public class BaseQueryPlanRequest
    extends BaseRequest
{

    protected String rootNodeTitle;

    /**
     * Gets the value of the rootNodeTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRootNodeTitle() {
        return rootNodeTitle;
    }

    /**
     * Sets the value of the rootNodeTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRootNodeTitle(String value) {
        this.rootNodeTitle = value;
    }

}
