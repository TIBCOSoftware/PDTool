
package com.compositesw.services.system.admin.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for removeUsersFromGroupRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="removeUsersFromGroupRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/user}domainGroupRequest">
 *       &lt;sequence>
 *         &lt;element name="userNames" type="{http://www.compositesw.com/services/system/admin/user}domainMemberReferenceList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeUsersFromGroupRequest", propOrder = {
    "userNames"
})
public class RemoveUsersFromGroupRequest
    extends DomainGroupRequest
{

    @XmlElement(required = true)
    protected DomainMemberReferenceList userNames;

    /**
     * Gets the value of the userNames property.
     * 
     * @return
     *     possible object is
     *     {@link DomainMemberReferenceList }
     *     
     */
    public DomainMemberReferenceList getUserNames() {
        return userNames;
    }

    /**
     * Sets the value of the userNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link DomainMemberReferenceList }
     *     
     */
    public void setUserNames(DomainMemberReferenceList value) {
        this.userNames = value;
    }

}
