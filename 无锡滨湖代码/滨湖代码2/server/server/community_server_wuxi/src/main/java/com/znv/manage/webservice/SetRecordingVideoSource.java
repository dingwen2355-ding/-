
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
 *         &lt;element name="isAutoRecording" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="videoSourceUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpVideoSource" type="{http://smc.huawei.com/}ContinuousPresenceParam" minOccurs="0"/>
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
    "isAutoRecording",
    "videoSourceUri",
    "cpVideoSource"
})
@XmlRootElement(name = "SetRecordingVideoSource")
public class SetRecordingVideoSource {

    protected ConferenceId confId;
    protected boolean isAutoRecording;
    protected String videoSourceUri;
    protected ContinuousPresenceParam cpVideoSource;

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
     * ��ȡisAutoRecording���Ե�ֵ��
     * 
     */
    public boolean isIsAutoRecording() {
        return isAutoRecording;
    }

    /**
     * ����isAutoRecording���Ե�ֵ��
     * 
     */
    public void setIsAutoRecording(boolean value) {
        this.isAutoRecording = value;
    }

    /**
     * ��ȡvideoSourceUri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVideoSourceUri() {
        return videoSourceUri;
    }

    /**
     * ����videoSourceUri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVideoSourceUri(String value) {
        this.videoSourceUri = value;
    }

    /**
     * ��ȡcpVideoSource���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ContinuousPresenceParam }
     *     
     */
    public ContinuousPresenceParam getCpVideoSource() {
        return cpVideoSource;
    }

    /**
     * ����cpVideoSource���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ContinuousPresenceParam }
     *     
     */
    public void setCpVideoSource(ContinuousPresenceParam value) {
        this.cpVideoSource = value;
    }

}
