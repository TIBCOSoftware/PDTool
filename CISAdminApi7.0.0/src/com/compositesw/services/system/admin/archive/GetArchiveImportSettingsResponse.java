
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getArchiveImportSettingsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getArchiveImportSettingsResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
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
@XmlType(name = "getArchiveImportSettingsResponse", propOrder = {
    "settings"
})
public class GetArchiveImportSettingsResponse
    extends BaseResponse
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
