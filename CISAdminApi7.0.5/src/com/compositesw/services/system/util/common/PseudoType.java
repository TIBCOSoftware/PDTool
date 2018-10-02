
package com.compositesw.services.system.util.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pseudoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="pseudoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ANY"/>
 *     &lt;enumeration value="DATE_OR_TIME"/>
 *     &lt;enumeration value="FLOATING_POINT"/>
 *     &lt;enumeration value="NUMBER"/>
 *     &lt;enumeration value="STRING"/>
 *     &lt;enumeration value="WHOLE_NUMBER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "pseudoType")
@XmlEnum
public enum PseudoType {

    ANY,
    DATE_OR_TIME,
    FLOATING_POINT,
    NUMBER,
    STRING,
    WHOLE_NUMBER;

    public String value() {
        return name();
    }

    public static PseudoType fromValue(String v) {
        return valueOf(v);
    }

}
