
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for indexType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="indexType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="UNKNOWN"/>
 *     &lt;enumeration value="STATISTIC"/>
 *     &lt;enumeration value="CLUSTERED"/>
 *     &lt;enumeration value="HASHED"/>
 *     &lt;enumeration value="OTHER"/>
 *     &lt;enumeration value="PRIMARY_KEY"/>
 *     &lt;enumeration value="FOREIGN_KEY"/>
 *     &lt;enumeration value="SYSTEM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "indexType")
@XmlEnum
public enum IndexType {

    UNKNOWN,
    STATISTIC,
    CLUSTERED,
    HASHED,
    OTHER,
    PRIMARY_KEY,
    FOREIGN_KEY,
    SYSTEM;

    public String value() {
        return name();
    }

    public static IndexType fromValue(String v) {
        return valueOf(v);
    }

}
