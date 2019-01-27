
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.compositesw.services.system.util.common.ServerTaskResultResponse;


/**
 * <p>Java class for getIntrospectedResourceIdsResultResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getIntrospectedResourceIdsResultResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}serverTaskResultResponse">
 *       &lt;sequence>
 *         &lt;element name="lastUpdate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="resourceIdentifiers" type="{http://www.compositesw.com/services/system/admin/resource}pathTypePairList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getIntrospectedResourceIdsResultResponse", propOrder = {
    "lastUpdate",
    "resourceIdentifiers"
})
public class GetIntrospectedResourceIdsResultResponse
    extends ServerTaskResultResponse
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastUpdate;
    @XmlElement(required = true)
    protected PathTypePairList resourceIdentifiers;

    /**
     * Gets the value of the lastUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the value of the lastUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastUpdate(XMLGregorianCalendar value) {
        this.lastUpdate = value;
    }

    /**
     * Gets the value of the resourceIdentifiers property.
     * 
     * @return
     *     possible object is
     *     {@link PathTypePairList }
     *     
     */
    public PathTypePairList getResourceIdentifiers() {
        return resourceIdentifiers;
    }

    /**
     * Sets the value of the resourceIdentifiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathTypePairList }
     *     
     */
    public void setResourceIdentifiers(PathTypePairList value) {
        this.resourceIdentifiers = value;
    }

}
