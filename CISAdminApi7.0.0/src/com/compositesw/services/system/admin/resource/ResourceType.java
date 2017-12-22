
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resourceType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="resourceType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="ADAPTER"/>
 *     &lt;enumeration value="CONTAINER"/>
 *     &lt;enumeration value="DATA_SOURCE"/>
 *     &lt;enumeration value="DEFINITION_SET"/>
 *     &lt;enumeration value="LINK"/>
 *     &lt;enumeration value="PROCEDURE"/>
 *     &lt;enumeration value="TABLE"/>
 *     &lt;enumeration value="TREE"/>
 *     &lt;enumeration value="TRIGGER"/>
 *     &lt;enumeration value="RELATIONSHIP"/>
 *     &lt;enumeration value="MODEL"/>
 *     &lt;enumeration value="POLICY"/>
 *     &lt;enumeration value="EXTENSION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "resourceType")
@XmlEnum
public enum ResourceType {

    NONE,
    ADAPTER,
    CONTAINER,
    DATA_SOURCE,
    DEFINITION_SET,
    LINK,
    PROCEDURE,
    TABLE,
    TREE,
    TRIGGER,
    RELATIONSHIP,
    MODEL,
    POLICY,
    EXTENSION;

    public String value() {
        return name();
    }

    public static ResourceType fromValue(String v) {
        return valueOf(v);
    }

}
