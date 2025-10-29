
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
 *         &lt;element name="SetAudioSwitchResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
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
    "setAudioSwitchResult"
})
@XmlRootElement(name = "SetAudioSwitchResponse")
public class SetAudioSwitchResponse {

    @XmlElement(name = "SetAudioSwitchResult")
    protected SmcErr2 setAudioSwitchResult;

    /**
     * ��ȡsetAudioSwitchResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getSetAudioSwitchResult() {
        return setAudioSwitchResult;
    }

    /**
     * ����setAudioSwitchResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setSetAudioSwitchResult(SmcErr2 value) {
        this.setAudioSwitchResult = value;
    }

}
