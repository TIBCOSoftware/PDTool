
package com.compositesw.services.system.admin.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for pathsRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pathsRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="paths" type="{http://www.compositesw.com/services/system/admin/server}pathList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pathsRequest", propOrder = {
    "paths"
})
@XmlSeeAlso({
    GetServerAttributeDefsRequest.class,
    GetServerAttributesRequest.class
})
public class PathsRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected PathList paths;

    /**
     * Gets the value of the paths property.
     * 
     * @return
     *     possible object is
     *     {@link PathList }
     *     
     */
    public PathList getPaths() {
        return paths;
    }

    /**
     * Sets the value of the paths property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathList }
     *     
     */
    public void setPaths(PathList value) {
        this.paths = value;
    }

}
