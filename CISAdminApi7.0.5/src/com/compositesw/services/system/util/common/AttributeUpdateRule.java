
package com.compositesw.services.system.util.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attributeUpdateRule.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="attributeUpdateRule">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="READ_ONLY"/>
 *     &lt;enumeration value="READ_WRITE"/>
 *     &lt;enumeration value="WRITE_ON_CREATE"/>
 *     &lt;enumeration value="WRITE_ON_EDIT"/>
 *     &lt;enumeration value="WRITE_ON_IMPORT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "attributeUpdateRule")
@XmlEnum
public enum AttributeUpdateRule {

    READ_ONLY,
    READ_WRITE,
    WRITE_ON_CREATE,
    WRITE_ON_EDIT,
    WRITE_ON_IMPORT;

    public String value() {
        return name();
    }

    public static AttributeUpdateRule fromValue(String v) {
        return valueOf(v);
    }

}
