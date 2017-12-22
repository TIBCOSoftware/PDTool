
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bindingType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="bindingType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DOCUMENT_LITERAL"/>
 *     &lt;enumeration value="RPC_SOAP"/>
 *     &lt;enumeration value="RPC_LITERAL"/>
 *     &lt;enumeration value="HTTP_POST"/>
 *     &lt;enumeration value="HTTP_GET"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "bindingType")
@XmlEnum
public enum BindingType {

    DOCUMENT_LITERAL,
    RPC_SOAP,
    RPC_LITERAL,
    HTTP_POST,
    HTTP_GET;

    public String value() {
        return name();
    }

    public static BindingType fromValue(String v) {
        return valueOf(v);
    }

}
