//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.16 at 10:25:54 AM EDT 
//


package com.tibco.ps.deploytool.modules;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				This is a list of resources for which use as a filter against the actual Composite datasource(s) to perform comparisons or test executions.  
 * 				A wild card ".*" may be used at the catalog or schema level to denote that all resources under that level should be compared or executed.  
 * 				A fully qualified resource may be identified as well. This of this as a filter that can be fine-tuned for one or more resources. 
 * 				If left empty then all resources are compared or executed. Overlapping resource wild cards will use the highest level specified in this list.					
 * 					Examples: 
 * 					CAT.* (include all resources under the catalog CAT), 
 * 					CAT.SCHEMA.* (include all resources under the schema located at CAT.SCHEMA)
 * 					CAT.SCHEMA.VIEW1 (include the resource specified by CAT.SCHEMA.VIEW1)
 * 			
 * 
 * <p>Java class for RegressionResourcesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RegressionResourcesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="resource" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegressionResourcesType", propOrder = {
    "resource"
})
public class RegressionResourcesType {

    protected List<String> resource;

    /**
     * Gets the value of the resource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getResource() {
        if (resource == null) {
            resource = new ArrayList<String>();
        }
        return this.resource;
    }

}