
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>SiteStatus complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SiteStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Uri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type" type="{http://smc.huawei.com/}SiteType"/>
 *         &lt;element name="Status" type="{http://smc.huawei.com/}ConfSiteStatus"/>
 *         &lt;element name="Volume" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="VideoSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsMute" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsRemoteMute" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsQuiet" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsLocalVideoOpen" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ParticipantType" type="{http://smc.huawei.com/}ParticipantType"/>
 *         &lt;element name="callFailedReson" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="Screens" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="McuId" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="IsDataOnline" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsBroadCast" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsChairMan" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsDisplaySite" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AccessCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteNetInfo" type="{http://smc.huawei.com/}SiteNetInfo" minOccurs="0"/>
 *         &lt;element name="IsSendLiveAmc" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SiteStatus", propOrder = {
    "uri",
    "name",
    "type",
    "status",
    "volume",
    "videoSource",
    "isMute",
    "isRemoteMute",
    "isQuiet",
    "isLocalVideoOpen",
    "participantType",
    "callFailedReson",
    "screens",
    "mcuId",
    "isDataOnline",
    "isBroadCast",
    "isChairMan",
    "isDisplaySite",
    "accessCode",
    "siteNetInfo",
    "isSendLiveAmc"
})
public class SiteStatus {

    @XmlElement(name = "Uri")
    protected String uri;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Type", required = true)
    @XmlSchemaType(name = "string")
    protected SiteType type;
    @XmlElement(name = "Status", required = true)
    @XmlSchemaType(name = "string")
    protected ConfSiteStatus status;
    @XmlElement(name = "Volume")
    protected int volume;
    @XmlElement(name = "VideoSource")
    protected String videoSource;
    @XmlElement(name = "IsMute")
    protected boolean isMute;
    @XmlElement(name = "IsRemoteMute", required = true, type = Boolean.class, nillable = true)
    protected Boolean isRemoteMute;
    @XmlElement(name = "IsQuiet")
    protected boolean isQuiet;
    @XmlElement(name = "IsLocalVideoOpen", required = true, type = Boolean.class, nillable = true)
    protected Boolean isLocalVideoOpen;
    @XmlElement(name = "ParticipantType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ParticipantType participantType;
    protected SmcErr2 callFailedReson;
    @XmlElement(name = "Screens", required = true, type = Integer.class, nillable = true)
    protected Integer screens;
    @XmlElement(name = "McuId", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long mcuId;
    @XmlElement(name = "IsDataOnline", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDataOnline;
    @XmlElement(name = "IsBroadCast", required = true, type = Boolean.class, nillable = true)
    protected Boolean isBroadCast;
    @XmlElement(name = "IsChairMan", required = true, type = Boolean.class, nillable = true)
    protected Boolean isChairMan;
    @XmlElement(name = "IsDisplaySite", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDisplaySite;
    @XmlElement(name = "AccessCode")
    protected String accessCode;
    @XmlElement(name = "SiteNetInfo")
    protected SiteNetInfo siteNetInfo;
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
     */
    public int getVolume() {
        return volume;
    }

    /**
     * ����volume���Ե�ֵ��
     * 
     */
    public void setVolume(int value) {
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
     */
    public boolean isIsMute() {
        return isMute;
    }

    /**
     * ����isMute���Ե�ֵ��
     * 
     */
    public void setIsMute(boolean value) {
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
     */
    public boolean isIsQuiet() {
        return isQuiet;
    }

    /**
     * ����isQuiet���Ե�ֵ��
     * 
     */
    public void setIsQuiet(boolean value) {
        this.isQuiet = value;
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
     * ��ȡcallFailedReson���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getCallFailedReson() {
        return callFailedReson;
    }

    /**
     * ����callFailedReson���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setCallFailedReson(SmcErr2 value) {
        this.callFailedReson = value;
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
     * ��ȡmcuId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMcuId() {
        return mcuId;
    }

    /**
     * ����mcuId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMcuId(Long value) {
        this.mcuId = value;
    }

    /**
     * ��ȡisDataOnline���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDataOnline() {
        return isDataOnline;
    }

    /**
     * ����isDataOnline���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDataOnline(Boolean value) {
        this.isDataOnline = value;
    }

    /**
     * ��ȡisBroadCast���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsBroadCast() {
        return isBroadCast;
    }

    /**
     * ����isBroadCast���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsBroadCast(Boolean value) {
        this.isBroadCast = value;
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
     * ��ȡaccessCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessCode() {
        return accessCode;
    }

    /**
     * ����accessCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessCode(String value) {
        this.accessCode = value;
    }

    /**
     * ��ȡsiteNetInfo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SiteNetInfo }
     *     
     */
    public SiteNetInfo getSiteNetInfo() {
        return siteNetInfo;
    }

    /**
     * ����siteNetInfo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SiteNetInfo }
     *     
     */
    public void setSiteNetInfo(SiteNetInfo value) {
        this.siteNetInfo = value;
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
