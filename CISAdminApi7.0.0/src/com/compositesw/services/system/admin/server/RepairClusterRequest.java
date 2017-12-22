
package com.compositesw.services.system.admin.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for repairClusterRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="repairClusterRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="option" type="{http://www.compositesw.com/services/system/admin/server}repairClusterOption"/>
 *         &lt;element name="remoteHostName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remotePort" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="remoteDomainName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remoteUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remotePassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "repairClusterRequest", propOrder = {
    "option",
    "remoteHostName",
    "remotePort",
    "remoteDomainName",
    "remoteUserName",
    "remotePassword"
})
public class RepairClusterRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected RepairClusterOption option;
    protected String remoteHostName;
    protected Integer remotePort;
    protected String remoteDomainName;
    protected String remoteUserName;
    protected String remotePassword;

    /**
     * Gets the value of the option property.
     * 
     * @return
     *     possible object is
     *     {@link RepairClusterOption }
     *     
     */
    public RepairClusterOption getOption() {
        return option;
    }

    /**
     * Sets the value of the option property.
     * 
     * @param value
     *     allowed object is
     *     {@link RepairClusterOption }
     *     
     */
    public void setOption(RepairClusterOption value) {
        this.option = value;
    }

    /**
     * Gets the value of the remoteHostName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteHostName() {
        return remoteHostName;
    }

    /**
     * Sets the value of the remoteHostName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteHostName(String value) {
        this.remoteHostName = value;
    }

    /**
     * Gets the value of the remotePort property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRemotePort() {
        return remotePort;
    }

    /**
     * Sets the value of the remotePort property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRemotePort(Integer value) {
        this.remotePort = value;
    }

    /**
     * Gets the value of the remoteDomainName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteDomainName() {
        return remoteDomainName;
    }

    /**
     * Sets the value of the remoteDomainName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteDomainName(String value) {
        this.remoteDomainName = value;
    }

    /**
     * Gets the value of the remoteUserName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteUserName() {
        return remoteUserName;
    }

    /**
     * Sets the value of the remoteUserName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteUserName(String value) {
        this.remoteUserName = value;
    }

    /**
     * Gets the value of the remotePassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemotePassword() {
        return remotePassword;
    }

    /**
     * Sets the value of the remotePassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemotePassword(String value) {
        this.remotePassword = value;
    }

}
