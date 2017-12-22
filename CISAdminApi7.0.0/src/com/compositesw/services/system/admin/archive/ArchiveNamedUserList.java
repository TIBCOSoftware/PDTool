
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.Marker;
import com.compositesw.services.system.util.common.NameList;


/**
 * <p>Java class for archiveNamedUserList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="archiveNamedUserList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="all" type="{http://www.compositesw.com/services/system/util/common}marker" minOccurs="0"/>
 *         &lt;element name="userNames" type="{http://www.compositesw.com/services/system/util/common}nameList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "archiveNamedUserList", propOrder = {
    "name",
    "all",
    "userNames"
})
public class ArchiveNamedUserList {

    @XmlElement(required = true)
    protected String name;
    protected Marker all;
    protected NameList userNames;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the all property.
     * 
     * @return
     *     possible object is
     *     {@link Marker }
     *     
     */
    public Marker getAll() {
        return all;
    }

    /**
     * Sets the value of the all property.
     * 
     * @param value
     *     allowed object is
     *     {@link Marker }
     *     
     */
    public void setAll(Marker value) {
        this.all = value;
    }

    /**
     * Gets the value of the userNames property.
     * 
     * @return
     *     possible object is
     *     {@link NameList }
     *     
     */
    public NameList getUserNames() {
        return userNames;
    }

    /**
     * Sets the value of the userNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameList }
     *     
     */
    public void setUserNames(NameList value) {
        this.userNames = value;
    }

}
