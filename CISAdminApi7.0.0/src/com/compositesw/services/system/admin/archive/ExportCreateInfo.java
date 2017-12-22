
package com.compositesw.services.system.admin.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for exportCreateInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exportCreateInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="exportDomain" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="exportUser" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="exportVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="exportDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="exportServer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="exportJvm" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="exportOperatingSystem" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exportCreateInfo", propOrder = {
    "exportDomain",
    "exportUser",
    "exportVersion",
    "exportDate",
    "exportServer",
    "exportJvm",
    "exportOperatingSystem"
})
public class ExportCreateInfo {

    @XmlElement(required = true)
    protected String exportDomain;
    @XmlElement(required = true)
    protected String exportUser;
    @XmlElement(required = true)
    protected String exportVersion;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar exportDate;
    @XmlElement(required = true)
    protected String exportServer;
    @XmlElement(required = true)
    protected String exportJvm;
    @XmlElement(required = true)
    protected String exportOperatingSystem;

    /**
     * Gets the value of the exportDomain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportDomain() {
        return exportDomain;
    }

    /**
     * Sets the value of the exportDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportDomain(String value) {
        this.exportDomain = value;
    }

    /**
     * Gets the value of the exportUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportUser() {
        return exportUser;
    }

    /**
     * Sets the value of the exportUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportUser(String value) {
        this.exportUser = value;
    }

    /**
     * Gets the value of the exportVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportVersion() {
        return exportVersion;
    }

    /**
     * Sets the value of the exportVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportVersion(String value) {
        this.exportVersion = value;
    }

    /**
     * Gets the value of the exportDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExportDate() {
        return exportDate;
    }

    /**
     * Sets the value of the exportDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExportDate(XMLGregorianCalendar value) {
        this.exportDate = value;
    }

    /**
     * Gets the value of the exportServer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportServer() {
        return exportServer;
    }

    /**
     * Sets the value of the exportServer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportServer(String value) {
        this.exportServer = value;
    }

    /**
     * Gets the value of the exportJvm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportJvm() {
        return exportJvm;
    }

    /**
     * Sets the value of the exportJvm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportJvm(String value) {
        this.exportJvm = value;
    }

    /**
     * Gets the value of the exportOperatingSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportOperatingSystem() {
        return exportOperatingSystem;
    }

    /**
     * Sets the value of the exportOperatingSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportOperatingSystem(String value) {
        this.exportOperatingSystem = value;
    }

}
