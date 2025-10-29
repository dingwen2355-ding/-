
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>AttendConferenceReq complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="AttendConferenceReq">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MSAddrType" type="{http://smc.huawei.com/}AccessMSType"/>
 *         &lt;element name="MSAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConfId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Passcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EnableAV" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="EnableData" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="UserNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NickName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttendConferenceReq", propOrder = {
    "msAddrType",
    "msAddress",
    "confId",
    "passcode",
    "enableAV",
    "enableData",
    "userNum",
    "userId",
    "nickName"
})
public class AttendConferenceReq {

    @XmlElement(name = "MSAddrType", required = true)
    @XmlSchemaType(name = "string")
    protected AccessMSType msAddrType;
    @XmlElement(name = "MSAddress")
    protected String msAddress;
    @XmlElement(name = "ConfId")
    protected String confId;
    @XmlElement(name = "Passcode")
    protected String passcode;
    @XmlElement(name = "EnableAV")
    protected boolean enableAV;
    @XmlElement(name = "EnableData")
    protected boolean enableData;
    @XmlElement(name = "UserNum")
    protected String userNum;
    @XmlElement(name = "UserId")
    protected String userId;
    @XmlElement(name = "NickName")
    protected String nickName;

    /**
     * ��ȡmsAddrType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link AccessMSType }
     *     
     */
    public AccessMSType getMSAddrType() {
        return msAddrType;
    }

    /**
     * ����msAddrType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link AccessMSType }
     *     
     */
    public void setMSAddrType(AccessMSType value) {
        this.msAddrType = value;
    }

    /**
     * ��ȡmsAddress���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMSAddress() {
        return msAddress;
    }

    /**
     * ����msAddress���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMSAddress(String value) {
        this.msAddress = value;
    }

    /**
     * ��ȡconfId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfId() {
        return confId;
    }

    /**
     * ����confId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfId(String value) {
        this.confId = value;
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
     * ��ȡenableAV���Ե�ֵ��
     * 
     */
    public boolean isEnableAV() {
        return enableAV;
    }

    /**
     * ����enableAV���Ե�ֵ��
     * 
     */
    public void setEnableAV(boolean value) {
        this.enableAV = value;
    }

    /**
     * ��ȡenableData���Ե�ֵ��
     * 
     */
    public boolean isEnableData() {
        return enableData;
    }

    /**
     * ����enableData���Ե�ֵ��
     * 
     */
    public void setEnableData(boolean value) {
        this.enableData = value;
    }

    /**
     * ��ȡuserNum���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserNum() {
        return userNum;
    }

    /**
     * ����userNum���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserNum(String value) {
        this.userNum = value;
    }

    /**
     * ��ȡuserId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * ����userId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * ��ȡnickName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * ����nickName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNickName(String value) {
        this.nickName = value;
    }

}
