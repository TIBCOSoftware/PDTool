
package com.compositesw.services.system.admin.execute;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.compositesw.services.system.admin.execute package. 
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

    private final static QName _GetResourcePlanResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "getResourcePlanResponse");
    private final static QName _CloseResult_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "closeResult");
    private final static QName _ExecutePreparedSqlResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "executePreparedSqlResponse");
    private final static QName _ExecuteProcedure_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "executeProcedure");
    private final static QName _ExecuteSqlScriptResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "executeSqlScriptResponse");
    private final static QName _ParseSqlQueryResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "parseSqlQueryResponse");
    private final static QName _CloseResultResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "closeResultResponse");
    private final static QName _ExecuteProcedureResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "executeProcedureResponse");
    private final static QName _ExecutePreparedSql_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "executePreparedSql");
    private final static QName _ExecuteSql_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "executeSql");
    private final static QName _GetSqlPlan_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "getSqlPlan");
    private final static QName _ExecuteNativeSqlResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "executeNativeSqlResponse");
    private final static QName _GetProceduralResultResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "getProceduralResultResponse");
    private final static QName _GetResultSetPlan_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "getResultSetPlan");
    private final static QName _GetSqlPlanResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "getSqlPlanResponse");
    private final static QName _GetProceduralResult_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "getProceduralResult");
    private final static QName _ParseSqlQuery_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "parseSqlQuery");
    private final static QName _GetTabularResult_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "getTabularResult");
    private final static QName _GetResultSetPlanResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "getResultSetPlanResponse");
    private final static QName _GetTabularResultResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "getTabularResultResponse");
    private final static QName _GetResourcePlan_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "getResourcePlan");
    private final static QName _ExecuteNativeSql_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "executeNativeSql");
    private final static QName _ExecuteSqlScript_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "executeSqlScript");
    private final static QName _ExecuteSqlResponse_QNAME = new QName("http://www.compositesw.com/services/system/admin/execute", "executeSqlResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.compositesw.services.system.admin.execute
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ExecutePreparedSqlRequest }
     * 
     */
    public ExecutePreparedSqlRequest createExecutePreparedSqlRequest() {
        return new ExecutePreparedSqlRequest();
    }

    /**
     * Create an instance of {@link ExecuteProcedureRequest }
     * 
     */
    public ExecuteProcedureRequest createExecuteProcedureRequest() {
        return new ExecuteProcedureRequest();
    }

    /**
     * Create an instance of {@link GetResultSetPlanResponse }
     * 
     */
    public GetResultSetPlanResponse createGetResultSetPlanResponse() {
        return new GetResultSetPlanResponse();
    }

    /**
     * Create an instance of {@link ExecuteNativeSqlRequest }
     * 
     */
    public ExecuteNativeSqlRequest createExecuteNativeSqlRequest() {
        return new ExecuteNativeSqlRequest();
    }

    /**
     * Create an instance of {@link ParseSqlQueryRequest }
     * 
     */
    public ParseSqlQueryRequest createParseSqlQueryRequest() {
        return new ParseSqlQueryRequest();
    }

    /**
     * Create an instance of {@link BaseExecuteSqlResponse }
     * 
     */
    public BaseExecuteSqlResponse createBaseExecuteSqlResponse() {
        return new BaseExecuteSqlResponse();
    }

    /**
     * Create an instance of {@link QueryPlanNode }
     * 
     */
    public QueryPlanNode createQueryPlanNode() {
        return new QueryPlanNode();
    }

    /**
     * Create an instance of {@link BaseExecuteSqlRequest }
     * 
     */
    public BaseExecuteSqlRequest createBaseExecuteSqlRequest() {
        return new BaseExecuteSqlRequest();
    }

    /**
     * Create an instance of {@link ExecuteSqlScriptRequest }
     * 
     */
    public ExecuteSqlScriptRequest createExecuteSqlScriptRequest() {
        return new ExecuteSqlScriptRequest();
    }

    /**
     * Create an instance of {@link ExecuteNativeSqlResponse }
     * 
     */
    public ExecuteNativeSqlResponse createExecuteNativeSqlResponse() {
        return new ExecuteNativeSqlResponse();
    }

    /**
     * Create an instance of {@link NameValue }
     * 
     */
    public NameValue createNameValue() {
        return new NameValue();
    }

    /**
     * Create an instance of {@link ExecuteSqlResponse }
     * 
     */
    public ExecuteSqlResponse createExecuteSqlResponse() {
        return new ExecuteSqlResponse();
    }

    /**
     * Create an instance of {@link QueryPlanNode.Children }
     * 
     */
    public QueryPlanNode.Children createQueryPlanNodeChildren() {
        return new QueryPlanNode.Children();
    }

    /**
     * Create an instance of {@link BaseQueryPlanResponse }
     * 
     */
    public BaseQueryPlanResponse createBaseQueryPlanResponse() {
        return new BaseQueryPlanResponse();
    }

    /**
     * Create an instance of {@link GetProceduralResultResponse }
     * 
     */
    public GetProceduralResultResponse createGetProceduralResultResponse() {
        return new GetProceduralResultResponse();
    }

    /**
     * Create an instance of {@link ParameterList }
     * 
     */
    public ParameterList createParameterList() {
        return new ParameterList();
    }

    /**
     * Create an instance of {@link ExecuteProcedureResponse }
     * 
     */
    public ExecuteProcedureResponse createExecuteProcedureResponse() {
        return new ExecuteProcedureResponse();
    }

    /**
     * Create an instance of {@link CloseResultRequest }
     * 
     */
    public CloseResultRequest createCloseResultRequest() {
        return new CloseResultRequest();
    }

    /**
     * Create an instance of {@link GetResultSetPlanRequest }
     * 
     */
    public GetResultSetPlanRequest createGetResultSetPlanRequest() {
        return new GetResultSetPlanRequest();
    }

    /**
     * Create an instance of {@link GetSqlPlanResponse }
     * 
     */
    public GetSqlPlanResponse createGetSqlPlanResponse() {
        return new GetSqlPlanResponse();
    }

    /**
     * Create an instance of {@link GetProceduralResultRequest }
     * 
     */
    public GetProceduralResultRequest createGetProceduralResultRequest() {
        return new GetProceduralResultRequest();
    }

    /**
     * Create an instance of {@link RowValueList }
     * 
     */
    public RowValueList createRowValueList() {
        return new RowValueList();
    }

    /**
     * Create an instance of {@link BaseProceduralResultResponse }
     * 
     */
    public BaseProceduralResultResponse createBaseProceduralResultResponse() {
        return new BaseProceduralResultResponse();
    }

    /**
     * Create an instance of {@link ExecuteSqlRequest }
     * 
     */
    public ExecuteSqlRequest createExecuteSqlRequest() {
        return new ExecuteSqlRequest();
    }

    /**
     * Create an instance of {@link GetSqlPlanRequest }
     * 
     */
    public GetSqlPlanRequest createGetSqlPlanRequest() {
        return new GetSqlPlanRequest();
    }

    /**
     * Create an instance of {@link GetResourcePlanRequest }
     * 
     */
    public GetResourcePlanRequest createGetResourcePlanRequest() {
        return new GetResourcePlanRequest();
    }

    /**
     * Create an instance of {@link QueryPlanNode.Properties }
     * 
     */
    public QueryPlanNode.Properties createQueryPlanNodeProperties() {
        return new QueryPlanNode.Properties();
    }

    /**
     * Create an instance of {@link TabularValue }
     * 
     */
    public TabularValue createTabularValue() {
        return new TabularValue();
    }

    /**
     * Create an instance of {@link GetTabularResultRequest }
     * 
     */
    public GetTabularResultRequest createGetTabularResultRequest() {
        return new GetTabularResultRequest();
    }

    /**
     * Create an instance of {@link BaseQueryPlanRequest }
     * 
     */
    public BaseQueryPlanRequest createBaseQueryPlanRequest() {
        return new BaseQueryPlanRequest();
    }

    /**
     * Create an instance of {@link CloseResultResponse }
     * 
     */
    public CloseResultResponse createCloseResultResponse() {
        return new CloseResultResponse();
    }

    /**
     * Create an instance of {@link GetTabularResultResponse }
     * 
     */
    public GetTabularResultResponse createGetTabularResultResponse() {
        return new GetTabularResultResponse();
    }

    /**
     * Create an instance of {@link ValueList }
     * 
     */
    public ValueList createValueList() {
        return new ValueList();
    }

    /**
     * Create an instance of {@link BaseExecuteProcedureResponse }
     * 
     */
    public BaseExecuteProcedureResponse createBaseExecuteProcedureResponse() {
        return new BaseExecuteProcedureResponse();
    }

    /**
     * Create an instance of {@link Value }
     * 
     */
    public Value createValue() {
        return new Value();
    }

    /**
     * Create an instance of {@link ExecutePreparedSqlResponse }
     * 
     */
    public ExecutePreparedSqlResponse createExecutePreparedSqlResponse() {
        return new ExecutePreparedSqlResponse();
    }

    /**
     * Create an instance of {@link BaseTabularResultResponse }
     * 
     */
    public BaseTabularResultResponse createBaseTabularResultResponse() {
        return new BaseTabularResultResponse();
    }

    /**
     * Create an instance of {@link ExecuteSqlScriptResponse }
     * 
     */
    public ExecuteSqlScriptResponse createExecuteSqlScriptResponse() {
        return new ExecuteSqlScriptResponse();
    }

    /**
     * Create an instance of {@link ParameterValue }
     * 
     */
    public ParameterValue createParameterValue() {
        return new ParameterValue();
    }

    /**
     * Create an instance of {@link GetResourcePlanResponse }
     * 
     */
    public GetResourcePlanResponse createGetResourcePlanResponse() {
        return new GetResourcePlanResponse();
    }

    /**
     * Create an instance of {@link ParseSqlQueryResponse }
     * 
     */
    public ParseSqlQueryResponse createParseSqlQueryResponse() {
        return new ParseSqlQueryResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourcePlanResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "getResourcePlanResponse")
    public JAXBElement<GetResourcePlanResponse> createGetResourcePlanResponse(GetResourcePlanResponse value) {
        return new JAXBElement<GetResourcePlanResponse>(_GetResourcePlanResponse_QNAME, GetResourcePlanResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CloseResultRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "closeResult")
    public JAXBElement<CloseResultRequest> createCloseResult(CloseResultRequest value) {
        return new JAXBElement<CloseResultRequest>(_CloseResult_QNAME, CloseResultRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecutePreparedSqlResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "executePreparedSqlResponse")
    public JAXBElement<ExecutePreparedSqlResponse> createExecutePreparedSqlResponse(ExecutePreparedSqlResponse value) {
        return new JAXBElement<ExecutePreparedSqlResponse>(_ExecutePreparedSqlResponse_QNAME, ExecutePreparedSqlResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteProcedureRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "executeProcedure")
    public JAXBElement<ExecuteProcedureRequest> createExecuteProcedure(ExecuteProcedureRequest value) {
        return new JAXBElement<ExecuteProcedureRequest>(_ExecuteProcedure_QNAME, ExecuteProcedureRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteSqlScriptResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "executeSqlScriptResponse")
    public JAXBElement<ExecuteSqlScriptResponse> createExecuteSqlScriptResponse(ExecuteSqlScriptResponse value) {
        return new JAXBElement<ExecuteSqlScriptResponse>(_ExecuteSqlScriptResponse_QNAME, ExecuteSqlScriptResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParseSqlQueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "parseSqlQueryResponse")
    public JAXBElement<ParseSqlQueryResponse> createParseSqlQueryResponse(ParseSqlQueryResponse value) {
        return new JAXBElement<ParseSqlQueryResponse>(_ParseSqlQueryResponse_QNAME, ParseSqlQueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CloseResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "closeResultResponse")
    public JAXBElement<CloseResultResponse> createCloseResultResponse(CloseResultResponse value) {
        return new JAXBElement<CloseResultResponse>(_CloseResultResponse_QNAME, CloseResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteProcedureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "executeProcedureResponse")
    public JAXBElement<ExecuteProcedureResponse> createExecuteProcedureResponse(ExecuteProcedureResponse value) {
        return new JAXBElement<ExecuteProcedureResponse>(_ExecuteProcedureResponse_QNAME, ExecuteProcedureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecutePreparedSqlRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "executePreparedSql")
    public JAXBElement<ExecutePreparedSqlRequest> createExecutePreparedSql(ExecutePreparedSqlRequest value) {
        return new JAXBElement<ExecutePreparedSqlRequest>(_ExecutePreparedSql_QNAME, ExecutePreparedSqlRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteSqlRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "executeSql")
    public JAXBElement<ExecuteSqlRequest> createExecuteSql(ExecuteSqlRequest value) {
        return new JAXBElement<ExecuteSqlRequest>(_ExecuteSql_QNAME, ExecuteSqlRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSqlPlanRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "getSqlPlan")
    public JAXBElement<GetSqlPlanRequest> createGetSqlPlan(GetSqlPlanRequest value) {
        return new JAXBElement<GetSqlPlanRequest>(_GetSqlPlan_QNAME, GetSqlPlanRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteNativeSqlResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "executeNativeSqlResponse")
    public JAXBElement<ExecuteNativeSqlResponse> createExecuteNativeSqlResponse(ExecuteNativeSqlResponse value) {
        return new JAXBElement<ExecuteNativeSqlResponse>(_ExecuteNativeSqlResponse_QNAME, ExecuteNativeSqlResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProceduralResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "getProceduralResultResponse")
    public JAXBElement<GetProceduralResultResponse> createGetProceduralResultResponse(GetProceduralResultResponse value) {
        return new JAXBElement<GetProceduralResultResponse>(_GetProceduralResultResponse_QNAME, GetProceduralResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResultSetPlanRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "getResultSetPlan")
    public JAXBElement<GetResultSetPlanRequest> createGetResultSetPlan(GetResultSetPlanRequest value) {
        return new JAXBElement<GetResultSetPlanRequest>(_GetResultSetPlan_QNAME, GetResultSetPlanRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSqlPlanResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "getSqlPlanResponse")
    public JAXBElement<GetSqlPlanResponse> createGetSqlPlanResponse(GetSqlPlanResponse value) {
        return new JAXBElement<GetSqlPlanResponse>(_GetSqlPlanResponse_QNAME, GetSqlPlanResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProceduralResultRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "getProceduralResult")
    public JAXBElement<GetProceduralResultRequest> createGetProceduralResult(GetProceduralResultRequest value) {
        return new JAXBElement<GetProceduralResultRequest>(_GetProceduralResult_QNAME, GetProceduralResultRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParseSqlQueryRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "parseSqlQuery")
    public JAXBElement<ParseSqlQueryRequest> createParseSqlQuery(ParseSqlQueryRequest value) {
        return new JAXBElement<ParseSqlQueryRequest>(_ParseSqlQuery_QNAME, ParseSqlQueryRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTabularResultRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "getTabularResult")
    public JAXBElement<GetTabularResultRequest> createGetTabularResult(GetTabularResultRequest value) {
        return new JAXBElement<GetTabularResultRequest>(_GetTabularResult_QNAME, GetTabularResultRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResultSetPlanResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "getResultSetPlanResponse")
    public JAXBElement<GetResultSetPlanResponse> createGetResultSetPlanResponse(GetResultSetPlanResponse value) {
        return new JAXBElement<GetResultSetPlanResponse>(_GetResultSetPlanResponse_QNAME, GetResultSetPlanResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTabularResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "getTabularResultResponse")
    public JAXBElement<GetTabularResultResponse> createGetTabularResultResponse(GetTabularResultResponse value) {
        return new JAXBElement<GetTabularResultResponse>(_GetTabularResultResponse_QNAME, GetTabularResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourcePlanRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "getResourcePlan")
    public JAXBElement<GetResourcePlanRequest> createGetResourcePlan(GetResourcePlanRequest value) {
        return new JAXBElement<GetResourcePlanRequest>(_GetResourcePlan_QNAME, GetResourcePlanRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteNativeSqlRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "executeNativeSql")
    public JAXBElement<ExecuteNativeSqlRequest> createExecuteNativeSql(ExecuteNativeSqlRequest value) {
        return new JAXBElement<ExecuteNativeSqlRequest>(_ExecuteNativeSql_QNAME, ExecuteNativeSqlRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteSqlScriptRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "executeSqlScript")
    public JAXBElement<ExecuteSqlScriptRequest> createExecuteSqlScript(ExecuteSqlScriptRequest value) {
        return new JAXBElement<ExecuteSqlScriptRequest>(_ExecuteSqlScript_QNAME, ExecuteSqlScriptRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteSqlResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.compositesw.com/services/system/admin/execute", name = "executeSqlResponse")
    public JAXBElement<ExecuteSqlResponse> createExecuteSqlResponse(ExecuteSqlResponse value) {
        return new JAXBElement<ExecuteSqlResponse>(_ExecuteSqlResponse_QNAME, ExecuteSqlResponse.class, null, value);
    }

}
