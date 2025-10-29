
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DeviceStatusSubscribe complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="DeviceStatusSubscribe">
 *   &lt;complexContent>
 *     &lt;extension base="{http://smc.huawei.com/}SubscribeInfo">
 *       &lt;sequence>
 *         &lt;element name="SitesUriList" type="{http://smc.huawei.com/}ArrayOfString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceStatusSubscribe", propOrder = {
    "sitesUriList"
})
public class DeviceStatusSubscribe
    extends SubscribeInfo
{

    @XmlElement(name = "SitesUriList")
    protected ArrayOfString sitesUriList;

    /**
     * ��ȡsitesUriList���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getSitesUriList() {
        return sitesUriList;
    }

    /**
     * ����sitesUriList���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setSitesUriList(ArrayOfString value) {
        this.sitesUriList = value;
    }

}
