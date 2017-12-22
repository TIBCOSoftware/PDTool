
package com.compositesw.services.system.admin.resource;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsdlDocumentSet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsdlDocumentSet">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}xmlDocumentSet">
 *       &lt;sequence>
 *         &lt;element name="importedDocuments" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="importedDocument" type="{http://www.compositesw.com/services/system/admin/resource}wsdlDocumentSet" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsdlDocumentSet", propOrder = {
    "importedDocuments"
})
public class WsdlDocumentSet
    extends XmlDocumentSet
{

    protected WsdlDocumentSet.ImportedDocuments importedDocuments;

    /**
     * Gets the value of the importedDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link WsdlDocumentSet.ImportedDocuments }
     *     
     */
    public WsdlDocumentSet.ImportedDocuments getImportedDocuments() {
        return importedDocuments;
    }

    /**
     * Sets the value of the importedDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsdlDocumentSet.ImportedDocuments }
     *     
     */
    public void setImportedDocuments(WsdlDocumentSet.ImportedDocuments value) {
        this.importedDocuments = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="importedDocument" type="{http://www.compositesw.com/services/system/admin/resource}wsdlDocumentSet" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "importedDocument"
    })
    public static class ImportedDocuments {

        protected List<WsdlDocumentSet> importedDocument;

        /**
         * Gets the value of the importedDocument property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the importedDocument property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getImportedDocument().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link WsdlDocumentSet }
         * 
         * 
         */
        public List<WsdlDocumentSet> getImportedDocument() {
            if (importedDocument == null) {
                importedDocument = new ArrayList<WsdlDocumentSet>();
            }
            return this.importedDocument;
        }

    }

}
