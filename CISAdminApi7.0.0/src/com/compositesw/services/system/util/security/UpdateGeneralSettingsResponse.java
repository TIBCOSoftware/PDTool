
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for updateGeneralSettingsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateGeneralSettingsResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="enablePAM" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="mayDisallowUser" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="logAuthFailures" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="logPerformance" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="assignModuleGroups" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateGeneralSettingsResponse", propOrder = {
    "enablePAM",
    "mayDisallowUser",
    "logAuthFailures",
    "logPerformance",
    "assignModuleGroups"
})
public class UpdateGeneralSettingsResponse
    extends BaseResponse
{

    protected boolean enablePAM;
    protected boolean mayDisallowUser;
    protected boolean logAuthFailures;
    protected boolean logPerformance;
    protected boolean assignModuleGroups;

    /**
     * Gets the value of the enablePAM property.
     * 
     */
    public boolean isEnablePAM() {
        return enablePAM;
    }

    /**
     * Sets the value of the enablePAM property.
     * 
     */
    public void setEnablePAM(boolean value) {
        this.enablePAM = value;
    }

    /**
     * Gets the value of the mayDisallowUser property.
     * 
     */
    public boolean isMayDisallowUser() {
        return mayDisallowUser;
    }

    /**
     * Sets the value of the mayDisallowUser property.
     * 
     */
    public void setMayDisallowUser(boolean value) {
        this.mayDisallowUser = value;
    }

    /**
     * Gets the value of the logAuthFailures property.
     * 
     */
    public boolean isLogAuthFailures() {
        return logAuthFailures;
    }

    /**
     * Sets the value of the logAuthFailures property.
     * 
     */
    public void setLogAuthFailures(boolean value) {
        this.logAuthFailures = value;
    }

    /**
     * Gets the value of the logPerformance property.
     * 
     */
    public boolean isLogPerformance() {
        return logPerformance;
    }

    /**
     * Sets the value of the logPerformance property.
     * 
     */
    public void setLogPerformance(boolean value) {
        this.logPerformance = value;
    }

    /**
     * Gets the value of the assignModuleGroups property.
     * 
     */
    public boolean isAssignModuleGroups() {
        return assignModuleGroups;
    }

    /**
     * Sets the value of the assignModuleGroups property.
     * 
     */
    public void setAssignModuleGroups(boolean value) {
        this.assignModuleGroups = value;
    }

}
