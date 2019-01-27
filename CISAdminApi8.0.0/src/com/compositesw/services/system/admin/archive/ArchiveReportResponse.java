
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;
import com.compositesw.services.system.util.common.MessageEntryList;
import com.compositesw.services.system.util.common.OperationStatus;


/**
 * <p>Java class for archiveReportResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="archiveReportResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="status" type="{http://www.compositesw.com/services/system/util/common}operationStatus"/>
 *         &lt;element name="archiveReport" type="{http://www.compositesw.com/services/system/util/common}messageEntryList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "archiveReportResponse", propOrder = {
    "status",
    "archiveReport"
})
@XmlSeeAlso({
    PerformArchiveImportResponse.class,
    GetArchiveImportReportResponse.class,
    GetArchiveExportDataResponse.class,
    CancelArchiveResponse.class
})
public class ArchiveReportResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected OperationStatus status;
    protected MessageEntryList archiveReport;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link OperationStatus }
     *     
     */
    public OperationStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationStatus }
     *     
     */
    public void setStatus(OperationStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the archiveReport property.
     * 
     * @return
     *     possible object is
     *     {@link MessageEntryList }
     *     
     */
    public MessageEntryList getArchiveReport() {
        return archiveReport;
    }

    /**
     * Sets the value of the archiveReport property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageEntryList }
     *     
     */
    public void setArchiveReport(MessageEntryList value) {
        this.archiveReport = value;
    }

}
