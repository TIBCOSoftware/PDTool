
package com.compositesw.services.system.util.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for messageSeverity.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="messageSeverity">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DEBUG"/>
 *     &lt;enumeration value="INFO"/>
 *     &lt;enumeration value="WARNING"/>
 *     &lt;enumeration value="ERROR"/>
 *     &lt;enumeration value="CRITICAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "messageSeverity")
@XmlEnum
public enum MessageSeverity {

    DEBUG,
    INFO,
    WARNING,
    ERROR,
    CRITICAL;

    public String value() {
        return name();
    }

    public static MessageSeverity fromValue(String v) {
        return valueOf(v);
    }

}
