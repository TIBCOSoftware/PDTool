
package com.compositesw.services.system.util.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for detailLevel.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="detailLevel">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FULL"/>
 *     &lt;enumeration value="SIMPLE"/>
 *     &lt;enumeration value="NONE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "detailLevel")
@XmlEnum
public enum DetailLevel {

    FULL,
    SIMPLE,
    NONE;

    public String value() {
        return name();
    }

    public static DetailLevel fromValue(String v) {
        return valueOf(v);
    }

}
