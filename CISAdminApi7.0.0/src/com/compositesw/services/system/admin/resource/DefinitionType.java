
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for definitionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="definitionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BINDING_DEFINITION"/>
 *     &lt;enumeration value="CONSTANT_DEFINITION"/>
 *     &lt;enumeration value="ELEMENT_DEFINITION"/>
 *     &lt;enumeration value="EXCEPTION_DEFINITION"/>
 *     &lt;enumeration value="MESSAGE_DEFINITION"/>
 *     &lt;enumeration value="PORT_TYPE_DEFINITION"/>
 *     &lt;enumeration value="SERVICE_DEFINITION"/>
 *     &lt;enumeration value="TYPE_DEFINITION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "definitionType")
@XmlEnum
public enum DefinitionType {

    BINDING_DEFINITION,
    CONSTANT_DEFINITION,
    ELEMENT_DEFINITION,
    EXCEPTION_DEFINITION,
    MESSAGE_DEFINITION,
    PORT_TYPE_DEFINITION,
    SERVICE_DEFINITION,
    TYPE_DEFINITION;

    public String value() {
        return name();
    }

    public static DefinitionType fromValue(String v) {
        return valueOf(v);
    }

}
