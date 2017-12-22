
package com.compositesw.services.system.admin.resource;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeList;


/**
 * <p>Java class for updateDefinitionSetRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateDefinitionSetRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}pathDetailRequest">
 *       &lt;sequence>
 *         &lt;element name="sourceDocument" type="{http://www.compositesw.com/services/system/admin/resource}xmlDocumentSet" minOccurs="0"/>
 *         &lt;element name="definitions" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="definition" type="{http://www.compositesw.com/services/system/admin/resource}definition" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="annotation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attributes" type="{http://www.compositesw.com/services/system/util/common}attributeList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateDefinitionSetRequest", propOrder = {
    "sourceDocument",
    "definitions",
    "annotation",
    "attributes"
})
public class UpdateDefinitionSetRequest
    extends PathDetailRequest
{

    protected XmlDocumentSet sourceDocument;
    protected UpdateDefinitionSetRequest.Definitions definitions;
    protected String annotation;
    protected AttributeList attributes;

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
     * Gets the value of the definitions property.
     * 
     * @return
     *     possible object is
     *     {@link UpdateDefinitionSetRequest.Definitions }
     *     
     */
    public UpdateDefinitionSetRequest.Definitions getDefinitions() {
        return definitions;
    }

    /**
     * Sets the value of the definitions property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpdateDefinitionSetRequest.Definitions }
     *     
     */
    public void setDefinitions(UpdateDefinitionSetRequest.Definitions value) {
        this.definitions = value;
    }

    /**
     * Gets the value of the annotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * Sets the value of the annotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnotation(String value) {
        this.annotation = value;
    }

    /**
     * Gets the value of the attributes property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeList }
     *     
     */
    public AttributeList getAttributes() {
        return attributes;
    }

    /**
     * Sets the value of the attributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeList }
     *     
     */
    public void setAttributes(AttributeList value) {
        this.attributes = value;
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
     *         &lt;element name="definition" type="{http://www.compositesw.com/services/system/admin/resource}definition" maxOccurs="unbounded" minOccurs="0"/>
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
        "definition"
    })
    public static class Definitions {

        protected List<Definition> definition;

        /**
         * Gets the value of the definition property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the definition property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDefinition().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Definition }
         * 
         * 
         */
        public List<Definition> getDefinition() {
            if (definition == null) {
                definition = new ArrayList<Definition>();
            }
            return this.definition;
        }

    }

}
