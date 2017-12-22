
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for archiveTypes.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="archiveTypes">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BACKUP"/>
 *     &lt;enumeration value="PACKAGE"/>
 *     &lt;enumeration value="ROOT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "archiveTypes")
@XmlEnum
public enum ArchiveTypes {

    BACKUP,
    PACKAGE,
    ROOT;

    public String value() {
        return name();
    }

    public static ArchiveTypes fromValue(String v) {
        return valueOf(v);
    }

}
