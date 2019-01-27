
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for updateGeneralSettingsRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateGeneralSettingsRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="enablePAM" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="mayDisallowUser" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="logAuthFailures" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="logPerformance" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="assignModuleGroups" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateGeneralSettingsRequest", propOrder = {
    "enablePAM",
    "mayDisallowUser",
    "logAuthFailures",
    "logPerformance",
    "assignModuleGroups"
})
public class UpdateGeneralSettingsRequest
    extends BaseRequest
{

    protected Boolean enablePAM;
    protected Boolean mayDisallowUser;
    protected Boolean logAuthFailures;
    protected Boolean logPerformance;
    protected Boolean assignModuleGroups;

    /**
     * Gets the value of the enablePAM property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnablePAM() {
        return enablePAM;
    }

    /**
     * Sets the value of the enablePAM property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnablePAM(Boolean value) {
        this.enablePAM = value;
    }

    /**
     * Gets the value of the mayDisallowUser property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMayDisallowUser() {
        return mayDisallowUser;
    }

    /**
     * Sets the value of the mayDisallowUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMayDisallowUser(Boolean value) {
        this.mayDisallowUser = value;
    }

    /**
     * Gets the value of the logAuthFailures property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLogAuthFailures() {
        return logAuthFailures;
    }

    /**
     * Sets the value of the logAuthFailures property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLogAuthFailures(Boolean value) {
        this.logAuthFailures = value;
    }

    /**
     * Gets the value of the logPerformance property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLogPerformance() {
        return logPerformance;
    }

    /**
     * Sets the value of the logPerformance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLogPerformance(Boolean value) {
        this.logPerformance = value;
    }

    /**
     * Gets the value of the assignModuleGroups property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAssignModuleGroups() {
        return assignModuleGroups;
    }

    /**
     * Sets the value of the assignModuleGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAssignModuleGroups(Boolean value) {
        this.assignModuleGroups = value;
    }

}
