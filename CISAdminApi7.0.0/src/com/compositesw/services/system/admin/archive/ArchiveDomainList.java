
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.Marker;
import com.compositesw.services.system.util.common.NameList;


/**
 * <p>Java class for archiveDomainList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="archiveDomainList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="all" type="{http://www.compositesw.com/services/system/util/common}marker" minOccurs="0"/>
 *         &lt;element name="domains" type="{http://www.compositesw.com/services/system/util/common}nameList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "archiveDomainList", propOrder = {
    "all",
    "domains"
})
public class ArchiveDomainList {

    protected Marker all;
    protected NameList domains;

    /**
     * Gets the value of the all property.
     * 
     * @return
     *     possible object is
     *     {@link Marker }
     *     
     */
    public Marker getAll() {
        return all;
    }

    /**
     * Sets the value of the all property.
     * 
     * @param value
     *     allowed object is
     *     {@link Marker }
     *     
     */
    public void setAll(Marker value) {
        this.all = value;
    }

    /**
     * Gets the value of the domains property.
     * 
     * @return
     *     possible object is
     *     {@link NameList }
     *     
     */
    public NameList getDomains() {
        return domains;
    }

    /**
     * Sets the value of the domains property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameList }
     *     
     */
    public void setDomains(NameList value) {
        this.domains = value;
    }

}
