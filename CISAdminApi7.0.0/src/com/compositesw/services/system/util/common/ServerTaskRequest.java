
package com.compositesw.services.system.util.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.compositesw.services.system.admin.resource.GetIntrospectableResourceIdsTaskRequest;
import com.compositesw.services.system.admin.resource.GetIntrospectedResourceIdsTaskRequest;
import com.compositesw.services.system.admin.resource.IntrospectResourcesTaskRequest;


/**
 * <p>Java class for serverTaskRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="serverTaskRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.compositesw.com/services/system/util/common}baseRequest">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serverTaskRequest")
@XmlSeeAlso({
    GetIntrospectedResourceIdsTaskRequest.class,
    IntrospectResourcesTaskRequest.class,
    GetIntrospectableResourceIdsTaskRequest.class
})
public class ServerTaskRequest
    extends BaseRequest
{


}
