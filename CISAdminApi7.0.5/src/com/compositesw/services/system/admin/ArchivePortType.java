
package com.compositesw.services.system.admin;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import com.compositesw.services.system.admin.archive.ArchiveContents;
import com.compositesw.services.system.admin.archive.ExportSettings;
import com.compositesw.services.system.admin.archive.ImportSettings;
import com.compositesw.services.system.util.common.MessageEntryList;
import com.compositesw.services.system.util.common.OperationStatus;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.1-b01-
 * Generated source version: 2.2
 * 
 */
@WebService(name = "archivePortType", targetNamespace = "http://www.compositesw.com/services/system/admin")
@XmlSeeAlso({
    com.compositesw.services.system.admin.resource.ObjectFactory.class,
    com.compositesw.services.system.admin.archive.ObjectFactory.class,
    com.compositesw.services.system.util.common.ObjectFactory.class,
    com.compositesw.services.system.util.security.ObjectFactory.class,
    com.compositesw.services.system.admin.execute.ObjectFactory.class,
    com.compositesw.services.system.admin.server.ObjectFactory.class,
    com.compositesw.services.system.admin.user.ObjectFactory.class
})
public interface ArchivePortType {


    /**
     * 
     * @param archiveReport
     * @param archiveId
     * @param status
     * @throws CancelArchiveSoapFault
     */
    @WebMethod(action = "cancelArchive")
    @RequestWrapper(localName = "cancelArchive", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.CancelArchiveRequest")
    @ResponseWrapper(localName = "cancelArchiveResponse", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.CancelArchiveResponse")
    public void cancelArchive(
        @WebParam(name = "archiveId", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
        String archiveId,
        @WebParam(name = "status", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", mode = WebParam.Mode.OUT)
        Holder<OperationStatus> status,
        @WebParam(name = "archiveReport", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", mode = WebParam.Mode.OUT)
        Holder<MessageEntryList> archiveReport)
        throws CancelArchiveSoapFault
    ;

    /**
     * 
     * @param settings
     * @return
     *     returns java.lang.String
     * @throws CreateExportArchiveSoapFault
     */
    @WebMethod(action = "createExportArchive")
    @WebResult(name = "archiveId", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
    @RequestWrapper(localName = "createExportArchive", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.CreateExportArchiveRequest")
    @ResponseWrapper(localName = "createExportArchiveResponse", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.CreateExportArchiveResponse")
    public String createExportArchive(
        @WebParam(name = "settings", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
        ExportSettings settings)
        throws CreateExportArchiveSoapFault
    ;

    /**
     * 
     * @param data
     * @return
     *     returns java.lang.String
     * @throws CreateImportArchiveSoapFault
     */
    @WebMethod(action = "createImportArchive")
    @WebResult(name = "archiveId", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
    @RequestWrapper(localName = "createImportArchive", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.CreateImportArchiveRequest")
    @ResponseWrapper(localName = "createImportArchiveResponse", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.CreateImportArchiveResponse")
    public String createImportArchive(
        @WebParam(name = "data", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
        byte[] data)
        throws CreateImportArchiveSoapFault
    ;

    /**
     * 
     * @param archiveId
     * @return
     *     returns com.compositesw.services.system.admin.archive.ArchiveContents
     * @throws GetArchiveContentsSoapFault
     */
    @WebMethod(action = "getArchiveContents")
    @WebResult(name = "contents", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
    @RequestWrapper(localName = "getArchiveContents", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.GetArchiveContentsRequest")
    @ResponseWrapper(localName = "getArchiveContentsResponse", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.GetArchiveContentsResponse")
    public ArchiveContents getArchiveContents(
        @WebParam(name = "archiveId", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
        String archiveId)
        throws GetArchiveContentsSoapFault
    ;

    /**
     * 
     * @param data
     * @param maxBytes
     * @param archiveReport
     * @param archiveId
     * @param status
     * @throws GetArchiveExportDataSoapFault
     */
    @WebMethod(action = "getArchiveExportData")
    @RequestWrapper(localName = "getArchiveExportData", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.GetArchiveExportDataRequest")
    @ResponseWrapper(localName = "getArchiveExportDataResponse", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.GetArchiveExportDataResponse")
    public void getArchiveExportData(
        @WebParam(name = "archiveId", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
        String archiveId,
        @WebParam(name = "maxBytes", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
        Integer maxBytes,
        @WebParam(name = "status", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", mode = WebParam.Mode.OUT)
        Holder<OperationStatus> status,
        @WebParam(name = "archiveReport", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", mode = WebParam.Mode.OUT)
        Holder<MessageEntryList> archiveReport,
        @WebParam(name = "data", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", mode = WebParam.Mode.OUT)
        Holder<byte[]> data)
        throws GetArchiveExportDataSoapFault
    ;

    /**
     * 
     * @param archiveId
     * @return
     *     returns com.compositesw.services.system.admin.archive.ExportSettings
     * @throws GetArchiveExportSettingsSoapFault
     */
    @WebMethod(action = "getArchiveExportSettings")
    @WebResult(name = "settings", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
    @RequestWrapper(localName = "getArchiveExportSettings", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.GetArchiveExportSettingsRequest")
    @ResponseWrapper(localName = "getArchiveExportSettingsResponse", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.GetArchiveExportSettingsResponse")
    public ExportSettings getArchiveExportSettings(
        @WebParam(name = "archiveId", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
        String archiveId)
        throws GetArchiveExportSettingsSoapFault
    ;

    /**
     * 
     * @param isBlocking
     * @param archiveReport
     * @param archiveId
     * @param status
     * @throws GetArchiveImportReportSoapFault
     */
    @WebMethod(action = "getArchiveImportReport")
    @RequestWrapper(localName = "getArchiveImportReport", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.GetArchiveImportReportRequest")
    @ResponseWrapper(localName = "getArchiveImportReportResponse", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.GetArchiveImportReportResponse")
    public void getArchiveImportReport(
        @WebParam(name = "archiveId", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
        String archiveId,
        @WebParam(name = "isBlocking", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
        boolean isBlocking,
        @WebParam(name = "status", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", mode = WebParam.Mode.OUT)
        Holder<OperationStatus> status,
        @WebParam(name = "archiveReport", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", mode = WebParam.Mode.OUT)
        Holder<MessageEntryList> archiveReport)
        throws GetArchiveImportReportSoapFault
    ;

    /**
     * 
     * @param archiveId
     * @return
     *     returns com.compositesw.services.system.admin.archive.ImportSettings
     * @throws GetArchiveImportSettingsSoapFault
     */
    @WebMethod(action = "getArchiveImportSettings")
    @WebResult(name = "settings", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
    @RequestWrapper(localName = "getArchiveImportSettings", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.GetArchiveImportSettingsRequest")
    @ResponseWrapper(localName = "getArchiveImportSettingsResponse", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.GetArchiveImportSettingsResponse")
    public ImportSettings getArchiveImportSettings(
        @WebParam(name = "archiveId", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
        String archiveId)
        throws GetArchiveImportSettingsSoapFault
    ;

    /**
     * 
     * @param isBlocking
     * @param archiveReport
     * @param archiveId
     * @param status
     * @throws PerformArchiveImportSoapFault
     */
    @WebMethod(action = "performArchiveImport")
    @RequestWrapper(localName = "performArchiveImport", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.PerformArchiveImportRequest")
    @ResponseWrapper(localName = "performArchiveImportResponse", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.PerformArchiveImportResponse")
    public void performArchiveImport(
        @WebParam(name = "archiveId", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
        String archiveId,
        @WebParam(name = "isBlocking", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
        boolean isBlocking,
        @WebParam(name = "status", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", mode = WebParam.Mode.OUT)
        Holder<OperationStatus> status,
        @WebParam(name = "archiveReport", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", mode = WebParam.Mode.OUT)
        Holder<MessageEntryList> archiveReport)
        throws PerformArchiveImportSoapFault
    ;

    /**
     * 
     * @param settings
     * @param archiveId
     * @throws UpdateArchiveExportSettingsSoapFault
     */
    @WebMethod(action = "updateArchiveExportSettings")
    @RequestWrapper(localName = "updateArchiveExportSettings", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.UpdateArchiveExportSettingsRequest")
    @ResponseWrapper(localName = "updateArchiveExportSettingsResponse", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.UpdateArchiveExportSettingsResponse")
    public void updateArchiveExportSettings(
        @WebParam(name = "archiveId", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
        String archiveId,
        @WebParam(name = "settings", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
        ExportSettings settings)
        throws UpdateArchiveExportSettingsSoapFault
    ;

    /**
     * 
     * @param settings
     * @param archiveId
     * @throws UpdateArchiveImportSettingsSoapFault
     */
    @WebMethod(action = "updateArchiveImportSettings")
    @RequestWrapper(localName = "updateArchiveImportSettings", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.UpdateArchiveImportSettingsRequest")
    @ResponseWrapper(localName = "updateArchiveImportSettingsResponse", targetNamespace = "http://www.compositesw.com/services/system/admin/archive", className = "com.compositesw.services.system.admin.archive.UpdateArchiveImportSettingsResponse")
    public void updateArchiveImportSettings(
        @WebParam(name = "archiveId", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
        String archiveId,
        @WebParam(name = "settings", targetNamespace = "http://www.compositesw.com/services/system/admin/archive")
        ImportSettings settings)
        throws UpdateArchiveImportSettingsSoapFault
    ;

}
