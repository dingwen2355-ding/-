
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>FreeBusy2 complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="FreeBusy2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConfId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="ConfName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConfCreaterInfo" type="{http://smc.huawei.com/}CreaterInfo" minOccurs="0"/>
 *         &lt;element name="TimeRange" type="{http://smc.huawei.com/}DateTimeRange" minOccurs="0"/>
 *         &lt;element name="State" type="{http://smc.huawei.com/}FreeBusyState"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FreeBusy2", propOrder = {
    "confId",
    "confName",
    "confCreaterInfo",
    "timeRange",
    "state"
})
public class FreeBusy2 {

    @XmlElement(name = "ConfId")
    protected ConferenceId confId;
    @XmlElement(name = "ConfName")
    protected String confName;
    @XmlElement(name = "ConfCreaterInfo")
    protected CreaterInfo confCreaterInfo;
    @XmlElement(name = "TimeRange")
    protected DateTimeRange timeRange;
    @XmlElement(name = "State", required = true)
    @XmlSchemaType(name = "string")
    protected FreeBusyState state;

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
     * ��ȡconfCreaterInfo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link CreaterInfo }
     *     
     */
    public CreaterInfo getConfCreaterInfo() {
        return confCreaterInfo;
    }

    /**
     * ����confCreaterInfo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link CreaterInfo }
     *     
     */
    public void setConfCreaterInfo(CreaterInfo value) {
        this.confCreaterInfo = value;
    }

    /**
     * ��ȡtimeRange���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link DateTimeRange }
     *     
     */
    public DateTimeRange getTimeRange() {
        return timeRange;
    }

    /**
     * ����timeRange���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeRange }
     *     
     */
    public void setTimeRange(DateTimeRange value) {
        this.timeRange = value;
    }

    /**
     * ��ȡstate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link FreeBusyState }
     *     
     */
    public FreeBusyState getState() {
        return state;
    }

    /**
     * ����state���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link FreeBusyState }
     *     
     */
    public void setState(FreeBusyState value) {
        this.state = value;
    }

}
