
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.compositesw.services.system.admin.archive package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetArchiveContentsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "getArchiveContentsResponse");
    private final static QName _CreateImportArchive_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "createImportArchive");
    private final static QName _PerformArchiveImportResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "performArchiveImportResponse");
    private final static QName _CreateExportArchive_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "createExportArchive");
    private final static QName _UpdateArchiveImportSettings_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "updateArchiveImportSettings");
    private final static QName _GetArchiveImportReport_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "getArchiveImportReport");
    private final static QName _CreateExportArchiveResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "createExportArchiveResponse");
    private final static QName _PerformArchiveImport_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "performArchiveImport");
    private final static QName _GetArchiveExportData_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "getArchiveExportData");
    private final static QName _UpdateArchiveImportSettingsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "updateArchiveImportSettingsResponse");
    private final static QName _CancelArchive_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "cancelArchive");
    private final static QName _GetArchiveExportSettingsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "getArchiveExportSettingsResponse");
    private final static QName _CancelArchiveResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "cancelArchiveResponse");
    private final static QName _GetArchiveImportReportResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "getArchiveImportReportResponse");
    private final static QName _UpdateArchiveExportSettingsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "updateArchiveExportSettingsResponse");
    private final static QName _GetArchiveContents_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "getArchiveContents");
    private final static QName _GetArchiveExportSettings_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "getArchiveExportSettings");
    private final static QName _GetArchiveImportSettingsResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "getArchiveImportSettingsResponse");
    private final static QName _UpdateArchiveExportSettings_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "updateArchiveExportSettings");
    private final static QName _GetArchiveImportSettings_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "getArchiveImportSettings");
    private final static QName _CreateImportArchiveResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "createImportArchiveResponse");
    private final static QName _GetArchiveExportDataResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/archive", "getArchiveExportDataResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.compositesw.services.system.admin.archive
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ResourceAttribute }
     * 
     */
    public ResourceAttribute createResourceAttribute() {
        return new ResourceAttribute();
    }

    /**
     * Create an instance of {@link GetArchiveImportSettingsRequest }
     * 
     */
    public GetArchiveImportSettingsRequest createGetArchiveImportSettingsRequest() {
        return new GetArchiveImportSettingsRequest();
    }

    /**
     * Create an instance of {@link CreateImportArchiveRequest }
     * 
     */
    public CreateImportArchiveRequest createCreateImportArchiveRequest() {
        return new CreateImportArchiveRequest();
    }

    /**
     * Create an instance of {@link GetArchiveExportDataResponse }
     * 
     */
    public GetArchiveExportDataResponse createGetArchiveExportDataResponse() {
        return new GetArchiveExportDataResponse();
    }

    /**
     * Create an instance of {@link ImportArchiveRequest }
     * 
     */
    public ImportArchiveRequest createImportArchiveRequest() {
        return new ImportArchiveRequest();
    }

    /**
     * Create an instance of {@link UpdateArchiveExportSettingsRequest }
     * 
     */
    public UpdateArchiveExportSettingsRequest createUpdateArchiveExportSettingsRequest() {
        return new UpdateArchiveExportSettingsRequest();
    }

    /**
     * Create an instance of {@link ArchiveUsers }
     * 
     */
    public ArchiveUsers createArchiveUsers() {
        return new ArchiveUsers();
    }

    /**
     * Create an instance of {@link PathTypeMap }
     * 
     */
    public PathTypeMap createPathTypeMap() {
        return new PathTypeMap();
    }

    /**
     * Create an instance of {@link ArchiveRequest }
     * 
     */
    public ArchiveRequest createArchiveRequest() {
        return new ArchiveRequest();
    }

    /**
     * Create an instance of {@link PathTypeMapList }
     * 
     */
    public PathTypeMapList createPathTypeMapList() {
        return new PathTypeMapList();
    }

    /**
     * Create an instance of {@link GetArchiveExportDataRequest }
     * 
     */
    public GetArchiveExportDataRequest createGetArchiveExportDataRequest() {
        return new GetArchiveExportDataRequest();
    }

    /**
     * Create an instance of {@link GetArchiveImportSettingsResponse }
     * 
     */
    public GetArchiveImportSettingsResponse createGetArchiveImportSettingsResponse() {
        return new GetArchiveImportSettingsResponse();
    }

    /**
     * Create an instance of {@link ResourceAttributeNames }
     * 
     */
    public ResourceAttributeNames createResourceAttributeNames() {
        return new ResourceAttributeNames();
    }

    /**
     * Create an instance of {@link ArchiveDomainUserList }
     * 
     */
    public ArchiveDomainUserList createArchiveDomainUserList() {
        return new ArchiveDomainUserList();
    }

    /**
     * Create an instance of {@link GetArchiveContentsResponse }
     * 
     */
    public GetArchiveContentsResponse createGetArchiveContentsResponse() {
        return new GetArchiveContentsResponse();
    }

    /**
     * Create an instance of {@link CreateExportArchiveRequest }
     * 
     */
    public CreateExportArchiveRequest createCreateExportArchiveRequest() {
        return new CreateExportArchiveRequest();
    }

    /**
     * Create an instance of {@link ExportArchiveRequest }
     * 
     */
    public ExportArchiveRequest createExportArchiveRequest() {
        return new ExportArchiveRequest();
    }

    /**
     * Create an instance of {@link CancelArchiveRequest }
     * 
     */
    public CancelArchiveRequest createCancelArchiveRequest() {
        return new CancelArchiveRequest();
    }

    /**
     * Create an instance of {@link ArchiveDomainList }
     * 
     */
    public ArchiveDomainList createArchiveDomainList() {
        return new ArchiveDomainList();
    }

    /**
     * Create an instance of {@link CancelArchiveResponse }
     * 
     */
    public CancelArchiveResponse createCancelArchiveResponse() {
        return new CancelArchiveResponse();
    }

    /**
     * Create an instance of {@link GetArchiveExportSettingsRequest }
     * 
     */
    public GetArchiveExportSettingsRequest createGetArchiveExportSettingsRequest() {
        return new GetArchiveExportSettingsRequest();
    }

    /**
     * Create an instance of {@link ExportServerAttributes }
     * 
     */
    public ExportServerAttributes createExportServerAttributes() {
        return new ExportServerAttributes();
    }

    /**
     * Create an instance of {@link GetArchiveImportReportResponse }
     * 
     */
    public GetArchiveImportReportResponse createGetArchiveImportReportResponse() {
        return new GetArchiveImportReportResponse();
    }

    /**
     * Create an instance of {@link UpdateArchiveExportSettingsResponse }
     * 
     */
    public UpdateArchiveExportSettingsResponse createUpdateArchiveExportSettingsResponse() {
        return new UpdateArchiveExportSettingsResponse();
    }

    /**
     * Create an instance of {@link PerformArchiveImportResponse }
     * 
     */
    public PerformArchiveImportResponse createPerformArchiveImportResponse() {
        return new PerformArchiveImportResponse();
    }

    /**
     * Create an instance of {@link UserMapList }
     * 
     */
    public UserMapList createUserMapList() {
        return new UserMapList();
    }

    /**
     * Create an instance of {@link ExportResource }
     * 
     */
    public ExportResource createExportResource() {
        return new ExportResource();
    }

    /**
     * Create an instance of {@link ImportHints }
     * 
     */
    public ImportHints createImportHints() {
        return new ImportHints();
    }

    /**
     * Create an instance of {@link ArchiveReportResponse }
     * 
     */
    public ArchiveReportResponse createArchiveReportResponse() {
        return new ArchiveReportResponse();
    }

    /**
     * Create an instance of {@link ArchiveResources }
     * 
     */
    public ArchiveResources createArchiveResources() {
        return new ArchiveResources();
    }

    /**
     * Create an instance of {@link ArchiveDomainGroupList }
     * 
     */
    public ArchiveDomainGroupList createArchiveDomainGroupList() {
        return new ArchiveDomainGroupList();
    }

    /**
     * Create an instance of {@link ExportResourceList }
     * 
     */
    public ExportResourceList createExportResourceList() {
        return new ExportResourceList();
    }

    /**
     * Create an instance of {@link CreateExportArchiveResponse }
     * 
     */
    public CreateExportArchiveResponse createCreateExportArchiveResponse() {
        return new CreateExportArchiveResponse();
    }

    /**
     * Create an instance of {@link UpdateArchiveImportSettingsResponse }
     * 
     */
    public UpdateArchiveImportSettingsResponse createUpdateArchiveImportSettingsResponse() {
        return new UpdateArchiveImportSettingsResponse();
    }

    /**
     * Create an instance of {@link ExportCreateInfo }
     * 
     */
    public ExportCreateInfo createExportCreateInfo() {
        return new ExportCreateInfo();
    }

    /**
     * Create an instance of {@link GetArchiveImportReportRequest }
     * 
     */
    public GetArchiveImportReportRequest createGetArchiveImportReportRequest() {
        return new GetArchiveImportReportRequest();
    }

    /**
     * Create an instance of {@link UserMap }
     * 
     */
    public UserMap createUserMap() {
        return new UserMap();
    }

    /**
     * Create an instance of {@link GetArchiveContentsRequest }
     * 
     */
    public GetArchiveContentsRequest createGetArchiveContentsRequest() {
        return new GetArchiveContentsRequest();
    }

    /**
     * Create an instance of {@link ExportSettings }
     * 
     */
    public ExportSettings createExportSettings() {
        return new ExportSettings();
    }

    /**
     * Create an instance of {@link GetArchiveExportSettingsResponse }
     * 
     */
    public GetArchiveExportSettingsResponse createGetArchiveExportSettingsResponse() {
        return new GetArchiveExportSettingsResponse();
    }

    /**
     * Create an instance of {@link ArchiveContents }
     * 
     */
    public ArchiveContents createArchiveContents() {
        return new ArchiveContents();
    }

    /**
     * Create an instance of {@link ArchiveNamedGroupList }
     * 
     */
    public ArchiveNamedGroupList createArchiveNamedGroupList() {
        return new ArchiveNamedGroupList();
    }

    /**
     * Create an instance of {@link DomainUserPair }
     * 
     */
    public DomainUserPair createDomainUserPair() {
        return new DomainUserPair();
    }

    /**
     * Create an instance of {@link ImportSettings }
     * 
     */
    public ImportSettings createImportSettings() {
        return new ImportSettings();
    }

    /**
     * Create an instance of {@link UpdateArchiveImportSettingsRequest }
     * 
     */
    public UpdateArchiveImportSettingsRequest createUpdateArchiveImportSettingsRequest() {
        return new UpdateArchiveImportSettingsRequest();
    }

    /**
     * Create an instance of {@link PerformArchiveImportRequest }
     * 
     */
    public PerformArchiveImportRequest createPerformArchiveImportRequest() {
        return new PerformArchiveImportRequest();
    }

    /**
     * Create an instance of {@link CreateImportArchiveResponse }
     * 
     */
    public CreateImportArchiveResponse createCreateImportArchiveResponse() {
        return new CreateImportArchiveResponse();
    }

    /**
     * Create an instance of {@link ArchiveNamedUserList }
     * 
     */
    public ArchiveNamedUserList createArchiveNamedUserList() {
        return new ArchiveNamedUserList();
    }

    /**
     * Create an instance of {@link ResourceAttributeList }
     * 
     */
    public ResourceAttributeList createResourceAttributeList() {
        return new ResourceAttributeList();
    }

    /**
     * Create an instance of {@link ResourceAttributeNamesList }
     * 
     */
    public ResourceAttributeNamesList createResourceAttributeNamesList() {
        return new ResourceAttributeNamesList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetArchiveContentsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "getArchiveContentsResponse")
    public JAXBElement<GetArchiveContentsResponse> createGetArchiveContentsResponse(GetArchiveContentsResponse value) {
        return new JAXBElement<GetArchiveContentsResponse>(_GetArchiveContentsResponse_QNAME, GetArchiveContentsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateImportArchiveRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "createImportArchive")
    public JAXBElement<CreateImportArchiveRequest> createCreateImportArchive(CreateImportArchiveRequest value) {
        return new JAXBElement<CreateImportArchiveRequest>(_CreateImportArchive_QNAME, CreateImportArchiveRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PerformArchiveImportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "performArchiveImportResponse")
    public JAXBElement<PerformArchiveImportResponse> createPerformArchiveImportResponse(PerformArchiveImportResponse value) {
        return new JAXBElement<PerformArchiveImportResponse>(_PerformArchiveImportResponse_QNAME, PerformArchiveImportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateExportArchiveRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "createExportArchive")
    public JAXBElement<CreateExportArchiveRequest> createCreateExportArchive(CreateExportArchiveRequest value) {
        return new JAXBElement<CreateExportArchiveRequest>(_CreateExportArchive_QNAME, CreateExportArchiveRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateArchiveImportSettingsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "updateArchiveImportSettings")
    public JAXBElement<UpdateArchiveImportSettingsRequest> createUpdateArchiveImportSettings(UpdateArchiveImportSettingsRequest value) {
        return new JAXBElement<UpdateArchiveImportSettingsRequest>(_UpdateArchiveImportSettings_QNAME, UpdateArchiveImportSettingsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetArchiveImportReportRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "getArchiveImportReport")
    public JAXBElement<GetArchiveImportReportRequest> createGetArchiveImportReport(GetArchiveImportReportRequest value) {
        return new JAXBElement<GetArchiveImportReportRequest>(_GetArchiveImportReport_QNAME, GetArchiveImportReportRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateExportArchiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "createExportArchiveResponse")
    public JAXBElement<CreateExportArchiveResponse> createCreateExportArchiveResponse(CreateExportArchiveResponse value) {
        return new JAXBElement<CreateExportArchiveResponse>(_CreateExportArchiveResponse_QNAME, CreateExportArchiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PerformArchiveImportRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "performArchiveImport")
    public JAXBElement<PerformArchiveImportRequest> createPerformArchiveImport(PerformArchiveImportRequest value) {
        return new JAXBElement<PerformArchiveImportRequest>(_PerformArchiveImport_QNAME, PerformArchiveImportRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetArchiveExportDataRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "getArchiveExportData")
    public JAXBElement<GetArchiveExportDataRequest> createGetArchiveExportData(GetArchiveExportDataRequest value) {
        return new JAXBElement<GetArchiveExportDataRequest>(_GetArchiveExportData_QNAME, GetArchiveExportDataRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateArchiveImportSettingsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "updateArchiveImportSettingsResponse")
    public JAXBElement<UpdateArchiveImportSettingsResponse> createUpdateArchiveImportSettingsResponse(UpdateArchiveImportSettingsResponse value) {
        return new JAXBElement<UpdateArchiveImportSettingsResponse>(_UpdateArchiveImportSettingsResponse_QNAME, UpdateArchiveImportSettingsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelArchiveRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "cancelArchive")
    public JAXBElement<CancelArchiveRequest> createCancelArchive(CancelArchiveRequest value) {
        return new JAXBElement<CancelArchiveRequest>(_CancelArchive_QNAME, CancelArchiveRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetArchiveExportSettingsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "getArchiveExportSettingsResponse")
    public JAXBElement<GetArchiveExportSettingsResponse> createGetArchiveExportSettingsResponse(GetArchiveExportSettingsResponse value) {
        return new JAXBElement<GetArchiveExportSettingsResponse>(_GetArchiveExportSettingsResponse_QNAME, GetArchiveExportSettingsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelArchiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "cancelArchiveResponse")
    public JAXBElement<CancelArchiveResponse> createCancelArchiveResponse(CancelArchiveResponse value) {
        return new JAXBElement<CancelArchiveResponse>(_CancelArchiveResponse_QNAME, CancelArchiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetArchiveImportReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "getArchiveImportReportResponse")
    public JAXBElement<GetArchiveImportReportResponse> createGetArchiveImportReportResponse(GetArchiveImportReportResponse value) {
        return new JAXBElement<GetArchiveImportReportResponse>(_GetArchiveImportReportResponse_QNAME, GetArchiveImportReportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateArchiveExportSettingsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "updateArchiveExportSettingsResponse")
    public JAXBElement<UpdateArchiveExportSettingsResponse> createUpdateArchiveExportSettingsResponse(UpdateArchiveExportSettingsResponse value) {
        return new JAXBElement<UpdateArchiveExportSettingsResponse>(_UpdateArchiveExportSettingsResponse_QNAME, UpdateArchiveExportSettingsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetArchiveContentsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "getArchiveContents")
    public JAXBElement<GetArchiveContentsRequest> createGetArchiveContents(GetArchiveContentsRequest value) {
        return new JAXBElement<GetArchiveContentsRequest>(_GetArchiveContents_QNAME, GetArchiveContentsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetArchiveExportSettingsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "getArchiveExportSettings")
    public JAXBElement<GetArchiveExportSettingsRequest> createGetArchiveExportSettings(GetArchiveExportSettingsRequest value) {
        return new JAXBElement<GetArchiveExportSettingsRequest>(_GetArchiveExportSettings_QNAME, GetArchiveExportSettingsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetArchiveImportSettingsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "getArchiveImportSettingsResponse")
    public JAXBElement<GetArchiveImportSettingsResponse> createGetArchiveImportSettingsResponse(GetArchiveImportSettingsResponse value) {
        return new JAXBElement<GetArchiveImportSettingsResponse>(_GetArchiveImportSettingsResponse_QNAME, GetArchiveImportSettingsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateArchiveExportSettingsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "updateArchiveExportSettings")
    public JAXBElement<UpdateArchiveExportSettingsRequest> createUpdateArchiveExportSettings(UpdateArchiveExportSettingsRequest value) {
        return new JAXBElement<UpdateArchiveExportSettingsRequest>(_UpdateArchiveExportSettings_QNAME, UpdateArchiveExportSettingsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetArchiveImportSettingsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "getArchiveImportSettings")
    public JAXBElement<GetArchiveImportSettingsRequest> createGetArchiveImportSettings(GetArchiveImportSettingsRequest value) {
        return new JAXBElement<GetArchiveImportSettingsRequest>(_GetArchiveImportSettings_QNAME, GetArchiveImportSettingsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateImportArchiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "createImportArchiveResponse")
    public JAXBElement<CreateImportArchiveResponse> createCreateImportArchiveResponse(CreateImportArchiveResponse value) {
        return new JAXBElement<CreateImportArchiveResponse>(_CreateImportArchiveResponse_QNAME, CreateImportArchiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetArchiveExportDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/archive", name = "getArchiveExportDataResponse")
    public JAXBElement<GetArchiveExportDataResponse> createGetArchiveExportDataResponse(GetArchiveExportDataResponse value) {
        return new JAXBElement<GetArchiveExportDataResponse>(_GetArchiveExportDataResponse_QNAME, GetArchiveExportDataResponse.class, null, value);
    }

}
