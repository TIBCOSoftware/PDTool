
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for definitionSetResource complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="definitionSetResource">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}resource">
 *       &lt;sequence>
 *         &lt;element name="sourceDocument" type="{http://www.compositesw.com/services/system/admin/resource}xmlDocumentSet" minOccurs="0"/>
 *         &lt;element name="sourceList" type="{http://www.compositesw.com/services/system/admin/resource}definitionSetSourceList" minOccurs="0"/>
 *         &lt;element name="definitions" type="{http://www.compositesw.com/services/system/admin/resource}definitionList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "definitionSetResource", propOrder = {
    "sourceDocument",
    "sourceList",
    "definitions"
})
public class DefinitionSetResource
    extends Resource
{

    protected XmlDocumentSet sourceDocument;
    protected DefinitionSetSourceList sourceList;
    protected DefinitionList definitions;

    /**
     * Gets the value of the sourceDocument property.
     * 
     * @return
     *     possible object is
     *     {@link XmlDocumentSet }
     *     
     */
    public XmlDocumentSet getSourceDocument() {
        return sourceDocument;
    }

    /**
     * Sets the value of the sourceDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlDocumentSet }
     *     
     */
    public void setSourceDocument(XmlDocumentSet value) {
        this.sourceDocument = value;
    }

    /**
     * Gets the value of the sourceList property.
     * 
     * @return
     *     possible object is
     *     {@link DefinitionSetSourceList }
     *     
     */
    public DefinitionSetSourceList getSourceList() {
        return sourceList;
    }

    /**
     * Sets the value of the sourceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link DefinitionSetSourceList }
     *     
     */
    public void setSourceList(DefinitionSetSourceList value) {
        this.sourceList = value;
    }

    /**
     * Gets the value of the definitions property.
     * 
     * @return
     *     possible object is
     *     {@link DefinitionList }
     *     
     */
    public DefinitionList getDefinitions() {
        return definitions;
    }

    /**
     * Sets the value of the definitions property.
     * 
     * @param value
     *     allowed object is
     *     {@link DefinitionList }
     *     
     */
    public void setDefinitions(DefinitionList value) {
        this.definitions = value;
    }

}
