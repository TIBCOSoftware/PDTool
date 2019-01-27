
package com.compositesw.services.system.util.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attributeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="attributeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NULL"/>
 *     &lt;enumeration value="BOOLEAN"/>
 *     &lt;enumeration value="BYTE"/>
 *     &lt;enumeration value="BYTE_ARRAY"/>
 *     &lt;enumeration value="DATE"/>
 *     &lt;enumeration value="DOUBLE"/>
 *     &lt;enumeration value="FLOAT"/>
 *     &lt;enumeration value="INTEGER"/>
 *     &lt;enumeration value="LONG"/>
 *     &lt;enumeration value="OBJECT"/>
 *     &lt;enumeration value="SHORT"/>
 *     &lt;enumeration value="STRING"/>
 *     &lt;enumeration value="BOOLEAN_ARRAY"/>
 *     &lt;enumeration value="DATE_ARRAY"/>
 *     &lt;enumeration value="DOUBLE_ARRAY"/>
 *     &lt;enumeration value="FLOAT_ARRAY"/>
 *     &lt;enumeration value="LONG_ARRAY"/>
 *     &lt;enumeration value="INT_ARRAY"/>
 *     &lt;enumeration value="SHORT_ARRAY"/>
 *     &lt;enumeration value="STRING_ARRAY"/>
 *     &lt;enumeration value="FILE_PATH_STRING"/>
 *     &lt;enumeration value="PATH_STRING"/>
 *     &lt;enumeration value="PASSWORD_STRING"/>
 *     &lt;enumeration value="GUID"/>
 *     &lt;enumeration value="LIST"/>
 *     &lt;enumeration value="MAP"/>
 *     &lt;enumeration value="SET"/>
 *     &lt;enumeration value="FOLDER"/>
 *     &lt;enumeration value="UNKNOWN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "attributeType")
@XmlEnum
public enum AttributeType {

    NULL,
    BOOLEAN,
    BYTE,
    BYTE_ARRAY,
    DATE,
    DOUBLE,
    FLOAT,
    INTEGER,
    LONG,
    OBJECT,
    SHORT,
    STRING,
    BOOLEAN_ARRAY,
    DATE_ARRAY,
    DOUBLE_ARRAY,
    FLOAT_ARRAY,
    LONG_ARRAY,
    INT_ARRAY,
    SHORT_ARRAY,
    STRING_ARRAY,
    FILE_PATH_STRING,
    PATH_STRING,
    PASSWORD_STRING,
    GUID,
    LIST,
    MAP,
    SET,
    FOLDER,
    UNKNOWN;

    public String value() {
        return name();
    }

    public static AttributeType fromValue(String v) {
        return valueOf(v);
    }

}
