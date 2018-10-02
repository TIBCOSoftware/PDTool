
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for introspectionAction.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="introspectionAction">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ADD"/>
 *     &lt;enumeration value="REMOVE"/>
 *     &lt;enumeration value="SKIP"/>
 *     &lt;enumeration value="UPDATE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "introspectionAction")
@XmlEnum
public enum IntrospectionAction {

    ADD,
    REMOVE,
    SKIP,
    UPDATE;

    public String value() {
        return name();
    }

    public static IntrospectionAction fromValue(String v) {
        return valueOf(v);
    }

}
