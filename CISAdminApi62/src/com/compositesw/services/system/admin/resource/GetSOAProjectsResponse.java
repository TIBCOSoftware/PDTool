
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getSOAProjectsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSOAProjectsResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="projects" type="{http://www.compositesw.com/services/system/admin/resource}projectList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSOAProjectsResponse", propOrder = {
    "projects"
})
public class GetSOAProjectsResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected ProjectList projects;

    /**
     * Gets the value of the projects property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectList }
     *     
     */
    public ProjectList getProjects() {
        return projects;
    }

    /**
     * Sets the value of the projects property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectList }
     *     
     */
    public void setProjects(ProjectList value) {
        this.projects = value;
    }

}
