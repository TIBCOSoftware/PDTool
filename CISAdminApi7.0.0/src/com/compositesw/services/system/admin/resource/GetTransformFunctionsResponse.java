
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getTransformFunctionsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getTransformFunctionsResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="functions" type="{http://www.compositesw.com/services/system/admin/resource}functionList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTransformFunctionsResponse", propOrder = {
    "functions"
})
public class GetTransformFunctionsResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected FunctionList functions;

    /**
     * Gets the value of the functions property.
     * 
     * @return
     *     possible object is
     *     {@link FunctionList }
     *     
     */
    public FunctionList getFunctions() {
        return functions;
    }

    /**
     * Sets the value of the functions property.
     * 
     * @param value
     *     allowed object is
     *     {@link FunctionList }
     *     
     */
    public void setFunctions(FunctionList value) {
        this.functions = value;
    }

}
