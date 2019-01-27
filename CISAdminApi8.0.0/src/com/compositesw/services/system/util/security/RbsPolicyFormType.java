
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rbsPolicyFormType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="rbsPolicyFormType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CODE"/>
 *     &lt;enumeration value="RULE"/>
 *     &lt;enumeration value="GROUP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "rbsPolicyFormType")
@XmlEnum
public enum RbsPolicyFormType {

    CODE,
    RULE,
    GROUP;

    public String value() {
        return name();
    }

    public static RbsPolicyFormType fromValue(String v) {
        return valueOf(v);
    }

}
