
package com.compositesw.services.system.admin.user;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for domainTypeValue.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="domainTypeValue">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="COMPOSITE"/>
 *     &lt;enumeration value="DYNAMIC"/>
 *     &lt;enumeration value="LDAP"/>
 *     &lt;enumeration value="SITE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "domainTypeValue")
@XmlEnum
public enum DomainTypeValue {

    COMPOSITE,
    DYNAMIC,
    LDAP,
    SITE;

    public String value() {
        return name();
    }

    public static DomainTypeValue fromValue(String v) {
        return valueOf(v);
    }

}
