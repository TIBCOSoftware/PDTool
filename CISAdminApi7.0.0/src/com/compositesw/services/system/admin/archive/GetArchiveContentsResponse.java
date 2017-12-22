
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getArchiveContentsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getArchiveContentsResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="contents" type="{http://www.compositesw.com/services/system/admin/archive}archiveContents"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getArchiveContentsResponse", propOrder = {
    "contents"
})
public class GetArchiveContentsResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected ArchiveContents contents;

    /**
     * Gets the value of the contents property.
     * 
     * @return
     *     possible object is
     *     {@link ArchiveContents }
     *     
     */
    public ArchiveContents getContents() {
        return contents;
    }

    /**
     * Sets the value of the contents property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArchiveContents }
     *     
     */
    public void setContents(ArchiveContents value) {
        this.contents = value;
    }

}
