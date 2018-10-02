
package com.compositesw.services.system.admin.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for joinClusterRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="joinClusterRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="remoteHostName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="remotePort" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="remoteDomainName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="remoteUserName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="remotePassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "joinClusterRequest", propOrder = {
    "remoteHostName",
    "remotePort",
    "remoteDomainName",
    "remoteUserName",
    "remotePassword"
})
public class JoinClusterRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected String remoteHostName;
    protected Integer remotePort;
    @XmlElement(required = true)
    protected String remoteDomainName;
    @XmlElement(required = true)
    protected String remoteUserName;
    @XmlElement(required = true)
    protected String remotePassword;

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
