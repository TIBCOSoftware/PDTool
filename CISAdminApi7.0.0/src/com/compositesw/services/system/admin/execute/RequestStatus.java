
package com.compositesw.services.system.admin.execute;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for requestStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="requestStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="STARTED"/>
 *     &lt;enumeration value="RUNNING"/>
 *     &lt;enumeration value="WAITING"/>
 *     &lt;enumeration value="COMPLETED"/>
 *     &lt;enumeration value="CLOSING"/>
 *     &lt;enumeration value="SUCCESS"/>
 *     &lt;enumeration value="FAILURE"/>
 *     &lt;enumeration value="TERMINATED"/>
 *     &lt;enumeration value="COMMITTED"/>
 *     &lt;enumeration value="ROLLED_BACK"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "requestStatus")
@XmlEnum
public enum RequestStatus {

    STARTED,
    RUNNING,
    WAITING,
    COMPLETED,
    CLOSING,
    SUCCESS,
    FAILURE,
    TERMINATED,
    COMMITTED,
    ROLLED_BACK;

    public String value() {
        return name();
    }

    public static RequestStatus fromValue(String v) {
        return valueOf(v);
    }

}
