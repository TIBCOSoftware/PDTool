
package com.compositesw.services.system.util.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


/**
 * <p>Java class for dataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sqlType" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="definition" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="nativeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="referencePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="referenceTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="proprietaryModel" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="trailingSpaces" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="xmlType" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="namespace" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="schema" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="referencePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="proprietaryModel" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pseudoType" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="definition" type="{http://www.compositesw.com/services/system/util/common}pseudoType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@javax.xml.bind.annotation.XmlType(name = "dataType", propOrder = {
    "sqlType",
    "xmlType",
    "pseudoType"
})
public class DataType {

    protected DataType.SqlType sqlType;
    protected DataType.XmlType xmlType;
    protected DataType.PseudoType pseudoType;

    /**
     * Gets the value of the sqlType property.
     * 
     * @return
     *     possible object is
     *     {@link DataType.SqlType }
     *     
     */
    public DataType.SqlType getSqlType() {
        return sqlType;
    }

    /**
     * Sets the value of the sqlType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataType.SqlType }
     *     
     */
    public void setSqlType(DataType.SqlType value) {
        this.sqlType = value;
    }

    /**
     * Gets the value of the xmlType property.
     * 
     * @return
     *     possible object is
     *     {@link DataType.XmlType }
     *     
     */
    public DataType.XmlType getXmlType() {
        return xmlType;
    }

    /**
     * Sets the value of the xmlType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataType.XmlType }
     *     
     */
    public void setXmlType(DataType.XmlType value) {
        this.xmlType = value;
    }

    /**
     * Gets the value of the pseudoType property.
     * 
     * @return
     *     possible object is
     *     {@link DataType.PseudoType }
     *     
     */
    public DataType.PseudoType getPseudoType() {
        return pseudoType;
    }

    /**
     * Sets the value of the pseudoType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataType.PseudoType }
     *     
     */
    public void setPseudoType(DataType.PseudoType value) {
        this.pseudoType = value;
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
     *         &lt;element name="definition" type="{http://www.compositesw.com/services/system/util/common}pseudoType"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @javax.xml.bind.annotation.XmlType(name = "", propOrder = {
        "definition"
    })
    public static class PseudoType {

        @XmlElement(required = true)
        protected com.compositesw.services.system.util.common.PseudoType definition;

        /**
         * Gets the value of the definition property.
         * 
         * @return
         *     possible object is
         *     {@link com.compositesw.services.system.util.common.PseudoType }
         *     
         */
        public com.compositesw.services.system.util.common.PseudoType getDefinition() {
            return definition;
        }

        /**
         * Sets the value of the definition property.
         * 
         * @param value
         *     allowed object is
         *     {@link com.compositesw.services.system.util.common.PseudoType }
         *     
         */
        public void setDefinition(com.compositesw.services.system.util.common.PseudoType value) {
            this.definition = value;
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
     *         &lt;element name="definition" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="nativeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="referencePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="referenceTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="proprietaryModel" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="trailingSpaces" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @javax.xml.bind.annotation.XmlType(name = "", propOrder = {
        "definition",
        "nativeType",
        "referencePath",
        "referenceTypeName",
        "proprietaryModel",
        "name",
        "trailingSpaces"
    })
    public static class SqlType {

        @XmlElement(required = true)
        protected String definition;
        protected String nativeType;
        protected String referencePath;
        protected String referenceTypeName;
        protected byte[] proprietaryModel;
        protected String name;
        protected Boolean trailingSpaces;

        /**
         * Gets the value of the definition property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDefinition() {
            return definition;
        }

        /**
         * Sets the value of the definition property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDefinition(String value) {
            this.definition = value;
        }

        /**
         * Gets the value of the nativeType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNativeType() {
            return nativeType;
        }

        /**
         * Sets the value of the nativeType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNativeType(String value) {
            this.nativeType = value;
        }

        /**
         * Gets the value of the referencePath property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getReferencePath() {
            return referencePath;
        }

        /**
         * Sets the value of the referencePath property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReferencePath(String value) {
            this.referencePath = value;
        }

        /**
         * Gets the value of the referenceTypeName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getReferenceTypeName() {
            return referenceTypeName;
        }

        /**
         * Sets the value of the referenceTypeName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReferenceTypeName(String value) {
            this.referenceTypeName = value;
        }

        /**
         * Gets the value of the proprietaryModel property.
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getProprietaryModel() {
            return proprietaryModel;
        }

        /**
         * Sets the value of the proprietaryModel property.
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setProprietaryModel(byte[] value) {
            this.proprietaryModel = ((byte[]) value);
        }

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the trailingSpaces property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isTrailingSpaces() {
            return trailingSpaces;
        }

        /**
         * Sets the value of the trailingSpaces property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setTrailingSpaces(Boolean value) {
            this.trailingSpaces = value;
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
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="namespace" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="schema" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="referencePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="proprietaryModel" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @javax.xml.bind.annotation.XmlType(name = "", propOrder = {
        "name",
        "namespace",
        "schema",
        "referencePath",
        "proprietaryModel"
    })
    public static class XmlType {

        @XmlElement(required = true)
        protected String name;
        @XmlElement(required = true)
        protected String namespace;
        protected String schema;
        protected String referencePath;
        protected byte[] proprietaryModel;

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the namespace property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNamespace() {
            return namespace;
        }

        /**
         * Sets the value of the namespace property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNamespace(String value) {
            this.namespace = value;
        }

        /**
         * Gets the value of the schema property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSchema() {
            return schema;
        }

        /**
         * Sets the value of the schema property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSchema(String value) {
            this.schema = value;
        }

        /**
         * Gets the value of the referencePath property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getReferencePath() {
            return referencePath;
        }

        /**
         * Sets the value of the referencePath property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReferencePath(String value) {
            this.referencePath = value;
        }

        /**
         * Gets the value of the proprietaryModel property.
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getProprietaryModel() {
            return proprietaryModel;
        }

        /**
         * Sets the value of the proprietaryModel property.
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setProprietaryModel(byte[] value) {
            this.proprietaryModel = ((byte[]) value);
        }

    }

}
