
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for statsDataSourceDefault.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="statsDataSourceDefault">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALL"/>
 *     &lt;enumeration value="COLUMN_BOUNDARY"/>
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="TABLE_BOUNDARY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "statsDataSourceDefault")
@XmlEnum
public enum StatsDataSourceDefault {

    ALL,
    COLUMN_BOUNDARY,
    NONE,
    TABLE_BOUNDARY;

    public String value() {
        return name();
    }

    public static StatsDataSourceDefault fromValue(String v) {
        return valueOf(v);
    }

}
