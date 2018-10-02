
package com.compositesw.services.system.util.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.util.common.BaseResponse;


/**
 * <p>Java class for cbsGetAssignmentsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cbsGetAssignmentsResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="assignmentList" type="{http://www.compositesw.com/services/system/util/security}cbsAssignmentStandaloneListType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cbsGetAssignmentsResponse", propOrder = {
    "assignmentList"
})
public class CbsGetAssignmentsResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected CbsAssignmentStandaloneListType assignmentList;

    /**
     * Gets the value of the assignmentList property.
     * 
     * @return
     *     possible object is
     *     {@link CbsAssignmentStandaloneListType }
     *     
     */
    public CbsAssignmentStandaloneListType getAssignmentList() {
        return assignmentList;
    }

    /**
     * Sets the value of the assignmentList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CbsAssignmentStandaloneListType }
     *     
     */
    public void setAssignmentList(CbsAssignmentStandaloneListType value) {
        this.assignmentList = value;
    }

}
