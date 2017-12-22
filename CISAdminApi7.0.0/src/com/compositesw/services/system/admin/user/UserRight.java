
package com.compositesw.services.system.admin.user;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userRight.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="userRight">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ACCESS_TOOLS"/>
 *     &lt;enumeration value="MODIFY_ALL_CONFIG"/>
 *     &lt;enumeration value="MODIFY_ALL_RESOURCES"/>
 *     &lt;enumeration value="MODIFY_ALL_STATUS"/>
 *     &lt;enumeration value="MODIFY_ALL_USERS"/>
 *     &lt;enumeration value="READ_ALL_RESOURCES"/>
 *     &lt;enumeration value="READ_ALL_CONFIG"/>
 *     &lt;enumeration value="READ_ALL_STATUS"/>
 *     &lt;enumeration value="READ_ALL_USERS"/>
 *     &lt;enumeration value="UNLOCK_RESOURCE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "userRight")
@XmlEnum
public enum UserRight {

    ACCESS_TOOLS,
    MODIFY_ALL_CONFIG,
    MODIFY_ALL_RESOURCES,
    MODIFY_ALL_STATUS,
    MODIFY_ALL_USERS,
    READ_ALL_RESOURCES,
    READ_ALL_CONFIG,
    READ_ALL_STATUS,
    READ_ALL_USERS,
    UNLOCK_RESOURCE;

    public String value() {
        return name();
    }

    public static UserRight fromValue(String v) {
        return valueOf(v);
    }

}
