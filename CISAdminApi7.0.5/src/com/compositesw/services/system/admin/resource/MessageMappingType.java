
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for messageMappingType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="messageMappingType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ENVELOPE"/>
 *     &lt;enumeration value="MESSAGE_PARTS"/>
 *     &lt;enumeration value="CHILDREN_OF_MESSAGE_PARTS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "messageMappingType")
@XmlEnum
public enum MessageMappingType {

    ENVELOPE,
    MESSAGE_PARTS,
    CHILDREN_OF_MESSAGE_PARTS;

    public String value() {
        return name();
    }

    public static MessageMappingType fromValue(String v) {
        return valueOf(v);
    }

}
