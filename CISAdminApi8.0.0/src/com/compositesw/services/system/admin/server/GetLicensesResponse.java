
package com.compositesw.services.system.admin.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for getLicensesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getLicensesResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="licenses" type="{http://www.compositesw.com/services/system/admin/server}licenseList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLicensesResponse", propOrder = {
    "licenses"
})
public class GetLicensesResponse
    extends BaseResponse
{

    protected LicenseList licenses;

    /**
     * Gets the value of the licenses property.
     * 
     * @return
     *     possible object is
     *     {@link LicenseList }
     *     
     */
    public LicenseList getLicenses() {
        return licenses;
    }

    /**
     * Sets the value of the licenses property.
     * 
     * @param value
     *     allowed object is
     *     {@link LicenseList }
     *     
     */
    public void setLicenses(LicenseList value) {
        this.licenses = value;
    }

}
