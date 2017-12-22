
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getArchiveExportDataRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getArchiveExportDataRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/archive}exportArchiveRequest">
 *       &lt;sequence>
 *         &lt;element name="maxBytes" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getArchiveExportDataRequest", propOrder = {
    "maxBytes"
})
public class GetArchiveExportDataRequest
    extends ExportArchiveRequest
{

    protected Integer maxBytes;

    /**
     * Gets the value of the maxBytes property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxBytes() {
        return maxBytes;
    }

    /**
     * Sets the value of the maxBytes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxBytes(Integer value) {
        this.maxBytes = value;
    }

}
