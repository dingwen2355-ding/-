
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>OngoingConfNotification complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="OngoingConfNotification">
 *   &lt;complexContent>
 *     &lt;extension base="{http://smc.huawei.com/}Notification">
 *       &lt;sequence>
 *         &lt;element name="Event" type="{http://smc.huawei.com/}EventTye"/>
 *         &lt;element name="ConfId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="RealConfId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="BeginTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="SiteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Changes" type="{http://smc.huawei.com/}ArrayOfNotificationChanges" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OngoingConfNotification", propOrder = {
    "event",
    "confId",
    "realConfId",
    "beginTime",
    "siteUri",
    "changes"
})
public class OngoingConfNotification
    extends Notification
{

    @XmlElement(name = "Event", required = true)
    @XmlSchemaType(name = "string")
    protected EventTye event;
    @XmlElement(name = "ConfId")
    protected ConferenceId confId;
    @XmlElement(name = "RealConfId")
    protected ConferenceId realConfId;
    @XmlElement(name = "BeginTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar beginTime;
    @XmlElement(name = "SiteUri")
    protected String siteUri;
    @XmlElement(name = "Changes")
    protected ArrayOfNotificationChanges changes;

    /**
     * ��ȡevent���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link EventTye }
     *     
     */
    public EventTye getEvent() {
        return event;
    }

    /**
     * ����event���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link EventTye }
     *     
     */
    public void setEvent(EventTye value) {
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
     * ��ȡchanges���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfNotificationChanges }
     *     
     */
    public ArrayOfNotificationChanges getChanges() {
        return changes;
    }

    /**
     * ����changes���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfNotificationChanges }
     *     
     */
    public void setChanges(ArrayOfNotificationChanges value) {
        this.changes = value;
    }

}
