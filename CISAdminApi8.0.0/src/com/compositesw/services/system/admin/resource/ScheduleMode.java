
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for scheduleMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="scheduleMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INTERVAL"/>
 *     &lt;enumeration value="CALENDAR"/>
 *     &lt;enumeration value="NONE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "scheduleMode")
@XmlEnum
public enum ScheduleMode {

    INTERVAL,
    CALENDAR,
    NONE;

    public String value() {
        return name();
    }

    public static ScheduleMode fromValue(String v) {
        return valueOf(v);
    }

}
