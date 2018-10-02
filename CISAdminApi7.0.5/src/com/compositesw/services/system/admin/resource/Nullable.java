
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for nullable.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="nullable">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="UNKNOWN"/>
 *     &lt;enumeration value="IS_NULLABLE"/>
 *     &lt;enumeration value="IS_NOT_NULLABLE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "nullable")
@XmlEnum
public enum Nullable {

    UNKNOWN,
    IS_NULLABLE,
    IS_NOT_NULLABLE;

    public String value() {
        return name();
    }

    public static Nullable fromValue(String v) {
        return valueOf(v);
    }

}
