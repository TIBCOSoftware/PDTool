
package com.compositesw.services.system.admin.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for baseUserResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="baseUserResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="users" type="{http://www.compositesw.com/services/system/admin/user}userList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseUserResponse", propOrder = {
    "users"
})
@XmlSeeAlso({
    GetUsersByGroupResponse.class,
    GetUsersResponse.class
})
public class BaseUserResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected UserList users;

    /**
     * Gets the value of the users property.
     * 
     * @return
     *     possible object is
     *     {@link UserList }
     *     
     */
    public UserList getUsers() {
        return users;
    }

    /**
     * Sets the value of the users property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserList }
     *     
     */
    public void setUsers(UserList value) {
        this.users = value;
    }

}
