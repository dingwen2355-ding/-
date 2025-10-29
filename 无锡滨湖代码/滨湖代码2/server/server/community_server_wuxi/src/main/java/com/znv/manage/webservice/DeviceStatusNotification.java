
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DeviceStatusNotification complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="DeviceStatusNotification">
 *   &lt;complexContent>
 *     &lt;extension base="{http://smc.huawei.com/}Notification">
 *       &lt;sequence>
 *         &lt;element name="SiteURI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DevStatus" type="{http://smc.huawei.com/}DeviceStatusEx" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceStatusNotification", propOrder = {
    "siteURI",
    "devStatus"
})
public class DeviceStatusNotification
    extends Notification
{

    @XmlElement(name = "SiteURI")
    protected String siteURI;
    @XmlElement(name = "DevStatus")
    protected DeviceStatusEx devStatus;

    /**
     * ��ȡsiteURI���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteURI() {
        return siteURI;
    }

    /**
     * ����siteURI���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteURI(String value) {
        this.siteURI = value;
    }

    /**
     * ��ȡdevStatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link DeviceStatusEx }
     *     
     */
    public DeviceStatusEx getDevStatus() {
        return devStatus;
    }

    /**
     * ����devStatus���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceStatusEx }
     *     
     */
    public void setDevStatus(DeviceStatusEx value) {
        this.devStatus = value;
    }

}
