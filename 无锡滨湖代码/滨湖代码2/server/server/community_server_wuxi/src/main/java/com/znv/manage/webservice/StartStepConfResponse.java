
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
 *         &lt;element name="StartStepConfResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="confInfo" type="{http://smc.huawei.com/}ConferenceInfo" minOccurs="0"/>
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
    "startStepConfResult",
    "confInfo"
})
@XmlRootElement(name = "StartStepConfResponse")
public class StartStepConfResponse {

    @XmlElement(name = "StartStepConfResult")
    protected SmcErr2 startStepConfResult;
    protected ConferenceInfo confInfo;

    /**
     * ��ȡstartStepConfResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getStartStepConfResult() {
        return startStepConfResult;
    }

    /**
     * ����startStepConfResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setStartStepConfResult(SmcErr2 value) {
        this.startStepConfResult = value;
    }

    /**
     * ��ȡconfInfo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceInfo }
     *     
     */
    public ConferenceInfo getConfInfo() {
        return confInfo;
    }

    /**
     * ����confInfo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceInfo }
     *     
     */
    public void setConfInfo(ConferenceInfo value) {
        this.confInfo = value;
    }

}
