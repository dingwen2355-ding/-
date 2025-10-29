
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SiteNetParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SiteNetParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteNetRevParam" type="{http://smc.huawei.com/}SiteNetRevParam" minOccurs="0"/>
 *         &lt;element name="SiteNetSenParam" type="{http://smc.huawei.com/}SiteNetSenParam" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SiteNetParam", propOrder = {
    "siteNetRevParam",
    "siteNetSenParam"
})
public class SiteNetParam {

    @XmlElement(name = "SiteNetRevParam")
    protected SiteNetRevParam siteNetRevParam;
    @XmlElement(name = "SiteNetSenParam")
    protected SiteNetSenParam siteNetSenParam;

    /**
     * ��ȡsiteNetRevParam���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SiteNetRevParam }
     *     
     */
    public SiteNetRevParam getSiteNetRevParam() {
        return siteNetRevParam;
    }

    /**
     * ����siteNetRevParam���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SiteNetRevParam }
     *     
     */
    public void setSiteNetRevParam(SiteNetRevParam value) {
        this.siteNetRevParam = value;
    }

    /**
     * ��ȡsiteNetSenParam���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SiteNetSenParam }
     *     
     */
    public SiteNetSenParam getSiteNetSenParam() {
        return siteNetSenParam;
    }

    /**
     * ����siteNetSenParam���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SiteNetSenParam }
     *     
     */
    public void setSiteNetSenParam(SiteNetSenParam value) {
        this.siteNetSenParam = value;
    }

}
