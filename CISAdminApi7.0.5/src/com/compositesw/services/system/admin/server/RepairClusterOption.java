
package com.compositesw.services.system.admin.server;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for repairClusterOption.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="repairClusterOption">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PREVIEW_MERGE_CONFLICTS"/>
 *     &lt;enumeration value="MERGE_IF_NO_CONFLICTS"/>
 *     &lt;enumeration value="OVERWRITE_MERGE_CONFLICTS"/>
 *     &lt;enumeration value="REPAIR_FULL"/>
 *     &lt;enumeration value="REGROUP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "repairClusterOption")
@XmlEnum
public enum RepairClusterOption {

    PREVIEW_MERGE_CONFLICTS,
    MERGE_IF_NO_CONFLICTS,
    OVERWRITE_MERGE_CONFLICTS,
    REPAIR_FULL,
    REGROUP;

    public String value() {
        return name();
    }

    public static RepairClusterOption fromValue(String v) {
        return valueOf(v);
    }

}
