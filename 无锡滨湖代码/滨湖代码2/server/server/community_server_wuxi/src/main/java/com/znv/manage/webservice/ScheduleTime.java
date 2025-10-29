
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>ScheduleTime complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ScheduleTime">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BeginTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="TimeZoneId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Duration" type="{http://smc.huawei.com/}TimeSpanEx" minOccurs="0"/>
 *         &lt;element name="Recurrence" type="{http://smc.huawei.com/}RecurrenceInfo" minOccurs="0"/>
 *         &lt;element name="IsInstantConf" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleTime", propOrder = {
    "beginTime",
    "timeZoneId",
    "duration",
    "recurrence",
    "isInstantConf"
})
public class ScheduleTime {

    @XmlElement(name = "BeginTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar beginTime;
    @XmlElement(name = "TimeZoneId")
    protected String timeZoneId;
    @XmlElement(name = "Duration")
    protected TimeSpanEx duration;
    @XmlElement(name = "Recurrence")
    protected RecurrenceInfo recurrence;
    @XmlElement(name = "IsInstantConf", required = true, type = Boolean.class, nillable = true)
    protected Boolean isInstantConf;

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
     * ��ȡtimeZoneId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeZoneId() {
        return timeZoneId;
    }

    /**
     * ����timeZoneId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeZoneId(String value) {
        this.timeZoneId = value;
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
     * ��ȡrecurrence���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RecurrenceInfo }
     *     
     */
    public RecurrenceInfo getRecurrence() {
        return recurrence;
    }

    /**
     * ����recurrence���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RecurrenceInfo }
     *     
     */
    public void setRecurrence(RecurrenceInfo value) {
        this.recurrence = value;
    }

    /**
     * ��ȡisInstantConf���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsInstantConf() {
        return isInstantConf;
    }

    /**
     * ����isInstantConf���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsInstantConf(Boolean value) {
        this.isInstantConf = value;
    }

}
