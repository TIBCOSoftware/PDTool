
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateArchiveExportSettingsRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateArchiveExportSettingsRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/archive}exportArchiveRequest">
 *       &lt;sequence>
 *         &lt;element name="settings" type="{http://www.compositesw.com/services/system/admin/archive}exportSettings"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateArchiveExportSettingsRequest", propOrder = {
    "settings"
})
public class UpdateArchiveExportSettingsRequest
    extends ExportArchiveRequest
{

    @XmlElement(required = true)
    protected ExportSettings settings;

    /**
     * Gets the value of the settings property.
     * 
     * @return
     *     possible object is
     *     {@link ExportSettings }
     *     
     */
    public ExportSettings getSettings() {
        return settings;
    }

    /**
     * Sets the value of the settings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportSettings }
     *     
     */
    public void setSettings(ExportSettings value) {
        this.settings = value;
    }

}
