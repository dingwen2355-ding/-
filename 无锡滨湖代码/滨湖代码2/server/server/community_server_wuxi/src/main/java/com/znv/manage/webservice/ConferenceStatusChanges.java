
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>ConferenceStatusChanges complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ConferenceStatusChanges">
 *   &lt;complexContent>
 *     &lt;extension base="{http://smc.huawei.com/}NotificationChanges">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="RealConfId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
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
 *         &lt;element name="isLockPresentation" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="LockPresentation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConferenceMode" type="{http://smc.huawei.com/}ConferenceMode"/>
 *         &lt;element name="IsDataConf" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ConfGlobalTalk" type="{http://smc.huawei.com/}GlobalTalkCtrlParam" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConferenceStatusChanges", propOrder = {
    "id",
    "realConfId",
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
    "conferenceMode",
    "isDataConf",
    "confGlobalTalk"
})
public class ConferenceStatusChanges
    extends NotificationChanges
{

    @XmlElement(name = "Id")
    protected ConferenceId id;
    @XmlElement(name = "RealConfId")
    protected ConferenceId realConfId;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Status", required = true, nillable = true)
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
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean isLock;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean isAudioSwitch;
    protected AudioSwitchParam audioSwitchParam;
    @XmlElement(name = "BeginTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar beginTime;
    @XmlElement(name = "EndTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endTime;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean isLockPresentation;
    @XmlElement(name = "LockPresentation")
    protected String lockPresentation;
    @XmlElement(name = "ConferenceMode", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ConferenceMode conferenceMode;
    @XmlElement(name = "IsDataConf", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDataConf;
    @XmlElement(name = "ConfGlobalTalk")
    protected GlobalTalkCtrlParam confGlobalTalk;

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
     * ��ȡrealConfId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceId }
     *     
     */
    public ConferenceId getRealConfId() {
        return realConfId;
    }

    /**
     * ����realConfId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceId }
     *     
     */
    public void setRealConfId(ConferenceId value) {
        this.realConfId = value;
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
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsLock() {
        return isLock;
    }

    /**
     * ����isLock���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsLock(Boolean value) {
        this.isLock = value;
    }

    /**
     * ��ȡisAudioSwitch���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsAudioSwitch() {
        return isAudioSwitch;
    }

    /**
     * ����isAudioSwitch���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAudioSwitch(Boolean value) {
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
     * ��ȡisDataConf���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDataConf() {
        return isDataConf;
    }

    /**
     * ����isDataConf���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDataConf(Boolean value) {
        this.isDataConf = value;
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

}
