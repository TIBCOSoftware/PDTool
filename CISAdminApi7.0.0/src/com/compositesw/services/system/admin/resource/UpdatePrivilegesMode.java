
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updatePrivilegesMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="updatePrivilegesMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OVERWRITE_APPEND"/>
 *     &lt;enumeration value="SET_EXACTLY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "updatePrivilegesMode")
@XmlEnum
public enum UpdatePrivilegesMode {

    OVERWRITE_APPEND,
    SET_EXACTLY;

    public String value() {
        return name();
    }

    public static UpdatePrivilegesMode fromValue(String v) {
        return valueOf(v);
    }

}
