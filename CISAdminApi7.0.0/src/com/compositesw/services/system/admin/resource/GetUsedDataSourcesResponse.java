
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getUsedDataSourcesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getUsedDataSourcesResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="lineageResourceList" type="{http://www.compositesw.com/services/system/admin/resource}lineageResourceList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUsedDataSourcesResponse", propOrder = {
    "lineageResourceList"
})
public class GetUsedDataSourcesResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected LineageResourceList lineageResourceList;

    /**
     * Gets the value of the lineageResourceList property.
     * 
     * @return
     *     possible object is
     *     {@link LineageResourceList }
     *     
     */
    public LineageResourceList getLineageResourceList() {
        return lineageResourceList;
    }

    /**
     * Sets the value of the lineageResourceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link LineageResourceList }
     *     
     */
    public void setLineageResourceList(LineageResourceList value) {
        this.lineageResourceList = value;
    }

}
