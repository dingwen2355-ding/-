
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>ScheduleSiteParam2 complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ScheduleSiteParam2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Uri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type" type="{http://smc.huawei.com/}SiteType"/>
 *         &lt;element name="From" type="{http://smc.huawei.com/}SiteFrom"/>
 *         &lt;element name="DialingMode" type="{http://smc.huawei.com/}DialMode"/>
 *         &lt;element name="Rate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Video" type="{http://smc.huawei.com/}VideoParam" minOccurs="0"/>
 *         &lt;element name="IsLockVideoSource" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ParticipantType" type="{http://smc.huawei.com/}ParticipantType"/>
 *         &lt;element name="Dtmf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="McuId" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="SBCIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MCUIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsChairMan" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CustomCallingNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GroupID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MonitorCamera" type="{http://smc.huawei.com/}MonitorCameraParam" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleSiteParam2", propOrder = {
    "name",
    "uri",
    "type",
    "from",
    "dialingMode",
    "rate",
    "video",
    "isLockVideoSource",
    "participantType",
    "dtmf",
    "mcuId",
    "sbcip",
    "mcuip",
    "isChairMan",
    "customCallingNum",
    "groupID",
    "monitorCamera"
})
public class ScheduleSiteParam2 {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Uri")
    protected String uri;
    @XmlElement(name = "Type", required = true)
    @XmlSchemaType(name = "string")
    protected SiteType type;
    @XmlElement(name = "From", required = true)
    @XmlSchemaType(name = "string")
    protected SiteFrom from;
    @XmlElement(name = "DialingMode", required = true)
    @XmlSchemaType(name = "string")
    protected DialMode dialingMode;
    @XmlElement(name = "Rate")
    protected String rate;
    @XmlElement(name = "Video")
    protected VideoParam video;
    @XmlElement(name = "IsLockVideoSource", required = true, type = Boolean.class, nillable = true)
    protected Boolean isLockVideoSource;
    @XmlElement(name = "ParticipantType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ParticipantType participantType;
    @XmlElement(name = "Dtmf")
    protected String dtmf;
    @XmlElement(name = "McuId", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long mcuId;
    @XmlElement(name = "SBCIP")
    protected String sbcip;
    @XmlElement(name = "MCUIP")
    protected String mcuip;
    @XmlElement(name = "IsChairMan", required = true, type = Boolean.class, nillable = true)
    protected Boolean isChairMan;
    @XmlElement(name = "CustomCallingNum")
    protected String customCallingNum;
    @XmlElement(name = "GroupID")
    protected String groupID;
    @XmlElement(name = "MonitorCamera")
    protected MonitorCameraParam monitorCamera;

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
     * ��ȡfrom���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SiteFrom }
     *     
     */
    public SiteFrom getFrom() {
        return from;
    }

    /**
     * ����from���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SiteFrom }
     *     
     */
    public void setFrom(SiteFrom value) {
        this.from = value;
    }

    /**
     * ��ȡdialingMode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link DialMode }
     *     
     */
    public DialMode getDialingMode() {
        return dialingMode;
    }

    /**
     * ����dialingMode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link DialMode }
     *     
     */
    public void setDialingMode(DialMode value) {
        this.dialingMode = value;
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
     * ��ȡvideo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link VideoParam }
     *     
     */
    public VideoParam getVideo() {
        return video;
    }

    /**
     * ����video���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link VideoParam }
     *     
     */
    public void setVideo(VideoParam value) {
        this.video = value;
    }

    /**
     * ��ȡisLockVideoSource���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsLockVideoSource() {
        return isLockVideoSource;
    }

    /**
     * ����isLockVideoSource���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsLockVideoSource(Boolean value) {
        this.isLockVideoSource = value;
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
     * ��ȡdtmf���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtmf() {
        return dtmf;
    }

    /**
     * ����dtmf���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtmf(String value) {
        this.dtmf = value;
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
     * ��ȡsbcip���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSBCIP() {
        return sbcip;
    }

    /**
     * ����sbcip���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSBCIP(String value) {
        this.sbcip = value;
    }

    /**
     * ��ȡmcuip���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMCUIP() {
        return mcuip;
    }

    /**
     * ����mcuip���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMCUIP(String value) {
        this.mcuip = value;
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
     * ��ȡcustomCallingNum���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomCallingNum() {
        return customCallingNum;
    }

    /**
     * ����customCallingNum���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomCallingNum(String value) {
        this.customCallingNum = value;
    }

    /**
     * ��ȡgroupID���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupID() {
        return groupID;
    }

    /**
     * ����groupID���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupID(String value) {
        this.groupID = value;
    }

    /**
     * ��ȡmonitorCamera���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link MonitorCameraParam }
     *     
     */
    public MonitorCameraParam getMonitorCamera() {
        return monitorCamera;
    }

    /**
     * ����monitorCamera���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link MonitorCameraParam }
     *     
     */
    public void setMonitorCamera(MonitorCameraParam value) {
        this.monitorCamera = value;
    }

}
