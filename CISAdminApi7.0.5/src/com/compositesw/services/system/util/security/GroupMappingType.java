
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for groupMappingType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="groupMappingType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="X500_DN"/>
 *     &lt;enumeration value="KRB_REALM"/>
 *     &lt;enumeration value="NAME_MATCH"/>
 *     &lt;enumeration value="NAME_REGEX"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "groupMappingType")
@XmlEnum
public enum GroupMappingType {

    @XmlEnumValue("X500_DN")
    X_500_DN("X500_DN"),
    KRB_REALM("KRB_REALM"),
    NAME_MATCH("NAME_MATCH"),
    NAME_REGEX("NAME_REGEX");
    private final String value;

    GroupMappingType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GroupMappingType fromValue(String v) {
        for (GroupMappingType c: GroupMappingType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
