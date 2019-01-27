
package com.compositesw.services.system.util.common;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fault">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorEntry" type="{http://www.compositesw.com/services/system/util/common}messageEntry" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fault", propOrder = {
    "errorEntry"
})
public class Fault {

    protected List<MessageEntry> errorEntry;

    /**
     * Gets the value of the errorEntry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorEntry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageEntry }
     * 
     * 
     */
    public List<MessageEntry> getErrorEntry() {
        if (errorEntry == null) {
            errorEntry = new ArrayList<MessageEntry>();
        }
        return this.errorEntry;
    }

}
