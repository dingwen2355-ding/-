
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
 *         &lt;element name="confTemplateParam" type="{http://smc.huawei.com/}ScheduledConfTemplateParamEx2" minOccurs="0"/>
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
    "confTemplateParam"
})
@XmlRootElement(name = "ScheduleConfByConfTemplate")
public class ScheduleConfByConfTemplate {

    protected ScheduledConfTemplateParamEx2 confTemplateParam;

    /**
     * ��ȡconfTemplateParam���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ScheduledConfTemplateParamEx2 }
     *     
     */
    public ScheduledConfTemplateParamEx2 getConfTemplateParam() {
        return confTemplateParam;
    }

    /**
     * ����confTemplateParam���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduledConfTemplateParamEx2 }
     *     
     */
    public void setConfTemplateParam(ScheduledConfTemplateParamEx2 value) {
        this.confTemplateParam = value;
    }

}
