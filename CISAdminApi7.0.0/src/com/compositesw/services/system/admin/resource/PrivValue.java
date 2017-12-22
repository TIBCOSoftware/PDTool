
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for privValue.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="privValue">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OWNER"/>
 *     &lt;enumeration value="READ"/>
 *     &lt;enumeration value="WRITE"/>
 *     &lt;enumeration value="SELECT"/>
 *     &lt;enumeration value="INSERT"/>
 *     &lt;enumeration value="UPDATE"/>
 *     &lt;enumeration value="DELETE"/>
 *     &lt;enumeration value="EXECUTE"/>
 *     &lt;enumeration value="GRANT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "privValue")
@XmlEnum
public enum PrivValue {

    OWNER,
    READ,
    WRITE,
    SELECT,
    INSERT,
    UPDATE,
    DELETE,
    EXECUTE,
    GRANT;

    public String value() {
        return name();
    }

    public static PrivValue fromValue(String v) {
        return valueOf(v);
    }

}
