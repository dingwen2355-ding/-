
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>SiteNetSenParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SiteNetSenParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VideoTxProtocol" type="{http://smc.huawei.com/}VideoProtocol"/>
 *         &lt;element name="VideoTxBandwidth" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="AudioTxProtocol" type="{http://smc.huawei.com/}AudioProtocol"/>
 *         &lt;element name="AudioTxBandwidth" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="IsOpenSendAmc" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AmcTxProtocol" type="{http://smc.huawei.com/}VideoProtocol"/>
 *         &lt;element name="AmcTxBandwidth" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SiteNetSenParam", propOrder = {
    "videoTxProtocol",
    "videoTxBandwidth",
    "audioTxProtocol",
    "audioTxBandwidth",
    "isOpenSendAmc",
    "amcTxProtocol",
    "amcTxBandwidth"
})
public class SiteNetSenParam {

    @XmlElement(name = "VideoTxProtocol", required = true)
    @XmlSchemaType(name = "string")
    protected VideoProtocol videoTxProtocol;
    @XmlElement(name = "VideoTxBandwidth")
    @XmlSchemaType(name = "unsignedInt")
    protected long videoTxBandwidth;
    @XmlElement(name = "AudioTxProtocol", required = true)
    @XmlSchemaType(name = "string")
    protected AudioProtocol audioTxProtocol;
    @XmlElement(name = "AudioTxBandwidth")
    @XmlSchemaType(name = "unsignedInt")
    protected long audioTxBandwidth;
    @XmlElement(name = "IsOpenSendAmc")
    protected boolean isOpenSendAmc;
    @XmlElement(name = "AmcTxProtocol", required = true)
    @XmlSchemaType(name = "string")
    protected VideoProtocol amcTxProtocol;
    @XmlElement(name = "AmcTxBandwidth")
    @XmlSchemaType(name = "unsignedInt")
    protected long amcTxBandwidth;

    /**
     * ��ȡvideoTxProtocol���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link VideoProtocol }
     *     
     */
    public VideoProtocol getVideoTxProtocol() {
        return videoTxProtocol;
    }

    /**
     * ����videoTxProtocol���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link VideoProtocol }
     *     
     */
    public void setVideoTxProtocol(VideoProtocol value) {
        this.videoTxProtocol = value;
    }

    /**
     * ��ȡvideoTxBandwidth���Ե�ֵ��
     * 
     */
    public long getVideoTxBandwidth() {
        return videoTxBandwidth;
    }

    /**
     * ����videoTxBandwidth���Ե�ֵ��
     * 
     */
    public void setVideoTxBandwidth(long value) {
        this.videoTxBandwidth = value;
    }

    /**
     * ��ȡaudioTxProtocol���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link AudioProtocol }
     *     
     */
    public AudioProtocol getAudioTxProtocol() {
        return audioTxProtocol;
    }

    /**
     * ����audioTxProtocol���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link AudioProtocol }
     *     
     */
    public void setAudioTxProtocol(AudioProtocol value) {
        this.audioTxProtocol = value;
    }

    /**
     * ��ȡaudioTxBandwidth���Ե�ֵ��
     * 
     */
    public long getAudioTxBandwidth() {
        return audioTxBandwidth;
    }

    /**
     * ����audioTxBandwidth���Ե�ֵ��
     * 
     */
    public void setAudioTxBandwidth(long value) {
        this.audioTxBandwidth = value;
    }

    /**
     * ��ȡisOpenSendAmc���Ե�ֵ��
     * 
     */
    public boolean isIsOpenSendAmc() {
        return isOpenSendAmc;
    }

    /**
     * ����isOpenSendAmc���Ե�ֵ��
     * 
     */
    public void setIsOpenSendAmc(boolean value) {
        this.isOpenSendAmc = value;
    }

    /**
     * ��ȡamcTxProtocol���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link VideoProtocol }
     *     
     */
    public VideoProtocol getAmcTxProtocol() {
        return amcTxProtocol;
    }

    /**
     * ����amcTxProtocol���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link VideoProtocol }
     *     
     */
    public void setAmcTxProtocol(VideoProtocol value) {
        this.amcTxProtocol = value;
    }

    /**
     * ��ȡamcTxBandwidth���Ե�ֵ��
     * 
     */
    public long getAmcTxBandwidth() {
        return amcTxBandwidth;
    }

    /**
     * ����amcTxBandwidth���Ե�ֵ��
     * 
     */
    public void setAmcTxBandwidth(long value) {
        this.amcTxBandwidth = value;
    }

}
