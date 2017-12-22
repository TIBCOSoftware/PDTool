
package com.compositesw.services.system.util.common;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.admin.resource.GetIntrospectableResourceIdsTaskResponse;
import com.compositesw.services.system.admin.resource.GetIntrospectedResourceIdsTaskResponse;


/**
 * <p>Java class for serverTaskResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="serverTaskResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="taskId" type="{http://www.compositesw.com/services/system/util/common}taskId"/>
 *         &lt;element name="totalResults" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serverTaskResponse", propOrder = {
    "taskId",
    "totalResults"
})
@XmlSeeAlso({
    GetIntrospectedResourceIdsTaskResponse.class,
    GetIntrospectableResourceIdsTaskResponse.class,
    ServerTaskResultResponse.class
})
public class ServerTaskResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected String taskId;
    protected BigInteger totalResults;

    /**
     * Gets the value of the taskId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * Sets the value of the taskId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaskId(String value) {
        this.taskId = value;
    }

    /**
     * Gets the value of the totalResults property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalResults() {
        return totalResults;
    }

    /**
     * Sets the value of the totalResults property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalResults(BigInteger value) {
        this.totalResults = value;
    }

}
