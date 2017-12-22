
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for copyMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="copyMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALTER_NAME_IF_EXISTS"/>
 *     &lt;enumeration value="FAIL_IF_EXISTS"/>
 *     &lt;enumeration value="OVERWRITE_MERGE_IF_EXISTS"/>
 *     &lt;enumeration value="OVERWRITE_REPLACE_IF_EXISTS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "copyMode")
@XmlEnum
public enum CopyMode {

    ALTER_NAME_IF_EXISTS,
    FAIL_IF_EXISTS,
    OVERWRITE_MERGE_IF_EXISTS,
    OVERWRITE_REPLACE_IF_EXISTS;

    public String value() {
        return name();
    }

    public static CopyMode fromValue(String v) {
        return valueOf(v);
    }

}
