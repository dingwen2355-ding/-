
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>McuResourceItem complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="McuResourceItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResourceType" type="{http://smc.huawei.com/}McuResourceType"/>
 *         &lt;element name="ResourceStatus" type="{http://smc.huawei.com/}ArrayOfResourceOccupiedStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "McuResourceItem", propOrder = {
    "resourceType",
    "resourceStatus"
})
public class McuResourceItem {

    @XmlElement(name = "ResourceType", required = true)
    @XmlSchemaType(name = "string")
    protected McuResourceType resourceType;
    @XmlElement(name = "ResourceStatus")
    protected ArrayOfResourceOccupiedStatus resourceStatus;

    /**
     * ��ȡresourceType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link McuResourceType }
     *     
     */
    public McuResourceType getResourceType() {
        return resourceType;
    }

    /**
     * ����resourceType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link McuResourceType }
     *     
     */
    public void setResourceType(McuResourceType value) {
        this.resourceType = value;
    }

    /**
     * ��ȡresourceStatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfResourceOccupiedStatus }
     *     
     */
    public ArrayOfResourceOccupiedStatus getResourceStatus() {
        return resourceStatus;
    }

    /**
     * ����resourceStatus���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfResourceOccupiedStatus }
     *     
     */
    public void setResourceStatus(ArrayOfResourceOccupiedStatus value) {
        this.resourceStatus = value;
    }

}
