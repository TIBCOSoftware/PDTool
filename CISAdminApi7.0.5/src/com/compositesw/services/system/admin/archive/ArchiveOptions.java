
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for archiveOptions.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="archiveOptions">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INCLUDE_CACHING"/>
 *     &lt;enumeration value="INCLUDE_CUSTOM_JAVA_JARS"/>
 *     &lt;enumeration value="INCLUDE_STATISTICS"/>
 *     &lt;enumeration value="INCLUDE_DEPENDENCY"/>
 *     &lt;enumeration value="INCLUDE_PHYSICAL_SOURCE_INFO"/>
 *     &lt;enumeration value="INCLUDE_REQUIRED_USERS"/>
 *     &lt;enumeration value="INCLUDE_SECURITY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "archiveOptions")
@XmlEnum
public enum ArchiveOptions {

    INCLUDE_CACHING,
    INCLUDE_CUSTOM_JAVA_JARS,
    INCLUDE_STATISTICS,
    INCLUDE_DEPENDENCY,
    INCLUDE_PHYSICAL_SOURCE_INFO,
    INCLUDE_REQUIRED_USERS,
    INCLUDE_SECURITY;

    public String value() {
        return name();
    }

    public static ArchiveOptions fromValue(String v) {
        return valueOf(v);
    }

}
