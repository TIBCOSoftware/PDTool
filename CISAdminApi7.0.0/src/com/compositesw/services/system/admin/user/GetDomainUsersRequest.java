
package com.compositesw.services.system.admin.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getDomainUsersRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getDomainUsersRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/user}domainRequest">
 *       &lt;sequence>
 *         &lt;element name="scope" type="{http://www.compositesw.com/services/system/admin/user}scopeValue"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDomainUsersRequest", propOrder = {
    "scope"
})
public class GetDomainUsersRequest
    extends DomainRequest
{

    @XmlElement(required = true)
    protected ScopeValue scope;

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link ScopeValue }
     *     
     */
    public ScopeValue getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScopeValue }
     *     
     */
    public void setScope(ScopeValue value) {
        this.scope = value;
    }

}
