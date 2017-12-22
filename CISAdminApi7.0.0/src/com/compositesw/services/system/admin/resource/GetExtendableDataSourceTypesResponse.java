
package com.compositesw.services.system.admin.resource;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getExtendableDataSourceTypesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getExtendableDataSourceTypesResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="dataSourceTypes">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="dataSourceType" type="{http://www.compositesw.com/services/system/admin/resource}dataSourceTypeInfo" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getExtendableDataSourceTypesResponse", propOrder = {
    "dataSourceTypes"
})
public class GetExtendableDataSourceTypesResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected GetExtendableDataSourceTypesResponse.DataSourceTypes dataSourceTypes;

    /**
     * Gets the value of the dataSourceTypes property.
     * 
     * @return
     *     possible object is
     *     {@link GetExtendableDataSourceTypesResponse.DataSourceTypes }
     *     
     */
    public GetExtendableDataSourceTypesResponse.DataSourceTypes getDataSourceTypes() {
        return dataSourceTypes;
    }

    /**
     * Sets the value of the dataSourceTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetExtendableDataSourceTypesResponse.DataSourceTypes }
     *     
     */
    public void setDataSourceTypes(GetExtendableDataSourceTypesResponse.DataSourceTypes value) {
        this.dataSourceTypes = value;
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
     *         &lt;element name="dataSourceType" type="{http://www.compositesw.com/services/system/admin/resource}dataSourceTypeInfo" maxOccurs="unbounded" minOccurs="0"/>
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
        "dataSourceType"
    })
    public static class DataSourceTypes {

        protected List<DataSourceTypeInfo> dataSourceType;

        /**
         * Gets the value of the dataSourceType property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dataSourceType property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDataSourceType().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DataSourceTypeInfo }
         * 
         * 
         */
        public List<DataSourceTypeInfo> getDataSourceType() {
            if (dataSourceType == null) {
                dataSourceType = new ArrayList<DataSourceTypeInfo>();
            }
            return this.dataSourceType;
        }

    }

}
