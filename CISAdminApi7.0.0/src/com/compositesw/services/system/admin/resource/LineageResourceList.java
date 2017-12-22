
package com.compositesw.services.system.admin.resource;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for lineageResourceList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lineageResourceList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lineageResource" type="{http://www.compositesw.com/services/system/admin/resource}lineageResource" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lineageResourceList", propOrder = {
    "lineageResource"
})
public class LineageResourceList {

    protected List<LineageResource> lineageResource;

    /**
     * Gets the value of the lineageResource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lineageResource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLineageResource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LineageResource }
     * 
     * 
     */
    public List<LineageResource> getLineageResource() {
        if (lineageResource == null) {
            lineageResource = new ArrayList<LineageResource>();
        }
        return this.lineageResource;
    }

}
