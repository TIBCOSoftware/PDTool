
package com.compositesw.services.system.util.security;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getAvailableLoginModuleNamesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getAvailableLoginModuleNamesResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="loginModuleName" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAvailableLoginModuleNamesResponse", propOrder = {
    "loginModuleName"
})
public class GetAvailableLoginModuleNamesResponse
    extends BaseResponse
{

    protected List<String> loginModuleName;

    /**
     * Gets the value of the loginModuleName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the loginModuleName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLoginModuleName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getLoginModuleName() {
        if (loginModuleName == null) {
            loginModuleName = new ArrayList<String>();
        }
        return this.loginModuleName;
    }

}
