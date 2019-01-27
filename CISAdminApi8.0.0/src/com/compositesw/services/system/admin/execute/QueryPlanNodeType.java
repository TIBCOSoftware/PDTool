
package com.compositesw.services.system.admin.execute;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for queryPlanNodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="queryPlanNodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ROOT"/>
 *     &lt;enumeration value="SELECT"/>
 *     &lt;enumeration value="UPDATE"/>
 *     &lt;enumeration value="INSERT"/>
 *     &lt;enumeration value="DELETE"/>
 *     &lt;enumeration value="UNION_ALL"/>
 *     &lt;enumeration value="UNION_DISTINCT"/>
 *     &lt;enumeration value="DISTINCT"/>
 *     &lt;enumeration value="ORDER_BY"/>
 *     &lt;enumeration value="GROUP_BY"/>
 *     &lt;enumeration value="SELECTION"/>
 *     &lt;enumeration value="JOIN"/>
 *     &lt;enumeration value="INTERSECT_ALL"/>
 *     &lt;enumeration value="INTERSECT_DISTINCT"/>
 *     &lt;enumeration value="EXCEPT_ALL"/>
 *     &lt;enumeration value="EXCEPT_DISTINCT"/>
 *     &lt;enumeration value="FUNCTION"/>
 *     &lt;enumeration value="PROCEDURE"/>
 *     &lt;enumeration value="SCAN_PHYSICAL"/>
 *     &lt;enumeration value="SCAN_CACHE"/>
 *     &lt;enumeration value="SCAN_WITH"/>
 *     &lt;enumeration value="JOIN_NESTED"/>
 *     &lt;enumeration value="JOIN_HASH"/>
 *     &lt;enumeration value="JOIN_MERGE"/>
 *     &lt;enumeration value="JOIN_CROSS"/>
 *     &lt;enumeration value="JOIN_INNER"/>
 *     &lt;enumeration value="JOIN_FULL_OUTER"/>
 *     &lt;enumeration value="JOIN_RIGHT_OUTER"/>
 *     &lt;enumeration value="JOIN_LEFT_OUTER"/>
 *     &lt;enumeration value="WITH_SUBQUERY"/>
 *     &lt;enumeration value="IN_SUBQUERY"/>
 *     &lt;enumeration value="WITH_PLACEHOLDER"/>
 *     &lt;enumeration value="EXISTS_SUBQUERY"/>
 *     &lt;enumeration value="SCALAR_SUBQUERY"/>
 *     &lt;enumeration value="ALL_SUBQUERY"/>
 *     &lt;enumeration value="ANY_SUBQUERY"/>
 *     &lt;enumeration value="OPEN"/>
 *     &lt;enumeration value="EXECUTE"/>
 *     &lt;enumeration value="CALL"/>
 *     &lt;enumeration value="INCOMPLETE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "queryPlanNodeType")
@XmlEnum
public enum QueryPlanNodeType {

    ROOT,
    SELECT,
    UPDATE,
    INSERT,
    DELETE,
    UNION_ALL,
    UNION_DISTINCT,
    DISTINCT,
    ORDER_BY,
    GROUP_BY,
    SELECTION,
    JOIN,
    INTERSECT_ALL,
    INTERSECT_DISTINCT,
    EXCEPT_ALL,
    EXCEPT_DISTINCT,
    FUNCTION,
    PROCEDURE,
    SCAN_PHYSICAL,
    SCAN_CACHE,
    SCAN_WITH,
    JOIN_NESTED,
    JOIN_HASH,
    JOIN_MERGE,
    JOIN_CROSS,
    JOIN_INNER,
    JOIN_FULL_OUTER,
    JOIN_RIGHT_OUTER,
    JOIN_LEFT_OUTER,
    WITH_SUBQUERY,
    IN_SUBQUERY,
    WITH_PLACEHOLDER,
    EXISTS_SUBQUERY,
    SCALAR_SUBQUERY,
    ALL_SUBQUERY,
    ANY_SUBQUERY,
    OPEN,
    EXECUTE,
    CALL,
    INCOMPLETE;

    public String value() {
        return name();
    }

    public static QueryPlanNodeType fromValue(String v) {
        return valueOf(v);
    }

}
