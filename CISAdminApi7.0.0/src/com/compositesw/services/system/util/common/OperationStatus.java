
package com.compositesw.services.system.util.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for operationStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="operationStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CANCELED"/>
 *     &lt;enumeration value="FAIL"/>
 *     &lt;enumeration value="INCOMPLETE"/>
 *     &lt;enumeration value="SUCCESS"/>
 *     &lt;enumeration value="WAITING"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "operationStatus")
@XmlEnum
public enum OperationStatus {

    CANCELED,
    FAIL,
    INCOMPLETE,
    SUCCESS,
    WAITING;

    public String value() {
        return name();
    }

    public static OperationStatus fromValue(String v) {
        return valueOf(v);
    }

}
