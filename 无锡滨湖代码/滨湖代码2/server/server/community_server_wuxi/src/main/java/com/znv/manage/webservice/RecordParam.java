
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>RecordParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="RecordParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RseId" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="RecordVideoParam" type="{http://smc.huawei.com/}VideoParam" minOccurs="0"/>
 *         &lt;element name="IsAutoRecord" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsVoiceRecord" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
@XmlType(name = "RecordParam", propOrder = {
    "rseId",
    "recordVideoParam",
    "isAutoRecord",
    "isVoiceRecord",
    "liveUrl",
    "liveAuxUrl",
    "isAutoSendStream"
})
public class RecordParam {

    @XmlElement(name = "RseId")
    @XmlSchemaType(name = "unsignedInt")
    protected long rseId;
    @XmlElement(name = "RecordVideoParam")
    protected VideoParam recordVideoParam;
    @XmlElement(name = "IsAutoRecord")
    protected boolean isAutoRecord;
    @XmlElement(name = "IsVoiceRecord")
    protected boolean isVoiceRecord;
    @XmlElement(name = "LiveUrl")
    protected String liveUrl;
    @XmlElement(name = "LiveAuxUrl")
    protected String liveAuxUrl;
    @XmlElement(name = "IsAutoSendStream", required = true, type = Boolean.class, nillable = true)
    protected Boolean isAutoSendStream;

    /**
     * ��ȡrseId���Ե�ֵ��
     * 
     */
    public long getRseId() {
        return rseId;
    }

    /**
     * ����rseId���Ե�ֵ��
     * 
     */
    public void setRseId(long value) {
        this.rseId = value;
    }

    /**
     * ��ȡrecordVideoParam���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link VideoParam }
     *     
     */
    public VideoParam getRecordVideoParam() {
        return recordVideoParam;
    }

    /**
     * ����recordVideoParam���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link VideoParam }
     *     
     */
    public void setRecordVideoParam(VideoParam value) {
        this.recordVideoParam = value;
    }

    /**
     * ��ȡisAutoRecord���Ե�ֵ��
     * 
     */
    public boolean isIsAutoRecord() {
        return isAutoRecord;
    }

    /**
     * ����isAutoRecord���Ե�ֵ��
     * 
     */
    public void setIsAutoRecord(boolean value) {
        this.isAutoRecord = value;
    }

    /**
     * ��ȡisVoiceRecord���Ե�ֵ��
     * 
     */
    public boolean isIsVoiceRecord() {
        return isVoiceRecord;
    }

    /**
     * ����isVoiceRecord���Ե�ֵ��
     * 
     */
    public void setIsVoiceRecord(boolean value) {
        this.isVoiceRecord = value;
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
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsAutoSendStream() {
        return isAutoSendStream;
    }

    /**
     * ����isAutoSendStream���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAutoSendStream(Boolean value) {
        this.isAutoSendStream = value;
    }

}
