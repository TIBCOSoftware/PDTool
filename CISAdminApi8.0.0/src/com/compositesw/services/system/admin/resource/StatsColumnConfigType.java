
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for statsColumnConfigType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="statsColumnConfigType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="BOUNDARY"/>
 *     &lt;enumeration value="ALL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "statsColumnConfigType")
@XmlEnum
public enum StatsColumnConfigType {

    NONE,
    BOUNDARY,
    ALL;

    public String value() {
        return name();
    }

    public static StatsColumnConfigType fromValue(String v) {
        return valueOf(v);
    }

}
