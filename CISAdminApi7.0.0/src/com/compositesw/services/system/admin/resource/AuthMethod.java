
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for authMethod.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="authMethod">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="HTTP_BASIC"/>
 *     &lt;enumeration value="HTTP_DIGEST"/>
 *     &lt;enumeration value="HTTPS_X509_CERT"/>
 *     &lt;enumeration value="WSS_USERNAME_TOKEN"/>
 *     &lt;enumeration value="WSS_X509_CERT_BINARY_TOKEN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "authMethod")
@XmlEnum
public enum AuthMethod {

    HTTP_BASIC("HTTP_BASIC"),
    HTTP_DIGEST("HTTP_DIGEST"),
    @XmlEnumValue("HTTPS_X509_CERT")
    HTTPS_X_509_CERT("HTTPS_X509_CERT"),
    WSS_USERNAME_TOKEN("WSS_USERNAME_TOKEN"),
    @XmlEnumValue("WSS_X509_CERT_BINARY_TOKEN")
    WSS_X_509_CERT_BINARY_TOKEN("WSS_X509_CERT_BINARY_TOKEN");
    private final String value;

    AuthMethod(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AuthMethod fromValue(String v) {
        for (AuthMethod c: AuthMethod.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
