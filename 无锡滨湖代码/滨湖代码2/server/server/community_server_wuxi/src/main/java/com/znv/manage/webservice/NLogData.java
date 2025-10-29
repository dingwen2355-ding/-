
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>NLogData complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="NLogData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VideoRxLostNumber" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="VideoRxLostRatio" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="VideoTxLostNumber" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="VideoTxLostRatio" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="AudioRxLostNumber" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="AudioRxLostRatio" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="AudioTxLostNumber" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="AudioTxLostRatio" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="AmcRxLostNumber" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="AmcRxLostRatio" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="AmcTxLostNumber" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="AmcTxLostRatio" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="CrcLossRatio" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="NetDelay" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="JitterRxLossRatio" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="JitterTxLossRatio" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="SiteIFrameReqNum" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="McuIFrameReqNum" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="VideoRevSerialLossNum" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="VideoSendSerialLossNum" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="AudioRevSerialLossNum" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="AudioSendSerialLossNum" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="IFrameReqRevTotal" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="IFrameReqSendTotal" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NLogData", propOrder = {
    "videoRxLostNumber",
    "videoRxLostRatio",
    "videoTxLostNumber",
    "videoTxLostRatio",
    "audioRxLostNumber",
    "audioRxLostRatio",
    "audioTxLostNumber",
    "audioTxLostRatio",
    "amcRxLostNumber",
    "amcRxLostRatio",
    "amcTxLostNumber",
    "amcTxLostRatio",
    "crcLossRatio",
    "netDelay",
    "jitterRxLossRatio",
    "jitterTxLossRatio",
    "siteIFrameReqNum",
    "mcuIFrameReqNum",
    "videoRevSerialLossNum",
    "videoSendSerialLossNum",
    "audioRevSerialLossNum",
    "audioSendSerialLossNum",
    "iFrameReqRevTotal",
    "iFrameReqSendTotal"
})
public class NLogData {

    @XmlElement(name = "VideoRxLostNumber", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long videoRxLostNumber;
    @XmlElement(name = "VideoRxLostRatio", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long videoRxLostRatio;
    @XmlElement(name = "VideoTxLostNumber", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long videoTxLostNumber;
    @XmlElement(name = "VideoTxLostRatio", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long videoTxLostRatio;
    @XmlElement(name = "AudioRxLostNumber", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long audioRxLostNumber;
    @XmlElement(name = "AudioRxLostRatio", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long audioRxLostRatio;
    @XmlElement(name = "AudioTxLostNumber", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long audioTxLostNumber;
    @XmlElement(name = "AudioTxLostRatio", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long audioTxLostRatio;
    @XmlElement(name = "AmcRxLostNumber", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long amcRxLostNumber;
    @XmlElement(name = "AmcRxLostRatio", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long amcRxLostRatio;
    @XmlElement(name = "AmcTxLostNumber", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long amcTxLostNumber;
    @XmlElement(name = "AmcTxLostRatio", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long amcTxLostRatio;
    @XmlElement(name = "CrcLossRatio", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long crcLossRatio;
    @XmlElement(name = "NetDelay", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long netDelay;
    @XmlElement(name = "JitterRxLossRatio", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long jitterRxLossRatio;
    @XmlElement(name = "JitterTxLossRatio", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long jitterTxLossRatio;
    @XmlElement(name = "SiteIFrameReqNum", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long siteIFrameReqNum;
    @XmlElement(name = "McuIFrameReqNum", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long mcuIFrameReqNum;
    @XmlElement(name = "VideoRevSerialLossNum", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long videoRevSerialLossNum;
    @XmlElement(name = "VideoSendSerialLossNum", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long videoSendSerialLossNum;
    @XmlElement(name = "AudioRevSerialLossNum", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long audioRevSerialLossNum;
    @XmlElement(name = "AudioSendSerialLossNum", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long audioSendSerialLossNum;
    @XmlElement(name = "IFrameReqRevTotal", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long iFrameReqRevTotal;
    @XmlElement(name = "IFrameReqSendTotal", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long iFrameReqSendTotal;

    /**
     * ��ȡvideoRxLostNumber���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVideoRxLostNumber() {
        return videoRxLostNumber;
    }

    /**
     * ����videoRxLostNumber���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVideoRxLostNumber(Long value) {
        this.videoRxLostNumber = value;
    }

    /**
     * ��ȡvideoRxLostRatio���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVideoRxLostRatio() {
        return videoRxLostRatio;
    }

    /**
     * ����videoRxLostRatio���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVideoRxLostRatio(Long value) {
        this.videoRxLostRatio = value;
    }

    /**
     * ��ȡvideoTxLostNumber���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVideoTxLostNumber() {
        return videoTxLostNumber;
    }

    /**
     * ����videoTxLostNumber���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVideoTxLostNumber(Long value) {
        this.videoTxLostNumber = value;
    }

    /**
     * ��ȡvideoTxLostRatio���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVideoTxLostRatio() {
        return videoTxLostRatio;
    }

    /**
     * ����videoTxLostRatio���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVideoTxLostRatio(Long value) {
        this.videoTxLostRatio = value;
    }

    /**
     * ��ȡaudioRxLostNumber���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAudioRxLostNumber() {
        return audioRxLostNumber;
    }

    /**
     * ����audioRxLostNumber���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAudioRxLostNumber(Long value) {
        this.audioRxLostNumber = value;
    }

    /**
     * ��ȡaudioRxLostRatio���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAudioRxLostRatio() {
        return audioRxLostRatio;
    }

    /**
     * ����audioRxLostRatio���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAudioRxLostRatio(Long value) {
        this.audioRxLostRatio = value;
    }

    /**
     * ��ȡaudioTxLostNumber���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAudioTxLostNumber() {
        return audioTxLostNumber;
    }

    /**
     * ����audioTxLostNumber���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAudioTxLostNumber(Long value) {
        this.audioTxLostNumber = value;
    }

    /**
     * ��ȡaudioTxLostRatio���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAudioTxLostRatio() {
        return audioTxLostRatio;
    }

    /**
     * ����audioTxLostRatio���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAudioTxLostRatio(Long value) {
        this.audioTxLostRatio = value;
    }

    /**
     * ��ȡamcRxLostNumber���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAmcRxLostNumber() {
        return amcRxLostNumber;
    }

    /**
     * ����amcRxLostNumber���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAmcRxLostNumber(Long value) {
        this.amcRxLostNumber = value;
    }

    /**
     * ��ȡamcRxLostRatio���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAmcRxLostRatio() {
        return amcRxLostRatio;
    }

    /**
     * ����amcRxLostRatio���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAmcRxLostRatio(Long value) {
        this.amcRxLostRatio = value;
    }

    /**
     * ��ȡamcTxLostNumber���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAmcTxLostNumber() {
        return amcTxLostNumber;
    }

    /**
     * ����amcTxLostNumber���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAmcTxLostNumber(Long value) {
        this.amcTxLostNumber = value;
    }

    /**
     * ��ȡamcTxLostRatio���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAmcTxLostRatio() {
        return amcTxLostRatio;
    }

    /**
     * ����amcTxLostRatio���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAmcTxLostRatio(Long value) {
        this.amcTxLostRatio = value;
    }

    /**
     * ��ȡcrcLossRatio���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCrcLossRatio() {
        return crcLossRatio;
    }

    /**
     * ����crcLossRatio���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCrcLossRatio(Long value) {
        this.crcLossRatio = value;
    }

    /**
     * ��ȡnetDelay���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNetDelay() {
        return netDelay;
    }

    /**
     * ����netDelay���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNetDelay(Long value) {
        this.netDelay = value;
    }

    /**
     * ��ȡjitterRxLossRatio���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getJitterRxLossRatio() {
        return jitterRxLossRatio;
    }

    /**
     * ����jitterRxLossRatio���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setJitterRxLossRatio(Long value) {
        this.jitterRxLossRatio = value;
    }

    /**
     * ��ȡjitterTxLossRatio���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getJitterTxLossRatio() {
        return jitterTxLossRatio;
    }

    /**
     * ����jitterTxLossRatio���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setJitterTxLossRatio(Long value) {
        this.jitterTxLossRatio = value;
    }

    /**
     * ��ȡsiteIFrameReqNum���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSiteIFrameReqNum() {
        return siteIFrameReqNum;
    }

    /**
     * ����siteIFrameReqNum���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSiteIFrameReqNum(Long value) {
        this.siteIFrameReqNum = value;
    }

    /**
     * ��ȡmcuIFrameReqNum���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMcuIFrameReqNum() {
        return mcuIFrameReqNum;
    }

    /**
     * ����mcuIFrameReqNum���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMcuIFrameReqNum(Long value) {
        this.mcuIFrameReqNum = value;
    }

    /**
     * ��ȡvideoRevSerialLossNum���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVideoRevSerialLossNum() {
        return videoRevSerialLossNum;
    }

    /**
     * ����videoRevSerialLossNum���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVideoRevSerialLossNum(Long value) {
        this.videoRevSerialLossNum = value;
    }

    /**
     * ��ȡvideoSendSerialLossNum���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVideoSendSerialLossNum() {
        return videoSendSerialLossNum;
    }

    /**
     * ����videoSendSerialLossNum���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVideoSendSerialLossNum(Long value) {
        this.videoSendSerialLossNum = value;
    }

    /**
     * ��ȡaudioRevSerialLossNum���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAudioRevSerialLossNum() {
        return audioRevSerialLossNum;
    }

    /**
     * ����audioRevSerialLossNum���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAudioRevSerialLossNum(Long value) {
        this.audioRevSerialLossNum = value;
    }

    /**
     * ��ȡaudioSendSerialLossNum���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAudioSendSerialLossNum() {
        return audioSendSerialLossNum;
    }

    /**
     * ����audioSendSerialLossNum���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAudioSendSerialLossNum(Long value) {
        this.audioSendSerialLossNum = value;
    }

    /**
     * ��ȡiFrameReqRevTotal���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIFrameReqRevTotal() {
        return iFrameReqRevTotal;
    }

    /**
     * ����iFrameReqRevTotal���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIFrameReqRevTotal(Long value) {
        this.iFrameReqRevTotal = value;
    }

    /**
     * ��ȡiFrameReqSendTotal���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIFrameReqSendTotal() {
        return iFrameReqSendTotal;
    }

    /**
     * ����iFrameReqSendTotal���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIFrameReqSendTotal(Long value) {
        this.iFrameReqSendTotal = value;
    }

}
