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
 * 				Server Attribute Choice Type: This selection provides a choice between server attributes and server attribute definitions.
 * 			
 * 
 * <p>Java class for ServerAttributeChoiceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServerAttributeChoiceType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="serverAttribute" type="{http://www.tibco.com/ps/deploytool/modules}ServerAttributeType" maxOccurs="unbounded"/&gt;
 *         &lt;element name="serverAttributeDef" type="{http://www.tibco.com/ps/deploytool/modules}ServerAttributeDefType" maxOccurs="unbounded"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServerAttributeChoiceType", propOrder = {
    "serverAttribute",
    "serverAttributeDef"
})
public class ServerAttributeChoiceType {

    protected List<ServerAttributeType> serverAttribute;
    protected List<ServerAttributeDefType> serverAttributeDef;

    /**
     * Gets the value of the serverAttribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serverAttribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServerAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServerAttributeType }
     * 
     * 
     */
    public List<ServerAttributeType> getServerAttribute() {
        if (serverAttribute == null) {
            serverAttribute = new ArrayList<ServerAttributeType>();
        }
        return this.serverAttribute;
    }

    /**
     * Gets the value of the serverAttributeDef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serverAttributeDef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServerAttributeDef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServerAttributeDefType }
     * 
     * 
     */
    public List<ServerAttributeDefType> getServerAttributeDef() {
        if (serverAttributeDef == null) {
            serverAttributeDef = new ArrayList<ServerAttributeDefType>();
        }
        return this.serverAttributeDef;
    }

}
