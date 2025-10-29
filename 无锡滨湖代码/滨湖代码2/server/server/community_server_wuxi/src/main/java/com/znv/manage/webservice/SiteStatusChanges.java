
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>SiteStatusChanges complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SiteStatusChanges">
 *   &lt;complexContent>
 *     &lt;extension base="{http://smc.huawei.com/}NotificationChanges">
 *       &lt;sequence>
 *         &lt;element name="Uri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://smc.huawei.com/}ConfSiteStatus"/>
 *         &lt;element name="Volume" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="VideoSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsMute" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsRemoteMute" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsQuiet" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Type" type="{http://smc.huawei.com/}SiteType"/>
 *         &lt;element name="CallFailedReason" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="ParticipantType" type="{http://smc.huawei.com/}ParticipantType"/>
 *         &lt;element name="Screens" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IsLocalVideoOpen" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsDataJoin" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsChairMan" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsDisplaySite" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsSendLiveAmc" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SiteStatusChanges", propOrder = {
    "uri",
    "name",
    "status",
    "volume",
    "videoSource",
    "isMute",
    "isRemoteMute",
    "isQuiet",
    "type",
    "callFailedReason",
    "participantType",
    "screens",
    "isLocalVideoOpen",
    "isDataJoin",
    "isChairMan",
    "isDisplaySite",
    "isSendLiveAmc"
})
public class SiteStatusChanges
    extends NotificationChanges
{

    @XmlElement(name = "Uri")
    protected String uri;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Status", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ConfSiteStatus status;
    @XmlElement(name = "Volume", required = true, type = Integer.class, nillable = true)
    protected Integer volume;
    @XmlElement(name = "VideoSource")
    protected String videoSource;
    @XmlElement(name = "IsMute", required = true, type = Boolean.class, nillable = true)
    protected Boolean isMute;
    @XmlElement(name = "IsRemoteMute", required = true, type = Boolean.class, nillable = true)
    protected Boolean isRemoteMute;
    @XmlElement(name = "IsQuiet", required = true, type = Boolean.class, nillable = true)
    protected Boolean isQuiet;
    @XmlElement(name = "Type", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected SiteType type;
    @XmlElement(name = "CallFailedReason")
    protected SmcErr2 callFailedReason;
    @XmlElement(name = "ParticipantType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ParticipantType participantType;
    @XmlElement(name = "Screens", required = true, type = Integer.class, nillable = true)
    protected Integer screens;
    @XmlElement(name = "IsLocalVideoOpen", required = true, type = Boolean.class, nillable = true)
    protected Boolean isLocalVideoOpen;
    @XmlElement(name = "IsDataJoin", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDataJoin;
    @XmlElement(name = "IsChairMan", required = true, type = Boolean.class, nillable = true)
    protected Boolean isChairMan;
    @XmlElement(name = "IsDisplaySite", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDisplaySite;
    @XmlElement(name = "IsSendLiveAmc", required = true, type = Boolean.class, nillable = true)
    protected Boolean isSendLiveAmc;

    /**
     * ��ȡuri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUri() {
        return uri;
    }

    /**
     * ����uri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUri(String value) {
        this.uri = value;
    }

    /**
     * ��ȡname���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * ����name���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * ��ȡstatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConfSiteStatus }
     *     
     */
    public ConfSiteStatus getStatus() {
        return status;
    }

    /**
     * ����status���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConfSiteStatus }
     *     
     */
    public void setStatus(ConfSiteStatus value) {
        this.status = value;
    }

    /**
     * ��ȡvolume���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVolume() {
        return volume;
    }

    /**
     * ����volume���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVolume(Integer value) {
        this.volume = value;
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
     * ��ȡisMute���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsMute() {
        return isMute;
    }

    /**
     * ����isMute���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsMute(Boolean value) {
        this.isMute = value;
    }

    /**
     * ��ȡisRemoteMute���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsRemoteMute() {
        return isRemoteMute;
    }

    /**
     * ����isRemoteMute���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsRemoteMute(Boolean value) {
        this.isRemoteMute = value;
    }

    /**
     * ��ȡisQuiet���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsQuiet() {
        return isQuiet;
    }

    /**
     * ����isQuiet���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsQuiet(Boolean value) {
        this.isQuiet = value;
    }

    /**
     * ��ȡtype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SiteType }
     *     
     */
    public SiteType getType() {
        return type;
    }

    /**
     * ����type���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SiteType }
     *     
     */
    public void setType(SiteType value) {
        this.type = value;
    }

    /**
     * ��ȡcallFailedReason���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getCallFailedReason() {
        return callFailedReason;
    }

    /**
     * ����callFailedReason���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setCallFailedReason(SmcErr2 value) {
        this.callFailedReason = value;
    }

    /**
     * ��ȡparticipantType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ParticipantType }
     *     
     */
    public ParticipantType getParticipantType() {
        return participantType;
    }

    /**
     * ����participantType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ParticipantType }
     *     
     */
    public void setParticipantType(ParticipantType value) {
        this.participantType = value;
    }

    /**
     * ��ȡscreens���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getScreens() {
        return screens;
    }

    /**
     * ����screens���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setScreens(Integer value) {
        this.screens = value;
    }

    /**
     * ��ȡisLocalVideoOpen���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsLocalVideoOpen() {
        return isLocalVideoOpen;
    }

    /**
     * ����isLocalVideoOpen���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsLocalVideoOpen(Boolean value) {
        this.isLocalVideoOpen = value;
    }

    /**
     * ��ȡisDataJoin���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDataJoin() {
        return isDataJoin;
    }

    /**
     * ����isDataJoin���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDataJoin(Boolean value) {
        this.isDataJoin = value;
    }

    /**
     * ��ȡisChairMan���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsChairMan() {
        return isChairMan;
    }

    /**
     * ����isChairMan���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsChairMan(Boolean value) {
        this.isChairMan = value;
    }

    /**
     * ��ȡisDisplaySite���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDisplaySite() {
        return isDisplaySite;
    }

    /**
     * ����isDisplaySite���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDisplaySite(Boolean value) {
        this.isDisplaySite = value;
    }

    /**
     * ��ȡisSendLiveAmc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSendLiveAmc() {
        return isSendLiveAmc;
    }

    /**
     * ����isSendLiveAmc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSendLiveAmc(Boolean value) {
        this.isSendLiveAmc = value;
    }

}
