
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for exportArchiveRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exportArchiveRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="archiveId" type="{http://www.compositesw.com/services/system/admin/archive}exportArchiveId"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exportArchiveRequest", propOrder = {
    "archiveId"
})
@XmlSeeAlso({
    UpdateArchiveExportSettingsRequest.class,
    GetArchiveExportDataRequest.class
})
public class ExportArchiveRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected String archiveId;

    /**
     * Gets the value of the archiveId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArchiveId() {
        return archiveId;
    }

    /**
     * Sets the value of the archiveId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArchiveId(String value) {
        this.archiveId = value;
    }

}
