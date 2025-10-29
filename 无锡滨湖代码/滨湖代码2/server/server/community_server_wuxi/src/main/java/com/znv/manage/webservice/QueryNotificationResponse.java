
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
 *         &lt;element name="QueryNotificationResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="notifications" type="{http://smc.huawei.com/}ArrayOfNotification" minOccurs="0"/>
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
    "queryNotificationResult",
    "notifications"
})
@XmlRootElement(name = "QueryNotificationResponse")
public class QueryNotificationResponse {

    @XmlElement(name = "QueryNotificationResult")
    protected SmcErr2 queryNotificationResult;
    protected ArrayOfNotification notifications;

    /**
     * ��ȡqueryNotificationResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryNotificationResult() {
        return queryNotificationResult;
    }

    /**
     * ����queryNotificationResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryNotificationResult(SmcErr2 value) {
        this.queryNotificationResult = value;
    }

    /**
     * ��ȡnotifications���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfNotification }
     *     
     */
    public ArrayOfNotification getNotifications() {
        return notifications;
    }

    /**
     * ����notifications���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfNotification }
     *     
     */
    public void setNotifications(ArrayOfNotification value) {
        this.notifications = value;
    }

}
