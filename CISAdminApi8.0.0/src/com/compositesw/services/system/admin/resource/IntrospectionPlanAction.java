
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for introspectionPlanAction.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="introspectionPlanAction">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ADD_OR_UPDATE"/>
 *     &lt;enumeration value="ADD_OR_UPDATE_RECURSIVELY"/>
 *     &lt;enumeration value="REMOVE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "introspectionPlanAction")
@XmlEnum
public enum IntrospectionPlanAction {

    ADD_OR_UPDATE,
    ADD_OR_UPDATE_RECURSIVELY,
    REMOVE;

    public String value() {
        return name();
    }

    public static IntrospectionPlanAction fromValue(String v) {
        return valueOf(v);
    }

}
