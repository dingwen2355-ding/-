
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
 *         &lt;element name="confId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="enable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="audioSwitchParam" type="{http://smc.huawei.com/}AudioSwitchParam" minOccurs="0"/>
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
    "confId",
    "enable",
    "audioSwitchParam"
})
@XmlRootElement(name = "SetAudioSwitch")
public class SetAudioSwitch {

    protected ConferenceId confId;
    protected boolean enable;
    protected AudioSwitchParam audioSwitchParam;

    /**
     * ��ȡconfId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceId }
     *     
     */
    public ConferenceId getConfId() {
        return confId;
    }

    /**
     * ����confId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceId }
     *     
     */
    public void setConfId(ConferenceId value) {
        this.confId = value;
    }

    /**
     * ��ȡenable���Ե�ֵ��
     * 
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * ����enable���Ե�ֵ��
     * 
     */
    public void setEnable(boolean value) {
        this.enable = value;
    }

    /**
     * ��ȡaudioSwitchParam���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link AudioSwitchParam }
     *     
     */
    public AudioSwitchParam getAudioSwitchParam() {
        return audioSwitchParam;
    }

    /**
     * ����audioSwitchParam���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link AudioSwitchParam }
     *     
     */
    public void setAudioSwitchParam(AudioSwitchParam value) {
        this.audioSwitchParam = value;
    }

}
