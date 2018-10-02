
package com.compositesw.services.system.admin.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for removeUserFromGroupsRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="removeUserFromGroupsRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/user}domainUserRequest">
 *       &lt;sequence>
 *         &lt;element name="groupNames" type="{http://www.compositesw.com/services/system/admin/user}domainMemberReferenceList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeUserFromGroupsRequest", propOrder = {
    "groupNames"
})
public class RemoveUserFromGroupsRequest
    extends DomainUserRequest
{

    @XmlElement(required = true)
    protected DomainMemberReferenceList groupNames;

    /**
     * Gets the value of the groupNames property.
     * 
     * @return
     *     possible object is
     *     {@link DomainMemberReferenceList }
     *     
     */
    public DomainMemberReferenceList getGroupNames() {
        return groupNames;
    }

    /**
     * Sets the value of the groupNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link DomainMemberReferenceList }
     *     
     */
    public void setGroupNames(DomainMemberReferenceList value) {
        this.groupNames = value;
    }

}
