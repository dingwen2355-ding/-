
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>DeviceStatusEx complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="DeviceStatusEx">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GkRegStatus" type="{http://smc.huawei.com/}DeviceStatusEnum"/>
 *         &lt;element name="LiveStatus" type="{http://smc.huawei.com/}DeviceStatusEnum"/>
 *         &lt;element name="CallStatus" type="{http://smc.huawei.com/}DeviceStatusEnum"/>
 *         &lt;element name="SipStatus" type="{http://smc.huawei.com/}DeviceStatusEnum"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceStatusEx", propOrder = {
    "gkRegStatus",
    "liveStatus",
    "callStatus",
    "sipStatus"
})
public class DeviceStatusEx {

    @XmlElement(name = "GkRegStatus", required = true)
    @XmlSchemaType(name = "string")
    protected DeviceStatusEnum gkRegStatus;
    @XmlElement(name = "LiveStatus", required = true)
    @XmlSchemaType(name = "string")
    protected DeviceStatusEnum liveStatus;
    @XmlElement(name = "CallStatus", required = true)
    @XmlSchemaType(name = "string")
    protected DeviceStatusEnum callStatus;
    @XmlElement(name = "SipStatus", required = true)
    @XmlSchemaType(name = "string")
    protected DeviceStatusEnum sipStatus;

    /**
     * ��ȡgkRegStatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link DeviceStatusEnum }
     *     
     */
    public DeviceStatusEnum getGkRegStatus() {
        return gkRegStatus;
    }

    /**
     * ����gkRegStatus���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceStatusEnum }
     *     
     */
    public void setGkRegStatus(DeviceStatusEnum value) {
        this.gkRegStatus = value;
    }

    /**
     * ��ȡliveStatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link DeviceStatusEnum }
     *     
     */
    public DeviceStatusEnum getLiveStatus() {
        return liveStatus;
    }

    /**
     * ����liveStatus���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceStatusEnum }
     *     
     */
    public void setLiveStatus(DeviceStatusEnum value) {
        this.liveStatus = value;
    }

    /**
     * ��ȡcallStatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link DeviceStatusEnum }
     *     
     */
    public DeviceStatusEnum getCallStatus() {
        return callStatus;
    }

    /**
     * ����callStatus���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceStatusEnum }
     *     
     */
    public void setCallStatus(DeviceStatusEnum value) {
        this.callStatus = value;
    }

    /**
     * ��ȡsipStatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link DeviceStatusEnum }
     *     
     */
    public DeviceStatusEnum getSipStatus() {
        return sipStatus;
    }

    /**
     * ����sipStatus���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceStatusEnum }
     *     
     */
    public void setSipStatus(DeviceStatusEnum value) {
        this.sipStatus = value;
    }

}
