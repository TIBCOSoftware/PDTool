
package com.compositesw.services.system.admin.resource;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.Attribute;
import com.compositesw.services.system.util.common.AttributeList;


/**
 * <p>Java class for resource complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resource">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.compositesw.com/services/system/admin/resource}resourceType"/>
 *         &lt;element name="subtype" type="{http://www.compositesw.com/services/system/admin/resource}resourceSubType"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="changeId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="introspectState" type="{http://www.compositesw.com/services/system/admin/resource}introspectState" minOccurs="0"/>
 *         &lt;element name="ownerDomain" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ownerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="impactLevel" type="{http://www.compositesw.com/services/system/admin/resource}impactLevel" minOccurs="0"/>
 *         &lt;element name="impactMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="lockState" type="{http://www.compositesw.com/services/system/admin/resource}lockState" minOccurs="0"/>
 *         &lt;element name="hints" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="hint" type="{http://www.compositesw.com/services/system/util/common}attribute" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="annotation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attributes" type="{http://www.compositesw.com/services/system/util/common}attributeList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resource", propOrder = {
    "name",
    "path",
    "type",
    "subtype",
    "id",
    "changeId",
    "version",
    "introspectState",
    "ownerDomain",
    "ownerName",
    "impactLevel",
    "impactMessage",
    "enabled",
    "lockState",
    "hints",
    "annotation",
    "attributes"
})
@XmlSeeAlso({
    TreeResource.class,
    LinkResource.class,
    TriggerResource.class,
    ProcedureResource.class,
    TableResource.class,
    DefinitionSetResource.class,
    ContainerResource.class
})
public class Resource {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String path;
    @XmlElement(required = true)
    protected ResourceType type;
    @XmlElement(required = true)
    protected ResourceSubType subtype;
    protected String id;
    protected Integer changeId;
    protected String version;
    protected IntrospectState introspectState;
    protected String ownerDomain;
    protected String ownerName;
    protected ImpactLevel impactLevel;
    protected String impactMessage;
    protected Boolean enabled;
    protected LockState lockState;
    protected Resource.Hints hints;
    protected String annotation;
    protected AttributeList attributes;

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
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceType }
     *     
     */
    public ResourceType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceType }
     *     
     */
    public void setType(ResourceType value) {
        this.type = value;
    }

    /**
     * Gets the value of the subtype property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceSubType }
     *     
     */
    public ResourceSubType getSubtype() {
        return subtype;
    }

    /**
     * Sets the value of the subtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceSubType }
     *     
     */
    public void setSubtype(ResourceSubType value) {
        this.subtype = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the changeId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getChangeId() {
        return changeId;
    }

    /**
     * Sets the value of the changeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setChangeId(Integer value) {
        this.changeId = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the introspectState property.
     * 
     * @return
     *     possible object is
     *     {@link IntrospectState }
     *     
     */
    public IntrospectState getIntrospectState() {
        return introspectState;
    }

    /**
     * Sets the value of the introspectState property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntrospectState }
     *     
     */
    public void setIntrospectState(IntrospectState value) {
        this.introspectState = value;
    }

    /**
     * Gets the value of the ownerDomain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnerDomain() {
        return ownerDomain;
    }

    /**
     * Sets the value of the ownerDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnerDomain(String value) {
        this.ownerDomain = value;
    }

    /**
     * Gets the value of the ownerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * Sets the value of the ownerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnerName(String value) {
        this.ownerName = value;
    }

    /**
     * Gets the value of the impactLevel property.
     * 
     * @return
     *     possible object is
     *     {@link ImpactLevel }
     *     
     */
    public ImpactLevel getImpactLevel() {
        return impactLevel;
    }

    /**
     * Sets the value of the impactLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImpactLevel }
     *     
     */
    public void setImpactLevel(ImpactLevel value) {
        this.impactLevel = value;
    }

    /**
     * Gets the value of the impactMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImpactMessage() {
        return impactMessage;
    }

    /**
     * Sets the value of the impactMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImpactMessage(String value) {
        this.impactMessage = value;
    }

    /**
     * Gets the value of the enabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the lockState property.
     * 
     * @return
     *     possible object is
     *     {@link LockState }
     *     
     */
    public LockState getLockState() {
        return lockState;
    }

    /**
     * Sets the value of the lockState property.
     * 
     * @param value
     *     allowed object is
     *     {@link LockState }
     *     
     */
    public void setLockState(LockState value) {
        this.lockState = value;
    }

    /**
     * Gets the value of the hints property.
     * 
     * @return
     *     possible object is
     *     {@link Resource.Hints }
     *     
     */
    public Resource.Hints getHints() {
        return hints;
    }

    /**
     * Sets the value of the hints property.
     * 
     * @param value
     *     allowed object is
     *     {@link Resource.Hints }
     *     
     */
    public void setHints(Resource.Hints value) {
        this.hints = value;
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
     *         &lt;element name="hint" type="{http://www.compositesw.com/services/system/util/common}attribute" maxOccurs="unbounded" minOccurs="0"/>
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
        "hint"
    })
    public static class Hints {

        protected List<Attribute> hint;

        /**
         * Gets the value of the hint property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hint property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHint().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Attribute }
         * 
         * 
         */
        public List<Attribute> getHint() {
            if (hint == null) {
                hint = new ArrayList<Attribute>();
            }
            return this.hint;
        }

    }

}
