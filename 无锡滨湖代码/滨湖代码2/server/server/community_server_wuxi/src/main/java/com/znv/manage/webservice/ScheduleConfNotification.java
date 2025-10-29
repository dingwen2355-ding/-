
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>ScheduleConfNotification complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ScheduleConfNotification">
 *   &lt;complexContent>
 *     &lt;extension base="{http://smc.huawei.com/}Notification">
 *       &lt;sequence>
 *         &lt;element name="Event" type="{http://smc.huawei.com/}EventType"/>
 *         &lt;element name="ConfId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="RealConfId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="BeginTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleConfNotification", propOrder = {
    "event",
    "confId",
    "realConfId",
    "beginTime"
})
public class ScheduleConfNotification
    extends Notification
{

    @XmlElement(name = "Event", required = true)
    @XmlSchemaType(name = "string")
    protected EventType event;
    @XmlElement(name = "ConfId")
    protected ConferenceId confId;
    @XmlElement(name = "RealConfId")
    protected ConferenceId realConfId;
    @XmlElement(name = "BeginTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar beginTime;

    /**
     * ��ȡevent���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link EventType }
     *     
     */
    public EventType getEvent() {
        return event;
    }

    /**
     * ����event���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link EventType }
     *     
     */
    public void setEvent(EventType value) {
        this.event = value;
    }

    /**
     * ��ȡconfId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceId }
     *     
     */
    public ConferenceId getConfId() {
        return confId;
    }

    /**
     * ����confId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceId }
     *     
     */
    public void setConfId(ConferenceId value) {
        this.confId = value;
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

}
