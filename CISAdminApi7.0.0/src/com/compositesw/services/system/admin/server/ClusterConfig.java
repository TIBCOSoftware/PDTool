
package com.compositesw.services.system.admin.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for clusterConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="clusterConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clusterDisplayName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serverList" type="{http://www.compositesw.com/services/system/admin/server}serverList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clusterConfig", propOrder = {
    "clusterDisplayName",
    "serverList"
})
public class ClusterConfig {

    @XmlElement(required = true)
    protected String clusterDisplayName;
    @XmlElement(required = true)
    protected ServerList serverList;

    /**
     * Gets the value of the clusterDisplayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClusterDisplayName() {
        return clusterDisplayName;
    }

    /**
     * Sets the value of the clusterDisplayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClusterDisplayName(String value) {
        this.clusterDisplayName = value;
    }

    /**
     * Gets the value of the serverList property.
     * 
     * @return
     *     possible object is
     *     {@link ServerList }
     *     
     */
    public ServerList getServerList() {
        return serverList;
    }

    /**
     * Sets the value of the serverList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServerList }
     *     
     */
    public void setServerList(ServerList value) {
        this.serverList = value;
    }

}
