
package com.compositesw.services.system.admin.execute;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for baseQueryPlanResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="baseQueryPlanResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="queryPlanRoot" type="{http://www.compositesw.com/services/system/admin/execute}queryPlanNode"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseQueryPlanResponse", propOrder = {
    "queryPlanRoot"
})
@XmlSeeAlso({
    GetSqlPlanResponse.class,
    GetResourcePlanResponse.class,
    GetResultSetPlanResponse.class
})
public class BaseQueryPlanResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected QueryPlanNode queryPlanRoot;

    /**
     * Gets the value of the queryPlanRoot property.
     * 
     * @return
     *     possible object is
     *     {@link QueryPlanNode }
     *     
     */
    public QueryPlanNode getQueryPlanRoot() {
        return queryPlanRoot;
    }

    /**
     * Sets the value of the queryPlanRoot property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryPlanNode }
     *     
     */
    public void setQueryPlanRoot(QueryPlanNode value) {
        this.queryPlanRoot = value;
    }

}
