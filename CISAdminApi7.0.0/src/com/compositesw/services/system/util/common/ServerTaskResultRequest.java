
package com.compositesw.services.system.util.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.admin.resource.GetIntrospectableResourceIdsResultRequest;
import com.compositesw.services.system.admin.resource.GetIntrospectedResourceIdsResultRequest;
import com.compositesw.services.system.admin.resource.IntrospectResourcesResultRequest;


/**
 * <p>Java class for serverTaskResultRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="serverTaskResultRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="taskId" type="{http://www.compositesw.com/services/system/util/common}taskId"/>
 *         &lt;element name="block" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="page" type="{http://www.compositesw.com/services/system/util/common}page" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serverTaskResultRequest", propOrder = {
    "taskId",
    "block",
    "page"
})
@XmlSeeAlso({
    GetIntrospectedResourceIdsResultRequest.class,
    GetIntrospectableResourceIdsResultRequest.class,
    IntrospectResourcesResultRequest.class
})
public class ServerTaskResultRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected String taskId;
    protected Boolean block;
    protected Page page;

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
     * Gets the value of the block property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBlock() {
        return block;
    }

    /**
     * Sets the value of the block property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBlock(Boolean value) {
        this.block = value;
    }

    /**
     * Gets the value of the page property.
     * 
     * @return
     *     possible object is
     *     {@link Page }
     *     
     */
    public Page getPage() {
        return page;
    }

    /**
     * Sets the value of the page property.
     * 
     * @param value
     *     allowed object is
     *     {@link Page }
     *     
     */
    public void setPage(Page value) {
        this.page = value;
    }

}
