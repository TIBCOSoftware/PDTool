
package com.compositesw.services.system.admin.execute;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for valueType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="valueType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RESULT_ID"/>
 *     &lt;enumeration value="VALUE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "valueType")
@XmlEnum
public enum ValueType {

    RESULT_ID,
    VALUE;

    public String value() {
        return name();
    }

    public static ValueType fromValue(String v) {
        return valueOf(v);
    }

}
