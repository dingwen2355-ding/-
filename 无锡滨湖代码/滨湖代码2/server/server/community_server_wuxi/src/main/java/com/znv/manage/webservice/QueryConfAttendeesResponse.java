
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
 *         &lt;element name="QueryConfAttendeesResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="attendeeList" type="{http://smc.huawei.com/}ArrayOfAttendee" minOccurs="0"/>
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
    "queryConfAttendeesResult",
    "attendeeList"
})
@XmlRootElement(name = "QueryConfAttendeesResponse")
public class QueryConfAttendeesResponse {

    @XmlElement(name = "QueryConfAttendeesResult")
    protected SmcErr2 queryConfAttendeesResult;
    protected ArrayOfAttendee attendeeList;

    /**
     * ��ȡqueryConfAttendeesResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryConfAttendeesResult() {
        return queryConfAttendeesResult;
    }

    /**
     * ����queryConfAttendeesResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryConfAttendeesResult(SmcErr2 value) {
        this.queryConfAttendeesResult = value;
    }

    /**
     * ��ȡattendeeList���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAttendee }
     *     
     */
    public ArrayOfAttendee getAttendeeList() {
        return attendeeList;
    }

    /**
     * ����attendeeList���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAttendee }
     *     
     */
    public void setAttendeeList(ArrayOfAttendee value) {
        this.attendeeList = value;
    }

}
