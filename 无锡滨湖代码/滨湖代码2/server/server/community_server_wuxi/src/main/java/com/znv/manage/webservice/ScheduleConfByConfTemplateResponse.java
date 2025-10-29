
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
 *         &lt;element name="ScheduleConfByConfTemplateResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
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
    "scheduleConfByConfTemplateResult",
    "confs"
})
@XmlRootElement(name = "ScheduleConfByConfTemplateResponse")
public class ScheduleConfByConfTemplateResponse {

    @XmlElement(name = "ScheduleConfByConfTemplateResult")
    protected SmcErr2 scheduleConfByConfTemplateResult;
    protected ArrayOfScheduledConfInfo confs;

    /**
     * ��ȡscheduleConfByConfTemplateResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getScheduleConfByConfTemplateResult() {
        return scheduleConfByConfTemplateResult;
    }

    /**
     * ����scheduleConfByConfTemplateResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setScheduleConfByConfTemplateResult(SmcErr2 value) {
        this.scheduleConfByConfTemplateResult = value;
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
