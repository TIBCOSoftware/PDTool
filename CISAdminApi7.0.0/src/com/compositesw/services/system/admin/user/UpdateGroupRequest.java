
package com.compositesw.services.system.admin.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateGroupRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateGroupRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/user}domainGroupRequest">
 *       &lt;sequence>
 *         &lt;element name="userNames" type="{http://www.compositesw.com/services/system/admin/user}domainMemberReferenceList" minOccurs="0"/>
 *         &lt;element name="explicitRights" type="{http://www.compositesw.com/services/system/admin/user}userRightsList" minOccurs="0"/>
 *         &lt;element name="annotation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateGroupRequest", propOrder = {
    "userNames",
    "explicitRights",
    "annotation"
})
public class UpdateGroupRequest
    extends DomainGroupRequest
{

    protected DomainMemberReferenceList userNames;
    protected String explicitRights;
    protected String annotation;

    /**
     * Gets the value of the userNames property.
     * 
     * @return
     *     possible object is
     *     {@link DomainMemberReferenceList }
     *     
     */
    public DomainMemberReferenceList getUserNames() {
        return userNames;
    }

    /**
     * Sets the value of the userNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link DomainMemberReferenceList }
     *     
     */
    public void setUserNames(DomainMemberReferenceList value) {
        this.userNames = value;
    }

    /**
     * Gets the value of the explicitRights property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExplicitRights() {
        return explicitRights;
    }

    /**
     * Sets the value of the explicitRights property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExplicitRights(String value) {
        this.explicitRights = value;
    }

    /**
     * Gets the value of the annotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * Sets the value of the annotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnotation(String value) {
        this.annotation = value;
    }

}
