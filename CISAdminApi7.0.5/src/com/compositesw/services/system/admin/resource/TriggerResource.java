
package com.compositesw.services.system.admin.resource;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeList;


/**
 * <p>Java class for triggerResource complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="triggerResource">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/admin/resource}resource">
 *       &lt;sequence>
 *         &lt;element name="conditionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="conditionSchedule" type="{http://www.compositesw.com/services/system/admin/resource}schedule" minOccurs="0"/>
 *         &lt;element name="conditionAttributes" type="{http://www.compositesw.com/services/system/util/common}attributeList" minOccurs="0"/>
 *         &lt;element name="actionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="actionAttributes" type="{http://www.compositesw.com/services/system/util/common}attributeList" minOccurs="0"/>
 *         &lt;element name="maxEventsQueued" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "triggerResource", propOrder = {
    "rest"
})
public class TriggerResource
    extends Resource
{

    @XmlElementRefs({
        @XmlElementRef(name = "actionAttributes", namespace = "http://www.compositesw.com/services/system/admin/resource", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "conditionAttributes", namespace = "http://www.compositesw.com/services/system/admin/resource", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "actionType", namespace = "http://www.compositesw.com/services/system/admin/resource", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "conditionType", namespace = "http://www.compositesw.com/services/system/admin/resource", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "enabled", namespace = "http://www.compositesw.com/services/system/admin/resource", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "maxEventsQueued", namespace = "http://www.compositesw.com/services/system/admin/resource", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "conditionSchedule", namespace = "http://www.compositesw.com/services/system/admin/resource", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> rest;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "Enabled" is used by two different parts of a schema. See: 
     * line 2099 of file:/C:/MyFiles/git/ASAssets_GIT_Repo/PDTool/CISAdminApi7.0.5/wsdl/CisAdminApi.wsdl
     * line 2053 of file:/C:/MyFiles/git/ASAssets_GIT_Repo/PDTool/CISAdminApi7.0.5/wsdl/CisAdminApi.wsdl
     * <p>
     * To get rid of this property, apply a property customization to one 
     * of both of the following declarations to change their names: 
     * Gets the value of the rest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link AttributeList }{@code >}
     * {@link JAXBElement }{@code <}{@link AttributeList }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     * {@link JAXBElement }{@code <}{@link Schedule }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getRest() {
        if (rest == null) {
            rest = new ArrayList<JAXBElement<?>>();
        }
        return this.rest;
    }

}
