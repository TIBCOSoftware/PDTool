
package com.compositesw.services.system.admin.execute;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.admin.resource.ResourceType;
import com.compositesw.services.system.admin.user.DomainMemberReferenceList;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for executeProcedureRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="executeProcedureRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="isBlocking" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="includeMetadata" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="inputs" type="{http://www.compositesw.com/services/system/admin/execute}parameterList" minOccurs="0"/>
 *         &lt;element name="dataServiceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.compositesw.com/services/system/admin/resource}resourceType"/>
 *         &lt;element name="users" type="{http://www.compositesw.com/services/system/admin/user}domainMemberReferenceList" minOccurs="0"/>
 *         &lt;element name="groups" type="{http://www.compositesw.com/services/system/admin/user}domainMemberReferenceList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "executeProcedureRequest", propOrder = {
    "isBlocking",
    "includeMetadata",
    "inputs",
    "dataServiceName",
    "path",
    "type",
    "users",
    "groups"
})
public class ExecuteProcedureRequest
    extends BaseRequest
{

    protected Boolean isBlocking;
    @XmlElement(defaultValue = "false")
    protected Boolean includeMetadata;
    protected ParameterList inputs;
    protected String dataServiceName;
    @XmlElement(required = true)
    protected String path;
    @XmlElement(required = true)
    protected ResourceType type;
    protected DomainMemberReferenceList users;
    protected DomainMemberReferenceList groups;

    /**
     * Gets the value of the isBlocking property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsBlocking() {
        return isBlocking;
    }

    /**
     * Sets the value of the isBlocking property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsBlocking(Boolean value) {
        this.isBlocking = value;
    }

    /**
     * Gets the value of the includeMetadata property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeMetadata() {
        return includeMetadata;
    }

    /**
     * Sets the value of the includeMetadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeMetadata(Boolean value) {
        this.includeMetadata = value;
    }

    /**
     * Gets the value of the inputs property.
     * 
     * @return
     *     possible object is
     *     {@link ParameterList }
     *     
     */
    public ParameterList getInputs() {
        return inputs;
    }

    /**
     * Sets the value of the inputs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParameterList }
     *     
     */
    public void setInputs(ParameterList value) {
        this.inputs = value;
    }

    /**
     * Gets the value of the dataServiceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataServiceName() {
        return dataServiceName;
    }

    /**
     * Sets the value of the dataServiceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataServiceName(String value) {
        this.dataServiceName = value;
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
     * Gets the value of the users property.
     * 
     * @return
     *     possible object is
     *     {@link DomainMemberReferenceList }
     *     
     */
    public DomainMemberReferenceList getUsers() {
        return users;
    }

    /**
     * Sets the value of the users property.
     * 
     * @param value
     *     allowed object is
     *     {@link DomainMemberReferenceList }
     *     
     */
    public void setUsers(DomainMemberReferenceList value) {
        this.users = value;
    }

    /**
     * Gets the value of the groups property.
     * 
     * @return
     *     possible object is
     *     {@link DomainMemberReferenceList }
     *     
     */
    public DomainMemberReferenceList getGroups() {
        return groups;
    }

    /**
     * Sets the value of the groups property.
     * 
     * @param value
     *     allowed object is
     *     {@link DomainMemberReferenceList }
     *     
     */
    public void setGroups(DomainMemberReferenceList value) {
        this.groups = value;
    }

}
