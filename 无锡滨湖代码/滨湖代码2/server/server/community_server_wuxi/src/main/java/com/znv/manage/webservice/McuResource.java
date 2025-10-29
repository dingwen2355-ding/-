
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>McuResource complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="McuResource">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="McuId" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="ResourceList" type="{http://smc.huawei.com/}ArrayOfMcuResourceItem" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "McuResource", propOrder = {
    "mcuId",
    "resourceList"
})
public class McuResource {

    @XmlElement(name = "McuId")
    @XmlSchemaType(name = "unsignedInt")
    protected long mcuId;
    @XmlElement(name = "ResourceList")
    protected ArrayOfMcuResourceItem resourceList;

    /**
     * ��ȡmcuId���Ե�ֵ��
     * 
     */
    public long getMcuId() {
        return mcuId;
    }

    /**
     * ����mcuId���Ե�ֵ��
     * 
     */
    public void setMcuId(long value) {
        this.mcuId = value;
    }

    /**
     * ��ȡresourceList���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMcuResourceItem }
     *     
     */
    public ArrayOfMcuResourceItem getResourceList() {
        return resourceList;
    }

    /**
     * ����resourceList���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMcuResourceItem }
     *     
     */
    public void setResourceList(ArrayOfMcuResourceItem value) {
        this.resourceList = value;
    }

}
