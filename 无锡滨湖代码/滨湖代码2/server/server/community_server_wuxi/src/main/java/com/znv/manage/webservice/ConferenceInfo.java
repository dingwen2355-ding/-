
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>ConferenceInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ConferenceInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConfName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConfID" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="CreateUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaxSitesCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="EndTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="IsDataConference" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ConfMediaType" type="{http://smc.huawei.com/}ConfStreamType"/>
 *         &lt;element name="Status" type="{http://smc.huawei.com/}ConfStatus"/>
 *         &lt;element name="UnifiedAccessCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChairManPasscode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CommonPasscode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChairmanUuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CommonUuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EnableConfSignIn" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SignInTimeBeforeConf" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="GuestLink" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChairmanLink" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConferenceInfo", propOrder = {
    "confName",
    "confID",
    "createUser",
    "maxSitesCount",
    "startTime",
    "endTime",
    "isDataConference",
    "confMediaType",
    "status",
    "unifiedAccessCode",
    "chairManPasscode",
    "commonPasscode",
    "chairmanUuid",
    "commonUuid",
    "token",
    "enableConfSignIn",
    "signInTimeBeforeConf",
    "guestLink",
    "chairmanLink"
})
public class ConferenceInfo {

    @XmlElement(name = "ConfName")
    protected String confName;
    @XmlElement(name = "ConfID")
    protected ConferenceId confID;
    @XmlElement(name = "CreateUser")
    protected String createUser;
    @XmlElement(name = "MaxSitesCount", required = true, type = Integer.class, nillable = true)
    protected Integer maxSitesCount;
    @XmlElement(name = "StartTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;
    @XmlElement(name = "EndTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endTime;
    @XmlElement(name = "IsDataConference")
    protected boolean isDataConference;
    @XmlElement(name = "ConfMediaType", required = true)
    @XmlSchemaType(name = "string")
    protected ConfStreamType confMediaType;
    @XmlElement(name = "Status", required = true)
    @XmlSchemaType(name = "string")
    protected ConfStatus status;
    @XmlElement(name = "UnifiedAccessCode")
    protected String unifiedAccessCode;
    @XmlElement(name = "ChairManPasscode")
    protected String chairManPasscode;
    @XmlElement(name = "CommonPasscode")
    protected String commonPasscode;
    @XmlElement(name = "ChairmanUuid")
    protected String chairmanUuid;
    @XmlElement(name = "CommonUuid")
    protected String commonUuid;
    @XmlElement(name = "Token")
    protected String token;
    @XmlElement(name = "EnableConfSignIn", required = true, type = Boolean.class, nillable = true)
    protected Boolean enableConfSignIn;
    @XmlElement(name = "SignInTimeBeforeConf", required = true, type = Integer.class, nillable = true)
    protected Integer signInTimeBeforeConf;
    @XmlElement(name = "GuestLink")
    protected String guestLink;
    @XmlElement(name = "ChairmanLink")
    protected String chairmanLink;

    /**
     * ��ȡconfName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfName() {
        return confName;
    }

    /**
     * ����confName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfName(String value) {
        this.confName = value;
    }

    /**
     * ��ȡconfID���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceId }
     *     
     */
    public ConferenceId getConfID() {
        return confID;
    }

    /**
     * ����confID���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceId }
     *     
     */
    public void setConfID(ConferenceId value) {
        this.confID = value;
    }

    /**
     * ��ȡcreateUser���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * ����createUser���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateUser(String value) {
        this.createUser = value;
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
     * ��ȡstartTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * ����startTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
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
     * ��ȡisDataConference���Ե�ֵ��
     * 
     */
    public boolean isIsDataConference() {
        return isDataConference;
    }

    /**
     * ����isDataConference���Ե�ֵ��
     * 
     */
    public void setIsDataConference(boolean value) {
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
     * ��ȡunifiedAccessCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnifiedAccessCode() {
        return unifiedAccessCode;
    }

    /**
     * ����unifiedAccessCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnifiedAccessCode(String value) {
        this.unifiedAccessCode = value;
    }

    /**
     * ��ȡchairManPasscode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChairManPasscode() {
        return chairManPasscode;
    }

    /**
     * ����chairManPasscode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChairManPasscode(String value) {
        this.chairManPasscode = value;
    }

    /**
     * ��ȡcommonPasscode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommonPasscode() {
        return commonPasscode;
    }

    /**
     * ����commonPasscode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommonPasscode(String value) {
        this.commonPasscode = value;
    }

    /**
     * ��ȡchairmanUuid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChairmanUuid() {
        return chairmanUuid;
    }

    /**
     * ����chairmanUuid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChairmanUuid(String value) {
        this.chairmanUuid = value;
    }

    /**
     * ��ȡcommonUuid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommonUuid() {
        return commonUuid;
    }

    /**
     * ����commonUuid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommonUuid(String value) {
        this.commonUuid = value;
    }

    /**
     * ��ȡtoken���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * ����token���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
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
     * ��ȡguestLink���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuestLink() {
        return guestLink;
    }

    /**
     * ����guestLink���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuestLink(String value) {
        this.guestLink = value;
    }

    /**
     * ��ȡchairmanLink���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChairmanLink() {
        return chairmanLink;
    }

    /**
     * ����chairmanLink���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChairmanLink(String value) {
        this.chairmanLink = value;
    }

}
