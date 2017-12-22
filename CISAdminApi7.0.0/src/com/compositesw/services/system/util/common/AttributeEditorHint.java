
package com.compositesw.services.system.util.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attributeEditorHint.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="attributeEditorHint">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CHECK"/>
 *     &lt;enumeration value="CHOICE"/>
 *     &lt;enumeration value="EDITABLE_CHOICE"/>
 *     &lt;enumeration value="LABEL"/>
 *     &lt;enumeration value="PASSWORD"/>
 *     &lt;enumeration value="RADIO"/>
 *     &lt;enumeration value="TEXT"/>
 *     &lt;enumeration value="TEXT_PANE"/>
 *     &lt;enumeration value="RADIO_TEXT"/>
 *     &lt;enumeration value="RADIO_SCHEMA"/>
 *     &lt;enumeration value="ENTRY_LABEL"/>
 *     &lt;enumeration value="CONNECTOR"/>
 *     &lt;enumeration value="DATA_SOURCE"/>
 *     &lt;enumeration value="DS_FILTER"/>
 *     &lt;enumeration value="FILE"/>
 *     &lt;enumeration value="JDBC_PROPERTIES"/>
 *     &lt;enumeration value="KEY_STORE"/>
 *     &lt;enumeration value="PATH"/>
 *     &lt;enumeration value="PIPELINE_PROCEDURE"/>
 *     &lt;enumeration value="PROCEDURE"/>
 *     &lt;enumeration value="PROCEDURE_PARAMETER"/>
 *     &lt;enumeration value="PROCEDURE_VIEW"/>
 *     &lt;enumeration value="SCHEMA"/>
 *     &lt;enumeration value="TABLE_VIEW"/>
 *     &lt;enumeration value="VALUE_COLUMN"/>
 *     &lt;enumeration value="BUTTON_GROUP"/>
 *     &lt;enumeration value="GROUP"/>
 *     &lt;enumeration value="TAB_GROUP"/>
 *     &lt;enumeration value="TAB"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "attributeEditorHint")
@XmlEnum
public enum AttributeEditorHint {

    CHECK,
    CHOICE,
    EDITABLE_CHOICE,
    LABEL,
    PASSWORD,
    RADIO,
    TEXT,
    TEXT_PANE,
    RADIO_TEXT,
    RADIO_SCHEMA,
    ENTRY_LABEL,
    CONNECTOR,
    DATA_SOURCE,
    DS_FILTER,
    FILE,
    JDBC_PROPERTIES,
    KEY_STORE,
    PATH,
    PIPELINE_PROCEDURE,
    PROCEDURE,
    PROCEDURE_PARAMETER,
    PROCEDURE_VIEW,
    SCHEMA,
    TABLE_VIEW,
    VALUE_COLUMN,
    BUTTON_GROUP,
    GROUP,
    TAB_GROUP,
    TAB;

    public String value() {
        return name();
    }

    public static AttributeEditorHint fromValue(String v) {
        return valueOf(v);
    }

}
