
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for clearRule.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="clearRule">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="ON_LOAD"/>
 *     &lt;enumeration value="ON_FAILURE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "clearRule")
@XmlEnum
public enum ClearRule {

    NONE,
    ON_LOAD,
    ON_FAILURE;

    public String value() {
        return name();
    }

    public static ClearRule fromValue(String v) {
        return valueOf(v);
    }

}
