
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for introspectState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="introspectState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="IGNORED"/>
 *     &lt;enumeration value="NEW"/>
 *     &lt;enumeration value="REMOVED"/>
 *     &lt;enumeration value="SELF"/>
 *     &lt;enumeration value="SELF_AND_CHILDREN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "introspectState")
@XmlEnum
public enum IntrospectState {

    IGNORED,
    NEW,
    REMOVED,
    SELF,
    SELF_AND_CHILDREN;

    public String value() {
        return name();
    }

    public static IntrospectState fromValue(String v) {
        return valueOf(v);
    }

}
