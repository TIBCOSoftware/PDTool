
package com.compositesw.services.system.admin.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getDomainTypesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getDomainTypesResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="domainTypes" type="{http://www.compositesw.com/services/system/admin/user}domainTypeList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDomainTypesResponse", propOrder = {
    "domainTypes"
})
public class GetDomainTypesResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected DomainTypeList domainTypes;

    /**
     * Gets the value of the domainTypes property.
     * 
     * @return
     *     possible object is
     *     {@link DomainTypeList }
     *     
     */
    public DomainTypeList getDomainTypes() {
        return domainTypes;
    }

    /**
     * Sets the value of the domainTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link DomainTypeList }
     *     
     */
    public void setDomainTypes(DomainTypeList value) {
        this.domainTypes = value;
    }

}
