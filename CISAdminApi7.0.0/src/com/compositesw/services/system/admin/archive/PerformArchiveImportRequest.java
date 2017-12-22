
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for performArchiveImportRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="performArchiveImportRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/archive}importArchiveRequest">
 *       &lt;sequence>
 *         &lt;element name="isBlocking" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "performArchiveImportRequest", propOrder = {
    "isBlocking"
})
public class PerformArchiveImportRequest
    extends ImportArchiveRequest
{

    protected boolean isBlocking;

    /**
     * Gets the value of the isBlocking property.
     * 
     */
    public boolean isIsBlocking() {
        return isBlocking;
    }

    /**
     * Sets the value of the isBlocking property.
     * 
     */
    public void setIsBlocking(boolean value) {
        this.isBlocking = value;
    }

}
