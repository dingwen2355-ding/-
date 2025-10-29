
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AttendConferenceResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="attendInfo" type="{http://smc.huawei.com/}AttendConferenceInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "attendConferenceResult",
    "attendInfo"
})
@XmlRootElement(name = "AttendConferenceResponse")
public class AttendConferenceResponse {

    @XmlElement(name = "AttendConferenceResult")
    protected SmcErr2 attendConferenceResult;
    protected AttendConferenceInfo attendInfo;

    /**
     * ��ȡattendConferenceResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getAttendConferenceResult() {
        return attendConferenceResult;
    }

    /**
     * ����attendConferenceResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setAttendConferenceResult(SmcErr2 value) {
        this.attendConferenceResult = value;
    }

    /**
     * ��ȡattendInfo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link AttendConferenceInfo }
     *     
     */
    public AttendConferenceInfo getAttendInfo() {
        return attendInfo;
    }

    /**
     * ����attendInfo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link AttendConferenceInfo }
     *     
     */
    public void setAttendInfo(AttendConferenceInfo value) {
        this.attendInfo = value;
    }

}
