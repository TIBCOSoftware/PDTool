
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for statsGatherType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="statsGatherType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CUSTOM"/>
 *     &lt;enumeration value="DEFAULT"/>
 *     &lt;enumeration value="DISABLED"/>
 *     &lt;enumeration value="TABLE_BOUNDARY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "statsGatherType")
@XmlEnum
public enum StatsGatherType {

    CUSTOM,
    DEFAULT,
    DISABLED,
    TABLE_BOUNDARY;

    public String value() {
        return name();
    }

    public static StatsGatherType fromValue(String v) {
        return valueOf(v);
    }

}
