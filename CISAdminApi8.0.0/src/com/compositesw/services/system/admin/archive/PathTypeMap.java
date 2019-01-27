
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.admin.resource.PathTypePair;


/**
 * <p>Java class for pathTypeMap complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pathTypeMap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="from" type="{http://www.compositesw.com/services/system/admin/resource}pathTypePair"/>
 *         &lt;element name="to" type="{http://www.compositesw.com/services/system/admin/resource}pathTypePair"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pathTypeMap", propOrder = {
    "from",
    "to"
})
public class PathTypeMap {

    @XmlElement(required = true)
    protected PathTypePair from;
    @XmlElement(required = true)
    protected PathTypePair to;

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link PathTypePair }
     *     
     */
    public PathTypePair getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathTypePair }
     *     
     */
    public void setFrom(PathTypePair value) {
        this.from = value;
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link PathTypePair }
     *     
     */
    public PathTypePair getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathTypePair }
     *     
     */
    public void setTo(PathTypePair value) {
        this.to = value;
    }

}
