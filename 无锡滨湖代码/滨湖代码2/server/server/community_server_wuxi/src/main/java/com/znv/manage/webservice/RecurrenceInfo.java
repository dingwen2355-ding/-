
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>RecurrenceInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="RecurrenceInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Frequency" type="{http://smc.huawei.com/}RecurrenceFrequency"/>
 *         &lt;element name="Interval" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="WeekDays" type="{http://smc.huawei.com/}ArrayOfDayOfWeek" minOccurs="0"/>
 *         &lt;element name="WeekDay" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MonthDay" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Month" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TimeZone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Region" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FirstDayOfWeek" type="{http://smc.huawei.com/}DayOfWeek"/>
 *         &lt;element name="Range" type="{http://smc.huawei.com/}RecurrenceRange" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecurrenceInfo", propOrder = {
    "frequency",
    "interval",
    "weekDays",
    "weekDay",
    "monthDay",
    "month",
    "timeZone",
    "region",
    "firstDayOfWeek",
    "range"
})
public class RecurrenceInfo {

    @XmlElement(name = "Frequency", required = true)
    @XmlSchemaType(name = "string")
    protected RecurrenceFrequency frequency;
    @XmlElement(name = "Interval")
    protected int interval;
    @XmlElement(name = "WeekDays")
    protected ArrayOfDayOfWeek weekDays;
    @XmlElement(name = "WeekDay")
    protected int weekDay;
    @XmlElement(name = "MonthDay")
    protected int monthDay;
    @XmlElement(name = "Month")
    protected int month;
    @XmlElement(name = "TimeZone")
    protected String timeZone;
    @XmlElement(name = "Region")
    protected String region;
    @XmlElement(name = "FirstDayOfWeek", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected DayOfWeek firstDayOfWeek;
    @XmlElement(name = "Range")
    protected RecurrenceRange range;

    /**
     * ��ȡfrequency���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RecurrenceFrequency }
     *     
     */
    public RecurrenceFrequency getFrequency() {
        return frequency;
    }

    /**
     * ����frequency���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RecurrenceFrequency }
     *     
     */
    public void setFrequency(RecurrenceFrequency value) {
        this.frequency = value;
    }

    /**
     * ��ȡinterval���Ե�ֵ��
     * 
     */
    public int getInterval() {
        return interval;
    }

    /**
     * ����interval���Ե�ֵ��
     * 
     */
    public void setInterval(int value) {
        this.interval = value;
    }

    /**
     * ��ȡweekDays���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDayOfWeek }
     *     
     */
    public ArrayOfDayOfWeek getWeekDays() {
        return weekDays;
    }

    /**
     * ����weekDays���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDayOfWeek }
     *     
     */
    public void setWeekDays(ArrayOfDayOfWeek value) {
        this.weekDays = value;
    }

    /**
     * ��ȡweekDay���Ե�ֵ��
     * 
     */
    public int getWeekDay() {
        return weekDay;
    }

    /**
     * ����weekDay���Ե�ֵ��
     * 
     */
    public void setWeekDay(int value) {
        this.weekDay = value;
    }

    /**
     * ��ȡmonthDay���Ե�ֵ��
     * 
     */
    public int getMonthDay() {
        return monthDay;
    }

    /**
     * ����monthDay���Ե�ֵ��
     * 
     */
    public void setMonthDay(int value) {
        this.monthDay = value;
    }

    /**
     * ��ȡmonth���Ե�ֵ��
     * 
     */
    public int getMonth() {
        return month;
    }

    /**
     * ����month���Ե�ֵ��
     * 
     */
    public void setMonth(int value) {
        this.month = value;
    }

    /**
     * ��ȡtimeZone���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * ����timeZone���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeZone(String value) {
        this.timeZone = value;
    }

    /**
     * ��ȡregion���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegion() {
        return region;
    }

    /**
     * ����region���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegion(String value) {
        this.region = value;
    }

    /**
     * ��ȡfirstDayOfWeek���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link DayOfWeek }
     *     
     */
    public DayOfWeek getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    /**
     * ����firstDayOfWeek���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link DayOfWeek }
     *     
     */
    public void setFirstDayOfWeek(DayOfWeek value) {
        this.firstDayOfWeek = value;
    }

    /**
     * ��ȡrange���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RecurrenceRange }
     *     
     */
    public RecurrenceRange getRange() {
        return range;
    }

    /**
     * ����range���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RecurrenceRange }
     *     
     */
    public void setRange(RecurrenceRange value) {
        this.range = value;
    }

}
