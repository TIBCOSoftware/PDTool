
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rbsAssignmentOperationType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="rbsAssignmentOperationType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ASSIGN"/>
 *     &lt;enumeration value="REMOVE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "rbsAssignmentOperationType")
@XmlEnum
public enum RbsAssignmentOperationType {

    ASSIGN,
    REMOVE;

    public String value() {
        return name();
    }

    public static RbsAssignmentOperationType fromValue(String v) {
        return valueOf(v);
    }

}
