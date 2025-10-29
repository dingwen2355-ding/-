
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>AdhocConfTempplateParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="AdhocConfTempplateParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AdhocConfTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AccessCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Duration" type="{http://smc.huawei.com/}TimeSpanEx" minOccurs="0"/>
 *         &lt;element name="BillCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CPResource" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Rate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MediaEncryptType" type="{http://smc.huawei.com/}MediaEncryptType"/>
 *         &lt;element name="IsLiveBroadcast" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsRecording" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Presentation" type="{http://smc.huawei.com/}PresentationType"/>
 *         &lt;element name="ChairmanPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PresentationVideo" type="{http://smc.huawei.com/}VideoParam" minOccurs="0"/>
 *         &lt;element name="MainSiteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Notice" type="{http://smc.huawei.com/}ConferenceNotice" minOccurs="0"/>
 *         &lt;element name="MaxSitesCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ReservedCsdPorts" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="State" type="{http://smc.huawei.com/}ConfTemplateRegState"/>
 *         &lt;element name="Record" type="{http://smc.huawei.com/}RecordParam" minOccurs="0"/>
 *         &lt;element name="MainMcuId" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="IsDataConference" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Sites" type="{http://smc.huawei.com/}ArrayOfScheduleSiteParam2" minOccurs="0"/>
 *         &lt;element name="ConfMediaType" type="{http://smc.huawei.com/}ConfStreamType"/>
 *         &lt;element name="CpMode" type="{http://smc.huawei.com/}ContinuousPresenceMode"/>
 *         &lt;element name="MultiCsdChannel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "AdhocConfTempplateParam", propOrder = {
    "adhocConfTemplateId",
    "name",
    "accessCode",
    "duration",
    "billCode",
    "password",
    "cpResource",
    "rate",
    "mediaEncryptType",
    "isLiveBroadcast",
    "isRecording",
    "presentation",
    "chairmanPassword",
    "presentationVideo",
    "mainSiteUri",
    "notice",
    "maxSitesCount",
    "reservedCsdPorts",
    "state",
    "record",
    "mainMcuId",
    "isDataConference",
    "sites",
    "confMediaType",
    "cpMode",
    "multiCsdChannel",
    "isSpecCTConf"
})
public class AdhocConfTempplateParam {

    @XmlElement(name = "AdhocConfTemplateId", required = true, type = Long.class, nillable = true)
    protected Long adhocConfTemplateId;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "AccessCode")
    protected String accessCode;
    @XmlElement(name = "Duration")
    protected TimeSpanEx duration;
    @XmlElement(name = "BillCode")
    protected String billCode;
    @XmlElement(name = "Password")
    protected String password;
    @XmlElement(name = "CPResource")
    protected int cpResource;
    @XmlElement(name = "Rate")
    protected String rate;
    @XmlElement(name = "MediaEncryptType", required = true)
    @XmlSchemaType(name = "string")
    protected MediaEncryptType mediaEncryptType;
    @XmlElement(name = "IsLiveBroadcast")
    protected boolean isLiveBroadcast;
    @XmlElement(name = "IsRecording")
    protected boolean isRecording;
    @XmlElement(name = "Presentation", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PresentationType presentation;
    @XmlElement(name = "ChairmanPassword")
    protected String chairmanPassword;
    @XmlElement(name = "PresentationVideo")
    protected VideoParam presentationVideo;
    @XmlElement(name = "MainSiteUri")
    protected String mainSiteUri;
    @XmlElement(name = "Notice")
    protected ConferenceNotice notice;
    @XmlElement(name = "MaxSitesCount", required = true, type = Integer.class, nillable = true)
    protected Integer maxSitesCount;
    @XmlElement(name = "ReservedCsdPorts", required = true, type = Integer.class, nillable = true)
    protected Integer reservedCsdPorts;
    @XmlElement(name = "State", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ConfTemplateRegState state;
    @XmlElement(name = "Record")
    protected RecordParam record;
    @XmlElement(name = "MainMcuId", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long mainMcuId;
    @XmlElement(name = "IsDataConference", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDataConference;
    @XmlElement(name = "Sites")
    protected ArrayOfScheduleSiteParam2 sites;
    @XmlElement(name = "ConfMediaType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ConfStreamType confMediaType;
    @XmlElement(name = "CpMode", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ContinuousPresenceMode cpMode;
    @XmlElement(name = "MultiCsdChannel")
    protected String multiCsdChannel;
    @XmlElement(name = "IsSpecCTConf", required = true, type = Boolean.class, nillable = true)
    protected Boolean isSpecCTConf;

    /**
     * ��ȡadhocConfTemplateId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAdhocConfTemplateId() {
        return adhocConfTemplateId;
    }

    /**
     * ����adhocConfTemplateId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAdhocConfTemplateId(Long value) {
        this.adhocConfTemplateId = value;
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
     * ��ȡduration���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link TimeSpanEx }
     *     
     */
    public TimeSpanEx getDuration() {
        return duration;
    }

    /**
     * ����duration���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link TimeSpanEx }
     *     
     */
    public void setDuration(TimeSpanEx value) {
        this.duration = value;
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
     * ��ȡstate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConfTemplateRegState }
     *     
     */
    public ConfTemplateRegState getState() {
        return state;
    }

    /**
     * ����state���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConfTemplateRegState }
     *     
     */
    public void setState(ConfTemplateRegState value) {
        this.state = value;
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
     * ��ȡsites���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfScheduleSiteParam2 }
     *     
     */
    public ArrayOfScheduleSiteParam2 getSites() {
        return sites;
    }

    /**
     * ����sites���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfScheduleSiteParam2 }
     *     
     */
    public void setSites(ArrayOfScheduleSiteParam2 value) {
        this.sites = value;
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
