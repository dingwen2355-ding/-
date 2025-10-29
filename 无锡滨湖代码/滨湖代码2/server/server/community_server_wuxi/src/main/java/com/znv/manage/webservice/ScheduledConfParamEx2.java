
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>ScheduledConfParamEx2 complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ScheduledConfParamEx2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AccessCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConfTime" type="{http://smc.huawei.com/}ScheduleTime" minOccurs="0"/>
 *         &lt;element name="BillCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaxSitesCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CPResource" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Rate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MediaEncryptType" type="{http://smc.huawei.com/}MediaEncryptType"/>
 *         &lt;element name="Presentation" type="{http://smc.huawei.com/}PresentationType"/>
 *         &lt;element name="IsLiveBroadcast" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsRecording" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ChairmanPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PresentationVideo" type="{http://smc.huawei.com/}VideoParam" minOccurs="0"/>
 *         &lt;element name="MainSiteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Notice" type="{http://smc.huawei.com/}ConferenceNotice" minOccurs="0"/>
 *         &lt;element name="ConferenceMode" type="{http://smc.huawei.com/}ConferenceMode"/>
 *         &lt;element name="ReservedCsdPorts" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Record" type="{http://smc.huawei.com/}RecordParam" minOccurs="0"/>
 *         &lt;element name="MainMcuId" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="IsDataConference" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ConfMediaType" type="{http://smc.huawei.com/}ConfStreamType"/>
 *         &lt;element name="CpMode" type="{http://smc.huawei.com/}ContinuousPresenceMode"/>
 *         &lt;element name="GroupUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MultiCsdChannel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LockVideoSourceNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EnableChairManContinuousPresence" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="EnableConfSignIn" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SignInTimeBeforeConf" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ConfOrgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConfOwner" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VMRID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HwPCTermType" type="{http://smc.huawei.com/}HwPCTermType"/>
 *         &lt;element name="IsSpecCTConf" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduledConfParamEx2", propOrder = {
    "id",
    "name",
    "accessCode",
    "confTime",
    "billCode",
    "password",
    "maxSitesCount",
    "cpResource",
    "rate",
    "mediaEncryptType",
    "presentation",
    "isLiveBroadcast",
    "isRecording",
    "chairmanPassword",
    "presentationVideo",
    "mainSiteUri",
    "notice",
    "conferenceMode",
    "reservedCsdPorts",
    "record",
    "mainMcuId",
    "isDataConference",
    "confMediaType",
    "cpMode",
    "groupUri",
    "multiCsdChannel",
    "lockVideoSourceNum",
    "enableChairManContinuousPresence",
    "enableConfSignIn",
    "signInTimeBeforeConf",
    "confOrgId",
    "confOwner",
    "vmrid",
    "hwPCTermType",
    "isSpecCTConf"
})
public class ScheduledConfParamEx2 {

    @XmlElement(name = "Id")
    protected ConferenceId id;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "AccessCode")
    protected String accessCode;
    @XmlElement(name = "ConfTime")
    protected ScheduleTime confTime;
    @XmlElement(name = "BillCode")
    protected String billCode;
    @XmlElement(name = "Password")
    protected String password;
    @XmlElement(name = "MaxSitesCount", required = true, type = Integer.class, nillable = true)
    protected Integer maxSitesCount;
    @XmlElement(name = "CPResource")
    protected int cpResource;
    @XmlElement(name = "Rate")
    protected String rate;
    @XmlElement(name = "MediaEncryptType", required = true)
    @XmlSchemaType(name = "string")
    protected MediaEncryptType mediaEncryptType;
    @XmlElement(name = "Presentation", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PresentationType presentation;
    @XmlElement(name = "IsLiveBroadcast")
    protected boolean isLiveBroadcast;
    @XmlElement(name = "IsRecording")
    protected boolean isRecording;
    @XmlElement(name = "ChairmanPassword")
    protected String chairmanPassword;
    @XmlElement(name = "PresentationVideo")
    protected VideoParam presentationVideo;
    @XmlElement(name = "MainSiteUri")
    protected String mainSiteUri;
    @XmlElement(name = "Notice")
    protected ConferenceNotice notice;
    @XmlElement(name = "ConferenceMode", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ConferenceMode conferenceMode;
    @XmlElement(name = "ReservedCsdPorts", required = true, type = Integer.class, nillable = true)
    protected Integer reservedCsdPorts;
    @XmlElement(name = "Record")
    protected RecordParam record;
    @XmlElement(name = "MainMcuId", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long mainMcuId;
    @XmlElement(name = "IsDataConference", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDataConference;
    @XmlElement(name = "ConfMediaType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ConfStreamType confMediaType;
    @XmlElement(name = "CpMode", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ContinuousPresenceMode cpMode;
    @XmlElement(name = "GroupUri")
    protected String groupUri;
    @XmlElement(name = "MultiCsdChannel")
    protected String multiCsdChannel;
    @XmlElement(name = "LockVideoSourceNum")
    protected String lockVideoSourceNum;
    @XmlElement(name = "EnableChairManContinuousPresence", required = true, type = Boolean.class, nillable = true)
    protected Boolean enableChairManContinuousPresence;
    @XmlElement(name = "EnableConfSignIn", required = true, type = Boolean.class, nillable = true)
    protected Boolean enableConfSignIn;
    @XmlElement(name = "SignInTimeBeforeConf", required = true, type = Integer.class, nillable = true)
    protected Integer signInTimeBeforeConf;
    @XmlElement(name = "ConfOrgId")
    protected String confOrgId;
    @XmlElement(name = "ConfOwner")
    protected String confOwner;
    @XmlElement(name = "VMRID")
    protected String vmrid;
    @XmlElement(name = "HwPCTermType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected HwPCTermType hwPCTermType;
    @XmlElement(name = "IsSpecCTConf", required = true, type = Boolean.class, nillable = true)
    protected Boolean isSpecCTConf;

    /**
     * ��ȡid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceId }
     *     
     */
    public ConferenceId getId() {
        return id;
    }

    /**
     * ����id���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceId }
     *     
     */
    public void setId(ConferenceId value) {
        this.id = value;
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
     * ��ȡconfTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ScheduleTime }
     *     
     */
    public ScheduleTime getConfTime() {
        return confTime;
    }

    /**
     * ����confTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduleTime }
     *     
     */
    public void setConfTime(ScheduleTime value) {
        this.confTime = value;
    }

    /**
     * ��ȡbillCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillCode() {
        return billCode;
    }

    /**
     * ����billCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillCode(String value) {
        this.billCode = value;
    }

    /**
     * ��ȡpassword���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * ����password���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * ��ȡmaxSitesCount���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxSitesCount() {
        return maxSitesCount;
    }

    /**
     * ����maxSitesCount���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxSitesCount(Integer value) {
        this.maxSitesCount = value;
    }

    /**
     * ��ȡcpResource���Ե�ֵ��
     * 
     */
    public int getCPResource() {
        return cpResource;
    }

    /**
     * ����cpResource���Ե�ֵ��
     * 
     */
    public void setCPResource(int value) {
        this.cpResource = value;
    }

    /**
     * ��ȡrate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRate() {
        return rate;
    }

    /**
     * ����rate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRate(String value) {
        this.rate = value;
    }

    /**
     * ��ȡmediaEncryptType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link MediaEncryptType }
     *     
     */
    public MediaEncryptType getMediaEncryptType() {
        return mediaEncryptType;
    }

    /**
     * ����mediaEncryptType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link MediaEncryptType }
     *     
     */
    public void setMediaEncryptType(MediaEncryptType value) {
        this.mediaEncryptType = value;
    }

    /**
     * ��ȡpresentation���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link PresentationType }
     *     
     */
    public PresentationType getPresentation() {
        return presentation;
    }

    /**
     * ����presentation���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link PresentationType }
     *     
     */
    public void setPresentation(PresentationType value) {
        this.presentation = value;
    }

    /**
     * ��ȡisLiveBroadcast���Ե�ֵ��
     * 
     */
    public boolean isIsLiveBroadcast() {
        return isLiveBroadcast;
    }

    /**
     * ����isLiveBroadcast���Ե�ֵ��
     * 
     */
    public void setIsLiveBroadcast(boolean value) {
        this.isLiveBroadcast = value;
    }

    /**
     * ��ȡisRecording���Ե�ֵ��
     * 
     */
    public boolean isIsRecording() {
        return isRecording;
    }

    /**
     * ����isRecording���Ե�ֵ��
     * 
     */
    public void setIsRecording(boolean value) {
        this.isRecording = value;
    }

    /**
     * ��ȡchairmanPassword���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChairmanPassword() {
        return chairmanPassword;
    }

    /**
     * ����chairmanPassword���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChairmanPassword(String value) {
        this.chairmanPassword = value;
    }

    /**
     * ��ȡpresentationVideo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link VideoParam }
     *     
     */
    public VideoParam getPresentationVideo() {
        return presentationVideo;
    }

    /**
     * ����presentationVideo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link VideoParam }
     *     
     */
    public void setPresentationVideo(VideoParam value) {
        this.presentationVideo = value;
    }

    /**
     * ��ȡmainSiteUri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainSiteUri() {
        return mainSiteUri;
    }

    /**
     * ����mainSiteUri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainSiteUri(String value) {
        this.mainSiteUri = value;
    }

    /**
     * ��ȡnotice���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceNotice }
     *     
     */
    public ConferenceNotice getNotice() {
        return notice;
    }

    /**
     * ����notice���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceNotice }
     *     
     */
    public void setNotice(ConferenceNotice value) {
        this.notice = value;
    }

    /**
     * ��ȡconferenceMode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceMode }
     *     
     */
    public ConferenceMode getConferenceMode() {
        return conferenceMode;
    }

    /**
     * ����conferenceMode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceMode }
     *     
     */
    public void setConferenceMode(ConferenceMode value) {
        this.conferenceMode = value;
    }

    /**
     * ��ȡreservedCsdPorts���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getReservedCsdPorts() {
        return reservedCsdPorts;
    }

    /**
     * ����reservedCsdPorts���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReservedCsdPorts(Integer value) {
        this.reservedCsdPorts = value;
    }

    /**
     * ��ȡrecord���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RecordParam }
     *     
     */
    public RecordParam getRecord() {
        return record;
    }

    /**
     * ����record���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RecordParam }
     *     
     */
    public void setRecord(RecordParam value) {
        this.record = value;
    }

    /**
     * ��ȡmainMcuId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMainMcuId() {
        return mainMcuId;
    }

    /**
     * ����mainMcuId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMainMcuId(Long value) {
        this.mainMcuId = value;
    }

    /**
     * ��ȡisDataConference���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDataConference() {
        return isDataConference;
    }

    /**
     * ����isDataConference���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDataConference(Boolean value) {
        this.isDataConference = value;
    }

    /**
     * ��ȡconfMediaType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConfStreamType }
     *     
     */
    public ConfStreamType getConfMediaType() {
        return confMediaType;
    }

    /**
     * ����confMediaType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConfStreamType }
     *     
     */
    public void setConfMediaType(ConfStreamType value) {
        this.confMediaType = value;
    }

    /**
     * ��ȡcpMode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ContinuousPresenceMode }
     *     
     */
    public ContinuousPresenceMode getCpMode() {
        return cpMode;
    }

    /**
     * ����cpMode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ContinuousPresenceMode }
     *     
     */
    public void setCpMode(ContinuousPresenceMode value) {
        this.cpMode = value;
    }

    /**
     * ��ȡgroupUri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupUri() {
        return groupUri;
    }

    /**
     * ����groupUri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupUri(String value) {
        this.groupUri = value;
    }

    /**
     * ��ȡmultiCsdChannel���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMultiCsdChannel() {
        return multiCsdChannel;
    }

    /**
     * ����multiCsdChannel���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMultiCsdChannel(String value) {
        this.multiCsdChannel = value;
    }

    /**
     * ��ȡlockVideoSourceNum���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLockVideoSourceNum() {
        return lockVideoSourceNum;
    }

    /**
     * ����lockVideoSourceNum���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLockVideoSourceNum(String value) {
        this.lockVideoSourceNum = value;
    }

    /**
     * ��ȡenableChairManContinuousPresence���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnableChairManContinuousPresence() {
        return enableChairManContinuousPresence;
    }

    /**
     * ����enableChairManContinuousPresence���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnableChairManContinuousPresence(Boolean value) {
        this.enableChairManContinuousPresence = value;
    }

    /**
     * ��ȡenableConfSignIn���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnableConfSignIn() {
        return enableConfSignIn;
    }

    /**
     * ����enableConfSignIn���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnableConfSignIn(Boolean value) {
        this.enableConfSignIn = value;
    }

    /**
     * ��ȡsignInTimeBeforeConf���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSignInTimeBeforeConf() {
        return signInTimeBeforeConf;
    }

    /**
     * ����signInTimeBeforeConf���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSignInTimeBeforeConf(Integer value) {
        this.signInTimeBeforeConf = value;
    }

    /**
     * ��ȡconfOrgId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfOrgId() {
        return confOrgId;
    }

    /**
     * ����confOrgId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfOrgId(String value) {
        this.confOrgId = value;
    }

    /**
     * ��ȡconfOwner���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfOwner() {
        return confOwner;
    }

    /**
     * ����confOwner���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfOwner(String value) {
        this.confOwner = value;
    }

    /**
     * ��ȡvmrid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVMRID() {
        return vmrid;
    }

    /**
     * ����vmrid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVMRID(String value) {
        this.vmrid = value;
    }

    /**
     * ��ȡhwPCTermType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link HwPCTermType }
     *     
     */
    public HwPCTermType getHwPCTermType() {
        return hwPCTermType;
    }

    /**
     * ����hwPCTermType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link HwPCTermType }
     *     
     */
    public void setHwPCTermType(HwPCTermType value) {
        this.hwPCTermType = value;
    }

    /**
     * ��ȡisSpecCTConf���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSpecCTConf() {
        return isSpecCTConf;
    }

    /**
     * ����isSpecCTConf���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSpecCTConf(Boolean value) {
        this.isSpecCTConf = value;
    }

}
