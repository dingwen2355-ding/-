
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="subscribeInfos" type="{http://smc.huawei.com/}ArrayOfSubscribeInfo" minOccurs="0"/>
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
    "subscribeInfos"
})
@XmlRootElement(name = "Subscribe")
public class Subscribe {

    protected ArrayOfSubscribeInfo subscribeInfos;

    /**
     * ��ȡsubscribeInfos���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSubscribeInfo }
     *     
     */
    public ArrayOfSubscribeInfo getSubscribeInfos() {
        return subscribeInfos;
    }

    /**
     * ����subscribeInfos���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSubscribeInfo }
     *     
     */
    public void setSubscribeInfos(ArrayOfSubscribeInfo value) {
        this.subscribeInfos = value;
    }

}
