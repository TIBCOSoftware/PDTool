
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for refreshMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="refreshMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MANUAL"/>
 *     &lt;enumeration value="SCHEDULED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "refreshMode")
@XmlEnum
public enum RefreshMode {

    MANUAL,
    SCHEDULED;

    public String value() {
        return name();
    }

    public static RefreshMode fromValue(String v) {
        return valueOf(v);
    }

}
