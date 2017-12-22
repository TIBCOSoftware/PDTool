
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rbsGroupJoinType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="rbsGroupJoinType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALL"/>
 *     &lt;enumeration value="ANY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "rbsGroupJoinType")
@XmlEnum
public enum RbsGroupJoinType {

    ALL,
    ANY;

    public String value() {
        return name();
    }

    public static RbsGroupJoinType fromValue(String v) {
        return valueOf(v);
    }

}
