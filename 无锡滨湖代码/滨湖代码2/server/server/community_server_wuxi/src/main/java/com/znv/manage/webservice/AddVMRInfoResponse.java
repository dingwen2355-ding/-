
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


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
 *         &lt;element name="AddVMRInfoResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="vmrConfInfo" type="{http://smc.huawei.com/}VMRConfInfo" minOccurs="0"/>
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
    "addVMRInfoResult",
    "vmrConfInfo"
})
@XmlRootElement(name = "AddVMRInfoResponse")
public class AddVMRInfoResponse {

    @XmlElement(name = "AddVMRInfoResult")
    protected SmcErr2 addVMRInfoResult;
    protected VMRConfInfo vmrConfInfo;

    /**
     * ��ȡaddVMRInfoResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getAddVMRInfoResult() {
        return addVMRInfoResult;
    }

    /**
     * ����addVMRInfoResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setAddVMRInfoResult(SmcErr2 value) {
        this.addVMRInfoResult = value;
    }

    /**
     * ��ȡvmrConfInfo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link VMRConfInfo }
     *     
     */
    public VMRConfInfo getVmrConfInfo() {
        return vmrConfInfo;
    }

    /**
     * ����vmrConfInfo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link VMRConfInfo }
     *     
     */
    public void setVmrConfInfo(VMRConfInfo value) {
        this.vmrConfInfo = value;
    }

}
