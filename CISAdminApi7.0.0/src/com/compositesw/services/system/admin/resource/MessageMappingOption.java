
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for messageMappingOption.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="messageMappingOption">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="INLCUDE_HEADERS"/>
 *     &lt;enumeration value="INCLUDE_FAULT"/>
 *     &lt;enumeration value="INCLUDE_ATTACHEMENTS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "messageMappingOption")
@XmlEnum
public enum MessageMappingOption {

    NONE,
    INLCUDE_HEADERS,
    INCLUDE_FAULT,
    INCLUDE_ATTACHEMENTS;

    public String value() {
        return name();
    }

    public static MessageMappingOption fromValue(String v) {
        return valueOf(v);
    }

}
