
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
 *         &lt;element name="EditFutureConfResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="confs" type="{http://smc.huawei.com/}ArrayOfScheduledConfInfo" minOccurs="0"/>
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
    "editFutureConfResult",
    "confs"
})
@XmlRootElement(name = "EditFutureConfResponse")
public class EditFutureConfResponse {

    @XmlElement(name = "EditFutureConfResult")
    protected SmcErr2 editFutureConfResult;
    protected ArrayOfScheduledConfInfo confs;

    /**
     * ��ȡeditFutureConfResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getEditFutureConfResult() {
        return editFutureConfResult;
    }

    /**
     * ����editFutureConfResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setEditFutureConfResult(SmcErr2 value) {
        this.editFutureConfResult = value;
    }

    /**
     * ��ȡconfs���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfScheduledConfInfo }
     *     
     */
    public ArrayOfScheduledConfInfo getConfs() {
        return confs;
    }

    /**
     * ����confs���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfScheduledConfInfo }
     *     
     */
    public void setConfs(ArrayOfScheduledConfInfo value) {
        this.confs = value;
    }

}
