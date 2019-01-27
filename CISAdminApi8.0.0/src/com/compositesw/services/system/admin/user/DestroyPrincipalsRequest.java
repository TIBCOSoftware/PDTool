
package com.compositesw.services.system.admin.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;
import com.compositesw.services.system.util.common.PrincipalList;


/**
 * <p>Java class for destroyPrincipalsRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="destroyPrincipalsRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="principals" type="{http://www.compositesw.com/services/system/util/common}principalList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "destroyPrincipalsRequest", propOrder = {
    "principals"
})
public class DestroyPrincipalsRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected PrincipalList principals;

    /**
     * Gets the value of the principals property.
     * 
     * @return
     *     possible object is
     *     {@link PrincipalList }
     *     
     */
    public PrincipalList getPrincipals() {
        return principals;
    }

    /**
     * Sets the value of the principals property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrincipalList }
     *     
     */
    public void setPrincipals(PrincipalList value) {
        this.principals = value;
    }

}
