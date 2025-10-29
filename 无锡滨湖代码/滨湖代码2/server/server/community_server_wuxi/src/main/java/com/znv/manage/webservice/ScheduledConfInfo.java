
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ScheduledConfInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ScheduledConfInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="RecorderAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AccessCode" type="{http://smc.huawei.com/}ArrayOfAccessCode" minOccurs="0"/>
 *         &lt;element name="ChairmanUuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CommonUuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataConfId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UnifiedAccessCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Passcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChairToken" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConfAccessCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "ScheduledConfInfo", propOrder = {
    "id",
    "recorderAddr",
    "accessCode",
    "chairmanUuid",
    "commonUuid",
    "dataConfId",
    "unifiedAccessCode",
    "passcode",
    "chairToken",
    "confAccessCode",
    "enableConfSignIn",
    "signInTimeBeforeConf",
    "guestLink",
    "chairmanLink"
})
public class ScheduledConfInfo {

    @XmlElement(name = "Id")
    protected ConferenceId id;
    @XmlElement(name = "RecorderAddr")
    protected String recorderAddr;
    @XmlElement(name = "AccessCode")
    protected ArrayOfAccessCode accessCode;
    @XmlElement(name = "ChairmanUuid")
    protected String chairmanUuid;
    @XmlElement(name = "CommonUuid")
    protected String commonUuid;
    @XmlElement(name = "DataConfId")
    protected String dataConfId;
    @XmlElement(name = "UnifiedAccessCode")
    protected String unifiedAccessCode;
    @XmlElement(name = "Passcode")
    protected String passcode;
    @XmlElement(name = "ChairToken")
    protected String chairToken;
    @XmlElement(name = "ConfAccessCode")
    protected String confAccessCode;
    @XmlElement(name = "EnableConfSignIn", required = true, type = Boolean.class, nillable = true)
    protected Boolean enableConfSignIn;
    @XmlElement(name = "SignInTimeBeforeConf", required = true, type = Integer.class, nillable = true)
    protected Integer signInTimeBeforeConf;
    @XmlElement(name = "GuestLink")
    protected String guestLink;
    @XmlElement(name = "ChairmanLink")
    protected String chairmanLink;

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
     * ��ȡrecorderAddr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecorderAddr() {
        return recorderAddr;
    }

    /**
     * ����recorderAddr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecorderAddr(String value) {
        this.recorderAddr = value;
    }

    /**
     * ��ȡaccessCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAccessCode }
     *     
     */
    public ArrayOfAccessCode getAccessCode() {
        return accessCode;
    }

    /**
     * ����accessCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAccessCode }
     *     
     */
    public void setAccessCode(ArrayOfAccessCode value) {
        this.accessCode = value;
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
     * ��ȡdataConfId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataConfId() {
        return dataConfId;
    }

    /**
     * ����dataConfId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataConfId(String value) {
        this.dataConfId = value;
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
     * ��ȡpasscode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPasscode() {
        return passcode;
    }

    /**
     * ����passcode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPasscode(String value) {
        this.passcode = value;
    }

    /**
     * ��ȡchairToken���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChairToken() {
        return chairToken;
    }

    /**
     * ����chairToken���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChairToken(String value) {
        this.chairToken = value;
    }

    /**
     * ��ȡconfAccessCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfAccessCode() {
        return confAccessCode;
    }

    /**
     * ����confAccessCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfAccessCode(String value) {
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
