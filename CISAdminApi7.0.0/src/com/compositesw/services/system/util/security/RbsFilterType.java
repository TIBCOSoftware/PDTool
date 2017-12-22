
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rbsFilterType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="rbsFilterType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALL"/>
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="PREDICATE"/>
 *     &lt;enumeration value="PROCEDURE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "rbsFilterType")
@XmlEnum
public enum RbsFilterType {

    ALL,
    NONE,
    PREDICATE,
    PROCEDURE;

    public String value() {
        return name();
    }

    public static RbsFilterType fromValue(String v) {
        return valueOf(v);
    }

}
