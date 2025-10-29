
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SiteFreeBusyStateEx2 complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SiteFreeBusyStateEx2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Freebusys" type="{http://smc.huawei.com/}ArrayOfFreeBusy2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SiteFreeBusyStateEx2", propOrder = {
    "siteUri",
    "freebusys"
})
public class SiteFreeBusyStateEx2 {

    @XmlElement(name = "SiteUri")
    protected String siteUri;
    @XmlElement(name = "Freebusys")
    protected ArrayOfFreeBusy2 freebusys;

    /**
     * ��ȡsiteUri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteUri() {
        return siteUri;
    }

    /**
     * ����siteUri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteUri(String value) {
        this.siteUri = value;
    }

    /**
     * ��ȡfreebusys���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFreeBusy2 }
     *     
     */
    public ArrayOfFreeBusy2 getFreebusys() {
        return freebusys;
    }

    /**
     * ����freebusys���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFreeBusy2 }
     *     
     */
    public void setFreebusys(ArrayOfFreeBusy2 value) {
        this.freebusys = value;
    }

}
