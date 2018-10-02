
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;
import com.compositesw.services.system.util.common.DetailLevel;


/**
 * <p>Java class for getResourceUpdatesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getResourceUpdatesRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="detail" type="{http://www.compositesw.com/services/system/util/common}detailLevel"/>
 *         &lt;element name="discoverChildren" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="includeLockState" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="changeId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resourcesToUpdate" type="{http://www.compositesw.com/services/system/admin/resource}resourceUpdateInfoList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getResourceUpdatesRequest", propOrder = {
    "detail",
    "discoverChildren",
    "includeLockState",
    "changeId",
    "version",
    "resourcesToUpdate"
})
public class GetResourceUpdatesRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected DetailLevel detail;
    protected boolean discoverChildren;
    protected boolean includeLockState;
    protected Integer changeId;
    protected String version;
    @XmlElement(required = true)
    protected ResourceUpdateInfoList resourcesToUpdate;

    /**
     * Gets the value of the detail property.
     * 
     * @return
     *     possible object is
     *     {@link DetailLevel }
     *     
     */
    public DetailLevel getDetail() {
        return detail;
    }

    /**
     * Sets the value of the detail property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailLevel }
     *     
     */
    public void setDetail(DetailLevel value) {
        this.detail = value;
    }

    /**
     * Gets the value of the discoverChildren property.
     * 
     */
    public boolean isDiscoverChildren() {
        return discoverChildren;
    }

    /**
     * Sets the value of the discoverChildren property.
     * 
     */
    public void setDiscoverChildren(boolean value) {
        this.discoverChildren = value;
    }

    /**
     * Gets the value of the includeLockState property.
     * 
     */
    public boolean isIncludeLockState() {
        return includeLockState;
    }

    /**
     * Sets the value of the includeLockState property.
     * 
     */
    public void setIncludeLockState(boolean value) {
        this.includeLockState = value;
    }

    /**
     * Gets the value of the changeId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getChangeId() {
        return changeId;
    }

    /**
     * Sets the value of the changeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setChangeId(Integer value) {
        this.changeId = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the resourcesToUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceUpdateInfoList }
     *     
     */
    public ResourceUpdateInfoList getResourcesToUpdate() {
        return resourcesToUpdate;
    }

    /**
     * Sets the value of the resourcesToUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceUpdateInfoList }
     *     
     */
    public void setResourcesToUpdate(ResourceUpdateInfoList value) {
        this.resourcesToUpdate = value;
    }

}
