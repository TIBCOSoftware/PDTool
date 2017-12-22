
package com.compositesw.services.system.admin.resource;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for xmlSchemaDocumentSet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xmlSchemaDocumentSet">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}xmlDocumentSet">
 *       &lt;sequence>
 *         &lt;element name="importedDocuments" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="importedDocument" type="{http://www.compositesw.com/services/system/admin/resource}xmlSchemaDocumentSet" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="includedDocuments" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="includedDocument" type="{http://www.compositesw.com/services/system/admin/resource}xmlSchemaDocumentSet" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="redefinedDocuments" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="redefinedDocument" type="{http://www.compositesw.com/services/system/admin/resource}xmlSchemaDocumentSet" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "xmlSchemaDocumentSet", propOrder = {
    "importedDocuments",
    "includedDocuments",
    "redefinedDocuments"
})
public class XmlSchemaDocumentSet
    extends XmlDocumentSet
{

    protected XmlSchemaDocumentSet.ImportedDocuments importedDocuments;
    protected XmlSchemaDocumentSet.IncludedDocuments includedDocuments;
    protected XmlSchemaDocumentSet.RedefinedDocuments redefinedDocuments;

    /**
     * Gets the value of the importedDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link XmlSchemaDocumentSet.ImportedDocuments }
     *     
     */
    public XmlSchemaDocumentSet.ImportedDocuments getImportedDocuments() {
        return importedDocuments;
    }

    /**
     * Sets the value of the importedDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlSchemaDocumentSet.ImportedDocuments }
     *     
     */
    public void setImportedDocuments(XmlSchemaDocumentSet.ImportedDocuments value) {
        this.importedDocuments = value;
    }

    /**
     * Gets the value of the includedDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link XmlSchemaDocumentSet.IncludedDocuments }
     *     
     */
    public XmlSchemaDocumentSet.IncludedDocuments getIncludedDocuments() {
        return includedDocuments;
    }

    /**
     * Sets the value of the includedDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlSchemaDocumentSet.IncludedDocuments }
     *     
     */
    public void setIncludedDocuments(XmlSchemaDocumentSet.IncludedDocuments value) {
        this.includedDocuments = value;
    }

    /**
     * Gets the value of the redefinedDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link XmlSchemaDocumentSet.RedefinedDocuments }
     *     
     */
    public XmlSchemaDocumentSet.RedefinedDocuments getRedefinedDocuments() {
        return redefinedDocuments;
    }

    /**
     * Sets the value of the redefinedDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlSchemaDocumentSet.RedefinedDocuments }
     *     
     */
    public void setRedefinedDocuments(XmlSchemaDocumentSet.RedefinedDocuments value) {
        this.redefinedDocuments = value;
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
     *         &lt;element name="importedDocument" type="{http://www.compositesw.com/services/system/admin/resource}xmlSchemaDocumentSet" maxOccurs="unbounded" minOccurs="0"/>
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

        protected List<XmlSchemaDocumentSet> importedDocument;

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
         * {@link XmlSchemaDocumentSet }
         * 
         * 
         */
        public List<XmlSchemaDocumentSet> getImportedDocument() {
            if (importedDocument == null) {
                importedDocument = new ArrayList<XmlSchemaDocumentSet>();
            }
            return this.importedDocument;
        }

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
     *         &lt;element name="includedDocument" type="{http://www.compositesw.com/services/system/admin/resource}xmlSchemaDocumentSet" maxOccurs="unbounded" minOccurs="0"/>
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
        "includedDocument"
    })
    public static class IncludedDocuments {

        protected List<XmlSchemaDocumentSet> includedDocument;

        /**
         * Gets the value of the includedDocument property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the includedDocument property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIncludedDocument().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link XmlSchemaDocumentSet }
         * 
         * 
         */
        public List<XmlSchemaDocumentSet> getIncludedDocument() {
            if (includedDocument == null) {
                includedDocument = new ArrayList<XmlSchemaDocumentSet>();
            }
            return this.includedDocument;
        }

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
     *         &lt;element name="redefinedDocument" type="{http://www.compositesw.com/services/system/admin/resource}xmlSchemaDocumentSet" maxOccurs="unbounded" minOccurs="0"/>
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
        "redefinedDocument"
    })
    public static class RedefinedDocuments {

        protected List<XmlSchemaDocumentSet> redefinedDocument;

        /**
         * Gets the value of the redefinedDocument property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the redefinedDocument property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRedefinedDocument().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link XmlSchemaDocumentSet }
         * 
         * 
         */
        public List<XmlSchemaDocumentSet> getRedefinedDocument() {
            if (redefinedDocument == null) {
                redefinedDocument = new ArrayList<XmlSchemaDocumentSet>();
            }
            return this.redefinedDocument;
        }

    }

}
