
package com.compositesw.services.system.util.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.admin.resource.GetIntrospectableResourceIdsResultResponse;
import com.compositesw.services.system.admin.resource.GetIntrospectedResourceIdsResultResponse;
import com.compositesw.services.system.admin.resource.IntrospectResourcesResultResponse;
import com.compositesw.services.system.admin.resource.IntrospectResourcesTaskResponse;


/**
 * <p>Java class for serverTaskResultResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="serverTaskResultResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}serverTaskResponse">
 *       &lt;sequence>
 *         &lt;element name="completed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serverTaskResultResponse", propOrder = {
    "completed"
})
@XmlSeeAlso({
    IntrospectResourcesResultResponse.class,
    GetIntrospectableResourceIdsResultResponse.class,
    GetIntrospectedResourceIdsResultResponse.class,
    IntrospectResourcesTaskResponse.class
})
public class ServerTaskResultResponse
    extends ServerTaskResponse
{

    protected boolean completed;

    /**
     * Gets the value of the completed property.
     * 
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the value of the completed property.
     * 
     */
    public void setCompleted(boolean value) {
        this.completed = value;
    }

}
