
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getResourceStatsSummaryResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getResourceStatsSummaryResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="msg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="last_refresh_end" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="curr_refresh_start" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getResourceStatsSummaryResponse", propOrder = {
    "status",
    "msg",
    "lastRefreshEnd",
    "currRefreshStart"
})
public class GetResourceStatsSummaryResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected String status;
    @XmlElement(required = true)
    protected String msg;
    @XmlElement(name = "last_refresh_end")
    protected long lastRefreshEnd;
    @XmlElement(name = "curr_refresh_start")
    protected long currRefreshStart;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the msg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets the value of the msg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsg(String value) {
        this.msg = value;
    }

    /**
     * Gets the value of the lastRefreshEnd property.
     * 
     */
    public long getLastRefreshEnd() {
        return lastRefreshEnd;
    }

    /**
     * Sets the value of the lastRefreshEnd property.
     * 
     */
    public void setLastRefreshEnd(long value) {
        this.lastRefreshEnd = value;
    }

    /**
     * Gets the value of the currRefreshStart property.
     * 
     */
    public long getCurrRefreshStart() {
        return currRefreshStart;
    }

    /**
     * Sets the value of the currRefreshStart property.
     * 
     */
    public void setCurrRefreshStart(long value) {
        this.currRefreshStart = value;
    }

}
