
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sortOrder.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="sortOrder">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ASCENDING"/>
 *     &lt;enumeration value="DESCENDING"/>
 *     &lt;enumeration value="NONE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "sortOrder")
@XmlEnum
public enum SortOrder {

    ASCENDING,
    DESCENDING,
    NONE;

    public String value() {
        return name();
    }

    public static SortOrder fromValue(String v) {
        return valueOf(v);
    }

}
