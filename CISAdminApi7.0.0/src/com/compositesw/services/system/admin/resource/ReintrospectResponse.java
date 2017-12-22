
package com.compositesw.services.system.admin.resource;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;
import com.compositesw.services.system.util.common.OperationStatus;


/**
 * <p>Java class for reintrospectResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reintrospectResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="status" type="{http://www.compositesw.com/services/system/util/common}operationStatus"/>
 *         &lt;element name="reintrospectReport" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="changeEntry" type="{http://www.compositesw.com/services/system/admin/resource}reintrospectChangeEntry" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "reintrospectResponse", propOrder = {
    "status",
    "reintrospectReport"
})
@XmlSeeAlso({
    GetDataSourceReintrospectResultResponse.class,
    ReintrospectDataSourceResponse.class,
    CancelDataSourceReintrospectResponse.class
})
public class ReintrospectResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected OperationStatus status;
    protected ReintrospectResponse.ReintrospectReport reintrospectReport;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link OperationStatus }
     *     
     */
    public OperationStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationStatus }
     *     
     */
    public void setStatus(OperationStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the reintrospectReport property.
     * 
     * @return
     *     possible object is
     *     {@link ReintrospectResponse.ReintrospectReport }
     *     
     */
    public ReintrospectResponse.ReintrospectReport getReintrospectReport() {
        return reintrospectReport;
    }

    /**
     * Sets the value of the reintrospectReport property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReintrospectResponse.ReintrospectReport }
     *     
     */
    public void setReintrospectReport(ReintrospectResponse.ReintrospectReport value) {
        this.reintrospectReport = value;
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
     *         &lt;element name="changeEntry" type="{http://www.compositesw.com/services/system/admin/resource}reintrospectChangeEntry" maxOccurs="unbounded" minOccurs="0"/>
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
        "changeEntry"
    })
    public static class ReintrospectReport {

        protected List<ReintrospectChangeEntry> changeEntry;

        /**
         * Gets the value of the changeEntry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the changeEntry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getChangeEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ReintrospectChangeEntry }
         * 
         * 
         */
        public List<ReintrospectChangeEntry> getChangeEntry() {
            if (changeEntry == null) {
                changeEntry = new ArrayList<ReintrospectChangeEntry>();
            }
            return this.changeEntry;
        }

    }

}
