
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="vmrInfo" type="{http://smc.huawei.com/}VMRInfo" minOccurs="0"/>
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
    "vmrInfo"
})
@XmlRootElement(name = "AddVMRInfo")
public class AddVMRInfo {

    protected VMRInfo vmrInfo;

    /**
     * ��ȡvmrInfo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link VMRInfo }
     *     
     */
    public VMRInfo getVmrInfo() {
        return vmrInfo;
    }

    /**
     * ����vmrInfo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link VMRInfo }
     *     
     */
    public void setVmrInfo(VMRInfo value) {
        this.vmrInfo = value;
    }

}
