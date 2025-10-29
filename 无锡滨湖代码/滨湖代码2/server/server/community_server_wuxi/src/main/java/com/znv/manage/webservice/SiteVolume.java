
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SiteVolume complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SiteVolume">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Volume" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SiteVolume", propOrder = {
    "siteUri",
    "volume"
})
public class SiteVolume {

    @XmlElement(name = "SiteUri")
    protected String siteUri;
    @XmlElement(name = "Volume")
    protected int volume;

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
     * ��ȡvolume���Ե�ֵ��
     * 
     */
    public int getVolume() {
        return volume;
    }

    /**
     * ����volume���Ե�ֵ��
     * 
     */
    public void setVolume(int value) {
        this.volume = value;
    }

}
