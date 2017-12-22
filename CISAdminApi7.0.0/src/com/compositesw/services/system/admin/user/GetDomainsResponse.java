
package com.compositesw.services.system.admin.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getDomainsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getDomainsResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="domains" type="{http://www.compositesw.com/services/system/admin/user}domainList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDomainsResponse", propOrder = {
    "domains"
})
public class GetDomainsResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected DomainList domains;

    /**
     * Gets the value of the domains property.
     * 
     * @return
     *     possible object is
     *     {@link DomainList }
     *     
     */
    public DomainList getDomains() {
        return domains;
    }

    /**
     * Sets the value of the domains property.
     * 
     * @param value
     *     allowed object is
     *     {@link DomainList }
     *     
     */
    public void setDomains(DomainList value) {
        this.domains = value;
    }

}
