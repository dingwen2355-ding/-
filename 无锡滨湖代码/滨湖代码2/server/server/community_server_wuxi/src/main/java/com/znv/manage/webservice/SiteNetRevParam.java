
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>SiteNetRevParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SiteNetRevParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VideoRxProtocol" type="{http://smc.huawei.com/}VideoProtocol"/>
 *         &lt;element name="VideoRxBandwidth" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="AudioRxProtocol" type="{http://smc.huawei.com/}AudioProtocol"/>
 *         &lt;element name="AudioRxBandwidth" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="IsOpenRevAmc" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AmcRxProtocol" type="{http://smc.huawei.com/}VideoProtocol"/>
 *         &lt;element name="AmcRxBandwidth" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SiteNetRevParam", propOrder = {
    "videoRxProtocol",
    "videoRxBandwidth",
    "audioRxProtocol",
    "audioRxBandwidth",
    "isOpenRevAmc",
    "amcRxProtocol",
    "amcRxBandwidth"
})
public class SiteNetRevParam {

    @XmlElement(name = "VideoRxProtocol", required = true)
    @XmlSchemaType(name = "string")
    protected VideoProtocol videoRxProtocol;
    @XmlElement(name = "VideoRxBandwidth")
    @XmlSchemaType(name = "unsignedInt")
    protected long videoRxBandwidth;
    @XmlElement(name = "AudioRxProtocol", required = true)
    @XmlSchemaType(name = "string")
    protected AudioProtocol audioRxProtocol;
    @XmlElement(name = "AudioRxBandwidth")
    @XmlSchemaType(name = "unsignedInt")
    protected long audioRxBandwidth;
    @XmlElement(name = "IsOpenRevAmc")
    protected boolean isOpenRevAmc;
    @XmlElement(name = "AmcRxProtocol", required = true)
    @XmlSchemaType(name = "string")
    protected VideoProtocol amcRxProtocol;
    @XmlElement(name = "AmcRxBandwidth")
    @XmlSchemaType(name = "unsignedInt")
    protected long amcRxBandwidth;

    /**
     * ��ȡvideoRxProtocol���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link VideoProtocol }
     *     
     */
    public VideoProtocol getVideoRxProtocol() {
        return videoRxProtocol;
    }

    /**
     * ����videoRxProtocol���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link VideoProtocol }
     *     
     */
    public void setVideoRxProtocol(VideoProtocol value) {
        this.videoRxProtocol = value;
    }

    /**
     * ��ȡvideoRxBandwidth���Ե�ֵ��
     * 
     */
    public long getVideoRxBandwidth() {
        return videoRxBandwidth;
    }

    /**
     * ����videoRxBandwidth���Ե�ֵ��
     * 
     */
    public void setVideoRxBandwidth(long value) {
        this.videoRxBandwidth = value;
    }

    /**
     * ��ȡaudioRxProtocol���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link AudioProtocol }
     *     
     */
    public AudioProtocol getAudioRxProtocol() {
        return audioRxProtocol;
    }

    /**
     * ����audioRxProtocol���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link AudioProtocol }
     *     
     */
    public void setAudioRxProtocol(AudioProtocol value) {
        this.audioRxProtocol = value;
    }

    /**
     * ��ȡaudioRxBandwidth���Ե�ֵ��
     * 
     */
    public long getAudioRxBandwidth() {
        return audioRxBandwidth;
    }

    /**
     * ����audioRxBandwidth���Ե�ֵ��
     * 
     */
    public void setAudioRxBandwidth(long value) {
        this.audioRxBandwidth = value;
    }

    /**
     * ��ȡisOpenRevAmc���Ե�ֵ��
     * 
     */
    public boolean isIsOpenRevAmc() {
        return isOpenRevAmc;
    }

    /**
     * ����isOpenRevAmc���Ե�ֵ��
     * 
     */
    public void setIsOpenRevAmc(boolean value) {
        this.isOpenRevAmc = value;
    }

    /**
     * ��ȡamcRxProtocol���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link VideoProtocol }
     *     
     */
    public VideoProtocol getAmcRxProtocol() {
        return amcRxProtocol;
    }

    /**
     * ����amcRxProtocol���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link VideoProtocol }
     *     
     */
    public void setAmcRxProtocol(VideoProtocol value) {
        this.amcRxProtocol = value;
    }

    /**
     * ��ȡamcRxBandwidth���Ե�ֵ��
     * 
     */
    public long getAmcRxBandwidth() {
        return amcRxBandwidth;
    }

    /**
     * ����amcRxBandwidth���Ե�ֵ��
     * 
     */
    public void setAmcRxBandwidth(long value) {
        this.amcRxBandwidth = value;
    }

}
