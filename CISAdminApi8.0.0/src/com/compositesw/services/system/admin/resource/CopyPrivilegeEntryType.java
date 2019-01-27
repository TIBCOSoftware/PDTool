
package com.compositesw.services.system.admin.resource;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for copyPrivilegeEntryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="copyPrivilegeEntryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="srcResource" type="{http://www.compositesw.com/services/system/admin/resource}pathTypeOrColumnPair"/>
 *         &lt;element name="dstResource" type="{http://www.compositesw.com/services/system/admin/resource}pathTypeOrColumnPair" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "copyPrivilegeEntryType", propOrder = {
    "srcResource",
    "dstResource"
})
public class CopyPrivilegeEntryType {

    @XmlElement(required = true)
    protected PathTypeOrColumnPair srcResource;
    @XmlElement(required = true)
    protected List<PathTypeOrColumnPair> dstResource;

    /**
     * Gets the value of the srcResource property.
     * 
     * @return
     *     possible object is
     *     {@link PathTypeOrColumnPair }
     *     
     */
    public PathTypeOrColumnPair getSrcResource() {
        return srcResource;
    }

    /**
     * Sets the value of the srcResource property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathTypeOrColumnPair }
     *     
     */
    public void setSrcResource(PathTypeOrColumnPair value) {
        this.srcResource = value;
    }

    /**
     * Gets the value of the dstResource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dstResource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDstResource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PathTypeOrColumnPair }
     * 
     * 
     */
    public List<PathTypeOrColumnPair> getDstResource() {
        if (dstResource == null) {
            dstResource = new ArrayList<PathTypeOrColumnPair>();
        }
        return this.dstResource;
    }

}
