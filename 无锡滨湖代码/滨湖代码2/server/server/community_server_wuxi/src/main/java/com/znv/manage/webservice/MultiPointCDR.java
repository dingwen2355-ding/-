
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>MultiPointCDR complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="MultiPointCDR">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteType" type="{http://smc.huawei.com/}SiteType"/>
 *         &lt;element name="ParticipantType" type="{http://smc.huawei.com/}ParticipantType"/>
 *         &lt;element name="Rate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="EndTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Duration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ConferenceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConferenceId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="ConferenceCreater" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsSoftTerminal" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MultiPointCDR", propOrder = {
    "siteName",
    "siteUri",
    "siteType",
    "participantType",
    "rate",
    "startTime",
    "endTime",
    "duration",
    "conferenceName",
    "conferenceId",
    "conferenceCreater",
    "billCode",
    "isSoftTerminal"
})
public class MultiPointCDR {

    @XmlElement(name = "SiteName")
    protected String siteName;
    @XmlElement(name = "SiteUri")
    protected String siteUri;
    @XmlElement(name = "SiteType", required = true)
    @XmlSchemaType(name = "string")
    protected SiteType siteType;
    @XmlElement(name = "ParticipantType", required = true)
    @XmlSchemaType(name = "string")
    protected ParticipantType participantType;
    @XmlElement(name = "Rate")
    protected String rate;
    @XmlElement(name = "StartTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;
    @XmlElement(name = "EndTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endTime;
    @XmlElement(name = "Duration")
    protected int duration;
    @XmlElement(name = "ConferenceName")
    protected String conferenceName;
    @XmlElement(name = "ConferenceId")
    protected ConferenceId conferenceId;
    @XmlElement(name = "ConferenceCreater")
    protected String conferenceCreater;
    @XmlElement(name = "BillCode")
    protected String billCode;
    @XmlElement(name = "IsSoftTerminal", required = true, type = Boolean.class, nillable = true)
    protected Boolean isSoftTerminal;

    /**
     * ��ȡsiteName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * ����siteName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteName(String value) {
        this.siteName = value;
    }

    /**
     * ��ȡsiteUri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteUri() {
        return siteUri;
    }

    /**
     * ����siteUri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteUri(String value) {
        this.siteUri = value;
    }

    /**
     * ��ȡsiteType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SiteType }
     *     
     */
    public SiteType getSiteType() {
        return siteType;
    }

    /**
     * ����siteType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SiteType }
     *     
     */
    public void setSiteType(SiteType value) {
        this.siteType = value;
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
     * ��ȡduration���Ե�ֵ��
     * 
     */
    public int getDuration() {
        return duration;
    }

    /**
     * ����duration���Ե�ֵ��
     * 
     */
    public void setDuration(int value) {
        this.duration = value;
    }

    /**
     * ��ȡconferenceName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConferenceName() {
        return conferenceName;
    }

    /**
     * ����conferenceName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConferenceName(String value) {
        this.conferenceName = value;
    }

    /**
     * ��ȡconferenceId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceId }
     *     
     */
    public ConferenceId getConferenceId() {
        return conferenceId;
    }

    /**
     * ����conferenceId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceId }
     *     
     */
    public void setConferenceId(ConferenceId value) {
        this.conferenceId = value;
    }

    /**
     * ��ȡconferenceCreater���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConferenceCreater() {
        return conferenceCreater;
    }

    /**
     * ����conferenceCreater���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConferenceCreater(String value) {
        this.conferenceCreater = value;
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
     * ��ȡisSoftTerminal���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSoftTerminal() {
        return isSoftTerminal;
    }

    /**
     * ����isSoftTerminal���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSoftTerminal(Boolean value) {
        this.isSoftTerminal = value;
    }

}
