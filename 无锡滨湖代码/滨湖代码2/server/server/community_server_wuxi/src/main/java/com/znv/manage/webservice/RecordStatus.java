
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>RecordStatus complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="RecordStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IsSupportLiving" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsSupportRecording" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Status" type="{http://smc.huawei.com/}RecordingStatus"/>
 *         &lt;element name="IsAutoRecordingVideoSource" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="VideoSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LiveAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LiveUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LiveAuxUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsAutoSendStream" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecordStatus", propOrder = {
    "isSupportLiving",
    "isSupportRecording",
    "status",
    "isAutoRecordingVideoSource",
    "videoSource",
    "liveAddress",
    "liveUrl",
    "liveAuxUrl",
    "isAutoSendStream"
})
public class RecordStatus {

    @XmlElement(name = "IsSupportLiving")
    protected boolean isSupportLiving;
    @XmlElement(name = "IsSupportRecording")
    protected boolean isSupportRecording;
    @XmlElement(name = "Status", required = true)
    @XmlSchemaType(name = "string")
    protected RecordingStatus status;
    @XmlElement(name = "IsAutoRecordingVideoSource")
    protected boolean isAutoRecordingVideoSource;
    @XmlElement(name = "VideoSource")
    protected String videoSource;
    @XmlElement(name = "LiveAddress")
    protected String liveAddress;
    @XmlElement(name = "LiveUrl")
    protected String liveUrl;
    @XmlElement(name = "LiveAuxUrl")
    protected String liveAuxUrl;
    @XmlElement(name = "IsAutoSendStream")
    protected boolean isAutoSendStream;

    /**
     * ��ȡisSupportLiving���Ե�ֵ��
     * 
     */
    public boolean isIsSupportLiving() {
        return isSupportLiving;
    }

    /**
     * ����isSupportLiving���Ե�ֵ��
     * 
     */
    public void setIsSupportLiving(boolean value) {
        this.isSupportLiving = value;
    }

    /**
     * ��ȡisSupportRecording���Ե�ֵ��
     * 
     */
    public boolean isIsSupportRecording() {
        return isSupportRecording;
    }

    /**
     * ����isSupportRecording���Ե�ֵ��
     * 
     */
    public void setIsSupportRecording(boolean value) {
        this.isSupportRecording = value;
    }

    /**
     * ��ȡstatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RecordingStatus }
     *     
     */
    public RecordingStatus getStatus() {
        return status;
    }

    /**
     * ����status���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RecordingStatus }
     *     
     */
    public void setStatus(RecordingStatus value) {
        this.status = value;
    }

    /**
     * ��ȡisAutoRecordingVideoSource���Ե�ֵ��
     * 
     */
    public boolean isIsAutoRecordingVideoSource() {
        return isAutoRecordingVideoSource;
    }

    /**
     * ����isAutoRecordingVideoSource���Ե�ֵ��
     * 
     */
    public void setIsAutoRecordingVideoSource(boolean value) {
        this.isAutoRecordingVideoSource = value;
    }

    /**
     * ��ȡvideoSource���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVideoSource() {
        return videoSource;
    }

    /**
     * ����videoSource���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVideoSource(String value) {
        this.videoSource = value;
    }

    /**
     * ��ȡliveAddress���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiveAddress() {
        return liveAddress;
    }

    /**
     * ����liveAddress���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiveAddress(String value) {
        this.liveAddress = value;
    }

    /**
     * ��ȡliveUrl���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiveUrl() {
        return liveUrl;
    }

    /**
     * ����liveUrl���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiveUrl(String value) {
        this.liveUrl = value;
    }

    /**
     * ��ȡliveAuxUrl���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiveAuxUrl() {
        return liveAuxUrl;
    }

    /**
     * ����liveAuxUrl���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiveAuxUrl(String value) {
        this.liveAuxUrl = value;
    }

    /**
     * ��ȡisAutoSendStream���Ե�ֵ��
     * 
     */
    public boolean isIsAutoSendStream() {
        return isAutoSendStream;
    }

    /**
     * ����isAutoSendStream���Ե�ֵ��
     * 
     */
    public void setIsAutoSendStream(boolean value) {
        this.isAutoSendStream = value;
    }

}
