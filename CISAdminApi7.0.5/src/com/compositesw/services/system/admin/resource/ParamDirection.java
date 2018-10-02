
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paramDirection.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="paramDirection">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="IN"/>
 *     &lt;enumeration value="INOUT"/>
 *     &lt;enumeration value="OUT"/>
 *     &lt;enumeration value="RETURN"/>
 *     &lt;enumeration value="UNKNOWN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "paramDirection")
@XmlEnum
public enum ParamDirection {

    IN,
    INOUT,
    OUT,
    RETURN,
    UNKNOWN;

    public String value() {
        return name();
    }

    public static ParamDirection fromValue(String v) {
        return valueOf(v);
    }

}
