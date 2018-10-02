
package com.compositesw.services.system.util.common;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.compositesw.services.system.util.common package. 
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

    private final static QName _Fault_QNAME = new QName("http://www.compositesw.com/services/system/util/common", "fault");
    private final static QName _CancelServerTask_QNAME = new QName("http://www.compositesw.com/services/system/util/common", "cancelServerTask");
    private final static QName _CancelServerTaskResponse_QNAME = new QName("http://www.compositesw.com/services/system/util/common", "cancelServerTaskResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.compositesw.services.system.util.common
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ServerTaskRequest }
     * 
     */
    public ServerTaskRequest createServerTaskRequest() {
        return new ServerTaskRequest();
    }

    /**
     * Create an instance of {@link Fault }
     * 
     */
    public Fault createFault() {
        return new Fault();
    }

    /**
     * Create an instance of {@link AttributeDef }
     * 
     */
    public AttributeDef createAttributeDef() {
        return new AttributeDef();
    }

    /**
     * Create an instance of {@link Attribute }
     * 
     */
    public Attribute createAttribute() {
        return new Attribute();
    }

    /**
     * Create an instance of {@link CancelServerTaskResponse }
     * 
     */
    public CancelServerTaskResponse createCancelServerTaskResponse() {
        return new CancelServerTaskResponse();
    }

    /**
     * Create an instance of {@link MessageEntry }
     * 
     */
    public MessageEntry createMessageEntry() {
        return new MessageEntry();
    }

    /**
     * Create an instance of {@link ServerTaskResponse }
     * 
     */
    public ServerTaskResponse createServerTaskResponse() {
        return new ServerTaskResponse();
    }

    /**
     * Create an instance of {@link DataType.SqlType }
     * 
     */
    public DataType.SqlType createDataTypeSqlType() {
        return new DataType.SqlType();
    }

    /**
     * Create an instance of {@link CancelServerTaskRequest }
     * 
     */
    public CancelServerTaskRequest createCancelServerTaskRequest() {
        return new CancelServerTaskRequest();
    }

    /**
     * Create an instance of {@link AttributeTypeValue }
     * 
     */
    public AttributeTypeValue createAttributeTypeValue() {
        return new AttributeTypeValue();
    }

    /**
     * Create an instance of {@link AttributeTypeValueMap }
     * 
     */
    public AttributeTypeValueMap createAttributeTypeValueMap() {
        return new AttributeTypeValueMap();
    }

    /**
     * Create an instance of {@link Marker }
     * 
     */
    public Marker createMarker() {
        return new Marker();
    }

    /**
     * Create an instance of {@link MessageList }
     * 
     */
    public MessageList createMessageList() {
        return new MessageList();
    }

    /**
     * Create an instance of {@link AttributeComplexValue }
     * 
     */
    public AttributeComplexValue createAttributeComplexValue() {
        return new AttributeComplexValue();
    }

    /**
     * Create an instance of {@link DataType.PseudoType }
     * 
     */
    public DataType.PseudoType createDataTypePseudoType() {
        return new DataType.PseudoType();
    }

    /**
     * Create an instance of {@link AttributeTypeValueList }
     * 
     */
    public AttributeTypeValueList createAttributeTypeValueList() {
        return new AttributeTypeValueList();
    }

    /**
     * Create an instance of {@link BaseResponse }
     * 
     */
    public BaseResponse createBaseResponse() {
        return new BaseResponse();
    }

    /**
     * Create an instance of {@link ServerTaskResultResponse }
     * 
     */
    public ServerTaskResultResponse createServerTaskResultResponse() {
        return new ServerTaskResultResponse();
    }

    /**
     * Create an instance of {@link DataType.XmlType }
     * 
     */
    public DataType.XmlType createDataTypeXmlType() {
        return new DataType.XmlType();
    }

    /**
     * Create an instance of {@link BaseRequest }
     * 
     */
    public BaseRequest createBaseRequest() {
        return new BaseRequest();
    }

    /**
     * Create an instance of {@link DataType }
     * 
     */
    public DataType createDataType() {
        return new DataType();
    }

    /**
     * Create an instance of {@link PrincipalList }
     * 
     */
    public PrincipalList createPrincipalList() {
        return new PrincipalList();
    }

    /**
     * Create an instance of {@link NameList }
     * 
     */
    public NameList createNameList() {
        return new NameList();
    }

    /**
     * Create an instance of {@link Page }
     * 
     */
    public Page createPage() {
        return new Page();
    }

    /**
     * Create an instance of {@link MessageEntryList }
     * 
     */
    public MessageEntryList createMessageEntryList() {
        return new MessageEntryList();
    }

    /**
     * Create an instance of {@link AttributeDefList }
     * 
     */
    public AttributeDefList createAttributeDefList() {
        return new AttributeDefList();
    }

    /**
     * Create an instance of {@link ServerTaskResultRequest }
     * 
     */
    public ServerTaskResultRequest createServerTaskResultRequest() {
        return new ServerTaskResultRequest();
    }

    /**
     * Create an instance of {@link AttributeList }
     * 
     */
    public AttributeList createAttributeList() {
        return new AttributeList();
    }

    /**
     * Create an instance of {@link AttributeSimpleValueList }
     * 
     */
    public AttributeSimpleValueList createAttributeSimpleValueList() {
        return new AttributeSimpleValueList();
    }

    /**
     * Create an instance of {@link AttributeTypeValueMap.Entry }
     * 
     */
    public AttributeTypeValueMap.Entry createAttributeTypeValueMapEntry() {
        return new AttributeTypeValueMap.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Fault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/util/common", name = "fault")
    public JAXBElement<Fault> createFault(Fault value) {
        return new JAXBElement<Fault>(_Fault_QNAME, Fault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelServerTaskRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/util/common", name = "cancelServerTask")
    public JAXBElement<CancelServerTaskRequest> createCancelServerTask(CancelServerTaskRequest value) {
        return new JAXBElement<CancelServerTaskRequest>(_CancelServerTask_QNAME, CancelServerTaskRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelServerTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/util/common", name = "cancelServerTaskResponse")
    public JAXBElement<CancelServerTaskResponse> createCancelServerTaskResponse(CancelServerTaskResponse value) {
        return new JAXBElement<CancelServerTaskResponse>(_CancelServerTaskResponse_QNAME, CancelServerTaskResponse.class, null, value);
    }

}
