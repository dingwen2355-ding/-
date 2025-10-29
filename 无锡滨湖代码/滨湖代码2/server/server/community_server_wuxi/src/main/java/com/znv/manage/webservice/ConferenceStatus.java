
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>ConferenceStatus complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ConferenceStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Id" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://smc.huawei.com/}ConfStatus"/>
 *         &lt;element name="Chair" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Broadcast" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Speaking" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Presentation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isLock" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isAudioSwitch" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="audioSwitchParam" type="{http://smc.huawei.com/}AudioSwitchParam" minOccurs="0"/>
 *         &lt;element name="BeginTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="EndTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="IsLockPresentation" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="LockPresentation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Recording" type="{http://smc.huawei.com/}RecordStatus" minOccurs="0"/>
 *         &lt;element name="ConferenceMode" type="{http://smc.huawei.com/}ConferenceMode"/>
 *         &lt;element name="MainMcuId" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="IsSupportMerge" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsSupportSplit" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ConfMediaType" type="{http://smc.huawei.com/}ConfStreamType"/>
 *         &lt;element name="ConfGlobalTalk" type="{http://smc.huawei.com/}GlobalTalkCtrlParam" minOccurs="0"/>
 *         &lt;element name="ConfAccessCode" type="{http://smc.huawei.com/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="EnableConfSignIn" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SignInTimeBeforeConf" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConferenceStatus", propOrder = {
    "orgId",
    "id",
    "name",
    "status",
    "chair",
    "broadcast",
    "speaking",
    "presentation",
    "isLock",
    "isAudioSwitch",
    "audioSwitchParam",
    "beginTime",
    "endTime",
    "isLockPresentation",
    "lockPresentation",
    "recording",
    "conferenceMode",
    "mainMcuId",
    "isSupportMerge",
    "isSupportSplit",
    "confMediaType",
    "confGlobalTalk",
    "confAccessCode",
    "enableConfSignIn",
    "signInTimeBeforeConf"
})
public class ConferenceStatus {

    @XmlElement(name = "OrgId")
    protected String orgId;
    @XmlElement(name = "Id")
    protected ConferenceId id;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Status", required = true)
    @XmlSchemaType(name = "string")
    protected ConfStatus status;
    @XmlElement(name = "Chair")
    protected String chair;
    @XmlElement(name = "Broadcast")
    protected String broadcast;
    @XmlElement(name = "Speaking")
    protected String speaking;
    @XmlElement(name = "Presentation")
    protected String presentation;
    protected boolean isLock;
    protected boolean isAudioSwitch;
    protected AudioSwitchParam audioSwitchParam;
    @XmlElement(name = "BeginTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar beginTime;
    @XmlElement(name = "EndTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endTime;
    @XmlElement(name = "IsLockPresentation", required = true, type = Boolean.class, nillable = true)
    protected Boolean isLockPresentation;
    @XmlElement(name = "LockPresentation")
    protected String lockPresentation;
    @XmlElement(name = "Recording")
    protected RecordStatus recording;
    @XmlElement(name = "ConferenceMode", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ConferenceMode conferenceMode;
    @XmlElement(name = "MainMcuId", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long mainMcuId;
    @XmlElement(name = "IsSupportMerge", required = true, type = Boolean.class, nillable = true)
    protected Boolean isSupportMerge;
    @XmlElement(name = "IsSupportSplit", required = true, type = Boolean.class, nillable = true)
    protected Boolean isSupportSplit;
    @XmlElement(name = "ConfMediaType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ConfStreamType confMediaType;
    @XmlElement(name = "ConfGlobalTalk")
    protected GlobalTalkCtrlParam confGlobalTalk;
    @XmlElement(name = "ConfAccessCode")
    protected ArrayOfString confAccessCode;
    @XmlElement(name = "EnableConfSignIn", required = true, type = Boolean.class, nillable = true)
    protected Boolean enableConfSignIn;
    @XmlElement(name = "SignInTimeBeforeConf", required = true, type = Integer.class, nillable = true)
    protected Integer signInTimeBeforeConf;

    /**
     * ��ȡorgId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * ����orgId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgId(String value) {
        this.orgId = value;
    }

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
     * ��ȡstatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConfStatus }
     *     
     */
    public ConfStatus getStatus() {
        return status;
    }

    /**
     * ����status���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConfStatus }
     *     
     */
    public void setStatus(ConfStatus value) {
        this.status = value;
    }

    /**
     * ��ȡchair���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChair() {
        return chair;
    }

    /**
     * ����chair���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChair(String value) {
        this.chair = value;
    }

    /**
     * ��ȡbroadcast���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBroadcast() {
        return broadcast;
    }

    /**
     * ����broadcast���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBroadcast(String value) {
        this.broadcast = value;
    }

    /**
     * ��ȡspeaking���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeaking() {
        return speaking;
    }

    /**
     * ����speaking���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeaking(String value) {
        this.speaking = value;
    }

    /**
     * ��ȡpresentation���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPresentation() {
        return presentation;
    }

    /**
     * ����presentation���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPresentation(String value) {
        this.presentation = value;
    }

    /**
     * ��ȡisLock���Ե�ֵ��
     * 
     */
    public boolean isIsLock() {
        return isLock;
    }

    /**
     * ����isLock���Ե�ֵ��
     * 
     */
    public void setIsLock(boolean value) {
        this.isLock = value;
    }

    /**
     * ��ȡisAudioSwitch���Ե�ֵ��
     * 
     */
    public boolean isIsAudioSwitch() {
        return isAudioSwitch;
    }

    /**
     * ����isAudioSwitch���Ե�ֵ��
     * 
     */
    public void setIsAudioSwitch(boolean value) {
        this.isAudioSwitch = value;
    }

    /**
     * ��ȡaudioSwitchParam���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link AudioSwitchParam }
     *     
     */
    public AudioSwitchParam getAudioSwitchParam() {
        return audioSwitchParam;
    }

    /**
     * ����audioSwitchParam���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link AudioSwitchParam }
     *     
     */
    public void setAudioSwitchParam(AudioSwitchParam value) {
        this.audioSwitchParam = value;
    }

    /**
     * ��ȡbeginTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBeginTime() {
        return beginTime;
    }

    /**
     * ����beginTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBeginTime(XMLGregorianCalendar value) {
        this.beginTime = value;
    }

    /**
     * ��ȡendTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndTime() {
        return endTime;
    }

    /**
     * ����endTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndTime(XMLGregorianCalendar value) {
        this.endTime = value;
    }

    /**
     * ��ȡisLockPresentation���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsLockPresentation() {
        return isLockPresentation;
    }

    /**
     * ����isLockPresentation���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsLockPresentation(Boolean value) {
        this.isLockPresentation = value;
    }

    /**
     * ��ȡlockPresentation���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLockPresentation() {
        return lockPresentation;
    }

    /**
     * ����lockPresentation���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLockPresentation(String value) {
        this.lockPresentation = value;
    }

    /**
     * ��ȡrecording���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RecordStatus }
     *     
     */
    public RecordStatus getRecording() {
        return recording;
    }

    /**
     * ����recording���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RecordStatus }
     *     
     */
    public void setRecording(RecordStatus value) {
        this.recording = value;
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
     * ��ȡisSupportMerge���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSupportMerge() {
        return isSupportMerge;
    }

    /**
     * ����isSupportMerge���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSupportMerge(Boolean value) {
        this.isSupportMerge = value;
    }

    /**
     * ��ȡisSupportSplit���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSupportSplit() {
        return isSupportSplit;
    }

    /**
     * ����isSupportSplit���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSupportSplit(Boolean value) {
        this.isSupportSplit = value;
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
     * ��ȡconfGlobalTalk���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link GlobalTalkCtrlParam }
     *     
     */
    public GlobalTalkCtrlParam getConfGlobalTalk() {
        return confGlobalTalk;
    }

    /**
     * ����confGlobalTalk���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link GlobalTalkCtrlParam }
     *     
     */
    public void setConfGlobalTalk(GlobalTalkCtrlParam value) {
        this.confGlobalTalk = value;
    }

    /**
     * ��ȡconfAccessCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getConfAccessCode() {
        return confAccessCode;
    }

    /**
     * ����confAccessCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setConfAccessCode(ArrayOfString value) {
        this.confAccessCode = value;
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

}
