
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
 *         &lt;element name="VMRNumber" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
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
    "vmrNumber"
})
@XmlRootElement(name = "DelVMRInfo")
public class DelVMRInfo {

    @XmlElement(name = "VMRNumber")
    @XmlSchemaType(name = "unsignedInt")
    protected long vmrNumber;

    /**
     * ��ȡvmrNumber���Ե�ֵ��
     * 
     */
    public long getVMRNumber() {
        return vmrNumber;
    }

    /**
     * ����vmrNumber���Ե�ֵ��
     * 
     */
    public void setVMRNumber(long value) {
        this.vmrNumber = value;
    }

}
