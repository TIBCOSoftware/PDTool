
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for project complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="project">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="projectName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="projectRootPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="deploymentID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bundleSymbolicName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="owner" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ownerId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="resourceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "project", propOrder = {
    "projectName",
    "projectRootPath",
    "deploymentID",
    "bundleSymbolicName",
    "owner",
    "ownerId",
    "resourceType",
    "status"
})
public class Project {

    @XmlElement(required = true)
    protected String projectName;
    @XmlElement(required = true)
    protected String projectRootPath;
    @XmlElement(required = true)
    protected String deploymentID;
    @XmlElement(required = true)
    protected String bundleSymbolicName;
    @XmlElement(required = true)
    protected String owner;
    protected int ownerId;
    @XmlElement(required = true)
    protected String resourceType;
    @XmlElement(required = true)
    protected String status;

    /**
     * Gets the value of the projectName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the value of the projectName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectName(String value) {
        this.projectName = value;
    }

    /**
     * Gets the value of the projectRootPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectRootPath() {
        return projectRootPath;
    }

    /**
     * Sets the value of the projectRootPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectRootPath(String value) {
        this.projectRootPath = value;
    }

    /**
     * Gets the value of the deploymentID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeploymentID() {
        return deploymentID;
    }

    /**
     * Sets the value of the deploymentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeploymentID(String value) {
        this.deploymentID = value;
    }

    /**
     * Gets the value of the bundleSymbolicName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBundleSymbolicName() {
        return bundleSymbolicName;
    }

    /**
     * Sets the value of the bundleSymbolicName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBundleSymbolicName(String value) {
        this.bundleSymbolicName = value;
    }

    /**
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwner(String value) {
        this.owner = value;
    }

    /**
     * Gets the value of the ownerId property.
     * 
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Sets the value of the ownerId property.
     * 
     */
    public void setOwnerId(int value) {
        this.ownerId = value;
    }

    /**
     * Gets the value of the resourceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * Sets the value of the resourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceType(String value) {
        this.resourceType = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
