
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>SubscribeInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SubscribeInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IsSubscribe" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubscribeInfo", propOrder = {
    "isSubscribe"
})
@XmlSeeAlso({
    ScheduleConfSubscribe.class,
    OnGoingConfSubscribe.class,
    DeviceStatusSubscribe.class
})
public class SubscribeInfo {

    @XmlElement(name = "IsSubscribe")
    protected boolean isSubscribe;

    /**
     * ��ȡisSubscribe���Ե�ֵ��
     * 
     */
    public boolean isIsSubscribe() {
        return isSubscribe;
    }

    /**
     * ����isSubscribe���Ե�ֵ��
     * 
     */
    public void setIsSubscribe(boolean value) {
        this.isSubscribe = value;
    }

}
