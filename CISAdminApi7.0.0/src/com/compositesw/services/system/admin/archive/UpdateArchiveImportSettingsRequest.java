
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateArchiveImportSettingsRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateArchiveImportSettingsRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/archive}importArchiveRequest">
 *       &lt;sequence>
 *         &lt;element name="settings" type="{http://www.compositesw.com/services/system/admin/archive}importSettings"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateArchiveImportSettingsRequest", propOrder = {
    "settings"
})
public class UpdateArchiveImportSettingsRequest
    extends ImportArchiveRequest
{

    @XmlElement(required = true)
    protected ImportSettings settings;

    /**
     * Gets the value of the settings property.
     * 
     * @return
     *     possible object is
     *     {@link ImportSettings }
     *     
     */
    public ImportSettings getSettings() {
        return settings;
    }

    /**
     * Sets the value of the settings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImportSettings }
     *     
     */
    public void setSettings(ImportSettings value) {
        this.settings = value;
    }

}
