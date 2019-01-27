
package com.compositesw.services.system.admin.resource;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.AttributeList;
import com.compositesw.services.system.util.common.BaseRequest;


/**
 * <p>Java class for updateDataSourceChildInfosRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateDataSourceChildInfosRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="childInfos">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="childInfo" type="{http://www.compositesw.com/services/system/admin/resource}dataSourceChildInfo" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="attributes" type="{http://www.compositesw.com/services/system/util/common}attributeList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateDataSourceChildInfosRequest", propOrder = {
    "path",
    "childInfos",
    "attributes"
})
public class UpdateDataSourceChildInfosRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected String path;
    @XmlElement(required = true)
    protected UpdateDataSourceChildInfosRequest.ChildInfos childInfos;
    protected AttributeList attributes;

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
     * Gets the value of the childInfos property.
     * 
     * @return
     *     possible object is
     *     {@link UpdateDataSourceChildInfosRequest.ChildInfos }
     *     
     */
    public UpdateDataSourceChildInfosRequest.ChildInfos getChildInfos() {
        return childInfos;
    }

    /**
     * Sets the value of the childInfos property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpdateDataSourceChildInfosRequest.ChildInfos }
     *     
     */
    public void setChildInfos(UpdateDataSourceChildInfosRequest.ChildInfos value) {
        this.childInfos = value;
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
     *         &lt;element name="childInfo" type="{http://www.compositesw.com/services/system/admin/resource}dataSourceChildInfo" maxOccurs="unbounded" minOccurs="0"/>
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
        "childInfo"
    })
    public static class ChildInfos {

        protected List<DataSourceChildInfo> childInfo;

        /**
         * Gets the value of the childInfo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the childInfo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getChildInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DataSourceChildInfo }
         * 
         * 
         */
        public List<DataSourceChildInfo> getChildInfo() {
            if (childInfo == null) {
                childInfo = new ArrayList<DataSourceChildInfo>();
            }
            return this.childInfo;
        }

    }

}
