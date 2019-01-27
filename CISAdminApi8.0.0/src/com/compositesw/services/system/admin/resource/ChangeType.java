
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for changeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="changeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ADDED"/>
 *     &lt;enumeration value="CHANGED"/>
 *     &lt;enumeration value="REMOVED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "changeType")
@XmlEnum
public enum ChangeType {

    ADDED,
    CHANGED,
    REMOVED;

    public String value() {
        return name();
    }

    public static ChangeType fromValue(String v) {
        return valueOf(v);
    }

}
