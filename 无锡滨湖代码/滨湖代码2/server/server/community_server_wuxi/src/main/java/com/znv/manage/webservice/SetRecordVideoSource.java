
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
 *         &lt;element name="confId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="videoSourceType" type="{http://smc.huawei.com/}RecordVideoSourceType"/>
 *         &lt;element name="videoSource" type="{http://smc.huawei.com/}ContinuousPresenceParam" minOccurs="0"/>
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
    "videoSourceType",
    "videoSource"
})
@XmlRootElement(name = "SetRecordVideoSource")
public class SetRecordVideoSource {

    protected ConferenceId confId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected RecordVideoSourceType videoSourceType;
    protected ContinuousPresenceParam videoSource;

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
     * ��ȡvideoSourceType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RecordVideoSourceType }
     *     
     */
    public RecordVideoSourceType getVideoSourceType() {
        return videoSourceType;
    }

    /**
     * ����videoSourceType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RecordVideoSourceType }
     *     
     */
    public void setVideoSourceType(RecordVideoSourceType value) {
        this.videoSourceType = value;
    }

    /**
     * ��ȡvideoSource���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ContinuousPresenceParam }
     *     
     */
    public ContinuousPresenceParam getVideoSource() {
        return videoSource;
    }

    /**
     * ����videoSource���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ContinuousPresenceParam }
     *     
     */
    public void setVideoSource(ContinuousPresenceParam value) {
        this.videoSource = value;
    }

}
