
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for storageMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="storageMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AUTOMATIC"/>
 *     &lt;enumeration value="DATA_SOURCE"/>
 *     &lt;enumeration value="DATA_SOURCE_OTPS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "storageMode")
@XmlEnum
public enum StorageMode {

    AUTOMATIC,
    DATA_SOURCE,
    DATA_SOURCE_OTPS;

    public String value() {
        return name();
    }

    public static StorageMode fromValue(String v) {
        return valueOf(v);
    }

}
