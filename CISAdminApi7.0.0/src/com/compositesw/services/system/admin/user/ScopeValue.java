
package com.compositesw.services.system.admin.user;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for scopeValue.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="scopeValue">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALL"/>
 *     &lt;enumeration value="LOCAL_ONLY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "scopeValue")
@XmlEnum
public enum ScopeValue {

    ALL,
    LOCAL_ONLY;

    public String value() {
        return name();
    }

    public static ScopeValue fromValue(String v) {
        return valueOf(v);
    }

}
